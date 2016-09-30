package com.callippus.web.tada.beans.request;

import java.util.Date;
import java.util.List;

import com.callippus.web.beans.dto.DepartmentsDTO;
import com.callippus.web.beans.dto.EmpPaymentsDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.requests.RequestCommonBean;
import com.callippus.web.beans.workflowmapping.WorkFlowMappingBean;
import com.callippus.web.cghs.beans.dto.SingingAuthorityDTO;
import com.callippus.web.ltc.dto.LtcPenalMasterDTO;
import com.callippus.web.tada.dto.CityTypeDTO;
import com.callippus.web.tada.dto.TaEntitleTypeDTO;
import com.callippus.web.tada.dto.TaTravelTypeMapDTO;
import com.callippus.web.tada.dto.TadaApprovalRequestDTO;
import com.callippus.web.tada.dto.TadaDaNewDetailsDTO;
import com.callippus.web.tada.dto.TadaDetailsDTO;
import com.callippus.web.tada.dto.TadaEmpDetailsDTO;
import com.callippus.web.tada.dto.TadaTdDaNewDetailsDTO;
import com.callippus.web.tada.dto.TadaTdDaNewFoodDetailsDTO;
import com.callippus.web.tada.dto.TadaTdDaOldDetailsDTO;
import com.callippus.web.tada.dto.TadaTdJDADetailsDTO;
import com.callippus.web.tada.dto.TadaTdJourneyDTO;
import com.callippus.web.tada.dto.TadaTdLeaveDetailsDTO;
import com.callippus.web.tada.dto.TadaTdRMADayDTO;
import com.callippus.web.tada.dto.TadaTdRMAKmDTO;
import com.callippus.web.tada.dto.TadaTdReqJourneyDTO;
import com.callippus.web.tada.dto.TadaTdTxnDTO;
import com.callippus.web.tada.dto.TravelTypeDTO;

public class TadaRequestBean {
	
	private float taxi;
	private float onTransit;
	private String name;
	private String designation;
	private String sfid;
	private String phnNo;
	private String basicPay;
	private String gradePay;
	private String workingPlace;
	private Date departureDate;
	private String entitlementClass;
	private String conveyanceMode;
	private int stayDuration;
	private String tdWorkPlace;
	private String purpose;
	private String authorizedMove;
	private String tadaRequirement;
	private String tatkalQuota;
	private String daType;
	private String daOldRequirement;
	private String thoousnd;
	
	private String param;
	private String result;
	private List<TadaEmpDetailsDTO> empDetails;
	private String nameInServiceBook;
	private EmployeeBean empDetailsList;
	private String type;
	private String gpfNo;
	private EmpPaymentsDTO payDetailsList;
	private List<TaTravelTypeMapDTO> conveyanceModeList;
	private DepartmentsDTO deptDTO;
	
	private String travelType;
	private int key;
	private int value;
	private int travelTypeId;
	private List<TaEntitleTypeDTO> entitleTypeList;
	private List<TravelTypeDTO> travelTypeList;
	private EmployeeBean empBean;
	private int tadaAdvanceAmount;
	private Date applyDate;
	private String referenceRequestID;
	private String requestId;
	private String requestID;
	private String departureDateOne;
	private String remarks;
	private String id;
	private String sfID;
	private String requestType;
	private String amendmentType;
	private String amendmentReqId;
	private List<TadaTdTxnDTO> tadaTdTxnDetails;
	private TadaApprovalRequestDTO tadaApprovalRequestDTO;
	
	private int accommodationBill;
	private int foodBill;
	private String milageAllw;
	private String travelBy;
	private int distance;
	private int amount;
	private List<TadaDaNewDetailsDTO> daNewDetailsList;
	private List<TadaDetailsDTO> daOldDetailsList;
	
	private String cityName;
	private List<CityTypeDTO> cityTypeList;
	private String jsonValue;
	private float claimAmount;
	private List<LtcPenalMasterDTO> penalMasterList;
	
	private Date toDayDate;
	private String toDayDateOne;
	private String message;
	
