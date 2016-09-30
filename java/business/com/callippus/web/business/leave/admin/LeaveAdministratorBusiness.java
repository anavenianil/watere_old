package com.callippus.web.business.leave.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.CasualityDetailsDTO;
import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.DoPartDTO;
import com.callippus.web.beans.dto.EssModuleDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.TaskHolderDesignationsDTO;
import com.callippus.web.beans.dto.TaskHolderDetailsDTO;
import com.callippus.web.beans.dto.TypeDesigDetailsDTO;
import com.callippus.web.beans.dto.TypeDetailsDTO;
import com.callippus.web.business.requestprocess.LeaveRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.leave.beans.admin.LeaveAdministratorBean;
import com.callippus.web.leave.beans.request.LeaveApplicationBean;
import com.callippus.web.leave.dao.admin.ILeaveAdministratorDAO;
import com.callippus.web.leave.dao.request.ILeaveRequestDAO;
import com.callippus.web.leave.dto.LeaveBusinessRulesDTO;
import com.callippus.web.leave.dto.LeaveDetailsDTO;
import com.callippus.web.leave.dto.LeaveManualEntryDTO;

@Service
public class LeaveAdministratorBusiness {
	@Autowired
	private ILeaveAdministratorDAO leaveAdministratorDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;
	@Autowired
	private ILeaveRequestDAO leaveRequestDAO;
	@Autowired
	private LeaveRequestProcess leaveRequestProcess;

