package com.callippus.web.dao.hindi;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.hindi.HindiIncentiveBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.hindi.dto.EligibleEmpExamDTO;
import com.callippus.web.hindi.dto.EmpKeyValueDTO;
import com.callippus.web.hindi.dto.ExamDTO;
import com.callippus.web.hindi.dto.HindiCashAwardDTO;
import com.callippus.web.hindi.dto.HindiEmpResultDTO;
import com.callippus.web.hindi.dto.HindiEmployeeDTO;
import com.callippus.web.hindi.dto.HindiExamConfigDTO;
import com.callippus.web.hindi.dto.HindiIncentiveDetailsDTO;
import com.callippus.web.hindi.dto.HindiNominationDTO;
import com.callippus.web.hindi.dto.NonEligibleEmpExamDTO;
import com.callippus.web.paybill.dto.PayBillEmpPaymentDeatilsDTO;
@Service
public class SQLHindiIncentiveDAO implements IHindiIncentive{
	
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	SessionFactory sessionFactory;
	
	public String getCurrentRunMonth() throws Exception
	{
		Session session = null;
		String result = null;
			try
			{
				session = hibernateUtils.getSession();
				result = session.createSQLQuery("select to_char(Add_Months(run_month, 1), 'Mon-YYYY') from paybill_status_details where status not in (0, 2) and run_month = (select max(run_month) from paybill_status_details where status not in (0, 2))").uniqueResult().toString();
			}catch(Exception e)
			{
				throw e;	
			}
		return result;
	}
	
