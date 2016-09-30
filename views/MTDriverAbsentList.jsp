<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : MTDriverAbsentList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

	<c:if test="${message eq 'success'}"><div class="myStyle success""><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'update'}"><div class="myStyle success"><spring:message code="update"/></div></c:if>
	<c:if test="${message eq 'delete'}"><div class="myStyle success"><spring:message code="delete"/></div></c:if> 
	<c:if test="${message eq 'failure'}"><div class="myStyle failure"><spring:message code="failure"/></div></c:if>
	<c:if test="${message eq 'exist'}"><div class="myStyle failure"">Driver With Given From Date and To Date Already Exist</div></c:if>

<div id="dataTable">
   		<display:table name="${sessionScope.AbsentDriverList}" excludedParams="*"
			export="false" class="list" requestURI="" id="driver" pagesize="10" sort="list">
			<display:column  title='Driver Name' >${driver.diverDetails.driverName}</display:column>
			<display:column  title='From Date' ><fmt:formatDate value="${driver.fromDate}" pattern="dd-MMM-yyyy"/></display:column>
			<display:column  title='From Time' >${driver.fromTime}</display:column>
			<display:column  title='To Date' ><fmt:formatDate value="${driver.toDate}" pattern="dd-MMM-yyyy"/></display:column>
			<display:column  title='To Time' >${driver.toTime}</display:column>
			<display:column  title='Remarks' >${driver.remarks}</display:column>
			<display:column style="width:10%;text-align:center" title="Edit">
				<%--<img src="./images/edit.gif" title="Edit" onclick="editDriverAbsentDetails('${driver.id}','${driver.driverId}','<fmt:formatDate value="${driver.fromDate}" pattern="dd-MMM-yyyy"/>','${driver.fromTime}','<fmt:formatDate value="${driver.toDate}" pattern="dd-MMM-yyyy"/>','${driver.toTime}','${driver.remarks}')" />--%>
			<img src="./images/edit.gif" title="Edit" onclick="editDriverAbsentDetails('${driver.id}','<fmt:formatDate value="${driver.fromDate}" pattern="dd-MMM-yyyy"/>','<fmt:formatDate value="${driver.toDate}" pattern="dd-MMM-yyyy"/>')" />
			</display:column>
			<display:column style="width:10%;text-align:center" title="Delete">
				<img src="./images/delete.gif" title="Delete" onclick="deleteDriverAbsentDetails('${driver.id}')" />
			</display:column>
	</display:table>
</div>

<script>

	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	AbsentDriverListJson	= <%=(net.sf.json.JSONArray)session.getAttribute("AbsentDriverListJson")%>;
	
</script>


<%-- End :  MTDriverAbsentList.jsp --%>
