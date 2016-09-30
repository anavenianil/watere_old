package com.callippus.web.leave.beans.request;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.leave.dto.ErpAvailableLeaveSaveDTO;
import com.callippus.web.leave.dto.ErpAvailableLeaveTypesDTO;
import com.callippus.web.ltc.dto.LeaveYearsDTO;


public class LeaveWaterApplicationBean {
	private String dateCheck;
	private String param;
	private String sfID;
	private String type;
	private int annualLeavePermanent;
	private int annualLeaveContract;
	private String annualLeaveGender;
	private int sickLeavePermanent;
	private int sickLeaveContract;
	private String sickLeaveGender;
	private int maternityLeavePermanent;
	private int maternityLeaveContract;
	private String maternityLeaveGender;	
	private int paternityLeavePermanent;
	private int paternityLeaveContract;
	private String paternityLeaveGender;
	private String leaveYear;
	private List<LeaveYearsDTO> setleaveYearsList;
	private String result;
	private String requestId;
	private String requestID;
	private String remarks;
	
	
	private String leaveType;
	private Date fromDate;
	private Date toDate;
	private String noOfDays;
	
	private String reason;
	
	private String contactNo;
	
	private EmployeeBean empDetailsList;
	
	private String sfid;
	private String sfId;
	
	private HttpSession session;
	
	private String eorN;
	
	private String prescriptionYorN;
	
	private String prescriptiondoc;
	//private MultipartFile prescriptiondoc;
	
	private MultipartFile prescriptionFile;
	
	private String reasonCancellation;
	
	private String cancelRequestId;
	
	private int leaveStatus;
	
	private String amendRequestId;
	
	
	private String leaveTypeCopy;
	
	private String prescriptionDOC;
	
	
	
	
	
	public String getPrescriptiondoc() {
		return prescriptiondoc;
	}
	public void setPrescriptiondoc(String prescriptiondoc) {
		this.prescriptiondoc = prescriptiondoc;
	}
	public String getPrescriptionDOC() {
		return prescriptionDOC;
	}
	public void setPrescriptionDOC(String prescriptionDOC) {
		this.prescriptionDOC = prescriptionDOC;
	}
	public MultipartFile getPrescriptionFile() {
		return prescriptionFile;
	}
	public void setPrescriptionFile(MultipartFile prescriptionFile) {
		this.prescriptionFile = prescriptionFile;
	}

	

	
	
	public String getLeaveTypeCopy() {
		return leaveTypeCopy;
	}
	public void setLeaveTypeCopy(String leaveTypeCopy) {
		this.leaveTypeCopy = leaveTypeCopy;
	}
	/*public MultipartFile getPrescriptiondoc() {
		return prescriptiondoc;
	}
	public void setPrescriptiondoc(MultipartFile prescriptiondoc) {
		this.prescriptiondoc = prescriptiondoc;
	}*/
	public String getAmendRequestId() {
		return amendRequestId;
	}
	public void setAmendRequestId(String amendRequestId) {
		this.amendRequestId = amendRequestId;
	}
	public int getLeaveStatus() {
		return leaveStatus;
	}
	public void setLeaveStatus(int leaveStatus) {
		this.leaveStatus = leaveStatus;
	}
	public String getCancelRequestId() {
		return cancelRequestId;
	}
	public void setCancelRequestId(String cancelRequestId) {
		this.cancelRequestId = cancelRequestId;
	}
	public String getReasonCancellation() {
		return reasonCancellation;
	}
	public void setReasonCancellation(String reasonCancellation) {
		this.reasonCancellation = reasonCancellation;
	}
	public String getPrescriptionYorN() {
		return prescriptionYorN;
	}
	public void setPrescriptionYorN(String prescriptionYorN) {
		this.prescriptionYorN = prescriptionYorN;
	}
	public String getDateCheck() {
		return dateCheck;
	}
	public void setDateCheck(String dateCheck) {
		this.dateCheck = dateCheck;
	}
	public String getEorN() {
		return eorN;
	}
	public void setEorN(String eorN) {
		this.eorN = eorN;
	}
	public HttpSession getSession() {
		return session;
	}
	public void setSession(HttpSession session) {
		this.session = session;
	}
	private List<ErpAvailableLeaveTypesDTO> erpAvailTypeleavesList;
	
	private String designation;
	private String phnNo;
	public List<ErpAvailableLeaveTypesDTO> setErpAppliedLeavesList;
	
	 private ErpAvailableLeaveSaveDTO erpAvailableLeaveSaveDTO;
	 
	 
	 
	
	
