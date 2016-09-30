package com.callippus.web.tada.dao.approval;

import java.io.Serializable;

import org.antlr.stringtemplate.language.Expr;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.tada.beans.request.TadaRequestBean;
import com.callippus.web.tada.dto.TadaApprovalRequestDTO;
import com.callippus.web.tada.dto.TadaRequestProcessBean;
import com.callippus.web.tada.dto.TadaTdAdvanceRequestDTO;
@SuppressWarnings("serial")
@Service
public class SQLTadaTdApprovalRequestDAO implements ITadaTdApprovalRequestDAO , Serializable{
	
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	
	public TadaTdAdvanceRequestDTO getTadaTdApprovalDetails (String requestId) throws Exception {
		Session session = null;
		String sql=null;
		TadaTdAdvanceRequestDTO tadaTdAdvanceRequestDTO=null;
		try {
			session = hibernateUtils.getSession();
			sql="select max(request_id) requestId from REQUEST_WORKFLOW_HISTORY where request_id="+requestId+" and status=8";
			tadaTdAdvanceRequestDTO=(TadaTdAdvanceRequestDTO)session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaTdAdvanceRequestDTO.class)).uniqueResult();
			if(tadaTdAdvanceRequestDTO.getRequestId()==null){
				return tadaTdAdvanceRequestDTO;
			} else {
				return tadaTdAdvanceRequestDTO;
			}
			
		}catch (Exception e) {
			throw e;
		}
		
	}
	public TadaApprovalRequestDTO getRefRequestId (TadaRequestBean tadaRequestBean) throws Exception {
		Session session=null;
		String sql=null;
		TadaApprovalRequestDTO tadaApprovalRequestDTO=null;
		TadaTdAdvanceRequestDTO tadaTdAdvanceRequestDTO=null;
		try {
			session=hibernateUtils.getSession();
			if(CPSUtils.compareStrings(CPSConstants.TADATDBUILDUP, tadaRequestBean.getRequestType()) || CPSUtils.compareStrings(CPSConstants.TADATDPROJECT, tadaRequestBean.getRequestType())){
				tadaApprovalRequestDTO=(TadaApprovalRequestDTO)session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("sfID", tadaRequestBean.getSfid())).add(Expression.eq("requestId", tadaRequestBean.getRequestId())).uniqueResult();
			} else if(CPSUtils.compareStrings(CPSConstants.TADATDADVANCES, tadaRequestBean.getRequestType())){
				tadaTdAdvanceRequestDTO=(TadaTdAdvanceRequestDTO)session.createCriteria(TadaTdAdvanceRequestDTO.class).add(Expression.eq("sfID", tadaRequestBean.getSfid())).add(Expression.eq("requestId", tadaRequestBean.getRequestId())).uniqueResult();
				tadaApprovalRequestDTO=(TadaApprovalRequestDTO)session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("sfID", tadaRequestBean.getSfid())).add(Expression.eq("requestId", tadaTdAdvanceRequestDTO.getReferenceRequestID())).uniqueResult();
			}
		} catch(Exception e){
			throw e;
		}
		return tadaApprovalRequestDTO;
	}
	public TadaApprovalRequestDTO getRefRequestId (TadaRequestProcessBean tadaRPB) throws Exception {
		Session session=null;
		String sql=null;
		TadaApprovalRequestDTO tadaApprovalRequestDTO=null;
		TadaTdAdvanceRequestDTO tadaTdAdvanceRequestDTO=null;
		try {
			session=hibernateUtils.getSession();
			if(CPSUtils.compareStrings(CPSConstants.TADA_TD_ADVANCE, tadaRPB.getAmendmentType())){
				tadaTdAdvanceRequestDTO=(TadaTdAdvanceRequestDTO)session.createCriteria(TadaTdAdvanceRequestDTO.class).add(Expression.eq("sfID", tadaRPB.getSfid())).add(Expression.eq("requestId", tadaRPB.getAmendmentReqId())).uniqueResult();
				tadaApprovalRequestDTO=(TadaApprovalRequestDTO)session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("sfID", tadaRPB.getSfid())).add(Expression.eq("requestId", tadaTdAdvanceRequestDTO.getReferenceRequestID())).uniqueResult();
			} else{
				tadaApprovalRequestDTO=(TadaApprovalRequestDTO)session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("sfID", tadaRPB.getSfid())).add(Expression.eq("requestId", tadaRPB.getReferenceRequestID())).uniqueResult();
			}
		} catch(Exception e){
			throw e;
		}
		return tadaApprovalRequestDTO;
	}

}
