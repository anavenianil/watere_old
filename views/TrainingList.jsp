<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : TrainingList.jsp -->

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
</div>
<aa:zone name="dataTable">
<% int i=0; %>
   	<display:table name="${sessionScope.TrainingList}" excludedParams="*"
		export="false" class="list" requestURI="" id="training" pagesize="10"
		sort="list">
		<display:column title="Course" style="width:22%;">${training.course}</display:column>
		<display:column title="Subject" style="width:22%;">&nbsp;${training.subject}</display:column>
		<display:column title="Training Area" style="width:20%;">&nbsp;${training.area}</display:column>
		<display:column title="Duration" style="width:20%;">&nbsp;${training.duration}</display:column>
		<display:column style="width:8%;text-align:center" title="Edit">
		<script>
			document.forms[0].course.value=escape("${training.course}");
			document.forms[0].subject.value=escape("${training.subject}");
		</script>
			<img src="./images/edit.gif" title="Edit"
				onclick="editTraining('${training.id}','${training.course}','${training.subject}','${training.area}','${training.institute}','${training.year}','${training.duration}','${training.fromDate}','${training.toDate}')" />
		</display:column>
		<display:column style="width:8%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete"
				onclick="deleteTraining('${training.id}')" />
		</display:column>
		<% i++; %>
	</display:table>
</aa:zone>




<script>displayPaging("dataTable","dataList");
		resetTraining();
		</script>

<!-- End : TrainingList.jsp -->





