<!-- Begin : TrainingMasterDataList.jsp -->
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
			<display:table name="${sessionScope.jsonTrainingMstDataList}" excludedParams="*"
				export="false" class="list" requestURI="" id="dataList" pagesize="20"
				sort="list">
				
					<c:if test="${sessionScope.trainingMstType eq 'trainingType' || sessionScope.trainingMstType eq 'courseSubjCategory'  || sessionScope.trainingMstType eq 'courseSubjSubCategory'}">
					<display:column title="Order No" style="width:30%">${dataList.orderNo}</display:column>
					<display:column title="${sessionScope.trainingMstName}" style="width:30%">${dataList.name}</display:column>
					<display:column title="Description" style="width:30%">${dataList.description}</display:column>
					</c:if>
					<c:if test="${sessionScope.trainingMstType eq 'trainingInistitute'}">
				
					<display:column title="Training Institute" style="width:30%">${dataList.name}</display:column>
					<display:column title="Short Name" style="width:30%">${dataList.shortName}</display:column>
					<display:column title="Training Type" style="width:30%">${dataList.trainingType}</display:column>
					<display:column title="Region" style="width:30%">${dataList.trainingRegion}</display:column>
					
					</c:if>
					<c:if test="${sessionScope.trainingMstType eq 'courseSubjCategory'}">
					<display:column title="Category" style="width:30%">${dataList.category}</display:column>
					</c:if>
					<c:if test="${sessionScope.trainingMstType eq 'courseSubjSubCategory'}">
					<display:column title="Subject Category" style="width:30%">${dataList.courseSubjCategory}</display:column>
					</c:if>
					<c:if test="${sessionScope.trainingMstType eq 'trainingVenue'}">
				
					<display:column title="Training Venue" style="width:30%">${dataList.city}</display:column>
					<display:column title="Training Institute" style="width:30%">${dataList.trainingInistitute}</display:column>
					<display:column title="City" style="width:30%">${dataList.city}</display:column>
					<display:column title="Address" style="width:30%">${dataList.address}</display:column>
					
					</c:if>
					<c:if test="${sessionScope.trainingMstType eq 'trainingType'}">
						<display:column style="width:10%;text-align:center" title="Create Region">
						<a href="javascript:getTrainingRegionBasedOnTrainingType('${dataList.id}',jsonMasterDataList);">Region</a>
					 	</display:column>
					</c:if>
					<c:if test="${sessionScope.trainingMstType eq 'trainingInistitute'}">
						<display:column style="width:10%;text-align:center" title="Create Venue">
						<a href="javascript:getTrainingVenueBasedOnTrainingInistitute('${dataList.id}',jsonMasterDataList);">Venue</a>
					 	</display:column>
					</c:if>
		
						<display:column style="width:10%;text-align:center" title="Edit">
						<img src="./images/edit.gif"
							id="masterListEdit" onclick="editTrainingData(type,jsonMasterDataList,'${dataList.id}',jsonTrainingInistituteList,jsonCourseSubjCategoryList)"/>
					 	</display:column>
						<display:column style="width:10%;text-align:center" title="Delete">
							<img src="./images/delete.gif"
								onclick="deleteTrainingMaster('${dataList.id}','${sessionScope.trainingMstType}')" />
						</display:column>	
					
			</display:table>
			</div>
		<script>
			$jq( function(){
				   $jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
				   
				})
				var type='<c:out value='${sessionScope.trainingMstType}'/>';
			jsonMasterDataList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonTrainingMstDataList") %>;
			jsonTrainingInistituteList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonTrainingInistituteList") %>;
			jsonCourseSubjCategoryList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonCourseSubjCategoryList") %>;
		
		</script>
		
		
	</div>
</div>
<!-- End : TrainingMasterDataList.jsp -->