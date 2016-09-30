<!-- Begin : LtcFamilyDetailsList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>

<div>
	<div>
		<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
		<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
		<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
		<c:if test="${message=='deletefail'}"> <span class="failure"><spring:message code="recordexists"/></span></c:if>
	</div>
	<%int i=0; %>
	<div>
		<div id="Pagination">
			<display:table name="${sessionScope.LtcFamilyMemberList}" excludedParams="*"
				export="false" class="list" requestURI="" id="dataList" pagesize="10"
				sort="list">
					<display:column title='<input type="checkbox"/>' style="width:2%"><input type="checkbox" value="${dataList.id}" name="familyId" id="familyId<%=i %>"/></display:column>
					<display:column title="Name" style="width:20%">${dataList.name}</display:column>
					<display:column title="DOB" style="width:20%">${dataList.dob}</display:column>
					<display:column title="Age" style="width:10%">${dataList.age}</display:column>
					<display:column title="Relationship With Govt.servant" style="width:20%">${dataList.relation}</display:column>
					<display:column title="LTC() AVEAIL LIST" style="width:10%">${dataList.availYears}</display:column>
			<% i++; %>
			</display:table>
		</div>
		<script>
			$jq( function(){
				   $jq("#Pagination").displayTagAjax('home');
				})
		</script>
	</div>
</div>
<!-- End : LtcFamilyDetailsList.jsp -->