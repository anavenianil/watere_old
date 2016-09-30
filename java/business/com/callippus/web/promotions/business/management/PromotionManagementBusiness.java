package com.callippus.web.promotions.business.management;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.empPayment.IEmployeePaymentDAO;
import com.callippus.web.paybill.dto.PayScaleDesignationDTO;
import com.callippus.web.promotions.beans.management.PromotionManagementBean;
import com.callippus.web.promotions.dao.management.IPromotionManagementDAO;
import com.callippus.web.promotions.dto.AssessmentCategoryDTO;
import com.callippus.web.promotions.dto.BoardMappingDTO;
import com.callippus.web.promotions.dto.BoardMasterDTO;
import com.callippus.web.promotions.dto.DesignationExperienceDTO;
import com.callippus.web.promotions.dto.ExceptionalEmpDTO;
import com.callippus.web.promotions.dto.ExternalEmpDTO;
import com.callippus.web.promotions.dto.PromoteesEntryDTO;
import com.callippus.web.promotions.dto.PromotionsDisciplineDTO;
import com.callippus.web.promotions.dto.PromotionsSubDisciplineDTO;
import com.callippus.web.promotions.dto.ResidencyPeriodDTO;
import com.callippus.web.promotions.dto.VenueDetailsDTO;

@Service
public class PromotionManagementBusiness {
	@Autowired
	private IPromotionManagementDAO promotionManagementDAO;
	@Autowired
	private IComonObjectDAO commonDAO;
	@Autowired
	private IEmployeePaymentDAO empPaymentDAO;
	

	public PromotionManagementBean getResidencyHomeDetails(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setAssessmentCategoryList(promotionManagementDAO.getAssessmentCategoryList());
			promotionBean.setAssessmentTypeList(promotionManagementDAO.getAssessmentTypeList());
			// promotionBean.setDesignationList(promotionManagementDAO.getDesignationList());
			promotionBean = promotionManagementDAO.getTypeDesignationList(promotionBean);
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}
	
	//start getBoardTypeInformation
		public List<AssessmentCategoryDTO> getBoardTypeInfoList(PromotionManagementBean promotionBean) throws Exception {
			try {
				promotionBean.setAssessmentCategoryList(promotionManagementDAO.getBoardTypeInfoList(promotionBean));
			} catch (Exception e) {
				throw e;
			}
			return promotionBean.getAssessmentCategoryList();
		}
		
		//end getBoardTypeInformation
	

	public List<ResidencyPeriodDTO> getResidencyPeriodList(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setResidencyPeriodList(promotionManagementDAO.getResidencyPeriodList(promotionBean));
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResidencyPeriodList();
	}

