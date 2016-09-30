package com.callippus.web.business.empExperience;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.empExperience.EmpExperienceBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.empExperience.IEmpExperienceDAO;

@Service
public class EmpExperienceBusiness {
	@Autowired
	private IEmpExperienceDAO empExpDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;

	public String saveExperienceDetails(EmpExperienceBean empExpBean) throws Exception {
		String message = "";
		try {
			if (CPSUtils.isNullOrEmpty(empExpBean.getId())) {
				empExpBean.setId(String.valueOf(commonDataDAO.getTableID(CPSConstants.EXPERIENCE_DETAILS, CPSConstants.UPDATE)));
				SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
				empExpBean.setCreationDate(df.format(new Date()));
				empExpBean.setLastModifiedDate(df.format(new Date()));
				empExpBean.setStatus(1);
				message = empExpDAO.createEmpExperience(empExpBean);
			} else {
				message = updateEmpExperience(empExpBean);
			}
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String updateEmpExperience(EmpExperienceBean empExpBean) throws Exception {
		String message = "";
		try {
			empExpBean.setCreationDate(commonDataDAO.getCreationDate(empExpBean.getId(), empExpBean.getSfid(), CPSConstants.EXPERIENCE_DETAILS));
			empExpBean.setLastModifiedDate(CPSUtils.getCurrentDate());
			// SimpleDateFormat df=new SimpleDateFormat("dd-MMM-yyyy");
			// empExpBean.setCreationDate(df.format(new Date()));
			// empExpBean.setLastModifiedDate(df.format(new Date()));
			empExpBean.setStatus(1);
			message = empExpDAO.updateEmpExperience(empExpBean);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String deleteEmpExperience(EmpExperienceBean empExpBean) throws Exception {
		String message = "";
		try {
			message = empExpDAO.deleteEmpExperience(empExpBean);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public List getEmpExperienceDetails(String sfid) throws Exception {
		List familylist = null;
		try {
			familylist = empExpDAO.getEmpExperienceDetails(sfid);
		} catch (Exception e) {
			throw e;
		}
		return familylist;
	}

	public EmpExperienceBean editExperience(EmpExperienceBean empExpBean) throws Exception {
		List experienceList = null;
		try {
			experienceList = empExpDAO.getExperience(empExpBean);
			if (CPSUtils.checkList(experienceList)) {
				empExpBean = (EmpExperienceBean) experienceList.get(0);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy");
				String[] date1 = empExpBean.getFromDate().split("\\.");
				Date date = df.parse(date1[0]);
				empExpBean.setFromDate(df1.format(date));
				date1 = empExpBean.getToDate().split("\\.");
				date = df.parse(date1[0]);
				empExpBean.setToDate(df1.format(date));
			}
		} catch (Exception e) {
			throw e;
		}
		return empExpBean;
	}
}