<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : signingAuthorityList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<div class="line"><jsp:include page="Result.jsp"></jsp:include></div>

<div id="Pagination">
	<display:table name="${sessionScope.signingAuthorityList}"
		excludedParams="*" export="false" class="list" requestURI=""
		id="authorityList" pagesize="100" sort="list" cellpadding="2"
		cellspacing="1">
		<c:choose>
			<c:when test="${authorityList.type  eq 'ACC'}">
				<display:column title="AuthorityType"
					style="width:2%;vertical-align:middle">Accounts Officer</display:column>
			</c:when>
			<c:otherwise>
				<display:column title="AuthorityType"
					style="width:2%;vertical-align:middle">${authorityList.type}</display:column>
			</c:otherwise>
		</c:choose>
		<display:column title="Sfid" style="width:5%;vertical-align:middle">${authorityList.sfid}</display:column>
		<display:column title="AuthorityName"
			style="width:5%;vertical-align:middle">${authorityList.name}</display:column>
		<display:column style="width:5%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit"
				onclick="editSigningAuthorityList('${authorityList.id}','${authorityList.type}','${authorityList.name}-','${authorityList.sfid}')" />
		</display:column>
		<display:column style="width:5%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete"
				onclick="deleteSigningAuthorityList('${authorityList.id}')" />
		</display:column>
	</display:table>
</div>

<script>
	$jq(function() {
		$jq("#Pagination").displayTagAjax('paging');
	})
</script>

<!-- End : signingAuthorityList.jsp -->




