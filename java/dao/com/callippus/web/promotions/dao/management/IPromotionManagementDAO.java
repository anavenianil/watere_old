package com.callippus.web.promotions.dao.management;

import java.util.List;

import com.callippus.web.beans.dto.CasualityDetailsDTO;
import com.callippus.web.beans.dto.CategoryDTO;
import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.DoPartDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.promotions.beans.management.PromotionManagementBean;
import com.callippus.web.promotions.beans.management.PromotionOfflineEntryBean;
import com.callippus.web.promotions.dto.AssessmentCategoryDTO;
import com.callippus.web.promotions.dto.AssessmentDetailsDTO;
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

public interface IPromotionManagementDAO {
	
	//boardMaster starts
	public String storeBoardTypeInfo(AssessmentCategoryDTO assessmentCategoryDTO)throws Exception;
	public List<AssessmentCategoryDTO> getBoardTypeInfoList(PromotionManagementBean promotionBean) throws Exception;
	public String deleteBoardTypeInfo(PromotionManagementBean promotionBean) throws Exception;
	public String checkDuplicateBoard(PromotionManagementBean promotionBean) throws Exception;
	
	//boardMaster end

	public List<AssessmentCategoryDTO> getAssessmentCategoryList() throws Exception;

	public List<CategoryDTO> getAssessmentTypeList() throws Exception;

	public List<DesignationDTO> getDesignationList() throws Exception;

	public List<ResidencyPeriodDTO> getResidencyPeriodList(PromotionManagementBean promotionBean) throws Exception;

	public String checkDuplicate(PromotionManagementBean promotionBean) throws Exception;

	public String submitResidencyPeriodDetails(ResidencyPeriodDTO residencyPeriodDTO) throws Exception;

	public String deleteResidencyPeriodDetails(PromotionManagementBean promotionBean) throws Exception;

	public List<VenueDetailsDTO> getVenueList(PromotionManagementBean promotionBean) throws Exception;

	public List<BoardMasterDTO> getBoardMasterList(PromotionManagementBean promotionBean) throws Exception;

	public String checkDuplicateVenue(PromotionManagementBean promotionBean) throws Exception;

	public String deleteVenueDetails(PromotionManagementBean promotionBean) throws Exception;

	public String submitVenueDetails(VenueDetailsDTO venueDetails) throws Exception;

	public List<ExceptionalEmpDTO> getAddAssessmentEmpList(PromotionManagementBean promotionBean) throws Exception;

	public String deleteExceptionEmpDetails(PromotionManagementBean promotionBean) throws Exception;

	public String checkDuplicateExp(PromotionManagementBean promotionBean) throws Exception;

	public String submitExpEmpDetails(ExceptionalEmpDTO exceptionalEmpDTO) throws Exception;

	public List<KeyValueDTO> getLocalBoardMembers(PromotionManagementBean promotionBean) throws Exception;

	public String deleteLocalBoardDetails(PromotionManagementBean promotionBean) throws Exception;

	public String checkDuplicateLocalBoard(PromotionManagementBean promotionBean) throws Exception;

	public String submitLocalBoardDetails(PromotionManagementBean promotionBean) throws Exception;

	public List<DesignationDTO> getLocalMembersDesignationList() throws Exception;

	public int submitBoardMasterDetails(BoardMasterDTO boardMasterDTO) throws Exception;

	public List<BoardMappingDTO> getLocalAssessmentBoardList(PromotionManagementBean promotionBean) throws Exception;

	public String submitDesigExperienceDetails(DesignationExperienceDTO experienceDTO) throws Exception;

	public List<DesignationExperienceDTO> getDesigExperienceList() throws Exception;

	public String checkDuplicateDesigExp(PromotionManagementBean promotionBean) throws Exception;

	public String deleteDesigExperienceDetails(PromotionManagementBean promotionBean) throws Exception;

	public PromotionManagementBean getQualifiedCandidates(PromotionManagementBean promotionBean) throws Exception;

	public PromotionManagementBean submitQualifiedCandidates(PromotionManagementBean promotionBean) throws Exception;

	public PromotionManagementBean getDesigName(PromotionManagementBean promotionBean) throws Exception;

