<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:specialCasualLeaveDetails.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form" %>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
	$jq(document).ready(function(){onloadservices();});
</script>

<div class="line"> 
			<div class="quarter">Leave Sub-Type<span class="mandatory">&nbsp;*</span></div>
			<div class="quarter">
				<spring:bind path="leave">
					<form:select path="leave.leaveSubType" id="leaveSubType" cssClass="formSelect" onchange="javascript: getleavesubtypedetails()" onmouseover="setSelectWidth('#leaveSubType')">
						<form:option value="select">Select</form:option>
						<form:option value="new">New</form:option>
						<form:options items="${leave.leaveSubTypeList}" itemValue="id" itemLabel="name"/>
					</form:select>
				</spring:bind>
			</div>
			<c:if test="${leave.leaveSubType eq 'select'}">
				<div class="quarter"  style="display:none;" id="newleavedescID">
					<spring:bind path="leave">
	    				<form:input path="leave.specialLeaveDesc" />
	    			</spring:bind>
	    		</div>
    		</c:if>
    		<c:if test="${leave.leaveSubType ne 'select'}">
				<div class="quarter" id="newleavedescID" style="padding-left: 10%;">
					<spring:bind path="leave">
	    				<form:input path="leave.specialLeaveDesc" size="50"/>
	    			</spring:bind>
	    		</div>
    		</c:if>	
</div>
<div class="line">
			<div class="quarter">Eligibility<span class="mandatory">*</span></div>
			<div class="threefourth">
				<spring:bind path="leave">
					<form:radiobutton path="leave.eligibilityFlag" id="eligiblityFlag1" label="Male" value="M"/>
					<form:radiobutton path="leave.eligibilityFlag" id="eligiblityFlag2" label="Female" value="F"/>
					<form:radiobutton path="leave.eligibilityFlag" id="eligiblityFlag3" label="Both" value="B"/>
				</spring:bind>
			</div>
</div>
<%-- <div class="line">
			<div class="quarter">No of Spells in a Year<span class="mandatory">*</span></div>
			<div class="half">
	    		<spring:bind path="leave">
	    			<form:input path="leave.spellsPerYear" id="spellsPerYear" onkeypress="return checkTwoDigitFloat(event, 'spellsPerYear');"/>
	    		</spring:bind>
    		</div>
</div>
<div class="line">
			<div class="quarter">Min no of days per spell<span class="mandatory">*</span></div>
			<div class="half">
	    		<spring:bind path="leave">
	    			<form:input path="leave.minPerSpell" id="minPerSpell" onkeypress="return checkTwoDigitFloat(event, 'minPerSpell');"/>
	    		</spring:bind>
    		</div>
</div>	
<div class="line">
			<div class="quarter">Max no of days per spell<span class="mandatory">*</span></div>
			<div class="half">
	    		<spring:bind path="leave">
	    			<form:input path="leave.maxPerSPell" id="maxPerSPell" onkeypress="return checkTwoDigitFloat(event, 'maxPerSPell');"/>
	    		</spring:bind>
    		</div>
</div>	--%>
<div class="line">
    			<div class="quarter">
	    			<span id="noofdayslabelID">
	    				<c:if test="${leave.categoryType eq 'T1'}">Max No of Days</c:if>
	    				<c:if test="${leave.categoryType eq 'T2'}">Display Expired in days</c:if>
	    			</span><span class="mandatory">*</span>
    			</div>
    			<div class="half">
	    			<spring:bind path="leave">
	    				<form:input path="leave.noOfDays" id="noofdays" onkeypress="return checkTwoDigitFloat(event,'noofdays');"/>
	    			</spring:bind>
    			</div>
</div>

<c:if test="${leave.categoryType eq 'T1'}">
				<div class="line" id="type1ID" style="display:block">
	    			<div class="quarter">Remark for second time</div>
	    			<div class="half">
		    			<spring:bind path="leave">
		    				<form:textarea path="leave.secondTimeRemarks" cols="30" rows="3"></form:textarea>
		    			</spring:bind>
	    			</div>
    			</div>
