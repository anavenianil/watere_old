<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : DesigExperienceList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<div>
	<jsp:include page="Result.jsp"></jsp:include>
</div>
<div id="Pagination">
	<display:table name="${sessionScope.desigExperienceList}" excludedParams="*"
		export="false" class="list" requestURI="" id="desigExperienceList" pagesize="10"
		sort="list">
		<display:column title="SFID" style="width:10%" sortable="true">
			${desigExperienceList.sfid}			
		</display:column>		
		<display:column title="Name" style="width:20%">${desigExperienceList.empName}</display:column>
		<display:column title="Designation" style="width:25%;">${desigExperienceList.name}</display:column>
		<display:column title="Start Date" style="width:20%;text-align:right">&nbsp;	<fmt:formatDate pattern="dd-MMM-yyyy" value="${desigExperienceList.startDate}"/></display:column>
		<display:column title="No of Attempts" style="width:15%;text-align:right">${desigExperienceList.noOfAttempts}</display:column>
		<display:column style="width:5%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit"
				onclick="editDesigExperienceDetails('${desigExperienceList.id}','${desigExperienceList.sfid}','${desigExperienceList.name}','${desigExperienceList.designationID}','<fmt:formatDate pattern="dd-MMM-yyyy" value="${desigExperienceList.startDate}"/>','${desigExperienceList.noOfAttempts}');" />
		</display:column>
		<display:column style="width:5%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete"
				onclick="deleteDesigExperienceDetails('${desigExperienceList.id}')" />
		</display:column>				
	</display:table>
	<script>
		$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
	</script>
	</div>
<!-- End :DesigExperienceList.jsp -->
