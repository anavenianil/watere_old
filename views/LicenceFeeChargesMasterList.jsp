<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin :LicenceFeeChargesMasterList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<div class="line"><jsp:include page="Result.jsp"></jsp:include></div>
<div>
<div id="Pagination">
	
<display:table name="${sessionScope.LicenceFeeList}" excludedParams="*" export="false" class="list" requestURI="" id="licence" pagesize="5" sort="list">
	<display:column title="Quarter Type" style="width:20%">${licence.quarterSubTypeDetails.quarterTypeDetails.name}</display:column>
	<display:column title="Quarter Sub Type" style="width:20%">${licence.quarterSubTypeDetails.quarterSubType}</display:column>
	<display:column title="Rent" style="width:10%;text-align:right;"> ${licence.licenceFee}</display:column>
	<display:column title="Water Bill" style="width:10%;text-align:right;"> ${licence.water}</display:column>
	<display:column title="Elec Bill" style="width:10%;text-align:right;"> ${licence.elec}</display:column>
	<display:column title="Furn Bill" style="width:10%;text-align:right;"> ${licence.furn}</display:column>
	<display:column title="Effective From" style="width:10%;text-align:center;"><fmt:formatDate value="${licence.effDate}" pattern="dd-MMM-yyyy"/></display:column>
	<display:column style="width:5%;text-align:center" title="Edit">
		<img src="./images/edit.gif" title="Edit"
			onclick="editLicenceFeeDetails('${licence.id}','${licence.quartersType}','${licence.quarterSubType}','${licence.licenceFee}','${licence.water}','${licence.elec}','${licence.furn}','<fmt:formatDate value="${licence.effDate}" pattern="dd-MMM-yyyy"/>');" />
	</display:column>
	<display:column style="width:5%;text-align:center" title="Delete">
		<img src="./images/delete.gif" title="Delete"
			onclick="deleteLicenceFeeDetails('${licence.id}')" />
	</display:column>
</display:table>

</div>	
	<script>
		$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
	</script>
</div>
<!-- End :LicenceFeeChargesMasterList.jsp-->