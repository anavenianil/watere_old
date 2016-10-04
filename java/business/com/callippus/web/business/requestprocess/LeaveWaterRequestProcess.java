package com.callippus.web.business.requestprocess;


import java.io.InputStream;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.impl.SessionImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.beans.workflowmapping.WorkFlowMappingBean;
import com.callippus.web.business.tx.workflow.TxRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.leave.beans.request.LeaveWaterApplicationBean;
import com.callippus.web.leave.beans.request.LeaveWaterRequestProcessBean;
import com.callippus.web.leave.dto.ErpAvailableLeaveSaveDTO;
import com.callippus.web.leave.dto.LeavesDaysMasterDTO;
import com.callippus.web.ltc.dto.LTCWaterRequestDTO;


@Service
public class LeaveWaterRequestProcess extends TxRequestProcess {

	private static Log log = LogFactory.getLog(LeaveWaterRequestProcess.class);

	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	@Autowired
	private TxRequestProcess txRequestProcess;

	public String initWorkflow(LeaveWaterRequestProcessBean leaveWRPB)
			throws Exception {

		String message = null;
		try {
			/**
			 * Generate Unique ID for this Request
			 */
			leaveWRPB.setRequestID(txRequestProcess
					.generateUniqueID(CPSConstants.REQUEST));
			leaveWRPB.setRequestId(leaveWRPB.getRequestID());
			if (CPSUtils.compareStrings(CPSConstants.LEAVE_ERP,
					leaveWRPB.getType())) {

				leaveWRPB.setRequestTypeID("10");
				leaveWRPB.setRequestType(CPSConstants.LEAVE_WATER);
				// leaveWRPB = setTadaMemberDetails(leaveWRPB);
				message = submitTxnDetails(leaveWRPB);

			}

			if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
				/**
				 * decide the work flow & insert into history table
				 */
				RequestBean rb = new RequestBean();
				BeanUtils.copyProperties(rb, leaveWRPB);
				txRequestProcess.initWorkflow(rb);
			}

		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}

