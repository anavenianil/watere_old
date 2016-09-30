<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin :DaNewEntitlementList.jsp -->
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
			<display:table name="${sessionScope.daNewDetailsList}" excludedParams="*"
				export="false" class="list" requestURI="" id="daNewDetailsList" pagesize="5"
				sort="list">
				<display:column title="Grade Pay" style="width:10%;text-align:right" sortable="true" >${daNewDetailsList.gradePay}</display:column>
				<display:column title="Accommodation Bill" style="width:15%;text-align:right" sortable="true" >&nbsp;${daNewDetailsList.accommodationBill}</display:column>
				<display:column title="Food Bill" style="width:7%;text-align:right">&nbsp;${daNewDetailsList.foodBill}</display:column>
				<display:column title="Milage Allowances (per k.m/per day)" style="width:30%">&nbsp;${daNewDetailsList.milageAllw}</display:column>
				<display:column title="Travel By" style="width:8%">&nbsp;${daNewDetailsList.travelBy}</display:column>
				<display:column title="Distance" style="width:10%;text-align:right">&nbsp;${daNewDetailsList.distance}</display:column>
				<display:column title="Amount" style="width:10%;text-align:right">&nbsp;${daNewDetailsList.amount}</display:column>				
				<display:column style="width:5%;text-align:center" title="Edit">
					<img src="./images/edit.gif"
						onclick="editDaNewDetails('${daNewDetailsList.id}','${daNewDetailsList.gradePay}','${daNewDetailsList.accommodationBill}','${daNewDetailsList.foodBill}','${daNewDetailsList.milageAllw}','${daNewDetailsList.travelBy}','${daNewDetailsList.distance}','${daNewDetailsList.amount}');hilightRow(this);" />
				</display:column>
				<display:column style="width:5%;text-align:center" title="Delete">
					<img src="./images/delete.gif"
						onclick="deleteDaNewDetails('${daNewDetailsList.id}')" />
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
<!-- End : DaNewEntitlementList.jsp -->