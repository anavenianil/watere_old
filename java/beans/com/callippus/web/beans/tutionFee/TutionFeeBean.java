package com.callippus.web.beans.tutionFee;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.callippus.web.beans.configuration.ConfigurationBean;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.YearTypeDTO;
import com.callippus.web.beans.family.FamilyBean;
import com.callippus.web.cghs.beans.dto.FamilyMemberDetailsDTO;
import com.callippus.web.cghs.beans.dto.SingingAuthorityDTO;
import com.callippus.web.incometax.dto.PayFinYearDTO;

public class TutionFeeBean {

	
	private String param;
	private String message;
	private String remarks;
	private Date fromDate;
	private Date toDate;
	private String sfid;
	private List<FamilyBean> familyList;
	private String pk;
	private String type;
	private String dateOfBirth;
	private String classId;
	private String appliedFlag;
	private String age;
	private List<TutionFeeClaimMasterDTO> claimList;
    private String childClaimId;
    private String mainClaimList;
	private String grandTotal;
	private String requestID;
	private String requestTypeID;
	private String requestType;
	private String sfID;
	private String childName;
	private String className;
	private String othersClassName;
	private String relationType;
	private File file;
	private int childRelationId;
	private String sanctionedAmount;
	private Date sanctionedDate;
	private String ipAddress;
	private List<TutionFeeBean> tfClaimFinanceDetails;
	private List<TuitionFeeAcedemicYearDTO> classNameList;
	private String empName;
	private String claimedAmount;
	private String sanctionNo;
	private String billNo;
	private String dvNo;
	private Date dvDate;
	private String mainCDAList;
	private String maxChildToBeApplied;
	private String maxAmountPerOneChild;
	private String claimName;
	private String cdaAmount;
	private String desig;
	private List<TutionFeeBean> officersList;
	private List<TutionFeeBean> staffList;
	private int id;
	private int status;
	private String claimType;
	private String limitId;
	
	private String tutionFeeMaxLimitPerOneChild;
	private String tutionFeeMaxChildToBeClaimed;
	private String configurationDetails;
	private ConfigurationBean confBean;
	
	
	private String limit;
	private String academicType;
	private List<TutionFeeLimitMasterDTO> academicTypeList;
	private String Quarterly;
	private String halfyearly;
	private String annually;
	private List<TutionFeeLimitMasterDTO> tutionList;
	private String boardId;
	private String typeId;
	private List<KeyValueDTO> centralQuarterList;
	private List<KeyValueDTO> centralHalfList;
	private List<KeyValueDTO> stateQuarterList;
	private List<KeyValueDTO> stateHalfList;
	private List<KeyValueDTO> centralAnnualList;
	private List<KeyValueDTO> stateAnnualList;
	private int quarterAmount;
	private int halfyearlyAmount;
	private String claimType1;
	private String workFlowId;
	//private List<YearTypeDTO> yearList;
	private String fromDate1;
	private String toDate1;
	private String academicYear;
	private String typeQuartarly;
	private String typeHalfyearly;
	
	private ArrayList<FamilyMemberDetailsDTO> tutionFeeEligibilityList;
	
	private List<TutionFeeRequestDetailsDTO> tuitionFeeApplicantDetails;
	private String statusMsg;
	private String back;
	private String historyID;
	private String requestId;
	private List<TuitionFeeAcedemicYearDTO> firstChildList;
	private List<TuitionFeeAcedemicYearDTO> secondChildList;
	private int firstChildId;
	private int secondChildId;
	private String familyChildId;
	private int claimTypeId;
	private String claimOrderNo;
	private String accOfficer;
	private int financeBillNo;
	private int eligiblity;
	private int amtAdmissible;
	private List<PayFinYearDTO> yearList;
	private Map<Integer, List<TuitionFeeAcedemicYearDTO>> mapChildList;
	private Map<Integer, List<PayFinYearDTO>> mapYearList;
	private List<SingingAuthorityDTO> cfaOfficerList;
	private List<SingingAuthorityDTO> accountOfficerList;
	private String cfaOfficer;
	private String displayCalculateTotal;

	public int getFirstChildId() {
		return firstChildId;
	}

	public String getFamilyChildId() {
		return familyChildId;
	}

	public void setFamilyChildId(String familyChildId) {
		this.familyChildId = familyChildId;
	}

	public void setFirstChildId(int firstChildId) {
		this.firstChildId = firstChildId;
	}

	public int getSecondChildId() {
		return secondChildId;
	}

	public void setSecondChildId(int secondChildId) {
		this.secondChildId = secondChildId;
	}

