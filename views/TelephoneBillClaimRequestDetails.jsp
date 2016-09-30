<!-- begin: TelephoneBillClaimRequestDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<head>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="script/telephone.js"></script>
<script language="javascript" src="script/workflow.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
</head>
<div class="line">
    <c:if test="${(workflowmap.statusMsg ne 'COMPLETED')}">
     	<div>  
	       <marquee direction="left" behavior="alternate"><div class="line" style="color:red" align="center">
	       Immediately submit your bills to Finance,Unless bills verified with Finance Section this claim will not be processed further.
	      </div>
	      </marquee>
	   </div>
	   </c:if>
	   
	  <table width="100%" cellpadding="2" cellspacing="0" border="1" id="telephoneClaimInternetDetails" class="sub_2">
							        <tr>
							        <th style="color: blue" width="10%"> Designation</th> 
							        <th style="color: blue" width="10%">Internet Applicability</th>
								    <th style="color: blue" width="10%"> Eligible Amount</th> 
								    <th style="color: blue" width="10%">Service Tax </th>
								    <th style="color: blue" width="8%">Total Eligible Amount</th>
								    </tr>
				         <c:forEach items="${workflowmap.teleMaxAmount}" var="teleInteFlag">			    
								    <tr>
								    <td style="text-align: center;color: purple;" width="10%">${workflowmap.designation}</td> 
								    <c:choose>
								    <c:when test="${workflowmap.internetFlag eq 1}">
								    <td style="text-align: center" width="10%">With Internet</td>
								    </c:when>
								    <c:otherwise>
								    <td style="text-align: center" width="10%">WithOut Internet</td>
								    </c:otherwise>
								    </c:choose>
								   <td style="text-align: center" width="10%"><font size="4.5em"><span class="WebRupee" >R</span></font>${workflowmap.amount}</td>
								   	<td style="text-align: center" width="10%">${workflowmap.serviceTax}%</td>
								   	<td style="text-align: center"><font size="4.5em"><span class="WebRupee" >R</span></font>${workflowmap.totAmount}</td>
								    </tr>
					   </c:forEach>	
		 </table>
      <div class="line" id="space">&nbsp;</div>
<table width="100%" cellpadding="2" cellspacing="0" border="1" id="telephoneClaimDetails" class="sub_2">
<tr>
            <th width="10%">Name</th>
            <th width="10%">ClaimedFrom</th>
            <th width="10%">ClaimedTo</th>
            <th width="10%">ClaimedAmount</th>
            <c:if test="${(workflowmap.lastStagePendingCheck eq'lpending') || (workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.workflowStage eq 'FORWARDED' && workflowmap.checkStage eq 'previous')}"><th width="10%">Amt to be sanctioned</th></c:if>
            <c:if test="${(workflowmap.lastStagePendingCheck eq 'pending' &&  workflowmap.workflowStage ne 'FORWARDED') || workflowmap.lastStagePendingCheck eq 'completed'  || (workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.workflowStage eq 'FORWARDED' && workflowmap.checkStage eq 'last')}"><th width="8%" style="color: blue;">Sanctioned Amt</th></c:if>   
</tr>
<c:forEach items="${workflowmap.telephoneBillClaimList}" var="claim">
<c:if test="${claim.name!=null}" >
 <tr>
           <td style="text-align:center">${claim.name}</td>
           <td style="text-align:center"><fmt:formatDate value="${claim.fromDate}" pattern="dd-MMM-yyyy"/></td>
           <td style="text-align:center"><fmt:formatDate value="${claim.toDate}" pattern="dd-MMM-yyyy"/></td>
           <td style="text-align:center"><font size="4.5em"><span class="WebRupee" >R</span></font>${claim.grandTotal}</td>
           <c:if test="${workflowmap.lastStagePendingCheck eq 'lpending' || (workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.workflowStage eq 'FORWARDED' && workflowmap.checkStage eq 'previous')}">
           <td><font size="4.5em"><span class="WebRupee" >R</span></font>
     	   <input id="${claim.name}" name="sanctionedAmt" size="20" readonly="readonly" style="text-align:right; background-color: #EEF4FC" disabled="disabled" value="${workflowmap.teleSanctionedAmount}"/> 
     	   </td>
		   </c:if>	
           <c:if test="${workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.workflowStage ne 'FORWARDED' || workflowmap.lastStagePendingCheck eq 'completed' || (workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.workflowStage eq 'FORWARDED' && workflowmap.checkStage eq 'last')}">
		   <td style=" text-align:center" ><font size="4.5em"><span class="WebRupee" >R</span></font>${claim.sanctionedAmount}</td>
	       </c:if>	
 </tr>
 </c:if>
