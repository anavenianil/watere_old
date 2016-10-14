package com.callippus.web.ltc.controller.request;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.callippus.web.business.requestprocess.LTCWaterRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.ltc.beans.request.LTCWaterRequestBean;
import com.callippus.web.ltc.business.request.LTCWaterRequestBusiness;
import com.callippus.web.ltc.dao.approval.ILtcApprovalRequestDAO;
import com.callippus.web.ltc.dto.LTCWaterRequestProcessBean;
import com.callippus.web.tada.dto.TadaWaterRequestProcessBean;


@SuppressWarnings("serial")
@Controller
@RequestMapping("/ltcWaterRequest.htm")
@SessionAttributes
public class LTCWaterRequestController  implements Serializable {
	
	private static Log log = LogFactory.getLog(LTCWaterRequestController.class);
	
	@Autowired
	LTCWaterRequestBusiness ltcWaterRequestBusiness;
	@Autowired
	private ILtcApprovalRequestDAO ltcApprovalRequestDAO;
	
	@Autowired
	private LTCWaterRequestProcess ltcWaterRequestProcess;
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(
			@ModelAttribute LTCWaterRequestBean ltcWaterRequestBean,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ModelAndView mav = null;
		HttpSession session = null;
		String viewName = null;
		
		try {
			
			session = request.getSession(true);
			ltcWaterRequestBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
			if (CPSUtils.compareStrings(CPSConstants.LTCWATERHOME, ltcWaterRequestBean.getParam())) {
				
				log.debug("LTC HOME PAGE 1");
				ltcWaterRequestBean = ltcWaterRequestBusiness.getEmpDetails(ltcWaterRequestBean);
				ltcWaterRequestBean = ltcWaterRequestBusiness.getDeptDetails(ltcWaterRequestBean);
				ltcWaterRequestBean = ltcWaterRequestBusiness.getltcYearsList(ltcWaterRequestBean);
				ltcWaterRequestBean.setHomeTownAddress(ltcApprovalRequestDAO.getHometownAddress(ltcWaterRequestBean.getSfid()));
				ltcWaterRequestBean = ltcWaterRequestBusiness.getLTCDetails(ltcWaterRequestBean);
				session.setAttribute(CPSConstants.LTCFAMILYMEMBERLIST, (JSONArray) JSONSerializer.toJSON(ltcWaterRequestBean.getFamilyMemberDetails()));
				viewName = CPSConstants.LTCWATERREQUESTHOME;
			}else if(CPSUtils.compareStrings(CPSConstants.LTCSUBMITREQUEST, ltcWaterRequestBean.getParam())){
				log.debug("LTC HOME PAGE  2");
				viewName = CPSConstants.REQUESTRESULTPAGE;
				session.setAttribute(CPSConstants.EMPLOYEEDETAILS, ltcWaterRequestBusiness.getEmpDetails(ltcWaterRequestBean));
				LTCWaterRequestProcessBean ltcWaterRequestProcessBean = new LTCWaterRequestProcessBean();
				BeanUtils.copyProperties(ltcWaterRequestProcessBean, ltcWaterRequestBean);
				ltcWaterRequestProcessBean.setSfID((String) session.getAttribute(CPSConstants.SFID));
				ltcWaterRequestProcessBean.setIpAddress(request.getRemoteAddr());
				ltcWaterRequestProcessBean.setSession(request.getSession(true));
				ltcWaterRequestBean.setResult(ltcWaterRequestProcess.initWorkflow(ltcWaterRequestProcessBean));
				ltcWaterRequestProcess.saveltcdetails(ltcWaterRequestProcessBean);
				ltcWaterRequestBean.setRequestID(ltcWaterRequestProcessBean.getRequestID());
				session.setAttribute("ltcWaterAprovalRequestID",ltcWaterRequestBean.getRequestID());
				session.setAttribute("ltcAprovalRequestID",ltcWaterRequestBean.getRequestID());
			} else if(CPSUtils.compareStrings(CPSConstants.LTCWATERSETTLEMENTS, ltcWaterRequestBean.getParam())){
				log.debug("LTC HOME PAGE  3 25/05/2016");
				session.setAttribute(CPSConstants.LTCFINADVLIST, (JSONArray) JSONSerializer.toJSON(ltcWaterRequestProcess.getFinAdvDetails(ltcWaterRequestBean)));
				log.debug("LTCWaterRequestController --> added 11/05/2016    1");
				session.setAttribute(CPSConstants.LTCWATERSETTLEMENTLIST, (JSONArray) JSONSerializer.toJSON(ltcWaterRequestProcess.getLTCWaterSettlementDetails(ltcWaterRequestBean)));
				log.debug("LTCWaterRequestController --> added 11/05/2016    2");
				session.setAttribute(CPSConstants.LTCWATERREIMBURSEMENTLIST, (JSONArray) JSONSerializer.toJSON(ltcWaterRequestProcess.getLTCWaterReimbursementDetails(ltcWaterRequestBean)));
				viewName = CPSConstants.LTCWATERFINSETTLEMENTS;
			}
			else if(CPSUtils.compareStrings(CPSConstants.LTCWATERREUESTDETAILS, ltcWaterRequestBean.getParam())){
				log.debug("LTC HOME PAGE 4  25/05/2016");
				LTCWaterRequestProcessBean ltcWaterRequestProcessBean = new LTCWaterRequestProcessBean();
				//ltcWaterRequestBean = ltcWaterRequestBusiness.getEmpDetails(ltcWaterRequestBean);
				//ltcWaterRequestBean = ltcWaterRequestBusiness.getDeptDetails(ltcWaterRequestBean);
				ltcWaterRequestBean = ltcWaterRequestBusiness.getEmpDetailsOne(ltcWaterRequestBean);
				ltcWaterRequestBean = ltcWaterRequestBusiness.getDeptDetailsOne(ltcWaterRequestBean);
				
				ltcWaterRequestBean=ltcWaterRequestBusiness.getCurrentWaterReqIdDetails(ltcWaterRequestBean);
				ltcWaterRequestBean = ltcWaterRequestBusiness.getBankNameDetails(ltcWaterRequestBean);
				viewName = CPSConstants.LTCADVANCEUPDATETHOME;
			} else if(CPSUtils.compareStrings(CPSConstants.LTCWATERADVANCEISSUE, ltcWaterRequestBean.getParam())){
				log.debug("LTC HOME PAGE 5  26/05/2016");
				LTCWaterRequestProcessBean ltcWaterRequestProcessBean = new LTCWaterRequestProcessBean();
				BeanUtils.copyProperties(ltcWaterRequestProcessBean, ltcWaterRequestBean);
				ltcWaterRequestBean = ltcWaterRequestBusiness.getEmpDetails(ltcWaterRequestBean);
				ltcWaterRequestBean = ltcWaterRequestBusiness.getDeptDetails(ltcWaterRequestBean);
				ltcWaterRequestBean.setResult(ltcWaterRequestBusiness.updateLTCAdvance(ltcWaterRequestProcessBean));
				viewName = CPSConstants.LTCWATERFINSETTLEMENTS;
			} else if(CPSUtils.compareStrings(CPSConstants.LTCWATERSETTANDREIM, ltcWaterRequestBean.getParam())){
				
				log.debug("LTC HOME PAGE 6  26/05/2016");
				LTCWaterRequestProcessBean ltcWaterRequestProcessBean = new LTCWaterRequestProcessBean();
				BeanUtils.copyProperties(ltcWaterRequestProcessBean, ltcWaterRequestBean);
				ltcWaterRequestBean = ltcWaterRequestBusiness.getEmpDetails(ltcWaterRequestBean);
				ltcWaterRequestBean = ltcWaterRequestBusiness.getDeptDetails(ltcWaterRequestBean);
				session.setAttribute(CPSConstants.LTCFINCESETORREIM, (JSONArray) JSONSerializer.toJSON(ltcWaterRequestProcess.getFinAdvCompletedDetails(ltcWaterRequestBean)));
				//TadaWaterSettelement.jsp
				viewName = CPSConstants.LTCWATERSETTELEMENT;
			}  else if(CPSUtils.compareStrings(CPSConstants.LTCWATERADVCOMPDETAILS, ltcWaterRequestBean.getParam())){
				
				log.debug("LTC HOME PAGE 7  26/05/2016");
				LTCWaterRequestProcessBean ltcWaterRequestProcessBean = new LTCWaterRequestProcessBean();
				BeanUtils.copyProperties(ltcWaterRequestProcessBean, ltcWaterRequestBean);
				ltcWaterRequestBean = ltcWaterRequestBusiness.getEmpDetails(ltcWaterRequestBean);
				ltcWaterRequestBean = ltcWaterRequestBusiness.getDeptDetails(ltcWaterRequestBean);
				ltcWaterRequestBean=ltcWaterRequestBusiness.getCurrentWaterReqIdDetail(ltcWaterRequestBean);
				viewName = CPSConstants.LTCWATERSETTLEMENTTHOME;
				
			}  else if(CPSUtils.compareStrings(CPSConstants.LTCWATERADVCOMPDETAILSFORCANCEL, ltcWaterRequestBean.getParam())){
				
				log.debug("LTC HOME PAGE 14  26/05/2016");
				LTCWaterRequestProcessBean ltcWaterRequestProcessBean = new LTCWaterRequestProcessBean();
				BeanUtils.copyProperties(ltcWaterRequestProcessBean, ltcWaterRequestBean);
				ltcWaterRequestBean = ltcWaterRequestBusiness.getEmpDetails(ltcWaterRequestBean);
				ltcWaterRequestBean = ltcWaterRequestBusiness.getDeptDetails(ltcWaterRequestBean);
				ltcWaterRequestBean=ltcWaterRequestBusiness.getCurrentWaterReqIdDetail(ltcWaterRequestBean);
				viewName = CPSConstants.LTCWATERCANCELREQUEST;
				
			} else if(CPSUtils.compareStrings(CPSConstants.LTCWATERSETTLEMENTREQUEST, ltcWaterRequestBean.getParam())){
				log.debug("LTC HOME PAGE 8");
				LTCWaterRequestProcessBean ltcWaterRequestProcessBean = new LTCWaterRequestProcessBean();
				BeanUtils.copyProperties(ltcWaterRequestProcessBean, ltcWaterRequestBean);
				ltcWaterRequestBean = ltcWaterRequestBusiness.getEmpDetails(ltcWaterRequestBean);
				ltcWaterRequestBean = ltcWaterRequestBusiness.getDeptDetails(ltcWaterRequestBean);
				ltcWaterRequestBean.setResult(ltcWaterRequestProcess.ltcWaterSettlementApply(ltcWaterRequestProcessBean));
				viewName = CPSConstants.LTCWATERSETTELEMENT;
			} else if(CPSUtils.compareStrings(CPSConstants.LTCWATERSETTLEMENTDETAILS, ltcWaterRequestBean.getParam())){
				log.debug("LTC HOME PAGE 9");
				
				LTCWaterRequestProcessBean ltcWaterRequestProcessBean = new LTCWaterRequestProcessBean();
				BeanUtils.copyProperties(ltcWaterRequestProcessBean, ltcWaterRequestBean);
				//ltcWaterRequestBean = ltcWaterRequestBusiness.getEmpDetails(ltcWaterRequestBean);
				//ltcWaterRequestBean = ltcWaterRequestBusiness.getDeptDetails(ltcWaterRequestBean);

				ltcWaterRequestBean = ltcWaterRequestBusiness.getEmpDetailsOne(ltcWaterRequestBean);
				ltcWaterRequestBean = ltcWaterRequestBusiness.getDeptDetailsOne(ltcWaterRequestBean);
				
				ltcWaterRequestBean=ltcWaterRequestBusiness.getCurrentWaterSettlementDetail(ltcWaterRequestBean);
				viewName = CPSConstants.LTCWATERADMINSETTLEMENTHOME;
			} else if(CPSUtils.compareStrings(CPSConstants.LTCWATERADMINSETTLEMENT, ltcWaterRequestBean.getParam())){
				
				log.debug("LTC HOME PAGE 10  26/05/2016");
			LTCWaterRequestProcessBean ltcWaterRequestProcessBean = new LTCWaterRequestProcessBean();
			BeanUtils.copyProperties(ltcWaterRequestProcessBean, ltcWaterRequestBean);
			ltcWaterRequestBean = ltcWaterRequestBusiness.getEmpDetails(ltcWaterRequestBean);
			ltcWaterRequestBean = ltcWaterRequestBusiness.getDeptDetails(ltcWaterRequestBean);
			ltcWaterRequestBean.setResult(ltcWaterRequestProcess.ltcWaterAdminSettlement(ltcWaterRequestProcessBean));
			viewName = CPSConstants.LTCWATERFINSETTLEMENTS;
			
		}  else if(CPSUtils.compareStrings(CPSConstants.LTCWATERREIMBURSEMENTDETAILS, ltcWaterRequestBean.getParam())){
			
			log.debug("LTC HOME PAGE 11");
		LTCWaterRequestProcessBean ltcWaterRequestProcessBean = new LTCWaterRequestProcessBean();
		BeanUtils.copyProperties(ltcWaterRequestProcessBean, ltcWaterRequestBean);
		
		/*ltcWaterRequestBean = ltcWaterRequestBusiness.getEmpDetails(ltcWaterRequestBean);
		ltcWaterRequestBean = ltcWaterRequestBusiness.getDeptDetails(ltcWaterRequestBean);*/
		
		ltcWaterRequestBean = ltcWaterRequestBusiness.getEmpDetailsOne(ltcWaterRequestBean);
		ltcWaterRequestBean = ltcWaterRequestBusiness.getDeptDetailsOne(ltcWaterRequestBean);
		
		ltcWaterRequestBean=ltcWaterRequestBusiness.getCurrentWaterReimbursementDetail(ltcWaterRequestBean);
		ltcWaterRequestBean = ltcWaterRequestBusiness.getBankNameDetails(ltcWaterRequestBean);
		viewName = CPSConstants.LTCWATERADMINREIMBURSEMENTTHOME;
		
		
	}  else if(CPSUtils.compareStrings(CPSConstants.LTCWATERADMINREIMBURSEMENT, ltcWaterRequestBean.getParam())){
		
		log.debug("LTC HOME PAGE 12");
	LTCWaterRequestProcessBean ltcWaterRequestProcessBean = new LTCWaterRequestProcessBean();
	BeanUtils.copyProperties(ltcWaterRequestProcessBean, ltcWaterRequestBean);
	ltcWaterRequestBean = ltcWaterRequestBusiness.getEmpDetails(ltcWaterRequestBean);
	ltcWaterRequestBean = ltcWaterRequestBusiness.getDeptDetails(ltcWaterRequestBean);
	ltcWaterRequestBean.setResult(ltcWaterRequestProcess.ltcWaterAdminReimbursement(ltcWaterRequestProcessBean));
	
	viewName = CPSConstants.LTCWATERFINSETTLEMENTS;
	
			} else if(CPSUtils.compareStrings(CPSConstants.LTCYEARCHCKING, ltcWaterRequestBean.getParam())){
				
				log.debug("LTC HOME PAGE 13");
				session.removeAttribute("ltcYear");
				session.setAttribute("ltcYear", ltcWaterRequestBusiness.getLtcYear(ltcWaterRequestBean.getLtcYear(),ltcWaterRequestBean.getSfid()));
			} else 	if (CPSUtils.compareStrings(CPSConstants.LTCWATERREPORTHOME, ltcWaterRequestBean.getParam())) {
				
				log.debug("LTC HOME PAGE 14");
				
				//ltcWaterRequestBean = ltcWaterRequestBusiness.getEmpDetails(ltcWaterRequestBean);
				//ltcWaterRequestBean = ltcWaterRequestBusiness.getDeptDetails(ltcWaterRequestBean);
				ltcWaterRequestBean = ltcWaterRequestBusiness.getltcYearsList(ltcWaterRequestBean);
				//ltcWaterRequestBean.setHomeTownAddress(ltcApprovalRequestDAO.getHometownAddress(ltcWaterRequestBean.getSfid()));
				//ltcWaterRequestBean = ltcWaterRequestBusiness.getLTCDetails(ltcWaterRequestBean);
				//session.setAttribute(CPSConstants.LTCFAMILYMEMBERLIST, (JSONArray) JSONSerializer.toJSON(ltcWaterRequestBean.getFamilyMemberDetails()));
				viewName = CPSConstants.LTCWATERREPORTHOMEPAGE;
			}

			mav = new ModelAndView(viewName, CPSConstants.LTCWATER,ltcWaterRequestBean);
			if(!CPSUtils.isNullOrEmpty(ltcWaterRequestBean.getResult()))
				if(CPSUtils.compareStrings(ltcWaterRequestBean.getResult(), CPSConstants.SUCCESS) ){
					mav.addObject(CPSConstants.MESSAGE, ltcWaterRequestBean.getResult());
				} else {
					ltcWaterRequestBean.setRemarks(ltcWaterRequestBean.getResult());
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mav;
		
	}
	
	

}
