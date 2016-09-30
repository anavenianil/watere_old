<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : HierarchyLevelList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="net.sf.json.JSONSerializer,java.util.List" %>
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
	</div>
	<div>
	<% int i=0; %>
		<aa:zone name="levelTable">		
			<display:table name="${sessionScope.displayLevel}" excludedParams="*"
				export="false" class="list" requestURI="" id="levelList" pagesize="10"
				sort="list">				
				<display:column title="Level" style="width:42%">${levelList.roleName}</display:column>
				<display:column title="Minimum Level of Reporting" style="width:42%">&nbsp;${levelList.parentName}</display:column>								
				<display:column style="width:8%;text-align:center" title="Edit">
				<img src="./images/edit.gif" title="Edit" 
						onclick="editLevel('${levelList.roleID}','${levelList.parentID}',displayNode)" />
				</display:column>
				<display:column style="width:8%;text-align:center" title="Delete">
					<img src="./images/delete.gif" title="Delete"
						onclick="deleteLevel('${levelList.roleID}','${levelList.type}')" />
				</display:column>
				<% i++; %>
			</display:table>
		</aa:zone>
			<script>
			type='${hierarchy.type}';
			displayPaging("levelTable","levelList");
			displayNode=<%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<Object>)session.getAttribute("displayLevel"))%>;
			parentJSONList=<%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<Object>)session.getAttribute("parentList"))%>;
			//levelID="";
			setLevel(${newLevelID});
			setParentList(parentJSONList);			
		</script>
	</div>
</div>
<!-- End : HierarchyLevelList.jsp -->