package com.callippus.web.business.requestprocess;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.higherQualification.HQRequestBean;
import com.callippus.web.beans.higherQualification.dto.HQDetailsDTO;
import com.callippus.web.beans.higherQualification.dto.HQRequestDTO;
import com.callippus.web.beans.higherQualification.dto.HQSanctionDTO;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.beans.workflowmapping.WorkFlowMappingBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
@Service
public class HQRequestProcess {
	private static Log log = LogFactory.getLog(QuarterRequestProcess.class);
	@Autowired
	private RequestProcess requestProcess;
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	
	
	public String initWorkflow(HQRequestBean hqRequestBean) throws Exception {
		log.debug("<<<<<HQRequestProcess<<<<<<Method>>>>>>>>>>>>>>>initWorkflow(HQRequestBean qrb)>>>>>>>>>");
		try {
			hqRequestBean.setRequestID(requestProcess.generateUniqueID(CPSConstants.REQUEST));
			if (CPSUtils.compareStrings(hqRequestBean.getParam(), "manageHQ")) {
				hqRequestBean.setRequestTypeID(CPSConstants.HQREQUESTID);
				hqRequestBean.setResult(submitTxnDetails(hqRequestBean));
			}
			if (CPSUtils.compareStrings(hqRequestBean.getParam(), "manageSOI")) {
				hqRequestBean.setRequestTypeID(CPSConstants.HQSANCOFINCREQUESTID);
				hqRequestBean.setResult(submitTxnDetails(hqRequestBean));
			}
			if (CPSUtils.compareStrings(hqRequestBean.getResult(), CPSConstants.SUCCESS)) {
				RequestBean rb = new RequestBean();
				BeanUtils.copyProperties(hqRequestBean, rb);
				hqRequestBean.setResult(requestProcess.initWorkflow(rb));
			}
		} catch (Exception e) {
			throw e;
		}
		return hqRequestBean.getResult();
	}


