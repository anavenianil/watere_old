<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : AddAssessmentList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.promotions.dto.VenueDetailsDTO"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<div>
	<jsp:include page="Result.jsp"></jsp:include>
</div>
<div id="Pagination">
	<display:table name="${sessionScope.addAssessmentList}" excludedParams="*"
		export="false" class="list" requestURI="" id="expEmp" pagesize="10"
		sort="list">
		<display:column title="Year" style="width:10%" sortable="true">
			${expEmp.yearDetails.name}			
		</display:column>
		<display:column title="SFID" style="width:10%" sortable="true">${expEmp.sfID}</display:column>	
		<display:column title="Description" style="width:60%" sortable="true">${expEmp.description}</display:column>						
		<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit"
				onclick="editExpEmpDetails('${expEmp.id}','${expEmp.yearID}','${expEmp.sfID}','${expEmp.description}')" />
		</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete"
				onclick="deleteExpEmpDetails('${expEmp.id}')" />
		</display:column>				
	</display:table>
	<script>
		$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
	</script>
</div>
<!-- End : AddAssessmentList.jsp -->