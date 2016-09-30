package com.callippus.web.beans.requests;

import java.util.Date;

import javax.servlet.http.HttpSession;

import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.family.FamilyBean;
import com.callippus.web.cghs.beans.dto.ReferralHospitalDTO;
import com.callippus.web.cghs.beans.dto.RequestTypeDTO;
import com.callippus.web.cghs.beans.dto.WardTypeDTO;


public class CGHSRequestProcessBean extends RequestBean{
	
	private String address;
	private String designationId;
 	private String departmentId;
 	private String familyMemberId;
 	private String disease;
 	private Date prescriptionDate;
 	private String basicPay;
 	private String gradePay;
 	private String cghsRequestTypeId;
 	private String referredDoctor;
 	private String cghsCardNumber;
 	private int referralHospitalId;
 	
	private int otherHospitalId;
 	private String referenceSpecialist;
 	private String prescriptionFileName;
 	private Date appliedDate;
 	private String wardTypeId;
 	private String age; 	
 	private String gender;
 	private String dob;
 	private String relation;
 	private String empDetails;
 	private String referrenceRequestID;
 	private Date admissionDate;
 	private HttpSession session;
 	private String amountInWords;
 	
	private String advanceDetails;
	private String permissionDetails;
	private String amountClaimed;
	private Date dischargeDate;
	private String hospitalDetails;
	
	
	
 	/**
 	 * uploded files name
 	 */
	private String prescriptionFile;
	private String permissionFile;
	private String estimationFile;
	private String contingentFile;
	private String cghsCardFile;
	private String refundChequeFile;
	private String dischargeSummeryFile;
	private String medicalBillsFile;
	private String essentialityFile;
	private String med2004File;
	private String checkListFile;
	private String requestFormFile;
	private String familyMembetId;
	private String reimbursementFile;
	
	private EmployeeBean empMasterDetails;
	private FamilyBean familyMemberDetails;
	private RequestTypeDTO requestTypeDetails;
	private ReferralHospitalDTO referralHospitalDetails;
	private WardTypeDTO wardTypeDetails;
	private String hospitalAddress;
	private String emergencyDetails;
	private String patientIllAddress;
	private String otherAddress;
	private String nonCghsReason;
	private Date estimationDate;
	private String estimationAmount;
	private String InPatientNumber;
	private String issuedAmount;
	
