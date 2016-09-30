package com.callippus.web.hrdg.training.request.beans.dto;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.hrdg.training.beans.dto.CourseDTO;

public class TrainingNominationDTO extends CommonDTO{
	
	private Integer cirId;
	private String nomineeSfid;
	private String sfid;
	private Integer requestId;
	private String currentAssignment;
	private String relevance;
	private String lastAttendedCourse;
	private Integer offlineFlag;
	private String nominationDate;
	private CourseDTO courseDto;
	private HrdgTxnCancelRequestDTO hrdgTxnCancelReqDto;
	private HrdgTxnRequestDTO hrdgTxnReqDto;
	
	
	public HrdgTxnRequestDTO getHrdgTxnReqDto() {
		return hrdgTxnReqDto;
	}
	public void setHrdgTxnReqDto(HrdgTxnRequestDTO hrdgTxnReqDto) {
		this.hrdgTxnReqDto = hrdgTxnReqDto;
	}
	public HrdgTxnCancelRequestDTO getHrdgTxnCancelReqDto() {
		return hrdgTxnCancelReqDto;
	}
	public void setHrdgTxnCancelReqDto(HrdgTxnCancelRequestDTO hrdgTxnCancelReqDto) {
		this.hrdgTxnCancelReqDto = hrdgTxnCancelReqDto;
	}
	public CourseDTO getCourseDto() {
		return courseDto;
	}
	public void setCourseDto(CourseDTO courseDto) {
		this.courseDto = courseDto;
	}
	public String getNominationDate() {
		return nominationDate;
	}
	public void setNominationDate(String nominationDate) {
		this.nominationDate = nominationDate;
	}
	public Integer getOfflineFlag() {
		return offlineFlag;
	}
	public void setOfflineFlag(Integer offlineFlag) {
		this.offlineFlag = offlineFlag;
	}
	public Integer getCirId() {
		return cirId;
	}
	public void setCirId(Integer cirId) {
		this.cirId = cirId;
	}
	public String getNomineeSfid() {
		return nomineeSfid;
	}
	public void setNomineeSfid(String nomineeSfid) {
		this.nomineeSfid = nomineeSfid;
	}
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public Integer getRequestId() {
		return requestId;
	}
	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}
	public String getCurrentAssignment() {
		return currentAssignment;
	}
	public void setCurrentAssignment(String currentAssignment) {
		this.currentAssignment = currentAssignment;
	}
	public String getRelevance() {
		return relevance;
	}
	public void setRelevance(String relevance) {
		this.relevance = relevance;
	}
	public String getLastAttendedCourse() {
		return lastAttendedCourse;
	}
	public void setLastAttendedCourse(String lastAttendedCourse) {
		this.lastAttendedCourse = lastAttendedCourse;
	}
	
	

}
