package com.callippus.web.hrdg.training.request;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.letterNumberFormat.IONDTO;
import com.callippus.web.beans.letterNumberFormat.LetterNumberFormatDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.hrdg.training.beans.dto.CourseAttendedDetailsDTO;
import com.callippus.web.hrdg.training.beans.dto.CourseDTO;
import com.callippus.web.hrdg.training.beans.dto.CourseDurationsDTO;
import com.callippus.web.hrdg.training.beans.dto.HRDGBoardDTO;
import com.callippus.web.hrdg.training.beans.dto.TrainingTypeDTO;
import com.callippus.web.hrdg.training.request.beans.dto.HrdgTxnCancelRequestDTO;
import com.callippus.web.hrdg.training.request.beans.dto.HrdgTxnRequestDTO;
import com.callippus.web.hrdg.training.request.beans.dto.TrainingNominationDTO;
import com.callippus.web.hrdg.training.request.beans.dto.TrainingRequestBean;
import com.callippus.web.loan.beans.dto.FinancialYearDTO;

@Service
public class SQLTrainingRequestDAO implements ITrainingRequestDAO{
	
	private static Log log = LogFactory.getLog(SQLTrainingRequestDAO.class);
	private static final long serialVersionUID = 5611041426238862266L;
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	private HibernateUtils hibernateUtils;

	
	public List getCourseListBasedCirculation(TrainingRequestBean trainingReqBean) throws Exception 
	{
		Session session = null; 
		List list = null;  
		String sb = null; 
		try
		{
			session = hibernateUtils.getSession();  
			sb = "SELECT C.Training_course_id AS id,(c.course_name||'-('||dur.start_date||')' ) as name,(select c1.cir_id from Hrdg_training_cir_master c1 where c1.course_id=c.training_course_id and c1.course_duration_id=dur.course_duration_id)as trainingTypeId FROM Hrdg_training_cir_master Cm,Hrdg_training_course_details C,hrdg_course_duration_master dur, hrdg_circulation_departments cd,  Emp_master Emp ,  Departments_master D WHERE dur.course_id=c.training_course_id and cm.course_duration_id=dur.course_duration_id and sysdate BETWEEN cm.nom_start_date AND cm.nom_end_date AND c.training_course_id=cm.course_id And Cm.Cir_id= Cd.Cir_id And Emp.Sfid Like '"+trainingReqBean.getSfid()+"' And (D.Department_id in ((select ori.department_id from emp_role_mapping erm,org_role_instance ori where ori.org_role_id=erm.org_role_id and erm.sfid like '"+trainingReqBean.getSfid()+"') union (select ori.department_id from org_role_instance ori where ori.org_role_id=emp.office_id))) AND D.Department_id   =Cd.Department_id";
			sb = "SELECT  C.Training_course_id AS id,(c.course_name||'-('||dur.start_date||')') AS name,(SELECT c1.cir_id FROM Hrdg_training_cir_master c1 WHERE c1.course_id=c.training_course_id And C1.Course_duration_id=Dur.Course_duration_id)AS trainingTypeId FROM Hrdg_training_cir_master Cm,Hrdg_training_course_details C,hrdg_course_duration_master dur WHERE dur.course_id=c.training_course_id AND cm.course_duration_id=dur.course_duration_id AND sysdate BETWEEN cm.nom_start_date AND cm.nom_end_date And C.Training_course_id=Cm.Course_id And Cm.Cir_id In (Select cd.cir_id From Hrdg_circulation_departments Cd,Emp_master Emp,Departments_master D Where Cm.Cir_id= Cd.Cir_id And Emp.Sfid Like '"+trainingReqBean.getSfid()+"' And D.Department_id =Cd.Department_id AND D.Department_id IN ((SELECT ori.department_id FROM emp_role_mapping erm,org_role_instance ori WHERE ori.org_role_id=erm.org_role_id AND erm.sfid LIKE '"+trainingReqBean.getSfid()+"') UNION (SELECT ori.department_id FROM org_role_instance ori Where Ori.Org_role_id=Emp.Office_id)))";    //this is restriction to show the course Details only to hrdg circulated departments.  
			sb = "SELECT  C.Training_course_id AS id,(c.course_name||'-('||dur.start_date||')') AS name,(SELECT c1.cir_id FROM Hrdg_training_cir_master c1 WHERE c1.course_id=c.training_course_id And C1.Course_duration_id=Dur.Course_duration_id)AS trainingTypeId FROM Hrdg_training_cir_master Cm,Hrdg_training_course_details C,hrdg_course_duration_master dur WHERE dur.course_id=c.training_course_id AND cm.course_duration_id=dur.course_duration_id AND Sysdate>=to_date(Cm.Nom_start_date,'dd-Mon-YYYY') and sysdate<=to_date(cm.nom_end_date,'dd-Mon-YYYY') And C.Training_course_id=Cm.Course_id";
			sb = "SELECT  C.Training_course_id AS id,(c.course_name||'-('||dur.start_date||')') AS name,(SELECT c1.cir_id FROM Hrdg_training_cir_master c1 WHERE c1.course_id=c.training_course_id And C1.Course_duration_id=Dur.Course_duration_id)AS trainingTypeId FROM Hrdg_training_cir_master Cm,Hrdg_training_course_details C,hrdg_course_duration_master dur WHERE dur.course_id=c.training_course_id AND cm.course_duration_id=dur.course_duration_id AND sysdate BETWEEN cm.nom_start_date AND cm.nom_end_date And C.Training_course_id=Cm.Course_id";
			sb = "SELECT  C.Training_course_id AS id,(c.course_name||'-('||dur.start_date||')') AS name,(SELECT c1.cir_id FROM Hrdg_training_cir_master c1 WHERE c1.course_id=c.training_course_id And C1.Course_duration_id=Dur.Course_duration_id)AS trainingTypeId FROM Hrdg_training_cir_master Cm,Hrdg_training_course_details C,hrdg_course_duration_master dur WHERE dur.course_id=c.training_course_id AND cm.course_duration_id=dur.course_duration_id AND ((Sysdate  Between To_date(Cm.Nom_start_date,'DD-Mon-YYYY') and To_date(Cm.Nom_end_date,'DD-Mon-YYYY')) or To_char(Sysdate,'DD-Mon-YYYY') In (Cm.Nom_end_date)) And C.Training_course_id=Cm.Course_id";
			
			//sb = "SELECT DISTINCT(C.Training_course_id) AS id,c.course_name AS name,(select max(c1.cir_id) from  Hrdg_training_cir_master c1 where c1.course_id=c.training_course_id)as trainingTypeId FROM Hrdg_training_cir_master Cm,Hrdg_training_course_details C,  hrdg_circulation_departments cd,  Emp_master Emp ,  Departments_master D WHERE sysdate BETWEEN cm.nom_start_date AND cm.nom_end_date AND c.training_course_id=cm.course_id And Cm.Cir_id= Cd.Cir_id And Emp.Sfid Like '"+trainingReqBean.getSfid()+"' And (D.Department_id in ((select ori.department_id from emp_role_mapping erm,org_role_instance ori where ori.org_role_id=erm.org_role_id and erm.sfid like '"+trainingReqBean.getSfid()+"') union (select ori.department_id from org_role_instance ori where ori.org_role_id=emp.office_id))) AND D.Department_id   =Cd.Department_id";
			//sb = "Select Distinct(C.Training_course_id) as id,c.course_name as name,cm.cir_id as trainingTypeId From Hrdg_training_cir_master Cm,Hrdg_training_course_details C,hrdg_circulation_departments cd,Emp_master Emp ,Departments_master D Where sysdate between cm.nom_start_date and cm.nom_end_date and c.training_course_id=cm.course_id and Cm.Cir_id = cd.cir_id and Emp.Sfid like '"+trainingReqBean.getSfid()+"' AND Emp.directorate_id=d.department_id AND  D.Department_id =Cd.Department_id" ;
			list = session.createSQLQuery(sb).addScalar("id", Hibernate.INTEGER).addScalar("name", Hibernate.STRING)
			.addScalar("trainingTypeId",Hibernate.INTEGER)
			.setResultTransformer(Transformers.aliasToBean(CourseDTO.class)).list();
			
			
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			
		}
		return list;
	}
	public List checkTrainingNomination(TrainingRequestBean trainingReqBean) throws Exception
	{
		Session session = null;
		List list = null;
		String sb = null;
		try
		{
			session = hibernateUtils.getSession();
			sb = "from TrainingCirculationDTO cm,TrainingNominationDTO nom,CourseDTO c where cm.id=nom.cirId and c.id=cm.courseId and cm.courseId=? and nom.nomineeSfid=? and nom.status=1 and cm.status=1 and c.status=1";
			Query qry = session.createQuery(sb);
			qry.setString(0, trainingReqBean.getCourseId());
			qry.setString(1, trainingReqBean.getNomineeSfid());
			list = qry.list();
			
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			
		}
		return list;
	}
	public EmployeeBean getEmpDetails(String sfid) throws Exception
	{
		Session session = null;
		List list = null;
		EmployeeBean bean = null;
		
		try
		{
			session = hibernateUtils.getSession();
			bean = (EmployeeBean)session.createCriteria(EmployeeBean.class).add(Expression.eq("userSfid",sfid)).add(Expression.eq("status",1)).uniqueResult();
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			
		}
		return bean;
	}
	public TrainingNominationDTO getNominationDetails(int requestId) throws Exception
	{
		Session session = null;
		List list = null;
		TrainingNominationDTO dto = null;
		String sb = null;
		try
		{
			session = hibernateUtils.getSession();
			sb = "select nom.id as id,nom.cirId as cirId,nom.nominationDate as nominationDate,c.id as offlineFlag,nom.currentAssignment as currentAssignment,nom.relevance as relevance,nom.lastAttendedCourse as lastAttendedCourse from TrainingNominationDTO nom,TrainingCirculationDTO cir,CourseDTO c where c.id=cir.courseId and nom.cirId=cir.id and c.status=1 and nom.status=1 and cir.status=1 and nom.requestId=?";
			list = session.createQuery(sb).setString(0, String.valueOf(requestId)).setResultTransformer(Transformers.aliasToBean(TrainingNominationDTO.class)).list();
			if(CPSUtils.checkList(list)){
			 dto = (TrainingNominationDTO)list.get(0);
			 dto.setCourseDto((CourseDTO)session.createCriteria(CourseDTO.class).add(Expression.eq("id",dto.getOfflineFlag())).uniqueResult());
			 sb = "select c.startDate as startDate,((To_date(c.endDate,'DD-MON-YYYY')-To_date(c.startDate,'DD-MON-YYYY'))+1) as duration from TrainingNominationDTO nom,TrainingCirculationDTO cir,CourseDurationsDTO c where c.id=cir.durationId and nom.cirId=cir.id and c.status=1 and nom.status=1 and cir.status=1 and nom.requestId=?";
			 List durlist = session.createQuery(sb).setString(0, String.valueOf(requestId)).setResultTransformer(Transformers.aliasToBean(CourseDTO.class)).list();
			 if(CPSUtils.checkList(durlist))
			 {
				 dto.getCourseDto().setStartDate(((CourseDTO)durlist.get(0)).getStartDate());
				dto.getCourseDto().setDays(String.valueOf(((CourseDTO)durlist.get(0)).getDuration().intValue()));
			 }
			 dto.setHrdgTxnCancelReqDto((HrdgTxnCancelRequestDTO)session.createQuery("select requestId as requestId from HrdgTxnCancelRequestDTO where refRequestId=? and status =1 and (requestStatus !=1 or requestStatus !=5)").setString(0, String.valueOf(requestId)).setResultTransformer(Transformers.aliasToBean(HrdgTxnCancelRequestDTO.class)).uniqueResult());
			}
			
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			
		}
		
		return dto;
	}
	public List getTrainingNominationReqList(TrainingRequestBean trainingReqBean) throws Exception
	{
		Session session = null;
		List list = null;
		String sb = null;
		try
		{
			session = hibernateUtils.getSession();
//			sb = "select id as id,requestId as requestId,requestStatus as requestStatus from HrdgTxnRequestDTO where requestStatus=1";
//			list = session.createQuery(sb).setResultTransformer(Transformers.aliasToBean(HrdgTxnRequestDTO.class)).list();
//			if(CPSUtils.checkList(list))
//			{
//				for(int i =0;i<list.size();i++)
//				{
//					HrdgTxnRequestDTO dto = (HrdgTxnRequestDTO)list.get(i);
//					//((HrdgTxnRequestDTO)list.get(i)).setCourseDto(session.createCriteria(CouseDTO.class).add(Expression.eq()))
//					dto.setTrainingNominationDto((TrainingNominationDTO)session.createCriteria(TrainingNominationDTO.class).add(Expression.eq("requestId",dto.getRequestId())).add(Expression.eq("status",1)).uniqueResult());
//					dto.setEmpBean((EmployeeBean)session.createCriteria(EmployeeBean.class).add(Expression.eq("userSfid",dto.getTrainingNominationDto().getNomineeSfid())).add(Expression.eq("status",1)).uniqueResult());
//				}
//			}
			sb= "select (select max(historyID) from RequestCommonBean where requestID=t.requestId)as historyID,t.id as id,t.requestId as requestId,t.requestStatus as requestStatus from HrdgTxnRequestDTO t,TrainingNominationDTO n,TrainingCirculationDTO c,CourseDurationsDTO d where n.requestId=t.requestId and c.id=n.cirId and c.durationId=? and d.id=c.durationId and d.startDate >=sysdate and t.status=1 and (t.requestStatus=15 or t.requestStatus=21)";
			list = session.createQuery(sb).setString(0, trainingReqBean.getDurationId()).setResultTransformer(Transformers.aliasToBean(HrdgTxnRequestDTO.class)).list();
			if(CPSUtils.checkList(list))
				{
					for(int i =0;i<list.size();i++)
					{
						HrdgTxnRequestDTO dto = (HrdgTxnRequestDTO)list.get(i);
						dto.setCourseDurationDto((CourseDurationsDTO)session.createCriteria(CourseDurationsDTO.class).add(Expression.eq("id",Integer.parseInt(trainingReqBean.getDurationId()))).add(Expression.eq("status",1)).uniqueResult());
						dto.setTrainingNominationDto((TrainingNominationDTO)session.createCriteria(TrainingNominationDTO.class).add(Expression.eq("requestId",dto.getRequestId())).add(Expression.eq("status",1)).uniqueResult());
						dto.setEmpBean((EmployeeBean)session.createCriteria(EmployeeBean.class).add(Expression.eq("userSfid",dto.getTrainingNominationDto().getNomineeSfid())).add(Expression.eq("status",1)).uniqueResult());
					}
				}
			
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			
		}
		return list;
	}
	
