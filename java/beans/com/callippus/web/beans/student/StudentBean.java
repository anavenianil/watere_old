package com.callippus.web.beans.student;

public class StudentBean 
{
	private int studentId;
	private String studentName;
	private String qualification;
	private double aggregate;
	private String param;
	private String  message;
		
	public int getStudentId() 
	{
		return studentId;
	}
	public void setStudentId(int studentId) 
	{
		this.studentId = studentId;
	}
	
	public String getStudentName() 
	{
		return studentName;
	}
	public void setStudentName(String studentName) 
	{
		this.studentName = studentName;
	}
	
	public String getQualification() 
	{
		return qualification;
	}
	public void setQualification(String qualification) 
	{
		this.qualification = qualification;
	}
	
	public double getAggregate() 
	{
		return aggregate;
	}
	public void setAggregate(double aggregate) 
	{
		this.aggregate = aggregate;
	}
	
	public String getParam() 
	{
		return param;
	}
	public void setParam(String param) 
	{
		this.param = param;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
		
}
