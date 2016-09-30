package com.callippus.web.promotions.dao.request;

import com.callippus.web.promotions.beans.request.PromotionRequestBean;

public interface IPromotionRequestDAO {
	public PromotionRequestBean getEmployeeDetails(PromotionRequestBean promotionBean) throws Exception;

	public PromotionRequestBean getOptionalCertificateList(PromotionRequestBean promotionBean) throws Exception;

	public PromotionRequestBean deleteOptionalCertificate(PromotionRequestBean promotionBean) throws Exception;

	public String checkAssessmentStatus(PromotionRequestBean promotionBean) throws Exception;

	public PromotionRequestBean submitOptionalCertificate(PromotionRequestBean promotionBean) throws Exception;

	public String checkDuplicateOptionalCertificate(PromotionRequestBean promotionBean) throws Exception;

	public PromotionRequestBean getOptionalCertificatesDetails(PromotionRequestBean promotionBean) throws Exception ;

	public PromotionRequestBean getPromotedCandidatesRole(PromotionRequestBean promotionBean) throws Exception;
}
