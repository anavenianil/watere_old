package com.callippus.web.promotions.beans.management;

import java.util.Date;
import java.util.List;

import com.callippus.web.beans.dto.CasualityDetailsDTO;
import com.callippus.web.beans.dto.CategoryDTO;
import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.DoPartDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.ReservationDTO;
import com.callippus.web.beans.dto.TypeDetailsDTO;
import com.callippus.web.beans.dto.YearTypeDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.requests.DoPartBean;
import com.callippus.web.paybill.dto.PayScaleDesignationDTO;
import com.callippus.web.promotions.dto.AssessmentCategoryDTO;
import com.callippus.web.promotions.dto.AssessmentDetailsDTO;
import com.callippus.web.promotions.dto.BoardMappingDTO;
import com.callippus.web.promotions.dto.BoardMasterDTO;
import com.callippus.web.promotions.dto.ExceptionalEmpDTO;
import com.callippus.web.promotions.dto.ExternalEmpDTO;
import com.callippus.web.promotions.dto.PromoteesEntryDTO;
import com.callippus.web.promotions.dto.PromotionsDisciplineDTO;
import com.callippus.web.promotions.dto.PromotionsSubDisciplineDTO;
import com.callippus.web.promotions.dto.ResidencyPeriodDTO;
import com.callippus.web.promotions.dto.VenueDetailsDTO;

public class PromotionManagementBean {
	private String sfID;
	private String param;
	private String type;
	private String result;
	private String nodeID;

	private List<AssessmentCategoryDTO> assessmentCategoryList;
	private List<CategoryDTO> assessmentTypeList;
	private List<DesignationDTO> designationList;
	private List<ResidencyPeriodDTO> residencyPeriodList;

	
	private String boardType;
	private int assessmentTypeID;
	private int assessmentCategoryID;
	private int designationFrom;
	private int designationTo;
	private int residencyPeriod;
	private int relaxationInMonths;
	private String dateFrom;
	private String dateTo;

	private Date startDate;
	private int desigAttempts;
	private int designation;
	private String refSfid;
	private int desigID;

	private List<VenueDetailsDTO> venueList;
	private int yearID;
	private String center;
	private String coOrdinator;
	private String coOrdinatorLab;
	private String address;
	private String contactAddress;
	private String venue;
	private List<YearTypeDTO> yearList;
	private List<ExceptionalEmpDTO> exceptionalEmp;
	private String expEmp;
	private List<KeyValueDTO> localBoardMembersList;
	private String boardName;
	private String boardMembers;
	private int boardID;
	private String description;
	private List<BoardMappingDTO> localBoardMappingList;
	private int editYearID;
	private List<AssessmentDetailsDTO> assessmentDetails;
	private List<AssessmentDetailsDTO> assessmentDetails1;
	private List<PromotionsSubDisciplineDTO> disciplineDetails;
	private List<BoardMasterDTO> boardMasterList;
	private int venueID;
	private String rowValues;
	private Date interviewDate;
	private String labRepresentative;
	private String message;
	private List<ReservationDTO> reservationList;
	private String doPartNumber;
	private String doPartDate;
	private int doPartID;
	private String reportType;
	private int status;
	private Date promotionDate;
	private Date effectiveDate;
	private Integer variableIncr;
	private int reservationID;
	private int disciplineID;
	private String gazettedType;
	private EmployeeBean empDetails;
	private Date assessmentDate;
	private int subDisciplineID;
	private String name;
	private String shortForm;
	private String subName;
	private String varIncEnd;

	private List<PromotionsDisciplineDTO> disciplineList;
	private List<PromotionsSubDisciplineDTO> subDisciplineList;
	private List<ExternalEmpDTO> externalEmpList;
	private String desigName;

	private String basicPay;
	private String gradePay;
	private Date presentEffectivedate;
	private List<PromoteesEntryDTO> offlineEntryList;
	private String newBasicPay;
	private String newGradePay;
	private List<PromotionOfflineEntryBean> catwiseDesig;
	private Date endingDate;
	private String doPartNo;
	private List<DoPartDTO> doPartList;
	private List<CasualityDetailsDTO> casualitiesList;
	private List<TypeDetailsDTO> typeList;
	private int moduleId;
	private String casualityId;
	private String serialNumber;
	private List<AssessmentDetailsDTO> varAssessmentDetails;
	private String seniorityDate;
	private String twoAddl;
	private Date financeAcceptedDate;
	private Date payBillRunMonth;
	
