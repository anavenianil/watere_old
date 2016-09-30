package com.callippus.web.controller.letterNumberFormat;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

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

import com.callippus.web.beans.dto.OrgInstanceDTO;
import com.callippus.web.beans.letterNumberFormat.IONDTO;
import com.callippus.web.beans.letterNumberFormat.LetterNumberFormatBean;
import com.callippus.web.business.letterNumberFormat.LetterNumberFormatBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;


@Controller
@RequestMapping("/ion")
@SessionAttributes
public class IONController {

	private static Log log = LogFactory.getLog(IONController.class);
	@Autowired
	private LetterNumberFormatBusiness letterNumberBusiness;
	
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView onSubmit(@ModelAttribute("ion") LetterNumberFormatBean letterNumberFormatBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		String viewName = "";
		String message = "";
		Map<String,String> modelMap = null;
		

		try { 
			//ErpAction.userChecks(request); 
			HttpSession session = request.getSession(true);
			if (CPSUtils.compareStrings(CPSConstants.PAGING, letterNumberFormatBean.getParam())) {
				mav = new ModelAndView(session.getAttribute(CPSConstants.RETURN).toString(), "ion", letterNumberFormatBean);
				return mav;
			}
			
			letterNumberFormatBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
			if(CPSUtils.compareStrings("noticeHome", request.getParameter(CPSConstants.PARAM)))
			{
				List<IONDTO> noticeBoardList = null;
				
				log.debug("IONController --> onSubmit --> param=noticeHome");
				viewName = "NoticeBoardDetails";
				 
				List<OrgInstanceDTO> userOrgRoleList = letterNumberBusiness.getUserOrgRoleList(letterNumberFormatBean); 
				if(!CPSUtils.checkList(userOrgRoleList))
				{
			    noticeBoardList = letterNumberBusiness.getNoticeBoardList(letterNumberFormatBean);
				}//List<String> level1SfidList = letterNumberBusiness.getLevel1SfidList(letterNumberFormatBean);
				if(!CPSUtils.isNull(session.getAttribute("userOrgRoleList")))session.removeAttribute("userOrgRoleList");
				if(CPSUtils.checkList(userOrgRoleList))
				session.setAttribute("userOrgRoleList", userOrgRoleList);
				
				if(!CPSUtils.isNull(session.getAttribute("jsonNoticeBoardList")))session.removeAttribute("jsonNoticeBoardList");
				
				
				if(!CPSUtils.checkList(userOrgRoleList))
				{
					session.setAttribute("noticeBoardList", noticeBoardList);
				
					if(CPSUtils.checkList(noticeBoardList))
					{
					session.setAttribute("jsonNoticeBoardList", (JSONArray)JSONSerializer.toJSON(noticeBoardList));
					}
				}
				
				
				
					
			}
			else if(CPSUtils.compareStrings("getLevel1SfidList", request.getParameter(CPSConstants.PARAM)))
			{
				log.debug("IONController-->onSubmit-->param=noticeBoard");
				viewName = "LetterNumberION";
				List<String> level1SfidList = letterNumberBusiness.getLevel1SfidList(letterNumberFormatBean);
				session.setAttribute("level1SfidList", level1SfidList);
				if(!CPSUtils.isNull(session.getAttribute("jsonLevel1SfidList")))session.removeAttribute("jsonLevel1SfidList");
				if(CPSUtils.checkList(level1SfidList))
				{
					session.setAttribute("jsonLevel1SfidList", (JSONArray)JSONSerializer.toJSON(level1SfidList));
				}
				
			} 
			else if(CPSUtils.compareStrings("getNoticeBoardList", request.getParameter(CPSConstants.PARAM)))
			{
				log.debug("IONController-->onSubmit-->param=getNoticeBoardList");
				viewName = "NoticeBoardDataList";
				if(!CPSUtils.isNull(session.getAttribute("jsonNoticeBoardList")))session.removeAttribute("jsonNoticeBoardList");
				
				letterNumberFormatBean.setType("type");
				List noticeBoardList = letterNumberBusiness.getNoticeBoardList(letterNumberFormatBean); 
				
				
			if(!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getRefOrgRoleId()))
				{
				session.setAttribute("noticeBoardList", noticeBoardList);
				if(CPSUtils.checkList(noticeBoardList))
				{
					session.setAttribute("jsonNoticeBoardList", (JSONArray)JSONSerializer.toJSON(noticeBoardList));
				}
				}
				
			}
			if(CPSUtils.compareStrings("manageIONCirculationDetails", request.getParameter(CPSConstants.PARAM)))
			{
				log.debug("IONController --> onSubmit --> param=manageIONCirculationDetails");
				viewName = "NoticeBoardDataList";
				message = letterNumberBusiness.manageIONCirculationDetails(letterNumberFormatBean);
				letterNumberFormatBean.setType("type");
				List noticeBoardList = letterNumberBusiness.getNoticeBoardList(letterNumberFormatBean); 
				
				if(!CPSUtils.isNull(session.getAttribute("jsonNoticeBoardList")))session.removeAttribute("jsonNoticeBoardList");
				if(CPSUtils.checkList(noticeBoardList))
				{
					session.setAttribute("jsonNoticeBoardList", (JSONArray)JSONSerializer.toJSON(noticeBoardList));
				}
				
				
			}
			
			mav = new ModelAndView(viewName, "ion", letterNumberFormatBean);
			mav.addObject(CPSConstants.MESSAGE, message);
			mav.addAllObjects(modelMap);
			session.setAttribute(CPSConstants.RETURN, viewName);
			
		

		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
	

}


