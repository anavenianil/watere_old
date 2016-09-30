<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : DAArrearsPreviewHome.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div class="line">
 <b>Category</b><span class="failure">*</span>
	<spring:bind path="arrears">
	<form:select path="arrears.categoryId" id="categoryId"  cssClass="formSelect" style="width:65%" >
		<form:option value="0">All</form:option>
		<form:options items="${sessionScope.empCategoryMasterDetails}" itemValue="id" itemLabel="name"/>
	</form:select>
	</spring:bind>
</div>
<div class="line" style="width:80%">
	<display:table name="${sessionScope.daArrearsPreviewList}" excludedParams="*"
				export="false" class="list" requestURI="" id="previewHome" pagesize="1000"
				sort="list" cellpadding="2" cellspacing="1">
			    <display:column title="Run Month" style="width:0.25%;vertical-align:middle"><fmt:formatDate value="${previewHome.runMonth}" pattern="dd-MMM-yyyy"/></display:column>
				<display:column title="Paid DA Value" style="width:0.33%;vertical-align:middle">${previewHome.payCondrValue}</display:column>
				<display:column title="Actual DA Value" style="width:0.33%;vertical-align:middle">${previewHome.actualValue}</display:column>
	</display:table>
   <script>$jq('.pagebanner').hide();</script>
</div>
<div class="line">
    <div style="margin-left:80%"><a href="javascript:previewDAArrearsDetails();" class="appbutton">Preview</a></div>
</div>
<script>
	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
</script>
<spring:bind path="arrears">
<form:hidden path="arrears.adminAccDate"/>
</spring:bind>
<spring:bind path="arrears">
<form:hidden path="arrears.financeAccDate"/>
</spring:bind>
<!-- End : DAArrearsPreviewHome.jsp -->
