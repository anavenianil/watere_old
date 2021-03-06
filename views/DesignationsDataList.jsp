<!-- Begin :DesignationsDataList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/spring-form" prefix="form"%>


<div>
	<div id="resultVal">
		<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
		<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
		<c:if test="${message=='update'}"> <span class="success"><spring:message code="update"/></span></c:if>
		<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
		<c:if test="${message=='deletefail'}"> <span class="failure"><spring:message code="recordexists"/></span></c:if>
		<c:if test="${message=='delete'}"> <span class="failure"><spring:message code="delete"/></span></c:if>
	</div>
	<div>
		<div id="Pagination">
			<display:table name="${sessionScope.jsonTrainingMstDataList}" excludedParams="*"
				export="false" class="list" requestURI="" id="dataList" pagesize="10"
				sort="list">
													
					<display:column title="Category" style="width:5%">${dataList.category}</display:column>
					<display:column title="Designations" style="width:80%">${dataList.name}</display:column>
					<display:column style="width:10%;text-align:center" title="Edit">
						<img src="./images/edit.gif"
							id="masterListEdit" onclick="editCourseDesignation('${dataList.categoryId}')"/>
					 	</display:column>
						<display:column style="width:10%;text-align:center" title="Delete">
							<img src="./images/delete.gif"
								onclick="deleteCourseDesignation('${dataList.categoryId}')" />
						</display:column>
			</display:table>
			</div>
		<script>
			$jq( function(){
				   $jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
				   
				})
			
			jsonCourseDesignationsList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonTrainingMstDataList") %>;
			
			type='<c:out value="${sessionScope.trainingMstType}"/>';	
			
			jsonDesignationList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonDesignationList") %>;
			jsonSelectedDesignationList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonSelectedCourseDesignationList") %>;
			
			setDesignationsForCourse(jsonDesignationList,jsonSelectedDesignationList);
	
		</script>
		
	</div>
</div>
<!-- End : DesignationsDataList.jsp -->