	private String accountentSign;
	private String sanctionNo;
	private String billNo;
	private float cdaAmount;
	private String dvNumber;
	private Date dvDate;
	private List<SingingAuthorityDTO> accountOfficerList;
	private List<TadaTdDaNewDetailsDTO> tdDaNewDetailsList;
    private List<TadaTdRMAKmDTO> tdRMAKmList;
    private List<TadaTdJourneyDTO> tdJourneyList;
	private List<TadaTdRMADayDTO> tdRMADayList;
	private List<TadaTdDaOldDetailsDTO> tdDaOldDetailsList;
	private String daTypeRate;
	private String fromPlaceLocal;
	private String toPlaceLocal;
	private float ticketFare;
	private List<TadaTdDaNewFoodDetailsDTO> foodDetailsDayRep;
	private String requesterSfid;
	private int noOfDaysAcc;
	private int noOfDaysFood;
	private float accAmtPerDay;
	private float foodAmtPerDay;
	private float totalAccAmt;
	private float totalFoodAmt;
	private TadaDaNewDetailsDTO tadaDaNewDetails;
	private String fromPlace;
	private String toPlace;
	private List<TadaTdReqJourneyDTO> tadaTdReqJourneyList;
	private List<TadaTdJDADetailsDTO> tadaTdJdaList;
	private String projDirName;
	private String historyID;
	private String statusMsg;
	private String back;
	private String leaveRequirement;
	private String leaveId;
	private List<KeyValueDTO> tdLeaveTypeList;
	private List<TadaTdLeaveDetailsDTO> tdLeaveDetailsList;
	private Date arrivalDate;
	private String strArrivalDate;
	private String reason;
	private Date amendArrivalDate;
	private Date amendDeptDate;
	private String strAmendArrDate;
	private String strAmendDeptDate;
	private List<TadaTdReqJourneyDTO> tdReqJourneyList;
	private String entitlementExemption;
	private String requesterRoleID;
	private List<WorkFlowMappingBean> buildUpWfList;
	private List<WorkFlowMappingBean> projectWfList;
	private List<WorkFlowMappingBean> buildUpScGWflist;
	private List<WorkFlowMappingBean> projectScGWfList;
	private String bossId;
	private String tdId;
	private List<RequestCommonBean> pendingReqList;
	private List<KeyValueDTO> keyList;
	private String dyTechDirId;
	private String delegateTo;
	private String absenceSfid;
	private String adId;
	private String bossesBossId;
	private String labDirId;
	private String projectName;
	private float ticketCancelCharges;
	private String ticketCancelReason;
	private float totalAdvAmount;
	private String searchWith;
	private String directorate;
	private List<TadaApprovalRequestDTO> tdAppliedUsersList;
	private String userRemarks;
	private List<DepartmentsDTO> directorateList;
	private int status;
	
	private Float tadaDaPercentage; //new Da Percentatge
	private Float payDaValue;
	
	private String tadaTimeBoundPast;
	private String tadaTimeBoundFuture;
	
	
	
	public float getTaxi() {
		return taxi;
	}

	public void setTaxi(float taxi) {
		this.taxi = taxi;
	}

	public float getOnTransit() {
		return onTransit;
	}

	public void setOnTransit(float onTransit) {
		this.onTransit = onTransit;
	}

	public void setOnTransit(int onTransit) {
		this.onTransit = onTransit;
	}