	public List getNominationsCourseList(TrainingRequestBean trainingReqBean) throws Exception
	{
		Session session = null;
		List list = null;
		String sb = null;
		try
		{
			session = hibernateUtils.getSession();
				
			sb = "select d.id as id,(c.name||'-('||d.startDate||')') as course,d.startDate as startDate from TrainingCirculationDTO cir,CourseDTO c,CourseDurationsDTO d where cir.durationId=d.id and c.id=d.courseId and cir.courseId=c.id and cir.status=1 and c.status=1 and d.status=1 and d.startDate>=sysdate";
			if(CPSUtils.compareStrings(CPSConstants.PARAM, trainingReqBean.getParam()))
				sb = "select d.id as id,(c.name||'-('||d.startDate||')') as course,d.startDate as startDate from TrainingCirculationDTO cir,CourseDTO c,CourseDurationsDTO d where cir.durationId=d.id and c.id=d.courseId and cir.courseId=c.id and cir.status=1 and c.status=1 and d.status=1";
			list = session.createQuery(sb).setResultTransformer(Transformers.aliasToBean(CourseDurationsDTO.class)).list();
			
					
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			
		}
		return list;
	}
	
	public List getMDBList() throws Exception
	{
		Session session = null;
		List list = null;
		String sb = null;
		try
		{
			session = hibernateUtils.getSession();
				
			sb = "select b.id as id,b.name as name from HRDGBoardDTO b";
			list = session.createQuery(sb).setResultTransformer(Transformers.aliasToBean(HRDGBoardDTO.class)).list();
					
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			
		}
		return list;
	}
	public String updateNominationStatus(String id, String boardId,TrainingRequestBean trainingReqBean) throws Exception
	 {
		 log.debug("updateNominationStatus() --> Start");
			
			Session session = null;
			HrdgTxnRequestDTO dto= null;
			String message = null;
			Transaction tx = null;
			try {
				session = hibernateUtils.getSession();
				
				dto = (HrdgTxnRequestDTO) session.createCriteria(HrdgTxnRequestDTO.class).add(Expression.eq("id",Integer.parseInt(id))).uniqueResult();
				dto.setRequestStatus(Integer.parseInt(trainingReqBean.getSelectStatus()));
				dto.setBoardId(Integer.parseInt(boardId));
				session.update(dto);
				message = CPSConstants.SUCCESS;
				
				
			} catch (Exception e) {
				message = CPSConstants.FAILED;
				
				throw e;
			} finally {
				
				
			}
			log.debug("updateNominationStatus() --> End");
			return message; 
	 }
	 