	private List<PayScaleDesignationDTO> empPayScaleList;
	private String varIncVal;
	//
	private Integer Written;
	private Integer trade;
	private Integer interview;
	private Integer board;
	private Integer residencyPeroidId;
	private String WrittenDate;
	private List<Integer> desigList;
	private String jsonValue;
    private List gazttedTypeList;    //New Property has been added for pay fixation screen

	private List<AssessmentDetailsDTO> ViewOptionalCertificateList;
	
	

	
	
	


	
	
  public List getGazttedTypeList() {
		return gazttedTypeList;
	}

	public void setGazttedTypeList(List gazttedTypeList) {
		this.gazttedTypeList = gazttedTypeList;
	}

	public List<AssessmentDetailsDTO> getAssessmentDetails1() {
		return assessmentDetails1;
	}

	public void setAssessmentDetails1(List<AssessmentDetailsDTO> assessmentDetails1) {
		this.assessmentDetails1 = assessmentDetails1;
	}

	public List<AssessmentDetailsDTO> getViewOptionalCertificateList() {
		return ViewOptionalCertificateList;
	}

	public void setViewOptionalCertificateList(List<AssessmentDetailsDTO> viewOptionalCertificateList) {
		ViewOptionalCertificateList = viewOptionalCertificateList;
	}
	public String getJsonValue() {
		return jsonValue;
	}

	public void setJsonValue(String jsonValue) {
		this.jsonValue = jsonValue;
	}

	public List<Integer> getDesigList() {
		return desigList;
	}

	public void setDesigList(List<Integer> desigList) {
		this.desigList = desigList;
	}

	
	
	public String getWrittenDate() {
		return WrittenDate;
	}

	
	public void setWrittenDate(String writtenDate) {
		WrittenDate = writtenDate;
	}

