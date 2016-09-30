package com.callippus.web.beans.transfer;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.callippus.web.beans.dto.DepartmentsDTO;
import com.callippus.web.beans.dto.DoPartDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.OrgInstanceDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.requests.DoPartBean;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.beans.transfer.dto.EmpTransferTxnDTO;

public class EmpTransferBean extends RequestBean {
	private String sfID;
	private int transferTypeID;
	private int departmentFromID;
	private String departmentTo;
	private String reason;
	private String attachment;
	private String hqRefNo;
	private DoPartBean doPartDetails;
	private DoPartDTO doPartDTO;
	private int status;
	private String requestedBy;
	private Date requestedDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String transferedSFID;
	private String transferType;
	private String otherLab;
	private String reqDepartment;
	private String departmentID;
	private String ipAddress;
	private EmployeeBean employeeDetails;
	private String departmentFrom;
	private String message;
	private String result;
	private String param;
	private String type;
	private List<DepartmentsDTO> departmentsList;
	private List<DepartmentsDTO> empWorkingDept;
	private List<KeyValueDTO> employeesList;
	private List<EmpTransferTxnDTO> empTransferTxnList;
	private List<OrgInstanceDTO> orgInstanceList;
	private List<DoPartDTO> doPartList;
	private List<String> empRoles;
	private String requestType;
	private MultipartFile attachmentFile;
	private String fromDept;
	private String toDept;
	private String assignedTo;
	private int doPartID;
	private String gazType;
	
	
	
	public List<DoPartDTO> getDoPartList() {
		return doPartList;
	}

	public void setDoPartList(List<DoPartDTO> doPartList) {
		this.doPartList = doPartList;
	}

	public String getGazType() {
		return gazType;
	}

	public void setGazType(String gazType) {
		this.gazType = gazType;
	}

	public List<OrgInstanceDTO> getOrgInstanceList() {
		return orgInstanceList;
	}

	public void setOrgInstanceList(List<OrgInstanceDTO> orgInstanceList) {
		this.orgInstanceList = orgInstanceList;
	}

	public List<EmpTransferTxnDTO> getEmpTransferTxnList() {
		return empTransferTxnList;
	}

	public void setEmpTransferTxnList(List<EmpTransferTxnDTO> empTransferTxnList) {
		this.empTransferTxnList = empTransferTxnList;
	}

	public String getFromDept() {
		return fromDept;
	}

	public void setFromDept(String fromDept) {
		this.fromDept = fromDept;
	}

	public String getToDept() {
		return toDept;
	}

	public void setToDept(String toDept) {
		this.toDept = toDept;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public int getDoPartID() {
		return doPartID;
	}

	public void setDoPartID(int doPartID) {
		this.doPartID = doPartID;
	}

	public DoPartDTO getDoPartDTO() {
		return doPartDTO;
	}

	public void setDoPartDTO(DoPartDTO doPartDTO) {
		this.doPartDTO = doPartDTO;
	}

	public MultipartFile getAttachmentFile() {
		return attachmentFile;
	}

	public void setAttachmentFile(MultipartFile attachmentFile) {
		this.attachmentFile = attachmentFile;
	}

	public String getDepartmentFrom() {
		return departmentFrom;
	}

	public void setDepartmentFrom(String departmentFrom) {
		this.departmentFrom = departmentFrom;
	}

	public EmployeeBean getEmployeeDetails() {
		return employeeDetails;
	}

	public void setEmployeeDetails(EmployeeBean employeeDetails) {
		this.employeeDetails = employeeDetails;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public List<String> getEmpRoles() {
		return empRoles;
	}

	public void setEmpRoles(List<String> empRoles) {
		this.empRoles = empRoles;
	}

	public List<KeyValueDTO> getEmployeesList() {
		return employeesList;
	}

	public void setEmployeesList(List<KeyValueDTO> employeesList) {
		this.employeesList = employeesList;
	}

	public String getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}

	public List<DepartmentsDTO> getEmpWorkingDept() {
		return empWorkingDept;
	}

	public void setEmpWorkingDept(List<DepartmentsDTO> empWorkingDept) {
		this.empWorkingDept = empWorkingDept;
	}

	public List<DepartmentsDTO> getDepartmentsList() {
		return departmentsList;
	}

	public void setDepartmentsList(List<DepartmentsDTO> departmentsList) {
		this.departmentsList = departmentsList;
	}

	public String getTransferedSFID() {
		return transferedSFID;
	}

	public void setTransferedSFID(String transferedSFID) {
		this.transferedSFID = transferedSFID;
	}

	public String getTransferType() {
		return transferType;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	public String getOtherLab() {
		return otherLab;
	}

	public void setOtherLab(String otherLab) {
		this.otherLab = otherLab;
	}

	public String getReqDepartment() {
		return reqDepartment;
	}

	public void setReqDepartment(String reqDepartment) {
		this.reqDepartment = reqDepartment;
	}

	public String getSfID() {
		return sfID;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
	}

	public int getTransferTypeID() {
		return transferTypeID;
	}

	public void setTransferTypeID(int transferTypeID) {
		this.transferTypeID = transferTypeID;
	}

	public int getDepartmentFromID() {
		return departmentFromID;
	}

	public void setDepartmentFromID(int departmentFromID) {
		this.departmentFromID = departmentFromID;
	}

	public String getDepartmentTo() {
		return departmentTo;
	}

	public void setDepartmentTo(String departmentTo) {
		this.departmentTo = departmentTo;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getHqRefNo() {
		return hqRefNo;
	}

	public void setHqRefNo(String hqRefNo) {
		this.hqRefNo = hqRefNo;
	}

	public DoPartBean getDoPartDetails() {
		return doPartDetails;
	}

	public void setDoPartDetails(DoPartBean doPartDetails) {
		this.doPartDetails = doPartDetails;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public Date getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
