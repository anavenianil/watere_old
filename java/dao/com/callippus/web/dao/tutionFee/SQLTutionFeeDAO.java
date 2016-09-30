package com.callippus.web.dao.tutionFee;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.configuration.SigningAuthorityDTO;
import com.callippus.web.beans.dto.CDADetailsDTO;
import com.callippus.web.beans.dto.FinanceDetailsDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.YearTypeDTO;
import com.callippus.web.beans.family.FamilyBean;
import com.callippus.web.beans.tutionFee.ClaimTypeMasterDTO;
import com.callippus.web.beans.tutionFee.TuitionFeeAcedemicYearDTO;
import com.callippus.web.beans.tutionFee.TuitionFeeBillAndSanctionDTO;
import com.callippus.web.beans.tutionFee.TutionFeeAcademicYearMasterDTO;
import com.callippus.web.beans.tutionFee.TutionFeeBean;
import com.callippus.web.beans.tutionFee.TutionFeeClaimDetailsDTO;
import com.callippus.web.beans.tutionFee.TutionFeeClaimMasterDTO;
import com.callippus.web.beans.tutionFee.TutionFeeLimitMasterDTO;
import com.callippus.web.beans.tutionFee.TutionFeeRequestDetailsDTO;
import com.callippus.web.beans.workflowmapping.WorkFlowMappingBean;
import com.callippus.web.cghs.beans.dto.SingingAuthorityDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.incometax.dto.PayFinYearDTO;

@Service
public class SQLTutionFeeDAO implements ITutionFeeDAO{

@Autowired
com.callippus.web.controller.common.HibernateUtils hibernateUtils;

@SuppressWarnings("unchecked")
public TutionFeeBean getFamilyDetails(TutionFeeBean tutionFeeBean) throws Exception{
	List<FamilyBean> familyList=null;
	List<TutionFeeClaimMasterDTO> claimList= null;
	Session session=null;
	int maxChild=0;
	int maxAgeLimit=0;
	List<FamilyBean> childFamilyList=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		String query="select to_number(value) from Configuration_Details where name='TUITION_FEE_MAX_CHILD_TO_BE_CLAIMED'";  
		maxChild=((BigDecimal)session.createSQLQuery(query).uniqueResult()).intValue();
		String query1="select  to_number(value)  from Configuration_Details where name='CGHS_MAJOR_AGE_LIMIT'";
		maxAgeLimit=((BigDecimal)session.createSQLQuery(query1).uniqueResult()).intValue();
		//code to remove the children whose age>18
		String query3=" SELECT TAB.AGE as age,(CASE WHEN TAB.DOB IS NULL THEN TAB.AGE ELSE" +
					  " TAB.DOB end) AS DOB,TAB.NAME as name ,TAB.RELATION as relation,TAB.ID as id FROM (SELECT (CASE WHEN FD.DOB IS NULL THEN FD.AGE ELSE (SELECT ROUND(MONTHS_BETWEEN(SYSDATE,FD.DOB)/12) FROM DUAL)||'' end ) as age," +
				      " fd.sex gender,to_char(fd.dob,'DD-MON-YYYY') dob,fd.id,fd.name" +
				      " as name,frm.name as relation FROM FAMILY_DETAILS FD,FAMILY_RELATION_MASTER FRM WHERE FD.RELATION_ID=FRM.ID AND FD.STATUS=1 and fd.sfid='"+tutionFeeBean.getSfid()+"' and fd.ltc_facility='Y' and relation_id in(5,6) order by fd.dob)TAB WHERE TAB.AGE<="+maxAgeLimit+"";
		childFamilyList=session.createSQLQuery(query3).addScalar("age",Hibernate.STRING).addScalar("dob", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("relation", Hibernate.STRING).addScalar("id", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(FamilyBean.class)).list();
		//familyList=session.createCriteria(FamilyBean.class).add(Expression.eq("status", 1)).add(Expression.or(Expression.eq("relationId", "5"), Expression.eq("relationId", "6"))).add(Expression.eq("sfid", tutionFeeBean.getSfid())).addOrder(Order.asc("dob")).list();
		if(maxChild==0){//if configuration is 0 then removing all 
			childFamilyList.removeAll(childFamilyList);	
		}
		if(childFamilyList.size()>maxChild){
		for(int i=0;i<childFamilyList.size();i++){
			if(i>maxChild-1){
				childFamilyList.remove(i);
			}
		}
		}
		claimList = session.createCriteria(TutionFeeClaimMasterDTO.class).add(Expression.eq("status", 1)).add(Expression.eq("claimTypeId", 1)).addOrder(Order.asc("orderNo")).list();
		tutionFeeBean.setFamilyList(childFamilyList);
		tutionFeeBean.setClaimList(claimList);
	}catch (Exception e) {
		throw e;
	}
	finally{
		//session.close();
	}
	return tutionFeeBean;
 }

@SuppressWarnings("unchecked")
public List<TutionFeeBean> getTfClaimRequestDetails(int requestId,WorkFlowMappingBean workFlowMappingBean) throws Exception{
	Session session=null;
	List<TutionFeeBean> claimList= null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		
		String query=" Select Tflm.Academic_Type as academicType,Tfcd.Limit_Id as limitId,Tflm.Type as type,Tfcd.Sfid As sfid,Sum(Tfcd.Amount) As grandtotal," +
					 " (case when fd.dob is null then fd.age else To_Char(Fd.Dob,'dd-MON-yyyy') end)as dateOfBirth,Fd.Name as childName,sum(tfcd.sanctioned_amount) as sanctionedAmount,tab.fromDate as fromDate1,tab.toDate as toDate1,tab.className as className,"+  
			         " Fd.Id As childRelationId,Tfrd.Ip_Address as ipAddress  From " +
			         "(select distinct to_char(tfcd.from_date) as fromDate ,to_char(tfcd.To_Date) as toDate,tfam.class_name as className,tfcd.child_relation_id from Tution_Fee_Claim_Details tfcd ,Tution_Fee_Academy_Year_Master tfam where Request_Id="+requestId+" and tfam.id=tfcd.class_id) tab,"+
			         " Tution_Fee_Claim_Details Tfcd,Family_Details Fd,Tution_Fee_Request_Details Tfrd,tution_fee_limit_master tflm  "+
			         " Where Tfcd.Child_Relation_Id=Fd.Id And Tfrd.Request_Id=Tfcd.Request_Id And Tfcd.Request_Id="+requestId+" and tfcd.limit_id=tflm.id and tab.child_relation_id =tfcd.child_relation_id and tflm.status=1"+
			         " GROUP BY Tfcd.Limit_Id,Tflm.Type,Tfcd.Child_relation_id,Tfcd.Sfid, Fd.Dob,Fd.Name,tfrd.grand_total,fd.id, "+
			         " Tfcd.Limit_Id,tab.fromDate,tab.toDate,Tfrd.Ip_Address,Tflm.Academic_Type,tab.className,fd.age order by childRelationId";
		
		claimList=session.createSQLQuery(query).addScalar("academicType",Hibernate.STRING).addScalar("type",Hibernate.STRING).addScalar("limitId",Hibernate.STRING).addScalar("sfid",Hibernate.STRING).addScalar("grandTotal",Hibernate.STRING).
		addScalar("dateOfBirth",Hibernate.STRING).addScalar("sanctionedAmount",Hibernate.STRING).addScalar("fromDate1",Hibernate.STRING).addScalar("toDate1",Hibernate.STRING).addScalar("className", Hibernate.STRING).
		addScalar("childName",Hibernate.STRING).addScalar("childRelationId",Hibernate.INTEGER).addScalar("ipAddress",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TutionFeeBean.class)).list();
	    
