package com.callippus.web.business.hrdg.training;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.workflowmapping.WorkFlowMappingBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.hrdg.training.beans.dto.TrainingCirculationDTO;
import com.callippus.web.hrdg.training.dao.ITrainingMasterDAO;

@Service
public class TrainingCirculationBusiness {
	@Autowired
	private ITrainingMasterDAO trainingMasterDAO;
	
	public TrainingCirculationDTO getAlertDetails(WorkFlowMappingBean workBean) throws Exception 
	{
		TrainingCirculationDTO trainingCirculationDto = null;
		List list = null;
		try
		{
			if(CPSUtils.compareStrings(String.valueOf(workBean.getAlertDetails().getAlertDetails().getId()),CPSConstants.TRAININGALERTID))
			{
						
				list = trainingMasterDAO.getTrainingCirculationDetails(String.valueOf(workBean.getAlertDetails().getReferenceID()),CPSConstants.TRAININGALERTID);
				if(CPSUtils.checkList(list))
					trainingCirculationDto=(TrainingCirculationDTO)list.get(0);
				
			}
			else if(CPSUtils.compareStrings(String.valueOf(workBean.getAlertDetails().getAlertDetails().getId()),CPSConstants.TRAININGFINALSELECTIONALERTID))
			{
						
				list = trainingMasterDAO.getTrainingCirculationDetails(String.valueOf(workBean.getAlertDetails().getReferenceID()),CPSConstants.TRAININGFINALSELECTIONALERTID);
				if(CPSUtils.checkList(list))
					trainingCirculationDto=(TrainingCirculationDTO)list.get(0);
				
			} 
		}
		catch(Exception e)
		{
			throw e;
		}
		return trainingCirculationDto;
	}

	

}
