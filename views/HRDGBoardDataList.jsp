<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : HRDGBoardDataList.jsp -->
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
			<display:table name="${sessionScope.HRDGBoardDataList}" excludedParams="*"
				export="false" class="list" requestURI="" id="dataList" pagesize="1"
				sort="list">
				
													
					<display:column title="Board Type" style="width:30%">${dataList.boardType}</display:column>
					<display:column title="Year" style="width:10%">${dataList.year}</display:column>
					<display:column title="Board Name" style="width:30%">${dataList.name}</display:column>
					
						<display:column style="width:17%;text-align:center" title="Create Board Member">
						<a href="javascript:getHrdgBoardMemberList('${dataList.id}','${dataList.boardTypeId}','${dataList.yearId}','${dataList.boardType}','${dataList.year}','${dataList.name}');">Create Board Member</a>
					 	</display:column>
					
					<display:column style="width:10%;text-align:center" title="Edit">
						<img src="./images/edit.gif"
							id="masterListEdit" onclick="editHRDGBoard(jsonHRDGBoardList,${dataList.id})"/>
					 	</display:column>
						<display:column style="width:10%;text-align:center" title="Delete">
							<img src="./images/delete.gif"
								onclick="deleteTrainingMaster('${dataList.id}','${sessionScope.trainingMstType}');" />
						</display:column>	
					
			</display:table>
			</div>
		<script>
			
				   $jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
				   
				
			
			jsonHRDGBoardList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonHRDGBoardList") %>;
			
			
		
			
			
		</script>
		
	</div>
</div>
<!-- End : HRDGBoardDataList.jsp -->