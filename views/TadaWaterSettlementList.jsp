<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : TadaWaterSettlementList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<script type="text/javascript" src="script/script.js"></script>
<div>
	<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
	<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
</div>
<div>
<div id="advPagination">
<%int i=0; %>
	<display:table name="${sessionScope.tadaFinAdvList}" excludedParams="*"
		export="false" class="list" requestURI="" id="advance" pagesize="1000"
		sort="list" cellpadding="2" cellspacing="1">
		<display:column title="Req ID" style="width:3%;vertical-align:middle">&nbsp;<font color="blue">${advance.requestId}</font></display:column>
		<display:column title="Employee ID" style="width:6%;vertical-align:middle">&nbsp;${advance.sfid}</display:column>
		<display:column title="Issed Advance Amount" style="width:5%;vertical-align:middle">&nbsp;<font size="4.5em"><span class="WebRupee" >R</span></font><input type="text" size="7"  readonly="readonly" value="${advance.totalAmt}" name="tadaAdvanceAmount" id="tadaAdvanceAmount<%=i %>"/></display:column> <%-- ${advance.advanceAmountAftRes} --%>     <!-- This line has been changed -->
		<display:column title="Issuing Authority" style="width:12%;vertical-align:middle;">&nbsp;
		WATER BOARD
		</display:column>
		<display:column title="PDF Download" style="width:8%;vertical-align:middle;">&nbsp;
		<a href="hello.htm?requestId=${advance.requestId}&param=ExpenseesClaim"><font color="blue"> PDF</font></a>
		</display:column>
		
		<display:column title="Settlement/Reimbursement" style="width:12%;vertical-align:middle;">&nbsp;
		<a href="javascript:getWaterAdvCompDetails('${advance.requestId}')"><font color="blue"> Settlement/Reimbursement</font></a>
		</display:column>
		
		<display:column title="Cancel Request" style="width:12%;vertical-align:middle;">&nbsp;
		<a href="javascript:getWaterAdvCompDetailsForCancel('${advance.requestId}')"><font color="blue"> Cancel</font></a>
		</display:column>
	<%-- 	
		<display:column title="PDF Download" style="width:12%;vertical-align:middle;">&nbsp;
		<a href="hello.htm?requestId=${advance.requestId}&param=ExpenseesClaim"><font color="blue"> Cancel</font></a>
		</display:column> --%>
	
		<% i++; %>
	</display:table>
	</div>
		<div class="line" style="padding-top:3%;">


	
	</div>
	
	<script>
	  tadaFinAdvList = <%= (net.sf.json.JSONArray)session.getAttribute("tadaFinAdvList") %>;
	  tadaTdFinAdvCmplList = <%= (net.sf.json.JSONArray)session.getAttribute("tadaTdFinAdvCmplList") %>;
	  enableAdvList('advance');
	</script>
	<script>
			
	</script>
</div>
<!-- End : TadaWaterSettlementList.jsp -->





