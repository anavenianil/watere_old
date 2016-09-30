<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : tutionFeeClaimMasterList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div class="line"><jsp:include page="Result.jsp"/></div>

<div id="dataTable">
	<display:table name="${tutionFee.claimList}" excludedParams="*" export="false" class="list" requestURI="" id="stud" pagesize="10" sort="list" cellpadding="2" cellspacing="1">
		<display:column title="ID" style="width:2%;vertical-align:middle">${stud.studentId}</display:column>
		<display:column title="Name" style="width:2%;vertical-align:middle">${stud.studentName}</display:column>
		<display:column title="Qualification" style="width:2%;vertical-align:middle">${stud.qualification}</display:column>
		<display:column title="Aggregate" style="width:2%;vertical-align:middle">${stud.aggregate}</display:column>
		<display:column style="width:5%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="editTutionClaimMaster('${claim.id}','${claim.claimName}','${claim.claimTypeMaster.id}')"/>
		</display:column>	
		<display:column style="width:5%;text-align:center" title="Delete">
		<img src="./images/delete.gif" title="Delete" onclick="deleteTutionClaimMaster('${claim.id}')" />
		</display:column>
		</display:table>	   
</div>
	 <script>
			$jq( 
				 function()
				 { 
				 	$jq("#dataTable").displayTagAjax('paging');
				 }
			   )
	  </script>
<!-- End : tutionFeeClaimMasterList.jsp -->