	public ArrayList<FamilyMemberDetailsDTO> getTutionFeeEligibilityList() {
		return tutionFeeEligibilityList;
	}

	public void setTutionFeeEligibilityList(
			ArrayList<FamilyMemberDetailsDTO> tutionFeeEligibilityList) {
		this.tutionFeeEligibilityList = tutionFeeEligibilityList;
	}

	public List<KeyValueDTO> getCentralAnnualList() {
		return centralAnnualList;
	}

	public void setCentralAnnualList(List<KeyValueDTO> centralAnnualList) {
		this.centralAnnualList = centralAnnualList;
	}

	public List<KeyValueDTO> getStateAnnualList() {
		return stateAnnualList;
	}

	public void setStateAnnualList(List<KeyValueDTO> stateAnnualList) {
		this.stateAnnualList = stateAnnualList;
	}

	public int getQuarterAmount() {
		return quarterAmount;
	}

	public void setQuarterAmount(int quarterAmount) {
		this.quarterAmount = quarterAmount;
	}

	public int getHalfyearlyAmount() {
		return halfyearlyAmount;
	}

	public void setHalfyearlyAmount(int halfyearlyAmount) {
		this.halfyearlyAmount = halfyearlyAmount;
	}

	public String getLimitId() {
		return limitId;
	}

	public void setLimitId(String limitId) {
		this.limitId = limitId;
	}

	public List<KeyValueDTO> getCentralQuarterList() {
		return centralQuarterList;
	}

	public void setCentralQuarterList(List<KeyValueDTO> centralQuarterList) {
		this.centralQuarterList = centralQuarterList;
	}

	public List<KeyValueDTO> getCentralHalfList() {
		return centralHalfList;
	}

	public void setCentralHalfList(List<KeyValueDTO> centralHalfList) {
		this.centralHalfList = centralHalfList;
	}

	public List<KeyValueDTO> getStateQuarterList() {
		return stateQuarterList;
	}

	public void setStateQuarterList(List<KeyValueDTO> stateQuarterList) {
		this.stateQuarterList = stateQuarterList;
	}

	public List<KeyValueDTO> getStateHalfList() {
		return stateHalfList;
	}

	public void setStateHalfList(List<KeyValueDTO> stateHalfList) {
		this.stateHalfList = stateHalfList;
	}

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public List<TutionFeeLimitMasterDTO> getTutionList() {
		return tutionList;
	}

	public void setTutionList(List<TutionFeeLimitMasterDTO> tutionList) {
		this.tutionList = tutionList;
	}
	public String getQuarterly() {
		return Quarterly;
	}

	public void setQuarterly(String quarterly) {
		Quarterly = quarterly;
	}

	public String getHalfyearly() {
		return halfyearly;
	}

	public void setHalfyearly(String halfyearly) {
		this.halfyearly = halfyearly;
	}

	public String getAnnually() {
		return annually;
	}

	public void setAnnually(String annually) {
		this.annually = annually;
	}

	public List<TutionFeeLimitMasterDTO> getAcademicTypeList() {
		return academicTypeList;
	}

	public void setAcademicTypeList(List<TutionFeeLimitMasterDTO> academicTypeList) {
		this.academicTypeList = academicTypeList;
	}

	public String getAcademicType() {
		return academicType;
	}