	public String manageResidencyPeriodDetails(PromotionManagementBean promotionBean) throws Exception {
		try {
			// check duplicate
			promotionBean.setResult(promotionManagementDAO.checkDuplicate(promotionBean));

			if (!CPSUtils.compareStrings(promotionBean.getResult(), CPSConstants.DUPLICATE)) {
				ResidencyPeriodDTO residencyPeriodDTO = new ResidencyPeriodDTO();
				if (!CPSUtils.isNullOrEmpty(promotionBean.getNodeID())) {
					// New
					residencyPeriodDTO.setId(Integer.valueOf(promotionBean.getNodeID()));
				}
				residencyPeriodDTO.setAssessmentTypeID(promotionBean.getAssessmentTypeID());
				residencyPeriodDTO.setAssessmentCategoryID(promotionBean.getAssessmentCategoryID());
				residencyPeriodDTO.setDesignationFrom(promotionBean.getDesignationFrom());
				residencyPeriodDTO.setDesignationTo(promotionBean.getDesignationTo());
				residencyPeriodDTO.setResidencyPeriod(promotionBean.getResidencyPeriod());
				residencyPeriodDTO.setRelaxationInMonths(promotionBean.getRelaxationInMonths());
				residencyPeriodDTO.setDateFrom(promotionBean.getDateFrom());
				residencyPeriodDTO.setDateTo(promotionBean.getDateTo());
				residencyPeriodDTO.setWritten(promotionBean.getWritten().intValue());
				residencyPeriodDTO.setTrade(promotionBean.getTrade().intValue());
				residencyPeriodDTO.setInterview(promotionBean.getInterview().intValue());
				residencyPeriodDTO.setBoard(promotionBean.getBoard().intValue());
				residencyPeriodDTO.setStatus(1);
				residencyPeriodDTO.setCreatedBy(promotionBean.getSfID());
				residencyPeriodDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
				residencyPeriodDTO.setLastModifiedBy(promotionBean.getSfID());
				residencyPeriodDTO.setLastModifiedTime(residencyPeriodDTO.getCreationTime());

				promotionBean.setResult(promotionManagementDAO.submitResidencyPeriodDetails(residencyPeriodDTO));
			}
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}
	// start storeBoardTypeInfo
public String storeBoardTypeInfo(PromotionManagementBean promotionBean)throws Exception{
		
		try{
			// check duplicate
			
			promotionBean.setResult(promotionManagementDAO.checkDuplicateBoard(promotionBean));
			if (!CPSUtils.compareStrings(promotionBean.getResult(), CPSConstants.DUPLICATE)) {
				//NEW
				AssessmentCategoryDTO assessmentCategoryDTO=new AssessmentCategoryDTO();
				if (!CPSUtils.isNullOrEmpty(promotionBean.getNodeID())) {
					assessmentCategoryDTO.setId(Integer.valueOf(promotionBean.getNodeID()));
				}	
    	assessmentCategoryDTO.setCategory(promotionBean.getBoardType().toUpperCase());
		//assessmentCategoryDTO.setCategory(promotionBean.getBoardType());
		System.out.println(assessmentCategoryDTO.getCategory());
		assessmentCategoryDTO.setStatus(1);
		assessmentCategoryDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
		assessmentCategoryDTO.setCreatedBy(promotionBean.getSfID());
		assessmentCategoryDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
		assessmentCategoryDTO.setLastModifiedBy(promotionBean.getSfID());
		promotionBean.setResult(promotionManagementDAO.storeBoardTypeInfo(assessmentCategoryDTO));
		 }
		}catch (Exception e){
			throw e;
		}
		return promotionBean.getResult();	
	}

	
	//end boarTypeInfo(P)

	public String deleteResidencyPeriodDetails(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setResult(promotionManagementDAO.deleteResidencyPeriodDetails(promotionBean));
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}

	//start deleteBoardTypeInfo(promotionBean)
		public String deleteBoardTypeInfo(PromotionManagementBean promotionBean) throws Exception {
			try {
				promotionBean.setResult(promotionManagementDAO.deleteBoardTypeInfo(promotionBean));
			} catch (Exception e) {
				throw e;
			}
			return promotionBean.getResult();
		}
		//end deleteBoardTypeInfo(promotionBean)
	
	@SuppressWarnings("unchecked")
	public PromotionManagementBean getVenueHomeDetails(PromotionManagementBean promotionBean) throws Exception {
		try {
			if(promotionBean.getType().equals("venueHome") || promotionBean.getType().equals("QualifiedCandidates") ) {
    		promotionBean.setAssessmentCategoryList(promotionManagementDAO.getAssessmentCategoryList());
    		promotionBean.setYearList(commonDAO.getMasterData(CPSConstants.YEARTYPEDTO));
    		promotionBean.setAssessmentTypeList(promotionManagementDAO.getAssessmentTypeList());
			}
			else {
			promotionBean = promotionManagementDAO.getOptionCertificateHome(promotionBean);
		if(!CPSUtils.checkList(promotionBean.getAssessmentDetails())) {
				promotionBean.setType("option1");
				promotionBean = promotionManagementDAO.getOptionCertificateHome(promotionBean);
			}
			}
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	public List<VenueDetailsDTO> getVenueList(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setVenueList(promotionManagementDAO.getVenueList(promotionBean));
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getVenueList();
	}

	public String manageVenueDetails(PromotionManagementBean promotionBean) throws Exception {
		try {
			// check duplicate
			promotionBean.setResult(promotionManagementDAO.checkDuplicateVenue(promotionBean));

			if (!CPSUtils.compareStrings(promotionBean.getResult(), CPSConstants.DUPLICATE)) {
				VenueDetailsDTO venueDetailsDTO = new VenueDetailsDTO();
				if (!CPSUtils.isNullOrEmpty(promotionBean.getNodeID())) {
					// New
					venueDetailsDTO.setId(Integer.valueOf(promotionBean.getNodeID()));
				}
				venueDetailsDTO.setCategoryId(promotionBean.getAssessmentTypeID());
				venueDetailsDTO.setAssessmentCategoryID(promotionBean.getAssessmentCategoryID());
				venueDetailsDTO.setYearID(promotionBean.getYearID());
				venueDetailsDTO.setCenter(promotionBean.getCenter().toUpperCase());
				venueDetailsDTO.setCoOrdinator(promotionBean.getCoOrdinator().toUpperCase());
				venueDetailsDTO.setCoOrdinatorLab(promotionBean.getCoOrdinatorLab().toUpperCase());
				venueDetailsDTO.setAddress(promotionBean.getAddress().toUpperCase());
				venueDetailsDTO.setContactAddress(promotionBean.getContactAddress().toUpperCase());
				venueDetailsDTO.setVenue(promotionBean.getVenue().toUpperCase());
				venueDetailsDTO.setStatus(1);
				venueDetailsDTO.setCreatedBy(promotionBean.getSfID());
				venueDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
				venueDetailsDTO.setLastModifiedBy(promotionBean.getSfID());
				venueDetailsDTO.setLastModifiedTime(venueDetailsDTO.getCreationTime());

				promotionBean.setResult(promotionManagementDAO.submitVenueDetails(venueDetailsDTO));
			}
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}

	public String deleteVenueDetails(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setResult(promotionManagementDAO.deleteVenueDetails(promotionBean));
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}

	@SuppressWarnings("unchecked")
	public PromotionManagementBean getAddAssessmentHomeDetails(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setYearList(commonDAO.getMasterData(CPSConstants.YEARTYPEDTO));
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	public List<ExceptionalEmpDTO> getAddAssessmentEmpList(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setExceptionalEmp(promotionManagementDAO.getAddAssessmentEmpList(promotionBean));
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getExceptionalEmp();
	}

	public String manageExceptionEmpDetails(PromotionManagementBean promotionBean) throws Exception {
		try {
			// Check sfid
			promotionBean.setResult(commonDAO.checkEmployee(promotionBean.getExpEmp()));
			if (CPSUtils.compareStrings(promotionBean.getResult(), CPSConstants.SUCCESS)) {
				// check duplicate
				promotionBean.setResult(promotionManagementDAO.checkDuplicateExp(promotionBean));

				if (!CPSUtils.compareStrings(promotionBean.getResult(), CPSConstants.DUPLICATE)) {
					ExceptionalEmpDTO exceptionalEmpDTO = new ExceptionalEmpDTO();

					if (!CPSUtils.isNullOrEmpty(promotionBean.getNodeID())) {
						// New
						exceptionalEmpDTO.setId(Integer.valueOf(promotionBean.getNodeID()));
					}
					exceptionalEmpDTO.setYearID(promotionBean.getYearID());
					exceptionalEmpDTO.setSfID(promotionBean.getExpEmp());
					exceptionalEmpDTO.setDescription(promotionBean.getDescription());
					exceptionalEmpDTO.setStatus(1);
					exceptionalEmpDTO.setCreatedBy(promotionBean.getSfID());
					exceptionalEmpDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
					exceptionalEmpDTO.setLastModifiedBy(promotionBean.getSfID());
					exceptionalEmpDTO.setLastModifiedTime(exceptionalEmpDTO.getCreationTime());

					promotionBean.setResult(promotionManagementDAO.submitExpEmpDetails(exceptionalEmpDTO));
				}
			} else {
				promotionBean.setResult(CPSConstants.EMPNOTEXISTS);
			}

		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}

	public String deleteExceptionEmpDetails(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setResult(promotionManagementDAO.deleteExceptionEmpDetails(promotionBean));
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}

	@SuppressWarnings("unchecked")
	public PromotionManagementBean getLocalBoardHomeDetails(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setYearList(commonDAO.getMasterData(CPSConstants.YEARTYPEDTO));
			promotionBean.setDesignationList(promotionManagementDAO.getLocalMembersDesignationList());
			promotionBean.setLocalBoardMembersList(promotionManagementDAO.getLocalBoardMembers(promotionBean));
			promotionBean.setAssessmentTypeList(promotionManagementDAO.getAssessmentTypeList());
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	public String deleteLocalBoardDetails(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setResult(promotionManagementDAO.deleteLocalBoardDetails(promotionBean));
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}

	public String manageLocalBoardDetails(PromotionManagementBean promotionBean) throws Exception {
		try {
			// check duplicate
			promotionBean.setResult(promotionManagementDAO.checkDuplicateLocalBoard(promotionBean));

			if (!CPSUtils.compareStrings(promotionBean.getResult(), CPSConstants.DUPLICATE)) {
				BoardMasterDTO boardMasterDTO = new BoardMasterDTO();
				boardMasterDTO.setName(promotionBean.getBoardName());
				boardMasterDTO.setStatus(1);
				boardMasterDTO.setCreatedBy(promotionBean.getSfID());
				boardMasterDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
				boardMasterDTO.setLastModifiedBy(promotionBean.getSfID());
				boardMasterDTO.setLastModifiedTime(boardMasterDTO.getCreationTime());
				if (!CPSUtils.isNullOrEmpty(promotionBean.getNodeID())) {
					boardMasterDTO.setId(Integer.valueOf(promotionBean.getNodeID()));
				}
				boardMasterDTO.setId(promotionManagementDAO.submitBoardMasterDetails(boardMasterDTO));
				promotionBean.setBoardID(boardMasterDTO.getId());

				promotionBean.setResult(promotionManagementDAO.submitLocalBoardDetails(promotionBean));
			}
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}

	public List<BoardMappingDTO> getLocalAssessmentBoardList(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setLocalBoardMappingList(promotionManagementDAO.getLocalAssessmentBoardList(promotionBean));
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getLocalBoardMappingList();
	}

	// Desig Experience Start

	public PromotionManagementBean getDesigExperienceHomeDetails(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setDesignationList(promotionManagementDAO.getDesignationList());
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	public String manageDesigExperienceDetails(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean = promotionManagementDAO.getDesigName(promotionBean);
			EmployeeBean empBean = promotionBean.getEmpDetails();
			promotionBean.setResult(promotionManagementDAO.checkDuplicateDesigExp(promotionBean));
			if (!CPSUtils.compareStrings(promotionBean.getResult(), CPSConstants.DUPLICATE)) {
				DesignationExperienceDTO experienceDTO = new DesignationExperienceDTO();
				if (!CPSUtils.isNullOrEmpty(promotionBean.getNodeID())) {
					// Update
					experienceDTO.setId(Integer.valueOf(promotionBean.getNodeID()));
				}
				experienceDTO.setSfid(promotionBean.getRefSfid().toUpperCase());
				experienceDTO.setDesignationID(Integer.valueOf(empBean.getDesignation()));
				if (!CPSUtils.isNull(empBean.getSeniorityDate())) {
					experienceDTO.setStartDate(CPSUtils.convertStringToDate(CPSUtils.formattedDate(empBean.getSeniorityDate())));
				}
				experienceDTO.setStatus(1);
				experienceDTO.setNoOfAttempts(promotionBean.getDesigAttempts());
				promotionBean.setResult(promotionManagementDAO.submitDesigExperienceDetails(experienceDTO));
			} else {
				promotionBean.setResult(CPSConstants.DUPLICATE);
			}
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}

	public List<DesignationExperienceDTO> getDesigExperienceList(PromotionManagementBean promotionBean) throws Exception {
		List<DesignationExperienceDTO> list = null;
		try {
			list = promotionManagementDAO.getDesigExperienceList();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public String deleteDesigExperienceDetails(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setResult(promotionManagementDAO.deleteDesigExperienceDetails(promotionBean));
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}

	public String retrieveDesigName(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setResult(commonDAO.checkEmployee(promotionBean.getRefSfid()));
			if (CPSUtils.compareStrings(promotionBean.getResult(), CPSConstants.SUCCESS)) {
				promotionBean = promotionManagementDAO.getDesigName(promotionBean);
			}
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}

	// Desig Experience end

	@SuppressWarnings("unchecked")
	public PromotionManagementBean getQualifiedCandidatesHome(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setAssessmentCategoryList(promotionManagementDAO.getAssessmentCategoryList());
			promotionBean.setYearList(commonDAO.getMasterData(CPSConstants.YEARTYPEDTO));
			// promotionBean.setDesignationList(promotionManagementDAO.getDesignationList());
			promotionBean = promotionManagementDAO.getTypeDesignationList(promotionBean);
			promotionBean.setAssessmentTypeList(promotionManagementDAO.getAssessmentTypeList());
			if (CPSUtils.compareStrings(CPSConstants.QUALIFIEDCANDIDATESHOME, promotionBean.getParam())) {
				promotionBean.setVenueList(promotionManagementDAO.getVenueList(promotionBean));
				promotionBean.setBoardMasterList(promotionManagementDAO.getBoardMasterList(promotionBean));
				promotionBean.setDisciplineDetails(promotionManagementDAO.getDesciplineDetails(promotionBean));
			} else if (CPSUtils.compareStrings(CPSConstants.PROMOTEDCANDIDATESHOME, promotionBean.getParam())) {
				promotionBean.setReservationList(commonDAO.getMasterData(CPSConstants.RESERVATIONDTO));
			}else if(CPSUtils.compareStrings(CPSConstants.YEARWISEASSESSMENTLIST, promotionBean.getParam())){
				 promotionManagementDAO.getOptionCertificateHome(promotionBean);
			}
			promotionBean.setYearID(promotionManagementDAO.getCurrentYearID());
			// promotionBean.setDoPartList(administratorDAO.getdop);
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	@SuppressWarnings("unchecked")
	public PromotionManagementBean getQualifiedCandidates(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean = promotionManagementDAO.getQualifiedCandidates(promotionBean);
			promotionBean.setDisciplineDetails(promotionManagementDAO.getDesciplineDetails(promotionBean));// sub
																											// discipline
																											// details
			// promotionBean.setDisciplineList(promotionManagementDAO.getDisciplineList());//discipline
			// details
			promotionBean.setDisciplineList(commonDAO.getMasterData("PromotionsDisciplineDTO"));
			promotionBean.setMessage(CPSConstants.SEARCH);
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	public PromotionManagementBean getHQCandidates(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean = promotionManagementDAO.getHQCandidates(promotionBean);
			
			for(int i=0;i<= promotionBean.getAssessmentDetails().size()-1;i++ ){
			//System.out.println("Residency Id is-"+promotionBean.getAssessmentDetails().get(i).getResidencyPeriodId().intValue());
			}
			// subDiscipline List
			promotionBean.setDisciplineDetails(promotionManagementDAO.getCategorySubDisciplineList(promotionBean));
			// promotionBean.setDisciplineList(promotionManagementDAO.getDisciplineList());//discipline
			// details

			// DisciplineList
			promotionBean.setDisciplineList(promotionManagementDAO.getCategoryDisciplineList(promotionBean));
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	public PromotionManagementBean submitQualifiedCandidates(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean = promotionManagementDAO.submitQualifiedCandidates(promotionBean);
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	public PromotionManagementBean getPromotedCandidates(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean = promotionManagementDAO.getPromotedCandidates(promotionBean);

		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	public PromotionManagementBean getPayFixationDetails(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean = promotionManagementDAO.getPayFixationDetails(promotionBean);
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}
	public PromotionManagementBean getPayFixationDetailsWithOption(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean = promotionManagementDAO.getPromotedCandidatesWithoption(promotionBean);
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	public PromotionManagementBean submitPayFixationDetails(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean = promotionManagementDAO.submitPayFixationDetails(promotionBean);
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	public PromotionManagementBean getPayFixationDOPartDetails(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean = promotionManagementDAO.getPayFixationDOPartDetails(promotionBean);
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	public PromotionManagementBean getdesignationList(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean = promotionManagementDAO.getTypeDesignationList(promotionBean);
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	public PromotionManagementBean submitDiscipline(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setResult(promotionManagementDAO.checkDiscipline(promotionBean));
			if (!CPSUtils.compareStrings(promotionBean.getResult(), CPSConstants.DUPLICATE)) {
				PromotionsDisciplineDTO disciplineDTO = new PromotionsDisciplineDTO();

				if (!CPSUtils.isNullOrEmpty(promotionBean.getNodeID())) {
					// New
					disciplineDTO.setId(Integer.valueOf(promotionBean.getNodeID()));
					disciplineDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				}

				disciplineDTO.setName(promotionBean.getName());
				disciplineDTO.setDescription(promotionBean.getDescription());
				disciplineDTO.setStatus(1);
				disciplineDTO.setCreationDate(CPSUtils.getCurrentDate());
				disciplineDTO.setShortForm(promotionBean.getShortForm());
				disciplineDTO.setCategoryID(promotionBean.getAssessmentTypeID());
				promotionBean.setResult(promotionManagementDAO.submitDiscipline(disciplineDTO));
			}
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	@SuppressWarnings("unchecked")
	public PromotionManagementBean getDisciplineDetails(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setDisciplineList(commonDAO.getMasterData("PromotionsDisciplineDTO"));
			promotionBean.setAssessmentTypeList(promotionManagementDAO.getAssessmentTypeList());
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	@SuppressWarnings("unchecked")
	public List<PromotionsDisciplineDTO> getDisciplineList(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setDisciplineList(commonDAO.getMasterData("PromotionsDisciplineDTO"));
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getDisciplineList();
	}

	@SuppressWarnings("unchecked")
	public List<PromotionsSubDisciplineDTO> getSubDisciplineDetails(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setSubDisciplineList(commonDAO.getMasterData("PromotionsSubDisciplineDTO"));
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getSubDisciplineList();
	}

	public PromotionManagementBean submitSubDiscipline(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setResult(promotionManagementDAO.checkDiscipline(promotionBean));
			if (!CPSUtils.compareStrings(promotionBean.getResult(), CPSConstants.DUPLICATE)) {
				PromotionsSubDisciplineDTO subDisciplineDTO = new PromotionsSubDisciplineDTO();

				if (!CPSUtils.isNullOrEmpty(promotionBean.getNodeID())) {
					// Update
					subDisciplineDTO.setId(Integer.valueOf(promotionBean.getNodeID()));
					subDisciplineDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				}
				subDisciplineDTO.setDisciplineID(Integer.valueOf(promotionBean.getName()));
				subDisciplineDTO.setName(promotionBean.getSubName());
				subDisciplineDTO.setDescription(promotionBean.getDescription());
				subDisciplineDTO.setStatus(1);
				subDisciplineDTO.setCreationDate(CPSUtils.getCurrentDate());
				subDisciplineDTO.setShortForm(promotionBean.getShortForm());
				promotionBean.setResult(promotionManagementDAO.submitSubDiscipline(subDisciplineDTO));
			}
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;

	}

	public List<ExternalEmpDTO> getExternalBoardList(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setExternalEmpList(promotionManagementDAO.getExternalBoardList());
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getExternalEmpList();
	}

	public PromotionManagementBean submitExternalBoardMember(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setResult(promotionManagementDAO.checkDuplicateExtMember(promotionBean));
			if (!CPSUtils.compareStrings(promotionBean.getResult(), CPSConstants.DUPLICATE)) {
				ExternalEmpDTO externalEmpDTO = new ExternalEmpDTO();
				if (!CPSUtils.isNullOrEmpty(promotionBean.getNodeID())) {
					// Old
					externalEmpDTO.setId(Integer.valueOf(promotionBean.getNodeID()));
				}
				// new
				externalEmpDTO.setName(promotionBean.getName());
				externalEmpDTO.setDesignation(promotionBean.getDesigName());
				externalEmpDTO.setStatus(1);
				externalEmpDTO.setCreatedBy(promotionBean.getSfID());
				externalEmpDTO.setLastModifiedBy(promotionBean.getSfID());
				externalEmpDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
				externalEmpDTO.setLastModifiedTime(externalEmpDTO.getCreationTime());
				promotionBean.setResult(promotionManagementDAO.submitExternalMember(externalEmpDTO));
			}
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	public String deleteDiscipline(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setResult(promotionManagementDAO.deleteDiscipline(promotionBean));
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}

	public String deleteSubDiscipline(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setResult(promotionManagementDAO.deleteSubDiscipline(promotionBean));
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}

	public String deleteExternalBoardMember(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setResult(promotionManagementDAO.deleteExtBoardMember(promotionBean));
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}

	public PromotionManagementBean getOfflineEntryHomeDetails(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setDesignationList(promotionManagementDAO.getDesignationList());
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	public PromotionManagementBean submitOfflineEntry(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setResult(promotionManagementDAO.checkDuplicateOfflineEntry(promotionBean));
			if (!CPSUtils.compareStrings(promotionBean.getResult(), CPSConstants.DUPLICATE)) {
				promotionBean.setResult(promotionManagementDAO.saveOfflineEntry(promotionBean));
			} else if (CPSUtils.compareStrings(promotionBean.getResult(), CPSConstants.DUPLICATE)
					&& !CPSUtils.isNullOrEmpty(promotionBean.getNodeID())) {
				promotionBean.setResult(promotionManagementDAO.saveOfflineEntry(promotionBean));
			} else
				promotionBean.setResult(CPSConstants.DUPLICATE);
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;

	}

	public List<PromoteesEntryDTO> getOfflineEntryList(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setOfflineEntryList(promotionManagementDAO.getOfflineEntryList(promotionBean));
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getOfflineEntryList();
	}

	public String desigUpdation() throws Exception {
		String message = null;
		try {
			message = promotionManagementDAO.designationUpdation();

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public List<EmployeeBean> validateSFID(PromotionManagementBean promotionBean) throws Exception{
		
		List<EmployeeBean> empDetailsList = null;
		try{
			empDetailsList = empPaymentDAO.validateSFID(promotionBean.getRefSfid().toUpperCase());
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return empDetailsList;
	}
	
	public PromotionManagementBean retrieveFamilyCatDesigList(PromotionManagementBean promotionBean) throws Exception {
		try {
			//promotionBean.setResult(commonDAO.checkEmployee(promotionBean.getRefSfid()));
			
			
				promotionBean.setCatwiseDesig(promotionManagementDAO.getCatwiseDesig(promotionBean.getRefSfid()));
				
				promotionBean.setResult("");
				//added for scale
				List<PayScaleDesignationDTO> arrList = null;
			
				arrList = empPaymentDAO.getPayScaleDetailsList();
				/*for(int i=0; i<arrList.size(); i++){
					PayScaleDesignationDTO pdtoDesignationDTO = arrList.get(i);
					pdtoDesignationDTO.setId(pdtoDesignationDTO.getDesignationDetails().getId());
					pdtoDesignationDTO.setName(pdtoDesignationDTO.getPaybandDetails().getName() + "-" + pdtoDesignationDTO.getPaybandDetails().getRangeFrom() + "-"
									+ pdtoDesignationDTO.getPaybandDetails().getRangeTo());
					pdtoDesignationDTO.setGradePay(pdtoDesignationDTO.getGradePay());
					
			
				}*/
				promotionBean.setEmpPayScaleList(arrList);
				
			
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}
	public String getVariableIncrementValue(PromotionManagementBean promotionBean) throws Exception {
		String varIncValue = "";
		try{
			if(promotionBean.getVarIncVal()!=null){
				 varIncValue = promotionManagementDAO.getVarIncr(Integer.parseInt(promotionBean.getNewGradePay()), promotionBean.getDesignationFrom());
				 varIncValue = varIncValue.split("@")[1];
			}	
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return varIncValue;
	}
	public String getBasicPayValue(PromotionManagementBean promotionBean) throws Exception {
		String basicValue = "";
		try{
			
			basicValue = promotionManagementDAO.getBasicPay(promotionBean);
				
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return basicValue;
	}

	@SuppressWarnings("unchecked")
	public PromotionManagementBean getSubDisciplineHomeList(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setDisciplineList(commonDAO.getMasterData("PromotionsDisciplineDTO"));
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	@SuppressWarnings("unchecked")
	public PromotionManagementBean getPublishDoPartHome(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setCasualitiesList(promotionManagementDAO.getModuleSpecificCasuality(promotionBean));
			promotionBean.setTypeList(commonDAO.getMasterData(CPSConstants.TYPEDETAILSDTO));
			if (CPSUtils.compareStrings(promotionBean.getParam(), "gazetted")) {
				promotionBean.setDoPartList(promotionManagementDAO.getTypeDoPartList(promotionBean.getGazettedType()));
			}
		} catch (Exception e) {
			throw e;
		}
 		return promotionBean;
	}

//	public PromotionManagementBean getGazettedTypeDo(PromotionManagementBean promotionBean) throws Exception {
//		try {
//			promotionBean.setDoPartList(promotionManagementDAO.getGazTypeDoPartList(promotionBean));
//		} catch (Exception e) {
//			throw e;
//		}
//		return promotionBean;
//	}

	public PromotionManagementBean getPublishedFixationDOPartDetails(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setAssessmentDetails(promotionManagementDAO.getPublishedDoList(promotionBean));
			promotionBean.setVarAssessmentDetails(promotionManagementDAO.getPublishedVarDoList(promotionBean));
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	public PromotionManagementBean submitFinanceAcceptanceDetails(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setResult(promotionManagementDAO.submitFinanceAcceptanceDetails(promotionBean));
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	public PromotionManagementBean deleteRecord(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setResult(promotionManagementDAO.deleteRecord(promotionBean));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return promotionBean;
	}

	@SuppressWarnings("unchecked")
	public PromotionManagementBean getTypeMasterDetails(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setTypeList(commonDAO.getMasterData(CPSConstants.TYPEDETAILSDTO));
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	public PromotionManagementBean getDoPartList(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean.setDoPartList(promotionManagementDAO.getGazTypeDoPartList(promotionBean));
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	public PromotionManagementBean getPromotionCasualitiesList(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean = promotionManagementDAO.getpromotionCasualityList(promotionBean);
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
		
	}

	public PromotionManagementBean getEmpDetails(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean=promotionManagementDAO.getEmpDetails(promotionBean);
			promotionBean.setPayBillRunMonth(commonDAO.getPaybillRunmonth());
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}
 ////
	
	public PromotionManagementBean submitCandidatesOption(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean = promotionManagementDAO.submitCandidatesOption(promotionBean);
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}
	public PromotionManagementBean getPayFixationPayDetails(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean = promotionManagementDAO.getPromotedCandidatesPayData(promotionBean);
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}
	public PromotionManagementBean submitPromotionPayDetails(PromotionManagementBean promotionBean) throws Exception {
		try {
			promotionBean = promotionManagementDAO.submitPromotionPayUpdate(promotionBean);
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}
	
	
	
	
	
	
	
	//
}
