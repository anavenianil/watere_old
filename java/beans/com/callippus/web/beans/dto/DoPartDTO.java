package com.callippus.web.beans.dto;

import java.util.Date;

public class DoPartDTO {
	private int id;
	private String doPartNumber;
	private Date doPartDate;
	private String requestTypeID;
	private int status;
	private String createdBy;
	private Date creationDate;
	private String preDoPartNo;
	private Date preDoPartDate;
	private String preDOPartDate;
	private String dOPartDate;
	private String description;
	private String distribution;
	private String gazType;
	private String year;
	private String doPartType;
	private String verifyFlag;
	private String releasedBy;
	private Date releasedDate;
	private String acceptedBy;
	private Date acceptedDate;
	private String DoDate;
	private int typeId;
	private int count;
	private String name;
	private String month;
	//added by rakesh
	private String leaveCancelDopartno;
	private Date	leaveCancelDopartDate;
	private String leaveConversionDopartno;
	private Date leaveConversionDopartDate;
	
	public String getLeaveConversionDopartno() {
		return leaveConversionDopartno;
	}

	public void setLeaveConversionDopartno(String leaveConversionDopartno) {
		this.leaveConversionDopartno = leaveConversionDopartno;
	}

	public Date getLeaveConversionDopartDate() {
		return leaveConversionDopartDate;
	}

	public void setLeaveConversionDopartDate(Date leaveConversionDopartDate) {
		this.leaveConversionDopartDate = leaveConversionDopartDate;
	}

	public String getLeaveCancelDopartno() {
		return leaveCancelDopartno;
	}

	public void setLeaveCancelDopartno(String leaveCancelDopartno) {
		this.leaveCancelDopartno = leaveCancelDopartno;
	}

	public Date getLeaveCancelDopartDate() {
		return leaveCancelDopartDate;
	}

	public void setLeaveCancelDopartDate(Date leaveCancelDopartDate) {
		this.leaveCancelDopartDate = leaveCancelDopartDate;
	}

	public String getDistribution() {
		return distribution;
	}

	public void setDistribution(String distribution) {
		this.distribution = distribution;
	}
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getDoDate() {
		return DoDate;
	}

	public void setDoDate(String doDate) {
		DoDate = doDate;
	}

	public Date getReleasedDate() {
		return releasedDate;
	}

	public void setReleasedDate(Date releasedDate) {
		this.releasedDate = releasedDate;
	}

	public Date getAcceptedDate() {
		return acceptedDate;
	}

	public void setAcceptedDate(Date acceptedDate) {
		this.acceptedDate = acceptedDate;
	}

	public String getReleasedBy() {
		return releasedBy;
	}

	public void setReleasedBy(String releasedBy) {
		this.releasedBy = releasedBy;
	}


	public String getAcceptedBy() {
		return acceptedBy;
	}

	public void setAcceptedBy(String acceptedBy) {
		this.acceptedBy = acceptedBy;
	}


	public String getVerifyFlag() {
		return verifyFlag;
	}

	public void setVerifyFlag(String verifyFlag) {
		this.verifyFlag = verifyFlag;
	}

	public String getDoPartType() {
		return doPartType;
	}

	public void setDoPartType(String doPartType) {
		this.doPartType = doPartType;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getPreDoPartNo() {
		return preDoPartNo;
	}

	public void setPreDoPartNo(String preDoPartNo) {
		this.preDoPartNo = preDoPartNo;
	}

	public Date getPreDoPartDate() {
		return preDoPartDate;
	}

	public void setPreDoPartDate(Date preDoPartDate) {
		this.preDoPartDate = preDoPartDate;
	}

	public String getPreDOPartDate() {
		return preDOPartDate;
	}

	public void setPreDOPartDate(String preDOPartDate) {
		this.preDOPartDate = preDOPartDate;
	}

	public String getGazType() {
		return gazType;
	}

	public void setGazType(String gazType) {
		this.gazType = gazType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    public String getdOPartDate() {
		return dOPartDate;
	}

	public void setdOPartDate(String dOPartDate) {
		this.dOPartDate = dOPartDate;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDoPartNumber() {
		return doPartNumber;
	}

	public void setDoPartNumber(String doPartNumber) {
		this.doPartNumber = doPartNumber;
	}

	public Date getDoPartDate() {
		return doPartDate;
	}

	public void setDoPartDate(Date doPartDate) {
		this.doPartDate = doPartDate;
	}

	public String getRequestTypeID() {
		return requestTypeID;
	}

	public void setRequestTypeID(String requestTypeID) {
		this.requestTypeID = requestTypeID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
