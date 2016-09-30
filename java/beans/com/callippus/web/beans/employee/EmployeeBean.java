package com.callippus.web.beans.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.ws.BindingType;

import org.springframework.web.multipart.MultipartFile;

import net.sf.json.JSONObject;

import com.callippus.web.beans.dto.AppointmentDTO;
import com.callippus.web.beans.dto.BloodGroupDTO;
import com.callippus.web.beans.dto.CommunityDTO;
import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.DirectorateDTO;
import com.callippus.web.beans.dto.DispensaryNumberDTO;
import com.callippus.web.beans.dto.DivisionDTO;
import com.callippus.web.beans.dto.EmploymentDTO;
import com.callippus.web.beans.dto.JoinTypeDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.MaritalDTO;
import com.callippus.web.beans.dto.NationalityDTO;
import com.callippus.web.beans.dto.OrgInstanceDTO;
import com.callippus.web.beans.dto.OrganizationsDTO;
import com.callippus.web.beans.dto.PHTypeDTO;
import com.callippus.web.beans.dto.ReligionDTO;
import com.callippus.web.beans.dto.ReservationDTO;
import com.callippus.web.beans.dto.TitleDTO;

/**
 * @author admin
 *
 */
/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 * 
 */
@BindingType
public class EmployeeBean {
   
    private String organizationId;
	private String password;
	private String newpassword;
    private String confirmpassword;
	private String param;
	private String sfid;
	private String parentSfid;
	private String title;
	private int titleId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String gender;
	private String personalNumber;
	private String dob;
	private String maritalId;
	private int marital;
	private String communityId;
	private int community;
	private String appointmentTypeId;
	private int appointmentId;
	private String employmentTypeId;
	private int employmentId;
	private String categoryId;
	private String empGroupId;
	private String designationId;
	private int designation;
	private String directorateId;
	private int directorate;
	private String divisionId;
	private int division;
	private String dojGovt;
	private String dojDrdo;
	private String dojAsl;
	private String seniorityDate;
	private String motherTongue;
	private String height;
	private String idMarks;
	private String panCardNumber;
	private String cgshNumber;
	private String dispersaryNumber;
	private String dispersaryName;
	private String gpfAcNo;
	private String pranNo;
	private String religion;
	private String lastPromotion;
	private List<OrganizationsDTO> orgList;
	private List<TitleDTO> titleList;
	private List<MaritalDTO> maritalList;
	private List<ReligionDTO> religionList;
	private List<CommunityDTO> communityList;
	private List<AppointmentDTO> appointmentTypeList;
	private List<EmploymentDTO> employementTypeList;
	private List<DesignationDTO> designationList;
	private List<DirectorateDTO> directorateList;
	private List<DivisionDTO> divisionList;
	private List<DispensaryNumberDTO> dispensaryNumberList;
	private List<ReservationDTO> reservationList;
	private List<PHTypeDTO> handicapList;
	private List<JoinTypeDTO> joinTypeList;

