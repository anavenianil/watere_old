package com.callippus.web.business.outerEmployee;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.ApplicationRoleMappingDTO;
import com.callippus.web.beans.dto.AppointmentDTO;
import com.callippus.web.beans.dto.BloodGroupDTO;
import com.callippus.web.beans.dto.CommunityDTO;
import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.DispensaryNumberDTO;
import com.callippus.web.beans.dto.DivisionDTO;
import com.callippus.web.beans.dto.EmpRoleMappingDTO;
import com.callippus.web.beans.dto.EmploymentDTO;
import com.callippus.web.beans.dto.MaritalDTO;
import com.callippus.web.beans.dto.NationalityDTO;
import com.callippus.web.beans.dto.OrganizationsDTO;
import com.callippus.web.beans.dto.PHTypeDTO;
import com.callippus.web.beans.dto.ReligionDTO;
import com.callippus.web.beans.dto.TitleDTO;
import com.callippus.web.beans.dto.UserDTO;
import com.callippus.web.beans.outerEmployee.OuterEmployeeBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.employee.ICreateEmployeeDAO;

@Service
public class OuterEmployeeBusiness {

	private static Log log = LogFactory.getLog(OuterEmployeeBusiness.class);
	@Autowired
	private ICreateEmployeeDAO createEmployeeDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	
	@SuppressWarnings("unchecked")
	public OuterEmployeeBean getOuterEmployeeGeneralDetails(HttpSession session, OuterEmployeeBean employee) throws Exception {
		try {
			log.debug("::OtherEmployeeBusiness>>>>>>>>>>>>getOtherEmployeeGeneralDetails(-,-) Start>>>>>>>>>>>>>>");

			List<TitleDTO> titleList = commonDataDAO.getMasterData(CPSConstants.TITLEDTO);
			employee.setTitleList(titleList);
			session.setAttribute(CPSConstants.TITLELIST, employee.getTitleList());

			List<OrganizationsDTO> organizationsList = commonDataDAO.getMasterData("OrganizationsDTO");
			employee.setOrganizationsList(organizationsList);
			session.setAttribute("organizationsList", employee.getOrganizationsList());

			List<NationalityDTO> nationalityList = commonDataDAO.getMasterData(CPSConstants.NATIONALITYDTO);
			employee.setNationalityList(nationalityList);
			session.setAttribute(CPSConstants.NATIONALITYLIST, employee.getNationalityList());

			employee.setCheckDate(CPSUtils.getCurrentDate());
			Calendar now = Calendar.getInstance();
			now.add(Calendar.YEAR, -Integer.parseInt(commonDataDAO.getConfigurationValue("dob")));
			String date1 = now.get(Calendar.DATE) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.YEAR);
			SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			Date date = (Date) df.parse(date1);
			employee.setCheckDate(df1.format(date));
			session.setAttribute("checkDate", employee.getCheckDate());
			
			// un comment by bkr 16/04/2016 2lines
			// employee.setUserSfid(String.valueOf(commonDataDAO.getTableID(CPSConstants.USERS, CPSConstants.NEW)));
			// employee.setGeneratedId(employee.getUserSfid());
			 //employee.setUserSfid(createEmployeeBusiness.setEmployeeId(employee.getUserSfid()));
			// employee.setSfid(employee.getUserSfid().trim());
			
			
			
			employee.setUserSfid(String.valueOf(commonDataDAO.getTableID(CPSConstants.USERS, CPSConstants.NEW)));
			employee.setGeneratedId(employee.getUserSfid());
			
			
			employee.setUserSfid(setEmployeeId(employee.getUserSfid(),commonDataDAO.getTableID("LAB_ID", CPSConstants.NEW)));

			
			session.setAttribute(CPSConstants.USERSFID, employee.getUserSfid());
			
			
			
			List<DesignationDTO> designationList = commonDataDAO.getMasterData(CPSConstants.DESIGNATIONDTO);
			employee.setDesignationList(designationList);
			session.setAttribute(CPSConstants.DESIGNATIONLIST, employee.getDesignationList());

			List directorateList = commonDataDAO.getDirectorateList();
			employee.setDirectorateList(directorateList);
			session.setAttribute(CPSConstants.DIRECTORATELIST, employee.getDirectorateList());
			
			List<EmploymentDTO> employementTypeList = commonDataDAO.getMasterData(CPSConstants.EMPLOYMENTDTO);
			employee.setEmployementTypeList(employementTypeList);
			session.setAttribute(CPSConstants.EMPLOYMENTYPELIST, employee.getEmployementTypeList());
			
			List<AppointmentDTO> appointmentTypeList = commonDataDAO.getMasterData(CPSConstants.APPOINTMENTDTO);
			employee.setAppointmentTypeList(appointmentTypeList);
			session.setAttribute(CPSConstants.APPLOINTMENTYPELIST, employee.getAppointmentTypeList());
			
			List<MaritalDTO> maritalList = commonDataDAO.getMasterData(CPSConstants.MARITALDTO);
			employee.setMaritalList(maritalList);
			session.setAttribute(CPSConstants.MARITALLIST, employee.getMaritalList());
			
			List<CommunityDTO> communityList = commonDataDAO.getMasterData(CPSConstants.COMMUNITYDTO);
			employee.setCommunityList(communityList);
			session.setAttribute(CPSConstants.COMMUNITYLIST, employee.getCommunityList());
			
			List<BloodGroupDTO> bloodList = commonDataDAO.getMasterData(CPSConstants.BLOODGROUPDTO);
			employee.setBloodList(bloodList);
			session.setAttribute(CPSConstants.BLOODLIST, employee.getBloodList());

			List<PHTypeDTO> handicapList = commonDataDAO.getMasterData(CPSConstants.PHTYPEDTO);
			employee.setHandicapList(handicapList);
			session.setAttribute("handicapList", employee.getHandicapList());

			List<DispensaryNumberDTO> dispensaryList = commonDataDAO.getMasterData(CPSConstants.DISPENSARYNUMBERDTO);
			employee.setDispensaryNumberList(dispensaryList);
			session.setAttribute(CPSConstants.DISPENSARYLIST, employee.getDispensaryNumberList());
			
			
			List<ReligionDTO> religionList = commonDataDAO.getMasterData(CPSConstants.RELIGIONDTO);
			employee.setReligionList(religionList);
			session.setAttribute(CPSConstants.RELIGIONLIST, employee.getReligionList());
			
			/*List<DivisionDTO> divisionList = commonDataDAO.getMasterData(CPSConstants.DIVISIONDTO);
			employee.setDivisionList(divisionList);
			session.setAttribute(CPSConstants.DIVISIONLIST, employee.getDivisionList());
*/

			/*if (employee.getDirectorateId() != null && !CPSUtils.compareStrings(employee.getDirectorateId(), "Select")) {
				employee.setDivisionList(createEmployeeDAO.selectedDivisionList(employee.getDirectorateId()));
				session.setAttribute(CPSConstants.DIVISIONLIST, employee.getDivisionList());
			}*/
			
		} catch (Exception e) {
			throw e;
		}
		log.debug("::OtherEmployeeBusiness>>>>>>>>>>>>getOtherEmployeeGeneralDetails(-,-) End>>>>>>>>>>>>>>");
		return employee;
	}

	public String submitEmployeeDetails(HttpSession session, OuterEmployeeBean employee) throws Exception {
		String message = "failure";
		try {
			log.debug("::OtherEmployeeBusiness>>>>>>>>>>>>submitOtherEmployeeDetails(-,-) Start>>>>>>>>>>>>>>");
			SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
			Date date = new Date();
			String[] sfid = employee.getUserSfid().split(",");
			//String[] userarray = sfid[0].split("//s");
			String[] userarray = sfid[0].split("\\s");
			if (userarray.length < 2) {
				employee.setUserSfid(userarray[0].trim());
			} else {
				String userid = userarray[0].concat(userarray[1].toString().trim());
				for (int i = 2; i <= userarray.length - 1; i++) {
					userid = userid.concat(userarray[2].toString().trim());
				}//String userid = userarray[0].trim()+ userarray[1].trim();
				//String userid = userarray[0].trim();
				employee.setUserSfid(userid.trim());
			}
			employee.setCreationDate(CPSUtils.getCurrentDate());
			employee.setLastModifiedDate(CPSUtils.getCurrentDate());
			employee.setCreatedBy(session.getAttribute(CPSConstants.SFID).toString());
			employee.setLastModifiedBy(session.getAttribute(CPSConstants.SFID).toString());
			employee.setDataentryFlag("1");
			employee.setFlag("1");
			//employee.setWorkingLocation("1");
			if (employee.getTitle() != null && !employee.getTitle().equals(CPSConstants.SELECT)) {
				employee.setTitleId(Integer.parseInt(employee.getTitle()));
			}
			employee.setFirstName(employee.getFirstName());
			employee.setMiddleName(employee.getMiddleName());
			employee.setLastName(employee.getLastName());
			employee.setPersonalNumber(employee.getPersonalNumber());
			if (!CPSUtils.isNullOrEmpty(employee.getDob())) {
				date = (Date) df.parse(employee.getDob());
				employee.setDob(df.format(date));
			}
			if (employee.getNationalityId() != null && !employee.getNationalityId().equals(CPSConstants.SELECT)) {
				employee.setNationalityId(employee.getNationalityId());
			}
			employee.setResidenceNo(employee.getResidenceNo());
			employee.setInternalNo(employee.getInternalNo());
			if (employee.getDesignationId() != null && !employee.getDesignationId().equals(CPSConstants.SELECT)) {
				employee.setDesignation(Integer.parseInt(employee.getDesignationId()));
			}
			if (employee.getDirectorateId() != null && !employee.getDirectorateId().equals(CPSConstants.SELECT)) {
				employee.setDirectorate(Integer.parseInt(employee.getDirectorateId()));
				employee.setOffice(Integer.parseInt(employee.getDirectorateId()));
			}
			if (employee.getDivisionId() != null && !employee.getDivisionId().equals(CPSConstants.SELECT)) {
				employee.setDirectorate(Integer.parseInt(employee.getDivisionId()));
			}
			// employee.setOffice(Integer.parseInt(getOtherEmployeeDAO().getOfficeId(String.valueOf(employee.getOffice()))));
			if (!CPSUtils.isNullOrEmpty(employee.getDojAsl())) {
				date = (Date) df.parse(employee.getDojAsl());
				employee.setDojAsl(df.format(date));
			}
			/*else
			{
				date =  new Date();
				employee.setDojAsl(df.format(date));
				employee.setLastPromotion(df.format(date));
			}*/
			if (!CPSUtils.isNullOrEmpty(employee.getDojGovt())) {
				date = (Date) df.parse(employee.getDojGovt());
				employee.setDojGovt(df.format(date));
			}
			if (!CPSUtils.isNullOrEmpty(employee.getLastPromotion())) {
				date = (Date) df.parse(employee.getLastPromotion());
				employee.setLastPromotion(df.format(date));
			}
			if (!CPSUtils.isNullOrEmpty(employee.getDojDrdo())) { 
				date = (Date) df.parse(employee.getDojDrdo());
				employee.setDojDrdo(df.format(date));
			}
			if (employee.getWorkingLocation() != null && !employee.getWorkingLocation().equals(CPSConstants.SELECT)) {
				employee.setWorkingLocation(employee.getWorkingLocation());
			}
			employee.setNameInServiceBook(employee.getNameInServiceBook());
			employee.setGpfAcNo(employee.getGpfAcNo());
			
			employee.setEmploymentId(employee.getEmploymentId()); //recently added employeetypeid, seneoritydate,mothertongue,appointmentId,marital,community
			employee.setSeniorityDate(employee.getSeniorityDate());
			employee.setMotherTongue(employee.getMotherTongue());
			employee.setAppointmentId(employee.getAppointmentId());
			employee.setMarital(employee.getMarital());
			employee.setCommunity(employee.getCommunity());
			
			employee.setIdMarks(employee.getIdMarks()); //added by bkr

			UserDTO user = new UserDTO();
			user.setSfid(employee.getUserSfid().trim());
			user.setPassword(employee.getUserSfid().trim());
			user.setUserName(employee.getUserSfid().trim());
			user.setStatus(1);

			String parentId = createEmployeeDAO.checkParentSFID(employee.getOffice());

			EmpRoleMappingDTO emprolemapping = new EmpRoleMappingDTO();
			emprolemapping.setCreationDate(CPSUtils.getCurrentDate());
			//emprolemapping.setId(1);
			emprolemapping.setInternalDivision(null);
			emprolemapping.setLastModifiedDate(CPSUtils.getCurrentDate());
			emprolemapping.setSfid(employee.getUserSfid().trim());
			emprolemapping.setStatus(1);
			emprolemapping.setOrgRoleId(null);
			employee.setStatus(1);

			ApplicationRoleMappingDTO appRoleMap = new ApplicationRoleMappingDTO();
			appRoleMap.setSfid(employee.getUserSfid().trim());
			appRoleMap.setStatus(1);
			appRoleMap.setApplicationRoleId(4);
			appRoleMap.setCreationDate(CPSUtils.getCurrentDate());
			appRoleMap.setLastModifiedDate(CPSUtils.getCurrentDate());

			message = createEmployeeDAO.createEmployee(employee);// Insert data into emp_master table
			/*if (CPSUtils.compareStrings(CPSConstants.SUCCESS, message)) {
				String	message1 = createEmployeeDAO.addOtherEmployee(employee);
				commonDataDAO.updateTableID(CPSConstants.USERS, session.getAttribute(CPSConstants.GENERATEDID).toString());
                System.out.println(message1);
			}*/
			if (CPSUtils.compareStrings(CPSConstants.SUCCESS, message)) {
				message = createEmployeeDAO.saveApplicationRoleMapping(appRoleMap);// Insert data into application_role_mapping
			}
			if (CPSUtils.compareStrings(CPSConstants.SUCCESS, message)) {
				//added by bkr 17/05/2016
				//commonDataDAO.updateTableID(CPSConstants.USERS, session.getAttribute(CPSConstants.GENERATEDID).toString());
				message = createEmployeeDAO.createUser(user);// Insert data into users table
			}
			
			if (CPSUtils.compareStrings(CPSConstants.SUCCESS, message)) {
				Session session1 = null;
				session1 = hibernateUtils.getSession();
				//commented by bkr 24/06/2016
			//	int abc=Integer.parseInt((employee.getUserSfid()).substring(2));
				//added by bkr for emplyee id start from 1000 purpose
				int abc=Integer.parseInt((employee.getUserSfid()));
				
				//System.out.println("abc vale is :"+abc);
				Query qry=session1.createSQLQuery("update ID_GENERATOR set VALUE =? where TABLE_NAME=?");
				qry.setInteger(0,abc).setString(1,CPSConstants.USERS).executeUpdate();
		
				
				
			}
			
			if (CPSUtils.compareStrings(CPSConstants.SUCCESS, message)) {
				if (CPSUtils.isNullOrEmpty(parentId)) {
					emprolemapping.setInternalRole("Head");
					emprolemapping.setRoleInstanceId(employee.getOffice());
					emprolemapping.setParentId(null);
					// emprolemapping.setId((commonDataDAO.getTableID(CPSConstants.EMP_ROLE_MAPPING, CPSConstants.NEW)));
					message = createEmployeeDAO.saveEmpRoleMapping(emprolemapping);
					// commonDataDAO.updateTableID(CPSConstants.EMP_ROLE_MAPPING, String.valueOf(emprolemapping.getId()));
				} else {
					emprolemapping.setParentId(parentId);
					emprolemapping.setInternalRole("Employee");
					emprolemapping.setParentRoleID(employee.getOffice());
					// emprolemapping.setId((commonDataDAO.getTableID(CPSConstants.EMP_ROLE_MAPPING, CPSConstants.NEW)));
					message = createEmployeeDAO.saveEmpRoleMapping(emprolemapping);// Insert data into emp_role_mapping
					// commonDataDAO.updateTableID(CPSConstants.EMP_ROLE_MAPPING, String.valueOf(emprolemapping.getId()));
				}
			}
			if (CPSUtils.compareStrings(CPSConstants.SUCCESS, message)) {
				createEmployeeDAO.addLeaveExceptionalDetails(employee.getUserSfid(), employee.getCreatedBy(), employee.getCreationDate(), employee.getLastModifiedBy(), employee.getLastModifiedDate());
				// commonDataDAO.updateTableID(CPSConstants.USERS, session.getAttribute(CPSConstants.GENERATEDID).toString());
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		log.debug("::OtherEmployeeBusiness>>>>>>>>>>>>submitEmployeeDetails(-,-) End>>>>>>>>>>>>>>");
		return message;
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
			
			
			//userSfid = "SF" + str;
			
			userSfid = "" + str;
			
			
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
}
