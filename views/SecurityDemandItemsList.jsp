<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : SecurityDemandItemsList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<div class="line">
	<c:if test="${message eq 'success'}"><div class="myStyle success""><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'update'}"><div class="myStyle success"><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'delete'}"><div class="myStyle success"><spring:message code="delete"/></div></c:if> 
	<c:if test="${message eq 'failure'}"><div class="myStyle failure"><spring:message code="failure"/></div></c:if>
	<c:if test="${message eq 'duplicate'}"><div class="myStyle failure"><spring:message code="duplicate"/></div></c:if>
	<c:if test="${message eq 'EXISTS'}"><div class="myStyle failure"><spring:message code="recordexists"/></div></c:if>
</div>

<div id="Pagination">
		  <%int i=1;%>
		<display:table name="${sessionScope.DemandItemsList}" excludedParams="*"	export="false" class="list" requestURI="" id="dil" pagesize="10" 
			sort="list">
				<display:column  title='Select' style="width:5%;">
					<c:choose>
						<c:when test="${not empty dil.memoNo}">
							<label><%=i%></label> 
						</c:when>
						<c:otherwise>
							<input type="checkbox" id="selectDemand<%=i%>" value=${dil.materialCode} name="materialCode"/> 
						</c:otherwise>
					</c:choose>
				</display:column>
				<display:column  title='NomenClature' style="width:20%;">${dil.description}</display:column>
				<display:column  title='qty' style="width:20%;">
				<c:choose>
						<c:when test="${not empty dil.memoNo}">
							<label>${dil.qty}</label> 
						</c:when>
						<c:otherwise>
							<input type="text" id="qty<%=i%>" value='${dil.qty}' name="qty"/> 
						</c:otherwise>
					</c:choose>
				</display:column>
				<display:column  title='UOM' style="width:15%;" >&nbsp;<div id="uom<%=i%>">${dil.uom}</div></display:column>
				<display:column  title='Memo Number' style="width:20%;">&nbsp;${dil.memoNo}</display:column>
				<display:column  title='Memo Date' style="width:20%;">&nbsp;${dil.memoDate}</display:column>
				<%i++;%>
		</display:table>
	</div>



<script>
$jq( function(){
	 $jq("#Pagination").displayTagAjax("paging");
	})
	var demandDate='${command.demandDate}';
	if(demandDate!=null && demandDate!='')
		document.getElementById("demandDate").innerHTML='<fmt:formatDate pattern="dd-MMM-yyyy" value="${command.demandDate}" />';
</script> 
<!-- End : SecurityDemandItemsList.jsp -->