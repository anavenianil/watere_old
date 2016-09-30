<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TuitionFeeFinanceDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/tabs.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.min.js"></script>
<script type="text/javascript" src="script/jquery.ui.custom.min.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/tutionFee.js"></script>
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
<title>Tuition Fee Reimbursement Details</title>
</head>

<body>
	<form:form method="post" commandName="tutionFee">
		<div>
			<div class="Innermaindiv">
				<div class="header"></div>
				<div><jsp:include page="MenuLinks.jsp" /></div>					
				<div class="mainDisplay">
					<div class="whitebox1">
						<div class="whitebox_t">
							<div class="whitebox_tl">
								<div class="whitebox_tr"></div>
							</div>
						</div>
						<div class="whitebox_m1">
							<div class="lightblueBg1">
								<div class="headTitle"> Finance Details</div>
								<div class="line">
								<div id="result"></div>
									<%-- Content Page starts --%>
	      <div class="line">
	      <div class="quarter bold">Claim Type<span style="color:red">*</span></div>
				<form:select path="claimType" id="claimType" cssStyle="width:145px;" onchange="javascript:checkGridForfinanceDetails()">
				<form:option value="select">select</form:option>
			    <form:options items="${sessionScope.claimTypeMasterList}" itemLabel="claimType" itemValue="id"/>
               </form:select>
	      </div>
	<div class="line" id="tutionFeeDiv" style="display:none">
	     <div id="page-wrap">		
			 <div id="tabs">
				 <ul>        		
	        		<li><a href="#fragment-1">Officers</a></li>
	        		<li><a href="#fragment-2">Staff</a></li>
	        	</ul>
		     <div id="fragment-1" class="ui-tabs-panel">
		     
		     <div id="dataTable" class="line">
     <%int i=0; %>
	 <display:table name="${tutionFee.officersList}" excludedParams="*"
		export="false" class="list" requestURI="" id="tfOfficers" pagesize="1000"
		sort="list" cellpadding="2" cellspacing="1">
		<display:column  style="width:3%;vertical-align:middle" title='<input type=checkbox  name="selectall " id="selectall1" onclick="checkBoxCheckAll(this.name,\'row\');"/>'>
			<input type="checkbox" value="${tfOfficers.pk}" class="row" name="row" id="encash<%=i %>" onclick="checkBoxCheck(this.name);"/>
		</display:column>
		<display:column title="Req ID" style="width:2%;vertical-align:middle">&nbsp;${tfOfficers.requestID}</display:column>
		<display:column title="SFID" style="width:6%;vertical-align:middle">&nbsp;${tfOfficers.sfID}</display:column>
		<display:column title="Employee" style="width:8%;vertical-align:middle">&nbsp;${tfOfficers.empName}</display:column>
		<display:column title="Claimed Amount" style="width:5%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${tfOfficers.claimedAmount}" name="claimedAmount" id="claimedAmount" readonly="readonly"/></display:column>
		<display:column title="Sanctioned Amount" style="width:5%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${tfOfficers.sanctionedAmount}" name="sanctionedAmount" id="sanctionedAmount" readonly="readonly"/></display:column>
		<display:column title="Sanction No<span class='mandatory'>*</span>" style="width:7%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${tfOfficers.sanctionNo}" name="sanctionNo" id="sanctionNoEncash<%=i %>" onkeypress="javascript:return checkInt(event);"/></display:column>
		<display:column title="Bill No<span class='mandatory'>*</span>" style="width:7%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${tfOfficers.billNo}" name="billNo" id="billNoEncash<%=i %>" onkeypress="javascript:return checkInt(event);"/></display:column>
		<display:column title="DV.No" style="width:7%;align:middle">
		<c:if test="${tfOfficers.dvNo==null}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--</c:if>
		<c:if test="${tfOfficers.dvNo!=''}">${tfOfficers.dvNo}</c:if>
		</display:column>
		<display:column title="DV.Date" style="width:10%;vertical-align:middle">
		<c:if test="${tfOfficers.dvDate==null}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--</c:if>
		<c:if test="${tfOfficers.dvDate!=''}"><fmt:formatDate value="${tfOfficers.dvDate}" pattern="dd-MMM-yyyy"/></c:if>
		</display:column>
		<display:column title="CDA Amount" style="width:7%;vertical-align:middle">
		<c:if test="${tfOfficers.cdaAmount==null}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--</c:if>
		<c:if test="${tfOfficers.cdaAmount!=''}">${tfOfficers.cdaAmount}</c:if>
		</display:column>
		<% i++; %>
	</display:table>
	</div>
	<div class="line">
           <div class="expbutton" style="float:right"><a href="javascript:saveClaimFinanceDetails('TF','officers');"><span>Save</span></a>
           </div>
    </div>
   	     </div>
		     <div id="fragment-2" class="ui-tabs-panel">
		     
		     <div id="dataTable" class="line">
     <%int j=0; %>
	 <display:table name="${tutionFee.staffList}" excludedParams="*"
		export="false" class="list" requestURI="" id="tfStaff" pagesize="1000"
		sort="list" cellpadding="2" cellspacing="1">
		<display:column  style="width:3%;vertical-align:middle" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'row\');"/>'>
			<input type="checkbox" value="${tfStaff.pk}" class="row" name="row" id="encash<%=j %>" onclick="checkBoxCheck(this.name);"/>
		</display:column>
		<display:column title="Req ID" style="width:2%;vertical-align:middle">&nbsp;${tfStaff.requestID}</display:column>
		<display:column title="SFID" style="width:6%;vertical-align:middle">&nbsp;${tfStaff.sfID}</display:column>
		<display:column title="Employee" style="width:8%;vertical-align:middle">&nbsp;${tfStaff.empName}</display:column>
		<display:column title="Claimed Amount" style="width:5%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${tfStaff.claimedAmount}" name="claimedAmount" id="claimedAmount" readonly="readonly"/></display:column>
		<display:column title="Sanctioned Amount" style="width:5%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${tfStaff.sanctionedAmount}" name="sanctionedAmount" id="sanctionedAmount" readonly="readonly"/></display:column>
		<display:column title="Sanction No<span class='mandatory'>*</span>" style="width:7%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${tfStaff.sanctionNo}" name="sanctionNo" id="sanctionNoEncash<%=i %>" onkeypress="javascript:return checkInt(event);"/></display:column>
		<display:column title="Bill No<span class='mandatory'>*</span>" style="width:7%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${tfStaff.billNo}" name="billNo" id="billNoEncash<%=i %>" onkeypress="javascript:return checkInt(event);"/></display:column>
		<display:column title="DV.No" style="width:7%;vertical-align:middle">${tfStaff.dvNo}</display:column>
		<display:column title="DV.Date" style="width:10%;vertical-align:middle"><fmt:formatDate value="${tfStaff.dvDate}" pattern="dd-MMM-yyyy"/>
		</display:column>
		<display:column title="CDA Amount" style="width:7%;vertical-align:middle">${tfStaff.cdaAmount}</display:column>
		<display:column title="Report" style="width:6%;vertical-align:middle">&nbsp;<a href="javascript:getInitialReport('${encashment.id}','encash');">Report</a></display:column>
		<% j++; %>
	</display:table>
	</div>
	<div class="line">
           <div class="expbutton" style="float:right"><a href="javascript:saveClaimFinanceDetails('TF','staff');"><span>Save</span></a>
           </div>
    </div>
		     
		     </div>
		    </div>
		  </div>
    </div>		
   
				<%-- Content Page end --%>
								</div>
								<div>&nbsp;</div>
							</div>
						</div>
						<div class="whitebox_b_non">
							<div class="whitebox_bl">
								<div class="whitebox_br"></div>
							</div>
						</div>
					</div>
				</div>
				
			</div>
			<div><jsp:include page="Footer.jsp" /></div>			
		</div>
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		</form:form>
	</body>
</html>
<!-- End:TuitionFeeFinanceDetails.jsp -->
