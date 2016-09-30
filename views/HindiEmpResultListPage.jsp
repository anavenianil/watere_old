<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start :HindiEmpResultListPage.jsp -->
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
	<c:if test="${message eq 'duplicate'}"><div class="myStyle failure"><spring:message code="duplicate"/></div></c:if>
<div>
<div id="dataTable">
   	<display:table name="${sessionScope.getResultDetails}" excludedParams="*"
		export="false" class="list" requestURI="" id="hindiResult" pagesize="10" sort="list">
		<display:column  title='SFID' >${hindiResult.sfid}</display:column>
		<display:column  title='Exam Name' >${hindiResult.name}</display:column>
		<display:column  title='Total Marks' >${hindiResult.totalMarks}</display:column>
		<display:column  title='%Of Marks' >${hindiResult.marksPercentage}</display:column>
		<display:column  title='Examination Date' ><fmt:formatDate value="${hindiResult.examDate}" pattern="dd-MMM-yyyy"/></display:column>
		<display:column  title='Result Declared Date' ><fmt:formatDate value="${hindiResult.resultDate}" pattern="dd-MMM-yyyy"/></display:column>
		
		<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="editResultDetails('${hindiResult.id}','${hindiResult.sfid}','${hindiResult.examId}','${hindiResult.totalMarks}','${hindiResult.marksPercentage}')" />
		</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="deleteResultDetails('${hindiResult.id}')" />
		</display:column>
	</display:table>
</div>

<script>

	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	
	
</script>
</div>

<%-- End : HindiEmpResultListPage.jsp --%>
