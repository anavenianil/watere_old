<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin :TravelTypeList.jsp -->
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
			<display:table name="${sessionScope.ttList}" excludedParams="*"
				export="false" class="list" requestURI="" id="travelTypeList" pagesize="5"
				sort="list">
				<display:column title="Travel Type" style="width:40%" sortable="true" >&nbsp;${travelTypeList.travelType}</display:column>
				<display:column title="Description" style="width:25%">&nbsp;${travelTypeList.description}</display:column>			
				<display:column style="width:5%;text-align:center" title="Edit">
					<img src="./images/edit.gif"
						onclick="editTravelType('${travelTypeList.id}','${travelTypeList.travelType}','${travelTypeList.description}');hilightRow(this);" />
				</display:column>
				<display:column style="width:5%;text-align:center" title="Delete">
					<img src="./images/delete.gif"
						onclick="deleteTravelType('${travelTypeList.id}')" />
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
<!-- End : TravelTypeList.jsp -->