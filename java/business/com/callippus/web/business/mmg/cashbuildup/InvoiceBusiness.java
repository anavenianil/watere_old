package com.callippus.web.business.mmg.cashbuildup;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.mmg.cashbuildup.IMMGMasterDAO;
import com.callippus.web.dao.mmg.cashbuildup.IVoucherDAO;
import com.callippus.web.mmg.cashbuildup.beans.DemandMasterDTO;
import com.callippus.web.mmg.cashbuildup.beans.IRMasterBean;

@Service
public class InvoiceBusiness {
	@Autowired
	private IMMGMasterDAO mmgMasterDAO;
	@Autowired
	private IVoucherDAO voucherDAO;
	@Autowired
	private IComonObjectDAO commonDAO;

	public IRMasterBean getMasterData(IRMasterBean invoice, HttpServletRequest request) throws Exception {
		List<DemandMasterDTO> demandList = null;
		try {
			demandList = mmgMasterDAO.getDemandNumbers(CPSConstants.DEMANDSECURITY);
			invoice.setDemandList(demandList);
			int vId = Integer.parseInt(voucherDAO.getVoucherId("IRMasterBean", "voucherNo")) + 1;
			String vNum = "CRV";
			vNum += String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).substring(2) + "-" + new DecimalFormat("0000").format(vId);
			invoice.setVoucherNo(vNum);
			invoice.setVoucherDate(CPSUtils.getCurrentDateWithTime());
		} catch (Exception e) {
			throw e;
		}
		return invoice;
	}

	@SuppressWarnings("unchecked")
	public IRMasterBean getDemandDetails(IRMasterBean invoice, HttpServletRequest request) throws Exception {
		HttpSession session = null;
		try {
			session = request.getSession(true);
			invoice = voucherDAO.getSecurityDetails(invoice.getDemandNo(), CPSConstants.DEMANDSECURITY);
			session.setAttribute("InvoiceRequest", invoice);
			session.setAttribute("IRItemsJson", (JSONArray) JSONSerializer.toJSON(invoice.getIrItems()));
			List<Object> taxTypeList = commonDAO.getMasterData("TaxTypeDTO");
			invoice.setTaxTypeList(taxTypeList);
			session.setAttribute("taxTypeList", (JSONArray) JSONSerializer.toJSON(taxTypeList));
		} catch (Exception e) {
			throw e;
		}
		return invoice;
	}

	public List<IRMasterBean> getInvoiceNumbers() throws Exception {
		List<IRMasterBean> invoiceList = null;
		try {
			invoiceList = voucherDAO.getInvoiceNumbers("1");
		} catch (Exception e) {
			throw e;
		}
		return invoiceList;
	}

}