		int sumSanctionedAmount=((BigDecimal)session.createSQLQuery("select sanctioned_amount from tution_fee_request_details where request_id='"+requestId+"'").uniqueResult()).intValue();
		workFlowMappingBean.setTeleSanctionedAmount(String.valueOf(sumSanctionedAmount));
	    
	}catch (Exception e) {
		throw e;
	}
	finally{
		//session.close();
	}
	return claimList;
}
public String tutionFeeOrgRoleId(String sfid)throws Exception{
	Session session = null;
	String message =null;
	int orgRoleId = 0;
	int empRoleId = 0;
	try{
		session = hibernateUtils.getSession();//session = sessionFactory.openSession();
		String query1 ="Select Org_role_id From Org_role_instance Where Org_role_name ='TA DA /Medical Section Head'";
		orgRoleId = ((BigDecimal)session.createSQLQuery(query1).uniqueResult()).intValue();
		
		String query2 ="select org_role_id from emp_role_mapping where status=1 and sfid='"+sfid+"' order by nvl(org_role_id,-1)";
		List empRolesList=session.createSQLQuery(query2).list();
		if(empRolesList.size()==1 && empRolesList.get(0)==null || empRolesList.size()==2 && empRolesList.get(0)==null){
			message="Yes";
		}else{
			for(int i=0;i<empRolesList.size();i++){
				empRoleId=Integer.parseInt(empRolesList.get(i).toString());
				if(orgRoleId==empRoleId){
					message="No";
					break;
				}else{
					message="Yes";
				}
			}
		}
	}catch (Exception e) {
		throw e;
	}finally{
		//session.close();
	}
	return message;
}
@SuppressWarnings("unchecked")
public List<List<TutionFeeClaimDetailsDTO>> getClaimListDetais(int requestId)throws Exception{
	Session session=null;
	List<List<TutionFeeClaimDetailsDTO>> mainList=null;
  try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		mainList=new ArrayList<List<TutionFeeClaimDetailsDTO>>();
		List<BigDecimal> appliedChildList=session.createSQLQuery("select distinct child_relation_id "+
				"from tution_fee_claim_details where request_id=? order by Child_Relation_Id").setInteger(0, requestId).list();
		for(int i=0;i<appliedChildList.size();i++){
			List<TutionFeeClaimDetailsDTO> subList=session.createCriteria(TutionFeeClaimDetailsDTO.class).add(Expression.eq("requestId", requestId)).
		    add(Expression.eq("childRelationId", appliedChildList.get(i).intValue())).addOrder(Order.asc("childRelationId")).list();
			//code for displaying the totalsanctionedamount in the user claimform
			if(subList.size()>0){
				int total =0;
				int totalSanctioned=0;
				for(int j=0;j<subList.size();j++){
					total = total+subList.get(j).getAmount();
					totalSanctioned = totalSanctioned+subList.get(j).getSanctionedAmount();
				}
				subList.get(subList.size()-1).setTotalClaimed(total);
				subList.get(subList.size()-1).setTotalSanctionedAmt(totalSanctioned);
			}
			
			mainList.add(subList);
		}
		
	}catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	finally{
		//session.close();
	}
	return mainList;
}
@SuppressWarnings("unchecked")
public List<TuitionFeeAcedemicYearDTO> getTutionFeeAcademicYearList(TutionFeeBean tutionFeeBean) throws Exception{
	Session session=null;
	List<TuitionFeeAcedemicYearDTO> mainList=null;
	List<TuitionFeeAcedemicYearDTO> classIdList1=null;
	Map<Integer, List<TuitionFeeAcedemicYearDTO>> tutionFeeChildList =new TreeMap<Integer, List<TuitionFeeAcedemicYearDTO>>();
	try{
		session=hibernateUtils.getSession();
		mainList=session.createCriteria(TuitionFeeAcedemicYearDTO.class).add(Expression.eq("status", 1)).addOrder(Order.asc("id")).list();
		/*int firstChildId=0;
		int secondChildId=0;
	    for(int i=0;i<tutionFeeBean.getFamilyList().size();i++){
			if(i==0){
				if(!CPSUtils.isNullOrEmpty(tutionFeeBean.getFamilyList().get(i))){
					 firstChildId=Integer.parseInt(tutionFeeBean.getFamilyList().get(i).getId());
				}
				mainList=getAcademicList(firstChildId);
				tutionFeeBean.setFirstChildList(mainList);
				tutionFeeBean.setFirstChildId(firstChildId);
			}else if(i==1){
				if(!CPSUtils.isNullOrEmpty(tutionFeeBean.getFamilyList().get(i))){
					secondChildId=Integer.parseInt(tutionFeeBean.getFamilyList().get(i).getId());
				} 
				mainList=getAcademicList(secondChildId);
				tutionFeeBean.setSecondChildList(mainList);
				tutionFeeBean.setSecondChildId(secondChildId);
			}
		}*/
		int childId=0;
		for(int i=0;i<tutionFeeBean.getFamilyList().size();i++){
			if(!CPSUtils.isNullOrEmpty(tutionFeeBean.getFamilyList().get(i))){
				childId=Integer.parseInt(tutionFeeBean.getFamilyList().get(i).getId());
			}
			mainList=getAcademicList(childId);
			tutionFeeChildList.put(childId, mainList);
		}
		tutionFeeBean.setMapChildList(tutionFeeChildList);
		}catch (Exception e) {
		throw e;
	}finally{
		hibernateUtils.closeSession();
	}
	return mainList;
}
@SuppressWarnings("unchecked")
public List<TuitionFeeAcedemicYearDTO> getAcademicList(int childId) throws Exception{
	Session session=null;
	List<TuitionFeeAcedemicYearDTO> tempMainList = null;
	//List<KeyValueDTO> tempMainList = null;
	//List academicYearList=null;
	Integer [] classIdList= new Integer[50];
	try{
		session = hibernateUtils.getSession();
String query =" Select tfam.Id as id,tfam.Class_Name From Tution_Fee_Academy_Year_Master tfam left outer join(select x.Class_Id cid from (Select Distinct Class_Id,limit_id From Tution_Fee_Claim_Details Where Child_Relation_Id =?  And Status In(2,8))X Where X.Limit_Id < 7 "+
			  " Union "+
			  " select y.Class_Id cid from (Select Distinct Class_Id,limit_id From Tution_Fee_Claim_Details Where Child_Relation_Id =?  And Status In(2,8))y where y.limit_id < 14 and y.limit_id >7)tab on (tab.cid = tfam.Id)" +
			  " minus " +
			  " Select tfam.Id ,tfam.Class_Name From Tution_Fee_Academy_Year_Master Tfam Where  Tfam.Id In (select x.Class_Id cid from (Select Distinct Class_Id,limit_id From Tution_Fee_Claim_Details Where Child_Relation_Id =? And Status In(2,8))x where x.limit_id in (4,6,7)"+
			  " Union  "+
			  " select y.Class_Id cid from(Select Distinct Class_Id,limit_id From Tution_Fee_Claim_Details Where Child_Relation_Id =? And Status In(2,8))y where y.limit_id in(11,13,14) and y.limit_id > 7) "+
			  " Minus "+
			  " Select tfam.Id ,tfam.Class_Name From Tution_Fee_Academy_Year_Master tfam where tfam.Id < (select max (cid) from ((select x.Class_Id cid from (Select Distinct Class_Id,limit_id From Tution_Fee_Claim_Details Where Child_Relation_Id =?  And Status In(2,8))x where x.limit_id in (4,6,7)" +
			  " union " +
			  " select Nvl(Max(Y.Class_Id),0) cid from (Select Distinct Class_Id,limit_id From Tution_Fee_Claim_Details Where Child_Relation_Id =?  And Status In(2,8))y where y.limit_id in(11,13,14) and y.limit_id > 7 )))"+ 
			  " Minus "+
			  " Select Tfam.Id ,Tfam.Class_Name From Tution_Fee_Academy_Year_Master Tfam Where Tfam.Id < (select max (cid) from ((Select X.Class_Id Cid From (Select Distinct Class_Id,Limit_Id From Tution_Fee_Claim_Details Where Child_Relation_Id =?  And Status In(2,8))X Where X.Limit_Id < 7 "+
			  " Union " +
			  " Select Y.Class_Id Cid From (Select Distinct Class_Id,limit_id From Tution_Fee_Claim_Details Where Child_Relation_Id =? And Status In(2,8))y where y.limit_id < 14 and Y.Limit_Id >7)))";
	    	
	List<TuitionFeeAcedemicYearDTO> academicYearList=session.createSQLQuery(query).addScalar("id", Hibernate.INTEGER).setInteger(0, childId).setInteger(1, childId).setInteger(2, childId).setInteger(3, childId).setInteger(4, childId).setInteger(5, childId).setInteger(6, childId).setInteger(7, childId).setResultTransformer(Transformers.aliasToBean(TuitionFeeAcedemicYearDTO.class)).list();
	for(int i=0;i<academicYearList.size();i++){
	    classIdList[i]=(academicYearList.get(i).getId());
	    }
	    tempMainList=session.createCriteria(TuitionFeeAcedemicYearDTO.class).add(Expression.eq("status", 1)).add(Expression.in("id", classIdList)).addOrder(Order.asc("id")).list();
	    String query1 ="Select Distinct max(Class_Id) From Tution_Fee_Claim_Details where Child_Relation_Id=? and status in(2,8) and Class_Id in(1,2,3)";
		 Object tuitionFeeRemainingClassId = session.createSQLQuery(query1).setInteger(0, childId).uniqueResult();
		 if(!CPSUtils.isNullOrEmpty(tuitionFeeRemainingClassId)){
		int tuitionFeeRemainingClassId1=((BigDecimal)tuitionFeeRemainingClassId).intValue();
	    if(tuitionFeeRemainingClassId1==2){
	   //removing remaining classId
            String qry ="Select Id as id From (Select Id  From Tution_Fee_Academy_Year_Master Where Id  In (1,2,3)) Where Id Not In (Select Distinct Class_Id From Tution_Fee_Claim_Details Where child_relation_id =? and class_id in (1,2,3) and status in(2,8))order by id";
	         List<TuitionFeeAcedemicYearDTO>  removedClassIdList =  session.createSQLQuery(qry).addScalar("id", Hibernate.INTEGER).setInteger(0, childId).setResultTransformer(Transformers.aliasToBean(TuitionFeeAcedemicYearDTO.class)).list();
	         for(int i=0;i<removedClassIdList.size();i++){
	        	 int id=removedClassIdList.get(i).getId();
	        	for(int j=0;i<tempMainList.size();i++){
	        	 if(id==(tempMainList.get(i).getId())){
	        		 tempMainList.remove(j);
	        	 }
	        	 }
	        	 
	         }
	    }
	   }
	}catch(Exception e){
		throw e;
		 
	}
return tempMainList;
}
@SuppressWarnings("unchecked")
public String checkTutionFeeApplicantDetails(TutionFeeBean tutionFeeBean)throws Exception{
	String message=CPSConstants.SUCCESS;
	Session session=null;
	List<String> messageList=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		messageList=new ArrayList<String>(); 
	    String query= "Select  Tfcd.LIMIT_ID as limitId,tfrd.SFID as sfid,tfrd.REQUEST_ID as requestID,tfrd.REQUEST_TYPE as requestType,tfrd.REQUEST_TYPE_ID as requestTypeID, "+
	    		      " tfrd.SANCTIONED_DATE as sanctionedDate, tfrd.SANCTIONED_AMOUNT as sanctionedAmount,tfrd.GRAND_TOTAL as grandToatal,tfrd.status as status From Tution_fee_request_details tfrd,Tution_Fee_Claim_Details Tfcd "+
	    		      " Where tfrd.Sfid='"+tutionFeeBean.getSfid()+"' And tfrd.Status In (2,8) and Tfrd.Request_Id=Tfcd.Request_Id  ";
	   List<TutionFeeRequestDetailsDTO> keyList=session.createSQLQuery(query).addScalar("sfid",Hibernate.STRING).
       addScalar("requestID",Hibernate.STRING).addScalar("limitId",Hibernate.INTEGER).
	   addScalar("requestType",Hibernate.STRING).addScalar("grandToatal",Hibernate.INTEGER).addScalar("status",Hibernate.INTEGER).
	   addScalar("requestTypeID",Hibernate.STRING).addScalar("sanctionedDate",Hibernate.DATE).
	   addScalar("sanctionedAmount",Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(TutionFeeRequestDetailsDTO.class)).list();
	   
	 /*List<TutionFeeBean> tf=  session.createSQLQuery("select id as childRelationId,name as className from temp").addScalar("childRelationId",Hibernate.INTEGER).addScalar("className",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TutionFeeBean.class)).list();
	 */  
	    if(keyList.size()!=0){
		   for(int m=0;m<keyList.size();m++){
			   TutionFeeRequestDetailsDTO tutionFeeRequestDetailsDTO=keyList.get(m);
		       JSONObject jsonObj=new JSONObject(tutionFeeBean.getMainClaimList());
			for(int i=0;i<jsonObj.length();i++){
				JSONObject sunJSON = (JSONObject)jsonObj.get(String.valueOf(i));
				String childRelationId=sunJSON.getString("childId");
				String limitId=sunJSON.getString("limitId");
	            childRelationId=childRelationId.substring(8);
				List<TutionFeeClaimDetailsDTO> claimList=session.createCriteria(TutionFeeClaimDetailsDTO.class).
				add(Expression.eq("childRelationId", Integer.parseInt(childRelationId))).
				add(Expression.eq("requestId", Integer.parseInt(tutionFeeRequestDetailsDTO.getRequestID()))).add(Expression.eq("limitId",Integer.parseInt(limitId))).list();
			   if(claimList.size()!=0){
					for(int j=0;j<claimList.size();){
						String innerMsg=claimList.get(j).getFamilyDetails().getName()+"\n";
						messageList.add(innerMsg);
						break;
					}
				}
			}
		 }
	   }
	    /*if(messageList.size()>0){
	    	message="You Have Already Claimed For The Reimbursement Of Tution Fee In Respective Of ";
	        for(String m:messageList){
		     message=message+m.toString();
		    }
	    }*/
	}catch (Exception e) {
		throw e;
	}
	finally{
		//session.close();
	}
	return message;
}
public String getTutionFeeConfigDetails() throws Exception{
	String finalValue="";
	Session session=null;
	try{
		session = hibernateUtils.getSession();//session = sessionFactory.openSession();
		String query=" Select Rtrim (Xmlagg (Xmlelement (E, Value || '@')).Extract ('//text()'), '@') value "+
                     " From Configuration_details Where Name='TUITION_FEE_MAX_LIMIT_PER_ONE_CHILD' "+
                     " or name='TUITION_FEE_MAX_CHILD_TO_BE_CLAIMED' ";
		finalValue=session.createSQLQuery(query).uniqueResult().toString();
	}catch (Exception e) {
		throw e;
	}
	finally{
		//session.close();
	}
	return finalValue;
  }
