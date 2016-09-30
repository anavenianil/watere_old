package com.callippus.web.hrdg.training.controller.master;



import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.callippus.web.beans.dto.DisciplineDTO;
import com.callippus.web.beans.dto.QualificationDTO;
import com.callippus.web.beans.file.FileUploadBean;
import com.callippus.web.beans.file.FilesBean;
import com.callippus.web.business.hrdg.training.TrainingMasterBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.FileUpload;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.hrdg.file.UploadAndDownloadBean;
import com.callippus.web.hrdg.training.beans.TrainingMasterBean;


@Controller
@RequestMapping("/" + CPSConstants.TRAININGMASTER)
@SessionAttributes
public class TrainingMasterController {

	private static Log log = LogFactory.getLog(TrainingMasterController.class);
	@Autowired
	private TrainingMasterBusiness trainingMasterBusiness;
	@Autowired
	private FileUpload fileUpload;
	
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView onSubmit(@ModelAttribute(CPSConstants.TRAININGMASTER) TrainingMasterBean trainingMstBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		String viewName = "";
		String message = "";
		Map<String,String> modelMap = null;

		try {
			ErpAction.userChecks(request);  
			
			HttpSession session = request.getSession(true);
			if (CPSUtils.compareStrings(CPSConstants.PAGING, trainingMstBean.getParam())) {
				mav = new ModelAndView(session.getAttribute(CPSConstants.RETURN).toString(), CPSConstants.TRAININGMASTER, trainingMstBean);
				return mav;
			}
			if (!CPSUtils.isNullOrEmpty(trainingMstBean.getType())) {
				if(!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.JSONHRDGBOARDMEMBERLIST)))session.removeAttribute(CPSConstants.JSONHRDGBOARDMEMBERLIST);
				if(!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.JSONTRAININGMSTDATALIST)))session.removeAttribute(CPSConstants.JSONTRAININGMSTDATALIST);
				if(!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.JSONHRDGBOARDLIST)))session.removeAttribute(CPSConstants.JSONHRDGBOARDLIST);
				
			}
			if (!CPSUtils.isNullOrEmpty(request.getParameter(CPSConstants.TYPE)))
				trainingMstBean.setType(request.getParameter(CPSConstants.TYPE));
			if (!CPSUtils.isNullOrEmpty(trainingMstBean.getType()))
			{
				session.setAttribute(CPSConstants.TRAININGMSTTYPE, trainingMstBean.getType());
				session.setAttribute(CPSConstants.TRAININGMSTNAME, trainingMasterBusiness.getMstName(trainingMstBean.getType()));
			}
			
			
			viewName = CPSConstants.TRAININGMASTERDATA;
			trainingMstBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
			
			if (CPSUtils.isNull(request.getParameter(CPSConstants.PARAM)) || CPSUtils.compareStrings(CPSConstants.TRAININGHOME, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=home");
				
				if(CPSUtils.compareStrings(trainingMstBean.getType(), CPSConstants.TRAININGINISTITUTE))
				{
					session.setAttribute(CPSConstants.CATEGORYLIST, trainingMasterBusiness.getMasterData(CPSConstants.CATEGORY));
					session.setAttribute(CPSConstants.TRAININGTYPELIST, trainingMasterBusiness.getMasterData(CPSConstants.TRAININGTYPE));
					List regionList = trainingMasterBusiness.getMasterData(CPSConstants.TRAININGREGIONMASTER);
					if(CPSUtils.checkList(regionList))
						session.setAttribute(CPSConstants.JSONTRAININGREGIONLIST, (JSONArray) JSONSerializer.toJSON(regionList));
				}
				else if(CPSUtils.compareStrings(trainingMstBean.getType(), CPSConstants.COURSESUBJCATEGORY))
				{
					session.setAttribute(CPSConstants.CATEGORYLIST, trainingMasterBusiness.getMasterData(CPSConstants.CATEGORY));
				}
				else if(CPSUtils.compareStrings(trainingMstBean.getType(), CPSConstants.COURSESUBJSUBCATEGORY))
				{
					session.setAttribute(CPSConstants.COURSESUBJCATEGORYLIST, trainingMasterBusiness.getMasterData(CPSConstants.COURSESUBJCATEGORY));
					session.setAttribute(CPSConstants.CATEGORYLIST, trainingMasterBusiness.getMasterData(CPSConstants.CATEGORY));
					if (CPSUtils.checkList(trainingMasterBusiness.getMasterData(CPSConstants.COURSESUBJCATEGORY))) {
						session.setAttribute(CPSConstants.JSONCOURSESUBJCATEGORYLIST, (JSONArray) JSONSerializer.toJSON(trainingMasterBusiness.getMasterData(CPSConstants.COURSESUBJCATEGORY)));
					}
				}
				else if(CPSUtils.compareStrings(trainingMstBean.getType(), CPSConstants.TRAININGVENUE))
				{
					session.setAttribute(CPSConstants.CATEGORYLIST, trainingMasterBusiness.getMasterData(CPSConstants.CATEGORY));
					session.setAttribute(CPSConstants.TRAININGTYPELIST, trainingMasterBusiness.getMasterData(CPSConstants.TRAININGTYPE));
					if (CPSUtils.checkList(trainingMasterBusiness.getMasterData(CPSConstants.TRAININGINISTITUTE))) {
						session.setAttribute(CPSConstants.JSONTRAININGINISTITUTELIST, (JSONArray) JSONSerializer.toJSON(trainingMasterBusiness.getMasterData(CPSConstants.TRAININGINISTITUTE)));
					}
					session.setAttribute(CPSConstants.TRAININGINISTITUTELIST, trainingMasterBusiness.getMasterData(CPSConstants.TRAININGINISTITUTE));
					session.setAttribute(CPSConstants.CITYTYPEMASTERLIST, trainingMasterBusiness.getMasterData(CPSConstants.CITYTYPE));
				}
				trainingMstBean = trainingMasterBusiness.getTrainingMasterData(trainingMstBean);
				if(CPSUtils.compareStrings(trainingMstBean.getType(), CPSConstants.TRAININGINISTITUTE))
				{
					trainingMstBean.setMasterDataList(null);
				}
				
				}
			
			//trainingType
			
			if(CPSUtils.compareStrings(CPSConstants.TRAININGTYPE, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=trainingType");
				viewName = CPSConstants.TRAININGTYPEMASTER; 
				trainingMstBean = trainingMasterBusiness.getTrainingMasterData(trainingMstBean);
				session.setAttribute(CPSConstants.TRAININGTYPELIST, trainingMstBean.getMasterDataList());
			}
			else if(CPSUtils.compareStrings(CPSConstants.MANAGETRAININGTYPE, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=manageTrainingType");
				viewName = CPSConstants.TRAININGMASTERDATALIST; 
				message = trainingMasterBusiness.manageTrainingMasterData(trainingMstBean);
				
				trainingMstBean = trainingMasterBusiness.getTrainingMasterData(trainingMstBean);
			}
			else if(CPSUtils.compareStrings(CPSConstants.DELETETRAININGTYPE, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=deleteTrainingType");
				viewName = CPSConstants.TRAININGMASTERDATALIST; 
				trainingMstBean = trainingMasterBusiness.checkForexists(trainingMstBean);
				message = trainingMstBean.getMessage();
				if(CPSUtils.compareStrings(trainingMstBean.getMessage(),CPSConstants.DELETEFAIL))
					session.setAttribute("reason", trainingMstBean.getDtoName());
				trainingMstBean = trainingMasterBusiness.getTrainingMasterData(trainingMstBean);
			}
			
			

			//Training Region
			else if(CPSUtils.compareStrings(CPSConstants.TRAININGREGIONMASTER, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=TrainingRegionMaster");
				viewName = CPSConstants.TRAININGREGIONMASTER;
				List trainingRegionDataList = trainingMasterBusiness.getTrainingRegionDataList(trainingMstBean);
				session.setAttribute(CPSConstants.TRAININGREGIONDATALIST, trainingRegionDataList);
				if(CPSUtils.checkList(trainingRegionDataList))
					session.setAttribute(CPSConstants.JSONTRAININGREGIONDATALIST, (JSONArray) JSONSerializer.toJSON(trainingRegionDataList));
				
			} 

			else if(CPSUtils.compareStrings(CPSConstants.MANAGETRAININGREGION, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=manageTrainingRegion");
				viewName = CPSConstants.TRAININGREGIONDATALIST;
				message = trainingMasterBusiness.manageTrainingRegion(trainingMstBean);
				List trainingRegionDataList = trainingMasterBusiness.getTrainingRegionDataList(trainingMstBean);
				session.setAttribute(CPSConstants.TRAININGREGIONDATALIST, trainingRegionDataList);
				if(CPSUtils.checkList(trainingRegionDataList))
					session.setAttribute(CPSConstants.JSONTRAININGREGIONDATALIST, (JSONArray) JSONSerializer.toJSON(trainingRegionDataList));
				
			}
			else if(CPSUtils.compareStrings(CPSConstants.DELETETRAININGREGION, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=manageTrainingRegion");
				viewName = CPSConstants.TRAININGREGIONDATALIST;
				trainingMstBean = trainingMasterBusiness.checkForexists(trainingMstBean);
				message = trainingMstBean.getMessage();
				if(CPSUtils.compareStrings(trainingMstBean.getMessage(),CPSConstants.DELETEFAIL))
					session.setAttribute("reason", trainingMstBean.getDtoName());
				List trainingRegionDataList = trainingMasterBusiness.getTrainingRegionDataList(trainingMstBean);
				session.setAttribute(CPSConstants.TRAININGREGIONDATALIST, trainingRegionDataList);
				if(CPSUtils.checkList(trainingRegionDataList))
					session.setAttribute(CPSConstants.JSONTRAININGREGIONDATALIST, (JSONArray) JSONSerializer.toJSON(trainingRegionDataList));
				
			}
			
			
			//Training Inistitute
			else if(CPSUtils.compareStrings(CPSConstants.TRAININGINISTITUTE, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=trainingInistitute");
				viewName = CPSConstants.TRAININGINISTITUTEMASTER; 
				session.setAttribute(CPSConstants.TRAININGTYPELIST, trainingMasterBusiness.getMasterData(CPSConstants.TRAININGTYPE));
				List regionList = trainingMasterBusiness.getMasterData(CPSConstants.TRAININGREGIONMASTER);
				if(CPSUtils.checkList(regionList))
					session.setAttribute(CPSConstants.JSONTRAININGREGIONLIST, (JSONArray) JSONSerializer.toJSON(regionList));
				
			}
			else if(CPSUtils.compareStrings(CPSConstants.TRAININGINISTITUTEDATALIST, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=TrainingInistituteDataList");
				viewName = CPSConstants.TRAININGMASTERDATALIST; 
				trainingMstBean = trainingMasterBusiness.getTrainingInistituteDataList(trainingMstBean); 
			}
			else if (CPSUtils.compareStrings(CPSConstants.MANAGETRAININGINISTITUTE, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=manage");
				message = trainingMasterBusiness.manageTrainingMasterData(trainingMstBean);
				viewName = CPSConstants.TRAININGMASTERDATALIST; 
				trainingMstBean = trainingMasterBusiness.getTrainingInistituteDataList(trainingMstBean); 
			}
			else if (CPSUtils.compareStrings(CPSConstants.DELETETRAININGINISTITUTE, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=manage");
				
				trainingMstBean = trainingMasterBusiness.checkForexists(trainingMstBean);
				message = trainingMstBean.getMessage();
				if(CPSUtils.compareStrings(trainingMstBean.getMessage(),CPSConstants.DELETEFAIL))
					session.setAttribute("reason", trainingMstBean.getDtoName());
				
				viewName = CPSConstants.TRAININGMASTERDATALIST; 
				trainingMstBean = trainingMasterBusiness.getTrainingInistituteDataList(trainingMstBean); 
			}
			
			//Training Venue
			else if(CPSUtils.compareStrings(CPSConstants.TRAININGVENUE, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=TrainingVenue");
				viewName = CPSConstants.TRAININGVENUEMASTER;
				List trainingVenueDataList = trainingMasterBusiness.getTrainingVenueDataList(trainingMstBean);
				trainingMstBean.setMasterDataList(trainingVenueDataList);
				session.setAttribute(CPSConstants.CITYTYPEMASTERLIST, trainingMasterBusiness.getMasterData(CPSConstants.CITYTYPE));
			} 

			else if(CPSUtils.compareStrings(CPSConstants.MANAGETRAININGVENUE, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=manageTrainingRegion");
				viewName = CPSConstants.TRAININGMASTERDATALIST; 
				message = trainingMasterBusiness.manageTrainingMasterData(trainingMstBean);
				List trainingVenueDataList = trainingMasterBusiness.getTrainingVenueDataList(trainingMstBean);
				trainingMstBean.setMasterDataList(trainingVenueDataList);
			}
			else if(CPSUtils.compareStrings(CPSConstants.DELETETRAININGVENUE, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=manageTrainingRegion");
				viewName = CPSConstants.TRAININGMASTERDATALIST; 
				
				trainingMstBean = trainingMasterBusiness.checkForexists(trainingMstBean);
				message = trainingMstBean.getMessage();
				if(CPSUtils.compareStrings(trainingMstBean.getMessage(),CPSConstants.DELETEFAIL))
					session.setAttribute("reason", trainingMstBean.getDtoName());
				
				List trainingVenueDataList = trainingMasterBusiness.getTrainingVenueDataList(trainingMstBean);
				trainingMstBean.setMasterDataList(trainingVenueDataList);
			}
			
			else if (CPSUtils.compareStrings(CPSConstants.MANAGE, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=manage");
				
				if(CPSUtils.compareStrings(trainingMstBean.getType(), CPSConstants.COURSE))
					viewName = CPSConstants.COURSEMASTER;
					message = trainingMasterBusiness.manageTrainingMasterData(trainingMstBean);
				trainingMstBean = trainingMasterBusiness.getTrainingMasterData(trainingMstBean);
			}
			else if (CPSUtils.compareStrings(CPSConstants.DELETE, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=delete");
				viewName = CPSConstants.TRAININGMASTERDATA;
				
				trainingMstBean = trainingMasterBusiness.checkForexists(trainingMstBean);
				message = trainingMstBean.getMessage();
				if(CPSUtils.compareStrings(trainingMstBean.getMessage(),CPSConstants.DELETEFAIL))
					session.setAttribute("reason", trainingMstBean.getDtoName());
				
				trainingMstBean = trainingMasterBusiness.getTrainingMasterData(trainingMstBean);
				
			} 
			
			//Course
			else if(CPSUtils.compareStrings(CPSConstants.COURSE, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=course");
				viewName = CPSConstants.COURSEMASTER;
				List categoryList = null;
				List trainingTypeList = null;
				List trainingRegionList = null;
				List trainingInistituteList = null;
				List financialList = null;
				categoryList =  trainingMasterBusiness.getMasterData(CPSConstants.CATEGORY);
				trainingTypeList = trainingMasterBusiness.getMasterData(CPSConstants.TRAININGTYPE);
				if(CPSUtils.compareStrings(CPSConstants.COURSE, request.getParameter(CPSConstants.BACK)))
				{
					
					trainingRegionList = trainingMasterBusiness.getTrainingRegionDataList(trainingMstBean);
					trainingMstBean = trainingMasterBusiness.getTrainingInistituteDataList(trainingMstBean);
					trainingInistituteList = trainingMstBean.getMasterDataList();
				}
				else
				{
					trainingTypeList = trainingMasterBusiness.getMasterData(CPSConstants.TRAININGTYPE);
					trainingRegionList = trainingMasterBusiness.getMasterData(CPSConstants.TRAININGREGIONMASTER);
					trainingInistituteList = trainingMasterBusiness.getMasterData(CPSConstants.TRAININGINISTITUTE);
				}
				
				financialList = trainingMasterBusiness.getMasterData(CPSConstants.FINANCIALYEAR);
				session.setAttribute(CPSConstants.CATEGORYLIST, categoryList);
				session.setAttribute(CPSConstants.TRAININGTYPELIST, trainingTypeList);
				session.setAttribute(CPSConstants.TRAININGREGIONLIST, trainingRegionList);
				session.setAttribute(CPSConstants.TRAININGINISTITUTELIST, trainingInistituteList);

				session.setAttribute(CPSConstants.FINANCIALYEARLIST, financialList);
				if (CPSUtils.checkList(trainingInistituteList)) {
					session.setAttribute(CPSConstants.JSONTRAININGINISTITUTELIST, (JSONArray) JSONSerializer.toJSON(trainingInistituteList));
				}

				if (CPSUtils.checkList(trainingRegionList)) {
					session.setAttribute(CPSConstants.JSONTRAININGREGIONLIST, (JSONArray) JSONSerializer.toJSON(trainingRegionList));
				}
				
				
				trainingMstBean = trainingMasterBusiness.getCourseList(trainingMstBean);
			}
			else if(CPSUtils.compareStrings(CPSConstants.COURSEMASTERDATALIST, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=CourseMasterDataList");
				viewName = CPSConstants.COURSEMASTERDATALIST;
				trainingMstBean = trainingMasterBusiness.getCourseList(trainingMstBean);
			}
			
			else if(CPSUtils.compareStrings(CPSConstants.MANAGECOURSE, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=manageCourse");
				viewName = CPSConstants.COURSEMASTERDATALIST;
				message = trainingMasterBusiness.manageCourse(trainingMstBean);
				trainingMstBean = trainingMasterBusiness.getCourseList(trainingMstBean);
			}
			else if(CPSUtils.compareStrings(CPSConstants.DELETECOURSE, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=manageCourse");
				viewName = CPSConstants.COURSEMASTERDATALIST;
				
				trainingMstBean = trainingMasterBusiness.checkForexists(trainingMstBean);
				message = trainingMstBean.getMessage();
				if(CPSUtils.compareStrings(trainingMstBean.getMessage(),CPSConstants.DELETEFAIL))
					session.setAttribute("reason", trainingMstBean.getDtoName());
				
				trainingMstBean = trainingMasterBusiness.getCourseList(trainingMstBean);
			}
			
			//course duration
			else if(CPSUtils.compareStrings(CPSConstants.COURSEDURATION, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=CourseDuration");
				viewName = CPSConstants.COURSEDURATION;
				
				session.setAttribute(CPSConstants.FINANCIALYEARLIST, trainingMasterBusiness.getMasterData(CPSConstants.FINANCIALYEAR));
				if (CPSUtils.checkList(trainingMasterBusiness.getMasterData(CPSConstants.FINANCIALYEAR))) {
					session.setAttribute(CPSConstants.JSONFINANCIALYEARLIST, (JSONArray) JSONSerializer.toJSON(trainingMasterBusiness.getMasterData(CPSConstants.FINANCIALYEAR)));
				}
				trainingMstBean = trainingMasterBusiness.getCourseDurationList(trainingMstBean);
	
			}
//			else if(CPSUtils.compareStrings(CPSConstants.COURSEDURATIONDATALIST, request.getParameter(CPSConstants.PARAM))) {
//				log.debug("TrainingMasterController --> onSubmit --> param=CourseDurationDataList");
//				viewName = CPSConstants.COURSEDURATIONDATALIST;
//				trainingMstBean = trainingMasterBusiness.getCourseDurationList(trainingMstBean);
//			}
			else if(CPSUtils.compareStrings(CPSConstants.MANAGECOURSEDURATION, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=manageCourseDuration");
				viewName = CPSConstants.COURSEDURATIONDATALIST;
				message = trainingMasterBusiness.manageCourseDuration(trainingMstBean);
				trainingMstBean = trainingMasterBusiness.getCourseDurationList(trainingMstBean);
			}
			else if(CPSUtils.compareStrings(CPSConstants.DELETECOURSEDURATION, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=manageCourseDuration");
				viewName = CPSConstants.COURSEDURATIONDATALIST;
				
				trainingMstBean = trainingMasterBusiness.checkForexists(trainingMstBean);
				message = trainingMstBean.getMessage();
				if(CPSUtils.compareStrings(trainingMstBean.getMessage(),CPSConstants.DELETEFAIL))
					session.setAttribute("reason", trainingMstBean.getDtoName());
				
				trainingMstBean = trainingMasterBusiness.getCourseDurationList(trainingMstBean);
			}
			
			
			//course discipline
			else if(CPSUtils.compareStrings(CPSConstants.COURSEDISCIPLINE, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=CourseDiscipline");
				viewName = CPSConstants.COURSEDISCIPLINE;
				List<DisciplineDTO> disciplineList =  trainingMasterBusiness.getMasterData(CPSConstants.DISCIPLINE);
				List<DisciplineDTO> selectedDisciplineList = trainingMasterBusiness.getSelectedDisciplineList(trainingMstBean);
				trainingMasterBusiness.removeLists(disciplineList,selectedDisciplineList,"discipline");
				
				session.setAttribute(CPSConstants.DISCIPLINELIST, disciplineList);
				session.setAttribute(CPSConstants.SELECTEDCOURSEDISCIPLINELIST, selectedDisciplineList);
				//qualificationList.removeAll(qualificationList);
				if (CPSUtils.checkList(disciplineList)) {
					session.setAttribute(CPSConstants.JSONDISCIPLINELIST, (JSONArray) JSONSerializer.toJSON(disciplineList));
				}
				if (CPSUtils.checkList(selectedDisciplineList)) {
					session.setAttribute(CPSConstants.JSONSELECTEDCOURSEDISCIPLINELIST, (JSONArray) JSONSerializer.toJSON(selectedDisciplineList));
				}
				trainingMstBean = trainingMasterBusiness.getCourseDisciplineList(trainingMstBean);
	
			}
			else if(CPSUtils.compareStrings(CPSConstants.MANAGECOURSEDISCIPLINE, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=manageCourseDuration");
				viewName = CPSConstants.COURSERESULT;
				message = trainingMasterBusiness.manageCourseDiscipline(trainingMstBean);
				trainingMstBean = trainingMasterBusiness.getCourseDisciplineList(trainingMstBean);
			}
			
			
			//course qualification
			else if(CPSUtils.compareStrings(CPSConstants.COURSEQUALIFICATION, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=CourseQualification");
				viewName = CPSConstants.COURSEQUALIFICATION;
				List<QualificationDTO> disciplineList =  trainingMasterBusiness.getMasterData(CPSConstants.QUALIFICATION);
				List<QualificationDTO> selectedQualificationList = trainingMasterBusiness.getSelectedQualificationList(trainingMstBean);
				trainingMasterBusiness.removeLists(disciplineList,selectedQualificationList,"qualification");
				session.setAttribute(CPSConstants.QUALIFICATIONLIST, disciplineList);
				session.setAttribute(CPSConstants.SELECTEDCOURSEQUALIFICATIONLIST, selectedQualificationList);

				if (CPSUtils.checkList(disciplineList)) {
					session.setAttribute(CPSConstants.JSONQUALIFICATIONLIST, (JSONArray) JSONSerializer.toJSON(disciplineList));
				}
				if (CPSUtils.checkList(selectedQualificationList)) {
					session.setAttribute(CPSConstants.JSONSELECTEDCOURSEQUALIFICATIONLIST, (JSONArray) JSONSerializer.toJSON(selectedQualificationList));
				}
				trainingMstBean.setMasterDataList(trainingMasterBusiness.getCourseQualificationList(trainingMstBean));
	
			}
			else if(CPSUtils.compareStrings(CPSConstants.MANAGECOURSEQUALIFICATION, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=manageCourseDuration");
				viewName = CPSConstants.COURSERESULT;
				message = trainingMasterBusiness.manageCourseQualification(trainingMstBean);
				trainingMstBean.setMasterDataList(trainingMasterBusiness.getCourseQualificationList(trainingMstBean));
			}
			
			
			//course designation
			else if(CPSUtils.compareStrings(CPSConstants.COURSEDESIGNATION, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=CourseDesignation");
				viewName = CPSConstants.COURSEDESIGNATION;
				session.setAttribute(CPSConstants.CATEGORYLIST, trainingMasterBusiness.getMasterData(CPSConstants.CATEGORY));
//				List designationList =  trainingMasterBusiness.getDesignationList(trainingMstBean);
//				List selectedDesignationList = trainingMasterBusiness.getSelectedDesignationList(trainingMstBean);
//				//trainingMasterBusiness.removeList(disciplineList,selectedQualificationList);
//				session.setAttribute(CPSConstants.DESIGNATIONLIST, designationList);
//				session.setAttribute(CPSConstants.SELECTEDCOURSEDESIGNATIONLIST, selectedDesignationList);
//
//				if (CPSUtils.checkList(designationList)) {
//					session.setAttribute(CPSConstants.JSONDESIGNATIONLIST, (JSONArray) JSONSerializer.toJSON(designationList));
//				}
//				if (CPSUtils.checkList(selectedDesignationList)) {
//					session.setAttribute(CPSConstants.JSONSELECTEDCOURSEDESIGNATIONLIST, (JSONArray) JSONSerializer.toJSON(selectedDesignationList));
//				}
				trainingMstBean.setMasterDataList(trainingMasterBusiness.getCourseDesignationList(trainingMstBean));
	
			}
			else if(CPSUtils.compareStrings("getDesignationList", request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=getDesignationList");
				viewName = "DesignationsDataList";
				session.setAttribute(CPSConstants.CATEGORYLIST, trainingMasterBusiness.getMasterData(CPSConstants.CATEGORY));
				List designationList =  trainingMasterBusiness.getDesignationList(trainingMstBean);
				List selectedDesignationList = trainingMasterBusiness.getSelectedDesignationList(trainingMstBean);
				//trainingMasterBusiness.removeList(disciplineList,selectedQualificationList);
				session.setAttribute(CPSConstants.DESIGNATIONLIST, designationList);
				session.setAttribute(CPSConstants.SELECTEDCOURSEDESIGNATIONLIST, selectedDesignationList);

				if (CPSUtils.checkList(designationList)) {
					session.setAttribute(CPSConstants.JSONDESIGNATIONLIST, (JSONArray) JSONSerializer.toJSON(designationList));
				}
				if(!CPSUtils.isNullOrEmpty(CPSConstants.JSONSELECTEDCOURSEDESIGNATIONLIST))
				{
					session.removeAttribute(CPSConstants.JSONSELECTEDCOURSEDESIGNATIONLIST);
				}
				if (CPSUtils.checkList(selectedDesignationList)) {
					session.setAttribute(CPSConstants.JSONSELECTEDCOURSEDESIGNATIONLIST, (JSONArray) JSONSerializer.toJSON(selectedDesignationList));
				}
				trainingMstBean.setMasterDataList(trainingMasterBusiness.getCourseDesignationList(trainingMstBean));
				
	
			}
			else if(CPSUtils.compareStrings(CPSConstants.MANAGECOURSEDESIGNATION, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=manageCourseDesignation");
				viewName = "DesignationsDataList";
				message = trainingMasterBusiness.manageCourseDesignation(trainingMstBean);
				List designationList =  trainingMasterBusiness.getDesignationList(trainingMstBean);
				List selectedDesignationList = trainingMasterBusiness.getSelectedDesignationList(trainingMstBean);
				//trainingMasterBusiness.removeList(disciplineList,selectedQualificationList);
				session.setAttribute(CPSConstants.DESIGNATIONLIST, designationList);
				session.setAttribute(CPSConstants.SELECTEDCOURSEDESIGNATIONLIST, selectedDesignationList);

				if (CPSUtils.checkList(designationList)) {
					session.setAttribute(CPSConstants.JSONDESIGNATIONLIST, (JSONArray) JSONSerializer.toJSON(designationList));
				}
				if(!CPSUtils.isNullOrEmpty(CPSConstants.JSONSELECTEDCOURSEDESIGNATIONLIST))
				{
					session.removeAttribute(CPSConstants.JSONSELECTEDCOURSEDESIGNATIONLIST);
				}
				if (CPSUtils.checkList(selectedDesignationList)) {
					session.setAttribute(CPSConstants.JSONSELECTEDCOURSEDESIGNATIONLIST, (JSONArray) JSONSerializer.toJSON(selectedDesignationList));
				}
				trainingMstBean.setMasterDataList(trainingMasterBusiness.getCourseDesignationList(trainingMstBean));
			}
			
			//Course Content
			else if(CPSUtils.compareStrings(CPSConstants.COURSECONTENT, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=CourseQualification");
				viewName = CPSConstants.COURSECONTENT;
				
				trainingMstBean = trainingMasterBusiness.getCourseContent(trainingMstBean);
	
			}
			else if(CPSUtils.compareStrings(CPSConstants.MANAGECOURSECONTENT, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=manageCourseContent");
				viewName = CPSConstants.COURSERESULT;
				message = trainingMasterBusiness.manageCourseContent(trainingMstBean);
				trainingMstBean = trainingMasterBusiness.getCourseContent(trainingMstBean);
			}
			
			
			
			
			
			
			
			
			else if(CPSUtils.compareStrings("commitee", request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=course");
				viewName = "HRDGCommiteeMaster";
				
				session.setAttribute(CPSConstants.CATEGORYLIST, trainingMasterBusiness.getMasterData(CPSConstants.CATEGORY));
				session.setAttribute(CPSConstants.TRAININGTYPELIST, trainingMasterBusiness.getMasterData(CPSConstants.TRAININGTYPE));
				session.setAttribute(CPSConstants.TRAININGINISTITUTELIST, trainingMasterBusiness.getMasterData(CPSConstants.TRAININGINISTITUTE));
				session.setAttribute(CPSConstants.COURSESUBJCATEGORYLIST, trainingMasterBusiness.getMasterData(CPSConstants.COURSESUBJCATEGORY));
				session.setAttribute(CPSConstants.COURSESUBJSUBCATEGORYLIST, trainingMasterBusiness.getMasterData(CPSConstants.COURSESUBJSUBCATEGORY));
				session.setAttribute(CPSConstants.DESIGLIST, trainingMasterBusiness.getMasterData(CPSConstants.DESIGNATION));
				session.setAttribute(CPSConstants.DESCIPLINELIST, trainingMasterBusiness.getMasterData(CPSConstants.DISCIPLINE));
				if (CPSUtils.checkList(trainingMasterBusiness.getMasterData(CPSConstants.TRAININGINISTITUTE))) {
					session.setAttribute(CPSConstants.JSONTRAININGINISTITUTELIST, (JSONArray) JSONSerializer.toJSON(trainingMasterBusiness.getMasterData(CPSConstants.TRAININGINISTITUTE)));
				}
				if (CPSUtils.checkList(trainingMasterBusiness.getMasterData(CPSConstants.COURSESUBJCATEGORY))) {
					session.setAttribute(CPSConstants.JSONCOURSESUBJCATEGORYLIST, (JSONArray) JSONSerializer.toJSON(trainingMasterBusiness.getMasterData(CPSConstants.COURSESUBJCATEGORY)));
				}
				if (CPSUtils.checkList(trainingMasterBusiness.getMasterData(CPSConstants.COURSESUBJSUBCATEGORY))) {
					session.setAttribute(CPSConstants.JSONCOURSESUBJSUBCATEGORYLIST, (JSONArray) JSONSerializer.toJSON(trainingMasterBusiness.getMasterData(CPSConstants.COURSESUBJSUBCATEGORY)));
				}
				if (CPSUtils.checkList(trainingMasterBusiness.getMasterData(CPSConstants.DESIGNATION))) {
					session.setAttribute(CPSConstants.JSONDESIGNATIONLIST, (JSONArray) JSONSerializer.toJSON(trainingMasterBusiness.getMasterData(CPSConstants.DESIGNATION)));
				}
				if (CPSUtils.checkList(trainingMasterBusiness.getMasterData(CPSConstants.DISCIPLINE))) {
					session.setAttribute(CPSConstants.JSONDISCIPLINELIST, (JSONArray) JSONSerializer.toJSON(trainingMasterBusiness.getMasterData(CPSConstants.DISCIPLINE)));
				}
				
				trainingMstBean = trainingMasterBusiness.getCourseList(trainingMstBean);
			}
//			else if(CPSUtils.compareStrings(CPSConstants.COURSEQUALIFICATION, request.getParameter(CPSConstants.PARAM))) {
//				log.debug("TrainingMasterController --> onSubmit --> param=CourseQualification");
//				viewName = CPSConstants.COURSEQUALIFICATION;
//				List<QualificationDTO> qualificationList =  trainingMasterBusiness.getMasterData(CPSConstants.QUALIFICATION);
//				List<QualificationDTO> qualificationList1 = trainingMasterBusiness.getMasterData(CPSConstants.QUALIFICATION);
//				qualificationList1.remove(0);
//				qualificationList1.remove(1);
//				qualificationList.containsAll(Collections.singletonList(qualificationList1));
//				session.setAttribute(CPSConstants.QUALIFICATIONLIST, qualificationList);
//				session.setAttribute(CPSConstants.SELECTEDCOURSEQUALIFICATIONLIST, qualificationList1);
//				//qualificationList.removeAll(qualificationList);
//				if (CPSUtils.checkList(qualificationList)) {
//					session.setAttribute(CPSConstants.JSONCOURSEQUALIFICATIONLIST, (JSONArray) JSONSerializer.toJSON(qualificationList));
//				}
//				if (CPSUtils.checkList(qualificationList)) {
//					session.setAttribute(CPSConstants.JSONSELECTEDCOURSEQUALIFICATIONLIST, (JSONArray) JSONSerializer.toJSON(qualificationList1));
//				}
//				modelMap = new HashMap<String,String>();
//				modelMap.put("categoryName",trainingMstBean.getCategoryName());
//				modelMap.put("trainingType",trainingMstBean.getTrainingType());
//				modelMap.put("trainingInistitute",trainingMstBean.getTrainingInistitute());
//				modelMap.put("courseSubjCategory",trainingMstBean.getCourseSubjCategory());
//				modelMap.put("courseSubjSubCategory",trainingMstBean.getCourseSubjSubCategory());
//				modelMap.put("name",trainingMstBean.getName());
//				
//				
//			} 
//			 if(CPSUtils.compareStrings(CPSConstants.COURSE, request.getParameter(CPSConstants.PARAM))) {
//				log.debug("TrainingMasterController --> onSubmit --> param=CourseDuration");
//				viewName = "TrainingCirculationMaster";
//				
//				session.setAttribute(CPSConstants.CATEGORYLIST, trainingMasterBusiness.getMasterData(CPSConstants.CATEGORY));
//				session.setAttribute(CPSConstants.TRAININGTYPELIST, trainingMasterBusiness.getMasterData(CPSConstants.TRAININGTYPE));
//				session.setAttribute(CPSConstants.TRAININGINISTITUTELIST, trainingMasterBusiness.getMasterData(CPSConstants.TRAININGINISTITUTE));
//				session.setAttribute(CPSConstants.COURSESUBJCATEGORYLIST, trainingMasterBusiness.getMasterData(CPSConstants.COURSESUBJCATEGORY));
//				session.setAttribute(CPSConstants.COURSESUBJSUBCATEGORYLIST, trainingMasterBusiness.getMasterData(CPSConstants.COURSESUBJSUBCATEGORY));
//				session.setAttribute(CPSConstants.COURSELIST, trainingMasterBusiness.getMasterData(CPSConstants.COURSE));
//				session.setAttribute(CPSConstants.DEPARTMENTTYPELIST, trainingMasterBusiness.getMasterData(CPSConstants.DEPARTMENTS));
//				if (CPSUtils.checkList(trainingMasterBusiness.getMasterData(CPSConstants.TRAININGINISTITUTE))) {
//					session.setAttribute(CPSConstants.JSONTRAININGINISTITUTELIST, (JSONArray) JSONSerializer.toJSON(trainingMasterBusiness.getMasterData(CPSConstants.TRAININGINISTITUTE)));
//				}
//				if (CPSUtils.checkList(trainingMasterBusiness.getMasterData(CPSConstants.COURSESUBJCATEGORY))) {
//					session.setAttribute(CPSConstants.JSONCOURSESUBJCATEGORYLIST, (JSONArray) JSONSerializer.toJSON(trainingMasterBusiness.getMasterData(CPSConstants.COURSESUBJCATEGORY)));
//				}
//				if (CPSUtils.checkList(trainingMasterBusiness.getMasterData(CPSConstants.COURSESUBJSUBCATEGORY))) {
//					session.setAttribute(CPSConstants.JSONCOURSESUBJSUBCATEGORYLIST, (JSONArray) JSONSerializer.toJSON(trainingMasterBusiness.getMasterData(CPSConstants.COURSESUBJSUBCATEGORY)));
//				}
//				if (CPSUtils.checkList(trainingMasterBusiness.getMasterData(CPSConstants.COURSE))) {
//					session.setAttribute(CPSConstants.JSONCOURSELIST, (JSONArray) JSONSerializer.toJSON(trainingMasterBusiness.getMasterData(CPSConstants.COURSE)));
//				}
//				session.setAttribute(CPSConstants.CITYTYPEMASTERLIST, trainingMasterBusiness.getMasterData(CPSConstants.CITYTYPE));
//				
//			}
//			 if(CPSUtils.compareStrings(CPSConstants.COURSEDURATION, request.getParameter(CPSConstants.PARAM))) {
//				log.debug("TrainingMasterController --> onSubmit --> param=CourseDuration");
//				viewName = "TrainingNominationMaster";
//				
//				session.setAttribute(CPSConstants.CATEGORYLIST, trainingMasterBusiness.getMasterData(CPSConstants.CATEGORY));
//				session.setAttribute(CPSConstants.TRAININGTYPELIST, trainingMasterBusiness.getMasterData(CPSConstants.TRAININGTYPE));
//				session.setAttribute(CPSConstants.TRAININGINISTITUTELIST, trainingMasterBusiness.getMasterData(CPSConstants.TRAININGINISTITUTE));
//				session.setAttribute(CPSConstants.COURSESUBJCATEGORYLIST, trainingMasterBusiness.getMasterData(CPSConstants.COURSESUBJCATEGORY));
//				session.setAttribute(CPSConstants.COURSESUBJSUBCATEGORYLIST, trainingMasterBusiness.getMasterData(CPSConstants.COURSESUBJSUBCATEGORY));
//				session.setAttribute(CPSConstants.COURSELIST, trainingMasterBusiness.getMasterData(CPSConstants.COURSE));
//				session.setAttribute(CPSConstants.DEPARTMENTTYPELIST, trainingMasterBusiness.getMasterData(CPSConstants.DEPARTMENTS));
//				if (CPSUtils.checkList(trainingMasterBusiness.getMasterData(CPSConstants.TRAININGINISTITUTE))) {
//					session.setAttribute(CPSConstants.JSONTRAININGINISTITUTELIST, (JSONArray) JSONSerializer.toJSON(trainingMasterBusiness.getMasterData(CPSConstants.TRAININGINISTITUTE)));
//				}
//				if (CPSUtils.checkList(trainingMasterBusiness.getMasterData(CPSConstants.COURSESUBJCATEGORY))) {
//					session.setAttribute(CPSConstants.JSONCOURSESUBJCATEGORYLIST, (JSONArray) JSONSerializer.toJSON(trainingMasterBusiness.getMasterData(CPSConstants.COURSESUBJCATEGORY)));
//				}
//				if (CPSUtils.checkList(trainingMasterBusiness.getMasterData(CPSConstants.COURSESUBJSUBCATEGORY))) {
//					session.setAttribute(CPSConstants.JSONCOURSESUBJSUBCATEGORYLIST, (JSONArray) JSONSerializer.toJSON(trainingMasterBusiness.getMasterData(CPSConstants.COURSESUBJSUBCATEGORY)));
//				}
//				if (CPSUtils.checkList(trainingMasterBusiness.getMasterData(CPSConstants.COURSE))) {
//					session.setAttribute(CPSConstants.JSONCOURSELIST, (JSONArray) JSONSerializer.toJSON(trainingMasterBusiness.getMasterData(CPSConstants.COURSE)));
//				}
//				
//				
//			}
			
			
			//hrdg board
			else if(CPSUtils.compareStrings(CPSConstants.HRDGBOARD, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=HRDGBoard");
				viewName = CPSConstants.HRDGBOARDMASTER;
				List hrdgBoardTypeList = trainingMasterBusiness.getMasterData(CPSConstants.HRDGBOARDTYPE);
				
				session.setAttribute(CPSConstants.HRDGBOARDTYPELIST, hrdgBoardTypeList);
				if (CPSUtils.checkList(hrdgBoardTypeList)) {
					session.setAttribute(CPSConstants.JSONHRDGBOARDTYPELIST, (JSONArray) JSONSerializer.toJSON(hrdgBoardTypeList));
				}
				session.setAttribute(CPSConstants.FINANCIALYEARLIST, trainingMasterBusiness.getMasterData(CPSConstants.FINANCIALYEAR));
				if(CPSUtils.compareStrings(CPSConstants.HRDGBOARD, request.getParameter("back")))
				{
				trainingMstBean = trainingMasterBusiness.getHRDGBoardList(trainingMstBean);
				session.setAttribute(CPSConstants.HRDGBOARDDATALIST, trainingMstBean.getMasterDataList());
				if (CPSUtils.checkList(trainingMstBean.getMasterDataList())) {
					session.setAttribute(CPSConstants.JSONHRDGBOARDLIST, (JSONArray) JSONSerializer.toJSON(trainingMstBean.getMasterDataList()));
				}
				}
				
				
			} 
			else if(CPSUtils.compareStrings(CPSConstants.HRDGBOARDDATALIST, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=HRDGBoardDataList");
				viewName = CPSConstants.HRDGBOARDDATALIST;
				
				trainingMstBean = trainingMasterBusiness.getHRDGBoardList(trainingMstBean);
				
				session.setAttribute(CPSConstants.HRDGBOARDDATALIST, trainingMstBean.getMasterDataList());
				if (CPSUtils.checkList(trainingMstBean.getMasterDataList())) {
					session.setAttribute(CPSConstants.JSONHRDGBOARDLIST, (JSONArray) JSONSerializer.toJSON(trainingMstBean.getMasterDataList()));
				}
			} 
			
			else if(CPSUtils.compareStrings(CPSConstants.MANAGEHRDGBOARD, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=manageHRDGBoard");
				viewName = CPSConstants.HRDGBOARDDATALIST;
				
				message = trainingMasterBusiness.manageHRDGBoard(trainingMstBean);
				trainingMstBean.setId(null);
				trainingMstBean = trainingMasterBusiness.getHRDGBoardList(trainingMstBean);
				session.setAttribute(CPSConstants.HRDGBOARDDATALIST, trainingMstBean.getMasterDataList());
				if (CPSUtils.checkList(trainingMstBean.getMasterDataList())) {
					session.setAttribute(CPSConstants.JSONHRDGBOARDLIST, (JSONArray) JSONSerializer.toJSON(trainingMstBean.getMasterDataList()));
				}
			}
			else if(CPSUtils.compareStrings(CPSConstants.DELETEHRDGBOARD, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=deleteHRDGBoard");
				viewName = CPSConstants.HRDGBOARDDATALIST;
				
				trainingMstBean = trainingMasterBusiness.checkForexists(trainingMstBean);
				message = trainingMstBean.getMessage();
				if(CPSUtils.compareStrings(trainingMstBean.getMessage(),CPSConstants.DELETEFAIL))
					session.setAttribute("reason", trainingMstBean.getDtoName());
				
				trainingMstBean.setId(null);
				trainingMstBean = trainingMasterBusiness.getHRDGBoardList(trainingMstBean);
				session.setAttribute(CPSConstants.HRDGBOARDDATALIST, trainingMstBean.getMasterDataList());
				if (CPSUtils.checkList(trainingMstBean.getMasterDataList())) {
					session.setAttribute(CPSConstants.JSONHRDGBOARDLIST, (JSONArray) JSONSerializer.toJSON(trainingMstBean.getMasterDataList()));
				}
			}
			
			//hrdg board member
			else if(CPSUtils.compareStrings(CPSConstants.HRDGBOARDMEMBER, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=HRDGBoardMember");
				viewName = CPSConstants.HRDGBOARDMEMBERMASTER;
				
				
				List hrdgBoardMemberTypeList = trainingMasterBusiness.getMasterData(CPSConstants.HRDGBOARDMEMBERTYPE);
				session.setAttribute(CPSConstants.HRDGBOARDMEMBERTYPELIST, hrdgBoardMemberTypeList);
				if (CPSUtils.checkList(hrdgBoardMemberTypeList)) {
					session.setAttribute(CPSConstants.JSONHRDGBOARDMEMBERTYPELIST, (JSONArray) JSONSerializer.toJSON(hrdgBoardMemberTypeList));
				}
				trainingMstBean = trainingMasterBusiness.getHRDGBoardMemberList(trainingMstBean);
				session.setAttribute(CPSConstants.HRDGBOARDMEMBERDATALIST, trainingMstBean.getMasterDataList());
				if (CPSUtils.checkList(trainingMstBean.getMasterDataList())) {
					session.setAttribute(CPSConstants.JSONHRDGBOARDMEMBERLIST, (JSONArray) JSONSerializer.toJSON(trainingMstBean.getMasterDataList()));
				}
				
			} 
//			else if(CPSUtils.compareStrings(CPSConstants.HRDGBOARDMEMBERDATALIST, request.getParameter(CPSConstants.PARAM))) {
//				log.debug("TrainingMasterController --> onSubmit --> param=HRDGBoardMemberDataList");
//				viewName = CPSConstants.HRDGBOARDMEMBERDATALIST;
//				trainingMstBean = trainingMasterBusiness.getHRDGBoardMemberList(trainingMstBean);
//				session.setAttribute(CPSConstants.HRDGBOARDMEMBERDATALIST, trainingMstBean.getMasterDataList());
//				if (CPSUtils.checkList(trainingMstBean.getMasterDataList())) {
//					session.setAttribute(CPSConstants.JSONHRDGBOARDMEMBERLIST, (JSONArray) JSONSerializer.toJSON(trainingMstBean.getMasterDataList()));
//				}
//			}
			else if(CPSUtils.compareStrings(CPSConstants.MANAGEHRDGBOARDMEMBER, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=manageHRDGBoard");
				viewName = CPSConstants.HRDGBOARDMEMBERDATALIST;
				message = trainingMasterBusiness.manageHRDGBoardMember(trainingMstBean);
				trainingMstBean = trainingMasterBusiness.getHRDGBoardMemberList(trainingMstBean);
				session.setAttribute(CPSConstants.HRDGBOARDMEMBERDATALIST, trainingMstBean.getMasterDataList());
				if (CPSUtils.checkList(trainingMstBean.getMasterDataList())) {
					session.setAttribute(CPSConstants.JSONHRDGBOARDMEMBERLIST, (JSONArray) JSONSerializer.toJSON(trainingMstBean.getMasterDataList()));
				}
			}
			else if(CPSUtils.compareStrings(CPSConstants.DELETEHRDGBOARDMEMBER, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=deleteHRDGBoardMember");
				viewName = CPSConstants.HRDGBOARDMEMBERDATALIST;
				
				trainingMstBean = trainingMasterBusiness.checkForexists(trainingMstBean);
				message = trainingMstBean.getMessage();
				if(CPSUtils.compareStrings(trainingMstBean.getMessage(),CPSConstants.DELETEFAIL))
					session.setAttribute("reason", trainingMstBean.getDtoName());
				
				trainingMstBean = trainingMasterBusiness.getHRDGBoardMemberList(trainingMstBean);
				session.setAttribute(CPSConstants.HRDGBOARDMEMBERDATALIST, trainingMstBean.getMasterDataList());
				if (CPSUtils.checkList(trainingMstBean.getMasterDataList())) {
					session.setAttribute(CPSConstants.JSONHRDGBOARDMEMBERLIST, (JSONArray) JSONSerializer.toJSON(trainingMstBean.getMasterDataList()));
				}
			}
			//HRDGYearBook
			else if(CPSUtils.compareStrings("HRDGYearBook", request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=HRDGYearBook");
				viewName = "HRDGYearBookMaster";
				session.setAttribute(CPSConstants.TRAININGTYPELIST, trainingMasterBusiness.getMasterData(CPSConstants.TRAININGTYPE));
				session.setAttribute(CPSConstants.FINANCIALYEARLIST, trainingMasterBusiness.getMasterData(CPSConstants.FINANCIALYEAR));
			}
			
			//training Circulation
			else if(CPSUtils.compareStrings(CPSConstants.TRAININGCIRCULATIONDETAILS, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=TrainingCirculationDetails");
				viewName = CPSConstants.TRAININGCIRCULATIONDETAILS;
				
				
			}
			else if(CPSUtils.compareStrings(CPSConstants.TRAININGCIRCULATIONDETAILSDATALIST, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=TrainingCirculationDetailsDataList");
				viewName = CPSConstants.TRAININGCIRCULATIONDETAILSDATALIST;
				List courseList = trainingMasterBusiness.getCourseListBasedonDuration(trainingMstBean);
				session.setAttribute(CPSConstants.COURSELIST, courseList);
				trainingMstBean.setMasterDataList(courseList);
				
			}
			
			else if(CPSUtils.compareStrings(CPSConstants.TRAININGCIRCULATIONMASTER, request.getParameter(CPSConstants.PARAM))) {
					log.debug("TrainingMasterController --> onSubmit --> param=TrainingCirculationMaster");
					viewName = CPSConstants.TRAININGCIRCULATIONMASTER;
					trainingMstBean = trainingMasterBusiness.setCirculationDetails(trainingMstBean);
					
					//List departmentList = trainingMasterBusiness.getMasterData(CPSConstants.DEPARTMENTS);
					List departmentList = trainingMasterBusiness.getDepartmentList();
					List list = departmentList;
					List selectedList = trainingMstBean.getMasterDataList();
					trainingMasterBusiness.removeLists(list, selectedList,CPSConstants.TRAININGCIRCULATIONMASTER);
					departmentList = list;
					trainingMstBean.setMasterDataList(selectedList);
					session.setAttribute(CPSConstants.SELECTEDDEPARTMENTLIST, trainingMstBean.getMasterDataList());
					session.setAttribute(CPSConstants.DEPARTMENTTYPELIST, departmentList);
					if (CPSUtils.checkList(departmentList)) {
						session.setAttribute(CPSConstants.JSONDEPARTMENTLIST, (JSONArray) JSONSerializer.toJSON(departmentList));
					}
					
					List cityList = trainingMasterBusiness.getTrainingVenueDetails(trainingMstBean);
					session.setAttribute(CPSConstants.CITYTYPEMASTERLIST, cityList);
					if (CPSUtils.checkList(cityList)) {
						session.setAttribute(CPSConstants.JSONCITYTYPELIST, (JSONArray) JSONSerializer.toJSON(cityList));
					}
					
					if(!CPSUtils.isNull(session.getAttribute("jsonSeriesMstList")))session.removeAttribute("jsonSeriesMstList");
					if(!CPSUtils.isNull(session.getAttribute("jsonIonMstList")))session.removeAttribute("jsonIonMstList");
					
					List seriesMstList = trainingMasterBusiness.getSeriesMstList();
					session.setAttribute("seriesMstList", seriesMstList);
					if (CPSUtils.checkList(seriesMstList)) {
						session.setAttribute("jsonSeriesMstList", (JSONArray) JSONSerializer.toJSON(seriesMstList));
					}
					
					List ionMstList = trainingMstBean.getIonList();
					session.setAttribute("ionMstList", ionMstList);
					if (CPSUtils.checkList(ionMstList)) {
						session.setAttribute("jsonIonMstList", (JSONArray) JSONSerializer.toJSON(ionMstList));
					}
					
					
				}
			else if(CPSUtils.compareStrings("getIonMstList", request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=getIonMstList"); 
				viewName ="TrainingION";
				if(!CPSUtils.isNull(session.getAttribute("jsonIonMstList")))session.removeAttribute("jsonIonMstList");
				List ionMstList = trainingMasterBusiness.getIONMstList(trainingMstBean);
				session.setAttribute("ionMstList", ionMstList);
				if (CPSUtils.checkList(ionMstList)) {
					session.setAttribute("jsonIonMstList", (JSONArray) JSONSerializer.toJSON(ionMstList));
				}
			}
			
			else if(CPSUtils.compareStrings(CPSConstants.MANAGETRAININGCIRCULATION, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=manageTrainingCirculation");
				viewName = "TrainingCirBrochure";
				trainingMstBean.setIpAddress(request.getRemoteAddr());
				trainingMstBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				if((CPSUtils.isNullOrEmpty(trainingMstBean.getBrochure()) && !CPSUtils.isNullOrEmpty(trainingMstBean.getId())) || (CPSUtils.isNullOrEmpty(trainingMstBean.getId())))
				{
				trainingMstBean.setUploadBean((UploadAndDownloadBean)session.getAttribute("trainingBrochure"));	
				Map<String, MultipartFile> map =new HashMap<String, MultipartFile>();
				map.put("brochure", trainingMstBean.getBrochureFile());
				FileUploadBean fub=new FileUploadBean();
				fub.setFiles(map);
				fileUpload.uploadFileToDatabase(fub);
				trainingMstBean.setBrochure(String.valueOf(fub.getFileIds().get("brochure")));
				trainingMstBean.setBrochureName(String.valueOf(fub.getFileNames().get("brochure")));
				session.setAttribute("brochureId", trainingMstBean.getBrochure());
				session.setAttribute("brochureName", trainingMstBean.getBrochureName());
				}
				message = trainingMasterBusiness.manageTrainingCirculation(trainingMstBean); 
			
			}
			else if(CPSUtils.compareStrings("viewBrochure", request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=getBrochure");
				viewName = "TrainingCirBrochure";
				trainingMstBean.setBrochure(session.getAttribute("brochureId").toString());
				trainingMstBean.setBrochureName(session.getAttribute("brochureName").toString());
			}
			else if(CPSUtils.compareStrings(CPSConstants.CANCELTRAININGCIRCULATION, request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=CancelTrainingCirculation");
				viewName = CPSConstants.TRAININGCIRCULATIONMASTER;
				message = trainingMasterBusiness.manageTrainingCirculation(trainingMstBean);
				
			}
			else if(CPSUtils.compareStrings("GetBrochure", request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=GetBrochure");
				viewName = CPSConstants.TRAININGCIRCULATIONMASTER;
				FilesBean file = trainingMasterBusiness.getBrochure(Integer.parseInt(trainingMstBean.getFileId()));
				response.setContentType(file.getType());
				response.setContentLength(file.getFile().length);
				response.setHeader("Content-Disposition", "inline; filename=\"" + file.getFilename() + "\"");
				FileCopyUtils.copy(file.getFile(), response.getOutputStream());
				
			}
			else if(CPSUtils.compareStrings("CourseAttendedDetails", request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=CourseAttendedDetails");
				viewName = "CourseAttendedDetails";
				
				List trainingTypeList = null;
				List financialList = null;
				
				trainingTypeList = trainingMasterBusiness.getMasterData(CPSConstants.TRAININGTYPE);
				financialList = trainingMasterBusiness.getMasterData(CPSConstants.FINANCIALYEAR);
				
				session.setAttribute(CPSConstants.TRAININGTYPELIST, trainingTypeList);
				session.setAttribute(CPSConstants.FINANCIALYEARLIST, financialList);
				if(CPSUtils.checkList(financialList))
					session.setAttribute("jsonFinancialList", (JSONArray) JSONSerializer.toJSON(financialList));
				
				trainingMstBean = trainingMasterBusiness.getAttendedCourseList(trainingMstBean);
			}
			else if(CPSUtils.compareStrings("CourseAttendedDataList", request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=CourseAttendedDataList");
				viewName = "CourseAttendedDataList";
				
				trainingMstBean = trainingMasterBusiness.getAttendedCourseList(trainingMstBean);
			}
			else if(CPSUtils.compareStrings("manageCourseAttended", request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=manageCourseAttended");
				viewName = "CourseAttendedDataList";
				message = trainingMasterBusiness.manageCourseAttended(trainingMstBean);
				trainingMstBean = trainingMasterBusiness.getAttendedCourseList(trainingMstBean);
			}
			else if(CPSUtils.compareStrings("deleteCourseAttended", request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=deleteCourseAttended");
				viewName = "CourseAttendedDataList";
				message = trainingMasterBusiness.deleteCourseAttended(trainingMstBean);
				trainingMstBean = trainingMasterBusiness.getAttendedCourseList(trainingMstBean);
			}
			else if(CPSUtils.compareStrings("getEmployeeName", request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=getEmployeeName"); 
				viewName ="TrainingEmployeeName";
				trainingMstBean.setSfid(trainingMstBean.getNomineeSfid()); 
				trainingMstBean = trainingMasterBusiness.getEmpDetails(trainingMstBean);
				if(!CPSUtils.isNull(trainingMstBean.getEmpDetails())) message = CPSConstants.EMPEXITS;
				else message = CPSConstants.EMPNOTEXISTS;
			}
			//HRDG Board Type
			else if(CPSUtils.compareStrings("HRDGBoardType", request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=HRDGBoardType"); 
				viewName ="HRDGBoardTypeMaster";
				trainingMstBean = trainingMasterBusiness.getTrainingMasterData(trainingMstBean);
			}
			else if(CPSUtils.compareStrings("manageHRDGBoardType", request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=manageHRDGBoardType"); 
				viewName ="HRDGBoardTypeMasterDataList";
				message = trainingMasterBusiness.manageHRDGBoardType(trainingMstBean);
				trainingMstBean = trainingMasterBusiness.getTrainingMasterData(trainingMstBean);
			}
			else if(CPSUtils.compareStrings("deleteHRDGBoardType", request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=deleteHRDGBoardType");
				viewName ="HRDGBoardTypeMasterDataList";
				trainingMstBean = trainingMasterBusiness.checkForexists(trainingMstBean);
				message = trainingMstBean.getMessage();
				if(CPSUtils.compareStrings(trainingMstBean.getMessage(),CPSConstants.DELETEFAIL))
					session.setAttribute("reason", trainingMstBean.getDtoName());
				trainingMstBean = trainingMasterBusiness.getTrainingMasterData(trainingMstBean);
			}
			
			//HRDG Board Member Type
			else if(CPSUtils.compareStrings("HRDGBoardMemberType", request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=HRDGBoardType"); 
				viewName ="HRDGBoardMemberTypeMaster";
				trainingMstBean = trainingMasterBusiness.getTrainingMasterData(trainingMstBean);
			}
			else if(CPSUtils.compareStrings("manageHRDGBoardMemberType", request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=manageHRDGBoardMemberType"); 
				viewName ="HRDGBoardMemberTypeMasterDataList";
				message = trainingMasterBusiness.manageHRDGBoardMemberType(trainingMstBean);
				trainingMstBean = trainingMasterBusiness.getTrainingMasterData(trainingMstBean);
			}
			else if(CPSUtils.compareStrings("deleteHRDGBoardMemberType", request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=deleteHRDGBoardMemberType");
				viewName ="HRDGBoardMemberTypeMasterDataList";
				trainingMstBean = trainingMasterBusiness.checkForexists(trainingMstBean);
				message = trainingMstBean.getMessage();
				if(CPSUtils.compareStrings(trainingMstBean.getMessage(),CPSConstants.DELETEFAIL))
					session.setAttribute("reason", trainingMstBean.getDtoName());
				trainingMstBean = trainingMasterBusiness.getTrainingMasterData(trainingMstBean);
			}
			
			
			
		
			
			
			mav = new ModelAndView(viewName, CPSConstants.TRAININGMASTER, trainingMstBean);
			mav.addObject(CPSConstants.MESSAGE, message);
			mav.addAllObjects(modelMap);
			session.setAttribute(CPSConstants.RETURN, viewName);
			if (CPSUtils.checkList(trainingMstBean.getMasterDataList())) {
				session.setAttribute(CPSConstants.JSONTRAININGMSTDATALIST, (JSONArray) JSONSerializer.toJSON(trainingMstBean.getMasterDataList()));
			}
			
		

		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
	

}

