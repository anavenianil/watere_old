package com.callippus.web.leave.beans.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.callippus.web.beans.dto.CasualityDetailsDTO;
import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.DoPartDTO;
import com.callippus.web.beans.dto.EmploymentDTO;
import com.callippus.web.beans.dto.EssModuleDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.OrgInstanceDTO;
import com.callippus.web.beans.dto.TaskHolderDesignationsDTO;
import com.callippus.web.beans.dto.TaskHolderDetailsDTO;
import com.callippus.web.beans.dto.TypeDesigDetailsDTO;
import com.callippus.web.beans.dto.TypeDetailsDTO;
import com.callippus.web.beans.dto.YearTypeDTO;
import com.callippus.web.beans.requests.LeaveRequestBean;
import com.callippus.web.leave.dto.ErpAvailableLeaveTypesDTO;
import com.callippus.web.leave.dto.ErpAvailableLeavesDTO;
import com.callippus.web.leave.dto.ErpUsedLeavesDTO;
import com.callippus.web.leave.dto.LeaveBusinessRulesVO;
import com.callippus.web.leave.dto.LeaveManualEntryDTO;
import com.callippus.web.leave.dto.LeaveTypeDTO;

public class LeaveAdministratorBean {
	private String param;
	private String type;
	private String result;
	private String message;
	private String sfID;
	private String entrySfid;
	private String earnedLeaveId;
	private String halfPayLeaveId;
	private String casualLeaveId;
	private String leaveNotDue;
	private String childCareLeave;
	private String studyLeave;
	private String verifyFlag;
	private List<LeaveManualEntryDTO> entryLeaves;
	private List<LeaveManualEntryDTO> leaveBalanceList;
	private String fromDate;
	private String toDate;
	private List<LeaveRequestBean> appliedLeavesList;
	private List<LeaveRequestBean> cancelledLavesList;
	private List<LeaveRequestBean> convertedLavesList;
	private List<DoPartDTO> doPartList;
	private String ipAddress;
	private String doPartDate;
	private String doPartNo;
	private String requestIDs;
	private String doPartID;
	private String requestID;
	private String searchSfid;
	private String slServiceSpells;
	private String cclYearSpells;
	private String cclServiceSpells;
	private String slYearSpells;
	private String ltcEncashmentDays;
	private HashMap<String, String> spellsDetails;
	private String eolLeaveId;
	private String eolWOMCLeaveId;
	private String cmlwomc;
	private String leaveNotDuewomc;
	private String eolWOMCInSerive;
	private List<DesignationDTO> designationList;
	private List<DesignationDTO> allDesignationList;
	private String designationID;
	private String scriptDate;
	private String leaveType;
	private String txnDate;
	private List<LeaveTypeDTO> leaveTypeList;
	private List<LeaveAdministratorBean> leaveTxnList;
	private String designation;
	private String empName;
	private int noOfLeaves;
	private List<EmploymentDTO> leaveExpList;
	private String employeeID;
	private String selectedID;
	private String selectedLinks;
	private List<EmploymentDTO> employeesList;
	private String noOfDays;
	private String txnType;
	private String txnTypeVal;
	private String txnFromDate;
	private String txnToDate;
	private String gazettedType;
	private String searchedDoPartNo;
	private String cancelIDs;
	private String convertIDs;
	private float balanceLeaves;
	private List<YearTypeDTO> yearsList;
	private String yearID;
	private String year;
	private String name;
	private String description;
	private String status;
	private String txnList;

	// Leave Business Rules
	private List<KeyValueDTO> requestTypes;
	private String requestType;
	private String one;
	private String two;
	private String three;
	private String four;
	private String five;
	private String condition;
	private String resultValue;
	private String duration;
	private List<LeaveBusinessRulesVO> businessRules;
	private String businessRulesList;
	private int pk;
	

	
	
	//added by bkr
	private int annualLeaves;
	private int sickLeaves;
	private int maternityLeaves;
	private int peternityLeaves;
	
