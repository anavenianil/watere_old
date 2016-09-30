<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : OrganisationalLevelsList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.beans.dto.OrgLevelsDTO"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>

<div class="line">
	<div class="line">
		<c:if test="${Result == 'success'}"><span class="success"><spring:message code="success"/></span></c:if>
		<c:if test="${Result == 'failure'}"><span class="failure"><spring:message code="failure"/></span></c:if>
		<c:if test="${remarks ne null}"><span class="failure"><br/>${remarks}</span></c:if>		
		<c:if test="${Result == 'duplicate'}"><span class="failure"><spring:message code="duplicate"/></span></c:if>
		<c:if test="${Result == 'delete'}"><span class="failure"><spring:message code="delete" /></span></c:if>
	</div>
	<div class="line">
		<div id="Pagination">
			<display:table name="${sessionScope.rolesList}" excludedParams="*" export="false" class="list" requestURI="" id="levelList" pagesize="10" sort="list">
				<display:column title="Level" style="width:42%" sortable="true">${levelList.name}</display:column>
				<display:column title="Minimum Level of Reporting" style="width:42%">${levelList.parentName}</display:column>
				<display:column style="width:8%;text-align:center" title="Edit">
					<img src="./images/edit.gif" title="Edit" onclick="editLevel('${levelList.id}','${levelList_rowNum}','${levelList.parentID}','10')" />
				</display:column>
				<display:column style="width:8%;text-align:center" title="Delete">
					<img src="./images/delete.gif" title="Delete" onclick="deleteStr('${levelList.id}','${message}')" />
				</display:column>	
			</display:table>
			<script>
				$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
			</script>
		</div>
	</div>
	<script>	
		jsonArrayObject = <%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<OrgLevelsDTO>)session.getAttribute("rolesListJson"))%>;
	</script>
</div>
<!-- End : OrganisationalLevelsList.jsp -->