package com.callippus.web.hrdg.training.beans.dto;

import java.util.List;

import com.callippus.web.beans.dto.CommonDTO;

public class CourseDurationsDTO extends CommonDTO{
	private String startDate;
	private String endDate;
	private int courseId;
	private String course;
	private List trainingCirculationDto;
	
	public List getTrainingCirculationDto() {
		return trainingCirculationDto;
	}
	public void setTrainingCirculationDto(List trainingCirculationDto) {
		this.trainingCirculationDto = trainingCirculationDto;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	
	
	

}
