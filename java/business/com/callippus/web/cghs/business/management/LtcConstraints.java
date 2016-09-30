package com.callippus.web.cghs.business.management;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.ConstraintsDTO;
import com.callippus.web.beans.dto.ResultDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;

@Service
public class LtcConstraints extends FamilyMemberConstraints {
	private static Log log = LogFactory.getLog(LtcConstraints.class);
	@Autowired
	private IComonObjectDAO commonObjectDAO;
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	public ResultDTO checkLtcConstraints(ConstraintsDTO constraintsDTO, ResultDTO permanent) throws Exception {
		log.debug("LtcConstraints>>>>>>>>>>>>>>>>>>>>>>>>>> checkLtcConstraints()");
		String maritalStatus = null;
		String empGender = null;
		String relationName = null;
		try {
			if (CPSUtils.isNull(permanent.getResult())) {
				permanent.setResult(CPSConstants.SUCCESS);
			}
			// get the govt servant gender
			empGender = getEmployeeGender(constraintsDTO.getSfid());
			// get family member/dependent marital status name
			maritalStatus = getMaritalStatus(constraintsDTO.getMaritalId());
			// get family member/dependent relationship
			relationName = getRelationship(constraintsDTO.getRelationId());

			// check the employee gender
			if (CPSUtils.compareStrings(CPSConstants.M, empGender)) {
				// male
				if (CPSUtils.compareStrings(CPSConstants.FATHER, relationName) || CPSUtils.compareStrings(CPSConstants.MOTHER, relationName)) {
					permanent = resultMigrate(permanent, checkSalary(constraintsDTO.getEarningMoney(), CPSConstants.LTC));
					permanent = resultMigrate(permanent, checkFatherAndMother(CPSConstants.LTC, constraintsDTO.getSfid(), constraintsDTO.getRelationId(), "9,10"));
				} else if (CPSUtils.compareStrings(CPSConstants.SON, relationName)) {
					permanent = resultMigrate(permanent, checkSalary(constraintsDTO.getEarningMoney(), CPSConstants.LTC));
					permanent = resultMigrate(permanent, marriageStatus(maritalStatus));
					//String ltcSonAgeLimit = commonObjectDAO.getConfigurationValue(CPSConstants.LTC_SON_AGE_LIMIT);
					//permanent = resultMigrate(permanent, checkAge(constraintsDTO.getAge(), constraintsDTO.getDob(), CPSConstants.SON, ltcSonAgeLimit, constraintsDTO.getType()));
					permanent = resultMigrate(permanent, checkLtcSonAndDaughterConstrains(constraintsDTO.getAge(), constraintsDTO.getDob(), constraintsDTO.getSfid(), constraintsDTO.getId()));
				} else if (CPSUtils.compareStrings(CPSConstants.BROTHER, relationName)) {
					String ltcMajorAgeLimit = commonObjectDAO.getConfigurationValue(CPSConstants.LTC_MAJOR_AGE_LIMIT);
					permanent = resultMigrate(permanent, checkSalary(constraintsDTO.getEarningMoney(), CPSConstants.LTC));
					permanent = resultMigrate(permanent, checkAge(constraintsDTO.getAge(), constraintsDTO.getDob(), CPSConstants.BROTHER, ltcMajorAgeLimit, constraintsDTO.getType()));
					// IN ABOVE 22 IS THE MAJOR AGE LIMIT
				} else if (CPSUtils.compareStrings(CPSConstants.DAUGHTER, relationName)) {
					permanent = resultMigrate(permanent, checkLtcSonAndDaughterConstrains(constraintsDTO.getAge(), constraintsDTO.getDob(), constraintsDTO.getSfid(), constraintsDTO.getId()));
				} else if (CPSUtils.compareStrings(CPSConstants.DAUGHTERINLAW, relationName)) {
					permanent.setResult(CPSConstants.FAILED);
					permanent.setRemark(permanent.getRemark() + "," + CPSConstants.LTC_DAUGHTERINLAW_REMARKS);
				} else if (CPSUtils.compareStrings(CPSConstants.SISTER, relationName)) {
					permanent = resultMigrate(permanent, marriageStatus(maritalStatus));
				} else if (CPSUtils.compareStrings(CPSConstants.SONINLAW, relationName)) {
					permanent.setResult(CPSConstants.FAILED);
					permanent.setRemark(permanent.getRemark() + "," + CPSConstants.LTC_SONINLAW_REMARKS);
				} else if (CPSUtils.compareStrings(CPSConstants.FATHERINLAW, relationName) || CPSUtils.compareStrings(CPSConstants.MOTHERINLAW, relationName)) {
					permanent = resultMigrate(permanent, checkSalary(constraintsDTO.getEarningMoney(), CPSConstants.LTC));
					permanent = resultMigrate(permanent, checkFatherAndMother(CPSConstants.LTC, constraintsDTO.getSfid(), constraintsDTO.getRelationId(), "1,2"));
				} else if (CPSUtils.compareStrings(CPSConstants.WIFE, relationName)) {
					// permanent = resultMigrate(permanent, checkSalary(constraintsDTO.getEarningMoney(),CPSConstants.LTC));
					/**
					 * For Spouse no need to check any constrains
					 */
					permanent.setResult(CPSConstants.SUCCESS);
				} else if (CPSUtils.compareStrings(CPSConstants.SELF, relationName)) {
					permanent.setResult(CPSConstants.SUCCESS);
				} else {
					permanent.setResult(CPSConstants.FAILED);
					permanent.setRemark(relationName + " LTC Not Applicable");
				}
			} else {
				// female
				if (CPSUtils.compareStrings(CPSConstants.FATHER, relationName) || CPSUtils.compareStrings(CPSConstants.MOTHER, relationName)) {
					permanent = resultMigrate(permanent, checkSalary(constraintsDTO.getEarningMoney(), CPSConstants.LTC));
					permanent = resultMigrate(permanent, checkFatherAndMother(CPSConstants.LTC, constraintsDTO.getSfid(), constraintsDTO.getRelationId(), "9,10"));
				} else if (CPSUtils.compareStrings(CPSConstants.FATHERINLAW, relationName) || CPSUtils.compareStrings(CPSConstants.MOTHERINLAW, relationName)) {
					permanent = resultMigrate(permanent, checkSalary(constraintsDTO.getEarningMoney(), CPSConstants.LTC));
					permanent = resultMigrate(permanent, checkFatherAndMother(CPSConstants.LTC, constraintsDTO.getSfid(), constraintsDTO.getRelationId(), "1,2"));
				} else if (CPSUtils.compareStrings(CPSConstants.SON, relationName)) {
					permanent = resultMigrate(permanent, checkSalary(constraintsDTO.getEarningMoney(), CPSConstants.LTC));
					permanent = resultMigrate(permanent, marriageStatus(maritalStatus));
					//String ltcSonAgeLimit = commonObjectDAO.getConfigurationValue(CPSConstants.LTC_SON_AGE_LIMIT);
					//permanent = resultMigrate(permanent, checkAge(constraintsDTO.getAge(), constraintsDTO.getDob(), CPSConstants.SON, ltcSonAgeLimit, constraintsDTO.getType()));
					permanent = resultMigrate(permanent, checkLtcSonAndDaughterConstrains(constraintsDTO.getAge(), constraintsDTO.getDob(), constraintsDTO.getSfid(), constraintsDTO.getId()));
				} else if (CPSUtils.compareStrings(CPSConstants.DAUGHTER, relationName)) {
					permanent = resultMigrate(permanent, checkLtcSonAndDaughterConstrains(constraintsDTO.getAge(), constraintsDTO.getDob(), constraintsDTO.getSfid(), constraintsDTO.getId()));
				} else if (CPSUtils.compareStrings(CPSConstants.BROTHER, relationName)) {
					String ltcMajorAgeLimit = commonObjectDAO.getConfigurationValue(CPSConstants.CGHS_MAJOR_AGE_LIMIT);
					permanent = resultMigrate(permanent, checkSalary(constraintsDTO.getEarningMoney(), CPSConstants.LTC));
					permanent = resultMigrate(permanent, checkAge(constraintsDTO.getAge(), constraintsDTO.getDob(), CPSConstants.BROTHER, ltcMajorAgeLimit, constraintsDTO.getType()));
					// IN ABOVE 22 IS THE MAJOR AGE LIMIT
				} else if (CPSUtils.compareStrings(CPSConstants.BROTHERINLAW, relationName)) {
					permanent.setResult(CPSConstants.FAILED);
					permanent.setRemark(permanent.getRemark() + "," + CPSConstants.LTC_BROTHERINLAW_REMARKS);
				} else if (CPSUtils.compareStrings(CPSConstants.SONINLAW, relationName)) {
					permanent.setResult(CPSConstants.FAILED);
					permanent.setRemark(permanent.getRemark() + "," + CPSConstants.LTC_SONINLAW_REMARKS);
				} else if (CPSUtils.compareStrings(CPSConstants.HUSBAND, relationName)) {
					// permanent = resultMigrate(permanent, checkSalary(constraintsDTO.getEarningMoney(),CPSConstants.LTC));
					/**
					 * For Spouse no need to check any constrains
					 */
					permanent.setResult(CPSConstants.SUCCESS);
				} else if (CPSUtils.compareStrings(CPSConstants.SISTER, relationName)) {
					permanent = resultMigrate(permanent, marriageStatus(maritalStatus));
				} else if (CPSUtils.compareStrings(CPSConstants.SELF, relationName)) {
					permanent.setResult(CPSConstants.SUCCESS);
				} else {
					permanent.setResult(CPSConstants.FAILED);
					permanent.setRemark(permanent.getRemark() + relationName + " LTC Not Applicable");
				}

			}

		} catch (Exception e) {
			throw e;
		} finally {

		}
		return permanent;
	}

