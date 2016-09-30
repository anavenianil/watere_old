package com.callippus.web.business.incometax;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.PayChallanDetailsDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.incometax.IncomeTaxMasterBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.incometax.IIncomeTaxDAO;
import com.callippus.web.dao.paybill.IPayBillDAO;
import com.callippus.web.incometax.dto.IncomeTaxConfigDTO;
import com.callippus.web.incometax.dto.IncomeTaxDurationDTO;
import com.callippus.web.incometax.dto.IncomeTaxRentDTO;
import com.callippus.web.incometax.dto.IncomeTaxRunStatusDTO;
import com.callippus.web.incometax.dto.IncomeTaxSavingsDTO;
import com.callippus.web.incometax.dto.IncomeTaxSectionDTO;
import com.callippus.web.incometax.dto.IncomeTaxSectionMappingDTO;
import com.callippus.web.incometax.dto.IncomeTaxStageDTO;
import com.callippus.web.incometax.dto.ItarrearsDto;
import com.callippus.web.incometax.dto.PayFinYearDTO;
import com.callippus.web.incometax.dto.PrUpdateAllwDTO;
import com.callippus.web.paybill.dto.PayBillDTO;
import com.callippus.web.paybill.dto.PayScaleDesignationDTO;

@Service
public class IncomeTaxBusiness {
	@Autowired
	private IIncomeTaxDAO iIncomeTaxDAO;

	@Autowired
	private IPayBillDAO iPayBillDAO;

