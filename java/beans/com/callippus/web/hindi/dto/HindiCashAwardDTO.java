package com.callippus.web.hindi.dto;

public class HindiCashAwardDTO {
	private String status;
	private int examId;
	private int id;
	
	private String examName;
	private float lowerPercentage;
	private float upperPercentage;
	private String cashAwardAmount;
	
	
	private ExamDTO examDetails;
	
	public ExamDTO getExamDetails() {
		return examDetails;
	}
	public void setExamDetails(ExamDTO examDetails) {
		this.examDetails = examDetails;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCashAwardAmount() {
		return cashAwardAmount;
	}
	public void setCashAwardAmount(String cashAwardAmount) {
		this.cashAwardAmount = cashAwardAmount;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	
	public float getLowerPercentage() {
		return lowerPercentage;
	}
	public void setLowerPercentage(float lowerPercentage) {
		this.lowerPercentage = lowerPercentage;
	}
	public float getUpperPercentage() {
		return upperPercentage;
	}
	public void setUpperPercentage(float upperPercentage) {
		this.upperPercentage = upperPercentage;
	}
	public int getExamId() {
		return examId;
	}
	public void setExamId(int examId) {
		this.examId = examId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
