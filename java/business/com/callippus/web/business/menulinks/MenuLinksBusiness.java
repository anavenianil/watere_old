package com.callippus.web.business.menulinks;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.RequestRolesMappingDTO;
import com.callippus.web.beans.dto.RequestWorkDTO;
import com.callippus.web.beans.menulinks.MenuLinksBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.menulinks.IMenuLinksDAO;

@Service
public class MenuLinksBusiness {
	@Autowired
	private IMenuLinksDAO menuLinksDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;

	@SuppressWarnings("unchecked")
	public MenuLinksBean getMenuLinksMapping() throws Exception {
		MenuLinksBean menuLinksBean = null;
		try {
			menuLinksBean = new MenuLinksBean();
			menuLinksBean.setApplicationRolesList(commonDataDAO.getMasterData(CPSConstants.APPLICATIONROLESDTO));
			menuLinksBean.setSelectedMenuLinksList(commonDataDAO.getMasterData(CPSConstants.MENULINKSDTO));
		} catch (Exception e) {
			throw e;
		}
		return menuLinksBean;
	}
	//Request Roles mapping
	@SuppressWarnings("unchecked")
	public MenuLinksBean getRequestMapping() throws Exception {
		MenuLinksBean menuLinksBean = null;
		try {
			menuLinksBean = new MenuLinksBean();
			menuLinksBean.setApplicationRolesList(commonDataDAO.getMasterData(CPSConstants.APPLICATIONROLESDTO));
			} catch (Exception e) {
			throw e;
		}
		return menuLinksBean;
	}
	@SuppressWarnings("unchecked")
	public List<RequestWorkDTO> getRequestTypes() throws Exception {
		List<RequestWorkDTO> reqList = null;
		try {
			reqList = commonDataDAO.getMasterData(CPSConstants.REQUESTWORKDTO);
		} catch (Exception e) {
			throw e;
		}
		return reqList;
	}
	public String submitRoleLinksMapping(MenuLinksBean menuLinksBean) throws Exception {
		String message = null;
		try {
			message = menuLinksDAO.submitRoleLinksMapping(menuLinksBean);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}
	
	@SuppressWarnings("unchecked")
	public MenuLinksBean getSpecificRoleLinks(String id) throws Exception {
	MenuLinksBean menuLinksBean = new MenuLinksBean();
		try {
			menuLinksBean.setSelectedRequestList(menuLinksDAO.getSelectedRequestList(id));
			menuLinksBean.setDeSelectedRequestList(menuLinksDAO.getDeSelectedRequestList(id));
			} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return menuLinksBean;
	}

	//End Request Roles mapping
	@SuppressWarnings("unchecked")
	public MenuLinksBean getSpecificMenuLinks(String applicationRole) throws Exception {
		MenuLinksBean menuLinksBean = null;
		try {
			menuLinksBean = new MenuLinksBean();
			menuLinksBean.setDeselectedMenuLinksList(commonDataDAO.getMasterData(CPSConstants.MENULINKSDTO));
			menuLinksBean.setSelectedMenuLinksList(menuLinksDAO.getSpecificMenuLinks(applicationRole));
		} catch (Exception e) {
			throw e;
		}
		return menuLinksBean;
	}

	public String submitLinksMapping(MenuLinksBean menuLinksBean) throws Exception {
		String message = null;
		try {
			message = menuLinksDAO.submitLinksMapping(menuLinksBean);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public MenuLinksBean getDescriptionList(MenuLinksBean menuLinksBean) throws Exception {
		try {
			menuLinksBean.setDescriptionList(menuLinksDAO.getDescriptionList());
		} catch (Exception e) {
			throw e;
		}
		return menuLinksBean;
	}

	public MenuLinksBean getMenuLinksList(MenuLinksBean menuLinksBean) throws Exception {
		try {
			menuLinksBean.setMenuLinksList(menuLinksDAO.getMenuLinksList());
		} catch (Exception e) {
			throw e;
		}
		return menuLinksBean;
	}

	public String saveMenuLinksMapping(MenuLinksBean menuLinksBean) throws Exception {
		String message = null;
		try {
			message = menuLinksDAO.saveMenuLinksMapping(menuLinksBean);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}
}