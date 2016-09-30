<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<script type="text/javascript" src="script/aa.js"></script>
<script type="text/javascript" src="script/prototype.js"></script>
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/prototype.js"></script>
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/aa.js"></script>
<div>
	<div>
		<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
		<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
		<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
		<c:if test="${message=='deleted'}"> <span class="failure"><spring:message code="deleted"/></span></c:if>
	</div>
	<aa:zone name="ConfigurationListTable">
   	<display:table name="${sessionScope.jsonConfigurationList}" excludedParams="*"
		export="false" class="list" requestURI="" id="configurationData" pagesize="5"
		sort="list">
		
		<display:column title="Name">${configurationData.name}</display:column>
		<display:column title="Value">${configurationData.value}</display:column>
	<display:column>
			<img src="./images/edit.gif" title="Edit"
				onclick="editAward(jsonConfigurationList,'${configurationData.id}')" />
		</display:column>
		<display:column>
			<img src="./images/delete.gif" title="Delete"
				onclick="deleteAward('${onfigurationData.id}')" />
		</display:column>
	</display:table>
	
</aa:zone>

<script>
	jsonConfigurationList= <%= (net.sf.json.JSONArray)session.getAttribute("jsonConfigurationList") %>
	displayPaging("ConfigurationListTable","configurationData");
</script>
</div>

	