@SuppressWarnings("unchecked")
public Map<String,List<TutionFeeBean>> getTfClaimFinanceDetails(int claimTypeId,TutionFeeBean tfbean) throws Exception{
	Session session=null;
	List<TutionFeeBean> tfClaimFinanceDetails=null;
	List<TutionFeeBean> tfClaimFinanceDetails1=new ArrayList<TutionFeeBean>();
	Map<String,List<TutionFeeBean>> mainList=null;
	List<TutionFeeBean> officersList=null;
	List<TutionFeeBean> staffList=null;
	String type=null;
	int requestTypeId=0;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		mainList=new TreeMap<String,List<TutionFeeBean>>();
		officersList=new ArrayList<TutionFeeBean>();
		staffList=new ArrayList<TutionFeeBean>();
		if(claimTypeId==1){
			requestTypeId=Integer.parseInt(CPSConstants.TUTIONFEEREQUESTTYPEID);
		}else{
			requestTypeId=Integer.parseInt(CPSConstants.TELEPHONEBILLREQUESTTYPEID);
		}
		/*tfClaimFinanceDetails=session.createSQLQuery("Select ecd.id as pk,ecd.request_id as requestID,Em.Sfid as sfID,em.name_in_service_book as empName,tfrd.grand_total as claimedAmount , " +
                            " tfrd.sanctioned_amount sanctionedAmount From Emp_claim_details Ecd,Request_workflow_history Rwh ,Tution_fee_request_details Tfrd,Emp_master Em"+
							" Where Ecd.Request_type_id=? And ecd.Workflow_status=? and rwh.status=8 And Em.Sfid=Tfrd.Sfid And Rwh.Request_type_id=Ecd.Request_type_id And "+
							" Ecd.Request_id=Rwh.Request_id And Rwh.Request_id=Tfrd.Request_id And Ecd.Request_id=Tfrd.Request_id "+ 
							" and Rwh.Request_type_id=tfrd.request_type_id order by ecd.id").addScalar("requestID",Hibernate.STRING).addScalar("pk",Hibernate.STRING).addScalar("claimedAmount",Hibernate.STRING).addScalar("sfID",Hibernate.STRING).addScalar("empName",Hibernate.STRING).addScalar("sanctionedAmount",Hibernate.STRING).setInteger(0, requestTypeId).setInteger(1, Integer.parseInt(CPSConstants.STATUSCOMPLETED)).
							setResultTransformer(Transformers.aliasToBean(TutionFeeBean.class)).list();
		//tfClaimFinanceDetails1.addAll( tfClaimFinanceDetails);
		int size=tfClaimFinanceDetails.size();
		for(int i=0; i<size; i++){
				TutionFeeBean tutionFeeBean =tfClaimFinanceDetails.get(i);
				FinanceDetailsDTO financeDetailsDTO=(FinanceDetailsDTO) session.createCriteria(FinanceDetailsDTO.class).add(Expression.eq("referenceID", Integer.parseInt(tutionFeeBean.getPk()))).uniqueResult();
				if(!CPSUtils.isNullOrEmpty(financeDetailsDTO)){
					tutionFeeBean.setAccOfficer(financeDetailsDTO.getAccountentSign());
					tutionFeeBean.setCfaOfficer(financeDetailsDTO.getRepSig());
					TuitionFeeBillAndSanctionDTO tuitionFeeBillAndSanctionDTO=(TuitionFeeBillAndSanctionDTO) session.get(TuitionFeeBillAndSanctionDTO.class, Integer.parseInt(financeDetailsDTO.getBillNo()));
					if(!CPSUtils.isNullOrEmpty(tuitionFeeBillAndSanctionDTO)){
					tutionFeeBean.setBillNo(String.valueOf(tuitionFeeBillAndSanctionDTO.getBillOrSanctionNo()));
					tuitionFeeBillAndSanctionDTO=(TuitionFeeBillAndSanctionDTO) session.get(TuitionFeeBillAndSanctionDTO.class, Integer.parseInt(financeDetailsDTO.getSanctionNo()));
					tutionFeeBean.setSanctionNo(String.valueOf(tuitionFeeBillAndSanctionDTO.getBillOrSanctionNo()));
					  }
					}
				
				String query ="Select cd.Reference_Id as id From Cda_Details Cd ,Finance_Details Fd Where Fd.Reference_Id= ? and fd.id=Cd.Reference_Id";
				Object financeReferenceId1 =session.createSQLQuery(query).setString(0, tutionFeeBean.getPk()).uniqueResult();
				if(!CPSUtils.isNullOrEmpty(financeReferenceId1)){
					int financeReferenceId = ((BigDecimal)financeReferenceId1).intValue();
				CDADetailsDTO cDADetailsDTO=(CDADetailsDTO) session.createCriteria(CDADetailsDTO.class).add(Expression.eq("referenceID", financeReferenceId)).uniqueResult();
				if(!CPSUtils.isNullOrEmpty(cDADetailsDTO)){
					tfClaimFinanceDetails1.add(tfClaimFinanceDetails.get(i));
					//tfClaimFinanceDetails1.remove(i);
				}
				}
	}
		tfClaimFinanceDetails.removeAll(tfClaimFinanceDetails1);*/
		
		
		tfClaimFinanceDetails=session.createSQLQuery("(SELECT ecd.id AS pk,ecd.request_id AS requestID,Em.Sfid AS sfID,"+
  " em.name_in_service_book AS empName,tfrd.grand_total AS claimedAmount ,tfrd.sanctioned_amount sanctionedAmount,"+
  " fd.ACCOUNTENT_SIGN AS accOfficer,fd.rep_Sig  AS cfaOfficer, tfS.bill_or_sanction_no AS sanctionNo,tfb.bill_or_sanction_no AS billNo,DM.TYPE desig FROM designation_MAPPINGS DM, Request_workflow_history Rwh,Tution_fee_request_details Tfrd,"+
  " Emp_master Em,Emp_claim_details Ecd LEFT OUTER JOIN FINANCE_DETAILS fd ON (ECD.ID=fd.REFERENCE_ID) left outer join tution_fee_bill_sanction TFB ON(TFB.ID=FD.BILL_NUMBER)"+
  " left outer join tution_fee_bill_sanction TFS ON(TFS.ID=fd.sanction_number) LEFT OUTER JOIN Cda_Details Cd ON ( fd.id=Cd.Reference_Id)	 WHERE Ecd.Request_type_id=?"+
  " AND ecd.Workflow_status  =? AND rwh.status=8 AND Em.Sfid =Tfrd.Sfid AND Rwh.Request_type_id =Ecd.Request_type_id AND Ecd.Request_id =Rwh.Request_id AND Rwh.Request_id =Tfrd.Request_id"+
  " AND Ecd.Request_id =Tfrd.Request_id AND Rwh.Request_type_id  =tfrd.request_type_id AND dm.desig_id=EM.DESIGNATION_ID ) MINUS"+
  " (SELECT ecd.id            AS pk,ecd.request_id AS requestID,Em.Sfid  AS sfID,em.name_in_service_book AS empName,tfrd.grand_total  AS claimedAmount ,tfrd.sanctioned_amount sanctionedAmount,"+
  " fd.ACCOUNTENT_SIGN AS accOfficer ,fd.rep_Sig         AS cfaOfficer , tfS.bill_or_sanction_no sanctionNo,tfb.bill_or_sanction_no billNo,DM.TYPE desig FROM designation_MAPPINGS DM, Request_workflow_history Rwh ,Tution_fee_request_details Tfrd,"+
  " Emp_master Em,Emp_claim_details Ecd  LEFT OUTER JOIN FINANCE_DETAILS fd  ON (ECD.ID=fd.REFERENCE_ID) left outer join tution_fee_bill_sanction TFB ON(TFB.ID=FD.BILL_NUMBER)"+
  " left outer join tution_fee_bill_sanction TFS ON(TFS.ID=fd.sanction_number) JOIN Cda_Details Cd ON ( fd.id =Cd.Reference_Id)  WHERE Ecd.Request_type_id=?"+
  " AND ecd.Workflow_status  =?  AND rwh.status =8  AND Em.Sfid =Tfrd.Sfid  AND Rwh.Request_type_id  =Ecd.Request_type_id  AND Ecd.Request_id  =Rwh.Request_id  AND Rwh.Request_id=Tfrd.Request_id"+
  " AND Ecd.Request_id =Tfrd.Request_id  AND Rwh.Request_type_id  =tfrd.request_type_id AND dm.desig_id=EM.DESIGNATION_ID )").addScalar("requestID",Hibernate.STRING).
  addScalar("pk",Hibernate.STRING).addScalar("claimedAmount",Hibernate.STRING).addScalar("sfID",Hibernate.STRING).addScalar("empName",Hibernate.STRING).
  addScalar("sanctionedAmount",Hibernate.STRING).addScalar("accOfficer", Hibernate.STRING).addScalar("cfaOfficer", Hibernate.STRING).
  addScalar("sanctionNo", Hibernate.STRING).addScalar("billNo", Hibernate.STRING).addScalar("desig", Hibernate.STRING).
  setInteger(0, requestTypeId).setInteger(1, Integer.parseInt(CPSConstants.STATUSCOMPLETED)).setInteger(2, requestTypeId).
  setInteger(3, Integer.parseInt(CPSConstants.STATUSCOMPLETED)).setResultTransformer(Transformers.aliasToBean(TutionFeeBean.class)).list();
		
		
		
		
		
		
		for(int j=0;j<tfClaimFinanceDetails.size();j++){
			TutionFeeBean tfBean=tfClaimFinanceDetails.get(j);
			/*type=(String)session.createSQLQuery(" select to_char(type) from designation_mappings where " +
					"desig_id=( select designation_id from emp_master where sfid=?)").setString(0, tfBean.getSfID()).uniqueResult();*/
			if(CPSUtils.compareStrings("GAZETTED", tfBean.getDesig())){
				officersList.add(tfBean);
			}else{
				staffList.add(tfBean);
			}
		}
		mainList.put("officers", officersList);
		mainList.put("staff", staffList);
		
		tfbean.setAccountOfficerList(session.createCriteria(SingingAuthorityDTO.class).add(Expression.eq("type", "ACC")).add(Expression.eq("status", 1)).list());
		
		tfbean.setCfaOfficerList(session.createCriteria(SingingAuthorityDTO.class).add(Expression.eq("type", "CFA")).add(Expression.eq("status", 1)).list());
	}catch (Exception e) {
		throw e;
	}finally{
		//session.close();
	}
	return mainList;
}
public String saveCDAFinanceDetails(TutionFeeBean tutionFeeBean)throws Exception{
	Session session=null;
	String message="";
	Transaction tx=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		//tx=session.beginTransaction();
		JSONObject jsonObj=new JSONObject(tutionFeeBean.getMainCDAList());
		for(int i=0;i<jsonObj.length();i++){
			JSONObject innerJson=(JSONObject) jsonObj.get(String.valueOf(i));
			FinanceDetailsDTO finDetailsDTO=(FinanceDetailsDTO) session.createCriteria(FinanceDetailsDTO.class).add(Expression.eq("referenceID", Integer.parseInt(innerJson.getString("financeId")))).uniqueResult();
			
			if(CPSUtils.isNullOrEmpty(finDetailsDTO)){
			finDetailsDTO=new FinanceDetailsDTO();
			finDetailsDTO.setCreatedBy(tutionFeeBean.getSfid());
			finDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
			}
			finDetailsDTO.setAmount(Integer.parseInt(innerJson.getString("sanctionedAmount")));
			finDetailsDTO.setAccountentSign(tutionFeeBean.getAccOfficer());
			finDetailsDTO.setRepSig(tutionFeeBean.getCfaOfficer());
			
			if(finDetailsDTO.getId()==0){
				
				TuitionFeeBillAndSanctionDTO tuitionFeeBillAndSanctionDTO = new TuitionFeeBillAndSanctionDTO(); 
				tuitionFeeBillAndSanctionDTO.setFinancialYearId(getCurrentFinancialYearId());
				//tuitionFeeBillAndSanctionDTO.setBillOrSanctionNo(Integer.parseInt(innerJson.getString("sanctionNo")));
				tuitionFeeBillAndSanctionDTO.setBillOrSanctionNo(Integer.parseInt(tutionFeeBean.getSanctionNo()));
				if(tutionFeeBean.getClaimTypeId()==1){
					tuitionFeeBillAndSanctionDTO.setClaimTypeId(Integer.parseInt(CPSConstants.TUTIONFEECLAIMID));
				}else if(tutionFeeBean.getClaimTypeId()==2){
					tuitionFeeBillAndSanctionDTO.setClaimTypeId(Integer.parseInt(CPSConstants.TELEPHONEBILLCLAIMID));
				}
				tuitionFeeBillAndSanctionDTO.setType("S");
				tuitionFeeBillAndSanctionDTO.setStatus(1);
				finDetailsDTO.setSanctionNo(String.valueOf(session.save(tuitionFeeBillAndSanctionDTO)));
				
				tuitionFeeBillAndSanctionDTO = new TuitionFeeBillAndSanctionDTO(); 
				tuitionFeeBillAndSanctionDTO.setFinancialYearId(getCurrentFinancialYearId());
				//tuitionFeeBillAndSanctionDTO.setBillOrSanctionNo(Integer.parseInt(innerJson.getString("billNo")));
				tuitionFeeBillAndSanctionDTO.setBillOrSanctionNo(Integer.parseInt(tutionFeeBean.getBillNo()));
				if(tutionFeeBean.getClaimTypeId()==1){
					tuitionFeeBillAndSanctionDTO.setClaimTypeId(Integer.parseInt(CPSConstants.TUTIONFEECLAIMID));
				}else if(tutionFeeBean.getClaimTypeId()==2){
					tuitionFeeBillAndSanctionDTO.setClaimTypeId(Integer.parseInt(CPSConstants.TELEPHONEBILLCLAIMID));
				}
				tuitionFeeBillAndSanctionDTO.setType("B");
				tuitionFeeBillAndSanctionDTO.setStatus(1);
				finDetailsDTO.setBillNo(String.valueOf(session.save(tuitionFeeBillAndSanctionDTO)));
			}
			else{
				TuitionFeeBillAndSanctionDTO tuitionFeeBillAndSanctionDTO = (TuitionFeeBillAndSanctionDTO) session.get(TuitionFeeBillAndSanctionDTO.class, Integer.parseInt(finDetailsDTO.getBillNo()));
				//tuitionFeeBillAndSanctionDTO.setBillOrSanctionNo(Integer.parseInt(innerJson.getString("billNo")));
				tuitionFeeBillAndSanctionDTO.setBillOrSanctionNo(Integer.parseInt(tutionFeeBean.getBillNo()));
				if(tutionFeeBean.getClaimTypeId()==1){
					tuitionFeeBillAndSanctionDTO.setClaimTypeId(Integer.parseInt(CPSConstants.TUTIONFEECLAIMID));
				}else if(tutionFeeBean.getClaimTypeId()==2){
					tuitionFeeBillAndSanctionDTO.setClaimTypeId(Integer.parseInt(CPSConstants.TELEPHONEBILLCLAIMID));
				}
				session.update(tuitionFeeBillAndSanctionDTO);
				
				tuitionFeeBillAndSanctionDTO = (TuitionFeeBillAndSanctionDTO) session.get(TuitionFeeBillAndSanctionDTO.class, Integer.parseInt(finDetailsDTO.getSanctionNo()));
				//tuitionFeeBillAndSanctionDTO.setBillOrSanctionNo(Integer.parseInt(innerJson.getString("sanctionNo")));
				tuitionFeeBillAndSanctionDTO.setBillOrSanctionNo(Integer.parseInt(tutionFeeBean.getSanctionNo()));
				if(tutionFeeBean.getClaimTypeId()==1){
					tuitionFeeBillAndSanctionDTO.setClaimTypeId(Integer.parseInt(CPSConstants.TUTIONFEECLAIMID));
				}else if(tutionFeeBean.getClaimTypeId()==2){
					tuitionFeeBillAndSanctionDTO.setClaimTypeId(Integer.parseInt(CPSConstants.TELEPHONEBILLCLAIMID));
				}
				session.update(tuitionFeeBillAndSanctionDTO);

			}
			
			finDetailsDTO.setReferenceID(Integer.parseInt(innerJson.getString("financeId")));
			finDetailsDTO.setLastModifiedBy(tutionFeeBean.getSfid());
			finDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
			finDetailsDTO.setStatus(1);
			session.saveOrUpdate(finDetailsDTO);
			
			/*finDetailsDTO.setReferenceID(Integer.parseInt(innerJson.getString("financeId")));
			finDetailsDTO.setLastModifiedBy(tutionFeeBean.getSfid());
			finDetailsDTO.setStatus(1);
			if(finDetailsDTO.getId()==0 ){
				int resultId =0;
				int cdaReferenceId=((BigDecimal)session.createSQLQuery("select max(reference_id)+1 from cda_details").uniqueResult()).intValue();
				int finceId = ((BigDecimal)session.createSQLQuery("select max(id) from finance_details").uniqueResult()).intValue();
				if(finceId>=cdaReferenceId){
					resultId = finceId+1;
				}else{
					resultId =cdaReferenceId;
				}
				String sql = "insert into FINANCE_DETAILS(ID,AMOUNT,ACCOUNTENT_SIGN,CREATED_BY,CREATION_DATE,LAST_MODIFIED_DATE,REFERENCE_ID,LAST_MODIFIED_BY,STATUS,SANCTION_NUMBER,BILL_NUMBER) values("+resultId+","+finDetailsDTO.getAmount()+",'"+finDetailsDTO.getAccountentSign()+"','"+finDetailsDTO.getCreatedBy()+"','"+CPSUtils.getCurrentDate()+"','"+CPSUtils.getCurrentDate()+"',"+finDetailsDTO.getReferenceID()+",'"+finDetailsDTO.getLastModifiedBy()+"',1,"+finDetailsDTO.getSanctionNo()+","+finDetailsDTO.getBillNo()+")";
				session.createSQLQuery(sql).executeUpdate();
				session.flush();
			}else{
				finDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				session.saveOrUpdate(finDetailsDTO);
				session.flush();
			}*/
		  message=CPSConstants.SUCCESS;
		}
		session.flush();//tx.commit() ;
	}catch (Exception e) {
		//tx.rollback();
		e.printStackTrace();
		throw e;
	}finally{
		//session.close();
	}
	return message;
}
@SuppressWarnings("unchecked")
public String submitTuitionFeeClaimMasterDetails(TutionFeeClaimMasterDTO tutionFeeClaimMasterDTO)throws Exception{
	String message="";
	Session session=null;
	Transaction tx=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		//tx=session.beginTransaction();
		List<TutionFeeClaimMasterDTO> keyList=session.createCriteria(TutionFeeClaimMasterDTO.class).add(Expression.eq("claimName", tutionFeeClaimMasterDTO.getClaimName())).add(Expression.eq("status", 1)).list();
		if(keyList.size()==0){
		session.saveOrUpdate(tutionFeeClaimMasterDTO);
		session.flush();//tx.commit() ;
		session.clear();
		message=CPSConstants.SUCCESS;
		}else{
			message=CPSConstants.DUPLICATE;
		}
	}catch (Exception e) {
		//tx.rollback();
		throw e;
	}finally{
		//session.close();
	}
	return message;
}
@SuppressWarnings("unchecked")
public List<TutionFeeClaimMasterDTO> getTuitionFeeClaimMasterDetails()throws Exception{
    Session session=null;
    List<TutionFeeClaimMasterDTO> tutionFeeClaimMasterList=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		tutionFeeClaimMasterList=session.createCriteria(TutionFeeClaimMasterDTO.class).add(Expression.eq("status", 1)).addOrder(Order.asc("claimTypeId")).addOrder(Order.asc("orderNo")).list();
	}catch (Exception e){
		throw e;
	}finally{
		//session.close();
	}
    return tutionFeeClaimMasterList;
}
@SuppressWarnings("unchecked")
public List<ClaimTypeMasterDTO> getClaimTypeMasterDetails()throws Exception{
	Session session=null;
	List<ClaimTypeMasterDTO> claimTypeMasterList=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		claimTypeMasterList=session.createCriteria(ClaimTypeMasterDTO.class).add(Expression.eq("status", 1)).list();
	}catch (Exception e) {
		throw e;
	}finally{
		//session.close();
	}
	return claimTypeMasterList;
}
@SuppressWarnings("unchecked")
public List<TutionFeeBean> getTuitionFeeSendToCDAList(int billNo,int finYearId,int claimTypeId,TutionFeeBean tutionFeeBean)throws Exception{
	List<TutionFeeBean> keyList=null;
	Session session=null;
	int requestTypeId=0;
	String message =null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		if(claimTypeId==1){
			requestTypeId=Integer.parseInt(CPSConstants.TUTIONFEEREQUESTTYPEID);
		 String query=" Select tab.RequestId as requestId,Tab.Pk As pk,Tab.Sfid As sfid,Tab.Empname As empName,Tab.Desig As "+
		                 " desig,Tab.Claimedamount As claimedAmount,Tab.Sanctionedamount As sanctionedAmount, "+
	                     " Tab.Billnumber As billNumber,Cda.Cda_amount As cdaAmount, "+
		                 " cda.dv_no as dvNo,cda.dv_date as dvDate From (Select Tfrd.Request_Id as RequestId,Fd.Id As Pk, Em.Sfid As Sfid, "+
		                 " Em.Name_in_service_book As empName,Ds.Name As desig,Tfrd.Grand_total As Claimedamount,"+
		                 " tfrd.sanctioned_amount as sanctionedAmount,Fd.Bill_number as billNumber "+
		                 " From Finance_details Fd,Emp_claim_details Ecd, "+
		                 " Tution_fee_request_details Tfrd,Emp_master Em,Designation_master Ds,Request_Workflow_History req "+
		                 " Where Fd.Reference_id=Ecd.Id  And Tfrd.Request_id=Ecd.Request_id "+
		                 " And Em.Sfid=Tfrd.Sfid And Em.Designation_id=Ds.Id and req.status=8 "+
		                 " And Fd.Bill_Number In (Select Id From Tution_Fee_Bill_Sanction  Bill "+
                         " Where Bill.Bill_Or_Sanction_No=? And Bill.Type='B') And Fd.Reference_Id=Ecd.Id And  "+
                         " req.request_id=Ecd.Request_Id and Req.Request_Type_Id=? and tfrd.financial_year_id in" +
                         "(Select Distinct Tfrd.Financial_year_id From Tution_fee_request_details Tfrd,Emp_claim_details Ecd,Finance_details Fd," +
                         " Tution_fee_bill_sanction Tfbs Where Tfrd.Request_type_id=52 And Tfrd.Request_id=Ecd.Request_id And Ecd.Id=Fd.Reference_id And Tfbs.Id=Fd.Bill_number And Tfbs.Bill_or_sanction_no= ?))"+
                         " tab,cda_details cda where tab.pk=cda.reference_id(+) and Cda.Cda_amount is null and Cda.Dv_No is null and Cda.Dv_Date is null";

		keyList=session.createSQLQuery(query).addScalar("requestID",Hibernate.STRING).addScalar("pk",Hibernate.STRING).addScalar("sfid",Hibernate.STRING).addScalar("empName",Hibernate.STRING).
		        addScalar("desig",Hibernate.STRING).addScalar("cdaAmount",Hibernate.STRING).addScalar("dvDate",Hibernate.DATE).addScalar("dvNo",Hibernate.STRING).
		        addScalar("claimedAmount",Hibernate.STRING).addScalar("sanctionedAmount",Hibernate.STRING).setInteger(0, billNo).setInteger(1, requestTypeId).setInteger(2, billNo).
		        setResultTransformer(Transformers.aliasToBean(TutionFeeBean.class)).list();
		}else if(claimTypeId==2){
			requestTypeId=Integer.parseInt(CPSConstants.TELEPHONEBILLREQUESTTYPEID);
			String query= " Select Tab.Requestid As requestID,Tab.Pk As pk,Tab.Sfid As sfid,Tab.Empname  As empName,Tab.Desig As desig,Tab.Sanctionedamount As sanctionedAmount,Tab.Eligibility As eligiblity,Tab.Amtadmissible As amtAdmissible,Tab.Billnumber As Billnumber," +
			 			  " Cda.Cda_amount As cdaAmount,Cda.Dv_no  As dvNo,Cda.Dv_date As dvDate From(Select Requestid,Pk,Sfid,Empname,Desig,Sanctionedamount,Eligibility,Billnumber,(Case When To_number(Sanctionedamount)>To_number(Eligibility) Then To_number(Eligibility) " +
			 			  " Else To_number(Sanctionedamount) End) Amtadmissible From(Select Distinct Tfrd.Request_id As Requestid,Fd.Id As Pk,Em.Sfid As Sfid,Em.Name_in_service_book As Empname, Ds.Name   As Desig,Tfrd.Sanctioned_amount As Sanctionedamount,Fd.Bill_number  As Billnumber," +
			 			  "(Case When Tbcd.Internet_flag =1 Then Tded.Total_amount_withinternet Else Tded.Total_amount_without_internet End) Eligibility From Finance_details Fd,Emp_claim_details Ecd,Tution_fee_request_details Tfrd,Emp_master Em, Designation_master Ds,Request_workflow_history Req," +
			 			  " Tele_desig_eligibile_details Tded,Telephone_bill_claim_details Tbcd Where Fd.Reference_id =Ecd.Id And Tfrd.Request_id  =Ecd.Request_id And Em.Sfid =Tfrd.Sfid And Tded.Designation_id   =Ds.Id And Req.Status =8 " +
			 			  " And Tbcd.Eligibility_id =Tded.Id  And Tbcd.Request_id     =Ecd.Request_id And Fd.Bill_number  In (Select Id From Tution_fee_bill_sanction Bill Where Bill.Bill_or_sanction_no=? And Bill.Type ='B')And Fd.Reference_id =Ecd.Id And " +
			 			  " Req.Request_id =Ecd.Request_id And Req.Request_type_id =? And Tfrd.Financial_year_id In(Select Distinct Tfrd.Financial_year_id From Tution_fee_request_details Tfrd,Emp_claim_details Ecd,Finance_details Fd,Tution_fee_bill_sanction Tfbs Where Tfrd.Request_type_id=55" +
			 			  " And Tfrd.Request_id=Ecd.Request_id And Ecd.Id=Fd.Reference_id And Tfbs.Id=Fd.Bill_number And Tfbs.Bill_or_sanction_no= ?))Order By Requestid)Tab,Cda_details Cda Where Tab.Pk =Cda.Reference_id(+) " +
			 			  " And Cda.Cda_amount Is Null And Cda.Dv_no Is Null And Cda.Dv_date Is Null";
			 keyList=session.createSQLQuery(query).addScalar("requestID",Hibernate.STRING).addScalar("pk",Hibernate.STRING).addScalar("sfid",Hibernate.STRING).addScalar("empName",Hibernate.STRING).
			 			addScalar("desig",Hibernate.STRING).addScalar("cdaAmount",Hibernate.STRING).addScalar("dvDate",Hibernate.DATE).addScalar("dvNo",Hibernate.STRING).addScalar("eligiblity", Hibernate.INTEGER).addScalar("amtAdmissible", Hibernate.INTEGER).
			 		    addScalar("sanctionedAmount",Hibernate.STRING).setInteger(0, billNo).setInteger(1, requestTypeId).setInteger(2, billNo).
			 			setResultTransformer(Transformers.aliasToBean(TutionFeeBean.class)).list();
		}
	
	}catch (Exception e){
		throw e;
	}finally{
		//session.close();
	}
	return keyList;
}
public int getCurrentFinancialYearId()throws Exception{
	Session session=null;
	int id=0;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		id=(Integer.parseInt((session.createSQLQuery("select to_char(id) from financial_year_master where sysdate between from_date and to_date").uniqueResult().toString())));
	}catch (Exception e){
		throw e;
	}finally{
		//session.close();
	}
	return id;
}
public int getFinancialYearId(String finYearName) throws Exception{
	Session session=null;
	int id=0;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		id=(Integer.parseInt((session.createSQLQuery("select to_char(id) from financial_year_master where to_char(from_date,'yyyy')||'-'||to_char(to_date,'yyyy')='"+finYearName+"'").uniqueResult().toString())));
		}catch (Exception e){
		throw e;
	}finally{
		//session.close();
	}
	return id;
}
public String getCurrentFinancialYear(int claimTypeId) throws Exception{
	Session session=null;
	String name="";
		try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		int id=getCurrentFinancialYearId();
		name=(String)session.createSQLQuery("select to_char(from_date,'yyyy')||'-'||to_char(to_date,'yyyy') finYear from financial_year_master where id="+id).uniqueResult();
		}catch (Exception e){
		throw e;
	}finally{
		//session.close();
	}
	return name;
}
public String saveCDADetails(TutionFeeBean tutionFeeBean)throws Exception{
	Session session=null;
	String message="";
	Transaction tx=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		//tx=session.beginTransaction();
		JSONObject jsonObj=new JSONObject(tutionFeeBean.getMainCDAList());
		for(int i=0;i<jsonObj.length();i++){
			JSONObject innerJson=(JSONObject) jsonObj.get(String.valueOf(i));
			
			CDADetailsDTO cDADetailsDTO=(CDADetailsDTO) session.createCriteria(CDADetailsDTO.class).add(Expression.eq("referenceID", Integer.parseInt(innerJson.getString("referenceID")))).uniqueResult();
			if(CPSUtils.isNullOrEmpty(cDADetailsDTO)){
				cDADetailsDTO=new CDADetailsDTO();
				cDADetailsDTO.setCreatedBy(tutionFeeBean.getSfid());
				cDADetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
			}
			cDADetailsDTO.setCdaAmount(Float.parseFloat(innerJson.getString("cdaAmount").toString().equalsIgnoreCase("")? "0":innerJson.getString("cdaAmount")));
			//cDADetailsDTO.setDvNumber(innerJson.getString("dvNo").toString().equalsIgnoreCase("")? null:innerJson.getString("dvNo"));
			//cDADetailsDTO.setDvDate(innerJson.getString("dvDate").toString().equalsIgnoreCase("")? null:new SimpleDateFormat("dd-MMM-yyyy").parse(innerJson.getString("dvDate")));
			cDADetailsDTO.setDvNumber(tutionFeeBean.getDvNo().toString().equalsIgnoreCase("")? null:tutionFeeBean.getDvNo());
			cDADetailsDTO.setDvDate(tutionFeeBean.getDvDate().toString().equalsIgnoreCase("")? null:tutionFeeBean.getDvDate());
			cDADetailsDTO.setReferenceID(Integer.parseInt(innerJson.getString("referenceID")));
			cDADetailsDTO.setCreatedBy(tutionFeeBean.getSfid());
			cDADetailsDTO.setLastModifiedBy(tutionFeeBean.getSfid());
			cDADetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
			cDADetailsDTO.setStatus(1);
			session.saveOrUpdate(cDADetailsDTO);
			message=CPSConstants.SUCCESS;
		}
		session.flush();//tx.commit() ;
	}catch (Exception e) {
 		//tx.rollback();
		throw e;
	}finally{
		//session.close();
	}
	return message;
}
public String deleteTuitionFeeClaimDetails(int id)throws Exception{
	Session session=null;
	Transaction transaction=null;
	String message="";
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		TutionFeeClaimMasterDTO tutionFeeClaimMasterDTO=(TutionFeeClaimMasterDTO) session.get(TutionFeeClaimMasterDTO.class, id);
		//transaction=session.beginTransaction();
		if(tutionFeeClaimMasterDTO!=null){
			tutionFeeClaimMasterDTO.setStatus(0);
			session.update(tutionFeeClaimMasterDTO);
			message=CPSConstants.DELETE;
		}else{
			message=CPSConstants.FAILED;
		}
		session.flush() ; //transaction.commit();	
	}catch (Exception e) {
		//transaction.rollback();
		throw e;
	}finally{
		//session.close();
	}
	return message;
}
public String deleteTutionFeeLimitMasterDetails(int id) throws Exception{
	Session session=null;
	String message="";
	Transaction transaction=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		//transaction=session.beginTransaction();
		TutionFeeLimitMasterDTO tutionFeeLimitMasterDTO =(TutionFeeLimitMasterDTO)session.get(TutionFeeLimitMasterDTO.class,id);
		tutionFeeLimitMasterDTO.setStatus(0);
		message=CPSConstants.DELETE;	
		session.flush() ; //transaction.commit();
	}catch (Exception e) {
		//transaction.rollback();
		throw e;
	}finally{
		//session.close();
	}
	return message;
}
@SuppressWarnings("unchecked")
public String submitTutionFeeAcademicYearMasterDetails(TuitionFeeAcedemicYearDTO tuitionFeeAcedemicYearDTO)throws Exception{
	String message="";
	Session session=null;
	Transaction tx=null;
	try{
        	session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		    //tx=session.beginTransaction();
		    List<TuitionFeeAcedemicYearDTO> keyList=session.createCriteria(TuitionFeeAcedemicYearDTO.class).add(Expression.eq("className",tuitionFeeAcedemicYearDTO.getClassName())).add(Expression.eq("status", 1)).list();
		    if(keyList.size()==0){
		    session.saveOrUpdate(tuitionFeeAcedemicYearDTO);
			session.flush();//tx.commit() ;
		    message=CPSConstants.SUCCESS;
		    }else{
		    	message=CPSConstants.DUPLICATE;
		    }
	}catch (Exception e) {
		//tx.rollback();
		throw e;
	}finally{
		//session.close();
	}
	return message;
  }
