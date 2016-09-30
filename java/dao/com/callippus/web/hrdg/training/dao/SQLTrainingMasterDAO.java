package com.callippus.web.hrdg.training.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.CityDTO;
import com.callippus.web.beans.dto.DepartmentsDTO;
import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.DisciplineDTO;
import com.callippus.web.beans.dto.QualificationDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.file.FilesBean;
import com.callippus.web.beans.letterNumberFormat.IONDTO;
import com.callippus.web.beans.letterNumberFormat.LetterNumberFormatDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.hrdg.training.beans.TrainingMasterBean;
import com.callippus.web.hrdg.training.beans.dto.CourseAttendedDetailsDTO;
import com.callippus.web.hrdg.training.beans.dto.CourseContentDTO;
import com.callippus.web.hrdg.training.beans.dto.CourseDTO;
import com.callippus.web.hrdg.training.beans.dto.CourseDesignationsDTO;
import com.callippus.web.hrdg.training.beans.dto.CourseDisciplinesDTO;
import com.callippus.web.hrdg.training.beans.dto.CourseDurationsDTO;
import com.callippus.web.hrdg.training.beans.dto.CourseQualificationsDTO;
import com.callippus.web.hrdg.training.beans.dto.CourseSubjCategoryDTO;
import com.callippus.web.hrdg.training.beans.dto.CourseSubjSubCategoryDTO;
import com.callippus.web.hrdg.training.beans.dto.HRDGBoardDTO;
import com.callippus.web.hrdg.training.beans.dto.HRDGBoardMemberDTO;
import com.callippus.web.hrdg.training.beans.dto.HRDGBoardMemberTypeDTO;
import com.callippus.web.hrdg.training.beans.dto.TrainingCirculationDTO;
import com.callippus.web.hrdg.training.beans.dto.TrainingInistituteDTO;
import com.callippus.web.hrdg.training.beans.dto.TrainingRegionDTO;
import com.callippus.web.hrdg.training.beans.dto.TrainingVenueDTO;
import com.callippus.web.loan.beans.dto.FinancialYearDTO;



@Service
public class SQLTrainingMasterDAO implements ITrainingMasterDAO{
	private static Log log = LogFactory.getLog(SQLTrainingMasterDAO.class);
	private static final long serialVersionUID = 5611041426238862266L;

	@Autowired
	private HibernateUtils hibernateUtils;