	public PromotionManagementBean getPromotedCandidates(PromotionManagementBean promotionBean) throws Exception;

	public PromotionManagementBean getPayFixationDetails(PromotionManagementBean promotionBean) throws Exception;
	
	public PromotionManagementBean getPromotedCandidatesWithoption(PromotionManagementBean promotionBean) throws Exception;
	
	

	public PromotionManagementBean submitPayFixationDetails(PromotionManagementBean promotionBean) throws Exception;

	public String changeStatus(String referenceID, int status) throws Exception;

	public PromotionManagementBean getPayFixationDOPartDetails(PromotionManagementBean promotionBean) throws Exception;

	public PromotionManagementBean getHQCandidates(PromotionManagementBean promotionBean) throws Exception;

	public List<PromotionsSubDisciplineDTO> getDesciplineDetails(PromotionManagementBean promotionBean) throws Exception;

	public PromotionManagementBean getTypeDesignationList(PromotionManagementBean promotionBean) throws Exception;

	public int getCurrentYearID() throws Exception;

	public String submitDiscipline(PromotionsDisciplineDTO disciplineDTO) throws Exception;

	public String checkDiscipline(PromotionManagementBean promotionBean) throws Exception;

	public String submitSubDiscipline(PromotionsSubDisciplineDTO subDisciplineDTO) throws Exception;

	public String submitExternalMember(ExternalEmpDTO externalEmpDTO) throws Exception;

	public String checkDuplicateExtMember(PromotionManagementBean promotionBean) throws Exception;

	public String deleteDiscipline(PromotionManagementBean promotionBean) throws Exception;

	public String deleteSubDiscipline(PromotionManagementBean promotionBean) throws Exception;

	public String deleteExtBoardMember(PromotionManagementBean promotionBean) throws Exception;

	public List<ExternalEmpDTO> getExternalBoardList() throws Exception;

	public String saveOfflineEntry(PromotionManagementBean promotionBean) throws Exception;

	public List<PromoteesEntryDTO> getOfflineEntryList(PromotionManagementBean promotionBean) throws Exception;

	public String checkDuplicateOfflineEntry(PromotionManagementBean promotionBean) throws Exception;

	public String designationUpdation() throws Exception;

	public List<PromotionOfflineEntryBean> getCatwiseDesig(String refSfid) throws Exception;

	public List<PromotionsDisciplineDTO> getCategoryDisciplineList(PromotionManagementBean promotionBean) throws Exception;

	public List<PromotionsSubDisciplineDTO> getCategorySubDisciplineList(PromotionManagementBean promotionBean) throws Exception;

	public List<DoPartDTO> getTypeDoPartList(String gazettedType) throws Exception;

	public List<CasualityDetailsDTO> getModuleSpecificCasuality(PromotionManagementBean promotionBean) throws Exception;

	public List<DoPartDTO> getGazTypeDoPartList(PromotionManagementBean promotionBean) throws Exception;

	public List<AssessmentDetailsDTO> getPublishedDoList(PromotionManagementBean promotionBean) throws Exception;

	public List<AssessmentDetailsDTO> getPublishedVarDoList(PromotionManagementBean promotionBean) throws Exception;

	public String submitFinanceAcceptanceDetails(PromotionManagementBean promotionBean) throws Exception;

	public String deleteRecord(PromotionManagementBean promotionBean) throws Exception;

	public PromotionManagementBean getpromotionCasualityList(PromotionManagementBean promotionBean) throws Exception;

	public PromotionManagementBean getEmpDetails(PromotionManagementBean promotionBean) throws Exception;
	//
	public PromotionManagementBean submitCandidatesOption(PromotionManagementBean promotionBean) throws Exception;
	//
	public String getVarIncr(int gradepay, int varIncrPts) throws Exception;
	public String getBasicPay(PromotionManagementBean promotionBean) throws Exception;

	public PromotionManagementBean getPromotedCandidatesPayData(PromotionManagementBean promotionBean) throws Exception;
	
	public PromotionManagementBean submitPromotionPayUpdate(PromotionManagementBean promotionBean) throws Exception;
	
	

	public PromotionManagementBean getOptionCertificateHome(PromotionManagementBean promotionBean) throws Exception;
	

}
