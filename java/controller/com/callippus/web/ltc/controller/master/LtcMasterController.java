package com.callippus.web.ltc.controller.master;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

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

import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.ltc.beans.application.LtcApplicationBean;
import com.callippus.web.ltc.business.master.LtcMasterBusiness;

@Controller
@RequestMapping("/" + CPSConstants.LTCMASTER)
@SessionAttributes
public class LtcMasterController {
	private static Log log = LogFactory.getLog(LtcMasterController.class);
	@Autowired
	private LtcMasterBusiness ltcMasterBusiness;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.LTCMASTER) LtcApplicationBean ltcBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String viewName = null;
		String message = "";
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);

			if (CPSUtils.isNullOrEmpty(ltcBean.getParam())) {
				ltcBean.setParam(CPSConstants.HOME);
			} else if (CPSUtils.compareStrings(ltcBean.getParam(), CPSConstants.PAGING)) {
				viewName = session.getAttribute(CPSConstants.RETURN).toString();
			}
			ltcBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
			if (!CPSUtils.isNullOrEmpty(ltcBean.getType())) {
				session.setAttribute(CPSConstants.MASTERTYPE, ltcBean.getType());
			}
			if (CPSUtils.compareStrings(CPSConstants.HOME, ltcBean.getParam())) {
				log.debug("LtcMasterController --> onSubmit --> param=home");
				if (CPSUtils.isNullOrEmpty(ltcBean.getType())) {
					if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.MASTERTYPE)))
						ltcBean.setType(session.getAttribute(CPSConstants.MASTERTYPE).toString());
					else
						ltcBean.setType(CPSConstants.LTCTYPE);
				}
				ltcBean = ltcMasterBusiness.getMasterData(ltcBean);
				if (CPSUtils.compareStrings(ltcBean.getType(), CPSConstants.LTCBLOCKYEAR)) {
					session.setAttribute(CPSConstants.LTCBLOCKLIST, ltcBean.getLtcBlockDetails());
					session.setAttribute(CPSConstants.LTCBLOCKYEARLIST, ltcBean.getLtcBlockYearList());
				}
				viewName = CPSConstants.LTCMASTERDATA;

				session.setAttribute(CPSConstants.RETURN, CPSConstants.LTCMASTERDATALIST);
			} else if (CPSUtils.compareStrings(CPSConstants.MANAGE, ltcBean.getParam())) {
				log.debug("LtcMasterController --> onSubmit --> param=manage");
				message = ltcMasterBusiness.manageLtcMasterData(ltcBean);
				ltcBean = ltcMasterBusiness.getMasterData(ltcBean);
				viewName = CPSConstants.LTCMASTERDATALIST;
				session.setAttribute(CPSConstants.RETURN, viewName);
			} else if (CPSUtils.compareStrings(CPSConstants.DELETE, ltcBean.getParam())) {
				log.debug("LtcMasterController --> onSubmit --> param=delete");
				message = ltcMasterBusiness.deleteLtcMasterData(ltcBean);
				ltcBean = ltcMasterBusiness.getMasterData(ltcBean);
				viewName = CPSConstants.LTCMASTERDATALIST;
				session.setAttribute(CPSConstants.RETURN, viewName);
			}

			mav = new ModelAndView(viewName, CPSConstants.LTCMASTER, ltcBean);
			mav.addObject(CPSConstants.MESSAGE, message);
			if (CPSUtils.checkList(ltcBean.getMasterDataList())) {
				session.setAttribute(CPSConstants.JSONMASTERLIST, (JSONArray) JSONSerializer.toJSON(ltcBean.getMasterDataList()));
			}
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}