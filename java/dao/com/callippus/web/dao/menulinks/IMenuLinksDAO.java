package com.callippus.web.dao.menulinks;

import java.util.List;

import com.callippus.web.beans.dto.MenuLinksDTO;
import com.callippus.web.beans.dto.RequestRolesMappingDTO;
import com.callippus.web.beans.menulinks.MenuLinksBean;



public interface IMenuLinksDAO {
	public List getSpecificMenuLinks(String applicationRole) throws Exception;

	public String submitLinksMapping(MenuLinksBean menuLinksBean) throws Exception;

	public String saveMenuLinksMapping(MenuLinksBean menuLinksBean) throws Exception;

	public List<MenuLinksBean> getMenuLinksList() throws Exception;

	public List<MenuLinksDTO> getDescriptionList() throws Exception;
	
	public String submitRoleLinksMapping(MenuLinksBean menuLinksBean) throws Exception;
	public List<RequestRolesMappingDTO> getSelectedRequestList(String id) throws Exception;
	//public List getSpecificRoleLinks(String applicationRoleId) throws Exception;
	public List getDeSelectedRequestList(String id) throws Exception;
	
}
