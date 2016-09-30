<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:SelectedDistrict.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="script/jquery.js"></script>
<form:form method="post" commandName="address">
					<form:select path="district" id="district" cssClass="formSelect" onmouseover="setSelectWidth('#district')">
						<form:option value="0">Select</form:option>
						<c:forEach items="${districtList}" var="dist">
						<form:option value="${dist.id}">${dist.name}</form:option>
						</c:forEach>
					</form:select>
</form:form>