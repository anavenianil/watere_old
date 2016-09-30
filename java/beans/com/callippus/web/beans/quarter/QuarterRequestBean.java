package com.callippus.web.beans.quarter;

import java.util.Date;
import java.util.List;

import com.callippus.web.beans.dto.EmpPaymentsDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.quarterType.QuarterTypeBean;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.paybill.dto.PayBillQuartersTypeMasterDTO;
import com.callippus.web.paybill.dto.PayQuarterManagementDTO;

public class QuarterRequestBean extends RequestBean {
	private int id;
	//private String sfID;
	private String param;
	private String type;
	private String result;
	private EmployeeBean employeeDetails;
	private EmpPaymentsDTO employeePaymentDetails;
	private String eligibility;
	private Date priorityDate;
	private String entitled;
	private String allotment;
	private String saToRm;
	private String otherDept;
	private String debarred;
	private String rentFree;
	private String suretyType;
	private String suretyName;
	private String presentDesig;
	private String officeName;
	private String retirement;
	private String departmentID;
	private String basicPay;
	private String gradePay;
	private String gradePayID;
	private String presentQuarter;
	private int allotedQuarterID;
	private String entitledType;
	private QuarterTypeBean quarterDetails;
	private PayBillQuartersTypeMasterDTO quarterTypeDTO;
	private List<PayBillQuartersTypeMasterDTO> quarterTypeList;
	private List<QuarterTypeBean> quarterSubTypeList;
	
	private String quarterType;
	private String allotedDt;
	private String occupiedDt;
	private String vacatedDt;
	private int wfStatus;
	private String sfID1;
	private String jsonValues;
	
	private String letterDt;
	private String letterNo;
	
	private String selectedGradePays;
	private String gradePayValueId;
	private String quarterTypeId;
	private String quarterSubTypeId;
	private String quarterNo;
	private List<PayQuarterManagementDTO> empQuarterDetails;
	private Date vacationDate;
	private String appliedQuarter;
	private String suretyReq;
	private String quarterOption;
	private String allotedId;
	private String occupiedId;
	private String vacatedId;
	
	public String getQuarterOption() {
		return quarterOption;
	}

	public void setQuarterOption(String quarterOption) {
		this.quarterOption = quarterOption;
	}

	public Date getVacationDate() {
		return vacationDate;
	}

	public void setVacationDate(Date vacationDate) {
		this.vacationDate = vacationDate;
	}

	public List<PayQuarterManagementDTO> getEmpQuarterDetails() {
		return empQuarterDetails;
	}

	public void setEmpQuarterDetails(List<PayQuarterManagementDTO> empQuarterDetails) {
		this.empQuarterDetails = empQuarterDetails;
	}

	public String getQuarterNo() {
		return quarterNo;
	}

	public void setQuarterNo(String quarterNo) {
		this.quarterNo = quarterNo;
	}

	public String getQuarterTypeId() {
		return quarterTypeId;
	}

	public void setQuarterTypeId(String quarterTypeId) {
		this.quarterTypeId = quarterTypeId;
	}

	public String getQuarterSubTypeId() {
		return quarterSubTypeId;
	}

	public void setQuarterSubTypeId(String quarterSubTypeId) {
		this.quarterSubTypeId = quarterSubTypeId;
	}

	public String getGradePayID() {
		return gradePayID;
	}

	public void setGradePayID(String gradePayID) {
		this.gradePayID = gradePayID;
	}

	public String getSelectedGradePays() {
		return selectedGradePays;
	}

	public void setSelectedGradePays(String selectedGradePays) {
		this.selectedGradePays = selectedGradePays;
	}

	public String getGradePayValueId() {
		return gradePayValueId;
	}

	public void setGradePayValueId(String gradePayValueId) {
		this.gradePayValueId = gradePayValueId;
	}

	public int getWfStatus() {
		return wfStatus;
	}

	public void setWfStatus(int wfStatus) {
		this.wfStatus = wfStatus;
	}

	public String getVacatedDt() {
		return vacatedDt;
	}

	public void setVacatedDt(String vacatedDt) {
		this.vacatedDt = vacatedDt;
	}

	public String getLetterDt() {
		return letterDt;
	}

	public void setLetterDt(String letterDt) {
		this.letterDt = letterDt;
	}

	public String getLetterNo() {
		return letterNo;
	}

	public void setLetterNo(String letterNo) {
		this.letterNo = letterNo;
	}

	public String getJsonValues() {
		return jsonValues;
	}

	public void setJsonValues(String jsonValues) {
		this.jsonValues = jsonValues;
	}

	public String getAllotedDt() {
		return allotedDt;
	}

	public void setAllotedDt(String allotedDt) {
		this.allotedDt = allotedDt;
	}

	public String getOccupiedDt() {
		return occupiedDt;
	}

	public void setOccupiedDt(String occupiedDt) {
		this.occupiedDt = occupiedDt;
	}

	public List<QuarterTypeBean> getQuarterSubTypeList() {
		return quarterSubTypeList;
	}

