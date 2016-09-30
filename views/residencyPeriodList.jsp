<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : residencyPeriodList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<div>
	<jsp:include page="Result.jsp"></jsp:include>
</div>
<div id="Pagination">
	<display:table name="${sessionScope.residencyPeriodList}" excludedParams="*"
		export="false" class="list" requestURI="" id="residencyDetailsList" pagesize="10"
		sort="list">
		<display:column title="Assessment Type" style="width:12%" sortable="true">
			${residencyDetailsList.assessmentTypeDetails.name}			
		</display:column>
		<display:column title="Board Type" style="width:12%" sortable="true">
			${residencyDetailsList.assessmentCategoryDetails.category}			
		</display:column>		
		<display:column title="Designation From" style="width:15%">${residencyDetailsList.designationFromDetails.name}		</display:column>
		<display:column title="Designation To" style="width:15%;">${residencyDetailsList.designationToDetails.name}		</display:column>
		<display:column title="Experience" style="width:10%;text-align:right">&nbsp;	${residencyDetailsList.residencyPeriod}		</display:column>
		<display:column title="Relaxation in Exp." style="width:10%;text-align:right">			
			${residencyDetailsList.relaxationInMonths}		
		</display:column>
		<display:column style="width:5%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit"
				onclick="editResidencyPeriodDetails('${residencyDetailsList.id}','${residencyDetailsList.assessmentTypeDetails.id}','${residencyDetailsList.assessmentCategoryDetails.id}','${residencyDetailsList.designationFromDetails.id}','${residencyDetailsList.designationToDetails.id}','${residencyDetailsList.residencyPeriod}','${residencyDetailsList.relaxationInMonths}','${residencyDetailsList.written}','${residencyDetailsList.trade}','${residencyDetailsList.interview}','${residencyDetailsList.board}')"/>
		</display:column>
		<display:column style="width:5%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete"
				onclick="deleteResidencyPeriodDetails('${residencyDetailsList.id}')" />
		</display:column>				
	</display:table>
	<script>
		$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
		residencyListJSON= <%= (net.sf.json.JSONArray)session.getAttribute("residencyListJSON") %>;

	</script>
</div>
<!-- End : residencyPeriodList.jsp -->