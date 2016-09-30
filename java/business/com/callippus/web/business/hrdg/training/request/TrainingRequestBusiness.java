package com.callippus.web.business.hrdg.training.request;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.file.FilesBean;
import com.callippus.web.beans.requests.AlertMessageDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.hrdg.dao.ICommonDAO;
import com.callippus.web.hrdg.file.dao.IFileDAO;
import com.callippus.web.hrdg.training.beans.TrainingMasterBean;
import com.callippus.web.hrdg.training.beans.dto.CourseAttendedDetailsDTO;
import com.callippus.web.hrdg.training.dao.ITrainingAlertDAO;
import com.callippus.web.hrdg.training.request.ITrainingRequestDAO;
import com.callippus.web.hrdg.training.request.beans.dto.HrdgTxnCancelRequestDTO;
import com.callippus.web.hrdg.training.request.beans.dto.HrdgTxnRequestDTO;
import com.callippus.web.hrdg.training.request.beans.dto.TrainingNominationDTO;
import com.callippus.web.hrdg.training.request.beans.dto.TrainingRequestBean;

@Service
public class TrainingRequestBusiness {
	@Autowired
	private ITrainingRequestDAO trainingRequestDAO; 
	@Autowired
	private ICommonDAO commonDAO;
	@Autowired
	private ITrainingAlertDAO trainingAlertDAO;
	@Autowired
	private IFileDAO fileDAO;
	public List getCourseList(TrainingRequestBean trainingReqBean) throws Exception
	{
		List list = null;
		try
		{
			list = trainingRequestDAO.getCourseListBasedCirculation(trainingReqBean);
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	public TrainingRequestBean getEmpDetails(TrainingRequestBean trainingReqBean) throws Exception
	{
		List list = null;
		try
		{
			trainingReqBean.setEmpDetails(trainingRequestDAO.getEmpDetails(trainingReqBean.getSfid()));
		}
		catch(Exception e)
		{
			throw e;
		}
		return trainingReqBean;
	}
	
	
	public String manageTrainingNomination(TrainingRequestBean trainingReqBean) throws Exception{
		String message = null;
		try
		{
						
			trainingReqBean.setCreationDate(CPSUtils.getCurrentDate());
			trainingReqBean.setCreatedBy(trainingReqBean.getSfid());
			trainingReqBean.setStatus(1);
				if(CPSUtils.isNullOrEmpty(trainingReqBean.getId()))
				{
					if(CPSUtils.compareStrings(checkTrainingNomination(trainingReqBean), CPSConstants.SUCCESS))
						{
						
						trainingReqBean.setNominationDate(CPSUtils.getCurrentDate());	  
						TrainingNominationDTO dto = new TrainingNominationDTO();
					     	  BeanUtils.copyProperties(dto,trainingReqBean);
					    	  message = commonDAO.save(dto);
					    	  
						}
					    	
						
				
						else
						{
							message = CPSConstants.DUPLICATE;
						}
						  
						  
						
					
				}
				else
				{
					if(CPSUtils.compareStrings(checkTrainingNomination(trainingReqBean), CPSConstants.SUCCESS))
						{
						//message = trainingRequestDAO.updateHRDGBoard(trainingReqBean);
						
						
						}
					else
					{
						message = CPSConstants.DUPLICATE;
					}
					
				}
				
				
			
			
		}
		catch (Exception e) {
			throw e;
		}
		return message;
	}
	public String checkTrainingNomination(TrainingRequestBean trainingReqBean) throws Exception{
		String message = null;
		try{
			
			if (CPSUtils.checkList(trainingRequestDAO.checkTrainingNomination(trainingReqBean))) {
				message = CPSConstants.DUPLICATE;
			} else {
				message = CPSConstants.SUCCESS;
			}
		}
		catch(Exception e)
		{
			message = CPSConstants.FAILED;
			throw e;
			
		}
		return message;
	
	}
	
	public TrainingNominationDTO getNominationDetails(int requestId) throws Exception{
		TrainingNominationDTO dto = null;
		try
		{
		
			dto = trainingRequestDAO.getNominationDetails(requestId);
		}
		catch(Exception e)
		{
			
			throw e;
			
		}
		return dto;


	}
	public String insertTxnDetails(TrainingRequestBean trainingReqBean) throws Exception{
		String message = null;
		try{
			trainingReqBean.setCreationDate(CPSUtils.getCurrentDate());
			trainingReqBean.setCreatedBy(trainingReqBean.getSfid());
			trainingReqBean.setStatus(1);
			HrdgTxnRequestDTO txnDto = new HrdgTxnRequestDTO();
			BeanUtils.copyProperties(txnDto, trainingReqBean);
			txnDto.setRequestStatus(8);
			txnDto.setRequestStatus(8);
			
			
			message = commonDAO.save(txnDto);
			
		}
		catch(Exception e)
		{
			message = CPSConstants.FAILED;
			throw e;
			
		}
		return message;
	}
	public List getTrainingNominationReqList(TrainingRequestBean trainingReqBean) throws Exception{
	
		List list = null;
		try
		{
			list = trainingRequestDAO.getTrainingNominationReqList(trainingReqBean);
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	
	public List getNominationsCourseList(TrainingRequestBean trainingReqBean) throws Exception{
		
		List list = null;
		try
		{
			list = trainingRequestDAO.getNominationsCourseList(trainingReqBean);
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	public List getMDBList() throws Exception{
		List list = null;
		try
		{
			list = trainingRequestDAO.getMDBList();
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	public String manageHRDGTrainingNominations(TrainingRequestBean trainingReqBean) throws Exception{
		String message = null;
		try
		{
			
					JSONObject mainJson = new JSONObject(trainingReqBean.getJsonValues());
					if(CPSUtils.compareStrings(trainingReqBean.getType(),"nomination")){
						for(int i=0;i<mainJson.length();i++) {
							JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
							message = trainingRequestDAO.updateNominationStatus(subJson.get("id").toString(),trainingReqBean.getBoardId(),trainingReqBean);
							//ltcBean.setResult(ltcApprovalRequestDAO.saveCdaAmountAdvance(subJson.get("requestId").toString(),subJson.get("cdaAmount").toString(),subJson.get("sanctionNo").toString(),subJson.get("billNo").toString(),subJson.get("dvNo").toString(),subJson.get("dvDate").toString(),subJson.get("accOfficer").toString()));
						}
					}
					if(CPSUtils.compareStrings(trainingReqBean.getType(),"boardSelection")){
						for(int i=0;i<mainJson.length();i++) {
							JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
							message = trainingRequestDAO.updateBoardSelectionStatus(subJson.get("id").toString(),trainingReqBean);
							
						}
					}
					if(CPSUtils.compareStrings(trainingReqBean.getType(),"ADSelection")){
						for(int i=0;i<mainJson.length();i++) {
							JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
							message = trainingRequestDAO.updateADSelectionStatus(subJson.get("id").toString(),trainingReqBean);
							
						}
					}
					
			
		}
		catch(Exception e)
		{
			throw e;
		}
		return message;
	}
	public List getTrainingNominationBoardSelectionList(TrainingRequestBean trainingReqBean) throws Exception {
		List list = null;
		try
		{
			list = trainingRequestDAO.getTrainingNominationBoardSelectionList(trainingReqBean);
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	public List getTrainingNominationADSelectionList(TrainingRequestBean trainingReqBean) throws Exception {
		List list = null;
		try
		{
			list = trainingRequestDAO.getTrainingNominationADSelectionList(trainingReqBean);
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	public List getTrainingNominationFinalSelectionList(TrainingRequestBean trainingReqBean) throws Exception {
		List list = null;
		try
		{
			list = trainingRequestDAO.getTrainingNominationFinalSelectionList(trainingReqBean);
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	public String manageTrainingFinalList(TrainingRequestBean trainingReqBean) throws Exception{
		String message = null;
		String courseName = null;
		CourseAttendedDetailsDTO dto = null;
		String sfid = null;
		try
		{
			
			dto =  trainingRequestDAO.getCourseDetails(trainingReqBean);
			trainingReqBean.setCreationDate(CPSUtils.getCurrentDate());
			trainingReqBean.setCreatedBy(trainingReqBean.getSfid());
			trainingReqBean.setStatus(1);
			JSONObject mainJson = new JSONObject(trainingReqBean.getJsonValues());
			for(int i=0;i<mainJson.length();i++) {
				JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
				message = trainingRequestDAO.updateFinalSelectionStatus(subJson.get("id").toString(), trainingReqBean);
				sfid = trainingRequestDAO.getNomineeSfid(subJson.get("id").toString());
					dto.setNomineeSfid(sfid);
					String msg = trainingRequestDAO.checkAttendedCourseDetails(dto); 
					if(CPSUtils.compareStrings(msg, CPSConstants.SUCCESS))
					{
						trainingRequestDAO.updateAttendedCourseDetails(dto,trainingReqBean);
						
					}
					else if(!CPSUtils.compareStrings(msg, CPSConstants.SUCCESS) && trainingReqBean.getSelectStatus().equalsIgnoreCase("50"))
					{
						CourseAttendedDetailsDTO cadDto = new CourseAttendedDetailsDTO();
						BeanUtils.copyProperties(cadDto, dto);
						
						cadDto.setCreationDate(CPSUtils.getCurrentDate());
						cadDto.setCreatedBy(trainingReqBean.getSfid());
						cadDto.setStatus(1);
						commonDAO.save(cadDto);
					}
					
				
			
		    	  if(CPSUtils.compareStrings(message, CPSConstants.SUCCESS))
		    	  {
		    		  	AlertMessageDTO alertMessageDTO = new AlertMessageDTO();
		    			alertMessageDTO.setAlertMessage(CPSConstants.HRDGTRAININGFINALSELECTION);
		    			alertMessageDTO.setAssignedFrom(trainingReqBean.getSfid());
		    			alertMessageDTO.setAssignedDate(CPSUtils.getCurrentDateWithTime());
		    			alertMessageDTO.setAssignedIpAddress(trainingReqBean.getIpAddress());	
		    			alertMessageDTO.setAssignedTo(subJson.get("sfid").toString());
		    			alertMessageDTO.setAlertID(Integer.valueOf(CPSConstants.TRAININGFINALSELECTIONALERTID));
		    			alertMessageDTO.setStatus(Integer.valueOf(CPSConstants.STATUSPENDING));
		    			alertMessageDTO.setReferenceID(Integer.parseInt(subJson.get("id").toString()));
		    			alertMessageDTO.setVenueID(1);
		    			
		    		//	trainingAlertDAO.submitAlert(alertMessageDTO);
		    		 
		    	  }
		    	 
			}
			
//				if(CPSUtils.isNullOrEmpty(trainingReqBean.getId()))
//				{
//					if(CPSUtils.compareStrings(checkTrainingFinalAlert(trainingReqBean), CPSConstants.SUCCESS))
//						{
//						JSONObject mainJson = new JSONObject(trainingReqBean.getJsonValues());
//						for(int i=0;i<mainJson.length();i++) {
//							JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
//							message = trainingRequestDAO.updateFinalSelectionStatus(subJson.get("id").toString());
//						
//					    	  if(CPSUtils.compareStrings(message, CPSConstants.SUCCESS))
//					    	  {
//					    		  submitAlert(trainingReqBean,depts,cirId);
//					    	  }
//					    	 
//						}
//						}
//					    	
//						
//				
//						else
//						{
//							message = CPSConstants.DUPLICATE;
//						}
//						  
//						  
//						
//					
//				}
//				else
//				{
//					if(CPSUtils.compareStrings(checkTrainingCirculation(trainingMstBean), CPSConstants.SUCCESS))
//						{
//						message = trainingMasterDAO.updateTrainingCirculation(trainingMstBean);
//						trainingMasterDAO.deleteCourse(trainingMstBean,CPSConstants.COURSEDESIGNATIONSDTO);
//						
//						int cirId = Integer.parseInt(trainingMstBean.getId());
//				    	  if(CPSUtils.compareStrings(message, CPSConstants.UPDATE))
//				    	  {
//				    		 
//				    		  String[] depts = (!CPSUtils.isNullOrEmpty(trainingMstBean.getDepartment()))?trainingMstBean.getDepartment().split(","):null;
//				    		  for(int i = 0;!CPSUtils.isNullOrEmpty(depts) && i < depts.length;i++)
//					    		  {
//				    			  TrainingCirculationDeptDTO tcDTO = new TrainingCirculationDeptDTO();
//					    			  BeanUtils.copyProperties(tcDTO,trainingMstBean);
//					    			  tcDTO.setCirculationId(cirId);
//					    			  tcDTO.setDepartmentId(Integer.parseInt(depts[i]));
//					    			  commonDAO.save(tcDTO);
//					    		  }
//				    		  
//				    		  
//				    	  }
//						
//						}
//					else
//					{
//						message = CPSConstants.DUPLICATE;
//					}
//					
//				}
//				
//				
//			
//			
		}
		catch (Exception e) {
			throw e;
		}
		return message;
	}
	public List getNominationsForCFACourseList(TrainingRequestBean trainingReqBean) throws Exception{
		List list = null;
		try
		{
			list = trainingRequestDAO.getNominationsForCFACourseList(trainingReqBean);
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	public List getTrainingNominationsCFASelectionList(TrainingRequestBean trainingReqBean) throws Exception {
		List list = null;
		try
		{
			list = trainingRequestDAO.getTrainingNominationsCFASelectionList(trainingReqBean);
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	public String manageTrainingNominationsCFASelectionList(TrainingRequestBean trainingReqBean) throws Exception{
		String message = null;
		try
		{
		JSONObject mainJson = new JSONObject(trainingReqBean.getJsonValues());
		if(CPSUtils.compareStrings(trainingReqBean.getType(),"CFASelection")){
			for(int i=0;i<mainJson.length();i++) {
				JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
				message = trainingRequestDAO.updateTrainingNominationsCFAStatus(subJson.get("id").toString(),trainingReqBean);
				
			}
		}
		
		}
		catch(Exception e)
		{
			throw e;
		}
		return message;
	
	}
	public List viewTrainingNominationDetails(TrainingRequestBean trainingReqBean) throws Exception{
	    List list = null;
		try
		{
			list = trainingRequestDAO.viewTrainingNominationDetails(trainingReqBean);
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	public TrainingRequestBean getBrochureAndION(TrainingRequestBean trainingReqBean) throws Exception{
		 List list = null;
			try
			{
				list = trainingRequestDAO.getBrochureAndION(trainingReqBean);
				if(CPSUtils.checkList(list))
				{
					Map map = (Map)list.get(0);
					trainingReqBean.setBrochure(map.get("BROCHURE").toString());
					trainingReqBean.setBrochureName(map.get("FILENAME").toString());
					trainingReqBean.setDurationId(map.get("DURATIONID").toString());
				}
				list = trainingRequestDAO.getDeptAndION(trainingReqBean);
				if(CPSUtils.checkList(list))
				{
					Map map = (Map)list.get(0);
					trainingReqBean.setDept(map.get("DENAME").toString());
					
				}
			}
			catch(Exception e)
			{
				throw e;
			}
			return trainingReqBean;
	}
	public FilesBean getBrochure(int id) throws Exception {
		List list = null;
		FilesBean file = null;
		try {
			file = fileDAO.downloadFromDatabase(id);
		} catch (Exception e) {
			throw e;
		}
		return file;
	}
	public String updateRequest(TrainingRequestBean trainingReqBean,int status) throws Exception {
		String message = null;
		try
		{
			
			message = trainingRequestDAO.updateRequest(trainingReqBean,status);
		}
		catch(Exception e)
		{
			throw e;
		}
		return message;
	}
	public TrainingRequestBean viewTrainingNomination(TrainingRequestBean trainingReqBean) throws Exception {
		List list = null;
		try
		{
			list = trainingRequestDAO.viewTrainingNomination(trainingReqBean);
			if(CPSUtils.checkList(list)){
				for(int i = 0;i<list.size();i++)
				{
					HrdgTxnRequestDTO dto = (HrdgTxnRequestDTO)list.get(i);
					trainingReqBean.setCourse(dto.getCourseDto().getName());
					trainingReqBean.setNominationDate(dto.getTrainingNominationDto().getNominationDate());
					trainingReqBean.setCurrentAssignment(dto.getTrainingNominationDto().getCurrentAssignment());
					trainingReqBean.setRelevance(dto.getTrainingNominationDto().getRelevance());
					trainingReqBean.setLastAttendedCourse(dto.getTrainingNominationDto().getLastAttendedCourse());
					trainingReqBean.setId(String.valueOf(dto.getTrainingNominationDto().getId()));
					trainingReqBean.setHistoryID(dto.getHistoryID());
					trainingReqBean.setStageID(dto.getStageID());
				}
			
			}
			
		}
		catch(Exception e)
		{
			
		}
		return trainingReqBean;
	}
	public String insertTxnCancelDetails(TrainingRequestBean trainingReqBean) throws Exception{
		String message = null;
		try{
			trainingReqBean.setCreationDate(CPSUtils.getCurrentDate());
			trainingReqBean.setCreatedBy(trainingReqBean.getSfid());
			trainingReqBean.setStatus(1);
			HrdgTxnCancelRequestDTO txnDto = new HrdgTxnCancelRequestDTO();
			BeanUtils.copyProperties(txnDto, trainingReqBean);
			txnDto.setRequestStatus(2);
			txnDto.setRefRequestId(Integer.parseInt(trainingReqBean.getRefRequestId()));
			message = commonDAO.save(txnDto);
			
		}
		catch(Exception e)
		{
			message = CPSConstants.FAILED;
			throw e;
			
		}
		return message;
	}
	public TrainingNominationDTO getNominationCancelDetails(int requestId) throws Exception{
		TrainingNominationDTO dto = null;
		try
		{
		
			dto = trainingRequestDAO.getNominationCancelDetails(requestId);
		}
		catch(Exception e)
		{
			
			throw e;
			
		}
		return dto;
	}
	public String updateCancelRequest(TrainingRequestBean trainingReqBean, int status) throws Exception{
		String message = null;
		try
		{
			
			message = trainingRequestDAO.updateCancelRequest(trainingReqBean,status);
		}
		catch(Exception e)
		{
			throw e;
		}
		return message;
		
	}
	public HrdgTxnRequestDTO getRefRequestId(TrainingRequestBean trainingReqBean) throws Exception{
		List list = null;
		HrdgTxnRequestDTO historyDto = null;
		try
		{
			
			list = trainingRequestDAO.getRefRequestId(trainingReqBean);
			if(CPSUtils.checkList(list))
			{
				for(int i = 0;i<list.size();i++)
				{
					historyDto = (HrdgTxnRequestDTO)list.get(i);
				}
			}
			
		}
		catch(Exception e)
		{
			throw e;
		}
		return historyDto;
	}
	public String cancelRequest(TrainingRequestBean trainingReqBean, String i) throws Exception{
		String message = null;
		try
		{
			
			message = trainingRequestDAO.cancelRequest(trainingReqBean,i);
		}
		catch(Exception e)
		{
			throw e;
		}
		return message;
		
	}
	public String cancelNewRequest(TrainingRequestBean trainingReqBean, String i) throws Exception{
		String message = null;
		try
		{
			
			message = trainingRequestDAO.cancelNewRequest(trainingReqBean,i);
		}
		catch(Exception e)
		{
			throw e;
		}
		return message;
		
	}
	public TrainingRequestBean getNominationReqStatus(TrainingRequestBean trainingReqBean) throws Exception {
		String message = null;
		List list = null;
		try
		{
			
			list = trainingRequestDAO.getNominationReqStatus(trainingReqBean);
			if(CPSUtils.checkList(list)){
				trainingReqBean.setNomRequestStatus("1");
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return trainingReqBean;
	}
	public List getReqIPAddress(String requestId,String requestType) throws Exception{
		String message = null;
		List list = null;
		try
		{
			
			list = trainingRequestDAO.getReqIPAddress(requestId,requestType);
			
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	public List getNominationLastAttededCourse(TrainingRequestBean trainingReqBean) throws Exception {
		String message = null;
		List list = null;
		try
		{
			
			list = trainingRequestDAO.getNominationLastAttededCourse(trainingReqBean);
						
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	public String updateCancelStatusOfRequest(TrainingRequestBean trainingReqBean) throws Exception {
		String message = null;
		try
		{
			
			message = trainingRequestDAO.updateCancelStatusOfRequest(trainingReqBean);
		}
		catch(Exception e)
		{
			throw e;
		}
		return message;
	}
	public List getSeriesMstList() throws Exception{
		List list = null;
		try
		{
			list = trainingRequestDAO.getSeriesMstList();
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	public List getIONMstList(TrainingRequestBean trainingReqBean) throws Exception{
		List list = null;
		try
		{
			list = trainingRequestDAO.getIONMstList(trainingReqBean);
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	public TrainingRequestBean checkCourseFee(TrainingRequestBean trainingReqBean) throws Exception{
		List list = null;
		
		try
		{
			list = trainingRequestDAO.checkCourseFee(trainingReqBean);
			if(CPSUtils.checkList(list)){
				trainingReqBean.setFeeFlag("1");
			}
			else
				trainingReqBean.setFeeFlag("0");
		}
		catch(Exception e)
		{
			throw e;
		}
		return trainingReqBean;
	}
	public Object getTrainingTypeList() throws Exception{
		List list = null;
		try
		{
			list = trainingRequestDAO.getTrainingTypeList();
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	public Object getFinancialYear()throws Exception{
		List list = null;
		try
		{
			list = trainingRequestDAO.getFinancialYear();
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	
}
