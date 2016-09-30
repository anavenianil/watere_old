package com.callippus.web.dao.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.AllowancesDTO;
import com.callippus.web.beans.dto.CategoryDTO;
import com.callippus.web.beans.dto.DepartmentsDTO;
import com.callippus.web.beans.dto.DistrictTypeDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.OrganizationsDTO;
import com.callippus.web.beans.dto.StateTypeDTO;
import com.callippus.web.beans.dto.YearTypeDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.requests.DoPartBean;
import com.callippus.web.beans.schedulereports.ScheduleReportsBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.ConnectionUtil;
import com.callippus.web.leave.beans.request.LeaveWaterApplicationBean;
import com.callippus.web.mmg.cashbuildup.beans.DemandMasterDTO;
import com.callippus.web.mmg.cashbuildup.beans.MMGMasterBean;
import com.callippus.web.mmg.cashbuildup.dto.FundTypeDTO;
import com.callippus.web.mmg.cashbuildup.dto.InventoryMasterDTO;
import com.callippus.web.mmg.cashbuildup.dto.MaterialMasterDTO;
import com.callippus.web.mmg.cashbuildup.dto.UomDTO;
import com.callippus.web.paybill.dto.PayBillEmpCategoryDTO;
import com.callippus.web.paybill.dto.PayBillStatusDTO;

