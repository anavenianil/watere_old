<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TutionFeeTelephoneClaimsAmendement.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt" %>
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
<script type="text/javascript" src="script/workflow.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script type="text/javascript" src="script/tutionFee.js"></script>
<script type="text/javascript" src="script/telephone.js"></script>
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
<title>Tuition\Telephone Approval Details</title>
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
								<div class="headTitle">Tuition\Telephone Approved Details </div>
								<div id="historyDetails" style="display: none"></div>
								<div class="line bold">List of approved Tuition\Telephone</div>
                          <%-- Content Page starts --%>
                            <div class="line">
							   <div id="page-wrap">
							    <div id="tabs" >
						              	<ul>        		
								        		<li><a href="#fragment-1">Tuition</a></li>
								        		<li><a href="#fragment-2">Telephone</a></li>
								       </ul>		
								  
								  
								     
								       
						    <div id="fragment-1" class="ui-tabs-panel ui-tabs-hide" >
								<c:if test="${tutionFee.param eq 'financeClaimsAmendementDetailsParam'}">
								  <div>&nbsp;</div>
								      <div class="line">
								       <div   class="quarter bold leftmar" style="width: 10%">Sfid<span class="failure">*</span></div>
								       <div class="quarter"><input path=tuitionFeeSfid id="tuitionFeeSfid" maxlength="50"/></div>
								       <div class="quarter bold leftmar" style="width: 10%">Year</div>
								       <div class="quarter"><input path="tuitionYear" id="tuitionYear" maxlength="50" onkeypress="return checkInt(event);"/></div>
								      <div> <a href="javascript:getTutionFeeClaimDetails();" class="appbutton" style="color: white;">Print</a></div>
								      
								      </div>
								  </c:if> 
								  <c:if test="${tutionFee.param eq 'claimsAmendementDetailsParam'}">
								  <div>&nbsp;</div>
								      <div class="line">
								       <div class="quarter bold leftmar" style="width: 10%">Year</div>
								       <div class="quarter"><input path="tuitionYear" id="tuitionYear" maxlength="50" onkeypress="return checkInt(event);"/></div>
								       <div  class="quarter bold leftmar" style="width: 50%">Print Details Of Last 10 Claims From Here :<span class="failure">*</span></div>
								       <div> <a href="javascript:getUserTutionFeeClaimDetails('${tutionFee.sfid}');" class="appbutton" style="color: white;">Print</a></div>
								      
								      </div>
								  </c:if> 
								<div class="line "id="tutionClaimsTable" >
							    <display:table name="${sessionScope.tutionFeeAppliedDetails}" excludedParams="*"
		                                       export="false" class="list" requestURI="" id="tutionClaim" pagesize="100"
		                                       sort="list" cellpadding="2" cellspacing="1">
		                        <display:column title="RequestId" style="width:15%;vertical-align:middle">&nbsp;
		                        <a href="javascript:getRequestDetails('${tutionClaim.historyID}','${tutionClaim.requestID}','myRequests','pending','')" id="${tutionClaim.requestID}"">${tutionClaim.requestID}</a>
		                        </display:column>
							    <display:column title="RequestType" style="width:15%;vertical-align:middle">${tutionClaim.requestType}</display:column>
							    <display:column title="AppliedDate" style="width:15%;vertical-align:middle"><fmt:formatDate value="${tutionClaim.creationDate}" pattern="dd-MMM-yyyy"/></display:column>
							    <display:column title="Print" style="width:15%;vertical-align:middle"><a style="color:blue" href="javascript:printTutionFeeRequestFormDetails('${tutionClaim.requestID}');">PRINT</display:column> 
							    </display:table>
			                      </div>
							</div>
						<div id="fragment-2" class="ui-tabs-panel ui-tabs-hide" >
									<c:if test="${tutionFee.param eq 'financeClaimsAmendementDetailsParam'}">
								  	  <div>&nbsp;</div>
								      <div class="line">
								       <div   class="quarter bold leftmar" style="width: 10%">Sfid<span class="failure">*</span></div>
								       <div class="quarter"><input path=teleSfid id="teleSfid" maxlength="50"/></div>
								       <div class="quarter bold leftmar" style="width: 10%">Year</div>
								       <div class="quarter"><input path="year" id="year" maxlength="50" onkeypress="return checkInt(event);"/></div>
								      <div> <a href="javascript:getTelephoneBillClaimDetails();" class="appbutton" style="color: white;">Print</a></div>
								      
								      </div>
								  </c:if> 
								  <c:if test="${tutionFee.param eq 'claimsAmendementDetailsParam'}">
								  <div>&nbsp;</div>
								      <div class="line">
								       <div class="quarter bold leftmar" style="width: 10%">Year</div>
								       <div class="quarter"><input path="year" id="year" maxlength="50" onkeypress="return checkInt(event);"/></div>
								       <div  class="quarter bold leftmar" style="width: 50%">Print Details Of Last 10 Claims From Here :<span class="failure">*</span></div>
								       <div> <a href="javascript:getUserTelephoneBillClaimDetails('${tutionFee.sfid}');" class="appbutton" style="color: white;">Print</a></div>
								      
								      </div>
								  </c:if>  
							 <div class="line" id="telephoneClaimsTable">
							    <display:table name="${sessionScope.telephoneBillAppliedDetails}" excludedParams="*"
		                                       export="false" class="list" requestURI="" id="teleClaim" pagesize="100"
		                                       sort="list" cellpadding="2" cellspacing="1">
		                         <display:column title="RequestId" style="width:15%;vertical-align:middle">&nbsp;
		                        <a href="javascript:getRequestDetails('${teleClaim.historyID}','${teleClaim.requestID}','myRequests','pending','')" id="${teleClaim.requestID}"">${teleClaim.requestID}</a>
		                        </display:column>
							    <display:column title="RequestType" style="width:15%;vertical-align:middle">${teleClaim.requestType}</display:column>
							    <display:column title="Bill-Period" style="width:15%;vertical-align:middle"><fmt:formatDate pattern="dd-MMM-yyyy" value="${teleClaim.fromDate}"/>----<fmt:formatDate pattern="dd-MMM-yyyy" value="${teleClaim.toDate}"/></display:column>  
							     <display:column title="Print" style="width:15%;vertical-align:middle"><a style="color:blue" href="javascript:printTelephoneBillRequestFormDetails('${teleClaim.requestID}');">PRINT</display:column>                 
							   </display:table>
							</div>
						</div>
							    </div>
							 </div>
						 </div>
					    <%-- Content Page end --%>
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
			<form:hidden path="param"/>
			<form:hidden path="requestType"/>
			<form:hidden path="requestId"/>
			<form:hidden path="type"/>
			<form:hidden path="message"/>
			<form:hidden path="historyID"/>
		    <form:hidden path="statusMsg"/>
		    <form:hidden path="back"/>		
		</div>
	</form:form>
	</body>
</html>
<!-- End:TutionFeeTelephoneClaimsAmendement.jsp -->