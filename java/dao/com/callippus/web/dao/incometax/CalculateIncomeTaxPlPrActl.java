package com.callippus.web.dao.incometax;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.ConfigDTO;
import com.callippus.web.beans.incometax.IncomeTaxMasterBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.incometax.dto.IncomeTaxConfigDTO;
import com.callippus.web.incometax.dto.IncomeTaxPayBillDTO;
import com.callippus.web.incometax.dto.IncomeTaxRentDTO;
import com.callippus.web.incometax.dto.IncomeTaxRunDTO;
import com.callippus.web.incometax.dto.IncomeTaxRunStatusDTO;
import com.callippus.web.incometax.dto.IncomeTaxSavingsDTO;
import com.callippus.web.incometax.dto.IncomeTaxSavingsRunDTO;
import com.callippus.web.incometax.dto.IncomeTaxSectionDTO;
import com.callippus.web.incometax.dto.PayFinYearDTO;
import com.callippus.web.incometax.dto.PrUpdateAllwDTO;
import com.callippus.web.paybill.dto.PayBillDTO;
import com.callippus.web.paybill.dto.PayBillUserDTO;

@Service
public class CalculateIncomeTaxPlPrActl {

@Autowired
private HibernateUtils hibernateUtils;
  /*Calculating Planned Details::: Start ::::*/

public int getStartingMonthOfFinYear(String finYearId)throws Exception{
	Session session=null;
	PayFinYearDTO payFinYearDTO=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		payFinYearDTO=(PayFinYearDTO) session.get(PayFinYearDTO.class, Integer.parseInt(finYearId));
	}catch (Exception e) {
		throw e;
	}finally{
		session.flush(); //session.close();
	}
	return payFinYearDTO.getFyFrom();
}
public int getTransportAllowance() throws Exception{
	int traAllowance=0;
	Session session=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		//traAllowance=Integer.parseInt(session.createSQLQuery("select value from configuration_details where name='IT_TRANSPORT_ALLOWANCE'").uniqueResult().toString());
		ConfigDTO configDTO=(ConfigDTO)session.createCriteria(ConfigDTO.class).add(Expression.eq("name", "IT_TRANSPORT_ALLOWANCE")).uniqueResult();
		if(!CPSUtils.isNullOrEmpty(configDTO))
			traAllowance=Integer.parseInt(configDTO.getValue());
	}catch (Exception e) {
		throw e;
	}finally{
		session.flush(); //session.close();
	}
	return traAllowance;
}
public int getProffessionalUpdateAllowance(String sfid)throws Exception{
	int value=0;
	Session session=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		value=Integer.parseInt(session.createSQLQuery("select CALCULATE_PRUPDALLOWANCE(?) from dual").setString(0, sfid).uniqueResult().toString());
	}catch (Exception e) {
		throw e;
	}finally{
		session.flush(); //session.close();
	}
	return value;
}
@SuppressWarnings("unchecked")
public Map<String,Object> getPlannedConfigurationDetails(IncomeTaxMasterBean incomeTaxMasterBean)throws Exception{
	Map<String,Object> finalMap=null;
	Session session=null;
	List<IncomeTaxSavingsRunDTO> savingsRunList=null;
	List<IncomeTaxSectionDTO> sectionList=null;
	List<IncomeTaxSavingsDTO> savingsList=null;
	int indSavings=0;
	int indExemptions=0;
	int indOtherIncome=0;
	int totSavings=0;
	int totExemptions=0;
	int totOtherIncome=0;
	IncomeTaxSavingsRunDTO savingsRunDTO=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		finalMap=new TreeMap<String, Object>();
		savingsRunList=new ArrayList<IncomeTaxSavingsRunDTO>();
		sectionList=session.createCriteria(IncomeTaxSectionDTO.class).add(Expression.eq("status", 1)).list();
		sectionList=getUniqueSectionList(sectionList);
		for(int d=0;d<sectionList.size();d++){
			IncomeTaxSectionDTO incomeTaxSectionDTO =sectionList.get(d);
			List<IncomeTaxSectionDTO> uniqueNameSectionList=getUniqueSectionNameList(incomeTaxSectionDTO.getSecName(),incomeTaxSectionDTO.getId());
			List<Integer> intIds= new ArrayList<Integer>();
			for(int i=0;i<uniqueNameSectionList.size();i++){
				intIds.add(uniqueNameSectionList.get(i).getId());
			}
			intIds.add(incomeTaxSectionDTO.getId());
			
			savingsList=session.createCriteria(IncomeTaxSavingsDTO.class).add(Expression.eq("status", 1)).add(Expression.in("sectionId", intIds)).list();
		    for(IncomeTaxSavingsDTO incomeTaxSavingsDTO:savingsList){
		    	savingsRunDTO=calculateSavingsDetails(incomeTaxSavingsDTO,incomeTaxMasterBean.getSearchSfid(),incomeTaxMasterBean.getSelectedFYear(),incomeTaxMasterBean.getSfID(),incomeTaxMasterBean.getRunTypeFlag());
		    	if(CPSUtils.compareStrings(incomeTaxSavingsDTO.getsType(),"Savings"))
		    	indSavings+=savingsRunDTO.getAmount();
		    	else if(CPSUtils.compareStrings(incomeTaxSavingsDTO.getsType(),"Exemptions"))
		    	indExemptions+=savingsRunDTO.getAmount();
		    	else
		        indOtherIncome+=savingsRunDTO.getAmount();
		    	savingsRunList.add(savingsRunDTO);
		    }
		    if(indSavings>getLimit(incomeTaxSectionDTO,incomeTaxMasterBean.getSearchSfid())){
		    	totSavings=+getLimit(incomeTaxSectionDTO,incomeTaxMasterBean.getSearchSfid());
		    }else{
		    	totSavings+=indSavings;
		    }
		    if(indExemptions>getLimit(incomeTaxSectionDTO,incomeTaxMasterBean.getSearchSfid())){
		    	totExemptions=+getLimit(incomeTaxSectionDTO,incomeTaxMasterBean.getSearchSfid());
		    }else{
		    	totExemptions+=indExemptions;
		    }
		    if(indOtherIncome>getLimit(incomeTaxSectionDTO,incomeTaxMasterBean.getSearchSfid())){
		    	totOtherIncome=+getLimit(incomeTaxSectionDTO,incomeTaxMasterBean.getSearchSfid());
		    }else{
		    	totOtherIncome+=indOtherIncome;
		    }
		    indSavings=0;
		    indOtherIncome=0;
		    indExemptions=0;
		}
		finalMap.put("finalList", savingsRunList);
		finalMap.put("totalSavings", totSavings);
		finalMap.put("totalExcmptions", totExemptions);
		finalMap.put("totalOtherIncome", totOtherIncome);
		
	}catch (Exception e) {
		throw e;
	}finally{
		session.flush(); //session.close();
	}
	return finalMap;
}
@SuppressWarnings("unchecked")
public Map<String,Object> getProjectedConfigurationDetails(IncomeTaxMasterBean incomeTaxMasterBean)throws Exception{
	Map<String,Object> finalMap=null;
	Session session=null;
	List<IncomeTaxSavingsRunDTO> savingsRunList=null;
	List<IncomeTaxSectionDTO> sectionList=null;
	List<IncomeTaxSavingsDTO> savingsList=null;
	int indSavings=0;
	int indExemptions=0;
	int indOtherIncome=0;
	int totSavings=0;
	int totExemptions=0;
	int totOtherIncome=0;
	IncomeTaxSavingsRunDTO savingsRunDTO=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		finalMap=new TreeMap<String, Object>();
		savingsRunList=new ArrayList<IncomeTaxSavingsRunDTO>();
		sectionList=session.createCriteria(IncomeTaxSectionDTO.class).add(Expression.eq("status", 1)).list();
		sectionList=getUniqueSectionList(sectionList);
		for(int d=0;d<sectionList.size();d++){
			IncomeTaxSectionDTO incomeTaxSectionDTO =sectionList.get(d);
			List<IncomeTaxSectionDTO> uniqueNameSectionList=getUniqueSectionNameList(incomeTaxSectionDTO.getSecName(),incomeTaxSectionDTO.getId());
			List<Integer> intIds= new ArrayList<Integer>();
			for(int i=0;i<uniqueNameSectionList.size();i++){
				intIds.add(uniqueNameSectionList.get(i).getId());
			}
			intIds.add(incomeTaxSectionDTO.getId());
		
			savingsList=session.createCriteria(IncomeTaxSavingsDTO.class).add(Expression.eq("status", 1)).add(Expression.in("sectionId", intIds)).list();
		    for(IncomeTaxSavingsDTO incomeTaxSavingsDTO:savingsList){
		    	savingsRunDTO=calculateSavingsDetails(incomeTaxSavingsDTO,incomeTaxMasterBean.getSearchSfid(),incomeTaxMasterBean.getSelectedFYear(),incomeTaxMasterBean.getSfID(),incomeTaxMasterBean.getRunTypeFlag());
		    	if(CPSUtils.compareStrings(incomeTaxSavingsDTO.getsType(),"Savings"))
		    	indSavings+=savingsRunDTO.getAmount();
		    	else if(CPSUtils.compareStrings(incomeTaxSavingsDTO.getsType(),"Exemptions"))
		    	indExemptions+=savingsRunDTO.getAmount();
		    	else
		        indOtherIncome+=savingsRunDTO.getAmount();
		    	savingsRunList.add(savingsRunDTO);
		    }
		    if(indSavings>getLimit(incomeTaxSectionDTO,incomeTaxMasterBean.getSearchSfid())){
		    	totSavings+=getLimit(incomeTaxSectionDTO,incomeTaxMasterBean.getSearchSfid());
		    }else{
		    	totSavings+=indSavings;
		    }
		    if(indExemptions>getLimit(incomeTaxSectionDTO,incomeTaxMasterBean.getSearchSfid())){
		    	totExemptions+=getLimit(incomeTaxSectionDTO,incomeTaxMasterBean.getSearchSfid());
		    }else{
		    	totExemptions+=indExemptions;
		    }
		    if(indOtherIncome>getLimit(incomeTaxSectionDTO,incomeTaxMasterBean.getSearchSfid())){
		    	totOtherIncome+=getLimit(incomeTaxSectionDTO,incomeTaxMasterBean.getSearchSfid());
		    }else{
		    	totOtherIncome+=indOtherIncome;
		    }
		    indSavings=0;
		    indOtherIncome=0;
		    indExemptions=0;
		}
		finalMap.put("finalList", savingsRunList);
		finalMap.put("totalSavings", totSavings);
		finalMap.put("totalExcmptions", totExemptions);
		finalMap.put("totalOtherIncome", totOtherIncome);
		
	}catch (Exception e) {
		throw e;
	}finally{
		session.flush(); //session.close();
	}
	return finalMap;
}
@SuppressWarnings("unchecked")
public Map<String,Object> getActualConfigurationDetails(IncomeTaxMasterBean incomeTaxMasterBean)throws Exception{
	Map<String,Object> finalMap=null;
	Session session=null;
	List<IncomeTaxSavingsRunDTO> savingsRunList=null;
	List<IncomeTaxSectionDTO> sectionList=null;
	List<IncomeTaxSavingsDTO> savingsList=null;
	int indSavings=0;
	int indExemptions=0;
	int indOtherIncome=0;
	int totSavings=0;
	int totExemptions=0;
	int totOtherIncome=0;
	IncomeTaxSavingsRunDTO savingsRunDTO=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		finalMap=new TreeMap<String, Object>();
		savingsRunList=new ArrayList<IncomeTaxSavingsRunDTO>();
		sectionList=session.createCriteria(IncomeTaxSectionDTO.class).add(Expression.eq("status", 1)).list();
		sectionList=getUniqueSectionList(sectionList);
		for(int d=0;d<sectionList.size();d++){
			IncomeTaxSectionDTO incomeTaxSectionDTO =sectionList.get(d);
			List<IncomeTaxSectionDTO> uniqueNameSectionList=getUniqueSectionNameList(incomeTaxSectionDTO.getSecName(),incomeTaxSectionDTO.getId());
			List<Integer> intIds= new ArrayList<Integer>();
			for(int i=0;i<uniqueNameSectionList.size();i++){
				intIds.add(uniqueNameSectionList.get(i).getId());
			}
			intIds.add(incomeTaxSectionDTO.getId());
		
			savingsList=session.createCriteria(IncomeTaxSavingsDTO.class).add(Expression.eq("status", 1)).add(Expression.in("sectionId", intIds)).list();
		    for(IncomeTaxSavingsDTO incomeTaxSavingsDTO:savingsList){
		    	savingsRunDTO=calculateSavingsDetails(incomeTaxSavingsDTO,incomeTaxMasterBean.getSearchSfid(),incomeTaxMasterBean.getSelectedFYear(),incomeTaxMasterBean.getSfID(),incomeTaxMasterBean.getRunTypeFlag());
		    	if(CPSUtils.compareStrings(incomeTaxSavingsDTO.getsType(),"Savings"))
		    	indSavings+=savingsRunDTO.getAmount();
		    	else if(CPSUtils.compareStrings(incomeTaxSavingsDTO.getsType(),"Exemptions"))
		    	indExemptions+=savingsRunDTO.getAmount();
		    	else
		        indOtherIncome+=savingsRunDTO.getAmount();
		    	savingsRunList.add(savingsRunDTO);
		    }
		    if(indSavings>getLimit(incomeTaxSectionDTO,incomeTaxMasterBean.getSearchSfid())){
		    	totSavings+=getLimit(incomeTaxSectionDTO,incomeTaxMasterBean.getSearchSfid());
		    }else{
		    	totSavings+=indSavings;
		    }
		    if(indExemptions>getLimit(incomeTaxSectionDTO,incomeTaxMasterBean.getSearchSfid())){
		    	totExemptions+=getLimit(incomeTaxSectionDTO,incomeTaxMasterBean.getSearchSfid());
		    }else{
		    	totExemptions+=indExemptions;
		    }
		    if(indOtherIncome>getLimit(incomeTaxSectionDTO,incomeTaxMasterBean.getSearchSfid())){
		    	totOtherIncome+=getLimit(incomeTaxSectionDTO,incomeTaxMasterBean.getSearchSfid());
		    }else{
		    	totOtherIncome+=indOtherIncome;
		    }
		    indSavings=0;
		    indOtherIncome=0;
		    indExemptions=0;
		}
		finalMap.put("finalList", savingsRunList);
		finalMap.put("totalSavings", totSavings);
		finalMap.put("totalExcmptions", totExemptions);
		finalMap.put("totalOtherIncome", totOtherIncome);
		
	}catch (Exception e) {
		throw e;
	}finally{
		session.flush(); //session.close();
	}
	return finalMap;
}
public IncomeTaxSavingsRunDTO calculateSavingsDetails(IncomeTaxSavingsDTO incomeTaxSavingsDTO,String sfid,String finYearId,String createdBy,String runType)throws Exception{
	Session session=null;
	IncomeTaxConfigDTO configDTO=null;
	IncomeTaxSavingsRunDTO incomeTaxSavingsRunDTO=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		incomeTaxSavingsRunDTO=new IncomeTaxSavingsRunDTO();
		incomeTaxSavingsRunDTO.setFinYearId(Integer.parseInt(finYearId));
		incomeTaxSavingsRunDTO.setRunType(runType);
		incomeTaxSavingsRunDTO.setConfTypeId(incomeTaxSavingsDTO.getId());
		incomeTaxSavingsRunDTO.setSfid(sfid);
		incomeTaxSavingsRunDTO.setCreatedBy(createdBy);
		incomeTaxSavingsRunDTO.setLastModifiedBy(createdBy);
		incomeTaxSavingsRunDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
		incomeTaxSavingsRunDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
		incomeTaxSavingsRunDTO.setStatus(1);
		configDTO=(IncomeTaxConfigDTO) session.createCriteria(IncomeTaxConfigDTO.class).add(Expression.eq("sfID",sfid)).
		add(Expression.eq("selectedFYear", finYearId)).
		add(Expression.eq("savingsTypeId", incomeTaxSavingsDTO.getId())).add(Expression.eq("status", 1)).uniqueResult();
		if(CPSUtils.isNullOrEmpty(configDTO))
			incomeTaxSavingsRunDTO.setAmount(0);
		else{
			if(CPSUtils.compareStrings(runType,"pl")){//Planned amount
				if(CPSUtils.compareStrings(incomeTaxSavingsDTO.getsType(),"Exemptions")){
					incomeTaxSavingsRunDTO.setAmount(0);
				}else if(CPSUtils.compareStrings(incomeTaxSavingsDTO.getsType(),"Savings")){
					incomeTaxSavingsRunDTO.setAmount(0);
				}else{
					incomeTaxSavingsRunDTO.setAmount(0);
				}
			}else if(CPSUtils.compareStrings(runType,"pr")){//Projection amount
                 if(CPSUtils.compareStrings(incomeTaxSavingsDTO.getsType(),"Exemptions")){
                	 incomeTaxSavingsRunDTO.setAmount(configDTO.getActual());
				}else if(CPSUtils.compareStrings(incomeTaxSavingsDTO.getsType(),"Savings")){
					 incomeTaxSavingsRunDTO.setAmount(configDTO.getProjection());
				}else{
					 incomeTaxSavingsRunDTO.setAmount(configDTO.getActual());
				}
			}else{//Actual amount
                 if(CPSUtils.compareStrings(incomeTaxSavingsDTO.getsType(),"Exemptions")){
					 if(CPSUtils.compareStrings(configDTO.getSubmissionFlag(),"true")){
						 incomeTaxSavingsRunDTO.setAmount(configDTO.getActual());
					 }else{
						 incomeTaxSavingsRunDTO.setAmount(0);
					 }
				}else if(CPSUtils.compareStrings(incomeTaxSavingsDTO.getsType(),"Savings")){
					if(CPSUtils.compareStrings(configDTO.getSubmissionFlag(),"true")){
						 incomeTaxSavingsRunDTO.setAmount(configDTO.getActual());
					 }else{
						 incomeTaxSavingsRunDTO.setAmount(0);
					 }
				}else{
					if(CPSUtils.compareStrings(configDTO.getSubmissionFlag(),"true")){
						 incomeTaxSavingsRunDTO.setAmount(configDTO.getActual());
					 }else{
						 incomeTaxSavingsRunDTO.setAmount(0);
					 }
				}
			}
		}
	}catch (Exception e) {
		throw e;
	}finally{
		session.flush(); //session.close();
	}
	
	return incomeTaxSavingsRunDTO;
	
}
public int getLimit(IncomeTaxSectionDTO incomeTaxSectionDTO,String sfid)throws Exception{
	int limit=0;
	Session session=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		limit=Integer.parseInt(session.createSQLQuery("Select Get_incometax_section_flags(?,?) From Dual").
		setString(0, sfid).setString(1, incomeTaxSectionDTO.getSecName()).uniqueResult().toString());
	}catch (Exception e) {
		throw e;
	}finally{
		session.flush(); //session.close();
	}
	return limit;
}
@SuppressWarnings("unchecked")
public List<IncomeTaxSectionDTO> getUniqueSectionNameList(String secName,int id) throws Exception{
	Session session=null;
	List<IncomeTaxSectionDTO> secList=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		secList=session.createCriteria(IncomeTaxSectionDTO.class).add(Expression.eq("secName", secName))
		.add(Expression.not(Expression.eq("id", id))).add(Expression.eq("status", 1)).list();
	}catch (Exception e) {
		throw e;
	}finally{
		session.flush(); //session.close();
	}
	return secList;
}
@SuppressWarnings({ "unchecked", "deprecation" })
public List<PayBillDTO> getProjectionPayBillList(String sfid,Date fromMonth,Date toMonth)throws Exception{
	Session session=null;
	List<PayBillDTO> payBillList=null;
	PayBillDTO lastMonthDTO=null;
	PayBillDTO remainingPayDTO=null;
	try{
		session=hibernateUtils.getSession();
		Calendar cal=Calendar.getInstance();
		cal.set(fromMonth.getYear()+1900, fromMonth.getMonth(), 01);
		String  runMonth=String.valueOf(session.createSQLQuery(" Select  to_char(Max(Run_month),'dd-Mon-yyyy') Runmonth From Pay_Txn_Details Where  (Run_Month Between "+
                                                          " ? And ?) and sfid=? and status in (0,60,51,52) order by Run_id desc").setDate(0, fromMonth).setDate(1, toMonth).setString(2, sfid).uniqueResult().toString());
		
		payBillList=session.createCriteria(PayBillDTO.class).add(Expression.or(Expression.between("runMonthDate",fromMonth,toMonth), Expression.in("runMonth", new String[]{CPSUtils.formatDate(fromMonth),CPSUtils.formatDate(toMonth)}))).add(Expression.eq("sfid", sfid)).add(Expression.not(Expression.eq("status", 50))).list();
		
		lastMonthDTO = (PayBillDTO)session.createCriteria(PayBillDTO.class).add(Expression.eq("sfid", sfid)).add(Expression.ne("status",50)).add(Expression.eq("runMonth", runMonth)).uniqueResult();
		int monthSize=payBillList.size();
		for(int p=monthSize;p<12;p++){
			remainingPayDTO=new PayBillDTO();
			BeanUtils.copyProperties(remainingPayDTO, lastMonthDTO);
			remainingPayDTO.setIncomeTax(0);
			remainingPayDTO.setCess(0);
			remainingPayDTO.setSecondaryCess(0);
			remainingPayDTO.setRunMonth(CPSUtils.formatDate1((cal.getTime())));
			payBillList.add(remainingPayDTO);
			cal.add(Calendar.MONTH, 1);
		}
		
	}catch (Exception e) {
		throw e;
	}
	return payBillList;
	
}
@SuppressWarnings("unchecked")
public List<PayBillDTO> getProjectionPayBillList1(String sfid,Date fromMonth,Date toMonth)throws Exception{
	Session session=null;
	List<PayBillDTO> payBillList=null;
	PayBillDTO firstMonthdto=null;
	PayBillDTO lastMonthdto=null;
	PayBillDTO paydto=null;
	PayBillDTO payBillDTO=null;
	
	try{
		session=hibernateUtils.getSession();
		Calendar cal =Calendar.getInstance();
		DateFormat df=new SimpleDateFormat("dd-MMM-yyyy");
		String qry=" Select min(Run_Month) From Pay_Txn_Details Where (Run_Month Between ? and ?) and status in (0,51,60,52) and sfid =? ";
		Date from=(Date)session.createSQLQuery(qry).setDate(0, fromMonth).setDate(1, toMonth).setString(2, sfid).uniqueResult();
		firstMonthdto=(PayBillDTO)session.createCriteria(PayBillDTO.class).add(Expression.eq("sfid", sfid)).add(Expression.in("status", new Integer[]{0,51,52,60})).add(Expression.eq("runMonth", df.format(from))).uniqueResult();
		
		qry=" Select max(Run_Month) From Pay_Txn_Details Where (Run_Month Between ? and ?) and status in (0,51,60,52) and sfid =? ";
		Date to=(Date)session.createSQLQuery(qry).setDate(0, fromMonth).setDate(1, toMonth).setString(2, sfid).uniqueResult();
		lastMonthdto=(PayBillDTO)session.createCriteria(PayBillDTO.class).add(Expression.eq("sfid", sfid)).add(Expression.in("status", new Integer[]{0,51,52,60})).add(Expression.eq("runMonth", df.format(to))).uniqueResult();
		
		payBillList=new ArrayList<PayBillDTO>();
		
		while(CPSUtils.compareTwoDatesUptoMonth(fromMonth, from)==-1){
			payBillDTO=new PayBillDTO();
			BeanUtils.copyProperties(payBillDTO,firstMonthdto);
			payBillDTO.setRunMonth(df.format(fromMonth));
			payBillList.add(payBillDTO);
			cal.setTime(fromMonth);
			cal.add(Calendar.MONTH, 1);
			fromMonth=cal.getTime();
		}
		int i=-1;
		while(CPSUtils.compareTwoDatesUptoMonth(from, to)==-1){
			paydto=(PayBillDTO)session.createCriteria(PayBillDTO.class).add(Expression.eq("sfid", sfid)).add(Expression.in("status", new Integer[]{0,51,52,60})).add(Expression.eq("runMonth", df.format(from))).uniqueResult();
			if(CPSUtils.isNullOrEmpty(paydto)){
			cal.setTime(from);
			cal.add(Calendar.MONTH, i);
			i-=1;
			paydto=(PayBillDTO)session.createCriteria(PayBillDTO.class).add(Expression.eq("sfid", sfid)).add(Expression.in("status", new Integer[]{0,51,52,60})).add(Expression.eq("runMonth", df.format(cal.getTime()))).uniqueResult();
			}else{
			i=-1;
			}
			paydto.setRunMonth(df.format(from));
			payBillList.add(paydto);
			session.evict(paydto);
			cal.setTime(from);
			cal.add(Calendar.MONTH, 1);
			from=cal.getTime();
		}
		int check=0;
		while(CPSUtils.compareTwoDatesUptoMonth(to, toMonth)==-1){
			payBillDTO=new PayBillDTO();
			BeanUtils.copyProperties(payBillDTO,lastMonthdto);
			payBillDTO.setRunMonth(df.format(to));
			if(check!=0){
			payBillDTO.setIncomeTax(0);
			payBillDTO.setCess(0);
			payBillDTO.setSecondaryCess(0);
			}
			payBillList.add(payBillDTO);
			cal.setTime(to);
			cal.add(Calendar.MONTH, 1);
			to=cal.getTime();
			check++;
		}
	}
	catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	return payBillList;
}
@SuppressWarnings("unchecked")
public List<PayBillDTO> getProjectionPayBillList2(String sfid,Date fromMonth,Date toMonth)throws Exception{
	Session session=null;
	List<PayBillDTO> payBillList=null;
	PayBillDTO firstMonthdto=null;
	PayBillDTO lastMonthdto=null;
	PayBillDTO paydto=null;
	PayBillDTO payBillDTO=null;
	try{
		session=hibernateUtils.getSession();
		Calendar cal =Calendar.getInstance();
		DateFormat df=new SimpleDateFormat("dd-MMM-yyyy");
		String qry=" Select min(Run_Month) From Pay_Txn_Details Where (Run_Month Between ? and ?) and status in (0,51,60,52) and sfid =? ";
		Date from=(Date)session.createSQLQuery(qry).setDate(0, fromMonth).setDate(1, toMonth).setString(2, sfid).uniqueResult();
		//firstMonthdto=(PayBillDTO)session.createCriteria(PayBillDTO.class).add(Expression.eq("sfid", sfid)).add(Expression.in("status", new Integer[]{0,51,52,60})).add(Expression.eq("runMonth", df.format(from))).uniqueResult();
		firstMonthdto=(PayBillDTO)session.createQuery("from PayBillDTO where sfid=? and status in (0,51,52,60) and runMonth=?").setString(0, sfid).setString(1, df.format(from)).uniqueResult();
		
		qry=" Select max(Run_Month) From Pay_Txn_Details Where (Run_Month Between ? and ?) and status in (0,51,60,52) and sfid =? ";
		Date to=(Date)session.createSQLQuery(qry).setDate(0, fromMonth).setDate(1, toMonth).setString(2, sfid).uniqueResult();
		//lastMonthdto=(PayBillDTO)session.createCriteria(PayBillDTO.class).add(Expression.eq("sfid", sfid)).add(Expression.in("status", new Integer[]{0,51,52,60})).add(Expression.eq("runMonth", df.format(to))).uniqueResult();
		lastMonthdto=(PayBillDTO)session.createQuery("from PayBillDTO where sfid=? and status in (0,51,52,60) and runMonth=?").setString(0, sfid).setString(1, df.format(to)).uniqueResult();
		
		payBillList=new ArrayList<PayBillDTO>();
		
		while(CPSUtils.compareTwoDatesUptoMonth(fromMonth, from)==-1){
			payBillDTO=new PayBillDTO();
			BeanUtils.copyProperties(payBillDTO,firstMonthdto);
			payBillDTO.setRunMonth(df.format(fromMonth));
			payBillList.add(payBillDTO);
			cal.setTime(fromMonth);
			cal.add(Calendar.MONTH, 1);
			fromMonth=cal.getTime();
		}
		int i=-1;
		/*while(CPSUtils.compareTwoDatesUptoMonth(from, to)==-1){
			//paydto=(PayBillDTO)session.createCriteria(PayBillDTO.class).add(Expression.eq("sfid", sfid)).add(Expression.in("status", new Integer[]{0,51,52,60})).add(Expression.eq("runMonth", df.format(from))).uniqueResult();
			Query query=session.createQuery("select id as id,sfid as sfid,basicPay as basicPay,gradePay as gradePay,specialPay as specialPay,da as da,hra as hra,tpt as tpt,twoAddlIncr as twoAddlIncr,variableIncr as variableIncr,pli as pli,totalCredits as totalCredits,gpf as gpf,cegis as cegis,cghs as cghs,profTax as profTax,hbaLoan as hbaLoan,lic as lic,eol as eol,runId as runId from PayBillDTO where sfid=? and status in (0,51,52,60) and runMonth=?").setString(0, sfid).setString(1, df.format(from)).setResultTransformer(Transformers.aliasToBean(PayBillDTO.class));
			query.setCacheable(true);
			paydto=(PayBillDTO)query.uniqueResult();
			if(CPSUtils.isNullOrEmpty(paydto)){
			cal.setTime(from);
			cal.add(Calendar.MONTH, i);
			i-=1;
			//paydto=(PayBillDTO)session.createCriteria(PayBillDTO.class).add(Expression.eq("sfid", sfid)).add(Expression.in("status", new Integer[]{0,51,52,60})).add(Expression.eq("runMonth", df.format(cal.getTime()))).uniqueResult();
			Query query2=session.createQuery("select id as id,sfid as sfid,basicPay as basicPay,gradePay as gradePay,specialPay as specialPay,da as da,hra as hra,tpt as tpt,twoAddlIncr as twoAddlIncr,variableIncr as variableIncr,pli as pli,totalCredits as totalCredits,gpf as gpf,cegis as cegis,cghs as cghs,profTax as profTax,hbaLoan as hbaLoan,lic as lic,eol as eol,runId as runId from PayBillDTO where sfid=? and status in (0,51,52,60) and runMonth=?").setString(0, sfid).setString(1, df.format(cal.getTime())).setResultTransformer(Transformers.aliasToBean(PayBillDTO.class));
			query2.setCacheable(true);
			paydto=(PayBillDTO)query2.uniqueResult();
			}else{
			i=-1;
			}
			paydto.setRunMonth(df.format(from));
			payBillList.add(paydto);
			session.evict(paydto);
			cal.setTime(from);
			cal.add(Calendar.MONTH, 1);
			from=cal.getTime();
		}*/
		int count=0;
		String str="";
		List<PayBillDTO> payList=null;
		String[] list=null;
		while(CPSUtils.compareTwoDatesUptoMonth(from, to)==-1){
			if(count==0){
				paydto=(PayBillDTO)session.createQuery("select id as id,sfid as sfid,basicPay as basicPay,gradePay as gradePay,specialPay as specialPay,da as da,hra as hra,tpt as tpt,twoAddlIncr as twoAddlIncr,variableIncr as variableIncr,pli as pli,totalCredits as totalCredits,gpf as gpf,cegis as cegis,cghs as cghs,profTax as profTax,hbaLoan as hbaLoan,lic as lic,eol as eol,runId as runId,to_char(runMonth) as runMonth,runMonthDate as runMonthDate from PayBillDTO where sfid=? and status in (0,51,52,60) and runMonth=?").setString(0, sfid).setString(1, df.format(from)).setResultTransformer(Transformers.aliasToBean(PayBillDTO.class)).uniqueResult();
				if(CPSUtils.isNullOrEmpty(paydto)){
					cal.setTime(from);
					cal.add(Calendar.MONTH, i);
					i-=1;
					paydto=(PayBillDTO)session.createQuery("select id as id,sfid as sfid,basicPay as basicPay,gradePay as gradePay,specialPay as specialPay,da as da,hra as hra,tpt as tpt,twoAddlIncr as twoAddlIncr,variableIncr as variableIncr,pli as pli,totalCredits as totalCredits,gpf as gpf,cegis as cegis,cghs as cghs,profTax as profTax,hbaLoan as hbaLoan,lic as lic,eol as eol,runId as runId,to_char(runMonth) as runMonth,runMonthDate as runMonthDate from PayBillDTO where sfid=? and status in (0,51,52,60) and runMonth=?").setString(0, sfid).setString(1, df.format(cal.getTime())).setResultTransformer(Transformers.aliasToBean(PayBillDTO.class)).uniqueResult();
				}else{
					i=-1;
					count++;
				}
				paydto.setRunMonth(df.format(from));
				payBillList.add(paydto);
				session.evict(paydto);
				cal.setTime(from);
				cal.add(Calendar.MONTH, 1);
				from=cal.getTime();
			}else{
				str+=df.format(from)+",";
				cal.setTime(from);
				cal.add(Calendar.MONTH, 1);
				from=cal.getTime();
			}
		}
		list=str.split(",");
//		str="";
//		for (int j = 0; j < list.length; j++) {
//			if(j==0 && j!=list.length-1){
//				str+=list[j]+"',";
//			}else if(j!=list.length-1){
//				str+="'"+list[j]+"',";
//			}else if(list.length-1!=0){
//				str+="'"+list[j];
//			}
//		}
		//payList=session.createQuery("select id as id,sfid as sfid,basicPay as basicPay,gradePay as gradePay,specialPay as specialPay,da as da,hra as hra,tpt as tpt,twoAddlIncr as twoAddlIncr,variableIncr as variableIncr,pli as pli,totalCredits as totalCredits,gpf as gpf,cegis as cegis,cghs as cghs,profTax as profTax,hbaLoan as hbaLoan,lic as lic,eol as eol,runId as runId,to_char(runMonth) as runMonth from PayBillDTO where sfid=? and status in (0,51,52,60) and runMonth in ('"+str+"')").setString(0, sfid).setResultTransformer(Transformers.aliasToBean(PayBillDTO.class)).list();
		payList=session.createCriteria(PayBillDTO.class).add(Expression.eq("sfid", sfid)).add(Expression.in("status", new Integer[]{0,51,52,60})).add(Expression.in("runMonth", list)).add(Restrictions.eq("sfid", sfid)).list();
		for (PayBillDTO payBillDTO2 : payList) {
			payBillList.add(payBillDTO2);
		}
		
		int check=0;
		while(CPSUtils.compareTwoDatesUptoMonth(to, toMonth)==-1){
			payBillDTO=new PayBillDTO();
			BeanUtils.copyProperties(payBillDTO,lastMonthdto);
			payBillDTO.setRunMonth(df.format(to));
			if(check!=0){
			payBillDTO.setIncomeTax(0);
			payBillDTO.setCess(0);
			payBillDTO.setSecondaryCess(0);
			}
			payBillList.add(payBillDTO);
			cal.setTime(to);
			cal.add(Calendar.MONTH, 1);
			to=cal.getTime();
			check++;
		}
	}
	catch (Exception e) {
		throw e;
	}
	return payBillList;
	
}
public List<PayBillDTO> getPlannedPayBillList(String sfid,int finYearId)throws Exception{
	Session session=null;
	List<PayBillDTO> payBillList=null;
	PayBillDTO lastMonthDTO=null;
	PayBillDTO mainDTO=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		payBillList=new ArrayList<PayBillDTO>();
		int fromYear=((PayFinYearDTO)session.get(PayFinYearDTO.class, finYearId)).getFyFrom();
		Calendar cal=Calendar.getInstance();
		cal.set(fromYear, 03, 01);
		int runId=Integer.parseInt(session.createSQLQuery("select run_id" +
				" from paybill_status_details where id=(Select Max(id) " +
				"From Paybill_status_details)").uniqueResult().toString());
		lastMonthDTO = (PayBillDTO)session.createCriteria(PayBillDTO.class).add(Expression.eq("sfid", sfid)).add(Expression.ne("status","50")).add(Expression.eq("runId", String.valueOf(runId))).uniqueResult();
		lastMonthDTO.setIncomeTax(0);
		lastMonthDTO.setCess(0);
		lastMonthDTO.setSecondaryCess(0);
		
		for(int p=0;p<12;p++){
			mainDTO=new PayBillDTO();
			BeanUtils.copyProperties(mainDTO, lastMonthDTO);
			mainDTO.setRunMonth(new SimpleDateFormat("dd-MMM-yyyy").format(cal.getTime()));
			payBillList.add(mainDTO);
			cal.add(Calendar.MONTH, 1);
			
		}
		
	}catch (Exception e) {
		throw e;
	}finally{
		session.flush(); //session.close();
	}
	return payBillList;
	
}
public List<IncomeTaxSectionDTO> getUniqueSectionList(List<IncomeTaxSectionDTO> sectionList)throws Exception{
	Session session=null;
	TreeSet<IncomeTaxSectionDTO> treeSet=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		treeSet=new TreeSet<IncomeTaxSectionDTO>(new Comparator<IncomeTaxSectionDTO>() {
			public int compare(IncomeTaxSectionDTO s1,IncomeTaxSectionDTO s2){
				if(s1.getSecName().equals(s2.getSecName())){
					return 0;
				}else{
					return 1;
				}
			}
		});
		for(IncomeTaxSectionDTO secDTO:sectionList){
			treeSet.add(secDTO);
		}
		sectionList.clear();
		for(IncomeTaxSectionDTO secDTO:treeSet){
			sectionList.add(secDTO);
		}
	}catch (Exception e) {
		throw e;
	}finally{
		session.flush(); //session.close();
	}
	return sectionList;
}
public String submitITStatusDetails(IncomeTaxMasterBean incomeTaxMasterBean)throws Exception{
	String message=null;
	IncomeTaxRunStatusDTO statusDTO=null;
	Session session=null;
	try{
		session=hibernateUtils.getSession();
		statusDTO=(IncomeTaxRunStatusDTO)session.createCriteria(IncomeTaxRunStatusDTO.class).add(Expression.eq("finYearId", Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()))).uniqueResult();
		if(CPSUtils.isNullOrEmpty(statusDTO)){
			statusDTO = new IncomeTaxRunStatusDTO();
			statusDTO.setFinYearId(Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()));
		}
		if(CPSUtils.compareStrings(CPSConstants.ITPLANNEDFLAG, incomeTaxMasterBean.getRunTypeFlag())){
			statusDTO.setPlannedStatus(1);
		}else if(CPSUtils.compareStrings(CPSConstants.ITPROJECTEDFLAG, incomeTaxMasterBean.getRunTypeFlag())){
			statusDTO.setProjectedStatus(1);
		}else{
			statusDTO.setActualStatus(1);
		}
		statusDTO.setRunId(incomeTaxMasterBean.getRunId());
		statusDTO.setStatus(1);
		statusDTO.setCreatedBy(incomeTaxMasterBean.getSfID());
		statusDTO.setLastModifiedBy(incomeTaxMasterBean.getSfID());
		statusDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
		statusDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
		session.saveOrUpdate(statusDTO);
		message = CPSConstants.SUCCESS;
	}catch (Exception e) {
		throw e;
	}
	return message;
}
@SuppressWarnings("unchecked")
public List<PayBillUserDTO> getEmployeeList(Date fromDate,Date toDate)throws Exception{
	List<PayBillUserDTO> empList=null;
	Session session=null;
	try{
		session=hibernateUtils.getSession();
		
		/*String qr = "select emp.sfid as sfID,Epd.Grade_Pay as gPay,Epd.Pay_Designation_Id as payDesignationId from emp_master emp,emp_pay_master epd "
			+ "where emp.status=1 and emp.employment_type_id not in (0,9) "
			+ "and epd.sfid = emp.sfid and epd.status=1 and epd.pay_stop='No' ";*/
		String qr=	" Select A.Sfid as sfID,A.Grade_Pay as gPay,A.Pay_Designation_Id as payDesignationId From Emp_Pay_Master A,Emp_Master B Where A.Sfid=B.Sfid And A.Sfid In "+
        " (Select Sfid From Pay_Txn_Details Where Run_Month Between ?  "+
        " and ? and sfid in(select F_CPERSNO from MIS_CREDITS) and status in (1,51,52,60)) order by a.sfid asc";
		
	    empList = session.createSQLQuery(qr).addScalar("sfID", Hibernate.STRING).addScalar("gPay", Hibernate.INTEGER).addScalar("payDesignationId", Hibernate.STRING).setDate(0, fromDate).setDate(1, toDate).setResultTransformer(
			Transformers.aliasToBean(PayBillUserDTO.class)).list();
	}catch (Exception e) {
		throw e;
	}
	return empList;
}
public IncomeTaxMasterBean submitITPayBillDetails(IncomeTaxMasterBean incomeTaxMasterBean)throws Exception{
	List<IncomeTaxPayBillDTO> payItPayBillList=null;
	IncomeTaxRunDTO runDTO=null;
	Session session=null;
	try{
		session=hibernateUtils.getSession();
		payItPayBillList=incomeTaxMasterBean.getPayItPayBillList();
		int transportAllowance=getTransportAllowance();
		runDTO=incomeTaxMasterBean.getIncomeTaxRunDTO();
        for(int i=0;i<payItPayBillList.size();i++){
        	//kumari test for add procedure it_insert_paybill_details();
		/*PayBillDTO payDTO = payBillList.get(i);
		IncomeTaxPayBillDTO incomeTaxPayBillDTO = new IncomeTaxPayBillDTO();
		BeanUtils.copyProperties(incomeTaxPayBillDTO, payDTO);
		incomeTaxPayBillDTO.setId(0);
		incomeTaxPayBillDTO.setRunId(incomeTaxMasterBean.getRunId());
		incomeTaxPayBillDTO.setETA(0);
		incomeTaxPayBillDTO.setIncomeTaxRec(payDTO.getIncomeTax()+payDTO.getSecondaryCess()+payDTO.getCess());
		incomeTaxPayBillDTO.setRunType(incomeTaxMasterBean.getRunTypeFlag());
		if(CPSUtils.compareStrings(incomeTaxMasterBean.getRunTypeFlag(), "actl")){
			incomeTaxPayBillDTO.setMonth(new SimpleDateFormat("dd-MMM-yyyy").parse(CPSUtils.formattedDate(payDTO.getRunMonth())));
		}else{
			incomeTaxPayBillDTO.setMonth(new SimpleDateFormat("dd-MMM-yyyy").parse(CPSUtils.formatDate(payDTO.getRunMonthDate())));
		}
		
		/*if(CPSUtils.compareStrings(incomeTaxMasterBean.getRunTypeFlag(), "actl")){
			incomeTaxPayBillDTO.setMonth(new SimpleDateFormat("dd-MMM-yyyy").parse(CPSUtils.formattedDate(payDTO.getRunMonth())));
		}else{
			incomeTaxPayBillDTO.setMonth(new SimpleDateFormat("dd-MMM-yyyy").parse(CPSUtils.formatDate(payDTO.getRunMonthDate())));
		}*/
		/*if(payDTO.getRunMonth().contains(" ")){
			incomeTaxPayBillDTO.setMonth(new SimpleDateFormat("dd-MMM-yyyy").parse(new SimpleDateFormat("dd-MMM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(payDTO.getRunMonth()))));	
		}else{
			incomeTaxPayBillDTO.setMonth(new SimpleDateFormat("dd-MMM-yyyy").parse(payDTO.getRunMonth()));
		}
		incomeTaxPayBillDTO.setCreationDate(new SimpleDateFormat("dd-MMM-yyyy").parse(new SimpleDateFormat("dd-MMM-yyyy").format(Calendar.getInstance().getTime())));
		incomeTaxPayBillDTO.setModifiedDate(new SimpleDateFormat("dd-MMM-yyyy").parse(new SimpleDateFormat("dd-MMM-yyyy").format(Calendar.getInstance().getTime())));
		incomeTaxPayBillDTO.setModifiedBy(incomeTaxMasterBean.getSfID());
		
		
		session.saveOrUpdate(incomeTaxPayBillDTO);
		
		
		session.saveOrUpdate(incomeTaxPayBillDTO);*/
        	IncomeTaxPayBillDTO incomeTaxPayBillDTO = new IncomeTaxPayBillDTO();
        	incomeTaxPayBillDTO=payItPayBillList.get(i);
		
		runDTO.setTaxRecovered(runDTO.getTaxRecovered()+incomeTaxPayBillDTO.getIncomeTaxRec());
		runDTO.setTotCghs(runDTO.getTotCghs()+incomeTaxPayBillDTO.getCghs());
		runDTO.setTotCegis(runDTO.getTotCegis()+incomeTaxPayBillDTO.getCegis());
		runDTO.setTotSal(runDTO.getTotSal()+incomeTaxPayBillDTO.getTotalCredits());
		runDTO.setTotHra(runDTO.getTotHra()+incomeTaxPayBillDTO.getHra());
		runDTO.setTotPTax(runDTO.getTotPTax()+incomeTaxPayBillDTO.getProfTax());
		runDTO.setGovtSubs(runDTO.getGovtSubs()+incomeTaxPayBillDTO.getGpf());
		
		/*Transport Allowance Calculation ::::: START  :::::*/
		
		if(incomeTaxPayBillDTO.getTpt()==0)
			runDTO.setTraAllw(runDTO.getTraAllw()+0);
		else
			runDTO.setTraAllw(runDTO.getTraAllw()+transportAllowance);
		
		/*Transport Allowance Calculation ::::: END  :::::*/
		
		runDTO.setLessHraEolHpl(runDTO.getLessHraEolHpl()+payItPayBillList.get(i).getHbaLoan()+payItPayBillList.get(i).getEol());
	 }  
       
        
	}catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	return incomeTaxMasterBean;
}
@SuppressWarnings("unchecked")
public int getRentPaid(String sfid,int finYearId)throws Exception{
	int rentPaid=0;
	Session session=null;
	List<IncomeTaxRentDTO> rentList=null;
	try{
	
		
		session=hibernateUtils.getSession();
		//kumari add function  calculate_rent_paid()
		String qry="select calculate_rent_paid(?,?) from dual";
		rentPaid=((BigDecimal)session.createSQLQuery(qry).setString(0, sfid).setInteger(1, finYearId).uniqueResult()).intValue();
		
		/*rentList=session.createCriteria(IncomeTaxRentDTO.class).add(Expression.eq("finYearId", finYearId)).add(Expression.eq("sfid", sfid)).add(Expression.eq("status", 1)).list();
		//rentList=session.createQuery("select rent as rent from IncomeTaxRentDTO where finYearId=? and sfid=? and status=1").setInteger(0, finYearId).setString(1, sfid).setResultTransformer(Transformers.aliasToBean(IncomeTaxRentDTO.class)).list();
	    for(int i=0;i<rentList.size();i++){
	    	rentPaid+=rentList.get(i).getRent();
	    }*/
	}catch (Exception e) {
		throw e;
	}
	return rentPaid;
}
@SuppressWarnings("unchecked")
public int submitPlannedExemptionsDetails(String sfid,int finYearId,String runTypeFlag,int runId,String loggedSfid,int totalCghs,int totalProfTax)throws Exception{
	int totalDeductions=0;
	Session session=null;
	List<IncomeTaxSavingsDTO> exemptionsMasterList=null;
	List<IncomeTaxSectionDTO> sectionList=null;
	IncomeTaxConfigDTO empExemptionDTO=null;
	try{
		session=hibernateUtils.getSession();
		/*String sql=" select sec From IncomeTaxSectionDTO sec Where sec.id In (select sav.sectionId from IncomeTaxSavingsDTO sav where sav.sType='Exemptions')";
		sectionList=session.createQuery(sql).list();
		totalDeductions+=(totalCghs+totalProfTax);
		for(int k=0;k<sectionList.size();k++){
			int sectionWiseTotal=0;
			int sectionWiseLimit=getLimit(sfid,sectionList.get(k).getId());
			if(CPSUtils.compareString(CPSConstants.CGHSRELATEDSECTIONID, ""+sectionList.get(k).getId())){
				sectionWiseTotal+=totalCghs;
			}
			exemptionsMasterList=session.createCriteria(IncomeTaxSavingsDTO.class).add(Expression.eq("status", 1)).add(Expression.eq("sType", "Exemptions")).add(Expression.eq("sectionId", sectionList.get(k).getId())).list();
			//exemptionsMasterList=session.createQuery("select id as id from IncomeTaxSavingsDTO where status=1 and sType=? and sectionId=?").setString(0, "Exemptions").setInteger(1, sectionList.get(k).getId()).setResultTransformer(Transformers.aliasToBean(IncomeTaxSavingsDTO.class)).list();
			for(int j=0;j<exemptionsMasterList.size();j++){
				if(!CPSUtils.compareString(CPSConstants.ITACTUALFLAG, runTypeFlag)){
					empExemptionDTO=(IncomeTaxConfigDTO)session.createCriteria(IncomeTaxConfigDTO.class).add(Expression.eq("selectedFYear", String.valueOf(finYearId))).add(Expression.eq("savingsTypeId", exemptionsMasterList.get(j).getId())).add(Expression.eq("sfID", sfid)).uniqueResult();
					//empExemptionDTO=(IncomeTaxConfigDTO)session.createQuery("select actual from IncomeTaxConfigDTO where selectedFYear=? and savingsTypeId=? and sfID=?").setString(0, String.valueOf(finYearId)).setInteger(1, exemptionsMasterList.get(j).getId()).setString(2, sfid).uniqueResult();
				}else{
					empExemptionDTO=(IncomeTaxConfigDTO)session.createCriteria(IncomeTaxConfigDTO.class).add(Expression.eq("selectedFYear", String.valueOf(finYearId))).add(Expression.eq("submissionFlag", "true")).add(Expression.eq("savingsTypeId", exemptionsMasterList.get(j).getId())).add(Expression.eq("sfID", sfid)).uniqueResult();
					//empExemptionDTO=(IncomeTaxConfigDTO)session.createQuery("select actual from IncomeTaxConfigDTO where selectedFYear=? and submissionFlag=? and savingsTypeId=? and sfID=?").setString(0, String.valueOf(finYearId)).setString(1, "true").setInteger(2, exemptionsMasterList.get(j).getId()).setString(3, sfid).uniqueResult();
				}
				IncomeTaxSavingsRunDTO incomeTaxSavingsRunDTO =new IncomeTaxSavingsRunDTO();
				incomeTaxSavingsRunDTO.setFinYearId(finYearId);
				incomeTaxSavingsRunDTO.setRunType(runTypeFlag);
				incomeTaxSavingsRunDTO.setConfTypeId(exemptionsMasterList.get(j).getId());
				incomeTaxSavingsRunDTO.setSfid(sfid);
				incomeTaxSavingsRunDTO.setStatus(1);
				incomeTaxSavingsRunDTO.setRunId(runId);
				incomeTaxSavingsRunDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
				incomeTaxSavingsRunDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				incomeTaxSavingsRunDTO.setCreatedBy(loggedSfid);
				incomeTaxSavingsRunDTO.setLastModifiedBy(loggedSfid);
				if(!CPSUtils.isNullOrEmpty(empExemptionDTO)){
					sectionWiseTotal+=empExemptionDTO.getActual();
					incomeTaxSavingsRunDTO.setAmount(empExemptionDTO.getActual());
				}else{
					incomeTaxSavingsRunDTO.setAmount(0);
				}
				session.saveOrUpdate(incomeTaxSavingsRunDTO);
			}
			if(sectionWiseTotal>sectionWiseLimit){
				totalDeductions+=sectionWiseLimit;
			}else{
				totalDeductions+=sectionWiseTotal;
			}
		}*/
		//kumari test call procedure Insert_It_Pl_Exemptions() and call function calculate_it_deductions()
		totalDeductions=((BigDecimal)session.createSQLQuery("select calculate_it_deductions(?,?,?,?,?,?,?) from dual").setString(0, sfid).setInteger(1, finYearId).setString(2, runTypeFlag).setString(3, loggedSfid).setInteger(4, totalCghs).setInteger(5, totalProfTax).setString(6, "Exemptions").uniqueResult()).intValue();
		
		//session.createSQLQuery("call Insert_It_Pl_Exemptions(?,?,?,?,?,?)").setString(0, sfid).setInteger(1, finYearId).setString(2, runTypeFlag).setInteger(3, runId).setString(4, loggedSfid).setString(5, "Exemptions").uniqueResult();
		session.createSQLQuery("{ call Insert_It_Pl_Exemptions(?,?,?,?,?,?) }").setString(0, sfid).setInteger(1, finYearId).setString(2, runTypeFlag).setInteger(3, runId).setString(4, loggedSfid).setString(5, "Exemptions").executeUpdate();
	}catch (Exception e) {
		throw e;
	}
	return totalDeductions;
}
@SuppressWarnings("unchecked")
public int submitPlannedExemptionsDetails1(String sfid,int finYearId,String runTypeFlag,int runId,String loggedSfid,int totalCghs,int totalProfTax)throws Exception{
	int totalDeductions=0;
	Session session=null;
	List<IncomeTaxSavingsDTO> exemptionsMasterList=null;
	List<IncomeTaxSectionDTO> sectionList=null;
	IncomeTaxConfigDTO empExemptionDTO=null;
	String sectionWiseTot=null;
	String sql1=null;
	try{
		session=hibernateUtils.getSession();
		String sql=" select sec From IncomeTaxSectionDTO sec Where sec.id In (select sav.sectionId from IncomeTaxSavingsDTO sav where sav.sType='Exemptions')";
		sectionList=session.createQuery(sql).list();
		totalDeductions+=(totalCghs+totalProfTax);
		for(int k=0;k<sectionList.size();k++){
			int sectionWiseTotal=0;
			int sectionWiseLimit=getLimit(sfid,sectionList.get(k).getId());
			if(CPSUtils.compareString(CPSConstants.CGHSRELATEDSECTIONID, ""+sectionList.get(k).getId())){
				sectionWiseTotal+=totalCghs;
			}
			exemptionsMasterList=session.createCriteria(IncomeTaxSavingsDTO.class).add(Expression.eq("status", 1)).add(Expression.eq("sType", "Exemptions")).add(Expression.eq("sectionId", sectionList.get(k).getId())).list();
			//exemptionsMasterList=session.createQuery("select id as id from IncomeTaxSavingsDTO where status=1 and sType=? and sectionId=?").setString(0, "Exemptions").setInteger(1, sectionList.get(k).getId()).setResultTransformer(Transformers.aliasToBean(IncomeTaxSavingsDTO.class)).list();
			for(int j=0;j<exemptionsMasterList.size();j++){
				if(!CPSUtils.compareString(CPSConstants.ITACTUALFLAG, runTypeFlag)){
					sql1="insert into INCOME_TAX_CONF_RUN_DETAILS (id,FIN_YEAR_ID,RUN_TYPE,SFID,status,RUN_ID,CREATION_DATE,LAST_MODIFIED_DATE,CREATED_BY,LAST_MODIFIED_BY,CONF_TYPE_ID,AMOUNT) values " +
					"((select case when ((select max(id) from INCOME_TAX_CONF_RUN_DETAILS) is null) then 1 else (select max(id)+1 from INCOME_TAX_CONF_RUN_DETAILS) end as idVal from dual),?,?,?,?,?,?,?,?,?,?,(select case when (select Projection from PAY_ITAX_CONFIG_DETAILS where SELECTED_FYEAR=? and SAVINGS_TYPE_ID=? and SFID=?) is null then 0 else (select Projection from PAY_ITAX_CONFIG_DETAILS where SELECTED_FYEAR=? and SAVINGS_TYPE_ID=? and SFID=?) end as Projection from dual))";
					session.createSQLQuery(sql1).setInteger(0, finYearId).setString(1, runTypeFlag).setString(2, sfid).setInteger(3, 1).setInteger(4, runId).setDate(5, CPSUtils.getCurrentDateWithTime())
					.setDate(6, CPSUtils.getCurrentDateWithTime()).setString(7, loggedSfid).setString(8, loggedSfid).setInteger(9, exemptionsMasterList.get(j).getId()).setString(10, String.valueOf(finYearId)).setInteger(11, exemptionsMasterList.get(j).getId()).setString(12, sfid).setString(13, String.valueOf(finYearId)).setInteger(14, exemptionsMasterList.get(j).getId()).setString(15, sfid).executeUpdate();
					//empExemptionDTO=(IncomeTaxConfigDTO)session.createCriteria(IncomeTaxConfigDTO.class).add(Expression.eq("selectedFYear", String.valueOf(finYearId))).add(Expression.eq("savingsTypeId", exemptionsMasterList.get(j).getId())).add(Expression.eq("sfID", sfid)).uniqueResult();
					//empExemptionDTO=(IncomeTaxConfigDTO)session.createQuery("select actual from IncomeTaxConfigDTO where selectedFYear=? and savingsTypeId=? and sfID=?").setString(0, String.valueOf(finYearId)).setInteger(1, exemptionsMasterList.get(j).getId()).setString(2, sfid).uniqueResult();
				}else{
					empExemptionDTO=(IncomeTaxConfigDTO)session.createCriteria(IncomeTaxConfigDTO.class).add(Expression.eq("selectedFYear", String.valueOf(finYearId))).add(Expression.eq("submissionFlag", "true")).add(Expression.eq("savingsTypeId", exemptionsMasterList.get(j).getId())).add(Expression.eq("sfID", sfid)).uniqueResult();
					//empExemptionDTO=(IncomeTaxConfigDTO)session.createQuery("select actual from IncomeTaxConfigDTO where selectedFYear=? and submissionFlag=? and savingsTypeId=? and sfID=?").setString(0, String.valueOf(finYearId)).setString(1, "true").setInteger(2, exemptionsMasterList.get(j).getId()).setString(3, sfid).uniqueResult();
				}
			}
			if(!CPSUtils.compareString(CPSConstants.ITACTUALFLAG, runTypeFlag)){
				if(exemptionsMasterList.size()>0)
					sectionWiseTot=(String)session.createSQLQuery("select to_char(sum(projection)) as sumVal from PAY_ITAX_CONFIG_DETAILS where SELECTED_FYEAR=? and SFID=? and SAVINGS_TYPE_ID in (:sTypeId)").setString(0, String.valueOf(finYearId)).setString(1, sfid).setParameterList("sTypeId", exemptionsMasterList).uniqueResult();
				if(!CPSUtils.isNullOrEmpty(sectionWiseTot))
					sectionWiseTotal+=Integer.valueOf(sectionWiseTot);
			}else{
				sectionWiseTotal+=(Integer)session.createQuery("select sum(actual) from IncomeTaxConfigDTO where selectedFYear=? and sfID=? and submissionFlag=? and savingsTypeId in (:sTypeId)").setString(0, String.valueOf(finYearId)).setString(1, sfid).setString(2, "true").setParameterList("sTypeId", exemptionsMasterList).uniqueResult();
			}
			if(sectionWiseTotal>sectionWiseLimit){
				totalDeductions+=sectionWiseLimit;
			}else{
				totalDeductions+=sectionWiseTotal;
			}
		}
		
	}catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	return totalDeductions;
}
@SuppressWarnings("unchecked")
public int submitPlannedTotalIncomeFromOtherSources(String sfid,int finYearId,String runTypeFlag,int runId,String loggedSfid)throws Exception{
	int totalIncomeFromOtherSources=0;
	Session session=null;
	List<IncomeTaxSavingsDTO> otherIncomeMasterList=null;
	List<IncomeTaxSectionDTO> sectionList=null;
	IncomeTaxConfigDTO empExemptionDTO=null;
	try{
		session=hibernateUtils.getSession();
		/*String sql=" select sec From IncomeTaxSectionDTO sec Where sec.id In (select sav.sectionId from IncomeTaxSavingsDTO sav where sav.sType='Exemptions')";
		sectionList=session.createQuery(sql).list();
		for(int k=0;k<sectionList.size();k++){
			int sectionWiseTotal=0;
			int sectionWiseLimit=0;
			if(sectionList.get(k).getId()!=Integer.valueOf(CPSConstants.NOSECTIONID)){
				sectionWiseLimit=getLimit(sfid,sectionList.get(k).getId());
			}
			otherIncomeMasterList=session.createCriteria(IncomeTaxSavingsDTO.class).add(Expression.eq("status", 1)).add(Expression.eq("sType", "Other Income Sources")).add(Expression.eq("sectionId", sectionList.get(k).getId())).list();
			//otherIncomeMasterList=session.createQuery("select id as id from IncomeTaxSavingsDTO where status=1 and sType=? and sectionId=?").setString(0, "Other Income Sources").setInteger(1, sectionList.get(k).getId()).setResultTransformer(Transformers.aliasToBean(IncomeTaxSavingsDTO.class)).list();
			for(int j=0;j<otherIncomeMasterList.size();j++){
				if(!CPSUtils.compareString(CPSConstants.ITACTUALFLAG, runTypeFlag)){
				empExemptionDTO=(IncomeTaxConfigDTO)session.createCriteria(IncomeTaxConfigDTO.class).add(Expression.eq("selectedFYear", String.valueOf(finYearId))).add(Expression.eq("savingsTypeId", otherIncomeMasterList.get(j).getId())).add(Expression.eq("sfID", sfid)).uniqueResult();
				//empExemptionDTO=(IncomeTaxConfigDTO)session.createQuery("select actual from IncomeTaxConfigDTO where selectedFYear=? and savingsTypeId=? and sfID=?").setString(0, String.valueOf(finYearId)).setInteger(1, otherIncomeMasterList.get(j).getId()).setString(2, sfid).uniqueResult();
				}else{
					empExemptionDTO=(IncomeTaxConfigDTO)session.createCriteria(IncomeTaxConfigDTO.class).add(Expression.eq("selectedFYear", String.valueOf(finYearId))).add(Expression.eq("savingsTypeId", otherIncomeMasterList.get(j).getId())).add(Expression.eq("sfID", sfid)).add(Expression.eq("submissionFlag", "true")).uniqueResult();
					//empExemptionDTO=(IncomeTaxConfigDTO)session.createQuery("select actual from IncomeTaxConfigDTO where selectedFYear=? and submissionFlag=? and savingsTypeId=? and sfID=?").setString(0, String.valueOf(finYearId)).setString(1, "true").setInteger(2, otherIncomeMasterList.get(j).getId()).setString(3, sfid).uniqueResult();
				}
				IncomeTaxSavingsRunDTO incomeTaxSavingsRunDTO =new IncomeTaxSavingsRunDTO();
				incomeTaxSavingsRunDTO.setFinYearId(finYearId);
				incomeTaxSavingsRunDTO.setRunType(runTypeFlag);
				incomeTaxSavingsRunDTO.setConfTypeId(otherIncomeMasterList.get(j).getId());
				incomeTaxSavingsRunDTO.setSfid(sfid);
				incomeTaxSavingsRunDTO.setStatus(1);
				incomeTaxSavingsRunDTO.setRunId(runId);
				incomeTaxSavingsRunDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
				incomeTaxSavingsRunDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				incomeTaxSavingsRunDTO.setCreatedBy(loggedSfid);
				incomeTaxSavingsRunDTO.setLastModifiedBy(loggedSfid);
				if(!CPSUtils.isNullOrEmpty(empExemptionDTO)){
					sectionWiseTotal+=empExemptionDTO.getActual();
					incomeTaxSavingsRunDTO.setAmount(empExemptionDTO.getActual());
				}else{
					incomeTaxSavingsRunDTO.setAmount(0);
				}
				session.saveOrUpdate(incomeTaxSavingsRunDTO);
				session.flush();
			}
			if(sectionWiseLimit==0){
				totalIncomeFromOtherSources+=sectionWiseTotal;
			}else if(sectionWiseTotal>sectionWiseLimit){
				totalIncomeFromOtherSources+=sectionWiseLimit;
			}else{
				totalIncomeFromOtherSources+=sectionWiseTotal;
			}
		}*/
		totalIncomeFromOtherSources=((BigDecimal)session.createSQLQuery("select calculate_it_income_from_other(?,?,?,?,?) from dual").setString(0, sfid).setInteger(1, finYearId).setString(2, runTypeFlag).setString(3, loggedSfid).setString(4, "Other Income Sources").uniqueResult()).intValue();
		session.createSQLQuery("{ call Insert_It_Pl_Exemptions(?,?,?,?,?,?) }").setString(0, sfid).setInteger(1, finYearId).setString(2, runTypeFlag).setInteger(3, runId).setString(4, loggedSfid).setString(5, "Other Income Sources").executeUpdate();
	}catch (Exception e) {
		throw e;
	}
	return totalIncomeFromOtherSources;
}
@SuppressWarnings("unchecked")
public int submitPlannedTotalIncomeFromOtherSources1(String sfid,int finYearId,String runTypeFlag,int runId,String loggedSfid)throws Exception{
	int totalIncomeFromOtherSources=0;
	Session session=null;
	List<IncomeTaxSavingsDTO> otherIncomeMasterList=null;
	List<IncomeTaxSectionDTO> sectionList=null;
	IncomeTaxConfigDTO empExemptionDTO=null;
	String sql1=null;
	String sectionWiseTot=null;
	try{
		session=hibernateUtils.getSession();
		String sql=" select sec From IncomeTaxSectionDTO sec Where sec.id In (select sav.sectionId from IncomeTaxSavingsDTO sav where sav.sType='Exemptions')";
		sectionList=session.createQuery(sql).list();
		for(int k=0;k<sectionList.size();k++){
			int sectionWiseTotal=0;
			int sectionWiseLimit=0;
			if(sectionList.get(k).getId()!=Integer.valueOf(CPSConstants.NOSECTIONID)){
				sectionWiseLimit=getLimit(sfid,sectionList.get(k).getId());
			}
			otherIncomeMasterList=session.createCriteria(IncomeTaxSavingsDTO.class).add(Expression.eq("status", 1)).add(Expression.eq("sType", "Other Income Sources")).add(Expression.eq("sectionId", sectionList.get(k).getId())).list();
			//otherIncomeMasterList=session.createQuery("select id as id from IncomeTaxSavingsDTO where status=1 and sType=? and sectionId=?").setString(0, "Other Income Sources").setInteger(1, sectionList.get(k).getId()).setResultTransformer(Transformers.aliasToBean(IncomeTaxSavingsDTO.class)).list();
			for(int j=0;j<otherIncomeMasterList.size();j++){
				if(!CPSUtils.compareString(CPSConstants.ITACTUALFLAG, runTypeFlag)){
					sql1="insert into INCOME_TAX_CONF_RUN_DETAILS (id,FIN_YEAR_ID,RUN_TYPE,SFID,status,RUN_ID,CREATION_DATE,LAST_MODIFIED_DATE,CREATED_BY,LAST_MODIFIED_BY,CONF_TYPE_ID,AMOUNT) values " +
					"((select case when ((select max(id) from INCOME_TAX_CONF_RUN_DETAILS) is null) then 1 else (select max(id)+1 from INCOME_TAX_CONF_RUN_DETAILS) end as idVal from dual),?,?,?,?,?,?,?,?,?,?,(select case when (select Projection from PAY_ITAX_CONFIG_DETAILS where SELECTED_FYEAR=? and SAVINGS_TYPE_ID=? and SFID=?) is null then 0 else (select Projection from PAY_ITAX_CONFIG_DETAILS where SELECTED_FYEAR=? and SAVINGS_TYPE_ID=? and SFID=?) end as Projection from dual))";
					session.createSQLQuery(sql1).setInteger(0, finYearId).setString(1, runTypeFlag).setString(2, sfid).setInteger(3, 1).setInteger(4, runId).setDate(5, CPSUtils.getCurrentDateWithTime())
					.setDate(6, CPSUtils.getCurrentDateWithTime()).setString(7, loggedSfid).setString(8, loggedSfid).setInteger(9, otherIncomeMasterList.get(j).getId()).setString(10, String.valueOf(finYearId)).setInteger(11, otherIncomeMasterList.get(j).getId()).setString(12, sfid).setString(13, String.valueOf(finYearId)).setInteger(14, otherIncomeMasterList.get(j).getId()).setString(15, sfid).executeUpdate();
				//empExemptionDTO=(IncomeTaxConfigDTO)session.createCriteria(IncomeTaxConfigDTO.class).add(Expression.eq("selectedFYear", String.valueOf(finYearId))).add(Expression.eq("savingsTypeId", otherIncomeMasterList.get(j).getId())).add(Expression.eq("sfID", sfid)).uniqueResult();
				//empExemptionDTO=(IncomeTaxConfigDTO)session.createQuery("select actual from IncomeTaxConfigDTO where selectedFYear=? and savingsTypeId=? and sfID=?").setString(0, String.valueOf(finYearId)).setInteger(1, otherIncomeMasterList.get(j).getId()).setString(2, sfid).uniqueResult();
				}else{
					empExemptionDTO=(IncomeTaxConfigDTO)session.createCriteria(IncomeTaxConfigDTO.class).add(Expression.eq("selectedFYear", String.valueOf(finYearId))).add(Expression.eq("savingsTypeId", otherIncomeMasterList.get(j).getId())).add(Expression.eq("sfID", sfid)).add(Expression.eq("submissionFlag", "true")).uniqueResult();
					//empExemptionDTO=(IncomeTaxConfigDTO)session.createQuery("select actual from IncomeTaxConfigDTO where selectedFYear=? and submissionFlag=? and savingsTypeId=? and sfID=?").setString(0, String.valueOf(finYearId)).setString(1, "true").setInteger(2, otherIncomeMasterList.get(j).getId()).setString(3, sfid).uniqueResult();
				}
			}
			if(!CPSUtils.compareString(CPSConstants.ITACTUALFLAG, runTypeFlag)){
				if(otherIncomeMasterList.size()>0)
					sectionWiseTot=(String)session.createSQLQuery("select to_char(sum(projection)) as sumVal from PAY_ITAX_CONFIG_DETAILS where SELECTED_FYEAR=? and SFID=? and SAVINGS_TYPE_ID in (:sTypeId)").setString(0, String.valueOf(finYearId)).setString(1, sfid).setParameterList("sTypeId", otherIncomeMasterList).uniqueResult();
				if(!CPSUtils.isNullOrEmpty(sectionWiseTot))
					sectionWiseTotal+=Integer.valueOf(sectionWiseTot);
			}else{
				sectionWiseTotal+=(Integer)session.createQuery("select sum(actual) from IncomeTaxConfigDTO where selectedFYear=? and sfID=? and submissionFlag=? and savingsTypeId in (:sTypeId)").setString(0, String.valueOf(finYearId)).setString(1, sfid).setString(2, "true").setParameterList("sTypeId", otherIncomeMasterList).uniqueResult();
			}
			if(sectionWiseLimit==0){
				totalIncomeFromOtherSources+=sectionWiseTotal;
			}else if(sectionWiseTotal>sectionWiseLimit){
				totalIncomeFromOtherSources+=sectionWiseLimit;
			}else{
				totalIncomeFromOtherSources+=sectionWiseTotal;
			}
		}
	}catch (Exception e) {
		throw e;
	}
	return totalIncomeFromOtherSources;
}
@SuppressWarnings("unchecked")
public int submitPlannedTotalSavings(String sfid,int finYearId,int cgeis,int pli,int tuitionFee,int gpfSubscription,String runTypeFlag,String loggedSfid,int runId)throws Exception{
	int totalSavings=0;
	Session session=null;
	List<IncomeTaxSavingsDTO> savingsMasterList=null;
	List<IncomeTaxSectionDTO> sectionList=null;
	IncomeTaxConfigDTO empExemptionDTO=null;
	try{
		session=hibernateUtils.getSession();
		/*String sql=" select sec From IncomeTaxSectionDTO sec Where sec.id In (select sav.sectionId from IncomeTaxSavingsDTO sav where sav.sType='Savings')";
		sectionList=session.createQuery(sql).list();
		for(int k=0;k<sectionList.size();k++){
			int sectionWiseTotal=0;
			int sectionWiseLimit=0;
			if(sectionList.get(k).getId()!=Integer.valueOf(CPSConstants.NOSECTIONID)){
				sectionWiseLimit=getLimit(sfid,sectionList.get(k).getId());
			}
			if(sectionList.get(k).getId()==Integer.parseInt(CPSConstants.PAYRELATEDSECTIONID)){
				sectionWiseTotal+=(cgeis+pli+tuitionFee+gpfSubscription);
			}
				savingsMasterList=session.createCriteria(IncomeTaxSavingsDTO.class).add(Expression.eq("status", 1)).add(Expression.eq("sType", "Savings")).add(Expression.eq("sectionId", sectionList.get(k).getId())).list();
				for(int j=0;j<savingsMasterList.size();j++){
					if(!CPSUtils.compareString(CPSConstants.ITACTUALFLAG, runTypeFlag)){
						empExemptionDTO=(IncomeTaxConfigDTO)session.createCriteria(IncomeTaxConfigDTO.class).add(Expression.eq("selectedFYear", String.valueOf(finYearId))).add(Expression.eq("savingsTypeId", savingsMasterList.get(j).getId())).add(Expression.eq("sfID", sfid)).uniqueResult();
					}else{
						empExemptionDTO=(IncomeTaxConfigDTO)session.createCriteria(IncomeTaxConfigDTO.class).add(Expression.eq("selectedFYear", String.valueOf(finYearId))).add(Expression.eq("savingsTypeId", savingsMasterList.get(j).getId())).add(Expression.eq("sfID", sfid)).add(Expression.eq("submissionFlag", "true")).uniqueResult();
					}
					IncomeTaxSavingsRunDTO incomeTaxSavingsRunDTO =new IncomeTaxSavingsRunDTO();
					incomeTaxSavingsRunDTO.setFinYearId(finYearId);
					incomeTaxSavingsRunDTO.setRunType(runTypeFlag);
					incomeTaxSavingsRunDTO.setConfTypeId(savingsMasterList.get(j).getId());
					incomeTaxSavingsRunDTO.setSfid(sfid);
					incomeTaxSavingsRunDTO.setStatus(1);
					incomeTaxSavingsRunDTO.setRunId(runId);
					incomeTaxSavingsRunDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
					incomeTaxSavingsRunDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
					incomeTaxSavingsRunDTO.setCreatedBy(loggedSfid);
					incomeTaxSavingsRunDTO.setLastModifiedBy(loggedSfid);
					if(!CPSUtils.isNullOrEmpty(empExemptionDTO)){
						if(!CPSUtils.compareString(CPSConstants.ITACTUALFLAG, runTypeFlag)){
						sectionWiseTotal+=empExemptionDTO.getProjection();
						incomeTaxSavingsRunDTO.setAmount(empExemptionDTO.getProjection());
						}
						else{
						sectionWiseTotal+=empExemptionDTO.getActual();	
						incomeTaxSavingsRunDTO.setAmount(empExemptionDTO.getActual());
						}
					}else{
						incomeTaxSavingsRunDTO.setAmount(0);
					}
					session.saveOrUpdate(incomeTaxSavingsRunDTO);
				}
				if(sectionWiseLimit==0){
					totalSavings+=sectionWiseTotal;
				}else if(sectionWiseTotal>sectionWiseLimit){
					totalSavings+=sectionWiseLimit;
				}else{
					totalSavings+=sectionWiseTotal;
				}
			}*/
		totalSavings=((BigDecimal)session.createSQLQuery("select Calculate_It_Tot_Savings(?,?,?,?,?,?,?,?,?) from dual").setString(0, sfid).setInteger(1, finYearId).setInteger(2, cgeis).setInteger(3, pli).setInteger(4, tuitionFee).setInteger(5, gpfSubscription).setString(6, runTypeFlag).setString(7, loggedSfid).setString(8, "Savings").uniqueResult()).intValue();
		session.createSQLQuery("{ call Insert_It_Pl_Exemptions(?,?,?,?,?,?) }").setString(0, sfid).setInteger(1, finYearId).setString(2, runTypeFlag).setInteger(3, runId).setString(4, loggedSfid).setString(5, "Savings").executeUpdate();
	}catch (Exception e) {
		throw e;
	}
	return totalSavings;
}
@SuppressWarnings("unchecked")
public int submitPlannedTotalSavings1(String sfid,int finYearId,int cgeis,int pli,int tuitionFee,int gpfSubscription,String runTypeFlag,String loggedSfid,int runId)throws Exception{
	int totalSavings=0;
	Session session=null;
	List<IncomeTaxSavingsDTO> savingsMasterList=null;
	List<IncomeTaxSectionDTO> sectionList=null;
	String sql1=null;
	String sectionWiseTot=null;
	try{
		System.out.println("---------------------------------submitPlannedTotalSavings1-----------------------------------------");
		session=hibernateUtils.getSession();
		String sql=" select sec From IncomeTaxSectionDTO sec Where sec.id In (select sav.sectionId from IncomeTaxSavingsDTO sav where sav.sType='Savings')";
		sectionList=session.createQuery(sql).list();
		for(int k=0;k<sectionList.size();k++){
			int sectionWiseTotal=0;
			int sectionWiseLimit=0;
			if(sectionList.get(k).getId()!=Integer.valueOf(CPSConstants.NOSECTIONID)){
				sectionWiseLimit=getLimit(sfid,sectionList.get(k).getId());
			}
			if(sectionList.get(k).getId()==Integer.parseInt(CPSConstants.PAYRELATEDSECTIONID)){
				sectionWiseTotal+=(cgeis+pli+tuitionFee+gpfSubscription);
			}
				savingsMasterList=session.createCriteria(IncomeTaxSavingsDTO.class).add(Expression.eq("status", 1)).add(Expression.eq("sType", "Savings")).add(Expression.eq("sectionId", sectionList.get(k).getId())).list();
				for(int j=0;j<savingsMasterList.size();j++){
					if(!CPSUtils.compareString(CPSConstants.ITACTUALFLAG, runTypeFlag)){
						sql1="insert into INCOME_TAX_CONF_RUN_DETAILS (id,FIN_YEAR_ID,RUN_TYPE,SFID,status,RUN_ID,CREATION_DATE,LAST_MODIFIED_DATE,CREATED_BY,LAST_MODIFIED_BY,CONF_TYPE_ID,AMOUNT) values " +
						"((select case when ((select max(id) from INCOME_TAX_CONF_RUN_DETAILS) is null) then 1 else (select max(id)+1 from INCOME_TAX_CONF_RUN_DETAILS) end as idVal from dual),?,?,?,?,?,?,?,?,?,?,(select case when (select Projection from PAY_ITAX_CONFIG_DETAILS where SELECTED_FYEAR=? and SAVINGS_TYPE_ID=? and SFID=?) is null then 0 else (select Projection from PAY_ITAX_CONFIG_DETAILS where SELECTED_FYEAR=? and SAVINGS_TYPE_ID=? and SFID=?) end as Projection from dual))";
						session.createSQLQuery(sql1).setInteger(0, finYearId).setString(1, runTypeFlag).setString(2, sfid).setInteger(3, 1).setInteger(4, runId).setDate(5, CPSUtils.getCurrentDateWithTime())
						.setDate(6, CPSUtils.getCurrentDateWithTime()).setString(7, loggedSfid).setString(8, loggedSfid).setInteger(9, savingsMasterList.get(j).getId()).setString(10, String.valueOf(finYearId)).setInteger(11, savingsMasterList.get(j).getId()).setString(12, sfid).setString(13, String.valueOf(finYearId)).setInteger(14, savingsMasterList.get(j).getId()).setString(15, sfid).executeUpdate();
					}else{
						sql1="insert into INCOME_TAX_CONF_RUN_DETAILS (id,FIN_YEAR_ID,RUN_TYPE,SFID,status,RUN_ID,CREATION_DATE,LAST_MODIFIED_DATE,CREATED_BY,LAST_MODIFIED_BY,CONF_TYPE_ID,AMOUNT) values " +
						"((select case when ((select max(id) from INCOME_TAX_CONF_RUN_DETAILS) is null) then 1 else (select max(id)+1 from INCOME_TAX_CONF_RUN_DETAILS) end as idVal from dual),?,?,?,?,?,?,?,?,?,?,(select case when (select Projection from PAY_ITAX_CONFIG_DETAILS where SELECTED_FYEAR=? and SAVINGS_TYPE_ID=? and SFID=? and SUBMISSION_FLAG='true') is null then 0 else (select Projection from PAY_ITAX_CONFIG_DETAILS where SELECTED_FYEAR=? and SAVINGS_TYPE_ID=? and SFID=? and SUBMISSION_FLAG='true') end as Projection from dual))";
						session.createSQLQuery(sql1).setInteger(0, finYearId).setString(1, runTypeFlag).setString(2, sfid).setInteger(3, 1).setInteger(4, runId).setDate(5, CPSUtils.getCurrentDateWithTime())
						.setDate(6, CPSUtils.getCurrentDateWithTime()).setString(7, loggedSfid).setString(8, loggedSfid).setInteger(9, savingsMasterList.get(j).getId()).setString(10, String.valueOf(finYearId)).setInteger(11, savingsMasterList.get(j).getId()).setString(12, sfid).setString(13, String.valueOf(finYearId)).setInteger(14, savingsMasterList.get(j).getId()).setString(15, sfid).executeUpdate();
					}
				}
				if(!CPSUtils.compareString(CPSConstants.ITACTUALFLAG, runTypeFlag)){
					if(savingsMasterList.size()>0)
						sectionWiseTot=(String)session.createSQLQuery("select to_char(sum(projection)) as sumVal from PAY_ITAX_CONFIG_DETAILS where SELECTED_FYEAR=? and SFID=? and SAVINGS_TYPE_ID in (:sTypeId)").setString(0, String.valueOf(finYearId)).setString(1, sfid).setParameterList("sTypeId", savingsMasterList).uniqueResult();
					if(!CPSUtils.isNullOrEmpty(sectionWiseTot))
						sectionWiseTotal+=Integer.valueOf(sectionWiseTot);
				}else{
					if(savingsMasterList.size()>0)
						sectionWiseTot=(String)session.createSQLQuery("select to_char(sum(actual)) as sumVal from PAY_ITAX_CONFIG_DETAILS where SELECTED_FYEAR=? and SFID=? and SUBMISSION_FLAG='true' and SAVINGS_TYPE_ID in (:sTypeId)").setString(0, String.valueOf(finYearId)).setString(1, sfid).setParameterList("sTypeId", savingsMasterList).uniqueResult();
					if(!CPSUtils.isNullOrEmpty(sectionWiseTot))
						sectionWiseTotal+=Integer.valueOf(sectionWiseTot);
				}
				if(sectionWiseLimit==0){
					totalSavings+=sectionWiseTotal;
				}else if(sectionWiseTotal>sectionWiseLimit){
					totalSavings+=sectionWiseLimit;
				}else{
					totalSavings+=sectionWiseTotal;
				}
			}
	}catch (Exception e) {
		throw e;
	}
	return totalSavings;
}
public int getLimit(String sfid,int sectionId)throws Exception{
	int limit=0;
	Session session=null;
	try{
		session=hibernateUtils.getSession();
		String  sLimit=(String)session.createSQLQuery("select Get_It_Limit_For_Sections(?,?) from dual").setString(0, sfid).setInteger(1, sectionId).uniqueResult();
		if(!CPSUtils.isNullOrEmpty(sLimit)){
			limit=Integer.parseInt(sLimit);
		}
	}catch (Exception e) {
		throw e;
	}
	return limit;
}
public int getArrearsDetails(String arrearsType,int daTypeFlag,String sfid,int finYearId)throws Exception{
	//public String getArrearsDetails(String sfid,int finYearId)throws Exception{
		int arrears=0;
		//String allArrears="";
		Session session=null;
		try{
			session=hibernateUtils.getSession();
			//kumari add function  calculate_arrears()
			String qry="select calculate_arrears(?,?,?,?) from dual";
			arrears=((BigDecimal)session.createSQLQuery(qry).setString(0, arrearsType).setInteger(1, daTypeFlag).setString(2, sfid).setInteger(3, finYearId).uniqueResult()).intValue();
			
			/*List<ArrearsTxnDetailsDTO> daArrearsList=session.createCriteria(ArrearsTxnDetailsDTO.class).add(Expression.eq("sfid", sfid)).add(Expression.eq("arrearsType", arrearsType)).add(Expression.eq("daTypeFlag", daTypeFlag)).add(Expression.eq("finYearId", finYearId)).list();
		    for(ArrearsTxnDetailsDTO arrearsTxnDetailsDTO:daArrearsList){
		    	arrears+=arrearsTxnDetailsDTO.getTotalAmount();
		    }*/
		}catch (Exception e) {
			throw e;
		}
		//return allArrears;
		return arrears;
	  }

