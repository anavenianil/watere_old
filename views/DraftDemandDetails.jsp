<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : DraftDemandDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<div class="line">
	<c:if test="${message eq 'success'}"><div class="myStyle success"><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'update'}"><div class="myStyle success"><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'delete'}"><div class="myStyle success"><spring:message code="delete"/></div></c:if> 
	<c:if test="${message eq 'failure'}"><div class="myStyle failure"><spring:message code="failure"/></div></c:if>
	<c:if test="${message eq 'duplicate'}"><div class="myStyle failure"><spring:message code="duplicate"/></div></c:if>
	<c:if test="${message eq 'EXISTS'}"><div class="myStyle failure"><spring:message code="recordexists"/></div></c:if>
</div>

<div id="DemandPagination">
	<%int i=1;%>
	<display:table name="${sessionScope.draftDetails}" excludedParams="*"
		export="false" class="list" requestURI="" id="draftList" pagesize="10" 
		sort="list">
		<display:column  title='Demand No' style="width:20%;">&nbsp;<a id="draft<%=i%>" href="javascript:getDraftDetails('draft<%=i%>');">${draftList.demandNo}</a></display:column>
		<display:column  title='Demand Date' style="width:20%;">&nbsp;<fmt:formatDate pattern="dd-MMM-yyyy hh:mm:ss" value="${draftList.demandDate}" /></display:column>
		<display:column  title='Inventory Number' style="width:15%;" >&nbsp;${draftList.inventoryNo}</display:column>
		<display:column  title='Raised By' style="width:20%;">&nbsp;${draftList.raisedBy}</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete"
				onclick="deleteDraft('draft<%=i%>')"/>
		</display:column>
		<%i++;%>
	</display:table>
</div>
<script>
$jq( function(){
	 $jq("#DemandPagination").displayTagAjax();
	})
</script> 
<!-- End : FamilyList.jsp -->