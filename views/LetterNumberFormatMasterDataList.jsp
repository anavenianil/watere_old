<!-- Begin :LetterNumberFormatMasterDataList.jsp -->
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
			<display:table name="${sessionScope.jsonLetterNumberFormatList}" excludedParams="*"
				export="false" class="list" requestURI="" id="dataList" pagesize="10"
				sort="list">
					<display:column title="Topic" style="width:30%">${dataList.topic}</display:column>
					<display:column title="Short Form" style="width:30%">${dataList.shortForm}</display:column>
					<display:column title="Serial Number" style="width:30%">${dataList.serialNum}</display:column>
					<display:column title="File Type" style="width:30%">${dataList.fileTypeName}</display:column>
					<display:column title="Running Number" style="width:30%"><b style=" color:purple;  font-size: 20pt ">${dataList.runningNum}</b></display:column>
					<display:column title="Increment" style="width:30%"><a href="javascript:getIncrementLetterNumberFormat(${dataList.id},jsonLetterNumberFormatList)">Go & Increment</a></display:column>
					<c:if test="${sessionScope.LetterNumberEditFlag =='flag'}">
						<display:column style="width:10%;text-align:center" title="Edit">
						<img src="./images/edit.gif"
							id="masterListEdit" onclick="editLetterNumberFormat(jsonLetterNumberFormatList,${dataList.id})"/>
					 	</display:column>
						<display:column style="width:10%;text-align:center" title="Delete">
							<img src="./images/delete.gif"
								onclick="deleteLetterNumberFormat('${dataList.id}')" />
						</display:column>	
					</c:if>
			</display:table>
			</div>
		<script>
			$jq( function(){
				   $jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
				   
				})
		
		 jsonLetterNumberFormatList = <%=(net.sf.json.JSONArray)session.getAttribute("jsonLetterNumberFormatList")%>
		 jsonLetterNumberSerisList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonLetterNumberSerisList") %>;		
		
			
		</script>
		
	</div>
</div>
<!-- End : LetterNumberFormatMasterDataList.jsp -->