</c:forEach>
</table>
<%int i=1; %>
<div class="line" id="space">&nbsp;</div>
<table width="100%" cellpadding="2" cellspacing="0" border="1" id="telephoneClaimTable" class="sub_2">
      <tr>
              <th width="10%">Claim Name</th>
              <th width="10%">Bill No</th>
              <th width="10%">Bill Date</th>
              <th width="10%">Receipt No</th>
              <th width="10%">Receipt Date</th>
              <th width="10%">Claimed Amount</th>
              <c:if test="${(workflowmap.lastStagePendingCheck eq'lpending') || (workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.workflowStage eq 'FORWARDED' && workflowmap.checkStage eq 'previous')}"><th width="10%">Amt to be sanctioned</th></c:if>
              <c:if test="${(workflowmap.lastStagePendingCheck eq 'pending' &&  workflowmap.workflowStage ne 'FORWARDED') || workflowmap.lastStagePendingCheck eq 'completed' || (workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.workflowStage eq 'FORWARDED' && workflowmap.checkStage eq 'last')}">
	 	      <th width="12%" style="color: blue;">Sanctioned Amount</th>
			  </c:if>
			 
        </tr>
<c:forEach items="${workflowmap.mainTelephoneBillClaimList}" var="telephoneClaimList">
<c:if test="${telephoneClaimList.billDated!=null && telephoneClaimList.billDated!='' && telephoneClaimList.billDated!='[]'}" >
        <tr>
              <td style="text-align:center">${telephoneClaimList.claimName}</td>
              <td style="text-align:center">${telephoneClaimList.billNo}</td>
              <td style="text-align:center"><fmt:formatDate value="${telephoneClaimList.billDated}" pattern="dd-MMM-yyyy"/></td>
              <td style="text-align:center">${telephoneClaimList.receiptNo}</td>
              <td style="text-align:center"><fmt:formatDate value="${telephoneClaimList.receiptDated}" pattern="dd-MMM-yyyy"/></td>
              <td style="text-align:center"><font size="4.5em"><span class="WebRupee" >R</span></font>${telephoneClaimList.total1}</td>
              <c:if test="${(workflowmap.lastStagePendingCheck eq'lpending') || (workflowmap.lastStagePendingCheck eq 'pending'  && workflowmap.workflowStage eq 'FORWARDED' && workflowmap.checkStage eq 'previous')}">
              	<c:if test="${(telephoneClaimList.sanctionedAmount ne 0)}">
              		<td><font size="4.5em"><span class="WebRupee" >R</span></font><input id="${telephoneClaimList.id}" value="${telephoneClaimList.sanctionedAmount}" name="sanctionedAmt"  size="20"  style="background-color: pink;text-align:right"  onkeypress="return checkInt(event);" onkeyup="javascript:sumAllOfTelephoneAtSanctionStatus('${workflowmap.totAmount}','${workflowmap.claimedAmount}','<%=i %>');"/></td><%-- value="${telephoneClaimList.sanctionedAmount}" --%>
              	</c:if>
              	<c:if test="${(telephoneClaimList.sanctionedAmount eq 0)}">
              		<td><font size="4.5em"><span class="WebRupee" >R</span></font><input id="${telephoneClaimList.id}" value="${telephoneClaimList.total1}" name="sanctionedAmt"  size="20"  style="background-color: pink;text-align:right"  onkeypress="return checkInt(event);" onkeyup="javascript:sumAllOfTelephoneAtSanctionStatus('${workflowmap.totAmount}','${workflowmap.claimedAmount}','<%=i %>');"/></td><%-- value="${telephoneClaimList.sanctionedAmount}" --%>
              	</c:if>
              </c:if>
              <c:if test="${(workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.workflowStage ne 'FORWARDED') || workflowmap.lastStagePendingCheck eq 'completed' || (workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.workflowStage eq 'FORWARDED' && workflowmap.checkStage eq 'last')}">
		      <td style="text-align:center"><font size="4.5em"><span class="WebRupee" >R</span></font>${telephoneClaimList.sanctionedAmount}</td>
	          </c:if>
        </tr>
        
