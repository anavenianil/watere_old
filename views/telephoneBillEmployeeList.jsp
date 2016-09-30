<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : telephoneBillEmployeeList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	
		                            	<div class="line">
											<div class="leftmar">
												<div style="float: left; width: 38%;">De Selected</div>
													<div class="half" >Selected Desig</div>
											</div>
									   </div>
									<div class="line">
											<div class="leftmar">
												<div style="float: left; width : 28%">
													<spring:bind path="telephone">
													<form:select path="telephone.fromID" id="SelectLeft" size="10" multiple="true" cssStyle="width:250px">
													<form:options items="${sessionScope.telephoneBillEmpNotSelectedList}" itemLabel="name" itemValue="value"/>
													</form:select>
													</spring:bind>
												</div>
												<div style="float: left; width : 10%; margin-top: 60px;">									
														<div style="margin-bottom: 5px;" align="center">
													          <input style="margin-bottom: 5px"id="MoveRight" type="button" value=" Add " />
      											              <input id="MoveLeft" type="button" value=" Remove " />    											        
      											        </div>		      																				
		      									</div>
												<div style="float: left; width : 35%">
													<spring:bind path="telephone">
													<form:select path="telephone.fromID" id="SelectRight" size="10" multiple="true" cssStyle="width:250px">
													<form:options items="${sessionScope.telephoneBillEmpSelectedList}" itemLabel="name" itemValue="value"/>
													</form:select>
													</spring:bind>
												</div>
											</div>
									</div>
									<div class="line">
									  <div class="quarter bold">Eligible Amount<span class="mandatory">*</span></div>
									  <div class="half">
									  <spring:bind path="telephone">
									  <form:input path="telephone.amount" id="amount" size="6" onkeyup="javascript:calculateTeleEligibleAmount();"/></spring:bind>&nbsp;+
									  <spring:bind path="telephone">
									  <form:input path="telephone.serviceTax" id="serviceTax" size="6" onkeyup="javascript:calculateTeleEligibleAmount();"/></spring:bind>&nbsp;%&nbsp;&nbsp;=
									  <spring:bind path="telephone">
									  <form:input path="telephone.totAmount" id="totAmount" size="8" readonly="true"/></spring:bind>
									  </div>
									</div>
									<div class="line">
									     <div class="quarter bold">Internet Flag<span class="mandatory">*</span></div>
									     <div class="half bold">
									          <spring:bind path="telephone"><form:radiobutton path="telephone.internetFlag" value="1" label="with internet"/></spring:bind>
									         <spring:bind path="telephone"><form:radiobutton path="telephone.internetFlag" value="0" label="without internet"/></spring:bind>
									     </div>
									</div>
				
	<script>multiSelectBox();</script>	
<!-- End : telephoneBillEligibilityList.jsp -->