	public String setFinYearMasterDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception
	{
		String message = "";
		try
		{
			PayFinYearDTO finYearDTO = new PayFinYearDTO();
			if (CPSUtils.compareStrings(incomeTaxMasterBean.getType(), CPSConstants.EDIT))
				finYearDTO.setId(Integer.parseInt(incomeTaxMasterBean.getPk()));
			finYearDTO.setFyFrom(Integer.parseInt(incomeTaxMasterBean.getFYearFrom()));
			finYearDTO.setFyTo(Integer.parseInt(incomeTaxMasterBean.getFYearTo()));
			finYearDTO.setFyToFrom(incomeTaxMasterBean.getFYearFrom() + "-" + incomeTaxMasterBean.getFYearTo());
			finYearDTO.setCreatedBy(incomeTaxMasterBean.getSfID());
			finYearDTO.setCreationDate(CPSUtils.getCurrentDate());
			finYearDTO.setLastModifiedBy(incomeTaxMasterBean.getSfID());
			finYearDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			finYearDTO.setStatus(1);
			message = iIncomeTaxDAO.insertFinYearMasterDetails(finYearDTO);
			if (CPSUtils.compareStrings(incomeTaxMasterBean.getType(), CPSConstants.EDIT))
				message = CPSConstants.UPDATE;
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String submitIncomeTaxDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception 
	{
		try 
		{
			if (incomeTaxMasterBean.getType() == "old")
				incomeTaxMasterBean.setMessage(iIncomeTaxDAO.deleteIncomeTaxDetails(incomeTaxMasterBean));
			JSONObject mainJSON = new JSONObject(incomeTaxMasterBean.getStages());
			for (int i = 0; i < mainJSON.length(); i++) 
			{
				JSONObject inJSON = (JSONObject) mainJSON.get(String.valueOf(i));
				IncomeTaxStageDTO taxStage = new IncomeTaxStageDTO();
				taxStage.setTaxableFlag(incomeTaxMasterBean.getTaxableflag());
				taxStage.setSelectedFYear(incomeTaxMasterBean.getSelectedFYear());
				taxStage.setAge(incomeTaxMasterBean.getAge());
				taxStage.setStageId(inJSON.getInt("stageId"));
				taxStage.setFrom(inJSON.getInt("from"));
				taxStage.setTo(inJSON.getInt("to"));
				taxStage.setTax(inJSON.getInt("tax"));
				taxStage.setRebate(inJSON.getInt("rebate"));
				taxStage.setSurcharge(inJSON.getInt("surcharge"));
				taxStage.setEducationCess(inJSON.getInt("cess"));
				taxStage.setSecHigherEducation(inJSON.getInt("secondaryCess"));
				taxStage.setCreatedBy(incomeTaxMasterBean.getSfID());
				taxStage.setCreationDate(CPSUtils.getCurrentDate());
				taxStage.setLastModifiedBy(incomeTaxMasterBean.getSfID());
				taxStage.setLastModifiedDate(CPSUtils.getCurrentDate());
				taxStage.setStatus(1);
				incomeTaxMasterBean.setMessage(iIncomeTaxDAO.submitIncomeTaxDetails(taxStage));
			}
		} catch (Exception e) 
		{
			throw e;
		}
		return incomeTaxMasterBean.getMessage();
	}

	public IncomeTaxMasterBean getincomeTaxStagelist(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception {
		List<IncomeTaxStageDTO> keyList = null;
		try {
			keyList = iIncomeTaxDAO.getincomeTaxStagelist(incomeTaxMasterBean);
			incomeTaxMasterBean.setIncomeTaxStageList(keyList);
			if(keyList.size() != 0)
			{
				if(!CPSUtils.isNullOrEmpty(keyList.get(0).getDescription()))
					incomeTaxMasterBean.setMessage(keyList.get(0).getDescription());
			}
		} catch (Exception e) {
			throw e;
		}
		return incomeTaxMasterBean;
	}

	public List<PayFinYearDTO> getFinYearList() throws Exception {
		List<PayFinYearDTO> arrList = null;
		try {
			arrList = iIncomeTaxDAO.getFinYearList();

		} catch (Exception e) {
			throw e;
		}
		return arrList;
	}
	
	//start of saveChallanDetails(P)
	public String saveChallanDetails(IncomeTaxMasterBean incomeTaxMasterBean)throws Exception{
	String message=null;
	PayChallanDetailsDTO payChallanDTO=null;
	try{
		payChallanDTO=new PayChallanDetailsDTO();
		payChallanDTO.setItTxnId(Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()));
		payChallanDTO.setChallanDate(CPSUtils.convertStringToDate(incomeTaxMasterBean.getChallanDate()));
		payChallanDTO.setChallanNo(incomeTaxMasterBean.getChallanNo());
		payChallanDTO.setBankBSRNo(incomeTaxMasterBean.getBankBSRNo());
		payChallanDTO.setChallanAmt(incomeTaxMasterBean.getChallanAmount());
		payChallanDTO.setChallanRemarks(incomeTaxMasterBean.getChallanRemarks());
		payChallanDTO.setStatus(1);
		payChallanDTO.setSfid(incomeTaxMasterBean.getSearchSfid());
		payChallanDTO.setCreatedBy(incomeTaxMasterBean.getSfID());
		payChallanDTO.setCreationDate(CPSUtils.convertStringToDate(CPSUtils.getCurrentDate()));
		payChallanDTO.setLastModifiedBy(incomeTaxMasterBean.getSfID());
		payChallanDTO.setLastModifiedDate(CPSUtils.convertStringToDate(CPSUtils.getCurrentDate()));

		message=iIncomeTaxDAO.saveChallanDetails(payChallanDTO,incomeTaxMasterBean.getSfID());
		
	} catch(Exception e){
		throw e;
	}
	return message;
		
	}
	
	
	//end of saveChallanDetails
	
	
	//start of getSaveChallanDetails(P)
	public IncomeTaxMasterBean getSaveChallanDetails(IncomeTaxMasterBean incomeTaxMasterBean)throws Exception{
		
		PayChallanDetailsDTO payChallanDTO=null;
		try {
			payChallanDTO = iIncomeTaxDAO.getSaveChallanDetails(incomeTaxMasterBean.getSearchSfid(),incomeTaxMasterBean.getSelectedFYear());
			if(!CPSUtils.isNullOrEmpty(payChallanDTO)){
				incomeTaxMasterBean.setChallanAmount(payChallanDTO.getChallanAmt());
				incomeTaxMasterBean.setChallanNo(payChallanDTO.getChallanNo());
				incomeTaxMasterBean.setChallanDate(CPSUtils.formatDate( payChallanDTO.getChallanDate()));
				incomeTaxMasterBean.setBankBSRNo(payChallanDTO.getBankBSRNo());
				
				
			}
		} catch (Exception e) {
			throw e;
		}
		return incomeTaxMasterBean;
	}
	
	//end of getSaveChallanDetails(P)
	
	
//nagendra.v
	public String deleteIncomeTaxDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception {
		String message = "";
		try {

			message = iIncomeTaxDAO.deleteIncomeTaxDetails(incomeTaxMasterBean);

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String deleteFinYearDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception {

		String message = "";
		try {
			int id = Integer.parseInt(incomeTaxMasterBean.getPk());
			message = iIncomeTaxDAO.deleteFinYearDetails(id);

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	@SuppressWarnings("deprecation")
	public String submitArrearsDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception
	{
		String message = "";
		try {
			DateFormat formatter = new SimpleDateFormat("MMM-yyyy");
			String fromDate = formatter.format(incomeTaxMasterBean.getFromDate());
			String toDate = formatter.format(incomeTaxMasterBean.getToDate());

			if (incomeTaxMasterBean.getFromDate().getYear() >= incomeTaxMasterBean.getToDate().getYear()
					&& incomeTaxMasterBean.getFromDate().getMonth() > incomeTaxMasterBean.getToDate().getMonth()) {
				message = CPSConstants.LESSDATE;
				incomeTaxMasterBean.setRemarks(" From(" + fromDate + ") Date must Be before Or Equals to To(" + toDate + ") Date");
			} else {
				message = iIncomeTaxDAO.submitArrearsDetails(incomeTaxMasterBean);
			}
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String submitSavingsDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception
	{
		String message = "";
		try 
		{
			IncomeTaxSavingsDTO incomeTaxSavingsDTO = new IncomeTaxSavingsDTO();
			if (CPSUtils.compareStrings(incomeTaxMasterBean.getType(), CPSConstants.EDIT))
			incomeTaxSavingsDTO.setId(Integer.parseInt(incomeTaxMasterBean.getPk()));
			incomeTaxSavingsDTO.setsName(incomeTaxMasterBean.getsName());
			incomeTaxSavingsDTO.setsType(incomeTaxMasterBean.getsType());
			incomeTaxSavingsDTO.setSectionId(Integer.parseInt(incomeTaxMasterBean.getSectionId()));
			incomeTaxSavingsDTO.setYearFrom(Integer.parseInt(incomeTaxMasterBean.getValidFrom()));
			incomeTaxSavingsDTO.setYearTo(Integer.parseInt(incomeTaxMasterBean.getValidTo()));
			incomeTaxSavingsDTO.setCreatedBy(incomeTaxMasterBean.getSfID());
			incomeTaxSavingsDTO.setCreationDate(CPSUtils.getCurrentDate());
			incomeTaxSavingsDTO.setLastModifiedBy(incomeTaxMasterBean.getSfID());
			incomeTaxSavingsDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			incomeTaxSavingsDTO.setStatus(1);
			/*incomeTaxSavingsDTO.setId(Integer.parseInt(incomeTaxMasterBean.getPk()));*/
			message = iIncomeTaxDAO.submitSavingsDetails(incomeTaxSavingsDTO);
			if(CPSUtils.compareStrings(incomeTaxMasterBean.getType(), CPSConstants.EDIT))
				message = CPSConstants.UPDATE;
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String insertSectionDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception
	{
		String message = "";
		try
		{
			IncomeTaxSectionDTO incomeTaxSectionDTO = new IncomeTaxSectionDTO();
			if (CPSUtils.compareStrings(incomeTaxMasterBean.getType(), CPSConstants.EDIT))
				incomeTaxSectionDTO.setId(Integer.parseInt(incomeTaxMasterBean.getPk()));
			incomeTaxSectionDTO.setSecName(incomeTaxMasterBean.getSecName());
			incomeTaxSectionDTO.setSecOrderNo(incomeTaxMasterBean.getSectionOrderNo());
			incomeTaxSectionDTO.setYearFrom(Integer.parseInt(incomeTaxMasterBean.getValidFrom()));
			incomeTaxSectionDTO.setYearTo(Integer.parseInt(incomeTaxMasterBean.getValidTo()));
			incomeTaxSectionDTO.setCreationDate(CPSUtils.getCurrentDate());
			incomeTaxSectionDTO.setCreatedBy(incomeTaxMasterBean.getSfID());
			incomeTaxSectionDTO.setLastModifiedBy(incomeTaxMasterBean.getSfID());
			incomeTaxSectionDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			incomeTaxSectionDTO.setStatus(1);
			message = iIncomeTaxDAO.submitSectionDetails(incomeTaxSectionDTO);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public List<IncomeTaxSavingsDTO> getSavingsList() throws Exception {
		List<IncomeTaxSavingsDTO> keyList = null;
		List<PayFinYearDTO>  finYearList = null;
		try {
			finYearList = iIncomeTaxDAO.getFinYearList();
			keyList = iIncomeTaxDAO.getSavingsList();
			for(IncomeTaxSavingsDTO savingsDTO : keyList)
			{
				for(PayFinYearDTO finYearDTO : finYearList)
				{
					if(savingsDTO.getYearFrom() == finYearDTO.getId())
						savingsDTO.setFromYear(finYearDTO.getFyFrom() + " - " + String.valueOf(finYearDTO.getFyTo()).substring(2));
					if(savingsDTO.getYearTo() == finYearDTO.getId())
						savingsDTO.setToYear(finYearDTO.getFyFrom() + " - " + String.valueOf(finYearDTO.getFyTo()).substring(2));
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return keyList;
	}
	
	public List<IncomeTaxSavingsDTO> getSavingsListWithSections() throws Exception
	{
		List<PayFinYearDTO>  finYearList = null;
		List<IncomeTaxSavingsDTO> keyList = null;
		try
		{
			finYearList = iIncomeTaxDAO.getFinYearList();
			keyList = iIncomeTaxDAO.getSavingsListWithSections();
			for(IncomeTaxSavingsDTO savingsDTO : keyList)
			{
				for(PayFinYearDTO finYearDTO : finYearList)
				{
					if(savingsDTO.getYearFrom() == finYearDTO.getId())
						savingsDTO.setFromYear(finYearDTO.getFyFrom() + " - " + String.valueOf(finYearDTO.getFyTo()).substring(2));
					if(savingsDTO.getYearTo() == finYearDTO.getId())
						savingsDTO.setToYear(finYearDTO.getFyFrom() + " - " + String.valueOf(finYearDTO.getFyTo()).substring(2));
				}
			}
		}catch(Exception e)
		{
			throw e;
		}
		return keyList;
	}
	//nagendra.v
	public String deleteSavingsDetails(IncomeTaxMasterBean incomeTaxMasterBean)
			throws Exception {

		String message = "";
		Integer sectionscount = null;
		try {
			int id = Integer.parseInt(incomeTaxMasterBean.getPk());
					try {
						sectionscount = iIncomeTaxDAO.chekchild(incomeTaxMasterBean);
					} catch (Exception e) {
						e.printStackTrace();
					}
			if (sectionscount.equals(0)) {
				message = iIncomeTaxDAO.deleteSavingsDetails(id);
			} else {
				message = CPSConstants.UNABLETODELETE1;
			}

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public List<IncomeTaxSavingsDTO> getConfigList() throws Exception {
		List<IncomeTaxSavingsDTO> keyList = null;
		try {

			keyList = iIncomeTaxDAO.getConfigList();

		} catch (Exception e) {
			throw e;
		}
		return keyList;
	}

	public String submitConfigDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception 
	{
		String checkProjection = "";
		List<IncomeTaxConfigDTO> configList = null;
		boolean checkObject = false;
		try 
		{
			configList = new ArrayList<IncomeTaxConfigDTO>();
			JSONObject mainJSON = new JSONObject(incomeTaxMasterBean.getConfigList());
			for (int i = 0; i < mainJSON.length(); i++) 
			{
				JSONObject inJSON = (JSONObject) mainJSON.get(String.valueOf(i));
				checkProjection = iIncomeTaxDAO.validateProjectionDetails(inJSON.getInt("savingsTypeId"));
				if (CPSUtils.compareStrings(checkProjection, CPSConstants.YES)) 
					checkObject = (CPSUtils.isNullOrEmpty(inJSON.getString("projection")) 
							&& CPSUtils.isNullOrEmpty(inJSON.getString("actual"))
							&& CPSUtils.isNullOrEmpty(inJSON.getString("remarks")) 
							&& CPSUtils.compareStrings(inJSON.getString("submissionFlag"), "false"));
				else 
					checkObject = (CPSUtils.isNullOrEmpty(inJSON.getString("actual")) 
							&& CPSUtils.isNullOrEmpty(inJSON.getString("remarks")) 
							&& CPSUtils.compareStrings(inJSON.getString("submissionFlag"), "false"));

				if (!checkObject || CPSUtils.compareStrings(CPSConstants.YES, (iIncomeTaxDAO.getConfigRowDetails(inJSON.getInt("Id"),
								incomeTaxMasterBean.getSfID(), incomeTaxMasterBean.getSelectedFYear())))) 
				{
					IncomeTaxConfigDTO configDTO = new IncomeTaxConfigDTO();
					configDTO.setSfID(incomeTaxMasterBean.getSearchSfid());
					configDTO.setSelectedFYear(incomeTaxMasterBean.getSelectedFYear());
					if (CPSUtils.compareStrings(checkProjection, CPSConstants.YES)) 
					{
						if (CPSUtils.isNullOrEmpty(inJSON.getString("projection")))
							configDTO.setProjection(0);
						else
							configDTO.setProjection(Integer.parseInt(inJSON.getString("projection")));
					} 
					else
						configDTO.setProjection(0);
					if (CPSUtils.isNullOrEmpty(inJSON.getString("actual"))) 
						configDTO.setActual(0);
					else
						configDTO.setActual(Integer.parseInt(inJSON.getString("actual")));
					configDTO.setRemarks(inJSON.getString("remarks"));
					configDTO.setSubmissionFlag(inJSON.getString("submissionFlag"));
					configDTO.setSavingsTypeId(inJSON.getInt("Id"));
				//nagendra 
					configDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
					configDTO.setLastModifiedBy(incomeTaxMasterBean.getSfID());
					configDTO.setCreatedBy(incomeTaxMasterBean.getSfID());
					configDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
					configDTO.setStatus(1);
					configList.add(configDTO);
				}
			}
			incomeTaxMasterBean.setMessage(iIncomeTaxDAO.submitConfigDetails(configList));
		} catch (Exception e) {
			throw e;
		}
		return incomeTaxMasterBean.getMessage();
	}

	public String getEmpExist(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception {
		String message = "";
		try {

			message = iPayBillDAO.getEmpExist(incomeTaxMasterBean.getSearchSfid());
			if (CPSUtils.compareStrings(message, CPSConstants.NO))
				message = CPSConstants.EMPNOTEXISTS;

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public List<IncomeTaxConfigDTO> getConfigListDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception {
		List<IncomeTaxConfigDTO> keyList = null;
		try {
			keyList = (List<IncomeTaxConfigDTO>) iIncomeTaxDAO.getConfigListDetails(incomeTaxMasterBean);
		} catch (Exception e) {
			throw e;
		}
		return keyList;
	}

	public IncomeTaxMasterBean getEmpList(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception {
		EmployeeBean empBean = null;
		try {
			empBean = (EmployeeBean) iPayBillDAO.getEmployeeDetails(incomeTaxMasterBean.getSearchSfid());
			incomeTaxMasterBean.setSearchSfid(empBean.getSfid());
			incomeTaxMasterBean.setDivision(empBean.getDivisionName());
			incomeTaxMasterBean.setNameInServiceBook(empBean.getName());
			incomeTaxMasterBean.setDesignationName(empBean.getDesignationName());

		} catch (Exception e) {
			throw e;
		}
		return incomeTaxMasterBean;
	}
	public String submitTransferEmpDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception{
		String message="";
		try{
			
			message = iIncomeTaxDAO.submitTransferEmpDetails(incomeTaxMasterBean);
		}catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String getFinancialYear(int id) throws Exception {
		String finYear = "";
		List<PayFinYearDTO> finYearList = null;
		try {
			finYearList = iIncomeTaxDAO.getFinYearList();
			for (int i = 0; i < finYearList.size(); i++) {
				PayFinYearDTO finDTO = finYearList.get(i);
				if (finDTO.getId() ==id) {
					finYear = finDTO.getFyFrom()+"-"+finDTO.getFyTo();
					break;
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return finYear;
	}

	public List<IncomeTaxSectionDTO> getITSectionList() throws Exception {
		
		List<IncomeTaxSectionDTO> arrList = null;
		List<PayFinYearDTO>  finYearList = null;
		try 
		{
			finYearList = iIncomeTaxDAO.getFinYearList();
			arrList = iIncomeTaxDAO.getITSectionList();
			for(IncomeTaxSectionDTO sectionDTO : arrList)
			{
				for(PayFinYearDTO finYearDTO : finYearList)
				{
					if(sectionDTO.getYearFrom() == finYearDTO.getId())
						sectionDTO.setFromYear(finYearDTO.getFyFrom() + " - " + String.valueOf(finYearDTO.getFyTo()).substring(2));
					if(sectionDTO.getYearTo() == finYearDTO.getId())
						sectionDTO.setToYear(finYearDTO.getFyFrom() + " - " + String.valueOf(finYearDTO.getFyTo()).substring(2));
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return arrList;
	}

	
	public String deleteSectionDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception 
	{
		Integer sectionscount = null;
		String message = "";
		try 
		{
			int id = Integer.parseInt(incomeTaxMasterBean.getPk());
			try{
			 sectionscount=	iIncomeTaxDAO.chekchild(incomeTaxMasterBean);
			}catch(Exception e){
				e.printStackTrace();
			}
		if(sectionscount==0){
			message = iIncomeTaxDAO.deleteSectionDetails(id);
			}
			else{
				message= CPSConstants.UNABLETODELETE;
			}
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	@SuppressWarnings("deprecation")
	public Map<String, String> checkRentMonth(Date d1, Date d2, int finYearId) throws Exception {
		List<PayFinYearDTO> finYearList = null;
		PayFinYearDTO finDTO = null;
		Date fromDate = null;
		Date toDate = null;
		int count = 0;
		Date fRentDate = (Date) d1.clone();
		Date tRentDate = (Date) d2.clone();
		Map<String, String> map=new HashMap<String, String>();
		try {
			finYearList = iIncomeTaxDAO.getFinYearList();
			for (int i = 0; i < finYearList.size(); i++) {
				if (finYearList.get(i).getId() == finYearId) {
					finDTO = finYearList.get(i);
					break;
				}
			}
			fromDate = new Date("01-MAR-" + finDTO.getFyFrom());
			toDate = new Date("01-MAR-" + finDTO.getFyTo());
			while (fRentDate.compareTo(tRentDate) != 0) {
				while (!(fromDate.compareTo(toDate) == 0)) {
					if (fromDate.compareTo(fRentDate) == 0) {
						count++;
						break;
					}
					fromDate.setMonth(fromDate.getMonth() + 1);
				}
				fRentDate.setMonth(fRentDate.getMonth() + 1);
			}
			map.put("count", (String.valueOf(count)));
			map.put("fyear", finDTO.getFyToFrom());
		} catch (Exception e) {
			throw e;
		}
		return map;
	}

	@SuppressWarnings("deprecation")
	public String submitRentDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception {

		String message = CPSConstants.YES;
		DateFormat formatter = null;
		Date fRentDate = null;
		String sRentFromDate = "";
		Date tRentDate = null;
		String sRentToDate = "";
		int noOfMonth;
		int rentPerMonth;
		String sOriginDate = "";
		List<IncomeTaxRentDTO> rentList=null;
		Map<String, String> map=null;
		try {
			DateFormat formatter1 = new SimpleDateFormat("MMM-yyyy");
			String fromDate = formatter1.format(incomeTaxMasterBean.getRentFromMonth());
			String toDate = formatter1.format(incomeTaxMasterBean.getRentToMonth());
			if (incomeTaxMasterBean.getRentFromMonth().getYear() >= incomeTaxMasterBean.getRentToMonth().getYear()
					&& incomeTaxMasterBean.getRentFromMonth().getMonth() > incomeTaxMasterBean.getRentToMonth().getMonth()) {
				message = CPSConstants.LESSDATE;
				incomeTaxMasterBean.setRemarks(" From(" + fromDate + ") Date must Be before Or Equals to To(" + toDate + ") Date");
			} else {
				formatter = new SimpleDateFormat("dd-MMM-yy");
				sRentFromDate = formatter.format(incomeTaxMasterBean.getRentFromMonth());
				fRentDate = formatter.parse(sRentFromDate);
				sRentToDate = formatter.format(incomeTaxMasterBean.getRentToMonth());
				tRentDate = formatter.parse(sRentToDate);
				/*tRentDate.setMonth(tRentDate.getMonth() + 1);
				noOfMonth = (int) (((tRentDate.getYear() * 12) + tRentDate.getMonth()) - ((fRentDate.getYear() * 12) + fRentDate.getMonth()));
				rentPerMonth = Integer.parseInt(incomeTaxMasterBean.getRent()) / noOfMonth;
				map = checkRentMonth(fRentDate, tRentDate, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()));
				if (noOfMonth == Integer.parseInt(map.get("count"))) {
					rentList=new ArrayList<IncomeTaxRentDTO>();
					while (fRentDate.compareTo(tRentDate) != 0) {
						IncomeTaxRentDTO rentDTO = new IncomeTaxRentDTO();
						if(CPSUtils.compareStrings("edit", incomeTaxMasterBean.getType())){
							rentDTO.setId(Integer.parseInt(incomeTaxMasterBean.getPk()));
							rentDTO.setType(incomeTaxMasterBean.getType());
						}
						sOriginDate = formatter.format(fRentDate);
						//rentDTO.setRentMonth(sOriginDate);
						rentDTO.setRent(rentPerMonth);
						rentDTO.setRemarks(incomeTaxMasterBean.getRentRemarks());
						rentDTO.setSfid(incomeTaxMasterBean.getSearchSfid());
						rentDTO.setFinYearId(Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()));
						rentDTO.setStatus(1);
						rentDTO.setCreatedBy(incomeTaxMasterBean.getSfID());
						rentDTO.setLastModifiedBy(incomeTaxMasterBean.getSfID());
						rentDTO.setCreationDate(CPSUtils.getCurrentDate());
						rentDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
						rentList.add(rentDTO);
						fRentDate.setMonth(fRentDate.getMonth() + 1);
					}*/
				rentList=new ArrayList<IncomeTaxRentDTO>();
				IncomeTaxRentDTO rentDTO = new IncomeTaxRentDTO();
				if(CPSUtils.compareStrings("edit", incomeTaxMasterBean.getType())){
					rentDTO.setId(Integer.parseInt(incomeTaxMasterBean.getPk()));
					rentDTO.setType(incomeTaxMasterBean.getType());
				}
				sOriginDate = formatter.format(fRentDate);
				rentDTO.setRentFromDate(formatter.format(fRentDate));
				rentDTO.setRentToDate(formatter.format(tRentDate));
				rentDTO.setRent(Integer.parseInt(incomeTaxMasterBean.getRent()));
				rentDTO.setRemarks(incomeTaxMasterBean.getRentRemarks());
				rentDTO.setSfid(incomeTaxMasterBean.getSearchSfid());
				rentDTO.setFinYearId(Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()));
				rentDTO.setStatus(1);
				rentDTO.setCreatedBy(incomeTaxMasterBean.getSfID());
				rentDTO.setLastModifiedBy(incomeTaxMasterBean.getSfID());
				rentDTO.setCreationDate(CPSUtils.getCurrentDate());
				rentDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				rentList.add(rentDTO);
				fRentDate.setMonth(fRentDate.getMonth() + 1);
					message = iIncomeTaxDAO.submitRentDetails(rentList);
					if(CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
						incomeTaxMasterBean.setRemarks("Saved Record Successfully");
					}
					if(CPSUtils.compareStrings(message, CPSConstants.DUPLICATE)) {
						incomeTaxMasterBean.setRemarks("Other Record  Exists in these dates.");
					}
					
				/*}else {
					incomeTaxMasterBean.setMessage(CPSConstants.NOTBWMONTH);
					incomeTaxMasterBean.setRemarks("From Date and To Date must be within The Financial Year("+map.get("fyear")+")");
				}*/
			}
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	
	public List<IncomeTaxRentDTO> getITRentList(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception {
		List<IncomeTaxRentDTO> rentList = null;
		try {
			rentList = iIncomeTaxDAO.getITRentList(incomeTaxMasterBean);
		} catch (Exception e) {
			throw e;
		}
		return rentList;
	}

	public String deleteRentDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception {
		String message = "";
		try {
			message = iIncomeTaxDAO.deleteRentDetails(Integer.parseInt(incomeTaxMasterBean.getPk()));
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public List<IncomeTaxRunStatusDTO> submitITCalcDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception {
		String message = "";
		List<IncomeTaxRunStatusDTO> resultList=null;
		try {
			if (!CPSUtils.isNullOrEmpty(incomeTaxMasterBean.getSelectSfid())) {
				message = iPayBillDAO.getEmpExist(incomeTaxMasterBean.getSelectSfid());
				if (CPSUtils.compareStrings(message, CPSConstants.NO))
					message = CPSConstants.EMPNOTEXISTS;
			}
			if (!CPSUtils.compareStrings(message, CPSConstants.EMPNOTEXISTS)) {
				if (CPSUtils.compareStrings(incomeTaxMasterBean.getRunTypeFlag(), "pl")) {
					// Planned code goes here
					message = iIncomeTaxDAO.submitPlannedDetails(incomeTaxMasterBean);

				} else if (CPSUtils.compareStrings(incomeTaxMasterBean.getRunTypeFlag(), "pr")) {
					// Projected code goes here
					message = iIncomeTaxDAO.submitProjectedDetails(incomeTaxMasterBean);
				} else {
					// Actual code goes here
					message = iIncomeTaxDAO.submitActualDetails(incomeTaxMasterBean);

				}
			}
			if(CPSUtils.compareString(CPSConstants.SUCCESS, message)){
				resultList=iIncomeTaxDAO.getIncomeTaxRunStatusList();
			}
		} catch (Exception e) {
			throw e;
		}
		return resultList;
	}
	public List<PayScaleDesignationDTO> getDesignationList() throws Exception{
		List<PayScaleDesignationDTO> designationList=null;
		try{
			designationList = iIncomeTaxDAO.getDesignationList();
		}catch (Exception e) {
			throw e;
		}
		return designationList;
	}
	public String submitPUADetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception
	{
		String message = "";
		try 
		{
			PrUpdateAllwDTO PUADTO = new PrUpdateAllwDTO();
			if (CPSUtils.compareStrings(incomeTaxMasterBean.getType(), CPSConstants.EDIT))
				PUADTO.setId(Integer.parseInt(incomeTaxMasterBean.getPk()));
			PUADTO.setGradePay(Integer.parseInt(incomeTaxMasterBean.getGradePay()));
			PUADTO.setDesignationId(Integer.parseInt(incomeTaxMasterBean.getDesignationId()));
			PUADTO.setAmount(Integer.parseInt(incomeTaxMasterBean.getAmount()));
			PUADTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
			PUADTO.setLastModifiedBy(incomeTaxMasterBean.getSfID());
			PUADTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
			PUADTO.setCreatedBy(incomeTaxMasterBean.getSfID());
			PUADTO.setStatus(1);
			message = iIncomeTaxDAO.submitPUADetails(PUADTO);
			if(CPSUtils.compareStrings(incomeTaxMasterBean.getType(), CPSConstants.EDIT))
				message = CPSConstants.UPDATE;
		} catch (Exception e) {
			throw e;
		}
		return message;
}
	public List<PrUpdateAllwDTO> getPUAList() throws Exception{
		List<PrUpdateAllwDTO> puaList=null;
		try{
			puaList = iIncomeTaxDAO.getPUAList();
		}catch (Exception e) {
			throw e;
		}
		return puaList;
	}
	
	public String deletePUADetails(int id) throws Exception
	{
		String message = "";
		try{
			message = iIncomeTaxDAO.deletePUADetails(id);
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	public String checkITRunStatus(int finyearid,String runType)throws Exception{
		String message="";
		try{
			message = iIncomeTaxDAO.checkITRunStatus(finyearid,runType);
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	public String submitDBMigrationDetails(String month)throws Exception{
		String message="";
		try{
			message = iIncomeTaxDAO.submitDBMigrationDetails(month);
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	@SuppressWarnings("unchecked")
	public Map compareDBMigrationDetails(String month,String matchedFlag,IncomeTaxMasterBean incomeTaxMasterBean)throws Exception{
		Map map=null;
		try{
			map = iIncomeTaxDAO.compareDBMigrationDetails(month,matchedFlag,incomeTaxMasterBean);
		}catch (Exception e) {
			throw e;
		}
		return map;
	}
	public List<KeyValueDTO> getPayMonthList(String finYear)throws Exception{
		List<KeyValueDTO> monthList=null;
		try{
			monthList = new ArrayList<KeyValueDTO>();
			String[] st=finYear.split("-");
			Calendar cal=Calendar.getInstance();
			cal.set(Integer.parseInt(st[0]),02,01);
			DateFormat dateFormat = new SimpleDateFormat("MMM-yyyy",Locale.US);
			for(int i=0;i<12;i++){
				KeyValueDTO keyValueDTO=new KeyValueDTO();
				keyValueDTO.setKey(dateFormat.format(cal.getTime()));
				keyValueDTO.setValue(dateFormat.format(cal.getTime()));
				cal.add(Calendar.MONTH, 1);
				monthList.add(keyValueDTO);
			}
		}catch (Exception e) {
			throw e;
		}
		return monthList;
	}
	
	public IncomeTaxMasterBean getPayMonthDetailsList(IncomeTaxMasterBean incomeTaxMasterBean)throws Exception{
		PayBillDTO payBillDTO=null;
		try{
			payBillDTO = iIncomeTaxDAO.getPayMonthDetails(incomeTaxMasterBean);
			if(!CPSUtils.isNullOrEmpty(payBillDTO)){
			incomeTaxMasterBean.setGradePay((String.valueOf(payBillDTO.getGradePay())));
			incomeTaxMasterBean.setBasic((String.valueOf(payBillDTO.getBasicPay())));
			incomeTaxMasterBean.setsPay((String.valueOf(payBillDTO.getSpecialPay())));
			incomeTaxMasterBean.setfPay((String.valueOf(payBillDTO.getFpa())));
			incomeTaxMasterBean.setDa((String.valueOf(payBillDTO.getDa())));
			incomeTaxMasterBean.setHra((String.valueOf(payBillDTO.getHra())));
			incomeTaxMasterBean.setCcs((String.valueOf(payBillDTO.getCcs())));
			incomeTaxMasterBean.setTpt((String.valueOf(payBillDTO.getTpt())));
			incomeTaxMasterBean.setcMisc((String.valueOf(payBillDTO.getCrMisc())));
			incomeTaxMasterBean.setArrears("0");
			incomeTaxMasterBean.setGpf((String.valueOf(payBillDTO.getGpf())));
			incomeTaxMasterBean.setCghs(String.valueOf(payBillDTO.getCghs()));
			incomeTaxMasterBean.setCgeis(String.valueOf(payBillDTO.getCegis()));
			incomeTaxMasterBean.setpTax(String.valueOf(payBillDTO.getProfTax()));
			incomeTaxMasterBean.setiTax(String.valueOf(payBillDTO.getIncomeTax()+payBillDTO.getCess()+payBillDTO.getSecondaryCess()));
			incomeTaxMasterBean.setHba(String.valueOf(payBillDTO.getHbaLoan()));
			incomeTaxMasterBean.setPli(String.valueOf(payBillDTO.getPli()));
			incomeTaxMasterBean.setLic(String.valueOf(payBillDTO.getLic()));
			incomeTaxMasterBean.setOtBill(String.valueOf(payBillDTO.getOtBill()));
			incomeTaxMasterBean.setEolHpl(String.valueOf(payBillDTO.getEol()));
			incomeTaxMasterBean.setHdfc(String.valueOf(payBillDTO.getHdfc()));
			incomeTaxMasterBean.setGic(String.valueOf(payBillDTO.getGic()));
			incomeTaxMasterBean.setCanfin(String.valueOf(payBillDTO.getCanfin()));
			incomeTaxMasterBean.setdMisc(String.valueOf(payBillDTO.getDrMisc1()+payBillDTO.getDrMisc2()));
			incomeTaxMasterBean.setVarInc(payBillDTO.getVariableIncr());
			incomeTaxMasterBean.setTwoAddInc(payBillDTO.getTwoAddlIncr());
			incomeTaxMasterBean.setTotalCredits(payBillDTO.getTotalCredits());
			incomeTaxMasterBean.setHdGicCfin(payBillDTO.getHdGicCfin());
			incomeTaxMasterBean.setITaxRecovery(payBillDTO.getItRec());
			incomeTaxMasterBean.setLastModifiedBy(payBillDTO.getLastModifiedBy());
			incomeTaxMasterBean.setLastModifiedDate(payBillDTO.getLastModifiedTime());
			incomeTaxMasterBean.setCess(payBillDTO.getCess());
			incomeTaxMasterBean.setSecondaryCess(payBillDTO.getSecondaryCess());
			incomeTaxMasterBean.setPayRemarks(payBillDTO.getRemarks());
			}
		}catch (Exception e) {
			throw e;
		}
		return incomeTaxMasterBean;
	}
	
	public List<DesignationDTO> getEmpDesignationList() throws Exception
	{
		List<DesignationDTO> desList = null;
		try
		{
			desList = iIncomeTaxDAO.getEmpDesignationList();
		}catch (Exception e) {
			throw e;
		}
		return desList;
	}
	public String incomeTaxStatusDetails()throws Exception{
		String message="";
       try{
    	   message=iIncomeTaxDAO.incomeTaxStatusDetails();
       }catch (Exception e) {
            	throw e;
              }
        return message;
	}
	public IncomeTaxMasterBean getSectionMappingDetails(IncomeTaxMasterBean incomeTaxMasterBean)throws Exception{
		List<PayFinYearDTO> finYearDTO = null;
		List<IncomeTaxSectionMappingDTO> sectionMapDTO = null;
		try{
			incomeTaxMasterBean.setSectionList(getITSectionList());
			finYearDTO = iIncomeTaxDAO.getFinYearList();
			sectionMapDTO = iIncomeTaxDAO.getSectionMappingDetails();
			
			incomeTaxMasterBean.setFinYearList(finYearDTO);
			incomeTaxMasterBean.setSectionMappingList(sectionMapDTO);
			for(IncomeTaxSectionMappingDTO mappingDTO : sectionMapDTO)
			{
				for(PayFinYearDTO yearDTO : finYearDTO)
				{
					if(mappingDTO.getYearFrom() == yearDTO.getId())
						mappingDTO.setFromYear(yearDTO.getFyFrom() + " - " + String.valueOf(yearDTO.getFyTo()).substring(2));
					if(mappingDTO.getYearTo() == yearDTO.getId())
						mappingDTO.setToYear(yearDTO.getFyFrom() + " - " + String.valueOf(yearDTO.getFyTo()).substring(2));
				}
			}
		}
		catch (Exception e) {
        	throw e;
        }
		return incomeTaxMasterBean;
	}
	public String submitIncomeTaxSectionMappingDetails(IncomeTaxMasterBean incomeTaxMasterBean)throws Exception
	{
		String message = "";
		try
		{   
			//String keyvalue= iIncomeTaxDAO.getkey(incomeTaxMasterBean.getSecName());
			//incomeTaxMasterBean.setSectionId(keyvalue);
			message = iIncomeTaxDAO.submitIncomeTaxSectionMappingDetails(incomeTaxMasterBean);
			if(CPSUtils.compareStrings(CPSConstants.EDIT, incomeTaxMasterBean.getType()) && !CPSUtils.compareStrings(message, CPSConstants.INVALID))
				message = CPSConstants.UPDATE;
			//incomeTaxMasterBean.setSectionMappingList(iIncomeTaxDAO.getSectionMappingDetails());
		}catch (Exception e) {
        	throw e;
        }
		return message;
	}
	public String deleteIncomeTaxSectionMappingDetails(IncomeTaxMasterBean incomeTaxMasterBean)throws Exception
	{
		String message = "";
		try
		{
			message = iIncomeTaxDAO.deleteIncomeTaxSectionMappingDetails(Integer.parseInt(incomeTaxMasterBean.getPk()));
			//incomeTaxMasterBean.setSectionMappingList(iIncomeTaxDAO.getSectionMappingDetails());
		}catch (Exception e) {
        	throw e;
        }
		 return message;
	}
	public List<IncomeTaxRunStatusDTO> getIncomeTaxRunStatusList() throws Exception{
		List<IncomeTaxRunStatusDTO> keyList=null;
		try{
			keyList=iIncomeTaxDAO.getIncomeTaxRunStatusList();
		}catch (Exception e) {
        	throw e;
        }
		return keyList;
	}
	public IncomeTaxMasterBean getEditEmpList(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception{
		 List <EmployeeBean> empList = null;
		 try{
			 incomeTaxMasterBean = iIncomeTaxDAO.getEditEmpList(incomeTaxMasterBean);
		 }catch(Exception e){
			 throw e;
		 }
		 return incomeTaxMasterBean;
	}
	public IncomeTaxMasterBean getITTotals(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception{
		 try{
			 incomeTaxMasterBean = iIncomeTaxDAO.getITTotals(incomeTaxMasterBean);
		 }catch(Exception e){
			 throw e;
		 }
		 return incomeTaxMasterBean;
	}
	
	// Start: OT
	public List<Integer> getOTYearList() throws Exception {
		List<Integer> list = null;
		try
		{
			list = iIncomeTaxDAO.getYearList();
		} catch(Exception e) {
			throw e;
		}
		return list;
	}
	
	public List<KeyValueDTO> getCategoryList() throws Exception {
		List<KeyValueDTO> list = null;
		try
		{
			list = iIncomeTaxDAO.getCategoryList();
		} catch(Exception e) {
			throw e;
		}
		return list;
	}
	
	public List<KeyValueDTO> getDesignations() throws Exception {
		List<KeyValueDTO> list = null;
		try
		{
			list = iIncomeTaxDAO.getDesignations();
		} catch(Exception e) {
			throw e;
		}
		return list;
	}
	
	public IncomeTaxMasterBean getEmpOTAmount(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception {
		try
		{
			incomeTaxMasterBean.setMessage(getEmpExist(incomeTaxMasterBean));
			if(CPSUtils.compareStrings(CPSConstants.YES, incomeTaxMasterBean.getMessage())) {
				incomeTaxMasterBean.setOverTime(iIncomeTaxDAO.getOTDetails(incomeTaxMasterBean));
				if(CPSUtils.isNull(incomeTaxMasterBean.getOverTime()))
					incomeTaxMasterBean.setMessage(CPSConstants.OTNOTAPPLICABLE);
			}	
		} catch(Exception e) {
			throw e;
		}
		return incomeTaxMasterBean;
	}
	
	public IncomeTaxMasterBean getOTCalculationDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception {
		try
		{
			incomeTaxMasterBean.setOverTimeList(iIncomeTaxDAO.getOTDetailsList(incomeTaxMasterBean));
		} catch(Exception e) {
			throw e;
		}
		return incomeTaxMasterBean;
	}

	public IncomeTaxMasterBean saveOTDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception {
		try
		{
			incomeTaxMasterBean.setMessage(iIncomeTaxDAO.saveOTDetails(incomeTaxMasterBean));
			if(CPSUtils.compareStrings(CPSConstants.SUCCESS, incomeTaxMasterBean.getMessage()))
				incomeTaxMasterBean.setOverTimeList(iIncomeTaxDAO.getOTDetailsList(incomeTaxMasterBean));
		} catch(Exception e) {
			throw e;
		}
		return incomeTaxMasterBean;
	} // End: OT
	
	public String submitITDurationDetails(IncomeTaxMasterBean incomeTaxMasterBean)throws Exception{
		String message="";
		try{
			message=iIncomeTaxDAO.submitITDurationDetails(incomeTaxMasterBean);
			incomeTaxMasterBean.setMessage(message);
		}catch (Exception e) {
        	throw e;
        }
		 return message;
	}
	public List<IncomeTaxDurationDTO> getITDurationDetails() throws Exception{
		List<IncomeTaxDurationDTO> itDurationList=null;
		try{
			itDurationList=iIncomeTaxDAO.getITDurationDetails();
		}catch (Exception e) {
        	throw e;
        }
		return itDurationList;
	}
	public String deleteITDurationDetails(IncomeTaxMasterBean incomeTaxMasterBean)throws Exception{
		String message="";
		try{
			message=iIncomeTaxDAO.deleteITDurationDetails(incomeTaxMasterBean);
			incomeTaxMasterBean.setMessage(message);
		}catch (Exception e) {
        	throw e;
        }
		 return message;
	}
	//nagendra.v
	public List<IncomeTaxSavingsDTO> getItSavingSections(IncomeTaxMasterBean incomeTaxMasterBean)throws Exception{
		List<IncomeTaxSavingsDTO> savingsections=null;
 		try{
 			
 			savingsections= iIncomeTaxDAO.getItSavedSections(incomeTaxMasterBean.getYear());
			
		}catch (Exception e) {
        	throw e;
        }
		 return savingsections;
	}

	public  String getkey(IncomeTaxMasterBean incomeTaxMasterBean)throws Exception {
	   String keyvalue=null;	
  	 try {
		keyvalue=	iIncomeTaxDAO.getkey(incomeTaxMasterBean.getSectionId());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		  
		
		return keyvalue;
		// TODO Auto-generated method stub
		
	}
	//nagendra.v
	public  List<IncomeTaxSavingsDTO> getSectionName(IncomeTaxMasterBean incomeTaxMasterBean)throws Exception {
		   List<IncomeTaxSavingsDTO> keyvalue=null;	
	  	 try {
			keyvalue=iIncomeTaxDAO.getSectionName(incomeTaxMasterBean.getSavingsTypeId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			  
			
			return keyvalue;
			// TODO Auto-generated method stub
			
		}
	//nagendra.v
	public  List<IncomeTaxSectionDTO> getUniqueSectionName()throws Exception {
		   List<IncomeTaxSectionDTO> keyvalue=null;	
	  	 try {
			keyvalue=iIncomeTaxDAO.getdistinctSections();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			  
			
			return keyvalue;
			// TODO Auto-generated method stub
			
		}
	//nagendra.v
	 	public List<IncomeTaxSectionMappingDTO> getRelatedSectionById( IncomeTaxMasterBean  iMasterBean){
	 	/*	String sfid=iMasterBean.getSfID();*/
	 		IncomeTaxMasterBean incometaxmasterbean=null;
	 		List<IncomeTaxSectionMappingDTO> sectionsList=null;
	 		
	 		try {
	 		String Serniorcitizen=iIncomeTaxDAO.checkSeniorCitizen(iMasterBean.getSearchSfid());
	 			incometaxmasterbean =iIncomeTaxDAO.getEmpDetails(iMasterBean);
	 			sectionsList = iIncomeTaxDAO.getRelatedSection(incometaxmasterbean,iMasterBean.getSelectedFYear(),Serniorcitizen);
	 		     if(sectionsList.equals(null)|| sectionsList==null){
	 		    	String  message="Section are not configured for this Sfid";
	 		     }
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	 		
	 		
	 		
	 		
			return sectionsList;
	 		
	 	}
	 	
	 	public  List<ItarrearsDto> getITarrears(IncomeTaxMasterBean incometaxmasterbean){
	 		 
	 		 List<ItarrearsDto> arrears=null;
	 		
	 		try {
	 			//iIncomeTaxDAO.saveItArrears(incometaxmasterbean);
	 			arrears=iIncomeTaxDAO.getItarrears(incometaxmasterbean);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
	 		
	 		
			return arrears;
	 		
	 	}
	 	
	 	
	 	public String  saveItarrear(IncomeTaxMasterBean incometaxmasterbean){
		   
	 		String msg=null;
	 		
	 		try {
	 		  msg=	iIncomeTaxDAO.saveItArrears(incometaxmasterbean);
				
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
	 		
	 		
	 		return msg;
	 		
	 	}
	 	
	
	
}