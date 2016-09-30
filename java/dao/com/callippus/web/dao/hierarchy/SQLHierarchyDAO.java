package com.callippus.web.dao.hierarchy;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.ApplicationRoleMappingDTO;
import com.callippus.web.beans.dto.DepartmentsDTO;
import com.callippus.web.beans.dto.DeptHierarchyDTO;
import com.callippus.web.beans.dto.EmpRoleMappingDTO;
import com.callippus.web.beans.dto.EmployeeRoleDTO;
import com.callippus.web.beans.dto.HierarchyDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.OrgInstanceDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.hierarchy.HierarchyBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.ConnectionUtil;

@Service
public class SQLHierarchyDAO implements IHierarchyDAO, Serializable {

	private static final long serialVersionUID = -8156682243652623866L;

	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	public HierarchyBean getHierarchyLevelList(HierarchyBean hierarchyBean) throws Exception {
		Session session = null;
		Statement stmt = null;
		ResultSet rsq = null;
		Connection con = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			ArrayList<HierarchyDTO> levelHierarchyList = new ArrayList<HierarchyDTO>();
			if (CPSUtils.compareStrings(hierarchyBean.getType(), CPSConstants.ROLE)) {
				String getIntialHierarchy = "select oh.role_hierarchy_id,oh.role_hierarchy_name roleName,'' parentName,0 parent_role_hierarchy_id from org_role_hierarchy oh where oh.status=1 and oh.parent_role_hierarchy_id=0";
				stmt = con.createStatement();
				rsq = stmt.executeQuery(getIntialHierarchy);
				while (rsq.next()) {
					HierarchyDTO hierDTO = new HierarchyDTO();
					hierDTO.setRoleID(rsq.getInt("role_hierarchy_id"));
					hierDTO.setRoleName(rsq.getString("roleName"));
					hierDTO.setParentName(rsq.getString("parentName"));
					hierDTO.setParentID(rsq.getInt("parent_role_hierarchy_id"));
					levelHierarchyList.add(hierDTO);
				}
				String sql = "select oh.role_hierarchy_id,oh.role_hierarchy_name roleName,poh.role_hierarchy_name parentName,oh.parent_role_hierarchy_id from org_role_hierarchy oh,org_role_hierarchy poh "
						+ "where oh.parent_role_hierarchy_id = poh.role_hierarchy_id and oh.status=1 and poh.status=1 order by oh.role_hierarchy_id";
				stmt = con.createStatement();
				rsq = stmt.executeQuery(sql);

				while (rsq.next()) {
					HierarchyDTO hierDTO = new HierarchyDTO();
					hierDTO.setRoleID(rsq.getInt("role_hierarchy_id"));
					hierDTO.setRoleName(rsq.getString("roleName"));
					hierDTO.setParentName(rsq.getString("parentName"));
					hierDTO.setParentID(rsq.getInt("parent_role_hierarchy_id"));
					hierDTO.setType("role");
					levelHierarchyList.add(hierDTO);
				}
			} else {
				String getIntialHierarchy = "select dh.dept_hierarchy_id,dh.dept_hierarchy_name deptName,'' parentName,0 parent_dept_hierarchy_id from departments_hierarchy dh where dh.status=1 and dh.parent_dept_hierarchy_id=0";
				stmt = con.createStatement();
				rsq = stmt.executeQuery(getIntialHierarchy);
				while (rsq.next()) {
					HierarchyDTO hierDTO = new HierarchyDTO();
					hierDTO.setRoleID(rsq.getInt("dept_hierarchy_id"));
					hierDTO.setRoleName(rsq.getString("deptName"));
					hierDTO.setParentName(rsq.getString("parentName"));
					hierDTO.setParentID(rsq.getInt("parent_dept_hierarchy_id"));
					levelHierarchyList.add(hierDTO);
				}
				String sql = "select dh.dept_hierarchy_id,dh.dept_hierarchy_name deptName,pdh.dept_hierarchy_name parentName,dh.parent_dept_hierarchy_id "
						+ "from departments_hierarchy dh,departments_hierarchy pdh where dh.parent_dept_hierarchy_id=pdh.dept_hierarchy_id and dh.status=1 and pdh.status=1 "
						+ "order by dh.dept_hierarchy_id";
				stmt = con.createStatement();
				rsq = stmt.executeQuery(sql);

				while (rsq.next()) {
					HierarchyDTO hierDTO = new HierarchyDTO();
					hierDTO.setRoleID(rsq.getInt("dept_hierarchy_id"));
					hierDTO.setRoleName(rsq.getString("deptName"));
					hierDTO.setParentName(rsq.getString("parentName"));
					hierDTO.setParentID(rsq.getInt("parent_dept_hierarchy_id"));
					hierDTO.setType("department");
					levelHierarchyList.add(hierDTO);
				}
			}
			hierarchyBean.setHierarchyDisplayTable(levelHierarchyList);
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, stmt, rsq);
		}
		return hierarchyBean;
	}

	public String checkLevel(HierarchyBean hierarchyBean) throws Exception {
		Session session = null;
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rsq = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			StringBuffer sb = new StringBuffer();
			if (CPSUtils.compareStrings(hierarchyBean.getType(), CPSConstants.ROLE)) {
				sb.append("select role_hierarchy_id from org_role_hierarchy where upper(role_hierarchy_name)=? and status=1");
				if (!CPSUtils.isNullOrEmpty(hierarchyBean.getId())) {
					sb.append(" and role_hierarchy_id!=" + hierarchyBean.getId());
				}
			} else {
				sb.append("select dept_hierarchy_id from departments_hierarchy where upper(dept_hierarchy_name)=? and status=1");
				if (!CPSUtils.isNullOrEmpty(hierarchyBean.getId())) {
					sb.append(" and dept_hierarchy_id!=" + hierarchyBean.getId());
				}
			}
			ps = con.prepareStatement(sb.toString());
			ps.setString(1, hierarchyBean.getNewLevel().toUpperCase());
			rsq = ps.executeQuery();
			if (!rsq.next()) {
				message = CPSConstants.SUCCESS;
			} else {
				message = CPSConstants.DUPLICATE;
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;

		} finally {
			ConnectionUtil.closeConnection(session, ps, rsq);
		}
		return message;
	}

	public String createHierarchyLevel(HierarchyBean hierarchyBean) throws Exception {
		Session session = null;
		PreparedStatement ps = null;
		Connection con = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			// update the chain
			/*
			 * if (CPSUtils.compareStrings(hierarchyBean.getType(), CPSConstants.ROLE)) { sql = "update org_role_hierarchy set parent_role_hierarchy_id=? where parent_role_hierarchy_id=?"; } else {
			 * sql = "update departments_hierarchy set parent_dept_hierarchy_id=? where parent_dept_hierarchy_id=?"; } ps = con.prepareStatement(sql); ps.setString(1, hierarchyBean.getId());
			 * ps.setString(2, hierarchyBean.getParent()); ps.executeUpdate();
			 */

			StringBuffer sb = new StringBuffer();
			sb.append("insert into ");
			if (CPSUtils.compareStrings(hierarchyBean.getType(), CPSConstants.ROLE)) {
				sb.append("org_role_hierarchy(role_hierarchy_id,role_hierarchy_name,parent_role_hierarchy_id,status,creation_date,last_modified_date) ");
			} else {
				sb.append("departments_hierarchy(dept_hierarchy_id,dept_hierarchy_name,parent_dept_hierarchy_id,status,creation_date,last_modified_date) ");
			}

			sb.append("values(?,?,?,1,sysdate,sysdate)");

			ps = con.prepareStatement(sb.toString());
			ps.setString(1, hierarchyBean.getId());
			ps.setString(2, hierarchyBean.getNewLevel());
			ps.setString(3, hierarchyBean.getParent());
			ps.executeUpdate();
			message = CPSConstants.SUCCESS;

		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;

		} finally {
			ConnectionUtil.closeConnection(session, ps, null);
		}
		return message;
	}

	public String updateHierarchyLevel(HierarchyBean hierarchyBean) throws Exception {
		Session session = null;
		PreparedStatement ps = null;
		Connection con = null;
		String message = "";
		String sql = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			// first update the down tree to its parent
			/*
			 * if (CPSUtils.compareStrings(hierarchyBean.getType(), CPSConstants.ROLE)) { sql =
			 * "update org_role_hierarchy set parent_role_hierarchy_id=(select parent_role_hierarchy_id from org_role_hierarchy where role_hierarchy_id=?) " +
			 * "where status=1 and parent_role_hierarchy_id=?"; } else { sql =
			 * "update departments_hierarchy set parent_dept_hierarchy_id=(select parent_dept_hierarchy_id from departments_hierarchy where dept_hierarchy_id=?) " +
			 * "where status=1 and parent_dept_hierarchy_id=?"; } ps = con.prepareStatement(sql); ps.setString(1, hierarchyBean.getId()); ps.setString(2, hierarchyBean.getId()); ps.executeUpdate();
			 */

			// second update the upper tree
			/*
			 * if (CPSUtils.compareStrings(hierarchyBean.getType(), CPSConstants.ROLE)) { sql =
			 * "update org_role_hierarchy set parent_role_hierarchy_id=? where parent_role_hierarchy_id=? and status=1"; } else { sql =
			 * "update departments_hierarchy set parent_dept_hierarchy_id=? where parent_dept_hierarchy_id=? and status=1"; } ps = con.prepareStatement(sql); ps.setString(1, hierarchyBean.getId());
			 * ps.setString(2, hierarchyBean.getParent()); ps.executeUpdate();
			 */

			if (CPSUtils.compareStrings(hierarchyBean.getType(), CPSConstants.ROLE)) {
				sql = "update org_role_hierarchy set role_hierarchy_name=?,parent_role_hierarchy_id=?,last_modified_date=sysdate where role_hierarchy_id=?";
			} else {
				sql = "update departments_hierarchy set dept_hierarchy_name=?,parent_dept_hierarchy_id=?,last_modified_date=sysdate where dept_hierarchy_id=?";
			}
			ps = con.prepareStatement(sql);
			ps.setString(1, hierarchyBean.getNewLevel());
			ps.setString(2, hierarchyBean.getParent());
			ps.setString(3, hierarchyBean.getId());
			ps.executeUpdate();
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;

		} finally {
			ConnectionUtil.closeConnection(session, ps, null);
		}
		return message;
	}

	public String deleteHierarchyLevel(HierarchyBean hierarchyBean) throws Exception {
		Session session = null;
		PreparedStatement ps = null;
		Connection con = null;
		String message = "";
		String sql = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			// first move the current level down tree
			if (CPSUtils.compareStrings(hierarchyBean.getType(), CPSConstants.ROLE)) {
				sql = "update org_role_hierarchy set parent_role_hierarchy_id=(select parent_role_hierarchy_id from org_role_hierarchy where role_hierarchy_id=?) "
						+ "where status=1 and parent_role_hierarchy_id=?";
			} else {
				sql = "update departments_hierarchy set parent_dept_hierarchy_id=(select parent_dept_hierarchy_id from departments_hierarchy where dept_hierarchy_id=?) "
						+ "where status=1 and parent_dept_hierarchy_id=?";
			}
			ps = con.prepareStatement(sql);
			ps.setString(1, hierarchyBean.getId());
			ps.setString(2, hierarchyBean.getId());
			ps.executeUpdate();

			if (CPSUtils.compareStrings(hierarchyBean.getType(), CPSConstants.ROLE)) {
				sql = "update org_role_hierarchy set status=0,last_modified_date=sysdate where role_hierarchy_id=?";
			} else {
				sql = "update departments_hierarchy set status=0,last_modified_date=sysdate where dept_hierarchy_id=?";
			}
			ps = con.prepareStatement(sql);
			ps.setString(1, hierarchyBean.getId());
			ps.executeUpdate();

			message = CPSConstants.SUCCESS;

		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;

		} finally {
			ConnectionUtil.closeConnection(session, ps, null);
		}
		return message;
	}

	public HierarchyBean getHierarchyNodeList(HierarchyBean hierarchyBean, List<Object> rolelist) throws Exception {
		Session session = null;
		Statement stmt = null;
		ResultSet rsq = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		ArrayList<HierarchyDTO> nodeHierarchyList = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String instanceType = hierarchyBean.getType();
			if (rolelist.contains("ROLE_ADMIN") || rolelist.contains("ROLE_SUPERADMIN")) {
				String sql = "";
				if (CPSUtils.compareStrings(instanceType, "Logical")) {
					sql = "select ori.org_role_id nodeid,ori.org_role_name nodename,orh.role_hierarchy_id levelid,"
							+ "orh.role_hierarchy_name levelname,ori.parent_org_role_id nodeparentid,pori.org_role_name nodeparentname,ori.department_id from org_role_hierarchy orh,org_role_instance ori,org_role_instance pori "
							+ "where ori.role_hierarchy_id=orh.role_hierarchy_id and ori.parent_org_role_id=pori.org_role_id and ori.status=1 and pori.status=1 and orh.status=1 order by ori.org_role_id";
				} else {
					sql = "select dept.department_id nodeid,dept.department_name nodename,drh.dept_hierarchy_id levelid,drh.dept_hierarchy_name levelname,dept.parent_department_id nodeparentid,"
							+ " pdept.department_name nodeparentname,dept.department_type_id dtypeid,(select d.name from department_type_master d where d.id=dept.department_type_id ) departmentTypeName from departments_hierarchy drh,departments_master dept,departments_master pdept,department_type_master dtype where dept.dept_hierarchy_id=drh.dept_hierarchy_id"
							+ " and dept.parent_department_id=pdept.department_id and dept.status=1 and pdept.status=1 and drh.status=1 and dept.department_type_id=dtype.id order by dept.department_id";
				}

				stmt = con.createStatement();
				rsq = stmt.executeQuery(sql);
				nodeHierarchyList = new ArrayList<HierarchyDTO>();
				while (rsq.next()) {
					HierarchyDTO hierDTO = new HierarchyDTO();
					hierDTO.setNodeID(rsq.getString("nodeid"));
					hierDTO.setNodeName(rsq.getString("nodename"));
					hierDTO.setLevelID(rsq.getString("levelid"));
					hierDTO.setLevelName(rsq.getString("levelname"));
					hierDTO.setNodeParentID(rsq.getString("nodeparentid"));
					hierDTO.setNodeParentName(rsq.getString("nodeparentname"));
					if (CPSUtils.compareStrings(instanceType, "Physical")) {
						hierDTO.setDepartmentType(rsq.getString("dtypeid"));
						hierDTO.setDepartmentTypeName(rsq.getString("departmentTypeName"));
					}
					if (CPSUtils.compareStrings(instanceType, "Logical"))
						hierDTO.setDepartmentId(rsq.getString("department_id"));
					nodeHierarchyList.add(hierDTO);

				}
			} else {
				List<Object> roleslist = null;
				if (CPSUtils.compareStrings(instanceType, "Logical")) {
					roleslist = getMultipleroles(hierarchyBean.getSfid());
				} else {
					roleslist = getMultipleDepartments(hierarchyBean.getSfid());
				}
				if (CPSUtils.checkList(roleslist)) {
					nodeHierarchyList = new ArrayList<HierarchyDTO>();
					for (int i = 0; i < roleslist.size(); i++) {
						String sql = "";
						if (CPSUtils.compareStrings(instanceType, "Logical")) {
							sql = "select org.org_role_id nodeid,org.org_role_name nodename,orh.role_hierarchy_id levelid,orh.role_hierarchy_name levelname,org.parent_org_role_id nodeparentid,pori.org_role_name nodeparentname,org.department_id "
									+ " from org_role_instance org,org_role_hierarchy orh,org_role_instance pori where org.status=1 and org.role_hierarchy_id=orh.role_hierarchy_id and org.parent_org_role_id=pori.org_role_id and orh.status=1 and pori.status=1 "
									+ " start with org.parent_org_role_id=? connect by org.parent_org_role_id=prior org.org_role_id order by org.org_role_name";
						} else {
							sql = "select org.department_id nodeid,org.department_name nodename,orh.dept_hierarchy_id levelid,orh.dept_hierarchy_name levelname,org.parent_department_id nodeparentid,pori.department_name nodeparentname "
									+ " from departments_master org,departments_hierarchy orh,departments_master pori where org.status=1 and org.dept_hierarchy_id=orh.dept_hierarchy_id and org.parent_department_id=pori.department_id and orh.status=1 and pori.status=1 "
									+ " start with org.parent_department_id=? connect by org.parent_department_id=prior org.department_id order by org.department_name";
						}

						pstmt = con.prepareStatement(sql);
						pstmt.setString(1, roleslist.get(i).toString());
						rs = pstmt.executeQuery();
						while (rs.next()) {
							boolean flag = true;
							for (int j = 0; j < nodeHierarchyList.size(); j++) {
								HierarchyDTO dto = (HierarchyDTO) nodeHierarchyList.get(j);
								if (CPSUtils.compareStrings(rs.getString("nodeid"), String.valueOf(dto.getNodeID()))) {
									flag = false;
									break;
								}
							}
							if (flag) {
								HierarchyDTO hierDTO = new HierarchyDTO();
								hierDTO.setNodeID(rs.getString("nodeid"));
								hierDTO.setNodeName(rs.getString("nodename"));
								hierDTO.setLevelID(rs.getString("levelid"));
								hierDTO.setLevelName(rs.getString("levelname"));
								hierDTO.setNodeParentID(rs.getString("nodeparentid"));
								hierDTO.setNodeParentName(rs.getString("nodeparentname"));
								if (CPSUtils.compareStrings(instanceType, "Logical"))
									hierDTO.setDepartmentId(rs.getString("department_id"));
								nodeHierarchyList.add(hierDTO);
							}

						}
					}
				}
			}
			hierarchyBean.setHierarchyDisplayTable(nodeHierarchyList);
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, stmt, rsq);
			ConnectionUtil.closeConnection(null, pstmt, rs);
		}
		return hierarchyBean;
	}

	public ArrayList<HierarchyDTO> getNodeInstanceParentList(HierarchyBean hierarchyBean, List<Object> rolelist) throws Exception {
		Session session = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		Connection con = null;
		ArrayList<HierarchyDTO> nodeInstanceParentList = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String instanceType = hierarchyBean.getType();
			con = session.connection();
			String sql = "";
			if (rolelist.contains("ROLE_ADMIN") || rolelist.contains("ROLE_SUPERADMIN")) {
				if (CPSUtils.compareStrings(instanceType, "Logical")) {
					sql = "select org_role_id,org_role_name from org_role_instance org where status=1 and org.role_hierarchy_id<? start with org_role_id=1 connect by parent_org_role_id=prior org_role_id order by org_role_name";
				} else {
					sql = "select department_id as org_role_id,department_name as org_role_name from departments_master org where status=1 and org.dept_hierarchy_id<? start with department_id=1 connect by parent_department_id=prior department_id order by department_name";
				}
				ps = con.prepareStatement(sql);
				ps.setString(1, hierarchyBean.getId());
				rsq = ps.executeQuery();

				nodeInstanceParentList = new ArrayList<HierarchyDTO>();
				while (rsq.next()) {
					HierarchyDTO hierDTO = new HierarchyDTO();
					hierDTO.setRoleID(rsq.getInt("org_role_id"));
					hierDTO.setRoleName(rsq.getString("org_role_name"));
					nodeInstanceParentList.add(hierDTO);
				}
			} else {
				List<Object> roleslist = null;
				if (CPSUtils.compareStrings(instanceType, "Logical")) {
					roleslist = getMultipleroles(hierarchyBean.getSfid());
				} else {
					roleslist = getMultipleDepartments(hierarchyBean.getSfid());
				}
				if (CPSUtils.checkList(roleslist)) {
					nodeInstanceParentList = new ArrayList<HierarchyDTO>();
					for (int i = 0; i < roleslist.size(); i++) {
						if (CPSUtils.compareStrings(instanceType, "Logical")) {
							sql = "select org_role_id,org_role_name from org_role_instance org where status=1 and org.role_hierarchy_id<? start with org_role_id=? connect by parent_org_role_id=prior org_role_id order by org_role_name";
						} else {
							sql = "select department_id as org_role_id,department_name as org_role_name from departments_master org where status=1 and org.dept_hierarchy_id<? start with department_id=? connect by parent_department_id=prior department_id order by department_name";
						}
						ps = con.prepareStatement(sql);
						ps.setString(1, hierarchyBean.getId());
						ps.setString(2, roleslist.get(i).toString());
						rsq = ps.executeQuery();
						while (rsq.next()) {
							boolean flag = true;
							for (int j = 0; j < nodeInstanceParentList.size(); j++) {
								HierarchyDTO dto = (HierarchyDTO) nodeInstanceParentList.get(j);
								if (CPSUtils.compareStrings(rsq.getString("org_role_id"), String.valueOf(dto.getRoleID()))) {
									flag = false;
									break;
								}
							}
							if (flag) {
								HierarchyDTO hierDTO = new HierarchyDTO();
								hierDTO.setRoleID(rsq.getInt("org_role_id"));
								hierDTO.setRoleName(rsq.getString("org_role_name"));
								nodeInstanceParentList.add(hierDTO);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rsq);
		}
		return nodeInstanceParentList;
	}

	public String checkNodeLevel(HierarchyBean hierarchyBean) throws Exception {
		Session session = null;
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rsq = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			StringBuffer sb = new StringBuffer();
			if (CPSUtils.compareStrings(hierarchyBean.getType(), "Logical")) {
				sb.append("select role_hierarchy_id from org_role_instance where upper(org_role_name)=? and status=1 and parent_org_role_id=? and role_hierarchy_id=?");
			} else {
				sb.append("select dept_hierarchy_id from departments_master where upper(department_name)=? and status=1 and parent_department_id=? and dept_hierarchy_id=?");
			}
			if (!CPSUtils.isNullOrEmpty(hierarchyBean.getId())) {
				if (CPSUtils.compareStrings(hierarchyBean.getType(), "Logical"))
					sb.append(" and org_role_id!=" + hierarchyBean.getId());
				else
					sb.append(" and department_id!=" + hierarchyBean.getId());
			}

			ps = con.prepareStatement(sb.toString());
			ps.setString(1, hierarchyBean.getNodeNames().toUpperCase());
			ps.setString(2, hierarchyBean.getNodeParentName());
			ps.setString(3, hierarchyBean.getLevelName());
			rsq = ps.executeQuery();
			if (!rsq.next()) {
				message = CPSConstants.SUCCESS;
			} else {
				message = CPSConstants.DUPLICATE;
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rsq);
		}
		return message;
	}

	public String createRoleInstance(HierarchyBean hierarchyBean) throws Exception {
		Session session = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		Connection con = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			String psql = "select count(*) from org_role_instance where department_id=?";
			ps1 = con.prepareStatement(psql);
			ps1.setString(1, hierarchyBean.getDepartmentId());
			rs = ps1.executeQuery();
			boolean flag = false;
			if (rs.next()) {
				flag = true;
			}

			String sql = "insert into org_role_instance(org_role_id,role_hierarchy_id,org_role_name,parent_org_role_id,status,creation_date,last_modified_date,department_id,is_head) values(?,?,?,?,1,sysdate,sysdate,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, hierarchyBean.getId());
			ps.setString(2, hierarchyBean.getLevelName());
			ps.setString(3, hierarchyBean.getNodeNames());
			ps.setString(4, hierarchyBean.getNodeParentName());
			ps.setString(5, hierarchyBean.getDepartmentId());
			if (flag)
				ps.setString(6, "0");
			else
				ps.setString(6, "1");
			ps.executeUpdate();

			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(null, ps1, rs);
			ConnectionUtil.closeConnection(session, ps, null);
		}
		return message;
	}

	public String saveOrgPhysicalInstance(DepartmentsDTO hierarchyBean) throws Exception {
		Session session = null;
		PreparedStatement ps = null;
		Connection con = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			//Transaction tx = session.beginTransaction();
			session.saveOrUpdate(hierarchyBean);
			session.flush();//tx.commit() ;
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, null);
		}
		return message;
	}

	public String updateRoleInstance(HierarchyBean hierarchyBean) throws Exception {
		Session session = null;
		PreparedStatement ps = null;
		Connection con = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String sql = "update org_role_instance set role_hierarchy_id=?,org_role_name=?,parent_org_role_id=?,department_id=?,last_modified_date=sysdate where org_role_id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, hierarchyBean.getLevelName());
			ps.setString(2, hierarchyBean.getNodeNames());
			ps.setString(3, hierarchyBean.getNodeParentName());
			ps.setString(4, hierarchyBean.getDepartmentId());
			ps.setString(5, hierarchyBean.getId());
			ps.executeUpdate();
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, null);
		}
		return message;
	}

	public String deleteHierarchyNode(String nodeID, String instanceType) throws Exception {
		Session session = null;
		PreparedStatement ps = null;
		Connection con = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String sql = "";
			if (CPSUtils.compareStrings(instanceType, "Logical"))
				sql = "update org_role_instance set status=0,last_modified_date=sysdate where org_role_id=?";
			else
				sql = "update departments_master set status=0,last_modified_date=sysdate where department_id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, nodeID);
			ps.executeUpdate();

			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, null);
		}
		return message;
	}

	public ArrayList<KeyValueDTO> getInternalDivisionsList(String sfID) throws Exception {
		Session session = null;
		PreparedStatement ps = null;
		Connection con = null;
		ArrayList<KeyValueDTO> divisionList = null;
		ResultSet rsq = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			divisionList = new ArrayList<KeyValueDTO>();
			String sql = "select distinct erm.internal_division,ori.department_id from emp_role_mapping erm,emp_master emp,org_role_instance ori "
					+ "where erm.status=1 and erm.sfid!=? and erm.internal_division is not null and emp.status=1 and emp.sfid=erm.sfid and ori.org_role_id=emp.office_id and ori.status=1 "
					+ "start with erm.sfid=? connect by erm.parent_id = prior erm.sfid";
			ps = con.prepareStatement(sql);
			ps.setString(1, sfID);
			ps.setString(2, sfID);
			rsq = ps.executeQuery();
			int i = 1;
			while (rsq.next()) {
				KeyValueDTO keyVal = new KeyValueDTO();
				keyVal.setKey(String.valueOf(i));
				keyVal.setName(rsq.getString("internal_division"));
				keyVal.setId(rsq.getInt("department_id"));
				divisionList.add(keyVal);
				i++;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rsq);
		}
		return divisionList;
	}

	public ArrayList<KeyValueDTO> getInternalRolesList(String sfID) throws Exception {
		Session session = null;
		PreparedStatement ps = null;
		Connection con = null;
		ArrayList<KeyValueDTO> rolesList = null;
		ResultSet rsq = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			rolesList = new ArrayList<KeyValueDTO>();
			String sql = "select distinct erm.internal_role,ori.department_id from emp_role_mapping erm,emp_master emp,org_role_instance ori "
					+ "where erm.status=1 and erm.sfid!=? and erm.internal_role not in ('Head','Employee') and emp.status=1 and erm.sfid=emp.sfid and ori.status=1 and ori.org_role_id=emp.office_id "
					+ "start with erm.sfid=? connect by erm.parent_id = prior erm.sfid";
			ps = con.prepareStatement(sql);
			ps.setString(1, sfID);
			ps.setString(2, sfID);
			rsq = ps.executeQuery();
			int i = 1;
			while (rsq.next()) {
				KeyValueDTO keyVal = new KeyValueDTO();
				keyVal.setKey(String.valueOf(i));
				keyVal.setName(rsq.getString("internal_role"));
				keyVal.setId(rsq.getInt("department_id"));
				rolesList.add(keyVal);
				i++;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rsq);
		}
		return rolesList;
	}

	public String submitInternalRoleMapping(HierarchyBean hierarchyBean) throws Exception {
		Session session = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		Connection con = null;
		String message = null;
		ResultSet rsq = null;
		ResultSet rsq1 = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			String checkEmployee = "select * from emp_role_mapping where sfid=? and status=1";
			ps = con.prepareStatement(checkEmployee);
			ps.setString(1, hierarchyBean.getMapSFID());
			rsq = ps.executeQuery();

			if (!rsq.next()) {
				// Employee not mapped
				hierarchyBean.setParent(hierarchyBean.getSfid());
				if (!CPSUtils.compareStrings(CPSConstants.NEW, hierarchyBean.getInternalDivisions())) {
					// employee mapped to internal division, so we need to get
					// that division head
					String getParentID = "select sfid from emp_role_mapping where upper(internal_role) = upper('Head') and status=1 and upper(internal_division)=? and parent_id=?";
					ps1 = con.prepareStatement(getParentID);
					ps1.setString(1, hierarchyBean.getIntDivision().toUpperCase());
					ps1.setString(2, hierarchyBean.getSfid());
					rsq1 = ps1.executeQuery();
					if (rsq1.next()) {
						hierarchyBean.setParent(rsq1.getString("sfid"));
					}
				}
				String insertInternalRole = "insert into emp_role_mapping(id,sfid,org_role_id,internal_division,internal_role,parent_id,status,creation_date,last_modified_date) "
						+ "values(?,?,?,?,?,?,1,sysdate,sysdate)";
				ps = con.prepareStatement(insertInternalRole);
				ps.setString(1, hierarchyBean.getId());
				ps.setString(2, hierarchyBean.getMapSFID());
				ps.setString(3, null);
				ps.setString(4, hierarchyBean.getIntDivision());
				ps.setString(5, hierarchyBean.getIntRole());
				ps.setString(6, hierarchyBean.getParent());
				ps.executeUpdate();
				message = CPSConstants.SUCCESS;

			} else {
				// employee already mapped
				message = CPSConstants.EMPDUPLICATE;
			}

		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rsq);
			ConnectionUtil.closeConnection(null, ps1, rsq1);
		}
		return message;
	}

	public String submitOrganizationRoleMapping(HierarchyBean hierarchyBean) throws Exception {
		Session session = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		Connection con = null;
		String message = null;
		ResultSet rsq = null;
		ResultSet rs = null;
		ResultSet childrs = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			String checkMultipleRoles = "select sfid from emp_role_mapping where sfid=? and status=1 and upper(internal_role)=upper('Head')";
			ps = con.prepareStatement(checkMultipleRoles);
			ps.setString(1, hierarchyBean.getMapSFID());
			rsq = ps.executeQuery();
			boolean flag = true;

			if (!CPSUtils.isNull(hierarchyBean.getType()) && CPSUtils.compareStrings(hierarchyBean.getType(), "updatehead")) {
				String sfid = "";
				String instanceId = "";
				String sql = "select sfid,org_role_id from emp_role_mapping where id=? and status=1";
				ps2 = con.prepareStatement(sql);
				ps2.setString(1, hierarchyBean.getId());
				rs = ps2.executeQuery();
				if (rs.next()) {
					sfid = rs.getString("sfid");
					instanceId = rs.getString("org_role_id");
				}
				String message1 = checkUserSpecificConfiguration(sfid, instanceId);
				if (CPSUtils.compareStrings(CPSConstants.SUCCESS, message1)) {
					deleteHeadMapping(sfid, instanceId);
					String childs = "select erm.sfid from emp_role_mapping erm,emp_master emp where parent_id=(select sfid from emp_role_mapping where id=? and status=1) and emp.sfid=erm.sfid"
							+ " and erm.parent_role_id=(select org_role_id from emp_role_mapping where id=? and status=1) and erm.status=1 and emp.status=1";
					ps4 = con.prepareStatement(childs);
					ps4.setString(1, hierarchyBean.getId());
					ps4.setString(2, hierarchyBean.getId());
					childrs = ps4.executeQuery();

					while (childrs.next()) {
						String childinstance = "update emp_role_mapping set parent_id=? where sfid=?";
						ps3 = con.prepareStatement(childinstance);
						ps3.setString(1, hierarchyBean.getMapSFID());
						ps3.setString(2, childrs.getString("sfid"));
						ps3.executeUpdate();
						ps3.close();
					}
				} else
					flag = false;

			}
			if (flag) {
				String insertEmployee = "insert into emp_role_mapping(id,sfid,org_role_id,internal_division,internal_role,parent_id,status,creation_date,last_modified_date,department_id) "
						+ "values(?,?,?,'','Head',?,1,sysdate,sysdate,?)";
				ps1 = con.prepareStatement(insertEmployee);
				if (!CPSUtils.isNull(hierarchyBean.getType()) && CPSUtils.compareStrings(hierarchyBean.getType(), "updatehead"))
					ps1.setString(1, hierarchyBean.getManageID());
				else
					ps1.setString(1, hierarchyBean.getId());
				ps1.setString(2, hierarchyBean.getMapSFID().toUpperCase());
				ps1.setString(3, hierarchyBean.getSelectedRole());
				ps1.setString(4, null);
				ps1.setString(5, hierarchyBean.getSelectedRole());
				ps1.executeUpdate();

				String directorate_id = getRoleInstanceValues(hierarchyBean.getSelectedRole());
				String sql = "update emp_master set office_id=?, directorate_id=?  where sfid=?";
				// if (rsq.next()) {
				if (CPSUtils.compareStrings(hierarchyBean.getDefaultFlag(), CPSConstants.YES)) {
					updateOfficeId(hierarchyBean.getSelectedRole(), directorate_id, hierarchyBean.getMapSFID());
				}
				// } else {
				// ps2 = con.prepareStatement(sql);
				// ps2.setString(1, hierarchyBean.getSelectedRole());
				// ps2.setString(2, directorate_id);
				// ps2.setString(3, hierarchyBean.getMapSFID());
				// ps2.executeUpdate();
				// }
				message = CPSConstants.SUCCESS;
			} else
				message = CPSConstants.USERSPECIFICEXISTS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rsq);
			ConnectionUtil.closeConnection(null, ps1, null);
			ConnectionUtil.closeConnection(null, ps2, rs);
			ConnectionUtil.closeConnection(null, ps3, null);
		}
		return message;
	}

	public String updateInternalRoleMapping(HierarchyBean hierarchyBean) throws Exception {
		Session session = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		Connection con = null;
		String message = null;
		ResultSet rsq = null;
		ResultSet rsq1 = null;
		String sfid = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			if (CPSUtils.compareStrings(hierarchyBean.getIntDivision(), "Select")) {
				hierarchyBean.setIntDivision(null);
			}

			else if (!CPSUtils.compareStrings(CPSConstants.NEW, hierarchyBean.getInternalDivisions())) {
				// employee mapped to internal division, so we need to get
				// that division head
				String getParentID = "select sfid from emp_role_mapping where upper(internal_role) = upper('Head') and status=1 and upper(internal_division)=? and parent_id=?";
				ps = con.prepareStatement(getParentID);
				ps.setString(1, hierarchyBean.getIntDivision().toUpperCase());
				ps.setString(2, hierarchyBean.getSfid());
				rsq = ps.executeQuery();
				if (rsq.next()) {

					String sql = "select sfid from emp_role_mapping where id=? and status=1";
					ps1 = con.prepareStatement(sql);
					ps1.setString(1, hierarchyBean.getManageID());
					rsq1 = ps1.executeQuery();
					if (rsq1.next()) {
						sfid = rsq1.getString("sfid");
					}
					if (!CPSUtils.compareStrings(sfid, rsq.getString("sfid"))) {
						hierarchyBean.setParent(rsq.getString("sfid"));
					}

				}
			}
			if (CPSUtils.isNullOrEmpty(hierarchyBean.getParent())) {
				hierarchyBean.setParent(hierarchyBean.getSfid());
			}

			String updateInternalRole = "update emp_role_mapping set internal_division=?,internal_role=?,parent_id=? where id=?";
			ps = con.prepareStatement(updateInternalRole);
			ps.setString(1, hierarchyBean.getIntDivision());
			ps.setString(2, hierarchyBean.getIntRole());
			ps.setString(3, hierarchyBean.getParent());
			ps.setString(4, hierarchyBean.getManageID());
			ps.executeUpdate();
			message = CPSConstants.SUCCESS;

		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rsq);
			ConnectionUtil.closeConnection(null, ps1, rsq1);
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	public List<OrgInstanceDTO> getRoleInstanceList(HierarchyBean hierarchybean, List roleList) throws Exception {
		Session session = null;
		List<OrgInstanceDTO> roleInstanceList = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		Connection con = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			StringBuffer sb = new StringBuffer();
			if (roleList.contains("ROLE_ADMIN") || roleList.contains("ROLE_SUPERADMIN")) {
				sb
						.append("select org.org_role_id as id,org_role_name as name from org_role_instance org,org_role_hierarchy orh  where org.status=1 and orh.status=1 and org.role_hierarchy_id=orh.role_hierarchy_id and parent_role_hierarchy_id<9 order by org_role_name");
				Query qry = session.createSQLQuery(sb.toString()).addScalar("id", Hibernate.INTEGER).addScalar("name", Hibernate.STRING).setResultTransformer(
						Transformers.aliasToBean(OrgInstanceDTO.class));
				roleInstanceList = qry.list();
			} else {
				String sfid = hierarchybean.getSfid();
				String sql = "select org.org_role_id from emp_role_mapping erm,org_role_instance org,org_role_hierarchy orh where erm.sfid=? and orh.parent_role_hierarchy_id<9 and erm.status=1 and org.status=1 "
						+ " and orh.status=1 and org.role_hierarchy_id=orh.role_hierarchy_id and erm.org_role_id=org.org_role_id order by org_role_name";
				ps = con.prepareStatement(sql);
				ps.setString(1, sfid);
				rs = ps.executeQuery();
				roleInstanceList = new ArrayList();
				while (rs.next()) {
					String roles = "select org_role_id,org_role_name from org_role_instance org,org_role_hierarchy orh where org.status=1 and orh.status=1 and org.role_hierarchy_id=orh.role_hierarchy_id and parent_role_hierarchy_id<9 start with org_role_id =? connect by parent_org_role_id = prior org_role_id order by org_role_name";
					ps1 = con.prepareStatement(roles);
					ps1.setString(1, rs.getString("org_role_id"));
					rs1 = ps1.executeQuery();
					while (rs1.next()) {
						boolean flag = true;
						for (int i = 0; i < roleInstanceList.size(); i++) {
							OrgInstanceDTO dto = (OrgInstanceDTO) roleInstanceList.get(i);
							if (CPSUtils.compareStrings(rs1.getString("org_role_id"), String.valueOf(dto.getId()))) {
								flag = false;
								break;
							}
						}
						if (flag) {
							OrgInstanceDTO orgdto = new OrgInstanceDTO();
							orgdto.setId(Integer.parseInt(rs1.getString("org_role_id")));
							orgdto.setName(rs1.getString("org_role_name"));
							roleInstanceList.add(orgdto);
						}
					}
				}
			}

		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rs);
			ConnectionUtil.closeConnection(null, ps1, rs1);
		}
		return roleInstanceList;
	}

	public ArrayList<HierarchyDTO> getSubInstance(String instanceID) throws Exception {
		Session session = null;
		PreparedStatement ps = null;
		Connection con = null;
		ArrayList<HierarchyDTO> nodeSubInstanceList = null;
		ResultSet rsq = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			StringBuffer sb = new StringBuffer();
			sb
					.append("select org_role_id,org_role_name,level from org_role_instance where status=1 and role_hierarchy_id>7 start with  parent_org_role_id = ? connect by parent_org_role_id = prior org_role_id");

			ps = con.prepareStatement(sb.toString());
			ps.setString(1, instanceID);
			rsq = ps.executeQuery();

			nodeSubInstanceList = new ArrayList<HierarchyDTO>();
			while (rsq.next()) {
				HierarchyDTO hierDTO = new HierarchyDTO();
				hierDTO.setRoleID(rsq.getInt("org_role_id"));
				String rolename = "";
				if (!CPSUtils.compareStrings(rsq.getString("level"), "1"))
					rolename += "\r\t\r\t\r\t";
				int level = Integer.parseInt(rsq.getString("level"));
				for (int i = 2; i <= level; i++) {
					rolename += " - ";
				}
				rolename += rsq.getString("org_role_name");
				hierDTO.setRoleName(rolename);
				nodeSubInstanceList.add(hierDTO);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rsq);
		}
		return nodeSubInstanceList;
	}

	public ArrayList<EmployeeRoleDTO> getInternalStructureTree(String sfid) throws Exception {
		Session session = null;
		PreparedStatement ps = null;
		Connection con = null;
		ArrayList<EmployeeRoleDTO> internalStructureList = null;
		ResultSet rsq = null;
		try {
			internalStructureList = new ArrayList<EmployeeRoleDTO>();
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			/**
			 * First get the tree structute from emp_role_mapping Then get the names(sfid & parent sfid) from emp_master
			 */
			String internalTree = "select empa.id,empa.sfid,emp.name_in_service_book,empa.internal_division,empa.internal_role,empa.parent_id,empa.parent_name,oi.org_role_name,oi.department_id  "
					+ "from (select emp_roles.id,emp_roles.sfid,emp_roles.internal_division,emp_roles.internal_role,emp_roles.parent_id,emp.name_in_service_book parent_name "
					+ "from (select distinct id,sfid,internal_division,internal_role,parent_id from emp_role_mapping where status=1 and sfid!=? "
					+ "start with parent_id=? connect by sfid = prior parent_id) emp_roles,emp_master emp where emp.status=1 and emp.sfid=parent_id) empa,emp_role_mapping role,org_role_instance oi,emp_master emp where role.status=1 "
					+ "and empa.sfid=role.sfid and role.parent_role_id=oi.org_role_id and emp.status=1 and emp.sfid=empa.sfid and role.org_role_id is null and role.sfid=emp.sfid order by empa.id";
			ps = con.prepareStatement(internalTree);
			ps.setString(1, sfid);
			ps.setString(2, sfid);
			rsq = ps.executeQuery();
			while (rsq.next()) {
				EmployeeRoleDTO empRoleDTO = new EmployeeRoleDTO();
				empRoleDTO.setId(rsq.getString("id"));
				empRoleDTO.setSfid(rsq.getString("sfid"));
				empRoleDTO.setEmployeeName(rsq.getString("name_in_service_book"));
				empRoleDTO.setInternalDivision(rsq.getString("internal_division"));
				empRoleDTO.setInternalRole(rsq.getString("internal_role"));
				empRoleDTO.setParentID(rsq.getString("parent_id"));
				empRoleDTO.setParentName(rsq.getString("parent_name"));
				empRoleDTO.setOfficeName(rsq.getString("org_role_name"));
				empRoleDTO.setDepartmentID(rsq.getString("department_id"));
				internalStructureList.add(empRoleDTO);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rsq);
		}
		return internalStructureList;

	}

	public String deleteInternalEmployee(String id) throws Exception {
		Session session = null;
		PreparedStatement ps = null;
		Connection con = null;
		String message = null;
		ResultSet rsq = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			String checkEmployee = "select sfid from emp_role_mapping where parent_id=(select distinct sfid from emp_role_mapping where id =? and status=1) and status=1";
			ps = con.prepareStatement(checkEmployee);
			ps.setString(1, id);
			rsq = ps.executeQuery();
			if (!rsq.next()) {
				String deleteEmployeeRole = "update emp_role_mapping set status=0 where id=?";
				ps = con.prepareStatement(deleteEmployeeRole);
				ps.setString(1, id);
				ps.executeUpdate();
				message = CPSConstants.SUCCESS;
			} else {
				// records exists
				message = CPSConstants.DELETEFAIL;
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rsq);
		}
		return message;
	}

	// suneetha starts
	public String chekHead(String sfid, String instanceID) throws Exception {
		Session session = null;
		String message = null;
		List list = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String sql = "select sfid from emp_role_mapping where org_role_id=? and status=1 and upper(internal_role)=upper('Head')";
			Query qry = session.createSQLQuery(sql);
			qry.setString(0, instanceID);
			list = qry.list();
			if (!CPSUtils.checkList(list)) {
				message = CPSConstants.SUCCESS;
			} else {
				message = CPSConstants.DUPLICATE;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String chekDuplicateHead(String sfid, String instanceID) throws Exception {
		Session session = null;
		String message = null;
		List list = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String sql = "select sfid from emp_role_mapping where org_role_id=? and status=1 and upper(internal_role)=upper('Head') and sfid=?";
			Query qry = session.createSQLQuery(sql);
			qry.setString(0, instanceID);
			qry.setString(1, sfid);
			list = qry.list();
			if (!CPSUtils.checkList(list)) {
				message = CPSConstants.SUCCESS;
			} else {
				message = CPSConstants.DUPLICATE;
			}

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String chekEmployee(String sfid, String instanceID) throws Exception {
		Session session = null;
		String message = null;
		List list = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String rolesql = "select parent_id from emp_role_mapping where sfid=? and status=1 and parent_id is not null ";
			Query roleqry = session.createSQLQuery(rolesql);
			roleqry.setString(0, sfid);
			list = roleqry.list();
			if (CPSUtils.checkList(list)) {
				message = CPSConstants.EMPLOYEESAME;
			} else {
				message = CPSConstants.SUCCESS;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public List getRoleInstances(HierarchyBean hierarchyBean, List roleList) throws Exception {
		Session session = null;
		List list = null;
		StringBuffer sb = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		Connection con = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			sb = new StringBuffer();
			if (roleList.contains("ROLE_ADMIN") || roleList.contains("ROLE_SUPERADMIN")) {
				sb
						.append("select emp.sfid,emp.name_in_service_book,org.org_role_name,org.org_role_id,"
								+ "erm.id,orh.parent_role_hierarchy_id,level from emp_master emp,emp_role_mapping erm,org_role_instance org,org_role_hierarchy orh where emp.sfid=erm.sfid and erm.org_role_id=org.org_role_id and emp.status='1' and erm.status='1' and org.status=1 "
								+ " and orh.status=1 and org.role_hierarchy_id=orh.role_hierarchy_id and upper(internal_role)=upper('Head') ");
				sb.append("start with org.org_role_id = 1 connect by org.parent_org_role_id = prior org.org_role_id order by org_role_id");
				ps1 = con.prepareStatement(sb.toString());
				rs1 = ps1.executeQuery();
				list = new ArrayList();
				while (rs1.next()) {
					boolean flag = true;
					for (int i = 0; i < list.size(); i++) {
						EmployeeBean dto = (EmployeeBean) list.get(i);
						if (CPSUtils.compareStrings(rs1.getString("id"), String.valueOf(dto.getId()))) {
							flag = false;
							break;
						}
					}
					if (flag) {
						EmployeeBean empbean = new EmployeeBean();
						empbean.setSfid(rs1.getString("sfid"));
						empbean.setEmpName(rs1.getString("name_in_service_book"));
						empbean.setDivisionName(rs1.getString("org_role_name"));
						empbean.setDirectorateId(getRoleInstanceValues(rs1.getString("org_role_id")));
						empbean.setInstanceId(rs1.getString("org_role_id"));
						empbean.setId(rs1.getString("id"));
						empbean.setRoleId(rs1.getString("parent_role_hierarchy_id"));
						list.add(empbean);
					}
				}
			} else {
				String sfid = hierarchyBean.getSfid();
				List<Object> rolelist = getMultipleroles(sfid);
				list = new ArrayList<Object>();
				list = new ArrayList<Object>();
				if (CPSUtils.checkList(rolelist)) {
					for (int j = 0; j < rolelist.size(); j++) {
						sb = new StringBuffer();
						sb.append("select emp.sfid,emp.name_in_service_book,org.org_role_name,org.org_role_id,erm.id,orh.parent_role_hierarchy_id,level from emp_master emp, ");
						sb
								.append("emp_role_mapping erm,org_role_instance org,org_role_hierarchy orh where emp.sfid=erm.sfid and erm.status='1' and org.status=1 and erm.org_role_id=org.org_role_id and emp.status='1' "
										+ " and orh.status=1 and org.role_hierarchy_id=orh.role_hierarchy_id and upper(internal_role)=upper('Head') start with org.org_role_id = ? connect by org.parent_org_role_id = prior org.org_role_id order by org_role_name");
						ps1 = con.prepareStatement(sb.toString());
						ps1.setString(1, rolelist.get(j).toString());
						rs1 = ps1.executeQuery();
						while (rs1.next()) {
							boolean flag = true;
							for (int i = 0; i < list.size(); i++) {
								EmployeeBean dto = (EmployeeBean) list.get(i);
								if (CPSUtils.compareStrings(rs1.getString("id"), String.valueOf(dto.getId()))) {
									flag = false;
									break;
								}
							}
							if (flag) {
								EmployeeBean empbean = new EmployeeBean();
								empbean.setSfid(rs1.getString("sfid"));
								empbean.setEmpName(rs1.getString("name_in_service_book"));
								empbean.setDivisionName(rs1.getString("org_role_name"));
								empbean.setDirectorateId(getRoleInstanceValues(rs1.getString("org_role_id")));
								empbean.setInstanceId(rs1.getString("org_role_id"));
								empbean.setId(rs1.getString("id"));
								empbean.setRoleId(rs1.getString("parent_role_hierarchy_id"));
								list.add(empbean);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rs);
			ConnectionUtil.closeConnection(null, ps1, rs1);
		}
		return list;
	}

	public String getRoleInstanceValues(String instanceId) throws Exception {
		Session session = null;
		List list = null;
		String parentroleInstance = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String sql = "select max(org_role_id) from org_role_instance org,org_role_hierarchy orh where org.status=1 and orh.status=1 and org.role_hierarchy_id=orh.role_hierarchy_id and parent_role_hierarchy_id<9 start with org_role_id = ? connect by org_role_id = prior parent_org_role_id";
			Query qry = session.createSQLQuery(sql);
			qry.setString(0, instanceId);
			list = qry.list();
			if (CPSUtils.checkList(list)) {
				parentroleInstance = list.get(0).toString();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return parentroleInstance;
	}

	public String checkParentInstanceId(String sfid, String instanceID) throws Exception {
		Session session = null;
		String message = null;
		List list = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String sql = "select * from emp_role_mapping erm,emp_role_mapping parentrole,emp_master emp where erm.parent_id=? and erm.sfid=emp.sfid and erm.parent_id=parentrole.sfid and erm.status=1 and parentrole.status=1 "
					+ " and emp.office_id=parentrole.org_role_id and parentrole.org_role_id=?";
			Query qry = session.createSQLQuery(sql);
			qry.setString(0, sfid);
			qry.setString(1, instanceID);
			list = qry.list();
			if (CPSUtils.checkList(list)) {
				message = CPSConstants.FAILED;
			} else {
				message = CPSConstants.SUCCESS;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String checkTree(String sfid, String instanceId) throws Exception {
		Session session = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			int count = 0;
			List list = getSubInstance(instanceId);
			if (CPSUtils.checkList(list)) {
				count++;
			}
			if (count == 0) {
				message = CPSConstants.SUCCESS;
			} else {
				message = CPSConstants.FAILED;
			}

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String deleteHeadMapping(String sfid, String instanceID) throws Exception {
		Session session = null;
		String message = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		Connection con = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String rolesql = "select org_role_id from emp_role_mapping where sfid=? and org_role_id <> ? and status=1";
			pstmt1 = con.prepareStatement(rolesql);
			pstmt1.setString(1, sfid);
			pstmt1.setString(2, instanceID);
			rs = pstmt1.executeQuery();
			/**
			 * if the head is already a normal employee then we should not make him employee under director
			 */
			String empolyeesql = "select sfid,department_id as departmentId from emp_role_mapping where sfid=? and parent_id is not null and status=1";
			pstmt4 = con.prepareStatement(empolyeesql);
			pstmt4.setString(1, sfid);
			rs2 = pstmt4.executeQuery();
			String officeId = "";
			String defaultOfficeId = "";
			boolean flag = false;
			boolean flag1 = false;
			if (rs.next()) {
				officeId = rs.getString("org_role_id");
				flag = true;
			}
			if (rs2.next()) {
				defaultOfficeId = rs2.getString("departmentId");
				flag1 = true;
			}
			if (!flag) {
				/**
				 * removing as head and employee has no other roles then making normal employee under director
				 */
				if (!flag1) {
					String sql = "update  emp_role_mapping set org_role_id=?,parent_id=(select sfid from emp_role_mapping where org_role_id=1 and status=1),internal_role='Employee',last_modified_date=?,department_id=? where sfid=? and org_role_id=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, null);
					pstmt.setString(2, CPSUtils.getCurrentDate());
					pstmt.setString(4, sfid);
					pstmt.setString(3, "1");
					pstmt.setString(5, instanceID);
					pstmt.executeUpdate();

					updateOfficeId("1", "1", sfid);
				} else {
					String sql = "update  emp_role_mapping set status=0,last_modified_date=? where sfid=? and org_role_id=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, CPSUtils.getCurrentDate());
					pstmt.setString(2, sfid);
					pstmt.setString(3, instanceID);
					pstmt.executeUpdate();

					updateOfficeId(defaultOfficeId, getRoleInstanceValues(defaultOfficeId), sfid);
				}
				/**
				 * Remove application role id head
				 */
				updateApplicationRoleId(sfid, instanceID);
			} else {
				/**
				 * removing as head and employee has other roles then making other role as default in emp_master
				 */

				String sql = "update  emp_role_mapping set status=0,last_modified_date=? where sfid=? and org_role_id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, CPSUtils.getCurrentDate());
				pstmt.setString(2, sfid);
				pstmt.setString(3, instanceID);
				pstmt.executeUpdate();

				String officesql = "select count(*) from emp_master where office_id=? and sfid=? and status=1";
				pstmt2 = con.prepareStatement(officesql);
				pstmt2.setString(1, instanceID);
				pstmt2.setString(2, sfid);
				rs1 = pstmt2.executeQuery();
				boolean defaultvalue = false;
				if (rs1.next()) {
					defaultvalue = true;
				}
				if (defaultvalue && !flag1) {
					updateOfficeId(officeId, getRoleInstanceValues(officeId), sfid);
				}

			}

			message = CPSConstants.DELETED;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, pstmt, rs);
			ConnectionUtil.closeConnection(null, pstmt1, null);
			ConnectionUtil.closeConnection(null, pstmt2, rs1);
			ConnectionUtil.closeConnection(null, pstmt3, null);
			ConnectionUtil.closeConnection(null, pstmt4, rs2);
		}
		return message;
	}

	public List getEmployeeMappingDetails(HierarchyBean hierarchyBean, List roleList) throws Exception {
		Session session = null;
		List<Object> list = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String sql = "";
			if (roleList.contains("ROLE_ADMIN") || roleList.contains("ROLE_SUPERADMIN")) {
				sql = "select erm.id,emp.sfid,emp.name_in_service_book,erm.parent_id,dept.department_name as org_role_name,org.org_role_id,(select NAME_IN_SERVICE_BOOK from emp_master where sfid=erm.parent_id)parentName,dm.name designation,erm.parent_role_id as office_id from emp_master emp,emp_role_mapping erm,org_role_instance org,designation_master dm,departments_master dept where "
						+ " parent_id is not null and emp.status=1 and erm.status=1 and org.status=1 and dept.status=1 and dept.department_id=org.department_id and erm.sfid=emp.sfid and erm.parent_role_id=org.org_role_id and dm.status=1 and dm.id=emp.designation_id and org.department_id in(select org_role_id from org_role_instance where status=1 start with org_role_id=1 connect by parent_org_role_id = prior org_role_id) order by dm.order_no,emp.sfid";
				Query qry = session.createSQLQuery(sql);
				list = qry.list();
			} else {
				String sfid = hierarchyBean.getSfid();
				List rolelist = getMultipleroles(sfid);
				list = new ArrayList();
				if (CPSUtils.checkList(rolelist)) {
					for (int i = 0; i < rolelist.size(); i++) {
						sql = "select erm.id,emp.sfid,emp.name_in_service_book,erm.parent_id,dept.department_name as org_role_name,org.org_role_id,(select NAME_IN_SERVICE_BOOK from emp_master where sfid=erm.parent_id)parentName,dm.name designation,erm.parent_role_id as office_id from emp_master emp,emp_role_mapping erm,org_role_instance org,designation_master dm,departments_master dept where "
								+ " parent_id is not null and emp.status=1 and erm.status=1 and org.status=1 and dept.status=1 and dept.department_id=org.department_id and erm.sfid=emp.sfid and erm.parent_role_id=org.org_role_id and dm.status=1 and dm.id=emp.designation_id and org.department_id in(select org_role_id from org_role_instance where status=1 start with org_role_id=? connect by parent_org_role_id = prior org_role_id) order by dm.order_no,emp.sfid";
						Query qry = session.createSQLQuery(sql);
						qry.setString(0, rolelist.get(i).toString());
						List emplist = qry.list();
						if (CPSUtils.checkList(emplist))
							list.addAll(emplist);
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public String saveEmployeeMapping(EmpRoleMappingDTO rolemappingdto, String instanceId) throws Exception {
		Session session = null;
		String message = null;
		PreparedStatement ps2 = null;
		Connection con = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String directorate_id = getRoleInstanceValues(String.valueOf(instanceId));
			/**
			 * If an employee has two roles(normal employee,head) then while changing employee dept check whether any role is having the office_id as org_role_id in emp_role_mapping
			 */
			String rolesql = "select org_role_id from emp_role_mapping where org_role_id=(select office_id from emp_master where sfid=? and status=1) and status=1 and sfid=?";
			List<Object> list = session.createSQLQuery(rolesql).setString(0, rolemappingdto.getSfid()).setString(1, rolemappingdto.getSfid()).list();
			if (!CPSUtils.checkList(list)) {
				String sql = "update emp_master set office_id=?, directorate_id=?  where sfid=?";
				ps2 = con.prepareStatement(sql);
				ps2.setString(1, String.valueOf(instanceId));
				ps2.setString(2, directorate_id);
				ps2.setString(3, rolemappingdto.getSfid());
				ps2.executeUpdate();
			}
			//Transaction tx = session.beginTransaction();
			session.saveOrUpdate(rolemappingdto);
			session.flush();//tx.commit() ;

			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps2, null);
		}
		return message;
	}

	public String deleteEmployeeMapping(EmpRoleMappingDTO rolemappingdto) throws Exception {
		Session session = null;
		String message = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt4 = null;
		ResultSet rs2 = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			/**
			 * before deleting employee mapping check whether this employee has any user specific configuration
			 * 
			 */
			message = checkUserSpecificConfiguration(rolemappingdto.getSfid(), "");
			if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
				String sql = "update  emp_role_mapping set status=0,parent_id=?,last_modified_date=? where id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, null);
				pstmt.setString(2, CPSUtils.getCurrentDate());
				pstmt.setInt(3, rolemappingdto.getId());
				pstmt.executeUpdate();

				/**
				 * Updating head for sub employee if the employee is an internal division head.
				 */
				changeParentForSubEmp(rolemappingdto.getSfid());

				/**
				 * if the employee is a mapped with any role then should not update emp_master
				 */
				String empolyeesql = "select erm.org_role_id from emp_role_mapping erm,org_role_instance org where sfid=? and parent_id is null and erm.status=1 and org.org_role_id = erm.org_role_id and org.status=1 order by org.role_hierarchy_id";
				pstmt4 = con.prepareStatement(empolyeesql);
				pstmt4.setString(1, rolemappingdto.getSfid());
				rs2 = pstmt4.executeQuery();
				boolean flag = false;
				String officeId = "";
				if (rs2.next()) {
					flag = true;
					officeId = rs2.getString("org_role_id");
				}

				if (!flag) {
					String empsql = "update emp_master set office_id=null,directorate_id=null where sfid=?";
					pstmt = con.prepareStatement(empsql);
					pstmt.setString(1, rolemappingdto.getSfid());
					pstmt.executeUpdate();
					message = CPSConstants.DELETE;
				} else {
					/**
					 * If a person has two roles i.e normal employee and a role head,normal employee is the default in emp_master and deleting the employee mapping then update org_role_id of that role
					 * as the officeId in emp_master
					 */
					String empsql = "update emp_master set office_id=?,directorate_id=? where sfid=?";
					pstmt = con.prepareStatement(empsql);
					pstmt.setString(1, officeId);
					pstmt.setString(2, getRoleInstanceValues(officeId));
					pstmt.setString(3, rolemappingdto.getSfid());
					pstmt.executeUpdate();
					message = CPSConstants.DELETE;
				}
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, pstmt, null);
		}
		return message;
	}

	public String deleteEmployee(String sfid) throws Exception {
		Session session = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String sql = "update emp_role_mapping set status=0 where sfid=? and parent_id is not null";
			Query qry = session.createSQLQuery(sql);
			qry.setString(0, sfid);
			qry.executeUpdate();
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String getParentId(String instanceId) throws Exception {
		Session session = null;
		List<Object> list = null;
		String parentId = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String sql = "select sfid from emp_role_mapping where org_role_id=? and status=1";
			Query qry = session.createSQLQuery(sql);
			qry.setString(0, instanceId);
			list = qry.list();

			if (CPSUtils.checkList(list)) {
				parentId = list.get(0).toString();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return parentId;
	}

	public List<Object> getAllTreeInstances(String sfid) throws Exception {
		List<Object> list = null;
		Session session = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		Connection con = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			List rolelist = getMultipleroles(sfid);
			list = new ArrayList();
			if (CPSUtils.checkList(rolelist)) {
				// problem
				for (int i = 0; i < rolelist.size(); i++) {
					String instancesql = "select org_role_id,org_role_name from org_role_instance where status=1 start with org_role_id=? connect by parent_org_role_id=prior org_role_id order by org_role_name";
					ps1 = con.prepareStatement(instancesql);
					ps1.setString(1, rolelist.get(i).toString());
					rs1 = ps1.executeQuery();
					while (rs1.next()) {
						OrgInstanceDTO orgdto = new OrgInstanceDTO();
						orgdto.setId(Integer.parseInt(rs1.getString("org_role_id")));
						orgdto.setName(rs1.getString("org_role_name"));
						list.add(orgdto);
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps1, rs1);
		}
		return list;
	}

	public List<Object> getRoleLevelList(String sfid) throws Exception {
		List<Object> list = null;
		Session session = null;
		Connection con = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			List<Object> rolelist = getMultipleroles(sfid);
			list = new ArrayList<Object>();
			if (CPSUtils.checkList(rolelist)) {
				for (int i = 0; i < rolelist.size(); i++) {
					String sql = "select orh.role_hierarchy_id,orh.role_hierarchy_name from org_role_hierarchy orh,org_role_instance org where orh.parent_role_hierarchy_id>=org.role_hierarchy_id and org.org_role_id=? and org.status=1 and orh.status=1";
					ps1 = con.prepareStatement(sql);
					ps1.setString(1, rolelist.get(i).toString());
					rs1 = ps1.executeQuery();
					while (rs1.next()) {
						boolean flag = true;
						for (int j = 0; j < list.size(); j++) {
							HierarchyDTO dto = (HierarchyDTO) list.get(j);
							if (CPSUtils.compareStrings(rs1.getString("role_hierarchy_id"), String.valueOf(dto.getRoleID()))) {
								flag = false;
								break;
							}
						}
						if (flag) {
							HierarchyDTO hierdto = new HierarchyDTO();
							hierdto.setRoleID(Integer.parseInt(rs1.getString("role_hierarchy_id")));
							hierdto.setRoleName(rs1.getString("role_hierarchy_name"));
							list.add(hierdto);
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps1, rs1);
		}
		return list;
	}

	public List<Object> getDepartmentsList(String sfid) throws Exception {
		List<Object> list = null;
		Session session = null;
		Connection con = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			List<Object> rolelist = getMultipleDepartments(sfid);
			list = new ArrayList<Object>();
			if (CPSUtils.checkList(rolelist)) {
				for (int i = 0; i < rolelist.size(); i++) {
					String sql = "select orh.dept_hierarchy_id as role_hierarchy_id,orh.dept_hierarchy_name as role_hierarchy_name from departments_hierarchy orh,departments_master org where orh.parent_dept_hierarchy_id>org.dept_hierarchy_id"
							+ " and org.department_id=? and org.status=1 and orh.status=1";
					ps1 = con.prepareStatement(sql);
					ps1.setString(1, rolelist.get(i).toString());
					rs1 = ps1.executeQuery();
					while (rs1.next()) {
						boolean flag = true;
						for (int j = 0; j < list.size(); j++) {
							DeptHierarchyDTO dto = (DeptHierarchyDTO) list.get(j);
							if (CPSUtils.compareStrings(rs1.getString("role_hierarchy_id"), String.valueOf(dto.getId()))) {
								flag = false;
								break;
							}
						}
						if (flag) {
							DeptHierarchyDTO hierdto = new DeptHierarchyDTO();
							hierdto.setId(rs1.getInt("role_hierarchy_id"));
							hierdto.setHierarchyName(rs1.getString("role_hierarchy_name"));
							list.add(hierdto);
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps1, rs1);
		}
		return list;
	}

	public List<Object> getMultipleroles(String sfid) throws Exception {
		List list = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String rolesql = "select org_role_id from emp_role_mapping where sfid=? and status=1 order by org_role_id";
			Query qry = session.createSQLQuery(rolesql);
			qry.setString(0, sfid);
			list = qry.list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}

	public String changeParentForSubEmp(String sfid) throws Exception {
		String message = null;
		Session session = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Object> list = null;
		Connection con = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String sql = "select sfid from emp_role_mapping where parent_id=? and status=1 and internal_division is not null";
			Query qry = session.createSQLQuery(sql);
			qry.setString(0, sfid);
			list = qry.list();
			if (CPSUtils.checkList(list)) {
				for (int i = 0; i < list.size(); i++) {
					String updateempsql = "update emp_role_mapping set parent_id=(select parent_id from emp_role_mapping where sfid=?),internal_division=null,last_modified_date=sysdate  where sfid=?";
					ps = con.prepareStatement(updateempsql);
					ps.setString(1, sfid);
					ps.setString(2, list.get(i).toString());
					ps.executeUpdate();
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rs);
		}
		return message;
	}

	public List<Object> getMultipleDepartments(String sfid) throws Exception {
		List list = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String rolesql = "select org.department_id from org_role_instance org,emp_role_mapping erm where erm.sfid=? and erm.org_role_id=org.org_role_id";
			Query qry = session.createSQLQuery(rolesql);
			qry.setString(0, sfid);
			list = qry.list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}

	public List<Object> getDepartments() throws Exception {
		List list = null;
		Session session = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String sql = "select department_id,department_name,level from departments_master start with department_id=1 connect by parent_department_id=prior department_id";
			con = session.connection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			list = new ArrayList();
			while (rs.next()) {
				DepartmentsDTO dept = new DepartmentsDTO();
				dept.setId(rs.getInt("department_id"));
				String rolename = "";
				if (!CPSUtils.compareStrings(rs.getString("level"), "1"))
					rolename += "\r\t\r\t\r\t";
				int level = Integer.parseInt(rs.getString("level"));
				for (int i = 2; i <= level; i++) {
					rolename += "\t\t\t\t\t-";
				}
				rolename += rs.getString("department_name");
				dept.setDeptName(rolename);
				list.add(dept);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}

	public ArrayList<KeyValueDTO> getOrgnRolesList(String sfid) throws Exception {
		ArrayList<KeyValueDTO> orgnList = null;
		Session session = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			String sql = "select dm.department_id,dm.department_name from emp_role_mapping erm,org_role_instance ori,departments_master dm where erm.status=1 and ori.status=1 and erm.sfid=? and erm.org_role_id=ori.org_role_id and dm.status=1 and dm.department_id=ori.department_id";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, sfid);
			rs = pstmt.executeQuery();
			orgnList = new ArrayList<KeyValueDTO>();
			while (rs.next()) {
				KeyValueDTO dept = new KeyValueDTO();
				dept.setKey(rs.getString("department_id"));
				dept.setName(rs.getString("department_name"));
				orgnList.add(dept);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return orgnList;
	}

	public List<Object> getAllTreeDepartments(String sfid, List roleList) throws Exception {
		List<Object> list = null;
		Session session = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		Connection con = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			if (roleList.contains("ROLE_ADMIN") || roleList.contains("ROLE_SUPERADMIN")) {
				String instancesql = "select department_id as org_role_id,department_name as org_role_name from departments_master dept where dept.status=1 start with dept.department_id=(select department_id from org_role_instance where org_role_id='1' and status=1) "
						+ " connect by dept.parent_department_id=prior department_id order by department_name";
				ps1 = con.prepareStatement(instancesql);
				rs1 = ps1.executeQuery();
				list = new ArrayList();
				while (rs1.next()) {
					OrgInstanceDTO orgdto = new OrgInstanceDTO();
					orgdto.setId(Integer.parseInt(rs1.getString("org_role_id")));
					orgdto.setName(rs1.getString("org_role_name"));
					list.add(orgdto);
				}
			} else {

				List rolelist = getMultipleroles(sfid);
				list = new ArrayList();
				if (CPSUtils.checkList(rolelist)) {
					// problem
					for (int i = 0; i < rolelist.size(); i++) {
						String instancesql = "select department_id as org_role_id,department_name as org_role_name from departments_master dept where dept.status=1 start with dept.department_id=(select department_id from org_role_instance where org_role_id=? and status=1) "
								+ " connect by dept.parent_department_id=prior department_id order by department_name";
						ps1 = con.prepareStatement(instancesql);
						ps1.setString(1, rolelist.get(i).toString());
						rs1 = ps1.executeQuery();
						while (rs1.next()) {
							OrgInstanceDTO orgdto = new OrgInstanceDTO();
							orgdto.setId(Integer.parseInt(rs1.getString("org_role_id")));
							orgdto.setName(rs1.getString("org_role_name"));
							list.add(orgdto);
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps1, rs1);
		}
		return list;
	}

	public String updateOfficeId(String officeId, String directorate_id, String sfid) throws Exception {
		String message = null;
		PreparedStatement pstmt = null;
		Connection con = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String empsql = "update emp_master set office_id=?,directorate_id=? where sfid=? and status=1";
			pstmt = con.prepareStatement(empsql);
			pstmt.setString(1, officeId);
			pstmt.setString(2, directorate_id);
			pstmt.setString(3, sfid);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, pstmt, null);
		}
		return message;
	}

	public String checkUserSpecificConfiguration(String sfid, String roleInstanceId) throws Exception {
		String message = null;
		PreparedStatement pstmt = null;
		Connection con = null;
		Session session = null;
		ResultSet rs = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String empsql = "select * from user_specific_configuration where status=1 and (delegate=? or delegate=?)";
			pstmt = con.prepareStatement(empsql);
			pstmt.setString(1, sfid);
			pstmt.setString(2, roleInstanceId);
			rs = pstmt.executeQuery();
			if (rs.next())
				message = CPSConstants.FAILED;
			else
				message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, pstmt, null);
		}
		return message;
	}

	public Integer getApplicationRoleId(String sfid, String instanceId) throws Exception {
		Session session = null;
		Integer roleId = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			Integer hierarchyId = (Integer) session.createSQLQuery("select role_hierarchy_id as roleId from org_role_instance where org_role_id=?").addScalar("roleId", Hibernate.INTEGER).setString(0,
					instanceId).uniqueResult();
			if (hierarchyId >= 19)
				roleId = 2;
			else if (hierarchyId >= 11 && hierarchyId < 19)
				roleId = 3;
			else if (hierarchyId >= 9 && hierarchyId < 11)
				roleId = 8;
			else if (hierarchyId >= 7 && hierarchyId < 9)
				roleId = 8;
			else if (hierarchyId >= 5 && hierarchyId < 7)
				roleId = 7;
			else if (hierarchyId >= 3 && hierarchyId < 5)
				roleId = 6;
			else
				roleId = 5;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return roleId;
	}

	public String saveApplicationRoleId(ApplicationRoleMappingDTO appRole) throws Exception {
		Session session = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			List rolesList = session.createCriteria(ApplicationRoleMappingDTO.class).add(Expression.eq("sfid", appRole.getSfid())).add(
					Expression.eq("applicationRoleId", appRole.getApplicationRoleId())).add(Expression.eq("status", 1)).list();
			if (!CPSUtils.checkList(rolesList)) {
				//Transaction tx = session.beginTransaction();
				session.save(appRole);
				session.flush();//tx.commit() ;
				message = CPSConstants.SUCCESS;
			} else
				message = CPSConstants.EXISTS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String updateApplicationRoleId(String sfid, String instanceId) throws Exception {
		Session session = null;
		String message = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String sql = "delete from ApplicationRoleMappingDTO where sfid=? and applicationRoleId=?";
			//tx = session.beginTransaction();
			session.createQuery(sql).setString(0, sfid).setInteger(1, getApplicationRoleId(sfid, instanceId)).executeUpdate();
			session.flush();//tx.commit() ;
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}
}