@SuppressWarnings("unchecked")
public List<TuitionFeeAcedemicYearDTO> getTutionFeeAcademicYearDetails()throws Exception{
    Session session=null;
    List<TuitionFeeAcedemicYearDTO> tutionFeeAcademicYear=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		tutionFeeAcademicYear=session.createCriteria(TuitionFeeAcedemicYearDTO.class).add(Expression.eq("status", 1)).addOrder(Order.asc("orderNo")).list();
		
	}catch (Exception e){
		throw e;
	}finally{
		//session.close();
	}
    return tutionFeeAcademicYear;
}
public String deleteTutionFeeAcademicYearMasterDetails(int id)throws Exception{
	Session session = null;
	Transaction transaction=null;
		String message="";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//update
			TuitionFeeAcedemicYearDTO tuitionFeeAcedemicYearDTO=(TuitionFeeAcedemicYearDTO)session.get(TuitionFeeAcedemicYearDTO.class,id);
			//transaction=session.beginTransaction();
			if(tuitionFeeAcedemicYearDTO!=null){
				tuitionFeeAcedemicYearDTO.setStatus(0);
				session.update(tuitionFeeAcedemicYearDTO);
				message=CPSConstants.DELETE;
			}else{
				message=CPSConstants.FAILED;
			}
			session.flush() ; //transaction.commit();
		} catch (Exception e) {
			//transaction.rollback();
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}
public String submitTutionFeeLimitMaster(TutionFeeLimitMasterDTO tutionFeeLimitMasterDTO)throws Exception{
	String message="";
	Session session=null;
	Transaction tx=null;
	try{
            session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
            //tx=session.beginTransaction();
	        session.saveOrUpdate(tutionFeeLimitMasterDTO);
   	        message=CPSConstants.SUCCESS;
		    session.flush();//tx.commit() ;
	}catch (Exception e) {
		//tx.rollback();
	    throw e;
	}finally{
		//session.close();
	}
	return message;
  }