public int calculateHRAExempted(int hra,int totRentPaid,int basicPay,int da)throws Exception{
	int hraExempted=0;
	try{
		//Formula to be applied
		if(hra<totRentPaid){
			hraExempted=hra;
		}else{
			hraExempted=totRentPaid;
		}
		
	}catch (Exception e) {
		throw e;
	}
	return hraExempted;
}
public int getProfessionalUpdateAllowance(int gradePay,int designationId)throws Exception{
	int prUpdateAllowance=0;
	Session session=null;
	try{
		session=hibernateUtils.getSession();
		PrUpdateAllwDTO prUpdateAllwDTO=(PrUpdateAllwDTO)session.createCriteria(PrUpdateAllwDTO.class).add(Expression.eq("gradePay", gradePay)).add(Expression.eq("designationId", designationId)).add(Expression.eq("status", 1)).uniqueResult();
		//PrUpdateAllwDTO prUpdateAllwDTO=(PrUpdateAllwDTO)session.createQuery("select amount from PrUpdateAllwDTO where gradePay=? and designationId=? and status=1").setInteger(0, gradePay).setInteger(1, designationId).uniqueResult();
	    if(!CPSUtils.isNullOrEmpty(prUpdateAllwDTO)){
	    	prUpdateAllowance=prUpdateAllwDTO.getAmount();
	    }
	}catch (Exception e) {
		throw e;
	}
	return prUpdateAllowance;
}
//---------------------------My Code is started------------------------------------//
@SuppressWarnings("unchecked")
public List<PayBillDTO> getProjectionPayBillListed(List<PayBillUserDTO> empList,Date fromMonth,Date toMonth)throws Exception{
	Session session=null;
	List<PayBillDTO> payBillList=null;
	PayBillDTO firstMonthdto=null;
	PayBillDTO lastMonthdto=null;
	List<PayBillDTO> firstMonthdtoList=null;
	List<PayBillDTO> lastMonthdtoList=null;
	List<PayBillDTO> paydtoList=null;
	List<PayBillDTO> paydtoList1=new ArrayList<PayBillDTO>();
	PayBillDTO paydto=null;
	PayBillDTO payBillDTO=null;
	try{
		session=hibernateUtils.getSession();
		Calendar cal =Calendar.getInstance();
		DateFormat df=new SimpleDateFormat("dd-MMM-yyyy");
		String qry=" Select min(Run_Month) From Pay_Txn_Details Where (Run_Month Between ? and ?) and status in (0,51,60,52)";
		Date from=(Date)session.createSQLQuery(qry).setDate(0, fromMonth).setDate(1, toMonth).uniqueResult();
		firstMonthdtoList=session.createCriteria(PayBillDTO.class).add(Expression.in("status", new Integer[]{0,51,52,60})).add(Expression.eq("runMonth", df.format(from))).list();
		//firstMonthdto=(PayBillDTO)session.createQuery("from PayBillDTO where sfid=? and status in (0,51,52,60) and runMonth=?").setString(0, sfid).setString(1, df.format(from)).uniqueResult();
		
		qry=" Select max(Run_Month) From Pay_Txn_Details Where (Run_Month Between ? and ?) and status in (0,51,60,52)";
		Date to=(Date)session.createSQLQuery(qry).setDate(0, fromMonth).setDate(1, toMonth).uniqueResult();
		lastMonthdtoList=session.createCriteria(PayBillDTO.class).add(Expression.in("status", new Integer[]{0,51,52,60})).add(Expression.eq("runMonth", df.format(to))).list();
		//lastMonthdto=(PayBillDTO)session.createQuery("from PayBillDTO where sfid=? and status in (0,51,52,60) and runMonth=?").setString(0, sfid).setString(1, df.format(to)).uniqueResult();
		
		payBillList=new ArrayList<PayBillDTO>();
		for(int t=0;t<firstMonthdtoList.size();t++){
			for (int i = 0; i < empList.size(); i++) {
				if(CPSUtils.compareStrings(firstMonthdtoList.get(t).getSfid(), empList.get(i).getSfID())){
					while(CPSUtils.compareTwoDatesUptoMonth(fromMonth, from)==-1){
						payBillDTO=new PayBillDTO();
						BeanUtils.copyProperties(payBillDTO,firstMonthdto);
						payBillDTO.setRunMonth(df.format(fromMonth));
						payBillList.add(payBillDTO);
						cal.setTime(fromMonth);
						cal.add(Calendar.MONTH, 1);
						fromMonth=cal.getTime();
					}
				}
			}
		}
		int i=-1;
		while(CPSUtils.compareTwoDatesUptoMonth(from, to)==-1){
			paydtoList=session.createCriteria(PayBillDTO.class).add(Expression.in("status", new Integer[]{0,51,52,60})).add(Expression.eq("runMonth", df.format(from))).addOrder(Order.asc("sfid")).list();
			//paydto=(PayBillDTO)session.createQuery("from PayBillDTO where sfid=? and status in (0,51,52,60) and runMonth=?").setString(0, sfid).setString(1, df.format(from)).uniqueResult();
			if(CPSUtils.isNullOrEmpty(paydtoList) && paydtoList.size()!=empList.size()){
			cal.setTime(from);
			cal.add(Calendar.MONTH, i);
			i-=1;
			for (PayBillDTO payBillDTO2 : paydtoList) {
				paydtoList1.add(payBillDTO2);
			}
			for(int j=0;j<empList.size();j++){
				String sfid=empList.get(i).getSfID();
				boolean flag=true;
				for (PayBillDTO payBillDTO2 : paydtoList1) {
					if(CPSUtils.compareStrings(sfid.toUpperCase(), payBillDTO2.getSfid().toUpperCase())){
						flag=false;
						break;
					}
				}
				if(flag){
					paydto=(PayBillDTO)session.createCriteria(PayBillDTO.class).add(Expression.eq("sfid", sfid)).add(Expression.in("status", new Integer[]{0,51,52,60})).add(Expression.eq("runMonth", df.format(cal.getTime()))).uniqueResult();
				}
				paydtoList.add(paydto);
			}
			
			//paydto=(PayBillDTO)session.createQuery("from PayBillDTO where sfid=? and status in (0,51,52,60) and runMonth=?").setString(0, sfid).setString(1, df.format(cal.getTime())).uniqueResult();
			}else{
			i=-1;
			}
			for (PayBillDTO payBillDTO2 : paydtoList) {
				payBillDTO2.setRunMonth(df.format(from));
				payBillList.add(payBillDTO2);
			}
			//session.evict(paydto);
			cal.setTime(from);
			cal.add(Calendar.MONTH, 1);
			from=cal.getTime();
		}
		int check=0;
		while(CPSUtils.compareTwoDatesUptoMonth(to, toMonth)==-1){
			for (int m = 0; m < empList.size(); m++) {
				for (PayBillDTO payBillDTO2 : paydtoList) {
					if(CPSUtils.compareStrings(empList.get(m).getSfID(), payBillDTO2.getSfid())){
						for (int j = 0; j < lastMonthdtoList.size(); j++) {
							if(CPSUtils.compareStrings(payBillDTO2.getSfid(), lastMonthdtoList.get(j).getSfid())){
								BeanUtils.copyProperties(payBillDTO2,lastMonthdtoList.get(j));
								payBillDTO2.setRunMonth(df.format(to));
								if(check!=0){
									payBillDTO2.setIncomeTax(0);
									payBillDTO2.setCess(0);
									payBillDTO2.setSecondaryCess(0);
								}
								payBillList.add(payBillDTO2);
							}
						}
					}
				}
				cal.setTime(to);
				cal.add(Calendar.MONTH, 1);
				to=cal.getTime();
				check++;
			}
		}
	}
	catch (Exception e) {
		throw e;
	}
	return payBillList;
	
}
@SuppressWarnings("unchecked")
public List<IncomeTaxSavingsRunDTO> submitPlannedExemptionsDetails2(String sfid,int finYearId,String runTypeFlag,int runId,String loggedSfid,int totalCghs,int totalProfTax)throws Exception{
	int totalDeductions=0;
	Session session=null;
	List<IncomeTaxSavingsDTO> exemptionsMasterList=null;
	List<IncomeTaxSectionDTO> sectionList=null;
	IncomeTaxConfigDTO empExemptionDTO=null;
	List<IncomeTaxConfigDTO> empExemptionList=null;
	List<IncomeTaxSavingsRunDTO> savingsList=new ArrayList<IncomeTaxSavingsRunDTO>();
	try{
		session=hibernateUtils.getSession();
		String sql=" select sec From IncomeTaxSectionDTO sec Where sec.id In (select sav.sectionId from IncomeTaxSavingsDTO sav where sav.sType='Exemptions')";
		sectionList=session.createQuery(sql).list();
		totalDeductions+=(totalCghs+totalProfTax);
		for(int k=0;k<sectionList.size();k++){
			int sectionWiseTotal=0;
			int sectionWiseLimit=getLimit(sfid,sectionList.get(k).getId());
			if(CPSUtils.compareString(CPSConstants.CGHSRELATEDSECTIONID, ""+sectionList.get(k).getId())){
				sectionWiseTotal+=totalCghs;
			}
			exemptionsMasterList=session.createCriteria(IncomeTaxSavingsDTO.class).add(Expression.eq("status", 1)).add(Expression.eq("sType", "Exemptions")).add(Expression.eq("sectionId", sectionList.get(k).getId())).list();
			for(int j=0;j<exemptionsMasterList.size();j++){
				if(!CPSUtils.compareString(CPSConstants.ITACTUALFLAG, runTypeFlag)){
					empExemptionList=session.createCriteria(IncomeTaxConfigDTO.class).add(Expression.eq("selectedFYear", String.valueOf(finYearId))).add(Expression.eq("savingsTypeId", exemptionsMasterList.get(j).getId())).list();
				}else{
					empExemptionList=session.createCriteria(IncomeTaxConfigDTO.class).add(Expression.eq("selectedFYear", String.valueOf(finYearId))).add(Expression.eq("submissionFlag", "true")).add(Expression.eq("savingsTypeId", exemptionsMasterList.get(j).getId())).list();
				}
			}
			
			
			
			
			for(int j=0;j<exemptionsMasterList.size();j++){
				if(!CPSUtils.compareString(CPSConstants.ITACTUALFLAG, runTypeFlag)){
					empExemptionList=session.createCriteria(IncomeTaxConfigDTO.class).add(Expression.eq("selectedFYear", String.valueOf(finYearId))).add(Expression.eq("savingsTypeId", exemptionsMasterList.get(j).getId())).list();
				}else{
					empExemptionList=session.createCriteria(IncomeTaxConfigDTO.class).add(Expression.eq("selectedFYear", String.valueOf(finYearId))).add(Expression.eq("submissionFlag", "true")).add(Expression.eq("savingsTypeId", exemptionsMasterList.get(j).getId())).list();
				}
				for(int i=0;i<empExemptionList.size();i++){
					
				}
				
				IncomeTaxSavingsRunDTO incomeTaxSavingsRunDTO =new IncomeTaxSavingsRunDTO();
				incomeTaxSavingsRunDTO.setFinYearId(finYearId);
				incomeTaxSavingsRunDTO.setRunType(runTypeFlag);
				incomeTaxSavingsRunDTO.setConfTypeId(exemptionsMasterList.get(j).getId());
				incomeTaxSavingsRunDTO.setSfid(sfid);
				incomeTaxSavingsRunDTO.setStatus(1);
				incomeTaxSavingsRunDTO.setRunId(runId);
				incomeTaxSavingsRunDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
				incomeTaxSavingsRunDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				incomeTaxSavingsRunDTO.setCreatedBy(loggedSfid);
				incomeTaxSavingsRunDTO.setLastModifiedBy(loggedSfid);
				if(!CPSUtils.isNullOrEmpty(empExemptionDTO)){
					sectionWiseTotal+=empExemptionDTO.getActual();
					incomeTaxSavingsRunDTO.setAmount(empExemptionDTO.getActual());
				}else{
					incomeTaxSavingsRunDTO.setAmount(0);
				}
				
			}
			if(sectionWiseTotal>sectionWiseLimit){
				totalDeductions+=sectionWiseLimit;
			}else{
				totalDeductions+=sectionWiseTotal;
			}
		}
		
	}catch (Exception e) {
		throw e;
	}
	return savingsList;
}

//kumari test for add procedure
public List<IncomeTaxPayBillDTO> getPayItPayBillList(String sfid,Date fromMonth,Date toMonth)throws Exception{
	Session session=null;
	List<IncomeTaxPayBillDTO> payItPayBillList=null;
	
	try{
		session=hibernateUtils.getSession();
		payItPayBillList=session.createCriteria(IncomeTaxPayBillDTO.class).add(Expression.eq("sfid", sfid)).add(Expression.between("month", fromMonth, toMonth)).list();
		}
	
	catch (Exception e) {
		throw e;
	}
	return payItPayBillList;
}

}
