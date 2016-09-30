package com.callippus.web.loan.dao.management;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.FamilyRelationDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.leave.dto.LeaveTypeDTO;
import com.callippus.web.loan.beans.dto.FinancialYearDTO;
import com.callippus.web.loan.beans.dto.GPFClosingBalanceDTO;
import com.callippus.web.loan.beans.dto.GPFRulesDTO;
import com.callippus.web.loan.beans.dto.LoanAmountDetailsDTO;
import com.callippus.web.loan.beans.dto.LoanAmountGridDTO;
import com.callippus.web.loan.beans.dto.LoanCDADetailsDTO;
import com.callippus.web.loan.beans.dto.LoanDesigMappingDTO;
import com.callippus.web.loan.beans.dto.LoanDetailsDTO;
import com.callippus.web.loan.beans.dto.LoanFestivalMasterDTO;
import com.callippus.web.loan.beans.dto.LoanHBAInterestGridDTO;
import com.callippus.web.loan.beans.dto.LoanLeavesMappingDTO;
import com.callippus.web.loan.beans.dto.LoanPaymentDTO;
import com.callippus.web.loan.beans.dto.LoanReliefFund;
import com.callippus.web.loan.beans.dto.LoanSigningAuthorityDTO;
import com.callippus.web.loan.beans.dto.LoanTypeMasterDTO;
import com.callippus.web.loan.beans.management.LoanManagementBean;
import com.callippus.web.loan.beans.request.LoanRequestBean;

