<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : EmpPaySlipMonthList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div>
<form:form method="post" commandName="payBill">
<form:select path="paySlipMonth" id="paySlipMonth" cssStyle="width:90px;">
            <form:option value="select">Select</form:option>
			<form:options items="${payBill.paySlipMonthList}"/>
 </form:select></form:form>
</div>
<!-- End : EmpPaySlipMonthList.jsp -->
