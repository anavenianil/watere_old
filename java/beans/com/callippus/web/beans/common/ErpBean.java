package com.callippus.web.beans.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.callippus.web.controller.address.AddressController;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;

public class ErpBean {
	private static Log log = LogFactory.getLog(AddressController.class);
	private String param;
	private HttpServletRequest request;
	private String sfid;
	HttpSession session = null;

	public ErpBean() {
	}

	public ErpBean(HttpServletRequest request) {
		this.request = request;
		session = request.getSession(true);
		param = request.getParameter(CPSConstants.PARAM);
		sfid = request.getParameter(CPSConstants.SFID);
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getSfid() {
		return sfid;
	}

	public void setSfid(String sfid) {
		this.sfid = sfid;
	}

	public void setValues() throws Exception {
		try {
			if (CPSUtils.compareStrings(CPSConstants.VIEW, param)) {
				sfid = request.getParameter(CPSConstants.SFID);
			}
			else if (CPSUtils.compareStrings(CPSConstants.HOME, param)) {
				if (CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.VIEW_SFID))) {
					log.debug("AddressController --> onSubmit --> param=home");
					setSfid(session.getAttribute(CPSConstants.SFID).toString());
				} else {
					param = CPSConstants.VIEW;
					setParam(param);
					sfid = (String) session.getAttribute(CPSConstants.VIEW_SFID);
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}
}