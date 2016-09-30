package com.callippus.web.cghs.beans.request;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.callippus.web.beans.dto.CghsAdvanceRequestDTO;
import com.callippus.web.beans.requests.CGHSRequestProcessBean;
import com.callippus.web.cghs.beans.dto.FamilyMemberDetailsDTO;
import com.callippus.web.cghs.beans.dto.ReferralHospitalDTO;
import com.callippus.web.cghs.beans.dto.RequestTypeDTO;
import com.callippus.web.cghs.beans.dto.SingingAuthorityDTO;
import com.callippus.web.hrdg.file.UploadAndDownloadBean;

/**
 * @author Administrator
 *
 */
public class CghsRequestBean {
	
	private String param;
	
	private String type;
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
	private String requestID;
	private int roleId;
	private String result;
	private int admissibleAmount;
	
	
	
	
	public int getAdmissibleAmount() {
		return admissibleAmount;
	}
	public void setAdmissibleAmount(int admissibleAmount) {
		this.admissibleAmount = admissibleAmount;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRequestID() {
		return requestID;
	}
	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	private String referenceSpecialist;
	private String prescriptionFileName;
	
	private String permissionFileName;
	
	private String cghsCardFileName;
	
	private String estimationFileName;
	private String refundChequeFileName;
	private String dischargeSummeryFileName;
	private String medicalBillsFileName;
	private String reimbursementFileName;
	
	private String referenceNumber;
	private String approvedBy;
	private String prescriptionReceivedFlag;
	private String name;
	private String sfID;
	private String relation;
	private String gender;
	private String age;
	private String dob;
	private String id;
	private String employeeName;
	private ArrayList<FamilyMemberDetailsDTO> familyMemberList;
	private List<ReferralHospitalDTO> referralHospitalList;
	
	
	private List<RequestTypeDTO> requestTypeList;
	private String requestId;
	private int status;
	private List<CghsRequestBean> reimbursementMemberList;
	private String wardName;
	private String permissionDetails;
	private String message;
	
	private MultipartFile prescriptionFile;
	private MultipartFile permissionFile;
	private MultipartFile estimationFile;
	private MultipartFile cghsCardFile;
	private MultipartFile refundChequeFile;
	private MultipartFile dischargeSummeryFile;
	private MultipartFile medicalBillsFile;
	private MultipartFile reimbursementFile;
	
	

	private Date admissionDate;
	private String advanceDetails;
	
	private String amountClaimed;
	private Date dischargeDate;
	private String hospitalDetails;
	private String statusName;
	
	private String requestFile;
	private String familyMembetId;
	
	private CGHSRequestProcessBean cghsRequestDetails;
	private CghsRequestBean cghsRequestBean;
	private String emergencyDetails;
	private String patientIllAddress;
	private String otherAddress;
	private String amountInWords;
	private String nonCghsReason;
	private String requestType;
	private String hospitalAddress;
	private String todaysDate;
	private Date estimationDate;
	private String estimationAmount;
	private String InPatientNumber;
	private CghsAdvanceRequestDTO cghsAdvanceDetails;
	private String settleAmount;
	private String cdaAmount;
	private List<CghsRequestBean> advanceList;
	private List<CghsRequestBean> settlementList;
	private List<CghsRequestBean> reimbursementList;
	private List<CghsRequestBean> nonCghsReimList;
	private List<CghsRequestBean> emergencyList;
	private String billNo;
	private String sanctionNo;
	private String issuedAmount;
	private String jsonValues;
	private String repSig;
	private String dvNo;
	private String dvDate;
	private String accOfficer;
	private List<SingingAuthorityDTO> accountOfficerList;
	private List<SingingAuthorityDTO> cfaOfficerList;
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
	private String offlineSFID;
	private String requestedBy;
	
	private String settlement;
	private String historyID;
	private String statusMsg;
	private String back;
	private List<CghsRequestBean> cdaadvanceList;
	private List<CghsRequestBean> cdasettlementList;
	private List<CghsRequestBean> cdareimbursementList;
	private List<CghsRequestBean> cdanonCghsReimList;
	private List<CghsRequestBean> cdaemergencyList;
	

	public List<CghsRequestBean> getCdaadvanceList() {
		return cdaadvanceList;
	}
	public void setCdaadvanceList(List<CghsRequestBean> cdaadvanceList) {
		this.cdaadvanceList = cdaadvanceList;
	}
	public List<CghsRequestBean> getCdasettlementList() {
		return cdasettlementList;
	}
	public void setCdasettlementList(List<CghsRequestBean> cdasettlementList) {
		this.cdasettlementList = cdasettlementList;
	}
	public List<CghsRequestBean> getCdareimbursementList() {
		return cdareimbursementList;
	}
	public void setCdareimbursementList(List<CghsRequestBean> cdareimbursementList) {
		this.cdareimbursementList = cdareimbursementList;
	}
	public List<CghsRequestBean> getCdanonCghsReimList() {
		return cdanonCghsReimList;
	}
	public void setCdanonCghsReimList(List<CghsRequestBean> cdanonCghsReimList) {
		this.cdanonCghsReimList = cdanonCghsReimList;
	}
	public List<CghsRequestBean> getCdaemergencyList() {
		return cdaemergencyList;
	}
	public void setCdaemergencyList(List<CghsRequestBean> cdaemergencyList) {
		this.cdaemergencyList = cdaemergencyList;
	}

	private UploadAndDownloadBean uploadBean;
	private String cghscardref;

	
	
	public UploadAndDownloadBean getUploadBean() {
		return uploadBean;
	}
	public void setUploadBean(UploadAndDownloadBean uploadBean) {
		this.uploadBean = uploadBean;
	}
	public String getSettlement() {
		return settlement;
	}
	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}
	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}
	public String getOfflineSFID() {
		return offlineSFID;
	}
	public void setOfflineSFID(String offlineSFID) {
		this.offlineSFID = offlineSFID;
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
	public List<SingingAuthorityDTO> getAccountOfficerList() {
		return accountOfficerList;
	}
	public void setAccountOfficerList(List<SingingAuthorityDTO> accountOfficerList) {
		this.accountOfficerList = accountOfficerList;
	}
	public List<SingingAuthorityDTO> getCfaOfficerList() {
		return cfaOfficerList;
	}
	public void setCfaOfficerList(List<SingingAuthorityDTO> cfaOfficerList) {
		this.cfaOfficerList = cfaOfficerList;
	}
	public String getAccOfficer() {
		return accOfficer;
	}
	public void setAccOfficer(String accOfficer) {
		this.accOfficer = accOfficer;
	}
	public String getDvNo() {
		return dvNo;
	}
	public void setDvNo(String dvNo) {
		this.dvNo = dvNo;
	}
	public String getDvDate() {
		return dvDate;
	}
	public void setDvDate(String dvDate) {
		this.dvDate = dvDate;
	}
	public String getRepSig() {
		return repSig;
	}
	public void setRepSig(String repSig) {
		this.repSig = repSig;
	}
	public List<CghsRequestBean> getEmergencyList() {
		return emergencyList;
	}
	public void setEmergencyList(List<CghsRequestBean> emergencyList) {
		this.emergencyList = emergencyList;
	}
	public List<CghsRequestBean> getNonCghsReimList() {
		return nonCghsReimList;
	}
	public void setNonCghsReimList(List<CghsRequestBean> nonCghsReimList) {
		this.nonCghsReimList = nonCghsReimList;
	}
	public String getJsonValues() {
		return jsonValues;
	}
	public void setJsonValues(String jsonValues) {
		this.jsonValues = jsonValues;
	}
	public String getIssuedAmount() {
		return issuedAmount;
	}
	public void setIssuedAmount(String issuedAmount) {
		this.issuedAmount = issuedAmount;
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
	public String getCdaAmount() {
		return cdaAmount;
	}
	public void setCdaAmount(String cdaAmount) {
		this.cdaAmount = cdaAmount;
	}
	public List<CghsRequestBean> getSettlementList() {
		return settlementList;
	}
	public void setSettlementList(List<CghsRequestBean> settlementList) {
		this.settlementList = settlementList;
	}
	public List<CghsRequestBean> getReimbursementList() {
		return reimbursementList;
	}
	public void setReimbursementList(List<CghsRequestBean> reimbursementList) {
		this.reimbursementList = reimbursementList;
	}
	public List<CghsRequestBean> getAdvanceList() {
		return advanceList;
	}
	public void setAdvanceList(List<CghsRequestBean> advanceList) {
		this.advanceList = advanceList;
	}
	public String getSettleAmount() {
		return settleAmount;
	}
	public void setSettleAmount(String settleAmount) {
		this.settleAmount = settleAmount;
	}
	public CghsAdvanceRequestDTO getCghsAdvanceDetails() {
		return cghsAdvanceDetails;
	}
	public void setCghsAdvanceDetails(CghsAdvanceRequestDTO cghsAdvanceDetails) {
		this.cghsAdvanceDetails = cghsAdvanceDetails;
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
	public String getTodaysDate() {
		return todaysDate;
	}
	public void setTodaysDate(String todaysDate) {
		this.todaysDate = todaysDate;
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
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
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
	public String getAmountInWords() {
		return amountInWords;
	}
	public void setAmountInWords(String amountInWords) {
		this.amountInWords = amountInWords;
	}
	
	public MultipartFile getReimbursementFile() {
		return reimbursementFile;
	}
	public void setReimbursementFile(MultipartFile reimbursementFile) {
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
	public CghsRequestBean getCghsRequestBean() {
		return cghsRequestBean;
	}
	public void setCghsRequestBean(CghsRequestBean cghsRequestBean) {
		this.cghsRequestBean = cghsRequestBean;
	}
	public CGHSRequestProcessBean getCghsRequestDetails() {
		return cghsRequestDetails;
	}
	public void setCghsRequestDetails(CGHSRequestProcessBean cghsRequestDetails) {
		this.cghsRequestDetails = cghsRequestDetails;
	}
	public String getRequestFile() {
		return requestFile;
	}
	public void setRequestFile(String requestFile) {
		this.requestFile = requestFile;
	}
	public String getPermissionFileName() {
		return permissionFileName;
	}
	public void setPermissionFileName(String permissionFileName) {
		this.permissionFileName = permissionFileName;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getAdvanceDetails() {
		return advanceDetails;
	}
	public void setAdvanceDetails(String advanceDetails) {
		this.advanceDetails = advanceDetails;
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
	public MultipartFile getRefundChequeFile() {
		return refundChequeFile;
	}
	public void setRefundChequeFile(MultipartFile refundChequeFile) {
		this.refundChequeFile = refundChequeFile;
	}
	public MultipartFile getDischargeSummeryFile() {
		return dischargeSummeryFile;
	}
	public void setDischargeSummeryFile(MultipartFile dischargeSummeryFile) {
		this.dischargeSummeryFile = dischargeSummeryFile;
	}
	public MultipartFile getMedicalBillsFile() {
		return medicalBillsFile;
	}
	public void setMedicalBillsFile(MultipartFile medicalBillsFile) {
		this.medicalBillsFile = medicalBillsFile;
	}
	public MultipartFile getPrescriptionFile() {
		return prescriptionFile;
	}
	public void setPrescriptionFile(MultipartFile prescriptionFile) {
		this.prescriptionFile = prescriptionFile;
	}
	public MultipartFile getPermissionFile() {
		return permissionFile;
	}
	public void setPermissionFile(MultipartFile permissionFile) {
		this.permissionFile = permissionFile;
	}
	public MultipartFile getEstimationFile() {
		return estimationFile;
	}
	public void setEstimationFile(MultipartFile estimationFile) {
		this.estimationFile = estimationFile;
	}
	public MultipartFile getCghsCardFile() {
		return cghsCardFile;
	}
	public void setCghsCardFile(MultipartFile cghsCardFile) {
		this.cghsCardFile = cghsCardFile;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getPermissionDetails() {
		return permissionDetails;
	}
	public void setPermissionDetails(String permissionDetails) {
		this.permissionDetails = permissionDetails;
	}
	public String getWardName() {
		return wardName;
	}
	public void setWardName(String wardName) {
		this.wardName = wardName;
	}
	public List<CghsRequestBean> getReimbursementMemberList() {
		return reimbursementMemberList;
	}
	public void setReimbursementMemberList(List<CghsRequestBean> reimbursementMemberList) {
		this.reimbursementMemberList = reimbursementMemberList;
	}

	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getReferenceNumber() {
		return referenceNumber;
	}
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public String getPrescriptionReceivedFlag() {
		return prescriptionReceivedFlag;
	}
	public void setPrescriptionReceivedFlag(String prescriptionReceivedFlag) {
		this.prescriptionReceivedFlag = prescriptionReceivedFlag;
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
	//For DataBase Uploading
	public String getEstimationFileName() {
		return estimationFileName;
	}
	public void setEstimationFileName(String estimationFileName) {
		this.estimationFileName = estimationFileName;
	}

	public String getPrescriptionFileName() {
		return prescriptionFileName;
	}
	public void setPrescriptionFileName(String prescriptionFileName) {
		this.prescriptionFileName = prescriptionFileName;
	}
	public String getCghsCardFileName() {
		return cghsCardFileName;
	}
	public void setCghsCardFileName(String cghsCardFileName) {
		this.cghsCardFileName = cghsCardFileName;
	}
	public String getRefundChequeFileName() {
		return refundChequeFileName;
	}
	public void setRefundChequeFileName(String refundChequeFileName) {
		this.refundChequeFileName = refundChequeFileName;
	}
	public String getDischargeSummeryFileName() {
		return dischargeSummeryFileName;
	}
	public void setDischargeSummeryFileName(String dischargeSummeryFileName) {
		this.dischargeSummeryFileName = dischargeSummeryFileName;
	}
	public String getMedicalBillsFileName() {
		return medicalBillsFileName;
	}
	public void setMedicalBillsFileName(String medicalBillsFileName) {
		this.medicalBillsFileName = medicalBillsFileName;
	}
	public String getReimbursementFileName() {
		return reimbursementFileName;
	}
	public void setReimbursementFileName(String reimbursementFileName) {
		this.reimbursementFileName = reimbursementFileName;
	}
	
    //
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getSfID() {
		return sfID;
	}
	public void setSfID(String sfID) {
		this.sfID = sfID;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public ArrayList<FamilyMemberDetailsDTO> getFamilyMemberList() {
		return familyMemberList;
	}
	public void setFamilyMemberList(
			ArrayList<FamilyMemberDetailsDTO> familyMemberList) {
		this.familyMemberList = familyMemberList;
	}
	public List<ReferralHospitalDTO> getReferralHospitalList() {
		return referralHospitalList;
	}
	public void setReferralHospitalList(
			List<ReferralHospitalDTO> referralHospitalList) {
		this.referralHospitalList = referralHospitalList;
	}
	public List<RequestTypeDTO> getRequestTypeList() {
		return requestTypeList;
	}
	public void setRequestTypeList(List<RequestTypeDTO> requestTypeList) {
		this.requestTypeList = requestTypeList;
	}
	
	//
	public int getOtherHospitalId() {
		return otherHospitalId;
	}
	public String getCghscardref() {
		return cghscardref;
	}
	public void setCghscardref(String cghscardref) {
		this.cghscardref = cghscardref;
	}
	
	public String getHistoryID() {
		return historyID;
	}
	public void setHistoryID(String historyID) {
		this.historyID = historyID;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	public String getBack() {
		return back;
	}
	public void setBack(String back) {
		this.back = back;
	}
	
	//
	
	

}
