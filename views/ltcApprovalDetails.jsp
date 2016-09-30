<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:ltcApprovalDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/prototype.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/workflow.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/ltc.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>

<script>
		$jq( function(){$jq("#Pagination").displayTagAjax('paging');})		
		//var type='<c:out value='${sessionScope.ltcApproveDetailsList}'/>';	
		//var type='<c:out value='${ltcApprovalRequest.ltcApproveDetailsList}'/>';				
	</script>
<title>LTC approval details</title>

</head>
<body>
	<form:form method="post" id="LtcApplicationBean" commandName="ltcApprovalRequest">
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
								<div class="headTitle">LTC Approved Details</div>
								<%-- Content Page starts --%>
								
								<div class="line bold">List of approved LTC</div>
								<div id="historyDetails" style="display: none"></div>
								<div id="result"></div>
								<%-- <div id=""></div>--%>
								<div>
	
								<div class="line">
								<div id="Pagination">
								<%int i=0; %>
									
									<%--   
									<display:table name="${ltcApprovalRequest.ltcApproveDetailsList}" excludedParams="*"
									 --%>
									 <display:table name="${ltcApproveDetailsList}" excludedParams="*"   
											export="false" class="list" requestURI="" id="ltclist" pagesize="10" sort="list" cellpadding="2" cellspacing="1">
										<display:column title="Request Id" style="width:8%;vertical-align:middle">&nbsp;<a href="javascript:getRequestDetails('${ltclist.historyID}','${ltclist.requestId}','myRequests','pending','')">${ltclist.requestId}</a></display:column>
										<display:column title="LTC Type" style="width:8%;vertical-align:middle">&nbsp;${ltclist.ltcType}</display:column>
										<display:column title="Block Year" style="width:5%;vertical-align:middle">&nbsp;${ltclist.blockYear}</display:column>
										<display:column title="Journey Date" style="width:9%;vertical-align:middle">&nbsp;${ltclist.journeyDate}</display:column>																		
										<display:column title="Family Member" style="width:30%;vertical-align:middle">&nbsp;${ltclist.familyMemberName}</display:column>
										<display:column title="Settlement" style="width:10%;vertical-align:middle">&nbsp;
										<c:if test="${not empty ltclist.reimbursementFlag}">
										<c:if test="${empty ltclist.refundFlag}">
												<c:if test="${ltclist.doPartId ne '0' && ltclist.reimbursementFlag eq 'Sett/Refund' && ltclist.status ne 'COMPLETED' &&  ltclist.status ne 'CANCELLED' || ltclist.status eq 'DECLINED'}">
															<a href="javascript:getLtcSettlement('<c:out value="${ltclist.requestId}"/>','settlement','${ltclist.issuedAmount}','${ltclist.cdaAmount}');">Settlement</a>
														</c:if>
														<!-- <c:if test="${ltclist.doPartId ne '0' && ltclist.reimbursementFlag eq 'Sett/Refund' && ltclist.status ne 'COMPLETED' && ltclist.status ne 'CANCELLED'}">
															<a href="javascript:getLtcSettlement('<c:out value="${ltclist.requestId}"/>','settlement','${ltclist.issuedAmount}','${ltclist.cdaAmount}');">Settlement</a>
														</c:if> -->
														</c:if>
										</c:if>
										</display:column>
										<display:column title="Reimbursement / Advance" style="width:8%;vertical-align:middle">&nbsp;
										<c:if test="${not empty ltclist.reimbursementFlag}">
														<c:if test="${ltclist.doPartId ne '0' && ltclist.status ne 'CANCELLED'}">
														<c:if test="${not empty ltclist.refundFlag}">
															<a href="javascript:getLtcReimbursment('<c:out value="${ltclist.requestId}"/>','reimbursement');">Reimbursement</a>
															</c:if>
															<div style="display: none;" id="advanceDiv<%= i %>">
																/<a href="javascript:getLtcAdvance('<c:out value="${ltclist.requestId}"/>');">Advance</a>
																
															</div>
														</c:if>
										</c:if>
										</display:column>
										<display:column title="Amendment" style="width:8%;vertical-align:middle">&nbsp;<div style="display: none;" id="amendmentDiv<%= i %>">
										<c:if test="${not empty ltclist.reimbursementFlag && ltclist.status ne 'CANCELLED'}">
														<a href="javascript:amendmentRequest('${ltclist.requestId}','${ltclist.requestType}','${ltclist.issuedAmount}','${ltclist.cdaAmount}','${ltclist.journeyDate}','${ltclist.blockYear}','${ltclist.doPartId}','${ltclist.historyID}','${ltclist.issuedAmount}')">Amendment</a>
														</c:if>
													</div>
										</display:column>
										<display:column title="cancel" style="width:6%;vertical-align:middle">&nbsp;<div style="display: none;" id="cancelDiv<%= i %>">
										<c:if test="${not empty ltclist.reimbursementFlag && ltclist.status ne 'CANCELLED' && ltclist.status ne 'SANCTIONED'}">
														<a href="javascript:cancelLtcRequest('${ltclist.requestType}','${ltclist.requestId}','${ltclist.doPartId}','${ltclist.historyID}','${ltclist.issuedAmount}','${ltclist.cdaAmount}')">Cancel</a>
										</c:if>
													</div>
													<script>setVisibility('${ltclist.journeyDate}','${ltclist.currentDate}','${ltclist.doPartId}','${ltclist.amendmentRefId}','${ltclist.requestType}','${ltclist.cdaAmount}','<%= i++ %>');</script>
										</display:column>
																										
										<display:column title="Status" style="width:9%;vertical-align:middle;">&nbsp;${ltclist.status}</display:column>
									</display:table>
									
								</div>
								</div>
								<br/>
								<div class="line">
									<font color="red">Note for Individual</font><br/>
									<font color="blue">
									1)Settlement link will be enabled only after completion of Advance Request.<br/>
									2)Reimbursement link will be enabled only after completion of Approval Request.<br/>
									3)Advance link is available before one week from the date of Journey.<br/>
									4)Amendment link will be enabled only after completion of Approval Request<br/>
									</font>
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
		</div>
			<form:hidden path="param"/>
			<form:hidden path="type"/>
			<form:hidden path="id"/>
			<form:hidden path="message"/>
			<form:hidden path="historyID"/>
			<form:hidden path="requestId"/>
			<form:hidden path="ltcAmendmentBlockYear"/>
			<form:hidden path="cancelReqDoPartId"/>
			<form:hidden path="cancelReqHistoryId"/>
			<form:hidden path="cancelReqIssuedAmount"/>
			<form:hidden path="cancelReqCdaAmount"/>
		<form:hidden path="statusMsg"/>
		<form:hidden path="back"/>
		
		
			
		</form:form>
		
	</body>
	
</html>

<!-- End:ltcApprovalDetails.jsp -->