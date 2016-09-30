<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin :DaEntitlementList.jsp -->
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
			<display:table name="${sessionScope.daDetailsList}" excludedParams="*"
				export="false" class="list" requestURI="" id="daDetailsList" pagesize="5"
				sort="list">
				<display:column title="City Type" style="width:25%" sortable="true" >&nbsp;
				<c:forEach var="uniqueCityTypeList" items="${sessionScope.uniqueCityTypeList}">
				<c:if test="${uniqueCityTypeList.id==daDetailsList.cityTypeId}">${uniqueCityTypeList.cityClass}</c:if></c:forEach></display:column>
				<display:column title="Pay Range" style="width:25%" sortable="true" >&nbsp;${daDetailsList.payRangeFrom} - ${daDetailsList.payRangeTo}</display:column>
				<display:column title="ORD" style="width:20%;text-align:right">&nbsp;${daDetailsList.ord}</display:column>
				<display:column title="Hotel" style="width:20%;text-align:right">&nbsp;${daDetailsList.hotel}</display:column>				
				<display:column style="width:5%;text-align:center" title="Edit">
					<img src="./images/edit.gif"
						onclick="editDaDetails('${daDetailsList.id}','${daDetailsList.cityTypeId}','${daDetailsList.payRangeFrom}','${daDetailsList.payRangeTo}','${daDetailsList.ord}','${daDetailsList.hotel}');hilightRow(this);" />
				</display:column>
				<display:column style="width:5%;text-align:center" title="Delete">
					<img src="./images/delete.gif"
						onclick="deleteDaDetails('${daDetailsList.id}')" />
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
<!-- End : DaEntitlementList.jsp -->