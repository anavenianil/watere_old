<!-- Begin : LetterNumberMasterDataList.jsp -->
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
			<display:table name="${sessionScope.jsonLetterNumberSeriesList}" excludedParams="*"
				export="false" class="list" requestURI="" id="dataList" pagesize="10"
				sort="list">
					<display:column title="Department Name" style="width:30%">${dataList.deptName}</display:column>
					<display:column title="Department Short Name" style="width:30%">${dataList.deptShortName}</display:column>
					<display:column title="Department Code" style="width:30%">${dataList.deptCode}</display:column>
					<display:column title="Series Start Number" style="width:30%">${dataList.deptSeriesStartNum}</display:column>
					<display:column title="Series Last Number" style="width:30%">${dataList.deptSeriesEndNum}</display:column>
					
					
						<display:column style="width:10%;text-align:center" title="Edit">
						<img src="./images/edit.gif"
							id="masterListEdit" onclick="editLetterNumber(jsonLetterNumberSeriesList,${dataList.id})"/>
					 	</display:column>
						<display:column style="width:10%;text-align:center" title="Delete">
							<img src="./images/delete.gif"
								onclick="deleteLetterNumber('${dataList.id}')" />
						</display:column>	
					
			</display:table>
			</div>
		<script>
			$jq( function(){
				   $jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
				   
				})
		
		 jsonLetterNumberSeriesList = <%=(net.sf.json.JSONArray)session.getAttribute("jsonLetterNumberSeriesList")%>
			
		
			
		</script>
		
	</div>
</div>
<!-- End : LetterNumberMasterDataList.jsp -->