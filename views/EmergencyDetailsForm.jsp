
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin:EmergencyDetailsForm.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<!-- <link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/> -->
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/ajaxUpload.js"></script>
<!-- <script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script> -->

<link href="styles/calendar.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="script/calendar-hd.js"></script>
<script type="text/javascript" src="script/calendar_us.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/cghs.js"></script>


<title>Emergency Claims</title>
</head>

<body onload="clearEmergencyRequest();">
	<form:form method="post" commandName="cghs" id="cghs"   name="Emergencycghs">
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
								<div class="headTitle">Emergency Claims</div>
								<div>
									<%-- Content Page starts --%>
										
										<fieldset>
										<div class="line">
												<div class="half"><form:radiobutton path="requestType" id="nonCghsEmergency" onclick="setEmergencyDetails()"/>Non-CGHS</div>
												<div class="half" style="float:right"><form:radiobutton path="requestType" id="cghsEmergency" onclick="setEmergencyDetails()"/>CGHS</div>
										</div>
										</fieldset>												
										<div class="line"></div>
										<div class="line"></div>	
										<div class="lightblueBg1">
										<div class="headTitle" id="headTitle">CGHS Emergency Request Form</div>
										<div>
										<div id="result"></div>
										<div id="requestForm">
										<div class="line">
												<div class="quarter leftmar">Name of the Patient<span class="mandatory">*</span></div>
												<div class="quarter"><form:select path="familyMemberId" id="familyMemberId"    onchange="javascript:displayFamilyMemberDetails();">
																		<form:option value="">Select</form:option>
																		<form:options items="${cghs.familyMemberList}" itemLabel="name" itemValue="id"  />																	</form:select>
												</div>
										</div>
										<div class="line">
											<div class="quarter leftmar">Date of Admission<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="admissionDate" id="admissionDate" cssClass="dateClass" readonly="true"/>&nbsp;
	                                            
	                                            <script type="text/javascript">
											new tcal({'formname':'Emergencycghs','controlname':'admissionDate'});
											</script>
	                                            <!-- <img  src="./images/calendar.gif"   id="date_start_trigger3" />	
													<script type="text/javascript">
														Calendar.setup({inputField :"admissionDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger3",singleClick : true,step : 1});
													</script> -->
											</div>
											<div class="quarter leftmar">Date of Discharge<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="dischargeDate" id="dischargeDate" cssClass="dateClass" readonly="true"/>&nbsp;
											<!-- <img  src="./images/calendar.gif"   id="date_start_trigger4" />	
													<script type="text/javascript">
														Calendar.setup({inputField :"dischargeDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger4",singleClick : true,step : 1});
													</script> -->
													
													
													<script type="text/javascript">
											new tcal({'formname':'Emergencycghs','controlname':'dischargeDate'});
											</script>
											</div>
											
										</div>
											<div class="line">
												<div id="cghsHospital">
													<div class="quarter leftmar">Hospital Name<span class="mandatory">*</span></div>
													<div ><form:select path="referralHospitalId" id="referralHospitalId">
																		<form:option value="">Select</form:option>
																		<form:options items="${cghs.referralHospitalList}" itemLabel="hospitalName" itemValue="id" />
																	</form:select>
													</div>
												</div>
												<div id="nonCghsHospital" style="display: none">
													<div class="quarter leftmar">Hospital Address<span class="mandatory">*</span></div>
													<div class="quarter"><form:textarea path="hospitalAddress" id="hospitalAddress"/></div>
												</div>
											</div>
											<div class="line">
												<div class="quarter leftmar">Lab Charges</div>
												<div class="quarter"><form:input path="labCharges" id="labCharges" onchange="javascript:CalculateAmount('emergency');" onkeypress="return checkFloat(event,'labCharges');"/></div>
												<div class="quarter leftmar">Medicines Charges</div>
												<div class="quarter"><form:input path="medicinesCharges" id="medicinesCharges" onchange="javascript:CalculateAmount('emergency');" onkeypress="return checkFloat(event,'medicinesCharges');"/></div>
											</div>
											<div class="line">
												<div class="quarter leftmar">Room Rent Charges(ICU/ICCU/Ward)</div>
												<div class="quarter"><form:input path="roomRent" id="roomRent" onchange="javascript:CalculateAmount('emergency');" onkeypress="return checkFloat(event,'roomRent');"/></div>
												<div class="quarter leftmar">O.T Charges</div>
												<div class="quarter"><form:input path="otCharges" id="otCharges" onchange="javascript:CalculateAmount('emergency');" onkeypress="return checkFloat(event,'otCharges');"/></div>
											</div>
											<div class="line">
												<div class="quarter leftmar">O.T.Consumables Charges</div>
												<div class="quarter"><form:input path="otConsumables" id="otConsumables" onchange="javascript:CalculateAmount('emergency');" onkeypress="return checkFloat(event,'otConsumables');"/></div>
												<div class="quarter leftmar">Anaesthesia Charges</div>
												<div class="quarter"><form:input path="anaesthesiaCharges" id="anaesthesiaCharges" onchange="javascript:CalculateAmount('emergency');" onkeypress="return checkFloat(event,'anaesthesiaCharges');"/></div>
											</div>
											<div class="line">
												<div class="quarter leftmar">Implants like pacemaker joint replacement,Coronary stent,etc</div>
												<div class="quarter"><form:input path="implantsCharges" id="implantsCharges" onchange="javascript:CalculateAmount('emergency');" onkeypress="return checkFloat(event,'implantsCharges');"/></div>
												<div class="quarter leftmar">Artificial devices Charges</div>
												<div class="quarter"><form:input path="artificialCharges" id="artificialCharges" onchange="javascript:CalculateAmount('emergency');" onkeypress="return checkFloat(event,'artificialCharges');"/></div>
											</div>
											<div class="line">
												<div class="quarter leftmar">Procedure</div>
												<div class="quarter"><form:input path="procedure" id="procedure" onchange="javascript:CalculateAmount('emergency');" onkeypress="return checkFloat(event,'procedure');"/></div>
												<div class="quarter leftmar">Special Nurse/Ayah Charges(if any)</div>
												<div class="quarter"><form:input path="specialNurse" id="specialNurse" onchange="javascript:CalculateAmount('emergency');" onkeypress="return checkFloat(event,'specialNurse');"/></div>
											</div>	
											<div class="line">
												<div class="quarter leftmar">Miscellaneous Charges</div>
												<div class="quarter"><form:input path="miscellaneousCharges" id="miscellaneousCharges" onchange="javascript:CalculateAmount('emergency');" onkeypress="return checkFloat(event,'miscellaneousCharges');"/></div>
												<div class="quarter leftmar">Total Amount</div>
												<div class="quarter" id="total"></div>
											</div>
										<div class="line">
												<div class="quarter leftmar">Nature/ Brief details of Emergency<span class="mandatory">*</span></div>
												<div class="quarter"><form:textarea  path="emergencyDetails" id="emergencyDetails" rows="5" cols="20" onkeypress="textCounter(this,document.forms[0].counter,500);" onkeyup="textCounter(this,document.forms[0].counter,500);"/>												
												<input type="text" class="counter" name="counter" value="500" id="counter" disabled="disabled"/>
												</div>
												<div class="quarter leftmar">Full address of the place where the patient fell ill</div>
												<div class="quarter"><form:textarea  path="patientIllAddress" id="patientIllAddress" rows="5" cols="20" onkeypress="textCounter(this,document.forms[0].counter1,500);" onkeyup="textCounter(this,document.forms[0].counter1,500);"/>
												<input type="text" class="counter" name="counter1" value="500" id="counter1" disabled="disabled"/>
												</div>
										</div>
										
										<div class="line">
												<div class="quarter leftmar">If the address is other than duty station reason there to</div>
												<div class="quarter"><form:textarea  path="otherAddress" id="otherAddress" rows="5" cols="20" onkeypress="textCounter(this,document.forms[0].counter2,500);" />
												<input type="text" class="counter" name="counter2" value="500" id="counter2" disabled="disabled"/> 
												</div>
											<div id="reason" style="display: none">
												<div class="quarter leftmar">Reason for not taking treatment in Govt.Hospital/CGHS Dispensary<span class="mandatory">*</span></div>
												<div class="quarter"><form:textarea path="nonCghsReason" id="reason1" rows="5" cols="20" onkeypress="textCounter(this,document.forms[0].counter3,500);" onkeyup="textCounter(this,document.forms[0].counter3,500);"/>
												<input type="text" class="counter" name="counter3" value="500" id="counter3" disabled="disabled"/></div>
											</div>
										</div>
										<div id="files">
										<div class="line">
											<div class="quarter leftmar">Medical bills (Cash Receipt duly signed by hospital authorities)</div>
											<div class="quarter"><form:input path="medicalBillsFile" type="file" id="files1" size="12"/></div>
											<div class="quarter leftmar">Discharge Summary (In case of Admission)</div>
											<div class="quarter"><form:input path="dischargeSummeryFile" type="file" id="files2" size="12"/></div>
											
										</div>
										<div class="line">
											<div class="quarter leftmar">Copy of CGHS Card</div>
											<div class="quarter"><form:input path="cghsCardFile" type="file" id="files3"  size="12"/></div>
										</div>
										</div>
										<div class="line float">
											<div  style="margin-left:80%"><a href="javascript:manageEmergencyRequest()"> <div class="appbutton"><span>Submit</span></div></a>
											<a href="javascript:clearEmergencyRequest()"> <div class="appbutton"><span>Clear</span></div></a>
										</div>
									</div>
									<%-- Content Page end --%>
								
								       <div>&nbsp;</div>
							     </div>
						    </div>
						    
					  </div>
				  </div>
				  
			 </div>
			 </div>
			 <div class="whitebox_b_non">
							   <div class="whitebox_bl">
								   <div class="whitebox_br"></div>
							   </div>
						   </div>
		    
			     		
		    </div>
	   </div>	
	    <div><jsp:include page="Footer.jsp" /></div>	
	</div>
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		<form:hidden path="requestId"/>
		<form:hidden path="cghscardref"/>
		
		</form:form>
		<script>
			familyMember = <%= (net.sf.json.JSONArray)session.getAttribute("jsonfamilyMemberList") %>;
			</script>
	</body>
</html>

<!-- End:EmergencyDetailsForm.jsp -->