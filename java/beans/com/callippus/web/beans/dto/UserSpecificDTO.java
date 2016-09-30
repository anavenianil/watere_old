package com.callippus.web.beans.dto;

public class UserSpecificDTO {

	private int id;
	private String sfid;
	private int roleMapId;
	private int requestTypeId;
	private String gazettedType;
	private String delegate;
	private int status;
	private int delegateType;
	private int noOfRequests;
	private int assignedType;
	private String designation;
	private String requestName;
	private String roleName;
	private int designationID;
	private String assigned;
	private String delegateUser;
	private String uniqueID;
	private String requestTypeIdValue; //newly added
	private String requesterType; //newly added
	

	private String getRequesterType() {
		return requesterType;
	}

	private void setRequesterType(String requesterType) {
		this.requesterType = requesterType;
	}

	public String getUniqueID() {
		return uniqueID;
	}

	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}

	public String getDelegateUser() {
		return delegateUser;
	}

	public void setDelegateUser(String delegateUser) {
		this.delegateUser = delegateUser;
	}

	public String getRequestName() {
		return requestName;
	}

	public void setRequestName(String requestName) {
		this.requestName = requestName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getDesignationID() {
		return designationID;
	}

	public void setDesignationID(int designationID) {
		this.designationID = designationID;
	}

	public String getAssigned() {
		return assigned;
	}

	public void setAssigned(String assigned) {
		this.assigned = assigned;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public int getAssignedType() {
		return assignedType;
	}

	public void setAssignedType(int assignedType) {
		this.assignedType = assignedType;
	}

	public int getId() {
		return id;
	}

	public int getNoOfRequests() {
		return noOfRequests;
	}

	public void setNoOfRequests(int noOfRequests) {
		this.noOfRequests = noOfRequests;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSfid() {
		return sfid;
	}

	public void setSfid(String sfid) {
		this.sfid = sfid;
	}

	public int getRoleMapId() {
		return roleMapId;
	}

	public void setRoleMapId(int roleMapId) {
		this.roleMapId = roleMapId;
	}

	public int getRequestTypeId() {
		return requestTypeId;
	}

	public void setRequestTypeId(int requestTypeId) {
		this.requestTypeId = requestTypeId;
	}

	public String getGazettedType() {
		return gazettedType;
	}

	public void setGazettedType(String gazettedType) {
		this.gazettedType = gazettedType;
	}

	public String getDelegate() {
		return delegate;
	}

	public void setDelegate(String delegate) {
		this.delegate = delegate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDelegateType() {
		return delegateType;
	}

	public void setDelegateType(int delegateType) {
		this.delegateType = delegateType;
	}

	public String getRequestTypeIdValue() {
		return requestTypeIdValue;
	}

	public void setRequestTypeIdValue(String requestTypeIdValue) {
		this.requestTypeIdValue = requestTypeIdValue;
	}

}
