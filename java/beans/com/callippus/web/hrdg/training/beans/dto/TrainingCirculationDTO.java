package com.callippus.web.hrdg.training.beans.dto;

import java.util.List;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.beans.file.FilesBean;

public class TrainingCirculationDTO extends CommonDTO{
	private String circulationDate;
	private Integer courseId;
	private Integer durationId;
	private Integer venueId;
	private String nominationStartDate;
	private String nominationEndDate;
	private Integer departmentId;
	private String organizer;
	private Integer brochure;
	private CourseDTO courseDto;
	private CourseDurationsDTO courseDurationDto;
	private TrainingVenueDTO trainingVenueDto;
	private TrainingInistituteDTO trainingInistituteDto;
	private List trainingCirculationDepts;
	private FilesBean fileBean;
	private Integer ionId;
	private String letterNumber;
	private List ionList;
	private String letterFormatId;
	
	
	
	public String getLetterFormatId() {
		return letterFormatId;
	}
	public void setLetterFormatId(String letterFormatId) {
		this.letterFormatId = letterFormatId;
	}
	public List getIonList() {
		return ionList;
	}
	public void setIonList(List ionList) {
		this.ionList = ionList;
	}
	public FilesBean getFileBean() {
		return fileBean;
	}
	public void setFileBean(FilesBean fileBean) {
		this.fileBean = fileBean;
	}
	public List getTrainingCirculationDepts() {
		return trainingCirculationDepts;
	}
	public void setTrainingCirculationDepts(List trainingCirculationDepts) {
		this.trainingCirculationDepts = trainingCirculationDepts;
	}
	public TrainingInistituteDTO getTrainingInistituteDto() {
		return trainingInistituteDto;
	}
	public void setTrainingInistituteDto(TrainingInistituteDTO trainingInistituteDto) {
		this.trainingInistituteDto = trainingInistituteDto;
	}
	public String getCirculationDate() {
		return circulationDate;
	}
	public void setCirculationDate(String circulationDate) {
		this.circulationDate = circulationDate;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	public Integer getDurationId() {
		return durationId;
	}
	public void setDurationId(Integer durationId) {
		this.durationId = durationId;
	}
	public Integer getVenueId() {
		return venueId;
	}
	public void setVenueId(Integer venueId) {
		this.venueId = venueId;
	}
	public String getNominationStartDate() {
		return nominationStartDate;
	}
	public void setNominationStartDate(String nominationStartDate) {
		this.nominationStartDate = nominationStartDate;
	}
	public String getNominationEndDate() {
		return nominationEndDate;
	}
	public void setNominationEndDate(String nominationEndDate) {
		this.nominationEndDate = nominationEndDate;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public String getOrganizer() {
		return organizer;
	}
	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}
	public Integer getBrochure() {
		return brochure;
	}
	public void setBrochure(Integer brochure) {
		this.brochure = brochure;
	}
	public CourseDTO getCourseDto() {
		return courseDto;
	}
	public void setCourseDto(CourseDTO courseDto) {
		this.courseDto = courseDto;
	}
	public CourseDurationsDTO getCourseDurationDto() {
		return courseDurationDto;
	}
	public void setCourseDurationDto(CourseDurationsDTO courseDurationDto) {
		this.courseDurationDto = courseDurationDto;
	}
	public TrainingVenueDTO getTrainingVenueDto() {
		return trainingVenueDto;
	}
	public void setTrainingVenueDto(TrainingVenueDTO trainingVenueDto) {
		this.trainingVenueDto = trainingVenueDto;
	}
	public Integer getIonId() {
		return ionId;
	}
	public void setIonId(Integer ionId) {
		this.ionId = ionId;
	}
	public String getLetterNumber() {
		return letterNumber;
	}
	public void setLetterNumber(String letterNumber) {
		this.letterNumber = letterNumber;
	}
	
		
}