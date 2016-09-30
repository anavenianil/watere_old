package com.callippus.web.dao.student;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.student.StudentBean;
import com.callippus.web.beans.student.StudentDTO;
import com.callippus.web.controller.common.CPSConstants;

import antlr.collections.List;

@Service
public class SQLStudentDAO  implements IStudentDAO
{
	@Autowired(required = true)
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	
 	public String saveStudentDetails(StudentDTO studentDTO) throws Exception
	{
		Session session = null;
		String message = "";
		try
		{
			session = hibernateUtils.getSession();
			session.save(studentDTO);
			session.flush();
			message = CPSConstants.SUCCESS;
		}catch (Exception e)
		{
			throw e;
		}finally{}
		return message;
	}
	
}