	 public List getTrainingNominationBoardSelectionList(TrainingRequestBean trainingReqBean) throws Exception
	 {
		 Session session = null;
			List list = null;
			String sb = null;
			try
			{
				session = hibernateUtils.getSession(); 

				sb= "select (select max(historyID) from RequestCommonBean where requestID=t.requestId)as historyID,t.id as id,t.requestId as requestId,t.requestStatus as requestStatus,(select letterNumber from IONDTO where id=t.mdbApprovedIonId and status=1) as letterNumber from HrdgTxnRequestDTO t,TrainingNominationDTO n,TrainingCirculationDTO c,CourseDurationsDTO d where n.requestId=t.requestId and c.id=n.cirId and c.durationId=? and t.boardId=? and d.id=c.durationId and d.startDate >=sysdate and (t.requestStatus=21 or t.requestStatus=27) and t.status=1 order by t.requestStatus";
				list = session.createQuery(sb).setString(0, trainingReqBean.getCourseId()).setString(1, trainingReqBean.getBoardId()).setResultTransformer(Transformers.aliasToBean(HrdgTxnRequestDTO.class)).list();
				if(CPSUtils.checkList(list))
					{
						for(int i =0;i<list.size();i++)
						{
							HrdgTxnRequestDTO dto = (HrdgTxnRequestDTO)list.get(i);
							dto.setTrainingNominationDto((TrainingNominationDTO)session.createCriteria(TrainingNominationDTO.class).add(Expression.eq("requestId",dto.getRequestId())).add(Expression.eq("status",1)).uniqueResult());
							dto.setEmpBean((EmployeeBean)session.createCriteria(EmployeeBean.class).add(Expression.eq("userSfid",dto.getTrainingNominationDto().getNomineeSfid())).add(Expression.eq("status",1)).uniqueResult());
						}
					}
				
			}
			catch(Exception e)
			{
				throw e;
			}
			finally
			{
				
			}
			return list;
	 }
	 
	 public String updateBoardSelectionStatus(String id,TrainingRequestBean trainingReqBean) throws Exception
	 {
		 log.debug("updateBoardSelectionStatus() --> Start");
			
			Session session = null;
			HrdgTxnRequestDTO dto= null;
			String message = null;
			Transaction tx = null;
			try {
				session = hibernateUtils.getSession();
				
				dto = (HrdgTxnRequestDTO) session.createCriteria(HrdgTxnRequestDTO.class).add(Expression.eq("id",Integer.parseInt(id))).uniqueResult();
				dto.setRequestStatus(Integer.parseInt(trainingReqBean.getSelectStatus()));
				dto.setMdbApprovedIonId(trainingReqBean.getIonId());
				session.update(dto);
				message = CPSConstants.SUCCESS;
				
				
			} catch (Exception e) {
				message = CPSConstants.FAILED;
				
				throw e;
			} finally {
				
				
			}
			log.debug("updateBoardSelectionStatus() --> End");
			return message; 
	 }
	 
	 public List getTrainingNominationADSelectionList(TrainingRequestBean trainingReqBean) throws Exception
	 {
		 Session session = null;
			List list = null;
			String sb = null;
			try
			{
				session = hibernateUtils.getSession(); 

				sb= "select (select max(historyID) from RequestCommonBean where requestID=t.requestId)as historyID,t.id as id,t.requestId as requestId,t.requestStatus as requestStatus,(select letterNumber from IONDTO where id=t.adApprovedIonId and status=1) as letterNumber from HrdgTxnRequestDTO t,TrainingNominationDTO n,TrainingCirculationDTO c,CourseDurationsDTO d where n.requestId=t.requestId and c.id=n.cirId and c.durationId=? and d.id=c.durationId and d.startDate >=sysdate and t.status=1 and (t.requestStatus=27 or t.requestStatus=33) order by t.requestStatus";
				list = session.createQuery(sb).setString(0, trainingReqBean.getCourseId()).setResultTransformer(Transformers.aliasToBean(HrdgTxnRequestDTO.class)).list();
				if(CPSUtils.checkList(list))
					{
						for(int i =0;i<list.size();i++)
						{
							HrdgTxnRequestDTO dto = (HrdgTxnRequestDTO)list.get(i);
							dto.setTrainingNominationDto((TrainingNominationDTO)session.createCriteria(TrainingNominationDTO.class).add(Expression.eq("requestId",dto.getRequestId())).add(Expression.eq("status",1)).uniqueResult());
							dto.setEmpBean((EmployeeBean)session.createCriteria(EmployeeBean.class).add(Expression.eq("userSfid",dto.getTrainingNominationDto().getNomineeSfid())).add(Expression.eq("status",1)).uniqueResult());
						}
					}
				
			}
			catch(Exception e)
			{
				throw e;
			}
			finally
			{
				
			}
			return list;
	 }
	 
	 public String updateADSelectionStatus(String id,TrainingRequestBean trainingReqBean) throws Exception
	 {
		 log.debug("updateBoardSelectionStatus() --> Start");
			
			Session session = null;
			HrdgTxnRequestDTO dto= null;
			String message = null;
			Transaction tx = null;
			try {
				session = hibernateUtils.getSession(); 
				session.flush();
				
				dto = (HrdgTxnRequestDTO) session.createCriteria(HrdgTxnRequestDTO.class).add(Expression.eq("id",Integer.parseInt(id))).uniqueResult();
				dto.setRequestStatus(Integer.parseInt(trainingReqBean.getSelectStatus()));
				dto.setAdApprovedIonId(trainingReqBean.getIonId());
				session.update(dto);
				message = CPSConstants.SUCCESS;
				
				
			} catch (Exception e) {
				message = CPSConstants.FAILED;
				
				throw e;
			} finally {
				
				
			}
			log.debug("updateBoardSelectionStatus() --> End");
			return message; 
	 }
	 
