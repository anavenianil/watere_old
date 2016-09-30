<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin :EntitleTypeMsterList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div>
	<div>
	<jsp:include page="Result.jsp"></jsp:include>
	</div>
	<div>
		<div id="Pagination">
			<display:table name="${sessionScope.entitleTypeList}" excludedParams="*"
				export="false" class="list" requestURI="" id="entitleTypeList" pagesize="5"
				sort="list">
				<display:column title="Entitlement Type" style="width:40%" sortable="true" >&nbsp;<c:forEach var="travelTypeList" items="${sessionScope.travelTypeList}">
				<c:if test="${travelTypeList.id==entitleTypeList.entitleTypeId}">${travelTypeList.travelType}</c:if></c:forEach></display:column>
				<display:column title="Entitlement Class" style="width:25%">&nbsp;${entitleTypeList.entitleClass}</display:column>
				<display:column title="description" style="width:25%">&nbsp;${entitleTypeList.description}</display:column>				
				<display:column style="width:5%;text-align:center" title="Edit">
					<img src="./images/edit.gif"
						onclick="editEntitleType('${entitleTypeList.id}','${entitleTypeList.entitleTypeId}','${entitleTypeList.entitleClass}','${entitleTypeList.description}');hilightRow(this);" />
				</display:column>
				<display:column style="width:5%;text-align:center" title="Delete">
					<img src="./images/delete.gif"
						onclick="deleteEntitleType('${entitleTypeList.id}')" />
				</display:column>
				
			</display:table>
		</div>
		<script>
			$jq( function(){
					$jq("#Pagination").displayTagAjax('paging');
				})
		</script>
	</div>
</div>
<!-- End : EntitleTypeMsterList.jsp -->