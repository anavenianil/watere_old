<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin :LoanImmediateReliefList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>

<div class="line"><jsp:include page="Result.jsp"></jsp:include></div>
<div>
<div id="Pagination">
	<display:table name="${sessionScope.reliefList}" excludedParams="*"
		export="false" class="list" requestURI="" id="relList" pagesize="10"
		sort="list">
		<display:column title="SFID" style="width:5%">&nbsp;${relList.sfID}</display:column>
		<display:column title="Date of Death" style="width:10%"><fmt:formatDate pattern="dd-MMM-yyyy" value="${relList.dateOfDeath}"/></display:column>
		<display:column title="Name of Applicant" style="width:15%">${relList.nameOfApplicant}</display:column>
		<display:column title="Relationship" style="width:10%">&nbsp;${relList.relationDetails.name}</display:column>
		<display:column title="Advance Amount" style="width:10%"><fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="2" value="${relList.advance}"/></display:column>
		<display:column title="Witness 1" style="width:10%">&nbsp;${relList.witness1}</display:column>
		<display:column title="Witness 2" style="width:10%">&nbsp;${relList.witness2}</display:column>
		<display:column title="Residential Address" style="width:10%">&nbsp;${relList.address}</display:column>
		<display:column title="Approved By" style="width:5%">&nbsp;${relList.approvedBy}</display:column>
		<display:column title="Report" style="width:5%"><a href="javascript:viewDeathForm('${relList.sfID}')">PDF</a></display:column>
		<display:column style="width:5%;text-align:center" title="Edit">
				<img src="./images/edit.gif" title="Edit"
					onclick="editRelief('${relList.id}','${relList.sfID}','<fmt:formatDate pattern="dd-MMM-yyyy" value="${relList.dateOfDeath}"/>','${relList.nameOfApplicant}','${relList.relationshipID}','<fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="2" value="${relList.advance}"/>','${relList.witness1}','${relList.witness2}','${relList.address}','${relList.approvedBy}');" />
		</display:column>
		<display:column style="width:5%;text-align:center" title="Delete">
				<img src="./images/delete.gif" title="Delete"
					onclick="deleteRelief('${relList.id}');" />
		</display:column>
	</display:table>	
	</div>
	</div>
	<script>
		$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
	</script>
	
<!-- End :LoanImmediateReliefList.jsp -->