	private int compassionateLeaves; 
	
	
	private List<ErpAvailableLeavesDTO> entryErpLeaves;
	
	  private ErpAvailableLeavesDTO erpAvailableLeavesDTO;
	
	  private ErpUsedLeavesDTO erpUsedLeavesDTO;

	  
	  
	public ErpUsedLeavesDTO getErpUsedLeavesDTO() {
		return erpUsedLeavesDTO;
	}

	public void setErpUsedLeavesDTO(ErpUsedLeavesDTO erpUsedLeavesDTO) {
		this.erpUsedLeavesDTO = erpUsedLeavesDTO;
	}

	public ErpAvailableLeavesDTO getErpAvailableLeavesDTO() {
		return erpAvailableLeavesDTO;
	}

	public void setErpAvailableLeavesDTO(ErpAvailableLeavesDTO erpAvailableLeavesDTO) {
		this.erpAvailableLeavesDTO = erpAvailableLeavesDTO;
	}

	public int getAnnualLeaves() {
		return annualLeaves;
	}

	public void setAnnualLeaves(int annualLeaves) {
		this.annualLeaves = annualLeaves;
	}

	public int getSickLeaves() {
		return sickLeaves;
	}

	public void setSickLeaves(int sickLeaves) {
		this.sickLeaves = sickLeaves;
	}

	public int getMaternityLeaves() {
		return maternityLeaves;
	}

	public void setMaternityLeaves(int maternityLeaves) {
		this.maternityLeaves = maternityLeaves;
	}

	public int getPeternityLeaves() {
		return peternityLeaves;
	}

	public void setPeternityLeaves(int peternityLeaves) {
		this.peternityLeaves = peternityLeaves;
	}

	public List<ErpAvailableLeavesDTO> getEntryErpLeaves() {
		return entryErpLeaves;
	}

	public void setEntryErpLeaves(List<ErpAvailableLeavesDTO> entryErpLeaves) {
		this.entryErpLeaves = entryErpLeaves;
	}

	public int getPk() {
		return pk;
	}

	public void setPk(int pk) {
		this.pk = pk;
	}

	public String getBusinessRulesList() {
		return businessRulesList;
	}

	public void setBusinessRulesList(String businessRulesList) {
		this.businessRulesList = businessRulesList;
	}

	public List<KeyValueDTO> getRequestTypes() {
		return requestTypes;
	}

