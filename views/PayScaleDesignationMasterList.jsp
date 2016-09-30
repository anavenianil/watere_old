<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin :PayScaleDesignationMasterList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<div class="line"><jsp:include page="Result.jsp"></jsp:include></div>
<div>
<div id="Pagination">
		<display:table name="${sessionScope.payDesignationList}" excludedParams="*" export="false" class="list" requestURI="" id="payDesignation" pagesize="5" sort="list">
	    	<display:column title="Designation" style="width:25%" sortable="true">${payDesignation.designationDetails.name}</display:column>
			<display:column title="Pay band" style="width:25%" sortable="true">${payDesignation.paybandDetails.name} - ${payDesignation.paybandDetails.rangeFrom} - ${payDesignation.paybandDetails.rangeTo}</display:column>
			<display:column title="Grade Pay " style="width:20%;text-align:right;" sortable="true">${payDesignation.gradePay}</display:column>
			<display:column style="width:10%;text-align:center" title="Edit">
				<img src="./images/edit.gif" title="Edit"
					onclick="editPayscaleDesignation('${payDesignation.id}','${payDesignation.designationDetails.id}','${payDesignation.paybandDetails.id}','${payDesignation.gradePay}')" />
			</display:column>
			<display:column style="width:10%;text-align:center" title="Delete">
				<img src="./images/delete.gif" title="Delete"
					onclick="deletePayscaleDesignation('${payDesignation.id}')" />
			</display:column>
		</display:table>
</div>	
	<script>
		$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
	</script>
</div>
<!-- End :PayScaleDesignationMasterList.jsp-->