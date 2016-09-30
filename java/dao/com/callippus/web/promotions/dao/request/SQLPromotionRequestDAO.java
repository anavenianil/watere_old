package com.callippus.web.promotions.dao.request;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.callippus.web.beans.dto.ApplicationRoleMappingDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.promotions.beans.request.PromotionRequestBean;
import com.callippus.web.promotions.dto.AssessmentDetailsDTO;
import com.callippus.web.promotions.dto.OptionalCertificateDTO;

@Service
public class SQLPromotionRequestDAO implements IPromotionRequestDAO {
	private static Log log = LogFactory.getLog(SQLPromotionRequestDAO.class);
	@Autowired
	private HibernateUtils hibernateUtils;


	
	@Override
	public PromotionRequestBean getEmployeeDetails(PromotionRequestBean promotionBean) throws Exception {
		Session session = null;
		try {

			session = hibernateUtils.getSession(); //session = sessionFactory.getCurrentSession();

			promotionBean.setEmployeeDetails((EmployeeBean) session.createCriteria(EmployeeBean.class).add(Expression.eq("userSfid", promotionBean.getSfID())).uniqueResult());
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	
	public PromotionRequestBean getOptionalCertificateList(PromotionRequestBean promotionBean) throws Exception {
		Session session = null;
		try {

			session = hibernateUtils.getSession(); 
			promotionBean
					.setOptionalCertificateList(session
							.createSQLQuery(
									"select poc.id id,poc.assessment_id assessmentID,POC.REQUESTED_BY sfID,poc.increment_date incrementDate,POC.PAY_UPDATE payStatus,poc.requested_date requestedDate,poc.scale_of_pay scaleOfPay, poc.PAY_UPDATE payStatus,pad.interview_date interviewDate from pro_assessment_details pad,pro_optional_certificate poc "
											+ "where poc.status in(1,100) and poc.assessment_id=pad.id and pad.sfid=?").addScalar("id", Hibernate.INTEGER).addScalar("assessmentID", Hibernate.INTEGER).addScalar("payStatus", Hibernate.INTEGER)
							.addScalar("interviewDate", Hibernate.DATE).addScalar("sfID", Hibernate.STRING).addScalar("scaleOfPay", Hibernate.STRING).addScalar("incrementDate", Hibernate.DATE).addScalar("requestedDate", Hibernate.DATE).addScalar("payStatus", Hibernate.INTEGER)
							.setResultTransformer(Transformers.aliasToBean(AssessmentDetailsDTO.class)).setString(0, promotionBean.getSfID()).list());

		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	
	@Override
	public PromotionRequestBean deleteOptionalCertificate(PromotionRequestBean promotionBean) throws Exception {
		Session session = null;
		try {

			session = hibernateUtils.getSession(); //session = sessionFactory.getCurrentSession();

			OptionalCertificateDTO optionalCertificateDTO = (OptionalCertificateDTO) session.get(OptionalCertificateDTO.class, Integer.valueOf(promotionBean.getNodeID()));
			optionalCertificateDTO.setStatus(0);
			session.saveOrUpdate(optionalCertificateDTO);
			session.flush();
			promotionBean.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		}
		return promotionBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public String checkAssessmentStatus(PromotionRequestBean promotionBean) throws Exception {
		Session session = null;
		List<AssessmentDetailsDTO> list = null;
		try {

			session = hibernateUtils.getSession(); //session = sessionFactory.getCurrentSession();


			/*list = session.createCriteria(AssessmentDetailsDTO.class).add(Expression.eq("sfID", promotionBean.getSfID()))
			.add(Expression.eq(CPSConstants.STATUS, Integer.valueOf(CPSConstants.STATUSPROMOTED)))
			.list();*/
            AssessmentDetailsDTO assessmentDetailsDTO=(AssessmentDetailsDTO)session.createSQLQuery("select id from pro_assessment_details " +
    		        "where sfid='"+promotionBean.getSfID()+"' and status in ("+Integer.valueOf(CPSConstants.STATUSPROMOTED)+","+Integer.valueOf(CPSConstants.STATUSDEFERRED)+") and " +
    		        "year_id='"+promotionBean.getYearID()+"'").addScalar("id",Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(AssessmentDetailsDTO.class)).uniqueResult();
			if (!CPSUtils.isNull(assessmentDetailsDTO)) {
				promotionBean.setResult(CPSConstants.SUCCESS);
			} else {
				promotionBean.setResult(CPSConstants.NOTAPPLICABLE);
			}
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}

	
	@Override
	public PromotionRequestBean submitOptionalCertificate(PromotionRequestBean promotionBean) throws Exception {
		Session session = null;
		OptionalCertificateDTO optionalCertificateDTO = null;
		try {

			session = hibernateUtils.getSession(); //session = sessionFactory.getCurrentSession();


			if (CPSUtils.isNullOrEmpty(promotionBean.getNodeID())) {
				// New
				optionalCertificateDTO = new OptionalCertificateDTO();

			//AssessmentDetailsDTO assessmentDetailsDTO = (AssessmentDetailsDTO) session.createCriteria(AssessmentDetailsDTO.class).add(Expression.eq("sfID", promotionBean.getSfID())).add(
						//Expression.eq(CPSConstants.STATUS, Integer.valueOf(CPSConstants.STATUSPROMOTED))).uniqueResult();
               AssessmentDetailsDTO assessmentDetailsDTO=(AssessmentDetailsDTO)session.createSQLQuery("select id from pro_assessment_details " +
		        "where sfid='"+promotionBean.getSfID()+"' and status IN("+Integer.valueOf(CPSConstants.STATUSPROMOTED)+","+Integer.valueOf(CPSConstants.STATUSDEFERRED)+") and " +
		        "year_id='"+promotionBean.getYearID()+"'").addScalar("id",Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(AssessmentDetailsDTO.class)).uniqueResult();
				optionalCertificateDTO.setAssessmentID(assessmentDetailsDTO.getId());
				optionalCertificateDTO.setStatus(1);
				optionalCertificateDTO.setRequestedBy(promotionBean.getSfID());
				optionalCertificateDTO.setRequestedDate(CPSUtils.getCurrentDateWithTime());
			} else {
				optionalCertificateDTO = (OptionalCertificateDTO) session.get(OptionalCertificateDTO.class, Integer.valueOf(promotionBean.getNodeID()));
			}
			optionalCertificateDTO.setIpAddress(promotionBean.getIpAddress());
			if(!CPSUtils.isNullOrEmpty(promotionBean.getIncrementDate())){
			optionalCertificateDTO.setIncrementDate(promotionBean.getIncrementDate());
			}
			if(!CPSUtils.isNullOrEmpty(promotionBean.getScaleOfPay())){
			optionalCertificateDTO.setScaleOfPay(Float.valueOf(promotionBean.getScaleOfPay()));
			} optionalCertificateDTO.setPayUpdate(promotionBean.getPayUpdate().intValue());
		   session.saveOrUpdate(optionalCertificateDTO);
		   session.flush();
		   session.clear();
			promotionBean.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public String checkDuplicateOptionalCertificate(PromotionRequestBean promotionBean) throws Exception {
		Session session = null;
		List<String> list = null;
		StringBuffer sb = new StringBuffer();
		try {

			session = hibernateUtils.getSession(); //session = sessionFactory.getCurrentSession();


			sb.append("select poc.ip_address from pro_assessment_details pro,pro_optional_certificate poc where pro.sfid='" + promotionBean.getSfID() + "' and pro.status='"
					+ CPSConstants.STATUSPROMOTED + "' and poc.status IN(1,100) and poc.assessment_id=pro.id and pro.year_id="+promotionBean.getYearID()+"");
			if (!CPSUtils.isNullOrEmpty(promotionBean.getNodeID())) {
				sb.append("and poc.id != " + promotionBean.getNodeID());
			}

			list = session.createSQLQuery(sb.toString()).list();
			if (CPSUtils.checkList(list)) {
				// duplicate
				promotionBean.setResult(CPSConstants.DUPLICATE);
			} else {
				promotionBean.setResult(CPSConstants.SUCCESS);
			}
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	
	public PromotionRequestBean getOptionalCertificatesDetails(PromotionRequestBean promotionBean) throws Exception {
		Session session = null;
		Collection sfidList = new ArrayList();
		try {

			session = hibernateUtils.getSession(); //session = sessionFactory.getCurrentSession();
//           for(int i=0;i<promotionBean.getAssessmentDetails().size()-1;i++){
//        	   sfidList.add(promotionBean.getAssessmentDetails().get(i).getSfID());
//           }
           
          // Type String;
		/*promotionBean
					.setViewOptionalCertificateList(session
							.createSQLQuery("select emp.sfid sfID,emp.name_in_service_book empName,poc.id id,poc.assessment_id assessmentID,poc.requested_date requestedDate,poc.scale_of_pay scaleOfPay,pad.interview_date interviewDate, pad.promotion_date promotionDate from pro_assessment_details pad,pro_optional_certificate poc,emp_master emp " +
									"where poc.status=1 and poc.assessment_id=pad.id and pad.sfid=emp.sfid and pad.sfid In(?)")
									.addScalar("sfID",Hibernate.STRING).addScalar("empName",Hibernate.STRING).addScalar("promotionDate",Hibernate.DATE)
									.addScalar("id", Hibernate.INTEGER).addScalar("assessmentID", Hibernate.INTEGER)
							.addScalar("interviewDate", Hibernate.DATE).addScalar("scaleOfPay", Hibernate.STRING).addScalar("requestedDate", Hibernate.DATE)
							.setResultTransformer(Transformers.aliasToBean(AssessmentDetailsDTO.class)).setParameter(0, sfidList,String).list());
*/
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PromotionRequestBean getPromotedCandidatesRole(PromotionRequestBean promotionBean) throws Exception {
		
		Session session = null;
		
		session =hibernateUtils.getSession();
	
	List<ApplicationRoleMappingDTO> list = 	session.createCriteria(ApplicationRoleMappingDTO.class).add(Expression.eq("sfid", promotionBean.getSfID())).add(Expression.eq("status",1)).list();
		
		promotionBean.setAppRoleList(list);
		
		return promotionBean;
	}
}