	 public List getTrainingNominationFinalSelectionList(TrainingRequestBean trainingReqBean)throws Exception
	 {
		 Session session = null;
			List list = null;
			String sb = null;
			try
			{
				session = hibernateUtils.getSession();

				sb= "select (select max(historyID) from RequestCommonBean where requestID=t.requestId)as historyID,t.id as id,t.requestId as requestId,t.requestStatus as requestStatus,(select letterNumber from IONDTO where id=t.inistituteIonId and status=1 ) as letterNumber from HrdgTxnRequestDTO t,TrainingNominationDTO n,TrainingCirculationDTO c,CourseDurationsDTO d,CourseDTO cd where n.requestId=t.requestId and c.id=n.cirId and c.durationId=? and d.id=c.durationId and c.courseId=cd.id and t.status=1 and (((t.requestStatus=33 or t.requestStatus=50) and cd.fee is null) or ((t.requestStatus=39 or t.requestStatus=50) and cd.fee is not null)) order by t.requestStatus";
				list = session.createQuery(sb).setString(0, trainingReqBean.getCourseId()).setResultTransformer(Transformers.aliasToBean(HrdgTxnRequestDTO.class)).list();
				if(CPSUtils.checkList(list))
					{
						for(int i =0;i<list.size();i++)
						{
							HrdgTxnRequestDTO dto = (HrdgTxnRequestDTO)list.get(i);
							dto.setTrainingNominationDto((TrainingNominationDTO)session.createCriteria(TrainingNominationDTO.class).add(Expression.eq("requestId",dto.getRequestId())).add(Expression.eq("status",1)).uniqueResult());
							dto.setEmpBean((EmployeeBean)session.createCriteria(EmployeeBean.class).add(Expression.eq("userSfid",dto.getTrainingNominationDto().getNomineeSfid())).add(Expression.eq("status",1)).uniqueResult());
						}
					}
								
			}
			catch(Exception e)
			{
				throw e;
			}
			finally
			{
				
			}
			return list;
	 }
	 public String updateFinalSelectionStatus(String id, TrainingRequestBean trainingReqBean) throws Exception
	 {
		 log.debug("updateFinalSelectionStatus() --> Start");
			
			Session session = null;
			HrdgTxnRequestDTO dto= null;
			String message = null;
			Transaction tx = null;
			try {
				session = hibernateUtils.getSession();
				
				dto = (HrdgTxnRequestDTO) session.createCriteria(HrdgTxnRequestDTO.class).add(Expression.eq("id",Integer.parseInt(id))).uniqueResult();
				dto.setRequestStatus(Integer.parseInt(trainingReqBean.getSelectStatus()));
				dto.setInistituteIonId(trainingReqBean.getIonId());
				session.update(dto);
				message = CPSConstants.SUCCESS;
				session.flush();
				
			} catch (Exception e) {
				message = CPSConstants.FAILED;
				
				throw e;
			} finally {
				
				
			}
			log.debug("updateFinalSelectionStatus() --> End");
			return message;  
	 }
	 
	 public List getNominationsForCFACourseList(TrainingRequestBean trainingReqBean) throws Exception
	 {
		 	Session session = null;
			List list = null;
			String sb = null;
			try
			{
				session = hibernateUtils.getSession();
				sb = "Select cir.cir_id,c.training_course_id,c.course_name From Hrdg_training_cir_master Cir,Hrdg_training_course_details C,Hrdg_course_duration_master D Where Cir.Course_duration_id=D.Course_duration_id And D.Course_id=C.Training_course_id And d.start_date >=sysdate;";	
				sb = "select d.id as id,c.name||'('||d.startDate||' to '||d.endDate||')' as name from TrainingCirculationDTO cir,CourseDTO c,CourseDurationsDTO d where cir.durationId=d.id and c.id=d.courseId and d.startDate>=sysdate and c.fee is not null and cir.status=1 and d.status=1 and c.status=1";
				list = session.createQuery(sb).setResultTransformer(Transformers.aliasToBean(CourseDTO.class)).list();
						
			}
			catch(Exception e)
			{
				throw e;
			}
			finally
			{
				
			}
			return list;
	 }