	public ErpAvailableLeaveSaveDTO getErpAvailableLeaveSaveDTO() {
		return erpAvailableLeaveSaveDTO;
	}
	public void setErpAvailableLeaveSaveDTO(
			ErpAvailableLeaveSaveDTO erpAvailableLeaveSaveDTO) {
		this.erpAvailableLeaveSaveDTO = erpAvailableLeaveSaveDTO;
	}
	public List<ErpAvailableLeaveTypesDTO> getSetErpAppliedLeavesList() {
		return setErpAppliedLeavesList;
	}
	public void setSetErpAppliedLeavesList(
			List<ErpAvailableLeaveTypesDTO> setErpAppliedLeavesList) {
		this.setErpAppliedLeavesList = setErpAppliedLeavesList;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getPhnNo() {
		return phnNo;
	}
	public void setPhnNo(String phnNo) {
		this.phnNo = phnNo;
	}
	public List<ErpAvailableLeaveTypesDTO> getErpAvailTypeleavesList() {
		return erpAvailTypeleavesList;
	}
	public void setErpAvailTypeleavesList(
			List<ErpAvailableLeaveTypesDTO> erpAvailTypeleavesList) {
		this.erpAvailTypeleavesList = erpAvailTypeleavesList;
	}
	public EmployeeBean getEmpDetailsList() {
		return empDetailsList;
	}
	public void setEmpDetailsList(EmployeeBean empDetailsList) {
		this.empDetailsList = empDetailsList;
	}
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public String getSfId() {
		return sfId;
	}
	public void setSfId(String sfId) {
		this.sfId = sfId;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getRequestID() {
		return requestID;
	}
	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getLeaveYear() {
		return leaveYear;
	}
	public void setLeaveYear(String leaveYear) {
		this.leaveYear = leaveYear;
	}
	public List<LeaveYearsDTO> getSetleaveYearsList() {
		return setleaveYearsList;
	}
	public void setSetleaveYearsList(List<LeaveYearsDTO> setleaveYearsList) {
		this.setleaveYearsList = setleaveYearsList;
	}
	
	
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getSfID() {
		return sfID;
	}
	public void setSfID(String sfID) {
		this.sfID = sfID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getAnnualLeavePermanent() {
		return annualLeavePermanent;
	}
	public void setAnnualLeavePermanent(int annualLeavePermanent) {
		this.annualLeavePermanent = annualLeavePermanent;
	}
	public int getAnnualLeaveContract() {
		return annualLeaveContract;
	}
	public void setAnnualLeaveContract(int annualLeaveContract) {
		this.annualLeaveContract = annualLeaveContract;
	}
	
	public int getSickLeavePermanent() {
		return sickLeavePermanent;
	}
	public void setSickLeavePermanent(int sickLeavePermanent) {
		this.sickLeavePermanent = sickLeavePermanent;
	}
	public int getSickLeaveContract() {
		return sickLeaveContract;
	}
	public void setSickLeaveContract(int sickLeaveContract) {
		this.sickLeaveContract = sickLeaveContract;
	}
	
	public int getMaternityLeavePermanent() {
		return maternityLeavePermanent;
	}
	public void setMaternityLeavePermanent(int maternityLeavePermanent) {
		this.maternityLeavePermanent = maternityLeavePermanent;
	}
	public int getMaternityLeaveContract() {
		return maternityLeaveContract;
	}
	public void setMaternityLeaveContract(int maternityLeaveContract) {
		this.maternityLeaveContract = maternityLeaveContract;
	}

	public int getPaternityLeavePermanent() {
		return paternityLeavePermanent;
	}
	public void setPaternityLeavePermanent(int paternityLeavePermanent) {
		this.paternityLeavePermanent = paternityLeavePermanent;
	}
	public int getPaternityLeaveContract() {
		return paternityLeaveContract;
	}
	public void setPaternityLeaveContract(int paternityLeaveContract) {
		this.paternityLeaveContract = paternityLeaveContract;
	}
	public String getAnnualLeaveGender() {
		return annualLeaveGender;
	}
	public void setAnnualLeaveGender(String annualLeaveGender) {
		this.annualLeaveGender = annualLeaveGender;
	}
	public String getSickLeaveGender() {
		return sickLeaveGender;
	}
	public void setSickLeaveGender(String sickLeaveGender) {
		this.sickLeaveGender = sickLeaveGender;
	}
	public String getMaternityLeaveGender() {
		return maternityLeaveGender;
	}
	public void setMaternityLeaveGender(String maternityLeaveGender) {
		this.maternityLeaveGender = maternityLeaveGender;
	}
	public String getPaternityLeaveGender() {
		return paternityLeaveGender;
	}
	public void setPaternityLeaveGender(String paternityLeaveGender) {
		this.paternityLeaveGender = paternityLeaveGender;
	}
	
	
	
	
	
	
}
