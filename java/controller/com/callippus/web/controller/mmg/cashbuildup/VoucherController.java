package com.callippus.web.controller.mmg.cashbuildup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.callippus.web.controller.exception.ErpAction;
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

import com.callippus.web.business.mmg.cashbuildup.VoucherBusiness;
import com.callippus.web.business.mmg.cashbuildup.workflow.VoucherRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.mmg.cashbuildup.beans.VoucherMasterBean;
import com.callippus.web.mmg.cashbuildup.beans.VoucherRequestBean;

/**
 * @MMG Cash Buildup VoucherController
 * 
 */

@Controller
@RequestMapping("/" + CPSConstants.MMGVOUCHER)
@SessionAttributes
public class VoucherController {
	private static Log log = LogFactory.getLog(VoucherController.class);
	@Autowired
	private VoucherBusiness voucherBusiness;
	@Autowired
	private VoucherRequestProcess voucherRequestProcess;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView onSubmit(@ModelAttribute(CPSConstants.MMGVOUCHER) VoucherMasterBean voucherMasterBean, BindingResult result, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = null;
		String viewName = "";
		String message = "";
		try {
			ErpAction.userChecks(request);
			HttpSession session = request.getSession(true);

			if (CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.MMGVOUCHER)))
				session.setAttribute(CPSConstants.MMGVOUCHER, CPSConstants.VOUCHER);
			if (CPSUtils.isNull(request.getParameter(CPSConstants.PARAM)) || CPSUtils.compareStrings(CPSConstants.HOME, request.getParameter(CPSConstants.PARAM))) {
				log.debug("VoucherController --> onSubmit --> param=home");
				if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.DEMANDITEMSJSON)))
					session.removeAttribute(CPSConstants.DEMANDITEMSJSON);
				viewName = CPSConstants.MMGCPVOUCHER;
				session.setAttribute(CPSConstants.VOUCHERTYPELIST, voucherBusiness.getVoucherTypes());
				session.setAttribute(CPSConstants.UOMTYPESLIST, voucherBusiness.getUomTypes());
				voucherMasterBean.setPostingDate(CPSUtils.getCurrentDate());
			} else if (CPSUtils.compareStrings(CPSConstants.GETVOUCHERS, request.getParameter(CPSConstants.PARAM))) {
				log.debug("VoucherController --> onSubmit --> param=getVouchers");
				if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.DEMANDITEMSJSON)))
					session.removeAttribute(CPSConstants.DEMANDITEMSJSON);
				if (CPSUtils.compareStrings(voucherMasterBean.getType(), CPSConstants.EXPENSEVOUVHER))
					viewName = CPSConstants.EXPENSEVOUVHER;
				else if (CPSUtils.compareStrings(voucherMasterBean.getType(), CPSConstants.TRANSFERVOUVHER))
					viewName = CPSConstants.TRANSFERVOUVHER;
				else if (CPSUtils.compareStrings(voucherMasterBean.getType(), CPSConstants.CONDEMNATIONVOUVHER))
					viewName = CPSConstants.CONDEMNATIONVOUVHER;
				else if (CPSUtils.compareStrings(voucherMasterBean.getType(), CPSConstants.CONVERSIONVOUVHER))
					viewName = CPSConstants.CONVERSIONVOUVHER;
				else if (CPSUtils.compareStrings(voucherMasterBean.getType(), CPSConstants.CERTIFICATESANCTIONVOUCHER))
					viewName = CPSConstants.CERTIFICATESANCTIONVOUCHER;
				else
					viewName = CPSConstants.EXTERNALRECEIPTVOUVHER;
				voucherMasterBean.setType(request.getParameter(CPSConstants.TYPE));
				voucherMasterBean.setInventorySfid(session.getAttribute(CPSConstants.SFID).toString());
				voucherMasterBean = voucherBusiness.getVoucherNum(voucherMasterBean);

			} else if (CPSUtils.compareStrings(CPSConstants.SUBMITREQUEST, request.getParameter(CPSConstants.PARAM))) {
				log.debug("VoucherController --> onSubmit --> param=submitRequest");
				VoucherRequestBean vRequestBean = new VoucherRequestBean();
				BeanUtils.copyProperties(vRequestBean, voucherMasterBean);
				vRequestBean.setItemsJson((JSONArray) session.getAttribute(CPSConstants.DEMANDITEMSJSON));
				vRequestBean.setSfID((String) session.getAttribute(CPSConstants.SFID));
				vRequestBean.setIpAddress(request.getRemoteAddr());
				if (CPSUtils.compareStrings(vRequestBean.getVoucherTypeId(), "8")) {
					message = voucherBusiness.checkingAccountHeadAmt(voucherMasterBean);
					if (CPSUtils.compareStrings(CPSConstants.SUCCESS, message))
						message = voucherRequestProcess.initWorkflow(vRequestBean);
				} else
					message = voucherRequestProcess.initWorkflow(vRequestBean);
				viewName = CPSConstants.REQUESTRESULTPAGE;
			} else if (CPSUtils.compareStrings("voucherPreview", request.getParameter(CPSConstants.PARAM))) {
				viewName = "VoucherPreview";
				if (!CPSUtils.isNullOrEmpty(request.getParameter("previewJson")))
					session.setAttribute("previewJson", JSONSerializer.toJSON(request.getParameter("previewJson")));
			}
			mav = new ModelAndView(viewName, CPSConstants.MMGVOUCHER, voucherMasterBean);
			mav.addObject(CPSConstants.MESSAGE, message);

		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}
