<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : DemoList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>

	<c:if test="${message eq 'success'}"><div class="myStyle success""><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'update'}"><div class="myStyle success"><spring:message code="update"/></div></c:if>
	<c:if test="${message eq 'delete'}"><div class="myStyle success"><spring:message code="delete"/></div></c:if> 
	<c:if test="${message eq 'failure'}"><div class="myStyle failure"><spring:message code="failure"/></div></c:if>
	<c:if test="${message eq 'duplicate'}"><div class="myStyle failure"><spring:message code="duplicate"/></div></c:if>
<div>
<div id="dataTable">
   	<display:table name="${sessionScope.hindiEmployeeDetailsList}" excludedParams="*"
		export="false" class="list" requestURI="" id="hindiEmp" pagesize="10" sort="list">
		<display:column  title='SFID' >${hindiEmp.sfid}</display:column>
		<display:column  title='Mother Tongue' >${hindiEmp.mothertongue}</display:column>
		<display:column  title='Remarks' >${hindiEmp.remarks}</display:column>
		
		
		<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="editEmployeeDetails('${hindiEmp.sfid}','${hindiEmp.remarks}','${hindiEmp.nonEligibleExamId}')"/>
		</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="deleteEmployeeDetails('${hindiEmp.sfid}')" />
		</display:column>
	</display:table>
</div>

<script>

	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	var nonEligibleExamJsonList =  <%= (net.sf.json.JSONArray)session.getAttribute("hindiNonEligibleExamJsonList") %>
	
</script>
</div>

<%-- End : DemoList.jsp --%>
