package com.callippus.web.dao.qualification;

import java.util.ArrayList;
import java.util.List;

import com.callippus.web.beans.dto.DegreeDTO;
import com.callippus.web.beans.qualification.QualificationBean;

public interface IQualificationDAO {

	public ArrayList getEmpQualificationDetails(QualificationBean qualification) throws Exception;// String sfid

	public String manageQualification(QualificationBean qualificationBean) throws Exception;

	public String deleteQualification(String id) throws Exception;

	public List getQualification() throws Exception;

	public List getDescipline() throws Exception;

	public String updateQualification(QualificationBean qualificationBean) throws Exception;

	public List checkQualification(QualificationBean qualificationBean) throws Exception;

	public List<DegreeDTO> getDegreeList() throws Exception;

}