	public void setAcademicType(String academicType) {
		this.academicType = academicType;
	}
	
public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}
    public String getTutionFeeMaxLimitPerOneChild() {
		return tutionFeeMaxLimitPerOneChild;
	}

	public void setTutionFeeMaxLimitPerOneChild(String tutionFeeMaxLimitPerOneChild) {
		this.tutionFeeMaxLimitPerOneChild = tutionFeeMaxLimitPerOneChild;
	}

	public String getTutionFeeMaxChildToBeClaimed() {
		return tutionFeeMaxChildToBeClaimed;
	}

	public void setTutionFeeMaxChildToBeClaimed(String tutionFeeMaxChildToBeClaimed) {
		this.tutionFeeMaxChildToBeClaimed = tutionFeeMaxChildToBeClaimed;
	}

	public String getConfigurationDetails() {
		return configurationDetails;
	}

	public void setConfigurationDetails(String configurationDetails) {
		this.configurationDetails = configurationDetails;
	}

	public ConfigurationBean getConfBean() {
		return confBean;
	}

	public void setConfBean(ConfigurationBean confBean) {
		this.confBean = confBean;
	}

	public String getClaimType() {
		return claimType;
	}

	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<TuitionFeeAcedemicYearDTO> getClassNameList() {
		return classNameList;
	}

	public void setClassNameList(List<TuitionFeeAcedemicYearDTO> classNameList) {
		this.classNameList = classNameList;
	}
	
	public String getOthersClassName() {
		return othersClassName;
	}

	public void setOthersClassName(String othersClassName) {
		this.othersClassName = othersClassName;
	}

	public List<TutionFeeBean> getOfficersList() {
		return officersList;
	}

	public void setOfficersList(List<TutionFeeBean> officersList) {
		this.officersList = officersList;
	}

	public List<TutionFeeBean> getStaffList() {
		return staffList;
	}

	public void setStaffList(List<TutionFeeBean> staffList) {
		this.staffList = staffList;
	}

	public String getDesig() {
		return desig;
	}

	public void setDesig(String desig) {
		this.desig = desig;
	}

	public String getCdaAmount() {
		return cdaAmount;
	}

	public void setCdaAmount(String cdaAmount) {
		this.cdaAmount = cdaAmount;
	}

	public String getClaimName() {
		return claimName;
	}

	public void setClaimName(String claimName) {
		this.claimName = claimName;
	}

	public String getMaxChildToBeApplied() {
		return maxChildToBeApplied;
	}

	public void setMaxChildToBeApplied(String maxChildToBeApplied) {
		this.maxChildToBeApplied = maxChildToBeApplied;
	}

	public String getMaxAmountPerOneChild() {
		return maxAmountPerOneChild;
	}

	public void setMaxAmountPerOneChild(String maxAmountPerOneChild) {
		this.maxAmountPerOneChild = maxAmountPerOneChild;
	}

	public String getMainCDAList() {
		return mainCDAList;
	}

	public void setMainCDAList(String mainCDAList) {
		this.mainCDAList = mainCDAList;
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

	public String getClaimedAmount() {
		return claimedAmount;
	}

	public void setClaimedAmount(String claimedAmount) {
		this.claimedAmount = claimedAmount;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public List<TutionFeeBean> getTfClaimFinanceDetails() {
		return tfClaimFinanceDetails;
	}

	public void setTfClaimFinanceDetails(List<TutionFeeBean> tfClaimFinanceDetails) {
		this.tfClaimFinanceDetails = tfClaimFinanceDetails;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getSanctionedAmount() {
		return sanctionedAmount;
	}

	public void setSanctionedAmount(String sanctionedAmount) {
		this.sanctionedAmount = sanctionedAmount;
	}

	public Date getSanctionedDate() {
		return sanctionedDate;
	}

	public void setSanctionedDate(Date sanctionedDate) {
		this.sanctionedDate = sanctionedDate;
	}

	public int getChildRelationId() {
		return childRelationId;
	}

	public void setChildRelationId(int childRelationId) {
		this.childRelationId = childRelationId;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	public String getSfID() {
		return sfID;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
	}

	public String getRequestTypeID() {
		return requestTypeID;
	}

	public void setRequestTypeID(String requestTypeID) {
		this.requestTypeID = requestTypeID;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public String getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(String grandTotal) {
		this.grandTotal = grandTotal;
	}

	public String getMainClaimList() {
		return mainClaimList;
	}

	public void setMainClaimList(String mainClaimList) {
		this.mainClaimList = mainClaimList;
	}

	public String getChildClaimId() {
		return childClaimId;
	}

	public void setChildClaimId(String childClaimId) {
		this.childClaimId = childClaimId;
	}

	public List<TutionFeeClaimMasterDTO> getClaimList() {
		return claimList;
	}

	public void setClaimList(List<TutionFeeClaimMasterDTO> claimList) {
		this.claimList = claimList;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getAppliedFlag() {
		return appliedFlag;
	}

	public void setAppliedFlag(String appliedFlag) {
		this.appliedFlag = appliedFlag;
	}


	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<FamilyBean> getFamilyList() {
		return familyList;
	}

	public void setFamilyList(List<FamilyBean> familyList) {
		this.familyList = familyList;
	}

	public String getSfid() {
		return sfid;
	}

	public void setSfid(String sfid) {
		this.sfid = sfid;
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

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getClaimType1() {
		return claimType1;
	}

	public void setClaimType1(String claimType1) {
		this.claimType1 = claimType1;
	}

	public String getWorkFlowId() {
		return workFlowId;
	}

	public void setWorkFlowId(String workFlowId) {
		this.workFlowId = workFlowId;
	}

	/*public List<YearTypeDTO> getYearList() {
		return yearList;
	}

	public void setYearList(List<YearTypeDTO> yearList) {
		this.yearList = yearList;
	}*/

	public String getFromDate1() {
		return fromDate1;
	}

	public void setFromDate1(String fromDate1) {
		this.fromDate1 = fromDate1;
	}

	public String getToDate1() {
		return toDate1;
	}

	public void setToDate1(String toDate1) {
		this.toDate1 = toDate1;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getTypeQuartarly() {
		return typeQuartarly;
	}

	public void setTypeQuartarly(String typeQuartarly) {
		this.typeQuartarly = typeQuartarly;
	}

	public String getTypeHalfyearly() {
		return typeHalfyearly;
	}

	public void setTypeHalfyearly(String typeHalfyearly) {
		this.typeHalfyearly = typeHalfyearly;
	}

	public List<TutionFeeRequestDetailsDTO> getTuitionFeeApplicantDetails() {
		return tuitionFeeApplicantDetails;
	}

	public void setTuitionFeeApplicantDetails(
			List<TutionFeeRequestDetailsDTO> tuitionFeeApplicantDetails) {
		this.tuitionFeeApplicantDetails = tuitionFeeApplicantDetails;
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

	public String getHistoryID() {
		return historyID;
	}

	public void setHistoryID(String historyID) {
		this.historyID = historyID;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
   public List<TuitionFeeAcedemicYearDTO> getFirstChildList() {
		return firstChildList;
	}

	public void setFirstChildList(List<TuitionFeeAcedemicYearDTO> firstChildList) {
		this.firstChildList = firstChildList;
	}

	public List<TuitionFeeAcedemicYearDTO> getSecondChildList() {
		return secondChildList;
	}

	public void setSecondChildList(List<TuitionFeeAcedemicYearDTO> secondChildList) {
		this.secondChildList = secondChildList;
	}

	public int getClaimTypeId() {
		return claimTypeId;
	}

	public void setClaimTypeId(int claimTypeId) {
		this.claimTypeId = claimTypeId;
	}

	public String getClaimOrderNo() {
		return claimOrderNo;
	}

	public void setClaimOrderNo(String claimOrderNo) {
		this.claimOrderNo = claimOrderNo;
	}

	public String getAccOfficer() {
		return accOfficer;
	}

	public void setAccOfficer(String accOfficer) {
		this.accOfficer = accOfficer;
	}

	public int getFinanceBillNo() {
		return financeBillNo;
	}

	public void setFinanceBillNo(int financeBillNo) {
		this.financeBillNo = financeBillNo;
	}

	public int getEligiblity() {
		return eligiblity;
	}

	public void setEligiblity(int eligiblity) {
		this.eligiblity = eligiblity;
	}

	public int getAmtAdmissible() {
		return amtAdmissible;
	}

	public void setAmtAdmissible(int amtAdmissible) {
		this.amtAdmissible = amtAdmissible;
	}

	public List<PayFinYearDTO> getYearList() {
		return yearList;
	}

	public void setYearList(List<PayFinYearDTO> yearList) {
		this.yearList = yearList;
	}

	public Map<Integer, List<TuitionFeeAcedemicYearDTO>> getMapChildList() {
		return mapChildList;
	}

	public void setMapChildList(
			Map<Integer, List<TuitionFeeAcedemicYearDTO>> mapChildList) {
		this.mapChildList = mapChildList;
	}

	public Map<Integer, List<PayFinYearDTO>> getMapYearList() {
		return mapYearList;
	}

	public void setMapYearList(Map<Integer, List<PayFinYearDTO>> mapYearList) {
		this.mapYearList = mapYearList;
	}

	public List<SingingAuthorityDTO> getCfaOfficerList() {
		return cfaOfficerList;
	}

	public void setCfaOfficerList(List<SingingAuthorityDTO> cfaOfficerList) {
		this.cfaOfficerList = cfaOfficerList;
	}

	public List<SingingAuthorityDTO> getAccountOfficerList() {
		return accountOfficerList;
	}

	public void setAccountOfficerList(List<SingingAuthorityDTO> accountOfficerList) {
		this.accountOfficerList = accountOfficerList;
	}

	public String getCfaOfficer() {
		return cfaOfficer;
	}

	public void setCfaOfficer(String cfaOfficer) {
		this.cfaOfficer = cfaOfficer;
	}

	public String getDisplayCalculateTotal() {
		return displayCalculateTotal;
	}

	public void setDisplayCalculateTotal(String displayCalculateTotal) {
		this.displayCalculateTotal = displayCalculateTotal;
	}
		

}
