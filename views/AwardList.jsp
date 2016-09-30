<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : AwardList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="line">
	<c:if test="${message eq 'success'}"><div class="myStyle success""><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'update'}"><div class="myStyle success"><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'delete'}"><div class="myStyle success"><spring:message code="delete"/></div></c:if> 
	<c:if test="${message eq 'failure'}"><div class="myStyle failure"><spring:message code="failure"/></div></c:if>
</div>
<aa:zone name="AwardtListTable">
   	<display:table name="${sessionScope.jsonAwardList}" excludedParams="*"
		export="false" class="list" requestURI="" id="awardData" pagesize="10"
		sort="list">
		
		<display:column title="Award Category" style="width:20%;">${awardData.categoryName}</display:column>
		<display:column title="Organisation" style="width:10%;">&nbsp;${awardData.organization}</display:column>
		<display:column title="Cash" style="width:10%;">&nbsp;${awardData.cash}</display:column>
		<display:column title="Medallion" style="width:10%;">&nbsp;${awardData.medallion}</display:column>
		<display:column title="Citation" style="width:10%;">&nbsp;${awardData.citation}</display:column>
		<display:column title="Certificate" style="width:10%;">&nbsp;${awardData.certificate}</display:column>
		<display:column title="Details of Work" style="width:20%;">&nbsp;${awardData.detailsOfWork}</display:column>
		<display:column title="Year" style="width:5%;">${awardData.yearName}</display:column>
		
		<display:column style="width:5%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit"
				onclick="editAward(jsonAwardList,'${awardData.id}')" />
		</display:column>
		<display:column style="width:5%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete"
				onclick="deleteAward('${awardData.id}')" />
		</display:column>
	</display:table>
</aa:zone>

<script>
	jsonAwardList= <%= (net.sf.json.JSONArray)session.getAttribute("jsonAwardList") %>
	displayPaging("AwardtListTable","awardData");
</script>



<!-- End : AwardList.jsp -->
