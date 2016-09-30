package com.callippus.web.controller.exception;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.exception.JDBCConnectionException;

import sun.reflect.Reflection;

import com.callippus.web.controller.common.CPSConstants;

public class SetErpException {
	private static Log log = LogFactory.getLog("com.callippus.web.controller.exception.SetErpException");

	public static ErpException setValues(Exception ex) throws Exception {
		//to stop SessionTimedOutException related log in logfile if condition is added. 
		if(ex.getClass()!=new SessionTimedOutException().getClass())
		{
		log.error("Exception accured class is >>>>>>>>>>>>>>>>>>>>" + Reflection.getCallerClass(2).getName());
		log.error("Exception is :>>>>>>>>>> " + ex);
		log.debug("Exception>>>>>>>>>>>>>>>>>>>" + getStackTraceAsString(ex));
		}
		ex.printStackTrace();

		// Rollback
		com.callippus.web.controller.common.HibernateUtils.rollbackTransaction();

		String codeToSet = CPSConstants.ThrowableCode;

		if (ex instanceof AccessDeniedException) {
			codeToSet = CPSConstants.AccessDeniedExceptionCode;
		} else if (ex instanceof NullPointerException) {
			codeToSet = CPSConstants.NullPointerException;
		} else if (ex instanceof SQLException) {
			codeToSet = CPSConstants.SQLExceptionCode;
		} else if (ex instanceof ClassNotFoundException) {
			codeToSet = CPSConstants.ClassNotFoundExceptionCode;
		} else if (ex instanceof NumberFormatException) {
			codeToSet = CPSConstants.NubmerFormatExceptionCode;
		} else if (ex instanceof IOException) {
			codeToSet = CPSConstants.IOExceptionCode;
		} else if (ex instanceof FileNotFoundException) {
			codeToSet = CPSConstants.FileNotFoundExceptionCode;
		} else if (ex instanceof SessionTimedOutException) {
			codeToSet = CPSConstants.SessionTimedOutExceptionCode;
		} else if (ex instanceof ArrayIndexOutOfBoundsException) {
			codeToSet = CPSConstants.ArrayIndexOutOfBoundsException;
		} else if (ex instanceof NoSuchFieldException) {
			codeToSet = CPSConstants.NoSuchFieldException;
		} else if (ex instanceof JDBCConnectionException) {
			codeToSet = CPSConstants.JDBCConnectionCode;
		} else if (ex instanceof GenericJDBCException) {
			codeToSet = CPSConstants.GenericJDBCExceptionCode;
		} else if (ex instanceof Exception) {
			codeToSet = CPSConstants.ExceptionCode;
		}

		ErpException eexp = new ErpException();
		ResultStatus rstatus = new ResultStatus(codeToSet);
		if (ex.getMessage() != null)
			rstatus.setOrigin(ex.getMessage());

		rstatus.setErrorDescription(ex.toString());
		rstatus.setErrorCode(codeToSet);

		eexp.setResultStatus(rstatus);

		return eexp;
	}

	public static ErpException setException(Exception ex) throws Exception {
		try {
			return (ErpException) ex;
		} catch (Exception e) {
			return setValues(ex);
		}
	}

	public static String getStackTraceAsString(Throwable thr) {
		if (thr != null) {
			StringWriter swrt = new StringWriter();
			PrintWriter pwrt = new PrintWriter(swrt);
			thr.printStackTrace(pwrt);
			return swrt.toString();
		} else {
			return null;
		}
	}
}