	private List<OrgInstanceDTO> officeList;
	private String name;
	private String type;
	private String generatedId;
	private String id;
	private int status;
	private String blood;
	private String internalNo;
	private String residenceNo;
	private String reservationId;
	private int reservation;
	private String ppaNo;
	private String handicapId;
	private int handicap;
	private int religionId;
	private int joinTypeId;
	private String workedIn;
	private String workedYears;
	private String famPlanning;
	private String houseAllowence;
	private String groupAllowence;
	private String uptoDate;
	private String userSfid;
	private String titleName;
	private String communityName;
	private String religionName;
	private String maritalName;
	private String appName;
	private String empName;
	private String divisionName;
	private String directorateName;
	private String designationName;
	private String reservationName;
	private String dispensaryName;
	private String officeId;
	private int office;
	private String officeName;
	private List employeeList;
	public String searchWith;
	private String instanceId;
	private List<KeyValueDTO> empInstanceList;
	private List<EmployeeBean> empSearchList;
	private List<EmployeeBean> empRoleslist;
	private List<EmployeeBean> empPaylist;
	private String parentRole;
	private String defaultRole;
	private String additionalRoles;
	//private String parentRole;
	private int dispensaryNumberId;
	private String joinType;
	private String nameInServiceBook;
	private String parent;
	private String handicapName;
	private String joinName;
	private String checkDate;
	private String message;
	private String nationality;
	private int nationalityId;
	private List<NationalityDTO> nationalityList;
	private String nationalityName;
	private List<BloodGroupDTO> bloodList;
	private String bloodName;
	private int bloodGroupId;
	private String headName;
	private String roleId;
	private String changedValues;
	private String searchingWith;
	private String selectedValue;
	private String enteredValue;
	private ArrayList<KeyValueDTO> instanceList;
	private ArrayList<List> empTreeDetails;
	private String relationName;
	private String relationId;
	private String seniorityRank;
	private String relationTitle;
	private String creationDate;
	private String lastModifiedDate;
	private String createdBy;
	private String lastModifiedBy;
	private String heightType;
	private String dataentryFlag;
	private String storeHight;
	private String relationIdName;
	private JSONObject empTreeDetailsJson;
	private String parentName;
	private String empTreeIdentity;
	private String empSearchId;
	private String loginSfid;
	private ArrayList<String> roleList;
	private int level;
	private String departmentId;
	private String discipline;

	private String basicPay;
	private String gradePay;
	private String retirementDate;
	private String etype;
	private List<KeyValueDTO> headList;
	private EmploymentDTO employmentDetails;
	private DesignationDTO designationDetails;
	private Date dobInFormat;
	private Date dojGovtInFormat;
	private Date dojDrdoInFormat;
	private Date dojAslInFormat;
	private String retiredDate;
	private String ltcAdvanceDays;
	private String workingLocation;
	private String fromID;

	private String toID;
	private List<EmployeeBean> EnabledEmployeeList;
	private List<EmployeeBean> DisabledEmployeeList;

	private String requestID;
	private String requestId;
	//private List<OrganizationsDTO> orgList;
	
	private int pk;
	private String selectedLinks;
	
	// Uploading photo attributes
	private String[] fileNames;
	private MultipartFile passportImageFile;
	private MultipartFile stampImageFile;
	private MultipartFile smallImageFile;
	private String accountNo;
	
	
	
	public MultipartFile getPassportImageFile() {
		return passportImageFile;
	}

	public void setPassportImageFile(MultipartFile passportImageFile) {
		this.passportImageFile = passportImageFile;
	}

	public MultipartFile getStampImageFile() {
		return stampImageFile;
	}

	public void setStampImageFile(MultipartFile stampImageFile) {
		this.stampImageFile = stampImageFile;
	}

	public MultipartFile getSmallImageFile() {
		return smallImageFile;
	}

	public void setSmallImageFile(MultipartFile smallImageFile) {
		this.smallImageFile = smallImageFile;
	}

	public String[] getFileName() {
		return fileNames;
	}

	public void setFileName(String[] fileNames) {
		this.fileNames = fileNames;
	}


	

	public int getPk() {
		return pk;
	}


	public String getToID() {
		return toID;
	}

	public void setToID(String toID) {
		this.toID = toID;
	}


	public void setPk(int pk) {
		this.pk = pk;
	}

	public String getSelectedLinks() {
		return selectedLinks;
	}

	public void setSelectedLinks(String selectedLinks) {
		this.selectedLinks = selectedLinks;
	}


