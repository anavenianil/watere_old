package com.callippus.web.controller.workflowmapping;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.callippus.web.beans.dto.WorkflowStageMasterDTO;
import com.callippus.web.beans.workflowmapping.WorkFlowMappingBean;
import com.callippus.web.business.workflowmapping.CreateWorkflowBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@RequestMapping("/createworkflow.htm")
@SessionAttributes
public class CreateWorkflowController {
	private static Log log = LogFactory.getLog(CreateWorkflowController.class);
	@Autowired
	private CreateWorkflowBusiness createWorkflowBusiness;

	@SuppressWarnings("unchecked")
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.WORKFLOWMAP) WorkFlowMappingBean workBeanMap, BindingResult result, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String viewName = "";
		try {
			ErpAction.sessionChecks(request);
			session = request.getSession(true);
			if (CPSUtils.isNullOrEmpty(workBeanMap.getParam())) {
				workBeanMap.setParam(CPSConstants.HOME);
			}

			if (CPSUtils.compareStrings(CPSConstants.HOME, workBeanMap.getParam())) {
				log.debug("::CreateWorkflowController --> onSubmit --> param=home");
				workBeanMap = createWorkflowBusiness.getWorkflowHomeDetails(workBeanMap);
				viewName = CPSConstants.CREATEWORKFLOW;
			} else if (CPSUtils.compareStrings(CPSConstants.STAGES, workBeanMap.getParam())) {
				log.debug("::CreateWorkflowController --> onSubmit --> param=stages");
				workBeanMap.setWorkflowStageList((List<WorkflowStageMasterDTO>) session.getAttribute(CPSConstants.WORKFLOWSTAGESLIST));
				workBeanMap = createWorkflowBusiness.getStagesDetails(workBeanMap);

				if (CPSUtils.checkList(workBeanMap.getRelationList())) {
					session.setAttribute(CPSConstants.WORKFLOWSTAGESLIST, workBeanMap.getWorkflowStageList());
					session.setAttribute(CPSConstants.WORKFLOWLIST, workBeanMap.getWorkflowsList());
					session.setAttribute(CPSConstants.RELATIONLIST, workBeanMap.getRelationList());
					session.setAttribute(CPSConstants.RELATIONSHIPLIST, workBeanMap.getRelationShipList());
					session.setAttribute(CPSConstants.ROLEINSTANCELIST, workBeanMap.getRoleInstanceList());
					session.setAttribute(CPSConstants.WORKFLOWDEPENDENTLIST, workBeanMap.getWorkflowDependentList());
					session.setAttribute(CPSConstants.DYNAMICTABLESLIST, workBeanMap.getDynamicTableList());
				}
				if (CPSUtils.checkList(workBeanMap.getWorkflowDetails())) {
					session.setAttribute(CPSConstants.WORKFLOWDETAILS, workBeanMap.getWorkflowDetails());
				}
				viewName = CPSConstants.CREATEWORKFLOWSTAGES;
			} else if (CPSUtils.compareStrings(CPSConstants.MANAGE, workBeanMap.getParam())) {
				log.debug("::CreateWorkflowController --> onSubmit --> param=manage");
				workBeanMap = createWorkflowBusiness.manageWorkflowStages(workBeanMap);
				workBeanMap.setType(workBeanMap.getNodeID());
				viewName = CPSConstants.WORKFLOWRESULT;
			} else if (CPSUtils.compareStrings(CPSConstants.DELETE, workBeanMap.getParam())) {
				log.debug("::CreateWorkflowController --> onSubmit --> param=delete");
				workBeanMap = createWorkflowBusiness.deleteWorkflow(workBeanMap);
				viewName = CPSConstants.WORKFLOWRESULT;
			}

			mav = new ModelAndView(viewName, CPSConstants.WORKFLOWMAP, workBeanMap);
			if (!CPSUtils.isNullOrEmpty(workBeanMap.getResult())) {
				mav.addObject(CPSConstants.RESULT, workBeanMap.getResult());
			}
			if (!CPSUtils.isNullOrEmpty(workBeanMap.getMessage())) {
				mav.addObject(CPSConstants.MESSAGE, workBeanMap.getMessage());
			}
			if (!CPSUtils.isNullOrEmpty(workBeanMap.getType())) {
				mav.addObject(CPSConstants.TYPE, workBeanMap.getType());
			}
			if (!CPSUtils.isNullOrEmpty(workBeanMap.getRemarks())) {
				mav.addObject(CPSConstants.REMARKS, workBeanMap.getRemarks());
			}
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}

		return mav;
	}
}
