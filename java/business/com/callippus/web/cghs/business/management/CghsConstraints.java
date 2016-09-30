package com.callippus.web.cghs.business.management;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.ConstraintsDTO;
import com.callippus.web.beans.dto.ResultDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;

@Service
public class CghsConstraints extends FamilyMemberConstraints{
	private static Log log = LogFactory.getLog(CghsConstraints.class);
	@Autowired
	private IComonObjectDAO commonObjectDAO;

	public ResultDTO checkCghsConstraints(ConstraintsDTO constraintsDTO,ResultDTO permanent) throws Exception {
		log.debug("CghsConstraints>>>>>>>>>>>>>>>>>>>>>>>>>> checkCghsConstraints()");
		String maritalStatus = null;
		String empGender = null;
		String relationName = null;
		try {
			permanent.setResult(CPSConstants.SUCCESS);

			// get the govt servant gender
			empGender = getEmployeeGender(constraintsDTO.getSfid());
			// get family member/dependent marital status name
			maritalStatus = getMaritalStatus(constraintsDTO.getMaritalId());
			// get family member/dependent relationship
			relationName = getRelationship(constraintsDTO.getRelationId());
			
			// check the employee gender
			if (CPSUtils.compareStrings(CPSConstants.M, empGender)) {
				// male
				  if (CPSUtils.compareStrings(CPSConstants.WIFE, relationName)) {
						//permanent = resultMigrate(permanent, checkSalary(constraintsDTO.getEarningMoney(),CPSConstants.CGHS));
						/**
						 * For Spouse no need to check any constrains
						 */
						permanent.setResult(CPSConstants.SUCCESS);
				}else if (CPSUtils.compareStrings(CPSConstants.FATHER, relationName) || CPSUtils.compareStrings(CPSConstants.MOTHER, relationName)) {
					permanent = resultMigrate(permanent, checkSalary(constraintsDTO.getEarningMoney(),CPSConstants.CGHS));
					permanent = resultMigrate(permanent, checkFatherAndMother(CPSConstants.CGHS,constraintsDTO.getSfid(),constraintsDTO.getRelationId(),"9,10"));
					/**
					 * In above 9,10 are MOTHERINLAW,FATHERINLAW ids in family_relation_master table
					 */
				} else if (CPSUtils.compareStrings(CPSConstants.SON, relationName)) {
					permanent = resultMigrate(permanent, checkSalary(constraintsDTO.getEarningMoney(),CPSConstants.CGHS));
					String cghsSonAgeLimit = commonObjectDAO.getConfigurationValue(CPSConstants.CGHS_SON_AGE_LIMIT);
					permanent = resultMigrate(permanent, checkAge(constraintsDTO.getAge(), constraintsDTO.getDob(), CPSConstants.SON,cghsSonAgeLimit,constraintsDTO.getType()));
				} else if (CPSUtils.compareStrings(CPSConstants.BROTHER, relationName)) {
					permanent = resultMigrate(permanent, checkSalary(constraintsDTO.getEarningMoney(),CPSConstants.CGHS));
					String cghsMajorAgeLimit = commonObjectDAO.getConfigurationValue(CPSConstants.CGHS_MAJOR_AGE_LIMIT);
					resultMigrate(permanent,checkAge(constraintsDTO.getAge(), constraintsDTO.getDob(), CPSConstants.BROTHER,cghsMajorAgeLimit,constraintsDTO.getType()));
				} else if (CPSUtils.compareStrings(CPSConstants.MOTHERINLAW, relationName) || CPSUtils.compareStrings(CPSConstants.FATHERINLAW, relationName)) {
					permanent = resultMigrate(permanent, checkSalary(constraintsDTO.getEarningMoney(),CPSConstants.CGHS));
					permanent = resultMigrate(permanent, checkFatherAndMother(CPSConstants.CGHS,constraintsDTO.getSfid(),constraintsDTO.getRelationId(),"1,2"));
					/**
					 * In above 1,2 are MOTHER,FATHER ids in family_relation_master table
					 */
				} else if (CPSUtils.compareStrings(CPSConstants.DAUGHTER, relationName)) {
					permanent = resultMigrate(permanent, checkSalary(constraintsDTO.getEarningMoney(),CPSConstants.CGHS));
					permanent = resultMigrate(permanent, marriageStatus(maritalStatus));
				} else if (CPSUtils.compareStrings(CPSConstants.SISTER, relationName)) {
					permanent = resultMigrate(permanent, checkSalary(constraintsDTO.getEarningMoney(),CPSConstants.CGHS));
					permanent = resultMigrate(permanent, marriageStatus(maritalStatus));
				}else if (CPSUtils.compareStrings(CPSConstants.SELF, relationName)) {
					permanent.setResult(CPSConstants.SUCCESS);
				}else {
					permanent.setResult(CPSConstants.FAILED);
					permanent.setRemark(relationName+" CGHS Not Applicable");
				}
			} else {
				// female
				if (CPSUtils.compareStrings(CPSConstants.HUSBAND, relationName)) {
					//permanent = resultMigrate(permanent, checkSalary(constraintsDTO.getEarningMoney(),CPSConstants.CGHS));
					/**
					 * For Spouse no need to check any constrains
					 */
					permanent.setResult(CPSConstants.SUCCESS);
				}else if (CPSUtils.compareStrings(CPSConstants.FATHER, relationName) || CPSUtils.compareStrings(CPSConstants.MOTHER, relationName)) {
					permanent = resultMigrate(permanent, checkSalary(constraintsDTO.getEarningMoney(),CPSConstants.CGHS));
					permanent = resultMigrate(permanent, checkFatherAndMother(CPSConstants.CGHS,constraintsDTO.getSfid(),constraintsDTO.getRelationId(),"9,10"));
					/**
					 * In above 9,10 are MOTHERINLAW,FATHERINLAW ids in family_relation_master table
					 */
				} else if (CPSUtils.compareStrings(CPSConstants.FATHERINLAW, relationName) || CPSUtils.compareStrings(CPSConstants.MOTHERINLAW, relationName)) {
					permanent = resultMigrate(permanent, checkSalary(constraintsDTO.getEarningMoney(),CPSConstants.CGHS));
					permanent = resultMigrate(permanent, checkFatherAndMother(CPSConstants.CGHS,constraintsDTO.getSfid(),constraintsDTO.getRelationId(),"1,2"));
					/**
					 * In above 1,2 are MOTHER,FATHER ids in family_relation_master table
					 */
				} else if (CPSUtils.compareStrings(CPSConstants.SON, relationName)) {
					permanent = resultMigrate(permanent, checkSalary(constraintsDTO.getEarningMoney(),CPSConstants.CGHS));
					String cghsSonAgeLimit = commonObjectDAO.getConfigurationValue(CPSConstants.CGHS_SON_AGE_LIMIT);
					permanent = resultMigrate(permanent, checkAge(constraintsDTO.getAge(), constraintsDTO.getDob(), CPSConstants.SON,cghsSonAgeLimit,constraintsDTO.getType()));
				} else if (CPSUtils.compareStrings(CPSConstants.DAUGHTER, relationName)||CPSUtils.compareStrings(CPSConstants.DAUGHTERINLAW, relationName)) {
					permanent = resultMigrate(permanent, checkSalary(constraintsDTO.getEarningMoney(),CPSConstants.CGHS));
					permanent = resultMigrate(permanent, marriageStatus(maritalStatus));
					permanent = resultMigrate(permanent, disability(constraintsDTO.getPhFlag()));
				} else if (CPSUtils.compareStrings(CPSConstants.BROTHER, relationName)||CPSUtils.compareStrings(CPSConstants.BROTHERINLAW, relationName)) {
					permanent = resultMigrate(permanent, checkSalary(constraintsDTO.getEarningMoney(),CPSConstants.CGHS));
					String cghsMajorAgeLimit = commonObjectDAO.getConfigurationValue(CPSConstants.CGHS_MAJOR_AGE_LIMIT);
					resultMigrate(permanent,checkAge(constraintsDTO.getAge(), constraintsDTO.getDob(), CPSConstants.BROTHER,cghsMajorAgeLimit,constraintsDTO.getType()));
				} else if (CPSUtils.compareStrings(CPSConstants.SISTER, relationName)||CPSUtils.compareStrings(CPSConstants.SISTERINLAW, relationName)) {
					permanent = resultMigrate(permanent, checkSalary(constraintsDTO.getEarningMoney(),CPSConstants.CGHS));
					permanent = resultMigrate(permanent, marriageStatus(maritalStatus));
				} else if (CPSUtils.compareStrings(CPSConstants.SELF, relationName)) {
					permanent.setResult(CPSConstants.SUCCESS);
				} else {
					permanent.setResult(CPSConstants.FAILED);
					permanent.setRemark(relationName+" CGHS Not Applicable");
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {

		}
		return permanent;
	}
}
