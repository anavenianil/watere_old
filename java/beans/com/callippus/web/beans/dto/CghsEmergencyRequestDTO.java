package com.callippus.web.beans.dto;

import java.util.Date;

import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.family.FamilyBean;
import com.callippus.web.cghs.beans.dto.ReferralHospitalDTO;

public class CghsEmergencyRequestDTO {

	
	private String message;
	private String requestID;
	private String sfID;
	private String cghsCardFile;
	private String dischargeSummeryFile;
	private String medicalBillsFile;
	private String reimbursementFile;
	private String nonCghsReason;
	private String otherAddress;
	private String patientIllAddress;
	private String emergencyDetails;
	private String familyMemberId;
	private String amountClaimed;
	private int referralHospitalId;
	private Date dischargeDate;
	private Date admissionDate;
	private int designation;
	private int department;
	private int status;
	private Date appliedDate;
	private String ipAddress; 	 
	private String approvedBy;
	private Date approvedDate;
	private String requestType;
	private String emergencyForms;
	private String hospitalAddress;
	private EmployeeBean employeeDetails;
	private FamilyBean familyDetails;
	private ReferralHospitalDTO hospitalDetails;
	private String disease;
	private String cdaAmount;
	private String repSig;
	private String requestForm;
	private String dvNo;      
	private Date dvDate;      
	private String accOfficer;
	
	private int labCharges;
	private int medicinesCharges;
	private int roomRent;
	private int otCharges;
	private int otConsumables;
	private int anaesthesiaCharges;
	private int implantsCharges;
	private int artificialCharges;
	private int procedure;
	private int specialNurse;
	private int miscellaneousCharges;
	private String billNo;
	private String sanctionNo;
	
