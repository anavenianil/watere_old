<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : TadaTdAdvList.jsp -->

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
		<display:column title="Req ID" style="width:3%;vertical-align:middle">&nbsp;<a href="javascript:getWaterRequestDetails('${advance.requestId}')"><font color="blue">${advance.requestId}</font></a></display:column>
		<display:column title="Employee ID" style="width:6%;vertical-align:middle">&nbsp;${advance.sfid}</display:column>
		<%-- <display:column title="Employee" style="width:8%;vertical-align:middle">&nbsp;${advance.name}</display:column> --%>
		<display:column title="Required Advance Amount" style="width:5%;vertical-align:middle">&nbsp;<font size="4.5em"><span class="WebRupee" >R</span></font><input type="text" size="7" value="${advance.totalAmt}" readonly="readonly" name="tadaAdvanceAmount" id="tadaAdvanceAmount<%=i %>"/></display:column> <%-- ${advance.advanceAmountAftRes} --%>     <!-- This line has been changed -->
		
		<display:column title="Issuing Authority" style="width:12%;vertical-align:middle;">&nbsp;
		WATER BOARD
		</display:column>
		
		<display:column title="Authority to Move" style="width:12%;vertical-align:middle;">&nbsp;
		 TADA WATER
		</display:column>
		
	
		<% i++; %>
	</display:table>
	</div>
		<div class="line" style="padding-top:3%;">


<div style="display: none">
	<div class="line">
	<div class="quarter bold">Sanction No123 <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text"  name="sanctionNo" id="sanctionNoAdv" onkeypress="javascript:return checkInt(event);"/></div>
	                </div>
	<div class="line">
	<div class="quarter bold">Bill No <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text" name="billNo" id="billNoAdv"/></div></div>
	<div class="line">
    <div class="quarter bold">Account Officer<span class="mandatory">*</span></div>
    <div class="quarter"><select name="accOfficer" id="accOfficerAdv" style="width:160px"><option value="select">Select</option>
				<c:forEach var="acc" items="${sessionScope.AccountOfficerList}">
					<option value="${acc.sfid}">${acc.name}</option>
				</c:forEach> </select>
				</div>
	</div>
	</div>
	
	</div>
	
	<script>
	  tadaFinAdvList = <%= (net.sf.json.JSONArray)session.getAttribute("tadaFinAdvList") %>;
	  tadaTdFinAdvCmplList = <%= (net.sf.json.JSONArray)session.getAttribute("tadaTdFinAdvCmplList") %>;
	  enableAdvList('advance');
	</script>
	<script>
			
	</script>
</div>
<!-- End : TadaTdAdvList.jsp -->





