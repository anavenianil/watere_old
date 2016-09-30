package com.callippus.web.dao.increment;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.transform.Transformers;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.CasualityDetailsDTO;
import com.callippus.web.beans.dto.DoPartDTO;
import com.callippus.web.beans.dto.DoPartSerialNoDTO;
import com.callippus.web.beans.dto.EmpBasicPayHistoryDTO;
import com.callippus.web.beans.dto.YearTypeDTO;
import com.callippus.web.beans.increment.AnnualIncrementBean;
import com.callippus.web.beans.increment.AnnualIncrementDTO;
import com.callippus.web.beans.requests.DoPartBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;

@Service
public class SQLAnnualIncrementDAO implements IAnnualIncrementDAO{

	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	
	@SuppressWarnings("unchecked")
	public AnnualIncrementBean getEmpDetails(AnnualIncrementBean annualIncrementBean)
			throws Exception {
		Session session = null;
		try{
			// commit transaction & close the session
			//hibernateUtils.closeSession();
			session = hibernateUtils.getSession();
			session.flush();
			annualIncrementBean.setEmpDetails(session.createSQLQuery("select em.sfid userSfid,upper(em.name_in_service_book) name,dlo.desigdescription designationName,epd.basic_pay basicPay,epd.grade_pay gradePay,(epd.basic_pay+epd.grade_pay) basicGradeTotal,(epd.basic_pay+epd.grade_pay)*0.03 increment3,(floor(floor((epd.basic_pay+epd.grade_pay)*0.03)/10)*10)+(ceil(mod(floor((epd.basic_pay+epd.grade_pay)*0.03),10)/10)*10) incRoundOff ,least((epd.basic_pay+(floor(floor((epd.basic_pay+epd.grade_pay)*0.03)/10)*10)+(ceil(mod(floor((epd.basic_pay+epd.grade_pay)*0.03),10)/10)*10)),(get_payband_torange(em.designation_id))) incrementBasicPay from emp_master em,emp_payment_details epd,designation_list_in_order dlo where em.sfid=epd.sfid and em.status=1  and dlo.id=em.designation_id and em.working_location in (select working_location from emp_master where status=1 and sfid=?) and dlo.id in(SELECT DESIGNATION_ID FROM DOPART_II_TYPE_DESIG_MASTER WHERE STATUS=1 AND ID IN(SELECT TYPE_DESIG_ID FROM DOPART_II_TH_DESIGNATIONS WHERE STATUS=1 and type_id=? AND ROLE_ID IN(select id from DOPART_II_TASK_HOLDER where ROLE_ID=(SELECT ORG_ROLE_ID FROM EMP_ROLE_MAPPING WHERE SFID=? AND STATUS=1 AND ORG_ROLE_ID IN(select role_id from DOPART_II_TASK_HOLDER where status=1 and type_id=?))))) and em.sfid not in(select SFID from ANNUAL_INCREMENT_DETAILS where INCREMENT_YEAR_ID=(select id from YEAR_MASTER where name=(select to_char(sysdate,'YYYY') from dual))) order by dlo.category_order,dlo.sub_category_order, dlo.designation_order,em.sfid")
			.addScalar("userSfid", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("designationName", Hibernate.STRING).addScalar("basicPay", Hibernate.FLOAT).addScalar("gradePay", Hibernate.FLOAT).addScalar("basicGradeTotal", Hibernate.STRING).addScalar("increment3", Hibernate.FLOAT).addScalar("incRoundOff", Hibernate.FLOAT).addScalar("incrementBasicPay", Hibernate.FLOAT).setResultTransformer(Transformers.aliasToBean(AnnualIncrementDTO.class)).setString(0, annualIncrementBean.getSfID()).setString(1, annualIncrementBean.getGazettedType()).setString(2, annualIncrementBean.getSfID()).setString(3, annualIncrementBean.getGazettedType()).list());  // working location of login sfid is called
		
			Object roleId=session.createSQLQuery("select distinct dotask.role_id from dopart_ii_task_holder dotask,emp_role_mapping emprole where dotask.role_id=emprole.org_role_id and emprole.status=1 and dotask.status=1 and emprole.sfid='"+annualIncrementBean.getSfID()+"'").uniqueResult();
			if(!CPSUtils.isNullOrEmpty(roleId)){
				annualIncrementBean.setRoleId(((BigDecimal)roleId).intValue());
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return annualIncrementBean;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public AnnualIncrementBean submitIncrementDetails(AnnualIncrementBean annualIncrementBean) throws Exception {
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			annualIncrementBean.setEmpDetails(session.createSQLQuery("select em.sfid userSfid,upper(em.name_in_service_book) name,dm.description designationName,epd.basic_pay basicPay,epd.grade_pay gradePay,(epd.basic_pay+epd.grade_pay)*0.03 increment3,(floor(floor((epd.basic_pay+epd.grade_pay)*0.03)/10)*10)+(ceil(mod(floor((epd.basic_pay+epd.grade_pay)*0.03),10)/10)*10) incRoundOff ,least((epd.basic_pay+(floor(floor((epd.basic_pay+epd.grade_pay)*0.03)/10)*10)+(ceil(mod(floor((epd.basic_pay+epd.grade_pay)*0.03),10)/10)*10)),(get_payband_torange(em.designation_id))) incrementBasicPay from emp_master em,emp_payment_details epd,designation_master dm where em.sfid=epd.sfid and em.status=1  and dm.status=1 and dm.id=em.designation_id  and dm.id in(SELECT DESIGNATION_ID FROM DOPART_II_TYPE_DESIG_MASTER WHERE STATUS=1 AND ID IN(SELECT TYPE_DESIG_ID FROM DOPART_II_TH_DESIGNATIONS WHERE STATUS=1 and type_id=? AND ROLE_ID IN(select id from DOPART_II_TASK_HOLDER where ROLE_ID=(SELECT ORG_ROLE_ID FROM EMP_ROLE_MAPPING WHERE SFID=? AND STATUS=1 AND ORG_ROLE_ID IN(select role_id from DOPART_II_TASK_HOLDER where status=1 and type_id=?))))) and em.sfid in("+(annualIncrementBean.getSfIds().substring(0,annualIncrementBean.getSfIds().length()-1))+") order by dm.id").addScalar("userSfid", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("designationName", Hibernate.STRING).addScalar("basicPay", Hibernate.FLOAT).addScalar("gradePay", Hibernate.FLOAT).addScalar("increment3", Hibernate.FLOAT).addScalar("incRoundOff", Hibernate.FLOAT).addScalar("incrementBasicPay", Hibernate.FLOAT).setResultTransformer(Transformers.aliasToBean(AnnualIncrementDTO.class)).setString(0, annualIncrementBean.getGazettedType()).setString(1, annualIncrementBean.getSfID()).setString(2, annualIncrementBean.getGazettedType()).list());
			for(int i=0;i<annualIncrementBean.getEmpDetails().size();i++){
				
				/*AnnualIncrementDTO annualIncrementDTO=annualIncrementBean.getEmpDetails().get(i);
				if(Float.valueOf(annualIncrementDTO.getBasicPay())+ Float.valueOf(annualIncrementDTO.getIncRoundOff())>empPayBand){
				annualIncrementDTO.setIncrementBasicPay(Float.valueOf("80000"));
				}else{
				annualIncrementDTO.setIncrementBasicPay(Float.valueOf(annualIncrementDTO.getBasicPay())+ Float.valueOf(annualIncrementDTO.getIncRoundOff()));
				}*/
				AnnualIncrementDTO annualIncrementDTO=annualIncrementBean.getEmpDetails().get(i);
				
				//new basic pay is incremented from query
				annualIncrementDTO.setIncrementBasicPay(annualIncrementDTO.getIncrementBasicPay());
				annualIncrementDTO.setIncrementGradePay(annualIncrementDTO.getGradePay());
				annualIncrementDTO.setGazettedType(Integer.valueOf(annualIncrementBean.getGazettedType()));
				annualIncrementDTO.setDoPartNo(Integer.valueOf(annualIncrementBean.getDoPartNo().split("#")[0]));
				annualIncrementDTO.setCasualityId(Integer.valueOf(annualIncrementBean.getCasualityId()));
				annualIncrementDTO.setIncrementYearId(((YearTypeDTO)session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.NAME, String.valueOf(CPSUtils.getCurrentDateWithTime().getYear()+1900))).uniqueResult()).getId());
				annualIncrementDTO.setCreatedBy(annualIncrementBean.getSfID());
				annualIncrementDTO.setLastModifiedBy(annualIncrementBean.getSfID());
				annualIncrementDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
				annualIncrementDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				annualIncrementDTO.setAdminEffectDate(CPSUtils.convertStringToDate(annualIncrementBean.getAdminEffectDateString()));
				annualIncrementDTO.setStatus(1);
				annualIncrementBean.setDesignationId(((BigDecimal)session.createSQLQuery("select em.designation_id as designationId from emp_master em where em.sfid ='"+annualIncrementDTO.getUserSfid()+"' and em.status=1").uniqueResult()).intValue());
				annualIncrementDTO.setDesignationId(annualIncrementBean.getDesignationId()); // to set empmaster designationId
				session.saveOrUpdate(annualIncrementDTO);
				session.flush();
				
				DoPartSerialNoDTO dopartSerialNoDTO=new DoPartSerialNoDTO();
				dopartSerialNoDTO.setRefDoPartId(Integer.valueOf(annualIncrementBean.getDoPartNo().split("#")[0]));
				dopartSerialNoDTO.setModuleId(Integer.valueOf(CPSConstants.ANNUALINCREMENTMODULEID));
				dopartSerialNoDTO.setCasualityId(Integer.valueOf(annualIncrementBean.getCasualityId()));
				dopartSerialNoDTO.setSlNo("1");
				dopartSerialNoDTO.setRequestId(String.valueOf(annualIncrementDTO.getId()));
				dopartSerialNoDTO.setStatus(1);
				dopartSerialNoDTO.setCreationDate(CPSUtils.getCurrentDate());
				dopartSerialNoDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				dopartSerialNoDTO.setCreatedBy(annualIncrementBean.getSfID());
				dopartSerialNoDTO.setLastModifiedBy(annualIncrementBean.getSfID());
				session.save(dopartSerialNoDTO);
				session.flush();
				
				/*EmpBasicPayHistoryDTO empBasicPayHistoryDTO=(EmpBasicPayHistoryDTO)session.createCriteria(EmpBasicPayHistoryDTO.class).add(Expression.eq(CPSConstants.SFID,annualIncrementDTO.getUserSfid())).add(Expression.eq(CPSConstants.STATUS,1)).add(Expression.eq("presentEffectiveDate", session.createCriteria(EmpBasicPayHistoryDTO.class).add(Expression.eq(CPSConstants.SFID,annualIncrementDTO.getUserSfid())).add(Expression.eq(CPSConstants.STATUS,1)).setProjection(Projections.max("presentEffectiveDate")).uniqueResult())).uniqueResult();
				EmpBasicPayHistoryDTO empIncrementBasic=new EmpBasicPayHistoryDTO();
				BeanUtils.copyProperties(empBasicPayHistoryDTO, empIncrementBasic);
				empBasicPayHistoryDTO.setStatus(100);
				empBasicPayHistoryDTO.setPresentEffectiveDate(CPSUtils.convertStringToDate(CPSUtils.formattedDate(empBasicPayHistoryDTO.getPresentEffectiveDate().toString())));
				empBasicPayHistoryDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				session.update(empBasicPayHistoryDTO);
				session.flush();
				empIncrementBasic.setSfid(empBasicPayHistoryDTO.getSfid());
				empIncrementBasic.setBasicPay(annualIncrementDTO.getIncrementBasicPay());
				empIncrementBasic.setIncrementType("A");
				empIncrementBasic.setIncrementValue(annualIncrementDTO.getIncRoundOff());
				empIncrementBasic.setReferenceType("ONLINE");
				empIncrementBasic.setDesignationId(empBasicPayHistoryDTO.getDesignationId());
				empIncrementBasic.setStatus(1);
				empIncrementBasic.setPresentEffectiveDate(CPSUtils.convertStringToDate(annualIncrementBean.getAdminEffectDateString()));
			    empIncrementBasic.setLastModifiedDate(CPSUtils.getCurrentDate());
				empIncrementBasic.setCreationDate(CPSUtils.getCurrentDate());
				session.save(empIncrementBasic);
				session.flush();*/
				
				/*List<PromotionOfflineEntryBean> offlineList = session.createSQLQuery("Select Emp.Sfid ,DESIG.NAME designationFrom,Prpm.Designation_to designationTo,Pad.Effective_date effectiveDate From Pro_assessment_details Pad,Emp_master Emp,Pro_residency_period_master Prpm,Designation_master Desig WHERE EMP.SFID=PAD.SFID AND TO_CHAR(PAD.EFFECTIVE_DATE)=TO_DATE(SYSDATE) AND EMP.DESIGNATION_ID=DESIG.ID AND DESIG.ID=PRPM.DESIGNATION_FROM and emp.status=1 and prpm.status=1 and emp.sfid='"+empIncrementBasic.getSfid()+"' order by to_date(PAD.EFFECTIVE_DATE) desc").addScalar("id", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("age", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(FamilyBean.class)).list();
				if(offlineList.size()!=0){
					PromotionOfflineEntryBean eachRow = (PromotionOfflineEntryBean) offlineList.get(0);
					int j = session.createQuery("update EmployeeBean set designation=?,seniorityDate=?,lastModifiedDate=sysdate where userSfid=?").setInteger(0, eachRow.getDesignationTo()).setDate(1,eachRow.getEffectiveDate()).setString(2,eachRow.getSfid()).executeUpdate();
					
				}*/
			annualIncrementBean.setResult("Save Record Successfully");
			}
		}catch(Exception e){
			throw e;
		}
		return annualIncrementBean;
	}

	@SuppressWarnings("unchecked")
	public AnnualIncrementBean getIncrementCasualityList(AnnualIncrementBean annualIncrementBean) throws Exception {
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			annualIncrementBean.setCasualitiesList(session.createCriteria(CasualityDetailsDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("moduleId", Integer.valueOf(CPSConstants.ANNUALINCREMENTMODULEID))).add(Expression.eq("typeId", Integer.parseInt(annualIncrementBean.getGazettedType()))).addOrder(Order.asc("orderBy")).list());	
		}catch(Exception e){
			throw e;
		}
		return annualIncrementBean;
		
	}

	@SuppressWarnings("unchecked")
	public AnnualIncrementBean getDoPartList(AnnualIncrementBean annualIncrementBean) throws Exception {
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			annualIncrementBean.setDoPartList(session.createCriteria(DoPartDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("typeId", Integer.parseInt(annualIncrementBean.getGazettedType()))).addOrder(Order.desc("doPartDate")).list());
		}catch(Exception e){
			throw e;
		}
		return annualIncrementBean;
	}

