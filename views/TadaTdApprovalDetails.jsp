<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TadaTdApprovalDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/prototype.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/workflow.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/tada.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<title>TD approval details</title>

</head>
<body>
	<form:form method="post" id="tada" commandName="tada">
	    
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
								<div class="headTitle">TD Approved Details</div>
								<div id="result"></div>
								<%-- Content Page starts --%>
								
								<div class="line bold">List of approved TD</div>
								<div id="historyDetails" style="display: none"></div>
							
								<div id=""></div>
								<div class="line">
								<%int i=0; %>
								<div id="Pagination">
									<display:table name="${sessionScope.tadaTdTxnDetails}" excludedParams="*"
											export="false" class="list" requestURI="" id="tadaTdTxnDetails" pagesize="5" sort="list" cellpadding="2" cellspacing="1">
										
										<display:column title="Request Id" style="width:10%;vertical-align:middle">&nbsp;
										<a href="javascript:getRequestDetails('${tadaTdTxnDetails.historyID}','${tadaTdTxnDetails.requestId}','myRequests','pending','')" id="${tadaTdTxnDetails.requestId}">${tadaTdTxnDetails.requestId}</a>
										</display:column>
										<display:column title="Request Type" style="width:10%;vertical-align:middle">&nbsp;
										
										${tadaTdTxnDetails.requestType}
										</display:column>
										<display:column title="Journey Date" style="width:10%;vertical-align:middle">&nbsp;${tadaTdTxnDetails.journeyDateOne}</display:column>
										<display:column title="Settlement" style="width:10%;vertical-align:middle">&nbsp;
										<c:if test="${(tadaTdTxnDetails.settFlag=='' || tadaTdTxnDetails.settFlag==null || tadaTdTxnDetails.settFlag=='N') && tadaTdTxnDetails.advanceFlag=='C' && (tadaTdTxnDetails.status==8 || tadaTdTxnDetails.status==2 || tadaTdTxnDetails.status==6)}">
										<c:if test="${tadaTdTxnDetails.stayDuration!=0}">
										    <a href="javascript:settlementTdApproval('${tadaTdTxnDetails.requestId}','${tadaTdTxnDetails.requestType}','${tadaTdTxnDetails.historyID}');">Settlement</a>
										</c:if>
										<c:if test="${tadaTdTxnDetails.stayDuration==0}">
										    <a href="javascript:settlementTdApproval('${tadaTdTxnDetails.requestId}','${tadaTdTxnDetails.requestType}','${tadaTdTxnDetails.historyID}');">Settlement</a>
										</c:if>
										</c:if>
										<c:if test="${tadaTdTxnDetails.advanceFlag=='Y'}">
										   <font color="red"><b>Advance amount not yet issued.</b><br/>Contact finance for further details.</font>
										</c:if>
										</display:column>
										<display:column title="Advance/Reimbursement" style="width:15%;vertical-align:middle">&nbsp;
										<c:if test="${(tadaTdTxnDetails.advanceFlag=='' || tadaTdTxnDetails.advanceFlag==null || tadaTdTxnDetails.advanceFlag=='N') && (tadaTdTxnDetails.status==8 || tadaTdTxnDetails.status==2) && (tadaTdTxnDetails.reimFlag=='' || tadaTdTxnDetails.reimFlag==null || tadaTdTxnDetails.reimFlag=='N') && (tadaTdTxnDetails.stayDuration!=0)}">   
										       <a href="javascript:advanceTdApproval('${tadaTdTxnDetails.requestId}','${tadaTdTxnDetails.requestType}','${tadaTdTxnDetails.historyID}');">Advance&nbsp;/&nbsp;</a>
										</c:if>
										<c:if test="${(tadaTdTxnDetails.reimFlag=='' || tadaTdTxnDetails.reimFlag==null || tadaTdTxnDetails.reimFlag=='N') && tadaTdTxnDetails.status==8 && (tadaTdTxnDetails.advanceFlag=='' || tadaTdTxnDetails.advanceFlag==null || tadaTdTxnDetails.advanceFlag=='N')}">    
										       <a href="javascript:reimbursementTdApproval('${tadaTdTxnDetails.requestId}','${tadaTdTxnDetails.requestType}','${tadaTdTxnDetails.historyID}');">Reimbursement</a>
										</c:if>
										<c:if test="${tadaTdTxnDetails.advanceFlag=='P'}">
										   <font color="red"><b>Advance Request is under processing.</b></font>
										</c:if>
										</display:column>
										<display:column title="Amendment" style="width:13%;vertical-align:middle">&nbsp;
										<c:if test="${tadaTdTxnDetails.status==8 && (tadaTdTxnDetails.settFlag=='' || tadaTdTxnDetails.settFlag==null || tadaTdTxnDetails.settFlag=='N') && (tadaTdTxnDetails.reimFlag=='' || tadaTdTxnDetails.reimFlag==null || tadaTdTxnDetails.reimFlag=='N') && (tadaTdTxnDetails.stayDuration!=0)}">
										    <a href="javascript:amendmentRequest('${tadaTdTxnDetails.requestId}','${tadaTdTxnDetails.requestType}','${tadaTdTxnDetails.journeyDateOne}');">Req Amendment</a>
										</c:if>
										<c:if test="${tadaTdTxnDetails.status==2}">
										   <font color="red"><b>TD Request is under processing.</b></font>
										</c:if>
										    
										</display:column>
										
										
										
										
									</display:table>
								</div>
								</div>
								<div class="line" id="individualDetails">
										<div style="colore:red"><font color='red'>Note for Individual</font></div>
										<div><font color='purple'>
										1)Amendment link will be enabled only after completion of TD Request.<br/>
										2)Settlement link will be enabled only after advanced request is completed and advanced amount is issued from finance.<br/>
										2)Reimbursement link will be enabled if advance is not taken and TD Request is completed.<br/>
										</font></div> 
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
		</form:form>
		<script>
			$jq( function(){
					$jq("#Pagination").displayTagAjax('paging');
				})
		</script>
		<script type="text/javascript">
		    tadaTdDetails = <%= (net.sf.json.JSONArray) session.getAttribute("tadaTdDetails") %>;
		    tadaTdTxnDetails = <%= (net.sf.json.JSONArray) session.getAttribute("tadaTdTxnDetails") %>;
		    tadaTdAmendmentDetails = <%= (net.sf.json.JSONArray) session.getAttribute("tadaTdAmendmentDetails") %>;
		    //showTdHyperLinks();
		</script>
		
	</body>
</html>
<!-- End:TadaTdApprovalDetails.jsp -->
