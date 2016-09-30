package com.callippus.web.beans.EmpPayment;

import java.util.Date;
import java.util.List;

import com.callippus.web.beans.dto.EmpPaymentsDTO;
import com.callippus.web.paybill.dto.PayScaleDesignationDTO;
import com.callippus.web.promotions.beans.management.PromotionOfflineEntryBean;
import com.callippus.web.promotions.dto.EmpGradePayHistoryDTO;
import com.callippus.web.promotions.dto.PromoteesEntryDTO;


public class EmployeePaymentBean {
	private int id;
	private String type;
	private String sfid;
	private String basic;
	private String grade;
	private String param;
	private String message;
	private String result;
	private String effectiveDate;
	private List<EmpPaymentsDTO> empPaymentDetails;
	
	private int designationId;
	private String incrementType;
	private float incrementValue;
	private int status;
	private String referenceType;
	private List<PromotionOfflineEntryBean> catwiseDesig;
	private List<PayScaleDesignationDTO> empPayScaleList;
	private int worklocation;
	private int role;
	private String sfID;
	private String refSfid;
	//private String designationTo;
	private int designationTo;
	private String newBasicPay;
	private String twoAddl;
	private int designationFrom;
	private String valriableIncVal;
	private Date presentEffectivedate;
	private String newGradePay;
	private String seniorityDate;
	private Date promotionDate;
	private String varIncEnd;
	private String BasicPay;
	private List<PromoteesEntryDTO> offlineEntryList;
	private String nodeID;
	private String varIncVal;
	
	public String getVarIncVal() {
		return varIncVal;
	}
	public void setVarIncVal(String varIncVal) {
		this.varIncVal = varIncVal;
	}
	public String getNodeID() {
		return nodeID;
	}
	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}
	public int getDesignationTo() {
		return designationTo;
	}
	public void setDesignationTo(int designationTo) {
		this.designationTo = designationTo;
	}
	public List<PromoteesEntryDTO> getOfflineEntryList() {
		return offlineEntryList;
	}
	public void setOfflineEntryList(List<PromoteesEntryDTO> offlineEntryList) {
		this.offlineEntryList = offlineEntryList;
	}
	public String getBasicPay() {
		return BasicPay;
	}
	public void setBasicPay(String basicPay) {
		BasicPay = basicPay;
	}
	public String getVarIncEnd() {
		return varIncEnd;
	}
	public void setVarIncEnd(String varIncEnd) {
		this.varIncEnd = varIncEnd;
	}
	public String getSeniorityDate() {
		return seniorityDate;
	}
	public void setSeniorityDate(String seniorityDate) {
		this.seniorityDate = seniorityDate;
	}
	public Date getPromotionDate() {
		return promotionDate;
	}
	public void setPromotionDate(Date promotionDate) {
		this.promotionDate = promotionDate;
	}
	public String getNewGradePay() {
		return newGradePay;
	}
	public void setNewGradePay(String newGradePay) {
		this.newGradePay = newGradePay;
	}
	public String getTwoAddl() {
		return twoAddl;
	}
	public void setTwoAddl(String twoAddl) {
		this.twoAddl = twoAddl;
	}
	public int getDesignationFrom() {
		return designationFrom;
	}
	public void setDesignationFrom(int designationFrom) {
		this.designationFrom = designationFrom;
	}
	public String getValriableIncVal() {
		return valriableIncVal;
	}
	public void setValriableIncVal(String valriableIncVal) {
		this.valriableIncVal = valriableIncVal;
	}
	public Date getPresentEffectivedate() {
		return presentEffectivedate;
	}
	public void setPresentEffectivedate(Date presentEffectivedate) {
		this.presentEffectivedate = presentEffectivedate;
	}
	public String getNewBasicPay() {
		return newBasicPay;
	}
	public void setNewBasicPay(String newBasicPay) {
		this.newBasicPay = newBasicPay;
	}
	
	public String getRefSfid() {
		return refSfid;
	}
	public void setRefSfid(String refSfid) {
		this.refSfid = refSfid;
	}
	private List <EmpGradePayHistoryDTO> empgradpaymentHistory;
	
	public List<EmpGradePayHistoryDTO> getEmpgradpaymentHistory() {
		return empgradpaymentHistory;
	}
	public void setEmpgradpaymentHistory(
			List<EmpGradePayHistoryDTO> empgradpaymentHistory) {
		this.empgradpaymentHistory = empgradpaymentHistory;
	}
	public int getWorklocation() {
		return worklocation;
	}
	public void setWorklocation(int worklocation) {
		this.worklocation = worklocation;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	private String invalidSFID;
	
	
	public String getInvalidSFID() {
		return invalidSFID;
	}
	public void setInvalidSFID(String invalidSFID) {
		this.invalidSFID = invalidSFID;
	}
	public List<PayScaleDesignationDTO> getEmpPayScaleList() {
		return empPayScaleList;
	}
	public void setEmpPayScaleList(List<PayScaleDesignationDTO> empPayScaleList) {
		this.empPayScaleList = empPayScaleList;
	}
	public List<PromotionOfflineEntryBean> getCatwiseDesig() {
		return catwiseDesig;
	}
	public void setCatwiseDesig(List<PromotionOfflineEntryBean> catwiseDesig) {
		this.catwiseDesig = catwiseDesig;
	}
	public String getReferenceType() {
		return referenceType;
	}
	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getDesignationId() {
		return designationId;
	}
	public void setDesignationId(int designationId) {
		this.designationId = designationId;
	}
	public String getIncrementType() {
		return incrementType;
	}
	public void setIncrementType(String incrementType) {
		this.incrementType = incrementType;
	}
	public float getIncrementValue() {
		return incrementValue;
	}
	public void setIncrementValue(float incrementValue) {
		this.incrementValue = incrementValue;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public List<EmpPaymentsDTO> getEmpPaymentDetails() {
		return empPaymentDetails;
	}
	public void setEmpPaymentDetails(List<EmpPaymentsDTO> empPaymentDetails) {
		this.empPaymentDetails = empPaymentDetails;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
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
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public String getBasic() {
		return basic;
	}
	public void setBasic(String basic) {
		this.basic = basic;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getSfID() {
		return sfID;
	}
	public void setSfID(String sfID) {
		this.sfID = sfID;
	}
	
}
