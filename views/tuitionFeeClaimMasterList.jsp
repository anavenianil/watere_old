<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : tuitionFeeClaimMasterList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div class="line"><jsp:include page="Result.jsp"></jsp:include></div>

<div id="Pagination">
	<display:table name="${sessionScope.claimList}" excludedParams="*"
		export="false" class="list" requestURI="" id="claim" pagesize="10"
		sort="list" cellpadding="2" cellspacing="1">
		<display:column title="ClaimType" style="width:5%;vertical-align:middle">${claim.claimTypeMaster.claimType}</display:column>
		<display:column title="ClaimName" style="width:5%;vertical-align:middle">${claim.claimName}</display:column>
		<display:column title="OrderNo" style="width:5%;vertical-align:middle">${claim.orderNo}</display:column>
		<display:column style="width:5%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="editTutionClaimMaster('${claim.id}','${claim.claimName}','${claim.claimTypeMaster.id}','${claim.orderNo}')"/>
		</display:column>
		<display:column style="width:5%;text-align:center" title="Delete">
		<img src="./images/delete.gif" title="Delete" onclick="deleteTutionClaimMaster('${claim.id}')" />
		</display:column>
		</display:table>	   
</div>

		<script>
			$jq( function(){
					$jq("#Pagination").displayTagAjax('paging');
				})
		</script>
	  
<!-- End : tuitionFeeClaimMasterList.jsp -->




