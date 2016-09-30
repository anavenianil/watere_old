package com.callippus.web.hindi.dto;

import java.util.Date;

public class HindiEmpResultDTO {

	private int id;
	private String status;
	private String sfid;
	private String name;
	//private String examName;
	private int examId;	
	private float marksPercentage;	
	private int totalMarks;
	private ExamDTO examDetails;
	
	private String cashAwardAmount;
	private Date examDate;
	private Date resultDate;
	private String passOrFail;
	private Date effectiveDate;
	
	
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassOrFail() {
		return passOrFail;
	}
	public void setPassOrFail(String passOrFail) {
		this.passOrFail = passOrFail;
	}
	public Date getExamDate() {
		return examDate;
	}
	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}
	public Date getResultDate() {
		return resultDate;
	}
	public void setResultDate(Date resultDate) {
		this.resultDate = resultDate;
	}
	public String getCashAwardAmount() {
		return cashAwardAmount;
	}
	public void setCashAwardAmount(String cashAwardAmount) {
		this.cashAwardAmount = cashAwardAmount;
	}
	public int getTotalMarks() {
		return totalMarks;
	}
	public void setTotalMarks(int totalMarks) {
		this.totalMarks = totalMarks;
	}
	public int getExamId() {
		return examId;
	}
	public void setExamId(int examId) {
		this.examId = examId;
	}
	public ExamDTO getExamDetails() {
		return examDetails;
	}
	public void setExamDetails(ExamDTO examDetails) {
		this.examDetails = examDetails;
	}
	public int getId() {
		return id;
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
	
	public float getMarksPercentage() {
		return marksPercentage;
	}
	public void setMarksPercentage(float marksPercentage) {
		this.marksPercentage = marksPercentage;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
