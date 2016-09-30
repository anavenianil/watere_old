package com.callippus.web.beans.dto;

public class WorkFlow {
    
    
    private String fromRole;
    private String toRole;
    private String relationshipType;
    private String escalationRelationshipType;
    
    private int workflow;
    private int stage;
    private int escalation;
    private int workflowType;
    private int workflowTo;
    
    
    public String getFromRole() {
        return fromRole;
    }
    public void setFromRole(String fromRole) {
        this.fromRole = fromRole;
    }
    public String getToRole() {
        return toRole;
    }
    public void setToRole(String toRole) {
        this.toRole = toRole;
    }
    public String getRelationshipType() {
        return relationshipType;
    }
    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }
    public String getEscalationRelationshipType() {
        return escalationRelationshipType;
    }
    public void setEscalationRelationshipType(String escalationRelationshipType) {
        this.escalationRelationshipType = escalationRelationshipType;
    }
    public int getWorkflow() {
        return workflow;
    }
    public void setWorkflow(int workflow) {
        this.workflow = workflow;
    }
    public int getStage() {
        return stage;
    }
    public void setStage(int stage) {
        this.stage = stage;
    }
    public int getEscalation() {
        return escalation;
    }
    public void setEscalation(int escalation) {
        this.escalation = escalation;
    }
    public int getWorkflowType() {
        return workflowType;
    }
    public void setWorkflowType(int workflowType) {
        this.workflowType = workflowType;
    }
    public int getWorkflowTo() {
        return workflowTo;
    }
    public void setWorkflowTo(int workflowTo) {
        this.workflowTo = workflowTo;
    }
   
    
    
   }
