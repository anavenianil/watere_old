package com.callippus.web.hindi.dto;

import com.callippus.web.beans.dto.CategoryDTO;
import com.callippus.web.beans.dto.DesignationDTO;

public class HindiExamConfigDTO {
	private String status;
	private int id;
	
	
	private String examName;
	private int examId;
	
	//private int mothertongueForExam;
	 private String mothertongue;
	 private float passMarks;	
	private int empCategory;
	private int empType;
	private int incrementApplicable;
	private int noOfIncrements;
	private int cashAwardApplicable;
	private String cashAwardAmount;
	private String selectDesignation;
	private String deselectDesignation;
	
	private String  empCategoryName;
	
	
	private ExamDTO examDetails;
	private CategoryDTO categoryDetails;
	private DesignationDTO designationDetails;
	
	public int getExamId() {
		return examId;
	}
	public void setExamId(int examId) {
		this.examId = examId;
	}
	public String getEmpCategoryName() {
		return empCategoryName;
	}
	public void setEmpCategoryName(String empCategoryName) {
		this.empCategoryName = empCategoryName;
	}
	public DesignationDTO getDesignationDetails() {
		return designationDetails;
	}
	public void setDesignationDetails(DesignationDTO designationDetails) {
		this.designationDetails = designationDetails;
	}
	public CategoryDTO getCategoryDetails() {
		return categoryDetails;
	}
	public void setCategoryDetails(CategoryDTO categoryDetails) {
		this.categoryDetails = categoryDetails;
	}
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
	
	
	public int getNoOfIncrements() {
		return noOfIncrements;
	}
	public void setNoOfIncrements(int noOfIncrements) {
		this.noOfIncrements = noOfIncrements;
	}
	
	
	public int getEmpCategory() {
		return empCategory;
	}
	public void setEmpCategory(int empCategory) {
		this.empCategory = empCategory;
	}
	public int getEmpType() {
		return empType;
	}
	public void setEmpType(int empType) {
		this.empType = empType;
	}
	public int getCashAwardApplicable() {
		return cashAwardApplicable;
	}
	public void setCashAwardApplicable(int cashAwardApplicable) {
		this.cashAwardApplicable = cashAwardApplicable;
	}
	public String getCashAwardAmount() {
		return cashAwardAmount;
	}
	public void setCashAwardAmount(String cashAwardAmount) {
		this.cashAwardAmount = cashAwardAmount;
	}
	public String getMothertongue() {
		return mothertongue;
	}
	public void setMothertongue(String mothertongue) {
		this.mothertongue = mothertongue;
	}
	public float getPassMarks() {
		return passMarks;
	}
	public void setPassMarks(float passMarks) {
		this.passMarks = passMarks;
	}
	public int getIncrementApplicable() {
		return incrementApplicable;
	}
	public void setIncrementApplicable(int incrementApplicable) {
		this.incrementApplicable = incrementApplicable;
	}
	public String getSelectDesignation() {
		return selectDesignation;
	}
	public void setSelectDesignation(String selectDesignation) {
		this.selectDesignation = selectDesignation;
	}
	public String getDeselectDesignation() {
		return deselectDesignation;
	}
	public void setDeselectDesignation(String deselectDesignation) {
		this.deselectDesignation = deselectDesignation;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

}
