
package com.callippus.web.dao.login;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.login.LoginBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.ConnectionUtil;

@Service
public class SQLLoginDAO implements ILoginDAO, Serializable {
	private static Log log = LogFactory.getLog(SQLLoginDAO.class);
	private static final long serialVersionUID = 2566182737057938684L;

	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	public LoginBean checkuser(LoginBean user) throws Exception {
		Session session = null;
		List<Object> list = null;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rsq = null;
		Statement stmt = null;
		PreparedStatement pst = null;
		Connection conn = null;

		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String sql = "select u.userId from LoginBean u where upper(u.username)=upper(?) and u.password=? and status=1";
			/*if(!user.getUsername().equals("SF0781")){
				return user;
			}*/
			Query qry = session.createQuery(sql);
			qry.setString(0, user.getUsername());
			qry.setString(1, user.getPassword());
			list = qry.list();
			if (!list.isEmpty()) {
				conn = session.connection();

				//String query = "insert into login_details(id,sfid,ip_address,login_date,status) values(?,?,?,to_date(sysdate,'dd-MM-yyyy'),24)";

				String query = "insert into login_details(id,sfid,ip_address,login_date,status,BROWSER_NAME) values(?,?,?,systimestamp,24,?)";

				pst = conn.prepareStatement(query);
				pst.setString(1, user.getId());
				pst.setString(2, (list.get(0)).toString());
				pst.setString(3, user.getIpAddress());
				pst.setString(4,user.getBrowser());
				pst.executeUpdate();
                 session.flush();
				user.setLoginList(list);
				user.setSfid((list.get(0)).toString());
				
				//commented by bkr 04 aug 2016 due to ravi sir requirement

			/*	String getDetails = "select rtrim(name,', ') name from (select name_in_service_book||', '||dm.name||', '||(select rtrim (xmlagg (xmlelement (e, roleName || ',')order by ord).extract ('//text()'), ',')  from ( "
						+ "select ori.org_role_name roleName,1 ord from org_role_instance ori,emp_role_mapping erm,emp_master emp where ori.status=1  "
						+ "and emp.sfid=? and emp.status=1 and emp.office_id=ori.org_role_id and erm.status=1 and erm.sfid=emp.sfid and erm.org_role_id=ori.org_role_id "
						+ "union  "
						+ "select ori.org_role_name roleName,2 ord from org_role_instance ori,emp_role_mapping erm, emp_master emp "
						+ "where emp.status=1 and ori.status=1 and erm.status=1 and erm.sfid=emp.sfid  "
						+ "and emp.sfid=? and ori.org_role_id=erm.org_role_id and ori.org_role_id!=emp.office_id) "
						+ ") name from emp_master emp,designation_master dm " + "where sfid=? and dm.status=1 and emp.status=1 and emp.designation_id=dm.id)";
				*/
				
				//added by bkr due to ravi sir requirement
				String getDetails = " SELECT name_in_service_book name  FROM emp_master  WHERE sfid =? ";
				
				
				Query empDetails = session.createSQLQuery(getDetails);
				empDetails.setString(0, user.getSfid());
				//commented by bkr 04 aug 2016 due to ravi sir requirement
				//empDetails.setString(1, user.getSfid());
				//empDetails.setString(2, user.getSfid());
				user.setName((String) empDetails.uniqueResult());

				HashMap<String, String> roleLinksMap = new HashMap<String, String>();
				HashMap<String, String> checkParent = new HashMap<String, String>();
				con = session.connection();
				String getRoles = "select distinct mm.name,mm.status,mm.parent_id,rm.name rolename,mm.controller_class roleAction  from menu_links_master mm,role_links_mapping rlm,role_master rm "
						+ "where rlm.link_id=mm.id and mm.status=1 and rlm.status=1 and rlm.role_id=rm.id and rm.status=1 "
						+ "and rm.id in (select arm.role_id from application_role_mapping arm where arm.sfid=? and arm.status=1)";
				ps = con.prepareStatement(getRoles);
				ps.setString(1, (list.get(0)).toString());
				rsq = ps.executeQuery();
				ArrayList<String> rolelist = new ArrayList<String>();
				ArrayList<String> roleActionClass = new ArrayList<String>();
				while (rsq.next()) {
					if (!roleLinksMap.containsKey(rsq.getString("name")) || (roleLinksMap.get(rsq.getString("name")) != null && roleLinksMap.get(rsq.getString("name")).equals("0"))) {
						roleLinksMap.put(rsq.getString("name"), rsq.getString("status"));
						checkParent.put(rsq.getString("parent_id"), "");
						roleActionClass.add(rsq.getString("roleAction"));
					}
					if (!rolelist.contains(rsq.getString("rolename")))
						rolelist.add(rsq.getString("rolename"));
				}

				// to display mainlinks
				String checkMainLinks = "select id,name from menu_links_master where status=1 and parent_id=0";
				stmt = con.createStatement();
				rsq = stmt.executeQuery(checkMainLinks);
				while (rsq.next()) {
					if (checkParent.containsKey(rsq.getString("id"))) {
						roleLinksMap.put(rsq.getString("name"), "1");
					}
				}

				// verify Leave link should display to a perticular role
				String checkRole = "select org_role_id from emp_role_mapping where sfid=? and org_role_id in (?)";
				ps = con.prepareStatement(checkRole);
				ps.setString(1, user.getSfid());
				ps.setString(2, CPSConstants.VERIFYLEAVESLINK);
				rsq = ps.executeQuery();
				if (rsq.next()) {
					roleLinksMap.put("verifyLeaves", "1");
				}

				user.setMenuLinks(roleLinksMap);
				user.setRoleList(rolelist);
				user.setRoleActions(roleActionClass);
			}
		   
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rsq);
			
		}
		return user;
	}

	public String saveLogOutDetails(LoginBean loginBean) throws Exception {
		PreparedStatement pst = null;
		Connection conn = null;
		String message = null;
		Session session = null;
		try {
			log.debug("SQLLoginDAO saveLogOutDetails()>>>>>>>>>start");
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			conn = session.connection();

			//String query = "insert into login_details values(?,?,?,to_date(sysdate,'dd-MM-yyyy'),25)";

			String query = "insert into login_details values(?,?,?,systimestamp,25,?)";

			pst = conn.prepareStatement(query);
			pst.setString(1, loginBean.getId());
			pst.setString(2, loginBean.getSfid());
			pst.setString(3, loginBean.getIpAddress());
			pst.setString(4,loginBean.getBrowser());
			pst.executeUpdate();
			message = CPSConstants.SUCCESS;
			log.debug("SQLLoginDAO saveLogOutDetails()>>>>>>>>>end");
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;

		} finally {
			ConnectionUtil.closeConnection(session, pst, null);
		}
		return message;
	}
}
