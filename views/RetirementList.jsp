<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : RetirementList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div class="line">
	<c:if test="${message eq 'success'}"><div class="myStyle success""><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'update'}"><div class="myStyle success"><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'delete'}"><div class="myStyle success"><spring:message code="delete"/></div></c:if> 
	<c:if test="${message eq 'failure'}"><div class="myStyle failure"><spring:message code="failure"/></div></c:if>
	<c:if test="${message eq 'duplicate'}"><div class="myStyle failure"><spring:message code="duplicate"/></div></c:if>
</div>
<aa:zone name="RetirementListTable">
   	<display:table name="${sessionScope.jsonRetirementList}" excludedParams="*"
		export="false" class="list" requestURI="" id="retirementData" pagesize="5"
		sort="list">
		
		<display:column title="ID No" style="width:20%;">&nbsp;${retirementData.idNo}</display:column>
		<display:column title="Retirement Type" style="width:22%;">${retirementData.retirementTypeName}</display:column>
		<display:column title="Retirement Date" style="width:22%;">&nbsp;${retirementData.retirementDate}</display:column>
		<display:column title="Reference Number" style="width:20%;">&nbsp;${retirementData.referenceNumber}</display:column>
		<display:column style="width:8%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit"
				onclick="editRetirement(jsonRetirementList,'${retirementData.id}')" />
		</display:column>
		<display:column style="width:8%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete"
				onclick="deleteRetirement('${retirementData.id}')" />
		</display:column>
	</display:table>
</aa:zone>

<script>
	jsonRetirementList= <%= (net.sf.json.JSONArray)session.getAttribute("jsonRetirementList") %>
	displayPaging("RetirementListTable","retirementData");
</script>



<!-- End : RetirementList.jsp -->
