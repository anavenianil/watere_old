package com.callippus.web.dao.login;

import com.callippus.web.beans.login.LoginBean;

public interface ILoginDAO {
	public LoginBean checkuser(LoginBean user) throws Exception;

	public String saveLogOutDetails(LoginBean loginBean) throws Exception;

}
