package com.callippus.web.beans.telephone;

import java.util.Date;

public class TelephoneDesigEligibilityDetailsDTO {

	private int id;
	private int designationId;
    private int amountWithInternet;
	private float serviceTaxWithInternet;
	private int totAmountWithInternet;
	private int amountWithoutInternet;
	private float serviceTaxWithoutInternet;
	private int totAmountWithoutInternet;
	private int applicableEmployee;
	private String designations;
	private String status;
	private String designationsIds;
	private String sfid;
	private String createdBy;
    private String lastModifiedBy;
    private Date creationDate;
    private Date lastModifiedDate;
	
	//private int amount1;//for without internet
	//private float serviceTax1;
	//private int totAmount1;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDesignationId() {
		return designationId;
	}
	public void setDesignationId(int designationId) {
		this.designationId = designationId;
	}
	
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public int getApplicableEmployee() {
		return applicableEmployee;
	}
	public void setApplicableEmployee(int applicableEmployee) {
		this.applicableEmployee = applicableEmployee;
	}
	public String getDesignations() {
		return designations;
	}
	public void setDesignations(String designations) {
		this.designations = designations;
	}
	public String getDesignationsIds() {
		return designationsIds;
	}
	public void setDesignationsIds(String designationsIds) {
		this.designationsIds = designationsIds;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getAmountWithInternet() {
		return amountWithInternet;
	}
	public void setAmountWithInternet(int amountWithInternet) {
		this.amountWithInternet = amountWithInternet;
	}
	public float getServiceTaxWithInternet() {
		return serviceTaxWithInternet;
	}
	public void setServiceTaxWithInternet(float serviceTaxWithInternet) {
		this.serviceTaxWithInternet = serviceTaxWithInternet;
	}
	public int getTotAmountWithInternet() {
		return totAmountWithInternet;
	}
	public void setTotAmountWithInternet(int totAmountWithInternet) {
		this.totAmountWithInternet = totAmountWithInternet;
	}
	public int getAmountWithoutInternet() {
		return amountWithoutInternet;
	}
	public void setAmountWithoutInternet(int amountWithoutInternet) {
		this.amountWithoutInternet = amountWithoutInternet;
	}
	public float getServiceTaxWithoutInternet() {
		return serviceTaxWithoutInternet;
	}
	public void setServiceTaxWithoutInternet(float serviceTaxWithoutInternet) {
		this.serviceTaxWithoutInternet = serviceTaxWithoutInternet;
	}
	public int getTotAmountWithoutInternet() {
		return totAmountWithoutInternet;
	}
	public void setTotAmountWithoutInternet(int totAmountWithoutInternet) {
		this.totAmountWithoutInternet = totAmountWithoutInternet;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	
	
}
