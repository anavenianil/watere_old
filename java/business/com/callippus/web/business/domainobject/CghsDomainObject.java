package com.callippus.web.business.domainobject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.CghsAdvanceRequestDTO;
import com.callippus.web.beans.dto.CghsEmergencyRequestDTO;
import com.callippus.web.beans.dto.CghsReimbursementRequestDTO;
import com.callippus.web.beans.requests.CGHSRequestProcessBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;

@Service
public class CghsDomainObject {
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

/**
 * If admin approve the initial request then update the cghs_request_details with admin provided referrenceNumber etc... 
 * @param cghsReqBean
 * @return
 * @throws Exception
 */
	public String updateTxnDetails (CGHSRequestProcessBean cghsReqBean)throws Exception {
		String message= " ";
		Session session=null;
		String sql = null;
        try {
            session = hibernateUtils.getSession();
            sql = "update CGHSRequestProcessBean set referenceNumber=?,approvedBy=?,approvedDate=?,status=? where requestID=?";
            Query qry=session.createQuery(sql);
            qry.setString(0, cghsReqBean.getReferenceNumber());
            qry.setString(1, cghsReqBean.getApprovedBy());
            qry.setDate(2, cghsReqBean.getApprovedDate());
            qry.setString(3, CPSConstants.STATUSREIMREQPENDING);//status id
            qry.setString(4, cghsReqBean.getRequestID());
            
            qry.executeUpdate();
            session.flush();
            message = CPSConstants.SUCCESS;
		}catch (Exception e) {
			throw e;
		}
		return message;
		
	}
	/**
	 * If user applied for CGHSADVANCE,CGHSSETTELMENT OR CGHSREIMBURSEMENT for approved initial 
	 * then update the the status value for appropriate request
	 * @param cghsReqBean
	 * @return
	 * @throws Exception
	 */
	public String updateCghsRequestDetails (CGHSRequestProcessBean cghsReqBean)throws Exception {
		String message= "";
		Session session=null;
		String sql = null;
        try {
            session = hibernateUtils.getSession();
            sql = "update CGHSRequestProcessBean set status=? where requestID=?";
            Query qry=session.createQuery(sql);
            if(CPSUtils.compareStrings(cghsReqBean.getType(),CPSConstants.CGHSADVANCE)) {
            	 qry.setString(0, "35");
            } else if(CPSUtils.compareStrings(cghsReqBean.getType(),CPSConstants.CGHSSETTLEMENT)) {
            	 qry.setString(0, "36");
            } else if(CPSUtils.compareStrings(cghsReqBean.getType(),CPSConstants.CGHSREIMBURSEMENT)) {
            	 qry.setString(0, "37");
            }
            qry.setString(1, cghsReqBean.getReferrenceRequestID());
            qry.executeUpdate();
            session.flush();
            message = CPSConstants.SUCCESS;
		}catch (Exception e) {
			throw e;
		}
		return message;
		
	}
	public String updateCghsAdvanceDetails (CghsAdvanceRequestDTO car)throws Exception {
		String message= "";
		Session session=null;
		String sql = null;
        try {
            session = hibernateUtils.getSession();
             sql = "update CghsAdvanceRequestDTO set status=?,approvedBy=?,issuedAmount=?,approvedDate=sysdate where requestID=?";
            Query qry=session.createQuery(sql);
            qry.setString(0, CPSConstants.STATUSCOMPLETED);//request is completed
            qry.setString(1, car.getSfID());
            qry.setString(2, car.getIssuedAmount());
            qry.setString(3, car.getRequestID());
            qry.executeUpdate();
            session.flush();
            message = CPSConstants.SUCCESS;
		}catch (Exception e) {
			throw e;
		}
		return message;
		
	}
	public String updateCghsReimbursementDetails (CghsReimbursementRequestDTO crr)throws Exception {
		String message= "";
		Session session=null;
		try {
            session = hibernateUtils.getSession();
             
            CghsReimbursementRequestDTO crr1= (CghsReimbursementRequestDTO)session.get(CghsReimbursementRequestDTO.class, crr.getRequestID());
            crr1.setStatus(Integer.parseInt(CPSConstants.STATUSCOMPLETED));
            crr1.setApprovedBy(crr.getSfID());
            crr1.setApprovedDate(CPSUtils.getCurrentDateWithTime());
            crr1.setAdmissibleLabCharges(crr.getAdmissibleLabCharges());
            crr1.setAdmissibleMedicinesCharges(crr.getAdmissibleMedicinesCharges());
            crr1.setAdmissibleConsultationFees(crr.getAdmissibleConsultationFees());
            crr1.setAdmissibleDisposableCharges(crr.getAdmissibleDisposableCharges());
            crr1.setAdmissibleSpecialDevices(crr.getAdmissibleSpecialDevices());
            crr1.setAdmissibleRoomRent(crr.getAdmissibleRoomRent());
            crr1.setAdmissibleOtCharges(crr.getAdmissibleOtCharges());
            crr1.setAdmissibleOtConsumables(crr.getAdmissibleOtConsumables());
            crr1.setAdmissibleAnaesthesiaCharges(crr.getAdmissibleAnaesthesiaCharges());
            crr1.setAdmissibleImplantsCharges(crr.getAdmissibleImplantsCharges());
            crr1.setAdmissibleArtificialCharges(crr.getAdmissibleArtificialCharges());
            crr1.setAdmissibleProcedure(crr.getAdmissibleProcedure());
            crr1.setAdmissibleSpecialNurse(crr.getAdmissibleSpecialNurse());
            crr1.setAdmissibleMiscellaneousCharges(crr.getAdmissibleMiscellaneousCharges());
            crr1.setAdmissibleTotalAmount(crr.getAdmissibleTotalAmount());
            
            session.saveOrUpdate(crr1);
            session.flush();
            message = CPSConstants.SUCCESS;
		}catch (Exception e) {
			throw e;
		}
		return message;
		
	}
	public String updateCghsEmergencyDetails (CghsEmergencyRequestDTO cerd)throws Exception {
		String message= "";
		Session session=null;
        try {
            session = hibernateUtils.getSession();
            CghsEmergencyRequestDTO cerd1= (CghsEmergencyRequestDTO)session.get(CghsEmergencyRequestDTO.class, cerd.getRequestID());
            cerd1.setStatus(Integer.parseInt(CPSConstants.STATUSCOMPLETED));
            cerd1.setApprovedBy(cerd.getSfID());
            cerd1.setApprovedDate(CPSUtils.getCurrentDateWithTime());
            cerd1.setAdmissibleLabCharges(cerd.getAdmissibleLabCharges());
            cerd1.setAdmissibleMedicinesCharges(cerd.getAdmissibleMedicinesCharges());
            cerd1.setAdmissibleRoomRent(cerd.getAdmissibleRoomRent());
            cerd1.setAdmissibleOtCharges(cerd.getAdmissibleOtCharges());
            cerd1.setAdmissibleOtConsumables(cerd.getAdmissibleOtConsumables());
            cerd1.setAdmissibleAnaesthesiaCharges(cerd.getAdmissibleAnaesthesiaCharges());
            cerd1.setAdmissibleImplantsCharges(cerd.getAdmissibleImplantsCharges());
            cerd1.setAdmissibleArtificialCharges(cerd.getAdmissibleArtificialCharges());
            cerd1.setAdmissibleProcedure(cerd.getAdmissibleProcedure());
            cerd1.setAdmissibleSpecialNurse(cerd.getAdmissibleSpecialNurse());
            cerd1.setAdmissibleMiscellaneousCharges(cerd.getAdmissibleMiscellaneousCharges());
            cerd1.setAdmissibleTotalAmount(cerd.getAdmissibleTotalAmount());
            session.saveOrUpdate(cerd1);
            session.flush();
            message = CPSConstants.SUCCESS;
		}catch (Exception e) {
			throw e;
		}
		return message;
		
	}
	@SuppressWarnings("unchecked")
	public String updateTxnDetailsStatus (String statusID,String requestID,Class beanName)throws Exception {
		String message= "";
		Session session=null;
		String sql = null;
        try {
            session = hibernateUtils.getSession();
            sql = "update "+beanName.getSimpleName()+" set status=? where requestID=?";
            Query qry=session.createQuery(sql);
            qry.setString(0, statusID);
            qry.setString(1, requestID);
            qry.executeUpdate();
            session.flush();
             message = CPSConstants.SUCCESS;
		}catch (Exception e) {
			throw e;
		}
		return message;
		
	}
	public String updateCghsAdvance (String issuedAmount,String statusID,String requestID)throws Exception {
		String message= "";
		Session session=null;
		String sql = null;
        try {
            session = hibernateUtils.getSession();
            sql = "update  CghsAdvanceRequestDTO set status=?,issuedAmount=? where requestID=?";
            Query qry=session.createQuery(sql);
            qry.setString(0, statusID);
            qry.setString(1, issuedAmount);
            qry.setString(2, requestID);
            qry.executeUpdate();
            session.flush();
            message = CPSConstants.SUCCESS;
		}catch (Exception e) {
			throw e;
		}
		return message;
		
	}
	public String updateAdvanceDetails (String settleAmount,String requestID)throws Exception {
		String message= "";
		Session session=null;
		String sql = null;
        try {
            session = hibernateUtils.getSession();
            sql = "update  CghsAdvanceRequestDTO set settleAmount=? where referrenceRequestID=?";
            Query qry=session.createQuery(sql);
            qry.setString(0, settleAmount);
            qry.setString(1, requestID);
            qry.executeUpdate();
            session.flush();
            message = CPSConstants.SUCCESS;
		}catch (Exception e) {
			throw e;
		}
		return message;
		
	}
	public String  updateReferenceRequestStatus(String requestID,String type) throws Exception {
		Session session=null;
		String referenceRequestID = null;
        try {
        	session = hibernateUtils.getSession();
            if(CPSUtils.compareStrings(type, CPSConstants.CGHS_ADVANCE)) {
            	referenceRequestID = (String)session.createSQLQuery("select reference_request_id||'' from cghs_advance_details where request_id = ?").setString(0, requestID).uniqueResult();            	
            }else {
            	referenceRequestID = (String)session.createSQLQuery("select reference_request_id||'' from cghs_reimbursement_details where request_id=?").setString(0, requestID).uniqueResult();            	
            }
            
        }catch (Exception e) {
			throw e;
		}
		return referenceRequestID;
		
	}
	
	
}