	public String saveExamData(ExamDTO examDTO) throws Exception
	{
		String message = null;
		Session session = null;
		try
		{
			session = hibernateUtils.getSession();
			
			if(examDTO.getExamId() !=0){
		    session.saveOrUpdate(examDTO);
			message = CPSConstants.UPDATE;	
			}else{
		    session.saveOrUpdate(examDTO);	
			message = CPSConstants.SUCCESS;
			}
		}
		catch(Exception e)
		{
			message = CPSConstants.FAILED;
			e.printStackTrace();
			throw e;
		}
		hibernateUtils.closeSession();
		
		return message;
	}
	public List examDuplicateChecklist(HindiIncentiveBean hindiBean) throws Exception{
		List<ExamDTO> examDuplicateChecklist = null;
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			String qry = "select exam_id as examId from hindi_exam_master where status=1 and exam_name='"+hindiBean.getExamName()+"'";
			examDuplicateChecklist = session.createSQLQuery(qry).addScalar("examId", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(ExamDTO.class)).list();
				
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return examDuplicateChecklist;
	}
	@SuppressWarnings("unchecked")
	public List getExamsList() throws Exception
	{
	List<ExamDTO> examsList = null;	
	Session session = null;
		try
		{
			session = hibernateUtils.getSession();
			//String qry = "select exam_name as examName,exam_id as examId,total_marks as totalMarks,description as description from hindi_exam_master where status=1";
			examsList = session.createCriteria(ExamDTO.class).add(Expression.eq(CPSConstants.STATUS, "1")).list();
			/*String qry="select hem.exam_name as examName,hem.exam_id as examId,hem.total_marks as totalMarks,hem.description as description,cfg.id as configId" +
					" from hindi_exam_master hem,hindi_examconfig_master cfg where hem.status=1 and cfg.status=1 and hem.exam_id=cfg.exam_id";
			examsList = session.createSQLQuery(qry).addScalar("examName", Hibernate.STRING).addScalar("examId", Hibernate.INTEGER).addScalar("totalMarks", Hibernate.INTEGER).addScalar("description", Hibernate.STRING).addScalar("configId", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(ExamDTO.class)).list();*/
	        
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}finally{
		hibernateUtils.closeSession();
		}
		return examsList;
		}
	public String deleteExam(HindiIncentiveBean hindiBean) throws Exception
	{
		Session session = null;
		String message = null;
		try
		{
			session = hibernateUtils.getSession();
			session.createQuery("update ExamDTO set status=0 where examId="+hindiBean.getExamId()).executeUpdate();
			message = CPSConstants.DELETE;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		hibernateUtils.closeSession();
		
		return message;
	}
	@SuppressWarnings("unchecked")
	public List getEmployeeList() throws Exception
	{
		List<EmpKeyValueDTO> empList = null;
		Session session = null;
		try
		{
			session = hibernateUtils.getSession();
			String qry = "select em.sfid as id,em.name_in_service_book as name,em.mother_tongue as language,qm.name as qual from emp_master em,QUALIFICATION_DETAILS qd,qualification_master qm where em.status=1 and qd.status=1 and em.sfid=qd.sfid and qd.qualification_id=qm.id order by em.sfid";
			empList = session.createSQLQuery(qry).addScalar("id", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("language", Hibernate.STRING).addScalar("qual", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(EmpKeyValueDTO.class)).list();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			hibernateUtils.closeSession();
		}
		return empList;
	}
	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> getSfidList() throws Exception
	{
		List<KeyValueDTO> sfidList = null;
		Session session = null;
		try
		{
			session = hibernateUtils.getSession();
			String qry = "select sfid as key,sfid||'('||name_in_service_book||')' as value, mother_tongue as name from Emp_Master where status = 1 order by sfid";			
			sfidList = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
		}catch(Exception e) {              
			throw e;
		}
		return sfidList;
	}
	
	public String getIncentiveAmount(HindiIncentiveBean hindiBean) throws Exception
	{
		Session session = null;
		String incentiveAmount = "";
		try
		{
			session = hibernateUtils.getSession();
			String qry = "select CALCULATE_HINDIINC(?) from dual";	
			incentiveAmount = session.createSQLQuery(qry).setString(0, hindiBean.getSfidSearch().toUpperCase()).uniqueResult().toString();
			//payList = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
		}catch(Exception e) {              
			throw e;
		}
		return incentiveAmount;
	}
	
	@SuppressWarnings("unchecked")
	public List getEmployeeDetailsList() throws Exception
	{
		List<HindiEmployeeDTO> empDetailsList = null;
		Session session = null;
		try
		{
			session = hibernateUtils.getSession();
		   empDetailsList = session.createCriteria(HindiEmployeeDTO.class).add(Expression.eq(CPSConstants.STATUS, "1")).list();
		   String qry = " select mother_tongue from emp_master where status=1 and sfid=?";
		   for(int i=0;i<empDetailsList.size();i++){
			HindiEmployeeDTO hindiEmployeeDTO=empDetailsList.get(i);

	   		String motherTongue = (String)session.createSQLQuery(qry).setString(0, hindiEmployeeDTO.getSfid()).uniqueResult();
			hindiEmployeeDTO.setMothertongue(motherTongue);
		   }
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			hibernateUtils.closeSession();
		}
		return empDetailsList;
	}
	@SuppressWarnings("unchecked")
	public List employeeDuplicateCheckList(HindiIncentiveBean hindiBean) throws Exception
	{
		List<HindiEmployeeDTO> empDuplicateCheckList = null;
		Session session = null;
		try
		{
			session = hibernateUtils.getSession();
			//empDuplicateCheckList = session.createSQLQuery("select sfid from hindi_employee_master where status=1 and sfid='"+hindiBean.getSfid()+"'").list();
		    String qry = "select sfid as sfid from hindi_employee_master where status=1 and sfid='"+hindiBean.getSfid()+"'";
		    empDuplicateCheckList = session.createSQLQuery(qry).addScalar("sfid", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(HindiEmployeeDTO.class)).list();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			hibernateUtils.closeSession();
		}
		return empDuplicateCheckList;
	}
	public String saveEmployeeData(HindiEmployeeDTO employeeDTO)throws Exception
	{
		String message = null;
		Session session = null;
		try
		{
			
			session = hibernateUtils.getSession();
		
				session.saveOrUpdate(employeeDTO);
				message = CPSConstants.SUCCESS;
			
			
		}
		catch(Exception e)
		{
			message = CPSConstants.FAILED;
			e.printStackTrace();
			throw e;
		}
		finally{
			hibernateUtils.closeSession();
		}
		
		
		return message;
	}
	public void saveNonEligibleEmpExamData(HindiIncentiveBean hindiBean) throws Exception
	{		
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			String[] nonEligibleIds = hindiBean.getNonEligibleExamId().split(",");
			session.createSQLQuery("delete from hindi_noneligible_exam_details where sfid='"+hindiBean.getSfid()+"'").executeUpdate();
			for(int i=0; i<nonEligibleIds.length; i++)
			{
				 NonEligibleEmpExamDTO nonEligibleExamDTO = new NonEligibleEmpExamDTO();
				 nonEligibleExamDTO.setSfid(hindiBean.getSfid());
				 nonEligibleExamDTO.setExamId(Integer.parseInt(nonEligibleIds[i]));
				 nonEligibleExamDTO.setStatus("1");
				 
				 session.saveOrUpdate(nonEligibleExamDTO);
			}
		   
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
	}
	public void saveEligibleEmpExamData(HindiIncentiveBean hindiBean) throws Exception{
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			String[] eligibleIds = hindiBean.getEligibleExamId().split(",");
			session.createSQLQuery("delete from hindi_eligible_exam_details where sfid='"+hindiBean.getSfid()+"'").executeUpdate();
			for(int i=0; i<eligibleIds.length; i++)
			{
				 EligibleEmpExamDTO eligibleExamDTO = new EligibleEmpExamDTO();
				 eligibleExamDTO.setSfid(hindiBean.getSfid());
				 eligibleExamDTO.setExamId(Integer.parseInt(eligibleIds[i]));
				 eligibleExamDTO.setStatus("1");
				 
				 session.saveOrUpdate(eligibleExamDTO);
			}
		   
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
	}
	public void deleteNonEligibleEmpExamData(HindiIncentiveBean hindiBean) throws Exception
	{		
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			session.createSQLQuery("delete from hindi_noneligible_exam_details where sfid='"+hindiBean.getSfid()+"'").executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	public void deleteEligibleEmpExamData(HindiIncentiveBean hindiBean) throws Exception
	{
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			session.createSQLQuery("delete from hindi_eligible_exam_details where sfid='"+hindiBean.getSfid()+"'").executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	@SuppressWarnings("unchecked")
	public List getNonEligibleEmpExamList() throws Exception
	{
		Session session = null;
		List<NonEligibleEmpExamDTO> nonEligibleEmpExamList = null;
		try{
			session = hibernateUtils.getSession();
			nonEligibleEmpExamList = session.createCriteria(NonEligibleEmpExamDTO.class).list();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return nonEligibleEmpExamList;
	}
	public String deleteEmployeeData(HindiIncentiveBean hindiBean) throws Exception
	{
		String message = null;
		Session session = null;
		//List existSfidInNominationList = null;
		try
		{
			session = hibernateUtils.getSession();
			session.createQuery("update HindiEmployeeDTO set status=0 where sfid='"+hindiBean.getSfid()+"'").executeUpdate();
			session.createQuery("update NonEligibleEmpExamDTO set status=0 where sfid='"+hindiBean.getSfid()+"'").executeUpdate();
			session.createQuery("update EligibleEmpExamDTO set status=0 where sfid='"+hindiBean.getSfid()+"'").executeUpdate();
			
			String sql ="select sfid from HINDI_NOMINATION_MASTER where sfid='"+hindiBean.getSfid()+"' and status=1";
			int sfid = session.createSQLQuery(sql).executeUpdate();
			//if(existSfidInNominationList !=null && existSfidInNominationList.size()>0){
			if(sfid >0){
				session.createQuery("update HindiNominationDTO set status=0 where sfid='"+hindiBean.getSfid()+"'").executeUpdate();	
			}
			message = CPSConstants.DELETE;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			hibernateUtils.closeSession();
		}
		return message;
	}
	
	@SuppressWarnings("unchecked")
	public List getCategorylist() throws Exception
	{
		List<KeyValueDTO> categoryList = null;
		Session session = null;
		try
		{
			session = hibernateUtils.getSession();
			String qry = "select name as key,id as value from category_master where status=1";
			categoryList = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		hibernateUtils.closeSession();
		
		return categoryList;
	}
	@SuppressWarnings("unchecked")
	public List getDeselectDesignationList(HindiIncentiveBean hindiBean) throws Exception
	{
		List<KeyValueDTO> deselectDesignationList = null;
		Session session = null;
		try
		{
			session = hibernateUtils.getSession();
			String empType =""; 
			if(hindiBean.getEmpType()==1){
				empType = "GAZETTED";
			}else if(hindiBean.getEmpType()==2){
				empType = "NON GAZETTED";
			}
			 
			String qry = "select dms.name as key,dms.id as value from designation_master dms where dms.status=1 and dms.id in" +
					"(select dmp.desig_id from designation_mappings dmp where dmp.category_id="+hindiBean.getEmpCategory()+" and  type='"+empType+"')MINUS " +
					"select dm.name,dm.id from designation_master dm where dm.status=1 and dm.id in(select designation_id from " +
					"hindi_examconfig_designations where status=1 and id in" +
					"(select id from hindi_examconfig_master where status=1 and category_id ="+hindiBean.getEmpCategory()+" and emp_type='"+hindiBean.getEmpType()+"'))";
			deselectDesignationList = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		hibernateUtils.closeSession();
		
		return deselectDesignationList;
	}
	@SuppressWarnings("unchecked")
	public String saveExamConfigDetails(HindiIncentiveBean hindiBean) throws Exception
	{
		String message = null;
		Session session = null;
		
		List<KeyValueDTO>examConfigDuplicateCheckList = null;
		try {
			session = hibernateUtils.getSession();
			HindiExamConfigDTO examConfigDTO = new HindiExamConfigDTO();
			//edit the existing row
			if(!hindiBean.getPk().equals("")){
				examConfigDuplicateCheckList = examConfigDuplicateCheckList(hindiBean);
				if(examConfigDuplicateCheckList.size() >0){
					for(int i=0;i<examConfigDuplicateCheckList.size();i++){
						int id=examConfigDuplicateCheckList.get(i).getId();
						if(Integer.parseInt(hindiBean.getPk())==id){
							examConfigDTO.setId(Integer.parseInt(hindiBean.getPk()));
							break;
						}
						else{
							message = CPSConstants.DUPLICATE;
							return message;
						}
				  }
				}else if(examConfigDuplicateCheckList.size() ==0){
					examConfigDTO.setId(Integer.parseInt(hindiBean.getPk()));
				}
					
						examConfigDTO.setExamId(hindiBean.getExamId());
						examConfigDTO.setMothertongue(hindiBean.getMothertongue());
						examConfigDTO.setPassMarks(hindiBean.getPassMarks());
						examConfigDTO.setEmpCategory(hindiBean.getEmpCategory());
						examConfigDTO.setEmpType(hindiBean.getEmpType());
						examConfigDTO.setIncrementApplicable(hindiBean.getIncrementApplicable());
						examConfigDTO.setNoOfIncrements(hindiBean.getNoOfIncrements());
						examConfigDTO.setCashAwardApplicable(hindiBean.getCashAwardApplicable());
						examConfigDTO.setCashAwardAmount(hindiBean.getCashAwardAmount());
						examConfigDTO.setStatus("1");
						
						//int id=(Integer)session.save(examConfigDTO);
						session.createSQLQuery("delete from hindi_examconfig_designations where id="+hindiBean.getPk()).executeUpdate();
						String[] selectedDesg = hindiBean.getSelectDesignation().split(",");
						if(selectedDesg.length != 0){
							for(int j=0;j<selectedDesg.length;j++)
							{
								session.createSQLQuery("insert into hindi_examconfig_designations values("+hindiBean.getPk()+","+selectedDesg[j]+",1)").executeUpdate();	
							}
						}

						session.saveOrUpdate(examConfigDTO);
						message = CPSConstants.UPDATE;
						
					}
			
			//insert new row
			else{
				examConfigDuplicateCheckList = examConfigDuplicateCheckList(hindiBean);
				if(examConfigDuplicateCheckList.size()==0){
				examConfigDTO.setMothertongue(hindiBean.getMothertongue());
				examConfigDTO.setExamId(hindiBean.getExamId());
				examConfigDTO.setPassMarks(hindiBean.getPassMarks());
				examConfigDTO.setEmpCategory(hindiBean.getEmpCategory());
				examConfigDTO.setEmpType(hindiBean.getEmpType());
				examConfigDTO.setIncrementApplicable(hindiBean.getIncrementApplicable());
				examConfigDTO.setNoOfIncrements(hindiBean.getNoOfIncrements());
				examConfigDTO.setCashAwardApplicable(hindiBean.getCashAwardApplicable());
				examConfigDTO.setCashAwardAmount(hindiBean.getCashAwardAmount());
				examConfigDTO.setStatus("1");
				
				int id=(Integer)session.save(examConfigDTO);
				String[] selectedDesg = hindiBean.getSelectDesignation().split(",");
				if(selectedDesg.length != 0){
					for(int i=0;i<selectedDesg.length;i++)
					{
						session.createSQLQuery("insert into hindi_examconfig_designations values("+id+","+selectedDesg[i]+",1)").executeUpdate();	
					}
				}

				
				message = CPSConstants.SUCCESS;
				}else{
					message = CPSConstants.DUPLICATE;
				}
			}
			

			//insert new row
			/*if(hindiBean.getType().equals(""))
			{
			examConfigDuplicateCheckList = examConfigDuplicateCheckList(hindiBean);
			if(examConfigDuplicateCheckList.size()==0)
			{
			String[] selectedDesg = hindiBean.getSelectDesignation().split(",");
			session.createSQLQuery("delete from hindi_examconfig_master where exam_id="+hindiBean.getExamId()+" and category_id ="+hindiBean.getEmpCategory()+" and emp_type="+hindiBean.getEmpType()).executeUpdate();
			for (int i = 0; i < selectedDesg.length; i++) {
				HindiExamConfigDTO examConfigDTO = new HindiExamConfigDTO();
				examConfigDTO.setSelectDesignation(selectedDesg[i]);

				examConfigDTO.setExamId(hindiBean.getExamId());
				examConfigDTO.setMothertongue(hindiBean.getMothertongue());
				examConfigDTO.setPassMarks(hindiBean.getPassMarks());
				examConfigDTO.setEmpCategory(hindiBean.getEmpCategory());
				examConfigDTO.setEmpType(hindiBean.getEmpType());
				examConfigDTO.setIncrementApplicable(hindiBean.getIncrementApplicable());
				examConfigDTO.setNoOfIncrements(hindiBean.getNoOfIncrements());
				examConfigDTO.setCashAwardApplicable(hindiBean.getCashAwardApplicable());
				examConfigDTO.setCashAwardAmount(hindiBean.getCashAwardAmount());
				examConfigDTO.setStatus("1");

				session.saveOrUpdate(examConfigDTO);
				message = CPSConstants.SUCCESS;
			}
		 }
			else{
				message = CPSConstants.DUPLICATE;
			}
	  }
			//edit existing row
			else 
			{
				//examConfigDuplicateCheckList = examConfigDuplicateCheckList(hindiBean);
				//if(examConfigDuplicateCheckList.size()==0)
				//{
				String[] selectedDesg = hindiBean.getSelectDesignation().split(",");
				session.createSQLQuery("delete from hindi_examconfig_master where exam_id="+hindiBean.getOldExamId()+" and category_id ="+hindiBean.getOldEmpCategory()+" and emp_type="+hindiBean.getOldEmpType()).executeUpdate();
				for (int i = 0; i < selectedDesg.length; i++) {
					HindiExamConfigDTO examConfigDTO = new HindiExamConfigDTO();
					examConfigDTO.setSelectDesignation(selectedDesg[i]);

					examConfigDTO.setExamId(hindiBean.getExamId());
					examConfigDTO.setMothertongue(hindiBean.getMothertongue());
					examConfigDTO.setPassMarks(hindiBean.getPassMarks());
					examConfigDTO.setEmpCategory(hindiBean.getEmpCategory());
					examConfigDTO.setEmpType(hindiBean.getEmpType());
					examConfigDTO.setIncrementApplicable(hindiBean.getIncrementApplicable());
					examConfigDTO.setNoOfIncrements(hindiBean.getNoOfIncrements());
					examConfigDTO.setCashAwardApplicable(hindiBean.getCashAwardApplicable());
					examConfigDTO.setCashAwardAmount(hindiBean.getCashAwardAmount());
					examConfigDTO.setStatus("1");

					session.saveOrUpdate(examConfigDTO);					
					message = CPSConstants.UPDATE;
				//}
			 }
				//else{
					//message = CPSConstants.DUPLICATE;
				//}
			}*/
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			e.printStackTrace();
			throw e;
		}finally{
		hibernateUtils.closeSession();
		}
		return message;
	}	
	@SuppressWarnings("unchecked")
	public List examConfigDuplicateCheckList(HindiIncentiveBean hindiBean) throws Exception
	{
		List<KeyValueDTO> examConfigDuplicateCheckList = null;
		Session session = null;
		try
		{
			session = hibernateUtils.getSession();
			
			//examConfigDuplicateCheckList = session.createSQLQuery("select exam_id,Emp_Type from Hindi_Examconfig_Master where status=1 and Exam_Id="+hindiBean.getExamId()+" and Emp_Type="+hindiBean.getEmpType()).list();
			String qry = "select id as id from Hindi_Examconfig_Master where status=1 and Exam_Id="+hindiBean.getExamId()+" and" +
					     " category_id="+hindiBean.getEmpCategory()+" and Emp_Type="+hindiBean.getEmpType();
			examConfigDuplicateCheckList = session.createSQLQuery(qry).addScalar("id",Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		
		return examConfigDuplicateCheckList;
	}
	@SuppressWarnings("unchecked")
	public List getExamConfigDetails() throws Exception
	{
		List<HindiExamConfigDTO> examConfigDetailsList = null;
		Session session = null;
		List examList = null;
		List catList = null;
		try{
			session = hibernateUtils.getSession();
			examConfigDetailsList = session.createCriteria(HindiExamConfigDTO.class).add(Expression.eq(CPSConstants.STATUS, "1")).list();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return examConfigDetailsList;
	}	
	@SuppressWarnings("unchecked")
	public List getSelectDesignationList(HindiIncentiveBean hindiBean) throws Exception
	{
		Session session = null;
		List<KeyValueDTO> selectDesignationList = null;
		try
		{
			session = hibernateUtils.getSession();
			/*String qry = "select dm.name as key,dm.id as value from designation_master dm where dm.status=1 and dm.id in"+
			              "(select designation_id from hindi_examconfig_master dms where dms.status=1 and dms.category_id="+hindiBean.getEmpCategory()+" and emp_type="+hindiBean.getEmpType()+")";*/
			String qry = "select dm.name as key,dm.id as value from designation_master dm where dm.status=1 and dm.id in" +
					"(select designation_id from hindi_examconfig_designations where status=1 and id in" +
					"(select id from hindi_examconfig_master where status=1 and category_id ="+hindiBean.getEmpCategory()+" and" +
					" emp_type='"+hindiBean.getEmpType()+"'))";
			selectDesignationList = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
			
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
		String message = null;
		Session session = null;
		try
		{
			session = hibernateUtils.getSession();
			//session.createQuery("update HindiExamConfigDTO set status=0 where id="+hindiBean.getExamId()+" and empCategory="+hindiBean.getEmpCategory()+" and empType="+hindiBean.getEmpType()).executeUpdate();
			session.createQuery("update HindiExamConfigDTO set status=0 where id="+hindiBean.getId()).executeUpdate();
			session.createSQLQuery("update hindi_examconfig_designations set status=0 where id="+hindiBean.getId()).executeUpdate();
			message = CPSConstants.DELETE;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			hibernateUtils.closeSession();
		}
		return message;
	}
	public String saveCashAwardData(HindiCashAwardDTO cashAwardDTO) throws Exception
	{
		String message = null;
		Session session = null;
		try
		{
			session = hibernateUtils.getSession();
			
			if(cashAwardDTO.getId() !=0){
				session.saveOrUpdate(cashAwardDTO);
				message = CPSConstants.UPDATE;	
				}else{
					session.saveOrUpdate(cashAwardDTO);	
				message = CPSConstants.SUCCESS;
				}		
		
		}
		catch(Exception e)
		{
			message = CPSConstants.FAILED;
			e.printStackTrace();
			throw e;
		}
		hibernateUtils.closeSession();
		return message;
	}
	@SuppressWarnings("unchecked")
	public List cashAwardDuplicateCheckList(HindiIncentiveBean hindiBean) throws Exception
	{
		Session session = null;
		List<HindiCashAwardDTO> cashAwardDuplicateCheckList = null;
		try{
			session = hibernateUtils.getSession();
			String qry = "Select exam_id as examId,id as id From hindi_cashaward_master Where  Status=1 and exam_id="+hindiBean.getExamId()+
			             " And("+hindiBean.getLowerPercentage()+" Between lower_percentage And upper_percentage "+ 
                         " or "+hindiBean.getUpperPercentage()+" Between lower_percentage And upper_percentage)";
			cashAwardDuplicateCheckList = session.createSQLQuery(qry).addScalar("examId", Hibernate.INTEGER).addScalar("id", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(HindiCashAwardDTO.class)).list();
			/*cashAwardDuplicateCheckList = session.createQuery("Select examId From HindiCashAwardDTO Where  Status=1 "+ 
					            " and examId="+hindiBean.getExamId()+
                               " And("+hindiBean.getLowerPercentage()+" Between lowerPercentage And upperPercentage "+ 
                           "or "+hindiBean.getUpperPercentage()+" Between lowerPercentage And upperPercentage)").list();*/
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return cashAwardDuplicateCheckList;
	}
	@SuppressWarnings("unchecked")
	public List getCashAwardDetailsList() throws Exception
	{
		Session session = null;
		List<HindiCashAwardDTO> hindiCashAwardList = null;
		try{
			session = hibernateUtils.getSession();
			hindiCashAwardList = session.createCriteria(HindiCashAwardDTO.class).add(Expression.eq(CPSConstants.STATUS, "1")).list();
		}catch(Exception e)
		{
		e.printStackTrace();
		throw e;
		}
		hibernateUtils.closeSession();
		return hindiCashAwardList;
	}
	public String deleteCashAwardDetails(HindiIncentiveBean hindiBean) throws Exception
	{
		String message = null;
		Session session = null;
		try
		{
			session = hibernateUtils.getSession();
			session.createQuery("update HindiCashAwardDTO set status=0 where id="+hindiBean.getId()).executeUpdate();
			message = CPSConstants.DELETE;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			hibernateUtils.closeSession();
		}
		return message;
	}
	
	@SuppressWarnings("unchecked")
	public List getDepartmentsList() throws Exception
	{
		Session session = null;
		List<KeyValueDTO> departmentsList = null;
		try{
			session = hibernateUtils.getSession();
			String qry = "select DEPARTMENT_NAME as key,DEPARTMENT_ID as value from DEPARTMENTS_MASTER where status =1";
			departmentsList = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		finally{
			hibernateUtils.closeSession();
		}
		return departmentsList;
	}
	@SuppressWarnings("unchecked")
	public List getNominationList(HindiIncentiveBean hindiBean) throws Exception
	{
		Session session = null;
		List<EmpKeyValueDTO> nominationList = null;
		try{
			session = hibernateUtils.getSession();
			/*String qry ="select emp.sfid as id,emp.first_name as name,des.name as designation," +
		     " dpt.department_name as department from EMP_MASTER emp,ORG_ROLE_INSTANCE org," +
		     " DESIGNATION_MASTER des,DEPARTMENTS_MASTER dpt where emp.designation_id = des.id " +
		     " and emp.office_id = org.org_role_id and Org.department_id = dpt.department_id " +
		     " and dpt.department_id="+hindiBean.getDepartment()+" and sfid in(select sfid from HINDI_EMPLOYEE_MASTER where" +
		     " status=1 and remarks is null)";
		     
		     nominationList = session.createSQLQuery(qry).addScalar("id", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("designation", Hibernate.STRING).addScalar("department", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(EmpKeyValueDTO.class)).list();
		     */
			
			String qry ="select emp.sfid as id,emp.name_in_service_book as name,des.name as designation," +
		     " dpt.department_name as department from EMP_MASTER emp,ORG_ROLE_INSTANCE org," +
		     " DESIGNATION_MASTER des,DEPARTMENTS_MASTER dpt where emp.designation_id = des.id " +
		     " and emp.office_id = org.org_role_id and Org.department_id = dpt.department_id " +
		     " and emp.status=1 and dpt.department_id="+hindiBean.getDepartment();
			
			nominationList = session.createSQLQuery(qry).addScalar("id", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("designation", Hibernate.STRING).addScalar("department", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(EmpKeyValueDTO.class)).list();
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return nominationList;
	}
	@SuppressWarnings("unchecked")
	public List getSelectedNominationList() throws Exception
	{
		Session session = null;
		List<HindiNominationDTO> selectedNominationList = null;
		try{
			session = hibernateUtils.getSession();
			String qry = "select hn.sfid as key,emp.sfid||'('||EMP.NAME_IN_SERVICE_BOOK||')' as value from hindi_nomination_master hn,emp_master emp where hn.status=1 and emp.status=1 and emp.sfid=hn.sfid";
			selectedNominationList = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
			/*String qry = "Select Hnm.Sfid as sfid,Hnm.Department_Id as department,Dm.Department_Name as deptName From Hindi_Nomination_Master Hnm," +
			   " Departments_Master Dm Where hnm.Status=1 and dm.status=1 and hnm.department_id = dm.department_id";
			selectedNominationList = session.createSQLQuery(qry).addScalar("sfid", Hibernate.STRING).addScalar("department", Hibernate.INTEGER).addScalar("deptName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(HindiNominationDTO.class)).list();*/
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return selectedNominationList;
	}
	public String saveNominationList(HindiIncentiveBean hindiBean) throws Exception
	{
		String message = null;
		Session session = null;
		try{
			
			session = hibernateUtils.getSession();
			
			String[] sfidArray = hindiBean.getSfid().split(",");
			session.createSQLQuery("delete from hindi_nomination_master where department_id="+hindiBean.getDepartment()).executeUpdate();
			for(int i=0; i<sfidArray.length; i++)
			{
				if (!sfidArray[i].equals("")) {
					HindiNominationDTO nominationDTO = new HindiNominationDTO();

					nominationDTO.setSfid(sfidArray[i]);
					nominationDTO.setFromDate(hindiBean.getFromDate());
					nominationDTO.setDepartment(hindiBean.getDepartment());
					nominationDTO.setStatus("1");

					session.save(nominationDTO);
					message = CPSConstants.SUCCESS;
				}
				else{
					message = CPSConstants.SUCCESS;
				}
			}	
		}
		catch(Exception e)
		{
			message = CPSConstants.FAILED;
			e.printStackTrace();
			throw e;
		}
		finally{
			hibernateUtils.closeSession();
		}
		return message;
	}
	
	@SuppressWarnings("unchecked")
	public String saveResultDetails(HindiEmpResultDTO resultDTO) throws Exception
	{
		Session session = null;
		String message = null;
		//List<KeyValueDTO> cashAwardCheck = null;
		List<KeyValueDTO> cashAwardAmount = null;
		
		try{
			session = hibernateUtils.getSession();
			  
			/*//for checking employee eligible for cash award or not
			String qry ="Select hem.sfid as key" +
		   		" From Hindi_Examconfig_Master Cfg,hindi_employee_master hem Where  Cfg.Exam_Id ="+resultDTO.getExamId()+
		   		" and Cfg.status=1 and  hem.status=1 and cfg.CASH_AWARD_APPLICABLE=1 " +
		   		" And Cfg.Exam_Id In(Select elg.Eligible_Exam_Id " +
		   		" From Hindi_eligible_Exam_Details  elg " +
		   		" Where Status=1 And elg.Sfid='"+resultDTO.getSfid()+"')And Cfg.Category_Id In  (Select Dmp.Category_Id " +
		   		" From Emp_Master Emp,Designation_Master Dm,Designation_Mappings Dmp,Hindi_Employee_Master Hem " +
		   		" where emp.designation_id = dm.id and emp.status=1 and dm.status=1 and emp.sfid=hem.sfid and dm.id = dmp.desig_id and emp.sfid='"+resultDTO.getSfid()+"')";
		   
			  cashAwardCheck = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
			
			if(cashAwardCheck.size()==1){
			String qry1 = "select cash_award_amount as key from hindi_cashaward_master where status=1 and exam_id=1 and"+ resultDTO.getMarksPercentage() +
					      "  between lower_percentage and upper_percentage";
			cashAwardAmount = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
			  if(cashAwardAmount.size()==1){
				  resultDTO.setCashAwardAmount(cashAwardAmount.get(0).getKey()) ;
			  }
			}*/
			
			
			String qry = "Select distinct Cash.Cash_Award_Amount as cashAwardAmount From Hindi_Examconfig_Master Cfg,Hindi_Cashaward_Master Cash " +
					"Where Cfg.Status=1 And Cash.Status=1 And cfg.exam_id="+resultDTO.getExamId()+" and cfg.Cash_Award_Applicable=1 and " +
					"cfg.exam_id=cash.exam_id and ("+resultDTO.getMarksPercentage()+" between cash.lower_percentage and cash.upper_percentage)";
			List amount = session.createSQLQuery(qry).addScalar("cashAwardAmount", Hibernate.STRING).list();
			if(amount.size() != 0){
				resultDTO.setCashAwardAmount((String)amount.get(0)) ;
			}
			//for adding pass or fails flag
			int passPercentage=((BigDecimal)session.createSQLQuery("SELECT pass_percentage_marks FROM HINDI_EXAMCONFIG_MASTER CFG where cfg.status=1 and cfg.id="+resultDTO.getExamId()).uniqueResult()).intValue();
			if(resultDTO.getMarksPercentage()>=passPercentage){
				resultDTO.setPassOrFail("P");
			}else{
				resultDTO.setPassOrFail("F");
			}
			
			if(resultDTO.getId() !=0){
			session.saveOrUpdate(resultDTO);
			message = CPSConstants.UPDATE;
			}else{
				session.saveOrUpdate(resultDTO);
				message = CPSConstants.SUCCESS;
				}
			
		}
		catch(Exception e)
		{
			message = CPSConstants.FAILED;
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return message;
	}
	@SuppressWarnings("unchecked")
	public List resultDuplicateCheckList(HindiIncentiveBean hindiBean) throws Exception
	{
		List<HindiEmpResultDTO> resultDuplicateCheckList = null;
		Session session = null;
		try{
			session = hibernateUtils.getSession();
		    String qry = "select sfid as sfid,id as id from hindi_result_master where status=1 and sfid='"+hindiBean.getSfid()+"' and cfg_id="+hindiBean.getExamId();
		    resultDuplicateCheckList=session.createSQLQuery(qry).addScalar("sfid", Hibernate.STRING).addScalar("id", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(HindiEmpResultDTO.class)).list();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return resultDuplicateCheckList;
	}
	
	@SuppressWarnings("unchecked")
	public List getResultDetails() throws Exception
	{
		Session session = null;
		List<HindiEmpResultDTO> resultlist = null;
		try{
			session = hibernateUtils.getSession();
			//resultlist = session.createCriteria(HindiEmpResultDTO.class).add(Expression.eq(CPSConstants.STATUS, "1")).list();
			String qry="SELECT HR.ID as id,HR.SFID as sfid,HR.TOTAL_MARKS as totalMarks,HR.MARKS_PERCENTAGE as marksPercentage," +
					" HR.EXAMINATION_DATE as examDate,HR.RESULT_DECLARED_DATE as resultDate,HR.CFG_ID as examId," +
					"HEM.EXAM_NAME as name FROM HINDI_RESULT_MASTER hr,HINDI_EXAMCONFIG_MASTER cfg,HINDI_EXAM_MASTER hem where HR.CFG_ID=cfg.id " +
					" and CFG.EXAM_ID=HEM.EXAM_ID";
			resultlist = session.createSQLQuery(qry).addScalar("id", Hibernate.INTEGER).addScalar("sfid", Hibernate.STRING).addScalar("totalMarks", Hibernate.INTEGER).addScalar("marksPercentage", Hibernate.INTEGER).addScalar("examDate", Hibernate.DATE).addScalar("resultDate", Hibernate.DATE).addScalar("examId", Hibernate.INTEGER).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(HindiEmpResultDTO.class)).list();
	     }
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return resultlist;
	}
	public String deleteResultDetails(HindiIncentiveBean hindiBean) throws Exception
	{
		Session session = null;
		String message = null;
		try{
			session = hibernateUtils.getSession();
			session.createQuery("update HindiEmpResultDTO set status=0 where id="+hindiBean.getId()).executeUpdate();
			message = CPSConstants.DELETE;
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
	    }
		return message;
	}
   @SuppressWarnings("unchecked")
public List getEligibleExamsList(HindiIncentiveBean hindiBean) throws Exception
   {
	   List<KeyValueDTO> eligibleExamslist = null;
	   Session session = null;
	   try{
		   session = hibernateUtils.getSession();
		   
		  String qry ="Select Distinct Em.Exam_Name As Key,Cfg.Id As Value " +
		   		" From Hindi_Exam_Master Em,Hindi_Examconfig_Master Cfg,hindi_employee_master hem Where  Cfg.Exam_Id = Em.Exam_Id " +
		   		" and Cfg.status=1 and em.status=1 and hem.status=1  And Cfg.Exam_Id In(Select elg.Eligible_Exam_Id " +
		   		" From Hindi_eligible_Exam_Details  elg " +
		   		" Where Status=1 And elg.Sfid='"+hindiBean.getSfid()+"')And Cfg.Category_Id In  (Select Dmp.Category_Id " +
		   		" From Emp_Master Emp,Designation_Master Dm,Designation_Mappings Dmp,Hindi_Employee_Master Hem " +
		   		" where emp.designation_id = dm.id and emp.status=1 and dm.status=1 and emp.sfid=hem.sfid and dm.id = dmp.desig_id and emp.sfid='"+hindiBean.getSfid()+"')";
		   
		   eligibleExamslist = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
	   }
	   catch(Exception e)
	   {
		 e.printStackTrace();  
		 throw e;
	   }
	   return eligibleExamslist;
   }
   
   @SuppressWarnings("unchecked")
   public List getCheckExamsList(HindiIncentiveBean hindiBean) throws Exception
      {
   	   List<KeyValueDTO> checkExamslist = null;
   	   Session session = null;
   	   try{
   		   session = hibernateUtils.getSession();
   		  /* String qry =" Select Hem.Exam_Name as key,Cfg.Exam_Id as value From Hindi_Examconfig_Master Cfg,Hindi_Exam_Master Hem" + 		   		
   		   		       " Where Cfg.Status=1 And Hem.Status=1 And Cfg.Exam_Id=Hem.Exam_Id And Category_Id In" +
   		   		       "(Select Cm.Id From Category_Master Cm,Emp_Master Emp,Designation_Mappings Dm Where" +
   		   		       " Cm.Status=1 And Emp.Status=1 And Emp.Designation_Id=Dm.Desig_Id And Dm.Category_Id=Cm.Id " +
   		   		       " And Emp.Sfid='"+hindiBean.getSfid()+"') group by Cfg.Exam_Id,Hem.Exam_Name";
   		   checkExamslist = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).addScalar("id", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();		       
   		   		       */
   /*	String qry = "Select Hem.Exam_Name As Key,Cfg.Exam_Id As Value,Cfg.Mother_Tongue as id From Hindi_Examconfig_Master Cfg,Hindi_Exam_Master Hem," +
   			"hindi_employee_master hemp Where Cfg.Status=1 And Hem.Status=1 And Cfg.Exam_Id=Hem.Exam_Id And cfg.Category_Id In" +
   			" (Select Cm.Id From Category_Master Cm,Emp_Master Emp,Designation_Mappings Dm Where Cm.Status=1 And Emp.Status=1 And " +
   			" Emp.Designation_Id=Dm.Desig_Id And Dm.Category_Id=Cm.Id And Emp.Sfid='"+hindiBean.getSfid()+"') group by Hem.Exam_Name,Cfg.Exam_Id,Cfg.Mother_Tongue"; */
   		   
   
   		//List<Integer> temp = session.createSQLQuery(qry).addScalar("id", Hibernate.INTEGER).list();
   		/*if(temp.size()>0){
   			for(int i=0;i<temp.size();i++){
   				if(temp.get(i)==1){
   					String qry1 = "Select Hem.Exam_Name As Key,Cfg.Exam_Id As Value,Cfg.Mother_Tongue From Hindi_Examconfig_Master Cfg," +
   							"Hindi_Exam_Master Hem,Hindi_Employee_Master Hemp Where Cfg.Status=1 And Hem.Status=1 And Cfg.Exam_Id=Hem.Exam_Id" +
   							" And cfg.Category_Id In (Select Cm.Id From Category_Master Cm,Emp_Master Emp,Designation_Mappings Dm Where" +
   							"Cm.Status=1 And Emp.Status=1 And Emp.Designation_Id=Dm.Desig_Id And Dm.Category_Id=Cm.Id And " +
   							"Emp.Sfid='"+hindiBean.getSfid()+"')And Cfg.Exam_Id In (Select Exam_Id From Hindi_Examconfig_Master Where Status=1 And " +
   							"mother_tongue=1)Group By Cfg.Exam_Id,Hem.Exam_Name,Cfg.Mother_Tongue";
   				}
   				else if(temp.get(i)==2){
   					
   				}
   				
   			}
   		}*/
   		   
   		String qry = " select mother_tongue from emp_master where status=1 and sfid='"+hindiBean.getSfid()+"'";
   		String motherTongue = (String)session.createSQLQuery(qry).uniqueResult();
   		String temp="SELECT dm.type FROM Category_Master Cm,Emp_Master Emp,Designation_Mappings Dm WHERE Cm.Status=1 AND Emp.Status=1 " +
   				      " AND Emp.Designation_Id=Dm.Desig_Id AND Dm.Category_Id=Cm.Id AND Emp.Sfid='"+hindiBean.getSfid()+"'";
   		String empType=(String)session.createSQLQuery(temp).uniqueResult();
   		
   		if(motherTongue != null && motherTongue !=""){
   			if(!motherTongue.trim().equalsIgnoreCase("hindi")){
   				StringBuffer qry1=new StringBuffer();
   				String st1 = "Select Hem.Exam_Name As Key,Cfg.Exam_Id As value From Hindi_Examconfig_Master Cfg," +
					" Hindi_Exam_Master Hem,Hindi_Employee_Master Hemp Where Cfg.Status=1 And Hem.Status=1 And Cfg.Exam_Id=Hem.Exam_Id" +
					" And cfg.Category_Id In (Select Cm.Id From Category_Master Cm,Emp_Master Emp,Designation_Mappings Dm Where" +
					" Cm.Status=1 And Emp.Status=1 And Emp.Designation_Id=Dm.Desig_Id And Dm.Category_Id=Cm.Id And " +
					" Emp.Sfid='"+hindiBean.getSfid()+"')And Cfg.Exam_Id In (Select Exam_Id From Hindi_Examconfig_Master Where Status=1 And " +
					" mother_tongue in(1,2))";
   				qry1=qry1.append(st1);
   				
   				if(empType.equals("GAZETTED")){
   					qry1=qry1.append("  and cfg.emp_type=1");
   				}
   				else if(empType.equals("NON GAZETTED")){
   					qry1=qry1.append("  and cfg.emp_type=2");
   				}
   				qry1=qry1.append(" Group By Cfg.Exam_Id,Hem.Exam_Name,Cfg.Mother_Tongue");
   				
   				checkExamslist = session.createSQLQuery(qry1.toString()).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
   				return checkExamslist;
   				
   			}else if(motherTongue.trim().equalsIgnoreCase("hindi")){
   				StringBuffer qry2=new StringBuffer();
   				String st2 = "Select Hem.Exam_Name As Key,Cfg.Exam_Id As value From Hindi_Examconfig_Master Cfg," +
				" Hindi_Exam_Master Hem,Hindi_Employee_Master Hemp Where Cfg.Status=1 And Hem.Status=1 And Cfg.Exam_Id=Hem.Exam_Id" +
				" And cfg.Category_Id In (Select Cm.Id From Category_Master Cm,Emp_Master Emp,Designation_Mappings Dm Where" +
				" Cm.Status=1 And Emp.Status=1 And Emp.Designation_Id=Dm.Desig_Id And Dm.Category_Id=Cm.Id And " +
				" Emp.Sfid='"+hindiBean.getSfid()+"')And Cfg.Exam_Id In (Select Exam_Id From Hindi_Examconfig_Master Where Status=1 And " +
				" mother_tongue=2)";
   				
                qry2=qry2.append(st2);
   				
   				if(empType.equals("GAZETTED")){
   					qry2=qry2.append("  and cfg.emp_type=1");
   				}
   				else if(empType.equals("NON GAZETTED")){
   					qry2=qry2.append("  and cfg.emp_type=2");
   				}
   				qry2=qry2.append(" Group By Cfg.Exam_Id,Hem.Exam_Name,Cfg.Mother_Tongue");
   				checkExamslist = session.createSQLQuery(qry2.toString()).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
   				return checkExamslist;
   			}
   		}
   		
   		
   		
   	   }
   	   catch(Exception e)
   	   {
   		 e.printStackTrace();  
   		 throw e;
   	   }
   	   return checkExamslist;
      }

   public String saveIncentiveDetails(HindiIncentiveDetailsDTO incentiveDTO) throws Exception
   {
	   Session session = null;
	   String message = null;
	   try
	   {
		   session = hibernateUtils.getSession();
		   if(incentiveDTO.getId() != 0)
		   {
			   session.saveOrUpdate(incentiveDTO);
			   message = CPSConstants.UPDATE;
		   }
		   else
		   {
			   session.saveOrUpdate(incentiveDTO);
			   message = CPSConstants.SUCCESS;
		   }
		}catch(Exception e)
		{
			message = CPSConstants.FAILED;
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return message;
   }

@SuppressWarnings("unchecked")
public List<HindiIncentiveDetailsDTO> getIncentiveDetails(HindiIncentiveBean hindiBean) throws Exception
{
	Session session = null;
	List<HindiIncentiveDetailsDTO> incentiveDetailsList = null;
	try
	{
		session = hibernateUtils.getSession();			
		//incentiveDetailsList = session.createCriteria(HindiIncentiveDetailsDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).list();
	    /* String qry = "select sfid as sfid,id as id,total_amount as totalAmount,cash_award_amount as cashAwardAmount,tot_inst as noOfInst,present_inst as presentInst,effective_date as fromDate,creation_date as creationDate,created_by as createdBy from pay_hindi_inc_details where status=1 and sfid='"+ hindiBean.getSfidSearch().toUpperCase()+"'";
	       incentiveDetailsList = session.createSQLQuery(qry).addScalar("sfid", Hibernate.STRING).addScalar("id", Hibernate.INTEGER).addScalar("totalAmount", Hibernate.FLOAT).addScalar("cashAwardAmount", Hibernate.FLOAT).addScalar("noOfInst", Hibernate.INTEGER).addScalar("presentInst", Hibernate.INTEGER).addScalar("fromDate", Hibernate.DATE).addScalar("creationDate", Hibernate.STRING).addScalar("createdBy", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(HindiIncentiveDetailsDTO.class)).list();*/
		String qry = "select sfid as sfid,id as id,total_amount as totalAmount,tot_inst as noOfInst,present_inst as presentInst,FROM_MONTH as fromDate,creation_date as creationDate,created_by as createdBy from pay_hindi_inc_details where status=1 and sfid='"+ hindiBean.getSfidSearch().toUpperCase()+"'";
		incentiveDetailsList = session.createSQLQuery(qry).addScalar("sfid", Hibernate.STRING).addScalar("id", Hibernate.INTEGER).addScalar("totalAmount", Hibernate.FLOAT).addScalar("noOfInst", Hibernate.INTEGER).addScalar("presentInst", Hibernate.INTEGER).addScalar("fromDate", Hibernate.DATE).addScalar("creationDate", Hibernate.STRING).addScalar("createdBy", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(HindiIncentiveDetailsDTO.class)).list();	
	}catch(Exception e) {
		throw e;
	}
	return incentiveDetailsList;
}
   
@SuppressWarnings("unchecked")
public List<HindiIncentiveDetailsDTO> incentiveDuplicateCheckList(HindiIncentiveBean hindiBean) throws Exception
{
	List<HindiIncentiveDetailsDTO> incentiveDuplicateCheckList = null;
	Session session = null;
	try
	{
		session = hibernateUtils.getSession();
		incentiveDuplicateCheckList = session.createQuery("select sfid from HindiIncentiveDetailsDTO where status = 1 and sfid = '"+ hindiBean.getSfidSearch().toUpperCase() +"'").list();
	}catch(Exception e) {
		throw e;
	}
	return incentiveDuplicateCheckList;
}

   public String deleteIncentiveDetails(HindiIncentiveBean hindiBean) throws Exception
   {
	   Session session = null;
		String message = null;
		try{
			session = hibernateUtils.getSession();
			session.createQuery("update HindiIncentiveDetailsDTO set status=0 where id="+hindiBean.getId()).executeUpdate();
			message = CPSConstants.DELETE;
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
	    }
		return message;
   }
   
@SuppressWarnings("unchecked")
public List<EmpKeyValueDTO> getIncentiveEmpList(HindiIncentiveBean hindiBean) throws Exception
{
	Session session = null;
	List<EmpKeyValueDTO> incentiveEmpList = null;
	try
	{
		session = hibernateUtils.getSession();
		String qry = "select emp.sfid as id, emp.name_in_service_book as name, des.name as designation, dpt.department_name as department "
				+ "from EMP_MASTER emp, ORG_ROLE_INSTANCE org, DESIGNATION_MASTER des,DEPARTMENTS_MASTER dpt where emp.designation_id = des.id "
				+ "and emp.office_id = org.org_role_id and Org.department_id = dpt.department_id and emp.status = 1 and emp.sfid = '"+ hindiBean.getSfidSearch().toUpperCase() +"'";
		incentiveEmpList = session.createSQLQuery(qry).addScalar("id", Hibernate.STRING).addScalar("name", Hibernate.STRING).
				addScalar("designation", Hibernate.STRING).addScalar("department", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(EmpKeyValueDTO.class)).list();
	}catch(Exception e) {
		throw e;
	}
	return incentiveEmpList;
}
   
@SuppressWarnings("unchecked")
public List getEligibleEmpForIncentives(HindiIncentiveBean hindiBean) throws Exception
{
	Session session = null;
	List<KeyValueDTO> elgEmpList = null;
	try
	{
		session = hibernateUtils.getSession();
		String qry = "select trim(' ' from mother_tongue) from emp_master where status = 1 and sfid = '"+ hindiBean.getSfidSearch().toUpperCase() +"'";
		List<String> language= session.createSQLQuery(qry).list();
		String motherTongue = language.get(0);
		//AFTER SAVE CFGID IN HINDI_RESULT_MASTER===KUMARI
		/* String sql = "Select Hrm.Sfid As Sfid,Hrm.Exam_Id As Id From Hindi_Result_Master Hrm,Hindi_Examconfig_Master Cfg,Emp_Master Emp " +
		  		"Where Hrm.Status=1 And Cfg.Status=1 And Emp.Status=1 And Emp.Sfid='"+hindiBean.getSfidSearch().toUpperCase()+"' and " +
		  		"('"+motherTongue+"' !=lower('HINDI') and '"+motherTongue+"' !=upper('HINDI') and '"+motherTongue+"' !=initcap('HINDI') )"+
		  		"And Hrm.Exam_Id=Cfg.Exam_Id And Hrm.Exam_Id In (Select Exam_Id From Hindi_Result_Master Where Status=1 And " +
		  		"Sfid='"+hindiBean.getSfidSearch().toUpperCase()+"') And Emp.Sfid=Hrm.Sfid And Cfg.Emp_Type=" +
		  		"(Select Case When (Select Dm.Type From Designation_Mappings Dm Where Dm.Desig_Id=(Select Designation_Id From Emp_Master Where " +
		  		"Status=1 And Sfid='"+hindiBean.getSfidSearch().toUpperCase()+"'))='GAZETTED' Then 1 Else 2 End AS TYPE FROM DUAL) And " +
		  		"hrm.marks_percentage >= cfg.pass_percentage_marks group by  Hrm.Sfid,hrm.exam_id"; */
		String sql = "SELECT Hrm.Sfid AS sfid, CFG.Exam_Id AS id FROM Hindi_Result_Master Hrm, Hindi_Examconfig_Master Cfg WHERE Hrm.Status = 1" +
		  		" AND Cfg.Status = 1 AND ('Telugu' != lower('HINDI') AND 'Telugu' != upper('HINDI') and 'Telugu' != initcap('HINDI')) and " +
		  		" hrm.cfg_id = cfg.id AND hrm.pass_or_fail = 'P' and hrm.sfid = '"+ hindiBean.getSfidSearch().toUpperCase() +"' group by hrm.sfid, CFG.exam_id";
		   elgEmpList = session.createSQLQuery(sql).addScalar("sfid", Hibernate.STRING).addScalar("id", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(HindiIncentiveDetailsDTO.class)).list();
	   }catch (Exception e) {
		   throw e;
	   }
	   return elgEmpList;
   }
   @SuppressWarnings("unchecked")
   public List getIncentiveEmpListForFinance(HindiIncentiveBean hindiBean) throws Exception{
   	   Session session = null;
   	   List<HindiEmpResultDTO> elgEmpListForFinanace = null;
   	   try{
   		   session = hibernateUtils.getSession();
   		   String qry="SELECT HR.id AS id,HR.SFID AS sfid,HEM.EXAM_NAME AS name,HR.MARKS_PERCENTAGE AS marksPercentage,HR.PASS_OR_FAIL AS " +
   		   		" passOrFail,HR.EXAMINATION_DATE AS examDate,HR.RESULT_DECLARED_DATE AS resultDate FROM HINDI_RESULT_MASTER HR," +
   		   		"HINDI_EXAM_MASTER HEM,HINDI_EXAMCONFIG_MASTER cfg WHERE HR.STATUS=1 AND HEM.STATUS=1 and CFG.STATUS=1 AND HR.CFG_ID=CFG.ID" +
   		   		" and cfg.exam_id=HEM.EXAM_ID AND HR.PASS_OR_FAIL='P' and cfg.increment_applicable=1";
   		elgEmpListForFinanace=session.createSQLQuery(qry).addScalar("id", Hibernate.INTEGER).addScalar("sfid", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("marksPercentage", Hibernate.FLOAT).addScalar("passOrFail", Hibernate.STRING).addScalar("examDate", Hibernate.DATE).addScalar("resultDate", Hibernate.DATE).setResultTransformer(Transformers.aliasToBean(HindiEmpResultDTO.class)).list();
   	   }catch (Exception e) {
   		e.printStackTrace();
   		throw e;
   	}
   	   return elgEmpListForFinanace;
      }
   @SuppressWarnings("unchecked")
   public String savePayHindiIncentiveDetails(HindiIncentiveBean hindiBean) throws Exception{
   	   Session session = null;
   	String message="";
   	SimpleDateFormat sd=new SimpleDateFormat("dd-MMM-yyyy");
   	   try{
   		   session = hibernateUtils.getSession();
   		  String[] resultIds=hindiBean.getResultIds().split(",");
   		  List<Integer> idList=new ArrayList<Integer>();
   		  for(int i=0;i<resultIds.length;i++){
   			idList.add(Integer.parseInt(resultIds[i]));
   		  }
   			  
   			
   			List<HindiEmpResultDTO> hindiEmpResultList=session.createCriteria(HindiEmpResultDTO.class).add(Expression.in("id", idList)).list();
   			for(int i=0;i<hindiEmpResultList.size();i++){
   				HindiIncentiveDetailsDTO hindiIncentiveDetailsDTO =new HindiIncentiveDetailsDTO();
   				HindiEmpResultDTO hindiEmpResultDTO = hindiEmpResultList.get(i);
   				PayBillEmpPaymentDeatilsDTO payBillEmpPaymentDeatilsDTO=(PayBillEmpPaymentDeatilsDTO)session.createCriteria(PayBillEmpPaymentDeatilsDTO.class).add(Expression.eq("sfID", hindiEmpResultDTO.getSfid())).add(Expression.eq("status", 1)).uniqueResult();
   				int totInsts=((BigDecimal)session.createSQLQuery("select NO_OF_INCREMENTS from HINDI_EXAMCONFIG_MASTER where STATUS=1 and id="+hindiEmpResultDTO.getExamId()+"").uniqueResult()).intValue();
   				int totInstAmount=((BigDecimal)session.createSQLQuery("Select Calculate_hindiinc(?,?,?) From Dual").setString(0, hindiEmpResultDTO.getSfid()).setInteger(1, payBillEmpPaymentDeatilsDTO.getBasicPay()).setInteger(2, payBillEmpPaymentDeatilsDTO.getgPay()).uniqueResult()).intValue();
   				
   				
   				hindiIncentiveDetailsDTO.setSfid(hindiEmpResultDTO.getSfid());
   				hindiIncentiveDetailsDTO.setFromDate(sd.parse(hindiBean.getFromDate()));
   				hindiIncentiveDetailsDTO.setToDate(sd.parse(hindiBean.getToDate()));
   				hindiIncentiveDetailsDTO.setNoOfInst(totInsts);
   				hindiIncentiveDetailsDTO.setPresentInst(0);
   				hindiIncentiveDetailsDTO.setTotalAmount(totInstAmount);
   				hindiIncentiveDetailsDTO.setStatus(1);
   				hindiIncentiveDetailsDTO.setCreationDate(CPSUtils.getCurrentDate());
   				hindiIncentiveDetailsDTO.setCreatedBy(hindiBean.getLoginSfid());
   				hindiIncentiveDetailsDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
   				hindiIncentiveDetailsDTO.setLastModifiedBy(hindiBean.getLoginSfid());
   				session.saveOrUpdate(hindiIncentiveDetailsDTO);
   			}
   		  
   		session.createSQLQuery("update HINDI_RESULT_MASTER set status=2 where id IN(:idList)").setParameterList("idList", idList).executeUpdate();
   		
   		  message=CPSConstants.SUCCESS;
   	   }catch (Exception e) {
   		message=CPSConstants.FAIL; 
   		e.printStackTrace();
   		throw e;
   	}
   	   return message;
      }
   
}