	public List<OrganizationsDTO> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<OrganizationsDTO> orgList) {
		this.orgList = orgList;
	}

	
	public List<EmployeeBean> getDisabledEmployeeList() {
		return DisabledEmployeeList;
	}

	public void setDisabledEmployeeList(List<EmployeeBean> disabledEmployeeList) {
		DisabledEmployeeList = disabledEmployeeList;
	}

	
	
	public List<OrganizationsDTO> getOrganizationList;
    public List<OrganizationsDTO> getGetOrganizationList() {
		return getOrganizationList;
	}

	public void setGetOrganizationList(List<OrganizationsDTO> getOrganizationList) {
		this.getOrganizationList = getOrganizationList;
	}

	

	

	

	public String getWorkingLocation() {
		return workingLocation;
	}

	public void setWorkingLocation(String workingLocation) {
		this.workingLocation = workingLocation;
	}

	public String getLtcAdvanceDays() {
		return ltcAdvanceDays;
	}

	public void setLtcAdvanceDays(String ltcAdvanceDays) {
		this.ltcAdvanceDays = ltcAdvanceDays;
	}

	public String getRetiredDate() {
		return retiredDate;
	}

	public void setRetiredDate(String retiredDate) {
		this.retiredDate = retiredDate;
	}

	public Date getDobInFormat() {
		return dobInFormat;
	}

	public void setDobInFormat(Date dobInFormat) {
		this.dobInFormat = dobInFormat;
	}

	public Date getDojGovtInFormat() {
		return dojGovtInFormat;
	}

	public void setDojGovtInFormat(Date dojGovtInFormat) {
		this.dojGovtInFormat = dojGovtInFormat;
	}

	public Date getDojDrdoInFormat() {
		return dojDrdoInFormat;
	}

	public void setDojDrdoInFormat(Date dojDrdoInFormat) {
		this.dojDrdoInFormat = dojDrdoInFormat;
	}

	public Date getDojAslInFormat() {
		return dojAslInFormat;
	}

	public void setDojAslInFormat(Date dojAslInFormat) {
		this.dojAslInFormat = dojAslInFormat;
	}

	public EmploymentDTO getEmploymentDetails() {
		return employmentDetails;
	}

	public void setEmploymentDetails(EmploymentDTO employmentDetails) {
		this.employmentDetails = employmentDetails;
	}

	public DesignationDTO getDesignationDetails() {
		return designationDetails;
	}

	public void setDesignationDetails(DesignationDTO designationDetails) {
		this.designationDetails = designationDetails;
	}

	public List<KeyValueDTO> getHeadList() {
		return headList;
	}

	public void setHeadList(List<KeyValueDTO> headList) {
		this.headList = headList;
	}

	public String getGradePay() {
		return gradePay;
	}

	public void setGradePay(String gradePay) {
		this.gradePay = gradePay;
	}

	public String getRetirementDate() {
		return retirementDate;
	}

	public void setRetirementDate(String retirementDate) {
		this.retirementDate = retirementDate;
	}

	public String getEtype() {
		return etype;
	}

	public void setEtype(String etype) {
		this.etype = etype;
	}

	public String getDiscipline() {
		return discipline;
	}

	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public ArrayList<String> getRoleList() {
		return roleList;
	}

	public void setRoleList(ArrayList<String> roleList) {
		this.roleList = roleList;
	}

	public String getLoginSfid() {
		return loginSfid;
	}

	public void setLoginSfid(String loginSfid) {
		this.loginSfid = loginSfid;
	}

	public String getEmpSearchId() {
		return empSearchId;
	}

	public void setEmpSearchId(String empSearchId) {
		this.empSearchId = empSearchId;
	}

	public String getEmpTreeIdentity() {
		return empTreeIdentity;
	}

	public void setEmpTreeIdentity(String empTreeIdentity) {
		this.empTreeIdentity = empTreeIdentity;
	}

	public JSONObject getEmpTreeDetailsJson() {
		return empTreeDetailsJson;
	}

	public void setEmpTreeDetailsJson(JSONObject empTreeDetailsJson) {
		this.empTreeDetailsJson = empTreeDetailsJson;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
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

	public int getTitleId() {
		return titleId;
	}

	public void setTitleId(int titleId) {
		this.titleId = titleId;
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

	public String getMaritalId() {
		return maritalId;
	}

	public void setMaritalId(String maritalId) {
		this.maritalId = maritalId;
	}

	public int getMarital() {
		return marital;
	}

	public void setMarital(int marital) {
		this.marital = marital;
	}

	public String getCommunityId() {
		return communityId;
	}

	public void setCommunityId(String communityId) {
		this.communityId = communityId;
	}

	public int getCommunity() {
		return community;
	}

	public void setCommunity(int community) {
		this.community = community;
	}

	public String getAppointmentTypeId() {
		return appointmentTypeId;
	}

	public void setAppointmentTypeId(String appointmentTypeId) {
		this.appointmentTypeId = appointmentTypeId;
	}

	public int getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getEmploymentTypeId() {
		return employmentTypeId;
	}

	public void setEmploymentTypeId(String employmentTypeId) {
		this.employmentTypeId = employmentTypeId;
	}

	public int getEmploymentId() {
		return employmentId;
	}

	public void setEmploymentId(int employmentId) {
		this.employmentId = employmentId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getEmpGroupId() {
		return empGroupId;
	}

	public void setEmpGroupId(String empGroupId) {
		this.empGroupId = empGroupId;
	}

	public String getDesignationId() {
		return designationId;
	}

	public void setDesignationId(String designationId) {
		this.designationId = designationId;
	}

	public int getDesignation() {
		return designation;
	}

	public void setDesignation(int designation) {
		this.designation = designation;
	}

	public String getDirectorateId() {
		return directorateId;
	}

	public void setDirectorateId(String directorateId) {
		this.directorateId = directorateId;
	}

	public int getDirectorate() {
		return directorate;
	}

	public void setDirectorate(int directorate) {
		this.directorate = directorate;
	}

	public String getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}

	public int getDivision() {
		return division;
	}

	public void setDivision(int division) {
		this.division = division;
	}

	public String getDojGovt() {
		return dojGovt;
	}

	public void setDojGovt(String dojGovt) {
		this.dojGovt = dojGovt;
	}

	public String getDojDrdo() {
		return dojDrdo;
	}

	public void setDojDrdo(String dojDrdo) {
		this.dojDrdo = dojDrdo;
	}

	public String getDojAsl() {
		return dojAsl;
	}

	public void setDojAsl(String dojAsl) {
		this.dojAsl = dojAsl;
	}

	public String getSeniorityDate() {
		return seniorityDate;
	}

	public void setSeniorityDate(String seniorityDate) {
		this.seniorityDate = seniorityDate;
	}

	public String getMotherTongue() {
		return motherTongue;
	}

	public void setMotherTongue(String motherTongue) {
		this.motherTongue = motherTongue;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getIdMarks() {
		return idMarks;
	}

	public void setIdMarks(String idMarks) {
		this.idMarks = idMarks;
	}

	public String getPanCardNumber() {
		return panCardNumber;
	}

	public void setPanCardNumber(String panCardNumber) {
		this.panCardNumber = panCardNumber;
	}

	public String getCgshNumber() {
		return cgshNumber;
	}

	public void setCgshNumber(String cgshNumber) {
		this.cgshNumber = cgshNumber;
	}

	public String getDispersaryNumber() {
		return dispersaryNumber;
	}

	public void setDispersaryNumber(String dispersaryNumber) {
		this.dispersaryNumber = dispersaryNumber;
	}

	public String getDispersaryName() {
		return dispersaryName;
	}

	public void setDispersaryName(String dispersaryName) {
		this.dispersaryName = dispersaryName;
	}

	public String getGpfAcNo() {
		return gpfAcNo;
	}

	public void setGpfAcNo(String gpfAcNo) {
		this.gpfAcNo = gpfAcNo;
	}

	public String getPranNo() {
		return pranNo;
	}

	public void setPranNo(String pranNo) {
		this.pranNo = pranNo;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getLastPromotion() {
		return lastPromotion;
	}

	public void setLastPromotion(String lastPromotion) {
		this.lastPromotion = lastPromotion;
	}

	public List<TitleDTO> getTitleList() {
		return titleList;
	}

	public void setTitleList(List<TitleDTO> titleList) {
		this.titleList = titleList;
	}

	public List<MaritalDTO> getMaritalList() {
		return maritalList;
	}

	public void setMaritalList(List<MaritalDTO> maritalList) {
		this.maritalList = maritalList;
	}

	public List<ReligionDTO> getReligionList() {
		return religionList;
	}

	public void setReligionList(List<ReligionDTO> religionList) {
		this.religionList = religionList;
	}

	public List<CommunityDTO> getCommunityList() {
		return communityList;
	}

	public void setCommunityList(List<CommunityDTO> communityList) {
		this.communityList = communityList;
	}

	public List<AppointmentDTO> getAppointmentTypeList() {
		return appointmentTypeList;
	}

	public void setAppointmentTypeList(List<AppointmentDTO> appointmentTypeList) {
		this.appointmentTypeList = appointmentTypeList;
	}

	public List<EmploymentDTO> getEmployementTypeList() {
		return employementTypeList;
	}

	public void setEmployementTypeList(List<EmploymentDTO> employementTypeList) {
		this.employementTypeList = employementTypeList;
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

	public List<DivisionDTO> getDivisionList() {
		return divisionList;
	}

	public void setDivisionList(List<DivisionDTO> divisionList) {
		this.divisionList = divisionList;
	}

	public List<DispensaryNumberDTO> getDispensaryNumberList() {
		return dispensaryNumberList;
	}

	public void setDispensaryNumberList(List<DispensaryNumberDTO> dispensaryNumberList) {
		this.dispensaryNumberList = dispensaryNumberList;
	}

	public List<ReservationDTO> getReservationList() {
		return reservationList;
	}

	public void setReservationList(List<ReservationDTO> reservationList) {
		this.reservationList = reservationList;
	}

	public List<PHTypeDTO> getHandicapList() {
		return handicapList;
	}

	public void setHandicapList(List<PHTypeDTO> handicapList) {
		this.handicapList = handicapList;
	}

	public List<JoinTypeDTO> getJoinTypeList() {
		return joinTypeList;
	}

	public void setJoinTypeList(List<JoinTypeDTO> joinTypeList) {
		this.joinTypeList = joinTypeList;
	}

	public List<OrgInstanceDTO> getOfficeList() {
		return officeList;
	}

	public void setOfficeList(List<OrgInstanceDTO> officeList) {
		this.officeList = officeList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGeneratedId() {
		return generatedId;
	}

	public void setGeneratedId(String generatedId) {
		this.generatedId = generatedId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getBlood() {
		return blood;
	}

	public void setBlood(String blood) {
		this.blood = blood;
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

	public String getReservationId() {
		return reservationId;
	}

	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}

	public int getReservation() {
		return reservation;
	}

	public void setReservation(int reservation) {
		this.reservation = reservation;
	}

	public String getPpaNo() {
		return ppaNo;
	}

	public void setPpaNo(String ppaNo) {
		this.ppaNo = ppaNo;
	}

	public String getHandicapId() {
		return handicapId;
	}

	public void setHandicapId(String handicapId) {
		this.handicapId = handicapId;
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

	public int getJoinTypeId() {
		return joinTypeId;
	}

	public void setJoinTypeId(int joinTypeId) {
		this.joinTypeId = joinTypeId;
	}

	public String getWorkedIn() {
		return workedIn;
	}

	public void setWorkedIn(String workedIn) {
		this.workedIn = workedIn;
	}

	public String getWorkedYears() {
		return workedYears;
	}

	public void setWorkedYears(String workedYears) {
		this.workedYears = workedYears;
	}

	public String getFamPlanning() {
		return famPlanning;
	}

	public void setFamPlanning(String famPlanning) {
		this.famPlanning = famPlanning;
	}

	public String getHouseAllowence() {
		return houseAllowence;
	}

	public void setHouseAllowence(String houseAllowence) {
		this.houseAllowence = houseAllowence;
	}

	public String getGroupAllowence() {
		return groupAllowence;
	}

	public void setGroupAllowence(String groupAllowence) {
		this.groupAllowence = groupAllowence;
	}

	public String getUptoDate() {
		return uptoDate;
	}

	public void setUptoDate(String uptoDate) {
		this.uptoDate = uptoDate;
	}

	public String getUserSfid() {
		return userSfid;
	}

	public void setUserSfid(String userSfid) {
		this.userSfid = userSfid;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public String getReligionName() {
		return religionName;
	}

	public void setReligionName(String religionName) {
		this.religionName = religionName;
	}

	public String getMaritalName() {
		return maritalName;
	}

	public void setMaritalName(String maritalName) {
		this.maritalName = maritalName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public String getDirectorateName() {
		return directorateName;
	}

	public void setDirectorateName(String directorateName) {
		this.directorateName = directorateName;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public String getReservationName() {
		return reservationName;
	}

	public void setReservationName(String reservationName) {
		this.reservationName = reservationName;
	}

	public String getDispensaryName() {
		return dispensaryName;
	}

	public void setDispensaryName(String dispensaryName) {
		this.dispensaryName = dispensaryName;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public int getOffice() {
		return office;
	}

	public void setOffice(int office) {
		this.office = office;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public List getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List employeeList) {
		this.employeeList = employeeList;
	}

	public String getSearchWith() {
		return searchWith;
	}

	public void setSearchWith(String searchWith) {
		this.searchWith = searchWith;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public List<KeyValueDTO> getEmpInstanceList() {
		return empInstanceList;
	}

	public void setEmpInstanceList(List<KeyValueDTO> empInstanceList) {
		this.empInstanceList = empInstanceList;
	}

	public List<EmployeeBean> getEmpSearchList() {
		return empSearchList;
	}

	public void setEmpSearchList(List<EmployeeBean> empSearchList) {
		this.empSearchList = empSearchList;
	}

	public int getDispensaryNumberId() {
		return dispensaryNumberId;
	}

	public void setDispensaryNumberId(int dispensaryNumberId) {
		this.dispensaryNumberId = dispensaryNumberId;
	}

	public String getJoinType() {
		return joinType;
	}

	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}

	public String getNameInServiceBook() {
		return nameInServiceBook;
	}

	public void setNameInServiceBook(String nameInServiceBook) {
		this.nameInServiceBook = nameInServiceBook;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getHandicapName() {
		return handicapName;
	}

	public void setHandicapName(String handicapName) {
		this.handicapName = handicapName;
	}

	public String getJoinName() {
		return joinName;
	}

	public void setJoinName(String joinName) {
		this.joinName = joinName;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public int getNationalityId() {
		return nationalityId;
	}

	public void setNationalityId(int nationalityId) {
		this.nationalityId = nationalityId;
	}

	public List<NationalityDTO> getNationalityList() {
		return nationalityList;
	}

	public void setNationalityList(List<NationalityDTO> nationalityList) {
		this.nationalityList = nationalityList;
	}

	public String getNationalityName() {
		return nationalityName;
	}

	public void setNationalityName(String nationalityName) {
		this.nationalityName = nationalityName;
	}

	public List<BloodGroupDTO> getBloodList() {
		return bloodList;
	}

	public void setBloodList(List<BloodGroupDTO> bloodList) {
		this.bloodList = bloodList;
	}

	public String getBloodName() {
		return bloodName;
	}

	public void setBloodName(String bloodName) {
		this.bloodName = bloodName;
	}

	public int getBloodGroupId() {
		return bloodGroupId;
	}

	public void setBloodGroupId(int bloodGroupId) {
		this.bloodGroupId = bloodGroupId;
	}

	public String getHeadName() {
		return headName;
	}

	public void setHeadName(String headName) {
		this.headName = headName;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getChangedValues() {
		return changedValues;
	}

	public void setChangedValues(String changedValues) {
		this.changedValues = changedValues;
	}

	public String getSearchingWith() {
		return searchingWith;
	}

	public void setSearchingWith(String searchingWith) {
		this.searchingWith = searchingWith;
	}

	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	public String getEnteredValue() {
		return enteredValue;
	}

	public void setEnteredValue(String enteredValue) {
		this.enteredValue = enteredValue;
	}

	public ArrayList<KeyValueDTO> getInstanceList() {
		return instanceList;
	}

	public void setInstanceList(ArrayList<KeyValueDTO> instanceList) {
		this.instanceList = instanceList;
	}

	public ArrayList<List> getEmpTreeDetails() {
		return empTreeDetails;
	}

	public void setEmpTreeDetails(ArrayList<List> empTreeDetails) {
		this.empTreeDetails = empTreeDetails;
	}

	public String getRelationName() {
		return relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public String getSeniorityRank() {
		return seniorityRank;
	}

	public void setSeniorityRank(String seniorityRank) {
		this.seniorityRank = seniorityRank;
	}

	public String getRelationTitle() {
		return relationTitle;
	}

	public void setRelationTitle(String relationTitle) {
		this.relationTitle = relationTitle;
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

	public String getHeightType() {
		return heightType;
	}

	public void setHeightType(String heightType) {
		this.heightType = heightType;
	}

	public String getDataentryFlag() {
		return dataentryFlag;
	}

	public void setDataentryFlag(String dataentryFlag) {
		this.dataentryFlag = dataentryFlag;
	}

	public String getStoreHight() {
		return storeHight;
	}

	public void setStoreHight(String storeHight) {
		this.storeHight = storeHight;
	}

	public String getRelationIdName() {
		return relationIdName;
	}

	public void setRelationIdName(String relationIdName) {
		this.relationIdName = relationIdName;
	}

	public void setBasicPay(String basicPay) {
		this.basicPay = basicPay;
	}

	public String getBasicPay() {
		return basicPay;
	}

	public List<EmployeeBean> getEmpRoleslist() {
		return empRoleslist;
	}

	public void setEmpRoleslist(List<EmployeeBean> empRoleslist) {
		this.empRoleslist = empRoleslist;
	}

	public String getParentRole() {
		return parentRole;
	}

	public void setParentRole(String parentRole) {
		this.parentRole = parentRole;
	}

	public String getDefaultRole() {
		return defaultRole;
	}

	public void setDefaultRole(String defaultRole) {
		this.defaultRole = defaultRole;
	}

	public String getAdditionalRoles() {
		return additionalRoles;
	}

	public void setAdditionalRoles(String additionalRoles) {
		this.additionalRoles = additionalRoles;
	}

	public String getParentSfid() {
		return parentSfid;
	}

	public void setParentSfid(String parentSfid) {
		this.parentSfid = parentSfid;
	}
// cahnge password
	 public String getNewpassword() {
			return newpassword;
		}

		public void setNewpassword(String newpassword) {
			this.newpassword = newpassword;
		}

		public String getconfirmpassword() {
			return confirmpassword;
		}

		public void setconfirmpassword(String confirmpassword) {
			this.confirmpassword = confirmpassword;
		}

		

	    public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
		//

	public List<EmployeeBean> getEmpPaylist() {
		return empPaylist;
	}

	public void setEmpPaylist(List<EmployeeBean> empPaylist) {
		this.empPaylist = empPaylist;
	}

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

	public List<EmployeeBean> getEnabledEmployeeList() {
		return EnabledEmployeeList;
	}

	public void setEnabledEmployeeList(List<EmployeeBean> enabledEmployeeList) {
		EnabledEmployeeList = enabledEmployeeList;
	}

	
	public String getFromID() {
		return fromID;
	}

	public void setFromID(String fromID) {
		this.fromID = fromID;
	}

	
	


	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}


	public String getRequestID() {
		return requestID;
	}


	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}


	public String getRequestId() {
		return requestId;
	}


	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

}