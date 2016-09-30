package com.callippus.web.beans.workflowmapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.callippus.web.beans.MT.MTRequestDetailsDTO;
import com.callippus.web.beans.dto.CDADetailsDTO;
import com.callippus.web.beans.dto.CghsAdvanceRequestDTO;
import com.callippus.web.beans.dto.CghsEmergencyRequestDTO;
import com.callippus.web.beans.dto.CghsReimbursementRequestDTO;
import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.DoPartDTO;
import com.callippus.web.beans.dto.EmployeeClaimDetailsDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.MRODetailsDTO;
import com.callippus.web.beans.dto.MROPaymentDetailsDTO;
import com.callippus.web.beans.dto.OrgInstanceDTO;
import com.callippus.web.beans.dto.OrganizationsDTO;
import com.callippus.web.beans.dto.RequestsDTO;
import com.callippus.web.beans.dto.TadaProjectDirectorsDTO;
import com.callippus.web.beans.dto.TxnDetailsDTO;
import com.callippus.web.beans.dto.WorkFlowMasterDTO;
import com.callippus.web.beans.dto.WorkFlowRelationDTO;
import com.callippus.web.beans.dto.WorkFlowRelationShipDTO;
import com.callippus.web.beans.dto.WorkflowDependentDTO;
import com.callippus.web.beans.dto.WorkflowDynamicTablesDTO;
import com.callippus.web.beans.dto.WorkflowStageMasterDTO;
import com.callippus.web.beans.higherQualification.dto.HQRequestDTO;
import com.callippus.web.beans.nominee.NomineeBean;
import com.callippus.web.beans.passport.PassportApplicationDetailsDTO;
import com.callippus.web.beans.quarter.QuarterRequestBean;
import com.callippus.web.beans.quarterType.QuarterTypeBean;
import com.callippus.web.beans.requests.AlertMessageDTO;
import com.callippus.web.beans.requests.CGHSRequestProcessBean;
import com.callippus.web.beans.requests.ConvertLeaveRequestBean;
import com.callippus.web.beans.requests.DoPartBean;
import com.callippus.web.beans.requests.FPARequestProcessBean;
import com.callippus.web.beans.requests.LeaveRequestBean;
import com.callippus.web.beans.requests.LoanHBARequestProcessBean;
import com.callippus.web.beans.requests.LoanRequestProcessBean;
import com.callippus.web.beans.requests.RequestCommonBean;
import com.callippus.web.beans.telephone.TelePhoneBillBean;
import com.callippus.web.beans.telephone.TelephoneBillClaimDetailsDTO;
import com.callippus.web.beans.telephone.TelephoneDesigEligibilityDetailsDTO;
import com.callippus.web.beans.transfer.EmpTransferBean;
import com.callippus.web.beans.tutionFee.TutionFeeBean;
import com.callippus.web.beans.tutionFee.TutionFeeClaimDetailsDTO;
import com.callippus.web.hrdg.training.beans.dto.TrainingCirculationDTO;
import com.callippus.web.hrdg.training.request.beans.dto.TrainingNominationDTO;
import com.callippus.web.leave.dto.ErpAvailableLeaveSaveDTO;
import com.callippus.web.leave.dto.LeaveRequestExceptionsDTO;
import com.callippus.web.loan.beans.dto.ErpLoanRequestDTO;
import com.callippus.web.loan.beans.dto.LoanHBAPaymentDTO;
import com.callippus.web.loan.beans.dto.LoanPaymentDTO;
import com.callippus.web.ltc.beans.application.LtcApplicationBean;
import com.callippus.web.ltc.dto.LTCWaterRequestDTO;
import com.callippus.web.ltc.dto.LtcAdvanceRequestDTO;
import com.callippus.web.ltc.dto.LtcApprovalRequestDTO;
import com.callippus.web.ltc.dto.LtcJourneyDetailsDTO;
import com.callippus.web.ltc.dto.LtcPenalMasterDTO;
import com.callippus.web.mmg.cashbuildup.beans.DemandRequestBean;
import com.callippus.web.mmg.cashbuildup.beans.InvoiceRequestBean;
import com.callippus.web.mmg.cashbuildup.beans.VoucherRequestBean;
import com.callippus.web.tada.dto.CityTypeDTO;
import com.callippus.web.tada.dto.TadaApprovalRequestDTO;
import com.callippus.web.tada.dto.TadaDaNewDetailsDTO;
import com.callippus.web.tada.dto.TadaDetailsDTO;
import com.callippus.web.tada.dto.TadaTdAccDetDayRepDTO;
import com.callippus.web.tada.dto.TadaTdAdvanceRequestDTO;
import com.callippus.web.tada.dto.TadaTdDaNewAccDetailsDTO;
import com.callippus.web.tada.dto.TadaTdDaNewDetailsDTO;
import com.callippus.web.tada.dto.TadaTdDaNewFoodDetailsDTO;
import com.callippus.web.tada.dto.TadaTdDaOldDetailsDTO;
import com.callippus.web.tada.dto.TadaTdJourneyDTO;
import com.callippus.web.tada.dto.TadaTdLocalRMADetailsDTO;
import com.callippus.web.tada.dto.TadaTdRMADailyDTO;
import com.callippus.web.tada.dto.TadaTdRMADayDTO;
import com.callippus.web.tada.dto.TadaTdRMAKmDTO;
import com.callippus.web.tada.dto.TadaTdReqJourneyDTO;
import com.callippus.web.tada.dto.TadaTdTotalFoodClaimDTO;
import com.callippus.web.tada.dto.TadaWaterApprovalRequestDTO;
@SuppressWarnings("unchecked")
public class WorkFlowMappingBean {