	private int admissibleLabCharges;
	private int admissibleMedicinesCharges;
	private int admissibleRoomRent;
	private int admissibleOtCharges;
	private int admissibleOtConsumables;
	private int admissibleAnaesthesiaCharges;
	private int admissibleImplantsCharges;
	private int admissibleArtificialCharges;
	private int admissibleProcedure;
	private int admissibleSpecialNurse;
	private int admissibleMiscellaneousCharges;
	private int admissibleTotalAmount;
	private String noofVouchers;
	private String cghscardref;
	
	
	
	
	public String getNoofVouchers() {
		return noofVouchers;
	}
	public void setNoofVouchers(String noofVouchers) {
		this.noofVouchers = noofVouchers;
	}
	public int getAdmissibleTotalAmount() {
		return admissibleTotalAmount;
	}
	public void setAdmissibleTotalAmount(int admissibleTotalAmount) {
		this.admissibleTotalAmount = admissibleTotalAmount;
	}
	public int getAdmissibleLabCharges() {
		return admissibleLabCharges;
	}
	public void setAdmissibleLabCharges(int admissibleLabCharges) {
		this.admissibleLabCharges = admissibleLabCharges;
	}
	public int getAdmissibleMedicinesCharges() {
		return admissibleMedicinesCharges;
	}
	public void setAdmissibleMedicinesCharges(int admissibleMedicinesCharges) {
		this.admissibleMedicinesCharges = admissibleMedicinesCharges;
	}
	public int getAdmissibleRoomRent() {
		return admissibleRoomRent;
	}
	public void setAdmissibleRoomRent(int admissibleRoomRent) {
		this.admissibleRoomRent = admissibleRoomRent;
	}
	public int getAdmissibleOtCharges() {
		return admissibleOtCharges;
	}
	public void setAdmissibleOtCharges(int admissibleOtCharges) {
		this.admissibleOtCharges = admissibleOtCharges;
	}
	public int getAdmissibleOtConsumables() {
		return admissibleOtConsumables;
	}
	public void setAdmissibleOtConsumables(int admissibleOtConsumables) {
		this.admissibleOtConsumables = admissibleOtConsumables;
	}
	public int getAdmissibleAnaesthesiaCharges() {
		return admissibleAnaesthesiaCharges;
	}
	public void setAdmissibleAnaesthesiaCharges(int admissibleAnaesthesiaCharges) {
		this.admissibleAnaesthesiaCharges = admissibleAnaesthesiaCharges;
	}
	public int getAdmissibleImplantsCharges() {
		return admissibleImplantsCharges;
	}
	public void setAdmissibleImplantsCharges(int admissibleImplantsCharges) {
		this.admissibleImplantsCharges = admissibleImplantsCharges;
	}
	public int getAdmissibleArtificialCharges() {
		return admissibleArtificialCharges;
	}
	public void setAdmissibleArtificialCharges(int admissibleArtificialCharges) {
		this.admissibleArtificialCharges = admissibleArtificialCharges;
	}
	public int getAdmissibleProcedure() {
		return admissibleProcedure;
	}
	public void setAdmissibleProcedure(int admissibleProcedure) {
		this.admissibleProcedure = admissibleProcedure;
	}
	public int getAdmissibleSpecialNurse() {
		return admissibleSpecialNurse;
	}
	public void setAdmissibleSpecialNurse(int admissibleSpecialNurse) {
		this.admissibleSpecialNurse = admissibleSpecialNurse;
	}
	public int getAdmissibleMiscellaneousCharges() {
		return admissibleMiscellaneousCharges;
	}
	public void setAdmissibleMiscellaneousCharges(int admissibleMiscellaneousCharges) {
		this.admissibleMiscellaneousCharges = admissibleMiscellaneousCharges;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getSanctionNo() {
		return sanctionNo;
	}
	public void setSanctionNo(String sanctionNo) {
		this.sanctionNo = sanctionNo;
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
	public String getDvNo() {
		return dvNo;
	}
	public void setDvNo(String dvNo) {
		this.dvNo = dvNo;
	}
	public Date getDvDate() {
		return dvDate;
	}
	public void setDvDate(Date dvDate) {
		this.dvDate = dvDate;
	}
	public String getAccOfficer() {
		return accOfficer;
	}
	public void setAccOfficer(String accOfficer) {
		this.accOfficer = accOfficer;
	}
	public String getRequestForm() {
		return requestForm;
	}
	public void setRequestForm(String requestForm) {
		this.requestForm = requestForm;
	}
	public String getEmergencyForms() {
		return emergencyForms;
	}
	public void setEmergencyForms(String emergencyForms) {
		this.emergencyForms = emergencyForms;
	}
	public String getRepSig() {
		return repSig;
	}
	public void setRepSig(String repSig) {
		this.repSig = repSig;
	}
	public String getCdaAmount() {
		return cdaAmount;
	}
	public void setCdaAmount(String cdaAmount) {
		this.cdaAmount = cdaAmount;
	}
	public String getDisease() {
		return disease;
	}
	public void setDisease(String disease) {
		this.disease = disease;
	}
	public EmployeeBean getEmployeeDetails() {
		return employeeDetails;
	}
	public void setEmployeeDetails(EmployeeBean employeeDetails) {
		this.employeeDetails = employeeDetails;
	}
	public FamilyBean getFamilyDetails() {
		return familyDetails;
	}
	public void setFamilyDetails(FamilyBean familyDetails) {
		this.familyDetails = familyDetails;
	}
	public ReferralHospitalDTO getHospitalDetails() {
		return hospitalDetails;
	}
	public void setHospitalDetails(ReferralHospitalDTO hospitalDetails) {
		this.hospitalDetails = hospitalDetails;
	}
	public String getHospitalAddress() {
		return hospitalAddress;
	}
	public void setHospitalAddress(String hospitalAddress) {
		this.hospitalAddress = hospitalAddress;
	}
	public Date getAppliedDate() {
		return appliedDate;
	}
	public void setAppliedDate(Date appliedDate) {
		this.appliedDate = appliedDate;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getDesignation() {
		return designation;
	}
	public void setDesignation(int designation) {
		this.designation = designation;
	}
	public int getDepartment() {
		return department;
	}
	public void setDepartment(int department) {
		this.department = department;
	}
	public String getCghsCardFile() {
		return cghsCardFile;
	}
	public void setCghsCardFile(String cghsCardFile) {
		this.cghsCardFile = cghsCardFile;
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
	public String getReimbursementFile() {
		return reimbursementFile;
	}
	public void setReimbursementFile(String reimbursementFile) {
		this.reimbursementFile = reimbursementFile;
	}
	
	public String getNonCghsReason() {
		return nonCghsReason;
	}
	public void setNonCghsReason(String nonCghsReason) {
		this.nonCghsReason = nonCghsReason;
	}
	public String getOtherAddress() {
		return otherAddress;
	}
	public void setOtherAddress(String otherAddress) {
		this.otherAddress = otherAddress;
	}
	public String getPatientIllAddress() {
		return patientIllAddress;
	}
	public void setPatientIllAddress(String patientIllAddress) {
		this.patientIllAddress = patientIllAddress;
	}
	public String getEmergencyDetails() {
		return emergencyDetails;
	}
	public void setEmergencyDetails(String emergencyDetails) {
		this.emergencyDetails = emergencyDetails;
	}
	public String getFamilyMemberId() {
		return familyMemberId;
	}
	public void setFamilyMemberId(String familyMemberId) {
		this.familyMemberId = familyMemberId;
	}
	public String getAmountClaimed() {
		return amountClaimed;
	}
	public void setAmountClaimed(String amountClaimed) {
		this.amountClaimed = amountClaimed;
	}
	public int getReferralHospitalId() {
		return referralHospitalId;
	}
	public void setReferralHospitalId(int referralHospitalId) {
		this.referralHospitalId = referralHospitalId;
	}
	public Date getDischargeDate() {
		return dischargeDate;
	}
	public void setDischargeDate(Date dischargeDate) {
		this.dischargeDate = dischargeDate;
	}
	public Date getAdmissionDate() {
		return admissionDate;
	}
	public void setAdmissionDate(Date admissionDate) {
		this.admissionDate = admissionDate;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getRequestID() {
		return requestID;
	}
	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
	public String getSfID() {
		return sfID;
	}
	public void setSfID(String sfID) {
		this.sfID = sfID;
	}
	public String getCghscardref() {
		return cghscardref;
	}
	public void setCghscardref(String cghscardref) {
		this.cghscardref = cghscardref;
	}
	
	
}
