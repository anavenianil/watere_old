<!-- Begin : RequestResult.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div>
	<c:if test="${message=='failure' && remarks eq ''}"> <span class="failure"><spring:message code="failure"/></span></c:if>
	<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
	<c:if test="${message=='update'}"> <span class="update"><spring:message code="update"/></span></c:if>
	<c:if test="${message=='delete'}"> <span class="delete"><spring:message code="delete"/></span></c:if>
	<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="alreadyApplied"/></span></c:if>
	<c:if test="${message=='duplicateConvert'}"> <span class="failure"><spring:message code="alreadyApplied"/></span></c:if>
	<c:if test="${message=='cancelLeave'}"> <span class="failure"><spring:message code="cancelLeave"/></span></c:if>
	<c:if test="${message=='completeSuccess'}"> <span class="success"><spring:message code="completeSuccess"/></span></c:if>
	<c:if test="${message=='notEnoughAmount'}"> <span class="failure"><spring:message code="voucher.notEnoughAmount"/></span></c:if>
	<c:if test="${message=='empNotExists'}"> <span class="success"><spring:message code="empNotExists"/></span></c:if>
	<c:if test="${message=='AlreadyApplied'}"> <span class="failure"><spring:message code="AlreadyApplied"/></span></c:if>
	<c:if test="${message=='duplicateDoPart'}"> <span class="failure"><spring:message code="duplicateDoPart"/></span></c:if>
	<c:if test="${remarks ne '' && message ne 'success' && message ne 'Request Delegated Successfully'}"><div class="myStyle failure">${remarks}</div></c:if>
	<c:if test="${message=='noDRDOJoinDate'}"> <span class="failure"><spring:message code="noDRDOJoinDate"/></span></c:if>
	<c:if test="${message=='ltcApplied'}"> <span class="failure"><spring:message code="ltcApplied"/></span></c:if>
	<c:if test="${remarks ne '' && message eq 'encashFail'}"><div class="myStyle failure">${remarks}</div></c:if>
	<c:if test="${remarks ne '' && message eq 'requestFail'}"><div class="myStyle failure">${remarks}</div></c:if>
	<c:if test="${message=='TD request submitted successfully'}"><span class="success">${message}</span></c:if>
	<c:if test="${message=='TD Advance Request submitted successfully'}"><span class="success">${message}</span></c:if>
	<c:if test="${message=='TD Settlement Request submitted successfully'}"><span class="success">${message}</span></c:if>
	<c:if test="${message=='TD Reimbursement Request submitted successfully'}"><span class="success">${message}</span></c:if>
	<c:if test="${message=='Request Delegated Successfully'}"><span class="success">${message}</span></c:if>
	<script>
requestIDLTC = '<%= session.getAttribute("LtcApprovalRequest") != null ? session.getAttribute("LtcApprovalRequest") : "" %>';
</script>
<script>
requestIDLTCReimb= '<%= session.getAttribute("reimbursRequestID") != null ? session.getAttribute("reimbursRequestID") : "" %>';
</script>
	
	<script>
requestID = '<%= session.getAttribute("RequestIdwork") != null ? session.getAttribute("RequestIdwork") : "" %>';
</script>
<script>
requestIDCGHs = '<%= session.getAttribute("CghsRequestID") != null ? session.getAttribute("CghsRequestID") : "" %>';
</script>

<!--  <script>
requestIDNONCGHs = '<%= session.getAttribute("NONCghsRequestId") != null ? session.getAttribute("NONCghsRequestId") : "" %>';
</script>-->
<script>
requestIDltcA = '<%= session.getAttribute("ltcAprovalRequestID") != null ? session.getAttribute("ltcAprovalRequestID") : "" %>';
</script>

<script>
requestIDleaveA = '<%= session.getAttribute("leaveAprovalRequestID") != null ? session.getAttribute("leaveAprovalRequestID") : "" %>';
</script>
<script>
copyrequestIDleaveA = '<%= session.getAttribute("leaveAprovalcopyRequestID") != null ? session.getAttribute("leaveAprovalcopyRequestID") : "" %>';
</script>

<script>
requestIDtadaA = '<%= session.getAttribute("tadaAprovalRequestID") != null ? session.getAttribute("tadaAprovalRequestID") : "" %>';
</script>
<script>
requestIDAddr = '<%= session.getAttribute("changeRequestID") != null ? session.getAttribute("changeRequestID") : "" %>';
</script>
<script>
requestIDpis = '<%= session.getAttribute("pisrequestID") != null ? session.getAttribute("pisrequestID") : "" %>';
</script>
<script>
paymentDetails = '<%= session.getAttribute("PaymentDetailsNotFound") != null ? session.getAttribute("PaymentDetailsNotFound") : "" %>';
</script>


</div>
<!-- End : RequestResult.jsp -->
