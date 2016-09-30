<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin : CreateTraining.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/jquery.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
	<c:if test="${message eq 'viewTraining'}">
	<div class="headTitle">Training Details</div>
	</c:if>
	<fieldset><legend><strong><font color='green'>Training Details</font></strong></legend>
	
	<div>
		<div class="line">
			<div class="quarter">SFID</div>
			<div class="quarter">${training.sfid}</div>
			
		</div>
		<div class="line">
			<div class="quarter">Course<span class="failure">*</span></div>
			<div class="quarter">
				<spring:bind path="training"><form:input path="training.course" id="course" maxlength="250"/></spring:bind>
			</div>
			<div class="quarter">Subject</div>
			<div class="quarter"><spring:bind path="training"><form:input path="training.subject" id="subject" maxlength="250"/></spring:bind></div>	 
			
		</div>
		<div class="line">
			<div class="quarter">Training Area</div>
			<div class="quarter"><spring:bind path="training"><form:select path="training.area"  id="area" cssClass="formSelect" onmouseover="setSelectWidth('#area')">
					<form:option value="0">Select</form:option>
					<form:option value="INDIA">India</form:option>
					<form:option value="ABROAD">Abroad</form:option>
				</form:select>
				</spring:bind>
			</div>
			<div class="quarter">Organisation</div>
			<div class="quarter"><spring:bind path="training"><form:input path="training.institute" id="institute" maxlength="300"/></spring:bind></div>
		</div>
		<div class="line">
				<div class="quarter">Year Of Completion</div>
				<div class="quarter"><spring:bind path="training"><form:select path="training.year"  id="year" cssClass="formSelect" onmouseover="setSelectWidth('#year')">
										<form:option value="0">Select</form:option>
										<form:options items="${yearList}" itemValue="id" itemLabel="name"/>									
									</form:select></spring:bind></div>
				<div class="quarter">Duration <span class="failure">(In months)</span></div>
				<div class="quarter"><spring:bind path="training"><form:input path="training.duration" id="duration" maxlength="100" cssClass="formSelect"/></spring:bind></div>
		</div>
		<div class="line">
				<div class="quarter">From Date</div>
				<div class="quarter">
					<spring:bind path="training"><form:input path="training.fromDate" id="fromDate" cssClass="dateClass" readonly="true"/></spring:bind>
					<img  src="./images/calendar.gif"   id="date_start_trigger1" />	
					<script type="text/javascript">
					Calendar.setup({inputField :"fromDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
					</script>
				</div>
				<div class="quarter">To Date</div>
				<div class="quarter">
					<spring:bind path="training"><form:input path="training.toDate" id="toDate" cssClass="dateClass" readonly="true"/></spring:bind>
					<img  src="./images/calendar.gif"   id="date_start_trigger2" />	
					<script type="text/javascript">
					Calendar.setup({inputField :"toDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger2",singleClick : true,step : 1});
					</script>
				</div>
		</div>
		<c:if test="${message ne 'viewTraining'}">
			<div>
				<div class="appbutton" style="float:right"><a href="javascript:resetTraining();" style="text-decoration: none">Clear</a></div>
				<div class="appbutton" style="float:right"><a href="javascript:manageTraining();" style="text-decoration: none">Submit</a></div>
			</div>
		</c:if>
			
			<div class="height"><hr/></div>
			
		</div>
		
	
	
	
	</fieldset>
	<div class="height"><hr/></div>
	<!-- End : CreateTraining.jsp -->