	@SuppressWarnings("unchecked")
	public List checkMasterData(TrainingMasterBean trainingMstBean) throws Exception
	{
		log.debug("checkMasterData() --> Start");
		Session session = null;
		List list = null;
		
		
		try {
			
			session = hibernateUtils.getSession();
			String sql = "from "+trainingMstBean.getBeanName()+" where upper(name)=? and status = 1"; 
		    
			
			if(CPSUtils.compareStrings(trainingMstBean.getType(), CPSConstants.TRAININGINISTITUTE))
				sql +=" and trainingRegionId=? and trainingTypeId=?";
			else if(CPSUtils.compareStrings(trainingMstBean.getType(), CPSConstants.COURSESUBJCATEGORY))
				sql +=" and categoryId=?";
			else if(CPSUtils.compareStrings(trainingMstBean.getType(), CPSConstants.COURSESUBJSUBCATEGORY))
				sql +=" and courseSubjCategoryId=?";
			else if(CPSUtils.compareStrings(trainingMstBean.getType(), CPSConstants.TRAININGVENUE))
				sql ="from "+trainingMstBean.getBeanName()+" where cityId=? and status=1 and trainingInistituteId=?";
			else if(CPSUtils.compareStrings(trainingMstBean.getType(), CPSConstants.COURSE))
			{
				sql +=" and categoryId=? and trainingTypeId=? and trainingInistituteId=? and courseSubjCategoryId=? and courseYear=?";
				if(!CPSUtils.isNullOrEmpty(trainingMstBean.getCourseSubjSubCategoryId()))
				  sql +=" and courseSubjSubCategoryId=?";
			}
			if(!CPSUtils.isNullOrEmpty(trainingMstBean.getId()))
				sql +=" and id != "+trainingMstBean.getId();
			
			
			Query qry = session.createQuery(sql);
			qry.setString(0, trainingMstBean.getName().trim().toUpperCase());
			
			if(CPSUtils.compareStrings(trainingMstBean.getType(), CPSConstants.TRAININGINISTITUTE)){
					qry.setString(1, trainingMstBean.getTrainingRegionId());
					qry.setString(2, trainingMstBean.getTrainingTypeId());
			}else if(CPSUtils.compareStrings(trainingMstBean.getType(), CPSConstants.COURSESUBJCATEGORY)){
				qry.setString(1, trainingMstBean.getCategoryId());
				
			}else if(CPSUtils.compareStrings(trainingMstBean.getType(), CPSConstants.COURSESUBJSUBCATEGORY)){
				qry.setString(1, trainingMstBean.getCourseSubjCategoryId());
				
			}else if(CPSUtils.compareStrings(trainingMstBean.getType(), CPSConstants.TRAININGVENUE)){
				qry.setString(0, trainingMstBean.getCityId());
				qry.setString(1, trainingMstBean.getTrainingInistituteId());
				
			}
			else if(CPSUtils.compareStrings(trainingMstBean.getType(), CPSConstants.COURSE)){
				qry.setString(1, trainingMstBean.getCategoryId());
				qry.setString(2, trainingMstBean.getTrainingTypeId());
				qry.setString(3, trainingMstBean.getTrainingInistituteId());
				qry.setString(4, trainingMstBean.getCourseSubjCategoryId());
				qry.setString(5, trainingMstBean.getCourseYear());
				if(!CPSUtils.isNullOrEmpty(trainingMstBean.getCourseSubjSubCategoryId()))
				qry.setString(6, trainingMstBean.getCourseSubjSubCategoryId());
				
				
			}
				
			list = qry.list();
			

		} catch (Exception e) {
			
			throw e;
			
		} finally {
			
		}
		log.debug("checkMasterData() --> End");
		return list;
	}
	
	
	public String update(TrainingMasterBean trainingMstBean) throws Exception
	{
		log.debug("update() --> Start");
		String message = null;
		Session session = null;
		
		String sb = null;
		try {
			session = hibernateUtils.getSession();
			
			if (CPSUtils.compareStrings(trainingMstBean.getBeanName(), CPSConstants.TRAININGTYPEDTO))
				sb = "update " + trainingMstBean.getBeanName() + " set name=?,lastModifiedDate=sysdate,description=?,lastModifiedBy=?,orderNo=? where id=?";
			else if (CPSUtils.compareStrings(trainingMstBean.getBeanName(), CPSConstants.TRAININGINISTITUTEDTO))
				sb = "update " + trainingMstBean.getBeanName() + " set name=?,shortName=?,trainingTypeId=?,trainingRegionId=?,lastModifiedDate=sysdate,lastModifiedBy=? where id=?";
			else if (CPSUtils.compareStrings(trainingMstBean.getBeanName(), CPSConstants.COURSESUBJCATEGORYDTO))
				sb = "update " + trainingMstBean.getBeanName() + " set name=?,categoryId=?,lastModifiedDate=sysdate,description=?,lastModifiedBy=? where id=?";
			else if (CPSUtils.compareStrings(trainingMstBean.getBeanName(), CPSConstants.COURSESUBJSUBCATEGORYDTO))
				sb = "update " + trainingMstBean.getBeanName() + " set name=?,courseSubjCategoryId=?,lastModifiedDate=sysdate,description=?,lastModifiedBy=? where id=?";
			else if (CPSUtils.compareStrings(trainingMstBean.getBeanName(), CPSConstants.TRAININGVENUEDTO))
				sb = "update " + trainingMstBean.getBeanName() + " set trainingInistituteId=?,lastModifiedDate=sysdate,cityId=?,phone=?,fax=?,email=?,address=?,ddFlag=?,headOfficeFlag=?,ddAddress=?,payableAt=?,lastModifiedBy=? where id=?";
			else if (CPSUtils.compareStrings(trainingMstBean.getBeanName(), CPSConstants.HRDGBOARDTYPEDTO))
				sb = "update " + trainingMstBean.getBeanName() + " set name=?,description=?,lastModifiedDate=sysdate,lastModifiedBy=? where id=?";
			else if (CPSUtils.compareStrings(trainingMstBean.getBeanName(), CPSConstants.HRDGBOARDMEMBERTYPEDTO))
				sb = "update " + trainingMstBean.getBeanName() + " set name=?,description=?,maxLimit=?,lastModifiedDate=sysdate,lastModifiedBy=? where id=?";
			
			
			
			Query qry = session.createQuery(sb);
			
			if (CPSUtils.compareStrings(trainingMstBean.getBeanName(), CPSConstants.TRAININGTYPEDTO))
			{
				qry.setString(0, trainingMstBean.getName());
				qry.setString(1, trainingMstBean.getDescription());
				qry.setString(2, trainingMstBean.getSfid());
				qry.setString(3, trainingMstBean.getOrderNo());
				qry.setString(4, trainingMstBean.getId());
			}else if (CPSUtils.compareStrings(trainingMstBean.getBeanName(), CPSConstants.TRAININGINISTITUTEDTO))
			{
				qry.setString(0, trainingMstBean.getName());
				qry.setString(1, trainingMstBean.getShortName());
				qry.setString(2, trainingMstBean.getTrainingTypeId());
				qry.setString(3, trainingMstBean.getTrainingRegionId());
				qry.setString(4, trainingMstBean.getSfid());
				qry.setString(5, trainingMstBean.getId());
			}else if (CPSUtils.compareStrings(trainingMstBean.getBeanName(), CPSConstants.COURSESUBJCATEGORYDTO))
			{
				qry.setString(0, trainingMstBean.getName());
				qry.setString(1, trainingMstBean.getCategoryId());
				qry.setString(2, trainingMstBean.getDescription());
				qry.setString(3, trainingMstBean.getSfid());
				qry.setString(4, trainingMstBean.getId());
			}
			else if (CPSUtils.compareStrings(trainingMstBean.getBeanName(), CPSConstants.COURSESUBJSUBCATEGORYDTO))
			{
				qry.setString(0, trainingMstBean.getName());
				qry.setString(1, trainingMstBean.getCourseSubjCategoryId());
				qry.setString(2, trainingMstBean.getDescription());
				qry.setString(3, trainingMstBean.getSfid());
				qry.setString(4, trainingMstBean.getId());
			}
			else if (CPSUtils.compareStrings(trainingMstBean.getBeanName(), CPSConstants.TRAININGVENUEDTO)) 
			{
				
				qry.setString(0, trainingMstBean.getTrainingInistituteId());
				qry.setString(1, trainingMstBean.getCityId());
				qry.setString(2, trainingMstBean.getPhone());
				qry.setString(3, trainingMstBean.getFax());
				qry.setString(4, trainingMstBean.getEmail());
				qry.setString(5, trainingMstBean.getAddress());
				qry.setString(6, trainingMstBean.getDdFlag());
				qry.setString(7, trainingMstBean.getHeadOfficeFlag());
				qry.setString(8, trainingMstBean.getDdAddress());
				qry.setString(9, trainingMstBean.getPayableAt());
				qry.setString(10, trainingMstBean.getSfid());
				qry.setString(11, trainingMstBean.getId());
			}
			else if (CPSUtils.compareStrings(trainingMstBean.getBeanName(), CPSConstants.HRDGBOARDTYPEDTO))
			{
				qry.setString(0, trainingMstBean.getName());
				qry.setString(1, trainingMstBean.getDescription());
				qry.setString(2, trainingMstBean.getSfid());
				qry.setString(3, trainingMstBean.getId());
			}
			else if (CPSUtils.compareStrings(trainingMstBean.getBeanName(), CPSConstants.HRDGBOARDMEMBERTYPEDTO))
			{
				qry.setString(0, trainingMstBean.getName());
				qry.setString(1, trainingMstBean.getDescription());
				qry.setString(2, trainingMstBean.getMaxLimit());
				qry.setString(3, trainingMstBean.getSfid());
				qry.setString(4, trainingMstBean.getId());
			}
			
			qry.executeUpdate();
			

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
	/**
	 * Gets the Master tables Object.
	 * 
	 * @param tableName
	 *            the table name
	 * @return the List.
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getMasterData(String beanName) throws Exception { 
		List<Object> masterObjList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession(); 
			StringBuffer sb = new StringBuffer();
			if (CPSUtils.compareStrings(beanName, CPSConstants.TRAININGTYPEDTO) || CPSUtils.compareStrings(beanName, CPSConstants.CATEGORYDTO) || CPSUtils.compareStrings(beanName, CPSConstants.CITYDTO) || CPSUtils.compareStrings(beanName, CPSConstants.DISCIPLINEDTO) || CPSUtils.compareStrings(beanName, CPSConstants.QUALIFICATIONDTO) || CPSUtils.compareStrings(beanName, CPSConstants.DepartmentsDTO) || CPSUtils.compareStrings(beanName, CPSConstants.HRDGBOARDTYPEDTO)|| CPSUtils.compareStrings(beanName, CPSConstants.TRAININGREGIONDTO))
			sb.append("from " + beanName + " m where m.status=1");
			else if (CPSUtils.compareStrings(beanName, CPSConstants.TRAININGINISTITUTEDTO))
				sb.append("select m.id as id,m.name as name,m.trainingRegionId as trainingRegionId,m.trainingTypeId as trainingTypeId,ttype.name as trainingType,region.name as trainingRegion,m.shortName as shortName from " + beanName + " m,TrainingTypeDTO ttype,TrainingRegionDTO region where m.status=1 and ttype.status=1 and m.trainingRegionId=region.id and m.trainingTypeId=ttype.id and region.status=1 and ttype.status=1 order by m.name");
			else if (CPSUtils.compareStrings(beanName, CPSConstants.COURSESUBJCATEGORYDTO))
				sb.append("select m.id as id,m.name as name,m.description as description,m.categoryId as categoryId,c.name as category from " + beanName + " m,CategoryDTO c where m.status=1 and m.categoryId=c.id and c.status=1 and m.id != 0 order by c.orderNo,m.name");
			else if (CPSUtils.compareStrings(beanName, CPSConstants.COURSESUBJSUBCATEGORYDTO))
				sb.append("select m.id as id,m.name as name,m.description as description,m.courseSubjCategoryId as courseSubjCategoryId,c.name as courseSubjCategory,c.categoryId as categoryId,cat.name as category from " + beanName + " m,CourseSubjCategoryDTO c,CategoryDTO cat where m.status=1 and m.courseSubjCategoryId=c.id and c.status=1 and cat.status=1 and c.categoryId=cat.id");
			else if (CPSUtils.compareStrings(beanName, CPSConstants.TRAININGVENUEDTO))
				sb.append("select m.id as id,t.name as trainingInistitute,t.trainingTypeId as trainingTypeId,m.trainingInistituteId as trainingInistituteId,m.cityId as cityId,c.name as city,m.address as address,m.phone as phone,m.fax as fax,m.email as email,m.ddFlag as ddFlag,m.headOfficeFlag as headOfficeFlag,m.ddAddress as ddAddress,m.payableAt as payableAt from " + beanName + " m,CityDTO c,TrainingInistituteDTO t where m.status=1 and m.cityId=c.id and c.status=1 and t.id=m.trainingInistituteId and t.status=1");
			else if (CPSUtils.compareStrings(beanName, CPSConstants.COURSEDTO))
				sb.append("select m.id as id,m.name as name,m.courseYear as courseYear,m.categoryId as categoryId,c.name as category,m.trainingTypeId as trainingTypeId,tt.name as trainingType,m.trainingInistituteId as trainingInistituteId,t.name as trainingInistitute,m.courseSubjCategoryId as courseSubjCategoryId,cs.name as courseSubjCategory,m.courseSubjSubCategoryId as courseSubjSubCategoryId,(select sub.name from CourseSubjSubCategoryDTO sub where sub.id=m.courseSubjSubCategoryId and sub.courseSubjCategoryId=m.courseSubjCategoryId) as courseSubjSubCategory from " + beanName + " m,CategoryDTO c,TrainingTypeDTO tt,TrainingInistituteDTO t,CourseSubjCategoryDTO cs where m.status=1 and c.id=m.categoryId and c.status=1 and tt.id=m.trainingTypeId and tt.status = 1 and t.id=m.trainingInistituteId and t.status=1 and cs.id=m.courseSubjCategoryId and cs.status=1");
			else if (CPSUtils.compareStrings(beanName, CPSConstants.DESIGNATIONDTO))
				sb.append("select d.category_id as category_id,m.id as id,m.name as name from designation_master m,designation_mappings d where m.id=d.desig_id and m.status=1");
			
			else if (CPSUtils.compareStrings(beanName, CPSConstants.FINANCIALYEARDTO))
				sb.append("select fym.id,to_char(from_date,'YYYY') ||'-'|| to_char(to_date,'YYYY') financialYear,to_char(from_date,'DD-Mon-YYYY') as lastModifiedBy,to_char(to_date,'DD-Mon-YYYY') as lastModifiedDate from financial_year_master fym where status=1 order by financialYear");
			else if (CPSUtils.compareStrings(beanName, CPSConstants.HRDGBOARDMEMBERTYPEDTO))
				sb.append("select m.id as id,m.name as name,m.description as description,m.status as status,m.members as maxLimit from Hrdg_board_member_type_master m where m.status=1");
			
			
			if(CPSUtils.compareStrings(beanName, CPSConstants.CATEGORYDTO) || CPSUtils.compareStrings(beanName, CPSConstants.TRAININGTYPEDTO))
				sb.append(" order by m.orderNo,m.name");
			if (!CPSUtils.compareStrings(beanName, CPSConstants.CITYDTO) && !CPSUtils.compareStrings(beanName, CPSConstants.CATEGORYDTO) && !CPSUtils.compareStrings(beanName, CPSConstants.TRAININGVENUEDTO) && !CPSUtils.compareStrings(beanName, CPSConstants.TRAININGINISTITUTEDTO) && !CPSUtils.compareStrings(beanName, CPSConstants.COURSESUBJCATEGORYDTO) && CPSUtils.compareStrings(beanName, CPSConstants.QUALIFICATIONDTO) && !CPSUtils.compareStrings(beanName, CPSConstants.DepartmentsDTO) && !CPSUtils.compareStrings(beanName, CPSConstants.FINANCIALYEARDTO) && !CPSUtils.compareStrings(beanName, CPSConstants.TRAININGREGIONDTO))
				sb.append(" order by m.name");
			
			Query qry = null;
			
				
			if (CPSUtils.compareStrings(beanName, CPSConstants.DESIGNATIONDTO))
				qry = session.createSQLQuery(sb.toString());
			else if(CPSUtils.compareStrings(beanName, CPSConstants.FINANCIALYEARDTO))
				qry = session.createSQLQuery(sb.toString()).addScalar("id", Hibernate.INTEGER).addScalar("financialYear", Hibernate.STRING).addScalar("lastModifiedBy", Hibernate.STRING).addScalar("lastModifiedDate", Hibernate.STRING);
			else if(CPSUtils.compareStrings(beanName, CPSConstants.HRDGBOARDMEMBERTYPEDTO))
				qry = session.createSQLQuery(sb.toString()).addScalar("id", Hibernate.INTEGER).addScalar("name", Hibernate.STRING).addScalar("description", Hibernate.STRING).addScalar("status", Hibernate.INTEGER).addScalar("maxLimit", Hibernate.INTEGER);
			else	
			 qry = session.createQuery(sb.toString());
			if (CPSUtils.compareStrings(beanName, CPSConstants.TRAININGINISTITUTEDTO))
				qry = qry.setResultTransformer(Transformers.aliasToBean(TrainingInistituteDTO.class));
			else if(CPSUtils.compareStrings(beanName, CPSConstants.COURSESUBJCATEGORYDTO))
				qry = qry.setResultTransformer(Transformers.aliasToBean(CourseSubjCategoryDTO.class));
			else if(CPSUtils.compareStrings(beanName, CPSConstants.COURSESUBJSUBCATEGORYDTO))
				qry = qry.setResultTransformer(Transformers.aliasToBean(CourseSubjSubCategoryDTO.class));
			else if (CPSUtils.compareStrings(beanName, CPSConstants.TRAININGVENUEDTO))
				qry = qry.setResultTransformer(Transformers.aliasToBean(TrainingVenueDTO.class));
			else if (CPSUtils.compareStrings(beanName, CPSConstants.COURSEDTO))
				qry = qry.setResultTransformer(Transformers.aliasToBean(CourseDTO.class)); 
			else if (CPSUtils.compareStrings(beanName, CPSConstants.FINANCIALYEARDTO))
			    qry = qry.setResultTransformer(Transformers.aliasToBean(FinancialYearDTO.class));
			else if (CPSUtils.compareStrings(beanName, CPSConstants.HRDGBOARDMEMBERTYPEDTO))
				qry = qry.setResultTransformer(Transformers.aliasToBean(HRDGBoardMemberTypeDTO.class));
			
  			
			
			    
				
			
	
			masterObjList = qry.list();
			if (CPSUtils.compareStrings(beanName, CPSConstants.DESIGNATIONDTO) && CPSUtils.checkList(masterObjList))
			{
				List list = new ArrayList();
				for(int i = 0;i < masterObjList.size();i++)
				{
					DesignationDTO dto = new DesignationDTO();
					Object[] obj = (Object[])masterObjList.get(i);
					dto.setCategoryID(!(CPSUtils.isNullOrEmpty(obj[0]))?obj[0].toString():null);
					dto.setId(!(CPSUtils.isNullOrEmpty(obj[1]))?Integer.parseInt(obj[1].toString()):1);
					dto.setName(!(CPSUtils.isNullOrEmpty(obj[2]))?obj[2].toString():null);
					list.add(dto);
				}
				return list;
				
			}
		} catch (Exception e) {
			throw e;
		} finally {
			
		}
		return masterObjList;
	}
	
	public String deleteTrainingCheckMaster(String beanName, String propertyName, String propertyValue, String id) throws Exception
	{
		Session session = null;
		
		String message = null;
		List list = null;
		try {
			session = hibernateUtils.getSession();
			
			StringBuffer sb = new StringBuffer();
			sb.append("select count(*) as lastModifiedDate from " + beanName + " where " + propertyName + "=? and status=1");
			Query qry = session.createQuery(sb.toString());
				qry.setString(0, id);
				list = qry.list();
				
			

			if (CPSUtils.checkList(list) && Integer.parseInt(list.get(0).toString()) != 0) {
				message = CPSConstants.FAILED;
			}

		} catch (Exception e) {
			throw e;
		} finally {
			
		}
		return message;
	}

	public String deleteTrainingMasterData(TrainingMasterBean trainingMstBean) throws Exception
	{
		log.debug("deleteTrainingMasterData() --> Start");
		String message = null;
		Session session = null;
		String sql = null;
		
		try {
			session = hibernateUtils.getSession();
			
			
				sql = "update " + trainingMstBean.getBeanName() + " set status=0,lastModifiedDate=sysdate,lastModifiedBy=? where id=?";
			
			log.debug("Query -->" + sql);
			Query qry = session.createQuery(sql);
			
				qry.setString(0, trainingMstBean.getSfid());
				qry.setString(1, trainingMstBean.getId());
			
			qry.executeUpdate();
			
			message = CPSConstants.DELETE;

		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			
		}
		log.debug("deleteTrainingMasterData() --> End"); 
		return message;
	}
	public List getTrainingInistituteDataList(TrainingMasterBean trainingMstBean) throws Exception
	{
		
		log.debug("deleteTrainingMasterData() --> Start");
		
		Session session = null;
		String sql = null;
		List list = null;
		
		try {
			session = hibernateUtils.getSession();
			sql = "select m.id as id,m.name as name,m.trainingTypeId as trainingTypeId,ttype.name as trainingType,m.shortName as shortName,tr.id as trainingRegionId,tr.name as trainingRegion from TrainingInistituteDTO m,TrainingTypeDTO ttype,TrainingRegionDTO tr where m.status=1 and m.trainingTypeId=ttype.id and ttype.status=1 and tr.id=m.trainingRegionId and m.trainingTypeId=? and m.trainingRegionId = ? and tr.status=1 and m.status=1 order by m.name";
			
			Query qry = session.createQuery(sql);
			qry.setString(0, trainingMstBean.getTrainingTypeId());
			qry.setString(1, trainingMstBean.getTrainingRegionId());
			qry = qry.setResultTransformer(Transformers.aliasToBean(TrainingInistituteDTO.class));
			list = qry.list();
						

		} catch (Exception e) {
			
			throw e;
			
		} finally {
			
		}
		log.debug("deleteTrainingMasterData() --> End");
		return list;
	}
	public List getTrainingVenueDataList(TrainingMasterBean trainingMstBean) throws Exception
	 {
		 log.debug("getTrainingVenueDataList() --> Start");
			
		 Session session = null;
			String sql = null;
			List list = null;
			
			try {
				session = hibernateUtils.getSession();
				sql = "select inistitute.trainingTypeId as trainingTypeId,inistitute.trainingRegionId as trainingRegionId,venue.trainingInistituteId as trainingInistituteId,venue.cityId as cityId,ttype.name as trainingType,region.name as trainingRegion,inistitute.name as trainingInistitute,city.name as city,venue.address as address,venue.phone as phone,venue.fax as fax,venue.email as email,venue.ddFlag as ddFlag,venue.ddAddress as ddAddress,venue.payableAt as payableAt,venue.headOfficeFlag as headOfficeFlag,venue.id as id from TrainingInistituteDTO inistitute,TrainingTypeDTO ttype,TrainingRegionDTO region,TrainingVenueDTO venue,CityDTO city where ttype.status=1 and inistitute.trainingTypeId=ttype.id and region.status=1 and region.id=inistitute.trainingRegionId and inistitute.status=1 and inistitute.id=venue.trainingInistituteId and venue.cityId=city.id and city.status=1 and inistitute.trainingTypeId=? and inistitute.trainingRegionId = ? and venue.trainingInistituteId = ? and venue.status=1 order by inistitute.name";
				
				Query qry = session.createQuery(sql);
				qry.setString(0, trainingMstBean.getTrainingTypeId());
				qry.setString(1, trainingMstBean.getTrainingRegionId());
				qry.setString(2, trainingMstBean.getTrainingInistituteId());
				qry = qry.setResultTransformer(Transformers.aliasToBean(TrainingVenueDTO.class));
				list = qry.list();
							

			} catch (Exception e) {
				
				throw e;
				
			} finally {
				
			}
			log.debug("getTrainingVenueDataList() --> End");
			return list;
	 }
	public List getCourseList(TrainingMasterBean trainingMstBean) throws Exception
	{
		log.debug("deleteTrainingMasterData() --> Start");
		
		Session session = null;
		String sql = null;
		List list = null;
		
		try {
			session = hibernateUtils.getSession();
			sql = "select m.id as id,m.name as name,m.trainingRegionId as trainingRegionId,region.name as trainingRegion,m.trainingTypeId as trainingTypeId,tt.name as trainingType,m.trainingInistituteId as trainingInistituteId,t.name as trainingInistitute,m.courseSubjCategory as courseSubjCategory,m.courseSubjSubCategory as courseSubjSubCategory,m.fee as fee,m.serviceTax as serviceTax,m.courseYear as courseYear,(to_char(fin.fromDate,'YYYY') ||'-'|| to_char(fin.toDate,'YYYY')) as year from CourseDTO m,TrainingRegionDTO region,TrainingTypeDTO tt,TrainingInistituteDTO t,FinancialYearDTO fin where m.status=1 and region.id=m.trainingRegionId and region.status=1 and tt.id=m.trainingTypeId and tt.status = 1 and t.id=m.trainingInistituteId and t.status=1 and fin.id=m.courseYear and fin.status=1 and m.trainingTypeId=? and m.trainingRegionId=? and m.trainingInistituteId=? and m.courseYear =?";
			Query qry = session.createQuery(sql);
			qry.setString(0, trainingMstBean.getTrainingTypeId());
			qry.setString(1, trainingMstBean.getTrainingRegionId());
			qry.setString(2, trainingMstBean.getTrainingInistituteId());
			qry.setString(3, trainingMstBean.getCourseYear());
			
			
			
			qry = qry.setResultTransformer(Transformers.aliasToBean(CourseDTO.class));
						
			list = qry.list();
//			for(int i = 0;CPSUtils.checkList(list) && i < list.size();i++)
//			{
//				CourseDTO dto = (CourseDTO) list.get(i);
//				sql = "select m.designation_id as designationId,m.course_id as courseId,c.category_id as categoryId,d.name as name from hrdg_course_designation_master m,designation_mappings c,designation_master d where c.desig_id=m.designation_id and d.status=1 and d.id=m.designation_id";
//				List designationList = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
//				dto.setDesignationList(designationList);
//
//				sql = "select m.discipline_id as disciplineId,d.name as name,m.course_id as courseId from hrdg_course_discipline_master m,discipline_master d where d.status=1 and d.id=m.discipline_id";
//				List desciplineList = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
//				dto.setDesciplineList(desciplineList);
//			}
			
			

		} catch (Exception e) {
			
			throw e;
			
		} finally {
			
		}
		log.debug("deleteTrainingMasterData() --> End");
		return list;
	}
	public List checkCourse(TrainingMasterBean trainingMstBean) throws Exception
	{
		log.debug("checkCourse() --> Start");
		Session session = null;
		List list = null;
		
		
		try {
			
			session = hibernateUtils.getSession();
			String sql = "from CourseDTO where upper(name)=? and status = 1";
			       sql +=" and trainingRegionId=? and trainingTypeId=? and trainingInistituteId=? and courseYear=?";// and to_char(to_date(courseYear),'mm')+0=?";
//				if(!CPSUtils.isNullOrEmpty(trainingMstBean.getCourseSubjSubCategoryId()))
//				  sql +=" and courseSubjSubCategoryId=?";
//				else
//					sql +=" and courseSubjSubCategoryId is null";
//				if(!CPSUtils.isNullOrEmpty(trainingMstBean.getCourseSubjCategoryId()))
//					sql +=" and courseSubjCategoryId=?";
//				else
//					sql +=" and courseSubjCategoryId is null";
			
			    if(!CPSUtils.isNullOrEmpty(trainingMstBean.getId()))
				  sql +=" and id != "+trainingMstBean.getId();
			
			
			Query qry = session.createQuery(sql);
			qry.setString(0, trainingMstBean.getName().trim().toUpperCase());
			
			
				qry.setString(1, trainingMstBean.getTrainingRegionId());
				qry.setString(2, trainingMstBean.getTrainingTypeId());
				qry.setString(3, trainingMstBean.getTrainingInistituteId());
				qry.setString(4, trainingMstBean.getCourseYear());
//				qry.setInteger(5, CPSUtils.critFormattedDate(trainingMstBean.getCourseYear()).getYear()+1900);
//				qry.setInteger(6, CPSUtils.critFormattedDate(trainingMstBean.getCourseYear()).getMonth());
//				if(!CPSUtils.isNullOrEmpty(trainingMstBean.getCourseSubjSubCategoryId()))
//				qry.setString(5, trainingMstBean.getCourseSubjSubCategoryId());
//				
//				if(!CPSUtils.isNullOrEmpty(trainingMstBean.getCourseSubjCategoryId()))
//					qry.setString(6, trainingMstBean.getCourseSubjCategoryId());
//				
				
			
			list = qry.setResultTransformer(Transformers.aliasToBean(CourseDTO.class)).list();
			

		} catch (Exception e) {
			
			throw e;
			
		} finally {
			
		}
		log.debug("checkCourse() --> End");
		return list;
	}
	public String updateCourse(TrainingMasterBean trainingMstBean) throws Exception
	{
		log.debug("update() --> Start");
		String message = null;
		Session session = null;
		
		String sb = null;
		try {
			session = hibernateUtils.getSession();
			
			
				sb = "update CourseDTO set name=?,lastModifiedDate=sysdate,trainingRegionId=?,trainingTypeId=?,trainingInistituteId=?,courseSubjCategory=?,courseSubjSubCategory=?,fee=?,serviceTax=?,courseYear=?,lastModifiedBy=?  ";
				
					
				sb += "where id="+trainingMstBean.getId();
			
			
				Query qry = session.createQuery(sb);
			
			
				qry.setString(0, trainingMstBean.getName());
				qry.setString(1, trainingMstBean.getTrainingRegionId());
				qry.setString(2, trainingMstBean.getTrainingTypeId());
				qry.setString(3, trainingMstBean.getTrainingInistituteId());
				qry.setString(4, trainingMstBean.getCourseSubjCategory());
				qry.setString(5, trainingMstBean.getCourseSubjSubCategory());
				qry.setString(6, trainingMstBean.getFee());
				qry.setString(7, trainingMstBean.getServiceTax());
				qry.setString(8, trainingMstBean.getCourseYear());
				qry.setString(9, trainingMstBean.getSfid());
			
			
			qry.executeUpdate();
			

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
	public String deleteCourse(TrainingMasterBean trainingMstBean,String beanName) throws Exception
	{
		log.debug("deleteCourse() --> Start");
		String message = null;
		Session session = null;
		
		String sb = null;
		try {
			session = hibernateUtils.getSession();
			
			
				sb = "delete "+beanName+" where courseId=?";
				if(CPSUtils.compareStrings(beanName, CPSConstants.COURSEDESIGNATIONSDTO))
					sb +=" and categoryId=?";
				else if(CPSUtils.compareStrings(beanName, CPSConstants.TRAININGCIRCULATIONDEPTDTO))
					sb = "delete "+beanName+" where circulationId=?";
				
				Query qry = session.createQuery(sb);
				qry.setString(0, trainingMstBean.getCourseId());
				if(CPSUtils.compareStrings(beanName, CPSConstants.COURSEDESIGNATIONSDTO))
					qry.setString(1, trainingMstBean.getCategoryId());
				else if(CPSUtils.compareStrings(beanName, CPSConstants.TRAININGCIRCULATIONDEPTDTO))
					qry.setString(0, trainingMstBean.getId());
				
		    qry.executeUpdate();
			

			message = CPSConstants.DELETE;
			log.debug("message -->" + message);
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			
		}
		log.debug("deleteCourse() --> End");
		return message;
	}
	public List getCourseDurationList(TrainingMasterBean trainingMstBean) throws Exception
	{
log.debug("getCourseDurationList() --> Start");
		
		Session session = null;
		String sql = null;
		List list = null;
		
		try {
			session = hibernateUtils.getSession();
			sql = "select m.id as id,m.startDate as startDate,m.endDate as endDate,m.courseId as courseId,c.name as course from CourseDurationsDTO m,CourseDTO c where c.status=1 and m.status=1 and m.courseId=c.id and m.courseId=?";
			
			Query qry = session.createQuery(sql);
			qry.setString(0, trainingMstBean.getCourseId());
			qry = qry.setResultTransformer(Transformers.aliasToBean(CourseDurationsDTO.class));
			list = qry.list();
			
			

		} catch (Exception e) {
			
			throw e;
			
		} finally {
			
		}
		log.debug("getCourseDurationList() --> End");
		return list;
	}
	public List checkCourseDuration(TrainingMasterBean trainingMstBean) throws Exception
	{
		log.debug("checkCourse() --> Start");
		Session session = null;
		List list = null;
		
		
		try {
			
			session = hibernateUtils.getSession();
			String sql = "from CourseDurationsDTO where status = 1";
			       sql +=" and startDate=? and endDate=? and courseId=?";
				
			    if(!CPSUtils.isNullOrEmpty(trainingMstBean.getId()))
				  sql +=" and id != "+trainingMstBean.getId();
			
			
			    Query qry = session.createQuery(sql);
				qry.setString(0, trainingMstBean.getStartDate());
				qry.setString(1, trainingMstBean.getEndDate());
				qry.setString(2, trainingMstBean.getCourseId());
				
				
			
			list = qry.setResultTransformer(Transformers.aliasToBean(CourseDurationsDTO.class)).list();
			

		} catch (Exception e) {
			
			throw e;
			
		} finally {
			
		}
		log.debug("checkCourse() --> End");
		return list;
	}
	
	public String updateCourseDuration(TrainingMasterBean trainingMstBean) throws Exception
	{
		log.debug("update() --> Start");
		String message = null;
		Session session = null;
		
		String sb = null;
		try {
			session = hibernateUtils.getSession();
			
			
				sb = "update CourseDurationsDTO set startDate=?,endDate=?,lastModifiedDate=sysdate,lastModifiedBy=?";
				sb += "where id="+trainingMstBean.getId();
				Query qry = session.createQuery(sb);
			
			
				qry.setString(0, trainingMstBean.getStartDate());
				qry.setString(1, trainingMstBean.getEndDate());
				qry.setString(2, trainingMstBean.getSfid());
				
			
			
			qry.executeUpdate();
			

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
	public List checkHRDGBoard(TrainingMasterBean trainingMstBean) throws Exception
	{
		log.debug("checkHRDGBoard() --> Start");
		Session session = null;
		List list = null;
		
		
		try {
			
			session = hibernateUtils.getSession();
			String sql = "from HRDGBoardDTO where status = 1";
			       sql +=" and name=?";
				
			    if(!CPSUtils.isNullOrEmpty(trainingMstBean.getId()))
				  sql +=" and id != "+trainingMstBean.getId();
			
			
			    Query qry = session.createQuery(sql);
				qry.setString(0, trainingMstBean.getName());
				
				
			
			list = qry.list();
			

		} catch (Exception e) {
			
			throw e;
			
		} finally {
			
		}
		log.debug("checkCourse() --> End");
		return list;
	}
	public String updateHRDGBoard(TrainingMasterBean trainingMstBean) throws Exception
	{
		log.debug("updateHRDGBoard() --> Start");
		String message = null;
		Session session = null;
		
		String sb = null;
		try {
			session = hibernateUtils.getSession();
			
			
				sb = "update HRDGBoardDTO set name=?,boardTypeId=?,yearId=?,lastModifiedDate=sysdate,lastModifiedBy=?";
				sb += " where id="+trainingMstBean.getId();
				Query qry = session.createQuery(sb);
			
			
				qry.setString(0, trainingMstBean.getName());
				qry.setString(1, trainingMstBean.getBoardTypeId());
				qry.setString(2, trainingMstBean.getYearId());
				qry.setString(3, trainingMstBean.getSfid());
				
			
			
			qry.executeUpdate();
			

			message = CPSConstants.UPDATE;
			log.debug("message -->" + message);
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			
		}
		log.debug("updateHRDGBoard() --> End");
		return message;
	}
	public List getHRDGBoardList(TrainingMasterBean trainingMstBean) throws Exception
	{
		log.debug("getCourseDurationList() --> Start");
		
		Session session = null;
		String sql = null;
		List list = null;
		
		try {
			session = hibernateUtils.getSession();
			sql = "select m.id as id,m.name as name,m.yearId as yearId,y.name as year,m.boardTypeId as boardTypeId,bt.name as boardType from HRDGBoardDTO m,HRDGBoardTypeDTO bt,YearTypeDTO y where m.status=1 and y.status=1 and bt.status=1 and m.yearId=y.id and m.boardTypeId=bt.id";
			
			sql +=" and m.boardTypeId = ? and m.yearId=?";
				if(!CPSUtils.isNullOrEmpty(trainingMstBean.getId()))
					sql += " and m.id=?";
			Query qry = session.createQuery(sql);
			qry.setString(0, trainingMstBean.getBoardTypeId());
			qry.setString(1, trainingMstBean.getYearId());
			if(!CPSUtils.isNullOrEmpty(trainingMstBean.getId()))
			qry.setString(2, trainingMstBean.getId());
			list = qry.setResultTransformer(Transformers.aliasToBean(HRDGBoardDTO.class)).list();
				
			

		} catch (Exception e) {
			
			throw e;
			
		} finally {
			
		}
		log.debug("getCourseDurationList() --> End");
		return list;
	}
	public List checkHRDGBoardMember(TrainingMasterBean trainingMstBean) throws Exception
	{
		log.debug("checkHRDGBoard() --> Start");
		Session session = null;
		List list = null;
		
		
		try {
			
			session = hibernateUtils.getSession();
			String sql = "from HRDGBoardMemberDTO where status = 1";
			       sql +=" and sfid = ? and boardId = ?";
				
			    if(!CPSUtils.isNullOrEmpty(trainingMstBean.getId()))
				  sql +=" and id != "+trainingMstBean.getId();
			    
			    if(!CPSUtils.isNullOrEmpty(trainingMstBean.getId()) && !CPSUtils.isNullOrEmpty(trainingMstBean.getMemberTypeId()))
					  sql +=" and memberTypeId = "+trainingMstBean.getMemberTypeId();
			   
			
			    Query qry = session.createQuery(sql);
				qry.setString(0, trainingMstBean.getBoardMemberSfid());
				qry.setString(1, trainingMstBean.getBoardId());
				
				
				
			
			list = qry.list();
			

		} catch (Exception e) {
			
			throw e;
			
		} finally {
			
		}
		log.debug("checkCourse() --> End");
		return list;
	}
	public String updateHRDGBoardMember(TrainingMasterBean trainingMstBean) throws Exception
	{
		log.debug("updateHRDGBoardMember() --> Start");
		String message = null;
		Session session = null;
		
		String sb = null;
		try {
			session = hibernateUtils.getSession();
			
			
				sb = "update HRDGBoardMemberDTO set sfid=?,boardId=?,memberTypeId=?,lastModifiedDate=sysdate,lastModifiedBy=?";
				sb += " where id="+trainingMstBean.getId();
				Query qry = session.createQuery(sb);
			
			
				qry.setString(0, trainingMstBean.getBoardMemberSfid());
				qry.setString(1, trainingMstBean.getBoardId());
				qry.setString(2, trainingMstBean.getMemberTypeId());
				qry.setString(3, trainingMstBean.getSfid());
				
			
			
			qry.executeUpdate();
			

			message = CPSConstants.UPDATE;
			log.debug("message -->" + message);
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			
		}
		log.debug("updateHRDGBoardMember() --> End");
		return message;
	}
	public List getHRDGBoardMemberList(TrainingMasterBean trainingMstBean) throws Exception
	{
		log.debug("getCourseDurationList() --> Start");
		
		Session session = null;
		String sql = null;
		List list = null;
		
		try {
			session = hibernateUtils.getSession();
			sql = "select m.id as id,m.sfid as sfid,e.firstName as name,m.boardId as boardId,b.name as board,m.memberTypeId as memberTypeId,mt.name as memberType from HRDGBoardMemberDTO m,HRDGBoardDTO b,HRDGBoardMemberTypeDTO mt,EmployeeBean e where m.status=1 and b.status=1 and e.status=1 and mt.status=1 and m.sfid=e.userSfid and m.boardId=b.id and m.memberTypeId=mt.id and m.boardId=?";
			
			Query qry = session.createQuery(sql);
			qry.setString(0, trainingMstBean.getBoardId());
			qry = qry.setResultTransformer(Transformers.aliasToBean(HRDGBoardMemberDTO.class));
			list = qry.list();

		} catch (Exception e) {
			
			throw e;
			
		} finally {
			
		}
		log.debug("getCourseDurationList() --> End");
		return list;
	}
	public List getCourseListBasedonDuration(TrainingMasterBean trainingMstBean) throws Exception
	{
		log.debug("getCourseListBasedonDuration() --> Start");
		
		Session session = null;
		String sql = null;
		List list = null; 
		
		try {
			session = hibernateUtils.getSession();
			//sql = "Select Ini.name As description,course.id as courseId,course.name as course,dur.id as id,dur.startDate as startDate,dur.endDate as endDate,(select cir.id as circulationId from TrainingCirculationDTO cir where cir.durationId=dur.id) as lastModifiedBy from TrainingInistituteDTO ini,CourseDTO course,CourseDurationsDTO dur where ini.id=course.trainingInistituteId and course.id=dur.courseId and dur.startDate >=to_date(?,'DD-MON-YYYY') and dur.endDate <=to_date(?,'DD-MON-YYYY') and course.status=1 and ini.status=1 order by ini.id,course.id";
			sql = "Select Init.Training_inistitute_name As Description,Course.Training_course_id As courseId,Course.Course_name As Course,Dur.Course_duration_id As id,Dur.Start_date As Startdate,Dur.End_date As Enddate,(select cir.status from hrdg_training_cir_master cir where cir.course_duration_id=dur.course_duration_id) as lastModifiedBy From Hrdg_training_course_details Course,hrdg_training_inist_master Init,Hrdg_course_duration_master Dur Where Course.Inistitute_id=Init.Training_inistitute_id And Course.Training_course_id=Dur.Course_id and dur.start_date >=to_date('"+trainingMstBean.getStartDate()+"','DD-MON-YYYY') and dur.end_date <=to_date('"+trainingMstBean.getEndDate()+"','DD-MON-YYYY') and course.status=1 and init.status=1 and dur.status=1 Order By Init.Training_inistitute_id,course.training_course_id";
			//sql  = "select distinct(c.id) as id,c.name as name from CourseDTO c,CourseDurationsDTO cd where c.status = 1 and cd.status=1 and cd.startDate >=to_date(?,'DD-MON-YYYY') and cd.endDate <=to_date(?,'DD-MON-YYYY') and c.id=cd.courseId group by c.id,c.name";

			
			list = session.createSQLQuery(sql).addScalar("description",Hibernate.STRING).addScalar("courseId",Hibernate.INTEGER).addScalar("course",Hibernate.STRING).addScalar("id",Hibernate.INTEGER).addScalar("startDate",Hibernate.STRING).addScalar("endDate",Hibernate.STRING).addScalar("lastModifiedBy",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(CourseDurationsDTO.class)).list();
         

		} catch (Exception e) {
			
			throw e;
			
		} finally {
			 
		}
		log.debug("getCourseListBasedonDuration() --> End");
		return list;
	}
	public List getTrainingRegionDataList(TrainingMasterBean trainingMstBean) throws Exception
	{
		log.debug("getTrainingRegionDataList() --> Start");
		
		Session session = null;
		String sql = null;
		List list = null;
		
		try {
			session = hibernateUtils.getSession();
			sql = "select tr.id as id,tr.name as name,tr.description as description,t.id as trainingTypeId,t.name as trainingType from TrainingRegionDTO tr,TrainingTypeDTO t where t.id=? and t.id=tr.trainingTypeId and tr.status=1 and t.status=1";
			
			Query qry = session.createQuery(sql);
			qry.setString(0, trainingMstBean.getTrainingTypeId());
			qry = qry.setResultTransformer(Transformers.aliasToBean(TrainingRegionDTO.class));
			list = qry.list();

		} catch (Exception e) {
			
			throw e;
			
		} finally {
			
		}
		log.debug("getTrainingRegionDataList() --> End");
		return list;
	}


	public List checkTrainingRegion(TrainingMasterBean trainingMstBean) throws Exception
	{
		log.debug("checkTrainingRegion() --> Start");
		
		Session session = null;
		String sql = null;
		List list = null;
		
		try {
			session = hibernateUtils.getSession();
			sql = "select tr.id as id,tr.name as name,tr.description as description,t.id as trainingTypeId,t.name as trainingType from TrainingRegionDTO tr,TrainingTypeDTO t where t.id=? and upper(tr.name)=? and t.id=tr.trainingTypeId and tr.status = 1 and t.status=1";
			if(!CPSUtils.isNullOrEmpty(trainingMstBean.getId()))
				sql +=" and tr.id != "+trainingMstBean.getId();
			Query qry = session.createQuery(sql);
			qry.setString(0, trainingMstBean.getTrainingTypeId());
			qry.setString(1, trainingMstBean.getName().trim().toUpperCase());
			qry = qry.setResultTransformer(Transformers.aliasToBean(TrainingRegionDTO.class));
			list = qry.list();

		} catch (Exception e) {
			
			throw e;
			
		} finally {
			
		}
		log.debug("checkTrainingRegion() --> End");
		return list;
	}
	
	public List getTrainingCirculationList(TrainingMasterBean trainingMstBean) throws Exception
	{
		log.debug("getTrainingCirculationList() --> Start");
		
		Session session = null;
		String sql = null; 
		List list = null;
		
		try {
			session = hibernateUtils.getSession();
			sql = "select id as id from TrainingCirculationDTO where courseId=? and durationId=? and venueId=? and nominationStartDate=? and nominationEndDate=? and circulationDate=?";
			if(!CPSUtils.isNullOrEmpty(trainingMstBean.getId()))
				sql += " and id!="+trainingMstBean.getId();
			
			Query qry = session.createQuery(sql);
			qry.setString(0, trainingMstBean.getCourseId());
			qry.setString(1, trainingMstBean.getDurationId());
			qry.setString(2, trainingMstBean.getVenueId());
			qry.setString(3, trainingMstBean.getNominationStartDate());
			qry.setString(4, trainingMstBean.getNominationEndDate());
			qry.setString(5, trainingMstBean.getCirculationDate());
			list = qry.setResultTransformer(Transformers.aliasToBean(TrainingCirculationDTO.class)).list();
			
			
			

		} catch (Exception e) {
			
			throw e;
			
		} finally {
			
		}
		log.debug("getTrainingCirculationList() --> End");
		return list;
	}
	public String deleteTrainingCirculation(TrainingMasterBean trainingMstBean,String beanName) throws Exception
	{
		log.debug("update() --> Start");
		String message = null;
		Session session = null;
		
		String sb = null;
		try {
			session = hibernateUtils.getSession();
			
			
				sb = "delete "+beanName+" where circulationId=?";
				
				Query qry = session.createQuery(sb);
				qry.setString(0, trainingMstBean.getId());
				
		    qry.executeUpdate();
			

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
	public String updateTrainingCirculation(TrainingMasterBean trainingMstBean) throws Exception
	{
		log.debug("updateTrainingCirculation() --> Start");
		String message = null;
		Session session = null;
		
		String sb = null;
		try {
			session = hibernateUtils.getSession();
			
			
				sb = "update TrainingCirculationDTO set venueId=?,nominationStartDate=?,nominationEndDate=?,organizer=?,brochure=?,lastModifiedDate=sysdate,circulationDate=?,ionId=?,lastModifiedBy=? ";
				
					
				sb += "where id="+trainingMstBean.getId();
			
			
				Query qry = session.createQuery(sb);
			 
			
				qry.setString(0, trainingMstBean.getVenueId());
				qry.setString(1, trainingMstBean.getNominationStartDate());
				qry.setString(2, trainingMstBean.getNominationEndDate());
				qry.setString(3, trainingMstBean.getOrganizer());
				qry.setString(4, trainingMstBean.getBrochure());
				qry.setString(5, trainingMstBean.getCirculationDate());
				qry.setString(6, trainingMstBean.getIonId());
				qry.setString(7, trainingMstBean.getSfid());
				
				
			
			qry.executeUpdate();
			

			message = CPSConstants.UPDATE;
			log.debug("message -->" + message);
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			
		}
		log.debug("updateTrainingCirculation() --> End");
		return message;
	}
	public List getDeptHeadSfidList(String[] departmentIds,int cirId) throws Exception
	{
		log.debug("getDeptHeadSfidList() --> Start");
		
		Session session = null;
		Query qry = null;
		String sb = null;
		List list = null;
		try {
			session = hibernateUtils.getSession();
			//session.flush();
			sb = "select distinct(emp.sfid) as sfid from emp_role_mapping emp,org_role_instance ori,departments_master d where ori.is_head=1 and ori.department_id =d.department_id and d.department_id in (select cd.department_id from hrdg_circulation_departments cd where cd.cir_id="+cirId+") and emp.org_role_id=ori.org_role_id and ori.status=1 and emp.status=1 and d.status=1";

			qry = session.createSQLQuery(sb).addScalar("sfid", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(EmployeeBean.class));
			
			list = qry.list();
		    			
			
		} catch (Exception e) {
			
			throw e;
		} finally {
			
		}
		log.debug("getDeptHeadSfidList() --> End");
		return list;
	}
	 public List getTrainingCirculationDetails(String id,String param) throws Exception
	{
		log.debug("getTrainingCirculationDetails() --> Start");
		
		Session session = null;
		Query qry = null;
		String sb = null;
		List list = null;
		try {
			session = hibernateUtils.getSession();
			if(CPSUtils.compareStrings(param, CPSConstants.TRAININGALERTID))
			{
			list = session.createCriteria(TrainingCirculationDTO.class).add(Expression.eq("id",Integer.parseInt(id))).list();
			}
			else if(CPSUtils.compareStrings(param, CPSConstants.TRAININGFINALSELECTIONALERTID))
			{
				sb = "select c.id as id,c.courseId as courseId,c.durationId as durationId,c.venueId from HrdgTxnRequestDTO t,TrainingNominationDTO n,TrainingCirculationDTO c where t.id=? and t.requestId=n.requestId and n.cirId=c.id";
				list = session.createCriteria(TrainingCirculationDTO.class).add(Expression.eq("id",Integer.parseInt(id))).list();
			}
			if(CPSUtils.checkList(list))
			{
				TrainingCirculationDTO dto = (TrainingCirculationDTO)list.get(0);
				dto.setCourseDto((CourseDTO)session.createCriteria(CourseDTO.class).add(Expression.eq("id",dto.getCourseId())).add(Expression.eq("status",1)).uniqueResult());
				dto.setCourseDurationDto((CourseDurationsDTO)session.createCriteria(CourseDurationsDTO.class).add(Expression.eq("id",dto.getDurationId())).add(Expression.eq("status",1)).uniqueResult());
				dto.setTrainingVenueDto((TrainingVenueDTO)session.createCriteria(TrainingVenueDTO.class).add(Expression.eq("id",dto.getVenueId())).add(Expression.eq("status",1)).uniqueResult());
				dto.setTrainingInistituteDto((TrainingInistituteDTO)session.createCriteria(TrainingInistituteDTO.class).add(Expression.eq("id",dto.getCourseDto().getTrainingInistituteId())).add(Expression.eq("status",1)).uniqueResult());
			}
		    			
			
		} catch (Exception e) {
			
			throw e;
		} finally {
			
		}
		log.debug("getTrainingCirculationDetails() --> End");
		return list;
	}
	 public List<DisciplineDTO> getSelectedDisciplineList(TrainingMasterBean trainingMstBean) throws Exception
	 {
		 log.debug("getDeptHeadSfidList() --> Start");
			
			Session session = null;
			Query qry = null;
			String sb = null;
			List list = null;
			try {
				session = hibernateUtils.getSession();
				sb = "select cd.disciplineId as id,d.name as name from CourseDisciplinesDTO cd,DisciplineDTO d where cd.courseId=? and cd.disciplineId=d.id and d.status=1";

				qry = session.createQuery(sb).setString(0, trainingMstBean.getCourseId());
				qry = qry.setResultTransformer(Transformers.aliasToBean(DisciplineDTO.class));
				
				list = qry.list();
			    			
				
			} catch (Exception e) { 
				
				throw e;
			} finally {
				
			}
			log.debug("getDeptHeadSfidList() --> End");
			return list;
	 }
	 public List checkCourseDiscipline(TrainingMasterBean trainingMstBean) throws Exception
	 {
	 log.debug("getDeptHeadSfidList() --> Start");
		
		Session session = null;
		Query qry = null;
		String sb = null;
		List list = null;
		try {
			session = hibernateUtils.getSession();
			sb = "select cd.disciplineId as id,d.name as discipline,c.name as course,c.id as courseId from CourseDisciplinesDTO cd,DisciplineDTO d,CourseDTO c where cd.courseId=? and cd.disciplineId=d.id and cd.courseId=c.id and c.status=1 and d.status=1";

			qry = session.createQuery(sb).setString(0, trainingMstBean.getCourseId());
			qry = qry.setResultTransformer(Transformers.aliasToBean(CourseDisciplinesDTO.class));
			
			
			list = qry.list();
		    			
			
		} catch (Exception e) {
			
			throw e;
		} finally {
			
		}
		log.debug("getDeptHeadSfidList() --> End");
		return list;
	 }
	 public List getCourseDisciplineList(TrainingMasterBean trainingMstBean) throws Exception
	 {
	 log.debug("getDeptHeadSfidList() --> Start");
		
		Session session = null;
		Query qry = null;
		String sb = null;
		List list = null;
		try {
			session = hibernateUtils.getSession();
			sb = "select cd.disciplineId as id,d.name as discipline,c.name as course,c.id as courseId from CourseDisciplinesDTO cd,DisciplineDTO d,CourseDTO c where cd.courseId=? and cd.disciplineId=d.id and cd.courseId=c.id and c.status=1 and d.status=1";

			qry = session.createQuery(sb).setString(0, trainingMstBean.getCourseId());
			qry = qry.setResultTransformer(Transformers.aliasToBean(CourseDisciplinesDTO.class));
			
			list = qry.list();
		    			
			
		} catch (Exception e) {
			
			throw e;
		} finally {
			
		}
		log.debug("getDeptHeadSfidList() --> End");
		return list;
	 }
	 public List checkCourseQualification(TrainingMasterBean trainingMstBean) throws Exception
	 {
		 log.debug("getDeptHeadSfidList() --> Start");
			
			Session session = null;
			Query qry = null;
			String sb = null;
			List list = null;
			try {
				session = hibernateUtils.getSession();
				sb = "select cd.qualificationId as id,d.name as qualification,c.name as course,c.id as courseId from CourseQualificationsDTO cd,QualificationDTO d,CourseDTO c where cd.courseId=? and cd.qualificationId=d.id and cd.courseId=c.id and c.status=1 and d.status=1";

				qry = session.createQuery(sb).setString(0, trainingMstBean.getCourseId());
				qry = qry.setResultTransformer(Transformers.aliasToBean(CourseQualificationsDTO.class));
				
				
				list = qry.list();
			    			
				
			} catch (Exception e) {
				
				throw e;
			} finally {
				
			}
			log.debug("getDeptHeadSfidList() --> End");
			return list;
	 }
	 


		public List getCourseQualificationList(TrainingMasterBean trainingMstBean) throws Exception
		{
			log.debug("getDeptHeadSfidList() --> Start");
			
			Session session = null;
			Query qry = null;
			String sb = null;
			List list = null;
			try {
				session = hibernateUtils.getSession();
				sb = "select cd.qualificationId as id,d.name as qualification,c.name as course,c.id as courseId from CourseQualificationsDTO cd,QualificationDTO d,CourseDTO c where cd.courseId=? and cd.qualificationId=d.id and cd.courseId=c.id and c.status=1 and d.status=1";

				qry = session.createQuery(sb).setString(0, trainingMstBean.getCourseId());
				qry = qry.setResultTransformer(Transformers.aliasToBean(CourseQualificationsDTO.class));
				
				
				list = qry.list();
			    			
				
			} catch (Exception e) {
				
				throw e;
			} finally {
				
			}
			log.debug("getDeptHeadSfidList() --> End");
			return list;
		}


		public List<QualificationDTO> getSelectedQualificationList(TrainingMasterBean trainingMstBean) throws Exception
		{
			log.debug("getDeptHeadSfidList() --> Start");
			
			Session session = null;
			Query qry = null;
			String sb = null;
			List list = null;
			try {
				session = hibernateUtils.getSession();
				sb = "select cd.qualificationId as id,d.name as name from CourseQualificationsDTO cd,QualificationDTO d where cd.courseId=? and cd.qualificationId=d.id and d.status=1";

				qry = session.createQuery(sb).setString(0, trainingMstBean.getCourseId());
				qry = qry.setResultTransformer(Transformers.aliasToBean(QualificationDTO.class));
				
				
				list = qry.list();
			    			
				
			} catch (Exception e) {
				
				throw e;
			} finally {
				
			}
			log.debug("getDeptHeadSfidList() --> End");
			return list;
		}
		public String updateTrainingRegion(TrainingMasterBean trainingMstBean) throws Exception
		{
			log.debug("updateTrainingRegion() --> Start");
			String message = null;
			Session session = null;
			
			String sb = null;
			try {
				session = hibernateUtils.getSession();
				
				
					sb = "update TrainingRegionDTO set name=?,description=?,lastModifiedDate=sysdate,lastModifiedBy=? where id="+trainingMstBean.getId();
					Query qry = session.createQuery(sb);
				
				
					qry.setString(0, trainingMstBean.getName());
					qry.setString(1, trainingMstBean.getDescription());
					qry.setString(2, trainingMstBean.getSfid());
					
				
				qry.executeUpdate();
				

				message = CPSConstants.UPDATE;
				log.debug("message -->" + message);
			} catch (Exception e) {
				message = CPSConstants.FAILED;
				throw e;
			} finally {
				
			}
			log.debug("updateTrainingRegion() --> End");
			return message;
		}
		public List getDesignationList(TrainingMasterBean trainingMstBean) throws Exception
		{
			log.debug("getDesignationList() --> Start");
			
			Session session = null;
			Query qry = null;
			String sb = null;
			List masterObjList = null;
			try {
				session = hibernateUtils.getSession();
				sb = "select d.category_id as category_id,m.id as id,m.name as name from designation_master m,designation_mappings d where m.id=d.desig_id and m.status=1 and d.category_id=? order by m.order_no";
				qry = session.createSQLQuery(sb).setString(0, trainingMstBean.getCategoryId());
				masterObjList = qry.list();
				if (CPSUtils.checkList(masterObjList))
				{
					List list = new ArrayList();
					for(int i = 0;i < masterObjList.size();i++)
					{
						DesignationDTO dto = new DesignationDTO();
						Object[] obj = (Object[])masterObjList.get(i);
						dto.setCategoryID(!(CPSUtils.isNullOrEmpty(obj[0]))?obj[0].toString():null);
						dto.setId(!(CPSUtils.isNullOrEmpty(obj[1]))?Integer.parseInt(obj[1].toString()):1);
						dto.setName(!(CPSUtils.isNullOrEmpty(obj[2]))?obj[2].toString():null);
						list.add(dto);
					}
					return list;
					
				}
			    			
				
			} catch (Exception e) {
				
				throw e;
			} finally {
				
			}
			log.debug("getDesignationList() --> End");
			return masterObjList;
		}


		public List getCourseDesignationList(TrainingMasterBean trainingMstBean) throws Exception
		{
			log.debug("getCourseDesignationList() --> Start");
			
			Session session = null;
			Query qry = null;
			String sb = null;
			List list = null;
			try {
				session = hibernateUtils.getSession();
				session = hibernateUtils.getSession();
//				sb = "SELECT d.category_id AS categoryId,m.id AS id, M.Name As Name,Cm.Name As Category,c.COURSE_NAME as course FROM designation_master m,Designation_mappings D,Hrdg_course_designation_master Cd,Hrdg_training_course_details C,category_master cm WHERE m.id=d.desig_id And M.Status =1 AND d.category_id ="+trainingMstBean.getCategoryId()+" AND cd.category_id =d.category_id And Cd.Designation_id=M.Id And Cd.Course_id=C.Training_course_id and c.status=1 and cm.status=1 and C.Training_course_id  ="+trainingMstBean.getCourseId();
//				list = session.createSQLQuery(sb).addScalar("categoryId",Hibernate.INTEGER).addScalar("id",Hibernate.INTEGER).addScalar("name",Hibernate.STRING).addScalar("category",Hibernate.STRING).addScalar("course",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(CourseDesignationsDTO.class)).list();
//				
//				
				sb = "SELECT c.id as categoryId,C.Name as category,(Select Rtrim(Xmlagg (Xmlelement (E, d1.name|| ',')).Extract ('//text()'),',')As Date1 From Hrdg_course_designation_master Cd1, Designation_master D1 WHERE cd1.category_id=c.id AND d1.id  =cd1.designation_id and cd1.course_id    ="+trainingMstBean.getCourseId()+")AS name FROM hrdg_course_designation_master cd,Category_master C WHERE cd.category_id=c.id AND cd.course_id    ="+trainingMstBean.getCourseId()+" Group By C.Id,C.Name";
				list = session.createSQLQuery(sb).addScalar("categoryId",Hibernate.INTEGER).addScalar("name",Hibernate.STRING).addScalar("category",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(CourseDesignationsDTO.class)).list();
				
				
			} catch (Exception e) {
				
				throw e;
			} finally {
				
			}
			log.debug("getCourseDesignationList() --> End");
			return list;
		}
		
		public List getSelectedDesignationList(TrainingMasterBean trainingMstBean) throws Exception
		{
			log.debug("getCourseDesignationList() --> Start");
			
			Session session = null;
			Query qry = null;
			String sb = null;
			List list = null;
			try {
				
				session = hibernateUtils.getSession();
				sb = "SELECT d.category_id AS categoryId,m.id AS id, M.Name As Name FROM designation_master m,Designation_mappings D,Hrdg_course_designation_master Cd WHERE m.id=d.desig_id And M.Status =1 and d.category_id ="+trainingMstBean.getCategoryId()+" AND cd.category_id =d.category_id And Cd.Designation_id=M.Id and cd.course_id  ="+trainingMstBean.getCourseId()+" order by m.order_no";
				list = session.createSQLQuery(sb).addScalar("categoryId",Hibernate.INTEGER).addScalar("id",Hibernate.INTEGER).addScalar("name",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(CourseDesignationsDTO.class)).list();
				
			    			
				
			} catch (Exception e) {
				
				throw e;
			} finally {
				
			}
			log.debug("getCourseDesignationList() --> End");
			return list;
		}
		public List checkCourseDesignation(TrainingMasterBean trainingMstBean) throws Exception
		{
			log.debug("getCourseDesignationList() --> Start");
			
			Session session = null;
			Query qry = null;
			String sb = null;
			List list = null;
			try {
				session = hibernateUtils.getSession();
				
				sb = "SELECT d.category_id AS categoryId,m.id AS id, M.Name As Name FROM designation_master m,Designation_mappings D,Hrdg_course_designation_master Cd WHERE m.id=d.desig_id And M.Status =1 AND d.category_id ="+trainingMstBean.getCategoryId()+" AND cd.category_id =d.category_id And Cd.Designation_id=M.Id and cd.course_id  ="+trainingMstBean.getCourseId();
				list = session.createSQLQuery(sb).addScalar("categoryId",Hibernate.INTEGER).addScalar("id",Hibernate.INTEGER).addScalar("name",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(CourseDesignationsDTO.class)).list();
				
			    			
				
			} catch (Exception e) {
				
				throw e;
			} finally {
				
			}
			log.debug("getCourseDesignationList() --> End");
			return list;
		}
		public List getCourseContent(TrainingMasterBean trainingMstBean) throws Exception
		{

			log.debug("getCourseContent() --> Start");
			
			Session session = null;
			Query qry = null;
			String sb = null;
			List list = null;
			try {
				session = hibernateUtils.getSession();
				session = hibernateUtils.getSession();
				sb = "select content.id as id,content.courseId as courseId,content.courseContent as courseContent from CourseContentDTO content where content.courseId=?";
				list = session.createQuery(sb).setString(0, trainingMstBean.getCourseId())
				.setResultTransformer(Transformers.aliasToBean(CourseContentDTO.class)).list();
				
			    			
				
			} catch (Exception e) {
				
				throw e;
			} finally {
				
			}
			log.debug("getCourseContent() --> End");
			return list;
		}


		public List checkCourseContent(TrainingMasterBean trainingMstBean) throws Exception
		{
			log.debug("checkCourseContent() --> Start");
			
			Session session = null;
			Query qry = null;
			String sb = null;
			List list = null;
			try {
				session = hibernateUtils.getSession();
				session = hibernateUtils.getSession();
				sb = "select content.id as id,content.courseId as courseId,content.courseContent as courseContent from CourseContentDTO content where content.courseId=?";
				list = session.createQuery(sb).setString(0, trainingMstBean.getCourseId())
				.setResultTransformer(Transformers.aliasToBean(CourseContentDTO.class)).list();
				
			    			
				
			} catch (Exception e) {
				
				throw e;
			} finally {
				
			}
			log.debug("checkCourseContent() --> End");
			return list;
		}


		public String updateCourseContent(TrainingMasterBean trainingMstBean) throws Exception
		{
			log.debug("updateCourseContent() --> Start");
			String message = null;
			Session session = null;
			
			String sb = null;
			try {
				session = hibernateUtils.getSession();
				
				
					sb = "update CourseContentDTO set courseContent=? ";
					sb += "where id="+trainingMstBean.getId();
				
				
					Query qry = session.createQuery(sb);
				
					qry.setString(0, trainingMstBean.getCourseContent());
					
				qry.executeUpdate();
				

				message = CPSConstants.UPDATE;
				log.debug("message -->" + message);
			} catch (Exception e) {
				message = CPSConstants.FAILED;
				throw e;
			} finally {
				
			}
			log.debug("updateCourseContent() --> End");
			return message;
		}
		public List getCirculationDetails(TrainingMasterBean trainingMstBean) throws Exception
		{
			log.debug("getCourseDesignationList() --> Start");
			
			Session session = null;
			Query qry = null;
			String sb = null;
			List list = null;
			try {
				session = hibernateUtils.getSession();
				
				
				sb = "select course.id as courseId,course.name as course,dur.startDate as startDate,dur.endDate as endDate,dur.id as id from CourseDTO course,CourseDurationsDTO dur where dur.id=? and course.id=dur.courseId and course.status=1 and dur.status=1";
				qry = session.createQuery(sb);
				qry.setString(0, trainingMstBean.getDurationId());
				list = qry.setResultTransformer(Transformers.aliasToBean(CourseDurationsDTO.class)).list();
				if(CPSUtils.checkList(list)){
					CourseDurationsDTO dto = (CourseDurationsDTO)list.get(0);
					dto.setTrainingCirculationDto(session.createCriteria(TrainingCirculationDTO.class).add(Expression.eq("durationId",Integer.parseInt(trainingMstBean.getDurationId()))).list());
					if(CPSUtils.checkList(dto.getTrainingCirculationDto())){
						TrainingCirculationDTO cir = (TrainingCirculationDTO)dto.getTrainingCirculationDto().get(0);
						cir.setFileBean((FilesBean)session.createSQLQuery("select filename as filename from files where id="+cir.getBrochure()).addScalar("filename", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(FilesBean.class)).uniqueResult());
						cir.setTrainingCirculationDepts(session.createQuery("select dept.id as id,dept.deptName as deptName from DepartmentsDTO dept,TrainingCirculationDeptDTO cir where dept.id=cir.departmentId and dept.status=1 and cir.circulationId=?").setInteger(0, cir.getId()).setResultTransformer(Transformers.aliasToBean(DepartmentsDTO.class)).list());
						if(!CPSUtils.isNullOrEmpty(cir.getIonId()))
						{	
						cir.setLetterFormatId(((IONDTO)(session.createCriteria(IONDTO.class).add(Expression.eq("id",cir.getIonId())).add(Expression.eq("status",1))).uniqueResult()).getLetterFormatId().toString());
						sb = "select i.id as id,i.letterFormatId as letterFormatId,i.ionDate as ionDate,i.letterNumber||'-('||i.ionDate||')' as letterNumber from IONDTO i,LetterNumberFormatDTO l where i.status=1 and l.status=1 and l.deptNum=67 and i.letterFormatId=l.id and l.id=? and (i.circulationStatus=0 or i.id=?)";
						List ionlist = session.createQuery(sb).setString(0, cir.getLetterFormatId()).setString(1, cir.getIonId().toString()).setResultTransformer(Transformers.aliasToBean(IONDTO.class)).list();
						cir.setIonList(ionlist);
						}
					}
				}
			    			
				
			} catch (Exception e) {
				
				throw e;
			} finally {
				
			}
			log.debug("getCourseDesignationList() --> End");
			return list;
		}
		public List getCirculationDetailsForION(TrainingMasterBean trainingMstBean) throws Exception
		{
			log.debug("getCourseDesignationList() --> Start");
			
			Session session = null;
			Query qry = null;
			String sb = null;
			List list = null;
			try {
				session = hibernateUtils.getSession();
				
				session.flush();
				sb = "SELECT TO_CHAR(to_date(Cir.Cir_date,'dd-Mon-YYYY'),'dd Month YYYY') Circulationdate,  Cir.Nom_end_date Nominationlastdate,  Course.Course_name Coursename,  Ini.Training_inistitute_name Traininginistitute,  Venue.Address Address,  Dur.Start_date Startdate,  Dur.End_date Enddate,  Ini.Training_institute_short_name Shortname,  City.name City,  ((To_date(Dur.End_date,'DD-MON-YYYY')-To_date(Dur.Start_date,'DD-MON-YYYY'))+1) Days,   (SELECT E.Sfid  Name  FROM emp_role_mapping Emp,    Org_role_instance Ori,    Departments_master D,    emp_master e  WHERE Ori.Is_head     =1  AND Ori.Department_id =D.Department_id  AND d.department_id   =67  AND emp.org_role_id   =ori.org_role_id  AND Ori.Status        =1  AND Emp.Status        =1  AND E.Sfid            = Emp.Sfid  And E.Status          = 1  ) AS empSfid,(SELECT ori.org_role_id as roleId FROM emp_role_mapping Emp,Org_role_instance Ori,Departments_master D WHERE Ori.Is_head     =1 AND Ori.Department_id =D.Department_id AND d.department_id   =67 AND emp.org_role_id   =ori.org_role_id And Ori.Status        =1 And Emp.Status        =1)as hrroleId,cir.brochure FROM Hrdg_training_cir_master Cir,  Hrdg_course_duration_master Dur,  Hrdg_training_course_details Course,  Hrdg_training_venue_details Venue,  Hrdg_training_inist_master Ini,  city_master city WHERE Cir.Course_duration_id=Dur.Course_duration_id AND Cir.Course_id           =Course.Training_course_id AND Course.Inistitute_id    =Ini.Training_inistitute_id AND Cir.Venue_id            =Venue.Training_venue_id And City.Id                 =Venue.City_id AND dur.course_duration_id  =?";
				qry = session.createSQLQuery(sb);
				qry.setString(0, trainingMstBean.getDurationId()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				list = qry.list();
				
			    			
				
			} catch (Exception e) {
				
				throw e;
			} finally {
				
			}
			log.debug("getCourseDesignationList() --> End");
			return list;
		}
		public List getTrainingVenueDetails(TrainingMasterBean trainingMstBean) throws Exception
		{
			Session session = null;
			List list = null;
			String sb = null;
			try
			{
				session = hibernateUtils.getSession();
				sb = "select venue.id as id,city.name as name from CityDTO city,CourseDurationsDTO dur,CourseDTO course,TrainingInistituteDTO ini,TrainingVenueDTO venue where dur.id=? and dur.courseId=course.id and course.trainingInistituteId=ini.id and ini.id=venue.trainingInistituteId and venue.cityId=city.id and course.status=1 and dur.status=1 and ini.status=1 and venue.status=1 and city.status=1";
				Query qr = session.createQuery(sb);
				qr=qr.setString(0, trainingMstBean.getDurationId());
				list=qr.setResultTransformer(Transformers.aliasToBean(CityDTO.class)).list();
						
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
		
		public List getDepartmentList() throws Exception
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
		public List getAttendedCourseList(TrainingMasterBean trainingMstBean)throws Exception
		{
			Session session = null;
			List list = null;
			String sb = null;
			try
			{
				session = hibernateUtils.getSession();
				sb = "select attended.id as id,attended.course as course,attended.courseYear as courseYear,attended.trainingTypeId as trainingTypeId,attended.status as status,attended.fee as fee,attended.serviceTax as serviceTax,attended.nomineeSfid as nomineeSfid,training.name as trainingType,to_char(yearDto.fromDate,'YYYY') ||'-'|| to_char(yearDto.toDate,'YYYY') as year,attended.nomineeSfid as nomineeSfid,attended.durationStartDate as durationStartDate,attended.durationEndDate as durationEndDate from CourseAttendedDetailsDTO attended,TrainingTypeDTO training,FinancialYearDTO yearDto where attended.trainingTypeId=? and attended.courseYear=? and attended.status=1 and training.id=attended.trainingTypeId and attended.courseYear=yearDto.id and training.status=1";
				//sb = "select attended.id as id,attended.course as course,attended.courseYear as courseYear,attended.trainingTypeId as trainingTypeId,attended.status as status,attended.fee as fee,attended.serviceTax as serviceTax,attended.nomineeSfid as nomineeSfid,training.name as trainingType,to_char(to_date(attended.durationStartDate,'DD-Mon-YYYY')) ||' to '|| to_char(to_date(attended.durationEndDate,'DD-Mon-YYYY')) as year,((To_date(attended.durationEndDate,'DD-MON-YYYY')-To_date(attended.durationStartDate,'DD-MON-YYYY'))+1) as duration,attended.nomineeSfid as nomineeSfid,attended.durationStartDate as durationStartDate,attended.durationEndDate as durationEndDate from CourseAttendedDetailsDTO attended,TrainingTypeDTO training,FinancialYearDTO yearDto where attended.trainingTypeId=? and attended.courseYear=? and attended.status=1 and training.id=attended.trainingTypeId and attended.courseYear=yearDto.id and training.status=1";
				list = session.createQuery(sb).setString(0, trainingMstBean.getTrainingTypeId()).setString(1, trainingMstBean.getCourseYear()). setResultTransformer(Transformers.aliasToBean(CourseAttendedDetailsDTO.class)).list();
				
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
		
		public String checkAttendedCoursedetails(TrainingMasterBean trainingMstBean)throws Exception
		{
			Session session = null; 
			List list = null;
			CourseAttendedDetailsDTO dto1 = null;
			String sb = null;
			String message = null;
			try
			{
				session = hibernateUtils.getSession();
				sb = "select id as id from CourseAttendedDetailsDTO where trainingTypeId=? and upper(course)=? and courseYear=? and nomineeSfid=? and status=1";
				list = session.createQuery(sb).setString(0, trainingMstBean.getTrainingTypeId()).setString(1, trainingMstBean.getCourse().toUpperCase()).setString(2, trainingMstBean.getCourseYear()).setString(3, trainingMstBean.getNomineeSfid()).setResultTransformer(Transformers.aliasToBean(CourseAttendedDetailsDTO.class)).list();
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
		


		public String updateCourseAttendeddetails(TrainingMasterBean trainingMstBean)throws Exception
		{
			log.debug("cancelNewRequest() --> Start");
			
			Session session = null;
			CourseAttendedDetailsDTO cadDto= null;
			String message = null;
			
			String sb = null;
			try {
				session = hibernateUtils.getSession();

				
				sb = "update CourseAttendedDetailsDTO set nomineeSfid=?,course=?,lastModifiedDate=sysdate,trainingTypeId=?,courseYear=?,lastModifiedBy=?,durationStartDate=?,durationEndDate=?,fee=?,serviceTax=?";
				
					
				sb += "where id="+trainingMstBean.getId();
			
			
				Query qry = session.createQuery(sb);
			
			
				qry.setString(0, trainingMstBean.getNomineeSfid());
				qry.setString(1, trainingMstBean.getCourse());
				qry.setString(2, trainingMstBean.getTrainingTypeId());
				qry.setString(3, trainingMstBean.getCourseYear());
				qry.setString(4, trainingMstBean.getSfid());
				qry.setString(5, trainingMstBean.getDurationStartDate());
				qry.setString(6, trainingMstBean.getDurationEndDate());
				qry.setString(7, trainingMstBean.getFee());
				qry.setString(8, trainingMstBean.getServiceTax());
				
			
			
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
		public String deleteCourseAttended(TrainingMasterBean trainingMstBean)throws Exception
		{
log.debug("cancelNewRequest() --> Start");
			
			Session session = null;
			CourseAttendedDetailsDTO cadDto= null;
			String message = null;
			
			String sb = null;
			try {
				session = hibernateUtils.getSession();

				
				sb = "update CourseAttendedDetailsDTO set status=0,lastModifiedDate=sysdate,lastModifiedBy=?";
				
					
				sb += "where id="+trainingMstBean.getId();
			
			
				Query qry = session.createQuery(sb);
			
			
				
				qry.setString(0, trainingMstBean.getSfid());
			
			
			qry.executeUpdate();
			message = CPSConstants.DELETE;
				
			} catch (Exception e) {
				message = CPSConstants.FAILED;
				
				throw e;
			} finally {
				
				
			}
			log.debug("cancelNewRequest() --> End");
			return message;
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
		public List getIONMstList(TrainingMasterBean trainingMstBean)throws Exception
		{
			Session session = null;
			List list = null;			
			String sb = null;
			
			try
			{
				session = hibernateUtils.getSession();
				sb = "select i.id as id,i.letterFormatId as letterFormatId,i.ionDate as ionDate,i.letterNumber||'-('||i.ionDate||')' as letterNumber from IONDTO i,LetterNumberFormatDTO l where i.status=1 and l.status=1 and i.letterFormatId=? and i.letterFormatId=l.id and (i.circulationStatus=0";
				if(!CPSUtils.isNullOrEmpty(trainingMstBean.getId()))
					sb += " or i.id in (select ionId from TrainingCirculationDTO where status=1 and id="+trainingMstBean.getId()+")";
				sb +=")";    
				list = session.createQuery(sb).setString(0, trainingMstBean.getLetterFormatId()).setResultTransformer(Transformers.aliasToBean(IONDTO.class)).list();
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
				sb = "select l.id as id,l.serialNum as serialNum,l.shortForm as shortForm from LetterNumberFormatDTO l where l.status=1 and l.deptNum=? and deptNum in (select deptNum from LetterNumberSeriesDTO where status=1 and deptNum=?) order by l.serialNum";
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
		

	 
}
