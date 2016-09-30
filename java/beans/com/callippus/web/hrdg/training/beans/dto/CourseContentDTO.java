package com.callippus.web.hrdg.training.beans.dto;

import java.io.Serializable;

import com.callippus.web.beans.dto.CommonDTO;

public class CourseContentDTO extends CommonDTO implements Serializable{
	private int courseId;
	private String courseContent;
	private String course;
	
	
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getCourseContent() {
		return courseContent;
	}
	public void setCourseContent(String courseContent) {
		this.courseContent = courseContent;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	
	
	
	
	
	

}
