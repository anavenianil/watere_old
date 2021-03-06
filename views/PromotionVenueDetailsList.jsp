<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : PromotionVenueDetailsList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.promotions.dto.VenueDetailsDTO"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<div>
	<jsp:include page="Result.jsp"></jsp:include>
</div>
<div id="Pagination">
	<display:table name="${sessionScope.venueList}" excludedParams="*"
		export="false" class="list" requestURI="" id="venue" pagesize="10"
		sort="list">
		<display:column title="Category" style="width:5%" sortable="true">
			${venue.categoryName}			
		</display:column>
		<display:column title="Board" style="width:5%" sortable="true">
			${venue.assessmentCategoryDetails.category}			
		</display:column>
		<display:column title="Year" style="width:5%" sortable="true">
			${venue.yearDetails.name}			
		</display:column>
		<display:column title="Center" style="width:5%" sortable="true">${venue.center}</display:column>				
		<display:column title="Co-Ordinator" style="width:5%" sortable="true">${venue.coOrdinator}</display:column>				
		<display:column title="Co-Ordinate Lab" style="width:5%" sortable="true">${venue.coOrdinatorLab}</display:column>				
		<display:column title="Address" style="width:5%" sortable="true">${venue.address}</display:column>				
		<display:column title="Contact Details" style="width:5%" sortable="true">${venue.contactAddress}</display:column>				
		<display:column title="Venue" style="width:15%" sortable="true">${venue.venue}</display:column>			
		<display:column style="width:5%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit"
				onclick="editVenueDetails('${venue.id}')" />
		</display:column>
		<display:column style="width:5%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete"
				onclick="deleteVenueDetails('${venue.id}')" />
		</display:column>				
	</display:table>
	<script>
		$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
		jsonArrayObject =<%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<VenueDetailsDTO>)session.getAttribute("venueList"))%>;
	</script>
</div>
<!-- End : PromotionVenueDetailsList.jsp -->