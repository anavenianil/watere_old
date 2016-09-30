<!-- Begin : CirculateIONDataList.jsp-->
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
		<%int i=0; %>
			<display:table name="${sessionScope.jsonIONListToCirculate}" excludedParams="*"
				export="false" class="list" requestURI="" id="dataList" pagesize="10"
				sort="list">
					<display:column title="Letter Number & Date" style="width:30%">${dataList.letterNumber}<br style="text-align: center"><a href="javascript:getPreview('${dataList.id}','pdf');"> PREVIEW ION</a></display:column>
					<display:column title="Subject" style="width:30%">${dataList.subject}</display:column>
					
					<%--<display:column style="width:5%;text-align:center" title="ION">
						<a href="javascript:getPreview('${dataList.id}','pdf');"> View ION</a>
					</display:column>--%>
					<%-- new  column  forwaredList is added --%>
					<display:column style="width:20%;text-align:left" title="Forward List" >
					<c:if test="${(dataList.createdBy ne null) && (dataList.createdBy eq '1')}">
					<a style="color: blue">Initiated By :</a> ${dataList.ionInitiatedSfid}<br>
					<a style="color: blue">Forward By :</a> ${dataList.ionForwardSfid}<br>
					<a style="color: blue">Approved By :</a> ${dataList.ionApprovedSfid}<br>
				</c:if>
				   </display:column>
				   	<display:column style="width:25%;text-align:left" title="Meta Data" >
					<c:if test="${(dataList.createdBy ne null) && (dataList.createdBy eq '1')}">
					<a style="color: blue">CreatedBy :</a> ${dataList.ionCreatedBy}<br>
					<a style="color: blue">CreatedDate :</a> ${dataList.ionCreationDate}<br>
					<a style="color: blue">LastModifiedBy :</a> ${dataList.lastModifiedBy}<br>
					<a style="color: blue">LastModifiedDate :</a> ${dataList.lastModifiedDate}<br>
					</c:if>
				   </display:column>
					<%-- 
					<display:column style="width:5%;text-align:center" title="ION">
						<a href="javascript:getPreview('${dataList.id}','pdf');">ION</a>
					</display:column>
					--%>
					<%-- 
					<display:column title="Circulate ION" style="width:8%;vertical-align:middle">
						<div class="expbutton"> <a href="javascript:manageCirculation('${dataList.id}');"><span>Circulate </span></a></div>
					</display:column>--%>
					
								
					<display:column title="Edit & Circulate ION" style="width:8%;vertical-align:middle">
						<a href="javascript:getIONMasterToCirculate('${dataList.id}');">Edit & Circulate ION</a>
					</display:column>
					
					
			<% i++; %>
					
				
					
			</display:table>
			</div>
		<script>
			$jq( function(){
				   $jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
				   
				})
		
		
			
			
			
		</script>
		
	</div>
</div>
<!-- End : CirculateIONDataList.jsp -->