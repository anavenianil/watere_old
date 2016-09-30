package com.callippus.web.dao.telephone;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.print.DocFlavor.STRING;

import net.sf.cglib.transform.impl.AddStaticInitTransformer;

import org.apache.poi.hssf.record.formula.functions.T;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.loader.custom.Return;
import org.hibernate.transform.Transformers;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.TaskHolderDesignationsDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.telephone.TelePhoneBillBean;
import com.callippus.web.beans.telephone.TelephoneBillClaimDetailsDTO;
import com.callippus.web.beans.telephone.TelephoneBillEligibleDTO;
import com.callippus.web.beans.telephone.TelephoneCashAssignmentDTO;
import com.callippus.web.beans.telephone.TelephoneDesigEligibilityDetailsDTO;
import com.callippus.web.beans.telephone.TelephoneEmpEligibiltyDetailsDTO;
import com.callippus.web.beans.tutionFee.TuitionFeeAcedemicYearDTO;
import com.callippus.web.beans.tutionFee.TutionFeeBean;
import com.callippus.web.beans.tutionFee.TutionFeeClaimDetailsDTO;
import com.callippus.web.beans.tutionFee.TutionFeeClaimMasterDTO;
import com.callippus.web.beans.tutionFee.TutionFeeLimitMasterDTO;
import com.callippus.web.beans.tutionFee.TutionFeeRequestDetailsDTO;
import com.callippus.web.beans.workflowmapping.WorkFlowMappingBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.leave.beans.admin.LeaveAdministratorBean;
@Service
 public class SQLTelePhoneBillDAO implements ITelePhoneBillDAO{
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private HibernateUtils hibernateUtils;
	@SuppressWarnings("unchecked")
	public List<DesignationDTO> getTelephoneDesignationDeSelectedList() throws Exception{
		List<DesignationDTO> telephoneDesignationDeSelectedList = null;
		Session session = null;
		try{
			session=hibernateUtils.getSession(); ////session=sessionFactory.openSession();
			//designationList = session.createCriteria(DesignationDTO.class).add(Expression.eq("status", 1)).list();
			String query ="Select Des.id as desigID,Des.name as designation From Designation_master Des Where Des.Status=1 And " +
					      "Des.id not in (select designation_id from tele_desig_eligibile_details where status=1) order by desigId";
			telephoneDesignationDeSelectedList =session.createSQLQuery(query).addScalar("desigID", Hibernate.STRING).addScalar("designation", Hibernate.STRING)
			                 .setResultTransformer(Transformers.aliasToBean(DesignationDTO.class)).list();
		}catch (Exception e) {
			throw e;
		}finally{
			//session.close();
		}
		return telephoneDesignationDeSelectedList;
	}
	@SuppressWarnings("unchecked")
	public List<DesignationDTO> getTelephoneDesignationSelectedList(TelePhoneBillBean telePhoneBillBean)throws Exception{
		Session session=null;
		List<DesignationDTO> telephoneDesignationSelectedList = null;
		try{
			session=hibernateUtils.getSession();  //session=sessionFactory.openSession();
			String query =" Select Des.id as desigID,Des.name as designation From Designation_master Des Where Des.Status=1 And " +
				  	      " Des.id  in("+telePhoneBillBean.getEditDesignations()+")";
			telephoneDesignationSelectedList =session.createSQLQuery(query).addScalar("desigID", Hibernate.STRING).addScalar("designation", Hibernate.STRING)
							       .setResultTransformer(Transformers.aliasToBean(DesignationDTO.class)).list();
		}catch (Exception e) {
			throw e;
		}finally{
			//session.close();
		}
		return telephoneDesignationSelectedList;
	}
	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> getTelephoneSelectedEmployeeList()throws Exception{
		Session session=null;
		List<KeyValueDTO> teleSelectedEmployeeList = null;
		try{
			session=hibernateUtils.getSession();  //session=sessionFactory.openSession();
			//teleSelectedEmployeeList=session.createCriteria(TelephoneDesigEligibilityDetailsDTO.class).add(Expression.eq("applicableEmployee", 1)).list();
		   String query =" Select Distinct Teldes.Designation_id As key,Des.Name As value From Tele_desig_eligibile_details Teldes,Designation_master Des " +
		   		         " where des.id=teldes.designation_id and Teldes.applicable_employee=0 and Teldes.status=1 and Des.status=1 order by Teldes.Designation_Id" ;
		   teleSelectedEmployeeList = session.createSQLQuery(query).addScalar("key", Hibernate.STRING).addScalar("Value", Hibernate.STRING)
		       .setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
		}catch (Exception e) {
			throw e;
		}finally{
			//session.close();
		}
		return teleSelectedEmployeeList;
	}
	/*@SuppressWarnings("unchecked")
	public List<KeyValueDTO> getSelectedEmployeeList(String designationId) throws Exception{
		List<KeyValueDTO> empList = null;
		Session session = null;
		try{
			session = sessionFactory.openSession();
			String query = " Select Sfid||'('||name_in_service_book||')' as name,sfid as value From Emp_master Where "+
                          " Designation_id="+Integer.parseInt(designationId)+" and status=1 And Sfid  In "+
                          " (Select Sfid From Telephone_eligibility_details) order by sfid";
			empList = session.createSQLQuery(query).addScalar("name",Hibernate.STRING).addScalar("value",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
		}catch (Exception e) {
			throw e;
		}finally{
			session.close();
		}
		return empList;
	}
   @SuppressWarnings("unchecked")
	public List<KeyValueDTO> getNotSelectedEmployeeList(String designationId) throws Exception{
		List<KeyValueDTO> empList= null;
		Session session = null;
		try{
			session=sessionFactory.openSession();
			String query = " Select emp.Sfid ||'('||emp.name_in_service_book ||')' as name,Value From " +
					      "((Select Em.Sfid As Value From Emp_master Em Where Em.Status=1) Minus "+
                          " (select sfid from telephone_eligibility_details)) tab,emp_master emp " +
                          " where tab.value=emp.sfid and emp.designation_id="+Integer.parseInt(designationId)+" order by emp.sfid";
            empList = session.createSQLQuery(query).addScalar("name",Hibernate.STRING).addScalar("value",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
         }catch (Exception e) {
			throw e;
		}finally{
			session.close();
		}
		return empList;
	}*/
    public String submitTelephoneElibilityDetails(TelePhoneBillBean telePhoneBillBean)throws Exception{
		String message = "";
		Session session = null;
		//Transaction tx = null;
		try{
			session=hibernateUtils.getSession();    //session=sessionFactory.openSession();
			//tx=session.beginTransaction();
			
			String[] sfidList=telePhoneBillBean.getPk().split(",");
			session.createSQLQuery("delete from Telephone_eligibility_details where designation_id="+Integer.parseInt(telePhoneBillBean.getDesignationId())).executeUpdate();
				for(int i = 0;i < sfidList.length;i++){
					String sfid = sfidList[i];
					TelephoneBillEligibleDTO telephoneBillEligibleDTO = new TelephoneBillEligibleDTO();
					telephoneBillEligibleDTO.setSfid(sfid);
					telephoneBillEligibleDTO.setDesignationId(Integer.parseInt(telePhoneBillBean.getDesignationId()));
					telephoneBillEligibleDTO.setAmount(Integer.parseInt(telePhoneBillBean.getAmount()));
					telephoneBillEligibleDTO.setServiceTax(Float.parseFloat(telePhoneBillBean.getServiceTax()));
					telephoneBillEligibleDTO.setTotAmount(Math.round(Float.parseFloat(telePhoneBillBean.getTotAmount())));
					telephoneBillEligibleDTO.setInternetFlag(Integer.parseInt(telePhoneBillBean.getInternetFlag()));
					session.saveOrUpdate(telephoneBillEligibleDTO);
				}
				//tx.commit();
				message = CPSConstants.SUCCESS;
		}catch (Exception e) {
			//tx.rollback();
			throw e;
		}finally{
			//session.close();
		}
		return message;
	}
  /*public String submitTelephoneDesignationEligibileDetails(TelePhoneBillBean telePhoneBillBean)throws Exception{
    	String message = "";
    	Session session = null;
    	//Transaction tx = null;
    	TelephoneDesigEligibilityDetailsDTO telephoneDesigEligibilityDetailsDTO =null;
    	TelephoneDesigEligibilityDetailsDTO telephoneDesigEligibilityDetailsDTO1 =null;
    	try{
    		session=hibernateUtils.getSession();  //session=sessionFactory.openSession();
			//tx=session.beginTransaction();
			
			String[] desigList=telePhoneBillBean.getDesignationId().split(",");
			for(int i = 0;i < desigList.length;i++){
				String designationList = desigList[i];
				 if(Float.parseFloat(telePhoneBillBean.getServiceTax())!= 0){
				telephoneDesigEligibilityDetailsDTO = (TelephoneDesigEligibilityDetailsDTO) session.createCriteria(TelephoneDesigEligibilityDetailsDTO.class).add(Expression.eq("designationId", Integer.parseInt(designationList))).add(Expression.eq("internetFlag", 1)).uniqueResult();
				if(CPSUtils.isNull(telephoneDesigEligibilityDetailsDTO)){
					telephoneDesigEligibilityDetailsDTO = new TelephoneDesigEligibilityDetailsDTO();
				}
				//for Eligible Amount With   Internet with flag 1
				telephoneDesigEligibilityDetailsDTO.setDesignationId(Integer.parseInt(designationList));
				telephoneDesigEligibilityDetailsDTO.setAmount(Integer.parseInt(telePhoneBillBean.getAmount()));
				telephoneDesigEligibilityDetailsDTO.setServiceTax(Float.parseFloat(telePhoneBillBean.getServiceTax()));
				telephoneDesigEligibilityDetailsDTO.setTotAmount(Math.round(Float.parseFloat(telePhoneBillBean.getTotAmount())));
				telephoneDesigEligibilityDetailsDTO.setApplicableEmployee(Integer.parseInt(telePhoneBillBean.getApplicableEmployee()));
				telephoneDesigEligibilityDetailsDTO.setStatus("1");
				telephoneDesigEligibilityDetailsDTO.setInternetFlag(1);
			    session.saveOrUpdate(telephoneDesigEligibilityDetailsDTO);
				 }
			  //for Eligible Amount With Out Internet with flag 2
			   if(Float.parseFloat(telePhoneBillBean.getServiceTax1())!= 0){
				   telephoneDesigEligibilityDetailsDTO1 = (TelephoneDesigEligibilityDetailsDTO) session.createCriteria(TelephoneDesigEligibilityDetailsDTO.class).add(Expression.eq("designationId", Integer.parseInt(designationList))).add(Expression.eq("internetFlag", 2)).uniqueResult();
				   if(CPSUtils.isNull(telephoneDesigEligibilityDetailsDTO1)){
					   telephoneDesigEligibilityDetailsDTO1 = new TelephoneDesigEligibilityDetailsDTO(); 
				   }
				telephoneDesigEligibilityDetailsDTO1.setDesignationId(Integer.parseInt(designationList));
			    telephoneDesigEligibilityDetailsDTO1.setAmount(Integer.parseInt(telePhoneBillBean.getAmount1()));
			    telephoneDesigEligibilityDetailsDTO1.setServiceTax(Float.parseFloat(telePhoneBillBean.getServiceTax1()));
			    telephoneDesigEligibilityDetailsDTO1.setTotAmount(Math.round(Float.parseFloat((telePhoneBillBean.getTotAmount1()))));
			    telephoneDesigEligibilityDetailsDTO1.setApplicableEmployee(Integer.parseInt(telePhoneBillBean.getApplicableEmployee()));
			    telephoneDesigEligibilityDetailsDTO1.setStatus("1");
			    telephoneDesigEligibilityDetailsDTO1.setInternetFlag(2);
			    session.saveOrUpdate(telephoneDesigEligibilityDetailsDTO1);
			   }
			}
			//tx.commit();
			message=CPSConstants.SUCCESS;
			}catch (Exception e) {
    		//tx.rollback();
			throw e;
		}finally{
			//session.close();
		}
		return message;
    }*/
    // new code for telephone master
    @SuppressWarnings("unchecked")
	public String submitTelephoneDesignationEligibileDetails(TelePhoneBillBean telePhoneBillBean)throws Exception{
    	String message = "";
    	Session session = null;
    	//Transaction tx = null;
    	TelephoneDesigEligibilityDetailsDTO telephoneDesigEligibilityDetailsDTO =null;
    	try{
    		session=hibernateUtils.getSession();  //session=sessionFactory.openSession();
			//tx=session.beginTransaction();
			String[] desigList=telePhoneBillBean.getDesignationId().split(",");
    		for(int i = 0;i < desigList.length;i++){
				String designationList = desigList[i];
				//deselected designation status updated to 100 
				List deselectDesigList=session.createSQLQuery("select designation_id from tele_desig_eligibile_details where amount_withinternet="+telePhoneBillBean.getAmountWithInternet()+" and amount_without_internet="+telePhoneBillBean.getAmountWithoutInternet()+" and applicable_employee="+telePhoneBillBean.getApplicableEmployee()+" and status=1 and designation_id not in("+telePhoneBillBean.getDesignationId()+")").list();
			    if(deselectDesigList.size()!=0){
			    	session.createSQLQuery("update tele_desig_eligibile_details set status =100 where designation_id in("+deselectDesigList.get(i)+")").executeUpdate();
			    }
			    if(telePhoneBillBean.getServiceTaxWithInternet()!= 0 || telePhoneBillBean.getServiceTaxWithoutInternet()!=0){
				telephoneDesigEligibilityDetailsDTO = (TelephoneDesigEligibilityDetailsDTO) session.createCriteria(TelephoneDesigEligibilityDetailsDTO.class).add(Expression.eq("designationId",Integer.parseInt(designationList))).add(Expression.eq("status", "1")).uniqueResult();
				if(CPSUtils.isNullOrEmpty(telephoneDesigEligibilityDetailsDTO)){
					telephoneDesigEligibilityDetailsDTO = new TelephoneDesigEligibilityDetailsDTO();
				}
				telephoneDesigEligibilityDetailsDTO.setDesignationId(Integer.parseInt(designationList));
				telephoneDesigEligibilityDetailsDTO.setAmountWithInternet(telePhoneBillBean.getAmountWithInternet());
				telephoneDesigEligibilityDetailsDTO.setServiceTaxWithInternet(telePhoneBillBean.getServiceTaxWithInternet());
				telephoneDesigEligibilityDetailsDTO.setTotAmountWithInternet(Math.round(telePhoneBillBean.getTotAmountWithInternet()));
				
				telephoneDesigEligibilityDetailsDTO.setAmountWithoutInternet(telePhoneBillBean.getAmountWithoutInternet());
				telephoneDesigEligibilityDetailsDTO.setServiceTaxWithoutInternet(telePhoneBillBean.getServiceTaxWithoutInternet());
				telephoneDesigEligibilityDetailsDTO.setTotAmountWithoutInternet(Math.round((telePhoneBillBean.getTotAmountWithoutInternet())));
				
				telephoneDesigEligibilityDetailsDTO.setApplicableEmployee(Integer.parseInt(telePhoneBillBean.getApplicableEmployee()));
				telephoneDesigEligibilityDetailsDTO.setStatus("1");
				telephoneDesigEligibilityDetailsDTO.setCreatedBy(telePhoneBillBean.getSfid());
				telephoneDesigEligibilityDetailsDTO.setLastModifiedBy(telePhoneBillBean.getSfid());
				telephoneDesigEligibilityDetailsDTO.setLastModifiedDate(CPSUtils.getCurrentDateWithTime());
				telephoneDesigEligibilityDetailsDTO.setCreationDate(CPSUtils.getCurrentDateWithTime());
			    session.saveOrUpdate(telephoneDesigEligibilityDetailsDTO);
				
			    }
			}
			session.flush();
			if(telePhoneBillBean.getApplicableEmployee().equals("0")){
		    List teleStatusList=session.createSQLQuery("select designation_id from tele_desig_eligibile_details where status=100").list();
		     if(teleStatusList.size()!=0){
			   for(int m=0;m<teleStatusList.size();m++){
					int teleStatusDesigId=((BigDecimal)teleStatusList.get(m)).intValue();
				   session.createSQLQuery("delete from tele_emp_eligibilty_details where designation_id="+teleStatusDesigId+"").executeUpdate();
				}  
		     }
		   
		    /*List empDesigList=session.createSQLQuery("select designation_id from tele_desig_eligibile_details where amount_withinternet="+telePhoneBillBean.getAmountWithInternet()+" and amount_without_internet="+telePhoneBillBean.getAmountWithoutInternet()+" and applicable_employee="+telePhoneBillBean.getApplicableEmployee()+" and status=1").list();
			for(int l=0;l<empDesigList.size();l++){
				int empDesig=((BigDecimal)empDesigList.get(l)).intValue();
				session.createSQLQuery("delete from tele_emp_eligibilty_details where designation_id="+empDesig+"").executeUpdate();
			}*/
			for(int k=0;k<desigList.length;k++){
				String designationList = desigList[k];
				if(telePhoneBillBean.getApplicableEmployee().equals("1")){
				    List appEmpList=session.createSQLQuery("select em.sfid as sfid from emp_master em,designation_master dm where em.designation_id="+Integer.parseInt(designationList)+" and dm.id=em.designation_id and em.working_location in (select working_location from emp_master where sfid='"+telePhoneBillBean.getSfid()+"' and status=1) and em.status=1 and dm.status=1 order by em.sfid").list();
				    for(int j = 0;j < appEmpList.size();j++){
						String sfid = (String)appEmpList.get(j);
						TelephoneEmpEligibiltyDetailsDTO telephoneEmpEligibiltyDetailsDTO = new TelephoneEmpEligibiltyDetailsDTO();
						telephoneEmpEligibiltyDetailsDTO.setDesignationId(Integer.parseInt(designationList));
						telephoneEmpEligibiltyDetailsDTO.setSfid(sfid);
						telephoneEmpEligibiltyDetailsDTO.setStatus(1);
					    session.saveOrUpdate(telephoneEmpEligibiltyDetailsDTO);
					}
					}
			}
			}
			session.flush();
			//tx.commit();
			message=CPSConstants.SUCCESS;
			}catch (Exception e) {
    		//tx.rollback();
			throw e;
		}finally{
			//session.close();
		}
		return message;
    }
    // commenting old working code before 25102013
  /*@SuppressWarnings("unchecked")
	public List<TelephoneDesigEligibilityDetailsDTO>  getTelephoneDesigEligibilityDetails(TelePhoneBillBean telePhoneBillBean)throws Exception{
    	Session session = null;
    	List<TelephoneDesigEligibilityDetailsDTO>  telephoneDesigEligibilityList = null;
    	try{
    		session=hibernateUtils.getSession(); //session=sessionFactory.openSession(); 
    		String query =" Select (Rtrim ( Xmlagg (Xmlelement (E, Dm.Id|| ',')).Extract ('//text()'), ',')) As designationsIds, (Rtrim ( Xmlagg (Xmlelement (E, Dm.Name|| ',')).Extract ('//text()'), ',')) as designations," +
    				      " Tded.Service_Tax As serviceTax,Tded.Amount As amount,Tded.Total_Amount as totAmount,Tded.Internet_Flag as internetFlag,Tded.applicable_employee as applicableEmployee From Designation_Master Dm," +
    				      " Tele_Desig_Eligibile_Details Tded Where Dm.Id=Tded.Designation_Id and tded.status=1 Group By Tded.Service_Tax, Tded.Amount,Tded.Total_Amount,Tded.Internet_Flag,Tded.applicable_employee";
    		telephoneDesigEligibilityList=session.createSQLQuery(query).addScalar("designationsIds", Hibernate.STRING).addScalar("designations", Hibernate.STRING).addScalar("serviceTax", Hibernate.FLOAT)
    		                              .addScalar("amount", Hibernate.INTEGER).addScalar("totAmount", Hibernate.INTEGER).addScalar("internetFlag", Hibernate.INTEGER).addScalar("applicableEmployee", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(TelephoneDesigEligibilityDetailsDTO.class)).list();
    	}catch (Exception e) {
			throw e;
		}finally{
			//session.close();
		}
    	return telephoneDesigEligibilityList;
    	
    }*/
    //present code on 28102013
    @SuppressWarnings("unchecked")
	public List<TelephoneDesigEligibilityDetailsDTO>  getTelephoneDesigEligibilityDetails(TelePhoneBillBean telePhoneBillBean)throws Exception{
    	Session session = null;
    	List<TelephoneDesigEligibilityDetailsDTO>  telephoneDesigEligibilityList = null;
    	try{
    		session=hibernateUtils.getSession(); //session=sessionFactory.openSession(); 
    		String query =" Select (Rtrim ( Xmlagg (Xmlelement (E, Dm.Id|| ',')).Extract ('//text()'), ',')) As designationsIds, (Rtrim ( Xmlagg (Xmlelement (E, Dm.Name|| ',')).Extract ('//text()'), ',')) as designations," +
    				      " Tded.amount_Withinternet As amountWithInternet,Tded.service_tax_withinternet As serviceTaxWithInternet,Tded.Total_Amount_withinternet as totAmountWithInternet, " +
    				      " Tded.amount_Without_internet As amountWithoutInternet,Tded.service_tax_without_internet As serviceTaxWithoutInternet,Tded.Total_Amount_without_internet as totAmountWithoutInternet," +
    				      " Tded.applicable_employee as applicableEmployee From Designation_Master Dm," +
    				      " Tele_Desig_Eligibile_Details Tded Where Dm.Id=Tded.Designation_Id and tded.status=1 and Dm.status=1 Group By Tded.amount_Withinternet, Tded.service_tax_withinternet,Tded.Total_Amount_withinternet," +
    				      " Tded.amount_Without_internet,Tded.service_tax_without_internet,Tded.Total_Amount_without_internet ,Tded.applicable_employee";
    		telephoneDesigEligibilityList=session.createSQLQuery(query).addScalar("designationsIds", Hibernate.STRING).addScalar("designations", Hibernate.STRING).addScalar("serviceTaxWithInternet", Hibernate.FLOAT)
    		                              .addScalar("amountWithInternet", Hibernate.INTEGER).addScalar("totAmountWithInternet", Hibernate.INTEGER).addScalar("amountWithoutInternet", Hibernate.INTEGER).addScalar("serviceTaxWithoutInternet", Hibernate.FLOAT).addScalar("totAmountWithoutInternet", Hibernate.INTEGER).
    		                              addScalar("applicableEmployee", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(TelephoneDesigEligibilityDetailsDTO.class)).list();
    	}catch (Exception e) {
			throw e;
		}finally{
			//session.close();
		}
    	return telephoneDesigEligibilityList;
    	
    }
   /*public TelePhoneBillBean getTeleBillConfDetails(TelePhoneBillBean telePhoneBillBean)throws Exception{
		Session session = null;
		TelephoneBillEligibleDTO telephoneBillEligibleDTO = null;
		try{
			session=sessionFactory.openSession();
			telephoneBillEligibleDTO = (TelephoneBillEligibleDTO)session.createSQLQuery(" Select unique Amount as amount,Internet_flag as internetFlag,Service_tax as serviceTax" +
					               ",Total_amount as totAmount from telephone_eligibility_details where" +
                                    " designation_id="+Integer.parseInt(telePhoneBillBean.getDesignationId())).
                                     addScalar("amount",Hibernate.INTEGER).addScalar("internetFlag", Hibernate.INTEGER).
                                     addScalar("serviceTax", Hibernate.FLOAT).addScalar("totAmount", Hibernate.INTEGER).
                                     setResultTransformer(Transformers.aliasToBean(TelephoneBillEligibleDTO.class)).uniqueResult();
			if(!CPSUtils.isNullOrEmpty(telephoneBillEligibleDTO)){
			telePhoneBillBean.setAmount(String.valueOf(telephoneBillEligibleDTO.getAmount()));
			telePhoneBillBean.setServiceTax(String.valueOf(telephoneBillEligibleDTO.getServiceTax()));
			telePhoneBillBean.setTotAmount(String.valueOf(telephoneBillEligibleDTO.getTotAmount()));
			telePhoneBillBean.setInternetFlag(String.valueOf(telephoneBillEligibleDTO.getInternetFlag()));
			}
		}catch (Exception e) {
			throw e;
		}finally{
			session.close();
		}
		return telePhoneBillBean;
	}*/
	public TelePhoneBillBean getEmpDetails(TelePhoneBillBean telePhoneBillBean)throws Exception{
		Session session = null;
		EmployeeBean employeeBean = null;
		try{
			session=hibernateUtils.getSession();   //session=sessionFactory.openSession();
			employeeBean = (EmployeeBean)session.createCriteria(EmployeeBean.class).add(Expression.eq("userSfid", telePhoneBillBean.getSfid())).uniqueResult();
			String query = "select unique dm.department_name as deptName from emp_master em,departments_master dm,org_role_instance ori where dm.department_id=(select ori.department_id from org_role_instance ori where ori.org_role_id=(select em.office_id from emp_master em where em.sfid=?))";
			String designation = (String)session.createSQLQuery(query).setString(0, employeeBean.getUserSfid().toUpperCase()).uniqueResult();
			
			employeeBean.setDivisionName(designation);
			Object accountNumber = session.createSQLQuery("select AC_NUMBER from emp_payment_details where sfid='"+telePhoneBillBean.getSfid()+"'").uniqueResult();
			String accNum=(String)accountNumber;
		    employeeBean.setAccountNo(accNum);
		    telePhoneBillBean.setEmployeeBean(employeeBean);
		    
		}catch (Exception e) {
			throw e;
		}finally{
			//session.close();
		}
		return telePhoneBillBean; 
	}
	@SuppressWarnings("unchecked")
	public List<TutionFeeClaimMasterDTO> getTelephoneBillClaimMasterDetails(TelePhoneBillBean telePhoneBillBean)throws Exception{
	    Session session = null;
	    List<TutionFeeClaimMasterDTO> telephoneBillClaimMasterList = null;
		try{
			session=hibernateUtils.getSession();  //session=sessionFactory.openSession();
			//telephoneBillClaimMasterList = session.createCriteria(TutionFeeClaimMasterDTO.class).add(Expression.eq("status", 1)).add(Expression.eq("claimTypeId", 2)).addOrder(Order.desc("id")).list();
		    
			/*String query1="(Select Distinct Claim.Claim_name as name,Claim.Id as claimId,Tele.Telephone_no as telephoneNo,tele.bill_no as billNo From Telephone_bill_claim_details Tele,Claim_master Claim Where Tele.Claim_id=Claim.Id And Tele.Sfid='"+telePhoneBillBean.getSfid()+"' And Claim.Id Not In " +
					"(Select Distinct Cm.Id As Claimid From Telephone_bill_claim_details Tbcd,Claim_master Cm Where Tbcd.Sfid ='"+telePhoneBillBean.getSfid()+"' And Tbcd.Claim_id  =Cm.Id And Cm.Status=1 And Cm.Claim_type_id=2 And Tbcd.Status In (2,8,9) And Tbcd.Request_id In " +
					"(Select Max(Request_id) From Telephone_bill_claim_details Where Sfid='"+telePhoneBillBean.getSfid()+"') Group By Cm.Claim_name,Cm.Id,Tbcd.Telephone_no,tbcd.bill_no Having tbcd.bill_no Is Not Null ) And  Tele.Telephone_no Is Null)" +
					"Union (Select Cm.Claim_name As Name,Cm.Id As Claimid,Tbcd.Telephone_no  As Telephoneno,tbcd.bill_no as billNo From Telephone_bill_claim_details Tbcd,Claim_master Cm Where Tbcd.Sfid  ='"+telePhoneBillBean.getSfid()+"' And Cm.Status=1 And Cm.Claim_type_id=2 And Tbcd.Claim_id =Cm.Id " +
					"And Tbcd.Status  In (2,8,9) And Tbcd.Request_id In (Select Max(Request_id) From Telephone_bill_claim_details Where Sfid='"+telePhoneBillBean.getSfid()+"')Group By Cm.Claim_name,Cm.Id,Tbcd.Telephone_no,tbcd.bill_no Having tbcd.bill_no Is Not Null)";*/
			String query1=" select tab.claim_name as name,tele.telephone_no as telephoneNo,tab.id as claimId from telephone_bill_claim_details tele ,(select claim.id,claim.claim_name from claim_master claim where claim.claim_type_id=2 and claim.status=1)tab where tele.claim_id=tab.id and tele.sfid='"+telePhoneBillBean.getSfid()+"' " +
						  " and tele.request_id in(select max(request_id) from telephone_bill_claim_details where sfid='"+telePhoneBillBean.getSfid()+"') union select claim.claim_name,null,claim.id from claim_master claim where claim.claim_type_id=2 and claim.status=1 and claim.id not in " +
						  "(select claim_id from telephone_bill_claim_details where sfid='"+telePhoneBillBean.getSfid()+"' and request_id in (select max(request_id) from telephone_bill_claim_details where sfid='"+telePhoneBillBean.getSfid()+"'))";
			telephoneBillClaimMasterList=session.createSQLQuery(query1).addScalar("name", Hibernate.STRING).addScalar("telephoneNo", Hibernate.STRING).addScalar("claimId", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TelePhoneBillBean.class)).list();
			if(telephoneBillClaimMasterList.size()==0){
				String query="select claim_name as name,id as claimId from claim_master where claim_type_id=2 and status=1 order by claimid";
				telephoneBillClaimMasterList=session.createSQLQuery(query).addScalar("name", Hibernate.STRING).addScalar("claimId", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TelePhoneBillBean.class)).list();
			}
		}catch (Exception e){
			throw e;
		}finally{
			//session.close();
		}
	    return telephoneBillClaimMasterList;
	}
	public int  checkSelectedEmployeeExisted(String sfid) throws Exception{
		Session session = null;
		int check=-1;
		try{
			session=hibernateUtils.getSession();  //session=sessionFactory.openSession();
			String query = "select count(*) from Telephone_Eligibility_Details where sfid='"+sfid+"'";
			check=((BigDecimal)(session.createSQLQuery(query).uniqueResult())).intValue();
		}catch (Exception e) {
			throw e;
		}finally{
			//session.close();
		}
		return check;
	}
	@SuppressWarnings("unchecked")
	public List<TelePhoneBillBean> getTelephoneBillClaimRequestDetails(int requestId,WorkFlowMappingBean workFlowMappingBean)throws Exception{
		Session session=null;
		List<TelePhoneBillBean> telephoneBillClaimList=null;
		try{
			session=hibernateUtils.getSession();  //session=sessionFactory.openSession();
			String query = " Select tbcd.sfid as sfid,Em.Name_In_Service_Book as name,Tfrd.From_Date as fromDate,Tfrd.To_Date as toDate,tfrd.sanctioned_amount as SanctionedAmount,"+
                         " Sum(Tbcd.Total) as grandTotal,Tfrd.Ip_Address as ipAddress ,tbcd.internet_flag as internetFlag, em.designation_id as designationId,tfrd.cash_assignment_amount as cashAssignmentAmount,tfrd.taskholder_remarks as taskHolderRemarks,tfrd.user_remarks as userRemarks"+
                         " From Telephone_Bill_Claim_Details Tbcd ,Tution_Fee_Request_Details Tfrd ,emp_master em "+
                         " Where Tbcd.Request_Id="+requestId+" And Tbcd.Request_Id=Tfrd.Request_Id And Em.Sfid=Tfrd.Sfid "+
                         " Group By Tbcd.Sfid,Tfrd.From_Date,Tfrd.To_Date,tfrd.sanctioned_amount,Tfrd.Ip_Address,Em.Name_In_Service_Book,tbcd.internet_flag,em.designation_id,tfrd.cash_assignment_amount,tfrd.taskholder_remarks,tfrd.user_remarks" ;
 
			      telephoneBillClaimList = session.createSQLQuery(query).addScalar("sfid",Hibernate.STRING).
			            addScalar("SanctionedAmount",Hibernate.STRING).addScalar("name",Hibernate.STRING).
	                    addScalar("fromDate",Hibernate.DATE).addScalar("toDate",Hibernate.DATE).addScalar("ipAddress",Hibernate.STRING).
	                    addScalar("designationId", Hibernate.STRING).addScalar("grandTotal",Hibernate.STRING).addScalar("internetFlag", Hibernate.STRING)
	                    .addScalar("cashAssignmentAmount", Hibernate.INTEGER).addScalar("taskHolderRemarks", Hibernate.STRING).addScalar("userRemarks", Hibernate.STRING).
	                    setResultTransformer(Transformers.aliasToBean(TelePhoneBillBean.class)).list();
		           for(int i=0;i<telephoneBillClaimList.size();i++){
		        	   workFlowMappingBean.setClaimedAmount(telephoneBillClaimList.get(i).getGrandTotal());
		        	   workFlowMappingBean.setTeleSanctionedAmount(telephoneBillClaimList.get(i).getSanctionedAmount());
		               workFlowMappingBean.setCashAssignmentAmount(telephoneBillClaimList.get(i).getCashAssignmentAmount());
		            }
			     
		}catch (Exception e) {
			throw e;		
		}finally{
			//session.close();
		}
		return telephoneBillClaimList;
	}
	@SuppressWarnings("unchecked")
	public List<TelephoneBillClaimDetailsDTO> getTelephoneBillClaimListDetails(int requestId) throws Exception{
		Session session = null;
		List<TelephoneBillClaimDetailsDTO> telephoneMainList = null;
		try{
			session=hibernateUtils.getSession();  //session=sessionFactory.openSession();
			telephoneMainList = session.createCriteria(TelephoneBillClaimDetailsDTO.class).add(Expression.eq("requestId", String.valueOf(requestId))).list();
			for (TelephoneBillClaimDetailsDTO telephoneBillClaimDetailsDTO : telephoneMainList) {
				TutionFeeClaimMasterDTO tutionFeeClaimMasterDTO=(TutionFeeClaimMasterDTO)session.createCriteria(TutionFeeClaimMasterDTO.class).add(Expression.eq("id", telephoneBillClaimDetailsDTO.getClaimId())).uniqueResult();
				telephoneBillClaimDetailsDTO.setClaimName(tutionFeeClaimMasterDTO.getClaimName());
			}
		}catch (Exception e) {
			throw e;
		}finally{
			//session.close();
		}
		return telephoneMainList;
	}
	 //code for getting the list of the employee based on the selected designation
	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> getSelectedEmployeeList(String designationId,TelePhoneBillBean telePhoneBillBean)throws Exception{
		Session session = null;
		List<KeyValueDTO> empList =null;
		try{
			session=hibernateUtils.getSession();  //session=sessionFactory.openSession();
			String query="Select Unique Applicable_Employee From Tele_Desig_Eligibile_Details Where Designation_Id="+Integer.parseInt(designationId)+" and status=1";
			int applicableEmp=((BigDecimal)session.createSQLQuery(query).uniqueResult()).intValue();
			if(applicableEmp==1){
				String qry =" select distinct designation_id from Tele_Emp_Eligibilty_Details where designation_id="+Integer.parseInt(designationId)+" and status=1";
				Object appEmp =session.createSQLQuery(qry).uniqueResult();
				if(CPSUtils.isNullOrEmpty(appEmp)){ //condition fr getting list in seleted box after submiting(displaying remaining sfid)
					String query1 ="Select Sfid||'('||Name_In_Service_Book||')' As name,Sfid As value From Emp_Master Where Designation_Id="+Integer.parseInt(designationId)+" And Status=1 and working_location in(select working_location from emp_master where sfid='"+telePhoneBillBean.getSfid()+"' and status=1) Order By Sfid";
								empList	=session.createSQLQuery(query1).addScalar("name", Hibernate.STRING).addScalar("value", Hibernate.STRING).
								setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
				}else{
					String query1 =" Select Sfid||'('||Name_In_Service_Book||')' As Name,Sfid As Value From Emp_Master Where Designation_Id="+Integer.parseInt(designationId)+" And Status=1 and working_location in(select working_location from emp_master where sfid='"+telePhoneBillBean.getSfid()+"' and status=1)  And Sfid In(Select Sfid From Tele_Emp_Eligibilty_Details Where Status =1)order by sfid";
  		          empList=session.createSQLQuery(query1).addScalar("name", Hibernate.STRING).addScalar("value", Hibernate.STRING).
  		         setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();

				}
			}else{
				String query1 =" Select Sfid||'('||Name_In_Service_Book||')' As Name,Sfid As Value From Emp_Master Where Designation_Id="+Integer.parseInt(designationId)+" And Status=1 and working_location in(select working_location from emp_master where sfid='"+telePhoneBillBean.getSfid()+"' and status=1) And Sfid In (select sfid from tele_emp_eligibilty_details where status=1) order by sfid";
								empList=session.createSQLQuery(query1).addScalar("name", Hibernate.STRING).addScalar("value", Hibernate.STRING).
								setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			//session.close();
		}
		return empList;
	}
	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> getNotSelectedEmployeeList(String designationId,TelePhoneBillBean telePhoneBillBean) throws Exception{
		Session session = null;
		List<KeyValueDTO> empList =null;
		try{
			session=hibernateUtils.getSession();  //session=sessionFactory.openSession();
			String query="Select Unique Applicable_Employee From Tele_Desig_Eligibile_Details Where Designation_Id="+Integer.parseInt(designationId)+" and status=1";
			int applicableEmp1=((BigDecimal)session.createSQLQuery(query).uniqueResult()).intValue();
			if(applicableEmp1==0){
				String query1 =" Select Emp.Sfid||'('||Emp.Name_In_Service_Book||')' As name,value From ((Select Em.Sfid As Value From Emp_master Em Where Em.Status=1 and em.working_location in (select working_location from emp_master where sfid='"+telePhoneBillBean.getSfid()+"' and status=1)) Minus " +
							   " (Select Sfid From Tele_Emp_Eligibilty_Details where status=1)) Tab,Emp_Master Emp Where tab.value=emp.sfid and emp.status=1 and emp.designation_id="+Integer.parseInt(designationId)+" order by emp.sfid";
				empList	=session.createSQLQuery(query1).addScalar("name", Hibernate.STRING).addScalar("value", Hibernate.STRING).
						 setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
			}else{
				  String qry ="select distinct Designation_Id from Tele_Emp_Eligibilty_Details where designation_id="+Integer.parseInt(designationId)+" and status=1 ";
				  Object appliedEmployee = session.createSQLQuery(qry).uniqueResult();
				if(CPSUtils.isNullOrEmpty(appliedEmployee)){
						String query1 =" Select Sfid||'('||Name_In_Service_Book||')' As Name,Sfid As Value From Emp_Master Where Designation_Id="+Integer.parseInt(designationId)+" And Status=1 And working_location in (select working_location from emp_master where sfid='"+telePhoneBillBean.getSfid()+"' and status=1)  and Sfid  In(Select Sfid From Tele_Emp_Eligibilty_Details Where Status =1)order by sfid";
		        		empList=session.createSQLQuery(query1).addScalar("name", Hibernate.STRING).addScalar("value", Hibernate.STRING).
		        		setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
				}else{
					String query1 =" Select Sfid||'('||Name_In_Service_Book||')' As Name,Sfid As Value From Emp_Master Where Designation_Id="+Integer.parseInt(designationId)+" And Status=1 and working_location in (select working_location from emp_master where sfid='"+telePhoneBillBean.getSfid()+"' and status=1) And Sfid not In(Select Sfid From Tele_Emp_Eligibilty_Details Where Status =1)order by sfid";
	        		          empList=session.createSQLQuery(query1).addScalar("name", Hibernate.STRING).addScalar("value", Hibernate.STRING).
	        		         setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
				}
				 
			}
		}catch (Exception e) {
			throw e;
		}finally{
			//session.close();
		}
		return empList;
	}
	//code for submitting the selected employee details
	public String submitTelephoneEmployeeSelectedList(TelePhoneBillBean telePhoneBillBean)throws Exception{
		String message = "";
		Session session = null;
		//Transaction tx = null;
		try{
			session=hibernateUtils.getSession(); //session=sessionFactory.openSession();
			//tx=session.beginTransaction();
			
			String[] sfidList=telePhoneBillBean.getPk().split(",");
			session.createSQLQuery("delete from Tele_emp_eligibilty_details where designation_id="+Integer.parseInt(telePhoneBillBean.getDesignationId())).executeUpdate();
			for(int i = 0;i < sfidList.length;i++){
				String sfid = sfidList[i];
				TelephoneEmpEligibiltyDetailsDTO telephoneEmpEligibiltyDetailsDTO = new TelephoneEmpEligibiltyDetailsDTO();
				telephoneEmpEligibiltyDetailsDTO.setDesignationId(Integer.parseInt(telePhoneBillBean.getDesignationId()));
				telephoneEmpEligibiltyDetailsDTO.setSfid(sfid);
				telephoneEmpEligibiltyDetailsDTO.setStatus(1);
			    session.saveOrUpdate(telephoneEmpEligibiltyDetailsDTO);
			}
			//tx.commit();
			message=CPSConstants.SUCCESS;
		}catch (Exception e) {
			//tx.rollback();
			throw e;
		}finally{
			//session.close();
		}
		return message;
	}
	// delete designationeligibility details
	public String deleteDesignationEligibilityDetails(TelePhoneBillBean telePhoneBillBean)throws Exception{
		Session session=null;
		String message="";
		try{
			session=hibernateUtils.getSession();  //session=sessionFactory.openSession();
			String[] desigList=telePhoneBillBean.getDesignationId().split(",");
			for(int i=0;i<desigList.length;i++){
				String designationList = desigList[i];
				session.createSQLQuery("delete from tele_desig_eligibile_details where designation_id="+designationList+" and status=1 and applicable_employee=1").executeUpdate();
				session.createSQLQuery("delete from Tele_emp_eligibilty_details where designation_id="+designationList+" and status=1").executeUpdate();
			}
			//session.createSQLQuery("update Tele_Desig_Eligibile_Details set status=0 where amount=?").setFloat(0, Float.parseFloat(telePhoneBillBean.getPk())).executeUpdate();
			message=CPSConstants.DELETE;
		}catch (Exception e) {
			throw e;
		}finally{
			//session.close();
		}
		return message;
	}
	// code for checking whether user Claimed for telephoneBil
	@SuppressWarnings("unchecked")
	public String checkTelephoneBillApplicantDetails(TelePhoneBillBean telePhoneBillBean)throws Exception{
		String message=CPSConstants.SUCCESS;
		Session session=null;
		try{
			session=hibernateUtils.getSession();
			String query ="SELECT REQUEST_ID as requestID,REQUEST_TYPE as requestType,FROM_DATE AS fromDate,TO_DATE as " +
					      "toDate FROM TUTION_FEE_REQUEST_DETAILS WHERE REQUEST_TYPE_ID =55 and sfid='"+telePhoneBillBean.getSfid()+"' AND " +
					      "FINANCIAL_YEAR_ID IN (SELECT TO_CHAR(id) FROM financial_year_master WHERE sysdate BETWEEN From_date AND To_date) and status in(2,8)";
			List<TutionFeeRequestDetailsDTO> checkTeleBillClaimList=session.createSQLQuery(query).addScalar("requestID", Hibernate.STRING).addScalar("requestType", Hibernate.STRING).addScalar("fromDate", Hibernate.DATE).addScalar("toDate", Hibernate.DATE)
																	.setResultTransformer(Transformers.aliasToBean(TutionFeeRequestDetailsDTO.class)).list();
			if(checkTeleBillClaimList.size()!=0){
				for(int i=0;i<checkTeleBillClaimList.size();i++){
					SimpleDateFormat sd =new SimpleDateFormat("MMM-yyyy");;
					String listDate =sd.format(checkTeleBillClaimList.get(i).getFromDate());
					String beanDate=sd.format(telePhoneBillBean.getFromDate());
					if(listDate.equals(beanDate)){
						message="telephoneBillAlreadyApplied";
					}
				}
			}	
		}catch (Exception e) {
			throw e;
		}finally{
			
		}
		return message;
	}
	@SuppressWarnings("unchecked")
	public TelePhoneBillBean checkTelephoneBillInternetDetails(TelePhoneBillBean telePhoneBillBean)throws Exception{
		Session session = null;
		List<TelephoneDesigEligibilityDetailsDTO> teleDesigEmpInternet = null;
		try{
			session = hibernateUtils.getSession();
			if(telePhoneBillBean.getInternetFlag().equals("1")){
				String query ="select amount_withInternet as amountWithInternet,service_tax_withinternet as serviceTaxWithInternet,total_amount_withinternet as totAmountWithInternet from tele_desig_eligibile_details where designation_id=? and status=1";
				teleDesigEmpInternet= session.createSQLQuery(query).addScalar("amountWithInternet", Hibernate.INTEGER).addScalar("serviceTaxWithInternet", Hibernate.FLOAT).addScalar("totAmountWithInternet", Hibernate.INTEGER).setString(0, telePhoneBillBean.getDesignationId()).setResultTransformer(Transformers.aliasToBean(TelephoneDesigEligibilityDetailsDTO.class)).list();
				
				telePhoneBillBean.setAmount(String.valueOf(teleDesigEmpInternet.get(0).getAmountWithInternet()));
				telePhoneBillBean.setServiceTax(String.valueOf(teleDesigEmpInternet.get(0).getServiceTaxWithInternet()));
				telePhoneBillBean.setTotAmount(String.valueOf(teleDesigEmpInternet.get(0).getTotAmountWithInternet()));
			}else if(telePhoneBillBean.getInternetFlag().equals("2")){
				String query ="select amount_without_internet as amountWithoutInternet,service_tax_without_internet as serviceTaxWithoutInternet,total_amount_without_internet as totAmountWithoutInternet  from tele_desig_eligibile_details where designation_id=? and status=1";
				teleDesigEmpInternet= session.createSQLQuery(query).addScalar("amountWithoutInternet", Hibernate.INTEGER).addScalar("serviceTaxWithoutInternet", Hibernate.FLOAT).addScalar("totAmountWithoutInternet", Hibernate.INTEGER).setString(0, telePhoneBillBean.getDesignationId()).setResultTransformer(Transformers.aliasToBean(TelephoneDesigEligibilityDetailsDTO.class)).list();
				
				telePhoneBillBean.setAmount(String.valueOf(teleDesigEmpInternet.get(0).getAmountWithoutInternet()));
				telePhoneBillBean.setServiceTax(String.valueOf(teleDesigEmpInternet.get(0).getServiceTaxWithoutInternet()));
				telePhoneBillBean.setTotAmount(String.valueOf(teleDesigEmpInternet.get(0).getTotAmountWithoutInternet()));
			}
			
		}catch (Exception e) {
			throw e;
		}finally{
			
		}
		return telePhoneBillBean;
	}
	//cash assignment
	public String submitTelephoneCashAssignment(TelephoneCashAssignmentDTO telephoneCashAssignmentDTO)throws Exception{
		Session session =null;
		String message ="";
		try{
			session=hibernateUtils.getSession();
			session.saveOrUpdate(telephoneCashAssignmentDTO);
			message=CPSConstants.SUCCESS;
			session.flush();
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	@SuppressWarnings("unchecked")
	public List<TelephoneCashAssignmentDTO> getTelephoneCashAssignmentDetails()throws Exception{
		Session session =null;
		List<TelephoneCashAssignmentDTO> teleCashAssignament =null;
		try{
			session=hibernateUtils.getSession();
			teleCashAssignament=session.createCriteria(TelephoneCashAssignmentDTO.class).add(Expression.eq("status", 1)).list();
		}catch (Exception e) {
			throw e;
		}
		return teleCashAssignament;
	}
	public String getTeleCashDetails(String sfid) throws Exception{
		String cashSfid ="";
		Session session=null;
		try{
			session=hibernateUtils.getSession();
			Object obj=session.createSQLQuery("select Sfid from TELEPHONE_CASH_ASSIGNMENT where sfid='"+sfid+"' and status=1").uniqueResult();
		if(!CPSUtils.isNullOrEmpty(obj)){
			cashSfid=obj.toString();
		}
		}catch (Exception e) {
			throw e;
		}
		return cashSfid;
	}
	public String deleteTeleCashAssignmentDetails(int id)throws Exception{
		Session session = null;
		String message="";
		try{
			session=hibernateUtils.getSession();
			TelephoneCashAssignmentDTO telephoneCashAssignmentDTO=(TelephoneCashAssignmentDTO)session.get(TelephoneCashAssignmentDTO.class, id);
			telephoneCashAssignmentDTO.setStatus(0);
			message=CPSConstants.DELETE;
			session.flush();
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	public TelePhoneBillBean checkMissingPeriodDetails(TelePhoneBillBean telephoneBillBean)throws Exception{
		Session session =null;
		try{
			session=hibernateUtils.getSession();
			String fromDate = CPSUtils.formatDate(telephoneBillBean.getFromDate());
			String maxDate=(String)session.createSQLQuery("Select To_char(Max(to_date),'dd-MON-yyyy') From Tution_fee_request_details Where Sfid='"+telephoneBillBean.getSfid()+"' And Status In(2,8)").uniqueResult();
			if(!CPSUtils.isNullOrEmpty(maxDate)){
				float  missingPeriod=((BigDecimal)session.createSQLQuery("Select Months_between((to_date('"+fromDate+"','dd-MON-yyyy')-1),to_date('"+maxDate+"','dd-MON-yyyy')) As missingPeriod From Dual").uniqueResult()).floatValue();
			if(missingPeriod>0){
				//if(missingPeriod !=1 && missingPeriod!=0 &&(missingPeriod>1 || missingPeriod<1)){
				if(missingPeriod!=0 &&(missingPeriod>1 || missingPeriod<1 || missingPeriod ==1)){
					 String nextDay =CPSUtils.nextDate(maxDate);
				     String prevoiusDay=CPSUtils.previousDate(fromDate);
				     telephoneBillBean.setMissingPeriodFrom(nextDay);
				     telephoneBillBean.setMissingPeriodTo(prevoiusDay);
				 }
			}
			}
			
		}catch (Exception e) {
			throw e;
		}
		return telephoneBillBean;
	}
	public String checkingTelephoneBillCashAssignmentDetails(TelePhoneBillBean telePhoneBillBean) throws Exception{
		Session session = null;
		String message =null;
		try{
			Criteria crit=null;
			session=hibernateUtils.getSession();
			crit=session.createCriteria(TelephoneCashAssignmentDTO.class).add(Expression.eq("status", 1)).add(Expression.ilike("sfid", telePhoneBillBean.getSfid().trim()));
		if(CPSUtils.checkList(crit.list())){
			message=CPSConstants.DUPLICATE;
		}
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	public String checkTelephoneBillEnableLoginLink(TelePhoneBillBean telePhoneBillBean) throws Exception{
		Session session =null;
		String message =null;
		try{
			session =hibernateUtils.getSession();
			String query ="select applicable_employee from tele_desig_eligibile_details where designation_id in(select designation_id from emp_master where sfid='"+telePhoneBillBean.getSfid().toUpperCase()+"' and status=1) and status=1";
			Object obj=(session.createSQLQuery(query).uniqueResult());
			if(!CPSUtils.isNullOrEmpty(obj)){
				int check=((BigDecimal)obj).intValue();
				if(check==1){
					message=CPSConstants.YES;
				}else{
					String query1= "select count(*) from Tele_Emp_Eligibilty_Details where sfid='"+telePhoneBillBean.getSfid().toUpperCase()+"' and status=1";
					int empEligibiltyCheck=((BigDecimal)(session.createSQLQuery(query1).uniqueResult())).intValue();
					if(empEligibiltyCheck==1){
						message=CPSConstants.YES;
					}else{
						message=CPSConstants.NO;
					}
				}
				
			}else{
				message=CPSConstants.NO;
			}
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
}