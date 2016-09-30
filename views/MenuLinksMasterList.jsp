<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : MenuLinksMasterList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>

<div class="line"><jsp:include page="Result.jsp"></jsp:include></div>

<div id="Pagination">
	<display:table name="${sessionScope.menuLinksList}" excludedParams="*" export="false" class="list" requestURI="" id="menu" pagesize="10" sort="list">
		<display:column title="Link Name" style="width:20%">${menu.linkName}</display:column>
		<display:column title="Parent Name" style="width:20%">${menu.parentName}</display:column>
		<display:column title="Key" style="width:15%">${menu.keyName}</display:column>
		<display:column title="Class Name" style="width:35%">${menu.controllerClass}</display:column>

		<display:column style="width:5%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit"
				onclick="editMenuLinks('${menu.id}','${menu.linkName}','${menu.parentId}','${menu.keyName}','${menu.controllerClass}')" />
		</display:column>
		<display:column style="width:5%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="deleteMenuLinks('${menu.id}')" />
		</display:column>
	</display:table>	
	<script>
			$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
	</script>
</div>
<!-- End : MenuLinksMasterList.jsp-->