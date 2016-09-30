<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : tutionFeeClaimRequestDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="net.sf.json.JSONArray"%>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css"/>
<script language="javascript" src="script/tutionFee.js"></script>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>

<div class="line">

		 <c:if test="${(workflowmap.statusMsg ne 'COMPLETED')}">
          <marquee direction="left" behavior="alternate"><div class="line" style="color:red" align="center">
	       Immediately submit your bills to Finance,Unless bills verified with Finance Section this claim will not be processed further.
	      </div>
	      </marquee>
	      </c:if>
<table width="100%" cellpadding="2" cellspacing="0" border="1" id="claimDetails1" class="sub_2">
<tr>
	<th width="12%">Name</th>
	<th width="10%">Date Of Birth/Age</th>
	<th width="10%">Claimed From</th>	
	<th width="10%">Claimed To</th>
	<th width="12%">AcademicType & Class Which is Claimed</th>
	<th width="6%">Type</th>
	<th width="8%">Claimed Amount</th>	
	<c:if test="${(workflowmap.lastStagePendingCheck=='lpending') || (workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.workflowStage eq 'FORWARDED' && workflowmap.checkStage eq 'previous')}"><th width="15%">Amt to be sanctioned</th></c:if>
	<c:if test="${workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.workflowStage ne 'FORWARDED' || workflowmap.lastStagePendingCheck eq 'completed' || (workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.workflowStage eq 'FORWARDED' && workflowmap.checkStage eq 'last')}"><th width="8%">Sanctioned Amt</th></c:if>
    <th width="8%">Details</th>
    </tr>
<c:forEach items="${workflowmap.tutionFeeClaimList}" var="claim">
	<tr>
		<td style="text-align:center">${claim.childName}</td>
		<td style="text-align:center">${claim.dateOfBirth}</td>
		<td style="text-align:center">${claim.fromDate1}</td>	
		<td style="text-align:center">${claim.toDate1}</td>	
		<td style="text-align:center"><b style="color: purple;">AcademicType:</b>${claim.academicType}, <b style="color: purple;">Class:</b>${claim.className}</td>
		<td style="text-align:center">${claim.type}</td>
		<td style="text-align:center">${claim.grandTotal}</td>	
		<c:if test="${(workflowmap.lastStagePendingCheck=='lpending') || (workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.workflowStage eq 'FORWARDED'  && workflowmap.checkStage eq 'previous')}">
		<td>
     	<input  id="${claim.childRelationId}" name="sanctionedAmt" size="20" readonly="readonly" style="text-align:right; background-color: #EEF4FC" disabled="disabled" value="${claim.sanctionedAmount}"/> 
     	</td>
		</c:if>	
		<c:if test="${workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.workflowStage ne 'FORWARDED' || workflowmap.lastStagePendingCheck eq 'completed' || (workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.workflowStage eq 'FORWARDED' && workflowmap.checkStage eq 'last')}">
		<td style="text-align:center">${claim.sanctionedAmount}</td>
	    </c:if>	
	    <c:if test="${(workflowmap.lastStagePendingCheck=='lpending') || (workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.workflowStage eq 'FORWARDED' &&  workflowmap.checkStage eq 'previous')}">
	    <td style="text-align:center"><a href="javascript:getChildClaimDetails('${claim.childRelationId}','${claim.childName}','${claim.limitId}');"><span id="hideDiv${claim.childRelationId}" style="display:none; color:red" >Hide</span><span id="showDiv${claim.childRelationId}">Click to Enter Amt ToBe Sanctioned</span></a></td>	
	    </c:if>
	    <c:if test="${(workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.statusMsg eq 'PENDING') || (workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.statusMsg eq 'CANCELLED') || (workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.statusMsg eq 'pending') || (workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.statusMsg eq null && workflowmap.checkStage eq 'previous')|| (workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.statusMsg eq '' && workflowmap.checkStage eq 'previous' && workflowmap.workflowStage ne 'FORWARDED') || (workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.statusMsg eq '' && workflowmap.checkStage eq 'last' && workflowmap.workflowStage ne 'FORWARDED')}">
	    <td style="text-align:center"><a href="javascript:getChildClaimDetails('${claim.childRelationId}','${claim.childName}','${claim.limitId}');"><span id="hideDiv${claim.childRelationId}" style="display:none; color:red" >Hide</span><span id="showDiv${claim.childRelationId}">Show</span></a></td>	
	    </c:if>
	    <c:if test="${(workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.statusMsg eq 'COMPLETED') || (workflowmap.lastStagePendingCheck eq 'completed' && workflowmap.workflowStage  eq 'COMPLETED') || (workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.workflowStage eq 'FORWARDED' && workflowmap.checkStage eq 'last') || (workflowmap.workflowStage eq 'Approve' && workflowmap.statusMsg  eq 'PROCESSING') || (workflowmap.lastStagePendingCheck eq 'completed' && workflowmap.workflowStage eq 'APPROVED') || (workflowmap.lastStagePendingCheck eq 'completed' && workflowmap.workflowStage eq 'FORWARDED')}">
	    <td style="text-align:center"><a href="javascript:getChildClaimDetails('${claim.childRelationId}','${claim.childName}','${claim.limitId}');"><span id="hideDiv${claim.childRelationId}" style="display:none; color:red" >Hide</span><span id="showDiv${claim.childRelationId}">View Sanctioned Amount</span></a></td>	
	    </c:if>
	    <td style="display: none">
				<input type="text" value=${claim.childRelationId} />
	    </td>
	</tr>
</c:forEach>
</table>	
<c:if test="${workflowmap.lastStagePendingCheck=='lpending'}">
<table width="100%" cellpadding="2" cellspacing="0" border="1" id="tableTotal" >
<tr>
<td width="74%">
          <b>Total Sanctioned Amount</b>
