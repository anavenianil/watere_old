<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : TelephoneEmpDesignationList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
						                    	<div style="float: left; width : 35%">
													<spring:bind path="telephone">
													<form:select path="telephone.fromID" id="SelectRight" size="10" multiple="true" cssStyle="width:250px">
													<form:options items="${telephone.telephoneDesignationSelectedList}" itemLabel="designation" itemValue="desigID"/>
													</form:select>
													</spring:bind>
												</div>
									
				
	<script>multiSelectBox();</script>	
<!-- End : TelephoneEmpDesignationList.jsp-->
