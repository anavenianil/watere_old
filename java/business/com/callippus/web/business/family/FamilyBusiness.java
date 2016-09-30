package com.callippus.web.business.family;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.address.AddressBean;
import com.callippus.web.beans.dto.ConstraintsDTO;
import com.callippus.web.beans.dto.DistrictTypeDTO;
import com.callippus.web.beans.dto.ResultDTO;
import com.callippus.web.beans.dto.StateTypeDTO;
import com.callippus.web.beans.family.FamilyBean;
import com.callippus.web.cghs.business.management.CghsConstraints;
import com.callippus.web.cghs.business.management.LtcConstraints;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.address.IAddressDAO;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.family.IFamilyDAO;

@Service
public class FamilyBusiness {
	@Autowired
	private IFamilyDAO familyDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;
	@Autowired
	private IAddressDAO addressDAO;
	@Autowired
	private CghsConstraints cghsConstraints;
	@Autowired
	private LtcConstraints ltcConstraints;

	@SuppressWarnings("unchecked")
	public FamilyBean getMasterData(FamilyBean familyBean) throws Exception {
		try {
			familyBean.setRelationList(commonDataDAO.getMasterData(CPSConstants.FAMILYRELATIONDTO));
			familyBean.setMaritalStatusList(commonDataDAO.getMasterData(CPSConstants.MARITAL));
			familyBean.setDisabilityList(commonDataDAO.getMasterData(CPSConstants.DISABILITYDTO));
			AddressBean addressBean = new AddressBean();
			addressBean.setSfid(familyBean.getSfid());
			List list = addressDAO.getEmployeeAddressDetails(addressBean);
			if (list.size() > 0) {
				list.remove(list.size() - 1);
			}
			familyBean.setAddressTypeList(list);
			familyBean.setBloodGroupList(commonDataDAO.getMasterData(CPSConstants.BLOODGROUPDTO));
		} catch (Exception e) {
			throw e;
		}
		return familyBean;
	}

	public FamilyBean getFamilyDetails(FamilyBean familyBean) throws Exception {
		try {
			familyBean.setEmpFamilyList(familyDAO.getFamilyDetails(familyBean.getSfid()));
		} catch (Exception e) {
			throw e;
		}
		return familyBean;

	}

