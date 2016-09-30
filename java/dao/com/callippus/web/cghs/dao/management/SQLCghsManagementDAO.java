package com.callippus.web.cghs.dao.management;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.cghs.beans.dto.ReferralHospitalDTO;
import com.callippus.web.cghs.beans.dto.WardTypeDTO;
import com.callippus.web.cghs.beans.management.CghsManagementBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;

/**
 * The Class SQLCghsManagementDAO.
 */
@Service
public class SQLCghsManagementDAO implements ICghsManagementDAO, Serializable {
	private static final long serialVersionUID = -7753238725888858447L;

	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	/**
	 * THIS METHOD IS USED TO CREATE , UPDATE AND DELTE RECORD FROM ANY TABLE BASED ON THE BEAN NAME WE PASS TO THIS METHOD.
	 * 
	 * @param object
	 *            the object
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String saveBeanVales(Object object) throws Exception {
		Session session = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();
			session.saveOrUpdate(object);
			session.flush();
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	/**
	 * THIS METHOD IS USED TO GET ALL RECORD FROM ANY TABLE WHOSE STATUS IS ONE.
	 * 
	 * @param beanName
	 *            the bean name
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<ReferralHospitalDTO> getList(Class beanName) throws Exception {
		Session session = null;
		
		List list = null;
		try {
			session = hibernateUtils.getSession();
			
			if (CPSUtils.compareStrings(beanName.getSimpleName(), "ReferralHospitalDTO")) {
				list = session.createCriteria(beanName).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.desc("deletionDate")).list();
			} else {
				list = session.createCriteria(beanName).add(Expression.eq(CPSConstants.STATUS, 1)).list();
			}
           
            
		} catch (Exception e) {
			throw e;
		}
		return list;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.callippus.web.cghs.dao.management.ICghsManagementDAO#getBeanValue(java.lang.Class, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	/**
	 * THIS METHOD IS  USED TO GET ANY TABLE PRIMARY KEY ROW VALUES
	 * IT IDENTTIFIES THE TABLE BASED ON BEAN NAME WE PASS 
	 */
	public Object getBeanValue(Class beanName, String pk) throws Exception {
		Session session = null;
		Object object = null;
		try {
			session = hibernateUtils.getSession();
			if (!CPSUtils.isNullOrEmpty(pk)) {
				object = session.get(beanName, Integer.parseInt(pk));
			}
		} catch (Exception e) {
			throw e;
		}
		return object;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.callippus.web.cghs.dao.management.ICghsManagementDAO#checkDuplicateEntry(com.callippus.web.cghs.beans.management.CghsManagementBean, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	/**
	 * THIS METHOD IS USED TO CHECK DUPLICATE ENTRY OF RECORD IN ANY TABLE
	 */
	public Object checkDuplicateEntry(CghsManagementBean cghsBean, Class beanName) throws Exception {
		Session session = null;
		Object object = null;
		try {
			session = hibernateUtils.getSession();
			if (CPSUtils.compareStrings(beanName.getSimpleName(), "WardTypeDTO")) {
				object = (WardTypeDTO) session.createCriteria(beanName).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq(CPSConstants.NAME, (cghsBean.getWardName().trim()).toUpperCase()))
						.uniqueResult();
			} else if (CPSUtils.compareStrings(beanName.getSimpleName(), "ReferralHospitalDTO")) {
				Criteria crit = session.createCriteria(beanName).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq(CPSConstants.HOSPITALNAME, cghsBean.getHospitalName().toUpperCase()))
						.add(Expression.eq(CPSConstants.HOSPITALLOCATION, cghsBean.getHospitalLocation().toUpperCase()));
				if (!CPSUtils.isNullOrEmpty(cghsBean.getPk())) {
					crit = crit.add(Expression.ne(CPSConstants.ID, Integer.parseInt(cghsBean.getPk())));
				}
				object = (ReferralHospitalDTO) crit.uniqueResult();
			}
		} catch (Exception e) {
			throw e;
		}
		return object;
	}

	public String seletedDeleteRefHospital(int id, String deletionDate) throws Exception {
		Session session = null;
		String message = null;
		String hql = "";
		try {
			session = hibernateUtils.getSession();
			hql = "update ReferralHospitalDTO set deletionDate = ? where id = ?";
			Query query = session.createQuery(hql);
			query.setString(0, deletionDate);
			query.setInteger(1, id);
			query.executeUpdate();
			session.flush();
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		} 
		return message;
	}
}
