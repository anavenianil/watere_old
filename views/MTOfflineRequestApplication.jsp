<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:MTOfflineRequestApplication.jsp  -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>


<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript"  src="script/calendar.js"></script>
<script type="text/javascript"  src="script/calendar-en.js"></script>
<script type="text/javascript"  src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/report.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/mt.js"></script>
<title>Offline Vehicle Request Application</title>
</head>
<body onload="javascript:clearRequestFormDetails();">
	<form:form method="post" commandName="mtBean" id="mtBean">
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
								<div class="headTitle">Offline Vehicle Request Application</div>
								<div>
									<%-- Content Page starts --%>
								  <div class="line" id="result"></div>
	                              <div class="line">
											<div class="quarter bold">Requested For(SFID)<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="requestedFor" id="requestedFor" maxlength="100" /></div>
											<div class="quarter bold">Mobile Number<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="mobileNo" id="mobileNo" maxlength="100" onkeypress="return checkInt(event)" /></div>
								</div>	
								<div class="line">
											<div class="quarter bold">No of People<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="noOfPeople" id="noOfPeople" maxlength="100" onkeypress="return checkInt(event)" /></div>
											<div class="quarter bold">Purpose of Visit<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="purposeOfVisiting" id="purposeOfVisiting" maxlength="100" /></div>		
								</div>
								<div class="line">
											<div class="quarter bold">Travelling Date & Time<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="travellingDate" id="travellingDate" maxlength="100" readonly="true"/><img  src="./images/calendar.gif"   id="date_start_trigger1" />	
														<script type="text/javascript">
											           		Calendar.setup({inputField :"travellingDate",ifFormat : "%d-%b-%Y %H-%M ",showsTime : true,button :"date_start_trigger1",singleClick : true,step : 1});
														</script> 
											</div>
											<div class="quarter bold">Estimated Date & Time<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="estimatedDateTime" id="estimatedDateTime" maxlength="100" readonly="true"/><img  src="./images/calendar.gif"   id="date_start_trigger4" />	
														<script type="text/javascript">
											           		Calendar.setup({inputField :"estimatedDateTime",ifFormat : "%d-%b-%Y %H-%M ",showsTime : true,button :"date_start_trigger4",singleClick : true,step : 1});
														</script> 
											</div>
											
								</div>
								<div class="line">
											<div class="quarter bold">Pickup point<span class="mandatory">*</span></div>
											<div class="quarter">
													<form:select path="pickupPoint" id="pickupPoint" >
																<form:option value="0">Select</form:option>
																<form:options items="${addressList}" itemValue="addressId" itemLabel="addressName"/>
													</form:select>
											</div>
											<div class="quarter bold">Other Source<span class="mandatory"></span></div>
											<div class="quarter"><form:input path="otherSourceName" id="otherSourceName" maxlength="100" /></div>
		 										
								</div>
								<div class="line">
												<div class="quarter bold">Dropping point<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:select path="droppingPoint" id="droppingPoint">
														<form:option value="0">Select</form:option>
														<form:options items="${addressList}" itemValue="addressId" itemLabel="addressName"/>
													</form:select>
												</div>
												<div class="quarter bold">Other Destination<span class="mandatory"></span></div>
												<div class="quarter"><form:input path="otherDestiName" id="otherDestiName" maxlength="100" /></div>
								</div>	
								<div class="line">
													<div class="quarter bold">If any article to be Carry<span class="mandatory">*</span></div>
													<div class="quarter bold">
															<input type="radio" name="articleCarry" id="articleCarry1" value="1" onclick="javascript:getArticle('YES');">Yes</input> 
															<input type="radio" name="articleCarry" id="articleCarry2" value="0"  onclick="javascript:getArticle('NO');">No</input>
													</div>
								</div>	
								<div id="articlediv" style="display:none"></div>
								<div class="line">
													<div class="quarter bold">Accommodation Required<span class="mandatory">*</span></div>
													<div class="quarter bold" >
															<input type="radio" name="accommodation" id="accommodation1" value="1"  onclick="javascript:getAccomdation('YES');">Yes</input>
															<input type="radio" name="accommodation" id="accommodation2" value="0"  onclick="javascript:getAccomdation('NO');">No</input>
													</div>
								</div>	
								<div id="accomdationdiv" style="display:none"></div>
								<div class="line">
													<div class="quarter bold">Whether Vehicle Reqiured for return Journey<span class="mandatory">*</span></div>
													<div class="quarter bold">
															<input type="radio" name="vehicleReturn" id="vehicleReturn1" value="1"  onclick="javascript:getVehicleReturn('YES');">Return</input> 
															<input type="radio" name="vehicleReturn" id="vehicleReturn2" value="0"  onclick="javascript:getVehicleReturn('NO');">Drop only </input>
													</div>
								</div>	
								<div id="vehicleReturndiv" style="display:none">
									
												
												<div class="line">
													<div class="quarter bold">Same Vehicle Reqiured <span class="mandatory">*</span></div>
													<div class="quarter bold">
														<input type="radio" name="vehicleReturnSame" id="vehicleReturnSame1" value="1" >Yes</input>
														<input type="radio" name="vehicleReturnSame" id="vehicleReturnSame2" value="0" >No </input>
													</div>
											   </div>
												<div class="line">	
													<div class="quarter bold" >No. of People<span class="mandatory">*</span></div>
													<div class="quarter"><input name="returnPeople" id="returnPeople" onkeypress="return checkInt(event)"/></div>
												</div>
												<div class="line">
													<div class="quarter bold" >Travelling Date & Time<span class="mandatory">*</span></div>
													<div class="quarter"><input name="returnDate" id="returnDate"  readonly="true" /><img  src="./images/calendar.gif" id="date_start_trigger3" />
														<script>Calendar.setup( {inputField : "returnDate",ifFormat : "%d-%b-%Y %H-%M ",showsTime : true,button : "date_start_trigger3",singleClick : true,step : 1});</script>
												    </div>
													<div class="quarter bold" >Estimated Date & Time<span class="mandatory">*</span></div>
													<div class="quarter"><input name="returnEstimatedDateTime" id="returnEstimatedDateTime"  readonly="true" /><img  src="./images/calendar.gif" id="date_start_trigger2" />
														<script>Calendar.setup( {inputField : "returnEstimatedDateTime",ifFormat : "%d-%b-%Y %H-%M ",showsTime : true,button : "date_start_trigger2",singleClick : true,step : 1});</script>
												    </div>
											  </div>
											 <div class="line">
													<div class="quarter bold">Pickup point<span class="mandatory">*</span></div>
													<div class="quarter">
															<form:select path="returnPickUpPoint" id="returnPickUpPoint" >
																		<form:option value="0">Select</form:option>
																		<form:options items="${addressList}" itemValue="addressId" itemLabel="addressName"/>
															</form:select>
													</div>
													<div class="quarter bold">Other Source<span class="mandatory"></span></div>
													<div class="quarter"><form:input path="returnOtherSourceName" id="returnOtherSourceName" maxlength="100" /></div>
				 										
											</div>
											<div class="line">
														<div class="quarter bold">Dropping point<span class="mandatory">*</span></div>
														<div class="quarter">
															<form:select path="returnDroppingPoint" id="returnDroppingPoint">
																<form:option value="0">Select</form:option>
																<form:options items="${addressList}" itemValue="addressId" itemLabel="addressName"/>
															</form:select>
														</div>
														<div class="quarter bold">Other Destination<span class="mandatory"></span></div>
														<div class="quarter"><form:input path="returnOtherDestiName" id="returnOtherDestiName" maxlength="100" /></div>
											</div>	
											<div class="line">
												<div class="quarter bold">If any article to be Carry<span class="mandatory">*</span></div>
												<div class="quarter bold">
													<input type="radio" name="returnArticleCarry" id="returnArticleCarry1" value="1" onclick="javascript:getReturnArticle('YES');" />Yes 
													<input type="radio" name="returnArticleCarry" id="returnArticleCarry2" value="0" onclick="javascript:getReturnArticle('NO');" />No 
												</div>
										</div>
										<div id="returnarticlediv" style="display:none"></div>
								
								</div>
								<div class="line" style="margin-left: 25%;">
												<div class="expbutton"><a onclick="javascript:saveRequestFormDetails('Offline');"> <span>Send Request</span></a></div>
												<div class="expbutton"><a onclick="javascript:clearRequestFormDetails();"> <span>Clear</span></a></div>
								</div>	
								<div id="result" class="line"></div>
								<%-- Content Page end --%>
								</div>
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
			<div><jsp:include page="Footer.jsp" /></div>			
		</div>
		<form:hidden path="param" id="param"/>
		<form:hidden path="type" id="type"/>
		<form:hidden path="pk" id="pk"/>
		<form:hidden path="articleJson" id="articleJson"/>
		</form:form>
	</body>
</html>
<!-- End:MTOfflineRequestApplication.jsp  -->