@SuppressWarnings("unchecked")
public List<TutionFeeLimitMasterDTO> getTutionFeeLimitMasterDetails()throws Exception{
	Session session = null;
    List<TutionFeeLimitMasterDTO> tutionFeeLimitMaster = null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
	tutionFeeLimitMaster=session.createCriteria(TutionFeeLimitMasterDTO.class).add(Expression.eq("status", 1)).addOrder(Order.asc("id")).list();
	}catch (Exception e) {
		throw e;
}finally{
	//session.close();
}
return tutionFeeLimitMaster;  
}
//new present working code of requestform
@SuppressWarnings("unchecked")
public List<KeyValueDTO> setTutionFeeRequestDetails(String acedemicType,String type,String userSfid,String academicYear,String familyChildId) throws Exception{
	Session session=null;
	List<KeyValueDTO> keyList=null;
	List<KeyValueDTO> keyList2=new ArrayList<KeyValueDTO>();
	//List<KeyValueDTO> keyList3=null;
    try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		
		String date=(String)session.createSQLQuery("select from_date from tution_fee_limit_master where academic_type='"+acedemicType+"' and limit=100 and status=1").uniqueResult();
		date=date.concat("-"+academicYear+"");
		//String query= "SELECT Fd.Id AS id,Tflm.Id AS KEY,Tflm.Academic_Type||'-' ||Tflm.Type||'-'||TO_CHAR(Tflm.From_Date)||'-'||TO_CHAR(Tflm.To_Date) AS Value FROM Tution_Fee_Limit_Master Tflm,Family_Details Fd WHERE Tflm.Status =1 and academic_type=? AND Fd.Id IN (SELECT id FROM family_details WHERE sfid=? AND id=? AND relation_id IN(5,6))ORDER BY Tflm.Id"; // old query back up before adding function
		String query= "SELECT Fd.Id AS id,Tflm.Id AS KEY,Tflm.Academic_Type||'-' ||Tflm.Type||'-'||(select get_tuitionfee_claim_period(Tflm.Type,'"+date+"','fromDate')  from dual)||'---'||(select get_tuitionfee_claim_period(Tflm.Type,'"+date+"','toDate') from dual) AS Value FROM Tution_Fee_Limit_Master Tflm,Family_Details Fd WHERE Tflm.Status =1 and fd.status=1 and academic_type=? AND Fd.Id IN (SELECT id FROM family_details WHERE sfid=? AND id=? AND relation_id IN(5,6) and status=1)ORDER BY Tflm.Id";
		keyList=session.createSQLQuery(query).addScalar("id",Hibernate.INTEGER).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setString(0, acedemicType).setString(1, userSfid).setString(2, familyChildId).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
		
		String query1="Select Distinct max(Limit_Id) As key,Boardtype as value From Tution_Fee_Claim_Details where Child_Relation_Id=? And Status  In (2,8) and sfid=? and To_char(From_Date,'yyyy')='"+academicYear+"' group by Boardtype" ;
	    List<KeyValueDTO> tutionFeeClaimList  = session.createSQLQuery(query1).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setString(0, familyChildId).setString(1, userSfid).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
	    if(tutionFeeClaimList.size()>0){
	    for(int i=0;i<tutionFeeClaimList.size();i++){
	    	String key=tutionFeeClaimList.get(i).getKey();
	    	String value=tutionFeeClaimList.get(i).getValue();
	    	for(int j=0;j<keyList.size();j++){
 	    		if(value.equals(acedemicType)){
	    			 if(key.equals(keyList.get(j).getKey())){
	    				 for(int k=j;k>=0;k--){  // this loop for removing applied one and before of it
	    				  		 // for H1 remaining Q4,H2
			    				 if(tutionFeeClaimList.get(i).getKey().equals("5") || tutionFeeClaimList.get(i).getKey().equals("12")){
			   					  	if(!(keyList.get(k).getKey().equals("4") || keyList.get(k).getKey().equals("11"))){
			   					  		keyList.remove(k);
			   					  	    }
			    				    }
			    				 //for Q1 remaining Q2,Q3,Q4,H2
			    				 else if(tutionFeeClaimList.get(i).getKey().equals("1") || tutionFeeClaimList.get(i).getKey().equals("8")){
			    					 	keyList.remove(k);
			    				      }
			    				//for Q1 and Q2 remaining Q4,H2
			    				 else if(tutionFeeClaimList.get(i).getKey().equals("2") || tutionFeeClaimList.get(i).getKey().equals("9")){
			    							keyList.remove(k);
			    				      }
			    				 //modifiying code on 01082013 Applied for Q3 remaining Q4
			    				 else if(tutionFeeClaimList.get(i).getKey().equals("3") || tutionFeeClaimList.get(i).getKey().equals("10")){
			    						keyList.remove(k);
			    				 }
			    				}	
	    				 for(int k=0;k<keyList.size();k++){ // this loop for removing remaining list
	    					 //for H1 remaining Q4,H2
		    				 if(tutionFeeClaimList.get(i).getKey().equals("5") || tutionFeeClaimList.get(i).getKey().equals("12")){
		    					 if(!(keyList.get(k).getKey().equals("4") || keyList.get(k).getKey().equals("11"))){ // modified to get  q4 for state 
		    						if(type!=("Halfyearly")){
		    							 keyList.remove(k);
		    						}
		    					 }
		    					 if(type.equals("Quartarly")){ // for removing halfyearly from list
	    								if(keyList.get(k).getKey().equals("13")|| keyList.get(k).getKey().equals("6")){
	    									keyList.remove(k);
	    								}
	    								if(keyList.get(k).getKey().equals("14") || keyList.get(k).getKey().equals("7")){
	    									keyList.remove(k);
	    								}
		    						}
		    					if(type.equals("Halfyearly")){ // for removing Quarterly from list
		    					    		 if(keyList.get(k).getKey().equals("11") || keyList.get(k).getKey().equals("4")){
				    							   keyList.remove(k); 
				    						   }
		    					    		 if(keyList.get(k).getKey().equals("14") || keyList.get(k).getKey().equals("7")){
		    					    			 keyList.remove(k);
		    					    		 }
		    					    		 
		    					   }
		    					if(type.equals("Annually")){
		    								keyList.removeAll(keyList);
			    				 } 
		    				 }
	    					//for Q1 remaining Q2,Q3,Q4,H2
		    				 String flag="";
	    					 if(tutionFeeClaimList.get(i).getKey().equals("1") || tutionFeeClaimList.get(i).getKey().equals("8")){
		    					   if(!(keyList.get(k).getKey().equals("2") || keyList.get(k).getKey().equals("3") || keyList.get(k).getKey().equals("4") || keyList.get(k).getKey().equals("9") || keyList.get(k).getKey().equals("10") || keyList.get(k).getKey().equals("11"))){	
		    						   if(type!=("Halfyearly")){
		    						   keyList.remove(k);
		    						   }
		    							if(type.equals("Halfyearly")){  // for removing Quarterly from list
				    						   if(!(keyList.get(k).getKey().equals("13")|| keyList.get(k).getKey().equals("6"))){
				    							   keyList.remove(k);
				    							   flag ="Y";
				    						   }
				    					  }
		    						}
		    					   if(type.equals("Quartarly")){ // for removing halfyearly from list
	    								if(keyList.get(k).getKey().equals("13")|| keyList.get(k).getKey().equals("6")){
	    									keyList.remove(k);
	    								}
	    								if(keyList.get(k).getKey().equals("14") || keyList.get(k).getKey().equals("7")){
	    									keyList.remove(k);
	    								}
		    						}
		    					    if(type.equals("Halfyearly")){ // for removing Quarterly from list
		    					    	if(flag==""){
		    					    		 if(keyList.get(k).getKey().equals("9")||  keyList.get(k).getKey().equals("10") || keyList.get(k).getKey().equals("11") || keyList.get(k).getKey().equals("2") || keyList.get(k).getKey().equals("3") || keyList.get(k).getKey().equals("4")){
				    							   keyList.remove(k); 
				    						   }
		    					    		 if(keyList.get(k).getKey().equals("12") || keyList.get(k).getKey().equals("5")){
				    							   keyList.remove(k);
				    						   }
		    					    	} else
			    							  keyList.remove(0);  
		    						 
	    						   }
		    					   if(type.equals("Annually")){
	    								keyList.removeAll(keyList);
		    						}
		    				}
    				  		//for Q1 and Q2 remaining Q4,H2
		    				  else if(tutionFeeClaimList.get(i).getKey().equals("2") || tutionFeeClaimList.get(i).getKey().equals("9")){
		    						if(!(keyList.get(k).getKey().equals("4") || keyList.get(k).getKey().equals("11") )){		 
		    							if(type!=("Halfyearly")){
		    							keyList.remove(k);
		    							}
		    							if(type.equals("Halfyearly")){ // for removing Quarterly from list
				    						   if(!(keyList.get(k).getKey().equals("13")|| keyList.get(k).getKey().equals("6"))){
				    							   keyList.remove(k);
				    							   flag ="Y";
				    						   }
				    					  }
		    						}
		    						if(type.equals("Quartarly")){ // for removing halfyearly from list
	    								if(keyList.get(k).getKey().equals("13")|| keyList.get(k).getKey().equals("6") ){
	    									keyList.remove(k);
	    								}
	    								if(keyList.get(k).getKey().equals("14") || keyList.get(k).getKey().equals("7")){
	    									keyList.remove(k);
	    								}
		    						}
		    						if(type.equals("Halfyearly")){ // for removing Quarterly from list
		    					    	if(flag==""){
		    					    		 if(keyList.get(k).getKey().equals("10")||  keyList.get(k).getKey().equals("11") || keyList.get(k).getKey().equals("12") || keyList.get(k).getKey().equals("3") || keyList.get(k).getKey().equals("4") || keyList.get(k).getKey().equals("5")){
				    							   keyList.remove(k); 
				    						   }
		    					    		 if(keyList.get(k).getKey().equals("12")){
				    							   keyList.remove(k);
				    						   }
		    					    	} else
			    							  keyList.remove(0);  
		    						 
	    						   }
		    						if(type.equals("Annually")){
	    								keyList.removeAll(keyList);
		    						}
		    				  }
	    					 //modified code on 01082013 Applied for Q3 removing H1,H2,Annually remaining Q4
		    				  else if(tutionFeeClaimList.get(i).getKey().equals("3") || tutionFeeClaimList.get(i).getKey().equals("10")){
		    					  if(!(keyList.get(k).getKey().equals("4") || keyList.get(k).getKey().equals("11") )){		 
		    							if(type!=("Halfyearly")){
		    							keyList.remove(k);
		    							}
		    							if(type.equals("Halfyearly")){ // for removing Quarterly from list
				    						   if(!(keyList.get(k).getKey().equals("13")|| keyList.get(k).getKey().equals("6"))){
				    							   keyList.remove(k);
				    							   flag ="Y";
				    						   }
				    					  }
		    						}
		    						if(type.equals("Quartarly")){ // for removing halfyearly from list
	    								if(keyList.get(k).getKey().equals("13")|| keyList.get(k).getKey().equals("6") ){
	    									keyList.remove(k);
	    								}
	    								if(keyList.get(k).getKey().equals("14") || keyList.get(k).getKey().equals("7")){
	    									keyList.remove(k);
	    								}
		    						}
		    						if(type.equals("Halfyearly")){ // for removing Quarterly from list and removing the complete list because applied for Q3
		    							keyList.removeAll(keyList);
		    					   }
		    						if(type.equals("Annually")){ // for removing the complete list because applied for Q3
	    								keyList.removeAll(keyList);
		    						}
		    				  }
		    				}	
	    			  }
	    		}else{
	    			for(int k=keyList.size();k>0;k--){
	    				keyList.remove(k-1);
	    			}
	    				
	    		}
	     	}
	    	// for Q4 and H2
	    	for(int z=0; z<keyList.size(); z++){
	    		if(tutionFeeClaimList.get(i).getKey().equals("6") || tutionFeeClaimList.get(i).getKey().equals("13") || tutionFeeClaimList.get(i).getKey().equals("4") || tutionFeeClaimList.get(i).getKey().equals("11") || tutionFeeClaimList.get(i).getKey().equals("14") || tutionFeeClaimList.get(i).getKey().equals("7")){
					  keyList.removeAll(keyList);
	    		}
	    	}
	    	
	    }
	  	}else{
			if(CPSUtils.compareStrings(type, "Quartarly")){
				 //query1= "Select Fd.Id as id,Tflm.Id As Key,Tflm.Academic_Type||'-'||Tflm.Type||'-'||To_Char(Tflm.From_Date)||'-'||To_Char(Tflm.To_Date) As Value From Tution_Fee_Limit_Master Tflm,Family_Details Fd Where Tflm.Status=1 And Tflm.Academic_Type=? And (Tflm.Type='Q1' or Tflm.Type='Q2' or Tflm.Type='Q3' or Tflm.Type='Q4') and fd.id in (select id from family_details where sfid=? and id=? and relation_id in(5,6)) order by Tflm.Id";
				query1= "Select Fd.Id as id,Tflm.Id As Key,Tflm.Academic_Type||'-'||Tflm.Type||'-'||(select get_tuitionfee_claim_period(Tflm.Type,'"+date+"','fromDate')  from dual)||'---'||(select get_tuitionfee_claim_period(Tflm.Type,'"+date+"','toDate') from dual) As Value From Tution_Fee_Limit_Master Tflm,Family_Details Fd Where Tflm.Status=1 and fd.status=1 And Tflm.Academic_Type=? And (Tflm.Type='Q1' or Tflm.Type='Q2' or Tflm.Type='Q3' or Tflm.Type='Q4') and fd.id in (select id from family_details where sfid=? and id=? and relation_id in(5,6)) order by Tflm.Id";
				keyList=session.createSQLQuery(query1).addScalar("id",Hibernate.INTEGER).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setString(0, acedemicType).setString(1, userSfid).setString(2, familyChildId).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
			}else if(CPSUtils.compareStrings(type, "Halfyearly")){
				 //query1= "Select Fd.Id as id,Tflm.Id As Key,Tflm.Academic_Type||'-'||Tflm.Type||'-'||To_Char(Tflm.From_Date)||'-'||To_Char(Tflm.To_Date) As Value From Tution_Fee_Limit_Master Tflm,Family_Details Fd Where Tflm.Status=1 And Tflm.Academic_Type=? And (Tflm.Type='H1' or Tflm.Type='H2') and fd.id in (select id from family_details where sfid=? and id=? and relation_id in(5,6)) order by Tflm.Id";
				 query1= "Select Fd.Id as id,Tflm.Id As Key,Tflm.Academic_Type||'-'||Tflm.Type||'-'||(select get_tuitionfee_claim_period(Tflm.Type,'"+date+"','fromDate')  from dual)||'---'||(select get_tuitionfee_claim_period(Tflm.Type,'"+date+"','toDate') from dual) As Value From Tution_Fee_Limit_Master Tflm,Family_Details Fd Where Tflm.Status=1 and fd.status=1 And Tflm.Academic_Type=? And (Tflm.Type='H1' or Tflm.Type='H2') and fd.id in (select id from family_details where sfid=? and id=? and relation_id in(5,6)) order by Tflm.Id";
				keyList=session.createSQLQuery(query1).addScalar("id",Hibernate.INTEGER).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setString(0, acedemicType).setString(1, userSfid).setString(2, familyChildId).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
			}else if(CPSUtils.compareStrings(type, "Annually")){
				 //query1= "Select Fd.Id as id,Tflm.Id As Key,Tflm.Academic_Type||'-'||Tflm.Type||'-'||To_Char(Tflm.From_Date)||'-'||To_Char(Tflm.To_Date) As Value From Tution_Fee_Limit_Master Tflm,Family_Details Fd Where Tflm.Status=1 And Tflm.Academic_Type=? And Tflm.Type='Annually' and fd.id in (select id from family_details where sfid=? and id=? and relation_id in(5,6)) order by Tflm.Id";
				query1= "Select Fd.Id as id,Tflm.Id As Key,Tflm.Academic_Type||'-'||Tflm.Type||'-'||(select get_tuitionfee_claim_period(Tflm.Type,'"+date+"','fromDate')  from dual)||'---'||(select get_tuitionfee_claim_period(Tflm.Type,'"+date+"','toDate') from dual) As Value From Tution_Fee_Limit_Master Tflm,Family_Details Fd Where Tflm.Status=1 and fd.status=1 And Tflm.Academic_Type=? And Tflm.Type='Annually' and fd.id in (select id from family_details where sfid=? and id=? and relation_id in(5,6)) order by Tflm.Id";
				keyList=session.createSQLQuery(query1).addScalar("id",Hibernate.INTEGER).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setString(0, acedemicType).setString(1, userSfid).setString(2, familyChildId).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
			}
		}
		}catch (Exception e) {
		e.printStackTrace();
		throw e;
	}finally{
		session.close();
	}
	return keyList;
}

