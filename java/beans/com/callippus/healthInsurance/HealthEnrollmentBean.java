package com.callippus.healthInsurance;
import java.util.Date;
//import java.sql.Date;
import java.util.List;



	public class HealthEnrollmentBean
	{	

	private String sfid;
	private String fullName;
	private String designation;
	private Date joiningDate;
	private int status;
	private String jsonValue;
	private String pram;
	private String requestId;
	private String requestID;
	private String sfID;
	private String sfId;

	private Date fromDate;
	private Date toDate;
	private String type;
	private String param;
	private String message;
	
	private Date effctiveDate;
	private int subScriptiomAmt;

	private String nameOrSfid;
	private List<HealthInsuranceDTO> healthInsuranceDetails;

	private List<HealthEnrollmentDTO> healthEnrollmentDTO;

	private List<HealthEnrollmentBean> healthInsuranceList;
	
	private String hicName;
	private int hicNo;
		  
		
		public String getHicName() {
		return hicName;
	}
	public void setHicName(String hicName) {
		this.hicName = hicName;
	}
	public int getHicNo() {
		return hicNo;
	}
	public void setHicNo(int hicNo) {
		this.hicNo = hicNo;
	}
		public Date getEffctiveDate() {
		return effctiveDate;
	}
	public void setEffctiveDate(Date effctiveDate) {
		this.effctiveDate = effctiveDate;
	}
	public int getSubScriptiomAmt() {
		return subScriptiomAmt;
	}
	public void setSubScriptiomAmt(int subScriptiomAmt) {
		this.subScriptiomAmt = subScriptiomAmt;
	}
		public List<HealthEnrollmentDTO> getHealthEnrollmentDTO() {
			return healthEnrollmentDTO;
		}
		public void setHealthEnrollmentDTO(List<HealthEnrollmentDTO> healthEnrollmentDTO) {
			this.healthEnrollmentDTO = healthEnrollmentDTO;
		}
		public String getNameOrSfid() {
			return nameOrSfid;
		}
		public void setNameOrSfid(String nameOrSfid) {
			this.nameOrSfid = nameOrSfid;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
	
		
	

		public List<HealthEnrollmentBean> getHealthInsuranceList() {
			return healthInsuranceList;
		}
		public void setHealthInsuranceList(
				List<HealthEnrollmentBean> healthInsuranceList) {
			this.healthInsuranceList = healthInsuranceList;
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
	

		public Date getJoiningDate() {
			return joiningDate;
		}
		public void setJoiningDate(Date joiningDate) {
			this.joiningDate = joiningDate;
		}
		public Date getFromDate() {
			return fromDate;
		}
		public void setFromDate(Date fromDate) {
			this.fromDate = fromDate;
		}
		public Date getToDate() {
			return toDate;
		}
		public void setToDate(Date toDate) {
			this.toDate = toDate;
		}
		public String getPram() {
			return pram;
		}
		public void setPram(String pram) {
			this.pram = pram;
		}
		public String getRequestId() {
			return requestId;
		}
		public void setRequestId(String requestId) {
			this.requestId = requestId;
		}
		public String getRequestID() {
			return requestID;
		}
		public void setRequestID(String requestID) {
			this.requestID = requestID;
		}
		public String getSfID() {
			return sfID;
		}
		public void setSfID(String sfID) {
			this.sfID = sfID;
		}
		public String getSfId() {
			return sfId;
		}
		public void setSfId(String sfId) {
			this.sfId = sfId;
		}
		public String getJsonValue() {
			return jsonValue;
		}
		public void setJsonValue(String jsonValue) {
			this.jsonValue = jsonValue;
		}
		public String getSfid() {
			return sfid;
		}
		public void setSfid(String sfid) {
			this.sfid = sfid;
		}
		public String getFullName() {
			return fullName;
		}
		public void setFullName(String fullName) {
			this.fullName = fullName;
		}
		public String getDesignation() {
			return designation;
		}
		public void setDesignation(String designation) {
			this.designation = designation;
		}
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
	  


		public List<HealthInsuranceDTO> getHealthInsuranceDetails() {
			return healthInsuranceDetails;
		}
		public void setHealthInsuranceDetails(
				List<HealthInsuranceDTO> healthInsuranceDetails) {
			this.healthInsuranceDetails = healthInsuranceDetails;
		}
	    
	    
		
		
		

		    
		
	}



