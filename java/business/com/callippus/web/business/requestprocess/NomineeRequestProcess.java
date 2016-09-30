package com.callippus.web.business.requestprocess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.WorkflowTxnDTO;
import com.callippus.web.beans.requests.NomineeRequestBean;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.business.domainobject.NomineeDomainObject;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.ConnectionUtil;

@Service
public class NomineeRequestProcess extends RequestProcess {
	private static Log log = LogFactory.getLog(PISRequestProcess.class);
	@Autowired
	private RequestProcess requestProcess;
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private NomineeDomainObject nomineeDomainObject;

	/**
	 * This method will be called when a user requested for his personal information changes
	 * 
	 * @param prb
	 * @return
	 */
	public String initWorkflow(NomineeRequestBean nrb) throws Exception {
		log.debug("::<<<<<NomineeRequestProcess<<<<<<Method>>>>>>>>>>>>>>>initWorkflow(NomineeRequestBean prb)>>>>>>>>>");
		String message = null;
		try {
			/**
			 * Generate Unique ID for this Request
			 */
			nrb.setRequestID(requestProcess.generateUniqueID(CPSConstants.REQUEST));
			nrb.setRequestType(CPSConstants.NOMINEE);
			nrb.setRequestTypeID("5");// Nominee Request type ID = 5

			if (CPSUtils.compareStrings(CPSConstants.SELECT, nrb.getStateId())) {
				nrb.setStateId(null);
			}
			if (CPSUtils.compareStrings(CPSConstants.SELECT, nrb.getDistrictId())) {
				nrb.setDistrictId(null);
			}
			nrb = setChangedValues(nrb);
			message = requestProcess.submitTxnDetails(nrb.getTxnDetails());
			if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
				nrb = insertIncontengensyDetails(nrb);
				message = requestProcess.submitTxnDetails(nrb.getTxnDetails());
			}
			if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
				/**
				 * decide the work flow & insert into history table
				 */
				RequestBean rb = new RequestBean();
				BeanUtils.copyProperties(nrb, rb);
				message = requestProcess.initWorkflow(rb);

				/**
				 * If No workflow is assigned then update the details in the table
				 */
				if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())) {
					log.debug("::request workflow not assigned, so update in the main table");
					BeanUtils.copyProperties(rb, nrb);
					message = nomineeDomainObject.updateTxnDetails(nrb);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public NomineeRequestBean setChangedValues(NomineeRequestBean nrb) throws Exception {
		Session session = null;
		Connection con = null;
		PreparedStatement ps1 = null;
		ResultSet rsq1 = null;
		PreparedStatement ps2 = null;
		ResultSet rsq2 = null;
		ArrayList<WorkflowTxnDTO> txnDetails = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			txnDetails = new ArrayList<WorkflowTxnDTO>();
			/*
			 * if (CPSUtils.compareStrings(nrb.getStateId(), CPSConstants.SELECT)) { nrb.setStateId(null); } if (CPSUtils.compareStrings(nrb.getDistrictId(), CPSConstants.SELECT)) {
			 * nrb.setDistrictId(null); }
			 */
			txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), nrb.getNomineeID(), "SFID", nrb.getIpAddress(), nrb.getSfID(), nrb.getSfID(), "0",
					CPSConstants.SFID));

			String sql = "select sfid,nominee,percentage,remarks,TO_CHAR(date_of_nominate,'DD-MON-YYYY') rdate_of_nominate,family_member_id,nominee_type_id from nominee_details where id=?";
			ps1 = con.prepareStatement(sql);
			ps1.setString(1, nrb.getNomineeID());
			rsq1 = ps1.executeQuery();
			if (rsq1.next()) {
				// if
				// (!CPSUtils.compareStrings(rsq1.getString("nominee_type_id"),
				// nrb.getNomineeTypeId()) &&
				// !CPSUtils.bothNullOrNot(rsq1.getString("nominee_type_id"),
				// nrb.getNomineeTypeId())) {
				txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), nrb.getNomineeID(), "NOMINEE_TYPE_ID", nrb.getIpAddress(), nrb.getNomineeTypeId(), rsq1
						.getString("nominee_type_id"), "0", "Nominee Type"));
				// }
				if (!CPSUtils.compareStrings(rsq1.getString("nominee"), nrb.getNomineeName()) && !CPSUtils.bothNullOrNot(rsq1.getString("nominee"), nrb.getNomineeName())) {
					// both should not be null
					// both should not be equal
					txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), nrb.getNomineeID(), "NOMINEE", nrb.getIpAddress(), nrb.getNomineeName(), rsq1
							.getString("nominee"), "0", "Nominee"));
				}

				if (CPSUtils.isNull(rsq1.getString("family_member_id")) && CPSUtils.compareStrings(nrb.getFamilyID(), "other")) {
					txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), nrb.getNomineeID(), "FAMILY_MEMBER_ID", nrb.getIpAddress(), "OTHER", "OTHER", "0",
							"Family Member"));
				} else if (CPSUtils.isNull(rsq1.getString("family_member_id")) && !CPSUtils.compareStrings(nrb.getFamilyID(), "other")) {
					txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), nrb.getNomineeID(), "FAMILY_MEMBER_ID", nrb.getIpAddress(), nrb.getFamilyID(), "OTHER",
							"0", "Family Member"));
				}
				if (!CPSUtils.isNull(rsq1.getString("family_member_id")) && CPSUtils.compareStrings(nrb.getFamilyID(), "other")) {
					txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), nrb.getNomineeID(), "FAMILY_MEMBER_ID", nrb.getIpAddress(), "OTHER", rsq1
							.getString("family_member_id"), "0", "Family Member"));
				}
				if (!CPSUtils.isNull(rsq1.getString("family_member_id")) && !CPSUtils.compareStrings(nrb.getFamilyID(), "other")) {
					txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), nrb.getNomineeID(), "FAMILY_MEMBER_ID", nrb.getIpAddress(), nrb.getFamilyID(), rsq1
							.getString("family_member_id"), "0", "Family Member"));
				}

				if (!CPSUtils.compareStrings(rsq1.getString("percentage"), nrb.getPercentage()) && !CPSUtils.bothNullOrNot(rsq1.getString("percentage"), nrb.getPercentage())) {
					txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), nrb.getNomineeID(), "PERCENTAGE", nrb.getIpAddress(), nrb.getPercentage(), rsq1
							.getString("percentage"), "0", "Percentage"));
				}

				if (!CPSUtils.compareStrings(rsq1.getString("rdate_of_nominate"), nrb.getDateOfNominate()) && !CPSUtils.bothNullOrNot(rsq1.getString("rdate_of_nominate"), nrb.getDateOfNominate())) {
					txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), nrb.getNomineeID(), "DATE_OF_NOMINATE", nrb.getIpAddress(), nrb.getDateOfNominate(), rsq1
							.getString("rdate_of_nominate"), "0", "Date of Nominate"));
				}

				if (!CPSUtils.compareStrings(rsq1.getString("remarks"), nrb.getRemarks()) && !CPSUtils.bothNullOrNot(rsq1.getString("remarks"), nrb.getRemarks())) {
					txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), nrb.getNomineeID(), "REMARKS", nrb.getIpAddress(), nrb.getRemarks(), rsq1
							.getString("remarks"), "0", "Remarks"));
				}

				if (CPSUtils.isNull(rsq1.getString("family_member_id"))) {
					// other was selected earlier
					if (CPSUtils.compareStrings(nrb.getFamilyID(), "other")) {
						// other was selected now
						// No family id row
						// update the address details in nominee_address_details

						// get the other details from nominee address details &
						// compare the details
						String getNomineeAddDetails = "select address1,address2,address3,city,state_id,district_id,pincode from nominee_address_details where nominee_id=?";
						ps2 = con.prepareStatement(getNomineeAddDetails);
						ps2.setString(1, nrb.getNomineeID());
						rsq2 = ps2.executeQuery();
						if (rsq2.next()) {
							if (!CPSUtils.compareStrings(rsq2.getString("address1"), nrb.getAddress1()) && !CPSUtils.bothNullOrNot(rsq1.getString("address1"), nrb.getAddress1())) {
								txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), nrb.getNomineeID(), "ADDRESS1", nrb.getIpAddress(), nrb.getAddress1(), rsq2
										.getString("address1"), "0", "Address1"));
							}
							if (!CPSUtils.compareStrings(rsq2.getString("address2"), nrb.getAddress2()) && !CPSUtils.bothNullOrNot(rsq1.getString("address2"), nrb.getAddress2())) {
								txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), nrb.getNomineeID(), "ADDRESS2", nrb.getIpAddress(), nrb.getAddress2(), rsq2
										.getString("address2"), "0", "Address2"));
							}
							if (!CPSUtils.compareStrings(rsq2.getString("address3"), nrb.getAddress3()) && !CPSUtils.bothNullOrNot(rsq1.getString("address3"), nrb.getAddress3())) {
								txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), nrb.getNomineeID(), "ADDRESS3", nrb.getIpAddress(), nrb.getAddress3(), rsq2
										.getString("address3"), "0", "Address3"));
							}
							if (!CPSUtils.compareStrings(rsq2.getString("city"), nrb.getCity()) && !CPSUtils.bothNullOrNot(rsq1.getString("city"), nrb.getCity())) {
								txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), nrb.getNomineeID(), "CITY", nrb.getIpAddress(), nrb.getCity(), rsq2
										.getString("city"), "0", "City"));
							}

							if (!CPSUtils.compareStrings(rsq2.getString("state_id"), nrb.getStateId()) && !CPSUtils.bothNullOrNot(rsq1.getString("state_id"), nrb.getStateId())) {
								txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), nrb.getNomineeID(), "STATE_ID", nrb.getIpAddress(), nrb.getStateId(), rsq2
										.getString("state_id"), "0", "State"));
							}
							if (!CPSUtils.compareStrings(rsq2.getString("district_id"), nrb.getDistrictId()) && !CPSUtils.bothNullOrNot(rsq1.getString("district_id"), nrb.getDistrictId())) {
								txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), nrb.getNomineeID(), "DISTRICT_ID", nrb.getIpAddress(), nrb.getDistrictId(),
										rsq2.getString("district_id"), "0", "District"));
							}
							if (!CPSUtils.compareStrings(rsq2.getString("pincode"), nrb.getPincode()) && !CPSUtils.bothNullOrNot(rsq1.getString("pincode"), nrb.getPincode())) {
								txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), nrb.getNomineeID(), "PINCODE", nrb.getIpAddress(), nrb.getPincode(), rsq2
										.getString("pincode"), "0", "Pincode"));
							}
						}

					} else {
						// family member is seleted now
						// insert family member id

						// end
					}

				} else {
					// family member id was selected earlier
					if (CPSUtils.compareStrings(nrb.getFamilyID(), "other")) {
						// other was selected now
						// insert family member id with null value,update that
						// null into nominee_details table after completion

						// address1,address2,address3,city,state_id,district_id,pincode
						if (!CPSUtils.isNullOrEmpty(nrb.getAddress1())) {
							txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), nrb.getNomineeID(), "ADDRESS1", nrb.getIpAddress(), nrb.getAddress1(), "", "0",
									"Address1"));
						}
						if (!CPSUtils.isNullOrEmpty(nrb.getAddress2())) {
							txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), nrb.getNomineeID(), "ADDRESS2", nrb.getIpAddress(), nrb.getAddress2(), "", "0",
									"Address2"));
						}
						if (!CPSUtils.isNullOrEmpty(nrb.getAddress3())) {
							txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), nrb.getNomineeID(), "ADDRESS3", nrb.getIpAddress(), nrb.getAddress3(), "", "0",
									"Address3"));
						}
						if (!CPSUtils.isNullOrEmpty(nrb.getCity())) {
							txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), nrb.getNomineeID(), "CITY", nrb.getIpAddress(), nrb.getCity(), "", "0", "City"));
						}
						if (!CPSUtils.isNullOrEmpty(nrb.getStateId())) {
							txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), nrb.getNomineeID(), "STATE_ID", nrb.getIpAddress(), nrb.getStateId(), "", "0",
									"State"));
						}
						if (!CPSUtils.isNullOrEmpty(nrb.getDistrictId())) {
							txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), nrb.getNomineeID(), "DISTRICT_ID", nrb.getIpAddress(), nrb.getDistrictId(), "",
									"0", "District"));
						}
						if (!CPSUtils.isNullOrEmpty(nrb.getPincode())) {
							txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), nrb.getNomineeID(), "PINCODE", nrb.getIpAddress(), nrb.getPincode(), "", "0",
									"State"));
						}
					} else {
						// family member is seleted now
						// change the family member id

						// end
					}
				}

			} else {
				// new record
				if (!CPSUtils.isNullOrEmpty(nrb.getNomineeTypeId())) {
					txnDetails.add(requestProcess
							.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), "0", "NOMINEE_TYPE_ID", nrb.getIpAddress(), nrb.getNomineeTypeId(), "", "0", "Nominee Type"));
				}
				if (!CPSUtils.isNullOrEmpty(nrb.getNomineeName())) {
					txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), "0", "NOMINEE", nrb.getIpAddress(), nrb.getNomineeName(), "", "0", "Nominee"));
				}
				if (CPSUtils.compareStrings(nrb.getFamilyID(), "other")) {
					txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), "0", "FAMILY_MEMBER_ID", nrb.getIpAddress(), "OTHER", "", "0", "Family Member"));

				} else {
					txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), "0", "FAMILY_MEMBER_ID", nrb.getIpAddress(), nrb.getFamilyID(), "", "0", "Family Member"));
				}
				if (!CPSUtils.isNullOrEmpty(nrb.getPercentage())) {
					txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), "0", "PERCENTAGE", nrb.getIpAddress(), nrb.getPercentage(), "", "0", "Percentage"));
				}
				if (!CPSUtils.isNullOrEmpty(nrb.getDateOfNominate())) {
					txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), "0", "DATE_OF_NOMINATE", nrb.getIpAddress(), nrb.getDateOfNominate(), "", "0",
							"Date of Nominate"));
				}
				if (!CPSUtils.isNullOrEmpty(nrb.getRemarks())) {
					txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), "0", "REMARKS", nrb.getIpAddress(), nrb.getRemarks(), "", "0", "Remarks"));
				}

				if (CPSUtils.compareStrings(nrb.getFamilyID(), "other")) {

					// address1,address2,address3,city,state_id,district_id,pincode
					if (!CPSUtils.isNullOrEmpty(nrb.getAddress1())) {
						txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), "0", "ADDRESS1", nrb.getIpAddress(), nrb.getAddress1(), "", "0", "Address1"));
					}
					if (!CPSUtils.isNullOrEmpty(nrb.getAddress2())) {
						txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), "0", "ADDRESS2", nrb.getIpAddress(), nrb.getAddress2(), "", "0", "Address2"));
					}
					if (!CPSUtils.isNullOrEmpty(nrb.getAddress3())) {
						txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), "0", "ADDRESS3", nrb.getIpAddress(), nrb.getAddress3(), "", "0", "Address3"));
					}
					if (!CPSUtils.isNullOrEmpty(nrb.getCity())) {
						txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), "0", "CITY", nrb.getIpAddress(), nrb.getCity(), "", "0", "City"));
					}
					if (!CPSUtils.isNullOrEmpty(nrb.getStateId())) {
						txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), "0", "STATE_ID", nrb.getIpAddress(), nrb.getStateId(), "", "0", "State"));
					}
					if (!CPSUtils.isNullOrEmpty(nrb.getDistrictId())) {
						txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), "0", "DISTRICT_ID", nrb.getIpAddress(), nrb.getDistrictId(), "", "0", "District"));
					}
					if (!CPSUtils.isNullOrEmpty(nrb.getPincode())) {
						txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), "0", "PINCODE", nrb.getIpAddress(), nrb.getPincode(), "", "0", "State"));
					}
				}
			}
			nrb.setTxnDetails(txnDetails);
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps1, rsq1);
		}
		return nrb;
	}

	public NomineeRequestBean insertIncontengensyDetails(NomineeRequestBean nrb) throws Exception {
		JSONObject inconsistencyDetails = null;
		ArrayList<WorkflowTxnDTO> txnDetails = null;
		try {
			txnDetails = new ArrayList<WorkflowTxnDTO>();
			String[] inconstDetails = nrb.getInconsistencyDetails().split("#");
			for (int i = 0; i < inconstDetails.length; i++) {
				if (!CPSUtils.isNullOrEmpty(inconstDetails[i])) {
					inconsistencyDetails = (JSONObject) JSONSerializer.toJSON(inconstDetails[i]);

					// (reqTypeID, reqID, refNo, colName, ipAddress, newValue,
					// prevValue, rowNo, desc)
					txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), "INC", "NOMINEE_TYPE_ID", nrb.getIpAddress(), nrb.getNomineeTypeId(), "", String
							.valueOf(i + 1), "Nominee Type"));
					txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), "INC", "NOMINEE", nrb.getIpAddress(), inconsistencyDetails.get("nomineeName").toString(),
							"", String.valueOf(i + 1), "Nominee Name"));

					txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), "INC", "PERCENTAGE", nrb.getIpAddress(), inconsistencyDetails.get("percentage").toString(),
							"", String.valueOf(i + 1), "Percentage"));

					if (CPSUtils.compareStrings("other", inconsistencyDetails.get("familyID").toString())) {
						txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), "INC", "FAMILY_MEMBER_ID", nrb.getIpAddress(), "OTHER", "", String.valueOf(i + 1),
								"Family Member"));

						if (!CPSUtils.isNullOrEmpty(inconsistencyDetails.get("address1").toString()) && !CPSUtils.compareStrings(inconsistencyDetails.get("address1").toString(), CPSConstants.NULL)) {
							txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), "INC", "ADDRESS1", nrb.getIpAddress(), inconsistencyDetails.get("address1")
									.toString(), "", String.valueOf(i + 1), "Address1"));
						}
						if (!CPSUtils.isNullOrEmpty(inconsistencyDetails.get("address2").toString()) && !CPSUtils.compareStrings(inconsistencyDetails.get("address2").toString(), CPSConstants.NULL)) {
							txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), "INC", "ADDRESS2", nrb.getIpAddress(), inconsistencyDetails.get("address2")
									.toString(), "", String.valueOf(i + 1), "Address2"));
						}
						if (!CPSUtils.isNullOrEmpty(inconsistencyDetails.get("address3").toString()) && !CPSUtils.compareStrings(inconsistencyDetails.get("address3").toString(), CPSConstants.NULL)) {
							txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), "INC", "ADDRESS3", nrb.getIpAddress(), inconsistencyDetails.get("address3")
									.toString(), "", String.valueOf(i + 1), "Address3"));
						}
						if (!CPSUtils.isNullOrEmpty(inconsistencyDetails.get("city").toString()) && !CPSUtils.compareStrings(inconsistencyDetails.get("city").toString(), CPSConstants.NULL)) {
							txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), "INC", "CITY", nrb.getIpAddress(), inconsistencyDetails.get("city").toString(), "",
									String.valueOf(i + 1), "city"));
						}
						if (!CPSUtils.compareStrings(inconsistencyDetails.get("state").toString(), "select")) {
							txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), "INC", "STATE_ID", nrb.getIpAddress(),
									inconsistencyDetails.get("state").toString(), "", String.valueOf(i + 1), "State"));
						}
						if (!CPSUtils.compareStrings(inconsistencyDetails.get("district").toString(), "select")) {
							txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), "INC", "DISTRICT_ID", nrb.getIpAddress(), inconsistencyDetails.get("district")
									.toString(), "", String.valueOf(i + 1), "District"));
						}
						if (!CPSUtils.isNullOrEmpty(inconsistencyDetails.get("pincode").toString()) && !CPSUtils.compareStrings(inconsistencyDetails.get("pincode").toString(), CPSConstants.NULL)) {
							txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), "INC", "PINCODE", nrb.getIpAddress(), inconsistencyDetails.get("pincode")
									.toString(), "", String.valueOf(i + 1), "Pincode"));
						}
					} else {
						txnDetails.add(requestProcess.txnRowDetails(nrb.getRequestTypeID(), nrb.getRequestID(), "INC", "FAMILY_MEMBER_ID", nrb.getIpAddress(), inconsistencyDetails.get("familyID")
								.toString(), "", String.valueOf(i + 1), "Family Member"));
					}
				}
			}
			nrb.setTxnDetails(txnDetails);
		} catch (Exception e) {
			throw e;
		}
		return nrb;
	}

	/**
	 * This method will be called when a user wants to approve the request
	 * 
	 * @param prb
	 * @return
	 * @throws Exception
	 */
	public String approvedRequest(NomineeRequestBean nrb) throws Exception {
		log.debug("::<<<<<NomineeRequestProcess<<<<<<Method>>>>>>>>>>>>>>>approvedRequest(NomineeRequestBean prb)>>>>>>>>>");
		RequestBean rb = null;
		try {
			rb = new RequestBean();
			BeanUtils.copyProperties(nrb, rb);
			rb = requestProcess.approvedRequest(rb);
			if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())) {
				log.debug("::request workflow completed, so update in the main table");
				BeanUtils.copyProperties(rb, nrb);
				rb.setMessage(nomineeDomainObject.updateTxnDetails(nrb));
			}
		} catch (Exception e) {
			throw e;
		}
		return rb.getMessage();
	}

}
