<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : movablePropertyList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<div>
    
	<c:if test="${message=='failure' || Result=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
	<c:if test="${message=='success' || Result=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
	<c:if test="${message=='delete' || Result=='delete'}"> <span class="failure"><spring:message code="delete"/></span></c:if>
	<c:if test="${message=='update' || Result=='update'}"> <span class="success"><spring:message code="update"/></span></c:if>
	<c:if test="${message=='deletefail'}"> <span class="failure">"Record Deletion Failed"</span></c:if>

</div>


<div id="dataTable">
   	<display:table name="${sessionScope.movablePropertyList}" excludedParams="*"
		export="false" class="list" requestURI="" id="movList" pagesize="10" sort="list">		
		<display:column style="width:10%;text-align:center" title="Mode of Reg">${movList.modeOfReg}</display:column>
		<display:column style="width:10%;text-align:center" title="Sale or Purchase Price">${movList.saleOrPurchasePrice}</display:column>
		<display:column style="width:10%;text-align:center" title="Source">${movList.source}</display:column>
		<display:column style="width:10%;text-align:center" title="Personal Savings">${movList.personalSavings}</display:column>
		<display:column style="width:10%;text-align:center" title="Remarks">${movList.remarks}</display:column>
		<display:column style="width:10%;text-align:center" title="Purpose">${movList.purpose}</display:column>
		
		<c:if test="${movList.status=='1'}">
			<display:column style="width:10%;text-align:center" title="Edit">
				<img src="./images/edit.gif" title="Edit" onclick="editMovableProperty()" />
				
			</display:column>
			
			<display:column style="width:10%;text-align:center" title="Delete">
					<img src="./images/delete.gif" title="Delete" onclick="deleteMovableProperty()" />		
			</display:column>
		</c:if>
	</display:table>
</div>



	<script>
		propertyJson = <%= (net.sf.json.JSONArray)session.getAttribute("movablePropertyList")%>
		$jq( function(){
	 $jq("#dataTable").displayTagAjax('paging');
	})
	</script>


<!-- End : movablePropertyList.jsp -->





