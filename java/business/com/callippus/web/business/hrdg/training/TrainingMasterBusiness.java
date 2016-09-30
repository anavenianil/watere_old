package com.callippus.web.business.hrdg.training;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.DepartmentsDTO;
import com.callippus.web.beans.dto.DisciplineDTO;
import com.callippus.web.beans.dto.QualificationDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.file.FilesBean;
import com.callippus.web.beans.letterNumberFormat.IONDTO;
import com.callippus.web.beans.letterNumberFormat.IonDeptDTO;
import com.callippus.web.beans.letterNumberFormat.IonFileDTO;
import com.callippus.web.beans.requests.AlertMessageDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.letterNumberFormat.ILetterNumberFormatDAO;
import com.callippus.web.hrdg.dao.ICommonDAO;
import com.callippus.web.hrdg.file.dao.IFileDAO;
import com.callippus.web.hrdg.training.beans.TrainingMasterBean;
import com.callippus.web.hrdg.training.beans.dto.CourseAttendedDetailsDTO;
import com.callippus.web.hrdg.training.beans.dto.CourseContentDTO;
import com.callippus.web.hrdg.training.beans.dto.CourseDTO;
import com.callippus.web.hrdg.training.beans.dto.CourseDesignationsDTO;
import com.callippus.web.hrdg.training.beans.dto.CourseDisciplinesDTO;
import com.callippus.web.hrdg.training.beans.dto.CourseDurationsDTO;
import com.callippus.web.hrdg.training.beans.dto.CourseQualificationsDTO;
import com.callippus.web.hrdg.training.beans.dto.CourseSubjCategoryDTO;
import com.callippus.web.hrdg.training.beans.dto.CourseSubjSubCategoryDTO;
import com.callippus.web.hrdg.training.beans.dto.HRDGBoardDTO;
import com.callippus.web.hrdg.training.beans.dto.HRDGBoardMemberDTO;
import com.callippus.web.hrdg.training.beans.dto.HRDGBoardMemberTypeDTO;
import com.callippus.web.hrdg.training.beans.dto.HRDGBoardTypeDTO;
import com.callippus.web.hrdg.training.beans.dto.TrainingCirculationDTO;
import com.callippus.web.hrdg.training.beans.dto.TrainingCirculationDeptDTO;
import com.callippus.web.hrdg.training.beans.dto.TrainingInistituteDTO;
import com.callippus.web.hrdg.training.beans.dto.TrainingRegionDTO;
import com.callippus.web.hrdg.training.beans.dto.TrainingTypeDTO;
import com.callippus.web.hrdg.training.beans.dto.TrainingVenueDTO;
import com.callippus.web.hrdg.training.dao.ITrainingAlertDAO;
import com.callippus.web.hrdg.training.dao.ITrainingMasterDAO;

@Service
public class TrainingMasterBusiness {
	private static Log log = LogFactory.getLog(TrainingMasterBusiness.class);
	@Autowired
	private ITrainingMasterDAO trainingMasterDAO;
	@Autowired
	private ITrainingAlertDAO trainingAlertDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;
	@Autowired
	private IFileDAO fileDAO;
	@Autowired
	private ICommonDAO commonDAO;
	@Autowired
	private ILetterNumberFormatDAO letterNumberFormatDAO;

	public TrainingMasterBean getTrainingMasterData(TrainingMasterBean trainingMstBean) throws Exception {
		try {

			trainingMstBean.setMasterDataList(trainingMasterDAO.getMasterData(getBeanName(trainingMstBean.getType())));
		} catch (Exception e) {
			throw e;
		}
		return trainingMstBean;
	}

	public String getBeanName(String type) throws Exception {
		try {

			if (CPSUtils.compareStrings(type, CPSConstants.TRAININGTYPE))
				return CPSConstants.TRAININGTYPEDTO;
			else if (CPSUtils.compareStrings(type, CPSConstants.CATEGORY))
				return CPSConstants.CATEGORYDTO;
			else if (CPSUtils.compareStrings(type, CPSConstants.TRAININGINISTITUTE))
				return CPSConstants.TRAININGINISTITUTEDTO;
			else if (CPSUtils.compareStrings(type, CPSConstants.COURSESUBJCATEGORY))
				return CPSConstants.COURSESUBJCATEGORYDTO;
			else if (CPSUtils.compareStrings(type, CPSConstants.COURSESUBJSUBCATEGORY))
				return CPSConstants.COURSESUBJSUBCATEGORYDTO;
			else if (CPSUtils.compareStrings(type, CPSConstants.TRAININGVENUE))
				return CPSConstants.TRAININGVENUEDTO;
			else if (CPSUtils.compareStrings(type, CPSConstants.CITYTYPE))
				return CPSConstants.CITYDTO;
			else if (CPSUtils.compareStrings(type, CPSConstants.COURSE))
				return CPSConstants.COURSEDTO;
			else if (CPSUtils.compareStrings(type, CPSConstants.COURSEDURATION))
				return CPSConstants.COURSEDURATIONSDTO;
			else if (CPSUtils.compareStrings(type, CPSConstants.DESIGNATION))
				return CPSConstants.DESIGNATIONDTO;
			else if (CPSUtils.compareStrings(type, CPSConstants.DISCIPLINE))
				return CPSConstants.DISCIPLINEDTO;
			else if (CPSUtils.compareStrings(type, CPSConstants.QUALIFICATION))
				return CPSConstants.QUALIFICATIONDTO;
			else if (CPSUtils.compareStrings(type, CPSConstants.DEPARTMENTS))
				return CPSConstants.DepartmentsDTO;
			else if (CPSUtils.compareStrings(type, CPSConstants.HRDGBOARDTYPE))
				return CPSConstants.HRDGBOARDTYPEDTO;
			else if (CPSUtils.compareStrings(type, CPSConstants.HRDGBOARDMEMBERTYPE))
				return CPSConstants.HRDGBOARDMEMBERTYPEDTO;
			else if (CPSUtils.compareStrings(type, CPSConstants.HRDGBOARD))
				return CPSConstants.HRDGBOARDDTO;
			else if (CPSUtils.compareStrings(type, CPSConstants.HRDGBOARDMEMBER))
				return CPSConstants.HRDGBOARDMEMBERDTO;
			else if (CPSUtils.compareStrings(type, CPSConstants.TRAININGCIRCULATIONMASTER))
				return CPSConstants.TRAININGCIRCULATIONDTO;
			else if (CPSUtils.compareStrings(type, CPSConstants.FINANCIALYEAR))
				return CPSConstants.FINANCIALYEARDTO;
			else if (CPSUtils.compareStrings(type, CPSConstants.TRAININGREGIONMASTER))
				return CPSConstants.TRAININGREGIONDTO;
			

		} catch (Exception e) {
			throw e;
		}
		return null;
	}

