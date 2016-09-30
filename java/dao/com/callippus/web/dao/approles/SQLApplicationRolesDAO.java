package com.callippus.web.dao.approles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.approles.ApplicationRolesBean;
import com.callippus.web.beans.dto.ApplicationRoleMappingDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.ConnectionUtil;

@Service
public class SQLApplicationRolesDAO implements IApplicationRolesDAO {
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	public List getApplicationRolesMappingList(ApplicationRolesBean appRolesBean) throws Exception {
		Session session = null;
		Query qry = null;
		List employeeList = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			StringBuilder sb = new StringBuilder();
			sb.append("select arm.id,emp.sfid,emp.name_in_service_book empName,dm.name designation,rm.name approlename,arm.role_id "
					+ "from application_role_mapping arm,emp_master emp,role_master rm,designation_master dm "
					+ "where emp.status=1 and arm.status=1 and rm.status=1 and rm.id=arm.role_id and emp.sfid=arm.sfid and dm.status=1 and dm.id=emp.designation_id ");

			if (CPSUtils.compareStrings("name", appRolesBean.getSearchingWith())) {
				if (CPSUtils.compareStrings("startWith", appRolesBean.getSelectedValue())) {
					sb.append(" and upper(emp.name_in_service_book) like '" + appRolesBean.getEnteredValue().toUpperCase() + "%'");
				} else {
					sb.append(" and upper(emp.name_in_service_book) like '%" + appRolesBean.getEnteredValue().toUpperCase() + "%'");
				}

			} else if (CPSUtils.compareStrings("sfid", appRolesBean.getSearchingWith())) {
				sb.append(" and upper(emp.sfid)='" + appRolesBean.getEnteredValue().toUpperCase() + "'");
			} else if (CPSUtils.compareStrings("designation", appRolesBean.getSearchingWith())) {
				sb.append(" and dm.id=" + appRolesBean.getSelectedValue());
			} else if (CPSUtils.compareStrings("approle", appRolesBean.getSearchingWith())) {
				sb.append(" and arm.role_id=" + appRolesBean.getSelectedValue());
			}
			sb.append(" order by emp.sfid");

			qry = session.createSQLQuery(sb.toString());
			employeeList = qry.list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return employeeList;
	}

	public String submitApplicationRoles(ApplicationRolesBean appRolesBean) throws Exception {
		Session session = null;
		Transaction tx = null;

		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			String updateApplicationRoles = "update application_role_mapping set status=0,last_modified_date=sysdate where sfid=? and status=1";
			session.createSQLQuery(updateApplicationRoles).setString(0, appRolesBean.getMapSfid()).executeUpdate();

			String[] checkedValues = appRolesBean.getCheckedValues().split(",");
			for (int i = 0; i < checkedValues.length; i++) {
				ApplicationRoleMappingDTO appRoleDTO = new ApplicationRoleMappingDTO();
				appRoleDTO.setSfid(appRolesBean.getMapSfid());
				appRoleDTO.setStatus(1);
				appRoleDTO.setCreationDate(CPSUtils.getCurrentDate());
				appRoleDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				appRoleDTO.setApplicationRoleId(Integer.parseInt(checkedValues[i]));
				session.saveOrUpdate(appRoleDTO);
			}
			session.flush();//tx.commit() ;
			appRolesBean.setMessage(CPSConstants.SUCCESS);
		} catch (Exception e) {
			appRolesBean.setMessage(CPSConstants.FAILED);
			throw e;

		} finally {
			//session.close();
		}
		return appRolesBean.getMessage();
	}

	public String deleteApplicationRoles(ApplicationRolesBean appRolesBean) throws Exception {
		PreparedStatement pstmt = null;
		String message = "";
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			Connection con = session.connection();
			String sql = "update application_role_mapping set status=? where id=? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, 0);
			pstmt.setString(2, appRolesBean.getId());
			pstmt.executeUpdate();
			message = CPSConstants.DELETED;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, pstmt, null);
		}
		return message;
	}
}