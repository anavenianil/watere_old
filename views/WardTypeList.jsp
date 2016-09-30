<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : WardTypeList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div>
	<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
	<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
	<c:if test="${message=='deleted'}"> <span class="success"><spring:message code="delete"/></span></c:if>
	<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
</div>
<div id="dataTable">
   	<display:table name="${sessionScope.WardTypeList}" excludedParams="*"
		export="false" class="list" requestURI="" id="ward" pagesize="10"
		sort="list">
		<display:column title="Ward Name" style="width:20%;">${ward.name}</display:column>
		<display:column title="Pay Band From" style="width:20%;">&nbsp;<fmt:formatNumber  pattern="##0.00" type="number" maxFractionDigits="2" value="${ward.startBasicPay}"/> </display:column>
		<display:column title="Pay Band To" style="width:20%;">&nbsp;<fmt:formatNumber  pattern="##0.00" type="number" maxFractionDigits="2" value="${ward.endBasicPay}"/> </display:column>
		<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit"
				onclick="editWardType('${ward.id}')" />
		</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete"
				onclick="deleteWardType('${ward.id}')" />
		</display:column>
	</display:table>
</div>

	<script>
		wardTypeList = <%= (net.sf.json.JSONArray)session.getAttribute("WardTypeList") %>;
		$jq( function(){
	 $jq("#dataTable").displayTagAjax('paging');
	})
	</script>


<!-- End : WardTypeList.jsp -->





