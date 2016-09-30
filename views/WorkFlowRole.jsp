<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">	
				<c:if test="${name eq 'relation'}">		
					<spring:bind path="workflowmap">
						<form:select path="workflowmap.to" onchange="javascript:ChangeValues(this);" cssClass="formClass">
							<form:option value="0">Select</form:option>
							<form:options items="${relationList}" itemValue="id" itemLabel="name"></form:options> 
						</form:select>						
					</spring:bind>
				</c:if>
				<c:if test="${name eq 'escalationRelation'}">
					<spring:bind path="workflowmap">
						<form:select path="workflowmap.escalteTo"  cssClass="formClass">
							<form:option value="0">Select</form:option>
							<form:options items="${relationList}" itemValue="id" itemLabel="name"></form:options> 
						</form:select>
					</spring:bind>
				</c:if>