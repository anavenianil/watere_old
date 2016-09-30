package com.callippus.web.dao.redetails;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.CategoryDTO;
import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.redetails.ReDetailsBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.ConnectionUtil;

@Service
public class SQLReDetailsDAO implements IReDetailsDAO, Serializable {
	private static final long serialVersionUID = -3961924378632808464L;
	private static Log log = LogFactory.getLog(SQLReDetailsDAO.class);
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	@SuppressWarnings("unchecked")
	public List<ReDetailsBean> designationsList(ReDetailsBean reDetailsBean) throws Exception {
		List designationsList = null;
		Session session = null;
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			if (reDetailsBean.getParam().equalsIgnoreCase("home")) {
				designationsList = session.createCriteria(DesignationDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq(CPSConstants.SERVICETYPE, "N")).list();
			} /*else if (reDetailsBean.getParam().equalsIgnoreCase("category")) {
				sb
						.append("select dm.id,dm.name from designation_master dm,designation_mappings dms,category_master cm where dm.id=dms.desig_id and dm.status=1 and dm.service_type='N' and cm.id=dms.category_id and cm.status=1");
				if (!CPSUtils.isNullOrEmpty(reDetailsBean.getCategoryID()))
					sb.append(" and cm.id=" + reDetailsBean.getCategoryID());
				designationsList = session.createSQLQuery(sb.toString()).addScalar("id", Hibernate.INTEGER).addScalar("name", Hibernate.STRING).setResultTransformer(
						Transformers.aliasToBean(DesignationDTO.class)).list();
			}*/ else if (reDetailsBean.getParam().equalsIgnoreCase("desigReValues")) {
				if (!CPSUtils.isNullOrEmpty(reDetailsBean.getTxnDate()) && !CPSUtils.isNullOrEmpty(reDetailsBean.getCategoryID())){
					sb.append("select rd.key_name id,dm.name,rd.key_value reValue from report_details rd,designation_master dm,category_master cm where rd.key_name=dm.id and cm.id=rd.category_id and dm.status=1 and cm.status=1");
					sb.append("and rd.txn_date= '" + reDetailsBean.getTxnDate() + "'");
					sb.append("and rd.CATEGORY_ID= '" + reDetailsBean.getCategoryID() + "'");
				designationsList = session.createSQLQuery(sb.toString()).addScalar("id", Hibernate.INTEGER).addScalar("name", Hibernate.STRING).addScalar("reValue", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(DesignationDTO.class)).list();
				}
				if(CPSUtils.isNullOrEmpty(designationsList) || designationsList.size()==0){
					sb1.append("select dm.id,dm.name from designation_master dm,designation_mappings dms,category_master cm where dm.id=dms.desig_id and dm.status=1 and dm.service_type='N' and cm.id=dms.category_id and cm.status=1 ");
				if (!CPSUtils.isNullOrEmpty(reDetailsBean.getCategoryID()))
				sb1.append(" and cm.id=" + reDetailsBean.getCategoryID());
				designationsList = session.createSQLQuery(sb1.toString()).addScalar("id", Hibernate.INTEGER).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(DesignationDTO.class)).list();
				}
			}
		} catch (Exception e) {

			throw e;
		} finally {
			//session.close();
		}
		return designationsList;
	}

	@SuppressWarnings("unchecked")
	public List<CategoryDTO> categoryList() throws Exception {
		log.debug("SQLReDetailsDAO---->categoryList");
		List<CategoryDTO> categoryList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			categoryList = session.createCriteria(CategoryDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.asc("orderNo")).list();
			/*
			 * categoryList=session.createSQLQuery("select cm.id categoryID,cm.name categoryName,cm.order_by orderNo,dm.id,dm.name from designation_master dm,designation_mappings dms,category_master cm where dm.id=dms.desig_id and dm.status=1 and dm.service_type='N' and cm.id=dms.category_id and cm.status=1"
			 * ) .addScalar("categoryID", Hibernate.STRING).addScalar("categoryName", Hibernate.STRING).addScalar("orderNo", Hibernate.STRING).addScalar("id", Hibernate.INTEGER).addScalar("name",
			 * Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(CategoryDTO.class)).list();
			 */
		} catch (Exception e) {
			throw e;
		}
		 finally {
				ConnectionUtil.closeConnection(session, null, null);
			}
		return categoryList;
	}

	public ReDetailsBean saveReDetails(ReDetailsBean reDetailsBean) throws Exception {
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			session.saveOrUpdate(reDetailsBean);
			session.flush();//tx.commit() ;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return reDetailsBean;
	}

	public ReDetailsBean checkReDetails(ReDetailsBean reDetailsBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			reDetailsBean = (ReDetailsBean) session.createCriteria(ReDetailsBean.class).add(Expression.eq(CPSConstants.DESIGNATIONID, reDetailsBean.getDesignationId())).uniqueResult();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return reDetailsBean;
	}
}