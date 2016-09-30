package com.callippus.web.hrdg.training.request.beans.dto;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.hrdg.file.UploadAndDownloadBean;

public class TrainingRequestBean {
	
	private String param;
	private String type;
	private String name;
	private String pastProgrammes;
	private String courseId;
		
	private int status;
	private String createdBy;
	private String creationDate;
	private String lastModifiedDate;
	private String lastModifiedBy;
	private String id;
		
	private String cirId;
	private String requestId;
	private String nomineeSfid;
	private String sfid;
	private String sfID;
	
	private String lastAttendedCourse;
	private String relevance;
	private String currentAssignment;
		
	private String requestID;
	private String requestType;
	private String requestTypeID;
	
	private String ipAddress;
	private HttpSession session;
	private EmployeeBean empDetails;
	private String nominationDate;
	
	private String historyID;
	private String stageID;
	
	private String workflowStageID;
	private String approvedDept;
	private String gazType;
	
	private String boardTypeId;
	private String boardType;
	private String boardId;
	private String board;
	
	private String jsonValues;
	private String message;
	private String statusMsg;
	private String back;
	private String durationId;
	private String selectStatus;
	private String startDate;
	private String endDate;
	
	private String brochure;
	private String brochureName;
	private String dept;
	private String fileId;
	
	private String course;
	private String cancelReason;
	private String refRequestId;
	private String requestStatus;
	private String nomRequestStatus;
	private JSONObject lastAttendedProgram;
	
	private String letterFormatId2;
	
	private String letterFormatId3;
	private String letterFormatId4;
	private String ionId4;
	private String letterFormatIdFinal;
	private String letterFormatId;
	
