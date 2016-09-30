package com.callippus.web.dao.menulinks;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.MenuLinksDTO;
import com.callippus.web.beans.dto.RequestRolesMappingDTO;
import com.callippus.web.beans.menulinks.MenuLinksBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.ConnectionUtil;

@Service
public class SQLMenuLinksDAO implements IMenuLinksDAO, Serializable {

	private static final long serialVersionUID = -44397746390491037L;

	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	@Override
	public List getSpecificMenuLinks(String applicationRole) throws Exception {
		List list = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String hql = "from RoleLinksMappingDTO r where r.status=1 and r.roleID=?";
			Query qry = session.createQuery(hql);
			qry.setInteger(0, Integer.valueOf(applicationRole));
			list = qry.list();  
			
			
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}

	@Override
	public String submitLinksMapping(MenuLinksBean menuLinksBean) throws Exception {
		Session session = null;
		String message = null;
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String sql = "delete role_links_mapping where role_id=? and role_id!=0";
			ps = con.prepareStatement(sql);
			ps.setString(1, menuLinksBean.getApplicationRole());
			ps.executeUpdate();

			String[] roleLinks = (menuLinksBean.getSelectedLinks().substring(0, menuLinksBean.getSelectedLinks().length() - 1)).split(",");
			sql = "insert into role_links_mapping(id,role_id,link_id,status,description) "
					+ "values((select case when (select max(id)+1 from role_links_mapping) is null then 0 else (select max(id)+1 from role_links_mapping) end from dual),?,?,1,'')";
			for (int i = 0; i < roleLinks.length; i++) {
				ps1 = con.prepareStatement(sql);
				ps1.setString(1, menuLinksBean.getApplicationRole());
				ps1.setString(2, roleLinks[i]);
				ps1.executeUpdate();
				ps1.close();
			}
			message = CPSConstants.SUCCESS;
			ps.close();
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, null);
			ConnectionUtil.closeConnection(null, ps1, null);
		}
		return message;
	}
	
	//Application Request Roles Mapping
	  public String submitRoleLinksMapping(MenuLinksBean menuLinksBean) throws Exception {
		Session session = null;
		String message = null;
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String sql = "delete request_role_mapping where application_role_id=? and application_role_id!=0";
			ps = con.prepareStatement(sql);
			ps.setString(1, menuLinksBean.getApplicationRole());
			ps.executeUpdate();

			String[] roleLinks = (menuLinksBean.getSelectedLinks().substring(0, menuLinksBean.getSelectedLinks().length() - 1)).split(",");
			sql = "insert into request_role_mapping(id,application_role_id,request_type_id,status,creation_date,last_modified_date,last_modified_by) "
					+ "values((select case when (select max(id)+1 from request_role_mapping) is null then 0 else (select max(id)+1 from request_role_mapping) end from dual),?,?,1,?,?,?)";
			for (int i = 0; i < roleLinks.length; i++) {
				ps1 = con.prepareStatement(sql);
				ps1.setString(1, menuLinksBean.getApplicationRole());
				ps1.setString(2, roleLinks[i]);
				ps1.setString(3,CPSUtils.getCurrentDate());
				ps1.setString(4,CPSUtils.getCurrentDate());
				ps1.setString(5,menuLinksBean.getSfid());
				ps1.executeUpdate();
			}
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, null);
			ConnectionUtil.closeConnection(null, ps1, null);
		}
		return message;
	}
	  
	   @Override
	public List<RequestRolesMappingDTO> getSelectedRequestList(String id) throws Exception {
	        Session session = null;
	        List<RequestRolesMappingDTO> list = null;
	 	  try {
			     session = hibernateUtils.getSession();//session = sessionFactory.openSession();
				 String sql = "select REQUEST_TYPE as name,REQUEST_TYPE_ID as id from REQUEST_MASTER where REQUEST_TYPE_ID in " +
			     		      "(select REQUEST_TYPE_ID from REQUEST_ROLE_MAPPING WHERE STATUS=1 AND APPLICATION_ROLE_ID="+id+") order by Request_type ";
			     list=session.createSQLQuery(sql).addScalar("name",Hibernate.STRING).addScalar("id",Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(RequestRolesMappingDTO.class)).list();
				 } catch (Exception e) {
			      throw e;
			     } finally {
			    	 //session.close();
	    	}
			return list;
		 }
	   
	   @Override
	public List getDeSelectedRequestList(String id) throws Exception {
	        Session session = null;
	        List list = null;
	 	   try {
			     session = hibernateUtils.getSession();//session = sessionFactory.openSession();
				 String sql = "select RM.REQUEST_TYPE as name,RM.REQUEST_TYPE_ID as id from REQUEST_MASTER RM where REQUEST_TYPE_ID not in " +
                               "(select REQUEST_TYPE_ID from REQUEST_ROLE_MAPPING where STATUS = 1 and  APPLICATION_ROLE_ID="+id+") order by rm.Request_type";
			     list=session.createSQLQuery(sql).addScalar("name",Hibernate.STRING).addScalar("id",Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(RequestRolesMappingDTO.class)).list();
				 } catch (Exception e) {
			      throw e;
			     } finally {
			    	 
		     //session.close();
	    	}
			return list;
		 }
	
	//End Application Request Roles Mapping
	
	   @Override
	@SuppressWarnings("unchecked")
	public List<MenuLinksDTO> getDescriptionList() throws Exception {
		List<MenuLinksDTO> descriptionList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			descriptionList = session.createCriteria(MenuLinksDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.asc("description")).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return descriptionList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<MenuLinksBean> getMenuLinksList() throws Exception {
		List<MenuLinksBean> menuLinksList = null;
		Session session = null;
		StringBuffer sb = new StringBuffer();
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			sb
					.append("select id,parentId,linkName,parentName,keyName,controllerClass from ((select m1.id ,m2.id parentID,m1.description linkName,m2.description parentName,m1.name keyName,m1.controller_class controllerClass from menu_links_master m1,menu_links_master m2 where m1.status=1 and m2.status=1 and m1.parent_id=m2.id) "
							+ "union (select m.id, m.parent_id parentId ,m.description linkName,'' parentName,m.name keyName,m.controller_class controllerClass from menu_links_master m where m.status=1 and m.parent_id=0))");

			menuLinksList = session.createSQLQuery(sb.toString()).addScalar("id", Hibernate.STRING).addScalar("linkName").addScalar("parentName").addScalar("keyName").addScalar("parentId",
					Hibernate.STRING).addScalar("controllerClass").setResultTransformer(Transformers.aliasToBean(MenuLinksBean.class)).list();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return menuLinksList;
	}

	@Override
	public String saveMenuLinksMapping(MenuLinksBean menuLinksBean) throws Exception {
		Transaction tx = null;
		Session session = null;
		String message = null;
		StringBuffer sb = new StringBuffer();

		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			if (menuLinksBean.getStatus() == 0) {
				// delete
				menuLinksBean = (MenuLinksBean) session.get(MenuLinksBean.class, Integer.valueOf(menuLinksBean.getPk()));
				menuLinksBean.setStatus(0);
				session.saveOrUpdate(menuLinksBean);
				session.flush();
				message = CPSConstants.DELETE;
			} else {
				// duplicate checking
				sb.append("select count(*) cnt from menu_links_master where name=? and status=1 ");
				if (!CPSUtils.isNullOrEmpty(menuLinksBean.getPk())) {
					sb.append("and id!=" + menuLinksBean.getPk());
				}
				String count = session.createSQLQuery(sb.toString()).setString(0, menuLinksBean.getKeyName()).uniqueResult().toString();
				if (CPSUtils.compareStrings(count, "0")) {
					if (!CPSUtils.isNullOrEmpty(menuLinksBean.getPk())) {
						// update
						menuLinksBean.setUid(Integer.valueOf(menuLinksBean.getPk()));
					}
					session.saveOrUpdate(menuLinksBean);
					session.flush();
					message = CPSConstants.SUCCESS;
				} else {
					// duplicate
					message = CPSConstants.DUPLICATE;
				}
			}
			
		} catch (Exception e) {
			//tx.rollback();
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}



	
	
}