	public FamilyBean manageFamily(FamilyBean familyBean) throws Exception {
		String message = "";
		List list = null;
		ConstraintsDTO constraintsDto = null;
		ResultDTO permanent = null;
		ResultDTO resultDTO = null;
		String tempCghsFacility = null;
		String tempLtcFacility = null;
		String individualCghs = null;
		String individualLtc = null;
		try {
			tempCghsFacility = familyBean.getCghsFacility();
			tempLtcFacility = familyBean.getLtcFacility();
			if (CPSUtils.compareStrings(familyBean.getDisabilityId(), CPSConstants.SELECT)) {
				familyBean.setDisabilityId("0");
			}
			if (CPSUtils.isNull(familyBean.getDisabilityId())) {
				familyBean.setDisabilityId("0");
			}
			if (CPSUtils.compareStrings(familyBean.getAddressTypeId(), CPSConstants.SELECT)) {
				familyBean.setAddressTypeId(null);
			}
			if (CPSUtils.compareStrings(familyBean.getMaritalstatus(), CPSConstants.SELECT)) {
				familyBean.setMaritalstatus(null);
			}
			if (CPSUtils.compareStrings(familyBean.getDependent(), CPSConstants.SELECT)) {
				familyBean.setDependent(CPSConstants.N);
			}
			if (CPSUtils.compareStrings(familyBean.getEmployeed(), CPSConstants.SELECT)) {
				familyBean.setEmployeed(CPSConstants.N);
			}
			if (CPSUtils.compareStrings(familyBean.getEmployeedType(), CPSConstants.SELECT)) {
				familyBean.setEmployeedType(null);
			}
			if (CPSUtils.compareStrings(familyBean.getState(), CPSConstants.SELECT)) {
				familyBean.setState(null);
			}
			if (CPSUtils.compareStrings(familyBean.getDistrict(), CPSConstants.SELECT)) {
				familyBean.setDistrict(null);
			}
			if (CPSUtils.compareStrings(familyBean.getPhtypeFlag(), CPSConstants.SELECT)) {
				familyBean.setPhtypeFlag(CPSConstants.N);
			}
			if (CPSUtils.compareStrings(familyBean.getAdopted(), CPSConstants.SELECT)) {
				familyBean.setAdopted(null);
			}
			if (CPSUtils.compareStrings(familyBean.getAdoptedDate(), CPSConstants.SELECT)) {
				familyBean.setAdoptedDate(null);
			}
			if (CPSUtils.compareStrings(familyBean.getRelationId(), CPSConstants.SELF_RELATION_ID)) {
				familyBean.setCghsFacility(CPSConstants.Y);
				familyBean.setLtcFacility(CPSConstants.Y);
			}

			/**
			 * For new record insertion set CGHS and LTC facility as N For updation get the previous values for CGHS and LTC
			 */
			/*if (CPSUtils.compareStrings(tempCghsFacility, CPSConstants.Y)) {
				familyBean.setCghsFacility(familyDAO.getPreviousValue(CPSConstants.CGHS, familyBean.getSfid(), familyBean.getId()));
			}
			if (CPSUtils.compareStrings(tempLtcFacility, CPSConstants.Y)) {
				familyBean.setLtcFacility(familyDAO.getPreviousValue(CPSConstants.LTC, familyBean.getSfid(), familyBean.getId()));
			}*/
			if (!CPSUtils.isNullOrEmpty(familyBean.getDob())) {
				familyBean.setAge("");
			}
			familyBean.setStatus(1);
			familyBean.setCreationDate(CPSUtils.getCurrentDate());
			familyBean.setModifiedDate(CPSUtils.getCurrentDate());
			
			if (!CPSUtils.compareStrings(familyBean.getRelationId(), CPSConstants.SELF_RELATION_ID)
					&& (CPSUtils.compareStrings(tempCghsFacility, CPSConstants.Y) || CPSUtils.compareStrings(tempLtcFacility, CPSConstants.Y))) {
				permanent = new ResultDTO();
				constraintsDto = new ConstraintsDTO();
				constraintsDto.setId(familyBean.getId());
				constraintsDto.setAge(familyBean.getAge());
				constraintsDto.setDob(familyBean.getDob());
				constraintsDto.setEarningMoney(familyBean.getEarningMoney());
				constraintsDto.setPhFlag(familyBean.getPhtypeFlag());
				constraintsDto.setEmployeeFlag(familyBean.getEmployeed());
				constraintsDto.setGender(familyBean.getGender());
				constraintsDto.setRelationId(familyBean.getRelationId());
				constraintsDto.setMaritalId(familyBean.getMaritalstatus());
				constraintsDto.setSfid(familyBean.getSfid());
				if (CPSUtils.compareStrings(tempCghsFacility, CPSConstants.Y)) {
					individualCghs = CPSConstants.N;
					// checks the CGHS Constrains if user clicks on CGHS facility available
					constraintsDto.setType(CPSConstants.CGHS);
					resultDTO = cghsConstraints.checkCghsConstraints(constraintsDto, permanent);
					if (CPSUtils.compareStrings(resultDTO.getResult(), CPSConstants.SUCCESS)) {
						individualCghs = CPSConstants.Y;
					}
				}
				if (CPSUtils.compareStrings(tempLtcFacility, CPSConstants.Y)) {
					individualLtc = CPSConstants.N;
					// checks the LTC Constrains if user clicks on LTC facility available
					constraintsDto.setType(CPSConstants.LTC);
					resultDTO = ltcConstraints.checkLtcConstraints(constraintsDto, permanent);
					if (CPSUtils.compareStrings(resultDTO.getResult(), CPSConstants.SUCCESS)) {
						individualLtc = CPSConstants.Y;
					}
				}
				familyBean.setMessage(resultDTO.getResult());
				familyBean.setReason(resultDTO.getRemark());
				if (CPSUtils.compareStrings(familyBean.getMessage(), CPSConstants.SUCCESS)) {
					// If all constraints are satisfied then update the LTC and CGHS facility in family_details table
					familyBean.setMessage(familyDAO.updateLtcAndCghsDetails(tempCghsFacility, tempLtcFacility, familyBean.getSfid(), familyBean.getId()));
				} else {
					// constraints are not satisfied then display the remarks to the user
					//familyBean.setMessage(familyDAO.updateLtcAndCghsDetails(individualCghs, individualLtc, familyBean.getSfid(), familyBean.getId()));
					familyBean.setMessage(CPSConstants.FAILED);
					familyBean.setEmpFamilyList(familyDAO.getFamilyDetails(familyBean.getSfid()));
					return familyBean;
				}
			}

			if (CPSUtils.isNullOrEmpty(familyBean.getId())) {
				list = familyDAO.checkFamily(familyBean);
				if (CPSUtils.checkList(list)) {
					String relation = list.get(0).toString();
					if (CPSUtils.compareStrings(relation, CPSConstants.FATHER) || CPSUtils.compareStrings(relation, CPSConstants.MOTHER) || CPSUtils.compareStrings(relation, CPSConstants.FATHERINLAW)
							|| CPSUtils.compareStrings(relation, CPSConstants.MOTHERINLAW) || CPSUtils.compareStrings(relation, CPSConstants.WIFE) 
							|| CPSUtils.compareStrings(relation, CPSConstants.HUSBAND) || CPSUtils.compareStrings(relation, CPSConstants.SELF)) {
						familyBean.setMessage(CPSConstants.DUPLICATE);
					} else {
						int id = commonDataDAO.getTableID(CPSConstants.FAMILY_DETAILS, CPSConstants.UPDATE);
						familyBean.setId(String.valueOf(id));						
						message = familyDAO.manageFamily(familyBean);
						familyBean.setMessage(message);
					}
				} else {
					int id = commonDataDAO.getTableID(CPSConstants.FAMILY_DETAILS, CPSConstants.UPDATE);
					familyBean.setId(String.valueOf(id));		
					message = familyDAO.manageFamily(familyBean);
					familyBean.setMessage(message);
				}
			} else { // update
				familyBean.setCreationDate(commonDataDAO.getCreationDate(familyBean.getId(), familyBean.getSfid(), CPSConstants.FAMILY_DETAILS));
				familyBean.setModifiedDate(CPSUtils.getCurrentDate());
				familyBean.setStatus(1);
				message = familyDAO.updateFamily(familyBean);
				familyBean.setMessage(message);
			}
			
			familyBean.setEmpFamilyList(familyDAO.getFamilyDetails(familyBean.getSfid()));
			
		} catch (Exception e) {
			throw e;
		}
		return familyBean;
	}

