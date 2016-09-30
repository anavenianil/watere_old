package com.callippus.web.beans.paybill;

import java.util.Date;
import java.util.List;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.paybill.dto.PayAllwDetailsDTO;
import com.callippus.web.paybill.dto.PayAllwTypeDTO;
import com.callippus.web.paybill.dto.PayScaleDesignationDTO;
import com.callippus.web.paybill.dto.PaybandDTO;

public class PayScaleBean extends CommonDTO {
	private String param;
	private String message;
	private String type;
	private String sfid;
	private String pk;
	private String firstTypeValue;
	private String secondTypeValue;
	private String to;
	private List<PayScaleBean> payScaleList;
	private DesignationDTO designationDetails;
	private PaybandDTO paybandDetails;
	private String designation;
	private String payband;
	private String rangeFrom;
	private String rangeTo;
	private String gradePay;
	private List<PayScaleBean> designationList;
	private List<PayScaleBean> paybandList;
	private List<PayScaleDesignationDTO> payDesignationList;
	private Date effDate;
	private List<PaybandDTO> payBandList;
	private List<DesignationDTO> payDesigList;
	private String designationId;
	private List<PayAllwTypeDTO> allwTypeList;
	private String cofigurationId;
	private String amount;
	private List<PayAllwDetailsDTO> allwDetailsList;
	private String effDateString;
	private String payRunMonth;
	
	private String basicFm;
	private String basicTo;
	
	public String getBasicFm() {
		return basicFm;
	}

	public void setBasicFm(String basicFm) {
		this.basicFm = basicFm;
	}

	public String getBasicTo() {
		return basicTo;
	}

	public void setBasicTo(String basicTo) {
		this.basicTo = basicTo;
	}

	public String getPayRunMonth() {
		return payRunMonth;
	}

	public void setPayRunMonth(String payRunMonth) {
		this.payRunMonth = payRunMonth;
	}

	public String getEffDateString() {
		return effDateString;
	}

	public void setEffDateString(String effDateString) {
		this.effDateString = effDateString;
	}

	public List<PayAllwDetailsDTO> getAllwDetailsList() {
		return allwDetailsList;
	}

	public void setAllwDetailsList(List<PayAllwDetailsDTO> allwDetailsList) {
		this.allwDetailsList = allwDetailsList;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCofigurationId() {
		return cofigurationId;
	}

	public void setCofigurationId(String cofigurationId) {
		this.cofigurationId = cofigurationId;
	}

	public List<PayAllwTypeDTO> getAllwTypeList() {
		return allwTypeList;
	}

	public void setAllwTypeList(List<PayAllwTypeDTO> allwTypeList) {
		this.allwTypeList = allwTypeList;
	}

	public String getDesignationId() {
		return designationId;
	}

	public void setDesignationId(String designationId) {
		this.designationId = designationId;
	}

	public List<DesignationDTO> getPayDesigList() {
		return payDesigList;
	}

	public void setPayDesigList(List<DesignationDTO> payDesigList) {
		this.payDesigList = payDesigList;
	}

	public List<PaybandDTO> getPayBandList() {
		return payBandList;
	}

	public void setPayBandList(List<PaybandDTO> payBandList) {
		this.payBandList = payBandList;
	}

	public Date getEffDate() {
		return effDate;
	}

	public void setEffDate(Date effDate) {
		this.effDate = effDate;
	}

	public List<PayScaleBean> getDesignationList() {
		return designationList;
	}

	public void setDesignationList(List<PayScaleBean> designationList) {
		this.designationList = designationList;
	}

	public List<PayScaleBean> getPaybandList() {
		return paybandList;
	}

	public void setPaybandList(List<PayScaleBean> paybandList) {
		this.paybandList = paybandList;
	}

	public List<PayScaleDesignationDTO> getPayDesignationList() {
		return payDesignationList;
	}

	public void setPayDesignationList(List<PayScaleDesignationDTO> payDesignationList) {
		this.payDesignationList = payDesignationList;
	}

	public DesignationDTO getDesignationDetails() {
		return designationDetails;
	}

	public void setDesignationDetails(DesignationDTO designationDetails) {
		this.designationDetails = designationDetails;
	}

	public PaybandDTO getPaybandDetails() {
		return paybandDetails;
	}

	public void setPaybandDetails(PaybandDTO paybandDetails) {
		this.paybandDetails = paybandDetails;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getPayband() {
		return payband;
	}

	public void setPayband(String payband) {
		this.payband = payband;
	}

	public String getRangeFrom() {
		return rangeFrom;
	}

	public void setRangeFrom(String rangeFrom) {
		this.rangeFrom = rangeFrom;
	}

	public String getRangeTo() {
		return rangeTo;
	}

	public void setRangeTo(String rangeTo) {
		this.rangeTo = rangeTo;
	}

	public String getGradePay() {
		return gradePay;
	}

	public void setGradePay(String gradePay) {
		this.gradePay = gradePay;
	}

	public List<PayScaleBean> getPayScaleList() {
		return payScaleList;
	}

	public void setPayScaleList(List<PayScaleBean> payScaleList) {
		this.payScaleList = payScaleList;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFirstTypeValue() {
		return firstTypeValue;
	}

	public void setFirstTypeValue(String firstTypeValue) {
		this.firstTypeValue = firstTypeValue;
	}

	public String getSecondTypeValue() {
		return secondTypeValue;
	}

	public void setSecondTypeValue(String secondTypeValue) {
		this.secondTypeValue = secondTypeValue;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getSfid() {
		return sfid;
	}

	public void setSfid(String sfid) {
		this.sfid = sfid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

}
