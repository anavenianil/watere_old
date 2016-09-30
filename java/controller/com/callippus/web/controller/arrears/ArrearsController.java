package com.callippus.web.controller.arrears;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.callippus.web.beans.arrears.ArrearsBean;
import com.callippus.web.business.arrears.ArrearsBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;

@Controller
@RequestMapping("arrears.htm")
@SessionAttributes
public class ArrearsController {
	@Autowired
	private ArrearsBusiness arrearsBusiness;
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.ARREARS) ArrearsBean arrearsBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		String viewName = null;
		HttpSession session = null;
		try {
		        session= request.getSession(true);
		        if (CPSUtils.compareStrings(CPSConstants.PAGING, arrearsBean.getParam())) {
					viewName = session.getAttribute(CPSConstants.RETURN).toString();
				}
				if (CPSUtils.isNullOrEmpty(arrearsBean.getSfid())) {
					arrearsBean.setUserSfid(session.getAttribute(CPSConstants.SFID).toString());
				}
				if (!CPSUtils.isNullOrEmpty(arrearsBean.getType())) {
					session.setAttribute(CPSConstants.MASTERTYPE, arrearsBean.getType());
				}
				if (CPSUtils.compareStrings(CPSConstants.DAARREARSHOME, arrearsBean.getParam())) {
					viewName=CPSConstants.DAARREARSHOMEDETAILS;
				}else if (CPSUtils.compareStrings(CPSConstants.PROMOTIONARREARSHOME, arrearsBean.getParam())) {
					viewName=CPSConstants.PROMOTIONARREARSHOMEDETAILS;
				}else if (CPSUtils.compareStrings(CPSConstants.ANNUALINCRARREARSHOME, arrearsBean.getParam())) {
					arrearsBean.setArrearsStatusList(arrearsBusiness.getAIArrearsStatusList());
					viewName=CPSConstants.ANNUALINCRARREARSHOMEDETAILS;
				}else if (CPSUtils.compareStrings(CPSConstants.PREVIEWANNINCRARREARS, arrearsBean.getParam())) {
					arrearsBean.setArrearsStatusList(arrearsBusiness.getAIArrearsStatusList(arrearsBean.getAdminAccDate(), arrearsBean.getFinanceAccDate()));
					session.setAttribute("annIncrArrearsPrevieList", arrearsBean.getArrearsStatusList());
					viewName=CPSConstants.ANNUALINCRPREVIEWLIST;
				} else if (CPSUtils.compareStrings(CPSConstants.FPAARREARSHOME, arrearsBean.getParam())) {
					viewName=CPSConstants.FPAARREARSHOMEDETAILS;
				}else if (CPSUtils.compareStrings("submitAnnualIncrArrearsDetails", arrearsBean.getParam())) {
					arrearsBean.setMessage(arrearsBusiness.submitAnnualIncrArrearsDetails(arrearsBean));
					arrearsBean.setArrearsStatusList(arrearsBusiness.getAIArrearsStatusList(arrearsBean.getAdminAccDate(), arrearsBean.getFinanceAccDate()));
					session.setAttribute("annIncrArrearsPrevieList", arrearsBean.getArrearsStatusList());
					viewName=CPSConstants.ANNUALINCRPREVIEWLIST;
				}else if(CPSUtils.compareStrings(CPSConstants.PREVIEWDAARREARS, arrearsBean.getParam())){
					arrearsBean.setArrearsStatusList(arrearsBusiness.getDaArrearsPreviewList(arrearsBean));
				    session.setAttribute("empCategoryMasterDetails", arrearsBusiness.getEmpPayCategoryList());
					session.setAttribute("daArrearsPreviewList", arrearsBean.getArrearsStatusList());
				viewName=CPSConstants.DAARREARSPREVIEWHOME;
				}else if(CPSUtils.compareStrings(CPSConstants.DAARREARSPREVIEDETAILS, arrearsBean.getParam())){
					arrearsBean.setArrearsStatusList(arrearsBusiness.getDaArrearsPreviewDetailedList(arrearsBean));
					session.setAttribute("categoryId", arrearsBean.getCategoryId());
					session.setAttribute("daArrearsPreviewDetails", arrearsBean.getArrearsStatusList());
					viewName=CPSConstants.DAARREARSPREVIEDETAILSLIST;
				}else if(CPSUtils.compareStrings(CPSConstants.SUBMITDAARREARSDETAILS, arrearsBean.getParam())){
					arrearsBean.setMessage(arrearsBusiness.submitDAArrearsDetails(arrearsBean));
					arrearsBean.setCategoryId((String)session.getAttribute("categoryId"));
					arrearsBean.setArrearsStatusList(arrearsBusiness.getDaArrearsPreviewDetailedList(arrearsBean));
					session.setAttribute("daArrearsPreviewDetails", arrearsBean.getArrearsStatusList());
					viewName=CPSConstants.DAARREARSPREVIEDETAILSLIST;
				}else if(CPSUtils.compareStrings(CPSConstants.GETFPAARREARSDETAILS, arrearsBean.getParam())){
					session.setAttribute("userSfid", arrearsBean.getUserSfid());
					arrearsBean=arrearsBusiness.getFpaArrearsDetails(arrearsBean);
					if(!CPSUtils.compareStrings(CPSConstants.EMPNOTEXISTS, arrearsBean.getMessage())){
					viewName=CPSConstants.FPAARREARSDETAILSLIST;
					}else{
						viewName=CPSConstants.ARREARSRESULT;
					}
				}else if(CPSUtils.compareStrings(CPSConstants.SUBMITFPAARREARSDETAILS, arrearsBean.getParam())){
					arrearsBean.setUserSfid((String)session.getAttribute("userSfid"));
					arrearsBusiness.submitFPAArrearsDetails(arrearsBean);
					viewName=CPSConstants.FPAARREARSDETAILSLIST;
				}else if(CPSUtils.compareStrings(CPSConstants.GETPROMOTIONARREARSDETAILS, arrearsBean.getParam())){
					arrearsBean.setArrearsStatusList(arrearsBusiness.getPromotionArrearsDetails(arrearsBean));
					if(!CPSUtils.compareStrings(CPSConstants.EMPNOTEXISTS, arrearsBean.getMessage())){
						viewName=CPSConstants.PROMOTIONARREARSPREVIEWDETAILS;
						}else{
							viewName=CPSConstants.ARREARSRESULT;
						}
				}else if(CPSUtils.compareStrings(CPSConstants.GETPROMOTIONARREARSPREVIEW, arrearsBean.getParam())){
					arrearsBean.setArrearsStatusList(arrearsBusiness.getPromotionArrearsPreviewList(arrearsBean));
					session.setAttribute("promotionArrearsPreviewList", arrearsBean.getArrearsStatusList());
					viewName=CPSConstants.PROMOTIONARREARSPREVIEWLIST;
				}else if(CPSUtils.compareStrings(CPSConstants.SUBMITPROMOTIONARREARSDETAILS, arrearsBean.getParam())){
					arrearsBean.setArrearsStatusList(arrearsBusiness.submitPromotionArrearsPreviewList(arrearsBean));
					session.setAttribute("promotionArrearsPreviewList", arrearsBean.getArrearsStatusList());
					viewName=CPSConstants.PROMOTIONARREARSPREVIEWLIST;
				}else if(CPSUtils.compareStrings(CPSConstants.GETFPAARREARSHOMEPAGE, arrearsBean.getParam())){
					arrearsBean.setArrearsStatusList(arrearsBusiness.getFPAArrearsPrintDetails());
					session.setAttribute("fpaArrearsList", arrearsBean.getArrearsStatusList());
					viewName=CPSConstants.FPAARREARSREPORTHOME;
				}else if(CPSUtils.compareStrings(CPSConstants.GETPROMOTIONARREARSREPORT, arrearsBean.getParam())){
					session.setAttribute("promotionArrearsList", arrearsBusiness.getPromotionArrearsPrintDetails());
					viewName=CPSConstants.PROMOTIONARREARSREPORT;
				}
				
				mav = new ModelAndView(viewName, CPSConstants.ARREARS, arrearsBean);
				if (!CPSUtils.isNullOrEmpty(arrearsBean.getMessage())) {
					mav.addObject(CPSConstants.MESSAGE, arrearsBean.getMessage());
				}
				if (!CPSUtils.isNullOrEmpty(arrearsBean.getRemarks())) {
					mav.addObject(CPSConstants.REMARKS, arrearsBean.getRemarks());
				}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		}
		return mav;
   }
	
}