</td>
<td  width="74%">
<input type="text" size="35%" id="total" readonly="readonly" disabled="disabled" style="text-align:right; background-color: #EEF4FC " value="${workflowmap.teleSanctionedAmount}"></input>
</td>
</tr>
</table>
</c:if>
<div id="indDetails" class="line">
<%int i=1; %>
<c:forEach items="${workflowmap.mainTutionFeeClaimList}" var="mainList">
<div class="line" id="space">&nbsp;</div>

<fieldset id="claimDetails" style="display:none"><legend><strong><font color='green'>In respective Of  <span style="color:blue" id="childName"></span></font></strong></legend>
<table width="100%" cellpadding="2" cellspacing="0" border="1" id="claimTableDeails" class="sub_2">
<tr>
    <th width="20%">Claim Name</th>
	<th width="12%">App No.</th>
	<th width="12%">App Date</th>	
	<th width="12%">Claimed Amount</th>
    <c:if test="${(workflowmap.lastStagePendingCheck=='lpending') || (workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.workflowStage eq 'FORWARDED' && workflowmap.checkStage eq 'previous')}"><th width="10%">Amt to be sanctioned</th></c:if>
    <c:if test="${workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.workflowStage ne 'FORWARDED' || workflowmap.lastStagePendingCheck eq 'completed' || (workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.workflowStage eq 'FORWARDED' && workflowmap.checkStage eq 'last')}">
		<th width="12%" style="color:blue">Sanctioned Amount</th>
	</c:if>
</tr>
<c:forEach items="${mainList}" var="subList">
<script>
$jq('#claimDetails').attr('id','subChildList${subList.childRelationId}');
$jq('#childName').attr('id','childName${subList.childRelationId}');
$jq('#claimTableDeails').attr('id','claimTableDeails${subList.childRelationId}');
</script>
<c:if test="${subList.appNo!=null && subList.appNo!='' && subList.appNo!='[]'}" >
<tr id='${subList.id}'>
    <td style="text-align:center">${subList.claimDetails.claimName}</td>
	<td style="text-align:center">${subList.appNo}</td>
	<td style="text-align:center"><fmt:formatDate pattern="dd-MMM-yyyy" value="${subList.appDate}"/></td>	
	<td style="text-align:center">${subList.amount}</td>	
     <c:if test="${(workflowmap.lastStagePendingCheck=='lpending') || (workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.workflowStage eq 'FORWARDED' && workflowmap.checkStage eq 'previous')}"><td>
     	<input id="${subList.claimId}" name="sanctionedAmt"  size="15"  style="text-align:right"  onkeypress="return checkInt(event);" onkeyup="javascript:sumAllAtSanctionStatus('${workflowmap.maxAmountPerOneChild}','${subList.limitId}','<%=i %>');" value="${subList.sanctionedAmount}" /> 
     	</td>
     </c:if>
     <c:if test="${workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.workflowStage ne 'FORWARDED' || workflowmap.lastStagePendingCheck eq 'completed' || (workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.workflowStage eq 'FORWARDED' && workflowmap.checkStage eq 'last')}">
		<td style="text-align:center">${subList.sanctionedAmount}</td>
	 </c:if>

</tr>
</c:if>
</c:forEach>
</table>
<c:if test="${workflowmap.tutionFeeOrgRoleId  eq 'Yes'}">
<c:forEach items="${mainList}" var="tot">
<c:if test="${tot.totalClaimed ne 0 && workflowmap.statusMsg eq 'PENDING'}">
<div>
<table width="100%" cellpadding="2" cellspacing="0" border="1" id="tableTotal1" >
<tr>
<td style="text-align:left;" width="55%"><b>TOTAL</b></td>
<td style="text-align:center" width="15%">${tot.totalClaimed}</td>
<td style="text-align:center" width="15%">${tot.totalSanctionedAmt}</td>
</tr>
</table>
</div>
</c:if>
</c:forEach>
</c:if>

<c:forEach items="${mainList}" var="tot">
<c:if test="${(tot.totalClaimed ne 0 && workflowmap.tutionFeeOrgRoleId  eq 'Yes'  && workflowmap.statusMsg eq 'COMPLETED') || (tot.totalClaimed ne 0 && workflowmap.tutionFeeOrgRoleId  eq 'No' && workflowmap.lastStagePendingCheck eq 'completed') || (workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.statusMsg eq 'pending' && workflowmap.workflowStage  eq 'Approve' && tot.totalClaimed ne 0) ||(workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.statusMsg eq 'CANCELLED' && tot.totalClaimed ne 0)}">
<div>
<table width="100%" cellpadding="2" cellspacing="0" border="1" id="tableTotal1" >
<tr>
<td style="text-align: left;" width="55%"><b>TOTAL</b></td>
<td style="text-align:center" width="15%">${tot.totalClaimed}</td>
<td style="text-align:center" width="15%">${tot.totalSanctionedAmt}</td>
</tr>
</table>
</div>
</c:if>
</c:forEach>

</fieldset>
<% i++; %>
</c:forEach></div>
<div class="line">
	     <div class="bold half">TuitionFee Request Form :<a style="color:blue" href="javascript:printTutionFeeRequestFormDetails('${workflowmap.requestId}');">Print & Sign</a></div>
</div>
<br/>
<div style="color: red;">
      Note:<br/>
          1.Submit the Signed Application Form (1 Original + 1 Duplicate) along with the Bills.<br/>
          2.Unless Above Documents Submitted Claim will not be Processed.
     </div>
</div>
<script>
tutionFeeLimitList= <%= (net.sf.json.JSONArray) session.getAttribute("tutionFeeLimitList") %>;
</script>

<!-- End : tutionFeeClaimRequestDetails.jsp -->