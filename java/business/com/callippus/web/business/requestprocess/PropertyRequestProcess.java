package com.callippus.web.business.requestprocess;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.misc.AdminMisc;
import com.callippus.web.beans.passport.MovablePropertyDTO;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.business.tx.workflow.TxRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
@Service
public class PropertyRequestProcess extends RequestProcess{
	
	private static Log log = LogFactory.getLog(QuarterRequestProcess.class);
	@Autowired
	private TxRequestProcess txRequestProcess;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	
	
	
	public String initWorkflow(AdminMisc adminMisc) throws Exception {
		log.debug("<<<<<QuarterRequestProcess<<<<<<Method>>>>>>>>>>>>>>>initWorkflow(QuarterRequestBean qrb)>>>>>>>>>");
		try {
			adminMisc.setRequestID(txRequestProcess.generateUniqueID(CPSConstants.REQUEST));
			if (CPSUtils.compareStrings(adminMisc.getType(), CPSConstants.MOVABLE)) {
				adminMisc.setRequestType(CPSConstants.MOVABLE);
				adminMisc.setRequestTypeID(CPSConstants.MOVABLEPROPERTY);
				adminMisc.setResult(submitTxnDetails(adminMisc));
			} else {
				adminMisc.setRequestType(CPSConstants.IMMOVABLE);
				adminMisc.setRequestTypeID(CPSConstants.IMMOVABLEPROPERTY);
				
			}

			if (CPSUtils.compareStrings(adminMisc.getResult(), CPSConstants.SUCCESS)) {
				RequestBean rb = new RequestBean();
				BeanUtils.copyProperties(adminMisc, rb);
				adminMisc.setResult(txRequestProcess.initWorkflow(rb));
			}
		} catch (Exception e) {
			throw e;
		}
		return adminMisc.getResult();
	}
	
	
	private String submitTxnDetails(AdminMisc adminMisc) throws Exception {
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			session = hibernateUtils.getSession();
			con = session.connection();
			//adminMisc.setRequestedDate(CPSUtils.getCurrentDateWithTime());
			//session.createSQLQuery("update property_details set staus=?,requested_date=?,request_id=? where id=?");
			
			ps = con.prepareStatement("update property_details set status=?,requested_date=sysdate,request_id=? where id=?");
			ps.setInt(1, Integer.parseInt(CPSConstants.MOVABLE_REQ));
			ps.setString(2, adminMisc.getRequestID());
			ps.setInt(3, adminMisc.getId());
			ps.executeUpdate();
			
			adminMisc.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		} 
		return adminMisc.getResult();
	}
	
	
	
	
	/**
	 * This method will be called when a user wants to approve the request.
	 * 
	 */
	public String approvedRequest(AdminMisc adminMisc) throws Exception {
		log.debug("<<<<<PropertyRequestProcess<<<<<<Method>>>>>>>>>>>>>>>approvedRequest(AdminMisc admin)>>>>>>>>>");
		RequestBean rb = null;
		try {
			rb = new RequestBean();
			BeanUtils.copyProperties(adminMisc, rb);
			rb = txRequestProcess.approvedRequest(rb);
			rb.setResult(rb.getMessage());
			if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getResult())) {
				rb.setResult(updatePropertyDetails(adminMisc));
			}
		} catch (Exception e) {
			throw e;
		}
		return rb.getResult();
	}

	public String updatePropertyDetails(AdminMisc adminMisc) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			MovablePropertyDTO adminMisc1 = (MovablePropertyDTO) session.createCriteria(MovablePropertyDTO.class).add(Expression.eq(CPSConstants.REQUESTID, adminMisc.getRequestID()))
					.uniqueResult();
			
			adminMisc1.setStatus(Integer.valueOf(CPSConstants.STATUSCOMPLETED));
			adminMisc1.setWfStatus(Integer.valueOf(CPSConstants.MP_STATUS));
			session.saveOrUpdate(adminMisc1);

			adminMisc.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		} 
		return adminMisc.getResult();
	}
	
	
	
	public String cancelPropertyRequest(RequestBean requestBean) throws Exception {
		Session session = null;
		String message="";
		try {
			session = hibernateUtils.getSession();
			
			String sql="update MovablePropertyDTO set status=? where requestID=?";
			
			Query query = session.createQuery(sql);
			query.setString(0, CPSConstants.STATUSCANCELLED);
			query.setString(1, requestBean.getRequestID());
			query.executeUpdate();
			message="cancel";
		} catch (Exception e) {
			throw e;
		} 
		return message;
	}
	
	
	
	

}
