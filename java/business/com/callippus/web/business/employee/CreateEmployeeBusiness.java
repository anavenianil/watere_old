package com.callippus.web.business.employee;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.ApplicationRoleMappingDTO;
import com.callippus.web.beans.dto.AppointmentDTO;
import com.callippus.web.beans.dto.BloodGroupDTO;
import com.callippus.web.beans.dto.CommunityDTO;
import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.DispensaryNumberDTO;
import com.callippus.web.beans.dto.EmpRoleMappingDTO;
import com.callippus.web.beans.dto.EmploymentDTO;
import com.callippus.web.beans.dto.JoinTypeDTO;
import com.callippus.web.beans.dto.MaritalDTO;
import com.callippus.web.beans.dto.NationalityDTO;
import com.callippus.web.beans.dto.OrganizationsDTO;
import com.callippus.web.beans.dto.PHTypeDTO;
import com.callippus.web.beans.dto.ReligionDTO;
import com.callippus.web.beans.dto.ReservationDTO;
import com.callippus.web.beans.dto.TitleDTO;
import com.callippus.web.beans.dto.UserDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.family.FamilyBean;
import com.callippus.web.beans.tutionFee.TutionFeeClaimMasterDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.employee.ICreateEmployeeDAO;

@Service
public class CreateEmployeeBusiness {
	
	private static Log log = LogFactory.getLog(CreateEmployeeBusiness.class);
	@Autowired
	private ICreateEmployeeDAO createEmployeeDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;

