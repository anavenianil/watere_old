package com.callippus.web.business.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.callippus.web.beans.student.StudentBean;
import com.callippus.web.beans.student.StudentDTO;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.student.IStudentDAO;

@Service
public class StudentBusiness 
{
	
	@Autowired(required = true)
	private IStudentDAO iStudentDAO;

	private String message = "";
	public String submitStudentDetails(StudentBean studentBean) throws Exception
	{
		try
		{
			StudentDTO studentDTO = new StudentDTO();
			studentDTO.setStudentId(studentBean.getStudentId());
			studentDTO.setStudentName(studentBean.getStudentName());
			studentDTO.setQualification(studentBean.getQualification());
			studentDTO.setAggregate(studentBean.getAggregate());
			studentDTO.setStatus(1);
			studentDTO.setCreated(CPSUtils.getCurrentDateWithTime());
			message = iStudentDAO.saveStudentDetails(studentDTO);
			
		}catch (Exception e) { throw e;}
		finally{}
		return message;
	}
	
}
