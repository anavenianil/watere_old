<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : PayEmpLoanDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fieldset>
	<div class="line">
		<div class="quarter">SFID:<b> ${quarterRequest.employeeDetails.userSfid}</b></div>
		<div class="quarter">NAME: <b>${quarterRequest.employeeDetails.nameInServiceBook}</b></div>
		<div class="quarter">DESIGNATION:<b> ${quarterRequest.employeeDetails.designationDetails.name}</b></div>
	</div>
</fieldset>
<fieldset><legend><strong><font color='green'>Employee Quarter Details</font></strong></legend>
		<div class="line">
			<div class="quarter bold">SFID<span class="mandatory">*</span></div>
			<div class="quarter">
			<spring:bind path="quarterRequest"><form:input path="quarterRequest.sfID1" id="sfID" readonly="true" size="0.2%"/></spring:bind>
			</div>
		</div>
		<div class="line">
			<div class="quarter bold">Quarter Type<span class="mandatory">*</span></div>
			<div class="quarter">
				<spring:bind path="quarterRequest">
				  <form:select path="quarterRequest.quarterTypeId"  id="quartersType" maxlength="30" cssClass="formSelect" onchange="javascript:getQuarterSubTypeList();">
				    <form:option value="select">Select</form:option>
				    <form:options items="${quarterRequest.quarterTypeList}" itemLabel="name" itemValue="id"></form:options>
				  </form:select>
				</spring:bind>
			</div>
		</div>
		<div class="line">
			<div class="quarter bold">Quarter Sub Type<span class="mandatory">*</span></div>
			<div class="quarter">
				<spring:bind path="quarterRequest">
				  <form:select path="quarterRequest.quarterSubTypeId"  id="quarterSubType" cssClass="formSelect" >
				    <form:option value="select">Select</form:option>
				  </form:select>
				</spring:bind>
			</div>
			<div class="quarter bold">Quarter No<span class="mandatory">*</span></div>
			<div class="quarter"><spring:bind path="quarterRequest"><form:input path="quarterRequest.quarterNo" id="quarterNo" maxlength="30"/></spring:bind></div>
		</div>
		<div class="line">
			<div class="quarter bold">Occupied Date<span class="mandatory">*</span></div>
			<div class="quarter"><spring:bind path="quarterRequest">
				<form:input path="quarterRequest.occupiedDate" id="occupiedDt" class="dateClass" readonly="true"/></spring:bind>&nbsp;
				<img  src="./images/calendar.gif" id="occupiedDtImg" />	
				<script type="text/javascript">
					Calendar.setup({inputField :"occupiedDt",ifFormat : "%d-%b-%Y",showsTime : false,button :"occupiedDtImg",singleClick : true,step : 1});
				</script>
			</div>
			<div class="quarter bold">Vacation Date</div>
			<div class="quarter"><spring:bind path="quarterRequest">
				<form:input path="quarterRequest.vacationDate" id="vacatedDt" class="dateClass" readonly="true"/></spring:bind>&nbsp;
				<img  src="./images/calendar.gif" id="vacatDtImg" />	
				<script type="text/javascript">
					Calendar.setup({inputField :"vacatedDt",ifFormat : "%d-%b-%Y",showsTime : false,button :"vacatDtImg",singleClick : true,step : 1});
				</script>
			</div>
		</div>
		<div class="line">
			<div style="padding-left:50%">
				<div class="expbutton"><a href="javascript:saveQuarterOfflineDetails()"><span>Submit</span></a></div>
				<div class="expbutton"><a href="javascript:clearQuarterOfflineEntryDetails();"><span>Clear</span></a></div>
			</div>
		</div>
		<div class="line" id="listResult"><jsp:include page="quarterOfflineDetailsEntryList.jsp" /></div>
</fieldset>
<!-- End : PayEmpLoanDetails.jsp -->
