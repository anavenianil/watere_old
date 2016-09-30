package com.callippus.web.controller.exception;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.callippus.web.controller.common.CPSConstants;

public class ErpAction extends SimpleFormController {
	private static Log log = LogFactory
			.getLog("com.callippus.web.beans.common.ErpAction");

	public static void userChecks(HttpServletRequest request)
			throws SessionTimedOutException, AccessDeniedException, Exception {
		sessionChecks(request);
		roleChecks(request);
	}

	public static void sessionChecks(HttpServletRequest request)
			throws SessionTimedOutException, AccessDeniedException, Exception {
		log.debug("*********ErpAction ****sessionChecks()****"
				+ request.getSession());
		HttpSession session = request.getSession(true);
		if (session.isNew()) {
			log.debug("Throwing Exception because Null");
			session.invalidate();
			throw SetErpException.setValues(new SessionTimedOutException());
		}
	}

	public static void roleChecks(HttpServletRequest request) throws AccessDeniedException, Exception {
		log.debug("::::::::ErpAction:::::::::::::roleChecks()::::::::::");
		String callingClassName = sun.reflect.Reflection.getCallerClass(3).getName();

		HttpSession session = request.getSession();
		ArrayList<String> roleActionClasses = (ArrayList<String>) session.getAttribute(CPSConstants.ROLEACTIONS);
		log.debug(":SFID::>>>>" + session.getAttribute(CPSConstants.SFID).toString());
		log.debug(":Class Name::>>>>" + callingClassName);
		log.debug(":Roles::>>>>" + session.getAttribute(CPSConstants.ROLESLIST));
		log.debug(":::::::::::::Role Classes:::::::::::::" + roleActionClasses);

		if (roleActionClasses.size() > 0) {
			if (!roleActionClasses.contains(callingClassName)) {
				throw SetErpException.setValues(new AccessDeniedException());
			}
		} else {
			throw SetErpException.setValues(new SessionTimedOutException());
		}
	}
	
	public static void roleChecks1(HttpServletRequest request)
			throws AccessDeniedException, Exception {
		log.debug("::::::::ErpAction:::::::::::::roleChecks()::::::::::");
		String callingClassName = "";

		/*for (int i = 0; i < 12; i++) {
			String callingClassPack = "";
			
			callingClassPack = sun.reflect.Reflection.getCallerClass(i).getPackage().getName();

			callingClassName = sun.reflect.Reflection.getCallerClass(i).getName();

			if (!callingClassPack.startsWith("com.callippus.web.controller.exception") && callingClassPack.startsWith("com.callippus.web.controller"))
				break;			
		}
*/		HttpSession session = request.getSession();
		ArrayList<String> roleActionClasses = (ArrayList<String>) session
				.getAttribute(CPSConstants.ROLEACTIONS);
		log.debug(":SFID::>>>>"
				+ session.getAttribute(CPSConstants.SFID).toString());
		log.debug(":Class Name::>>>>" + callingClassName);
		log.debug(":Roles::>>>>" + session.getAttribute(CPSConstants.ROLESLIST));
		log.debug(":::::::::::::Role Classes:::::::::::::" + roleActionClasses);

		if (roleActionClasses.size() > 0) {
			if (!roleActionClasses.contains(callingClassName)) {
				throw SetErpException.setValues(new AccessDeniedException());
			}
		} else {
			throw SetErpException.setValues(new SessionTimedOutException());
		}
	}
}