	private String param;
	private String type;
	private String cghsadvanceRequestId;
	private int cghsadvHistoryID;
	private String cghssetlementHistoryID;
	private String cghssetlementRequestId;
	private String cghsreque;
	private String requestadvlink;
	private List instanceList;
	private List requestList;
	private List alertList;
	private List<TadaProjectDirectorsDTO> prjlist;
	private List workflowList;
	private List allList;
	private List reqWorkFlowList;
	private String workflowId;
	private String requestId;
	private String instanceId;
	private int id;
	private int status;
	private String statusMsg;
	private String requestDate;
	private String workFlowType;
	private String employee;
	private String boss;
	private String relationShip;
	private String values;
	private String requestName;
	private String requestType;
	private String alertType;
	private String workflow;
	private String rowId;
	private String instanceName;
	private String gazettedType;
	private List subordinateList;
	private String subordinateId;
	private String delegateType;
	private String jsonValue;
	private ArrayList<RequestsDTO> pendingRequests;
	private ArrayList<RequestsDTO> delegatedRequests;
	private ArrayList<RequestsDTO> completedRequests;
	private ArrayList<RequestsDTO> escalatedRequests;
	private ArrayList<RequestsDTO> myRequests;
	private List<RequestsDTO> myHoldRequests;
	private List<RequestsDTO> totalHoldRequests;
	private String historyID;
	private String requesterSfid;
	private String requester;
	private String designation;
	private String designationID;
	private ArrayList<RequestsDTO> workflowHistory;
	private String sfid;
	private List<KeyValueDTO> delegateList;
	private List<KeyValueDTO> cghsDelegateList;
	private List<KeyValueDTO> loanDelegateList;
	private String delegatedSfid;
	private String message;
	private String workflowName;
	private String flowname;
	private List   workflowTypeList;
	private String from;
	private String to;
	private String relation;
	private String escalteTo;
	private String esclateRelation;
	private List<WorkFlowRelationDTO> relationList;
	private List<WorkFlowRelationShipDTO> relationShipList;
	private String stageId;
	private String toworkflow;
	private String division;
	private List divisionList;
	private ArrayList<TxnDetailsDTO> modifiedValues;
	private String deletedSfid;
	private String empName;
	private String remarks;
	private String ipAddress;
	private String assignedType;
	private ArrayList<NomineeBean> contengensyList;
	private String rowNumber;
	private String familyMemberName;
	private String recordType;
	private String nomineeType;
	private LeaveRequestBean leaveRequestDetails;
	private DemandRequestBean demandRequestDetails;
	private String reason;
	private List<ConvertLeaveRequestBean> conversionDetails;
	private VoucherRequestBean voucherRequestDetails;
	private InvoiceRequestBean invoiceRequestDetails;
	private CGHSRequestProcessBean cghsRequestDetails;
	private LoanRequestProcessBean loanRequestDetails;
	private List<OrgInstanceDTO> roleInstanceList;
	private List<WorkflowDynamicTablesDTO> dynamicTableList;
	private String roleInstanceID;
	private String workflowType;
	private String roleInstanceName;
	private Date paymentDate;
	private Date settlementDate;
	private String settleAmount;
	private String description;
	private String paymentType;
	private String paymentTypeId;
	private List<WorkflowStageMasterDTO> workflowStageList;
	private String workflowStage;
	private String workflowStageID;
	private Date formattedAppliedDate;
	private String approvedDept;
	private String leaveRequestID;
	private String requestSize;
	private String myalertSize;
	private ArrayList<RequestsDTO> requestsList;
	private String mcFilePath;
	private String fitnessFilePath;
	private String medicalFlag;
	private String checkStage;
	private String back;
	private String referenceNumber;
	private String prescriptionReceivedFlag;
	private String approvedBy;
	private CghsAdvanceRequestDTO cghsAdvanceDTO;
	private CghsReimbursementRequestDTO cghsReimbursement;
	private List<LeaveRequestExceptionsDTO> requestExceptionList;
	private LtcApprovalRequestDTO ltcApprovalRequestDTO;
	private CghsEmergencyRequestDTO cghsEmergency;
	private LtcAdvanceRequestDTO ltcAdvanceRequestDTO;
	private List<KeyValueDTO> ltcAdvanceDelegateList;
	private LtcApplicationBean lTCRefundDetails;
	private List<LtcJourneyDetailsDTO> ltcJourneyDetails;
	private String gradePay;
	private String employmentTypeId;
	private String retirementDate;
	private String dojDrdo;
	private String myRequestSize;
	private String treasury;
	private String payee;
	private String amount;
	private String dvNo;
	private String checkNo;
	private String checkDate;
	private String reqType;
	private String selectedRequestType;
	private String selectedAlertType;
	private String requestIDValue;
	private String fromDate;
	private String toDate;
	private String requestStage;
	private List<String> roleList;
	private EmpTransferBean transferRequestDetails;
	private String doPartNo;
	private Date doPartDate;
	private String hqRefNo;
	private Date receivedDate;
	private String requestTypeID;
	private String result;
	private List<WorkFlowMasterDTO> workflowsList;
	private List<WorkflowDependentDTO> workflowDependentList;
	private String nodeID;
	private List<WorkFlowMappingBean> workflowDetails;
	private String stages;
	private LoanPaymentDTO loanPaymentDetails;
	private KeyValueDTO keyValuePair;
	private String gpfNumber;
	private String lastStagePendingCheck;
	private float sanctionedAmount;
	private Date sanctionedDate;
	private int sanctionedInstalments;
	private float emi;
	private String bankAccount;
	private String bankBranch;
	private Date recStartDate;
	private String loanRefNo;
	private LoanHBARequestProcessBean loanHBARequestDetails;
	private LoanHBAPaymentDTO loanHBAPaymentDetails;
	private List<AlertMessageDTO> myAlerts;
	private AlertMessageDTO alertDetails;
	private String alertMsgID;
	private String referenceID;
	private String alertTypeID;
	private float advanceReq;
	private int noOfInstalPrinciple;
	private int noOfInstalInterest;
	private String reqAmount;
	private int requestedInstalments;
	private int requestedInterestInstalments;
	private String quarterType;
	private Date allotedDate;
	private String quarterNo;
	private Date occupiedDate;
	private QuarterRequestBean quarterDetails;
	private List<QuarterTypeBean> quarterSubTypeList;
	private List<DoPartBean> doPartList;
	private Date approvedDate;
	private HQRequestDTO hqRequestDTO;
	private String course;
	private String labWork;
	private String religionFlag;
	private String dischargeOfDuties;
	private FPARequestProcessBean fpaRequestDetails;
	private float sanctionedFpaAmount;
	private Date sanctionedFpaDate;
    private PassportApplicationDetailsDTO passportRequestDetails;
    private TadaApprovalRequestDTO tadaApprovalRequestDTO;
    //added by bkr 04/05/2016
    private TadaWaterApprovalRequestDTO tadaWaterApprovalRequestDTO;
    private TadaTdAdvanceRequestDTO tadaTdAdvanceRequestDTO;
   private TrainingCirculationDTO trainingCirculationDto;
    private TrainingNominationDTO trainingNominationDto;
	private List<TadaTdDaNewDetailsDTO> tdDaNewDetailsList;
    private List<TadaTdDaNewAccDetailsDTO> tdDaNewAccDetailsList;
    private List<TadaTdDaNewFoodDetailsDTO> tdDaNewFoodDetailsList;
    private List<TadaTdDaOldDetailsDTO> tdDaOldDetailsList;
    private List<TadaTdRMAKmDTO> tdRMAKmList;
    private List<TadaTdRMADayDTO> tdRMADayList;
    private List<TadaTdRMADailyDTO> tdRMADailyList;
    private List<TadaTdLocalRMADetailsDTO> tdRMALocalList;
    private List<TadaTdJourneyDTO> tdJourneyList;
    private List<EmployeeClaimDetailsDTO> empClaimDetailsList;
    private String daNewAmount;
	private String rmaKmAmount;
	private String journeyAmount;
	private String rmaDayAmount;
	private String daOldAmount;
	private int tadaAdvanceAmount;
	private String foodDaNewAmount;
	private String stayDaNewAmount;
	private String accDaNewAmount;
	private String distanceAftRes;
	private String amountPerKmAftRes;
    private List<LtcPenalMasterDTO> ltcPenalMasterList;
    private LtcPenalMasterDTO ltcPenalInterestRate;
    private int penalInterestId;
    private String noOfDays;
    private String projectType;
	private String projectName;
    private RequestCommonBean requestCommonBean;
    private List<TadaProjectDirectorsDTO> tadaPrjDirList;
    private String amountPerDayAftRes;
    private String stayDaOldAmount;
    private String amountPerDay;
    private CDADetailsDTO cdaDetailsDTO;
    private List<TadaDaNewDetailsDTO> daNewDetailsList;
    private List<TadaDetailsDTO> daOldDetailsList;
    private List<CityTypeDTO> cityTypeList;
    private List<TadaTdDaNewFoodDetailsDTO> daNewFoodDayRepList;
  	private List<TadaTdAccDetDayRepDTO> daNewAccDayRepList;
    private String penalInterestReq;
    private String foodAmtRepresentation;
    private String foodAmtRep;
    private String totalFoodAmount;
   	private String totalAccAmount;
    private float totalTadaTdCalAmount;
    private String strTicketFareAftRes;
    private String strClaimedJourneyAmtAftRes;
    private String strAccAmtAftRes;
    private String strClaimedAccAmtAftRes;
    private String strRMAKmDisAftRes;
    private String strRMAKmAmtAftRes;
    private String strClaimedRMAKmAftRes;
    private String strRMALocalDisAftRes;
    private String strRMALocalAmtAftRes;
    private String strClaimedRMALocalAftRes;
    private String strFoodAmtAftRes;
    private TadaTdTotalFoodClaimDTO tdTotalFoodDetails;
    private List<TadaTdReqJourneyDTO> tadaTdReqJourneyList;
    private float issuedAdvAmount;
    private String strRMADayAmtPerDay;
    private String strRMADayAmtAftRes;
    private List<DesignationDTO> designationList;
    private String tdSettlementReqId;
    private String tdSettlementHistoryID;
    private String tdSettStrStatus;
    private int tdSettStatus;
    private String tdReimReqId;
    private String tdReimHistoryID;
    private String tdReimStrStatus;
    private int tdReimStatus;
    private List<LeaveRequestBean> tdLeaveDetailsList;
    private MRODetailsDTO mroDetails;
    private MROPaymentDetailsDTO mroPaymentDetails;
    private TadaApprovalRequestDTO tadaAmendmentDetails;
    private List<MRODetailsDTO> mroDetailsList;
    private List<MROPaymentDetailsDTO> mroPaymentDetailsList;
	private String projectDirSfid;
	private List<OrganizationsDTO> organizationsList;
	private String organizationID;
	private String organization;
	private String strRMADailyDisAftRes;
    private String strRMADailyAmtAftRes;
    private String strClaimedRMADailyAftRes;
    private String issueAuthority;
    private float totalAdvAmount;
    private String userRemarks;
    private Float tadaDaPercentage;   //This declared for tada Da configuration
    private Float daValue;            //This declared for tada Da configuration
      //for TelephoneBill
    private List<TutionFeeBean> tutionFeeClaimList;
    private List<List<TutionFeeClaimDetailsDTO>> mainTutionFeeClaimList;
	private int maxAmountPerOneChild;
	private int quarterAmount;
	private int halfyearlyAmount;
	private String limitId;
	private String doPartShowFlag;
	private int count;
	private List<TelePhoneBillBean> telephoneBillClaimList;
	private List<TelephoneBillClaimDetailsDTO> mainTelephoneBillClaimList;
	private List<TelephoneDesigEligibilityDetailsDTO> teleMaxAmount;
	private String serviceTax;
	private String totAmount;
	private String CashAssignEligibilitySfid;
	private String claimedAmount;
	private String teleSanctionedAmount;
	private String internetFlag;
	private int cashAssignmentAmount;
	private String teleEligibleAmt;
	private String cdaApprovedAmt;
	private String approvedBillNo;
	private String approvedSantionNo;
	private String dvNumber;
	private String dvDate;
	  //for MT
	private MTRequestDetailsDTO mtRequester;
	private List vehicleDriverFreeList;
	private String vehicleDriverMapId;
	private List articleList;
	private List returnArticleList;
	private List vehicleDriverBusyList;
	private List hiredVehicleList;
	private String freeVehicleDriverMapId;
	private String hiredVehicleDriverMapId;
	private String busyVehicleDriverMapId;
	private String returnVehicleDriverMapId;
	private String returnFreeVehicleDriverMapId;
	private String returnHiredVehicleDriverMapId;
	private String returnBusyVehicleDriverMapId;
	private String returnVehicleType;
	private String requestIdBased;
	private List<RequestsDTO> requestStatus;
	private String orgRoleId;
	private String roleRequestType;
	private String roleId;
	private String assignedFrom;
	private String assignedTo;
	private String requestsuccess;
	private String requestfailure;
	private String requestnotexist;
	//for cghs file uploading

	private String prescriptionFileName;
	private String permissionFileName;
	private String estimationFileName;
	private String contingentFile;
	private String refundChequeFileName;
	private String dischargeSummeryFileName;
	private String medicalBillsFileName;
	private String essentialityFile;
	private String med2004File;
	private String checkListFile;
	private String requestFormFile;
	private String familyMembetId;
	private String reimbursementFile;
	private MultipartFile mcFile;
	private MultipartFile fitnessFile;
	private MultipartFile otherCertFile;

	//files

	private MultipartFile prescriptionFile;
	private MultipartFile permissionFile;
	private MultipartFile estimationFile;
	private MultipartFile cghsCardFile;
	private MultipartFile refundChequeFile;
	private MultipartFile dischargeSummeryFile;
	private MultipartFile medicalBillsFile;
	private String cghsreimbursementId;
    private String cghsreimbursementHistoryId;

    private String orgroleIdLink;

    private int amphistoryid;
    private String amprequestid;
    private String requestidApprival;
    private int  historyrequestidApprival;
    private String leaveCancelDopartno;
    private Date leaveCancelDopartDate;
    private List<DoPartDTO> cancelLeaveDoPartList;
    private String leaveConversionDopartno;
    private Date leaveConversionDopartDate;
    private  List<DoPartDTO> convertLeaveDoPartList;
    private String remarksDecline;
    // Leave
    private String amendment;
    private List<String>  dopartdateList;
    private LTCWaterRequestDTO ltcWaterRequestDTO;
    
    private ErpAvailableLeaveSaveDTO erpAvailableLeaveSaveDTO;
    //loan(mohib)
    private ErpLoanRequestDTO erpLoanRequestDTO;
    private Float eligibleAmount;
    private Float previousLoanAmount;
	private Float monthlyDeduction;
	private Float approvedAmount;
    
    private String cancelRequestId;
    
    

	public String getCancelRequestId() {
		return cancelRequestId;
	}

	public void setCancelRequestId(String cancelRequestId) {
		this.cancelRequestId = cancelRequestId;
	}

	public ErpAvailableLeaveSaveDTO getErpAvailableLeaveSaveDTO() {
		return erpAvailableLeaveSaveDTO;
	}

	public void setErpAvailableLeaveSaveDTO(
			ErpAvailableLeaveSaveDTO erpAvailableLeaveSaveDTO) {
		this.erpAvailableLeaveSaveDTO = erpAvailableLeaveSaveDTO;
	}

	

	public Float getEligibleAmount() {
		return eligibleAmount;
	}

	public void setEligibleAmount(Float eligibleAmount) {
		this.eligibleAmount = eligibleAmount;
	}

	public Float getPreviousLoanAmount() {
		return previousLoanAmount;
	}

	public void setPreviousLoanAmount(Float previousLoanAmount) {
		this.previousLoanAmount = previousLoanAmount;
	}

	public Float getMonthlyDeduction() {
		return monthlyDeduction;
	}

	public void setMonthlyDeduction(Float monthlyDeduction) {
		this.monthlyDeduction = monthlyDeduction;
	}

	public LTCWaterRequestDTO getLtcWaterRequestDTO() {
		return ltcWaterRequestDTO;
	}

	public void setLtcWaterRequestDTO(LTCWaterRequestDTO ltcWaterRequestDTO) {
		this.ltcWaterRequestDTO = ltcWaterRequestDTO;
	}

	public TadaWaterApprovalRequestDTO getTadaWaterApprovalRequestDTO() {
		return tadaWaterApprovalRequestDTO;
	}

