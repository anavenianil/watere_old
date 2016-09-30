package com.callippus.web.beans.dto;

public class WorkFlowMasterDTO extends CommonDTO{
    private String workflowTypeId;
    private String toWorkFlow;
    
    
    public String getWorkflowTypeId() {
        return workflowTypeId;
    }
    public void setWorkflowTypeId(String workflowTypeId) {
        this.workflowTypeId = workflowTypeId;
    }
    public String getToWorkFlow() {
        return toWorkFlow;
    }
    public void setToWorkFlow(String toWorkFlow) {
        this.toWorkFlow = toWorkFlow;
    }

}
