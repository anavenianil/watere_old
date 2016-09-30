package com.callippus.web.business.master;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.AwardDTO;
import com.callippus.web.beans.dto.LetterNumberReferenceDTO;
import com.callippus.web.beans.dto.PinNumberDTO;
import com.callippus.web.beans.master.MasterDataBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.master.IMasterDataDAO;

@Service
public class MasterDataBusiness {
	
	private static Log log = LogFactory.getLog(MasterDataBusiness.class);
	@Autowired
	private IMasterDataDAO masterDataDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;

	public String manageMasterData(MasterDataBean masterBean) throws Exception {
		String message = null;
		try {
			log.debug("::MasterDataBusiness>>>>>>>>>>>>manageMasterData(-,-) Start>>>>>>>>>>>>>>");
			if (CPSUtils.isNullOrEmpty(masterBean.getId())) {
				masterBean.setBeanName(getBeanName(masterBean.getType()));
				String tableName = getTableName(masterBean.getType());
				masterBean.setTableName(tableName);
				if(CPSUtils.compareStrings(masterBean.getType(), CPSConstants.PIN)){
					masterBean.setParam(CPSConstants.PIN);
				    message = masterDataDAO.checkName(masterBean);
					if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)){
				    masterBean.setParam(CPSConstants.SFID);					
					message = masterDataDAO.checkName(masterBean);
					}
					if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)){
					message = masterDataDAO.validSfid(masterBean);	
					}
				}else{
					message = masterDataDAO.checkName(masterBean);	
				}					
				if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
					//masterBean.setId(Integer.valueOf(commonDataDAO.getTableID(tableName,CPSConstants.TYPE)).toString());
					//masterBean.setId((Integer.valueOf(commonDataDAO.getID(tableName)).toString()));
					message = masterDataDAO.createMasterData(masterBean);
				}
			} else {
				masterBean.setTableName(getTableName(masterBean.getType()));
					message = masterDataDAO.checkName(masterBean);
					if(CPSUtils.compareStrings(masterBean.getParam(), CPSConstants.SFID)){
					if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)){
					    masterBean.setParam(CPSConstants.SFID);					
						message = masterDataDAO.checkName(masterBean);
						message = masterDataDAO.validSfid(masterBean);	
						}	
					}

				if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
					message = masterDataDAO.updateMasterData(masterBean);
				}
			}

		} catch (Exception e) {
			throw e;
		}
		log.debug("::MasterDataBusiness>>>>>>>>>>>>manageMasterData(-,-) End>>>>>>>>>>>>>>");
		return message;
	}
	public String deleteMasterData(MasterDataBean masterBean) throws Exception {
		String message = null;
		boolean status = true;
		try {
			log.debug("::MasterDataBusiness>>>>>>>>>>>>deleteMasterData(-,-) Start>>>>>>>>>>>>>>");
			masterBean.setTableName(getTableName(masterBean.getType()));

			message = getTableDetails(masterBean.getType());

			if (!CPSUtils.isNullOrEmpty(message)) {
				String[] record = message.split(",");
				for (int i = 0; i <= record.length - 1; i++) {
					message = commonDataDAO.deleteCheckMaster(record[i].split("#")[0], record[i].split("#")[1], record[i].split("#")[2], masterBean.getId());
					if (CPSUtils.compareStrings(message, CPSConstants.FAILED)) {
						status = false;
						break;
					}
				}
			}
			if (status) {
				message = masterDataDAO.deleteMasterData(masterBean);
			} else {
				message = CPSConstants.DELETEFAIL;
			}

		} catch (Exception e) {
			throw e;
		}
		log.debug("::MasterDataBusiness>>>>>>>>>>>>deleteMasterData(-,-) End>>>>>>>>>>>>>>");
		return message;
	}

	@SuppressWarnings("unchecked")
	public MasterDataBean getMasterData(MasterDataBean masterDataBean) throws Exception {
		try {
			log.debug("::MasterDataBusiness>>>>>>>>>>>>getMasterData(-,-) Start>>>>>>>>>>>>>>");
			if (CPSUtils.compareStrings(masterDataBean.getType(), CPSConstants.DISTRICT)) {
				masterDataBean.setMasterDataList(masterDataDAO.getDistrictStateList());
			} else if (CPSUtils.compareStrings(masterDataBean.getType(), CPSConstants.SUBCATEGORY)) {
				masterDataBean.setMasterDataList(commonDataDAO.getSubCategoryList());
			} else if (CPSUtils.compareStrings(masterDataBean.getType(), CPSConstants.CATEGORY)) {
				masterDataBean.setMasterDataList(commonDataDAO.getCategoryList());
			} else if (CPSUtils.compareStrings(masterDataBean.getType(), CPSConstants.DISNUMBER)) {
				masterDataBean.setMasterDataList(masterDataDAO.getDisLocationList());
			} else if (CPSUtils.compareStrings(masterDataBean.getType(), CPSConstants.AWARDCATEGORY)) {
				masterDataBean.setMasterDataList(masterDataDAO.getAwardOrganizationList());
			} else if (CPSUtils.compareStrings(masterDataBean.getType(), CPSConstants.REQUEST)) {
				masterDataBean.setMasterDataList(masterDataDAO.getRequestDetails());
			} else if (CPSUtils.compareStrings(masterDataBean.getType(), CPSConstants.PIN)) {
				masterDataBean.setMasterDataList(masterDataDAO.getPinNumberList());
			} else if (CPSUtils.compareStrings(masterDataBean.getType(), CPSConstants.SUBQUARTER)) {
				masterDataBean.setMasterDataList(masterDataDAO.getSubQuarterList());
			}else if (CPSUtils.compareStrings(masterDataBean.getType(), "awardMaster")) {
				masterDataBean.setMasterDataList(masterDataDAO.getAwardMasterList(masterDataBean));
			}
			else if (CPSUtils.compareStrings(masterDataBean.getType(), "city")) {
					masterDataBean.setMasterDataList(masterDataDAO.getCityList());
			
			}else if(CPSUtils.compareStrings(masterDataBean.getType(), "letter")||CPSUtils.compareStrings(masterDataBean.getType(), "updateletter")){
				  masterDataBean.setLetterList(masterDataDAO.getLetterNoRefList(masterDataBean));
			
			}else {
				masterDataBean.setMasterDataList(commonDataDAO.getMasterData(getBeanName(masterDataBean.getType())));
			}
			

		} catch (Exception e) {
			throw e;
		}
		log.debug("::MasterDataBusiness>>>>>>>>>>>>getMasterData(-,-) End>>>>>>>>>>>>>>");
		return masterDataBean;
	}

	public String getBeanName(String type) throws Exception {
		try {
			log.debug("::MasterDataBusiness>>>>>>>>>>>>getBeanName(-,-) Start>>>>>>>>>>>>>>");
			if (CPSUtils.compareStrings(type, CPSConstants.CATEGORY) || CPSUtils.compareStrings(type, CPSConstants.SUBCATEGORY)) {
				return CPSConstants.CATEGORYDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.APPOINTMENT)) {
				return CPSConstants.APPOINTMENTDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.GROUP)) {
				return CPSConstants.GROUPDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.EMPLOYMENT)) {
				return CPSConstants.EMPLOYMENTDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.RELIGION)) {
				return CPSConstants.RELIGIONDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.RELATION)) {
				return CPSConstants.FAMILYRELATIONDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.QUALIFICATION)) {
				return CPSConstants.QUALIFICATIONDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.DISCIPLINE)) {
				return CPSConstants.DISCIPLINEDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.DIRECTORATE)) {
				return CPSConstants.DIRECTORATEDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.DIVISION)) {
				return CPSConstants.DIVISIONDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.DISTRICT)) {
				return CPSConstants.DISTRICTTYPEDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.REQUEST)) {
				return CPSConstants.REQUESTWORKDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.STATE)) {
				return CPSConstants.STATETYPEDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.WORKFLOW)) {
				return CPSConstants.WORKFLOWTYPEDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.DEGREE)) {
				return CPSConstants.DEGREEDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.COMMUNITY)) {
				return CPSConstants.COMMUNITYDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.APPROLES)) {
				return CPSConstants.APPLICATIONROLESDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.RESERVATION)) {
				return CPSConstants.RESERVATIONDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.DISLOCATION)) {
				return CPSConstants.DISPENSARYLOCATIONDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.DISNUMBER)) {
				return CPSConstants.DISPENSARYNUMBERDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.AWARDCATEGORY)) {
				return CPSConstants.AWARDCATEGORYDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.RETIREMENTTYPE)) {
				return CPSConstants.RetirementTypeDTO;
			} else if (CPSUtils.compareStrings(type,CPSConstants.NOMINEE)) {
				return CPSConstants.NOMINEETYPEDTO;
			} else if (CPSUtils.compareStrings(type,CPSConstants.YEAR)) {
				return CPSConstants.YEARTYPEDTO;
			} else if (CPSUtils.compareStrings(type,CPSConstants.MOTHERTONGUE)) {
				return CPSConstants.MOTHERTONGUEDTO;
			} else if (CPSUtils.compareStrings(type,CPSConstants.PIN)) {
				return CPSConstants.PINNUMBERDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.DEPARTMENTTYPE)) {
				return CPSConstants.DEPARTMENTTYPEDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.QUARTERTYPE)) {
				return CPSConstants.PAYBILLQUARTERSTYPEMASTERDTO;
			}
			else if (CPSUtils.compareStrings(type, "city")) {
				return "CityDTO";
			}
		} catch (Exception e) {
			throw e;
		}
		log.debug("::MasterDataBusiness>>>>>>>>>>>>getBeanName(-,-) End>>>>>>>>>>>>>>");
		return null;
	}

	public String getTableName(String type) throws Exception {
		try {
			log.debug("::MasterDataBusiness>>>>>>>>>>>>getTableName(-,-) Start>>>>>>>>>>>>>>");
			if (CPSUtils.compareStrings(type, CPSConstants.CATEGORY)) {
				return CPSConstants.CATEGORY_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.SUBCATEGORY)) {
				return CPSConstants.SUB_CATEGORY_MASTER;
			} else if(CPSUtils.compareString(type,CPSConstants.DESIGNATION)){
				return CPSConstants.DESIGNATION_MASTER;
			}
			else if (CPSUtils.compareStrings(type, CPSConstants.GROUP)) {
				return CPSConstants.GROUP_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.APPOINTMENT)) {
				return CPSConstants.APPOINTMENT_TYPE_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.EMPLOYMENT)) {
				return CPSConstants.EMPLOYMENT_TYPE_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.RELIGION)) {
				return CPSConstants.RELIGION_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.RELATION)) {
				return CPSConstants.FAMILY_RELATION_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.QUALIFICATION)) {
				return CPSConstants.QUALIFICATION_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.DISCIPLINE)) {
				return CPSConstants.DISCIPLINE_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.DISCIPLINE)) {
				return CPSConstants.DIRECTORATE_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.DISCIPLINE)) {
				return CPSConstants.DIVISION_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.DISTRICT)) {
				return CPSConstants.DISTRICT_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.REQUEST)) {
				return CPSConstants.REQUEST_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.STATE)) {
				return CPSConstants.STATE_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.WORKFLOW)) {
				return CPSConstants.WORKFLOW_TYPE_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.DEGREE)) {
				return CPSConstants.DEGREE_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.COMMUNITY)) {
				return CPSConstants.COMMUNITY_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.APPROLES)) {
				return CPSConstants.ROLE_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.RESERVATION)) {
				return CPSConstants.RESERVATION_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.DISLOCATION)) {
				return CPSConstants.DISPENSARY_LOCATION;
			} else if (CPSUtils.compareStrings(type, CPSConstants.DISNUMBER)) {
				return CPSConstants.DISPENSARY_MAPPING;
			} else if (CPSUtils.compareStrings(type, CPSConstants.AWARDCATEGORY)) {
				return CPSConstants.AWARD_CATEGORY_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.QUARTERTYPE)) {
				return CPSConstants.PAY_QUARTERS_TYPE_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.SUBQUARTER)) {
				return CPSConstants.PAY_QUARTERS_SUBTYPE_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.RETIREMENTTYPE)) {
				return CPSConstants.RETIREMENT_TYPE_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.NOMINEETYPE)) {
				return CPSConstants.NOMINEE_TYPE_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.YEAR)) {
				return CPSConstants.YEAR_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.MOTHERTONGUE)) {
				return CPSConstants.MOTHER_TONGUE_MASTER;
			}else if (CPSUtils.compareStrings(type, CPSConstants.PIN)) {
				return CPSConstants.EMP_PIN_NUMBER;
			}else if (CPSUtils.compareStrings(type, CPSConstants.DEPARTMENTTYPE)) {
				return CPSConstants.DEPARTMENT_TYPE_MASTER;
			}else if (CPSUtils.compareStrings(type, "city")) {
				return "CITY_MASTER";
			}
		} catch (Exception e) {
			throw e;
		}
		log.debug("::MasterDataBusiness>>>>>>>>>>>>getTableName(-,-) End>>>>>>>>>>>>>>");
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public MasterDataBean getDesignationHome(HttpSession session) throws Exception {
		MasterDataBean masterBean = null;
		try {
			log.debug("::MasterDataBusiness>>>>>>>>>>>>getDesignationHome(-,-) Start>>>>>>>>>>>>>>");
			masterBean = new MasterDataBean();
			// get the category list
			masterBean.setCategoryList(commonDataDAO.getCategoryList());
			// get the subcategory list
			masterBean.setSubCategoryList(commonDataDAO.getSubCategoryList());
			// get the group list
			masterBean.setGroupList(commonDataDAO.getMasterData(CPSConstants.GROUPDTO));
			// get the designation table list
			masterBean = masterDataDAO.getDesignationTableList(masterBean);
			session.setAttribute(CPSConstants.DISPLAYDESIGNATION, masterBean.getDesignationDisplayTable());
			session.setAttribute(CPSConstants.SUBCATEGORYLIST, masterBean.getSubCategoryList());
			session.setAttribute(CPSConstants.CATEGORYLIST, masterBean.getCategoryList());
			session.setAttribute(CPSConstants.GROUPLIST, masterBean.getGroupList());
		} catch (Exception e) {
			throw e;
		}
		log.debug("::MasterDataBusiness>>>>>>>>>>>>getDesignationHome(-,-) End>>>>>>>>>>>>>>");
		return masterBean;
	}

	public MasterDataBean manageDesignation(MasterDataBean masterBean, HttpSession session) throws Exception {
		String message = "";
		try {
			log.debug("::MasterDataBusiness>>>>>>>>>>>>manageDesignation(-,-) Start>>>>>>>>>>>>>>");
			if (CPSUtils.isNullOrEmpty(masterBean.getId())) {
				/*masterBean.setBeanName(getBeanName(masterBean.getType()));//This is new code for removing duplicatd records.
				String tableName = getTableName(masterBean.getType());
				masterBean.setTableName(tableName);
				message=masterDataDAO.checkName(masterBean);
				if(CPSUtils.compareStrings(message, CPSConstants.SUCCESS)){		*/
				// new designation created
				masterBean.setId(commonDataDAO.getSequenceValue("DESIGNATION_MASTER_SEQ"));
				message = masterDataDAO.createDesignation(masterBean);
				message = masterDataDAO.createDesigMapping(masterBean);
//				}

			} else {
				// updated old designation
				message = masterDataDAO.updateDesignation(masterBean);
				message = masterDataDAO.updateDesignationMapping(masterBean);
			}
			masterBean = masterDataDAO.getDesignationTableList(masterBean);
			session.setAttribute(CPSConstants.DISPLAYDESIGNATION, masterBean.getDesignationDisplayTable());
			masterBean.setMessage(message);
		} catch (Exception e) {
			throw e;
		}
		log.debug("::MasterDataBusiness>>>>>>>>>>>>manageDesignation(-,-) End>>>>>>>>>>>>>>");
		return masterBean;
	}

	public MasterDataBean deleteDesignation(String desigID, HttpSession session) throws Exception {
		MasterDataBean masterBean = null;
		String message = "";
		boolean status = true;
		try {
			log.debug("::MasterDataBusiness>>>>>>>>>>>>deleteDesignation(-,-) Start>>>>>>>>>>>>>>");
			masterBean = new MasterDataBean();

			message = getTableDetails(CPSConstants.DESIGNATION);

			if (!CPSUtils.isNullOrEmpty(message)) {
				String[] record = message.split(",");
				for (int i = 0; i <= record.length - 1; i++) {
					message = commonDataDAO.deleteCheckMaster(record[i].split("#")[0], record[i].split("#")[1], record[i].split("#")[2], desigID);
					if (CPSUtils.compareStrings(message, CPSConstants.FAILED)) {
						status = false;
						break;
					}
				}
			}
			if (status) {
				masterBean.setMessage(masterDataDAO.deleteDesignation(desigID));
				masterBean = masterDataDAO.getDesignationTableList(masterBean);
				session.setAttribute(CPSConstants.DISPLAYDESIGNATION, masterBean.getDesignationDisplayTable());
			} else {
				masterBean.setMessage(CPSConstants.DELETEFAIL);
			}
		} catch (Exception e) {
			throw e;
		}
		log.debug("::MasterDataBusiness>>>>>>>>>>>>deleteDesignation(-,-) End>>>>>>>>>>>>>>");
		return masterBean;
	}

	public List getParentDataList(String type) throws Exception {
		List list = null;
		try {
			log.debug("::MasterDataBusiness>>>>>>>>>>>>getParentDataList(-,-) Start>>>>>>>>>>>>>>");
			if (CPSUtils.compareStrings(CPSConstants.DISTRICT, type))
				list = commonDataDAO.getMasterData(CPSConstants.STATETYPEDTO);
			else if (CPSUtils.compareStrings(CPSConstants.SUBCATEGORY, type))
				list = commonDataDAO.getMasterData(CPSConstants.CATEGORYDTO);
			else if (CPSUtils.compareStrings(CPSConstants.DISNUMBER, type))
				list = commonDataDAO.getMasterData(CPSConstants.DISPENSARYLOCATIONDTO);
			else if (CPSUtils.compareStrings(CPSConstants.AWARDCATEGORY, type))
				list = commonDataDAO.getMasterData(CPSConstants.AWARDCATEGORYDTO);
			else if (CPSUtils.compareStrings(CPSConstants.SUBQUARTER, type))
				list = commonDataDAO.getMasterData(CPSConstants.PAYBILLQUARTERTYPEMASTERDTO);
		} catch (Exception e) {
			throw e;
		}
		log.debug("::MasterDataBusiness>>>>>>>>>>>>getParentDataList(-,-) End>>>>>>>>>>>>>>");
		return list;
	}

	public String getTableDetails(String type) throws Exception {
		String message = "";
		try {
			log.debug("::MasterDataBusiness>>>>>>>>>>>>getTableDetails(-,-) Start>>>>>>>>>>>>>>");
			if (CPSUtils.compareStrings(type, CPSConstants.SUBCATEGORY)) {
				return CPSConstants.DESIGNATION_MAPPINGS + "#" + "SUB_CATEGORY_ID" + "#" + "NOSTATUS";
			} else if (CPSUtils.compareStrings(type, CPSConstants.CATEGORY)) {
				return CPSConstants.DESIGNATION_MAPPINGS + "#" + "CATEGORY_ID" + "#" + "NOSTATUS";
			} else if (CPSUtils.compareStrings(type, CPSConstants.GROUP)) {
				return CPSConstants.DESIGNATION_MAPPINGS + "#" + "GROUP_ID" + "#" + "NOSTATUS";
			} else if (CPSUtils.compareStrings(type, CPSConstants.APPOINTMENT)) {
				return CPSConstants.EMP_MASTER + "#" + "APPOINTMENT_TYPE_ID" + "#" + "STATUS";
			} else if (CPSUtils.compareStrings(type, CPSConstants.EMPLOYMENT)) {
				return CPSConstants.EMP_MASTER + "#" + "EMPLOYMENT_TYPE_ID" + "#" + "STATUS";
			} else if (CPSUtils.compareStrings(type, CPSConstants.RELIGION)) {
				return CPSConstants.EMP_MASTER + "#" + "RELIGION_ID" + "#" + "STATUS";
			} else if (CPSUtils.compareStrings(type, CPSConstants.RELATION)) {
				return CPSConstants.FAMILY_DETAILS + "#" + "RELATION_ID" + "#" + "STATUS";
			} else if (CPSUtils.compareStrings(type, CPSConstants.QUALIFICATION)) {
				return CPSConstants.QUALIFICATION_DETAILS + "#" + "QUALIFICATION_ID" + "#" + "STATUS";
			} else if (CPSUtils.compareStrings(type, CPSConstants.DISCIPLINE)) {
				return CPSConstants.QUALIFICATION_DETAILS + "#" + "DISCIPLINE_ID" + "#" + "STATUS";
			} else if (CPSUtils.compareStrings(type, CPSConstants.DISTRICT)) {
				return CPSConstants.ADDRESS_DETAILS_MASTER + "#" + "DISTRICT" + "#" + "STATUS" + "," + CPSConstants.FAMILY_DETAILS + "#" + "DISTRICT_ID" + "#" + "STATUS";
			} else if (CPSUtils.compareStrings(type, CPSConstants.STATE)) {
				return CPSConstants.ADDRESS_DETAILS_MASTER + "#" + "STATE" + "#" + "STATUS" + "," + CPSConstants.FAMILY_DETAILS + "#" + "STATE_ID" + "#" + "STATUS";
			} else if (CPSUtils.compareStrings(type, CPSConstants.DEGREE)) {
				return CPSConstants.QUALIFICATION_DETAILS + "#" + "DEGREE" + "#" + "STATUS";
			} else if (CPSUtils.compareStrings(type, CPSConstants.COMMUNITY)) {
				return CPSConstants.EMP_MASTER + "#" + "COMMUNITY_ID" + "#" + "STATUS";
			} else if (CPSUtils.compareStrings(type, CPSConstants.DESIGNATION)) {
				return CPSConstants.EMP_MASTER + "#" + "DESIGNATION_ID" + "#" + "STATUS";
			} else if (CPSUtils.compareStrings(type, CPSConstants.APPROLES)) {
				return CPSConstants.APPLICATION_ROLE_MAPPING + "#" + "ROLE_ID" + "#" + "STATUS";
			} else if (CPSUtils.compareStrings(type, CPSConstants.DISNUMBER)) {
				return CPSConstants.EMP_MASTER + "#" + "DISPENSARY_NUMBER" + "#" + "STATUS";
			} else if (CPSUtils.compareStrings(type, CPSConstants.DISLOCATION)) {
				return CPSConstants.DISPENSARY_MAPPING + "#" + "DISPENSARY_LOCATION_ID" + "#" + "STATUS";
			} else if (CPSUtils.compareStrings(type, CPSConstants.AWARDCATEGORY)) {
				return CPSConstants.AWARD_DETAILS + "#" + "AWARD_CATEGORY_ID" + "#" + "STATUS";
			} else if (CPSUtils.compareStrings(type, CPSConstants.QUARTERTYPE)) {
				return CPSConstants.PAY_QUARTERS_SUBTYPE_MASTER + "#" + "QUARTERS_TYPE_ID" + "#" + "STATUS";
			} else if (CPSUtils.compareStrings(type, CPSConstants.SUBQUARTER)) {
				return CPSConstants.PAY_QUARTER_MANAGEMENT_DETAILS + "#" + "QUARTER_SUB_TYPE" + "#" + "STATUS";
			} else if (CPSUtils.compareStrings(type, CPSConstants.RETIREMENTTYPE)) {
				return CPSConstants.RETIREMENT_DETAILS + "#" + "RETIREMENT_TYPE_ID" + "#" + "STATUS";
			} else if (CPSUtils.compareStrings(type, CPSConstants.RESERVATION)) {
				return CPSConstants.EMP_MASTER + "#" + "RESERVATION_TYPE_ID" + "#" + "STATUS";
			}else if (CPSUtils.compareStrings(type, CPSConstants.NOMINEETYPE)) {
				return CPSConstants.NOMINEE_DETAILS + "#" + "NOMINEE_TYPE_ID" + "#" + "STATUS";
			}else if (CPSUtils.compareStrings(type, CPSConstants.YEAR)) {
				return CPSConstants.QUALIFICATION_DETAILS + "#" + "YEAR" + "#" + "STATUS"+","+"TRAINING_DETAILS"+"#"+"YEAR"+ "#" +"STATUS";
			}
			else if (CPSUtils.compareStrings(type, "city")) {
				return "hrdg_training_venue_details" + "#" + "city_id" + "#" + "STATUS";
			}

		} catch (Exception e) {
			throw e;
		}
		log.debug("::MasterDataBusiness>>>>>>>>>>>>getTableDetails(-,-) End>>>>>>>>>>>>>>");
		return message;
	}
	
	public MasterDataBean empChangedSFID(MasterDataBean masterDataBean) throws Exception {
		List list = null;
		try {
			log.debug("::MasterDataBusiness>>>>>>>>>>>>empChangedSFID(-,-) Start>>>>>>>>>>>>>>");
			list = commonDataDAO.checkingChangedSFID(masterDataBean.getChangeSfid());
			if (CPSUtils.checkList(list)) {

				masterDataBean.setMessage(CPSConstants.SUCCESS);
				for (Object obj : list) {
					Object[] rows = (Object[]) obj;
					masterDataBean.setChangeSfidName(rows[1].toString());
				}
			} else {
				masterDataBean.setMessage(CPSConstants.FAILED);
			}
		} catch (Exception e) {
			throw e;
		}
		log.debug("::MasterDataBusiness>>>>>>>>>>>>empChangedSFID(-,-) End>>>>>>>>>>>>>>");
		return masterDataBean;
	}

	public String saveRequestTypes(MasterDataBean masterDataBean) throws Exception {
		String message = "";
		try {
			log.debug("::MasterDataBusiness>>>>>>>>>>>>saveRequestTypes(-,-) Start>>>>>>>>>>>>>>");
			message = masterDataDAO.updateRequestTypes(masterDataBean.getRequestFlags());
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		log.debug("::MasterDataBusiness>>>>>>>>>>>>saveRequestTypes(-,-) End>>>>>>>>>>>>>>");
		return message;
	}

	public String changeEmployeeDetails(String changeSfid,HttpSession session)throws Exception {
		String name=null;
		String message="empNotExists";
		try {
			name = masterDataDAO.chageEmployeeDetails(changeSfid);
			if(!CPSUtils.isNullOrEmpty(name)){
				session.removeAttribute(CPSConstants.CHANGESFID);
				session.setAttribute(CPSConstants.CHANGESFID, changeSfid);
				session.setAttribute(CPSConstants.CHANGESFIDNAME, name);
				System.out.println(session.getAttribute("changeSfid"));
				System.out.println(session.getAttribute(changeSfid));
//				message = CPSConstants.SUCCESS;
				message="employeeexists";
			}
		}catch (Exception e) {
			throw e;
		}
		
		return message;
	}
	public List getAwardCategoryList() throws Exception{
		List list = null;
		try
		{
			list = masterDataDAO.getAwardCategoryList();
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	public String manageAwardMasterData(MasterDataBean masterBean) throws Exception{
		String message = null;
		try {

			if (CPSUtils.isNullOrEmpty(masterBean.getId())) {
				if (CPSUtils.compareStrings(checkAwardMasterData(masterBean), CPSConstants.SUCCESS)) {

					AwardDTO dto = new AwardDTO();
					BeanUtils.copyProperties(dto, masterBean);
					dto.setStatus(1);
					message = commonDataDAO.saveObject(dto);
					
				}
			 else {
				message = CPSConstants.DUPLICATE;
			}



			} else {
				if (CPSUtils.compareStrings(checkAwardMasterData(masterBean), CPSConstants.SUCCESS)) {
					message = masterDataDAO.updateAwardMasterData(masterBean);
					
									
				} else {
					message = CPSConstants.DUPLICATE;
				}

			}

		} catch (Exception e) {
			throw e;
		}
		return message;
	}
	
	private String checkAwardMasterData(MasterDataBean masterBean) throws Exception{
		String message = null;
		try {

			if (CPSUtils.checkList(masterDataDAO.checkAwardMasterData(masterBean))) {
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
	public String manageLetter(MasterDataBean masterBean) throws Exception{
		String message = "";
		try{
			//Integer deptId = masterDataDAO.getDeptId(masterBean);
			message = masterDataDAO.manageLetterData(masterBean);
			
		}catch (Exception e) {
			throw e;
		}
		
		
		return message;
	}
	public String deleteLetterNumberDetails(MasterDataBean masterBean) throws Exception {
		String message = null;
		try{
		message = masterDataDAO.deleteLetterData(masterBean);
		
	}catch (Exception e) {
		
	}
		return message;
		}
	
	public String deleteAwardMasterDetails(int id) throws Exception{
		String message = null;
		try{
			message=masterDataDAO.deleteAwardMasterDetails(id);
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	public List<PinNumberDTO> getPinNoSfidList(String sfid) throws Exception{
		List<PinNumberDTO> getPinNoSfidList =null;
		try{
			getPinNoSfidList=masterDataDAO.getPinNoSfidList(sfid);
		}catch (Exception e) {
			throw e;
		}
		return getPinNoSfidList;
	}
}
