package com.callippus.web.cghs.business.management;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.cghs.beans.dto.ReferralHospitalDTO;
import com.callippus.web.cghs.beans.dto.WardTypeDTO;
import com.callippus.web.cghs.beans.management.CghsManagementBean;
import com.callippus.web.cghs.dao.management.ICghsManagementDAO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;

@Service
public class CghsManagementBusiness {
	private static Log log = LogFactory.getLog(CghsManagementBusiness.class);
	@Autowired
	private ICghsManagementDAO cghsManagementDAO;
	
	/**
	 * THIS METHOD CONTAIN BUSINESS LOGIC FOR ADD,UPDATE AND DELETE AN REFERRAL HOSPITAL NAMES
	 * AND ALSO VERIFY DUPLICATE ENTRY OF REFERRAL HOSPITAL NAME
	 */
	@SuppressWarnings("unchecked")
	public String manageReferralHospital(CghsManagementBean cghsBean) throws Exception {
		ReferralHospitalDTO referralDTO=null;
		try {
			if(CPSUtils.compareStrings(cghsBean.getParam(), CPSConstants.HOME)) {
				cghsBean.setReferralHospitalList(cghsManagementDAO.getList(ReferralHospitalDTO.class));
				cghsBean.setCreationDate(CPSUtils.getCurrentDate());
			}else if(CPSUtils.compareStrings(cghsBean.getParam(), CPSConstants.MANAGE)) {
				referralDTO= (ReferralHospitalDTO)cghsManagementDAO.getBeanValue(ReferralHospitalDTO.class,cghsBean.getPk());
				if(CPSUtils.isNull(cghsManagementDAO.checkDuplicateEntry(cghsBean,ReferralHospitalDTO.class))){
					if(CPSUtils.isNull(referralDTO)) {
							referralDTO=new ReferralHospitalDTO();
							
							referralDTO.setHospitalLocation(cghsBean.getHospitalLocation().toUpperCase());
							referralDTO.setHospitalName(cghsBean.getHospitalName().toUpperCase());
							referralDTO.setAddress(cghsBean.getAddress());
							referralDTO.setBankName(cghsBean.getBankName().toUpperCase());
							referralDTO.setBranchName(cghsBean.getBranchName().toUpperCase());
							referralDTO.setAcNumber(cghsBean.getAcNumber());
							referralDTO.setCreatedBy(cghsBean.getSfid());
							referralDTO.setCreationDate(cghsBean.getCreationDate().toString());
							referralDTO.setLastModifiedBy(cghsBean.getSfid());
							referralDTO.setStatus(1);
							cghsBean.setMessage(cghsManagementDAO.saveBeanVales(referralDTO));
							cghsBean.setReferralHospitalList(cghsManagementDAO.getList(ReferralHospitalDTO.class));
						}else {
							referralDTO.setHospitalLocation(cghsBean.getHospitalLocation().toUpperCase());
							referralDTO.setHospitalName(cghsBean.getHospitalName().toUpperCase());
							referralDTO.setAddress(cghsBean.getAddress());
							referralDTO.setBankName(cghsBean.getBankName().toUpperCase());
							referralDTO.setBranchName(cghsBean.getBranchName().toUpperCase());
							referralDTO.setAcNumber(cghsBean.getAcNumber());
							referralDTO.setLastModifiedBy(cghsBean.getSfid());
							referralDTO.setCreationDate(cghsBean.getCreationDate());
							referralDTO.setStatus(1);
							cghsBean.setMessage(cghsManagementDAO.saveBeanVales(referralDTO));
							cghsBean.setReferralHospitalList(cghsManagementDAO.getList(ReferralHospitalDTO.class));
						}
				}else {
					cghsBean.setMessage(CPSConstants.DUPLICATE);
				}
			}else if(CPSUtils.compareStrings(cghsBean.getParam(), CPSConstants.DELETE)) {
				referralDTO = (ReferralHospitalDTO)cghsManagementDAO.getBeanValue(ReferralHospitalDTO.class,cghsBean.getPk());
				if(!CPSUtils.isNull(referralDTO)) {
					referralDTO.setCreationDate(referralDTO.getStrCreationDate());
					referralDTO.setStatus(0);
					referralDTO.setLastModifiedBy(cghsBean.getSfid());
					referralDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					cghsBean.setMessage(cghsManagementDAO.saveBeanVales(referralDTO));
					cghsBean.setReferralHospitalList(cghsManagementDAO.getList(ReferralHospitalDTO.class));
				}
			}else if(CPSUtils.compareStrings(cghsBean.getParam(), CPSConstants.SELECTEDDELETE)) {
				String values[] = cghsBean.getSelectedDelete().split(",");
				for(int i=0;i<values.length;i++) {
					cghsBean.setMessage(cghsManagementDAO.seletedDeleteRefHospital(Integer.parseInt(values[i]),cghsBean.getDeletionDate()));
				}
				cghsBean.setReferralHospitalList(cghsManagementDAO.getList(ReferralHospitalDTO.class));
			}
		}catch(Exception e) {
			throw e;
		}
		return cghsBean.getMessage();
	}
	@SuppressWarnings("unchecked")
	public String manageWardType(CghsManagementBean cghsBean) throws Exception {
		WardTypeDTO wardDTO = null;
		try {
			if(CPSUtils.compareStrings(cghsBean.getParam(), CPSConstants.WARDHOME)) {
				cghsBean.setWardTypeList(cghsManagementDAO.getList(WardTypeDTO.class));
			}
			else if(CPSUtils.compareStrings(cghsBean.getParam(), CPSConstants.WARDMANAGE)) {
				wardDTO= (WardTypeDTO)cghsManagementDAO.getBeanValue(WardTypeDTO.class,cghsBean.getPk());
				if(CPSUtils.isNull(wardDTO)) {
					if(CPSUtils.isNull(cghsManagementDAO.checkDuplicateEntry(cghsBean,WardTypeDTO.class))) {
						wardDTO=new WardTypeDTO();
						wardDTO.setName((cghsBean.getWardName().trim()).toUpperCase());
						wardDTO.setStartBasicPay(cghsBean.getStartBasicPay());
						wardDTO.setEndBasicPay(cghsBean.getEndBasicPay());
						wardDTO.setCreatedBy(cghsBean.getSfid());
						wardDTO.setCreationDate(CPSUtils.getCurrentDate());
						wardDTO.setLastModifiedBy(cghsBean.getSfid());
						wardDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
						wardDTO.setStatus(1);
						cghsBean.setMessage(cghsManagementDAO.saveBeanVales(wardDTO));
						cghsBean.setWardTypeList(cghsManagementDAO.getList(WardTypeDTO.class));
					}else {
						cghsBean.setMessage(CPSConstants.DUPLICATE);
					}
				}else {
					wardDTO.setName(cghsBean.getWardName().toUpperCase());
					wardDTO.setStartBasicPay(cghsBean.getStartBasicPay());
					wardDTO.setEndBasicPay(cghsBean.getEndBasicPay());
					wardDTO.setLastModifiedBy(cghsBean.getSfid());
					wardDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					wardDTO.setStatus(1);
					cghsBean.setMessage(cghsManagementDAO.saveBeanVales(wardDTO));
					cghsBean.setWardTypeList(cghsManagementDAO.getList(WardTypeDTO.class));
				}
			}
			else if(CPSUtils.compareStrings(cghsBean.getParam(), CPSConstants.WARDDELETE)) {
				wardDTO = (WardTypeDTO)cghsManagementDAO.getBeanValue(WardTypeDTO.class,cghsBean.getPk());
				if(!CPSUtils.isNull(wardDTO)) {
					wardDTO.setStatus(0);
					wardDTO.setLastModifiedBy(cghsBean.getSfid());
					wardDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					cghsBean.setMessage(cghsManagementDAO.saveBeanVales(wardDTO));
					cghsBean.setWardTypeList(cghsManagementDAO.getList(WardTypeDTO.class));
				}
			}
		}catch (Exception e) {
			throw e;
		}
		return cghsBean.getMessage();
		
	}
}
