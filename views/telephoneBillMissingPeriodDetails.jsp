<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : telephoneBillMissingPeriodDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>

	<c:if test="${telephone.missingPeriodFrom ne null && telephone.missingPeriodTo ne null}">
	<fieldset><legend><strong><font color='red'>Missing TelephoneBill Period Details</font></strong></legend>
	<div class="line" id="missingTelephoneClaimsDetails">
	<div class="quarter leftmar">Missing Period FromDate<span class="failure">&nbsp;*</span></div>
										<div class="quarter">
											<input name="missingPeriodFrom" id="missingPeriodFrom" cssClass="dateClass" readonly="true" value="${telephone.missingPeriodFrom}" disabled="disabled"/>
											<img  src="./images/calendar.gif" id="fromDateImg" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"missingPeriodFrom",ifFormat : "%d-%b-%Y",showsTime : false,button :"missingPeriodFromImg",singleClick : true,step : 1});
											</script>
									
										</div>				
									 <div class="quarter leftmar">Missing Period ToDate<span class="failure">&nbsp;*</span></div>
										<div class="quarter"> 
											<input name="missingPeriodTo" id="missingPeriodTo" cssClass="dateClass" readonly="true" value="${telephone.missingPeriodTo}" disabled="disabled"/>
											<img  src="./images/calendar.gif" id="toDateImg" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"missingPeriodTo",ifFormat : "%d-%b-%Y",showsTime : false,button :"missingPeriodToImg",singleClick : true,step : 1});
											</script>
										</div><br/><br/>
									<div class="line">
										           <div class="quarter leftmar" style="margin-left: 8px;"><b>Reason For Not Claiming The TelephoneBill For Above Missing Period</b><span class='failure'>*</span></div>
													<div class="quarter"><textarea name="missingClaimRemarks" id="missingClaimRemarks" cols="20" rows="4" onkeypress="textCounter(this,document.forms[0].counter,500);" onkeyup="textCounter(this,document.forms[0].counter,500);"></textarea>
													<input type="text" name="counter" id="counter" value="500" class="counter" disabled="disabled"/>
													</div>
									</div>
   </div>
 </fieldset>
 </c:if>
<!-- End : telephoneBillMissingPeriodDetails.jsp-->