	public void setRequestTypes(List<KeyValueDTO> requestTypes) {
		this.requestTypes = requestTypes;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getOne() {
		return one;
	}

	public void setOne(String one) {
		this.one = one;
	}

	public String getTwo() {
		return two;
	}

	public void setTwo(String two) {
		this.two = two;
	}

	public String getThree() {
		return three;
	}

	public void setThree(String three) {
		this.three = three;
	}

	public String getFour() {
		return four;
	}

	public void setFour(String four) {
		this.four = four;
	}

	public String getFive() {
		return five;
	}

	public void setFive(String five) {
		this.five = five;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getResultValue() {
		return resultValue;
	}

	public void setResultValue(String resultValue) {
		this.resultValue = resultValue;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public List<LeaveBusinessRulesVO> getBusinessRules() {
		return businessRules;
	}

	public void setBusinessRules(List<LeaveBusinessRulesVO> businessRules) {
		this.businessRules = businessRules;
	}

	public String getTxnList() {
		return txnList;
	}

	public void setTxnList(String txnList) {
		this.txnList = txnList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private String prefixName;
	private List<TypeDetailsDTO> typeList;
	private String nodeID;
	private List<EssModuleDTO> essModuleList;
	private List<CasualityDetailsDTO> casualitiesList;
	private String moduleId;
	private String typeId;
	private String taskHolderId;
	private List<OrgInstanceDTO> roleList;
	private String roleId;
	private List<TaskHolderDetailsDTO> tHolderList;
	private String delimeter;
	private String order;
	private List<TypeDesigDetailsDTO> typeDesigList;
	private int typeDesigID;
	private String nodeIds;
	private String taskHolderMap;
	private List<TaskHolderDesignationsDTO> taskHolderDesigList;
	private String releasedBy;
	private Date releasedDate;
	private String acceptedBy;
	private Date acceptedDate;
	private String serialNumber;
	private String casualityId;
	private String gazType;
	private List<DesignationDTO> deselectedDesigList;
	private String doMonth;
	private int code;

	public List<DesignationDTO> getAllDesignationList() {
		return allDesignationList;
	}

	public void setAllDesignationList(List<DesignationDTO> allDesignationList) {
		this.allDesignationList = allDesignationList;
	}

	public String getCasualityId() {
		return casualityId;
	}

	public void setCasualityId(String casualityId) {
		this.casualityId = casualityId;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDoMonth() {
		return doMonth;
	}

	public void setDoMonth(String doMonth) {
		this.doMonth = doMonth;
	}

	public List<DesignationDTO> getDeselectedDesigList() {
		return deselectedDesigList;
	}

	public void setDeselectedDesigList(List<DesignationDTO> deselectedDesigList) {
		this.deselectedDesigList = deselectedDesigList;
	}

	public String getGazType() {
		return gazType;
	}

	public void setGazType(String gazType) {
		this.gazType = gazType;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getReleasedBy() {
		return releasedBy;
	}

	public void setReleasedBy(String releasedBy) {
		this.releasedBy = releasedBy;
	}

	public Date getReleasedDate() {
		return releasedDate;
	}

	public void setReleasedDate(Date releasedDate) {
		this.releasedDate = releasedDate;
	}

	public String getAcceptedBy() {
		return acceptedBy;
	}

	public void setAcceptedBy(String acceptedBy) {
		this.acceptedBy = acceptedBy;
	}

	public Date getAcceptedDate() {
		return acceptedDate;
	}

	public void setAcceptedDate(Date acceptedDate) {
		this.acceptedDate = acceptedDate;
	}

	public List<TaskHolderDesignationsDTO> getTaskHolderDesigList() {
		return taskHolderDesigList;
	}

	public void setTaskHolderDesigList(
			List<TaskHolderDesignationsDTO> taskHolderDesigList) {
		this.taskHolderDesigList = taskHolderDesigList;
	}

	public String getTaskHolderMap() {
		return taskHolderMap;
	}

	public void setTaskHolderMap(String taskHolderMap) {
		this.taskHolderMap = taskHolderMap;
	}

	public String getNodeIds() {
		return nodeIds;
	}

	public void setNodeIds(String nodeIds) {
		this.nodeIds = nodeIds;
	}

	public int getTypeDesigID() {
		return typeDesigID;
	}

	public void setTypeDesigID(int typeDesigID) {
		this.typeDesigID = typeDesigID;
	}

	public List<TypeDesigDetailsDTO> getTypeDesigList() {
		return typeDesigList;
	}

	public void setTypeDesigList(List<TypeDesigDetailsDTO> typeDesigList) {
		this.typeDesigList = typeDesigList;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getDelimeter() {
		return delimeter;
	}

	public void setDelimeter(String delimeter) {
		this.delimeter = delimeter;
	}

	public List<TaskHolderDetailsDTO> gettHolderList() {
		return tHolderList;
	}

	public void settHolderList(List<TaskHolderDetailsDTO> tHolderList) {
		this.tHolderList = tHolderList;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public List<OrgInstanceDTO> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<OrgInstanceDTO> roleList) {
		this.roleList = roleList;
	}

	public String getTaskHolderId() {
		return taskHolderId;
	}

	public void setTaskHolderId(String taskHolderId) {
		this.taskHolderId = taskHolderId;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public List<CasualityDetailsDTO> getCasualitiesList() {
		return casualitiesList;
	}

	public void setCasualitiesList(List<CasualityDetailsDTO> casualitiesList) {
		this.casualitiesList = casualitiesList;
	}

	public List<EssModuleDTO> getEssModuleList() {
		return essModuleList;
	}

	public void setEssModuleList(List<EssModuleDTO> essModuleList) {
		this.essModuleList = essModuleList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNodeID() {
		return nodeID;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}

	public List<TypeDetailsDTO> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<TypeDetailsDTO> typeList) {
		this.typeList = typeList;
	}

	public String getPrefixName() {
		return prefixName;
	}

	public void setPrefixName(String prefixName) {
		this.prefixName = prefixName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<YearTypeDTO> getYearsList() {
		return yearsList;
	}

	public void setYearsList(List<YearTypeDTO> yearsList) {
		this.yearsList = yearsList;
	}

	public String getYearID() {
		return yearID;
	}

	public void setYearID(String yearID) {
		this.yearID = yearID;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public float getBalanceLeaves() {
		return balanceLeaves;
	}

	public void setBalanceLeaves(float balanceLeaves) {
		this.balanceLeaves = balanceLeaves;
	}

	public List<LeaveRequestBean> getCancelledLavesList() {
		return cancelledLavesList;
	}

	public void setCancelledLavesList(List<LeaveRequestBean> cancelledLavesList) {
		this.cancelledLavesList = cancelledLavesList;
	}

	public List<LeaveRequestBean> getConvertedLavesList() {
		return convertedLavesList;
	}

	public void setConvertedLavesList(List<LeaveRequestBean> convertedLavesList) {
		this.convertedLavesList = convertedLavesList;
	}

	public String getCancelIDs() {
		return cancelIDs;
	}

	public void setCancelIDs(String cancelIDs) {
		this.cancelIDs = cancelIDs;
	}

	public String getConvertIDs() {
		return convertIDs;
	}

	public void setConvertIDs(String convertIDs) {
		this.convertIDs = convertIDs;
	}

	public String getSearchedDoPartNo() {
		return searchedDoPartNo;
	}

	public void setSearchedDoPartNo(String searchedDoPartNo) {
		this.searchedDoPartNo = searchedDoPartNo;
	}

	public String getGazettedType() {
		return gazettedType;
	}

	public void setGazettedType(String gazettedType) {
		this.gazettedType = gazettedType;
	}

	public String getTxnFromDate() {
		return txnFromDate;
	}

	public void setTxnFromDate(String txnFromDate) {
		this.txnFromDate = txnFromDate;
	}

	public String getTxnToDate() {
		return txnToDate;
	}

	public void setTxnToDate(String txnToDate) {
		this.txnToDate = txnToDate;
	}

	public String getTxnTypeVal() {
		return txnTypeVal;
	}

	public void setTxnTypeVal(String txnTypeVal) {
		this.txnTypeVal = txnTypeVal;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
	}

	private String remarks;
	//public List setErpEmpAppliedLeavesList;
	
	public List<ErpAvailableLeavesDTO> setErpEmpAppliedLeavesList;
	public List<ErpAvailableLeaveTypesDTO> setErpAppliedLeavesList;
	
	
	
//	 private ErpAvailableLeavesDTO erpAvailableLeavesDTO;

	public List<ErpAvailableLeaveTypesDTO> getSetErpAppliedLeavesList() {
		return setErpAppliedLeavesList;
	}

	public void setSetErpAppliedLeavesList(
			List<ErpAvailableLeaveTypesDTO> setErpAppliedLeavesList) {
		this.setErpAppliedLeavesList = setErpAppliedLeavesList;
	}

	public List<ErpAvailableLeavesDTO> getSetErpEmpAppliedLeavesList() {
		return setErpEmpAppliedLeavesList;
	}

	public void setSetErpEmpAppliedLeavesList(
			List<ErpAvailableLeavesDTO> setErpEmpAppliedLeavesList) {
		this.setErpEmpAppliedLeavesList = setErpEmpAppliedLeavesList;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public String getSelectedID() {
		return selectedID;
	}

	public void setSelectedID(String selectedID) {
		this.selectedID = selectedID;
	}

	public List<EmploymentDTO> getEmployeesList() {
		return employeesList;
	}

	public void setEmployeesList(List<EmploymentDTO> employeesList) {
		this.employeesList = employeesList;
	}

	public String getSelectedLinks() {
		return selectedLinks;
	}

	public void setSelectedLinks(String selectedLinks) {
		this.selectedLinks = selectedLinks;
	}

	public List<EmploymentDTO> getLeaveExpList() {
		return leaveExpList;
	}

	public void setLeaveExpList(List<EmploymentDTO> leaveExpList) {
		this.leaveExpList = leaveExpList;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public int getNoOfLeaves() {
		return noOfLeaves;
	}

	public void setNoOfLeaves(int noOfLeaves) {
		this.noOfLeaves = noOfLeaves;
	}

	public List<LeaveAdministratorBean> getLeaveTxnList() {
		return leaveTxnList;
	}

	public void setLeaveTxnList(List<LeaveAdministratorBean> leaveTxnList) {
		this.leaveTxnList = leaveTxnList;
	}

	public List<LeaveTypeDTO> getLeaveTypeList() {
		return leaveTypeList;
	}

	public void setLeaveTypeList(List<LeaveTypeDTO> leaveTypeList) {
		this.leaveTypeList = leaveTypeList;
	}

	public String getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getScriptDate() {
		return scriptDate;
	}

	public void setScriptDate(String scriptDate) {
		this.scriptDate = scriptDate;
	}

	public String getDesignationID() {
		return designationID;
	}

	public void setDesignationID(String designationID) {
		this.designationID = designationID;
	}

	public List<DesignationDTO> getDesignationList() {
		return designationList;
	}

	public void setDesignationList(List<DesignationDTO> designationList) {
		this.designationList = designationList;
	}

	public String getEolWOMCInSerive() {
		return eolWOMCInSerive;
	}

	public void setEolWOMCInSerive(String eolWOMCInSerive) {
		this.eolWOMCInSerive = eolWOMCInSerive;
	}

	public String getCmlwomc() {
		return cmlwomc;
	}

	public void setCmlwomc(String cmlwomc) {
		this.cmlwomc = cmlwomc;
	}

	public String getLeaveNotDuewomc() {
		return leaveNotDuewomc;
	}

	public void setLeaveNotDuewomc(String leaveNotDuewomc) {
		this.leaveNotDuewomc = leaveNotDuewomc;
	}

	public String getEolWOMCLeaveId() {
		return eolWOMCLeaveId;
	}

	public void setEolWOMCLeaveId(String eolWOMCLeaveId) {
		this.eolWOMCLeaveId = eolWOMCLeaveId;
	}

	public String getEolLeaveId() {
		return eolLeaveId;
	}

	public void setEolLeaveId(String eolLeaveId) {
		this.eolLeaveId = eolLeaveId;
	}

	public HashMap<String, String> getSpellsDetails() {
		return spellsDetails;
	}

	public void setSpellsDetails(HashMap<String, String> spellsDetails) {
		this.spellsDetails = spellsDetails;
	}

	public String getLtcEncashmentDays() {
		return ltcEncashmentDays;
	}

	public void setLtcEncashmentDays(String ltcEncashmentDays) {
		this.ltcEncashmentDays = ltcEncashmentDays;
	}

	public String getSlServiceSpells() {
		return slServiceSpells;
	}

	public void setSlServiceSpells(String slServiceSpells) {
		this.slServiceSpells = slServiceSpells;
	}

	public String getCclYearSpells() {
		return cclYearSpells;
	}

	public void setCclYearSpells(String cclYearSpells) {
		this.cclYearSpells = cclYearSpells;
	}

	public String getCclServiceSpells() {
		return cclServiceSpells;
	}

	public void setCclServiceSpells(String cclServiceSpells) {
		this.cclServiceSpells = cclServiceSpells;
	}

	public String getSlYearSpells() {
		return slYearSpells;
	}

	public void setSlYearSpells(String slYearSpells) {
		this.slYearSpells = slYearSpells;
	}

	public List<DoPartDTO> getDoPartList() {
		return doPartList;
	}

	public void setDoPartList(List<DoPartDTO> doPartList) {
		this.doPartList = doPartList;
	}

	public String getSearchSfid() {
		return searchSfid;
	}

	public void setSearchSfid(String searchSfid) {
		this.searchSfid = searchSfid;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public String getDoPartID() {
		return doPartID;
	}

	public void setDoPartID(String doPartID) {
		this.doPartID = doPartID;
	}

	public String getRequestIDs() {
		return requestIDs;
	}

	public void setRequestIDs(String requestIDs) {
		this.requestIDs = requestIDs;
	}

	public String getDoPartNo() {
		return doPartNo;
	}

	public void setDoPartNo(String doPartNo) {
		this.doPartNo = doPartNo;
	}

	public String getDoPartDate() {
		return doPartDate;
	}

	public void setDoPartDate(String doPartDate) {
		this.doPartDate = doPartDate;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public List<LeaveRequestBean> getAppliedLeavesList() {
		return appliedLeavesList;
	}

	public void setAppliedLeavesList(List<LeaveRequestBean> appliedLeavesList) {
		this.appliedLeavesList = appliedLeavesList;
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

	public List<LeaveManualEntryDTO> getLeaveBalanceList() {
		return leaveBalanceList;
	}

	public void setLeaveBalanceList(List<LeaveManualEntryDTO> leaveBalanceList) {
		this.leaveBalanceList = leaveBalanceList;
	}

	public String getVerifyFlag() {
		return verifyFlag;
	}

	public void setVerifyFlag(String verifyFlag) {
		this.verifyFlag = verifyFlag;
	}

	public String getSfID() {
		return sfID;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
	}

	public String getEntrySfid() {
		return entrySfid;
	}

	public void setEntrySfid(String entrySfid) {
		this.entrySfid = entrySfid;
	}

	public String getEarnedLeaveId() {
		return earnedLeaveId;
	}

	public void setEarnedLeaveId(String earnedLeaveId) {
		this.earnedLeaveId = earnedLeaveId;
	}

	public String getHalfPayLeaveId() {
		return halfPayLeaveId;
	}

	public void setHalfPayLeaveId(String halfPayLeaveId) {
		this.halfPayLeaveId = halfPayLeaveId;
	}

	public String getCasualLeaveId() {
		return casualLeaveId;
	}

	public void setCasualLeaveId(String casualLeaveId) {
		this.casualLeaveId = casualLeaveId;
	}

	public String getLeaveNotDue() {
		return leaveNotDue;
	}

	public void setLeaveNotDue(String leaveNotDue) {
		this.leaveNotDue = leaveNotDue;
	}

	public String getChildCareLeave() {
		return childCareLeave;
	}

	public void setChildCareLeave(String childCareLeave) {
		this.childCareLeave = childCareLeave;
	}

	public String getStudyLeave() {
		return studyLeave;
	}

	public void setStudyLeave(String studyLeave) {
		this.studyLeave = studyLeave;
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

	public List<LeaveManualEntryDTO> getEntryLeaves() {
		return entryLeaves;
	}

	public void setEntryLeaves(List<LeaveManualEntryDTO> entryLeaves) {
		this.entryLeaves = entryLeaves;
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

	public int getCompassionateLeaves() {
		return compassionateLeaves;
	}

	public void setCompassionateLeaves(int compassionateLeaves) {
		this.compassionateLeaves = compassionateLeaves;
	}



}
