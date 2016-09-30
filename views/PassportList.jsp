<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : PassportList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<script type="text/javascript" src="script/aa.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="line">
	<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
	<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
	<c:if test="${message=='update'}"> <span class="success"><spring:message code="success"/></span></c:if>
	<c:if test="${message=='delete'}"> <span class="success"><spring:message code="delete"/></span></c:if>
	<c:if test="${message=='passportTypeExisted'}"> <span class="failure"><spring:message code="recordexists"/></span></c:if>
</div>
<aa:zone name="PassportListTable">
   	<display:table name="${sessionScope.JsonPassportList}" excludedParams="*"
		export="false" class="list" requestURI="" id="passportData" pagesize="10"
		sort="list">
		<display:column title="Passport Type" style="width:15%;">${passportData.passportType}</display:column>
		<display:column title="Passport Number" style="width:20%;">${passportData.passportNumber}</display:column>
		<display:column title="Issued Place" style="width:20%;">${passportData.issuePlace}</display:column>
		<display:column title="Valid Upto Date" style="width:10%;">${passportData.validUpto}</display:column>
		<display:column title="Details" style="width:29%;">&nbsp;${passportData.details}</display:column>
		
		<display:column style="width:8%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit"
				onclick="editPassport('${passportData.id}')" />
		</display:column>
		<display:column style="width:8%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete"
				onclick="deletePassport('${passportData.id}')" />
		</display:column>
	</display:table>
</aa:zone>


<script>
	displayPaging("PassportListTable","passportData");
	clearPassport();
	 JsonPassportList= <%= (net.sf.json.JSONArray)session.getAttribute("JsonPassportList") %>;
</script>

<!-- End : PassportList.jsp -->
