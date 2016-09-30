<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start :  MTAddressList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>

	<c:if test="${message eq 'success'}"><div class="myStyle success""><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'update'}"><div class="myStyle success"><spring:message code="update"/></div></c:if>
	<c:if test="${message eq 'delete'}"><div class="myStyle success"><spring:message code="delete"/></div></c:if> 
	<c:if test="${message eq 'failure'}"><div class="myStyle failure"><spring:message code="failure"/></div></c:if>
	<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
<div id="dataTable">
   	<display:table name="${sessionScope.addressList}" excludedParams="*"
		export="false" class="list" requestURI="" id="address" pagesize="10" sort="list">
		<display:column  title='Address' >${address.addressName}</display:column>
		<display:column style="width:10%;text-align:center" title="Edit">
			<%-- <img src="./images/edit.gif" title="Edit" onclick="editAddressDetails('${address.addressId}','${address.addressName}')"/>--%>
		<img src="./images/edit.gif" title="Edit" onclick="editAddressDetails('${address.addressId}')"/>
		</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="deleteAddressDetails('${address.addressId}')" />
		</display:column>
	</display:table>
</div>
<script>
	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	 addressListJson	= <%=(net.sf.json.JSONArray)session.getAttribute("addressListJson")%>;
	
</script>


<%-- End :   MTAddressList.jsp --%>
