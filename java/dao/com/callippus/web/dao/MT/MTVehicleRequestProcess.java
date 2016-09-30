package com.callippus.web.dao.MT;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.MT.ArticleDetailsBean;
import com.callippus.web.beans.MT.MTApplicationBean;
import com.callippus.web.beans.MT.MTArticleDetailsDTO;
import com.callippus.web.beans.MT.MTJourneyDetailsDTO;
import com.callippus.web.beans.MT.MTRequestDetailsDTO;
import com.callippus.web.beans.MT.MTVehicleAllocationtDetailsDTO;
import com.callippus.web.beans.MT.MTVehicleRequestDetailsDTO;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.business.tx.workflow.TxRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;

@Service
public class MTVehicleRequestProcess {

	@Autowired
	private TxRequestProcess txRequestProcess;
	
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	
	@SuppressWarnings("unused")
	@Autowired
	private IMechincalTransportDAO iMechincalTransportDAO;
	
	 /**
	  * workflow start method
	  **/
	 @SuppressWarnings("static-access")
	public String initWorkflow(MTApplicationBean mtBean) throws Exception {
		 String message = "";
		 try{
			 mtBean.setRequestID(txRequestProcess.generateUniqueID(CPSConstants.REQUEST));
			 mtBean.setRequestType(CPSConstants.MTVEHICLEREQUESTYPE);
			 mtBean.setRequestTypeID(CPSConstants.MTVEHICLEREQUESTTYPEID);
			
			 mtBean = submitVehicleRequestDetails(mtBean);
			 
			 if(mtBean.getMessage().equals(CPSConstants.SUCCESS)){
				
				 /**
				  * inserting data into Request_workflow_history table
				  **/
				
					 RequestBean rb = new RequestBean();
					 BeanUtils.copyProperties(rb, mtBean);
					 rb.setRequestId(mtBean.getRequestID());
					 message = txRequestProcess.initWorkflow(rb);
				
					 mtBean.setMessage(CPSConstants.MTVEHICLEREQUEST);
					 
			 }else{
				 mtBean.setMessage(CPSConstants.FAILED);
			 }
		
		 }catch (Exception e) {
			e.printStackTrace();
			hibernateUtils.rollbackTransaction();
			message = CPSConstants.FAILED;
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		 return message;
	 }
	 
	 public MTApplicationBean submitVehicleRequestDetails(MTApplicationBean mtBean) throws Exception {
		 
		 Session session = null; 
		 try{
			 session = hibernateUtils.getSession();
			 
			 /**
			  * inserting data into mt_vehicle_request_details table
			  **/
			 MTRequestDetailsDTO mtRequestDetailsDTO = new MTRequestDetailsDTO();
			 mtRequestDetailsDTO.setRequestID(mtBean.getRequestID());
			 mtRequestDetailsDTO.setSfid(mtBean.getSfID());
			 mtRequestDetailsDTO.setRequestTypeID(mtBean.getRequestTypeID());
			 mtRequestDetailsDTO.setRequestType("ONLINE");
			 mtRequestDetailsDTO.setVehicleRequiredFlag(Integer.parseInt(mtBean.getVehicleReturn()));
			 mtRequestDetailsDTO.setPurposeOfVisit(mtBean.getPurposeOfVisiting());
			 mtRequestDetailsDTO.setRequestedBy(mtBean.getSfID());
			 mtRequestDetailsDTO.setRequestedDate(CPSUtils.getCurrentDateWithTime());
			 mtRequestDetailsDTO.setIpAddress(mtBean.getIpAddress());
			 mtRequestDetailsDTO.setStatus(Integer.parseInt(CPSConstants.STATUSPENDING));
			 
			 int refID = (Integer)session.save(mtRequestDetailsDTO);
			 
			 if(!CPSUtils.isNullOrEmpty(mtBean.getOnwardMainJSON()) && mtBean.getOnwardMainJSON().length()>2){
				
				 JSONObject onwardMainJSON = new JSONObject(mtBean.getOnwardMainJSON());
				 for(int i=1; i<=onwardMainJSON.length(); i++){
					 JSONObject onwardSubJSON = (JSONObject)(onwardMainJSON.get(String.valueOf(i)));
					 JSONObject onwardPassEngRowJSON = new JSONObject(onwardSubJSON.getString("onwardPassEngRowJSON"));
					 
					 /**
					  * inserting data into mt_vehicle_journey_details table
					  **/
					 		MTJourneyDetailsDTO mtJourneyDetailsDTO = new MTJourneyDetailsDTO();
					 		
					 		mtJourneyDetailsDTO.setReferenceID(refID);
					 		
						if(!CPSUtils.isNullOrEmpty(onwardPassEngRowJSON.getString("onwardNOP"))){
							 mtJourneyDetailsDTO.setNoOfPeople(Integer.parseInt(onwardPassEngRowJSON.getString("onwardNOP"))); 
						}
						if(!CPSUtils.isNullOrEmpty(onwardPassEngRowJSON.getString("onwardNameWithDesig"))){
							mtJourneyDetailsDTO.setNameWithDesignation(onwardPassEngRowJSON.getString("onwardNameWithDesig"));					 
						}
						if(!CPSUtils.isNullOrEmpty(onwardPassEngRowJSON.getString("onwardDepartureDate"))){
							String dateTime= onwardPassEngRowJSON.getString("onwardDepartureDate")+" "+onwardPassEngRowJSON.getString("onwardDepartureTime");
							mtJourneyDetailsDTO.setDepartureDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(dateTime)); 
							//for edit flexibility of journey from date to date 
							mtJourneyDetailsDTO.setRequestFromDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(dateTime));
						}
						if(!CPSUtils.isNullOrEmpty(onwardPassEngRowJSON.getString("onwardDepartureTime"))){
							mtJourneyDetailsDTO.setDepartureTime(onwardPassEngRowJSON.getString("onwardDepartureTime")); 
						}
						if(!CPSUtils.isNullOrEmpty(onwardPassEngRowJSON.getString("onwardEstiDate"))){
							String dateTime= onwardPassEngRowJSON.getString("onwardEstiDate")+" "+onwardPassEngRowJSON.getString("onwardEstiTime");
							mtJourneyDetailsDTO.setEstimatedDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(dateTime)); 
							//for edit flexibility of journey from date to date 
							mtJourneyDetailsDTO.setRequestToDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(dateTime));
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
						mtJourneyDetailsDTO.setStatus(1);
						
						int passEngID = (Integer)session.save(mtJourneyDetailsDTO);
					 
					 
					 JSONObject onwardArticleTabJSON = new JSONObject(onwardSubJSON.getString("onwardArticleTabJSON"));
					 for(int j=1; j<=onwardArticleTabJSON.length(); j++){
						 JSONObject onwardArticleRowJSON = (JSONObject)(onwardArticleTabJSON.get(String.valueOf(j)));
						
						 /**
						  * inserting data into mt_article_details table
						  **/
						 MTArticleDetailsDTO mtArticleDetailsDTO = new MTArticleDetailsDTO();
						 
						 	mtArticleDetailsDTO.setReferenceID(passEngID);
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
					 }
				 }
			 }
			 if((!CPSUtils.isNullOrEmpty(mtBean.getReturnMainJSON())) && mtBean.getReturnMainJSON().length()>2){
				
				 JSONObject returnMainJSON = new JSONObject(mtBean.getReturnMainJSON());
				 for(int i=1; i<=returnMainJSON.length(); i++){
					 JSONObject returnSubJSON = (JSONObject)(returnMainJSON.get(String.valueOf(i)));
					 JSONObject returnPassEngRowJSON = new JSONObject(returnSubJSON.getString("returnPassEngRowJSON"));
					 
					 /**
					  * inserting data into mt_vehicle_journey_details table
					  **/
					 		MTJourneyDetailsDTO mtJourneyDetailsDTO = new MTJourneyDetailsDTO();
					 		
					 		mtJourneyDetailsDTO.setReferenceID(refID);
					 		
						if(!CPSUtils.isNullOrEmpty(returnPassEngRowJSON.getString("returnNOP"))){
							 mtJourneyDetailsDTO.setNoOfPeople(Integer.parseInt(returnPassEngRowJSON.getString("returnNOP"))); 
						}
						if(!CPSUtils.isNullOrEmpty(returnPassEngRowJSON.getString("returnNameWithDesig"))){
							mtJourneyDetailsDTO.setNameWithDesignation(returnPassEngRowJSON.getString("returnNameWithDesig"));					 
						}
						if(!CPSUtils.isNullOrEmpty(returnPassEngRowJSON.getString("returnDepartureDate"))){
							String dateTime= returnPassEngRowJSON.getString("returnDepartureDate")+" "+returnPassEngRowJSON.getString("returnDepartureTime");
							mtJourneyDetailsDTO.setDepartureDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(dateTime)); 
							//for edit flexibility of journey from date to date 
							mtJourneyDetailsDTO.setRequestFromDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(dateTime));
						}
						if(!CPSUtils.isNullOrEmpty(returnPassEngRowJSON.getString("returnDepartureTime"))){
							mtJourneyDetailsDTO.setDepartureTime(returnPassEngRowJSON.getString("returnDepartureTime")); 
						}
						if(!CPSUtils.isNullOrEmpty(returnPassEngRowJSON.getString("returnEstiDate"))){
							String dateTime= returnPassEngRowJSON.getString("returnEstiDate")+" "+returnPassEngRowJSON.getString("returnEstiTime");
							mtJourneyDetailsDTO.setEstimatedDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(dateTime)); 
							//for edit flexibility of journey from date to date 
							mtJourneyDetailsDTO.setRequestToDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(dateTime));
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
						mtJourneyDetailsDTO.setStatus(1);
						
						int passEngID = (Integer)session.save(mtJourneyDetailsDTO);
					 
					 
					 JSONObject returnArticleTabJSON = new JSONObject(returnSubJSON.getString("returnArticleTabJSON"));
					 for(int j=1; j<=returnArticleTabJSON.length(); j++){
						 JSONObject returnArticleRowJSON = (JSONObject)(returnArticleTabJSON.get(String.valueOf(j)));
						
						 /**
						  * inserting data into mt_article_details table
						  **/
						 MTArticleDetailsDTO mtArticleDetailsDTO = new MTArticleDetailsDTO();
						 
						 	mtArticleDetailsDTO.setReferenceID(passEngID);
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
					 }
				 }
			 }
			 
			 		mtBean.setMessage(CPSConstants.SUCCESS);
			
			 /*session = hibernateUtils.getSession();
			 MTVehicleRequestDetailsDTO mtVehicleRequestDetailsDTO = new MTVehicleRequestDetailsDTO();
			
			mtVehicleRequestDetailsDTO.setRequestID(mtBean.getRequestID());
			mtVehicleRequestDetailsDTO.setRequestType(mtBean.getRequestType());
			mtVehicleRequestDetailsDTO.setRequestTypeID(mtBean.getRequestTypeID());
			mtVehicleRequestDetailsDTO.setSfid(mtBean.getSfID());
			mtVehicleRequestDetailsDTO.setCreatedBy(mtBean.getSfID());
			mtVehicleRequestDetailsDTO.setLastModifiedBy(mtBean.getSfID());
			mtVehicleRequestDetailsDTO.setStatus(Integer.parseInt(CPSConstants.STATUSPENDING));
			mtVehicleRequestDetailsDTO.setCreationDate(CPSUtils.getCurrentDateWithTime());
			mtVehicleRequestDetailsDTO.setLastModifiedDate(CPSUtils.getCurrentDateWithTime());
			mtVehicleRequestDetailsDTO.setIpAddress(mtBean.getIpAddress());
			
			
			mtVehicleRequestDetailsDTO.setRequestedFor(mtBean.getRequestedFor());
			mtVehicleRequestDetailsDTO.setMobileNo(mtBean.getMobileNo());
			mtVehicleRequestDetailsDTO.setNoOfPeople(mtBean.getNoOfPeople());
			mtVehicleRequestDetailsDTO.setPurposeOfVisiting(mtBean.getPurposeOfVisiting());
			mtVehicleRequestDetailsDTO.setTravellingDateTime(new SimpleDateFormat("dd-MMM-yyyy HH-mm").parse(mtBean.getTravellingDate()));
			mtVehicleRequestDetailsDTO.setEstimatedDateTime(new SimpleDateFormat("dd-MMM-yyyy HH-mm").parse(mtBean.getEstimatedDateTime()));
			mtVehicleRequestDetailsDTO.setPickupPoint(Integer.parseInt(mtBean.getPickupPoint()));
			mtVehicleRequestDetailsDTO.setOtherSourcePlace(mtBean.getOtherSourceName());
			mtVehicleRequestDetailsDTO.setDroppingPoint(Integer.parseInt(mtBean.getDroppingPoint()));
			mtVehicleRequestDetailsDTO.setOtherDestinationPlace(mtBean.getOtherDestiName());
			mtVehicleRequestDetailsDTO.setArticleCarry(Integer.parseInt(mtBean.getArticleCarry()));
			mtVehicleRequestDetailsDTO.setAccommodationRequired(Integer.parseInt(mtBean.getAccommodation()));
				if(mtBean.getAccommodation() !=null && !mtBean.getAccommodation().equals("0")){
			mtVehicleRequestDetailsDTO.setAccommodationPlace(mtBean.getAccPlace());
			}
			
			mtVehicleRequestDetailsDTO.setReturnVehicleRequired(Integer.parseInt(mtBean.getVehicleReturn()));
				if(mtBean.getVehicleReturn()!=null && !mtBean.getVehicleReturn().equals("0")){
			mtVehicleRequestDetailsDTO.setReturnVehicleSame(Integer.parseInt(mtBean.getVehicleReturnSame()));
			mtVehicleRequestDetailsDTO.setReturnNoOfPeople(mtBean.getReturnPeople());
			mtVehicleRequestDetailsDTO.setReturnTravellingDateTime(new SimpleDateFormat("dd-MMM-yyyy HH-mm").parse(mtBean.getReturnDate()));
			mtVehicleRequestDetailsDTO.setReturnEstimatedDateTime(new SimpleDateFormat("dd-MMM-yyyy HH-mm").parse(mtBean.getReturnEstimatedDateTime()));
			mtVehicleRequestDetailsDTO.setReturnPickupPoint(Integer.parseInt(mtBean.getReturnPickUpPoint()));
			mtVehicleRequestDetailsDTO.setReturnOtherSourcePlace(mtBean.getReturnOtherSourceName());
			mtVehicleRequestDetailsDTO.setReturnDroppingPoint(Integer.parseInt(mtBean.getReturnDroppingPoint()));
			mtVehicleRequestDetailsDTO.setReturnOtherDestinationPlace(mtBean.getReturnOtherDestiName());
			mtVehicleRequestDetailsDTO.setReturnArticleCarry(Integer.parseInt(mtBean.getReturnArticleCarry()));
			}
			session.saveOrUpdate(mtVehicleRequestDetailsDTO);
			
			//for saving articles
			if(mtBean.getArticleCarry() !=null && !mtBean.getArticleCarry().equals("0")){
				if(mtBean.getArticleJson() !=null){
					JSONObject jsonObj = new JSONObject(mtBean.getArticleJson());
						JSONObject tabObj = new JSONObject(jsonObj.getString("takingArticles"));
						for(int j=0;j<tabObj.length(); j++){
							ArticleDetailsBean adBean = new ArticleDetailsBean();
							JSONObject rowObj = (JSONObject)(tabObj.get(String.valueOf(j)));
							
							adBean.setRequestId(mtVehicleRequestDetailsDTO.getRequestID());
							if(!rowObj.getString("articleType").equals("")){
								adBean.setArticleType(rowObj.getString("articleType"));
							}
							if(!rowObj.getString("length").equals("")){
								adBean.setLength(Integer.parseInt(rowObj.getString("length")));
							}
							if(!rowObj.getString("breadth").equals("")){
								adBean.setBreadth(Integer.parseInt(rowObj.getString("breadth")));
							}
							if(!rowObj.getString("height").equals("")){
								adBean.setHeight(Integer.parseInt(rowObj.getString("height")));
							}
							if(!rowObj.getString("weight").equals("")){
								adBean.setWeight(Integer.parseInt(rowObj.getString("weight")));
							}
							if(!rowObj.getString("quantity").equals("")){
								adBean.setQuantity(Integer.parseInt(rowObj.getString("quantity")));
							}
							adBean.setAtricleFlag(1);
							session.save(adBean);
						}
				}
			}
			if(mtBean.getReturnArticleCarry() !=null && !mtBean.getReturnArticleCarry().equals("0")){
				if(mtBean.getArticleJson() !=null){
					JSONObject jsonObj = new JSONObject(mtBean.getArticleJson());
						JSONObject tabObj = new JSONObject(jsonObj.getString("returnArticles"));
						for(int j=0;j<tabObj.length(); j++){
							ArticleDetailsBean adBean = new ArticleDetailsBean();
							JSONObject rowObj = (JSONObject)(tabObj.get(String.valueOf(j)));
							
							adBean.setRequestId(mtVehicleRequestDetailsDTO.getRequestID());
							if(!rowObj.getString("articleType").equals("")){
								adBean.setArticleType(rowObj.getString("articleType"));
							}
							if(!rowObj.getString("length").equals("")){
								adBean.setLength(Integer.parseInt(rowObj.getString("length")));
							}
							if(!rowObj.getString("breadth").equals("")){
								adBean.setBreadth(Integer.parseInt(rowObj.getString("breadth")));
							}
							if(!rowObj.getString("height").equals("")){
								adBean.setHeight(Integer.parseInt(rowObj.getString("height")));
							}
							if(!rowObj.getString("weight").equals("")){
								adBean.setWeight(Integer.parseInt(rowObj.getString("weight")));
							}
							if(!rowObj.getString("quantity").equals("")){
								adBean.setQuantity(Integer.parseInt(rowObj.getString("quantity")));
							}
							adBean.setAtricleFlag(2);
							session.save(adBean);
						}
				}
			}
			
			mtBean.setMessage(CPSConstants.MTVEHICLEREQUEST);*/
		
		 }catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return mtBean;
	 }
	 
	 /**
	  * for request declined & cancel
	  **/
	 @SuppressWarnings("static-access")
	public String declainedRequest(String requestID, String status) throws Exception {
		 
		 String message = null;
		 Session session = null;
		 MTRequestDetailsDTO reqDTO = null;
		 try{
			 session = hibernateUtils.getSession();
			 reqDTO = (MTRequestDetailsDTO)session.createCriteria(MTRequestDetailsDTO.class).add(Expression.eq("requestID", requestID)).uniqueResult();
			 if(!CPSUtils.isNullOrEmpty(reqDTO)){
				if(CPSUtils.compareStrings(CPSConstants.STATUSCANCELLED, status)) {
					String sql = "update MTRequestDetailsDTO set status=? where requestID=?";	
					session.createQuery(sql).setString(0, CPSConstants.STATUSCANCELLED).setString(1, requestID).executeUpdate();
					message = CPSConstants.SUCCESS;
				}
				if(CPSUtils.compareStrings(CPSConstants.STATUSDECLINED, status)) {
					String sql = "update MTRequestDetailsDTO set status=? where requestID=?";	
					session.createQuery(sql).setString(0, CPSConstants.STATUSDECLINED).setString(1, requestID).executeUpdate();
					message = CPSConstants.SUCCESS;
				}
			 }
		 
		 }catch (Exception e) {
			e.printStackTrace();
			hibernateUtils.rollbackTransaction();
			message = CPSConstants.FAILED;
			throw e;
		}
	
		return message;
	 } 
	 
	 
	 /**
	  * for request approvel 
	  **/
	 @SuppressWarnings("static-access")
		public String approvedRequest(RequestBean rb) throws Exception {
			 
			 String message = null;
			 Session session = null;
			 MTRequestDetailsDTO reqDTO = null;
			 try{
				 session = hibernateUtils.getSession();
				 reqDTO = (MTRequestDetailsDTO)session.createCriteria(MTRequestDetailsDTO.class).add(Expression.eq("requestID", rb.getRequestID())).uniqueResult();
				 if(!CPSUtils.isNullOrEmpty(reqDTO)){
					 rb=txRequestProcess.approvedRequest(rb);
					 if(CPSUtils.compareStrings(CPSConstants.SUCCESS, rb.getMessage())) {
						String sql = "update MTRequestDetailsDTO set status=? where requestID=?";	
						session.createQuery(sql).setString(0, CPSConstants.STATUSAPPROVED).setString(1, rb.getRequestID()).executeUpdate();
						message = CPSConstants.SUCCESS;
						
					 }else if(CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())) {
							String sql = "update MTRequestDetailsDTO set status=? where requestID=?";	
							session.createQuery(sql).setString(0, CPSConstants.STATUSCOMPLETED).setString(1, rb.getRequestID()).executeUpdate();
							message = CPSConstants.SUCCESS;
							
					}
				 }
			 
			 }catch (Exception e) {
				e.printStackTrace();
				hibernateUtils.rollbackTransaction();
				message = CPSConstants.FAILED;
				throw e;
			}
		
			return message;
		 } 
	 /**
	  * for request approvel and sanction previous
	  **//*
	 @SuppressWarnings("static-access")
	public String approvedRequest(MTVehicleRequestDetailsDTO mtrDTO,RequestBean rb) throws Exception {
		 
		 String message = null;
		 Session session = null;
		 MTVehicleRequestDetailsDTO reqDTO = null;
			List hireList = new ArrayList();
			List returnHireList = new ArrayList();
		 try{
			 session = hibernateUtils.getSession();
			 rb.setSfID(mtrDTO.getSfid());
			 reqDTO = (MTVehicleRequestDetailsDTO)session.createCriteria(MTVehicleRequestDetailsDTO.class).add(Expression.eq("requestID", mtrDTO.getRequestID())).uniqueResult();
			 if(!CPSUtils.isNullOrEmpty(reqDTO)){
				 	rb=txRequestProcess.approvedRequest(rb);
					 if(CPSUtils.compareStrings(String.valueOf(reqDTO.getStatus()), CPSConstants.STATUSPENDING)){
						  if(CPSUtils.compareStrings(CPSConstants.SUCCESS, rb.getMessage())){
							String sql = "update MTVehicleRequestDetailsDTO set status=? where requestID=?";	
							session.createQuery(sql).setString(0, CPSConstants.STATUSAPPROVED).setString(1, mtrDTO.getRequestID()).executeUpdate();
							message = CPSConstants.SUCCESS;
						  }
						  if(CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())){
								String sql = "update MTVehicleRequestDetailsDTO set status=? where requestID=?";	
								session.createQuery(sql).setString(0, CPSConstants.STATUSAPPROVED).setString(1, mtrDTO.getRequestID()).executeUpdate();
								message = CPSConstants.SUCCESS;
								hibernateUtils.closeSession();
								session = hibernateUtils.getSession();
								reqDTO = (MTVehicleRequestDetailsDTO)session.createCriteria(MTVehicleRequestDetailsDTO.class).add(Expression.eq("requestID", mtrDTO.getRequestID())).uniqueResult();
							  }
			          }
					 
					 if(CPSUtils.compareStrings(String.valueOf(reqDTO.getStatus()), CPSConstants.STATUSAPPROVED)){
						 if(CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())){
							 mtrDTO.setAllottedDate(CPSUtils.getCurrentDateWithTime());
							 String sql = "update MTVehicleRequestDetailsDTO set status=?,allottedDate=? where requestID=?";	
								session.createQuery(sql).setString(0, CPSConstants.STATUSCOMPLETED).setDate(1,CPSUtils.getCurrentDateWithTime()).setString(2, mtrDTO.getRequestID()).executeUpdate();
								
								//for checking whether vehicle type is Govt or Hired
								if((!rb.getVehicleDriverMapId().equals("")) && (rb.getVehicleDriverMapId().contains("H"))){
									hireList = session.createQuery("select vehicleId from VehicleDetailsBean where vehicleId=? and status=1").setInteger(0, Integer.parseInt(rb.getVehicleDriverMapId().split("H")[0])).list();
								}
								if((!rb.getReturnVehicleDriverMapId().equals("")) && (rb.getReturnVehicleDriverMapId().contains("H"))){
									 returnHireList = session.createQuery("select vehicleId from VehicleDetailsBean where vehicleId=? and status=1").setInteger(0, Integer.parseInt(rb.getReturnVehicleDriverMapId().split("H")[0])).list();
								}
							
								if((hireList != null && hireList.size()==0 )&& (returnHireList !=null && returnHireList.size()==0)){
									//for inserting data into vehicle_Allocation table
									MTVehicleAllocationtDetailsDTO maDTO = new MTVehicleAllocationtDetailsDTO();
									maDTO.setRequestId(Integer.parseInt(mtrDTO.getRequestID()));
									maDTO.setFromDate(reqDTO.getTravellingDateTime());
									maDTO.setToDate(reqDTO.getEstimatedDateTime());
									maDTO.setRequestType(5);
									maDTO.setStatusFlag(8);
									maDTO.setVehicleFlag(4);
									maDTO.setVehicleDriverMapId(Integer.parseInt(rb.getVehicleDriverMapId()));
									session.saveOrUpdate(maDTO);
									
									//returnvehicleType 0-othervehicle,1-samevehicle
									if(rb.getReturnVehicleType()!=null && rb.getReturnVehicleType().equals("1")){
										MTVehicleAllocationtDetailsDTO sDTO = new MTVehicleAllocationtDetailsDTO();
										sDTO.setRequestId(Integer.parseInt(mtrDTO.getRequestID()));
										sDTO.setFromDate(reqDTO.getReturnTravellingDateTime());
										sDTO.setToDate(reqDTO.getReturnEstimatedDateTime());
										sDTO.setRequestType(5);
										sDTO.setStatusFlag(8);
										sDTO.setVehicleFlag(4);
										sDTO.setVehicleDriverMapId(Integer.parseInt(rb.getVehicleDriverMapId()));
										session.saveOrUpdate(sDTO);	
									}else if(rb.getReturnVehicleType()!=null && rb.getReturnVehicleType().equals("0")){
										MTVehicleAllocationtDetailsDTO oDTO = new MTVehicleAllocationtDetailsDTO();
										oDTO.setRequestId(Integer.parseInt(mtrDTO.getRequestID()));
										oDTO.setFromDate(reqDTO.getReturnTravellingDateTime());
										oDTO.setToDate(reqDTO.getReturnEstimatedDateTime());
										oDTO.setRequestType(5);
										oDTO.setStatusFlag(8);
										oDTO.setVehicleFlag(4);
										oDTO.setVehicleDriverMapId(Integer.parseInt(rb.getReturnVehicleDriverMapId()));
										session.saveOrUpdate(oDTO);	
									}
									String daysDiff = CPSUtils.daysDifferenceWithTimeWithOutMonthString(reqDTO.getTravellingDateTime().toString(), reqDTO.getEstimatedDateTime().toString());
									List<MTVehicleAllocationtDetailsDTO> aDTOList = iMechincalTransportDAO.getVehicleFreeDetails(reqDTO.getTravellingDateTime(),reqDTO.getEstimatedDateTime(),rb.getVehicleDriverMapId(),session);
									if(daysDiff.equals("0")){
											if(aDTOList.size()==0){
												Object[] resDates = getDateStartingTimeEndingTime(reqDTO.getTravellingDateTime().toString(), reqDTO.getEstimatedDateTime().toString());
												message = saveAllocationDetails(session,"",(Date)resDates[0],(Date)resDates[1],5,8,3,rb.getVehicleDriverMapId());
												if((!message.equals("")) && message.equals("InsertionSuccess")){
														message = saveAllocationDetails(session,"",(Date)resDates[2],(Date)resDates[3],5,8,3,rb.getVehicleDriverMapId());	
														message = saveAllocationDetails(session,mtrDTO.getRequestID(),reqDTO.getTravellingDateTime(),reqDTO.getEstimatedDateTime(),5,8,4,rb.getVehicleDriverMapId());
														message = CPSConstants.SUCCESS;
												  }else{
														message = "";
													}
											}else{
												Object[] queryDates1 = getDateStartingTimeEndingTime(reqDTO.getTravellingDateTime().toString(), reqDTO.getTravellingDateTime().toString());
												Object[] queryDates2 = getDateStartingTimeEndingTime(reqDTO.getEstimatedDateTime().toString(), reqDTO.getEstimatedDateTime().toString());
												//Object[] queryDates3 = getDateStartingTimeEndingTime(CPSUtils.formattedDateWithTime(aDTOList.get(0).getStringToDate()), CPSUtils.formattedDateWithTime(aDTOList.get(0).getStringToDate()));
												message = updateAllocationDetails(session,new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(aDTOList.get(0).getStringFromDate()),(Date)queryDates1[1],aDTOList.get(0).getId(),5,8,3,rb.getVehicleDriverMapId(),"");
												if((!message.equals("")) && message.equals("UpdateSuccess")){
													message = saveAllocationDetails(session,mtrDTO.getRequestID(),reqDTO.getTravellingDateTime(),reqDTO.getEstimatedDateTime(),5,8,4,rb.getVehicleDriverMapId());
													message = saveAllocationDetails(session,"",(Date)queryDates2[2],new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(aDTOList.get(0).getStringToDate()),5,8,3,rb.getVehicleDriverMapId());
													message = CPSConstants.SUCCESS;
												}
											}
									}else{
										if(aDTOList.size()>0){
											Object[] queryDates1 = getDateStartingTimeEndingTime(reqDTO.getTravellingDateTime().toString(), reqDTO.getTravellingDateTime().toString());
											Object[] queryDates2 = getDateStartingTimeEndingTime(reqDTO.getEstimatedDateTime().toString(), reqDTO.getEstimatedDateTime().toString());
											if(aDTOList.size()==1){
												message = updateAllocationDetails(session,new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(aDTOList.get(0).getStringFromDate()),(Date)queryDates1[1],aDTOList.get(0).getId(),5,8,3,rb.getVehicleDriverMapId(),"");	
												if((!message.equals("")) && message.equals("UpdateSuccess")){
													message = saveAllocationDetails(session,mtrDTO.getRequestID(),reqDTO.getTravellingDateTime(),new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(aDTOList.get(0).getStringToDate()),5,8,4,rb.getVehicleDriverMapId());
													message = saveAllocationDetails(session,mtrDTO.getRequestID(),(Date)queryDates2[0],reqDTO.getEstimatedDateTime(),5,8,4,rb.getVehicleDriverMapId());
													message = saveAllocationDetails(session,"",(Date)queryDates2[2],(Date)queryDates2[3],5,8,3,rb.getVehicleDriverMapId());
													message = CPSConstants.SUCCESS;
												}
											}else if(aDTOList.size()==2){
												message = updateAllocationDetails(session,new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(aDTOList.get(0).getStringFromDate()),(Date)queryDates1[1],aDTOList.get(0).getId(),5,8,3,rb.getVehicleDriverMapId(),"");
												message = updateAllocationDetails(session,new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(aDTOList.get(1).getStringFromDate()),reqDTO.getEstimatedDateTime(),aDTOList.get(1).getId(),5,8,4,rb.getVehicleDriverMapId(),mtrDTO.getRequestID());
												if((!message.equals("")) && message.equals("UpdateSuccess")){
													message = saveAllocationDetails(session,mtrDTO.getRequestID(),reqDTO.getTravellingDateTime(),new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(aDTOList.get(0).getStringToDate()),5,8,4,rb.getVehicleDriverMapId());
													message = saveAllocationDetails(session,"",(Date)queryDates2[2],new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(aDTOList.get(1).getStringToDate()),5,8,3,rb.getVehicleDriverMapId());
												}
											}
											
									  }else{
										  	Object[] resDates1 = getDateStartingTimeEndingTime(reqDTO.getTravellingDateTime().toString(), reqDTO.getTravellingDateTime().toString());
											Object[] resDates2 = getDateStartingTimeEndingTime(reqDTO.getEstimatedDateTime().toString(), reqDTO.getEstimatedDateTime().toString());
											message = saveAllocationDetails(session,"",(Date)resDates1[0],(Date)resDates1[1],5,8,3,rb.getVehicleDriverMapId());
											if((!message.equals("")) && message.equals("InsertionSuccess")){
												message = saveAllocationDetails(session,mtrDTO.getRequestID(),reqDTO.getTravellingDateTime(),(Date)resDates1[3],5,8,4,rb.getVehicleDriverMapId());
												message = saveAllocationDetails(session,mtrDTO.getRequestID(),(Date)resDates2[0],reqDTO.getEstimatedDateTime(),5,8,4,rb.getVehicleDriverMapId());
												message = saveAllocationDetails(session,"",(Date)resDates2[2],(Date)resDates2[3],5,8,3,rb.getVehicleDriverMapId());
												message = CPSConstants.SUCCESS;	
												
											}else{
												message = "";
											} 
									   }
									}
									
									//returnvehicleType 0-othervehicle,1-samevehicle
									
									if(rb.getReturnVehicleType()!=null && rb.getReturnVehicleType().equals("1")){
										String returndaysDiff = CPSUtils.daysDifferenceWithTimeWithOutMonthString(reqDTO.getReturnTravellingDateTime().toString(), reqDTO.getReturnEstimatedDateTime().toString());
										List<MTVehicleAllocationtDetailsDTO> aDTOList1 = iMechincalTransportDAO.getVehicleFreeDetails(reqDTO.getReturnTravellingDateTime(),reqDTO.getReturnEstimatedDateTime(),rb.getVehicleDriverMapId(),session);
										if(returndaysDiff.equals("0")){
												if(aDTOList1.size()==0){
													Object[] resDates = getDateStartingTimeEndingTime(reqDTO.getReturnTravellingDateTime().toString(), reqDTO.getReturnEstimatedDateTime().toString());
													message = saveAllocationDetails(session,"",(Date)resDates[0],(Date)resDates[1],5,8,3,rb.getVehicleDriverMapId());
													if((!message.equals("")) && message.equals("InsertionSuccess")){
															message = saveAllocationDetails(session,"",(Date)resDates[2],(Date)resDates[3],5,8,3,rb.getVehicleDriverMapId());	
															message = saveAllocationDetails(session,mtrDTO.getRequestID(),reqDTO.getReturnTravellingDateTime(),reqDTO.getReturnEstimatedDateTime(),5,8,4,rb.getVehicleDriverMapId());
															message = CPSConstants.SUCCESS;
													  }else{
															message = "";
														}
												}else{
													Object[] queryDates1 = getDateStartingTimeEndingTime(reqDTO.getReturnTravellingDateTime().toString(), reqDTO.getReturnTravellingDateTime().toString());
													Object[] queryDates2 = getDateStartingTimeEndingTime(reqDTO.getReturnEstimatedDateTime().toString(), reqDTO.getReturnEstimatedDateTime().toString());
													//Object[] queryDates3 = getDateStartingTimeEndingTime(CPSUtils.formattedDateWithTime(aDTOList1.get(0).getStringToDate()), CPSUtils.formattedDateWithTime(aDTOList1.get(0).getStringToDate()));
													message = updateAllocationDetails(session,new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(aDTOList1.get(0).getStringFromDate()),(Date)queryDates1[1],aDTOList1.get(0).getId(),5,8,3,rb.getVehicleDriverMapId(),"");
													if((!message.equals("")) && message.equals("UpdateSuccess")){
														message = saveAllocationDetails(session,mtrDTO.getRequestID(),reqDTO.getReturnTravellingDateTime(),reqDTO.getReturnEstimatedDateTime(),5,8,4,rb.getVehicleDriverMapId());
														message = saveAllocationDetails(session,"",(Date)queryDates2[2],new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(aDTOList1.get(0).getStringToDate()),5,8,3,rb.getVehicleDriverMapId());
														message = CPSConstants.SUCCESS;
													}
												}
										}else{
											if(aDTOList1.size()>0){
												Object[] queryDates1 = getDateStartingTimeEndingTime(reqDTO.getReturnTravellingDateTime().toString(), reqDTO.getReturnTravellingDateTime().toString());
												Object[] queryDates2 = getDateStartingTimeEndingTime(reqDTO.getReturnEstimatedDateTime().toString(), reqDTO.getReturnEstimatedDateTime().toString());
												if(aDTOList1.size()==1){
													message = updateAllocationDetails(session,new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(aDTOList1.get(0).getStringFromDate()),(Date)queryDates1[1],aDTOList1.get(0).getId(),5,8,3,rb.getVehicleDriverMapId(),"");	
													if((!message.equals("")) && message.equals("UpdateSuccess")){
														message = saveAllocationDetails(session,mtrDTO.getRequestID(),reqDTO.getReturnTravellingDateTime(),new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(aDTOList1.get(0).getStringToDate()),5,8,4,rb.getVehicleDriverMapId());
														message = saveAllocationDetails(session,mtrDTO.getRequestID(),(Date)queryDates2[0],reqDTO.getReturnEstimatedDateTime(),5,8,4,rb.getVehicleDriverMapId());
														message = saveAllocationDetails(session,"",(Date)queryDates2[2],(Date)queryDates2[3],5,8,3,rb.getVehicleDriverMapId());
														message = CPSConstants.SUCCESS;
													}
												}else if(aDTOList1.size()==2){
													message = updateAllocationDetails(session,new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(aDTOList1.get(0).getStringFromDate()),(Date)queryDates1[1],aDTOList1.get(0).getId(),5,8,3,rb.getVehicleDriverMapId(),"");
													message = updateAllocationDetails(session,new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(aDTOList1.get(1).getStringFromDate()),reqDTO.getReturnEstimatedDateTime(),aDTOList1.get(1).getId(),5,8,4,rb.getVehicleDriverMapId(),mtrDTO.getRequestID());
													if((!message.equals("")) && message.equals("UpdateSuccess")){
														message = saveAllocationDetails(session,mtrDTO.getRequestID(),reqDTO.getReturnTravellingDateTime(),new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(aDTOList1.get(0).getStringToDate()),5,8,4,rb.getVehicleDriverMapId());
														message = saveAllocationDetails(session,"",(Date)queryDates2[2],new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(aDTOList1.get(1).getStringToDate()),5,8,3,rb.getVehicleDriverMapId());
													}
												}
												
										  }else{
											  	Object[] resDates1 = getDateStartingTimeEndingTime(reqDTO.getReturnTravellingDateTime().toString(), reqDTO.getReturnTravellingDateTime().toString());
												Object[] resDates2 = getDateStartingTimeEndingTime(reqDTO.getReturnEstimatedDateTime().toString(), reqDTO.getReturnEstimatedDateTime().toString());
												message = saveAllocationDetails(session,"",(Date)resDates1[0],(Date)resDates1[1],5,8,3,rb.getVehicleDriverMapId());
												if((!message.equals("")) && message.equals("InsertionSuccess")){
													message = saveAllocationDetails(session,mtrDTO.getRequestID(),reqDTO.getReturnTravellingDateTime(),(Date)resDates1[3],5,8,4,rb.getVehicleDriverMapId());
													message = saveAllocationDetails(session,mtrDTO.getRequestID(),(Date)resDates2[0],reqDTO.getReturnEstimatedDateTime(),5,8,4,rb.getVehicleDriverMapId());
													message = saveAllocationDetails(session,"",(Date)resDates2[2],(Date)resDates2[3],5,8,3,rb.getVehicleDriverMapId());
													message = CPSConstants.SUCCESS;	
													
												}else{
													message = "";
												} 
										   }
										}
										MTVehicleAllocationtDetailsDTO sDTO = new MTVehicleAllocationtDetailsDTO();
										sDTO.setRequestId(Integer.parseInt(mtrDTO.getRequestID()));
										sDTO.setFromDate(reqDTO.getReturnTravellingDateTime());
										sDTO.setToDate(reqDTO.getReturnEstimatedDateTime());
										sDTO.setRequestType(5);
										sDTO.setStatusFlag(8);
										sDTO.setVehicleFlag(4);
										sDTO.setVehicleDriverMapId(Integer.parseInt(rb.getVehicleDriverMapId()));
										session.saveOrUpdate(sDTO);	
									}else if(rb.getReturnVehicleType()!=null && rb.getReturnVehicleType().equals("0")){
										
										String returndaysDiff = CPSUtils.daysDifferenceWithTimeWithOutMonthString(reqDTO.getReturnTravellingDateTime().toString(), reqDTO.getReturnEstimatedDateTime().toString());
										List<MTVehicleAllocationtDetailsDTO> aDTOList1 = iMechincalTransportDAO.getVehicleFreeDetails(reqDTO.getReturnTravellingDateTime(),reqDTO.getReturnEstimatedDateTime(),rb.getReturnVehicleDriverMapId(),session);
										if(returndaysDiff.equals("0")){
												if(aDTOList1.size()==0){
													Object[] resDates = getDateStartingTimeEndingTime(reqDTO.getReturnTravellingDateTime().toString(), reqDTO.getReturnEstimatedDateTime().toString());
													message = saveAllocationDetails(session,"",(Date)resDates[0],(Date)resDates[1],5,8,3,rb.getReturnVehicleDriverMapId());
													if((!message.equals("")) && message.equals("InsertionSuccess")){
															message = saveAllocationDetails(session,"",(Date)resDates[2],(Date)resDates[3],5,8,3,rb.getReturnVehicleDriverMapId());	
															message = saveAllocationDetails(session,mtrDTO.getRequestID(),reqDTO.getReturnTravellingDateTime(),reqDTO.getReturnEstimatedDateTime(),5,8,4,rb.getReturnVehicleDriverMapId());
															message = CPSConstants.SUCCESS;
													  }else{
															message = "";
														}
												}else{
													Object[] queryDates1 = getDateStartingTimeEndingTime(reqDTO.getReturnTravellingDateTime().toString(), reqDTO.getReturnTravellingDateTime().toString());
													Object[] queryDates2 = getDateStartingTimeEndingTime(reqDTO.getReturnEstimatedDateTime().toString(), reqDTO.getReturnEstimatedDateTime().toString());
													//Object[] queryDates3 = getDateStartingTimeEndingTime(CPSUtils.formattedDateWithTime(aDTOList1.get(0).getStringToDate()), CPSUtils.formattedDateWithTime(aDTOList1.get(0).getStringToDate()));
													message = updateAllocationDetails(session,new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(aDTOList1.get(0).getStringFromDate()),(Date)queryDates1[1],aDTOList1.get(0).getId(),5,8,3,rb.getReturnVehicleDriverMapId(),"");
													if((!message.equals("")) && message.equals("UpdateSuccess")){
														message = saveAllocationDetails(session,mtrDTO.getRequestID(),reqDTO.getReturnTravellingDateTime(),reqDTO.getReturnEstimatedDateTime(),5,8,4,rb.getReturnVehicleDriverMapId());
														message = saveAllocationDetails(session,"",(Date)queryDates2[2],new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(aDTOList1.get(0).getStringToDate()),5,8,3,rb.getReturnVehicleDriverMapId());
														message = CPSConstants.SUCCESS;
													}
												}
										}else{
											if(aDTOList1.size()>0){
												Object[] queryDates1 = getDateStartingTimeEndingTime(reqDTO.getReturnTravellingDateTime().toString(), reqDTO.getReturnTravellingDateTime().toString());
												Object[] queryDates2 = getDateStartingTimeEndingTime(reqDTO.getReturnEstimatedDateTime().toString(), reqDTO.getReturnEstimatedDateTime().toString());
												if(aDTOList1.size()==1){
													message = updateAllocationDetails(session,new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(aDTOList1.get(0).getStringFromDate()),(Date)queryDates1[1],aDTOList1.get(0).getId(),5,8,3,rb.getReturnVehicleDriverMapId(),"");	
													if((!message.equals("")) && message.equals("UpdateSuccess")){
														message = saveAllocationDetails(session,mtrDTO.getRequestID(),reqDTO.getReturnTravellingDateTime(),new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(aDTOList1.get(0).getStringToDate()),5,8,4,rb.getReturnVehicleDriverMapId());
														message = saveAllocationDetails(session,mtrDTO.getRequestID(),(Date)queryDates2[0],reqDTO.getReturnEstimatedDateTime(),5,8,4,rb.getReturnVehicleDriverMapId());
														message = saveAllocationDetails(session,"",(Date)queryDates2[2],(Date)queryDates2[3],5,8,3,rb.getReturnVehicleDriverMapId());
														message = CPSConstants.SUCCESS;
													}
												}else if(aDTOList1.size()==2){
													message = updateAllocationDetails(session,new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(aDTOList1.get(0).getStringFromDate()),(Date)queryDates1[1],aDTOList1.get(0).getId(),5,8,3,rb.getReturnVehicleDriverMapId(),"");
													message = updateAllocationDetails(session,new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(aDTOList1.get(1).getStringFromDate()),reqDTO.getReturnEstimatedDateTime(),aDTOList1.get(1).getId(),5,8,4,rb.getReturnVehicleDriverMapId(),mtrDTO.getRequestID());
													if((!message.equals("")) && message.equals("UpdateSuccess")){
														message = saveAllocationDetails(session,mtrDTO.getRequestID(),reqDTO.getReturnTravellingDateTime(),new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(aDTOList1.get(0).getStringToDate()),5,8,4,rb.getReturnVehicleDriverMapId());
														message = saveAllocationDetails(session,"",(Date)queryDates2[2],new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(aDTOList1.get(1).getStringToDate()),5,8,3,rb.getReturnVehicleDriverMapId());
													}
												}
												
										  }else{
											  	Object[] resDates1 = getDateStartingTimeEndingTime(reqDTO.getReturnTravellingDateTime().toString(), reqDTO.getReturnTravellingDateTime().toString());
												Object[] resDates2 = getDateStartingTimeEndingTime(reqDTO.getReturnEstimatedDateTime().toString(), reqDTO.getReturnEstimatedDateTime().toString());
												message = saveAllocationDetails(session,"",(Date)resDates1[0],(Date)resDates1[1],5,8,3,rb.getReturnVehicleDriverMapId());
												if((!message.equals("")) && message.equals("InsertionSuccess")){
													message = saveAllocationDetails(session,mtrDTO.getRequestID(),reqDTO.getReturnTravellingDateTime(),(Date)resDates1[3],5,8,4,rb.getReturnVehicleDriverMapId());
													message = saveAllocationDetails(session,mtrDTO.getRequestID(),(Date)resDates2[0],reqDTO.getReturnEstimatedDateTime(),5,8,4,rb.getReturnVehicleDriverMapId());
													message = saveAllocationDetails(session,"",(Date)resDates2[2],(Date)resDates2[3],5,8,3,rb.getReturnVehicleDriverMapId());
													message = CPSConstants.SUCCESS;	
													
												}else{
													message = "";
												} 
										   }
										}
										MTVehicleAllocationtDetailsDTO oDTO = new MTVehicleAllocationtDetailsDTO();
										oDTO.setRequestId(Integer.parseInt(mtrDTO.getRequestID()));
										oDTO.setFromDate(reqDTO.getReturnTravellingDateTime());
										oDTO.setToDate(reqDTO.getReturnEstimatedDateTime());
										oDTO.setRequestType(5);
										oDTO.setStatusFlag(8);
										oDTO.setVehicleFlag(4);
										oDTO.setVehicleDriverMapId(Integer.parseInt(rb.getReturnVehicleDriverMapId()));
										session.saveOrUpdate(oDTO);	
									}
								}else{
								  if(hireList != null && hireList.size()>0){
									  session.createSQLQuery("UPDATE MT_VEHICLE_MASTER SET TRAVELLING_REQUEST_ID=? WHERE ID=?").setString(0, mtrDTO.getRequestID()).setInteger(1, Integer.parseInt(rb.getVehicleDriverMapId().split("H")[0])).executeUpdate();
								  }
								  if(returnHireList != null && returnHireList.size()>0){
									  session.createSQLQuery("UPDATE MT_VEHICLE_MASTER SET RETURN_TRAVELLING_REQUEST_ID=? WHERE ID=?").setString(0, mtrDTO.getRequestID()).setInteger(1, Integer.parseInt(rb.getReturnVehicleDriverMapId().split("H")[0])).executeUpdate();
								  }
								 
							  }
							
						 }
					 }
			 }
		 }catch (Exception e) {
			e.printStackTrace();
			hibernateUtils.rollbackTransaction();
			message = CPSConstants.FAILED;
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return message;
	 }
	 */
	 //inserting data into Allocation Table
	 public static String saveAllocationDetails(Session session,String reqId, Date fdate,Date tdate,int reqType, int statusFlag,int vehicleFlag,String mapId) throws Exception{
		 String message ="";
		 try{
			 MTVehicleAllocationtDetailsDTO maDTO = new MTVehicleAllocationtDetailsDTO();
				if(!reqId.equals("")){
					maDTO.setRequestId(Integer.parseInt(reqId));	
				}else{
					maDTO.setRequestId(0);
				}
			 
				maDTO.setFromDate(fdate);
				maDTO.setToDate(tdate);
				maDTO.setRequestType(reqType);
				maDTO.setStatusFlag(statusFlag);
				maDTO.setVehicleFlag(vehicleFlag);
				maDTO.setVehicleDriverMapId(Integer.parseInt(mapId));
				session.saveOrUpdate(maDTO);
				message = "InsertionSuccess";
		 }catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		 return message;
	 }
	 //updating data into Allocation Table
	 public static String updateAllocationDetails(Session session,Date fdate,Date tdate,int id, int reqType,int statusFlag, int vehicleFlag,String mapId,String reqId) throws Exception{
		 String message ="";
		 try{
			 MTVehicleAllocationtDetailsDTO maDTO = new MTVehicleAllocationtDetailsDTO();
			    maDTO.setId(id);
				maDTO.setFromDate(fdate);
				maDTO.setToDate(tdate);
				if(!reqId.equals("")){
					maDTO.setRequestId(Integer.parseInt(reqId));
				}
			
				maDTO.setRequestType(reqType);
				maDTO.setStatusFlag(statusFlag);
				maDTO.setVehicleFlag(vehicleFlag);
				maDTO.setVehicleDriverMapId(Integer.parseInt(mapId));
				session.update(maDTO);
				message = "UpdateSuccess";
		 }catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		 return message;
	 }
	/* *//**
	  * for request declined & cancel
	  **//*
	 @SuppressWarnings("static-access")
	public String updateStatusToDecline(String requestID , String requestType , String status) throws Exception {
		 
		 String message = null;
		 Session session = null;
		 MTVehicleRequestDetailsDTO reqDTO = null;
		 try{
			 session = hibernateUtils.getSession();
			 reqDTO = (MTVehicleRequestDetailsDTO)session.createCriteria(MTVehicleRequestDetailsDTO.class).add(Expression.eq("requestID", requestID)).uniqueResult();
			 if(!CPSUtils.isNullOrEmpty(reqDTO)){
				if(CPSUtils.compareStrings(CPSConstants.STATUSCANCELLED, status)) {
					String sql = "update MTVehicleRequestDetailsDTO set status=? where requestID=?";	
					session.createQuery(sql).setString(0, CPSConstants.STATUSCANCELLED).setString(1, requestID).executeUpdate();
					message = CPSConstants.SUCCESS;
				}
				if(CPSUtils.compareStrings(CPSConstants.STATUSDECLINED, status)) {
					String sql = "update MTVehicleRequestDetailsDTO set status=? where requestID=?";	
					session.createQuery(sql).setString(0, CPSConstants.STATUSDECLINED).setString(1, requestID).executeUpdate();
					message = CPSConstants.SUCCESS;
				}
			 }
		 
		 }catch (Exception e) {
			e.printStackTrace();
			hibernateUtils.rollbackTransaction();
			message = CPSConstants.FAILED;
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
	
		return message;
	 }*/
	 public static Object[] getDateStartingTimeEndingTime(String tdate, String edate) throws Exception{
		
			SimpleDateFormat sfd = new SimpleDateFormat("dd-MMM-yyyy HH-mm");
			Object strDate[] = new Object[4];
			try{
			String tdateArr[] = new String[3];
			tdateArr = tdate.split(" ")[0].split("-");
			String ttimeArr[] = new String[3];
			ttimeArr = tdate.split(" ")[1].split(":");
			String edateArr[] = new String[3];
			edateArr = edate.split(" ")[0].split("-");
			String etimeArr[] = new String[3];
			etimeArr = edate.split(" ")[1].split(":");
			
			Calendar tcal = Calendar.getInstance();
			tcal.set(Integer.valueOf(tdateArr[0]) , Integer.valueOf(tdateArr[1]) - 1, Integer.valueOf(tdateArr[2]), 0, 0, 0);
			Calendar tecal = Calendar.getInstance();
			tecal.set(Integer.valueOf(tdateArr[0]) , Integer.valueOf(tdateArr[1]) - 1, Integer.valueOf(tdateArr[2]), Integer.valueOf(ttimeArr[1])==0 ? Integer.valueOf(ttimeArr[0])-1 : Integer.valueOf(ttimeArr[0]), Integer.valueOf(ttimeArr[1])==0 ? 59 : Integer.valueOf(ttimeArr[1])-1, 0);
			Calendar ecal = Calendar.getInstance();
			ecal.set(Integer.valueOf(edateArr[0]) , Integer.valueOf(edateArr[1]) - 1, Integer.valueOf(edateArr[2]),Integer.valueOf(etimeArr[0]),Integer.valueOf(etimeArr[1])+1 , 0);
			Calendar eecal = Calendar.getInstance();
			eecal.set(Integer.valueOf(edateArr[0]) , Integer.valueOf(edateArr[1]) - 1, Integer.valueOf(edateArr[2]), 23,59, 0);
			    strDate[0] = sfd.parse(sfd.format(tcal.getTime()));
			    strDate[1] = sfd.parse(sfd.format(tecal.getTime()));
			    strDate[2] = sfd.parse(sfd.format(ecal.getTime()));
				strDate[3] = sfd.parse(sfd.format(eecal.getTime()));
			}catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			return strDate;
		}
}
