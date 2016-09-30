package com.callippus.web.cghs.dao.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.transform.Transformers;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.CDADetailsDTO;
import com.callippus.web.beans.dto.CghsAdvanceRequestDTO;
import com.callippus.web.beans.dto.CghsEmergencyRequestDTO;
import com.callippus.web.beans.dto.CghsReimbursementRequestDTO;
import com.callippus.web.beans.dto.EmployeeClaimDetailsDTO;
import com.callippus.web.beans.dto.FinanceDetailsDTO;
import com.callippus.web.beans.requests.CGHSRequestProcessBean;
import com.callippus.web.cghs.beans.dto.FamilyMemberDetailsDTO;
import com.callippus.web.cghs.beans.dto.ReferralHospitalDTO;
import com.callippus.web.cghs.beans.dto.SingingAuthorityDTO;
import com.callippus.web.cghs.beans.request.CghsRequestBean;

import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;

@Service
public class SQLCghsRequestDAO implements ICghsRequestDao, Serializable {

	private static final long serialVersionUID = -7753238725888858447L;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	@SuppressWarnings("unchecked")
	public CghsRequestBean getEmployeeDetails(String sfid) throws Exception {
		Session session = null;
		CghsRequestBean cghsRequest = null;
		String details = null;
		try {			cghsRequest = new CghsRequestBean();
			session = hibernateUtils.getSession();
			details = "select emp.cgsh_number cghsCardNumber,emp.sfid sfID,emp.name_in_service_book employeeName,dm.name designationId,dept.department_name departmentId,epd.basic_pay basicPay,epd.grade_pay gradePay,"
					+ "(select adm.address1||','||adm.address2||','||adm.address3||','||dm.name||','||sm.name||','||adm.pincode||','||adm.mobile_number from  "
					+ "address_details_master adm,state_master sm,district_master dm where sfid=? and adm.address_type_id=2 and dm.status=1 and sm.status=1 and adm.state=sm.id and adm.district=dm.id) as address  "
					+ "from emp_master emp,designation_master dm,org_role_instance ori ,emp_payment_details epd,departments_master dept "
					+ "where emp.sfid=? and emp.designation_id=dm.id and dm.status=1 and emp.status=1 and dept.department_id=ori.department_id and dept.status=1 and "
					+ "ori.status=1 and emp.office_id=ori.org_role_id and emp.sfid=epd.sfid";
			session.createSQLQuery(details).addScalar("cghsCardNumber",Hibernate.STRING).addScalar("sfID",Hibernate.STRING).addScalar("employeeName",Hibernate.STRING).addScalar("designationId",Hibernate.STRING).addScalar("departmentId",Hibernate.STRING).addScalar("basicPay",Hibernate.STRING).addScalar("gradePay",Hibernate.STRING).addScalar("address",Hibernate.STRING).setString(0, sfid).setString(1, sfid).setResultTransformer(Transformers.aliasToBean(CghsRequestBean.class)).uniqueResult();		

			details = "select (case when fd.dob is null then fd.age else (select round(months_between(sysdate,fd.dob)/12) from dual)||'' end ) as age,fd.sex gender,to_char(fd.dob,'DD-MON-YYYY') dob,fd.id,fd.name||'-'||frm.name as name,place_of_issue placeofissue,beneficiary,fd.cghscard as cghscard,valid_from validFrom,valid_to validTo from family_details fd,family_relation_master frm where "
					+ "fd.relation_id=frm.id and fd.status=1 and fd.sfid=? and fd.cghs_facility='Y' order by frm.name";
			
			cghsRequest.setFamilyMemberList((ArrayList<FamilyMemberDetailsDTO>)session.createSQLQuery(details).addScalar("age",Hibernate.STRING).addScalar("gender",Hibernate.STRING).addScalar("dob",Hibernate.STRING).addScalar("name",Hibernate.STRING).addScalar("id",Hibernate.STRING).addScalar("placeofissue",Hibernate.STRING).addScalar("beneficiary",Hibernate.STRING).addScalar("cghscard",Hibernate.STRING).addScalar("validFrom",Hibernate.STRING).addScalar("validTo",Hibernate.STRING).setString(0, sfid).setResultTransformer(Transformers.aliasToBean(FamilyMemberDetailsDTO.class)).list());		
		} catch (Exception e) {
			throw e;
		} 
		return cghsRequest;
	}