		public List getTrainingNominationsCFASelectionList(TrainingRequestBean trainingReqBean) throws Exception
		{
			Session session = null;
			List list = null;
			String sb = null;
			try
			{
				session = hibernateUtils.getSession();

				sb= "select (select max(historyID) from RequestCommonBean where requestID=t.requestId)as historyID,t.id as id,t.requestId as requestId,t.requestStatus as requestStatus from HrdgTxnRequestDTO t,TrainingNominationDTO n,TrainingCirculationDTO c,CourseDurationsDTO d,CourseDTO cd where n.requestId=t.requestId and c.id=n.cirId and d.id=? and d.id=c.durationId and d.startDate >=sysdate and d.courseId=cd.id and cd.fee is not null and t.status=1 and (t.requestStatus=33 or t.requestStatus=39)";
				list = session.createQuery(sb).setString(0, trainingReqBean.getCourseId()).setResultTransformer(Transformers.aliasToBean(HrdgTxnRequestDTO.class)).list();
				if(CPSUtils.checkList(list))
					{
						for(int i =0;i<list.size();i++)
						{
							HrdgTxnRequestDTO dto = (HrdgTxnRequestDTO)list.get(i);
							dto.setTrainingNominationDto((TrainingNominationDTO)session.createCriteria(TrainingNominationDTO.class).add(Expression.eq("requestId",dto.getRequestId())).add(Expression.eq("status",1)).uniqueResult());
							dto.setEmpBean((EmployeeBean)session.createCriteria(EmployeeBean.class).add(Expression.eq("userSfid",dto.getTrainingNominationDto().getNomineeSfid())).add(Expression.eq("status",1)).uniqueResult());
							dto.setCourseDto((CourseDTO)session.createQuery("select c.id as id,c.fee as fee,c.serviceTax as serviceTax from CourseDTO c,CourseDurationsDTO cd where cd.id=? and cd.courseId=c.id and c.status=1 and cd.status=1").setString(0,trainingReqBean.getCourseId()).setResultTransformer(Transformers.aliasToBean(CourseDTO.class)).uniqueResult());
						}
					}
				
			}
			catch(Exception e)
			{
				throw e;
			}
			finally
			{
				
			}
			return list;
		}
		public String updateTrainingNominationsCFAStatus(String id,TrainingRequestBean trainingReqBean) throws Exception
		{
log.debug("updateTrainingNominationsCFASelectionList() --> Start");
			
			Session session = null;
			HrdgTxnRequestDTO dto= null;
			String message = null;
			Transaction tx = null;
			try {
				session = hibernateUtils.getSession();
				
				dto = (HrdgTxnRequestDTO) session.createCriteria(HrdgTxnRequestDTO.class).add(Expression.eq("id",Integer.parseInt(id))).uniqueResult();
				dto.setRequestStatus(Integer.parseInt(trainingReqBean.getSelectStatus()));
				session.update(dto);
				message = CPSConstants.SUCCESS;
				
				
			} catch (Exception e) {
				message = CPSConstants.FAILED;
				
				throw e;
			} finally {
				
				
			}
			log.debug("updateFinalSelectionStatus() --> End");
			return message; 
		}
		public List viewTrainingNominationDetails(TrainingRequestBean trainingReqBean) throws Exception
		{
			Session session = null;
			List list = null;
			String sb = null;    
			try
			{
				session = hibernateUtils.getSession(); 
				session.flush();
				sb = "SELECT (select max(historyID) from RequestCommonBean where requestID=req.requestId)as historyID,course.course_name,dur.start_date,dur.end_date,nom.request_id FROM hrdg_training_course_details course,hrdg_course_duration_master dur,hrdg_training_cir_master cir,Hrdg_training_nomination_mst Nom,Hrdg_request_master Req Where Req.Request_status>0 And Course.Training_course_id=Dur.Course_id And Cir.Course_duration_id=Dur.Course_duration_id And Cir.Course_id= Course.Training_course_id And Nom.Cir_id=Cir.Cir_id And Req.Request_id=Nom.Request_id And Req.Status = 1 And Nom.Status = 1 And Cir.Status = 1 And Dur.Status = 1 and course.status = 1";
				sb = "select (select max(historyID) from RequestCommonBean where requestID=req.requestId)as historyID,nom.requestId as requestId from CourseDTO course,CourseDurationsDTO dur,TrainingCirculationDTO cir,TrainingNominationDTO nom,HrdgTxnRequestDTO req where req.requestStatus>8 and course.id=dur.courseId and cir.durationId=dur.id and cir.courseId=course.id and nom.cirId=cir.id and nom.requestId=req.requestId and course.status=1 and dur.status=1 and cir.status=1 and nom.status=1 and req.status=1 and req.requestStatus>=8 and nom.nomineeSfid ='"+trainingReqBean.getNomineeSfid()+"'";//and dur.startDate >=to_date('"+trainingReqBean.getStartDate()+"','DD-MON-YYYY') and dur.endDate <=to_date('"+trainingReqBean.getEndDate()+"','DD-MON-YYYY') ;
				sb = "select history.historyID as historyID,history.stageID as stageID, nom.requestId as requestId from CourseDTO course,CourseDurationsDTO dur,TrainingCirculationDTO cir,TrainingNominationDTO nom,HrdgTxnRequestDTO req,RequestCommonBean history where (req.requestStatus=8 or req.requestStatus=15 or req.requestStatus=21 or req.requestStatus=27 or req.requestStatus=33 or req.requestStatus=39) and req.requestId not in (select refRequestId as refRequestId from HrdgTxnCancelRequestDTO where refRequestId=req.requestId and status=1 and requestStatus=2) and course.id=dur.courseId and cir.durationId=dur.id and cir.courseId=course.id and nom.cirId=cir.id and nom.requestId=req.requestId and course.status=1 and dur.status=1 and cir.status=1 and nom.status=1 and req.status=1 and req.requestStatus>=8 and history.status != 29 and history.requestID=req.requestId and history.stageStatus != 6 and history.stageStatus != 9 and history.historyID in (select max(historyID) from RequestCommonBean where requestID=req.requestId) and nom.nomineeSfid ='"+trainingReqBean.getNomineeSfid()+"'";//and dur.startDate >=to_date('"+trainingReqBean.getStartDate()+"','DD-MON-YYYY') and dur.endDate <=to_date('"+trainingReqBean.getEndDate()+"','DD-MON-YYYY') ;
				sb = "select history.historyID as historyID,nom.requestId as requestId from CourseDTO course,CourseDurationsDTO dur,TrainingCirculationDTO cir,TrainingNominationDTO nom,HrdgTxnRequestDTO req,RequestCommonBean history where course.id=dur.courseId and cir.durationId=dur.id and cir.courseId=course.id and nom.cirId=cir.id and nom.requestId=req.requestId and course.status=1 and dur.status=1 and cir.status=1 and nom.status=1 and history.requestID=req.requestId and history.historyID in (select max(historyID) from RequestCommonBean where requestID=req.requestId) and nom.nomineeSfid ='"+trainingReqBean.getNomineeSfid()+"'";//and dur.startDate >=to_date('"+trainingReqBean.getStartDate()+"','DD-MON-YYYY') and dur.endDate <=to_date('"+trainingReqBean.getEndDate()+"','DD-MON-YYYY') ;
				list = session.createQuery(sb).setResultTransformer(Transformers.aliasToBean(HrdgTxnRequestDTO.class)).list();
				if(CPSUtils.checkList(list)) 
				{
					for(int i =0;i<list.size();i++)
					{
						HrdgTxnRequestDTO dto = (HrdgTxnRequestDTO)list.get(i);
//						Object str = session.createSQLQuery("Select Request_stage From Request_workflow_history where id=? and status != 29").setString(0, dto.getHistoryID()).uniqueResult();
//						dto.setHistoryStage(str.toString());
						dto.setCourseDto((CourseDTO)session.createQuery("select id as id,name as name from CourseDTO where status=1 and id in (select cir.courseId from TrainingCirculationDTO cir,TrainingNominationDTO nom where nom.requestId="+dto.getRequestId()+" and cir.id=nom.cirId and cir.status=1 and nom.status=1)").setResultTransformer(Transformers.aliasToBean(CourseDTO.class)).uniqueResult());
						dto.setCourseDurationDto((CourseDurationsDTO)session.createQuery("select id as id,startDate as startDate,endDate as endDate from CourseDurationsDTO where status= 1 and id in (select cir.durationId from TrainingCirculationDTO cir,TrainingNominationDTO nom where nom.requestId="+dto.getRequestId()+" and cir.id=nom.cirId and cir.status=1 and nom.status=1)").setResultTransformer(Transformers.aliasToBean(CourseDurationsDTO.class)).uniqueResult());
						sb = "select history.historyID as historyID,history.stageID as stageID, nom.requestId as requestId from CourseDTO course,CourseDurationsDTO dur,TrainingCirculationDTO cir,TrainingNominationDTO nom,HrdgTxnRequestDTO req,RequestCommonBean history where (req.requestStatus=8 or req.requestStatus=15 or req.requestStatus=21 or req.requestStatus=27 or req.requestStatus=33 or req.requestStatus=39) and req.requestId not in (select refRequestId as refRequestId from HrdgTxnCancelRequestDTO where refRequestId=req.requestId and status=1 and requestStatus=2) and course.id=dur.courseId and cir.durationId=dur.id and cir.courseId=course.id and nom.cirId=cir.id and nom.requestId=req.requestId and course.status=1 and dur.status=1 and cir.status=1 and nom.status=1 and req.status=1 and req.requestStatus>=8 and history.status != 29 and history.requestID=req.requestId and history.stageStatus != 6 and history.stageStatus != 9 and history.historyID in (select max(historyID) from RequestCommonBean where requestID=req.requestId) and req.requestId=? and nom.nomineeSfid ='"+trainingReqBean.getNomineeSfid()+"'";//and dur.startDate >=to_date('"+trainingReqBean.getStartDate()+"','DD-MON-YYYY') and dur.endDate <=to_date('"+trainingReqBean.getEndDate()+"','DD-MON-YYYY') ;
						List list1 = session.createQuery(sb).setString(0, dto.getRequestId().toString()).setResultTransformer(Transformers.aliasToBean(HrdgTxnRequestDTO.class)).list();
						if(CPSUtils.checkList(list1))
						{
							dto.setRequestType("1");
						}
						
						sb = "Select Req.Request_status From Hrdg_request_master Req,Hrdg_training_nomination_mst Nom,Hrdg_training_cir_master Cir,Hrdg_course_duration_master Dur Where Req.Request_id="+dto.getRequestId()+" And Req.Request_id=Nom.Request_id And Nom.Cir_id=Cir.Cir_id And Cir.Course_duration_id=Dur.Course_duration_id And Req.Status=1 And Nom.Status=1 And Cir.Status=1 And Dur.Status=1 and req.request_status=50";
						List list2 = session.createSQLQuery(sb).list();
						if(CPSUtils.checkList(list2))
						{
							dto.setAlias("Attended");
						}
						else
						{
							sb = "Select Req.Request_status From Hrdg_request_master Req,Hrdg_training_nomination_mst Nom,Hrdg_training_cir_master Cir,Hrdg_course_duration_master Dur Where Req.Request_id="+dto.getRequestId()+" And Req.Request_id=Nom.Request_id And Nom.Cir_id=Cir.Cir_id And Cir.Course_duration_id=Dur.Course_duration_id And Req.Status=1 And Nom.Status=1 And Cir.Status=1 And Dur.Status=1 and dur.start_date>sysdate";
							List list3 = session.createSQLQuery(sb).list();
							if(CPSUtils.checkList(list3))
							{
								dto.setAlias("Not Attended");
							}
							else
							{
								dto.setAlias("Not Attended");
								
							}
						}
						
					}
				}
			}
			catch(Exception e)
			{
				throw e;
			}
			finally
			{
				
			}
			return list;
		}
		public List viewTrainingNomination(TrainingRequestBean trainingReqBean)throws Exception
		{
			Session session = null;
			List list = null;
			String sb = null;    
			try
			{
				session = hibernateUtils.getSession(); 
				sb = "select history.historyID as historyID,req.requestId as requestId,reqType.name as requestType from HrdgTxnRequestDTO req,RequestCommonBean history,RequestWorkDTO reqType where req.requestId=? and req.status=1 and history.status != 29 and reqType.id=history.requestTypeID and history.requestID=req.requestId and history.historyID in (select max(historyID) from RequestCommonBean where requestID=req.requestId)";//and dur.startDate >=to_date('"+trainingReqBean.getStartDate()+"','DD-MON-YYYY') and dur.endDate <=to_date('"+trainingReqBean.getEndDate()+"','DD-MON-YYYY') ;
				list = session.createQuery(sb).setString(0, trainingReqBean.getRequestId()).setResultTransformer(Transformers.aliasToBean(HrdgTxnRequestDTO.class)).list();
				if(CPSUtils.checkList(list)) 
				{
					for(int i =0;i<list.size();i++)
					{
						HrdgTxnRequestDTO dto = (HrdgTxnRequestDTO)list.get(i);
						
						List l = session.createSQLQuery("Select status From Request_workflow_history where request_id=?").setString(0, trainingReqBean.getRequestId()).list();
						if(CPSUtils.checkList(l))
						{
							Object str = l.get(0).toString();
							if(CPSUtils.compareStrings("2", str.toString()))
								dto.setStageID("1");
							else
								dto.setStageID(str.toString());
						}
						dto.setCourseDto((CourseDTO)session.createQuery("select id as id,name as name from CourseDTO where status=1 and id in (select cir.courseId from TrainingCirculationDTO cir,TrainingNominationDTO nom where nom.requestId="+dto.getRequestId()+" and cir.id=nom.cirId and cir.status=1 and nom.status=1)").setResultTransformer(Transformers.aliasToBean(CourseDTO.class)).uniqueResult());
						dto.setCourseDurationDto((CourseDurationsDTO)session.createQuery("select id as id,startDate as startDate,endDate as endDate from CourseDurationsDTO where status= 1 and id in (select cir.durationId from TrainingCirculationDTO cir,TrainingNominationDTO nom where nom.requestId="+dto.getRequestId()+" and cir.id=nom.cirId and cir.status=1 and nom.status=1)").setResultTransformer(Transformers.aliasToBean(CourseDurationsDTO.class)).uniqueResult());
						dto.setTrainingNominationDto(((TrainingNominationDTO)session.createCriteria(TrainingNominationDTO.class).add(Expression.eq("requestId", Integer.parseInt(trainingReqBean.getRequestId()))).uniqueResult()));
					}
				}
			}
			catch(Exception e)
			{
				throw e;
			}
			finally
			{
				
			}
			return list;
		}
		
