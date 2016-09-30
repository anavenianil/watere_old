package com.callippus.web.tada.dao.management;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.address.AddressBean;
import com.callippus.web.beans.dto.EmpPaymentsDTO;
import com.callippus.web.beans.dto.EmployeeClaimDetailsDTO;
import com.callippus.web.beans.dto.TadaProjectDirectorsDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.tada.beans.management.TadaManagementBean;
import com.callippus.web.tada.beans.request.TadaRequestBean;
import com.callippus.web.tada.dto.CityTypeDTO;
import com.callippus.web.tada.dto.DaOnTourDTO;
import com.callippus.web.tada.dto.LocalRMADTO;
import com.callippus.web.tada.dto.ProgramDTO;
import com.callippus.web.tada.dto.TaEntitleClassDTO;
import com.callippus.web.tada.dto.TaEntitleExemptionDTO;
import com.callippus.web.tada.dto.TaEntitleTypeDTO;
import com.callippus.web.tada.dto.TaMapDTO;
import com.callippus.web.tada.dto.TaTravelTypeMapDTO;
import com.callippus.web.tada.dto.TadaApprovalRequestDTO;
import com.callippus.web.tada.dto.TadaDaNewDetailsDTO;
import com.callippus.web.tada.dto.TadaDetailsDTO;
import com.callippus.web.tada.dto.TadaTdReqJourneyDTO;
import com.callippus.web.tada.dto.TravelTypeDTO;
@SuppressWarnings("unchecked")
@Service
 public class SQLTadaManagementDAO implements ITadaManagementDAO{
	
	@Autowired

	com.callippus.web.controller.common.HibernateUtils hibernateUtils;




	public String submitDaDetails(TadaDetailsDTO tadaDetailsDTO) throws Exception {
		Session session = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();
			session.saveOrUpdate(tadaDetailsDTO);
			//hibernateUtils.commitTransaction();
			session.flush();
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		} 
		return message;
	}
	public String checkDaDetails(TadaManagementBean tadaBean) throws Exception {
		Session session = null;
		try {
			Criteria crit=null;
			session = hibernateUtils.getSession();
			crit = session.createCriteria(TadaDetailsDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("cityTypeId", tadaBean.getCityClassId())).add(Expression.or(Expression.and(Expression.le("payRangeFrom", tadaBean.getPayRangeFrom()), Expression.ge("payRangeTo", tadaBean.getPayRangeTo())), Expression.between("payRangeFrom", tadaBean.getPayRangeFrom(), tadaBean.getPayRangeTo())));
			
			if (!CPSUtils.isNullOrEmpty(tadaBean.getNodeID())) {
				crit = crit.add(Expression.ne(CPSConstants.ID, tadaBean.getNodeID()));
			}

			
			if (CPSUtils.checkList(crit.list())) {
				tadaBean.setResult(CPSConstants.DUPLICATE);
			}
		} catch (Exception e) {
			throw e;
		}
		return tadaBean.getResult();
	}
	public List<TadaDetailsDTO> getDaDetails(TadaDetailsDTO tadaDetailsDTO)throws Exception {
		Session session=null;
		List<TadaDetailsDTO> daDetailsList=null;
		try{
			Criteria crit=null;
			session=hibernateUtils.getSession();
			crit=session.createCriteria(TadaDetailsDTO.class).add(Expression.eq(CPSConstants.STATUS,1)).addOrder(Order.asc("cityTypeId")).addOrder(Order.asc("payRangeFrom"));
			daDetailsList=crit.list();
		} catch(Exception e){
			throw e;
		}
		return daDetailsList;
	}
	public String deleteDaDetails(TadaManagementBean tadaBean)throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			TadaDetailsDTO tadaDetailsDTO = (TadaDetailsDTO) session.get(TadaDetailsDTO.class, Integer.valueOf(tadaBean.getId()));
			tadaDetailsDTO.setStatus(0);
			tadaDetailsDTO.setLastModifiedBy(tadaBean.getSfID());
			tadaDetailsDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			session.saveOrUpdate(tadaDetailsDTO);
			tadaBean.setResult(CPSConstants.DELETE);
		}
		catch(Exception e){
			throw e;
		}
		finally{
			hibernateUtils.closeSession();
		}
		return tadaBean.getResult();
	}
	@SuppressWarnings("unchecked")
	public List<EmpPaymentsDTO> getGradePayDetails(EmpPaymentsDTO empPaymentsDTO)throws Exception {
		Session session=null;
		List<EmpPaymentsDTO> gradePayList=null;
		try {
			Criteria crit=null;
			session=hibernateUtils.getSession();
			
			//crit = session.createCriteria(EmpPaymentsDTO.class);
			gradePayList= session.createSQLQuery("select distinct grade_pay as gradePay from emp_payment_details order by grade_pay desc").addScalar("gradePay",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(EmpPaymentsDTO.class)).list();
			
			//gradePayList=crit.list();
		} catch(Exception e){
			throw e;
		}
		return gradePayList;
		
	}
	public List<EmpPaymentsDTO> getUniqueGradePayDetails(EmpPaymentsDTO empPaymentsDTO)throws Exception {
		List<EmpPaymentsDTO> arrList = null;
		List<EmpPaymentsDTO> arrList1 = null;
		Set<EmpPaymentsDTO> arrSet = null;
		Iterator<EmpPaymentsDTO> itr = null;
		try {
			arrList = getGradePayDetails(empPaymentsDTO);
			arrSet = new TreeSet<EmpPaymentsDTO>(new Comparator<EmpPaymentsDTO>() {
				public int compare(EmpPaymentsDTO i1, EmpPaymentsDTO i2) {

					if (i1.getGradePay().equals(i2.getGradePay())) {
						return 0;
					} else {
						return 1;
					}
				}
			});
			for (int i = 0; i < arrList.size(); i++) {
				EmpPaymentsDTO keyDTO = arrList.get(i);
				arrSet.add(keyDTO);
			}
			itr = arrSet.iterator();
			arrList = new ArrayList<EmpPaymentsDTO>();
			while (itr.hasNext()) {
				arrList.add(itr.next());
			}

		} catch (Exception e) {
			throw e;
		}
		return arrList;
		
	}
	
	public String checkEntitleType(TadaManagementBean tadaBean) throws Exception {
		Session session = null;
		try {
			Criteria crit=null;
			session = hibernateUtils.getSession();
			crit = session.createCriteria(TaEntitleTypeDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("entitleTypeId",tadaBean.getEntitleTypeId())).add(
    					Expression.ilike("entitleClass", tadaBean.getEntitleClass().trim()));
			
			if (!CPSUtils.isNullOrEmpty(tadaBean.getNodeID())) {
				crit = crit.add(Expression.ne(CPSConstants.ID, tadaBean.getNodeID()));
			}

			
			if (CPSUtils.checkList(crit.list())) {
				tadaBean.setResult(CPSConstants.DUPLICATE);
			}
		} catch (Exception e) {
			throw e;
		}
		return tadaBean.getResult();
	}
	public String submitEntitleType(TaEntitleTypeDTO taEntitleTypeDTO)throws Exception {
		    Session session = null;
			String message = null;
			try {
				session = hibernateUtils.getSession();
				session.saveOrUpdate(taEntitleTypeDTO);
				session.flush();
				//hibernateUtils.commitTransaction();
				message = CPSConstants.SUCCESS;
			} catch (Exception e) {
				throw e;
			}
			return message;
	}
	@SuppressWarnings("unchecked")
	public List<TaEntitleTypeDTO> getEntitleType(TaEntitleTypeDTO taEntitleTypeDTO)throws Exception {
		Session session=null;
		List<TaEntitleTypeDTO> entitleTypeList=null;
		try{
			Criteria crit=null;
			session=hibernateUtils.getSession();
			crit=session.createCriteria(TaEntitleTypeDTO.class).add(Expression.eq(CPSConstants.STATUS,1)).addOrder(Order.asc("entitleTypeId"));
			entitleTypeList=crit.list();
		} catch(Exception e){
			throw e;
		}
		return entitleTypeList;
	}
	@SuppressWarnings("unchecked")
	public List<TaEntitleTypeDTO> getEntitleClass(TadaManagementBean tadaBean)throws Exception {
		Session session=null;
		List<TaEntitleTypeDTO> entitleTypeList=null;
		try{
			Criteria crit=null;
			session=hibernateUtils.getSession();
			crit=session.createCriteria(TaEntitleTypeDTO.class).add(Expression.eq("entitleTypeId", tadaBean.getEntitleTypeId())).add(Expression.eq(CPSConstants.STATUS,1)).addOrder(Order.asc("entitleTypeId"));
			entitleTypeList=crit.list();
		} catch(Exception e){
			throw e;
		}
		return entitleTypeList;
	}
	public String deleteEntitleType(TadaManagementBean tadaBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			TaEntitleTypeDTO taEntitleTypeDTO = (TaEntitleTypeDTO) session.get(TaEntitleTypeDTO.class, Integer.valueOf(tadaBean.getId()));
			taEntitleTypeDTO.setStatus(0);
			taEntitleTypeDTO.setLastModifiedBy(tadaBean.getSfID());
			taEntitleTypeDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			session.saveOrUpdate(taEntitleTypeDTO);
			tadaBean.setResult(CPSConstants.DELETE);
		}
		catch(Exception e){
			throw e;
		}
		finally{
			hibernateUtils.closeSession();
		}
		return tadaBean.getResult();
	}
	public String checkCityType(TadaManagementBean tadaBean) throws Exception {
		Session session = null;
		try {
			Criteria crit=null;
			session = hibernateUtils.getSession();
			crit = session.createCriteria(CityTypeDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.ilike("cityName", tadaBean.getCityName().trim()));
			
			if (!CPSUtils.isNullOrEmpty(tadaBean.getNodeID())) {
				crit = crit.add(Expression.ne(CPSConstants.ID, tadaBean.getNodeID()));
			}

			
			if (CPSUtils.checkList(crit.list())) {
				tadaBean.setResult(CPSConstants.DUPLICATE);
			}
		} catch (Exception e) {
			throw e;
		}
		return tadaBean.getResult();
	}
	public String submitCityType(CityTypeDTO cityTypeDTO) throws Exception {
		Session session = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();
			session.saveOrUpdate(cityTypeDTO);
			session.flush();
			//hibernateUtils.commitTransaction();
			message = CPSConstants.SUCCESS;
			
			
				
		} catch (Exception e) {
			throw e;
		}
		return message;
	}
	@SuppressWarnings("unchecked")
	public List<CityTypeDTO> getCityTypeList(CityTypeDTO cityTypeDTO)throws Exception {
		Session session=null;
		List<CityTypeDTO> cityTypeList=null;
		try{
			Criteria crit=null;
			session=hibernateUtils.getSession();
			cityTypeList = session.createCriteria(CityTypeDTO.class).addOrder(Order.asc("id")).add(Expression.eq("status", 1)).list();
		} catch(Exception e){
			throw e;
		}
		return cityTypeList;
	}
	@SuppressWarnings("unchecked")
	public List<TadaManagementBean> cityTypeList(TadaManagementBean tadaBean)throws Exception {
		Session session=null;
		List<TadaManagementBean> cityTypeList=null;
		try{
			Criteria crit=null;
			session=hibernateUtils.getSession();
			cityTypeList = session.createCriteria(CityTypeDTO.class).addOrder(Order.asc("cityClass")).add(Expression.eq("status", 1)).add(Expression.eq("cityClass", tadaBean.getCityClass())).list();
		} catch(Exception e){
			throw e;
		}
		return cityTypeList;
	}
	public String deleteCityType(TadaManagementBean tadaBean)throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			CityTypeDTO cityTypeDTO = (CityTypeDTO) session.get(CityTypeDTO.class, Integer.valueOf(tadaBean.getId()));
			cityTypeDTO.setStatus(0);
			cityTypeDTO.setLastModifiedBy(tadaBean.getSfID());
			cityTypeDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			session.saveOrUpdate(cityTypeDTO);
			tadaBean.setResult(CPSConstants.DELETE);
		}
		catch(Exception e){
			throw e;
		}
		finally{
			hibernateUtils.closeSession();
		}
		return tadaBean.getResult();
	}
	public List<CityTypeDTO> getUniqueCityTypeList(CityTypeDTO cityTypeDTO)throws Exception {
		List<CityTypeDTO> arrList = null;
		Set<CityTypeDTO> arrSet = null;
		Iterator<CityTypeDTO> itr = null;
		try {
			arrList = getCityTypeList(cityTypeDTO);
			arrSet = new TreeSet<CityTypeDTO>(new Comparator<CityTypeDTO>() {
				public int compare(CityTypeDTO i1, CityTypeDTO i2) {

					if (i1.getCityClass().equals(i2.getCityClass())) {
						return 0;
					} else {
						return 1;
					}
				}
			});
			for (int i = 0; i < arrList.size(); i++) {
				CityTypeDTO keyDTO = arrList.get(i);
				arrSet.add(keyDTO);
			}
			itr = arrSet.iterator();
			arrList = new ArrayList<CityTypeDTO>();
			while (itr.hasNext()) {
				arrList.add(itr.next());
			}

		} catch (Exception e) {
			throw e;
		}
		return arrList;
		
	}
	public String checkTravelType(TadaManagementBean tadaBean) throws Exception {
		Session session = null;
		try {
			Criteria crit=null;
			session = hibernateUtils.getSession();
			crit = session.createCriteria(TravelTypeDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.ilike("travelType",tadaBean.getTravelType().trim()));
			
			if (!CPSUtils.isNullOrEmpty(tadaBean.getNodeID())) {
				crit = crit.add(Expression.ne(CPSConstants.ID, tadaBean.getNodeID()));
			}

			
			if (CPSUtils.checkList(crit.list())) {
				tadaBean.setResult(CPSConstants.DUPLICATE);
			}
		} catch (Exception e) {
			throw e;
		}
		return tadaBean.getResult();
	}
	public String submitTravelType(TravelTypeDTO travelTypeDTO) throws Exception {
		Session session = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();
			session.saveOrUpdate(travelTypeDTO);
			session.flush();
			//hibernateUtils.commitTransaction();
			message = CPSConstants.SUCCESS;
			
			
				
		} catch (Exception e) {
			throw e;
		}
		return message;
	}
	public List<TravelTypeDTO> getTravelTypeList(TravelTypeDTO travelTypeDTO)throws Exception {
		Session session=null;
		List<TravelTypeDTO> travelTypeList=null;
		EmpPaymentsDTO empPaymentsDTO=new EmpPaymentsDTO();
		try{
			Criteria crit=null;
			session=hibernateUtils.getSession();
			crit=session.createCriteria(TravelTypeDTO.class).add(Expression.eq(CPSConstants.STATUS,1)).addOrder(Order.asc("id"));
			travelTypeList=crit.list();
			
		} catch(Exception e){
			throw e;
		}
		return travelTypeList;
	}
	public String deleteTravelType(TadaManagementBean tadaBean)throws Exception {
		Session session = null;
		Transaction tx=null;
		List<TaTravelTypeMapDTO> travelTypeMapList=null;
		try {

			//session = hibernateUtils.getSession();
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx=session.beginTransaction();

			session = hibernateUtils.getSession();
			//session = sessionFactory.openSession();
			//tx=session.beginTransaction();

			TravelTypeDTO travelTypeDTO = (TravelTypeDTO) session.get(TravelTypeDTO.class, Integer.valueOf(tadaBean.getId()));
			if(!CPSUtils.isNullOrEmpty(travelTypeDTO)){
				travelTypeMapList=session.createCriteria(TaTravelTypeMapDTO.class).add(Expression.eq("travelTypeId", travelTypeDTO.getTravelTypeId())).list();
				for (TaTravelTypeMapDTO taTravelTypeMapDTO : travelTypeMapList) {
					taTravelTypeMapDTO.setStatus(0);
				}
			}
			travelTypeDTO.setStatus(0);
			travelTypeDTO.setLastModifiedBy(tadaBean.getSfID());
			travelTypeDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			session.saveOrUpdate(travelTypeDTO);
			session.createSQLQuery("delete from TADA_TRAVEL_TYPE_MAP where TRAVEL_TYPE_ID="+tadaBean.getId()+"").executeUpdate();
			session.createSQLQuery("delete from TADA_TA_ENTITLECLASS where ENTITLE_TYPE_ID="+tadaBean.getId()+"").executeUpdate();
			session.createSQLQuery("delete from TADA_TA_ENTITLETYPE_MASTER where ENTITLE_TYPE_ID="+tadaBean.getId()+"").executeUpdate();
			tadaBean.setResult(CPSConstants.DELETE);


			//tx.commit();

		}
		catch(Exception e){
			//tx.rollback();
			throw e;
		} finally {

			//session.close();

			session.flush();

		}
		return tadaBean.getResult();
	}
	
	public String submitTravelTypeMap(TaTravelTypeMapDTO taTravelTypeMapDTO)throws Exception{
		Session session = null;
		String message = null;
		Transaction tx=null;
		int id=0;
		try {
			session=hibernateUtils.getSession();

			//tx=session.beginTransaction();
			//TaTravelTypeMapDTO tempDTO = (TaTravelTypeMapDTO) session.createCriteria(TaTravelTypeMapDTO.class).add(Expression.eq("key",taTravelTypeMapDTO.getKey())).add(Expression.eq("value",taTravelTypeMapDTO.getValue())).add(Expression.eq("gradePay",taTravelTypeMapDTO.getGradePay())).uniqueResult();

			//tx=session.beginTransaction();
			//TaTravelTypeMapDTO tempDTO = (TaTravelTypeMapDTO) session.createCriteria(TaTravelTypeMapDTO.class).add(Expression.eq("key",taTravelTypeMapDTO.getKey())).add(Expression.eq("value",taTravelTypeMapDTO.getValue())).add(Expression.eq("gradePay",taTravelTypeMapDTO.getGradePay())).uniqueResult();
			TaTravelTypeMapDTO tempDTO = (TaTravelTypeMapDTO) session.createCriteria(TaTravelTypeMapDTO.class).add(Expression.eq("travelTypeId",taTravelTypeMapDTO.getTravelTypeId())).add(Expression.eq("gradePay",taTravelTypeMapDTO.getGradePay())).uniqueResult();

			if (!CPSUtils.isNullOrEmpty(tempDTO)) {
				id=tempDTO.getId();
				BeanUtils.copyProperties(taTravelTypeMapDTO, tempDTO);
				tempDTO.setId(id);
				session.update(tempDTO);
				session.flush();
				
			} else {
				session.save(taTravelTypeMapDTO);
				session.flush();
			}
			
			message = CPSConstants.SUCCESS;
			
		} catch (Exception e) {
			//tx.rollback();
			e.printStackTrace();
			throw e;
		}finally{
			}
		return message;
	}
	@SuppressWarnings("unchecked")
	public List<TaMapDTO> getTravelTypeMapList()throws Exception{
		Session session=null;
		List<TaMapDTO> keyValueDTOList = null;
		
		try{
			session = hibernateUtils.getSession();
			keyValueDTOList = session.createSQLQuery("select key as key,value as value,grade_pay as id,travel_type_id as travelTypeId from TADA_TRAVEL_TYPE_MAP where STATUS=1 order by grade_pay ").addScalar("value", Hibernate.STRING)
					.addScalar("key", Hibernate.STRING).addScalar("id", Hibernate.INTEGER).addScalar("travelTypeId", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(TaMapDTO.class)).list();
			
		} catch (Exception e) {
			throw e;
		}
		return keyValueDTOList;
	}
	@SuppressWarnings("unchecked")
	public List<TaMapDTO> getTravelTypeUnMapList()throws Exception{
		Session session=null;
		List<TaMapDTO> keyValueDTOList = null;
		
		try{
			session = hibernateUtils.getSession();
			keyValueDTOList = session.createSQLQuery("select key as key,value as value,grade_pay as id,travel_type_id as travelTypeId from TADA_TRAVEL_TYPE_MAP where STATUS=0 order by grade_pay ").addScalar("value", Hibernate.STRING)
					.addScalar("key", Hibernate.STRING).addScalar("id", Hibernate.INTEGER).addScalar("travelTypeId", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(TaMapDTO.class)).list();
			
		} catch (Exception e) {
			throw e;
		}
		return keyValueDTOList;
	}
	@SuppressWarnings("unchecked")
	public String submitEntitlementClass(List<TaEntitleClassDTO> taEntitleClassDTOList)throws Exception {
		Session session = null;
		String message = null;
		Iterator itr=null;
		TaEntitleClassDTO taEntitleClassDTO=null;
		TaEntitleClassDTO tempDTO=null;
		Transaction tx = null;
		try {

			session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
			//tx=session.beginTransaction();

			/*session=sessionFactory.openSession();
			tx=session.beginTransaction();*/
			session = hibernateUtils.getSession();

			itr=taEntitleClassDTOList.iterator();
			while(itr.hasNext()){
				taEntitleClassDTO=(TaEntitleClassDTO)itr.next();
				tempDTO=(TaEntitleClassDTO)session.createCriteria(TaEntitleClassDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("gradePay",taEntitleClassDTO.getGradePay())).add(Expression.eq("entitleClassId",taEntitleClassDTO.getEntitleClassId())).uniqueResult();
				if(!CPSUtils.isNullOrEmpty(tempDTO)){
					int id=tempDTO.getId();
					BeanUtils.copyProperties(taEntitleClassDTO, tempDTO);
					tempDTO.setId(id);
					session.update(tempDTO);
					session.flush();
				}
				else{
					session.save(taEntitleClassDTO);
					session.flush();
				}
			}
			
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}
	@SuppressWarnings("unchecked")
	public String submitNonEntitlementClass(List<TaEntitleClassDTO> taEntitleClassDTOList)throws Exception {
		Session session = null;
		String message = null;
		Iterator itr=null;;
		TaEntitleClassDTO tempDTO=null;
		Transaction tx = null;
		try {

			//session = hibernateUtils.getSession();
			session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
			//tx=session.beginTransaction();
			//session = hibernateUtils.getSession(); //session=sessionFactory.openSession();

			session = hibernateUtils.getSession();
			//session=sessionFactory.openSession();

			//tx=session.beginTransaction();
			//session=sessionFactory.openSession();
			itr=taEntitleClassDTOList.iterator();
			while(itr.hasNext()){
				TaEntitleClassDTO taEntitleClassDTO=(TaEntitleClassDTO)itr.next();
				tempDTO=(TaEntitleClassDTO)session.createCriteria(TaEntitleClassDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("gradePay",taEntitleClassDTO.getGradePay())).add(Expression.eq("entitleClassId",taEntitleClassDTO.getEntitleClassId())).uniqueResult();
				if(!CPSUtils.isNullOrEmpty(tempDTO)){
					int id=tempDTO.getId();
					BeanUtils.copyProperties(taEntitleClassDTO, tempDTO);
					tempDTO.setId(id);
					tempDTO.setStatus(0);
					session.saveOrUpdate(tempDTO);
					session.flush();
				}
			}
			//hibernateUtils.commitTransaction();

			session.flush();//tx.commit() ;

			//tx.commit();

			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			//tx.rollback();
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}
	public List<TaEntitleClassDTO> getEntitlementClassList(TaEntitleClassDTO taEntitleClassDTO)throws Exception{
		Session session=null;
		List<TaEntitleClassDTO> entitlementClassList=null;
		try{
			Criteria crit=null;
			session=hibernateUtils.getSession();
			crit=session.createCriteria(TaEntitleClassDTO.class).add(Expression.eq(CPSConstants.STATUS,1)).add(Expression.eq("gradePay",taEntitleClassDTO.getGradePay()));
			entitlementClassList=crit.list();
		} catch(Exception e){
			throw e;
		}
		return entitlementClassList;
	}
	@SuppressWarnings("unchecked")
	public List<TaEntitleClassDTO> getEntitlementList(TaEntitleClassDTO taEntitleClassDTO)throws Exception{
		Session session=null;
		List<TaEntitleClassDTO> entitlementClassList=null;
		String sql=null;
		try{
			session=hibernateUtils.getSession();
			sql="select unique grade_pay as gradePay,entitle_type_id as entitleTypeId from TADA_TA_ENTITLECLASS where status=1 order by grade_pay desc";
			entitlementClassList=session.createSQLQuery(sql).addScalar("gradePay", Hibernate.INTEGER).addScalar("entitleTypeId", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(TaEntitleClassDTO.class)).list();
		} catch(Exception e){
			throw e;
		}
		return entitlementClassList;
	}
	@SuppressWarnings("unchecked")
	public List<TaEntitleClassDTO> getEntitlementClassList()throws Exception{
		Session session=null;
		List<TaEntitleClassDTO> entitlementClassList=null;
		try{
			session=hibernateUtils.getSession();
			entitlementClassList=session.createCriteria(TaEntitleClassDTO.class).add(Expression.eq(CPSConstants.STATUS,1)).list();
		} catch(Exception e){
			throw e;
		}
		return entitlementClassList;
	}
	@SuppressWarnings("unchecked")
	public List<TaEntitleClassDTO> getEntitlementClassLists(TaEntitleClassDTO taEntitleClassDTO)throws Exception{
		Session session=null;
		Session session1=null;
		List<TaEntitleClassDTO> entitlementClassList=null;
		List<TaEntitleTypeDTO> entitlementTypeLists=null;
		TaEntitleTypeDTO taEntitleTypeDTO=new TaEntitleTypeDTO();
		try{
			Criteria crit=null;
			Criteria crit1=null;
			session=hibernateUtils.getSession();
			int gp=taEntitleClassDTO.getGradePay();
			entitlementClassList=session.createSQLQuery("select t1.grade_pay,t2.entitle_type,t2.entitle_class from tada_ta_entitleclass t1,tada_ta_entitletype_master t2 where t1.entitle_class_id=t2.id and grade_pay=" + gp + "").list();
			//entitlementClassList=crit.list();
			//loanBean.setFinancialYearList(session.createSQLQuery("select fym.id,to_char(from_date,'YYYY') ||'-'|| to_char(to_date,'YYYY') financialYear from financial_year_master fym where status=1 order by financialYear")
			//		.addScalar("id", Hibernate.INTEGER).addScalar("financialYear", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(FinancialYearDTO.class)).list());
			
			
		} catch(Exception e){
			throw e;
		}
		
//		itr = arrSet.iterator();
//		arrList = new ArrayList<CityTypeDTO>();
//		while (itr.hasNext()) {
//			arrList.add(itr.next());
//		}
		return entitlementClassList;
	}
	public String checkDaNewDetails(TadaManagementBean tadaBean) throws Exception {
		Session session = null;
		try {
			Criteria crit=null;
			session = hibernateUtils.getSession();
			crit = session.createCriteria(TadaDaNewDetailsDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("gradePay",Integer.parseInt(tadaBean.getGradePay())));
			
			if (!CPSUtils.isNullOrEmpty(tadaBean.getNodeID())) {
				crit = crit.add(Expression.ne(CPSConstants.ID, tadaBean.getNodeID()));
			}

			TadaDaNewDetailsDTO tadaDaNewDetails=null;
			if (CPSUtils.checkList(crit.list())) {
				String sql="UPDATE tada_da_new_details_master SET  status =0,last_modified_date=?, last_modified_by=?WHERE id =?";
				List<TadaDaNewDetailsDTO> list=crit.list();
				Iterator it = list.iterator();
				for(int i=1;i<=list.size();i++){
					if (it.hasNext()) {
						tadaDaNewDetails = (TadaDaNewDetailsDTO) it.next();
//						tadaDaNewDetails.getId();
//						tadaDaNewDetails.setStatus(0);
//						session.saveOrUpdate(tadaDaNewDetails);
						
						session.createSQLQuery(sql).setString(0, CPSUtils.getCurrentDate()).setString(1,tadaBean.getSfID()).setInteger(2,tadaDaNewDetails.getId()).executeUpdate();
						//System.out.println("gouri"+tadaDaNewDetails.getId());
					}
				}
				
//			TadaDaNewDetailsDTO tadaDaNewDetails=(TadaDaNewDetailsDTO)crit.setResultTransformer(Transformers.aliasToBean(TadaDaNewDetailsDTO.class)).uniqueResult();             //These 3 lines are new da details
//				tadaDaNewDetails.setStatus(0);
//				System.out.println("gouri"+tadaDaNewDetails.getId());
//				session.saveOrUpdate(tadaDaNewDetails);
               	tadaBean.setResult(CPSConstants.DUPLICATE);
			}
		} catch (Exception e) {
			throw e;
		}
		return tadaBean.getResult();
	}
	public String submitDaNewDetails(TadaDaNewDetailsDTO tadaDaNewDetailsDTO)throws Exception {
		Session session = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();
			session.saveOrUpdate(tadaDaNewDetailsDTO);
			session.flush();
			//hibernateUtils.commitTransaction();
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		} 
		return message;
	}
	public List<TadaDaNewDetailsDTO> getDaNewDetails(TadaDaNewDetailsDTO tadaDaNewDetailsDTO)throws Exception {
		Session session=null;
		List<TadaDaNewDetailsDTO> daNewDetailsList=null;
		try{
			Criteria crit=null;
			session=hibernateUtils.getSession();
			crit=session.createCriteria(TadaDaNewDetailsDTO.class).add(Expression.eq(CPSConstants.STATUS,1)).addOrder(Order.desc("gradePay"));
			daNewDetailsList=crit.list();
		} catch(Exception e){
			throw e;
		}
		return daNewDetailsList;
	}
	public String deleteDaNewDetails(TadaManagementBean tadaBean)throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			TadaDaNewDetailsDTO tadaDaNewDetailsDTO = (TadaDaNewDetailsDTO) session.get(TadaDaNewDetailsDTO.class, Integer.valueOf(tadaBean.getId()));
			tadaDaNewDetailsDTO.setStatus(0);
			tadaDaNewDetailsDTO.setLastModifiedBy(tadaBean.getSfID());
			tadaDaNewDetailsDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			session.saveOrUpdate(tadaDaNewDetailsDTO);
			tadaBean.setResult(CPSConstants.DELETE);
		}
		catch(Exception e){
			throw e;
		}
		finally{
			hibernateUtils.closeSession();
		}
		return tadaBean.getResult();
	}
	@SuppressWarnings("unchecked")
	public String checkLocalRMADetails(TadaManagementBean tadaBean)throws Exception{
		Session session=null;
		List<LocalRMADTO> list=null;
		try{
			session=hibernateUtils.getSession();
			list = session.createCriteria(LocalRMADTO.class).add(Expression.eq("fromPlace", tadaBean.getFromPlace().trim())).add(Expression.eq("toPlace", tadaBean.getToPlace().trim())).add(Expression.eq("status", 1)).list();
			if(CPSUtils.checkList(list)){
				tadaBean.setResult(CPSConstants.DUPLICATE);
			}
		} catch (Exception e) {
			throw e;
		}
		return tadaBean.getResult();
	}
	public String submitLocalRMADetails(LocalRMADTO localRMADTO)throws Exception{
		Session session=null;
		String message=null;
		try{
			session = hibernateUtils.getSession();
			session.saveOrUpdate(localRMADTO);
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return message;
	}
	public String checkDaOnTourDetails(TadaManagementBean tadaBean)throws Exception{                        //start submit  submitDaOnTourDetails for DaOnTour master
		Session session=null;
		List<DaOnTourDTO> list=null;
		try{
			session=hibernateUtils.getSession();
			list = session.createCriteria(DaOnTourDTO.class).add(Expression.between("daRangeFrom", tadaBean.getDaRangeFrom(), tadaBean.getDaRangeTo())).add(Expression.between("daRangeTo",  tadaBean.getDaRangeFrom(), tadaBean.getDaRangeTo())).add(Expression.eq("status", 1)).list();
			if(CPSUtils.checkList(list)){
			/*	String sql="UPDATE tada_da_on_tour_master SET  status =0, last_modified_date=?,last_modified_by=?  WHERE id =?";
//				List<DaOnTourDTO> list=crit.list();
				Iterator it = list.iterator();
				DaOnTourDTO daOnTourDetails=null;
				for(int i=1;i<=list.size();i++){
					if (it.hasNext()) {
						daOnTourDetails = (DaOnTourDTO) it.next();
						session.createSQLQuery(sql).setString(0, CPSUtils.getCurrentDate()).setString(1,tadaBean.getSfID()).setInteger(2,daOnTourDetails.getId()).executeUpdate();
						
					}
				}*/
				tadaBean.setResult(CPSConstants.DUPLICATE);
			}
			session.flush();
		} catch (Exception e) {
			throw e;
		}
		return tadaBean.getResult();
		
	}
	
	public String submitDaOnTourDetails(DaOnTourDTO daOnTourDTO)throws Exception{                                     //start submit  submitDaOnTourDetails for DaOnTour master
		Session session=null;
		String message=null;
		try{
			DaOnTourDTO daOnTourDTO2=null;
			session = hibernateUtils.getSession();
			if(!CPSUtils.isNullOrEmpty(daOnTourDTO.getId())){
				daOnTourDTO2=(DaOnTourDTO)session.load(DaOnTourDTO.class,daOnTourDTO.getId());
				daOnTourDTO2.setDaRangeFrom(daOnTourDTO.getDaRangeFrom());
				daOnTourDTO2.setDaRangeTo(daOnTourDTO.getDaRangeTo());
				daOnTourDTO2.setDaOnTour(daOnTourDTO.getDaOnTour());
				daOnTourDTO2.setLastModifiedBy(daOnTourDTO.getLastModifiedBy());
             	daOnTourDTO2.setCreationDate(CPSUtils.formattedDate(daOnTourDTO2.getCreationDate()));
				daOnTourDTO2.setLastModifiedDate(CPSUtils.getCurrentDate());
				session.saveOrUpdate(daOnTourDTO2);
			}else{
				session.saveOrUpdate(daOnTourDTO);
				message = CPSConstants.SUCCESS;
			}
			
		} catch (Exception e) {
			throw e;
		}
		return message;
	}//end submitDaOnTourDetails for DaOnTour master
	@SuppressWarnings("unchecked")
	public List<LocalRMADTO> getLocalRMAList(LocalRMADTO localRMADTO)throws Exception{
		Session session=null;
		List<LocalRMADTO> localRMAList=null;
		try{
			session = hibernateUtils.getSession();
			localRMAList = session.createCriteria(LocalRMADTO.class).add(Expression.eq("status", 1)).addOrder(Order.desc("fromPlace")).list();
		} catch(Exception e){
			throw e;
		}
		return localRMAList;
	}
	//start of get
	public List<DaOnTourDTO> getTourDaDetails(DaOnTourDTO daOnTourDTO)throws Exception{
		Session session=null;
		List<DaOnTourDTO> daOnTourList=null;
		try{
			session = hibernateUtils.getSession();
			daOnTourList = session.createCriteria(DaOnTourDTO.class).add(Expression.eq("status", 1)).list();
		
		} catch(Exception e){
			throw e;
		}
		return daOnTourList ;
	}
	//end
	public String deleteLocalRMA(TadaManagementBean tadaBean)throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			LocalRMADTO localRMADTO = (LocalRMADTO) session.get(LocalRMADTO.class, Integer.valueOf(tadaBean.getId()));
			localRMADTO.setStatus(0);
			localRMADTO.setLastModifiedBy(tadaBean.getSfID());
			localRMADTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			session.saveOrUpdate(localRMADTO);
			tadaBean.setResult(CPSConstants.DELETE);
		}
		catch(Exception e){
			throw e;
		}finally{
			session.flush();
		}
		return tadaBean.getResult();
	}
	
	public String deleteDaOnTour(TadaManagementBean tadaBean)throws Exception {                //This function for delete DaOnTourDetails 
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			DaOnTourDTO daOnTourDTO = (DaOnTourDTO) session.get(DaOnTourDTO.class, Integer.valueOf(tadaBean.getId()));
			daOnTourDTO.setStatus(0);
			daOnTourDTO.setLastModifiedBy(tadaBean.getSfID());
			daOnTourDTO.setCreationDate(CPSUtils.formattedDate(daOnTourDTO.getCreationDate()));
			daOnTourDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			session.saveOrUpdate(daOnTourDTO);
			tadaBean.setResult(CPSConstants.DELETE);
		}
		catch(Exception e){
			throw e;
		}
		return tadaBean.getResult();
	}