	public String getThoousnd() {
		return thoousnd;
	}
	public void setThoousnd(String thoousnd) {
		this.thoousnd = thoousnd;
	}
	public String getTadaTimeBoundPast() {
		return tadaTimeBoundPast;
	}
	public void setTadaTimeBoundPast(String tadaTimeBoundPast) {
		this.tadaTimeBoundPast = tadaTimeBoundPast;
	}
	public String getTadaTimeBoundFuture() {
		return tadaTimeBoundFuture;
	}
	public void setTadaTimeBoundFuture(String tadaTimeBoundFuture) {
		this.tadaTimeBoundFuture = tadaTimeBoundFuture;
	}
	public Float getTadaDaPercentage() {
		return tadaDaPercentage;
	}
	public void setTadaDaPercentage(Float tadaDaPercentage) {
		this.tadaDaPercentage = tadaDaPercentage;
	}
	public Float getPayDaValue() {
		return payDaValue;
	}
	public void setPayDaValue(Float payDaValue) {
		this.payDaValue = payDaValue;
	}
	public String getEntitlementExemption() {
		return entitlementExemption;
	}
	public void setEntitlementExemption(String entitlementExemption) {
		this.entitlementExemption = entitlementExemption;
	}
	public String getToPlace() {
		return toPlace;
	}
	public void setToPlace(String toPlace) {
		this.toPlace = toPlace;
	}
	public String getFromPlace() {
		return fromPlace;
	}
	public void setFromPlace(String fromPlace) {
		this.fromPlace = fromPlace;
	}
	public String getRequesterSfid() {
		return requesterSfid;
	}
	public void setRequesterSfid(String requesterSfid) {
		this.requesterSfid = requesterSfid;
	}
	public List<TadaTdDaNewFoodDetailsDTO> getFoodDetailsDayRep() {
		return foodDetailsDayRep;
	}
	public void setFoodDetailsDayRep(
			List<TadaTdDaNewFoodDetailsDTO> foodDetailsDayRep) {
		this.foodDetailsDayRep = foodDetailsDayRep;
	}
	public float getTicketFare() {
		return ticketFare;
	}
	public void setTicketFare(float ticketFare) {
		this.ticketFare = ticketFare;
	}
	public DepartmentsDTO getDeptDTO() {
		return deptDTO;
	}
	public void setDeptDTO(DepartmentsDTO deptDTO) {
		this.deptDTO = deptDTO;
	}
	public String getFromPlaceLocal() {
		return fromPlaceLocal;
	}
	public void setFromPlaceLocal(String fromPlaceLocal) {
		this.fromPlaceLocal = fromPlaceLocal;
	}
	public String getToPlaceLocal() {
		return toPlaceLocal;
	}
	public void setToPlaceLocal(String toPlaceLocal) {
		this.toPlaceLocal = toPlaceLocal;
	}
	public String getDaTypeRate() {
		return daTypeRate;
	}
	public void setDaTypeRate(String daTypeRate) {
		this.daTypeRate = daTypeRate;
	}
	public List<TadaTdDaOldDetailsDTO> getTdDaOldDetailsList() {
		return tdDaOldDetailsList;
	}
	public void setTdDaOldDetailsList(List<TadaTdDaOldDetailsDTO> tdDaOldDetailsList) {
		this.tdDaOldDetailsList = tdDaOldDetailsList;
	}
	public List<TadaTdRMADayDTO> getTdRMADayList() {
		return tdRMADayList;
	}
	public void setTdRMADayList(List<TadaTdRMADayDTO> tdRMADayList) {
		this.tdRMADayList = tdRMADayList;
	}
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
	public List<SingingAuthorityDTO> getAccountOfficerList() {
		return accountOfficerList;
	}
	public void setAccountOfficerList(List<SingingAuthorityDTO> accountOfficerList) {
		this.accountOfficerList = accountOfficerList;
	}
	public String getAccountentSign() {
		return accountentSign;
	}
	public void setAccountentSign(String accountentSign) {
		this.accountentSign = accountentSign;
	}
	public String getSanctionNo() {
		return sanctionNo;
	}
	public void setSanctionNo(String sanctionNo) {
		this.sanctionNo = sanctionNo;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public float getCdaAmount() {
		return cdaAmount;
	}
	public void setCdaAmount(float cdaAmount) {
		this.cdaAmount = cdaAmount;
	}
	public String getDvNumber() {
		return dvNumber;
	}
	public void setDvNumber(String dvNumber) {
		this.dvNumber = dvNumber;
	}
	public Date getDvDate() {
		return dvDate;
	}
	public void setDvDate(Date dvDate) {
		this.dvDate = dvDate;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getToDayDateOne() {
		return toDayDateOne;
	}
	public void setToDayDateOne(String toDayDateOne) {
		this.toDayDateOne = toDayDateOne;
	}
	public Date getToDayDate() {
		return toDayDate;
	}
	public void setToDayDate(Date toDayDate) {
		this.toDayDate = toDayDate;
	}
	public List<LtcPenalMasterDTO> getPenalMasterList() {
		return penalMasterList;
	}
	public void setPenalMasterList(List<LtcPenalMasterDTO> penalMasterList) {
		this.penalMasterList = penalMasterList;
	}
	public float getClaimAmount() {
		return claimAmount;
	}
	public void setClaimAmount(float claimAmount) {
		this.claimAmount = claimAmount;
	}
	public String getJsonValue() {
		return jsonValue;
	}
	public void setJsonValue(String jsonValue) {
		this.jsonValue = jsonValue;
	}
	public List<CityTypeDTO> getCityTypeList() {
		return cityTypeList;
	}
	public void setCityTypeList(List<CityTypeDTO> cityTypeList) {
		this.cityTypeList = cityTypeList;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public int getAccommodationBill() {
		return accommodationBill;
	}
	public void setAccommodationBill(int accommodationBill) {
		this.accommodationBill = accommodationBill;
	}
	public int getFoodBill() {
		return foodBill;
	}
	public void setFoodBill(int foodBill) {
		this.foodBill = foodBill;
	}
	public String getMilageAllw() {
		return milageAllw;
	}
	public void setMilageAllw(String milageAllw) {
		this.milageAllw = milageAllw;
	}
	public String getTravelBy() {
		return travelBy;
	}
	public void setTravelBy(String travelBy) {
		this.travelBy = travelBy;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public List<TadaDaNewDetailsDTO> getDaNewDetailsList() {
		return daNewDetailsList;
	}
	public void setDaNewDetailsList(List<TadaDaNewDetailsDTO> daNewDetailsList) {
		this.daNewDetailsList = daNewDetailsList;
	}
	public String getDaOldRequirement() {
		return daOldRequirement;
	}
	public void setDaOldRequirement(String daOldRequirement) {
		this.daOldRequirement = daOldRequirement;
	}
	public TadaApprovalRequestDTO getTadaApprovalRequestDTO() {
		return tadaApprovalRequestDTO;
	}
	public void setTadaApprovalRequestDTO(
			TadaApprovalRequestDTO tadaApprovalRequestDTO) {
		this.tadaApprovalRequestDTO = tadaApprovalRequestDTO;
	}
	public List<TadaTdTxnDTO> getTadaTdTxnDetails() {
		return tadaTdTxnDetails;
	}
	public void setTadaTdTxnDetails(List<TadaTdTxnDTO> tadaTdTxnDetails) {
		this.tadaTdTxnDetails = tadaTdTxnDetails;
	}
	public String getAmendmentReqId() {
		return amendmentReqId;
	}
	public void setAmendmentReqId(String amendmentReqId) {
		this.amendmentReqId = amendmentReqId;
	}
	public String getAmendmentType() {
		return amendmentType;
	}
	public void setAmendmentType(String amendmentType) {
		this.amendmentType = amendmentType;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getSfID() {
		return sfID;
	}
	public void setSfID(String sfID) {
		this.sfID = sfID;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDepartureDateOne() {
		return departureDateOne;
	}
	public void setDepartureDateOne(String departureDateOne) {
		this.departureDateOne = departureDateOne;
	}
	public String getReferenceRequestID() {
		return referenceRequestID;
	}
	public void setReferenceRequestID(String referenceRequestID) {
		this.referenceRequestID = referenceRequestID;
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
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public int getTadaAdvanceAmount() {
		return tadaAdvanceAmount;
	}
	public void setTadaAdvanceAmount(int tadaAdvanceAmount) {
		this.tadaAdvanceAmount = tadaAdvanceAmount;
	}
	public EmployeeBean getEmpBean() {
		return empBean;
	}
	public void setEmpBean(EmployeeBean empBean) {
		this.empBean = empBean;
	}
	public String getDaType() {
		return daType;
	}
	public void setDaType(String daType) {
		this.daType = daType;
	}
	public List<TravelTypeDTO> getTravelTypeList() {
		return travelTypeList;
	}
	public void setTravelTypeList(List<TravelTypeDTO> travelTypeList) {
		this.travelTypeList = travelTypeList;
	}
	public List<TaEntitleTypeDTO> getEntitleTypeList() {
		return entitleTypeList;
	}
	public void setEntitleTypeList(List<TaEntitleTypeDTO> entitleTypeList) {
		this.entitleTypeList = entitleTypeList;
	}
	public String getTravelType() {
		return travelType;
	}
	public void setTravelType(String travelType) {
		this.travelType = travelType;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getTravelTypeId() {
		return travelTypeId;
	}
	public void setTravelTypeId(int travelTypeId) {
		this.travelTypeId = travelTypeId;
	}
	
	
	public List<TaTravelTypeMapDTO> getConveyanceModeList() {
		return conveyanceModeList;
	}
	public void setConveyanceModeList(List<TaTravelTypeMapDTO> conveyanceModeList) {
		this.conveyanceModeList = conveyanceModeList;
	}
	public String getWorkingPlace() {
		return workingPlace;
	}
	public void setWorkingPlace(String workingPlace) {
		this.workingPlace = workingPlace;
	}
	public EmpPaymentsDTO getPayDetailsList() {
		return payDetailsList;
	}
	public void setPayDetailsList(EmpPaymentsDTO payDetailsList) {
		this.payDetailsList = payDetailsList;
	}
	public String getGpfNo() {
		return gpfNo;
	}
	public void setGpfNo(String gpfNo) {
		this.gpfNo = gpfNo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public EmployeeBean getEmpDetailsList() {
		return empDetailsList;
	}
	public void setEmpDetailsList(EmployeeBean empDetailsList) {
		this.empDetailsList = empDetailsList;
	}
	public String getNameInServiceBook() {
		return nameInServiceBook;
	}
	public void setNameInServiceBook(String nameInServiceBook) {
		this.nameInServiceBook = nameInServiceBook;
	}
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public List<TadaEmpDetailsDTO> getEmpDetails() {
		return empDetails;
	}
	public void setEmpDetails(List<TadaEmpDetailsDTO> empDetails) {
		this.empDetails = empDetails;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Date getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
	public String getEntitlementClass() {
		return entitlementClass;
	}
	public void setEntitlementClass(String entitlementClass) {
		this.entitlementClass = entitlementClass;
	}
	public String getConveyanceMode() {
		return conveyanceMode;
	}
	public void setConveyanceMode(String conveyanceMode) {
		this.conveyanceMode = conveyanceMode;
	}
	public int getStayDuration() {
		return stayDuration;
	}
	public void setStayDuration(int stayDuration) {
		this.stayDuration = stayDuration;
	}
	
	public String getTdWorkPlace() {
		return tdWorkPlace;
	}
	public void setTdWorkPlace(String tdWorkPlace) {
		this.tdWorkPlace = tdWorkPlace;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getAuthorizedMove() {
		return authorizedMove;
	}
	public void setAuthorizedMove(String authorizedMove) {
		this.authorizedMove = authorizedMove;
	}
	public String getTadaRequirement() {
		return tadaRequirement;
	}
	public void setTadaRequirement(String tadaRequirement) {
		this.tadaRequirement = tadaRequirement;
	}
	public String getTatkalQuota() {
		return tatkalQuota;
	}
	public void setTatkalQuota(String tatkalQuota) {
		this.tatkalQuota = tatkalQuota;
	}
	public int getNoOfDaysAcc() {
		return noOfDaysAcc;
	}
	public void setNoOfDaysAcc(int noOfDaysAcc) {
		this.noOfDaysAcc = noOfDaysAcc;
	}
	public int getNoOfDaysFood() {
		return noOfDaysFood;
	}
	public void setNoOfDaysFood(int noOfDaysFood) {
		this.noOfDaysFood = noOfDaysFood;
	}
	public float getAccAmtPerDay() {
		return accAmtPerDay;
	}
	public void setAccAmtPerDay(float accAmtPerDay) {
		this.accAmtPerDay = accAmtPerDay;
	}
	public float getFoodAmtPerDay() {
		return foodAmtPerDay;
	}
	public void setFoodAmtPerDay(float foodAmtPerDay) {
		this.foodAmtPerDay = foodAmtPerDay;
	}
	public float getTotalAccAmt() {
		return totalAccAmt;
	}
	public void setTotalAccAmt(float totalAccAmt) {
		this.totalAccAmt = totalAccAmt;
	}
	public float getTotalFoodAmt() {
		return totalFoodAmt;
	}
	public void setTotalFoodAmt(float totalFoodAmt) {
		this.totalFoodAmt = totalFoodAmt;
	}
	public TadaDaNewDetailsDTO getTadaDaNewDetails() {
		return tadaDaNewDetails;
	}
	public void setTadaDaNewDetails(TadaDaNewDetailsDTO tadaDaNewDetails) {
		this.tadaDaNewDetails = tadaDaNewDetails;
	}
	public List<TadaTdReqJourneyDTO> getTadaTdReqJourneyList() {
		return tadaTdReqJourneyList;
	}
	public void setTadaTdReqJourneyList(
			List<TadaTdReqJourneyDTO> tadaTdReqJourneyList) {
		this.tadaTdReqJourneyList = tadaTdReqJourneyList;
	}
	public List<TadaDetailsDTO> getDaOldDetailsList() {
		return daOldDetailsList;
	}
	public void setDaOldDetailsList(List<TadaDetailsDTO> daOldDetailsList) {
		this.daOldDetailsList = daOldDetailsList;
	}
	public List<TadaTdJDADetailsDTO> getTadaTdJdaList() {
		return tadaTdJdaList;
	}
	public void setTadaTdJdaList(List<TadaTdJDADetailsDTO> tadaTdJdaList) {
		this.tadaTdJdaList = tadaTdJdaList;
	}
	public String getProjDirName() {
		return projDirName;
	}
	public void setProjDirName(String projDirName) {
		this.projDirName = projDirName;
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
	public String getLeaveRequirement() {
		return leaveRequirement;
	}
	public void setLeaveRequirement(String leaveRequirement) {
		this.leaveRequirement = leaveRequirement;
	}
	public String getLeaveId() {
		return leaveId;
	}
	public void setLeaveId(String leaveId) {
		this.leaveId = leaveId;
	}
	public List<KeyValueDTO> getTdLeaveTypeList() {
		return tdLeaveTypeList;
	}
	public void setTdLeaveTypeList(List<KeyValueDTO> tdLeaveTypeList) {
		this.tdLeaveTypeList = tdLeaveTypeList;
	}
	public List<TadaTdLeaveDetailsDTO> getTdLeaveDetailsList() {
		return tdLeaveDetailsList;
	}
	public void setTdLeaveDetailsList(List<TadaTdLeaveDetailsDTO> tdLeaveDetailsList) {
		this.tdLeaveDetailsList = tdLeaveDetailsList;
	}
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public String getStrArrivalDate() {
		return strArrivalDate;
	}
	public void setStrArrivalDate(String strArrivalDate) {
		this.strArrivalDate = strArrivalDate;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Date getAmendArrivalDate() {
		return amendArrivalDate;
	}
	public void setAmendArrivalDate(Date amendArrivalDate) {
		this.amendArrivalDate = amendArrivalDate;
	}
	public Date getAmendDeptDate() {
		return amendDeptDate;
	}
	public void setAmendDeptDate(Date amendDeptDate) {
		this.amendDeptDate = amendDeptDate;
	}
	public String getStrAmendArrDate() {
		return strAmendArrDate;
	}
	public void setStrAmendArrDate(String strAmendArrDate) {
		this.strAmendArrDate = strAmendArrDate;
	}
	public String getStrAmendDeptDate() {
		return strAmendDeptDate;
	}
	public void setStrAmendDeptDate(String strAmendDeptDate) {
		this.strAmendDeptDate = strAmendDeptDate;
	}
	public List<TadaTdReqJourneyDTO> getTdReqJourneyList() {
		return tdReqJourneyList;
	}
	public void setTdReqJourneyList(List<TadaTdReqJourneyDTO> tdReqJourneyList) {
		this.tdReqJourneyList = tdReqJourneyList;
	}
	public String getRequesterRoleID() {
		return requesterRoleID;
	}
	public void setRequesterRoleID(String requesterRoleID) {
		this.requesterRoleID = requesterRoleID;
	}
	public List<WorkFlowMappingBean> getBuildUpWfList() {
		return buildUpWfList;
	}
	public void setBuildUpWfList(List<WorkFlowMappingBean> buildUpWfList) {
		this.buildUpWfList = buildUpWfList;
	}
	public List<WorkFlowMappingBean> getProjectWfList() {
		return projectWfList;
	}
	public void setProjectWfList(List<WorkFlowMappingBean> projectWfList) {
		this.projectWfList = projectWfList;
	}
	public List<WorkFlowMappingBean> getBuildUpScGWflist() {
		return buildUpScGWflist;
	}
	public void setBuildUpScGWflist(List<WorkFlowMappingBean> buildUpScGWflist) {
		this.buildUpScGWflist = buildUpScGWflist;
	}
	public List<WorkFlowMappingBean> getProjectScGWfList() {
		return projectScGWfList;
	}
	public void setProjectScGWfList(List<WorkFlowMappingBean> projectScGWfList) {
		this.projectScGWfList = projectScGWfList;
	}
	public String getBossId() {
		return bossId;
	}
	public void setBossId(String bossId) {
		this.bossId = bossId;
	}
	public String getTdId() {
		return tdId;
	}
	public void setTdId(String tdId) {
		this.tdId = tdId;
	}
	public List<RequestCommonBean> getPendingReqList() {
		return pendingReqList;
	}
	public void setPendingReqList(List<RequestCommonBean> pendingReqList) {
		this.pendingReqList = pendingReqList;
	}
	public List<KeyValueDTO> getKeyList() {
		return keyList;
	}
	public void setKeyList(List<KeyValueDTO> keyList) {
		this.keyList = keyList;
	}
	public String getDyTechDirId() {
		return dyTechDirId;
	}
	public void setDyTechDirId(String dyTechDirId) {
		this.dyTechDirId = dyTechDirId;
	}
	public String getDelegateTo() {
		return delegateTo;
	}
	public void setDelegateTo(String delegateTo) {
		this.delegateTo = delegateTo;
	}
	public String getAbsenceSfid() {
		return absenceSfid;
	}
	public void setAbsenceSfid(String absenceSfid) {
		this.absenceSfid = absenceSfid;
	}
	public String getAdId() {
		return adId;
	}
	public void setAdId(String adId) {
		this.adId = adId;
	}
	public String getBossesBossId() {
		return bossesBossId;
	}
	public void setBossesBossId(String bossesBossId) {
		this.bossesBossId = bossesBossId;
	}
	public String getLabDirId() {
		return labDirId;
	}
	public void setLabDirId(String labDirId) {
		this.labDirId = labDirId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public float getTicketCancelCharges() {
		return ticketCancelCharges;
	}
	public void setTicketCancelCharges(float ticketCancelCharges) {
		this.ticketCancelCharges = ticketCancelCharges;
	}
	public String getTicketCancelReason() {
		return ticketCancelReason;
	}
	public void setTicketCancelReason(String ticketCancelReason) {
		this.ticketCancelReason = ticketCancelReason;
	}
	public float getTotalAdvAmount() {
		return totalAdvAmount;
	}
	public void setTotalAdvAmount(float totalAdvAmount) {
		this.totalAdvAmount = totalAdvAmount;
	}
	public String getSearchWith() {
		return searchWith;
	}
	public void setSearchWith(String searchWith) {
		this.searchWith = searchWith;
	}
	public String getDirectorate() {
		return directorate;
	}
	public void setDirectorate(String directorate) {
		this.directorate = directorate;
	}
	public List<TadaApprovalRequestDTO> getTdAppliedUsersList() {
		return tdAppliedUsersList;
	}
	public void setTdAppliedUsersList(
			List<TadaApprovalRequestDTO> tdAppliedUsersList) {
		this.tdAppliedUsersList = tdAppliedUsersList;
	}
	public String getUserRemarks() {
		return userRemarks;
	}
	public void setUserRemarks(String userRemarks) {
		this.userRemarks = userRemarks;
	}
	public List<DepartmentsDTO> getDirectorateList() {
		return directorateList;
	}
	public void setDirectorateList(List<DepartmentsDTO> directorateList) {
		this.directorateList = directorateList;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}


	
	

}
