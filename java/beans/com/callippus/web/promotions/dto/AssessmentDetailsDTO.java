package com.callippus.web.promotions.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.beans.dto.DepartmentsDTO;
import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.ReservationDTO;
import com.callippus.web.beans.dto.YearTypeDTO;
import com.callippus.web.beans.requests.DoPartBean;

public class AssessmentDetailsDTO extends CommonDTO {
	private AssessmentCategoryDTO assessmentCategoryDetails;
	private int assessmentCategoryID;
	private YearTypeDTO yearDetails;
	private int yearID;
	private DoPartBean referenceDetails;
	private int referenceID;
	private String sfID;
	private DesignationDTO designationDetails;
	private int designationID;
	private DepartmentsDTO departmentDetails;
	private int departmentID;
	private String attempts;
	private Date interviewDate;
	private VenueDetailsDTO venueDetails;
	private int venueID;
	private String venue;
	private BoardMasterDTO boardDetails;
	private int boardID;
	private String board;
	private String labRepresentative;
	private Date effectiveDate;
	private Integer variableIncr;
	private ReservationDTO reservationDetails;
	private int reservationID;
	private String reservation;
	private String empName;
	private String designation;
	private String department;

	private String grade;
	private String gradePay;
	private String basicPay;
	private String twoAddIncr;

	private String newBasicPay;
	private String newGradePay;
	private String newTwoAddIncr;
	private Date promotionDate;
	private int status;
	private int relaxationInMonths;
	private int residencyPeriod;
	private int assessmentID;
	private Date incrementDate;
	private String scaleOfPay;
	private Date requestedDate;
    private String promotionStatus;
    private String disciplineName;
    private int disciplineID;
    private Date seniorityDate;
    private String desigExperince;
    private int subDisciplineID;
    private String subDisciplineName;
    private String designationTo;
    private Date endingDate;
    private String eolWomc;
    private Date incrementsAccepteddate;
    private Date fixationAcceptedDate;
    private Date varIncEndDate;
    private String payFixVariableIncr;
    private int incrementsDoPartId;
	private Integer fixationDoPartId;
	private Date fixationAcceptedDateFinance;
    private int toDesignation;
    private Integer residencyPeriodId;
    private Integer Written;
	private Integer trade;
	private Integer interview;
	private Integer boardFlag;
    private String WrittenDate;
    private String tradeDate;
    private Integer basicpayId;
    private Integer gradepayId;    
    private Integer optionStatus;
    private Integer payStatus;
    private Integer annualIncrementId;
    private String currentDesignation;
    private String masterBasicPay;
    private String calculatedBasicPay;
    private String optionID;
    private Integer varIncrValue;  //This property has been added for to display the increment value
    private Integer gazettedId;  //This property has been added for to to get gazettedType value
    private Date doj_drdo; 	

	
	
	
	
	public Date getDoj_drdo() {
		return doj_drdo;
	}
	public void setDoj_drdo(Date doj_drdo) {
		this.doj_drdo = doj_drdo;
	}
	public Integer getGazettedId() {
		return gazettedId;
	}
	public void setGazettedId(Integer gazettedId) {
		this.gazettedId = gazettedId;
	}
	public Integer getVarIncrValue() {
		return varIncrValue;
	}
	public void setVarIncrValue(Integer varIncrValue) {
		this.varIncrValue = varIncrValue;
	}
	//	
	public String getOptionID() {
		return optionID;
	}
	public void setOptionID(String optionID) {
		this.optionID = optionID;
	}
	public String getCalculatedBasicPay() {
		return calculatedBasicPay;
	}
	public void setCalculatedBasicPay(String calculatedBasicPay) {
		this.calculatedBasicPay = calculatedBasicPay;
	}
    
    
    
    public String getMasterBasicPay() {
		return masterBasicPay;
	}
	
	public void setMasterBasicPay(String masterBasicPay) {
		this.masterBasicPay = masterBasicPay;
	}
    
    public String getCurrentDesignation() {
		return currentDesignation;
	}
	
	public void setCurrentDesignation(String currentDesignation) {
		this.currentDesignation = currentDesignation;
	}
   
	public Integer getAnnualIncrementId() {
		return annualIncrementId;
	}
	
	public void setAnnualIncrementId(Integer annualIncrementId) {
		this.annualIncrementId = annualIncrementId;
	}
	 public Integer getPayStatus() {
			return payStatus;
		}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
    
    
    
	public Integer getOptionStatus() {
		return optionStatus;
	}
	
	public void setOptionStatus(Integer optionStatus) {
		this.optionStatus = optionStatus;
	}
	public Integer getBasicpayId() {
		return basicpayId;
	}

	

