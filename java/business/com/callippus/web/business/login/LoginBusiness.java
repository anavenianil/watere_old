package com.callippus.web.business.login;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.login.LoginBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.login.ILoginDAO;

@Service
public class LoginBusiness {
	private static Log log = LogFactory.getLog(LoginBusiness.class);
	@Autowired
	private IComonObjectDAO commonDataDAO;
	@Autowired
	private ILoginDAO loginDAO;

	public LoginBean checkUser(LoginBean loginBean, HttpSession session) throws Exception {
		try {
			/*loginBean.setId(String.valueOf(commonDataDAO.getTableID(CPSConstants.LOGINDETAILS, CPSConstants.UPDATE)));*/
			loginBean.setId(commonDataDAO.getSequenceValue("LOGIN_DETAILS_SEQ")); //NARAYANA
			loginBean = loginDAO.checkuser(loginBean);
			if (!CPSUtils.isNull(loginBean.getLoginList())) {
				session.setAttribute(CPSConstants.SFID, (loginBean.getLoginList().get(0)).toString());
				session.setAttribute(CPSConstants.WELCOME, loginBean.getName());
				session.setAttribute(CPSConstants.MENULINKS, loginBean.getMenuLinks());
				session.setAttribute(CPSConstants.ROLELIST, loginBean.getRoleList());
				session.setAttribute("telecheck", loginBean.getTeleCheck());
			}
		} catch (Exception e) {
			throw e;
		}
		return loginBean;
	}

	public LoginBean saveLogOutDetails(LoginBean loginBean) throws Exception {
		try {
			log.debug("LoginBusiness saveLogOutDetails() start");
			/*loginBean.setId(String.valueOf(commonDataDAO.getTableID(CPSConstants.LOGINDETAILS, CPSConstants.UPDATE)));*/
			loginBean.setId(commonDataDAO.getSequenceValue("LOGIN_DETAILS_SEQ")); //NARAYANA
			loginDAO.saveLogOutDetails(loginBean);
			log.debug("LoginBusiness saveLogOutDetails() End");
		} catch (Exception e) {
			throw e;
		}
		return loginBean;
	}
}