	@SuppressWarnings("unchecked")
	public List<CghsRequestBean> getFamilyMemberReimbursementList(String sfid) throws Exception {
		Session session = null;
		StringBuilder sb = null;
		StringBuilder sb2 = null;
		List<CghsRequestBean> reimbursementMemberList;
		List<CghsRequestBean> settlementMemberList;
		List<CghsRequestBean> totalMemberList;
		try {
			session = hibernateUtils.getSession();
			sb = new StringBuilder();
			sb2 = new StringBuilder();
			/*sb.append("select  cad.cda_amount cdaAmount,crd.request_id requestId ,To_char(Crd.APPLIED_DATE,'dd-MON-yyyy') todaysDate,(case when crd.family_member_id like 'SF%'then (select name_in_service_book||'-SELF' from emp_master where sfid=?) "
							+ "else  (select fd.name||'-'||frm.name from family_details fd,family_relation_master frm where sfid=? and fd.id=crd.family_member_id and frm.status=1 and frm.id=fd.relation_id) end) relation "
							+ ",crd.prescription_date prescriptionDate,crm.name cghsRequestTypeId,crd.family_member_id familyMemberId,sm.status statusName "
							+ "from cghs_request_details crd,cghs_advance_details cad,request_workflow_history rwh,cghs_request_master crm,status_master sm "
							+ "where sfid=?  And Crd.Request_Id=Rwh.Request_Id And crd.request_id=cad.reference_request_id(+) and rwh.status=8 and crm.status=1 and crm.id=crd.cghs_request_type_id " + "and sm.id=crd.status order by requestId desc");
		  reimbursementMemberList = session.createSQLQuery(sb.toString()).addScalar("cdaAmount",Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("relation", Hibernate.STRING)
			// .addScalar("disease",Hibernate.STRING)
					.addScalar("prescriptionDate", Hibernate.DATE).addScalar("cghsRequestTypeId", Hibernate.STRING).addScalar("familyMemberId", Hibernate.STRING).addScalar("statusName",
							Hibernate.STRING).addScalar("todaysDate", Hibernate.STRING)

					.setResultTransformer(Transformers.aliasToBean(CghsRequestBean.class)).setString(0, sfid).setString(1, sfid).setString(2, sfid).list();
		*/
			/*sb.append("SELECT CAD.CDA_AMOUNT CDAAMOUNT,CRD.REQUEST_ID REQUESTID ,RWH.ID historyID,TO_CHAR(CRD.APPLIED_DATE,'dd-MON-yyyy')TODAYSDATE,(CASE WHEN CRD.FAMILY_MEMBER_ID LIKE 'SF%'THEN (SELECT NAME_IN_SERVICE_BOOK||'-SELF' FROM EMP_MASTER WHERE SFID=?)"
					 +" ELSE (SELECT FD.NAME ||'-'||FRM.NAME FROM FAMILY_DETAILS FD,FAMILY_RELATION_MASTER FRM  WHERE SFID=? AND FD.ID=CRD.FAMILY_MEMBER_ID AND FRM.STATUS=1 AND FRM.ID=FD.RELATION_ID)  END) RELATION "
				   +", CRD.PRESCRIPTION_DATE PRESCRIPTIONDATE, CRM.NAME CGHSREQUESTTYPEID, CRD.FAMILY_MEMBER_ID FAMILYMEMBERID,SM.STATUS STATUSNAME "
				  + " FROM CGHS_REQUEST_DETAILS CRD left outer join request_workflow_history rwh on(rwh.request_id = crd.request_id) left outer join CGHS_ADVANCE_DETAILS CAD on(CRD.REQUEST_ID=CAD.REFERENCE_REQUEST_ID), CGHS_REQUEST_MASTER CRM, STATUS_MASTER SM"
				  + " WHERE SFID=? AND CRD.REQUEST_ID=RWH.REQUEST_ID AND RWH.STATUS=8 AND CRM.STATUS =1 AND CRM.ID =CRD.CGHS_REQUEST_TYPE_ID AND SM.ID  =CRD.STATUS ORDER BY REQUESTID DESC");*/
			//code cda_amount for cda_details table 
			sb.append("select cda.cda_amount cdaamount,  crd.request_id requestid ,  rwh.id historyid,  to_char(crd.applied_date,'dd-MON-yyyy')todaysdate,  (  case    when crd.family_member_id like 'SF%'    then      (select name_in_service_book||'-SELF' from emp_master where sfid=? )    else      (select fd.name  ||'-'||frm.name      from family_details fd,        family_relation_master frm      where sfid    =?"
            +" AND FD.ID     =CRD.FAMILY_MEMBER_ID      AND FRM.STATUS=1      AND FRM.ID    =FD.RELATION_ID  )  END) RELATION ,  CRD.PRESCRIPTION_DATE PRESCRIPTIONDATE,  crm.name cghsrequesttypeid,  crd.family_member_id familymemberid,  sm.status statusname from cghs_request_details crd left outer join request_workflow_history rwh on(rwh.request_id = crd.request_id)left outer join cghs_advance_details cad on(crd.request_id=cad.reference_request_id)  left outer join emp_claim_details ecd on(ecd.request_id=cad.request_id) left outer join finance_details finance on(finance.reference_id=ecd.id) left  outer join cda_details cda on(finance.id=cda.reference_id)"
           +" , CGHS_REQUEST_MASTER CRM,  STATUS_MASTER SM where sfid        =? AND CRD.REQUEST_ID=RWH.REQUEST_ID AND RWH.STATUS    =8 and crm.status    =1 AND CRM.ID        =CRD.CGHS_REQUEST_TYPE_ID AND SM.ID         =CRD.STATUS ORDER BY REQUESTID DESC"); 
			
			reimbursementMemberList = session.createSQLQuery(sb.toString()).addScalar("cdaAmount",Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("historyID", Hibernate.STRING).addScalar("relation", Hibernate.STRING)
						// .addScalar("disease",Hibernate.STRING)
								.addScalar("prescriptionDate", Hibernate.DATE).addScalar("cghsRequestTypeId", Hibernate.STRING).addScalar("familyMemberId", Hibernate.STRING).addScalar("statusName",
										Hibernate.STRING).addScalar("todaysDate", Hibernate.STRING)
										.setResultTransformer(Transformers.aliasToBean(CghsRequestBean.class)).setString(0, sfid).setString(1, sfid).setString(2, sfid).list();
					
					
					
					
		 /* sb2.append("select Distinct cad.cda_amount cdaAmount,crd.request_id requestId ,To_char(Crd.APPLIED_DATE,'dd-MON-yyyy') todaysDate,(case when crd.family_member_id like 'SF%'then (select name_in_service_book||'-SELF' from emp_master where sfid=?) "
					+ "else  (select fd.name||'-'||frm.name from family_details fd,family_relation_master frm where sfid=? and fd.id=crd.family_member_id and frm.status=1 and frm.id=fd.relation_id) end) relation "
					+ ",crd.prescription_date prescriptionDate,crm.name cghsRequestTypeId,crd.family_member_id familyMemberId,sm.status statusName "
					+ "from cghs_request_details crd,cghs_advance_details cad,request_workflow_history rwh,cghs_request_master crm,status_master sm "
					+ "where sfid=?  And crd.request_id=cad.reference_request_id(+) and rwh.status=8 and crm.status=1 and crm.id=crd.cghs_request_type_id " 
					+ "and sm.id=crd.status  And Crd.Cghs_Request_Type_Id =2 And Crd.Status =39 order by requestId desc");*/
        //code cda_amount for cda_details table 
			sb2.append("select distinct cda.cda_amount cdaamount,  crd.request_id requestid ,  to_char(crd.applied_date,'dd-MON-yyyy') todaysdate,  (  case    when crd.family_member_id like 'SF%'    then      (select name_in_service_book||'-SELF' from emp_master where sfid=? )    else      (select fd.name        ||'-'        ||frm.name      from family_details fd,        family_relation_master frm      where sfid    =?      and fd.id     =crd.family_member_id      and frm.status=1      and frm.id    =fd.relation_id   )  end) relation ,  crd.prescription_date prescriptiondate,"
                     +"  crm.name cghsrequesttypeid,  crd.family_member_id familymemberid,  sm.status statusname from  request_workflow_history rwh, cghs_request_details crd left outer join   cghs_advance_details cad on(crd.request_id           =cad.reference_request_id) left outer join emp_claim_details ecd on(ecd.request_id=cad.request_id) left outer join  finance_details finance on(ecd.id=finance.reference_id) left outer join cda_details cda on(finance.id=cda.reference_id) , cghs_request_master crm,  status_master sm  where sfid   =? and rwh.status               =8 and crm.status               =1 and crm.id                   =crd.cghs_request_type_id and sm.id     =crd.status and crd.cghs_request_type_id =2"
                     +"  AND Crd.Status   =39 ORDER BY requestId DESC");
		  settlementMemberList = session.createSQLQuery(sb2.toString()).addScalar("cdaAmount",Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("relation", Hibernate.STRING)
	// .addScalar("disease",Hibernate.STRING).
			.addScalar("prescriptionDate", Hibernate.DATE).addScalar("familyMemberId", Hibernate.STRING).addScalar("statusName",
					Hibernate.STRING).addScalar("cghsRequestTypeId", Hibernate.STRING).addScalar("todaysDate", Hibernate.STRING)

			.setResultTransformer(Transformers.aliasToBean(CghsRequestBean.class)).setString(0, sfid).setString(1, sfid).setString(2, sfid).list();
		
		reimbursementMemberList.addAll(settlementMemberList);
		} catch (Exception e) {
			throw e;
		} 
		return reimbursementMemberList;
	}

	

	public CghsRequestBean getFamilyMemberAdvanceDetails(CghsRequestBean cghsRequest) throws Exception {
		Session session = null;
		
		try {
			session = hibernateUtils.getSession();
			if (CPSUtils.compareStrings(cghsRequest.getType(), "cghssettlement")) {
				
				CghsAdvanceRequestDTO adv=new  CghsAdvanceRequestDTO();
				
				adv =(CghsAdvanceRequestDTO)session.createCriteria(CghsAdvanceRequestDTO.class).add(Expression.not(Expression.in("status", new Integer[] { 9, 6 }))).add(Expression.eq("referrenceRequestID", cghsRequest.getRequestId())).uniqueResult();
				 if(!CPSUtils.isNullOrEmpty(adv)){
					EmployeeClaimDetailsDTO empcailm =new EmployeeClaimDetailsDTO();
					empcailm = (EmployeeClaimDetailsDTO)session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq(CPSConstants.REQUESTID,Integer.valueOf(adv.getRequestID()))).add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
               if(!CPSUtils.isNullOrEmpty(empcailm)){
	       FinanceDetailsDTO financeDetails=new FinanceDetailsDTO();
	            financeDetails = (FinanceDetailsDTO) session.createCriteria(FinanceDetailsDTO.class).add(Expression.eq(CPSConstants.REFERENCEID, empcailm.getId())).uniqueResult();
           if(!CPSUtils.isNullOrEmpty(financeDetails)){
	           CDADetailsDTO cda=new  CDADetailsDTO();
	             cda = (CDADetailsDTO) session.createCriteria(CDADetailsDTO.class).add(Expression.eq(CPSConstants.REFERENCEID, financeDetails.getId())).uniqueResult();
	             cghsRequest.setCghsAdvanceDetails((CghsAdvanceRequestDTO) session.createCriteria(CghsAdvanceRequestDTO.class).add(Expression.not(Expression.in("status", new Integer[] { 9, 6 }))).add(
							Expression.eq("referrenceRequestID", cghsRequest.getRequestId())).uniqueResult());
				
				adv.setCdaAmount(((Float)cda.getCdaAmount()).toString());    
           }
             }
				}
			/*cghsRequest.setCghsAdvanceDetails((CghsAdvanceRequestDTO) session.createCriteria(CghsAdvanceRequestDTO.class).add(Expression.not(Expression.in("status", new Integer[] { 9, 6 }))).add(
						Expression.eq("referrenceRequestID", cghsRequest.getRequestId())).uniqueResult());
			*/
			
			
			} else {
				cghsRequest.setCghsRequestDetails((CGHSRequestProcessBean) session.createCriteria(CGHSRequestProcessBean.class).add(Expression.eq("requestID", cghsRequest.getRequestId()))
						.uniqueResult());
			}
		} catch (Exception e) {
			throw e;
		} 
		return cghsRequest;

	}

	@SuppressWarnings("unchecked")
	public List<ReferralHospitalDTO> getReferralHospitalList(String prescriptionDate) throws Exception {
		Session session = null;
		List<ReferralHospitalDTO> hospitalList = null;
		String sql = null;
		try {
			if (CPSUtils.isNull(prescriptionDate)) {
				prescriptionDate = CPSUtils.getCurrentDate();
			}
			session = hibernateUtils.getSession();
			sql = "select id,hospital_name||'-'||location hospitalName from cghs_referral_hospital_master "
					+ "where status=1 and to_date is null or ?  between from_Date and to_Date order by hospitalName";
			hospitalList = session.createSQLQuery(sql).addScalar("id", Hibernate.INTEGER).addScalar("hospitalName", Hibernate.STRING).setResultTransformer(
					Transformers.aliasToBean(ReferralHospitalDTO.class)).setString(0, prescriptionDate).list();
		} catch (Exception e) {
			throw e;
		} 
		return hospitalList;
	}

	@SuppressWarnings("unchecked")
	public CghsRequestBean financeReimbursementHome(CghsRequestBean cghsRequest) throws Exception {
		Session session = null;
		String sql = null;
		try {
			session = hibernateUtils.getSession();
			/*sql = "select cad.dv_no dvNo,to_char(cad.dv_date,'dd-Mon-yyyy') dvDate,cad.account_officer accOfficer, cad.issued_amount issuedAmount ,cad.rep_sig repSig,cad.issued_amount issuedAmount,cad.estimation_amount estimationAmount,cad.sanction_no sanctionNo,cad.bill_no billNo,cad.request_id requestId,crd.sfid sfID,fd.name name,emp.name_in_service_book employeeName from cghs_advance_details cad,cghs_request_details crd,family_details fd,emp_master emp "
					+ "where cad.status=8 and cad.cda_amount is null and crd.request_id=cad.reference_request_id and fd.status=1 and fd.id=crd.family_member_id and emp.status=1 and emp.sfid=crd.sfid order by cad.request_id desc";
			cghsRequest.setAdvanceList(session.createSQLQuery(sql).addScalar("issuedAmount", Hibernate.STRING).addScalar("dvNo", Hibernate.STRING).addScalar("dvDate", Hibernate.STRING).addScalar(
					"accOfficer", Hibernate.STRING).addScalar("repSig").addScalar("issuedAmount", Hibernate.STRING).addScalar("estimationAmount", Hibernate.STRING).addScalar("billNo",
					Hibernate.STRING).addScalar("sanctionNo", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("sfID", Hibernate.STRING).addScalar("name", Hibernate.STRING)
					.addScalar("employeeName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(CghsRequestBean.class)).list());
			sql = "select reim.sanction_no sanctionNo,reim.bill_no billNo,reim.dv_no dvNo,to_char(reim.dv_date,'dd-Mon-yyyy') dvDate,reim.account_officer accOfficer, reim.rep_sig repSig,emp.name_in_service_book employeeName, crd.sfid sfID,reim.request_id requestId,reim.amount_claimed amountClaimed "
					+ "from cghs_reimbursement_details reim,cghs_request_details crd,emp_master emp where request_type='CS' and reim.cda_amount is null and reim.status=8 and crd.request_id=reim.reference_request_id and emp.sfid=crd.sfid and emp.status=1  order by reim.request_id desc";
			cghsRequest.setSettlementList(session.createSQLQuery(sql).addScalar("billNo", Hibernate.STRING).addScalar("sanctionNo", Hibernate.STRING).addScalar("dvNo", Hibernate.STRING).addScalar(
					"dvDate", Hibernate.STRING).addScalar("accOfficer", Hibernate.STRING).addScalar("repSig").addScalar("requestId", Hibernate.STRING).addScalar("sfID", Hibernate.STRING).addScalar(
					"amountClaimed", Hibernate.STRING).addScalar("employeeName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(CghsRequestBean.class)).list());

			sql = "select reim.dv_no dvNo,reim.sanction_no sanctionNo,reim.bill_no billNo,to_char(reim.dv_date,'dd-Mon-yyyy') dvDate,reim.account_officer accOfficer,reim.rep_sig repSig,emp.name_in_service_book employeeName,crd.sfid sfID,reim.request_id requestId,reim.amount_claimed amountClaimed "
					+ "from cghs_reimbursement_details reim,cghs_request_details crd,emp_master emp where reim.request_type='CR' and reim.status=8 and reim.cda_amount is null and crd.request_id=reim.reference_request_id and emp.status=1 and emp.sfid=crd.sfid  order by reim.request_id desc";
			cghsRequest.setReimbursementList(session.createSQLQuery(sql).addScalar("billNo", Hibernate.STRING).addScalar("sanctionNo", Hibernate.STRING).addScalar("dvNo", Hibernate.STRING).addScalar(
					"dvDate", Hibernate.STRING).addScalar("accOfficer", Hibernate.STRING).addScalar("repSig").addScalar("requestId", Hibernate.STRING).addScalar("sfID", Hibernate.STRING).addScalar(
					"amountClaimed", Hibernate.STRING).addScalar("employeeName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(CghsRequestBean.class)).list());

			sql = "select reim.dv_no dvNo,reim.sanction_no sanctionNo,reim.bill_no billNo,to_char(reim.dv_date,'dd-Mon-yyyy') dvDate,reim.account_officer accOfficer,reim.rep_sig repSig,emp.sfid sfID,emp.name_in_service_book employeeName,reim.amount_claimed amountClaimed,reim.request_id requestId from cghs_reimbursement_details reim,family_details fd,emp_master emp "
					+ "where reim.status=8 and reim.request_type='NCR' and reim.cda_amount is null and fd.status=1 and fd.id=reim.family_member_id and emp.status=1 and emp.sfid=fd.sfid  order by reim.request_id desc";
			cghsRequest.setNonCghsReimList(session.createSQLQuery(sql).addScalar("billNo", Hibernate.STRING).addScalar("sanctionNo", Hibernate.STRING).addScalar("dvNo", Hibernate.STRING).addScalar(
					"dvDate", Hibernate.STRING).addScalar("accOfficer", Hibernate.STRING).addScalar("repSig").addScalar("requestId", Hibernate.STRING).addScalar("sfID", Hibernate.STRING).addScalar(
					"amountClaimed", Hibernate.STRING).addScalar("employeeName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(CghsRequestBean.class)).list());

			sql = "select ced.dv_no dvNo,ced.sanction_no sanctionNo,ced.bill_no billNo,to_char(ced.dv_date,'dd-Mon-yyyy') dvDate,ced.account_officer accOfficer,ced.rep_sig repSig,emp.sfid sfID,emp.name_in_service_book employeeName,(ced.lab_Charges+ced.medicines_Charges+ced.room_Rent+ced.ot_Charges+ced.ot_Consumables+ced.anaesthesia_Charges+ced.implants_Charges+ced.artificial_Charges+ced.procedure_Charges+ced.special_Nurse+ced.miscellaneous_Charges) amountClaimed,ced.request_id requestId "
					+ "from cghs_emergency_details ced,family_details fd,emp_master emp where ced.cda_amount is null and ced.status=8 and fd.status=1 and fd.id=ced.family_member_id and emp.status=1 and emp.sfid=fd.sfid  order by ced.request_id desc";
			cghsRequest.setEmergencyList(session.createSQLQuery(sql).addScalar("billNo", Hibernate.STRING).addScalar("sanctionNo", Hibernate.STRING).addScalar("dvNo", Hibernate.STRING).addScalar(
					"dvDate", Hibernate.STRING).addScalar("accOfficer", Hibernate.STRING).addScalar("repSig").addScalar("requestId", Hibernate.STRING).addScalar("sfID", Hibernate.STRING).addScalar(
					"amountClaimed", Hibernate.STRING).addScalar("employeeName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(CghsRequestBean.class)).list());
*/
			//settement screen code for finance
			
			
	sql = "select distinct cad.issued_amount issuedamount , cad.estimation_amount estimationamount, cad.request_id requestid, crd.sfid sfid, fd.name name, emp.name_in_service_book employeename FROM  cghs_request_details crd,  family_details fd,  emp_master emp,  cghs_advance_details cad left outer join   emp_claim_details ecd on (cad.request_id=ecd.request_id)"
                   +"left outer join finance_details finance on(ecd.id  =finance.reference_id) where cad.status   =8 and cad.cda_amount      is null and crd.request_id       =cad.reference_request_id and fd.status   =1 and ecd.status=1 AND fd.id  =crd.family_member_id AND emp.status  =1 AND emp.sfid   =crd.sfid AND finance.bill_number IS NULL ORDER BY cad.request_id DESC";
		cghsRequest.setAdvanceList(session.createSQLQuery(sql).addScalar("issuedAmount", Hibernate.STRING).addScalar("estimationAmount", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("sfID", Hibernate.STRING).addScalar("name", Hibernate.STRING)
				.addScalar("employeeName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(CghsRequestBean.class)).list());
		 
		sql = "select distinct emp.name_in_service_book employeename, crd.sfid sfid, reim.request_id requestid, reim.admissible_total_amount amountclaimed from cghs_request_details crd, emp_master emp , cghs_reimbursement_details reim left outer join  emp_claim_details ecd on(ecd.request_id=reim.request_id) left outer join finance_details finance on(ecd.id                =finance.reference_id) where reim.request_type  ='CS' and reim.cda_amount     is null and reim.status   =8 AND Crd.Request_Id  =Reim.Reference_Request_Id AND Emp.Sfid             =Crd.Sfid AND Emp.Status           =1 AND finance.bill_number IS NULL ORDER BY reim.request_id DESC";
		cghsRequest.setSettlementList(session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("sfID", Hibernate.STRING).addScalar("amountClaimed", Hibernate.STRING).addScalar("employeeName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(CghsRequestBean.class)).list());

		//sql = "Select Reim.Sanction_No Sanctionno,  Reim.Bill_No Billno, Reim.Account_Officer Accofficer, Reim.Rep_Sig Repsig,Emp.Name_In_Service_Book Employeename, crd.sfid sfID, reim.request_id requestId,reim.admissible_total_amount as admissibleAmount, reim.amount_claimed amountClaimed  FROM cghs_reimbursement_details reim, cghs_request_details crd, emp_master emp WHERE reim.request_type='CR' AND reim.status        =8 AND reim.cda_amount   IS NULL And Crd.Request_Id     =Reim.Reference_Request_Id AND emp.status         =1 And Emp.Sfid           =Crd.Sfid And   Reim.Bill_No Is Null and  reim.sanction_no is null ORDER BY reim.request_id DESC";
		
		sql ="select distinct  emp.name_in_service_book employeename,crd.sfid sfid,reim.request_id requestid,reim.admissible_total_amount as admissibleamount from cghs_reimbursement_details reim, cghs_request_details crd, emp_master emp,emp_claim_details ecd left outer join finance_details finance on(ecd.id=finance.reference_id) where reim.request_type='CR'and reim.status        =8 and reim.cda_amount   is null and crd.request_id     =reim.reference_request_id and reim.request_id     =ecd.request_id AND emp.status   =1 and emp.sfid   =crd.sfid and finance.bill_number is null ORDER BY reim.request_id DESC";
		
		cghsRequest.setReimbursementList(session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("sfID", Hibernate.STRING).addScalar("admissibleAmount", Hibernate.INTEGER).addScalar("employeeName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(CghsRequestBean.class)).list());

		sql = "select distinct  finance.sanction_number sanctionno, finance.bill_number billno, emp.sfid sfid, emp.name_in_service_book employeename, reim.amount_claimed amountclaimed, reim.admissible_total_amount as admissibleamount, reim.request_id requestid from cghs_reimbursement_details reim, family_details fd, emp_master emp,  emp_claim_details ecd left outer join finance_details finance on(ecd.id    =finance.reference_id) where reim.status  =8 and reim.request_type ='NCR' and reim.request_id      =ecd.request_id AND fd.status   =1 AND fd.id  =reim.family_member_id AND emp.status    =1 and emp.sfid   =fd.sfid and finance.bill_number is null ORDER BY reim.request_id DESC";

		cghsRequest.setNonCghsReimList(session.createSQLQuery(sql).addScalar("billNo", Hibernate.STRING).addScalar("sanctionNo", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("sfID", Hibernate.STRING).addScalar(
				"amountClaimed", Hibernate.STRING).addScalar("admissibleAmount", Hibernate.INTEGER).addScalar("employeeName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(CghsRequestBean.class)).list());

		sql = "select distinct emp.sfid sfid, emp.name_in_service_book employeename,  ced.admissible_total_amount amountclaimed,  ced.request_id requestid from  family_details fd,  emp_master emp,  cghs_emergency_details ced left outer join   emp_claim_details ecd on (ecd.request_id=ced.request_id) left outer join finance_details finance ON(ecd.id =finance.reference_id)LEFT OUTER JOIN cda_details cda ON(finance.id     =cda.reference_id) WHERE ced.status   =8 AND fd.status      =1 AND fd.id       =ced.family_member_id AND ced.request_id      =ecd.request_id AND emp.status     =1 and emp.sfid     =fd.sfid AND finance.bill_number IS NULL AND finance.sanction_number   IS NULL ORDER BY ced.request_id DESC";

		cghsRequest.setEmergencyList(session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("sfID", Hibernate.STRING).addScalar("amountClaimed", Hibernate.STRING).addScalar("employeeName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(CghsRequestBean.class)).list());

			
			cghsRequest.setAccountOfficerList(session.createCriteria(SingingAuthorityDTO.class).add(Expression.eq("type", "ACC")).add(Expression.eq("status", 1)).list());

			cghsRequest.setCfaOfficerList(session.createCriteria(SingingAuthorityDTO.class).add(Expression.eq("type", "CFA")).add(Expression.eq("status", 1)).list());

		} catch (Exception e) {
			throw e;
		} 
		return cghsRequest;
	}

	
	
	
	
	@SuppressWarnings("unchecked")
	public CghsRequestBean cdaReimbursementHome(CghsRequestBean cghsRequest) throws Exception {
		Session session = null;
		String sql = null;
		try {
			session = hibernateUtils.getSession();
			sql = "select finance.accountent_sign accofficer, finance.amount issuedamount , finance.rep_sig repsig, cad.issued_amount issuedamount, cad.estimation_amount estimationamount, finance.sanction_number sanctionno,  finance.bill_number billno, cad.request_id requestid,  crd.sfid sfid,  fd.name name,  emp.name_in_service_book employeename from cghs_advance_details cad,  cghs_request_details crd,  family_details fd,  emp_master emp,   emp_claim_details ecd left outer join finance_details finance on(ecd.id =finance.reference_id) left outer join cda_details cda on(finance.id                = cda.reference_id) where cad.status             =8 and crd.request_id           =cad.reference_request_id and cad.request_id   =ecd.request_id and fd.status =1"
                 +"  and fd.id                    =crd.family_member_id and emp.status               =1 and ecd.status =1 and emp.sfid  =crd.sfid and finance.sanction_number is not null and finance.bill_number     is not null and cda.dv_no is null ORDER BY cad.request_id DESC";
			cghsRequest.setCdaadvanceList(session.createSQLQuery(sql).addScalar("issuedAmount", Hibernate.STRING).addScalar("accOfficer", Hibernate.STRING).addScalar("repSig").addScalar("billNo",
					Hibernate.STRING).addScalar("sanctionNo", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("sfID", Hibernate.STRING).addScalar("name", Hibernate.STRING)
					.addScalar("employeeName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(CghsRequestBean.class)).list());
			sql = "select finance.sanction_number sanctionno, finance.bill_number billno,finance.accountent_sign accofficer, finance.rep_sig repsig,  finance.amount issuedamount,  emp.name_in_service_book employeename,  crd.sfid sfid,  reim.request_id requestid  from   cghs_request_details crd,"
                  +"  emp_master emp,  cghs_reimbursement_details reim left outer join emp_claim_details ecd on (reim.request_id=ecd.request_id) left outer join finance_details finance on(ecd.id =finance.reference_id)left outer join cda_details cda on(cda.reference_id          =finance.id) where reim.request_type    ='CS'and reim.cda_amount  is null and reim.status       =8 and crd.request_id    =reim.reference_request_id AND emp.sfid          =crd.sfid AND emp.status        =1 AND finance.bill_number     IS NOT NULL AND finance.sanction_number IS NOT NULL AND cda.dv_no    IS NULL ORDER BY reim.request_id DESC";
			cghsRequest.setCdasettlementList(session.createSQLQuery(sql).addScalar("billNo", Hibernate.STRING).addScalar("sanctionNo", Hibernate.STRING).addScalar("accOfficer", Hibernate.STRING).addScalar("repSig").addScalar("requestId", Hibernate.STRING).addScalar("sfID", Hibernate.STRING).addScalar(
					"issuedAmount", Hibernate.STRING).addScalar("employeeName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(CghsRequestBean.class)).list());

			sql = "select finance.accountent_sign accofficer, finance.rep_sig repsig,  finance.amount issuedamount,  finance.sanction_number sanctionno,  finance.bill_number billno,  emp.name_in_service_book employeename,  crd.sfid sfid,  reim.request_id requestid from cghs_reimbursement_details reim,   cghs_request_details crd, emp_master emp,  emp_claim_details ecd left outer join finance_details finance on(ecd.id   =finance.reference_id) left outer join cda_details cda on(cda.reference_id=finance.id) where reim.request_type ='CR' and reim.status  =8 and crd.request_id  =reim.reference_request_id and reim.request_id    =ecd.request_id and emp.status =1 and emp.sfid =crd.sfid"
                +"  and finance.bill_number  is not null and finance.sanction_number is not null AND cda.dv_no  IS NULL and finance.status=1 ORDER BY reim.request_id DESC";
		cghsRequest.setCdareimbursementList(session.createSQLQuery(sql).addScalar("billNo", Hibernate.STRING).addScalar("sanctionNo", Hibernate.STRING).addScalar("accOfficer", Hibernate.STRING).addScalar("repSig").addScalar("requestId", Hibernate.STRING).addScalar("sfID", Hibernate.STRING).addScalar(
					"issuedAmount", Hibernate.STRING).addScalar("employeeName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(CghsRequestBean.class)).list());

			sql = "select  finance.sanction_number sanctionno, finance.bill_number billno,  emp.sfid sfid,finance.accountent_sign accofficer, finance.rep_sig repsig,  emp.name_in_service_book employeename,  finance.amount issuedamount,  reim.request_id requestid from cghs_reimbursement_details reim,  family_details fd, emp_master emp, emp_claim_details ecd left outer join finance_details finance on(ecd.id                =finance.reference_id) left outer join  cda_details cda on(finance.id  =cda.reference_id)where reim.status  =8 and reim.request_type='NCR'and reim.request_id      =ecd.request_id"
                 + " AND fd.status   =1 AND fd.id     =reim.family_member_id AND emp.status    =1 AND emp.sfid    =fd.sfid AND finance.bill_number IS NOT NULL AND cda.dv_no           IS NULL ORDER BY reim.request_id DESC";
			cghsRequest.setCdanonCghsReimList(session.createSQLQuery(sql).addScalar("billNo", Hibernate.STRING).addScalar("sanctionNo", Hibernate.STRING).addScalar("accOfficer", Hibernate.STRING).addScalar("repSig").addScalar("requestId", Hibernate.STRING).addScalar("sfID", Hibernate.STRING).addScalar(
					"issuedAmount", Hibernate.STRING).addScalar("employeeName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(CghsRequestBean.class)).list());

			//sql = "select ced.sanction_no sanctionno,ced.bill_no billno, ced.account_officer accofficer, ced.rep_sig repsig, emp.sfid sfid, emp.name_in_service_book employeename,(ced.lab_charges+ced.medicines_charges+ced.room_rent+ced.ot_charges+ced.ot_consumables+ced.anaesthesia_charges+ced.implants_charges+ced.artificial_charges+ced.procedure_charges+ced.special_nurse+ced.miscellaneous_charges) amountclaimed, ced.request_id requestid from cghs_emergency_details ced, family_details fd,  emp_master emp where ced.cda_amount is null and ced.status        =8 and fd.status         =1 and fd.id             =ced.family_member_id and emp.status =1 and emp.sfid =fd.sfid and ced.sanction_no is not null and ced.bill_no is not null ORDER BY ced.request_id DESC";
		
			sql="select emp.sfid sfid,  finance.accountent_sign accofficer,  finance.rep_sig repsig,  finance.bill_number billno,  finance.sanction_number sanctionno,  emp.name_in_service_book employeename,  finance.amount issuedamount,  ced.request_id requestid from   family_details fd,  emp_master emp,   cghs_emergency_details ced left outer join   emp_claim_details ecd on (ced.request_id   =ecd.request_id)left outer join finance_details finance on(ecd.id =finance.reference_id) left outer join cda_details cda on(finance.id      =cda.reference_id) where ced.status         =8 and fd.status                =1 and fd.id                    =ced.family_member_id and ecd.status=1 and emp.status               =1 and finance.status =1 and emp.sfid                 =fd.sfid AND finance.bill_number     IS NOT NULL AND finance.sanction_number IS NOT NULL AND cda.dv_no               IS NULL ORDER BY ced.request_id DESC";
			cghsRequest.setCdaemergencyList(session.createSQLQuery(sql).addScalar("billNo", Hibernate.STRING).addScalar("sanctionNo", Hibernate.STRING).addScalar("accOfficer", Hibernate.STRING).addScalar("repSig").addScalar("requestId", Hibernate.STRING).addScalar("sfID", Hibernate.STRING).addScalar(
					"issuedAmount", Hibernate.STRING).addScalar("employeeName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(CghsRequestBean.class)).list());

			cghsRequest.setAccountOfficerList(session.createCriteria(SingingAuthorityDTO.class).add(Expression.eq("type", "ACC")).add(Expression.eq("status", 1)).list());

			cghsRequest.setCfaOfficerList(session.createCriteria(SingingAuthorityDTO.class).add(Expression.eq("type", "CFA")).add(Expression.eq("status", 1)).list());

		} catch (Exception e) {
			throw e;
		} 
		return cghsRequest;
	} 
	
	
	
	
	public String saveCdaAmountAdvance(String requestId, String cdaAmount, String sanctionNo, String billNo, String repSig, String dvNo, String dvDate, String accOfficer) throws Exception {
		Session session = null;
		String message = null;
		CghsAdvanceRequestDTO cghsAdvance = null;
		try {
			session = hibernateUtils.getSession();
			cghsAdvance = (CghsAdvanceRequestDTO) session.get(CghsAdvanceRequestDTO.class, requestId);
			if (!CPSUtils.isNullOrEmpty(cdaAmount)) {
				cghsAdvance.setCdaAmount(cdaAmount);
			}
			if (!CPSUtils.isNullOrEmpty(sanctionNo)) {
				cghsAdvance.setSanctionNo(sanctionNo);
			}
			if (!CPSUtils.isNullOrEmpty(billNo)) {
				cghsAdvance.setBillNo(billNo);
			}
			if (!CPSUtils.isNullOrEmpty(repSig)) {
				cghsAdvance.setRepSig(repSig);
			}
			if (!CPSUtils.isNullOrEmpty(dvNo)) {
				cghsAdvance.setDvNo(dvNo);
			}
			if (!CPSUtils.isNullOrEmpty(dvDate)) {
				cghsAdvance.setDvDate(CPSUtils.convertStringToDate(dvDate));
			}
			if (!CPSUtils.isNullOrEmpty(accOfficer)) {
				cghsAdvance.setAccOfficer(accOfficer);
			}
			session.clear();
			session.update(cghsAdvance);
			session.flush();
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		} 
		return message;
	}

	public String saveCdaAmountReimbursement(String requestId, String cdaAmount, String sanctionNo, String billNo, String repSig, String dvNo, String dvDate, String accOfficer) throws Exception {
		Session session = null;
		String message = null;
		CghsReimbursementRequestDTO cghsReim = null;
		try {
			session = hibernateUtils.getSession();
			cghsReim = (CghsReimbursementRequestDTO) session.get(CghsReimbursementRequestDTO.class, requestId);
			//System.out.println(`4);
			if (!CPSUtils.isNullOrEmpty(cdaAmount)) {
				cghsReim.setCdaAmount(cdaAmount);
			}
			if (!CPSUtils.isNullOrEmpty(sanctionNo)) {
				cghsReim.setSanctionNo(sanctionNo);
			}
			if (!CPSUtils.isNullOrEmpty(billNo)) {
				cghsReim.setBillNo(billNo);
			}
			if (!CPSUtils.isNullOrEmpty(repSig)) {
				cghsReim.setRepSig(repSig);
			}
			if (!CPSUtils.isNullOrEmpty(dvNo)) {
				cghsReim.setDvNo(dvNo);
			}
			if (!CPSUtils.isNullOrEmpty(dvDate)) {
				cghsReim.setDvDate(CPSUtils.convertStringToDate(dvDate));
			}
			if (!CPSUtils.isNullOrEmpty(accOfficer)) {
				cghsReim.setAccOfficer(accOfficer);
			}
			session.clear();
			session.update(cghsReim);
			session.flush();
			
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
		return message;

	}

	public String saveCdaAmountEmergency(String requestId, String cdaAmount, String sanctionNo, String billNo, String repSig, String dvNo, String dvDate, String accOfficer) throws Exception 
	{
		Session session = null;
		String message = null;
		CghsEmergencyRequestDTO cghsEmergency = null;
		try {
			session = hibernateUtils.getSession();
			cghsEmergency = (CghsEmergencyRequestDTO) session.get(CghsEmergencyRequestDTO.class, requestId);
			if (!CPSUtils.isNullOrEmpty(cdaAmount)) {
				cghsEmergency.setCdaAmount(cdaAmount);
			}
			if (!CPSUtils.isNullOrEmpty(sanctionNo)) {
				cghsEmergency.setSanctionNo(sanctionNo);
			}
			if (!CPSUtils.isNullOrEmpty(billNo)) {
				cghsEmergency.setBillNo(billNo);
			}
			if (!CPSUtils.isNullOrEmpty(repSig)) {
				cghsEmergency.setRepSig(repSig);
			}
			if (!CPSUtils.isNullOrEmpty(dvNo)) {
				cghsEmergency.setDvNo(dvNo);
			}
			if (!CPSUtils.isNullOrEmpty(dvDate)) {
				cghsEmergency.setDvDate(CPSUtils.convertStringToDate(dvDate));
			}
			if (!CPSUtils.isNullOrEmpty(accOfficer)) {
				cghsEmergency.setAccOfficer(accOfficer);
			}
			session.clear();
			session.update(cghsEmergency);
			session.flush();
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		} 
		return message;

	}
   //cghs finance settlement code start
	public String saveFinanceAmount(JSONObject subJson, CghsRequestBean cghsRequest) throws Exception {
		Session session = null;
		String message = null;
		EmployeeClaimDetailsDTO employeeClaimDetailsDTO=null;
		//FinanceDetailsDTO financeDetails=null;
		try {
			session = hibernateUtils.getSession();
			employeeClaimDetailsDTO = (EmployeeClaimDetailsDTO)session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq(CPSConstants.REQUESTID,Integer.valueOf(subJson.get("requestId").toString()))).add(Expression.eq("requestType", cghsRequest.getType())).add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
			if(!CPSUtils.isNullOrEmpty(employeeClaimDetailsDTO)){
			
					FinanceDetailsDTO financeDetails = new FinanceDetailsDTO();
					financeDetails.setReferenceID(employeeClaimDetailsDTO.getId());
				
						if(cghsRequest.getType().equals("settlement")||cghsRequest.getType().equals("reimbursement")||cghsRequest.getType().equals("nonCghsReim")){
																	   
						 if (!CPSUtils.isNullOrEmpty(subJson.get("Amount").toString())) {
							
							 financeDetails.setAmount(Float.valueOf((subJson.get("Amount")).toString()));}
						 if(!CPSUtils.isNullOrEmpty(subJson.get("sanctionNo").toString())){
						      financeDetails.setSanctionNo(subJson.get("sanctionNo").toString());}
						  if(!CPSUtils.isNullOrEmpty(subJson.get("billNo").toString())){
						     financeDetails.setBillNo(subJson.get("billNo").toString());}
						 if(!CPSUtils.isNullOrEmpty(subJson.get("repSig").toString())){
						     financeDetails.setRepSig(subJson.get("repSig").toString());}
						 if(!CPSUtils.isNullOrEmpty(subJson.get("accOfficer").toString())){
						   financeDetails.setAccountentSign(subJson.get("accOfficer").toString());}
						  financeDetails.setLastModifiedBy(cghsRequest.getSfID());
						  financeDetails.setCreatedBy(cghsRequest.getSfID());
						  financeDetails.setCreationTime(CPSUtils.getCurrentDateWithTime());
					      financeDetails.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
					      financeDetails.setStatus(1);
					      session.clear();
						   session.save(financeDetails);
						  session.flush();
						  message = CPSConstants.SUCCESS;
					}
					else if(cghsRequest.getType().equals("advance")){
						 if (!CPSUtils.isNullOrEmpty(subJson.get("Amount").toString())) {
	                	      financeDetails.setAmount(Float.valueOf((subJson.get("Amount")).toString()));}
	                	   if(!CPSUtils.isNullOrEmpty(subJson.get("sanctionNo").toString())){
						     financeDetails.setSanctionNo(subJson.get("sanctionNo").toString());}
	                	   if(!CPSUtils.isNullOrEmpty(subJson.get("billNo").toString())){
						      financeDetails.setBillNo(subJson.get("billNo").toString());}
	                	   if(!CPSUtils.isNullOrEmpty(subJson.get("repSig").toString())){
						       financeDetails.setRepSig(subJson.get("repSig").toString());}
	                	   if(!CPSUtils.isNullOrEmpty(subJson.get("accOfficer").toString())){
						      financeDetails.setAccountentSign(subJson.get("accOfficer").toString());}
	                	   financeDetails.setCreatedBy(cghsRequest.getSfID());
						financeDetails.setLastModifiedBy(cghsRequest.getSfID());
						financeDetails.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
						financeDetails.setCreationTime(CPSUtils.getCurrentDateWithTime());
						financeDetails.setStatus(1);
					 session.clear();
					     session.save(financeDetails);
						 session.flush();
						 message = CPSConstants.SUCCESS;
					}
                   else if(CPSUtils.compareStrings(cghsRequest.getType(),"emergency")){
                	   if (!CPSUtils.isNullOrEmpty(subJson.get("Amount").toString())) {
                	      financeDetails.setAmount(Float.valueOf((subJson.get("Amount")).toString()));}
                	   if(!CPSUtils.isNullOrEmpty(subJson.get("sanctionNo").toString())){
					     financeDetails.setSanctionNo(subJson.get("sanctionNo").toString());}
                	   if(!CPSUtils.isNullOrEmpty(subJson.get("billNo").toString())){
					      financeDetails.setBillNo(subJson.get("billNo").toString());}
                	   if(!CPSUtils.isNullOrEmpty(subJson.get("repSig").toString())){
					       financeDetails.setRepSig(subJson.get("repSig").toString());}
                	   if(!CPSUtils.isNullOrEmpty(subJson.get("accOfficer").toString())){
					      financeDetails.setAccountentSign(subJson.get("accOfficer").toString());}
                	   financeDetails.setCreatedBy(cghsRequest.getSfID());
                	  financeDetails.setLastModifiedBy(cghsRequest.getSfID());
					  financeDetails.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
					  financeDetails.setCreationTime(CPSUtils.getCurrentDateWithTime());
					  financeDetails.setStatus(1);
					  session.clear();
					  session.save(financeDetails);
					  session.flush();
					  message = CPSConstants.SUCCESS;
					    }
						/*session.clear();
					     session.saveOrUpdate(financeDetails);
						 session.flush();*/
						
				
			}//close not null emp_claims_details
		     	
			
		    	
	    	  
	
	    }
        //}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		 } 
		 return message;

	 }
	
   //cghs finance settlement code end
	//cghs cda settlement code start
	
	
	public String saveCdaAmount(JSONObject subJson, CghsRequestBean cghsRequest) throws Exception {
	String message =null;
	Session session =null;
	EmployeeClaimDetailsDTO employeeClaimDetailsDTO=null;
	FinanceDetailsDTO financeDetails=null;
	try{
		session=hibernateUtils.getSession();
		employeeClaimDetailsDTO = (EmployeeClaimDetailsDTO) session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq(CPSConstants.REQUESTID, Integer.valueOf(subJson.get("requestId").toString()))).add(Expression.eq("requestType", cghsRequest.getType())).add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
		if(!CPSUtils.isNullOrEmpty(employeeClaimDetailsDTO)){
		financeDetails = (FinanceDetailsDTO) session.createCriteria(FinanceDetailsDTO.class).add(Expression.eq(CPSConstants.REFERENCEID, employeeClaimDetailsDTO.getId())).add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
		if(!CPSUtils.isNullOrEmpty(financeDetails)){
		CDADetailsDTO cdaDetailsDTO= new CDADetailsDTO();
		cdaDetailsDTO.setReferenceID(financeDetails.getId());
		if(!CPSUtils.isNullOrEmpty(subJson.get("dvNo").toString())){
			cdaDetailsDTO.setDvNumber(subJson.getString("dvNo").toString());
		}
		if(!CPSUtils.isNullOrEmpty(subJson.get("dvDate").toString())){
			cdaDetailsDTO.setDvDate(CPSUtils.convertStringToDate(subJson.getString("dvDate").toString()));
		}
		if (!CPSUtils.isNullOrEmpty(subJson.get("Amount").toString())) {
			cdaDetailsDTO.setCdaAmount(Float.valueOf(subJson.get("Amount").toString()));
		}
		cdaDetailsDTO.setCreatedBy(cghsRequest.getSfID());
		cdaDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
		cdaDetailsDTO.setLastModifiedBy(cghsRequest.getSfID());
		cdaDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
		 session.clear();
		session.save(cdaDetailsDTO);
		session.flush();
		/*if(cghsRequest.getType().equals("settlement")||cghsRequest.getType().equals("reimbursement")||cghsRequest.getType().equals("nonCghsReim")){
		}
		else if(cghsRequest.getType().equals("advance")){
		}
		  else if(CPSUtils.compareStrings(cghsRequest.getType(),"emergency")){
			  
		  }*/
		message = CPSConstants.SUCCESS;
		}}
	}catch(Exception e){
	throw e;	
	}
		return message;
	}
	 //cghs cda settlement code end
	
	
	
}
