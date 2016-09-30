<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : itSectionMappingList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div>
    <jsp:include page="Result.jsp"></jsp:include>	
</div>
<div id="dataTable">
   	<display:table name="${sessionScope.sectionMappingList}" excludedParams="*"
		export="false" class="list" requestURI="" id="sec" pagesize="10" sort="list">
		<display:column title="Section Name" style="width:20%;text-align:left">&nbsp;${sec.sectionDetails.secName}</display:column>
		<display:column title="Gender" style="width:20%;text-align:left">
		<c:if test="${sec.genderFlag=='1'}">Male</c:if>
		<c:if test="${sec.genderFlag=='0'}">Female</c:if>
        </display:column>
		<display:column title="Disability" style="width:20%;text-align:left">
		<c:choose>
		 <c:when test="${sec.disabilityFlag=='1'}">Yes</c:when>
		 <c:otherwise>No</c:otherwise>
		</c:choose>
        </display:column>
		<display:column title="Senior Citizen" style="width:20%;text-align:left">
        <c:if test="${sec.srCitizenFlag=='1'}">Yes</c:if>
		<c:if test="${sec.srCitizenFlag=='0'}">No</c:if>
        </display:column>
		<display:column title="Limit" style="width:20%;text-align:left">&nbsp;${sec.limit}</display:column>
		<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="editSectionMappingDetails('${sec.id}','${sec.sectionId}','${sec.genderFlag}','${sec.disabilityFlag}','${sec.srCitizenFlag}','${sec.limit}')"/>
		</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="deleteSectionMappingDetails('${sec.id}')" />
		</display:column>
	</display:table>
</div>

<script>
	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	
</script>


<!-- End : itSectionMappingList.jsp -->
