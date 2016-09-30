<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : HierarchyNodeList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div>
	<div>
		<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
		<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
		<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
		<c:if test="${message=='deletefail'}"> <span class="failure"><spring:message code="recordexists"/></span></c:if>
		<c:if test="${message=='subordinates'}"> <span class="failure"><spring:message code="treeexists"/></span></c:if>
	</div>
	<div>
		<aa:zone name="nodeTable">
			<display:table name="${displayNode}" excludedParams="*"
				export="false" class="list" requestURI="" id="nodeList" pagesize="10"
				sort="list">
				<display:column title="DIR/PD/AD/TD/PROJ/DIV" style="width:25%">${nodeList.nodeName}</display:column>
				<display:column title="Hierarchy Level" style="width:30%">${nodeList.levelName}</display:column>
				<display:column title="Reports To" style="width:25%">${nodeList.nodeParentName}</display:column>
				<c:if test="${not empty nodeList.departmentType  }">
				<display:column title="Area of Deployment" style="width:16%">${nodeList.departmentTypeName}
				</display:column>
				</c:if>
				<display:column style="width:5%;text-align:center" title="Edit">
					<img src="./images/edit.gif"
						onclick="editNode('${nodeList.nodeID}',displayNode,'${hierarchy.type}')" />
				</display:column>
				<display:column style="width:5%;text-align:center" title="Delete">
					<img src="./images/delete.gif"
						onclick="deleteNode('${nodeList.nodeID}','${hierarchy.type}')" />
				</display:column>
			</display:table>
		</aa:zone>
		<script>
			displayPaging("nodeTable","nodeList");
			displayNode= <%= (net.sf.json.JSONArray)session.getAttribute("displayNode") %>;
			if('${hierarchy.type}'!="")
			type='${hierarchy.type}';
		</script>
	</div>
</div>
<!-- End : HierarchyNodeList.jsp -->