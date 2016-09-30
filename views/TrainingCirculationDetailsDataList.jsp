<!-- Begin : TrainingCirculationDetailsDataList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>

<div>
	
	<div>
		<div id="Pagination">
			<display:table name="${sessionScope.jsonTrainingMstDataList}" excludedParams="*"
				export="false" class="list" requestURI="" id="dataList" pagesize="10"
				sort="list">
					<display:column title="Training Inistitute" style="width:30%">${dataList.description}</display:column>
					<display:column title="Course Name" style="width:30%">${dataList.course}</display:column>
					<display:column title="Course Start Date" style="width:20%">${dataList.startDate}</display:column>
					<display:column title="Course End Date" style="width:30%">${dataList.endDate}</display:column>
					<display:column style="width:10%;text-align:center" title="Circulation">
					<c:if test="${dataList.lastModifiedBy eq ''}">
						<a href="javascript:getCourseCirculationBasedOnCourseDuration('${dataList.id}');">
						
						Generate Circulation
						</a>
						</c:if>
						<c:if test="${dataList.lastModifiedBy ne null && dataList.lastModifiedBy eq '1'}">
						<a href="javascript:getCourseCirculationBasedOnCourseDuration('${dataList.id}');">
						Edit Circulation
						</a>
						</c:if>
						
						
						
					</display:column>
					<display:column style="width:10%;text-align:center" title="I.O.N">
						<c:if test="${dataList.lastModifiedBy ne null && dataList.lastModifiedBy eq '1'}">
						<a href="javascript:reportION('${dataList.id}');">
						I.O.N
						</a>
						</c:if>
					</display:column>
					 	
			</display:table>
			</div>
		<script>
			$jq( function(){
				   $jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
				   
				})
		
			
			jsonMasterDataList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonTrainingMstDataList") %>;
			
		</script>
		
	</div>
</div>
<!-- End : TrainingCirculationDetailsDataList.jsp -->