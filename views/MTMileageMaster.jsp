<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:MTMileageMaster.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/menu.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/mt.js"></script>
<title>Mileage Master</title>
</head>
<body onload="javascript:clearMileageDetails();">
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
								<div class="headTitle">Daily Mileage Entry</div>
								<div>
									<%-- Content Page starts --%>

								<div class="line">
										<div class="quarter leftmar" style="width: 24%;"><b>Date</b><span class='failure'>*</span> </div>
										<div class="quarter">
											<form:input path="todayDate" id="todayDate" readonly="true" cssClass="required" />
											<img  src="./images/calendar.gif" id="date_start_trigger1" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"todayDate",ifFormat : "%d-%b-%Y ",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
											</script>
										</div>
								</div>
								  <div class="line">
							     		<div class="quarter leftmar" >Vehicle No.<span class='failure'>*</span></div>
										<div class="quarter" >	
		                              					<form:select path="vehicleNo" id="vehicleNo"  onchange="javascript:getPreDayClosingRDE();">
														<form:option value="0">Select</form:option>
														<form:options items="${VehicleList}" itemValue="id" itemLabel="name"/>
														</form:select>								
		                                </div>
	                              </div>
								
								 <div class="line">
							     		<div class="quarter leftmar" >Nature Of Duty<span class='failure'>*</span></div>
										<div class="quarter" >	
		                              					<form:input path="natureOfDuty" id="natureOfDuty" value="OFFICIAL"/>							
		                                </div>
	                              </div>
	                               <div class="line">
							     		<div class="quarter leftmar" >From Place Including Internal Movements</div>
										<div class="quarter" >	
		                              					<form:textarea path="fromPlace" id="fromPlace"/>							
		                                </div>
		                                <div class="quarter leftmar" >To Place Including Internal Movements</div>
										<div class="quarter" >	
		                              					<form:textarea path="toPlace" id="toPlace"/>							
		                                </div>
	                              </div>
	                              <div class="line">
							     		<div class="quarter leftmar" >Starting kilometer Reading<span class='failure'>*</span></div>
										<div class="quarter" >	
		                              					<form:input path="startingMilometerRde" id="startingMilometerRde" onkeypress="return checkInt(event)" onkeyup="javascript:kilometerDiff()"/>							
		                                </div>
		                                <div class="quarter leftmar" >Closing kilometer Reading<span class='failure'>*</span></div>
										<div class="quarter" >	
		                              					<form:input path="endingMilometerRde" id="endingMilometerRde" onkeypress="return checkInt(event)" onkeyup="javascript:kilometerDiff()"/>							
		                                </div>
	                              </div>
	                               <div class="line">
							     		<div class="quarter leftmar" >Total Kilometers<span class='failure'>*</span></div>
										<div class="quarter" >	
		                              					<form:input path="totKilometers" id="totKilometers" readonly="true"/>							
		                                </div>
	                              </div>
	                               <div class="line">
							     		<div class="quarter leftmar" >P.O.L drawn<span class='failure'>*</span></div>
										<div class="quarter" >	
		                              					<input type="radio" name="fuelConsumed" id="fuel1" value="1" onclick="javascript:getPOLDiv('YES');"/>Yes
		                              					<input type="radio"  name="fuelConsumed" id="fuel2" value="0" onclick="javascript:getPOLDiv('NO');"/>No								
		                                </div>
	                              </div>
	                              <div class="line" style="display:none;" id="POLDiv">
	                               <fieldset><legend><strong><font color="green">FUEL</font></strong></legend>
	                               	<div>
							     		<div class="quarter leftmar" >Quantity(Ltrs)<span class='failure'>*</span></div>
										<div class="quarter" >	
		                              					<form:input path="fuel" id="fuel" onkeypress="return checkInt(event)"/>							
		                                </div>
		                                <div class="quarter leftmar" >Meter Reading<span class='failure'>*</span></div>
										<div class="quarter" >	
		                              					<form:input path="fuelMRD" id="fuelMRD" onkeypress="return checkInt(event)" onkeyup="javascript:getFuelKPL();"/>
		                         					
		                                </div>
		                              </div>
		                                <div class="quarter leftmar">KPL Achived</div>
		                                <div class="quarter" id="FKPL"></div>
		                           </fieldset>
		                                 
	                              </div>
	                              <div class="line">
							     		<div class="quarter leftmar" >Eng. Oil drawn<span class='failure'>*</span></div>
										<div class="quarter" >	
		                              					<input type="radio"  name="engOilConsumed" id="engineOil1" value="1" onclick="javascript:getEngOilDiv('YES');"/>Yes
		                              					<input type="radio"  name="engOilConsumed" id="engineOil2" value="0" onclick="javascript:getEngOilDiv('NO');"/>No								
		                                </div>
	                              </div>
	                              <div class="line" style="display:none;" id="EngOilDiv">
	                              <fieldset><legend><strong><font color="green">Engine Oil</font></strong></legend>
	                               	<div>
							     		<div class="quarter leftmar" >Quantity(Ltrs)<span class='failure'>*</span></div>
										<div class="quarter" >	
		                              					<form:input path="engineOil" id="engineOil" onkeypress="return checkInt(event)"/>							
		                                </div>
		                                <div class="quarter leftmar" >Meter Reading<span class='failure'>*</span></div>
										<div class="quarter" >	
		                              					<form:input path="engineOilMRD" id="engineOilMRD" onkeypress="return checkInt(event)" onchange="javascript:getEngineOilKPL();"/>							
		                                </div>
		                                </div>
		                                <div class="quarter leftmar">KPL Achived</div>
		                                <div class="quarter" id="EKPL"></div>
		                           </fieldset>
		                                 
	                              </div>
	                               <div class="line">
							     		<div class="quarter leftmar" >Coolent<span class='failure'>*</span></div>
										<div class="quarter" >	
		                              					<input type="radio"  name="coolentConsumed" id="coolent1" value="1" onclick="javascript:getCoolentDiv('YES');"/>Yes
		                              					<input type="radio"  name="coolentConsumed" id="coolent2" value="0" onclick="javascript:getCoolentDiv('NO');"/>No								
		                                </div>
	                              </div>
	                              <div class="line" style="display:none;" id="CoolentDiv">
	                               <fieldset><legend><strong><font color="green">Coolent</font></strong></legend>
	                               	<div>
							     		<div class="quarter leftmar">Quantity(Ltrs)<span class='failure'>*</span></div>
										<div class="quarter" >	
		                              					<form:input path="coolent" id="coolent" onkeypress="return checkInt(event)"/>							
		                                </div>
		                                <div class="quarter leftmar" >Meter Reading<span class='failure'>*</span></div>
										<div class="quarter" >	
		                              					<form:input path="coolentMRD" id="coolentMRD" onkeypress="return checkInt(event)" onchange="javascript:getCoolentKPL();"/>							
		                                </div>
		                                </div>
		                                <div class="quarter leftmar">KPL Achived</div>
		                                <div class="quarter" id="CKPL"></div>
		                           </fieldset>
		                                 
	                              </div>
								<div class="line" style="margin-left: 25%;">
												<div class="expbutton"><a onclick="javascript:saveMileageDetails();"> <span>Save</span></a></div>
												<div class="expbutton"><a onclick="javascript:clearMileageDetails();"> <span>Clear</span></a></div>
								</div>	
								<div id="result" class="line"><jsp:include page="MTMileageMasterList.jsp"></jsp:include></div>
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
		</form:form>
	</body>
</html>
<!-- End:MTMileageMaster.jsp -->