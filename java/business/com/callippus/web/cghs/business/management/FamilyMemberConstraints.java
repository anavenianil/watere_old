package com.callippus.web.cghs.business.management;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.FamilyDependentConstraintsDTO;
import com.callippus.web.beans.dto.ResultDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.dao.common.IComonObjectDAO;

@Service
public class FamilyMemberConstraints {
	private static Log log = LogFactory.getLog(FamilyMemberConstraints.class);
	@Autowired
	private IComonObjectDAO commonDataDAO;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	/**
	 * THIS METHOD RETURN GOVT SERVENT GENDER DETAILS
	 * 
	 * @param Sfid
	 * @return
	 * @throws Exception
	 */
	public String getEmployeeGender(String Sfid) throws Exception {
		Session session = null;
		String sql = null;
		String gender = null;
		try {
			log.debug("FamilyMemberConstraints >>>>>>>>>>>>>>>>>>>>>>getEmployeeGender(SFID)::" + Sfid);
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			sql = "select gender from emp_master where sfid='" + Sfid + "' and status=1";
			gender = session.createSQLQuery(sql).uniqueResult().toString();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return gender;
	}

	/**
	 * THIS METHOD RETURN EMPLOYEE FAMILY MEMBER MARITAL STATUS
	 * 
	 * @param maritalId
	 * @return
	 * @throws Exception
	 */
	public String getMaritalStatus(String maritalId) throws Exception {
		Session session = null;
		String sql = null;
		String maritalStatus = null;
		try {
			log.debug("FamilyMemberConstraints >>>>>>>>>>>>>>>>>>>>>>getMaritalStatus(maritalId)::" + maritalId);
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			sql = "select name from marital_master where status=1 and id=?";
			maritalStatus = session.createSQLQuery(sql).setString(0, maritalId).uniqueResult().toString();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return maritalStatus;
	}

	/**
	 * THIS MEHTOD RETURN EMPLOYEE FAMILY MEMBER RELATIONSHIP
	 * 
	 * @param relationId
	 * @return
	 * @throws Exception
	 */
	public String getRelationship(String relationId) throws Exception {
		Session session = null;
		String sql = null;
		String relationName = null;
		try {
			log.debug("FamilyMemberConstraints >>>>>>>>>>>>>>>>>>>>>>getRelationship(relationId)::" + relationId);
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			sql = "select name from family_relation_master where status=1 and id=?";
			relationName = session.createSQLQuery(sql).addScalar(CPSConstants.NAME, Hibernate.STRING).setString(0, relationId).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return relationName;
	}

	public ResultDTO resultMigrate(ResultDTO permanent, ResultDTO temp) throws Exception {
		try {
			/*commented by bkr 01/06/2016*/
			/*if (CPSUtils.compareStrings(temp.getResult(), CPSConstants.FAILED)) {
				permanent.setResult(CPSConstants.FAILED);
				if (!CPSUtils.isNull(permanent.getRemark())) {
					permanent.setRemark(permanent.getRemark() + "," + temp.getRemark());
				} else {
					permanent.setRemark(temp.getRemark());
				}
			}*/
		} catch (Exception e) {
			throw e;
		}
		return permanent;
	}

	public ResultDTO checkSalary(String salary, String type) throws Exception {
		ResultDTO temp = new ResultDTO();
		try {
			log.debug("FamilyMemberConstraints >>>>>>>>>>>>>>>>>>>>>>checkSalary(salary)::" + salary);
			// get the salary from the configuration & check the salary
			String confSalary = commonDataDAO.getConfigurationValue(CPSConstants.CGHS_SALARY_LIMIT);
			if (!CPSUtils.isNullOrEmpty(salary) && Float.valueOf(salary) >= Integer.valueOf(confSalary)) {
				temp.setResult(CPSConstants.FAILED);
				temp.setRemark(CPSConstants.SALARY_LIMIT_REMARK);
				temp.setRemark(temp.getRemark().replace("%2%", type.toUpperCase()));
				temp.setRemark(temp.getRemark().replace("%1%", confSalary));
			}
		} catch (Exception e) {
			throw e;
		}
		return temp;
	}

	public ResultDTO marriageStatus(String marriageStatus) throws Exception {
		ResultDTO temp = new ResultDTO();

		try {
			log.debug("FamilyMemberConstraints >>>>>>>>>>>>>>>>>>>>>>marriageStatus(marriageStatus)::" + marriageStatus);
			if (CPSUtils.compareStrings(marriageStatus, CPSConstants.MARRIED)) {
				// means she may be unmarried / divorced / abandoned or separated from their husband
				temp.setResult(CPSConstants.FAILED);
				temp.setRemark(CPSConstants.MARITAL_REMARK);
			}
		} catch (Exception e) {
			throw e;
		}
		return temp;
	}

	public ResultDTO disability(String phFlag) throws Exception {
		ResultDTO temp = new ResultDTO();
		try {
			log.debug("FamilyMemberConstraints >>>>>>>>>>>>>>>>>>>>>>disability(phFlag)::" + phFlag);
			if (CPSUtils.compareStrings(phFlag, CPSConstants.Y)) {
				temp.setResult(CPSConstants.FAILED);
				temp.setRemark(CPSConstants.DISABILITY_REMARK);
			}
		} catch (Exception e) {
			throw e;
		}
		return temp;
	}

	public ResultDTO checkAge(String age, String dob, String relation, String ageLimit, String type) throws Exception {
		ResultDTO temp = new ResultDTO();
		try {
			log.debug("FamilyMemberConstraints >>>>>>>>>>>>>>>>>>>>>>checkAge(age,dob,relation,String ageLimit,String type)::" + age + "," + dob + "," + relation + "," + type);
			if (CPSUtils.compareStrings(relation, CPSConstants.SON)) {
				if (!CPSUtils.isNullOrEmpty(age)) {
					if (CPSUtils.isGreaterOrNot(age, Integer.parseInt(ageLimit))) {
						temp.setResult(CPSConstants.FAILED);
						if (CPSUtils.compareStrings(type, CPSConstants.CGHS)) {
							temp.setRemark(CPSConstants.CGHS_SON_AGE_LIMIT_REMARK);
						} else {
							temp.setRemark(CPSConstants.LTC_SON_AGE_LIMIT_REMARK);
						}
						temp.setRemark(temp.getRemark().replace("%1%", ageLimit));
					}
				} else {
					if (CPSUtils.isGreaterOrNot(String.valueOf(CPSUtils.yearsDifference(dob)), Integer.parseInt(ageLimit))) {
						temp.setResult(CPSConstants.FAILED);
						if (CPSUtils.compareStrings(type, CPSConstants.CGHS)) {
							temp.setRemark(CPSConstants.CGHS_SON_AGE_LIMIT_REMARK);
						} else {
							temp.setRemark(CPSConstants.LTC_SON_AGE_LIMIT_REMARK);
						}
						temp.setRemark(temp.getRemark().replace("%1%", ageLimit));
					}
				}
			} else if (CPSUtils.compareStrings(relation, CPSConstants.SONINLAW) || CPSUtils.compareStrings(relation, CPSConstants.BROTHER)
					|| CPSUtils.compareStrings(relation, CPSConstants.BROTHERINLAW)) {
				if (!CPSUtils.isNullOrEmpty(age)) {
					if (CPSUtils.isGreaterOrNot(age, Integer.parseInt(ageLimit))) {
						temp.setResult(CPSConstants.FAILED);
						if (CPSUtils.compareStrings(type, CPSConstants.CGHS)) {
							temp.setRemark(CPSConstants.CGHS_MAJOR_AGE_LIMIT_REMARK);
						} else {
							temp.setRemark(CPSConstants.LTC_MAJOR_AGE_LIMIT_REMARK);
						}
						temp.setRemark(temp.getRemark().replace("%1%", ageLimit));
					}
				} else {
					if (CPSUtils.isGreaterOrNot(String.valueOf(CPSUtils.yearsDifference(dob)), Integer.parseInt(ageLimit))) {
						temp.setResult(CPSConstants.FAILED);
						if (CPSUtils.compareStrings(type, CPSConstants.CGHS)) {
							temp.setRemark(CPSConstants.CGHS_MAJOR_AGE_LIMIT_REMARK);
						} else {
							temp.setRemark(CPSConstants.LTC_MAJOR_AGE_LIMIT_REMARK);
						}
						temp.setRemark(temp.getRemark().replace("%1%", ageLimit));
					}
				}
			}

		} catch (Exception e) {
			throw e;
		}
		return temp;
	}

	public ResultDTO checkFatherAndMother(String type, String sfid, String relationId, String nonRelationIds) throws Exception {
		ResultDTO temp = new ResultDTO();
		Session session = null;
		StringBuffer sql = null;
		List list = null;
		Transaction tx = null;
		try {
			sql = new StringBuffer();
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			sql.append("select ");
			if (CPSUtils.compareStrings(type, CPSConstants.LTC)) {
				sql.append("ltc_facility from family_details where sfid=? and relation_id=? and status=1 ");
			} else {
				sql.append("cghs_facility from family_details where sfid=? and relation_id=? and status=1 ");
			}
			String result =(String) session.createSQLQuery(sql.toString()).setString(0, sfid).setString(1, relationId).uniqueResult();
			if (CPSUtils.compareStrings(result, CPSConstants.N)) {
				sql.delete(0, sql.length());
				sql.append("select count(*) from family_details where sfid=? and relation_id in(" + nonRelationIds + ")");
				if (CPSUtils.compareStrings(type, CPSConstants.LTC)) {
					sql.append(" and ltc_facility='Y'");
				}
				if (CPSUtils.compareStrings(type, CPSConstants.CGHS)) {
					sql.append(" and cghs_facility='Y'");
				}
				list = session.createSQLQuery(sql.toString()).setString(0, sfid).list();
				if (!CPSUtils.isNull(list.get(0)) && Integer.parseInt(list.get(0).toString()) > 0) {
					temp.setResult(CPSConstants.FAILED);
					temp.setRemark("U can give this " + type.toUpperCase() + " facility eighter Father and Mother or Father-In-Law and Mother-In-Law");
				} else {
					sql.delete(0, sql.length());
					sql.append("select count(*) from family_dependent_constraints where sfid=? and family_dependent_id=?");
					if (CPSUtils.compareStrings(type, CPSConstants.LTC)) {
						sql.append(" and const_type='" + CPSConstants.LTC + "'");
					}
					if (CPSUtils.compareStrings(type, CPSConstants.CGHS)) {
						sql.append(" and const_type='" + CPSConstants.CGHS + "'");
					}
					list = session.createSQLQuery(sql.toString()).setString(0, sfid).setString(1, relationId).list();
					if (Integer.parseInt(list.get(0).toString()) > 0) {
						temp.setResult(CPSConstants.FAILED);
						temp.setRemark("Previously " + type.toUpperCase() + " facility is assigned for this dependent u cant change multiple no of times");
					} else {
						FamilyDependentConstraintsDTO dependentDTO = new FamilyDependentConstraintsDTO();
						dependentDTO.setSfid(sfid);
						dependentDTO.setType(type);
						dependentDTO.setFamilyDependentId(Integer.parseInt(relationId));
						dependentDTO.setStatus(1);
						session.saveOrUpdate(dependentDTO);
						session.flush();//tx.commit() ;
					}
				}

			} else {
				// already ltc or cghs facility is available no need to check constraints
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return temp;
	}
}
