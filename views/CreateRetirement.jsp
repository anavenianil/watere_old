<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:createRetirement.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<fieldset><legend><strong><font color='green'>Retirement Details</font></strong></legend>
	<div class="line">
		<div class="line">
			<div class="quarter">SFID</div>
			<div class="quarter">${changeSfid}</div>
			<div class="quarter">Name</div>
			<div class="quarter">${changeSfidName}</div>
		</div>
		<div class="line">
				<div class="quarter">ID No</div>
				<div class="quarter">
						<form:input path="idNo" id="idNo" maxlength="30" onkeypress="javascript:return checkSpecialChar(event);"/>		
				</div>
				<div class="quarter">Retirement Type<span class="mandatory">*</span></div>
				<div class="quarter">
					<form:select path="retirementType"  id="retirementType" cssClass="formSelect" >
					 		<form:option value="Select">Select</form:option>
					 		<form:options items="${RetirementTypeList}" itemLabel="name" itemValue="id"></form:options>
					 		
					</form:select>
				</div>
		</div>
		<div class="line">
				<div class="quarter">Retirement Date</div>
				<div class="quarter">
						<form:input path="retirementDate" id="retirementDate" cssClass="dateClass" readonly="true"/>
							<img  src="./images/calendar.gif"   id="date_start_trigger1" />	
							<script type="text/javascript">
								Calendar.setup({inputField :"retirementDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
							</script>
				</div>
				<div class="quarter">Reference Number</div>
				<div class="quarter">
						<form:input path="referenceNumber" id="referenceNumber" maxlength="30" onkeypress="javascript:return checkSpecialChar(event);"/>
				</div>
		</div>
			<div class="float">
					<div style="float:right" class="appbutton"><a class="quarterbutton" href="javascript:clearRetirement();">Clear</a></div>
					<div style="float:right" class="appbutton"><a class="quarterbutton" href="javascript:manageRetirement();">Submit</a></div>
			</div>
		
	</div>
</fieldset>
<div class="height"><hr/></div>
<!-- End:createRetirement.jsp -->