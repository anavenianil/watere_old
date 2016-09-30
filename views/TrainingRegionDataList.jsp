<!-- Begin : TrainingRegionDataList.jsp -->
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
		<c:if test="${message=='deletefail'}"> <span class="failure"><spring:message code="recordexists"/></span></c:if>
		<c:if test="${message=='delete'}"> <span class="failure"><spring:message code="delete"/></span></c:if>
	</div>
	<div>
		<div id="Pagination">
			<display:table name="${sessionScope.TrainingRegionDataList}" excludedParams="*"
				export="false" class="list" requestURI="" id="dataList" pagesize="20"
				sort="list">
				<display:column title="Training Type" style="width:30%">${dataList.trainingType}</display:column>
					<display:column title="Region Name" style="width:30%">${dataList.name}</display:column>
					<display:column title="Description" style="width:30%">${dataList.description}</display:column>
					<display:column style="width:10%;text-align:center" title="Edit">
						<img src="./images/edit.gif"
							id="masterListEdit" onclick="editTrainingRegion(jsonTrainingRegionDataList,'${dataList.id}')"/>
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
			
			jsonTrainingRegionDataList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonTrainingRegionDataList") %>;
			
			
		
			
			
		</script>
		
	</div>
</div>
<!-- End : TrainingRegionDataList.jsp -->