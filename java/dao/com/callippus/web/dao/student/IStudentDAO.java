package com.callippus.web.dao.student;

import org.springframework.stereotype.Service;

import com.callippus.web.beans.student.StudentDTO;

@Service
public interface IStudentDAO 
{
	public String saveStudentDetails(StudentDTO studentDTO) throws Exception;
}
