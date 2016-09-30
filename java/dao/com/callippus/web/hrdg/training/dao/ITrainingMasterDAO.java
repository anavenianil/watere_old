package com.callippus.web.hrdg.training.dao;

import java.util.List;

import com.callippus.web.beans.dto.DisciplineDTO;
import com.callippus.web.beans.dto.QualificationDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.letterNumberFormat.IONDTO;
import com.callippus.web.hrdg.training.beans.TrainingMasterBean;

public interface ITrainingMasterDAO {
	
	public List checkMasterData(TrainingMasterBean trainingMstBean) throws Exception;
	
	
	public String update(TrainingMasterBean trainingMstBean) throws Exception;
	
	public List<Object> getMasterData(String beanName) throws Exception ;

	public String deleteTrainingCheckMaster(String beanName, String parameter, String status, String id) throws Exception ;

	public String deleteTrainingMasterData(TrainingMasterBean trainingMstBean) throws Exception ;
	
	public List getCourseList(TrainingMasterBean trainingMstBean) throws Exception;
	
	public List checkCourse(TrainingMasterBean trainingMstBean) throws Exception;
	
	public String updateCourse(TrainingMasterBean trainingMstBean) throws Exception;
	
	public String deleteCourse(TrainingMasterBean trainingMstBean,String beanName) throws Exception;
	
	public List getCourseDurationList(TrainingMasterBean trainingMstBean) throws Exception;
	
	public List checkCourseDuration(TrainingMasterBean trainingMstBean) throws Exception;
	
	public String updateCourseDuration(TrainingMasterBean trainingMstBean) throws Exception;
	
	public List checkHRDGBoard(TrainingMasterBean trainingMstBean) throws Exception;
	
	public String updateHRDGBoard(TrainingMasterBean trainingMstBean) throws Exception;
	
	public List getHRDGBoardList(TrainingMasterBean trainingMstBean) throws Exception;
	
	public List checkHRDGBoardMember(TrainingMasterBean trainingMstBean) throws Exception;
	
	public String updateHRDGBoardMember(TrainingMasterBean trainingMstBean) throws Exception;
	
	public List getHRDGBoardMemberList(TrainingMasterBean trainingMstBean) throws Exception;
	
	public List getCourseListBasedonDuration(TrainingMasterBean trainingMstBean) throws Exception;
	
	public List getTrainingCirculationList(TrainingMasterBean trainingMstBean) throws Exception;
	
	public String deleteTrainingCirculation(TrainingMasterBean trainingMstBean,String beanName) throws Exception;
	
	public String updateTrainingCirculation(TrainingMasterBean trainingMstBean) throws Exception;
	
	public List getDeptHeadSfidList(String[] departmentIds,int cirId) throws Exception;
	
	public List getTrainingCirculationDetails(String id,String param) throws Exception;


	public List getTrainingRegionDataList(TrainingMasterBean trainingMstBean) throws Exception;


	public List checkTrainingRegion(TrainingMasterBean trainingMstBean) throws Exception;


	public List getTrainingInistituteDataList(TrainingMasterBean trainingMstBean) throws Exception;


	public List getTrainingVenueDataList(TrainingMasterBean trainingMstBean) throws Exception;


	public List getCourseDisciplineList(TrainingMasterBean trainingMstBean) throws Exception;	
	public List<DisciplineDTO> getSelectedDisciplineList(TrainingMasterBean trainingMstBean) throws Exception;


	public List checkCourseDiscipline(TrainingMasterBean trainingMstBean) throws Exception;


	public List checkCourseQualification(TrainingMasterBean trainingMstBean) throws Exception;


	public List getCourseQualificationList(TrainingMasterBean trainingMstBean) throws Exception;


	public List<QualificationDTO> getSelectedQualificationList(TrainingMasterBean trainingMstBean) throws Exception;


	public String updateTrainingRegion(TrainingMasterBean trainingMstBean) throws Exception;


	public List getDesignationList(TrainingMasterBean trainingMstBean) throws Exception;


	public List getCourseDesignationList(TrainingMasterBean trainingMstBean) throws Exception;


	public List getSelectedDesignationList(TrainingMasterBean trainingMstBean) throws Exception;


	public List checkCourseDesignation(TrainingMasterBean trainingMstBean) throws Exception;


	public List getCourseContent(TrainingMasterBean trainingMstBean) throws Exception;


	public List checkCourseContent(TrainingMasterBean trainingMstBean) throws Exception;


	public String updateCourseContent(TrainingMasterBean trainingMstBean) throws Exception;


	public List getCirculationDetails(TrainingMasterBean trainingMstBean) throws Exception;


	public List getTrainingVenueDetails(TrainingMasterBean trainingMstBean) throws Exception;


	public List getDepartmentList() throws Exception;


	public List getAttendedCourseList(TrainingMasterBean trainingMstBean)throws Exception;


	public String checkAttendedCoursedetails(TrainingMasterBean trainingMstBean)throws Exception;


	public String updateCourseAttendeddetails(TrainingMasterBean trainingMstBean)throws Exception;


	public String deleteCourseAttended(TrainingMasterBean trainingMstBean)throws Exception;


	public EmployeeBean getEmpDetails(String sfid)throws Exception;


	public List getIONMstList(TrainingMasterBean trainingMstBean)throws Exception;
	
	public List getCirculationDetailsForION(TrainingMasterBean trainingMstBean) throws Exception;


	public List getSeriesMstList() throws Exception;






	
	
	
	

}