	public String manageTrainingMasterData(TrainingMasterBean trainingMstBean) throws Exception {
		String message = null;
		try {
			if (!CPSUtils.isNullOrEmpty(trainingMstBean.getType())) {
				trainingMstBean.setBeanName(getBeanName(trainingMstBean.getType()));
				trainingMstBean.setCreationDate(CPSUtils.getCurrentDate());
				trainingMstBean.setCreatedBy(trainingMstBean.getSfid());
				trainingMstBean.setStatus(1);
				if (CPSUtils.isNullOrEmpty(trainingMstBean.getId())) {
					if (CPSUtils.compareStrings(checkMasterData(trainingMstBean), CPSConstants.SUCCESS)) {

						if (CPSUtils.compareStrings(trainingMstBean.getType(), CPSConstants.TRAININGTYPE)) {
							TrainingTypeDTO dto = new TrainingTypeDTO();
							BeanUtils.copyProperties(dto, trainingMstBean);

							message = commonDAO.save(dto);
						} else if (CPSUtils.compareStrings(trainingMstBean.getType(), CPSConstants.TRAININGINISTITUTE)) {
							TrainingInistituteDTO dto = new TrainingInistituteDTO();
							BeanUtils.copyProperties(dto, trainingMstBean);

							message = commonDAO.save(dto);
						} else if (CPSUtils.compareStrings(trainingMstBean.getType(), CPSConstants.COURSESUBJCATEGORY)) {
							CourseSubjCategoryDTO dto = new CourseSubjCategoryDTO();
							BeanUtils.copyProperties(dto, trainingMstBean);

							message = commonDAO.save(dto);
						} else if (CPSUtils.compareStrings(trainingMstBean.getType(), CPSConstants.COURSESUBJSUBCATEGORY)) {
							CourseSubjSubCategoryDTO dto = new CourseSubjSubCategoryDTO();
							BeanUtils.copyProperties(dto, trainingMstBean);

							message = commonDAO.save(dto);
						} else if (CPSUtils.compareStrings(trainingMstBean.getType(), CPSConstants.TRAININGVENUE)) {
							TrainingVenueDTO dto = new TrainingVenueDTO();

							BeanUtils.copyProperties(dto, trainingMstBean);

							message = commonDAO.save(dto);
						} else if (CPSUtils.compareStrings(trainingMstBean.getType(), CPSConstants.COURSE)) {
							CourseDTO dto = new CourseDTO();

							BeanUtils.copyProperties(dto, trainingMstBean);

							message = commonDAO.save(dto);
							List list = trainingMasterDAO.checkMasterData(trainingMstBean);
							int courseId = (!CPSUtils.isNullOrEmpty(list)) ? ((CourseDTO) list.get(0)).getId() : 0;
							if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
								String[] designations = (!CPSUtils.isNullOrEmpty(trainingMstBean.getDesignation())) ? trainingMstBean.getDesignation().split(",") : null;
								for (int i = 0; !CPSUtils.isNullOrEmpty(designations) && i < designations.length; i++) {
									CourseDesignationsDTO courseDesigDTO = new CourseDesignationsDTO();
									BeanUtils.copyProperties(courseDesigDTO, trainingMstBean);
									courseDesigDTO.setCourseId(courseId);
									courseDesigDTO.setDesignationId(Integer.parseInt(designations[i]));
									message = commonDAO.save(courseDesigDTO);
								}
								String[] disciplines = (!CPSUtils.isNullOrEmpty(trainingMstBean.getDiscipline())) ? trainingMstBean.getDiscipline().split(",") : null;
								for (int i = 0; !CPSUtils.isNullOrEmpty(disciplines) && i < disciplines.length; i++) {
									CourseDisciplinesDTO courseDiscipDTO = new CourseDisciplinesDTO();
									BeanUtils.copyProperties(courseDiscipDTO, trainingMstBean);
									courseDiscipDTO.setCourseId(courseId);
									courseDiscipDTO.setDisciplineId(Integer.parseInt(disciplines[i]));
									message = commonDAO.save(courseDiscipDTO);

								}
							}

						}

					} else {
						message = CPSConstants.DUPLICATE;
					}

				} else {
					if (CPSUtils.compareStrings(checkMasterData(trainingMstBean), CPSConstants.SUCCESS)) {
						message = trainingMasterDAO.update(trainingMstBean);
					} else {
						message = CPSConstants.DUPLICATE;
					}

				}

			}

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public TrainingMasterBean deleteTrainingMasterData(TrainingMasterBean trainingMstBean) throws Exception {
		String message = null;
		String status = "true";
		
		try { 

			trainingMstBean.setBeanName(getBeanName(trainingMstBean.getType()));
			message = getTrainingTableDetails(trainingMstBean.getType());
			trainingMstBean.setDtoName(" in");
			if (!CPSUtils.isNullOrEmpty(message)) {
				String[] record = message.split(",");
				for (int i = 0; i <= record.length - 1; i++) {
					message = trainingMasterDAO.deleteTrainingCheckMaster(record[i].split("#")[0], record[i].split("#")[1], record[i].split("#")[2], trainingMstBean.getId());
					if (CPSUtils.compareStrings(message, CPSConstants.FAILED)) {
						trainingMstBean.setDtoName((CPSUtils.isNullOrEmpty(trainingMstBean.getDtoName()))?record[i].split("#")[0]+",":trainingMstBean.getDtoName()+record[i].split("#")[0]+",");
						status += "false";
						
					}
				}
			}
			if (!status.contains("false")) {
				message = trainingMasterDAO.deleteTrainingMasterData(trainingMstBean);
				
			} else {
				message = CPSConstants.DELETEFAIL;
			}

		} catch (Exception e) {
			throw e;
		}
		trainingMstBean.setMessage(message);
		return trainingMstBean;
	}
	public TrainingMasterBean checkForexists(TrainingMasterBean trainingMstBean) throws Exception
	{
		String message = null;
		try
		{
			trainingMstBean =  deleteTrainingMasterData(trainingMstBean);
			if(CPSUtils.compareStrings(trainingMstBean.getMessage(),CPSConstants.DELETEFAIL))
					{
						if (trainingMstBean.getDtoName() != null && trainingMstBean.getDtoName().contains(CPSConstants.TRAININGREGIONDTO))
							trainingMstBean.setDtoName(trainingMstBean.getDtoName().replaceAll(CPSConstants.TRAININGREGIONDTO, " Region Master"));
						
						if (trainingMstBean.getDtoName() != null && trainingMstBean.getDtoName().contains(CPSConstants.TRAININGINISTITUTEDTO))
							trainingMstBean.setDtoName(trainingMstBean.getDtoName().replaceAll(CPSConstants.TRAININGINISTITUTEDTO, " Training Institute Master"));
					
						
						if (trainingMstBean.getDtoName() != null && trainingMstBean.getDtoName().contains(CPSConstants.TRAININGVENUEDTO))
							trainingMstBean.setDtoName(trainingMstBean.getDtoName().replaceAll(CPSConstants.TRAININGVENUEDTO, " Training Venue Master"));
					
						if (trainingMstBean.getDtoName() != null && trainingMstBean.getDtoName().contains(CPSConstants.COURSEDTO))
							trainingMstBean.setDtoName(trainingMstBean.getDtoName().replaceAll(CPSConstants.COURSEDTO, " Course Duration Master"));
					
						if (trainingMstBean.getDtoName() != null && trainingMstBean.getDtoName().contains(CPSConstants.HRDGBOARDDTO))
							trainingMstBean.setDtoName(trainingMstBean.getDtoName().replaceAll(CPSConstants.HRDGBOARDDTO, " Board Master"));
						
						if (trainingMstBean.getDtoName() != null && trainingMstBean.getDtoName().contains(CPSConstants.HRDGBOARDTYPEDTO))
							trainingMstBean.setDtoName(trainingMstBean.getDtoName().replaceAll(CPSConstants.HRDGBOARDTYPEDTO, " Board Type Master"));
					
						if (trainingMstBean.getDtoName() != null && trainingMstBean.getDtoName().contains(CPSConstants.HRDGBOARDMEMBERTYPEDTO))
							trainingMstBean.setDtoName(trainingMstBean.getDtoName().replaceAll(CPSConstants.HRDGBOARDMEMBERTYPEDTO, " Board Member Type Master"));
					
						if (trainingMstBean.getDtoName() != null && trainingMstBean.getDtoName().contains(CPSConstants.HRDGBOARDMEMBERDTO))
							trainingMstBean.setDtoName(trainingMstBean.getDtoName().replaceAll(CPSConstants.HRDGBOARDMEMBERDTO, " Board Member Master"));
					
						if (trainingMstBean.getDtoName() != null && trainingMstBean.getDtoName().contains(CPSConstants.TRAININGCIRCULATIONDTO))
							trainingMstBean.setDtoName(trainingMstBean.getDtoName().replaceAll(CPSConstants.TRAININGCIRCULATIONDTO, " Circulation Details"));
					
						if (trainingMstBean.getDtoName() != null && trainingMstBean.getDtoName().contains(CPSConstants.HRDGTXNREQUESTDTO))
							trainingMstBean.setDtoName(trainingMstBean.getDtoName().replaceAll(CPSConstants.HRDGTXNREQUESTDTO, " Training Request Details"));
						
						if (trainingMstBean.getDtoName() != null && trainingMstBean.getDtoName().contains(CPSConstants.COURSEDURATIONSDTO))
							trainingMstBean.setDtoName(trainingMstBean.getDtoName().replaceAll(CPSConstants.COURSEDURATIONSDTO, " Course Durations"));

										 
						 if (trainingMstBean.getDtoName() != null && trainingMstBean.getDtoName().contains(CPSConstants.COURSEDESIGNATIONSDTO))
								trainingMstBean.setDtoName(trainingMstBean.getDtoName().replaceAll(CPSConstants.COURSEDESIGNATIONSDTO, " Course Designations"));
						
						 if (trainingMstBean.getDtoName() != null && trainingMstBean.getDtoName().contains(CPSConstants.COURSEDISCIPLINESDTO))
								trainingMstBean.setDtoName(trainingMstBean.getDtoName().replaceAll(CPSConstants.COURSEDISCIPLINESDTO, " Course Disciplines"));
						
						 if (trainingMstBean.getDtoName() != null && trainingMstBean.getDtoName().contains(CPSConstants.COURSEQUALIFICATIONSDTO))
								trainingMstBean.setDtoName(trainingMstBean.getDtoName().replaceAll(CPSConstants.COURSEQUALIFICATIONSDTO, " Course Qualifications"));
						 
						 if (trainingMstBean.getDtoName() != null && trainingMstBean.getDtoName().contains(CPSConstants.COURSECONTENTDTO))
								trainingMstBean.setDtoName(trainingMstBean.getDtoName().replaceAll(CPSConstants.COURSECONTENTDTO, " Course Content"));
						
						 trainingMstBean.setDtoName(trainingMstBean.getDtoName().substring(0, trainingMstBean.getDtoName().length()-1));
					}
					}
		catch (Exception e) {
			throw e;
		}
		return trainingMstBean;
		
	}

	private String getTrainingTableDetails(String type) throws Exception {
		String message = "";
		try {
			if (CPSUtils.compareStrings(type, CPSConstants.TRAININGTYPE)) {
				return CPSConstants.TRAININGREGIONDTO + "#" + "trainingTypeId" + "#" + "status";
			} 
			else if (CPSUtils.compareStrings(type, CPSConstants.TRAININGREGIONMASTER)) {
				return CPSConstants.TRAININGINISTITUTEDTO + "#" + "trainingRegionId" + "#" + "status";
			}
			else if (CPSUtils.compareStrings(type, CPSConstants.TRAININGINISTITUTE)) {
				return CPSConstants.TRAININGVENUEDTO + "#" + "trainingInistituteId" + "#" + "status";
			} else if (CPSUtils.compareStrings(type, CPSConstants.TRAININGVENUE)) {
				return CPSConstants.TRAININGCIRCULATIONDTO + "#" + "venueId" + "#" + "status";
			} else if (CPSUtils.compareStrings(type, CPSConstants.COURSE)) {
				return CPSConstants.COURSEDURATIONSDTO + "#" + "courseId" + "#" + "status"+","+CPSConstants.COURSEDESIGNATIONSDTO + "#" + "courseId" + "#" + "status"+","+CPSConstants.COURSEDISCIPLINESDTO + "#" + "courseId" + "#" + "status"+","+CPSConstants.COURSEQUALIFICATIONSDTO + "#" + "courseId" + "#" + "status"+","+CPSConstants.COURSECONTENTDTO + "#" + "courseId" + "#" + "status";
			} else if (CPSUtils.compareStrings(type, CPSConstants.COURSEDURATION)) {
				return CPSConstants.TRAININGCIRCULATIONDTO + "#" + "durationId" + "#" + "status";
			}
			else if (CPSUtils.compareStrings(type, CPSConstants.HRDGBOARD)) {
				return CPSConstants.HRDGBOARDMEMBERDTO + "#" + "boardId" + "#" + "status"+","+CPSConstants.HRDGTXNREQUESTDTO + "#" + "boardId" + "#" + "status";
			}
			else if (CPSUtils.compareStrings(type, CPSConstants.HRDGBOARDTYPE)) {
				return CPSConstants.HRDGBOARDDTO + "#" + "boardTypeId" + "#" + "status";
			}
			else if (CPSUtils.compareStrings(type, CPSConstants.HRDGBOARDMEMBERTYPE)) {
				return CPSConstants.HRDGBOARDMEMBERDTO + "#" + "memberTypeId" + "#" + "status";
			}
//			else if (CPSUtils.compareStrings(type, CPSConstants.HRDGBOARDMEMBER)) {
//				return CPSConstants.HRDGTXNREQUESTDTO + "#" + "boardMemberId" + "#" + "status";
//			}

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public List getMasterData(String type) throws Exception {
		List list = null;
		try {
			list = trainingMasterDAO.getMasterData(getBeanName(type));
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public String checkMasterData(TrainingMasterBean trainingMstBean) throws Exception {
		String message = null;
		try {

			if (CPSUtils.checkList(trainingMasterDAO.checkMasterData(trainingMstBean))) {
				message = CPSConstants.DUPLICATE;
			} else {
				message = CPSConstants.SUCCESS;
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;

		}
		return message;

	}

	public String getMstName(String type) throws Exception {
		try {
			if (CPSUtils.compareStrings(type, CPSConstants.TRAININGTYPE))
				return CPSConstants.TRAININGTYPENAME;
			else if (CPSUtils.compareStrings(type, CPSConstants.TRAININGINISTITUTE))
				return CPSConstants.TRAININGINISTITUTENAME;
			else if (CPSUtils.compareStrings(type, CPSConstants.COURSESUBJCATEGORY))
				return CPSConstants.COURSESUBJCATEGORYNAME;
			else if (CPSUtils.compareStrings(type, CPSConstants.COURSESUBJSUBCATEGORY))
				return CPSConstants.COURSESUBJSUBCATEGORYNAME;
			else if (CPSUtils.compareStrings(type, CPSConstants.TRAININGVENUE))
				return CPSConstants.TRAININGVENUENAME;

		} catch (Exception e) {
			throw e;
		}
		return null;
	}

	public TrainingMasterBean getCourseList(TrainingMasterBean trainingMstBean) throws Exception {
		try {

			trainingMstBean.setMasterDataList(trainingMasterDAO.getCourseList(trainingMstBean));
		} catch (Exception e) {
			throw e;
		}
		return trainingMstBean;
	}

	public String manageCourse(TrainingMasterBean trainingMstBean) throws Exception {
		String message = null;
		try {

			trainingMstBean.setBeanName(getBeanName(trainingMstBean.getType()));
			trainingMstBean.setCreationDate(CPSUtils.getCurrentDate());
			trainingMstBean.setCreatedBy(trainingMstBean.getSfid());
			trainingMstBean.setStatus(1);
			if (CPSUtils.isNullOrEmpty(trainingMstBean.getId())) {
				if (CPSUtils.compareStrings(checkCourse(trainingMstBean), CPSConstants.SUCCESS)) {

					CourseDTO dto = new CourseDTO();
					BeanUtils.copyProperties(dto, trainingMstBean);

					message = commonDAO.save(dto);
					// List list = trainingMasterDAO.checkMasterData(trainingMstBean);
					// int courseId = (!CPSUtils.isNullOrEmpty(list))?((CourseDTO)list.get(0)).getId():0;
					// if(CPSUtils.compareStrings(message, CPSConstants.SUCCESS))
					// {
					// String[] designations = (!CPSUtils.isNullOrEmpty(trainingMstBean.getDesignation()))?trainingMstBean.getDesignation().split(","):null;
					// for(int i = 0;!CPSUtils.isNullOrEmpty(designations) && i < designations.length;i++)
					// {
					// CourseDesignationsDTO courseDesigDTO = new CourseDesignationsDTO();
					// BeanUtils.copyProperties(courseDesigDTO,trainingMstBean);
					// courseDesigDTO.setCourseId(courseId);
					// courseDesigDTO.setDesignationId(Integer.parseInt(designations[i]));
					// message = commonDAO.save(courseDesigDTO);
					// }
					// String[] disciplines = (!CPSUtils.isNullOrEmpty(trainingMstBean.getDiscipline()))?trainingMstBean.getDiscipline().split(","):null;
					// for(int i = 0;!CPSUtils.isNullOrEmpty(disciplines) && i < disciplines.length;i++)
					// {
					// CourseDisciplinesDTO courseDiscipDTO = new CourseDisciplinesDTO();
					// BeanUtils.copyProperties(courseDiscipDTO,trainingMstBean);
					// courseDiscipDTO.setCourseId(courseId);
					// courseDiscipDTO.setDisciplineId(Integer.parseInt(disciplines[i]));
					// message = commonDAO.save(courseDiscipDTO);
					//					    			  
					// }
					// }
				}

				else {
					message = CPSConstants.DUPLICATE;
				}

			} else {
				if (CPSUtils.compareStrings(checkCourse(trainingMstBean), CPSConstants.SUCCESS)) {
					message = trainingMasterDAO.updateCourse(trainingMstBean);
					// trainingMasterDAO.deleteCourse(trainingMstBean,CPSConstants.COURSEDESIGNATIONSDTO);
					// trainingMasterDAO.deleteCourse(trainingMstBean,CPSConstants.COURSEDISCIPLINESDTO);
					// int courseId = Integer.parseInt(trainingMstBean.getId());
					// if(CPSUtils.compareStrings(message, CPSConstants.UPDATE))
					// {
					//				    		 
					// String[] designations = (!CPSUtils.isNullOrEmpty(trainingMstBean.getDesignation()))?trainingMstBean.getDesignation().split(","):null;
					// for(int i = 0;!CPSUtils.isNullOrEmpty(designations) && i < designations.length;i++)
					// {
					// CourseDesignationsDTO courseDesigDTO = new CourseDesignationsDTO();
					// BeanUtils.copyProperties(courseDesigDTO,trainingMstBean);
					// courseDesigDTO.setCourseId(courseId);
					// courseDesigDTO.setDesignationId(Integer.parseInt(designations[i]));
					// commonDAO.save(courseDesigDTO);
					// }
					//				    		  
					// String[] disciplines = (!CPSUtils.isNullOrEmpty(trainingMstBean.getDiscipline()))?trainingMstBean.getDiscipline().split(","):null;
					// for(int i = 0;!CPSUtils.isNullOrEmpty(disciplines) && i < disciplines.length;i++)
					// {
					// CourseDisciplinesDTO courseDiscipDTO = new CourseDisciplinesDTO();
					// BeanUtils.copyProperties(courseDiscipDTO,trainingMstBean);
					// courseDiscipDTO.setCourseId(courseId);
					// courseDiscipDTO.setDisciplineId(Integer.parseInt(disciplines[i]));
					// commonDAO.save(courseDiscipDTO);
					//				    			  
					// }
					// }
					//						
				} else {
					message = CPSConstants.DUPLICATE;
				}

			}

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String checkCourse(TrainingMasterBean trainingMstBean) throws Exception {
		String message = null;
		try {

			if (CPSUtils.checkList(trainingMasterDAO.checkCourse(trainingMstBean))) {
				message = CPSConstants.DUPLICATE;
			} else {
				message = CPSConstants.SUCCESS;
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;

		}
		return message;

	}

	public TrainingMasterBean getCourseDurationList(TrainingMasterBean trainingMstBean) throws Exception {

		List list = null;
		try {
			trainingMstBean.setMasterDataList(trainingMasterDAO.getCourseDurationList(trainingMstBean));
		} catch (Exception e) {
			throw e;
		}
		return trainingMstBean;
	}

	public String manageCourseDuration(TrainingMasterBean trainingMstBean) throws Exception {
		String message = null;
		try {

			trainingMstBean.setBeanName(getBeanName(trainingMstBean.getType()));
			trainingMstBean.setCreationDate(CPSUtils.getCurrentDate());
			trainingMstBean.setCreatedBy(trainingMstBean.getSfid());
			trainingMstBean.setStatus(1);
			if (CPSUtils.isNullOrEmpty(trainingMstBean.getId())) {
				if (CPSUtils.compareStrings(checkCourseDuration(trainingMstBean), CPSConstants.SUCCESS)) {

					CourseDurationsDTO dto = new CourseDurationsDTO();
					BeanUtils.copyProperties(dto, trainingMstBean);

					message = commonDAO.save(dto);

				}

				else {
					message = CPSConstants.DUPLICATE;
				}

			} else {
				if (CPSUtils.compareStrings(checkCourseDuration(trainingMstBean), CPSConstants.SUCCESS)) {
					message = trainingMasterDAO.updateCourseDuration(trainingMstBean);

				} else {
					message = CPSConstants.DUPLICATE;
				}

			}

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String checkCourseDuration(TrainingMasterBean trainingMstBean) throws Exception {
		String message = null;
		try {

			if (CPSUtils.checkList(trainingMasterDAO.checkCourseDuration(trainingMstBean))) {
				message = CPSConstants.DUPLICATE;
			} else {
				message = CPSConstants.SUCCESS;
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;

		}
		return message;

	}

	public String manageHRDGBoard(TrainingMasterBean trainingMstBean) throws Exception {
		String message = null;
		try {

			trainingMstBean.setBeanName(getBeanName(trainingMstBean.getType()));
			trainingMstBean.setCreationDate(CPSUtils.getCurrentDate());
			trainingMstBean.setCreatedBy(trainingMstBean.getSfid());
			trainingMstBean.setStatus(1);
			if (CPSUtils.isNullOrEmpty(trainingMstBean.getId())) {
				if (CPSUtils.compareStrings(checkHRDGBoard(trainingMstBean), CPSConstants.SUCCESS)) {

					HRDGBoardDTO dto = new HRDGBoardDTO();
					BeanUtils.copyProperties(dto, trainingMstBean);

					message = commonDAO.save(dto);

				}

				else {
					message = CPSConstants.DUPLICATE;
				}

			} else {
				if (CPSUtils.compareStrings(checkHRDGBoard(trainingMstBean), CPSConstants.SUCCESS)) {
					message = trainingMasterDAO.updateHRDGBoard(trainingMstBean);

				} else {
					message = CPSConstants.DUPLICATE;
				}

			}

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String checkHRDGBoard(TrainingMasterBean trainingMstBean) throws Exception {
		String message = null;
		try {

			if (CPSUtils.checkList(trainingMasterDAO.checkHRDGBoard(trainingMstBean))) {
				message = CPSConstants.DUPLICATE;
			} else {
				message = CPSConstants.SUCCESS;
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;

		}
		return message;

	}

	public TrainingMasterBean getHRDGBoardList(TrainingMasterBean trainingMstBean) throws Exception {

		List list = null;
		try {
			trainingMstBean.setMasterDataList(trainingMasterDAO.getHRDGBoardList(trainingMstBean));
		} catch (Exception e) {
			throw e;
		}
		return trainingMstBean;
	}

	public String manageHRDGBoardMember(TrainingMasterBean trainingMstBean) throws Exception {
		String message = null;
		try {

			trainingMstBean.setBeanName(getBeanName(trainingMstBean.getType()));
			trainingMstBean.setCreationDate(CPSUtils.getCurrentDate());
			trainingMstBean.setCreatedBy(trainingMstBean.getSfid());
			trainingMstBean.setStatus(1);
			message = commonDAO.checkEmployee(trainingMstBean.getBoardMemberSfid());
			if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
			if (CPSUtils.isNullOrEmpty(trainingMstBean.getId())) {
				if (CPSUtils.compareStrings(checkHRDGBoardMember(trainingMstBean), CPSConstants.SUCCESS)) {

					HRDGBoardMemberDTO dto = new HRDGBoardMemberDTO();
					BeanUtils.copyProperties(dto, trainingMstBean);
					dto.setSfid(trainingMstBean.getBoardMemberSfid());

					message = commonDAO.save(dto);

				}

				else {
					message = CPSConstants.DUPLICATE;
				}

			} else {
				if (CPSUtils.compareStrings(checkHRDGBoardMember(trainingMstBean), CPSConstants.SUCCESS)) {
					message = trainingMasterDAO.updateHRDGBoardMember(trainingMstBean);

				} else {
					message = CPSConstants.DUPLICATE;
				}

			}
			}

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public List getTrainingRegionDataList(TrainingMasterBean trainingMstBean) throws Exception {

		List list = null;
		try {
			list = trainingMasterDAO.getTrainingRegionDataList(trainingMstBean);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public String manageTrainingRegion(TrainingMasterBean trainingMstBean) throws Exception {
		String message = null;
		try {

			trainingMstBean.setBeanName(getBeanName(trainingMstBean.getType()));
			trainingMstBean.setCreationDate(CPSUtils.getCurrentDate());
			trainingMstBean.setCreatedBy(trainingMstBean.getSfid());
			trainingMstBean.setStatus(1);
			if (CPSUtils.isNullOrEmpty(trainingMstBean.getId())) {
				if (CPSUtils.compareStrings(checkTrainingRegion(trainingMstBean), CPSConstants.SUCCESS)) {

					TrainingRegionDTO dto = new TrainingRegionDTO();
					BeanUtils.copyProperties(dto, trainingMstBean);

					message = commonDAO.save(dto);

				}

				else {
					message = CPSConstants.DUPLICATE;
				}

			} else {
				if (CPSUtils.compareStrings(checkTrainingRegion(trainingMstBean), CPSConstants.SUCCESS)) {
					message = trainingMasterDAO.updateTrainingRegion(trainingMstBean);

				} else {
					message = CPSConstants.DUPLICATE;
				}

			}

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String checkTrainingRegion(TrainingMasterBean trainingMstBean) throws Exception {
		String message = null;
		try {

			if (CPSUtils.checkList(trainingMasterDAO.checkTrainingRegion(trainingMstBean))) {
				message = CPSConstants.DUPLICATE;
			} else {
				message = CPSConstants.SUCCESS;
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;

		}
		return message;

	}

	public String checkHRDGBoardMember(TrainingMasterBean trainingMstBean) throws Exception {
		String message = null;
		try {

			if (CPSUtils.checkList(trainingMasterDAO.checkHRDGBoardMember(trainingMstBean))) {
				message = CPSConstants.DUPLICATE;
			} else {
				message = CPSConstants.SUCCESS;
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;

		}
		return message;

	}

	public TrainingMasterBean getHRDGBoardMemberList(TrainingMasterBean trainingMstBean) throws Exception {

		List list = null;
		try {
			trainingMstBean.setMasterDataList(trainingMasterDAO.getHRDGBoardMemberList(trainingMstBean));
		} catch (Exception e) {
			throw e;
		}
		return trainingMstBean;
	}

	public List getCourseListBasedonDuration(TrainingMasterBean trainingMstBean) throws Exception {
		List list = null;
		try {
			list = trainingMasterDAO.getCourseListBasedonDuration(trainingMstBean);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public String manageTrainingCirculation(TrainingMasterBean trainingMstBean) throws Exception {
		String message = null;
		try {

			trainingMstBean.setBeanName(getBeanName(trainingMstBean.getType()));
			trainingMstBean.setCreationDate(CPSUtils.getCurrentDate());
			trainingMstBean.setCreatedBy(trainingMstBean.getSfid());
			trainingMstBean.setStatus(1);
			//trainingMstBean.setCirculationDate(CPSUtils.getCurrentDate());
			if (CPSUtils.isNullOrEmpty(trainingMstBean.getId())) {
				//if (CPSUtils.compareStrings(checkTrainingCirculation(trainingMstBean), CPSConstants.SUCCESS)) {

					TrainingCirculationDTO dto = new TrainingCirculationDTO();
					BeanUtils.copyProperties(dto, trainingMstBean);

					message = commonDAO.save(dto);

					List list = trainingMasterDAO.getTrainingCirculationList(trainingMstBean);
					int cirId = (CPSUtils.checkList(list)) ? ((TrainingCirculationDTO) list.get(0)).getId() : 0;

					// trainingMstBean.getUploadBean().setRefId(cirId);
					// trainingMstBean.getUploadBean().setRefType(Integer.parseInt(CPSConstants.TRAININGCIRCULATIONTYPE));
					// // trainingMstBean.getUploadBean().setName(trainingMstBean.getBrochureName());
					// fileDAO.saveFile(trainingMstBean.getUploadBean(),cirId);
              
					
					
					updateIONLetterNumberDetails(trainingMstBean);
					
					if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
						String[] depts = (!CPSUtils.isNullOrEmpty(trainingMstBean.getDepartment())) ? trainingMstBean.getDepartment().split(",") : null;
						for (int i = 0; !CPSUtils.isNullOrEmpty(depts) && i < depts.length; i++) {
							TrainingCirculationDeptDTO tcDTO = new TrainingCirculationDeptDTO();
							BeanUtils.copyProperties(tcDTO, trainingMstBean);
							tcDTO.setCirculationId(cirId);
							tcDTO.setDepartmentId(Integer.parseInt(depts[i]));
							message = commonDAO.save(tcDTO);
							IonDeptDTO ionDto = new IonDeptDTO();
							ionDto.setIonId(Integer.parseInt(trainingMstBean.getIonId()));
							ionDto.setDepartmentId(Integer.parseInt(depts[i]));
							ionDto.setCopyStatus(0);
							commonDAO.save(ionDto);
							
						}
						submitAlert(trainingMstBean, depts, cirId);

					}
//
//				}
//
//				else {
//					message = CPSConstants.DUPLICATE;
//				}

			} else {
				//if (CPSUtils.compareStrings(checkTrainingCirculation(trainingMstBean), CPSConstants.SUCCESS)) {
					
					message = trainingMasterDAO.updateTrainingCirculation(trainingMstBean);
					trainingMasterDAO.deleteCourse(trainingMstBean, CPSConstants.TRAININGCIRCULATIONDEPTDTO);
					letterNumberFormatDAO.deleteIONDeptDetails(trainingMstBean.getIonId(),"IonDeptDTO","","");
					int cirId = Integer.parseInt(trainingMstBean.getId());
					
					
					
					if (CPSUtils.compareStrings(message, CPSConstants.UPDATE)) {

						String[] depts = (!CPSUtils.isNullOrEmpty(trainingMstBean.getDepartment())) ? trainingMstBean.getDepartment().split(",") : null;
						for (int i = 0; !CPSUtils.isNullOrEmpty(depts) && i < depts.length; i++) {
							TrainingCirculationDeptDTO tcDTO = new TrainingCirculationDeptDTO();
							BeanUtils.copyProperties(tcDTO, trainingMstBean);
							tcDTO.setCirculationId(cirId);
							tcDTO.setDepartmentId(Integer.parseInt(depts[i]));
							commonDAO.save(tcDTO);
							IonDeptDTO ionDto = new IonDeptDTO();
							ionDto.setIonId(Integer.parseInt(trainingMstBean.getIonId()));
							ionDto.setDepartmentId(Integer.parseInt(depts[i]));
							ionDto.setCopyStatus(0);
							commonDAO.save(ionDto);
						}
						updateIONLetterNumberDetails(trainingMstBean);
						submitAlert(trainingMstBean, depts, cirId);

					}

//				} else {
//					message = CPSConstants.DUPLICATE;
//				}

			}

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	private void updateIONLetterNumberDetails(TrainingMasterBean trainingMstBean) throws Exception{
		IONDTO idto = null;
		List list = null;
		try
		{
			list = trainingMasterDAO.getCirculationDetailsForION(trainingMstBean);
			if(CPSUtils.checkList(list))
			{
				Map obj = (HashMap)list.get(0);   
				idto = new IONDTO();
				
				idto.setSubject(obj.get("DAYS").toString()+" days Course on '"+obj.get("COURSENAME").toString()+"' During "+
						obj.get("STARTDATE").toString()+" to "+obj.get("ENDDATE").toString()+" at "+obj.get("ADDRESS").toString());//((obj.get("SHORTNAME") != null)?obj.get("SHORTNAME").toString():obj.get("TRAININGINISTITUTE").toString())+","+obj.get("CITY").toString());
				idto.setContent("1.      Kindly find enclosed copy of brochure on Course on "+obj.get("COURSENAME").toString()+
						" being conducted by "+obj.get("TRAININGINISTITUTE").toString().replaceAll("\n","")+((obj.get("SHORTNAME") != null)
							    ?"("+obj.get("SHORTNAME").toString()+")":"")+", during "+obj.get("STARTDATE").toString()+" to "+
							    obj.get("ENDDATE").toString()+" at "+obj.get("ADDRESS").toString().replaceAll("  ","").replaceAll("\n"," ")+"."+"<br/><br/>2.       Kindly send your nomination to HRDG by "+obj.get("NOMINATIONLASTDATE").toString()+".");
				idto.setEnclosers("(As above)");
				idto.setIonInitiatedSfid(obj.get("EMPSFID").toString());
				idto.setIonInitiatedRoleId(obj.get("HRROLEID").toString());
				idto.setCirculationStatus(5);
				letterNumberFormatDAO.updateIONdetailsForLetter(idto,trainingMstBean.getIonId());
				letterNumberFormatDAO.deleteIONDeptDetails(trainingMstBean.getIonId(),"IonFileDTO","","");
				
				IonFileDTO dto = new IonFileDTO();
				dto.setIonId(Integer.parseInt(trainingMstBean.getIonId()));
				dto.setFileId(Integer.parseInt(obj.get("BROCHURE").toString()));
				commonDAO.save(dto);
				
			}
			
			
		}
		catch(Exception e)
		{
			throw e;
		}
		
	}

	public String checkTrainingCirculation(TrainingMasterBean trainingMstBean) throws Exception {
		String message = null;
		try {

			if (CPSUtils.checkList(trainingMasterDAO.getTrainingCirculationList(trainingMstBean))) {
				message = CPSConstants.DUPLICATE;
			} else {
				message = CPSConstants.SUCCESS;
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;

		}
		return message;

	}

	public void submitAlert(TrainingMasterBean trainingMstBean, String[] depts, int cirId) throws Exception {

		String message = null;
		List deptHeadSfidList = null;
		try {
			deptHeadSfidList = trainingMasterDAO.getDeptHeadSfidList(depts, cirId);
			for (int i = 0; CPSUtils.checkList(deptHeadSfidList) && i < deptHeadSfidList.size(); i++) {
				AlertMessageDTO alertMessageDTO = new AlertMessageDTO();
				alertMessageDTO.setAlertMessage(CPSConstants.HRDGTRAININGCIRCULATION);
				alertMessageDTO.setAssignedFrom(trainingMstBean.getSfid());
				alertMessageDTO.setAssignedDate(CPSUtils.getCurrentDateWithTime());
				alertMessageDTO.setAssignedIpAddress(trainingMstBean.getIpAddress());
				alertMessageDTO.setAssignedTo(((EmployeeBean) deptHeadSfidList.get(i)).getSfid());
				alertMessageDTO.setAlertID(Integer.valueOf(CPSConstants.TRAININGALERTID));
				alertMessageDTO.setStatus(Integer.valueOf(CPSConstants.STATUSPENDING));
				alertMessageDTO.setReferenceID(cirId);
				alertMessageDTO.setVenueID(1);

				trainingAlertDAO.submitAlert(alertMessageDTO);
			}

		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;

		}

	}
	public void updateAlert(TrainingMasterBean trainingMstBean, String[] depts, int cirId) throws Exception {

		String message = null;
		List deptHeadSfidList = null;
		try {
			deptHeadSfidList = trainingMasterDAO.getDeptHeadSfidList(depts, cirId);
			for (int i = 0; CPSUtils.checkList(deptHeadSfidList) && i < deptHeadSfidList.size(); i++) {
				AlertMessageDTO alertMessageDTO = new AlertMessageDTO();
				alertMessageDTO.setAlertMessage(CPSConstants.HRDGTRAININGCIRCULATION);
				alertMessageDTO.setAssignedFrom(trainingMstBean.getSfid());
				alertMessageDTO.setAssignedDate(CPSUtils.getCurrentDateWithTime());
				alertMessageDTO.setAssignedIpAddress(trainingMstBean.getIpAddress());
				alertMessageDTO.setAssignedTo(((EmployeeBean) deptHeadSfidList.get(i)).getSfid());
				alertMessageDTO.setAlertID(Integer.valueOf(CPSConstants.TRAININGALERTID));
				alertMessageDTO.setStatus(Integer.valueOf(CPSConstants.STATUSPENDING));
				alertMessageDTO.setReferenceID(cirId);
				alertMessageDTO.setVenueID(1);

				trainingAlertDAO.submitAlert(alertMessageDTO);
			}

		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;

		}

	}

	public FilesBean getBrochure(int id) throws Exception {
		List list = null;
		FilesBean file = null;
		try {
			file = fileDAO.downloadFromDatabase(id);
		} catch (Exception e) {
			throw e;
		}
		return file;
	}

	public TrainingMasterBean getTrainingInistituteDataList(TrainingMasterBean trainingMstBean) throws Exception {

		try {
			trainingMstBean.setMasterDataList(trainingMasterDAO.getTrainingInistituteDataList(trainingMstBean));
		} catch (Exception e) {
			throw e;
		}
		return trainingMstBean;
	}

	public List getTrainingVenueDataList(TrainingMasterBean trainingMstBean) throws Exception {
		List list = null;
		try {
			list = trainingMasterDAO.getTrainingVenueDataList(trainingMstBean);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public TrainingMasterBean getCourseDisciplineList(TrainingMasterBean trainingMstBean) throws Exception {

		List list = null;
		try {
			trainingMstBean.setMasterDataList(trainingMasterDAO.getCourseDisciplineList(trainingMstBean));
		} catch (Exception e) {
			throw e;
		}
		return trainingMstBean;
	}

	public String manageCourseDiscipline(TrainingMasterBean trainingMstBean) throws Exception {
		String message = null;
		try {

			trainingMstBean.setBeanName(getBeanName(trainingMstBean.getType()));
			trainingMstBean.setCreationDate(CPSUtils.getCurrentDate());
			trainingMstBean.setCreatedBy(trainingMstBean.getSfid());
			trainingMstBean.setStatus(1);

			if (CPSUtils.compareStrings(checkCourseDiscipline(trainingMstBean), CPSConstants.SUCCESS)) {

				int courseId = (!CPSUtils.isNullOrEmpty(trainingMstBean.getCourseId())) ? Integer.parseInt(trainingMstBean.getCourseId()) : 0;
				JSONObject mainJson = new JSONObject(trainingMstBean.getDiscipline());
				for (int i = 0; i < mainJson.length(); i++) {
					JSONObject subJson = (JSONObject) mainJson.get(String.valueOf(i));

					CourseDisciplinesDTO courseDiscipDTO = new CourseDisciplinesDTO();
					BeanUtils.copyProperties(courseDiscipDTO, trainingMstBean);
					courseDiscipDTO.setCourseId(courseId);
					courseDiscipDTO.setDisciplineId(Integer.parseInt(subJson.get("id").toString()));
					message = commonDAO.save(courseDiscipDTO);
				}

			} else {
				
				message = trainingMasterDAO.deleteCourse(trainingMstBean, CPSConstants.COURSEDISCIPLINESDTO);
				int courseId = (!CPSUtils.isNullOrEmpty(trainingMstBean.getCourseId())) ? Integer.parseInt(trainingMstBean.getCourseId()) : 0;
				
				JSONObject mainJson = new JSONObject(trainingMstBean.getDiscipline());
				for (int i = 0; i < mainJson.length(); i++) {
					JSONObject subJson = (JSONObject) mainJson.get(String.valueOf(i));

					CourseDisciplinesDTO courseDiscipDTO = new CourseDisciplinesDTO();
					BeanUtils.copyProperties(courseDiscipDTO, trainingMstBean);
					courseDiscipDTO.setCourseId(courseId);
					courseDiscipDTO.setDisciplineId(Integer.parseInt(subJson.get("id").toString()));
					courseDiscipDTO.setLastModifiedBy(trainingMstBean.getSfid());
					courseDiscipDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					message = commonDAO.save(courseDiscipDTO);
				}
				if(CPSUtils.compareStrings(message, CPSConstants.SUCCESS))
					message = CPSConstants.UPDATE;

			}

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	private String checkCourseDiscipline(TrainingMasterBean trainingMstBean) throws Exception {
		String message = null;
		try {

			if (CPSUtils.checkList(trainingMasterDAO.checkCourseDiscipline(trainingMstBean))) {
				message = CPSConstants.DUPLICATE;
			} else {
				message = CPSConstants.SUCCESS;
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;

		}
		return message;
	}

	public List<DisciplineDTO> getSelectedDisciplineList(TrainingMasterBean trainingMstBean) throws Exception {
		List<DisciplineDTO> list = null;
		try {
			list = trainingMasterDAO.getSelectedDisciplineList(trainingMstBean);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public void removeList(List<DisciplineDTO> disciplineList, List<DisciplineDTO> selectedDisciplineList) {
		HashMap hm = new HashMap();
		try {
			for (int i = 0; CPSUtils.checkList(selectedDisciplineList) && i < selectedDisciplineList.size(); i++) {
				hm.put(selectedDisciplineList.get(i).getId(), selectedDisciplineList.get(i));
			}
			for (int i = 0; CPSUtils.checkList(disciplineList) && i < disciplineList.size(); i++) {
				if (hm.get(disciplineList.get(i).getId()) != null) {
					disciplineList.remove(i);
				}

			}
			disciplineList = disciplineList;
		} catch (Exception e) {

		}
	}

	public List<QualificationDTO> getSelectedQualificationList(TrainingMasterBean trainingMstBean) throws Exception {

		List<QualificationDTO> list = null;
		try {
			list = trainingMasterDAO.getSelectedQualificationList(trainingMstBean);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public String manageCourseQualification(TrainingMasterBean trainingMstBean) throws Exception {
		String message = null;
		try {

			trainingMstBean.setBeanName(getBeanName(trainingMstBean.getType()));
			trainingMstBean.setCreationDate(CPSUtils.getCurrentDate());
			trainingMstBean.setCreatedBy(trainingMstBean.getSfid());
			trainingMstBean.setStatus(1);

			if (CPSUtils.compareStrings(checkCourseQualification(trainingMstBean), CPSConstants.SUCCESS)) {

				int courseId = (!CPSUtils.isNullOrEmpty(trainingMstBean.getCourseId())) ? Integer.parseInt(trainingMstBean.getCourseId()) : 0;
				JSONObject mainJson = new JSONObject(trainingMstBean.getQualification());
				for (int i = 0; i < mainJson.length(); i++) {
					JSONObject subJson = (JSONObject) mainJson.get(String.valueOf(i));

					CourseQualificationsDTO courseDiscipDTO = new CourseQualificationsDTO();
					BeanUtils.copyProperties(courseDiscipDTO, trainingMstBean);
					courseDiscipDTO.setCourseId(courseId);
					courseDiscipDTO.setQualificationId(Integer.parseInt(subJson.get("id").toString()));
					message = commonDAO.save(courseDiscipDTO);
				}

			} else {
				message = trainingMasterDAO.deleteCourse(trainingMstBean, CPSConstants.COURSEQUALIFICATIONSDTO);
				
				int courseId = (!CPSUtils.isNullOrEmpty(trainingMstBean.getCourseId())) ? Integer.parseInt(trainingMstBean.getCourseId()) : 0;

				JSONObject mainJson = new JSONObject(trainingMstBean.getQualification());
				for (int i = 0; i < mainJson.length(); i++) {
					JSONObject subJson = (JSONObject) mainJson.get(String.valueOf(i));

					CourseQualificationsDTO courseDiscipDTO = new CourseQualificationsDTO();
					BeanUtils.copyProperties(courseDiscipDTO, trainingMstBean);
					courseDiscipDTO.setCourseId(courseId);
					courseDiscipDTO.setQualificationId(Integer.parseInt(subJson.get("id").toString()));
					courseDiscipDTO.setLastModifiedBy(trainingMstBean.getSfid());
					courseDiscipDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					message = commonDAO.save(courseDiscipDTO);
				}
				if(CPSUtils.compareStrings(message, CPSConstants.SUCCESS))
					message = CPSConstants.UPDATE;

			}

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public List getCourseQualificationList(TrainingMasterBean trainingMstBean) throws Exception {
		List list = null;
		try {
			list = trainingMasterDAO.getCourseQualificationList(trainingMstBean);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	private String checkCourseQualification(TrainingMasterBean trainingMstBean) throws Exception {
		String message = null;
		try {

			if (CPSUtils.checkList(trainingMasterDAO.checkCourseQualification(trainingMstBean))) {
				message = CPSConstants.DUPLICATE;
			} else {
				message = CPSConstants.SUCCESS;
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;

		}
		return message;
	}

	public List<QualificationDTO> getDesignationList(TrainingMasterBean trainingMstBean) throws Exception {
		List list = null;
		try {
			list = trainingMasterDAO.getDesignationList(trainingMstBean);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public List getCourseDesignationList(TrainingMasterBean trainingMstBean) throws Exception {
		List list = null;
		try {
			list = trainingMasterDAO.getCourseDesignationList(trainingMstBean);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public List getSelectedDesignationList(TrainingMasterBean trainingMstBean) throws Exception {
		List list = null;
		try {
			list = trainingMasterDAO.getSelectedDesignationList(trainingMstBean);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	public String manageCourseDesignation(TrainingMasterBean trainingMstBean) throws Exception {
		String message = null;
		try {

			trainingMstBean.setBeanName(getBeanName(trainingMstBean.getType()));
			trainingMstBean.setCreationDate(CPSUtils.getCurrentDate());
			trainingMstBean.setCreatedBy(trainingMstBean.getSfid());
			trainingMstBean.setStatus(1);

			if (CPSUtils.compareStrings(checkCourseDesignation(trainingMstBean), CPSConstants.SUCCESS)) {

				int courseId = (!CPSUtils.isNullOrEmpty(trainingMstBean.getCourseId())) ? Integer.parseInt(trainingMstBean.getCourseId()) : 0;
				JSONObject mainJson = new JSONObject(trainingMstBean.getDesignation());
				for (int i = 0; i < mainJson.length(); i++) {
					JSONObject subJson = (JSONObject) mainJson.get(String.valueOf(i));

					CourseDesignationsDTO courseDiscipDTO = new CourseDesignationsDTO();
					BeanUtils.copyProperties(courseDiscipDTO, trainingMstBean);
					courseDiscipDTO.setCourseId(courseId);
					courseDiscipDTO.setDesignationId(Integer.parseInt(subJson.get("id").toString()));
					message = commonDAO.save(courseDiscipDTO);
				}

			} else {
				message = trainingMasterDAO.deleteCourse(trainingMstBean, CPSConstants.COURSEDESIGNATIONSDTO);
				int courseId = (!CPSUtils.isNullOrEmpty(trainingMstBean.getCourseId())) ? Integer.parseInt(trainingMstBean.getCourseId()) : 0;
				
				JSONObject mainJson = new JSONObject(trainingMstBean.getDesignation());
				for (int i = 0; i < mainJson.length(); i++) {
					JSONObject subJson = (JSONObject) mainJson.get(String.valueOf(i));

					CourseDesignationsDTO courseDiscipDTO = new CourseDesignationsDTO();
					BeanUtils.copyProperties(courseDiscipDTO, trainingMstBean);
					courseDiscipDTO.setCourseId(courseId);
					courseDiscipDTO.setDesignationId(Integer.parseInt(subJson.get("id").toString()));
					courseDiscipDTO.setLastModifiedBy(trainingMstBean.getSfid());
					courseDiscipDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					message = commonDAO.save(courseDiscipDTO);
				}
				if(CPSUtils.compareStrings(message, CPSConstants.SUCCESS))
					message = CPSConstants.UPDATE;

			}

		} catch (Exception e) {
			throw e;
		}
		return message;
	}
	private String checkCourseDesignation(TrainingMasterBean trainingMstBean) throws Exception {
		String message = null;
		try {

			if (CPSUtils.checkList(trainingMasterDAO.checkCourseDesignation(trainingMstBean))) {
				message = CPSConstants.DUPLICATE;
			} else {
				message = CPSConstants.SUCCESS;
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;

		}
		return message;
	}

	public String manageCourseContent(TrainingMasterBean trainingMstBean) throws Exception {
		String message = null;
		try {

			trainingMstBean.setBeanName(getBeanName(trainingMstBean.getType()));
			trainingMstBean.setCreationDate(CPSUtils.getCurrentDate());
			trainingMstBean.setCreatedBy(trainingMstBean.getSfid());
			trainingMstBean.setStatus(1);

			if (CPSUtils.compareStrings(checkCourseContent(trainingMstBean), CPSConstants.SUCCESS)) {

				int courseId = (!CPSUtils.isNullOrEmpty(trainingMstBean.getCourseId())) ? Integer.parseInt(trainingMstBean.getCourseId()) : 0;
				CourseContentDTO dto = new CourseContentDTO();
				BeanUtils.copyProperties(dto, trainingMstBean);

				message = commonDAO.save(dto);
				
			} else {
				List list =trainingMasterDAO.checkCourseContent(trainingMstBean);
				trainingMstBean.setId((CPSUtils.checkList(list)) ?String.valueOf(((CourseContentDTO) list.get(0)).getId()) : "0");
				message = trainingMasterDAO.updateCourseContent(trainingMstBean);
				if(CPSUtils.isNullOrEmpty(trainingMstBean.getCourseContent().replaceAll("\n", "").replaceAll(" ", "")) && CPSUtils.compareStrings(message, CPSConstants.UPDATE))
					message = CPSConstants.DELETE;
			}

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	private String checkCourseContent(TrainingMasterBean trainingMstBean) throws Exception{
		String message = null;
		try {

			if (CPSUtils.checkList(trainingMasterDAO.checkCourseContent(trainingMstBean))) {
				message = CPSConstants.DUPLICATE;
			} else {
				message = CPSConstants.SUCCESS;
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;

		}
		return message;
	}

	public TrainingMasterBean getCourseContent(TrainingMasterBean trainingMstBean) throws Exception {
		List list = null;
		try {
			list = trainingMasterDAO.getCourseContent(trainingMstBean);
			if(CPSUtils.checkList(list))
			{
				trainingMstBean.setCourseContent(((CourseContentDTO)list.get(0)).getCourseContent());
			}
			
		} catch (Exception e) {
			throw e;
		}
		return trainingMstBean;
	}

	public TrainingMasterBean setCirculationDetails(TrainingMasterBean trainingMstBean) throws Exception {
		List list = null;
		try {
			list = trainingMasterDAO.getCirculationDetails(trainingMstBean);
			if(CPSUtils.checkList(list))
			{
				CourseDurationsDTO dto = (CourseDurationsDTO)list.get(0);
				trainingMstBean.setDurationId(String.valueOf(dto.getId()));
				trainingMstBean.setCourseId(String.valueOf(dto.getCourseId()));
				trainingMstBean.setCourse(dto.getCourse());
				trainingMstBean.setDurationStartDate(dto.getStartDate());
				trainingMstBean.setDurationEndDate(dto.getEndDate());
				trainingMstBean.setId(null);
				trainingMstBean.setNominationStartDate(CPSUtils.getCurrentDate());
				trainingMstBean.setNominationEndDate(dto.getStartDate());
				trainingMstBean.setCirculationDate(CPSUtils.getCurrentDate());
				if(CPSUtils.checkList(dto.getTrainingCirculationDto()))
				{
					TrainingCirculationDTO cirDto = (TrainingCirculationDTO)(dto.getTrainingCirculationDto().get(0));
					trainingMstBean.setId(String.valueOf(cirDto.getId()));
					trainingMstBean.setVenueId(String.valueOf(cirDto.getVenueId()));
					trainingMstBean.setNominationStartDate(cirDto.getNominationStartDate());
					trainingMstBean.setNominationEndDate(cirDto.getNominationEndDate());
					trainingMstBean.setOrganizer(cirDto.getOrganizer());
					trainingMstBean.setBrochure(String.valueOf(cirDto.getBrochure()));
					trainingMstBean.setBrochureName(String.valueOf(cirDto.getFileBean().getFilename()));
					trainingMstBean.setCirculationDate(String.valueOf(cirDto.getCirculationDate()));
					trainingMstBean.setMasterDataList(cirDto.getTrainingCirculationDepts());
					trainingMstBean.setIonId((cirDto.getIonId()!= null)?cirDto.getIonId().toString():null);
					trainingMstBean.setLetterFormatId(cirDto.getLetterFormatId());
					trainingMstBean.setIonList(cirDto.getIonList());
				}
				
			}
			
		} catch (Exception e) {
			throw e;
		}
		return trainingMstBean;	
	}
	public void removeLists(List list, List selectedList,String str) {
		HashMap hm = new HashMap();
		
		try {
			
			for (int i = 0; CPSUtils.checkList(selectedList) && i < selectedList.size(); i++) {
				int id = 0;
				if(CPSUtils.compareStrings(str, "TrainingCirculationMaster"))
					id = ((DepartmentsDTO)selectedList.get(i)).getId();
				if(CPSUtils.compareStrings(str, "discipline"))
					id = ((DisciplineDTO)selectedList.get(i)).getId();
				if(CPSUtils.compareStrings(str, "qualification"))
					id = ((QualificationDTO)selectedList.get(i)).getId();
				
				hm.put(id, selectedList.get(i));
			}
			for (int i = list.size()-1; CPSUtils.checkList(list) && i >= 0; i--) {
				int id = 0;
				if(CPSUtils.compareStrings(str, "TrainingCirculationMaster"))
					id = ((DepartmentsDTO)list.get(i)).getId();
				else if(CPSUtils.compareStrings(str, "discipline"))
					id = ((DisciplineDTO)list.get(i)).getId();
				else if(CPSUtils.compareStrings(str, "qualification"))
					id = ((QualificationDTO)list.get(i)).getId();
				if (hm.get(id) != null) {
					list.remove(i);
				} 

			}
			list = list;
		} catch (Exception e) {

		}
	}

	public List getTrainingVenueDetails(TrainingMasterBean trainingMstBean) throws Exception {
		List list = null;
		try {
			list = trainingMasterDAO.getTrainingVenueDetails(trainingMstBean);
						
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public List getDepartmentList() throws Exception {
		List list = null;
		try {
			list = trainingMasterDAO.getDepartmentList();
						
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public TrainingMasterBean getAttendedCourseList(TrainingMasterBean trainingMstBean) throws Exception {
		List list = null;
		try {
			trainingMstBean.setMasterDataList(trainingMasterDAO.getAttendedCourseList(trainingMstBean));
						
		} catch (Exception e) {
			throw e;
		}
		return trainingMstBean;
	}

	public String manageCourseAttended(TrainingMasterBean trainingMstBean) throws Exception {
		String message = null;
		String courseName = null;
		
		String sfid = null;
		try
		{
					
			trainingMstBean.setCreationDate(CPSUtils.getCurrentDate());
			trainingMstBean.setCreatedBy(trainingMstBean.getSfid());
			trainingMstBean.setStatus(1);
			message = commonDAO.checkEmployee(trainingMstBean.getNomineeSfid());
			if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
			if (CPSUtils.isNullOrEmpty(trainingMstBean.getId())) {
				//if (CPSUtils.compareStrings(trainingMasterDAO.checkAttendedCoursedetails(trainingMstBean), CPSConstants.SUCCESS)) {

					CourseAttendedDetailsDTO dto = new CourseAttendedDetailsDTO();
					BeanUtils.copyProperties(dto, trainingMstBean);

					message = commonDAO.save(dto);


			} else {
				//if (CPSUtils.compareStrings(trainingMasterDAO.checkAttendedCoursedetails(trainingMstBean), CPSConstants.SUCCESS)) {

				message = trainingMasterDAO.updateCourseAttendeddetails(trainingMstBean);
								
			
			}

		}

	} catch (Exception e) {
		throw e;
	}
			
		return message;
	}

	public String deleteCourseAttended(TrainingMasterBean trainingMstBean) throws Exception{
		String message = null;
		try
		{
			message = trainingMasterDAO.deleteCourseAttended(trainingMstBean);
		}
		catch (Exception e) {
			throw e;
		}
				
			return message;
		
	}
	public TrainingMasterBean getEmpDetails(TrainingMasterBean trainingMstBean) throws Exception
	{
		List list = null;
		try
		{
			trainingMstBean.setEmpDetails(trainingMasterDAO.getEmpDetails(trainingMstBean.getSfid()));
		}
		catch(Exception e)
		{
			throw e;
		}
		return trainingMstBean;
	}

	public List getIONMstList(TrainingMasterBean trainingMstBean) throws Exception{
		List list = null;
		try
		{
			list = trainingMasterDAO.getIONMstList(trainingMstBean);
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}

	public List getSeriesMstList() throws Exception{
		List list = null;
		try
		{
			list = trainingMasterDAO.getSeriesMstList();
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}

	public String manageHRDGBoardType(TrainingMasterBean trainingMstBean) throws Exception{
		String message = null;
		try {

			trainingMstBean.setBeanName(getBeanName(trainingMstBean.getType()));
			trainingMstBean.setCreationDate(CPSUtils.getCurrentDate());
			trainingMstBean.setCreatedBy(trainingMstBean.getSfid());
			trainingMstBean.setStatus(1);
			if (CPSUtils.isNullOrEmpty(trainingMstBean.getId())) {
				if (CPSUtils.compareStrings(checkMasterData(trainingMstBean), CPSConstants.SUCCESS)) {

					HRDGBoardTypeDTO dto = new HRDGBoardTypeDTO();
					BeanUtils.copyProperties(dto, trainingMstBean);

					message = commonDAO.save(dto);
					
				}

				else {
					message = CPSConstants.DUPLICATE;
				}

			} else {
				if (CPSUtils.compareStrings(checkMasterData(trainingMstBean), CPSConstants.SUCCESS)) {
					message = trainingMasterDAO.update(trainingMstBean);
									
				} else {
					message = CPSConstants.DUPLICATE;
				}

			}

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String manageHRDGBoardMemberType(TrainingMasterBean trainingMstBean) throws Exception{
		String message = null;
		try {

			trainingMstBean.setBeanName(getBeanName(trainingMstBean.getType()));
			trainingMstBean.setCreationDate(CPSUtils.getCurrentDate());
			trainingMstBean.setCreatedBy(trainingMstBean.getSfid());
			trainingMstBean.setStatus(1);
			if (CPSUtils.isNullOrEmpty(trainingMstBean.getId())) {
				if (CPSUtils.compareStrings(checkMasterData(trainingMstBean), CPSConstants.SUCCESS)) {

					HRDGBoardMemberTypeDTO dto = new HRDGBoardMemberTypeDTO();
					BeanUtils.copyProperties(dto, trainingMstBean);

					message = commonDAO.save(dto);
					
				}

				else {
					message = CPSConstants.DUPLICATE;
				}

			} else {
				if (CPSUtils.compareStrings(checkMasterData(trainingMstBean), CPSConstants.SUCCESS)) {
					message = trainingMasterDAO.update(trainingMstBean);
									
				} else {
					message = CPSConstants.DUPLICATE;
				}

			}

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	

	
	
	

	
	

}
