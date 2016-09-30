<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:createQuarter.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<fieldset><legend><strong><font color='green'>Quarter Details</font></strong></legend>
	<div class="line">
		<div class="line">
			<div class="quarter">SFID</div>
			<div class="quarter">${changeSfid}</div>
			<div class="quarter">Name</div>
			<div class="quarter">${changeSfidName}</div>
		</div>
		<div class="line">
			<div class="quarter">Quarter Type<span class="mandatory">*</span></div>
			<div class="quarter">
					<form:select path="quarterSubType"  id="quarterSubType" cssClass="formSelect" >
				 		<form:option value="Select">Select</form:option>
				 		<form:options items="${QuarterTypeList}" itemLabel="name" itemValue="id"></form:options>
				 	</form:select>
			</div>
			<div class="quarter">Quarter Number</div>
			<div class="quarter">
				<form:input path="quarterNumber" id="quarterNumber" maxlength="30"/>
			</div>
		</div>
		<div class="line">
			<div class="quarter">Occupied Date</div>
			<div class="quarter">
				<form:input path="occupiedDate" id="occupiedDate" cssClass="dateClass" readonly="true"/>
				<img  src="./images/calendar.gif"   id="date_start_trigger1" />	
				<script type="text/javascript">
					Calendar.setup({inputField :"occupiedDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
				</script>
			</div>				
		</div>
		<div class="float">
			<div style="float:right" class="appbutton"><a class="quarterbutton" href="javascript:clearQuarter();">Clear</a></div>
			<div style="float:right" class="appbutton"><a class="quarterbutton" href="javascript:manageQuarter();">Submit</a></div>
		</div>		
	</div>
</fieldset>
<div class="height"><hr/></div>
<!-- End:createQuarter.jsp -->