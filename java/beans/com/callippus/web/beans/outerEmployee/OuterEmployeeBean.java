package com.callippus.web.beans.outerEmployee;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.BindingType;

import com.callippus.web.beans.dto.AppointmentDTO;
import com.callippus.web.beans.dto.BloodGroupDTO;
import com.callippus.web.beans.dto.CommunityDTO;
import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.DirectorateDTO;
import com.callippus.web.beans.dto.DispensaryNumberDTO;
import com.callippus.web.beans.dto.EmploymentDTO;
import com.callippus.web.beans.dto.MaritalDTO;
import com.callippus.web.beans.dto.NationalityDTO;
import com.callippus.web.beans.dto.OrgInstanceDTO;
import com.callippus.web.beans.dto.OrganizationsDTO;
import com.callippus.web.beans.dto.PHTypeDTO;
import com.callippus.web.beans.dto.ReligionDTO;
import com.callippus.web.beans.dto.TitleDTO;

/**
 * 
 * @author Administrator
 *
 */
@BindingType
public class OuterEmployeeBean {
	private int marital;
	private int handicap;
	private int religionId;
	
	private int bloodGroupId;
	private int community;
	private int reservation;
	private int dispensaryNumberId;
	private int employmentId;
	private int joinTypeId;
	private String param;
	private String sfid;
	private String title;
	private String firstName;
	private String middleName;
	private String lastName;
	private String gender;
	private String personalNumber;
	private String dob;
	private String designationId;
	private String directorateId;
	private String dojGovt;
	private String dojAsl;
	private String dojDrdo;
	
	private int status;
	private String internalNo;
	private String residenceNo;
	private String officeId;
	private String nameInServiceBook;
    private String message;
	private String nationalityId;
	private String userSfid;
	private String generatedId;
	private String divisionId;
	private List<TitleDTO> titleList;
	private List<DesignationDTO> designationList;
	private List<DirectorateDTO> directorateList;
	private List<NationalityDTO> nationalityList;
	private List<BloodGroupDTO> bloodList;
	private List<PHTypeDTO> handicapList;
	private List<DispensaryNumberDTO> dispensaryNumberList;
	private String checkDate;
	private String creationDate;
	private String lastModifiedDate;
	private String createdBy;
	private String lastModifiedBy;
	private String dataentryFlag;
	private int titleId;
	private int designation;
	private int directorate;
	private int office;
	private int id;
	private int division;
	private List<OrgInstanceDTO> officeList;
	private String type;
	private String instanceId;
	private String nationality;
	private String loginSfid;
	private String flag;
	private String workingLocation;
	@SuppressWarnings("unchecked")
	private List divisionList;
	@SuppressWarnings("unchecked")
	private Integer roleInstanceId;
	private String headName;
	private int appointmentId;
	private List<OrganizationsDTO> organizationsList;
	private String gpfAcNo;
	private String seniorityDate;
	private List<EmploymentDTO> employementTypeList;
	private List<AppointmentDTO> appointmentTypeList;
	private List<MaritalDTO> maritalList;
	private List<CommunityDTO> communityList;
	private String motherTongue; 
	private String lastPromotion;
	private String pranNo;
	
	//16/05/2016
	private String idMarks;
	
