package com.callippus.web.beans.reports;

import java.util.Date;
import java.util.List;

import com.callippus.web.beans.dto.CategoryDTO;
import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.GroupDTO;
import com.callippus.web.beans.dto.PHTypeDTO;
import com.callippus.web.beans.dto.QualificationDTO;
import com.callippus.web.beans.dto.ReligionDTO;

public class ReportsBean {
	private int reports;
	private String param;
	private String type;
	private String reportFormat;
	// private String Emp_category_Name;
	private String categoryNamesList;

	private String category;
	private String inventoryNo;
	private String groupName;
	private String religionName;
	private String reservation;
	private List list;
	private String community;
	private String designation;
	private String fromDate;
	private String toDate;
	private String hierarchy;
	private String value1;
	private String jasperFile;
	private String parameter1;
	private String parameter2;
	private String value2;
	private String value;
	private String pwd;
	private String jsonValue;
	private String reportName;
	private String organization;
	private List<DesignationDTO> designationList;
	private List<ReligionDTO> religionList;
	private List<CategoryDTO> categoryList;
	private List<PHTypeDTO> handicapList;
	private List<GroupDTO> groupList;
	private List<QualificationDTO> qualificationList;
	private String XSLPath;
	private String requestID;
	private String report;
	private String doPartId;
	private List divisionList;
	private String searchWith;
	private String sfid;
	private String name;
	private String dob;
	private String dojasl;
	private String directorate;
	private String division;
	private String ph;
	private String religion;
	private String qualification;
	private String cghs;
	private String hindi;
	private String quarter;
	private String xmlFile;
	private String exportedFile;
	private String requestType;
	private String sendingReportNumber;
	private String sendingReportDate;
	private String requesterSfid;
	
	private int assessmentCategoryID;
	private int yearID;
	private Date assessmentDate;
	private int assessmentTypeID;
	
	private String adminNo;
	private String adminDt;
	
	private String letterNo;
	private String quarterType;
	private String gazType;	
	
	private String mroId;
	private String billNo;

	private String requestTypeID;

	private int claimId;
	private String workFlowId;
	private String userSfid;

	private String vehicleId;
	private String year;
	private int vehicleTypeId;
	
	private String sfID;
	private String requestId;
	private Date departureDate;
	private Date arrivalDate;
	private String searchDate;
	
	
	private String letterDate;
    private String status;
    private String draft;
	
    
    
