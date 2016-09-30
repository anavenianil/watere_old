package com.callippus.web.business.requestprocess;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.CghsAdvanceRequestDTO;
import com.callippus.web.beans.dto.CghsEmergencyRequestDTO;
import com.callippus.web.beans.dto.CghsReimbursementRequestDTO;
import com.callippus.web.beans.dto.EmployeeClaimDetailsDTO;
import com.callippus.web.beans.requests.CGHSRequestProcessBean;
import com.callippus.web.beans.requests.LeaveRequestBean;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.business.domainobject.CghsDomainObject;
import com.callippus.web.business.tx.workflow.TxRequestProcess;
import com.callippus.web.cghs.beans.request.CghsRequestBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;

@Service
public class CghsRequestProcess extends TxRequestProcess {

	private static Log log = LogFactory.getLog(CghsRequestProcess.class);
	@Autowired
	private TxRequestProcess txRequestProcess;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private CghsDomainObject cghsDomainObject;

	public String initWorkflow(CGHSRequestProcessBean crb) throws Exception {
		Session session = null;
		//log.debug("::<<<<<CghsRequestProcess<<<<<<Method>>>>>>>>>>>>>>>initWorkflowForTreatementRequest(CGHSRequestProcessBean crb)>>>>>>>>>");
		try {
			/**
			 * Generate Unique ID for this Request
			 */
			session = hibernateUtils.getSession();
			crb.setRequestID(txRequestProcess.generateUniqueID(CPSConstants.REQUEST));
			File reportFile = new File(crb.getSession().getServletContext().getResource("/jasper/").getPath());
			HashMap<String, String> pmap = new HashMap<String, String>();
			pmap.put("RequestID", crb.getRequestID());
			pmap.put("SubReportPath", reportFile.getAbsolutePath() + File.separatorChar);
			// pmap.put("SubReportPath",String.valueOf(reportFile + File.separatorChar));

			if (CPSUtils.compareStrings(crb.getType(), CPSConstants.TREATMENT)) {
				crb.setRequestType(CPSConstants.CGHS);
				crb.setRequestTypeID("15");// CGHS Request type ID = 15
				crb.setMessage(submitTxnDetails(crb));
			}else if (CPSUtils.compareStrings(crb.getType(), CPSConstants.ADVANCE)) {
				crb.setRequestType(CPSConstants.ADVANCE);
				crb.setRequestTypeID("57");// Invistegation ADVANCE Request type ID = 57
				crb.setMessage(submitAdvanceTxnDetails(crb));
				//crb.setMessage(submitTxnDetails(car)); 
			}else if (CPSUtils.compareStrings(crb.getType(), CPSConstants.REIMBURSEMENT)) {
				crb.setRequestType(CPSConstants.REIMBURSEMENT);
				crb.setRequestTypeID("57");// Invistegation REIMBURSEMENT Request type ID = 17
				crb.setMessage(submitReimbursementTxnDetails(crb));
				//crb.setMessage(submitTxnDetails(car)); 
				
				 
			}else if (CPSUtils.compareStrings(crb.getType(), CPSConstants.CGHSADVANCE)) {
				crb.setRequestType(CPSConstants.CGHS_ADVANCE);
				crb.setRequestTypeID("17");// CGHSADVANCE Request type ID = 17
				CghsAdvanceRequestDTO car = new CghsAdvanceRequestDTO();
				BeanUtils.copyProperties(crb, car);
				crb.setMessage(submitTxnDetails(car));
			} else if (CPSUtils.compareStrings(crb.getType(), CPSConstants.CGHSREIMBURSEMENT)) {
				crb.setRequestType(CPSConstants.CGHS_REIMBURSEMENT);
				crb.setRequestTypeID("18");// CGHSREIMBURSEMENT Request type ID = 18
				CghsReimbursementRequestDTO crr = new CghsReimbursementRequestDTO();
				BeanUtils.copyProperties(crb, crr);
				crb.setMessage(submitTxnDetails(crr));
				cghsDomainObject.updateTxnDetailsStatus(CPSConstants.STATUSREIMREQPROCESSING, crr.getReferrenceRequestID(), CGHSRequestProcessBean.class);
			} else if (CPSUtils.compareStrings(crb.getType(), CPSConstants.NONCGHSREIMBURSEMENT)) {
				crb.setRequestType(CPSConstants.NON_CGHS_REIMBURSEMENT);
				crb.setRequestTypeID("19");// NONCGHSREIMBURSEMENT Request type ID = 19
				CghsReimbursementRequestDTO crr = new CghsReimbursementRequestDTO();
				BeanUtils.copyProperties(crb, crr);
				crb.setMessage(submitTxnDetails(crr));
				// cghsDomainObject.updateCghsRequestDetails(crb);
			} else if (CPSUtils.compareStrings(crb.getType(), CPSConstants.CGHSSETTLEMENT)) {
				crb.setRequestType(CPSConstants.CGHS_SETTLEMENT);
				crb.setRequestTypeID("20");// CGHSSETTLEMENT Request type ID = 20
				CghsReimbursementRequestDTO crr = new CghsReimbursementRequestDTO();
				BeanUtils.copyProperties(crb, crr);
				crb.setMessage(submitTxnDetails(crr));
				cghsDomainObject.updateTxnDetailsStatus(CPSConstants.STATUSSETTREQPROCESSING, crr.getReferrenceRequestID(), CGHSRequestProcessBean.class);
				cghsDomainObject.updateAdvanceDetails(crb.getSettleAmount(), crr.getReferrenceRequestID());
			} else if (CPSUtils.compareStrings(crb.getType(), CPSConstants.EMERGENCY)) {
				crb.setRequestType(CPSConstants.CGHS_EMERGENCY);
				crb.setRequestTypeID("23");// EMERGENCY Request type ID = 23
				CghsEmergencyRequestDTO cer = new CghsEmergencyRequestDTO();
				BeanUtils.copyProperties(crb, cer);
				crb.setMessage(submitTxnDetails(cer));
			} else if (CPSUtils.compareStrings(crb.getType(), CPSConstants.NONCGHSEMERGENCY)) {
				crb.setRequestType(CPSConstants.NON_CGHS_EMERGENCY);
				crb.setRequestTypeID("27");// NONCGHSEMERGENCY Request type ID = 27
				CghsEmergencyRequestDTO cer = new CghsEmergencyRequestDTO();
				BeanUtils.copyProperties(crb, cer);
				crb.setMessage(submitTxnDetails(cer));
			}
			if (CPSUtils.compareStrings(crb.getMessage(), CPSConstants.SUCCESS)) {
				/**
				 * decide the work flow & insert into history table
				 */
				///////////////////////
				
			//if(!crb.getCghsRequestTypeId().equals("2")){
				if(!(crb.getType().equals("advance")|| crb.getType().equals("reimbursement"))){
					
					EmployeeClaimDetailsDTO eced=new EmployeeClaimDetailsDTO();
					if(!CPSUtils.isNullOrEmpty(crb.getAmountClaimed())){
					eced.setAmountClaimed(Float.valueOf(crb.getAmountClaimed()));}
					//eced.setRequestID(Integer.valueOf(crb.getRequestId()));
					if(!CPSUtils.isNullOrEmpty(crb.getReferrenceRequestID())){
						eced.setRefRequestId(Integer.valueOf(crb.getReferrenceRequestID()));
					}
					eced.setRequestID(Integer.valueOf(crb.getRequestID()));
					eced.setRequestTypeID(Integer.valueOf(crb.getRequestTypeID()));
					eced.setWorkFlowStatus(2);
					eced.setStatus(1);
					eced.setAppliedBy(crb.getSfID());
					eced.setAppliedDate(CPSUtils.getCurrentDateWithTime());
					eced.setIpAddress(crb.getIpAddress());
					if(crb.getType().equals("settlement")){
					eced.setRequestType(CPSConstants.SETTLEMENT);
					}
					if(crb.getType().equals("cghssettlement")){
						eced.setRequestType(CPSConstants.SETTLEMENT);
					}
					if(crb.getType().equals("nonCghsEmergency")){
						eced.setRequestType(CPSConstants.NONCGHSEMERGENCY);
						}
					
					if(crb.getType().equals("emergency")){
						eced.setRequestType(CPSConstants.EMERGENCY);
						}
					
					if(crb.getType().equals("advance")){
					eced.setRequestType(CPSConstants.ADVANCE);}
					if(crb.getType().equals("reimbursement")){
					eced.setRequestType(CPSConstants.REIMBURSEMENT);}
					if(crb.getType().equals("treatment")){
						eced.setRequestType(CPSConstants.TREATMENT);
					}
					if(crb.getType().equals("cghsreimbursement")){
						eced.setRequestType(CPSConstants.REIMBURSEMENT);
						//eced.setRequestType(CPSConstants.CGHSREIMBURSEMENT);
					}
					if(crb.getType().equals("cghsAdvance")){
						eced.setRequestType(CPSConstants.ADVANCE);
						//eced.setRequestType(CPSConstants.CGHSREIMBURSEMENT);
					} 
					if(crb.getType().equals("noncghsReimbursement")){
						//eced.setRequestType(CPSConstants.NONCGHSREIM);
						eced.setRequestType(CPSConstants.NONCGHSREIM2);
						//nonCghsReim.
						//eced.setRequestType(CPSConstants.CGHSREIMBURSEMENT);
					}
					
					session.save(eced);
				  RequestBean rb = new RequestBean();
				BeanUtils.copyProperties(crb, rb);
				crb.setMessage(txRequestProcess.initWorkflow(rb));
			
				/**
				 * If No workflow is assigned then update the details in the table
				 */
				if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())) {
					log.debug("::request workflow not assigned, so update in the main table");
					BeanUtils.copyProperties(rb, crb);
					crb.setMessage(approvedRequest(crb));
		}
				}
				}

		} catch (Exception e) {
			throw e;
		}
		return crb.getMessage();
	}

	public String submitTxnDetails(CGHSRequestProcessBean crb) throws Exception {
		Session session = null;
		/*
		 * PreparedStatement ps=null; ResultSet rsq = null; Connection con = null;
		 */String details = null;
		try {
			crb.setStatus(2);// for initial request
			crb.setAppliedDate(CPSUtils.getCurrentDateWithTime());
			session = hibernateUtils.getSession();
			// con = session.connection();
			/*
			 * details = "select emp.sfid,emp.designation_id,emp.office_id,emp.cgsh_number,epd.basic_pay,epd.grade_pay,"
			 * +"(select adm.address1||','||adm.address2||','||adm.address3||','||dm.name||','||sm.name||','||adm.pincode||','||adm.mobile_number from  " +
			 * "address_details_master adm,state_master sm,district_master dm where sfid=? and adm.address_type_id=5 and dm.status=1 and sm.status=1 and adm.state=sm.id and adm.district=dm.id)as address "
			 * +"from emp_master emp,designation_master dm,departments_master dept,org_role_instance ori,emp_payment_details epd"
			 * +" where emp.sfid=? and emp.status=1 and dm.status=1 and dept.status=1 and ori.status=1 and emp.designation_id=dm.id "
			 * +"and emp.office_id=ori.org_role_id and ori.department_id=dept.department_id and emp.sfid=epd.sfid"; ps = con.prepareStatement(details); ps.setString(1, crb.getSfID()); ps.setString(2,
			 * crb.getSfID()); rsq = ps.executeQuery(); if(rsq.next()) { crb.setDesignationId(rsq.getString("designation_id")); crb.setDepartmentId(rsq.getString("office_id"));
			 * crb.setCghsCardNumber(rsq.getString("cgsh_number")); crb.setAddress(rsq.getString("address")); crb.setBasicPay(rsq.getString("basic_pay")); crb.setGradePay(rsq.getString("grade_pay"));
			 * }
			 */
			/**
			 * if request type is other than Consultation then this if block will be executed
			 */
			if (CPSUtils.compareStrings(crb.getCghsRequestTypeId(), "3")) {
				details = "select id from cghs_ward_type_master where (select basic_pay from emp_payment_details where sfid=?) between start_basic_pay and end_basic_pay and status=1";
				crb.setWardTypeId(session.createSQLQuery(details).setString(0, crb.getSfID()).uniqueResult().toString());
			} else {
				/**
				 * if the request type is Consultation then there no entitled ward just set Zero in ward type
				 */
				crb.setWardTypeId("0");
			}
		     
			 
		    // crb.getRequestID();
			session.save(crb);
			session.flush();
		 
			
			crb.setMessage(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		} 
		return crb.getMessage();
	}

	public String submitTxnDetails(CghsAdvanceRequestDTO car) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			if (CPSUtils.isNull(session.createCriteria(CghsAdvanceRequestDTO.class).add(Expression.not(Expression.in("status", new Integer[] { 9, 6 }))).add(
					Expression.eq("referrenceRequestID", car.getReferrenceRequestID())).uniqueResult())) {
				car.setAppliedDate(new Date());
				car.setStatus(2);
				session.saveOrUpdate(car);
				// update cghs_request_details status column to 35 it means user applied for cghs advance request
				cghsDomainObject.updateTxnDetailsStatus(CPSConstants.STATUSADVREQPROCESSING, car.getReferrenceRequestID(), CGHSRequestProcessBean.class);
				session.flush();
				car.setMessage(CPSConstants.SUCCESS);
			} else {
				car.setMessage(CPSConstants.DUPLICATE);
			}
		} catch (Exception e) {
			throw e;
		} 
		return car.getMessage();
	}

	public String submitTxnDetails(CghsReimbursementRequestDTO crr) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			crr.setAppliedDate(CPSUtils.getCurrentDateWithTime());
			crr.setStatus(2);
			if (CPSUtils.compareStrings(crr.getType(), CPSConstants.CGHSREIMBURSEMENT)) {
				crr.setRequestType(CPSConstants.CR);
				crr.setRequestFormFile("reimbursementFormFile_" + crr.getRequestID() + ".pdf");
			} else if (CPSUtils.compareStrings(crr.getType(), CPSConstants.NONCGHSREIMBURSEMENT)) {
				crr.setRequestType(CPSConstants.NCR);
				crr.setRequestFormFile("reimbursementFormFile_" + crr.getRequestID() + ".pdf");
			} else if (CPSUtils.compareStrings(crr.getType(), CPSConstants.CGHSSETTLEMENT)) {
				crr.setRequestType(CPSConstants.CS);
			}
			if (CPSUtils.compareStrings(crr.getRequestType(), CPSConstants.CR) || CPSUtils.compareStrings(crr.getRequestType(), CPSConstants.CS)) {
				/**
				 * if the request type is CghsReimbursement or CghsSettelment then check the duplicate entry
				 */
				if (CPSUtils.isNull(session.createCriteria(CghsReimbursementRequestDTO.class).add(Expression.not(Expression.in("status", new Integer[] { 9, 6 }))).add(
						Expression.eq("referrenceRequestID", crr.getReferrenceRequestID())).uniqueResult())) {
					//crr.setFamilyMembetId(Integer.parseInt(crr.getFamilyMembetId()));
					//session.saveOrUpdate(crr);
					session.save(crr);
					session.flush();
					crr.setMessage(CPSConstants.SUCCESS);
				} else {
					crr.setMessage(CPSConstants.DUPLICATE);
				}
			} else {
				/**
				 * if request type id NonCghsReimburesement then no need of checking duplicate entry
				 */
				session.saveOrUpdate(crr);
				session.flush();
				crr.setMessage(CPSConstants.SUCCESS);
			}

		} catch (Exception e) {
		
			throw e;		
		} 
		return crr.getMessage();
	}

	public String approvedRequest(CGHSRequestProcessBean cghsReqBean) throws Exception {
		log.debug("::<<<<<CghsRequestProcess<<<<<<Method>>>>>>>>>>>>>>>approvedRequest(CGHSRequestProcessBean cghsReqBean)>>>>>>>>>");
		RequestBean rb = null;
		try {
			rb = new RequestBean();
			BeanUtils.copyProperties(cghsReqBean, rb);
			rb = txRequestProcess.approvedRequest(rb);
			if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())) {
				if (CPSUtils.compareStrings(CPSConstants.CGHS, cghsReqBean.getRequestType())) {
					BeanUtils.copyProperties(rb, cghsReqBean);
					rb.setMessage(cghsDomainObject.updateTxnDetails(cghsReqBean));
				} else if (CPSUtils.compareStrings(CPSConstants.CGHS_ADVANCE, cghsReqBean.getRequestType())) {
					CghsAdvanceRequestDTO crpb = new CghsAdvanceRequestDTO();
					BeanUtils.copyProperties(rb, crpb);
					cghsDomainObject.updateTxnDetailsStatus(CPSConstants.STATUSSETTREQPENDING, getReferenceRequestID(CghsAdvanceRequestDTO.class, crpb.getRequestID()), CGHSRequestProcessBean.class);
					rb.setMessage(cghsDomainObject.updateCghsAdvanceDetails(crpb));
				} else if (CPSUtils.compareStrings(CPSConstants.CGHS_SETTLEMENT, cghsReqBean.getRequestType())) {
					CghsReimbursementRequestDTO crr = new CghsReimbursementRequestDTO();
					BeanUtils.copyProperties(rb, crr);
					cghsDomainObject.updateTxnDetailsStatus(CPSConstants.STATUSCOMPLETED, getReferenceRequestID(CghsReimbursementRequestDTO.class, crr.getRequestID()), CGHSRequestProcessBean.class);
					rb.setMessage(cghsDomainObject.updateCghsReimbursementDetails(crr));
				} else if (CPSUtils.compareStrings(CPSConstants.CGHS_REIMBURSEMENT, cghsReqBean.getRequestType())) {
					CghsReimbursementRequestDTO crr = new CghsReimbursementRequestDTO();
					BeanUtils.copyProperties(rb, crr);
					cghsDomainObject.updateTxnDetailsStatus(CPSConstants.STATUSCOMPLETED, getReferenceRequestID(CghsReimbursementRequestDTO.class, crr.getRequestID()), CGHSRequestProcessBean.class);
					rb.setMessage(cghsDomainObject.updateCghsReimbursementDetails(crr));
				} else if (CPSUtils.compareStrings(CPSConstants.NON_CGHS_REIMBURSEMENT, cghsReqBean.getRequestType())) {
					CghsReimbursementRequestDTO crr = new CghsReimbursementRequestDTO();
					BeanUtils.copyProperties(rb, crr);
					rb.setMessage(cghsDomainObject.updateCghsReimbursementDetails(crr));
				} else if (CPSUtils.compareStrings(CPSConstants.CGHS_EMERGENCY, cghsReqBean.getRequestType()) || CPSUtils.compareStrings(CPSConstants.NON_CGHS_EMERGENCY, cghsReqBean.getRequestType())) {
					CghsEmergencyRequestDTO cerd = new CghsEmergencyRequestDTO();
					BeanUtils.copyProperties(rb, cerd);
					rb.setMessage(cghsDomainObject.updateCghsEmergencyDetails(cerd));
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return rb.getMessage();
	}

	public String approvedRequest(CghsAdvanceRequestDTO car) throws Exception {
		log.debug("::<<<<<CghsRequestProcess<<<<<<Method>>>>>>>>>>>>>>>approvedRequest(CGHSRequestProcessBean cghsReqBean)>>>>>>>>>");
		RequestBean rb = null;
		try {
			rb = new RequestBean();
			BeanUtils.copyProperties(car, rb);
			rb = txRequestProcess.approvedRequest(rb);
			if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())) {
				log.debug("::request workflow completed, so update in the main table");
				BeanUtils.copyProperties(rb, car);
				// rb.setMessage(cghsDomainObject.updateTxnDetails(car));
			}
		} catch (Exception e) {
			throw e;
		}
		return rb.getMessage();
	}

	/*
	 * public String generatePermissionLetter(String requestId,HttpSession session) throws Exception { String permissionFile=null; String requestTypeID = null; Session session1 = null; try {
	 * session1=hibernateUtils.getSession(); requestTypeID = String.valueOf(session1.createSQLQuery("select cghs_request_type_id wardName from cghs_request_details where request_id=?")
	 * .addScalar("wardName", Hibernate.STRING) .setString(0, requestId).uniqueResult()); if(CPSUtils.compareStrings(requestTypeID, "1")) { //if requestTypeID(Means he/she applied for Consultaion) is
	 * null it means treatment request type is for Consultation HashMap<String, String> pmap = new HashMap<String, String>(); pmap.put("RequestID", requestId); jasperReportCreator.createPdf(session,
	 * "ConsultationReport.jasper", pmap, "CghsAttachments", "permissionFile_"+requestId, false); }else { //if wardTypeId is not null it means treatment request type is for Investigation or Admission
	 * HashMap<String, String> pmap = new HashMap<String, String>(); pmap.put("RequestID", requestId); jasperReportCreator.createPdf(session, "PermissionLetter.jasper", pmap, "CghsAttachments",
	 * "permissionFile_"+requestId, false); } permissionFile = "permissionFile_"+requestId+".pdf"; }catch (Exception e) { throw e; }finally { //session1.close(); } return permissionFile; }
	 */
	public String submitTxnDetails(CghsEmergencyRequestDTO cer) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			cer.setAppliedDate(CPSUtils.getCurrentDateWithTime());
			cer.setStatus(2);
			if (CPSUtils.isNullOrEmpty(cer.getHospitalAddress())) {
				cer.setRequestType("C");
			} else {
				cer.setRequestType("NC");
			}
			session.saveOrUpdate(cer);
			session.flush();
			cer.setMessage(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		} 
		return cer.getMessage();
	}

	@SuppressWarnings("unchecked")
	public String getReferenceRequestID(Class beanName, String requestID) throws Exception {
		Session session = null;
		String referenceRequestID = null;
		try {
			session = hibernateUtils.getSession();
		//	referenceRequestID = session.createQuery("select referrenceRequestID from " + beanName.getSimpleName() + " where requestID=" + requestID).uniqueResult().toString();
		} catch (Exception e) {
			throw e;
		} 
		return referenceRequestID;

	}

	public String updateStatusToDecline(String requestID, String requestType, String status) throws Exception {
		String message = null;
		String referenceRequestID = null;
		try {
			if (CPSUtils.compareStrings(requestType, CPSConstants.CGHS)) {
				message = cghsDomainObject.updateTxnDetailsStatus(status, requestID, CGHSRequestProcessBean.class);
			} else if (CPSUtils.compareStrings(requestType, CPSConstants.CGHS_ADVANCE)) {
				message = cghsDomainObject.updateTxnDetailsStatus(status, requestID, CghsAdvanceRequestDTO.class);
				referenceRequestID = cghsDomainObject.updateReferenceRequestStatus(requestID, requestType);
				message = cghsDomainObject.updateTxnDetailsStatus(CPSConstants.STATUSREIMREQPENDING, referenceRequestID, CGHSRequestProcessBean.class);
			} else if (CPSUtils.compareStrings(requestType, CPSConstants.CGHS_REIMBURSEMENT)) {
				message = cghsDomainObject.updateTxnDetailsStatus(status, requestID, CghsReimbursementRequestDTO.class);
				referenceRequestID = cghsDomainObject.updateReferenceRequestStatus(requestID, requestType);
				message = cghsDomainObject.updateTxnDetailsStatus(CPSConstants.STATUSREIMREQPENDING, referenceRequestID, CGHSRequestProcessBean.class);
			} else if (CPSUtils.compareStrings(requestType, CPSConstants.CGHS_SETTLEMENT)) {
				message = cghsDomainObject.updateTxnDetailsStatus(status, requestID, CghsReimbursementRequestDTO.class);
				referenceRequestID = cghsDomainObject.updateReferenceRequestStatus(requestID, requestType);
				message = cghsDomainObject.updateTxnDetailsStatus(CPSConstants.STATUSSETTREQPENDING, referenceRequestID, CGHSRequestProcessBean.class);
			} else if (CPSUtils.compareStrings(requestType, CPSConstants.NON_CGHS_REIMBURSEMENT)) {
				message = cghsDomainObject.updateTxnDetailsStatus(status, requestID, CghsReimbursementRequestDTO.class);
			} else if (CPSUtils.compareStrings(requestType, CPSConstants.CGHS_EMERGENCY) || CPSUtils.compareStrings(requestType, CPSConstants.NON_CGHS_EMERGENCY)) {
				message = cghsDomainObject.updateTxnDetailsStatus(status, requestID, CghsEmergencyRequestDTO.class);
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		return message;

	}
	//
	public String submitAdvanceTxnDetails(CGHSRequestProcessBean crb) throws Exception {
		Session session = null;
		/*
		 * PreparedStatement ps=null; ResultSet rsq = null; Connection con = null;
		 */String details = null;
		try {
			crb.setStatus(2);// for initial request
			crb.setAppliedDate(CPSUtils.getCurrentDateWithTime());
			session = hibernateUtils.getSession();
			
			/**
			 * if request type is other than Consultation then this if block will be executed
			 */
			if (CPSUtils.compareStrings(crb.getCghsRequestTypeId(), "3")) {
				details = "select id from cghs_ward_type_master where (select basic_pay from emp_payment_details where sfid=?) between start_basic_pay and end_basic_pay and status=1";
				crb.setWardTypeId(session.createSQLQuery(details).setString(0, crb.getSfID()).uniqueResult().toString());
			} else {
				/**
				 * if the request type is Consultation then there no entitled ward just set Zero in ward type
				 */
				crb.setWardTypeId("0");
			}
		     
		    	 crb.setStatus(35);
		    	 session.saveOrUpdate(crb);
		    	 session.flush();
		   crb.setMessage(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		} 
		return crb.getMessage();
	}
	public String submitReimbursementTxnDetails(CGHSRequestProcessBean crb) throws Exception {
		Session session = null;
		/*
		 * PreparedStatement ps=null; ResultSet rsq = null; Connection con = null;
		 */String details = null;
		try {
			crb.setStatus(2);// for initial request
			crb.setAppliedDate(CPSUtils.getCurrentDateWithTime());
			session = hibernateUtils.getSession();
			
			/**
			 * if request type is other than Consultation then this if block will be executed
			 */
			if (CPSUtils.compareStrings(crb.getCghsRequestTypeId(), "3")) {
				details = "select id from cghs_ward_type_master where (select basic_pay from emp_payment_details where sfid=?) between start_basic_pay and end_basic_pay and status=1";
				crb.setWardTypeId(session.createSQLQuery(details).setString(0, crb.getSfID()).uniqueResult().toString());
			} else {
				/**
				 * if the request type is Consultation then there no entitled ward just set Zero in ward type
				 */
				crb.setWardTypeId("0");
			}
		     
		    	 crb.setStatus(37);
		    	 session.saveOrUpdate(crb);
		    	 session.flush();
		   crb.setMessage(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		} 
		return crb.getMessage();
	}


public String updateFileStatus(CghsRequestBean cghsBean) throws Exception{
	Session session = null;
	  try{
			session = hibernateUtils.getSession();
			/*********
		    For Cghs Request Screen File values Updating:START
		    ***********/
		if(cghsBean.getRequestType().equals("normal")||  cghsBean.getRequestType() == "normal"){
			CGHSRequestProcessBean crpb = null;
 			crpb = (CGHSRequestProcessBean)session.createCriteria(CGHSRequestProcessBean.class).add(Expression.eq("requestID",cghsBean.getRequestId())).uniqueResult();
 			if(CPSUtils.isNullOrEmpty(crpb.getPrescriptionFile())){
 			crpb.setPrescriptionFile(cghsBean.getPrescriptionFileName());
			session.flush();
 			}
 			
		}
		/*********
	    For Cghs Advance Screen File values Updating:START
	    ***********/
		else if(cghsBean.getRequestType().equals("advance")|| cghsBean.getRequestType() == "advance"){
	       CghsAdvanceRequestDTO cghsAdvance = null;
	       cghsAdvance = (CghsAdvanceRequestDTO)session.createCriteria(CghsAdvanceRequestDTO.class).add(Expression.eq("requestID", cghsBean.getRequestId())).uniqueResult();
		   if(!CPSUtils.isNullOrEmpty(cghsBean.getCghsCardFileName())){
	       cghsAdvance.setCghsCardFile(cghsBean.getCghsCardFileName());
		   }
		   if(!CPSUtils.isNullOrEmpty(cghsBean.getEstimationFileName())){
		   cghsAdvance.setEstimationFile(cghsBean.getEstimationFileName());
		   session.flush();
		   }
		   
		}
		/*********
	    For Cghs Advance Screen File values Updating:END 
	    ***********/
		
		/*********
	    For Cghs Reimbursement Screen File values Updating:START 
	    ***********/
		
		else if(cghsBean.getRequestType().equals("reimbursement")||cghsBean.getRequestType() == "reimbursement"){
			CghsReimbursementRequestDTO cghsReim = null;
				
		    cghsReim = (CghsReimbursementRequestDTO)session.createCriteria(CghsReimbursementRequestDTO.class).add(Expression.eq("requestID", cghsBean.getRequestId())).uniqueResult();
			if(!CPSUtils.isNullOrEmpty(cghsBean.getCghsCardFileName())){
			cghsReim.setCghsCardFile(cghsBean.getCghsCardFileName());
			}
			if(!CPSUtils.isNullOrEmpty(cghsBean.getMedicalBillsFileName())){
			cghsReim.setMedicalBillsFile(cghsBean.getMedicalBillsFileName());
			}
			if(!CPSUtils.isNullOrEmpty(cghsBean.getRefundChequeFileName())){
			cghsReim.setRefundChequeFile(cghsBean.getRefundChequeFileName());
			}
			session.flush();
     			
     		CGHSRequestProcessBean crpb = null;
     		crpb = (CGHSRequestProcessBean)session.createCriteria(CGHSRequestProcessBean.class).add(Expression.eq("requestID",cghsReim.getReferrenceRequestID())).uniqueResult();
     		if(!CPSUtils.isNullOrEmpty(cghsBean.getPrescriptionFileName())){
     		crpb.setPrescriptionFile(cghsBean.getPrescriptionFileName());
     		}
     		session.flush();	
		}
		 /*********
	     For Cghs Reimbursement Screen File values Updating:END
		    ***********/
		
		  /*********
	      For Cghs Emergency Screen File values Updating:START
	      ***********/
		  else if(cghsBean.getRequestType().equals("emergency")||cghsBean.getRequestType() == "emergency"){
				CghsEmergencyRequestDTO cghsEmergency = null;
			
				cghsEmergency = (CghsEmergencyRequestDTO)session.createCriteria(CghsEmergencyRequestDTO.class).add(Expression.eq("requestID",cghsBean.getRequestId())).uniqueResult();
				if(!CPSUtils.isNullOrEmpty(cghsBean.getCghsCardFileName())){
				cghsEmergency.setCghsCardFile(cghsBean.getCghsCardFileName());
				}if(!CPSUtils.isNullOrEmpty(cghsBean.getDischargeSummeryFileName())){
				 cghsEmergency.setDischargeSummeryFile(cghsBean.getDischargeSummeryFileName());
				}if(!CPSUtils.isNullOrEmpty(cghsBean.getMedicalBillsFileName())){
				cghsEmergency.setMedicalBillsFile(cghsBean.getMedicalBillsFileName());
				}session.flush();
			}
		/*********
	    For Cghs Emergency Screen File values Updating:END 
	    ***********/
		return CPSConstants.SUCCESS;
	}catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	
}
}

