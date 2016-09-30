<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
	
	<select name="doPartNo" id="doPartNo" class="formSelect">
	<option value="">Select</option>
	<c:forEach  var="dopart" items="${DoPartNumber}">
		<option value="${dopart.id}">${dopart.doPartNo}</option>
	</c:forEach> 
	</select>
