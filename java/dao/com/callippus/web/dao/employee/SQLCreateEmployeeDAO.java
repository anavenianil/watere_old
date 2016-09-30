package com.callippus.web.dao.employee;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.ApplicationRoleMappingDTO;
import com.callippus.web.beans.dto.EmpRoleMappingDTO;
import com.callippus.web.beans.dto.UserDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.family.FamilyBean;
import com.callippus.web.beans.outerEmployee.OuterEmployeeBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.leave.dto.LeaveExceptionalEmployees;

@Service
public class SQLCreateEmployeeDAO implements ICreateEmployeeDAO, Serializable {

	private static Log log = LogFactory.getLog(SQLCreateEmployeeDAO.class);
	private static final long serialVersionUID = 7376543344941542448L;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	/**
	 * THIS METHOD SAVE THE VAUES OF EmployeeBean TO EMP_MASTER TABLE
	 */
	@Override
	public String createEmployee(Object employee) throws Exception {
		log.debug("::SQLCreateEmployeeDAO>>>>>>>>>>>>>>>createEmployee(EmployeeBean) Start>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		// OuterEmployeeBean oeb = (OuterEmployeeBean) employee;
		String message = null;
		Session session = null;
		// Connection con = null;
		// EmployeeBean empbean =(EmployeeBean)employee;
		// CallableStatement cstmt = null;

		try {
			session = hibernateUtils.getSession();
			session.saveOrUpdate(employee);
			/*
			 * procedure call con = session.connection(); cstmt =
			 * con.prepareCall("{call Create_User(?)}"); cstmt.setString(1,"");
			 * cstmt.execute();
			 */
			session.flush();
			message = CPSConstants.SUCCESS;

			/*
			 * session = hibernateUtils.getSession(); session.createQuery(
			 * "update id_generator set value=substr('?',3) where table_name=?"
			 * ).setString(0,
			 * oeb.getUserSfid()).setString(1,CPSConstants.USER).executeUpdate
			 * ();
			 */
			// commonDataDAO.getTableID(CPSConstants.USERS, CPSConstants.NEW));

		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		log.debug("::SQLCreateEmployeeDAO>>>>>>>>>>>>>>>createEmployee(EmployeeBean) End>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return message;
	}

	/**
	 * THIS METHOD SAVE THE VALUES OF UserDTO TO USERS TABLE
	 */
	@Override
	public String createUser(UserDTO user) throws Exception {
		log.debug("::SQLCreateEmployeeDAO>>>>>>>>>>>>>>>createUser(UserDTO) Start>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String message = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			session.saveOrUpdate(user);
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		log.debug("::SQLCreateEmployeeDAO>>>>>>>>>>>>>>>createUser(UserDTO) End>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return message;
	}

	/**
	 * THIS METHOD SAVE THE VALUES OF FamilyBean TO FAMILY_FETAILS TABLE
	 */
	@Override
	public String selfFamilyDetails(FamilyBean family) throws Exception {
		log.debug("::SQLCreateEmployeeDAO>>>>>>>>>>>>>>>selfFamilyDetails(FamilyBean family) Start>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String message = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			session.saveOrUpdate(family);
			message = CPSConstants.SUCCESS;

			// hibernateUtils.closeSession();
		} catch (Exception e) {
			throw e;
		}
		log.debug("::SQLCreateEmployeeDAO>>>>>>>>>>>>>>>selfFamilyDetails(FamilyBean family) End>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return message;
	}

	/**
	 * THIS MEHTOD GET THE ALL THE DETAILS OF AN GIVEN EMPLOYEE SFID
	 */
	@Override
	@SuppressWarnings("unchecked")
	public EmployeeBean viewEmployeeDetails(EmployeeBean employee1)
			throws Exception {
		log.debug("::SQLCreateEmployeeDAO>>>>>>>>>>>>>>>viewEmployeeDetails(String Sfid) Start>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.debug("::Sfid is :::::" + employee1.getSfid());
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		EmployeeBean employee = null;
		try {
			employee = new EmployeeBean();
			session = hibernateUtils.getSession();
			con = session.connection();

			String sql = "select sfid,emp.title,tm.name title_name,emp.relation_title relation_title, tm1.name father_spouse_title_name,first_name, middle_name,last_name,to_char(dob,'DD-MON-YYYY') dob, "
					+ "gender,emp.marital_id,mm.name marital_name ,emp.nationality_id, ntm.name nationality_name,emp.religion_id,rm.name religion_name, emp.community_id,emp.blood_group,bgm.name blood_name, "
					+ "residence_no, personal_number,internal_phone_no,mother_tongue,height,upper(id_marks) as id_marks, name_in_service_book,emp.appointment_type_id, atm.name app_name ,emp.employment_type_id,etm.name "
					+ "emplo_name, dept2.department_id directorate_id , dept2.department_name directorate_name, cm.name community_name, designation_id,dm.name designation_name,emp.reservation_type_id,rem.name  "
					+ "reservation_name , handicap_id, ptm.name handicap_name, join_type_id,jtm.name join_name, pre_experiance_period,family_planning,   house_allowance, group_allowance, "
					+ "to_char(GROUP_INSURANCE_EXPIRE_DATE,'DD-MON-YYYY')upto_date,relation_id,relation_name, to_char(doj_govt,'DD-MON-YYYY') doj_govt  , to_char(doj_drdo,'DD-MON-YYYY') doj_drdo, "
					+ "to_char(doj_asl,'DD-MON-YYYY') doj_asl, dept1.department_id office_id, dept1.department_name as office_name, to_char(last_promotion,'DD-MON-YYYY') last_promotion , "
					+ "to_char(seniority_date,'DD-MON-YYYY') seniority_date, emp.dispensary_number ,  dispen.dispensary_number dispensary_name, cgsh_number,ppa_no,pran_no,gpf_ac_no, get_retirement_date(upper(?)) retirement_date from  "
					+ "emp_master emp,designation_master dm, departments_master  dept1,nationality_type_master ntm, departments_master dept2,org_role_instance ori1,org_role_instance ori2,"
					+ "religion_master rm,marital_master mm,  community_master cm,appointment_type_master  atm,employment_type_master etm,reservation_master rem,blood_group_master bgm, "
					+ "title_master tm,title_master tm1,ph_type_master ptm, dispensary_mapping dispen,join_type_master jtm "
					+ "where  cm.id=emp.community_id and rm.id=emp.religion_id and  "
					+ "mm.id=emp.marital_id and  bgm.id=emp.blood_group and atm.id=emp.appointment_type_id and etm.id=emp.employment_type_id and rem.id=emp.reservation_type_id and tm.id=emp.title "
					+ "and ptm.id =emp.handicap_id and dispen.id=emp.dispensary_number and jtm.id=emp.join_type_id and  dm.id=emp.designation_id  and ntm.id=emp.nationality_id "
					+ "and ori1.org_role_id=emp.office_id and ori2.org_role_id=emp.directorate_id "
					+ "and ori1.department_id=dept1.department_id and ori2.department_id=dept2.department_id and emp.status=1 and dept1.status=1 and ori1.status=1 and dept2.status=1 and ori2.status=1 "
					+ "and sfid=upper(?) and tm1.id(+)=emp.relation_title ";
			log.debug("::SQL Query :::::" + sql);

			System.out.println(sql);
			// String sql =
			// "select sfid,title,first_name,middle_name,last_name,to_char(dob,'DD-MON-YYYY') dob,gender,marital_id,religion_id,community_id, "+
			// "blood_group,residence_no,personal_number,internal_phone_no,mother_tongue,height,id_marks,appointment_type_id,employment_type_id, "+
			// "designation_id,reservation_type_id,handicap_id,join_type_id,pre_experiance_period,family_planning,house_allowance, "+
			// "group_allowance,to_char(upto_date,'DD-MON-YYYY')upto_date ,to_char(doj_govt,'DD-MON-YYYY') doj_govt,directorate_id, "+
			// "to_char(doj_drdo,'DD-MON-YYYY') doj_drdo,to_char(doj_asl,'DD-MON-YYYY') doj_asl, office_id, "+
			// "to_char(last_promotion,'DD-MON-YYYY') last_promotion ,to_char(seniority_date,'DD-MON-YYYY') seniority_date, "+
			// "dispensary_number,cgsh_number,ppa_no,pran_no,gpf_ac_no from emp_master where sfid=?";

			ps = con.prepareStatement(sql);
			ps.setString(1, employee1.getSfid());
			ps.setString(2, employee1.getSfid());
			rsq = ps.executeQuery();
			if (rsq.next()) {
				Date date = new Date();
				SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
				employee.setSfid(rsq.getString("sfid"));
				employee.setTitle(rsq.getString("title"));
				employee.setTitleName(rsq.getString("title_name"));
				employee.setFirstName(rsq.getString("first_name"));
				employee.setMiddleName(rsq.getString("middle_name"));
				employee.setLastName(rsq.getString("last_name"));
				if (rsq.getString("relation_id") == "25") {
					employee.setRelationIdName("Father");
					employee.setRelationId(rsq.getString("relation_id"));
				}

				else {
					employee.setRelationIdName("Spouse");
					employee.setRelationId(rsq.getString("relation_id"));
				}
				employee.setRelationName(rsq.getString("relation_name"));
				if (rsq.getString("dob") != null) {
					date = df.parse(rsq.getString("dob"));
					employee.setDob(df.format(date));
				}
				employee.setGender(rsq.getString("gender"));
				employee.setRelationTitle(rsq.getString("relation_title"));
				employee.setMaritalId(rsq.getString("marital_id"));
				employee.setMaritalName(rsq.getString("marital_name"));
				employee.setReligion(rsq.getString("religion_id"));
				employee.setReligionName(rsq.getString("religion_name"));
				employee.setCommunityId(rsq.getString("community_id"));
				employee.setCommunityName(rsq.getString("community_name"));
				employee.setNationality(rsq.getString("nationality_id"));
				employee.setNationalityName(rsq.getString("nationality_name"));
				employee.setBlood(rsq.getString("blood_group"));
				employee.setBloodName(rsq.getString("blood_name"));
				employee.setResidenceNo(rsq.getString("residence_no"));
				employee.setPersonalNumber(rsq.getString("personal_number"));
				employee.setInternalNo(rsq.getString("internal_phone_no"));
				employee.setMotherTongue(rsq.getString("mother_tongue"));
				employee.setHeight(rsq.getString("height"));
				employee.setIdMarks(rsq.getString("id_marks"));
				employee.setNameInServiceBook(rsq
						.getString("name_in_service_book"));

				// Professional details

				employee.setAppointmentTypeId(rsq
						.getString("appointment_type_id"));
				employee.setAppName(rsq.getString("app_name"));
				employee.setEmploymentTypeId(rsq
						.getString("employment_type_id"));
				employee.setEmpName(rsq.getString("emplo_name"));
				employee.setDesignationId(rsq.getString("designation_id"));
				employee.setDesignationName(rsq.getString("designation_name"));
				employee.setDirectorateId(rsq.getString("office_id"));
				employee.setDirectorateName(rsq.getString("office_name"));
				employee.setDivisionId(rsq.getString("directorate_id"));
				employee.setDivisionName(rsq.getString("directorate_name"));
				// employee.setDivisionId(rsq.getString("division_id"));
				employee.setReservationId(rsq.getString("reservation_type_id"));
				employee.setReservationName(rsq.getString("reservation_name"));

				employee.setHandicapId(rsq.getString("handicap_id"));
				employee.setHandicapName(rsq.getString("handicap_name").trim());

				employee.setJoinType(rsq.getString("join_type_id"));
				employee.setJoinName(rsq.getString("join_name").trim());
				employee.setWorkedYears(rsq.getString("pre_experiance_period"));

				employee.setFamPlanning(rsq.getString("family_planning"));
				employee.setHouseAllowence(rsq.getString("house_allowance"));
				employee.setGroupAllowence(rsq.getString("group_allowance"));
				employee.setRetirementDate(rsq.getString("retirement_date"));
				if (rsq.getString("upto_date") != null) {
					date = df.parse(rsq.getString("upto_date"));
					employee.setUptoDate(df.format(date));
				}
				if (rsq.getString("doj_govt") != null) {
					date = df.parse(rsq.getString("doj_govt"));
					employee.setDojGovt(df.format(date));
				}
				if (rsq.getString("doj_drdo") != null) {
					date = df.parse(rsq.getString("doj_drdo"));
					employee.setDojDrdo(df.format(date));
				}
				if (rsq.getString("doj_asl") != null) {
					date = df.parse(rsq.getString("doj_asl"));
					employee.setDojAsl(df.format(date));
				}
				if (rsq.getString("seniority_date") != null) {
					date = df.parse(rsq.getString("seniority_date"));
					employee.setSeniorityDate(df.format(date));
				}
				if (rsq.getString("last_promotion") != null) {
					date = df.parse(rsq.getString("last_promotion"));
					employee.setLastPromotion(df.format(date));
				}

				// Other Details
				employee.setDispersaryNumber(rsq.getString("dispensary_number"));
				employee.setDispensaryName(rsq.getString("dispensary_name"));

				if (rsq.getString("dispensary_name").equals("Select")) {
					employee.setDispensaryName("");

				}
				employee.setCgshNumber(rsq.getString("cgsh_number"));
				employee.setPpaNo(rsq.getString("ppa_no"));
				employee.setPranNo(rsq.getString("pran_no"));
				employee.setGpfAcNo(rsq.getString("gpf_ac_no"));
			} else {
				employee.setMessage("Invalid");
			}

			session = hibernateUtils.getSession();// session =
													// sessionFactory.openSession();
			Criteria crit = (session
					.createCriteria(ApplicationRoleMappingDTO.class)
					.add(Expression.eq("sfid", employee1.getLoginSfid())))
					.add(Expression.eq("applicationRoleId", 1));
			List<ApplicationRoleMappingDTO> list = crit.list();
			System.out.println(list.size());
			if (list.size() != 0)
				employee.setRoleId("1");

		} catch (Exception e) {
			throw e;
		}
		log.debug("::SQLCreateEmployeeDAO>>>>>>>>>>>>>>>viewEmployeeDetails(String Sfid) End>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return employee;
	}

	/**
	 * THIS MEHTOD UPDATE THE VALUES OF EMP_MASTER TABLE FOR A SPECIFIC SFID
	 */
	@Override
	public String saveEditEmployee(EmployeeBean employee) throws Exception {
		log.debug("::SQLCreateEmployeeDAO>>>>>>>>>>>>>>>saveEditEmployee(EmployeeBean) Start>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String message = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			session.saveOrUpdate(employee);
			session.flush();
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		log.debug("::SQLCreateEmployeeDAO>>>>>>>>>>>>>>>saveEditEmployee(EmployeeBean) End>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return message;
	}

	@Override
	public EmployeeBean viewRequestedDetails(String sfid) throws Exception {
		log.debug("::SQLCreateEmployeeDAO>>>>>>>>>>>>>>>viewRequestedDetails(String sfid) Start>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		EmployeeBean employee = null;
		try {
			employee = new EmployeeBean();
			session = hibernateUtils.getSession();
			con = session.connection();
			String reqDetails = "select residence_no,internal_phone_no,personal_number from emp_master where sfid=?";
			ps = con.prepareStatement(reqDetails);
			ps.setString(1, sfid);
			rsq = ps.executeQuery();
			if (rsq.next()) {
				employee.setResidenceNo(rsq.getString("residence_no"));
				employee.setInternalNo(rsq.getString("internal_phone_no"));
				employee.setPersonalNumber(rsq.getString("personal_number"));
			}
		} catch (Exception e) {
			throw e;
		}
		log.debug("::SQLCreateEmployeeDAO>>>>>>>>>>>>>>>viewRequestedDetails(String sfid) End>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return employee;
	}

	/**
	 * THIS MEHTOD PURPOSE IS TO KNOW THE PARENT SFID VALUE OF GIVEN OFFICE ID
	 */
	@Override
	public String checkParentSFID(int officeId) throws Exception {
		log.debug("::SQLCreateEmployeeDAO>>>>>>>>>>>>>>>checkParentSFID(int officeId) Start>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.debug("::Method Parameter Value ::::::" + officeId);
		String getSfid = "";
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		try {
			session = hibernateUtils.getSession();
			con = session.connection();
			String reqDetails = "select sfid from emp_role_mapping where org_role_id =? and status=1 ";
			log.debug("::SQL Query ::::" + reqDetails);
			ps = con.prepareStatement(reqDetails);
			ps.setInt(1, officeId);
			rsq = ps.executeQuery();
			if (rsq.next()) {
				getSfid = rsq.getString("sfid");
			}
		} catch (Exception e) {
			throw e;
		}
		log.debug("::SQLCreateEmployeeDAO>>>>>>>>>>>>>>>checkParentSFID(int officeId) End>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return getSfid;
	}

	@Override
	public boolean checkHead(String sfid) throws Exception {
		log.debug("::SQLCreateEmployeeDAO>>>>>>>>>>>>>>>checkHead(String sfid) Start>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.debug("::Method Parameter Value:::::" + sfid);
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		boolean headFlag = false;
		try {
			session = hibernateUtils.getSession();
			con = session.connection();
			String checkHead = "select sfid from emp_role_mapping where parent_id is null and sfid=? and status=1";
			log.debug("::SQL Query is::::" + checkHead);
			ps = con.prepareStatement(checkHead);
			ps.setString(1, sfid);
			rsq = ps.executeQuery();
			if (rsq.next()) {
				headFlag = true;
			}
		} catch (Exception e) {
			throw e;
		}
		log.debug("::SQLCreateEmployeeDAO>>>>>>>>>>>>>>>checkHead(String sfid) End>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return headFlag;
	}

	/**
	 * THIS METHOD SAVE EmpRoleMappingDTO VALUES IN TO THE EMP_ROLE_MAPPING
	 * TABLE
	 */
	@Override
	public String saveEmpRoleMapping(EmpRoleMappingDTO emprolemapping)
			throws Exception {
		log.debug("::SQLCreateEmployeeDAO>>>>>>>>>>>>>>>saveEmpRoleMapping(EmpRoleMappingDTO ) Start>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String message = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			session.saveOrUpdate(emprolemapping);
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		log.debug("::SQLCreateEmployeeDAO>>>>>>>>>>>>>>>saveEmpRoleMapping(EmpRoleMappingDTO ) End>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return message;
	}

	/**
	 * THE PURPOSE OF THIS MEHTOD IS GETTING DIVISION LIST OF GIVEN 'DIRECTORATE
	 * ID'
	 */
	@Override
	public List selectedDivisionList(String departmentId) throws Exception {
		log.debug("::SQLCreateEmployeeDAO>>>>>>>>>>>>>>>selectedDivisionList(String  directorateId ) Start>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.debug("::Method Parameter Value ::::::" + departmentId);
		List divisionList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			String sql = "select ori.org_role_id,dept.department_name from org_role_instance ori,departments_master dept where ori.department_id=(select department_id from departments_master where "
					+ "department_id=(select parent_department_id from departments_master where department_id=?)) and ori.status=1 and dept.status=1 "
					+ "and dept.department_id=ori.department_id and ori.is_head=1";
			log.debug("::SQL Query is ::::::" + sql);
			Query qry = session.createSQLQuery(sql);
			qry.setString(0, departmentId);
			divisionList = qry.list();
		} catch (Exception e) {
			throw e;
		}
		log.debug("::SQLCreateEmployeeDAO>>>>>>>>>>>>>>>selectedDivisionList(String  directorateId ) End>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return divisionList;
	}

	/**
	 * GETTING EMPLOYEE ROLEMAPID FROM EMP_ROLE_MAPPING WITH THE HELP OF 'SFID'
	 * AVAILABLE IN TH METHOD PARAMETER
	 */
	@Override
	public int getEmpRoleMapId(String sfid) throws Exception {
		log.debug("::SQLCreateEmployeeDAO>>>>>>>>>>>>>>>getEmpRoleMapId(String sfid ) Start>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.debug("::Method Parameter Value :::" + sfid);
		int id = 0;
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		try {
			session = hibernateUtils.getSession();
			con = session.connection();
			String reqDetails = "select id from emp_role_mapping where sfid=? and status=1";
			log.debug("::SQL Query :::" + reqDetails);
			ps = con.prepareStatement(reqDetails);
			ps.setString(1, sfid);
			rsq = ps.executeQuery();
			if (rsq.next()) {
				id = Integer.parseInt(rsq.getString("id"));
			}
		} catch (Exception e) {
			throw e;
		}
		log.debug("::SQLCreateEmployeeDAO>>>>>>>>>>>>>>>getEmpRoleMapId(String sfid ) End>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return id;
	}

	/**
	 * GETTING THE EMPLOYEE HEAD NAME FROM EMP_MASTER WITH THE HELP OF
	 * 'INSTANCEID' AVAILABLE IN THE METHOD PARAMETER
	 */
	@Override
	public String checkHeadName(String instanceId) throws Exception {
		log.debug("::SQLCreateEmployeeDAO>>>>>>>>>>>>>>>checkHeadName(String instanceId ) Start>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.debug("::Method Parameter Value :::" + instanceId);
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		String headName = "";
		try {
			session = hibernateUtils.getSession();
			con = session.connection();
			String chekcHead = "select erm.sfid,em.name_in_service_book from emp_role_mapping erm,emp_master em where "
					+ "erm.sfid=em.sfid and erm.status=1 and em.status=1 and org_role_id =?";
			log.debug("::SQL Query :::" + chekcHead);
			ps = con.prepareStatement(chekcHead);
			ps.setString(1, instanceId);
			rsq = ps.executeQuery();
			if (rsq.next()) {
				headName = rsq.getString("name_in_service_book");
			}
		} catch (Exception e) {
			throw e;
		}
		log.debug("::SQLCreateEmployeeDAO>>>>>>>>>>>>>>>checkHeadName(String instanceId ) End>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return headName;
	}

	/**
	 * SAVING THE VALUES OF ApplicationRoleMappingDTO IN THE
	 * APPLICATION_ROLE_MAPPING TABLE
	 */
	@Override
	public String saveApplicationRoleMapping(
			ApplicationRoleMappingDTO appRoleMap) throws Exception {

		log.debug("::SQLCreateEmployeeDAO>>>>>>>>>>>>>>>saveApplicationRoleMapping(ApplicationRoleMappingDTO ) Start>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String message = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			session.saveOrUpdate(appRoleMap);
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		log.debug("::SQLCreateEmployeeDAO>>>>>>>>>>>>>>>saveApplicationRoleMapping(ApplicationRoleMappingDTO ) End>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return message;
	}

	/**
	 * GETTING ORG_ROLE_ID FROM ORG_ROLE_INSTANCE TABLE WITH GIVEN OFFICEID
	 * WHICH IS IN THE METHOD PARAMETER
	 */
	@Override
	public String getOfficeId(String officeId) throws Exception {

		log.debug("::SQLCreateEmployeeDAO>>>>>>>>>>>>>>>getOfficeId(String officeId ) Start>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.debug("::Method Parameter Value :::" + officeId);
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		String id = null;
		try {
			session = hibernateUtils.getSession();
			con = session.connection();
			String getOfficeId = "select ori.org_role_id from org_role_instance ori,departments_master dm where dm.status=1 and ori.status=1 and "
					+ "dm.department_id=ori.department_id and ori.is_head=1 and dm.department_id=?";
			log.debug("::SQL Query :::" + getOfficeId);
			ps = con.prepareStatement(getOfficeId);
			ps.setString(1, officeId);
			rsq = ps.executeQuery();
			if (rsq.next()) {
				id = rsq.getString("org_role_id");
			}
		} catch (Exception e) {
			throw e;
		}
		log.debug("::SQLCreateEmployeeDAO>>>>>>>>>>>>>>>getOfficeId(String officeId ) End>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return id;
	}

	@Override
	public FamilyBean CheckSelfFamilyDetails(String sfid) throws Exception {
		FamilyBean familyBean = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			familyBean = (FamilyBean) session
					.createCriteria(FamilyBean.class)
					.add(Expression.eq(CPSConstants.SFID, sfid))
					.add(Expression.eq(CPSConstants.RELATIONID,
							CPSConstants.SELF_RELATION_ID))
					.add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
		} catch (Exception e) {
			throw e;
		}
		return familyBean;
	}

	@Override
	public int getEmployeeDetails(String sfid, String columnName)
			throws Exception {
		Session session = null;
		int id = 0;
		try {
			session = hibernateUtils.getSession();
			id = Integer.parseInt((String) session
					.createSQLQuery(
							"select " + columnName
									+ "||'' from emp_master where sfid=?")
					.setString(0, sfid).uniqueResult());
		} catch (Exception e) {
			throw e;
		}
		return id;
	}

	@Override
	public String createOtherDBEmployee(EmployeeBean employeeBean)
			throws Exception {
		String message = "";
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			session.createSQLQuery("select user_create(?) from dual")
					.setString(0, employeeBean.getUserSfid()).executeUpdate();
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	@Override
	public String addOtherEmployee(Object employee) throws Exception {
		String message1 = "";
		Session session = null;
		Connection con = null;
		CallableStatement cstmt = null;
		EmployeeBean normalEmployee = new EmployeeBean();
		OuterEmployeeBean outerEmployee = new OuterEmployeeBean();
		try {
			session = hibernateUtils.getSession();
			con = session.connection();
			cstmt = con.prepareCall("{call User_Create(?)}");
			if (employee.getClass().toString()
					.equals(normalEmployee.getClass().toString())) {
				cstmt.setString(1, ((EmployeeBean) employee).getSfid()
						.toUpperCase());
			} else if (employee.getClass().toString()
					.equals(outerEmployee.getClass().toString())) {
				cstmt.setString(1, ((OuterEmployeeBean) employee).getUserSfid()
						.toUpperCase());
			}
			// Boolean b = cstmt.execute();
			// cstmt.setString(1,((OuterEmployeeBean)
			// employee).getSfid().toUpperCase());
			Boolean result = cstmt.execute();
			con.commit();
			if (result) {
				message1 = CPSConstants.FAIL;
			} else {
				message1 = CPSConstants.ADDED;
			}
		} catch (Exception e) {
			throw e;
		}
		return message1;
	}

	public String addLeaveExceptionalDetails(String userId,
			String createdPerson, String creationDate,
			String lastModifiedPerson, String lastModifiedDate)
			throws Exception { // Added By Naresh
		String message = null;
		LeaveExceptionalEmployees person = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			person = new LeaveExceptionalEmployees();
			person.setSfID(userId);
			person.setStatus(1);
			person.setCreatedBy(createdPerson);
			person.setCreationDate(creationDate);
			person.setLastModifiedBy(lastModifiedPerson);
			person.setLastModifiedDate(lastModifiedDate);
			session.save(person);
			session.flush();
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return message;
	} // End

	@Override
	public List<EmployeeBean> getEmployeesList(String nameFilter,
			int noOfRecords) throws Exception {
		Session session = null;
		List<EmployeeBean> employeesList = new ArrayList<EmployeeBean>();
		try {
			session = hibernateUtils.getSession();
			Query query = null;
			if (!nameFilter.equals("")) {
				query = session
						.createQuery("FROM EmployeeBean E WHERE E.status = 1 and upper(E.firstName) like upper('%" + nameFilter + "%') or upper(E.middleName) like upper('%" + nameFilter + "%') or upper(E.lastName) like upper('%" + nameFilter + "%')  or upper(E.userSfid) like upper('%" + nameFilter + "%')");
			} else
				query = session
						.createQuery("FROM EmployeeBean E WHERE E.status = 1");

			query.setFirstResult(0);

			if (noOfRecords > 0)
				query.setMaxResults(noOfRecords);

			employeesList = (List<EmployeeBean>) query.list();
		} catch (Exception e) {
			throw e;
		}
		return employeesList;
	}

}