		return leaveWRPB.getMessage();

	}

	@SuppressWarnings("unchecked")
	private String submitTxnDetails(LeaveWaterRequestProcessBean leaveWRPB)
			throws Exception {
		Session session = null;
		ErpAvailableLeaveSaveDTO erpAvailableLeaveSaveDTO = null;
		try {

			erpAvailableLeaveSaveDTO = new ErpAvailableLeaveSaveDTO();
			BeanUtils.copyProperties(erpAvailableLeaveSaveDTO, leaveWRPB);
			erpAvailableLeaveSaveDTO.setApplyDate(CPSUtils.convertStringToDate(CPSUtils.getCurrentDate()));
			erpAvailableLeaveSaveDTO.setPhnNo(leaveWRPB.getEmpDetailsList().getPersonalNumber());
			erpAvailableLeaveSaveDTO.setDesignation(leaveWRPB.getEmpDetailsList().getDesignation());
			erpAvailableLeaveSaveDTO.setStatus(1);
			session = hibernateUtils.getSession();
			session.createCriteria(LTCWaterRequestDTO.class).add(
					Expression.eq("sfID", leaveWRPB.getSfID()));
			session.save(erpAvailableLeaveSaveDTO);
			session.flush();
			leaveWRPB.setMessage(CPSConstants.SUCCESS);
			
			//this if block addedby bkr 23/06/2016 for amend leave purpose
			if(leaveWRPB.getAmendRequestId()!=null){
				
				Session session1 = null;
				session1 = hibernateUtils.getSession();
				try{
				session1.createQuery("update ErpAvailableLeaveSaveDTO set status='3', leaveStatus='6' where requestId=? ")
				.setString(0, leaveWRPB.getAmendRequestId()).executeUpdate();
				}catch(Exception e1){
					e1.printStackTrace();
					
				}finally{
				session1.clear();
				}
				
				//added by bkr 27/06/2016
				
				
				session = null;
				session = hibernateUtils.getSession();
				String sql = null;
				ErpAvailableLeaveSaveDTO erpAvailableLeaveSaveDTO2 = null;
				sql = "select leave.LEAVE_TYPE_ID  AS leaveType,leave.NO_OF_DAYS AS noOfDays ,leave.SFID AS sfID   FROM ERP_LEAVE_REQUEST_DETAILS leave WHERE leave.REQUEST_ID="
						+ leaveWRPB.getAmendRequestId() + "";
				erpAvailableLeaveSaveDTO2 = (ErpAvailableLeaveSaveDTO) session
						.createSQLQuery(sql)
						.addScalar("leaveType", Hibernate.STRING)
						.addScalar("noOfDays", Hibernate.STRING)
						.addScalar("sfID", Hibernate.STRING)
						.setResultTransformer(
								Transformers
										.aliasToBean(ErpAvailableLeaveSaveDTO.class))
						.uniqueResult();
				
				session = null;
				session = hibernateUtils.getSession();
				
				if (CPSUtils.compareStrings(CPSConstants.AL,erpAvailableLeaveSaveDTO2.getLeaveType())
						|| CPSUtils.compareStrings(CPSConstants.CLAL,erpAvailableLeaveSaveDTO2.getLeaveType())) {
					
					session.createQuery("update ErpEmpLeavesDTO set noOfDays=noOfDays+? where sfID=? and leaveCode='AL' ")
					.setString(0, erpAvailableLeaveSaveDTO2.getNoOfDays())
					.setString(1, erpAvailableLeaveSaveDTO2.getSfID())
					.executeUpdate();
					
				} else{
					session.createQuery(
							"update ErpEmpLeavesDTO set noOfDays=noOfDays+? where sfID=? and leaveCode='"+erpAvailableLeaveSaveDTO2.getLeaveType()+"' ")
							.setString(0,
									erpAvailableLeaveSaveDTO2.getNoOfDays())
							.setString(1, erpAvailableLeaveSaveDTO2.getSfID())
							.executeUpdate();
					
				}
				
			}

		} catch (Exception e) {
			leaveWRPB.setMessage(CPSConstants.FAILED);
			throw e;
		} finally {
			session.clear();
		}

		return leaveWRPB.getMessage();

	}

	private LeaveWaterRequestProcessBean setTadaMemberDetails(
			LeaveWaterRequestProcessBean leaveWRPB) throws Exception {

		Session session = null;
		try {
			session = hibernateUtils.getSession();
			LeaveWaterApplicationBean empBean = (LeaveWaterApplicationBean) leaveWRPB
					.getSession().getAttribute(CPSConstants.EMPLOYEEDETAILS);
			String workingLocation = (String) session
					.createQuery(
							"select workingLocation from OuterEmployeeBean where userSfid=?")
					.setString(0, leaveWRPB.getSfID().toUpperCase())
					.uniqueResult();
			leaveWRPB.setOrganizationID(workingLocation);
		} catch (Exception e) {
			throw e;
		}
		return leaveWRPB;
	}

	public String saveLeaveDays(LeaveWaterApplicationBean leaveWaterBean)
			throws Exception {
		log.debug("Enter saveLeaveDays method");

		Session session = null;
		LeavesDaysMasterDTO leavesDaysMasterDTO = null;
		try {

			leavesDaysMasterDTO = new LeavesDaysMasterDTO();
			BeanUtils.copyProperties(leavesDaysMasterDTO, leaveWaterBean);
			leavesDaysMasterDTO.setLeaveType("Annual Leave");
			leavesDaysMasterDTO.setPermanent(leaveWaterBean
					.getAnnualLeavePermanent());
			leavesDaysMasterDTO.setContract(leaveWaterBean
					.getAnnualLeaveContract());
			leavesDaysMasterDTO
					.setGender(leaveWaterBean.getAnnualLeaveGender());
			leavesDaysMasterDTO.setStatus(1);
			leavesDaysMasterDTO.setYear(leaveWaterBean.getLeaveYear());
			session = hibernateUtils.getSession();
			session.createCriteria(LeavesDaysMasterDTO.class);
			session.save(leavesDaysMasterDTO);
			session.flush();
			session.clear();

			// /2nd row
			
			log.debug("Enter saveLeaveDays method");

			leavesDaysMasterDTO.setLeaveType("Sick Leave");
			leavesDaysMasterDTO.setPermanent(leaveWaterBean
					.getSickLeavePermanent());
			leavesDaysMasterDTO.setContract(leaveWaterBean
					.getSickLeaveContract());
			leavesDaysMasterDTO.setGender(leaveWaterBean.getSickLeaveGender());
			leavesDaysMasterDTO.setStatus(1);
			leavesDaysMasterDTO.setYear(leaveWaterBean.getLeaveYear());
			session = hibernateUtils.getSession();
			session.createCriteria(LeavesDaysMasterDTO.class);
			session.save(leavesDaysMasterDTO);
			session.flush();
			session.clear();

			// 3rd row

			leavesDaysMasterDTO.setLeaveType("Maternity Leave");
			leavesDaysMasterDTO.setPermanent(leaveWaterBean
					.getMaternityLeavePermanent());
			leavesDaysMasterDTO.setContract(leaveWaterBean
					.getMaternityLeaveContract());
			leavesDaysMasterDTO.setGender(leaveWaterBean
					.getMaternityLeaveGender());
			leavesDaysMasterDTO.setStatus(1);
			leavesDaysMasterDTO.setYear(leaveWaterBean.getLeaveYear());
			session = hibernateUtils.getSession();
			session.createCriteria(LeavesDaysMasterDTO.class);
			session.save(leavesDaysMasterDTO);
			session.flush();
			session.clear();
			// 4th row
			leavesDaysMasterDTO.setLeaveType("Paternity Leave");
			leavesDaysMasterDTO.setPermanent(leaveWaterBean
					.getPaternityLeavePermanent());
			leavesDaysMasterDTO.setContract(leaveWaterBean
					.getPaternityLeaveContract());
			leavesDaysMasterDTO.setGender(leaveWaterBean
					.getPaternityLeaveGender());
			leavesDaysMasterDTO.setStatus(1);
			leavesDaysMasterDTO.setYear(leaveWaterBean.getLeaveYear());
			session = hibernateUtils.getSession();
			session.createCriteria(LeavesDaysMasterDTO.class);
			session.save(leavesDaysMasterDTO);
			session.flush();
			session.clear();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.clear();
		}

		return CPSConstants.SUCCESS;

	}

	@SuppressWarnings("unchecked")
	public WorkFlowMappingBean getLeaveRequestDetails(
			WorkFlowMappingBean workflowMap) throws Exception {

		Session session = null;
		String sql = null;
		ErpAvailableLeaveSaveDTO erpAvailableLeaveSaveDTO = null;

		try {

			session = hibernateUtils.getSession();// session =
													// sessionFactory.openSession();
			String requestId = workflowMap.getRequestId();
			sql = "select leave.REQUEST_ID AS requestId,leave.LEAVE_TYPE_ID AS leaveType,leave.FROM_DATE AS fromDate,leave.TO_DATE AS toDate,leave.NO_OF_DAYS AS noOfDays,leave.REASON AS reason,leave.REQUESTED_DATE AS applyDate,leave.CONTACT_NUMBER AS contactNo,leave.PRESCRIPTION_COPY AS prescriptiondoc    FROM ERP_LEAVE_REQUEST_DETAILS leave WHERE leave.REQUEST_ID="
					+ requestId + "";
			erpAvailableLeaveSaveDTO = (ErpAvailableLeaveSaveDTO) session
					.createSQLQuery(sql)
					.addScalar("requestId", Hibernate.STRING)
					.addScalar("leaveType", Hibernate.STRING)
					.addScalar("fromDate", Hibernate.DATE)
					.addScalar("toDate", Hibernate.DATE)
					.addScalar("noOfDays", Hibernate.STRING)
					.addScalar("reason", Hibernate.STRING)
					.addScalar("applyDate", Hibernate.DATE)
					.addScalar("contactNo", Hibernate.STRING).addScalar("prescriptiondoc", Hibernate.STRING)
					.setResultTransformer(
							Transformers
									.aliasToBean(ErpAvailableLeaveSaveDTO.class))
					.uniqueResult();
			workflowMap.setErpAvailableLeaveSaveDTO(erpAvailableLeaveSaveDTO);

		} catch (Exception e) {
			throw e;
		} finally {

			session.flush();

		}
		return workflowMap;

	}

	// added 13/06/2016
	public String approvedRequest(LeaveWaterRequestProcessBean processBean)
			throws Exception {
		RequestBean rb = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			rb = new RequestBean();
			if (CPSUtils.compareStrings(processBean.getRequestTypeID(), "10")) {
				processBean
						.setHistoryID(processBean.getHistoryID().split(",")[0]);
			} else {
				processBean
						.setHistoryID(processBean.getHistoryID().split(",")[0]);
			}
			BeanUtils.copyProperties(rb, processBean);

			rb = txRequestProcess.approvedRequest(rb);

			if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())) {
				log.debug("::request workflow completed, so update in the main table");
				//request workflow completed, so update in the main table
				session.createQuery("update ErpAvailableLeaveSaveDTO set status='2' where requestId=? ")
						.setString(0, rb.getRequestId()).executeUpdate();
				//;

				session = null;
				session = hibernateUtils.getSession();
				String sql = null;
				ErpAvailableLeaveSaveDTO erpAvailableLeaveSaveDTO = null;
				sql = "select leave.LEAVE_TYPE_ID  AS leaveType,leave.NO_OF_DAYS AS noOfDays ,leave.SFID AS sfID   FROM ERP_LEAVE_REQUEST_DETAILS leave WHERE leave.REQUEST_ID="
						+ rb.getRequestId() + "";
				erpAvailableLeaveSaveDTO = (ErpAvailableLeaveSaveDTO) session
						.createSQLQuery(sql)
						.addScalar("leaveType", Hibernate.STRING)
						.addScalar("noOfDays", Hibernate.STRING)
						.addScalar("sfID", Hibernate.STRING)
						.setResultTransformer(
								Transformers
										.aliasToBean(ErpAvailableLeaveSaveDTO.class))
						.uniqueResult();
				session = null;
				session = hibernateUtils.getSession();
				if (CPSUtils.compareStrings(CPSConstants.AL,
						erpAvailableLeaveSaveDTO.getLeaveType())
						|| CPSUtils.compareStrings(CPSConstants.CLAL,
								erpAvailableLeaveSaveDTO.getLeaveType())) {
					
					
					
					
					/*session.createQuery(
							"update ErpAvailableLeavesDTO set annualLeaves=annualLeaves-? where sfID=?")
							.setString(0,
									erpAvailableLeaveSaveDTO.getNoOfDays())
							.setString(1, erpAvailableLeaveSaveDTO.getSfID())
							.executeUpdate();*/
					
					
					session.createQuery(
							"update ErpEmpLeavesDTO set noOfDays=noOfDays-? where sfID=? and leaveCode='AL' ")
							.setString(0,
									erpAvailableLeaveSaveDTO.getNoOfDays())
							.setString(1, erpAvailableLeaveSaveDTO.getSfID())
							.executeUpdate();
					
					
					
				}else{
					session.createQuery(
							"update ErpEmpLeavesDTO set noOfDays=noOfDays-? where sfID=? and leaveCode='"+erpAvailableLeaveSaveDTO.getLeaveType()+"' ")
							.setString(0,
									erpAvailableLeaveSaveDTO.getNoOfDays())
							.setString(1, erpAvailableLeaveSaveDTO.getSfID())
							.executeUpdate();
					
				}
			/*	if (CPSUtils.compareStrings(CPSConstants.SLU,
						erpAvailableLeaveSaveDTO.getLeaveType())) {
					session.createQuery(
							"update ErpAvailableLeavesDTO set sickLeaves=sickLeaves-? where sfID=?")
							.setString(0,
									erpAvailableLeaveSaveDTO.getNoOfDays())
							.setString(1, erpAvailableLeaveSaveDTO.getSfID())
							.executeUpdate();
					
					
					
				}
				if (CPSUtils.compareStrings(CPSConstants.MLU,
						erpAvailableLeaveSaveDTO.getLeaveType())) {
					session.createQuery(
							"update ErpAvailableLeavesDTO set maternityLeaves=maternityLeaves-? where sfID=?")
							.setString(0,
									erpAvailableLeaveSaveDTO.getNoOfDays())
							.setString(1, erpAvailableLeaveSaveDTO.getSfID())
							.executeUpdate();
				}
				if (CPSUtils.compareStrings(CPSConstants.PLU,
						erpAvailableLeaveSaveDTO.getLeaveType())) {
					session.createQuery(
							"update ErpAvailableLeavesDTO set peternityLeaves=annualLeaves-? where sfID=?")
							.setString(0,
									erpAvailableLeaveSaveDTO.getNoOfDays())
							.setString(1, erpAvailableLeaveSaveDTO.getSfID())
							.executeUpdate();
				}*/

			}

		} catch (Exception e) {
			throw e;
		}
		return rb.getMessage();
	}

	public String leveasUpdate(
			LeaveWaterRequestProcessBean leaveWaterRequestProcessBean)
			throws Exception {
		Session session = null;
		try {

			session = hibernateUtils.getSession();
			if (CPSUtils.compareStrings(CPSConstants.AL,
					leaveWaterRequestProcessBean.getLeaveType())
					|| CPSUtils.compareStrings(CPSConstants.CLAL,
							leaveWaterRequestProcessBean.getLeaveType())) {
				session.createQuery(
						"update ErpAvailableLeavesDTO set annualLeaves=annualLeaves-? where sfID=?")
						.setString(0,
								leaveWaterRequestProcessBean.getNoOfDays())
						.setString(1, leaveWaterRequestProcessBean.getSfID())
						.executeUpdate();
			}
			if (CPSUtils.compareStrings(CPSConstants.SLU,
					leaveWaterRequestProcessBean.getLeaveType())) {
				session.createQuery(
						"update ErpAvailableLeavesDTO set sickLeaves=sickLeaves-? where sfID=?")
						.setString(0,
								leaveWaterRequestProcessBean.getNoOfDays())
						.setString(1, leaveWaterRequestProcessBean.getSfID())
						.executeUpdate();
			}
			if (CPSUtils.compareStrings(CPSConstants.MLU,
					leaveWaterRequestProcessBean.getLeaveType())) {
				session.createQuery(
						"update ErpAvailableLeavesDTO set maternityLeaves=maternityLeaves-? where sfID=?")
						.setString(0,
								leaveWaterRequestProcessBean.getNoOfDays())
						.setString(1, leaveWaterRequestProcessBean.getSfID())
						.executeUpdate();
			}
			if (CPSUtils.compareStrings(CPSConstants.PLU,
					leaveWaterRequestProcessBean.getLeaveType())) {
				session.createQuery(
						"update ErpAvailableLeavesDTO set peternityLeaves=peternityLeaves-? where sfID=?")
						.setString(0,
								leaveWaterRequestProcessBean.getNoOfDays())
						.setString(1, leaveWaterRequestProcessBean.getSfID())
						.executeUpdate();
			}

		} catch (Exception e) {
			throw e;
		}

		return "success";
	}

	@SuppressWarnings("unchecked")
	public List<ErpAvailableLeaveSaveDTO> getErpAppliedLeavesList(
			LeaveWaterApplicationBean leaveWaterBean) throws Exception {
		
		List<ErpAvailableLeaveSaveDTO> erpAppliedLeavesList=null;
		Session session=null;
		String sql=null;
		String sfid=leaveWaterBean.getSfid();
		if(sfid==null){
			sfid = leaveWaterBean.getSfID();
			leaveWaterBean.setSfid(sfid);
		}
		try{
			session=hibernateUtils.getSession();
			
			sql="select leave.REQUEST_ID AS requestId, leave.LEAVE_TYPE_ID AS leaveType ,leave.FROM_DATE AS fromDate,leave.TO_DATE AS toDate, "
					+ "leave.NO_OF_DAYS AS noOfDays,REQUESTED_DATE AS applyDate  "
					+ "FROM ERP_LEAVE_REQUEST_DETAILS leave where STATUS='2' and SFID='"+leaveWaterBean.getSfID()+"' ";
			
			log.debug("sql"+sql);
			leaveWaterBean.setErpAppliedLeavesList =session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("leaveType", Hibernate.STRING).addScalar("fromDate", Hibernate.DATE).addScalar("toDate", Hibernate.DATE).addScalar("noOfDays", Hibernate.STRING).addScalar("applyDate", Hibernate.DATE).setResultTransformer(Transformers.aliasToBean(ErpAvailableLeaveSaveDTO.class)).list();
			
			
		
		}catch(Exception e){
			throw e;
		}
		return erpAppliedLeavesList;
	}

	public String cancelErpLeave(LeaveWaterApplicationBean leaveWaterBean) throws Exception {
		Session session =null;
		session=hibernateUtils.getSession();
		try {
			log.debug("leave cancel days back here");
			
			session.createQuery("update ErpAvailableLeaveSaveDTO set status='3' where  requestId=? and sfID=? ")
					.setString(0, leaveWaterBean.getRequestId())
					.setString(1, leaveWaterBean.getSfID())
					.executeUpdate();
			
			session = null;
			session = hibernateUtils.getSession();
			
			if (CPSUtils.compareStrings(CPSConstants.AL,leaveWaterBean.getLeaveType())
					|| CPSUtils.compareStrings(CPSConstants.CLAL,leaveWaterBean.getLeaveType())) {
				
				session.createQuery("update ErpAvailableLeavesDTO set annualLeaves=annualLeaves+? where sfID=?")
						.setString(0, leaveWaterBean.getNoOfDays())
						.setString(1, leaveWaterBean.getSfID())
						.executeUpdate();
				
				
			} else if (CPSUtils.compareStrings(CPSConstants.SLU,
					leaveWaterBean.getLeaveType())) {
				
				session.createQuery("update ErpAvailableLeavesDTO set sickLeaves=sickLeaves+? where sfID=?")
						.setString(0, leaveWaterBean.getNoOfDays())
						.setString(1, leaveWaterBean.getSfID())
						.executeUpdate();
				
				
				
			} else if (CPSUtils.compareStrings(CPSConstants.MLU,
					leaveWaterBean.getLeaveType())) {
				
				session.createQuery("update ErpAvailableLeavesDTO set maternityLeaves=maternityLeaves+? where sfID=?")
						.setString(0, leaveWaterBean.getNoOfDays())
						.setString(1, leaveWaterBean.getSfID())
						.executeUpdate();
				
				
			} else 	if (CPSUtils.compareStrings(CPSConstants.PLU,
					leaveWaterBean.getLeaveType())) {
				
				
				session.createQuery("update ErpAvailableLeavesDTO set peternityLeaves=peternityLeaves+? where sfID=?")
						.setString(0, leaveWaterBean.getNoOfDays())
						.setString(1, leaveWaterBean.getSfID())
						.executeUpdate();
				
			}
			

			leaveWaterBean.setResult(CPSConstants.SUCCESS);
			

			
		} catch (Exception e) {

			throw e;
		}
		
		return CPSConstants.SUCCESS;
		
	}

	public String updateErpLeaveRequestDetails(String requestID) throws Exception {
		
		try {
			Session session = null;
			session = hibernateUtils.getSession();
			
			session.createQuery("update ErpAvailableLeaveSaveDTO set status='0' where  requestId=?  ")
			.setString(0, requestID)
			.executeUpdate();
			
		} catch (Exception e) {
		throw e;
		}
		return requestID;
		
	}

	
	
	