//tada project director
	public String submitTadaProjectDetails(TadaProjectDirectorsDTO tadaProjectDirectorsDTO) throws Exception{
		Session session = null;
		String message ="";
		Transaction tx=null;
		try{

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx=session.beginTransaction();

			/*session = sessionFactory.openSession();
			tx=session.beginTransaction();*/
			session = hibernateUtils.getSession();

			session.saveOrUpdate(tadaProjectDirectorsDTO);

			session.flush();//tx.commit() ;

			//tx.commit();
			session.flush();

			message=CPSConstants.SUCCESS;
		}catch (Exception e){
			//tx.rollback();
			throw e;
		}finally{
			//session.close();
		}
		return message;
	}
	public List<TadaProjectDirectorsDTO> getTadaProjectDirectorList()throws Exception{
		Session session =null;
		List<TadaProjectDirectorsDTO> tadaProjectDirector = null;
		try{

			session = hibernateUtils.getSession(); //session=sessionFactory.openSession();

			//session=sessionFactory.openSession();
			session = hibernateUtils.getSession();

			tadaProjectDirector=session.createCriteria(TadaProjectDirectorsDTO.class).add(Expression.eq("status", 1)).list();
			for (TadaProjectDirectorsDTO tadaProjectDirectorsDTO : tadaProjectDirector) {
				ProgramDTO programDTO=(ProgramDTO)session.createCriteria(ProgramDTO.class).add(Expression.eq("programCode", tadaProjectDirectorsDTO.getProgramCode())).uniqueResult();
				tadaProjectDirectorsDTO.setProgramName(programDTO.getProgramName());
			}
		}catch(Exception e){
		throw e;
		}finally{

			//session.close();

			session.flush();
			//session.close();

		}
		return tadaProjectDirector;
	}
	public String deleteTadaProjectDetails(int id)throws Exception{
		Session session = null;
		Transaction transaction = null;
		String message="";
		try{

			session = hibernateUtils.getSession(); //session = sessionFactory.openSession();

			//session =sessionFactory.openSession();
			session = hibernateUtils.getSession();

			TadaProjectDirectorsDTO  tadaProjectDirectorsDTO =(TadaProjectDirectorsDTO)session.get(TadaProjectDirectorsDTO.class,id);
			//transaction=session.beginTransaction();
			if(tadaProjectDirectorsDTO!=null){
				tadaProjectDirectorsDTO.setStatus(0);
				session.update(tadaProjectDirectorsDTO);
				session.flush();
				message=CPSConstants.DELETE;
			}else{
				message=CPSConstants.FAILED;
			}

			session.flush() ; //transaction.commit();

			//transaction.commit();

		}catch (Exception e){
			//transaction.rollback();
			throw e;
		}finally{
			//session.close();
		}
		return message;
	}
	public String checkTadaProjectDetails(TadaManagementBean tadaBean) throws Exception{
		Session session = null;
		try{
			Criteria crit = null;
			session = hibernateUtils.getSession();
			if(CPSUtils.isNullOrEmpty(tadaBean.getPk())){
				crit= session.createCriteria(TadaProjectDirectorsDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("projectName",tadaBean.getProjectName()));
				
				if(CPSUtils.checkList(crit.list())){
					tadaBean.setResult(CPSConstants.DUPLICATE);
				}
			}
			
		}catch (Exception e){
			throw e;
		}
		return tadaBean.getResult();
	}
	public List<TadaManagementBean> getSfIDList()throws Exception{
		List<TadaManagementBean> list=new ArrayList<TadaManagementBean>();
		List<EmployeeBean> empList=null;
		Session session=null;
		try {
			session=hibernateUtils.getSession();
			empList = session.createCriteria(EmployeeBean.class).add(Expression.eq("status", 1)).addOrder(Order.asc("userSfid")).list();
			for (EmployeeBean employeeBean : empList) {
				TadaManagementBean tadaBean=new TadaManagementBean();
				tadaBean.setSfID(employeeBean.getUserSfid());
				list.add(tadaBean);
			}
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	public List<TadaManagementBean> getProjectsList()throws Exception{
		List<TadaManagementBean> list=new ArrayList<TadaManagementBean>();
		List<TadaProjectDirectorsDTO> projList=null;
		Session session=null;
		try {
			session=hibernateUtils.getSession();
			projList = session.createCriteria(TadaProjectDirectorsDTO.class).add(Expression.eq("status", 1)).list();
			for (TadaProjectDirectorsDTO tadaProjectDirectorsDTO : projList) {
				TadaManagementBean tadaBean=new TadaManagementBean();
				tadaBean.setProjectName(tadaProjectDirectorsDTO.getProjectName());
				tadaBean.setProgramCode(tadaProjectDirectorsDTO.getProgramCode());
				list.add(tadaBean);
			}
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	public String submitTaEntitleExemption(TaEntitleExemptionDTO taEntitleExemptionDTO)throws Exception{
		String message=null;
		Session session=null;
		try {
			session=hibernateUtils.getSession();
			session.saveOrUpdate(taEntitleExemptionDTO);
			message=CPSConstants.SUCCESS;
			hibernateUtils.closeSession();
		} catch (Exception e) {
			throw e;
		}
		return message;
	}
	public List<TaEntitleExemptionDTO> getEntitleExemptionList(TadaManagementBean tadBean)throws Exception{
		List<TaEntitleExemptionDTO> list=null;
		List<TaEntitleExemptionDTO> list1=new ArrayList<TaEntitleExemptionDTO>();
		Session session=null;
		try {
			session=hibernateUtils.getSession();
			list=session.createCriteria(TaEntitleExemptionDTO.class).add(Expression.eq("status", 1)).list();
			for (TaEntitleExemptionDTO taEntitleExemptionDTO : list) {
				ProgramDTO programDTO=(ProgramDTO)session.createCriteria(ProgramDTO.class).add(Expression.eq("programCode", taEntitleExemptionDTO.getProgramCode())).uniqueResult();
				taEntitleExemptionDTO.setProgramName(programDTO.getProgramName());
				list1.add(taEntitleExemptionDTO);
			}
		} catch (Exception e) {
			throw e;
		}
		return list1;
	}
	public String deleteEntitleExemption(TadaManagementBean tadaBean)throws Exception{
		TaEntitleExemptionDTO taEntitleExemptionDTO=null;
		Session session=null;
		try {
			session=hibernateUtils.getSession();
			taEntitleExemptionDTO=(TaEntitleExemptionDTO)session.get(TaEntitleExemptionDTO.class, Integer.valueOf(tadaBean.getNodeID()));
			if(!CPSUtils.isNullOrEmpty(taEntitleExemptionDTO)){
				taEntitleExemptionDTO.setStatus(0);
				taEntitleExemptionDTO.setLastModifiedBy(tadaBean.getSfID());
				taEntitleExemptionDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			}
			session.saveOrUpdate(taEntitleExemptionDTO);
			tadaBean.setMessage(CPSConstants.DELETE);
			hibernateUtils.closeSession();
		} catch (Exception e) {
			throw e;
		}
		return tadaBean.getMessage();
	}
	public String submitProgram(ProgramDTO programDTO)throws Exception{
		String message=null;
		Session session=null;
		try {
			session=hibernateUtils.getSession();
			session.saveOrUpdate(programDTO);
			message=CPSConstants.SUCCESS;
			hibernateUtils.closeSession();
		} catch (Exception e) {
			throw e;
		}
		return message;
	}
	public String deleteProgram(TadaManagementBean tadaBean)throws Exception{
		String message=null;
		Session session=null;
		ProgramDTO programDTO=null;
		try {
			session=hibernateUtils.getSession();
			programDTO=(ProgramDTO)session.get(ProgramDTO.class, Integer.valueOf(tadaBean.getNodeID()));
			if(!CPSUtils.isNullOrEmpty(programDTO)){
				programDTO.setStatus(0);
				programDTO.setLastModifiedBy(tadaBean.getSfID());
				programDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			}
			session.saveOrUpdate(programDTO);
			message=CPSConstants.DELETE;
			hibernateUtils.closeSession();
		} catch (Exception e) {
			throw e;
		}
		return message;
	}
	public List<ProgramDTO> programList()throws Exception{
		List<ProgramDTO> list=null;
		Session session=null;
		try {
			session=hibernateUtils.getSession();
			list=session.createCriteria(ProgramDTO.class).add(Expression.eq("status", 1)).addOrder(Order.asc("programCode")).list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	public String checkProgramList(TadaManagementBean tadaBean)throws Exception{
		String message=null;
		Session session=null;
		List<ProgramDTO> list=null;
		try {
			session=hibernateUtils.getSession();
			list=session.createCriteria(ProgramDTO.class).add(Expression.eq("programName", tadaBean.getProgramName().toUpperCase())).list();
			if(list.size()>0){
				message=CPSConstants.DUPLICATE;
			}
		} catch (Exception e) {
			throw e;
		}
		return message;
	}
	public List<EmpPaymentsDTO> getGradePayList()throws Exception{
		List<EmpPaymentsDTO> list=null;
		Session session=null;
		try {
			session=hibernateUtils.getSession();
			list=session.createCriteria(EmpPaymentsDTO.class).addOrder(Order.asc("sfid")).list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	public String getEntitleExemptionOption(TadaRequestBean tadaRequestBean)throws Exception{
		Session session=null;
		//List<TadaTdReqJourneyDTO> list=null;
		try {
			session=hibernateUtils.getSession();
			/*String sql="select id as id,reference_id as referenceId,departure_date as departureDate,from_place as fromPlace,to_place as toPlace,no_of_days as noOfDays," +
					"conveyance_mode as conveyanceMode,class_of_entitlement as classOfEntitlement,tatkal_quota as tatkalQuota,ticket_fare as ticketFare," +
					"ticket_fare_aft_res as ticketFareAftRes,arrival_date as arrivalDate from tada_td_req_journey_details where reference_id=? and conveyance_mode " +
					"in (select ttt.travel_type from tada_travel_type_map tttm,tada_travel_type ttt where tttm.grade_pay=? and tttm.status=0 and tttm.travel_type_id=ttt.id)";
			list=session.createSQLQuery(sql).addScalar("id", Hibernate.INTEGER).addScalar("referenceId", Hibernate.INTEGER).addScalar("departureDate", Hibernate.DATE)
			.addScalar("fromPlace", Hibernate.STRING).addScalar("toPlace", Hibernate.STRING).addScalar("conveyanceMode", Hibernate.STRING).addScalar("classOfEntitlement", Hibernate.STRING)
			.addScalar("tatkalQuota", Hibernate.STRING).addScalar("ticketFare", Hibernate.FLOAT).addScalar("ticketFareAftRes", Hibernate.FLOAT).addScalar("arrivalDate", Hibernate.DATE)
			.setInteger(0, Integer.parseInt(tadaRequestBean.getRequestId())).setInteger(1, Integer.parseInt(tadaRequestBean.getPayDetailsList().getGradePay())).setResultTransformer(Transformers.aliasToBean(TadaTdReqJourneyDTO.class)).list();*/
			TadaApprovalRequestDTO tadaApprovalRequestDTO=(TadaApprovalRequestDTO)session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("requestId", tadaRequestBean.getRequestId())).uniqueResult();
			if(!CPSUtils.isNullOrEmpty(tadaApprovalRequestDTO)){
				if(CPSUtils.compareString(tadaApprovalRequestDTO.getEntitleExemption(), "1")){
					tadaRequestBean.setEntitlementExemption("yes");
				}else{
					tadaRequestBean.setEntitlementExemption("no");
				}
			}else{
				tadaRequestBean.setEntitlementExemption("no");
			}
			
			
		} catch (Exception e) {
			throw e;
		}
		return tadaRequestBean.getEntitlementExemption();
	}
	public String getRequesterRoleID(TadaRequestBean tadaRequestBean)throws Exception{
		Session session=null;
		String requesterRoleID=null;
		try {
			session=hibernateUtils.getSession();
			String qry="select to_char(org_role_id) from emp_role_mapping where sfid=? and org_role_id=" +
					"(select office_id from emp_master where sfid=?)";
			requesterRoleID=(String)session.createSQLQuery(qry).setString(0, tadaRequestBean.getSfid()).setString(1, tadaRequestBean.getSfid()).uniqueResult();
		} catch (Exception e) {
			throw e;
		}
		return requesterRoleID;
	}
	public void removeRowId(TadaRequestBean tadaRequestBean) throws Exception
	{
		Session session=null;
		String requesterRoleID=null;
		try {
			session=hibernateUtils.getSession();
			Query qry=session.createQuery("delete from TadaTdDaNewFoodDetailsDTO where id=?");
			qry.setString(0, tadaRequestBean.getId()).executeUpdate();
			
			
		} catch (Exception e) {
			throw e;
		}
	}
}
