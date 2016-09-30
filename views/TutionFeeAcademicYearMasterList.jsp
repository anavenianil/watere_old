<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : TutionFeeAcademicYearMasterList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div class="line"><jsp:include page="Result.jsp"/></div>

<div id="dataTable">
	<display:table name="${sessionScope.classNameList}" excludedParams="*"
		export="false" class="list" requestURI="" id="class" pagesize="10"
		sort="list" cellpadding="2" cellspacing="1">
		<display:column title="SequenceOrder" style="width:5%;vertical-align:middle" sortable="true">${class.orderNo}</display:column>
		<display:column title="ClassName" style="width:15%;vertical-align:middle" sortable="true">${class.className}</display:column>
		<display:column style="width:5%;text-align:center" title="Edit">
		<c:if test="${class.className!='NURSERY' && class.className!='Nursery' && class.className!='nursery' && class.className!='L.K.G' && class.className!='LKG' && class.className!='lkg' && class.className!='l.k.g' && class.className!='U.K.G' && class.className!='UKG' && class.className!='ukg'&& class.className!='u.k.g'}">
			<img src="./images/edit.gif" title="Edit" onclick="editTutionAcademicMaster('${class.id}','${class.className}','${class.orderNo}')"/>
		</c:if>
		</display:column>
		<display:column style="width:5%;text-align:center" title="Delete">
		<c:if test="${class.className!='NURSERY' && class.className!='Nursery' && class.className!='nursery' && class.className!='L.K.G' && class.className!='LKG' && class.className!='lkg' && class.className!='l.k.g' && class.className!='U.K.G' && class.className!='u.k.g' && class.className!='UKG' && class.className!='ukg'}">
		<img src="./images/delete.gif" title="Delete" onclick="deleteTutionAcademicMaster('${class.id}')" />
		</c:if>
		</display:column>
	</display:table>
</div>
		<script>
			$jq( function(){
					$jq("#dataTable").displayTagAjax('paging');
				})
		</script>
<!-- End : TutionFeeAcademicYearMasterList.jsp -->








