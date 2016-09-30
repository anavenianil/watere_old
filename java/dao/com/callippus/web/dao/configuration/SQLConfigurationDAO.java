package com.callippus.web.dao.configuration;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.configuration.ConfigurationBean;
import com.callippus.web.beans.configuration.SigningAuthorityDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.cghs.beans.dto.SingingAuthorityDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.ConnectionUtil;

@Service
public class SQLConfigurationDAO implements IConfigurationDAO, Serializable {
	private static final long serialVersionUID = -6728048066023585848L;
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	public ConfigurationBean getConfigurationDetails() throws Exception {
		Session session = null;
		ConfigurationBean configutationBean = null;
		try {
			configutationBean = new ConfigurationBean();
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select MAX(CASE WHEN name = 'ADMIN' THEN value END) AS admin,MAX(CASE WHEN name = 'CGHS_SON_AGE_LIMIT' THEN value END) AS cghsSonAgeLimit, "
				+ "MAX(CASE WHEN name = 'CGHS_MAJOR_AGE_LIMIT' THEN value END) AS cghsMajorAgeLimit,MAX(CASE WHEN name = 'CGHS_SALARY_LIMIT' THEN value END) AS cghsSalaryLimit, "
				+ "MAX(CASE WHEN name = 'CML_WO_MC_EXCEPTIONID' THEN value END) AS cmlWoMcExceptionId,MAX(CASE WHEN name = 'DOB' THEN value END) AS minAgeForJob, "
				+ "MAX(CASE WHEN name = 'ESCALATED_TIME' THEN value END) AS escalationtime, MAX(CASE WHEN name = 'LEAVE_SCRIPT_START_DATE' THEN value END) AS leaveStartDate,"
				+ "MAX(CASE WHEN name = 'LTC_JUNIOR_WORK_EXP' THEN value END) AS ltcJuniorWorkExp,MAX(CASE WHEN name = 'LND_WO_MC_EXCEPTIONID' THEN value END) AS lndWoMcExceptionId,"
				+ "MAX(CASE WHEN name = 'LTC_PRIOR_DOB' THEN value END) AS ltcPriorDob,MAX(CASE WHEN name = 'LTC_SENIOR_WORK_EXP' THEN value END) AS ltcSeniorrWorkExp,"
				+ "MAX(CASE WHEN name = 'LTC_SON_AGE_LIMIT' THEN value END) AS ltcSonAgeLimit,MAX(CASE WHEN name = 'MY_REQUESTS_DASHBOARD_RECORDS' THEN value END) AS myRequestDashboardRecords,"
				+ "MAX(CASE WHEN name = 'SCG_SP_ALLOWANCE' THEN value END) AS specialAllowance,MAX(CASE WHEN name = 'WASH_ALLOWANCE' THEN value END) AS washAllowance,"
				+ "MAX(CASE WHEN name = 'XEROX_ALLOWANCE' THEN value END) AS xeroxAllowance,MAX(CASE WHEN name = 'WFUND_OFFICERS' THEN value END) AS wfundOfficers,"
				+ "MAX(CASE WHEN name = 'WFUND_STAFF' THEN value END) AS wfundStaff,MAX(CASE WHEN name = 'BFUND_OFFICERS' THEN value END) AS bfundOfficers,"
				+ "MAX(CASE WHEN name = 'BFUND_STAFF' THEN value END) AS bfundStaff,MAX(CASE WHEN name = 'REGFUND_OFFICERS' THEN value END) AS regFundOfficers,"
				+ "MAX(CASE WHEN name = 'REGFUND_STAFF' THEN value END) AS regFundStaff,MAX(CASE WHEN name = 'MESS_OFFICERS' THEN value END) AS messOfficers,"
				+ "MAX(CASE WHEN name = 'MESS_OUTSIDE' THEN value END) AS messOutside,MAX(CASE WHEN name = 'CGHS_ADVANCE_APPROVED_PERCENTAGE' THEN value END) AS cghsAdvApprPerc,"
				+ "MAX(CASE WHEN name = 'WATER' THEN value END) AS water," + "MAX(CASE WHEN name = 'FURNITURE' THEN value END) AS furniture,"
				+ "MAX(CASE WHEN name = 'LTC_MAJOR_AGE_LIMIT' THEN value END) AS ltcMajorAgeLimit," + "MAX(CASE WHEN name = 'LTC_ADVANCE_APPROVED_PERCENTAGE' THEN value END) AS ltcAdvancePercentage,"
				+ "MAX(CASE WHEN name = 'LTC_PRIOR_ADVANCE_REQUEST_DAYS' THEN value END) AS ltcAdvancePriorDays,MAX(CASE WHEN name = 'LTC_DA_PERCENTAGE' THEN value END) AS ltcDaPercengate,"
				+ "MAX(CASE WHEN name = 'DASHBOARD_RECORDS' THEN value END) AS dashboardRecords,"
				+ "MAX(CASE WHEN name = 'IT_TRANSPORT_ALLOWANCE' THEN value END) AS itTraAllw ,MAX(CASE WHEN name = 'AGE_LIMIT_FOR_SRCITIZEN' THEN value END) AS srCitizemMaxAge,"
				+ "MAX(CASE WHEN name = 'TUITION_FEE_MAX_LIMIT_PER_ONE_CHILD' THEN value END) AS tutionFeeMaxLimitPerOneChild,"
				+ "MAX(CASE WHEN name = 'TUITION_FEE_MAX_CHILD_TO_BE_CLAIMED' THEN value END) AS tutionFeeMaxChildToBeClaimed," 
				+"MAX(CASE WHEN name= 'TADA_REQUEST_TIMEBOUND_PAST' THEN VALUE END) AS tadaTimeBoundPast,"                                         //This is condition for tadaDaPerecentage
				+"MAX(CASE WHEN name= 'TADA_REQUEST_TIMEBOUND_FUTURE' THEN VALUE END) AS tadaTimeBoundFuture,"       
				+ " MAX(CASE WHEN name = 'IT_PHYSICALLY_HANDICAPPED' THEN value END) AS itphysicallyHandicapped from configuration_details";
			configutationBean = (ConfigurationBean) session.createSQLQuery(query).addScalar("ltcAdvancePriorDays", Hibernate.STRING)
					.addScalar("ltcMajorAgeLimit", Hibernate.STRING).addScalar("ltcAdvancePercentage", Hibernate.STRING)
					.addScalar("admin", Hibernate.STRING).addScalar("escalationtime", Hibernate.STRING).addScalar("minAgeForJob",Hibernate.STRING)
					.addScalar("ltcDaPercengate", Hibernate.STRING).addScalar("leaveStartDate", Hibernate.STRING)
					.addScalar("cghsMajorAgeLimit", Hibernate.STRING).addScalar("cghsSonAgeLimit", Hibernate.STRING).addScalar("cghsSalaryLimit", Hibernate.STRING)
					.addScalar("cmlWoMcExceptionId", Hibernate.STRING).addScalar("ltcJuniorWorkExp", Hibernate.STRING).addScalar("dashboardRecords", Hibernate.STRING)
					.addScalar("lndWoMcExceptionId", Hibernate.STRING).addScalar("ltcPriorDob", Hibernate.STRING).addScalar("myRequestDashboardRecords", Hibernate.STRING)
					.addScalar("ltcSeniorrWorkExp", Hibernate.STRING).addScalar("ltcSonAgeLimit", Hibernate.STRING).addScalar("specialAllowance", Hibernate.STRING)
					.addScalar("washAllowance", Hibernate.STRING).addScalar("xeroxAllowance", Hibernate.STRING).addScalar("wfundOfficers", Hibernate.STRING)
					.addScalar("wfundStaff", Hibernate.STRING).addScalar("bfundOfficers", Hibernate.STRING).addScalar("bfundStaff", Hibernate.STRING)
					.addScalar("regFundOfficers", Hibernate.STRING).addScalar("regFundStaff", Hibernate.STRING).addScalar("messOfficers", Hibernate.STRING)
					.addScalar("messOutside", Hibernate.STRING).addScalar("water", Hibernate.FLOAT).addScalar("furniture", Hibernate.FLOAT)
					.addScalar("cghsAdvApprPerc", Hibernate.STRING).addScalar("srCitizemMaxAge", Hibernate.STRING).addScalar("itTraAllw", Hibernate.STRING)
					.addScalar("tutionFeeMaxLimitPerOneChild", Hibernate.STRING).addScalar("tutionFeeMaxChildToBeClaimed", Hibernate.STRING).addScalar("tadaTimeBoundPast", Hibernate.INTEGER).addScalar("tadaTimeBoundFuture", Hibernate.INTEGER)  //This condition has been added
					.addScalar("itphysicallyHandicapped", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(ConfigurationBean.class)).uniqueResult();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return configutationBean;
	}

	public String submitConfigurationDetails(String configurationDetails) throws Exception {
		Session session = null;
		Connection con = null;
		String message = "";
		PreparedStatement ps = null;
		String updateConfiguration = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			String[] configurationValues = configurationDetails.split("#");
			for (int i = 0; i < configurationValues.length; i++) {
				updateConfiguration = "update configuration_details set value=? where name=?";
				System.out.println(updateConfiguration);
				ps = con.prepareStatement(updateConfiguration);
				ps.setString(1, configurationValues[i].split(",")[1]);
				ps.setString(2, configurationValues[i].split(",")[0]);
				ps.executeUpdate();
			}
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, null);
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> getExceptionalList(String id) throws Exception {
		Session session = null;
		List<KeyValueDTO> exceptionalList = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			exceptionalList = session.createSQLQuery("select led.id key,led.description value from leave_exception_details led where led.leave_type_id=" + id + " and led.status=1 ").addScalar(
					"value", Hibernate.STRING).addScalar("key", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return exceptionalList;
	}
	//signing authority details
	public String submitSigningAuthorityDetails(ConfigurationBean configurationBean)throws Exception{
		Session session = null;
		String message ="";
		String sfid;
		try{
			session =hibernateUtils.getSession();
			sfid =(String)session.createSQLQuery("select sfid from emp_master where NAME_IN_SERVICE_BOOK ='"+configurationBean.getAuthorityEmpName().split("-")[0]+"' and status=1 and sfid='"+configurationBean.getAuthorityEmpName().split("-")[1]+"'").uniqueResult();
			if(sfid!=null){
				SigningAuthorityDTO signingAuthorityDTO = new SigningAuthorityDTO();
				configurationBean.setMessage(checkSigningAuthorityDetails(configurationBean));
				if(!CPSUtils.compareString(configurationBean.getMessage(), CPSConstants.DUPLICATE)){
					if(!CPSUtils.isNullOrEmpty(configurationBean.getPk())){
						signingAuthorityDTO.setId(Integer.parseInt(configurationBean.getPk()));
					}
					signingAuthorityDTO.setName(configurationBean.getAuthorityEmpName().split("-")[0]);
					signingAuthorityDTO.setSfid(sfid);
					signingAuthorityDTO.setType(configurationBean.getAuthorityType());
					signingAuthorityDTO.setStatus(1);
					session.saveOrUpdate(signingAuthorityDTO);
		   	        message=CPSConstants.SUCCESS;
		   	       if(!CPSUtils.isNullOrEmpty(configurationBean.getPk())){
						message=CPSConstants.UPDATE;
					}
				}else{
					message=CPSConstants.DUPLICATE;
				}
			}
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	@SuppressWarnings("unchecked")
	public List<SigningAuthorityDTO> getSigningAuthorityList()throws Exception{
		Session session =null;
		List<SigningAuthorityDTO>  authorityDetails =new ArrayList<SigningAuthorityDTO>();
		try{
			session=hibernateUtils.getSession();
			authorityDetails=session.createCriteria(SigningAuthorityDTO.class).add(Expression.eq("status", 1)).addOrder(Order.asc("type")).list();
		}catch (Exception e) {
			throw e;
		}
		return authorityDetails;
	}
	public String checkSigningAuthorityDetails(ConfigurationBean configurationBean)throws Exception{
		Session session =null;
		String message="";
		try{
			Criteria crit =null;
			session=hibernateUtils.getSession();
			crit=session.createCriteria(SigningAuthorityDTO.class).add(Expression.eq("status", 1)).add(Expression.ilike("type", configurationBean.getAuthorityType().trim())).add(Expression.ilike("sfid", configurationBean.getAuthorityEmpName().split("-")[1].trim()));
			if(CPSUtils.checkList(crit.list())){
				message=CPSConstants.DUPLICATE;
			}
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	public String deleteSigningAuthorityDetails(int id)throws Exception{
		Session session =null;
		String message ="";
		try{
			session=hibernateUtils.getSession();
			SigningAuthorityDTO signingAuthorityDTO=(SigningAuthorityDTO)session.get(SigningAuthorityDTO.class,id);
		if(signingAuthorityDTO!=null){
			signingAuthorityDTO.setStatus(0);
			session.update(signingAuthorityDTO);
			message=CPSConstants.DELETE;
		}else{
			message=CPSConstants.FAILED;
		}
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
}