	private String submitTxnDetails(HQRequestBean hqRequestBean) throws Exception {
		Session session = null;
		Transaction tx = null;
		HQRequestDTO hQRequestDTO=null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			if(CPSUtils.compareStrings("hqRequest", hqRequestBean.getType())){
			hQRequestDTO=new HQRequestDTO();
			hqRequestBean.setStatus(1);
			hQRequestDTO.setDesigID(hqRequestBean.getDesigID());
			hQRequestDTO.setSfID(hqRequestBean.getSfID());
			hQRequestDTO.setCourse(hqRequestBean.getCourse());
			hQRequestDTO.setLabWorkFlag(hqRequestBean.getLabWork());
			hQRequestDTO.setDischargeOfDutiesFlag(hqRequestBean.getDischargeOfDuties());
			hQRequestDTO.setFromDate(hqRequestBean.getFromDate());
			hQRequestDTO.setToDate(hqRequestBean.getToDate());
			hQRequestDTO.setIpAddress(hqRequestBean.getIpAddress());
			hQRequestDTO.setRequestID(hqRequestBean.getRequestID());
			hQRequestDTO.setStatus(1);
			hqRequestBean.setRequestedDate(CPSUtils.getCurrentDateWithTime());
			//tx = session.beginTransaction();
			session.saveOrUpdate(hQRequestDTO);
			session.flush();//tx.commit() ;
			}else if(CPSUtils.compareStrings("hqSancOfIncRequest", hqRequestBean.getType())){
				HQSanctionDTO hqSanctionDTO=new HQSanctionDTO();
				hqSanctionDTO.setSfID(hqRequestBean.getSfID());
				hqSanctionDTO.setAddHq(hqRequestBean.getAddHq());
				hqSanctionDTO.setDateOfAcquire(hqRequestBean.getDateOfAcquire());
				hqSanctionDTO.setHqAcquired(hqRequestBean.getHqAcquired());
				hqSanctionDTO.setHqAfterInduction(hqRequestBean.getHqAfterInduction());
				hqSanctionDTO.setHqEnclosed(hqRequestBean.getHqEnclosed());
				hqSanctionDTO.setHqIsEssential(hqRequestBean.getHqIsEssential());
				hqSanctionDTO.setHqRecog(hqRequestBean.getHqRecog());
				hqSanctionDTO.setIncentiveClaimed(hqRequestBean.getIncentiveClaimed());
				hqSanctionDTO.setNexus(hqRequestBean.getNexus());
				hqSanctionDTO.setSponceredByGovt(hqRequestBean.getSponceredByGovt());
				hqSanctionDTO.setIpAddress(hqRequestBean.getIpAddress());
				hqSanctionDTO.setRequestID(hqRequestBean.getRequestID());
				hqSanctionDTO.setStatus(1);
				hqRequestBean.setRequestedDate(CPSUtils.getCurrentDateWithTime());
				//tx = session.beginTransaction();
				session.saveOrUpdate(hqSanctionDTO);
				session.flush();//tx.commit() ;
			}
			hqRequestBean.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			//tx.rollback();
			throw e;
		} finally {
			//session.close();
		}
		return hqRequestBean.getResult();
	}


	public WorkFlowMappingBean getHQRequestDetails(WorkFlowMappingBean workflowBean)throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			workflowBean.setHqRequestDTO((HQRequestDTO) session.createCriteria(HQRequestDTO.class).add(Expression.eq(CPSConstants.REQUESTID, workflowBean.getRequestId())).uniqueResult());
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return workflowBean;
	}


	public String approvedRequest(HQRequestBean hqRequestBean)throws Exception {
		log.debug("<<<<<HQRequestProcess<<<<<<Method>>>>>>>>>>>>>>>approvedRequest(HQRequestBean hqRequestBean)>>>>>>>>>");
		RequestBean rb = null;
		try {
			rb = new RequestBean();
			BeanUtils.copyProperties(hqRequestBean, rb);
			rb = requestProcess.approvedRequest(rb);
			rb.setResult(rb.getMessage());
			if (CPSUtils.compareStrings(CPSConstants.SUCCESS, rb.getResult()) ) {
				if(CPSUtils.compareStrings(hqRequestBean.getType(),CPSConstants.HIGHER_QUALIFICATION)){
					rb.setResult(updateHQlabDetails(hqRequestBean));
				}
			}
			if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getResult()) ) {
				if(CPSUtils.compareStrings(hqRequestBean.getType(),CPSConstants.SANCTIONOFINCENTIVE)){
					rb.setResult(updateHQSancOfIncDetails(hqRequestBean));
				}else if(CPSUtils.compareStrings(hqRequestBean.getType(),CPSConstants.HIGHER_QUALIFICATION)){
				rb.setResult(updateHQDetails(hqRequestBean));
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return rb.getResult();
	}


	private String updateHQlabDetails(HQRequestBean hqRequestBean)throws Exception{
		Session session = null;
		Transaction tx = null;
		HQRequestDTO  hqRequestDTO=null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			hqRequestDTO = (HQRequestDTO) session.createCriteria(HQRequestDTO.class).add(Expression.eq(CPSConstants.REQUESTID, hqRequestBean.getRequestID())).uniqueResult();
			hqRequestDTO.setDischargeOfDutiesFlag(hqRequestBean.getDischargeOfDuties());
			hqRequestDTO.setLabWorkFlag(hqRequestBean.getLabWork());
			session.saveOrUpdate(hqRequestDTO);
			session.flush();//tx.commit() ;
			hqRequestDTO.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			//tx.rollback();
			throw e;
		} finally {
			//session.close();
		}
		return hqRequestDTO.getResult();
	}


	private String updateHQDetails(HQRequestBean hqRequestBean)throws Exception {
		Session session = null;
		Transaction tx = null;
		HQDetailsDTO  hQDetailsDTO=null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();

			HQRequestDTO hqRequestDTO = (HQRequestDTO) session.createCriteria(HQRequestDTO.class).add(Expression.eq(CPSConstants.REQUESTID, hqRequestBean.getRequestID()))
					.uniqueResult();
			hqRequestDTO.setStatus(Integer.parseInt(CPSConstants.STATUSCOMPLETED));
			session.saveOrUpdate(hqRequestDTO);
			hQDetailsDTO= (HQDetailsDTO) session.createCriteria(HQDetailsDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("sfID", hqRequestDTO.getSfID())).uniqueResult();
			if (CPSUtils.isNull(hQDetailsDTO)) {
				// New
				hQDetailsDTO = new HQDetailsDTO();
			}
			hQDetailsDTO.setDischargeOfDuties(hqRequestBean.getDischargeOfDuties());
			hQDetailsDTO.setLabWork(hqRequestBean.getLabWork());
			hQDetailsDTO.setCourse(hqRequestDTO.getCourse());
			hQDetailsDTO.setFromDate(hqRequestDTO.getFromDate());
			hQDetailsDTO.setToDate(hqRequestDTO.getToDate());
			hQDetailsDTO.setSfID(hqRequestDTO.getSfID());
			hQDetailsDTO.setStatus(1);
			session.saveOrUpdate(hQDetailsDTO);
			session.flush();//tx.commit() ;
			hQDetailsDTO.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			//tx.rollback();
			throw e;
		} finally {
			//session.close();
		}
		return hQDetailsDTO.getResult();
	}
	private String updateHQSancOfIncDetails(HQRequestBean hqRequestBean)throws Exception{
		Session session = null;
		Transaction tx = null;
		HQSanctionDTO  hqSanctionDTO=null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			hqSanctionDTO = (HQSanctionDTO) session.createCriteria(HQSanctionDTO.class).add(Expression.eq(CPSConstants.REQUESTID, hqRequestBean.getRequestID())).uniqueResult();
			hqSanctionDTO.setStatus(Integer.parseInt(CPSConstants.STATUSCOMPLETED));
			session.saveOrUpdate(hqSanctionDTO);
			session.flush();//tx.commit() ;
			hqSanctionDTO.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			//tx.rollback();
			throw e;
		} finally {
			//session.close();
		}
		return hqSanctionDTO.getResult();
	}


}
