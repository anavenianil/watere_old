package com.callippus.web.beans.requests;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;

public class DoPartBean extends CommonDTO {
	private String doPartNo;
	private String doPartDate;
	private Date formatDoPartDate;
	private String sfID;
	private String leaveType;
	private String fromDate;
	private String toDate;
	private String empName;
	private String designationName;
	private String gazettedType;
	private String type;
	private String refType;
	private String doPartNumber;
	private Date doPartDate1;
	private int incrementsDoPartId;
	private Integer fixationDoPartId;
	
	
	
	
	
	public int getIncrementsDoPartId() {
		return incrementsDoPartId;
	}

	public void setIncrementsDoPartId(int incrementsDoPartId) {
		this.incrementsDoPartId = incrementsDoPartId;
	}

	public Integer getFixationDoPartId() {
		return fixationDoPartId;
	}

	public void setFixationDoPartId(Integer fixationDoPartId) {
		this.fixationDoPartId = fixationDoPartId;
	}

	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGazettedType() {
		return gazettedType;
	}

	public void setGazettedType(String gazettedType) {
		this.gazettedType = gazettedType;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public String getSfID() {
		return sfID;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Date getFormatDoPartDate() {
		return formatDoPartDate;
	}

	public void setFormatDoPartDate(Date formatDoPartDate) {
		this.formatDoPartDate = formatDoPartDate;
	}

	public String getDoPartNo() {
		return doPartNo;
	}

	public void setDoPartNo(String doPartNo) {
		this.doPartNo = doPartNo;
	}

	public String getDoPartDate() {
		return doPartDate;
	}

	public void setDoPartDate(String doPartDate) {
		this.doPartDate = doPartDate;
	}

	public void setDoPartNumber(String doPartNumber) {
		this.doPartNumber = doPartNumber;
	}

	public String getDoPartNumber() {
		return doPartNumber;
	}

	public Date getDoPartDate1() {
		return doPartDate1;
	}

	public void setDoPartDate1(Date doPartDate1) {
		this.doPartDate1 = doPartDate1;
	}

}
