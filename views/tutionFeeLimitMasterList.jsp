<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : TutionFeeLimitMasterList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt" %>

<div class="line"><jsp:include page="Result.jsp"/>
</div>
<div id="dataTable">
	<display:table name="${tutionFee.academicTypeList}" excludedParams="*"
		export="false" class="list" requestURI="" id="tutionLimit" pagesize="1000"
		sort="list" cellpadding="2" cellspacing="1">
		<display:column title="AcademicType" style="width:15%;vertical-align:middle">${tutionLimit.academicType}</display:column>
		<display:column title="FromDate" style="width:15%;vertical-align:middle">${tutionLimit.fromDate1}</display:column>
		<display:column title="ToDate" style="width:15%;vertical-align:middle">${tutionLimit.toDate1}</display:column>
		<display:column title="Type" style="width:15%;vertical-align:middle">${tutionLimit.type}</display:column>
		<display:column title="Limit in %" style="width:15%;vertical-align:middle">${tutionLimit.limit}</display:column>
		<display:column style="width:5%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="editTutionFeeLimitMaster('${tutionLimit.id}','${tutionLimit.academicType}','${tutionLimit.fromDate1}','${tutionLimit.toDate1}','${tutionLimit.limit}','${tutionLimit.type}')"/>
		</display:column>
		<display:column style="width:5%;text-align:center" title="Delete">
		<img src="./images/delete.gif" title="Delete" onclick="deleteTutionFeeLimitMaster('${tutionLimit.id}')" />
		</display:column>
		</display:table>
</div>
<!-- End : TutionFeeLimitMasterList.jsp -->







