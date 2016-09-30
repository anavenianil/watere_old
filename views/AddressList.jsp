<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : AddressList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>

<div>
<c:if test="${message=='success'}"><span class="success"><spring:message code="success"/></span></c:if>
<c:if test="${message=='update'}"><span class="success"><spring:message code="success"/></span></c:if>
<c:if test="${message=='delete'}"><span class="success"><spring:message code="delete"/></span></c:if>
<c:if test="${message=='addressTypeExisted'}"><span class="failure"><spring:message code="recordexists"/></span></c:if>
<c:if test="${message=='failure'}"><span class="failure"><spring:message code="failure"/></span></c:if>
</div>
<aa:zone name="dataTable">
   	<display:table name="${JsonAddressList}" excludedParams="*"
		export="false" class="list" requestURI="" id="dataList" pagesize="10"
		sort="list">
		<display:column title="Address Type" style="width:15%;">${dataList.addressType}</display:column>
		<display:column title="Address Line1" style="width:25%;">${dataList.address1}</display:column>
		<display:column title="Address Line2" style="width:25%;">&nbsp;${dataList.address2}</display:column>
		<display:column title="City" style="width:15%;">${dataList.city}</display:column>
		<display:column title="District" style="width:15%;">&nbsp;${dataList.district}</display:column>
	<%-- 	<display:column title="State" style="width:10%;">${dataList.state}</display:column>
		<display:column title="Phone" style="width:10%;">${dataList.phone}</display:column>
		<display:column title="Email" style="width:25%;">${dataList.email}</display:column>
		--%>
		<display:column style="width:5%;text-align:center" title="Edit">
				<img src="./images/edit.gif" title="Edit"
					onclick="editAddress(JsonAddressList,'${dataList.id}')" />
		</display:column >
	<%-- 	<display:column style="width:5%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete"
				onclick="deleteAddress('${dataList.id}')" />
		</display:column> 
		--%>
	</display:table>
</aa:zone>


<script>
	JsonAddressList= <%= (net.sf.json.JSONArray)session.getAttribute("JsonAddressList") %>;
	
	displayPaging("dataTable","dataList");
	
</script>

<!-- End : AddressList.jsp -->
