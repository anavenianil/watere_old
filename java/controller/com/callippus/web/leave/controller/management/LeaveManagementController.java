package com.callippus.web.leave.controller.management;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

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
import com.callippus.web.leave.beans.management.LeaveManagementBean;
import com.callippus.web.leave.business.management.LeaveManagementBusiness;

@Controller
@RequestMapping("/leave.htm")
@SessionAttributes
public class LeaveManagementController {
	@Autowired
	private LeaveManagementBusiness leaveManagementBusiness;
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute LeaveManagementBean leaveBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String viewName = null;
		String message = "";
		try {
			ErpAction.sessionChecks(request);

			session = request.getSession(true);
			leaveBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
			if (CPSUtils.isNullOrEmpty(leaveBean.getParam())) {
				leaveBean.setParam(CPSConstants.LEAVEMANAGEMENTHOME);

			} else if (CPSUtils.compareStrings(CPSConstants.PAGING, leaveBean.getParam())) {
				viewName = session.getAttribute(CPSConstants.RETURN).toString();
			}

			if (CPSUtils.compareStrings(CPSConstants.LEAVEMANAGEMENTHOME, leaveBean.getParam())) {
				leaveBean = leaveManagementBusiness.getLeaveTypeMaster(leaveBean);
				session.setAttribute(CPSConstants.LEAVETYPESLIST, leaveBean.getLeaveTypeList());
				leaveBean = leaveManagementBusiness.getExceptionMasterDetails(leaveBean);
				session.setAttribute(CPSConstants.LEAVEEXCEPTION, leaveBean.getExceptionMasterDetailsJSON());
				leaveBean = leaveManagementBusiness.getLeaveDurationMasterDetails(leaveBean);
				session.setAttribute(CPSConstants.LEAVEDURATIONLIST, leaveBean.getLeaveDurationList());
				viewName = CPSConstants.LEAVEMANAGEMENTMASTER;
			} else if (CPSUtils.compareStrings(CPSConstants.GETLEAVEDETAILS, leaveBean.getParam())) {
				if (CPSUtils.compareStrings(CPSConstants.LEAVETYPEIDVALUE1, leaveBean.getLeaveTypeId())) {
					viewName = CPSConstants.SPECIALCASUAL;
				} else {
					leaveBean = leaveManagementBusiness.getLeaveTypeDetails(leaveBean);
					viewName = CPSConstants.NORAMLLEAVE;
				}
			} else if (CPSUtils.compareStrings(CPSConstants.INSERTLEAVEGENERALDETAILS, leaveBean.getParam())) {
				message = leaveManagementBusiness.insertLeaveGeneralDetails(leaveBean);
				viewName = CPSConstants.RESULT;
			} else if (CPSUtils.compareStrings(CPSConstants.INSERTLEAVEOTHERDETAILS, leaveBean.getParam())) {
				message = leaveManagementBusiness.insertLeaveOtherDetails(leaveBean);
				viewName = CPSConstants.RESULT;
			} else if (CPSUtils.compareStrings(CPSConstants.INSERTLEAVERULESDETAILS, leaveBean.getParam())) {
				message = leaveManagementBusiness.insertLeaveRulesDetails(leaveBean);
				viewName = CPSConstants.RESULT;
			} else if (CPSUtils.compareStrings(CPSConstants.GETLEAVESUBTYPEDETAILSLIST, leaveBean.getParam())) {
				leaveBean.setLeaveSubTypeList(leaveManagementBusiness.getSpecialCasualLeaveTypes(leaveBean.getCategoryType()));
				if (CPSUtils.checkList(leaveBean.getLeaveSubTypeList())) {
					session.setAttribute(CPSConstants.LEAVESUBTYPESLIST, (JSONArray) JSONSerializer.toJSON(leaveBean.getLeaveSubTypeList()));
				}
				leaveBean = leaveManagementBusiness.clearBeanValues(leaveBean);
				viewName = CPSConstants.SPECIALCASUALDETAILS;
			} else if (CPSUtils.compareStrings(CPSConstants.GETLEAVESUBTYPEDETAILS, leaveBean.getParam())) {

				leaveBean = leaveManagementBusiness.getSpecialCasualLeaveDetails(leaveBean);
				leaveBean.setLeaveSubTypeList(leaveManagementBusiness.getSpecialCasualLeaveTypes(leaveBean.getCategoryType()));
				if (CPSUtils.checkList(leaveBean.getLeaveSubTypeList())) {
					session.setAttribute(CPSConstants.LEAVESUBTYPESLIST, (JSONArray) JSONSerializer.toJSON(leaveBean.getLeaveSubTypeList()));
				}
				viewName = CPSConstants.SPECIALCASUALDETAILS;
			} else if (CPSUtils.compareStrings(CPSConstants.INSERTSPECIALCASUALDETAILS, leaveBean.getParam())) {
				message = leaveManagementBusiness.insertSpecialCasualLeaveDetails(leaveBean);
				String spclid = "";
				if (CPSUtils.compareStrings(leaveBean.getCategoryType(), "T1")) {
					leaveManagementBusiness.getSpecialCasualID(leaveBean.getSpecialLeaveDesc());
				} else {
					leaveManagementBusiness.getSpecialCasualID1(leaveBean.getSpecialLeaveDesc(), leaveBean.getStrFromDate(), leaveBean.getStrToDate());
				}
				session.setAttribute(CPSConstants.SPCLID, spclid);
				viewName = CPSConstants.SPLRESULT;
			} else if (CPSUtils.compareStrings(CPSConstants.LEAVERELATIONMAPPINGHOME, leaveBean.getParam())) {
				if (CPSUtils.checkList(leaveManagementBusiness.getLeaveRelationMappingDetails())) {
					session.setAttribute(CPSConstants.LEAVERELATIONMAPPINGJSON, (JSONArray) JSONSerializer.toJSON(leaveManagementBusiness.getLeaveRelationMappingDetails()));
				}
				viewName = CPSConstants.LEAVERELATIONMAPPING;
			} else if (CPSUtils.compareStrings(CPSConstants.INSERTMAPPINGDETAILS, leaveBean.getParam())) {
				message = leaveManagementBusiness.insertLeaveRelationMappingDetails(leaveBean);
				viewName = CPSConstants.RESULT;
			}
			session.setAttribute(CPSConstants.RETURN, viewName);

			mav = new ModelAndView(viewName, CPSConstants.LEAVE, leaveBean);
			mav.addObject(CPSConstants.MESSAGE, message);
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}