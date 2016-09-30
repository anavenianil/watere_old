package com.callippus.web.business.hindi;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.hindi.HindiIncentiveBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.dao.hindi.IHindiIncentive;
import com.callippus.web.hindi.dto.EmpKeyValueDTO;
import com.callippus.web.hindi.dto.ExamDTO;
import com.callippus.web.hindi.dto.HindiCashAwardDTO;
import com.callippus.web.hindi.dto.HindiEmpResultDTO;
import com.callippus.web.hindi.dto.HindiEmployeeDTO;
import com.callippus.web.hindi.dto.HindiExamConfigDTO;
import com.callippus.web.hindi.dto.HindiIncentiveDetailsDTO;
import com.callippus.web.hindi.dto.HindiNominationDTO;

@Service
public class HindiIncentiveBusiness {
	String message = "";
	
	@Autowired IHindiIncentive hindiDAO;
	
	public String getCurrentRunMonth() throws Exception
	{
		String runMonth = "";
			try
			{
				runMonth = hindiDAO.getCurrentRunMonth();
				runMonth += " Not started!";
			}catch(Exception e)
			{
				throw e;
			}
		return runMonth;
	}
	
	@SuppressWarnings("unchecked")
	public List getExamsList() throws Exception
	{
		List<ExamDTO> examsList = null;
		try
		{
			examsList = hindiDAO.getExamsList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return examsList;
	}
	
	public String saveExams(HindiIncentiveBean hindiBean) throws Exception
	{
	
		
		ExamDTO examDTO = new ExamDTO();
		try {
			// edit the existing row
			if (!hindiBean.getPk().equals("")) {
				List<ExamDTO> examDuplicateChecklist = hindiDAO.examDuplicateChecklist(hindiBean);
				if(examDuplicateChecklist.size()>0){
					for(int i=0;i<examDuplicateChecklist.size();i++){
						int id = examDuplicateChecklist.get(i).getExamId();
						if(Integer.parseInt(hindiBean.getPk()) == id){
							examDTO.setExamId(Integer.parseInt(hindiBean.getPk()));
							break;
						}else{
							message = CPSConstants.DUPLICATE;
							return message;
						}
					}
				}else if(examDuplicateChecklist.size()==0){
					examDTO.setExamId(Integer.parseInt(hindiBean.getPk()));
				}
				
				examDTO.setExamName(hindiBean.getExamName());
				examDTO.setTotalMarks(Integer.parseInt(hindiBean.getTotalMarks()));
				examDTO.setDescription(hindiBean.getDescription());
				examDTO.setStatus("1");

			}
			// when insert new row
			else {
				List<ExamDTO> examsList = hindiDAO.getExamsList();
				if (examsList.size() > 0) {
					for (int i = 0; i < examsList.size(); i++) {
						if (examsList.get(i).getExamName().replaceAll(" ","").equalsIgnoreCase(hindiBean.getExamName().replaceAll(" ",""))) {
							message = CPSConstants.DUPLICATE;
							return message;

						}
					}
				}
				examDTO.setExamName(hindiBean.getExamName());
				examDTO.setTotalMarks(Integer.parseInt(hindiBean.getTotalMarks()));
				examDTO.setDescription(hindiBean.getDescription());
				examDTO.setStatus("1");
			}

			message = hindiDAO.saveExamData(examDTO);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return message;
	}
	public String deleteExam(HindiIncentiveBean hindiBean) throws Exception
	{
		message = hindiDAO.deleteExam(hindiBean);
		return message;
	}
	
	@SuppressWarnings("unchecked")
	public List getEmployeeList() throws Exception 
	{
		List<EmpKeyValueDTO> empList = null;
		try
		{
			empList = hindiDAO.getEmployeeList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return empList;
	}
	
	public List<KeyValueDTO> getSfidList() throws Exception
	{ 
		List<KeyValueDTO> sfidList = null;
		try
		{
			sfidList = hindiDAO.getSfidList();
		}catch(Exception e) {
			throw e;
		}
		return sfidList;
	}
	@SuppressWarnings("unchecked")
	   public List getCheckExamsList(HindiIncentiveBean hindiBean) throws Exception
	      {
	   	   List<KeyValueDTO> checkExamslist = null;
	   	   Session session = null;
	   	   try{
	   		checkExamslist = hindiDAO.getCheckExamsList(hindiBean);
	   		if(checkExamslist != null && checkExamslist.size()==0){
	   			checkExamslist = null;
	   		}
	   	   }
	   	   catch(Exception e)
	   	   {
	   		 e.printStackTrace();  
	   		 throw e;
	   	   }
	   	   return checkExamslist;
	      }
	@SuppressWarnings("unchecked")
	public String getIncentiveAmount(HindiIncentiveBean hindiBean) throws Exception
	{
		String incentiveAmount = "";
		List<KeyValueDTO> elgEmp = null;
		try
		{
			elgEmp = hindiDAO.getEligibleEmpForIncentives(hindiBean);
			if(elgEmp.size() > 0)
			{
				incentiveAmount = hindiDAO.getIncentiveAmount(hindiBean);
				//incentiveAmount = String.valueOf(Integer.parseInt(incentiveAmount)*Integer.parseInt(elgEmp.get(0).getKey()));
			}
			else
				incentiveAmount = "0";
		}catch(Exception e) {
			throw e;
		}
		return incentiveAmount;
	}
	@SuppressWarnings("unchecked")
	public List getEmployeeDetailsList() throws Exception
	{
		List<HindiEmployeeDTO> empDetailsList = null;
		try
		{
			empDetailsList = hindiDAO.getEmployeeDetailsList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return empDetailsList;
	}
	public String saveEmployeeDetails(HindiIncentiveBean hindiBean) throws Exception
	{
		HindiEmployeeDTO employeeDTO = new HindiEmployeeDTO();
		
		try {
			//insert new row
			//if(!hindiBean.getType().equals("1")){
			if(hindiBean.getPk().equals("")){
				List<HindiEmployeeDTO> empDuplicateCheckList = hindiDAO.employeeDuplicateCheckList(hindiBean);
				if(empDuplicateCheckList.size()>0){
					message = CPSConstants.DUPLICATE;
					return message;
				}
				else{
					employeeDTO.setSfid(hindiBean.getSfid());
					//employeeDTO.setMothertongue(hindiBean.getMothertongue());
					employeeDTO.setRemarks(hindiBean.getRemarks());
					employeeDTO.setStatus("1");
					if(hindiBean.getNonEligibleExamId().length()>0){
					String[] nonEligibleIds = hindiBean.getNonEligibleExamId().split(",");
					if(nonEligibleIds.length>0){
						employeeDTO.setNonEligibleExamId(1);
						hindiDAO.saveNonEligibleEmpExamData(hindiBean);			
						
					}
					}else{
						hindiDAO.deleteNonEligibleEmpExamData(hindiBean);	
						employeeDTO.setNonEligibleExamId(0);
					}
					if(hindiBean.getEligibleExamId().length()>0){
						String[] eligibleIds = hindiBean.getEligibleExamId().split(",");
							if(eligibleIds.length>0){
								employeeDTO.setEligibleExamId(1);
								hindiDAO.saveEligibleEmpExamData(hindiBean);			
								
							  }
						
						 }else{
							 hindiDAO.deleteEligibleEmpExamData(hindiBean);	
								employeeDTO.setEligibleExamId(0);
								
						 }
					message = hindiDAO.saveEmployeeData(employeeDTO);
					message = CPSConstants.SUCCESS;
					
				}
			}
			//edit existing row
			else if(!hindiBean.getPk().equals("")){
				List<HindiEmployeeDTO> empDuplicateCheckList = hindiDAO.employeeDuplicateCheckList(hindiBean);
				if(empDuplicateCheckList.size()>0){
					for(int i=0;i<empDuplicateCheckList.size();i++){
						String id=empDuplicateCheckList.get(i).getSfid();
						if(hindiBean.getPk().equals(id)){
							employeeDTO.setSfid(hindiBean.getPk());
							break;
						}else{
							message = CPSConstants.DUPLICATE;
						    return message;
						}
					}
					
				}else if(empDuplicateCheckList.size()==0){
					employeeDTO.setSfid(hindiBean.getPk());
				}
				//employeeDTO.setSfid(hindiBean.getSfid());
				//employeeDTO.setMothertongue(hindiBean.getMothertongue());
				employeeDTO.setRemarks(hindiBean.getRemarks());
				employeeDTO.setStatus("1");
				if(hindiBean.getNonEligibleExamId().length()>0){
				String[] nonEligibleIds = hindiBean.getNonEligibleExamId().split(",");
					if(nonEligibleIds.length>0){
						employeeDTO.setNonEligibleExamId(1);
						hindiDAO.saveNonEligibleEmpExamData(hindiBean);			
						
					  }
				
				 }else{
						hindiDAO.deleteNonEligibleEmpExamData(hindiBean);	
						employeeDTO.setNonEligibleExamId(0);
						
					}
				if(hindiBean.getEligibleExamId().length()>0){
					String[] eligibleIds = hindiBean.getEligibleExamId().split(",");
						if(eligibleIds.length>0){
							employeeDTO.setEligibleExamId(1);
							hindiDAO.saveEligibleEmpExamData(hindiBean);			
							
						  }
					
					 }else{
						 hindiDAO.deleteEligibleEmpExamData(hindiBean);	
							employeeDTO.setEligibleExamId(0);
							
					 }
			
				message = hindiDAO.saveEmployeeData(employeeDTO);
				message = CPSConstants.UPDATE;
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return message;
	}
	@SuppressWarnings("unchecked")
	public List getNonEligibleEmpExamList() throws Exception
	{
		List<HindiNominationDTO> nonEligibleEmpExamList = null;
		try{
			nonEligibleEmpExamList = hindiDAO.getNonEligibleEmpExamList();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return nonEligibleEmpExamList;
	}
	public String deleteEmployeeDetails(HindiIncentiveBean hindiBean) throws Exception
	{		
	
		message = hindiDAO.deleteEmployeeData(hindiBean);	
	return message;	
	}
	
	@SuppressWarnings("unchecked")
	public List getCategoryList() throws Exception
	{
		List<KeyValueDTO> categoryList = null;
		try
		{
			categoryList = 	hindiDAO.getCategorylist();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return categoryList;
	}
	@SuppressWarnings("unchecked")
	public List getDeselectDesignationList(HindiIncentiveBean hindiBean) throws Exception
	{
		List<KeyValueDTO> deselectDesignationList = null;
		try
		{
			deselectDesignationList = hindiDAO.getDeselectDesignationList(hindiBean);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return deselectDesignationList;
	}
	
	public String saveExamConfigDetails(HindiIncentiveBean hindiBean) throws Exception
	{
			try{
		message = hindiDAO.saveExamConfigDetails(hindiBean);
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}
		return message;
		
	}
	@SuppressWarnings("unchecked")
	public List getExamConfigDetails() throws Exception
	{
		List<HindiExamConfigDTO> examConfigDetailsList = null;
		try{
			examConfigDetailsList = hindiDAO.getExamConfigDetails();
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return examConfigDetailsList;
	}
	@SuppressWarnings("unchecked")
	public List getSelectDesignationList(HindiIncentiveBean hindiBean) throws Exception
	{
		List<KeyValueDTO> selectDesignationList = null;
		try
		{
			selectDesignationList = hindiDAO.getSelectDesignationList(hindiBean);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return selectDesignationList;
	}
	public String deleteExamConfigDetails(HindiIncentiveBean hindiBean) throws Exception
	{
		message = hindiDAO.deleteExamConfigDetails(hindiBean);
	    return message;
	}
	
	public String saveCashAwardDetails(HindiIncentiveBean hindiBean) throws Exception
	{
		HindiCashAwardDTO cashAwardDTO =new HindiCashAwardDTO();
		try{
		//edit the existing row
		if(!hindiBean.getPk().equals("")){	
			List<HindiCashAwardDTO> cashAwardDuplicateCheckList = hindiDAO.cashAwardDuplicateCheckList(hindiBean);
			if(cashAwardDuplicateCheckList.size()>0){
				for(int i=0;i<cashAwardDuplicateCheckList.size();i++){
					int id = cashAwardDuplicateCheckList.get(i).getId();
					if(Integer.parseInt(hindiBean.getPk()) == id){
						cashAwardDTO.setId(Integer.parseInt(hindiBean.getPk()));
						break;
					}else{
						message = CPSConstants.DUPLICATE;
						return message;
					}
				}
				
			}else if(cashAwardDuplicateCheckList.size()==0){
				cashAwardDTO.setId(Integer.parseInt(hindiBean.getPk()));
			}	
			cashAwardDTO.setExamId(hindiBean.getExamId());
			cashAwardDTO.setLowerPercentage(hindiBean.getLowerPercentage());
			cashAwardDTO.setUpperPercentage(hindiBean.getUpperPercentage());
			cashAwardDTO.setCashAwardAmount(hindiBean.getCashAwardAmount());
			cashAwardDTO.setStatus("1");
			
		}
		//insert new row
		else{			
			List<HindiCashAwardDTO> cashAwardDuplicateCheckList = hindiDAO.cashAwardDuplicateCheckList(hindiBean);
			if(cashAwardDuplicateCheckList.size()==0){					
				cashAwardDTO.setExamId(hindiBean.getExamId());
				cashAwardDTO.setLowerPercentage(hindiBean.getLowerPercentage());
				cashAwardDTO.setUpperPercentage(hindiBean.getUpperPercentage());
				cashAwardDTO.setCashAwardAmount(hindiBean.getCashAwardAmount());
				cashAwardDTO.setStatus("1");
				
				message = hindiDAO.saveCashAwardData(cashAwardDTO);	
				return message;
					}
				
			message = CPSConstants.DUPLICATE;
			return message;
			
		   }
		
		
		message = hindiDAO.saveCashAwardData(cashAwardDTO);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return message;
     }
	@SuppressWarnings("unchecked")
	public List getCashAwardDetailsList() throws Exception
	{
		List<HindiCashAwardDTO> hindiCashAwardList = null;
		try{
			hindiCashAwardList = hindiDAO.getCashAwardDetailsList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return hindiCashAwardList;
	}
	public String deleteCashAwardDetails(HindiIncentiveBean hindiBean) throws Exception
	{
		try{
		message = hindiDAO.deleteCashAwardDetails(hindiBean);	
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return message;	
	}
	
	@SuppressWarnings("unchecked")
	public List getDepartmentsList() throws Exception
	{
		List<KeyValueDTO> departmentsList = null;
		try
		{
			departmentsList = hindiDAO.getDepartmentsList();
		}
		catch (Exception e) {			
			e.printStackTrace();
			throw e;
		}
		return departmentsList;
	}
	@SuppressWarnings("unchecked")
	public List getNominationList(HindiIncentiveBean hindiBean) throws Exception
	{
		List<EmpKeyValueDTO> nominationList = null;
		try{
			nominationList = hindiDAO.getNominationList(hindiBean);
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return nominationList;
	}
	@SuppressWarnings("unchecked")
	public List getSelectedNominationList() throws Exception	
	{
		List<KeyValueDTO> selectedNominationList = null;
		try{
			selectedNominationList = hindiDAO.getSelectedNominationList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return selectedNominationList;
	}
	public String saveNominationList(HindiIncentiveBean hindiBean) throws Exception
	{		
		try{
		message = hindiDAO.saveNominationList(hindiBean);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	    return message;	
	}
	
	public String saveResultDetails(HindiIncentiveBean hindiBean) throws Exception
	{
		HindiEmpResultDTO resultDTO = new HindiEmpResultDTO();
		//edit the existing row
		try{
		if(!hindiBean.getPk().equals("")){	
			List<HindiEmpResultDTO>  resultDuplicateCheckList = hindiDAO.resultDuplicateCheckList(hindiBean);
			if(resultDuplicateCheckList.size()>0){
				for(int i=0;i<resultDuplicateCheckList.size();i++){
					int id=resultDuplicateCheckList.get(i).getId();
					if(Integer.parseInt(hindiBean.getPk()) == id){
						resultDTO.setId(Integer.parseInt(hindiBean.getPk()));
						break;
					}else{
						message = CPSConstants.DUPLICATE;
						   return message;
					}
				}
			}else if(resultDuplicateCheckList.size() == 0){
				resultDTO.setId(Integer.parseInt(hindiBean.getPk()));
			}
			resultDTO.setSfid(hindiBean.getSfid());
			resultDTO.setExamId(hindiBean.getExamId());
			resultDTO.setTotalMarks(Integer.parseInt(hindiBean.getTotalMarks()));
			resultDTO.setMarksPercentage(hindiBean.getMarksPercentage());
			resultDTO.setExamDate(hindiBean.getExamDate());
			resultDTO.setResultDate(hindiBean.getResultDate());
			resultDTO.setEffectiveDate(hindiBean.getEffectiveDate());
			resultDTO.setStatus("1");
			
		}
		//insert new row
		else{
			List<HindiEmpResultDTO>  resultDuplicateCheckList = hindiDAO.resultDuplicateCheckList(hindiBean);
			if(resultDuplicateCheckList.size()==0){
				resultDTO.setSfid(hindiBean.getSfid());
				resultDTO.setExamId(hindiBean.getExamId());
				resultDTO.setTotalMarks(Integer.parseInt(hindiBean.getTotalMarks()));
				resultDTO.setMarksPercentage(hindiBean.getMarksPercentage());
				resultDTO.setExamDate(hindiBean.getExamDate());
				resultDTO.setResultDate(hindiBean.getResultDate());
				resultDTO.setEffectiveDate(hindiBean.getEffectiveDate());
				resultDTO.setStatus("1");
				
				message = hindiDAO.saveResultDetails(resultDTO);
			    return message;
			}
			
		   message = CPSConstants.DUPLICATE;
		   return message;
		}
		
		message = hindiDAO.saveResultDetails(resultDTO);
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	    return message;
	}
	@SuppressWarnings("unchecked")
	public List getResultDetails() throws Exception
	{
		List<HindiEmpResultDTO>  resultList = null;
		try{
			resultList = hindiDAO.getResultDetails();
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return resultList;
	}
	public String deleteResultDetails(HindiIncentiveBean hindiBean) throws Exception
	{
		try{
		message = hindiDAO.deleteResultDetails(hindiBean);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	    return message;
	}
	@SuppressWarnings("unchecked")
	public List getEligibleExamsList(HindiIncentiveBean hindiBean) throws Exception
	{
		List<KeyValueDTO> eligibleExamslist = null;
		try{
			eligibleExamslist = hindiDAO.getEligibleExamsList(hindiBean);
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return eligibleExamslist;
	}
	
	@SuppressWarnings("deprecation")
	public String saveIncentiveDetails(HindiIncentiveBean hindiBean) throws Exception
	{
		HindiIncentiveDetailsDTO incentiveDTO = new HindiIncentiveDetailsDTO();
		try
		{
			if (!hindiBean.getPk().equals("")) //edit row
			{
				incentiveDTO.setId(Integer.parseInt(hindiBean.getPk()));
				incentiveDTO.setSfid(hindiBean.getSfid().toUpperCase());
				incentiveDTO.setFromDate(new SimpleDateFormat("dd-MMM-yyyy").parse(hindiBean.getFromDate()));
				/*incentiveDTO.setToDate(new SimpleDateFormat("dd-MMM-yyyy").parse(hindiBean.getToDate()));
				incentiveDTO.setNoOfInst(hindiBean.getNoOfInst());
				incentiveDTO.setTotalAmount(hindiBean.getTotalAmount());*/
				incentiveDTO.setNoOfInst(Integer.parseInt(hindiBean.getNoOfInst()));
				incentiveDTO.setTotalAmount(Float.parseFloat(hindiBean.getTotalAmount()));
				/*if(!hindiBean.getCashAwardAmount().equals(""))
				incentiveDTO.setCashAwardAmount(Float.parseFloat(hindiBean.getCashAwardAmount()));*/
				incentiveDTO.setPresentInst(Integer.parseInt(hindiBean.getPresentInst()));
				//incentiveDTO.setCreationDate(new SimpleDateFormat("dd-MMM-yyyy").format(new Date(hindiBean.getCreationDate())));
				incentiveDTO.setCreationDate(new SimpleDateFormat("dd-MMM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(hindiBean.getCreationDate())));
				incentiveDTO.setCreatedBy(hindiBean.getCreatedBy());
				incentiveDTO.setLastModifiedDate(new SimpleDateFormat("dd-MMM-yyyy").format(Calendar.getInstance().getTime()));
				incentiveDTO.setLastModifiedBy(hindiBean.getLoginSfid());
				incentiveDTO.setStatus(1);
			}
			else //insert new row
			{
				List<HindiIncentiveDetailsDTO> incentiveDetailsList = hindiDAO.incentiveDuplicateCheckList(hindiBean);
				if(incentiveDetailsList.size() == 0)
				{
					incentiveDTO.setSfid(hindiBean.getSfid().toUpperCase());
					incentiveDTO.setFromDate(new SimpleDateFormat("dd-MMM-yyyy").parse(hindiBean.getFromDate()));			
					incentiveDTO.setNoOfInst(Integer.parseInt(hindiBean.getNoOfInst()));
					incentiveDTO.setTotalAmount(Float.parseFloat(hindiBean.getTotalAmount()));
					/*if(!hindiBean.getCashAwardAmount().equals(""))
					incentiveDTO.setCashAwardAmount(Float.parseFloat(hindiBean.getCashAwardAmount()));*/
					incentiveDTO.setPresentInst(Integer.parseInt(hindiBean.getPresentInst()));
					incentiveDTO.setCreationDate(new SimpleDateFormat("dd-MMM-yyyy").format(Calendar.getInstance().getTime()));
					incentiveDTO.setCreatedBy(hindiBean.getLoginSfid());	
					incentiveDTO.setLastModifiedDate(new SimpleDateFormat("dd-MMM-yyyy").format(Calendar.getInstance().getTime()));
					incentiveDTO.setLastModifiedBy(hindiBean.getLoginSfid());
					incentiveDTO.setStatus(1);
					message = hindiDAO.saveIncentiveDetails(incentiveDTO);
					return message;
			    }
				else
				{
					message = CPSConstants.DUPLICATE;
					return message;
			    }
			}
			
			message = hindiDAO.saveIncentiveDetails(incentiveDTO);
		}catch(Exception e) {
			throw e;
		}
		return message;
	}
	
	public List<HindiIncentiveDetailsDTO> getIncentiveDetails(HindiIncentiveBean hindiBean) throws Exception
	{
		List<HindiIncentiveDetailsDTO> incentiveDetailsList = null;
		try
		{
			incentiveDetailsList = hindiDAO.getIncentiveDetails(hindiBean);
		}catch(Exception e) {
			throw e;
		}
		return incentiveDetailsList;
	}
	
	public String deleteIncentiveDetails(HindiIncentiveBean hindiBean) throws Exception
	{
		try{
			message = hindiDAO.deleteIncentiveDetails(hindiBean);
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}
		    return message;
	}
	
	public List<EmpKeyValueDTO> getIncentiveEmpList(HindiIncentiveBean hindiBean) throws Exception
	{
		List<EmpKeyValueDTO> incentiveEmpList = null;
		try
		{
			incentiveEmpList = hindiDAO.getIncentiveEmpList(hindiBean);
		}catch(Exception e) {
			throw e;
		}
		return incentiveEmpList;
	}
	@SuppressWarnings("unchecked")
	public List getIncentiveEmpListForFinance(HindiIncentiveBean hindiBean) throws Exception
	{
		List<HindiEmpResultDTO> incentiveEmpList = null;
		try{
			incentiveEmpList = hindiDAO.getIncentiveEmpListForFinance(hindiBean);
		}catch(Exception e)
		{
		e.printStackTrace();	
		throw e;
		}
		return incentiveEmpList;
	}
	@SuppressWarnings("unchecked")
	public String savePayHindiIncentiveDetails(HindiIncentiveBean hindiBean) throws Exception
	{
		String message="";
		try{
			message = hindiDAO.savePayHindiIncentiveDetails(hindiBean);
		}catch(Exception e)
		{
		e.printStackTrace();	
		throw e;
		}
		return message;
	}
	
}
