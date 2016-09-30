<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:chagedSfid.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<c:if test="${message eq 'success'}">
										<span class="success"><spring:message code="sfidChange.success"/></span>
									</c:if>
									<c:if test="${message eq 'failure'}">
										<span class="failure"><spring:message code="sfidChange.failed"/></span>
									</c:if>
									
									
<!-- end:chagedSfid.jsp -->				