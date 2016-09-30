package com.callippus.web.dao.empExperience;

import java.util.ArrayList;
import java.util.List;

import com.callippus.web.beans.empExperience.EmpExperienceBean;

public interface IEmpExperienceDAO {

	public String createEmpExperience(EmpExperienceBean empExpBean) throws Exception;

	public String deleteEmpExperience(EmpExperienceBean empExpBean) throws Exception;

	public String updateEmpExperience(EmpExperienceBean empExpBean) throws Exception;

	public ArrayList getEmpExperienceDetails(String sfid) throws Exception;

	public List getExperience(EmpExperienceBean empExpBean) throws Exception;

}
