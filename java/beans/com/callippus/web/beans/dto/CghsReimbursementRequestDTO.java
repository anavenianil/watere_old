package com.callippus.web.beans.dto;

import java.util.Date;

import com.callippus.web.beans.family.FamilyBean;
import com.callippus.web.beans.requests.CGHSRequestProcessBean;


public class CghsReimbursementRequestDTO {

	private String requestID;
	private String referrenceRequestID;
	
	private Date admissionDate;
	private String advanceDetails;
	private String permissionDetails;
	private String amountClaimed;
	private Date dischargeDate;
	private String hospitalDetails;
	private int status;
	private Date appliedDate;
	private String ipAddress;
	private String approvedBy;
	private Date approvedDate;
	private String requestType;
	private String prescriptionFile;
	private String permissionFile;
	private String estimationFile;
	private String contingentFile;
	private String refundChequeFile;
	private String med2004File;
	private String checkListFile;
	private String essentialityFile;
	private String medicalBillsFile;
	private String dischargeSummeryFile;
	private String cghsCardFile;
	private String message;
	private String type;
	private String requestFormFile;
	private String sfID ;
	private CGHSRequestProcessBean cghsRequestDetails;
	private String empName;
	private String cghsCard;
	private String disease;
	private String address;
	private String hospitalName;
	private String wardName;
	private FamilyBean familyMemberDetails;
	private String familyMembetId;
	private String cdaAmount;
	private String repSig;
	private String dvNo;      
	private Date dvDate;      
	private String accOfficer;
	
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
	private String billNo;
	private String sanctionNo;
	
	private int admissibleLabCharges;
	private int admissibleMedicinesCharges;
	private int admissibleConsultationFees;
	private int admissibleDisposableCharges;
	private int admissibleSpecialDevices;
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
	public int getAdmissibleConsultationFees() {
		return admissibleConsultationFees;
	}
	public void setAdmissibleConsultationFees(int admissibleConsultationFees) {
		this.admissibleConsultationFees = admissibleConsultationFees;
	}
	public int getAdmissibleDisposableCharges() {
		return admissibleDisposableCharges;
	}
	public void setAdmissibleDisposableCharges(int admissibleDisposableCharges) {
		this.admissibleDisposableCharges = admissibleDisposableCharges;
	}
	public int getAdmissibleSpecialDevices() {
		return admissibleSpecialDevices;
	}
	public void setAdmissibleSpecialDevices(int admissibleSpecialDevices) {
		this.admissibleSpecialDevices = admissibleSpecialDevices;
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
	public String getFamilyMembetId() {
		return familyMembetId;
	}
	public void setFamilyMembetId(String familyMembetId) {
		this.familyMembetId = familyMembetId;
	}
	public FamilyBean getFamilyMemberDetails() {
		return familyMemberDetails;
	}
	public void setFamilyMemberDetails(FamilyBean familyMemberDetails) {
		this.familyMemberDetails = familyMemberDetails;
	}
	public String getRequestID() {
		return requestID;
	}
	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
	public String getReferrenceRequestID() {
		return referrenceRequestID;
	}
	public void setReferrenceRequestID(String referrenceRequestID) {
		this.referrenceRequestID = referrenceRequestID;
	}
	public Date getAdmissionDate() {
		return admissionDate;
	}
	public void setAdmissionDate(Date admissionDate) {
		this.admissionDate = admissionDate;
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
	public Date getDischargeDate() {
		return dischargeDate;
	}
	public void setDischargeDate(Date dischargeDate) {
		this.dischargeDate = dischargeDate;
	}
	public String getHospitalDetails() {
		return hospitalDetails;
	}
	public void setHospitalDetails(String hospitalDetails) {
		this.hospitalDetails = hospitalDetails;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	public String getRefundChequeFile() {
		return refundChequeFile;
	}
	public void setRefundChequeFile(String refundChequeFile) {
		this.refundChequeFile = refundChequeFile;
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
	public String getEssentialityFile() {
		return essentialityFile;
	}
	public void setEssentialityFile(String essentialityFile) {
		this.essentialityFile = essentialityFile;
	}
	public String getMedicalBillsFile() {
		return medicalBillsFile;
	}
	public void setMedicalBillsFile(String medicalBillsFile) {
		this.medicalBillsFile = medicalBillsFile;
	}
	public String getDischargeSummeryFile() {
		return dischargeSummeryFile;
	}
	public void setDischargeSummeryFile(String dischargeSummeryFile) {
		this.dischargeSummeryFile = dischargeSummeryFile;
	}
	public String getCghsCardFile() {
		return cghsCardFile;
	}
	public void setCghsCardFile(String cghsCardFile) {
		this.cghsCardFile = cghsCardFile;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRequestFormFile() {
		return requestFormFile;
	}
	public void setRequestFormFile(String requestFormFile) {
		this.requestFormFile = requestFormFile;
	}
	public String getSfID() {
		return sfID;
	}
	public void setSfID(String sfID) {
		this.sfID = sfID;
	}
	public CGHSRequestProcessBean getCghsRequestDetails() {
		return cghsRequestDetails;
	}
	public void setCghsRequestDetails(CGHSRequestProcessBean cghsRequestDetails) {
		this.cghsRequestDetails = cghsRequestDetails;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getCghsCard() {
		return cghsCard;
	}
	public void setCghsCard(String cghsCard) {
		this.cghsCard = cghsCard;
	}
	public String getDisease() {
		return disease;
	}
	public void setDisease(String disease) {
		this.disease = disease;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	public String getWardName() {
		return wardName;
	}
	public void setWardName(String wardName) {
		this.wardName = wardName;
	}
	public String getCghscardref() {
		return cghscardref;
	}
	public void setCghscardref(String cghscardref) {
		this.cghscardref = cghscardref;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	}
