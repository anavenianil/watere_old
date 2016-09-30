package com.callippus.web.tada.dao.request;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.transform.Transformers;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.DepartmentsDTO;
import com.callippus.web.beans.dto.DynamicWorkflowTxnDTO;
import com.callippus.web.beans.dto.EmpPaymentsDTO;
import com.callippus.web.beans.dto.EmployeeClaimDetailsDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.TadaProjectDirectorsDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.requests.LeaveRequestBean;
import com.callippus.web.beans.requests.RequestCommonBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.tada.beans.request.TadaRequestBean;
import com.callippus.web.tada.dto.DaOnTourDTO;
import com.callippus.web.tada.dto.TaEntitleClassDTO;
import com.callippus.web.tada.dto.TaEntitleTypeDTO;
import com.callippus.web.tada.dto.TaMapDTO;
import com.callippus.web.tada.dto.TaTravelTypeMapDTO;
import com.callippus.web.tada.dto.TadaApprovalRequestDTO;
import com.callippus.web.tada.dto.TadaDaNewDetailsDTO;
import com.callippus.web.tada.dto.TadaTdAccDetDayRepDTO;
import com.callippus.web.tada.dto.TadaTdAdvanceRequestDTO;
import com.callippus.web.tada.dto.TadaTdDaNewAccDetailsDTO;
import com.callippus.web.tada.dto.TadaTdDaNewFoodDetailsDTO;
import com.callippus.web.tada.dto.TadaTdFoodDetDayRepDTO;
import com.callippus.web.tada.dto.TadaTdJDADetailsDTO;
import com.callippus.web.tada.dto.TadaTdLeaveDetailsDTO;
import com.callippus.web.tada.dto.TadaTdReqJourneyDTO;
import com.callippus.web.tada.dto.TadaTdSettlementDTO;
import com.callippus.web.tada.dto.TravelTypeDTO;

