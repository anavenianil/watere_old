<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : PayEmpLoanDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fieldset>
                 <div class="line">
					<div class="quarter">SFID: ${payBillMaster.empID}</div>
					<div class="quarter">NAME: ${payBillMaster.nameInServiceBook}</div>
					<div class="quarter">DIVISION: ${payBillMaster.divisionName}</div>
					<div class="line">
					<div class="quarter">DESIGNATION: ${payBillMaster.designationName}</div>
					</div></div>
</fieldset>
	<fieldset><legend><strong><font color='green'>Employee Loan Details</font></strong></legend>
	
			<div class="line">
				<div class="quarter bold">SFID<span class="mandatory">*</span></div>
				<div class="quarter">
				<spring:bind path="payBillMaster">
					<form:input path="payBillMaster.empID" id="empId" size="10" readonly="true"/>
					</spring:bind>
					
				</div>
			</div>
			<div class="line">
					<div class="quarter leftmar">Start Date<span class="mandatory">*</span></div>
					
					<div class="quarter"><spring:bind path="payBillMaster"><form:input path="payBillMaster.startDate" id="startDate" cssClass="required" readonly="true"/></spring:bind>
					<img  src="./images/calendar.gif" id="date_start_trigger1" />	
					<script type="text/javascript">
						Calendar.setup({inputField :"startDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
					</script>
					</div>
					<div class="quarter leftmar">End Date<span class="mandatory">*</span></div>
					<div class="quarter"><spring:bind path="payBillMaster"><form:input path="payBillMaster.endDate" id="endDate" cssClass="required" readonly="true"/></spring:bind>
					<img  src="./images/calendar.gif" id="date_start_trigger2" />	
					<script type="text/javascript">
						Calendar.setup({inputField :"endDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger2",singleClick : true,step : 1});
					</script>
					</div>
			</div>
			<div class="line"><div class="quarter bold"><div style="margin-left:5%">
										<a href="javascript:saveEmpHindiInceDeatils();" class="appbutton">Save</a>
										<a href="javascript:clearEmpHindiInceDeatils();" class="appbutton">Clear</a>
										</div>
					<div class="quarter">
					</div>
				</div></div>
				<div class="line" id="result1"></div>
				
	</fieldset>			
<!-- End : PayEmpLoanDetails.jsp -->