	@SuppressWarnings("unchecked")
	public ResultDTO checkLtcSonAndDaughterConstrains(String age, String dob, String sfid, String dependentId) throws Exception {
		ResultDTO temp = new ResultDTO();
		Session session = null;
		StringBuffer sb = null;
		List<ConstraintsDTO> constraints = null;
		ArrayList<String> list = null;
		String previousDate = null;
		try {
			list = new ArrayList<String>();
			sb = new StringBuffer();
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String ltcPriorDob = commonObjectDAO.getConfigurationValue(CPSConstants.LTC_PRIOR_DOB);
			sb
					.append("select ltc_facility,id id,sfid sfid,name,to_char(dob,'dd-Mon-yyyy') dob,marital_status_id maritalId,ph_type_flag phFlag,earning_money earningMoney,sex gender from(select fd.ltc_facility,fd.id,fd.sfid,fd.name,fd.dob,fd.marital_status_id,fd.ph_type_flag,fd.earning_money,fd.sex "
							+ "from family_details fd,family_relation_master frm "
							+ "where fd.relation_id=frm.id and fd.sfid=? and "
							+ "frm.name in (upper('son'),upper('daughter')) and fd.status=1 and frm.status=1 "
							+ "and fd.dob<'"
							+ ltcPriorDob
							+ "' and ((fd.sex='M' and ((fd.ph_type_flag='N'))) or (fd.sex='F' and fd.marital_status_id=2)) "
							+ "union "
							+ "select fd.ltc_facility, fd.id,fd.sfid,fd.name,fd.dob,fd.marital_status_id,fd.ph_type_flag,fd.earning_money,fd.sex "
							+ "from family_details fd,family_relation_master frm "
							+ "where fd.relation_id=frm.id and fd.sfid=? and "
							+ "frm.name in (upper('son'),upper('daughter')) "
							+ "and dob in ( "
							+ "select dob from ( "
							+ "select fd.dob from family_details fd,family_relation_master frm "
							+ "where fd.relation_id=frm.id and fd.sfid=? and "
							+ "frm.name in (upper('son'),upper('daughter')) and fd.status=1 and frm.status=1 and fd.dob >'"
							+ ltcPriorDob
							+ "' "
							+ "order by fd.dob) where rownum<3) and fd.status=1 and frm.status=1 "
							+ "and ((fd.sex='M' and ((fd.ph_type_flag='N' and add_months(fd.dob,12*18)>sysdate)) or "
							+ "(fd.ph_type_flag='Y' and add_months(fd.dob,12*22)>sysdate)) or (fd.sex='F' and fd.marital_status_id=2))) order by dob");
			// IN ABOVE QUERY 18,22 ARE AGE LIMIT FOR SON AND PHYSICALLY HANDICAPED AND rownum<3 is only two surviving children
			constraints = session.createSQLQuery(sb.toString()).addScalar("sfid", Hibernate.STRING).addScalar("dob", Hibernate.STRING).addScalar("maritalId", Hibernate.STRING).addScalar("phFlag",
					Hibernate.STRING).addScalar("earningMoney", Hibernate.STRING).addScalar("gender", Hibernate.STRING).addScalar("id", Hibernate.STRING).setResultTransformer(
					Transformers.aliasToBean(ConstraintsDTO.class)).setString(0, sfid).setString(1, sfid).setString(2, sfid).list();
			Date ltcPriorDobConverted = CPSUtils.convertStringToDate(ltcPriorDob);
			if (CPSUtils.checkList(constraints)) {
				Iterator<ConstraintsDTO> it = constraints.iterator();
				int i = 1;
				while (it.hasNext()) {
					ConstraintsDTO constrainsDTO = it.next();
					Date dobConverted = CPSUtils.convertStringToDate(constrainsDTO.getDob());
					if ((ltcPriorDobConverted.compareTo(dobConverted) > 0) || ((ltcPriorDobConverted.compareTo(dobConverted) < 0) && i < 3) || constrainsDTO.getDob().equals(previousDate)) {
						list.add(String.valueOf(constrainsDTO.getId()));
						previousDate = constrainsDTO.getDob();
					}
					i++;
				}
				if (!list.contains(String.valueOf(dependentId))) {
					temp.setResult(CPSConstants.FAILED);
					temp.setRemark(CPSConstants.LTC_REMARK);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return temp;
	}

}
