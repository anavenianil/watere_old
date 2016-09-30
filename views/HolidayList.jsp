<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : HolidayList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<div class="line"><jsp:include page="Result.jsp"></jsp:include></div>
<div id="Pagination">
	<display:table name="${sessionScope.holidaysList}" excludedParams="*"
		export="false" class="list" requestURI="" id="holiday" pagesize="10"
		sort="list">
		<display:column title="Holiday Name" style="width:25%">${holiday.holiday}</display:column>
				<display:column title="Date" style="width:10%">${holiday.holidayDate}</display:column>
		<display:column title="Description" style="width:35%">${holiday.description}</display:column>
		
		<c:if test="${holidays.isEditable==true}">
			<display:column style="width:10%;text-align:center" title="Edit">
				<img src="./images/edit.gif" title="Edit"
					onclick="editHolidayDetails('${holiday.id}','${holiday.holiday}','${holiday.holidayDate}','${holiday.description}')" />
			</display:column>
			<display:column style="width:5%;text-align:center" title="Delete">
				<img src="./images/delete.gif" title="Delete"
					onclick="deleteHolidayDetails('${holiday.id}')" />
			</display:column>
		</c:if>
	</display:table>
	<script>
			$jq( function(){ $jq("#Pagination").displayTagAjax('paging&year='+$jq('#year').val());})
	</script>
	
</div>
<!-- End : HolidayList.jsp -->