package com.callippus.web.hrdg.training.beans.dto;

import java.io.Serializable;

import com.callippus.web.beans.dto.CommonDTO;

public class CourseDisciplinesDTO extends CommonDTO implements Serializable{
	private int courseId;
	private int disciplineId;
	private String discipline;
	private String course;
	
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public int getDisciplineId() {
		return disciplineId;
	}
	public void setDisciplineId(int disciplineId) {
		this.disciplineId = disciplineId;
	}
	public String getDiscipline() {
		return discipline;
	}
	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	
	
	
	
	
	

}