		public List getBrochureAndION(TrainingRequestBean trainingReqBean) throws Exception
		{
			Session session = null; 
			List list = null;
			String sb = null;  
			try
			{
				session = hibernateUtils.getSession();
				sb = "select cir.brochure as brochure,files.filename,cir.course_duration_id as durationId from hrdg_training_cir_master cir,files files where cir.cir_id=? and cir.brochure=files.id";
				
				list = session.createSQLQuery(sb).setString(0, trainingReqBean.getCirId()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				
			}
			catch(Exception e)
			{
				throw e;
			}
			
			return list;
		}
		public List getDeptAndION(TrainingRequestBean trainingReqBean) throws Exception
		{
			Session session = null;
			List list = null;
			String sb = null;  
			try
			{
				session = hibernateUtils.getSession();
				sb = "Select Rtrim(Xmlagg (Xmlelement (E, Dept.Department_name|| ',')).Extract ('//text()'),',') AS Dename From Hrdg_circulation_departments Cir,Departments_master Dept Where Cir.Department_id=Dept.Department_id and cir.cir_id=?";
				
				list = session.createSQLQuery(sb).setString(0, trainingReqBean.getCirId()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				
			}
			catch(Exception e)
			{
				throw e;
			}
			
			return list;
		}
		public String updateRequest(TrainingRequestBean trainingReqBean,int status) throws Exception
		{
               log.debug("updateRequest() --> Start");
			
			Session session = null;
			HrdgTxnRequestDTO dto= null; 
			String message = null;
			Transaction tx = null;
			try {
				session = hibernateUtils.getSession();
				
				dto = (HrdgTxnRequestDTO) session.createCriteria(HrdgTxnRequestDTO.class).add(Expression.eq("requestId",Integer.parseInt(trainingReqBean.getRequestID()))).uniqueResult();
				dto.setRequestStatus(status);
				session.update(dto);
				message = CPSConstants.SUCCESS;
				
				
			} catch (Exception e) {
				message = CPSConstants.FAILED;
				
				throw e;
			} finally {
				
				
			}
			log.debug("updateRequest() --> End");
			return message; 
		}
		public TrainingNominationDTO getNominationCancelDetails(int requestId)throws Exception
		{
			Session session = null;
			List list = null;
			TrainingNominationDTO dto = null;
			String sb = null;
			try
			{
				session = hibernateUtils.getSession();
				sb = "select nom.id as id,nom.cirId as cirId,nom.nominationDate as nominationDate,c.id as offlineFlag,nom.currentAssignment as currentAssignment,nom.relevance as relevance,nom.lastAttendedCourse as lastAttendedCourse from TrainingNominationDTO nom,TrainingCirculationDTO cir,CourseDTO c where c.id=cir.courseId and nom.cirId=cir.id and c.status=1 and nom.status=1 and cir.status=1 and nom.requestId in (select refRequestId from HrdgTxnCancelRequestDTO where requestId=?)";
				list = session.createQuery(sb).setString(0, String.valueOf(requestId)).setResultTransformer(Transformers.aliasToBean(TrainingNominationDTO.class)).list();
				if(CPSUtils.checkList(list)){
				 dto = (TrainingNominationDTO)list.get(0);
				 dto.setCourseDto((CourseDTO)session.createCriteria(CourseDTO.class).add(Expression.eq("id",dto.getOfflineFlag())).uniqueResult());
				 sb = "select c.startDate as startDate,((To_date(c.endDate,'DD-MON-YYYY')-To_date(c.startDate,'DD-MON-YYYY'))+1) as duration from TrainingNominationDTO nom,TrainingCirculationDTO cir,CourseDurationsDTO c where c.id=cir.durationId and nom.cirId=cir.id and c.status=1 and nom.status=1 and cir.status=1 and nom.requestId in (select refRequestId from HrdgTxnCancelRequestDTO where requestId=?)";
				 List durlist = session.createQuery(sb).setString(0, String.valueOf(requestId)).setResultTransformer(Transformers.aliasToBean(CourseDTO.class)).list();
				 if(CPSUtils.checkList(durlist))
				 {
					 dto.getCourseDto().setStartDate(((CourseDTO)durlist.get(0)).getStartDate());
					dto.getCourseDto().setDays(String.valueOf(((CourseDTO)durlist.get(0)).getDuration().intValue()));
				 }
				 sb= "select (select max(historyID) from RequestCommonBean where requestID=t.requestId)as historyID,t.id as id,t.requestId as requestId,t.refRequestId as refRequestId,t.requestStatus as requestStatus from HrdgTxnCancelRequestDTO t where t.requestId=?";
				 dto.setHrdgTxnCancelReqDto((HrdgTxnCancelRequestDTO)(session.createQuery(sb).setString(0, String.valueOf(requestId)).setResultTransformer(Transformers.aliasToBean(HrdgTxnCancelRequestDTO.class)).uniqueResult()));
				 sb= "select (select max(historyID) from RequestCommonBean where requestID=t.requestId)as historyID,t.id as id,t.requestId as requestId,t.requestStatus as requestStatus from HrdgTxnRequestDTO t where t.requestId=?";
				 dto.setHrdgTxnReqDto((HrdgTxnRequestDTO)(session.createQuery(sb).setString(0, String.valueOf(dto.getHrdgTxnCancelReqDto().getRefRequestId())).setResultTransformer(Transformers.aliasToBean(HrdgTxnRequestDTO.class)).uniqueResult()));
				
				}
				
			}
			catch(Exception e)
			{
				throw e;
			}
			finally
			{
				
			}
			return dto;
		}
		public String updateCancelRequest(TrainingRequestBean trainingReqBean,int status) throws Exception
		{
               log.debug("updateCancelRequest() --> Start");
			
			Session session = null;
			HrdgTxnCancelRequestDTO dto= null;
			String message = null;
			Transaction tx = null;
			try {
				session = hibernateUtils.getSession();
				
				dto = (HrdgTxnCancelRequestDTO) session.createCriteria(HrdgTxnCancelRequestDTO.class).add(Expression.eq("requestId",Integer.parseInt(trainingReqBean.getRequestId()))).uniqueResult();
				dto.setRequestStatus(status);
				session.update(dto);
				message = CPSConstants.SUCCESS;
				
				
			} catch (Exception e) {
				message = CPSConstants.FAILED;
				
				throw e;
			} finally {
				
				
			}
			log.debug("updateRequest() --> End");
			return message; 
		}
		public List getRefRequestId(TrainingRequestBean trainingReqBean) throws Exception
		{
			Session session = null;
			List list = null;
			String sb = null;
			try
			{
				session = hibernateUtils.getSession();
				sb = "select (select max(historyID) from RequestCommonBean where requestID=t.requestId)as historyID,t.requestId as requestId from HrdgTxnRequestDTO t where t.status=1 and t.requestId in (select refRequestId from HrdgTxnCancelRequestDTO where status=1 and requestId=?)";
				list = session.createQuery(sb).setString(0,trainingReqBean.getRequestId()).setResultTransformer(Transformers.aliasToBean(HrdgTxnRequestDTO.class)).list();
				
			}
			catch(Exception e)
			{
				throw e;
			}
			finally
			{
				
			}
			return list;
		}
		public String cancelRequest(TrainingRequestBean trainingReqBean, String i) throws Exception
		{
			 log.debug("cancelRequest() --> Start");
				
				Session session = null;
				HrdgTxnRequestDTO dto= null;
				String message = null;
				Transaction tx = null;
				try {
					session = hibernateUtils.getSession();
					
					dto = (HrdgTxnRequestDTO) session.createCriteria(HrdgTxnRequestDTO.class).add(Expression.eq("requestId",trainingReqBean.getRequestID())).uniqueResult();
					dto.setStatus(Integer.parseInt(i));
					session.update(dto);
					message = CPSConstants.SUCCESS;
					
					
				} catch (Exception e) {
					message = CPSConstants.FAILED;
					
					throw e;
				} finally {
					
					
				}
				log.debug("cancelRequest() --> End");
				return message; 
		}

		public String cancelNewRequest(TrainingRequestBean trainingReqBean, String i)throws Exception
		{
			 log.debug("cancelNewRequest() --> Start");
				
				Session session = null;
				HrdgTxnCancelRequestDTO dto= null;
				String message = null;
				Transaction tx = null;
				try {
					session = hibernateUtils.getSession();
					
					dto = (HrdgTxnCancelRequestDTO) session.createCriteria(HrdgTxnCancelRequestDTO.class).add(Expression.eq("requestId",Integer.parseInt(trainingReqBean.getRequestID()))).uniqueResult();
					dto.setStatus(Integer.parseInt(i));
					session.update(dto);
					message = CPSConstants.SUCCESS;
					
					
				} catch (Exception e) {
					message = CPSConstants.FAILED;
					
					throw e;
				} finally {
					
					
				}
				log.debug("cancelNewRequest() --> End");
				return message; 
		}
		public List getNominationReqStatus(TrainingRequestBean trainingReqBean)throws Exception
		{
			Session session = null;
			List list = null;
			TrainingNominationDTO dto = null;
			String sb = null;
			try
			{
				session = hibernateUtils.getSession();
				sb = "Select * From Hrdg_training_nomination_mst Where Cir_id=? And Status=1 And Nominee_sfid='"+trainingReqBean.getNomineeSfid()+"' and request_id not in (select req.request_id from hrdg_request_master req where req.request_id=request_id and (req.request_status != 1 and req.request_status !=5 and req.request_status !=8 and req.request_status <8 ) or req.status=0) ";
				sb = "Select * From Hrdg_training_nomination_mst Where Cir_id=? And Status=1 And Nominee_sfid='"+trainingReqBean.getNomineeSfid()+"' and request_id in (select req.request_id from hrdg_request_master req where req.request_id=request_id and (req.request_status >=8) and req.status=1) ";
				list = session.createSQLQuery(sb).setString(0, String.valueOf(trainingReqBean.getCirId())).list();
				
				
			}
			catch(Exception e)
			{
				throw e;
			}
			finally
			{
				
			}
			return list;
		}
		public List getReqIPAddress(String requestId,String requestType)throws Exception
		{
			Session session = null;
			List list = null;
			TrainingNominationDTO dto = null;
			String sb = null;
			try
			{
				session = hibernateUtils.getSession();
				if(CPSUtils.compareStrings(CPSConstants.TRAININGNOMINATION, requestType))
				sb = "select ipAddress from HrdgTxnRequestDTO where requestId = ?";
				if(CPSUtils.compareStrings(CPSConstants.CANCELTRAININGNOMINATION, requestType))
					sb = "select ipAddress from HrdgTxnCancelRequestDTO where requestId = ?";
				list = session.createQuery(sb).setString(0, requestId).list();
			}
			catch(Exception e)
			{
				throw e;
			}
			finally
			{
				
			}
			return list;
		}
		public List getNominationLastAttededCourse(TrainingRequestBean trainingReqBean)throws Exception
		{
			Session session = null; 
			List list = null;
			TrainingNominationDTO dto = null;
			String sb = null;
			try
			{
				session = hibernateUtils.getSession();
				sb = "Select Details.Course_name||'('||to_char(to_date(Details.duration_start_date,'dd-Mon-yyyy'),'dd-Mon-yyyy')||' to '||to_char(to_date(Details.duration_end_date,'dd-Mon-yyyy'),'dd-Mon-yyyy')||')' as name From Hrdg_attended_course_details Details,Financial_year_master Year "+
                     "where details.year_id=year.id and (to_char(to_date(sysdate,'DD-MM-YYYY'),'YYYY')-to_char(to_date(year.from_date,'DD-MM-YYYY'),'YYYY'))<=2 and details.nominee_sfid=? and Details.status=1";
				list = session.createSQLQuery(sb).setString(0, trainingReqBean.getNomineeSfid()).list();
			}
			catch(Exception e)
			{
				throw e;
			}
			finally
			{
				
			}
			return list;
		}
		public CourseAttendedDetailsDTO getCourseDetails(TrainingRequestBean trainingReqBean)throws Exception
		{
			Session session = null; 
			List list = null;
			CourseAttendedDetailsDTO dto = null;
			String sb = null;
			try
			{
				session = hibernateUtils.getSession();
				sb = "select course.trainingTypeId as trainingTypeId,course.name as course,course.courseYear as courseYear,dur.startDate as durationStartDate,dur.endDate as durationEndDate,course.fee as fee,course.serviceTax as serviceTax from CourseDTO course,CourseDurationsDTO dur where dur.courseId = course.id and course.status=1 and dur.id=? and dur.status=1";
				dto = ((CourseAttendedDetailsDTO)session.createQuery(sb).setString(0, trainingReqBean.getCourseId()).setResultTransformer(Transformers.aliasToBean(CourseAttendedDetailsDTO.class)).uniqueResult());
			}
			catch(Exception e)
			{
				throw e;
			}
			finally
			{
				
			}
			return dto;
		}
		public String checkAttendedCourseDetails(CourseAttendedDetailsDTO dto)throws Exception
		{
			Session session = null; 
			List list = null;
			CourseAttendedDetailsDTO dto1 = null;
			String sb = null;
			String message = null;
			try
			{
				session = hibernateUtils.getSession();
				sb = "select id as id from CourseAttendedDetailsDTO where durationStartDate=? and durationEndDate=? and nomineeSfid=? and status=1";
				list = session.createQuery(sb).setString(0,dto.getDurationStartDate()).setString(1,dto.getDurationEndDate()).setString(2, dto.getNomineeSfid()).setResultTransformer(Transformers.aliasToBean(CourseAttendedDetailsDTO.class)).list();
				if(CPSUtils.checkList(list))
					message = CPSConstants.SUCCESS;
			}
			catch(Exception e)
			{
				throw e;
				
			}
			finally
			{
				
			}
			return message;
		}
		public void updateAttendedCourseDetails(CourseAttendedDetailsDTO dto, TrainingRequestBean trainingReqBean)throws Exception
		{
			 log.debug("cancelNewRequest() --> Start");
				
				Session session = null;
				CourseAttendedDetailsDTO cadDto= null;
				String message = null;
				
				String sb = null;
				try {
					session = hibernateUtils.getSession();
					sb = "select id as id,trainingTypeId as trainingTypeId,course as course,courseYear as courseYear,nomineeSfid as nomineeSfid,status as status from CourseAttendedDetailsDTO where trainingTypeId=? and course=? and courseYear=? and nomineeSfid=? and durationStartDate=? and durationEndDate=? and status=1";
					cadDto = (CourseAttendedDetailsDTO)session.createQuery(sb).setInteger(0, dto.getTrainingTypeId()).setString(1, dto.getCourse()).setString(2, dto.getCourseYear()).setString(3, dto.getNomineeSfid()).setString(3, dto.getNomineeSfid()).setString(4, dto.getDurationStartDate()).setString(5, dto.getDurationEndDate()).setResultTransformer(Transformers.aliasToBean(CourseAttendedDetailsDTO.class)).uniqueResult();
					if(trainingReqBean.getSelectStatus().equalsIgnoreCase("50"))
					cadDto.setStatus(1);
					else
						cadDto.setStatus(0);
					session.update(cadDto);
					message = CPSConstants.SUCCESS;
					
					
				} catch (Exception e) {
					message = CPSConstants.FAILED;
					
					throw e;
				} finally {
					
					
				}
				log.debug("cancelNewRequest() --> End");
				
		}
		public String getNomineeSfid(String txnId) throws Exception
		{
			Session session = null;
			List list = null;
			String sb = null;
			String nomineeSfid = null;
			try
			{
				session = hibernateUtils.getSession();

				sb= "select n.nomineeSfid as nomineeSfid from HrdgTxnRequestDTO t,TrainingNominationDTO n where n.requestId=t.requestId and t.id=?";
				list = session.createQuery(sb).setInteger(0, Integer.parseInt(txnId)).list();
				if(CPSUtils.checkList(list))
					{
					nomineeSfid = list.get(0).toString();
					}
				
			}
			catch(Exception e)
			{
				throw e;
			}
			finally
			{
				
			}
			return nomineeSfid;
		}
		public String updateCancelStatusOfRequest(TrainingRequestBean trainingReqBean)throws Exception
		{
			  log.debug("updateRequest() --> Start");
				
				Session session = null;
				HrdgTxnRequestDTO dto= null;
				TrainingNominationDTO nomDto = null;
				String message = null;
				Transaction tx = null;
				try {
					session = hibernateUtils.getSession();
					
					dto = (HrdgTxnRequestDTO) session.createCriteria(HrdgTxnRequestDTO.class).add(Expression.eq("requestId",Integer.parseInt(trainingReqBean.getRequestID()))).uniqueResult();
					dto.setStatus(0);
					session.update(dto);
					message = CPSConstants.SUCCESS;
//					nomDto = (TrainingNominationDTO) session.createCriteria(TrainingNominationDTO.class).add(Expression.eq("requestId",Integer.parseInt(trainingReqBean.getRequestID()))).uniqueResult();
//					nomDto.setStatus(0);
//					session.update(nomDto);
//					message = CPSConstants.SUCCESS;
					
				} catch (Exception e) {
					message = CPSConstants.FAILED;
					
					throw e;
				} finally {
					
					
				}
				log.debug("updateRequest() --> End");
				return message; 
		}
		public List getIONMstList(TrainingRequestBean trainingReqBean)throws Exception
		{
			Session session = null;
			List list = null;			
			String sb = null;
			
			try
			{
				session = hibernateUtils.getSession();
				sb = "select i.id as id,i.letterFormatId as letterFormatId,i.ionDate as ionDate,i.letterNumber||'-('||i.ionDate||')' as letterNumber from IONDTO i,LetterNumberFormatDTO l where i.status=1 and l.status=1 and i.letterFormatId=? and i.letterFormatId=l.id";
				list = session.createQuery(sb).setString(0, trainingReqBean.getLetterFormatId()).setResultTransformer(Transformers.aliasToBean(IONDTO.class)).list();
			}
			catch(Exception e)
			{
				throw e;
			}
			finally
			{
				
			}
			return list;
		}
		public List getSeriesMstList() throws Exception
		{
			Session session = null;
			List list = null;			
			String sb = null;
			
			try
			{
				session = hibernateUtils.getSession();
				sb = "select l.id as id,l.serialNum as serialNum,l.shortForm as shortForm from LetterNumberFormatDTO l where l.status=1 and l.deptNum=?  and deptNum in (select deptNum from LetterNumberSeriesDTO where status=1 and deptNum=?)";
				list = session.createQuery(sb).setString(0, CPSConstants.HRDG_DEPT_ID).setString(1, CPSConstants.HRDG_DEPT_ID).setResultTransformer(Transformers.aliasToBean(LetterNumberFormatDTO.class)).list();
			}
			catch(Exception e)
			{
				throw e;
			}
			finally
			{
				
			}
			return list;
		}
		public List checkCourseFee(TrainingRequestBean trainingReqBean)throws Exception
		{
			Session session = null;
			List list = null;			
			String sb = null;
			
			try
			{
				session = hibernateUtils.getSession();
				sb = "select c.id as id from CourseDTO c,CourseDurationsDTO d where d.courseId=c.id and d.status=1 and c.status=1 and c.fee is not null and c.fee > 0 and d.id=?";
				list = session.createQuery(sb).setString(0, trainingReqBean.getCourseId()).setResultTransformer(Transformers.aliasToBean(CourseDTO.class)).list();
			}
			catch(Exception e)
			{
				throw e;
			}
			finally
			{
				
			}
			return list;
		}
		public List getTrainingTypeList()throws Exception
		{
			Session session = null;
			List list = null;			
			String sb = null;
			
			try
			{
				session = hibernateUtils.getSession();
				sb = "from TrainingTypeDTO where status=1 order by orderNo";
				list = session.createQuery(sb).list();
			}
			catch(Exception e)
			{
				throw e;
			}
			finally
			{
				
			}
			return list;
		}

		public List getFinancialYear()throws Exception
		{
			Session session = null;
			List list = null;			
			String sb = null;
			
			try
			{
				session = hibernateUtils.getSession();
				sb = "select fym.id,to_char(from_date,'YYYY') ||'-'|| to_char(to_date,'YYYY') financialYear,to_char(from_date,'DD-Mon-YYYY') as fromDate,to_char(to_date,'DD-Mon-YYYY') as toDate from financial_year_master fym where status=1 order by financialYear";
				list = session.createSQLQuery(sb.toString()).addScalar("id", Hibernate.INTEGER).addScalar("financialYear", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(FinancialYearDTO.class)).list();
			}
			catch(Exception e)
			{
				throw e;
			}
			finally
			{
				
			}
			return list;
		}
		
}
