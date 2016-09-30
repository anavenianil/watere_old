<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:MTVehicleMaster.jsp  -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
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
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/report.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/mt.js"></script>
<title>Vehicle Master</title>
</head>
<body onload="javascript:clearVehicleDetails();" style="width: 98%;">
	<form:form method="post" commandName="mtBean" id="mtBean">
		<div>
			<div class="Innermaindiv">
				<div class="header"  style="width: 100%;"></div>
				<div><jsp:include page="MenuLinks.jsp" /></div>					
				<div class="mainDisplay"  style="width: 100%;">
					<div class="whitebox1"   style="width: 98%;">
						<div class="whitebox_t">
							<div class="whitebox_tl">
								<div class="whitebox_tr"></div>
							</div>
						</div>
						<div class="whitebox_m1"    style="width: 98.55%;">
							<div class="lightblueBg1 mtBMW"  style="width: 100%;">
								<div class="headTitle">Vehicle Master</div>
								<div>
									<%-- Content Page starts --%>
								  <div class="line">
							     			<div class="quarter bold">Vehicle Type<span class="mandatory">*</span></div>
											<div class="quarter bold">
												<input type=radio name="vehicleType" tabindex="1" value="1" id="vehicleType1" onclick="javascript:onselectHiredVehicleDropDown('NO');getSpecificVehicleList();" />Govt
												<input type=radio name="vehicleType" tabindex="1" value="2" id="vehicleType2" onclick="javascript:onselectHiredVehicleDropDown('YES');getSpecificVehicleList();"/>Hired												
											</div>	
	                              </div>
	                              <div class="line">
													<div class="quarter bold">Purpose of Vehicle<span class="mandatory">*</span></div>
													<div class="half">
														<input type=radio name="vehiclePoolType" tabindex="1" value="1" id="vehiclePoolType1" onclick="javascript:onselectDedicatedVehicle('YES');getSpecificVehicleList();"/>Dedicated
														<input type=radio name="vehiclePoolType" tabindex="1" value="2" id="vehiclePoolType2" onclick="javascript:onselectDedicatedVehicle('NO');getSpecificVehicleList();"/>Open Pool	
													</div>
								</div>	
								<div id="vehicleDedicatedPerson" style="display:none;"></div>
	                              <div id="vehicleTravelAgency" style="display:none;"></div>
	                              <div class="line">
											<div class="quarter bold">Category<span class="mandatory">*</span></div>
											<div class="quarter bold">
													<form:select path="vehicleCategoryId" id="vehicleCategoryId" cssClass="formSelect" onchange="javascript:onchangeCategory();" >
													<form:option value="0">Select</form:option>
													<form:options items="${categoryList}" itemValue="categoryId" itemLabel="categoryName"/>
													</form:select>												
											</div>
								</div>	
								<div id ="CarriageDetails" style="display:none"></div>
								<div id ="ModelListDetails" style="display:none"></div>	
								<div class="line">
													<div class="quarter bold">Vehicle No<span class="mandatory">*</span></div>
													<div class="half"><form:input path="vehicleNo" id="vehicleNo" maxlength="100" /></div>
								</div>
								<div class="line">
													<div class="quarter bold">Vehicle Name<span class="mandatory">*</span></div>
													<div class="half"><form:input path="vehicleName" id="vehicleName" maxlength="100" /></div>
								</div>	
								<div class="line">
													<div class="quarter bold">No. of Persons/Load Capacity<span class="mandatory">*</span></div>
													<div class="half"><form:input path="noOfPeople" id="noOfPeople" maxlength="100" onkeypress="return checkInt(event);"/></div>
								</div>	
								<div id="GovtVehicles" style="display:none;">
								<div class="line">
														<div class="quarter bold">Type of Fuel<span class="mandatory">*</span></div>
														<div class="half">
															<form:select path="fuelTypeId" id="fuelTypeId" cssClass="formSelect">
																<form:option value="0">Select</form:option>
																<form:options items="${MtFuelTypeList}" itemValue="fuelId" itemLabel="fuelName"/>
															</form:select>
														</div>
									</div>	
								
									<div class="line">
														<div class="quarter bold">Tank Capacity(Ltrs)<span class="mandatory">*</span></div>
														<div class="half"><form:input path="fuelCapacity" id="fuelCapacity" maxlength="100" onkeypress="return checkInt(event);"/></div>
									</div>	
									<div class="line">
														<div class="quarter bold">KPL<span class="mandatory">*</span></div>
														<div class="half"><form:input path="mileage" id="mileage" maxlength="100" onkeypress="return checkInt(event);"/></div>
									</div>	
								</div>
								<div id="HiredVehicles" style="display:none;">
								<div class="line">
														<div class="quarter bold">Type of Fuel</div>
														<div class="half">
															<form:select path="fuelTypeId" id="fuelTypeId" cssClass="formSelect">
																<form:option value="0">Select</form:option>
																<form:options items="${MtFuelTypeList}" itemValue="fuelId" itemLabel="fuelName"/>
															</form:select>
														</div>
									</div>	
									<div class="line">
														<div class="quarter bold">Tank Capacity(Ltrs)</div>
														<div class="half"><form:input path="fuelCapacity" id="hiredFuelCapacity" maxlength="100" onkeypress="return checkInt(event);"/></div>
									</div>	
									<div class="line">
														<div class="quarter bold">KPL</div>
														<div class="half"><form:input path="mileage" id="hiredMileage" maxlength="100" onkeypress="return checkInt(event)"/></div>
									</div>	
								</div>
								<div class="line">
													<div class="quarter bold">Intial Reading<span class="mandatory">*</span></div>
													<div class="half"><form:input path="initialReading" id="initialReading" maxlength="100" onkeypress="return checkInt(event);"/></div>
								</div>	
								<div class="line">
													<div class="quarter bold">Vehicle Used For senstive Item<span class="mandatory">*</span></div>
													<div class="half">
														<input type=radio name="vehicleSensitiveType" tabindex="1" value="1" id="vehicleSensitiveType1" />Sensitive
														<input type=radio name="vehicleSensitiveType" tabindex="1" value="2" id="vehicleSensitiveType2" />Non Sensitive	
													</div>
								</div>			
								<div class="line">
												<div  style="margin-left: 25%;" class="expbutton"><a onclick="javascript:saveVehicleDetails();"> <span>Save</span></a></div>
												<div class="expbutton"><a onclick="javascript:clearVehicleDetails();"> <span>Clear</span></a></div>
								</div>	
								<div id="result" class="line"><jsp:include page="MTVehicleList.jsp" /></div>
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
	<script>
	var travelList=<%= (net.sf.json.JSONArray)session.getAttribute("TravelList") %>;
	var modelList=<%= (net.sf.json.JSONArray)session.getAttribute("modelList") %>;
	var categoryList1=<%= (net.sf.json.JSONArray)session.getAttribute("categoryList1") %>;
	var AllEmployeeList=<%= (net.sf.json.JSONArray)session.getAttribute("AllEmployeeList") %>;
	</script>
</html>
<!-- End:MTVehicleMaster.jsp  -->