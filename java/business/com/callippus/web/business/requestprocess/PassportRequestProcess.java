package com.callippus.web.business.requestprocess;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.passport.PassportApplicationDetailsDTO;
import com.callippus.web.beans.passport.PassportRequestProcessBean;
import com.callippus.web.beans.quarter.QuarterRequestBean;
import com.callippus.web.beans.requests.FPARequestProcessBean;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.beans.workflowmapping.WorkFlowMappingBean;
import com.callippus.web.business.passport.PassportBusiness;
import com.callippus.web.business.tx.workflow.TxRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.dao.passport.IPassportDAO;

@Service
public class PassportRequestProcess extends RequestProcess{

	private static Log log = LogFactory.getLog(CghsRequestProcess.class);
	@Autowired
	private TxRequestProcess txRequestProcess;
	@Autowired
    private PassportBusiness passportBusiness;
    @Autowired
    private IPassportDAO passportDAO;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

    
	public String initWorkflow(PassportRequestProcessBean prb) throws Exception {
		log.debug("::<<<<<PassportRequestProcesss<<<<<<Method>>>>>>>>>>>>>>>initWorkflowForPassportApplicationRequest(PassportRequestProcessBean prb)>>>>>>>>>");
		String message = null;
		try {
			/**
			 * Generate Unique ID for this Request
			 */
			prb.setRequestID(txRequestProcess.generateUniqueID(CPSConstants.REQUEST));
			prb.setRequestType(CPSConstants.PASSPORT);
			prb.setRequestTypeID("42");
	//		prb.setResult(submitPassportRequest(prb));
			if (CPSUtils.compareStrings(prb.getResult(), CPSConstants.SUCCESS)) {
				/**
				 * decide the work flow & insert into history table
				 */
				RequestBean rb = new RequestBean();
				BeanUtils.copyProperties(prb, rb);
				message = txRequestProcess.initWorkflow(rb);
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		return message;
	}
	/*public String submitPassportRequest(PassportRequestProcessBean passportBean) throws Exception{
		Session session = null;
		try{
			session = hibernateUtils.getSession();

			PassportApplicationDetailsDTO detailsDTO=new PassportApplicationDetailsDTO();
			
			EmployeeBean employeeBean=passportDAO.getEmpDetails(passportBean);
			detailsDTO.setRequestID(Integer.valueOf(passportBean.getRequestID()));
			detailsDTO.setSfID(passportBean.getSfID());
			detailsDTO.setDesignationID(employeeBean.getDesignation());
			if(!CPSUtils.isNull(employeeBean.getOffice())){
			detailsDTO.setDepartmentID(Integer.valueOf(employeeBean.getOffice()));
			}detailsDTO.setRelationsAbroad(passportBean.getRelations());
			detailsDTO.setVigilenceFlag(passportBean.getVigilanceFlag());
			if(!CPSUtils.isNull(passportBean.getType())){
			detailsDTO.setPassportTypeID(Integer.valueOf(passportBean.getType()));
			}detailsDTO.setPassportNumber(passportBean.getPassportNumber());
			detailsDTO.setIssueDate(passportBean.getIssueDate());
			detailsDTO.setReturnDate(passportBean.getReturnDate());
			detailsDTO.setValidityDate(passportBean.getValidityDate());
			detailsDTO.setIpAddress(passportBean.getIpAddress());
			detailsDTO.setDurationOfStay(Integer.valueOf(passportBean.getDuration()));
			detailsDTO.setPurposeOfVisit(passportBean.getPurpose());
			detailsDTO.setStatus(Integer.valueOf(CPSConstants.STATUSPENDING));
			detailsDTO.setFamilyMembersIDs(passportBean.getFamilyMemberId());
			detailsDTO.setRemarks(passportBean.getRemarks());
			if(!CPSUtils.isNull(employeeBean.getInternalNo())){
			detailsDTO.setInternalNo(Integer.valueOf(employeeBean.getInternalNo()));
			}detailsDTO.setAmountToBeSpend(Float.valueOf(passportBean.getSpendingAmount()));
			detailsDTO.setSourceOfAmount(passportBean.getSourceOfAmount());
			detailsDTO.setFinancierName(passportBean.getLenderName());
			detailsDTO.setFinancierRelationship(passportBean.getLenderRelationship());
			detailsDTO.setFinancierNationality(passportBean.getNationality());
			detailsDTO.setCountriesToVisit(passportBean.getCountriesToVisit());
			detailsDTO.setDepartureDate(passportBean.getDepartureDate());
			session.saveOrUpdate(detailsDTO);
			passportBean.setResult(CPSConstants.SUCCESS);
			
		}catch(Exception e){
			passportBean.setResult(CPSConstants.FAILED);
			throw e;
		}
		return passportBean.getResult();
	}*/
	public WorkFlowMappingBean getPassportRequestDetails(WorkFlowMappingBean workflowBean) throws Exception {
		log.debug("::<<<<<PassportRequestProcess<<<<<<Method>>>>>>>>>>>>>>>getPassportRequestDetails(WorkFlowMappingBean workflowBean)>>>>>>>>>");
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			workflowBean.setPassportRequestDetails((PassportApplicationDetailsDTO) session.createCriteria(PassportApplicationDetailsDTO.class).add(Expression.eq(CPSConstants.REQUESTID, Integer.parseInt(workflowBean.getRequestId()))).uniqueResult());
		} catch (Exception e) {
			throw e;
		}
		return workflowBean;
	}

}
