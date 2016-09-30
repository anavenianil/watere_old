<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : IRItemDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<div class="line">
	<c:if test="${message eq 'success'}"><div class="myStyle success"><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'update'}"><div class="myStyle success"><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'delete'}"><div class="myStyle success"><spring:message code="delete"/></div></c:if> 
	<c:if test="${message eq 'failure'}"><div class="myStyle failure"><spring:message code="failure"/></div></c:if>
	<c:if test="${message eq 'duplicate'}"><div class="myStyle failure"><spring:message code="duplicate"/></div></c:if>
	<c:if test="${message eq 'EXISTS'}"><div class="myStyle failure"><spring:message code="recordexists"/></div></c:if>
</div>

<div id="Pagination">
	<%int i=1;%>
	<display:table name="${sessionScope.IRItemsJson}" excludedParams="*"
		export="false" class="list" requestURI="" id="invoiceList" pagesize="10" 
		sort="list">
		<display:column  title='Sno' style="width:5%;"><%=i%></display:column>
		<display:column  title='Material Code' style="width:5%;">&nbsp;${invoiceList.materialCode}</display:column>
		<display:column  title='Nomenclature' style="width:20%;">&nbsp;${invoiceList.description}</display:column>
		<display:column  title='Qty' style="width:20%;text-align:right">&nbsp;${invoiceList.qty}</display:column>
		<display:column  title='Unit Rate(<b><del>&#2352;</del></b>)' style="width:15%;text-align:right" >&nbsp;${invoiceList.unitRate}</display:column>
		<display:column  title='Tax Type' style="width:20%;">&nbsp;
		<select name="taxType" style="width:145px" id="IRdemandNo">
		 	<option value="">Select</option>
			<c:forEach var="demand" items="${command.taxTypeList}">
				 <option value="${demand.id}#${demand.percentage}#${invoiceList.materialCode}">${demand.name}-${demand.percentage}%</option>
			</c:forEach>														
		</select>
		</display:column>
		<display:column  title='Tax Amount(<b><del>&#2352;</del></b>)' style="width:20%;text-align:right">&nbsp;${invoiceList.taxAmount}</display:column>
	<%i++;%>
	</display:table>
</div>
<script>
$jq( function(){
	 $jq("#Pagination").displayTagAjax();
	})
</script> 
<!-- End : IRItemDetails.jsp -->