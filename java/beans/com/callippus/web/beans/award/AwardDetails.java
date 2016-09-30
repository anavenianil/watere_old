package com.callippus.web.beans.award;

import java.util.List;

public class AwardDetails {
	private String id;
	private String sfid; 
	private String year;
	private String organization;
	private String detailsOfWork;
	private String cash;
	private String medallion;
	private String citation;
	private String certificate;
	private String awardCategory;
	private String remarks;
	private String awardDescription;
	private int status;
	private String param;
	private String type;
	private String message;
	private List<AwardDetails> awardList;
	private String yearName;
	private String categoryName;
	private String creationDate;
	private String lastModifiedDate;
	
	
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
	public String getYearName() {
		return yearName;
	}
	public void setYearName(String yearName) {
		this.yearName = yearName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public List<AwardDetails> getAwardList() {
        return awardList;
    }
    public void setAwardList(List<AwardDetails> awardList) {
        this.awardList = awardList;
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
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getSfid() {
        return sfid;
    }
    public void setSfid(String sfid) {
        this.sfid = sfid;
    }
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public String getOrganization() {
        return organization;
    }
    public void setOrganization(String organization) {
        this.organization = organization;
    }
    public String getDetailsOfWork() {
        return detailsOfWork;
    }
    public void setDetailsOfWork(String detailsOfWork) {
        this.detailsOfWork = detailsOfWork;
    }
    public String getCash() {
        return cash;
    }
    public void setCash(String cash) {
        this.cash = cash;
    }
    public String getMedallion() {
        return medallion;
    }
    public void setMedallion(String medallion) {
        this.medallion = medallion;
    }
    public String getCitation() {
        return citation;
    }
    public void setCitation(String citation) {
        this.citation = citation;
    }
    public String getCertificate() {
        return certificate;
    }
    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }
    public String getAwardCategory() {
        return awardCategory;
    }
    public void setAwardCategory(String awardCategory) {
        this.awardCategory = awardCategory;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public String getAwardDescription() {
        return awardDescription;
    }
    public void setAwardDescription(String awardDescription) {
        this.awardDescription = awardDescription;
    }

}