	private int labCharges;
	private int medicinesCharges;
	private int consultationFees;
	private int disposableCharges;
	private int specialDevices;
	private int roomRent;
	private int otCharges;
	private int otConsumables;
	private int anaesthesiaCharges;
	private int implantsCharges;
	private int artificialCharges;
	private int procedure;
	private int specialNurse;
	private int miscellaneousCharges;
	private String noofVouchers;
    private String settlement;
    private String cghscardref;
	
	
	public String getSettlement() {
		return settlement;
	}
	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}
	public int getOtherHospitalId() {
		return otherHospitalId;
	}
	public void setOtherHospitalId(int otherHospitalId) {
		this.otherHospitalId = otherHospitalId;
	}
	
	
	
	
	public String getNoofVouchers() {
		return noofVouchers;
	}
	public void setNoofVouchers(String noofVouchers) {
		this.noofVouchers = noofVouchers;
	}
	public int getLabCharges() {
		return labCharges;
	}
	public void setLabCharges(int labCharges) {
		this.labCharges = labCharges;
	}
	public int getMedicinesCharges() {
		return medicinesCharges;
	}
	public void setMedicinesCharges(int medicinesCharges) {
		this.medicinesCharges = medicinesCharges;
	}
	public int getConsultationFees() {
		return consultationFees;
	}
	public void setConsultationFees(int consultationFees) {
		this.consultationFees = consultationFees;
	}
	public int getDisposableCharges() {
		return disposableCharges;
	}
	public void setDisposableCharges(int disposableCharges) {
		this.disposableCharges = disposableCharges;
	}
	public int getSpecialDevices() {
		return specialDevices;
	}
	public void setSpecialDevices(int specialDevices) {
		this.specialDevices = specialDevices;
	}
	public int getRoomRent() {
		return roomRent;
	}
	public void setRoomRent(int roomRent) {
		this.roomRent = roomRent;
	}
	public int getOtCharges() {
		return otCharges;
	}
	public void setOtCharges(int otCharges) {
		this.otCharges = otCharges;
	}
	public int getOtConsumables() {
		return otConsumables;
	}
	public void setOtConsumables(int otConsumables) {
		this.otConsumables = otConsumables;
	}
	public int getAnaesthesiaCharges() {
		return anaesthesiaCharges;
	}
	public void setAnaesthesiaCharges(int anaesthesiaCharges) {
		this.anaesthesiaCharges = anaesthesiaCharges;
	}
	public int getImplantsCharges() {
		return implantsCharges;
	}
	public void setImplantsCharges(int implantsCharges) {
		this.implantsCharges = implantsCharges;
	}
	public int getArtificialCharges() {
		return artificialCharges;
	}
	public void setArtificialCharges(int artificialCharges) {
		this.artificialCharges = artificialCharges;
	}
	public int getProcedure() {
		return procedure;
	}
	public void setProcedure(int procedure) {
		this.procedure = procedure;
	}
	public int getSpecialNurse() {
		return specialNurse;
	}
	public void setSpecialNurse(int specialNurse) {
		this.specialNurse = specialNurse;
	}
	public int getMiscellaneousCharges() {
		return miscellaneousCharges;
	}
	public void setMiscellaneousCharges(int miscellaneousCharges) {
		this.miscellaneousCharges = miscellaneousCharges;
	}
	public String getIssuedAmount() {
		return issuedAmount;
	}
	public void setIssuedAmount(String issuedAmount) {
		this.issuedAmount = issuedAmount;
	}
	public Date getEstimationDate() {
		return estimationDate;
	}
	public void setEstimationDate(Date estimationDate) {
		this.estimationDate = estimationDate;
	}
	public String getEstimationAmount() {
		return estimationAmount;
	}
	public void setEstimationAmount(String estimationAmount) {
		this.estimationAmount = estimationAmount;
	}
	public String getInPatientNumber() {
		return InPatientNumber;
	}
	public void setInPatientNumber(String inPatientNumber) {
		InPatientNumber = inPatientNumber;
	}
	public String getAmountInWords() {
		return amountInWords;
	}
	public void setAmountInWords(String amountInWords) {
		this.amountInWords = amountInWords;
	}	
	
	

	public String getEmergencyDetails() {
		return emergencyDetails;
	}
	public void setEmergencyDetails(String emergencyDetails) {
		this.emergencyDetails = emergencyDetails;
	}
	public String getPatientIllAddress() {
		return patientIllAddress;
	}
	public void setPatientIllAddress(String patientIllAddress) {
		this.patientIllAddress = patientIllAddress;
	}
	public String getOtherAddress() {
		return otherAddress;
	}
	public void setOtherAddress(String otherAddress) {
		this.otherAddress = otherAddress;
	}
	public String getNonCghsReason() {
		return nonCghsReason;
	}
	public void setNonCghsReason(String nonCghsReason) {
		this.nonCghsReason = nonCghsReason;
	}
	public String getHospitalAddress() {
		return hospitalAddress;
	}
	public void setHospitalAddress(String hospitalAddress) {
		this.hospitalAddress = hospitalAddress;
	}
	public String getReimbursementFile() {
		return reimbursementFile;
	}
	public void setReimbursementFile(String reimbursementFile) {
		this.reimbursementFile = reimbursementFile;
	}
	public String getFamilyMembetId() {
		return familyMembetId;
	}
	public void setFamilyMembetId(String familyMembetId) {
		this.familyMembetId = familyMembetId;
	}
	public Date getAdmissionDate() {
		return admissionDate;
	}
	public void setAdmissionDate(Date admissionDate) {
		this.admissionDate = admissionDate;
	}
	public FamilyBean getFamilyMemberDetails() {
		return familyMemberDetails;
	}
	public void setFamilyMemberDetails(FamilyBean familyMemberDetails) {
		this.familyMemberDetails = familyMemberDetails;
	}
	public RequestTypeDTO getRequestTypeDetails() {
		return requestTypeDetails;
	}
	public void setRequestTypeDetails(RequestTypeDTO requestTypeDetails) {
		this.requestTypeDetails = requestTypeDetails;
	}
	public ReferralHospitalDTO getReferralHospitalDetails() {
		return referralHospitalDetails;
	}
	public void setReferralHospitalDetails(ReferralHospitalDTO referralHospitalDetails) {
		this.referralHospitalDetails = referralHospitalDetails;
	}
	public WardTypeDTO getWardTypeDetails() {
		return wardTypeDetails;
	}
	public void setWardTypeDetails(WardTypeDTO wardTypeDetails) {
		this.wardTypeDetails = wardTypeDetails;
	}
	public EmployeeBean getEmpMasterDetails() {
		return empMasterDetails;
	}
	public void setEmpMasterDetails(EmployeeBean empMasterDetails) {
		this.empMasterDetails = empMasterDetails;
	}
	public String getRequestFormFile() {
		return requestFormFile;
	}
	public void setRequestFormFile(String requestFormFile) {
		this.requestFormFile = requestFormFile;
	}
	public HttpSession getSession() {
		return session;
	}
	public void setSession(HttpSession session) {
		this.session = session;
	}
	public String getAdvanceDetails() {
		return advanceDetails;
	}
	public void setAdvanceDetails(String advanceDetails) {
		this.advanceDetails = advanceDetails;
	}
	public String getPermissionDetails() {
		return permissionDetails;
	}
	public void setPermissionDetails(String permissionDetails) {
		this.permissionDetails = permissionDetails;
	}
	public String getAmountClaimed() {
		return amountClaimed;
	}
	public void setAmountClaimed(String amountClaimed) {
		this.amountClaimed = amountClaimed;
	}
	public String getHospitalDetails() {
		return hospitalDetails;
	}
	public Date getDischargeDate() {
		return dischargeDate;
	}
	public void setDischargeDate(Date dischargeDate) {
		this.dischargeDate = dischargeDate;
	}
	public void setHospitalDetails(String hospitalDetails) {
		this.hospitalDetails = hospitalDetails;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDesignationId() {
		return designationId;
	}
	public void setDesignationId(String designationId) {
		this.designationId = designationId;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getFamilyMemberId() {
		return familyMemberId;
	}
	public void setFamilyMemberId(String familyMemberId) {
		this.familyMemberId = familyMemberId;
	}
	public String getDisease() {
		return disease;
	}
	public void setDisease(String disease) {
		this.disease = disease;
	}
	
	public Date getPrescriptionDate() {
		return prescriptionDate;
	}
	public void setPrescriptionDate(Date prescriptionDate) {
		this.prescriptionDate = prescriptionDate;
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
	public String getCghsRequestTypeId() {
		return cghsRequestTypeId;
	}
	public void setCghsRequestTypeId(String cghsRequestTypeId) {
		this.cghsRequestTypeId = cghsRequestTypeId;
	}
	public String getReferredDoctor() {
		return referredDoctor;
	}
	public void setReferredDoctor(String referredDoctor) {
		this.referredDoctor = referredDoctor;
	}
	public String getCghsCardNumber() {
		return cghsCardNumber;
	}
	public void setCghsCardNumber(String cghsCardNumber) {
		this.cghsCardNumber = cghsCardNumber;
	}
	public int getReferralHospitalId() {
		return referralHospitalId;
	}
	public void setReferralHospitalId(int referralHospitalId) {
		this.referralHospitalId = referralHospitalId;
	}
	public String getReferenceSpecialist() {
		return referenceSpecialist;
	}
	public void setReferenceSpecialist(String referenceSpecialist) {
		this.referenceSpecialist = referenceSpecialist;
	}
	public String getPrescriptionFileName() {
		return prescriptionFileName;
	}
	public void setPrescriptionFileName(String prescriptionFileName) {
		this.prescriptionFileName = prescriptionFileName;
	}
	public Date getAppliedDate() {
		return appliedDate;
	}
	public void setAppliedDate(Date appliedDate) {
		this.appliedDate = appliedDate;
	}
	public String getWardTypeId() {
		return wardTypeId;
	}
	public void setWardTypeId(String wardTypeId) {
		this.wardTypeId = wardTypeId;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getEmpDetails() {
		return empDetails;
	}
	public void setEmpDetails(String empDetails) {
		this.empDetails = empDetails;
	}
	public String getReferrenceRequestID() {
		return referrenceRequestID;
	}
	public void setReferrenceRequestID(String referrenceRequestID) {
		this.referrenceRequestID = referrenceRequestID;
	}
	public String getPrescriptionFile() {
		return prescriptionFile;
	}
	public void setPrescriptionFile(String prescriptionFile) {
		this.prescriptionFile = prescriptionFile;
	}
	public String getPermissionFile() {
		return permissionFile;
	}
	public void setPermissionFile(String permissionFile) {
		this.permissionFile = permissionFile;
	}
	public String getEstimationFile() {
		return estimationFile;
	}
	public void setEstimationFile(String estimationFile) {
		this.estimationFile = estimationFile;
	}
	public String getContingentFile() {
		return contingentFile;
	}
	public void setContingentFile(String contingentFile) {
		this.contingentFile = contingentFile;
	}
	public String getCghsCardFile() {
		return cghsCardFile;
	}
	public void setCghsCardFile(String cghsCardFile) {
		this.cghsCardFile = cghsCardFile;
	}
	public String getRefundChequeFile() {
		return refundChequeFile;
	}
	public void setRefundChequeFile(String refundChequeFile) {
		this.refundChequeFile = refundChequeFile;
	}
	public String getDischargeSummeryFile() {
		return dischargeSummeryFile;
	}
	public void setDischargeSummeryFile(String dischargeSummeryFile) {
		this.dischargeSummeryFile = dischargeSummeryFile;
	}
	public String getMedicalBillsFile() {
		return medicalBillsFile;
	}
	public void setMedicalBillsFile(String medicalBillsFile) {
		this.medicalBillsFile = medicalBillsFile;
	}
	public String getEssentialityFile() {
		return essentialityFile;
	}
	public void setEssentialityFile(String essentialityFile) {
		this.essentialityFile = essentialityFile;
	}
	public String getMed2004File() {
		return med2004File;
	}
	public void setMed2004File(String med2004File) {
		this.med2004File = med2004File;
	}
	public String getCheckListFile() {
		return checkListFile;
	}
	public void setCheckListFile(String checkListFile) {
		this.checkListFile = checkListFile;
	}
	public String getCghscardref() {
		return cghscardref;
	}
	public void setCghscardref(String cghscardref) {
		this.cghscardref = cghscardref;
	}
	
	
 	
	
	

}