	public void setBasicpayId(Integer basicpayId) {
		this.basicpayId = basicpayId;
	}

	public Integer getGradepayId() {
		return gradepayId;
	}

	public void setGradepayId(Integer gradepayId) {
		this.gradepayId = gradepayId;
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
	public Integer getBoardFlag() {
		return boardFlag;
	}

	public void setBoardFlag(Integer boardFlag) {
		this.boardFlag = boardFlag;
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
	public int getToDesignation() {
		return toDesignation;
	}

	public Integer getResidencyPeriodId() {
		return residencyPeriodId;
	}

	public void setResidencyPeriodId(Integer residencyPeriodId) {
		this.residencyPeriodId = residencyPeriodId;
	}

	public void setToDesignation(int toDesignation) {
		this.toDesignation = toDesignation;
	}

	public Date getVarIncEndDate() {
		return varIncEndDate;
	}

	public void setVarIncEndDate(Date varIncEndDate) {
		this.varIncEndDate = varIncEndDate;
	}

	public Date getIncrementsAccepteddate() {
		return incrementsAccepteddate;
	}

	public void setIncrementsAccepteddate(Date incrementsAccepteddate) {
		this.incrementsAccepteddate = incrementsAccepteddate;
	}
	public Date getFixationAcceptedDate() {
		return fixationAcceptedDate;
	}

	public void setFixationAcceptedDate(Date fixationAcceptedDate) {
		this.fixationAcceptedDate = fixationAcceptedDate;
	}

	public String getEolWomc() {
		return eolWomc;
	}

	public void setEolWomc(String eolWomc) {
		this.eolWomc = eolWomc;
	}

	public Date getEndingDate() {
		return endingDate;
	}

	public void setEndingDate(Date endingDate) {
		this.endingDate = endingDate;
	}

	public String getDesignationTo() {
		return designationTo;
	}

	public void setDesignationTo(String designationTo) {
		this.designationTo = designationTo;
	}

	public String getSubDisciplineName() {
		return subDisciplineName;
	}

	public void setSubDisciplineName(String subDisciplineName) {
		this.subDisciplineName = subDisciplineName;
	}

	public int getSubDisciplineID() {
		return subDisciplineID;
	}

	public void setSubDisciplineID(int subDisciplineID) {
		this.subDisciplineID = subDisciplineID;
	}

	public String getPromotionStatus() {
		return promotionStatus;
	}

	public void setPromotionStatus(String promotionStatus) {
		this.promotionStatus = promotionStatus;
	}

	public String getReservation() {
		return reservation;
	}

	public void setReservation(String reservation) {
		this.reservation = reservation;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getBoard() {
		return board;
	}

	public void setBoard(String board) {
		this.board = board;
	}

	public Date getIncrementDate() {
		return incrementDate;
	}

	public void setIncrementDate(Date incrementDate) {
		this.incrementDate = incrementDate;
	}

	public String getScaleOfPay() {
		return scaleOfPay;
	}

	public void setScaleOfPay(String scaleOfPay) {
		this.scaleOfPay = scaleOfPay;
	}

	public Date getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}

	public int getAssessmentID() {
		return assessmentID;
	}

	public void setAssessmentID(int assessmentID) {
		this.assessmentID = assessmentID;
	}

	public Date getPromotionDate() {
		return promotionDate;
	}

	public void setPromotionDate(Date promotionDate) {
		this.promotionDate = promotionDate;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getGradePay() {
		return gradePay;
	}

	public void setGradePay(String gradePay) {
		this.gradePay = gradePay;
	}

	public String getBasicPay() {
		return basicPay;
	}

	public void setBasicPay(String basicPay) {
		this.basicPay = basicPay;
	}

	public String getTwoAddIncr() {
		return twoAddIncr;
	}

	public void setTwoAddIncr(String twoAddIncr) {
		this.twoAddIncr = twoAddIncr;
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

	public String getNewTwoAddIncr() {
		return newTwoAddIncr;
	}

	public void setNewTwoAddIncr(String newTwoAddIncr) {
		this.newTwoAddIncr = newTwoAddIncr;
	}

	@Override
	public int getStatus() {
		return status;
	}

	@Override
	public void setStatus(int status) {
		this.status = status;
	}

	public int getRelaxationInMonths() {
		return relaxationInMonths;
	}

	public void setRelaxationInMonths(int relaxationInMonths) {
		this.relaxationInMonths = relaxationInMonths;
	}

	public int getResidencyPeriod() {
		return residencyPeriod;
	}

	public void setResidencyPeriod(int residencyPeriod) {
		this.residencyPeriod = residencyPeriod;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public AssessmentCategoryDTO getAssessmentCategoryDetails() {
		return assessmentCategoryDetails;
	}

	public void setAssessmentCategoryDetails(AssessmentCategoryDTO assessmentCategoryDetails) {
		this.assessmentCategoryDetails = assessmentCategoryDetails;
	}

	public int getAssessmentCategoryID() {
		return assessmentCategoryID;
	}

	public void setAssessmentCategoryID(int assessmentCategoryID) {
		this.assessmentCategoryID = assessmentCategoryID;
	}

	public YearTypeDTO getYearDetails() {
		return yearDetails;
	}

	public void setYearDetails(YearTypeDTO yearDetails) {
		this.yearDetails = yearDetails;
	}

	public int getYearID() {
		return yearID;
	}

	public void setYearID(int yearID) {
		this.yearID = yearID;
	}

	public DoPartBean getReferenceDetails() {
		return referenceDetails;
	}

	public void setReferenceDetails(DoPartBean referenceDetails) {
		this.referenceDetails = referenceDetails;
	}

	public int getReferenceID() {
		return referenceID;
	}

	public void setReferenceID(int referenceID) {
		this.referenceID = referenceID;
	}

	public String getSfID() {
		return sfID;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
	}

	public DesignationDTO getDesignationDetails() {
		return designationDetails;
	}

	public void setDesignationDetails(DesignationDTO designationDetails) {
		this.designationDetails = designationDetails;
	}

	public int getDesignationID() {
		return designationID;
	}

	public void setDesignationID(int designationID) {
		this.designationID = designationID;
	}

	public DepartmentsDTO getDepartmentDetails() {
		return departmentDetails;
	}

	public void setDepartmentDetails(DepartmentsDTO departmentDetails) {
		this.departmentDetails = departmentDetails;
	}

	public int getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}

	public String getAttempts() {
		return attempts;
	}

	public void setAttempts(String attempts) {
		this.attempts = attempts;
	}

	public Date getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(Date interviewDate) {
		this.interviewDate = interviewDate;
	}

	public VenueDetailsDTO getVenueDetails() {
		return venueDetails;
	}

	public void setVenueDetails(VenueDetailsDTO venueDetails) {
		this.venueDetails = venueDetails;
	}

	public int getVenueID() {
		return venueID;
	}

	public void setVenueID(int venueID) {
		this.venueID = venueID;
	}

	public BoardMasterDTO getBoardDetails() {
		return boardDetails;
	}

	public void setBoardDetails(BoardMasterDTO boardDetails) {
		this.boardDetails = boardDetails;
	}

	public int getBoardID() {
		return boardID;
	}

	public void setBoardID(int boardID) {
		this.boardID = boardID;
	}

	public String getLabRepresentative() {
		return labRepresentative;
	}

	public void setLabRepresentative(String labRepresentative) {
		this.labRepresentative = labRepresentative;
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

	public ReservationDTO getReservationDetails() {
		return reservationDetails;
	}

	public void setReservationDetails(ReservationDTO reservationDetails) {
		this.reservationDetails = reservationDetails;
	}

	public int getReservationID() {
		return reservationID;
	}

	public void setReservationID(int reservationID) {
		this.reservationID = reservationID;
	}

	public String getDisciplineName() {
		return disciplineName;
	}

	public void setDisciplineName(String disciplineName) {
		this.disciplineName = disciplineName;
	}

	public int getDisciplineID() {
		return disciplineID;
	}

	public void setDisciplineID(int disciplineID) {
		this.disciplineID = disciplineID;
	}


	public Date getSeniorityDate() {
		return seniorityDate;
	}

	public void setSeniorityDate(Date seniorityDate) {
		this.seniorityDate = seniorityDate;
	}

	public String getDesigExperince() {
		return desigExperince;
	}

	public void setDesigExperince(String desigExperince) {
		this.desigExperince = desigExperince;
	}

	public void setPayFixVariableIncr(String payFixVariableIncr) {
		this.payFixVariableIncr = payFixVariableIncr;
	}

	public String getPayFixVariableIncr() {
		return payFixVariableIncr;
	}

	public void setFixationDoPartId(Integer fixationDoPartId) {
		this.fixationDoPartId = fixationDoPartId;
	}

	public Integer getFixationDoPartId() {
		return fixationDoPartId;
	}

	public void setIncrementsDoPartId(int incrementsDoPartId) {
		this.incrementsDoPartId = incrementsDoPartId;
	}

	public int getIncrementsDoPartId() {
		return incrementsDoPartId;
	}

	public void setFixationAcceptedDateFinance(Date fixationAcceptedDateFinance) {
		this.fixationAcceptedDateFinance = fixationAcceptedDateFinance;
	}

	public Date getFixationAcceptedDateFinance() {
		return fixationAcceptedDateFinance;
	}
	
}