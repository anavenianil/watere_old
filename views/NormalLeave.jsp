<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:NormalLeave.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
$jq(function() {
	var $tabs = $('#tabs').tabs();
	$jq(".ui-tabs-panel").each(function(i) {
		var totalSize = $jq(".ui-tabs-panel").size() - 1;
		if (i != totalSize) {
			next = i + 2;
			$jq(this).append("<div style='margin-left: 91%;'><a href='#' class='next-tab mover' rel='" + next + "'>Next Page &#187;</a></div>");
		}
		if (i != 0) {
			prev = i;
			$jq(this).append("<div style='width: 10%;'><a href='#' class='prev-tab mover' rel='" + prev + "'>&#171; Prev Page</a></div>");
		}
	});

	$jq('.next-tab, .prev-tab').click(function() { 
		$tabs.tabs('select', $(this).attr("rel"));
		return false;
	}); 
});
$jq(document).ready(function() { onloadservices(); });
</script>
<style>
	.line {
		margin-bottom:15px;
	}
</style>
<div class="line"><div id="result1"></div></div>
<div class="line">
	<div id="page-wrap">		
		<div id="tabs">
			<ul>        		
        		<li><a href="#fragment-1" onclick="javascript:clearMessage()">General</a></li>
        		<li><a href="#fragment-2" onclick="javascript:clearMessage()">Other</a></li>
        		<li><a href="#fragment-3" onclick="javascript:clearMessage()">Rules</a></li>
        	</ul>

	     	<div id="fragment-1" class="ui-tabs-panel">
	     		<div class="line">
					<div class="quarter bold">Eligibility<span class="mandatory">*</span></div>
					<div class="threefourth">
						<spring:bind path="leave">
							<form:radiobutton path="leave.eligibilityFlag" id="eligiblityFlag1" label="Male" value="M"/>
							<form:radiobutton path="leave.eligibilityFlag" id="eligiblityFlag2" label="Female" value="F"/>
							<form:radiobutton path="leave.eligibilityFlag" id="eligiblityFlag3" label="Both" value="B"/>
						</spring:bind>
					</div>
				</div>
				<div class="line">
					<div class="quarter bold">Prior Approval<span class="mandatory">*</span></div>
					<div class="threefourth">
						<spring:bind path="leave">
							<form:radiobutton path="leave.priorApprovalFlag" id="priorApprovalFlag1" label="Required" value="Y" onclick="javascript:showPriorApprovalDetails()"/>
							<form:radiobutton path="leave.priorApprovalFlag" id="priorApprovalFlag2" label="Not Required" value="N" onclick="javascript:showPriorApprovalDetails()"/>
						</spring:bind>
					</div>
				</div>
				
				<div class="line" style="display:none" id="priorApprovalID">
					<table id="priorapporval" width="100%" cellpadding="1" cellspacing="1">
						<tr>
							<td style="width: 24%"></td><td style="width: 60%"></td><td style="width: 30%"></td>
						</tr>
						<% int priorcount = 1; %>
						<c:if test="${not empty leave.exceptionList}">
							<c:forEach var="othercreditexcep" items="${leave.exceptionList}" varStatus="i" begin="0">
								<c:if test="${othercreditexcep.exceptionTypeDetails.name eq 'PRIOR_APPR'}">
									<tr>
										<td>Exception</td>
										<td><textarea name='priorapprExc<c:out value="${i.count}"/>' cols="47" rows="3">
												<c:if test="${othercreditexcep.exceptionTypeDetails.name eq 'PRIOR_APPR'}">
													<c:out value="${othercreditexcep.description}"/>
												</c:if>
											</textarea></td>
										<td>
											<% if (priorcount == 1) { %>
												<input type="button" value="+" onclick="javascript: funCreateNewExceptionRow('priorapporval', 'priorapprExc')"/>
											<% } else { %>
												<input type="button" value="-" id='btn<%=priorcount %>' name='btn<%=priorcount %>' onclick="javascript: deleteSpecificRow(this, 'priorapporval')"/>
											<% } %>
										</td>
										<% priorcount++; %>
									</tr>
								</c:if>
							</c:forEach>
						</c:if>
					</table>
				</div>
				<div class="line">
					<div class="quarter bold">Intervening holidays counted as leave<span class="mandatory">*</span></div>
					<div class="threefourth">
						<spring:bind path="leave">
							<form:radiobutton path="leave.holidayIntFlag" id="holidayIntFlag1" label="Yes" value="Y"/>
							<form:radiobutton path="leave.holidayIntFlag" id="holidayIntFlag2" label="No" value="N"/>
						</spring:bind>
					</div>
				</div>
				<div class="line">
					<div class="quarter bold">Halfday Leave<span class="mandatory">*</span></div>
					<div class="threefourth">
						<spring:bind path="leave">
							<form:radiobutton path="leave.halfDayFlag" id="halfdayflag1" label="Yes" value="Y"/>
							<form:radiobutton path="leave.halfDayFlag" id="halfdayflag2" label="No" value="N"/>
						</spring:bind>
					</div>
				</div>
				<div class="line">
					<div class="quarter bold">Available in notice period<span class="mandatory">*</span></div>
					<div class="threefourth">
						<spring:bind path="leave">
							<form:radiobutton path="leave.noticePeriodFlag" id="noticeperiodflag1" label="Yes" value="Y"/>
							<form:radiobutton path="leave.noticePeriodFlag" id="noticeperiodflag2" label="No" value="N"/>
						</spring:bind>
					</div>
				</div>
				<div class="line">
					<div class="quarter bold">Avail during LTC<span class="mandatory">*</span></div>
					<div class="threefourth">
						<spring:bind path="leave">
							<form:radiobutton path="leave.ltcAvailFlag" id="ltcAvailFlag1" label="Yes" value="Y"/>
							<form:radiobutton path="leave.ltcAvailFlag" id="ltcAvailFlag2" label="No" value="N"/>
						</spring:bind>
					</div>
				</div>
				<div class="line">
					<div class="quarter bold">Balance Shown to User<span class="mandatory">*</span></div>
					<div class="threefourth">
						<spring:bind path="leave">
							<form:radiobutton path="leave.viewFlag" id="viewflag1" label="Yes" value="Y"/>
							<form:radiobutton path="leave.viewFlag" id="viewflag2" label="No" value="N"/>
						</spring:bind>
					</div>
				</div>
				<div class="line">
					<div class="quarter bold">Max Limit shown to user</div>
					<div class="threefourth">
						<spring:bind path="leave">
							<form:radiobutton path="leave.maxShownFlag" id="maxshownflag1" label="Yes" value="Y" onclick="javascript:lapsMandatory();"/>
							<form:radiobutton path="leave.maxShownFlag" id="maxshownflag2" label="No" value="N" onclick="javascript:lapsMandatory();"/>
							<form:radiobutton path="leave.maxShownFlag" id="maxshownflag3" label="Not Applicable" value="NA" onclick="javascript:lapsMandatory();"/>
						</spring:bind>
					</div>
				</div>
				
				<div class="line" style="display:none" id="lapsDiv">
					<div class="quarter bold">Maximum Leaves Limit (lapse)<span class="mandatory">*</span></div>
					<div class="quarter">
						<spring:bind path="leave">
							<form:input path="leave.maxLeaves" id="maxleaves" maxlength="3" onkeypress="return checkTwoDigitFloat(event, 'maxleaves');"/>
						</spring:bind>
					</div>
				</div>
				<script>
					maxLeavesLimit = $jq("#maxleaves").val();
					lapsMandatory();
				</script>
				<div class="line">
					<div class="quarter bold">Offline Leave Entry<span class="mandatory">*</span></div>
					<div class="quarter">
						<spring:bind path="leave">
							<form:radiobutton path="leave.offlineFlag" id="offlineflag1" label="Possible" value="Y"/>
							<form:radiobutton path="leave.offlineFlag" id="offlineflag2" label="Not Possible" value="N"/>
						</spring:bind>
					</div>
				</div>
				<div class="line">
					<div class="quarter bold">Available without leaves<span class="mandatory">*</span></div>
					<div class="quarter">
					<spring:bind path="leave">
						<form:radiobutton path="leave.availWOLeaves" id="availwoleaves1" label="Yes" value="Y"/>
						<form:radiobutton path="leave.availWOLeaves" id="availwoleaves2" label="No" value="N"/>
						</spring:bind>
					</div>
				</div>
				<div class="line">
					<div class="quarter bold">Direct Leave debit<span class="mandatory">*</span></div>
					<div class="threefourth">
					<spring:bind path="leave">
						<form:radiobutton path="leave.directDebitFlag" id="debitflagid1" label="Yes" value="Y" onclick="javascript:shownLeaveDedit()"/>
						<form:radiobutton path="leave.directDebitFlag" id="debitflagid2" label="No" value="N" onclick="javascript:shownLeaveDedit()"/>
						<form:radiobutton path="leave.directDebitFlag" id="debitflagid3" label="Not Applicable" value="NA" onclick="javascript:shownLeaveDedit()"/>
						</spring:bind>
					</div>
				</div>
				<div class="line" style="display:none" id="debitmapID">
					<div class="quarter bold">Leave debit from <span class="mandatory">*</span></div>
					<div class="threefourth">
						<spring:bind path="leave">
							<form:select path="leave.debitMappingID" id="debitMappingID" cssClass="formSelect" style="width: 30%;">
								<form:option value="select">Select</form:option>
								<form:options items="${sessionScope.leaveTypesList}" itemValue="id" itemLabel="leaveType"/>
							</form:select>
							<form:input path="leave.debitMappingLeaves" id="debitmappingleaves" maxlength="4" onkeypress="return checkTwoDigitFloat(event, 'debitmappingleaves');" style="width: 13%;"/>
							&nbsp;days
						</spring:bind>					
					</div>
				</div>
				<div class="line">
					<div class="quarter bold">Medical Certificate<span class="mandatory">*</span></div>
					<div class="quarter">
					<spring:bind path="leave">
						<form:radiobutton path="leave.medicalCertFlag" id="medicalcert1" label="Required" value="Y" onclick="javascript:showMedicalCertificate()"/>
						<form:radiobutton path="leave.medicalCertFlag" id="medicalcert2" label="Not Required" value="N" onclick="javascript:showMedicalCertificate()"/>
						</spring:bind>
					</div>
				</div>
				<div class="line" style="display:none" id="medicalCertID">
					<table id="medical" width="100%" cellpadding="1" cellspacing="1">
						<tr>
							<td style="width: 24%"></td><td style="width: 60%"></td><td style="width: 30%"></td>
						</tr>
						<% int medicount = 1; %>
						<c:if test="${ not empty leave.exceptionList}">
							<c:forEach var="othercreditexcep" items="${leave.exceptionList}" varStatus="i" begin="0">
								<c:if test="${othercreditexcep.exceptionTypeDetails.name eq 'MED'}">
									<tr>
										<td>Exception</td>
										<td><textarea name='mcExc<c:out value="${i.count}"/>' cols="47" rows="3">
												<c:if test="${othercreditexcep.exceptionTypeDetails.name eq 'MED'}">
													<c:out value="${othercreditexcep.description}"/>
												</c:if>
											</textarea></td>
										<td>
											<% if (medicount == 1) { %>
												<input type="button" value="+" onclick="javascript:funCreateNewExceptionRow('medical','mcExc')"/>
											<% } else { %>
												<input type="button" value="-" id='btn<%=medicount%>' name='btn<%=medicount%>' onclick="javascript:deleteSpecificRow(this,'medical')"/>
											<% } %>
										</td>
										<% medicount++; %>
									</tr>
								</c:if>
							</c:forEach>
						</c:if>
					</table>
				</div>
				<div class="line">
					<div class="quarter bold">Fitness Certificate<span class="mandatory">*</span></div>
					<div class="quarter">
						<spring:bind path="leave">
							<form:radiobutton path="leave.fitnessCertFlag" id="fitnesscert1" label="Required" value="Y" onclick="javascript:showFitnessCertificate()"/>
							<form:radiobutton path="leave.fitnessCertFlag" id="fitnesscert2" label="Not Required" value="N" onclick="javascript:showFitnessCertificate()"/>
						</spring:bind>
					</div>
				</div>
				<div class="line" style="display:none" id="fitnessCertID">
					<table id="fitness" width="100%" cellpadding="1" cellspacing="1">
						<tr>
							<td style="width: 24%"></td><td style="width: 60%"></td><td style="width: 30%"></td>
						</tr>
						<% int fitcount = 1; %>
						<c:if test="${ not empty leave.exceptionList}">
							<c:forEach var="othercreditexcep" items="${leave.exceptionList}" varStatus="i" begin="0">
								<c:if test="${othercreditexcep.exceptionTypeDetails.name eq 'FIT'}">
									<tr>
										<td>Exception</td>
										<td><textarea name='fitExc<c:out value="${i.count}"/>' cols="47" rows="3">
												<c:if test="${othercreditexcep.exceptionTypeDetails.name eq 'FIT'}">
													<c:out value="${othercreditexcep.description}"/>
												</c:if>
											</textarea></td>
										<td>
											<% if (fitcount == 1) { %>
												<input type="button" value="+" onclick="javascript:funCreateNewExceptionRow('fitness','fitExc')"/>
											<% } else { %>
												<input type="button" value="-" id='btn<%=fitcount %>' name='btn<%=fitcount %>' onclick="javascript:deleteSpecificRow(this,'fitness')"/>
											<% } %>
										</td>
										<% fitcount++; %>
									</tr>
								</c:if>
							</c:forEach>
						</c:if>
					</table>
				</div>
				
				<div class="line">
					<div class="quarter bold">Any other attachment</div>
					<div class="quarter">
						<spring:bind path="leave">
							<form:radiobutton path="leave.otherCertFlag" id="othercertflag1" label="Yes" value="Y" onclick="javascript:showotherCertificate()"/>
							<form:radiobutton path="leave.otherCertFlag" id="othercertflag2" label="No" value="N" onclick="javascript:showotherCertificate()"/>
						</spring:bind>
					</div>
				</div>
				<div class="line" id="certNameID" style="display:none">
				<div class="line" >
					<div class="quarter bold">Name of the Certificate</div>
					<div class="quarter">
						<spring:bind path="leave">
							<form:input path="leave.certificateName" id="certificatename"/>
						</spring:bind>
					</div>
				</div>
				<div class="line">
					<table id="certExcID" width="100%" cellpadding="1" cellspacing="1">
						<tr>
							<td style="width: 24%"></td><td style="width: 60%"></td><td style="width: 30%"></td>
						</tr>
					 	<% int othercount = 1; %>
						<c:if test="${ not empty leave.exceptionList}">
							<c:forEach var="othercreditexcep" items="${leave.exceptionList}" varStatus="i" begin="0">
								<c:if test="${othercreditexcep.exceptionTypeDetails.name eq 'OTHER_CERT'}">
									<tr>
										<td>Exception</td>
										<td><textarea name='certExc<c:out value="${i.count}"/>' cols="47" rows="3">
												<c:if test="${othercreditexcep.exceptionTypeDetails.name eq 'OTHER_CERT'}">
													<c:out value="${othercreditexcep.description}"/>
												</c:if>
											</textarea></td>
										<td>
										<% if (othercount == 1) { %>
												<input type="button" value="+" onclick="javascript:funCreateNewExceptionRow('certExcID','certExc')"/>
										<% } else { %>
												<input type="button" value="-" id='btn<%=othercount%>' name='btn<%=othercount%>' onclick="javascript:deleteSpecificRow(this,'certExcID')"/>
										<% } %>
										</td>
										<% othercount++; %>
									</tr>
								</c:if>
							</c:forEach>
						</c:if>
					</table>
				</div> 
				</div>
				<div class="line">
					<div class="quarter bold">Leave cannot be applied, if other leaves are in credit<span class="mandatory">*</span></div>
					<div class="quarter">
					<spring:bind path="leave">
						<form:radiobutton path="leave.otherCreditCheck" id="otherCredit1" label="Yes" value="Y" onclick="javascript:showOtherLeaveCredit()"/>
						<form:radiobutton path="leave.otherCreditCheck" id="otherCredit2" label="No" value="N" onclick="javascript:showOtherLeaveCredit()"/>
						</spring:bind>
					</div>
				</div>
				<div class="line" style="display:none" id="otherCreditID">
					<table id="otherException" width="100%" cellpadding="1" cellspacing="1">
						<tr>
							<td style="width: 24%"></td><td style="width: 60%"></td><td style="width: 30%"></td>
						</tr>
						<% int othercrcount=1; %>
						<c:if test="${ not empty leave.exceptionList}">
							<c:forEach var="othercreditexcep" items="${leave.exceptionList}" varStatus="i" begin="0">
								<c:if test="${othercreditexcep.exceptionTypeDetails.name eq 'OTHER_CREDIT'}">
									<tr>
										<td>Exception</td>
										<td><textarea name='otherExc<c:out value="${i.count}"/>' cols="47" rows="3">
												<c:if test="${othercreditexcep.exceptionTypeDetails.name eq 'OTHER_CREDIT'}">
													<c:out value="${othercreditexcep.description}"/>
												</c:if>
											</textarea></td>
										<td>
										<% if (othercrcount == 1) { %>
												<input type="button" value="+" onclick="javascript:funCreateNewExceptionRow('otherException','otherExc')"/>
										<% } else { %>
												<input type="button" value="-" id='btn<%=othercrcount%>' name='btn<%=othercrcount%>' onclick="javascript:deleteSpecificRow(this,'otherException')"/>
										<% } %>
										</td>
										<% othercrcount++; %>
									</tr>
								</c:if>
							</c:forEach>
						</c:if>
					</table>
				</div>
				<div class="line">
					<div class="quarter bold">Leave Cancellation possibility<span class="mandatory">*</span></div>
					<div class="threefourth">
						<spring:bind path="leave">
							<form:radiobutton path="leave.leaveCancellation" id="leavecancellation1" label="No" value="N"/>
							<form:radiobutton path="leave.leaveCancellation" id="leavecancellation2" label="Till service book entry" value="S"/>
							<form:radiobutton path="leave.leaveCancellation" id="leavecancellation3" label="Publish in DO Part" value="P"/>
							<form:radiobutton path="leave.leaveCancellation" id="leavecancellation4" label="30 days of completion of the relevant spell of leave" value="M"/>  
							<form:radiobutton path="leave.leaveCancellation" id="leavecancellation5" label="Any Time" value="Y"/> 
						</spring:bind>
					</div>
				</div>
				<div class="line">
					<div class="quarter bold">Leave Conversion possible<span class="mandatory">*</span></div>
					<div class="threefourth">
						<spring:bind path="leave">
							<form:radiobutton path="leave.leaveConversion" id="leaveconversion1" label="No" value="N" onclick="javascript:showLeaveConversionType()"/>
							<form:radiobutton path="leave.leaveConversion" id="leaveconversion2" label="Till service book entry" value="S" onclick="javascript:showLeaveConversionType()"/>
							<form:radiobutton path="leave.leaveConversion" id="leaveconversion3" label="Publish in DO Part" value="P" onclick="javascript:showLeaveConversionType()"/>
							<form:radiobutton path="leave.leaveConversion" id="leaveconversion4" label="30 days of completion of the relevant spell of leave" value="M" onclick="javascript:showLeaveConversionType()"/>
							<form:radiobutton path="leave.leaveConversion" id="leaveconversion5" label="Any Time" value="Y" onclick="javascript:showLeaveConversionType()"/>
						</spring:bind>
					</div>
				</div>
				<div class="line" style="display:none" id="conversionID">
					<div class="quarter bold">Leave conversion possibilities<span class="mandatory">*</span></div>
					<div class="threefourth">
						<table id="ElConversionID" width="100%" cellpadding="1" cellspacing="1">
							<tr><td width="25%"></td><td width="25%"></td><td width="40%"></td><td width="10%"></td></tr>
							<c:if test="${not empty leave.conversionList}">
								<c:forEach var="othercreditexcep" items="${leave.conversionList}" varStatus="i" begin="0">
							<tr>
								<td>1 <c:out value="${othercreditexcep.convertFromDetails.leaveType}"/>&nbsp; =</td>
								<td><input type="text" id='elconversion<c:out value="${i.count}"/>' value='<c:out value="${othercreditexcep.noOfDays}"/>' maxlength="3" onkeypress="return checkTwoDigitFloat(event,'elconversion<c:out value="${i.count}"/>');"/></td>
								<td>
									<spring:bind path="leave">
										<form:select path="leave.leaveConversionType" id='leaveConversionType${i.count}' cssClass="formSelect" style="width: 75%;">
											<form:option value="select">Select</form:option>
											<form:options items="${sessionScope.leaveTypesList}" itemValue="id" itemLabel="leaveType"/>
										</form:select>
									</spring:bind>
								</td>
								<td>
									<c:if test="${i.count eq '1'}">
										<input type="button" value="+" onclick="javascript:funCreateNewELConversionRow('ElConversionID','elconversion','leaveConversionType')"/>
									</c:if>
									<c:if test="${i.count gt '1'}">
										<input type="button" value="-" id='btn<c:out value="${i.count}"/>' name='btn<c:out value="${i.count}"/>' onclick="javascript:deleteSpecificRow(this,'ElConversionID')"/>
									</c:if>
								</td>
							</tr>
							<script>
								$jq('#leaveConversionType${i.count}').val('<c:out value="${othercreditexcep.conversionTo}"/>');
							</script>
								</c:forEach>
							</c:if>
						</table>
					</div>
				</div>
				<div class="line">
					<div class="quarter bold">Publish in DO Part II<span class="mandatory">*</span></div>
					<div class="threefourth">
					<spring:bind path="leave">
						<form:radiobutton path="leave.doPartFlag" id="dopartflag1" label="Required" value="Y"/>
						<form:radiobutton path="leave.doPartFlag" id="dopartflag2" label="Not Required" value="N"/>
						</spring:bind>
					</div>
				</div>
				<div class="line">
					<div class="quarter bold">Service book entry<span class="mandatory">*</span></div>
					<div class="threefourth">
					<spring:bind path="leave">
						<form:radiobutton path="leave.serviceBookFlag" id="servicebookflag1" label="Required" value="Y"/>
						<form:radiobutton path="leave.serviceBookFlag" id="servicebookflag2" label="Not Required" value="N"/>
						</spring:bind>
					</div>
				</div>
				<div class="line">
					<div style="margin-left: 45%;"><div class="expbutton"><a onclick="javascript:submitLeaveGeneralConfigurationDetails()"> <span>Submit</span></a></div></div>
				</div>
     		</div>
     	
     		<div id="fragment-2" class="ui-tabs-panel ui-tabs-hide">
                	<fieldset><legend>Family Details</legend>
					<div class="line">
						<div class="quarter bold">Relevance with family members<span class="mandatory">*</span></div>
						<div class="quarter">
							<spring:bind path="leave">
								<form:radiobutton path="leave.familyImpactFlag" id="familyImpactFlag1" label="Yes" value="Y" onclick="javascript:showFamilyImpactDetails()"/>
								<form:radiobutton path="leave.familyImpactFlag" id="familyImpactFlag2" label="No" value="N" onclick="javascript:showFamilyImpactDetails()"/>
							</spring:bind>
						</div>
					</div>
					<div id="familyimpactID" style="display:none">
					<div class="line">
						<div class="quarter bold">Number of surviving children<span class="mandatory">*</span></div>
						<div class="quarter">
							<spring:bind path="leave">
								<form:input path="leave.servivingChild" id="servivingChild" maxlength="2" onkeypress="return checkInt(event);"/>
							</spring:bind>
						</div>
					</div>
					<div class="line">
						<div class="quarter bold">Children age limit</div>
						<div class="quarter">
							<spring:bind path="leave">
								<form:input path="leave.childAgeLimit" id="childAgeLimit" maxlength="3" onkeypress="return checkTwoDigitFloat(event,'childAgeLimit');"/>
							</spring:bind>
						</div>
					</div>
					<div class="line">
						<div class="quarter bold">If the child is PH, then child are limit</div>
						<div class="quarter">
							<spring:bind path="leave">
								<form:input path="leave.phChildAgeLimit" id="phChildAgeLimit" maxlength="3" onkeypress="return checkTwoDigitFloat(event,'phChildAgeLimit');"/>
							</spring:bind>
						</div>
					</div>
				</div>	
					
				</fieldset>
				<fieldset><legend>LTC Leave Encashment Details</legend>
					<div class="line">
						<div class="quarter bold">Is Encashment Possible<span class="mandatory">*</span></div>
						<div class="quarter">
							<spring:bind path="leave">
								<form:radiobutton path="leave.encashmentFlag" id="encashmentFlag1" label="Yes" value="Y" onclick="javascript:showEncashmentDetails()"/>
								<form:radiobutton path="leave.encashmentFlag" id="encashmentFlag2" label="No" value="N" onclick="javascript:showEncashmentDetails()"/>
							</spring:bind>
						</div>
					</div>
					<div id="enchasmentID" style="display:none">
						<div class="line">
							<div class="quarter bold" style="width: 29%;">Maximum number of days in service</div>
							<div class="quarter">
								<spring:bind path="leave">
									<form:input path="leave.noOfDaysService" id="noOfDaysService" maxlength="4" onkeypress="return checkTwoDigitFloat(event,'noOfDaysService');"/>
								</spring:bind>
							</div>
						</div>
						<div class="line">
							<div class="quarter bold" style="width: 29%;">No of spells in a year</div>
							<div class="quarter">
								<spring:bind path="leave">
									<form:input path="leave.noOfSpellsYear" id="noOfSpellsYear" maxlength="3"onkeypress="return checkTwoDigitFloat(event,'noOfSpellsYear');"/>
								</spring:bind>
							</div>
						</div>
						<div class="line">
							<div class="quarter bold" style="width: 29%;">Min No of days encash in a single spell</div>
							<div class="quarter">
								<spring:bind path="leave">
									<form:input path="leave.minDaysInSpell" id="minDaysInSpell" maxlength="3" onkeypress="return checkTwoDigitFloat(event,'minDaysInSpell');"/>
								</spring:bind>
							</div>
						</div>
						<div class="line">
							<div class="quarter bold" style="width: 29%;">Max No of days encash in a single spell</div>
							<div class="quarter">
								<spring:bind path="leave">
									<form:input path="leave.maxDaysEncashInSPell" id="maxDaysEncashInSPell" maxlength="4" onkeypress="return checkTwoDigitFloat(event,'maxDaysEncashInSPell');"/>
								</spring:bind>
							</div>
						</div>
						<div class="line">
							<div class="quarter bold" style="width: 29%;">Min no of leaves available after encash</div>
							<div class="quarter">
								<spring:bind path="leave">
									<form:input path="leave.minDaysAfterEncash" id="minDaysAfterEncash" maxlength="3" onkeypress="return checkTwoDigitFloat(event,'minDaysAfterEncash');"/>
								</spring:bind>
							</div>
						</div>
					</div>
				</fieldset>
				
				<div class="line">
					<div style="margin-left: 45%;margin-top: 10px;"><div class="expbutton"><a onclick="javascript:submitLeaveOtherDetails()"> <span>Submit</span></a></div></div>
				</div>
        	</div>
    	         	
         	<div id="fragment-3" class="ui-tabs-panel ui-tabs-hide">
    			<div class="line">
					<div class="quarter bold">Leave credit</div>
					<div class="threefourth">
						<div id="noofDaysID">
							<spring:bind path="leave">
								<form:input path="leave.leaveCredits" id="leaveCredits" maxlength="3" onkeypress="return checkTwoDigitFloat(event,'leaveCredits');"/>
							</spring:bind>
						</div>&nbsp;
						<spring:bind path="leave">
							<form:select path="leave.leaveDurationID" id="leaveDurationID" cssClass="formSelect" onchange="javascript:showDaysEntries1()">
								<form:option value="0">Select</form:option>
								<form:options items="${sessionScope.leaveDurationList}" itemValue="id" itemLabel="name"/>
							</form:select>
							<form:input path="leave.noOfDays" id="noOfDays" maxlength="4" onkeypress="return checkTwoDigitFloat(event,'noOfDays');"/> 
						</spring:bind>
						days
					</div>
				</div>	
        		<div class="line">
					<div class="quarter bold">No of spells in entire service</div>
					<div class="threefourth">
						<spring:bind path="leave">
							<form:input path="leave.spellsInService" id="spellsInService" maxlength="3" onkeypress="return checkTwoDigitFloat(event,'spellsInService');"/>
						</spring:bind>
					</div>
				</div>				
				<div class="line">
					<div class="quarter bold">No of spells in a year</div>
					<div class="threefourth">
						<spring:bind path="leave">
							<form:input path="leave.spellsPerYear" id="spellsPerYear" maxlength="3" onkeypress="return checkTwoDigitFloat(event,'spellsPerYear');"/>
						</spring:bind>
					</div>
				</div>				
				<div class="line">
					<div class="quarter bold">Min no of days per spell</div>
					<div class="threefourth">
						<spring:bind path="leave">
							<form:input path="leave.minPerSpell" id="minPerSpell" maxlength="3" onkeypress="return checkTwoDigitFloat(event,'minPerSpell');"/>
						</spring:bind>
					</div>
				</div>	
				<div class="line" >
					<table id="minspell" width="100%" cellpadding="1" cellspacing="1">
						<tr>
							<td style="width: 24%"></td><td style="width: 60%"></td><td style="width: 30%"></td>
						</tr>
						<% int mincount = 1; %>
						<c:if test="${ not empty leave.exceptionList}">
							<c:forEach var="othercreditexcep" items="${leave.exceptionList}" varStatus="i" begin="0">
								<c:if test="${othercreditexcep.exceptionTypeDetails.name eq 'MIN_PER_SPELL'}">
									<tr>
										<td>Exception<c:out value="${i.count}"/></td>
										<td><textarea name='minExc<c:out value="${i.count}"/>' cols="45" rows="3">
												<c:if test="${othercreditexcep.exceptionTypeDetails.name eq 'MIN_PER_SPELL'}">
													<c:out value="${othercreditexcep.description}"/>
												</c:if>
											</textarea></td>
										<td>
											<% if (mincount == 1) { %>
													<input type="button" value="+" onclick="javascript:funCreateNewExceptionRow('minspell','minExc')"/>
											<% } else { %>
													<input type="button" value="-" id='btn<%=mincount%>' name='btn<%=mincount%>' onclick="javascript:deleteSpecificRow(this,'minspell')"/>
											<% } %>
										</td>
										<% mincount++; %>
									</tr>
								</c:if>
							</c:forEach>
						</c:if>
					</table>
				</div>			
				<div class="line">
					<div class="quarter bold">Max no of days per spell</div>
					<div class="threefourth">
						<spring:bind path="leave">
							<form:input path="leave.maxPerSPell" id="maxPerSPell" maxlength="4" onkeypress="return checkTwoDigitFloat(event,'maxPerSPell');"/>
						</spring:bind>
					</div>
				</div>				
				<div class="line" >
					<table id="maxspell" width="100%" cellpadding="1" cellspacing="1">
					<tr>
						<td style="width: 24%"></td><td style="width: 60%"></td><td style="width: 30%"></td>
					</tr>
					<% int maxcount = 1; %>
					<c:if test="${ not empty leave.exceptionList}">
						<c:forEach var="othercreditexcep" items="${leave.exceptionList}" varStatus="i" begin="0">
							<c:if test="${othercreditexcep.exceptionTypeDetails.name eq 'MAX_PER_SPELL'}">
								<tr>
									<td>Exception<c:out value="${i.count}"/></td>
									<td><textarea name='maxExc<c:out value="${i.count}"/>' cols="45" rows="3">
											<c:if test="${othercreditexcep.exceptionTypeDetails.name eq 'MAX_PER_SPELL'}">
												<c:out value="${othercreditexcep.description}"/>
											</c:if>
										</textarea></td>
									<td>
										<% if (maxcount == 1) { %>
											<input type="button" value="+" onclick="javascript:funCreateNewExceptionRow('maxspell','maxExc')"/>
										<% } else { %>
											<input type="button" value="-" id='btn<%=maxcount%>' name='btn<%=maxcount%>' onclick="javascript:deleteSpecificRow(this,'maxspell')"/>
										<% } %>
									</td>
									<% maxcount++; %>
								</tr>
							</c:if>
						</c:forEach>
					</c:if>
					</table>
				</div>
				<div class="line">
					<div class="quarter bold">New Employee Eligibility</div>
					<div class="threefourth">
					<spring:bind path="leave">
						<form:input path="leave.newEmpAvailLeaves" id="newEmpAvailLeaves" maxlength="5" onkeypress="return checkTwoDigitFloat(event,'newEmpAvailLeaves');"/>
					</spring:bind>&nbsp;/ month
					</div>
				</div>
				
				<div class="line">
					<div style="margin-left: 45%;"><div class="expbutton"><a onclick="javascript:submitLeaveRulesDetails()"> <span>Submit</span></a></div></div>
				</div>			
         	</div>
       	</div>
	</div>
</div>

<!-- End:NormalLeave.jsp -->