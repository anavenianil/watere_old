package com.callippus.web.leave.dao.admin;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.CasualityDetailsDTO;
import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.DoPartDTO;
import com.callippus.web.beans.dto.DoPartSerialNoDTO;
import com.callippus.web.beans.dto.EmploymentDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.OrgInstanceDTO;
import com.callippus.web.beans.dto.TaskHolderDesignationsDTO;
import com.callippus.web.beans.dto.TaskHolderDetailsDTO;
import com.callippus.web.beans.dto.TypeDesigDetailsDTO;
import com.callippus.web.beans.dto.TypeDetailsDTO;
import com.callippus.web.beans.dto.YearTypeDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.business.requestprocess.LeaveRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.leave.beans.admin.LeaveAdministratorBean;
import com.callippus.web.leave.dto.AvailableLeavesDTO;
import com.callippus.web.leave.dto.EmpLeaveSpellsDTO;
import com.callippus.web.leave.dto.ErpAvailableLeavesDTO;
import com.callippus.web.leave.dto.ErpEmpLeavesDTO;
import com.callippus.web.leave.dto.LeaveAccountDTO;
import com.callippus.web.leave.dto.LeaveBusinessRulesDTO;
import com.callippus.web.leave.dto.LeaveBusinessRulesVO;
import com.callippus.web.leave.dto.LeaveDetailsDTO;
import com.callippus.web.leave.dto.LeaveExceptionalEmployees;
import com.callippus.web.leave.dto.LeaveManualEntryDTO;
import com.callippus.web.leave.dto.LeaveTxnDTO;
import com.callippus.web.leave.dto.LeaveTypeDTO;
import com.callippus.web.leave.dto.LeaveYearlyBalanceDTO;

@SuppressWarnings("serial")
@Service
public class SQLLeaveAdministratorDAO implements ILeaveAdministratorDAO, Serializable {
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private LeaveRequestProcess leaveRequestProcess;

	@Override
	@SuppressWarnings("unchecked")
	public LeaveAdministratorBean getEmpAvailableLeaves(LeaveAdministratorBean leaveBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			leaveBean.setEntryLeaves(session.createCriteria(LeaveManualEntryDTO.class).list());
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}
	
	//getEmpAvailableOrNot
	@Override
	@SuppressWarnings("unchecked")
	public LeaveAdministratorBean getEmpAvailableOrNot(LeaveAdministratorBean leaveBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			//COMMENTED BY BKR 28/06/2016
			//leaveBean.setEntryErpLeaves(session.createCriteria(ErpAvailableLeavesDTO.class).list());
			leaveBean.setEntryErpLeaves(session.createCriteria(ErpEmpLeavesDTO.class).list());
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}
	
	//getEmplyeeLeavesSave
	@Override
	@SuppressWarnings("unchecked")
	public LeaveAdministratorBean getEmplyeeLeavesSave(LeaveAdministratorBean leaveBean) throws Exception {
		
		Session session = null;
		ErpAvailableLeavesDTO erpAvailableLeavesDTO = null;
		
		
		ErpEmpLeavesDTO erpEmpLeavesDTO=null;
		try {
			erpEmpLeavesDTO=new ErpEmpLeavesDTO();
			erpEmpLeavesDTO.setSfID(leaveBean.getEntrySfid());
			erpEmpLeavesDTO.setLeaveType("Annual Leave");
			erpEmpLeavesDTO.setLeaveCode("AL");
			erpEmpLeavesDTO.setStatus(1);
			erpEmpLeavesDTO.setNoOfDays(leaveBean.getAnnualLeaves());
			
			session = hibernateUtils.getSession();
			session.createCriteria(ErpEmpLeavesDTO.class);
			session.save(erpEmpLeavesDTO);
			session.flush();
			session.clear();
			
			
			
			erpEmpLeavesDTO.setSfID(leaveBean.getEntrySfid());
			erpEmpLeavesDTO.setLeaveType("Sick Leave");
			erpEmpLeavesDTO.setLeaveCode("SL");
			erpEmpLeavesDTO.setStatus(1);
			erpEmpLeavesDTO.setNoOfDays(leaveBean.getSickLeaves());
			session = hibernateUtils.getSession();
			session.createCriteria(ErpEmpLeavesDTO.class);
			session.save(erpEmpLeavesDTO);
			session.flush();
			session.clear();
			
			
			erpEmpLeavesDTO.setSfID(leaveBean.getEntrySfid());
			erpEmpLeavesDTO.setLeaveType("Maternity Leave");
			erpEmpLeavesDTO.setLeaveCode("ML");
			erpEmpLeavesDTO.setStatus(1);
			erpEmpLeavesDTO.setNoOfDays(leaveBean.getMaternityLeaves());
			session = hibernateUtils.getSession();
			session.createCriteria(ErpEmpLeavesDTO.class);
			session.save(erpEmpLeavesDTO);
			session.flush();
			session.clear();
			
			erpEmpLeavesDTO.setSfID(leaveBean.getEntrySfid());
			erpEmpLeavesDTO.setLeaveType("Paternity Leave");
			erpEmpLeavesDTO.setLeaveCode("PL");
			erpEmpLeavesDTO.setStatus(1);
			erpEmpLeavesDTO.setNoOfDays(leaveBean.getPeternityLeaves());
			session = hibernateUtils.getSession();
			session.createCriteria(ErpEmpLeavesDTO.class);
			session.save(erpEmpLeavesDTO);
			session.flush();
			session.clear();
			
		} catch (Exception e) {
			throw e;
		
		} finally {
			session.clear();
		}
		
		//save leave details in single row
		
		/*
		try {
			
			erpAvailableLeavesDTO = new ErpAvailableLeavesDTO();
			erpAvailableLeavesDTO.setAnnualLeaves(leaveBean.getAnnualLeaves());
			erpAvailableLeavesDTO.setSfID(leaveBean.getEntrySfid());
			erpAvailableLeavesDTO.setSickLeaves(leaveBean.getSickLeaves());
			erpAvailableLeavesDTO.setMaternityLeaves(leaveBean.getMaternityLeaves());
			erpAvailableLeavesDTO.setPeternityLeaves(leaveBean.getPeternityLeaves());

			session = hibernateUtils.getSession();
			session.createCriteria(ErpAvailableLeavesDTO.class);
			session.save(erpAvailableLeavesDTO);
			session.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.clear();
		}*/
		return leaveBean;
		
}
	
	
	

	@Override
	@SuppressWarnings("unchecked")
	public LeaveAdministratorBean getSpellsDetails(LeaveAdministratorBean leaveBean) throws Exception {
		Session session = null;
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			session = hibernateUtils.getSession();
			Iterator<EmpLeaveSpellsDTO> leave = session.createCriteria(EmpLeaveSpellsDTO.class).add(Expression.eq("sfID", leaveBean.getSfID())).list().iterator();
			while (leave.hasNext()) {
				EmpLeaveSpellsDTO empLeaveBean = leave.next();
				if (empLeaveBean.getLeaveTypeID() == Integer.valueOf(CPSConstants.EL)) {
					map.put("ltcEncashmentDays", String.valueOf(empLeaveBean.getLtcEncashmentDays()));
				}
				if (empLeaveBean.getLeaveTypeID() == Integer.valueOf(CPSConstants.CCL)) {
					map.put("cclServiceSpellsCount", String.valueOf(empLeaveBean.getServiceSpellsCount()));
					map.put("cclYearSpellsCount", String.valueOf(empLeaveBean.getYearSpellsCount()));
				}
				if (empLeaveBean.getLeaveTypeID() == Integer.valueOf(CPSConstants.SL)) {
					map.put("slServiceSpellsCount", String.valueOf(empLeaveBean.getServiceSpellsCount()));
					map.put("slYearSpellsCount", String.valueOf(empLeaveBean.getYearSpellsCount()));
				}
				if (empLeaveBean.getLeaveTypeID() == Integer.valueOf(CPSConstants.EOLWOMC)) {
					map.put("eolwomc", String.valueOf(empLeaveBean.getLeavesCount()));
				}
			}
			leaveBean.setSpellsDetails(map);

		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	@Override
	public String saveEnterAvailableLeaves(LeaveAdministratorBean leaveBean) throws Exception {
		String message = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			LeaveManualEntryDTO leaveManEntry = (LeaveManualEntryDTO) session.createCriteria(LeaveManualEntryDTO.class).add(Expression.eq("sfID", leaveBean.getEntrySfid()))
					.add(Expression.isNotNull("verifiedBy")).uniqueResult();
			if (CPSUtils.isNull(leaveManEntry)) {
				leaveManEntry = new LeaveManualEntryDTO();
			}
			LeaveManualEntryDTO leaveManualEntry = (LeaveManualEntryDTO) session.createCriteria(LeaveManualEntryDTO.class).add(Expression.eq("sfID", leaveBean.getEntrySfid())).uniqueResult();
			if (CPSUtils.isNull(leaveManualEntry)) {
				leaveManualEntry = new LeaveManualEntryDTO();
				leaveManualEntry.setCreatedBy(leaveBean.getSfID());
				leaveManualEntry.setCreationDate(CPSUtils.getCurrentDate());
			}
			leaveManualEntry.setSfID(leaveBean.getEntrySfid());
			leaveManualEntry.setEl(leaveBean.getEarnedLeaveId());
			leaveManualEntry.setHpl(leaveBean.getHalfPayLeaveId());
			leaveManualEntry.setCl(leaveBean.getCasualLeaveId());
			leaveManualEntry.setLnd(leaveBean.getLeaveNotDue());
			if (!CPSUtils.isNullOrEmpty(leaveBean.getEolLeaveId())) {
				leaveManualEntry.setEol(leaveBean.getEolLeaveId());
			}
			if (!CPSUtils.isNullOrEmpty(leaveBean.getEolWOMCLeaveId())) {
				leaveManualEntry.setEolwomc(leaveBean.getEolWOMCLeaveId());
			}
			if (!CPSUtils.isNullOrEmpty(leaveBean.getCmlwomc())) {
				leaveManualEntry.setCmlwomc(leaveBean.getCmlwomc());
			}
			// else { leaveManualEntry.setCmlwomc("0"); }
			if (!CPSUtils.isNullOrEmpty(leaveBean.getLeaveNotDuewomc())) {
				leaveManualEntry.setLndwomc(leaveBean.getLeaveNotDuewomc());
			}
			// else { leaveManualEntry.setLndwomc("0"); }
			if (!CPSUtils.isNullOrEmpty(leaveBean.getChildCareLeave())) {
				EmployeeBean empBean = (EmployeeBean) session.createCriteria(EmployeeBean.class).add(Expression.eq(CPSConstants.USERSFID, leaveBean.getEntrySfid())).uniqueResult();
				if (CPSUtils.compareStrings(empBean.getGender(), "F")) {
					leaveManualEntry.setCcl(leaveBean.getChildCareLeave());
					if (!CPSUtils.isNullOrEmpty(leaveBean.getCclYearSpells())) {
						saveOrUpdateLeaveSpells(leaveManualEntry.getSfID(), CPSConstants.CCL, leaveBean.getCclServiceSpells(), leaveBean.getCclYearSpells(), null, null);
					}
				}
			}
			leaveManualEntry.setStl(leaveBean.getStudyLeave());
			leaveManualEntry.setLastModifiedBy(leaveBean.getSfID());
			leaveManualEntry.setLastModifiedDate(CPSUtils.getCurrentDate());
			if (CPSUtils.compareStrings(leaveBean.getVerifyFlag(), CPSConstants.Y)) {
				leaveManualEntry.setVerifiedBy(leaveBean.getSfID());
			}
			session.saveOrUpdate(leaveManualEntry);
			// Insert no of spells
			/*if (!CPSUtils.isNullOrEmpty(leaveBean.getSlYearSpells())) {
				saveOrUpdateLeaveSpells(leaveManualEntry.getSfID(), CPSConstants.SL, leaveBean.getSlServiceSpells(),
				leaveBean.getSlYearSpells(), null, null); }
			*/
			if (!CPSUtils.isNullOrEmpty(leaveBean.getLtcEncashmentDays())) {
				saveOrUpdateLeaveSpells(leaveManualEntry.getSfID(), CPSConstants.EL, null, null, leaveBean.getLtcEncashmentDays(), null);
			}
			if (!CPSUtils.isNullOrEmpty(leaveManualEntry.getCmlwomc())) {
				saveOrUpdateLeaveSpells(leaveManualEntry.getSfID(), CPSConstants.CML, null, null, null, leaveManualEntry.getCmlwomc());
			}
			if (!CPSUtils.isNullOrEmpty(leaveManualEntry.getLndwomc())) {
				saveOrUpdateLeaveSpells(leaveManualEntry.getSfID(), CPSConstants.LND, null, null, null, leaveManualEntry.getLndwomc());
			}
			if (!CPSUtils.isNullOrEmpty(leaveBean.getEolWOMCInSerive())) {
				saveOrUpdateLeaveSpells(leaveManualEntry.getSfID(), CPSConstants.EOLWOMC, null, null, null, leaveBean.getEolWOMCInSerive());
			}
			if (CPSUtils.compareStrings(leaveBean.getVerifyFlag(), CPSConstants.Y)) {
				LeaveDetailsDTO leaveDetailsDTO = new LeaveDetailsDTO();
				leaveDetailsDTO.setType(CPSConstants.BALANCE);
				leaveDetailsDTO.setSfID(leaveManualEntry.getSfID());

				if (CPSUtils.isNullOrEmpty(leaveManEntry.getEl())) {
					leaveDetailsDTO.setLeaveTypeID(CPSConstants.EL);
					leaveDetailsDTO.setNoOfDays(leaveBean.getEarnedLeaveId());
					leaveRequestProcess.updateLeaveAccount(leaveDetailsDTO);
				}
				if (CPSUtils.isNullOrEmpty(leaveManEntry.getHpl())) {
					leaveDetailsDTO.setLeaveTypeID(CPSConstants.HPL);
					leaveDetailsDTO.setNoOfDays(leaveBean.getHalfPayLeaveId());
					leaveRequestProcess.updateLeaveAccount(leaveDetailsDTO);
				}
				if (!CPSUtils.isNullOrEmpty(leaveBean.getEolLeaveId()) && CPSUtils.isNullOrEmpty(leaveManEntry.getEol())) {
					leaveDetailsDTO.setLeaveTypeID(CPSConstants.EOL);
					leaveDetailsDTO.setNoOfDays(leaveBean.getEolLeaveId());
					leaveRequestProcess.updateLeaveAccount(leaveDetailsDTO);
				}
				if (!CPSUtils.isNullOrEmpty(leaveBean.getEolWOMCLeaveId()) && CPSUtils.isNullOrEmpty(leaveManEntry.getEolwomc())) {
					leaveDetailsDTO.setLeaveTypeID(CPSConstants.EOL);
					leaveDetailsDTO.setNoOfDays(leaveBean.getEolWOMCLeaveId());
					leaveRequestProcess.updateLeaveAccount(leaveDetailsDTO);
				}
				// Insert the details into leave transaction table
				insertIntoLeaveTxnDetails(leaveManualEntry);
				removeFromLeaveExceptionalEmployees(leaveManualEntry.getSfID(), leaveManualEntry.getLastModifiedBy());
			}
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		} finally {
			session.flush();
		}
		return message;
	}