	public LeaveAdministratorBean getEmpAvailableLeaves(LeaveAdministratorBean leaveBean) throws Exception {
		try {
			leaveBean = leaveAdministratorDAO.getEmpAvailableLeaves(leaveBean);
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}
	
	//getEmpAvailableOrNot
	
	public LeaveAdministratorBean getEmpAvailableOrNot(LeaveAdministratorBean leaveBean) throws Exception {
		try {
			leaveBean = leaveAdministratorDAO.getEmpAvailableOrNot(leaveBean);
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}
	

	public LeaveAdministratorBean getSpellsDetails(LeaveAdministratorBean leaveBean) throws Exception {
		try {
			leaveBean = leaveAdministratorDAO.getSpellsDetails(leaveBean);
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	public String submitEnterAvailableLeaves(LeaveAdministratorBean leaveBean) throws Exception {
		String message = null;
		try {
			message = commonDataDAO.checkEmployee(leaveBean.getEntrySfid());
			if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
				message = leaveAdministratorDAO.saveEnterAvailableLeaves(leaveBean);
			} else {
				message = CPSConstants.EMPNOTEXISTS;
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	public List<DesignationDTO> getDesignationList() throws Exception {
		List<DesignationDTO> list = null;
		try {
			list = commonDataDAO.getMasterData(CPSConstants.DESIGNATIONDTO);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public List<LeaveManualEntryDTO> getLeaveBalanceList(LeaveAdministratorBean leaveBean) throws Exception {
		List<LeaveManualEntryDTO> list = null;
		try {
			list = leaveAdministratorDAO.getLeaveBalanceList(leaveBean);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public String submitLeaveBalance(LeaveAdministratorBean leaveBean) throws Exception {
		String message = null;
		try {
			message = leaveAdministratorDAO.submitLeaveBalance(leaveBean);
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		return message;
	}

	public void dataEntry() throws Exception {
		try {
			leaveAdministratorDAO.dataEntry();
		} catch (Exception e) {
			throw e;
		}
	}

	public LeaveAdministratorBean getSearchedLeaves(LeaveAdministratorBean leaveBean) throws Exception {
		LeaveApplicationBean leaveAppBean = new LeaveApplicationBean();
		try {
			if (!CPSUtils.isNullOrEmpty(leaveBean.getDoPartNo())) {
				String [] doPart = leaveBean.getDoPartNo().split("#");
				if (CPSUtils.compareStrings(leaveBean.getType(), "dateWiseLeaveList"))
					leaveAppBean.setDoPartDate(doPart[1]);
			}
			if (CPSUtils.compareStrings(leaveBean.getType(), CPSConstants.USER)) {
				leaveAppBean.setSfID(leaveBean.getSfID());
			}
			leaveAppBean.setType(leaveBean.getType());
			leaveAppBean.setFromDate(leaveBean.getFromDate());
			leaveAppBean.setToDate(leaveBean.getToDate());
			leaveAppBean.setGazettedType(leaveBean.getGazettedType());
			leaveAppBean = leaveRequestDAO.getAppliedLeaves(leaveAppBean);
			leaveBean.setAppliedLeavesList(leaveAppBean.getAppliedLeavesList());
			leaveAppBean = leaveRequestDAO.getCancelledLeaves(leaveAppBean);
			leaveBean.setCancelledLavesList(leaveAppBean.getCancelledLeavesList());
			leaveAppBean = leaveRequestDAO.getConvertedLeaves(leaveAppBean);
			leaveBean.setConvertedLavesList(leaveAppBean.getConvertedLeavesList());
			if (CPSUtils.compareStrings(leaveBean.getParam(), "gazetted")) {
				leaveBean.setDoPartList(leaveRequestDAO.getDoPartList(leaveBean));
			}
			if (CPSUtils.compareStrings(leaveBean.getType(), "dateWiseLeaveList")) {
				//leaveAppBean.setDoPartDate(leaveBean.getDoPartDate());
			}
			leaveBean.setTypeList(commonDataDAO.getMasterData(CPSConstants.TYPEDETAILSDTO));
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	public LeaveAdministratorBean completeLeaveRequests(LeaveAdministratorBean leaveBean) throws Exception {
		try {
			leaveBean = leaveAdministratorDAO.completeLeaveRequests(leaveBean);
		} catch (Exception e) {
			leaveBean.setMessage(CPSConstants.FAILED);
			throw e;
		}
		return leaveBean;
	}

	public LeaveAdministratorBean getDoPartSearchList(LeaveAdministratorBean leaveBean) throws Exception {
		try {
			leaveBean = leaveAdministratorDAO.getDoPartSearchList(leaveBean);
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	public List<DoPartDTO> getPublishedDoParts() throws Exception {
		List<DoPartDTO> doPart = null;
		try {
			doPart = leaveAdministratorDAO.getPublishedDoParts();
		} catch (Exception e) {
			throw e;
		}
		return doPart;
	}

	public LeaveAdministratorBean getLeaveTypeList(LeaveAdministratorBean leaveBean) throws Exception {
		try {
			leaveBean.setLeaveTypeList(leaveAdministratorDAO.getLeaveTypeList());
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	public LeaveAdministratorBean getTxnSearchedLeaves(LeaveAdministratorBean leaveBean) throws Exception {
		try {
			leaveBean.setLeaveTxnList(leaveAdministratorDAO.getTxnSearchedLeaves(leaveBean));
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	public LeaveAdministratorBean getLeaveExpList(LeaveAdministratorBean leaveBean) throws Exception {
		try {
			leaveBean.setLeaveExpList(leaveAdministratorDAO.getLeaveExpList(leaveBean));
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	public LeaveAdministratorBean getEmployeesList(LeaveAdministratorBean leaveBean) throws Exception {
		try {
			leaveBean.setEmployeesList(leaveAdministratorDAO.getEmployeesList(leaveBean));
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	public String submitLeaveExceptionalEmp(LeaveAdministratorBean leaveBean) throws Exception {
		String message = null;
		try {
			message = leaveAdministratorDAO.submitLeaveExceptionalEmp(leaveBean);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public float getAvailableLeavesList(LeaveAdministratorBean leaveBean) throws Exception {
		try {
			leaveBean.setBalanceLeaves(leaveAdministratorDAO.getAvailableLeavesList(leaveBean));
		} catch (Exception e) {
			throw e;
		}
		return leaveBean.getBalanceLeaves();
	}

	public String saveLeaveAdit(LeaveAdministratorBean leaveBean) throws Exception {
		try {
			leaveBean.setMessage(commonDataDAO.checkEmployee(leaveBean.getSearchSfid()));

			if (CPSUtils.compareStrings(leaveBean.getMessage(), CPSConstants.SUCCESS)) {
				leaveBean.setMessage(leaveAdministratorDAO.saveLeaveAdit(leaveBean));

				// Update leave credits
				leaveBean.setMessage(leaveAdministratorDAO.updateLeaveCredit(leaveBean));

				// update leave account if the leave type is EL,HPL,LND,EOL,EOLWOMC
				if (CPSUtils.compareStrings(leaveBean.getLeaveType(), CPSConstants.EL) || CPSUtils.compareStrings(leaveBean.getLeaveType(), CPSConstants.HPL)
						|| CPSUtils.compareStrings(leaveBean.getLeaveType(), CPSConstants.LND) || CPSUtils.compareStrings(leaveBean.getLeaveType(), CPSConstants.EOL)
						|| CPSUtils.compareStrings(leaveBean.getLeaveType(), CPSConstants.EOLWOMC)) {

					LeaveDetailsDTO leaveDetailsDTO = new LeaveDetailsDTO();
					leaveDetailsDTO.setType(CPSConstants.CREDIT);
					leaveDetailsDTO.setSfID(leaveBean.getSearchSfid());
					leaveDetailsDTO.setNoOfDays(leaveBean.getNoOfDays());
					leaveDetailsDTO.setFromDate(leaveBean.getTxnDate());
					leaveDetailsDTO.setToDate(leaveBean.getTxnDate());
					if (CPSUtils.compareStrings(leaveBean.getTxnType(), CPSConstants.ADITSTATUSID)) {
						leaveDetailsDTO.setNoOfMonths("Audit");
					} else if (CPSUtils.compareStrings(leaveBean.getTxnType(), CPSConstants.CORRECTIONSTATUSID)) {
						leaveDetailsDTO.setNoOfMonths("CRT");
					} else if (CPSUtils.compareStrings(leaveBean.getTxnType(), CPSConstants.LTCSTATUSID)) {
						leaveDetailsDTO.setNoOfMonths("LTC");
					}

					if (CPSUtils.compareStrings(leaveBean.getLeaveType(), CPSConstants.LND)) {
						leaveDetailsDTO.setLeaveTypeID(CPSConstants.HPL);
					} else {
						leaveDetailsDTO.setLeaveTypeID(leaveBean.getLeaveType());
					}
					leaveRequestProcess.updateLeaveAccount(leaveDetailsDTO);
				}

			} else {
				leaveBean.setMessage(CPSConstants.EMPNOTEXISTS);
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveBean.getMessage();
	}

	public void copyLeaves() throws Exception {
		try {
			leaveAdministratorDAO.copyLeaves();
		} catch (Exception e) {
			throw e;
		}
	}
	public void backup2011() throws Exception {
		try {
			//insert previous balance from backup_leave_balance to leave_yearly_balance
			leaveAdministratorDAO.backup2011();
			
			//update leave account balance columns
			leaveAdministratorDAO.updateLeaveAccountBalance();
			
		} catch (Exception e) {
			throw e;
		}
	}
	public LeaveAdministratorBean getLeaveAccountHomeDetails(LeaveAdministratorBean leaveBean) throws Exception {
		try {
			leaveBean.setYearsList(leaveRequestDAO.getYearsList());
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	public LeaveAdministratorBean getApplicableYears(LeaveAdministratorBean leaveBean) throws Exception {
		try {
			leaveBean.setYearsList(leaveRequestDAO.getYearsList(leaveBean.getSearchSfid())); 
		} catch(Exception e) {
			throw e;
		}
		return leaveBean;
	}
	
	public String getRetirementDate(String sfid) throws Exception {
		String retiredDate = null;
		try {
			retiredDate = leaveRequestDAO.getRetirementDate(sfid);
		} catch (Exception e) {
			throw e;
		}
		return retiredDate;
	}
	
	public String checkEmployee(String sfid) throws Exception {
		String result = null;
		try {
			result = commonDataDAO.checkEmployee(sfid);
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public LeaveAdministratorBean getTypeHomeDetails(LeaveAdministratorBean leaveBean) throws Exception{
		try{
		leaveBean.setTypeList(commonDataDAO.getMasterData(CPSConstants.TYPEDETAILSDTO));
		}catch(Exception e){
			throw e;
		}
		return leaveBean;
	}

	public String manageTypeDetails(LeaveAdministratorBean leaveBean) throws Exception{
		try{
		leaveBean.setResult(leaveAdministratorDAO.checkDupliateType(leaveBean));
		if(!CPSUtils.compareStrings(leaveBean.getResult(), CPSConstants.DUPLICATE)){
		TypeDetailsDTO detailsDTO=new TypeDetailsDTO();
		if(!CPSUtils.isNullOrEmpty(leaveBean.getNodeID())){
			detailsDTO.setId(Integer.parseInt(leaveBean.getNodeID()));
			detailsDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			detailsDTO.setCreationDate(CPSUtils.getCurrentDate());
		}else{
			detailsDTO.setCreationDate(CPSUtils.getCurrentDate());
		}
		detailsDTO.setName(leaveBean.getName());
		detailsDTO.setPrefixName(leaveBean.getPrefixName());
		detailsDTO.setDescription(leaveBean.getDescription());
		detailsDTO.setDelimeter(leaveBean.getDelimeter());
		detailsDTO.setStatus(1);
		leaveBean.setResult(leaveAdministratorDAO.saveTypeDetails(detailsDTO));
		}
	}catch(Exception e){
		throw e;
	}
	return leaveBean.getResult();
	}

	public String deleteTypeDetails(LeaveAdministratorBean leaveBean) throws Exception{
		try{
			leaveBean.setResult(leaveAdministratorDAO.deleteTypeDetails(leaveBean));
		}catch(Exception e){
			throw e;
		}
		return leaveBean.getResult();
	}

	@SuppressWarnings("unchecked")
	public LeaveAdministratorBean getCasualitiesDetails(LeaveAdministratorBean leaveBean)  throws Exception{
		try{
		leaveBean.setEssModuleList(commonDataDAO.getMasterData(CPSConstants.ESSMODULEDTO));
		leaveBean.setTypeList(commonDataDAO.getMasterData(CPSConstants.TYPEDETAILSDTO));
		leaveBean.setYearsList(commonDataDAO.getMasterData(CPSConstants.YEARTYPEDTO));
		}catch(Exception e){
			throw e;
		}
		return leaveBean;
	}

	public List<CasualityDetailsDTO> getCasualitiesList(LeaveAdministratorBean leaveBean) throws Exception{
	try{
		leaveBean.setCasualitiesList(leaveAdministratorDAO.getCasualitiesList(leaveBean));
	}catch(Exception e){
	throw e;	
	}
		return leaveBean.getCasualitiesList();
	}

	public String manageCasualityDetails(LeaveAdministratorBean leaveBean) throws Exception{
		try{
			if(CPSUtils.isNullOrEmpty(leaveBean.getNodeID()) || CPSUtils.compareStrings(leaveBean.getNodeID(), "0")){
				leaveBean.setResult(leaveAdministratorDAO.checkDupliateCasuality(leaveBean));
			}
			if(!CPSUtils.compareStrings(leaveBean.getResult(), CPSConstants.DUPLICATE)){
				CasualityDetailsDTO casualityDetailsDTO=new CasualityDetailsDTO();
				if(!CPSUtils.isNullOrEmpty(leaveBean.getNodeID()) && !CPSUtils.compareStrings(leaveBean.getNodeID(), "0")){
					casualityDetailsDTO.setId(Integer.parseInt(leaveBean.getNodeID()));
					casualityDetailsDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				}else{
					casualityDetailsDTO.setCreationDate(CPSUtils.getCurrentDate());
				}
			casualityDetailsDTO.setModuleId(Integer.parseInt(leaveBean.getModuleId()));
			casualityDetailsDTO.setTypeId(Integer.parseInt(leaveBean.getTypeId()));
			casualityDetailsDTO.setName(leaveBean.getName());
			casualityDetailsDTO.setDescription(leaveBean.getDescription());
			casualityDetailsDTO.setCode(leaveBean.getCode());
			casualityDetailsDTO.setOrderBy(Integer.parseInt(leaveBean.getOrder()));
			casualityDetailsDTO.setStatus(1);
			leaveBean.setResult(leaveAdministratorDAO.saveCasualityDetails(casualityDetailsDTO));
			if(!CPSUtils.compareStrings(leaveBean.getNodeID(), "0") && !CPSUtils.isNullOrEmpty(leaveBean.getNodeID())){
				leaveBean.setResult(CPSConstants.UPDATE);
			}
	
		}
		}catch(Exception e){
			throw e;
		}
		return leaveBean.getResult();
	}

	public String deleteCasualityDetails(LeaveAdministratorBean leaveBean) throws Exception{
		try{
			leaveBean.setResult(leaveAdministratorDAO.deleteCasualityDetails(leaveBean));
		}catch(Exception e){
			throw e;
		}
		return leaveBean.getResult();
		
	}

	@SuppressWarnings("unchecked")
	public LeaveAdministratorBean getTypeDesigHomeDetails(LeaveAdministratorBean leaveBean) throws Exception{
		try{
			leaveBean.setTypeList(commonDataDAO.getMasterData(CPSConstants.TYPEDETAILSDTO));
			leaveBean.setDeselectedDesigList(leaveAdministratorDAO.getDelectedDesignations(leaveBean));
		}catch(Exception e){
			throw e;
		}
		return leaveBean;
	}

	@SuppressWarnings("unchecked")
	public LeaveAdministratorBean getTaskHolderDetails(LeaveAdministratorBean leaveBean) throws Exception {
		try{
		leaveBean.setRoleList(leaveAdministratorDAO.getRoleList());
		leaveBean.setTypeList(commonDataDAO.getMasterData(CPSConstants.TYPEDETAILSDTO));
		}catch(Exception e){
		throw e;	
		}
		return leaveBean;
	}
	public List<TypeDesigDetailsDTO> getTypeDesigList(LeaveAdministratorBean leaveBean) throws Exception{
		try{
			leaveBean.setTypeDesigList(leaveAdministratorDAO.getTypeDesigList(leaveBean));
		}catch(Exception e){
		throw e;	
		}
		return leaveBean.getTypeDesigList();
	}

	public List<TaskHolderDetailsDTO> getTaskHolderList(LeaveAdministratorBean leaveBean) throws Exception{
		try{
			leaveBean.settHolderList(leaveAdministratorDAO.getTaskHolderList(leaveBean));
		}catch(Exception e){
		throw e;	
		}
		return leaveBean.gettHolderList();
	}

	public String manageTaskHolderDetails(LeaveAdministratorBean leaveBean) throws Exception{
		try{
			leaveBean.setResult(leaveAdministratorDAO.checkDupliateTaskHolder(leaveBean));
			if(!CPSUtils.compareStrings(leaveBean.getResult(), CPSConstants.DUPLICATE)){
			TaskHolderDetailsDTO holderDetailsDTO=new TaskHolderDetailsDTO();
			if (!CPSUtils.isNullOrEmpty(leaveBean.getNodeID())) {
				// Update Old row
				holderDetailsDTO.setId(Integer.valueOf(leaveBean.getNodeID()));
			}else{
				holderDetailsDTO.setCreationDate(CPSUtils.getCurrentDate());
			}
			holderDetailsDTO.setRoleId(Integer.parseInt(leaveBean.getRoleId()));
			holderDetailsDTO.setTypeId(Integer.parseInt(leaveBean.getTypeId()));
			holderDetailsDTO.setRemarks(leaveBean.getRemarks());
			holderDetailsDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			holderDetailsDTO.setStatus(1);
			leaveBean.setResult(leaveAdministratorDAO.submitTaskHolderDetails(holderDetailsDTO));
		}
		}catch(Exception e){
		throw e;	
		}
		return leaveBean.getResult();
	}

	public String deleteTaskHolderDetails(LeaveAdministratorBean leaveBean) throws Exception{
		try{
			leaveBean.setResult(leaveAdministratorDAO.deleteTaskHolderDetails(leaveBean));
		}catch(Exception e){
			throw e;
		}
		return leaveBean.getResult();
	}

	public String manageTypeDesigDetails(LeaveAdministratorBean leaveBean) throws Exception{
		try{
			//leaveBean.setResult(leaveAdministratorDAO.checkDuplicateTypeDesig(leaveBean));
			//if (!CPSUtils.compareStrings(leaveBean.getResult(), CPSConstants.DUPLICATE)) {
				leaveBean.setResult(leaveAdministratorDAO.saveTypeDesigDetails(leaveBean));
			//}
		}
		catch(Exception e){
			throw e;
		}
		return leaveBean.getResult();
	}
	
	public String deleteTypeDesigDetails(LeaveAdministratorBean leaveBean) throws Exception{
		try{
			leaveBean.setResult(leaveAdministratorDAO.deleteTypeDesigDetails(leaveBean));
		}catch(Exception e){
			throw e;
		}
		return leaveBean.getResult();
	}

	public LeaveAdministratorBean getDoPartRoleTaskDetails(LeaveAdministratorBean leaveBean) throws Exception{
		try{
			leaveBean.settHolderList(leaveAdministratorDAO.getTaskHolderList(leaveBean));
			//leaveBean.setDesignationList(leaveAdministratorDAO.getAssigningDesignations(leaveBean));    sridhar
			leaveBean.setAllDesignationList(leaveAdministratorDAO.getAllAssigningDesignations(leaveBean));
		}catch(Exception e){
			throw e;
		}
		return leaveBean;
	}

	public List<TaskHolderDetailsDTO> getTypeTaskHolder(LeaveAdministratorBean leaveBean) throws Exception{
		try{
		leaveBean.settHolderList(leaveAdministratorDAO.getTypeTaskHolder(leaveBean));	
		}catch(Exception e){
			throw e;
		}
		return leaveBean.gettHolderList();
	}

	public String manageTaskHolderDesignations(LeaveAdministratorBean leaveBean) throws Exception{
		try{
			leaveBean.setResult(leaveAdministratorDAO.saveTaskHolderDesignations(leaveBean));
		}catch(Exception e){
			throw e;
		}
		return leaveBean.getResult();
	}

	public List<TaskHolderDesignationsDTO> getTaskHolderDesigList(LeaveAdministratorBean leaveBean) throws Exception {
		try{
			leaveBean.setTaskHolderDesigList(leaveAdministratorDAO.getTaskHolderDesigList(leaveBean));
		}catch(Exception e){
		throw e;	
		}
		return leaveBean.getTaskHolderDesigList();
	}

	public String deleteTaskHolderDesigDetails(LeaveAdministratorBean leaveBean) throws Exception{
		try{
			leaveBean.setResult(leaveAdministratorDAO.deleteTaskHolderDesigDetails(leaveBean));
		}catch(Exception e){
		throw e;	
		}
		return leaveBean.getResult();
	}

	@SuppressWarnings("unchecked")
	public List<DesignationDTO> getEditingDesignations(LeaveAdministratorBean leaveBean) throws Exception{
		try{
		leaveBean.setDesignationList(commonDataDAO.getMasterData(CPSConstants.DESIGNATIONDTO));	
		}catch(Exception e){
			throw e;
		}
		return leaveBean.getDesignationList();
	}

	public LeaveAdministratorBean getDOPartReleaseDetails(LeaveAdministratorBean leaveBean) throws Exception {
		try{
		leaveBean.setDoPartList(leaveAdministratorDAO.getReleaseDOPartDetails(leaveBean));
			//SQLDoPartDAO dop = new SQLDoPartDAO();
		//	leaveBean.setDoPartList(dop.findAll());
		}catch(Exception e){
		throw e;	
		}
		return leaveBean;
	}

	public String manageDoPartPortal(LeaveAdministratorBean leaveBean) throws Exception{
		try{
			DoPartDTO doPartDTO=new DoPartDTO();
			doPartDTO.setId(Integer.parseInt(leaveBean.getNodeID()));
			if(leaveBean.getType().equals("r")){
				doPartDTO.setReleasedDate(leaveBean.getReleasedDate());
				doPartDTO.setReleasedBy(leaveBean.getSfID());
			}else if(leaveBean.getType().equals("a")){
				doPartDTO.setAcceptedBy(leaveBean.getSfID());
				doPartDTO.setAcceptedDate(leaveBean.getAcceptedDate());
			}
			leaveBean.setResult(leaveAdministratorDAO.saveDoPartPOrtal(leaveBean));
		}catch(Exception e){
			throw e;
		}
		return leaveBean.getResult();
	}
	
	public String dopartIIFreezeOut(LeaveAdministratorBean leaveBean) throws Exception{                  //start of dopartII freeae out
		try{
			leaveBean.setResult(leaveAdministratorDAO.dopartIIFreezeOut(leaveBean));
		}catch(Exception e){
			throw e;
		}
		return leaveBean.getResult();
	}//end

	@SuppressWarnings("unchecked")
	public List<EssModuleDTO> getEssModuleList(LeaveAdministratorBean leaveBean) throws Exception{
		try{
			leaveBean.setEssModuleList(commonDataDAO.getMasterData(CPSConstants.ESSMODULEDTO));
		}catch(Exception e){
		throw e;	
		}
			return leaveBean.getEssModuleList();
	}

	public Object getLeaveCasualitiesList(LeaveAdministratorBean leaveBean) throws Exception {
		try {
			leaveBean.setCasualitiesList(leaveAdministratorDAO.getLeaveCasualityList(leaveBean));
		} catch (Exception e) {
			throw e;
		}
		return leaveBean.getCasualitiesList();
	}

	@SuppressWarnings("unchecked")
	public LeaveAdministratorBean getdoPartCountHomeDetails(LeaveAdministratorBean leaveBean) throws Exception{
		try{
			leaveBean.setTypeList(commonDataDAO.getMasterData(CPSConstants.TYPEDETAILSDTO));
		}catch(Exception e){
		throw e;	
		}
			return leaveBean;
	}

	public LeaveAdministratorBean getdoPartCountList(LeaveAdministratorBean leaveBean) throws Exception{
		try{
			leaveBean.setDoPartList(leaveAdministratorDAO.getdoPartCount(leaveBean));
		}catch(Exception e){
		throw e;	
		}
			return leaveBean;
	}

	public List<CasualityDetailsDTO> getGazettedCasualitiesList(LeaveAdministratorBean leaveBean)throws Exception {
		try{
		leaveBean.setCasualitiesList(leaveAdministratorDAO.getGazettedCasualityList(leaveBean));
		}catch(Exception e){
			throw e;
		}
		return leaveBean.getCasualitiesList();
	}


    //sridhar
	
	public LeaveAdministratorBean getAssigningDesignationsList(LeaveAdministratorBean leaveBean) throws Exception{
		try{
			leaveBean.setDesignationList(leaveAdministratorDAO.getAssigningDesignationsList(leaveBean));
			leaveBean.setAllDesignationList(leaveAdministratorDAO.getAssignedDesignations(leaveBean));
		}catch(Exception e){
			throw e;
		}
		return leaveBean;
	}

	public List<DesignationDTO> getAssignedDesignations(String gazType) throws Exception{
		List<DesignationDTO> list=null;
		try{
			list=leaveAdministratorDAO.getAssignedDesignations(gazType);
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	
	public String deleteAssignedTypeDesig(LeaveAdministratorBean leaveBean) throws Exception{
		String message=null;
		try{
			message=leaveAdministratorDAO.deleteAssignedTypeDesig(leaveBean.getTaskHolderId());
		}catch(Exception e){
			throw e;
		}
		return message;
	}

	public LeaveAdministratorBean getLeaveBusinessRules(LeaveAdministratorBean leaveAdminBean) throws Exception { // Added by Naresh
		try {
			//leaveAdminBean.setRequestTypes(leaveAdministratorDAO.getRequestTypes());
			leaveAdminBean.setLeaveTypeList(leaveAdministratorDAO.getAllLeaveTypes());
			leaveAdminBean.setBusinessRules(leaveAdministratorDAO.getLeaveBusinessRulesList());
		} catch (Exception e) {
			throw e;
		}
		return leaveAdminBean;
	} // End
	
	public LeaveAdministratorBean submitLeaveBusinessRules(LeaveAdministratorBean leaveAdminBean) throws Exception {
		try {
			leaveAdminBean.setMessage(leaveAdministratorDAO.saveLeaveBusinessRules(leaveAdminBean));
			leaveAdminBean.setBusinessRules(leaveAdministratorDAO.getLeaveBusinessRulesList());
		} catch (Exception e) {
			throw e;
		}
		return leaveAdminBean;
	}
	
	public LeaveAdministratorBean deleteBusinessRule(LeaveAdministratorBean leaveAdminBean) throws Exception {
		try {
			leaveAdminBean.setMessage(leaveAdministratorDAO.deleteBusinessRule(leaveAdminBean.getPk()));
			leaveAdminBean.setBusinessRules(leaveAdministratorDAO.getLeaveBusinessRulesList());
		} catch(Exception e) {
			throw e;
		}
		return leaveAdminBean;
	}

	public LeaveAdministratorBean getEmplyeeLeavesSave(
			LeaveAdministratorBean leaveBean)  throws Exception  {
		try{
		leaveAdministratorDAO.getEmplyeeLeavesSave(leaveBean);
		leaveBean.setMessage("success");
		} catch(Exception e){
			throw e;
		}
		return leaveBean;
	}
}
