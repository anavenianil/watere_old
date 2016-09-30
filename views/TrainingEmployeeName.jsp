<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/spring-form" prefix="form"%>

	<c:if test="${message=='empNotExists'}"> <span class="failure" id="employeeName"><spring:message code="empNotExists"/></span></c:if>
	<c:if test="${message=='employeeexists'}"> 
 	<div class="leftmar">Name : ${trainingMaster.empDetails.nameInServiceBook} 
 	</c:if>

