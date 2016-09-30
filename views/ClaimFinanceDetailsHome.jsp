<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : ClaimFinanceDetailsHome.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/tabs.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />

<script type="text/javascript">
$jq(function() {
	var $tabs = $('#tabs').tabs();
	$jq(".ui-tabs-panel").each(function(i){
		var totalSize = $jq(".ui-tabs-panel").size() - 1;

		if (i != totalSize) {
			next = i + 2;
			$jq(this).append("<a href='#' class='next-tab mover' rel='" + next + "'>Next Page &#187;</a>");
			
		}
  
		if (i != 0) {
			prev = i;
			$jq(this).append("<a href='#' class='prev-tab mover' rel='" + prev + "'>&#171; Prev Page</a>");
			
		}
  		
	});

	$jq('.next-tab, .prev-tab').click(function() { 
		$tabs.tabs('select', $(this).attr("rel"));
		return false;
	});
      
});
</script>
<div class="line">
	     <div id="page-wrap">		
			 <div id="tabs">
				 <ul>        		
	        		<li><a href="#fragment-1">Officers</a></li>
	        		<li><a href="#fragment-2">Staff</a></li>
	        	</ul>
	<div id="fragment-1" class="ui-tabs-panel" >
	 <div id="dataTable" class="line">
     <%int i=0; %>
	 <display:table name="${tutionFee.officersList}" excludedParams="*"
		export="false" class="list" requestURI="" id="tfOfficers" pagesize="1000"
		sort="list" cellpadding="2" cellspacing="1">
		<display:column  style="width:3%;vertical-align:middle" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'row\');"/>'>
			<input type="checkbox" value="${tfOfficers.pk}" class="row" name="row" id="encashed<%=i %>" onclick="checkBoxCheckForTuitionFee(this.name);"/>
		</display:column>
		<display:column title="Req ID" style="width:2%;vertical-align:middle">&nbsp;${tfOfficers.requestID}</display:column>
		<display:column title="SFID" style="width:6%;vertical-align:middle">&nbsp;${tfOfficers.sfID}</display:column>
		<display:column title="Employee" style="width:18%;vertical-align:middle">&nbsp;${tfOfficers.empName}</display:column>
		<display:column title="Claimed Amount" style="width:5%;vertical-align:middle">&nbsp;<input type="text" size="7" style="text-align: right;" value="${tfOfficers.claimedAmount}" name="claimedAmount" id="claimedAmount" readonly="readonly"/></display:column>
		<display:column title="Sanctioned Amount" style="width:5%;vertical-align:middle">&nbsp;<input type="text" size="7" style="text-align: right;" value="${tfOfficers.sanctionedAmount}" name="sanctionedAmount" id="sanctionedAmount" readonly="readonly"/></display:column>
		<display:column title="Sanction No<span class='mandatory'>*</span>" style="width:7%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${tfOfficers.sanctionNo}" name="sanctionNo" id="sanctionNoEncash<%=i %>" readonly="readonly"/></display:column>
		<display:column title="Bill No<span class='mandatory'>*</span>" style="width:7%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${tfOfficers.billNo}" name="billNo" id="billNoEncash<%=i %>"  readonly="readonly"/></display:column>
		<display:column title="Accounts Officer.<span class='mandatory'>*</span>" style="width:10%;vertical-align:middle">&nbsp;<input type="text" size="12" value="${tfOfficers.accOfficer}" name="accOfficer" id="accOfficer<%=i %>" readonly="readonly"/></display:column>
		<display:column title="CFA Officer.<span class='mandatory'>*</span>" style="width:10%;vertical-align:middle">&nbsp;<input type="text" size="12" value="${tfOfficers.cfaOfficer}" name="cfaOfficer" id="cfaOfficer<%=i %>" readonly="readonly"/></display:column>
		<display:column title="DV.No" style="width:5%;align:middle">
		<c:if test="${tfOfficers.dvNo==null}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--</c:if>
		<c:if test="${tfOfficers.dvNo!=''}">${tfOfficers.dvNo}</c:if>
		</display:column>
		<display:column title="DV.Date" style="width:5%;vertical-align:middle">
		<c:if test="${tfOfficers.dvDate==null}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--</c:if>
		<c:if test="${tfOfficers.dvDate!=''}"><fmt:formatDate value="${tfOfficers.dvDate}" pattern="dd-MMM-yyyy"/></c:if>
		</display:column>
		<display:column title="CDA Amount" style="width:5%;vertical-align:middle">
		<c:if test="${tfOfficers.cdaAmount==null}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--</c:if>
		<c:if test="${tfOfficers.cdaAmount!=''}">${tfOfficers.cdaAmount}</c:if>
		</display:column>
		<% i++; %>
	</display:table>
	<table width="100%" bgcolor="#DDD" style="border: 1px solid #014495;">
		<tr>
			<td width="30%"  style="text-align: center;">Total SanctionedAmount</td>
			<td width="9%" style="text-align: right;"><input type="text" size="7" style="text-align: right;"  readonly="readonly"/></td>
			<td width="5%" style="text-align: right;"><input type="text" size="7" style="text-align: right;" value="${sessionScope.officersTotal}" readonly="readonly"/></td>
			<td width="58%"></td>
		</tr>
	</table>
	</div>
	
	<c:if test="${tutionFee.claimType=='2'}">
    <fieldset id="saveOfficerTuitionTeleFinance"><legend><strong><font color='green'>Print Or Save</font></strong></legend>
    <div class="line">
			<div class="quarter leftmar">Sanction No<span style="color:red">*</span></div>
            <input name="sanctionNo" id="sanctionNo" onkeypress="javascript:return checkInt(event);"/>
	</div> 
	<div class="line">
			<div class="quarter leftmar">Bill No<span style="color:red">*</span></div>
            <input name="billNo" id="billNo" onkeypress="javascript:return checkInt(event);"/>
	</div> 
	<div class="line">
		   <div class="quarter leftmar">Accounts Officer<span style="color:red">*</span></div>
		   <select name="accOfficer" id="accOfficer" cssStyle="width:145px;">
		   <option value="select">Select</option>
		   <c:forEach var="acc" items="${sessionScope.accountOfficerList}">
		   <option value="${acc.sfid}">${acc.name}</option>
		   </c:forEach>
		   </select>
	</div>
	<div class="line">
		   <div class="quarter leftmar">CFA Officer<span style="color:red">*</span></div>
		   <select name="cfaOfficer" id="cfaOfficer" cssStyle="width:145px;">
		   <option value="select">Select</option>
		   <c:forEach var="cfa" items="${sessionScope.cfaOfficerList}">
		   <option value="${cfa.sfid}">${cfa.name}</option>
		   </c:forEach>
		   </select>
	</div>
	<div class="line">
	       <div class="expbutton" style="float:right"><a href="javascript:saveClaimFinanceDetails('officers');"><span>Save</span></a>
           </div>
    </div>
	<c:if test="${tutionFee.claimType=='2'}">
	<div class="line">
	   	<div class="expbutton" style="float:right"><a href="javascript:printTelephoneBillSanctionedIndividualReport1('pdf','officers');"><span>CDA-Print2</span></a>
         	</div>
         	<div class="expbutton" style="float:right"><a href="javascript:printAllTelephoneBillRelatedDocuments1('pdf','officers');"><span>CDA-Print1</span></a>
        	 </div>
	</div>   
	<%-- reports in doc --%>
	<div class="line">
	   	<div class="expbutton" style="float:right"><a href="javascript:printTelephoneBillSanctionedIndividualReport1('excel','officers');"><span>CDA-Print2 In EXCEL</span></a>
         	</div>
         	<div class="expbutton" style="float:right"><a href="javascript:printAllTelephoneBillRelatedDocuments1('excel','officers');"><span>CDA-Print1 In EXCEL</span></a>
        	 </div>
	 </div> 
	 </c:if> 																		    
	</fieldset>
	</c:if>
	<c:if test="${tutionFee.claimType=='1'}">
	 <fieldset id="saveOfficerTuitionTeleFinance"><legend><strong><font color='green'>Print Or Save</font></strong></legend>
	<div class="line">
			<div class="quarter leftmar">Sanction No<span style="color:red">*</span></div>
            <input name="sanctionNo" id="sanctionNo" onkeypress="javascript:return checkInt(event);"/>
	</div> 
	<div class="line">
			<div class="quarter leftmar">Bill No<span style="color:red">*</span></div>
            <input name="billNo" id="billNo" onkeypress="javascript:return checkInt(event);"/>
	</div> 
	<div class="line">
		   <div class="quarter leftmar">Accounts Officer<span style="color:red">*</span></div>
		   <select name="accOfficer" id="accOfficer" cssStyle="width:145px;">
		   <option value="select">Select</option>
		   <c:forEach var="acc" items="${sessionScope.accountOfficerList}">
		   <option value="${acc.sfid}">${acc.name}</option>
		   </c:forEach>
		   </select>
	</div>
	<div class="line">
		   <div class="quarter leftmar">CFA Officer<span style="color:red">*</span></div>
		   <select name="cfaOfficer" id="cfaOfficer" cssStyle="width:145px;">
		   <option value="select">Select</option>
		   <c:forEach var="cfa" items="${sessionScope.cfaOfficerList}">
		   <option value="${cfa.sfid}">${cfa.name}</option>
		   </c:forEach>
		   </select>
	</div>
	<div class="line">
	       <div class="expbutton" style="float:right"><a href="javascript:saveClaimFinanceDetails('officers');"><span>Save</span></a>
           </div>
    </div>
	<div class="line">
           <div class="expbutton" style="float:right"><a href="javascript:printAllTuitionFeeRelatedDocuments1('pdf');"><span>SANCTION AND BILL REPORT</span></a>
           </div>
           
           <div class="expbutton" style="float:right"><a href="javascript:printTuitionFeeSanctionedIndividualReport1('pdf');"><span>SY.BILL REPORT</span></a>
           </div>
	</div>
	<%-- reports in doc --%>
	<div class="line">
	    <div class="expbutton" style="float:right"><a href="javascript:printAllTuitionFeeRelatedDocuments1('doc');"><span>SANCTION AND BILL REPORT IN DOC</span></a>
           </div>
           
           <div class="expbutton" style="float:right"><a href="javascript:printTuitionFeeSanctionedIndividualReport1('doc');"><span>SY.BILL REPORT IN DOC</span></a>
           </div>
	</div>
	</fieldset>
	</c:if>
	</div>
	<div id="fragment-2" class="ui-tabs-panel">
	<div id="dataTable" class="line">
     <%int j=0; %>
	 <display:table name="${tutionFee.staffList}" excludedParams="*"
		export="false" class="list" requestURI="" id="tfStaff" pagesize="1000"
		sort="list" cellpadding="2" cellspacing="1">
		<display:column  style="width:3%;vertical-align:middle" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'row\');"/>'>
			<input type="checkbox" value="${tfStaff.pk}" class="row" name="row" id="encash<%=j %>" onclick="checkBoxCheckForTuitionFee(this.name);"/>
		</display:column>
		<display:column title="Req ID" style="width:2%;vertical-align:middle">&nbsp;${tfStaff.requestID}</display:column>
		<display:column title="SFID" style="width:6%;vertical-align:middle">&nbsp;${tfStaff.sfID}</display:column>
		<display:column title="Employee" style="width:18%;vertical-align:middle">&nbsp;${tfStaff.empName}</display:column>
		<display:column title="Claimed Amount" style="width:5%;vertical-align:middle">&nbsp;<input type="text" size="7" style="text-align: right;" value="${tfStaff.claimedAmount}" name="claimedAmount" id="claimedAmount" readonly="readonly"/></display:column>
		<display:column title="Sanctioned Amount" style="width:5%;vertical-align:middle">&nbsp;<input type="text" size="7" style="text-align: right;" value="${tfStaff.sanctionedAmount}" name="sanctionedAmount" id="sanctionedAmount" readonly="readonly"/></display:column>
		<display:column title="Sanction No<span class='mandatory'>*</span>" style="width:7%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${tfStaff.sanctionNo}" name="sanctionNo" id="sanctionNoEncash<%=i %>"  readonly="readonly"/></display:column>
		<display:column title="Bill No<span class='mandatory'>*</span>" style="width:7%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${tfStaff.billNo}" name="billNo" id="billNoEncash<%=i %>"  readonly="readonly"/></display:column>
		<display:column title="Acc Officer.<span class='mandatory'>*</span>" style="width:10%;vertical-align:middle">&nbsp;<input type="text" size="12" value="${tfStaff.accOfficer}" name="accOfficer" id="accOfficer<%=i %>" readonly="readonly"/></display:column>
        <display:column title="CFA Officer.<span class='mandatory'>*</span>" style="width:10%;vertical-align:middle">&nbsp;<input type="text" size="12" value="${tfOfficers.cfaOfficer}" name="cfaOfficer" id="cfaOfficer<%=i %>" readonly="readonly"/></display:column>
        <display:column title="DV.No" style="width:5%;align:middle">
		<c:if test="${tfStaff.dvNo==null}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--</c:if>
		<c:if test="${tfStaff.dvNo!=''}">${tfStaff.dvNo}</c:if>
		</display:column>
		<display:column title="DV.Date" style="width:5%;vertical-align:middle">
		<c:if test="${tfStaff.dvDate==null}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--</c:if>
		<c:if test="${tfStaff.dvDate!=''}"><fmt:formatDate value="${tfStaff.dvDate}" pattern="dd-MMM-yyyy"/></c:if>
		</display:column>
		<display:column title="CDA Amount" style="width:5%;vertical-align:middle">
		<c:if test="${tfStaff.cdaAmount==null}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--</c:if>
		<c:if test="${tfStaff.cdaAmount!=''}">${tfOfficers.cdaAmount}</c:if>
		</display:column>
		<%--<display:column title="Report" style="width:6%;vertical-align:middle">&nbsp;<a href="javascript:getInitialReport('${encashment.id}','encash');">Report</a></display:column>--%>
		<% j++; %>
	</display:table>
	<table width="100%" bgcolor="#DDD" style="border: 1px solid #014495;">
		<tr>
			<td width="18%"  style="text-align: center;">Total SanctionedAmount</td>
			<td width="11%" style="text-align: right;"><input type="text" size="18" style="text-align: right;"  readonly="readonly"/></td>
			<td width="13%" style="text-align: right;"><input type="text" size="18" style="text-align: right;" value="${sessionScope.staffTotal}" readonly="readonly"/></td>
			<td width="55%"></td>
			
		
		</tr>
	</table>
	</div>
	<c:if test="${tutionFee.claimType=='2'}">
     <fieldset id="saveStaffTuitionTeleFinance"><legend><strong ><font color='green'>Print Or Save</font></strong></legend>
	<div class="line">
			<div class="quarter leftmar">Sanction No<span style="color:red">*</span></div>
            <input name="sanctionNo" id="sanctionNo" onkeypress="javascript:return checkInt(event);"/>
	</div> 
	<div class="line">
			<div class="quarter leftmar">Bill No<span style="color:red">*</span></div>
            <input name="billNo" id="billNo" onkeypress="javascript:return checkInt(event);"/>
	</div> 
	<div class="line">
		   <div class="quarter leftmar">Accounts Officer<span style="color:red">*</span></div>
		   <select name="accOfficer" id="accOfficer" cssStyle="width:145px;">
		   <option value="select">Select</option>
		   <c:forEach var="acc" items="${sessionScope.accountOfficerList}">
		   <option value="${acc.sfid}">${acc.name}</option>
		   </c:forEach>
		   </select>
	</div>
	<div class="line">
		   <div class="quarter leftmar">CFA Officer<span style="color:red">*</span></div>
		   <select name="cfaOfficer" id="cfaOfficer" cssStyle="width:145px;">
		   <option value="select">Select</option>
		   <c:forEach var="cfa" items="${sessionScope.cfaOfficerList}">
		   <option value="${cfa.sfid}">${cfa.name}</option>
		   </c:forEach>
		   </select>
	</div>
	<div class="line">
	      <div class="expbutton" style="float:right"><a href="javascript:saveClaimFinanceDetails('staff');"><span>Save</span></a>
           </div>
    </div>
	<c:if test="${tutionFee.claimType=='2'}">
	<div class="line">
	   <div class="expbutton" style="float:right"><a href="javascript:printTelephoneBillSanctionedIndividualReport1('pdf','staff');"><span>CDA-Print2</span></a>
         	</div>
         	<div class="expbutton" style="float:right"><a href="javascript:printAllTelephoneBillRelatedDocuments1('pdf','staff');"><span>CDA-Print1</span></a>
        	 </div>
	 </div>
	<%-- reports in excel --%>
	<div class="line">
	    	<div class="expbutton" style="float:right"><a href="javascript:printTelephoneBillSanctionedIndividualReport1('excel','staff');"><span>CDA-Print2 In EXCEL</span></a>
         	</div>
         	<div class="expbutton" style="float:right"><a href="javascript:printAllTelephoneBillRelatedDocuments1('excel','staff');"><span>CDA-Print1 In EXCEL</span></a>
        	 </div>
	</div>  
	</c:if>
	</fieldset>
	</c:if>
	<c:if test="${tutionFee.claimType=='1'}">
	<fieldset id="saveStaffTuitionTeleFinance"><legend><strong ><font color='green'>Print Or Save</font></strong></legend>
	<div class="line">
			<div class="quarter leftmar">Sanction No<span style="color:red">*</span></div>
            <input name="sanctionNo" id="sanctionNo" onkeypress="javascript:return checkInt(event);"/>
	</div> 
	<div class="line">
			<div class="quarter leftmar">Bill No<span style="color:red">*</span></div>
            <input name="billNo" id="billNo" onkeypress="javascript:return checkInt(event);"/>
	</div> 
	<div class="line">
		   <div class="quarter leftmar">Accounts Officer<span style="color:red">*</span></div>
		   <select name="accOfficer" id="accOfficer" cssStyle="width:145px;">
		   <option value="select">Select</option>
		   <c:forEach var="acc" items="${sessionScope.accountOfficerList}">
		   <option value="${acc.sfid}">${acc.name}</option>
		   </c:forEach>
		   </select>
	</div>
	<div class="line">
		   <div class="quarter leftmar">CFA Officer<span style="color:red">*</span></div>
		   <select name="cfaOfficer" id="cfaOfficer" cssStyle="width:145px;">
		   <option value="select">Select</option>
		   <c:forEach var="cfa" items="${sessionScope.cfaOfficerList}">
		   <option value="${cfa.sfid}">${cfa.name}</option>
		   </c:forEach>
		   </select>
	</div>
	<div class="line">
	      <div class="expbutton" style="float:right"><a href="javascript:saveClaimFinanceDetails('staff');"><span>Save</span></a>
           </div>
    </div>
	<div class="line">
           <div class="expbutton" style="float:right"><a href="javascript:printAllTuitionFeeRelatedDocuments1('pdf');"><span>SANCTION AND BILL REPORT</span></a>
           </div>
           
           <div class="expbutton" style="float:right"><a href="javascript:printTuitionFeeSanctionedIndividualReport1('pdf');"><span>SY.BILL REPORT</span></a>
           </div>
	</div>
	<%-- reports in excel --%>
	<div class="line">
	  <div class="expbutton" style="float:right"><a href="javascript:printAllTuitionFeeRelatedDocuments1('doc');"><span>SANCTION AND BILL REPORT IN DOC</span></a>
           </div>
           
           <div class="expbutton" style="float:right"><a href="javascript:printTuitionFeeSanctionedIndividualReport1('doc');"><span>SY.BILL REPORT IN DOC</span></a>
           </div>
	</div>
	</fieldset>
	</c:if>
   	     </div>
		    </div>
		  </div>
    </div>	
 <script>
	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})	
</script>   
    <!-- End : ClaimFinanceDetailsHome.jsp -->