/*//new code of requestform
@SuppressWarnings("unchecked")
public List<KeyValueDTO> setTutionFeeRequestDetails(String acedemicType,String type,String userSfid,String academicYear,String familyChildId) throws Exception{
	Session session=null;
	List<KeyValueDTO> keyList=null
	List<KeyValueDTO> keyList2=new ArrayList<KeyValueDTO>();
	//List<KeyValueDTO> keyList3=null;
    try{
		session=sessionFactory.openSession();
		//query for getting new list in new academic year
		String query2=" select creation_date from tution_fee_request_details where" +
				      " to_char(creation_date,'yyyy')=(select to_char(sysdate,'yyyy') from dual) and sfid='"+userSfid+"'";
		List yearDateList=session.createSQLQuery(query2).list();
		if(CPSUtils.compareStrings(type, "Quartarly")){
			String query= "Select Fd.Id as id,Tflm.Id As Key,Tflm.Academic_Type||'-'||Tflm.Type||'-'||To_Char(Tflm.From_Date)||'-'||To_Char(Tflm.To_Date) As Value From Tution_Fee_Limit_Master Tflm,Family_Details Fd Where Tflm.Status=1 And Tflm.Academic_Type=? And (Tflm.Type='Q1' or Tflm.Type='Q2' or Tflm.Type='Q3' or Tflm.Type='Q4') and fd.id in (select id from family_details where sfid=? and id=? and relation_id in(5,6)) order by Tflm.Id";
			keyList=session.createSQLQuery(query).addScalar("id",Hibernate.INTEGER).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setString(0, acedemicType).setString(1, userSfid).setString(2, familyChildId).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
		}else if(CPSUtils.compareStrings(type, "Halfyearly")){
			String query= "Select Fd.Id as id,Tflm.Id As Key,Tflm.Academic_Type||'-'||Tflm.Type||'-'||To_Char(Tflm.From_Date)||'-'||To_Char(Tflm.To_Date) As Value From Tution_Fee_Limit_Master Tflm,Family_Details Fd Where Tflm.Status=1 And Tflm.Academic_Type=? And (Tflm.Type='H1' or Tflm.Type='H2') and fd.id in (select id from family_details where sfid=? and id=? and relation_id in(5,6)) order by Tflm.Id";
			keyList=session.createSQLQuery(query).addScalar("id",Hibernate.INTEGER).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setString(0, acedemicType).setString(1, userSfid).setString(2, familyChildId).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
		}else if(CPSUtils.compareStrings(type, "Annually")){
			String query= "Select Fd.Id as id,Tflm.Id As Key,Tflm.Academic_Type||'-'||Tflm.Type||'-'||To_Char(Tflm.From_Date)||'-'||To_Char(Tflm.To_Date) As Value From Tution_Fee_Limit_Master Tflm,Family_Details Fd Where Tflm.Status=1 And Tflm.Academic_Type=? And Tflm.Type='Annually' and fd.id in (select id from family_details where sfid=? and id=? and relation_id in(5,6)) order by Tflm.Id";
			keyList=session.createSQLQuery(query).addScalar("id",Hibernate.INTEGER).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setString(0, acedemicType).setString(1, userSfid).setString(2, familyChildId).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
		}
		String query1="Select Distinct Limit_Id As key From Tution_Fee_Claim_Details where Child_Relation_Id=? And Status  In (2,8) and sfid=? and To_char(From_Date,'yyyy')='"+academicYear+"'";
	    List<KeyValueDTO> tutionFeeClaimList  = session.createSQLQuery(query1).addScalar("key", Hibernate.STRING).setString(0, familyChildId).setString(1, userSfid).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
		if(tutionFeeClaimList.size()>0){
	    for(int i=0;i<tutionFeeClaimList.size();i++){
	    	String key=tutionFeeClaimList.get(i).getKey();
	    	System.out.println(key);
	    	for(int j=0;j<keyList.size();j++){
	    		if(key.equals(keyList.get(j).getKey())){
	    		keyList.remove(j);
	    		}
	    	}
	    }
		}
		
		if(yearDateList.size()==0){
		  return keyList;
		}	
	   else{	   
		   return keyList2;
	   }
		
	}catch (Exception e) {
		
		e.printStackTrace();
		throw e;
	}finally{
		//session.close();
	}
	return keyList;
}*/
public String getIpAddress(String requestId) throws Exception{
	Session session=null;
	TutionFeeRequestDetailsDTO tutionFeeRequestDetails=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		tutionFeeRequestDetails= (TutionFeeRequestDetailsDTO) session.createCriteria(TutionFeeRequestDetailsDTO.class).add(Expression.eq("requestID", requestId)).uniqueResult();
		
	}catch (Exception e) {
		throw e;
	}finally{
		//session.close();
	}
	return tutionFeeRequestDetails.getIpAddress();
}
public String checkTutionFeeClaimDetails(TutionFeeBean tutionFeeBean) throws Exception{
	Session session=null;
	String message=null;
	try{
		Criteria crit=null;
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		crit=session.createCriteria(TutionFeeClaimMasterDTO.class).add(Expression.eq("status", 1)).add(Expression.ilike("claimName", tutionFeeBean.getClaimName().trim()));
		if(CPSUtils.checkList(crit.list())){
			message=CPSConstants.DUPLICATE;
		}
	}catch (Exception e) {
		throw e;
	}finally{
		//session.close();
	}
	return message;
}
public String checkingTutionFeeAcademicYear(TutionFeeBean tutionFeeBean)throws Exception{
	Session session=null;
	String message=null;
	try{
		Criteria crit=null;
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		crit=session.createCriteria(TuitionFeeAcedemicYearDTO.class).add(Expression.eq("status", 1)).add(Expression.ilike("className", tutionFeeBean.getClassName().trim()));
	if(CPSUtils.checkList(crit.list())){
		message=CPSConstants.DUPLICATE;
	}
	}catch (Exception e) {
		throw e;
	}finally{
		//session.close();
	}
	return message;
}
// checking the tutionfee applicants details before applying
@SuppressWarnings("unchecked")
public List<TutionFeeRequestDetailsDTO> checkingTuitionFeeAppliedDetails(TutionFeeBean tutionFeeBean) throws Exception{
	Session session=null;
	List<TutionFeeRequestDetailsDTO> tuitionFeeApplicantDetails = null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		tuitionFeeApplicantDetails=session.createCriteria(TutionFeeRequestDetailsDTO.class).add(Expression.eq("claimTypeId", 1)).add(Expression.eq("sfid",tutionFeeBean.getSfid() )).add(Expression.ne("status", 9)).addOrder(Order.desc("requestID")).list();
		for (TutionFeeRequestDetailsDTO tutionFeeRequestDetailsDTO : tuitionFeeApplicantDetails) {
			tutionFeeRequestDetailsDTO.setHistoryID((String)session.createQuery("select to_char(max(id)) from RequestCommonBean where requestID=?").setString(0, tutionFeeRequestDetailsDTO.getRequestID()).uniqueResult());
		}
	}catch (Exception e) {
		throw e;
	}finally{
		//session.close();
	}
	return tuitionFeeApplicantDetails;
}
@SuppressWarnings("unchecked")
public List<TutionFeeRequestDetailsDTO> telephoneBillAplliedDetails(TutionFeeBean tutionFeeBean) throws Exception{
	Session session=null;
	List<TutionFeeRequestDetailsDTO> telephoneBillAplliedDetails=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		telephoneBillAplliedDetails=session.createCriteria(TutionFeeRequestDetailsDTO.class).add(Expression.eq("claimTypeId", 2)).add(Expression.eq("sfid", tutionFeeBean.getSfid())).add(Expression.ne("status", 9)).addOrder(Order.desc("requestID")).list();
	for (TutionFeeRequestDetailsDTO tutionFeeRequestDetailsDTO : telephoneBillAplliedDetails) {
		tutionFeeRequestDetailsDTO.setHistoryID((String)session.createQuery("select to_char(max(id)) from RequestCommonBean where requestID=?").setString(0, tutionFeeRequestDetailsDTO.getRequestID()).uniqueResult());
	}
	}catch (Exception e) {
		throw e;
	}finally{
		//session.close();
	}
	return telephoneBillAplliedDetails;
}
//year details 
public List<PayFinYearDTO> getYearDetails(TutionFeeBean tutionFeeBean) throws Exception {
	Session session = null;
	List<PayFinYearDTO>  yearDetails =new ArrayList<PayFinYearDTO>();
	List<PayFinYearDTO>  tempYearDetails =new ArrayList<PayFinYearDTO>();
	Map<Integer, List<PayFinYearDTO>> tutionFeeYearList =new TreeMap<Integer, List<PayFinYearDTO>>();
	try {
		session = hibernateUtils.getSession();
		int childId=0;
		for(int k=0;k<tutionFeeBean.getFamilyList().size();k++){
			if(!CPSUtils.isNullOrEmpty(tutionFeeBean.getFamilyList().get(k))){
				childId=Integer.parseInt(tutionFeeBean.getFamilyList().get(k).getId());
			}
			tempYearDetails=getYearList(childId,tutionFeeBean);
			tutionFeeYearList.put(childId, tempYearDetails);
		}
		tutionFeeBean.setMapYearList(tutionFeeYearList);
	} catch (Exception e) {
		throw e;
	} finally {
		//session.close();
	}
	return yearDetails;
}
@SuppressWarnings("unchecked")
public List<PayFinYearDTO> getYearList(int childId,TutionFeeBean tutionFeeBean)throws Exception{
	Session session = null;
	List<PayFinYearDTO> yearTypeDetailsList = null;
	List<PayFinYearDTO>  yearDetails =new ArrayList<PayFinYearDTO>();
	List<PayFinYearDTO>  tempYearDetails =new ArrayList<PayFinYearDTO>();
	try{
		session=hibernateUtils.getSession();
		yearTypeDetailsList = session.createCriteria(PayFinYearDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.desc("id")).list();
	    for(int i = 0; i <2; i++)
	    {
	    	PayFinYearDTO dto = yearTypeDetailsList.get(i);
	    	dto.setFyToFrom(dto.getFyFrom() + "-" + String.valueOf(dto.getFyTo()).substring(2));
	    	yearDetails.add(dto);
	    }
		  String query ="select distinct pfym.id as id,tfcd.child_relation_id as fyFrom from financial_year_master pfym,tution_fee_claim_details tfcd,tution_fee_limit_master tflm where  to_char(pfym.from_date,'yyyy') in(select distinct to_char(tfcd.from_date,'yyyy') fromdate from tution_fee_claim_details where sfid =? and " +
		  " child_relation_id ='"+childId+"' and status in(2,8)) and to_char(tfcd.from_date,'yyyy')=to_char(pfym.from_date,'yyyy') and tfcd.sfid=? and child_relation_id ='"+childId+"' and tfcd.status in(2,8) and tflm.id  in(4,6,7,11,13,14) and tflm.id=tfcd.limit_id order by pfym.id desc";
		  List<PayFinYearDTO> removingYearList=session.createSQLQuery(query).addScalar("id", Hibernate.INTEGER).addScalar("fyFrom", Hibernate.INTEGER).setString(0, tutionFeeBean.getSfid()).setString(1, tutionFeeBean.getSfid()).setResultTransformer(Transformers.aliasToBean(PayFinYearDTO.class)).list();

		  for(int i=0;i<yearDetails.size();i++){
			  boolean flag=true;
			  int id=yearDetails.get(i).getId();
			 for(int j=0;j<removingYearList.size();j++){
				 if(id==(removingYearList.get(j).getId())){
					 flag=false;
						}
			 }
			 if(flag){
			 tempYearDetails.add(yearDetails.get(i));
			 }
		  }
	}catch (Exception e) {
		throw e;
	}
	return tempYearDetails;
}
//code for check limit master
public String checkTuitionFeeLimitMaster(TutionFeeBean tutionFeeBean) throws Exception{
	Session session = null;
	String message = null;
	try{
		Criteria crit = null;
		session=hibernateUtils.getSession();
		if(tutionFeeBean.getTypeQuartarly()!=null){
			if(tutionFeeBean.getTypeQuartarly().equals("Q1" )|| tutionFeeBean.getTypeQuartarly().equals("Q2") || tutionFeeBean.getTypeQuartarly().equals("Q3" )|| tutionFeeBean.getTypeQuartarly().equals("Q4")){
				crit=session.createCriteria(TutionFeeLimitMasterDTO.class).add(Expression.eq("status", 1)).add(Expression.ilike("academicType", tutionFeeBean.getAcademicType().trim())).add(Expression.eq("type", tutionFeeBean.getTypeQuartarly().trim())).add(Expression.eq("limit", tutionFeeBean.getLimit()));
			}
		}
		if(tutionFeeBean.getTypeHalfyearly()!=null){
			if(tutionFeeBean.getTypeHalfyearly().equals("H1") || tutionFeeBean.getTypeHalfyearly().equals("H2")){
				crit=session.createCriteria(TutionFeeLimitMasterDTO.class).add(Expression.eq("status", 1)).add(Expression.ilike("academicType", tutionFeeBean.getAcademicType().trim())).add(Expression.eq("type", tutionFeeBean.getTypeHalfyearly().trim())).add(Expression.eq("limit", tutionFeeBean.getLimit()));
			}
		}
	    if(tutionFeeBean.getType()!=null || tutionFeeBean.getType()!=""){
	    	if(tutionFeeBean.getType().equals("Annually")){
				crit=session.createCriteria(TutionFeeLimitMasterDTO.class).add(Expression.eq("status", 1)).add(Expression.ilike("academicType", tutionFeeBean.getAcademicType().trim())).add(Expression.eq("type", tutionFeeBean.getType().trim())).add(Expression.eq("limit", tutionFeeBean.getLimit()));
			}
	    }
	     if(crit.list().size()>=1){
			message=CPSConstants.DUPLICATE;
		}else{
			message=CPSConstants.SUCCESS;
		}
	}catch (Exception e) {
		throw e;
	}
	return message;
}
}
