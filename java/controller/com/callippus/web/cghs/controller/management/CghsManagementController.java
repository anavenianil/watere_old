package com.callippus.web.cghs.controller.management;

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

import com.callippus.web.cghs.beans.management.CghsManagementBean;
import com.callippus.web.cghs.business.management.CghsManagementBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@RequestMapping("/cghs.htm")
@SessionAttributes
public class CghsManagementController {
	private static Log log = LogFactory.getLog(CghsManagementController.class);
	@Autowired
	private CghsManagementBusiness cghsManagementBusiness;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute CghsManagementBean cghsBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);
			if (CPSUtils.isNullOrEmpty(cghsBean.getParam())) {
				cghsBean.setParam(session.getAttribute(CPSConstants.HOME).toString());
			} else if (CPSUtils.compareStrings(CPSConstants.PAGING, cghsBean.getParam())) {
				mav = new ModelAndView(session.getAttribute(CPSConstants.RETURN).toString(), CPSConstants.CGHS, cghsBean);
			}
			log.debug("CghsManagementController >>>>>>>>> Start" + cghsBean.getParam());
			if (CPSUtils.compareStrings(CPSConstants.HOME, cghsBean.getParam())) {
				cghsManagementBusiness.manageReferralHospital(cghsBean);
				mav = new ModelAndView(CPSConstants.REFERRALHOSPITALDETAILS, CPSConstants.CGHS, cghsBean);
				session.setAttribute(CPSConstants.RETURN, CPSConstants.REFERRALHOSPITALLIST);
				session.setAttribute(CPSConstants.HOME, CPSConstants.HOME);
			} else if (CPSUtils.compareStrings(CPSConstants.MANAGE, cghsBean.getParam())) {
				cghsBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				cghsBean.setMessage(cghsManagementBusiness.manageReferralHospital(cghsBean));
				mav = new ModelAndView(CPSConstants.REFERRALHOSPITALLIST, CPSConstants.CGHS, cghsBean);
				mav.addObject(CPSConstants.MESSAGE, cghsBean.getMessage());
			} else if (CPSUtils.compareStrings(CPSConstants.DELETE, cghsBean.getParam())) {
				cghsBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				cghsBean.setMessage(cghsManagementBusiness.manageReferralHospital(cghsBean));
				mav = new ModelAndView(CPSConstants.REFERRALHOSPITALLIST, CPSConstants.CGHS, cghsBean);
				mav.addObject(CPSConstants.MESSAGE, cghsBean.getMessage());
			} else if (CPSUtils.compareStrings(CPSConstants.SELECTEDDELETE, cghsBean.getParam())) {
				cghsBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				cghsBean.setMessage(cghsManagementBusiness.manageReferralHospital(cghsBean));
				mav = new ModelAndView(CPSConstants.REFERRALHOSPITALLIST, CPSConstants.CGHS, cghsBean);
				mav.addObject(CPSConstants.MESSAGE, cghsBean.getMessage());
			} else if (CPSUtils.compareStrings(CPSConstants.WARDHOME, cghsBean.getParam())) {
				cghsManagementBusiness.manageWardType(cghsBean);
				mav = new ModelAndView(CPSConstants.WARDTYPEDETAILS, CPSConstants.CGHS, cghsBean);
				session.setAttribute(CPSConstants.RETURN, CPSConstants.WARDTYPELIST);
				session.setAttribute(CPSConstants.HOME, CPSConstants.WARDHOME);
			} else if (CPSUtils.compareStrings(CPSConstants.WARDMANAGE, cghsBean.getParam())) {
				cghsBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				cghsBean.setMessage(cghsManagementBusiness.manageWardType(cghsBean));
				mav = new ModelAndView(CPSConstants.WARDTYPELIST, CPSConstants.CGHS, cghsBean);
				mav.addObject(CPSConstants.MESSAGE, cghsBean.getMessage());
			} else if (CPSUtils.compareStrings(CPSConstants.WARDDELETE, cghsBean.getParam())) {
				cghsBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				cghsBean.setMessage(cghsManagementBusiness.manageWardType(cghsBean));
				mav = new ModelAndView(CPSConstants.WARDTYPELIST, CPSConstants.CGHS, cghsBean);
				mav.addObject(CPSConstants.MESSAGE, cghsBean.getMessage());
			}
			if (!CPSUtils.isNull(cghsBean.getWardTypeList())) {
				session.setAttribute(CPSConstants.WARDTYPELIST, (JSONArray) JSONSerializer.toJSON(cghsBean.getWardTypeList()));
			} else if (!CPSUtils.isNull(cghsBean.getReferralHospitalList())) {
				session.setAttribute(CPSConstants.REFERRALHOSPITALLIST, (JSONArray) JSONSerializer.toJSON(cghsBean.getReferralHospitalList()));
			}
			log.debug("CghsManagementController >>>>>>>>> End" + cghsBean.getParam());

		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}

}
