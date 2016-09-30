package com.callippus.web.dao.MT;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.MT.AddressDetailsBean;
import com.callippus.web.beans.MT.ArticleDetailsBean;
import com.callippus.web.beans.MT.DriverDetailsBean;
import com.callippus.web.beans.MT.FuelTypeDTO;
import com.callippus.web.beans.MT.MTApplicationBean;
import com.callippus.web.beans.MT.MTArticleDetailsDTO;
import com.callippus.web.beans.MT.MTDriverAbsentDTO;
import com.callippus.web.beans.MT.MTJourneyDetailsDTO;
import com.callippus.web.beans.MT.MTRequestDetailsDTO;
import com.callippus.web.beans.MT.MTVehicleAbsentDTO;
import com.callippus.web.beans.MT.MTVehicleAllocationtDetailsDTO;
import com.callippus.web.beans.MT.MTVehicleDriverMapDTO;
import com.callippus.web.beans.MT.MTVehicleRequestDetailsDTO;
import com.callippus.web.beans.MT.ModelMasterBean;
import com.callippus.web.beans.MT.TravelAgencyDetailsBean;
import com.callippus.web.beans.MT.VehicleCategoryMasterBean;
import com.callippus.web.beans.MT.VehicleDetailsBean;
import com.callippus.web.beans.MT.VehicleMileageDTO;
import com.callippus.web.beans.dto.KeyDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.YearTypeDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.business.tx.workflow.TxRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.address.SQLAddressDAO;

@Service
public class SQLMechincalTransportDAO implements IMechincalTransportDAO {

	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	
	private static Log log = LogFactory.getLog(SQLAddressDAO.class);
	@Autowired
	private TxRequestProcess txRequestProcess;
	@Autowired
	private MTVehicleRequestProcess mtVehicleRequestProcess;
	
	

	
	
	
	
	//added by Narayana
	//for Vehicle Category
	@SuppressWarnings("unchecked")
	public List<VehicleCategoryMasterBean> categoryList() throws Exception {
		log.debug("SQLMechincalTransportDAO---->categoryList");
		List<VehicleCategoryMasterBean> categoryList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			categoryList = session.createCriteria(VehicleCategoryMasterBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.asc("categoryName").ignoreCase()).list();
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return categoryList;
	}
	@SuppressWarnings("unchecked")
	public String saveCategoryMasterData(VehicleCategoryMasterBean vcmDTO) throws Exception {
		log.debug("saveCategoryMasterData ---> method start");
		String message = null;
		Session session = null;
		List<VehicleCategoryMasterBean> categoryList = null;
		
		try {
			session = hibernateUtils.getSession();
			if(vcmDTO.getCategoryId() != 0){
				session.saveOrUpdate(vcmDTO);	
				session.flush();
				session.clear();
				message = CPSConstants.UPDATE;
			}else{
				
				categoryList = session.createCriteria(VehicleCategoryMasterBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.asc("carriageType")).list();
				System.out.println("This is the list size before saving:"+categoryList.size());
				
				categoryList = session.createCriteria(VehicleCategoryMasterBean.class).add(Expression.eq("status", 1)).add(Expression.eq("categoryName", vcmDTO.getCategoryName())).list();
				if(categoryList.size()==0){
					session.saveOrUpdate(vcmDTO);	
					session.flush();
					session.clear();
					message = CPSConstants.SUCCESS;
				}else{
					message = CPSConstants.DUPLICATE;
				}
				
			}
		
		}catch (Exception e) {
			message = CPSConstants.FAILED;
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
			
			session = hibernateUtils.getSession();
			categoryList = session.createCriteria(VehicleCategoryMasterBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.asc("carriageType")).list();
			System.out.println("This is the list size after saving:"+categoryList.size());

			hibernateUtils.closeSession();
		}
		log.debug("message ---> " + message);
		log.debug("saveCategoryMasterData ---> method end");
		return message;
	}

