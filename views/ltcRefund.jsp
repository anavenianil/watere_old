<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:ltcRefund.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/ltc.js"></script>
<title>LTC Cancellation Request</title>

</head>
<body >
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
								<div class="headTitle">LTC Cancellation Request</div>
								<%-- Content Page starts --%>
								<div class="line">
								<div id="result"></div>
									<div class="line">
										<!-- <div class="quarter bold">&nbsp;Internal phone number</div>
										<div class="quarter">:&nbsp;${ltcApprovalRequest.empBean.internalNo }</div> -->
										<div class="quarter bold">&nbsp;Block year</div>
										<div class="quarter">:&nbsp;${ltcApprovalRequest.ltcBlockYear}</div>
										<div class="quarter bold">&nbsp;LTC type</div>
										<div class="quarter">:&nbsp;${ltcApprovalRequest.ltcTypeId }</div>
									</div>
									<div class="line">
										<div class="quarter bold">&nbsp;Place of visit/home town</div>
										<div class="quarter">:&nbsp;${ltcApprovalRequest.placeOfVisit}</div>
										<div class="quarter bold">&nbsp;Applied on</div>
										<div class="quarter">:&nbsp;${ltcApprovalRequest.creationDate}</div>
									</div>
									<div class="line">
										<div class="quarter bold">&nbsp;DO part date</div>
										<div class="quarter">:&nbsp;${ltcApprovalRequest.doPartDate}</div>
										<div class="quarter bold">&nbsp;DO part no</div>
										<div class="quarter">:&nbsp;${ltcApprovalRequest.doPartNo}</div>
									</div>
									<div class="line">
									<c:if test="${not empty ltcApprovalRequest.leaveDetails}">
										<div class="quarter bold">&nbsp;Leave Details</div>
										<div class="quarter">:&nbsp;${ltcApprovalRequest.leaveDetails}</div>
									</c:if>
									<c:if test="${not empty ltcApprovalRequest.issuedAmount}">
										<div class="quarter bold">&nbsp;CDA Issued Advance</div>
										<div class="quarter" id="issuedAmount">:&nbsp;Rs. ${ltcApprovalRequest.issuedAmount} /-</div>
									</c:if>
									</div>
									<c:if test="${not empty ltcApprovalRequest.financeIssuedAmount}">
									<div class="line">
										<div class="quarter bold">&nbsp;Finance Issued Advance</div>
										<div class="quarter" id="financeIssuedAmount">:&nbsp;Rs. ${ltcApprovalRequest.financeIssuedAmount} /-</div>
									</div>
									</c:if>
									
									<c:if test="${not empty ltcApprovalRequest.encashmentDays}">
										<div class="line">
											<div class="quarter bold">&nbsp;Whether EL encashed</div>
											<div class="quarter">:&nbsp;Yes</div>
											<div class="quarter bold">&nbsp;No of days</div>
											<div class="quarter">:&nbsp;${ltcApprovalRequest.encashmentDays}</div>
										</div>
									</c:if>
									
									<div class="line">
										<div class="quarter bold">Reason for not performing journey<span class="mandatory">*</span></div>
										<div class="quarter"><form:textarea path="remarks" rows="5" cols="25"></form:textarea></div>
										<c:if test="${not empty ltcApprovalRequest.leaveDetails}">
											<div class="quarter bold">Do you want to cancel Leave also<span class="mandatory">*</span></div>
											<div class="quarter"><form:radiobutton path="leaveCancelFlag" id="leaveCancelFlag1"  label="Yes" value="Y"/>
											<form:radiobutton path="leaveCancelFlag" id="leaveCancelFlag2"  label="No" value="N"/></div>
										</c:if>
										
									</div>
							    <div class="line" id="individualNote" ><br/>
		                        <div style="colore:red"><font color='red'><u></>Note for Individual :</u></font></div>
		                        <div><font color='purple'>1)Please submit letter to admin stating the reason for your cancellation to admin so that admin will process the Leave cancellation based<br/> on the Online Request and letter.</font></div> 
	                            </div>
									
									<div class="line">
										<div class="appbutton" style="margin-left:90%;"><a  onclick="javascript:submitRefundDetails('${ltcApprovalRequest.id}','${ltcApprovalRequest.historyID}','${ltcApprovalRequest.type}','${ltcApprovalRequest.leaveRequestId}','${ltcApprovalRequest.typeValue}')" class="quarterbutton">Submit</a></div>
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
		</div>		
			<form:hidden path="param"/>
			<form:hidden path="type"/>
			<form:hidden path="cancleType"/>
			<form:hidden path="requestIDs"/>
		</form:form>
		
	</body>
</html>
<!-- End:ltcRefund.jsp -->