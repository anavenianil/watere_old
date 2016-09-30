package com.callippus.web.beans.qualification;

import java.util.List;

import com.callippus.web.beans.common.ErpBean;
import com.callippus.web.beans.dto.DegreeDTO;
import com.callippus.web.beans.dto.YearTypeDTO;

public class QualificationBean extends ErpBean {
	private String id;
	private String qualification;
	private String discipline;
	private String specilisation;
	private String year;
	private String university;
	private String sponsored;
	private String qualAttainedDRDO;
	private String reOraganised;
	private String incentive;
	private String amount;
	private int status;
	private String creationDate;
	private String lastModifiedDate;
	private List empQualificationList;
	private List qualificationList;
	private List desciplineList;
	private List<YearTypeDTO> yearList;
	private List<DegreeDTO> degreeList;
	private String type;
	private String cdate;
	private String divisionId;
	private String qualDivisionID;
	private String degree;
	private String totalPercentage;
	private String qualSFID;
	private String qualiMasterName;
	private String diciplineMasterName;
	private String yearMasterName;
	private String grade;
	private String message;
	private String marks;

	public String getMarks() {
		return marks;
	}

	public void setMarks(String marks) {
		this.marks = marks;
	}

	public List<DegreeDTO> getDegreeList() {
		return degreeList;
	}

	public void setDegreeList(List<DegreeDTO> degreeList) {
		this.degreeList = degreeList;
	}

	public List<YearTypeDTO> getYearList() {
		return yearList;
	}

	public void setYearList(List<YearTypeDTO> yearList) {
		this.yearList = yearList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getQualiMasterName() {
		return qualiMasterName;
	}

	public void setQualiMasterName(String qualiMasterName) {
		this.qualiMasterName = qualiMasterName;
	}

	public String getDiciplineMasterName() {
		return diciplineMasterName;
	}

	public void setDiciplineMasterName(String diciplineMasterName) {
		this.diciplineMasterName = diciplineMasterName;
	}

	public String getYearMasterName() {
		return yearMasterName;
	}

	public void setYearMasterName(String yearMasterName) {
		this.yearMasterName = yearMasterName;
	}

	public String getQualSFID() {
		return qualSFID;
	}

	public void setQualSFID(String qualSFID) {
		this.qualSFID = qualSFID;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getTotalPercentage() {
		return totalPercentage;
	}

	public void setTotalPercentage(String totalPercentage) {
		this.totalPercentage = totalPercentage;
	}

	public String getQualDivisionID() {
		return qualDivisionID;
	}

	public void setQualDivisionID(String qualDivisionID) {
		this.qualDivisionID = qualDivisionID;
	}

	public String getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}

	public String getCdate() {
		return cdate;
	}

	public void setCdate(String cdate) {
		this.cdate = cdate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getSpecilisation() {
		return specilisation;
	}

	public void setSpecilisation(String specilisation) {
		this.specilisation = specilisation;
	}

	public String getSponsored() {
		return sponsored;
	}

	public void setSponsored(String sponsored) {
		this.sponsored = sponsored;
	}

	public List getQualificationList() {
		return qualificationList;
	}

	public void setQualificationList(List qualificationList) {
		this.qualificationList = qualificationList;
	}

	public List getDesciplineList() {
		return desciplineList;
	}

	public void setDesciplineList(List desciplineList) {
		this.desciplineList = desciplineList;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List getEmpQualificationList() {
		return empQualificationList;
	}

	public void setEmpQualificationList(List empQualificationList) {
		this.empQualificationList = empQualificationList;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getDiscipline() {
		return discipline;
	}

	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getQualAttainedDRDO() {
		return qualAttainedDRDO;
	}

	public void setQualAttainedDRDO(String qualAttainedDRDO) {
		this.qualAttainedDRDO = qualAttainedDRDO;
	}

	public String getReOraganised() {
		return reOraganised;
	}

	public void setReOraganised(String reOraganised) {
		this.reOraganised = reOraganised;
	}

	public String getIncentive() {
		return incentive;
	}

	public void setIncentive(String incentive) {
		this.incentive = incentive;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

}
