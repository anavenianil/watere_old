package com.callippus.web.business.mmg.cashbuildup;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.mmg.cashbuildup.IMMGMasterDAO;
import com.callippus.web.dao.mmg.cashbuildup.IVoucherDAO;
import com.callippus.web.mmg.cashbuildup.beans.VoucherMasterBean;
import com.callippus.web.mmg.cashbuildup.dto.AccountHeadDTO;
import com.callippus.web.mmg.cashbuildup.dto.UomDTO;
import com.callippus.web.mmg.cashbuildup.dto.VoucherTypeDTO;

@Service
public class VoucherBusiness {
	@Autowired
	private IComonObjectDAO commonDataDAO;
	@Autowired
	private IVoucherDAO voucherDAO;
	@Autowired
	private IMMGMasterDAO mmgmasterDAO;

	public List<VoucherTypeDTO> getVoucherTypes() throws Exception {
		List<VoucherTypeDTO> list = null;
		try {
			list = voucherDAO.getVoucherTypes();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public List<UomDTO> getUomTypes() throws Exception {
		List<UomDTO> list = null;
		try {
			list = commonDataDAO.getUomTypes();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public VoucherMasterBean getVoucherNum(VoucherMasterBean voucherMasterBean) throws Exception {
		String vNum = null;
		try {
			int vId = Integer.parseInt(voucherDAO.getVoucherId("VoucherMasterBean", "voucherId")) + 1;
			if (CPSUtils.compareStrings(voucherMasterBean.getType(), CPSConstants.EXTERNALRECEIPTVOUVHER)) {
				vNum = "XR";
			} else if (CPSUtils.compareStrings(voucherMasterBean.getType(), CPSConstants.EXTERNALISSUEVOUVHER)) {
				vNum = "XI";
			} else if (CPSUtils.compareStrings(voucherMasterBean.getType(), CPSConstants.EXPENSEVOUVHER)) {
				vNum = "EV";
			} else if (CPSUtils.compareStrings(voucherMasterBean.getType(), CPSConstants.TRANSFERVOUVHER)) {
				vNum = "TV";
			} else if (CPSUtils.compareStrings(voucherMasterBean.getType(), CPSConstants.CONDEMNATIONVOUVHER)) {
				vNum = "CDV";
			} else if (CPSUtils.compareStrings(voucherMasterBean.getType(), CPSConstants.CONVERSIONVOUVHER)) {
				vNum = "CV";
			} else if (CPSUtils.compareStrings(voucherMasterBean.getType(), CPSConstants.CASHRECEIPTVOUCHER)) {
				vNum = "CRV";
			} else if (CPSUtils.compareStrings(voucherMasterBean.getType(), CPSConstants.CERTIFICATESANCTIONVOUCHER)) {
				vNum = "CS";
			}

			vNum += String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).substring(2) + "-" + new DecimalFormat("0000").format(vId);
			voucherMasterBean.setVoucherId(vNum);
			voucherMasterBean.setVoucherDate(CPSUtils.getCurrentDate());
			voucherMasterBean.setInventoryNumsList(commonDataDAO.getInventoryNumsList(voucherMasterBean.getInventorySfid()));
			if (CPSUtils.compareStrings(voucherMasterBean.getType(), CPSConstants.TRANSFERVOUVHER))
				voucherMasterBean.setToInventoryNumsList(voucherDAO.getToInventoryNumsList());
		} catch (Exception e) {
			throw e;
		}
		return voucherMasterBean;
	}

	public String checkingAccountHeadAmt(VoucherMasterBean voucherMasterBean) throws Exception {
		String message = null;
		try {
			AccountHeadDTO accdto = mmgmasterDAO.checkingAccountHeadAmt(voucherMasterBean.getInventoryNo());
			if (!CPSUtils.isNullOrEmpty(voucherMasterBean.getWorkAmount())) {
				float amt = Float.parseFloat(accdto.getAllottedFund()) - (Float.parseFloat(accdto.getConsumedFund()) + Float.parseFloat(accdto.getCommitedFund()));
				if (amt >= Float.parseFloat(voucherMasterBean.getWorkAmount())) {
					message = CPSConstants.SUCCESS;
				} else
					message = CPSConstants.NOTENOUGHAMOUNT;
			}
		} catch (Exception e) {
			throw e;
		}
		return message;
	}
}
