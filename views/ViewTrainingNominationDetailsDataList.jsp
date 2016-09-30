<!-- Begin : ViewTrainingNominationDetailsDataList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>


<div>
	<div>
		<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
		<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
		<c:if test="${message=='update'}"> <span class="success"><spring:message code="update"/></span></c:if>
		<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
		<c:if test="${message=='deletefail'}"> <span class="failure"><spring:message code="recordexists"/>${sessionScope.reason}</span></c:if>
		<c:if test="${message=='delete'}"> <span class="failure"><spring:message code="delete"/></span></c:if>
	</div>
	
	<div>
		<div id="Pagination">
			<display:table name="${sessionScope.ViewTrainingNominationDetailsDataList}" excludedParams="*"
				export="false" class="list" requestURI="" id="dataList" pagesize="10"
				sort="list">
				   <display:column title="Req ID" style="width:3%;vertical-align:middle">&nbsp;<a href="javascript:getRequestDetails('${dataList.historyID}','${dataList.requestId}','myRequests','pending','')"><font color="blue">${dataList.requestId}</font></a></display:column>
					<display:column title="Course Name" style="width:30%">${dataList.courseDto.name}</display:column>
					<display:column title="Course Start Date" style="width:20%">${dataList.courseDurationDto.startDate}</display:column>
					<display:column title="Course End Date" style="width:30%">${dataList.courseDurationDto.endDate}</display:column>
					
					<display:column title="Cancel" style="width:30%">
					<c:if test="${dataList.requestType != null}">
					&nbsp;<a href="javascript:cancelNominationRequest('${dataList.historyID}','${dataList.requestId}','myRequests','pending','${dataList.stageID}')"><font color="blue">Cancel</font></a>
					</c:if>
					</display:column>
					<display:column title="Status" style="width:30%">
					${dataList.alias}
					</display:column>
					
			</display:table>
			</div>
		<script>
			$jq( function(){
				   $jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
				   
				})
		
		</script>
		
	</div>
</div>
<!-- End : ViewTrainingNominationDetailsDataList.jsp -->