<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin:PromotionOfflineEntry.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/promotions.js"></script>

<title>Employee GradePay Details</title>

</head>
<body onload="resetOfflineEntry()">
	<form:form method="post" commandName="promotion" id="PromotionManagementBean">
		<div>
			<div class="Innermaindiv">
				<div class="header"></div>
				<div><jsp:include page="MenuLinks.jsp" /></div>					
				<div class="mainDisplay">
					<div class="whitebox1">
						<div class="whitebox_t">
							<div class="whitebox_tl">
								<div class="whitebox_tr"></div>
							</div>
						</div>
						<div class="whitebox_m1">
							<div class="lightblueBg1">
								<div class="headTitle">Employee GradePay Details</div>
								<%-- Content Page starts --%>
								<div class="line">
										<div class="line">
											<div class="quarter bold leftmar">Enter SFID<span class="mandatory">*</span></div>
											<div class="quarter">
												<input  id="SearchSfid" />
											</div>
											<div class="quarter">
												<div class="expbutton"><a href="javascript:getCategoryWiseDesignations();"><span>Search</span></a></div>
											</div>
										</div>
						<div id="empDetailsMainGrid" style="display:none;"></div>
						<div id="empGradePayGrid" style="display:none;">
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
													<form:options items="${promotion.designationList}" itemValue="id" itemLabel="name"/>
												</form:select>
											</div>
											<div class="quarter leftmar">Basic Pay</div>
											<div class="quarter">
														<form:input style="background-color: #EEF4FC; border: 1px solid #333333; color: #000000;" path="newBasicPay" id="newBasicPay" readonly="true" ></form:input>
									
											</div>
										</div>
										<div class="line">
											<div class="quarter leftmar">Grade Pay<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:input path="newGradePay" id="newGradePay" onkeypress="return checkInt(event);"></form:input>
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
										<div class="line" id="result">
										<jsp:include page="PromotionOfflineEntryList.jsp"></jsp:include>								
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
			<div><jsp:include page="Footer.jsp" /></div>			
		
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		<form:hidden path="varIncVal"/>
		</form:form>	
	</body>
</html>
<!-- End:PromotionOfflineEntry.jsp -->