///--------------------------------------* Cancel Leave Workflow Start Here  *--------------------------------------------------------
	
	public LeaveWaterApplicationBean erpLeaveCancelHome(LeaveWaterApplicationBean leaveWaterBean) throws Exception {
		
		Session session = null;
		String sql = null;
		ErpAvailableLeaveSaveDTO erpAvailableLeaveSaveDTO = null;

		try {

			session = hibernateUtils.getSession();// session =
													// sessionFactory.openSession();
			String requestId = leaveWaterBean.getRequestId();
			sql = "select leave.REQUEST_ID AS requestId,leave.LEAVE_TYPE_ID AS leaveType,leave.FROM_DATE AS fromDate,leave.TO_DATE AS toDate,leave.NO_OF_DAYS AS noOfDays,leave.REASON AS reason,leave.REQUESTED_DATE AS applyDate,leave.CONTACT_NUMBER AS contactNo ,leave.REASON_LEAVECANCELLATION  AS reasonCancellation,leave.LEAVE_STATUS AS leaveStatus   FROM ERP_LEAVE_REQUEST_DETAILS leave WHERE leave.REQUEST_ID="
					+ requestId + "";
			erpAvailableLeaveSaveDTO = (ErpAvailableLeaveSaveDTO) session
					.createSQLQuery(sql)
					.addScalar("requestId", Hibernate.STRING)
					.addScalar("leaveType", Hibernate.STRING)
					.addScalar("fromDate", Hibernate.DATE)
					.addScalar("toDate", Hibernate.DATE)
					.addScalar("noOfDays", Hibernate.STRING)
					.addScalar("reason", Hibernate.STRING)
					.addScalar("applyDate", Hibernate.DATE)
					.addScalar("contactNo", Hibernate.STRING)
					.addScalar("reasonCancellation", Hibernate.STRING).addScalar("leaveStatus", Hibernate.INTEGER)
					.setResultTransformer(
							Transformers
									.aliasToBean(ErpAvailableLeaveSaveDTO.class))
					.uniqueResult();
			leaveWaterBean.setErpAvailableLeaveSaveDTO(erpAvailableLeaveSaveDTO);

		} catch (Exception e) {
			throw e;
		} finally {

			session.flush();

		}
		return leaveWaterBean;

		
	}
	
	
	
	public String initWorkflow1(LeaveWaterRequestProcessBean leaveWRPB)
			throws Exception {

		String message = null;
		try {
			/**
			 * Generate Unique ID for this Request
			 */
			leaveWRPB.setRequestID(txRequestProcess
					.generateUniqueID(CPSConstants.REQUEST));
			leaveWRPB.setRequestId(leaveWRPB.getRequestID());
			if (CPSUtils.compareStrings(CPSConstants.LEAVE_ERP_CANCEL,
					leaveWRPB.getType())) {

				leaveWRPB.setRequestTypeID("11");
				leaveWRPB.setRequestType(CPSConstants.LEAVE_WATER_CANCEL);
				message = submitReasonTxnDetails(leaveWRPB);

			}

			if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
				/**
				 * decide the work flow & insert into history table
				 */
				RequestBean rb = new RequestBean();
				BeanUtils.copyProperties(rb, leaveWRPB);
				txRequestProcess.initWorkflow(rb);
			}

		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		return leaveWRPB.getMessage();

	}

	private String submitReasonTxnDetails(LeaveWaterRequestProcessBean leaveWRPB) throws Exception {
		
		try {
			Session session = null;
			session = hibernateUtils.getSession();
			
			session.createQuery("update ErpAvailableLeaveSaveDTO set reasonCancellation=?,cancelRequestId=?,leaveStatus='5' where  requestId=?  ")
			.setString(0, leaveWRPB.getReasonCancellation()).setString(1, leaveWRPB.getRequestID()).setString(2, leaveWRPB.getCancelRequestId())
			.executeUpdate();
			leaveWRPB.setMessage(CPSConstants.SUCCESS);
			
		} catch (Exception e) {
			leaveWRPB.setMessage(CPSConstants.FAILED);
			throw e;
			
		}
		return leaveWRPB.getMessage();
	}
	
	@SuppressWarnings("unchecked")
	public WorkFlowMappingBean getLeaveRequestDetailsCancel(
			WorkFlowMappingBean workflowMap) throws Exception {

		Session session = null;
		String sql = null;
		ErpAvailableLeaveSaveDTO erpAvailableLeaveSaveDTO = null;

		try {

			session = hibernateUtils.getSession();// session =
													// sessionFactory.openSession();
			String requestId = workflowMap.getCancelRequestId();
			sql = "select leave.REQUEST_ID AS requestId,leave.LEAVE_TYPE_ID AS leaveType,leave.FROM_DATE AS fromDate,leave.TO_DATE AS toDate,leave.NO_OF_DAYS AS noOfDays,leave.REASON AS reason,leave.REQUESTED_DATE AS applyDate,leave.CONTACT_NUMBER AS contactNo  ,leave.REASON_LEAVECANCELLATION  AS reasonCancellation    FROM ERP_LEAVE_REQUEST_DETAILS leave WHERE leave.REQUEST_ID="
					+ requestId + "";
			erpAvailableLeaveSaveDTO = (ErpAvailableLeaveSaveDTO) session
					.createSQLQuery(sql)
					.addScalar("requestId", Hibernate.STRING)
					.addScalar("leaveType", Hibernate.STRING)
					.addScalar("fromDate", Hibernate.DATE)
					.addScalar("toDate", Hibernate.DATE)
					.addScalar("noOfDays", Hibernate.STRING)
					.addScalar("reason", Hibernate.STRING)
					.addScalar("applyDate", Hibernate.DATE)
					.addScalar("contactNo", Hibernate.STRING).addScalar("reasonCancellation", Hibernate.STRING)
					.setResultTransformer(
							Transformers
									.aliasToBean(ErpAvailableLeaveSaveDTO.class))
					.uniqueResult();
			workflowMap.setErpAvailableLeaveSaveDTO(erpAvailableLeaveSaveDTO);

		} catch (Exception e) {
			throw e;
		} finally {

			session.flush();

		}
		return workflowMap;

	}

	@SuppressWarnings("unchecked")
	public WorkFlowMappingBean getCancelRequestID(
			WorkFlowMappingBean workflowMap) throws Exception {

		Session session = null;
		String sql = null;
		ErpAvailableLeaveSaveDTO erpAvailableLeaveSaveDTO = null;

		try {

			session = hibernateUtils.getSession();// session =
													// sessionFactory.openSession();
			String requestId = workflowMap.getRequestId();
			sql = "select leave.REQUEST_ID AS requestId FROM ERP_LEAVE_REQUEST_DETAILS leave WHERE leave.CANCEL_REQUESTID="
					+ requestId + "";
			erpAvailableLeaveSaveDTO = (ErpAvailableLeaveSaveDTO) session
					.createSQLQuery(sql)
					.addScalar("requestId", Hibernate.STRING)
					.setResultTransformer(
							Transformers
									.aliasToBean(ErpAvailableLeaveSaveDTO.class))
					.uniqueResult();
			//workflowMap.setErpAvailableLeaveSaveDTO(erpAvailableLeaveSaveDTO);
			workflowMap.setCancelRequestId(erpAvailableLeaveSaveDTO.getRequestId());

		} catch (Exception e) {
			throw e;
		} finally {

			session.flush();

		}
		return workflowMap;

	}
	
	
	// added 13/06/2016
	public String approvedRequestForErpLeaveCancel(LeaveWaterRequestProcessBean processBean)
			throws Exception {
		RequestBean rb = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			rb = new RequestBean();
			if (CPSUtils.compareStrings(processBean.getRequestTypeID(), "11")) {
				processBean
						.setHistoryID(processBean.getHistoryID().split(",")[0]);
			} else {
				processBean
						.setHistoryID(processBean.getHistoryID().split(",")[0]);
			}
			BeanUtils.copyProperties(rb, processBean);

			rb = txRequestProcess.approvedRequest(rb);

			if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())) {
				log.debug("::request workflow completed, so update in the main table");
				
				
				//request workflow completed, so update in the main table
				
				session =null;
				session=hibernateUtils.getSession();
				
				session.createQuery("update ErpAvailableLeaveSaveDTO set status='3' where  cancelRequestId=?  ")
				.setString(0, processBean.getRequestId())
				.executeUpdate();
				
				session = null;
				String sql = null;
				ErpAvailableLeaveSaveDTO erpAvailableLeaveSaveDTO = null;

				session = hibernateUtils.getSession();
				String requestId = processBean.getRequestId();
				
				sql = "select leave.REQUEST_ID AS requestId,leave.LEAVE_TYPE_ID AS leaveType ,leave.NO_OF_DAYS AS noOfDays,leave.SFID AS sfID  FROM ERP_LEAVE_REQUEST_DETAILS leave WHERE leave.CANCEL_REQUESTID="
						+ requestId + "";
			
			//	select leave.REQUEST_ID AS requestId,leave.FROM_DATE AS fromDate,leave.TO_DATE AS toDate,leave.REASON AS reason,leave.REQUESTED_DATE 
				
				erpAvailableLeaveSaveDTO = (ErpAvailableLeaveSaveDTO) session
						.createSQLQuery(sql)
						.addScalar("requestId", Hibernate.STRING)
						.addScalar("leaveType", Hibernate.STRING)
						.addScalar("noOfDays", Hibernate.STRING)
						.addScalar("sfID", Hibernate.STRING)
						.setResultTransformer(
								Transformers
										.aliasToBean(ErpAvailableLeaveSaveDTO.class))
						.uniqueResult();
				// workflowMap.setErpAvailableLeaveSaveDTO(erpAvailableLeaveSaveDTO);
			
				
			
				session = null;
				session = hibernateUtils.getSession();
				
				if (CPSUtils.compareStrings(CPSConstants.AL,erpAvailableLeaveSaveDTO.getLeaveType())
						|| CPSUtils.compareStrings(CPSConstants.CLAL,erpAvailableLeaveSaveDTO.getLeaveType())) {
					
				/*	session.createQuery("update ErpAvailableLeavesDTO set annualLeaves=annualLeaves+? where sfID=?")
							.setString(0, erpAvailableLeaveSaveDTO.getNoOfDays())
							.setString(1, erpAvailableLeaveSaveDTO.getSfID())
							.executeUpdate();*/
					
					
					
					
					session.createQuery("update ErpEmpLeavesDTO set noOfDays=noOfDays+? where sfID=? and leaveCode='AL' ")
					.setString(0, erpAvailableLeaveSaveDTO.getNoOfDays())
					.setString(1, erpAvailableLeaveSaveDTO.getSfID())
					.executeUpdate();
					
					
				} else{
					session.createQuery(
							"update ErpEmpLeavesDTO set noOfDays=noOfDays+? where sfID=? and leaveCode='"+erpAvailableLeaveSaveDTO.getLeaveType()+"' ")
							.setString(0,
									erpAvailableLeaveSaveDTO.getNoOfDays())
							.setString(1, erpAvailableLeaveSaveDTO.getSfID())
							.executeUpdate();
					
				}
				
				
				/*else if (CPSUtils.compareStrings(CPSConstants.SLU,
						erpAvailableLeaveSaveDTO.getLeaveType())) {
					
					session.createQuery("update ErpAvailableLeavesDTO set sickLeaves=sickLeaves+? where sfID=?")
							.setString(0, erpAvailableLeaveSaveDTO.getNoOfDays())
							.setString(1, erpAvailableLeaveSaveDTO.getSfID())
							.executeUpdate();
					
					
					
				} else if (CPSUtils.compareStrings(CPSConstants.MLU,
						erpAvailableLeaveSaveDTO.getLeaveType())) {
					
					session.createQuery("update ErpAvailableLeavesDTO set maternityLeaves=maternityLeaves+? where sfID=?")
							.setString(0, erpAvailableLeaveSaveDTO.getNoOfDays())
							.setString(1, erpAvailableLeaveSaveDTO.getSfID())
							.executeUpdate();
					
					
				} else 	if (CPSUtils.compareStrings(CPSConstants.PLU,
						erpAvailableLeaveSaveDTO.getLeaveType())) {
					
					
					session.createQuery("update ErpAvailableLeavesDTO set peternityLeaves=peternityLeaves+? where sfID=?")
							.setString(0, erpAvailableLeaveSaveDTO.getNoOfDays())
							.setString(1, erpAvailableLeaveSaveDTO.getSfID())
							.executeUpdate();
					
				}

*/				
		


			}

		} catch (Exception e) {
			throw e;
		}
		return rb.getMessage();
	}
	
	
	////////----------------------------***leaveamendment Here Start***-------------------
	
	
	public LeaveWaterApplicationBean getErpLeaveDetails(LeaveWaterApplicationBean leaveWaterBean) throws Exception {
		
		Session session = null;
		String sql = null;
		ErpAvailableLeaveSaveDTO erpAvailableLeaveSaveDTO = null;

		try {

			session = hibernateUtils.getSession();// session =
													// sessionFactory.openSession();
			String requestId = leaveWaterBean.getRequestId();
			sql = "select leave.REQUEST_ID AS requestId,leave.LEAVE_TYPE_ID AS leaveType,leave.FROM_DATE AS fromDate,leave.TO_DATE AS toDate,leave.NO_OF_DAYS AS noOfDays,leave.REASON AS reason,leave.REQUESTED_DATE AS applyDate,leave.CONTACT_NUMBER AS contactNo ,leave.REASON_LEAVECANCELLATION  AS reasonCancellation,leave.LEAVE_STATUS AS leaveStatus   FROM ERP_LEAVE_REQUEST_DETAILS leave WHERE leave.REQUEST_ID="
					+ requestId + "";
			erpAvailableLeaveSaveDTO = (ErpAvailableLeaveSaveDTO) session
					.createSQLQuery(sql)
					.addScalar("requestId", Hibernate.STRING)
					.addScalar("leaveType", Hibernate.STRING)
					.addScalar("fromDate", Hibernate.DATE)
					.addScalar("toDate", Hibernate.DATE)
					.addScalar("noOfDays", Hibernate.STRING)
					.addScalar("reason", Hibernate.STRING)
					.addScalar("applyDate", Hibernate.DATE)
					.addScalar("contactNo", Hibernate.STRING)
					.addScalar("reasonCancellation", Hibernate.STRING).addScalar("leaveStatus", Hibernate.INTEGER)
					.setResultTransformer(
							Transformers
									.aliasToBean(ErpAvailableLeaveSaveDTO.class))
					.uniqueResult();
			leaveWaterBean.setErpAvailableLeaveSaveDTO(erpAvailableLeaveSaveDTO);

		} catch (Exception e) {
			throw e;
		} finally {

			session.flush();

		}
		return leaveWaterBean;

		
	}
	
	
	public JasperPrint generateReport(InputStream jasperStream, Map<String,Object> params) throws Exception {
		Session session = hibernateUtils.getSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		Connection conn = (Connection) sessionImpl.connection();
		
		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObject(jasperStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,params, conn);
		
		return jasperPrint;		
	}
	

}
