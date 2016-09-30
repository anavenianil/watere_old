<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : ReferralHospitalList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div>
	<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
	<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
	<c:if test="${message=='deleted'}"> <span class="success"><spring:message code="delete"/></span></c:if>
	<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
</div>
<div id="dataTable">
   	<display:table name="${sessionScope.ReferralHospitalList}" excludedParams="*"
		export="false" class="list" requestURI="" id="referral" pagesize="10"
		sort="list">
		<display:column title="" style="width:3%;"><c:if test="${referral.deletionDate eq ''}"><input type="checkbox" value="${referral.id}" id="hospitalId${referral.id}" onclick="javascript:enableDeleteDatePicker()" /></c:if></display:column>
		<display:column title="Referral Hospital Name" style="width:20%;">${referral.hospitalName}</display:column>
		<display:column title="Location" style="width:20%;">&nbsp;${referral.hospitalLocation}</display:column>
		<display:column title="Address" style="width:20%;">&nbsp;${referral.address}</display:column>
		
			<display:column style="width:10%;text-align:center" title="Edit">
				<c:if test="${referral.deletionDate eq ''}">
				<img src="./images/edit.gif" title="Edit"
					onclick="editReferralHospital('${referral.id}')" />
					</c:if>
			</display:column>
			<display:column style="width:10%;text-align:center" title="Delete">
		
				<img src="./images/delete.gif" title="Delete"
					onclick="deleteReferralHospital('${referral.id}')" />
					
			</display:column>
		
	</display:table>
</div>

	<script>
		referralList = <%= (net.sf.json.JSONArray)session.getAttribute("ReferralHospitalList") %>;
		$jq( function(){
	 $jq("#dataTable").displayTagAjax('paging');
	})
	</script>


<!-- End : ReferralHospitalList.jsp -->





