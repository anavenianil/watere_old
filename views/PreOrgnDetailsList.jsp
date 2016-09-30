<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : PreOrgnDetailsList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<div class="line">
	<c:if test="${message eq 'success'}"><div class="myStyle success"><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'update'}"><div class="myStyle success"><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'delete'}"><div class="myStyle success"><spring:message code="delete"/></div></c:if> 
	<c:if test="${message eq 'failure'}"><div class="myStyle failure"><spring:message code="failure"/></div></c:if>
</div>
<aa:zone name="orgnDetailsTable">
	<display:table name="sessionScope.organisationList" excludedParams="*"
		export="false" class="list" requestURI="" id="orgnList" pagesize="10"
		sort="list">
		
		<display:column  title='Organisation Type' style="width:20%;">${orgnList.orgType}</display:column>
		<display:column  title='Organisation Name' style="width:25%;">${orgnList.orgName} (${orgnList.fromDate} to ${orgnList.toDate})</display:column>
		<display:column  title='division' style="width:23%;">&nbsp;${orgnList.divisionName}</display:column>
		<display:column  title='Job Description' style="width:20%;">&nbsp;${orgnList.jobDescription}</display:column>
		<display:column style="width:6%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit"
				onclick="editPreOrgnDetails(${orgnList.id})"/>
		</display:column>
		<display:column style="width:6%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Edit"
				onclick="deletePreOrgnDetails(${orgnList.id})"/>
				
		</display:column>
	</display:table>
</aa:zone>
<script>displayPaging("orgnDetailsTable","orgnList");
preOrgJSONList = <%= (net.sf.json.JSONArray)session.getAttribute("preOrgListJSON") %>;
</script>
<!-- End :  PreOrgnDetailsList.jsp -->
