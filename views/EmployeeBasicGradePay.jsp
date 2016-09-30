<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:EmployeeBasicGradePay.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/tabs.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<%-- <script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>--%>

<script type="text/javascript" src="script/jquery.min.js"></script>
<script type="text/javascript" src="script/jquery.ui.custom.min.js"></script>

<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>



<script type="text/javascript" src="script/date.js"></script>

<script type="text/javascript">
$jq(function() {
	var $tabs = $('#tabs').tabs();
	$jq(".ui-tabs-panel").each(function(i){
		var totalSize = $jq(".ui-tabs-panel").size() - 1;

		if (i != totalSize) {
			next = i + 2;
			$jq(this).append("<a href='#' class='next-tab mover' rel='" + next + "'>Next Page &#187;</a>");
			
		}
  
		if (i != 0) {
			prev = i;
			$jq(this).append("<a href='#' class='prev-tab mover' rel='" + prev + "'>&#171; Prev Page</a>");
			
		}
  		
	});

	$jq('.next-tab, .prev-tab').click(function() { 
		$tabs.tabs('select', $(this).attr("rel"));
		return false;
	});
      
});
</script>

<title>Basic Pay Grade Pay</title>
</head>
<body >
	<form:form method="post" commandName="empPayment"  id="EmployeePaymentBean">
	 <c:if test="${message eq 'invalidSFID'}">
	<legend><strong><font color="red"> ${message}</font></strong></legend>
	 
	
	 </c:if>

	 <c:if test="${message ne 'invalidSFID'}">
	 
		<div>
			<div class="Innermaindiv">
				
								
					
				<div class="mainDisplay">
					<div class="whitebox1">
						<div class="whitebox_t">
							<div class="whitebox_tl">
								<div class="whitebox_tr"></div>
							</div>
						</div>
						
						<div class="whitebox_m1">
							<div class="lightblueBg1">
								
								<%-- Content Page starts --%>
								<div class="line">
								
								<%--  <c:if test="${message eq 'invalidSFID'}">dfbg </c:if>== <c:if test="${message ne 'invalidSFID'}"> </c:if>--%>
								 
								 
									<div id="page-wrap">		
										<div id="tabs">
											<ul>        		
								        		<li><a href="#fragment-1"  onclick ="javascript:clearEmpPayment();javascript:resetOfflineEntry();" >Basic Pay</a></li>
								        		<li><a href="#fragment-2"  onclick ="javascript:clearEmpPayment();javascript:resetOfflineEntry();">Grade Pay</a></li> 
								        		<!--<li><a href="#fragment-1" >Basic Pay</a></li>
								        		<li><a href="#fragment-2" >Grade Pay</a></li>
								        			 -->
								        	</ul>
								        	<div id="result"></div>
									     	<div id="fragment-1" class="ui-tabs-panel">
											<div id="empBasicPayGrid" style="display:none;">
											<fieldset style="float:left;">
												<div class="line">
													<div class="quarter bold leftmar">SFID<span class="mandatory">*</span></div>
													<div class="quarter">
														<form:input path="sfid" id="sfid" readonly="true"  />
													</div>
													
													<div class="quarter leftmar">Designation<span class="mandatory">*</span></div>
													<div class="quarter">
														<form:select path="designationId" id="designationId" cssClass="formSelect" onchange="javascript:changeEmpPayscale();">
															<form:option value="0">Select</form:option>
															
														</form:select>
													</div>
												</div>
												
												<div class="line">
													<div class="quarter bold leftmar">Basic<span class="mandatory">*</span></div>
													<div class="quarter">
														<form:input path="basic" id="basic" maxlength="10" onkeypress="javascript:increaseTextWidth('basic');return checkFloat(event,'basic');"/>
													</div>
													<div class="quarter leftmar">Scale<span class="failure">*</span></div>
													<div class="quarter">
													<form:select path="" id="empPayScaleId"  cssClass="formSelect" disabled="true">
														<form:option value="0">Select</form:option>
														
													</form:select>
													</div>
												</div>	
												<div class="line">
													<div class="quarter bold leftmar">Increment Type<span class="mandatory">*</span></div>
													<div class="quarter">
														<form:select path="incrementType" id="incrementType" >
															<form:option value="">Select</form:option>
															<form:option value="N">Initial Entry</form:option>
															<form:option value="A">Annual Increment</form:option>
															<form:option value="I">Increment</form:option>
															<form:option value="P">Promotion</form:option>
															<form:option value="AA">AnnualIncrement Ammmend</form:option>
															
														</form:select>
													</div>
													
													<div class="quarter bold leftmar">Increment Value<span class="mandatory">*</span></div>
													<div class="quarter">
														<form:input path="incrementValue" id="incrementValue" onkeypress="return checkFloat(event,'incrementValue');"/>
													</div>
												</div>
												<div class="line">
													<div class="quarter leftmar">Effective Date<span class="mandatory">*</span></div>
																<div class="quarter">
																	<form:input path="effectiveDate" id="effectiveDate"  cssClass="dateClass" readonly="true"/>
																	<img src="./images/calendar.gif" id="effectiveDate_trigger" /> 
																	<script type="text/javascript">
										                        		Calendar.setup({inputField :"effectiveDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"effectiveDate_trigger",singleClick : true,step : 1});
									                             	</script>
																</div>
												</div>	
												<div class="line" id="empSumbitDiv" style="display: none;">
												   <div style="margin-left:24%;">
														<div class="expbutton"><a href="javascript:manageEmpPayment();"> <span>Submit</span></a></div>
													</div>
													<div class="expbutton"><a href="javascript:clearEmpPayment();"><span>Clear</span></a></div>
												</div>	
											</fieldset>
										</div>
										<div class="line" id="result11">
											<jsp:include page="EmployeePaymentEntryList.jsp" /></div>
												
											</div>
								     	
								     		<div id="fragment-2" class="ui-tabs-panel ui-tabs-hide">
													<div id="empGradePayGrid" style="display: none;">
							            <fieldset style="float:left;">
										<div class="line">
											<div class="quarter leftmar">SFID<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:input path="refSfid" id="refSfid" readonly="true"></form:input>
											</div>
										
									     <%-- <div class="half" id="result1">
			                            <jsp:include page="NameDesignation.jsp"></jsp:include>
		                                 </div>--%>
										
										</div>
										<div class="line"  id="designations">
											<div class="quarter leftmar">Designation<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:select path="designationTo" id="designationTo" onmouseover="setSelectWidth('#designationTo')" cssClass="formSelect"  onchange="javascript:getEmpGradePay();">
													<form:option value="0">Select</form:option>
													
												</form:select>
											</div>
											<div class="quarter leftmar">Basic Pay</div>
											<div class="quarter">
														<form:input style="background-color: #EEF4FC; border: 1px solid #333333; color: #000000;" path="newBasicPay" id="newBasicPay" readonly="true" disabled="true"></form:input>
									
											</div>
										</div>
										<div class="line">
											<div class="quarter leftmar">Grade Pay<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:input path="newGradePay" id="newGradePay" maxlength="6" onkeypress="return checkInt(event);"></form:input>
										    </div>
											<div class="quarter leftmar">Seniority Date<span class="mandatory">*</span></div>
						                        <div class="quarter">
						                        	<form:input path="seniorityDate" id="seniorityDate" cssClass="dateClass" readonly="true" /> 
						                        	<img src="./images/calendar.gif" id="date_start_trigger5" /> 
						                        	<script type="text/javascript">
							                        	Calendar.setup({inputField :"seniorityDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger5",singleClick : true,step : 1});
						                            </script>
		                             		</div>
										</div>
										<div class="line">
									       <div class="quarter leftmar">Effective From<span class="mandatory">*</span></div>
					                        <div class="quarter"><form:input path="promotionDate" id="promotionDate" cssClass="dateClass" readonly="true" /> <img src="./images/calendar.gif" id="date_start_trigger" /> <script type="text/javascript">
						                        Calendar.setup({inputField :"promotionDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger",singleClick : true,step : 1});
					                             </script>
					                         </div>
											<div class="quarter leftmar">Two Addl Inc.<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:input path="twoAddl" id="twoAddl" onkeypress="return checkInt(event);" />
											</div>
										</div>		
										<div class="line"  id="designations">
										<div class="quarter leftmar">Variable Increments Points<span class="mandatory">*</span></div>
										<div class="quarter">
												<form:input path="designationFrom" id="designationFrom"  onkeyup="javascript:checkSize(event);" />
					
										</div>
										<div class="quarter leftmar">Variable Increments Value<span class="mandatory">*</span></div>
										<div class="quarter">
												<form:input path="" id="valriableIncVal" readonly="true"/>
					
										</div>
												
										</div>
										<div class="line">
					                        <div class="quarter leftmar">Var Inc Start Date<span class="mandatory">*</span></div>
					                        <div class="quarter"><form:input path="presentEffectivedate" id="presentEffectivedate" cssClass="dateClass" readonly="true" /> <img src="./images/calendar.gif" id="date_start_trigger1" /> <script type="text/javascript">
						                        Calendar.setup({inputField :"presentEffectivedate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
					                             </script>
					                        </div>
				                             
				                            <div class="quarter leftmar">Var Inc. End Date<span class="mandatory">*</span></div>
														<div class="quarter">
															<form:input path="varIncEnd" id="varIncEnd"  cssClass="dateClass" readonly="true"/>
															<img src="./images/calendar.gif" id="date_start_trigger9" /> 
															<script type="text/javascript">
								                        		Calendar.setup({inputField :"varIncEnd",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger9",singleClick : true,step : 1});
							                             	</script>
												</div>
				                        
				                         </div>
			                          <div class="line" id="promoSumbitDiv" style="display: none;">
								   			<div class="quarter leftmar">&nbsp;</div>
												<div class="half">
													<div class="expbutton"><a onclick="javascript:submitOfflineEntryDetails()"> <span>Submit</span></a></div>
													<div class="expbutton"><a onclick="javascript:resetOfflineEntry()"> <span>Clear</span></a></div>
												</div>
									 </div>		
							</fieldset>
					</div>
								        
								        
								        <div class="line" id="result2">
										<jsp:include page="PromotionOfflineEntryList.jsp"></jsp:include>								
										</div>	
								        	</div>
								    	         	
								         
								       	</div>
									</div>
									
								</div>					
								<%-- Content Page end --%>
								<div>&nbsp;</div>
							</div>
						</div>
						
						<div class="whitebox_b_non">
							<div class="whitebox_bl">
								<div class="whitebox_br"></div>
							</div>
						</div>
					</div>
				</div>
				
			</div>
					
		</div>
				</c:if>
				<form:input type="hidden" path="param"/>	
		<form:hidden path="type"/>
				<form:hidden path="status" id="status"/>
		<form:hidden path="referenceType" id="referenceType"/>	
		<form:hidden path="varIncVal"/>
		<%--<form:input type="hidden" path="nodeID"/>--%>	
				
				
		</form:form>
	</body>
</html>
<!-- End:EmployeeBasicGradePay.jsp -->