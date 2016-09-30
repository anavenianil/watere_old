package com.callippus.web.business.qualification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.qualification.QualificationBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.qualification.IQualificationDAO;

@Service
public class QualificationBusiness {

	@Autowired
	private IQualificationDAO qualificationDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;

	public QualificationBean getEmpQualificationDetails(QualificationBean qualificationBean) throws Exception {
		try {
			qualificationBean.setEmpQualificationList(qualificationDAO.getEmpQualificationDetails(qualificationBean));
			qualificationBean.setQualificationList(qualificationDAO.getQualification());
			qualificationBean.setYearList(commonDataDAO.getYearList());
			qualificationBean.setDesciplineList(qualificationDAO.getDescipline());
			qualificationBean.setDegreeList(qualificationDAO.getDegreeList());
		} catch (Exception e) {
			throw e;
		}
		return qualificationBean;
	}

	public QualificationBean manageQualification(QualificationBean qualificationBean) throws Exception {
		String message = "";
		List list = null;
		try {

			if (CPSUtils.isNullOrEmpty(qualificationBean.getId())) {
				list = qualificationDAO.checkQualification(qualificationBean);
				if (!CPSUtils.checkList(list)) {
					int id = commonDataDAO.getTableID(CPSConstants.QUALIFICATION_DETAILS, CPSConstants.UPDATE);
					qualificationBean.setId(String.valueOf(id));
					qualificationBean.setStatus(1);
					qualificationBean.setCreationDate(CPSUtils.getCurrentDate());
					qualificationBean.setLastModifiedDate(CPSUtils.getCurrentDate());
					message = qualificationDAO.manageQualification(qualificationBean);
					qualificationBean.setMessage(message);
				} else {
					qualificationBean.setMessage(CPSConstants.DUPLICATE);
				}
			} else {
				message = qualificationDAO.updateQualification(qualificationBean);
				qualificationBean.setMessage(message);
			}
			qualificationBean.setEmpQualificationList(qualificationDAO.getEmpQualificationDetails(qualificationBean));
		} catch (Exception e) {
			throw e;
		}
		return qualificationBean;
	}

	public QualificationBean deleteQualification(QualificationBean qualificationBean) throws Exception {
		String message = null;
		try {
			message = qualificationDAO.deleteQualification(qualificationBean.getId());
			qualificationBean.setMessage(message);
			qualificationBean.setEmpQualificationList(qualificationDAO.getEmpQualificationDetails(qualificationBean));
		} catch (Exception e) {
			throw e;
		}
		return qualificationBean;
	}
}