	public String getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}

	private String tradeDate;
	

	
    public Integer getResidencyPeroidId() {
		return residencyPeroidId;
	}

	public void setResidencyPeroidId(Integer residencyPeroidId) {
		this.residencyPeroidId = residencyPeroidId;
	}

	public Integer getBoard() {
		return board;
	}

	public void setBoard(Integer board) {
		this.board = board;
	}

	public Integer getWritten() {
		return Written;
	}

	public void setWritten(Integer written) {
		Written = written;
	}

	public Integer getTrade() {
		return trade;
	}

	public void setTrade(Integer trade) {
		this.trade = trade;
	}

	public Integer getInterview() {
		return interview;
	}

	public void setInterview(Integer interview) {
		this.interview = interview;
	}
	

	//
	public String getVarIncVal() {
		return varIncVal;
	}

	public void setVarIncVal(String varIncVal) {
		this.varIncVal = varIncVal;
	}

	public List<PayScaleDesignationDTO> getEmpPayScaleList() {
		return empPayScaleList;
	}

	public void setEmpPayScaleList(List<PayScaleDesignationDTO> empPayScaleList) {
		this.empPayScaleList = empPayScaleList;
	}

	public String getVarIncEnd() {
		return varIncEnd;
	}

	public void setVarIncEnd(String varIncEnd) {
		this.varIncEnd = varIncEnd;
	}

	public String getTwoAddl() {
		return twoAddl;
	}

	public void setTwoAddl(String twoAddl) {
		this.twoAddl = twoAddl;
	}

	public String getSeniorityDate() {
		return seniorityDate;
	}

	public void setSeniorityDate(String seniorityDate) {
		this.seniorityDate = seniorityDate;
	}

	public List<AssessmentDetailsDTO> getVarAssessmentDetails() {
		return varAssessmentDetails;
	}

	public void setVarAssessmentDetails(List<AssessmentDetailsDTO> varAssessmentDetails) {
		this.varAssessmentDetails = varAssessmentDetails;
	}

	public String getCasualityId() {
		return casualityId;
	}

	public void setCasualityId(String casualityId) {
		this.casualityId = casualityId;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public List<TypeDetailsDTO> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<TypeDetailsDTO> typeList) {
		this.typeList = typeList;
	}

	public List<DoPartDTO> getDoPartList() {
		return doPartList;
	}

	public void setDoPartList(List<DoPartDTO> doPartList) {
		this.doPartList = doPartList;
	}

	public Date getEndingDate() {
		return endingDate;
	}

	public void setEndingDate(Date endingDate) {
		this.endingDate = endingDate;
	}

	public List<PromotionOfflineEntryBean> getCatwiseDesig() {
		return catwiseDesig;
	}

	public void setCatwiseDesig(List<PromotionOfflineEntryBean> catwiseDesig) {
		this.catwiseDesig = catwiseDesig;
	}

	public String getNewBasicPay() {
		return newBasicPay;
	}

	public void setNewBasicPay(String newBasicPay) {
		this.newBasicPay = newBasicPay;
	}

	public String getNewGradePay() {
		return newGradePay;
	}

	public void setNewGradePay(String newGradePay) {
		this.newGradePay = newGradePay;
	}

	public Date getPresentEffectivedate() {
		return presentEffectivedate;
	}

	public void setPresentEffectivedate(Date presentEffectivedate) {
		this.presentEffectivedate = presentEffectivedate;
	}

	public List<PromoteesEntryDTO> getOfflineEntryList() {
		return offlineEntryList;
	}

	public void setOfflineEntryList(List<PromoteesEntryDTO> offlineEntryList) {
		this.offlineEntryList = offlineEntryList;
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

	public String getDesigName() {
		return desigName;
	}

	public void setDesigName(String desigName) {
		this.desigName = desigName;
	}

	public List<ExternalEmpDTO> getExternalEmpList() {
		return externalEmpList;
	}

	public void setExternalEmpList(List<ExternalEmpDTO> externalEmpList) {
		this.externalEmpList = externalEmpList;
	}

	public int getSubDisciplineID() {
		return subDisciplineID;
	}

	public void setSubDisciplineID(int subDisciplineID) {
		this.subDisciplineID = subDisciplineID;
	}

	public List<PromotionsSubDisciplineDTO> getSubDisciplineList() {
		return subDisciplineList;
	}

	public void setSubDisciplineList(List<PromotionsSubDisciplineDTO> subDisciplineList) {
		this.subDisciplineList = subDisciplineList;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortForm() {
		return shortForm;
	}

	public void setShortForm(String shortForm) {
		this.shortForm = shortForm;
	}

	public Date getAssessmentDate() {
		return assessmentDate;
	}

	public void setAssessmentDate(Date assessmentDate) {
		this.assessmentDate = assessmentDate;
	}

	public Date getPromotionDate() {
		return promotionDate;
	}

	public void setPromotionDate(Date promotionDate) {
		this.promotionDate = promotionDate;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	
	public Integer getVariableIncr() {
		return variableIncr;
	}

	public void setVariableIncr(Integer variableIncr) {
		this.variableIncr = variableIncr;
	}

	public int getReservationID() {
		return reservationID;
	}

	public void setReservationID(int reservationID) {
		this.reservationID = reservationID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	private String ipAddress;
	private List<DoPartBean> payFixationDOPartDetails;

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public int getDoPartID() {
		return doPartID;
	}

	public void setDoPartID(int doPartID) {
		this.doPartID = doPartID;
	}

	public String getDoPartNumber() {
		return doPartNumber;
	}

	public void setDoPartNumber(String doPartNumber) {
		this.doPartNumber = doPartNumber;
	}

	public String getDoPartDate() {
		return doPartDate;
	}

	public void setDoPartDate(String doPartDate) {
		this.doPartDate = doPartDate;
	}

	public List<ReservationDTO> getReservationList() {
		return reservationList;
	}

	public void setReservationList(List<ReservationDTO> reservationList) {
		this.reservationList = reservationList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLabRepresentative() {
		return labRepresentative;
	}

	public void setLabRepresentative(String labRepresentative) {
		this.labRepresentative = labRepresentative;
	}

	public Date getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(Date interviewDate) {
		this.interviewDate = interviewDate;
	}

	public String getRowValues() {
		return rowValues;
	}

	public void setRowValues(String rowValues) {
		this.rowValues = rowValues;
	}

	public int getVenueID() {
		return venueID;
	}

	public void setVenueID(int venueID) {
		this.venueID = venueID;
	}

	public List<BoardMasterDTO> getBoardMasterList() {
		return boardMasterList;
	}

	public void setBoardMasterList(List<BoardMasterDTO> boardMasterList) {
		this.boardMasterList = boardMasterList;
	}

	public List<AssessmentDetailsDTO> getAssessmentDetails() {
		return assessmentDetails;
	}

	public void setAssessmentDetails(List<AssessmentDetailsDTO> assessmentDetails) {
		this.assessmentDetails = assessmentDetails;
	}

	public int getEditYearID() {
		return editYearID;
	}

	public void setEditYearID(int editYearID) {
		this.editYearID = editYearID;
	}

	public List<BoardMappingDTO> getLocalBoardMappingList() {
		return localBoardMappingList;
	}

	public void setLocalBoardMappingList(List<BoardMappingDTO> localBoardMappingList) {
		this.localBoardMappingList = localBoardMappingList;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getBoardID() {
		return boardID;
	}

	public void setBoardID(int boardID) {
		this.boardID = boardID;
	}

	public String getBoardMembers() {
		return boardMembers;
	}

	public void setBoardMembers(String boardMembers) {
		this.boardMembers = boardMembers;
	}

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	public List<KeyValueDTO> getLocalBoardMembersList() {
		return localBoardMembersList;
	}

	public void setLocalBoardMembersList(List<KeyValueDTO> localBoardMembersList) {
		this.localBoardMembersList = localBoardMembersList;
	}

	public String getExpEmp() {
		return expEmp;
	}

	public void setExpEmp(String expEmp) {
		this.expEmp = expEmp;
	}

	public List<ExceptionalEmpDTO> getExceptionalEmp() {
		return exceptionalEmp;
	}

	public void setExceptionalEmp(List<ExceptionalEmpDTO> exceptionalEmp) {
		this.exceptionalEmp = exceptionalEmp;
	}

	public List<YearTypeDTO> getYearList() {
		return yearList;
	}

	public void setYearList(List<YearTypeDTO> yearList) {
		this.yearList = yearList;
	}

	public int getYearID() {
		return yearID;
	}

	public void setYearID(int yearID) {
		this.yearID = yearID;
	}

	public String getCenter() {
		return center;
	}

	public void setCenter(String center) {
		this.center = center;
	}

	public String getCoOrdinator() {
		return coOrdinator;
	}

	public void setCoOrdinator(String coOrdinator) {
		this.coOrdinator = coOrdinator;
	}

	public String getCoOrdinatorLab() {
		return coOrdinatorLab;
	}

	public void setCoOrdinatorLab(String coOrdinatorLab) {
		this.coOrdinatorLab = coOrdinatorLab;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public List<VenueDetailsDTO> getVenueList() {
		return venueList;
	}

	public void setVenueList(List<VenueDetailsDTO> venueList) {
		this.venueList = venueList;
	}

	public String getNodeID() {
		return nodeID;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}

	public int getAssessmentTypeID() {
		return assessmentTypeID;
	}

	public void setAssessmentTypeID(int assessmentTypeID) {
		this.assessmentTypeID = assessmentTypeID;
	}

	public int getAssessmentCategoryID() {
		return assessmentCategoryID;
	}

	public void setAssessmentCategoryID(int assessmentCategoryID) {
		this.assessmentCategoryID = assessmentCategoryID;
	}

	public int getDesignationFrom() {
		return designationFrom;
	}

	public void setDesignationFrom(int designationFrom) {
		this.designationFrom = designationFrom;
	}

	public int getDesignationTo() {
		return designationTo;
	}

	public void setDesignationTo(int designationTo) {
		this.designationTo = designationTo;
	}

	public int getResidencyPeriod() {
		return residencyPeriod;
	}

	public void setResidencyPeriod(int residencyPeriod) {
		this.residencyPeriod = residencyPeriod;
	}

	public int getRelaxationInMonths() {
		return relaxationInMonths;
	}

	public void setRelaxationInMonths(int relaxationInMonths) {
		this.relaxationInMonths = relaxationInMonths;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public List<AssessmentCategoryDTO> getAssessmentCategoryList() {
		return assessmentCategoryList;
	}

	public void setAssessmentCategoryList(List<AssessmentCategoryDTO> assessmentCategoryList) {
		this.assessmentCategoryList = assessmentCategoryList;
	}

	public List<CategoryDTO> getAssessmentTypeList() {
		return assessmentTypeList;
	}

	public void setAssessmentTypeList(List<CategoryDTO> assessmentTypeList) {
		this.assessmentTypeList = assessmentTypeList;
	}

	public List<DesignationDTO> getDesignationList() {
		return designationList;
	}

	public void setDesignationList(List<DesignationDTO> designationList) {
		this.designationList = designationList;
	}

	public List<ResidencyPeriodDTO> getResidencyPeriodList() {
		return residencyPeriodList;
	}

	public void setResidencyPeriodList(List<ResidencyPeriodDTO> residencyPeriodList) {
		this.residencyPeriodList = residencyPeriodList;
	}

	public String getSfID() {
		return sfID;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getDesigAttempts() {
		return desigAttempts;
	}

	public void setDesigAttempts(int desigAttempts) {
		this.desigAttempts = desigAttempts;
	}

	public int getDesignation() {
		return designation;
	}

	public void setDesignation(int designation) {
		this.designation = designation;
	}

	public String getRefSfid() {
		return refSfid;
	}

	public void setRefSfid(String refSfid) {
		this.refSfid = refSfid;
	}

	public int getDesigID() {
		return desigID;
	}

	public void setDesigID(int desigID) {
		this.desigID = desigID;
	}

	public List<DoPartBean> getPayFixationDOPartDetails() {
		return payFixationDOPartDetails;
	}

	public void setPayFixationDOPartDetails(List<DoPartBean> payFixationDOPartDetails) {
		this.payFixationDOPartDetails = payFixationDOPartDetails;
	}

	public int getDisciplineID() {
		return disciplineID;
	}

	public void setDisciplineID(int disciplineID) {
		this.disciplineID = disciplineID;
	}

	public List<PromotionsSubDisciplineDTO> getDisciplineDetails() {
		return disciplineDetails;
	}

	public void setDisciplineDetails(List<PromotionsSubDisciplineDTO> disciplineDetails) {
		this.disciplineDetails = disciplineDetails;
	}

	public String getGazettedType() {
		return gazettedType;
	}

	public void setGazettedType(String gazettedType) {
		this.gazettedType = gazettedType;
	}

	public EmployeeBean getEmpDetails() {
		return empDetails;
	}

	public void setEmpDetails(EmployeeBean empDetails) {
		this.empDetails = empDetails;
	}

	public List<PromotionsDisciplineDTO> getDisciplineList() {
		return disciplineList;
	}

	public void setDisciplineList(List<PromotionsDisciplineDTO> disciplineList) {
		this.disciplineList = disciplineList;
	}

	public void setCasualitiesList(List<CasualityDetailsDTO> casualitiesList) {
		this.casualitiesList = casualitiesList;
	}

	public List<CasualityDetailsDTO> getCasualitiesList() {
		return casualitiesList;
	}

	public void setDoPartNo(String doPartNo) {
		this.doPartNo = doPartNo;
	}

	public String getDoPartNo() {
		return doPartNo;
	}

	public void setFinanceAcceptedDate(Date financeAcceptedDate) {
		this.financeAcceptedDate = financeAcceptedDate;
	}

	public Date getFinanceAcceptedDate() {
		return financeAcceptedDate;
	}

	public void setPayBillRunMonth(Date payBillRunMonth) {
		this.payBillRunMonth = payBillRunMonth;
	}

	public Date getPayBillRunMonth() {
		return payBillRunMonth;
	}

	

	public String getBoardType() {
		return boardType;
	}

	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}

	

}