</c:if>
</c:forEach>
     <c:if test="${(workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.workflowStage eq 'FORWARDED' && workflowmap.checkStage eq 'previous') || (workflowmap.lastStagePendingCheck eq 'lpending' && workflowmap.workflowStage eq 'FORWARDED' && workflowmap.checkStage eq 'last' || (workflowmap.lastStagePendingCheck eq 'lpending' && workflowmap.workflowStage eq 'APPROVED' && workflowmap.checkStage eq 'last'))}">
	<tr>
        <td colspan="6" style="text-align :right; "><b>PAID OUT OF CASH ASSIGNMENT</b></td>
        <td>
        <c:choose>
        <c:when test="${workflowmap.cashAssignEligibilitySfid ne ''}">
        <input id="cashAssignment" name="cashAssignment"  size="20"  style="text-align:right"  onkeypress="return checkInt(event);" onkeyup="javascript:sumAllOfTelephoneAtSanctionStatus('${workflowmap.totAmount}','${workflowmap.claimedAmount}' ,'<%=i %>');" value="${workflowmap.cashAssignmentAmount }"/> 
        </c:when>
        <c:otherwise>
        0
        </c:otherwise>
	   </c:choose>
	   </td>
     
       </tr>
       
	 </c:if>
</table>
 <c:if test="${(workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.workflowStage eq 'FORWARDED' && workflowmap.checkStage eq 'previous') || (workflowmap.lastStagePendingCheck eq 'lpending' && workflowmap.workflowStage eq 'FORWARDED' && workflowmap.checkStage eq 'last') || (workflowmap.lastStagePendingCheck eq 'lpending' && workflowmap.workflowStage eq 'APPROVED' && workflowmap.checkStage eq 'last')}">
 <table width="100%" cellpadding="2" cellspacing="0" border="1" id="totalAmountSanctionDiv">
       <tr>
        <td width="80%" style="text-align :right;"><b> <font color="blue">TOTAL AMOUNT TO BE SANCTIONED</font></b></td>
         <td><font size="4.5em"><span class="WebRupee" >R</span></font><input width="25%" id="telephoneTotal" name="telephoneTotal"  size="20"  style="text-align:right"  onkeypress="return checkInt(event);" readonly="readonly" value="${workflowmap.teleSanctionedAmount}"/> </td>
        </tr>
</table>
</c:if>
<% i++; %>
<div class="line">
<div class="quarter" style="color: purple; text-align: right; width: 83%; font-size: 120%">Finance Accepted Amount :</div>
<div style="text-align: right; width: 93%;font-size: 120%; color: purple;"><font size="4.5em"><span class="WebRupee" >R</span></font>${workflowmap.teleEligibleAmt}</div>
</div>
<c:if test="${workflowmap.roleInstanceName eq 'TA DA /Medical Section Head' or workflowmap.roleInstanceName eq 'Tuition Fee and Telephone Bill task holder' or menuLinks.configurationDetails eq '1'}">
<div class="line">
<div class="quarter" style="color: purple; text-align: right; width: 83%; font-size: 120%">CDA Accepted Amount :</div>
<div style="text-align: right; width: 93%;font-size: 120%; color: purple;"><font size="4.5em"><span class="WebRupee" >R</span></font>${workflowmap.cdaApprovedAmt}</div>
</div>
 <table width="40%" cellpadding="2" cellspacing="0" border="1" id="telephoneClaimInternetDetails" class="sub_2">
							        <tr>
							        <th  width="10%"> Sanction No</th> 
							        <th  width="10%">Bill No </th>
								    <th  width="10%"> DvNumber</th> 
								    <th  width="10%">DvDate </th>
								    </tr>
								    <tr>
								    <td style="text-align: center" width="10%" height="20">${workflowmap.approvedSantionNo}</td> 
								    
								    <td style="text-align: center" width="10%">${workflowmap.approvedBillNo}</td>
								    
								    <td style="text-align: center" width="10%"> ${workflowmap.dvNumber}</td>
								   
								   <td style="text-align: center" width="10%">${workflowmap.dvDate}</td>
								    </tr>
		 </table>
