package com.callippus.web.promotions.dto;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.beans.dto.YearTypeDTO;

public class VenueDetailsDTO extends CommonDTO {
	private AssessmentCategoryDTO assessmentCategoryDetails;
	private int assessmentCategoryID;
	private YearTypeDTO yearDetails;
	private int yearID;
	private String center;
	private String coOrdinator;
	private String coOrdinatorLab;
	private String address;
	private String contactAddress;
	private String venue;
	private int categoryId;
    private String categoryName;
	
    
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public AssessmentCategoryDTO getAssessmentCategoryDetails() {
		return assessmentCategoryDetails;
	}

	public void setAssessmentCategoryDetails(AssessmentCategoryDTO assessmentCategoryDetails) {
		this.assessmentCategoryDetails = assessmentCategoryDetails;
	}

	public int getAssessmentCategoryID() {
		return assessmentCategoryID;
	}

	public void setAssessmentCategoryID(int assessmentCategoryID) {
		this.assessmentCategoryID = assessmentCategoryID;
	}

	public YearTypeDTO getYearDetails() {
		return yearDetails;
	}

	public void setYearDetails(YearTypeDTO yearDetails) {
		this.yearDetails = yearDetails;
	}

	public int getYearID() {
		return yearID;
	}

	public void setYearID(int yearID) {
		this.yearID = yearID;
	}

	public String getCenter() {
		return center;
	}

	public void setCenter(String center) {
		this.center = center;
	}

	public String getCoOrdinator() {
		return coOrdinator;
	}

	public void setCoOrdinator(String coOrdinator) {
		this.coOrdinator = coOrdinator;
	}

	public String getCoOrdinatorLab() {
		return coOrdinatorLab;
	}

	public void setCoOrdinatorLab(String coOrdinatorLab) {
		this.coOrdinatorLab = coOrdinatorLab;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

}
