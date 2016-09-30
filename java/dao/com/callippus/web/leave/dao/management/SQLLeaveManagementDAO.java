package com.callippus.web.leave.dao.management;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.leave.beans.management.LeaveManagementBean;
import com.callippus.web.leave.dto.ExceptionTypeMasterDTO;
import com.callippus.web.leave.dto.LeaveConversionMappingsDTO;
import com.callippus.web.leave.dto.LeaveDurationDTO;
import com.callippus.web.leave.dto.LeaveEncashmentDTO;
import com.callippus.web.leave.dto.LeaveExceptionDetailsDTO;
import com.callippus.web.leave.dto.LeaveFamilyDTO;
import com.callippus.web.leave.dto.LeaveRelationsDTO;
import com.callippus.web.leave.dto.LeaveTypeDTO;
import com.callippus.web.leave.dto.SpecialCasualLeaveDTO;

@SuppressWarnings("serial")
@Service
public class SQLLeaveManagementDAO implements ILeaveManagementDAO, Serializable {
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	@SuppressWarnings("unchecked")
	public List<LeaveTypeDTO> getLeaveTypeMasterDetails(LeaveManagementBean leaveBean) throws Exception {
		List<LeaveTypeDTO> leaveTypeList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			leaveTypeList = session.createCriteria(LeaveTypeDTO.class).add(Expression.eq(CPSConstants.STATUS, Integer.valueOf("1"))).addOrder(Order.asc("orderNo")).list();
		} catch (Exception e) {
			throw e;
		}
		return leaveTypeList;
	}

	public String InsertLeaveGeneralDetailsDAO(LeaveManagementBean leaveBean) throws Exception {
		String message = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			hibernateUtils.closeSession();
			session = hibernateUtils.getSession();
			session.clear();
			session.saveOrUpdate(leaveBean);
			session.flush();
			List<LeaveExceptionDetailsDTO> exceptionList = leaveBean.getExceptionList();
			List<LeaveConversionMappingsDTO> conversionList = leaveBean.getConversionList();
			for (LeaveExceptionDetailsDTO exception : exceptionList) {
				session.clear();
				session.saveOrUpdate(exception);
				session.flush();
			}
			for (LeaveConversionMappingsDTO conversion : conversionList) {
				session.clear();
				session.saveOrUpdate(conversion);
				session.flush();
			}

			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String InsertLeaveOtherDetailsDAO(LeaveManagementBean leaveBean) throws Exception {
		String message = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			session.clear();
			session.saveOrUpdate(leaveBean);
			session.flush();
			if (CPSUtils.compareStrings(CPSConstants.Y, leaveBean.getFamilyImpactFlag())) {
				session.saveOrUpdate(leaveBean.getLeaveFamilyDTO());
				session.flush();
			}
			if (CPSUtils.compareStrings(CPSConstants.Y, leaveBean.getEncashmentFlag())) {
				session.saveOrUpdate(leaveBean.getLeaveEncashmentDTO());
				session.flush();
			}
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return message;
	}

	public String InsertLeaveRulesDetailsDAO(LeaveManagementBean leaveBean) throws Exception {
		String message = null;
		Session session = null;
		try {
			// commit transaction & close the session
			hibernateUtils.closeSession();
			session = hibernateUtils.getSession();
			session.clear();
			session.saveOrUpdate(leaveBean);
			session.flush();
			List<LeaveExceptionDetailsDTO> exceptionList = leaveBean.getExceptionList();
			for (LeaveExceptionDetailsDTO exception : exceptionList) {
				session.saveOrUpdate(exception);
				session.flush();
			}
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	public JSON getExceptionMasterDetails() throws Exception {
		JSON expjson = null;
		List<ExceptionTypeMasterDTO> exceptionTypeList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			exceptionTypeList = session.createCriteria(ExceptionTypeMasterDTO.class).list();

			HashMap<String, String> hashmap = new HashMap<String, String>();

			for (ExceptionTypeMasterDTO exception : exceptionTypeList) {
				hashmap.put(exception.getName(), String.valueOf(exception.getId()));
			}
			expjson = (JSON) JSONSerializer.toJSON(hashmap);
		} catch (Exception e) {
			throw e;
		}
		return expjson;
	}

	@SuppressWarnings("unchecked")
	public List<LeaveDurationDTO> getLeaveDurationMasterDetailsDAO(LeaveManagementBean leaveBean) throws Exception {
		List<LeaveDurationDTO> leaveDurationList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			leaveDurationList = session.createCriteria(LeaveDurationDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).list();
		} catch (Exception e) {
			throw e;
		}
		return leaveDurationList;
	}

	@SuppressWarnings("unchecked")
	public LeaveManagementBean getLeaveTypeDetailsDAO(LeaveManagementBean leaveBean) throws Exception {
		List<LeaveExceptionDetailsDTO> exceptionList = null;
		List<LeaveConversionMappingsDTO> conversionList = null;
		LeaveEncashmentDTO leaveEncashmentDTO = null;
		LeaveFamilyDTO leaveFamilyDTO = null;
		String leaveTypeID = leaveBean.getLeaveTypeId();
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			leaveBean = (LeaveManagementBean) session.createCriteria(LeaveManagementBean.class).add(Expression.eq("leaveTypeId", leaveBean.getLeaveTypeId())).uniqueResult();
			if (CPSUtils.isNull(leaveBean)) {
				leaveBean = new LeaveManagementBean();
				leaveBean.setLeaveTypeId(leaveTypeID);
			} else {
				exceptionList = session.createCriteria(LeaveExceptionDetailsDTO.class).add(Expression.eq("leaveTypeID", leaveBean.getLeaveTypeId())).list();
				leaveBean.setExceptionList(exceptionList);

				conversionList = session.createCriteria(LeaveConversionMappingsDTO.class).add(Expression.eq("conversionFrom", leaveBean.getLeaveTypeId())).list();
				leaveBean.setConversionList(conversionList);

				leaveEncashmentDTO = (LeaveEncashmentDTO) session.createCriteria(LeaveEncashmentDTO.class).add(Expression.eq("leaveTypeID", leaveBean.getLeaveTypeId())).uniqueResult();
				if (!CPSUtils.isNull(leaveEncashmentDTO)) {
					leaveBean.setNoOfDaysService(leaveEncashmentDTO.getNoOfDaysInService());
					leaveBean.setNoOfSpellsYear(leaveEncashmentDTO.getNoOfSpellsInYear());
					leaveBean.setMinDaysInSpell(leaveEncashmentDTO.getMinDaysPerSpell());
					leaveBean.setMaxDaysEncashInSPell(leaveEncashmentDTO.getMaxDaysPerSpell());
					leaveBean.setMinDaysAfterEncash(leaveEncashmentDTO.getMinLeavesAfterEncash());
				}
				leaveFamilyDTO = (LeaveFamilyDTO) session.createCriteria(LeaveFamilyDTO.class).add(Expression.eq("leaveTypeID", leaveBean.getLeaveTypeId())).uniqueResult();
				if (!CPSUtils.isNull(leaveFamilyDTO)) {
					leaveBean.setChildAgeLimit(leaveFamilyDTO.getChildAge());
					leaveBean.setPhChildAgeLimit(leaveFamilyDTO.getPhChildAge());
					leaveBean.setServivingChild(leaveFamilyDTO.getNoOfChildren());
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	public String getLeaveEncashmentIDDetails(final String leaveTypeID) throws Exception {
		String encashmentID = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			LeaveEncashmentDTO leaveEncashmentDTO = (LeaveEncashmentDTO) session.createCriteria(LeaveEncashmentDTO.class).add(Expression.eq("leaveTypeID", leaveTypeID)).uniqueResult();
			if (!CPSUtils.isNull(leaveEncashmentDTO)) {
				encashmentID = String.valueOf(leaveEncashmentDTO.getId());
			}
		} catch (Exception e) {
			throw e;
		}
		return encashmentID;
	}

	public String getLeaveTypeIDDetails(final String leaveTypeID) throws Exception {
		String leaveId = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			LeaveManagementBean leavebeBean = (LeaveManagementBean) session.createCriteria(LeaveManagementBean.class).add(Expression.eq("leaveTypeId", leaveTypeID)).uniqueResult();
			if (!CPSUtils.isNull(leavebeBean)) {
				leaveId = String.valueOf(leavebeBean.getId());
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveId;
	}

	public String getLeaveFamilyIDDetails(final String leaveTypeID) throws Exception {
		String familyId = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			LeaveFamilyDTO leaveFamilyDTO = (LeaveFamilyDTO) session.createCriteria(LeaveFamilyDTO.class).add(Expression.eq("leaveTypeID", leaveTypeID)).uniqueResult();
			if (!CPSUtils.isNull(leaveFamilyDTO)) {
				familyId = Integer.toString(leaveFamilyDTO.getId());
			}
		} catch (Exception e) {
			throw e;
		}
		return familyId;
	}

	public void deleteExceptionDetails(final String leaveTypeID, final int subtype) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			if (CPSUtils.compareStrings(CPSConstants.SCL, leaveTypeID)) {
				session.createSQLQuery("delete from LEAVE_EXCEPTION_DETAILS where LEAVE_TYPE_ID=? and LEAVE_SUB_TYPE_ID=?").setString(0, leaveTypeID).setInteger(1, subtype).executeUpdate();
			} else {
				session.createSQLQuery("delete from LEAVE_EXCEPTION_DETAILS where LEAVE_TYPE_ID=? and EXCEPTION_TYPE_ID=?").setString(0, leaveTypeID).setInteger(1, subtype).executeUpdate();
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public void deleteELconversionDetails(final String leaveTypeID) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			session.createSQLQuery("delete from LEAVE_CONVERSION_MAPPING where CONVERTED_FROM=?").setString(0, leaveTypeID).executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> getSpecialCasualLeaveTypes(final String categoryType) throws Exception {
		List<KeyValueDTO> keyValueDTO = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			keyValueDTO = session.createSQLQuery("select id as id, category_type as key, leave_sub_type as name, to_char(to_date, 'dd-Mon-yyyy') as flag, "
					+ "to_char(from_date, 'dd-Mon-yyyy') as value from SPECIAL_CASUAL_LEAVES where STATUS = 1 and CATEGORY_TYPE = ?").addScalar("id", Hibernate.INTEGER)
					.addScalar("name").addScalar("key").addScalar("flag").addScalar("value").setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(0, categoryType).list();

		} catch (Exception e) {
			throw e;
		}
		return keyValueDTO;
	}

	public SpecialCasualLeaveDTO getSpecialCasualLeaveDetails(String leavesubtype) throws Exception {
		SpecialCasualLeaveDTO specialCasualLeaveDTO = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			if (CPSUtils.compareStrings(CPSConstants.NEW, leavesubtype)) {
				leavesubtype = "0";
			}
			specialCasualLeaveDTO = (SpecialCasualLeaveDTO) session.createCriteria(SpecialCasualLeaveDTO.class).add(Expression.eq("id", Integer.parseInt(leavesubtype))).uniqueResult();
		} catch (Exception e) {
			throw e;
		}
		return specialCasualLeaveDTO;
	}

	@SuppressWarnings("unchecked")
	public List<LeaveExceptionDetailsDTO> getExceptionDetails(final String leaveTypeId, String leaveSubType) throws Exception {
		List<LeaveExceptionDetailsDTO> exceptionList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			if (CPSUtils.compareStrings(CPSConstants.NEW, leaveSubType)) {
				leaveSubType = "0";
			}
			exceptionList = session.createCriteria(LeaveExceptionDetailsDTO.class).add(Expression.eq("leaveTypeID", leaveTypeId)).add(Expression.eq("leaveSubTypeID", leaveSubType)).list();

		} catch (Exception e) {
			throw e;
		}
		return exceptionList;
	}

	public String getSpCLID(final String leavesubtype) throws Exception {
		String spclid = "";
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			spclid = String.valueOf(((SpecialCasualLeaveDTO) session.createCriteria(SpecialCasualLeaveDTO.class).add(Expression.eq("leaveSubType", leavesubtype)).uniqueResult()).getId());
		} catch (Exception e) {
			throw e;
		}
		return spclid;
	}

	public String getSpCLID1(final String leavesubtype, final String fromdate, String todate) throws Exception {
		String spclid = "";
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			spclid = Integer.toString(((SpecialCasualLeaveDTO) session.createCriteria(SpecialCasualLeaveDTO.class).add(Expression.eq("leaveSubType", leavesubtype)).add(
					Expression.eq("strFromDate", fromdate)).add(Expression.eq("strToDate", todate)).uniqueResult()).getId());
		} catch (Exception e) {
			throw e;
		}
		return spclid;
	}

	public String InsertSpecialCasualLeaveDetails(SpecialCasualLeaveDTO specialCasualLeaveDTO, List<LeaveExceptionDetailsDTO> exceptionlist) throws Exception {
		String message = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			session.saveOrUpdate(specialCasualLeaveDTO);
			session.flush();
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String InsertSpecialCasualLeaveExceptionDetails(SpecialCasualLeaveDTO specialCasualLeaveDTO, List<LeaveExceptionDetailsDTO> exceptionlist) throws Exception {
		String message = null;
		Session session = null;
		String id = "";
		try {
			session = hibernateUtils.getSession();
			if (CPSUtils.compareStrings(specialCasualLeaveDTO.getCategoryType(), "T2"))
				id = getSpCLID1(specialCasualLeaveDTO.getLeaveSubType(), specialCasualLeaveDTO.getStrFromDate(), specialCasualLeaveDTO.getStrToDate());
			else
				id = getSpCLID(specialCasualLeaveDTO.getLeaveSubType());
			for (int i = 0; i < exceptionlist.size(); i++) {
				LeaveExceptionDetailsDTO leaveExceptionDetailsDTO = exceptionlist.get(i);
				leaveExceptionDetailsDTO.setLeaveSubTypeID(id);
				session.saveOrUpdate(leaveExceptionDetailsDTO);
				session.flush();
			}

			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String insertLeaveRelationMappingDetails(List<LeaveRelationsDTO> exceptionlist) throws Exception {
		String message = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			for (LeaveRelationsDTO leaveRelationsDTO : exceptionlist) {
				session.saveOrUpdate(leaveRelationsDTO);
				session.flush();
			}

			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public void deleteLeaveMappingDetails() throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			session.createSQLQuery("delete from LEAVE_RELATION_MAPPINGS ").executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> getLeaveRelationMappingDetails() throws Exception {
		List<KeyValueDTO> keyValueDTOList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			keyValueDTOList = session.createSQLQuery("select leave_type_id1 as key,leave_type_id2 as name from LEAVE_RELATION_MAPPINGS where STATUS=1  ").addScalar("name", Hibernate.STRING)
					.addScalar("key", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
		} catch (Exception e) {
			throw e;
		}
		return keyValueDTOList;
	}
}