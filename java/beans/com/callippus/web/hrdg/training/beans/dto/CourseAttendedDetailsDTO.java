package com.callippus.web.hrdg.training.beans.dto;

import java.util.List;

import com.callippus.web.beans.dto.CommonDTO;

public class CourseAttendedDetailsDTO extends CommonDTO{
	
	
	private String course;
	private String nomineeSfid;
	

	private int trainingTypeId;
	private int trainingRegionId;
	
	private int trainingInistituteId;
	private Integer courseSubjCategoryId;
	private Integer courseSubjSubCategoryId;
	private String fee;
	private String serviceTax;
	private String courseYear;
	private String year;
	
	
	
	private String durationStartDate;
	private String durationEndDate;
	
	private String trainingRegion;
	private String trainingType;
	private String trainingInistitute;
	private String courseSubjCategory;
	private String courseSubjSubCategory;
	
	
	private List designationList;
	private List desciplineList;
	private List durationList;
	private List venueList;
	private Double duration;
	
	
	
	
	
	
	
	public Double getDuration() {
		return duration;
	}
	public void setDuration(Double duration) {
		this.duration = duration;
	}
	public int getTrainingTypeId() {
		return trainingTypeId;
	}
	public void setTrainingTypeId(int trainingTypeId) {
		this.trainingTypeId = trainingTypeId;
	}
	
	public int getTrainingInistituteId() {
		return trainingInistituteId;
	}
	public void setTrainingInistituteId(int trainingInistituteId) {
		this.trainingInistituteId = trainingInistituteId;
	}
	public String getTrainingInistitute() {
		return trainingInistitute;
	}
	public void setTrainingInistitute(String trainingInistitute) {
		this.trainingInistitute = trainingInistitute;
	}
	public Integer getCourseSubjCategoryId() {
		return courseSubjCategoryId;
	}
	public void setCourseSubjCategoryId(Integer courseSubjCategoryId) {
		this.courseSubjCategoryId = courseSubjCategoryId;
	}
	public String getCourseSubjCategory() {
		return courseSubjCategory;
	}
	public void setCourseSubjCategory(String courseSubjCategory) {
		this.courseSubjCategory = courseSubjCategory;
	}
	public Integer getCourseSubjSubCategoryId() {
		return courseSubjSubCategoryId;
	}
	public void setCourseSubjSubCategoryId(Integer courseSubjSubCategoryId) {
		this.courseSubjSubCategoryId = courseSubjSubCategoryId;
	}
	public String getCourseSubjSubCategory() {
		return courseSubjSubCategory;
	}
	public void setCourseSubjSubCategory(String courseSubjSubCategory) {
		this.courseSubjSubCategory = courseSubjSubCategory;
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
	public String getTrainingType() {
		return trainingType;
	}
	public void setTrainingType(String trainingType) {
		this.trainingType = trainingType;
	}
	public List getDesignationList() {
		return designationList;
	}
	public void setDesignationList(List designationList) {
		this.designationList = designationList;
	}
	public List getDesciplineList() {
		return desciplineList;
	}
	public void setDesciplineList(List desciplineList) {
		this.desciplineList = desciplineList;
	}

	public String getCourseYear() {
		return courseYear;
	}
	public void setCourseYear(String courseYear) {
		this.courseYear = courseYear;
	}

	public List getDurationList() {
		return durationList;
	}
	public void setDurationList(List durationList) {
		this.durationList = durationList;
	}
	public List getVenueList() {
		return venueList;
	}
	public void setVenueList(List venueList) {
		this.venueList = venueList;
	}
	public int getTrainingRegionId() {
		return trainingRegionId;
	}
	public void setTrainingRegionId(int trainingRegionId) {
		this.trainingRegionId = trainingRegionId;
	}
	public String getTrainingRegion() {
		return trainingRegion;
	}
	public void setTrainingRegion(String trainingRegion) {
		this.trainingRegion = trainingRegion;
	}

	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getNomineeSfid() {
		return nomineeSfid;
	}
	public void setNomineeSfid(String nomineeSfid) {
		this.nomineeSfid = nomineeSfid;
	}
	public String getDurationStartDate() {
		return durationStartDate;
	}
	public void setDurationStartDate(String durationStartDate) {
		this.durationStartDate = durationStartDate;
	}
	public String getDurationEndDate() {
		return durationEndDate;
	}
	public void setDurationEndDate(String durationEndDate) {
		this.durationEndDate = durationEndDate;
	}
}