</c:if>
<%-- <div class="line">
<div class="quarter" style="color: purple; text-align: right; width: 83%; font-size: 120%">Sanction No :</div>
<div style="text-align: right; width: 93%;font-size: 120%; color: purple;">${workflowmap.approvedSantionNo}</div>
</div>
<div class="line">
<div class="quarter" style="color: purple; text-align: right; width: 83%; font-size: 120%">Bill No :</div>
<div style="text-align: right; width: 93%;font-size: 120%; color: purple;">${workflowmap.approvedBillNo}</div>
</div>

<div class="line">
<div class="quarter" style="color: purple; text-align: left; width: 83%; font-size: 120%">DvNumber  / DvDate  : ${workflowmap.cdaDetailsDTO.dvNumber}   & ${workflowmap.cdaDetailsDTO.dvDate}</div>
<div style="text-align: left; width: 40%;font-size: 120%; color: purple;">${workflowmap.cdaDetailsDTO.dvNumber}   & ${workflowmap.cdaDetailsDTO.dvDate}</div>
</div>
<div class="line">
<div class="quarter" style="color: purple; text-align: right; width: 83%; font-size: 120%">DvDate :</div>
<div style="text-align: right; width: 93%;font-size: 120%; color: purple;">${workflowmap.cdaDetailsDTO.dvDate}</div>
</div> --%>

<div class="line">
	     <div class="bold half">Print Application Form Here <a style="color:blue" href="javascript:printTelephoneBillRequestFormDetails('${workflowmap.requestId}');">Print & Sign</a></div></div>
<br/>
<div style="color: red;">
      Note:<br/>
          1.Submit the Signed Application Form (1 Original + 1 Duplicate) along with the Bills.<br/>
          2.Unless Above Documents Submitted Claim will not be Processed.
</div>
     <br/>
<c:forEach items="${workflowmap.telephoneBillClaimList}" var="claimRemarks">
  <div class="line" >
 	<div class="quarter leftmar" style="margin-left: 8px;">Task Holder Remarks<span></span></div>
 	
 	<c:if test="${(workflowmap.statusMsg ne 'PENDING' && workflowmap.workflowStage ne 'Approve') || (workflowmap.statusMsg ne 'COMPLETED' && workflowmap.workflowStage ne 'Approve')}">
 	<div class="quarter"><textarea name="remarks" id="remarks">${claimRemarks.taskHolderRemarks}</textarea>
	</div>
 	</c:if>
 	<c:if test="${(workflowmap.statusMsg eq 'PENDING' || workflowmap.statusMsg eq 'PROCESSING' && workflowmap.workflowStage eq 'Approve') || (workflowmap.statusMsg eq 'COMPLETED' && workflowmap.workflowStage eq 'Approve') || (workflowmap.statusMsg eq 'CANCELLED' && workflowmap.workflowStage eq 'Approve')}">
 	<div class="quarter"><textarea id="remarks" style="text-align: left;" name="remarks" cols="18" rows="2" readonly="readonly">${claimRemarks.taskHolderRemarks } </textarea>
	</div>
 	</c:if>
 	
	 
	 
  
	<div class="quarter "><b>User Remarks</b></div>
	<div class="quarter left" style="margin-left:50;"><textarea style="background-color: #EEF4FC; border: 1px solid #333333; color: #000000;" rows="2" cols="20"  disabled="disabled">${claimRemarks.userRemarks}</textarea>
	</div>		
</div>
 </c:forEach>
</div>


<!-- end: TelephoneBillClaimRequestDetails.jsp -->