@Service
public class SQLCommonObjectDAO implements IComonObjectDAO, Serializable {
	private static final long serialVersionUID = 1195409952170989553L;
	private static Log log = LogFactory.getLog(SQLCommonObjectDAO.class);

	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	/**
	 * Gets the Master tables Object.
	 * 
	 * @param tableName
	 *            the table name
	 * @return the List.
	 * @throws Exception
	 *             the exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Object> getMasterData(String beanName) throws Exception {
		List<Object> masterObjList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			StringBuffer sb = new StringBuffer();
			sb.append("from " + beanName + " m where m.status = 1");
			if (CPSUtils.compareStrings(beanName, CPSConstants.MENULINKSDTO)) {
				sb.append(" and m.parentID != 0");
			} else if (CPSUtils.compareStrings(beanName, "AwardCategoryDTO") || CPSUtils.compareStrings(beanName, "LeaveTypeDTO")) {
				sb.append(" and m.parentID = 0");
			}
			if (!CPSUtils.compareStrings(beanName, "HierarchyDTO")) {
				if (CPSUtils.compareStrings(beanName, CPSConstants.DISPENSARYNUMBERDTO)) {
					sb.append(" order by m.dispensaryNumber");
				} else if (CPSUtils.compareStrings(beanName, "YearTypeDTO")) {
					sb.append(" order by m.name desc");
				} else if (CPSUtils.compareStrings(beanName, "QualificationDTO")) {
					sb.append(" order by m.name");
				} else if (CPSUtils.compareStrings(beanName, "EmpTransferTxnDTO")) {

				} else if (CPSUtils.compareStrings(beanName, "DeptHierarchyDTO")) {
					sb.append(" order by m.hierarchyName");
				} else if (CPSUtils.compareStrings(beanName, "DoPartDTO")) {
					
				} else if (CPSUtils.compareStrings(beanName, CPSConstants.INVHOLDERMASTER)) {
					sb.append(" order by m.inventoryNo");
				} else if (CPSUtils.compareStrings(beanName, CPSConstants.PROJECTCODEMASTER)) {
					sb.append(" order by m.projectName");
				} else if (CPSUtils.compareStrings(beanName, CPSConstants.ACCOUNTHEADMASTER)) {
					sb.append(" order by m.description");
				} else if (CPSUtils.compareStrings(beanName, "DepartmentsDTO")) {
					sb.append(" order by m.deptName");
				} else if (CPSUtils.compareStrings(beanName, "MenuLinksDTO")) {
					sb.append(" order by m.parentID, m.id");
				} else if (CPSUtils.compareStrings(beanName, CPSConstants.LTCBLOCKYEARSMASTERDTO)) {
					sb.append(" order by m.ltcBlockId, m.fromDate");
				} else if(CPSUtils.compareStrings(beanName, CPSConstants.LTCPENALMASTERDTO)){
					sb.append(" order by m.fromDate");
				} else if (CPSUtils.compareStrings(beanName, CPSConstants.PAYBILLCGHSMASTERDTO)) {
					sb.append(" order by m.gradePay");
				} else if (CPSUtils.compareStrings(beanName, CPSConstants.PAYBILLCGEISMASTERDTO)) {
					sb.append(" order by m.groupId");
				} else if (CPSUtils.compareStrings(beanName, CPSConstants.PAYBILLDEARNESSALLOWANCEMASTERDTO)) {
					sb.append(" order by m.daDate desc");
				} else if (CPSUtils.compareStrings(beanName, CPSConstants.PAYBILLRESIDENTIALSECURITYMASTERDTO)) {
					sb.append(" order by effDate desc");
				} else if (CPSUtils.compareStrings(beanName, CPSConstants.DESIGNATIONDTO)) {
					sb.append(" order by m.name");
				} else if (CPSUtils.compareStrings(beanName, CPSConstants.PROMOTIONBOARDTYPEDTO)) {
					sb.append(" order by m.boardName");
				} else if (CPSUtils.compareStrings(beanName, CPSConstants.TADADETAILSDTO)) {
					sb.append(" order by m.payRangeFrom");
				} else if (CPSUtils.compareStrings(beanName, CPSConstants.ESSMODULEDTO)){
					sb.append(" order by orderBy");
				} else if (CPSUtils.compareStrings(beanName, "PromotionsDisciplineDTO") || CPSUtils.compareStrings(beanName, "PromotionsSubDisciplineDTO")) {
					sb.append(" order by initcap(name)");
				} else if (CPSUtils.compareStrings(beanName, CPSConstants.TRAININGTYPEDTO)) {
					sb.append(" order by m.name");
				} else if (CPSUtils.compareStrings(beanName, CPSConstants.DESIGNATIONDTO)) {
					sb.append(" order by m.id");
				} else if (CPSUtils.compareStrings(beanName, CPSConstants.EMPLOYMENTDTO)) {
					sb.append(" order by m.id");
				} else if (CPSUtils.compareStrings(beanName, CPSConstants.ALERTMASTERDTO)) {
					sb.append(" order by m.id");
				}else if (CPSUtils.compareStrings(beanName, CPSConstants.BANKNAMESDTO)) {
					sb.append(" order by m.status ");
				}else if (CPSUtils.compareStrings(beanName, CPSConstants.BANKMASTERDTO)) {
					sb.append(" order by m.status ");
				}else if (CPSUtils.compareStrings(beanName, CPSConstants.LTCYEARSDTO)) {
					sb.append(" order by m.id ");
				}else if (CPSUtils.compareStrings(beanName, CPSConstants.LEAVEYEARSDTO)) {
					sb.append(" order by m.id ");
				}
				else {
					sb.append(" order by m.name");
				}
			} else {
				sb.append(" order by m.roleID");
			}
			session.clear();
			Query qry = session.createQuery(sb.toString());
			masterObjList = qry.list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return masterObjList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getID(String tableName) throws Exception {
		List<Object> masterObjList = null;
		Session session = null;
		int id = 0;
		//PreparedStatement pstmt = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String sql = "select max(id) from " + tableName;
			Query qry = session.createSQLQuery(sql);
			masterObjList = qry.list();
			id = Integer.parseInt(masterObjList.get(0).toString()) + 1;
		  //session.close();
		} catch(Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, null,null);
			session.flush();
		}
		return id;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getTableID(String tableName, String type) throws Exception {
		List<Object> masterObjList = null;
		Session session = null;
		int id = 0;
		PreparedStatement pstmt = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			Query qry = session.createSQLQuery("select value from id_generator where table_name=? FOR UPDATE");
			qry.setString(0, tableName);
			masterObjList = qry.list();
			if (CPSUtils.checkList(masterObjList)) {
				id = Integer.parseInt(masterObjList.get(0).toString()) + 1;
				if (CPSUtils.compareStrings(CPSConstants.UPDATE, type)) {
					Connection con = session.connection();
					String sql = "update id_generator set value=? where table_name=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, id);
					pstmt.setString(2, tableName);
					pstmt.executeUpdate();
					session.flush();
				}	
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, pstmt, null);
			session.flush();
		}
		return id;
	}

	@Override
	public void updateTableID(String tableName, String value) throws Exception {
		Session session = null;
		PreparedStatement ps = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			Connection con = session.connection();
			String sql = "update id_generator set value=? where table_name=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(value));
			ps.setString(2, tableName);
			ps.executeUpdate();	
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, null);
			session.flush();
		}
	}

	@Override
	public List getDirectorateList() throws Exception {
		List directorateList = null;
		Session session = null;
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			/*
			 * String sql = "select ori.org_role_id,dm.department_name from departments_master dm, org_role_instance ori where dm.status=1 and dm.dept_hierarchy_id in (select dh.dept_hierarchy_id " +
			 * "from departments_hierarchy dh where dh.status=1 start with dh.dept_hierarchy_id=7 connect by dh.dept_hierarchy_id=prior dh.parent_dept_hierarchy_id) and ori.status=1 and ori.org_role_id=dm.department_id order by dm.department_name"
			 * ;
			 */
			String sql = "select ori.org_role_id,department_name from (select dm.department_id,LPAD(' ', level, '- ')||dm.department_name department_name from departments_master dm "
					+ " where dm.status=1 start with dm.department_id=1 connect by dm.parent_department_id = prior dm.department_id) dept,org_role_instance ori  "
					+ " where ori.status=1 and ori.department_id=dept.department_id and ori.is_head=1";
			Query qry = session.createSQLQuery(sql);
			directorateList = qry.list();
		   
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
			session.flush();
		}
		return directorateList;
	}

	@Override
	public List getDivisionList() throws Exception {
		List divisionList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String sql = "select department_id as id,department_name as name from departments_master dm where dm.dept_hierarchy_id in(select  dept_hierarchy_id from departments_master dm where dm.dept_hierarchy_id='8') order by name ";
			divisionList = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();  
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
			session.flush();
		}
		return divisionList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StateTypeDTO> getStateList() throws Exception {
		List<StateTypeDTO> stateList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			Criteria crit = session.createCriteria(StateTypeDTO.class);
			crit.add(Expression.eq("status", 1));
			crit.addOrder(Order.asc("name"));
			stateList = crit.list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
			session.flush();
		}
		return stateList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<AllowancesDTO> getAllowancesList() throws Exception {
		List<AllowancesDTO> allowancesList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			Criteria crit = session.createCriteria(StateTypeDTO.class);
			crit.addOrder(Order.asc("allowanceType"));
			allowancesList = crit.list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
			session.flush();
		}
		return allowancesList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DistrictTypeDTO> getDistrictList() throws Exception {
		List<DistrictTypeDTO> districtList = null;
		Session session = null;
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(DistrictTypeDTO.class);
			crit.add(Expression.eq("status", 1));
			crit.addOrder(Order.asc("name"));
			districtList = crit.list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
			session.flush();
		}
		return districtList;
	}

	@Override
	public String checkEmployee(String sfID) throws Exception {
		Session session = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String sql = "from UserDTO where upper(sfid) = ? and status = 1";
			Query qry = session.createQuery(sql).setString(0, sfID.toUpperCase());
			if (CPSUtils.checkList(qry.list())) {
				message = CPSConstants.SUCCESS;
			} else {
				message = CPSConstants.INVALID;
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		return message;
	}

	@Override
	public List<EmployeeBean> getSubordinateList(String id) throws Exception {
		log.debug("getSubordinateList ---> method start");
		Session session = null;
		List<EmployeeBean> employeesList = null;
		Connection con1 = null;
		PreparedStatement ps1 = null;
		ResultSet rsq1 = null;
		try {
			employeesList = new ArrayList<EmployeeBean>();

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			con1 = session.connection();
			String getSubordinatesList = "select emp.sfid,emp.name_in_service_book empName," + "des.name designame,emp.personal_number personalnum "
					+ "from emp_master emp,designation_master des where office_id in (select org_role_id from emp_role_mapping erm " + "where id=?) and emp.status=1 and emp.designation_id=des.id";
			log.debug("getSubordinatesList query:" + getSubordinatesList);
			ps1 = con1.prepareStatement(getSubordinatesList);
			ps1.setString(1, id);
			rsq1 = ps1.executeQuery();

			while (rsq1.next()) {
				EmployeeBean employeeBean = new EmployeeBean();
				employeeBean.setSfid(rsq1.getString("sfid"));
				employeeBean.setFirstName(rsq1.getString("empName"));
				employeeBean.setDesignationName(rsq1.getString("designame"));
				employeeBean.setPersonalNumber(rsq1.getString("personalnum"));
				employeesList.add(employeeBean);
			}
		} catch (Exception e) {
			throw e;
		} finally {

			//session.close();

			session.flush();

		}
		log.debug("getSubordinateList ---> method end");
		return employeesList;

	}

	@Override
	public ArrayList<KeyValueDTO> getSubOrdinatesList(String sfid) throws Exception {
		Session session = null;
		ArrayList<KeyValueDTO> subOrdList = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		try {
			subOrdList = new ArrayList<KeyValueDTO>();

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			con = session.connection();

			String getSubordinatesList = "select emp.sfid,emp.name_in_service_book,dm.department_id from emp_master emp,departments_master dm,org_role_instance ori "
					+ "where emp.office_id in (select org_role_id from emp_role_mapping where sfid=? and status=1) and emp.status=1 and ori.status=1 and dm.status=1 "
					+ "and ori.org_role_id=emp.office_id and ori.department_id=dm.department_id order by emp.sfid";
			ps = con.prepareStatement(getSubordinatesList);
			ps.setString(1, sfid);
			rsq = ps.executeQuery();
			while (rsq.next()) {
				KeyValueDTO emp = new KeyValueDTO();
				emp.setKey(rsq.getString("sfid"));
				emp.setName(rsq.getString("sfid") + "-" + rsq.getString("name_in_service_book"));
				emp.setId(rsq.getInt("department_id"));
				subOrdList.add(emp);
			}

			/*
			 * String getOrgnRolesSubOrdinates =
			 * "select emp.sfid,emp.name_in_service_book,ori1.department_id from org_role_instance ori1,emp_role_mapping erm,emp_master emp where ori1.department_id in ( " +
			 * "select ori.department_id from emp_role_mapping erm,org_role_instance ori where erm.status=1 and erm.sfid=? and erm.org_role_id is not null " +
			 * "and ori.status=1 and erm.org_role_id=ori.org_role_id) and ori1.status=1 and ori1.is_head=0 and erm.status=1 and erm.org_role_id=ori1.org_role_id " +
			 * "and emp.status=1 and erm.sfid=emp.sfid"; ps = con.prepareStatement(getOrgnRolesSubOrdinates); ps.setString(1, sfid); rsq = ps.executeQuery(); while (rsq.next()) { KeyValueDTO emp = new
			 * KeyValueDTO(); emp.setKey(rsq.getString("sfid")); emp.setName(rsq.getString("sfid") + "-" + rsq.getString("name_in_service_book")); emp.setId(rsq.getInt("department_id"));
			 * subOrdList.add(emp); }
			 */
             
		} catch (Exception e) {
			throw e;
		} finally {

			ConnectionUtil.closeConnection(session, ps, rsq);

			session.flush();

		}
		return subOrdList;
	}

	@Override
	public String deleteCheckMaster(String tableName, String columnName, String status, String columnValue) throws Exception {
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		String message = null;
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			con = session.connection();
			StringBuffer sb = new StringBuffer();
			sb.append("select count(*) from " + tableName + " where " + columnName + "=? ");
			if (CPSUtils.compareStrings(status, CPSConstants.STATUS)) {
				sb.append("and status=1");
			}
			ps = con.prepareStatement(sb.toString());
			ps.setString(1, columnValue);
			rsq = ps.executeQuery();
			if (rsq.next()) {
				if (rsq.getInt(1) > 0) {
					// records exists
					message = CPSConstants.FAILED;
				}
			}
			
		} catch (Exception e) {
			throw e;
		} finally {

			ConnectionUtil.closeConnection(session, ps, rsq);

			session.flush();

		}
		return message;
	}

	@Override
	public List<YearTypeDTO> getYearList() throws Exception {
		List<YearTypeDTO> list = null;
		Session session = null;
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(YearTypeDTO.class);
			crit.add(Expression.eq("status", 1));
			crit.addOrder(Order.asc("name"));
			list = crit.list();
			
		} catch (Exception e) {
			throw e;
		} finally {

			//session.close();

			session.flush();

		}
		return list;
	}

	@Override
	public List getOfficeList() throws Exception {
		List officeList = null;
		Session session = null;
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String sql = "from OrgInstanceDTO o where o.status=1";
			Query qry = session.createQuery(sql);
			officeList = qry.list();
		} catch (Exception e) {
			throw e;
		} finally {

			//session.close();

			session.flush();

		}
		return officeList;
	}

	@Override
	public List<KeyValueDTO> getInstanceList(String sfid) throws Exception {
		log.debug("getInstanceList ---> method start");
		Session session = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection con = null;
		List<KeyValueDTO> instanceList = null;
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			con = session.connection();
			String sql = "select org_role_name,erm.id from org_role_instance oi,emp_role_mapping erm where oi.org_role_id=erm.org_role_id and erm.sfid=? and erm.status=1 and oi.status=1";
			log.debug("sql query:" + sql);
			pst = con.prepareStatement(sql);
			pst.setString(1, sfid);
			rs = pst.executeQuery();
			instanceList = new ArrayList<KeyValueDTO>();
			while (rs.next()) {
				KeyValueDTO keyVal = new KeyValueDTO();
				keyVal.setId(rs.getInt("id"));
				keyVal.setName(rs.getString("org_role_name"));
				instanceList.add(keyVal);
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, pst, rs);
		}
		log.debug("getInstanceList ---> method end");
		return instanceList;
	}

	@Override
	public List checkingChangedSFID(String sfid) throws Exception {
		List list = null;
		Session session = null;
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			Query qry = session.createSQLQuery("select sfid,name_in_service_book from emp_master where sfid=? and status=1");
			qry.setString(0, sfid);
			list = qry.list();

		} catch (Exception e) {
			throw e;
		} finally {

			//session.close();

			

		}
		return list;
	}

	@Override
	public String getConfigurationValue(String name) throws Exception {
		Session session = null;
		String value = "";
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			Query qry = session.createSQLQuery("select value from configuration_details where name=?");
			qry.setString(0, name.toUpperCase());
			value = (String) qry.uniqueResult();
		} catch (Exception e) {
			throw e;
		} finally {

			//session.close();

			

		}
		return value;
	}
	
	@Override
	public String setConfigurationValue(String name,String value) throws Exception {
		Session session = null;
		String message="fail";
		PreparedStatement pstmt=null;
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			Connection con=session.connection();
			String sql="update configuration_details set value=? where name=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, value.toUpperCase());
			pstmt.setString(2, name.toUpperCase());
			if(pstmt.executeUpdate()>0);
				message="success";
				session.flush();
		} catch (Exception e) {
			message="fail";
			throw e;
		} finally {

			//session.close();

			

		}
		return message;
	}


	@Override
	public List<CategoryDTO> getSubCategoryList() throws Exception {
		Session session = null;
		ArrayList<CategoryDTO> subCategoryList = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rsq = null;
		try {
			subCategoryList = new ArrayList<CategoryDTO>();

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			con = session.connection();
			String sql = "select scm.id,scm.name subcategory,cm.name category,cm.id categoryID,scm.description,scm.alias,scm.order_by orderno from sub_category_master scm ,category_master cm " +
					"where cm.id=scm.category_id and cm.status=1 and scm.status=1 order by  scm.order_by";
			stmt = con.createStatement();
			rsq = stmt.executeQuery(sql);
			while (rsq.next()) {
				CategoryDTO distDTO = new CategoryDTO();
				distDTO.setId(rsq.getInt("id"));
				distDTO.setName(rsq.getString("subcategory"));
				distDTO.setCategoryName(rsq.getString("category"));
				distDTO.setCategoryID(rsq.getString("categoryID"));
				distDTO.setDescription(rsq.getString("description"));
				distDTO.setOrderNo(rsq.getString("orderno"));
				distDTO.setAlias(rsq.getString("alias"));
				subCategoryList.add(distDTO);
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, stmt, rsq);
		}
		return subCategoryList;
	}

	@Override
	public List<InventoryMasterDTO> getInventoryNo() throws Exception {
		Session session = null;
		ArrayList<InventoryMasterDTO> districtList = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rsq = null;
		try {
			districtList = new ArrayList<InventoryMasterDTO>();

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			con = session.connection();
			String sql = "select * from mmg_b_inventory_holder order by inventory_no ";
			stmt = con.createStatement();
			rsq = stmt.executeQuery(sql);
			while (rsq.next()) {
				InventoryMasterDTO distDTO = new InventoryMasterDTO();
				distDTO.setInvId(rsq.getString("id"));
				distDTO.setInventoryNo(rsq.getString("inventory_no"));
				districtList.add(distDTO);
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, stmt, rsq);
		}
		return districtList;
	}

	@Override
	public List<EmployeeBean> getEmployeesList() throws Exception {
		List<EmployeeBean> list = null;
		Session session = null;
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(EmployeeBean.class);
			crit.add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.asc("userSfid")).list();
			list = crit.list();
		} catch (Exception e) {
			throw e;
		} finally {

			//session.close();

			session.flush();

		}
		return list;
	}

	@Override
	public String getHierarchyLevel(String sfid) throws Exception {
		String level = "";
		Session session = null;
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String sql = "select orgh.role_hierarchy_name from org_role_instance org,org_role_hierarchy orgh where org.status=1 and orgh.status=1 "
					+ " and org.role_hierarchy_id=orgh.role_hierarchy_id start with org_role_id = (select min(org_role_id) from emp_role_mapping where sfid=? and status=1) connect by parent_org_role_id = prior org_role_id";
			Query qry = session.createSQLQuery(sql);
			qry.setString(0, sfid);
			List<Object> list = qry.list();
			if (CPSUtils.checkList(list)) {
				for (int i = 0; i < list.size(); i++) {
					if (!level.contains(list.get(i).toString())) {
						level += list.get(i).toString() + "/";
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {

			//session.close();

			session.flush();

		}
		return level;
	}

	@Override
	public String getCreationDate(String id, String sfid, String tableName) throws Exception {
		String creationDate = null;
		Session session = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			con = session.connection();
			String sql = "select to_char(creation_date,'DD-MON-YYYY')creation_date from " + tableName + " where id=? and sfid=? and status=1";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, sfid);
			rs = ps.executeQuery();
			if (rs.next()) {
				Date date = new Date();
				SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
				if (rs.getString("creation_date") != null) {
					date = df.parse(rs.getString("creation_date"));
					creationDate = df.format(date);
				}
			}
			
		} catch (Exception e) {
			throw e;
		} finally {

			ConnectionUtil.closeConnection(session, ps, rs);

			session.flush();

		}
		return creationDate;
	}

	// for getting employee directorate and officeId
	@Override
	public EmployeeBean getEmployeeOfficeId(String sfid) throws Exception {
		Session session = null;
		EmployeeBean empbean = null;
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			empbean = (EmployeeBean) session.createCriteria(EmployeeBean.class).add(Expression.eq("userSfid", sfid)).uniqueResult();
		} catch (Exception e) {
			throw e;
		} finally {

			//session.close();

			session.flush();

		}
		return empbean;
	}

	/**
	 * MMG INVENTORY HOLDER DETAILS METHOD
	 * 
	 * @param inventoryNo
	 * @return
	 * @throws Exception
	 */
	@Override
	public InventoryMasterDTO getInventoryHolderDetails(String inventoryNo, String sfid, String orgRoleId) throws Exception {
		Session session = null;
		InventoryMasterDTO invdetails = null;
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			if (!CPSUtils.isNullOrEmpty(inventoryNo)) {
				InventoryMasterDTO inv = (InventoryMasterDTO) session.createCriteria(InventoryMasterDTO.class).add(Expression.eq("invId", inventoryNo)).uniqueResult();
				String sql = "select emp.name_in_service_book as holderName,dirdept.department_name as directorateName,divdept.department_name as divisionName,emp.office_id as divisionId from EMP_MASTER emp,ORG_ROLE_INSTANCE directorate,ORG_ROLE_INSTANCE division,DEPARTMENTS_MASTER dirdept,DEPARTMENTS_MASTER divdept where emp.sfid=? and "
						+ "emp.directorate_id=directorate.org_role_id and directorate.department_id=dirdept.department_id and emp.office_id=division.org_role_id and division.department_id=divdept.department_id";
				invdetails = (InventoryMasterDTO) session.createSQLQuery(sql).addScalar("holderName").addScalar("directorateName").addScalar("divisionName").addScalar("divisionId", Hibernate.STRING)
						.setResultTransformer(Transformers.aliasToBean(InventoryMasterDTO.class)).setString(0, inv.getSfid()).uniqueResult();
			} else if (!CPSUtils.isNullOrEmpty(sfid)) {
				if (!CPSUtils.isNullOrEmpty(orgRoleId)) {
					String sql = "select emp.name_in_service_book as holderName,dm.name as designation,emp.INTERNAL_PHONE_NO as phone,dirdept.department_name as directorateName,divdept.department_name as divisionName from EMP_MASTER emp,DESIGNATION_MASTER dm,ORG_ROLE_INSTANCE directorate,ORG_ROLE_INSTANCE division,DEPARTMENTS_MASTER dirdept,DEPARTMENTS_MASTER divdept where upper(emp.sfid)=? and "
							+ "emp.directorate_id=directorate.org_role_id and emp.designation_id=dm.id and directorate.department_id=dirdept.department_id and division.department_id=divdept.department_id and division.org_role_id=?";
					invdetails = (InventoryMasterDTO) session.createSQLQuery(sql).addScalar("holderName").addScalar("directorateName").addScalar("divisionName").addScalar("designation").addScalar(
							"phone").setResultTransformer(Transformers.aliasToBean(InventoryMasterDTO.class)).setString(0, sfid.toUpperCase()).setString(1, orgRoleId).uniqueResult();
				} else {
					String sql = "select emp.name_in_service_book as holderName,dm.name as designation,emp.INTERNAL_PHONE_NO as phone,dirdept.department_name as directorateName,divdept.department_name as divisionName from EMP_MASTER emp,DESIGNATION_MASTER dm,ORG_ROLE_INSTANCE directorate,ORG_ROLE_INSTANCE division,DEPARTMENTS_MASTER dirdept,DEPARTMENTS_MASTER divdept where upper(emp.sfid)=? and "
							+ "emp.directorate_id=directorate.org_role_id and emp.designation_id=dm.id and directorate.department_id=dirdept.department_id and emp.office_id=division.org_role_id and division.department_id=divdept.department_id";
					invdetails = (InventoryMasterDTO) session.createSQLQuery(sql).addScalar("holderName").addScalar("directorateName").addScalar("divisionName").addScalar("designation").addScalar(
							"phone").setResultTransformer(Transformers.aliasToBean(InventoryMasterDTO.class)).setString(0, sfid.toUpperCase()).uniqueResult();
				}

			}

		} catch (Exception e) {
			throw e;
		} finally {

			//session.close();

			session.flush();

		}
		return invdetails;
	}

	/**
	 * This method is used to get all the employee sfids.It returns List<Object>.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> getSfidList() throws Exception {
		log.debug("getSfidList() --> Start");
		Session session = null;
		List<KeyValueDTO> sfidList = null;
		
		try {
			sfidList = new ArrayList<KeyValueDTO>();

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();

			/*
			 * String sql = "select userSfid as sfid from EmployeeBean where status=1 order by userSfid"; log.debug("query --> "+sql);
			 * sfidList=session.createQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			 */
			sfidList = session
					.createSQLQuery(
							"Select erm.Sfid as key,erm.Org_role_id as flag From Emp_role_mapping erm Where erm.sfid in(select emp.sfid from emp_master emp,designation_master dm where emp.designation_id in(select DESIG_ID from designation_mappings dmap"
									+ " where dmap.type='GAZETTED' and emp.designation_id=dmap.desig_id and dm.status=1) and emp.status=1) and erm.status=1").addScalar("key").addScalar("flag",
							Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();

			session.flush();//tx.commit() ;

			

			for (KeyValueDTO keyValueDTO : sfidList) {
				if (!CPSUtils.isNullOrEmpty(keyValueDTO.getFlag())) {

					//tx = session.beginTransaction();

					

					KeyValueDTO keyval = (KeyValueDTO) session
							.createSQLQuery(
									"Select Department_id as id,Department_name as name From Departments_master Where Department_id=(select department_id from org_role_instance where org_role_id=? and status=1) and status=1")
							.addScalar("id", Hibernate.INTEGER).addScalar("name").setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(0, keyValueDTO.getFlag()).uniqueResult();

					session.flush();//tx.commit() ;

					

					if (!CPSUtils.isNullOrEmpty(keyval)) {
						keyValueDTO.setName(keyValueDTO.getKey() + "-" + keyval.getName());
						keyValueDTO.setKey(keyValueDTO.getKey() + "#" + keyval.getId());
					}
				} else {
					keyValueDTO.setName(keyValueDTO.getKey());
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {

			//session.close();

			

		}
		log.debug("getSfidList() --> End");
		return sfidList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object> getOrgDivisionList() throws Exception {
		log.debug("getOrgDivisionList() --> Start");
		Session session = null;
		List<Object> sfidList = null;
		Transaction tx = null;
		try {
			sfidList = new ArrayList();

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();

		

			Criteria crt = session.createCriteria(DepartmentsDTO.class);
			crt.add(Expression.eq("hierarchyID", "8"));
			crt.addOrder(Order.asc("deptName"));
			sfidList = crt.list();

			session.flush();//tx.commit() ;

			

		} catch (Exception e) {
			throw e;
		} finally {

			//session.close();

			session.flush();

		}
		log.debug("getOrgDivisionList() --> End");
		return sfidList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object> getFundTypeList() throws Exception {
		log.debug("getFundTypeList() --> Start");
		Session session = null;
		List<Object> fundTypeList = null;
		
		try {
			fundTypeList = new ArrayList();

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();

			Criteria crt = session.createCriteria(FundTypeDTO.class);
			crt.add(Expression.eq("status", 1));
			fundTypeList = crt.list();

			session.flush();//tx.commit() ;

			

		} catch (Exception e) {
			throw e;
		} finally {

			//session.close();

			session.flush();

		}
		log.debug("getFundTypeList() --> End");
		return fundTypeList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<MaterialMasterDTO> searchValues(DemandMasterDTO demand) throws Exception {
		log.debug("getMaterialDetails() --> Start");
		Session session = null;
		List itemSubCatList = null;
		
		try {
			itemSubCatList = new ArrayList<MMGMasterBean>();

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

		

			//tx = session.beginTransaction();


			StringBuffer sb = new StringBuffer();
			sb
					.append("Select Mm.Id as materialCode,Mm.Material_name as materialName,Mm.Category_id as categoryCode,Mm.Company_code as companyCode,Mm.Consumable_flag as consumableFlag,Mm.Description as description,Mm.Item_code_id as itemCode,"
							+ "Mm.Item_sub_code_id as itemSubCode,Mm.Rate_contract_flag as rcFlag,Mm.Sub_category_id as subCategoryCode,Mm.Unit_rate as unitRate,Mm.Uom as uom,Icm.Id as categoryId,Icm.Category_name as categoryName,Scm.Id as subCategoryId,Scm.Sub_category_name as subCategoryName,"
							+ "im.id as itemCodeId,im.item_name as codeName,iscm.id as itemSubCodeId,iscm.sub_code_name as subCodeName,cm.id as companyId,cm.name as companyName,um.name as uomName "
							+ "From Mmg_b_material_master Mm,Mmg_b_item_category_master Icm,Mmg_b_item_sub_category_master Scm,Mmg_b_item_code_master Im,"
							+ "Mmg_b_item_sub_code_master Iscm,Mmg_b_company_master Cm,Mmg_b_uom_master Um");
			if (CPSUtils.compareStrings(demand.getType(), CPSConstants.EXTERNALISSUEVOUVHER) || CPSUtils.compareStrings(demand.getType(), CPSConstants.TRANSFERVOUVHER)
					|| CPSUtils.compareStrings(demand.getType(), CPSConstants.EXPENSEVOUVHER) || CPSUtils.compareStrings(demand.getType(), CPSConstants.CONDEMNATIONVOUVHER))
				sb.append(",mmg_b_stock_details sdd");
			sb.append(" where");
			if (CPSUtils.compareStrings(demand.getType(), CPSConstants.EXTERNALISSUEVOUVHER) || CPSUtils.compareStrings(demand.getType(), CPSConstants.TRANSFERVOUVHER))
				sb.append(" sdd.material_code=mm.id and sdd.inventory_no=? and sdd.status=1 and");
			else if (CPSUtils.compareStrings(demand.getType(), CPSConstants.EXPENSEVOUVHER))
				sb.append(" sdd.material_code=mm.id and sdd.inventory_no=? and sdd.status=1 and upper(sdd.consumable_flag)='C' and");
			else if (CPSUtils.compareStrings(demand.getType(), CPSConstants.CONDEMNATIONVOUVHER))
				sb.append(" sdd.material_code=mm.id and sdd.inventory_no=? and sdd.status=1 and upper(sdd.consumable_flag)='NC' and");
			sb
					.append(" Icm.Id=Mm.Category_id And Scm.Id=Mm.Sub_category_id And Im.Id=Mm.Item_code_id And Iscm.Id(+)=Mm.Item_sub_code_id And Cm.Id(+)=Mm.Company_code And Um.Id=Mm.Uom and mm.status=1 Order By Mm.Material_name");
			log.debug("query --> " + sb.toString());

			if (CPSUtils.compareStrings(demand.getType(), CPSConstants.EXTERNALISSUEVOUVHER) || CPSUtils.compareStrings(demand.getType(), CPSConstants.TRANSFERVOUVHER)
					|| CPSUtils.compareStrings(demand.getType(), CPSConstants.EXPENSEVOUVHER) || CPSUtils.compareStrings(demand.getType(), CPSConstants.CONDEMNATIONVOUVHER)) {
				itemSubCatList = session.createSQLQuery(sb.toString()).addScalar("materialCode").addScalar("materialName").addScalar("categoryCode").addScalar("companyCode").addScalar(
						"consumableFlag").addScalar("description").addScalar("itemCode").addScalar("itemSubCode").addScalar("rcFlag", Hibernate.STRING).addScalar("subCategoryCode").addScalar(
						"unitRate").addScalar("uom").addScalar("categoryId").addScalar("categoryName").addScalar("subCategoryId").addScalar("subCategoryName").addScalar("itemCodeId").addScalar(
						"codeName").addScalar("itemSubCodeId").addScalar("subCodeName").addScalar("companyId").addScalar("companyName").addScalar("uomName").setResultTransformer(
						Transformers.aliasToBean(MMGMasterBean.class)).setString(0, demand.getInventoryNo()).list();

			} else {
				itemSubCatList = session.createSQLQuery(sb.toString()).addScalar("materialCode").addScalar("materialName").addScalar("categoryCode").addScalar("companyCode").addScalar(
						"consumableFlag").addScalar("description").addScalar("itemCode").addScalar("itemSubCode").addScalar("rcFlag", Hibernate.STRING).addScalar("subCategoryCode").addScalar(
						"unitRate").addScalar("uom").addScalar("categoryId").addScalar("categoryName").addScalar("subCategoryId").addScalar("subCategoryName").addScalar("itemCodeId").addScalar(
						"codeName").addScalar("itemSubCodeId").addScalar("subCodeName").addScalar("companyId").addScalar("companyName").addScalar("uomName").setResultTransformer(
						Transformers.aliasToBean(MMGMasterBean.class)).list();
			}

			session.flush();//tx.commit() ;

			

		} catch (Exception e) {
			throw e;
		} finally {

			//session.close();

			session.flush();

		}
		log.debug("getMaterialDetails() --> End");
		return itemSubCatList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UomDTO> getUomTypes() throws Exception {
		log.debug("getUomTypes() --> Start");
		Session session = null;
		
		List<UomDTO> list = null;
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();


			list = session.createCriteria(UomDTO.class).add(Expression.eq("status", 1)).addOrder(Order.asc("name")).list();

			session.flush();//tx.commit() ;

			

		} catch (Exception e) {
			throw e;
		} finally {

			//session.close();

			session.flush();

		}
		log.debug("getUomTypes() --> End");
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List getInventoryNumsList(String invHolderSfid) throws Exception {
		log.debug("getVoucherTypes method ----> Start");
		Session session = null;
		
		List<InventoryMasterDTO> invNumsList = null;
		List<Object> orgRoleIdList = null;
		List<Object> sfidList = null;
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();

			orgRoleIdList = new ArrayList<Object>();
			orgRoleIdList = session.createQuery("select roleInstanceId from EmpRoleMappingDTO where sfid=? and status=1").setString(0, invHolderSfid).list();

			session.flush();//tx.commit() ;

		


			if (CPSUtils.checkList(orgRoleIdList) && !CPSUtils.isNullOrEmpty(orgRoleIdList.get(0))) {
				sfidList = new ArrayList<Object>();
				for (int i = 0; i < orgRoleIdList.size(); i++) {

					//tx = session.beginTransaction();

					

					List<Object> list = session.createQuery("select userSfid from EmployeeBean where office=? and status=1").setString(0, orgRoleIdList.get(i).toString()).list();

					session.flush();//tx.commit() ;

					

					sfidList.addAll(list);
				}
			} else {
				sfidList = new ArrayList<Object>();

				//tx = session.beginTransaction();

				

				sfidList = session.createQuery("select userSfid from EmployeeBean where office=(select emp.office from EmployeeBean emp where emp.userSfid=? and emp.status=1) and status=1")
						.setString(0, invHolderSfid).list();

				session.flush();//tx.commit() ;

			

			}
			invNumsList = new ArrayList<InventoryMasterDTO>();
			for (int i = 0; i < sfidList.size(); i++) {

				//tx = session.beginTransaction();

				

				List<InventoryMasterDTO> list = session.createQuery("select inventoryNo as inventoryNo,invId as invId from InventoryMasterDTO where sfid=? and status=1 order by inventoryNo")
						.setString(0, sfidList.get(i).toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

				session.flush();//tx.commit() ;

				

				invNumsList.addAll(list);
			}
		} catch (Exception e) {
			throw e;
		} finally {

			//session.close();


		}
		log.debug("getVoucherTypes method ----> End");
		return invNumsList;
	}

	public List<KeyValueDTO> getHierarchySubOrdinatesList(String roleID) throws Exception {
		Session session = null;
		List<KeyValueDTO> list = null;
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();


		} catch (Exception e) {
			throw e;
		} finally {

			//session.close();

			

		}
		return list;
	}

	@Override
	public String checkDuplicate(String tableName, String columnName, String columnValue, String editID) throws Exception 
	{
		Session session = null;
		String result = null;
		StringBuffer sb = new StringBuffer();
		try
		{
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			sb.append("select count(*) from " + tableName + " where " + columnName + "='" + columnValue + "' " + " and status=" + 1);
			if (!CPSUtils.isNullOrEmpty(editID))
				sb.append(" and id!=" + editID);
			if (Integer.valueOf(session.createSQLQuery(sb.toString()).uniqueResult().toString()) > 0)
				result = CPSConstants.DUPLICATE;
			else
				result = CPSConstants.SUCCESS;
		} catch (Exception e) {
			result = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public DoPartBean getDoPartID(String doPartDate, String doPartNo, String gazType, String createdBy) throws Exception {
		Session session = null;
	
		DoPartBean doPartBean = new DoPartBean();
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			if (gazType.equals("GAZETTED")) {
				gazType = "G";
			} else {
				gazType = "NG";
			}
			List<DoPartBean> list = session.createQuery("from DoPartBean where status=1 and gaz_type=? and (doPartDate=? or doPartNo=? ) ").setString(0, gazType).setString(1, doPartDate).setString(2,
					doPartNo.toUpperCase()).list();
			if (!CPSUtils.checkList(list)) {
				// Insert do part details
				doPartBean.setDoPartDate(doPartDate);
				doPartBean.setDoPartNo(doPartNo.toUpperCase());
				doPartBean.setCreatedBy(createdBy);
				doPartBean.setCreationDate(CPSUtils.getCurrentDate());
				doPartBean.setStatus(1);
				doPartBean.setGazettedType(gazType);
				doPartBean.setRefType("D");

				//tx = session.beginTransaction();

				

				session.saveOrUpdate(doPartBean);

				session.flush();//tx.commit() ;


				

				doPartBean = (DoPartBean) session.get(DoPartBean.class, Integer.valueOf(session.createCriteria(DoPartBean.class).setProjection(Projections.projectionList().add(Projections.max("id")))
						.uniqueResult().toString()));

			} else {
				doPartBean = (list.get(0));
			}

		} catch (Exception e) {
			throw e;
		} finally {

			//session.close();

			session.flush();

		}
		return doPartBean;
	}

	@Override
	public ArrayList<CategoryDTO> getCategoryList() throws Exception {
		Session session = null;
		ArrayList<CategoryDTO> districtList = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rsq = null;
		try {
			districtList = new ArrayList<CategoryDTO>();

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			con = session.connection();
			String sql = "select order_by orderNo,cm.alias,cm.id,cm.name,cm.description from category_master cm where status=1 order by  order_by";
			stmt = con.createStatement();
			rsq = stmt.executeQuery(sql);
			while (rsq.next()) {
				CategoryDTO distDTO = new CategoryDTO();
				distDTO.setOrderNo(rsq.getString("orderNo"));
				distDTO.setId(rsq.getInt("id"));
				distDTO.setName(rsq.getString("name"));
				distDTO.setDescription(rsq.getString("description"));
				distDTO.setAlias(rsq.getString("alias"));
				districtList.add(distDTO);
			}
			
		} catch (Exception e) {
			throw e;
		} finally {

			ConnectionUtil.closeConnection(session, stmt, rsq);

			session.flush();

		}
		return districtList;
	}
	
	@Override
	public Date getPaybillRunmonth() throws Exception {
		Session session = null;
		Date payBillRunMonth=null;
		try {
			session = hibernateUtils.getSession();
			payBillRunMonth=((Date) session.createSQLQuery("select last_day(last_day(run_month)) from paybill_status_details where id=?").setInteger(0,
					(Integer) session.createCriteria(PayBillStatusDTO.class).setProjection(Projections.max(CPSConstants.ID)).uniqueResult())
					.uniqueResult());
		} catch (Exception e) {
			throw e;
		}
		return payBillRunMonth;
	}
	@Override
	public String saveObject(Object obj) throws Exception {
		log.debug("save() --> Start");
		String message = null;
		Session session = null;
		
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();

			session =  hibernateUtils.getSession();
			

			session.save(obj);
			session.flush();
			
			message = CPSConstants.SUCCESS;

			session.flush();//tx.commit() ;


			
			
			log.debug("message -->" + message);
		} catch (Exception e) {
			message = CPSConstants.FAILED;

			//tx.rollback();

		

			throw e;
		} finally {

			
			//session.close();
			

			session.flush();	

		}
		log.debug("save() --> End");
		return message;
	}
	
	@Override
	public String chageUserPassword(EmployeeBean employee) {
		log.debug("save() --> CHANGE USER PASSWORD Start");
		
		String message = null;
		Session session = null;
		Connection con = null;
		CallableStatement cstmt = null;
		PreparedStatement pst=null;
      try{
			session = hibernateUtils.getSession();

			con = session.connection();
			
			String npwd = employee.getNewpassword();
			String sfid = employee.getSfid();
			//commented by bkr 14/09/2016
			//cstmt = con.prepareCall("{call USER_MODIFY(?,?)}");
			//added by bkr 14/09/2016 one line
			//cstmt = con.prepareCall("{call CHANGE_PWD(?,?)}");
			
			//added by bkr 15/09/2016 
			String sql="update users set PASSWORD=? where USERNAME=?";
			pst=con.prepareStatement(sql);
			pst.setString(1, npwd);
			pst.setString(2, sfid);
			boolean b=pst.execute();
			//commented by bkr 15/09/2016
			//cstmt.setString(1,npwd);
			//cstmt.setString(2,sfid);
			//boolean b = cstmt.execute();
			if(b){
			message = CPSConstants.FAILED;
			}else{
				message = CPSConstants.SUCCESS;
		 log.debug("message --> + CHANGE PASSWORD SUCCESS");
			}	
		} catch (Exception e) {
		   message = CPSConstants.FAILED;
			e.printStackTrace();	
		} finally{

		}
		log.debug("save() --> End");
		return message;
		}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrganizationsDTO> getOrganizations(EmployeeBean employee) throws Exception {
		Session session = null;
		
		List<OrganizationsDTO> orgList = null;
		try{

			session = hibernateUtils.getSession();

			orgList = session.createCriteria(OrganizationsDTO.class).add(Expression.eq("status", 1)).list();
            employee.setOrgList(orgList);   
			 
		}catch(Exception e){
			throw e;
		}
		 finally {
			

			}
		
		return orgList;
	}
@Override
	public List<OrganizationsDTO> getOrganizations() throws Exception {
		Session session = null;
		Transaction tx = null;
		List<OrganizationsDTO> orgList = null;
		
		try{

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			orgList = session.createCriteria(OrganizationsDTO.class).add(Expression.eq("status", 1))
						.list();
            
		}catch(Exception e){
			throw e;
		}
		
		
		return orgList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeBean> getEnabledEmployeeList(EmployeeBean employee) throws Exception {
		List<EmployeeBean> employeeList = null;
		Session session = null;
		
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			
			String sql = "select * from Enabled_EMPLOYEE_VIEW where organizationId=?";
			employeeList = session.createSQLQuery(sql).addScalar("value", Hibernate.STRING).addScalar("name", Hibernate.STRING).setString(0,employee.getOrganizationId()).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
				
		} catch (Exception e) {
			throw e;
		} finally {
			session.flush();
	    	}
		return employeeList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeBean> getDisabledEmployeeList(EmployeeBean employee) throws Exception {
		List<EmployeeBean> employeeList = null;
		Session session = null;
		
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			
			String sql = "select * from disabled_employee_view where organizationid=?";
			employeeList = session.createSQLQuery(sql).addScalar("value", Hibernate.STRING).addScalar("name", Hibernate.STRING).setString(0, employee.getOrganizationId()).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
			
			
		} catch (Exception e) {
			throw e;
		} finally {

		

			session.flush();

		}
		return employeeList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public String manageEmpStatus(EmployeeBean employee,String sfid) throws Exception {
		String message ="";
		Session session = null;
	    Transaction tx = null;
		boolean flag = false;
		try{
			
	    session = hibernateUtils.getSession();
//GATTU		hibernateUtils.beginTransaction(session);
	    Connection con =   	session.connection();
	   
	    String[] a=employee.getFromID().split("\\,");
	    for (int i = 0; i < a.length; i++) {
			String fromID = a[i];
			session.clear();
			List<String> roleid = session.createSQLQuery("SELECT TO_CHAR(REQUEST_ID) FROM REQUEST_WORKFLOW_HISTORY WHERE STATUS=2 AND (APPLIED_BY in ('"+fromID+"') OR ASSIGNED_TO in ('"+fromID+"'))").list();
//			 List<String>rolemap=session.createSQLQuery("Select id from emp_role_mapping where ORG_ROLE_ID is not null and  STATUS=1 AND sfid='"+fromID+"'").list();
			 
			 KeyValueDTO rolemap = (KeyValueDTO)session.createSQLQuery("SELECT em.name_in_service_book name,dm.name key,ORI.org_role_name value FROM"
			 		+ " emp_role_mapping erm,designation_master dm,emp_master em,org_role_instance ORI "
			 		+ "WHERE erm.ORG_ROLE_ID IS NOT NULl  AND erm.STATUS=1 AND erm.sfid='"+fromID+"' AND em.sfid ='"+fromID+"' AND dm.id=em.DESIGNATION_ID AND ori.org_role_id = erm.org_role_id And rownum < 2").addScalar("name", Hibernate.STRING).addScalar("value",Hibernate.STRING).addScalar("key",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).uniqueResult();
			 
		    //List<String> roleid = session.createSQLQuery("SELECT TO_CHAR(REQUEST_ID) FROM REQUEST_WORKFLOW_HISTORY WHERE STATUS=2 AND (ASSIGNED_FROM = ? OR ASSIGNED_TO = ?)").setString(0,employee.getFromID()).setString(1,employee.getFromID()).list(); */
			

			
			
			 if (roleid.size() == 0 && CPSUtils.isNullOrEmpty(rolemap)){
					flag = true;
			 }else {
					flag = false;
					if (roleid.size() != 0)
					message+=fromID+" has pending requests"+roleid.toString()+".So unable to disable this employee. <br/>";
					
					if (!CPSUtils.isNullOrEmpty(rolemap))
						message+= rolemap.getName()+"("+rolemap.getKey()+") is "+rolemap.getValue()+".So unable to disable this employee. <br/>";
					
				}
				if (flag) {
					CallableStatement cstmt = con
							.prepareCall("{call USER_DISABLE2(?)}");
					cstmt.setString(1, employee.getFromID());
				 cstmt.execute();
					
						message = CPSConstants.SUCCESS;
					/*else
						message+= "Procedure has been failed for "+ fromID+"<br/>";
						//message = CPSConstants.FAILED;
*/				}
				
			roleid = null;	
				
		}
	    
                                           
	    }catch (Exception e) {
		    message = CPSConstants.FAILED;
			throw e;
	   }finally {
		   session.flush();

	   }
		return message;
	 }


	//@SuppressWarnings("unchecked")
	//@Override
	/*public String manageCghsUploads(WorkFlowMappingBean workBeanMap )throws Exception {
		String message = null;
		Session session = null;
		String sql = null;
	
		try{
			session = hibernateUtils.getSession();
		if(workBeanMap.getRequestType().equals("normal")||  workBeanMap.getRequestType() == "normal"){
			CGHSRequestProcessBean crpb = new CGHSRequestProcessBean();
 			crpb = (CGHSRequestProcessBean)session.createCriteria(CGHSRequestProcessBean.class).add(Expression.eq("requestID",workBeanMap.getRequestId())).uniqueResult();
 			crpb.setPrescriptionFile(workBeanMap.getPrescriptionFile());
			session.flush();
			
		}else if(workBeanMap.getRequestType().equals("advance")|| workBeanMap.getRequestType() == "advance"){
	       CghsAdvanceRequestDTO cghsAdvance = new CghsAdvanceRequestDTO();
	       cghsAdvance = (CghsAdvanceRequestDTO)session.createCriteria(CghsAdvanceRequestDTO.class).add(Expression.eq("requestID", workBeanMap.getRequestId())).uniqueResult();
		   cghsAdvance.setCghsCardFile(workBeanMap.getCghsCardFile());
		   cghsAdvance.setEstimationFile(workBeanMap.getEstimationFile());
		   session.flush();
			}
			else if(workBeanMap.getRequestType().equals("reimbursement")||workBeanMap.getRequestType() == "reimbursement"){
				CghsReimbursementRequestDTO cghsReim = new CghsReimbursementRequestDTO();
				
				cghsReim = (CghsReimbursementRequestDTO)session.createCriteria(CghsReimbursementRequestDTO.class).add(Expression.eq("requestID", workBeanMap.getRequestId())).uniqueResult();
	           	cghsReim.setCghsCardFile(workBeanMap.getCghsCardFile());
     			cghsReim.setMedicalBillsFile(workBeanMap.getMedicalBillsFile());
     			session.flush();
     			
     			CGHSRequestProcessBean crpb = new CGHSRequestProcessBean();
     			crpb = (CGHSRequestProcessBean)session.createCriteria(CGHSRequestProcessBean.class).add(Expression.eq("requestID",cghsReim.getReferrenceRequestID())).uniqueResult();
     			crpb.setPrescriptionFile(workBeanMap.getPrescriptionFile());
				session.flush();
			}else if(workBeanMap.getRequestType().equals("emergency")||workBeanMap.getRequestType() == "emergency"){
				CghsEmergencyRequestDTO cghsEmergency = new CghsEmergencyRequestDTO();
			
				cghsEmergency = (CghsEmergencyRequestDTO)session.createCriteria(CghsEmergencyRequestDTO.class).add(Expression.eq("requestID",workBeanMap.getRequestId())).uniqueResult();
			    cghsEmergency.setCghsCardFile(workBeanMap.getCghsCardFile());
			    cghsEmergency.setDischargeSummeryFile(workBeanMap.getDischargeSummeryFile());
			    cghsEmergency.setMedicalBillsFile(workBeanMap.getMedicalBillsFile());
			    session.flush();
			}*/
		  //message = CPSConstants.SUCCESS;

 /*}catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		return message;
	 }
*/
	
	@Override
	public String getSequenceValue(String srqName) throws Exception{
		Session session = null;
		Object id="";
		try{
			session = hibernateUtils.getSession();
			id = session.createSQLQuery("SELECT "+srqName+".NEXTVAL FROM DUAL").uniqueResult();
			session.flush();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return id.toString();
	}
	@Override
	public String getSequenceCurrentValue(String srqName) throws Exception{
		Session session = null;
		Object id="";
		try{
			session = hibernateUtils.getSession();
			id = session.createSQLQuery("SELECT "+srqName+".CURRVAL FROM DUAL").uniqueResult();
			session.flush();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return id.toString();
	}
	//code to get list category name
	@Override
	@SuppressWarnings("unchecked")
	public List<PayBillEmpCategoryDTO> getListOfCategoryDetails(ScheduleReportsBean scheduleReportsBean) throws Exception{
		Session session = null;
		List<PayBillEmpCategoryDTO> categoryList = null;
		try{
			session = hibernateUtils.getSession();
			//categoryList=session.createSQLQuery("select id as id,category_name as name from pay_emp_category_master where paybill_type='"+scheduleReportsBean.getPayBillType()+"' order by order_no asc").addScalar("id", Hibernate.INTEGER).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(PayBillEmpCategoryDTO.class)).list();
			//String convertedDate=scheduleReportsBean.getSchedulemonth().parse(s);
			String date = scheduleReportsBean.getSchedulemonth().toString();
			String[] tempDate = date.split(" ");
			String runMonth = tempDate[1].toUpperCase() + "-" + tempDate[5];
			categoryList=session.createSQLQuery("select category_id as id,category_name as name from SCHEDULE_CAT_COUNT WHERE to_char(run_month,'MON-yyyy')='"+runMonth+"' and schedule='"+scheduleReportsBean.getReportType()+"' order by order_no asc").addScalar("id", Hibernate.INTEGER).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(PayBillEmpCategoryDTO.class)).list();
		}catch (Exception e) {
			throw e;
		}
		return categoryList;
	}
	//FOR GETTING SCHEDULE REPORT TYPE NAMES FROM VIEW //--KUMARI
	public List<KeyValueDTO> getScheduleTypes() throws Exception{
		Session session=null;
		List<KeyValueDTO> schedulesList=null;
		try{
			session=hibernateUtils.getSession();
			//schedulesList=session.createSQLQuery("select distinct schedule as name from schedule_cat_count").addScalar("name",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
			schedulesList=session.createSQLQuery("select distinct schedule as name,order_by from schedule_cat_count order by order_by").addScalar("name",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
		}catch(Exception e){
			throw e;
		}
		return schedulesList;
	}
//for getting working location of specific employee//--KUMARI
	public int getWorkingLocation(String sfid) throws Exception{
		Session session=null;
		int workingLoaction=0;
		try{
			session=hibernateUtils.getSession();
			 Object obj=session.createSQLQuery("select working_location from emp_master where status=1 and sfid=?").setString(0, sfid).uniqueResult();
			if(!CPSUtils.isNullOrEmpty(obj)){
				 workingLoaction=((BigDecimal)obj).intValue();
			}
			
		}catch( Exception e){
			throw e;
		}
		return workingLoaction;
	}
	
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Object> getAvailLeaveData(String beanName,LeaveWaterApplicationBean leaveWaterBean) throws Exception {
		List<Object> masterObjList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			StringBuffer sb = new StringBuffer();
			//sb.append("from " + beanName + " m where m.status = 1");
			//sb.append("from " + beanName + " m where m.status = 1 and ( m.applicableGen='ALL' or m.applicableGen='"+leaveWaterBean.getEmpDetailsList().getGender()+"'");
			sb.append("from " + beanName + " m where m.status = 1  and ((m.applicableGen='ALL') or ( m.applicableGen='"+leaveWaterBean.getEmpDetailsList().getGender()+"'))      ");
			
			
			if (!CPSUtils.compareStrings(beanName, "ErpAvailableLeaveTypesDTO")) {
				
				sb.append(" order by m.id");
				
			} else {
				sb.append(" order by m.id");
			}
			session.clear();
			Query qry = session.createQuery(sb.toString());
			masterObjList = qry.list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return masterObjList;
	}
	
	
	
}




