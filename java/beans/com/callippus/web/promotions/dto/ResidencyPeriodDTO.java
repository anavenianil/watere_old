package com.callippus.web.promotions.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CategoryDTO;
import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.beans.dto.DesignationDTO;

public class ResidencyPeriodDTO extends CommonDTO {

	private AssessmentCategoryDTO assessmentCategoryDetails;
	private CategoryDTO assessmentTypeDetails;
	private int assessmentTypeID;
	private int assessmentCategoryID;
	private DesignationDTO designationFromDetails;
	private int designationFrom;
	private DesignationDTO designationToDetails;
	private int designationTo;
	private int residencyPeriod;
	private int relaxationInMonths;
	private String dateFrom;
	private String dateTo;
	//
	private Integer written;
	private Integer trade;
	private Integer interview;
	private Integer board;
	
	

	
    public Integer getBoard() {
		return board;
	}

	public void setBoard(Integer board) {
		this.board = board;
	} 
	public Integer getWritten() {
		return written;
	}

	public void setWritten(Integer written) {
		this.written = written;
	}
	

	public Integer getTrade() {
		return trade;
	}

	public void setTrade(Integer trade) {
		this.trade = trade;
	}

	public Integer getInterview() {
		return interview;
	}

	public void setInterview(Integer interview) {
		this.interview = interview;
	}
    //
	public AssessmentCategoryDTO getAssessmentCategoryDetails() {
		return assessmentCategoryDetails;
	}

	public void setAssessmentCategoryDetails(AssessmentCategoryDTO assessmentCategoryDetails) {
		this.assessmentCategoryDetails = assessmentCategoryDetails;
	}


	public CategoryDTO getAssessmentTypeDetails() {
		return assessmentTypeDetails;
	}

	public void setAssessmentTypeDetails(CategoryDTO assessmentTypeDetails) {
		this.assessmentTypeDetails = assessmentTypeDetails;
	}

	public int getAssessmentTypeID() {
		return assessmentTypeID;
	}

	public void setAssessmentTypeID(int assessmentTypeID) {
		this.assessmentTypeID = assessmentTypeID;
	}

	public int getAssessmentCategoryID() {
		return assessmentCategoryID;
	}

	public void setAssessmentCategoryID(int assessmentCategoryID) {
		this.assessmentCategoryID = assessmentCategoryID;
	}

	public DesignationDTO getDesignationFromDetails() {
		return designationFromDetails;
	}

	public void setDesignationFromDetails(DesignationDTO designationFromDetails) {
		this.designationFromDetails = designationFromDetails;
	}

	public int getDesignationFrom() {
		return designationFrom;
	}

	public void setDesignationFrom(int designationFrom) {
		this.designationFrom = designationFrom;
	}

	public DesignationDTO getDesignationToDetails() {
		return designationToDetails;
	}

	public void setDesignationToDetails(DesignationDTO designationToDetails) {
		this.designationToDetails = designationToDetails;
	}

	public int getDesignationTo() {
		return designationTo;
	}

	public void setDesignationTo(int designationTo) {
		this.designationTo = designationTo;
	}

	public int getResidencyPeriod() {
		return residencyPeriod;
	}

	public void setResidencyPeriod(int residencyPeriod) {
		this.residencyPeriod = residencyPeriod;
	}

	public int getRelaxationInMonths() {
		return relaxationInMonths;
	}

	public void setRelaxationInMonths(int relaxationInMonths) {
		this.relaxationInMonths = relaxationInMonths;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

}
