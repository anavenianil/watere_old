<!-- Begin : IONDetailsDataList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>

<div>
	
	<div>
		<div id="Pagination">
			<display:table name="${sessionScope.jsonIonDetails}" excludedParams="*"
				export="false" class="list" requestURI="" id="dataList" pagesize="10"
				sort="list">
			
					<display:column title="Letter Number" style="width:32%">
					<a style="color:black;"><b>${dataList.letterNumber}</b></a><br>
					<a style="color: gray;">CreatedBy : ${dataList.ionCreatedBy}</a><br>
					<a style="color: gray;">CreatedDate :${dataList.ionCreationDate}</a><br>
					<a style="color: gray;">LastModifiedBy : ${dataList.lastModifiedBy}</a><br>
					<a style="color: gray;">LastModifiedDate : ${dataList.lastModifiedDate}</a><br>
					</display:column>
					<display:column title="Subject" style="width:20%">${dataList.subject}</display:column>
					<display:column title="Forward List" style="width:20%">
					<a style="color: blue">InitiatedBy:</a>${dataList.ionInitiatedSfid}<br><a style="color: blue">ForwardBy:</a>${dataList.ionForwardSfid}<br><a style="color: blue">ApprovedBy:</a>${dataList.ionApprovedSfid}
					</display:column>
					<display:column style="width:20%;text-align:left" title="Circulation">
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
						<%-- newlly added code for conditions --%>
						<c:if test="${(dataList.createdBy ne null) && (dataList.createdBy eq '5')}">
						<a style="color: blue">CirculatedBy:</a>${dataList.lastModifiedBy}<br>
						<a style="color: blue">CirculatedDate:</a>${dataList.lastModifiedDate}<br>
						</c:if>
						
					</display:column>
					
					<display:column style="width:10%;text-align:center" title="DOC ">
						<c:if test="${dataList.createdBy ne null && (dataList.createdBy eq '1' || dataList.createdBy eq '5')}">
						<a href="javascript:getPreview('${dataList.id}','doc');">
						I.O.N
						</a>
						</br>
						</br>
						<a href="javascript:getIONDispatchList('${dataList.id}','doc');">
						Dispatch List
						</a>
						</c:if>
					</display:column>
					<display:column style="width:10%;text-align:center" title="PDF">
						<c:if test="${dataList.createdBy ne null && (dataList.createdBy eq '1' || dataList.createdBy eq '5')}">
						<a href="javascript:getPreview('${dataList.id}','pdf');">
						I.O.N
						</a>
						</br>
						</br>
						<a href="javascript:getIONDispatchList('${dataList.id}','pdf');">
						Dispatch List
						</a>
						</c:if>
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
<!-- End : IONDetailsDataList.jsp -->