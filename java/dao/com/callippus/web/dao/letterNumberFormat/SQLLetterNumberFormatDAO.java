package com.callippus.web.dao.letterNumberFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.beans.dto.DepartmentsDTO;
import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.HierarchyDTO;
import com.callippus.web.beans.dto.OrgInstanceDTO;
import com.callippus.web.beans.dto.OrganizationsDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.file.FileUploadBean;
import com.callippus.web.beans.file.FilesBean;
import com.callippus.web.beans.letterNumberFormat.IONDTO;
import com.callippus.web.beans.letterNumberFormat.IonCirculatedDetailsDTO;
import com.callippus.web.beans.letterNumberFormat.LetterNumberFormatBean;
import com.callippus.web.beans.letterNumberFormat.LetterNumberFormatDTO;
import com.callippus.web.beans.letterNumberFormat.LetterNumberSeriesDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.hrdg.training.beans.dto.CourseAttendedDetailsDTO;


@Service
public class SQLLetterNumberFormatDAO implements ILetterNumberFormatDAO{
	private static Log log = LogFactory.getLog(SQLLetterNumberFormatDAO.class);
	private static final long serialVersionUID = 5611041426238862266L;

	@Autowired

	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	



	@Override
	@SuppressWarnings("unchecked")
	public List getDeptList() throws Exception
	{
	
			Session session = null;
			List list = null;
			String sb = null;
			try
			{
				session = hibernateUtils.getSession();
				sb = " select department_id as id,department_name as deptName from dept_view";
				list = session.createSQLQuery(sb).addScalar("id", Hibernate.INTEGER).addScalar("deptName",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(DepartmentsDTO.class)).list();
				
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

	@Override
	public List checkLetterNumberSeries(LetterNumberFormatBean letterNumberFormatBean)throws Exception
	{
		Session session = null; 
		List list = null;
		CourseAttendedDetailsDTO dto1 = null;
		String sb = null;
		String message = null;
		try
		{
			session = hibernateUtils.getSession();
			sb = "select id as id from LetterNumberSeriesDTO where deptNum=? and deptCode=? and deptSeriesStartNum=? and deptSeriesEndNum=? and status=1";
			if(!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getDeptShortName()))
				sb +=" and deptShortName='"+letterNumberFormatBean.getDeptShortName()+"'";
			 if(!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getId()))
				  sb +=" and id != "+letterNumberFormatBean.getId();
			list = session.createQuery(sb).setString(0, letterNumberFormatBean.getDeptNum()).setString(1, letterNumberFormatBean.getDeptCode()).setString(2, letterNumberFormatBean.getDeptSeriesStartNum()).setString(3, letterNumberFormatBean.getDeptSeriesEndNum()).setResultTransformer(Transformers.aliasToBean(LetterNumberSeriesDTO.class)).list();
			
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

	@Override
	public String updateLetterNumberSeries(LetterNumberFormatBean letterNumberFormatBean)throws Exception
	{
		log.debug("cancelNewRequest() --> Start");
		
		Session session = null;
		CourseAttendedDetailsDTO cadDto= null;
		String message = null;
		
		String sb = null;
		try {
			session = hibernateUtils.getSession();

			
			sb = "update LetterNumberSeriesDTO set deptNum=?,deptCode=?,deptSeriesStartNum=?,deptSeriesEndNum=?,deptShortName=?,lastModifiedDate=sysdate,lastModifiedBy=?";
			
				
			sb += "where id="+letterNumberFormatBean.getId();
		
		
			Query qry = session.createQuery(sb);
			qry.setString(0, letterNumberFormatBean.getDeptNum());
			qry.setString(1, letterNumberFormatBean.getDeptCode());
			qry.setString(2, letterNumberFormatBean.getDeptSeriesStartNum());
			qry.setString(3, letterNumberFormatBean.getDeptSeriesEndNum());
			qry.setString(4, letterNumberFormatBean.getDeptShortName());
			qry.setString(5, letterNumberFormatBean.getSfid());
		
		
		qry.executeUpdate();
		message = CPSConstants.UPDATE;
			
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			
			throw e;
		} finally {
			
			
		}
		log.debug("cancelNewRequest() --> End");
		return message;
	}
	@Override
	public List getLetterNumberSeriesList()throws Exception
	{
		Session session = null; 
		List list = null;
		CourseAttendedDetailsDTO dto1 = null;
		String sb = null;
		String message = null;
		try
		{
			session = hibernateUtils.getSession();
			session.flush();
			sb = "select dept.department_name as deptName,letter.id as id,letter.department_id as deptNum,letter.dept_short_name as deptShortName,letter.dept_code as deptCode,letter.SERIES_START_NUMBER as deptSeriesStartNum,letter.SERIES_END_NUMBER as deptSeriesEndNum from dept_view dept,LETTER_NUMBER_SERIES_MASTER letter where letter.status=1 and dept.department_id=letter.department_id";
			
			list = session.createSQLQuery(sb).addScalar("deptName", Hibernate.STRING).addScalar("id", Hibernate.INTEGER).addScalar("deptNum", Hibernate.INTEGER).addScalar("deptShortName", Hibernate.STRING).addScalar("deptCode", Hibernate.STRING).addScalar("deptSeriesStartNum", Hibernate.INTEGER).addScalar("deptSeriesEndNum", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(LetterNumberSeriesDTO.class)).list();
			
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
	@Override
	public List getLetterNumberSerisList()throws Exception
	{
		Session session = null; 
		List list = null;
		CourseAttendedDetailsDTO dto1 = null;
		String sb = null;
		String message = null;
		try
		{
			session = hibernateUtils.getSession();
			session.flush();
			sb = "select dept.department_name as deptName,letter.id as id,letter.department_id as deptNum,letter.dept_short_name as deptShortName,letter.dept_code as deptCode,letter.SERIES_START_NUMBER as deptSeriesStartNum,letter.SERIES_END_NUMBER as deptSeriesEndNum,letter.SERIES_START_NUMBER||'-'||letter.SERIES_END_NUMBER as series from dept_view dept,LETTER_NUMBER_SERIES_MASTER letter where letter.status=1 and dept.department_id=letter.department_id";
			
			list = session.createSQLQuery(sb).addScalar("deptName", Hibernate.STRING).addScalar("id", Hibernate.INTEGER).addScalar("deptNum", Hibernate.INTEGER).addScalar("deptShortName", Hibernate.STRING).addScalar("deptCode", Hibernate.STRING).addScalar("deptSeriesStartNum", Hibernate.INTEGER).addScalar("deptSeriesEndNum", Hibernate.INTEGER).addScalar("series", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(LetterNumberSeriesDTO.class)).list();
			
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
	@Override
	public List getLetterNumberFormatList(LetterNumberFormatBean letterNumberFormatBean)throws Exception
	{
		Session session = null; 
		List list = null;
		CourseAttendedDetailsDTO dto1 = null;
		String sb = null;
		String message = null;
		try
		{
			session = hibernateUtils.getSession();
			session.flush();
			sb = "select dept.department_name as deptName,fmt.id as id,fmt.department_id as deptNum,fmt.topic as topic,fmt.short_form as shortForm,fmt.year_type as yearType,fmt.serial_number as serialNum,fmt.running_number as runningNum,(select id from file_type where id=fmt.file_type_id) as fileTypeId,(select name from file_type where id=fmt.file_type_id) as fileTypeName from dept_view dept,LETTER_NUMBER_SERIES_MASTER letter,letter_number_format_master fmt where letter.status=1 and fmt.status=1 and fmt.department_id=letter.department_id and dept.department_id=letter.department_id and fmt.department_id="+letterNumberFormatBean.getDeptNum()+" order by fmt.serial_number,fmt.topic";
			if(!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getDeptNum()))
			list = session.createSQLQuery(sb).addScalar("deptName", Hibernate.STRING).addScalar("id", Hibernate.INTEGER).addScalar("deptNum", Hibernate.INTEGER).addScalar("topic", Hibernate.STRING).addScalar("shortForm", Hibernate.STRING).addScalar("yearType", Hibernate.STRING).addScalar("serialNum", Hibernate.INTEGER).addScalar("runningNum", Hibernate.INTEGER).addScalar("fileTypeId", Hibernate.INTEGER).addScalar("fileTypeName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(LetterNumberFormatDTO.class)).list();
			
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
	@Override
	public List checkLetterNumberFormat(LetterNumberFormatBean letterNumberFormatBean)throws Exception
	{
		Session session = null; 
		List list = null;
		CourseAttendedDetailsDTO dto1 = null;
		String sb = null;
		String message = null;
		try
		{
			session = hibernateUtils.getSession();
			sb = "select id as id from LetterNumberFormatDTO where deptNum=? and upper(topic)=? and upper(shortForm)=? and serialNum=? and status=1";
//			if(!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getDeptShortName()))
//				sb +=" and deptShortName='"+letterNumberFormatBean.getDeptShortName()+"'";
			 if(!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getId()))
				  sb +=" and id != "+letterNumberFormatBean.getId();
			list = session.createQuery(sb).setString(0, letterNumberFormatBean.getDeptNum()).setString(1, letterNumberFormatBean.getTopic().toUpperCase()).setString(2, letterNumberFormatBean.getShortForm().toUpperCase()).setString(3, letterNumberFormatBean.getSerialNum()).setResultTransformer(Transformers.aliasToBean(LetterNumberFormatDTO.class)).list();
			
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
	@Override
	public String updateLetterNumberFormat(LetterNumberFormatBean letterNumberFormatBean)throws Exception
	{
		log.debug("cancelNewRequest() --> Start");
		
		Session session = null;
		CourseAttendedDetailsDTO cadDto= null;
		String message = null;
		
		String sb = null;
		try {
			session = hibernateUtils.getSession();

			
			sb = "update LetterNumberFormatDTO set deptNum=?,topic=?,shortForm=?,serialNum=?,yearType=?,runningNum=?,lastModifiedDate=sysdate,lastModifiedBy=?,fileTypeId=?";
			
				
			sb += "where id="+letterNumberFormatBean.getId();
		
		
			Query qry = session.createQuery(sb);
			qry.setString(0, letterNumberFormatBean.getDeptNum());
			qry.setString(1, letterNumberFormatBean.getTopic());
			qry.setString(2, letterNumberFormatBean.getShortForm());
			qry.setString(3, letterNumberFormatBean.getSerialNum());
			qry.setString(4, letterNumberFormatBean.getYearType());
			qry.setString(5, letterNumberFormatBean.getRunningNum());
			qry.setString(6, letterNumberFormatBean.getSfid());
			qry.setString(7, letterNumberFormatBean.getFileTypeId());
		
		
		qry.executeUpdate();
		message = CPSConstants.UPDATE;
			
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			
			throw e;
		} finally {
			
			
		}
		log.debug("cancelNewRequest() --> End");
		return message;
	}
	@Override
	public List getLetterNumberFormatDetails(LetterNumberFormatBean letterNumberFormatBean)throws Exception
	{
		Session session = null; 
		List list = null;
		CourseAttendedDetailsDTO dto1 = null; 
		String sb = null;
		String message = null;
		try
		{
			session = hibernateUtils.getSession();
			session.flush();
			sb = "select s.dept_code as letterNumberFormat,f.DEPARTMENT_ID as deptNum,f.SHORT_FORM as shortForm,f.SERIAL_NUMBER as serialNum,f.RUNNING_NUMBER as runningNum, "+
					"(case when YEAR_TYPE like 'financial' then (select to_char(from_date,'YYYY')||'-'||to_char(to_date,'YYYY') from financial_year_master where status=1 and sysdate<to_date and sysdate>from_date) "
			+ "else  (select name from year_master where status=1 and name=to_char(to_date(sysdate,'dd-mm-yy'),'yyyy'))end) as deptName"+
				" from LETTER_NUMBER_FORMAT_MASTER f,letter_number_series_master s where f.status=1 and s.department_id=f.department_id and s.status=1";
			 sb +=" and f.id = "+letterNumberFormatBean.getLetterFormatId();
			list = session.createSQLQuery(sb).addScalar("deptNum", Hibernate.INTEGER).addScalar("shortForm", Hibernate.STRING).addScalar("serialNum", Hibernate.INTEGER).addScalar("runningNum", Hibernate.INTEGER).addScalar("deptName", Hibernate.STRING).addScalar("letterNumberFormat", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(LetterNumberFormatDTO.class)).list();
			
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
	@Override
	public List getIONMstList(LetterNumberFormatBean letterNumberFormatBean)throws Exception
	{
		Session session = null; 
		List list = null;
		CourseAttendedDetailsDTO dto1 = null; 
		String sb = null;
		String message = null;
		try
		{
			session = hibernateUtils.getSession();
			session.flush();
			//sb = "select id as id,letterFormatId as letterFormatId,letterNumber as letterNumber,ionDate as ionDate,delegation as delegation,circulationStatus as circulationStatus from IONDTO where status=1 and letterFormatId=? order by to_date(ionDate,'dd-MM-YYYY') desc,id desc";
			sb = "select i.id as id,i.letter_format_id as letterFormatId,i.Letter_number as letterNumber,i.ion_Date as ionDate,i.delegation as delegation ,(select (name_in_service_book||'('||sfid||')') AS createdBy from emp_master where sfid=i.created_by) AS ionCreatedBy,i.creation_date as ionCreationDate," +
				 "(select (name_in_service_book||'('||sfid||')') AS lastModifiedBy FROM emp_master WHERE sfid=i.last_modified_by) AS lastModifiedBy,i.last_modified_date as lastmodifieddate,i.circulation_Status AS circulationStatus,i.status as status,i.circulation_Status as createdBy from ion_master i where i.letter_format_id = ? order by to_date(i.ion_date,'dd-MM-YYYY') desc,i.id desc";
			list = session.createSQLQuery(sb).addScalar("id", Hibernate.INTEGER).addScalar("letterFormatId", Hibernate.INTEGER).addScalar("letterNumber", Hibernate.STRING).addScalar("ionDate", Hibernate.STRING).addScalar("delegation", Hibernate.STRING).addScalar("circulationStatus", Hibernate.INTEGER).addScalar("status", Hibernate.INTEGER).addScalar("createdBy", Hibernate.STRING)
			       .addScalar("ionCreatedBy", Hibernate.STRING).addScalar("ionCreationDate", Hibernate.STRING).addScalar("lastModifiedBy", Hibernate.STRING).addScalar("lastModifiedDate", Hibernate.STRING).setString(0, letterNumberFormatBean.getLetterFormatId()).setResultTransformer(Transformers.aliasToBean(IONDTO.class)).list();
			
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
	@Override
	public String updateLetterNumberFormatRunningNum(LetterNumberFormatBean letterNumberFormatBean)throws Exception
	{
      log.debug("updateLetterNumberFormatRunningNum() --> Start");
		
		Session session = null;
		CourseAttendedDetailsDTO cadDto= null;
		String message = null;
		
		String sb = null;
		try {
			session = hibernateUtils.getSession();

			
			sb = "update LetterNumberFormatDTO set runningNum=runningNum+1,lastModifiedDate=sysdate,lastModifiedBy=?";
			sb += "where id="+letterNumberFormatBean.getLetterFormatId();
		
		
			Query qry = session.createQuery(sb);
			
			qry.setString(0, letterNumberFormatBean.getSfid());
		
		
		qry.executeUpdate();
		message = CPSConstants.UPDATE;
			
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			
			throw e;
		} finally {
			
			
		}
		log.debug("updateLetterNumberFormatRunningNum() --> End");
		return message;
		
	}
	@Override
	public List checkLetterNumberFormatDetails(LetterNumberFormatBean letterNumberFormatBean)throws Exception
	{
		Session session = null; 
		List list = null;
		CourseAttendedDetailsDTO dto1 = null;
		String sb = null;
		String message = null;
		try
		{
			session = hibernateUtils.getSession();
			sb = "select id as id from IONDTO where letterNumber=? and status=1";
			if(!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getId()))
				sb += " and id !="+letterNumberFormatBean.getId();

			list = session.createQuery(sb).setString(0, letterNumberFormatBean.getLetterNumber()).setResultTransformer(Transformers.aliasToBean(IONDTO.class)).list();
			
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
	@Override
	public String updateLetterNumberFormatDetails(LetterNumberFormatBean letterNumberFormatBean)throws Exception
	{
log.debug("updateLetterNumberFormatDetails() --> Start");
		
		Session session = null;
		CourseAttendedDetailsDTO cadDto= null;
		String message = null;
		
		String sb = null;
		try {
			session = hibernateUtils.getSession();

			
			sb = "update IONDTO set delegation=?,ionDate=?,lastModifiedDate=sysdate,lastModifiedBy=?";
			sb += "where id="+letterNumberFormatBean.getId();
		
		
			Query qry = session.createQuery(sb);
			qry.setString(0, letterNumberFormatBean.getDelegation());
			qry.setString(1, letterNumberFormatBean.getIonDate());
			qry.setString(2, letterNumberFormatBean.getSfid());
		
		
		qry.executeUpdate();
		message = CPSConstants.UPDATE;
			
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			
			throw e;
		} finally {
			
			
		}
		log.debug("updateLetterNumberFormatDetails() --> End");
		return message;
	}
	@Override
	public String updateIONdetailsForLetter(IONDTO idto, String id)throws Exception
	{
		log.debug("updateLetterNumberFormatDetails() --> Start");
		
		Session session = null;
		CourseAttendedDetailsDTO cadDto= null;
		String message = null;
		
		String sb = null;
		try {
			session = hibernateUtils.getSession();
			session.flush();
			
			
			sb = "update IONDTO set subject=?,content=?,enclosers=?,ionInitiatedSfid=?,lastModifiedDate=sysdate,lastModifiedBy=?,circulationStatus=?,reference=?,ionInitiatedRoleId=?,ionForwardSfid=?,ionForwardRoleId=?,ionApprovedSfid=?,ionApprovedRoleId=?,publicStatus=? ";
			sb += "where id="+id; 
			
		
			Query qry = session.createQuery(sb);
			qry.setString(0, replaceMSWordDoc(idto.getSubject()));
			qry.setString(1, replaceMSWordDoc(idto.getContent()));
			qry.setString(2, replaceMSWordDoc(idto.getEnclosers()));
			qry.setString(3, idto.getIonInitiatedSfid());
			qry.setString(4, idto.getSfid());
			qry.setString(5, String.valueOf(idto.getCirculationStatus()));
			qry.setString(6, replaceMSWordDoc(idto.getReference()));
			qry.setString(7, idto.getIonInitiatedRoleId());
			qry.setString(8, idto.getIonForwardSfid());
			qry.setString(9, idto.getIonForwardRoleId());
			qry.setString(10, idto.getIonApprovedSfid());
			qry.setString(11, idto.getIonApprovedRoleId());
			if(CPSUtils.isNullOrEmpty(idto.getPublicStatus()))idto.setPublicStatus("N"); 
			qry.setString(12, idto.getPublicStatus());
		
			log.debug("updateLetterNumberFormatDetails() --> at update :"+idto.toString());
			System.out.println("idto.toString():"+idto.toString());
		qry.executeUpdate();
		session.flush();
		message = CPSConstants.UPDATE;
			
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			
			throw e;
		} finally {
			
			
		}
		log.debug("updateLetterNumberFormatDetails() --> End");
		return message;
	}
	private String replaceMSWordDoc(String str) throws Exception
	{
		if(!CPSUtils.isNullOrEmpty(str))
		{
		while(str.contains("<!--[if gte mso"))
		{
		int one = str.indexOf("<!--[if gte mso");
		int two = str.indexOf("<![endif]-->")+12;
		String str3 = str.substring(0, one);
		String str4 = str.substring(two, str.length());
		str=str3+str4;
		System.out.println(str);
		}
		String[] tagAry ={"<p","<img","<span","<b","<i","<u"};
		for(int h = 0;h<tagAry.length;h++)
		{
			String strr = tagAry[h];
			String[] aa = str.split(strr);
			int j=0;
		for(int k = 0;k<aa.length;k++)
        {
        	CommonDTO dto = new CommonDTO();
            dto.setName(strr);
            int one=0;
                if (j==0)
        	    one = str.indexOf(strr);
                else
                	one = str.indexOf(strr, j);
        	    
        	    if(one != -1)
        	    {
        	    	dto.setId(one);
        	    	int temp = str.indexOf(">", one);
        	    	
        	    	
        	    	String str4=str.substring(dto.getId(),temp);
        	    	if(str4.contains("mso") || str4.contains("Mso") || str4.contains("MSO"))
        			{
        				String str3 = str.substring(0, dto.getId());
        				String str7 = dto.getName();
        				String str6 = str.substring(temp,str.length());
        				
        	    		str=str3+str7+str6;
        			}
        	    }
        	    j=one+dto.getName().length();
        }
		}
		System.out.println(str);

		}
		return str;
	}
	@Override
	public String deleteIONDeptDetails(String ionId,String str,String str1,String refOrgRoleId)throws Exception
	{
		log.debug("deleteIONDeptDetails() --> Start");
		String message = null;
		Session session = null;
		
		String sb = null;
		try {
			session = hibernateUtils.getSession();
			
			sb = "delete "+str+" where ionId=?";
			if(!CPSUtils.isNullOrEmpty(str1) && !CPSUtils.compareStrings(str, "IonCirculatedDetailsDTO") && !CPSUtils.compareStrings(str, "IonFileDTO"))
				sb += " and orgOrHirarchy='"+str1+"'";
			if(!CPSUtils.isNullOrEmpty(refOrgRoleId) && CPSUtils.compareStrings(str, "IonCirculatedDetailsDTO"))
				sb += " and refOrgRoleId='"+refOrgRoleId+"'";
			if(!CPSUtils.isNullOrEmpty(str1) && CPSUtils.compareStrings(str, "IonFileDTO"))
				sb += " and fileId='"+str1+"'";
//			if(!CPSUtils.isNullOrEmpty(str1) && CPSUtils.compareStrings(str, "IonCirculatedDetailsDTO"))
//				sb += " and sfid='"+str1+"'";
			Query qry = session.createQuery(sb);
			qry.setString(0, ionId);
		
		    qry.executeUpdate();
			

			message = CPSConstants.DELETE;
			log.debug("message -->" + message);
			session.flush();
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			
		}
		log.debug("deleteIONDeptDetails() --> End");
		return message;
	}
	@Override
	public List getIONMasterList(LetterNumberFormatBean letterNumberFormatBean)throws Exception
	{
		Session session = null; 
		List list = null;
		CourseAttendedDetailsDTO dto1 = null; 
		String sb = null;
		String message = null;
		try
		{
			session = hibernateUtils.getSession();
			session.flush();
			sb = "select i.id as id,i.letterFormatId as letterFormatId,i.letterNumber||'-('||i.ionDate||')' as letterNumber,i.ionDate as ionDate,i.delegation as delegation,i.circulationStatus as circulationStatus from IONDTO i,LetterNumberFormatDTO l where i.status=1 and l.status = 1 and i.letterFormatId=? and l.id=i.letterFormatId and l.deptNum=?";
			list = session.createQuery(sb).setString(0, letterNumberFormatBean.getLetterFormatId()).setString(1, letterNumberFormatBean.getDeptNum()).setResultTransformer(Transformers.aliasToBean(IONDTO.class)).list();
			
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
	@Override
	public List getDepartmentList()throws Exception
	{
		Session session = null;
		List list = null;
		String sb = null;
		try
		{
			session = hibernateUtils.getSession();
			sb = " select department_id as id,department_name as deptName from dept_view";
			list = session.createSQLQuery(sb).addScalar("id", Hibernate.INTEGER).addScalar("deptName",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(DepartmentsDTO.class)).list();
			
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
	@Override
	public List designationList() throws Exception
	{
		Session session = null;
		List list = null;
		String sb = null;
		try
		{
			session = hibernateUtils.getSession();
			sb = "from DesignationDTO where status =1";
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
	@Override
	public List getRole(LetterNumberFormatBean letterNumberFormatBean) throws Exception
	{
		Session session = null;
		List list = null;
		String sb = null;
		try
		{
			session = hibernateUtils.getSession();
			sb = "from ApplicationRoleMappingDTO where status =1 and upper(sfid) like '"+letterNumberFormatBean.getSfid()+"'";
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
	@Override
	public List getDeptListBasedOnRole(LetterNumberFormatBean letterNumberFormatBean)throws Exception
	{
		Session session = null;
		List list = null;
		String sb = null;
		try
		{
			session = hibernateUtils.getSession();
			sb = "select v.department_id as id,dept.department_name as deptName from dept_view v,departments_master dept where dept.department_id=v.department_id and dept.department_id in (SELECT ori.department_id  FROM emp_role_mapping erm,org_role_instance ori Where Ori.Org_role_id=Erm.Org_role_id AND erm.sfid LIKE '"+letterNumberFormatBean.getSfid()+"' UNION  Select Ori.Department_id  From Org_role_instance Ori,emp_master emp  Where Ori.Org_role_id=Emp.Office_id  and emp.sfid LIKE '"+letterNumberFormatBean.getSfid()+"')";
			sb = "Select techIds as id,Techs as deptName From (select deptId,deptName,nvl(tech,deptName) as techs,nvl(techId,deptId)as techIds from(Select Dept.Department_id as deptId,Dept.Department_name deptName,  (Select    dm1.department_name  FROM departments_master dm1  WHERE Dm1.Status               =1  AND Dm1.Dept_hierarchy_id      =5    START WITH Dm1.Department_id =dept.department_id    Connect By Dm1.Department_id = Prior Dm1.Parent_department_id  ) As Tech,  (Select    dm1.department_id  FROM departments_master dm1  WHERE Dm1.Status               =1  AND Dm1.Dept_hierarchy_id      =5    START WITH Dm1.Department_id =dept.department_id    Connect By Dm1.Department_id = Prior Dm1.Parent_department_id  ) AS TechId FROM Departments_master Dept WHERE dept.department_id IN (SELECT ori.department_id  FROM Emp_role_mapping Erm,    org_role_instance ori  WHERE Ori.Org_role_id=Erm.Org_role_id  AND erm.sfid LIKE '"+letterNumberFormatBean.getSfid()+"'  UNION  SELECT Ori.Department_id  FROM Org_role_instance Ori,    emp_master emp  Where Ori.Org_role_id=Emp.Office_id  And Emp.Sfid Like '"+letterNumberFormatBean.getSfid()+"'))) group by Techs,techIds ";
			list = session.createSQLQuery(sb).addScalar("id", Hibernate.INTEGER).addScalar("deptName",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(DepartmentsDTO.class)).list();
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
	@Override
	public List getSfidList(String sfid,String type,LetterNumberFormatBean letterNumberFormatBean )throws Exception
	{

		Session session = null;
		List list = null;
		String sb = null;
		try
		{
			session = hibernateUtils.getSession();
			
			sb = "select Emp.Sfid AS userSfid,emp.sfid||'-'||Emp.Name_in_service_book As nameInServiceBook from emp_master emp where emp.status=1 and emp.working_location in(select working_location from emp_master where sfid='"+sfid+"' and status=1) order by  emp.sfid"; // query is modified to get the list of employees based on working location
			list = session.createSQLQuery(sb).addScalar("userSfid",Hibernate.STRING).addScalar("nameInServiceBook",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(EmployeeBean.class)).list();
			
			if(!CPSUtils.isNullOrEmpty(sfid) && !CPSUtils.isNullOrEmpty(type) && CPSUtils.compareStrings(type, "sfid"))
			{
				if(letterNumberFormatBean.getCirculationStatus().equals("0") || letterNumberFormatBean.getCirculationStatus().equals("1")){
					//sb = "select sfid as userSfid,name_in_service_book as nameInServiceBook from emp_master where status =1 and sfid in (select emp.sfid from emp_master emp where emp.office_id in (select office_id  as dept from emp_master where sfid='"+sfid+"' union select role.department_id as dept from org_role_instance role,emp_role_mapping emprole where role.org_role_id=emprole.org_role_id and emprole.sfid='"+sfid+"')union select emp.sfid from org_role_instance role,emp_role_mapping emprole,emp_master emp where role.org_role_id=emprole.org_role_id and emprole.sfid=emp.sfid and role.department_id in(select office_id  as dept from emp_master where sfid='"+sfid+"' union select role.department_id as dept from org_role_instance role,emp_role_mapping emprole where role.org_role_id=emprole.org_role_id and emprole.sfid='"+sfid+"')) order by name_in_service_book";  // query is modified to get the sfid for 21735S
					sb = "select sfid as userSfid,name_in_service_book as nameInServiceBook from emp_master where status =1 and sfid in (select emp.sfid from emp_master emp where emp.office_id in (select office_id  as dept from emp_master where sfid='"+sfid+"' union select role.department_id as dept from org_role_instance role,emp_role_mapping emprole where role.org_role_id=emprole.org_role_id and emprole.sfid='"+sfid+"')union select emp.sfid from org_role_instance role,emp_role_mapping emprole,emp_master emp where role.org_role_id=emprole.org_role_id and emprole.sfid=emp.sfid and role.department_id in(select office_id  as dept from emp_master where sfid='"+sfid+"' union select role.department_id as dept from org_role_instance role,emp_role_mapping emprole where role.org_role_id=emprole.org_role_id and emprole.sfid='"+sfid+"') Union Select Sfid From Emp_role_mapping Where Parent_role_id In (Select Ori.Department_id From Emp_role_mapping Erm,Org_role_instance Ori Where Erm.Parent_role_id=Ori.Org_role_id And Erm.Status  =1 And Erm.Sfid Like '"+sfid+"')) order by name_in_service_book";
					list = session.createSQLQuery(sb).addScalar("userSfid",Hibernate.STRING).addScalar("nameInServiceBook",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(EmployeeBean.class)).list();
				}
				if(letterNumberFormatBean.getRefOrgRoleId()!=null){
					if(letterNumberFormatBean.getRefOrgRoleId().equals("29")){ // for adding SF0095 in the mis List
						if(letterNumberFormatBean.getCirculationStatus().equals("1") && letterNumberFormatBean.getCirculateVisible().equals("1")){
							sb = "(select em.sfid as userSfid,em.name_in_service_book as nameInServiceBook from emp_master em,org_role_instance ori where em.status =1 and em.office_id=ori.org_role_id and ori.org_role_id ='"+letterNumberFormatBean.getRefOrgRoleId()+"' and sfid in (select emp.sfid from emp_master emp where emp.office_id in (select office_id  as dept from emp_master where sfid='"+sfid+"' union select role.department_id as dept from org_role_instance role,emp_role_mapping emprole where role.org_role_id=emprole.org_role_id and emprole.sfid='"+sfid+"')union select emp.sfid from org_role_instance role,emp_role_mapping emprole,emp_master emp where role.org_role_id=emprole.org_role_id and emprole.sfid=emp.sfid and role.department_id in(select office_id  as dept from emp_master where sfid='"+sfid+"' union select role.department_id as dept from org_role_instance role,emp_role_mapping emprole where role.org_role_id=emprole.org_role_id and emprole.sfid='"+sfid+"')) ) union (SELECT emp.sfid AS userSfid,emp.NAME_IN_SERVICE_BOOK AS nameInServiceBook FROM EMP_MASTER emp WHERE EMP.SFID ='"+sfid+"')  ORDER BY nameInServiceBook";
							list = session.createSQLQuery(sb).addScalar("userSfid",Hibernate.STRING).addScalar("nameInServiceBook",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(EmployeeBean.class)).list();
						}
					}
				}else{
					if(letterNumberFormatBean.getCirculateVisible()!=null){
						if(letterNumberFormatBean.getCirculationStatus().equals("1") && letterNumberFormatBean.getCirculateVisible().equals("1")){
							sb = "select em.sfid as userSfid,em.name_in_service_book as nameInServiceBook from emp_master em,org_role_instance ori where em.status =1 and em.office_id=ori.org_role_id and ori.org_role_id ='"+letterNumberFormatBean.getRefOrgRoleId()+"' and sfid in (select emp.sfid from emp_master emp where emp.office_id in (select office_id  as dept from emp_master where sfid='"+sfid+"' union select role.department_id as dept from org_role_instance role,emp_role_mapping emprole where role.org_role_id=emprole.org_role_id and emprole.sfid='"+sfid+"')union select emp.sfid from org_role_instance role,emp_role_mapping emprole,emp_master emp where role.org_role_id=emprole.org_role_id and emprole.sfid=emp.sfid and role.department_id in(select office_id  as dept from emp_master where sfid='"+sfid+"' union select role.department_id as dept from org_role_instance role,emp_role_mapping emprole where role.org_role_id=emprole.org_role_id and emprole.sfid='"+sfid+"')) order by name_in_service_book";
							list = session.createSQLQuery(sb).addScalar("userSfid",Hibernate.STRING).addScalar("nameInServiceBook",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(EmployeeBean.class)).list();
						}
					}
				}
			}
			if(!CPSUtils.isNullOrEmpty(sfid) && !CPSUtils.isNullOrEmpty(type) && CPSUtils.compareStrings(type, "forwardBy"))
			{
				 String roleId ="";
				//sb = "select get_boss('"+sfid+"')as userSfid from dual union select get_head('"+sfid+"',7) as userSfid from dual";
				//list = session.createSQLQuery(sb).addScalar("userSfid",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(EmployeeBean.class)).list();
				   if(letterNumberFormatBean.getIonScreenType().equals("searchAndEdit")) {  
					   Object  roleId1=session.createSQLQuery("select  org_role_id from emp_role_mapping where status=1 and sfid='"+sfid+"' and org_role_id="+letterNumberFormatBean.getDeptNum()+"").uniqueResult();
					  if(!CPSUtils.isNullOrEmpty(roleId1)){
						  roleId =((BigDecimal)roleId1).toString();
						sb = "select get_superior_boss('"+sfid+"',"+roleId+")as userSfid from dual";
						list = session.createSQLQuery(sb).addScalar("userSfid",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(EmployeeBean.class)).list();
					}else{
						sb = "select get_superior_boss('"+sfid+"','')as userSfid from dual";
						list = session.createSQLQuery(sb).addScalar("userSfid",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(EmployeeBean.class)).list();
					     }
				    }
					 
				    if(letterNumberFormatBean.getIonScreenType().equals("circulateIon")){
				    	 Object  roleId1=session.createSQLQuery("select  org_role_id from emp_role_mapping where status=1 and sfid='"+sfid+"' and org_role_id="+letterNumberFormatBean.getRefOrgRoleId()+"").uniqueResult();
						  if(!CPSUtils.isNullOrEmpty(roleId1)){
							  roleId =((BigDecimal)roleId1).toString();
							sb = "select get_superior_boss('"+sfid+"',"+roleId+")as userSfid from dual";
							list = session.createSQLQuery(sb).addScalar("userSfid",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(EmployeeBean.class)).list();
						}else{
							sb = "select get_superior_boss('"+sfid+"','')as userSfid from dual";
							list = session.createSQLQuery(sb).addScalar("userSfid",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(EmployeeBean.class)).list();
						     }
				    }
					
			}
			if(!CPSUtils.isNullOrEmpty(sfid) && !CPSUtils.isNullOrEmpty(type) && CPSUtils.compareStrings(type, "approvedBy"))
			{
				sb = "select get_director('"+sfid+"') as userSfid from dual";
				list = session.createSQLQuery(sb).addScalar("userSfid",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(EmployeeBean.class)).list();
				
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

	@Override
	@SuppressWarnings("unchecked")
	public List<IONDTO> getNoticeBoardList(LetterNumberFormatBean letterNumberFormatBean)throws Exception
	{
		{		Session session = null;
		List<IONDTO> list = null;
		List<IonCirculatedDetailsDTO> list1 = new ArrayList<IonCirculatedDetailsDTO>();
		List<IonCirculatedDetailsDTO> list2 = new ArrayList<IonCirculatedDetailsDTO>();
		String sb = null;
		String sb1 = null;
		List<String> sfidList = null;
		try
		{
			session = hibernateUtils.getSession();
			session.flush();
			sb = "select Emp.Sfid AS userSfid,emp.sfid||'-'||Emp.Name_in_service_book As nameInServiceBook From Emp_master emp Where emp.sfid in((Select Emp.Sfid From Emp_master Emp Where Emp.Office_id In (Select Department_id From Org_role_instance Where Org_role_id="+letterNumberFormatBean.getRefOrgRoleId()+") Union  Select Emp.Sfid From Emp_master Emp,Emp_role_mapping M,Org_role_instance I Where Emp.Sfid=M.Sfid And M.Org_role_id=I.Org_role_id "+ 
			"And I.Department_id In (Select Department_id From Org_role_instance Where Org_role_id="+letterNumberFormatBean.getRefOrgRoleId()+"))minus Select Emp.Sfid From Emp_master Emp,Emp_role_mapping M,Org_role_instance I Where Emp.Sfid=M.Sfid And M.Org_role_id=I.Org_role_id and i.Org_role_id="+letterNumberFormatBean.getRefOrgRoleId()+") and  emp.status=1 order by Emp.Name_in_service_book";
			if(!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getRefOrgRoleId()))
				sfidList = session.createSQLQuery(sb).addScalar("userSfid",Hibernate.STRING).addScalar("nameInServiceBook",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(EmployeeBean.class)).list();
			
			
			//old query by Satya mam
			//sb1 = "Select id as id,subject as subject from ion_master where circulation_status=5 and status=1 and id in ((Select Ion_id as ionId From Ion_designation_details Where Designation_id In (Select Designation_id From Emp_master Where Sfid Like '"+letterNumberFormatBean.getSfid()+"')) Union (Select Ion_id As Ionid From Ion_sfid_details Where Sfid Like '"+letterNumberFormatBean.getSfid()+"')Union (Select Ion_id As Ionid From Ion_sfid_details Where Sfid Like '"+letterNumberFormatBean.getSfid()+"')Union (Select idd.ion_id From Org_role_instance Org,Emp_master Emp,Emp_role_mapping Rm,Ion_department_details Idd Where Org.Org_role_id=Rm.Org_role_id And Org.Is_head=1 And Idd.Department_id=Org.Department_id and emp.sfid=rm.sfid and emp.sfid Like '"+letterNumberFormatBean.getSfid()+"') Union (Select Ion_id As Ionid From Ion_org_role_details Where Org_role_id In (Select I.Org_role_id From Emp_role_mapping M,Org_role_instance I Where I.Org_role_id=M.Org_role_id And M.Sfid  Like '"+letterNumberFormatBean.getSfid()+"')) Union (select ion_id as ionId from ION_CIRCULATED_DETAILS where FORWARD_REPLAY_FILE='forwarded' and status=1 and sfid='"+letterNumberFormatBean.getSfid()+"'))";
			//modified query
			//sb1 = "Select i.id as id,i.subject as subject,i.circulation_Status as createdBy,(select (name_in_service_book||'('||sfid||')') AS initiatedBy from emp_master where sfid=i.initiated_by) AS ionInitiatedSfid,(select (name_in_service_book||'('||sfid ||')') AS forwardBy from emp_master WHERE sfid=i.forward_by) AS ionForwardSfid,(select (name_in_service_book||'('||sfid ||')') AS approvedBy from emp_master where sfid=i.approved_by) AS ionApprovedSfid,(select (name_in_service_book||'('||sfid||')') AS lastModifiedBy from emp_master where sfid=i.last_modified_by) AS lastModifiedBy from ion_master i where circulation_status=5 and status=1 and id in ((Select Ion_id as ionId From Ion_designation_details Where Designation_id In (Select Designation_id From Emp_master Where Sfid Like '"+letterNumberFormatBean.getSfid()+"')) Union (Select Ion_id As Ionid From Ion_sfid_details Where Sfid Like '"+letterNumberFormatBean.getSfid()+"')Union (Select Ion_id As Ionid From Ion_sfid_details Where Sfid Like '"+letterNumberFormatBean.getSfid()+"')Union (Select idd.ion_id From Org_role_instance Org,Emp_master Emp,Emp_role_mapping Rm,Ion_department_details Idd Where Org.Org_role_id=Rm.Org_role_id And Org.Is_head=1 And Idd.Department_id=Org.Department_id and emp.sfid=rm.sfid and emp.sfid Like '"+letterNumberFormatBean.getSfid()+"') Union (Select Ion_id As Ionid From Ion_org_role_details Where Org_role_id In (Select I.Org_role_id From Emp_role_mapping M,Org_role_instance I Where I.Org_role_id=M.Org_role_id And M.Sfid  Like '"+letterNumberFormatBean.getSfid()+"')) Union (select ion_id as ionId from ION_CIRCULATED_DETAILS where FORWARD_REPLAY_FILE='forwarded' and status=1 and sfid='"+letterNumberFormatBean.getSfid()+"'))";
			if(CPSUtils.isNullOrEmpty(letterNumberFormatBean.getRefOrgRoleId())){
				sb1 = "Select i.id as id,i.subject as subject,i.circulation_Status as createdBy,(select (name_in_service_book||'('||sfid||')') AS initiatedBy from emp_master where sfid=i.initiated_by) AS ionInitiatedSfid,(select (name_in_service_book||'('||sfid ||')') AS forwardBy from emp_master WHERE sfid=i.forward_by) AS ionForwardSfid,(select (name_in_service_book||'('||sfid ||')') AS approvedBy from emp_master where sfid=i.approved_by) AS ionApprovedSfid,(select (name_in_service_book||'('||sfid||')') AS lastModifiedBy from emp_master where sfid=i.last_modified_by) AS lastModifiedBy from ion_master i where circulation_status=5 and status=1 and id in ((Select Ion_id as ionId From Ion_designation_details Where Designation_id In (Select Designation_id From Emp_master Where Sfid Like '"+letterNumberFormatBean.getSfid()+"')) Union (Select Ion_id As Ionid From Ion_sfid_details Where Sfid Like '"+letterNumberFormatBean.getSfid()+"')Union (Select Ion_id As Ionid From Ion_sfid_details Where Sfid Like '"+letterNumberFormatBean.getSfid()+"')Union (Select idd.ion_id From Org_role_instance Org,Emp_master Emp,Emp_role_mapping Rm,Ion_department_details Idd Where Org.Org_role_id=Rm.Org_role_id And Org.Is_head=1 And Idd.Department_id=Org.Department_id and emp.sfid=rm.sfid and emp.sfid Like '"+letterNumberFormatBean.getSfid()+"') Union (Select Ion_id As Ionid From Ion_org_role_details Where Org_role_id In (Select I.Org_role_id From Emp_role_mapping M,Org_role_instance I Where I.Org_role_id=M.Org_role_id And M.Sfid  Like '"+letterNumberFormatBean.getSfid()+"')) Union (select ion_id as ionId from ION_CIRCULATED_DETAILS where FORWARD_REPLAY_FILE='forwarded' and status=1 and sfid='"+letterNumberFormatBean.getSfid()+"')minus"
                    +"(select icd.ion_id from ion_circulated_details icd where icd.sfid='"+letterNumberFormatBean.getSfid()+"'and icd.forward_replay_file in('filed','replied')))";
			}
			else{
			sb1 = "Select id as id,subject as subject,i.circulation_Status as createdBy,(select (name_in_service_book||'('||sfid||')') AS initiatedBy from emp_master where sfid=i.initiated_by) AS ionInitiatedSfid,(select (name_in_service_book||'('||sfid ||')') AS forwardBy from emp_master WHERE sfid=i.forward_by) AS ionForwardSfid,(select (name_in_service_book||'('||sfid ||')') AS approvedBy from emp_master where sfid=i.approved_by) AS ionApprovedSfid,(select (name_in_service_book||'('||sfid||')') AS lastModifiedBy from emp_master where sfid=i.last_modified_by) AS lastModifiedBy  from ion_master i" +
					" where circulation_status=5 and status=1 and id in (((Select Ion_id as ionId From Ion_designation_details Where Designation_id In (Select Designation_id From Emp_master Where Sfid Like '"+letterNumberFormatBean.getSfid()+"')) Union (Select Ion_id As Ionid From Ion_sfid_details Where Sfid Like '"+letterNumberFormatBean.getSfid()+"')Union " +
					"(Select Ion_id As Ionid From Ion_sfid_details Where Sfid Like '"+letterNumberFormatBean.getSfid()+"')Union (Select idd.ion_id From Org_role_instance Org,Emp_master Emp,Emp_role_mapping Rm,Ion_department_details Idd Where Org.Org_role_id=Rm.Org_role_id And Org.Is_head=1 And Idd.Department_id=Org.Department_id and emp.sfid=rm.sfid and emp.sfid Like '"+letterNumberFormatBean.getSfid()+"'" +
					" And Idd.Department_id='"+letterNumberFormatBean.getRefOrgRoleId()+"') Union (Select Ion_id As Ionid From Ion_org_role_details Where Org_role_id In (Select I.Org_role_id From Emp_role_mapping M,Org_role_instance I Where I.Org_role_id=M.Org_role_id And M.Sfid  Like '"+letterNumberFormatBean.getSfid()+"')And Org_role_id='"+letterNumberFormatBean.getRefOrgRoleId()+"') " +
					"Union (select ion_id as ionId from ION_CIRCULATED_DETAILS where FORWARD_REPLAY_FILE='forwarded' and status=1 and sfid='"+letterNumberFormatBean.getSfid()+"' And REF_ORG_ROLE_ID ='"+letterNumberFormatBean.getRefOrgRoleId()+"')) minus"
                    +"(select icd.ion_id from ion_circulated_details icd where icd.sfid='"+letterNumberFormatBean.getSfid()+"' and icd.ref_org_role_id ='"+letterNumberFormatBean.getRefOrgRoleId()+"' and icd.forward_replay_file in('filed','replied')))";
			
			}
	         //list = session.createSQLQuery(sb1).addScalar("subject",Hibernate.STRING).addScalar("id", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(IONDTO.class)).list();
             list = session.createSQLQuery(sb1).addScalar("subject",Hibernate.STRING).addScalar("id", Hibernate.INTEGER).addScalar("createdBy", Hibernate.STRING).addScalar("ionInitiatedSfid", Hibernate.STRING).addScalar("ionForwardSfid", Hibernate.STRING).addScalar("ionApprovedSfid", Hibernate.STRING).addScalar("lastModifiedBy", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(IONDTO.class)).list();

			if(CPSUtils.compareStrings("type", "type") && !CPSUtils.isNullOrEmpty(letterNumberFormatBean.getRefOrgRoleId()))
			{list = null;
		 	//old query by satya mam
		    //list = session.createSQLQuery(sb1).addScalar("subject",Hibernate.STRING).addScalar("id", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(IONDTO.class)).list();
			list = session.createSQLQuery(sb1).addScalar("subject",Hibernate.STRING).addScalar("id", Hibernate.INTEGER).addScalar("createdBy", Hibernate.STRING).addScalar("ionInitiatedSfid", Hibernate.STRING).addScalar("ionForwardSfid", Hibernate.STRING).addScalar("ionApprovedSfid", Hibernate.STRING).addScalar("lastModifiedBy", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(IONDTO.class)).list();
		    List<Integer> ionList = new ArrayList<Integer>();
		    List<IONDTO> dtoList = new ArrayList<IONDTO>();
		    for(int i = 0;CPSUtils.checkList(list) && i < list.size();i++)
			{
				Integer temp = list.get(i).getId();
				 ionList.add((Integer) list.get(i).getId());
				 dtoList.add((IONDTO)list.get(i));
				
			}
				//list1 = session.createQuery("from IonCirculatedDetailsDTO where forwardOrReplayOrFile='forwarded' and status=1 and ionId="+dto.getId()+" and refOrgRoleId="+letterNumberFormatBean.getRefOrgRoleId()).list();
				
			 
		    if(!ionList.isEmpty()){
		    list1	= session.createCriteria(IonCirculatedDetailsDTO.class).add(Expression.in("ionId",ionList)).add(Expression.eq("refOrgRoleId", letterNumberFormatBean.getRefOrgRoleId())).list();
		    
			session.clear();
				
			   for(int i = 0;CPSUtils.checkList(list) && i < list.size();i++){ 
			 List<IonCirculatedDetailsDTO> templist = new ArrayList<IonCirculatedDetailsDTO>();
				   for(int j=0;CPSUtils.checkList(list1)&& j < list1.size();j++){
			   if(list1.get(j).getIonId().intValue()==	list.get(i).getId()){  
				    templist.add(list1.get(j));  
			      }
			      }
				   if(templist.size()>1){
					   IonCirculatedDetailsDTO	dto1 = new IonCirculatedDetailsDTO();
					   ListIterator<IonCirculatedDetailsDTO> itr =  templist.listIterator();
						while(itr.hasNext()){
						if(itr.nextIndex()>0){
							dto1 = itr.next();
						 dto1.setRemarks(null);
						 //templist.add(dto1);
						}else{
							dto1 = itr.next();
							//templist.add(dto1);
						}}
					}
				   dtoList.get(i).setIonCirculationDetailsList(templist);
				   dtoList.get(i).setSfidList(sfidList);
				   list.set(i,(IONDTO)(dtoList.get(i)));
				   
			   }
			 }
			}
		        
				/*if(list1.size()>1){
					ListIterator<IonCirculatedDetailsDTO> itr =  list1.listIterator();
					while(itr.hasNext()){
						if(itr.nextIndex()>0){
					IonCirculatedDetailsDTO	dto1 = itr.next();
					 dto1.setRemarks(null);
					 list2.add(dto1);
					}else{
						IonCirculatedDetailsDTO	dto1 = itr.next();
						list2.add(dto1);
					}}
				}
			*/
			//
				
			
			//dto.setSfidList(sfidList);
				
			
		
			else if(CPSUtils.isNullOrEmpty(letterNumberFormatBean.getRefOrgRoleId()))
			{
				List<Integer> ionList = new ArrayList<Integer>();
			    List<IONDTO> dtoList = new ArrayList<IONDTO>();
			    for(int i = 0;CPSUtils.checkList(list) && i < list.size();i++)
				{
					Integer temp = list.get(i).getId();
					 ionList.add((Integer) list.get(i).getId());
					 dtoList.add((IONDTO)list.get(i));
					
				}
					//list1 = session.createQuery("from IonCirculatedDetailsDTO where forwardOrReplayOrFile='forwarded' and status=1 and ionId="+dto.getId()+" and refOrgRoleId="+letterNumberFormatBean.getRefOrgRoleId()).list();
			   // dto.setIonCirculationDetailsList(session.createQuery("from IonCirculatedDetailsDTO where ionId="+dto.getId()+" and forwardOrReplayOrFile='forwarded' and status=1 and sfid='"+letterNumberFormatBean.getSfid()+"'").list());	
				if(!(ionList.isEmpty())){
			    list1	= session.createCriteria(IonCirculatedDetailsDTO.class).add(Expression.in("ionId",ionList)).add(Expression.eq("sfid", letterNumberFormatBean.getSfid())).add(Expression.eq("status", 1)).list();
					
				session.clear();
					
				   for(int i = 0;CPSUtils.checkList(list) && i < list.size();i++){ 
				 List<IonCirculatedDetailsDTO> templist = new ArrayList<IonCirculatedDetailsDTO>();
					   for(int j=0;CPSUtils.checkList(list1)&& j < list1.size();j++){
				   if(list1.get(j).getIonId().intValue()==	list.get(i).getId()){  
					    templist.add(list1.get(j));  
				      }
					 }
					   dtoList.get(i).setIonCirculationDetailsList(templist);
					   dtoList.get(i).setSfidList(sfidList);
					   list.set(i,(IONDTO)(dtoList.get(i)));
					   
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

			//.addScalar("forwardedSfid",Hibernate.STRING)
			
			//.addScalar("remarks",Hibernate.STRING).addScalar("filed",Hibernate.STRING).addScalar("replied",Hibernate.STRING).addScalar("forwarded",Hibernate.STRING)
			//",(select rtrim(Xmlagg (Xmlelement (E, sfid|| ',')).Extract ('//text()'),',') as sfid from ion_circulated_details where ion_id = id and status=1 and ref_org_role_id in (select org_role_id from emp_role_mapping where sfid='"+letterNumberFormatBean.getSfid()+"' and status=1)) as forwardedSfid,(select forward_replay_file from ion_circulated_details where ion_id=id and forward_replay_file='filed') as filed,(select forward_replay_file from ion_circulated_details where ion_id=id and forward_replay_file='replied') as replied,(select forward_replay_file from ion_circulated_details where ion_id=id and forward_replay_file='forwarded') as forwarded,(select remarks As Remarks from ion_circulated_details where ion_id=id and forward_replay_file='forwarded')as remarks  " +
		}
		return list;
	}}
	@Override
	public List getIONDetails(LetterNumberFormatBean letterNumberFormatBean)throws Exception
	{
		Session session = null;
		List list = null;
		String sb = null;
		try
		{
			session = hibernateUtils.getSession();
			
			/*sb = "Select i.subject As subject,(case when i.circulationStatus is null then '0' else '1' end) as lastModifiedBy From IONDTO i Where letterFormatId In (Select Id From LetterNumberFormatDTO Where deptNum="+letterNumberFormatBean.getDeptNum()+") and i.status=1 and i.ionDate >=to_date('"+letterNumberFormatBean.getStartDate()+"','DD-MON-YYYY') and i.ionDate <=to_date('"+letterNumberFormatBean.getEndDate()+"','DD-MON-YYYY')";
			sb = "Select i.Id As id,i.Subject As subject,i.letter_number||' ('||to_char(TO_Date(i.ion_date,'DD-MON-YYYY'),'DD-Mon-YYYY')||' )' as letterNumber,i.circulation_status as createdBy"+//,(select (sfid||'-'||name_in_service_book) as lastModifiedBy from emp_master where sfid=i.last_modified_by) as lastModifiedBy,(select (sfid||'-'||name_in_service_book) as initiatedBy from emp_master where sfid=i.initiated_by) as ionInitiatedSfid,(SELECT (sfid||'-'||name_in_service_book) as forwardBy from emp_master where sfid=i.forward_by) as ionForwardSfid,(SELECT (sfid||'-'||name_in_service_book) as approvedBy from emp_master where sfid=i.approved_by) as ionApprovedSfid" +
					" From Ion_master i Where i.Letter_format_id ="+letterNumberFormatBean.getLetterFormatId()+" and i.status=1";
			if(!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getStartDate()) && !CPSUtils.isNullOrEmpty(letterNumberFormatBean.getEndDate()))
				 sb += " and i.ion_date >=to_date('"+letterNumberFormatBean.getStartDate()+"','DD-MON-YYYY') and i.ion_date <=to_date('"+letterNumberFormatBean.getEndDate()+"','DD-MON-YYYY')";
			if(!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getIonSubjectSearch()))
				sb += " and (upper(i.Subject) like '%"+letterNumberFormatBean.getIonSubjectSearch().toUpperCase()+"%' or lower(i.Subject) like '%"+letterNumberFormatBean.getIonSubjectSearch().toLowerCase()+"%')";
			if(CPSUtils.compareStrings(letterNumberFormatBean.getSelectStatus(), "0"))
				sb += " and (i.circulation_status=0 or i.circulation_status=1)";
			
			else sb += " and i.circulation_status!=0";
			sb+=" order by TO_Date(i.ion_date,'DD-MON-YYYY') desc,i.id desc";*/
			
			sb = "Select i.subject As subject,(case when i.circulationStatus is null then '0' else '1' end) as lastModifiedBy From IONDTO i Where letterFormatId In (Select Id From LetterNumberFormatDTO Where deptNum="+letterNumberFormatBean.getDeptNum()+") and i.status=1 and i.ionDate >=to_date('"+letterNumberFormatBean.getStartDate()+"','DD-MON-YYYY') and i.ionDate <=to_date('"+letterNumberFormatBean.getEndDate()+"','DD-MON-YYYY')";
			sb = "Select i.Id As id,i.Subject As subject,i.letter_number||' ('||to_char(TO_Date(i.ion_date,'DD-MON-YYYY'),'DD-Mon-YYYY')||' )' as letterNumber,i.circulation_status as createdBy,(SELECT (name_in_service_book||'('||sfid||')') AS ionCreatedBy FROM emp_master where sfid=i.created_by) AS ionCreatedBy ,i.creation_date as ionCreationDate,"+
			"(select (name_in_service_book||'('||sfid||')') as lastModifiedBy from emp_master where sfid=i.last_modified_by) as lastModifiedBy,i.last_modified_date as lastModifiedDate,(select (name_in_service_book||'('||sfid||')') as initiatedBy from emp_master where sfid=i.initiated_by) as ionInitiatedSfid,(SELECT (name_in_service_book||'('||sfid||')') as forwardBy from emp_master where sfid=i.forward_by) as ionForwardSfid,(SELECT (name_in_service_book||'('||sfid||')') as approvedBy from emp_master where sfid=i.approved_by) as ionApprovedSfid" +
					" From Ion_master i Where i.Letter_format_id ="+letterNumberFormatBean.getLetterFormatId()+" ";
			if(!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getStartDate()) && !CPSUtils.isNullOrEmpty(letterNumberFormatBean.getEndDate()))
				 sb += " and i.status=1 and i.ion_date >=to_date('"+letterNumberFormatBean.getStartDate()+"','DD-MON-YYYY') and i.ion_date <=to_date('"+letterNumberFormatBean.getEndDate()+"','DD-MON-YYYY')";
			if(!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getIonSubjectSearch()))
				sb += " and i.status=1 and (upper(i.Subject) like '%"+letterNumberFormatBean.getIonSubjectSearch().toUpperCase()+"%' or lower(i.Subject) like '%"+letterNumberFormatBean.getIonSubjectSearch().toLowerCase()+"%')";
			if(CPSUtils.compareStrings(letterNumberFormatBean.getSelectStatus(), "0"))
				sb += " and i.status=1 and (i.circulation_status=0 or i.circulation_status=1) order by i.Id desc";
			
			if(CPSUtils.compareStrings(letterNumberFormatBean.getSelectStatus(), "1")){
				sb += " and i.status=1 and i.circulation_status=5";
				sb+=" order by TO_Date(i.ion_date,'DD-MON-YYYY') desc,i.id desc";
			}
			//new code 
			if(CPSUtils.compareStrings(letterNumberFormatBean.getSelectStatus(), "2"))
				sb += " and i.status=0 and i.circulation_status=0 ";
			
			list = session.createSQLQuery(sb).addScalar("id",Hibernate.INTEGER).addScalar("letterNumber",Hibernate.STRING).addScalar("subject",Hibernate.STRING).addScalar("createdBy",Hibernate.STRING).addScalar("ionCreatedBy", Hibernate.STRING).addScalar("ionCreationDate", Hibernate.STRING)
			.addScalar("lastModifiedBy",Hibernate.STRING).addScalar("lastModifiedDate", Hibernate.STRING).addScalar("ionInitiatedSfid",Hibernate.STRING).addScalar("ionForwardSfid",Hibernate.STRING).addScalar("ionApprovedSfid",Hibernate.STRING)
			.setResultTransformer(Transformers.aliasToBean(IONDTO.class)).list();
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
	@Override
	@SuppressWarnings("unchecked")
	public List<IONDTO> getIONMstDetails(LetterNumberFormatBean letterNumberFormatBean)throws Exception
	{
		Session session = null;
		List<IONDTO> list = null;
		String sb = null;
		try
		{
			session = hibernateUtils.getSession();
			list = session.createCriteria(IONDTO.class).add(Expression.eq("id",Integer.parseInt(letterNumberFormatBean.getId()))).add(Expression.eq("status", 1)).list();
			for(int i=0;CPSUtils.checkList(list) && i < list.size();i++)
			{
				IONDTO ionDto = (IONDTO)list.get(i);
				List l = session.createSQLQuery("select id from files where id=100").list();
				ionDto.setFileEncloserList(session.createSQLQuery("select f.id as id,f.filename as filename from files f,ion_file_details i where i.file_id=f.id and i.ion_id="+ionDto.getId()).addScalar("id", Hibernate.INTEGER).addScalar("filename", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(FilesBean.class)).list());
				ionDto.setDeparmentList(session.createQuery("select d.id as id,d.deptName as deptName from DepartmentsDTO d,IonDeptDTO i where i.departmentId=d.id and d.status=1 and i.copyStatus=0 and i.ionId=?").setString(0,String.valueOf(ionDto.getId())).setResultTransformer(Transformers.aliasToBean(DepartmentsDTO.class)).list());
				ionDto.setDesignList(session.createQuery("select d.id as id,d.name as name from DesignationDTO d,IonDesignDTO i where i.designationId=d.id and d.status=1 and i.copyStatus=0 and i.ionId=?").setString(0,String.valueOf(ionDto.getId())).setResultTransformer(Transformers.aliasToBean(DesignationDTO.class)).list());
				ionDto.setOrgRoleList(session.createQuery("select d.id as id,d.name as name from OrgInstanceDTO d,IonOrgRoleDTO i where i.orgRoleId=d.id and d.status=1 and i.orgOrHirarchy='R' and i.ionId=?").setString(0,String.valueOf(ionDto.getId())).setResultTransformer(Transformers.aliasToBean(OrgInstanceDTO.class)).list());
				ionDto.setSfidList(session.createQuery("select d.userSfid as userSfid,d.userSfid||'-'||d.nameInServiceBook as nameInServiceBook from EmployeeBean d,IonSfidDTO i where i.sfid=d.userSfid and i.copyStatus=0 and d.status=1 and i.ionId=?").setString(0,String.valueOf(ionDto.getId())).setResultTransformer(Transformers.aliasToBean(EmployeeBean.class)).list());
				ionDto.setRoleHirarchyList(session.createQuery("select d.roleID as roleID,d.roleName as roleName from HierarchyDTO d,IonOrgRoleDTO i where i.orgRoleId=d.roleID and d.status=1 and i.orgOrHirarchy='H' and i.ionId=?").setString(0,String.valueOf(ionDto.getId())).setResultTransformer(Transformers.aliasToBean(HierarchyDTO.class)).list());
				ionDto.setOrgList(session.createQuery("select d.id as id,d.name as name from OrganizationsDTO d,IonOrgDTO i where i.organizationId=d.id and d.status=1 and i.copyStatus=0 and i.ionId=?").setString(0,String.valueOf(ionDto.getId())).setResultTransformer(Transformers.aliasToBean(OrganizationsDTO.class)).list());
				
				ionDto.setDeparmentListCopy(session.createQuery("select d.id as id,d.deptName as deptName from DepartmentsDTO d,IonDeptDTO i where i.departmentId=d.id and d.status=1 and i.copyStatus=1 and i.ionId=?").setString(0,String.valueOf(ionDto.getId())).setResultTransformer(Transformers.aliasToBean(DepartmentsDTO.class)).list());
				ionDto.setDesignListCopy(session.createQuery("select d.id as id,d.name as name from DesignationDTO d,IonDesignDTO i where i.designationId=d.id and d.status=1 and i.copyStatus=1 and i.ionId=?").setString(0,String.valueOf(ionDto.getId())).setResultTransformer(Transformers.aliasToBean(DesignationDTO.class)).list());
				ionDto.setOrgRoleListCopy(session.createQuery("select d.id as id,d.name as name from OrgInstanceDTO d,IonOrgRoleDTO i where i.orgRoleId=d.id and d.status=1 and i.orgOrHirarchy='RC' and i.ionId=?").setString(0,String.valueOf(ionDto.getId())).setResultTransformer(Transformers.aliasToBean(OrgInstanceDTO.class)).list());
				ionDto.setSfidListCopy(session.createQuery("select d.userSfid as userSfid,d.userSfid||'-'||d.nameInServiceBook as nameInServiceBook from EmployeeBean d,IonSfidDTO i where i.sfid=d.userSfid and i.copyStatus=1 and d.status=1 and i.ionId=?").setString(0,String.valueOf(ionDto.getId())).setResultTransformer(Transformers.aliasToBean(EmployeeBean.class)).list());
				ionDto.setRoleHirarchyListCopy(session.createQuery("select d.roleID as roleID,d.roleName as roleName from HierarchyDTO d,IonOrgRoleDTO i where i.orgRoleId=d.roleID and d.status=1 and i.orgOrHirarchy='HC' and i.ionId=?").setString(0,String.valueOf(ionDto.getId())).setResultTransformer(Transformers.aliasToBean(HierarchyDTO.class)).list());
				ionDto.setOrgListCopy(session.createQuery("select d.id as id,d.name as name from OrganizationsDTO d,IonOrgDTO i where i.organizationId=d.id and d.status=1 and i.copyStatus=1 and i.ionId=?").setString(0,String.valueOf(ionDto.getId())).setResultTransformer(Transformers.aliasToBean(OrganizationsDTO.class)).list());
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
	@Override
	@SuppressWarnings("unchecked")
	public List<HierarchyDTO> getRoleHirarchyList()throws Exception
	{
		Session session = null;
		List<HierarchyDTO> list = null;
		String sb = null;
		try
		{
			session = hibernateUtils.getSession();
			sb = "select roleID as roleID,roleName as roleName from HierarchyDTO where status=1";
			
			list = session.createQuery(sb).setResultTransformer(Transformers.aliasToBean(HierarchyDTO.class)).list();
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
	@Override
	@SuppressWarnings("unchecked")
	public List<String> getLevel1SfidList(LetterNumberFormatBean letterNumberFormatBean)throws Exception 
	{
		Session session = null;
		List<String> list = null;
		String sb = null;
		try
		{
			session = hibernateUtils.getSession();   
			sb = "select e.sfid as userSfid,e.sfid||'-'||e.name_in_service_book as nameInServiceBook from emp_role_mapping r,emp_master e where e.sfid=r.sfid and e.status=1 and r.parent_role_id="+letterNumberFormatBean.getRefOrgRoleId();
			
			sb = "select Emp.Sfid AS userSfid,emp.sfid||'-'||Emp.Name_in_service_book As Nameinservicebook From Emp_master emp Where emp.sfid in((Select Emp.Sfid From Emp_master Emp Where Emp.Office_id In (Select Department_id From Org_role_instance Where Org_role_id="+letterNumberFormatBean.getRefOrgRoleId()+") Union  Select Emp.Sfid From Emp_master Emp,Emp_role_mapping M,Org_role_instance I Where Emp.status=1 and M.status=1 and I.status=1 and Emp.Sfid=M.Sfid And M.Org_role_id=I.Org_role_id "+ 
					"And I.Department_id In (Select Department_id From Org_role_instance Where Org_role_id="+letterNumberFormatBean.getRefOrgRoleId()+"))minus Select Emp.Sfid From Emp_master Emp,Emp_role_mapping M,Org_role_instance I Where Emp.Sfid=M.Sfid And M.Org_role_id=I.Org_role_id and i.Org_role_id="+letterNumberFormatBean.getRefOrgRoleId()+") and emp.status=1 order by Emp.Name_in_service_book";
			if(!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getRefOrgRoleId()))
			list = session.createSQLQuery(sb).addScalar("userSfid",Hibernate.STRING).addScalar("nameInServiceBook",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(EmployeeBean.class)).list();
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
	@Override
	public String updateIonCirculationDetails(IonCirculatedDetailsDTO dto)throws Exception
	{
    log.debug("updateIonCirculationDetails() --> Start");
		
		Session session = null;
		IonCirculatedDetailsDTO idto = null;
		String message = null;
		
		String sb = null;
		try {
			session = hibernateUtils.getSession();
            
			idto = (IonCirculatedDetailsDTO)session.createCriteria(IonCirculatedDetailsDTO.class).add(Expression.eq("ionId", dto.getIonId())).add(Expression.eq("sfid", dto.getSfid())).uniqueResult();
			
			if(idto != null){
			String temp = idto.getRemarks();
			String temp2 = dto.getRemarks();
			String remarks = temp+"  "+dto.getSfid().toUpperCase().toString()+":"+temp2;
			//remarks =+ temp2;
			idto.setRemarks(remarks);
			idto.setForwardOrReplayOrFile(dto.getForwardOrReplayOrFile());
			session.update(idto);
			}
			else{
				String remarks = dto.getSfid().toUpperCase().toString()+":"+dto.getRemarks().toString();
				dto.setRemarks(remarks);
				session.save(dto);
			}
		message = CPSConstants.UPDATE;
			
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			
			throw e;
		} finally {
			
			
		} 
		log.debug("updateIonCirculationDetails() --> End");
		return message;
	}
	@Override
	public List<OrgInstanceDTO> getUserOrgRoleList(LetterNumberFormatBean letterNumberFormatBean,String str)throws Exception
	{
		Session session = null;
		List<OrgInstanceDTO> list = null;
		String sb = null;
		try
		{
			session = hibernateUtils.getSession(); 
			
			sb = "Select i.id as id,i.name as name,d.deptName as departmentName From EmpRoleMappingDTO m,OrgInstanceDTO i,DepartmentsDTO d Where m.sfid='";
			if(CPSUtils.compareStrings(str, "user"))
				sb+=letterNumberFormatBean.getSfid();
			else if(CPSUtils.compareStrings(str, "initiatedBy"))
				sb+=letterNumberFormatBean.getIonInitiatedSfid(); 
			else if(CPSUtils.compareStrings(str, "forwardBy"))
				sb+=letterNumberFormatBean.getIonForwardSfid(); 
			else if(CPSUtils.compareStrings(str, "approvedBy"))
				sb+=letterNumberFormatBean.getIonApprovedSfid(); 
			sb+="' and m.roleInstanceId=i.id and i.status=1 and m.status=1 and i.departmentID=d.id and d.status=1";
			
			list = session.createQuery(sb).setResultTransformer(Transformers.aliasToBean(OrgInstanceDTO.class)).list();
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
	@Override
	public String checkLetterNumberSeres(LetterNumberFormatBean letterNumberFormatBean)throws Exception
	{
		Session session = null;
		List list = null;
		String sb = null;
		String message = null;
		try
		{
			session = hibernateUtils.getSession(); 
			
			sb = "select l.id from LetterNumberFormatDTO l,LetterNumberSeriesDTO s where s.deptNum=l.deptNum and s.status=1 and l.status=1 and s.id="+letterNumberFormatBean.getId();
			
			list = session.createQuery(sb).list();
			if(!CPSUtils.checkList(list))
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
	@Override
	public String deleteLetterNumberSeries(LetterNumberFormatBean letterNumberFormatBean)throws Exception
	{
		log.debug("update() --> Start");
		String message = null;
		Session session = null;
		
		String sb = null;
		try {
			session = hibernateUtils.getSession();
			
			
				sb = "update LetterNumberSeriesDTO set lastModifiedBy=?,lastModifiedDate=sysdate,status=0 where id=?";
				
				Query qry = session.createQuery(sb);
				qry.setString(0, letterNumberFormatBean.getSfid());
				qry.setString(1, letterNumberFormatBean.getId());
				
		    qry.executeUpdate();
			session.flush();

			message = CPSConstants.DELETE;
			log.debug("message -->" + message);
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			
		}
		log.debug("update() --> End");
		return message;
	}
	@Override
	public String checkLetterNumberFormt(LetterNumberFormatBean letterNumberFormatBean)throws Exception
	{
		Session session = null;
		List list = null;
		String sb = null;
		String message = null;
		try
		{
			session = hibernateUtils.getSession(); 
			
			sb = "from IONDTO where status=1 and letterFormatId="+letterNumberFormatBean.getId();
			
			list = session.createQuery(sb).list();
			if(!CPSUtils.checkList(list))
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
	@Override
	public String deleteLetterNumberFormat(LetterNumberFormatBean letterNumberFormatBean)throws Exception
	{
		log.debug("update() --> Start");
		String message = null;
		Session session = null;
		
		String sb = null;
		try {
			session = hibernateUtils.getSession();
			
			
				sb = "update LetterNumberFormatDTO set lastModifiedBy=?,lastModifiedDate=sysdate,status=0 where id=?";
				
				Query qry = session.createQuery(sb);
				qry.setString(0, letterNumberFormatBean.getSfid());
				qry.setString(1, letterNumberFormatBean.getId());
				
		    qry.executeUpdate();
		    session.flush();

			message = CPSConstants.DELETE;
			log.debug("message -->" + message);
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			
		}
		log.debug("update() --> End");
		return message;
	}
	@Override
	public List getDepartmentListBasedOnSfid(LetterNumberFormatBean letterNumberFormatBean)throws Exception
	{
		Session session = null;
		List list = null;
		String sb = null;
		try
		{
			session = hibernateUtils.getSession();
			//sb = "select department_id as id,department_name as deptName from dept_view WHERE  department_id in ((select ori.department_id from emp_role_mapping erm,org_role_instance ori where ori.org_role_id=erm.org_role_id and erm.sfid like '"+letterNumberFormatBean.getSfid()+"') union (select department_id from departments_master d,emp_master emp where emp.office_id=d.department_id and emp.status=1 and d.status=1 and emp.sfid like '"+letterNumberFormatBean.getSfid()+"' ))";  // query is modified to all roles
			sb = "select department_id as id,department_name as deptName from departments_master WHERE  department_id in ((select ori.department_id from emp_role_mapping erm,org_role_instance ori where erm.parent_role_id=ori.org_role_id and erm.status=1 and erm.sfid like '"+letterNumberFormatBean.getSfid()+"') union (select department_id from departments_master d,emp_master emp where emp.office_id=d.department_id and emp.status=1 and d.status=1 and emp.sfid like '"+letterNumberFormatBean.getSfid()+"' ))";
			list = session.createSQLQuery(sb).addScalar("id", Hibernate.INTEGER).addScalar("deptName",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(DepartmentsDTO.class)).list();
			
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
	@Override
	public List getOrganizations(LetterNumberFormatBean letterNumberFormatBean)throws Exception
	{
		Session session = null;
		List list = null;
		String sb = null;
		try
		{
			session = hibernateUtils.getSession();
			sb = "select department_id as id,department_name as deptName from dept_view WHERE  department_id in ((select ori.department_id from emp_role_mapping erm,org_role_instance ori where ori.org_role_id=erm.org_role_id and erm.sfid like '"+letterNumberFormatBean.getSfid()+"') union (select department_id from departments_master d,emp_master emp where emp.office_id=d.department_id and emp.status=1 and d.status=1 and emp.sfid like '"+letterNumberFormatBean.getSfid()+"' ))";
			list = session.createCriteria(OrganizationsDTO.class).add(Expression.eq(CPSConstants.STATUS,1)).list();
			
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

	@Override
	public List getIONFileList(LetterNumberFormatBean letterNumberFormatBean)throws Exception
	{
		Session session = null;
		List list = null;
		String sb = null;
		try
		{
			session = hibernateUtils.getSession();
			list = session.createSQLQuery("select f.id as id,f.filename as filename from files f,ion_file_details i where i.file_id=f.id and i.ion_id="+letterNumberFormatBean.getId()).addScalar("id", Hibernate.INTEGER).addScalar("filename", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(FilesBean.class)).list();
			
			
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
	@Override
	public void checkHRDGIon(LetterNumberFormatBean letterNumberFormatBean, FileUploadBean fub)throws Exception
	{
		Session session = null;
		List list = null;
		String sb = null;
		try
		{
			session = hibernateUtils.getSession(); 
			list = session.createQuery("select id from TrainingCirculationDTO where ionId=?").setInteger(0, Integer.parseInt(letterNumberFormatBean.getId())).list();
			if(CPSUtils.checkList(list))
			list = session.createQuery("select t.id from TrainingCirculationDTO t,IonFileDTO f where f.ionId=t.ionId and t.brochure=f.fileId and t.ionId=?").setInteger(0, Integer.parseInt(letterNumberFormatBean.getId())).list();
			if(!CPSUtils.checkList(list))
			session.createQuery("update TrainingCirculationDTO set brochure=? where ionId=?").setInteger(0, fub.getFileIds().get("ionFile#"+letterNumberFormatBean.getEnclosureFileName()[0].toString())).setInteger(1, Integer.parseInt(letterNumberFormatBean.getId())).executeUpdate();
			session.flush();
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			
		}
		
	}
	
	@Override
	public List getIONListToCirculate(LetterNumberFormatBean letterNumberFormatBean)throws Exception
	{
		Session session = null;
		List list = null;
		String sb = null;
		try
		{
			session = hibernateUtils.getSession(); 
			
   
			
			sb = "Select i.Id As id,i.Subject As subject,i.letter_number||' ('||to_char(TO_Date(i.ion_date,'DD-MON-YYYY'),'DD-Mon-YYYY')||' )' as letterNumber,i.circulation_status as createdBy,(SELECT (name_in_service_book||'('||sfid||')') AS ionCreatedBy FROM emp_master where sfid=i.created_by) AS ionCreatedBy ,i.creation_date as ionCreationDate"+
			//",(select (sfid||'-'||name_in_service_book) as lastModifiedBy from emp_master where sfid=i.last_modified_by) as lastModifiedBy,(select (sfid||'-'||name_in_service_book) as initiatedBy from emp_master where sfid=i.initiated_by) as ionInitiatedSfid,(SELECT (sfid||'-'||name_in_service_book) as forwardBy from emp_master where sfid=i.forward_by) as ionForwardSfid,(SELECT (sfid||'-'||name_in_service_book) as approvedBy from emp_master where sfid=i.approved_by) as ionApprovedSfid " +  old Query by satya mam
			",(select (name_in_service_book||'('||sfid || ')') as lastModifiedBy from emp_master where sfid=i.last_modified_by) as lastModifiedBy, i.last_modified_date as lastModifiedDate,(select (name_in_service_book||'('||sfid || ')') as initiatedBy from emp_master where sfid=i.initiated_by) as ionInitiatedSfid,(SELECT (name_in_service_book||'('||sfid || ')') as forwardBy from emp_master where sfid=i.forward_by) as ionForwardSfid,(SELECT (name_in_service_book||'('||sfid || ')') as approvedBy from emp_master where sfid=i.approved_by) as ionApprovedSfid " +		// modified Query
			" From Ion_master i Where i.circulation_status=1 and i.status=1";
			if(CPSUtils.isNullOrEmpty(letterNumberFormatBean.getRefOrgRoleId()))
				sb += " and i.initiated_by in (select e.sfid from emp_master e where e.office_Id in(select org.department_id from org_role_instance org,emp_role_mapping emp where emp.org_Role_Id=org.org_Role_Id and emp.sfid='"+letterNumberFormatBean.getSfid()+"' and org.is_Head=1 and org.status=1 and emp.status=1) minus select sfid from emp_master where status=1 and sfid='"+letterNumberFormatBean.getSfid()+"')";
			//sb += " and i.ionInitiatedSfid in (select e.sfid from EmployeeBean e where e.officeId in (select org.departmentID from org_role_instance org,emp_role_mapping emp where emp.orgRoleId=org.orgRoleId and emp.sfid='"+letterNumberFormatBean.getSfid()+"' and org.isHead=1) minus select sfid from EmployeeBean where sfid='"+letterNumberFormatBean.getSfid()+"')";
			if(!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getRefOrgRoleId()))
			sb += " and ((i.initiated_by ='"+letterNumberFormatBean.getSfid()+"' and i.initiated_role_id ='"+letterNumberFormatBean.getRefOrgRoleId()+"') or (i.forward_by ='"+letterNumberFormatBean.getSfid()+"' and i.forward_role_id ='"+letterNumberFormatBean.getRefOrgRoleId()+"') or (i.Approved_by ='"+letterNumberFormatBean.getSfid()+"' and i.Approved_Role_Id ='"+letterNumberFormatBean.getRefOrgRoleId()+"'))";
			sb+=" order by TO_Date(i.ion_date,'DD-MON-YYYY') asc,I.LETTER_FORMAT_ID  asc";
			if(!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getRefOrgRoleId()))
			list = session.createSQLQuery(sb).addScalar("id",Hibernate.INTEGER).addScalar("letterNumber",Hibernate.STRING).addScalar("subject",Hibernate.STRING).addScalar("createdBy",Hibernate.STRING).addScalar("ionCreatedBy", Hibernate.STRING).addScalar("ionCreationDate", Hibernate.STRING)
			.addScalar("lastModifiedBy",Hibernate.STRING).addScalar("lastModifiedDate", Hibernate.STRING).addScalar("ionInitiatedSfid",Hibernate.STRING).addScalar("ionForwardSfid",Hibernate.STRING).addScalar("ionApprovedSfid",Hibernate.STRING) // comments are removed
			.setResultTransformer(Transformers.aliasToBean(IONDTO.class)).list();
		
			
			//here createdBy is used to fetch circulationStatus (this is used in jsp)
			if(list!=null){
				for(int i=0;i<list.size(); i++){
					IONDTO iondto=(IONDTO)list.get(i);
					String totLetterNum=iondto.getLetterNumber();
					String letterNum1=totLetterNum.split("/")[4];
					String letterNum="<html><b style='color :blue'>";
					letterNum=letterNum.concat(iondto.getLetterNumber().split("/")[4]);
					letterNum=letterNum.concat("</b></html>");
					String modifiedLetterNum=iondto.getLetterNumber().replace(letterNum1, letterNum);
					((IONDTO)list.get(i)).setLetterNumber(modifiedLetterNum);	
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
	@Override
	public List getFileTypeList()throws Exception
	{
		Session session = null;
		List list = null;
		String sb = null;
		try
		{
			session = hibernateUtils.getSession(); 
			

			
			sb = "select id as id,name as name from file_type";
			
			
			list = session.createSQLQuery(sb).addScalar("id",Hibernate.INTEGER).addScalar("name",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(CommonDTO.class)).list();
		
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
	@Override
	public String manageCirculation(LetterNumberFormatBean letterNumberFormatBean)throws Exception
	{
		log.debug("update() --> Start");
		String message = null;
		Session session = null;
		
		String sb = null;
		try {
			session = hibernateUtils.getSession();
			
			
				sb = "update IONDTO set lastModifiedBy=?,lastModifiedDate=sysdate,circulationStatus=5 where id=?";
				
				Query qry = session.createQuery(sb);
				qry.setString(0, letterNumberFormatBean.getSfid());
				qry.setString(1, letterNumberFormatBean.getId());
				
		    qry.executeUpdate();
		    session.flush();

			message = CPSConstants.UPDATE;
			log.debug("message -->" + message);
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			
		}
		log.debug("update() --> End");
		return message;
	}
	// code of deleting records in the letterNumberFormatDetailsScree
	@Override
	public String deleteLetterNumberFormatDetails(int id)throws Exception{
		Session session =null;
		String message = null;
		try{
			session=hibernateUtils.getSession();
			IONDTO iondto =(IONDTO)session.get(IONDTO.class, id);
		if(iondto!=null){
			iondto.setStatus(0);
			session.update(iondto);
			message=CPSConstants.DELETE;
		}else{
			message=CPSConstants.FAILED;
		}
		session.flush() ;
		}catch (Exception e) {
			throw e;
		}finally{
			
		}
		return message;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<IONDTO> getIONDisplayDetails(LetterNumberFormatBean letterNumberFormatBean) throws Exception{
		Session session = null;
		List<IONDTO> displayIonDetails =null;
		try{
			session=hibernateUtils.getSession();
			String query ="SELECT (SELECT (name_in_service_book||'('||sfid||')') AS ionCreatedBy FROM emp_master WHERE sfid=i.created_by)  as ionCreatedBy ,i.creation_date as ionCreationDate,(SELECT (name_in_service_book||'('||sfid||')') AS lastModifedBy FROM emp_master WHERE sfid=i.last_modified_by)  as lastModifiedBy ,i.last_modified_date as lastModifiedDate FROM ION_MASTER i where i.id = ?";
			displayIonDetails =session.createSQLQuery(query).addScalar("ionCreatedBy", Hibernate.STRING).addScalar("ionCreationDate", Hibernate.STRING).addScalar("lastModifiedBy", Hibernate.STRING).addScalar("lastModifiedDate", Hibernate.STRING).setString(0, letterNumberFormatBean.getId()).setResultTransformer(Transformers.aliasToBean(IONDTO.class)).list();
		}catch (Exception e) {
			throw e;
		}finally{
			
		}
		return displayIonDetails;
	}

}