	public void setQuarterSubTypeList(List<QuarterTypeBean> quarterSubTypeList) {
		this.quarterSubTypeList = quarterSubTypeList;
	}

	public String getSfID1() {
		return sfID1;
	}

	public void setSfID1(String sfID1) {
		this.sfID1 = sfID1;
	}

	public String getQuarterType() {
		return quarterType;
	}

	public void setQuarterType(String quarterType) {
		this.quarterType = quarterType;
	}
	
	public PayBillQuartersTypeMasterDTO getQuarterTypeDTO() {
		return quarterTypeDTO;
	}

	public void setQuarterTypeDTO(PayBillQuartersTypeMasterDTO quarterTypeDTO) {
		this.quarterTypeDTO = quarterTypeDTO;
	}

	public List<PayBillQuartersTypeMasterDTO> getQuarterTypeList() {
		return quarterTypeList;
	}

	public void setQuarterTypeList(
			List<PayBillQuartersTypeMasterDTO> quarterTypeList) {
		this.quarterTypeList = quarterTypeList;
	}

	public QuarterTypeBean getQuarterDetails() {
		return quarterDetails;
	}

	public void setQuarterDetails(QuarterTypeBean quarterDetails) {
		this.quarterDetails = quarterDetails;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EmpPaymentsDTO getEmployeePaymentDetails() {
		return employeePaymentDetails;
	}

	public void setEmployeePaymentDetails(EmpPaymentsDTO employeePaymentDetails) {
		this.employeePaymentDetails = employeePaymentDetails;
	}

	public String getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}

	public String getBasicPay() {
		return basicPay;
	}

	public void setBasicPay(String basicPay) {
		this.basicPay = basicPay;
	}

	public String getGradePay() {
		return gradePay;
	}

	public void setGradePay(String gradePay) {
		this.gradePay = gradePay;
	}

	public String getPresentQuarter() {
		return presentQuarter;
	}

	public void setPresentQuarter(String presentQuarter) {
		this.presentQuarter = presentQuarter;
	}

	public int getAllotedQuarterID() {
		return allotedQuarterID;
	}

	public void setAllotedQuarterID(int allotedQuarterID) {
		this.allotedQuarterID = allotedQuarterID;
	}

	public String getEligibility() {
		return eligibility;
	}

	public void setEligibility(String eligibility) {
		this.eligibility = eligibility;
	}

	public Date getPriorityDate() {
		return priorityDate;
	}

	public void setPriorityDate(Date priorityDate) {
		this.priorityDate = priorityDate;
	}

	public String getEntitledType() {
		return entitledType;
	}

	public void setEntitledType(String entitledType) {
		this.entitledType = entitledType;
	}

	public String getEntitled() {
		return entitled;
	}

	public void setEntitled(String entitled) {
		this.entitled = entitled;
	}

	public String getAllotment() {
		return allotment;
	}

	public void setAllotment(String allotment) {
		this.allotment = allotment;
	}

	public String getSaToRm() {
		return saToRm;
	}

	public void setSaToRm(String saToRm) {
		this.saToRm = saToRm;
	}

	public String getOtherDept() {
		return otherDept;
	}

	public void setOtherDept(String otherDept) {
		this.otherDept = otherDept;
	}

	public String getDebarred() {
		return debarred;
	}

	public void setDebarred(String debarred) {
		this.debarred = debarred;
	}

	public String getRentFree() {
		return rentFree;
	}

	public void setRentFree(String rentFree) {
		this.rentFree = rentFree;
	}

	public String getSuretyType() {
		return suretyType;
	}

	public void setSuretyType(String suretyType) {
		this.suretyType = suretyType;
	}

	public String getSuretyName() {
		return suretyName;
	}

	public void setSuretyName(String suretyName) {
		this.suretyName = suretyName;
	}

	public String getPresentDesig() {
		return presentDesig;
	}

	public void setPresentDesig(String presentDesig) {
		this.presentDesig = presentDesig;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getRetirement() {
		return retirement;
	}

	public void setRetirement(String retirement) {
		this.retirement = retirement;
	}

	public EmployeeBean getEmployeeDetails() {
		return employeeDetails;
	}

	public void setEmployeeDetails(EmployeeBean employeeDetails) {
		this.employeeDetails = employeeDetails;
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getAppliedQuarter() {
		return appliedQuarter;
	}

	public void setAppliedQuarter(String appliedQuarter) {
		this.appliedQuarter = appliedQuarter;
	}

	public String getSuretyReq() {
		return suretyReq;
	}

	public void setSuretyReq(String suretyReq) {
		this.suretyReq = suretyReq;
	}

	public String getAllotedId() {
		return allotedId;
	}

	public void setAllotedId(String allotedId) {
		this.allotedId = allotedId;
	}

	public String getOccupiedId() {
		return occupiedId;
	}

	public void setOccupiedId(String occupiedId) {
		this.occupiedId = occupiedId;
	}

	public String getVacatedId() {
		return vacatedId;
	}

	public void setVacatedId(String vacatedId) {
		this.vacatedId = vacatedId;
	}

}
