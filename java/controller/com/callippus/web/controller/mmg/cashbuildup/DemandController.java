package com.callippus.web.controller.mmg.cashbuildup;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.callippus.web.controller.exception.ErpAction;
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

import com.callippus.web.business.mmg.cashbuildup.DemandBusiness;
import com.callippus.web.business.mmg.cashbuildup.workflow.DemandRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.mmg.cashbuildup.beans.DemandMasterDTO;
import com.callippus.web.mmg.cashbuildup.beans.DemandRequestBean;

@Controller
@RequestMapping("/raiseDemand")
@SessionAttributes
public class DemandController {
	private static Log log = LogFactory.getLog(DemandController.class);
	/** The demand business. */
	@Autowired
	private DemandBusiness demandBusiness;
	@Autowired
	private DemandRequestProcess demandRequestProcess;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute DemandMasterDTO demand, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		log.debug("DemandController on submit");
		String message = "";
		HttpSession session = null;
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);
			if (CPSUtils.compareStrings(CPSConstants.HOME, request.getParameter(CPSConstants.PARAM))) {
				log.debug("DemandController --> onSubmit --> param=home");
				demand = demandBusiness.getMasterData(demand, request);
				session.setAttribute(CPSConstants.DRAFTJSON, demandBusiness.getDraftDetails(CPSConstants.DEMANDSAVEDRAFT, session.getAttribute(CPSConstants.SFID).toString()));
				mav = new ModelAndView(CPSConstants.DEMANDMASTER, CPSConstants.COMMAND, demand);
			} else if (CPSUtils.compareStrings(CPSConstants.SAVEDRAFT, request.getParameter(CPSConstants.PARAM))) {
				log.debug("DemandController --> onSubmit --> param=raiseDemand");
				demand = demandBusiness.raiseDemand(demand, request);
				session.setAttribute(CPSConstants.DRAFTJSON, demandBusiness.getDraftDetails(CPSConstants.DEMANDSAVEDRAFT, session.getAttribute(CPSConstants.SFID).toString()));
				mav = new ModelAndView(CPSConstants.DEMANDDRAFTDETAILS, CPSConstants.DEMAND, demand);
			} else if (CPSUtils.compareStrings(CPSConstants.SAVEITEMS, request.getParameter(CPSConstants.PARAM))) {
				log.debug("DemandController --> onSubmit --> param=addItem");
				message = demandBusiness.saveItem(demand.getJsonValue(), request);
				mav = new ModelAndView(CPSConstants.DEMANDITEMS, CPSConstants.DEMAND, demand);
				mav.addObject(CPSConstants.MESSAGE, message);
			} else if (CPSUtils.compareStrings(CPSConstants.PREVIEW, request.getParameter(CPSConstants.PARAM))) {
				log.debug("DemandController --> onSubmit --> param=preview");
				// demand=demandBusiness.getMasterData(demand,request);
				if (!CPSUtils.isNullOrEmpty(request.getParameter("demandPreviewJson")))
					session.setAttribute("demandPreviewJson", JSONSerializer.toJSON(request.getParameter("demandPreviewJson")));
				mav = new ModelAndView(CPSConstants.DEMANDPREVIEW, CPSConstants.DEMAND, demand);
			} else if (CPSUtils.compareStrings(CPSConstants.GETINVHOLDER, request.getParameter(CPSConstants.PARAM))) {
				log.debug("DemandController --> onSubmit --> param=GetInventoryHolder");
				demandBusiness.getInventoryHolder(demand.getInventoryNo(), request, "inventory");
				mav = new ModelAndView(CPSConstants.DEMANDRESULT, CPSConstants.DEMAND, demand);
			} else if (CPSUtils.compareStrings(CPSConstants.SEARCHVALUES, request.getParameter(CPSConstants.PARAM))) {
				log.debug("DemandController --> onSubmit --> param=searchValues");
				demandBusiness.getSearchValues(demand, request);
				mav = new ModelAndView(CPSConstants.DEMANDRESULT, CPSConstants.DEMAND, demand);
			} else if (CPSUtils.compareStrings(CPSConstants.DELETEITEMS, request.getParameter(CPSConstants.PARAM))) {
				log.debug("DemandController --> onSubmit --> param=deleteItem");
				demandBusiness.deleteItem(demand.getJsonValue(), request);
				mav = new ModelAndView(CPSConstants.DEMANDITEMS, CPSConstants.DEMAND, demand);
			} else if (CPSUtils.compareStrings(CPSConstants.SUBMITREQUEST, request.getParameter(CPSConstants.PARAM))) {
				log.debug("DemandController --> onSubmit --> param=submitRequest");
				/**
				 * DemandRequest mapping demand bean toDemandRequestBean
				 */
				DemandRequestBean drb = new DemandRequestBean();
				BeanUtils.copyProperties(drb, demand);
				drb.setItemsJson((JSONArray) session.getAttribute(CPSConstants.DEMANDITEMSJSON));
				drb.setSfID((String) session.getAttribute(CPSConstants.SFID));
				drb.setIpAddress(request.getRemoteAddr());
				if (!CPSUtils.isNull(session.getAttribute("resultType")) && CPSUtils.compareStrings(session.getAttribute("resultType").toString(), "draftDetails")) {
					drb.setStatus(1);
					session.removeAttribute("resultType");
				}
				if (CPSUtils.compareStrings(drb.getType(), "Demand")) {
					String message1 = demandBusiness.checkDemandLimit(drb);
					if (CPSUtils.compareStrings(message1, CPSConstants.SUCCESS))
						message = demandBusiness.checkBudgetAccountHead(drb);
				} else
					message = CPSConstants.SUCCESS;
				if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
					message = demandRequestProcess.initWorkflow(drb);
				}
				mav = new ModelAndView(CPSConstants.REQUESTRESULTPAGE, CPSConstants.DEMAND, demand);
				mav.addObject(CPSConstants.MESSAGE, message);
				return mav;
			} else if (CPSUtils.compareStrings("draftDetails", request.getParameter(CPSConstants.PARAM))) {
				log.debug("DemandController --> onSubmit --> param=deleteItem");
				demand = demandBusiness.getDemandDetails(demand, request);
				session.setAttribute("resultType", "draftDetails");
				mav = new ModelAndView(CPSConstants.DEMANDRESULT, CPSConstants.COMMAND, demand);
			} else if (CPSUtils.compareStrings("cancelDemand", request.getParameter(CPSConstants.PARAM))) {
				log.debug("DemandController --> onSubmit --> param=cancelDemand");
				List<DemandMasterDTO> demandList = demandBusiness.getDemandNumbers();
				session.setAttribute("demandList", demandList);
				mav = new ModelAndView("DemandCancel", CPSConstants.COMMAND, demand);
			} else if (CPSUtils.compareStrings("deleteDraft", request.getParameter(CPSConstants.PARAM))) {
				log.debug("DemandController --> onSubmit --> param=deleteDraft");
				demandBusiness.deleteDraft(demand.getDemandNo());
				session.setAttribute(CPSConstants.DRAFTJSON, demandBusiness.getDraftDetails(CPSConstants.DEMANDSAVEDRAFT, session.getAttribute(CPSConstants.SFID).toString()));
				mav = new ModelAndView("DraftDemandDetails", CPSConstants.DEMAND, demand);
			}
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}