	public void removeFromLeaveExceptionalEmployees(String userId, String lastModifiedPerson) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			session.createSQLQuery("UPDATE leave_exceptional_employees SET status = 0, last_modified_by = ?, last_modified_date = ? WHERE sfid = ? "
					+ "AND status = 1").setString(0, lastModifiedPerson).setString(1, CPSUtils.getCurrentDate()).setString(2, userId).executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void dataEntry() throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			List<LeaveManualEntryDTO> leaveManualEntrylist = session.createCriteria(LeaveManualEntryDTO.class).list();
			//int i = 1;
			for (LeaveManualEntryDTO leaveManualEntry : leaveManualEntrylist) {
				//System.out.println("count:" + i + "   :::sfid:" + leaveManualEntry.getSfID());
				//i++;
				LeaveDetailsDTO leaveDetailsDTO = new LeaveDetailsDTO();
				leaveDetailsDTO.setType(CPSConstants.BALANCE);
				leaveDetailsDTO.setSfID(leaveManualEntry.getSfID());

				if (!CPSUtils.isNullOrEmpty(leaveManualEntry.getEl())) {
					leaveDetailsDTO.setLeaveTypeID(CPSConstants.EL);
					leaveDetailsDTO.setNoOfDays(leaveManualEntry.getEl());
					leaveRequestProcess.updateLeaveAccount(leaveDetailsDTO);
				}
				if (!CPSUtils.isNullOrEmpty(leaveManualEntry.getHpl())) {
					leaveDetailsDTO.setLeaveTypeID(CPSConstants.HPL);
					leaveDetailsDTO.setNoOfDays(leaveManualEntry.getHpl());
					leaveRequestProcess.updateLeaveAccount(leaveDetailsDTO);
				}
				if (!CPSUtils.isNullOrEmpty(leaveManualEntry.getEol())) {
					leaveDetailsDTO.setLeaveTypeID(CPSConstants.EOL);
					leaveDetailsDTO.setNoOfDays(leaveManualEntry.getHpl());
					leaveRequestProcess.updateLeaveAccount(leaveDetailsDTO);
				}

				if (!CPSUtils.isNullOrEmpty(leaveManualEntry.getCl())) {
					saveAvailableLeaves(CPSConstants.CL, leaveManualEntry.getSfID(), leaveManualEntry.getCl());
				}
				if (!CPSUtils.isNullOrEmpty(leaveManualEntry.getHpl())) {
					saveAvailableLeaves(CPSConstants.HPL, leaveManualEntry.getSfID(), leaveManualEntry.getHpl());
				}
				if (!CPSUtils.isNullOrEmpty(leaveManualEntry.getEl())) {
					saveAvailableLeaves(CPSConstants.EL, leaveManualEntry.getSfID(), leaveManualEntry.getEl());
				}
				if (!CPSUtils.isNullOrEmpty(leaveManualEntry.getLnd())) {
					saveAvailableLeaves(CPSConstants.LND, leaveManualEntry.getSfID(), leaveManualEntry.getLnd());
				}
				if (!CPSUtils.isNullOrEmpty(leaveManualEntry.getCcl())) {
					saveAvailableLeaves(CPSConstants.CCL, leaveManualEntry.getSfID(), leaveManualEntry.getCcl());
				}
				if (!CPSUtils.isNullOrEmpty(leaveManualEntry.getStl())) {
					saveAvailableLeaves(CPSConstants.SL, leaveManualEntry.getSfID(), leaveManualEntry.getStl());
				}
				if (!CPSUtils.isNullOrEmpty(leaveManualEntry.getEol())) {
					saveAvailableLeaves(CPSConstants.EOL, leaveManualEntry.getSfID(), leaveManualEntry.getEol());
				}
			}

		} catch (Exception e) {
			throw e;
		}
	}

	public void saveOrUpdateLeaveSpells(final String sfid, final String leaveTypeID, final String serviceSpells, final String yearSpells, final String encashment, final String leavesWoMc)
			throws Exception {
		Session session = null;
		EmpLeaveSpellsDTO empLeaveSpells = null;
		try {
			session = hibernateUtils.getSession();

			empLeaveSpells = (EmpLeaveSpellsDTO) session.createCriteria(EmpLeaveSpellsDTO.class).add(Expression.eq("sfID", sfid)).add(Expression.eq("leaveTypeID", Integer.valueOf(leaveTypeID)))
					.uniqueResult();

			if (CPSUtils.isNull(empLeaveSpells)) {
				empLeaveSpells = new EmpLeaveSpellsDTO();
				empLeaveSpells.setSfID(sfid);
				empLeaveSpells.setLeaveTypeID(Integer.valueOf(leaveTypeID));
			}
			empLeaveSpells.setYearSpellsCount(CPSUtils.convertToInteger(yearSpells));
			empLeaveSpells.setServiceSpellsCount(CPSUtils.convertToInteger(serviceSpells));
			empLeaveSpells.setLtcEncashmentDays(CPSUtils.convertToInteger(encashment));
			empLeaveSpells.setLeavesCount(CPSUtils.convertToInteger(leavesWoMc));

			session.saveOrUpdate(empLeaveSpells);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LeaveManualEntryDTO> getLeaveBalanceList(LeaveAdministratorBean leaveBean) throws Exception {
		Session session = null;
		List<LeaveManualEntryDTO> list = null;
		StringBuffer sb = new StringBuffer();
		try {
			session = hibernateUtils.getSession();
			sb.append("select lme.id, emp.sfid as sfID, emp.name_in_service_book empName, lme.el, lme.hpl, lme.cl, lme.lnd, lme.ccl, lme.stl, lme.eol, "
					+ "lme.eol_wo_mc eolwomc, lme.cml_wo_mc cmlwomc, lme.lnd_wo_mc lndwomc, lme.verified_by verifiedBy, (select les.ltc_encashment_days "
					+ "from leave_emp_spells les where les.sfid = emp.sfid and les.leave_type_id = 1) encash, (select les.year_spells_count from leave_emp_spells les "
					+ "where les.sfid = emp.sfid and les.leave_type_id = 9) cclyear, (select les.service_spells_count from leave_emp_spells les "
					+ "where les.sfid = emp.sfid and les.leave_type_id = 9) cclservice, (select les.leaves_wo_mc_count from leave_emp_spells les "
					+ "where les.sfid = emp.sfid and les.leave_type_id = 15) eolInService, (select les.year_spells_count from leave_emp_spells les "
					+ "where les.sfid = emp.sfid and les.leave_type_id = 10) slyear, (select les.service_spells_count from leave_emp_spells les "
					+ "where les.sfid = emp.sfid and les.leave_type_id = 10) slservice from leave_manual_entry lme, emp_master emp, designation_master desig "
					+ "where emp.status = 1 and desig.status = 1 and emp.designation_id = desig.id and lme.sfid = emp.sfid");
			if (!CPSUtils.isNullOrEmpty(leaveBean.getDesignationID()) && !CPSUtils.compareStrings(leaveBean.getDesignationID(), CPSConstants.SELECT)) {
				sb.append(" and desig.id = " + leaveBean.getDesignationID());
			}
			sb.append(" order by lme.verified_by desc");
			list = session.createSQLQuery(sb.toString()).addScalar("sfID").addScalar("empName").addScalar("el", Hibernate.STRING).addScalar("hpl", Hibernate.STRING)
					.addScalar("cl", Hibernate.STRING).addScalar("lnd", Hibernate.STRING).addScalar("ccl", Hibernate.STRING).addScalar("stl", Hibernate.STRING)
					.addScalar("verifiedBy").addScalar("encash", Hibernate.STRING).addScalar("cclyear", Hibernate.STRING).addScalar("cclservice", Hibernate.STRING)
					.addScalar("slyear", Hibernate.STRING).addScalar("slservice", Hibernate.STRING).addScalar("eol", Hibernate.STRING).addScalar("eolwomc", Hibernate.STRING)
					.addScalar("cmlwomc", Hibernate.STRING).addScalar("lndwomc", Hibernate.STRING).addScalar("eolInService", Hibernate.STRING)
					.setResultTransformer(Transformers.aliasToBean(LeaveManualEntryDTO.class)).list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public String submitLeaveBalance(LeaveAdministratorBean leaveBean) throws Exception {
		String message = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			LeaveManualEntryDTO leaveManualEntry = (LeaveManualEntryDTO) session.createCriteria(LeaveManualEntryDTO.class).add(Expression.eq("sfID", leaveBean.getEntrySfid())).uniqueResult();

			if (!CPSUtils.isNullOrEmpty(leaveManualEntry.getCl())) {
				saveAvailableLeaves(CPSConstants.CL, leaveBean.getEntrySfid(), leaveManualEntry.getCl());
			}
			if (!CPSUtils.isNullOrEmpty(leaveManualEntry.getHpl())) {
				saveAvailableLeaves(CPSConstants.HPL, leaveBean.getEntrySfid(), leaveManualEntry.getHpl());
			}
			if (!CPSUtils.isNullOrEmpty(leaveManualEntry.getEl())) {
				saveAvailableLeaves(CPSConstants.EL, leaveBean.getEntrySfid(), leaveManualEntry.getEl());
			}
			if (!CPSUtils.isNullOrEmpty(leaveManualEntry.getLnd())) {
				saveAvailableLeaves(CPSConstants.LND, leaveBean.getEntrySfid(), leaveManualEntry.getLnd());
			}
			if (!CPSUtils.isNullOrEmpty(leaveManualEntry.getCcl())) {
				saveAvailableLeaves(CPSConstants.CCL, leaveBean.getEntrySfid(), leaveManualEntry.getCcl());
			}
			if (!CPSUtils.isNullOrEmpty(leaveManualEntry.getStl())) {
				saveAvailableLeaves(CPSConstants.SL, leaveBean.getEntrySfid(), leaveManualEntry.getStl());
			}
			if (!CPSUtils.isNullOrEmpty(leaveManualEntry.getEol())) {
				saveAvailableLeaves(CPSConstants.EOL, leaveBean.getEntrySfid(), leaveManualEntry.getEol());
			}
			if (!CPSUtils.isNullOrEmpty(leaveManualEntry.getEolwomc())) {
				saveAvailableLeaves(CPSConstants.EOLWOMC, leaveBean.getEntrySfid(), leaveManualEntry.getEolwomc());
			}
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		} finally {
			session.flush();
		}
		return message;
	}

	public String saveAvailableLeaves(final String leaveTypeID, final String sfid, final String leaves) throws Exception {
		Session session = null;
		String message = null;
		AvailableLeavesDTO availableLeaves = null;
		LeaveTypeDTO leaveTypeDTO = null;
		try {
			session = hibernateUtils.getSession();

			leaveTypeDTO = (LeaveTypeDTO) session.get(LeaveTypeDTO.class, Integer.valueOf(leaveTypeID));
			availableLeaves = (AvailableLeavesDTO) session.createCriteria(AvailableLeavesDTO.class).add(Expression.eq("sfID", sfid)).add(Expression.eq("leaveTypeDetails", leaveTypeDTO))
					.uniqueResult();
			if (CPSUtils.isNullOrEmpty(availableLeaves)) {
				availableLeaves = new AvailableLeavesDTO();
				availableLeaves.setSfID(sfid);
				availableLeaves.setLeaveTypeDetails(leaveTypeDTO);
				availableLeaves.setAvailableLeaves(Float.parseFloat(leaves));

				session.saveOrUpdate(availableLeaves);
			}
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	/**
	 * When the request is completed , then we should change the leave request status to completed in leave request details table & history table and if any other cancel/convert request is applied
	 * then we should decline those requests.
	 */

	@Override
	public LeaveAdministratorBean completeLeaveRequests(LeaveAdministratorBean leaveBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			if(!CPSUtils.isNullOrEmpty(leaveBean.getDoPartNo())){
				String [] doPart=leaveBean.getDoPartNo().split("#");
					leaveBean.setDoPartDate(doPart[1]);
					leaveBean.setDoPartNo(doPart[0]);
				}
			if(!CPSUtils.isNullOrEmpty(leaveBean.getCasualityId())){
				String [] casualityId=leaveBean.getCasualityId().split("#");
					leaveBean.setCasualityId(casualityId[0]);
				}
			String[] requestArr = leaveBean.getRequestIDs().split(",");
			for (String requestID : requestArr) {
				String id[] = requestID.split("#");
				// check whether this request was sanctioned or not
				// Change the workflow status of maximum history ID from sactioned to completed
				session
						.createSQLQuery(
								"update request_workflow_history set status=?,stage_status=? where id=(select id from (select max(id) id,max(request_stage) from request_workflow_history where request_id=? and ip_address is not null)) and status=?")
						.setString(0, CPSConstants.STATUSCOMPLETED).setString(1, CPSConstants.STATUSCOMPLETED).setString(2, id[0]).setString(3, CPSConstants.STATUSSANCTIONED).executeUpdate();

				// if (count > 0) {
				// update the status to complete=8
				if(!leaveBean.getDoPartNo().equals("")){
					if (id[1].equals("leave")) {
						leaveRequestProcess.updateLeaveRequestStatus(id[0], CPSConstants.LEAVE, CPSConstants.STATUSCOMPLETED, String.valueOf(leaveBean.getDoPartNo()));
						leaveRequestProcess.getLeaveDetailsForAccount(id[0], CPSConstants.LEAVE);
					} else if (id[1].equals("leaveCancel")) {
						leaveRequestProcess.updateLeaveRequestStatus(id[0], CPSConstants.CANCELLEAVEREQ, CPSConstants.STATUSCOMPLETED, String.valueOf(leaveBean.getDoPartNo()));
						leaveRequestProcess.getLeaveDetailsForAccount(id[0], CPSConstants.CANCELLEAVEREQ);
					} else if (id[1].equals("leaveConvert")) {
						leaveRequestProcess.updateLeaveRequestStatus(id[0], CPSConstants.CONVERTLEAVEREQ, CPSConstants.STATUSCOMPLETED, String.valueOf(leaveBean.getDoPartNo()));
						leaveRequestProcess.getLeaveDetailsForAccount(id[0], CPSConstants.CONVERTLEAVEREQ);
					}
					
					//insert into DOPART_II_SLNO
					DoPartSerialNoDTO serialNoDTO=new DoPartSerialNoDTO();
					serialNoDTO.setRefDoPartId(Integer.parseInt(leaveBean.getDoPartNo()));
					serialNoDTO.setModuleId(2);
					serialNoDTO.setCasualityId(Integer.parseInt(leaveBean.getCasualityId()));
					serialNoDTO.setSlNo(leaveBean.getSerialNumber());
					serialNoDTO.setRequestId(id[0]);
					serialNoDTO.setStatus(1);
					serialNoDTO.setCreationDate(CPSUtils.getCurrentDate());
					serialNoDTO.setCreatedBy(leaveBean.getSfID());
					serialNoDTO.setLastModifiedBy(leaveBean.getSfID());
					serialNoDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					session.saveOrUpdate(serialNoDTO);
				}
				//for no action 
				else {
					if (id[1].equals("leave")) {
						leaveRequestProcess.updateLeaveRequestStatus(id[0], CPSConstants.LEAVE, CPSConstants.STATUSCOMPLETED,String.valueOf(leaveBean.getDoPartNo()));
						leaveRequestProcess.getLeaveDetailsForAccount(id[0], CPSConstants.LEAVE);
					} else if (id[1].equals("leaveCancel")) {
						leaveRequestProcess.updateLeaveRequestStatus(id[0], CPSConstants.CANCELLEAVEREQ, CPSConstants.STATUSCOMPLETED, String.valueOf(leaveBean.getDoPartNo()));
						leaveRequestProcess.getLeaveDetailsForAccount(id[0], CPSConstants.CANCELLEAVEREQ);
					} else if (id[1].equals("leaveConvert")) {
						leaveRequestProcess.updateLeaveRequestStatus(id[0], CPSConstants.CONVERTLEAVEREQ, CPSConstants.STATUSCOMPLETED, String.valueOf(leaveBean.getDoPartNo()));
						leaveRequestProcess.getLeaveDetailsForAccount(id[0], CPSConstants.CONVERTLEAVEREQ);
					}
				}
				

				// }
				
			
			}
			leaveBean.setMessage(CPSConstants.SUCCESS);

		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public LeaveAdministratorBean getDoPartSearchList(LeaveAdministratorBean leaveBean) throws Exception {
		Session session = null;
		StringBuffer sb = new StringBuffer();
		try {
			session = hibernateUtils.getSession();
			sb
					.append("select distinct id,doPartNumber,gazType,doPartDate,preDoPartNo,preDoPartDate,releasedDate,acceptedDate,status from (select 'LEAVE' type,rnd.id id,emp.sfid sfID,emp.name_in_service_book empName,rnd.ref_number doPartNumber,rnd.gaz_type gazType, Rnd.Type_Id TypeId,rnd.predopartno preDoPartNo,rnd.predopartdate preDoPartDate,rnd.released_date releasedDate,rnd.accepted_date acceptedDate,rnd.ref_date doPartDate,rnd.status status,(select name from designation_master where id=lrd.designation_id ) designationName from leave_request_details lrd,emp_master emp,reference_number_details rnd where lrd.do_part_id =rnd.id	and lrd.convert_ref_id is null and lrd.sfid=emp.sfid and rnd.ref_type='D'   "
							+ "union all select 'LEAVE CONVERSION' type,rnd.id,emp.sfid sfID,emp.name_in_service_book empName,rnd.ref_number doPartNo,rnd.gaz_type gazType,Rnd.Type_Id TypeId,rnd.predopartno preDoPartNo,rnd.predopartdate preDoPartDate,rnd.released_date releasedDate,rnd.accepted_date acceptedDate,rnd.ref_date doPartDate,rnd.status status,(select name from designation_master where id=emp.designation_id) designationName from leave_conversion_details lcd,emp_master emp,reference_number_details rnd where lcd.do_part_id =rnd.id and lcd.sfid=emp.sfid and rnd.ref_type='D' "
							+ "union all select 'LEAVE CANCELLATION' type,rnd.id,emp.sfid sfID,emp.name_in_service_book empName,rnd.ref_number doPartNo,rnd.gaz_type gazType,Rnd.Type_Id TypeId,rnd.predopartno preDoPartNo,rnd.predopartdate preDoPartDate,rnd.released_date releasedDate,rnd.accepted_date acceptedDate,rnd.ref_date doPartDate,rnd.status status,(select name from designation_master where id=emp.designation_id ) designationName from leave_cancellation_details lcld,emp_master emp,reference_number_details rnd	where lcld.do_part_id =rnd.id and lcld.sfid=emp.sfid and rnd.ref_type='D'"
							+ "union all select 'LTC ADVANCE' type,rnd.id,emp.sfid sfID,emp.name_in_service_book empName, rnd.ref_number doPartNo,rnd.gaz_type gazType,Rnd.Type_Id TypeId,rnd.predopartno preDoPartNo,rnd.predopartdate preDoPartDate,rnd.released_date releasedDate,rnd.accepted_date acceptedDate,rnd.ref_date doPartDate,rnd.status status,(select name from designation_master where id=emp.designation_id ) designationName from ltc_advance_request_details lcld,emp_master emp,reference_number_details rnd	where lcld.do_part_id =rnd.id and lcld.sfid=emp.sfid and rnd.ref_type='D'"
							+ "union all select 'LTC APPROVAL' type,rnd.id,emp.sfid sfID,emp.name_in_service_book empName,rnd.ref_number doPartNo,rnd.gaz_type gazType,Rnd.Type_Id TypeId,rnd.predopartno preDoPartNo,rnd.predopartdate preDoPartDate,rnd.released_date releasedDate,rnd.accepted_date acceptedDate,rnd.ref_date doPartDate,rnd.status status,(select name from designation_master where id=emp.designation_id ) designationName	from ltc_request_details lcld,emp_master emp,reference_number_details rnd	where lcld.do_part_id =rnd.id and lcld.sfid=emp.sfid )reference where reference.id=id ");
			// sb.append("select * from (select 'LEAVE' type,rnd.id id,emp.sfid sfID,emp.name_in_service_book empName,rnd.ref_number doPartNumber,rnd.gaz_type gazType,rnd.predopartno preDoPartNo,rnd.predopartdate preDoPartDate,rnd.ref_date doPartDate,(select name from designation_master where id=lrd.designation_id ) designationName from leave_request_details lrd,emp_master emp,reference_number_details rnd where lrd.do_part_id =rnd.id and lrd.sfid=emp.sfid and rnd.ref_type='D' union all select 'LEAVE CONVERSION' type,rnd.id,emp.sfid sfID,emp.name_in_service_book empName,rnd.ref_number doPartNo,rnd.gaz_type gazType,rnd.predopartno preDoPartNo,rnd.predopartdate preDoPartDate,rnd.ref_date doPartDate,(select name from designation_master where id=emp.designation_id) designationName from leave_conversion_details lcd,emp_master emp,reference_number_details rnd where lcd.do_part_id =rnd.id and lcd.sfid=emp.sfid and rnd.ref_type='D' union all select 'LEAVE CANCELLATION' type,rnd.id,emp.sfid sfID,emp.name_in_service_book empName,rnd.ref_number doPartNo,rnd.gaz_type gazType,rnd.predopartno preDoPartNo,rnd.predopartdate preDoPartDate,rnd.ref_date doPartDate,(select name from designation_master where id=emp.designation_id ) designationName from leave_cancellation_details lcld,emp_master emp,reference_number_details rnd where lcld.do_part_id =rnd.id and lcld.sfid=emp.sfid and rnd.ref_type='D' union all select 'LEAVE ADVANCE' type,rnd.id,emp.sfid sfID,emp.name_in_service_book empName,rnd.ref_number doPartNo,rnd.gaz_type gazType,rnd.predopartno preDoPartNo,rnd.predopartdate preDoPartDate,rnd.ref_date doPartDate,(select name from designation_master where id=emp.designation_id ) designationName from ltc_advance_request_details lcld,emp_master emp,reference_number_details rnd where lcld.do_part_id =rnd.id and lcld.sfid=emp.sfid and rnd.ref_type='D' union all select 'LTC' type,rnd.id,emp.sfid sfID,emp.name_in_service_book empName,rnd.ref_number doPartNo,rnd.gaz_type gazType,rnd.predopartno preDoPartNo,rnd.predopartdate preDoPartDate,rnd.ref_date doPartDate,(select name from designation_master where id=emp.designation_id ) designationName from ltc_request_details lcld,emp_master emp,reference_number_details rnd where lcld.do_part_id =rnd.id and lcld.sfid=emp.sfid)reference where reference.id=id");
			if (!CPSUtils.isNullOrEmpty(leaveBean.getDoPartDate())) {
				sb.append(" and  reference.doPartDate='" + leaveBean.getDoPartDate() + "'");
			}
			if (!CPSUtils.isNullOrEmpty(leaveBean.getSearchSfid())) {
				sb.append(" and  reference.sfID='" + leaveBean.getSearchSfid() + "'");
			}
			if (!CPSUtils.isNullOrEmpty(leaveBean.getDoPartNo())) {
				sb.append(" and reference.doPartNumber='" + leaveBean.getDoPartNo() + "'");
			}
			if (!CPSUtils.isNullOrEmpty(leaveBean.getGazettedType())) {
				sb.append(" and reference.TypeId='" + Integer.parseInt(leaveBean.getGazettedType()) + "'");
			}
			if((!CPSUtils.isNullOrEmpty(leaveBean.getYear())) && !leaveBean.getYear().equals("0")){
				sb.append("and to_char(reference.doPartDate,'yyyy') = '"+leaveBean.getYear()+"'");
			}
			sb.append(" group by id,doPartNumber,gazType,doPartDate,preDoPartNo,preDoPartDate,releasedDate,acceptedDate,status order by doPartDate DESC");
			leaveBean.setDoPartList(session.createSQLQuery(sb.toString()).addScalar("id", Hibernate.INTEGER).addScalar("doPartNumber", Hibernate.STRING).
					addScalar("doPartDate", Hibernate.DATE).addScalar("status",Hibernate.INTEGER).addScalar("gazType", Hibernate.STRING).addScalar("preDoPartNo", Hibernate.STRING).addScalar("preDoPartDate", Hibernate.DATE)
					.setResultTransformer(Transformers.aliasToBean(DoPartDTO.class)).list());
			leaveBean.setYearsList(session.createQuery("select yDTO.name as name from YearTypeDTO yDTO where yDTO.status=1").setResultTransformer(Transformers.aliasToBean(YearTypeDTO.class)).list());
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DoPartDTO> getPublishedDoParts() throws Exception {
		Session session = null;
		List<DoPartDTO> doPartList = null;
		try {
			session = hibernateUtils.getSession();
			doPartList = session.createCriteria(DoPartDTO.class).add(Expression.eq("status", 1)).add(Expression.eq("year", CPSUtils.getCurrentYear())).list();
		} catch (Exception e) {
			throw e;
		}
		return doPartList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LeaveTypeDTO> getLeaveTypeList() throws Exception {
		List<LeaveTypeDTO> leaveTypeList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			leaveTypeList = session.createCriteria(LeaveTypeDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(
					Restrictions.in("id", new Integer[] { Integer.valueOf(CPSConstants.EL), Integer.valueOf(CPSConstants.HPL), Integer.valueOf(CPSConstants.CL),
							Integer.valueOf(CPSConstants.EOL), Integer.valueOf(CPSConstants.CCL), Integer.valueOf(CPSConstants.SL), Integer.valueOf(CPSConstants.EOLWOMC) })).addOrder(
					Order.asc("orderNo")).list();
		} catch (Exception e) {
			throw e;
		}
		return leaveTypeList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LeaveAdministratorBean> getTxnSearchedLeaves(LeaveAdministratorBean leaveBean) throws Exception {
		Session session = null;
		List<LeaveAdministratorBean> leaveTxnList = null;
		try {
			session = hibernateUtils.getSession();
			StringBuffer sb = new StringBuffer();
			sb.append("(select emp.sfid searchSfid,emp.name_in_service_book empName,dm.name designation,ltm.leave_type leaveType,ltx.txn_from_date as sortdate,ltx.no_of_leaves noOfLeaves,to_char(ltx.txn_from_date ,'DD-Mon-YYYY') txnFromDate,to_char(ltx.txn_to_date ,'DD-Mon-YYYY') txnToDate," +
							" SM.STATUS status,'' doPartNo,'' requestID from leave_txn_details ltx,emp_master emp,designation_master dm,leave_type_master ltm,status_master SM "
							+ " where SM.ID=LTX.txn_type AND emp.designation_id=dm.id and ltx.sfid=emp.sfid and dm.status=1 and ltm.status=1 and ltx.leave_type_id=ltm.id");

			if (!CPSUtils.isNullOrEmpty(leaveBean.getSearchSfid())) {
				sb.append(" and upper(emp.sfid)='" + leaveBean.getSearchSfid() + "'");
			}
			if (!CPSUtils.compareStrings(leaveBean.getLeaveType(), CPSConstants.SELECT)) {
				sb.append(" and ltm.leave_type='" + leaveBean.getLeaveType() + "'");
			}
			/*if (!CPSUtils.isNullOrEmpty(leaveBean.getTxnDate())) {
				sb.append(" and to_date('" + leaveBean.getTxnDate() + "','DD-MON-YYYY') between ltx.txn_from_date and ltx.txn_to_date");
			}*/ // Added by Naresh
			if(!CPSUtils.isNullOrEmpty(leaveBean.getFromDate()) && !CPSUtils.isNullOrEmpty(leaveBean.getToDate())) {
				sb.append(" AND ((ltx.txn_from_date BETWEEN to_date('"+ leaveBean.getFromDate() +"','DD-MON-YYYY') and to_date('"+ leaveBean.getToDate() +"','DD-MON-YYYY'))"
						+ " OR (ltx.txn_to_date BETWEEN to_date('"+ leaveBean.getFromDate() +"','DD-MON-YYYY') and to_date('"+ leaveBean.getToDate() +"','DD-MON-YYYY'))"
						+ " OR (to_date('"+ leaveBean.getFromDate() +"', 'DD-MON-YYYY') BETWEEN ltx.txn_from_date AND ltx.txn_to_date)"
						+ " OR (to_date('"+ leaveBean.getToDate() +"','DD-MON-YYYY') BETWEEN ltx.txn_from_date AND ltx.txn_to_date))");
			}
			if(!CPSUtils.isNullOrEmpty(leaveBean.getTxnList()) && !CPSUtils.compareStrings("0", leaveBean.getTxnList())) {
				sb.append(" AND sm.id IN (");
				JSONObject json = (JSONObject) JSONSerializer.toJSON(leaveBean.getTxnList());
				Object[] obj = json.values().toArray();
				for(int i = 0; i < obj.length; i++) {
					if (i == obj.length - 1) sb.append(obj[i] + ")");
					else sb.append(obj[i] + ",");
				}
			}
			// kumari
			// For leave request details
			sb.append(") union (select emp.sfid searchSfid,emp.name_in_service_book empName, dm.name designation,LM.LEAVE_TYPE leaveType,lrd.from_date as sortdate," +
					" (-1*(lrd.debit_leaves+lrd.prev_holidays+lrd.next_holidays)) noOfLeaves,TO_CHAR(lrd.from_date,'DD-Mon-YYYY') txnFromDate,TO_CHAR(lrd.to_date,'DD-Mon-YYYY') txnToDate," +
					" sm.STATUS||'(Request)' status,(select case when lrd.do_part_id is null then 'none' else do.ref_number||':'||do.ref_date end as donumber from " +
					" reference_number_details do where do.id=lrd.do_part_id) doPartNo,to_char(LRD.REQUEST_ID) requestID from leave_request_details lrd,status_master sm, " +
					" emp_master emp,designation_master dm,leave_type_master lm where LM.STATUS=1 AND LM.ID=lrd.leave_type_id AND " +
					" emp.designation_id=dm.id AND lrd.sfid=emp.sfid AND dm.status=1 and lm.status=1 and sm.id=lrd.status");
			if (!CPSUtils.isNullOrEmpty(leaveBean.getSearchSfid())) {
				sb.append(" and upper(emp.sfid)='" + leaveBean.getSearchSfid() + "'");
			}
			if (!CPSUtils.compareStrings(leaveBean.getLeaveType(), CPSConstants.SELECT)) {
				sb.append(" AND lm.leave_type ='"+ leaveBean.getLeaveType() + "'");
			}
			/*if (!CPSUtils.isNullOrEmpty(leaveBean.getTxnDate())) {
				sb.append(" AND to_date('"+leaveBean.getTxnDate()+"','DD-MON-YYYY') between lrd.from_date and lrd.to_date");
			}*/
			if(!CPSUtils.isNullOrEmpty(leaveBean.getFromDate()) && !CPSUtils.isNullOrEmpty(leaveBean.getToDate())) {
				sb.append(" AND ((lrd.from_date BETWEEN to_date('"+ leaveBean.getFromDate() +"','DD-MON-YYYY') and to_date('"+ leaveBean.getToDate() +"','DD-MON-YYYY'))"
						+ " OR (lrd.to_date BETWEEN to_date('"+ leaveBean.getFromDate() +"','DD-MON-YYYY') and to_date('"+ leaveBean.getToDate() +"','DD-MON-YYYY'))"
						+ " OR (to_date('"+ leaveBean.getFromDate() +"', 'DD-MON-YYYY') BETWEEN lrd.from_date AND lrd.to_date)"
						+ " OR (to_date('"+ leaveBean.getToDate() +"','DD-MON-YYYY') BETWEEN lrd.from_date AND lrd.to_date))");
			}
			if(!CPSUtils.isNullOrEmpty(leaveBean.getTxnList()) && !CPSUtils.compareStrings("0", leaveBean.getTxnList())) {
				sb.append(" AND sm.id IN (");
				JSONObject json = (JSONObject) JSONSerializer.toJSON(leaveBean.getTxnList());
				Object[] obj = json.values().toArray();
				for(int i = 0; i < obj.length; i++) {
					if (i == obj.length - 1) sb.append(obj[i] + ")");
					else sb.append(obj[i] + ",");
				}
			}
			// For leave cacellation details
			sb.append(") union (SELECT emp.sfid searchSfid,emp.name_in_service_book empName,dm.name designation,LM.LEAVE_TYPE leaveType,lrd.from_date as sortdate," +
					" (-1*(lrd.debit_leaves+lrd.prev_holidays+lrd.next_holidays)) noOfLeaves,TO_CHAR(lrd.from_date,'DD-Mon-YYYY') txnFromDate," +
					" TO_CHAR(lrd.to_date,'DD-Mon-YYYY') txnToDate,sm.STATUS||'(Cancellation)' status,(SELECT CASE WHEN lcd.do_part_id IS NULL" +
					" THEN 'none' ELSE do.ref_number||':'||do.ref_date END AS donumber FROM reference_number_details DO WHERE" +
					" do.id=lcd.do_part_id) doPartNo,TO_CHAR(lcd.REQUEST_ID) requestID FROM leave_request_details lrd,status_master sm," +
					" emp_master emp,designation_master dm,leave_type_master lm,leave_cancellation_details lcd WHERE LM.STATUS=1 AND " +
					" LM.ID=lrd.leave_type_id AND emp.designation_id=dm.id and lrd.sfid=emp.sfid AND lcd.sfid=emp.sfid AND dm.status=1 AND " +
					" lm.status=1 AND sm.id=lcd.status AND lcd.reference_id=lrd.id");
			if (!CPSUtils.isNullOrEmpty(leaveBean.getSearchSfid())) {
				sb.append(" and upper(emp.sfid)='" + leaveBean.getSearchSfid() + "'");
			}
			if (!CPSUtils.compareStrings(leaveBean.getLeaveType(), CPSConstants.SELECT)) {
				sb.append(" AND lm.leave_type ='"+ leaveBean.getLeaveType() + "'");
			}
			/*if (!CPSUtils.isNullOrEmpty(leaveBean.getTxnDate())) {
				sb.append(" AND to_date('"+leaveBean.getTxnDate()+"','DD-MON-YYYY') between lrd.from_date and lrd.to_date");
			}*/
			if(!CPSUtils.isNullOrEmpty(leaveBean.getFromDate()) && !CPSUtils.isNullOrEmpty(leaveBean.getToDate())) {
				sb.append(" AND ((lrd.from_date BETWEEN to_date('"+ leaveBean.getFromDate() +"','DD-MON-YYYY') and to_date('"+ leaveBean.getToDate() +"','DD-MON-YYYY'))"
						+ " OR (lrd.to_date BETWEEN to_date('"+ leaveBean.getFromDate() +"','DD-MON-YYYY') and to_date('"+ leaveBean.getToDate() +"','DD-MON-YYYY'))"
						+ " OR (to_date('"+ leaveBean.getFromDate() +"', 'DD-MON-YYYY') BETWEEN lrd.from_date AND lrd.to_date)"
						+ " OR (to_date('"+ leaveBean.getToDate() +"','DD-MON-YYYY') BETWEEN lrd.from_date AND lrd.to_date))");
			}
			if(!CPSUtils.isNullOrEmpty(leaveBean.getTxnList()) && !CPSUtils.compareStrings("0", leaveBean.getTxnList())) {
				sb.append(" AND sm.id IN (");
				JSONObject json = (JSONObject) JSONSerializer.toJSON(leaveBean.getTxnList());
				Object[] obj = json.values().toArray();
				for(int i = 0; i < obj.length; i++) {
					if(i == obj.length - 1) sb.append(obj[i] + ")");
					else sb.append(obj[i] + ",");
				}
			}
			// For leave conversion details
			sb.append(") union (SELECT emp.sfid searchSfid,emp.name_in_service_book empName,dm.name designation,LM.LEAVE_TYPE leaveType,lrd.from_date as sortdate," +
					" (-1*(lrd.debit_leaves+lrd.prev_holidays+lrd.next_holidays)) noOfLeaves,TO_CHAR(lrd.from_date,'DD-Mon-YYYY') txnFromDate," +
					" TO_CHAR(lrd.to_date,'DD-Mon-YYYY') txnToDate,sm.STATUS||'(Conversion)' status,(SELECT CASE WHEN lcon.do_part_id IS NULL " +
					" THEN 'none' ELSE do.ref_number||':'||do.ref_date END AS donumber FROM reference_number_details DO WHERE do.id=lcon.do_part_id)" +
					" doPartNo,TO_CHAR(lcon.REQUEST_ID) requestID FROM leave_request_details lrd,status_master sm,emp_master emp," +
					" designation_master dm,leave_type_master lm,leave_conversion_details lcon WHERE LM.STATUS=1 AND LM.ID=lcon.converted_to" +
					" AND emp.designation_id=dm.id and lrd.sfid=emp.sfid AND lcon.sfid=emp.sfid AND dm.status=1 AND lm.status=1 AND sm.id=lcon.status " +
					" AND lcon.reference_id=lrd.id");
			if (!CPSUtils.isNullOrEmpty(leaveBean.getSearchSfid())) {
				sb.append(" and upper(emp.sfid)='" + leaveBean.getSearchSfid() + "'");
			}
			if (!CPSUtils.compareStrings(leaveBean.getLeaveType(), CPSConstants.SELECT)) {
				sb.append(" AND lm.leave_type ='"+ leaveBean.getLeaveType() + "'");
			}
			/*if (!CPSUtils.isNullOrEmpty(leaveBean.getTxnDate())) {
				sb.append(" AND to_date('"+leaveBean.getTxnDate()+"','DD-MON-YYYY') between lrd.from_date and lrd.to_date");
			}*/
			if(!CPSUtils.isNullOrEmpty(leaveBean.getFromDate()) && !CPSUtils.isNullOrEmpty(leaveBean.getToDate())) {
				sb.append(" AND ((lrd.from_date BETWEEN to_date('"+ leaveBean.getFromDate() +"','DD-MON-YYYY') and to_date('"+ leaveBean.getToDate() +"','DD-MON-YYYY'))"
						+ " OR (lrd.to_date BETWEEN to_date('"+ leaveBean.getFromDate() +"','DD-MON-YYYY') and to_date('"+ leaveBean.getToDate() +"','DD-MON-YYYY'))"
						+ " OR (to_date('"+ leaveBean.getFromDate() +"', 'DD-MON-YYYY') BETWEEN lrd.from_date AND lrd.to_date)"
						+ " OR (to_date('"+ leaveBean.getToDate() +"','DD-MON-YYYY') BETWEEN lrd.from_date AND lrd.to_date))");
			}
			if(!CPSUtils.isNullOrEmpty(leaveBean.getTxnList()) && !CPSUtils.compareStrings("0", leaveBean.getTxnList())) {
				sb.append(" AND sm.id IN (");
				JSONObject json = (JSONObject) JSONSerializer.toJSON(leaveBean.getTxnList());
				Object[] obj = json.values().toArray();
				for(int i = 0; i < obj.length; i++) {
					if(i == obj.length - 1) sb.append(obj[i] + ")");
					else sb.append(obj[i] + ",");
				}
			}
			sb.append(")ORDER BY sortdate DESC");
					
			leaveTxnList = session.createSQLQuery(sb.toString()).addScalar("searchSfid").addScalar("empName").addScalar("designation").addScalar("leaveType")
					.addScalar("noOfLeaves", Hibernate.INTEGER).addScalar("txnFromDate", Hibernate.STRING).addScalar("txnToDate", Hibernate.STRING)
					.addScalar("status", Hibernate.STRING).addScalar("doPartNo", Hibernate.STRING).addScalar("requestID", Hibernate.STRING)
					.setResultTransformer(Transformers.aliasToBean(LeaveAdministratorBean.class)).list();
			
			//reqDetailsList
			
		} catch (Exception e) {
			throw e;
		}
		return leaveTxnList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<EmploymentDTO> getLeaveExpList(LeaveAdministratorBean leaveBean) throws Exception {
		Session session = null;
		List<EmploymentDTO> empList = null;
		try {
			session = hibernateUtils.getSession();
			empList = session.createSQLQuery("select emp.sfid sfid, emp.sfid ||' - '|| emp.name_in_service_book ename from emp_master emp, "
					+ "leave_exceptional_employees lee where emp.sfid = lee.sfid and emp.status = 1 and lee.status = 1 order by emp.sfid").addScalar("sfid", Hibernate.STRING)
					.addScalar("ename", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(EmploymentDTO.class)).list();
		} catch (Exception e) {
			throw e;
		}
		return empList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<EmploymentDTO> getEmployeesList(LeaveAdministratorBean leaveBean) throws Exception {
		Session session = null;
		List<EmploymentDTO> empList = null;
		try {
			session = hibernateUtils.getSession();
			empList = session.createSQLQuery("select emp.sfid sfid, emp.sfid ||' - '|| emp.name_in_service_book ename from emp_master emp "
					+ "where emp.status = 1 and emp.flag is null and emp.sfid not in (select sfid from leave_exceptional_employees where status = 1) order by emp.sfid")
					.addScalar("sfid", Hibernate.STRING).addScalar("ename", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(EmploymentDTO.class)).list();
		} catch (Exception e) {
			throw e;
		}
		return empList;
	}

	@Override
	public String submitLeaveExceptionalEmp(LeaveAdministratorBean leaveBean) throws Exception {
		Session session = null;
		String message = null;
		LeaveExceptionalEmployees leaveEmp = null;
		try {
			session = hibernateUtils.getSession();

			session.createSQLQuery("update leave_exceptional_employees set status = 0, last_modified_by = ?, last_modified_date = sysdate where status = 1").setString(0, leaveBean.getSfID()).executeUpdate();

			if (!CPSUtils.isNullOrEmpty(leaveBean.getSelectedLinks())) {
				String currentDate = CPSUtils.getCurrentDate();
				String[] roleLinks = (leaveBean.getSelectedLinks().substring(0, leaveBean.getSelectedLinks().length() - 1)).split(",");

				for (String sfid : roleLinks) {
					leaveEmp = new LeaveExceptionalEmployees();
					leaveEmp.setSfID(sfid);
					leaveEmp.setStatus(1);
					leaveEmp.setCreatedBy(leaveBean.getSfID());
					leaveEmp.setCreationDate(currentDate);
					leaveEmp.setLastModifiedBy(leaveBean.getSfID());
					leaveEmp.setLastModifiedDate(currentDate);
					session.saveOrUpdate(leaveEmp);
				}
			}
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	@Override
	public String saveLeaveAdit(LeaveAdministratorBean leaveBean) throws Exception {
		Session session = null;
		LeaveTxnDTO leaveTxnDTO = null;
		try {
			session = hibernateUtils.getSession();

			leaveTxnDTO = new LeaveTxnDTO();

			leaveTxnDTO.setSfID(leaveBean.getSearchSfid());
			leaveTxnDTO.setStatus(1);
			leaveTxnDTO.setCreatedBy(leaveBean.getSfID());
			leaveTxnDTO.setCreationDate(CPSUtils.getCurrentDateWithTime());
			leaveTxnDTO.setTxnToDate(leaveBean.getTxnDate());
			leaveTxnDTO.setTxnDate(leaveBean.getTxnDate());
			leaveTxnDTO.setTxnType(leaveBean.getTxnType());
			leaveTxnDTO.setRemarks(leaveBean.getRemarks());
			leaveTxnDTO.setNoOfLeaves(Float.parseFloat(leaveBean.getNoOfDays()));
			leaveTxnDTO.setLeaveTypeDetails((LeaveTypeDTO) session.get(LeaveTypeDTO.class, Integer.valueOf(leaveBean.getLeaveType())));

			session.saveOrUpdate(leaveTxnDTO);
			leaveBean.setMessage(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		}
		return leaveBean.getMessage();
	}

	@Override
	public String updateLeaveCredit(LeaveAdministratorBean leaveBean) throws Exception {
		Session session = null;
		AvailableLeavesDTO availableDTO = null;
		try {
			session = hibernateUtils.getSession();
			// If the leave type is EOL, then we should debit from EL

			if (CPSUtils.compareStrings(leaveBean.getLeaveType(), CPSConstants.EOL) || CPSUtils.compareStrings(leaveBean.getLeaveType(), CPSConstants.EOLWOMC)) {
				availableDTO = (AvailableLeavesDTO) session.createCriteria(AvailableLeavesDTO.class).add(Expression.eq("leaveTypeID", Integer.valueOf(CPSConstants.EL))).add(
						Expression.eq("sfID", leaveBean.getSearchSfid())).uniqueResult();

			} else {
				availableDTO = (AvailableLeavesDTO) session.createCriteria(AvailableLeavesDTO.class).add(Expression.eq("leaveTypeID", Integer.valueOf(leaveBean.getLeaveType()))).add(
						Expression.eq("sfID", leaveBean.getSearchSfid())).uniqueResult();
			}
			if (CPSUtils.isNull(availableDTO)) {
				availableDTO = new AvailableLeavesDTO();
				availableDTO.setSfID(leaveBean.getSearchSfid());
				availableDTO.setLeaveTypeDetails((LeaveTypeDTO) session.get(LeaveTypeDTO.class, Integer.valueOf(leaveBean.getLeaveType())));
			}
			if (CPSUtils.compareStrings(leaveBean.getLeaveType(), CPSConstants.EOL) || CPSUtils.compareStrings(leaveBean.getLeaveType(), CPSConstants.EOLWOMC)) {
				if (!CPSUtils.isNullOrEmpty(availableDTO) && availableDTO.getAvailableLeaves() > Float.parseFloat(leaveBean.getNoOfDays()) / 10) {

					availableDTO.setAvailableLeaves(CPSUtils.round(availableDTO.getAvailableLeaves() - Float.parseFloat(leaveBean.getNoOfDays()) / 10));
				} else {
					// EL leaves are very less, so add the leaves to EOL
					// Get the eol leaves & add the leaves to EOL
					availableDTO = (AvailableLeavesDTO) session.createCriteria(AvailableLeavesDTO.class).add(Expression.eq("leaveTypeID", Integer.valueOf(leaveBean.getLeaveType()))).add(
							Expression.eq("sfID", leaveBean.getSearchSfid())).uniqueResult();

					if (CPSUtils.isNull(availableDTO)) {
						availableDTO = new AvailableLeavesDTO();
						availableDTO.setSfID(leaveBean.getSearchSfid());
						availableDTO.setLeaveTypeDetails((LeaveTypeDTO) session.get(LeaveTypeDTO.class, Integer.valueOf(leaveBean.getLeaveType())));
					}
					availableDTO.setAvailableLeaves(CPSUtils.round(availableDTO.getAvailableLeaves() + Float.parseFloat(leaveBean.getNoOfDays())));
				}
			} else {
				availableDTO.setAvailableLeaves(availableDTO.getAvailableLeaves() + Float.parseFloat(leaveBean.getNoOfDays()));
			}

			session.saveOrUpdate(availableDTO);

			if (CPSUtils.compareStrings(leaveBean.getLeaveType(), CPSConstants.LND)) {
				// We should also debit the leaves from HPL
				availableDTO = (AvailableLeavesDTO) session.createCriteria(AvailableLeavesDTO.class).add(Expression.eq("leaveTypeID", Integer.valueOf(CPSConstants.HPL))).add(
						Expression.eq("sfID", leaveBean.getSearchSfid())).uniqueResult();
				availableDTO.setAvailableLeaves(CPSUtils.round(availableDTO.getAvailableLeaves() + Float.parseFloat(leaveBean.getNoOfDays())));
				session.saveOrUpdate(availableDTO);
			}

			leaveBean.setMessage(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		}
		return leaveBean.getMessage();
	}

	@Override
	@SuppressWarnings("unchecked")
	public void copyLeaves() throws Exception {
		Session session = null;
		List<LeaveManualEntryDTO> leaveList = null;
		try {
			session = hibernateUtils.getSession();
			leaveList = session.createCriteria(LeaveManualEntryDTO.class).add(Expression.isNotNull("verifiedBy")).list();
			for (LeaveManualEntryDTO leavesDTO : leaveList) {
				leavesDTO.setDescription("DataEntry");
				insertIntoLeaveTxnDetails(leavesDTO);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public void insertIntoLeaveTxnDetails(LeaveManualEntryDTO leavesDTO) throws Exception {
		Session session = null;
		LeaveTxnDTO leaveTxnDTO = null;
		String lastModifiedDate = null;
		Date creationDate = null;
		try {
			session = hibernateUtils.getSession();
			if (CPSUtils.compareStrings(leavesDTO.getDescription(), "DataEntry")) {
				lastModifiedDate = CPSUtils.formattedDate(leavesDTO.getLastModifiedDate());
				creationDate = CPSUtils.convertStringToDate(CPSUtils.formattedDate(leavesDTO.getCreationDate()));
			} else {
				lastModifiedDate = leavesDTO.getLastModifiedDate();
				creationDate = CPSUtils.convertStringToDate(CPSUtils.formattedDate(leavesDTO.getCreationDate()));
			}

			if (!CPSUtils.isNullOrEmpty(leavesDTO.getEl())) {
				// check whether already added or not
				if (checkTxnDetails(leavesDTO.getSfID(), CPSConstants.EL)) {
					leaveTxnDTO = new LeaveTxnDTO();
					leaveTxnDTO.setSfID(leavesDTO.getSfID());
					leaveTxnDTO.setTxnDate(lastModifiedDate);
					leaveTxnDTO.setTxnToDate(lastModifiedDate);
					leaveTxnDTO.setCreatedBy(leavesDTO.getCreatedBy());
					leaveTxnDTO.setCreationDate(creationDate);
					leaveTxnDTO.setTxnType(CPSConstants.LEAVEMANUALENTRYSTATUSID);
					leaveTxnDTO.setStatus(1);
					leaveTxnDTO.setOneTimeEntryFlag(1);
					leaveTxnDTO.setLeaveTypeDetails((LeaveTypeDTO) session.get(LeaveTypeDTO.class, Integer.valueOf(CPSConstants.EL)));
					leaveTxnDTO.setNoOfLeaves(Float.valueOf(leavesDTO.getEl()));
					session.save(leaveTxnDTO);
				}
			}
			if (!CPSUtils.isNullOrEmpty(leavesDTO.getHpl())) {
				// check whether already added or not
				if (checkTxnDetails(leavesDTO.getSfID(), CPSConstants.HPL)) {
					leaveTxnDTO = new LeaveTxnDTO();
					leaveTxnDTO.setSfID(leavesDTO.getSfID());
					leaveTxnDTO.setTxnDate(lastModifiedDate);
					leaveTxnDTO.setTxnToDate(lastModifiedDate);
					leaveTxnDTO.setCreatedBy(leavesDTO.getCreatedBy());
					leaveTxnDTO.setCreationDate(creationDate);
					leaveTxnDTO.setTxnType(CPSConstants.LEAVEMANUALENTRYSTATUSID);
					leaveTxnDTO.setStatus(1);
					leaveTxnDTO.setOneTimeEntryFlag(1);
					leaveTxnDTO.setLeaveTypeDetails((LeaveTypeDTO) session.get(LeaveTypeDTO.class, Integer.valueOf(CPSConstants.HPL)));
					leaveTxnDTO.setNoOfLeaves(Float.valueOf(leavesDTO.getHpl()));
					session.save(leaveTxnDTO);
				}
			}
			if (!CPSUtils.isNullOrEmpty(leavesDTO.getCl())) {
				// check whether already added or not
				if (checkTxnDetails(leavesDTO.getSfID(), CPSConstants.CL)) {
					leaveTxnDTO = new LeaveTxnDTO();
					leaveTxnDTO.setSfID(leavesDTO.getSfID());
					leaveTxnDTO.setTxnDate(lastModifiedDate);
					leaveTxnDTO.setTxnToDate(lastModifiedDate);
					leaveTxnDTO.setCreatedBy(leavesDTO.getCreatedBy());
					leaveTxnDTO.setCreationDate(creationDate);
					leaveTxnDTO.setTxnType(CPSConstants.LEAVEMANUALENTRYSTATUSID);
					leaveTxnDTO.setStatus(1);
					leaveTxnDTO.setOneTimeEntryFlag(1);
					leaveTxnDTO.setLeaveTypeDetails((LeaveTypeDTO) session.get(LeaveTypeDTO.class, Integer.valueOf(CPSConstants.CL)));
					leaveTxnDTO.setNoOfLeaves(Float.valueOf(leavesDTO.getCl()));
					session.save(leaveTxnDTO);
				}
			}
			if (!CPSUtils.isNullOrEmpty(leavesDTO.getLnd())) {
				// check whether already added or not
				if (checkTxnDetails(leavesDTO.getSfID(), CPSConstants.LND)) {
					leaveTxnDTO = new LeaveTxnDTO();
					leaveTxnDTO.setSfID(leavesDTO.getSfID());
					leaveTxnDTO.setTxnDate(lastModifiedDate);
					leaveTxnDTO.setTxnToDate(lastModifiedDate);
					leaveTxnDTO.setCreatedBy(leavesDTO.getCreatedBy());
					leaveTxnDTO.setCreationDate(creationDate);
					leaveTxnDTO.setTxnType(CPSConstants.LEAVEMANUALENTRYSTATUSID);
					leaveTxnDTO.setStatus(1);
					leaveTxnDTO.setOneTimeEntryFlag(1);
					leaveTxnDTO.setLeaveTypeDetails((LeaveTypeDTO) session.get(LeaveTypeDTO.class, Integer.valueOf(CPSConstants.LND)));
					leaveTxnDTO.setNoOfLeaves(Float.valueOf(leavesDTO.getLnd()));
					session.save(leaveTxnDTO);
				}
			}
			if (!CPSUtils.isNullOrEmpty(leavesDTO.getCcl())) {
				// check whether already added or not
				if (checkTxnDetails(leavesDTO.getSfID(), CPSConstants.CCL)) {
					leaveTxnDTO = new LeaveTxnDTO();
					leaveTxnDTO.setSfID(leavesDTO.getSfID());
					leaveTxnDTO.setTxnDate(lastModifiedDate);
					leaveTxnDTO.setTxnToDate(lastModifiedDate);
					leaveTxnDTO.setCreatedBy(leavesDTO.getCreatedBy());
					leaveTxnDTO.setCreationDate(creationDate);
					leaveTxnDTO.setTxnType(CPSConstants.LEAVEMANUALENTRYSTATUSID);
					leaveTxnDTO.setStatus(1);
					leaveTxnDTO.setOneTimeEntryFlag(1);
					leaveTxnDTO.setLeaveTypeDetails((LeaveTypeDTO) session.get(LeaveTypeDTO.class, Integer.valueOf(CPSConstants.CCL)));
					leaveTxnDTO.setNoOfLeaves(Float.valueOf(leavesDTO.getCcl()));
					session.save(leaveTxnDTO);
				}
			}
			if (!CPSUtils.isNullOrEmpty(leavesDTO.getStl())) {
				// check whether already added or not
				if (checkTxnDetails(leavesDTO.getSfID(), CPSConstants.SL)) {
					leaveTxnDTO = new LeaveTxnDTO();
					leaveTxnDTO.setSfID(leavesDTO.getSfID());
					leaveTxnDTO.setTxnDate(lastModifiedDate);
					leaveTxnDTO.setTxnToDate(lastModifiedDate);
					leaveTxnDTO.setCreatedBy(leavesDTO.getCreatedBy());
					leaveTxnDTO.setCreationDate(creationDate);
					leaveTxnDTO.setTxnType(CPSConstants.LEAVEMANUALENTRYSTATUSID);
					leaveTxnDTO.setStatus(1);
					leaveTxnDTO.setOneTimeEntryFlag(1);
					leaveTxnDTO.setLeaveTypeDetails((LeaveTypeDTO) session.get(LeaveTypeDTO.class, Integer.valueOf(CPSConstants.SL)));
					leaveTxnDTO.setNoOfLeaves(Float.valueOf(leavesDTO.getStl()));
					session.save(leaveTxnDTO);
				}
			}
			if (!CPSUtils.isNullOrEmpty(leavesDTO.getEol())) {
				// check whether already added or not
				if (checkTxnDetails(leavesDTO.getSfID(), CPSConstants.EOL)) {
					leaveTxnDTO = new LeaveTxnDTO();
					leaveTxnDTO.setSfID(leavesDTO.getSfID());
					leaveTxnDTO.setTxnDate(lastModifiedDate);
					leaveTxnDTO.setTxnToDate(lastModifiedDate);
					leaveTxnDTO.setCreatedBy(leavesDTO.getCreatedBy());
					leaveTxnDTO.setCreationDate(creationDate);
					leaveTxnDTO.setTxnType(CPSConstants.LEAVEMANUALENTRYSTATUSID);
					leaveTxnDTO.setStatus(1);
					leaveTxnDTO.setOneTimeEntryFlag(1);
					leaveTxnDTO.setLeaveTypeDetails((LeaveTypeDTO) session.get(LeaveTypeDTO.class, Integer.valueOf(CPSConstants.EOL)));
					leaveTxnDTO.setNoOfLeaves(Float.valueOf(leavesDTO.getEol()));
					session.save(leaveTxnDTO);
				}
			}
			if (!CPSUtils.isNullOrEmpty(leavesDTO.getEolwomc())) {
				// check whether already added or not
				if (checkTxnDetails(leavesDTO.getSfID(), CPSConstants.EOLWOMC)) {
					leaveTxnDTO = new LeaveTxnDTO();
					leaveTxnDTO.setSfID(leavesDTO.getSfID());
					leaveTxnDTO.setTxnDate(lastModifiedDate);
					leaveTxnDTO.setTxnToDate(lastModifiedDate);
					leaveTxnDTO.setCreatedBy(leavesDTO.getCreatedBy());
					leaveTxnDTO.setCreationDate(creationDate);
					leaveTxnDTO.setTxnType(CPSConstants.LEAVEMANUALENTRYSTATUSID);
					leaveTxnDTO.setStatus(1);
					leaveTxnDTO.setOneTimeEntryFlag(1);
					leaveTxnDTO.setLeaveTypeDetails((LeaveTypeDTO) session.get(LeaveTypeDTO.class, Integer.valueOf(CPSConstants.EOLWOMC)));
					leaveTxnDTO.setNoOfLeaves(Float.valueOf(leavesDTO.getEolwomc()));
					session.save(leaveTxnDTO);
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public boolean checkTxnDetails(final String sfid, final String leaveType) throws Exception {
		Session session = null;
		boolean status = true;
		try {
			session = hibernateUtils.getSession();
			if (Integer.valueOf(session.createSQLQuery("select count(*) from leave_txn_details where sfid = ? and status = 1 and leave_type_id = ? "
					+ "and one_time_entry_flag = 1").setString(0, sfid).setString(1, leaveType).uniqueResult().toString()) > 0) {
				status = false;
			}
		} catch (Exception e) {
			throw e;
		}
		return status;
	}

	@Override
	public float getAvailableLeavesList(LeaveAdministratorBean leaveBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			AvailableLeavesDTO availableLeavesDTO = (AvailableLeavesDTO) session.createCriteria(AvailableLeavesDTO.class).add(Expression.eq("sfID", leaveBean.getSearchSfid())).add(
					Expression.eq("leaveTypeID", Integer.valueOf(leaveBean.getLeaveType()))).uniqueResult();
			if (CPSUtils.isNull(availableLeavesDTO)) {
				leaveBean.setBalanceLeaves(0);
			} else {
				leaveBean.setBalanceLeaves(availableLeavesDTO.getAvailableLeaves());
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveBean.getBalanceLeaves();
	}

	@Override
	@SuppressWarnings("unchecked")
	public void backup2011() throws Exception {
		Session session = null;
		List<AvailableLeavesDTO> list = null;
		try {
			session = hibernateUtils.getSession();

			// insert previous balance from backup_leave_balance to leave_yearly_balance
			list = session.createSQLQuery("select sfid sfID,leave_type_id leaveTypeID,available_leaves availableLeaves from backup_leave_balance").addScalar("sfID", Hibernate.STRING).addScalar(
					"leaveTypeID", Hibernate.INTEGER).addScalar("availableLeaves", Hibernate.FLOAT).setResultTransformer(Transformers.aliasToBean(AvailableLeavesDTO.class)).list();

			for (AvailableLeavesDTO leave : list) {
				LeaveYearlyBalanceDTO leaveYearlyBalanceDTO = (LeaveYearlyBalanceDTO) session.createCriteria(LeaveYearlyBalanceDTO.class).add(Expression.eq("sfID", leave.getSfID())).add(
						Expression.eq("yearID", 63)).add(Expression.eq("leaveTypeID", leave.getLeaveTypeID())).add(Expression.eq("status", 1)).add(Expression.eq(CPSConstants.TYPE, 0)).uniqueResult();

				if (CPSUtils.isNull(leaveYearlyBalanceDTO)) {
					leaveYearlyBalanceDTO = new LeaveYearlyBalanceDTO();
					leaveYearlyBalanceDTO.setYearID(63);
					leaveYearlyBalanceDTO.setStatus(1);
					leaveYearlyBalanceDTO.setSfID(leave.getSfID());
					leaveYearlyBalanceDTO.setBalance(leave.getAvailableLeaves());
					leaveYearlyBalanceDTO.setLeaveTypeID(leave.getLeaveTypeID());
					leaveYearlyBalanceDTO.setType(0);

					session.save(leaveYearlyBalanceDTO);
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void updateLeaveAccountBalance() throws Exception {
		Session session = null;
		String sfid = "";
		int eleven = 0;
		int thirtyfive = 0;
		try {
			session = hibernateUtils.getSession();

			// First update the status=0 that were inserted in 2012 year balance
			session
					.createSQLQuery(
							"update leave_account set status=0 where id in (select id from leave_account where to_char(creation_date,'YYYY')=2012 and status=1 and (eleven is not null or thirtyfive is not null))")
					.executeUpdate();

			List<KeyValueDTO> elList = session.createSQLQuery("select id,eleven value,sfid key from leave_account where eleven is not null and status=1").addScalar("id", Hibernate.INTEGER).addScalar(
					"value", Hibernate.STRING).addScalar("key", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();

			for (KeyValueDTO key : elList) {
				session.createSQLQuery("update leave_account set eleven=? where status=1 and sfid=?").setString(0, key.getValue()).setString(1, key.getKey()).executeUpdate();
			}

			List<KeyValueDTO> hplList = session.createSQLQuery("select id,thirtyfive value,sfid key from leave_account where thirtyfive is not null and status=1").addScalar("id", Hibernate.INTEGER)
					.addScalar("value", Hibernate.STRING).addScalar("key", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();

			for (KeyValueDTO key : hplList) {
				session.createSQLQuery("update leave_account set thirtyfive=? where status=1 and sfid=?").setString(0, key.getValue()).setString(1, key.getKey()).executeUpdate();
			}

			List<LeaveAccountDTO> leaveaccountlist = session
					.createSQLQuery(
							"select id,request_id requestID,sfid sfID,one,two,three,four,five,six,seven,eight,nine,ten,eleven,twelve,thirteen,fourteen,fifteen,sixteen,seventeen,eighteen,nineteen,twenty,twentyone,twentytwo,twentythree,twentyfour,"
									+ "twentyfive,status,twentysix,twentyseven,twentyeight,twentynine,thirty,thirtyone,thirtytwo,thirtythree,thirtyfour,thirtyfive,thirtysix,creation_date creationDate,last_modified_date lastModifiedDate from leave_account where status=1 "
									+ "group by id,request_id,sfid,one,two,three,four,five,six,seven,eight,nine,ten,eleven,twelve,thirteen,fourteen,fifteen,sixteen,seventeen,eighteen,nineteen,status,twenty,twentyone,twentytwo,twentythree,twentyfour,twentyfive,twentysix,twentyseven,twentyeight,twentynine,thirty,thirtyone,thirtytwo,"
									+ "thirtythree,thirtyfour,thirtyfive,thirtysix,creation_date,last_modified_date order by sfid,creation_date").addScalar("id", Hibernate.INTEGER).addScalar(
							"requestID", Hibernate.STRING).addScalar("sfID", Hibernate.STRING).addScalar("one", Hibernate.STRING).addScalar("two", Hibernate.STRING).addScalar("three",
							Hibernate.STRING).addScalar("four", Hibernate.STRING).addScalar("five", Hibernate.STRING).addScalar("six", Hibernate.STRING).addScalar("seven", Hibernate.STRING)
					.addScalar("eight", Hibernate.STRING).addScalar("nine", Hibernate.STRING).addScalar("ten", Hibernate.STRING).addScalar("eleven", Hibernate.STRING).addScalar("twelve",
							Hibernate.STRING).addScalar("thirteen", Hibernate.STRING).addScalar("fourteen", Hibernate.STRING).addScalar("fifteen", Hibernate.STRING).addScalar("sixteen",
							Hibernate.STRING).addScalar("seventeen", Hibernate.STRING).addScalar("status", Hibernate.STRING).addScalar("nineteen", Hibernate.STRING).addScalar("twenty",
							Hibernate.STRING).addScalar("twentyone", Hibernate.STRING).addScalar("twentytwo", Hibernate.STRING).addScalar("twentythree", Hibernate.STRING).addScalar("twentyfour",
							Hibernate.STRING).addScalar("twentyfive", Hibernate.STRING).addScalar("twentysix", Hibernate.STRING).addScalar("twentyseven", Hibernate.STRING).addScalar("twentyeight",
							Hibernate.STRING).addScalar("twentynine", Hibernate.STRING).addScalar("thirty", Hibernate.STRING).addScalar("thirtyone", Hibernate.STRING).addScalar("thirtytwo",
							Hibernate.STRING).addScalar("thirtythree", Hibernate.STRING).addScalar("thirtyfour", Hibernate.STRING).addScalar("thirtyfive", Hibernate.STRING).addScalar("thirtysix",
							Hibernate.STRING).addScalar("creationDate", Hibernate.DATE).addScalar("lastModifiedDate", Hibernate.DATE).setResultTransformer(
							Transformers.aliasToBean(LeaveAccountDTO.class)).list();

			for (LeaveAccountDTO account : leaveaccountlist) {
				if (CPSUtils.isNullOrEmpty(sfid) || !CPSUtils.compareStrings(sfid, account.getSfID())) {
					sfid = account.getSfID();
					if (CPSUtils.isNullOrEmpty(account.getEleven())) {
						eleven = 0;
					} else {
						eleven = Integer.valueOf(account.getEleven());
					}
					if (CPSUtils.isNullOrEmpty(account.getThirtyfive())) {
						thirtyfive = 0;
					} else {
						thirtyfive = Integer.valueOf(account.getThirtyfive());
					}
				} else {
					// eleven
					if (!CPSUtils.isNullOrEmpty(account.getFour())) {
						eleven = eleven + Math.round(Float.valueOf(account.getFour()));
					}
					if (!CPSUtils.isNullOrEmpty(account.getTen())) {
						eleven = eleven - Integer.valueOf(account.getTen());
					}
					if (!CPSUtils.isNullOrEmpty(account.getSix())) {
						eleven = eleven - Math.round(Float.valueOf(account.getSix()));
					}
					// thiryfive
					if (!CPSUtils.isNullOrEmpty(account.getThirteen())) {
						thirtyfive = thirtyfive + Integer.valueOf(account.getThirteen());
					}
					if (!CPSUtils.isNullOrEmpty(account.getNineteen())) {
						thirtyfive = thirtyfive - Integer.valueOf(account.getNineteen());
					}
					if (!CPSUtils.isNullOrEmpty(account.getTwentytwo())) {
						thirtyfive = thirtyfive - (2 * Integer.valueOf(account.getTwentytwo()));
					}
					if (!CPSUtils.isNullOrEmpty(account.getTwentyfour())) {
						thirtyfive = thirtyfive - (2 * Integer.valueOf(account.getTwentyfour()));
					}
					if (!CPSUtils.isNullOrEmpty(account.getTwentynine())) {
						thirtyfive = thirtyfive - Integer.valueOf(account.getTwentynine());
					}
					if (!CPSUtils.isNullOrEmpty(account.getThirtytwo())) {
						thirtyfive = thirtyfive - Integer.valueOf(account.getThirtytwo());
					}
					// save
					account.setEleven(String.valueOf(eleven));
					account.setThirtyfive(String.valueOf(thirtyfive));
					session.saveOrUpdate(account);
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public String saveTypeDetails(TypeDetailsDTO detailsDTO) throws Exception {
		Session session = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();
			session.saveOrUpdate(detailsDTO);
			hibernateUtils.commitTransaction();
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return message;
		
	}

	@Override
	public String checkDupliateType(LeaveAdministratorBean leaveBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			Criteria crit = session.createCriteria(TypeDetailsDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(
					Expression.eq(CPSConstants.NAME, leaveBean.getName()));
			if (!CPSUtils.isNullOrEmpty(leaveBean.getNodeID())) {
				crit = crit.add(Expression.ne(CPSConstants.ID, Integer.parseInt(leaveBean.getNodeID())));
			}
			if (CPSUtils.checkList(crit.list())) {
				leaveBean.setResult(CPSConstants.DUPLICATE);
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveBean.getResult();

	}

	@Override
	public String deleteTypeDetails(LeaveAdministratorBean leaveBean) throws Exception {
		Session session = null;
		try{
		session = hibernateUtils.getSession();
		TypeDetailsDTO detailsDTO = (TypeDetailsDTO) session.get(TypeDetailsDTO.class, Integer.valueOf(leaveBean.getNodeID()));
		detailsDTO.setStatus(0);
		session.saveOrUpdate(detailsDTO);
		hibernateUtils.commitTransaction();
		leaveBean.setResult(CPSConstants.DELETE);
		}catch(Exception e){
		throw e;	
		}
		return leaveBean.getResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CasualityDetailsDTO> getCasualitiesList(LeaveAdministratorBean leaveBean) throws Exception {
		Session session=null;
		try{
		session=hibernateUtils.getSession();
		/*
			select cm.id,cm.module_id,cm.casuality,cm.description,cm.status,cm.order_by,cm.code,cm.type_id,tm.name,mm.name 
			from dopart_ii_casualities_master cm,dopart_ii_type_master tm,ess_module_master mm 
			where cm.type_id=tm.id and cm.module_id=mm.id and cm.status=1
		
		*/
		if (!CPSUtils.isNullOrEmpty(leaveBean.getTypeId()) && !CPSUtils.isNullOrEmpty(leaveBean.getModuleId())){
			leaveBean.setCasualitiesList(session.createCriteria(CasualityDetailsDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("typeId",Integer.parseInt(leaveBean.getTypeId()))).add(Expression.eq("moduleId",Integer.parseInt(leaveBean.getModuleId()))).addOrder(Order.desc("id")).list());
			if(leaveBean.getCasualitiesList().size()==0 && !CPSUtils.isNullOrEmpty(leaveBean.getName())){
				leaveBean.setCasualitiesList(session.createCriteria(CasualityDetailsDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("typeId",Integer.parseInt(leaveBean.getTypeId()))).add(Expression.eq("name",leaveBean.getName())).addOrder(Order.desc("id")).list());
			}
		}else
			leaveBean.setCasualitiesList(session.createCriteria(CasualityDetailsDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.desc("id")).list());
		
		}
		catch(Exception e){
			throw e;
		}
		return leaveBean.getCasualitiesList();
	}

	@Override
	public String checkDupliateCasuality(LeaveAdministratorBean leaveBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			Criteria crit = session.createCriteria(CasualityDetailsDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(
					Expression.eq(CPSConstants.NAME, leaveBean.getName())).add(Expression.eq("typeId",Integer.parseInt(leaveBean.getTypeId())));
			if (!CPSUtils.isNullOrEmpty(leaveBean.getNodeID())) {
				crit = crit.add(Expression.eq(CPSConstants.ID, Integer.parseInt(leaveBean.getNodeID())));
			}
			if (CPSUtils.checkList(crit.list())) {
				leaveBean.setResult(CPSConstants.DUPLICATE);
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveBean.getResult();
	}

	@Override
	public String saveCasualityDetails(CasualityDetailsDTO casualityDetailsDTO) throws Exception {
		Session session = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();
			session.saveOrUpdate(casualityDetailsDTO);
			hibernateUtils.commitTransaction();
			hibernateUtils.closeSession();
			hibernateUtils.getSession();
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	@Override
	public String deleteCasualityDetails(LeaveAdministratorBean leaveBean) throws Exception {
		Session session = null;
		try{
		session = hibernateUtils.getSession();
		CasualityDetailsDTO casualityDetailsDTO = (CasualityDetailsDTO) session.get(CasualityDetailsDTO.class, Integer.valueOf(leaveBean.getNodeID()));
		casualityDetailsDTO.setStatus(0);
		session.saveOrUpdate(casualityDetailsDTO);
		leaveBean.setResult(CPSConstants.DELETE);
		hibernateUtils.closeSession();
		}catch(Exception e){
		throw e;	
		}
		return leaveBean.getResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<OrgInstanceDTO> getRoleList() throws Exception {
		Session session=null;
		List<OrgInstanceDTO> list;
		try{
			session=hibernateUtils.getSession();
		list=session.createCriteria(OrgInstanceDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("roleID", 19)).list();	
		}catch(Exception e){
		throw e;	
		}
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TaskHolderDetailsDTO> getTaskHolderList(LeaveAdministratorBean leaveBean) throws Exception {
		Session session=null;
		try{
			session=hibernateUtils.getSession();
			session.clear();
			leaveBean.settHolderList(session.createCriteria(TaskHolderDetailsDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).list());	
		 
		}catch(Exception e){
		throw e;	
		}
		return leaveBean.gettHolderList();
	}

	@Override
	public String checkDupliateTaskHolder(LeaveAdministratorBean leaveBean)throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			Criteria crit = session.createCriteria(TaskHolderDetailsDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(
					Expression.eq("roleId", Integer.parseInt(leaveBean.getRoleId()))).add(Expression.eq("typeId", Integer.parseInt(leaveBean.getTypeId())));
			if (!CPSUtils.isNullOrEmpty(leaveBean.getNodeID())) {
				crit = crit.add(Expression.ne(CPSConstants.ID, Integer.parseInt(leaveBean.getNodeID())));
			}
			if (CPSUtils.checkList(crit.list())) {
				leaveBean.setResult(CPSConstants.DUPLICATE);
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveBean.getResult();
	}

	@Override
	public String submitTaskHolderDetails(TaskHolderDetailsDTO holderDetailsDTO) throws Exception {
		Session session = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();
			session.saveOrUpdate(holderDetailsDTO);
			session.flush();
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}finally{
			
			session.clear();
		}
		return message;
	}

	@Override
	public String deleteTaskHolderDetails(LeaveAdministratorBean leaveBean)throws Exception {
		Session session = null;
		try{
		session = hibernateUtils.getSession();
		TaskHolderDetailsDTO taskHolderDetailsDTO = (TaskHolderDetailsDTO) session.get(TaskHolderDetailsDTO.class, Integer.valueOf(leaveBean.getNodeID()));
		taskHolderDetailsDTO.setStatus(0);
		session.saveOrUpdate(taskHolderDetailsDTO);
		hibernateUtils.commitTransaction();
		leaveBean.setResult(CPSConstants.SUCCESS);
		}catch(Exception e){
		throw e;	
		}
		return leaveBean.getResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TypeDesigDetailsDTO> getTypeDesigList(LeaveAdministratorBean leaveBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			leaveBean
					.setTypeDesigList(session
							.createSQLQuery("select name name,type_id typeId,remarks remarks,(select rtrim (xmlagg (xmlelement (e, desig.name || ',')).extract ('//text()'), ',') from designation_master desig where desig.id in (select designation_id from dopart_ii_type_desig_master in1 where status=1 and type_id=tab.type_id)) designations,(select rtrim (xmlagg (xmlelement (e, desig.id || ',')).extract ('//text()'), ',') from designation_master desig where desig.id in (select designation_id from dopart_ii_type_desig_master in1 where status=1 and type_id=tab.type_id)) designationIds,(select rtrim (xmlagg (xmlelement (e, do.id || ',')).extract ('//text()'), ',') from  dopart_ii_type_desig_master do where status=1 and type_id=tab.type_id ) nodeIds from (select distinct dtm.name,dtdm.type_id,dtdm.remarks from dopart_ii_type_desig_master dtdm,dopart_ii_type_master dtm where dtdm.type_id=dtm.id and dtdm.status=1 and dtm.status=1) tab")
							.addScalar("name", Hibernate.STRING).addScalar("typeId", Hibernate.INTEGER).addScalar("remarks", Hibernate.STRING).addScalar("designations", Hibernate.STRING).addScalar("designationIds", Hibernate.STRING).addScalar("nodeIds", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TypeDesigDetailsDTO.class)).list());
		} catch (Exception e) {
			throw e;
		}
		return leaveBean.getTypeDesigList();
	}

	@Override
	public String saveTypeDesigDetails(LeaveAdministratorBean leaveBean) throws Exception {
		Session session = null;
		String message = null;
		TypeDesigDetailsDTO detailsDTO=null;
		try{
			session=hibernateUtils.getSession();
			session.createSQLQuery("update dopart_ii_type_desig_master set status=0,last_modified_date=? where type_id=?")
			.setDate(0, CPSUtils.getCurrentDateWithTime()).setInteger(1, Integer.parseInt(leaveBean.getTypeId())).executeUpdate();
			
			session.flush();
//GATTU	       hibernateUtils.commitTransaction();
//GATTU 	hibernateUtils.beginTransaction(session);

		String[] selectedDesig = leaveBean.getDesignation().split(",");
		
		for (String desig : selectedDesig) {

			detailsDTO = (TypeDesigDetailsDTO) session.createCriteria(TypeDesigDetailsDTO.class).add(Expression.eq("typeId", Integer.parseInt(leaveBean.getTypeId()))).add(Expression.eq("designationId",Integer.parseInt(desig))).uniqueResult();
			if (CPSUtils.isNull(detailsDTO)) {
				detailsDTO = new TypeDesigDetailsDTO();
				detailsDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			}
			detailsDTO.setTypeId(Integer.parseInt(leaveBean.getTypeId()));
			detailsDTO.setStatus(1);
			detailsDTO.setDesignationId(Integer.parseInt(desig));
			detailsDTO.setRemarks(leaveBean.getRemarks());
			detailsDTO.setCreationDate(CPSUtils.getCurrentDate());
			session.saveOrUpdate(detailsDTO);
		}
		message = CPSConstants.SUCCESS;
		session.flush();
//GATTU		hibernateUtils.closeSession();
//GATTU		hibernateUtils.getSession();
		}catch(Exception e){
			throw e;
		}
		return message;
	}

	@Override
	public String checkDuplicateTypeDesig(LeaveAdministratorBean leaveBean) throws Exception {
		Session session = null;
		StringBuilder sb = new StringBuilder();
		try{
			session = hibernateUtils.getSession();
			sb
					.append("select count(*) from dopart_ii_type_desig_master where status=1 and type_id=? ");
			if (!CPSUtils.isNullOrEmpty(leaveBean.getNodeIds())) {
				sb.append(" and id not in(" + leaveBean.getNodeIds()+")");
			}
			String count = session.createSQLQuery(sb.toString()).setInteger(0,Integer.parseInt(leaveBean.getTypeId())).uniqueResult().toString();
			if (!CPSUtils.compareStrings(count, "0")) {
				leaveBean.setResult(CPSConstants.DUPLICATE);
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveBean.getResult();
	}

	@Override
	public String deleteTypeDesigDetails(LeaveAdministratorBean leaveBean)throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			session.createSQLQuery("update dopart_ii_type_desig_master set status=0,last_modified_date=? where type_id=? ")
					.setDate(0, CPSUtils.getCurrentDateWithTime()).setInteger(1, Integer.parseInt(leaveBean.getTypeId())).executeUpdate();

			leaveBean.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		}
		return leaveBean.getResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DesignationDTO> getAssigningDesignations(LeaveAdministratorBean leaveBean) throws Exception {
		Session session = null;
		List<DesignationDTO> list = null;
		try {
			session = hibernateUtils.getSession();

			list = session.createSQLQuery("select dtm.name key,dm.name,dtdm.id id from designation_master dm,dopart_ii_type_desig_master dtdm,dopart_ii_type_master dtm where dtdm.status=1 and dtm.status=1 and dtdm.designation_id=dm.id and dtdm.type_id=dtm.id and dtdm.id not in(select type_desig_id from dopart_ii_th_designations where status=1)").addScalar("id",
					Hibernate.INTEGER).addScalar("name", Hibernate.STRING).addScalar("key", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();

		} catch (Exception e) {
			throw e;
		} 
		return list;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<DesignationDTO> getAllAssigningDesignations(LeaveAdministratorBean leaveBean) throws Exception {
		Session session = null;
		List<DesignationDTO> list = null;
		try {
			session = hibernateUtils.getSession();

			list = session.createSQLQuery("select dtm.name key,dm.name,dtdm.id id from designation_master dm,dopart_ii_type_desig_master dtdm,dopart_ii_type_master dtm where dtdm.status=1 and dtm.status=1 and dtdm.designation_id=dm.id and dtdm.type_id=dtm.id ").addScalar("id",
					Hibernate.INTEGER).addScalar("name", Hibernate.STRING).addScalar("key", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();

		} catch (Exception e) {
			throw e;
		} 
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TaskHolderDetailsDTO> getTypeTaskHolder(LeaveAdministratorBean leaveBean) throws Exception {
		Session session = null;
		List<TaskHolderDetailsDTO> list = null;
		try {
			session = hibernateUtils.getSession();

			list = session.createSQLQuery("select dth.role_id roleId,dth.id id,dtm.name typeName,dtm.id typeId,ori.org_role_name roleName from dopart_ii_type_master dtm,dopart_ii_task_holder dth,org_role_instance ori where dth.type_id=dtm.id and dth.role_id=org_role_id and dtm.status=1 and dth.status=1 and ori.status=1")
			.addScalar("roleId", Hibernate.INTEGER).addScalar("id", Hibernate.INTEGER).addScalar("typeName",Hibernate.STRING).addScalar("typeId",Hibernate.INTEGER).addScalar("roleName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TaskHolderDetailsDTO.class)).list();

		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DesignationDTO> getDelectedDesignations(LeaveAdministratorBean leaveBean) throws Exception {
		Session session = null;
		List<DesignationDTO> list = null;
		try {
			session = hibernateUtils.getSession();

			list = session.createSQLQuery("select dm.name,dm.id from designation_master dm where dm.id not in(select designation_id from dopart_ii_type_desig_master where status=1) and dm.status=1 order by dm.order_no")
			.addScalar("id", Hibernate.INTEGER).addScalar("name",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(DesignationDTO.class)).list();

		} catch (Exception e) {
			throw e;
		}
		return list;

	}

	@Override
	public String saveTaskHolderDesignations(LeaveAdministratorBean leaveBean) throws Exception {
		Session session = null;
		String message = null;
		TaskHolderDesignationsDTO holderDesignationsDTO=null;
		try{
			session=hibernateUtils.getSession();
			session.createSQLQuery("update dopart_ii_th_designations set status=0,last_modified_date=? where role_id=?")
			.setDate(0, CPSUtils.getCurrentDateWithTime()).setInteger(1, Integer.parseInt(leaveBean.getTaskHolderMap())).executeUpdate();
			
				session.flush();
//GATTU	       hibernateUtils.commitTransaction();
//GATTU	       hibernateUtils.beginTransaction(session);

		String[] selectedDesig = leaveBean.getDesignation().split(",");
		
		for (String desig : selectedDesig) {

			holderDesignationsDTO = (TaskHolderDesignationsDTO) session.createCriteria(TaskHolderDesignationsDTO.class).add(Expression.eq("roleId", Integer.parseInt(leaveBean.getTaskHolderMap()))).add(Expression.eq("typeDesigId",Integer.parseInt(desig))).uniqueResult();
			if (CPSUtils.isNull(holderDesignationsDTO)) {
				holderDesignationsDTO = new TaskHolderDesignationsDTO();
			}
			holderDesignationsDTO.setStatus(1);
			holderDesignationsDTO.setRoleId(Integer.parseInt(leaveBean.getTaskHolderMap()));
			holderDesignationsDTO.setTypeDesigId(Integer.parseInt(desig));
			holderDesignationsDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			holderDesignationsDTO.setCreationDate(CPSUtils.getCurrentDate());
			session.saveOrUpdate(holderDesignationsDTO);
		}
		hibernateUtils.commitTransaction();
	    hibernateUtils.closeSession();
	    hibernateUtils.getSession();
		message = CPSConstants.SUCCESS;
		}catch(Exception e){
			throw e;
		}
		return message;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TaskHolderDesignationsDTO> getTaskHolderDesigList(LeaveAdministratorBean leaveBean) throws Exception {
		Session session = null;
		List<TaskHolderDesignationsDTO> list = null;
		try {System.out.println("check");
			session = hibernateUtils.getSession();

			/*list = session.createSQLQuery("select org_role_id roleId,org_role_name name,(select rtrim (xmlagg (xmlelement (e, do.type_desig_id || ',')).extract ('//text()'), ',') from dopart_ii_th_designations do where do.status=1 and do.role_id=tab.org_role_id) typeDesigIds, (select rtrim (xmlagg (xmlelement (e, desig.name || ',')).extract ('//text()'), ',') from designation_master desig where desig.id in (select designation_id from dopart_ii_type_desig_master in1,dopart_ii_th_designations dth where in1.status=1 and dth.type_desig_id=in1.id and dth.status=1 and dth.role_id=tab.org_role_id)) designations from(select distinct ori.org_role_id,ori.org_role_name from dopart_ii_th_designations dtd,org_role_instance ori,dopart_ii_type_desig_master dtdm WHERE DTD.ROLE_ID=ORI.ORG_ROLE_ID and dtd.status=1 and DTD.TYPE_DESIG_ID=DTDM.ID)tab")
			.addScalar("typeDesigIds", Hibernate.STRING).addScalar("roleId", Hibernate.INTEGER).addScalar("name", Hibernate.STRING).addScalar("designations",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TaskHolderDesignationsDTO.class)).list();
			 */
			
			list = session.createSQLQuery("SELECT org_role_id roleId,org_role_name NAME,tab.ID ID,tab.type_id typeId,tab.typename typeName,(SELECT rtrim (xmlagg (XMLELEMENT (e, do.type_desig_id || ',')).EXTRACT ('//text()'), ',') FROM dopart_ii_th_designations do WHERE do.status=1 AND do.role_id=tab.ID) typeDesigIds,(SELECT rtrim (xmlagg (XMLELEMENT (e, desig.NAME || ',')).EXTRACT ('//text()'), ',') FROM designation_master desig WHERE desig.ID IN (SELECT designation_id FROM dopart_ii_type_desig_master in1,dopart_ii_th_designations dth WHERE in1.status=1 AND dth.type_desig_id=in1.ID AND dth.status=1 AND dth.role_id=tab.ID)) designations FROM(SELECT DISTINCT ori.org_role_id,ori.org_role_name,dth.ID,dtdm.type_id,dtm.name typename  FROM dopart_ii_th_designations dtd,org_role_instance ori,dopart_ii_type_desig_master dtdm,dopart_ii_task_holder dth,dopart_ii_type_master dtm  WHERE dth.ROLE_ID=ORI.ORG_ROLE_ID AND dtd.status=1 and DTD.TYPE_DESIG_ID=DTDM.ID  And dtm.status = 1 And dtm.id = dtdm.type_id  and dth.id=dtd.role_id)tab") 
				  .addScalar("typeDesigIds", Hibernate.STRING).addScalar("roleId", Hibernate.INTEGER).addScalar("typeName",Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("id",Hibernate.INTEGER).addScalar("typeId",Hibernate.INTEGER).addScalar("designations",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TaskHolderDesignationsDTO.class)).list();
			
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public String deleteTaskHolderDesigDetails(LeaveAdministratorBean leaveBean)throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			session.createSQLQuery("update dopart_ii_th_designations set status=0,last_modified_date=? where role_id=? ")
					.setDate(0, CPSUtils.getCurrentDateWithTime()).setInteger(1, Integer.parseInt(leaveBean.getTaskHolderMap())).executeUpdate();

			leaveBean.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		}
		return leaveBean.getResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DoPartDTO> getReleaseDOPartDetails(LeaveAdministratorBean leaveBean) throws Exception {
		List<DoPartDTO> list=null;
		Session session=null;
		StringBuffer sb = new StringBuffer();
		try{
			session=hibernateUtils.getSession();
			sb.append("select ref_number doPartNumber,ref_date doPartDate,id,released_date releasedDate,to_char(accepted_date,'DD-Mon-YYYY') doPartType,accepted_date acceptedDate from reference_number_details where");

			if(leaveBean.getType().equals("r")){
				//sb.append(" (released_date is null or released_by is null) and");        //This is only for unreleased records
//				sb.append(" accepted_date is not null and ");                             //This is only for release records
				sb.append(" status=60 ");
				if(!CPSUtils.isNullOrEmpty(leaveBean.getTypeId())){              //This condition added for sorting records
					sb.append(" AND TYPE_ID="+leaveBean.getTypeId());
				}
				sb.append(" ORDER BY released_date desc,doPartDate desc,doPartNumber ASC");
			}else if(leaveBean.getType().equals("a")){
				sb.append(" (accepted_by is null or accepted_date is null) and released_date is not null and");
				sb.append(" status=60 ");
				if(!CPSUtils.isNullOrEmpty(leaveBean.getTypeId())){                   //This condition added for sorting records
					sb.append(" AND TYPE_ID="+leaveBean.getTypeId());
				}
				sb.append(" ORDER BY accepted_date desc,doPartDate desc,doPartNumber ASC");
			}else if(leaveBean.getType().equals("all")){                               //This condition added for sorting records
				sb.append(" released_date is not null and accepted_date is not null and");
				sb.append(" status=60 ");
				if(!CPSUtils.isNullOrEmpty(leaveBean.getTypeId())){
					sb.append(" AND TYPE_ID="+leaveBean.getTypeId());
				}
			 sb.append("ORDER BY doPartNumber ASC,doPartDate desc,released_date desc");
			}
			list = session.createSQLQuery(sb.toString())
			.addScalar("doPartNumber", Hibernate.STRING).addScalar("doPartDate", Hibernate.DATE).addScalar("id", Hibernate.INTEGER).addScalar("releasedDate", Hibernate.DATE).addScalar("acceptedDate", Hibernate.DATE).addScalar("doPartType", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(DoPartDTO.class)).list();
		}catch(Exception e){
			throw e;
		}
		return list;
	}

	@Override
	public String saveDoPartPOrtal(LeaveAdministratorBean leaveBean)throws Exception {
		Session session = null;
		StringBuffer sb = new StringBuffer();
		try {
			session = hibernateUtils.getSession();
            sb.append("update reference_number_details");
            if(leaveBean.getType().equals("r")){
            	sb.append(" set released_by=?,released_date=? where id=?");
            	session.createSQLQuery(sb.toString()).setString(0,leaveBean.getSfID()).setDate(1,leaveBean.getReleasedDate()).setInteger(2,Integer.parseInt(leaveBean.getNodeID())).executeUpdate();
            }else if(leaveBean.getType().equals("a")){
            	sb.append(" set accepted_by=?,accepted_date=? where id=?");
            	session.createSQLQuery(sb.toString()).setString(0,leaveBean.getSfID()).setDate(1,leaveBean.getAcceptedDate()).setInteger(2,Integer.parseInt(leaveBean.getNodeID())).executeUpdate();
            }
			leaveBean.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		}
		return leaveBean.getResult();
	}
	
	@Override
	public String dopartIIFreezeOut(LeaveAdministratorBean leaveBean)throws Exception {          //start doPartIIfreezeOUt
		Session session = null;
		StringBuffer sb = new StringBuffer();
		try {
			session = hibernateUtils.getSession();
            sb.append("update reference_number_details");
            if(leaveBean.getType().equals("r")){
            	sb.append(" set status=1 where id=?");
            	session.createSQLQuery(sb.toString()).setInteger(0,Integer.parseInt(leaveBean.getNodeID())).executeUpdate();
            }
           /* else if(leaveBean.getType().equals("a")){
            	sb.append(" set accepted_by=?,accepted_date=? where id=?");
            	session.createSQLQuery(sb.toString()).setString(0,leaveBean.getSfID()).setDate(1,leaveBean.getAcceptedDate()).setInteger(2,Integer.parseInt(leaveBean.getNodeID())).executeUpdate();
            }*/
			leaveBean.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		}
		return leaveBean.getResult();
	}                                                                      //end

	@Override
	@SuppressWarnings("unchecked")
	public List<CasualityDetailsDTO> getLeaveCasualityList(LeaveAdministratorBean leaveBean) throws Exception {
		Session session = null;
		try {
		session = hibernateUtils.getSession();
		leaveBean.setCasualitiesList(session.createCriteria(CasualityDetailsDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("moduleId", 2))
				.add(Expression.eq("typeId", Integer.parseInt(leaveBean.getGazettedType()))).addOrder(Order.asc("orderBy")).list());	
		} catch (Exception e) {
			throw e;
		}
		return leaveBean.getCasualitiesList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DoPartDTO> getdoPartCount(LeaveAdministratorBean leaveBean)	throws Exception {
		Session session = null;
		List<DoPartDTO> list = null;
		try {
			session = hibernateUtils.getSession();
            if(CPSUtils.compareStrings(leaveBean.getType(),"homeList")){
			list = session.createSQLQuery("select count(*) count,to_char(rnd.ref_date,'yyyy') year,dtm.name gazType,dtm.id typeId  from reference_number_details rnd,dopart_ii_type_master dtm where (rnd.status=1 or rnd.status=60) and dtm.id=rnd.type_id and dtm.id=? group by to_char(rnd.ref_date,'yyyy'), dtm.name,dtm.id order by year desc")
			.addScalar("count", Hibernate.INTEGER).addScalar("year", Hibernate.STRING).addScalar("gazType", Hibernate.STRING).addScalar("typeId", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(DoPartDTO.class)).setInteger(0,Integer.parseInt(leaveBean.getTypeId())).list();
            }
            if(CPSUtils.compareStrings(leaveBean.getType(),"monthWise")){
    			list = session.createSQLQuery("select count(*) count,to_char(rnd.ref_date,'yyyy') year,initcap(to_char(rnd.ref_date,'month')) month,dtm.id typeId from reference_number_details rnd,dopart_ii_type_master dtm where (rnd.status=1 or rnd.status=60) and dtm.id=rnd.type_id and dtm.id=?  and to_char(rnd.ref_date,'yyyy')=? group by to_char(rnd.ref_date,'yyyy'),initcap(to_char(rnd.ref_date,'month')), dtm.id order by month asc")
    			.addScalar("count", Hibernate.INTEGER).addScalar("year", Hibernate.STRING).addScalar("month", Hibernate.STRING).addScalar("typeId", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(DoPartDTO.class)).setInteger(0,Integer.parseInt(leaveBean.getTypeId())).setString(1,leaveBean.getYear()).list();
            }
            if(CPSUtils.compareStrings(leaveBean.getType(),"dateWise")){
    			list = session.createSQLQuery("select rnd.id id,rnd.ref_date doPartDate from reference_number_details rnd,dopart_ii_type_master dtm where rnd.type_id=dtm.id and dtm.id=? and trim(to_char(rnd.ref_date,'month'))=? and to_char(rnd.ref_date,'yyyy')=? and (rnd.status=1 or rnd.status=60) order by doPartDate desc")
    			.addScalar("id", Hibernate.INTEGER).addScalar("doPartDate", Hibernate.DATE).setResultTransformer(Transformers.aliasToBean(DoPartDTO.class)).setInteger(0,Integer.parseInt(leaveBean.getTypeId())).setString(1,leaveBean.getDoMonth().toLowerCase().trim()).setString(2,leaveBean.getYear()).list();
            }

		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CasualityDetailsDTO> getGazettedCasualityList(LeaveAdministratorBean leaveBean) throws Exception {
		Session session=null;
		StringBuilder sb=new StringBuilder();
		List<CasualityDetailsDTO> list;
		try{
		session=hibernateUtils.getSession();
		sb.append("select dcm.id id,dcm.module_id moduleId,dcm.type_id typeId,DTM.name typeName,EMM.name moduleName,DCM.CASUALITY name,DCM.CODE code,DCM.ORDER_BY orderBy,DCM.DESCRIPTION description from DOPART_II_CASUALITIES_MASTER DCM,DOPART_II_TYPE_MASTER DTM,ESS_MODULE_MASTER EMM where DCM.TYPE_ID=DTM.ID and EMM.ID=DCM.MODULE_ID and DCM.STATUS=1 and DTM.STATUS=1 and EMM.STATUS=1  ");
		if(!(CPSUtils.isNullOrEmpty(leaveBean.getTypeId()))){
			sb.append(" and DCM.TYPE_ID="+Integer.parseInt(leaveBean.getTypeId()));
		}
		if(!(CPSUtils.isNullOrEmpty(leaveBean.getModuleId()))){
			sb.append(" and DCM.MODULE_ID="+Integer.parseInt(leaveBean.getModuleId()));
		}
		sb.append(" order by dcm.type_id,module_id,dcm.code");
		list = session.createSQLQuery(sb.toString())
		.addScalar("id", Hibernate.INTEGER).addScalar("moduleId", Hibernate.INTEGER).addScalar("typeId", Hibernate.INTEGER).addScalar("typeName", Hibernate.STRING).addScalar("moduleName", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("code", Hibernate.INTEGER).addScalar("orderBy", Hibernate.INTEGER).addScalar("description", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(CasualityDetailsDTO.class)).list();
		}catch(Exception e){
			throw e;
		}
		return list;
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<DesignationDTO> getAssigningDesignationsList(LeaveAdministratorBean leaveBean) throws Exception {
		Session session = null;
		List<DesignationDTO> list = null;
		try {
			session = hibernateUtils.getSession();

			/*list = session.createSQLQuery("SELECT unique dm.name,dtdm.id FROM DOPART_II_TH_DESIGNATIONS dtd,DOPART_II_TYPE_DESIG_MASTER dtdm,designation_master dm where dtd.type_desig_id=dtdm.id and dtdm.designation_id=dm.id and dtdm.type_id="+leaveBean.getGazettedType()+" and dtdm.status=1 and dm.status=1 and dtd.status=1")
			.addScalar("id",Hibernate.INTEGER).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(DesignationDTO.class)).list();
			 */
			
			
			list = session.createSQLQuery(" SELECT dm.NAME,dtdm.ID FROM  DOPART_II_TH_DESIGNATIONS dtd,DOPART_II_TYPE_DESIG_MASTER dtdm,designation_master dm,DOPART_II_TASK_HOLDER dth WHERE dth.ID="+leaveBean.getTaskHolderMap()+" AND dtdm.designation_id=dm.ID AND dtd.role_id=dth.ID AND dtd.type_desig_id=dtdm.ID AND dtdm.status=1 AND dm.status=1 AND dtd.status=1 AND dth.status=1 order by dtdm.id")
			.addScalar("id",Hibernate.INTEGER).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(DesignationDTO.class)).list();
		} catch (Exception e) {
			throw e;
		} 
		return list;
	}



/*
	 SELECT dm.NAME,dtdm.ID 
	 FROM  DOPART_II_TH_DESIGNATIONS dtd,DOPART_II_TYPE_DESIG_MASTER dtdm,designation_master dm,DOPART_II_TASK_HOLDER dth  
	 WHERE dth.ID=3 AND dtdm.designation_id=dm.ID AND dtd.role_id=dth.ID AND dtd.type_desig_id=dtdm.ID 
	 and dtdm.status=1 and dm.status=1 and dtd.status=1 and dth.status=1



*/

	@Override
	@SuppressWarnings("unchecked")
	public List<DesignationDTO> getAssignedDesignations(LeaveAdministratorBean leaveBean) throws Exception {
		Session session = null;
		List<DesignationDTO> list = null;
		try {
			session = hibernateUtils.getSession();

			/*list = session.createSQLQuery("SELECT dm.NAME,dtdm.ID FROM  DOPART_II_TH_DESIGNATIONS dtd,DOPART_II_TYPE_DESIG_MASTER dtdm,designation_master dm,DOPART_II_TASK_HOLDER dth WHERE dth.ID="+leaveBean.getTaskHolderMap()+" AND dtdm.designation_id=dm.ID AND dtd.role_id=dth.ID AND dtd.type_desig_id=dtdm.ID and dtdm.status=1 and dm.status=1 and dtd.status=1 and dth.status=1")
			.addScalar("id",Hibernate.INTEGER).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(DesignationDTO.class)).list();
			 */
			
			list = session.createSQLQuery("SELECT dm.NAME,dtdm.ID FROM  DOPART_II_TYPE_DESIG_MASTER dtdm,designation_master dm WHERE dtdm.designation_id=dm.ID and dtdm.status=1 and dm.status=1 and dtdm.type_id="+leaveBean.getGazettedType())
			.addScalar("id",Hibernate.INTEGER).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(DesignationDTO.class)).list();
			
			 
		} catch (Exception e) {
			throw e;
		} 
		return list;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<DesignationDTO> getAssignedDesignations(String gazType) throws Exception {
		Session session = null;
		List<DesignationDTO> list = null;
		try {
			session = hibernateUtils.getSession();
			
			list = session.createSQLQuery("SELECT dm.NAME,dtdm.ID FROM  DOPART_II_TYPE_DESIG_MASTER dtdm,designation_master dm WHERE dtdm.designation_id=dm.ID and dtdm.status=1 and dm.status=1 and dtdm.type_id="+gazType+" and dtdm.id in (select dtd.type_desig_id from DOPART_II_TH_DESIGNATIONS dtd where dtd.status=1)")
			.addScalar("id",Hibernate.INTEGER).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(DesignationDTO.class)).list();
 
		} catch (Exception e) {
			throw e;
		} 
		return list;
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public String deleteAssignedTypeDesig(String taskHolderID) throws Exception {
		Session session = null;
		String message = null;
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String sql = "delete DOPART_II_TH_DESIGNATIONS where role_id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, taskHolderID);
			ps.executeUpdate();
			message=CPSConstants.DELETE;
			
		}catch (Exception e) {
			throw e;
		}finally{
			//session.close();
		}
		return message;
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> getRequestTypes() throws Exception {
		List<KeyValueDTO> list = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			list = session.createSQLQuery("SELECT request_type_id AS id, request_type AS name FROM request_master WHERE request_type_id IN (2, 6, 7) "
					+ "AND status = 1 ORDER BY request_type_id").addScalar("id", Hibernate.INTEGER).addScalar("name", Hibernate.STRING)
					.setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<LeaveTypeDTO> getAllLeaveTypes() throws Exception {
		List<LeaveTypeDTO> leaveTypes = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			//leaveTypes = (List<LeaveTypeDTO>) session.createQuery("select ltm.id, ltm.leaveType, ltm.code from LeaveTypeDTO ltm").list();
			leaveTypes = session.createSQLQuery("SELECT id AS id, leave_type AS leaveType, code AS code FROM leave_type_master ORDER BY order_no")
					.addScalar("id", Hibernate.INTEGER).addScalar("leaveType", Hibernate.STRING).addScalar("code", Hibernate.STRING)
					.setResultTransformer(Transformers.aliasToBean(LeaveTypeDTO.class)).list();
		} catch (Exception e) {
			throw e;
		}
		return leaveTypes;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<LeaveBusinessRulesVO> getLeaveBusinessRulesList() throws Exception { // Added by Naresh
		// SQL Queries
		final String sql = "SELECT br.id AS id, (CASE WHEN rm.request_type_id = 2 THEN 'Request' WHEN rm.request_type_id = 6 THEN 'Cancel' WHEN rm.request_type_id = 7 "
				+ "THEN 'Convert' END) AS requestType, ltm1.code AS leaveCode1, ltm1.leave_type leaveType1, ltm2.code AS leaveCode2, ltm2.leave_type AS leaveType2, ltm3.code AS leaveCode3, ltm3.leave_type AS leaveType3, ltm4.code AS leaveCode4, ltm4.leave_type AS leaveType4, "
				+ "ltm5.code AS leaveCode5, ltm5.leave_type AS leaveType5, br.condition AS condition, br.result_value AS resultValue, (CASE WHEN br.duration_id = 3 THEN 'Years' WHEN br.duration_id = 2 "
				+ "THEN 'Months' ELSE 'Days' END) AS duration, br.remarks AS remarks FROM request_master rm, business_rules br LEFT OUTER JOIN "
				+ "leave_type_master ltm1 ON (ltm1.id = br.one AND ltm1.status = 1) LEFT OUTER JOIN leave_type_master ltm2 ON (ltm2.id = br.two AND ltm2.status = 1) "
				+ "LEFT OUTER JOIN leave_type_master ltm3 ON (ltm3.id = br.three AND ltm3.status = 1) LEFT OUTER JOIN leave_type_master ltm4 ON "
				+ "(ltm4.id = br.four AND ltm4.status = 1) LEFT OUTER JOIN leave_type_master ltm5 ON (ltm5.id = br.five AND ltm5.status = 1) WHERE "
				+ "rm.request_type_id = br.request_type_id AND rm.status = 1 AND br.status = 1 ORDER BY br.id";
		// Code
		Session session = null;
		List<LeaveBusinessRulesVO> ruleList = null;
		try {
			session = hibernateUtils.getSession();
			ruleList = session.createSQLQuery(sql).addScalar("id", Hibernate.INTEGER).addScalar("requestType", Hibernate.STRING).addScalar("leaveCode1", Hibernate.STRING).addScalar("leaveType1", Hibernate.STRING)
					.addScalar("leaveCode2", Hibernate.STRING).addScalar("leaveType2", Hibernate.STRING).addScalar("leaveCode3", Hibernate.STRING).addScalar("leaveType3", Hibernate.STRING).addScalar("leaveCode4", Hibernate.STRING).addScalar("leaveType4", Hibernate.STRING)
					.addScalar("leaveCode5", Hibernate.STRING).addScalar("leaveType5", Hibernate.STRING).addScalar("condition", Hibernate.STRING)
					.addScalar("resultValue", Hibernate.INTEGER).addScalar("duration", Hibernate.STRING).addScalar("remarks", Hibernate.STRING)
					.setResultTransformer(Transformers.aliasToBean(LeaveBusinessRulesVO.class)).list();
			/*ruleList = session.createSQLQuery("SELECT ID AS id, REQUEST_TYPE_ID AS requestType, ONE AS leaveType1, TWO AS leaveType2, THREE AS leaveType3, "
					+ "FOUR AS leaveType4, FIVE AS leaveType5, CONDITION AS condition, RESULT_VALUE AS resultValue, DURATION_ID AS duration, REMARKS AS remarks "
					+ "FROM BUSINESS_RULES ORDER BY ID").addScalar("id", Hibernate.INTEGER).addScalar("requestType", Hibernate.STRING).addScalar("leaveType1", Hibernate.STRING)
					.addScalar("leaveType2", Hibernate.STRING).addScalar("leaveType3", Hibernate.STRING).addScalar("leaveType4", Hibernate.STRING)
					.addScalar("leaveType5", Hibernate.STRING).addScalar("condition", Hibernate.STRING)
					.addScalar("resultValue", Hibernate.INTEGER).addScalar("duration", Hibernate.STRING).addScalar("remarks", Hibernate.STRING)
					.setResultTransformer(Transformers.aliasToBean(LeaveBusinessRulesDTO.class)).list();;*/
		} catch(Exception e) {
			throw e;
		}
		return ruleList;
	} // End
	
	public String saveLeaveBusinessRules(LeaveAdministratorBean leaveBean) throws Exception {
		Session session = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();
			org.json.JSONObject jsonObj = new org.json.JSONObject(leaveBean.getBusinessRulesList());
			for (int i = 0; i < jsonObj.length(); i++) {
				LeaveBusinessRulesDTO businessRule = new LeaveBusinessRulesDTO();
				org.json.JSONObject obj = (org.json.JSONObject) jsonObj.get(String.valueOf(i));
				businessRule.setRequestType(Integer.parseInt(obj.getString("requestyType")));
				businessRule.setOne(Integer.parseInt(obj.getString("one")));
				businessRule.setTwo(Integer.parseInt(obj.getString("two")));
				if (!CPSUtils.isNullOrEmpty(obj.getString("three"))) {
					businessRule.setThree(Integer.parseInt(obj.getString("three")));
				} else {
					businessRule.setThree(null);
				}
				if (!CPSUtils.isNullOrEmpty(obj.getString("four"))) {
					businessRule.setFour(Integer.parseInt(obj.getString("four")));
				} else {
					businessRule.setFour(null);
				}
				if (!CPSUtils.isNullOrEmpty(obj.getString("five"))) {
					businessRule.setFive(Integer.parseInt(obj.getString("five")));
				} else {
					businessRule.setFive(null);
				}
				businessRule.setCondition(obj.getString("condition"));
				businessRule.setResultValue(Integer.parseInt(obj.getString("resultValue")));
				businessRule.setDurationId(Integer.parseInt(obj.getString("duration")));
				businessRule.setRemarks(obj.getString("remarks"));
				
				businessRule.setStatus(1);
				businessRule.setCreatedBy(leaveBean.getSfID());
				businessRule.setCreationDate(CPSUtils.getCurrentDateWithTime());
				businessRule.setLastModifiedBy(leaveBean.getSfID());
				businessRule.setLastModifiedDate(CPSUtils.getCurrentDateWithTime());
				if (Integer.parseInt(obj.getString("pk")) != 0) {
					businessRule.setId(Integer.parseInt(obj.getString("pk")));
					message = CPSConstants.UPDATE;
				} else {
					message = CPSConstants.SUCCESS;
				}
				session.saveOrUpdate(businessRule);
				session.flush();
			}
			session.clear();
		} catch (Exception e) {
			throw e;
		}
		return message;
	}
	
	public String deleteBusinessRule(int id) throws Exception {
		String message = null;
		LeaveBusinessRulesDTO rule = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			rule = (LeaveBusinessRulesDTO) session.get(LeaveBusinessRulesDTO.class, id);
			if (!CPSUtils.isNullOrEmpty(rule)) {
				rule.setStatus(0);
				session.saveOrUpdate(rule);
				session.flush();
				message = CPSConstants.DELETE;
			} else {
				message = CPSConstants.FAILED;
			}
		} catch(Exception e) {
			throw e;
		}
		return message;
	}
}
