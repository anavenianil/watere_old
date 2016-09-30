package com.callippus.web.business.workflow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.controller.common.CPSConstants;

@Service
public class GenericWorkflowProcess extends WorkflowProcess {
	private static Log log = LogFactory.getLog(GenericWorkflowProcess.class);
	@Autowired
	private WorkflowProcess workflowProcess;

	/**
	 * This method is for finding the generic workflow id,stage id,requester parent id for this request.
	 * 
	 * @param rb
	 * @return workflow id
	 */
	public RequestBean initWorkflow(RequestBean rb) throws Exception {
		log.debug("::<<<GenericWorkflowProcess<<<<<<<<Method>>>>>>>>>>>>>>>initWorkflow(RequestBean rb)>>>>>>>>>");
		try {
			rb.setWorkflowType(CPSConstants.GENERIC);
			rb.setWorkflowID(workflowProcess.decideWorkFlow(rb.getRequestTypeID(), null, null));
			rb.setStageID("1");
			rb = workflowProcess.getWorkFlowAssignedSfid(rb, new RequestBean());
		} catch (Exception e) {
			throw e;
		}
		return rb;
	}
}
