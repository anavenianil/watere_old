<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:createEmpExperience.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<fieldset><legend><strong><font color='green'>Employee Experience Details</font></strong></legend>
	<div class="line">
		<div class="quarter">SFID</div>
		<div class="quarter">${changeSfid}</div>
		<div class="quarter">Name</div>
		<div class="quarter">${changeSfidName}</div>
	</div>
	<div class="line">
		<div class="quarter"><spring:message code="empExp.experience" /><span class="mandatory">*</span></div>
		<div class="quarter"><form:input path="experience" id="experience" /></div>
		<!-- <div class="quarter"><form:input path="experience" id="experience" onkeypress="return checkChar(event);"/></div> -->
		<div class="quarter"><spring:message code="empExp.fromDate" /><span class="mandatory">*</span></div>
		<div class="quarter"><form:input path="fromDate" id="fromdate" cssClass="dateClass" readonly="true"/>
			<img  src="./images/calendar.gif"   id="date_start_trigger3" />	
						<script type="text/javascript">
							Calendar.setup({inputField :"fromdate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger3",singleClick : true,step : 1});
						</script>
		</div>
	</div>
	<div class="line">
		<div class="quarter"><spring:message code="empExp.toDate" /><span class="mandatory">*</span></div>
		<div class="quarter"><form:input path="toDate" id="todate" cssClass="dateClass" readonly="true"/>
			<img  src="./images/calendar.gif"   id="date_start_trigger4" />	
						<script type="text/javascript">
							Calendar.setup({inputField :"todate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger4",singleClick : true,step : 1});
						</script>
		</div>
		<div class="quarter"><spring:message code="empExp.description" /></div>
		<div class="quarter">
			
				<form:textarea path="description" rows="3" cols="18" onkeypress="textCounter(this,document.forms[0].counter,500);" onkeyup="textCounter(this,document.forms[0].counter,500);"></form:textarea>	
			<input type="text" class="counter" name="counter" value="500" id="counter" disabled="disabled"/>
		</div>
	</div>
	<spring:bind path="empExp">
	<div class="float">
					<div class="appbutton" style="float:right"><a class="quarterbutton" href="javascript:clearEmpExperience()">Clear</a></div>
		<c:if test="${message ne 'getExperienceDetails'}">
			<c:if test="${empExp.param eq 'EditExperience'}">
				<div id="empExpUpdate" class="appbutton submitbutton"><a class="quarterbutton" href="javascript:updateExperience()">Submit</a></div>
			</c:if>
			<c:if test="${empExp.param ne 'EditExperience'}">	
				<div class="appbutton submitbutton"><a class="quarterbutton" href="javascript:manageEmpExperience()">Submit</a></div>	
			</c:if>
			<c:if test="${empExp.param eq 'EditExperience'}">	
				<div id="empExpSubmit" class="appbutton submitbutton displayvalues"><a class="quarterbutton" href="javascript:manageEmpExperience()">Submit</a></div>	
			</c:if>
		</c:if>
	</div>
	</spring:bind>
</fieldset>
<div class="height"><hr/></div>
<!-- End:createEmpExperience.jsp -->