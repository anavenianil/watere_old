package com.callippus.web.promotions.business.request;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.promotions.beans.management.PromotionManagementBean;
import com.callippus.web.promotions.beans.request.PromotionRequestBean;
import com.callippus.web.promotions.business.management.PromotionManagementBusiness;
import com.callippus.web.promotions.dao.request.IPromotionRequestDAO;
import com.callippus.web.promotions.dto.AssessmentDetailsDTO;

@Service
public class PromotionRequestBusiness {
	@Autowired
	private IPromotionRequestDAO promotionRequestDAO;
	@Autowired
	private IComonObjectDAO commonDAO;
	
	@Autowired
	private PromotionManagementBusiness promotionManagementBusiness;

	public PromotionRequestBean getOptionalCertificateHome(PromotionRequestBean promotionBean) throws Exception {
		try {
			
			promotionBean = promotionRequestDAO.getEmployeeDetails(promotionBean);
			
			                     
			promotionBean = promotionRequestDAO.getPromotedCandidatesRole(promotionBean);
			
			for(int i =0;i<promotionBean.getAppRoleList().size();i++){
				Integer temp = promotionBean.getAppRoleList().get(i).getApplicationRoleId();
				
				if(temp == 14 || temp==2){
					promotionBean.setAppRoleID(temp);
					break;
				}else{
					continue;
				}
			}
			
			PromotionManagementBean promotionBean1 = new PromotionManagementBean();
			BeanUtils.copyProperties(promotionBean, promotionBean1); 
			promotionBean1 = promotionManagementBusiness.getVenueHomeDetails(promotionBean1);
			BeanUtils.copyProperties(promotionBean1,promotionBean);   		
			promotionBean = promotionRequestDAO.getOptionalCertificateList(promotionBean);
			
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	public List<AssessmentDetailsDTO> getOptionalCertificateList(PromotionRequestBean promotionBean) throws Exception {
		try {
			promotionBean = promotionRequestDAO.getOptionalCertificateList(promotionBean);
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getOptionalCertificateList();
	}

	public PromotionRequestBean submitOptionalCertificate(PromotionRequestBean promotionBean) throws Exception {
		try {
			// User can able to apply optional certificate, if his assessment details status is in promotion stage
			promotionBean.setResult(promotionRequestDAO.checkAssessmentStatus(promotionBean));

			if (CPSUtils.compareStrings(promotionBean.getResult(), CPSConstants.SUCCESS)) {
				// Check duplicate
				promotionBean.setResult(promotionRequestDAO.checkDuplicateOptionalCertificate(promotionBean));

				if (CPSUtils.compareStrings(promotionBean.getResult(), CPSConstants.SUCCESS)) {
					// submit
					promotionBean = promotionRequestDAO.submitOptionalCertificate(promotionBean);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	public PromotionRequestBean deleteOptionalCertificate(PromotionRequestBean promotionBean) throws Exception {
		try {
			promotionBean = promotionRequestDAO.deleteOptionalCertificate(promotionBean);
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	public PromotionRequestBean getOptionalCertificatesSfidLIst(PromotionRequestBean promotionBean) throws Exception {
		try {
			
			
			PromotionManagementBean promotionBean1 = new PromotionManagementBean();
		   	BeanUtils.copyProperties(promotionBean, promotionBean1);  
			promotionBean1 = promotionManagementBusiness.getPayFixationDetailsWithOption(promotionBean1);
			BeanUtils.copyProperties(promotionBean1,promotionBean); 	
			
			
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}
	public PromotionRequestBean getOptionalCertificatesDetails(PromotionRequestBean promotionBean) throws Exception {
		try {
			// Check sfid
			//promotionBean.setResult(commonDAO.checkEmployee(promotionBean.getRefSfid()));
		//	if (CPSUtils.compareStrings(promotionBean.getResult(), CPSConstants.SUCCESS)) {
			promotionBean = promotionRequestDAO.getOptionalCertificatesDetails(promotionBean);
		//	}
			//else{
				promotionBean.setResult(CPSConstants.EMPNOTEXISTS);
			//}
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}
}
