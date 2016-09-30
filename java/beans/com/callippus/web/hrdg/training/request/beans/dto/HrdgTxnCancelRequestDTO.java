package com.callippus.web.hrdg.training.request.beans.dto;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.hrdg.training.beans.dto.CourseDTO;
import com.callippus.web.hrdg.training.beans.dto.CourseDurationsDTO;
import com.callippus.web.hrdg.training.beans.dto.TrainingCirculationDTO;

public class HrdgTxnCancelRequestDTO extends CommonDTO{
	
	private Integer requestId;
	private Integer txnType;
	private Integer requestTypeId;
	private Integer requestStatus;
	private Integer refRequestId;
	private String ipAddress;
	private String cancelReason;
	
	private CourseDTO courseDto;
	private CourseDurationsDTO courseDurationDto;  
	
	private TrainingCirculationDTO trainingCirculationDto;
	private TrainingNominationDTO trainingNominationDto;
	private EmployeeBean empBean;
	private String historyID;
	private String stageID;
	private String requestType;
	
	
	
	
	
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getStageID() {
		return stageID;
	}
	public void setStageID(String stageID) {
		this.stageID = stageID;
	}
	public String getHistoryID() {
		return historyID;
	}
	public void setHistoryID(String historyID) {
		this.historyID = historyID;
	}
	public EmployeeBean getEmpBean() {
		return empBean;
	}
	public void setEmpBean(EmployeeBean empBean) {
		this.empBean = empBean;
	}
	public TrainingNominationDTO getTrainingNominationDto() {
		return trainingNominationDto;
	}
	public void setTrainingNominationDto(TrainingNominationDTO trainingNominationDto) {
		this.trainingNominationDto = trainingNominationDto;
	}
	public Integer getRequestId() {
		return requestId;
	}
	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}
	public Integer getTxnType() {
		return txnType;
	}
	public void setTxnType(Integer txnType) {
		this.txnType = txnType;
	}
	public Integer getRequestTypeId() {
		return requestTypeId;
	}
	public void setRequestTypeId(Integer requestTypeId) {
		this.requestTypeId = requestTypeId;
	}
	public Integer getRequestStatus() {
		return requestStatus;
	}
	public void setRequestStatus(Integer requestStatus) {
		this.requestStatus = requestStatus;
	}
	public CourseDTO getCourseDto() {
		return courseDto;
	}
	public void setCourseDto(CourseDTO courseDto) {
		this.courseDto = courseDto;
	}
	public TrainingCirculationDTO getTrainingCirculationDto() {
		return trainingCirculationDto;
	}
	public void setTrainingCirculationDto(TrainingCirculationDTO trainingCirculationDto) {
		this.trainingCirculationDto = trainingCirculationDto;
	}
	
	public CourseDurationsDTO getCourseDurationDto() {
		return courseDurationDto;
	}
	public void setCourseDurationDto(CourseDurationsDTO courseDurationDto) {
		this.courseDurationDto = courseDurationDto;
	}

	public Integer getRefRequestId() {
		return refRequestId;
	}
	public void setRefRequestId(Integer refRequestId) {
		this.refRequestId = refRequestId;
	}

	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	
	
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

}
