<!-- Begin : InternalStructureTree.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>

<div>
	<div>
		<c:if test="${hierarchy.message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
		<c:if test="${hierarchy.message=='success'}"> 
			<span class="success"><spring:message code="success"/></span>
			<script>setValues();</script>
		</c:if>
		<c:if test="${hierarchy.message=='empduplicate'}"> <span class="failure">Employee Already Mapped</span></c:if>
		<c:if test="${hierarchy.message=='deletefail'}"> <span class="failure"><spring:message code="recordexists"/></span></c:if>
	</div>
	<div>
		<aa:zone name="treeTable">
			<display:table name="${sessionScope.internalTreeList}" excludedParams="*"
				export="false" class="list" requestURI="" id="treeList" pagesize="10"
				sort="list">
				<display:column title="Name" style="width:25%">${treeList.employeeName}</display:column>
				<display:column title="Internal Division" style="width:20%">&nbsp;${treeList.internalDivision}</display:column>
				<display:column title="Internal Role" style="width:15%">${treeList.internalRole}</display:column>
				<display:column title="Reports To" style="width:20%">${treeList.parentName}</display:column>
				<display:column title="Belongs To" style="width:15%">${treeList.officeName}</display:column>
				<display:column style="width:10%;text-align:center" title="Edit">
					<img src="./images/edit.gif"
						onclick="editInternalStructure('${treeList.id}','${treeList.sfid}','${treeList.internalDivision}','${treeList.internalRole}','${treeList.departmentID }')" />
				</display:column>				
			</display:table>
		</aa:zone>
		<script>displayPaging("treeTable","treeList");</script>
	</div>
</div>
<!-- End : InternalStructureTree.jsp -->