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

import com.callippus.web.beans.dto.ConstraintsDTO;
import com.callippus.web.beans.dto.ResultDTO;
import com.callippus.web.beans.dto.WorkflowTxnDTO;
import com.callippus.web.beans.requests.FamilyRequestBean;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.business.domainobject.FamilyDomainObject;
import com.callippus.web.cghs.business.management.CghsConstraints;
import com.callippus.web.cghs.business.management.LtcConstraints;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.ConnectionUtil;

@Service
public class FamilyRequestProcess extends RequestProcess {
	private static Log log = LogFactory.getLog(PISRequestProcess.class);
	@Autowired
	private RequestProcess requestProcess;
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private FamilyDomainObject familyDomainObject;
	@Autowired
	private CghsConstraints cghsConstraints;
	@Autowired
	private LtcConstraints ltcConstraints;

	/**
	 * This method will be called when a user requested for his personal information changes
	 * 
	 * @param prb
	 * @return
	 */
	public String initWorkflow(FamilyRequestBean frb) throws Exception {
		log.debug("::<<<<<FamilyRequestProcess<<<<<<Method>>>>>>>>>>>>>>>initWorkflow(FamilyRequestBean prb)>>>>>>>>>");
		String message = null;
		ConstraintsDTO constraintsDto = null;
		ResultDTO resultDTO = null;
		ResultDTO permanent = null;
		JSONObject changedValues = null;
		try {
			/**
			 * Generate Unique ID for this Request
			 */
			frb.setRequestID(requestProcess.generateUniqueID(CPSConstants.REQUEST));
			frb.setRequestType(CPSConstants.FAMILY);
			frb.setRequestTypeID("4");// Family Request type ID = 4
			changedValues = (JSONObject) JSONSerializer.toJSON(frb.getChangedValues());

			if (!CPSUtils.compareStrings(changedValues.getString("RELATION_ID"), "16") && CPSUtils.compareStrings(changedValues.getString("LTC_FACILITY"), CPSConstants.Y)
					|| CPSUtils.compareStrings(changedValues.getString("CGHS_FACILITY"), CPSConstants.Y)) {
				permanent = new ResultDTO();
				constraintsDto = new ConstraintsDTO();
				if (!CPSUtils.isNullOrEmpty(frb.getFamilyID())) {
					constraintsDto.setId(frb.getFamilyID());
				}
				constraintsDto.setAge(changedValues.getString("AGE"));
				constraintsDto.setDob(changedValues.getString("DOB"));
				constraintsDto.setEarningMoney(changedValues.getString("EARNING_MONEY"));
				constraintsDto.setPhFlag(changedValues.getString("PH_FLAG"));
				constraintsDto.setEmployeeFlag(changedValues.getString("EMPLOYED"));
				constraintsDto.setGender(changedValues.getString("SEX"));
				constraintsDto.setRelationId(changedValues.getString("RELATION_ID"));
				constraintsDto.setMaritalId(changedValues.getString("MARITAL_STATUS_ID"));
				constraintsDto.setSfid(frb.getSfID());
				if (CPSUtils.compareStrings(changedValues.getString("CGHS_FACILITY"), CPSConstants.Y)) {
					// checks the CGHS Constrains if user clicks on CGHS facility available
					constraintsDto.setType(CPSConstants.CGHS);
					resultDTO = cghsConstraints.checkCghsConstraints(constraintsDto, permanent);
				}
				if (CPSUtils.compareStrings(changedValues.getString("LTC_FACILITY"), CPSConstants.Y)) {
					// checks the LTC Constrains if user clicks on LTC facility available
					constraintsDto.setType(CPSConstants.LTC);
					resultDTO = ltcConstraints.checkLtcConstraints(constraintsDto, permanent);
				}
			} else {
				resultDTO = new ResultDTO();
				resultDTO.setResult(CPSConstants.SUCCESS);
			}
			if (CPSUtils.compareStrings(resultDTO.getResult(), CPSConstants.SUCCESS)) {
				frb = setChangedValues(frb);
				message = requestProcess.submitTxnDetails(frb.getTxnDetails());

				if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
					/**
					 * decide the work flow & insert into history table
					 */
					RequestBean rb = new RequestBean();
					BeanUtils.copyProperties(frb, rb);
					message = requestProcess.initWorkflow(rb);

					/**
					 * If No workflow is assigned then update the details in the table
					 */
					if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())) {
						log.debug("::request workflow not exists, so update in the main table");
						BeanUtils.copyProperties(rb, frb);
						message = familyDomainObject.updateTxnDetails(frb);
					}
				}
			} else {
				// THIS METHOD RETURN ONLY STRING BCOZ OF THAT I AM CONCATING THE RESULT WITH REMARKS
				message = resultDTO.getResult() + "#" + resultDTO.getRemark();
			}
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public FamilyRequestBean setChangedValues(FamilyRequestBean frb) throws Exception {
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		JSONObject changedValues = null;
		ArrayList<WorkflowTxnDTO> txnDetails = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			txnDetails = new ArrayList<WorkflowTxnDTO>();
			txnDetails.add(requestProcess
					.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "SFID", frb.getIpAddress(), frb.getSfID(), frb.getSfID(), "0", CPSConstants.SFID));

			changedValues = (JSONObject) JSONSerializer.toJSON(frb.getChangedValues());

			/**
			 * When the request is from IE browser empty fields are assigned to null value
			 */
			if (CPSUtils.compareStrings(changedValues.getString("NAME"), "null")) {
				changedValues.put("NAME", "");
			}
			if (CPSUtils.compareStrings(changedValues.getString("DOB"), "null")) {
				changedValues.put("DOB", "");
			}
			if (CPSUtils.compareStrings(changedValues.getString("AGE"), "null")) {
				changedValues.put("AGE", "");
			}
			if (CPSUtils.compareStrings(changedValues.getString("DEPENDENT_FROM"), "null")) {
				changedValues.put("DEPENDENT_FROM", "");
			}
			if (CPSUtils.compareStrings(changedValues.getString("PLACE_OF_ISSUE"), "null")) {
				changedValues.put("PLACE_OF_ISSUE", "");
			}
			if (CPSUtils.compareStrings(changedValues.getString("BENEFICIARY"), "null")) {
				changedValues.put("BENEFICIARY", "");
			}
			if (CPSUtils.compareStrings(changedValues.getString("VALID_FROM"), "null")) {
				changedValues.put("VALID_FROM", "");
			}
			if (CPSUtils.compareStrings(changedValues.getString("VALID_TO"), "null")) {
				changedValues.put("VALID_TO", "");
			}
			if (CPSUtils.compareStrings(changedValues.getString("ADDRESS_TYPE_ID"), "100")) {
				if (CPSUtils.compareStrings(changedValues.getString("ADDRESS1"), "null")) {
					changedValues.put("ADDRESS1", "");
				}
				if (CPSUtils.compareStrings(changedValues.getString("ADDRESS2"), "null")) {
					changedValues.put("ADDRESS2", "");
				}
				if (CPSUtils.compareStrings(changedValues.getString("ADDRESS3"), "null")) {
					changedValues.put("ADDRESS3", "");
				}
				if (CPSUtils.compareStrings(changedValues.getString("CITY"), "null")) {
					changedValues.put("CITY", "");
				}
				if (CPSUtils.compareStrings(changedValues.getString("PIN_CODE"), "null")) {
					changedValues.put("PIN_CODE", "");
				}
			} else {
				changedValues.put("ADDRESS1", "");
				changedValues.put("ADDRESS2", "");
				changedValues.put("ADDRESS3", "");
				changedValues.put("CITY", "");
				changedValues.put("PIN_CODE", "");
			}
			if (CPSUtils.compareStrings(changedValues.getString("CONTACT_NUMBER"), "null")) {
				changedValues.put("CONTACT_NUMBER", "");
			}
			if (CPSUtils.compareStrings(changedValues.getString("FAMILY_DECLARE_DATE"), "null")) {
				changedValues.put("FAMILY_DECLARE_DATE", "");
			}
			// if (CPSUtils.compareStrings(changedValues.getString("DEPENDENT"), "null")) {
			// changedValues.put("DEPENDENT", "N");
			// }
			if (CPSUtils.compareStrings(changedValues.getString("EMPLOYED"), "null")) {
				changedValues.put("EMPLOYED", "N");
			}
			if (CPSUtils.compareStrings(changedValues.getString("EMPLOYED_TYPE"), "null")) {
				changedValues.put("EMPLOYED_TYPE", "");
			}
			if (CPSUtils.compareStrings(changedValues.getString("EARNING_MONEY"), "null")) {
				changedValues.put("EARNING_MONEY", "");
			}

			if (CPSUtils.compareStrings(changedValues.getString("ADDRESS_TYPE_ID"), CPSConstants.SELECT)) {
				changedValues.put("ADDRESS_TYPE_ID", "");
			}
			if (CPSUtils.compareStrings(changedValues.getString("CGHS_FACILITY"), "null")) {
				changedValues.put("CGHS_FACILITY", "");
			}
			if (CPSUtils.compareStrings(changedValues.getString("LTC_FACILITY"), "null")) {
				changedValues.put("LTC_FACILITY", "");
			}
			if (CPSUtils.compareStrings(changedValues.getString("RESIDING_WITH"), "null")) {
				changedValues.put("RESIDING_WITH", "");
			}

			String sql = "select name,relation_id,sex,TO_CHAR(dob,'DD-MON-YYYY') rdob,dependent,TO_CHAR(dependent_from,'DD-MON-YYYY') rdependent_from,marital_status_id,blood_group,contact_number,address1,"
					+ "address2,address_type_id,city,state_id,district_id,pin_code,employed,employed_type,earning_money,residing_with,TO_CHAR(family_declare_date,'DD-MON-YYYY') rfamily_declare_date,age,address3,cghs_facility,ltc_facility,disability_id,ph_type_flag,place_of_issue,beneficiary,to_char(valid_from,'DD-Mon-YYYY')as fromDate,to_char(valid_to,'DD-Mon-YYYY')as toDate from family_details "
					+ "where id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, frb.getFamilyID());
			rsq = ps.executeQuery();
			if (rsq.next()) {

				if (!CPSUtils.compareStrings(rsq.getString("name"), changedValues.getString("NAME")) && !CPSUtils.bothNullOrNot(rsq.getString("name"), changedValues.getString("NAME"))) {
					// both should not be null
					// both should not be equal
					changedValues.put("NAME", CPSUtils.setEmptyIfNull(changedValues.getString("NAME")));
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "NAME", frb.getIpAddress(), changedValues.getString("NAME"), rsq
							.getString("name"), "0", CPSConstants.FAMILYMEMNAME));

				}

				if (!CPSUtils.compareStrings(rsq.getString("relation_id"), changedValues.getString("RELATION_ID"))
						&& !CPSUtils.bothNullOrNot(rsq.getString("relation_id"), changedValues.getString("RELATION_ID"))) {
					changedValues.put("RELATION_ID", CPSUtils.setEmptyIfNull(changedValues.getString("RELATION_ID")));
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "RELATION_ID", frb.getIpAddress(), changedValues
							.getString("RELATION_ID"), rsq.getString("relation_id"), "0", CPSConstants.RELATION_SHIP));
				}

				if (!CPSUtils.compareStrings(rsq.getString("sex"), changedValues.getString("SEX")) && !CPSUtils.bothNullOrNot(rsq.getString("sex"), changedValues.getString("SEX"))) {
					changedValues.put("SEX", CPSUtils.setEmptyIfNull(changedValues.getString("SEX")));
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "SEX", frb.getIpAddress(), changedValues.getString("SEX"), rsq
							.getString("sex"), "0", CPSConstants.SEX));
				}
				if (!CPSUtils.compareStrings(rsq.getString("rdob"), changedValues.getString("DOB")) && !CPSUtils.bothNullOrNot(rsq.getString("rdob"), changedValues.getString("DOB"))) {
					changedValues.put("DOB", CPSUtils.setEmptyIfNull(changedValues.getString("DOB")));
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "DOB", frb.getIpAddress(), changedValues.getString("DOB"), rsq
							.getString("rdob"), "0", CPSConstants.DATEOFBIRTH));
				}
				// if (!CPSUtils.compareStrings(rsq.getString("dependent"), changedValues.getString("DEPENDENT"))
				// && !CPSUtils.bothNullOrNot(rsq.getString("dependent"), changedValues.getString("DEPENDENT"))) {
				// changedValues.put("DEPENDENT", CPSUtils.setEmptyIfNull(changedValues.getString("DEPENDENT")));
				// txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "DEPENDENT", frb.getIpAddress(),
				// changedValues.getString("DEPENDENT"), rsq.getString("dependent"), "0", CPSConstants.DEPENDENT));
				// }
				if (!CPSUtils.compareStrings(rsq.getString("rdependent_from"), changedValues.getString("DEPENDENT_FROM"))
						&& !CPSUtils.bothNullOrNot(rsq.getString("rdependent_from"), changedValues.getString("DEPENDENT_FROM"))) {
					changedValues.put("DEPENDENT_FROM", CPSUtils.setEmptyIfNull(changedValues.getString("DEPENDENT_FROM")));
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "DEPENDENT_FROM", frb.getIpAddress(), changedValues
							.getString("DEPENDENT_FROM"), rsq.getString("rdependent_from"), "0", CPSConstants.DEPENDENTFROM));
				}
				if (!CPSUtils.compareStrings(rsq.getString("marital_status_id"), changedValues.getString("MARITAL_STATUS_ID"))
						&& !CPSUtils.bothNullOrNot(rsq.getString("marital_status_id"), changedValues.getString("MARITAL_STATUS_ID"))) {
					changedValues.put("MARITAL_STATUS_ID", CPSUtils.setEmptyIfNull(changedValues.getString("MARITAL_STATUS_ID")));
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "MARITAL_STATUS_ID", frb.getIpAddress(), changedValues
							.getString("MARITAL_STATUS_ID"), rsq.getString("marital_status_id"), "0", CPSConstants.MARITALSTATUS));
				}
				if (!CPSUtils.compareStrings(rsq.getString("age"), changedValues.getString("AGE")) && !CPSUtils.bothNullOrNot(rsq.getString("age"), changedValues.getString("AGE"))) {
					changedValues.put("AGE", CPSUtils.setEmptyIfNull(changedValues.getString("AGE")));
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "AGE", frb.getIpAddress(), changedValues.getString("AGE"), rsq
							.getString("age"), "0", CPSConstants.AGE));
				}

				if (!CPSUtils.compareStrings(rsq.getString("address_type_id"), changedValues.getString("ADDRESS_TYPE_ID"))
						&& !CPSUtils.bothNullOrNot(rsq.getString("address_type_id"), changedValues.getString("ADDRESS_TYPE_ID"))) {
					changedValues.put("ADDRESS_TYPE_ID", CPSUtils.setEmptyIfNull(changedValues.getString("ADDRESS_TYPE_ID")));
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "ADDRESS_TYPE_ID", frb.getIpAddress(), changedValues
							.getString("ADDRESS_TYPE_ID"), rsq.getString("address_type_id"), "0", CPSConstants.ADDRESS_TYPE));
				}
				if (!CPSUtils.compareStrings(rsq.getString("address1"), changedValues.getString("ADDRESS1")) && !CPSUtils.bothNullOrNot(rsq.getString("address1"), changedValues.getString("ADDRESS1"))) {
					changedValues.put("ADDRESS1", CPSUtils.setEmptyIfNull(changedValues.getString("ADDRESS1")));
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "ADDRESS1", frb.getIpAddress(), changedValues.getString("ADDRESS1"), rsq
							.getString("address1"), "0", CPSConstants.ADDRESS1));
				}
				if (!CPSUtils.compareStrings(rsq.getString("address2"), changedValues.getString("ADDRESS2")) && !CPSUtils.bothNullOrNot(rsq.getString("address2"), changedValues.getString("ADDRESS2"))) {
					changedValues.put("ADDRESS2", CPSUtils.setEmptyIfNull(changedValues.getString("ADDRESS2")));
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "ADDRESS2", frb.getIpAddress(), changedValues.getString("ADDRESS2"), rsq
							.getString("address2"), "0", CPSConstants.ADDRESS2));
				}
				if (!CPSUtils.compareStrings(rsq.getString("address3"), changedValues.getString("ADDRESS3")) && !CPSUtils.bothNullOrNot(rsq.getString("address3"), changedValues.getString("ADDRESS3"))) {
					changedValues.put("ADDRESS3", CPSUtils.setEmptyIfNull(changedValues.getString("ADDRESS3")));
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "ADDRESS3", frb.getIpAddress(), changedValues.getString("ADDRESS3"), rsq
							.getString("address3"), "0", CPSConstants.ADDRESS3));
				}
				if (!CPSUtils.compareStrings(rsq.getString("city"), changedValues.getString("CITY")) && !CPSUtils.bothNullOrNot(rsq.getString("city"), changedValues.getString("CITY"))) {
					changedValues.put("CITY", CPSUtils.setEmptyIfNull(changedValues.getString("CITY")));
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "CITY", frb.getIpAddress(), changedValues.getString("CITY"), rsq
							.getString("city"), "0", CPSConstants.CITY));
				}
				if (!CPSUtils.compareStrings(rsq.getString("state_id"), changedValues.getString("STATE_ID"))
						&& !(CPSUtils.isNullOrEmpty(rsq.getString("state_id")) && CPSUtils.compareStrings(changedValues.getString("STATE_ID"), "0"))) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "STATE_ID", frb.getIpAddress(), changedValues.getString("STATE_ID"), rsq
							.getString("state_id"), "0", CPSConstants.STATE));
				}
				if (!CPSUtils.compareStrings(rsq.getString("district_id"), changedValues.getString("DISTRICT_ID"))
						&& !(CPSUtils.isNullOrEmpty(rsq.getString("district_id")) && CPSUtils.compareStrings(changedValues.getString("DISTRICT_ID"), "0"))) {
					changedValues.put("DISTRICT_ID", CPSUtils.setEmptyIfNull(changedValues.getString("DISTRICT_ID")));
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "DISTRICT_ID", frb.getIpAddress(), changedValues
							.getString("DISTRICT_ID"), rsq.getString("district_id"), "0", CPSConstants.DISTRICT));
				}
				if (!CPSUtils.compareStrings(rsq.getString("pin_code"), changedValues.getString("PIN_CODE")) && !CPSUtils.bothNullOrNot(rsq.getString("pin_code"), changedValues.getString("PIN_CODE"))) {
					changedValues.put("PIN_CODE", CPSUtils.setEmptyIfNull(changedValues.getString("PIN_CODE")));
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "PIN_CODE", frb.getIpAddress(), changedValues.getString("PIN_CODE"), rsq
							.getString("pin_code"), "0", CPSConstants.PINCODE));
				}
				if (!CPSUtils.compareStrings(rsq.getString("employed"), changedValues.getString("EMPLOYED")) && !CPSUtils.bothNullOrNot(rsq.getString("employed"), changedValues.getString("EMPLOYED"))) {
					changedValues.put("EMPLOYED", CPSUtils.setEmptyIfNull(changedValues.getString("EMPLOYED")));
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "EMPLOYED", frb.getIpAddress(), changedValues.getString("EMPLOYED"), rsq
							.getString("employed"), "0", CPSConstants.EMPLOYED));
				}
				if (!CPSUtils.compareStrings(rsq.getString("employed_type"), changedValues.getString("EMPLOYED_TYPE"))
						&& !CPSUtils.bothNullOrNot(rsq.getString("employed_type"), changedValues.getString("EMPLOYED_TYPE"))) {
					changedValues.put("EMPLOYED_TYPE", CPSUtils.setEmptyIfNull(changedValues.getString("EMPLOYED_TYPE")));
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "EMPLOYED_TYPE", frb.getIpAddress(), changedValues
							.getString("EMPLOYED_TYPE"), rsq.getString("employed_type"), "0", CPSConstants.EMPLOYEDTYPE));
				}
				if (!CPSUtils.compareStrings(rsq.getString("earning_money"), changedValues.getString("EARNING_MONEY"))
						&& !CPSUtils.bothNullOrNot(rsq.getString("earning_money"), changedValues.getString("EARNING_MONEY"))) {
					changedValues.put("EARNING_MONEY", CPSUtils.setEmptyIfNull(changedValues.getString("EARNING_MONEY")));
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "EARNING_MONEY", frb.getIpAddress(), changedValues
							.getString("EARNING_MONEY"), rsq.getString("earning_money"), "0", CPSConstants.EARNINGMONEY));
				}
				if (!CPSUtils.compareStrings(rsq.getString("blood_group"), changedValues.getString("BLOOD_GROUP"))
						&& !(CPSUtils.isNullOrEmpty(rsq.getString("district_id")) && CPSUtils.compareStrings(changedValues.getString("DISTRICT_ID"), "0"))) {
					changedValues.put("BLOOD_GROUP", CPSUtils.setEmptyIfNull(changedValues.getString("BLOOD_GROUP")));
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "BLOOD_GROUP", frb.getIpAddress(), changedValues
							.getString("BLOOD_GROUP"), rsq.getString("blood_group"), "0", CPSConstants.BLOODGROUP));
				}
				if (!CPSUtils.compareStrings(rsq.getString("contact_number"), changedValues.getString("CONTACT_NUMBER"))
						&& !CPSUtils.bothNullOrNot(rsq.getString("contact_number"), changedValues.getString("CONTACT_NUMBER"))) {
					changedValues.put("CONTACT_NUMBER", CPSUtils.setEmptyIfNull(changedValues.getString("CONTACT_NUMBER")));
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "CONTACT_NUMBER", frb.getIpAddress(), changedValues
							.getString("CONTACT_NUMBER"), rsq.getString("contact_number"), "0", CPSConstants.CONTACTNUMBER));
				}
				if (!CPSUtils.compareStrings(rsq.getString("residing_with"), changedValues.getString("RESIDING_WITH"))
						&& !CPSUtils.bothNullOrNot(rsq.getString("residing_with"), changedValues.getString("RESIDING_WITH"))) {
					changedValues.put("RESIDING_WITH", CPSUtils.setEmptyIfNull(changedValues.getString("RESIDING_WITH")));
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "RESIDING_WITH", frb.getIpAddress(), changedValues
							.getString("RESIDING_WITH"), rsq.getString("residing_with"), "0", CPSConstants.RESIDINGWITH));
				}
				if (!CPSUtils.compareStrings(rsq.getString("rfamily_declare_date"), changedValues.getString("FAMILY_DECLARE_DATE"))
						&& !CPSUtils.bothNullOrNot(rsq.getString("rfamily_declare_date"), changedValues.getString("FAMILY_DECLARE_DATE"))) {
					changedValues.put("FAMILY_DECLARE_DATE", CPSUtils.setEmptyIfNull(changedValues.getString("FAMILY_DECLARE_DATE")));
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "FAMILY_DECLARE_DATE", frb.getIpAddress(), changedValues
							.getString("FAMILY_DECLARE_DATE"), rsq.getString("rfamily_declare_date"), "0", CPSConstants.FAMILYDECLAREDATE));
				}
				if (!CPSUtils.compareStrings(rsq.getString("cghs_facility"), changedValues.getString("CGHS_FACILITY"))
						&& !CPSUtils.bothNullOrNot(rsq.getString("cghs_facility"), changedValues.getString("CGHS_FACILITY"))) {
					changedValues.put("CGHS_FACILITY", CPSUtils.setEmptyIfNull(changedValues.getString("CGHS_FACILITY")));
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "CGHS_FACILITY", frb.getIpAddress(), changedValues
							.getString("CGHS_FACILITY"), rsq.getString("cghs_facility"), "0", CPSConstants.CGHSFACILITY));
				}
				if (!CPSUtils.compareStrings(rsq.getString("ltc_facility"), changedValues.getString("LTC_FACILITY"))
						&& !CPSUtils.bothNullOrNot(rsq.getString("ltc_facility"), changedValues.getString("LTC_FACILITY"))) {
					changedValues.put("LTC_FACILITY", CPSUtils.setEmptyIfNull(changedValues.getString("LTC_FACILITY")));
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "LTC_FACILITY", frb.getIpAddress(), changedValues
							.getString("LTC_FACILITY"), rsq.getString("ltc_facility"), "0", CPSConstants.LTCFACILITY));
				}
				if (!CPSUtils.compareStrings(rsq.getString("disability_Id"), changedValues.getString("DISABILITY_ID"))
						&& !CPSUtils.bothNullOrNot(rsq.getString("disability_Id"), changedValues.getString("DISABILITY_ID")) && !CPSUtils.compareStrings(changedValues.getString("DISABILITY_ID"), "0")) {
					changedValues.put("DISABILITY_ID", CPSUtils.setEmptyIfNull(changedValues.getString("DISABILITY_ID")));
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "DISABILITY_ID", frb.getIpAddress(), changedValues
							.getString("DISABILITY_ID"), rsq.getString("disability_Id"), "0", CPSConstants.DISABILITY));
				}
				if (!CPSUtils.compareStrings(rsq.getString("ph_type_flag"), changedValues.getString("PH_FLAG"))
						&& !CPSUtils.bothNullOrNot(rsq.getString("ph_type_flag"), changedValues.getString("PH_FLAG"))) {
					changedValues.put("PH_FLAG", CPSUtils.setEmptyIfNull(changedValues.getString("PH_FLAG")));
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "PH_FLAG", frb.getIpAddress(), changedValues.getString("PH_FLAG"), rsq
							.getString("ph_type_flag"), "0", CPSConstants.PHTYPE));
				}
				if (!CPSUtils.compareStrings(rsq.getString("place_of_issue"), changedValues.getString("PLACE_OF_ISSUE"))
						&& !CPSUtils.bothNullOrNot(rsq.getString("place_of_issue"), changedValues.getString("PLACE_OF_ISSUE"))) {
					changedValues.put("PLACE_OF_ISSUE", CPSUtils.setEmptyIfNull(changedValues.getString("PLACE_OF_ISSUE")));
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "PLACE_OF_ISSUE", frb.getIpAddress(), changedValues
							.getString("PLACE_OF_ISSUE"), rsq.getString("place_of_issue"), "0", CPSConstants.PLACEOFISSUE));
				}
				if (!CPSUtils.compareStrings(rsq.getString("beneficiary"), changedValues.getString("BENEFICIARY"))
						&& !CPSUtils.bothNullOrNot(rsq.getString("beneficiary"), changedValues.getString("BENEFICIARY"))) {
					changedValues.put("BENEFICIARY", CPSUtils.setEmptyIfNull(changedValues.getString("BENEFICIARY")));
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "BENEFICIARY", frb.getIpAddress(), changedValues
							.getString("BENEFICIARY"), rsq.getString("beneficiary"), "0", CPSConstants.CGHSNO));
				}
				if (!CPSUtils.compareStrings(rsq.getString("fromDate"), changedValues.getString("VALID_FROM"))
						&& !CPSUtils.bothNullOrNot(rsq.getString("fromDate"), changedValues.getString("VALID_FROM"))) {
					changedValues.put("VALID_FROM", CPSUtils.setEmptyIfNull(changedValues.getString("VALID_FROM")));
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "VALID_FROM", frb.getIpAddress(), changedValues.getString("VALID_FROM"),
							rsq.getString("fromDate"), "0", CPSConstants.VALIDFROM));
				}
				if (!CPSUtils.compareStrings(rsq.getString("toDate"), changedValues.getString("VALID_TO")) && !CPSUtils.bothNullOrNot(rsq.getString("toDate"), changedValues.getString("VALID_TO"))) {
					changedValues.put("VALID_TO", CPSUtils.setEmptyIfNull(changedValues.getString("VALID_TO")));
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), frb.getFamilyID(), "VALID_TO", frb.getIpAddress(), changedValues.getString("VALID_TO"), rsq
							.getString("toDate"), "0", CPSConstants.VALIDTO));
				}

			} else {
				// new record

				if (!CPSUtils.isNullOrEmpty(changedValues.getString("NAME"))) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), "0", "NAME", frb.getIpAddress(), changedValues.getString("NAME"), "", "0",
							CPSConstants.FAMILYMEMNAME));

				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("RELATION_ID"))) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), "0", "RELATION_ID", frb.getIpAddress(), changedValues.getString("RELATION_ID"), "", "0",
							CPSConstants.RELATION_SHIP));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("SEX"))) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), "0", "SEX", frb.getIpAddress(), changedValues.getString("SEX"), "", "0", CPSConstants.SEX));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("DOB"))) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), "0", "DOB", frb.getIpAddress(), changedValues.getString("DOB"), "", "0",
							CPSConstants.DATEOFBIRTH));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("DEPENDENT"))) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), "0", "DEPENDENT", frb.getIpAddress(), changedValues.getString("DEPENDENT"), "", "0",
							CPSConstants.DEPENDENT));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("DEPENDENT_FROM"))) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), "0", "DEPENDENT_FROM", frb.getIpAddress(), changedValues.getString("DEPENDENT_FROM"), "",
							"0", CPSConstants.DEPENDENTFROM));
				}
				if (!CPSUtils.compareStrings(changedValues.getString("MARITAL_STATUS_ID"), "0")) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), "0", "MARITAL_STATUS_ID", frb.getIpAddress(), changedValues.getString("MARITAL_STATUS_ID"),
							"", "0", CPSConstants.MARITALSTATUS));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("AGE"))) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), "0", "AGE", frb.getIpAddress(), changedValues.getString("AGE"), "", "0", CPSConstants.AGE));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("ADDRESS_TYPE_ID"))) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), "0", "ADDRESS_TYPE_ID", frb.getIpAddress(), changedValues.getString("ADDRESS_TYPE_ID"), "",
							"0", CPSConstants.ADDRESS_TYPE));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("ADDRESS1"))) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), "0", "ADDRESS1", frb.getIpAddress(), changedValues.getString("ADDRESS1"), "", "0",
							CPSConstants.ADDRESS1));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("ADDRESS2"))) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), "0", "ADDRESS2", frb.getIpAddress(), changedValues.getString("ADDRESS2"), "", "0",
							CPSConstants.ADDRESS2));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("ADDRESS3"))) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), "0", "ADDRESS3", frb.getIpAddress(), changedValues.getString("ADDRESS3"), "", "0",
							CPSConstants.ADDRESS3));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("CITY"))) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), "0", "CITY", frb.getIpAddress(), changedValues.getString("CITY"), "", "0",
							CPSConstants.CITY));
				}
				if (!CPSUtils.compareStrings(changedValues.getString("STATE_ID"), "0")) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), "0", "STATE_ID", frb.getIpAddress(), changedValues.getString("STATE_ID"), "", "0",
							CPSConstants.STATE));
				}
				if (!CPSUtils.compareStrings(changedValues.getString("DISTRICT_ID"), "0")) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), "0", "DISTRICT_ID", frb.getIpAddress(), changedValues.getString("DISTRICT_ID"), "", "0",
							CPSConstants.DISTRICT));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("PIN_CODE"))) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), "0", "PIN_CODE", frb.getIpAddress(), changedValues.getString("PIN_CODE"), "", "0",
							CPSConstants.PINCODE));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("EMPLOYED"))) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), "0", "EMPLOYED", frb.getIpAddress(), changedValues.getString("EMPLOYED"), "", "0",
							CPSConstants.EMPLOYED));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("EMPLOYED_TYPE"))) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), "0", "EMPLOYED_TYPE", frb.getIpAddress(), changedValues.getString("EMPLOYED_TYPE"), "",
							"0", CPSConstants.EMPLOYEDTYPE));
				}
				if (!CPSUtils.compareStrings(changedValues.getString("BLOOD_GROUP"), "0")) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), "0", "BLOOD_GROUP", frb.getIpAddress(), changedValues.getString("BLOOD_GROUP"), "", "0",
							CPSConstants.BLOODGROUP));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("CONTACT_NUMBER"))) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), "0", "CONTACT_NUMBER", frb.getIpAddress(), changedValues.getString("CONTACT_NUMBER"), "",
							"0", CPSConstants.CONTACTNUMBER));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("RESIDING_WITH"))) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), "0", "RESIDING_WITH", frb.getIpAddress(), changedValues.getString("RESIDING_WITH"), "",
							"0", CPSConstants.RESIDINGWITH));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("FAMILY_DECLARE_DATE"))) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), "0", "FAMILY_DECLARE_DATE", frb.getIpAddress(), changedValues
							.getString("FAMILY_DECLARE_DATE"), "", "0", CPSConstants.FAMILYDECLAREDATE));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("CGHS_FACILITY"))) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), "0", "CGHS_FACILITY", frb.getIpAddress(), changedValues.getString("CGHS_FACILITY"), "",
							"0", CPSConstants.CGHSFACILITY));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("LTC_FACILITY"))) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), "0", "LTC_FACILITY", frb.getIpAddress(), changedValues.getString("LTC_FACILITY"), "", "0",
							CPSConstants.LTCFACILITY));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("DISABILITY_ID"))) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), "0", "DISABILITY_ID", frb.getIpAddress(), changedValues.getString("DISABILITY_ID"), "",
							"0", CPSConstants.DISABILITY));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("PH_FLAG"))) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), "0", "PH_FLAG", frb.getIpAddress(), changedValues.getString("PH_FLAG"), "", "0",
							CPSConstants.PHTYPE));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("PLACE_OF_ISSUE"))) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), "0", "PLACE_OF_ISSUE", frb.getIpAddress(), changedValues.getString("PLACE_OF_ISSUE"), "",
							"0", CPSConstants.PLACEOFISSUE));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("BENEFICIARY"))) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), "0", "BENEFICIARY", frb.getIpAddress(), changedValues.getString("BENEFICIARY"), "", "0",
							CPSConstants.CGHSNO));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("VALID_FROM"))) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), "0", "VALID_FROM", frb.getIpAddress(), changedValues.getString("VALID_FROM"), "", "0",
							CPSConstants.VALIDFROM));
				}
				if (!CPSUtils.isNullOrEmpty(changedValues.getString("VALID_TO"))) {
					txnDetails.add(requestProcess.txnRowDetails(frb.getRequestTypeID(), frb.getRequestID(), "0", "VALID_TO", frb.getIpAddress(), changedValues.getString("VALID_TO"), "", "0",
							CPSConstants.VALIDTO));
				}

			}
			frb.setTxnDetails(txnDetails);
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rsq);
		}
		return frb;
	}

	/**
	 * This method will be called when a user wants to approve the request
	 * 
	 * @param prb
	 * @return
	 * @throws Exception
	 */
	public String approvedRequest(FamilyRequestBean frb) throws Exception {
		log.debug("::<<<<<FamilyRequestProcess<<<<<<Method>>>>>>>>>>>>>>>approvedRequest(FamilyRequestBean prb)>>>>>>>>>");
		RequestBean rb = null;
		try {
			rb = new RequestBean();
			BeanUtils.copyProperties(frb, rb);
			rb = requestProcess.approvedRequest(rb);
			if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())) {
				log.debug("::request workflow completed, so update in the main table");
				BeanUtils.copyProperties(rb, frb);
				rb.setMessage(familyDomainObject.updateTxnDetails(frb));
			}
		} catch (Exception e) {
			throw e;
		}
		return rb.getMessage();
	}

}
