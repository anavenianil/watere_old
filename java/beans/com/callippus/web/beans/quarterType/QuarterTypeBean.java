package com.callippus.web.beans.quarterType;

import java.util.Date;
import java.util.List;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.paybill.dto.LicenceFeeChargesDTO;
import com.callippus.web.paybill.dto.PayBillQuartersTypeMasterDTO;

public class QuarterTypeBean extends CommonDTO {
	private String param;
	private String message;
	private String quarterSubType;
	private List<QuarterTypeBean> quarterTypeList;
	private String sfid;
	private List<QuarterTypeBean> quarterSubTypeList;
	private PayBillQuartersTypeMasterDTO quarterTypeDetails;
	private String type;
	private String quartersType;
	private String pk;
	private String gradePay;
	private String licenceFee;
	private List<QuarterTypeBean> quarterList;
	private List<LicenceFeeChargesDTO> LicenceFeeList;
	private int parentID;
	private String parentName;
	private List<PayBillQuartersTypeMasterDTO> quarterTypeMasterList;
	private Date effDate;
	private String water;
	private String furn;
	private String elec;
	private String payRunMonth;

	public String getPayRunMonth() {
		return payRunMonth;
	}

	public void setPayRunMonth(String payRunMonth) {
		this.payRunMonth = payRunMonth;
	}

	public String getWater() {
		return water;
	}

	public void setWater(String water) {
		this.water = water;
	}

	public String getFurn() {
		return furn;
	}

	public void setFurn(String furn) {
		this.furn = furn;
	}

	public String getElec() {
		return elec;
	}

	public void setElec(String elec) {
		this.elec = elec;
	}

	public Date getEffDate() {
		return effDate;
	}

	public void setEffDate(Date effDate) {
		this.effDate = effDate;
	}

	public List<PayBillQuartersTypeMasterDTO> getQuarterTypeMasterList() {
		return quarterTypeMasterList;
	}

	public void setQuarterTypeMasterList(List<PayBillQuartersTypeMasterDTO> quarterTypeMasterList) {
		this.quarterTypeMasterList = quarterTypeMasterList;
	}

	public int getParentID() {
		return parentID;
	}

	public void setParentID(int parentID) {
		this.parentID = parentID;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public List<LicenceFeeChargesDTO> getLicenceFeeList() {
		return LicenceFeeList;
	}

	public void setLicenceFeeList(List<LicenceFeeChargesDTO> licenceFeeList) {
		LicenceFeeList = licenceFeeList;
	}

	public String getGradePay() {
		return gradePay;
	}

	public void setGradePay(String gradePay) {
		this.gradePay = gradePay;
	}

	public String getLicenceFee() {
		return licenceFee;
	}

	public void setLicenceFee(String licenceFee) {
		this.licenceFee = licenceFee;
	}

	public List<QuarterTypeBean> getQuarterList() {
		return quarterList;
	}

	public void setQuarterList(List<QuarterTypeBean> quarterList) {
		this.quarterList = quarterList;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getQuartersType() {
		return quartersType;
	}

	public void setQuartersType(String quartersType) {
		this.quartersType = quartersType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public PayBillQuartersTypeMasterDTO getQuarterTypeDetails() {
		return quarterTypeDetails;
	}

	public void setQuarterTypeDetails(PayBillQuartersTypeMasterDTO quarterTypeDetails) {
		this.quarterTypeDetails = quarterTypeDetails;
	}

	public List<QuarterTypeBean> getQuarterSubTypeList() {
		return quarterSubTypeList;
	}

	public void setQuarterSubTypeList(List<QuarterTypeBean> quarterSubTypeList) {
		this.quarterSubTypeList = quarterSubTypeList;
	}

	public String getSfid() {
		return sfid;
	}

	public void setSfid(String sfid) {
		this.sfid = sfid;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getQuarterSubType() {
		return quarterSubType;
	}

	public void setQuarterSubType(String quarterSubType) {
		this.quarterSubType = quarterSubType;
	}

	public List<QuarterTypeBean> getQuarterTypeList() {
		return quarterTypeList;
	}

	public void setQuarterTypeList(List<QuarterTypeBean> quarterTypeList) {
		this.quarterTypeList = quarterTypeList;
	}
}