	private String religion;
	
	
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	public String getIdMarks() {
		return idMarks;
	}
	public void setIdMarks(String idMarks) {
		this.idMarks = idMarks;
	}
	private List<ReligionDTO> religionList;
	
	
	public List<ReligionDTO> getReligionList() {
		return religionList;
	}
	public void setReligionList(List<ReligionDTO> religionList) {
		this.religionList = religionList;
	}
	public List<PHTypeDTO> getHandicapList() {
		return handicapList;
	}
	public void setHandicapList(List<PHTypeDTO> handicapList) {
		this.handicapList = handicapList;
	}
	public List<DispensaryNumberDTO> getDispensaryNumberList() {
		return dispensaryNumberList;
	}
	public void setDispensaryNumberList(
			List<DispensaryNumberDTO> dispensaryNumberList) {
		this.dispensaryNumberList = dispensaryNumberList;
	}
	public List<BloodGroupDTO> getBloodList() {
		return bloodList;
	}
	public void setBloodList(List<BloodGroupDTO> bloodList) {
		this.bloodList = bloodList;
	}
	public String getPranNo() {
		return pranNo;
	}
	public void setPranNo(String pranNo) {
		this.pranNo = pranNo;
	}
	public String getLastPromotion() {
		return lastPromotion;
	}
	public void setLastPromotion(String lastPromotion) {
		this.lastPromotion = lastPromotion;
	}
	public String getDojGovt() {
		return dojGovt;
	}
	public void setDojGovt(String dojGovt) {
		this.dojGovt = dojGovt;
	}
	public List<OrganizationsDTO> getOrganizationsList() {
		return organizationsList;
	}
	public void setOrganizationsList(List<OrganizationsDTO> organizationsList) {
		this.organizationsList = organizationsList;
	}
	public int getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}
	public int getMarital() {
		return marital;
	}
	public void setMarital(int marital) {
		this.marital = marital;
	}
	public int getHandicap() {
		return handicap;
	}
	public void setHandicap(int handicap) {
		this.handicap = handicap;
	}
	public int getReligionId() {
		return religionId;
	}
	public void setReligionId(int religionId) {
		this.religionId = religionId;
	}
	public int getBloodGroupId() {
		return bloodGroupId;
	}
	public void setBloodGroupId(int bloodGroupId) {
		this.bloodGroupId = bloodGroupId;
	}
	public int getCommunity() {
		return community;
	}
	public void setCommunity(int community) {
		this.community = community;
	}
	public int getReservation() {
		return reservation;
	}
	public void setReservation(int reservation) {
		this.reservation = reservation;
	}
	public int getDispensaryNumberId() {
		return dispensaryNumberId;
	}
	public void setDispensaryNumberId(int dispensaryNumberId) {
		this.dispensaryNumberId = dispensaryNumberId;
	}
	public int getEmploymentId() {
		return employmentId;
	}
	public void setEmploymentId(int employmentId) {
		this.employmentId = employmentId;
	}
	public int getJoinTypeId() {
		return joinTypeId;
	}
	public void setJoinTypeId(int joinTypeId) {
		this.joinTypeId = joinTypeId;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPersonalNumber() {
		return personalNumber;
	}
	public void setPersonalNumber(String personalNumber) {
		this.personalNumber = personalNumber;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getDesignationId() {
		return designationId;
	}
	public void setDesignationId(String designationId) {
		this.designationId = designationId;
	}
	public String getDirectorateId() {
		return directorateId;
	}
	public void setDirectorateId(String directorateId) {
		this.directorateId = directorateId;
	}
	public String getDojAsl() {
		return dojAsl;
	}
	public void setDojAsl(String dojAsl) {
		this.dojAsl = dojAsl;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getInternalNo() {
		return internalNo;
	}
	public void setInternalNo(String internalNo) {
		this.internalNo = internalNo;
	}
	public String getResidenceNo() {
		return residenceNo;
	}
	public void setResidenceNo(String residenceNo) {
		this.residenceNo = residenceNo;
	}
	public String getOfficeId() {
		return officeId;
	}
	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	public String getNameInServiceBook() {
		return nameInServiceBook;
	}
	public void setNameInServiceBook(String nameInServiceBook) {
		this.nameInServiceBook = nameInServiceBook;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getNationalityId() {
		return nationalityId;
	}
	public void setNationalityId(String nationalityId) {
		this.nationalityId = nationalityId;
	}
	public String getUserSfid() {
		return userSfid;
	}
	public void setUserSfid(String userSfid) {
		this.userSfid = userSfid;
	}
	public String getGeneratedId() {
		return generatedId;
	}
	public void setGeneratedId(String generatedId) {
		this.generatedId = generatedId;
	}
	public String getDivisionId() {
		return divisionId;
	}
	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}
	public List<TitleDTO> getTitleList() {
		return titleList;
	}
	public void setTitleList(List<TitleDTO> titleList) {
		this.titleList = titleList;
	}
	public List<DesignationDTO> getDesignationList() {
		return designationList;
	}
	public void setDesignationList(List<DesignationDTO> designationList) {
		this.designationList = designationList;
	}
	public List<DirectorateDTO> getDirectorateList() {
		return directorateList;
	}
	public void setDirectorateList(List<DirectorateDTO> directorateList) {
		this.directorateList = directorateList;
	}
	public List<NationalityDTO> getNationalityList() {
		return nationalityList;
	}
	public void setNationalityList(List<NationalityDTO> nationalityList) {
		this.nationalityList = nationalityList;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public String getDataentryFlag() {
		return dataentryFlag;
	}
	public void setDataentryFlag(String dataentryFlag) {
		this.dataentryFlag = dataentryFlag;
	}
	public int getTitleId() {
		return titleId;
	}
	public void setTitleId(int titleId) {
		this.titleId = titleId;
	}
	public int getDesignation() {
		return designation;
	}
	public void setDesignation(int designation) {
		this.designation = designation;
	}
	public int getDirectorate() {
		return directorate;
	}
	public void setDirectorate(int directorate) {
		this.directorate = directorate;
	}
	public int getOffice() {
		return office;
	}
	public void setOffice(int office) {
		this.office = office;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDivision() {
		return division;
	}
	public void setDivision(int division) {
		this.division = division;
	}
	public List<OrgInstanceDTO> getOfficeList() {
		return officeList;
	}
	public void setOfficeList(List<OrgInstanceDTO> officeList) {
		this.officeList = officeList;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getLoginSfid() {
		return loginSfid;
	}
	public void setLoginSfid(String loginSfid) {
		this.loginSfid = loginSfid;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getWorkingLocation() {
		return workingLocation;
	}
	public void setWorkingLocation(String workingLocation) {
		this.workingLocation = workingLocation;
	}
	public List getDivisionList() {
		return divisionList;
	}
	public void setDivisionList(List divisionList) {
		this.divisionList = divisionList;
	}
	public Integer getRoleInstanceId() {
		return roleInstanceId;
	}
	public void setRoleInstanceId(Integer roleInstanceId) {
		this.roleInstanceId = roleInstanceId;
	}
	public String getHeadName() {
		return headName;
	}
	public void setHeadName(String headName) {
		this.headName = headName;
	}
	public String getGpfAcNo() {
		return gpfAcNo;
	}
	public void setGpfAcNo(String gpfAcNo) {
		this.gpfAcNo = gpfAcNo;
	}

	public String getDojDrdo() {
		return dojDrdo;
	}
	public void setDojDrdo(String dojDrdo) {
		this.dojDrdo = dojDrdo;
	}
	public String getSeniorityDate() {
		return seniorityDate;
	}
	public void setSeniorityDate(String seniorityDate) {
		this.seniorityDate = seniorityDate;
	}
	public List<EmploymentDTO> getEmployementTypeList() {
		return employementTypeList;
	}
	public void setEmployementTypeList(List<EmploymentDTO> employementTypeList) {
		this.employementTypeList = employementTypeList;
	}
	public String getMotherTongue() {
		return motherTongue;
	}
	public void setMotherTongue(String motherTongue) {
		this.motherTongue = motherTongue;
	}
	public List<AppointmentDTO> getAppointmentTypeList() {
		return appointmentTypeList;
	}
	public void setAppointmentTypeList(List<AppointmentDTO> appointmentTypeList) {
		this.appointmentTypeList = appointmentTypeList;
	}
	public List<MaritalDTO> getMaritalList() {
		return maritalList;
	}
	public void setMaritalList(List<MaritalDTO> maritalList) {
		this.maritalList = maritalList;
	}
	public List<CommunityDTO> getCommunityList() {
		return communityList;
	}
	public void setCommunityList(List<CommunityDTO> communityList) {
		this.communityList = communityList;
	}
	
	

	}
