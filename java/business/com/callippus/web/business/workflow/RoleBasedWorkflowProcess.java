package com.callippus.web.business.workflow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.controller.common.CPSConstants;

@Service
public class RoleBasedWorkflowProcess extends WorkflowProcess {
	private static Log log = LogFactory.getLog(RoleBasedWorkflowProcess.class);
	@Autowired
	private WorkflowProcess workflowProcess;

	/**
	 * This method is for finding the role based workflow id,stage id,requester parent id for this request.
	 * 
	 * @param rb
	 * @return workflow id
	 */
	public RequestBean initWorkflow(RequestBean rb) throws Exception {
		log.debug("::<<<RoleBasedWorkflowProcess<<<<<<<<Method>>>>>>>>>>>>>>>initWorkflow(RequestBean rb)>>>>>>>>>");
		try {
			rb.setWorkflowType(CPSConstants.ROLE);
			rb.setWorkflowID(workflowProcess.decideWorkFlow(rb.getRequestTypeID(), rb.getSfID(), rb.getOrgRoleID()));
			rb.setStageID("1");
			rb = workflowProcess.getWorkFlowAssignedSfid(rb, new RequestBean());
		} catch (Exception e) {
			throw e;
		}
		return rb;
	}
}