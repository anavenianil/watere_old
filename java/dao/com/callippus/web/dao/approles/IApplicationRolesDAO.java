package com.callippus.web.dao.approles;

import java.util.List;

import com.callippus.web.beans.approles.ApplicationRolesBean;

public interface IApplicationRolesDAO {

	public List getApplicationRolesMappingList(ApplicationRolesBean appRolesBean) throws Exception;

	public String submitApplicationRoles(ApplicationRolesBean appRolesBean) throws Exception;

	public String deleteApplicationRoles(ApplicationRolesBean appRolesBean) throws Exception;
}
