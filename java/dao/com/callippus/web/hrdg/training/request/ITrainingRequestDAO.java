package com.callippus.web.hrdg.training.request;

import java.util.List;

import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.hrdg.training.beans.dto.CourseAttendedDetailsDTO;
import com.callippus.web.hrdg.training.request.beans.dto.TrainingNominationDTO;
import com.callippus.web.hrdg.training.request.beans.dto.TrainingRequestBean;

public interface ITrainingRequestDAO {
	public List getCourseListBasedCirculation(TrainingRequestBean trainingReqBean) throws Exception;

	public List checkTrainingNomination(TrainingRequestBean trainingReqBean) throws Exception;
	
	public EmployeeBean getEmpDetails(String sfid) throws Exception;
	
	public TrainingNominationDTO getNominationDetails(int requestId) throws Exception;
	
	public List getTrainingNominationReqList(TrainingRequestBean trainingReqBean) throws Exception;

	public List getNominationsCourseList(TrainingRequestBean trainingReqBean) throws Exception;
	
	public List getMDBList() throws Exception;

	public String updateNominationStatus(String string, String boardId,TrainingRequestBean trainingReqBean) throws Exception;

	public List getTrainingNominationBoardSelectionList(TrainingRequestBean trainingReqBean) throws Exception;

	public String updateBoardSelectionStatus(String id, TrainingRequestBean trainingReqBean) throws Exception;

	public List getTrainingNominationADSelectionList(TrainingRequestBean trainingReqBean) throws Exception;
	
	public String updateADSelectionStatus(String id, TrainingRequestBean trainingReqBean) throws Exception;

	public List getTrainingNominationFinalSelectionList(TrainingRequestBean trainingReqBean)throws Exception;
	
	public String updateFinalSelectionStatus(String string, TrainingRequestBean trainingReqBean) throws Exception;

	public List getNominationsForCFACourseList(TrainingRequestBean trainingReqBean) throws Exception;

	public List getTrainingNominationsCFASelectionList(TrainingRequestBean trainingReqBean) throws Exception;

	public String updateTrainingNominationsCFAStatus(String string, TrainingRequestBean trainingReqBean) throws Exception;

	public List viewTrainingNominationDetails(TrainingRequestBean trainingReqBean)throws Exception;
	
	public List getBrochureAndION(TrainingRequestBean trainingReqBean) throws Exception;
	
	public List getDeptAndION(TrainingRequestBean trainingReqBean) throws Exception;

	public String updateRequest(TrainingRequestBean trainingReqBean,int status) throws Exception;

	public List viewTrainingNomination(TrainingRequestBean trainingReqBean)throws Exception;

	public TrainingNominationDTO getNominationCancelDetails(int requestId)throws Exception;

	public String updateCancelRequest(TrainingRequestBean trainingReqBean, int status) throws Exception;

	public List getRefRequestId(TrainingRequestBean trainingReqBean) throws Exception;

	public String cancelRequest(TrainingRequestBean trainingReqBean, String i) throws Exception;

	public String cancelNewRequest(TrainingRequestBean trainingReqBean, String i)throws Exception;

	public List getNominationReqStatus(TrainingRequestBean trainingReqBean)throws Exception;

	public List getReqIPAddress(String requestId,String requestType)throws Exception;

	public List getNominationLastAttededCourse(TrainingRequestBean trainingReqBean)throws Exception;

	public CourseAttendedDetailsDTO getCourseDetails(TrainingRequestBean trainingReqBean)throws Exception;

	public String checkAttendedCourseDetails(CourseAttendedDetailsDTO dto)throws Exception;

	public void updateAttendedCourseDetails(CourseAttendedDetailsDTO dto, TrainingRequestBean trainingReqBean)throws Exception;

	public String getNomineeSfid(String string) throws Exception;

	public String updateCancelStatusOfRequest(TrainingRequestBean trainingReqBean)throws Exception;

	public List getSeriesMstList()throws Exception;

	public List getIONMstList(TrainingRequestBean trainingReqBean)throws Exception;

	public List checkCourseFee(TrainingRequestBean trainingReqBean)throws Exception;

	public List getTrainingTypeList()throws Exception;

	public List getFinancialYear()throws Exception;


	
	
	

}