@SuppressWarnings("serial")
@Service
public class SQLLoanManagementDAO implements ILoanManagementDAO, Serializable {
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	
	@SuppressWarnings("unchecked")
	public LoanManagementBean getLoanTypeMasterDetails(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			loanBean.setLoanTypeMasterList(session.createCriteria(LoanTypeMasterDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).list());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	@SuppressWarnings("unchecked")
	public LoanManagementBean getGpfLoanTypeMasterDetails(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			loanBean.setLoanTypeMasterList(session.createCriteria(LoanTypeMasterDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(
					Expression.or(Expression.eq(CPSConstants.ID, Integer.valueOf(CPSConstants.GPFWITHDRAWALLOANID)), Expression.eq(CPSConstants.ID, Integer.valueOf(CPSConstants.GPFADVANCELOANID))))
					.list());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	@SuppressWarnings("unchecked")
	public LoanManagementBean submitLoanTypeMasterDetails(LoanManagementBean loanBean) throws Exception {
		String result = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			List<LoanTypeMasterDTO> list = null;
			// duplicate check
			list = session.createCriteria(LoanTypeMasterDTO.class).add(Expression.eq("loanName", loanBean.getLoanName())).add(Expression.eq("loanType",loanBean.getLoanType())).add(Expression.eq(CPSConstants.STATUS, 1)).list();
			if (CPSUtils.checkList(list)) {
				loanBean.setResult(CPSConstants.DUPLICATE);
			}else {
			LoanTypeMasterDTO loanTypeMasterDTO = new LoanTypeMasterDTO();
			if (!CPSUtils.isNullOrEmpty(loanBean.getNodeID())) {
				// update
				loanTypeMasterDTO.setId(Integer.valueOf(loanBean.getNodeID()));
				loanTypeMasterDTO.setLastModifiedBy(loanBean.getSfID());
				loanTypeMasterDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
			}
			loanTypeMasterDTO.setLoanName(loanBean.getLoanName());
			loanTypeMasterDTO.setLoanType(loanBean.getLoanType());
			loanTypeMasterDTO.setStatus(1);
			loanTypeMasterDTO.setCreatedBy(loanBean.getSfID());
			loanTypeMasterDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
			loanTypeMasterDTO.setLastModifiedBy(loanBean.getSfID());
			loanTypeMasterDTO.setLastModifiedTime(loanTypeMasterDTO.getCreationTime());
		
			loanBean.setResult(CPSConstants.SUCCESS);
			session.saveOrUpdate(loanTypeMasterDTO);
			}		
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanManagementBean deleteLoanTypeMasterDetails(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			LoanTypeMasterDTO loanMasterBean = (LoanTypeMasterDTO) session.get(LoanTypeMasterDTO.class, Integer.valueOf(loanBean.getNodeID()));
			loanMasterBean.setLastModifiedBy(loanBean.getSfID());
			loanMasterBean.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
			loanMasterBean.setStatus(0);
			session.saveOrUpdate(loanMasterBean);
			loanBean.setResult(CPSConstants.DELETE);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	// Loan Festival start

	@SuppressWarnings("unchecked")
	public List<LoanFestivalMasterDTO> getLoanFestivalList() throws Exception {
		List<LoanFestivalMasterDTO> loanFestivalList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			loanFestivalList = session.createCriteria(LoanFestivalMasterDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.asc("festivalName")).list();
		} catch (Exception e) {
			throw e;
		}
		return loanFestivalList;
	}

	@SuppressWarnings("unchecked")
	public String saveLoanFestivalDetails(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			LoanFestivalMasterDTO loanFestivaldto = new LoanFestivalMasterDTO();
			List<LoanFestivalMasterDTO> list = null;
			// duplicate check
			list = session.createCriteria(LoanFestivalMasterDTO.class).add(Expression.eq("festivalName", loanBean.getFestivalName()).ignoreCase())
			//.add(Expression.eq("festivalDate", loanBean.getFestivalDate())).add(Expression.eq("festivalLoanStartDate", loanBean.getFestivalLoanStartDate())).add(Expression.eq("festivalLoanEndDate", loanBean.getFestivalLoanEndDate()))
			.add(Expression.eq(CPSConstants.STATUS, 1)).list();
			if (CPSUtils.checkList(list)) {
				loanBean.setResult(CPSConstants.DUPLICATE);
			} else {
				// Update
				if (!CPSUtils.isNullOrEmpty(loanBean.getNodeID())) {
					loanFestivaldto = (LoanFestivalMasterDTO) session.get(LoanFestivalMasterDTO.class, Integer.valueOf(loanBean.getNodeID()));
//					loanFestivaldto.setFestivalDate(loanBean.getFestivalDate());
//					loanFestivaldto.setFestivalLoanStartDate(loanBean.getFestivalLoanStartDate());
//					loanFestivaldto.setFestivalLoanEndDate(loanBean.getFestivalLoanEndDate());
					loanFestivaldto.setFestivalName(loanBean.getFestivalName());
					loanFestivaldto.setLastModifiedBy(loanBean.getSfID());;
					loanFestivaldto.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
					session.saveOrUpdate(loanFestivaldto);
					loanBean.setResult(CPSConstants.SUCCESS);
				} else {
					if (!CPSUtils.isNullOrEmpty(loanBean.getFestivalName())) {
						loanFestivaldto.setFestivalName(loanBean.getFestivalName());
//						loanFestivaldto.setFestivalDate(loanBean.getFestivalDate());
//						loanFestivaldto.setFestivalLoanStartDate(loanBean.getFestivalLoanStartDate());
//						loanFestivaldto.setFestivalLoanEndDate(loanBean.getFestivalLoanEndDate());
						loanFestivaldto.setStatus(1);
						loanFestivaldto.setLastModifiedBy(loanBean.getSfID());
						loanFestivaldto.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
						loanFestivaldto.setCreatedBy(loanBean.getSfID());
						loanFestivaldto.setCreationTime(CPSUtils.getCurrentDateWithTime());
						session.saveOrUpdate(loanFestivaldto);
					}
					loanBean.setResult(CPSConstants.SUCCESS);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return loanBean.getResult();
	}

	public LoanManagementBean deleteLoanFestivalDetails(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			LoanFestivalMasterDTO loanFestivaldto = new LoanFestivalMasterDTO();
			if (!CPSUtils.isNullOrEmpty(loanBean.getNodeID())) {
				loanFestivaldto = (LoanFestivalMasterDTO) session.get(LoanFestivalMasterDTO.class, Integer.valueOf(loanBean.getNodeID()));
				loanFestivaldto.setStatus(0);
				loanFestivaldto.setLastModifiedBy(loanBean.getSfID());
				loanFestivaldto.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				session.saveOrUpdate(loanFestivaldto);
				loanBean.setResult(CPSConstants.DELETE);
			}
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	// Loan Festival End

	@SuppressWarnings("unchecked")
	public LoanManagementBean getGpfBalanceList(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			loanBean.setGpfBalanceList(session.createCriteria(GPFClosingBalanceDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).list());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public String submitGpfClosingBalanceDetails(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		StringBuffer sb = new StringBuffer();
		GPFClosingBalanceDTO gpfClosingBalancedto = null;
		try {
			session = hibernateUtils.getSession();
			Date currentDate = CPSUtils.getCurrentDateWithTime();

			if (loanBean.getStatus() == 0) {
				// delete
				gpfClosingBalancedto = (GPFClosingBalanceDTO) session.get(GPFClosingBalanceDTO.class, Integer.valueOf(loanBean.getNodeID()));
				gpfClosingBalancedto.setStatus(0);
				gpfClosingBalancedto.setLastModifiedTime(currentDate);
				gpfClosingBalancedto.setLastModifiedBy(loanBean.getSfID());
				session.saveOrUpdate(gpfClosingBalancedto);
				loanBean.setResult(CPSConstants.DELETE);
			} else {
				// duplicate checking
				sb.append("select count(*) cnt from loan_gpf_close_balance where sfid=? and status=1 ");
				if (!CPSUtils.isNullOrEmpty(loanBean.getNodeID())) {
					sb.append("and id!=" + loanBean.getNodeID());
				}
				String count = session.createSQLQuery(sb.toString()).setString(0, loanBean.getChangeSfID()).uniqueResult().toString();

				if (CPSUtils.compareStrings(count, "0")) {
					if (!CPSUtils.isNullOrEmpty(loanBean.getNodeID())) {
						// update
						gpfClosingBalancedto = new GPFClosingBalanceDTO();
						gpfClosingBalancedto.setId(Integer.valueOf(loanBean.getNodeID()));
					} else {
						// new
						gpfClosingBalancedto = new GPFClosingBalanceDTO();
					}
					gpfClosingBalancedto.setStatus(1);
					gpfClosingBalancedto.setSfID(loanBean.getChangeSfID());
					gpfClosingBalancedto.setBalance(Float.parseFloat(loanBean.getBalance()));
					gpfClosingBalancedto.setFromDate(CPSUtils.critFormattedDate(loanBean.getFromDate()));
					gpfClosingBalancedto.setToDate(CPSUtils.critFormattedDate(loanBean.getToDate()));
					gpfClosingBalancedto.setCreatedBy(loanBean.getSfID());
					gpfClosingBalancedto.setLastModifiedBy(loanBean.getSfID());
					gpfClosingBalancedto.setCreationTime(currentDate);
					gpfClosingBalancedto.setLastModifiedTime(currentDate);

					session.saveOrUpdate(gpfClosingBalancedto);
					loanBean.setResult(CPSConstants.SUCCESS);
				} else {
					// duplicate
					loanBean.setResult(CPSConstants.DUPLICATE);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return loanBean.getResult();
	}

	@SuppressWarnings("unchecked")
	public List<GPFRulesDTO> getGpfRulesList() throws Exception {
		List<GPFRulesDTO> gpfSubTypeList = null;
		Session session = null;
		try {
			hibernateUtils.closeSession();
			session = hibernateUtils.getSession();
			gpfSubTypeList = session.createCriteria(GPFRulesDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.asc("loanTypeID")).addOrder(Order.asc("purpose")).list();
		} catch (Exception e) {
			throw e;
		}
		return gpfSubTypeList;
	}

	public String submitGPFRulesDetails(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		StringBuffer sb = new StringBuffer();
		GPFRulesDTO gpfRulesDTO = null;
		try {
			session = hibernateUtils.getSession();
			Date currentDate = CPSUtils.getCurrentDateWithTime();
			if (loanBean.getStatus() == 0) {
				// delete
				gpfRulesDTO = (GPFRulesDTO) session.get(GPFRulesDTO.class, Integer.valueOf(loanBean.getNodeID()));
				gpfRulesDTO.setStatus(0);
				gpfRulesDTO.setLastModifiedBy(loanBean.getSfID());
				gpfRulesDTO.setLastModifiedTime(currentDate);
				session.saveOrUpdate(gpfRulesDTO);
				loanBean.setResult(CPSConstants.DELETE);
			} else {
				// duplicate checking
				sb.append("select count(*) cnt from loan_gpf_rules_master where loan_type_id=? and upper(purpose)=upper(?) and status=1 "); 
				if (!CPSUtils.isNullOrEmpty(loanBean.getNodeID())) {
					sb.append("and id!=" + loanBean.getNodeID());
				}
				String count = session.createSQLQuery(sb.toString()).setInteger(0, Integer.valueOf(loanBean.getGpfType())).setString(1, loanBean.getPurpose()).uniqueResult().toString();

				if (CPSUtils.compareStrings(count, "0")) {
					if (!CPSUtils.isNullOrEmpty(loanBean.getNodeID())) {
						// update
						gpfRulesDTO = new GPFRulesDTO();
						gpfRulesDTO.setId(Integer.valueOf(loanBean.getNodeID()));
					} else {
						// new
						gpfRulesDTO = new GPFRulesDTO();
						gpfRulesDTO.setCreatedBy(loanBean.getSfID());
						gpfRulesDTO.setCreationTime(currentDate);
					}
					gpfRulesDTO.setStatus(1);
					gpfRulesDTO.setLoanTypeID(Integer.valueOf(loanBean.getGpfType()));
					gpfRulesDTO.setPurpose(loanBean.getPurpose());
					gpfRulesDTO.setRule(loanBean.getRule());
					gpfRulesDTO.setLastModifiedBy(loanBean.getSfID());
					gpfRulesDTO.setLastModifiedTime(currentDate);
					session.saveOrUpdate(gpfRulesDTO);
					gpfRulesDTO.setLoanTypeDetails((LoanTypeMasterDTO) session.createCriteria(LoanTypeMasterDTO.class).add(Expression.eq("id", gpfRulesDTO.getLoanTypeID())).add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult());
					loanBean.setResult(CPSConstants.SUCCESS);
				} else {
					// duplicate
					loanBean.setResult(CPSConstants.DUPLICATE);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return loanBean.getResult();
	}

	@SuppressWarnings("unchecked")
	public LoanManagementBean getLoanTypeDetails(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			session.flush();
			loanBean.setLoanTypeDetailsList(session.createCriteria(LoanDetailsDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).list());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	@SuppressWarnings("unchecked")
	public LoanManagementBean getLeaveTypes(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			loanBean.setLeaveTypesList(session.createCriteria(LeaveTypeDTO.class).list());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanManagementBean submitLoanTypeDetails(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			LoanDetailsDTO loanDetailsDTO = new LoanDetailsDTO();
			if (!CPSUtils.isNullOrEmpty(loanBean.getNodeID())) {
				loanDetailsDTO.setId(Integer.valueOf(loanBean.getNodeID()));
			}
			loanDetailsDTO.setLoanTypeID(Integer.valueOf(loanBean.getLoanType()));
			loanDetailsDTO.setLoanSubTypeID(Integer.valueOf(loanBean.getLoanSubType()));
			loanDetailsDTO.setMinInstallments(loanBean.getMinInstallments());
			loanDetailsDTO.setMaxInstallments(loanBean.getMaxInstallments());
			loanDetailsDTO.setMinInterestInstallments(loanBean.getMinInterestInstallments());
			loanDetailsDTO.setMaxInterestInstallments(loanBean.getMaxInterestInstallments());
			loanDetailsDTO.setExperience(loanBean.getExperience());
			loanDetailsDTO.setPeriodTypeFlag(loanBean.getPeriodTypeFlag());
			if (!CPSUtils.isNullOrEmpty(loanBean.getFromDate())) {
				loanDetailsDTO.setFromDate(CPSUtils.critFormattedDate(loanBean.getFromDate()));
			}
			if (!CPSUtils.isNullOrEmpty(loanBean.getToDate())) {
				loanDetailsDTO.setToDate(CPSUtils.critFormattedDate(loanBean.getToDate()));
			}
			loanDetailsDTO.setRecoveryFlag(loanBean.getRecoveryFlag());
			loanDetailsDTO.setRecoveryStart(loanBean.getNoOfMonths());
			loanDetailsDTO.setImpactOnLeaveFlag(loanBean.getImpactOnLeaveFlag());
			loanDetailsDTO.setInterestRate(loanBean.getInterestRate());
			loanDetailsDTO.setMaxRecoveryPeriod(loanBean.getMaxRecoveryPeriod());
			loanDetailsDTO.setStatus(1);
			loanDetailsDTO.setCreatedBy(loanBean.getSfID());
			loanDetailsDTO.setLastModifiedBy(loanBean.getSfID());
			loanDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
			loanDetailsDTO.setLastModifiedTime(loanDetailsDTO.getCreationTime());

			session.saveOrUpdate(loanDetailsDTO);
			loanDetailsDTO.setLoanTypeDetails((LoanTypeMasterDTO) session.createCriteria(LoanTypeMasterDTO.class).add(Expression.eq("id",loanDetailsDTO.getLoanTypeID())).add(Expression.eq(CPSConstants.STATUS,1)).uniqueResult());

			if (CPSUtils.isNullOrEmpty(loanBean.getNodeID())) {
				Criteria ctr = session.createCriteria(LoanDetailsDTO.class).add(Expression.eq("loanTypeID", Integer.valueOf(loanBean.getLoanType()))).add(Expression.eq(CPSConstants.STATUS, 1)).add(
						Expression.eq("loanSubTypeID", Integer.valueOf(loanBean.getLoanSubType())));
				loanDetailsDTO = (LoanDetailsDTO) ctr.uniqueResult();
			}
			// Get the max of id
			loanBean.setLoanDetailsID(loanDetailsDTO.getId());

			loanBean.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	@SuppressWarnings("unchecked")
	public LoanManagementBean getLoanLeavesMapping(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			loanBean.setLoanLeavesDetailsList(session.createCriteria(LoanLeavesMappingDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).list());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public String submitLoanLeavesMappings(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		String message = null;
		LoanLeavesMappingDTO loanLeavesMappingDTO = null;
		try {
			session = hibernateUtils.getSession();

			session.createQuery("update LoanLeavesMappingDTO set status=0 where loanDetailsID=? ").setInteger(0, loanBean.getLoanDetailsID()).executeUpdate();
			session.flush();
//GATTU			hibernateUtils.commitTransaction();
//GATTU			hibernateUtils.beginTransaction(session);
			String[] selectedLeaves = loanBean.getLeave().split(",");

			for (String leaveID : selectedLeaves) {
				if (!CPSUtils.isNullOrEmpty(leaveID)) {;
					loanLeavesMappingDTO = (LoanLeavesMappingDTO) session.createCriteria(LoanLeavesMappingDTO.class).add(Expression.eq("loanDetailsID", loanBean.getLoanDetailsID())).add(
							Expression.eq(CPSConstants.LEAVETYPEID, Integer.valueOf(leaveID))).uniqueResult();
					if (CPSUtils.isNull(loanLeavesMappingDTO)) {
						loanLeavesMappingDTO = new LoanLeavesMappingDTO();
					}
					loanLeavesMappingDTO.setLoanDetailsID(loanBean.getLoanDetailsID());
					loanLeavesMappingDTO.setLeaveTypeID(Integer.valueOf(leaveID));
					loanLeavesMappingDTO.setStatus(1);

					session.saveOrUpdate(loanLeavesMappingDTO);
				}
			}

			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public LoanManagementBean deleteLoanTypeDetails(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			LoanDetailsDTO loanDetailsBean = (LoanDetailsDTO) session.get(LoanDetailsDTO.class, Integer.valueOf(loanBean.getNodeID()));
			loanDetailsBean.setLastModifiedBy(loanBean.getSfID());
			loanDetailsBean.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
			loanDetailsBean.setStatus(0);
			session.saveOrUpdate(loanDetailsBean);
			loanBean.setResult(CPSConstants.DELETE);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public String submitLoanDesigMappings(LoanDesigMappingDTO loanDesigMappingDTO) throws Exception {
		Session session = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();
			LoanDesigMappingDTO loanDesigMappingDTO1 = (LoanDesigMappingDTO) session.createCriteria(LoanDesigMappingDTO.class)
					.add(Expression.eq("loanAmountID", loanDesigMappingDTO.getLoanAmountID())).add(Expression.eq("designationID", loanDesigMappingDTO.getDesignationID())).add(
							Expression.eq(CPSConstants.STATUS, 0)).uniqueResult();
			if (CPSUtils.isNull(loanDesigMappingDTO1)) {
				session.saveOrUpdate(loanDesigMappingDTO);
			} else {
				loanDesigMappingDTO1.setStatus(1);
				session.saveOrUpdate(loanDesigMappingDTO1);
			}
			session.flush();
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return message;
	}


	public void resetloanDesigMapping(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			session.createQuery("update LoanDesigMappingDTO set status=0 where loanAmountID=? ").setInteger(0, Integer.valueOf(loanBean.getAmountID())).executeUpdate();
			hibernateUtils.closeSession();
			hibernateUtils.getSession();
		} catch (Exception e) {
			throw e;
		}
	}

	public LoanManagementBean submitLoanAmountDetails(LoanManagementBean loanBean) throws Exception {
		Session session = null;
//		String localSubtype = null;
		try {
			session = hibernateUtils.getSession();

//			if (CPSUtils.compareStrings(loanBean.getLoanSubType(), CPSConstants.NULL)) {
//				localSubtype = CPSConstants.ZERO;
//			} else {
//				localSubtype = loanBean.getLoanSubType();
//			}

			// duplicate check
//			Criteria ctr = session.createCriteria(LoanAmountDetailsDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("loanTypeID", Integer.valueOf(loanBean.getLoanType()))).add(
//					Expression.eq("loanSubTypeID", Integer.valueOf(localSubtype))).add(Expression.eq("gazType",loanBean.getGazType()));
			Criteria ctr = session.createCriteria(LoanAmountDetailsDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("loanTypeID", Integer.valueOf(loanBean.getLoanType()))).add(Expression.eq("gazType",loanBean.getGazType()));
			if (!CPSUtils.isNullOrEmpty(loanBean.getNodeID())) {
				ctr.add(Expression.ne("id", Integer.valueOf(loanBean.getNodeID())));
			}
			LoanAmountDetailsDTO loanAmountDTO = (LoanAmountDetailsDTO) ctr.uniqueResult();
			if (CPSUtils.isNull(loanAmountDTO)) {
				loanAmountDTO = new LoanAmountDetailsDTO();
				if (!CPSUtils.isNullOrEmpty(loanBean.getNodeID())) {
					loanAmountDTO.setId(Integer.valueOf(loanBean.getNodeID()));
				}

				loanAmountDTO.setLoanTypeID(Integer.valueOf(loanBean.getLoanType()));
//				loanAmountDTO.setLoanSubTypeID(Integer.valueOf(localSubtype));
				loanAmountDTO.setGazType(loanBean.getGazType());
				loanAmountDTO.setPayFlag(loanBean.getImpactOnPayFlag());
				loanAmountDTO.setDaFlag(loanBean.getImpactOnDAFlag());
				loanAmountDTO.setBalanceFlag(loanBean.getImpactOnBalanceFlag());
				loanAmountDTO.setMonthsFlag(loanBean.getImpactOnNoOfMonthsPayFlag());
				loanAmountDTO.setDaPercentage(loanBean.getDaPercentage());
				loanAmountDTO.setMultipleFlag(loanBean.getImpactOnTimesFlag());
				loanAmountDTO.setStatus(1);
				loanAmountDTO.setCreatedBy(loanBean.getSfID());
				loanAmountDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
				loanAmountDTO.setLastModifiedBy(loanBean.getSfID());
				loanAmountDTO.setLastModifiedTime(loanAmountDTO.getCreationTime());

				session.saveOrUpdate(loanAmountDTO);
				session.flush();
				loanAmountDTO.setLoanTypeDetails((LoanTypeMasterDTO) session.createCriteria(LoanTypeMasterDTO.class).add(Expression.eq("id",loanAmountDTO.getLoanTypeID())).add(Expression.eq(CPSConstants.STATUS,1)).uniqueResult());
//GATTU				hibernateUtils.commitTransaction();
//GATTU				hibernateUtils.beginTransaction(session);
				
				if (CPSUtils.isNullOrEmpty(loanBean.getNodeID())) {
					// Get the max of id
					loanBean.setAmountID(Integer.valueOf(session.createCriteria(LoanAmountDetailsDTO.class).setProjection(Projections.projectionList().add(Projections.max("id"))).uniqueResult()
							.toString()));
				} else {
					loanBean.setAmountID(Integer.valueOf(loanBean.getNodeID()));
				}
				loanBean.setResult(CPSConstants.SUCCESS);
			} else {
				loanBean.setResult(CPSConstants.DUPLICATE);
			}
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanManagementBean duplicateLoanTypeDetailsCheck(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			Criteria ctr = session.createCriteria(LoanDetailsDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("loanTypeID", Integer.valueOf(loanBean.getLoanType()))).add(
					Expression.eq("loanSubTypeID", Integer.valueOf(loanBean.getLoanSubType())));
			if (!CPSUtils.isNullOrEmpty(loanBean.getNodeID())) {
				ctr.add(Expression.ne("id", Integer.valueOf(loanBean.getNodeID())));
			}
			if (CPSUtils.checkList(ctr.list())) {
				loanBean.setResult(CPSConstants.DUPLICATE);
			} else {
				loanBean.setResult(CPSConstants.SUCCESS);
			}
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	// Loan Immediate Relief
	public String saveLoanReliefDetails(LoanReliefFund reliefFund) throws Exception {
		Session session = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();
			session.saveOrUpdate(reliefFund);
			reliefFund.setRelationDetails((FamilyRelationDTO) session.createCriteria(FamilyRelationDTO.class).add(Expression.eq("id", reliefFund.getRelationshipID())).add(Expression.eq(CPSConstants.STATUS,1)).uniqueResult());
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String checkDuplicateSfid(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			Criteria ctr = session.createCriteria(LoanReliefFund.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("sfID", loanBean.getRefSfID()));
			if (!CPSUtils.isNullOrEmpty(loanBean.getNodeID())) {
				ctr.add(Expression.ne(CPSConstants.ID, Integer.valueOf(loanBean.getNodeID())));
			}
			LoanReliefFund loanReliefFund = (LoanReliefFund) ctr.uniqueResult();
			if (CPSUtils.isNull(loanReliefFund)) {
				loanBean.setResult(CPSConstants.SUCCESS);
			} else {
				loanBean.setResult(CPSConstants.DUPLICATE);
			}
		} catch (Exception e) {
			throw e;
		}
		return loanBean.getResult();
	}

	@SuppressWarnings("unchecked")
	public List<LoanReliefFund> getReliefList() throws Exception {
		Session session = null;
		List<LoanReliefFund> list = null;
		try {
			session = hibernateUtils.getSession();
			list = session.createCriteria(LoanReliefFund.class).add(Expression.eq(CPSConstants.STATUS, 1)).list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public String deleteReliefDetails(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			LoanReliefFund reliefFund = (LoanReliefFund) session.get(LoanReliefFund.class, Integer.valueOf(loanBean.getNodeID()));
			reliefFund.setStatus(0);
			reliefFund.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
			reliefFund.setLastModifiedBy(loanBean.getSfID());
			session.saveOrUpdate(reliefFund);
			loanBean.setResult(CPSConstants.DELETE);
		} catch (Exception e) {
			throw e;
		}
		return loanBean.getResult();
	}

	public void resetAmountGrid(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			session.createQuery("update LoanAmountGridDTO set status=0 where loanAmountID=? ").setInteger(0, Integer.valueOf(loanBean.getAmountID())).executeUpdate();
			hibernateUtils.closeSession();
			hibernateUtils.getSession();
		} catch (Exception e) {
			throw e;
		}
	}

	public String submitAmountGrid(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			JSONObject amountGridJSON = new JSONObject(loanBean.getAmountGrid());
			Date currentDate = CPSUtils.getCurrentDateWithTime();
			for (int i = 0; i < amountGridJSON.length(); i++) {
				JSONObject stepDetails = (JSONObject) amountGridJSON.get(String.valueOf(i));
				String subSeqRelation = stepDetails.getString("subSeqRelation");
				String subSeqMonths = stepDetails.getString("subSeqMonths");
				JSONObject payGrid = new JSONObject(stepDetails.getString("payGrid"));
				for (int j = 0; j < payGrid.length(); j++) {
					JSONObject payDetails = (JSONObject) payGrid.get(String.valueOf(j));
					LoanAmountGridDTO loanGrid = new LoanAmountGridDTO();
					loanGrid.setLoanAmountID(loanBean.getAmountID());
					loanGrid.setStageID(i + 1);
					loanGrid.setPayBalanceRelation("1");
					loanGrid.setAmount(Float.valueOf(payDetails.getString("amount")));
					loanGrid.setRelationShip(payDetails.getString("relation"));
					loanGrid.setPayOrGpf(payDetails.getString("pay"));
					if (!CPSUtils.isNullOrEmpty(payDetails.getString("da"))) {
						loanGrid.setDa(Integer.valueOf(payDetails.getString("da")));
					}
					loanGrid.setSubseqRelation(subSeqRelation);
					if (!CPSUtils.isNullOrEmpty(subSeqMonths)) {
						loanGrid.setSubseqMonths(Integer.valueOf(subSeqMonths));
					}
					loanGrid.setStatus(1);
					loanGrid.setCreatedBy(loanBean.getSfID());
					loanGrid.setCreationTime(currentDate);
					loanGrid.setLastModifiedBy(loanBean.getSfID());
					loanGrid.setLastModifiedTime(currentDate);
					session.saveOrUpdate(loanGrid);
				}
				JSONObject monthlyPay = new JSONObject(stepDetails.getString("monthlyPay"));
				if (monthlyPay.length() > 0) {
					LoanAmountGridDTO loanGrid = new LoanAmountGridDTO();
					loanGrid.setLoanAmountID(loanBean.getAmountID());
					loanGrid.setStageID(i + 1);
					loanGrid.setPayBalanceRelation("3");
					loanGrid.setAmount(Float.valueOf(monthlyPay.getString("amount")));
					loanGrid.setRelationShip(monthlyPay.getString("relation"));
					loanGrid.setPayOrGpf(monthlyPay.getString("pay"));
					if (!CPSUtils.isNullOrEmpty(monthlyPay.getString("da"))) {
						loanGrid.setDa(Integer.valueOf(monthlyPay.getString("da")));
					}
					loanGrid.setSubseqRelation(subSeqRelation);
					if (!CPSUtils.isNullOrEmpty(subSeqMonths)) {
						loanGrid.setSubseqMonths(Integer.valueOf(subSeqMonths));
					}
					loanGrid.setStatus(1);
					loanGrid.setCreatedBy(loanBean.getSfID());
					loanGrid.setCreationTime(currentDate);
					loanGrid.setLastModifiedBy(loanBean.getSfID());
					loanGrid.setLastModifiedTime(currentDate);
					session.saveOrUpdate(loanGrid);
				}

				JSONObject gpfBalance = new JSONObject(stepDetails.getString("gpfBalance"));
				if (gpfBalance.length() > 0) {
					LoanAmountGridDTO gpfloanGrid = new LoanAmountGridDTO();
					gpfloanGrid.setLoanAmountID(loanBean.getAmountID());
					gpfloanGrid.setStageID(i + 1);
					gpfloanGrid.setPayBalanceRelation("2");
					gpfloanGrid.setAmount(Float.valueOf(gpfBalance.getString("amount")));
					gpfloanGrid.setRelationShip(gpfBalance.getString("relation"));
					gpfloanGrid.setPayOrGpf(gpfBalance.getString("pay"));
					if (!CPSUtils.isNullOrEmpty(gpfBalance.getString("da"))) {
						gpfloanGrid.setDa(Integer.valueOf(gpfBalance.getString("da")));
					}
					gpfloanGrid.setSubseqRelation(subSeqRelation);
					if (!CPSUtils.isNullOrEmpty(subSeqMonths)) {
						gpfloanGrid.setSubseqMonths(Integer.valueOf(subSeqMonths));
					}
					gpfloanGrid.setStatus(1);
					gpfloanGrid.setCreatedBy(loanBean.getSfID());
					gpfloanGrid.setCreationTime(currentDate);
					gpfloanGrid.setLastModifiedBy(loanBean.getSfID());
					gpfloanGrid.setLastModifiedTime(currentDate);
					session.saveOrUpdate(gpfloanGrid);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return loanBean.getResult();
	}

	@SuppressWarnings("unchecked")
	public LoanManagementBean getLoanAmountList(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			loanBean.setLoanAmountDetailsList(session.createCriteria(LoanAmountDetailsDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).list());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	@SuppressWarnings("unchecked")
	public LoanManagementBean getLoanDesignationMappings(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			loanBean.setLoanDesigMappingList(session.createCriteria(LoanDesigMappingDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).list());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	@SuppressWarnings("unchecked")
	public LoanManagementBean getLoanAmountGridDetails(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			loanBean.setLoanAmountGrid(session.createCriteria(LoanAmountGridDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).list());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public String deleteLoanAmountDetails(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			LoanAmountDetailsDTO loanAmountBean = (LoanAmountDetailsDTO) session.get(LoanAmountDetailsDTO.class, Integer.valueOf(loanBean.getNodeID()));
			loanAmountBean.setLastModifiedBy(loanBean.getSfID());
			loanAmountBean.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
			loanAmountBean.setStatus(0);
			session.saveOrUpdate(loanAmountBean);

			session.createQuery("update LoanDesigMappingDTO set status=0 where loanAmountID=? ").setInteger(0, Integer.valueOf(loanBean.getNodeID())).executeUpdate();

			session.createQuery("update LoanAmountGridDTO set status=0, lastModifiedBy=? ,lastModifiedTime=? where loanAmountID=? ").setString(0, loanBean.getSfID()).setString(1,
					CPSUtils.getCurrentDate()).setInteger(2, Integer.valueOf(loanBean.getNodeID())).executeUpdate();

			loanBean.setResult(CPSConstants.DELETE);

		} catch (Exception e) {
			throw e;
		}
		return loanBean.getResult();
	}

	// Sending Report Start
	@SuppressWarnings("unchecked")
	public LoanManagementBean getFinancialYearList(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			loanBean.setFinancialYearList(session.createSQLQuery("select fym.id,to_char(from_date,'YYYY') ||'-'|| to_char(to_date,'YYYY') financialYear from financial_year_master fym where status=1 order by financialYear")
					.addScalar("id", Hibernate.INTEGER).addScalar("financialYear", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(FinancialYearDTO.class)).list());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	@SuppressWarnings("unchecked")
	public LoanManagementBean getAppliedLoansList(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			StringBuilder query = new StringBuilder();

			query
					.append("select lrd.request_id requestID,lrd.requested_date ,emp.sfid sfID,emp.name_in_service_book name,lpd.requested_amount reqAmount,ltm.loan_name loanName from loan_request_details lrd,emp_master emp,loan_payment_details lpd,loan_type_master ltm "
							+ "where lrd.status=58 and lpd.status=58 and lpd.request_id=lrd.request_id and lrd.sfid=emp.sfid and ltm.id=lpd.loan_type_id ");
			if (!CPSUtils.isNullOrEmpty(loanBean.getFinancialYear())) {
				query.append("and lrd.requested_date between (select from_date from financial_year_master where id=" + loanBean.getFinancialYear()
						+ ") and (select to_date from financial_year_master where id=" + loanBean.getFinancialYear() + ") ");
			}
			if (!CPSUtils.isNullOrEmpty(loanBean.getLoanType())) {
				query.append(" and lpd.loan_type_id=" + loanBean.getLoanType());
			}
			query
					.append(" union select lhrd.request_id requestID,lhrd.requested_date,emp.sfid sfID,emp.name_in_service_book name,lpd.requested_amount reqAmount,ltm.loan_name loanName from loan_hba_request_details lhrd,emp_master emp,loan_payment_details lpd,loan_type_master ltm "
							+ "where lhrd.status=58 and lpd.status=58 and lpd.request_id=lhrd.request_id and lhrd.sfid=emp.sfid and ltm.id=lpd.loan_type_id ");
			if (!CPSUtils.isNullOrEmpty(loanBean.getFinancialYear())) {
				query.append("and lhrd.requested_date between (select from_date from financial_year_master where id=" + loanBean.getFinancialYear()
						+ ") and (select to_date from financial_year_master where id=" + loanBean.getFinancialYear() + ")");
			}
			if (!CPSUtils.isNullOrEmpty(loanBean.getLoanType())) {
				query.append(" and lpd.loan_type_id=" + loanBean.getLoanType());
			}
			query.append(" order by requested_date ");
			loanBean.setAppliedLoansList(session.createSQLQuery(query.toString()).addScalar("requestID", Hibernate.STRING).addScalar("sfID").addScalar("name").addScalar("loanName", Hibernate.STRING)
					.addScalar("reqAmount", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(LoanRequestBean.class)).list());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanManagementBean submitSendingReportDetails(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			String[] selectedRequests = loanBean.getRequests().split(",");
			for (String requestID : selectedRequests) {
				LoanCDADetailsDTO loanCDADetails = new LoanCDADetailsDTO();
				loanCDADetails.setRequestID(Integer.valueOf(requestID));
				loanCDADetails.setSendingReportNumber(loanBean.getReportNumber());
				loanCDADetails.setSendingReportDate(loanBean.getReportDate());
				loanCDADetails.setStatus(Integer.valueOf(CPSConstants.STATUSHEADQUARTER));
				loanCDADetails.setCreatedBy(loanBean.getSfID());
				loanCDADetails.setCreationTime(CPSUtils.getCurrentDateWithTime());
				loanCDADetails.setLastModifiedBy(loanBean.getSfID());
				loanCDADetails.setLastModifiedTime(loanCDADetails.getCreationTime());
				session.saveOrUpdate(loanCDADetails);
				session.createSQLQuery("update loan_request_details set status=" + CPSConstants.STATUSPROCESSING + " where request_id=" + requestID + "").executeUpdate();
				session.createSQLQuery("update loan_hba_request_details set status=" + CPSConstants.STATUSPROCESSING + " where request_id=" + requestID + "").executeUpdate();
			}

			loanBean.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;

	}

	@SuppressWarnings("unchecked")
	public LoanManagementBean getReportNumberList(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			StringBuilder query = new StringBuilder();
			query.append("select distinct sending_report_no sendingReportNumber from loan_cda_details where status="+CPSConstants.STATUSHEADQUARTER);
			loanBean.setReportNumberList(session.createSQLQuery(query.toString()).addScalar("sendingReportNumber").setResultTransformer(Transformers.aliasToBean(LoanCDADetailsDTO.class)).list());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	@SuppressWarnings("unchecked")
	public LoanManagementBean getSearchCDAList(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			StringBuilder query = new StringBuilder();
			if (!CPSUtils.isNullOrEmpty(loanBean.getReportNumber())) {
				query
						.append("select lpd.id id,case when get_history_id(lpd.request_id) is null then 0 else get_history_id(lpd.request_id) end historyID,lpd.request_id requestID,lpd.REQUESTED_INSTALMENTS requestedInstalments, (case when lpd.interest_rate is null then 0 else  lpd.interest_rate end) interestRate,lpd.REQUESTED_INTEREST_INSTALMENTS requestedInterestInstalments,(select id from loan_cda_details lcd where lcd.request_id=lpd.request_id and lcd.sending_report_no='"
								+ loanBean.getReportNumber()
								+ "'and status="+ CPSConstants.STATUSHEADQUARTER +") cdaId,lpd.sfid sfID,(select emp.name_in_service_book from emp_master emp where emp.sfid=lpd.sfid) name,lpd.requested_amount reqAmount from loan_payment_details lpd where request_id in (select request_Id from loan_cda_details where sending_report_no='"
								+ loanBean.getReportNumber() + "' and status=" + CPSConstants.STATUSHEADQUARTER+ ")");
				if(!CPSUtils.isNullOrEmpty(loanBean.getLoanType())){
					query.append(" and lpd.loan_type_id="+loanBean.getLoanType()+"");
				}
				loanBean.setSearchCDAList(session.createSQLQuery(query.toString()).addScalar("id", Hibernate.INTEGER).addScalar("historyID", Hibernate.STRING).addScalar("requestID", Hibernate.STRING).addScalar("requestedInstalments",Hibernate.INTEGER).addScalar("requestedInterestInstalments",Hibernate.INTEGER).addScalar("interestRate", Hibernate.FLOAT).addScalar("cdaId", Hibernate.INTEGER)
						.addScalar("sfID").addScalar("name").addScalar("reqAmount", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(LoanRequestBean.class)).list());
			}
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanManagementBean submitCDAReportDetails(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
			JSONObject cdaReportGridJSON = new JSONObject(loanBean.getCdaReportGrid());
			for (int i = 0; i < cdaReportGridJSON.length(); i++) {
				JSONObject rowDetails = (JSONObject) cdaReportGridJSON.get(String.valueOf(i));

				LoanCDADetailsDTO loanCDADetails = (LoanCDADetailsDTO) session.get(LoanCDADetailsDTO.class, Integer.valueOf(rowDetails.getString("cdaID")));
				loanCDADetails.setHqReportNumber(loanBean.getHqReportNumber());
				loanCDADetails.setHqReportDate(loanBean.getHqReportDate());
				loanCDADetails.setLastModifiedBy(loanBean.getSfID());
				loanCDADetails.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				loanCDADetails.setHqReturnFlag(Integer.valueOf(CPSConstants.STATUSENABLE));
				loanCDADetails.setStatus(Integer.valueOf(CPSConstants.STATUSCOMPLETED));
				session.update(loanCDADetails);

//				session.createSQLQuery("update loan_payment_details set sanctioned_amount=?,recovery_start_date=?,sanctioned_instalments=?,emi=?,status=? where id=?").setFloat(0,
//						Float.valueOf(rowDetails.getString("sanAmount"))).setDate(1, df.parse(rowDetails.getString("recDate"))).setInteger(2, Integer.valueOf(rowDetails.getString("installment")))
//						.setFloat(3, Float.valueOf(rowDetails.getString("emi"))).setInteger(4, Integer.valueOf(CPSConstants.STATUSCOMPLETED))
//						.setInteger(5, Integer.valueOf(rowDetails.getString("id"))).executeUpdate();

				LoanPaymentDTO loanPaymentDTO = (LoanPaymentDTO) session.load(LoanPaymentDTO.class, Integer.valueOf(rowDetails.getString("id")));
				 loanPaymentDTO.setSanctionedAmount(Float.valueOf(rowDetails.getString("sanAmount")));
				 loanPaymentDTO.setSanctionedDate(CPSUtils.getCurrentDateWithTime());
				 loanPaymentDTO.setRecStartDate(df.parse(rowDetails.getString("recDate")));
				 loanPaymentDTO.setSanctionedInstalments(Integer.valueOf(rowDetails.getString("installment")));
				 loanPaymentDTO.setEmi(Float.valueOf(rowDetails.getString("emi")));
				 loanPaymentDTO.setLastEmi(Float.valueOf(rowDetails.getString("lastEmi")));
				 loanPaymentDTO.setInterestAmount(Float.valueOf(rowDetails.getString("interestAmount")));
				 loanPaymentDTO.setSanctionedIntInstalments(Integer.valueOf(rowDetails.getString("intInstallments")));
				 loanPaymentDTO.setInterestEmi(Float.valueOf(rowDetails.getString("interestEmi")));
				 loanPaymentDTO.setInterestLastEmi(Float.valueOf(rowDetails.getString("interestLastEmi")));
				 loanPaymentDTO.setStatus(Integer.valueOf(CPSConstants.STATUSCOMPLETED));
				 session.update(loanPaymentDTO);

				// LoanHBARequestProcessBean hbaRequestProcessBean = (LoanHBARequestProcessBean) session.get(LoanHBARequestProcessBean.class, rowDetails.getString("reqId"));
				// hbaRequestProcessBean.setStatus(Integer.valueOf(CPSConstants.STATUSCOMPLETED));
				// session.update(hbaRequestProcessBean);
				session.createSQLQuery("update loan_request_details set status=" + CPSConstants.STATUSCOMPLETED + " where request_id=" + rowDetails.getString("reqId") + "").executeUpdate();
				session.createSQLQuery("update loan_hba_request_details set status=" + CPSConstants.STATUSCOMPLETED + " where request_id=" + rowDetails.getString("reqId") + "").executeUpdate();
				// LoanRequestProcessBean requestProcessBean = (LoanRequestProcessBean) session.get(LoanRequestProcessBean.class, rowDetails.getString("reqId"));
				// requestProcessBean.setStatus(Integer.valueOf(CPSConstants.STATUSCOMPLETED));
				// session.update(requestProcessBean);

				session
						.createSQLQuery(
								"update request_workflow_history set status="
										+ CPSConstants.STATUSCOMPLETED
										+ ",ip_address=?, stage_status=(select w.stage_desc_id from request_workflow_history rwh,workflow w where rwh.request_id=? "
										+ "and w.workflow_id=rwh.workflow_id and w.stage_id=rwh.request_stage and rwh.request_stage=(select max(request_stage) from request_workflow_history where request_id=?)) where request_id=? and "
										+ "request_stage=(select max(request_stage) from request_workflow_history where request_id=?)").setString(0 , loanBean.getIpAddress()).setString(1, rowDetails.getString("reqId")).setString(2,
								rowDetails.getString("reqId")).setString(3, rowDetails.getString("reqId")).setString(4, rowDetails.getString("reqId")).executeUpdate();
			}
			loanBean.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			loanBean.setResult(CPSConstants.FAILED);
			throw e;
		}
		return loanBean;
	}

	public LoanManagementBean submitReGenerateDetails(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			JSONObject cdaReportGridJSON = new JSONObject(loanBean.getCdaReportGrid());
			for (int i = 0; i < cdaReportGridJSON.length(); i++) {
				JSONObject rowDetails = (JSONObject) cdaReportGridJSON.get(String.valueOf(i));
				LoanCDADetailsDTO loanCDADetails = (LoanCDADetailsDTO) session.get(LoanCDADetailsDTO.class, Integer.valueOf(rowDetails.getString("cdaID")));
				loanCDADetails.setHqReturnFlag(0);
				loanCDADetails.setStatus(0);
				session.saveOrUpdate(loanCDADetails);
				
				 LoanPaymentDTO loanPaymentDTO = (LoanPaymentDTO) session.load(LoanPaymentDTO.class, Integer.valueOf(rowDetails.getString("id")));
				 loanPaymentDTO.setStatus(Integer.valueOf(CPSConstants.STATUSHEADQUARTER));
				 session.saveOrUpdate(loanPaymentDTO);
				 
				 session.createSQLQuery("update loan_request_details set status=" + CPSConstants.STATUSHEADQUARTER + " where request_id=" + rowDetails.getString("reqId") + "").executeUpdate();
				 session.createSQLQuery("update loan_hba_request_details set status=" + CPSConstants.STATUSHEADQUARTER + " where request_id=" + rowDetails.getString("reqId") + "").executeUpdate();
				 
				 session
					.createSQLQuery(
							"update request_workflow_history set status="
									+ CPSConstants.STATUSAPPROVED
									+ ",stage_status="
									+ CPSConstants.STATUSHEADQUARTER
									+ " where request_id=? and "
									+ "request_stage=(select max(request_stage) from request_workflow_history where request_id=?)").setString(0, rowDetails.getString("reqId")).setString(1,
							rowDetails.getString("reqId")).executeUpdate();
				 
			}
			loanBean.setResult(CPSConstants.SUCCESS);
		}
		catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanManagementBean manageLoanHBAInterestGrid(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
//			if(!CPSUtils.isNullOrEmpty(loanBean.getNodeID()))
//			{
//				deleteLoanHBAInterestGrid(loanBean);
//			}
		JSONObject hbaInterestGridJSON = new JSONObject(loanBean.getHbaInterestGrid());
		for (int i = 0; i < hbaInterestGridJSON.length(); i++) {
			JSONObject rowDetails = (JSONObject) hbaInterestGridJSON.get(String.valueOf(i));
			LoanHBAInterestGridDTO loanInterestGrid= new LoanHBAInterestGridDTO();
			if(!CPSUtils.isNullOrEmpty(loanBean.getNodeID()))
			{
				loanInterestGrid.setId(Integer.parseInt(loanBean.getNodeID()));
			}
			loanInterestGrid.setLowerAmountLimit(Integer.valueOf(rowDetails.getString("lowerAmountLimit")));
			loanInterestGrid.setUpperAmountLimit(Integer.valueOf(rowDetails.getString("upperAmountLimit")));
			loanInterestGrid.setInterestRate(Float.valueOf(rowDetails.getString("interestRate")));
			loanInterestGrid.setApplicableYear(loanBean.getHbaInterestYear());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String df1 = df.format(loanBean.getHbaInterestYear());
			loanInterestGrid.setApplicableYear(Timestamp.valueOf(df1+" 00:00:00"));
			loanInterestGrid.setStatus(1);
			loanInterestGrid.setCreatedBy(loanBean.getSfID());
			loanInterestGrid.setCreationTime(CPSUtils.getCurrentDateWithTime());
			loanInterestGrid.setLastModifiedBy(loanBean.getSfID());
			loanInterestGrid.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
			session.saveOrUpdate(loanInterestGrid);		
			loanBean.setResult(CPSConstants.SUCCESS);
		}
	}catch(Exception e){
		e.printStackTrace();
		throw e;
	}finally{
		hibernateUtils.closeSession();
	}
		return loanBean;
	}

	@SuppressWarnings("unchecked")

	public List<LoanHBAInterestGridDTO> getLoanHBAInterestList()throws Exception {
		List<LoanHBAInterestGridDTO> loanHBAInterestList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			loanHBAInterestList = session.createCriteria(LoanHBAInterestGridDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.desc(CPSConstants.APPLICABLEYEAR)).addOrder(Order.asc(CPSConstants.LOWERAMOUNTLIMIT)).list();
		} catch (Exception e) {
			throw e;
		}
		return loanHBAInterestList;
	}

	public LoanManagementBean deleteLoanHBAInterestGrid(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
		LoanHBAInterestGridDTO loanHBAInterestGridDTO = (LoanHBAInterestGridDTO) session.get(LoanHBAInterestGridDTO.class, Integer.valueOf(loanBean.getNodeID()));
		loanHBAInterestGridDTO.setLastModifiedBy(loanBean.getSfID());
		loanHBAInterestGridDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
		loanHBAInterestGridDTO.setStatus(0);
		session.saveOrUpdate(loanHBAInterestGridDTO);
		loanBean.setResult(CPSConstants.DELETE);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	@SuppressWarnings("unchecked")
	public List<LoanCDADetailsDTO> getConveyanceReportList() throws Exception {
		List<LoanCDADetailsDTO> loanConveyanceReportList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			loanConveyanceReportList = session.createSQLQuery("select distinct sending_report_no sendingReportNumber,sending_report_date sendingReportDate,sr_account_officer as srAccOfficer from loan_cda_details where status=?").addScalar("sendingReportNumber", Hibernate.STRING).addScalar("srAccOfficer", Hibernate.STRING).addScalar("sendingReportDate",Hibernate.DATE).setInteger(0,Integer.valueOf(CPSConstants.STATUSHEADQUARTER)).setResultTransformer(Transformers.aliasToBean(LoanCDADetailsDTO.class)).list();
		} catch (Exception e) {
			throw e;
		}
		return loanConveyanceReportList;
	}

	@SuppressWarnings("unchecked")
	public List<LoanSigningAuthorityDTO> getSigningAuthorityList()throws Exception {
		List<LoanSigningAuthorityDTO> loanSigningAuthorityList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			loanSigningAuthorityList = session.createCriteria(LoanSigningAuthorityDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).list();
		} catch (Exception e) {
			throw e;
		}
		return loanSigningAuthorityList;
	}


	public LoanManagementBean submitAdminOfficer(LoanManagementBean loanBean)
			throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
		session.createSQLQuery("update loan_cda_details set SR_ACCOUNT_OFFICER=? where sending_report_no=? and sending_report_date=? and status=58").setString(0, loanBean.getSrAccOfficer()).setString(1, loanBean.getReportNumber()).setDate(2, loanBean.getReportDate()).executeUpdate();
		loanBean.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}


	public LoanManagementBean submitCDAReport(LoanManagementBean loanBean)
			throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
		JSONObject cdaJSON=new JSONObject(loanBean.getCdaGrid());
		for(int i=0;i<cdaJSON.length();i++){
			JSONObject rowDetails=(JSONObject)cdaJSON.get(String.valueOf(i));
			LoanCDADetailsDTO loanCdaDTO= (LoanCDADetailsDTO)session.get(LoanCDADetailsDTO.class,Integer.valueOf( rowDetails.getString("id")));
			loanCdaDTO.setSanctionNo(Integer.valueOf(rowDetails.getString("sanctionNo")));
			loanCdaDTO.setBillNo(Integer.valueOf(rowDetails.getString("billNo")));
			loanCdaDTO.setAccOfficer(rowDetails.getString("accOfficer"));
			loanCdaDTO.setCfaOfficer(rowDetails.getString("cfaOfficer"));
			if(!CPSUtils.isNullOrEmpty(rowDetails.getString("dvNo"))){
			loanCdaDTO.setDvNo(Integer.valueOf(rowDetails.getString("dvNo")));
			}
			if(!CPSUtils.isNullOrEmpty(rowDetails.getString("dvDate"))){
			loanCdaDTO.setDvDate(CPSUtils.convertStringToDate(rowDetails.getString("dvDate")));
			}
			if(!CPSUtils.isNullOrEmpty(rowDetails.getString("cdaAmount"))){
			loanCdaDTO.setCdaAmount(Integer.valueOf(rowDetails.getString("cdaAmount")));
			}
			if(!CPSUtils.isNullOrEmpty(rowDetails.getString("dvNo")) && !CPSUtils.isNullOrEmpty(rowDetails.getString("dvDate")) && !CPSUtils.isNullOrEmpty(rowDetails.getString("cdaAmount")) ){
				loanCdaDTO.setCdaReturnFlag(Integer.valueOf(CPSConstants.STATUSENABLE));
			}
				
			session.saveOrUpdate(loanCdaDTO);
			
		}
		session.flush();
		loanBean.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return loanBean;
	}

	@SuppressWarnings("unchecked")
	public List<LoanCDADetailsDTO> getHQReportList() throws Exception {
		Session session = null;
		List<LoanCDADetailsDTO> loanHQReportList = null;
		try {
			session = hibernateUtils.getSession();
			loanHQReportList=session.createSQLQuery("select distinct sending_report_no sendingReportNumber from loan_cda_details where status=? and cda_return_flag!=1 and hq_return_flag=1").addScalar("sendingReportNumber", Hibernate.STRING).setInteger(0,Integer.valueOf(CPSConstants.STATUSCOMPLETED)).setResultTransformer(Transformers.aliasToBean(LoanCDADetailsDTO.class)).list();
		} catch (Exception e) {
			throw e;
		}
		return loanHQReportList;
	}

	@SuppressWarnings("unchecked")
	public LoanManagementBean getCDAReportList(LoanManagementBean loanBean)
			throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
		loanBean.setLoanCDAist((List<LoanCDADetailsDTO>)session.createCriteria(LoanCDADetailsDTO.class).add(Expression.eq(CPSConstants.STATUS, 8)).add(Expression.ne(CPSConstants.CDARETURNFLAG, 1)).list());
		}
		catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	@SuppressWarnings("unchecked")
	public LoanManagementBean getPaybillBACReportList(
			LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
		loanBean.setPaybillBACReportList((List<LoanCDADetailsDTO>)session.createCriteria(LoanCDADetailsDTO.class).add(Expression.eq(CPSConstants.STATUS, 8)).add(Expression.ne(CPSConstants.CDARETURNFLAG, 1)).add(Expression.eq(CPSConstants.SENDINGREPORTNUMBER,loanBean.getReportNumber())).add(Expression.eq(CPSConstants.SENDINGREPORTDATE,loanBean.getReportDate())).list());
		}
		catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	@SuppressWarnings("unchecked")
	public LoanManagementBean getAppliedFestivalList(LoanManagementBean loanBean)
			throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			StringBuilder query = new StringBuilder();

			query.append("select lcd.id id,(select lpd.sfid from loan_payment_details lpd where lpd.request_id=lcd.request_id) sfID,(select emp.name_in_service_book from emp_master emp where emp.sfid=(select lpd.sfid from loan_payment_details lpd where lpd.request_id=lcd.request_id)) empName,get_history_id(lcd.request_id) historyID,lcd.request_id requestID,lcd.sanction_no sanctionNo,lcd.bill_no billNo,lcd.acc_officer accOfficer,lcd.cfa_officer cfaOfficer,lcd.dv_no dvNo,lcd.dv_date dvDate,lcd.cda_amount cdaAmount " +
					"from loan_cda_details lcd where lcd.status=1 and lcd.cda_return_flag!=1 and  lcd.request_id in " +
					"(select request_id from loan_payment_details lpd " +
					"where lpd.loan_type_id=? and lpd.loan_subtype_id=? and lpd.status=? and lpd.requested_date between " +
					"(select from_date from financial_year_master where id=?)" +
					" and " +
					"(select to_date from financial_year_master where id=?))");
			loanBean.setAppliedLFestivalList(session.createSQLQuery(query.toString()).addScalar("id",Hibernate.INTEGER).addScalar("sfID",Hibernate.STRING).addScalar("empName",Hibernate.STRING).addScalar("historyID",Hibernate.STRING).addScalar("requestID",Hibernate.INTEGER).addScalar("sanctionNo",Hibernate.INTEGER).addScalar("billNo",Hibernate.INTEGER).addScalar("accOfficer",Hibernate.STRING).addScalar("cfaOfficer",Hibernate.STRING).addScalar("dvNo",Hibernate.INTEGER).addScalar("dvDate",Hibernate.DATE).addScalar("cdaAmount",Hibernate.INTEGER)
					.setResultTransformer(Transformers.aliasToBean(LoanCDADetailsDTO.class)).setInteger(0,Integer.valueOf(CPSConstants.FESTIVALLOANID)).setInteger(1, Integer.valueOf(loanBean.getLoanSubType())).setInteger(2, Integer.valueOf(CPSConstants.STATUSCOMPLETED)).setInteger(3, Integer.valueOf(loanBean.getFinancialYear())).setInteger(4, Integer.valueOf(loanBean.getFinancialYear())).list());
			
		} catch (Exception e) {
			
			throw e;
		}
		return loanBean;
	}

	@SuppressWarnings("unchecked")
	public LoanManagementBean getHQReportNumberList(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			StringBuilder query = new StringBuilder();
			query.append("select distinct hq_report_no hqReportNumber,to_date(hq_report_date) hqReportDate from loan_cda_details where hq_return_flag=1 and status="+CPSConstants.STATUSCOMPLETED);
			loanBean.setReportNumberList(session.createSQLQuery(query.toString()).addScalar("hqReportNumber").addScalar("hqReportDate",Hibernate.TIMESTAMP).setResultTransformer(Transformers.aliasToBean(LoanCDADetailsDTO.class)).list());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	
		
	}

	@SuppressWarnings("unchecked")
	public LoanManagementBean getSanctionContingentList(LoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
		loanBean.setSanctionConReportList(session.createCriteria(LoanCDADetailsDTO.class).add(Expression.eq(CPSConstants.STATUS, 8)).add(Expression.eq(CPSConstants.HQREPORTNUMBER,loanBean.getReportNumber())).add(Expression.eq(CPSConstants.HQREPORTDATE,loanBean.getReportDate())).list());
		}
		catch (Exception e) {
			throw e;
		}
		return loanBean;
	}
}