	public void setTadaWaterApprovalRequestDTO(
			TadaWaterApprovalRequestDTO tadaWaterApprovalRequestDTO) {
		this.tadaWaterApprovalRequestDTO = tadaWaterApprovalRequestDTO;
	}

	public String getAmendment() {
		return amendment;
	}

	public void setAmendment(String amendment) {
		this.amendment = amendment;
	}

	public List<String> getDopartdateList() {
		return dopartdateList;
	}

	public void setDopartdateList(List<String> dopartdateList) {
		this.dopartdateList = dopartdateList;
	}

	public String getRemarksDecline() {
		return remarksDecline;
	}

	public void setRemarksDecline(String remarksDecline) {
		this.remarksDecline = remarksDecline;
	}

	public List<DoPartDTO> getConvertLeaveDoPartList() {
		return convertLeaveDoPartList;
	}

	public void setConvertLeaveDoPartList(List<DoPartDTO> convertLeaveDoPartList) {
		this.convertLeaveDoPartList = convertLeaveDoPartList;
	}

	public String getLeaveConversionDopartno() {
		return leaveConversionDopartno;
	}

	public void setLeaveConversionDopartno(String leaveConversionDopartno) {
		this.leaveConversionDopartno = leaveConversionDopartno;
	}

	public Date getLeaveConversionDopartDate() {
		return leaveConversionDopartDate;
	}

	public void setLeaveConversionDopartDate(Date leaveConversionDopartDate) {
		this.leaveConversionDopartDate = leaveConversionDopartDate;
	}

	public List<DoPartDTO> getCancelLeaveDoPartList() {
		return cancelLeaveDoPartList;
	}

	public void setCancelLeaveDoPartList(List<DoPartDTO> cancelLeaveDoPartList) {
		this.cancelLeaveDoPartList = cancelLeaveDoPartList;
	}

	public String getLeaveCancelDopartno() {
		return leaveCancelDopartno;
	}

	public void setLeaveCancelDopartno(String leaveCancelDopartno) {
		this.leaveCancelDopartno = leaveCancelDopartno;
	}

	public Date getLeaveCancelDopartDate() {
		return leaveCancelDopartDate;
	}

	public void setLeaveCancelDopartDate(Date leaveCancelDopartDate) {
		this.leaveCancelDopartDate = leaveCancelDopartDate;
	}

	public int getAmphistoryid() {
		return amphistoryid;
	}

	public void setAmphistoryid(int amphistoryid) {
		this.amphistoryid = amphistoryid;
	}

	public String getAmprequestid() {
		return amprequestid;
	}

	public void setAmprequestid(String amprequestid) {
		this.amprequestid = amprequestid;
	}

	public String getOrgroleIdLink() {
		return orgroleIdLink;
	}

	public void setOrgroleIdLink(String orgroleIdLink) {
		this.orgroleIdLink = orgroleIdLink;
	}

	public MultipartFile getMcFile() {
		return mcFile;
	}

	public Float getDaValue() {
		return daValue;
	}

	public void setDaValue(Float daValue) {
		this.daValue = daValue;
	}

	public Float getTadaDaPercentage() {
		return tadaDaPercentage;
	}

	public void setTadaDaPercentage(Float tadaDaPercentage) {
		this.tadaDaPercentage = tadaDaPercentage;
	}

	public void setMcFile(MultipartFile mcFile) {
		this.mcFile = mcFile;
	}

	public MultipartFile getFitnessFile() {
		return fitnessFile;
	}

	public void setFitnessFile(MultipartFile fitnessFile) {
		this.fitnessFile = fitnessFile;
	}

	public MultipartFile getOtherCertFile() {
		return otherCertFile;
	}

	public void setOtherCertFile(MultipartFile otherCertFile) {
		this.otherCertFile = otherCertFile;
	}

	private String cghsCardFileName;
	public String getPermissionFileName() {
		return permissionFileName;
	}

	public void setPermissionFileName(String permissionFileName) {
		this.permissionFileName = permissionFileName;
	}

	public String getEstimationFileName() {
		return estimationFileName;
	}

