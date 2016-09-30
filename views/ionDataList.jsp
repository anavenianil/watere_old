<!-- Begin :ionDataList.jsp -->
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
			<display:table name="${sessionScope.jsonIonMstList}" excludedParams="*"
				export="false" class="list" requestURI="" id="dataList" pagesize="10"
				sort="list">
					<display:column title="Letter Number" style="width:30%">${dataList.letterNumber}</display:column>
					<display:column title="Date" style="width:15%">${dataList.ionDate}</display:column>
					<display:column title="Delegation" style="width:30%">${dataList.delegation}</display:column>
					<display:column title="Meta Data" style="width:40%">
					<a style="color: blue;">CreatedBy : </a>${dataList.ionCreatedBy}<br>
					<a style="color: blue;">CreatedDate :</a>${dataList.ionCreationDate}<br>
					<a style="color: blue;">LastModifiedBy :</a> ${dataList.lastModifiedBy}<br>
					<a style="color: blue;">LastModifiedDate :</a> ${dataList.lastModifiedDate}<br>
					</display:column>	
						<display:column style="width:10%;text-align:center" title="Edit">
						<c:if test="${!(dataList.circulationStatus == '5' && dataList.status == '1' || dataList.circulationStatus == '0' && dataList.status == '0')}">
						<img src="./images/edit.gif"
							id="masterListEdit" onclick="editLetterNumberFormatDetails(jsonIonMstList,${dataList.id})"/>
						</c:if>
					 	</display:column>
					 	
					 	<display:column style="width:20%;text-align:left" title="Create/Edit I.O.N">
					  <c:if test="${dataList.createdBy eq '0'}">
						<a href="javascript:getIONMaster('${dataList.id}');">
						
						Generate Draft
						</a>
						</c:if>
						<c:if test="${(dataList.createdBy ne null) && (dataList.createdBy eq '1')}">
						<a href="javascript:getIONMaster('${dataList.id}');">
						Edit Draft
						</a>
						</c:if>
					  </display:column>
					 	
					 	<display:column style="width:10%;text-align:center" title="Delete">
						<c:if test="${!(dataList.circulationStatus == '5' && dataList.status == '1' || dataList.circulationStatus == '0' && dataList.status == '0')}">
							<img src="./images/delete.gif"
								onclick="deleteLetterNumberFormatDetails(jsonIonMstList,'${dataList.id}')" />
						</c:if>	
						<c:if test="${(dataList.circulationStatus == '0' && dataList.status == '0')}">
						  <a style="color: red">Discarded</a>
						</c:if>
						</display:column>
						
					
			</display:table>
			</div>
			
		<script>
			$jq( function(){
				   $jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
				   
				})
		
		 jsonIonMstList = <%=(net.sf.json.JSONArray)session.getAttribute("jsonIonMstList")%>
		 setLetterNumber(<%=(net.sf.json.JSONArray)session.getAttribute("letterNumber")%>,jsonIonMstList);
			
		
			
		</script>
		
	</div>
</div>

<!-- End : ionDataList.jsp -->