	@SuppressWarnings("unchecked")
	public String deleteCategoryDetails(MTApplicationBean mtBean) throws Exception {
		String message = "";
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			List<ModelMasterBean> modelList = session.createCriteria(ModelMasterBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("vehicleCategoryId", Integer.parseInt(mtBean.getPk()))).list();
			if(modelList.size()==0){
			session.createQuery("update VehicleCategoryMasterBean set status=0 where categoryId=" + Integer.parseInt(mtBean.getPk())).executeUpdate();
			message = CPSConstants.DELETE;
			}else{
				message = CPSConstants.FAILED;	
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}

		return message;
	}
	
	
	//for Vehicle Model
	@SuppressWarnings("unchecked")
	public List<ModelMasterBean> modelList() throws Exception {
		log.debug("SQLMechincalTransportDAO---->modelList");
		List<ModelMasterBean> modelList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			modelList = session.createCriteria(ModelMasterBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.asc("modelName").ignoreCase()).createAlias("categoryDetails", "category").addOrder(Order.asc("category.categoryName").ignoreCase()).list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return modelList;
	}
	@SuppressWarnings("unchecked")
	public String saveModelMasterData(ModelMasterBean vmmDTO) throws Exception {
		log.debug("saveModelMasterData ---> method start");
		String message = null;
		Session session = null;
		List<ModelMasterBean> modelList = null;
		try {
			session = hibernateUtils.getSession();
			if(vmmDTO.getModelId() != 0){
				session.saveOrUpdate(vmmDTO);	
				session.flush();
				session.clear();
				message = CPSConstants.UPDATE;
			}else{
				modelList = session.createCriteria(ModelMasterBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("modelName", vmmDTO.getModelName())).list();
				if(modelList.size()==0){
					session.saveOrUpdate(vmmDTO);	
					session.flush();
					session.clear();
					message = CPSConstants.SUCCESS;
				}else{
					message = CPSConstants.DUPLICATE;
				}
				
			}
		
		}catch (Exception e) {
			message = CPSConstants.FAILED;
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		log.debug("message ---> " + message);
		log.debug("saveModelMasterData ---> method end");
		return message;
	}

	@SuppressWarnings("unchecked")
	public String deleteModelDetails(MTApplicationBean mtBean) throws Exception {
		String message = "";
		Session session = null;
		List<VehicleDetailsBean > vehicleList = null;
		try {
			session = hibernateUtils.getSession();
			vehicleList = session.createCriteria(VehicleDetailsBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("vehicleModelId", Integer.parseInt(mtBean.getPk()))).list();
			if(vehicleList.size()==0){
			session.createQuery("update ModelMasterBean set status=0 where modelId=" + Integer.parseInt(mtBean.getPk())).executeUpdate();
			message = CPSConstants.DELETE;
			}else{
				message = CPSConstants.FAILED;
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}

		return message;
	}
	
	//for TravelAgency
	@SuppressWarnings("unchecked")
	public List<TravelAgencyDetailsBean> travelAgencyList() throws Exception {
		log.debug("SQLMechincalTransportDAO---->travelAgencyList");
		List<TravelAgencyDetailsBean> travelAgencyList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			travelAgencyList = session.createCriteria(TravelAgencyDetailsBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.asc("travelAgencyName").ignoreCase()).list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return travelAgencyList;
	}
	@SuppressWarnings("unchecked")
	public String saveTravelAgencyMasterData(TravelAgencyDetailsBean tamDTO) throws Exception {
		log.debug("saveTravelAgencyMasterData ---> method start");
		String message = null;
		Session session = null;
		List<TravelAgencyDetailsBean> travelAgencyList = null;
		try {
			session = hibernateUtils.getSession();
			if(tamDTO.getTravelId() != 0){
				session.saveOrUpdate(tamDTO);	
				session.flush();
				session.clear();
				message = CPSConstants.UPDATE;
			}else{
				travelAgencyList = session.createCriteria(TravelAgencyDetailsBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("travelAgencyName", tamDTO.getTravelAgencyName())).list();
				if(travelAgencyList.size()==0){
					session.saveOrUpdate(tamDTO);	
					session.flush();
					session.clear();
					message = CPSConstants.SUCCESS;
				}else{
					message = CPSConstants.DUPLICATE;
				}	
			}
		
		}catch (Exception e) {
			message = CPSConstants.FAILED;
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		log.debug("message ---> " + message);
		log.debug("saveTravelAgencyMaster ---> method end");
		return message;
	}

	@SuppressWarnings("unchecked")
	public String deleteTravelAgencyDetails(MTApplicationBean mtBean) throws Exception {
		String message = "";
		Session session = null;
		List<VehicleDetailsBean> vehicleList = null;
		try {
			session = hibernateUtils.getSession();
			vehicleList = session.createCriteria(VehicleDetailsBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("vehicleTravelAgencyName", mtBean.getPk())).list();
			if(vehicleList.size()==0){
			session.createQuery("update TravelAgencyDetailsBean set status=0 where travelId=" + Integer.parseInt(mtBean.getPk())).executeUpdate();
			message = CPSConstants.DELETE;
			}else{
				message = CPSConstants.FAILED;	
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}

		return message;
	}
	
	//for New Driver
	@SuppressWarnings("unchecked")
	public List<DriverDetailsBean> driverList() throws Exception {
		log.debug("SQLMechincalTransportDAO---->driverList");
		List<DriverDetailsBean> driverList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			driverList = session.createCriteria(DriverDetailsBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.asc("driverName").ignoreCase()).list();
			for(int i=0; i<driverList.size(); i++){
				DriverDetailsBean dDTO = (DriverDetailsBean)driverList.get(i);
				if(!CPSUtils.isNullOrEmpty(dDTO.getDriverTravelAgencyName())){
				Object obj = session.createQuery(
						"select travelAgencyName from TravelAgencyDetailsBean where status=1 and travelId="
								+ Integer.parseInt(dDTO.getDriverTravelAgencyName())).uniqueResult();
					if (!CPSUtils.isNullOrEmpty(obj)) {
						dDTO.setDriverTravelAgencyNameDetail(obj.toString());
					}
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return driverList;
	}
	
	public List<DriverDetailsBean> getHiredDriversList() throws Exception {
		log.debug("SQLMechincalTransportDAO---->driverList");
		List<DriverDetailsBean> driverList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			driverList = session.createCriteria(DriverDetailsBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("driverType", 2)).addOrder(Order.asc("driverName")).list();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return driverList;
	}
	@SuppressWarnings("unchecked")
	public String saveDriverData(DriverDetailsBean dDTO) throws Exception {
		log.debug("saveDriverData ---> method start");
		String message = null;
		Session session = null;
		List<DriverDetailsBean> driverList = null;
		try {
			session = hibernateUtils.getSession();
			if(dDTO.getDriverId() != 0){
				session.saveOrUpdate(dDTO);	
				session.flush();
				session.clear();
				message = CPSConstants.UPDATE;
			}else{
				driverList = session.createCriteria(DriverDetailsBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("driverIdSfid", dDTO.getDriverIdSfid())).list();
				if(driverList.size()==0){
					session.saveOrUpdate(dDTO);	
					session.flush();
					session.clear();
					message = CPSConstants.SUCCESS;
				}else{
					message = CPSConstants.DUPLICATE;
				}
				
			}
		
		}catch (Exception e) {
			message = CPSConstants.FAILED;
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		log.debug("message ---> " + message);
		log.debug("saveDriverData ---> method end");
		return message;
	}

	@SuppressWarnings("unchecked")
	public String deleteDriverDetails(MTApplicationBean mtBean) throws Exception {
		String message = "";
		Session session = null;
		List<MTVehicleDriverMapDTO> vehicleDriverList = null;
		try {
			session = hibernateUtils.getSession();
			vehicleDriverList = session.createCriteria(MTVehicleDriverMapDTO.class).add(Expression.eq("driverId", Integer.parseInt(mtBean.getPk()))).add(Expression.eq(CPSConstants.STATUS, 1)).list();
			if(vehicleDriverList.size()==0){
				session.createQuery("update DriverDetailsBean set status=0 where driverId=" + Integer.parseInt(mtBean.getPk())).executeUpdate();
				message = CPSConstants.DELETE;
			}else{
				message = CPSConstants.EXIST;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}

		return message;
	}
	
	//for New Vehicle
	@SuppressWarnings("unchecked")
	public List<VehicleDetailsBean> vehicleList(MTApplicationBean mtBean) throws Exception {
		log.debug("SQLMechincalTransportDAO---->vehicleList");
		List<VehicleDetailsBean> vehicleList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			if(mtBean.getVehicleType()==0 && mtBean.getVehiclePoolType()==0){
				vehicleList = session.createCriteria(VehicleDetailsBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).createAlias("modelMasterDetails", "model").addOrder(Order.asc("model.modelName").ignoreCase()).list();
			}else if(mtBean.getVehicleType()!=0 && mtBean.getVehiclePoolType()!=0){
				vehicleList = session.createCriteria(VehicleDetailsBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("vehicleType", mtBean.getVehicleType())).add(Expression.eq("vehiclePoolType", mtBean.getVehiclePoolType())).createAlias("modelMasterDetails", "model").addOrder(Order.asc("model.modelName").ignoreCase()).list();
			}else if(mtBean.getVehicleType()!=0){
				vehicleList = session.createCriteria(VehicleDetailsBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("vehicleType", mtBean.getVehicleType())).createAlias("modelMasterDetails", "model").addOrder(Order.asc("model.modelName").ignoreCase()).list();
			}else if(mtBean.getVehiclePoolType()!=0){
				vehicleList = session.createCriteria(VehicleDetailsBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("vehiclePoolType", mtBean.getVehiclePoolType())).createAlias("modelMasterDetails", "model").addOrder(Order.asc("model.modelName").ignoreCase()).list();
			}
			
			//vehicleList = session.createCriteria(VehicleDetailsBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).setProjection(Projections.)
			for(int i=0; i<vehicleList.size(); i++){
				VehicleDetailsBean vDTO = (VehicleDetailsBean)vehicleList.get(i);
				if(!CPSUtils.isNullOrEmpty(vDTO.getVehicleTravelAgencyName())){
				Object obj = session.createQuery(
						"select travelAgencyName from TravelAgencyDetailsBean where status=1 and travelId="
								+ Integer.parseInt(vDTO.getVehicleTravelAgencyName())).uniqueResult();
					if (!CPSUtils.isNullOrEmpty(obj)) {
						vDTO.setVehicleTravelAgencyNameDetail(obj.toString());
					}
					if(!CPSUtils.isNullOrEmpty(vDTO.getDriverName())){
						String driverName=(String)session.createSQLQuery("select driver_name from mt_driver_master where id="+vDTO.getDriverName()+" and status=1").uniqueResult();
						vDTO.setDriverNameString(driverName);
					}
					
				}	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return vehicleList;
	}
	@SuppressWarnings("unchecked")
	public String saveVehicleData(VehicleDetailsBean vDTO) throws Exception {
		log.debug("saveVehicleData ---> method start");
		String message = null;
		Session session = null;
		List<VehicleDetailsBean> vehicleList = null;
		try {
			session = hibernateUtils.getSession();
			if(vDTO.getVehicleId() != 0){
				session.saveOrUpdate(vDTO);	
				session.flush();
				session.clear();
				message = CPSConstants.UPDATE;
			}else{
				vehicleList = session.createCriteria(VehicleDetailsBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("vehicleNo", vDTO.getVehicleNo())).list();
				if(vehicleList.size()==0){
					session.save(vDTO);	
					session.flush();
				
					session.clear();
					message = CPSConstants.SUCCESS;
				}else{
					message = CPSConstants.DUPLICATE;
				}
				
			}
		
		}catch (Exception e) {
			message = CPSConstants.FAILED;
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		log.debug("message ---> " + message);
		log.debug("saveVehicleData ---> method end");
		return message;
	}

	@SuppressWarnings("unchecked")
	public String deleteVehicleDetails(MTApplicationBean mtBean) throws Exception {
		String message = "";
		Session session = null;
		@SuppressWarnings("unused")
		List<MTVehicleDriverMapDTO> vehicleDriverList = null;
		try {
			session = hibernateUtils.getSession();
			vehicleDriverList = session.createCriteria(MTVehicleDriverMapDTO.class).add(Expression.eq("vehicleId", Integer.parseInt(mtBean.getPk()))).add(Expression.eq(CPSConstants.STATUS, 1)).list();
			if(vehicleDriverList.size()==0){
			  session.createQuery("update VehicleDetailsBean set status=0 where vehicleId=" + Integer.parseInt(mtBean.getPk())).executeUpdate();
			  message = CPSConstants.DELETE;
			}else{
				message = CPSConstants.EXIST;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}

		return message;
	}
	
	//for New Address
	@SuppressWarnings("unchecked")
	public List<AddressDetailsBean> addressList() throws Exception {
		log.debug("SQLMechincalTransportDAO---->addressList");
		List<AddressDetailsBean> addressList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			addressList = session.createCriteria(AddressDetailsBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.asc("addressName").ignoreCase()).list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		System.out.println("Adress list ::------------------:::::::::  "+addressList);
		return addressList;
	}
	@SuppressWarnings("unchecked")
	public String saveAddressData(AddressDetailsBean aDTO) throws Exception {
		log.debug("saveAddressData ---> method start");
		String message = null;
		Session session = null;
		List<AddressDetailsBean> addressList = null;
		try {
			session = hibernateUtils.getSession();
			if(aDTO.getAddressId() != 0){
				session.saveOrUpdate(aDTO);	
				session.flush();
				session.clear();
				message = CPSConstants.UPDATE;
			}else{
				addressList = session.createCriteria(AddressDetailsBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("addressName", aDTO.getAddressName())).list();
				if(addressList.size()==0){
					session.saveOrUpdate(aDTO);	
					session.flush();
					session.clear();
					message = CPSConstants.SUCCESS;
				}else{
					message = CPSConstants.DUPLICATE;
				}
				
			}
		
		}catch (Exception e) {
			message = CPSConstants.FAILED;
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		log.debug("message ---> " + message);
		log.debug("saveAddressData ---> method end");
		return message;
	}

	public String deleteAddressDetails(MTApplicationBean mtBean) throws Exception {
		String message = "";
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			session.createQuery("update AddressDetailsBean set status=0 where addressId=" + Integer.parseInt(mtBean.getPk())).executeUpdate();
			message = CPSConstants.DELETE;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}

		return message;
	}
	
	//for driver Absent
	@SuppressWarnings("unchecked")
	public List getDriverEmplyeeList() throws Exception {
		log.debug("getDriverEmplyeeList ---> method start");
		List<KeyValueDTO> driverList = null;
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			String qry = "select id as value,driver_name as name from mt_driver_master where status=1 and driver_type!=2 order by DRIVER_NAME";
			driverList = session.createSQLQuery(qry).addScalar("name",Hibernate.STRING).addScalar("value",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return driverList;
	}
	
	@SuppressWarnings("unchecked")
	public String saveDriverAbsentDetails(MTDriverAbsentDTO vdaDTO) throws Exception {
		log.debug("saveDriverAbsentDetails ---> method start");
		String message = null;
		Session session = null;
		List<MTDriverAbsentDTO> existAbList = null;
		
		try {
			session = hibernateUtils.getSession();
			if(vdaDTO.getId() !=0){
				session.saveOrUpdate(vdaDTO);
				session.flush();
				session.clear();
				message = CPSConstants.UPDATE;	
			}else{
				//for knowing the existence of absent driver with same from and to date
				if(!CPSUtils.compareTwoDates(vdaDTO.getFromDate(), vdaDTO.getToDate())){
					existAbList = session.createCriteria(MTDriverAbsentDTO.class).add(Expression.eq("driverId",vdaDTO.getDriverId())).add(Expression.between("fromDate", vdaDTO.getFromDate(), vdaDTO.getToDate())).add(Expression.between("toDate", vdaDTO.getFromDate(), vdaDTO.getToDate())).add(Expression.eq("status", 1)).list();		
				}else if(CPSUtils.compareTwoDates(vdaDTO.getFromDate(), vdaDTO.getToDate())){
					existAbList = session.createCriteria(MTDriverAbsentDTO.class).add(Expression.eq("driverId",vdaDTO.getDriverId())).add(Expression.between("fromTime", vdaDTO.getFromTime(), vdaDTO.getToTime())).add(Expression.between("toTime", vdaDTO.getFromTime(), vdaDTO.getToTime())).add(Expression.eq("status", 1)).list();
				}
				if(existAbList.size()>0){
					message = CPSConstants.EXIST;
				}else{
					session.saveOrUpdate(vdaDTO);
					session.flush();
					session.clear();
					message = CPSConstants.SUCCESS;	
				}
			}
			
			
			 /*if(vdaDTO.getId() !=0){
				session.saveOrUpdate(vdaDTO);
				message = CPSConstants.UPDATE;
			}else{
				existAbList = session.createSQLQuery(sql).list();
				if(existAbList.size()==0){
					session.saveOrUpdate(vdaDTO);
					message = CPSConstants.SUCCESS;	
				}else{
					message = CPSConstants.EXIST;
				}	
			}*/
		}catch (Exception e) {
			message = CPSConstants.FAILED;
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		log.debug("message ---> " + message);
		log.debug("saveDriverAbsentDetails ---> method end");
		return message;
	}
	public String deleteDriverAbsentDetails(MTApplicationBean mtBean) throws Exception{
		String message = "";
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			session.createQuery("update MTDriverAbsentDTO set status=0 where id=" + Integer.parseInt(mtBean.getPk())).executeUpdate();
			message = CPSConstants.DELETE;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}

		return message;
	}
	@SuppressWarnings("unchecked")
	public List getDriverAbsentDetails() throws Exception{
		List<MTDriverAbsentDTO> absentList = null;
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			absentList = session.createCriteria(MTDriverAbsentDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.desc("id")).list();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return absentList;
		
	}
	//for vehicle Absent
	@SuppressWarnings("unchecked")
	public List getVehicleList() throws Exception {
		log.debug("getVehicleList ---> method start");
		List<VehicleDetailsBean> vList = null;
		List<KeyValueDTO> vehicleList = new ArrayList<KeyValueDTO>();
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			//String qry = "select id as value,vehicle_no as name from mt_vehicle_master where status=1 and vehicle_type=1";
			//vehicleList = session.createSQLQuery(qry).addScalar("name",Hibernate.STRING).addScalar("value",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();

			/*vList = session.createCriteria(VehicleDetailsBean.class).add(Expression.eq("vehicleType", 1)).add(Expression.eq("status", 1)).list();
			if(vList.size()>0){
				for(int i=0; i<vList.size() ; i++){
					KeyValueDTO kdDto = new KeyValueDTO();
					kdDto.setValue(String.valueOf(vList.get(i).getVehicleId()));
					kdDto.setName(vList.get(i).getVehicleNo()+"("+vList.get(i).getVehiclePoolTypeDetails().getVehiclePoolType()+")");
					vehicleList.add(kdDto);
				}
				
			}*/
			
			String sql ="SELECT VM.ID as id,VM.VEHICLE_NO ||'('|| VPT.TYPE ||')/'|| md.name as name FROM MT_VEHICLE_MASTER VM,MT_VEHICLE_POOL_TYPE VPT," +
		    " mt_vehicle_model_master md WHERE VM.STATUS=1 AND VM.VEHICLE_TYPE=1 AND VM.VEHICLE_FOR_DEDICATE_ITEM=VPT.TYPE_ID AND VPT.STATUS=1 and " +
		    " md.status=1 and vm.model_id=md.model_id ORDER BY md.name";

			vehicleList = session.createSQLQuery(sql).addScalar("id", Hibernate.INTEGER).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return vehicleList;
	}
	public String saveVehicleAbsentDetails(MTVehicleAbsentDTO vaDTO) throws Exception {
		log.debug("saveVehicleAbsentDetails ---> method start");
		String message = null;
		Session session = null;
		List existAbList = null;
		
		try {
			session = hibernateUtils.getSession();
			//for knowing the existence of absent driver with same from and to date
		/*	String sql="SELECT ID FROM MT_VEHICLE_ABSENT_DETAILS WHERE " +
					" FROM_DATE = TO_DATE('"+fDate+"','dd-mon-yyyy HH24-MI')" +
					" AND TO_DATE = TO_DATE('"+tDate+"','dd-mon-yyyy HH24-MI') AND VEHICLE_ID="+vaDTO.getVehicleId()+" AND STATUS=1";*/
			if(vaDTO.getId() !=0){
				session.saveOrUpdate(vaDTO);
				session.flush();
				session.clear();
				message = CPSConstants.UPDATE;	
			}else{
				//for knowing the existence of absent driver with same from and to date
				if(!CPSUtils.compareTwoDates(vaDTO.getFromDate(), vaDTO.getToDate())){
					existAbList = session.createCriteria(MTVehicleAbsentDTO.class).add(Expression.eq("vehicleId",vaDTO.getVehicleId())).add(Expression.between("fromDate", vaDTO.getFromDate(), vaDTO.getToDate())).add(Expression.between("toDate", vaDTO.getFromDate(), vaDTO.getToDate())).add(Expression.eq("status", 1)).list();	
				}else if(CPSUtils.compareTwoDates(vaDTO.getFromDate(), vaDTO.getToDate())){
					existAbList = session.createCriteria(MTVehicleAbsentDTO.class).add(Expression.eq("vehicleId",vaDTO.getVehicleId())).add(Expression.between("fromTime", vaDTO.getFromTime(), vaDTO.getToTime())).add(Expression.between("toTime", vaDTO.getFromTime(), vaDTO.getToTime())).add(Expression.eq("status", 1)).list();
				}if(existAbList.size()>0){
					message = CPSConstants.EXIST;
				}else{
					session.saveOrUpdate(vaDTO);
					session.flush();
					session.clear();
					message = CPSConstants.SUCCESS;	
					
				}
			}
			//String qry="update MT_VEHICLE_DRIVER_MAP_DETAILS set VEHICLE_ABSENT_ID=";
		}catch (Exception e) {
			message = CPSConstants.FAILED;
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		log.debug("message ---> " + message);
		log.debug("saveVehicleAbsentDetails ---> method end");
		return message;
	}
	public String deleteVehicleAbsentDetails(MTApplicationBean mtBean) throws Exception{
		String message = "";
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			session.createQuery("update MTVehicleAbsentDTO set status=0 where id=" + Integer.parseInt(mtBean.getPk())).executeUpdate();
			message = CPSConstants.DELETE;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}

		return message;
	}
	@SuppressWarnings("unchecked")
	public List getVehicleAbsentDetails() throws Exception{
		List<MTVehicleAbsentDTO> absentList = null;
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			absentList = session.createCriteria(MTVehicleAbsentDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.desc("fromDate")).list();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return absentList;
		
	}
	
	//for vehicle,driver List used as drop down in vehicle-driver-master screen
	@SuppressWarnings("unchecked")
	public List getNotInMapVehicleList() throws Exception {
		log.debug("SQLMechincalTransportDAO---->getNotInMapVehiclesList");
		Session session = null;
		List<VehicleDetailsBean> vlist = null;
		try{
			session = hibernateUtils.getSession();
	
			String sql ="SELECT VM.ID as vehicleId,VM.VEHICLE_NO ||'('|| VPT.TYPE ||')/'|| md.name as vehicleNo,VM.VEHICLE_FOR_DEDICATE_ITEM as vehiclePoolType  FROM MT_VEHICLE_MASTER VM,MT_VEHICLE_POOL_TYPE VPT," +
					    " mt_vehicle_model_master md WHERE VM.STATUS=1 AND VM.VEHICLE_TYPE=1 AND VM.VEHICLE_FOR_DEDICATE_ITEM=VPT.TYPE_ID AND VPT.STATUS=1 and " +
					    " md.status=1 and vm.model_id=md.model_id and VM.ID not in (select vehicle_id from mt_vehicle_driver_map_details where status in (1,10)) ORDER BY md.name";
			/*String sql ="SELECT VM.ID as vehicleId,VM.VEHICLE_NO ||'('|| VPT.TYPE ||')/'|| md.name as vehicleNo,VM.VEHICLE_FOR_DEDICATE_ITEM as vehiclePoolType,vtype.type as vehicleTypeName FROM MT_VEHICLE_MASTER VM,MT_VEHICLE_POOL_TYPE VPT," +
		    " mt_vehicle_model_master md, mt_vehicle_type vtype WHERE VM.STATUS=1 AND VM.VEHICLE_TYPE=1 AND VM.VEHICLE_FOR_DEDICATE_ITEM=VPT.TYPE_ID AND VPT.STATUS=1 and " +
		    " md.status=1 and vm.model_id=md.model_id  and vtype.status=1 and vtype.type_id=vm.vehicle_type and VM.ID not in (select vehicle_id from mt_vehicle_driver_map_details where status in (1,10)) ORDER BY md.name";*/
			
			vlist = session.createSQLQuery(sql).addScalar("vehicleId", Hibernate.INTEGER).addScalar("vehicleNo", Hibernate.STRING).addScalar("vehiclePoolType", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(VehicleDetailsBean.class)).list();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return vlist;
	}
	public List getallNotMappedVehicles() throws Exception {
		log.debug("SQLMechincalTransportDAO---->getNotInMapVehiclesList");
		Session session = null;
		List<VehicleDetailsBean> vlist = null;
		List<BigDecimal> mapList=null;
		try{
			List<Integer> ids=new ArrayList<Integer>();
			session = hibernateUtils.getSession();
			mapList=session.createSQLQuery("select unique vehicle_id as vehicleId from mt_vehicle_driver_map_details where status in (1,10)").list();
			if(!CPSUtils.isNullOrEmpty(mapList)){
				for(int i=0;i<mapList.size();i++){
					ids.add(mapList.get(i).intValue());
				}
			}
			vlist=session.createCriteria(VehicleDetailsBean.class).add(Expression.eq("status", 1)).add(Expression.not(Expression.in("vehicleId", ids))).list();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return vlist;
	}
	
	@SuppressWarnings("unchecked")
	public List getNotInMapDriverList() throws Exception {
		log.debug("SQLMechincalTransportDAO---->getNotInMapDriverList");
		Session session = null;
		List<DriverDetailsBean> dlist = null;
		try{
			session = hibernateUtils.getSession();
			/*String sql = "SELECT DM.ID AS driverId,DM.DRIVER_NAME AS driverName FROM MT_DRIVER_MASTER DM WHERE " +
					" DM.ID NOT IN(SELECT VDM.DRIVER_ID FROM MT_VEHICLE_DRIVER_MASTER VDM WHERE VDM.STATUS_FLAG=1 AND VDM.STATUS=1)" +
					" AND DM.STATUS=1 ";*/
			String sql = "SELECT DM.ID AS driverId,DM.DRIVER_NAME AS driverName FROM MT_DRIVER_MASTER DM WHERE " +
						 " DM.STATUS=1 AND DM.DRIVER_TYPE!=2 and DM.ID not in (select driver_id from mt_vehicle_driver_map_details where status in (1)) order by dm.DRIVER_NAME";
			dlist = session.createSQLQuery(sql).addScalar("driverId", Hibernate.INTEGER).addScalar("driverName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(DriverDetailsBean.class)).list();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return dlist;
	}
	
	//for getting present active map details
	@SuppressWarnings("unchecked")
	public List getVehicleDriverList(MTApplicationBean mtBean) throws Exception {
		log.debug("SQLMechincalTransportDAO---->vehicleDriverList");
		/*List<MTVehicleDriverMapDTO123> vehicleDriverList = new ArrayList<MTVehicleDriverMapDTO123>();
		Session session = null;
		List<KeyValueDTO> idList = null;
		MTVehicleDriverMapDTO123 dto = null;
		try {
			session = hibernateUtils.getSession();
			//vehicleDriverList = session.createCriteria(MTVehicleDriverMapDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("statusFlag", 1)).list();
			String sql="SELECT id as id FROM MT_VEHICLE_DRIVER_MASTER WHERE STATUS=1 AND STATUS_FLAG=1 " +
					"  UNION" +
					" SELECT id as id FROM MT_VEHICLE_DRIVER_MASTER WHERE " +
					" id in(" +
					" SELECT MAX(ID) FROM MT_VEHICLE_DRIVER_MASTER WHERE " +
					" STATUS_FLAG=1 AND STATUS=1 AND VEHICLE_ID " +
					" NOT IN(SELECT VEHICLE_ID FROM MT_VEHICLE_DRIVER_MASTER WHERE STATUS=1 AND STATUS_FLAG=1) GROUP BY VEHICLE_ID)";
			idList = session.createSQLQuery(sql).addScalar("id", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
			if(idList !=null && idList.size()>0){
				for(int i=0; i<idList.size(); i++){
					KeyValueDTO kDTO = (KeyValueDTO)idList.get(i);
					 dto = (MTVehicleDriverMapDTO123)session.createCriteria(MTVehicleDriverMapDTO123.class).add(Expression.eq("id", kDTO.getId())).list().get(0);
					 vehicleDriverList.add(dto);
				}
			}*/
		Session session = null;
		List<MTVehicleDriverMapDTO> mapList = null;
		try{
			session = hibernateUtils.getSession();
			if(!CPSUtils.isNullOrEmpty(mtBean.getSearchFromDate()) && !CPSUtils.isNullOrEmpty(mtBean.getSearchFromTime()) && !CPSUtils.isNullOrEmpty(mtBean.getSearchToDate()) && !CPSUtils.isNullOrEmpty(mtBean.getSearchToTime())){
				String fromDate=mtBean.getSearchFromDate()+" "+mtBean.getSearchFromTime();
				String toDate=mtBean.getSearchToDate()+" "+mtBean.getSearchToTime();
				/*String qry="select id from mt_vehicle_driver_map_details where (to_date(?,'dd-Mon-yyyy HH24-MI') between " +
						"allotment_from_date and allotment_to_date or to_date(?,'dd-Mon-yyyy HH24-MI') between " +
						"allotment_from_date and allotment_to_date or allotment_to_date is null) and status!=0";
						List idList=session.createSQLQuery(qry).setString(0, fromDate).setString(1, toDate).list();*/
				String qry="SELECT id FROM mt_vehicle_driver_map_details WHERE  status !=0 AND " +
						"(((allotment_from_date BETWEEN to_date(?,'dd-Mon-yyyy HH24-MI')  AND " +
						"to_date(?,'dd-Mon-yyyy HH24-MI')) or (allotment_to_date  BETWEEN " +
						" to_date(?,'dd-Mon-yyyy HH24-MI')  AND to_date(?,'dd-Mon-yyyy HH24-MI')))" +
						" OR allotment_to_date IS NULL)";
				List idList=session.createSQLQuery(qry).setString(0, fromDate).setString(1, toDate).setString(2, fromDate).setString(3, toDate).list();
				List<Integer> intIds=new ArrayList<Integer>();
				for(int i=0;i<idList.size();i++){
					intIds.add(((BigDecimal)idList.get(i)).intValue());
				}
				mapList = session.createCriteria(MTVehicleDriverMapDTO.class).add(Expression.in("id", intIds)).addOrder(Order.asc("vehicleId")).addOrder(Order.desc("allotmentFromDate")).list();
			}else{
				mapList = session.createCriteria(MTVehicleDriverMapDTO.class).add(Expression.eq("status", 1)).createAlias("diverDetails", "driver").addOrder(Order.asc("driver.driverName").ignoreCase()).list();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return mapList;
	}
	
	//for saving vid,did in vehicle-driver map table(from master and change screen) and inserting dedicated vehicles in vehicle allocation table
	@SuppressWarnings("unchecked")
	public String saveVehicleDriverMasterData(MTVehicleDriverMapDTO vdDTO,String param) throws Exception {
	
	/*	log.debug("saveVehicleDriverMasterData ---> method start");
		String message = null;
		Session session = null;
		List<MTVehicleDriverMapDTO123> existList = null;
		StringBuffer sb = new StringBuffer();
		try {
			session = hibernateUtils.getSession();
			//if mapId is 0 then record s from master else it is from change driver
			if(vdDTO.getMapId()==0){
				//for knowing the existence of vehicle-driver map 
				sb.append("SELECT ID FROM MT_VEHICLE_DRIVER_MASTER WHERE STATUS_FLAG=1 AND STATUS=1 ");
				if(vdDTO.getVehicleId() !=0){
					sb.append("AND (VEHICLE_ID=");
					sb.append(vdDTO.getVehicleId());	
				}
				if(vdDTO.getDriverId() !=0){
					sb.append(" OR DRIVER_ID=");
					sb.append(vdDTO.getDriverId());	
					sb.append(" )");
				}else{
					sb.append(" )");
				}
			
				//String sql="SELECT ID FROM MT_VEHICLE_DRIVER_MASTER WHERE (VEHICLE_ID="+vdDTO.getVehicleId()+" OR DRIVER_ID !="+vdDTO.getDriverId()+") AND STATUS_FLAG=1 AND STATUS=1";
				existList = session.createSQLQuery(sb.toString()).list();
				if(existList!=null && existList.size()>0){
					message = CPSConstants.EXIST;
				}else{
					int id=(Integer)session.save(vdDTO);
					vdDTO.setId(id);
					saveDedicatedVehicles(vdDTO);//for only Dedicated Vehicles
					message = CPSConstants.SUCCESS;
				 }
			}else{
					int id=(Integer)session.save(vdDTO);
					vdDTO.setId(id);
					updateVehicleDriverMap(vdDTO); //for changing driver dynamically
					message = CPSConstants.UPDATE;
			 	}*/
				log.debug("saveVehicleDriverMasterData ---> method start");
				String message = null;
				Session session = null;
				try{
					session = hibernateUtils.getSession();
					
					//if(vdDTO.getId()!=0){
					if(CPSUtils.compareString(param,"ChangeDriver")){
						session.saveOrUpdate(vdDTO);
						session.flush();
						session.clear();
						message = CPSConstants.UPDATE;
						
					}else{
						StringBuffer qry = new StringBuffer();
						qry.append("select map.id from MTVehicleDriverMapDTO map where map.status=1 and (map.vehicleId="+vdDTO.getVehicleId());
						if(vdDTO.getDriverId()!=0){
							qry.append(" or driverId="+vdDTO.getDriverId());	
						}
						qry.append(")");
						//StringBuffer qry ="select map.id from MTVehicleDriverMapDTO map where map.status=1 and (map.vehicleId="+vdDTO.getVehicleId()+" or driverId="+vdDTO.getDriverId()+")";
						if(session.createQuery(qry.toString()).list().size()==0){
							session.saveOrUpdate(vdDTO);
							session.flush();
							session.clear();
							message = CPSConstants.SUCCESS;
						}else{
							message = CPSConstants.EXIST;
						}
					}
					session.flush();
				}catch (Exception e) {
					message = CPSConstants.FAILED;
					e.printStackTrace();
					throw e;
				}finally{
					//hibernateUtils.closeSession();
				}
				log.debug("message ---> " + message);
				log.debug("saveVehicleDriverMasterData ---> method end");
				return message;
	}
	public VehicleDetailsBean getDedicatedVehicleDetails(int vehicleId) throws Exception {
		Session session = null;
		VehicleDetailsBean vehicleDetailsBean=null;
		try{
			session = hibernateUtils.getSession();
			vehicleDetailsBean=(VehicleDetailsBean)session.createCriteria(VehicleDetailsBean.class).add(Expression.eq("status", 1)).add(Expression.eq("vehicleId", vehicleId)).uniqueResult();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return vehicleDetailsBean;
	}
	
	public String updateAllocationMap(MTVehicleDriverMapDTO oldMapDTO,MTVehicleDriverMapDTO newMapDTO) throws Exception {
		String message="";
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			SimpleDateFormat df=new SimpleDateFormat("dd-MMM-yyyy HH:mm");
			/*String qry="update mt_vehicle_allocation_details set vehicle_driver_map_id=? where vehicle_driver_map_id=? and " +
					" (to_date(?,'dd-Mon-yyyy HH24:MI') between from_date_time and to_date_time or" +
					" to_date(?,'dd-Mon-yyyy HH24:MI') between from_date_time and to_date_time)";*/
			String qry="update mt_vehicle_allocation_details set vehicle_driver_map_id=?  where vehicle_driver_map_id=? and " +
			" (from_date_time between to_date(?,'dd-Mon-yyyy HH24:MI') and to_date(?,'dd-Mon-yyyy HH24:MI')) " +
			"and (to_date_time between to_date(?,'dd-Mon-yyyy HH24:MI') and to_date(?,'dd-Mon-yyyy HH24:MI'))";
			session.createSQLQuery(qry).setInteger(0, newMapDTO.getId()).setInteger(1, oldMapDTO.getId()).setString(2, df.format(oldMapDTO.getAllotmentFromDate())).setString(3, df.format(newMapDTO.getAllotmentToDate())).setString(4, df.format(oldMapDTO.getAllotmentFromDate())).setString(5, df.format(newMapDTO.getAllotmentToDate())).executeUpdate();
			session.flush();
			message=CPSConstants.UPDATE;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return message;
	}
	
	@SuppressWarnings("unchecked")
	public String deleteVehicleDriverDetails(MTApplicationBean mtBean) throws Exception {
		log.debug("deleteVehicleDriverDetails ---> method start");
		String message = "";
		Session session = null;
		List<MTVehicleAllocationtDetailsDTO> allotedList = null;
		try {
			session = hibernateUtils.getSession();
			allotedList = session.createCriteria(MTVehicleAllocationtDetailsDTO.class).add(Expression.eq("statusFlag", 8)).add(Expression.eq("vehicleDriverMapId", Integer.parseInt(mtBean.getPk()))).list();
			if(allotedList.size()>0){
				message = CPSConstants.FAILED;
			}else{
				session.createQuery("update MTVehicleDriverMapDTO set status=0 where id=" + Integer.parseInt(mtBean.getPk())).executeUpdate();
				message = CPSConstants.DELETE;
			}
		
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			//hibernateUtils.closeSession();
		}

		return message;
	}
	//for simultaneously saving dedicated vehicles in vehicle_allocation table 
	@SuppressWarnings("unchecked")
	public String saveDedicatedVehicles(MTVehicleDriverMapDTO vdDTO) throws Exception{
		log.debug("saveDedicatedVehicles ---> method start");
		String message = null;
		Session session = null;
		List<VehicleDetailsBean> vList = null;
		try {
			session = hibernateUtils.getSession();
			vList = session.createCriteria(VehicleDetailsBean.class).add(Expression.eq("vehicleId",vdDTO.getVehicleId())).add(Expression.eq("status", 1)).list();	
			if(vList.size()>0 && vList.get(0).getVehiclePoolType()==1){
				MTVehicleAllocationtDetailsDTO vaDTO = new MTVehicleAllocationtDetailsDTO();
				vaDTO.setFromDate(CPSUtils.getCurrentDateWithTime());
				//vaDTO.setSfID(vdList.get(0).getDedicatedPersonSfid());
				vaDTO.setVehicleDriverMapId(vdDTO.getId());
				vaDTO.setRequestId(Integer.parseInt(txRequestProcess.generateUniqueID(CPSConstants.MT_DEDICATED_VEHICLE)));
				vaDTO.setRequestType(5);
				//vaDTO.setStatusFlag(8);
				vaDTO.setVehicleFlag(4);
				session.saveOrUpdate(vaDTO);
				session.flush();
				session.clear();
			}
			
		}catch (Exception e) {
			message = CPSConstants.FAILED;
			e.printStackTrace();
			throw e;
		}
		log.debug("message ---> " + message);
		log.debug("saveDedicatedVehicles ---> method end");
		return message;
	} 
	
	//for changing mapid dynamically in vehicle allocation table
	@SuppressWarnings("unchecked")
	public String updateVehicleDriverMap(MTVehicleDriverMapDTO vdDTO) throws Exception{
		log.debug("updateVehicleDriverMap ---> method start");
		String message = null;
		Session session = null;
		List<MTVehicleAllocationtDetailsDTO> vaList = null;
		try {
			session = hibernateUtils.getSession();
			vaList = session.createCriteria(MTVehicleAllocationtDetailsDTO.class).add(Expression.eq("statusFlag", 8)).add(Expression.eq("vehicleDriverMapId", vdDTO.getId())).list();
			if(vaList.size()>0){
				MTVehicleAllocationtDetailsDTO oldDTO = (MTVehicleAllocationtDetailsDTO)vaList.get(0);
				oldDTO.setVehicleDriverMapId(vdDTO.getId());
				session.update(oldDTO);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	
	
	//for getting vehicle,driver absent lists
	@SuppressWarnings("unchecked")
	public List getDriverAbsentList(MTApplicationBean mtBean) throws Exception {
		log.debug("getDriverAbsentList --->method start");
		List<KeyValueDTO> driverList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			String startTime="";
			String endTime="";
			/*String qry = "select driver_id as key,to_char(from_date,'dd-Mon-yyyy HH24-MI') as name,to_char(to_date,'dd-Mon-yyyy HH24-MI') as value from mt_driver_absent_details where sysdate between from_date and to_date and status=1" ;
			if(mtBean.getFromDate() !=null && !mtBean.getFromDate().equals("")){
				qry = "select driver_id as key,to_char(from_date,'dd-Mon-yyyy HH24-MI') as name,to_char(to_date,'dd-Mon-yyyy HH24-MI') as value " +
						"  from mt_driver_absent_details where to_date('"+mtBean.getFromDate()+"','dd-Mon-yyyy HH24-MI') between from_date " +
						"  and to_date and status=1";
			}
			driverList = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();*/
			String qry = "SELECT driver_id AS key,TO_CHAR(from_date,'dd-Mon-yyyy HH24-MI') AS name,TO_CHAR(to_date,'dd-Mon-yyyy HH24-MI') AS value" +
					     " FROM mt_driver_absent_details WHERE ((from_date between to_date(?,'dd-Mon-yyyy HH24-MI') and " +
					     " to_date(?,'dd-Mon-yyyy HH24-MI')) or " +
					     " (to_date between to_date(?,'dd-Mon-yyyy HH24-MI') and " +
					     " to_date(?,'dd-Mon-yyyy HH24-MI'))) AND status=1";
			if(CPSUtils.compareString("getAvailableDrivers", mtBean.getParam())){
				 startTime = mtBean.getFromDate()+" 00:00";
				 endTime = mtBean.getFromDate()+" 23:59";
			}else{
			 startTime = mtBean.getCurrentDate()+" 00:00";
			 endTime = mtBean.getCurrentDate()+" 23:59";
			}
			driverList = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("value", Hibernate.STRING).setString(0, startTime).setString(1, endTime).setString(2, startTime).setString(3, endTime).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
			
		}catch (Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return driverList;
	}
	
	@SuppressWarnings("unchecked")
	public List getVehicleAbsentList(MTApplicationBean mtBean) throws Exception {
		log.debug("getVehicleAbsentList --->method start");
		List<KeyValueDTO> vehicleList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			/*String qry = "select vehicle_id as key,to_char(from_date,'dd-Mon-yyyy HH24-MI') as name,to_char(to_date,'dd-Mon-yyyy HH24-MI') as value  from mt_vehicle_absent_details where sysdate between from_date and to_date and status=1";
			if(mtBean.getFromDate() !=null && !mtBean.getFromDate().equals("")){
				qry = "select vehicle_id as key,to_char(from_date,'dd-Mon-yyyy HH24-MI') as name,to_char(to_date,'dd-Mon-yyyy HH24-MI') as value " +
						"  from mt_vehicle_absent_details where to_date('"+mtBean.getFromDate()+"','dd-Mon-yyyy HH24-MI') between from_date " +
						"  and to_date and status=1";
			}
			vehicleList = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();*/
		String qry="SELECT vehicle_id AS KEY,TO_CHAR(from_date,'dd-Mon-yyyy HH24-MI') AS name,TO_CHAR(to_date,'dd-Mon-yyyy HH24-MI') AS value" +
				   " FROM mt_vehicle_absent_details WHERE ((from_date between to_date(?,'dd-Mon-yyyy HH24-MI') and " +
				   " to_date(?,'dd-Mon-yyyy HH24-MI')) or " +
				   " (to_date between to_date(?,'dd-Mon-yyyy HH24-MI') and to_date(?,'dd-Mon-yyyy HH24-MI'))) AND status=1";
		String startTime = mtBean.getCurrentDate()+" 00:00";
		String endTime = mtBean.getCurrentDate()+" 23:59";
		vehicleList = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("value", Hibernate.STRING).setString(0, startTime).setString(1, endTime).setString(2, startTime).setString(3, endTime).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
		}catch (Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return vehicleList;
	}
	
	//for getting current available drivers excluding absent drivers,allocated drivers,
	@SuppressWarnings("unchecked")
	public List getCurrentAvailabeDrivers(MTApplicationBean mtBean) throws Exception{
		log.debug("getCurrentAvailabeDrivers  -->method start");
		List<KeyValueDTO> availableDriverList = null;
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			/*String qry ="select id as value,driver_name as name from mt_driver_master where id not in" +
					" (select driver_id from mt_driver_absent_details where sysdate between from_date and to_date)" +
					" and id not in" +
					" (select driver_id from mt_vehicle_driver_master) and status=1" +
					" union all" +
					" (select vdm.driver_id,dm.driver_name from mt_vehicle_driver_master vdm,mt_driver_master dm where vehicle_id in (" +
					" select vehicle_id from mt_vehicle_absent_details where sysdate between from_date and to_date)" +
					" and vdm.driver_id=dm.id and dm.status=1)";*/
			/*String qry ="select id as value,driver_name as name from mt_driver_master where id not in" +
					" (select driver_id from mt_driver_absent_details where sysdate between from_date and to_date and status=1)" +
					"  and id not in" +
					"  (select driver_id from mt_vehicle_driver_master where status_flag=1 and status=1) and status=1" +
					"  union all" +
					"  select driver_id as value,driver_name as name from mt_vehicle_driver_master vdm,mt_driver_master dm where driver_id in" +
					" (" +
					" select driver_id from mt_vehicle_driver_master where vehicle_id in(" +
					" select vehicle_id from mt_vehicle_absent_details where sysdate between from_date and to_date and status=1)" +
					" and status_flag=1 and status=1 and driver_id not in" +
					" (select driver_id from mt_vehicle_driver_master where vehicle_id not in(" +
					" select vehicle_id from mt_vehicle_absent_details where sysdate between from_date and to_date and status=1)" +
					"  and status_flag=1 and status=1)" +
					" and driver_id not in(select driver_id from mt_driver_absent_details where sysdate between from_date and to_date and status=1)"+
					"  and status_flag=1 and status=1) and vdm.status_flag=1 and vdm.status=1 and vdm.driver_id=dm.id and dm.status=1";*/
			//before add cnd in firstoff
			/*String qry="SELECT id AS value,driver_name AS name FROM mt_driver_master WHERE id NOT IN " +
			   "(SELECT driver_id FROM mt_driver_absent_details WHERE to_date(?,'dd-Mon-yyyy HH24-MI') BETWEEN from_date AND to_date" +
			   " AND status=1) AND id NOT IN"+
			   "(SELECT driver_id FROM MT_VEHICLE_DRIVER_MAP_DETAILS WHERE  status=1) AND status=1 " +
			   " UNION ALL " +
			   " SELECT driver_id AS value,driver_name AS name FROM MT_VEHICLE_DRIVER_MAP_DETAILS vdm,mt_driver_master dm " +
			   " WHERE driver_id IN " +
			   "(SELECT driver_id FROM MT_VEHICLE_DRIVER_MAP_DETAILS WHERE vehicle_id IN" +
			   "(SELECT vehicle_id FROM mt_vehicle_absent_details WHERE to_date(?,'dd-Mon-yyyy HH24-MI') BETWEEN from_date AND to_date AND status=1) " +
			   " and status=1 AND driver_id NOT IN " +
			   "(SELECT driver_id FROM mt_driver_absent_details WHERE to_date(?,'dd-Mon-yyyy HH24-MI') BETWEEN from_date AND to_date AND status=1)" +
			   " AND status=1" +
			   ") AND vdm.status=1 AND vdm.driver_id =dm.id AND dm.status=1";*/
			String qry="SELECT id AS value,driver_name AS name FROM mt_driver_master WHERE id NOT IN " +
					   "(SELECT driver_id FROM mt_driver_absent_details WHERE to_date(?,'dd-Mon-yyyy HH24-MI') BETWEEN from_date AND to_date" +
					   " AND status=1) AND id NOT IN"+
					   "(SELECT driver_id FROM MT_VEHICLE_DRIVER_MAP_DETAILS WHERE  status=1" +
					   " and allotment_from_date<=to_date(?,'dd-Mon-yyyy HH24-MI')) AND status=1  and driver_type!=2" +
					   " UNION ALL " +
					   " SELECT driver_id AS value,driver_name AS name FROM MT_VEHICLE_DRIVER_MAP_DETAILS vdm,mt_driver_master dm " +
					   " WHERE driver_id IN " +
					   "(SELECT driver_id FROM MT_VEHICLE_DRIVER_MAP_DETAILS WHERE vehicle_id IN" +
					   "(SELECT vehicle_id FROM mt_vehicle_absent_details WHERE to_date(?,'dd-Mon-yyyy HH24-MI') BETWEEN from_date AND to_date AND status=1) " +
					   " and status=1 AND driver_id NOT IN " +
					   "(SELECT driver_id FROM mt_driver_absent_details WHERE to_date(?,'dd-Mon-yyyy HH24-MI') BETWEEN from_date AND to_date AND status=1)" +
					   " AND status=1" +
					   ") AND vdm.status=1 AND vdm.driver_id =dm.id AND dm.status=1";
			String fromDateTime = mtBean.getFromDate()+" "+mtBean.getFromTime();
			availableDriverList =session.createSQLQuery(qry).addScalar("value",Hibernate.STRING).addScalar("name", Hibernate.STRING).setString(0, fromDateTime).setString(1, fromDateTime).setString(2, fromDateTime).setString(3, fromDateTime).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();	
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
				hibernateUtils.closeSession();
		}
		return availableDriverList;		
	}
	
	//for to check whether or not AbsentVehicle's driver Allocated To Other vehicle
	@SuppressWarnings("unchecked")
	public List getAbsentVehicleDrivers() throws Exception {
		log.debug("getAbsentVehicleDrivers  -->method start");
		List<KeyDTO> absentVehicleDriverList = null;
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			String qry = "select vehicle_id as key from mt_vehicle_driver_master where vehicle_id in(select vehicle_id from mt_vehicle_absent_details where sysdate between from_date and to_date and status=1) and status=1 and status_flag=1" +
					" intersect" +
					" select driver_id from mt_vehicle_driver_master where driver_id in" +
					" (select driver_id from mt_vehicle_driver_master where vehicle_id in(select vehicle_id from mt_vehicle_absent_details where sysdate between from_date and to_date and status=1) and status_flag=1 and status=1)" +
					" and driver_id in(select driver_id from mt_vehicle_driver_master where vehicle_id not in(select vehicle_id from mt_vehicle_absent_details where sysdate between from_date and to_date and status=1) and status_flag=1 and status=1)" +
					" and driver_id not in(select driver_id from mt_driver_absent_details where sysdate between from_date and to_date and status=1) and status_flag=1 and status=1";
			absentVehicleDriverList = session.createSQLQuery(qry).addScalar("key",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyDTO.class)).list();
		}catch (Exception e) {
		e.printStackTrace();
		throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return absentVehicleDriverList;
	}
	
	//for checking and updating vehicleId,driverId,statusFlag used in change driver screen 
	@SuppressWarnings("unchecked")
	public String checkVehicleDriverMap(MTApplicationBean mtBean) throws Exception {
		String message = "";
		Session session = null;
		List<MTVehicleDriverMapDTO> oldMapList = null;
		try{
			session = hibernateUtils.getSession();
			/*String sql1="SELECT ID FROM MT_VEHICLE_DRIVER_MASTER WHERE DRIVER_ID="+mtBean.getDriverId()+" AND STATUS=1 AND STATUS_FLAG=1";
			driverPkId = session.createSQLQuery(sql1).list();*/
			
			oldMapList = session.createCriteria(MTVehicleDriverMapDTO.class).add(Expression.eq("driverId",mtBean.getDriverId())).add(Expression.eq("status",1)).list();
			if(oldMapList.size()>0){
				MTVehicleDriverMapDTO newDTO = new MTVehicleDriverMapDTO();
				newDTO.setVehicleId(oldMapList.get(0).getVehicleId());
				newDTO.setStatus(1);
				newDTO.setLastModifiedBy(mtBean.getSfID());
				newDTO.setCreationDate(CPSUtils.getCurrentDate());
				newDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				int id = (Integer)session.save(newDTO);
				
				// for updating in allocation table start
					newDTO.setId(id);
					updateVehicleDriverMap(newDTO);
			//for updating in allocation table end
				
				String sql2 = "update MTVehicleDriverMapDTO set statusFlag=2 where id="+ oldMapList.get(0).getId();
				session.createQuery(sql2).executeUpdate();
				
			}
				String sql4 = "update MTVehicleDriverMapDTO set statusFlag=2 where id="+Integer.parseInt(mtBean.getPk()) ;
				session.createQuery(sql4).executeUpdate();
				message = CPSConstants.SUCCESS;
			//String sql4 = "update MTVehicleDriverMapDTO set statusFlag=2 where id="+Integer.parseInt(mtBean.getPk()) ;
			session.createQuery(sql4).executeUpdate();
			
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return message;
	}
	
	//for getting emp details used in request form
	@SuppressWarnings("unchecked")
	public EmployeeBean getEmployeeDetails(MTApplicationBean mtbBean) throws Exception {
		log.debug("getEmployeeDetails -->method start");
		Session session = null;
		EmployeeBean resBean = null;
		try{
			session = hibernateUtils.getSession();
			resBean = (EmployeeBean)session.createCriteria(EmployeeBean.class).add(Expression.eq("userSfid",mtbBean.getSfID())).add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return resBean;
	}
	
	
	//for checking the requester whether already requested for vehicle with same date or not 
	@SuppressWarnings("unchecked")
	public String checkVehicleRequesterDetails(MTApplicationBean mtBean) throws Exception {
		String message = "";
		Session session = null;
		List appList = null;
		StringBuffer sb = new StringBuffer();
		try{
			session = hibernateUtils.getSession();
			sb.append("select request_id from mt_vehicle_request_details where sfid='"+mtBean.getSfID()+"' and"+
					" travelling_date_time=to_date('"+mtBean.getTravellingDate()+"','dd-mon-yyyy HH24-MI') and "+
					" estimated_date_time =to_date('"+mtBean.getEstimatedDateTime()+"','dd-mon-yyyy HH24-MI')");
			if(!mtBean.getReturnDate().equals("") && !mtBean.getReturnEstimatedDateTime().equals("")){
				sb.append(" and return_travelling_date_time=to_date('"+mtBean.getReturnDate()+"','dd-mon-yyyy HH24-MI') and return_estimated_date_time =to_date('"+mtBean.getReturnEstimatedDateTime()+"','dd-mon-yyyy HH24-MI')");
			}
			appList = session.createSQLQuery(sb.toString()).list();
			if(appList.size()>0 && appList.size()==1){
				message = CPSConstants.DUPLICATE;
			}else{
				message = CPSConstants.SUCCESS;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return message;
	}
	
	//for getting requester details used in dashboard
	@SuppressWarnings("unchecked")
	public MTRequestDetailsDTO getVehicleApplicantDetails(String requestID) throws Exception {
		Session session = null;
		MTRequestDetailsDTO mtRequestDetailsDTO = null;
		try{
			session = hibernateUtils.getSession();
			//session.clear();
			mtRequestDetailsDTO = (MTRequestDetailsDTO)session.createCriteria(MTRequestDetailsDTO.class).add(Expression.eq("requestID", requestID)).uniqueResult();	
			if(!CPSUtils.isNullOrEmpty(mtRequestDetailsDTO)){
				//mtRequestDetailsDTO.setMtJourneyDetails(session.createCriteria(MTJourneyDetailsDTO.class).add(Expression.eq("referenceID", mtRequestDetailsDTO.getId())).add(Expression.eq("status", 1)).list());	
				mtRequestDetailsDTO.setMtJourneyDetails(session.createCriteria(MTJourneyDetailsDTO.class).add(Expression.eq("referenceID", mtRequestDetailsDTO.getId())).add(Expression.ne("status", 0)).list());
				if(mtRequestDetailsDTO.getMtJourneyDetails().size()>0){
					for(int i=0; i<mtRequestDetailsDTO.getMtJourneyDetails().size(); i++){
						mtRequestDetailsDTO.getMtJourneyDetails().get(i).setMtArticleDetails(session.createCriteria(MTArticleDetailsDTO.class).add(Expression.eq("referenceID", mtRequestDetailsDTO.getMtJourneyDetails().get(i).getId())).add(Expression.eq("status", 1)).list());
						
						
						String sql="select id as id from mt_vehicle_allocation_details where status_flag=25 and journey_id="+mtRequestDetailsDTO.getMtJourneyDetails().get(i).getId();
						//String sql="select id as id from mt_vehicle_allocation_details where (status_flag=8 or status_flag=25) and journey_id="+mtRequestDetailsDTO.getMtJourneyDetails().get(i).getId();
						
                       if(!CPSUtils.isNullOrEmpty(mtRequestDetailsDTO.getMtJourneyDetails().get(i).getAllocId())){
                    	
                    	 //mtRequestDetailsDTO.getMtJourneyDetails().get(i).setMtVehicleAllocationtDetailsDTO(session.createCriteria(MTVehicleAllocationtDetailsDTO.class).add(Expression.eq("journeyId", mtRequestDetailsDTO.getMtJourneyDetails().get(i).getId())).add(Expression.eq("statusFlag",25)).list());
                    	 
                    	   //changed bcz in journey table allocid saved
                    	   //mtRequestDetailsDTO.getMtJourneyDetails().get(i).setMtVehicleAllocationtDetailsDTO(session.createCriteria(MTVehicleAllocationtDetailsDTO.class).add(Expression.eq("id", Integer.parseInt(mtRequestDetailsDTO.getMtJourneyDetails().get(i).getAllocId()))).add(Expression.eq("statusFlag",25)).list());
                    	   
                    	   //for dash bord completed req(after release)
                    	   mtRequestDetailsDTO.getMtJourneyDetails().get(i).setMtVehicleAllocationtDetailsDTO(session.createCriteria(MTVehicleAllocationtDetailsDTO.class).add(Expression.eq("id", Integer.parseInt(mtRequestDetailsDTO.getMtJourneyDetails().get(i).getAllocId()))).add(Expression.or(Expression.eq("statusFlag",25), Expression.eq("statusFlag",8))).list());
                       }
						if((!CPSUtils.isNullOrEmpty(mtRequestDetailsDTO.getMtJourneyDetails().get(i).getMtVehicleAllocationtDetailsDTO())) && mtRequestDetailsDTO.getMtJourneyDetails().get(i).getMtVehicleAllocationtDetailsDTO().size()>0){
						if(CPSUtils.isNullOrEmpty(mtRequestDetailsDTO.getMtJourneyDetails().get(i).getMtVehicleAllocationtDetailsDTO().get(0).getVehicleDriverMapDetails())){
							mtRequestDetailsDTO.getMtJourneyDetails().get(i).getMtVehicleAllocationtDetailsDTO().get(0).setVehicleDriverMapDetails((MTVehicleDriverMapDTO)session.createCriteria(MTVehicleDriverMapDTO.class).add(Expression.eq("id", mtRequestDetailsDTO.getMtJourneyDetails().get(i).getMtVehicleAllocationtDetailsDTO().get(0).getVehicleDriverMapId())).uniqueResult());
						}
						}
					}
				}
			}
			
		}catch (Exception e) { 
			e.printStackTrace();
			System.out.println("sdSDGGFSDFgdfVBNDShghJGHFSDFf");
			throw e;
		}
		return mtRequestDetailsDTO;
		
	}
	
	//for getting Article Details
	/*@SuppressWarnings("unchecked")
	public List<ArticleDetailsBean> getArticleDetails(String requestId) throws Exception {
		Session session = null;
		List<ArticleDetailsBean> artiList = null;
		try{
			session = hibernateUtils.getSession();
			artiList = session.createCriteria(ArticleDetailsBean.class).add(Expression.eq("requestId", requestId)).add(Expression.eq("atricleFlag", 1)).list();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return artiList;
	}*/
	/*@SuppressWarnings("unchecked")
	public List<ArticleDetailsBean> getReturnArticleDetails(String requestId) throws Exception {
		Session session = null;
		List<ArticleDetailsBean> returnArtiList = null;
		try{
			session = hibernateUtils.getSession();
			returnArtiList = session.createCriteria(ArticleDetailsBean.class).add(Expression.eq("requestId", requestId)).add(Expression.eq("atricleFlag", 2)).list();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return returnArtiList;
	}*/
	//for getting freely available drivers,vehicles in dash board  
	/*@SuppressWarnings("unchecked")
	public MTVehicleDriverMapDTO getAvailableVehicleDriverList(String reqiestId) throws Exception {
		Session session = null;
		MTVehicleDriverMapDTO vdDto = new MTVehicleDriverMapDTO();
		List<MTVehicleDriverMapDTO> vdList1 = new ArrayList<MTVehicleDriverMapDTO>();
		List<MTVehicleDriverMapDTO> vdList2 = new ArrayList<MTVehicleDriverMapDTO>();
		List<KeyValueDTO> mapIdList1 = null;
		List<KeyValueDTO> mapIdList2 = null;
		try{
			session = hibernateUtils.getSession();
			MTVehicleRequestDetailsDTO mTVehicleRequestDetailsDTO=(MTVehicleRequestDetailsDTO)	session.createCriteria(MTVehicleRequestDetailsDTO.class).add(Expression.eq("requestID", reqiestId)).uniqueResult();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String tdate = format.format(mTVehicleRequestDetailsDTO.getTravellingDateTime());
			String edate = format.format(mTVehicleRequestDetailsDTO.getEstimatedDateTime());
			
			String sql="select vdm.id as id from mt_vehicle_driver_master vdm where vdm.status=1 and vdm.status_flag=1" +
					" and vdm.vehicle_id not in (select vehicle_id from mt_vehicle_absent_details " +
					" where ((to_date('"+tdate+"','yyyy-MM-dd HH24:MI') between from_date and to_date)" +
					" or (to_date('"+edate+"','yyyy-MM-dd HH24:MI') between from_date and to_date)" +
					" )and status=1)" +
					" and vdm.id not in (select vehicle_driver_map_id " +
					" from mt_vehicle_allocation_details  where " +
					" ((to_date('"+tdate+"','yyyy-MM-dd HH24:MI') between from_date_time and to_date_time)" +
					" or (to_date('"+edate+"','yyyy-MM-dd HH24:MI') between from_date_time and to_date_time)" +
					" and vehicle_flag=4  and status_flag=8))"+
					" and vdm.id not in(" +
					" select vehicle_driver_map_id  from mt_vehicle_allocation_details where to_date_time is null and " +
					" to_date('"+tdate+"','yyyy-MM-dd HH24:MI') > from_date_time and " +
					" to_date('"+edate+"','yyyy-MM-dd HH24:MI') > from_date_time and  vehicle_flag=4  and status_flag=8)";
			mapIdList1 = session.createSQLQuery(sql).addScalar("id", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
			if(mapIdList1.size()>0){
				 for(int i=0; i<mapIdList1.size(); i++){
						int mapId=mapIdList1.get(i).getId();
						MTVehicleDriverMapDTO MTVehicleDriverMapDTO = (MTVehicleDriverMapDTO)session.createCriteria(MTVehicleDriverMapDTO.class).add(Expression.eq("id", mapId)).add(Expression.eq("status", 1)).uniqueResult();
						//MTVehicleDriverMapDTO.setVehicleNo(MTVehicleDriverMapDTO.getVehicleDetails().getVehicleNo()+"-"+MTVehicleDriverMapDTO.getVehicleDetails().getVehicleName()+"("+MTVehicleDriverMapDTO.getVehicleDetails().getVehiclePoolTypeDetails().getVehiclePoolType()+")"+"("+MTVehicleDriverMapDTO.getVehicleDetails().getModelMasterDetails().getCategoryDetails().getCarriageDetails().getVehicleCarriageType()+")"+"("+MTVehicleDriverMapDTO.getVehicleDetails().getNoOfPeople()+")");
								vdList1.add(MTVehicleDriverMapDTO);
					}
			 }
			 if(mTVehicleRequestDetailsDTO.getReturnTravellingDateTime() != null && mTVehicleRequestDetailsDTO.getReturnEstimatedDateTime() != null){
					String	rtdate = format.format(mTVehicleRequestDetailsDTO.getReturnTravellingDateTime());
					String  redate = format.format(mTVehicleRequestDetailsDTO.getReturnEstimatedDateTime());
					
					String sql1="select vdm.id as id from mt_vehicle_driver_master vdm where vdm.status=1 and vdm.status_flag=1" +
					" and vdm.vehicle_id not in (select vehicle_id from mt_vehicle_absent_details " +
					" where ((to_date('"+rtdate+"','yyyy-MM-dd HH24:MI') between from_date and to_date)" +
					" or (to_date('"+redate+"','yyyy-MM-dd HH24:MI') between from_date and to_date)" +
					" )and status=1)" +
					" and vdm.id not in (select vehicle_driver_map_id " +
					" from mt_vehicle_allocation_details  where " +
					" ((to_date('"+rtdate+"','yyyy-MM-dd HH24:MI') between from_date_time and to_date_time)" +
					" or (to_date('"+redate+"','yyyy-MM-dd HH24:MI') between from_date_time and to_date_time)" +
					" and vehicle_flag=4  and status_flag=8))" +
					" and vdm.id not in(" +
					" select vehicle_driver_map_id  from mt_vehicle_allocation_details where to_date_time is null and " +
					" to_date('"+rtdate+"','yyyy-MM-dd HH24:MI') > from_date_time and " +
					" to_date('"+redate+"','yyyy-MM-dd HH24:MI') > from_date_time and  vehicle_flag=4  and status_flag=8)";
			mapIdList2 = session.createSQLQuery(sql1).addScalar("id", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
			 if(mapIdList2.size()>0){
				 for(int i=0; i<mapIdList2.size(); i++){
						int mapId=mapIdList2.get(i).getId();
						MTVehicleDriverMapDTO MTVehicleDriverMapDTO = (MTVehicleDriverMapDTO)session.createCriteria(MTVehicleDriverMapDTO.class).add(Expression.eq("id", mapId)).add(Expression.eq("statusFlag", 1)).add(Expression.eq("status", 1)).uniqueResult();
						//MTVehicleDriverMapDTO.setVehicleNo(MTVehicleDriverMapDTO.getVehicleDetails().getVehicleNo()+"-"+MTVehicleDriverMapDTO.getVehicleDetails().getVehicleName()+"("+MTVehicleDriverMapDTO.getVehicleDetails().getVehiclePoolTypeDetails().getVehiclePoolType()+")"+"("+MTVehicleDriverMapDTO.getVehicleDetails().getModelMasterDetails().getCategoryDetails().getCarriageDetails().getVehicleCarriageType()+")"+"("+MTVehicleDriverMapDTO.getVehicleDetails().getNoOfPeople()+")");
								vdList2.add(MTVehicleDriverMapDTO);
					}
			  }	
		   }
			
			
		
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return vdDto;
	}*/
	
	//for getting busy available drivers,vehicles in dash board  
	/*@SuppressWarnings("unchecked")
	public List getAllBusyVehicles(String requestId)throws Exception{
		Session session = null;
		List<MTVehicleDriverMapDTO> vdList = null;
		List<MTVehicleDriverMapDTO> vdList1 = new ArrayList<MTVehicleDriverMapDTO>();
		List mapIdList = null;
	    List<MTVehicleAllocationtDetailsDTO> vaList = null;
	   
		
		try{
			session = hibernateUtils.getSession();
			//vaList =  session.createCriteria(MTVehicleAllocationtDetailsDTO.class).add(Expression.eq("vehicleFlag", 4)).add(Expression.eq("statusFlag", 8)).add(Expression.isNotNull("fromDate")).add(Expression.isNotNull("toDate")).list();
			MTVehicleRequestDetailsDTO mTVehicleRequestDetailsDTO=(MTVehicleRequestDetailsDTO)	session.createCriteria(MTVehicleRequestDetailsDTO.class).add(Expression.eq("requestID", requestId)).uniqueResult();
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return vdList1;
	}*/
	//for Date Json(table in json)
	@SuppressWarnings("unchecked")
	public List getAvailableBusyVehicleDriverList() throws Exception {
		Session session = null;
		List<MTVehicleAllocationtDetailsDTO> vaList = null;
		List<MTVehicleDriverMapDTO> vdList = null;
		try{
			session = hibernateUtils.getSession();
			vaList = session.createCriteria(MTVehicleAllocationtDetailsDTO.class).add(Expression.eq("statusFlag", 8)).add(Expression.isNotNull("toDate")).addOrder(Order.asc("fromDate")).list();
			if(vaList.size()>0){
				for(int i=0; i<vaList.size(); i++){
					if(vaList.get(i).getVehicleFlag()==3){
						vaList.get(i).setVehicleStatus("<font color=green>Free</font>");
					}else if(vaList.get(i).getVehicleFlag()==4){
						vaList.get(i).setVehicleStatus("<font color=red>Busy</font>");
					}
				}	
			}
			/*if(vaList.size()>0){
				for(int i=0; i<vaList.size(); i++){
					vdList = session.createCriteria(MTVehicleDriverMapDTO.class).add(Expression.eq("id", vaList.get(i).getVehicleDriverMapId())).add(Expression.eq("statusFlag", 1)).add(Expression.eq("status", 1)).list();
					if(vdList !=null && vdList.size()>0 && vdList.get(0)!=null){
						vdList.get(0).setVehicleNo(vdList.get(0).getVehicleDetails().getVehicleNo()+"-"+vdList.get(0).getVehicleDetails().getVehicleName()+"("+vdList.get(0).getVehicleDetails().getVehiclePoolTypeDetails().getVehiclePoolType()+")"+"("+vdList.get(0).getVehicleDetails().getModelMasterDetails().getCategoryDetails().getCarriageDetails().getVehicleCarriageType()+")"+"("+vdList.get(0).getVehicleDetails().getNoOfPeople()+")");
					}
				}	
			}*/
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return vaList;
	}
	
	//for getting private vehicles in dash board  
	@SuppressWarnings("unchecked")
	public List<VehicleDetailsBean> getAllHiredVehiclesList() throws Exception{
		Session session = null;
		List< VehicleDetailsBean> hiredVehicleList = null;
		try{
			session = hibernateUtils.getSession();
			@SuppressWarnings("unused")
			List<VehicleDetailsBean> hiredAllotList = session.createSQLQuery("select id as vehicleId from mt_vehicle_master where travelling_request_id is not null or return_travelling_request_id is not null and status=1").addScalar("vehicleId", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(VehicleDetailsBean.class)).list();
			hiredVehicleList = session.createCriteria(VehicleDetailsBean.class).add(Expression.eq("status", 1)).add(Expression.eq("vehicleType", 2)).add(Expression.ne("vehiclePoolType", 1)).list();
			if(hiredVehicleList.size()>0){
				for(int i=0; i<hiredVehicleList.size(); i++){
					VehicleDetailsBean vehicleDetailsBean = hiredVehicleList.get(i);
					vehicleDetailsBean.setVehicleNoString(vehicleDetailsBean.getVehicleNo()+"-"+vehicleDetailsBean.getVehicleName()+"("+vehicleDetailsBean.getVehiclePoolTypeDetails().getVehiclePoolType()+")"+"("+vehicleDetailsBean.getModelMasterDetails().getCategoryDetails().getCarriageDetails().getVehicleCarriageType()+")"+"("+vehicleDetailsBean.getNoOfPeople()+")");
					if(hiredAllotList.size()>0){
						if(vehicleDetailsBean.getVehicleId() == hiredAllotList.get(0).getVehicleId()){
							hiredVehicleList.remove(i);
							i--;
						}
							
					}
					
				}
				
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return hiredVehicleList;
	}
	
	//for getting the allocated vehicles for particlar requested sfid
	/*@SuppressWarnings("unchecked")
	public List getAllocatedVehicleList(MTApplicationBean mtBean) throws Exception {
		Session session = null;
		List<MTVehicleAllocationtDetailsDTO> allocVehicleList = null;
		List<MTVehicleAllocationtDetailsDTO> allocVehicleList1 = new ArrayList<MTVehicleAllocationtDetailsDTO>();
		List<MTVehicleAllocationtDetailsDTO> dedicatedVehicleList = null;
		List checkIdList = null;
		List checkDedicatedIdList = null;
		try{
			session = hibernateUtils.getSession();
			String sql="select max(id) from mt_vehicle_allocation_details where id in(" +
					" select id from mt_vehicle_allocation_details where vehicle_flag=4 and status_flag=8 " +
					" and from_date_time is not null and to_date_time is null and vehicle_driver_map_id in" +
					" (SELECT ID FROM MT_VEHICLE_DRIVER_MASTER WHERE STATUS=1 AND STATUS_FLAG=1 AND VEHICLE_ID IN" +
					" (SELECT ID FROM MT_VEHICLE_MASTER WHERE STATUS=1 AND DEDICATED_PERSON_SFID='"+mtBean.getSfID()+"')))";
			String sql=" select id from mt_vehicle_allocation_details where vehicle_flag=4 and status_flag=8 and request_id in " +
					"(select request_id from mt_vehicle_request_details where sfid='"+mtBean.getSfID()+"')";
			checkIdList = session.createSQLQuery(sql).list();
			if(checkIdList.size()>0){
				for(int i=0; i<checkIdList.size(); i++){
					allocVehicleList = session.createCriteria(MTVehicleAllocationtDetailsDTO.class).add(Expression.eq("id",Integer.parseInt(checkIdList.get(i).toString()))).list();	
					if(allocVehicleList.size()>0){
						allocVehicleList1.add(allocVehicleList.get(0));
					}
				}
			}
			//for dedicated Vehicles
			String sql2 = "select id from mt_vehicle_allocation_details where vehicle_driver_map_id in" +
					" (select id from mt_vehicle_driver_master where vehicle_id in" +
					" (select id from mt_vehicle_master where status=1 and dedicated_person_sfid='"+mtBean.getSfID()+"')) " +
					" and vehicle_flag=4 and status_flag=8 and to_date(sysdate,'dd-MM-yyyy HH24-MI') >= to_date(from_date_time,'dd-MM-yyyy HH24-MI')";
			checkDedicatedIdList =session.createSQLQuery(sql2).list(); 
			if(checkDedicatedIdList.size()>0){
				for(int i=0; i<checkDedicatedIdList.size(); i++){
					dedicatedVehicleList = session.createCriteria(MTVehicleAllocationtDetailsDTO.class).add(Expression.eq("id",Integer.parseInt(checkDedicatedIdList.get(i).toString()))).list();	
					if(dedicatedVehicleList.size()>0){
						allocVehicleList1.add(dedicatedVehicleList.get(0));
					}
				}
			}
			
		 }catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return allocVehicleList1;
	}*/
	
	//for getting all allocated vehicles 
	@SuppressWarnings("unchecked")
	public List getAllAllocatedVehiclesList() throws Exception{
		Session session = null;
		List<MTVehicleAllocationtDetailsDTO> allotList = null;
		try{
			session = hibernateUtils.getSession();
			//allotList = session.createCriteria(MTVehicleAllocationtDetailsDTO.class).list();
			//allotList = session.createQuery("from MTVehicleAllocationtDetailsDTO where statusFlag=25 and journeyId!=0 and toDate!=null").list();
			String qry="select id from mt_vehicle_allocation_details where status_flag=25 and journey_id!=0 and to_date_time is not null and " +
					" journey_id not in(select id from mt_vehicle_journey_details where status=9)";
			List<BigDecimal> allocIdList=session.createSQLQuery(qry).list();
			List<Integer> ids=new ArrayList<Integer>();
			for(int i=0;i<allocIdList.size();i++){
				ids.add(allocIdList.get(i).intValue());
			}
			if(ids.size()>0){
			allotList = session.createCriteria(MTVehicleAllocationtDetailsDTO.class).add(Expression.in("id", ids)).addOrder(Order.asc("requestId")).list();
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return allotList;
	}
	@SuppressWarnings("unchecked")
	public List getDedicatedVehicles() throws Exception{
		Session session = null;
		List<MTVehicleAllocationtDetailsDTO> allotList = null;
		try{
			session = hibernateUtils.getSession();
			//SimpleDateFormat sd=new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat sd2=new SimpleDateFormat("yyyy-MM-dd");
			String currentDate=CPSUtils.getCurrentDate();
			Date d=new Date();
			//allotList = session.createCriteria(MTVehicleAllocationtDetailsDTO.class).list();
			
			allotList = session.createQuery("from MTVehicleAllocationtDetailsDTO where statusFlag=25 and toDate is null").list(); 
			session.clear();
			if(!CPSUtils.isNullOrEmpty(allotList)){
				for(int i=0;i<allotList.size();i++){
					if(CPSUtils.compareTwoDatesUptoDate(d, allotList.get(i).getFromDate())==1){
						allotList.get(i).setFlag("yes");
					}
			
					
				}
			
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return allotList;
	}
	
	@SuppressWarnings({ "static-access", "unchecked" })
	public String releaseAllSelectedVehicles(MTApplicationBean mtBean) throws Exception{
		Session session = null;
		String message ="";
		List<MTVehicleAllocationtDetailsDTO> aDTOList = null;
		try{
			session = hibernateUtils.getSession();
			
			String sql1 = "update mt_vehicle_allocation_details set vehicle_flag=?,status_flag=?,to_date_time=? where id=?";
			String sql2 = "update mt_vehicle_allocation_details set from_date_time=? where id=?";	
			
			String[] IdDateList = mtBean.getPk().split(",");
			
			for(int i=0; i<IdDateList.length; i++){
				if(!IdDateList[i].contains("@@")){
					String[] mapList = IdDateList[i].split("@"); 
					String[] idMapList = mapList[0].split("#"); 
					Object[] startEndDates = mtVehicleRequestProcess.getDateStartingTimeEndingTime(CPSUtils.formattedDateWithTime(mapList[1]),CPSUtils.formattedDateWithTime(mapList[1]));
					String qry = "select id as id,to_char(from_date_time,'dd-MM-yyyy HH24:MI') as stringFromDate,to_char(to_date_time,'dd-MM-yyyy HH24:MI') " +
				     " as stringToDate from mt_vehicle_allocation_details where vehicle_flag =3 and status_flag=8 and vehicle_driver_map_id="+Integer.parseInt(idMapList[1]) +
				     " and ((to_date('"+CPSUtils.formattedDateWithTime(mapList[1])+"','yyyy-MM-dd HH24:MI') between from_date_time and to_date_time)) ";
					aDTOList = session.createSQLQuery(qry).addScalar("id", Hibernate.INTEGER).addScalar("stringFromDate", Hibernate.STRING).addScalar("stringToDate", Hibernate.STRING)
					.setResultTransformer(Transformers.aliasToBean(MTVehicleAllocationtDetailsDTO.class)).list();
					
					if(aDTOList.size()>0){
						session.createSQLQuery(sql1).setInteger(0, 3).setInteger(1, 7).setTimestamp(2, new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(CPSUtils.formattedDateWithTime(mapList[1]))).setInteger(3,Integer.parseInt(idMapList[0])).executeUpdate();
						session.createSQLQuery(sql2).setTimestamp(0,(Date)startEndDates[2]).setInteger(1,aDTOList.get(0).getId()).executeUpdate();	
						message = CPSConstants.SUCCESS;
					}else{
						qry = "select id as id,to_char(from_date_time,'dd-MM-yyyy HH24:MI') as stringFromDate,to_char(to_date_time,'dd-MM-yyyy HH24:MI') " +
					     " as stringToDate from mt_vehicle_allocation_details where status_flag=8 and vehicle_driver_map_id="+Integer.parseInt(idMapList[1]) +
					     " and ((to_date('"+CPSUtils.formattedDateWithTime(mapList[1])+"','yyyy-MM-dd HH24:MI') between from_date_time and to_date_time)) ";
						aDTOList = session.createSQLQuery(qry).addScalar("id", Hibernate.INTEGER).addScalar("stringFromDate", Hibernate.STRING).addScalar("stringToDate", Hibernate.STRING)
						.setResultTransformer(Transformers.aliasToBean(MTVehicleAllocationtDetailsDTO.class)).list();
						
						session.createSQLQuery(sql1).setInteger(0, 3).setInteger(1, 7).setTimestamp(2, new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(CPSUtils.formattedDateWithTime(mapList[1]))).setInteger(3,Integer.parseInt(idMapList[0])).executeUpdate();
						MTVehicleAllocationtDetailsDTO mvaDto = new MTVehicleAllocationtDetailsDTO();
						mvaDto.setFromDate((Date)startEndDates[2]);
						mvaDto.setToDate(new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(mvaDto.getStringToDate()));
						mvaDto.setRequestType(5);
						mvaDto.setStatusFlag(8);
						mvaDto.setVehicleFlag(3);
						mvaDto.setVehicleDriverMapId(Integer.parseInt(idMapList[1]));
						session.save(mvaDto);
					}
					
				}else if(IdDateList[i].contains("@@")){
					String[] mapList = IdDateList[i].split("@@"); 
					String[] idMapList = mapList[0].split("@"); 
					Object[] startEndDates = mtVehicleRequestProcess.getDateStartingTimeEndingTime(CPSUtils.formattedDateWithTime(mapList[1]),CPSUtils.formattedDateWithTime(mapList[2]));
					session.createSQLQuery(sql1).setInteger(0, 3).setInteger(1, 7).setTimestamp(2, (Date)startEndDates[1]).setInteger(3,Integer.parseInt(idMapList[0])).executeUpdate();
					//for absent record
					MTVehicleAllocationtDetailsDTO mvaDto = new MTVehicleAllocationtDetailsDTO();
					mvaDto.setRequestId(Integer.parseInt(txRequestProcess.generateUniqueID(CPSConstants.MT_DEDICATED_VEHICLE)));
					mvaDto.setFromDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mapList[1]));
					mvaDto.setToDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mapList[2]));
					mvaDto.setRequestType(5);
					mvaDto.setStatusFlag(10);
					mvaDto.setVehicleFlag(10);
					mvaDto.setVehicleDriverMapId(Integer.parseInt(idMapList[0]));
					session.save(mvaDto);
					
					//for new record
					MTVehicleAllocationtDetailsDTO mvaDto1 = new MTVehicleAllocationtDetailsDTO();
					mvaDto1.setRequestId(Integer.parseInt(txRequestProcess.generateUniqueID(CPSConstants.MT_DEDICATED_VEHICLE)));
					mvaDto1.setFromDate((Date)startEndDates[2]);
					mvaDto1.setRequestType(5);
					mvaDto1.setStatusFlag(8);
					mvaDto1.setVehicleFlag(4);
					mvaDto1.setVehicleDriverMapId(Integer.parseInt(idMapList[0]));
					session.save(mvaDto1);
				}
				
			}
			
			
	
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return message;
	}
	@SuppressWarnings({ "unchecked", "static-access" })
	public String releaseAllAllocatedVehicles(MTApplicationBean mtBean) throws Exception{
		Session session = null;
		String message ="";
		List<MTVehicleAllocationtDetailsDTO> aDTOList = null;
		try{
			session = hibernateUtils.getSession();
			String sql1 = "update mt_vehicle_allocation_details set vehicle_flag=?,status_flag=?,to_date_time=? where id=?";
			String sql2 = "update mt_vehicle_allocation_details set from_date_time=? where id=?";	
			String[] IdDateList = mtBean.getPk().split(",");
			
			for(int i=0; i<IdDateList.length; i++){
				if(!IdDateList[i].contains("@@")){
					String[] mapList = IdDateList[i].split("@"); 
					String[] idMapList = mapList[0].split("#"); 
					Object[] startEndDates = mtVehicleRequestProcess.getDateStartingTimeEndingTime(CPSUtils.formattedDateWithTime(mapList[1]),CPSUtils.formattedDateWithTime(mapList[1]));
					String qry = "select id as id,to_char(from_date_time,'dd-MM-yyyy HH24:MI') as stringFromDate,to_char(to_date_time,'dd-MM-yyyy HH24:MI') " +
				     " as stringToDate from mt_vehicle_allocation_details where vehicle_flag =3 and status_flag=8 and vehicle_driver_map_id="+Integer.parseInt(idMapList[1]) +
				     " and ((to_date('"+CPSUtils.formattedDateWithTime(mapList[1])+"','yyyy-MM-dd HH24:MI') between from_date_time and to_date_time)) ";
					aDTOList = session.createSQLQuery(qry).addScalar("id", Hibernate.INTEGER).addScalar("stringFromDate", Hibernate.STRING).addScalar("stringToDate", Hibernate.STRING)
					.setResultTransformer(Transformers.aliasToBean(MTVehicleAllocationtDetailsDTO.class)).list();
					
					if(aDTOList.size()>0){
						session.createSQLQuery(sql1).setInteger(0, 3).setInteger(1, 7).setTimestamp(2, new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(CPSUtils.formattedDateWithTime(mapList[1]))).setInteger(3,Integer.parseInt(idMapList[0])).executeUpdate();
						session.createSQLQuery(sql2).setTimestamp(0,(Date)startEndDates[2]).setInteger(1,aDTOList.get(0).getId()).executeUpdate();	
						message = CPSConstants.SUCCESS;
					}else{
						qry = "select id as id,to_char(from_date_time,'dd-MM-yyyy HH24:MI') as stringFromDate,to_char(to_date_time,'dd-MM-yyyy HH24:MI') " +
					     " as stringToDate from mt_vehicle_allocation_details where status_flag=8 and vehicle_driver_map_id="+Integer.parseInt(idMapList[1]) +
					     " and ((to_date('"+CPSUtils.formattedDateWithTime(mapList[1])+"','yyyy-MM-dd HH24:MI') between from_date_time and to_date_time)) ";
						aDTOList = session.createSQLQuery(qry).addScalar("id", Hibernate.INTEGER).addScalar("stringFromDate", Hibernate.STRING).addScalar("stringToDate", Hibernate.STRING)
						.setResultTransformer(Transformers.aliasToBean(MTVehicleAllocationtDetailsDTO.class)).list();
						
						session.createSQLQuery(sql1).setInteger(0, 3).setInteger(1, 7).setTimestamp(2, new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(CPSUtils.formattedDateWithTime(mapList[1]))).setInteger(3,Integer.parseInt(idMapList[0])).executeUpdate();
						MTVehicleAllocationtDetailsDTO mvaDto = new MTVehicleAllocationtDetailsDTO();
						mvaDto.setFromDate((Date)startEndDates[2]);
						mvaDto.setToDate(new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(mvaDto.getStringToDate()));
						mvaDto.setRequestType(5);
						mvaDto.setStatusFlag(8);
						mvaDto.setVehicleFlag(3);
						mvaDto.setVehicleDriverMapId(Integer.parseInt(idMapList[1]));
						session.save(mvaDto);
					}
				}else if(IdDateList[i].contains("@@")){
					String[] mapList = IdDateList[i].split("@@"); 
					String[] idMapList = mapList[0].split("@"); 
					Object[] startEndDates = mtVehicleRequestProcess.getDateStartingTimeEndingTime(CPSUtils.formattedDateWithTime(mapList[1]),CPSUtils.formattedDateWithTime(mapList[2]));
					session.createSQLQuery(sql1).setInteger(0, 3).setInteger(1, 7).setTimestamp(2, (Date)startEndDates[1]).setInteger(3,Integer.parseInt(idMapList[0])).executeUpdate();
					//for absent record
					MTVehicleAllocationtDetailsDTO mvaDto = new MTVehicleAllocationtDetailsDTO();
					mvaDto.setRequestId(Integer.parseInt(txRequestProcess.generateUniqueID(CPSConstants.MT_DEDICATED_VEHICLE)));
					mvaDto.setFromDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mapList[1]));
					mvaDto.setToDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mapList[2]));
					mvaDto.setRequestType(5);
					mvaDto.setStatusFlag(10);
					mvaDto.setVehicleFlag(10);
					mvaDto.setVehicleDriverMapId(Integer.parseInt(idMapList[0]));
					session.save(mvaDto);
					
					//for new record
					MTVehicleAllocationtDetailsDTO mvaDto1 = new MTVehicleAllocationtDetailsDTO();
					mvaDto1.setRequestId(Integer.parseInt(txRequestProcess.generateUniqueID(CPSConstants.MT_DEDICATED_VEHICLE)));
					mvaDto1.setFromDate((Date)startEndDates[2]);
					mvaDto1.setRequestType(5);
					mvaDto1.setStatusFlag(8);
					mvaDto1.setVehicleFlag(4);
					mvaDto1.setVehicleDriverMapId(Integer.parseInt(idMapList[0]));
					session.save(mvaDto1);
				}
				
			}
			
			
	
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return message;
	}
	
	//kumari
	
	@SuppressWarnings("unchecked")
	public String releaseVehicles(MTApplicationBean mtBean) throws Exception{
		Session session = null;
		String message ="";
		List<MTVehicleAllocationtDetailsDTO> aDTOList = null;
		SimpleDateFormat df=new SimpleDateFormat("dd-MMM-yyyy HH:mm");
		try{
			
			session = hibernateUtils.getSession();
			/*String qry="select id from mt_vehicle_allocation_details where  (to_date(?,'dd-Mon-yyyy HH24-MI') between " +
					 " FROM_DATE_TIME AND TO_DATE_TIME ) OR (to_date(?,'dd-Mon-yyyy HH24-MI') between  " +
					 "FROM_DATE_TIME AND TO_DATE_TIME ) AND VEHICLE_DRIVER_MAP_ID=?";
			
			String qry2="update mt_vehicle_allocation_details set TO_DATE_TIME=to_date(?,'dd-Mon-yyyy HH24-MI'),STATUS_FLAG=8 where id=?" +
					    " and from_date_time=to_date(?,'dd-Mon-yyyy HH24-MI')";

			String qry3="update mt_vehicle_allocation_details set FROM_DATE_TIME=to_date(?,'dd-Mon-yyyy HH24-MI') where id=? and " +
					    "FROM_DATE_TIME<to_date(?,'dd-Mon-yyyy HH24-MI')";
			JSONObject jsonObj=new JSONObject(mtBean.getReleasedVehiclesJSON());
			for(int i=0;i<jsonObj.length();i++){
				JSONObject rowJson=new JSONObject(jsonObj.getString(String.valueOf(i)));
				mtBean.setFromDate(rowJson.getString("fromDateTime"));
				mtBean.setReleasedDate(rowJson.getString("relesedDateTime"));
				mtBean.setMapId(Integer.parseInt(rowJson.getString("mapId")));
				List<BigDecimal> idList=null;
				if(CPSUtils.isNullOrEmpty(rowJson.getString("toDateTime"))){
					String qry4="SELECT id FROM mt_vehicle_allocation_details WHERE (to_date(?,'dd-Mon-yyyy HH24-MI') " +
							   ">= FROM_DATE_TIME) AND VEHICLE_DRIVER_MAP_ID=?";
				 idList= session.createSQLQuery(qry4).setString(0, mtBean.getFromDate()).setInteger(1, mtBean.getMapId()).list();
					
				}else{
					 idList= session.createSQLQuery(qry).setString(0, mtBean.getFromDate()).setString(1, mtBean.getReleasedDate()).setInteger(2, mtBean.getMapId()).list();
				}
				List<BigDecimal> idList= session.createSQLQuery(qry).setString(0, mtBean.getFromDate()).setString(1, mtBean.getReleasedDate()).setInteger(2, mtBean.getMapId()).list();
				
				session.createSQLQuery(qry2).setString(0, mtBean.getReleasedDate()).setInteger(1, idList.get(0).intValue()).setString(2, mtBean.getFromDate()).executeUpdate();
				if(idList.size()>1){
				Calendar cal=Calendar.getInstance();
				cal.setTime(df.parse(mtBean.getReleasedDate()));
				cal.add(Calendar.MINUTE, 1);
				session.createSQLQuery(qry3).setString(0, df.format(cal.getTime())).setInteger(1, idList.get(1).intValue()).setString(2, mtBean.getReleasedDate()).executeUpdate();
			}
			}
			*/
			
			
			
			/*String qry1="update mt_vehicle_allocation_details set TO_DATE_TIME=to_date(?,'dd-Mon-yyyy HH24-MI'),STATUS_FLAG=8 where id=?";
			
			JSONObject jsonObj=new JSONObject(mtBean.getReleasedVehiclesJSON());
			String errMessage="";
			for(int i=0;i<jsonObj.length();i++){
				 errMessage="";
				JSONObject rowJson=new JSONObject(jsonObj.getString(String.valueOf(i)));
				String fromDateTime=rowJson.getString("fromDateTime");
				String toDateTime=rowJson.getString("toDateTime");
				String relesedDateTime=rowJson.getString("relesedDateTime");
				int mapId=Integer.parseInt(rowJson.getString("mapId"));
				int allocId=Integer.parseInt(rowJson.getString("allocId"));
				
				SimpleDateFormat df1=new SimpleDateFormat("yyyy-MM-dd HH:mm");
				
				//=====START:if allocated todate and released date is same day=====
				if(CPSUtils.compareTwoDatesUptoDate(df.parse(relesedDateTime),df.parse(toDateTime))==0){
					//if released date is after to the allocated todate(late release) 
					if(CPSUtils.compareTwoDatesUptoDateWithTime(df1.format(df.parse(relesedDateTime)), df1.format(df.parse(toDateTime)))==1){
						//check whether the vehicle is free at released time or not
					Object obj2 =session.createSQLQuery("select id from mt_vehicle_allocation_details where status_flag=25 AND vehicle_driver_map_id=?" +
							" and(to_date(?,'dd-Mon-yyyy HH24-MI') between from_date_time and to_date_time)" +
							" or (to_date_time is null and to_date(?,'dd-Mon-yyyy HH24-MI') >= from_date_time and " +
							" vehicle_driver_map_id=?)").setInteger(0, mapId).setString(1, relesedDateTime).setString(2, relesedDateTime).setInteger(3, mapId).uniqueResult();
					if(!CPSUtils.isNullOrEmpty(obj2)){
						int id=((BigDecimal)obj2).intValue();
						MTVehicleAllocationtDetailsDTO mDto = (MTVehicleAllocationtDetailsDTO)session.createCriteria(MTVehicleAllocationtDetailsDTO.class).add(Expression.eq("id", id)).uniqueResult();
						if(CPSUtils.isNullOrEmpty(mDto.getToDate())){
							errMessage = "VehicleNo "+mDto.getVehicleDriverMapDetails().getVehicleDetails().getVehicleNo()+" Is Busy From "+df.format(mDto.getFromDate())+" To Till Date";
						}else{
						errMessage = "VehicleNo "+mDto.getVehicleDriverMapDetails().getVehicleDetails().getVehicleNo()+" Is Busy From "+df.format(mDto.getFromDate())+" To "+df.format(mDto.getToDate());
					}
						message += " \n "+errMessage;
					}
					}
					//before release or correct time release
					if(errMessage==""){
						//update todatetime of specific released allocation record 
						session.createSQLQuery(qry1).setString(0, relesedDateTime).setInteger(1, allocId).executeUpdate();
						
						//check next record is free or not 
						Calendar cal=Calendar.getInstance();
						cal.setTime(df.parse(toDateTime));
						cal.add(Calendar.MINUTE, 1);
		               Object obj=session.createSQLQuery("select id from mt_vehicle_allocation_details where from_date_time=to_date(?,'dd-Mon-yyyy HH24-MI')" +
								" and vehicle_driver_map_id=? and status_flag=20").setString(0, df.format(cal.getTime())).setInteger(1, mapId).uniqueResult();
						if(!CPSUtils.isNullOrEmpty(obj)){
							//MTVehicleAllocationtDetailsDTO mAllocationtDetailsDTO =(MTVehicleAllocationtDetailsDTO)obj;
							int id=((BigDecimal)obj).intValue();
							cal.setTime(df.parse(relesedDateTime));
							cal.add(Calendar.MINUTE, 1);
							//update fromdatetime of next free record in allocation
						   session.createSQLQuery("update mt_vehicle_allocation_details set from_date_time=to_date(?,'dd-Mon-yyyy HH24-MI') where id=?").setString(0, df.format(cal.getTime())).setInteger(1, id).executeUpdate();
						}
					}
				}
				//=====END:if allocated todate and released date is same day=====
				
				
				//=====START:if allocated todate and released date are different days=====
				if(CPSUtils.compareTwoDatesUptoDate(df.parse(relesedDateTime),df.parse(toDateTime))!=0){
					//if released date is after to the allocated todate(late release) 
					if(CPSUtils.compareTwoDatesUptoDate(df.parse(relesedDateTime),df.parse(toDateTime))==1){
						
					}
					//before release or correct time release
					else if(CPSUtils.compareTwoDatesUptoDate(df.parse(relesedDateTime),df.parse(toDateTime))==-1){
						
					}
				}
				
				//=====END:if allocated todate and released date are different days=====
			}
			
			if(message==""){
				message = CPSConstants.RELEASEVEHICLE;
			}*/
			
			JSONObject jsonObj=new JSONObject(mtBean.getReleasedVehiclesJSON());
			for(int i=0;i<jsonObj.length();i++){
				JSONObject rowJson=new JSONObject(jsonObj.getString(String.valueOf(i)));
				String releasedDate=rowJson.getString("relesedDateTime");
			    int mapId=Integer.parseInt(rowJson.getString("mapId"));
				int allocId=Integer.parseInt(rowJson.getString("allocId"));
				
				String qry="update mt_vehicle_allocation_details set TO_DATE_TIME=to_date(?,'dd-Mon-yyyy HH24-MI'),STATUS_FLAG=8 where id=?";
				session.createSQLQuery(qry).setString(0, releasedDate).setInteger(1, allocId).executeUpdate();
				//session.flush();
			}
			message = CPSConstants.RELEASEVEHICLE;
		}catch(Exception e){
			message = "ReleaseVehicleFail";
			e.printStackTrace();
			throw e;
		}
		return message;
	}
	//for mileage
	@SuppressWarnings("unchecked")
	public List getMileageList()throws Exception{
		Session session = null;
		List<VehicleMileageDTO> vmList = null;
		try{
			session = hibernateUtils.getSession();
			vmList = session.createCriteria(VehicleMileageDTO.class).add(Expression.eq("status", 1)).addOrder(Order.desc("todayDate")).list();
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return vmList;
	}
	
	@SuppressWarnings("unchecked")
	public String saveMileageDetails(VehicleMileageDTO vmDTO) throws Exception{
		Session session = null;
		String message = "";
		List< VehicleMileageDTO> mileList = null;
		try{
			session = hibernateUtils.getSession();
				if(vmDTO.getId() !=0){
					session.saveOrUpdate(vmDTO);
					session.flush();
					session.clear();
					message = CPSConstants.UPDATE;
				}else{
					mileList = session.createCriteria(VehicleMileageDTO.class).add(Expression.eq("status", 1)).add(Expression.eq("vehicleId", vmDTO.getVehicleId())).add(Expression.eq("todayDate", vmDTO.getTodayDate())).list();
					if(mileList.size()==0){
						session.saveOrUpdate(vmDTO);
						session.flush();
						session.clear();
						message = CPSConstants.SUCCESS;
					}else{
						message = CPSConstants.EXIST;
					}
					
				}
		}catch ( Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return message;
	}
	public String deleteMileageDetails(MTApplicationBean mtBean) throws Exception{
		Session session = null;
		String message = "";
		try{
			session = hibernateUtils.getSession();
			session.createQuery("update VehicleMileageDTO set status=0 where id=" + Integer.parseInt(mtBean.getPk())).executeUpdate();
			message = CPSConstants.DELETE; 
			
		}catch ( Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return message;
	}
	@SuppressWarnings("unchecked")
	public List<YearTypeDTO> getYearList() throws Exception {
		Session session = null;
		List<YearTypeDTO> yearList = null;
		try{
			session = hibernateUtils.getSession();
			yearList =	session.createCriteria(YearTypeDTO.class).add(Expression.eq("status", 1)).addOrder(Order.desc("name")).list();
		}catch (Exception e) {
		e.printStackTrace();
		}
		return yearList;
	}
	@SuppressWarnings("unchecked")
	public List<FuelTypeDTO> getFuelTypeList() throws Exception {
		Session session = null;
		List< FuelTypeDTO> fuelTypeList = null;
		try{
			session = hibernateUtils.getSession();
			fuelTypeList = session.createCriteria(FuelTypeDTO.class).add(Expression.eq("status", 1)).list();
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return fuelTypeList;
	}
	
	public VehicleMileageDTO getPreDayClosingDetails(MTApplicationBean mtbean) throws Exception{
		Session session = null;
		VehicleMileageDTO vehicleMileageDTO = null;
		try{
			session = hibernateUtils.getSession();
			String sql = "select max(id)  from Mt_vehicle_mileage_master where vehicle_id="+Integer.parseInt(mtbean.getVehicleNo())+" and status=1" +
						" and to_day_date<to_date('"+mtbean.getTodayDate()+"','dd-Mon-yyyy')";
			Object mileId = session.createSQLQuery(sql).uniqueResult();
			if(!CPSUtils.isNullOrEmpty(mileId)){
				vehicleMileageDTO = (VehicleMileageDTO)session.createCriteria(VehicleMileageDTO.class).add(Expression.eq("id", ((BigDecimal)mileId).intValue())).add(Expression.eq("status", 1)).uniqueResult();
			}
			
			 
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return vehicleMileageDTO;
	}
	public List<VehicleMileageDTO> getMaxMeterReadings(MTApplicationBean mtbean) throws Exception{
		Session session = null;
		String message ="";
		VehicleMileageDTO vehicleMileageDTO = null;
		List<VehicleMileageDTO> meterList = new ArrayList<VehicleMileageDTO>(); 
		Object meterIDs = null;
		try{
			session = hibernateUtils.getSession();
			String sql = "select max(fuel_mrd) ,max(eng_oil_mrd),max(coolent_mrd) from mt_vehicle_mileage_master where status=1 and vehicle_id="+Integer.parseInt(mtbean.getVehicleNo());
			meterIDs = session.createSQLQuery(sql).uniqueResult();
			Object[] temp=(Object[])meterIDs;
			if(!CPSUtils.isNullOrEmpty(temp[0])){
				//vehicleMileageDTO=(VehicleMileageDTO)session.get(VehicleMileageDTO.class,(Integer)session.createCriteria(VehicleMileageDTO.class).add(Expression.eq("fuelMRD", ((BigDecimal)temp[0]).intValue())).add(Expression.eq("vehicleId", Integer.parseInt(mtbean.getVehicleNo()))).add(Expression.eq("status", 1)).setProjection(Projections.max("id")).uniqueResult());
				vehicleMileageDTO = (VehicleMileageDTO)session.createCriteria(VehicleMileageDTO.class).add(Expression.eq("fuelMRD", ((BigDecimal)temp[0]).intValue())).add(Expression.eq("vehicleId", Integer.parseInt(mtbean.getVehicleNo()))).add(Expression.eq("status", 1)).setProjection(Projections.max("id")).uniqueResult();	
				meterList.add(vehicleMileageDTO);
				session.flush();
			}
			if(!CPSUtils.isNullOrEmpty(temp[1])){
				//vehicleMileageDTO=(VehicleMileageDTO)session.get(VehicleMileageDTO.class,5);
				//vehicleMileageDTO=(VehicleMileageDTO)session.get(VehicleMileageDTO.class,(Integer)session.createCriteria(VehicleMileageDTO.class).add(Expression.eq("engineOilMRD", ((BigDecimal)temp[0]).intValue())).add(Expression.eq("vehicleId", Integer.parseInt(mtbean.getVehicleNo()))).add(Expression.eq("status", 1)).setProjection(Projections.max("id")).uniqueResult());
				vehicleMileageDTO = (VehicleMileageDTO)session.createCriteria(VehicleMileageDTO.class).add(Expression.eq("engineOilMRD", ((BigDecimal)temp[1]).intValue())).add(Expression.eq("vehicleId", Integer.parseInt(mtbean.getVehicleNo()))).add(Expression.eq("status", 1)).uniqueResult();	
				meterList.add(vehicleMileageDTO);
			}
			if(!CPSUtils.isNullOrEmpty(temp[2])){
				//vehicleMileageDTO=(VehicleMileageDTO)session.get(VehicleMileageDTO.class,(Integer)session.createCriteria(VehicleMileageDTO.class).add(Expression.eq("coolentMRD", ((BigDecimal)temp[0]).intValue())).add(Expression.eq("vehicleId", Integer.parseInt(mtbean.getVehicleNo()))).add(Expression.eq("status", 1)).setProjection(Projections.max("id")).uniqueResult());
				vehicleMileageDTO = (VehicleMileageDTO)session.createCriteria(VehicleMileageDTO.class).add(Expression.eq("coolentMRD", ((BigDecimal)temp[2]).intValue())).add(Expression.eq("vehicleId", Integer.parseInt(mtbean.getVehicleNo()))).add(Expression.eq("status", 1)).uniqueResult();	
				meterList.add(vehicleMileageDTO);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return meterList;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<MTVehicleAllocationtDetailsDTO> getVehicleFreeDetails(Date fdate, Date tdate, String mapId, Session session) throws Exception{
		
		List<MTVehicleAllocationtDetailsDTO> aDTOList = null;
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			String fsdate = format.format(fdate);
			String tsdate = format.format(tdate);
			String sql = "select id as id,to_char(from_date_time,'dd-MM-yyyy HH24:MI') as stringFromDate,to_char(to_date_time,'dd-MM-yyyy HH24:MI') " +
					     " as stringToDate from mt_vehicle_allocation_details where vehicle_flag =3 and status_flag=8 and vehicle_driver_map_id="+Integer.parseInt(mapId) +
					     " and ((to_date('"+fsdate+"','yyyy-MM-dd HH24:MI') between from_date_time and to_date_time) or "+
					     "(to_date('"+tsdate+"','yyyy-MM-dd HH24:MI') between from_date_time and to_date_time))";
			aDTOList = session.createSQLQuery(sql).addScalar("id", Hibernate.INTEGER).addScalar("stringFromDate", Hibernate.STRING).addScalar("stringToDate", Hibernate.STRING)
												.setResultTransformer(Transformers.aliasToBean(MTVehicleAllocationtDetailsDTO.class)).list();
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return aDTOList;
	}
	
	@SuppressWarnings("unchecked")
	public List<MTRequestDetailsDTO> getApprovedRequests() throws Exception{
		Session session = null;
		List<MTRequestDetailsDTO> mtAppReqList = null;
		try{
			session = hibernateUtils.getSession();
			session.clear();
			mtAppReqList = session.createCriteria(MTRequestDetailsDTO.class).add(Expression.in("status", new Integer[]{2,4})).addOrder(Order.desc("requestID")).list();
			for(int i=0; i<mtAppReqList.size(); i++){
				getVehicleApplicantDetails(mtAppReqList.get(i).getRequestID());	
			}
			
			//for getting only not allocated vehicles filter remining journies
			for (int j = 0; j < mtAppReqList.size(); j++) {
				List<MTJourneyDetailsDTO> journeyList=new ArrayList<MTJourneyDetailsDTO>();
				for (int k = 0; k < mtAppReqList.get(j).getMtJourneyDetails().size(); k++) {
					if (mtAppReqList.get(j).getMtJourneyDetails().get(k).getStatus() == 8 || mtAppReqList.get(j).getMtJourneyDetails().get(k).getStatus() == 9) {
						journeyList.add(mtAppReqList.get(j).getMtJourneyDetails().get(k));
						//mtAppReqList.get(j).getMtJourneyDetails().remove(k);
					}
				}
				mtAppReqList.get(j).getMtJourneyDetails().removeAll(journeyList);
			}
			List<MTRequestDetailsDTO> requestList=new ArrayList<MTRequestDetailsDTO>();
			for(int i=0;i<mtAppReqList.size();i++){
				if(mtAppReqList.get(i).getMtJourneyDetails().size()==0){
					requestList.add(mtAppReqList.get(i));
				}
			}
			mtAppReqList.removeAll(requestList);
			//session.evict(mtAppReqList);
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		session.clear();
		return mtAppReqList;
	}
	
	@SuppressWarnings("unchecked")
	//public List<MTVehicleDriverMapDTO> getAvailableVDListForParticularJourney(String journeyId) throws Exception{
	public List<MTVehicleDriverMapDTO> getAvailableVDListForParticularJourney(MTApplicationBean mtbean) throws Exception{
		Session session = null;
		List<MTVehicleDriverMapDTO> vdList = null;
		try{
			session = hibernateUtils.getSession();
			//MTJourneyDetailsDTO mtJourneyDetailsDTO = (MTJourneyDetailsDTO)session.createCriteria(MTJourneyDetailsDTO.class).add(Expression.eq("id", Integer.parseInt(journeyId.split("_")[1]))).uniqueResult();
			MTJourneyDetailsDTO mtJourneyDetailsDTO = (MTJourneyDetailsDTO)session.createCriteria(MTJourneyDetailsDTO.class).add(Expression.eq("id", Integer.parseInt(mtbean.getPk().split("_")[1]))).uniqueResult();
			SimpleDateFormat sd= new SimpleDateFormat("dd-MMM-yyyy HH:mm");
			String departureDate=sd.format(mtJourneyDetailsDTO.getDepartureDate());	
			mtJourneyDetailsDTO.setDepartureDateString(mtJourneyDetailsDTO.getDepartureDateString()+" "+mtJourneyDetailsDTO.getDepartureTime());
			mtJourneyDetailsDTO.setEstimatedDateString(mtJourneyDetailsDTO.getEstimatedDateString()+" "+mtJourneyDetailsDTO.getEstimatedTime());
		
			  
			
			
			
			//kumari********************before Live***********************************
			/*String qry="SELECT id FROM MT_VEHICLE_DRIVER_MAP_DETAILS vdm WHERE vdm.status=1 AND vdm.driver_id!=0 AND vdm.vehicle_Id NOT IN " +
					" (SELECT vehicle_Id FROM MT_VEHICLE_ABSENT_DETAILS WHERE ((to_date(?,'dd-Mon-yyyy HH24:MI')" +
					" BETWEEN from_Date AND to_Date) OR (to_date(?,'dd-Mon-yyyy HH24:MI') BETWEEN from_Date AND to_Date) ) " +
					" AND status=1) " +
					" AND vdm.driver_Id NOT IN (SELECT driver_Id FROM MT_DRIVER_ABSENT_DETAILS WHERE " +
					" ((to_date(?,'dd-Mon-yyyy HH24:MI') BETWEEN from_Date AND to_Date) OR " +
					" (to_date(?,'dd-Mon-yyyy HH24:MI') BETWEEN from_Date AND to_Date) ) AND status=1) " +
					" AND vdm.id NOT IN" +
					" (SELECT vehicle_Driver_Map_Id FROM MT_VEHICLE_ALLOCATION_DETAILS WHERE status_flag  =25 and " +
					" ( (to_date(?,'dd-Mon-yyyy HH24:MI') BETWEEN from_Date_TIME AND to_Date_TIME) or " +
					" (to_date(?,'dd-Mon-yyyy HH24:MI') BETWEEN from_Date_TIME AND to_Date_TIME)) OR TO_DATE_TIME IS NULL)" +
					"  AND (vdm.allotment_From_Date<to_date('"+departureDate+"','dd-Mon-yyyy HH24:MI') OR " +
					" vdm.allotment_From_Date=to_date('"+departureDate+"','dd-Mon-yyyy HH24:MI')) " +
					" union " +
					" SELECT id FROM MT_VEHICLE_DRIVER_MAP_DETAILS vdm WHERE vdm.status=1 AND vdm.id IN (SELECT vehicle_Driver_Map_Id FROM " +
					" MT_VEHICLE_ALLOCATION_DETAILS WHERE   status_Flag  =20 AND ((to_date('"+mtJourneyDetailsDTO.getDepartureDateString()+"','dd-Mon-yyyy HH24:MI') " +
					" BETWEEN from_Date_time AND to_Date_time) and (to_date('"+mtJourneyDetailsDTO.getEstimatedDateString()+"','dd-Mon-yyyy HH24:MI') BETWEEN " +
					" from_Date_time AND to_Date_time)))";*/
			//Narayana***************************added extra qry content due to busy vehicles are coming in free list(Line No:2360 to 2362)*****************************
					
			/*String qry="SELECT id FROM MT_VEHICLE_DRIVER_MAP_DETAILS vdm WHERE vdm.status=1 AND vdm.driver_id!=0 AND vdm.vehicle_Id NOT IN " +
					" (SELECT vehicle_Id FROM MT_VEHICLE_ABSENT_DETAILS WHERE ((to_date(?,'dd-Mon-yyyy HH24:MI')" +
					" BETWEEN from_Date AND to_Date) OR (to_date(?,'dd-Mon-yyyy HH24:MI') BETWEEN from_Date AND to_Date) ) " +
					" AND status=1) " +
					" AND vdm.driver_Id NOT IN (SELECT driver_Id FROM MT_DRIVER_ABSENT_DETAILS WHERE " +
					" ((to_date(?,'dd-Mon-yyyy HH24:MI') BETWEEN from_Date AND to_Date) OR " +
					" (to_date(?,'dd-Mon-yyyy HH24:MI') BETWEEN from_Date AND to_Date) ) AND status=1) " +
					" AND vdm.id NOT IN" +
					" (SELECT vehicle_Driver_Map_Id FROM MT_VEHICLE_ALLOCATION_DETAILS WHERE status_flag  =25 and " +
					" ( (to_date(?,'dd-Mon-yyyy HH24:MI') BETWEEN from_Date_TIME AND to_Date_TIME) or " +
					" (to_date(?,'dd-Mon-yyyy HH24:MI') BETWEEN from_Date_TIME AND to_Date_TIME)) " +
					"  Or" +
					"  ( From_Date_Time Between To_Date('"+mtJourneyDetailsDTO.getDepartureDateString()+"','dd-Mon-yyyy HH24:MI') And To_Date('"+mtJourneyDetailsDTO.getEstimatedDateString()+"','dd-Mon-yyyy HH24:MI') " +
					"  or To_Date_Time  Between To_Date('"+mtJourneyDetailsDTO.getDepartureDateString()+"','dd-Mon-yyyy HH24:MI') And To_Date('"+mtJourneyDetailsDTO.getEstimatedDateString()+"','dd-Mon-yyyy HH24:MI'))" +
					"  OR TO_DATE_TIME IS NULL)" +
					"  AND (vdm.allotment_From_Date<to_date('"+departureDate+"','dd-Mon-yyyy HH24:MI') OR " +
					" vdm.allotment_From_Date=to_date('"+departureDate+"','dd-Mon-yyyy HH24:MI')) " +
					" union " +
					" SELECT id FROM MT_VEHICLE_DRIVER_MAP_DETAILS vdm WHERE vdm.status=1 AND vdm.id IN (SELECT vehicle_Driver_Map_Id FROM " +
					" MT_VEHICLE_ALLOCATION_DETAILS WHERE   status_Flag  =20 AND ((to_date('"+mtJourneyDetailsDTO.getDepartureDateString()+"','dd-Mon-yyyy HH24:MI') " +
					" BETWEEN from_Date_time AND to_Date_time) and (to_date('"+mtJourneyDetailsDTO.getEstimatedDateString()+"','dd-Mon-yyyy HH24:MI') BETWEEN " +
					" from_Date_time AND to_Date_time)))";
		  List<BigDecimal> idList = session.createSQLQuery(qry).setString(0, mtJourneyDetailsDTO.getDepartureDateString()).setString(1, mtJourneyDetailsDTO.getEstimatedDateString()).setString(2, mtJourneyDetailsDTO.getDepartureDateString()).setString(3, mtJourneyDetailsDTO.getEstimatedDateString()).setString(4, mtJourneyDetailsDTO.getDepartureDateString()).setString(5, mtJourneyDetailsDTO.getEstimatedDateString()).list();*/
		
			//	search by date tyme 
			String fromDateTime="";
				String	toDateTime="";
				if( !(CPSUtils.isNullOrEmpty(mtbean.getSearchFromDate()) && CPSUtils.isNullOrEmpty(mtbean.getSearchFromTime()) && CPSUtils.isNullOrEmpty(mtbean.getSearchToDate()) && CPSUtils.isNullOrEmpty(mtbean.getSearchToTime())) ){
					fromDateTime=mtbean.getSearchFromDate()+" "+mtbean.getSearchFromTime();
					toDateTime=	mtbean.getSearchToDate()+" "+mtbean.getSearchToTime();
				}else{
					fromDateTime=mtJourneyDetailsDTO.getDepartureDateString();
					toDateTime=mtJourneyDetailsDTO.getEstimatedDateString();
				}
				
			String qry="SELECT id FROM MT_VEHICLE_DRIVER_MAP_DETAILS vdm WHERE vdm.status=1 AND vdm.driver_id!=0 AND vdm.vehicle_Id NOT IN " +
			" (SELECT vehicle_Id FROM MT_VEHICLE_ABSENT_DETAILS WHERE ((to_date('"+fromDateTime+"','dd-Mon-yyyy HH24:MI')" +
			" BETWEEN from_Date AND to_Date) OR (to_date('"+toDateTime+"','dd-Mon-yyyy HH24:MI') BETWEEN from_Date AND to_Date) ) " +
			" AND status=1) " +
			" AND vdm.driver_Id NOT IN (SELECT driver_Id FROM MT_DRIVER_ABSENT_DETAILS WHERE " +
			" ((to_date('"+fromDateTime+"','dd-Mon-yyyy HH24:MI') BETWEEN from_Date AND to_Date) OR " +
			" (to_date('"+toDateTime+"','dd-Mon-yyyy HH24:MI') BETWEEN from_Date AND to_Date) ) AND status=1) " +
			" AND vdm.id NOT IN" +
			" (SELECT vehicle_Driver_Map_Id FROM MT_VEHICLE_ALLOCATION_DETAILS WHERE status_flag  =25 and " +
			" ( (to_date('"+fromDateTime+"','dd-Mon-yyyy HH24:MI') BETWEEN from_Date_TIME AND to_Date_TIME) or " +
			" (to_date('"+toDateTime+"','dd-Mon-yyyy HH24:MI') BETWEEN from_Date_TIME AND to_Date_TIME)) " +
			"  Or" +
			"  ( From_Date_Time Between To_Date('"+fromDateTime+"','dd-Mon-yyyy HH24:MI') And To_Date('"+toDateTime+"','dd-Mon-yyyy HH24:MI') " +
			"  or To_Date_Time  Between To_Date('"+fromDateTime+"','dd-Mon-yyyy HH24:MI') And To_Date('"+toDateTime+"','dd-Mon-yyyy HH24:MI'))" +
			"  OR TO_DATE_TIME IS NULL)" +
			"  AND (vdm.allotment_From_Date<to_date('"+fromDateTime+"','dd-Mon-yyyy HH24:MI') OR " +
			" vdm.allotment_From_Date=to_date('"+fromDateTime+"','dd-Mon-yyyy HH24:MI')) " +
			" union " +
			" SELECT id FROM MT_VEHICLE_DRIVER_MAP_DETAILS vdm WHERE vdm.status=1 AND vdm.id IN (SELECT vehicle_Driver_Map_Id FROM " +
			" MT_VEHICLE_ALLOCATION_DETAILS WHERE   status_Flag  =20 AND ((to_date('"+fromDateTime+"','dd-Mon-yyyy HH24:MI') " +
			" BETWEEN from_Date_time AND to_Date_time) and (to_date('"+toDateTime+"','dd-Mon-yyyy HH24:MI') BETWEEN " +
			" from_Date_time AND to_Date_time))" +
			" minus SELECT vehicle_Driver_Map_Id FROM MT_VEHICLE_ALLOCATION_DETAILS  WHERE status_Flag =25 AND " +
			" (((to_date('"+fromDateTime+"','dd-Mon-yyyy HH24:MI') BETWEEN from_Date_time AND to_Date_time)" +
			" or (to_date('"+toDateTime+"','dd-Mon-yyyy HH24:MI') BETWEEN from_Date_time AND to_Date_time))" +
			"  OR ( From_Date_Time BETWEEN To_Date('"+fromDateTime+"','dd-Mon-yyyy HH24:MI') " +
			" AND To_Date('"+toDateTime+"','dd-Mon-yyyy HH24:MI') OR " +
			" To_Date_Time BETWEEN To_Date('"+fromDateTime+"','dd-Mon-yyyy HH24:MI')" +
			" AND To_Date('"+toDateTime+"','dd-Mon-yyyy HH24:MI')))"+
			") AND vdm.vehicle_Id NOT IN(SELECT vehicle_Id FROM MT_VEHICLE_ABSENT_DETAILS " +
			" WHERE ((to_date('"+fromDateTime+"','dd-Mon-yyyy HH24:MI') BETWEEN from_Date AND to_Date) OR " +
			" (to_date('"+toDateTime+"','dd-Mon-yyyy HH24:MI') BETWEEN from_Date AND to_Date) ) AND status=1) AND vdm.driver_Id NOT IN " +
			" (SELECT driver_Id FROM MT_DRIVER_ABSENT_DETAILS WHERE ((to_date('"+fromDateTime+"','dd-Mon-yyyy HH24:MI') " +
			" BETWEEN from_Date AND to_Date) OR (to_date('"+toDateTime+"','dd-Mon-yyyy HH24:MI') BETWEEN from_Date AND to_Date) )" +
			" AND status=1)";
		
  List<BigDecimal> idList = session.createSQLQuery(qry).list();
					
			List<Integer> ids=new ArrayList<Integer>();
			for(int i=0;i<idList.size();i++){
				ids.add(idList.get(i).intValue());
			}
			if(ids.size()>0){
			vdList = session.createCriteria(MTVehicleDriverMapDTO.class).add(Expression.in("id", ids)).createAlias("diverDetails", "driver").addOrder(Order.asc("driver.driverName")).list();
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return vdList;
	}
	public List<MTVehicleAllocationtDetailsDTO> getVDListForCombineAlloc(MTApplicationBean mtbean) throws Exception{
		Session session = null;
		List<MTVehicleAllocationtDetailsDTO> allocList = null;
		try{
			session = hibernateUtils.getSession();
			/*MTJourneyDetailsDTO mtJourneyDetailsDTO = (MTJourneyDetailsDTO)session.createCriteria(MTJourneyDetailsDTO.class).add(Expression.eq("id", Integer.parseInt(journeyId.split("_")[1]))).uniqueResult();
			SimpleDateFormat sd= new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat sdt= new SimpleDateFormat("dd-MMM-yyyy HH:mm");
			//if req is in same day get all busy records of that day
			if(CPSUtils.compareTwoDatesUptoDate(mtJourneyDetailsDTO.getDepartureDate(), mtJourneyDetailsDTO.getEstimatedDate())==0){
				Date fromDate=sdt.parse(sd.format(mtJourneyDetailsDTO.getDepartureDate()).concat(" 00:01"));
				Date toDate=sdt.parse(sd.format(mtJourneyDetailsDTO.getDepartureDate()).concat(" 23:59"));
				allocList=session.createCriteria(MTVehicleAllocationtDetailsDTO.class).add(Expression.eq("statusFlag", 25)).add(Expression.ne("journeyId", 0)).add(Expression.between("fromDate",fromDate,toDate)).add(Expression.between("toDate",fromDate,toDate)).list();
			}
			//if req is in diff days get all busy req between fromdate starting to to date ending
			else if(CPSUtils.compareTwoDatesUptoDate(mtJourneyDetailsDTO.getDepartureDate(), mtJourneyDetailsDTO.getEstimatedDate())!=0){
				Date fromDate=sdt.parse(sd.format(mtJourneyDetailsDTO.getDepartureDate()).concat(" 00:01"));
				Date toDate=sdt.parse(sd.format(mtJourneyDetailsDTO.getEstimatedDate()).concat(" 23:59"));
				allocList=session.createCriteria(MTVehicleAllocationtDetailsDTO.class).add(Expression.eq("statusFlag", 25)).add(Expression.ne("journeyId", 0)).add(Expression.between("fromDate",fromDate,toDate)).add(Expression.between("toDate",fromDate,toDate)).list();
			}*/
			
			//provide search functionality
			SimpleDateFormat sd= new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat sdt= new SimpleDateFormat("dd-MMM-yyyy HH:mm");
			//if req is in same day get all busy records of that day
			String searchFromDateTime=mtbean.getSearchFromDate()+" "+mtbean.getSearchFromTime();
			String searchToDateTime=mtbean.getSearchToDate()+" "+mtbean.getSearchToTime();
			if(CPSUtils.compareTwoDatesUptoDate(sd.parse(searchFromDateTime), sd.parse(searchToDateTime))==0){
				Date fromDate=sdt.parse(mtbean.getSearchFromDate().concat(" 00:01"));
				Date toDate=sdt.parse(mtbean.getSearchFromDate().concat(" 23:59"));
				allocList=session.createCriteria(MTVehicleAllocationtDetailsDTO.class).add(Expression.eq("statusFlag", 25)).add(Expression.ne("journeyId", 0)).add(Expression.between("fromDate",fromDate,toDate)).add(Expression.between("toDate",fromDate,toDate)).list();
			}
			//if req is in diff days get all busy req between fromdate starting to to date ending
			else if(CPSUtils.compareTwoDatesUptoDate(sd.parse(searchFromDateTime), sd.parse(searchToDateTime))!=0){
				Date fromDate=sdt.parse(mtbean.getSearchFromDate().concat(" 00:01"));
				Date toDate=sdt.parse(mtbean.getSearchToDate().concat(" 23:59"));
				allocList=session.createCriteria(MTVehicleAllocationtDetailsDTO.class).add(Expression.eq("statusFlag", 25)).add(Expression.ne("journeyId", 0)).add(Expression.between("fromDate",fromDate,toDate)).add(Expression.between("toDate",fromDate,toDate)).list();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return allocList;
	}
	/*public List<MTVehicleAllocationtDetailsDTO> getAllocationDetails(MTApplicationBean mtbean) throws Exception{
		String message = "";
		Session session = null;
		List<MTVehicleAllocationtDetailsDTO> allocationList = null;
		try{
			session=hibernateUtils.getSession();
			String qry="select * from mt_vehicle_allocation_details where (to_date('28-Aug-2013 14:00','dd-Mon-yyyy HH24:MI') between " +
			" from_date_time and to_date_time) and (to_date('28-Aug-2013 18:00','dd-Mon-yyyy HH24:MI') between from_date_time and to_date_time)";
			allocationList=session.createSQLQuery(qry).set
		}catch(Exception e){
			e.printStackTrace();
		}
		return allocationList;
	}*/
	
	
	public String saveVehicleAllocationDetails(MTVehicleAllocationtDetailsDTO mtVehicleAllocationtDetailsDTO) throws Exception{
		String message = "";
		Session session = null;
		try{
			session=hibernateUtils.getSession();
			//Transaction tx = session.beginTransaction();
			session.saveOrUpdate(mtVehicleAllocationtDetailsDTO);
			session.flush();
			session.clear();
			//tx.commit();
			
			if(mtVehicleAllocationtDetailsDTO.getStatusFlag()==25){
				session.createQuery("update MTJourneyDetailsDTO set status=8 where id="+mtVehicleAllocationtDetailsDTO.getJourneyId()).executeUpdate();
			}
			session.flush();
			message="allocateSuccess";
		}catch(Exception e){
			e.printStackTrace();
			message="allocateFail";
		}
		return message;
	}
	
	public String updateJourneyDetails(int journeyId,int allocId) throws Exception{
		String message = "";
		Session session = null;
		try{
			session=hibernateUtils.getSession();
			session.createQuery("update MTJourneyDetailsDTO set allocId=? where id=?").setString(0, String.valueOf(allocId)).setString(1, String.valueOf(journeyId)).executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return message;
	}
	public MTVehicleAllocationtDetailsDTO getVehicleAllocationDetails(MTApplicationBean mtbean) throws Exception{
		Session session = null;
		MTVehicleAllocationtDetailsDTO mtVehicleAllocationtDetailsDTO=null;
		try{
			session=hibernateUtils.getSession();
			
			
			String qry="select id from mt_vehicle_allocation_details where vehicle_driver_map_id=? and status_flag=20 and" +
			   " (to_date(?,'dd-Mon-yyyy HH24-MI') between from_date_time and to_date_time) and" +
			   " (to_date(?,'dd-Mon-yyyy HH24-MI') between from_date_time and to_date_time)";
	
	        Object obj=session.createSQLQuery(qry).setInteger(0, mtbean.getMapId()).setString(1, mtbean.getFromDate()+" "+mtbean.getFromTime()).setString(2, mtbean.getToDate()+" "+mtbean.getToTime()).uniqueResult();
			
	        if(!CPSUtils.isNullOrEmpty(obj)){
	        	int id=((BigDecimal)obj).intValue();
	        	mtVehicleAllocationtDetailsDTO=(MTVehicleAllocationtDetailsDTO)session.createCriteria(MTVehicleAllocationtDetailsDTO.class).add(Expression.eq("id", id)).uniqueResult();
	        	session.createQuery("delete MTVehicleAllocationtDetailsDTO where id="+id).executeUpdate();
	        }
	        /*mtVehicleAllocationtDetailsDTO=(MTVehicleAllocationtDetailsDTO)session.createQuery("from MTVehicleAllocationtDetailsDTO " +
					"where vehicleDriverMapId=? and statusFlag=20 and (to_date(?,'dd-Mon-yyyy HH24-MI')  between " +
					"fromDate and toDate and to_date(?,'dd-Mon-yyyy HH24-MI')  between fromDate and " +
					"toDate)").setInteger(0, mtbean.getMapId()).setString(1, mtbean.getFromDate()+" "+mtbean.getFromTime()).setString(2, mtbean.getToDate()+" "+mtbean.getToTime()).uniqueResult();
			if(!CPSUtils.isNullOrEmpty(mtVehicleAllocationtDetailsDTO)){
				session.createQuery("delete MTVehicleAllocationtDetailsDTO where id="+mtVehicleAllocationtDetailsDTO.getId()).executeUpdate();
			}*/
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return mtVehicleAllocationtDetailsDTO;
		
	}
	public List<MTVehicleAllocationtDetailsDTO> getVehicleAllocationDetailsForDiffDates(MTApplicationBean mtbean) throws Exception{
		Session session = null;
		List<MTVehicleAllocationtDetailsDTO> allocationtDetailsList=null;
		try{
			session=hibernateUtils.getSession();
			
			allocationtDetailsList=session.createQuery("from MTVehicleAllocationtDetailsDTO " +
					"where vehicleDriverMapId=? and statusFlag=20 and (to_date(?,'dd-Mon-yyyy HH24-MI')  between " +
					"fromDate and toDate or to_date(?,'dd-Mon-yyyy HH24-MI')  between fromDate and " +
					"toDate)").setInteger(0, mtbean.getMapId()).setString(1, mtbean.getFromDate()+" "+mtbean.getFromTime()).setString(2, mtbean.getToDate()+" "+mtbean.getToTime()).list();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return allocationtDetailsList;
		
	}
	//kumari
	public  MTVehicleDriverMapDTO getVehicleDriverMapDetails(MTApplicationBean mtbean) throws Exception{
		Session session = null;
		MTVehicleDriverMapDTO mtVehicleDriverMapDTO=null;
		try{
			session=hibernateUtils.getSession();
			//session = hibernateUtils.openNewSession();
			mtVehicleDriverMapDTO = new MTVehicleDriverMapDTO();
			Object obj = session.createQuery("from MTVehicleDriverMapDTO where status=1 and id="+Integer.parseInt(mtbean.getPk())).uniqueResult();	
			if(!CPSUtils.isNullOrEmpty(obj)){
				mtVehicleDriverMapDTO=(MTVehicleDriverMapDTO)obj;
			}
			session.clear();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}/*finally{
			hibernateUtils.closeSession();
		}*/
		
		return mtVehicleDriverMapDTO;
	}
	public MTVehicleAllocationtDetailsDTO getVehicleAllocationDetailsForPerticulerDate(String date,int mapId) throws Exception{
		Session session = null;
		MTVehicleAllocationtDetailsDTO mtVehicleAllocationtDetailsDTO=null;
		try{
			session=hibernateUtils.getSession();
			mtVehicleAllocationtDetailsDTO =new MTVehicleAllocationtDetailsDTO();
			mtVehicleAllocationtDetailsDTO=(MTVehicleAllocationtDetailsDTO)session.createQuery("from MTVehicleAllocationtDetailsDTO" +
					" where vehicleDriverMapId=? and statusFlag=20 and to_date(?,'dd-Mon-yyyy HH24-MI')  between" +
					" fromDate and toDate").setInteger(0, mapId).setString(1, date).uniqueResult();
			if(!CPSUtils.isNullOrEmpty(mtVehicleAllocationtDetailsDTO)){
				session.createQuery("delete MTVehicleAllocationtDetailsDTO where id="+mtVehicleAllocationtDetailsDTO.getId()).executeUpdate();
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return mtVehicleAllocationtDetailsDTO;
		
	}
	public String getEmpExist(String sfid) throws Exception {
		Session session = null;
		String message = "NO";
		try {
			session=hibernateUtils.getSession();
             
			if (Integer.valueOf(session.createSQLQuery("select count(*) from emp_master where sfid=? and status=1 ").setString(0, sfid).uniqueResult().toString()) > 0) {
				message = "YES";
			}

		} catch (Exception e) {
			throw e;
		} 
		return message;
	}
	
	public EmployeeBean getEmpDetails(String sfid) throws Exception {
		Session session = null;
		EmployeeBean empBean = null;
		String message="";
		try {
			session=hibernateUtils.getSession();
			//empBean= (EmployeeBean)session.createCriteria(EmployeeBean.class).add(Expression.eq("userSfid", sfid)).uniqueResult();
             String qry="select emp.sfid as sfid,emp.name_in_service_book as nameInServiceBook,desg.name as designationName,dept.department_name as officeName from emp_master emp,designation_master desg," +
             		    " departments_master dept where emp.sfid=? and  emp.designation_id=desg.id and emp.office_id=dept.department_id";
             empBean= (EmployeeBean)session.createSQLQuery(qry).addScalar("sfid", Hibernate.STRING).addScalar("nameInServiceBook", Hibernate.STRING).addScalar("designationName", Hibernate.STRING).addScalar("officeName", Hibernate.STRING).setString(0, sfid).setResultTransformer(Transformers.aliasToBean(EmployeeBean.class)).uniqueResult();
			
		} catch (Exception e) {
			message=CPSConstants.FAILED;
			throw e;
		} 
		return empBean;
	}
	
	@SuppressWarnings("unchecked")
	public List<MTRequestDetailsDTO> getAllocatedRequests() throws Exception{
		Session session = null;
		List<MTRequestDetailsDTO> mtAllocatedReqList =new ArrayList<MTRequestDetailsDTO>();
		try{
			session = hibernateUtils.getSession();
			List list=session.createSQLQuery("select reference_id from mt_vehicle_journey_details where status=8").list();
			
			if(list.size()>0){
			/*List<Integer> journeyIds=new ArrayList<Integer>();
			for(int i=0;i<list.size();i++){
				journeyIds.add(((BigDecimal)list.get(i)).intValue());
			}*/
		   mtAllocatedReqList = session.createCriteria(MTRequestDetailsDTO.class).add(Expression.in("status", new Integer[]{2,4})).list();
			for(int i=0; i<mtAllocatedReqList.size(); i++){
				getVehicleApplicantDetails(mtAllocatedReqList.get(i).getRequestID());	
			}
			//for getting only allocated vehicles filter remining journies
			for (int j = 0; j < mtAllocatedReqList.size(); j++) {
				List<MTJourneyDetailsDTO> journeyList=new ArrayList<MTJourneyDetailsDTO>();
				for (int k = 0; k < mtAllocatedReqList.get(j).getMtJourneyDetails().size(); k++) {
					if (mtAllocatedReqList.get(j).getMtJourneyDetails().get(k).getStatus() != 8) {
						journeyList.add(mtAllocatedReqList.get(j).getMtJourneyDetails().get(k));
						
					}else if(mtAllocatedReqList.get(j).getMtJourneyDetails().get(k).getStatus() == 8){
						if(CPSUtils.isNullOrEmpty(mtAllocatedReqList.get(j).getMtJourneyDetails().get(k).getMtVehicleAllocationtDetailsDTO()) || mtAllocatedReqList.get(j).getMtJourneyDetails().get(k).getMtVehicleAllocationtDetailsDTO().size()==0){
							journeyList.add(mtAllocatedReqList.get(j).getMtJourneyDetails().get(k));
						}
						//if release vehicle then filter it from edit tab
						else if(mtAllocatedReqList.get(j).getMtJourneyDetails().get(k).getMtVehicleAllocationtDetailsDTO().get(0).getStatusFlag()==8){
							journeyList.add(mtAllocatedReqList.get(j).getMtJourneyDetails().get(k));
						}
					}
				}
				mtAllocatedReqList.get(j).getMtJourneyDetails().removeAll(journeyList);
			}
			List<MTRequestDetailsDTO> requestList=new ArrayList<MTRequestDetailsDTO>();
			for(int i=0;i<mtAllocatedReqList.size();i++){
				if(mtAllocatedReqList.get(i).getMtJourneyDetails().size()==0){
					requestList.add(mtAllocatedReqList.get(i));
				}
			}
			mtAllocatedReqList.removeAll(requestList);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		session.clear();
		return mtAllocatedReqList;
	}
	public String userCancelJourney(MTApplicationBean mtbean) throws Exception{
		Session session = null;
		String message="";
		try{
			session = hibernateUtils.getSession();
			session.createSQLQuery("update mt_vehicle_journey_details set status=9 where id="+mtbean.getJourneyId()).executeUpdate();
			message="journeyCancel";
		}catch (Exception e) {
			e.printStackTrace();
			message="journeyCancelFail";
		}
		return message;
	}
	@SuppressWarnings("unchecked")
	public List<MTRequestDetailsDTO> getCanceledRequests() throws Exception{
		Session session = null;
		List<MTRequestDetailsDTO> mtCanceledJourneyList = null;
		try{
			session = hibernateUtils.getSession();
			String qry ="select request_id as requestID from mt_vehicle_request_details where id in" +
					    "(select reference_id from mt_vehicle_journey_details where status=9)";
			//List<MTRequestDetailsDTO> ids= session.createSQLQuery(qry).addScalar("requestID", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(MTRequestDetailsDTO.class)).list();
			List<String> ids=session.createSQLQuery(qry).list();
			if(ids.size()>0){
			
			mtCanceledJourneyList=session.createCriteria(MTRequestDetailsDTO.class).add(Expression.in("requestID", ids)).addOrder(Order.asc("requestID")).list();
			for(int i=0; i<mtCanceledJourneyList.size(); i++){
				getVehicleApplicantDetails(mtCanceledJourneyList.get(i).getRequestID());	
			}
			//for getting only cancelled vehicles filter remining journies
			for (int j = 0; j < mtCanceledJourneyList.size(); j++) {
				List<MTJourneyDetailsDTO> journeyList=new ArrayList<MTJourneyDetailsDTO>();
				for (int k = 0; k < mtCanceledJourneyList.get(j).getMtJourneyDetails().size(); k++) {
					//for getting already allocated cancel journies only
					//if (mtCanceledJourneyList.get(j).getMtJourneyDetails().get(k).getStatus() != 9 || mtCanceledJourneyList.get(j).getMtJourneyDetails().get(k).getMtVehicleAllocationtDetailsDTO().size()==0 || CPSUtils.isNullOrEmpty(mtCanceledJourneyList.get(j).getMtJourneyDetails().get(k).getMtVehicleAllocationtDetailsDTO())) {
					if (mtCanceledJourneyList.get(j).getMtJourneyDetails().get(k).getStatus() != 9 || CPSUtils.isNullOrEmpty(mtCanceledJourneyList.get(j).getMtJourneyDetails().get(k).getMtVehicleAllocationtDetailsDTO()) || (mtCanceledJourneyList.get(j).getMtJourneyDetails().get(k).getMtVehicleAllocationtDetailsDTO()!=null && mtCanceledJourneyList.get(j).getMtJourneyDetails().get(k).getMtVehicleAllocationtDetailsDTO().size()==0)) {	
					journeyList.add(mtCanceledJourneyList.get(j).getMtJourneyDetails().get(k));
						//mtAppReqList.get(j).getMtJourneyDetails().remove(k);
					
					}
				}
				mtCanceledJourneyList.get(j).getMtJourneyDetails().removeAll(journeyList);
			}
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		session.clear();
		return mtCanceledJourneyList;
	}
	
	@SuppressWarnings("unchecked")
	public String deallocateVehicle(MTApplicationBean mtbean) throws Exception{
		Session session = null;
		String message="";
		try{
			//daywise deallocation==KUMARI===
			/*session = hibernateUtils.getSession();
			Calendar cal=Calendar.getInstance();
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			SimpleDateFormat sd2 = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
			JSONObject jsonObj=new JSONObject(mtbean.getDeallocateJourneyJSON());
			
			String qry1="select id as id ,to_char(from_date_time,'dd-Mon-yyyy HH24-MI') as fromDate,to_char(to_date_time,'dd-Mon-yyyy HH24-MI') as toDate,status_flag as statusFlag,journey_id as journeyId from mt_vehicle_allocation_details where to_date_time =" +
			            " to_date(?,'dd-Mon-yyyy HH24-MI') AND vehicle_driver_map_id=?";
			
		    String qry2="select id as id ,to_char(from_date_time,'dd-Mon-yyyy HH24-MI') as fromDate,to_char(to_date_time,'dd-Mon-yyyy HH24-MI') as toDate,status_flag as statusFlag,journey_id as journeyId from mt_vehicle_allocation_details where from_date_time =" +
				        " to_date(?,'dd-Mon-yyyy HH24-MI') AND vehicle_driver_map_id=?";
		
			for(int i=0;i<jsonObj.length();i++){
				List<MTVehicleAllocationtDetailsDTO> allocatedListForJourney=null;
				MTVehicleAllocationtDetailsDTO mAllocationtDetailsDTO = new MTVehicleAllocationtDetailsDTO();
				MTJourneyDetailsDTO mtJourneyDetailsDTO = new MTJourneyDetailsDTO();
				MTVehicleAllocationtDetailsDTO previousAllocation =null;
				MTVehicleAllocationtDetailsDTO nextAllocation =null;
				List<Integer> allocationIds=new ArrayList<Integer>();
				
				int test=Integer.parseInt(jsonObj.get(String.valueOf(i)).toString());
				
				mtJourneyDetailsDTO = (MTJourneyDetailsDTO)session.createCriteria(MTJourneyDetailsDTO.class).add(Expression.eq("id", test)).uniqueResult();
				allocatedListForJourney=session.createCriteria(MTVehicleAllocationtDetailsDTO.class).add(Expression.eq("journeyId", test)).addOrder(Order.asc("fromDate")).list();
				
				BeanUtils.copyProperties(mAllocationtDetailsDTO, allocatedListForJourney.get(0));
				mAllocationtDetailsDTO.setId(0);
				mAllocationtDetailsDTO.setToDate(allocatedListForJourney.get(allocatedListForJourney.size()-1).getToDate());
				mAllocationtDetailsDTO.setStatusFlag(20);
				
				
				
				for(int j=0;j<allocatedListForJourney.size();j++){
					allocationIds.add(allocatedListForJourney.get(j).getId());
				}
				cal.setTime(mAllocationtDetailsDTO.getFromDate());
				cal.add(Calendar.MINUTE, -1);
				//Object obj1=session.createSQLQuery(qry1).addScalar("id", Hibernate.INTEGER).addScalar("fromDate", Hibernate.DATE).addScalar("toDate", Hibernate.DATE).addScalar("statusFlag", Hibernate.INTEGER).addScalar("journeyId", Hibernate.INTEGER).setString(0, sd2.format(cal.getTime())).setInteger(1, mAllocationtDetailsDTO.getVehicleDriverMapId()).setResultTransformer(Transformers.aliasToBean(MTVehicleAllocationtDetailsDTO.class)).uniqueResult();
				Object obj1=session.createCriteria(MTVehicleAllocationtDetailsDTO.class).add(Expression.and(Expression.eq("toDate", cal.getTime()), Expression.eq("vehicleDriverMapId", mAllocationtDetailsDTO.getVehicleDriverMapId()))).uniqueResult();
				cal.setTime(mAllocationtDetailsDTO.getToDate());
				cal.add(Calendar.MINUTE, 1);
				//Object obj2=session.createSQLQuery(qry2).addScalar("id", Hibernate.INTEGER).addScalar("fromDate", Hibernate.DATE).addScalar("toDate", Hibernate.DATE).addScalar("statusFlag", Hibernate.INTEGER).addScalar("journeyId", Hibernate.INTEGER).setString(0, sd2.format(cal.getTime())).setInteger(1, mAllocationtDetailsDTO.getVehicleDriverMapId()).setResultTransformer(Transformers.aliasToBean(MTVehicleAllocationtDetailsDTO.class)).uniqueResult();
				Object obj2=session.createCriteria(MTVehicleAllocationtDetailsDTO.class).add(Expression.and(Expression.eq("fromDate", cal.getTime()), Expression.eq("vehicleDriverMapId", mAllocationtDetailsDTO.getVehicleDriverMapId()))).uniqueResult();
				
				//same day req
				if(CPSUtils.compareTwoDates(mtJourneyDetailsDTO.getDepartureDate(),mtJourneyDetailsDTO.getEstimatedDate())){
				//check previous record exists
			
				if(!CPSUtils.isNullOrEmpty(obj1)){
				previousAllocation=(MTVehicleAllocationtDetailsDTO)obj1;
				
				if(CPSUtils.compareTwoDates(mAllocationtDetailsDTO.getFromDate(),previousAllocation.getToDate())==true){
					mAllocationtDetailsDTO.setJourneyId(previousAllocation.getJourneyId());
					if(previousAllocation.getStatusFlag()==20){
						mAllocationtDetailsDTO.setFromDate(previousAllocation.getFromDate());
						allocationIds.add(previousAllocation.getId());
					}
				
				
				}
			}
			//check next record exists
		
			if(!CPSUtils.isNullOrEmpty(obj2)){
				nextAllocation=(MTVehicleAllocationtDetailsDTO)obj2;
				if(CPSUtils.compareTwoDates(mAllocationtDetailsDTO.getToDate(),nextAllocation.getFromDate())==true){
					mAllocationtDetailsDTO.setJourneyId(nextAllocation.getJourneyId());
					if( nextAllocation.getStatusFlag()==20){
						mAllocationtDetailsDTO.setToDate(nextAllocation.getToDate());
						allocationIds.add(nextAllocation.getId());
					}
				
				}
			}
			
			if(!CPSUtils.isNullOrEmpty(obj1) || !CPSUtils.isNullOrEmpty(obj2)){
			saveVehicleAllocationDetails(mAllocationtDetailsDTO);
			}
			}
			//different day req
			else{
				MTVehicleAllocationtDetailsDTO fromAllocationtDetailsDTO=new MTVehicleAllocationtDetailsDTO();
				MTVehicleAllocationtDetailsDTO toAllocationtDetailsDTO=new MTVehicleAllocationtDetailsDTO();
				BeanUtils.copyProperties(fromAllocationtDetailsDTO, mAllocationtDetailsDTO);
				BeanUtils.copyProperties(toAllocationtDetailsDTO, mAllocationtDetailsDTO);
				
				fromAllocationtDetailsDTO.setToDate(sd2.parse(sd2.format(mAllocationtDetailsDTO.getFromDate()).split(" ")[0]+" "+"23:59"));
				toAllocationtDetailsDTO.setFromDate(allocatedListForJourney.get(allocatedListForJourney.size()-1).getFromDate());
				
				//check previous record exists
				if(!CPSUtils.isNullOrEmpty(obj1)){
					previousAllocation=(MTVehicleAllocationtDetailsDTO)obj1;	
					if(CPSUtils.compareTwoDates(mAllocationtDetailsDTO.getFromDate(),previousAllocation.getToDate())==true ){
						fromAllocationtDetailsDTO.setJourneyId(previousAllocation.getJourneyId());
						if(previousAllocation.getStatusFlag()==20){
							fromAllocationtDetailsDTO.setFromDate(previousAllocation.getFromDate());
							
							//fromAllocationtDetailsDTO.setJourneyId(previousAllocation.getJourneyId());
						allocationIds.add(previousAllocation.getId());
						//(fromAllocationtDetailsDTO);
						}else if(previousAllocation.getStatusFlag()==25){
							fromAllocationtDetailsDTO.setFromDate(mAllocationtDetailsDTO.getFromDate());
							//saveVehicleAllocationDetails(fromAllocationtDetailsDTO);
						}
						saveVehicleAllocationDetails(fromAllocationtDetailsDTO);
					}
				}
				//check next record exists
				if(!CPSUtils.isNullOrEmpty(obj2)){
					nextAllocation=(MTVehicleAllocationtDetailsDTO)obj2;
					if(CPSUtils.compareTwoDates(mAllocationtDetailsDTO.getToDate(),nextAllocation.getFromDate())==true){
						toAllocationtDetailsDTO.setJourneyId(nextAllocation.getJourneyId());
						if(nextAllocation.getStatusFlag()==20){
							toAllocationtDetailsDTO.setToDate(nextAllocation.getToDate());
							//toAllocationtDetailsDTO.setJourneyId(nextAllocation.getJourneyId());
						allocationIds.add(nextAllocation.getId());
						//saveVehicleAllocationDetails(toAllocationtDetailsDTO);
						}else if(previousAllocation.getStatusFlag()==25){
							toAllocationtDetailsDTO.setToDate(mAllocationtDetailsDTO.getToDate());
							//saveVehicleAllocationDetails(toAllocationtDetailsDTO);
						}
						saveVehicleAllocationDetails(toAllocationtDetailsDTO);
					}
				}
				
			}
				
				for(int p=0;p<allocationIds.size();p++){
					session.createSQLQuery("delete from mt_vehicle_allocation_details where id=?").setInteger(0, allocationIds.get(p)).executeUpdate();
					}
			}*/
			session = hibernateUtils.getSession();
			JSONObject jsonObj=new JSONObject(mtbean.getDeallocateJourneyJSON());
			List<Integer> allocIdList=new ArrayList<Integer>();
			for(int i=0;i<jsonObj.length();i++){
				int allocId=Integer.parseInt(jsonObj.get(String.valueOf(i)).toString());
				allocIdList.add(allocId);
				/*int allocId=Integer.parseInt(jsonObj.get(String.valueOf(i)).toString());
				//check if any journey having this allocId with out cancel.if exists allocation record wont delete
				List<MTJourneyDetailsDTO> journeyList=session.createCriteria(MTJourneyDetailsDTO.class).add(Expression.eq("allocId", String.valueOf(allocId))).list();
				if(journeyList.size()<=1){
					allocIdList.add(allocId);
				}else if(journeyList.size()>1){
					int count=1;
					for(int j=0;j<journeyList.size();j++){
						if(journeyList.get(j).getStatus()==9){
							count++;
						}
					}
					if(count==journeyList.size()){
						allocIdList.add(allocId);
					}
				}*/
				
			}
			if(allocIdList.size()>0){
			session.createQuery("delete MTVehicleAllocationtDetailsDTO where id in(:allocIds)").setParameterList("allocIds", allocIdList).executeUpdate();
			//session.createSQLQuery("delete from mt_vehicle_allocation_details where id in(:allocIds)").setParameterList("allocIds", allocIdList).executeUpdate();
			message="deallocateSuccess";
			}else{
				message = "deallocError";
			}
			
		}catch (Exception e) {
			
			e.printStackTrace();
			message="deallocateFail";
			throw e;
		}
		return message;
	}
	public void saveAllocationForDedicated(MTVehicleAllocationtDetailsDTO mAllocationtDetailsDTO) throws Exception{
		Session session=null;
		try{
			session=hibernateUtils.getSession();
			session.saveOrUpdate(mAllocationtDetailsDTO);
			session.flush();
			session.clear();
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	public List<EmployeeBean> getAllEmployeeList() throws Exception {
		log.debug("getEmployeeDetails---->empList");
		List<EmployeeBean> empList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			empList = session.createCriteria(EmployeeBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.asc("nameInServiceBook")).list();
			//empList = session.createCriteria(EmployeeBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Restrictions.sqlRestriction("trim('name_in_service_book')")).list();
				} catch (Exception e) {
			throw e;
		}
		return empList;
	}
	public String freeDedicatedVehicles(MTApplicationBean mtbean) throws Exception{
		String message="";
		Session session=null;
		
		try{
			session = hibernateUtils.getSession();
			SimpleDateFormat df=new SimpleDateFormat("dd-MMM-yyyy HH:mm");
			JSONObject jsonObj = new JSONObject(mtbean.getFreeDedicatedVehicleJSON());
			for(int i=0;i<jsonObj.length();i++){
				
				JSONObject rowJsonObj = new JSONObject(jsonObj.getString(String.valueOf(i)));
				mtbean.setFromDate(rowJsonObj.getString("fromDateTime"));
				mtbean.setToDate(rowJsonObj.getString("toDateTime"));
				mtbean.setMapId(Integer.parseInt(rowJsonObj.getString("mapId")));
				mtbean.setAllocId((Integer.parseInt(rowJsonObj.getString("allocId"))));
				
				Calendar cal=Calendar.getInstance();
				
				String fromDate="";
				if(Integer.parseInt(mtbean.getFromDate().split(" ")[1].split(":")[0].trim())==0){
					if(Integer.parseInt(mtbean.getFromDate().split(" ")[1].split(":")[1].trim())==1){
						fromDate=mtbean.getFromDate().split(" ")[0]+" 00:00";
					}
				}
				if(fromDate!=""){
					cal.setTime(df.parse(fromDate));
				}else{
					cal.setTime(df.parse(mtbean.getFromDate()));
				}
				
				cal.add(Calendar.MINUTE, -1);
				//update todate(set fromdate of free dedicated vehicles) for already existing record(dedicated) in allocation table
				session.createSQLQuery("update mt_vehicle_allocation_details set to_date_time=to_date(?,'dd-Mon-yyyy HH24:MI') where id=?").setString(0, df.format(cal.getTime())).setInteger(1, mtbean.getAllocId()).executeUpdate();
				session.clear();
				//insert new record after free time over(dedicated)(set todate of free dedicated vehicles as fromdate)
				MTVehicleAllocationtDetailsDTO mtAllocationtDetailsDTO = new MTVehicleAllocationtDetailsDTO();
				mtAllocationtDetailsDTO.setJourneyId(0);
				cal.setTime(df.parse(mtbean.getToDate()));
				String toDate="";
				if(Integer.parseInt(mtbean.getToDate().split(" ")[1].split(":")[0].trim())==23){
					if(Integer.parseInt(mtbean.getToDate().split(" ")[1].split(":")[1].trim())==59){
						
						toDate=CPSUtils.nextDate(mtbean.getToDate().split(" ")[0])+" 00:00";
					}
				}
				
				if(toDate!=""){
					cal.setTime(df.parse(toDate));
				}else{
					cal.setTime(df.parse(mtbean.getToDate()));
				}
				cal.add(Calendar.MINUTE, 1);
				mtAllocationtDetailsDTO.setFromDate(df.parse(df.format(cal.getTime())));
				mtAllocationtDetailsDTO.setVehicleDriverMapId(mtbean.getMapId());
				mtAllocationtDetailsDTO.setStatusFlag(25);
				session.save(mtAllocationtDetailsDTO);
				session.flush();
				session.evict(mtAllocationtDetailsDTO);
				
				//insert records as free (take between fromdate and todate)
				//SimpleDateFormat sd=new SimpleDateFormat("dd-MMM-yyyy");
				/*//for same day
				if(CPSUtils.compareTwoDates(sd.parse(mtbean.getFromDate()), sd.parse(mtbean.getToDate()))){
					MTVehicleAllocationtDetailsDTO mtAllocationtDetailsDTO2 = new MTVehicleAllocationtDetailsDTO();
					mtAllocationtDetailsDTO2.setJourneyId(0);
					mtAllocationtDetailsDTO2.setFromDate(df.parse(mtbean.getFromDate()));
					mtAllocationtDetailsDTO2.setToDate(df.parse(mtbean.getToDate()));
					mtAllocationtDetailsDTO2.setVehicleDriverMapId(mtbean.getMapId());
					mtAllocationtDetailsDTO2.setStatusFlag(20);
					session.save(mtAllocationtDetailsDTO2);
				}
				//for diff days
				else{
					int daysDiff = Integer.parseInt(CPSUtils.daysDifference(mtbean.getFromDate().split(" ")[0], mtbean.getToDate().split(" ")[0]));
					MTVehicleAllocationtDetailsDTO mtAllocationtDetailsDTO2 = new MTVehicleAllocationtDetailsDTO();
					mtAllocationtDetailsDTO2.setJourneyId(0);
					mtAllocationtDetailsDTO2.setFromDate(df.parse(mtbean.getFromDate()));
					mtAllocationtDetailsDTO2.setToDate(df.parse(mtbean.getFromDate().split(" ")[0]+" 23:59"));
					mtAllocationtDetailsDTO2.setVehicleDriverMapId(mtbean.getMapId());
					mtAllocationtDetailsDTO2.setStatusFlag(20);
					session.save(mtAllocationtDetailsDTO2);
					//days diff>1
					if(daysDiff>0){
						for(int j=0;j<daysDiff;j++){
							MTVehicleAllocationtDetailsDTO AllocationtDTO = new MTVehicleAllocationtDetailsDTO();
							AllocationtDTO.setJourneyId(0);
							String nextDay=CPSUtils.nextDate(mtbean.getFromDate().split(" ")[0]);
							AllocationtDTO.setFromDate(df.parse(nextDay+" 00:01"));
							AllocationtDTO.setToDate(df.parse(nextDay+" 23:59"));
							AllocationtDTO.setVehicleDriverMapId(mtbean.getMapId());
							AllocationtDTO.setStatusFlag(20);
							session.save(AllocationtDTO);
						}
					}
					MTVehicleAllocationtDetailsDTO mtAllocationtDetailsDTO3 = new MTVehicleAllocationtDetailsDTO();
					mtAllocationtDetailsDTO3.setJourneyId(0);
					mtAllocationtDetailsDTO3.setFromDate(df.parse(mtbean.getToDate().split(" ")[0]+" 00:01"));
					mtAllocationtDetailsDTO3.setToDate(df.parse(mtbean.getToDate()));
					mtAllocationtDetailsDTO3.setVehicleDriverMapId(mtbean.getMapId());
					mtAllocationtDetailsDTO3.setStatusFlag(20);
					session.save(mtAllocationtDetailsDTO3);
				}*/
				//insert records as free (take between fromdate and todate)
				MTVehicleAllocationtDetailsDTO mtAllocationtDetailsDTO2 = new MTVehicleAllocationtDetailsDTO();
				mtAllocationtDetailsDTO2.setJourneyId(0);
				//sdf
				mtAllocationtDetailsDTO2.setFromDate(df.parse(mtbean.getFromDate()));
				mtAllocationtDetailsDTO2.setToDate(df.parse(mtbean.getToDate()));
				mtAllocationtDetailsDTO2.setVehicleDriverMapId(mtbean.getMapId());
				mtAllocationtDetailsDTO2.setStatusFlag(20);
				session.save(mtAllocationtDetailsDTO2);
				session.flush();
				session.evict(mtAllocationtDetailsDTO2);
				//session.clear();
				
				
				message="freeSuccess";
				
			}
			
			
		}catch(Exception e){
			
		e.printStackTrace();	
		message="freeFail";
		}
		return message;
	}
	public String getVehicleDriverMapId(MTApplicationBean mtbean) throws Exception{
		Session session = null;
		String mapId=null;
		try {
			session = hibernateUtils.getSession();
			Object obj = session.createCriteria(MTVehicleDriverMapDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("vehicleId", mtbean.getVehicleId())).uniqueResult();
            if(!CPSUtils.isNullOrEmpty(obj)){
            	mapId=String.valueOf(((MTVehicleDriverMapDTO)obj).getId());
            }
				} catch (Exception e) {
			throw e;
		}
		return mapId;
	}
	public String saveCombineAlloc(MTApplicationBean mtbean) throws Exception{
		Session session = null;
		String mapId=null;
		String message="";
		try {
			session = hibernateUtils.getSession();
			SimpleDateFormat sd=new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat sdt=new SimpleDateFormat("dd-MMM-yyyy HH:mm");
			SimpleDateFormat db=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			//provide search functionality
			
			/*MTVehicleAllocationtDetailsDTO mtAllocationtDetailsDTO = (MTVehicleAllocationtDetailsDTO)session.createCriteria(MTVehicleAllocationtDetailsDTO.class).add(Expression.eq("id", mtbean.getAllocId())).uniqueResult();
			String fromDate="";
			String toDate="";
			String searchFromDateTime=mtbean.getSearchFromDate()+" "+mtbean.getSearchFromTime();
			String searchToDateTime=mtbean.getSearchToDate()+" "+mtbean.getSearchToTime();
			//get min date between req from date  and alloc from date
			if((sdt.parse(searchFromDateTime)).equals(mtAllocationtDetailsDTO.getFromDate())){
				fromDate=searchFromDateTime;
			}
			else if((sdt.parse(searchFromDateTime)).before(mtAllocationtDetailsDTO.getFromDate())){
				fromDate=searchFromDateTime;
				//fromDate=mtJourneyDetailsDTO.getDepartureDate();
			}else if((sdt.parse(searchFromDateTime)).after(mtAllocationtDetailsDTO.getFromDate())){
				fromDate=sdt.format(mtAllocationtDetailsDTO.getFromDate());
				//fromDate=mtAllocationtDetailsDTO.getFromDate();
			}
			//get max date between req to date  and alloc to date
			if((sdt.parse(searchToDateTime)).equals(mtAllocationtDetailsDTO.getToDate())){
				toDate=searchToDateTime;
			}
			else if((sdt.parse(searchToDateTime)).before(mtAllocationtDetailsDTO.getToDate())){
				toDate=sdt.format(mtAllocationtDetailsDTO.getToDate());
			}else if((sdt.parse(searchToDateTime)).after(mtAllocationtDetailsDTO.getToDate())){
				toDate=searchToDateTime;
			}
			session.createQuery("update MTVehicleAllocationtDetailsDTO set fromDate=to_date(?,'dd-Mon-yyyy HH24:MI'),toDate=to_date(?,'dd-Mon-yyyy HH24:MI') where id="+mtbean.getAllocId()).setString(0, fromDate).setString(1, toDate).executeUpdate();
			session.createQuery("update MTJourneyDetailsDTO set allocId=?,status=8 where id=?").setInteger(0, mtbean.getAllocId()).setInteger(1, mtbean.getJourneyId()).executeUpdate();*/
			
			message="allocateSuccess";
		} catch (Exception e) {
			message="allocateFail";
				e.printStackTrace();
			throw e ;
		}finally{
			session.flush();
		}
		return message;
	}
	public String checkNextAllocationForRelease(MTApplicationBean mtbean) throws Exception{
		Session session = null;
		String message="";
		List<MTRequestDetailsDTO> reqDetailsList=null;
		List<String> reqIdList =new ArrayList<String>();
		List<Integer> journeyList=new ArrayList<Integer>();
		//MTRequestDetailsDTO mtRequestDetailsDTO=null;
		try {
			session = hibernateUtils.getSession();
			String qry="select req.request_id as remarks,alloc.journey_id as id,journey.departure_date as departureDate," +
					"journey.departure_time as departureTime,journey.estimated_date as estimatedDate," +
					" journey.estimated_time as estimatedTime from mt_vehicle_allocation_details alloc,mt_vehicle_journey_details journey," +
					" mt_vehicle_request_details req where alloc.journey_id=journey.id and journey.status!=9 and journey.reference_id=req.id " +
					" and req.status!=9 and alloc.vehicle_driver_map_id="+mtbean.getMapId()+" AND alloc.id!="+mtbean.getAllocId()+" AND " +
					"alloc.from_date_time between to_date('"+mtbean.getFromDate()+"','dd-Mon-yyyy HH24-MI') AND " +
					" to_date('"+mtbean.getToDate()+"','dd-Mon-yyyy HH24-MI')";
			List<MTJourneyDetailsDTO> list=session.createSQLQuery(qry).addScalar("remarks", Hibernate.STRING).addScalar("departureDate", Hibernate.DATE).addScalar("departureTime", Hibernate.STRING).addScalar("estimatedDate", Hibernate.DATE).addScalar("estimatedTime", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(MTJourneyDetailsDTO.class)).list();
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					MTJourneyDetailsDTO mtJourneyDetailsDTO=list.get(i);
					message += mtJourneyDetailsDTO.getRemarks()+" Request Is Allocated From "+mtJourneyDetailsDTO.getDepartureDate()+" "+mtJourneyDetailsDTO.getDepartureTime()+" To "+mtJourneyDetailsDTO.getEstimatedDate()+" "+mtJourneyDetailsDTO.getEstimatedTime()+"\n";
				}
				//Object [] atrarr=list.toArray();
				/*for(int i=0; i<list.size();i++){
					String as=list.get(i).toString().split(",")[0];
					System.out.println(as);
				}*/
				
			}
			/*if(list.size()>0){
				for(int i=0;i<list.size();i++){
				reqIdList.add(list.get(i).toString().split(",")[0]);
				journeyList.add(Integer.parseInt(list.get(i).toString().split(",")[1]));
				}
				reqDetailsList=session.createCriteria(MTRequestDetailsDTO.class).add(Expression.in("requestID", reqIdList)).list();
			}
			if(reqDetailsList.size()>0){
				for(int j=0;j<reqDetailsList.size();j++){
					if(reqDetailsList.get(j).getRequestID()==){
						
					}
				}
			}*/
			
			//reqDetailsList=session.createCriteria(MTRequestDetailsDTO).
			//mtRequestDetailsDTO=session.
			/*if(reqList.size()>=1){
				message += "";
			}*/
			
			} catch (Exception e) {
				e.printStackTrace();
			throw e;
		}
		return message;
	}
	public String updateRequestDetails(MTApplicationBean mtBean) throws Exception{
		Session session = null;
		String message="";
		try{
			session=hibernateUtils.getSession();
			
			 if(!CPSUtils.isNullOrEmpty(mtBean.getOnwardMainJSON()) && mtBean.getOnwardMainJSON().length()>2){
					
				 JSONObject onwardMainJSON = new JSONObject(mtBean.getOnwardMainJSON());
				 for(int i=1; i<=onwardMainJSON.length(); i++){
					 JSONObject onwardSubJSON = (JSONObject)(onwardMainJSON.get(String.valueOf(i)));
					 JSONObject onwardPassEngRowJSON = new JSONObject(onwardSubJSON.getString("onwardPassEngRowJSON"));
					 
					 //get previous record
					 MTJourneyDetailsDTO oldJourneyDetailsDTO=(MTJourneyDetailsDTO)session.createCriteria(MTJourneyDetailsDTO.class).add(Expression.eq("id",Integer.parseInt(onwardPassEngRowJSON.getString("journeyId")) )).uniqueResult();
					 session.clear();
					 /**
					  * update data into mt_vehicle_journey_details table
					  **/
					 int journeyId=0;
					 		MTJourneyDetailsDTO mtJourneyDetailsDTO = new MTJourneyDetailsDTO();
					 	 
					 		if(!CPSUtils.isNullOrEmpty(onwardPassEngRowJSON.getString("journeyId"))){
					 			journeyId = Integer.parseInt(onwardPassEngRowJSON.getString("journeyId"));
								 mtJourneyDetailsDTO.setId(Integer.parseInt(onwardPassEngRowJSON.getString("journeyId"))); 
							}
						if(!CPSUtils.isNullOrEmpty(onwardPassEngRowJSON.getString("onwardNOP"))){
							 mtJourneyDetailsDTO.setNoOfPeople(Integer.parseInt(onwardPassEngRowJSON.getString("onwardNOP"))); 
						}
						if(!CPSUtils.isNullOrEmpty(onwardPassEngRowJSON.getString("onwardNameWithDesig"))){
							mtJourneyDetailsDTO.setNameWithDesignation(onwardPassEngRowJSON.getString("onwardNameWithDesig"));					 
						}
						if(!CPSUtils.isNullOrEmpty(onwardPassEngRowJSON.getString("onwardDepartureDate"))){
							String dateTime= onwardPassEngRowJSON.getString("onwardDepartureDate")+" "+onwardPassEngRowJSON.getString("onwardDepartureTime");
							mtJourneyDetailsDTO.setDepartureDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(dateTime)); 
							/*//for edit flexibility of journey from date to date 
							mtJourneyDetailsDTO.setRequestFromDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(dateTime));*/
						}
						if(!CPSUtils.isNullOrEmpty(onwardPassEngRowJSON.getString("onwardDepartureTime"))){
							mtJourneyDetailsDTO.setDepartureTime(onwardPassEngRowJSON.getString("onwardDepartureTime")); 
						}
						if(!CPSUtils.isNullOrEmpty(onwardPassEngRowJSON.getString("onwardEstiDate"))){
							String dateTime= onwardPassEngRowJSON.getString("onwardEstiDate")+" "+onwardPassEngRowJSON.getString("onwardEstiTime");
							mtJourneyDetailsDTO.setEstimatedDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(dateTime)); 
							/*//for edit flexibility of journey from date to date 
							mtJourneyDetailsDTO.setRequestToDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(dateTime));*/
						}
						if(!CPSUtils.isNullOrEmpty(onwardPassEngRowJSON.getString("onwardEstiTime"))){
							mtJourneyDetailsDTO.setEstimatedTime(onwardPassEngRowJSON.getString("onwardEstiTime"));
						}
						if(!CPSUtils.isNullOrEmpty(onwardPassEngRowJSON.getString("onwardPickup"))){
							mtJourneyDetailsDTO.setPickupPoint(Integer.parseInt(onwardPassEngRowJSON.getString("onwardPickup")));
						}
						if(!CPSUtils.isNullOrEmpty(onwardPassEngRowJSON.getString("onwardOtherSource"))){
							mtJourneyDetailsDTO.setPickupPlace(onwardPassEngRowJSON.getString("onwardOtherSource")); 
						}
						if(!CPSUtils.isNullOrEmpty(onwardPassEngRowJSON.getString("onwardDrop"))){
							mtJourneyDetailsDTO.setDropPoint(Integer.parseInt(onwardPassEngRowJSON.getString("onwardDrop"))); 
						}
						if(!CPSUtils.isNullOrEmpty(onwardPassEngRowJSON.getString("onwardOtherDesti"))){
							mtJourneyDetailsDTO.setDropPlace(onwardPassEngRowJSON.getString("onwardOtherDesti"));
						}
						if(!CPSUtils.isNullOrEmpty(onwardPassEngRowJSON.getString("onwardAccomReq"))){
							mtJourneyDetailsDTO.setAccommReqFlag(Integer.parseInt(onwardPassEngRowJSON.getString("onwardAccomReq"))); 
						}
						if(onwardPassEngRowJSON.getString("onwardAccomReq").equals("1")){
							if(!CPSUtils.isNullOrEmpty(onwardPassEngRowJSON.getString("onwardAccomPlace"))){
								mtJourneyDetailsDTO.setAccommPlace(onwardPassEngRowJSON.getString("onwardAccomPlace"));
							}
						}
						if(!CPSUtils.isNullOrEmpty(onwardPassEngRowJSON.getString("onwardArtiReq"))){
							mtJourneyDetailsDTO.setArticleCarryFlag(Integer.parseInt(onwardPassEngRowJSON.getString("onwardArtiReq")));
						}
						if(!CPSUtils.isNullOrEmpty(onwardPassEngRowJSON.getString("onwardRemarks"))){
							mtJourneyDetailsDTO.setRemarks(onwardPassEngRowJSON.getString("onwardRemarks")); 
						}
						
						mtJourneyDetailsDTO.setJourneyTypeFlag("ONWARD");
						mtJourneyDetailsDTO.setStatus(8);
						
						//set previous date 
						
						SimpleDateFormat sd=new SimpleDateFormat("dd-MMM-yyyy HH:mm");
						
						 mtJourneyDetailsDTO.setReferenceID(oldJourneyDetailsDTO.getReferenceID());
						mtJourneyDetailsDTO.setRequestFromDate(sd.parse(sd.format(oldJourneyDetailsDTO.getRequestFromDate())));
						mtJourneyDetailsDTO.setRequestToDate(sd.parse(sd.format(oldJourneyDetailsDTO.getRequestToDate())));
						mtJourneyDetailsDTO.setAllocId(oldJourneyDetailsDTO.getAllocId());
						
				
						session.saveOrUpdate(mtJourneyDetailsDTO);
						session.flush();
						
						//delete existing aricle details
						session.createQuery("delete MTArticleDetailsDTO where referenceID="+mtJourneyDetailsDTO.getId()).executeUpdate();
						session.flush();
						session.clear();
					 
					 JSONObject onwardArticleTabJSON = new JSONObject(onwardSubJSON.getString("onwardArticleTabJSON"));
					 for(int j=1; j<=onwardArticleTabJSON.length(); j++){
						 JSONObject onwardArticleRowJSON = (JSONObject)(onwardArticleTabJSON.get(String.valueOf(j)));
						
						 /**
						  * inserting data into mt_article_details table
						  **/
						 MTArticleDetailsDTO mtArticleDetailsDTO = new MTArticleDetailsDTO();
						 
						 	mtArticleDetailsDTO.setReferenceID(journeyId);
						 if(!CPSUtils.isNullOrEmpty(onwardArticleRowJSON.getString("onwardArtType"))){
							 mtArticleDetailsDTO.setType(onwardArticleRowJSON.getString("onwardArtType"));
						 }
						 if(!CPSUtils.isNullOrEmpty(onwardArticleRowJSON.getString("onwardArtLength"))){
							 mtArticleDetailsDTO.setLength(Integer.parseInt(onwardArticleRowJSON.getString("onwardArtLength")));
						 }
						 if(!CPSUtils.isNullOrEmpty(onwardArticleRowJSON.getString("onwardArtBreadth"))){
							 mtArticleDetailsDTO.setBreadth(Integer.parseInt(onwardArticleRowJSON.getString("onwardArtBreadth")));
						 }
						 if(!CPSUtils.isNullOrEmpty(onwardArticleRowJSON.getString("onwardArtHeight"))){
							 mtArticleDetailsDTO.setHeight(Integer.parseInt(onwardArticleRowJSON.getString("onwardArtHeight")));
						 }
						 if(!CPSUtils.isNullOrEmpty(onwardArticleRowJSON.getString("onwardArtWeight"))){
							 mtArticleDetailsDTO.setWeight(Integer.parseInt(onwardArticleRowJSON.getString("onwardArtWeight")));
						 }
						 if(!CPSUtils.isNullOrEmpty(onwardArticleRowJSON.getString("onwardArtQuantity"))){
							 mtArticleDetailsDTO.setQuantity(Integer.parseInt(onwardArticleRowJSON.getString("onwardArtQuantity")));
						 }
						 
						    mtArticleDetailsDTO.setJourneyTypeFlag("ONWARD");
						 	mtArticleDetailsDTO.setStatus(1);
						 	
						 	session.save(mtArticleDetailsDTO);
						 	session.flush();
					 }
				 }
			 }
			 
			 //for return
			 if(!CPSUtils.isNullOrEmpty(mtBean.getReturnMainJSON()) && mtBean.getReturnMainJSON().length()>2){
					
				 JSONObject returnMainJSON = new JSONObject(mtBean.getReturnMainJSON());
				 for(int i=1; i<=returnMainJSON.length(); i++){
					 JSONObject returnSubJSON = (JSONObject)(returnMainJSON.get(String.valueOf(i)));
					 JSONObject returnPassEngRowJSON = new JSONObject(returnSubJSON.getString("returnPassEngRowJSON"));
					 
					 //get previous record
					 MTJourneyDetailsDTO oldJourneyDetailsDTO=(MTJourneyDetailsDTO)session.createCriteria(MTJourneyDetailsDTO.class).add(Expression.eq("id",Integer.parseInt(returnPassEngRowJSON.getString("journeyId")) )).uniqueResult();
					 session.clear();
					 /**
					  * update data into mt_vehicle_journey_details table
					  **/
					 int journeyId=0;
					 		MTJourneyDetailsDTO mtJourneyDetailsDTO = new MTJourneyDetailsDTO();
					 	 
					 		if(!CPSUtils.isNullOrEmpty(returnPassEngRowJSON.getString("journeyId"))){
					 			journeyId = Integer.parseInt(returnPassEngRowJSON.getString("journeyId"));
								 mtJourneyDetailsDTO.setId(Integer.parseInt(returnPassEngRowJSON.getString("journeyId"))); 
							}
						if(!CPSUtils.isNullOrEmpty(returnPassEngRowJSON.getString("returnNOP"))){
							 mtJourneyDetailsDTO.setNoOfPeople(Integer.parseInt(returnPassEngRowJSON.getString("returnNOP"))); 
						}
						if(!CPSUtils.isNullOrEmpty(returnPassEngRowJSON.getString("returnNameWithDesig"))){
							mtJourneyDetailsDTO.setNameWithDesignation(returnPassEngRowJSON.getString("returnNameWithDesig"));					 
						}
						if(!CPSUtils.isNullOrEmpty(returnPassEngRowJSON.getString("returnDepartureDate"))){
							String dateTime= returnPassEngRowJSON.getString("returnDepartureDate")+" "+returnPassEngRowJSON.getString("returnDepartureTime");
							mtJourneyDetailsDTO.setDepartureDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(dateTime)); 
							/*//for edit flexibility of journey from date to date 
							mtJourneyDetailsDTO.setRequestFromDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(dateTime));*/
						}
						if(!CPSUtils.isNullOrEmpty(returnPassEngRowJSON.getString("returnDepartureTime"))){
							mtJourneyDetailsDTO.setDepartureTime(returnPassEngRowJSON.getString("returnDepartureTime")); 
						}
						if(!CPSUtils.isNullOrEmpty(returnPassEngRowJSON.getString("returnEstiDate"))){
							String dateTime= returnPassEngRowJSON.getString("returnEstiDate")+" "+returnPassEngRowJSON.getString("returnEstiTime");
							mtJourneyDetailsDTO.setEstimatedDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(dateTime)); 
							/*//for edit flexibility of journey from date to date 
							mtJourneyDetailsDTO.setRequestToDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(dateTime));*/
						}
						if(!CPSUtils.isNullOrEmpty(returnPassEngRowJSON.getString("returnEstiTime"))){
							mtJourneyDetailsDTO.setEstimatedTime(returnPassEngRowJSON.getString("returnEstiTime"));
						}
						if(!CPSUtils.isNullOrEmpty(returnPassEngRowJSON.getString("returnPickup"))){
							mtJourneyDetailsDTO.setPickupPoint(Integer.parseInt(returnPassEngRowJSON.getString("returnPickup")));
						}
						if(!CPSUtils.isNullOrEmpty(returnPassEngRowJSON.getString("returnOtherSource"))){
							mtJourneyDetailsDTO.setPickupPlace(returnPassEngRowJSON.getString("returnOtherSource")); 
						}
						if(!CPSUtils.isNullOrEmpty(returnPassEngRowJSON.getString("returnDrop"))){
							mtJourneyDetailsDTO.setDropPoint(Integer.parseInt(returnPassEngRowJSON.getString("returnDrop"))); 
						}
						if(!CPSUtils.isNullOrEmpty(returnPassEngRowJSON.getString("returnOtherDesti"))){
							mtJourneyDetailsDTO.setDropPlace(returnPassEngRowJSON.getString("returnOtherDesti"));
						}
						if(!CPSUtils.isNullOrEmpty(returnPassEngRowJSON.getString("returnAccomReq"))){
							mtJourneyDetailsDTO.setAccommReqFlag(Integer.parseInt(returnPassEngRowJSON.getString("returnAccomReq"))); 
						}
						if(returnPassEngRowJSON.getString("returnAccomReq").equals("1")){
							if(!CPSUtils.isNullOrEmpty(returnPassEngRowJSON.getString("returnAccomPlace"))){
								mtJourneyDetailsDTO.setAccommPlace(returnPassEngRowJSON.getString("returnAccomPlace"));
							}
						}
						if(!CPSUtils.isNullOrEmpty(returnPassEngRowJSON.getString("returnArtiReq"))){
							mtJourneyDetailsDTO.setArticleCarryFlag(Integer.parseInt(returnPassEngRowJSON.getString("returnArtiReq")));
						}
						if(!CPSUtils.isNullOrEmpty(returnPassEngRowJSON.getString("returnRemarks"))){
							mtJourneyDetailsDTO.setRemarks(returnPassEngRowJSON.getString("returnRemarks")); 
						}
						
						mtJourneyDetailsDTO.setJourneyTypeFlag("RETURN");
						mtJourneyDetailsDTO.setStatus(8);
						
						//set previous date 
						
						SimpleDateFormat sd=new SimpleDateFormat("dd-MMM-yyyy HH:mm");
						
						 mtJourneyDetailsDTO.setReferenceID(oldJourneyDetailsDTO.getReferenceID());
						mtJourneyDetailsDTO.setRequestFromDate(sd.parse(sd.format(oldJourneyDetailsDTO.getRequestFromDate())));
						mtJourneyDetailsDTO.setRequestToDate(sd.parse(sd.format(oldJourneyDetailsDTO.getRequestToDate())));
						mtJourneyDetailsDTO.setAllocId(oldJourneyDetailsDTO.getAllocId());
						
				
						session.saveOrUpdate(mtJourneyDetailsDTO);
						session.flush();
						
						//delete existing aricle details
						session.createQuery("delete MTArticleDetailsDTO where referenceID="+mtJourneyDetailsDTO.getId()).executeUpdate();
						session.flush();
						session.clear();
					 
					 JSONObject returnArticleTabJSON = new JSONObject(returnSubJSON.getString("returnArticleTabJSON"));
					 for(int j=1; j<=returnArticleTabJSON.length(); j++){
						 JSONObject returnArticleRowJSON = (JSONObject)(returnArticleTabJSON.get(String.valueOf(j)));
						
						 /**
						  * inserting data into mt_article_details table
						  **/
						 MTArticleDetailsDTO mtArticleDetailsDTO = new MTArticleDetailsDTO();
						 
						 	mtArticleDetailsDTO.setReferenceID(journeyId);
						 if(!CPSUtils.isNullOrEmpty(returnArticleRowJSON.getString("returnArtType"))){
							 mtArticleDetailsDTO.setType(returnArticleRowJSON.getString("returnArtType"));
						 }
						 if(!CPSUtils.isNullOrEmpty(returnArticleRowJSON.getString("returnArtLength"))){
							 mtArticleDetailsDTO.setLength(Integer.parseInt(returnArticleRowJSON.getString("returnArtLength")));
						 }
						 if(!CPSUtils.isNullOrEmpty(returnArticleRowJSON.getString("returnArtBreadth"))){
							 mtArticleDetailsDTO.setBreadth(Integer.parseInt(returnArticleRowJSON.getString("returnArtBreadth")));
						 }
						 if(!CPSUtils.isNullOrEmpty(returnArticleRowJSON.getString("returnArtHeight"))){
							 mtArticleDetailsDTO.setHeight(Integer.parseInt(returnArticleRowJSON.getString("returnArtHeight")));
						 }
						 if(!CPSUtils.isNullOrEmpty(returnArticleRowJSON.getString("returnArtWeight"))){
							 mtArticleDetailsDTO.setWeight(Integer.parseInt(returnArticleRowJSON.getString("returnArtWeight")));
						 }
						 if(!CPSUtils.isNullOrEmpty(returnArticleRowJSON.getString("returnArtQuantity"))){
							 mtArticleDetailsDTO.setQuantity(Integer.parseInt(returnArticleRowJSON.getString("returnArtQuantity")));
						 }
						 
						    mtArticleDetailsDTO.setJourneyTypeFlag("RETURN");
						 	mtArticleDetailsDTO.setStatus(1);
						 	
						 	session.save(mtArticleDetailsDTO);
						 	session.flush();
					 }
				 }
			 }
			 
			 message =CPSConstants.SUCCESS;
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return message;
	}
	public String updateVehicleAllocationDetails(MTApplicationBean mtbean) throws Exception{
		Session session = null;
		String message="";
		MTVehicleAllocationtDetailsDTO mtAllocationtDetailsDTO =null;
		MTJourneyDetailsDTO mtJourneyDetailsDTO = null;
		try {
			session = hibernateUtils.getSession();
			//get journey details for allocid
			mtJourneyDetailsDTO = (MTJourneyDetailsDTO)session.createCriteria(MTJourneyDetailsDTO.class).add(Expression.eq("id",mtbean.getJourneyId())).uniqueResult();
			
			mtAllocationtDetailsDTO=(MTVehicleAllocationtDetailsDTO)session.createCriteria(MTVehicleAllocationtDetailsDTO.class).add(Expression.eq("id",Integer.parseInt(mtJourneyDetailsDTO.getAllocId()))).uniqueResult();
			mtAllocationtDetailsDTO.setFromDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mtbean.getFromDate()+" "+mtbean.getFromTime()));
			mtAllocationtDetailsDTO.setToDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mtbean.getToDate()+" "+mtbean.getToTime()));
			mtAllocationtDetailsDTO.setVehicleDriverMapId(mtbean.getMapId());
			session.saveOrUpdate(mtAllocationtDetailsDTO);
			message=CPSConstants.SUCCESS;
				} catch (Exception e) {
					e.printStackTrace();
			throw e;
		}
		return message;
	}
	@SuppressWarnings("unchecked")
	public List getVehicleTypeList() throws Exception{
		Session session = null;
        List<KeyValueDTO> vehicleList=null;
		try {
			session = hibernateUtils.getSession();
			String qry="select type_id as id,type as name from mt_vehicle_type where status=1";
			vehicleList=session.createSQLQuery(qry).addScalar("id", Hibernate.INTEGER).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
				} catch (Exception e) {
					e.printStackTrace();
			throw e;
		}
		return vehicleList;
	}
	
}