@Service
public class SQLTadaRequestDAO implements ITadaRequestDAO{
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	

	
	Session session=null;
	@Override
	public TadaRequestBean getEmpDetails(TadaRequestBean tadaRequestBean)throws Exception {
		try{
			session=hibernateUtils.getSession();
			//crit=session.createCriteria(EmployeeBean.class).add(Expression.eq(CPSConstants.USERSFID, tadaEmpDetailsDTO.getSfid()));
			//tadaEmpDetailsDTO.setEmpDetails(session.createSQLQuery("select e1.sfid as sfid,e1.name_in_service_book as name,e1.personal_number as phnNo,e2.name as designation from emp_master e1,designation_master e2 where e1.sfid="+"'" + sfid +"'" +" and e1.designation_id=e2.id").addScalar("sfid",Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("phnNo", Hibernate.LONG).addScalar("designation", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaEmpDetailsDTO.class)).list());
			//empDetailsList=crit.list();
			//empDetailsList=session.createSQLQuery("select e1.sfid as sfid,e1.name_in_service_book as name,e1.personal_number as phnNo,e2.name as designation from emp_master e1,designation_master e2 where e1.sfid="+"'" + sfid +"'" +" and e1.designation_id=e2.id").addScalar("sfid",Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("phnNo", Hibernate.LONG).addScalar("designation", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaEmpDetailsDTO.class)).list();
			
			tadaRequestBean.setEmpDetailsList((EmployeeBean) session.createCriteria(EmployeeBean.class).add(Expression.or(Expression.eq("userSfid", tadaRequestBean.getSfid()), Expression.eq("userSfid", tadaRequestBean.getSfID()))).uniqueResult());
		}catch(Exception e){
			throw e;
		}
		return tadaRequestBean;
	}
	@Override
	public TadaRequestBean getDeptDetails(TadaRequestBean tadaRequestBean)throws Exception {
		String sql=null;
		try{
			session=hibernateUtils.getSession();
			String sfid=tadaRequestBean.getSfid();
			if(sfid==null){
				sfid=tadaRequestBean.getSfID();
			}
			sql="select unique dm.department_name as deptName from emp_master em,departments_master dm,org_role_instance ori where dm.department_id=(select ori.department_id from org_role_instance ori where ori.org_role_id=(select em.office_id from emp_master em where em.sfid=?))";
			tadaRequestBean.setDeptDTO((DepartmentsDTO)session.createSQLQuery(sql).addScalar("deptName", Hibernate.STRING).setString(0, sfid).setResultTransformer(Transformers.aliasToBean(DepartmentsDTO.class)).uniqueResult());
		}catch(Exception e){
			throw e;
		}
		return tadaRequestBean;
	}
	@Override
	public TadaRequestBean getDaOnTourDetails(TadaRequestBean tadaRequestBean)throws Exception {
	
		try{
			/*Criteria crit=null;*/
			session=hibernateUtils.getSession();
			/*String sfid=tadaRequestBean.getSfid();*/
		
			DaOnTourDTO daTourDTO=null;
			String sql="SELECT daontour.da_on_tour as daOnTour,payda.DA_VALUE as daRangeFrom FROM tada_da_on_tour_master daontour,(SELECT DA_VALUE FROM PAY_DEARNESS_ALLOWANCE_MASTER"
					+ " WHERE STATUS=1 AND DA_DATE = (SELECT MAX(DA_DATE) FROM PAY_DEARNESS_ALLOWANCE_MASTER WHERE STATUS=1 )) payda "
					+ " WHERE status =1 AND payda.da_value >daontour.da_range_from AND payda.da_value<=daontour.da_range_to";
			daTourDTO= (DaOnTourDTO) session.createSQLQuery(sql).addScalar("daOnTour", Hibernate.FLOAT).addScalar("daRangeFrom", Hibernate.FLOAT)
					.setResultTransformer(Transformers.aliasToBean(DaOnTourDTO.class)).uniqueResult();
   
		    tadaRequestBean.setTadaDaPercentage(daTourDTO.getDaOnTour());  
		    tadaRequestBean.setPayDaValue(daTourDTO.getDaRangeFrom());  //This is new condition for Tadada value
		} catch(Exception e){
			throw e;
		}
		return tadaRequestBean;
	}
	public TadaRequestBean getPayDetails(TadaRequestBean tadaRequestBean)throws Exception {             //DaOnTourdetails method
		
		try{
			/*Criteria crit=null;*/
			session=hibernateUtils.getSession();
			//String sfid=tadaRequestBean.getSfid();                                  //This variable doesn't use 
		
			tadaRequestBean.setPayDetailsList((EmpPaymentsDTO) session.createCriteria(EmpPaymentsDTO.class).add(Expression.or(Expression.eq("sfid", tadaRequestBean.getSfid()), Expression.eq("sfid", tadaRequestBean.getSfID()))).uniqueResult());	
		if(CPSUtils.isNullOrEmpty(tadaRequestBean.getPayDetailsList ())) {                                   //This condition has been added for to handle tada request form
			tadaRequestBean.setStatusMsg("failure");
		}  else{ tadaRequestBean.setStatusMsg("success");}
	
		} catch(Exception e){
			throw e;
		}
		return tadaRequestBean;
	}
	
	
	
	
	@Override
	@SuppressWarnings("unchecked")
	public TadaRequestBean getConveyanceMode(TadaRequestBean tadaRequestBean)throws Exception {
		EmpPaymentsDTO empPaymentsDTO=new EmpPaymentsDTO();
		List<TaTravelTypeMapDTO> list=null;
		try{
			Criteria crit=null;
			session=hibernateUtils.getSession();
			tadaRequestBean=getPayDetails(tadaRequestBean);
			empPaymentsDTO=tadaRequestBean.getPayDetailsList();                                
				int gradePay=Integer.parseInt(empPaymentsDTO.getGradePay());
				crit=session.createCriteria(TaTravelTypeMapDTO.class).add(Expression.eq("gradePay", gradePay));
				list=crit.list();
				tadaRequestBean.setConveyanceModeList(list);
				//tadaRequestBean.setConveyanceModeList((TaTravelTypeMapDTO) session.createCriteria(TaTravelTypeMapDTO.class).add(Expression.eq("gradePay", gradePay)));
		
		} catch(Exception e){
			throw e;
		}
		return tadaRequestBean;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<TaMapDTO> getTravelTypeMapList(TadaRequestBean tadaRequestBean)throws Exception{
		Session session=null;
		List<TaMapDTO> keyValueDTOList = null;
		EmpPaymentsDTO empPaymentsDTO=new EmpPaymentsDTO();
		try{
			session = hibernateUtils.getSession();
			tadaRequestBean=getPayDetails(tadaRequestBean);
			empPaymentsDTO=tadaRequestBean.getPayDetailsList();
			int gradePay=Integer.parseInt(empPaymentsDTO.getGradePay());
			keyValueDTOList = session.createSQLQuery("select key as key,value as value,grade_pay as id,travel_type_id as travelTypeId,id as flag from TADA_TRAVEL_TYPE_MAP where STATUS=1 and grade_pay="+gradePay+ "").addScalar("value", Hibernate.STRING)
					.addScalar("key", Hibernate.STRING).addScalar("id", Hibernate.INTEGER).addScalar("travelTypeId", Hibernate.INTEGER).addScalar("flag", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TaMapDTO.class)).list();
			
		} catch (Exception e) {
			throw e;
		}
		return keyValueDTOList;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<TaEntitleTypeDTO> getEntitleType(TaEntitleTypeDTO taEntitleTypeDTO)throws Exception {
		Session session=null;
		List<TaEntitleTypeDTO> entitleTypeList=null;
		try{
			session=hibernateUtils.getSession();
			entitleTypeList=session.createCriteria(TaEntitleTypeDTO.class).add(Expression.eq(CPSConstants.STATUS,1)).list();
		} catch(Exception e){
			throw e;
		}
		return entitleTypeList;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<TaEntitleClassDTO> getEntitlementClassList(TaEntitleClassDTO taEntitleClassDTO)throws Exception{
		Session session=null;
		List<TaEntitleClassDTO> entitlementClassList=null;
		try{
			session=hibernateUtils.getSession();
			entitlementClassList=session.createCriteria(TaEntitleClassDTO.class).add(Expression.eq(CPSConstants.STATUS,1)).add(Expression.eq("gradePay",taEntitleClassDTO.getGradePay())).list();
		} catch(Exception e){
			throw e;
		}
		return entitlementClassList;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<TravelTypeDTO> getTravelTypeList(TravelTypeDTO travelTypeDTO)throws Exception {
		Session session=null;
		List<TravelTypeDTO> travelTypeList=null;
		try{
			session=hibernateUtils.getSession();
			travelTypeList=session.createCriteria(TravelTypeDTO.class).add(Expression.eq(CPSConstants.STATUS,1)).list();
		} catch(Exception e){
			throw e;
		}
		return travelTypeList;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public TadaRequestBean getAmendmentDetails(TadaRequestBean tadaRequestBean)throws Exception {
		TadaApprovalRequestDTO tadaApprovalRequestDTO=null;
		TadaTdAdvanceRequestDTO tadaTdAdvanceRequestDTO=null;
		List<TadaTdLeaveDetailsDTO> tdLeaveDetailsList=null;
		try{
			tadaApprovalRequestDTO = new TadaApprovalRequestDTO();
			session=hibernateUtils.getSession();
			if(CPSUtils.compareStrings(CPSConstants.TADATDBUILDUP, tadaRequestBean.getRequestType()) || CPSUtils.compareStrings(CPSConstants.TADATDPROJECT, tadaRequestBean.getRequestType())){
				tadaApprovalRequestDTO = (TadaApprovalRequestDTO)session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("requestId", tadaRequestBean.getRequestId())).uniqueResult();
				
				//BeanUtils.copyProperties(tadaApprovalRequestDTO, tadaRequestBean);
				
			 org.apache.commons.beanutils.BeanUtils.copyProperties(tadaRequestBean, tadaApprovalRequestDTO);
				
				//new code
				tadaRequestBean.setTadaTdReqJourneyList(session.createCriteria(TadaTdReqJourneyDTO.class).add(Expression.eq("referenceId", Integer.parseInt(tadaRequestBean.getRequestId()))).list());
				if(CPSUtils.compareStrings(tadaRequestBean.getRequestType(), CPSConstants.TADATDPROJECT)){
					tadaRequestBean.setProjDirName(((DynamicWorkflowTxnDTO)session.createCriteria(DynamicWorkflowTxnDTO.class).add(Expression.eq("requestID", Integer.parseInt(tadaRequestBean.getRequestId()))).uniqueResult()).getDynamicTo());
				}
				tdLeaveDetailsList=session.createCriteria(TadaTdLeaveDetailsDTO.class).add(Expression.eq("refRequestId", Integer.parseInt(tadaRequestBean.getRequestId()))).list();
				if(tdLeaveDetailsList.size()!=0){
					tadaRequestBean.setTdLeaveDetailsList(tdLeaveDetailsList);
				}
			} else if(CPSUtils.compareStrings(CPSConstants.TADATDADVANCES, tadaRequestBean.getRequestType())){
				tadaTdAdvanceRequestDTO = (TadaTdAdvanceRequestDTO)session.createCriteria(TadaTdAdvanceRequestDTO.class).add(Expression.eq("requestId", tadaRequestBean.getRequestId())).uniqueResult();
				BeanUtils.copyProperties(tadaTdAdvanceRequestDTO, tadaRequestBean);
			}
			
		} catch(Exception e){
			throw e;
		}
		return tadaRequestBean;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<TadaDaNewDetailsDTO> getDaNewDetailsList()throws Exception{
		List<TadaDaNewDetailsDTO> list=null;
		Session session=null;
		try{
			session=hibernateUtils.getSession();
			list=session.createCriteria(TadaDaNewDetailsDTO.class).add(Expression.eq("status", 1)).list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<TadaTdDaNewFoodDetailsDTO> getFoodDetails1(TadaRequestBean tadaRequestBean)throws Exception{
		List<TadaTdDaNewFoodDetailsDTO> list=null;
		Session session=null;
		Date minDate=null;
		Date maxDate=null;
		String strMinDate=null;
		String strMaxDate=null;
		DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		Iterator itr=null;
		Iterator itr1=null;
		Iterator itr2=null;
		TadaTdDaNewFoodDetailsDTO tempDTO=null;
		TadaTdDaNewFoodDetailsDTO tempDTO1=null;
		TadaTdDaNewFoodDetailsDTO tempDTO2=null;
		TadaTdDaNewFoodDetailsDTO tempDTO3=null;
		List<TadaTdDaNewFoodDetailsDTO> representedDateList=new ArrayList<TadaTdDaNewFoodDetailsDTO>();
		List<TadaTdDaNewFoodDetailsDTO> representedDateList1=new ArrayList<TadaTdDaNewFoodDetailsDTO>();
		List<TadaDaNewDetailsDTO> daNewList=null;
		Iterator daNewItr=null;
		TadaDaNewDetailsDTO tadaDaNewDetailsDTO=null;
		float foodAmount=0;
		try {
			session=hibernateUtils.getSession();
			list=session.createCriteria(TadaTdDaNewFoodDetailsDTO.class).add(Expression.eq("settlementRequestId", tadaRequestBean.getRequestId())).addOrder(Order.asc("fromDate")).addOrder(Order.asc("toDate")).list();
			daNewList=session.createCriteria(TadaDaNewDetailsDTO.class).add(Expression.eq("status", 1)).addOrder(Order.asc("gradePay")).list();
			minDate=list.get(0).getFromDate();
			maxDate=list.get(list.size()-1).getToDate();
			daNewItr=daNewList.iterator();
			itr=list.iterator();
			while (itr.hasNext()) {
				tempDTO = (TadaTdDaNewFoodDetailsDTO) itr.next();
				if(minDate.compareTo(tempDTO.getFromDate())>0){
					minDate=tempDTO.getFromDate();
				}
				if(maxDate.compareTo(tempDTO.getToDate())<0){
					maxDate=tempDTO.getToDate();
				}
			}
			strMinDate=formatter.format(minDate);
			strMaxDate=formatter.format(maxDate);
			int diff=Integer.parseInt(CPSUtils.daysDifference(strMinDate, strMaxDate))+2;
			itr1=list.iterator();
			for(int i=0;i<diff;i++){
				tempDTO1=new TadaTdDaNewFoodDetailsDTO();
				tempDTO1.setRepresentationDate(strMinDate);
				representedDateList.add(tempDTO1);
				strMinDate=CPSUtils.nextDate(strMinDate);
			}
			itr2=representedDateList.iterator();
			while(daNewItr.hasNext()){
				tadaDaNewDetailsDTO=new TadaDaNewDetailsDTO();
				tadaDaNewDetailsDTO=(TadaDaNewDetailsDTO)daNewItr.next();
				if(tadaDaNewDetailsDTO.getGradePay()==Integer.parseInt(tadaRequestBean.getPayDetailsList().getGradePay())){
					while(itr2.hasNext()){
						tempDTO1=new TadaTdDaNewFoodDetailsDTO();
						tempDTO1=(TadaTdDaNewFoodDetailsDTO)itr2.next();
						while (itr1.hasNext()) {
							tempDTO2=new TadaTdDaNewFoodDetailsDTO();
							tempDTO2=(TadaTdDaNewFoodDetailsDTO)itr1.next();					
							if(CPSUtils.convertStringToDate(tempDTO1.getRepresentationDate()).compareTo(CPSUtils.convertStringToDate(CPSUtils.formatDate(tempDTO2.getFromDate())))>=0 && CPSUtils.convertStringToDate(tempDTO1.getRepresentationDate()).compareTo(CPSUtils.convertStringToDate(CPSUtils.formatDate(tempDTO2.getToDate())))<=0){
								tempDTO3=new TadaTdDaNewFoodDetailsDTO();
								int dayDiff=Integer.parseInt(CPSUtils.daysDifference(CPSUtils.formatDate(tempDTO2.getFromDate()), CPSUtils.formatDate(tempDTO2.getToDate())))+2;
								float tempFoodAmount=tempDTO2.getFoodAmountAftRes()/dayDiff;
								tempDTO3.setRepresentationDate(tempDTO1.getRepresentationDate());
								representedDateList1.add(tempDTO3);
								foodAmount=foodAmount+tempFoodAmount;
							}
						}
						itr1=list.iterator();
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return representedDateList1;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<TadaTdDaNewFoodDetailsDTO> getFoodDetails(TadaRequestBean tadaRequestBean)throws Exception{
		List<TadaTdDaNewFoodDetailsDTO> daNewFoodDetailsList=null;
		List<TadaTdDaNewFoodDetailsDTO> daNewFoodDetailsList1=null;
		Session session=null;
		Date minDate=null;
		Date maxDate=null;
		String strMinDate=null;
		String strMaxDate=null;
		Iterator daNewFoodDetailsItr=null;
		TadaTdDaNewFoodDetailsDTO dateCompareDTO=null;
		TadaTdDaNewFoodDetailsDTO tempRepresentationDateDTO=null;
		List<TadaTdDaNewFoodDetailsDTO> tempRepresentationDateList=new ArrayList<TadaTdDaNewFoodDetailsDTO>();
		Iterator tempRepresentationItr=null;
		List<TadaDaNewDetailsDTO> daNewList=null;
		Iterator daNewItr=null;
		TadaDaNewDetailsDTO daNewDetailsDTO=null;
		TadaTdDaNewFoodDetailsDTO repDateCompareDTO=null;
		TadaTdDaNewFoodDetailsDTO repDateDTO=null;
		List<TadaTdDaNewFoodDetailsDTO> repDateList=new ArrayList<TadaTdDaNewFoodDetailsDTO>();
		Iterator repDateItr=null;
		List<TadaTdDaNewFoodDetailsDTO> finalRepresentationList1=new ArrayList<TadaTdDaNewFoodDetailsDTO>();
		float repAmount=0;
		float totRepAmount=0;
		TadaTdFoodDetDayRepDTO tadaTdFoodDetDayRepDTO=null;
		Transaction tx=null;
		List<TadaTdDaNewAccDetailsDTO> daNewAccList=null;
		List<TadaTdLeaveDetailsDTO> tdAttachedLeaveList=null;
		List<LeaveRequestBean> leaveDetailsList=new ArrayList<LeaveRequestBean>();
		int leaveDays=0;
		String leaveStartDate=null;
		String leaveEndDate=null;
		List<TadaTdFoodDetDayRepDTO> tdFoodLeaveList=new ArrayList<TadaTdFoodDetDayRepDTO>();
		List<TadaTdFoodDetDayRepDTO> repDateListFinal=new ArrayList<TadaTdFoodDetDayRepDTO>();
		List<TadaTdDaNewFoodDetailsDTO> repDateListFinal1=new ArrayList<TadaTdDaNewFoodDetailsDTO>();
		List<TadaTdFoodDetDayRepDTO> checkingFoodList=null;
		Float daOnTourPercentage=null;                   //This property has been created newly
		try {
			session=hibernateUtils.getSession();
			checkingFoodList=session.createCriteria(TadaTdFoodDetDayRepDTO.class).add(Expression.eq("settlementRequestId", Integer.parseInt(tadaRequestBean.getRequestId()))).list();
			if(checkingFoodList.size()>0){
				session.createQuery("delete from TadaTdFoodDetDayRepDTO where settlementRequestId=?").setInteger(0, Integer.parseInt(tadaRequestBean.getRequestId())).executeUpdate();
			    session.flush();
			}
			JSONObject mainJson = new JSONObject(tadaRequestBean.getJsonValue());
			JSONObject daNewFoodDetailsJson=(JSONObject)mainJson.get("daNewFoodDetails");
			for (int i = 0; i < daNewFoodDetailsJson.length(); i++) {
				JSONObject valueJson=(JSONObject)daNewFoodDetailsJson.get(String.valueOf(i));
				TadaTdDaNewFoodDetailsDTO tadaTdDaNewFoodDetailsDTO=new TadaTdDaNewFoodDetailsDTO();
				if(!CPSUtils.compareStrings(valueJson.getString("id"), ""))
					tadaTdDaNewFoodDetailsDTO=(TadaTdDaNewFoodDetailsDTO)session.createCriteria(TadaTdDaNewFoodDetailsDTO.class).add(Expression.eq("id", valueJson.getInt("id"))).uniqueResult();
				if(!CPSUtils.isNullOrEmpty(tadaTdDaNewFoodDetailsDTO)){
					TadaTdSettlementDTO tadaTdSettlementDTO=(TadaTdSettlementDTO)session.createCriteria(TadaTdSettlementDTO.class).add(Expression.eq("requestID", Integer.parseInt(tadaRequestBean.getRequestId()))).uniqueResult();
					if(!CPSUtils.isNullOrEmpty(tadaTdSettlementDTO)){
						if(CPSUtils.compareStrings(tadaTdSettlementDTO.getRequestType(), "settlement"))
							tadaTdDaNewFoodDetailsDTO.setType("tadaTdSettlement");
	            		else if(CPSUtils.compareStrings(tadaTdSettlementDTO.getRequestType(), "reimbursement"))
	            			tadaTdDaNewFoodDetailsDTO.setType("tadaTdReimbursement");
					}
					tadaTdDaNewFoodDetailsDTO.setSettlementRequestId(tadaRequestBean.getRequestId());
					tadaTdDaNewFoodDetailsDTO.setFromDate(CPSUtils.convertStringToDate(valueJson.getString("fromDate")));
					tadaTdDaNewFoodDetailsDTO.setToDate(CPSUtils.convertStringToDate(valueJson.getString("toDate")));
					tadaTdDaNewFoodDetailsDTO.setFoodAmount(Float.parseFloat(valueJson.getString("foodAmount")));
					tadaTdDaNewFoodDetailsDTO.setFoodAmountAftRes(Float.parseFloat(valueJson.getString("foodAmountAftRes")));
					tadaTdDaNewFoodDetailsDTO.setClaimedAmount(Float.parseFloat(valueJson.getString("claimedAmount")));
					if(!CPSUtils.compareStrings(valueJson.getString("id"), "") && CPSUtils.compareStrings(valueJson.getString("editType"), "delete")){
            			session.delete(tadaTdDaNewFoodDetailsDTO);
				        session.flush();
					}else if(CPSUtils.compareStrings(valueJson.getString("editType"), "add")){
            			
            		session.saveOrUpdate(tadaTdDaNewFoodDetailsDTO);
					session.flush();
				}
			}
			}
			
			daNewFoodDetailsList=session.createCriteria(TadaTdDaNewFoodDetailsDTO.class).add(Expression.eq("settlementRequestId", tadaRequestBean.getRequestId())).addOrder(Order.asc("fromDate")).list();
			daNewFoodDetailsList1=session.createCriteria(TadaTdDaNewFoodDetailsDTO.class).add(Expression.eq("settlementRequestId", tadaRequestBean.getRequestId())).addOrder(Order.asc("toDate")).list();
			daNewList=session.createCriteria(TadaDaNewDetailsDTO.class).add(Expression.eq("status", 1)).addOrder(Order.asc("gradePay")).list();
			
			daOnTourPercentage=Float.parseFloat(session.createSQLQuery("SELECT daontour.da_on_tour FROM tada_da_on_tour_master daontour,(SELECT DA_VALUE FROM PAY_DEARNESS_ALLOWANCE_MASTER"
					+ " WHERE STATUS=1 AND DA_DATE = (SELECT MAX(DA_DATE) FROM PAY_DEARNESS_ALLOWANCE_MASTER WHERE STATUS=1 )) payda"
					+ " WHERE status =1 AND payda.da_value between daontour.da_range_from and daontour.da_range_to").uniqueResult().toString());     //This is new value of new Da percentage.
			 daOnTourPercentage=daOnTourPercentage/100;    
			 
			 
			minDate=daNewFoodDetailsList.get(0).getFromDate();
			maxDate=daNewFoodDetailsList1.get(daNewFoodDetailsList1.size()-1).getToDate();
			daNewFoodDetailsItr=daNewFoodDetailsList.iterator();
			strMinDate=CPSUtils.formatDate(minDate);
			strMaxDate=CPSUtils.formatDate(maxDate);
			int noOfDays=Integer.parseInt(CPSUtils.daysDifference(strMinDate, strMaxDate))+2;
			for(int i=0;i<noOfDays;i++){
				tempRepresentationDateDTO=new TadaTdDaNewFoodDetailsDTO();
				tempRepresentationDateDTO.setRepresentationDate(strMinDate);
				tempRepresentationDateList.add(tempRepresentationDateDTO);
				strMinDate=CPSUtils.nextDate(strMinDate);
			}
			tempRepresentationItr=tempRepresentationDateList.iterator();
			daNewFoodDetailsItr=daNewFoodDetailsList.iterator();
			while(tempRepresentationItr.hasNext()){
				repDateCompareDTO=(TadaTdDaNewFoodDetailsDTO)tempRepresentationItr.next();
				while(daNewFoodDetailsItr.hasNext()){
					dateCompareDTO=(TadaTdDaNewFoodDetailsDTO)daNewFoodDetailsItr.next();
					if(CPSUtils.convertStringToDate(repDateCompareDTO.getRepresentationDate()).compareTo(CPSUtils.convertStringToDate(CPSUtils.formatDate(dateCompareDTO.getFromDate())))>=0 && CPSUtils.convertStringToDate(repDateCompareDTO.getRepresentationDate()).compareTo(CPSUtils.convertStringToDate(CPSUtils.formatDate(dateCompareDTO.getToDate())))<=0){
						repDateDTO=new TadaTdDaNewFoodDetailsDTO();
						repDateDTO.setRepresentationDate(repDateCompareDTO.getRepresentationDate());
						repDateDTO.setRepresentationDateOne(repDateCompareDTO.getRepresentationDate());
						repDateList.add(repDateDTO);
						break;
					}
				}
				daNewFoodDetailsItr=daNewFoodDetailsList.iterator();
			}
			daNewItr=daNewList.iterator();
			repDateItr=repDateList.iterator();
			daNewFoodDetailsItr=daNewFoodDetailsList.iterator();
			for(int i=0;i<repDateList.size();i++){
				for(int j=0;j<daNewFoodDetailsList.size();j++){
					if(CPSUtils.convertStringToDate(repDateList.get(i).getRepresentationDate()).compareTo(CPSUtils.convertStringToDate(CPSUtils.formatDate(daNewFoodDetailsList.get(j).getFromDate())))>=0 && CPSUtils.convertStringToDate(repDateList.get(i).getRepresentationDate()).compareTo(CPSUtils.convertStringToDate(CPSUtils.formatDate(daNewFoodDetailsList.get(j).getToDate())))<=0){
						int dayDiff=Integer.parseInt(CPSUtils.daysDifference(CPSUtils.formatDate(daNewFoodDetailsList.get(j).getFromDate()), CPSUtils.formatDate(daNewFoodDetailsList.get(j).getToDate())))+2;
						float amount=daNewFoodDetailsList.get(j).getFoodAmountAftRes()/dayDiff;
						repDateList.get(i).setClaimedAmount(repDateList.get(i).getClaimedAmount()+amount);
					}
				}
			}
			for(int i=0;i<repDateList.size();i++){
				for(int j=0;j<daNewFoodDetailsList.size();j++){
					if(CPSUtils.convertStringToDate(repDateList.get(i).getRepresentationDate()).compareTo(CPSUtils.convertStringToDate(CPSUtils.formatDate(daNewFoodDetailsList.get(j).getFromDate())))>=0 && CPSUtils.convertStringToDate(repDateList.get(i).getRepresentationDate()).compareTo(CPSUtils.convertStringToDate(CPSUtils.formatDate(daNewFoodDetailsList.get(j).getToDate())))<=0){
						int dayDiff=Integer.parseInt(CPSUtils.daysDifference(CPSUtils.formatDate(daNewFoodDetailsList.get(j).getFromDate()), CPSUtils.formatDate(daNewFoodDetailsList.get(j).getToDate())))+2;
						float amount=daNewFoodDetailsList.get(j).getFoodAmountAftRes()/dayDiff;
						repDateList.get(i).setRepresentationAmount1(repDateList.get(i).getRepresentationAmount1()+amount);
					}
				}
			}
			while(daNewItr.hasNext()){
				daNewDetailsDTO=(TadaDaNewDetailsDTO)daNewItr.next();
				if(daNewDetailsDTO.getGradePay()==Integer.parseInt(tadaRequestBean.getPayDetailsList().getGradePay())){
					for(int i=0;i<repDateList.size();i++){
						if(repDateList.get(i).getRepresentationAmount1()>((daNewDetailsDTO.getFoodBill())+(daNewDetailsDTO.getFoodBill())*daOnTourPercentage)){              // Here  i changed da0.25 to 0.50
							repDateList.get(i).setRepresentationAmount1(Math.abs(((daNewDetailsDTO.getFoodBill())+(daNewDetailsDTO.getFoodBill())*daOnTourPercentage)));     // Here  i changed da0.25 to 0.50
						} else {
							repDateList.get(i).setRepresentationAmount1(Math.abs(repDateList.get(i).getRepresentationAmount1()));
						}
					}
				}
			}
			for(int i=0;i<daNewFoodDetailsList.size();i++){
				totRepAmount=daNewFoodDetailsList.get(i).getFoodAmountAftRes()+totRepAmount;
			}
			repAmount=totRepAmount/noOfDays;
			daNewItr=daNewList.iterator();
			while(daNewItr.hasNext()){
				daNewDetailsDTO=(TadaDaNewDetailsDTO)daNewItr.next();
				if(daNewDetailsDTO.getGradePay()==Integer.parseInt(tadaRequestBean.getPayDetailsList().getGradePay())){
					for(int i=0;i<repDateList.size();i++){
						if(repAmount>((daNewDetailsDTO.getFoodBill())+(daNewDetailsDTO.getFoodBill())*daOnTourPercentage)){                          // Here  i changed da0.25 to 0.50
							repDateList.get(i).setRepresentationAmount2(Math.abs(((daNewDetailsDTO.getFoodBill())+(daNewDetailsDTO.getFoodBill())*daOnTourPercentage)));                // Here  i changed da0.25 to 0.50
						} else {
							repDateList.get(i).setRepresentationAmount2(Math.abs(repAmount));
						}
					}
				}
			}
			TadaTdSettlementDTO tadaTdSettlementDTO=(TadaTdSettlementDTO)session.createCriteria(TadaTdSettlementDTO.class).add(Expression.eq("requestID", Integer.parseInt(tadaRequestBean.getRequestId()))).uniqueResult();
			if(!CPSUtils.isNullOrEmpty(tadaTdSettlementDTO)){
				tdAttachedLeaveList=session.createCriteria(TadaTdLeaveDetailsDTO.class).add(Expression.eq("refRequestId", tadaTdSettlementDTO.getRefRequestId())).list();
			}
			for (TadaTdLeaveDetailsDTO tadaTdLeaveDetailsDTO : tdAttachedLeaveList) {
				LeaveRequestBean leaveRequestBean=(LeaveRequestBean)session.createCriteria(LeaveRequestBean.class).add(Expression.eq("requestID", String.valueOf(tadaTdLeaveDetailsDTO.getLeaveRequestId()))).uniqueResult();
				leaveDetailsList.add(leaveRequestBean);
			}
			for (LeaveRequestBean leaveRequestBean : leaveDetailsList) {
				leaveStartDate=leaveRequestBean.getStrFromDate();
				leaveDays=Integer.parseInt(CPSUtils.daysDifference(leaveRequestBean.getStrFromDate(), leaveRequestBean.getStrToDate()))+2;
				for(int i=0;i<leaveDays;i++){
					TadaTdFoodDetDayRepDTO tdFoodLeaveDTO=new TadaTdFoodDetDayRepDTO();
					tdFoodLeaveDTO.setRepresentationDate(leaveStartDate);
					if(CPSUtils.compareStrings(leaveRequestBean.getFromHalfDayFlag(), "Y")){
						if(i==0){
							tdFoodLeaveDTO.setHalfDayFlag("Y");
						}
					}
					if(CPSUtils.compareStrings(leaveRequestBean.getToHalfDayFlag(), "Y")){
						if(i==leaveDays-1){
							tdFoodLeaveDTO.setHalfDayFlag("Y");
						}
					}
					tdFoodLeaveList.add(tdFoodLeaveDTO);
					leaveStartDate=CPSUtils.nextDate(leaveStartDate);
				}
			}
			for(int i=0;i<repDateList.size();i++){
				for(int j=0;j<tdFoodLeaveList.size();j++){
					if(CPSUtils.convertStringToDate(repDateList.get(i).getRepresentationDate()).compareTo(CPSUtils.convertStringToDate(tdFoodLeaveList.get(j).getRepresentationDate()))==0){
						if(CPSUtils.compareStrings(tdFoodLeaveList.get(j).getHalfDayFlag(), "Y")){
							repDateList.get(i).setRepresentationAmount1(repDateList.get(i).getRepresentationAmount1()/2);
						}else{
							repDateList.get(i).setRepresentationAmount1(0);
						}
					}
				}
				
			}
			session.flush();
		
			daNewAccList=session.createCriteria(TadaTdDaNewAccDetailsDTO.class).add(Expression.eq("settlementRequestId", tadaRequestBean.getRequestId())).list(); 
			TadaDaNewDetailsDTO tadaDaNewDetailsDTO=new TadaDaNewDetailsDTO();
			EmployeeClaimDetailsDTO empClaimDetailsDTO=(EmployeeClaimDetailsDTO)session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq("requestID", Integer.parseInt(tadaRequestBean.getRequestId()))).uniqueResult();
			EmpPaymentsDTO empPaymentsDTO=(EmpPaymentsDTO)session.createCriteria(EmpPaymentsDTO.class).add(Expression.eq("sfid", empClaimDetailsDTO.getAppliedBy())).uniqueResult();
			tadaDaNewDetailsDTO=(TadaDaNewDetailsDTO)session.createCriteria(TadaDaNewDetailsDTO.class).add(Expression.eq("gradePay", Integer.parseInt(empPaymentsDTO.getGradePay()))).add(Expression.eq("status", 1)).uniqueResult();
			
			for(int i=0;i<repDateList.size();i++){
				tadaTdFoodDetDayRepDTO=new TadaTdFoodDetDayRepDTO();
				tadaTdFoodDetDayRepDTO.setRepresentationDate(repDateList.get(i).getRepresentationDate());
				tadaTdFoodDetDayRepDTO.setRepresentationAmount1(repDateList.get(i).getRepresentationAmount1());
				tadaTdFoodDetDayRepDTO.setRepresentationAmount2(repDateList.get(i).getRepresentationAmount2());
				tadaTdFoodDetDayRepDTO.setSettlementRequestId(Integer.parseInt(tadaRequestBean.getRequestId()));
				tadaTdFoodDetDayRepDTO.setClaimedAmount(repDateList.get(i).getClaimedAmount());
				tadaTdFoodDetDayRepDTO.setEligibleAmount(((tadaDaNewDetailsDTO.getFoodBill())+tadaDaNewDetailsDTO.getFoodBill()*daOnTourPercentage));                         // Here  i changed da0.25 to 0.50
			   
				session.clear();
				session.saveOrUpdate(tadaTdFoodDetDayRepDTO);
				session.flush();
				if(session.contains(tadaTdFoodDetDayRepDTO)){
					session.clear();
				}
			}

			for (TadaTdDaNewFoodDetailsDTO tadaTdDaNewFoodDetailsDTO : repDateList) {
				TadaTdFoodDetDayRepDTO tadaTdFoodDetDayRepDTO2=new TadaTdFoodDetDayRepDTO();
				tadaTdFoodDetDayRepDTO2=(TadaTdFoodDetDayRepDTO)session.createCriteria(TadaTdFoodDetDayRepDTO.class).add(Expression.eq("settlementRequestId", Integer.parseInt(tadaRequestBean.getRequestId()) )).add(Expression.eq("representationDate", tadaTdDaNewFoodDetailsDTO.getRepresentationDate())).uniqueResult();
				if(tadaTdFoodDetDayRepDTO2.getRepresentationDate().contains(" ")){
					tadaTdFoodDetDayRepDTO2.setRepresentationDate(CPSUtils.formattedDate(tadaTdFoodDetDayRepDTO2.getRepresentationDate()));
				}
				
				repDateListFinal.add(tadaTdFoodDetDayRepDTO2);
				
			}
			for (TadaTdFoodDetDayRepDTO tadaTdFoodDetDayRepDTO3 : repDateListFinal) {
				TadaTdDaNewFoodDetailsDTO tadaTdDaNewFoodDetailsDTO=new TadaTdDaNewFoodDetailsDTO();
				tadaTdDaNewFoodDetailsDTO.setId(tadaTdFoodDetDayRepDTO3.getId());
				tadaTdDaNewFoodDetailsDTO.setRepresentationDate(tadaTdFoodDetDayRepDTO3.getRepresentationDate());
				tadaTdDaNewFoodDetailsDTO.setRepresentationDateOne(tadaTdFoodDetDayRepDTO3.getRepresentationDate());
				tadaTdDaNewFoodDetailsDTO.setRepresentationAmount1(tadaTdFoodDetDayRepDTO3.getRepresentationAmount1());
				tadaTdDaNewFoodDetailsDTO.setRepresentationAmount2(tadaTdFoodDetDayRepDTO3.getRepresentationAmount2());
				tadaTdDaNewFoodDetailsDTO.setEligibleAmount(tadaTdFoodDetDayRepDTO3.getEligibleAmount());
				repDateListFinal1.add(tadaTdDaNewFoodDetailsDTO);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally{
			session.clear();
		}
		return repDateListFinal1;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<TadaTdDaNewFoodDetailsDTO> getDayFoodDetails(TadaRequestBean tadaRequestBean)throws Exception{
		List<TadaTdDaNewFoodDetailsDTO> dayFoodList=null;
		Session session=null;
		try{
			session=hibernateUtils.getSession();
			dayFoodList=session.createCriteria(TadaTdFoodDetDayRepDTO.class).add(Expression.eq("settlementRequestId", Integer.parseInt(tadaRequestBean.getRequestId()))).list();
			/*if(dayFoodList.size()>0){
				for(int i=0 ; i<dayFoodList.size(); i++){
					dayFoodList.get(i).setRepresentationDate(CPSUtils.formattedDate(dayFoodList.get(i).getRepresentationDate()));
					
				}
			}*/
			
			session.flush();
		}catch (Exception e) {
			throw e;
		}
		return dayFoodList;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<TadaTdAccDetDayRepDTO> getDayAccDetails(TadaRequestBean tadaRequestBean)throws Exception{
		List<TadaTdAccDetDayRepDTO> dayAccList=null;
		Session session=null;
		try{
			session=hibernateUtils.getSession();
			
			dayAccList=session.createCriteria(TadaTdAccDetDayRepDTO.class).add(Expression.eq("settlementRequestId", Integer.parseInt(tadaRequestBean.getRequestId()))).list();
			
			// because od dirty object problem obj is  updating so code is placed in buisness
		if(dayAccList.size()>0){
				for(int i=0 ; i<dayAccList.size(); i++){
					if(dayAccList.get(i).getRepresentationDate().contains(" ")){
						dayAccList.get(i).setRepresentationDate(CPSUtils.formattedDate(dayAccList.get(i).getRepresentationDate()));
					}
				}
			}
		session.clear();

		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return dayAccList;
	}
	@Override
	public TadaDaNewDetailsDTO getDaNewDetails(TadaRequestBean tadaRequestBean)throws Exception{
		TadaDaNewDetailsDTO tadaDaNewDetailsDTO=new TadaDaNewDetailsDTO();
		Session session=null;
		Float daOnTourPercentage=null;
		try {
			session=hibernateUtils.getSession();
			tadaDaNewDetailsDTO=(TadaDaNewDetailsDTO)session.createCriteria(TadaDaNewDetailsDTO.class).add(Expression.eq("gradePay", Integer.parseInt(tadaRequestBean.getPayDetailsList().getGradePay()))).add(Expression.eq("status", 1)).uniqueResult();
			tadaDaNewDetailsDTO.setDaOnTourPercentage(Float.parseFloat(session.createSQLQuery("SELECT daontour.da_on_tour FROM tada_da_on_tour_master daontour,(SELECT DA_VALUE FROM PAY_DEARNESS_ALLOWANCE_MASTER"
					+ " WHERE STATUS=1 AND DA_DATE = (SELECT MAX(DA_DATE) FROM PAY_DEARNESS_ALLOWANCE_MASTER WHERE STATUS=1 )) payda"
					+ " WHERE status =1 AND payda.da_value between daontour.da_range_from and daontour.da_range_to").uniqueResult().toString()));   //ThisQuery for DAonTour Percentage depend on pay DA master
		} catch (Exception e) {
			throw e;
		}
		return tadaDaNewDetailsDTO;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<TadaTdJDADetailsDTO> getJDADetails(TadaRequestBean tadaRequestBean)throws Exception{
		List<TadaTdJDADetailsDTO> jdaList=new ArrayList<TadaTdJDADetailsDTO>();
		String strMinDate=null;
		String strMaxDate=null;
		Session session=null;
		int daysDiff=0;
		JSONObject valueJson1=null;
		JSONObject valueJson=null;
		JSONObject valueJson2=null;
		JSONObject valueJson3=null;
		String startTime=null;
		List<TadaTdJDADetailsDTO> jdaLeaveList=new ArrayList<TadaTdJDADetailsDTO>();
		List<LeaveRequestBean> tdAttachedLeaveDetailslist=null;
		List<TadaTdLeaveDetailsDTO> tdAttachedLeaveList=null;
		String strLeaveStartDate=null;
		String strLeaveEndDate=null;
		int countLeaveDays=0;
		try {
			session=hibernateUtils.getSession();
			JSONObject mainJson = new JSONObject(tadaRequestBean.getJsonValue());
			JSONObject journeyJson=(JSONObject)mainJson.get("journeyDetails");
			JSONObject minValueJson=(JSONObject)journeyJson.get(String.valueOf(0));
			strMinDate=CPSUtils.formatDate(CPSUtils.convertStringToDate(minValueJson.get("journeyDate").toString()));
			JSONObject maxValueJson=(JSONObject)journeyJson.get(String.valueOf(journeyJson.length()-1));
			strMaxDate=CPSUtils.formatDate(CPSUtils.convertStringToDate(maxValueJson.get("journeyEndDate").toString()));
			daysDiff=Integer.parseInt(CPSUtils.daysDifference(strMinDate, strMaxDate))+2;
			for(int i=0;i<daysDiff;i++){
				TadaTdJDADetailsDTO jDADetailsDTO=new TadaTdJDADetailsDTO();
				jDADetailsDTO.setJourneyDate(strMinDate);
				jdaList.add(jDADetailsDTO);
				strMinDate=CPSUtils.nextDate(strMinDate);
			}
			for(int i=0;i<jdaList.size();i++){
				boolean status=false;
				for(int j=0;j<journeyJson.length();j++){
					if(status)
						break;
					valueJson=(JSONObject)journeyJson.get(String.valueOf(j));
					if(j!=0){
						valueJson1=(JSONObject)journeyJson.get(String.valueOf(j-1));
					}
					if(CPSUtils.compareStrings(jdaList.get(i).getJourneyDate(), valueJson.get("journeyDate").toString()) && !CPSUtils.compareStrings(jdaList.get(i).getJourneyDate(), valueJson.get("journeyEndDate").toString())){
						if(j==0){
							startTime=valueJson.getString("startTime");
							if(Integer.parseInt(startTime.trim().split(":")[0])<12){
								jdaList.get(i).setJda(1);
								jdaList.get(i).setSda(0);
								jdaList.get(i).setPlace(valueJson.getString("startStation"));
								status=true;
								break;
							}else if(Integer.parseInt(startTime.trim().split(":")[0])==12 && Integer.parseInt(startTime.trim().split(":")[1])==0){
								jdaList.get(i).setJda(0.7f);
								jdaList.get(i).setSda(0);
								jdaList.get(i).setPlace(valueJson.getString("startStation"));
								status=true;
								break;
							}else if(Integer.parseInt(startTime.trim().split(":")[0])>=12 && Integer.parseInt(startTime.trim().split(":")[0])<18){
								jdaList.get(i).setJda(0.7f);
								jdaList.get(i).setSda(0);
								jdaList.get(i).setPlace(valueJson.getString("startStation"));
								status=true;
								break;
							}else if(Integer.parseInt(startTime.trim().split(":")[0])>=18){
								jdaList.get(i).setJda(0);
								jdaList.get(i).setSda(0);
								jdaList.get(i).setPlace(valueJson.getString("startStation"));
								status=true;
								break;
							}
						}else{
							if(CPSUtils.compareStrings(valueJson.get("journeyDate").toString(), valueJson1.get("journeyEndDate").toString())){
								String timeDiff=CPSUtils.timeDifference(valueJson.getString("startTime"), valueJson1.getString("endTime"));
								if((Integer.parseInt(timeDiff.trim().split(":")[0])>12) || (Integer.parseInt(timeDiff.trim().split(":")[0])==12 && Integer.parseInt(timeDiff.trim().split(":")[1])>0)){
									jdaList.get(i).setSda(1);
									jdaList.get(i).setJda(0);
									jdaList.get(i).setPlace(valueJson.getString("startStation"));
									status=true;
									break;
								}else if(Integer.parseInt(timeDiff.trim().split(":")[0])<=12 && Integer.parseInt(timeDiff.trim().split(":")[0])>6){
									jdaList.get(i).setSda(0.7f);
									startTime=valueJson.getString("startTime");
									if(Integer.parseInt(startTime.trim().split(":")[0])<12){
										jdaList.get(i).setJda(0.3f);
									}else if(Integer.parseInt(startTime.trim().split(":")[0])==12 && Integer.parseInt(startTime.trim().split(":")[1])==0){
										jdaList.get(i).setJda(0.3f);
									}else if(Integer.parseInt(startTime.trim().split(":")[0])>=12 && Integer.parseInt(startTime.trim().split(":")[0])<18){
										jdaList.get(i).setJda(0.3f);
									}else if(Integer.parseInt(startTime.trim().split(":")[0])>=18){
										jdaList.get(i).setJda(0);
									}
									jdaList.get(i).setPlace(valueJson.getString("startStation"));
									status=true;
									break;
								}else if(Integer.parseInt(timeDiff.trim().split(":")[0])<=12 && Integer.parseInt(timeDiff.trim().split(":")[0])==6 && Integer.parseInt(timeDiff.trim().split(":")[1])>0){
									jdaList.get(i).setSda(0.7f);
									startTime=valueJson.getString("startTime");
									if(Integer.parseInt(startTime.trim().split(":")[0])<12){
										jdaList.get(i).setJda(0.3f);
									}else if(Integer.parseInt(startTime.trim().split(":")[0])==12 && Integer.parseInt(startTime.trim().split(":")[1])==0){
										jdaList.get(i).setJda(0.3f);
									}else if(Integer.parseInt(startTime.trim().split(":")[0])>=12 && Integer.parseInt(startTime.trim().split(":")[0])<18){
										jdaList.get(i).setJda(0.3f);
									}else if(Integer.parseInt(startTime.trim().split(":")[0])>=18){
										jdaList.get(i).setJda(0);
									}
									jdaList.get(i).setPlace(valueJson.getString("startStation"));
									status=true;
									break;
								}else if(Integer.parseInt(timeDiff.trim().split(":")[0])<=6){
									jdaList.get(i).setSda(0);
									startTime=valueJson.getString("startTime");
									if(Integer.parseInt(startTime.trim().split(":")[0])<12){
										jdaList.get(i).setJda(1);
									}else if(Integer.parseInt(startTime.trim().split(":")[0])==12 && Integer.parseInt(startTime.trim().split(":")[1])==0){
										jdaList.get(i).setJda(0.7f);
									}else if(Integer.parseInt(startTime.trim().split(":")[0])>=12 && Integer.parseInt(startTime.trim().split(":")[0])<18){
										jdaList.get(i).setJda(0.7f);
									}else if(Integer.parseInt(startTime.trim().split(":")[0])>=18){
										jdaList.get(i).setJda(0);
									}
									jdaList.get(i).setPlace(valueJson.getString("startStation"));
									status=true;
									break;
								}
							}else if(!CPSUtils.compareStrings(valueJson.get("journeyDate").toString(), valueJson1.get("journeyEndDate").toString())){
								startTime=valueJson.getString("startTime");
								if((Integer.parseInt(startTime.trim().split(":")[0])>12) || (Integer.parseInt(startTime.trim().split(":")[0])==12 && Integer.parseInt(startTime.trim().split(":")[1])>0)){
									jdaList.get(i).setSda(1);
									jdaList.get(i).setJda(0);
									jdaList.get(i).setPlace(valueJson.getString("startStation"));
									status=true;
									break;
								}else if(Integer.parseInt(startTime.trim().split(":")[0])<=12 && Integer.parseInt(startTime.trim().split(":")[0])>6){
									jdaList.get(i).setSda(0.7f);
									jdaList.get(i).setJda(0.3f);
									jdaList.get(i).setPlace(valueJson.getString("startStation"));
									status=true;
									break;
								}else if(Integer.parseInt(startTime.trim().split(":")[0])<=12 && (Integer.parseInt(startTime.trim().split(":")[0])==6 && Integer.parseInt(startTime.trim().split(":")[1])>0)){
									jdaList.get(i).setSda(0.7f);
									jdaList.get(i).setJda(0.3f);
									jdaList.get(i).setPlace(valueJson.getString("startStation"));
									status=true;
									break;
								}else if(Integer.parseInt(startTime.trim().split(":")[0])<=6){
									jdaList.get(i).setSda(0);
									jdaList.get(i).setJda(1);
									jdaList.get(i).setPlace(valueJson.getString("startStation"));
									status=true;
									break;
								}
							}	
						}	
					}else if(!CPSUtils.compareStrings(jdaList.get(i).getJourneyDate(), valueJson.get("journeyDate").toString()) && CPSUtils.compareStrings(jdaList.get(i).getJourneyDate(), valueJson.get("journeyEndDate").toString())){
						int stayHrs=0;
						int stayMins=0;
						int journeyHrs=0;
						int journeyMins=0;
						int stayHrs1=0;
						int stayMins1=0;
						String stayTimeDiff="00:00";
						String journeyTimeDiff="00:00";
						boolean flag=false;
						boolean flag1=false;
						boolean flag3=false;
						boolean flag4=false;
						if(j!=journeyJson.length()-1){
							for(int k=j+1;k<journeyJson.length();k++){
								int d=j;
								if(flag3){
									break;
								}
								valueJson3=(JSONObject)journeyJson.get(String.valueOf(j));
								valueJson2=(JSONObject)journeyJson.get(String.valueOf(k));
								if(CPSUtils.compareStrings(valueJson3.get("journeyEndDate").toString(), valueJson2.get("journeyDate").toString())){
									if(flag==false){
										if(flag1==false){
											journeyTimeDiff=valueJson3.getString("endTime");
											flag1=true;
										}
										stayTimeDiff=CPSUtils.timeDifference(valueJson2.getString("startTime"), valueJson3.getString("endTime"));
										stayHrs=stayHrs+Integer.parseInt(stayTimeDiff.trim().split(":")[0]);
										stayMins=stayMins+Integer.parseInt(stayTimeDiff.trim().split(":")[1]);
										journeyHrs=journeyHrs+Integer.parseInt(journeyTimeDiff.trim().split(":")[0]);
										journeyMins=journeyMins+Integer.parseInt(journeyTimeDiff.trim().split(":")[1]);
										stayHrs1=Integer.parseInt(stayTimeDiff.trim().split(":")[0]);
										stayMins1=Integer.parseInt(stayTimeDiff.trim().split(":")[1]);
										if(stayMins>=60){
											stayHrs=stayHrs+(stayMins/60);
											stayMins=stayMins%60;
										}
										if(journeyMins>=60){
											journeyHrs=journeyHrs+(journeyMins/60);
											journeyMins=journeyMins%60;
										}
										if(flag4==false){
											if(stayHrs1>12 || (stayHrs1==12 && stayMins1>0)){
												jdaList.get(i).setSda(1);
												jdaList.get(i).setJda(0);
												jdaList.get(i).setPlace(valueJson3.getString("endStation"));
												flag=true;
												flag4=true;
												status=true;
												break;
											}else if((stayHrs1<=12 && stayHrs1>6) && (stayHrs1<=12 && stayHrs1==6 && stayMins1>0)){
												jdaList.get(i).setSda(0.7f);
												if((Integer.parseInt(journeyTimeDiff.trim().split(":")[0])>6) || (Integer.parseInt(journeyTimeDiff.trim().split(":")[0])==6 && Integer.parseInt(journeyTimeDiff.trim().split(":")[1])>0)){
													jdaList.get(i).setJda(0.3f);
												}
												jdaList.get(i).setPlace(valueJson3.getString("endStation"));
												flag=true;
												flag4=true;
												status=true;
												break;
											}
										}
									}
									if(flag){
										j++;
									}else{
										if(CPSUtils.compareStrings(valueJson2.get("journeyDate").toString(), valueJson2.get("journeyEndDate").toString())){
											journeyTimeDiff=CPSUtils.timeDifference(valueJson2.getString("endTime"), valueJson2.getString("startTime"));
											journeyHrs=journeyHrs+Integer.parseInt(journeyTimeDiff.trim().split(":")[0]);
											journeyMins=journeyMins+Integer.parseInt(journeyTimeDiff.trim().split(":")[1]);
											if(k+1<journeyJson.length())
												j++;
										}else if(!CPSUtils.compareStrings(valueJson2.get("journeyDate").toString(), valueJson2.get("journeyEndDate").toString())){
											journeyTimeDiff=CPSUtils.timeDifference("24:00", valueJson2.getString("startTime"));
											journeyHrs=journeyHrs+Integer.parseInt(journeyTimeDiff.trim().split(":")[0]);
											journeyMins=journeyMins+Integer.parseInt(journeyTimeDiff.trim().split(":")[1]);
											if(k+1<journeyJson.length())
												j++;
										}
									}
									if(d==j){
										if(journeyMins>=60){
											journeyHrs=journeyHrs+(journeyMins/60);
											journeyMins=journeyMins%60;
										}
										if(stayMins>=60){
											stayHrs=stayHrs+(stayMins/60);
											stayMins=stayMins%60;
										}
										if(stayHrs>12){
											jdaList.get(i).setSda(1);
											jdaList.get(i).setJda(0);
											status=true;
											break;
										}else if(stayHrs==12 && stayMins>0){
											jdaList.get(i).setSda(1);
											jdaList.get(i).setJda(0);
											status=true;
											break;
										}else if(stayHrs<=12 && stayHrs>6){
											jdaList.get(i).setSda(0.7f);
											if(journeyHrs>6 || (journeyHrs==6 && journeyMins>0)){
												jdaList.get(i).setJda(0.3f);
											}else if(journeyHrs<=6){
												jdaList.get(i).setJda(0);
											}
											status=true;
											break;
										}else if(stayHrs<=6){
											jdaList.get(i).setSda(0);
											if(journeyHrs>12){
												jdaList.get(i).setJda(1);
											}else if(journeyHrs==12 && journeyMins>0){
												jdaList.get(i).setJda(1);
											}else if(journeyHrs<=12 && journeyHrs>6){
												jdaList.get(i).setJda(0.7f);
											}else if(journeyHrs<=6){
												jdaList.get(i).setJda(0);
											}
											status=true;
											break;
										}
									}
								}else if(!CPSUtils.compareStrings(valueJson3.get("journeyEndDate").toString(), valueJson2.get("journeyDate").toString())){
									if(!CPSUtils.compareStrings(valueJson3.get("journeyDate").toString(), valueJson3.get("journeyEndDate").toString())){
										journeyTimeDiff=valueJson3.getString("endTime");
									}
									stayTimeDiff=CPSUtils.timeDifference("24:00", valueJson3.getString("endTime"));
									stayHrs=stayHrs+Integer.parseInt(stayTimeDiff.trim().split(":")[0]);
									stayMins=stayMins+Integer.parseInt(stayTimeDiff.trim().split(":")[1]);
									journeyHrs=journeyHrs+Integer.parseInt(journeyTimeDiff.trim().split(":")[0]);
									journeyMins=journeyMins+Integer.parseInt(journeyTimeDiff.trim().split(":")[1]);
									if(stayMins>=60){
										stayHrs=stayHrs+(stayMins/60);
										stayMins=stayMins%60;
									}
									if(journeyMins>=60){
										journeyHrs=journeyHrs+(journeyMins/60);
										journeyMins=journeyMins%60;
									}
									if(stayHrs>12){
										jdaList.get(i).setSda(1);
										jdaList.get(i).setJda(0);
										jdaList.get(i).setPlace(valueJson3.getString("endStation"));
									}else if(stayHrs==12 && stayMins>0){
										jdaList.get(i).setSda(1);
										jdaList.get(i).setJda(0);
										jdaList.get(i).setPlace(valueJson3.getString("endStation"));
									}else if(stayHrs<=12 && stayHrs>6){
										jdaList.get(i).setSda(0.7f);
										if(journeyHrs>6 || (journeyHrs==6 && journeyMins>0)){
											jdaList.get(i).setJda(0.3f);
										}else if(journeyHrs<=6){
											jdaList.get(i).setJda(0);
										}
										jdaList.get(i).setPlace(valueJson3.getString("endStation"));
									}else if(stayHrs<=6){
										jdaList.get(i).setSda(0);
										if(journeyHrs>12){
											jdaList.get(i).setJda(1);
										}else if(journeyHrs==12 && journeyMins>0){
											jdaList.get(i).setJda(1);
										}else if((journeyHrs<=12 && journeyHrs>6) || (journeyHrs<=12 && journeyHrs==6 && journeyMins>0)){
											jdaList.get(i).setJda(0.7f);
										}else if(journeyHrs<=6){
											jdaList.get(i).setJda(0);
										}
										jdaList.get(i).setPlace(valueJson3.getString("endStation"));
									}
									flag3=true;
									status=true;
									break;
								}
							}
						}else{
							journeyTimeDiff=valueJson.getString("endTime");
							journeyHrs=Integer.parseInt(journeyTimeDiff.trim().split(":")[0]);
							journeyMins=Integer.parseInt(journeyTimeDiff.trim().split(":")[1]);
							if(journeyHrs>12 || (journeyHrs==12 && journeyMins>0)){
								jdaList.get(i).setJda(1);
								jdaList.get(i).setSda(0);
								jdaList.get(i).setPlace(valueJson.getString("endStation"));
							}else if(journeyHrs>6 || (journeyHrs==6 && journeyMins>0)){
								jdaList.get(i).setJda(0.7f);
								jdaList.get(i).setSda(0);
								jdaList.get(i).setPlace(valueJson.getString("endStation"));
							}else{
								jdaList.get(i).setJda(0);
								jdaList.get(i).setSda(0);
								jdaList.get(i).setPlace(valueJson.getString("endStation"));
							}
						}
						
					}else if(CPSUtils.compareStrings(jdaList.get(i).getJourneyDate(), valueJson.get("journeyDate").toString()) && CPSUtils.compareStrings(jdaList.get(i).getJourneyDate(), valueJson.get("journeyEndDate").toString())){
						int stayHrs=0;
						int stayMins=0;
						int journeyHrs=0;
						int journeyMins=0;
						int stayHrs1=0;
						int stayMins1=0;
						String stayTimeDiff="00:00";
						String journeyTimeDiff="00:00";
						boolean flag=false;
						boolean flag1=false;
						boolean flag3=false;
						boolean flag4=false;
						if(j!=journeyJson.length()-1){
							for(int k=j+1;k<journeyJson.length();k++){
								int d=j;
								valueJson3=(JSONObject)journeyJson.get(String.valueOf(j));
								valueJson2=(JSONObject)journeyJson.get(String.valueOf(k));
								if(flag3){
									break;
								}
								if(CPSUtils.compareStrings(valueJson3.get("journeyEndDate").toString(), valueJson2.get("journeyDate").toString())){
									if(j!=0){
										valueJson1=(JSONObject)journeyJson.get(String.valueOf(j-1));
										if(!CPSUtils.compareStrings(valueJson1.get("journeyEndDate").toString(), valueJson3.get("journeyDate").toString())){
											stayTimeDiff=valueJson3.getString("startTime");
											stayHrs=stayHrs+Integer.parseInt(stayTimeDiff.split(":")[0].trim());
											stayMins=stayMins+Integer.parseInt(stayTimeDiff.split(":")[1].trim());
											
										}
									}
									if(flag==false){
										if(flag1==false){
											journeyTimeDiff=CPSUtils.timeDifference(valueJson3.getString("endTime"), valueJson3.getString("startTime"));
											flag1=true;
										}
										stayTimeDiff=CPSUtils.timeDifference(valueJson2.getString("startTime"), valueJson3.getString("endTime"));
										stayHrs=stayHrs+Integer.parseInt(stayTimeDiff.trim().split(":")[0]);
										stayMins=stayMins+Integer.parseInt(stayTimeDiff.trim().split(":")[1]);
										journeyHrs=journeyHrs+Integer.parseInt(journeyTimeDiff.trim().split(":")[0]);
										journeyMins=journeyMins+Integer.parseInt(journeyTimeDiff.trim().split(":")[1]);
										stayHrs1=Integer.parseInt(stayTimeDiff.split(":")[0].trim());
										stayMins1=Integer.parseInt(stayTimeDiff.split(":")[1].trim());
										if(stayMins>=60){
											stayHrs=stayHrs+(stayMins/60);
											stayMins=stayMins%60;
										}
										if(journeyMins>=60){
											journeyHrs=journeyHrs+(journeyMins/60);
											journeyMins=journeyMins%60;
										}
										if(flag4==false){
											if(stayHrs1>12 || (stayHrs1==12 && stayMins1>0)){
												jdaList.get(i).setSda(1);
												jdaList.get(i).setJda(0);
												jdaList.get(i).setPlace(valueJson3.getString("endStation"));
												flag=true;
												flag4=true;
												status=true;
												break;
											}else if((stayHrs1<=12 && stayHrs1>6) || (stayHrs1<=12 && stayHrs1==6 && stayMins1>0)){
												jdaList.get(i).setSda(0.7f);
												if(jdaList.size()==1){
													String timeDiff=CPSUtils.timeDifference(maxValueJson.getString("endTime"), minValueJson.getString("startTime"));
													if((Integer.parseInt(timeDiff.split(":")[0])>12) || (Integer.parseInt(timeDiff.split(":")[0])==12 && Integer.parseInt(timeDiff.split(":")[1])>0)){
														jdaList.get(i).setJda(0.3f);
													}
												}else{
													if((Integer.parseInt(journeyTimeDiff.trim().split(":")[0])>6) || (Integer.parseInt(journeyTimeDiff.trim().split(":")[0])==6 && Integer.parseInt(journeyTimeDiff.trim().split(":")[1])>0)){
														jdaList.get(i).setJda(0.3f);
													}
												}
												jdaList.get(i).setPlace(valueJson3.getString("endStation"));
												flag=true;
												flag4=true;
												status=true;
												break;
											}
										}
									}
									if(flag){
										j++;
									}else{
										if(CPSUtils.compareStrings(valueJson2.get("journeyDate").toString(), valueJson2.get("journeyEndDate").toString())){
											journeyTimeDiff=CPSUtils.timeDifference(valueJson2.getString("endTime"), valueJson2.getString("startTime"));
											journeyHrs=journeyHrs+Integer.parseInt(journeyTimeDiff.trim().split(":")[0]);
											journeyMins=journeyMins+Integer.parseInt(journeyTimeDiff.trim().split(":")[1]);
											if(k+1<journeyJson.length())
												j++;
										}else if(!CPSUtils.compareStrings(valueJson2.get("journeyDate").toString(), valueJson2.get("journeyEndDate").toString())){
											journeyTimeDiff=CPSUtils.timeDifference("24:00", valueJson2.getString("startTime"));
											journeyHrs=journeyHrs+Integer.parseInt(journeyTimeDiff.trim().split(":")[0]);
											journeyMins=journeyMins+Integer.parseInt(journeyTimeDiff.trim().split(":")[1]);
											if(k+1<journeyJson.length())
												j++;
										}
									}
									if(d==j){
										if(journeyMins>=60){
											journeyHrs=journeyHrs+(journeyMins/60);
											journeyMins=journeyMins%60;
										}
										if(stayMins>=60){
											stayHrs=stayHrs+(stayMins/60);
											stayMins=stayMins%60;
										}
										if(stayHrs>12){
											jdaList.get(i).setSda(1);
											jdaList.get(i).setJda(0);
											status=true;
											break;
										}else if(stayHrs==12 && stayMins>0){
											jdaList.get(i).setSda(1);
											jdaList.get(i).setJda(0);
											status=true;
											break;
										}else if(stayHrs<=12 && stayHrs>6){
											jdaList.get(i).setSda(0.7f);
											if(journeyHrs>6 || (journeyHrs==6 && journeyMins>0)){
												jdaList.get(i).setJda(0.3f);
											}else if(journeyHrs<=6){
												jdaList.get(i).setJda(0);
											}
											status=true;
											break;
										}else if(stayHrs<=6){
											if(jdaList.size()==1){
												jdaList.get(i).setSda(0);
												if((stayHrs+journeyHrs>12) || (stayHrs+journeyHrs==12 && stayMins+journeyMins>0)){
													jdaList.get(i).setJda(1);
												}else if((stayHrs+journeyHrs>6) || (stayHrs+journeyHrs==6 && stayMins+journeyMins>0)){
													jdaList.get(i).setJda(0.7f);
												}
												status=true;
												break;
											}else{
												jdaList.get(i).setSda(0);
												if(journeyHrs>12){
													jdaList.get(i).setJda(1);
												}else if(journeyHrs==12 && journeyMins>0){
													jdaList.get(i).setJda(1);
												}else if(journeyHrs<=12 && journeyHrs>6){
													jdaList.get(i).setJda(0.7f);
												}else if(journeyHrs<=6){
													jdaList.get(i).setJda(0);
												}
												status=true;
												break;
											}
										}
									}
								}else if(!CPSUtils.compareStrings(valueJson3.get("journeyEndDate").toString(), valueJson2.get("journeyDate").toString())){
									if(j!=0){
										valueJson1=(JSONObject)journeyJson.get(String.valueOf(j-1));
										if(!CPSUtils.compareStrings(valueJson1.get("journeyEndDate").toString(), valueJson3.get("journeyDate").toString())){
											if(!CPSUtils.compareStrings(valueJson3.get("journeyDate").toString(), valueJson3.get("journeyEndDate").toString())){
												journeyTimeDiff=valueJson3.getString("endTime");
											}else{
												journeyTimeDiff=CPSUtils.timeDifference(valueJson3.getString("endTime"), valueJson3.getString("startTime"));
											}
											stayTimeDiff=valueJson3.getString("startTime");
											stayHrs=stayHrs+Integer.parseInt(stayTimeDiff.trim().split(":")[0]);
											stayMins=stayMins+Integer.parseInt(stayTimeDiff.trim().split(":")[1]);
											journeyHrs=journeyHrs+Integer.parseInt(journeyTimeDiff.trim().split(":")[0]);
											journeyMins=journeyMins+Integer.parseInt(journeyTimeDiff.trim().split(":")[1]);
											if(journeyMins>=60){
												journeyHrs=journeyHrs+(journeyMins/60);
												journeyMins=journeyMins%60;
											}
											if(stayMins>=60){
												stayHrs=stayHrs+(stayMins/60);
												stayMins=stayMins%60;
											}
											if(stayHrs>12 || (stayHrs==12 && stayMins>0)){
												jdaList.get(i).setSda(1);
												jdaList.get(i).setJda(0);
												jdaList.get(i).setPlace(valueJson1.getString("endStation"));
											}else if((stayHrs<=12 && stayHrs>6) || (stayHrs<=12 && stayHrs==6 && stayMins>0)){
												jdaList.get(i).setSda(0.7f);
												jdaList.get(i).setJda(0.3f);
												jdaList.get(i).setPlace(valueJson1.getString("endStation"));
											}else if(stayHrs<=6){
												stayTimeDiff=CPSUtils.timeDifference("24:00", valueJson3.getString("endTime"));
												stayHrs=stayHrs+Integer.parseInt(stayTimeDiff.trim().split(":")[0]);
												stayMins=stayMins+Integer.parseInt(stayTimeDiff.trim().split(":")[1]);
												if(stayMins>=60){
													stayHrs=stayHrs+(stayMins/60);
													stayMins=stayMins%60;
												}
												if(stayHrs>12){
													jdaList.get(i).setSda(1);
													jdaList.get(i).setJda(0);
													jdaList.get(i).setPlace(valueJson3.getString("endStation"));
												}else if(stayHrs==12 && stayMins>0){
													jdaList.get(i).setSda(1);
													jdaList.get(i).setJda(0);
													jdaList.get(i).setPlace(valueJson3.getString("endStation"));
												}else if(stayHrs<=12 && stayHrs>6){
													jdaList.get(i).setSda(0.7f);
													if(stayHrs+journeyHrs>12 || (stayHrs+journeyHrs==12 && stayMins+journeyMins>0)){
														jdaList.get(i).setJda(0.3f);
													}else if(stayHrs+journeyHrs<=12){
														jdaList.get(i).setJda(0);
													}
													jdaList.get(i).setPlace(valueJson3.getString("endStation"));
												}else if(stayHrs<=6){
													if(journeyHrs>12){
														jdaList.get(i).setSda(0);
														jdaList.get(i).setJda(1);
													}else if(journeyHrs==12 && journeyMins>0){
														jdaList.get(i).setSda(0);
														jdaList.get(i).setJda(1);
													}else if((journeyHrs<=12 && journeyHrs>6) || (journeyHrs<=12 && journeyHrs==6 && journeyMins>0)){
														jdaList.get(i).setSda(0);
														jdaList.get(i).setJda(0.7f);
													}else if(journeyHrs<=6){
														if((stayHrs+journeyHrs>12) || (stayHrs+journeyHrs==12 && stayMins+journeyMins>0)){
															jdaList.get(i).setSda(0);
															jdaList.get(i).setJda(1);
														}else if((stayHrs+journeyHrs>6) || (stayHrs+journeyHrs==6 && stayMins+journeyMins>0)){
															jdaList.get(i).setSda(0);
															jdaList.get(i).setJda(0.7f);
														}else{
															jdaList.get(i).setJda(0);
														}
													}
													jdaList.get(i).setPlace(valueJson3.getString("endStation"));
												}
												flag3=true;
												status=true;
												break;
											}
										}else{
											if(!CPSUtils.compareStrings(valueJson3.get("journeyDate").toString(), valueJson3.get("journeyEndDate").toString())){
												journeyTimeDiff=valueJson3.getString("endTime");
											}else{
												journeyTimeDiff=CPSUtils.timeDifference(valueJson3.getString("endTime"), valueJson3.getString("startTime"));
											}
											stayTimeDiff=CPSUtils.timeDifference("24:00", valueJson3.getString("endTime"));
											stayHrs=stayHrs+Integer.parseInt(stayTimeDiff.trim().split(":")[0]);
											stayMins=stayMins+Integer.parseInt(stayTimeDiff.trim().split(":")[1]);
											journeyHrs=journeyHrs+Integer.parseInt(journeyTimeDiff.trim().split(":")[0]);
											journeyMins=journeyMins+Integer.parseInt(journeyTimeDiff.trim().split(":")[1]);
											if(journeyMins>=60){
												journeyHrs=journeyHrs+(journeyMins/60);
												journeyMins=journeyMins%60;
											}
											if(stayMins>=60){
												stayHrs=stayHrs+(stayMins/60);
												stayMins=stayMins%60;
											}
											if(stayHrs>12){
												jdaList.get(i).setSda(1);
												jdaList.get(i).setJda(0);
												jdaList.get(i).setPlace(valueJson3.getString("endStation"));
											}else if(stayHrs==12 && stayMins>0){
												jdaList.get(i).setSda(1);
												jdaList.get(i).setJda(0);
												jdaList.get(i).setPlace(valueJson3.getString("endStation"));
											}else if(stayHrs<=12 && stayHrs>6){
												jdaList.get(i).setSda(0.7f);
												if(stayHrs+journeyHrs>12 || (stayHrs+journeyHrs==12 && stayMins+journeyMins>0)){
													jdaList.get(i).setJda(0.3f);
												}else if(stayHrs+journeyHrs<=12){
													jdaList.get(i).setJda(0);
												}
												jdaList.get(i).setPlace(valueJson3.getString("endStation"));
											}else if(stayHrs<=6){
												if(journeyHrs>12){
													jdaList.get(i).setSda(0);
													jdaList.get(i).setJda(1);
												}else if(journeyHrs==12 && journeyMins>0){
													jdaList.get(i).setSda(0);
													jdaList.get(i).setJda(1);
												}else if((journeyHrs<=12 && journeyHrs>6) || (journeyHrs<=12 && journeyHrs==6 && journeyMins>0)){
													jdaList.get(i).setSda(0);
													jdaList.get(i).setJda(0.7f);
												}else if(journeyHrs<=6){
													if((stayHrs+journeyHrs>12) || (stayHrs+journeyHrs==12 && stayMins+journeyMins>0)){
														jdaList.get(i).setSda(0);
														jdaList.get(i).setJda(1);
													}else if((stayHrs+journeyHrs>6) || (stayHrs+journeyHrs==6 && stayMins+journeyMins>0)){
														jdaList.get(i).setSda(0);
														jdaList.get(i).setJda(0.7f);
													}else{
														jdaList.get(i).setJda(0);
													}
												}
												jdaList.get(i).setPlace(valueJson3.getString("endStation"));
											}
											flag3=true;
											status=true;
											break;
										}
									}else{
										if(!CPSUtils.compareStrings(valueJson3.get("journeyDate").toString(), valueJson3.get("journeyEndDate").toString())){
											journeyTimeDiff=valueJson3.getString("endTime");
										}else{
											journeyTimeDiff=CPSUtils.timeDifference(valueJson3.getString("endTime"), valueJson3.getString("startTime"));
										}
										stayTimeDiff=CPSUtils.timeDifference("24:00", valueJson3.getString("endTime"));
										stayHrs=stayHrs+Integer.parseInt(stayTimeDiff.trim().split(":")[0]);
										stayMins=stayMins+Integer.parseInt(stayTimeDiff.trim().split(":")[1]);
										journeyHrs=journeyHrs+Integer.parseInt(journeyTimeDiff.trim().split(":")[0]);
										journeyMins=journeyMins+Integer.parseInt(journeyTimeDiff.trim().split(":")[1]);
										if(journeyMins>=60){
											journeyHrs=journeyHrs+(journeyMins/60);
											journeyMins=journeyMins%60;
										}
										if(stayMins>=60){
											stayHrs=stayHrs+(stayMins/60);
											stayMins=stayMins%60;
										}
										if(stayHrs>12){
											jdaList.get(i).setSda(1);
											jdaList.get(i).setJda(0);
											jdaList.get(i).setPlace(valueJson3.getString("endStation"));
										}else if(stayHrs==12 && stayMins>0){
											jdaList.get(i).setSda(1);
											jdaList.get(i).setJda(0);
											jdaList.get(i).setPlace(valueJson3.getString("endStation"));
										}else if(stayHrs<=12 && stayHrs>6){
											jdaList.get(i).setSda(0.7f);
											if(stayHrs+journeyHrs>12 || (stayHrs+journeyHrs==12 && stayMins+journeyMins>0)){
												jdaList.get(i).setJda(0.3f);
											}else if(stayHrs+journeyHrs<=12){
												jdaList.get(i).setJda(0);
											}
											jdaList.get(i).setPlace(valueJson3.getString("endStation"));
										}else if(stayHrs<=6){
											if(journeyHrs>12){
												jdaList.get(i).setSda(0);
												jdaList.get(i).setJda(1);
											}else if(journeyHrs==12 && journeyMins>0){
												jdaList.get(i).setSda(0);
												jdaList.get(i).setJda(1);
											}else if((journeyHrs<=12 && journeyHrs>6) || (journeyHrs<=12 && journeyHrs==6 && journeyMins>0)){
												jdaList.get(i).setSda(0);
												jdaList.get(i).setJda(0.7f);
											}else if(journeyHrs<=6){
												if((stayHrs+journeyHrs>12) || (stayHrs+journeyHrs==12 && stayMins+journeyMins>0)){
													jdaList.get(i).setSda(0);
													jdaList.get(i).setJda(1);
												}else if((stayHrs+journeyHrs>6) || (stayHrs+journeyHrs==6 && stayMins+journeyMins>0)){
													jdaList.get(i).setSda(0);
													jdaList.get(i).setJda(0.7f);
												}else{
													jdaList.get(i).setJda(0);
												}
											}
											jdaList.get(i).setPlace(valueJson3.getString("endStation"));
										}
										flag3=true;
										status=true;
										break;
									}
								}
							}
						}else{
							stayTimeDiff=valueJson.getString("startTime");
							journeyTimeDiff=CPSUtils.timeDifference(valueJson.getString("endTime"), valueJson.getString("startTime"));
							String diff=valueJson.getString("endTime");
							stayHrs=Integer.parseInt(stayTimeDiff.split(":")[0].trim());
							stayMins=Integer.parseInt(stayTimeDiff.split(":")[1].trim());
							journeyHrs=Integer.parseInt(journeyTimeDiff.split(":")[0].trim());
							journeyMins=Integer.parseInt(journeyTimeDiff.split(":")[1].trim());
							if((Integer.parseInt(diff.split(":")[0].trim())>12) || (Integer.parseInt(diff.split(":")[0].trim())==12 && Integer.parseInt(diff.split(":")[0].trim())>0)){
								if(stayHrs>12 || (stayHrs==12 && stayMins>0)){
									jdaList.get(i).setSda(1);
									jdaList.get(i).setJda(0);
									jdaList.get(i).setPlace(valueJson.getString("startStation"));
								}else if((stayHrs<=12 && stayHrs>6) || (stayHrs<=12 && stayHrs==6 && stayMins>0)){
									jdaList.get(i).setSda(0.7f);
									jdaList.get(i).setJda(0.3f);
									jdaList.get(i).setPlace(valueJson.getString("startStation"));
								}else if(stayHrs<=6){
									if(journeyHrs>12 || (journeyHrs==12 && journeyMins>0)){
										jdaList.get(i).setJda(1);
										jdaList.get(i).setSda(0);
										jdaList.get(i).setPlace(valueJson.getString("startStation"));
									}else if((journeyHrs<=12 && journeyHrs>6) || (journeyHrs<=12 && journeyHrs==6 && journeyMins>0)){
										jdaList.get(i).setJda(0.7f);
										jdaList.get(i).setSda(0);
										jdaList.get(i).setPlace(valueJson.getString("startStation"));
									}else if(journeyHrs<=6){
										jdaList.get(i).setJda(0);
										jdaList.get(i).setSda(0);
										jdaList.get(i).setPlace(valueJson.getString("startStation"));
									}
								}
							}else if((Integer.parseInt(diff.split(":")[0].trim())>6) || (Integer.parseInt(diff.split(":")[0].trim())==6 && Integer.parseInt(diff.split(":")[0].trim())>0)){
								if(stayHrs>12 || (stayHrs==12 && stayMins>0)){
									jdaList.get(i).setSda(1);
									jdaList.get(i).setJda(0);
									jdaList.get(i).setPlace(valueJson.getString("startStation"));
								}else if((stayHrs<=12 && stayHrs>6) || (stayHrs<=12 && stayHrs==6 && stayMins>0)){
									jdaList.get(i).setSda(0.7f);
									jdaList.get(i).setJda(0);
									jdaList.get(i).setPlace(valueJson.getString("startStation"));
								}else if(stayHrs<=6){
									if(journeyHrs>12 || (journeyHrs==12 && journeyMins>0)){
										jdaList.get(i).setJda(1);
										jdaList.get(i).setSda(0);
										jdaList.get(i).setPlace(valueJson.getString("startStation"));
									}else if((journeyHrs<=12 && journeyHrs>6) || (journeyHrs<=12 && journeyHrs==6 && journeyMins>0)){
										jdaList.get(i).setJda(0.7f);
										jdaList.get(i).setSda(0);
										jdaList.get(i).setPlace(valueJson.getString("startStation"));
									}else if(journeyHrs<=6){
										jdaList.get(i).setJda(0);
										jdaList.get(i).setSda(0);
										jdaList.get(i).setPlace(valueJson.getString("startStation"));
									}
								}
							}
						}
					}else if(!CPSUtils.compareStrings(jdaList.get(i).getJourneyDate(), valueJson.get("journeyDate").toString()) && !CPSUtils.compareStrings(jdaList.get(i).getJourneyDate(), valueJson.get("journeyEndDate").toString())){
						boolean flagVal=true;
						String tdPlace=null;
						for(int m=0;m<journeyJson.length();m++){
							valueJson3=(JSONObject)journeyJson.get(String.valueOf(m));
							if(m!=0){
								valueJson2=(JSONObject)journeyJson.get(String.valueOf(m-1));
								if(CPSUtils.convertStringToDate(jdaList.get(i).getJourneyDate()).compareTo(CPSUtils.convertStringToDate(valueJson2.get("journeyEndDate").toString()))>=0 && 
										CPSUtils.convertStringToDate(jdaList.get(i).getJourneyDate()).compareTo(CPSUtils.convertStringToDate(valueJson3.get("journeyDate").toString()))<=0){
									tdPlace=valueJson3.getString("startStation");
								}
							}
							if(CPSUtils.compareStrings(jdaList.get(i).getJourneyDate(), valueJson3.get("journeyDate").toString()) || CPSUtils.compareStrings(jdaList.get(i).getJourneyDate(), valueJson3.get("journeyEndDate").toString())){
								flagVal=false;
							}
						}
						if(flagVal){
							jdaList.get(i).setSda(1);
							jdaList.get(i).setJda(0);
							if(!CPSUtils.isNullOrEmpty(tdPlace))
								jdaList.get(i).setPlace(tdPlace);
							break;
						}
					}
				}
			}
			for(int i=0;i<jdaList.size();i++){
				for(int j=0;j<tadaRequestBean.getCityTypeList().size();j++){
					if(CPSUtils.compareStrings(jdaList.get(i).getPlace(), tadaRequestBean.getCityTypeList().get(j).getCityName())){
						jdaList.get(i).setCityClass(tadaRequestBean.getCityTypeList().get(j).getCityClass());
						break;
					} else {
						jdaList.get(i).setCityClass("Other");
					}
				}
			}
			for(int j=0;j<tadaRequestBean.getDaOldDetailsList().size();j++){
				for(int k=0;k<tadaRequestBean.getCityTypeList().size();k++){
					if(tadaRequestBean.getDaOldDetailsList().get(j).getCityTypeId()==tadaRequestBean.getCityTypeList().get(k).getId()){
						tadaRequestBean.getDaOldDetailsList().get(j).setCityType(tadaRequestBean.getCityTypeList().get(k).getCityClass());
					}
				}
			}
			for(int i=0;i<jdaList.size();i++){
				for(int j=0;j<tadaRequestBean.getDaOldDetailsList().size();j++){
					if(Integer.parseInt(tadaRequestBean.getPayDetailsList().getBasicPay())>=tadaRequestBean.getDaOldDetailsList().get(j).getPayRangeFrom() && 
							Integer.parseInt(tadaRequestBean.getPayDetailsList().getBasicPay())<=tadaRequestBean.getDaOldDetailsList().get(j).getPayRangeTo()){
						if(CPSUtils.compareStrings(jdaList.get(i).getCityClass(), tadaRequestBean.getDaOldDetailsList().get(j).getCityType())){
							if(CPSUtils.compareStrings("hotelRate", tadaRequestBean.getDaType())){
								jdaList.get(i).setSdaAmount(tadaRequestBean.getDaOldDetailsList().get(j).getHotel());
							} else if(CPSUtils.compareStrings("normalRate", tadaRequestBean.getDaType())){
								jdaList.get(i).setSdaAmount(tadaRequestBean.getDaOldDetailsList().get(j).getOrd());
							}
						}
						if(CPSUtils.compareStrings("Other", tadaRequestBean.getDaOldDetailsList().get(j).getCityType()) || CPSUtils.compareStrings("Others", tadaRequestBean.getDaOldDetailsList().get(j).getCityType())){
							jdaList.get(i).setJdaAmount(tadaRequestBean.getDaOldDetailsList().get(j).getOrd());
						}
					}
				}
			}	
			tdAttachedLeaveList=session.createCriteria(TadaTdLeaveDetailsDTO.class).add(Expression.eq("refRequestId", Integer.parseInt(tadaRequestBean.getRequestId()))).list();
			if(tdAttachedLeaveList.size()>0){
				for (TadaTdLeaveDetailsDTO tadaTdLeaveDetailsDTO : tdAttachedLeaveList) {
					tdAttachedLeaveDetailslist=session.createCriteria(LeaveRequestBean.class).add(Expression.eq("requestID", String.valueOf(tadaTdLeaveDetailsDTO.getLeaveRequestId()))).list();
				}
			}
			if(!CPSUtils.isNullOrEmpty(tdAttachedLeaveDetailslist)){
				if(tdAttachedLeaveDetailslist.size()>0){
					for (LeaveRequestBean leaveRequestBean : tdAttachedLeaveDetailslist) {
						strLeaveStartDate=CPSUtils.formatDate(leaveRequestBean.getFormattedFromDate());
						strLeaveEndDate=CPSUtils.formatDate(leaveRequestBean.getFormattedToDate());
						countLeaveDays=Integer.parseInt(CPSUtils.daysDifference(strLeaveStartDate, strLeaveEndDate))+2;
						for(int i=0;i<countLeaveDays;i++){
							TadaTdJDADetailsDTO tadaTdJDADetailsDTO=new TadaTdJDADetailsDTO();
							tadaTdJDADetailsDTO.setJourneyDate(strLeaveStartDate);
							if(CPSUtils.compareStrings(leaveRequestBean.getFromHalfDayFlag(), "Y")){
								if(i==0){
									tadaTdJDADetailsDTO.setLeaveHalfDayFlag("Y");
								}
							}
							if(CPSUtils.compareStrings(leaveRequestBean.getToHalfDayFlag(), "Y")){
								if(i==countLeaveDays-1){
									tadaTdJDADetailsDTO.setLeaveHalfDayFlag("Y");
								}
							}
							jdaLeaveList.add(tadaTdJDADetailsDTO);
							strLeaveStartDate = CPSUtils.nextDate(strLeaveStartDate);
						}
					}
				}
			}
			for(int i=0;i<jdaList.size();i++){
				for(int j=0;j<jdaLeaveList.size();j++){
					if(CPSUtils.compareTwoDatesDays(CPSUtils.convertStringToDate(jdaList.get(i).getJourneyDate()), CPSUtils.convertStringToDate(jdaLeaveList.get(j).getJourneyDate()))){
						if(CPSUtils.compareStrings(jdaLeaveList.get(j).getLeaveHalfDayFlag(), "Y")){
							jdaList.get(i).setSda(jdaList.get(i).getSda()/2);
						}else{
							jdaList.get(i).setSda(0);
						}
					}
				}
			}
			
		} catch (Exception e) {
			throw e;
		}
		return jdaList;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<TadaProjectDirectorsDTO> getPrjDirNamesList(TadaRequestBean tadaRequestBean)throws Exception{
		List<TadaProjectDirectorsDTO> tempPrjDirNamesList=null;
		List<TadaProjectDirectorsDTO> prjDirNamesList=new ArrayList<TadaProjectDirectorsDTO>();
		Session session=null;
		try {
			session=hibernateUtils.getSession();
			tempPrjDirNamesList=session.createSQLQuery("select unique tpd.sfid as sfID from tada_project_directors tpd").addScalar("sfID", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaProjectDirectorsDTO.class)).list();
			for (TadaProjectDirectorsDTO tadaProjectDirectorsDTO : tempPrjDirNamesList) {
				EmployeeBean empBean=new EmployeeBean();
				empBean=(EmployeeBean)session.createCriteria(EmployeeBean.class).add(Expression.eq("userSfid", tadaProjectDirectorsDTO.getSfID().toUpperCase())).uniqueResult();
				tadaProjectDirectorsDTO.setProjectDirName(empBean.getNameInServiceBook());
				prjDirNamesList.add(tadaProjectDirectorsDTO);
			}
		} catch (Exception e) {
			throw e;
		}
		return prjDirNamesList;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> getTadaLeaveTypeList(TadaRequestBean tadaRequestBean)throws Exception{
		List<KeyValueDTO> tdLeaveTypeList=null;
		Session session=null;
		String qry=null;
		List<String> dateList=new ArrayList<String>();
		Date startDate=null;
		Date endDate=null;
		try {
			session = hibernateUtils.getSession();
			/*startDate=tadaRequestBean.getDepartureDate();
			
			CPSUtils.formatDate(startDate);
			endDate=tadaRequestBean.getArrivalDate();
			CPSUtils.formatDate(endDate);
			while(!CPSUtils.compareTwoDates(startDate, endDate)){
				dateList.add(CPSUtils.formatDate(startDate));
				startDate=CPSUtils.convertStringToDate(CPSUtils.addNoOfDays(CPSUtils.formatDate(startDate), "1"));
			}*/
			
  	//List<KeyValueDTO>	 tdLeaveTypeList1 = 	sqlltc.getLtcLeaveTypeList(tadaRequestBean.getSfid(), tadaRequestBean.getDepartureDate(), endDate);
			
		 qry = "select lrd.request_id as key,ltm.leave_type||'('||to_char(lrd.from_date,'dd-Mon-yyyy')||' '||'To'||' '||to_char(lrd.to_date,'dd-Mon-yyyy')||')' as name " 
			+"from leave_request_details lrd,leave_type_master ltm where lrd.sfid=? and lrd.status not in(6,9)  "
			+"and  ltm.id=lrd.leave_type_id and lrd.leave_type_id =3 " 
			+"and (? between lrd.from_date and lrd.to_date or ? between lrd.from_date and lrd.to_date or lrd.from_date between ? and ? " 
			+"and lrd.to_date between ? and ?)and lrd.request_id not in(select leave_request_id from ltc_request_details where leave_request_id is not null and status not in(6,9)"
			+"union select leave_request_id from ltc_advance_request_details where leave_request_id is not null and status not in(6,9)) order by name";	
		 tdLeaveTypeList = session.createSQLQuery(qry).addScalar("name").addScalar("key", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(0, tadaRequestBean.getSfid()).setDate(1, tadaRequestBean.getDepartureDate()).setDate(2, tadaRequestBean.getArrivalDate()).setDate(3, tadaRequestBean.getDepartureDate()).setDate(4, tadaRequestBean.getArrivalDate()).setDate(5, tadaRequestBean.getDepartureDate()).setDate(6, tadaRequestBean.getArrivalDate()).list();
		 /*dateList.add(CPSUtils.formatDate(startDate));
			qry="select lrd.request_id as key,ltm.leave_type||'('||to_char(lrd.from_date,'dd-Mon-yyyy')||' '||'To'||' '||to_char(lrd.to_date,'dd-Mon-yyyy')||')' as name from leave_request_details lrd," +
					"leave_type_master ltm where lrd.sfid=? and lrd.leave_type_id=ltm.id and (lrd.from_date in (:fromDate) or " +
					"lrd.to_date in (:toDate) or (lrd.from_date<'"+CPSUtils.formatDate(tadaRequestBean.getDepartureDate())+"' and lrd.to_date>'"+CPSUtils.formatDate(tadaRequestBean.getArrivalDate())+"')) and lrd.status not in(9,6)";
			tdLeaveTypeList = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).addScalar("name", Hibernate.STRING).setString(0, tadaRequestBean.getSfID()).setParameterList("fromDate", dateList).setParameterList("toDate", dateList).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();*/
		} catch (Exception e) {
			throw e;
		}
		return tdLeaveTypeList;
	}
	@Override
	@SuppressWarnings("unchecked")
	public void getDirectorateList(TadaRequestBean tadaRequestBean)throws Exception{
		Session session=null;
		try {
			session=hibernateUtils.getSession();
			tadaRequestBean.setDirectorateList(session.createCriteria(DepartmentsDTO.class).add(Expression.eq("status", 1)).list());
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	public TadaRequestBean getTadaRequestTimeBoundDetails(TadaRequestBean tadaRequestBean) throws Exception { 
		Session session=null;
		try{
			session=hibernateUtils.getSession();
			List<KeyValueDTO> requestTimeBoundDetails=null;
			String sql=null;
			sql="select name,value from configuration_details where name in('TADA_REQUEST_TIMEBOUND_PAST','TADA_REQUEST_TIMEBOUND_FUTURE') order by name asc";
			 requestTimeBoundDetails=session.createSQLQuery(sql).addScalar("name",Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list(); 
			 for (KeyValueDTO keyValueDTO : requestTimeBoundDetails) {
				if(CPSUtils.compareStrings("TADA_REQUEST_TIMEBOUND_FUTURE", keyValueDTO.getName())){
					tadaRequestBean.setTadaTimeBoundFuture(keyValueDTO.getValue());
				}else if(CPSUtils.compareStrings("TADA_REQUEST_TIMEBOUND_PAST", keyValueDTO.getName())){
					tadaRequestBean.setTadaTimeBoundPast(keyValueDTO.getValue());     
					}
				}
		}catch(Exception e){
			throw e;
		}
		return tadaRequestBean;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void getTdAppliedUsers(TadaRequestBean tadaRequestBean)throws Exception{
		Session session=null;
		List<TadaApprovalRequestDTO> tdAppliedUsersList=new ArrayList<TadaApprovalRequestDTO>();
		List<TadaApprovalRequestDTO> tdAppliedUsersList1=new ArrayList<TadaApprovalRequestDTO>();
		List<TadaApprovalRequestDTO> tdAppliedUsersList2=new ArrayList<TadaApprovalRequestDTO>();
		List<Integer> statusList=new ArrayList<Integer>();
		List<Date> dateList=new ArrayList<Date>();
		List<String> requestTypeList=new ArrayList<String>();
		List<EmployeeBean>  empList = null;
		dateList.add(tadaRequestBean.getDepartureDate());
		dateList.add(tadaRequestBean.getArrivalDate());
		StringBuffer sql = new StringBuffer();
		StringBuffer sql1 = new StringBuffer();
		try {
			session=hibernateUtils.getSession();
			JSONObject mainJson = new JSONObject(tadaRequestBean.getJsonValue());
			for(int i=0;i<mainJson.length();i++){
				JSONObject tempJson=(JSONObject)mainJson.get(String.valueOf(i));
				
				if((!CPSUtils.isNullOrEmpty(tempJson) )    && tempJson.has("statusVal")){
				if(tempJson.getInt("statusVal")==103)
					statusList.add(2);
				else 
					statusList.add(tempJson.getInt("statusVal"));
				}else if(tempJson.has("requestType")) {
					requestTypeList.add((tempJson.get("requestType")).toString());
				}
			}
			if(CPSUtils.compareStrings(tadaRequestBean.getSearchWith(), "dates") && CPSUtils.compareStrings(tadaRequestBean.getRequestType(), "Select")){
			
				dateList.add(tadaRequestBean.getDepartureDate());
				dateList.add(tadaRequestBean.getArrivalDate());
				tdAppliedUsersList=session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.or(Expression.between("applyDate", tadaRequestBean.getDepartureDate(), tadaRequestBean.getArrivalDate()), Expression.in("applyDate", dateList))).add(Expression.in("status", statusList)).list();
				tdAppliedUsersList1=session.createSQLQuery("select request_id as requestId,request_Type_ID as authorizedMove,applied_By as sfID,applied_Date as applyDate,workFlow_Status as status from emp_claim_details where ((applied_Date " +
				          "between ? and ?) or (applied_Date in (?,?))) and request_Type_ID in (47,48,49) and workFlow_Status in (:status) order by applied_Date asc").addScalar("requestId", Hibernate.STRING).addScalar("authorizedMove", Hibernate.STRING)
				          .addScalar("sfID", Hibernate.STRING).addScalar("applyDate", Hibernate.DATE).addScalar("status", Hibernate.INTEGER).setDate(0, tadaRequestBean.getDepartureDate()).setDate(1, tadaRequestBean.getArrivalDate())
				          .setDate(2, tadaRequestBean.getDepartureDate()).setDate(3, tadaRequestBean.getArrivalDate()).setParameterList("status", statusList)
				          .setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).list();
			}else if(CPSUtils.compareStrings(tadaRequestBean.getSearchWith(), "requestId")){
				tdAppliedUsersList=session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("requestId", tadaRequestBean.getRequestId())).list();
				tdAppliedUsersList1=session.createSQLQuery("select request_id as requestId,request_Type_ID as authorizedMove,applied_By as sfID,applied_Date as applyDate,workFlow_Status as status from emp_claim_details where " +
		                  "request_ID=?  ").addScalar("requestId", Hibernate.STRING).addScalar("authorizedMove", Hibernate.STRING)
		                  .addScalar("sfID", Hibernate.STRING).addScalar("applyDate", Hibernate.DATE).addScalar("status", Hibernate.INTEGER).setInteger(0, Integer.parseInt(tadaRequestBean.getRequestId()))
		                  .setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).list();
			}else if(CPSUtils.compareStrings(tadaRequestBean.getSearchWith(), "Select") && !CPSUtils.compareStrings(tadaRequestBean.getRequestType(), "Select")){
				if(CPSUtils.compareStrings(tadaRequestBean.getRequestType(), "45")){
					tdAppliedUsersList=session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("authorizedMove", "1")).add(Expression.in("status", statusList)).list();
				}else if(CPSUtils.compareStrings(tadaRequestBean.getRequestType(), "46")){
					tdAppliedUsersList=session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("authorizedMove", "2")).add(Expression.in("status", statusList)).list();
				}else if(CPSUtils.compareStrings(tadaRequestBean.getRequestType(), "47") || CPSUtils.compareStrings(tadaRequestBean.getRequestType(), "48") || CPSUtils.compareStrings(tadaRequestBean.getRequestType(), "49")){
					tdAppliedUsersList=session.createSQLQuery("select request_id as requestId,request_Type_ID as authorizedMove,applied_By as sfID,applied_Date as applyDate,workFlow_Status as status from emp_claim_details where " +
			              "request_Type_ID=? and workFlow_Status in (:status)").addScalar("requestId", Hibernate.STRING).addScalar("authorizedMove", Hibernate.STRING)
			              .addScalar("sfID", Hibernate.STRING).addScalar("applyDate", Hibernate.DATE).addScalar("status", Hibernate.INTEGER).setInteger(0, Integer.parseInt(tadaRequestBean.getRequestType())).setParameterList("status", statusList)
			              .setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).list();
				}
			}else if(CPSUtils.compareStrings(tadaRequestBean.getSearchWith(), "sfid") && CPSUtils.compareStrings(tadaRequestBean.getRequestType(), "Select")){
				
				
				   Criteria cry    =session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("sfID", tadaRequestBean.getSfID().toUpperCase())).add(Expression.in("status", statusList));
				
				   if(! CPSUtils.isNullOrEmpty(tadaRequestBean.getDepartureDate())   && !CPSUtils.isNullOrEmpty(tadaRequestBean.getArrivalDate())) {
				    cry.add(Expression.or(Expression.between("applyDate", tadaRequestBean.getDepartureDate(), tadaRequestBean.getArrivalDate()), Expression.in("applyDate", dateList)));
				   }
				    tdAppliedUsersList = cry.list();
				   
				    
			     sql.append("select request_id as requestId,request_Type_ID as authorizedMove,applied_By as sfID,applied_Date as applyDate,workFlow_Status as status from emp_claim_details where applied_by=? " +
				          "and request_Type_ID in (47,48,49) and workFlow_Status in (:status)")  ;
				
				if(! CPSUtils.isNullOrEmpty(tadaRequestBean.getDepartureDate())   && !CPSUtils.isNullOrEmpty(tadaRequestBean.getArrivalDate())) {
					
					sql.append("and ((applied_Date) between  TO_DATE('"+CPSUtils.formatDate(tadaRequestBean.getDepartureDate())+"') and  TO_DATE('"+CPSUtils.formatDate(tadaRequestBean.getArrivalDate())+"')) or ((applied_Date) in (TO_DATE('"+CPSUtils.formatDate(tadaRequestBean.getDepartureDate())+"'),TO_DATE('"+CPSUtils.formatDate(tadaRequestBean.getArrivalDate())+"'))) ");
				}
				
				tdAppliedUsersList1=session.createSQLQuery(sql.toString()).addScalar("requestId", Hibernate.STRING).addScalar("authorizedMove", Hibernate.STRING)
				          .addScalar("sfID", Hibernate.STRING).addScalar("applyDate", Hibernate.DATE).addScalar("status", Hibernate.INTEGER).setString(0, tadaRequestBean.getSfID().toUpperCase()).setParameterList("status", statusList).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).list();
			}else if(CPSUtils.compareStrings(tadaRequestBean.getSearchWith(), "entitlementexemption")){
				
			Criteria cry	=session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("entitleExemption", "1")).add(Expression.in("status", statusList));
				
			if(!CPSUtils.isNullOrEmpty(tadaRequestBean.getArrivalDate())  &&  !CPSUtils.isNullOrEmpty(tadaRequestBean.getDepartureDate())) {
			
			cry.add(Expression.or(Expression.between("applyDate", tadaRequestBean.getDepartureDate(), tadaRequestBean.getArrivalDate()), Expression.in("applyDate", dateList)));
			}
				tdAppliedUsersList  = cry.list();
			
			}else if(CPSUtils.compareStrings(tadaRequestBean.getSearchWith(), "directorate") && CPSUtils.compareStrings(tadaRequestBean.getRequestType(), "Select")){
				
				sql1.append("from TadaApprovalRequestDTO where sfid in (select userSfid from EmployeeBean where office in (select id from OrgInstanceDTO where departmentID=? and status=1) and status=1) and status in (:status) ");
				
				if(!CPSUtils.isNullOrEmpty(tadaRequestBean.getArrivalDate())  &&  !CPSUtils.isNullOrEmpty(tadaRequestBean.getDepartureDate())) {
				sql1.append("and (applyDate between  to_date('"+CPSUtils.formatDate(tadaRequestBean.getDepartureDate())+"') and to_date('"+CPSUtils.formatDate(tadaRequestBean.getArrivalDate())+"')) or (applyDate  in (to_date('"+CPSUtils.formatDate(tadaRequestBean.getDepartureDate())+"'),to_date('"+CPSUtils.formatDate(tadaRequestBean.getArrivalDate())+"'))) ");
				}
				
				tdAppliedUsersList=session.createQuery(sql1.toString()).setInteger(0, Integer.parseInt(tadaRequestBean.getDirectorate())).setParameterList("status", statusList).list();
				
				sql.append("select request_id as requestId,request_Type_ID as authorizedMove,applied_By as sfID,applied_Date as applyDate,workFlow_Status as status from emp_claim_details where applied_by in (select sfid from emp_master where office_id in (select org_role_id from org_role_instance where department_id=? and status=1) and status=1) " +
				          "and request_Type_ID in (47,48,49) and workFlow_Status in (:status) ");
				
				
				if(!CPSUtils.isNullOrEmpty(tadaRequestBean.getArrivalDate())  &&  !CPSUtils.isNullOrEmpty(tadaRequestBean.getDepartureDate())) {
					sql.append("and ((applied_Date) between  to_date('"+CPSUtils.formatDate(tadaRequestBean.getDepartureDate())+"') and  to_date('"+CPSUtils.formatDate(tadaRequestBean.getArrivalDate())+"')) or ((applied_Date) in (to_date('"+CPSUtils.formatDate(tadaRequestBean.getDepartureDate())+"'),to_date('"+CPSUtils.formatDate(tadaRequestBean.getArrivalDate())+"'))) ");
				}
				  sql.append("order by request_id asc");
              				
				
				tdAppliedUsersList1=session.createSQLQuery(sql.toString()).addScalar("requestId", Hibernate.STRING).addScalar("authorizedMove", Hibernate.STRING)
				          .addScalar("sfID", Hibernate.STRING).addScalar("applyDate", Hibernate.DATE).addScalar("status", Hibernate.INTEGER).setInteger(0, Integer.parseInt(tadaRequestBean.getDirectorate())).setParameterList("status", statusList)
				          .setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).list();
			}else if(CPSUtils.compareStrings(tadaRequestBean.getSearchWith(), "dates") && !CPSUtils.compareStrings(tadaRequestBean.getSearchWith(), "Select")){
				
				dateList.add(tadaRequestBean.getDepartureDate());
				dateList.add(tadaRequestBean.getArrivalDate());
				if(CPSUtils.compareStrings(tadaRequestBean.getRequestType(), "45")){
					tdAppliedUsersList=session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.or(Expression.between("applyDate", tadaRequestBean.getDepartureDate(), tadaRequestBean.getArrivalDate()), Expression.in("applyDate", dateList))).add(Expression.in("status", statusList)).add(Expression.eq("authorizedMove", "1")).list();
				}else if(CPSUtils.compareStrings(tadaRequestBean.getRequestType(), "46")){
					tdAppliedUsersList=session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.or(Expression.between("applyDate", tadaRequestBean.getDepartureDate(), tadaRequestBean.getArrivalDate()), Expression.in("applyDate", dateList))).add(Expression.eq("authorizedMove", "2")).list();
				}else if(CPSUtils.compareStrings(tadaRequestBean.getRequestType(), "47") || CPSUtils.compareStrings(tadaRequestBean.getRequestType(), "48") || CPSUtils.compareStrings(tadaRequestBean.getRequestType(), "49")){
					tdAppliedUsersList=session.createSQLQuery("select request_id as requestId,request_Type_ID as authorizedMove,applied_By as sfID,applied_Date as applyDate,workFlow_Status as status from emp_claim_details where ((applied_Date " +
							"between ? and ?) or (applied_Date in (?,?))) and request_Type_ID=? and workFlow_Status in (:status) order by applied_Date asc").addScalar("requestId", Hibernate.STRING).addScalar("authorizedMove", Hibernate.STRING)
							.addScalar("sfID", Hibernate.STRING).addScalar("applyDate", Hibernate.DATE).addScalar("status", Hibernate.INTEGER).setDate(0, tadaRequestBean.getDepartureDate()).setDate(1, tadaRequestBean.getArrivalDate())
							.setDate(2, tadaRequestBean.getDepartureDate()).setDate(3, tadaRequestBean.getArrivalDate()).setInteger(4, Integer.parseInt(tadaRequestBean.getRequestType()))
							.setParameterList("status", statusList).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).list();
				}
			}else if(CPSUtils.compareStrings(tadaRequestBean.getSearchWith(), "sfid") && !CPSUtils.compareStrings(tadaRequestBean.getSearchWith(), "Select")){
				if(CPSUtils.compareStrings(tadaRequestBean.getRequestType(), "45")){
					
					Criteria cry=session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("sfID", tadaRequestBean.getSfID().toUpperCase())).add(Expression.eq("authorizedMove", "1")).add(Expression.in("status", statusList));
					
					if(!CPSUtils.isNullOrEmpty(tadaRequestBean.getDepartureDate()) && !CPSUtils.isNullOrEmpty(tadaRequestBean.getArrivalDate())) {
						cry.add(Expression.or(Expression.between("applyDate", tadaRequestBean.getDepartureDate(), tadaRequestBean.getArrivalDate()), Expression.in("applyDate", dateList)));
					}
				tdAppliedUsersList = cry.list();
				}else if(CPSUtils.compareStrings(tadaRequestBean.getRequestType(), "46")){
					Criteria cry =session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("sfID", tadaRequestBean.getSfID().toUpperCase())).add(Expression.eq("authorizedMove", "2")).add(Expression.in("status", statusList));
					
					if(!CPSUtils.isNullOrEmpty(tadaRequestBean.getDepartureDate()) && !CPSUtils.isNullOrEmpty(tadaRequestBean.getArrivalDate())) {
						cry.add(Expression.or(Expression.between("applyDate", tadaRequestBean.getDepartureDate(), tadaRequestBean.getArrivalDate()), Expression.in("applyDate", dateList)));
					}
				tdAppliedUsersList = cry.list();
					
					
				}else if(CPSUtils.compareStrings(tadaRequestBean.getRequestType(), "47") || CPSUtils.compareStrings(tadaRequestBean.getRequestType(), "48") || CPSUtils.compareStrings(tadaRequestBean.getRequestType(), "49")){
					
					sql.append("select request_id as requestId,request_Type_ID as authorizedMove,applied_By as sfID,applied_Date as applyDate,workFlow_Status as status from emp_claim_details where applied_by=? " +
					"and request_Type_ID=? and workFlow_Status in (:status)");
					
					
					if(!CPSUtils.isNullOrEmpty(tadaRequestBean.getDepartureDate()) && !CPSUtils.isNullOrEmpty(tadaRequestBean.getArrivalDate())) {
						sql.append("and ((applied_Date) between  TO_DATE(('"+CPSUtils.formatDate(tadaRequestBean.getDepartureDate())+"') and  TO_DATE(('"+CPSUtils.formatDate(tadaRequestBean.getArrivalDate())+"')) or ((applied_Date) in (TO_DATE(('"+CPSUtils.formatDate(tadaRequestBean.getDepartureDate())+"'),TO_DATE(('"+CPSUtils.formatDate(tadaRequestBean.getArrivalDate())+"'))) ");
						}
					
					tdAppliedUsersList=session.createSQLQuery(sql.toString()).addScalar("requestId", Hibernate.STRING).addScalar("authorizedMove", Hibernate.STRING)
					.addScalar("sfID", Hibernate.STRING).addScalar("applyDate", Hibernate.DATE).addScalar("status", Hibernate.INTEGER).setString(0, tadaRequestBean.getSfID()).setInteger(1, Integer.parseInt(tadaRequestBean.getRequestType()))
					.setParameterList("status", statusList).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).list();
				}
			}else if(CPSUtils.compareStrings(tadaRequestBean.getSearchWith(), "directorate") && !CPSUtils.compareStrings(tadaRequestBean.getSearchWith(), "Select")){
				if(CPSUtils.compareStrings(tadaRequestBean.getRequestType(), "45")){
					
					sql.append("from TadaApprovalRequestDTO where sfid in (select userSfid from EmployeeBean where office in (select id from OrgInstanceDTO where departmentID=? and status=1) and status=1) and status in (:status) and authorizedMove='1'");
					
					if(!CPSUtils.isNullOrEmpty(tadaRequestBean.getArrivalDate())  &&  !CPSUtils.isNullOrEmpty(tadaRequestBean.getDepartureDate())) {
						sql.append("and (applyDate between  to_date('"+CPSUtils.formatDate(tadaRequestBean.getDepartureDate())+"') and to_date('"+CPSUtils.formatDate(tadaRequestBean.getArrivalDate())+"')) or (applyDate  in (to_date('"+CPSUtils.formatDate(tadaRequestBean.getDepartureDate())+"'),to_date('"+CPSUtils.formatDate(tadaRequestBean.getArrivalDate())+"'))) ");
						}
					Query cry =session.createQuery(sql.toString())
					.setInteger(0, Integer.parseInt(tadaRequestBean.getDirectorate())).setParameterList("status", statusList);
				
				tdAppliedUsersList = cry.list();
				}else if(CPSUtils.compareStrings(tadaRequestBean.getRequestType(), "46")){
					
					sql.append("from TadaApprovalRequestDTO where sfid in (select userSfid from EmployeeBean where office in (select id from OrgInstanceDTO where departmentID=? and status=1) and status=1) and status in (:status) and authorizedMove='2'");
					if(!CPSUtils.isNullOrEmpty(tadaRequestBean.getArrivalDate())  &&  !CPSUtils.isNullOrEmpty(tadaRequestBean.getDepartureDate())) {
						sql.append("and (applyDate between  to_date('"+CPSUtils.formatDate(tadaRequestBean.getDepartureDate())+"') and to_date('"+CPSUtils.formatDate(tadaRequestBean.getArrivalDate())+"')) or (applyDate  in (to_date('"+CPSUtils.formatDate(tadaRequestBean.getDepartureDate())+"'),to_date('"+CPSUtils.formatDate(tadaRequestBean.getArrivalDate())+"'))) ");
						}
					Query cry =session.createQuery(sql.toString())
					.setInteger(0, Integer.parseInt(tadaRequestBean.getDirectorate())).setParameterList("status", statusList);
				
				tdAppliedUsersList = cry.list();
				
				}else if(CPSUtils.compareStrings(tadaRequestBean.getRequestType(), "47") || CPSUtils.compareStrings(tadaRequestBean.getRequestType(), "48") || CPSUtils.compareStrings(tadaRequestBean.getRequestType(), "49")){
					
					sql.append("select request_id as requestId,request_Type_ID as authorizedMove,applied_By as sfID,applied_Date as applyDate,workFlow_Status as status from emp_claim_details where applied_by in (select sfid from emp_master where office_id in (select org_role_id from org_role_instance where department_id=? and status=1) and status=1) " +
					"and request_Type_ID=? and workFlow_Status in (:status) ");
					
					if(!CPSUtils.isNullOrEmpty(tadaRequestBean.getDepartureDate()) && !CPSUtils.isNullOrEmpty(tadaRequestBean.getArrivalDate())) {
					sql.append("and (applied_Date between "+CPSUtils.convertStringToDate(CPSUtils.formatDate(tadaRequestBean.getDepartureDate()))+" and "+CPSUtils.convertStringToDate(CPSUtils.formatDate(tadaRequestBean.getArrivalDate()))+") or (applied_Date in ("+CPSUtils.convertStringToDate(CPSUtils.formatDate(tadaRequestBean.getDepartureDate()))+","+CPSUtils.convertStringToDate(CPSUtils.formatDate(tadaRequestBean.getArrivalDate()))+"))");
					}
					sql.append("order by request_id asc");
					tdAppliedUsersList=session.createSQLQuery(sql.toString()).addScalar("requestId", Hibernate.STRING).addScalar("authorizedMove", Hibernate.STRING)
					.addScalar("sfID", Hibernate.STRING).addScalar("applyDate", Hibernate.DATE).addScalar("status", Hibernate.INTEGER).setString(0, tadaRequestBean.getDirectorate()).setInteger(1, Integer.parseInt(tadaRequestBean.getRequestType()))
					.setParameterList("status", statusList).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).list();
				}	
			}else if(CPSUtils.compareStrings(tadaRequestBean.getRequestType(), "Select") && CPSUtils.compareStrings(tadaRequestBean.getSearchWith(), "Select")){
	                   			
  				
				if(!CPSUtils.isNullOrEmpty(requestTypeList)){
					
					       for(int i = 0; i<= requestTypeList.size()-1;i++) {
					    	   
					    	  if( requestTypeList.get(i).equals("45")) {
					    		  requestTypeList.set(i, "1");
					    		  tdAppliedUsersList =	  session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("authorizedMove", requestTypeList.get(i))).add(Expression.in("status", statusList)).list();
					    	  }else if(requestTypeList.get(i) .equals("46") ){
					    		  requestTypeList.set(i, "2");
					    		  tdAppliedUsersList1 = session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("authorizedMove", requestTypeList.get(i))).add(Expression.in("status", statusList)).list();
					    	  }
					       }
					
				if(requestTypeList.contains("47") || requestTypeList.contains("48") || requestTypeList.contains("49")){
					tdAppliedUsersList2=session.createSQLQuery("select request_id as requestId,request_Type_ID as authorizedMove,applied_By as sfID,applied_Date as applyDate,workFlow_Status as status from emp_claim_details where "+
					"request_Type_ID in (:requestType) and workFlow_Status in (:status) and (applied_Date " +
				          "between ? and ?) or (applied_Date in (?,?)) order by request_id asc").addScalar("requestId", Hibernate.STRING).addScalar("authorizedMove", Hibernate.STRING)
					.addScalar("sfID", Hibernate.STRING).addScalar("applyDate", Hibernate.DATE).addScalar("status", Hibernate.INTEGER).setParameterList("requestType", requestTypeList)
					.setParameterList("status", statusList).setDate(0,tadaRequestBean.getDepartureDate()).setDate(1, tadaRequestBean.getArrivalDate())
			          .setDate(2, tadaRequestBean.getDepartureDate()).setDate(3, tadaRequestBean.getArrivalDate()).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).list();
				}				
			}
		}
			
			List<String> sfidlist = new ArrayList<String>();
			if((!(CPSUtils.compareStrings(tadaRequestBean.getRequestType(), "Select") && CPSUtils.compareStrings(tadaRequestBean.getSearchWith(), "Select"))) || (tdAppliedUsersList1.size() <1000)) {
			for (TadaApprovalRequestDTO tadaApprovalRequestDTO : tdAppliedUsersList1) {
				tdAppliedUsersList.add(tadaApprovalRequestDTO);
			}
			for (TadaApprovalRequestDTO tadaApprovalRequestDTO : tdAppliedUsersList2) {
				tdAppliedUsersList.add(tadaApprovalRequestDTO);
			}
			}
			for (TadaApprovalRequestDTO tadaApprovalRequestDTO : tdAppliedUsersList) {	
     			
				if(!sfidlist.contains(tadaApprovalRequestDTO.getSfID())) {
				sfidlist.add(tadaApprovalRequestDTO.getSfID());
				}
     			
			}
      
			if((sfidlist.size() > 0)) {
		 empList = session.createCriteria(EmployeeBean.class).add(Expression.in("userSfid",sfidlist)).setFlushMode(FlushMode.ALWAYS).list();
			}
			for (TadaApprovalRequestDTO tadaApprovalRequestDTO : tdAppliedUsersList) {
			
			  for(EmployeeBean empbean : empList){
				  
			if(empbean.getUserSfid().equals(tadaApprovalRequestDTO.getSfID())){
				  
				tadaApprovalRequestDTO.setEmpDetailsList(empbean);
				break;   
			      }
				
			  }
			  
			}
		Boolean b = 	sfidlist.removeAll(sfidlist);
			
			for (TadaApprovalRequestDTO tadaApprovalRequestDTO : tdAppliedUsersList) {
				
				if(!sfidlist.contains(tadaApprovalRequestDTO.getRequestId())) {
					sfidlist.add(tadaApprovalRequestDTO.getRequestId());
				}
			}
			if((sfidlist.size() > 0)) {
				List<String> historyIdist    =session.createSQLQuery("select to_char(max(id)) from request_workflow_history where request_id in (:sfidlist)").setParameterList("sfidlist", sfidlist).setFlushMode(FlushMode.ALWAYS).list();
				for (TadaApprovalRequestDTO tadaApprovalRequestDTO : tdAppliedUsersList) {
			         
					for(String historyid: historyIdist)
						tadaApprovalRequestDTO.setHistoryID(historyid);
				}
				
				List<RequestCommonBean> requestCommonBean= session.createCriteria(RequestCommonBean.class).add(Expression.in("requestID",sfidlist)).add(Expression.eq("stageStatus", 103)).list();
				if(!CPSUtils.isNullOrEmpty(requestCommonBean))
					for(RequestCommonBean requestCommonBean1: requestCommonBean)
					   if(!CPSUtils.isNullOrEmpty(requestCommonBean1)){
						
								for(TadaApprovalRequestDTO tadaApprovalRequestDTO : tdAppliedUsersList)
								tadaApprovalRequestDTO.setStatus(103);
		        }
			}
			statusList=new ArrayList<Integer>();
			for(int i=0;i<mainJson.length();i++){
				JSONObject tempJson=(JSONObject)mainJson.get(String.valueOf(i));
				
				if(tempJson.has("statusVal")) {
				statusList.add(tempJson.getInt("statusVal"));
				}
			}
			if(!statusList.contains(2)){
				for (TadaApprovalRequestDTO tadaApprovalRequestDTO : tdAppliedUsersList) {
					if(tadaApprovalRequestDTO.getStatus()!=2)
						tdAppliedUsersList2.add(tadaApprovalRequestDTO);
				}
				/*if(tdAppliedUsersList1.size() != 0) {
				for (TadaApprovalRequestDTO tadaApprovalRequestDTO : tdAppliedUsersList1) {
					if(tadaApprovalRequestDTO.getStatus()!=2)
						tdAppliedUsersList2.add(tadaApprovalRequestDTO);
				}}*/
				tadaRequestBean.setTdAppliedUsersList(tdAppliedUsersList2);
			}else if(!statusList.contains(103)){
				for (TadaApprovalRequestDTO tadaApprovalRequestDTO : tdAppliedUsersList) {
					if(tadaApprovalRequestDTO.getStatus()!=103)
						tdAppliedUsersList2.add(tadaApprovalRequestDTO);
				}
				tadaRequestBean.setTdAppliedUsersList(tdAppliedUsersList2);
			}else{
				tadaRequestBean.setTdAppliedUsersList(tdAppliedUsersList);
			}
		} catch (Exception e) {
			throw e;
		}
	}
}