</c:if>
<c:if test="${leave.categoryType eq 'T2'}">
    	<div class="line" id="type2ID" style="display:block">    	
	    	<div class="line">
	   			<div class="quarter">From Date<span class="mandatory">*</span></div>
	   			<div class="half">
		   			<spring:bind path="leave">
		    			<form:input path="leave.strFromDate" id="fromDate" cssClass="dateClass" readonly="true"/>
		    		</spring:bind>
					<img  src="./images/calendar.gif" id="fromDateImg" />	
					<script type="text/javascript">
						Calendar.setup({inputField :"fromDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"fromDateImg",singleClick : true,step : 1});
					</script>
				</div>
	   		</div>
	   		<div class="line">
	   			<div class="quarter">To Date<span class="mandatory">*</span></div>
	   			<div class="half">
		   			<spring:bind path="leave">
		    			<form:input path="leave.strToDate" id="toDate" cssClass="dateClass" readonly="true"/>
		    		</spring:bind>
					<img  src="./images/calendar.gif" id="toDateImg" />	
					<script type="text/javascript">
						Calendar.setup({inputField :"toDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"toDateImg",singleClick : true,step : 1});
					</script>
				</div>
	   		</div>
    	</div>
</c:if>
<%-- <div class="line">
	<div class="quarter">Intervening holidays counted as leave<span class="mandatory">*</span></div>
		<div class="threefourth">
			<spring:bind path="leave">
				<form:radiobutton path="leave.holidayIntFlag" id="holidayIntFlag1" label="Yes" value="Y" />
				<form:radiobutton path="leave.holidayIntFlag" id="holidayIntFlag2" label="No" value="N" />
			</spring:bind>	
		</div>
</div> --%>
<div class="line">
			<div class="quarter">Prior Approval<span class="mandatory">*</span></div>
			<div class="threefourth">
				<spring:bind path="leave">
					<form:radiobutton path="leave.priorApprovalFlag" id="priorApprovalFlag1" label="Required" value="Y" onclick="javascript:showPriorApprovalDetails()"/>
					<form:radiobutton path="leave.priorApprovalFlag" id="priorApprovalFlag2" label="Not Required" value="N" onclick="javascript:showPriorApprovalDetails()"/>
				</spring:bind>	
			</div>
</div>
<div class="line" style="display:none" id="priorApprovalID">
		<div class="line">
			<table id="priorapporval" width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td style="width: 24%"></td><td style="width: 60%"></td><td style="width: 30%"></td>
				</tr>
				<% int priorcount = 1; %>
				<c:if test="${ not empty leave.exceptionList}">
					<c:forEach var="othercreditexcep" items="${leave.exceptionList}" varStatus="i" begin="0">
						<c:if test="${othercreditexcep.exceptionTypeDetails.name eq 'PRIOR_APPR'}">
							<tr>
								<td>Exception</td>
								<td><textarea name='priorapprExc<c:out value="${i.count}"/>' cols="45" rows="3">
										<c:if test="${othercreditexcep.exceptionTypeDetails.name eq 'PRIOR_APPR'}">
											<c:out value="${othercreditexcep.description}"/>
										</c:if>
									</textarea></td>
								<td>
									<% if (priorcount == 1) { %>
										<input type="button" value="+" onclick="javascript:funCreateNewExceptionRow('priorapporval', 'priorapprExc')"/>
									<% } else { %>
										<input type="button" value="-" id='btn<c:out value="${i.count}"/>' name='btn<c:out value="${i.count}"/>' onclick="javascript:deleteSpecificRow(this, 'priorapporval')"/>
									<% } %>
								</td>
								<% priorcount++; %>
							</tr>
						</c:if>
					</c:forEach>
				</c:if>
			</table>
		</div>
</div>
<div class="line">
			<div class="quarter">Available in notice period<span class="mandatory">*</span></div>
			<div class="threefourth">
				<spring:bind path="leave">
					<form:radiobutton path="leave.noticePeriodFlag" id="noticePeriodFlag1" label="Yes" value="Y"/>
					<form:radiobutton path="leave.noticePeriodFlag" id="noticePeriodFlag2" label="No" value="N"/>
				</spring:bind>	
			</div>
</div>
<div class="line">
			<div class="quarter">Service book entry<span class="mandatory">*</span></div>
			<div class="threefourth">
				<spring:bind path="leave">
					<form:radiobutton path="leave.serviceBookFlag" id="serviceBookFlag1" label="Required" value="Y"/>
					<form:radiobutton path="leave.serviceBookFlag" id="serviceBookFlag2" label="Not Required" value="N"/>
				</spring:bind>	
			</div>
</div>
<div class="line">
			<div class="quarter">Publish in DO Part II<span class="mandatory">*</span></div>
			<div class="threefourth">
				<spring:bind path="leave">
					<form:radiobutton path="leave.doPartFlag" id="doPartFlag1" label="Required" value="Y"/>
					<form:radiobutton path="leave.doPartFlag" id="doPartFlag2" label="Not Required" value="N"/>
				</spring:bind>	
			</div>
</div>
<div class="line">
			<div class="quarter">Medical Certificate<span class="mandatory">*</span></div>
			<div class="quarter">
				<spring:bind path="leave">
					<form:radiobutton path="leave.medicalCertFlag" id="medicalcert1" label="Required" value="Y" onclick="javascript:showMedicalCertificate()"/>
					<form:radiobutton path="leave.medicalCertFlag" id="medicalcert2" label="Not Required" value="N" onclick="javascript:showMedicalCertificate()"/>
				</spring:bind>	
			</div>
</div>
<div class="line" style="display:none" id="medicalCertID">
		<div class="line">
			<table id="medical" width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td  style="width: 24%"></td><td  style="width: 60%"></td><td  style="width: 30%"></td>
				</tr>
				<% int medicount=1; %>
					<c:if test="${ not empty leave.exceptionList}">
						<c:forEach var="othercreditexcep" items="${leave.exceptionList}" varStatus="i" begin="0">
							<c:if test="${othercreditexcep.exceptionTypeDetails.name eq 'MED'}">
								<tr>
									<td>Exception </td>
									<td><textarea name='mcExc<c:out value="${i.count}"/>' cols="45" rows="3">
											<c:if test="${othercreditexcep.exceptionTypeDetails.name eq 'MED'}">
												<c:out value="${othercreditexcep.description}"/>
											</c:if>
										</textarea></td>
									<td>
										<% if (medicount == 1) { %>
											<input type="button" value="+" onclick="javascript:funCreateNewExceptionRow('medical','mcExc')"/>
										<% } else { %>
										<input type="button" value="-" id='btn<c:out value="${i.count}"/>' name='btn<c:out value="${i.count}"/>' onclick="javascript:deleteSpecificRow(this,'medical')"/>
										<% } %>
									</td>
									<% medicount++; %>
								</tr>
							</c:if>
						</c:forEach>
					</c:if>
			</table>
		</div>
</div>
<div class="line">
			<div class="quarter">Any other attachment required<span class="mandatory">*</span></div>
			<div class="quarter">
				<spring:bind path="leave">
					<form:radiobutton path="leave.otherCertFlag" label="Yes" value="Y" id="othercertflag1" onclick="javascript:showotherCertificate()"/>
					<form:radiobutton path="leave.otherCertFlag" label="No" value="N" id="othercertflag2" onclick="javascript:showotherCertificate()"/>
				</spring:bind>	
			</div>
</div>
<div class="line" id="certNameID" style="display:none">
		<div class="line">
			<div class="quarter">Name of the Certificate</div>
			<div class="quarter">
				<spring:bind path="leave">
					<form:input path="leave.otherFileName" id="certificatename"/>
				</spring:bind>
			</div>		
		</div>
		
		<div class="line">
			<table id="certExcID" width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td  style="width: 24%"></td><td  style="width: 60%"></td><td  style="width: 30%"></td>
				</tr>
					<% int othercount = 1; %>
					<c:if test="${ not empty leave.exceptionList}">
						<c:forEach var="othercreditexcep" items="${leave.exceptionList}" varStatus="i" begin="0">
							<c:if test="${othercreditexcep.exceptionTypeDetails.name eq 'OTHER_CERT'}">
								<tr>
									<td>Exception </td>
									<td><textarea name='certExc<c:out value="${i.count}"/>' cols="45" rows="3"><c:if test="${othercreditexcep.exceptionTypeDetails.name eq 'OTHER_CERT'}"><c:out value="${othercreditexcep.description}"/></c:if></textarea></td>
									<td>
										<% if (othercount == 1) { %>
											<input type="button" value="+" onclick="javascript:funCreateNewExceptionRow('certExcID','certExc')"/>
										<% } else { %>
											<input type="button" value="-" id='btn<c:out value="${i.count}"/>' name='btn<c:out value="${i.count}"/>' onclick="javascript:deleteSpecificRow(this,'certExcID')"/>
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
	<div style="margin-left:50%"><div class="expbutton"><a onclick="javascript:submitspclDetails()"><span>Submit</span></a></div></div>
</div>
	
<script>
	specialLeavesListJson = <%= (net.sf.json.JSONArray)session.getAttribute("leaveSubTypesList") %>;
</script>		
			
<!-- begin:specialCasualLeaveDetails.jsp -->
		