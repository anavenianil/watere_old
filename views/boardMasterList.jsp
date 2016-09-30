<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : boardMasterList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.promotions.dto.AssessmentCategoryDTO"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>

		<c:if test="${Result eq 'success'}"><div class="myStyle success""><spring:message code="success"/></div></c:if>
		<c:if test="${Result eq 'update'}"><div class="myStyle success"><spring:message code="update"/></div></c:if>
		<c:if test="${Result eq 'delete'}"><div class="myStyle success"><spring:message code="delete"/></div></c:if> 
		<c:if test="${Result=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
		<c:if test="${Result eq 'failure'}"><div class="myStyle failure">Sorry, You Can not Delete This Record Due to Models Exist For This Category</div></c:if>

<div id="Pagination">
	<display:table name="${sessionScope.boardMasterList}" excludedParams="*"
		export="false" class="list" requestURI="" id="boardDetailsList" pagesize="10"
		sort="list">	
		<display:column title="Board Type" style="width:10%" sortable="true">
			${boardDetailsList.category}			
		</display:column>		
		<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" 
			onclick="editBoardMasterDetails('${boardDetailsList.id}','${boardDetailsList.category}')" />
			</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete"
			onclick="deleteBoardMasterDetails('${boardDetailsList.id}')" />
		</display:column>				
	</display:table>
	<script>
		$jq( function(){ 
		$jq("#Pagination").displayTagAjax('paging');})
    
		
	</script>
</div>
<!-- End : boardMasterList.jsp -->