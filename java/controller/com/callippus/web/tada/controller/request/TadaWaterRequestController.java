package com.callippus.web.tada.controller.request;

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

import com.callippus.web.business.requestprocess.TadaWaterRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.tada.beans.request.TadaWaterRequestBean;
import com.callippus.web.tada.business.request.TadaWaterRequestBusiness;
import com.callippus.web.tada.dto.TadaWaterRequestProcessBean;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/tadaWaterRequest.htm")
@SessionAttributes
public class TadaWaterRequestController implements Serializable {
	private static Log log = LogFactory
			.getLog(TadaWaterRequestController.class);

	@Autowired
	TadaWaterRequestBusiness tadaWaterRequestBusiness;
	
	@Autowired
	private TadaWaterRequestProcess tadaWaterRequestProcess;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(
			@ModelAttribute TadaWaterRequestBean tadaWaterRequestBean,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String viewName = null;
		try {
			// ErpAction.userChecks(request);
			session = request.getSession(true);
			tadaWaterRequestBean.setSfid(session
					.getAttribute(CPSConstants.SFID).toString());
			if (CPSUtils.compareStrings(CPSConstants.TADAADVANCECUMREQUEST,
					tadaWaterRequestBean.getParam())) {
				tadaWaterRequestBean = tadaWaterRequestBusiness
						.getEmpDetails(tadaWaterRequestBean);
				tadaWaterRequestBean = tadaWaterRequestBusiness
						.getDeptDetails(tadaWaterRequestBean);

				viewName = CPSConstants.TADAADVANCECUMREQUESTHOME;
			} else if(CPSUtils.compareStrings(CPSConstants.TADAWATERSETTLEMENTS, tadaWaterRequestBean.getParam())){
				
				session.setAttribute(CPSConstants.TADAFINADVLIST, (JSONArray) JSONSerializer.toJSON(tadaWaterRequestProcess.getFinAdvDetails(tadaWaterRequestBean)));
				log.debug("TadaWaterRequestController --> added 11/05/2016    1");
				
				session.setAttribute(CPSConstants.TADAWATERSETTLEMENTLIST, (JSONArray) JSONSerializer.toJSON(tadaWaterRequestProcess.getTadaWaterSettlementDetails(tadaWaterRequestBean)));
				
				log.debug("TadaWaterRequestController --> added 11/05/2016    2");
				session.setAttribute(CPSConstants.TADAWATERREIMBURSEMENTLIST, (JSONArray) JSONSerializer.toJSON(tadaWaterRequestProcess.getTadaWaterReimbursementDetails(tadaWaterRequestBean)));
				
				viewName = CPSConstants.TADAWATERFINSETTLEMENTS;
			} else if(CPSUtils.compareStrings(CPSConstants.TADASUBMITREQUEST, tadaWaterRequestBean.getParam())){
				log.debug("TadaWaterRequestController --> onSubmit --> param=submitTadaAdvanceCumRequest");
				viewName = CPSConstants.REQUESTRESULTPAGE;
				session.setAttribute(CPSConstants.EMPLOYEEDETAILS, tadaWaterRequestBusiness.getEmpDetails(tadaWaterRequestBean));
				TadaWaterRequestProcessBean tadaWaterRequestProcessBean = new TadaWaterRequestProcessBean();
				BeanUtils.copyProperties(tadaWaterRequestProcessBean, tadaWaterRequestBean);
				tadaWaterRequestProcessBean.setSfID((String) session.getAttribute(CPSConstants.SFID));
				tadaWaterRequestProcessBean.setIpAddress(request.getRemoteAddr());
				tadaWaterRequestProcessBean.setSession(request.getSession(true));
				
				tadaWaterRequestBean.setResult(tadaWaterRequestProcess.initWorkflow(tadaWaterRequestProcessBean));
				
				tadaWaterRequestBean.setRequestID(tadaWaterRequestProcessBean.getRequestID());
				session.setAttribute("tadaWaterAprovalRequestID",tadaWaterRequestBean.getRequestID());
				session.setAttribute("tadaAprovalRequestID",tadaWaterRequestBean.getRequestID());
				
			} else if(CPSUtils.compareStrings(CPSConstants.TADAWATERREUESTDETAILS, tadaWaterRequestBean.getParam())){
					log.debug("get watertadarequestdetails  --> param=waterRequestDetails");
					TadaWaterRequestProcessBean tadaWaterRequestProcessBean = new TadaWaterRequestProcessBean();
				//	tadaWaterRequestBean = tadaWaterRequestBusiness.getEmpDetails(tadaWaterRequestBean);
				//	tadaWaterRequestBean = tadaWaterRequestBusiness.getDeptDetails(tadaWaterRequestBean);
					
					tadaWaterRequestBean = tadaWaterRequestBusiness.getEmpDetailsOne(tadaWaterRequestBean);
					tadaWaterRequestBean = tadaWaterRequestBusiness.getDeptDetailsOne(tadaWaterRequestBean);
					tadaWaterRequestBean=tadaWaterRequestBusiness.getCurrentWaterReqIdDetails(tadaWaterRequestBean);
					session.setAttribute(CPSConstants.BANKNAMESLIST, (JSONArray) JSONSerializer.toJSON(tadaWaterRequestProcess.getBankNamesList(tadaWaterRequestBean)));
					tadaWaterRequestBean = tadaWaterRequestBusiness.getBankNameDetails(tadaWaterRequestBean);
					viewName = CPSConstants.TADAADVANCEUPDATETHOME;
					
					
				} else if(CPSUtils.compareStrings(CPSConstants.TADAWATERRUPDATE, tadaWaterRequestBean.getParam())){
					log.debug("tadawater advance upadate");
					TadaWaterRequestProcessBean tadaWaterRequestProcessBean = new TadaWaterRequestProcessBean();
					BeanUtils.copyProperties(tadaWaterRequestProcessBean, tadaWaterRequestBean);
					tadaWaterRequestBean = tadaWaterRequestBusiness.getEmpDetails(tadaWaterRequestBean);
					tadaWaterRequestBean = tadaWaterRequestBusiness.getDeptDetails(tadaWaterRequestBean);
					
					tadaWaterRequestBean.setResult(tadaWaterRequestProcess.updateTadaAdvance(tadaWaterRequestProcessBean));
					
					tadaWaterRequestBean = tadaWaterRequestBusiness.getBankNameDetails(tadaWaterRequestBean);

					viewName = CPSConstants.TADAWATERFINSETTLEMENTS;
					
					
				} else if(CPSUtils.compareStrings(CPSConstants.TADAWATERSETTANDREIM, tadaWaterRequestBean.getParam())){
					log.debug("tadawater  get  settelment/reimbursement details added 01/05/2016  1");
					
					TadaWaterRequestProcessBean tadaWaterRequestProcessBean = new TadaWaterRequestProcessBean();
					BeanUtils.copyProperties(tadaWaterRequestProcessBean, tadaWaterRequestBean);
					tadaWaterRequestBean = tadaWaterRequestBusiness.getEmpDetails(tadaWaterRequestBean);
					tadaWaterRequestBean = tadaWaterRequestBusiness.getDeptDetails(tadaWaterRequestBean);
					
					session.setAttribute(CPSConstants.TADAFINADVLIST, (JSONArray) JSONSerializer.toJSON(tadaWaterRequestProcess.getFinAdvCompletedDetails(tadaWaterRequestBean)));
					
					//TadaWaterSettelement.jsp
					viewName = CPSConstants.TADAWATERSETTELEMENT;
					
					
				}  else if(CPSUtils.compareStrings(CPSConstants.TADAWATERADVCOMPDETAILS, tadaWaterRequestBean.getParam())){
					log.debug("tadawater settlement page added 01/05/2016  2 ");
					
					TadaWaterRequestProcessBean tadaWaterRequestProcessBean = new TadaWaterRequestProcessBean();
					BeanUtils.copyProperties(tadaWaterRequestProcessBean, tadaWaterRequestBean);
					tadaWaterRequestBean = tadaWaterRequestBusiness.getEmpDetails(tadaWaterRequestBean);
					tadaWaterRequestBean = tadaWaterRequestBusiness.getDeptDetails(tadaWaterRequestBean);
					
					tadaWaterRequestBean=tadaWaterRequestBusiness.getCurrentWaterReqIdDetail(tadaWaterRequestBean);
					
					viewName = CPSConstants.TADAWATERSETTLEMENTTHOME;
					
					
				}   else if(CPSUtils.compareStrings(CPSConstants.TADAWATERADVCOMPDETAILSFORCANCEL, tadaWaterRequestBean.getParam())){
					log.debug("tadawater settlement page added 01/05/2016  2 ");
					
					TadaWaterRequestProcessBean tadaWaterRequestProcessBean = new TadaWaterRequestProcessBean();
					BeanUtils.copyProperties(tadaWaterRequestProcessBean, tadaWaterRequestBean);
					tadaWaterRequestBean = tadaWaterRequestBusiness.getEmpDetails(tadaWaterRequestBean);
					tadaWaterRequestBean = tadaWaterRequestBusiness.getDeptDetails(tadaWaterRequestBean);
					
					tadaWaterRequestBean=tadaWaterRequestBusiness.getCurrentWaterReqIdDetail(tadaWaterRequestBean);
					
					viewName = CPSConstants.TADASETTLEMENTCANECLPAGE;
					
					
				} 
				
				else if(CPSUtils.compareStrings(CPSConstants.TADAWATERSETTLEMENTREQUEST, tadaWaterRequestBean.getParam())){
					log.debug("tadawater settlement page added 01/05/2016  3 ");
					
					log.debug("tadawater advance upadate");
					TadaWaterRequestProcessBean tadaWaterRequestProcessBean = new TadaWaterRequestProcessBean();
					BeanUtils.copyProperties(tadaWaterRequestProcessBean, tadaWaterRequestBean);
					tadaWaterRequestBean = tadaWaterRequestBusiness.getEmpDetails(tadaWaterRequestBean);
					tadaWaterRequestBean = tadaWaterRequestBusiness.getDeptDetails(tadaWaterRequestBean);
					
					tadaWaterRequestBean.setResult(tadaWaterRequestProcess.tadaWaterSettlementApply(tadaWaterRequestProcessBean));

					viewName = CPSConstants.TADAWATERSETTELEMENT;
					
					
				} else if(CPSUtils.compareStrings(CPSConstants.TADAWATERSETTLEMENTDETAILS, tadaWaterRequestBean.getParam())){
						log.debug("tadawater gettadaWaterSettlementDetails 11/05/2016");
						
						TadaWaterRequestProcessBean tadaWaterRequestProcessBean = new TadaWaterRequestProcessBean();
						BeanUtils.copyProperties(tadaWaterRequestProcessBean, tadaWaterRequestBean);
						//tadaWaterRequestBean = tadaWaterRequestBusiness.getEmpDetails(tadaWaterRequestBean);
						//tadaWaterRequestBean = tadaWaterRequestBusiness.getDeptDetails(tadaWaterRequestBean);
						
						tadaWaterRequestBean = tadaWaterRequestBusiness.getEmpDetailsOne(tadaWaterRequestBean);
						tadaWaterRequestBean = tadaWaterRequestBusiness.getDeptDetailsOne(tadaWaterRequestBean);
						
						tadaWaterRequestBean=tadaWaterRequestBusiness.getCurrentWaterSettlementDetail(tadaWaterRequestBean);
						
						viewName = CPSConstants.TADAWATERADMINSETTLEMENTHOME;
						
						
					} else if(CPSUtils.compareStrings(CPSConstants.TADAWATERREIMBURSEMENTDETAILS, tadaWaterRequestBean.getParam())){
						
					log.debug("tadawater gettadaWaterreimbursementDetails 11/05/2016 end of the day");
					
					
					TadaWaterRequestProcessBean tadaWaterRequestProcessBean = new TadaWaterRequestProcessBean();
					BeanUtils.copyProperties(tadaWaterRequestProcessBean, tadaWaterRequestBean);
					//tadaWaterRequestBean = tadaWaterRequestBusiness.getEmpDetails(tadaWaterRequestBean);
					//tadaWaterRequestBean = tadaWaterRequestBusiness.getDeptDetails(tadaWaterRequestBean);
					
					tadaWaterRequestBean = tadaWaterRequestBusiness.getEmpDetailsOne(tadaWaterRequestBean);
					tadaWaterRequestBean = tadaWaterRequestBusiness.getDeptDetailsOne(tadaWaterRequestBean);
					
					tadaWaterRequestBean=tadaWaterRequestBusiness.getCurrentWaterReimbursementDetail(tadaWaterRequestBean);
					session.setAttribute(CPSConstants.BANKNAMESLIST, (JSONArray) JSONSerializer.toJSON(tadaWaterRequestProcess.getBankNamesList(tadaWaterRequestBean)));
					
					
					tadaWaterRequestBean = tadaWaterRequestBusiness.getBankNameDetails(tadaWaterRequestBean);
					
					viewName = CPSConstants.TADAWATERADMINREIMBURSEMENTTHOME;
					
					
				} else if(CPSUtils.compareStrings(CPSConstants.TADAWATERADMINSETTLEMENT, tadaWaterRequestBean.getParam())){
					
				log.debug("tadawater gettadaWateradminsettlement 12/05/2016 ----1");
				
				TadaWaterRequestProcessBean tadaWaterRequestProcessBean = new TadaWaterRequestProcessBean();
				BeanUtils.copyProperties(tadaWaterRequestProcessBean, tadaWaterRequestBean);
				tadaWaterRequestBean = tadaWaterRequestBusiness.getEmpDetails(tadaWaterRequestBean);
				tadaWaterRequestBean = tadaWaterRequestBusiness.getDeptDetails(tadaWaterRequestBean);
				
				tadaWaterRequestBean.setResult(tadaWaterRequestProcess.tadaWaterAdminSettlement(tadaWaterRequestProcessBean));
				
				viewName = CPSConstants.TADAWATERFINSETTLEMENTS;
				
			} else if(CPSUtils.compareStrings(CPSConstants.TADAWATERADMINREIMBURSEMENT, tadaWaterRequestBean.getParam())){
				
			log.debug("tadawater gettadaWateradminsettlement 12/05/2016 ----2");
			
			TadaWaterRequestProcessBean tadaWaterRequestProcessBean = new TadaWaterRequestProcessBean();
			BeanUtils.copyProperties(tadaWaterRequestProcessBean, tadaWaterRequestBean);
			tadaWaterRequestBean = tadaWaterRequestBusiness.getEmpDetails(tadaWaterRequestBean);
			tadaWaterRequestBean = tadaWaterRequestBusiness.getDeptDetails(tadaWaterRequestBean);
			
			tadaWaterRequestBean.setResult(tadaWaterRequestProcess.tadaWaterAdminReimbursement(tadaWaterRequestProcessBean));
			
			viewName = CPSConstants.TADAWATERFINSETTLEMENTS;
			
		} else 	if (CPSUtils.compareStrings(CPSConstants.TADAWATERREPORTHOME,
				tadaWaterRequestBean.getParam())) {
		//	tadaWaterRequestBean = tadaWaterRequestBusiness.getEmpDetails(tadaWaterRequestBean);
		//	tadaWaterRequestBean = tadaWaterRequestBusiness.getDeptDetails(tadaWaterRequestBean);

			viewName = CPSConstants.TADAWATERREPORTHOMEPAGE;
		}
			
			//tadaWaterSettlementRequest
			mav = new ModelAndView(viewName, CPSConstants.TADA,
					tadaWaterRequestBean);
			
			if(!CPSUtils.isNullOrEmpty(tadaWaterRequestBean.getResult()))
				if(CPSUtils.compareStrings(tadaWaterRequestBean.getResult(), CPSConstants.SUCCESS) || CPSUtils.compareStrings(tadaWaterRequestBean.getResult(), CPSConstants.TDREQUESTSUBMIT) || CPSUtils.compareStrings(tadaWaterRequestBean.getResult(), CPSConstants.TDADVANCESUBMIT) || CPSUtils.compareStrings(tadaWaterRequestBean.getResult(), CPSConstants.TDSETTLEMENTSUBMIT) || CPSUtils.compareStrings(tadaWaterRequestBean.getResult(), CPSConstants.TDREIMSUBMIT)){
					mav.addObject(CPSConstants.MESSAGE, tadaWaterRequestBean.getResult());
				} else {
					tadaWaterRequestBean.setRemarks(tadaWaterRequestBean.getResult());
				}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;

	}

}
