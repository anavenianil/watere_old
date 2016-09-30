package com.callippus.web.leave.business.management;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.leave.beans.management.LeaveManagementBean;
import com.callippus.web.leave.dao.management.ILeaveManagementDAO;
import com.callippus.web.leave.dto.LeaveConversionMappingsDTO;
import com.callippus.web.leave.dto.LeaveEncashmentDTO;
import com.callippus.web.leave.dto.LeaveExceptionDetailsDTO;
import com.callippus.web.leave.dto.LeaveFamilyDTO;
import com.callippus.web.leave.dto.LeaveRelationsDTO;
import com.callippus.web.leave.dto.SpecialCasualLeaveDTO;

@Service
public class LeaveManagementBusiness {
	@Autowired
	private ILeaveManagementDAO leaveManagementDAO;

	public LeaveManagementBean getLeaveTypeMaster(LeaveManagementBean leaveBean) throws Exception {
		try {
			leaveBean.setLeaveTypeList(leaveManagementDAO.getLeaveTypeMasterDetails(leaveBean));
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	public String insertLeaveGeneralDetails(LeaveManagementBean leaveBean) throws Exception {
		String message = "";
		try {

			String id = leaveManagementDAO.getLeaveTypeIDDetails(leaveBean.getLeaveTypeId());
			if (!CPSUtils.isNullOrEmpty(id)) {
				leaveBean.setId(Integer.parseInt(id));
			}
			if (CPSUtils.compareStrings(CPSConstants.SELECT, leaveBean.getDebitMappingID())) {
				leaveBean.setDebitMappingID("0");
				leaveBean.setDebitMappingLeaves("0");
			}
			if (CPSUtils.isNull(leaveBean.getLeaveDurationID())) {
				leaveBean.setLeaveDurationID("0");
			}
			if (CPSUtils.isNull(leaveBean.getMinPerSpell())) {
				leaveBean.setMinPerSpell("0");
			}
			if (CPSUtils.isNull(leaveBean.getMaxPerSPell())) {
				leaveBean.setMaxPerSPell("0");
			}
			if (CPSUtils.isNull(leaveBean.getNewEmpAvailLeaves())) {
				leaveBean.setNewEmpAvailLeaves("0");
			}
			if (CPSUtils.isNull(leaveBean.getSpellsInService())) {
				leaveBean.setSpellsInService("0");
			}
			if (CPSUtils.isNull(leaveBean.getSpellsPerYear())) {
				leaveBean.setSpellsPerYear("0");
			}

			if (CPSUtils.isNull(leaveBean.getCreationDate())) {
				leaveBean.setCreationDate(CPSUtils.getCurrentDate());
			}
			if (CPSUtils.isNull(leaveBean.getLastModifiedDate())) {
				leaveBean.setLastModifiedDate(CPSUtils.getCurrentDate());
			}
			if (CPSUtils.isNull(leaveBean.getLastModifiedBy())) {
				leaveBean.setLastModifiedBy(leaveBean.getSfid());
			}
			if (CPSUtils.isNull(leaveBean.getCreatedBy())) {
				leaveBean.setCreatedBy(leaveBean.getSfid());
			}
			leaveBean.setStatus(1);
			List<LeaveExceptionDetailsDTO> items = new ArrayList<LeaveExceptionDetailsDTO>();
			List<LeaveConversionMappingsDTO> conversionList = new ArrayList<LeaveConversionMappingsDTO>();
			JSONObject mainjson = new JSONObject(leaveBean.getExceptionsJson());

			if (!CPSUtils.isNull(mainjson.get("medical"))) {
				JSONObject medicaljson = (JSONObject) mainjson.get("medical");
				items = saveExceptionDetails(medicaljson, leaveBean, items, 0);
			}
			if (!CPSUtils.isNull(mainjson.get("fitness"))) {
				JSONObject fitnessjson = (JSONObject) mainjson.get("fitness");
				items = saveExceptionDetails(fitnessjson, leaveBean, items, 0);
			}
			if (!CPSUtils.isNull(mainjson.get("otherCredit"))) {
				JSONObject otherCreditjson = (JSONObject) mainjson.get("otherCredit");
				items = saveExceptionDetails(otherCreditjson, leaveBean, items, 0);
			}
			if (!CPSUtils.isNull(mainjson.get("othercert"))) {
				JSONObject othercertjson = (JSONObject) mainjson.get("othercert");
				items = saveExceptionDetails(othercertjson, leaveBean, items, 0);
			}
			if (!CPSUtils.isNull(mainjson.get("prior"))) {
				JSONObject priorjson = (JSONObject) mainjson.get("prior");
				items = saveExceptionDetails(priorjson, leaveBean, items, 0);
			}
			leaveBean.setExceptionList(items);
			if (!CPSUtils.isNull(mainjson.get("elconversion"))) {
				JSONObject elconversionjson = (JSONObject) mainjson.get("elconversion");
				conversionList = saveElConversionDetails(elconversionjson, leaveBean, conversionList);
			}
			leaveBean.setConversionList(conversionList);
			leaveManagementDAO.deleteELconversionDetails(leaveBean.getLeaveTypeId());
			leaveManagementDAO.deleteExceptionDetails(leaveBean.getLeaveTypeId(), 1);
			leaveManagementDAO.deleteExceptionDetails(leaveBean.getLeaveTypeId(), 2);
			leaveManagementDAO.deleteExceptionDetails(leaveBean.getLeaveTypeId(), 3);
			leaveManagementDAO.deleteExceptionDetails(leaveBean.getLeaveTypeId(), 6);
			leaveManagementDAO.deleteExceptionDetails(leaveBean.getLeaveTypeId(), 7);
			message = leaveManagementDAO.InsertLeaveGeneralDetailsDAO(leaveBean);

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String insertLeaveOtherDetails(LeaveManagementBean leaveBean) throws Exception {
		String message = "";
		try {

			String id1 = leaveManagementDAO.getLeaveTypeIDDetails(leaveBean.getLeaveTypeId());
			if (!CPSUtils.isNullOrEmpty(id1)) {
				leaveBean.setId(Integer.parseInt(id1));
			}
			leaveBean.setStatus(1);
			if (CPSUtils.compareStrings(CPSConstants.SELECT, leaveBean.getDebitMappingID())) {
				leaveBean.setDebitMappingID("0");
				leaveBean.setDebitMappingLeaves("0");
			}
			if (CPSUtils.isNull(leaveBean.getCreationDate())) {
				leaveBean.setCreationDate(CPSUtils.getCurrentDate());
			}
			if (CPSUtils.isNull(leaveBean.getLastModifiedDate())) {
				leaveBean.setLastModifiedDate(CPSUtils.getCurrentDate());
			}
			if (CPSUtils.isNull(leaveBean.getLastModifiedBy())) {
				leaveBean.setLastModifiedBy(leaveBean.getSfid());
			}
			if (CPSUtils.isNull(leaveBean.getCreatedBy())) {
				leaveBean.setCreatedBy(leaveBean.getSfid());
			}

			if (CPSUtils.compareStrings(CPSConstants.Y, leaveBean.getFamilyImpactFlag())) {
				String id = leaveManagementDAO.getLeaveFamilyIDDetails(leaveBean.getLeaveTypeId());
				LeaveFamilyDTO leaveFamilyDTO = new LeaveFamilyDTO();
				if (!CPSUtils.isNullOrEmpty(id)) {
					leaveFamilyDTO.setId(Integer.parseInt(id));
				}

				leaveFamilyDTO.setChildAge(leaveBean.getChildAgeLimit());
				leaveFamilyDTO.setPhChildAge(leaveBean.getPhChildAgeLimit());

				leaveFamilyDTO.setNoOfChildren(leaveBean.getServivingChild());
				leaveFamilyDTO.setLeaveTypeID(leaveBean.getLeaveTypeId());
				leaveBean.setLeaveFamilyDTO(leaveFamilyDTO);
			}

			if (CPSUtils.compareStrings(CPSConstants.Y, leaveBean.getEncashmentFlag())) {
				String id = leaveManagementDAO.getLeaveEncashmentIDDetails(leaveBean.getLeaveTypeId());
				LeaveEncashmentDTO leaveEncashmentDTO = new LeaveEncashmentDTO();
				if (!CPSUtils.isNullOrEmpty(id)) {
					leaveEncashmentDTO.setId(Integer.parseInt(id));
				}
				leaveEncashmentDTO.setLeaveTypeID(leaveBean.getLeaveTypeId());
				leaveEncashmentDTO.setNoOfDaysInService(leaveBean.getNoOfDaysService());
				leaveEncashmentDTO.setNoOfSpellsInYear(leaveBean.getNoOfSpellsYear());
				leaveEncashmentDTO.setMinDaysPerSpell(leaveBean.getMinDaysInSpell());
				leaveEncashmentDTO.setMaxDaysPerSpell(leaveBean.getMaxDaysEncashInSPell());
				leaveEncashmentDTO.setMinLeavesAfterEncash(leaveBean.getMinDaysAfterEncash());
				leaveBean.setLeaveEncashmentDTO(leaveEncashmentDTO);
			}
			message = leaveManagementDAO.InsertLeaveOtherDetailsDAO(leaveBean);

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String insertLeaveRulesDetails(LeaveManagementBean leaveBean) throws Exception {
		String message = "";
		try {

			String id1 = leaveManagementDAO.getLeaveTypeIDDetails(leaveBean.getLeaveTypeId());
			if (!CPSUtils.isNull(id1)) {
				leaveBean.setId(Integer.parseInt(id1));
			}
			leaveBean.setStatus(1);
			if (CPSUtils.compareStrings(CPSConstants.SELECT, leaveBean.getDebitMappingID())) {
				leaveBean.setDebitMappingID("0");
				leaveBean.setDebitMappingLeaves("0");
			}

			leaveBean.setLastModifiedDate(CPSUtils.getCurrentDate());
			leaveBean.setLastModifiedBy(leaveBean.getSfid());

			List<LeaveExceptionDetailsDTO> items = new ArrayList<LeaveExceptionDetailsDTO>();
			JSONObject mainjson = new JSONObject(leaveBean.getExceptionsJson());
			if (!CPSUtils.isNull(mainjson.get("minspell"))) {
				JSONObject minspelljson = ((JSONObject) mainjson.get("minspell"));
				items = saveExceptionDetails(minspelljson, leaveBean, items, 0);
			}
			if (!CPSUtils.isNull(mainjson.get("maxspell"))) {
				JSONObject maxspelljson = ((JSONObject) mainjson.get("maxspell"));
				items = saveExceptionDetails(maxspelljson, leaveBean, items, 0);
			}
			leaveBean.setExceptionList(items);
			leaveManagementDAO.deleteExceptionDetails(leaveBean.getLeaveTypeId(), 4);
			leaveManagementDAO.deleteExceptionDetails(leaveBean.getLeaveTypeId(), 5);
			message = leaveManagementDAO.InsertLeaveRulesDetailsDAO(leaveBean);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public List<LeaveExceptionDetailsDTO> saveExceptionDetails(JSONObject jsonDetails, LeaveManagementBean leave, List<LeaveExceptionDetailsDTO> items, int subtypeid) throws Exception {
		try {

			LeaveExceptionDetailsDTO leaveExceptionDetailsDTO = null;
			for (int i = 0; i < jsonDetails.length(); i++) {
				JSONObject js = (JSONObject) jsonDetails.get(String.valueOf(i));
				String description = (String) js.get("exp");
				String exceptionTypeId = (String) js.get("expID");
				if (!CPSUtils.isNullOrEmpty(description)) {
					leaveExceptionDetailsDTO = new LeaveExceptionDetailsDTO();
					leaveExceptionDetailsDTO.setDescription(description);
					leaveExceptionDetailsDTO.setLeaveTypeID(leave.getLeaveTypeId());
					leaveExceptionDetailsDTO.setStatus(1);
					leaveExceptionDetailsDTO.setCreationDate(CPSUtils.getCurrentDate());
					leaveExceptionDetailsDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					if (CPSUtils.isNull(leave.getLastModifiedBy())) {
						leaveExceptionDetailsDTO.setLastModifiedBy(leave.getSfid());
					} else {
						leaveExceptionDetailsDTO.setLastModifiedBy(leave.getLastModifiedBy());
					}
					if (CPSUtils.isNull(leave.getCreatedBy())) {
						leaveExceptionDetailsDTO.setCreatedBy(leave.getSfid());
					} else {
						leaveExceptionDetailsDTO.setCreatedBy(leave.getCreatedBy());
					}

					leaveExceptionDetailsDTO.setExceptionTypeId(exceptionTypeId);
					if (CPSUtils.compareStrings(Integer.toString(subtypeid), "0"))
						leaveExceptionDetailsDTO.setLeaveSubTypeID(null);
					else
						leaveExceptionDetailsDTO.setLeaveSubTypeID(Integer.toString(subtypeid));
					items.add(leaveExceptionDetailsDTO);
				}
			}

		} catch (Exception e) {
			throw e;
		}
		return items;
	}

	public List<LeaveConversionMappingsDTO> saveElConversionDetails(JSONObject jsonDetails, LeaveManagementBean leave, List<LeaveConversionMappingsDTO> items) throws Exception {
		try {

			LeaveConversionMappingsDTO leaveConversionMappingsDTO = null;
			for (int i = 0; i < jsonDetails.length(); i++) {
				JSONObject js = (JSONObject) jsonDetails.get(String.valueOf(i));
				String noOfDays = (String) js.get("noofdays");
				String conversionTo = (String) js.get("leavetype");
				leaveConversionMappingsDTO = new LeaveConversionMappingsDTO();
				leaveConversionMappingsDTO.setConversionFrom(leave.getLeaveTypeId());
				leaveConversionMappingsDTO.setNoOfDays(noOfDays);
				leaveConversionMappingsDTO.setConversionTo(conversionTo);
				leaveConversionMappingsDTO.setStatus(1);
				items.add(leaveConversionMappingsDTO);
			}

		} catch (Exception e) {
			throw e;
		}
		return items;
	}

	public LeaveManagementBean getExceptionMasterDetails(LeaveManagementBean leaveBean) throws Exception {
		try {
			leaveBean.setExceptionMasterDetailsJSON(leaveManagementDAO.getExceptionMasterDetails());
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	public LeaveManagementBean getLeaveDurationMasterDetails(LeaveManagementBean leaveBean) throws Exception {
		try {
			leaveBean.setLeaveDurationList(leaveManagementDAO.getLeaveDurationMasterDetailsDAO(leaveBean));
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	public LeaveManagementBean getLeaveTypeDetails(LeaveManagementBean leaveBean) throws Exception {
		try {
			leaveBean = leaveManagementDAO.getLeaveTypeDetailsDAO(leaveBean);
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	public LeaveManagementBean clearBeanValues(LeaveManagementBean leaveBean) throws Exception {
		SpecialCasualLeaveDTO specialCasualLeaveDTO = null;
		try {

			specialCasualLeaveDTO = new SpecialCasualLeaveDTO();
			leaveBean.setEligibilityFlag(specialCasualLeaveDTO.getEligibilityFlag());
			leaveBean.setNoOfDays(specialCasualLeaveDTO.getNoOfDays());
			leaveBean.setSecondTimeRemarks(specialCasualLeaveDTO.getSecondTimeRemarks());
			leaveBean.setStrFromDate(specialCasualLeaveDTO.getFromDate());
			leaveBean.setStrToDate(specialCasualLeaveDTO.getToDate());
			leaveBean.setPriorApprovalFlag(specialCasualLeaveDTO.getPriorApprovalFlag());
			leaveBean.setNoticePeriodFlag(specialCasualLeaveDTO.getNoticePeriodFlag());
			leaveBean.setServiceBookFlag(specialCasualLeaveDTO.getServiceBookFlag());
			leaveBean.setDoPartFlag(specialCasualLeaveDTO.getDoPartFlag());
			leaveBean.setMedicalCertFlag(specialCasualLeaveDTO.getMedicalCertFlag());
			leaveBean.setOtherCertFlag(specialCasualLeaveDTO.getOtherCertFlag());
			leaveBean.setOtherFileName(specialCasualLeaveDTO.getOtherFileName());
			leaveBean.setLeaveSubType("select");

		} catch (Exception e) {
			throw e;
		}
		return leaveBean;

	}

	public String getSpecialCasualID(String leavesubtype) throws Exception {

		String spclId = "";
		try {
			spclId = leaveManagementDAO.getSpCLID(leavesubtype);
		} catch (Exception e) {
			throw e;
		}
		return spclId;
	}

	public String getSpecialCasualID1(final String leavesubtype, final String fromdate, final String todate) throws Exception {
		String spclId = "";
		try {
			spclId = leaveManagementDAO.getSpCLID1(leavesubtype, fromdate, todate);
		} catch (Exception e) {
			throw e;
		}
		return spclId;
	}

	public List<KeyValueDTO> getSpecialCasualLeaveTypes(final String categoryType) throws Exception {
		List<KeyValueDTO> list = null;
		try {
			list = leaveManagementDAO.getSpecialCasualLeaveTypes(categoryType);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public LeaveManagementBean getSpecialCasualLeaveDetails(LeaveManagementBean leavebean) throws Exception {

		SpecialCasualLeaveDTO specialCasualLeaveDTO = null;
		try {
			specialCasualLeaveDTO = leaveManagementDAO.getSpecialCasualLeaveDetails(leavebean.getLeaveSubType());
			if (CPSUtils.isNull(specialCasualLeaveDTO)) {
				specialCasualLeaveDTO = new SpecialCasualLeaveDTO();
			}
			leavebean.setEligibilityFlag(specialCasualLeaveDTO.getEligibilityFlag());
			leavebean.setNoOfDays(specialCasualLeaveDTO.getNoOfDays());
			leavebean.setSecondTimeRemarks(specialCasualLeaveDTO.getSecondTimeRemarks());
			leavebean.setStrFromDate(specialCasualLeaveDTO.getFromDate());
			leavebean.setStrToDate(specialCasualLeaveDTO.getToDate());
			leavebean.setPriorApprovalFlag(specialCasualLeaveDTO.getPriorApprovalFlag());
			leavebean.setNoticePeriodFlag(specialCasualLeaveDTO.getNoticePeriodFlag());
			leavebean.setServiceBookFlag(specialCasualLeaveDTO.getServiceBookFlag());
			leavebean.setDoPartFlag(specialCasualLeaveDTO.getDoPartFlag());
			leavebean.setMedicalCertFlag(specialCasualLeaveDTO.getMedicalCertFlag());
			leavebean.setOtherCertFlag(specialCasualLeaveDTO.getOtherCertFlag());
			leavebean.setOtherFileName(specialCasualLeaveDTO.getOtherFileName());
			leavebean.setSpecialLeaveDesc(specialCasualLeaveDTO.getLeaveSubType());
			leavebean.setExceptionList(leaveManagementDAO.getExceptionDetails(leavebean.getLeaveTypeId(), leavebean.getLeaveSubType()));

		} catch (Exception e) {
			throw e;
		}
		return leavebean;
	}

	public String insertSpecialCasualLeaveDetails(LeaveManagementBean leaveBean) throws Exception {
		String message = "";
		try {
			SpecialCasualLeaveDTO specialCasualLeaveDTO = new SpecialCasualLeaveDTO();
			if (!CPSUtils.compareStrings(CPSConstants.NEW, leaveBean.getLeaveSubType())) {
				specialCasualLeaveDTO.setId(Integer.parseInt(leaveBean.getLeaveSubType()));
			}
			specialCasualLeaveDTO.setCategoryType(leaveBean.getCategoryType());
			specialCasualLeaveDTO.setLeaveSubType(leaveBean.getSpecialLeaveDesc());
			specialCasualLeaveDTO.setStrFromDate(leaveBean.getStrFromDate());
			specialCasualLeaveDTO.setStrToDate(leaveBean.getStrToDate());
			specialCasualLeaveDTO.setEligibilityFlag(leaveBean.getEligibilityFlag());
			specialCasualLeaveDTO.setNoOfDays(leaveBean.getNoOfDays());
			specialCasualLeaveDTO.setSecondTimeRemarks(leaveBean.getSecondTimeRemarks());
			specialCasualLeaveDTO.setPriorApprovalFlag(leaveBean.getPriorApprovalFlag());
			specialCasualLeaveDTO.setNoticePeriodFlag(leaveBean.getNoticePeriodFlag());
			specialCasualLeaveDTO.setServiceBookFlag(leaveBean.getServiceBookFlag());
			specialCasualLeaveDTO.setDoPartFlag(leaveBean.getDoPartFlag());
			specialCasualLeaveDTO.setMedicalCertFlag(leaveBean.getMedicalCertFlag());
			specialCasualLeaveDTO.setOtherCertFlag(leaveBean.getOtherCertFlag());
			specialCasualLeaveDTO.setOtherFileName(leaveBean.getOtherFileName());
			specialCasualLeaveDTO.setStatus(1);

			if (CPSUtils.isNull(leaveBean.getCreationDate())) {
				specialCasualLeaveDTO.setCreationDate(CPSUtils.getCurrentDate());
			}

			specialCasualLeaveDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			specialCasualLeaveDTO.setLastModifiedBy(leaveBean.getSfid());

			if (CPSUtils.isNull(leaveBean.getCreatedBy())) {
				specialCasualLeaveDTO.setCreatedBy(leaveBean.getSfid());
			}

			List<LeaveExceptionDetailsDTO> items = new ArrayList<LeaveExceptionDetailsDTO>();
			JSONObject mainjson = new JSONObject(leaveBean.getExceptionsJson());
			if (!CPSUtils.isNull(mainjson.get("prior"))) {
				JSONObject priorjson = (JSONObject) mainjson.get("prior");
				items = saveExceptionDetails(priorjson, leaveBean, items, specialCasualLeaveDTO.getId());
			}
			if (!CPSUtils.isNull(mainjson.get("othercert"))) {
				JSONObject othercertjson = (JSONObject) mainjson.get("othercert");
				items = saveExceptionDetails(othercertjson, leaveBean, items, specialCasualLeaveDTO.getId());
			}
			if (!CPSUtils.isNull(mainjson.get("medical"))) {
				JSONObject medicaljson = (JSONObject) mainjson.get("medical");
				items = saveExceptionDetails(medicaljson, leaveBean, items, specialCasualLeaveDTO.getId());
			}

			leaveBean.setExceptionList(items);
			leaveManagementDAO.deleteExceptionDetails(leaveBean.getLeaveTypeId(), specialCasualLeaveDTO.getId());
			message = leaveManagementDAO.InsertSpecialCasualLeaveDetails(specialCasualLeaveDTO, items);
			if (CPSUtils.compareStrings(CPSConstants.SUCCESS, message)) {
				message = leaveManagementDAO.InsertSpecialCasualLeaveExceptionDetails(specialCasualLeaveDTO, items);
			}
		} catch (Exception e) {
			throw e;
		}

		return message;
	}

	public String insertLeaveRelationMappingDetails(LeaveManagementBean leaveBean) throws Exception {
		String message = "";
		try {
			List<LeaveRelationsDTO> list = new ArrayList<LeaveRelationsDTO>();
			JSONObject mainjson = new JSONObject(leaveBean.getExceptionsJson());
			if (!CPSUtils.isNull(mainjson)) {
				for (int i = 0; i < mainjson.length(); i++) {
					JSONObject items = (JSONObject) mainjson.get(String.valueOf(i));
					LeaveRelationsDTO leaveRelationsDTO = new LeaveRelationsDTO();
					leaveRelationsDTO.setLeaveTypeID1(items.get("map1").toString());
					leaveRelationsDTO.setLeaveTypeID2(items.get("map2").toString());
					leaveRelationsDTO.setLastModifiedBy(leaveBean.getSfid());
					leaveRelationsDTO.setCreatedBy(leaveBean.getSfid());
					leaveRelationsDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					leaveRelationsDTO.setCreationDate(CPSUtils.getCurrentDate());
					leaveRelationsDTO.setStatus(1);
					list.add(leaveRelationsDTO);

				}
			}
			leaveManagementDAO.deleteLeaveMappingDetails();
			message = leaveManagementDAO.insertLeaveRelationMappingDetails(list);

		} catch (Exception e) {
			throw e;
		}
		return message;

	}

	public List<KeyValueDTO> getLeaveRelationMappingDetails() throws Exception {

		List<KeyValueDTO> list = null;
		try {
			list = leaveManagementDAO.getLeaveRelationMappingDetails();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
}