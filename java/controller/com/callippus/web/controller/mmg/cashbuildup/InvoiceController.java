package com.callippus.web.controller.mmg.cashbuildup;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.callippus.web.business.mmg.cashbuildup.InvoiceBusiness;
import com.callippus.web.business.mmg.cashbuildup.workflow.InvoiceRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.mmg.cashbuildup.beans.IRMasterBean;
import com.callippus.web.mmg.cashbuildup.beans.InvoiceRequestBean;

@Controller
@RequestMapping("/invoiceReceipt")
@SessionAttributes
public class InvoiceController {
	private static Log log = LogFactory.getLog(InvoiceController.class);
	@Autowired
	private InvoiceBusiness invoiceBusiness;
	@Autowired
	private InvoiceRequestProcess invoiceRequestProcess;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute IRMasterBean invoice, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		log.debug("invoiceController on submit");
		String message = "";
		HttpSession session = null;
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);
			if (CPSUtils.compareStrings(CPSConstants.HOME, request.getParameter(CPSConstants.PARAM))) {
				log.debug("invoiceController --> onSubmit --> param=home");
				invoice = invoiceBusiness.getMasterData(invoice, request);
				mav = new ModelAndView(CPSConstants.INVOICEMASTER, CPSConstants.COMMAND, invoice);
			} else if (CPSUtils.compareStrings(CPSConstants.GETDEMANDETAILS, request.getParameter(CPSConstants.PARAM))) {
				log.debug("DemandController --> onSubmit --> param=deleteItem");
				invoice = invoiceBusiness.getDemandDetails(invoice, request);
				invoice = invoiceBusiness.getMasterData(invoice, request);
				mav = new ModelAndView(CPSConstants.INVOICEMASTER, CPSConstants.COMMAND, invoice);
			} else if (CPSUtils.compareStrings(CPSConstants.PREVIEW, request.getParameter(CPSConstants.PARAM))) {
				log.debug("invoiceController --> onSubmit --> param=preview");
				if (!CPSUtils.isNullOrEmpty(request.getParameter("cashReceiptPreviewJson")))
					session.setAttribute("cashReceiptPreviewJson", JSONSerializer.toJSON(request.getParameter("cashReceiptPreviewJson")));
				mav = new ModelAndView(CPSConstants.INVOICEPREVIEW, CPSConstants.COMMAND, invoice);
			} else if (CPSUtils.compareStrings(CPSConstants.SUBMITREQUEST, request.getParameter(CPSConstants.PARAM))) {
				log.debug("invoiceController --> onSubmit --> param=submitRequest");
				/**
				 * invoiceRequest mapping invoice bean to invoiceRequestBean
				 */
				String type = invoice.getType();
				String taxType = invoice.getTaxType();
				if (!CPSUtils.isNull(session.getAttribute("InvoiceRequest"))) {
					invoice = (IRMasterBean) session.getAttribute("InvoiceRequest");
					invoice.setType(type);
					invoice.setTaxType(taxType);
				}
				InvoiceRequestBean drb = new InvoiceRequestBean();
				BeanUtils.copyProperties(drb, invoice);
				drb.setItemsJson((JSONArray) session.getAttribute(CPSConstants.INVOICEITEMSJSON));
				drb.setSfID((String) session.getAttribute(CPSConstants.SFID));
				drb.setIpAddress(request.getRemoteAddr());
				message = invoiceRequestProcess.initWorkflow(drb);
				mav = new ModelAndView(CPSConstants.REQUESTRESULTPAGE, CPSConstants.COMMAND, invoice);
				mav.addObject(CPSConstants.MESSAGE, message);
				return mav;
			} else if (CPSUtils.compareStrings("cancelInvoice", request.getParameter(CPSConstants.PARAM))) {
				log.debug("InvoiceController --> onSubmit --> param=cancelInvoice");
				List<IRMasterBean> invoiceList = invoiceBusiness.getInvoiceNumbers();
				session.setAttribute("invoiceList", invoiceList);
				mav = new ModelAndView("InvoiceCancel", CPSConstants.COMMAND, invoice);
			}
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}
