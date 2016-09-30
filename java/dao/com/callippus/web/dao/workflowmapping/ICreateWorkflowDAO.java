package com.callippus.web.dao.workflowmapping;

import com.callippus.web.beans.dto.WorkFlowMasterDTO;
import com.callippus.web.beans.workflowmapping.WorkFlowMappingBean;

public interface ICreateWorkflowDAO {

	public WorkFlowMappingBean getWorkflowDetails(WorkFlowMappingBean workflowMapBean) throws Exception;

	public String submitNewWorkflow(WorkFlowMasterDTO workflowMasterDTO) throws Exception;

	public String deleteWorkflowStages(String nodeID) throws Exception;

	public String getWorkflowID(String workflowName) throws Exception;

	public String submitWorkflowStages(WorkFlowMappingBean workflowStagesDTO) throws Exception;

	public WorkFlowMappingBean checkAssignedRequests(WorkFlowMappingBean workflowStagesDTO) throws Exception;

	public String deleteWorkflow(WorkFlowMappingBean workflowStagesDTO) throws Exception;
}