	@SuppressWarnings("unchecked")
	public AnnualIncrementBean getEmpIncrementDoPartDetails(AnnualIncrementBean annualIncrementBean) throws Exception {
		Session session = null;
		try{
			// commit transaction & close the session
			hibernateUtils.closeSession();
			session = hibernateUtils.getSession();
			//annualIncrementBean.setEmpDetails(session.createSQLQuery("select aid.sfid  userSfid,upper(em.name_in_service_book) name,dm.description designationName,aid.BASIC_PAY basicPay,aid.GRADE_PAY gradePay,AID.INCREMENT3 increment3,AID.INCREMENT_ROUND_OFF incRoundOff from ANNUAL_INCREMENT_DETAILS aid,EMP_MASTER em,DESIGNATION_MASTER dm where EM.SFID=AID.SFID and dm.id=EM.DESIGNATION_ID and INCREMENT_YEAR_ID=(select id from YEAR_MASTER where name=(select to_char(sysdate,'YYYY') from dual)) and DO_PART_NO ="+annualIncrementBean.getDoPartNo()+" and finance_accepted_date is null").addScalar("userSfid", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("designationName", Hibernate.STRING).addScalar("basicPay", Hibernate.FLOAT).addScalar("gradePay", Hibernate.FLOAT).addScalar("increment3", Hibernate.FLOAT).addScalar("incRoundOff", Hibernate.FLOAT).setResultTransformer(Transformers.aliasToBean(AnnualIncrementDTO.class)).list());
			annualIncrementBean.setEmpDetails(session.createSQLQuery("select aid.sfid  userSfid,upper(em.name_in_service_book) name,dlo.desigdescription designationName,aid.basic_pay basicPay,aid.grade_pay gradePay,aid.increment3 increment3,aid.increment_round_off incRoundOff,aid.increment_basic_pay incrementBasicPay,aid.increment_grade_pay incrementGradePay,(aid.basic_pay+aid.grade_pay) basicGradeTotal from annual_increment_details aid,emp_master em,designation_list_in_order dlo where EM.SFID=AID.SFID and dlo.id=em.designation_id and increment_year_id in (select distinct increment_year_id from annual_increment_details where do_part_no="+annualIncrementBean.getDoPartNo()+") and DO_PART_NO ="+annualIncrementBean.getDoPartNo()+" and finance_accepted_date is null order by dlo.category_order,dlo.sub_category_order, dlo.designation_order,em.sfid").addScalar("userSfid", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("designationName", Hibernate.STRING).addScalar("basicPay", Hibernate.FLOAT).addScalar("gradePay", Hibernate.FLOAT).addScalar("increment3", Hibernate.FLOAT).addScalar("incRoundOff", Hibernate.FLOAT).addScalar("incrementBasicPay", Hibernate.FLOAT).addScalar("incrementGradePay", Hibernate.FLOAT).addScalar("basicGradeTotal", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(AnnualIncrementDTO.class)).list());
		}catch(Exception e){
			throw e;
		}
		return annualIncrementBean;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public AnnualIncrementBean submitIncrementToPayDetails(AnnualIncrementBean annualIncrementBean) throws Exception {
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			String[] sfidList=annualIncrementBean.getSfIds().split(",");
			for(int i=0;i<sfidList.length;i++){
				String sfid = sfidList[i].replaceAll("'", "");
				AnnualIncrementDTO annualIncrementDTO=(AnnualIncrementDTO)session.createCriteria(AnnualIncrementDTO.class).add(Expression.eq("userSfid", sfid)).add(Expression.eq("status", 1)).add(Expression.eq("doPartNo", Integer.parseInt(annualIncrementBean.getDoPartNo()))).uniqueResult();
				annualIncrementDTO.setFinanceAcceptedDate(annualIncrementBean.getFinanceAcceptedDate());
				annualIncrementDTO.setLastModifiedBy(annualIncrementBean.getSfID());
				annualIncrementDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				session.update(annualIncrementDTO);
				session.flush();
				annualIncrementBean.setResult("Save Record Successflly");
			}
			//annualIncrementBean.setEmpDetails((List<AnnualIncrementDTO>) session.createCriteria(AnnualIncrementDTO.class).add(Expression.eq("incrementYearId", ((YearTypeDTO)session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.NAME, String.valueOf(CPSUtils.getCurrentDateWithTime().getYear()+1900))).uniqueResult()).getId())).add(Expression.in("userSfid", annualIncrementBean.getSfIds().substring(1,annualIncrementBean.getSfIds().length()-2).split("','"))).list());
			//for(int i=0;i<annualIncrementBean.getEmpDetails().size();i++){
				//AnnualIncrementDTO annualIncrementDTO=annualIncrementBean.getEmpDetails().get(i);
				//annualIncrementDTO.setFinanceAcceptedDate(annualIncrementBean.getFinanceAcceptedDate());
				//annualIncrementDTO.setLastModifiedBy(annualIncrementBean.getSfID());
				//annualIncrementDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				//session.saveOrUpdate(annualIncrementDTO);
//				EmpBasicPayHistoryDTO empBasicPayHistoryDTO=(EmpBasicPayHistoryDTO)session.createCriteria(EmpBasicPayHistoryDTO.class).add(Expression.eq(CPSConstants.SFID,annualIncrementDTO.getUserSfid())).add(Expression.eq(CPSConstants.STATUS,1)).add(Expression.eq("presentEffectiveDate", session.createCriteria(EmpBasicPayHistoryDTO.class).add(Expression.eq(CPSConstants.SFID,annualIncrementDTO.getUserSfid())).add(Expression.eq(CPSConstants.STATUS,1)).setProjection(Projections.max("presentEffectiveDate")).uniqueResult())).uniqueResult();
//				EmpBasicPayHistoryDTO empIncrementBasic=new EmpBasicPayHistoryDTO();
//				BeanUtils.copyProperties(empBasicPayHistoryDTO, empIncrementBasic);
//				empBasicPayHistoryDTO.setStatus(2);
//				session.update(empBasicPayHistoryDTO);
//				empIncrementBasic.setBasicPay(annualIncrementDTO.getIncrementBasicPay());
//				empIncrementBasic.setIncrementType("A");
//				empIncrementBasic.setIncrementValue(annualIncrementDTO.getIncRoundOff());
//				empIncrementBasic.setReferenceType("ONLINE");
//				empIncrementBasic.setStatus(1);
//				empIncrementBasic.setPresentEffectiveDate(annualIncrementDTO.getAdminEffectDate());
//				session.save(empIncrementBasic);
				//annualIncrementBean.setResult("Save Record Successflly");
			//}
		
		}catch(Exception e){
			throw e;
		}
		return annualIncrementBean;
	}
	//code for new screen
	@SuppressWarnings("unchecked")
	public AnnualIncrementBean getAnnualIncrementBasicPayIdDetails(AnnualIncrementBean annualIncrementBean)throws Exception{
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			annualIncrementBean.setBasicPayIdList(session.createSQLQuery("SELECT rnd.REF_NUMBER as doPartNo,rnd.REF_DATE as doPartDate1 ,rnd.ID as id ,rnd.type_id as type ,dptm.name as gazettedType FROM REFERENCE_NUMBER_DETAILS rnd,DOPART_II_TYPE_MASTER dptm WHERE rnd.ID IN (SELECT DO_PART_NO FROM ANNUAL_INCREMENT_DETAILS WHERE basic_pay_id IS NULL GROUP BY DO_PART_NO) and RND.TYPE_ID =DPTM.ID order by rnd.TYPE_ID").addScalar("doPartNo", Hibernate.STRING).addScalar("doPartDate1", Hibernate.DATE).addScalar("id", Hibernate.INTEGER).addScalar("gazettedType", Hibernate.STRING).addScalar("type", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(DoPartBean.class)).list());
		}catch (Exception e) {
			throw e;
		}
		return annualIncrementBean;
	} 
	public AnnualIncrementBean getAnnualIncrementDetailsInFinance(AnnualIncrementBean annualIncrementBean)throws Exception{
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			annualIncrementBean.setBasicPayIdList(session.createSQLQuery("SELECT rnd.REF_NUMBER as doPartNo,rnd.REF_DATE as doPartDate1 ,rnd.ID as id ,rnd.type_id as type ,dptm.name as gazettedType FROM REFERENCE_NUMBER_DETAILS rnd,DOPART_II_TYPE_MASTER dptm WHERE rnd.ID IN (SELECT DO_PART_NO FROM ANNUAL_INCREMENT_DETAILS WHERE finance_accepted_date IS NULL GROUP BY DO_PART_NO) and RND.TYPE_ID =DPTM.ID order by rnd.TYPE_ID").addScalar("doPartNo", Hibernate.STRING).addScalar("doPartDate1", Hibernate.DATE).addScalar("id", Hibernate.INTEGER).addScalar("gazettedType", Hibernate.STRING).addScalar("type", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(DoPartBean.class)).list());
		}catch (Exception e) {
			throw e;
		}
		return annualIncrementBean;
	} 
	@SuppressWarnings("unchecked")
	public AnnualIncrementBean updateAnnualIncrementDetails(AnnualIncrementBean annualIncrementBean) throws Exception{
		Session session =null;
		try{
			session=hibernateUtils.getSession();
			String[] sfidList=annualIncrementBean.getSfIds().split(",");
			for(int i=0;i<sfidList.length;i++){
				String sfid = sfidList[i].replaceAll("'", "");
			AnnualIncrementDTO annualIncrementDTO=(AnnualIncrementDTO)session.createCriteria(AnnualIncrementDTO.class).add(Expression.eq("userSfid", sfid)).add(Expression.eq("status", 1)).add(Expression.eq("doPartNo", Integer.parseInt(annualIncrementBean.getDoPartNo()))).uniqueResult();
			EmpBasicPayHistoryDTO empBasicPayHistoryDTO=(EmpBasicPayHistoryDTO)session.createCriteria(EmpBasicPayHistoryDTO.class).add(Expression.eq("sfid",sfid)).add(Expression.eq("status", 1)).uniqueResult();
			EmpBasicPayHistoryDTO empIncrementBasic=new EmpBasicPayHistoryDTO();
			BeanUtils.copyProperties(empBasicPayHistoryDTO, empIncrementBasic);
			empBasicPayHistoryDTO.setStatus(100);
			empBasicPayHistoryDTO.setPresentEffectiveDate(CPSUtils.convertStringToDate(CPSUtils.formattedDate(empBasicPayHistoryDTO.getPresentEffectiveDate().toString())));
			empBasicPayHistoryDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			empBasicPayHistoryDTO.setLastModifiedBy(annualIncrementBean.getSfID());
			session.update(empBasicPayHistoryDTO);
			session.flush();
			
			empIncrementBasic.setSfid(annualIncrementDTO.getUserSfid());
			empIncrementBasic.setBasicPay(annualIncrementDTO.getIncrementBasicPay());
			empIncrementBasic.setIncrementType("A");
			empIncrementBasic.setIncrementValue(annualIncrementDTO.getIncRoundOff());
			empIncrementBasic.setReferenceType("ONLINE");
			empIncrementBasic.setDesignationId(annualIncrementDTO.getDesignationId());
			empIncrementBasic.setStatus(1);
			empIncrementBasic.setPresentEffectiveDate(annualIncrementDTO.getAdminEffectDate()); //admin effective is set here
		    empIncrementBasic.setLastModifiedDate(CPSUtils.getCurrentDate());
		    empIncrementBasic.setLastModifiedBy(annualIncrementBean.getSfID());
			empIncrementBasic.setCreationDate(CPSUtils.getCurrentDate());
			empIncrementBasic.setCreatedBy(annualIncrementBean.getSfID());
			session.save(empIncrementBasic);
			session.flush();
			
			/*DoPartSerialNoDTO dopartSerialNoDTO=new DoPartSerialNoDTO();
			dopartSerialNoDTO.setRefDoPartId(Integer.valueOf(annualIncrementBean.getDoPartNo()));
			dopartSerialNoDTO.setModuleId(Integer.valueOf(CPSConstants.ANNUALINCREMENTMODULEID));
			dopartSerialNoDTO.setCasualityId(Integer.valueOf(annualIncrementDTO.getCasualityId()));
			dopartSerialNoDTO.setSlNo("1");
			dopartSerialNoDTO.setRequestId(String.valueOf(annualIncrementDTO.getId()));
			dopartSerialNoDTO.setStatus(1);
			dopartSerialNoDTO.setCreationDate(CPSUtils.getCurrentDate());
			dopartSerialNoDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			dopartSerialNoDTO.setCreatedBy(annualIncrementBean.getSfID());
			dopartSerialNoDTO.setLastModifiedBy(annualIncrementBean.getSfID());
			session.save(dopartSerialNoDTO);
			session.flush();*/
			
			EmpBasicPayHistoryDTO empBasicPayHistoryDTO2 =(EmpBasicPayHistoryDTO)session.createCriteria(EmpBasicPayHistoryDTO.class).add(Expression.eq("status", 1)).add(Expression.eq("sfid", sfid )).uniqueResult();
			AnnualIncrementDTO annualIncrementDTO1=(AnnualIncrementDTO)session.createCriteria(AnnualIncrementDTO.class).add(Expression.eq("userSfid",sfid)).add(Expression.eq("status", 1)).add(Expression.eq("doPartNo", Integer.parseInt(annualIncrementBean.getDoPartNo()))).uniqueResult();
			annualIncrementDTO1.setBasicPayId(String.valueOf(empBasicPayHistoryDTO2.getId()));
			session.update(annualIncrementDTO1);
			session.flush();
			}
			annualIncrementBean.setResult("Save Record Successfully");
		}catch (Exception e) {
			throw e;
		}
		return annualIncrementBean;
	}
	@SuppressWarnings("unchecked")
	public AnnualIncrementBean getEmpNullDetails(AnnualIncrementBean annualIncrementBean)throws Exception{
		Session session = null;
		try{
			session=hibernateUtils.getSession();
			annualIncrementBean.setEmpNullValuesDetails(session.createSQLQuery("SELECT AID.SFID AS userSfid,EM.NAME_IN_SERVICE_BOOK AS name,dlo.desigdescription as designationName,AID.BASIC_PAY AS basicPay ,AID.GRADE_PAY AS gradePay,(aid.basic_pay+aid.grade_pay) as basicGradeTotal,AID.INCREMENT3 AS increment3,AID.INCREMENT_ROUND_OFF AS incRoundOff,AID.INCREMENT_BASIC_PAY AS incrementBasicPay,AID.INCREMENT_GRADE_PAY AS INCREMENTGRADEPAY" +
					" FROM ANNUAL_INCREMENT_DETAILS AID,emp_master em,designation_list_in_order dlo WHERE AID.DO_PART_NO ="+annualIncrementBean.getDoPartNo()+" and aid.DESIGNATION_ID=dlo.id  AND EM.SFID =AID.SFID AND dlo.id IN" +
					" (SELECT DESIGNATION_ID FROM DOPART_II_TYPE_DESIG_MASTER WHERE STATUS=1 AND ID IN(SELECT TYPE_DESIG_ID FROM DOPART_II_TH_DESIGNATIONS WHERE STATUS =1  AND ROLE_ID IN (SELECT id FROM DOPART_II_TASK_HOLDER WHERE ROLE_ID=(SELECT ORG_ROLE_ID FROM EMP_ROLE_MAPPING WHERE SFID='"+annualIncrementBean.getSfID()+"' AND STATUS=1 AND ORG_ROLE_ID IN(SELECT role_id FROM DOPART_II_TASK_HOLDER WHERE status=1 AND type_id="+annualIncrementBean.getGazettedType()+"))))) " +
					" AND BASIC_PAY_ID IS NULL order by dlo.category_order,dlo.sub_category_order, dlo.designation_order,em.sfid").addScalar("userSfid", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("designationName", Hibernate.STRING).addScalar("basicPay", Hibernate.FLOAT).addScalar("gradePay", Hibernate.FLOAT).addScalar("basicGradeTotal", Hibernate.STRING).addScalar("increment3", Hibernate.FLOAT).addScalar("incRoundOff", Hibernate.FLOAT).addScalar("incrementBasicPay", Hibernate.FLOAT).addScalar("incrementGradePay", Hibernate.FLOAT).setResultTransformer(Transformers.aliasToBean(AnnualIncrementDTO.class)).list());
		}catch (Exception e) {
			throw e;
		}
		return annualIncrementBean;
	}
	@SuppressWarnings("unchecked")
	public AnnualIncrementBean getEmpNotNullDetails(AnnualIncrementBean annualIncrementBean)throws Exception{
		Session session = null;
		try{
			session=hibernateUtils.getSession();
			annualIncrementBean.setEmpNotNullValuesDetails(session.createSQLQuery("SELECT AID.SFID AS userSfid,EM.NAME_IN_SERVICE_BOOK AS name,dlo.desigdescription as designationName,AID.BASIC_PAY AS basicPay ,AID.GRADE_PAY AS gradePay,(aid.basic_pay+aid.grade_pay) as basicGradeTotal,AID.INCREMENT3 AS increment3,AID.INCREMENT_ROUND_OFF AS incRoundOff,AID.INCREMENT_BASIC_PAY AS incrementBasicPay,AID.INCREMENT_GRADE_PAY AS INCREMENTGRADEPAY" +
					" FROM ANNUAL_INCREMENT_DETAILS AID,emp_master em,designation_list_in_order dlo WHERE AID.DO_PART_NO ="+annualIncrementBean.getDoPartNo()+" and aid.DESIGNATION_ID=dlo.id  AND EM.SFID =AID.SFID AND dlo.id IN" +
					" (SELECT DESIGNATION_ID FROM DOPART_II_TYPE_DESIG_MASTER WHERE STATUS=1 AND ID IN(SELECT TYPE_DESIG_ID FROM DOPART_II_TH_DESIGNATIONS WHERE STATUS =1  AND ROLE_ID IN (SELECT id FROM DOPART_II_TASK_HOLDER WHERE ROLE_ID=(SELECT ORG_ROLE_ID FROM EMP_ROLE_MAPPING WHERE SFID='"+annualIncrementBean.getSfID()+"' AND STATUS=1 AND ORG_ROLE_ID IN(SELECT role_id FROM DOPART_II_TASK_HOLDER WHERE status=1 AND type_id="+annualIncrementBean.getGazettedType()+"))))) " +
					" AND BASIC_PAY_ID IS not NULL order by dlo.category_order,dlo.sub_category_order, dlo.designation_order,em.sfid").addScalar("userSfid", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("designationName", Hibernate.STRING).addScalar("basicPay", Hibernate.FLOAT).addScalar("gradePay", Hibernate.FLOAT).addScalar("basicGradeTotal", Hibernate.STRING).addScalar("increment3", Hibernate.FLOAT).addScalar("incRoundOff", Hibernate.FLOAT).addScalar("incrementBasicPay", Hibernate.FLOAT).addScalar("incrementGradePay", Hibernate.FLOAT).setResultTransformer(Transformers.aliasToBean(AnnualIncrementDTO.class)).list());
		}catch (Exception e) {
			throw e;
		}
		return annualIncrementBean;
	}
}