	private String ionId2;
	private String ionId3;
	private String ionIdFinal;
	private String ionId;
	private String ionFlag;
	private String feeFlag;
	private String yearBookType;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getYearBookType() {
		return yearBookType;
	}
	public void setYearBookType(String yearBookType) {
		this.yearBookType = yearBookType;
	}
	public String getFeeFlag() {
		return feeFlag;
	}
	public void setFeeFlag(String feeFlag) {
		this.feeFlag = feeFlag;
	}
	public String getIonFlag() {
		return ionFlag;
	}
	public void setIonFlag(String ionFlag) {
		this.ionFlag = ionFlag;
	}
	public JSONObject getLastAttendedProgram() {
		return lastAttendedProgram;
	}
	public void setLastAttendedProgram(JSONObject lastAttendedProgram) {
		this.lastAttendedProgram = lastAttendedProgram;
	}
	public String getNomRequestStatus() {
		return nomRequestStatus;
	}
	public void setNomRequestStatus(String nomRequestStatus) {
		this.nomRequestStatus = nomRequestStatus;
	}
	public String getRequestStatus() {
		return requestStatus;
	}
	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}
	public String getRefRequestId() {
		return refRequestId;
	}
	public void setRefRequestId(String refRequestId) {
		this.refRequestId = refRequestId;
	}
	public String getTypeValue() {
		return typeValue;
	}
	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTrainingTypeId() {
		return trainingTypeId;
	}
	public void setTrainingTypeId(String trainingTypeId) {
		this.trainingTypeId = trainingTypeId;
	}
	public String getCourseSubjCategoryId() {
		return courseSubjCategoryId;
	}
	public void setCourseSubjCategoryId(String courseSubjCategoryId) {
		this.courseSubjCategoryId = courseSubjCategoryId;
	}
	public String getCourseSubjSubCategoryId() {
		return courseSubjSubCategoryId;
	}
	public void setCourseSubjSubCategoryId(String courseSubjSubCategoryId) {
		this.courseSubjSubCategoryId = courseSubjSubCategoryId;
	}
	public String getTrainingInistituteId() {
		return trainingInistituteId;
	}
	public void setTrainingInistituteId(String trainingInistituteId) {
		this.trainingInistituteId = trainingInistituteId;
	}
	public String getTrainingType() {
		return trainingType;
	}
	public void setTrainingType(String trainingType) {
		this.trainingType = trainingType;
	}
	public String getCourseSubjCategory() {
		return courseSubjCategory;
	}
	public void setCourseSubjCategory(String courseSubjCategory) {
		this.courseSubjCategory = courseSubjCategory;
	}
	public String getCourseSubjSubCategory() {
		return courseSubjSubCategory;
	}
	public void setCourseSubjSubCategory(String courseSubjSubCategory) {
		this.courseSubjSubCategory = courseSubjSubCategory;
	}
	public String getTrainingInistitute() {
		return trainingInistitute;
	}
	public void setTrainingInistitute(String trainingInistitute) {
		this.trainingInistitute = trainingInistitute;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDdFlag() {
		return ddFlag;
	}
	public void setDdFlag(String ddFlag) {
		this.ddFlag = ddFlag;
	}
	public String getHeadOfficeFlag() {
		return headOfficeFlag;
	}
	public void setHeadOfficeFlag(String headOfficeFlag) {
		this.headOfficeFlag = headOfficeFlag;
	}
	public String getDdAddress() {
		return ddAddress;
	}
	public void setDdAddress(String ddAddress) {
		this.ddAddress = ddAddress;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getServiceTax() {
		return serviceTax;
	}
	public void setServiceTax(String serviceTax) {
		this.serviceTax = serviceTax;
	}
	public String getDesignations() {
		return designations;
	}
	public void setDesignations(String designations) {
		this.designations = designations;
	}
	public String getDisciplines() {
		return disciplines;
	}
	public void setDisciplines(String disciplines) {
		this.disciplines = disciplines;
	}
	public String getDiscipline() {
		return discipline;
	}
	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getPayableAt() {
		return payableAt;
	}
	public void setPayableAt(String payableAt) {
		this.payableAt = payableAt;
	}
	public String getCourseYear() {
		return courseYear;
	}
	public void setCourseYear(String courseYear) {
		this.courseYear = courseYear;
	}
	public String getMemberTypeId() {
		return memberTypeId;
	}
	public void setMemberTypeId(String memberTypeId) {
		this.memberTypeId = memberTypeId;
	}
	public String getYearId() {
		return yearId;
	}
	public void setYearId(String yearId) {
		this.yearId = yearId;
	}
	public String getCirculationDate() {
		return circulationDate;
	}
	public void setCirculationDate(String circulationDate) {
		this.circulationDate = circulationDate;
	}
	public String getVenueId() {
		return venueId;
	}
	public void setVenueId(String venueId) {
		this.venueId = venueId;
	}
	public String getNominationStartDate() {
		return nominationStartDate;
	}
	public void setNominationStartDate(String nominationStartDate) {
		this.nominationStartDate = nominationStartDate;
	}
	public String getNominationEndDate() {
		return nominationEndDate;
	}
	public void setNominationEndDate(String nominationEndDate) {
		this.nominationEndDate = nominationEndDate;
	}
	public String getOrganizer() {
		return organizer;
	}
	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}
	public String getBrochureFile() {
		return brochureFile;
	}
	public void setBrochureFile(String brochureFile) {
		this.brochureFile = brochureFile;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDepartments() {
		return departments;
	}
	public void setDepartments(String departments) {
		this.departments = departments;
	}
	public UploadAndDownloadBean getUploadBean() {
		return uploadBean;
	}
	public void setUploadBean(UploadAndDownloadBean uploadBean) {
		this.uploadBean = uploadBean;
	}
	private String typeValue;
	private String description;
	
	private String trainingTypeId;
	private String courseSubjCategoryId;
	private String courseSubjSubCategoryId;
	private String trainingInistituteId;
	private String trainingType;
	private String courseSubjCategory;
	private String courseSubjSubCategory;
	private String trainingInistitute;
	private String cityId;
	private String fax;
	private String email;
	private String address;
	private String ddFlag;
	private String headOfficeFlag;
	private String ddAddress;
	private String fee;
	private String serviceTax;
	private String designations;
	private String disciplines;
	private String discipline;
	private String shortName;
	private String payableAt;
	
	
	private String courseYear;
	
	private String memberTypeId;
	private String yearId;
	
	
	private String circulationDate;
	
	private String venueId;
	private String nominationStartDate;
	private String nominationEndDate;
	private String organizer;

	private String brochureFile;
	private String department;
	private String departments;
	
	private UploadAndDownloadBean uploadBean;


	
	
	
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public String getCurrentAssignment() {
		return currentAssignment;
	}
	public void setCurrentAssignment(String currentAssignment) {
		this.currentAssignment = currentAssignment;
	}
	public String getPastProgrammes() {
		return pastProgrammes;
	}
	public void setPastProgrammes(String pastProgrammes) {
		this.pastProgrammes = pastProgrammes;
	}
	public String getRelevance() {
		return relevance;
	}
	public void setRelevance(String relevance) {
		this.relevance = relevance;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
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
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCirId() {
		return cirId;
	}
	public void setCirId(String cirId) {
		this.cirId = cirId;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getNomineeSfid() {
		return nomineeSfid;
	}
	public void setNomineeSfid(String nomineeSfid) {
		this.nomineeSfid = nomineeSfid;
	}
	public String getLastAttendedCourse() {
		return lastAttendedCourse;
	}
	public void setLastAttendedCourse(String lastAttendedCourse) {
		this.lastAttendedCourse = lastAttendedCourse;
	}
	public String getRequestID() {
		return requestID;
	}
	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getRequestTypeID() {
		return requestTypeID;
	}
	public void setRequestTypeID(String requestTypeID) {
		this.requestTypeID = requestTypeID;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public HttpSession getSession() {
		return session;
	}
	public void setSession(HttpSession session) {
		this.session = session;
	}
	public EmployeeBean getEmpDetails() {
		return empDetails;
	}
	public void setEmpDetails(EmployeeBean empDetails) {
		this.empDetails = empDetails;
	}
	public String getNominationDate() {
		return nominationDate;
	}
	public void setNominationDate(String nominationDate) {
		this.nominationDate = nominationDate;
	}
	public String getHistoryID() {
		return historyID;
	}
	public void setHistoryID(String historyID) {
		this.historyID = historyID;
	}
	public String getWorkflowStageID() {
		return workflowStageID;
	}
	public void setWorkflowStageID(String workflowStageID) {
		this.workflowStageID = workflowStageID;
	}
	public String getApprovedDept() {
		return approvedDept;
	}
	public void setApprovedDept(String approvedDept) {
		this.approvedDept = approvedDept;
	}
	public String getGazType() {
		return gazType;
	}
	public void setGazType(String gazType) {
		this.gazType = gazType;
	}
	
	public String getBoardTypeId() {
		return boardTypeId;
	}
	public void setBoardTypeId(String boardTypeId) {
		this.boardTypeId = boardTypeId;
	}
	public String getBoardType() {
		return boardType;
	}
	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public String getBoard() {
		return board;
	}
	public void setBoard(String board) {
		this.board = board;
	}
	public String getJsonValues() {
		return jsonValues;
	}
	public void setJsonValues(String jsonValues) {
		this.jsonValues = jsonValues;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	public String getBack() {
		return back;
	}
	public void setBack(String back) {
		this.back = back;
	}
	public String getSfID() {
		return sfID;
	}
	public void setSfID(String sfID) {
		this.sfID = sfID;
	}

	public String getSelectStatus() {
		return selectStatus;
	}
	public void setSelectStatus(String selectStatus) {
		this.selectStatus = selectStatus;
	}
	public String getDurationId() {
		return durationId;
	}
	public void setDurationId(String durationId) {
		this.durationId = durationId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getBrochure() {
		return brochure;
	}
	public void setBrochure(String brochure) {
		this.brochure = brochure;
	}
	public String getBrochureName() {
		return brochureName;
	}
	public void setBrochureName(String brochureName) {
		this.brochureName = brochureName;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getStageID() {
		return stageID;
	}
	public void setStageID(String stageID) {
		this.stageID = stageID;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public String getLetterFormatId2() {
		return letterFormatId2;
	}
	public void setLetterFormatId2(String letterFormatId2) {
		this.letterFormatId2 = letterFormatId2;
	}
	public String getLetterFormatId3() {
		return letterFormatId3;
	}
	public void setLetterFormatId3(String letterFormatId3) {
		this.letterFormatId3 = letterFormatId3;
	}
	public String getLetterFormatIdFinal() {
		return letterFormatIdFinal;
	}
	public void setLetterFormatIdFinal(String letterFormatIdFinal) {
		this.letterFormatIdFinal = letterFormatIdFinal;
	}
	public String getLetterFormatId() {
		return letterFormatId;
	}
	public void setLetterFormatId(String letterFormatId) {
		this.letterFormatId = letterFormatId;
	}
	public String getIonId2() {
		return ionId2;
	}
	public void setIonId2(String ionId2) {
		this.ionId2 = ionId2;
	}
	public String getIonId3() {
		return ionId3;
	}
	public void setIonId3(String ionId3) {
		this.ionId3 = ionId3;
	}
	public String getIonIdFinal() {
		return ionIdFinal;
	}
	public void setIonIdFinal(String ionIdFinal) {
		this.ionIdFinal = ionIdFinal;
	}
	public String getIonId() {
		return ionId;
	}
	public void setIonId(String ionId) {
		this.ionId = ionId;
	}
	public String getLetterFormatId4() {
		return letterFormatId4;
	}
	public void setLetterFormatId4(String letterFormatId4) {
		this.letterFormatId4 = letterFormatId4;
	}
	public String getIonId4() {
		return ionId4;
	}
	public void setIonId4(String ionId4) {
		this.ionId4 = ionId4;
	}
}
