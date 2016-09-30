<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : ExternalBoardEmployeeList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.promotions.dto.VenueDetailsDTO"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<div>
	<jsp:include page="Result.jsp"></jsp:include>
</div>
<div id="Pagination">
	<display:table name="${sessionScope.ExternalBoardEmployeeList}" excludedParams="*"
		export="false" class="list" requestURI="" id="extEmp" pagesize="10"
		sort="list">
		<display:column title="Year" style="width:10%" sortable="true">
			${extEmp.yearDetails.name}			
		</display:column>
		<display:column title="Name" style="width:10%" sortable="true">${extEmp.name}</display:column>	
		<display:column title="Designation" style="width:60%" sortable="true">${extEmp.designation}</display:column>						
		<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit"
				onclick="editExternalBoardMember('${extEmp.id}','${extEmp.name}','${extEmp.designation}')" />
		</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete"
				onclick="deleteextEmpDetails('${extEmp.id}')" />
		</display:column>				
	</display:table>
	<script>
		$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
	</script>
</div>
<!-- End : ExternalBoardEmployeeList.jsp -->