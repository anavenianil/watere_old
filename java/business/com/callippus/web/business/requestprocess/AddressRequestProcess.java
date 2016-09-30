package com.callippus.web.business.requestprocess;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.WorkflowTxnDTO;
import com.callippus.web.beans.requests.AddressRequestBean;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.business.domainobject.AddressDomainObject;
import com.callippus.web.business.leave.request.CommonConstraints;
import com.callippus.web.business.tx.workflow.TxRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;

@Service
public class AddressRequestProcess extends RequestProcess {
	private static Log log = LogFactory.getLog(PISRequestProcess.class);
	@Autowired
	private TxRequestProcess txRequestProcess;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private AddressDomainObject addressDomainObject;
	@Autowired
	private CommonConstraints commonConstraints;

	/**
	 * This method will be called when a user requested for his personal information changes
	 * 
	 * @param prb
	 * @return
	 */
	public String initWorkflow(AddressRequestBean arb) throws Exception {
		log.debug("::<<<<<AddressRequestProcess<<<<<<Method>>>>>>>>>>>>>>>initWorkflow(PISRequestBean prb)>>>>>>>>>");
		String message = null;
		try {
			/**
			 * Generate Unique ID for this Request
			 */
			arb.setRequestID(txRequestProcess.generateUniqueID(CPSConstants.REQUEST));
			arb.setRequestType(CPSConstants.ADDRESS);
			arb.setRequestTypeID("3");// ADDRESS Request type ID = 3

			arb = setChangedValues(arb);
			message = txRequestProcess.submitTxnDetails(arb.getTxnDetails());

			if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
				/**
				 * decide the work flow & insert into history table
				 */
				RequestBean rb = new RequestBean();
				BeanUtils.copyProperties(arb, rb);
				message = txRequestProcess.initWorkflow(rb);

				/**
				 * If No workflow is assigned then update the details in the table
				 */
				if (CPSUtils.compareStrings(CPSConstants.UPDATE, message)) {
					log.debug("::request workflow not assigned, so update in the main table");
					BeanUtils.copyProperties(rb, arb);
					message = addressDomainObject.updateTxnDetails(arb);
				}
			}

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public AddressRequestBean setChangedValues(AddressRequestBean arb) throws Exception {
		Session session = null;
		Connection con = null;
		PreparedStatement ps1 = null;
		ResultSet rsq1 = null;
		JSONObject changedValues = null;
		ArrayList<WorkflowTxnDTO> txnDetails = null;
		int historyStatus=0;
		String message = null;
		try {
			session = hibernateUtils.getSession();
			con = session.connection();
			txnDetails = new ArrayList<WorkflowTxnDTO>();

			changedValues = (JSONObject) JSONSerializer.toJSON(arb.getChangedValues());

			/**
			 * When the request is from IE browser empty fields are assigned to null value
			 */
			if (CPSUtils.compareStrings(changedValues.getString("ADDRESS1"), "null")) {
				changedValues.put("ADDRESS1", "");
			}
			if (CPSUtils.compareStrings(changedValues.getString("ADDRESS2"), "null")) {
				changedValues.put("ADDRESS2", "");
			}
			if (CPSUtils.compareStrings(changedValues.getString("ADDRESS3"), "null")) {
				changedValues.put("ADDRESS3", "");
			}
			if (CPSUtils.compareStrings(changedValues.getString("CARE_OF_ADDRESS"), "null")) {
				changedValues.put("CARE_OF_ADDRESS", "");
			}
			if (CPSUtils.compareStrings(changedValues.getString("CITY"), "null")) {
				changedValues.put("CITY", "");
			}
			if (CPSUtils.compareStrings(changedValues.getString("PINCODE"), "null")) {
				changedValues.put("PINCODE", "");
			}
			if (CPSUtils.compareStrings(changedValues.getString("PHONE_NUMBER"), "null")) {
				changedValues.put("PHONE_NUMBER", "");
			}
			if (CPSUtils.compareStrings(changedValues.getString("MOBILE_NUMBER"), "null")) {
				changedValues.put("MOBILE_NUMBER", "");
			}
			if (CPSUtils.compareStrings(changedValues.getString("EMAIL"), "null")) {
				changedValues.put("EMAIL", "");
			}
			if (CPSUtils.compareStrings(changedValues.getString("NEAREST_RLY_STN"), "null")) {
				changedValues.put("NEAREST_RLY_STN", "");
			}
			if (CPSUtils.compareStrings(changedValues.getString("NEAREST_AIRPORT"), "null")) {
				changedValues.put("NEAREST_AIRPORT", "");
			}
			if (CPSUtils.compareStrings(changedValues.getString("DISPENSARY_NUMBER"), "null")) {
				changedValues.put("DISPENSARY_NUMBER", "");
			}

			String sql = "select id,address1,address2,address3,nearest_airport,CARE_OF_ADDRESS,city,state,district,pincode,phone_number,mobile_number,email,nearest_rly_stn,dispensary_number from address_details_master where sfid=? and status=1 and address_type_id=?";
			ps1 = con.prepareStatement(sql);
			ps1.setString(1, arb.getSfID());
			ps1.setString(2, arb.getAddressTypeId());
			rsq1 = ps1.executeQuery();
			if (rsq1.next()) {
				txnDetails.add(txRequestProcess.txnRowDetails(arb.getRequestTypeID(), arb.getRequestID(), rsq1.getString("id"), "SFID", arb.getIpAddress(), arb.getSfID(), arb.getSfID(), arb
						.getAddressTypeId(), CPSConstants.SFID));

				if (!CPSUtils.compareStrings(rsq1.getString("address1"), changedValues.getString("ADDRESS1"))
						&& !CPSUtils.bothNullOrNot(rsq1.getString("address1"), changedValues.getString("ADDRESS1"))) {
					// both should not be null
					// both should not be equal
					changedValues.put("ADDRESS1", CPSUtils.setEmptyIfNull(changedValues.getString("ADDRESS1")));
					//already applied request is in process or not
					historyStatus=getAddressTxnHistoryStatus(String.valueOf(rsq1.getString("id")),Integer.valueOf(arb.getAddressTypeId()),"ADDRESS1");
					if(historyStatus== 8 || historyStatus== 9 || historyStatus== 6|| historyStatus== 0){	
					txnDetails.add(txRequestProcess.txnRowDetails(arb.getRequestTypeID(), arb.getRequestID(), rsq1.getString("id"), "ADDRESS1", arb.getIpAddress(), changedValues.getString("ADDRESS1"),
							rsq1.getString("address1"), arb.getAddressTypeId(), CPSConstants.ADDRESS1));
					}else{
						message=CPSConstants.FAILED;
						arb.setRemarks(commonConstraints.setRemarkDetails(arb.getRemarks(),CPSConstants.PREVIOUSUPATIONPENDING.replace("%1%", CPSConstants.ADDRESS1).replace("%2%", CPSConstants.ADDRESS1)) );
					}
					historyStatus=0;
				}
				if (!CPSUtils.compareStrings(rsq1.getString("address2"), changedValues.getString("ADDRESS2"))
						&& !CPSUtils.bothNullOrNot(rsq1.getString("address2"), changedValues.getString("ADDRESS2"))) {
					changedValues.put("ADDRESS2", CPSUtils.setEmptyIfNull(changedValues.getString("ADDRESS2")));
					//already applied request is in process or not
					historyStatus=getAddressTxnHistoryStatus(String.valueOf(rsq1.getString("id")),Integer.valueOf(arb.getAddressTypeId()),"ADDRESS2");
					if(historyStatus== 8 || historyStatus== 9 || historyStatus== 6|| historyStatus== 0){
					txnDetails.add(txRequestProcess.txnRowDetails(arb.getRequestTypeID(), arb.getRequestID(), rsq1.getString("id"), "ADDRESS2", arb.getIpAddress(), changedValues.getString("ADDRESS2"),
							rsq1.getString("address2"), arb.getAddressTypeId(), CPSConstants.ADDRESS2));
				}else{
					message=CPSConstants.FAILED;
					arb.setRemarks(commonConstraints.setRemarkDetails(arb.getRemarks(),CPSConstants.PREVIOUSUPATIONPENDING.replace("%1%", CPSConstants.ADDRESS2).replace("%2%", CPSConstants.ADDRESS2)) );
				}
					historyStatus=0;
				}
				if (!CPSUtils.compareStrings(rsq1.getString("address3"), changedValues.getString("ADDRESS3"))
						&& !CPSUtils.bothNullOrNot(rsq1.getString("address3"), changedValues.getString("ADDRESS3"))) {
					changedValues.put("ADDRESS3", CPSUtils.setEmptyIfNull(changedValues.getString("ADDRESS3")));
					//already applied request is in process or not
					historyStatus=getAddressTxnHistoryStatus(String.valueOf(rsq1.getString("id")),Integer.valueOf(arb.getAddressTypeId()),"ADDRESS3");
					if(historyStatus== 8 || historyStatus== 9 || historyStatus== 6|| historyStatus== 0){
					txnDetails.add(txRequestProcess.txnRowDetails(arb.getRequestTypeID(), arb.getRequestID(), rsq1.getString("id"), "ADDRESS3", arb.getIpAddress(), changedValues.getString("ADDRESS3"),
							rsq1.getString("address3"), arb.getAddressTypeId(), CPSConstants.ADDRESS3));
				}else{
					message=CPSConstants.FAILED;
					arb.setRemarks(commonConstraints.setRemarkDetails(arb.getRemarks(),CPSConstants.PREVIOUSUPATIONPENDING.replace("%1%", CPSConstants.ADDRESS3).replace("%2%", CPSConstants.ADDRESS3)) );
				}
					historyStatus=0;
				}
				if (!CPSUtils.compareStrings(rsq1.getString("CARE_OF_ADDRESS"), changedValues.getString("CARE_OF_ADDRESS"))
						&& !CPSUtils.bothNullOrNot(rsq1.getString("CARE_OF_ADDRESS"), changedValues.getString("CARE_OF_ADDRESS"))) {
					changedValues.put("CARE_OF_ADDRESS", CPSUtils.setEmptyIfNull(changedValues.getString("CARE_OF_ADDRESS")));
					//already applied request is in process or not
					historyStatus=getAddressTxnHistoryStatus(String.valueOf(rsq1.getString("id")),Integer.valueOf(arb.getAddressTypeId()),"CARE_OF_ADDRESS");
					if(historyStatus== 8 || historyStatus== 9 || historyStatus== 6|| historyStatus== 0){
					txnDetails.add(txRequestProcess.txnRowDetails(arb.getRequestTypeID(), arb.getRequestID(), rsq1.getString("id"), "CARE_OF_ADDRESS", arb.getIpAddress(), changedValues
							.getString("CARE_OF_ADDRESS"), rsq1.getString("CARE_OF_ADDRESS"), arb.getAddressTypeId(), CPSConstants.CAREOFADDRESS));
				}else{
					message=CPSConstants.FAILED;
					arb.setRemarks(commonConstraints.setRemarkDetails(arb.getRemarks(),CPSConstants.PREVIOUSUPATIONPENDING.replace("%1%", CPSConstants.CAREOFADDRESS).replace("%2%", CPSConstants.CAREOFADDRESS)) );
				}
					historyStatus=0;
				}
				if (!CPSUtils.compareStrings(rsq1.getString("city"), changedValues.getString("CITY")) && !CPSUtils.bothNullOrNot(rsq1.getString("city"), changedValues.getString("CITY"))) {
					changedValues.put("CITY", CPSUtils.setEmptyIfNull(changedValues.getString("CITY")));
					//already applied request is in process or not
					historyStatus=getAddressTxnHistoryStatus(String.valueOf(rsq1.getString("id")),Integer.valueOf(arb.getAddressTypeId()),"CITY");
					if(historyStatus== 8 || historyStatus== 9 || historyStatus== 6|| historyStatus== 0){
					txnDetails.add(txRequestProcess.txnRowDetails(arb.getRequestTypeID(), arb.getRequestID(), rsq1.getString("id"), "CITY", arb.getIpAddress(), changedValues.getString("CITY"), rsq1
							.getString("city"), arb.getAddressTypeId(), CPSConstants.CITY));
				}else{
					message=CPSConstants.FAILED;
					arb.setRemarks(commonConstraints.setRemarkDetails(arb.getRemarks(),CPSConstants.PREVIOUSUPATIONPENDING.replace("%1%", CPSConstants.CITY).replace("%2%", CPSConstants.CITY)) );
				}
					historyStatus=0;
				}
				if (!CPSUtils.compareStrings(rsq1.getString("state"), changedValues.getString("STATE")) && !CPSUtils.bothNullOrNot(rsq1.getString("state"), changedValues.getString("STATE"))) {
					changedValues.put("STATE", CPSUtils.setEmptyIfNull(changedValues.getString("STATE")));
					//already applied request is in process or not
					historyStatus=getAddressTxnHistoryStatus(String.valueOf(rsq1.getString("id")),Integer.valueOf(arb.getAddressTypeId()),"STATE");
					if(historyStatus== 8 || historyStatus== 9 || historyStatus== 6|| historyStatus== 0){
					txnDetails.add(txRequestProcess.txnRowDetails(arb.getRequestTypeID(), arb.getRequestID(), rsq1.getString("id"), "STATE", arb.getIpAddress(), changedValues.getString("STATE"), rsq1
							.getString("state"), arb.getAddressTypeId(), "State"));
				}else{
					message=CPSConstants.FAILED;
					arb.setRemarks(commonConstraints.setRemarkDetails(arb.getRemarks(),CPSConstants.PREVIOUSUPATIONPENDING.replace("%1%", CPSConstants.STATE).replace("%2%", CPSConstants.STATE)) );
				}
					historyStatus=0;
				}
				if (!CPSUtils.compareStrings(rsq1.getString("district"), changedValues.getString("DISTRICT"))
						&& !CPSUtils.bothNullOrNot(rsq1.getString("district"), changedValues.getString("DISTRICT"))) {
					changedValues.put("DISTRICT", CPSUtils.setEmptyIfNull(changedValues.getString("DISTRICT")));
					//already applied request is in process or not
					historyStatus=getAddressTxnHistoryStatus(String.valueOf(rsq1.getString("id")),Integer.valueOf(arb.getAddressTypeId()),"DISTRICT");
					if(historyStatus== 8 || historyStatus== 9 || historyStatus== 6 || historyStatus== 0){
					txnDetails.add(txRequestProcess.txnRowDetails(arb.getRequestTypeID(), arb.getRequestID(), rsq1.getString("id"), "DISTRICT", arb.getIpAddress(), changedValues.getString("DISTRICT"),
							rsq1.getString("district"), arb.getAddressTypeId(), "District"));
				}else{
					message=CPSConstants.FAILED;
					arb.setRemarks(commonConstraints.setRemarkDetails(arb.getRemarks(),CPSConstants.PREVIOUSUPATIONPENDING.replace("%1%", CPSConstants.DISTRICT).replace("%2%", CPSConstants.DISTRICT)) );
				}
					historyStatus=0;
				}
				if (!CPSUtils.compareStrings(rsq1.getString("pincode"), changedValues.getString("PINCODE")) && !CPSUtils.bothNullOrNot(rsq1.getString("pincode"), changedValues.getString("PINCODE"))) {
					changedValues.put("PINCODE", CPSUtils.setEmptyIfNull(changedValues.getString("PINCODE")));
					//already applied request is in process or not
					historyStatus=getAddressTxnHistoryStatus(String.valueOf(rsq1.getString("id")),Integer.valueOf(arb.getAddressTypeId()),"PINCODE");
					if(historyStatus== 8 || historyStatus== 9 || historyStatus== 6|| historyStatus== 0){
					txnDetails.add(txRequestProcess.txnRowDetails(arb.getRequestTypeID(), arb.getRequestID(), rsq1.getString("id"), "PINCODE", arb.getIpAddress(), changedValues.getString("PINCODE"),
							rsq1.getString("pincode"), arb.getAddressTypeId(), CPSConstants.PINCODE));
				}else{
					message=CPSConstants.FAILED;
					arb.setRemarks(commonConstraints.setRemarkDetails(arb.getRemarks(),CPSConstants.PREVIOUSUPATIONPENDING.replace("%1%", CPSConstants.PINCODE).replace("%2%", CPSConstants.PINCODE)) );
				}
					historyStatus=0;
				}
				if (!CPSUtils.compareStrings(rsq1.getString("phone_number"), changedValues.getString("PHONE_NUMBER"))
						&& !CPSUtils.bothNullOrNot(rsq1.getString("phone_number"), changedValues.getString("PHONE_NUMBER"))) {
					changedValues.put("PHONE_NUMBER", CPSUtils.setEmptyIfNull(changedValues.getString("PHONE_NUMBER")));
					//already applied request is in process or not
					historyStatus=getAddressTxnHistoryStatus(String.valueOf(rsq1.getString("id")),Integer.valueOf(arb.getAddressTypeId()),"PHONE_NUMBER");
					if(historyStatus== 8 || historyStatus== 9 || historyStatus== 6|| historyStatus== 0){
					txnDetails.add(txRequestProcess.txnRowDetails(arb.getRequestTypeID(), arb.getRequestID(), rsq1.getString("id"), "PHONE_NUMBER", arb.getIpAddress(), changedValues
							.getString("PHONE_NUMBER"), rsq1.getString("phone_number"), arb.getAddressTypeId(), CPSConstants.PHONENUMBER));
				}else{
						message=CPSConstants.FAILED;
						arb.setRemarks(commonConstraints.setRemarkDetails(arb.getRemarks(),CPSConstants.PREVIOUSUPATIONPENDING.replace("%1%", CPSConstants.PHONENUMBER).replace("%2%", CPSConstants.PHONENUMBER)) );
					}
					historyStatus=0;
				}
				if (!CPSUtils.compareStrings(rsq1.getString("mobile_number"), changedValues.getString("MOBILE_NUMBER"))
						&& !CPSUtils.bothNullOrNot(rsq1.getString("mobile_number"), changedValues.getString("MOBILE_NUMBER"))) {
					changedValues.put("MOBILE_NUMBER", CPSUtils.setEmptyIfNull(changedValues.getString("MOBILE_NUMBER")));
					//already applied request is in process or not
					historyStatus=getAddressTxnHistoryStatus(String.valueOf(rsq1.getString("id")),Integer.valueOf(arb.getAddressTypeId()),"MOBILE_NUMBER");
					if(historyStatus== 8 || historyStatus== 9 || historyStatus== 6|| historyStatus== 0){
					txnDetails.add(txRequestProcess.txnRowDetails(arb.getRequestTypeID(), arb.getRequestID(), rsq1.getString("id"), "MOBILE_NUMBER", arb.getIpAddress(), changedValues
							.getString("MOBILE_NUMBER"), rsq1.getString("mobile_number"), arb.getAddressTypeId(), CPSConstants.MOBILENUMBER));
				}else{
					message=CPSConstants.FAILED;
					arb.setRemarks(commonConstraints.setRemarkDetails(arb.getRemarks(),CPSConstants.PREVIOUSUPATIONPENDING.replace("%1%", CPSConstants.MOBILENUMBER).replace("%2%", CPSConstants.MOBILENUMBER)) );
				}
					historyStatus=0;
				}
				if (!CPSUtils.compareStrings(rsq1.getString("email"), changedValues.getString("EMAIL")) && !CPSUtils.bothNullOrNot(rsq1.getString("email"), changedValues.getString("EMAIL"))) {
					changedValues.put("EMAIL", CPSUtils.setEmptyIfNull(changedValues.getString("EMAIL")));
					//already applied request is in process or not
					historyStatus=getAddressTxnHistoryStatus(String.valueOf(rsq1.getString("id")),Integer.valueOf(arb.getAddressTypeId()),"EMAIL");
					if(historyStatus== 8 || historyStatus== 9 || historyStatus== 6|| historyStatus== 0){
					txnDetails.add(txRequestProcess.txnRowDetails(arb.getRequestTypeID(), arb.getRequestID(), rsq1.getString("id"), "EMAIL", arb.getIpAddress(), changedValues.getString("EMAIL"), rsq1
							.getString("email"), arb.getAddressTypeId(), CPSConstants.EMAIL));
				}else{
					message=CPSConstants.FAILED;
					arb.setRemarks(commonConstraints.setRemarkDetails(arb.getRemarks(),CPSConstants.PREVIOUSUPATIONPENDING.replace("%1%", CPSConstants.EMAIL).replace("%2%", CPSConstants.EMAIL)) );
				}
					historyStatus=0;
				}
				if (!CPSUtils.compareStrings(rsq1.getString("nearest_rly_stn"), changedValues.getString("NEAREST_RLY_STN"))
						&& !CPSUtils.bothNullOrNot(rsq1.getString("nearest_rly_stn"), changedValues.getString("NEAREST_RLY_STN"))) {
					changedValues.put("NEAREST_RLY_STN", CPSUtils.setEmptyIfNull(changedValues.getString("NEAREST_RLY_STN")));
					//already applied request is in process or not
					historyStatus=getAddressTxnHistoryStatus(String.valueOf(rsq1.getString("id")),Integer.valueOf(arb.getAddressTypeId()),"NEAREST_RLY_STN");
					if(historyStatus== 8 || historyStatus== 9 || historyStatus== 6|| historyStatus== 0){
					txnDetails.add(txRequestProcess.txnRowDetails(arb.getRequestTypeID(), arb.getRequestID(), rsq1.getString("id"), "NEAREST_RLY_STN", arb.getIpAddress(), changedValues
							.getString("NEAREST_RLY_STN"), rsq1.getString("nearest_rly_stn"), arb.getAddressTypeId(), CPSConstants.NEARESTRLYSTN));
				}else{
					message=CPSConstants.FAILED;
					arb.setRemarks(commonConstraints.setRemarkDetails(arb.getRemarks(),CPSConstants.PREVIOUSUPATIONPENDING.replace("%1%", CPSConstants.NEARESTRLYSTN).replace("%2%", CPSConstants.NEARESTRLYSTN)) );
				}
					historyStatus=0;
				}
				if (!CPSUtils.compareStrings(rsq1.getString("nearest_airport"), changedValues.getString("NEAREST_AIRPORT"))
						&& !CPSUtils.bothNullOrNot(rsq1.getString("nearest_airport"), changedValues.getString("NEAREST_AIRPORT"))) {
					changedValues.put("NEAREST_AIRPORT", CPSUtils.setEmptyIfNull(changedValues.getString("NEAREST_AIRPORT")));
					//already applied request is in process or not
					historyStatus=getAddressTxnHistoryStatus(String.valueOf(rsq1.getString("id")),Integer.valueOf(arb.getAddressTypeId()),"NEAREST_AIRPORT");
					if(historyStatus== 8 || historyStatus== 9 || historyStatus== 6|| historyStatus== 0){
					txnDetails.add(txRequestProcess.txnRowDetails(arb.getRequestTypeID(), arb.getRequestID(), rsq1.getString("id"), "NEAREST_AIRPORT", arb.getIpAddress(), changedValues
							.getString("NEAREST_AIRPORT"), rsq1.getString("nearest_airport"), arb.getAddressTypeId(), CPSConstants.NEARESTAIRPORT));
				}else{
					message=CPSConstants.FAILED;
					arb.setRemarks(commonConstraints.setRemarkDetails(arb.getRemarks(),CPSConstants.PREVIOUSUPATIONPENDING.replace("%1%", CPSConstants.NEARESTAIRPORT).replace("%2%", CPSConstants.NEARESTAIRPORT)) );
				}
					historyStatus=0;
				}
				if (!CPSUtils.compareStrings(rsq1.getString("dispensary_number"), changedValues.getString("DISPENSARY_NUMBER"))
						&& !CPSUtils.bothNullOrNot(rsq1.getString("dispensary_number"), changedValues.getString("DISPENSARY_NUMBER"))) {
					changedValues.put("DISPENSARY_NUMBER", CPSUtils.setEmptyIfNull(changedValues.getString("DISPENSARY_NUMBER")));
					//already applied request is in process or not
					historyStatus=getAddressTxnHistoryStatus(String.valueOf(rsq1.getString("id")),Integer.valueOf(arb.getAddressTypeId()),"DISPENSARY_NUMBER");
					if(historyStatus== 8 || historyStatus== 9 || historyStatus== 6|| historyStatus== 0){
					txnDetails.add(txRequestProcess.txnRowDetails(arb.getRequestTypeID(), arb.getRequestID(), rsq1.getString("id"), "DISPENSARY_NUMBER", arb.getIpAddress(), changedValues
							.getString("DISPENSARY_NUMBER"), rsq1.getString("dispensary_number"), arb.getAddressTypeId(), CPSConstants.DISPENSARYNUMBER));
				}else{
					message=CPSConstants.FAILED;
					arb.setRemarks(commonConstraints.setRemarkDetails(arb.getRemarks(),CPSConstants.PREVIOUSUPATIONPENDING.replace("%1%", CPSConstants.DISPENSARYNUMBER).replace("%2%", CPSConstants.DISPENSARYNUMBER)) );
				}
					historyStatus=0;
				}
			} else {
				// if any address does not exist then request for adding
				txnDetails.add(txRequestProcess.txnRowDetails(arb.getRequestTypeID(), arb.getRequestID(), "0", "SFID", arb.getIpAddress(), arb.getSfID(), arb.getSfID(), arb.getAddressTypeId(),
						CPSConstants.SFID));
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("ADDRESS1"))) {
					txnDetails.add(txRequestProcess.txnRowDetails(arb.getRequestTypeID(), arb.getRequestID(), "0", "ADDRESS1", arb.getIpAddress(), changedValues.getString("ADDRESS1"), "", arb
							.getAddressTypeId(), CPSConstants.ADDRESS1));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("ADDRESS2"))) {
					txnDetails.add(txRequestProcess.txnRowDetails(arb.getRequestTypeID(), arb.getRequestID(), "0", "ADDRESS2", arb.getIpAddress(), changedValues.getString("ADDRESS2"), "", arb
							.getAddressTypeId(), CPSConstants.ADDRESS2));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("ADDRESS3"))) {
					txnDetails.add(txRequestProcess.txnRowDetails(arb.getRequestTypeID(), arb.getRequestID(), "0", "ADDRESS3", arb.getIpAddress(), changedValues.getString("ADDRESS3"), "", arb
							.getAddressTypeId(), CPSConstants.ADDRESS3));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("CARE_OF_ADDRESS"))) {
					txnDetails.add(txRequestProcess.txnRowDetails(arb.getRequestTypeID(), arb.getRequestID(), "0", "CARE_OF_ADDRESS", arb.getIpAddress(), changedValues.getString("CARE_OF_ADDRESS"), "",
							arb.getAddressTypeId(), CPSConstants.CAREOFADDRESS));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("CITY"))) {
					txnDetails.add(txRequestProcess.txnRowDetails(arb.getRequestTypeID(), arb.getRequestID(), "0", "CITY", arb.getIpAddress(), changedValues.getString("CITY"), "", arb
							.getAddressTypeId(), CPSConstants.CITY));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("STATE"))) {
					txnDetails.add(txRequestProcess.txnRowDetails(arb.getRequestTypeID(), arb.getRequestID(), "0", "STATE", arb.getIpAddress(), changedValues.getString("STATE"), "", arb
							.getAddressTypeId(), CPSConstants.STATE));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("DISTRICT"))) {
					txnDetails.add(txRequestProcess.txnRowDetails(arb.getRequestTypeID(), arb.getRequestID(), "0", "DISTRICT", arb.getIpAddress(), changedValues.getString("DISTRICT"), "", arb
							.getAddressTypeId(), CPSConstants.DISTRICT));
				}
				if (!CPSUtils.compareStrings(changedValues.getString("PINCODE"), "0")) {
					txnDetails.add(txRequestProcess.txnRowDetails(arb.getRequestTypeID(), arb.getRequestID(), "0", "PINCODE", arb.getIpAddress(), changedValues.getString("PINCODE"), "", arb
							.getAddressTypeId(), CPSConstants.PINCODE));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("PHONE_NUMBER"))) {
					txnDetails.add(txRequestProcess.txnRowDetails(arb.getRequestTypeID(), arb.getRequestID(), "0", "PHONE_NUMBER", arb.getIpAddress(), changedValues.getString("PHONE_NUMBER"), "", arb
							.getAddressTypeId(), CPSConstants.PHONENUMBER));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("MOBILE_NUMBER"))) {
					txnDetails.add(txRequestProcess.txnRowDetails(arb.getRequestTypeID(), arb.getRequestID(), "0", "MOBILE_NUMBER", arb.getIpAddress(), changedValues.getString("MOBILE_NUMBER"), "", arb
							.getAddressTypeId(), CPSConstants.MOBILENUMBER));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("EMAIL"))) {
					txnDetails.add(txRequestProcess.txnRowDetails(arb.getRequestTypeID(), arb.getRequestID(), "0", "EMAIL", arb.getIpAddress(), changedValues.getString("EMAIL"), "", arb
							.getAddressTypeId(), CPSConstants.EMAIL));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("NEAREST_RLY_STN"))) {
					txnDetails.add(txRequestProcess.txnRowDetails(arb.getRequestTypeID(), arb.getRequestID(), "0", "NEAREST_RLY_STN", arb.getIpAddress(), changedValues.getString("NEAREST_RLY_STN"), "",
							arb.getAddressTypeId(), CPSConstants.NEARESTRLYSTN));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("NEAREST_AIRPORT"))) {
					txnDetails.add(txRequestProcess.txnRowDetails(arb.getRequestTypeID(), arb.getRequestID(), "0", "NEAREST_AIRPORT", arb.getIpAddress(), changedValues.getString("NEAREST_AIRPORT"), "",
							arb.getAddressTypeId(), CPSConstants.NEARESTAIRPORT));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("ADDRESS_TYPE_ID"))) {
					txnDetails.add(txRequestProcess.txnRowDetails(arb.getRequestTypeID(), arb.getRequestID(), "0", "ADDRESS_TYPE_ID", arb.getIpAddress(), changedValues.getString("ADDRESS_TYPE_ID"), "",
							arb.getAddressTypeId(), CPSConstants.ADDRESS_TYPE_ID));
				}

			}
			if(txnDetails.size()!=0 && message!=CPSConstants.FAILED){
			arb.setTxnDetails(txnDetails);
			}
		} catch (Exception e) {
			throw e;
		}
		return arb;
	}

	/**
	 * This method will be called when a user wants to approve the request
	 * 
	 * @param prb
	 * @return
	 * @throws Exception
	 */
	public String approvedRequest(AddressRequestBean arb) throws Exception {
		log.debug("::<<<<<AddressRequestProcess<<<<<<Method>>>>>>>>>>>>>>>approvedRequest(AddressRequestBean prb)>>>>>>>>>");
		RequestBean rb = null;
		try {
			rb = new RequestBean();
			BeanUtils.copyProperties(arb, rb);
			rb = txRequestProcess.approvedRequest(rb);
			if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())) {
				log.debug("::request workflow completed, so update in the main table");
				BeanUtils.copyProperties(rb, arb);
				rb.setMessage(addressDomainObject.updateTxnDetails(arb));
			}
		} catch (Exception e) {
			throw e;
		}
		return rb.getMessage();
	}
	
	public int getAddressTxnHistoryStatus(String referenceNumber,int addressTypeId,String field) throws Exception{
		log.debug("::<<<<<AddressRequestProcess<<<<<<Method>>>>>>>>>>>>>>>getHistoryStatus(String field)>>>>>>>>>");
		Session session = null;
		int status=0;
		int historyId=0;
		try {
			session=hibernateUtils.getSession();
			if(addressTypeId==0){
				historyId=(Integer)session.createSQLQuery("SELECT nvl(MAX(GET_HISTORY_ID(REQUEST_ID)),0) as id,MAX(REQUEST_ID) FROM WORKFLOW_TXN_DETAILS WHERE COLUMN_NAME=? AND REFERENCE_NUMBER=?").addScalar("id",Hibernate.INTEGER).setString(0,field).setString(1, referenceNumber).uniqueResult();
			}else{
			historyId=(Integer)session.createSQLQuery("SELECT nvl(MAX(GET_HISTORY_ID(REQUEST_ID)),0) as id,MAX(REQUEST_ID) FROM WORKFLOW_TXN_DETAILS WHERE COLUMN_NAME=? AND REFERENCE_NUMBER=? and ROW_NUMBER=?").addScalar("id",Hibernate.INTEGER).setString(0,field).setString(1, referenceNumber).setInteger(2, addressTypeId).uniqueResult();
			}
			if(historyId!=0){
			status=((BigDecimal)session.createSQLQuery("select nvl(to_number(status),0) from REQUEST_WORKFLOW_HISTORY where id=?").setInteger(0,historyId).uniqueResult()).intValue();
			}
		} catch (Exception e) {
			throw e;
		}
		return status;
	}

}
