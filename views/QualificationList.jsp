<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : QualificationList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<script type="text/javascript" src="script/aa.js"></script>
<div class="line">
	<c:if test="${message eq 'failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
	<c:if test="${message eq 'success'}"> <span class="success"><spring:message code="success"/></span></c:if>
	<c:if test="${message eq 'update'}"> <span class="success"><spring:message code="success"/></span></c:if>
	<c:if test="${message eq 'delete'}"> <span class="success"><spring:message code="delete"/></span></c:if>
	<c:if test="${message eq 'duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
</div>
<aa:zone name="qualificationTable">
	<display:table name="${JsonEmpQualificationList}"
		excludedParams="*" export="false"  requestURI=""
		id="qualList" class="list" pagesize="10" sort="list">
	<display:column title='Qualification' style="width:12%;" >${qualList.qualiMasterName}</display:column>
	<display:column title='Discipline' style="width:22%;">${qualList.diciplineMasterName}</display:column>
	<display:column title='Specialization' style="width:30%;">&nbsp;${qualList.specilisation}</display:column>
	<display:column title='Year of Passing' style="width:20%;">${qualList.yearMasterName}</display:column>
	
	 <display:column style="width:8%;text-align:center" title="Edit"><img src="./images/edit.gif" title="Edit" onclick="javascript:editQualification(JsonEmpQualificationList,'${qualList.id}')" /></display:column>
     <display:column style="width:8%;text-align:center" title="Delete"><img src="./images/delete.gif" title="Delete" onclick="javascript:deleteQualification('${qualList.id}')" /></display:column>
	</display:table>
</aa:zone> 

<script>
	JsonEmpQualificationList = <%= (net.sf.json.JSONArray)session.getAttribute("JsonEmpQualificationList") %>;
	displayPaging("qualificationTable","qualList");
	
</script> 
 

<!-- End : QualificationList.jsp -->