    private String addressType;
    
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	public String getRequesterSfid() {
		return requesterSfid;
	}
	public void setRequesterSfid(String requesterSfid) {
		this.requesterSfid = requesterSfid;
	}
	public int getVehicleTypeId() {
		return vehicleTypeId;
	}
	public void setVehicleTypeId(int vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}
	public String getDraft() {
		return draft;
	}
	public void setDraft(String draft) {
		this.draft = draft;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

	
	public String getLetterDate() {
		return letterDate;
	}
	public void setLetterDate(String letterDate) {
		this.letterDate = letterDate;
	}
	public String getSearchDate() {
		return searchDate;
	}
	public void setSearchDate(String searchDate) {
		this.searchDate = searchDate;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	public String getUserSfid() {
		return userSfid;
	}
	public void setUserSfid(String userSfid) {
		this.userSfid = userSfid;
	}
	public String getRequestTypeID() {
		return requestTypeID;
	}
	public void setRequestTypeID(String requestTypeID) {
		this.requestTypeID = requestTypeID;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getGazType() {
		return gazType;
	}
	public void setGazType(String gazType) {
		this.gazType = gazType;
	}
	public String getQuarterType() {
		return quarterType;
	}
	public void setQuarterType(String quarterType) {
		this.quarterType = quarterType;
	}
	public String getLetterNo() {
		return letterNo;
	}
	public void setLetterNo(String letterNo) {
		this.letterNo = letterNo;
	}
	public String getAdminNo() {
		return adminNo;
	}
	public void setAdminNo(String adminNo) {
		this.adminNo = adminNo;
	}
	public String getAdminDt() {
		return adminDt;
	}
	public void setAdminDt(String adminDt) {
		this.adminDt = adminDt;
	}
	public int getAssessmentTypeID() {
		return assessmentTypeID;
	}
	public void setAssessmentTypeID(int assessmentTypeID) {
		this.assessmentTypeID = assessmentTypeID;
	}
	public Date getAssessmentDate() {
		return assessmentDate;
	}
	public void setAssessmentDate(Date assessmentDate) {
		this.assessmentDate = assessmentDate;
	}

	public int getAssessmentCategoryID() {
		return assessmentCategoryID;
	}

	public void setAssessmentCategoryID(int assessmentCategoryID) {
		this.assessmentCategoryID = assessmentCategoryID;
	}

	public int getYearID() {
		return yearID;
	}

	public void setYearID(int yearID) {
		this.yearID = yearID;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getExportedFile() {
		return exportedFile;
	}

	public void setExportedFile(String exportedFile) {
		this.exportedFile = exportedFile;
	}

	public String getDoPartId() {
		return doPartId;
	}

	public void setDoPartId(String doPartId) {
		this.doPartId = doPartId;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public String getXSLPath() {
		return XSLPath;
	}

	public void setXSLPath(String xSLPath) {
		XSLPath = xSLPath;
	}

	public List<QualificationDTO> getQualificationList() {
		return qualificationList;
	}

	public void setQualificationList(List<QualificationDTO> qualificationList) {
		this.qualificationList = qualificationList;
	}

	public List<GroupDTO> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<GroupDTO> groupList) {
		this.groupList = groupList;
	}

	public List<DesignationDTO> getDesignationList() {
		return designationList;
	}

	public void setDesignationList(List<DesignationDTO> designationList) {
		this.designationList = designationList;
	}

	public List<ReligionDTO> getReligionList() {
		return religionList;
	}

	public void setReligionList(List<ReligionDTO> religionList) {
		this.religionList = religionList;
	}

	public List<CategoryDTO> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<CategoryDTO> categoryList) {
		this.categoryList = categoryList;
	}

	public List<PHTypeDTO> getHandicapList() {
		return handicapList;
	}

	public void setHandicapList(List<PHTypeDTO> handicapList) {
		this.handicapList = handicapList;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getJsonValue() {
		return jsonValue;
	}

	public void setJsonValue(String jsonValue) {
		this.jsonValue = jsonValue;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getXmlFile() {
		return xmlFile;
	}

	public void setXmlFile(String xmlFile) {
		this.xmlFile = xmlFile;
	}

	public String getSfid() {
		return sfid;
	}

	public void setSfid(String sfid) {
		this.sfid = sfid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getDojasl() {
		return dojasl;
	}

	public void setDojasl(String dojasl) {
		this.dojasl = dojasl;
	}

	public String getDirectorate() {
		return directorate;
	}

	public void setDirectorate(String directorate) {
		this.directorate = directorate;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getPh() {
		return ph;
	}

	public void setPh(String ph) {
		this.ph = ph;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getCghs() {
		return cghs;
	}

	public void setCghs(String cghs) {
		this.cghs = cghs;
	}

	public String getHindi() {
		return hindi;
	}

	public void setHindi(String hindi) {
		this.hindi = hindi;
	}

	public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

	public String getSearchWith() {
		return searchWith;
	}

	public void setSearchWith(String searchWith) {
		this.searchWith = searchWith;
	}

	public List getDivisionList() {
		return divisionList;
	}

	public void setDivisionList(List divisionList) {
		this.divisionList = divisionList;
	}

	public String getInventoryNo() {
		return inventoryNo;
	}

	public void setInventoryNo(String inventoryNo) {
		this.inventoryNo = inventoryNo;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getParameter1() {
		return parameter1;
	}

	public void setParameter1(String parameter1) {
		this.parameter1 = parameter1;
	}

	public String getParameter2() {
		return parameter2;
	}

	public void setParameter2(String parameter2) {
		this.parameter2 = parameter2;
	}

	public String getJasperFile() {
		return jasperFile;
	}

	public void setJasperFile(String jasperFile) {
		this.jasperFile = jasperFile;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(String hierarchy) {
		this.hierarchy = hierarchy;
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

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getReligionName() {
		return religionName;
	}

	public void setReligionName(String religionName) {
		this.religionName = religionName;
	}

	public String getReservation() {
		return reservation;
	}

	public void setReservation(String reservation) {
		this.reservation = reservation;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public String getCategoryNamesList() {
		return categoryNamesList;
	}

	public void setCategoryNamesList(String categoryNamesList) {
		this.categoryNamesList = categoryNamesList;
	}

	public String getReportFormat() {
		return reportFormat;
	}

	public void setReportFormat(String reportFormat) {
		this.reportFormat = reportFormat;
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

	public int getReports() {
		return reports;
	}

	public void setReports(int reports) {
		this.reports = reports;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setSendingReportNumber(String sendingReportNumber) {
		this.sendingReportNumber = sendingReportNumber;
	}

	public String getSendingReportNumber() {
		return sendingReportNumber;
	}

	public void setSendingReportDate(String sendingReportDate) {
		this.sendingReportDate = sendingReportDate;
	}

	public String getSendingReportDate() {
		return sendingReportDate;
	}
	public String getMroId() {
		return mroId;
	}
	public void setMroId(String mroId) {
		this.mroId = mroId;
	}
	public int getClaimId() {
		return claimId;
	}
	public void setClaimId(int claimId) {
		this.claimId = claimId;
	}
	public String getWorkFlowId() {
		return workFlowId;
	}
	public void setWorkFlowId(String workFlowId) {
		this.workFlowId = workFlowId;
	}
	public String getSfID() {
		return sfID;
	}
	public void setSfID(String sfID) {
		this.sfID = sfID;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public Date getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public void setAddresstype(String parameter) {
		// TODO Auto-generated method stub
		
	}
	

}
