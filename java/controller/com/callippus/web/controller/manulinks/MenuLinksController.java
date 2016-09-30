package com.callippus.web.controller.manulinks;

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
import org.springframework.web.servlet.view.RedirectView;

import com.callippus.web.beans.menulinks.MenuLinksBean;
import com.callippus.web.business.menulinks.MenuLinksBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@RequestMapping("/menu.htm")
@SessionAttributes
public class MenuLinksController {

	private static Log log = LogFactory.getLog(MenuLinksController.class);
	@Autowired
	private MenuLinksBusiness menuLinksBusiness;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.MENU) MenuLinksBean menuLinksBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String message = "";
		String viewName = null;
		try {

			ErpAction.userChecks(request);
			session = request.getSession(true);
			if (CPSUtils.compareStrings(CPSConstants.PAGING, menuLinksBean.getParam())) {
				mav = new ModelAndView(session.getAttribute(CPSConstants.RETURN).toString(), CPSConstants.MENU, menuLinksBean);
			} else if (CPSUtils.isNullOrEmpty(menuLinksBean.getParam())) {
				menuLinksBean.setParam("menuhome");
			}

			if (CPSUtils.compareStrings("menuhome", menuLinksBean.getParam())) {
				log.debug("MenuLinksController --> onSubmit --> param=menuhome");
				if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
					menuLinksBean = menuLinksBusiness.getMenuLinksMapping();
					session.setAttribute(CPSConstants.APPLICATIONROLESLIST, menuLinksBean.getApplicationRolesList());
					session.setAttribute(CPSConstants.MENULINKSLIST, menuLinksBean.getSelectedMenuLinksList());
					viewName = CPSConstants.MENULINKSMAPPINGPAGE;
					mav = new ModelAndView(viewName, CPSConstants.MENU, menuLinksBean);
				} else {
					session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
				}
			} 
			else if (CPSUtils.compareStrings("getList", menuLinksBean.getParam())) {
				log.debug("MenuLinksController --> onSubmit --> param=getList");
				menuLinksBean = menuLinksBusiness.getSpecificMenuLinks(menuLinksBean.getId());
				session.setAttribute(CPSConstants.MENULINKSLIST, (JSONArray) JSONSerializer.toJSON(menuLinksBean.getSelectedMenuLinksList()));
				session.setAttribute("demenuLinksList", (JSONArray) JSONSerializer.toJSON(menuLinksBean.getDeselectedMenuLinksList()));
				viewName = CPSConstants.MENULINKSLISTPAGE;
				mav = new ModelAndView(viewName, CPSConstants.MENU, menuLinksBean);
			} else if (CPSUtils.compareStrings("mapSubmit", menuLinksBean.getParam())) {
				log.debug("MenuLinksController --> onSubmit --> param=mapSubmit");
				message = menuLinksBusiness.submitLinksMapping(menuLinksBean);
				viewName = CPSConstants.MENULINKSRESULTPAGE;
				mav = new ModelAndView(viewName, CPSConstants.MENU, menuLinksBean);
			}

			/**
			 * Menu Links Master Starts
			 */
			else if (CPSUtils.compareStrings("menuLinkMap", menuLinksBean.getParam())) {
				log.debug("MenuLinksController --> onSubmit --> param=menuLinkMap");
				menuLinksBean = menuLinksBusiness.getDescriptionList(menuLinksBean);
				session.setAttribute("descriptionList", menuLinksBean.getDescriptionList());
				menuLinksBean = menuLinksBusiness.getMenuLinksList(menuLinksBean);
				session.setAttribute("menuLinksList", menuLinksBean.getMenuLinksList());

				viewName = CPSConstants.MENULINKSMASTER;
				mav = new ModelAndView(viewName, CPSConstants.MENU, menuLinksBean);
				session.setAttribute(CPSConstants.RETURN, CPSConstants.MENULINKSMASTERLIST);
			} else if (CPSUtils.compareStrings("manage", menuLinksBean.getParam())) {
				log.debug("MenuLinksController --> onSubmit --> param=manage");
				message = menuLinksBusiness.saveMenuLinksMapping(menuLinksBean);
				viewName = CPSConstants.MENULINKSMASTERLIST;
				menuLinksBean = menuLinksBusiness.getMenuLinksList(menuLinksBean);
				session.setAttribute("menuLinksList", menuLinksBean.getMenuLinksList());
				mav = new ModelAndView(viewName, CPSConstants.MENU, menuLinksBean);
			}
			//Application role mapping
			else if (CPSUtils.compareStrings("REQUESTMAPPING", menuLinksBean.getParam())) {
				log.debug("MenuLinksController --> onSubmit --> param=requestMapping");
				menuLinksBean = menuLinksBusiness.getRequestMapping();
				session.setAttribute(CPSConstants.APPLICATIONROLESLIST, menuLinksBean.getApplicationRolesList());
				session.setAttribute(CPSConstants.REQUESTTYPELIST, menuLinksBusiness.getRequestTypes());
				
                viewName = CPSConstants.REQUESTROLEMAPPING;
				mav = new ModelAndView(viewName, CPSConstants.MENU, menuLinksBean);
				
			}
			else if (CPSUtils.compareStrings("REQUESTSUBMIT", menuLinksBean.getParam())) {
				log.debug("MenuLinksController --> onSubmit --> param=requestSubmit");
				menuLinksBean.setSfid((String)session.getAttribute("sfid"));
				message = menuLinksBusiness.submitRoleLinksMapping(menuLinksBean);
				viewName = CPSConstants.REQUESTROLERESULTPAGE;
				mav = new ModelAndView(viewName, CPSConstants.MENU, menuLinksBean);
				}
			else if (CPSUtils.compareStrings("REQUESTLINKLIST", menuLinksBean.getParam())) {
				log.debug("MenuLinksController --> onSubmit --> param=requestLinkList");
				menuLinksBean = menuLinksBusiness.getSpecificRoleLinks(menuLinksBean.getId());
			    session.setAttribute(CPSConstants.SELECTEDROLESLIST, menuLinksBean.getSelectedRequestList());
				session.setAttribute(CPSConstants.DESELECTEDREQUESTLIST, menuLinksBean.getDeSelectedRequestList());
				
				viewName = CPSConstants.REQUESTSELECTLINKSPAGE;
				mav = new ModelAndView(viewName, CPSConstants.MENU, menuLinksBean);
			}
			//End Application role mapping
			/**
			 * end of Menu Links Master
			 */

			if (!CPSUtils.isNullOrEmpty(message)) {
				mav.addObject(CPSConstants.MESSAGE, message);
			}

		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}