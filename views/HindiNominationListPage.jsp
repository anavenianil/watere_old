<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start :HindiNominationList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>

	<c:if test="${message eq 'success'}"><div class="myStyle success""><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'update'}"><div class="myStyle success"><spring:message code="update"/></div></c:if>
	<c:if test="${message eq 'delete'}"><div class="myStyle success"><spring:message code="delete"/></div></c:if> 
	<c:if test="${message eq 'failure'}"><div class="myStyle failure"><spring:message code="failure"/></div></c:if>
<div>
<div id="dataTable">
   	<display:table name="${sessionScope.hindiNominationList}" excludedParams="*"
		export="false" class="list" requestURI="" id="hindiNomination" pagesize="10" sort="list">
		<%int i=0; %>
		<display:column  title='Eligibility' >
		<c:forEach var="selectedNomination" items="${sessionScope.SelectedNominationList}">
				<c:if test="${selectedNomination.key eq hindiNomination.id}">
					<input id="${hindiNomination.id}" type="checkbox" onchange="javascript:checkEligibility('${hindiNomination.id}')" checked="checked" />
						<%i++; %>												
				 </c:if>
	        </c:forEach>
	        <%if(i==0){ %>
	        <input id="${hindiNomination.id}" type="checkbox" onchange="javascript:checkEligibility('${hindiNomination.id}')" />
	        <%} %>
		</display:column>	
		
		<display:column  title='SFID' >${hindiNomination.id}</display:column>
		<display:column  title='Name' >${hindiNomination.name}</display:column>
		<display:column  title='Designation' >${hindiNomination.designation}</display:column>
		<display:column  title='Department' >${hindiNomination.department}</display:column>
				
	</display:table>
</div>

<c:if test="${CheckSize ne ''}">
   <div class="line" style="margin-left: 25%;">
     <div class="expbutton"><a onclick="javascript:saveNominationList();"> <span style="align:right">Submit</span></a></div>
   </div> 
   </c:if>
<script>

	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	
	
</script>
</div>

<%-- End : HindiNominationList.jsp --%>
