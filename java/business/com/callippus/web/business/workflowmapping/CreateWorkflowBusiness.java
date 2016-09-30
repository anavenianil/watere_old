package com.callippus.web.business.workflowmapping;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.WorkFlowMasterDTO;
import com.callippus.web.beans.workflowmapping.WorkFlowMappingBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.workflowmapping.ICreateWorkflowDAO;

@Service
public class CreateWorkflowBusiness {
	@Autowired
	private ICreateWorkflowDAO createWorkflowDAO;
	@Autowired
	private IComonObjectDAO commonObjectDAO;

	@SuppressWarnings("unchecked")
	public WorkFlowMappingBean getWorkflowHomeDetails(WorkFlowMappingBean workflowBean) throws Exception {
		try {
			workflowBean.setWorkflowsList(commonObjectDAO.getMasterData(CPSConstants.WORKFLOWMASTERDTO));
		} catch (Exception e) {
			throw e;
		}
		return workflowBean;
	}

	@SuppressWarnings("unchecked")
	public WorkFlowMappingBean getStagesDetails(WorkFlowMappingBean workflowBean) throws Exception {
		try {
			if (!CPSUtils.checkList(workflowBean.getWorkflowStageList())) {
				workflowBean.setWorkflowsList(commonObjectDAO.getMasterData(CPSConstants.WORKFLOWMASTERDTO));
				workflowBean.setWorkflowStageList(commonObjectDAO.getMasterData(CPSConstants.WORKFLOWSTAGEMASTERDTO));
				workflowBean.setRelationList(commonObjectDAO.getMasterData(CPSConstants.WORKFLOWRELATIONDTO));
				workflowBean.setRelationShipList(commonObjectDAO.getMasterData(CPSConstants.WORKFLOWRELATIONSHIPDTO));
				workflowBean.setRoleInstanceList(commonObjectDAO.getMasterData(CPSConstants.ORGINSTANCEDTO));
				workflowBean.setWorkflowDependentList(commonObjectDAO.getMasterData(CPSConstants.WORKFLOWDEPENDENTDTO));
				workflowBean.setDynamicTableList(commonObjectDAO.getMasterData(CPSConstants.WORKFLOWDYNAMICTABLESDTO));
			}

			if (!CPSUtils.isNullOrEmpty(workflowBean.getNodeID())) {
				// Edit
				workflowBean = createWorkflowDAO.getWorkflowDetails(workflowBean);

			}
		} catch (Exception e) {
			throw e;
		}
		return workflowBean;
	}

	public WorkFlowMappingBean manageWorkflowStages(WorkFlowMappingBean workflowBean) throws Exception {
		try {
			if (CPSUtils.compareStrings(CPSConstants.NEW, workflowBean.getNodeID())) {
				// New
				workflowBean.setMessage(commonObjectDAO.checkDuplicate(CPSConstants.WORKFLOW_MASTER, CPSConstants.WORKFLOW_NAME, workflowBean.getFlowname(), null));
				if (CPSUtils.compareStrings(workflowBean.getMessage(), CPSConstants.SUCCESS)) {
					WorkFlowMasterDTO workflowMasterDTO = new WorkFlowMasterDTO();
					workflowMasterDTO.setName(workflowBean.getFlowname());
					workflowMasterDTO.setStatus(1);
					workflowMasterDTO.setToWorkFlow(workflowBean.getToworkflow());
					workflowMasterDTO.setCreationDate(CPSUtils.getCurrentDate());
					workflowMasterDTO.setLastModifiedDate(workflowMasterDTO.getCreationDate());
					workflowBean.setMessage(createWorkflowDAO.submitNewWorkflow(workflowMasterDTO));
				}
				// Get the node ID
				workflowBean.setNodeID(createWorkflowDAO.getWorkflowID(workflowBean.getFlowname()));
			} else {
				// Edit
				// First delete the stages
				workflowBean.setMessage(createWorkflowDAO.deleteWorkflowStages(workflowBean.getNodeID()));
			}

			// Insert Stages
			JSONObject mainjson = new JSONObject(workflowBean.getStages());
			for (int i = 0; i < mainjson.length(); i++) {
				JSONObject row = (JSONObject) mainjson.get(String.valueOf(i));

				WorkFlowMappingBean workflowStagesDTO = new WorkFlowMappingBean();
				workflowStagesDTO.setWorkflowId(workflowBean.getNodeID());
				workflowStagesDTO.setStageId(String.valueOf(i + 1));
				workflowStagesDTO.setFrom(row.getString("from"));
				workflowStagesDTO.setTo(row.getString("to"));
				workflowStagesDTO.setRelation(row.getString("relation"));
				workflowStagesDTO.setEsclateRelation(row.getString("erelation"));
				workflowStagesDTO.setEscalteTo(row.getString("eto"));
				workflowStagesDTO.setWorkflowStage(row.getString("stage"));

				workflowBean.setMessage(createWorkflowDAO.submitWorkflowStages(workflowStagesDTO));
			}
		} catch (Exception e) {
			throw e;
		}
		return workflowBean;
	}

	public WorkFlowMappingBean deleteWorkflow(WorkFlowMappingBean workflowBean) throws Exception {
		try {
			workflowBean = createWorkflowDAO.checkAssignedRequests(workflowBean);
			if (!CPSUtils.compareStrings(CPSConstants.FAILED, workflowBean.getMessage())) {
				// Delete
				workflowBean.setMessage(createWorkflowDAO.deleteWorkflow(workflowBean));
			}
		} catch (Exception e) {
			throw e;
		}
		return workflowBean;
	}
}