	@SuppressWarnings("unchecked")
	public EmployeeBean getEmployeeGeneralDetails(HttpSession session, EmployeeBean employee) throws Exception {
		try {
			log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>getEmployeeGeneralDetails(-,-) Start>>>>>>>>>>>>>>");
			// if (CPSUtils.isNull(session.getAttribute(CPSConstants.HOMELISTS))) {
			// first time
			List<TitleDTO> titleList = commonDataDAO.getMasterData(CPSConstants.TITLEDTO);
			employee.setTitleList(titleList);
			session.setAttribute(CPSConstants.TITLELIST, employee.getTitleList());
            
			//Organization list retriveing
			List<OrganizationsDTO> orglist = commonDataDAO.getOrganizations(employee);
			employee.setOrgList(orglist);
			session.setAttribute(CPSConstants.ORGANIZATIONLIST, employee.getOrgList());
			//
			List<MaritalDTO> maritalList = commonDataDAO.getMasterData(CPSConstants.MARITALDTO);
			employee.setMaritalList(maritalList);
			session.setAttribute(CPSConstants.MARITALLIST, employee.getMaritalList());

			List<ReligionDTO> religionList = commonDataDAO.getMasterData(CPSConstants.RELIGIONDTO);
			employee.setReligionList(religionList);
			session.setAttribute(CPSConstants.RELIGIONLIST, employee.getReligionList());

			List<CommunityDTO> communityList = commonDataDAO.getMasterData(CPSConstants.COMMUNITYDTO);
			employee.setCommunityList(communityList);
			session.setAttribute(CPSConstants.COMMUNITYLIST, employee.getCommunityList());

			List<BloodGroupDTO> bloodList = commonDataDAO.getMasterData(CPSConstants.BLOODGROUPDTO);
			employee.setBloodList(bloodList);
			session.setAttribute(CPSConstants.BLOODLIST, employee.getBloodList());

			List<NationalityDTO> nationalityList = commonDataDAO.getMasterData(CPSConstants.NATIONALITYDTO);
			employee.setNationalityList(nationalityList);
			session.setAttribute(CPSConstants.NATIONALITYLIST, employee.getNationalityList());

			session.setAttribute(CPSConstants.HOMELISTS, employee);
			// }
			employee.setCheckDate(CPSUtils.getCurrentDate());
			Calendar now = Calendar.getInstance();
			now.add(Calendar.YEAR, -Integer.parseInt(commonDataDAO.getConfigurationValue("dob")));
			String date1 = now.get(Calendar.DATE) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.YEAR);
			SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			Date date = (Date) df.parse(date1);
			employee.setCheckDate(df1.format(date));
			session.setAttribute("checkDate", employee.getCheckDate());

			
			//new userid generated
			employee.setUserSfid(String.valueOf(commonDataDAO.getTableID(CPSConstants.USERS, CPSConstants.NEW)));
			employee.setGeneratedId(employee.getUserSfid());
			
			
			employee.setUserSfid(setEmployeeId(employee.getUserSfid(),commonDataDAO.getTableID("LAB_ID", CPSConstants.NEW)));

		} catch (Exception e) {
			throw e;
		}
		log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>getEmployeeGeneralDetails(-,-) End>>>>>>>>>>>>>>");
		return employee;

	}

	@SuppressWarnings("unchecked")
	public EmployeeBean getProfessionalDetails(HttpSession session, EmployeeBean employee) throws Exception {
		try {
			log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>getProfessionalDetails(-,-) Start>>>>>>>>>>>>>>");
			// second page
			session.setAttribute(CPSConstants.HOMEDATA, employee);
			if (!CPSUtils.isNull(session.getAttribute(CPSConstants.PROFESSIONALDATA))) {
				employee = (EmployeeBean) session.getAttribute(CPSConstants.PROFESSIONALDATA);
			}

			// if (CPSUtils.isNull(session.getAttribute(CPSConstants.PROFESSIONALLISTS))) {
			// first time
			List<AppointmentDTO> appointmentTypeList = commonDataDAO.getMasterData(CPSConstants.APPOINTMENTDTO);
			employee.setAppointmentTypeList(appointmentTypeList);
			session.setAttribute(CPSConstants.APPLOINTMENTYPELIST, employee.getAppointmentTypeList());

			List<EmploymentDTO> employementTypeList = commonDataDAO.getMasterData(CPSConstants.EMPLOYMENTDTO);
			employee.setEmployementTypeList(employementTypeList);
			session.setAttribute(CPSConstants.EMPLOYMENTYPELIST, employee.getEmployementTypeList());

			List<DesignationDTO> designationList = commonDataDAO.getMasterData(CPSConstants.DESIGNATIONDTO);
			employee.setDesignationList(designationList);
			session.setAttribute(CPSConstants.DESIGNATIONLIST, employee.getDesignationList());

			List<PHTypeDTO> handicapList = commonDataDAO.getMasterData(CPSConstants.PHTYPEDTO);
			employee.setHandicapList(handicapList);
			session.setAttribute("handicapList", employee.getHandicapList());

			List<JoinTypeDTO> joinTypeList = commonDataDAO.getMasterData(CPSConstants.JOINTYPEDTO);
			employee.setJoinTypeList(joinTypeList);
			session.setAttribute("joinTypeList", employee.getJoinTypeList());

			List<ReservationDTO> reservationList = commonDataDAO.getMasterData(CPSConstants.RESERVATIONDTO);
			employee.setReservationList(reservationList);
			session.setAttribute("reservationList", employee.getReservationList());

			List directorateList = commonDataDAO.getDirectorateList();
			employee.setDirectorateList(directorateList);
			session.setAttribute(CPSConstants.DIRECTORATELIST, employee.getDirectorateList());

			session.setAttribute(CPSConstants.PROFESSIONALLISTS, employee);
			// }
			EmployeeBean employee1 = (EmployeeBean) session.getAttribute(CPSConstants.HOMEDATA);

			employee.setDob(employee1.getDob());
			if (employee.getDirectorateId() != null && !CPSUtils.compareStrings(employee.getDirectorateId(), "Select")) {
				employee.setDivisionList(createEmployeeDAO.selectedDivisionList(employee.getDirectorateId()));
				session.setAttribute(CPSConstants.DIVISIONLIST, employee.getDivisionList());
			}

		} catch (Exception e) {
			throw e;
		}
		log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>getProfessionalDetails(-,-) End>>>>>>>>>>>>>>");
		return employee;
	}

	@SuppressWarnings("unchecked")
	public EmployeeBean getOtherDetails(HttpSession session, EmployeeBean employee) throws Exception {
		try {
			log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>getOtherDetails(-,-) Start>>>>>>>>>>>>>>");
			// third page
			session.setAttribute(CPSConstants.PROFESSIONALDATA, employee);// Get second page data
			if (!CPSUtils.isNull(session.getAttribute(CPSConstants.OTHERDATA))) {
				employee = (EmployeeBean) session.getAttribute(CPSConstants.OTHERDATA);
			}
			// if (CPSUtils.isNull(session.getAttribute(CPSConstants.OTHERLISTS))) {
			List<DispensaryNumberDTO> dispensaryList = commonDataDAO.getMasterData(CPSConstants.DISPENSARYNUMBERDTO);
			employee.setDispensaryNumberList(dispensaryList);
			session.setAttribute(CPSConstants.DISPENSARYLIST, employee.getDispensaryNumberList());
			session.setAttribute(CPSConstants.OTHERLISTS, employee);
			// }
		} catch (Exception e) {
			throw e;
		}
		log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>getOtherDetails(-,-) End>>>>>>>>>>>>>>");
		return employee;
	}

	public EmployeeBean showPreview(HttpSession session, EmployeeBean employee) throws Exception {
		try {
			log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>showPreview(-,-) Start>>>>>>>>>>>>>>");
			session.setAttribute(CPSConstants.OTHERDATA, employee);
			EmployeeBean employee2 = (EmployeeBean) session.getAttribute(CPSConstants.PROFESSIONALDATA);
			EmployeeBean employee1 = (EmployeeBean) session.getAttribute(CPSConstants.HOMEDATA);
			if (!CPSUtils.isNullOrEmpty(employee1)) {
				employee.setSfid(employee1.getUserSfid());
				employee.setTitle(employee1.getTitle());
				employee.setTitleName(employee1.getTitleName());
				employee.setFirstName(employee1.getFirstName());
				employee.setMiddleName(employee1.getMiddleName());
				employee.setLastName(employee1.getLastName());
				employee.setRelationId(employee1.getRelationId());
				employee.setRelationName(employee1.getRelationName());
				employee.setDob(employee1.getDob());
				employee.setGender(employee1.getGender());
				employee.setMaritalId(employee1.getMaritalId());
				employee.setMaritalName(employee1.getMaritalName());
				employee.setReligion(employee1.getReligion());
				employee.setReligionName(employee1.getReligionName());
				employee.setCommunityId(employee1.getCommunityId());
				employee.setCommunityName(employee1.getCommunityName());
				employee.setNationalityId(employee1.getNationalityId());
				employee.setNationalityName(employee1.getNationalityName());
				employee.setBlood(employee1.getBlood());
				employee.setBloodName(employee1.getBloodName());
				employee.setPersonalNumber(employee1.getPersonalNumber());
				employee.setResidenceNo(employee1.getResidenceNo());
				employee.setInternalNo(employee1.getInternalNo());
				employee.setMotherTongue(employee1.getMotherTongue());
				if (!CPSUtils.isNullOrEmpty(employee1.getStoreHight()))
					employee.setHeight(employee1.getStoreHight());
				else
					employee.setHeight(employee1.getHeight());
				employee.setIdMarks(employee1.getIdMarks());
				employee.setNameInServiceBook(employee1.getNameInServiceBook());
			}

			if (!CPSUtils.isNullOrEmpty(employee2)) {
				employee.setAppointmentTypeId(employee2.getAppointmentTypeId());
				employee.setAppName(employee2.getAppName());
				employee.setEmploymentTypeId(employee2.getEmploymentTypeId());
				employee.setEmpName(employee2.getEmpName());
				employee.setDivisionId(employee2.getDivisionId());
				employee.setDivisionName(employee2.getDivisionName());
				employee.setDesignationId(employee2.getDesignationId());
				employee.setDesignationName(employee2.getDesignationName());
				employee.setDirectorateId(employee2.getDirectorateId());
				employee.setDirectorateName(employee2.getDirectorateName());
				employee.setReservationId(employee2.getReservationId());
				employee.setReservationName(employee2.getReservationName());
				employee.setOfficeId(employee2.getOfficeId());
				employee.setHandicapId(employee2.getHandicapId());
				employee.setHandicapName(employee2.getHandicapName());
				employee.setJoinType(employee2.getJoinType());
				employee.setJoinName(employee2.getJoinName());
				employee.setWorkedYears(employee2.getWorkedYears());
				employee.setFamPlanning(employee2.getFamPlanning());
				employee.setHouseAllowence(employee2.getHouseAllowence());
				employee.setGroupAllowence(employee2.getGroupAllowence());
				employee.setUptoDate(employee2.getUptoDate());
				employee.setDojAsl(employee2.getDojAsl());
				employee.setDojDrdo(employee2.getDojDrdo());
				employee.setDojGovt(employee2.getDojGovt());
				employee.setSeniorityDate(employee2.getSeniorityDate());
				employee.setLastPromotion(employee2.getLastPromotion());
			}
		} catch (Exception e) {
			throw e;
		}
		log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>showPreview(-,-) End>>>>>>>>>>>>>>");
		return employee;
	}

	public String submitEmployeeDetails(HttpSession session, EmployeeBean employee) throws Exception {
		String message = "failure";
		try {
			log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>submitEmployeeDetails(-,-) Start>>>>>>>>>>>>>>");
			EmployeeBean homeData = (EmployeeBean) session.getAttribute(CPSConstants.HOMEDATA);
			EmployeeBean professionalData = (EmployeeBean) session.getAttribute(CPSConstants.PROFESSIONALDATA);
			EmployeeBean otherData = (EmployeeBean) session.getAttribute(CPSConstants.OTHERDATA);
			SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
			Date date = new Date();
			// general details
			employee.setUserSfid(homeData.getUserSfid().toUpperCase());
			employee.setCreationDate(CPSUtils.getCurrentDate());
			employee.setLastModifiedDate(CPSUtils.getCurrentDate());
			employee.setCreatedBy(session.getAttribute(CPSConstants.SFID).toString());
			employee.setLastModifiedBy(session.getAttribute(CPSConstants.SFID).toString());
			employee.setDataentryFlag("1");
			// employee.setWorkingLocation("1"); 
			// set working location of logged SFID //KUMARI
			employee.setWorkingLocation(String.valueOf(commonDataDAO.getWorkingLocation(session.getAttribute(CPSConstants.SFID).toString())));
			if (homeData.getTitle() != null && !homeData.getTitle().equals(CPSConstants.SELECT)) {
				employee.setTitleId(Integer.parseInt(homeData.getTitle()));
			}
			employee.setFirstName(homeData.getFirstName());
			employee.setMiddleName(homeData.getMiddleName());
			employee.setLastName(homeData.getLastName());
			employee.setGender(homeData.getGender());
			employee.setRelationId(homeData.getRelationId());
			employee.setRelationName(homeData.getRelationName());
			employee.setPersonalNumber(homeData.getPersonalNumber());
			if (!CPSUtils.isNullOrEmpty(homeData.getDob())) {
				date = (Date) df.parse(homeData.getDob());
				employee.setDob(df.format(date));
			}
			if (homeData.getMaritalId() != null && !homeData.getMaritalId().equals(CPSConstants.SELECT)) {
				employee.setMarital(Integer.parseInt(homeData.getMaritalId()));
			}
			if (homeData.getReligion() != null && !homeData.getReligion().equals(CPSConstants.SELECT)) {
				employee.setReligionId(Integer.parseInt(homeData.getReligion()));
			}
			if (homeData.getCommunityId() != null && !homeData.getCommunityId().equals(CPSConstants.SELECT)) {
				employee.setCommunity(Integer.parseInt(homeData.getCommunityId()));
			}

			if (homeData.getNationality() != null && !homeData.getNationality().equals(CPSConstants.SELECT)) {
				employee.setNationalityId(Integer.parseInt(homeData.getNationality()));
			}
			if (homeData.getBlood() != null && !homeData.getBlood().equals(CPSConstants.SELECT)) {
				employee.setBloodGroupId(Integer.parseInt(homeData.getBlood()));
			}
			employee.setResidenceNo(homeData.getResidenceNo());
			employee.setInternalNo(homeData.getInternalNo());
			employee.setMotherTongue(homeData.getMotherTongue());
			if (!CPSUtils.isNullOrEmpty(homeData.getStoreHight())) {
				employee.setHeight(homeData.getStoreHight());
			} else {
				employee.setHeight(homeData.getHeight());
			}
			employee.setIdMarks(homeData.getIdMarks());
			employee.setRelationTitle(homeData.getRelationTitle());

			// professional details
			if (professionalData.getAppointmentTypeId() != null && !professionalData.getAppointmentTypeId().equals(CPSConstants.SELECT)) {
				employee.setAppointmentId(Integer.parseInt(professionalData.getAppointmentTypeId()));
			}
			if (professionalData.getEmploymentTypeId() != null && !professionalData.getEmploymentTypeId().equals(CPSConstants.SELECT)) {
				employee.setEmploymentId(Integer.parseInt(professionalData.getEmploymentTypeId()));
			}
			if (professionalData.getDesignationId() != null && !professionalData.getDesignationId().equals(CPSConstants.SELECT)) {
				employee.setDesignation(Integer.parseInt(professionalData.getDesignationId()));
			}
			if (professionalData.getDirectorateId() != null && !professionalData.getDirectorateId().equals(CPSConstants.SELECT)) {
				employee.setDirectorate(Integer.parseInt(professionalData.getDirectorateId()));
				employee.setOffice(Integer.parseInt(professionalData.getDirectorateId()));
			}
			if (professionalData.getDivisionId() != null && !professionalData.getDivisionId().equals(CPSConstants.SELECT)) {
				employee.setDirectorate(Integer.parseInt(professionalData.getDivisionId()));
			}
			if (professionalData.getReservationId() != null && !professionalData.getReservationId().equals(CPSConstants.SELECT)) {
				employee.setReservation(Integer.parseInt(professionalData.getReservationId()));
			}
			if (professionalData.getHandicapId() != null && !professionalData.getHandicapId().equals(CPSConstants.SELECT)) {
				employee.setHandicap(Integer.parseInt(professionalData.getHandicapId()));
			}
			if (professionalData.getJoinType() != null && !professionalData.getJoinType().equals(CPSConstants.SELECT)) {
				/**
				 * Getting OfficeId for Selected Division
				 */
				employee.setOffice(Integer.parseInt(createEmployeeDAO.getOfficeId(String.valueOf(employee.getOffice()))));
				employee.setJoinTypeId(Integer.parseInt(professionalData.getJoinType()));
			}
			employee.setWorkedYears(professionalData.getWorkedYears());
			employee.setFamPlanning(professionalData.getFamPlanning());
			employee.setHouseAllowence(professionalData.getHouseAllowence());
			employee.setGroupAllowence(professionalData.getGroupAllowence());
			employee.setUptoDate(professionalData.getUptoDate());
			if (!CPSUtils.isNullOrEmpty(professionalData.getDojGovt())) {
				date = (Date) df.parse(professionalData.getDojGovt());
				employee.setDojGovt(df.format(date));
			}
			if (!CPSUtils.isNullOrEmpty(professionalData.getDojDrdo())) {
				date = (Date) df.parse(professionalData.getDojDrdo());
				employee.setDojDrdo(df.format(date));
			}
			if (!CPSUtils.isNullOrEmpty(professionalData.getDojAsl())) {
				date = (Date) df.parse(professionalData.getDojAsl());
				employee.setDojAsl(df.format(date));
			}
			// employee.setPresentRank(Integer.parseInt(professionalData
			// .getPresentRankId()));
			if (!CPSUtils.isNullOrEmpty(professionalData.getSeniorityDate())) {
				date = (Date) df.parse(professionalData.getSeniorityDate());
				employee.setSeniorityDate(df.format(date));
			}
			if (!CPSUtils.isNullOrEmpty(professionalData.getLastPromotion())) {
				date = (Date) df.parse(professionalData.getLastPromotion());
				employee.setLastPromotion(df.format(date));
			}

			// Other details
			if (otherData.getDispersaryNumber() != null && !otherData.getDispersaryNumber().equals(CPSConstants.SELECT)) {
				employee.setDispensaryNumberId(Integer.parseInt(otherData.getDispersaryNumber()));
			}
			employee.setCgshNumber(otherData.getCgshNumber());
			employee.setPpaNo(otherData.getPpaNo());
			employee.setPranNo(otherData.getPranNo());
			employee.setGpfAcNo(otherData.getGpfAcNo());
			employee.setNameInServiceBook(homeData.getNameInServiceBook());

			UserDTO user = new UserDTO();
			user.setSfid(homeData.getUserSfid().trim());
			user.setPassword(homeData.getUserSfid().trim());
			user.setUserName(homeData.getUserSfid().trim());
			user.setStatus(1);

			FamilyBean family = new FamilyBean();
			family.setId(String.valueOf(commonDataDAO.getTableID(CPSConstants.FAMILY_DETAILS, CPSConstants.NEW)));
			family.setSfid(homeData.getUserSfid());
			family.setName(homeData.getNameInServiceBook());
			family.setRelationId("16");
			family.setGender(homeData.getGender());
			family.setDob(homeData.getDob());
			if (CPSUtils.isNull(professionalData.getHandicapId()) || CPSUtils.compareStrings(professionalData.getHandicapId(), CPSConstants.SELECT)) {
				family.setDisabilityId("0");
			} else {
				family.setDisabilityId(professionalData.getHandicapId());
			}
			if (CPSUtils.compareStrings(homeData.getBlood(), CPSConstants.SELECT)) {
				family.setBloodGroup("0");
			} else {
				family.setBloodGroup(homeData.getBlood());
			}
			// family.setBloodGroup(homeData.getBlood());
			family.setResidingWith(CPSConstants.Y);
			family.setEmployeedType(CPSConstants.GOVTJOBTYPEID);
			family.setEmployeed(CPSConstants.Y);
			family.setMaritalstatus(homeData.getMaritalId());
			if (professionalData.getReservationId() == "1") {// && professionalData.getHandicapId().equals(CPSConstants.SELECT)
				family.setPhtypeFlag(CPSConstants.Y);
			} else {
				family.setPhtypeFlag(CPSConstants.N);
			}
			family.setState("0");
			family.setDistrict("0");
			family.setAddressTypeId("2");
			family.setCghsFacility(CPSConstants.Y);
			family.setLtcFacility(CPSConstants.Y);
			family.setDependent(CPSConstants.Y);
			family.setStatus(1);
			family.setCreationDate(CPSUtils.getCurrentDate());
			family.setMaritalstatusId(homeData.getMaritalId());
			family.setModifiedDate(CPSUtils.getCurrentDate());

			String parentId = createEmployeeDAO.checkParentSFID(employee.getOffice());
			EmpRoleMappingDTO emprolemapping = new EmpRoleMappingDTO();

			emprolemapping.setCreationDate(CPSUtils.getCurrentDate());
			emprolemapping.setInternalDivision(null);
			emprolemapping.setLastModifiedDate(CPSUtils.getCurrentDate());
			emprolemapping.setSfid(employee.getUserSfid());
			emprolemapping.setStatus(1);
			employee.setStatus(1);
			ApplicationRoleMappingDTO appRoleMap = new ApplicationRoleMappingDTO();
			appRoleMap.setSfid(homeData.getUserSfid().trim());
			appRoleMap.setStatus(1);
			appRoleMap.setApplicationRoleId(4);
			appRoleMap.setCreationDate(CPSUtils.getCurrentDate());
			appRoleMap.setLastModifiedDate(CPSUtils.getCurrentDate());

			message = createEmployeeDAO.createEmployee(employee);// Insert data into emp_master table
			if (CPSUtils.compareStrings(CPSConstants.SUCCESS, message)) {
				message = createEmployeeDAO.saveApplicationRoleMapping(appRoleMap);// Insert data into applicationn_role_mapping table
			}
			if (CPSUtils.compareStrings(CPSConstants.SUCCESS, message)) {
				message = createEmployeeDAO.selfFamilyDetails(family);// Insert data into family_details table
			}
			if (CPSUtils.compareStrings(CPSConstants.SUCCESS, message)) {
				message = createEmployeeDAO.createUser(user);// Insert data into users table
			}
			if (CPSUtils.compareStrings(CPSConstants.SUCCESS, message)) {
				if (CPSUtils.isNullOrEmpty(parentId)) {
					emprolemapping.setInternalRole("Head");
					emprolemapping.setRoleInstanceId(employee.getOffice());
					emprolemapping.setParentId(null);
					emprolemapping.setParentRoleID(employee.getOffice());
					// emprolemapping.setId((commonDataDAO.getTableID(CPSConstants.EMP_ROLE_MAPPING, CPSConstants.NEW)));
					message = createEmployeeDAO.saveEmpRoleMapping(emprolemapping);
					// commonDataDAO.updateTableID(CPSConstants.EMP_ROLE_MAPPING, String.valueOf(emprolemapping.getId()));
				} else {
					emprolemapping.setParentId(parentId);
					emprolemapping.setInternalRole("Employee");
					emprolemapping.setParentRoleID(employee.getOffice());
					// emprolemapping.setId((commonDataDAO.getTableID(CPSConstants.EMP_ROLE_MAPPING, CPSConstants.NEW)));
					message = createEmployeeDAO.saveEmpRoleMapping(emprolemapping);// Insert data into emp_role_mapping table
					// commonDataDAO.updateTableID(CPSConstants.EMP_ROLE_MAPPING, String.valueOf(emprolemapping.getId()));
				}
			}
			if (CPSUtils.compareStrings(CPSConstants.SUCCESS, message)) {
				commonDataDAO.updateTableID(CPSConstants.USERS, session.getAttribute(CPSConstants.GENERATEDID).toString());
				commonDataDAO.updateTableID(CPSConstants.FAMILY_DETAILS, String.valueOf(family.getId()));
				session.removeAttribute(CPSConstants.HOMEDATA);
				session.removeAttribute(CPSConstants.PROFESSIONALDATA);
				session.removeAttribute(CPSConstants.OTHERDATA);
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>submitEmployeeDetails(-,-) End>>>>>>>>>>>>>>");
		return message;
	}
	//change password
	public String changePassword(EmployeeBean employee) throws Exception {
		//UserDTO user = new UserDTO();
		//user.setSfid(homeData.getUserSfid().trim());
		//user.setPassword(homeData.getUserSfid().trim());
		String message = null;
		try {
		 message = commonDataDAO.chageUserPassword(employee);
		} catch(Exception e) {
			throw e;
		}
		return message;
	}
	
	//method for  calling  proceure for adding other organization
	public String addOtherEmployeeDetails(EmployeeBean employee) throws Exception {
		String message1 = null;
		try {
			message1 = createEmployeeDAO.addOtherEmployee(employee);
		} catch(Exception e) {
			throw e;
		}
		return message1;
	}

	public EmployeeBean setProfessionalLists(HttpSession session, EmployeeBean employee) throws Exception {
		try {
			log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>setProfessionalLists(-,-) Start>>>>>>>>>>>>>>");
			EmployeeBean professionalsList = (EmployeeBean) session.getAttribute(CPSConstants.PROFESSIONALLISTS);
			employee.setAppointmentTypeList(professionalsList.getAppointmentTypeList());
			employee.setEmployementTypeList(professionalsList.getEmployementTypeList());
			employee.setDesignationList(professionalsList.getDesignationList());
			employee.setDirectorateList(professionalsList.getDirectorateList());
			employee.setDivisionList(professionalsList.getDivisionList());
		} catch (Exception e) {
			throw e;
		}
		log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>setProfessionalLists(-,-) End>>>>>>>>>>>>>>");
		return employee;
	}

	public EmployeeBean backToHome(HttpSession session, EmployeeBean employee) throws Exception {
		try {
			log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>backToHome(-,-) Start>>>>>>>>>>>>>>");
			// first page
			session.setAttribute(CPSConstants.PROFESSIONALDATA, employee);// Get the second page data
			employee = (EmployeeBean) session.getAttribute(CPSConstants.HOMEDATA);// Get the first page data
			employee = setHomeLists(session, employee);
			// lists
			employee.setCheckDate(session.getAttribute("checkDate").toString());
		} catch (Exception e) {
			throw e;
		}
		log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>backToHome(-,-) End>>>>>>>>>>>>>>");
		return employee;
	}

	@SuppressWarnings("unchecked")
	public EmployeeBean backToProfessional(HttpSession session, EmployeeBean employee) throws Exception {
		try {
			log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>backToProfessional(-,-) Satrt>>>>>>>>>>>>>>");
			session.setAttribute(CPSConstants.OTHERDATA, employee);
			employee = (EmployeeBean) session.getAttribute(CPSConstants.PROFESSIONALDATA);
			employee = setProfessionalLists(session, employee);
			EmployeeBean employee1 = (EmployeeBean) session.getAttribute(CPSConstants.HOMEDATA);
			employee.setDob(employee1.getDob());
			employee.setDivisionList(createEmployeeDAO.selectedDivisionList(employee.getDirectorateId()));
			session.setAttribute(CPSConstants.DIVISIONLIST, employee.getDivisionList());
		} catch (Exception e) {
			throw e;
		}
		log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>backToProfessional(-,-) End>>>>>>>>>>>>>>");
		return employee;
	}

	public EmployeeBean backToOther(HttpSession session, EmployeeBean employee) throws Exception {
		try {
			log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>backToOther(-,-) Start>>>>>>>>>>>>>>");
			employee = (EmployeeBean) session.getAttribute(CPSConstants.OTHERDATA);
			EmployeeBean employee1 = (EmployeeBean) session.getAttribute(CPSConstants.OTHERLISTS);
			employee.setDispensaryNumberList(employee1.getDispensaryNumberList());

		} catch (Exception e) {
			throw e;
		}
		log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>backToOther(-,-) End>>>>>>>>>>>>>>");
		return employee;
	}

	public EmployeeBean setHomeLists(HttpSession session, EmployeeBean employee) throws Exception {
		try {
			log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>setHomeLists(-,-) Start>>>>>>>>>>>>>>");
			EmployeeBean homeList = (EmployeeBean) session.getAttribute(CPSConstants.HOMELISTS);
			employee.setTitleList(homeList.getTitleList());
			employee.setMaritalList(homeList.getMaritalList());
			employee.setReligionList(homeList.getReligionList());
			employee.setCommunityList(homeList.getCommunityList());
		} catch (Exception e) {
			throw e;
		}
		log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>setHomeLists(-,-) End>>>>>>>>>>>>>>");
		return employee;
	}

	@SuppressWarnings("unchecked")
	public EmployeeBean getAllEmployeeList(HttpSession session, EmployeeBean employee) throws Exception {
		try {
			log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>getAllEmployeeList(-,-) Start>>>>>>>>>>>>>>");
			if (CPSUtils.isNull(session.getAttribute("EmployeeAllList"))) {

				List<TitleDTO> titleList = commonDataDAO.getMasterData(CPSConstants.TITLEDTO);
				employee.setTitleList(titleList);
				session.setAttribute(CPSConstants.TITLELIST, employee.getTitleList());

				List<MaritalDTO> maritalList = commonDataDAO.getMasterData(CPSConstants.MARITALDTO);
				employee.setMaritalList(maritalList);
				session.setAttribute(CPSConstants.MARITALLIST, employee.getMaritalList());

				List<ReligionDTO> religionList = commonDataDAO.getMasterData(CPSConstants.RELIGIONDTO);
				employee.setReligionList(religionList);
				session.setAttribute(CPSConstants.RELIGIONLIST, employee.getReligionList());

				List<CommunityDTO> communityList = commonDataDAO.getMasterData(CPSConstants.COMMUNITYDTO);
				employee.setCommunityList(communityList);
				session.setAttribute(CPSConstants.COMMUNITYLIST, employee.getCommunityList());

				List<NationalityDTO> nationalityList = commonDataDAO.getMasterData(CPSConstants.NATIONALITYDTO);
				employee.setNationalityList(nationalityList);
				session.setAttribute(CPSConstants.NATIONALITYLIST, employee.getNationalityList());

				List<BloodGroupDTO> bloodList = commonDataDAO.getMasterData(CPSConstants.BLOODGROUPDTO);
				employee.setBloodList(bloodList);
				session.setAttribute(CPSConstants.BLOODLIST, employee.getBloodList());

				// List<CommunityDTO> reservationList =
				// commonDataDAO.getMasterData(CPSConstants.COMMUNITYDTO);
				// employee.setCommunityList(communityList);

				List<AppointmentDTO> appointmentTypeList = commonDataDAO.getMasterData(CPSConstants.APPOINTMENTDTO);
				employee.setAppointmentTypeList(appointmentTypeList);
				session.setAttribute(CPSConstants.APPLOINTMENTYPELIST, employee.getAppointmentTypeList());

				List<EmploymentDTO> employementTypeList = commonDataDAO.getMasterData(CPSConstants.EMPLOYMENTDTO);
				employee.setEmployementTypeList(employementTypeList);
				session.setAttribute(CPSConstants.EMPLOYMENTYPELIST, employee.getEmployementTypeList());

				List<DesignationDTO> designationList = commonDataDAO.getMasterData(CPSConstants.DESIGNATIONDTO);
				employee.setDesignationList(designationList);
				session.setAttribute(CPSConstants.DESIGNATIONLIST, employee.getDesignationList());

				List<PHTypeDTO> handicapList = commonDataDAO.getMasterData(CPSConstants.PHTYPEDTO);
				employee.setHandicapList(handicapList);
				session.setAttribute("handicapList", employee.getHandicapList());

				List<JoinTypeDTO> joinTypeList = commonDataDAO.getMasterData(CPSConstants.JOINTYPEDTO);
				employee.setJoinTypeList(joinTypeList);
				session.setAttribute("joinTypeList", employee.getJoinTypeList());

				List<ReservationDTO> reservationList = commonDataDAO.getMasterData(CPSConstants.RESERVATIONDTO);
				employee.setReservationList(reservationList);
				session.setAttribute("reservationList", employee.getReservationList());

				List directorateList = commonDataDAO.getDirectorateList();
				employee.setDirectorateList(directorateList);
				session.setAttribute(CPSConstants.DIRECTORATELIST, employee.getDirectorateList());

				List<DispensaryNumberDTO> dispensaryList = commonDataDAO.getMasterData(CPSConstants.DISPENSARYNUMBERDTO);
				employee.setDispensaryNumberList(dispensaryList);
				session.setAttribute(CPSConstants.DISPENSARYLIST, employee.getDispensaryNumberList());

				session.setAttribute("EmployeeAllList", employee);
			}

			/**
			 * To check date of birth validation when Edit Employee Link is clicked
			 */
			employee.setCheckDate(CPSUtils.getCurrentDate());
			Calendar now = Calendar.getInstance();
			now.add(Calendar.YEAR, -Integer.parseInt(commonDataDAO.getConfigurationValue("dob")));
			String date1 = now.get(Calendar.DATE) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.YEAR);
			SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			Date date = (Date) df.parse(date1);
			employee.setCheckDate(df1.format(date));
			session.setAttribute("checkDate", employee.getCheckDate());

		} catch (Exception e) {

			throw e;
		}
		log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>getAllEmployeeList(-,-) End>>>>>>>>>>>>>>");
		return employee;
	}

	@SuppressWarnings("unchecked")
	public EmployeeBean viewEmployeeDetails(EmployeeBean employee, HttpSession session) throws Exception {
		EmployeeBean employee1 = null;
		try {
			log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>viewEmployeeDetails(-,-) Start>>>>>>>>>>>>>>");
			
			employee.setLoginSfid((String)session.getAttribute("sfid"));
			employee = createEmployeeDAO.viewEmployeeDetails(employee);
			if (!CPSUtils.compareStrings(employee.getMessage(), CPSConstants.INVALID)) {
				session.setAttribute(CPSConstants.CHANGESFID, employee.getSfid());
				session.setAttribute(CPSConstants.CHANGESFIDNAME, employee.getNameInServiceBook());
				employee = getAllEmployeeList(session, employee);
				employee1 = new EmployeeBean();
				employee1.setDivisionList(createEmployeeDAO.selectedDivisionList(employee.getDirectorateId()));
				session.setAttribute(CPSConstants.DIVISIONLIST, employee1.getDivisionList());
				employee.setDivisionList(employee1.getDivisionList());
			}
		} catch (Exception e) {
			throw e;
		}
		log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>viewEmployeeDetails(-,-) End>>>>>>>>>>>>>>");
		return employee;
	}

	public String setEmployeeId(String userSfid,int labId) throws Exception {
		log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>setEmployeeId(-) Start>>>>>>>>>>>>>>");
		int str = Integer.parseInt(userSfid);
		//int dbId=Integer.parseInt(dbid);
		if(labId==2){
		if (str >= 1 && str <= 9) {
			userSfid = "SF000" + str;
		} else if (str >= 10 && str <= 99) {
			userSfid = "SF00" + str;
		} else if (str >= 100 && str <= 999) {
			userSfid = "SF0" + str;
		} else if (str >= 1000 && str <= 9999) {
			userSfid = "SF" + str;
		}
		}
		else if(labId==6)
		{

			if (str >= 1 && str <= 9) {
				userSfid = "DG00" + str;
			} else if (str >= 10 && str <= 99) {
				userSfid = "DG0" + str;
			} else if (str >= 100 && str <= 999) {
				userSfid = "DG" + str;
			} /*else if (str >= 1000 && str <= 9999) {
				userSfid = "DG" + str;
			}*/
			
		}
		log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>setEmployeeId(-) End>>>>>>>>>>>>>>");
		return userSfid;
	}

	@SuppressWarnings("unchecked")
	public String saveEditEmployee(EmployeeBean employee, HttpSession session) throws Exception {
		String message = null;
		try {
			log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>saveEditEmployee(-,-) Start>>>>>>>>>>>>>>");
			employee.setStatus(1);
			employee.setCreationDate(CPSUtils.getCurrentDate());
			employee.setLastModifiedDate(CPSUtils.getCurrentDate());
			employee.setCreatedBy(session.getAttribute(CPSConstants.SFID).toString());
			employee.setLastModifiedBy(session.getAttribute(CPSConstants.SFID).toString());
			employee.setDataentryFlag("1");
			//employee.setWorkingLocation("1");//while update employee data  workinglocation not updating//--KUMARI
			
			/*//employee.setDataentryFlag("2");
			if (!CPSUtils.isNullOrEmpty(employee.getStoreHight()))
				employee.setHeight(employee.getStoreHight());
			else*/
				employee.setHeight(employee.getHeight());

			if (employee.getTitle() != null && !employee.getTitle().equals(CPSConstants.SELECT)) {
				employee.setTitleId(Integer.parseInt(employee.getTitle()));
			}
			if (employee.getMaritalId() != null && !employee.getMaritalId().equals(CPSConstants.SELECT)) {
				employee.setMarital(Integer.parseInt(employee.getMaritalId()));
			}
			if (employee.getCommunityId() != null && !employee.getCommunityId().equals(CPSConstants.SELECT)) {
				employee.setCommunity(Integer.parseInt(employee.getCommunityId()));
			}
			if (employee.getReligion() != null && !employee.getReligion().equals(CPSConstants.SELECT)) {
				employee.setReligionId(Integer.parseInt(employee.getReligion()));
			}
			if (employee.getNationality() != null && !employee.getNationality().equals(CPSConstants.SELECT)) {
				employee.setNationalityId(Integer.parseInt(employee.getNationality()));
			}
			if (employee.getBlood() != null && !employee.getBlood().equals(CPSConstants.SELECT)) {
				employee.setBloodGroupId(Integer.parseInt(employee.getBlood()));
			}
			if (employee.getAppointmentTypeId() != null && !employee.getAppointmentTypeId().equals(CPSConstants.SELECT)) {
				employee.setAppointmentId(Integer.parseInt(employee.getAppointmentTypeId()));
			}
			if (employee.getEmploymentTypeId() != null && !employee.getEmploymentTypeId().equals(CPSConstants.SELECT)) {
				employee.setEmploymentId(Integer.parseInt(employee.getEmploymentTypeId()));
			}
			if (employee.getDesignationId() != null && !employee.getDesignationId().equals(CPSConstants.SELECT)) {
				employee.setDesignation(Integer.parseInt(employee.getDesignationId()));
			}
			/*
			 * if (employee.getDirectorateId() != null && !employee.getDirectorateId().equals(CPSConstants.SELECT)) { employee.setDirectorate(Integer.parseInt(employee.getDirectorateId()));
			 * employee.setOffice(Integer.parseInt(employee.getDirectorateId())); } if (employee.getDivisionId() != null && !employee.getDivisionId().equals(CPSConstants.SELECT)) {
			 * employee.setDirectorate(Integer.parseInt(employee.getDivisionId())); }
			 */
			/**
			 * Dont change the officeId and directorateId from Edit Employee Screen ------Navya
			 */
			employee.setDirectorate(createEmployeeDAO.getEmployeeDetails(employee.getSfid(), CPSConstants.DIRECTORATE_ID));
			employee.setOffice(createEmployeeDAO.getEmployeeDetails(employee.getSfid(), CPSConstants.OFFICE_ID));

			if (employee.getReservationId() != null && !employee.getReservationId().equals(CPSConstants.SELECT)) {
				employee.setReservation(Integer.parseInt(employee.getReservationId()));
			}
			if (employee.getHandicapId() != null && !employee.getHandicapId().equals(CPSConstants.SELECT)) {
				employee.setHandicap(Integer.parseInt(employee.getHandicapId()));
			}
			if (employee.getJoinType() != null && !employee.getJoinType().equals(CPSConstants.SELECT)) {
				employee.setJoinTypeId(Integer.parseInt(employee.getJoinType()));
			}
			if (employee.getDispersaryNumber() != null && !employee.getDispersaryNumber().equals(CPSConstants.SELECT)) {
				employee.setDispensaryNumberId(Integer.parseInt(employee.getDispersaryNumber()));
			}
			employee.setNameInServiceBook(employee.getNameInServiceBook());
			message = createEmployeeDAO.saveEditEmployee(employee);

			// To create self family details
			FamilyBean family = null;
			family = createEmployeeDAO.CheckSelfFamilyDetails(employee.getSfid());
			if (CPSUtils.isNull(family)) {
				family = new FamilyBean();
				family.setId(String.valueOf(commonDataDAO.getTableID(CPSConstants.FAMILY_DETAILS, CPSConstants.UPDATE)));
				family.setSfid(employee.getSfid());
				family.setName(employee.getNameInServiceBook());
				family.setRelationId(CPSConstants.SELF_RELATION_ID);
				family.setGender(employee.getGender());
				family.setDob(employee.getDob());
				if (employee.getHandicapId() != "" && employee.getHandicapId() != null)
					family.setDisabilityId(employee.getHandicapId());
				else
					family.setDisabilityId("0");
				family.setState("0");
				family.setDistrict("0");
				family.setAddressTypeId("");
				if (employee.getBlood() != null && !employee.getBlood().equals(CPSConstants.SELECT))
					family.setBloodGroup(employee.getBlood());
				family.setResidingWith(CPSConstants.N);
				if (employee.getEmploymentTypeId() != "" && employee.getEmploymentTypeId() != null)
					family.setEmployeedType(String.valueOf(employee.getEmploymentTypeId()));
				family.setEmployeed(CPSConstants.Y);
				if (employee.getMarital() != 0)
					family.setMaritalstatus(String.valueOf(employee.getMarital()));
				if (employee.getHandicapId() != "" && employee.getHandicapId() != null)
					family.setPhtypeFlag(CPSConstants.Y);
				else
					family.setPhtypeFlag(CPSConstants.N);
				family.setCghsFacility(CPSConstants.Y);
				family.setLtcFacility(CPSConstants.Y);
				// family.setDependent(CPSConstants.Y);
				family.setAdopted(CPSConstants.N);
				family.setStatus(1);
				family.setCreationDate(CPSUtils.getCurrentDate());
				family.setMaritalstatusId(employee.getMaritalId());
				family.setModifiedDate(CPSUtils.getCurrentDate());
				message = createEmployeeDAO.selfFamilyDetails(family);
			} else {
				family.setName(employee.getNameInServiceBook());
				family.setRelationId(CPSConstants.SELF_RELATION_ID);
				family.setGender(employee.getGender());
				family.setDob(employee.getDob());
				if (employee.getHandicapId() != "" && employee.getHandicapId() != null){
					//family.setDisabilityId(employee.getHandicapId());
					family.setDisabilityId("0");
				}
				else
					family.setDisabilityId("0");
				if (employee.getBlood() != null && !employee.getBlood().equals(CPSConstants.SELECT))
					family.setBloodGroup(employee.getBlood());
				family.setResidingWith(CPSConstants.Y);

				family.setEmployeedType(CPSConstants.GOVTJOBTYPEID);
				family.setEmployeed(CPSConstants.Y);
				if (employee.getMarital() != 0)
					family.setMaritalstatus(String.valueOf(employee.getMarital()));
				if (employee.getHandicapId() != "" && employee.getHandicapId() != null)
					family.setPhtypeFlag(CPSConstants.Y);
				else
					family.setPhtypeFlag(CPSConstants.N);
				family.setCghsFacility(CPSConstants.Y);
				family.setLtcFacility(CPSConstants.Y);
				// family.setDependent(CPSConstants.Y);
				family.setAdopted(CPSConstants.N);
				family.setStatus(1);
				family.setMaritalstatusId(employee.getMaritalId());
				family.setModifiedDate(CPSUtils.getCurrentDate());
				family.setValidFrom(CPSUtils.formattedDate(family.getValidFrom()));
				family.setValidTo(CPSUtils.formattedDate(family.getValidTo()));
				family.setDeclareDate(CPSUtils.formattedDate(family.getDeclareDate()));
				message = createEmployeeDAO.selfFamilyDetails(family);
			}

			if (CPSUtils.compareStrings(CPSConstants.SUCCESS, message)) {
				boolean headFlag = createEmployeeDAO.checkHead(employee.getUserSfid());
				if (!headFlag) {
					// employee

					String parentId = createEmployeeDAO.checkParentSFID(employee.getOffice());
					EmpRoleMappingDTO emprolemapping = new EmpRoleMappingDTO();

					emprolemapping.setCreationDate(CPSUtils.getCurrentDate());
					emprolemapping.setInternalDivision(null);
					emprolemapping.setLastModifiedDate(CPSUtils.getCurrentDate());
					emprolemapping.setSfid(employee.getUserSfid());
					emprolemapping.setStatus(1);
					if (CPSUtils.isNullOrEmpty(parentId)) {

						// emprolemapping.setInternalRole("Head");
						// emprolemapping.setRoleInstanceId(employee.getOffice());
						// emprolemapping.setParentId(null);
						// emprolemapping.setId(createEmployeeDAO.getEmpRoleMapId(employee.getUserSfid()));
						// message =
						// createEmployeeDAO.saveEmpRoleMapping(emprolemapping);

					} else {
						emprolemapping.setParentId(parentId);
						emprolemapping.setInternalRole("Employee");
						emprolemapping.setParentRoleID(employee.getOffice());
						emprolemapping.setId(createEmployeeDAO.getEmpRoleMapId(employee.getUserSfid()));
						message = createEmployeeDAO.saveEmpRoleMapping(emprolemapping);
					}
				}
			}

			employee.setDivisionList(createEmployeeDAO.selectedDivisionList(String.valueOf(employee.getDirectorate())));
			session.setAttribute(CPSConstants.DIVISIONLIST, employee.getDivisionList());
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;

		}
		log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>saveEditEmployee(-,-) End>>>>>>>>>>>>>>");
		return message;
	}

	public EmployeeBean viewRequestedDetails(String sfid) throws Exception {
		EmployeeBean employee = null;
		try {
			log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>viewRequestedDetails(-,-) Start>>>>>>>>>>>>>>");
			employee = createEmployeeDAO.viewRequestedDetails(sfid);
		} catch (Exception e) {
			throw e;
		}
		log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>viewRequestedDetails(-,-) End>>>>>>>>>>>>>>");
		return employee;
	}

	@SuppressWarnings("unchecked")
	public EmployeeBean selectedDivisionList(EmployeeBean employee, HttpSession session) throws Exception {
		try {
			log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>selectedDivisionList(-,-) Start>>>>>>>>>>>>>>");
			employee.setDivisionList(createEmployeeDAO.selectedDivisionList(employee.getDirectorateId()));
			session.setAttribute(CPSConstants.DIVISIONLIST, employee.getDivisionList());
			employee.setHeadName(createEmployeeDAO.checkHeadName(employee.getDirectorateId()));

		} catch (Exception e) {

			throw e;
		}
		log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>selectedDivisionList(-,-) End>>>>>>>>>>>>>>");
		return employee;
	}

	public EmployeeBean checkHeadName(EmployeeBean employee) throws Exception {
		try {
			log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>checkHeadName(-,-) Start>>>>>>>>>>>>>>");
			employee.setHeadName(createEmployeeDAO.checkHeadName(employee.getDivisionId()));
		} catch (Exception e) {
			throw e;
		}
		log.debug("::CreateEmployeeBusiness>>>>>>>>>>>>checkHeadName(-,-) End>>>>>>>>>>>>>>");
		return employee;
	}
	
	public String createOtherDBEmployee(EmployeeBean employeeBean)throws Exception {
		String message = "";
		try {
			message = createEmployeeDAO.createOtherDBEmployee(employeeBean);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public EmployeeBean getEmployeeWithOrgnizationWise(EmployeeBean employee) throws Exception{
		try {
			employee.setEnabledEmployeeList(commonDataDAO.getEnabledEmployeeList(employee));
			employee.setDisabledEmployeeList(commonDataDAO.getDisabledEmployeeList(employee));
		} catch (Exception e) {
			throw e;
		}
		return employee;
	}

	public String manageEmpStatus(String sfid, EmployeeBean employee) throws Exception {
		String message = "";
		try {
			message = commonDataDAO.manageEmpStatus(employee, sfid);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String leaveExceptionalDetails(EmployeeBean employee) throws Exception {
		String message = null;
		try {
			message = createEmployeeDAO.addLeaveExceptionalDetails(employee.getSfid(), employee.getCreatedBy(), employee.getCreationDate(), employee.getLastModifiedBy(), employee.getLastModifiedDate());
		} catch (Exception e) {
			throw e;
		}
		return message;
	}
	
	public List<EmployeeBean> searchEmployees(String searchString) throws Exception
	{
		List<EmployeeBean> empList = createEmployeeDAO.getEmployeesList(searchString, 10);
		return empList;
	}
}