	public FamilyBean deleteFamily(FamilyBean FamilyBean) throws Exception {
		String message = null;
		try {
			if (familyDAO.checkNomineeData(FamilyBean)) {
				message = familyDAO.deleteFamily(FamilyBean.getId(),FamilyBean.getRemarks());
				FamilyBean.setMessage(message);
				FamilyBean.setEmpFamilyList(familyDAO.getFamilyDetails(FamilyBean.getSfid()));
			} else {
				FamilyBean.setMessage("deleteMemberIsNominee");
				FamilyBean.setEmpFamilyList(familyDAO.getFamilyDetails(FamilyBean.getSfid()));
			}

		} catch (Exception e) {
			throw e;
		}
		return FamilyBean;
	}

	public List<StateTypeDTO> getStateList() throws Exception {
		List<StateTypeDTO> stateList = null;
		try {
			stateList = commonDataDAO.getStateList();
		} catch (Exception e) {
			throw e;
		}
		return stateList;
	}

	public List<DistrictTypeDTO> getDistrictList() throws Exception {
		List<DistrictTypeDTO> districtList = null;
		try {
			districtList = commonDataDAO.getDistrictList();
		} catch (Exception e) {
			throw e;
		}
		return districtList;
	}

	public String updateLtcAndCghsFacility() throws Exception {
		String message = null;
		try {
			String cghsSonAgeLimit = commonDataDAO.getConfigurationValue(CPSConstants.CGHS_SON_AGE_LIMIT);
			String cghsMajorAgeLimit = commonDataDAO.getConfigurationValue(CPSConstants.CGHS_MAJOR_AGE_LIMIT);
			String ltcSonAgeLimit = commonDataDAO.getConfigurationValue(CPSConstants.LTC_SON_AGE_LIMIT);
			familyDAO.updateCghsAndLtcFacility(CPSConstants.CGHS, Integer.parseInt(cghsSonAgeLimit), Integer.parseInt(cghsMajorAgeLimit));
			familyDAO.updateCghsAndLtcFacility(CPSConstants.LTC, Integer.parseInt(ltcSonAgeLimit), Integer.parseInt(cghsMajorAgeLimit));
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return message;
	}
	public String ageFieldUpdation() throws Exception {
		String message = null;
		try {
			message = familyDAO.ageFieldUpdation();
			
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
}