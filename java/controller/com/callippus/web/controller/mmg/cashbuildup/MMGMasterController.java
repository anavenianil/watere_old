package com.callippus.web.controller.mmg.cashbuildup;

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

import com.callippus.web.business.mmg.cashbuildup.MMGMasterBusiness;
import com.callippus.web.business.mmg.cashbuildup.workflow.InvHolderRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.mmg.cashbuildup.beans.MMGMasterBean;
import com.callippus.web.mmg.cashbuildup.dto.InventoryHolderRequestBean;

@Controller
@RequestMapping("/" + CPSConstants.MMGMASTER)
@SessionAttributes
public class MMGMasterController {

	private static Log log = LogFactory.getLog(MMGMasterController.class);
	@Autowired
	private MMGMasterBusiness mmgMasterBusiness;
	@Autowired
	private InvHolderRequestProcess invHolderRequestProcess;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView onSubmit(@ModelAttribute(CPSConstants.MMGMASTER) MMGMasterBean mmgmasterBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		String viewName = "";
		String message = "";

		try {
			ErpAction.userChecks(request);
			HttpSession session = request.getSession(true);
			if (!CPSUtils.isNullOrEmpty(mmgmasterBean.getType()) && !CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.JSONMASTERLIST))) {
				session.removeAttribute(CPSConstants.JSONMASTERLIST);
			}
			if (!CPSUtils.isNullOrEmpty(mmgmasterBean.getType()))
				session.setAttribute(CPSConstants.MASTERTYPE, mmgmasterBean.getType());
			if (CPSUtils.isNull(request.getParameter(CPSConstants.PARAM)) || CPSUtils.compareStrings(CPSConstants.HOME, request.getParameter(CPSConstants.PARAM))) {
				log.debug("MMGMasterController --> onSubmit --> param=home");
				if (!CPSUtils.isNullOrEmpty(request.getParameter(CPSConstants.TYPE)))
					mmgmasterBean.setType(request.getParameter(CPSConstants.TYPE));
				else if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.MASTERTYPE)))
					mmgmasterBean.setType(session.getAttribute(CPSConstants.MASTERTYPE).toString());
				else
					mmgmasterBean.setType(CPSConstants.TAXTYPE);
				if (CPSUtils.compareStrings(mmgmasterBean.getType(), CPSConstants.MATERIAL))
					viewName = CPSConstants.MMGMATERIALMASTER;
				else if (CPSUtils.compareStrings(mmgmasterBean.getType(), CPSConstants.IVENTHOLDER))
					viewName = CPSConstants.INVENTORYHOLDERMASTER;
				else if (CPSUtils.compareStrings(mmgmasterBean.getType(), CPSConstants.ACCHEAD))
					viewName = CPSConstants.ACCOUNTHEADMASTERJSP;
				else
					viewName = CPSConstants.MMGMASTERDATA;
				if (!CPSUtils.isNullOrEmpty(mmgmasterBean.getType()))
					mmgmasterBean = mmgMasterBusiness.getMMGMasterData(mmgmasterBean);
				if (!CPSUtils.isNullOrEmpty(mmgmasterBean.getType())) {
					if (CPSUtils.compareStrings(mmgmasterBean.getType(), CPSConstants.ITEMSUBCATEGORY)) {
						session.setAttribute(CPSConstants.PARENTLIST, mmgMasterBusiness.getMMGParentDataList(CPSConstants.ITEMCATEGORYDTO));
					} else if (CPSUtils.compareStrings(mmgmasterBean.getType(), CPSConstants.ITEMCODE)) {
						session.setAttribute(CPSConstants.PARENTLIST, mmgMasterBusiness.getMMGParentDataList(CPSConstants.ITEMCATEGORYDTO));
						session.setAttribute(CPSConstants.ITEMPARENTLIST, mmgMasterBusiness.getMMGParentDataList(CPSConstants.ITEMSUBCATEGORYDTO));
					}
					if (CPSUtils.compareStrings(mmgmasterBean.getType(), CPSConstants.ITEMSUBCODE)) {
						session.setAttribute(CPSConstants.PARENTLIST, mmgMasterBusiness.getMMGParentDataList(CPSConstants.ITEMCODEDTO));
					} else if (CPSUtils.compareStrings(mmgmasterBean.getType(), CPSConstants.MATERIAL)) {
						session.setAttribute(CPSConstants.PARENTLIST, mmgMasterBusiness.getMMGParentDataList(CPSConstants.ITEMCATEGORYDTO));
						session.setAttribute(CPSConstants.ITEMPARENTJSONLIST, (JSONArray) JSONSerializer.toJSON(mmgMasterBusiness.getMMGParentDataList(CPSConstants.ITEMSUBCATEGORYDTO)));
						session.setAttribute(CPSConstants.ITEMPARENTLIST, mmgMasterBusiness.getMMGParentDataList(CPSConstants.ITEMSUBCATEGORYDTO));
						session.setAttribute(CPSConstants.ITEMCODEJSONLIST, (JSONArray) JSONSerializer.toJSON(mmgMasterBusiness.getMMGParentDataList(CPSConstants.ITEMCODEDTO)));
						session.setAttribute(CPSConstants.ITEMCODELIST, mmgMasterBusiness.getMMGParentDataList(CPSConstants.ITEMCODEDTO));
						session.setAttribute(CPSConstants.ITEMSUBCODEJSONLIST, (JSONArray) JSONSerializer.toJSON(mmgMasterBusiness.getMMGParentDataList(CPSConstants.ITEMSUBCODEDTO)));
						session.setAttribute(CPSConstants.ITEMSUBCODELIST, mmgMasterBusiness.getMMGParentDataList(CPSConstants.ITEMSUBCODEDTO));
						session.setAttribute(CPSConstants.ITEMCOMPANYLIST, mmgMasterBusiness.getMMGParentDataList(CPSConstants.COMPANYMASTERDTO));
						session.setAttribute(CPSConstants.UOMLIST, mmgMasterBusiness.getMMGParentDataList(CPSConstants.UOMDTO));
					} else if (CPSUtils.compareStrings(mmgmasterBean.getType(), CPSConstants.IVENTHOLDER)) {
						session.setAttribute(CPSConstants.INVENTORYNO, String.valueOf(mmgMasterBusiness.getInventoryNum()));
						session.setAttribute("sfidjson", (net.sf.json.JSONArray) JSONSerializer.toJSON(mmgMasterBusiness.getSfidList()));
						session.setAttribute("projectList", mmgMasterBusiness.getProjectList());
					} else if (CPSUtils.compareStrings(mmgmasterBean.getType(), CPSConstants.ACCHEAD)) {
						session.setAttribute(CPSConstants.PARENTLIST, mmgMasterBusiness.getDivisionList());
						session.setAttribute(CPSConstants.FUNDTYPELIST, mmgMasterBusiness.getFundTypeList());
						session.setAttribute("FinancialYearList", mmgMasterBusiness.getFinancialYearList());
					}
				}

			} else if (CPSUtils.compareStrings(CPSConstants.MANAGE, request.getParameter(CPSConstants.PARAM))) {
				log.debug("MMGMasterController --> onSubmit --> param=manage");
				viewName = CPSConstants.MMGMASTERDATALIST;
				message = mmgMasterBusiness.manageMMGMasterData(mmgmasterBean);
				mmgmasterBean = mmgMasterBusiness.getMMGMasterData(mmgmasterBean);
				if (CPSUtils.compareStrings(mmgmasterBean.getType(), CPSConstants.IVENTHOLDER)) {
					session.setAttribute(CPSConstants.INVENTORYNO, String.valueOf(mmgMasterBusiness.getInventoryNum()));
				}
			} else if (CPSUtils.compareStrings(CPSConstants.DELETE, request.getParameter(CPSConstants.PARAM))) {
				log.debug("MMGMasterController --> onSubmit --> param=delete");
				message = mmgMasterBusiness.deleteMMGMasterData(mmgmasterBean);
				mmgmasterBean = mmgMasterBusiness.getMMGMasterData(mmgmasterBean);
				viewName = CPSConstants.MMGMASTERDATALIST;
			} else if (CPSUtils.compareStrings(CPSConstants.GETSFIDDETAILS, request.getParameter(CPSConstants.PARAM))) {
				log.debug("MMGMasterController --> onSubmit --> param=getSfidDetails");
				viewName = CPSConstants.INVENTORYHOLDERDETAILS;
				mmgmasterBean.setSfid(request.getParameter("mmgsfid"));
				mmgmasterBean = mmgMasterBusiness.getInventoryDetails(mmgmasterBean);
			} else if (CPSUtils.compareStrings(CPSConstants.ACCHEADSEARCH, request.getParameter(CPSConstants.PARAM))) {
				log.debug("MMGMasterController --> onSubmit --> param=accHeadSearch");
				viewName = CPSConstants.MMGMASTERDATALIST;
				mmgmasterBean = mmgMasterBusiness.getMMGMasterData(mmgmasterBean);
			} else if (CPSUtils.compareStrings(CPSConstants.INVENTORYHOLDERHOME, request.getParameter(CPSConstants.PARAM))) {
				log.debug("MMGMasterController --> onSubmit --> param=inventoryHolderHome");
				viewName = "ViewInventoryMaster";
				mmgmasterBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				mmgmasterBean = mmgMasterBusiness.getInventoryHolderDetails(mmgmasterBean);
				session.setAttribute(CPSConstants.MASTERJSON, mmgmasterBean.getInventoryDetails());
			} else if (CPSUtils.compareStrings("editInventHolderDetails", request.getParameter(CPSConstants.PARAM))) {
				log.debug("MMGMasterController --> onSubmit --> param=editInventHolderDetails");
				viewName = "EditInventoryDetails";
				mmgmasterBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				mmgmasterBean = mmgMasterBusiness.inventoryDetails(mmgmasterBean);
				// session.setAttribute(CPSConstants.PARENTLIST, mmgMasterBusiness.getSfidList());
				session.setAttribute("sfidjson", (net.sf.json.JSONArray) JSONSerializer.toJSON(mmgMasterBusiness.getSfidList()));
			} else if (CPSUtils.compareStrings("invHolderRequest", request.getParameter(CPSConstants.PARAM))) {
				log.debug("MMGMasterController --> onSubmit --> param=invHolderRequest");
				InventoryHolderRequestBean invRequestBean = new InventoryHolderRequestBean();
				BeanUtils.copyProperties(invRequestBean, mmgmasterBean);
				invRequestBean.setSfID((String) session.getAttribute(CPSConstants.SFID));
				invRequestBean.setIpAddress(request.getRemoteAddr());
				message = invHolderRequestProcess.initWorkflow(invRequestBean);
				viewName = CPSConstants.REQUESTRESULTPAGE;

			} else if (CPSUtils.compareStrings("mmgConfiguration", request.getParameter(CPSConstants.PARAM))) {
				log.debug("MMGMasterController --> onSubmit --> param=mmgConfiguration");
				viewName = CPSConstants.MMGCONFIGURATIONS;
			} else if (CPSUtils.compareStrings("mmgConfigSubmit", request.getParameter(CPSConstants.PARAM))) {
				log.debug("MMGMasterController --> onSubmit --> param=mmgConfigSubmit");
				viewName = CPSConstants.RESULT;
				mmgmasterBean = mmgMasterBusiness.saveMMGConfigDetails(mmgmasterBean);
				message = mmgmasterBean.getMessage();
			}

			mav = new ModelAndView(viewName, CPSConstants.MMGMASTER, mmgmasterBean);
			mav.addObject(CPSConstants.MESSAGE, message);
			if (CPSUtils.checkList(mmgmasterBean.getMasterDataList())) {
				session.setAttribute(CPSConstants.JSONMASTERLIST, (JSONArray) JSONSerializer.toJSON(mmgmasterBean.getMasterDataList()));
			}

		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}

}
