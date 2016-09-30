<!-- Begin: Result.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>

<script>
	sfidResult = '${message}';
 	levelID=<%=session.getAttribute("desigID")%>;
	familyMembersJson = <%= session.getAttribute("familyMembersJson")!=null ? (net.sf.json.JSONArray)session.getAttribute("familyMembersJson") : null %>;
	blockYearListJson = <%= session.getAttribute("blockYearListJson")!=null ? (net.sf.json.JSONArray)session.getAttribute("blockYearListJson") : null %>;
	ltcExtBlockYear ='<%= session.getAttribute("ltcExtBlockYear")!=null ? session.getAttribute("ltcExtBlockYear"):"" %>'
	
	ename = '<%= session.getAttribute("ename") != null ? session.getAttribute("ename") : "" %>';
	<% session.removeAttribute("ename"); %>
</script>

<div>
    
	<c:if test="${message=='empPayStop' || Result=='empPayStop'}"> <span class="failure"><spring:message code="empPayStop"/></span></c:if>
	<c:if test="${message=='failure' || Result=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
	<c:if test="${message=='success' || Result=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
	<c:if test="${message=='empNotExists' || Result=='empNotExists'}"> <span class="failure"><spring:message code="empNotExists"/></span></c:if>
	<c:if test="${message=='duplicate' || Result=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
	<c:if test="${message=='validrange' || Result=='validrange'}"> <span class="failure"><spring:message code="validrange"/></span></c:if>
	<c:if test="${message=='EXISTS' || Result=='EXISTS'}"> <span class="failure"><spring:message code="detailsExist"/></span></c:if>
	<c:if test="${message=='employeeexists' || Result=='employeeexists'}"> <span class="success"><spring:message code="employeeexists"/></span></c:if>
	<c:if test="${message=='delete' || Result=='delete'}"> <span class="failure"><spring:message code="delete"/></span></c:if>
	<c:if test="${message=='update' || Result=='update'}"> <span class="success"><spring:message code="update"/></span></c:if>
	<c:if test="${message=='autoRunNotCompleted' || Result=='autoRunNotCompleted'}"> <span class="success"><spring:message code="autoRunNotCompleted"/></span></c:if>
	<c:if test="${message=='constraints' || Result=='constraints'}"><div class="failure">${remarks}</div></c:if>
	<c:if test="${message=='NA' || Result=='NA'}"><span class="failure"><spring:message code="notApplicable"/></span></c:if>
	<c:if test="${message=='Debit sum is greater than credit sum' || Result=='Debit sum is greater than credit sum'}"><span class="failure"><spring:message code="moreDebit"/></span></c:if>
	<c:if test="${message=='Employee NOT using quarters' || Result=='Employee NOT using quarters'}"><span class="failure"><spring:message code="notUsingQuarters"/></span></c:if>
	<c:if test="${message=='Mess charges are NOT applicable for staff' || Result=='Mess charges are NOT applicable for staff'}"><span class="failure"><spring:message code="notUsingMess"/></span></c:if>
	<c:if test="${message=='payInactive' || Result=='payInactive'}"><span class="failure"><spring:message code="inactive"/></span></c:if>
	<c:if test="${message=='notUpdate' || Result=='notUpdate'}"><span class="failure"><spring:message code="notUpdate"/></span></c:if>
	<c:if test="${message=='upload'}"><span class="success">${remarks}</span></c:if>
	<c:if test="${Result=='quarterRequest'}"><span class="success">Quarter Application Request Saved Successfully</span></c:if>
	<c:if test="${message=='cancel'}"><span class="success">Cancelled Process Successed</span></c:if>
	<c:if test="${message=='tutionFeeRequest'}"><span class="success">Successfully Applied For Tuition Fee</span></c:if>
	<c:if test="${message=='tutionFeeRequestFailed'}"><span class="failure">${remarks}</span></c:if>
	<c:if test="${message=='MTVehicleRequest'}"><span class="success">You Have Successfully Requested For Vehicle</span></c:if>
	<c:if test="${message=='payDetailsNotExists'}"><span class="failure">${remarks}</span></c:if>
	<c:if test="${message=='telephoneBillRequest'}"><span class="success">Successfully Applied For Telephone Bill</span></c:if>
	<c:if test="${message=='billNumber'}"><span class="success">BillNumber Not Found</span></c:if>
	<c:if test="${message == 'telephoneBillAlreadyApplied'}"><span class="success"><b style="color:red;">You have already claimed for telephonebill for the given month</span></c:if>
	<c:if test="${message=='invalid'}"><span class="failure"><spring:message code="Invalid"/></span></c:if>
	<c:if test="${message=='autoRunCompleted'}"><span class="success">Auto Run Script executed successfully for ${sessionScope.scriptDate}</span></c:if>
	<c:if test="${message=='duplicateAutoRun'}"><span class="failure">Auto Run Script has already executed for the given month / year</span></c:if>
	<c:if test="${message=='invalidAutoRun'}"><span class="failure">Leave auto run script date should be either in the month of January or July</span></c:if>
	
	<c:if test="${message=='NO' || Result=='NO'}"><span class="failure"><spring:message code="noLoan"/></span></c:if>
	<c:if test="${message=='alreadyDone' || Result=='alreadyDone'}"><span class="failure"><spring:message code="alreadyDone"/></span></c:if>

	<c:if test="${message=='journeyCancel'}"><span class="success"><spring:message code="journeyCancel"/></span></c:if>
    <c:if test="${message=='journeyCancelFail'}"><span class="success"><spring:message code="journeyCancelFail"/></span></c:if>
    <c:if test="${message=='allocateSuccess'}"><span class="success"><spring:message code="allocateSuccess"/></span></c:if>
    <c:if test="${message=='allocateFail'}"><span class="success"><spring:message code="allocateFail"/></span></c:if>
    
	<c:if test="${message == 'disabledfailure'}"><span class="failure"><spring:message code="disabledfailure"/></span></c:if>
	
	<c:if test="${message=='deletefail'}"> <span class="failure"><spring:message code="recordexists"/></span></c:if>

	<c:if test="${message == 'LTCHOMETOWNNULL'}"><span class="failure"><b style="color:red;">Please Enter/Correct The Home Town Address</b></span></c:if>

	<script>
requestIDMT = '<%= session.getAttribute("MTRequestID") != null ? session.getAttribute("MTRequestID") : "" %>';
</script>
<script>
requestIDq = '<%= session.getAttribute("quarterrequestID") != null ? session.getAttribute("quarterrequestID") : "" %>';
</script>
<script>
requestIDtel = '<%= session.getAttribute("RequestIDtel") != null ? session.getAttribute("RequestIDtel") : "" %>';
</script>
 	
 	<c:if test="${message == 'missing vacation date'}"><span class="failure"><spring:message code="vacationDateMis"/></span></c:if>
	
	<c:if test="${message == 'invalid vacation date'}"><span class="failure"><spring:message code="invalidVacationDate"/></span></c:if>

<script>
paymentDetails = '<%= session.getAttribute("PaymentDetailsNotFound") != null ? session.getAttribute("PaymentDetailsNotFound") : "" %>';
</script>
</div>
<!-- End: Result.jsp -->