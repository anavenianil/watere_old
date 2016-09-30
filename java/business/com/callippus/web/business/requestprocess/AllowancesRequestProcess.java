package com.callippus.web.business.requestprocess;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.allowances.AllowancesRequestBean;
import com.callippus.web.beans.dto.AllowanceEmpDetailsDTO;
import com.callippus.web.beans.requests.FPARequestProcessBean;
import com.callippus.web.beans.workflowmapping.WorkFlowMappingBean;
import com.callippus.web.business.domainobject.AllowancesDomainObject;
import com.callippus.web.business.tx.workflow.TxRequestProcess;
import com.callippus.web.controller.common.CPSUtils;

@Service
public class AllowancesRequestProcess extends RequestProcess {
	private static Log log = LogFactory.getLog(AllowancesRequestProcess.class);
	@Autowired
	private TxRequestProcess txRequestProcess;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private AllowancesDomainObject allowancesDomainObject;


	public WorkFlowMappingBean getFpaRequestDetails(WorkFlowMappingBean workflowBean) throws Exception {
		log.debug("::<<<<<FPARequestProcess<<<<<<Method>>>>>>>>>>>>>>>getFpaRequestDetails(WorkFlowMappingBean workflowBean)>>>>>>>>>");
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			workflowBean.setFpaRequestDetails((FPARequestProcessBean) session.get(FPARequestProcessBean.class, workflowBean.getRequestId()));
		} catch (Exception e) {
			throw e;
		}
		return workflowBean;
	}
	
	
	
	public AllowancesRequestBean saveAllowanceDetails(AllowancesRequestBean arBean) throws Exception{ 
		try {
			
			
			JSONObject mainJson = new JSONObject(arBean.getJsonValue());
			if("REQUESTBASED".equalsIgnoreCase(arBean.getAllowanceFor()))
			{
				submitAllowanceDetails(arBean,arBean.getEmpDetails());
			}
			for(int i=0;i<mainJson.length();i++) {
				String value = (String) mainJson.get(String.valueOf(i));
				submitAllowanceDetails(arBean,value);
			}
			
			
		}catch (Exception e) {
			throw e;
		}
		return arBean;
	}
	
	@SuppressWarnings("unchecked")
	public String submitAllowanceDetails(AllowancesRequestBean allowancesRB,String value) throws Exception {
		
		Session session = null;
		//LTCWaterFamilyDTO ltcWaterFamilyDTO = null;
		AllowanceEmpDetailsDTO allowanceEmpDetailsDTO = null;
		try {
			allowanceEmpDetailsDTO =new AllowanceEmpDetailsDTO();
			allowanceEmpDetailsDTO.setAllowanceName(allowancesRB.getAllowanceName());
			allowanceEmpDetailsDTO.setAllowanceFor(allowancesRB.getAllowanceFor());
			allowanceEmpDetailsDTO.setApprovedBy(allowancesRB.getApprovedBy());
			allowanceEmpDetailsDTO.setApprovedDate(allowancesRB.getApprovedDate());
			if(!CPSUtils.isNullOrEmpty(allowancesRB.getAllowanceAmt())){
				allowanceEmpDetailsDTO.setAllowanceAmount(Integer.parseInt(allowancesRB.getAllowanceAmt()));
			}else{
				allowanceEmpDetailsDTO.setAllowanceAmount(0);
			}
			allowanceEmpDetailsDTO.setSfid(value);
			allowanceEmpDetailsDTO.setEffectFromDate(allowancesRB.getEffectFromDate());
			allowanceEmpDetailsDTO.setEffectToDate(allowancesRB.getEffectToDate());
			
			session = hibernateUtils.getSession();
			session.createCriteria(AllowanceEmpDetailsDTO.class);
			session.save(allowanceEmpDetailsDTO);
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.clear();
		}
		return allowancesRB.getMessage();
	}
}
