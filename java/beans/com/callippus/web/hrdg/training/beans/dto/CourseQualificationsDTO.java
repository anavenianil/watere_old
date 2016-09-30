package com.callippus.web.hrdg.training.beans.dto;

import java.io.Serializable;

import com.callippus.web.beans.dto.CommonDTO;

public class CourseQualificationsDTO extends CommonDTO implements Serializable{
	private int courseId;
	
	private int qualificationId;
	private String qualification;
	private String course;
	
	
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public int getQualificationId() {
		return qualificationId;
	}
	public void setQualificationId(int qualificationId) {
		this.qualificationId = qualificationId;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	
	
	
	
	

}
