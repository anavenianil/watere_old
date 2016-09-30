<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:createPreOrgnDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>


<fieldset><legend><strong><font color='green'>Pre Organisation Details</font></strong></legend>
	<div class="line">
		<div class="quarter">SFID</div>
		<div class="quarter">${changeSfid}</div>
		<div class="quarter">Name</div>
		<div class="quarter">${changeSfidName}</div>
	</div>
	<div class="line">
		<div class="quarter"><spring:message code="preOrgnDetails.orgType" /><span class="mandatory">*</span></div>
		<div class="quarter">
		<form:select path="orgType" id="orgtype"   cssClass="formSelect" >
				<form:option value="select">Select</form:option>
				<form:option value="21">Government</form:option>
				<form:option value="20">Private</form:option>
			</form:select>
		</div>
		<div class="quarter"><spring:message code="preOrgnDetails.orgName" /><span class="mandatory">*</span></div>
		<div class="quarter"><form:input path="orgName" id="orgname" maxlength="20"/></div>		
	</div>
	<div class="line">		
		<div class="quarter"><spring:message code="preOrgnDetails.fromDate" /><span class="mandatory">*</span></div>
		<div class="quarter"><form:input path="fromDate" id="fromDate" cssClass="dateClass" readonly="true"/>
		 <img  src="./images/calendar.gif"   id="date_start_trigger1" />	
						<script type="text/javascript">
			           		Calendar.setup({inputField :"fromDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
						</script>    
		</div>
		<div class="quarter"><spring:message code="preOrgnDetails.toDate" /><span class="mandatory">*</span></div>
		<div class="quarter"><form:input path="toDate" id="toDate" cssClass="dateClass" readonly="true"/>
		   <img  src="./images/calendar.gif"   id="date_start_trigger2" />	
						<script type="text/javascript">
							Calendar.setup({inputField :"toDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger2",singleClick : true,step : 1});
						</script>		
		</div>
	</div>
	<div class="line">		
		<div class="quarter"><spring:message code="preOrgnDetails.division" /></div>
		<div class="quarter"><form:input path="divisionName" maxlength="100"/></div>
		<div class="quarter"><spring:message code="preOrgnDetails.rankHeld" /></div>
		<div class="quarter"><form:input path="rankHeld" maxlength="20"/></div>
	</div>
	<div class="line">
		
		<div class="quarter"><spring:message code="preOrgnDetails.jobDescription" /></div>
		<div class="quarter"><form:input path="jobDescription" maxlength="500"/></div>
		<div class="quarter"><spring:message code="preOrgnDetails.skills" /></div> 
		<div class="quarter"><form:input path="skills" maxlength="100"/></div>
	</div>
	<div class="line">		
		<div class="quarter"><spring:message code="preOrgnDetails.pay" /></div>
		<div class="quarter"><form:input path="pay" onkeypress="return checkTwoDigitFloat(event,this.id);" maxlength="10"/></div>
		<div class="quarter"><spring:message code="preOrgnDetails.scale" /></div>
		<!-- <div class="quarter"><form:input path="scaleOfPay" id="scaleOfPay" onkeypress="return checkKeyPay(event,this.id);" maxlength="16"/></div> -->
		<div class="quarter"><form:input path="scaleOfPay" id="scaleOfPay"  maxlength="16"/></div>
	</div>
	<div class="line">
		<div class="quarter"><spring:message code="preOrgnDetails.remarks" /></div>
		<div class="quarter"><form:textarea path="remarks" id="remarks" rows="3" cols="18" onkeypress="textCounter(this,document.forms[0].counter,500);" onkeyup="textCounter(this,document.forms[0].counter,500);"/>
				<input type="text" class="counter" name="counter" value="500" id="counter" disabled="disabled"/></div>
	</div>
	<div class="float">
		<c:if test="${message ne 'getOrganisationDetails'}">
				
				<div class="appbutton" style="float:right"><a class="quarterbutton" href="javascript:clearPreOrgnDetails()">Clear</a></div>
				<c:if test="${preOrgn.param eq 'EditPreOrgn'}">
					<div id="preOrgnUpdate" class="appbutton submitbutton"><a class="quarterbutton" href="javascript:updatePreOrgnDetails();">Update</a></div>
				</c:if>
				<c:if test="${preOrgn.param ne 'EditPreOrgn'}">
					<div class="appbutton submitbutton"><a class="quarterbutton" href="javascript:managePreOrgnDetails();">Submit</a></div>
				</c:if>
				<c:if test="${preOrgn.param eq 'EditPreOrgn'}">
					<div id="preOrgnSubmit" class="appbutton submitbutton displayvalues"><a class="quarterbutton" href="javascript:managePreOrgnDetails();">Submit</a></div>
				</c:if>
		</c:if>
		
	</div>
</fieldset>
<div class="height"><hr/></div>

<!-- End:createPreOrgnDetails.jsp -->