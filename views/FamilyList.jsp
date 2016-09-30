<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : FamilyList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<div id="result"></div>
<div class="line">
	<c:if test="${message eq 'success'}"><div class="myStyle success""><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'update'}"><div class="myStyle success"><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'delete'}"><div class="myStyle success"><spring:message code="delete"/></div></c:if> 
	<c:if test="${message eq 'failure'}"><div class="myStyle failure"><spring:message code="failure"/></div></c:if>
	<c:if test="${message eq 'duplicate'}"><div class="myStyle failure"><spring:message code="duplicate"/></div></c:if>
	<c:if test="${message eq 'EXISTS'}"><div class="myStyle failure"><spring:message code="recordexists"/></div></c:if>
	<c:if test="${reason ne '' && message eq 'failure'}"><div class="myStyle failure">${reason}</div></c:if>
	<c:if test="${message=='deleteMemberIsNominee'}"> <span class="failure"><spring:message code="deleteMemberIsNominee"/></span></c:if>
</div>

<aa:zone name="familyTable">
	<display:table name="${sessionScope.JsonEmpFamilyList}" excludedParams="*"
		export="false" class="list" requestURI="" id="familyList" pagesize="10"
		sort="list">
		<display:column  title='Family Member Name' style="width:20%;">${familyList.name}</display:column>
		<display:column  title='Relationship' style="width:15%;">${familyList.relation}</display:column>
		<display:column  title='Date of Birth/Age' style="width:14%;" >
		<c:if test="${not empty familyList.dob}">
			${familyList.dob}
		</c:if>
		<c:if test="${empty familyList.dob}">
			${familyList.age}
		</c:if>
		</display:column>
		<display:column  title='Marital Status' style="width:15%;" >&nbsp;${familyList.maritalstatusId}</display:column>
		<display:column  title='Contact Number' style="width:20%;">&nbsp;${familyList.contactNumber}</display:column>
	<c:if test=	"${familyList.ltcFacility eq 'Y'}">
		<display:column  title='LTC Dependent' style="width:20%;">&nbsp;Yes</display:column>
		</c:if>
		<c:if test=	"${familyList.ltcFacility eq 'N'}">
		<display:column  title='LTC Dependent' style="width:20%;">&nbsp;No</display:column>
		</c:if>
		<c:if test="${familyList.cghsFacility eq 'Y'}">
		<display:column  title='CGHS Dependent' style="width:20%;">&nbsp;Yes</display:column>
          </c:if>
          <c:if test="${familyList.cghsFacility eq 'N'}">
		<display:column  title='CGHS Dependent' style="width:20%;">&nbsp;No</display:column>
          </c:if>
          
		<display:column style="width:8%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit"
				onclick="setFamilyDetails(JsonFamilyList,'${familyList.id}')"/>
		</display:column>
		<display:column style="width:8%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Edit"
				onclick="deleteFamily('${familyList.id}')"/>
		</display:column>
	</display:table>
</aa:zone>
<script>
	JsonFamilyList = <%= (net.sf.json.JSONArray) session.getAttribute("JsonEmpFamilyList") %>;
	displayPaging("familyTable", "familyList");
	//$jq( function(){ $jq("#familyTable").displayTagAjax('paging');})
</script> 
<!-- End : FamilyList.jsp -->