	public void setEstimationFileName(String estimationFileName) {
		this.estimationFileName = estimationFileName;
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
	public String getContingentFile() {
		return contingentFile;
	}

	public void setContingentFile(String contingentFile) {
		this.contingentFile = contingentFile;
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

	public String getRequestFormFile() {
		return requestFormFile;
	}

	public void setRequestFormFile(String requestFormFile) {
		this.requestFormFile = requestFormFile;
	}

	public String getFamilyMembetId() {
		return familyMembetId;
	}

	public void setFamilyMembetId(String familyMembetId) {
		this.familyMembetId = familyMembetId;
	}

	public String getReimbursementFile() {
		return reimbursementFile;
	}

	public void setReimbursementFile(String reimbursementFile) {
		this.reimbursementFile = reimbursementFile;
	}

	public String getRequestnotexist() {
		return requestnotexist;
	}

	public void setRequestnotexist(String requestnotexist) {
		this.requestnotexist = requestnotexist;
	}

	//tf
	private String tutionFeeOrgRoleId;
	//
	public String getTotalAccAmount() {
		return totalAccAmount;
	}

	public void setTotalAccAmount(String totalAccAmount) {
		this.totalAccAmount = totalAccAmount;
	}

	//alert start
	public String getAlertType() {
		return alertType;
	}

	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}

	public String getMyalertSize() {
		return myalertSize;
	}

	public void setMyalertSize(String myalertSize) {
		this.myalertSize = myalertSize;
	}
	public String getSelectedAlertType() {
		return selectedAlertType;
	}

	public void setSelectedAlertType(String selectedAlertType) {
		this.selectedAlertType = selectedAlertType;
	}
	//
	public String getRequestsuccess() {
		return requestsuccess;
	}

	public void setRequestsuccess(String requestsuccess) {
		this.requestsuccess = requestsuccess;
	}

	public String getRequestfailure() {
		return requestfailure;
	}

	public void setRequestfailure(String requestfailure) {
		this.requestfailure = requestfailure;
	}

	public String getAssignedFrom() {
		return assignedFrom;
	}

	public void setAssignedFrom(String assignedFrom) {
		this.assignedFrom = assignedFrom;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleRequestType() {
		return roleRequestType;
	}

	public void setRoleRequestType(String roleRequestType) {
		this.roleRequestType = roleRequestType;
	}

	public String getOrgRoleId() {
		return orgRoleId;
	}

	public void setOrgRoleId(String orgRoleId) {
		this.orgRoleId = orgRoleId;
	}

	public List<RequestsDTO> getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(List<RequestsDTO> requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getRequestIdBased() {
		return requestIdBased;
	}

	public void setRequestIdBased(String requestIdBased) {
		this.requestIdBased = requestIdBased;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}



	public List<TelephoneBillClaimDetailsDTO> getMainTelephoneBillClaimList() {
		return mainTelephoneBillClaimList;
	}

	public void setMainTelephoneBillClaimList(
			List<TelephoneBillClaimDetailsDTO> mainTelephoneBillClaimList) {
		this.mainTelephoneBillClaimList = mainTelephoneBillClaimList;
	}

	public List<TelePhoneBillBean> getTelephoneBillClaimList() {
		return telephoneBillClaimList;
	}

	public void setTelephoneBillClaimList(
			List<TelePhoneBillBean> telephoneBillClaimList) {
		this.telephoneBillClaimList = telephoneBillClaimList;
	}

	public String getLimitId() {
		return limitId;
	}
   public String getReturnVehicleDriverMapId() {
		return returnVehicleDriverMapId;
	}
	public void setLimitId(String limitId) {
		this.limitId = limitId;
	}
   public void setReturnVehicleDriverMapId(String returnVehicleDriverMapId) {
		this.returnVehicleDriverMapId = returnVehicleDriverMapId;
	}

	public String getReturnFreeVehicleDriverMapId() {
		return returnFreeVehicleDriverMapId;
	}

	public void setReturnFreeVehicleDriverMapId(String returnFreeVehicleDriverMapId) {
		this.returnFreeVehicleDriverMapId = returnFreeVehicleDriverMapId;
	}

	public String getReturnHiredVehicleDriverMapId() {
		return returnHiredVehicleDriverMapId;
	}

	public void setReturnHiredVehicleDriverMapId(
			String returnHiredVehicleDriverMapId) {
		this.returnHiredVehicleDriverMapId = returnHiredVehicleDriverMapId;
	}

	public String getReturnBusyVehicleDriverMapId() {
		return returnBusyVehicleDriverMapId;
	}

	public void setReturnBusyVehicleDriverMapId(String returnBusyVehicleDriverMapId) {
		this.returnBusyVehicleDriverMapId = returnBusyVehicleDriverMapId;
	}

	public String getReturnVehicleType() {
		return returnVehicleType;
	}

	public void setReturnVehicleType(String returnVehicleType) {
		this.returnVehicleType = returnVehicleType;
	}

	public String getBusyVehicleDriverMapId() {
		return busyVehicleDriverMapId;
	}

	public void setBusyVehicleDriverMapId(String busyVehicleDriverMapId) {
		this.busyVehicleDriverMapId = busyVehicleDriverMapId;
	}

	public String getFreeVehicleDriverMapId() {
		return freeVehicleDriverMapId;
	}

	public void setFreeVehicleDriverMapId(String freeVehicleDriverMapId) {
		this.freeVehicleDriverMapId = freeVehicleDriverMapId;
	}

	public String getHiredVehicleDriverMapId() {
		return hiredVehicleDriverMapId;
	}

	public void setHiredVehicleDriverMapId(String hiredVehicleDriverMapId) {
		this.hiredVehicleDriverMapId = hiredVehicleDriverMapId;
	}

	public List getVehicleDriverFreeList() {
		return vehicleDriverFreeList;
	}

	public void setVehicleDriverFreeList(List vehicleDriverFreeList) {
		this.vehicleDriverFreeList = vehicleDriverFreeList;
	}

	public List getVehicleDriverBusyList() {
		return vehicleDriverBusyList;
	}

	public void setVehicleDriverBusyList(List vehicleDriverBusyList) {
		this.vehicleDriverBusyList = vehicleDriverBusyList;
	}

	public List getHiredVehicleList() {
		return hiredVehicleList;
	}

	public void setHiredVehicleList(List hiredVehicleList) {
		this.hiredVehicleList = hiredVehicleList;
	}

	public List getArticleList() {
		return articleList;
	}

	public void setArticleList(List articleList) {
		this.articleList = articleList;
	}

	public List getReturnArticleList() {
		return returnArticleList;
	}

	public void setReturnArticleList(List returnArticleList) {
		this.returnArticleList = returnArticleList;
	}

	public String getVehicleDriverMapId() {
		return vehicleDriverMapId;
	}

	public void setVehicleDriverMapId(String vehicleDriverMapId) {
		this.vehicleDriverMapId = vehicleDriverMapId;
	}


	public MTRequestDetailsDTO getMtRequester() {
		return mtRequester;
	}

	public void setMtRequester(MTRequestDetailsDTO mtRequester) {
		this.mtRequester = mtRequester;
	}

	public int getMaxAmountPerOneChild() {
		return maxAmountPerOneChild;
	}

	public void setMaxAmountPerOneChild(int maxAmountPerOneChild) {
		this.maxAmountPerOneChild = maxAmountPerOneChild;
	}

	public List<List<TutionFeeClaimDetailsDTO>> getMainTutionFeeClaimList() {
		return mainTutionFeeClaimList;
	}

	public void setMainTutionFeeClaimList(List<List<TutionFeeClaimDetailsDTO>> mainTutionFeeClaimList) {
		this.mainTutionFeeClaimList = mainTutionFeeClaimList;
	}

	public List<TutionFeeBean> getTutionFeeClaimList() {
		return tutionFeeClaimList;
	}

	public void setTutionFeeClaimList(List<TutionFeeBean> tutionFeeClaimList) {
		this.tutionFeeClaimList = tutionFeeClaimList;
	}

	public CDADetailsDTO getCdaDetailsDTO() {
		return cdaDetailsDTO;
	}

	public void setCdaDetailsDTO(CDADetailsDTO cdaDetailsDTO) {
		this.cdaDetailsDTO = cdaDetailsDTO;
	}

	public String getStayDaOldAmount() {
		return stayDaOldAmount;
	}

	public void setStayDaOldAmount(String stayDaOldAmount) {
		this.stayDaOldAmount = stayDaOldAmount;
	}

	public String getAmountPerDay() {
		return amountPerDay;
	}

	public void setAmountPerDay(String amountPerDay) {
		this.amountPerDay = amountPerDay;
	}

	public List<TadaTdDaOldDetailsDTO> getTdDaOldDetailsList() {
		return tdDaOldDetailsList;
	}

	public void setTdDaOldDetailsList(List<TadaTdDaOldDetailsDTO> tdDaOldDetailsList) {
		this.tdDaOldDetailsList = tdDaOldDetailsList;
	}

	public String getAmountPerDayAftRes() {
		return amountPerDayAftRes;
	}

	public void setAmountPerDayAftRes(String amountPerDayAftRes) {
		this.amountPerDayAftRes = amountPerDayAftRes;
	}


	public List<TadaTdDaNewFoodDetailsDTO> getDaNewFoodDayRepList() {
		return daNewFoodDayRepList;
	}

	public void setDaNewFoodDayRepList(
			List<TadaTdDaNewFoodDetailsDTO> daNewFoodDayRepList) {
		this.daNewFoodDayRepList = daNewFoodDayRepList;
	}

	public List<TadaTdDaNewAccDetailsDTO> getTdDaNewAccDetailsList() {
		return tdDaNewAccDetailsList;
	}

	public void setTdDaNewAccDetailsList(
			List<TadaTdDaNewAccDetailsDTO> tdDaNewAccDetailsList) {
		this.tdDaNewAccDetailsList = tdDaNewAccDetailsList;
	}

	public List<TadaTdDaNewFoodDetailsDTO> getTdDaNewFoodDetailsList() {
		return tdDaNewFoodDetailsList;
	}

	public void setTdDaNewFoodDetailsList(
			List<TadaTdDaNewFoodDetailsDTO> tdDaNewFoodDetailsList) {
		this.tdDaNewFoodDetailsList = tdDaNewFoodDetailsList;
	}

	public List<TadaTdLocalRMADetailsDTO> getTdRMALocalList() {
		return tdRMALocalList;
	}

	public void setTdRMALocalList(List<TadaTdLocalRMADetailsDTO> tdRMALocalList) {
		this.tdRMALocalList = tdRMALocalList;
	}

	public List<TadaDaNewDetailsDTO> getDaNewDetailsList() {
		return daNewDetailsList;
	}

	public void setDaNewDetailsList(List<TadaDaNewDetailsDTO> daNewDetailsList) {
		this.daNewDetailsList = daNewDetailsList;
	}

	public List<TadaDetailsDTO> getDaOldDetailsList() {
		return daOldDetailsList;
	}

	public void setDaOldDetailsList(List<TadaDetailsDTO> daOldDetailsList) {
		this.daOldDetailsList = daOldDetailsList;
	}

	public List<CityTypeDTO> getCityTypeList() {
		return cityTypeList;
	}

	public void setCityTypeList(List<CityTypeDTO> cityTypeList) {
		this.cityTypeList = cityTypeList;
	}


	public String getPenalInterestReq() {
		return penalInterestReq;
	}

	public void setPenalInterestReq(String penalInterestReq) {
		this.penalInterestReq = penalInterestReq;
	}

	public List<TadaTdRMADayDTO> getTdRMADayList() {
		return tdRMADayList;
	}

	public void setTdRMADayList(List<TadaTdRMADayDTO> tdRMADayList) {
		this.tdRMADayList = tdRMADayList;
	}

	public String getStayDaNewAmount() {
		return stayDaNewAmount;
	}

	public void setStayDaNewAmount(String stayDaNewAmount) {
		this.stayDaNewAmount = stayDaNewAmount;
	}

	public List<TadaProjectDirectorsDTO> getTadaPrjDirList() {
		return tadaPrjDirList;
	}

	public void setTadaPrjDirList(List<TadaProjectDirectorsDTO> tadaPrjDirList) {
		this.tadaPrjDirList = tadaPrjDirList;
	}

	public RequestCommonBean getRequestCommonBean() {
		return requestCommonBean;
	}

	public void setRequestCommonBean(RequestCommonBean requestCommonBean) {
		this.requestCommonBean = requestCommonBean;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
    //For TD Advance Page
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public List<TadaProjectDirectorsDTO> getPrjlist() {
		return prjlist;
	}

	public void setPrjlist(List<TadaProjectDirectorsDTO> prjlist) {
		this.prjlist = prjlist;
	}

	//
	public List<TadaTdDaNewDetailsDTO> getTdDaNewDetailsList() {
		return tdDaNewDetailsList;
	}

	public void setTdDaNewDetailsList(List<TadaTdDaNewDetailsDTO> tdDaNewDetailsList) {
		this.tdDaNewDetailsList = tdDaNewDetailsList;
	}

	public List<TadaTdRMAKmDTO> getTdRMAKmList() {
		return tdRMAKmList;
	}

	public void setTdRMAKmList(List<TadaTdRMAKmDTO> tdRMAKmList) {
		this.tdRMAKmList = tdRMAKmList;
	}

	public List<TadaTdJourneyDTO> getTdJourneyList() {
		return tdJourneyList;
	}

	public void setTdJourneyList(List<TadaTdJourneyDTO> tdJourneyList) {
		this.tdJourneyList = tdJourneyList;
	}

	public List<EmployeeClaimDetailsDTO> getEmpClaimDetailsList() {
		return empClaimDetailsList;
	}

	public void setEmpClaimDetailsList(
			List<EmployeeClaimDetailsDTO> empClaimDetailsList) {
		this.empClaimDetailsList = empClaimDetailsList;
	}

	public String getDaNewAmount() {
		return daNewAmount;
	}

	public void setDaNewAmount(String daNewAmount) {
		this.daNewAmount = daNewAmount;
	}

	public String getRmaKmAmount() {
		return rmaKmAmount;
	}

	public void setRmaKmAmount(String rmaKmAmount) {
		this.rmaKmAmount = rmaKmAmount;
	}

	public String getJourneyAmount() {
		return journeyAmount;
	}

	public void setJourneyAmount(String journeyAmount) {
		this.journeyAmount = journeyAmount;
	}

	public String getRmaDayAmount() {
		return rmaDayAmount;
	}

	public void setRmaDayAmount(String rmaDayAmount) {
		this.rmaDayAmount = rmaDayAmount;
	}

	public String getDaOldAmount() {
		return daOldAmount;
	}

	public void setDaOldAmount(String daOldAmount) {
		this.daOldAmount = daOldAmount;
	}

	public int getTadaAdvanceAmount() {
		return tadaAdvanceAmount;
	}

	public void setTadaAdvanceAmount(int tadaAdvanceAmount) {
		this.tadaAdvanceAmount = tadaAdvanceAmount;
	}

	public String getFoodDaNewAmount() {
		return foodDaNewAmount;
	}

	public void setFoodDaNewAmount(String foodDaNewAmount) {
		this.foodDaNewAmount = foodDaNewAmount;
	}

	public String getAccDaNewAmount() {
		return accDaNewAmount;
	}

	public void setAccDaNewAmount(String accDaNewAmount) {
		this.accDaNewAmount = accDaNewAmount;
	}

	public String getDistanceAftRes() {
		return distanceAftRes;
	}

	public void setDistanceAftRes(String distanceAftRes) {
		this.distanceAftRes = distanceAftRes;
	}

	public String getAmountPerKmAftRes() {
		return amountPerKmAftRes;
	}

	public void setAmountPerKmAftRes(String amountPerKmAftRes) {
		this.amountPerKmAftRes = amountPerKmAftRes;
	}

	public List<LtcPenalMasterDTO> getLtcPenalMasterList() {
		return ltcPenalMasterList;
	}

	public void setLtcPenalMasterList(List<LtcPenalMasterDTO> ltcPenalMasterList) {
		this.ltcPenalMasterList = ltcPenalMasterList;
	}

	public int getPenalInterestId() {
		return penalInterestId;
	}

	public void setPenalInterestId(int penalInterestId) {
		this.penalInterestId = penalInterestId;
	}

	public String getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
	}





	public TadaTdAdvanceRequestDTO getTadaTdAdvanceRequestDTO() {
		return tadaTdAdvanceRequestDTO;
	}

	public void setTadaTdAdvanceRequestDTO(
			TadaTdAdvanceRequestDTO tadaTdAdvanceRequestDTO) {
		this.tadaTdAdvanceRequestDTO = tadaTdAdvanceRequestDTO;
	}

	public TadaApprovalRequestDTO getTadaApprovalRequestDTO() {
		return tadaApprovalRequestDTO;
	}

	public void setTadaApprovalRequestDTO(
			TadaApprovalRequestDTO tadaApprovalRequestDTO) {
		this.tadaApprovalRequestDTO = tadaApprovalRequestDTO;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getLabWork() {
		return labWork;
	}

	public void setLabWork(String labWork) {
		this.labWork = labWork;
	}

	public String getDischargeOfDuties() {
		return dischargeOfDuties;
	}

	public void setDischargeOfDuties(String dischargeOfDuties) {
		this.dischargeOfDuties = dischargeOfDuties;
	}

	public String getReligionFlag() {
		return religionFlag;
	}

	public void setReligionFlag(String religionFlag) {
		this.religionFlag = religionFlag;
	}


	public HQRequestDTO getHqRequestDTO() {
		return hqRequestDTO;
	}

	public void setHqRequestDTO(HQRequestDTO hqRequestDTO) {
		this.hqRequestDTO = hqRequestDTO;
	}

	public PassportApplicationDetailsDTO getPassportRequestDetails() {
		return passportRequestDetails;
	}

	public void setPassportRequestDetails(
			PassportApplicationDetailsDTO passportRequestDetails) {
		this.passportRequestDetails = passportRequestDetails;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public List<DoPartBean> getDoPartList() {
		return doPartList;
	}

	public void setDoPartList(List<DoPartBean> doPartList) {
		this.doPartList = doPartList;
	}

	public String getQuarterType() {
		return quarterType;
	}

	public void setQuarterType(String quarterType) {
		this.quarterType = quarterType;
	}

	public Date getAllotedDate() {
		return allotedDate;
	}

	public void setAllotedDate(Date allotedDate) {
		this.allotedDate = allotedDate;
	}

	public String getQuarterNo() {
		return quarterNo;
	}

	public void setQuarterNo(String quarterNo) {
		this.quarterNo = quarterNo;
	}

	public Date getOccupiedDate() {
		return occupiedDate;
	}

	public void setOccupiedDate(Date occupiedDate) {
		this.occupiedDate = occupiedDate;
	}

	public List<QuarterTypeBean> getQuarterSubTypeList() {
		return quarterSubTypeList;
	}

	public void setQuarterSubTypeList(List<QuarterTypeBean> quarterSubTypeList) {
		this.quarterSubTypeList = quarterSubTypeList;
	}

	public QuarterRequestBean getQuarterDetails() {
		return quarterDetails;
	}

	public void setQuarterDetails(QuarterRequestBean quarterDetails) {
		this.quarterDetails = quarterDetails;
	}

	public String getAlertTypeID() {
		return alertTypeID;
	}

	public void setAlertTypeID(String alertTypeID) {
		this.alertTypeID = alertTypeID;
	}

	public String getReferenceID() {
		return referenceID;
	}

	public void setReferenceID(String referenceID) {
		this.referenceID = referenceID;
	}

	public String getAlertMsgID() {
		return alertMsgID;
	}

	public void setAlertMsgID(String alertMsgID) {
		this.alertMsgID = alertMsgID;
	}

	public AlertMessageDTO getAlertDetails() {
		return alertDetails;
	}

	public void setAlertDetails(AlertMessageDTO alertDetails) {
		this.alertDetails = alertDetails;
	}

	public List<AlertMessageDTO> getMyAlerts() {
		return myAlerts;
	}

	public void setMyAlerts(List<AlertMessageDTO> myAlerts) {
		this.myAlerts = myAlerts;
	}

	public float getSanctionedAmount() {
		return sanctionedAmount;
	}

	public void setSanctionedAmount(float sanctionedAmount) {
		this.sanctionedAmount = sanctionedAmount;
	}

	public Date getSanctionedDate() {
		return sanctionedDate;
	}

	public void setSanctionedDate(Date sanctionedDate) {
		this.sanctionedDate = sanctionedDate;
	}

	public int getSanctionedInstalments() {
		return sanctionedInstalments;
	}

	public void setSanctionedInstalments(int sanctionedInstalments) {
		this.sanctionedInstalments = sanctionedInstalments;
	}

	public float getEmi() {
		return emi;
	}

	public void setEmi(float emi) {
		this.emi = emi;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public Date getRecStartDate() {
		return recStartDate;
	}

	public void setRecStartDate(Date recStartDate) {
		this.recStartDate = recStartDate;
	}

	public String getLoanRefNo() {
		return loanRefNo;
	}

	public void setLoanRefNo(String loanRefNo) {
		this.loanRefNo = loanRefNo;
	}

	public String getLastStagePendingCheck() {
		return lastStagePendingCheck;
	}

	public void setLastStagePendingCheck(String lastStagePendingCheck) {
		this.lastStagePendingCheck = lastStagePendingCheck;
	}

	public String getGpfNumber() {
		return gpfNumber;
	}

	public void setGpfNumber(String gpfNumber) {
		this.gpfNumber = gpfNumber;
	}

	public KeyValueDTO getKeyValuePair() {
		return keyValuePair;
	}

	public void setKeyValuePair(KeyValueDTO keyValuePair) {
		this.keyValuePair = keyValuePair;
	}

	public LoanPaymentDTO getLoanPaymentDetails() {
		return loanPaymentDetails;
	}

	public void setLoanPaymentDetails(LoanPaymentDTO loanPaymentDetails) {
		this.loanPaymentDetails = loanPaymentDetails;
	}

	public String getStages() {
		return stages;
	}

	public void setStages(String stages) {
		this.stages = stages;
	}

	public List<WorkFlowMappingBean> getWorkflowDetails() {
		return workflowDetails;
	}

	public void setWorkflowDetails(List<WorkFlowMappingBean> workflowDetails) {
		this.workflowDetails = workflowDetails;
	}

	public String getNodeID() {
		return nodeID;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}

	public List<WorkflowDependentDTO> getWorkflowDependentList() {
		return workflowDependentList;
	}

	public void setWorkflowDependentList(
			List<WorkflowDependentDTO> workflowDependentList) {
		this.workflowDependentList = workflowDependentList;
	}

	public List<WorkFlowRelationDTO> getRelationList() {
		return relationList;
	}

	public void setRelationList(List<WorkFlowRelationDTO> relationList) {
		this.relationList = relationList;
	}

	public List<WorkFlowRelationShipDTO> getRelationShipList() {
		return relationShipList;
	}

	public void setRelationShipList(
			List<WorkFlowRelationShipDTO> relationShipList) {
		this.relationShipList = relationShipList;
	}

	public List<OrgInstanceDTO> getRoleInstanceList() {
		return roleInstanceList;
	}

	public void setRoleInstanceList(List<OrgInstanceDTO> roleInstanceList) {
		this.roleInstanceList = roleInstanceList;
	}

	public List<WorkflowStageMasterDTO> getWorkflowStageList() {
		return workflowStageList;
	}

	public void setWorkflowStageList(
			List<WorkflowStageMasterDTO> workflowStageList) {
		this.workflowStageList = workflowStageList;
	}

	public List<WorkFlowMasterDTO> getWorkflowsList() {
		return workflowsList;
	}

	public void setWorkflowsList(List<WorkFlowMasterDTO> workflowsList) {
		this.workflowsList = workflowsList;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getRequestTypeID() {
		return requestTypeID;
	}

	public void setRequestTypeID(String requestTypeID) {
		this.requestTypeID = requestTypeID;
	}

	public String getDoPartNo() {
		return doPartNo;
	}

	public void setDoPartNo(String doPartNo) {
		this.doPartNo = doPartNo;
	}

	public Date getDoPartDate() {
		return doPartDate;
	}

	public void setDoPartDate(Date doPartDate) {
		this.doPartDate = doPartDate;
	}

	public String getHqRefNo() {
		return hqRefNo;
	}

	public void setHqRefNo(String hqRefNo) {
		this.hqRefNo = hqRefNo;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public EmpTransferBean getTransferRequestDetails() {
		return transferRequestDetails;
	}

	public void setTransferRequestDetails(EmpTransferBean transferRequestDetails) {
		this.transferRequestDetails = transferRequestDetails;
	}

	public List<String> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}

	public String getDesignationID() {
		return designationID;
	}

	public void setDesignationID(String designationID) {
		this.designationID = designationID;
	}

	public String getRequestStage() {
		return requestStage;
	}

	public void setRequestStage(String requestStage) {
		this.requestStage = requestStage;
	}

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getSelectedRequestType() {
		return selectedRequestType;
	}

	public void setSelectedRequestType(String selectedRequestType) {
		this.selectedRequestType = selectedRequestType;
	}

	public String getRequestIDValue() {
		return requestIDValue;
	}

	public void setRequestIDValue(String requestIDValue) {
		this.requestIDValue = requestIDValue;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getTreasury() {
		return treasury;
	}

	public void setTreasury(String treasury) {
		this.treasury = treasury;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDvNo() {
		return dvNo;
	}

	public void setDvNo(String dvNo) {
		this.dvNo = dvNo;
	}

	public String getCheckNo() {
		return checkNo;
	}

	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getMyRequestSize() {
		return myRequestSize;
	}

	public void setMyRequestSize(String myRequestSize) {
		this.myRequestSize = myRequestSize;
	}
	public String getMyAlertSize() {
		return myalertSize;
	}

	public void setMyAlertSize(String myalertSize) {
		this.myalertSize = myalertSize;
	}

	public String getGradePay() {
		return gradePay;
	}

	public void setGradePay(String gradePay) {
		this.gradePay = gradePay;
	}

	public String getEmploymentTypeId() {
		return employmentTypeId;
	}

	public void setEmploymentTypeId(String employmentTypeId) {
		this.employmentTypeId = employmentTypeId;
	}

	public String getRetirementDate() {
		return retirementDate;
	}

	public void setRetirementDate(String retirementDate) {
		this.retirementDate = retirementDate;
	}

	public String getDojDrdo() {
		return dojDrdo;
	}

	public void setDojDrdo(String dojDrdo) {
		this.dojDrdo = dojDrdo;
	}

	public LtcAdvanceRequestDTO getLtcAdvanceRequestDTO() {
		return ltcAdvanceRequestDTO;
	}

	public void setLtcAdvanceRequestDTO(
			LtcAdvanceRequestDTO ltcAdvanceRequestDTO) {
		this.ltcAdvanceRequestDTO = ltcAdvanceRequestDTO;
	}

	public List<LtcJourneyDetailsDTO> getLtcJourneyDetails() {
		return ltcJourneyDetails;
	}

	public void setLtcJourneyDetails(
			List<LtcJourneyDetailsDTO> ltcJourneyDetails) {
		this.ltcJourneyDetails = ltcJourneyDetails;
	}

	public LtcApplicationBean getlTCRefundDetails() {
		return lTCRefundDetails;
	}

	public void setlTCRefundDetails(LtcApplicationBean lTCRefundDetails) {
		this.lTCRefundDetails = lTCRefundDetails;
	}

	public List<KeyValueDTO> getLtcAdvanceDelegateList() {
		return ltcAdvanceDelegateList;
	}

	public void setLtcAdvanceDelegateList(
			List<KeyValueDTO> ltcAdvanceDelegateList) {
		this.ltcAdvanceDelegateList = ltcAdvanceDelegateList;
	}

	public List<LeaveRequestExceptionsDTO> getRequestExceptionList() {
		return requestExceptionList;
	}

	public void setRequestExceptionList(
			List<LeaveRequestExceptionsDTO> requestExceptionList) {
		this.requestExceptionList = requestExceptionList;
	}

	public LtcApprovalRequestDTO getLtcApprovalRequestDTO() {
		return ltcApprovalRequestDTO;
	}

	public void setLtcApprovalRequestDTO(
			LtcApprovalRequestDTO ltcApprovalRequestDTO) {
		this.ltcApprovalRequestDTO = ltcApprovalRequestDTO;
	}

	public CghsEmergencyRequestDTO getCghsEmergency() {
		return cghsEmergency;
	}

	public void setCghsEmergency(CghsEmergencyRequestDTO cghsEmergency) {
		this.cghsEmergency = cghsEmergency;
	}

	public CghsReimbursementRequestDTO getCghsReimbursement() {
		return cghsReimbursement;
	}

	public void setCghsReimbursement(
			CghsReimbursementRequestDTO cghsReimbursement) {
		this.cghsReimbursement = cghsReimbursement;
	}

	public CghsAdvanceRequestDTO getCghsAdvanceDTO() {
		return cghsAdvanceDTO;
	}

	public void setCghsAdvanceDTO(CghsAdvanceRequestDTO cghsAdvanceDTO) {
		this.cghsAdvanceDTO = cghsAdvanceDTO;
	}

	public List<KeyValueDTO> getLoanDelegateList() {
		return loanDelegateList;
	}

	public void setLoanDelegateList(List<KeyValueDTO> loanDelegateList) {
		this.loanDelegateList = loanDelegateList;
	}

	public LoanRequestProcessBean getLoanRequestDetails() {
		return loanRequestDetails;
	}

	public void setLoanRequestDetails(LoanRequestProcessBean loanRequestDetails) {
		this.loanRequestDetails = loanRequestDetails;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getPrescriptionReceivedFlag() {
		return prescriptionReceivedFlag;
	}

	public void setPrescriptionReceivedFlag(String prescriptionReceivedFlag) {
		this.prescriptionReceivedFlag = prescriptionReceivedFlag;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public List<KeyValueDTO> getCghsDelegateList() {
		return cghsDelegateList;
	}

	public void setCghsDelegateList(List<KeyValueDTO> cghsDelegateList) {
		this.cghsDelegateList = cghsDelegateList;
	}

	public CGHSRequestProcessBean getCghsRequestDetails() {
		return cghsRequestDetails;
	}

	public void setCghsRequestDetails(CGHSRequestProcessBean cghsRequestDetails) {
		this.cghsRequestDetails = cghsRequestDetails;
	}

	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	public String getBack() {
		return back;
	}

	public void setBack(String back) {
		this.back = back;
	}

	public String getCheckStage() {
		return checkStage;
	}

	public void setCheckStage(String checkStage) {
		this.checkStage = checkStage;
	}

	public String getMedicalFlag() {
		return medicalFlag;
	}

	public void setMedicalFlag(String medicalFlag) {
		this.medicalFlag = medicalFlag;
	}

	public String getMcFilePath() {
		return mcFilePath;
	}

	public void setMcFilePath(String mcFilePath) {
		this.mcFilePath = mcFilePath;
	}

	public String getFitnessFilePath() {
		return fitnessFilePath;
	}

	public void setFitnessFilePath(String fitnessFilePath) {
		this.fitnessFilePath = fitnessFilePath;
	}

	public ArrayList<RequestsDTO> getRequestsList() {
		return requestsList;
	}

	public void setRequestsList(ArrayList<RequestsDTO> requestsList) {
		this.requestsList = requestsList;
	}

	public String getRequestSize() {
		return requestSize;
	}

	public void setRequestSize(String requestSize) {
		this.requestSize = requestSize;
	}

	public String getLeaveRequestID() {
		return leaveRequestID;
	}

	public void setLeaveRequestID(String leaveRequestID) {
		this.leaveRequestID = leaveRequestID;
	}

	public String getApprovedDept() {
		return approvedDept;
	}

	public void setApprovedDept(String approvedDept) {
		this.approvedDept = approvedDept;
	}

	public Date getFormattedAppliedDate() {
		return formattedAppliedDate;
	}

	public void setFormattedAppliedDate(Date formattedAppliedDate) {
		this.formattedAppliedDate = formattedAppliedDate;
	}

	public String getWorkflowStageID() {
		return workflowStageID;
	}

	public void setWorkflowStageID(String workflowStageID) {
		this.workflowStageID = workflowStageID;
	}

	public String getWorkflowStage() {
		return workflowStage;
	}

	public void setWorkflowStage(String workflowStage) {
		this.workflowStage = workflowStage;
	}

	public String getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(String paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(String settleAmount) {
		this.settleAmount = settleAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public InvoiceRequestBean getInvoiceRequestDetails() {
		return invoiceRequestDetails;
	}

	public void setInvoiceRequestDetails(
			InvoiceRequestBean invoiceRequestDetails) {
		this.invoiceRequestDetails = invoiceRequestDetails;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public String getRoleInstanceName() {
		return roleInstanceName;
	}

	public void setRoleInstanceName(String roleInstanceName) {
		this.roleInstanceName = roleInstanceName;
	}

	public String getRoleInstanceID() {
		return roleInstanceID;
	}

	public void setRoleInstanceID(String roleInstanceID) {
		this.roleInstanceID = roleInstanceID;
	}

	public String getWorkflowType() {
		return workflowType;
	}

	public void setWorkflowType(String workflowType) {
		this.workflowType = workflowType;
	}

	public VoucherRequestBean getVoucherRequestDetails() {
		return voucherRequestDetails;
	}

	public void setVoucherRequestDetails(
			VoucherRequestBean voucherRequestDetails) {
		this.voucherRequestDetails = voucherRequestDetails;
	}

	public List<ConvertLeaveRequestBean> getConversionDetails() {
		return conversionDetails;
	}

	public void setConversionDetails(
			List<ConvertLeaveRequestBean> conversionDetails) {
		this.conversionDetails = conversionDetails;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public DemandRequestBean getDemandRequestDetails() {
		return demandRequestDetails;
	}

	public void setDemandRequestDetails(DemandRequestBean demandRequestDetails) {
		this.demandRequestDetails = demandRequestDetails;
	}

	public String getNomineeType() {
		return nomineeType;
	}

	public void setNomineeType(String nomineeType) {
		this.nomineeType = nomineeType;
	}

	public String getFamilyMemberName() {
		return familyMemberName;
	}

	public void setFamilyMemberName(String familyMemberName) {
		this.familyMemberName = familyMemberName;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(String rowNumber) {
		this.rowNumber = rowNumber;
	}

	public ArrayList<NomineeBean> getContengensyList() {
		return contengensyList;
	}

	public void setContengensyList(ArrayList<NomineeBean> contengensyList) {
		this.contengensyList = contengensyList;
	}

	public String getAssignedType() {
		return assignedType;
	}

	public void setAssignedType(String assignedType) {
		this.assignedType = assignedType;
	}

	public String getDeletedSfid() {
		return deletedSfid;
	}

	public void setDeletedSfid(String deletedSfid) {
		this.deletedSfid = deletedSfid;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getFlowname() {
		return flowname;
	}

	public void setFlowname(String flowname) {
		this.flowname = flowname;
	}

	public List getWorkflowTypeList() {
		return workflowTypeList;
	}

	public void setWorkflowTypeList(List workflowTypeList) {
		this.workflowTypeList = workflowTypeList;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getEscalteTo() {
		return escalteTo;
	}

	public void setEscalteTo(String escalteTo) {
		this.escalteTo = escalteTo;
	}

	public String getEsclateRelation() {
		return esclateRelation;
	}

	public void setEsclateRelation(String esclateRelation) {
		this.esclateRelation = esclateRelation;
	}

	public String getStageId() {
		return stageId;
	}

	public void setStageId(String stageId) {
		this.stageId = stageId;
	}

	public String getToworkflow() {
		return toworkflow;
	}

	public void setToworkflow(String toworkflow) {
		this.toworkflow = toworkflow;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public List getDivisionList() {
		return divisionList;
	}

	public void setDivisionList(List divisionList) {
		this.divisionList = divisionList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDelegateType() {
		return delegateType;
	}

	public void setDelegateType(String delegateType) {
		this.delegateType = delegateType;
	}

	public List getSubordinateList() {
		return subordinateList;
	}

	public void setSubordinateList(List subordinateList) {
		this.subordinateList = subordinateList;
	}

	public String getSubordinateId() {
		return subordinateId;
	}

	public void setSubordinateId(String subordinateId) {
		this.subordinateId = subordinateId;
	}

	public String getGazettedType() {
		return gazettedType;
	}

	public void setGazettedType(String gazettedType) {
		this.gazettedType = gazettedType;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getRequestName() {
		return requestName;
	}

	public void setRequestName(String requestName) {
		this.requestName = requestName;
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getWorkflow() {
		return workflow;
	}

	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
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

	public List getInstanceList() {
		return instanceList;
	}

	public void setInstanceList(List instanceList) {
		this.instanceList = instanceList;
	}

	public List getRequestList() {
		return requestList;
	}

	public void setRequestList(List requestList) {
		this.requestList = requestList;
	}
	public List getAlertList() {
		return alertList;
	}

	public void setAlertList(List alertList) {
		this.alertList = alertList;
	}


	public List getWorkflowList() {
		return workflowList;
	}

	public void setWorkflowList(List workflowList) {
		this.workflowList = workflowList;
	}


	public List getAllList() {
		return allList;
	}

	public void setAllList(List allList) {
		this.allList = allList;
	}

	public List getReqWorkFlowList() {
		return reqWorkFlowList;
	}

	public void setReqWorkFlowList(List reqWorkFlowList) {
		this.reqWorkFlowList = reqWorkFlowList;
	}

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getWorkFlowType() {
		return workFlowType;
	}

	public void setWorkFlowType(String workFlowType) {
		this.workFlowType = workFlowType;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public String getBoss() {
		return boss;
	}

	public void setBoss(String boss) {
		this.boss = boss;
	}

	public String getRelationShip() {
		return relationShip;
	}

	public void setRelationShip(String relationShip) {
		this.relationShip = relationShip;
	}

	public ArrayList<RequestsDTO> getPendingRequests() {
		return pendingRequests;
	}

	public String getJsonValue() {
		return jsonValue;
	}

	public void setJsonValue(String jsonValue) {
		this.jsonValue = jsonValue;
	}

	public void setPendingRequests(ArrayList<RequestsDTO> pendingRequests) {
		this.pendingRequests = pendingRequests;
	}

	public ArrayList<RequestsDTO> getDelegatedRequests() {
		return delegatedRequests;
	}

	public void setDelegatedRequests(ArrayList<RequestsDTO> delegatedRequests) {
		this.delegatedRequests = delegatedRequests;
	}

	public ArrayList<RequestsDTO> getCompletedRequests() {
		return completedRequests;
	}

	public void setCompletedRequests(ArrayList<RequestsDTO> completedRequests) {
		this.completedRequests = completedRequests;
	}

	public ArrayList<RequestsDTO> getEscalatedRequests() {
		return escalatedRequests;
	}

	public void setEscalatedRequests(ArrayList<RequestsDTO> escalatedRequests) {
		this.escalatedRequests = escalatedRequests;
	}

	public ArrayList<RequestsDTO> getMyRequests() {
		return myRequests;
	}

	public void setMyRequests(ArrayList<RequestsDTO> myRequests) {
		this.myRequests = myRequests;
	}

	public String getHistoryID() {
		return historyID;
	}

	public void setHistoryID(String historyID) {
		this.historyID = historyID;
	}

	public String getRequesterSfid() {
		return requesterSfid;
	}

	public void setRequesterSfid(String requesterSfid) {
		this.requesterSfid = requesterSfid;
	}

	public String getRequester() {
		return requester;
	}

	public void setRequester(String requester) {
		this.requester = requester;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public ArrayList<RequestsDTO> getWorkflowHistory() {
		return workflowHistory;
	}

	public void setWorkflowHistory(ArrayList<RequestsDTO> workflowHistory) {
		this.workflowHistory = workflowHistory;
	}

	public String getSfid() {
		return sfid;
	}

	public void setSfid(String sfid) {
		this.sfid = sfid;
	}

	public List<KeyValueDTO> getDelegateList() {
		return delegateList;
	}

	public void setDelegateList(List<KeyValueDTO> delegateList) {
		this.delegateList = delegateList;
	}

	public String getDelegatedSfid() {
		return delegatedSfid;
	}

	public void setDelegatedSfid(String delegatedSfid) {
		this.delegatedSfid = delegatedSfid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ArrayList<TxnDetailsDTO> getModifiedValues() {
		return modifiedValues;
	}

	public void setModifiedValues(ArrayList<TxnDetailsDTO> modifiedValues) {
		this.modifiedValues = modifiedValues;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public LeaveRequestBean getLeaveRequestDetails() {
		return leaveRequestDetails;
	}

	public void setLeaveRequestDetails(LeaveRequestBean leaveRequestDetails) {
		this.leaveRequestDetails = leaveRequestDetails;
	}

	public void setLoanHBARequestDetails(
			LoanHBARequestProcessBean loanHBARequestDetails) {
		this.loanHBARequestDetails = loanHBARequestDetails;
	}

	public LoanHBARequestProcessBean getLoanHBARequestDetails() {
		return loanHBARequestDetails;
	}

	public void setLoanHBAPaymentDetails(LoanHBAPaymentDTO loanHBAPaymentDetails) {
		this.loanHBAPaymentDetails = loanHBAPaymentDetails;
	}

	public LoanHBAPaymentDTO getLoanHBAPaymentDetails() {
		return loanHBAPaymentDetails;
	}

	public void setAdvanceReq(float advanceReq) {
		this.advanceReq = advanceReq;
	}

	public float getAdvanceReq() {
		return advanceReq;
	}

	public void setNoOfInstalPrinciple(int noOfInstalPrinciple) {
		this.noOfInstalPrinciple = noOfInstalPrinciple;
	}

	public int getNoOfInstalPrinciple() {
		return noOfInstalPrinciple;
	}

	public void setNoOfInstalInterest(int noOfInstalInterest) {
		this.noOfInstalInterest = noOfInstalInterest;
	}

	public int getNoOfInstalInterest() {
		return noOfInstalInterest;
	}

	public String getReqAmount() {
		return reqAmount;
	}

	public void setReqAmount(String reqAmount) {
		this.reqAmount = reqAmount;
	}

	public int getRequestedInstalments() {
		return requestedInstalments;
	}

	public void setRequestedInstalments(int requestedInstalments) {
		this.requestedInstalments = requestedInstalments;
	}

	public int getRequestedInterestInstalments() {
		return requestedInterestInstalments;
	}

	public void setRequestedInterestInstalments(int requestedInterestInstalments) {
		this.requestedInterestInstalments = requestedInterestInstalments;
	}

	public void setFpaRequestDetails(FPARequestProcessBean fpaRequestDetails) {
		this.fpaRequestDetails = fpaRequestDetails;
	}

	public FPARequestProcessBean getFpaRequestDetails() {
		return fpaRequestDetails;
	}

	public void setSanctionedFpaAmount(float sanctionedFpaAmount) {
		this.sanctionedFpaAmount = sanctionedFpaAmount;
	}

	public float getSanctionedFpaAmount() {
		return sanctionedFpaAmount;
	}

	public void setSanctionedFpaDate(Date sanctionedFpaDate) {
		this.sanctionedFpaDate = sanctionedFpaDate;
	}

	public Date getSanctionedFpaDate() {
		return sanctionedFpaDate;
	}
	public TrainingCirculationDTO getTrainingCirculationDto() {
		return trainingCirculationDto;
	}


	public void setTrainingCirculationDto(TrainingCirculationDTO trainingCirculationDto) {
		this.trainingCirculationDto = trainingCirculationDto;
	}


	public void setDynamicTableList(List<WorkflowDynamicTablesDTO> dynamicTableList) {
		this.dynamicTableList = dynamicTableList;
	}

	public List<WorkflowDynamicTablesDTO> getDynamicTableList() {
		return dynamicTableList;
	}

	public String getFoodAmtRepresentation() {
		return foodAmtRepresentation;
	}

	public void setFoodAmtRepresentation(String foodAmtRepresentation) {
		this.foodAmtRepresentation = foodAmtRepresentation;
	}

	public String getTotalFoodAmount() {
		return totalFoodAmount;
	}

	public void setTotalFoodAmount(String totalFoodAmount) {
		this.totalFoodAmount = totalFoodAmount;
	}

	public float getTotalTadaTdCalAmount() {
		return totalTadaTdCalAmount;
	}

	public void setTotalTadaTdCalAmount(float totalTadaTdCalAmount) {
		this.totalTadaTdCalAmount = totalTadaTdCalAmount;
	}

	public String getStrTicketFareAftRes() {
		return strTicketFareAftRes;
	}

	public void setStrTicketFareAftRes(String strTicketFareAftRes) {
		this.strTicketFareAftRes = strTicketFareAftRes;
	}

	public String getStrClaimedJourneyAmtAftRes() {
		return strClaimedJourneyAmtAftRes;
	}

	public void setStrClaimedJourneyAmtAftRes(String strClaimedJourneyAmtAftRes) {
		this.strClaimedJourneyAmtAftRes = strClaimedJourneyAmtAftRes;
	}

	public String getStrAccAmtAftRes() {
		return strAccAmtAftRes;
	}

	public void setStrAccAmtAftRes(String strAccAmtAftRes) {
		this.strAccAmtAftRes = strAccAmtAftRes;
	}

	public String getStrClaimedAccAmtAftRes() {
		return strClaimedAccAmtAftRes;
	}

	public void setStrClaimedAccAmtAftRes(String strClaimedAccAmtAftRes) {
		this.strClaimedAccAmtAftRes = strClaimedAccAmtAftRes;
	}

	public String getStrRMAKmDisAftRes() {
		return strRMAKmDisAftRes;
	}

	public void setStrRMAKmDisAftRes(String strRMAKmDisAftRes) {
		this.strRMAKmDisAftRes = strRMAKmDisAftRes;
	}

	public String getStrRMAKmAmtAftRes() {
		return strRMAKmAmtAftRes;
	}

	public void setStrRMAKmAmtAftRes(String strRMAKmAmtAftRes) {
		this.strRMAKmAmtAftRes = strRMAKmAmtAftRes;
	}

	public String getStrClaimedRMAKmAftRes() {
		return strClaimedRMAKmAftRes;
	}

	public void setStrClaimedRMAKmAftRes(String strClaimedRMAKmAftRes) {
		this.strClaimedRMAKmAftRes = strClaimedRMAKmAftRes;
	}

	public String getStrRMALocalDisAftRes() {
		return strRMALocalDisAftRes;
	}

	public void setStrRMALocalDisAftRes(String strRMALocalDisAftRes) {
		this.strRMALocalDisAftRes = strRMALocalDisAftRes;
	}

	public String getStrRMALocalAmtAftRes() {
		return strRMALocalAmtAftRes;
	}

	public void setStrRMALocalAmtAftRes(String strRMALocalAmtAftRes) {
		this.strRMALocalAmtAftRes = strRMALocalAmtAftRes;
	}

	public String getStrClaimedRMALocalAftRes() {
		return strClaimedRMALocalAftRes;
	}

	public void setStrClaimedRMALocalAftRes(String strClaimedRMALocalAftRes) {
		this.strClaimedRMALocalAftRes = strClaimedRMALocalAftRes;
	}

	public LtcPenalMasterDTO getLtcPenalInterestRate() {
		return ltcPenalInterestRate;
	}

	public void setLtcPenalInterestRate(LtcPenalMasterDTO ltcPenalInterestRate) {
		this.ltcPenalInterestRate = ltcPenalInterestRate;
	}

	public String getStrFoodAmtAftRes() {
		return strFoodAmtAftRes;
	}

	public void setStrFoodAmtAftRes(String strFoodAmtAftRes) {
		this.strFoodAmtAftRes = strFoodAmtAftRes;
	}

	public String getFoodAmtRep() {
		return foodAmtRep;
	}

	public void setFoodAmtRep(String foodAmtRep) {
		this.foodAmtRep = foodAmtRep;
	}




    public TrainingNominationDTO getTrainingNominationDto() {
		return trainingNominationDto;
	}

	public void setTrainingNominationDto(TrainingNominationDTO trainingNominationDto) {
		this.trainingNominationDto = trainingNominationDto;
	}

	public TadaTdTotalFoodClaimDTO getTdTotalFoodDetails() {
		return tdTotalFoodDetails;
	}

	public void setTdTotalFoodDetails(TadaTdTotalFoodClaimDTO tdTotalFoodDetails) {
		this.tdTotalFoodDetails = tdTotalFoodDetails;
	}

	public List<TadaTdReqJourneyDTO> getTadaTdReqJourneyList() {
		return tadaTdReqJourneyList;
	}

	public void setTadaTdReqJourneyList(
			List<TadaTdReqJourneyDTO> tadaTdReqJourneyList) {
		this.tadaTdReqJourneyList = tadaTdReqJourneyList;
	}

	public float getIssuedAdvAmount() {
		return issuedAdvAmount;
	}

	public void setIssuedAdvAmount(float issuedAdvAmount) {
		this.issuedAdvAmount = issuedAdvAmount;
	}

	public String getStrRMADayAmtPerDay() {
		return strRMADayAmtPerDay;
	}

	public void setStrRMADayAmtPerDay(String strRMADayAmtPerDay) {
		this.strRMADayAmtPerDay = strRMADayAmtPerDay;
	}

	public String getStrRMADayAmtAftRes() {
		return strRMADayAmtAftRes;
	}

	public void setStrRMADayAmtAftRes(String strRMADayAmtAftRes) {
		this.strRMADayAmtAftRes = strRMADayAmtAftRes;
	}

	public int getQuarterAmount() {
		return quarterAmount;
	}


	public void setQuarterAmount(int quarterAmount) {
		this.quarterAmount = quarterAmount;
	}

	public List<DesignationDTO> getDesignationList() {
		return designationList;
	}
	public int getHalfyearlyAmount() {
		return halfyearlyAmount;
	}

	public void setHalfyearlyAmount(int halfyearlyAmount) {
		this.halfyearlyAmount = halfyearlyAmount;
	}
	public void setDoPartShowFlag(String doPartShowFlag) {
		this.doPartShowFlag = doPartShowFlag;
	}

	public void setDesignationList(List<DesignationDTO> designationList) {
		this.designationList = designationList;
	}

	public String getDoPartShowFlag() {
		return doPartShowFlag;
	}

	public String getTdSettlementReqId() {
		return tdSettlementReqId;
	}

	public void setTdSettlementReqId(String tdSettlementReqId) {
		this.tdSettlementReqId = tdSettlementReqId;
	}

	public String getTdSettlementHistoryID() {
		return tdSettlementHistoryID;
	}

	public void setTdSettlementHistoryID(String tdSettlementHistoryID) {
		this.tdSettlementHistoryID = tdSettlementHistoryID;
	}

	public String getTdSettStrStatus() {
		return tdSettStrStatus;
	}

	public void setTdSettStrStatus(String tdSettStrStatus) {
		this.tdSettStrStatus = tdSettStrStatus;
	}

	public int getTdSettStatus() {
		return tdSettStatus;
	}

	public void setTdSettStatus(int tdSettStatus) {
		this.tdSettStatus = tdSettStatus;
	}

	public String getTdReimReqId() {
		return tdReimReqId;
	}

	public void setTdReimReqId(String tdReimReqId) {
		this.tdReimReqId = tdReimReqId;
	}

	public String getTdReimHistoryID() {
		return tdReimHistoryID;
	}

	public void setTdReimHistoryID(String tdReimHistoryID) {
		this.tdReimHistoryID = tdReimHistoryID;
	}

	public String getTdReimStrStatus() {
		return tdReimStrStatus;
	}

	public void setTdReimStrStatus(String tdReimStrStatus) {
		this.tdReimStrStatus = tdReimStrStatus;
	}

	public int getTdReimStatus() {
		return tdReimStatus;
	}

	public void setTdReimStatus(int tdReimStatus) {
		this.tdReimStatus = tdReimStatus;
	}

	public List<LeaveRequestBean> getTdLeaveDetailsList() {
		return tdLeaveDetailsList;
	}

	public void setTdLeaveDetailsList(List<LeaveRequestBean> tdLeaveDetailsList) {
		this.tdLeaveDetailsList = tdLeaveDetailsList;
	}

	public MRODetailsDTO getMroDetails() {
		return mroDetails;
	}

	public void setMroDetails(MRODetailsDTO mroDetails) {
		this.mroDetails = mroDetails;
	}

	public MROPaymentDetailsDTO getMroPaymentDetails() {
		return mroPaymentDetails;
	}

	public void setMroPaymentDetails(MROPaymentDetailsDTO mroPaymentDetails) {
		this.mroPaymentDetails = mroPaymentDetails;
	}

	public TadaApprovalRequestDTO getTadaAmendmentDetails() {
		return tadaAmendmentDetails;
	}

	public void setTadaAmendmentDetails(TadaApprovalRequestDTO tadaAmendmentDetails) {
		this.tadaAmendmentDetails = tadaAmendmentDetails;
	}

	public List<MRODetailsDTO> getMroDetailsList() {
		return mroDetailsList;
	}

	public void setMroDetailsList(List<MRODetailsDTO> mroDetailsList) {
		this.mroDetailsList = mroDetailsList;
	}

	public List<MROPaymentDetailsDTO> getMroPaymentDetailsList() {
		return mroPaymentDetailsList;
	}

	public void setMroPaymentDetailsList(
			List<MROPaymentDetailsDTO> mroPaymentDetailsList) {
		this.mroPaymentDetailsList = mroPaymentDetailsList;
	}

	public String getProjectDirSfid() {
		return projectDirSfid;
	}

	public void setProjectDirSfid(String projectDirSfid) {
		this.projectDirSfid = projectDirSfid;
	}

	public List<RequestsDTO> getMyHoldRequests() {
		return myHoldRequests;
	}

	public void setMyHoldRequests(List<RequestsDTO> myHoldRequests) {
		this.myHoldRequests = myHoldRequests;
	}

	public List<RequestsDTO> getTotalHoldRequests() {
		return totalHoldRequests;
	}

	public void setTotalHoldRequests(List<RequestsDTO> totalHoldRequests) {
		this.totalHoldRequests = totalHoldRequests;
	}

	public List<OrganizationsDTO> getOrganizationsList() {
		return organizationsList;
	}

	public void setOrganizationsList(List<OrganizationsDTO> organizationsList) {
		this.organizationsList = organizationsList;
	}

	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public List<TadaTdRMADailyDTO> getTdRMADailyList() {
		return tdRMADailyList;
	}

	public void setTdRMADailyList(List<TadaTdRMADailyDTO> tdRMADailyList) {
		this.tdRMADailyList = tdRMADailyList;
	}

	public String getStrRMADailyDisAftRes() {
		return strRMADailyDisAftRes;
	}

	public void setStrRMADailyDisAftRes(String strRMADailyDisAftRes) {
		this.strRMADailyDisAftRes = strRMADailyDisAftRes;
	}

	public String getStrRMADailyAmtAftRes() {
		return strRMADailyAmtAftRes;
	}

	public void setStrRMADailyAmtAftRes(String strRMADailyAmtAftRes) {
		this.strRMADailyAmtAftRes = strRMADailyAmtAftRes;
	}

	public String getStrClaimedRMADailyAftRes() {
		return strClaimedRMADailyAftRes;
	}

	public void setStrClaimedRMADailyAftRes(String strClaimedRMADailyAftRes) {
		this.strClaimedRMADailyAftRes = strClaimedRMADailyAftRes;
	}

	public String getIssueAuthority() {
		return issueAuthority;
	}

	public void setIssueAuthority(String issueAuthority) {
		this.issueAuthority = issueAuthority;
	}

	public String getTutionFeeOrgRoleId() {
		return tutionFeeOrgRoleId;
	}

	public void setTutionFeeOrgRoleId(String tutionFeeOrgRoleId) {
		this.tutionFeeOrgRoleId = tutionFeeOrgRoleId;
	}

	public float getTotalAdvAmount() {
		return totalAdvAmount;
	}

	public void setTotalAdvAmount(float totalAdvAmount) {
		this.totalAdvAmount = totalAdvAmount;
	}
	public List<TadaTdAccDetDayRepDTO> getDaNewAccDayRepList() {
		return daNewAccDayRepList;
	}

	public void setDaNewAccDayRepList(List<TadaTdAccDetDayRepDTO> daNewAccDayRepList) {
		this.daNewAccDayRepList = daNewAccDayRepList;
	}
    public String getUserRemarks() {
		return userRemarks;
	}

	public void setUserRemarks(String userRemarks) {
		this.userRemarks = userRemarks;
	}

	public void setPrescriptionFileName(String prescriptionFileName) {
		this.prescriptionFileName = prescriptionFileName;
	}

	public String getPrescriptionFileName() {
		return prescriptionFileName;
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
   public String getServiceTax() {
		return serviceTax;
	}

	public void setServiceTax(String serviceTax) {
		this.serviceTax = serviceTax;
	}

	public String getTotAmount() {
		return totAmount;
	}

	public void setTotAmount(String totAmount) {
		this.totAmount = totAmount;
	}

	public String getCashAssignEligibilitySfid() {
		return CashAssignEligibilitySfid;
	}

	public void setCashAssignEligibilitySfid(String cashAssignEligibilitySfid) {
		CashAssignEligibilitySfid = cashAssignEligibilitySfid;
	}

	public String getClaimedAmount() {
		return claimedAmount;
	}

	public void setClaimedAmount(String claimedAmount) {
		this.claimedAmount = claimedAmount;
	}

	public String getTeleSanctionedAmount() {
		return teleSanctionedAmount;
	}

	public void setTeleSanctionedAmount(String teleSanctionedAmount) {
		this.teleSanctionedAmount = teleSanctionedAmount;
	}

	public String getInternetFlag() {
		return internetFlag;
	}

	public void setInternetFlag(String internetFlag) {
		this.internetFlag = internetFlag;
	}

	public int getCashAssignmentAmount() {
		return cashAssignmentAmount;
	}

	public void setCashAssignmentAmount(int cashAssignmentAmount) {
		this.cashAssignmentAmount = cashAssignmentAmount;
	}

	public List<TelephoneDesigEligibilityDetailsDTO> getTeleMaxAmount() {
		return teleMaxAmount;
	}

	public void setTeleMaxAmount(
			List<TelephoneDesigEligibilityDetailsDTO> teleMaxAmount) {
		this.teleMaxAmount = teleMaxAmount;
	}




	public String getCghsreque() {
		return cghsreque;
	}

	public void setCghsreque(String cghsreque) {
		this.cghsreque = cghsreque;
	}

	public String getRequestadvlink() {
		return requestadvlink;
	}

	public void setRequestadvlink(String requestadvlink) {
		this.requestadvlink = requestadvlink;
	}




	public String getDvNumber() {
		return dvNumber;
	}

	public void setDvNumber(String dvNumber) {
		this.dvNumber = dvNumber;
	}

	public String getDvDate() {
		return dvDate;
	}

	public void setDvDate(String dvDate) {
		this.dvDate = dvDate;
	}

	public String getApprovedSantionNo() {
		return approvedSantionNo;
	}

	public void setApprovedSantionNo(String approvedSantionNo) {
		this.approvedSantionNo = approvedSantionNo;
	}

	public String getApprovedBillNo() {
		return approvedBillNo;
	}

	public void setApprovedBillNo(String approvedBillNo) {
		this.approvedBillNo = approvedBillNo;
	}

	public String getCdaApprovedAmt() {
		return cdaApprovedAmt;
	}

	public void setCdaApprovedAmt(String cdaApprovedAmt) {
		this.cdaApprovedAmt = cdaApprovedAmt;
	}

	public String getTeleEligibleAmt() {
		return teleEligibleAmt;
	}

	public String getCghsadvanceRequestId() {
		return cghsadvanceRequestId;
	}

	public void setCghsadvanceRequestId(String cghsadvanceRequestId) {
		this.cghsadvanceRequestId = cghsadvanceRequestId;
	}

	public int getCghsadvHistoryID() {
		return cghsadvHistoryID;
	}

	public void setCghsadvHistoryID(int cghsadvHistoryID) {
		this.cghsadvHistoryID = cghsadvHistoryID;
	}

	public String getCghssetlementRequestId() {
		return cghssetlementRequestId;
	}

	public void setCghssetlementRequestId(String cghssetlementRequestId) {
		this.cghssetlementRequestId = cghssetlementRequestId;
	}


	public void setTeleEligibleAmt(String teleEligibleAmt) {
		this.teleEligibleAmt = teleEligibleAmt;
	}


	public String getCghssetlementHistoryID() {
		return cghssetlementHistoryID;
	}

	public void setCghssetlementHistoryID(String cghssetlementHistoryID) {
		this.cghssetlementHistoryID = cghssetlementHistoryID;
	}

	public String getCghsreimbursementId() {
		return cghsreimbursementId;
	}

	public String getCghsreimbursementHistoryId() {
		return cghsreimbursementHistoryId;
	}

	public void setCghsreimbursementId(String cghsreimbursementId) {
		this.cghsreimbursementId = cghsreimbursementId;
	}

	public void setCghsreimbursementHistoryId(String cghsreimbursementHistoryId) {
		this.cghsreimbursementHistoryId = cghsreimbursementHistoryId;
	}

	public String getRequestidApprival() {
		return requestidApprival;
	}

	public void setRequestidApprival(String requestidApprival) {
		this.requestidApprival = requestidApprival;
	}

	public int getHistoryrequestidApprival() {
		return historyrequestidApprival;
	}

	public void setHistoryrequestidApprival(int historyrequestidApprival) {
		this.historyrequestidApprival = historyrequestidApprival;
	}

	public ErpLoanRequestDTO getErpLoanRequestDTO() {
		return erpLoanRequestDTO;
	}

	public void setErpLoanRequestDTO(ErpLoanRequestDTO erpLoanRequestDTO) {
		this.erpLoanRequestDTO = erpLoanRequestDTO;
	}

	public Float getApprovedAmount() {
		return approvedAmount;
	}

	public void setApprovedAmount(Float approvedAmount) {
		this.approvedAmount = approvedAmount;
	}

	
}
