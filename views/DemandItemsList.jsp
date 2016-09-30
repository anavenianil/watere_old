<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : DemandItemsList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<div class="line">
	<c:if test="${message eq 'success'}"><div class="myStyle success""><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'update'}"><div class="myStyle success"><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'delete'}"><div class="myStyle success"><spring:message code="delete"/></div></c:if> 
	<c:if test="${message eq 'failure'}"><div class="myStyle failure"><spring:message code="failure"/></div></c:if>
	<c:if test="${message eq 'duplicate'}"><div class="myStyle failure"><spring:message code="duplicate"/></div></c:if>
	<c:if test="${message eq 'EXISTS'}"><div class="myStyle failure"><spring:message code="recordexists"/></div></c:if>
	<c:if test="${message eq 'lessQty'}"><div class="myStyle failure"><spring:message code="lessQty"/></div></c:if>

</div>

<div id="Pagination">
	<%int i=1;%>
	<display:table name="${sessionScope.JsonDemandList}" excludedParams="*"
		export="false" class="list" requestURI="" id="demandList" pagesize="10" 
		sort="list">
		<display:column  title='Sno' style="width:5%;"><%=i%></display:column>
		<display:column  title='Nomenclature' style="width:20%;">&nbsp;${demandList.description}</display:column>
		
		<c:if test="${demand.type ne 'ConversionVoucher'}">
			<display:column  title='Qty' style="width:20%;">&nbsp;${demandList.qty}</display:column>
			<display:column  title='UOM' style="width:15%;" >&nbsp;${demandList.uom}</display:column>			
			<display:column  title='Consumable/Non-Consumable' style="width:10%;">
			&nbsp;
			<c:if test="${demandList.cflag eq 'NC'}">
				Non-Consumable
			</c:if>
			<c:if test="${demandList.cflag eq 'C'}">
				Consumable
			</c:if>
			</display:column>
			<c:if test="${sessionScope.mmgVoucher ne 'voucher'}">
				<display:column title='Estimated Cost(<b><del>&#2352;</del></b>)' style="width:20%;">&nbsp;<fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="2" value="${demandList.unitRate}"/></display:column>
			</c:if>
				
		</c:if>
		<c:if test="${demand.type eq 'ConversionVoucher'}">
			<display:column  title='UOM (Present)' style="width:15%;text-align:right" >&nbsp;${demandList.uom}</display:column>
			<display:column  title='UOM (To be convert)' style="width:15%;" >
			${demandList.uomConvert}
			</display:column>
			<display:column  title='C/NC (Present)' style="width:10%;">&nbsp;${demandList.cflag}</display:column>
			<display:column  title='C/NC (To be convert)' style="width:20%;">
			${demandList.cncConvert}
			</display:column>
		</c:if>
		<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit"
				onclick="setMaterialValues('${demandList.materialCode}',JsonDemandList)"/>
		</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete"
				onclick="deleteItem('${demandList.materialCode}')"/>
		</display:column>
		<%i++;%>
	</display:table>
</div>
<script>
JsonDemandList= <%= (net.sf.json.JSONArray)session.getAttribute("JsonDemandList") %>;
itemsjson = <%= (net.sf.json.JSONArray)session.getAttribute("materialsJson") %>
$jq( function(){
	 $jq("#Pagination").displayTagAjax();
	})
</script> 
<!-- End : DemandItemsList.jsp -->