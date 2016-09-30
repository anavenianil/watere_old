package com.callippus.web.business.tx.workflow;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.controller.common.CPSConstants;

@Service
public class TxOrgSpecificWorkflowProcess {
	private static Log log = LogFactory.getLog(TxDesigBasedWorkflowProcess.class);
	@Autowired
	private TxWorkflowProcess txWorkflowProcess;

	/**
	 * This method is for finding the organization specific workflow id,stage id,requester parent id for this request.
	 * 
	 * @param rb
	 * @return workflow id
	 */
	public RequestBean initWorkflow(RequestBean rb) throws Exception {
		log.debug("::<<<TxOrgSpecificWorkflowProcess<<<<<<<<Method>>>>>>>>>>>>>>>initWorkflow(RequestBean rb)>>>>>>>>>");
		try {
			rb.setWorkflowType(CPSConstants.ORGSPECIFIC);
			rb.setWorkflowID(txWorkflowProcess.decideWorkFlow(rb));
			rb.setStageID("1");
			rb = txWorkflowProcess.getWorkFlowAssignedSfid(rb, new RequestBean(),new ArrayList<RequestBean>());
		} catch (Exception e) {
			throw e;
		}
		return rb;
	}
}
