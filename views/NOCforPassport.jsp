<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:NOCforPassport.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/tabs.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.min.js"></script>
<script type="text/javascript" src="script/jquery.ui.custom.min.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/cghs.js"></script>

<script type="text/javascript" src="script/passport.js"></script>

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

<title>NOC For Passport</title>
</head>

<body onload="javascript:resetPassportAndProceeding();">

<form:form method="post" commandName="passport">
	
	
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
	<div class="headTitle">NOC Application For Passport</div>
	<%-- Content Page starts --%>
	<div class="line">
	
	
	
	<div id="tabs">
		<ul>        		
			<li><a href="#fragment-1">Passport</a></li>
			<li><a href="#fragment-2">Passport & Abroad</a></li>
		</ul>
	
			<div id="fragment-1" class="ui-tabs-panel">
			
			<div class="line" id="result">
	 			 <jsp:include page="nocPassportResult.jsp" />
			</div>
			
			<div class="line">
		    	<div class="leftmar quarter">SFID<span class="mandatory">*</span></div>
				<div class="quarter">
					<form:input path="sfID" id="sfID" ></form:input>
				</div>
				<div class="leftmar quarter">Type of NOC<span class="mandatory">*</span></div>
				<div class="quarter">
					<form:select path="passportAppType" id="passportAppType"  cssClass="formSelect"  onchange="javascript:getLetterDetailsView()">
						 <form:option value="">Select</form:option>
						 <form:option value="passport">passport</form:option>
						 <form:option value="passport & abroad">passport & abroad</form:option>
					</form:select>
				</div>
		    </div>
		    
		    <br/><br/>
		    
		    <div class="line" id="letterDetailsDiv" style="display:none">
		    
		    <div class="line">
				<div class="leftmar quarter">Letter No</div>
				<div class="quarter">
				    <form:input path="letterDesc" id="letterDesc" ></form:input>
				</div>    
		   		<div class="leftmar quarter">Letter Date<span class="mandatory">*</span></div>
		    	<div class="quarter">
		    		<form:input path="letterDate" id="letterDate" cssClass="dateClass" readonly="true" /> 
			    	<img  src="./images/calendar.gif" id="letterDate1" /> 
			    	<script type="text/javascript">
					  Calendar.setup({inputField :"letterDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"letterDate1",singleClick : true,step : 1});
					</script>
				</div>
		
		    </div>
		    
			</div>
		
		    <br/><br/>	
		    
		    
		    <div class="line" id="passportProceedDetailsDiv" style="display:none">

								<!-- passport form starts -->
						
									    <div class="line">
									    	<div class="leftmar quarter">Name</div>
											<div class="quarter">${passport.empDetails.nameInServiceBook}</div>
											<div class="leftmar quarter">Designation</div>
											<div class="quarter">${passport.empDetails.designationDetails.name}</div>
									    </div>
									
									
									   <div class="line">
										    <div class="leftmar quarter" >Applicant's Address</div>
											<div class="quarter" >${passport.empAddress}</div>
									    </div>
										
									    
									    
									    <div class="line">
									    <div class="leftmar quarter">Relations Working in Foreign Embassy/Firms in India/Abroad</div>
											<div><form:textarea path="relations" id="relations" rows="3" cols="20"  /></div>
											</div>
											 <div class="line">
										<div class="leftmar quarter">Details of Employment in Defence Officers or any Govt./other offices during last 10 years.</div>
											<div><form:textarea path="employmentDetails" id="employmentDetails" rows="3" cols="20"  /></div>
									    </div>
									    
										<div class="line">
									   		 <div class="leftmar quarter">Details of Father/Husband/Guardian<span class="mandatory">*</span></div>
											 <div class="quarter">
											    <form:radiobutton path="detailsFlag" label="Father" value="F" id="selfFinanceFlag" onclick="javascript:supportingDetails('');"/> 
												<form:radiobutton path="detailsFlag" label="Husband" value="H" id="selfFinanceFlag" onclick="javascript:supportingDetails('');"/>
												<form:radiobutton path="detailsFlag" label="Guardian" value="G" id="selfFinanceFlag" onclick="javascript:supportingDetails('');"/>
											</div>
									    </div>
									    
									    <div class="line" id="supportingDetailsDiv" style="display:none">
										    <div class="line">
										    	<div class="leftmar quarter">Name <span class="mandatory">*</span></div>
										    	<div class="quarter"><form:input path="supportName" id="supportName" /></div> 
										    </div>
										    <div class="line">
											    <div class="leftmar quarter">Occupation <span class="mandatory">*</span></div>
											    <div class="quarter"><form:input path="supportOccupation" id="supportOccupation" /></div> 
										    </div>
										    <div class="line">
										    <div class="leftmar quarter">Address </div>
										    <div class="quarter"><form:input path="supportAddress" id="supportAddress" /></div> 
										    </div>
									    </div>
									    
									    
									    <div class="line">
									    	<div class="leftmar quarter">Involved in any Court/Police/Vigilance case<span class="mandatory">*</span></div>
											<div class="quarter">
									    		<form:radiobutton path="vigilanceFlag" label="Yes" value="Y" id="vigilanceFlag"/> 
												<form:radiobutton path="vigilanceFlag" label="No" value="N" id="vigilanceFlag"/>
											</div>
										   <div class="leftmar quarter">Possess Passport Already<span class="mandatory">*</span></div>
									       <div class="quarter">
									    		<form:radiobutton path="passportPossessFlag" label="Yes" value="Y" id="passportPossessFlag" onclick="javascript:enablePassportDetails('');"/> 
										 		<form:radiobutton path="passportPossessFlag" label="No" value="N" id="passportPossessFlag" onclick="javascript:enablePassportDetails('');"/>
										   </div>
									   </div>
									
									   <div class="line">
									     </div>
									        <div class="line" id="passportDetailsDiv" style="display:none">
									        <div class="line">
									    <div class="leftmar quarter">Passport Type<span class="mandatory">*</span></div>
									    <div class="quarter"><form:select path="passportType" id="passportType" cssClass="formSelect">
											<form:option value="0" label="Select"></form:option>
														
											</form:select></div>
										<div class="leftmar quarter">Passport No<span class="mandatory">*</span></div>
									    <div class="quarter"><form:input path="passportNumber" id="passportNumber" /></div>    
									    </div>   
									    <div class="line">           
										<div class="leftmar quarter">Date of Issue<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="issueDate" id="issueDate"
											cssClass="dateClass" readonly="true" /> <img
											src="./images/calendar.gif" id="issueDate1" /> <script
											type="text/javascript">
											  Calendar.setup({inputField :"issueDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"issueDate1",singleClick : true,step : 1});
										</script></div>
										<div class="leftmar quarter">Validity<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="validityDate" id="validityDate"
											cssClass="dateClass" readonly="true" /> <img
											src="./images/calendar.gif" id="validityDate2" /> <script
											type="text/javascript">
											  Calendar.setup({inputField :"validityDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"validityDate2",singleClick : true,step : 1});
										</script></div>
									    </div>
									    </div>
									    
									    
									   <div class="line">
										   <div class="leftmar quarter">Details of passport lost,if any<span class="mandatory">*</span></div>
									       <div class="quarter">
									    		<form:radiobutton path="passportLostFlag" label="Yes" value="Y" id="passportLostFlag" onclick="javascript:enableLostPassportDetails('');"/> 
										 		<form:radiobutton path="passportLostFlag" label="No" value="N" id="passportLostFlag" onclick="javascript:enableLostPassportDetails('');"/>
										   </div>
									   </div>
									    
									   <div class="line" id="LostPassportDetailsDiv" style="display:none">
									        <div class="line">
											    <div class="leftmar quarter">Lost Passport Type<span class="mandatory">*</span></div>
											    <div class="quarter">
											    	<form:select path="lostPassportType" id="lostPassportType" cssClass="formSelect">
														<form:option value="0" label="Select"></form:option>
														
													</form:select>
												</div>
												<div class="leftmar quarter">Passport No<span class="mandatory">*</span></div>
									    		<div class="quarter">
									    			<form:input path="lostPassportNumber" id="lostPassportNumber" />
									    		</div>    
									    	</div>   
										    
										    <div class="line">           
												<div class="leftmar quarter">Date of Issue<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:input path="ppissueDate" id="ppissueDate" cssClass="dateClass" readonly="true" /> 
													<img src="./images/calendar.gif" id="ppissueDate1" /> 
													<script type="text/javascript">
													  Calendar.setup({inputField :"ppissueDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"ppissueDate1",singleClick : true,step : 1});
													</script>
												</div>
												<div class="leftmar quarter">Validity<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:input path="ppvalidityDate" id="ppvalidityDate" cssClass="dateClass" readonly="true" /> 
													<img src="./images/calendar.gif" id="ppvalidityDate2" /> 
													<script type="text/javascript">
													    Calendar.setup({inputField :"ppvalidityDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"ppvalidityDate2",singleClick : true,step : 1});
													</script>
												</div>
										    </div>
									    </div> 
									    
									    <div class="line">
									    	<div class="leftmar quarter">Date of Departure<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="departureDate" id="departureDate"
											cssClass="dateClass" readonly="true" /> <img
											src="./images/calendar.gif" id="departureDate3" /> <script
											type="text/javascript">
											  Calendar.setup({inputField :"departureDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"departureDate3",singleClick : true,step : 1});
										</script></div>
										<div class="leftmar quarter">Date of Return<span class="mandatory">*</span></div>
												<div class="quarter"><form:input path="returnDate" id="returnDate"
											cssClass="dateClass" readonly="true" /> <img
											src="./images/calendar.gif" id="returnDate4" /> <script
											type="text/javascript">
											  Calendar.setup({inputField :"returnDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"returnDate4",singleClick : true,step : 1});
										</script></div>
									
									    </div>
									    	<div class="line">
									    <div class="leftmar quarter">Duration of Stay<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="duration" id="duration" onkeypress="return checkFloat(event,'duration');" ></form:input> Months</div>
										<div class="leftmar quarter">Purpose of Visit<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="purpose" id="purpose"></form:input></div>
									    </div>
									     <div class="line">
									    <div class="leftmar quarter">Going Alone<span class="mandatory">*</span></div>
										<div class="quarter">
									    <form:radiobutton path="familyMembersFlag" label="Yes" value="Y" id="familyMembersFlag" onclick="javascript:enableFamilyMembers('');"/> 
										<form:radiobutton path="familyMembersFlag" label="No" value="N" id="familyMembersFlag" onclick="javascript:enableFamilyMembers('');"/>
										</div>
									   	<div class="leftmar quarter">Amount to be spend(Approx.)<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="spendingAmount"
											id="spendingAmount"
											onkeypress="return checkFloat(event,'spendingAmount');"></form:input></div>
									    </div>
									    <div class="line" id="familyDetailsDiv" style="display:none">
									    <div class="leftmar quarter">Family Members Details<span class="mandatory">*</span></div>
										<div class="quarter" id="familyDetails">
										<form:select path="familyMemberId" id="familyMemberId" size="3" multiple="true" cssStyle="width:150px">	
												
										</form:select></div>
										</div>
									    	<div class="line">
									    <div class="leftmar quarter">Trip Abroad Financed By Self<span class="mandatory">*</span></div>
										<div class="quarter">
									    <form:radiobutton path="selfFinanceFlag" label="Yes" value="Y" id="selfFinanceFlag" onclick="javascript:selfFinance('');"/> 
										<form:radiobutton path="selfFinanceFlag" label="No" value="N" id="selfFinanceFlag" onclick="javascript:selfFinance('');"/>
										</div>
									       <div class="leftmar quarter">Countries To be Visited <span class="mandatory">*</span></div>
									    <div class="quarter"><form:input path="countriesToVisit" id="countriesToVisit" /></div> 
									  
									    </div>
									    <div class="line" id="sourceOfAmountDiv" style="display:none">
									    <div class="leftmar quarter">Source Of Amount for Trip<span class="mandatory">*</span></div>
									    <div class="quarter"><form:input path="sourceOfAmount" id="sourceOfAmount" /></div> 
									    </div>
									    <div class="line" id="sourceOfAmountDivNo" style="display:none">
									    <div class="line">
									     <div class="leftmar quarter">Enter The Financed Person Details: </div>
									    </div>
									    <div class="line">
									    <div class="leftmar quarter">Name <span class="mandatory">*</span></div>
									    <div class="quarter"><form:input path="lenderName" id="lenderName" /></div> 
									    </div>
									    <div class="line">
									    <div class="leftmar quarter">Nationality <span class="mandatory">*</span></div>
									    <div class="quarter"><form:input path="nationality" id="nationality" /></div> 
									    </div>
									    <div class="line">
									    <div class="leftmar quarter">Relationship </div>
									    <div class="quarter"><form:input path="lenderRelationship" id="lenderRelationship" /></div> 
									    </div>
									    </div>
									    
									    <div class="line">
									    	<div class="leftmar quarter">Previous visited country Details with Dates if available</div>
											<div><form:textarea path="prevCountryDetails" id="prevCountryDetails" rows="3" cols="20" /></div>
											
											<div class="leftmar quarter">Are you likely to accept foreign hospitality</div>
									        <div class="quarter">
									    		<form:radiobutton path="hospitalFlag" label="Yes" value="Y" id="hospitalFlag" /> 
										 		<form:radiobutton path="hospitalFlag" label="No" value="N" id="hospitalFlag" />
										    </div>
									    </div>
									    
									
									    <div class="line">
											<div class="leftmar quarter">I will return my Identity Card before proceeding abroad<span class="mandatory">*</span></div>
									        <div class="quarter">
									    		<form:radiobutton path="idCardFlag" label="Yes" value="Y" id="idCardFlag" /> 
										 		<form:radiobutton path="idCardFlag" label="No" value="N" id="idCardFlag" />
										    </div>
											
											<div class="leftmar quarter">I shall not settle permanently abroad and rejoin my duty after expiry of leave<span class="mandatory">*</span></div>
									        <div class="quarter">
									    		<form:radiobutton path="settleFlag" label="Yes" value="Y" id="settleFlag" /> 
										 		<form:radiobutton path="settleFlag" label="No" value="N" id="settleFlag" />
										    </div>
									    </div>    
									    
									    
									    <div class="line">
											<div class="leftmar quarter">contractual obligation to serve DRDO<span class="mandatory">*</span></div>
									        <div class="quarter">
									    		<form:radiobutton path="contractualFlag" label="Yes" value="Y" id="contractualFlag" onclick="javascript:enableContractulaFlag('');"/> 
										 		<form:radiobutton path="contractualFlag" label="No" value="N" id="contractualFlag" onclick="javascript:enableContractulaFlag('');"/>
										    </div>
									    </div>    
									    
									   <div class="line" id="contractualDetailsDiv" style="display:none">	    
										    <div class="line">           
												<div class="leftmar quarter">Contractual obligation Period From<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:input path="coPeriodFrom" id="coPeriodFrom" cssClass="dateClass" readonly="true" /> 
													<img src="./images/calendar.gif" id="coPeriodFrom1" /> 
													<script type="text/javascript">
													  Calendar.setup({inputField :"coPeriodFrom",ifFormat : "%d-%b-%Y",showsTime : false,button :"coPeriodFrom1",singleClick : true,step : 1});
													</script>
												</div>
												<div class="leftmar quarter">Contractual obligation Period To<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:input path="coPeriodTo" id="coPeriodTo" cssClass="dateClass" readonly="true" /> 
													<img src="./images/calendar.gif" id="coPeriodTo2" /> 
													<script type="text/javascript">
													    Calendar.setup({inputField :"coPeriodTo",ifFormat : "%d-%b-%Y",showsTime : false,button :"coPeriodTo2",singleClick : true,step : 1});
													</script>
												</div>
										    </div>
									    </div>     
									      
									    
									    <div class="line">
									    	<div class="leftmar quarter">Any other information the applicant would like to add in support of the case</div>
											<div><form:textarea path="remarks" id="remarks" rows="3" cols="20" /></div>
										</div>	
										
										<div class="line">
											<div class="leftmar quarter">I also certify that the contents of the instructions given in Ministry
									    	  			of Defence OM No. 27(8)/91/D(Civ-II) dated 01-05-1991 have been read,understood and would be complied with by me
									    	  			<span class="mandatory">*</span></div>
									        <div class="quarter">
									    		<form:radiobutton path="certifyStatus" label="Yes" value="Y" id="certifyStatus" /> 
										 		<form:radiobutton path="certifyStatus" label="No" value="N" id="certifyStatus" />
										    </div>
									    </div>  

		   						<!-- passport form ends -->
		    					<div class="line">
									<div class="quarter bold leftmar rightmargin">Visualance Flag</div>
									<div class="quarter">
										<form:checkbox path="visualFlag" id="visualFlag" value="Y"/>
									</div>
									
									<div class="quarter bold leftmar rightmargin">Immovable Property Return</div>
									<div class="quarter">
										<form:checkbox path="propReturnFlag" id="propReturnFlag" value="Y"/>
									</div>
								</div>
		    
		    </div>
		    
		    
		   
		    
		    <div class="line">
		    	<div class="leftmar quarter">Admin No</div>
				<div class="quarter">
					<form:input path="adminNoteNo" id="adminNoteNo" ></form:input>
				</div>    
		   		<div class="leftmar quarter">Admin Date<span class="mandatory">*</span></div>
		    	<div class="quarter">
		    		<form:input path="adminNoteDt" id="adminNoteDt" cssClass="dateClass" readonly="true" /> 
			    	<img  src="./images/calendar.gif" id="adminNoteDt1" /> 
			    	<script type="text/javascript">
					  Calendar.setup({inputField :"adminNoteDt",ifFormat : "%d-%b-%Y",showsTime : false,button :"adminNoteDt1",singleClick : true,step : 1});
					</script>
				</div>
		    </div>
		    
		    <div class="line">
		    	<div class="leftmar quarter">ID Certificate No</div>
				<div class="quarter">
					<form:input path="idCertificateNo" id="idCertificateNo" ></form:input>
				</div>    
		   		<div class="leftmar quarter">ID Certificate Date<span class="mandatory">*</span></div>
		    	<div class="quarter">
		    		<form:input path="idCertificateDt" id="idCertificateDt" cssClass="dateClass" readonly="true" /> 
			    	<img  src="./images/calendar.gif" id="idCertificateDt1" /> 
			    	<script type="text/javascript">
					  Calendar.setup({inputField :"idCertificateDt",ifFormat : "%d-%b-%Y",showsTime : false,button :"idCertificateDt1",singleClick : true,step : 1});
					</script>
				</div>
		    </div>
		
		    
		    <br/><br/>
		    <div class="line">   
		   		<div class="leftmar quarter">Date of Issue to Employee<span class="mandatory">*</span></div>
		    	<div class="quarter">
		    		<form:input path="receivedDt" id="receivedDt" cssClass="dateClass" readonly="true" /> 
			    	<img  src="./images/calendar.gif" id="receivedDt1" /> 
			    	<script type="text/javascript">
					  Calendar.setup({inputField :"receivedDt",ifFormat : "%d-%b-%Y",showsTime : false,button :"receivedDt1",singleClick : true,step : 1});
					</script>
				</div>
		    </div>
		    
			<div style="margin-left: 25%;"  >
				<div id="sub" ><div class="expbutton"><a href="javascript:saveNOCforPassport()" ><span>Submit</span></a></div></div>  
				<div class="expbutton"><a href="javascript:clearPassPortApplicationDetails()" ><span>Clear</span></a></div> 
			</div>
		
			<div class="line" id="result1">
				<jsp:include page="nocforPassportList.jsp" />
			</div>
		    </div>
		   
		    
		    <div id="fragment-2" class="ui-tabs-panel">
		     <div class="line">
				<div class="leftmar quarter">SFID<span class="mandatory">*</span></div>
				<div class="quarter">
				    <form:select path="sfidForAbroad" id="sfidForAbroad"  cssClass="formSelect"  >
						 <form:option value="">Select</form:option>
						 <form:options items="${sfIDsForVerified}" itemValue="sfid" itemLabel="sfid"/>
					</form:select>
				</div>     
		    </div>
		    
		    <div class="line">
				<div class="leftmar quarter">HQrs Letter No<span class="mandatory">*</span></div>
				<div class="quarter">
				    <form:input path="hqLetterNo" id="hqLetterNo" ></form:input>
				</div>    
		   		<div class="leftmar quarter">HQrs Letter Date<span class="mandatory">*</span></div>
				<div class="quarter">
				    <form:input path="hqLetterDt" id="hqLetterDt" cssClass="dateClass" readonly="true" /> 
			    	<img  src="./images/calendar.gif" id="hqLetterDt1" /> 
			    	<script type="text/javascript">
					  Calendar.setup({inputField :"hqLetterDt",ifFormat : "%d-%b-%Y",showsTime : false,button :"hqLetterDt1",singleClick : true,step : 1});
					</script>
				</div> 
		    </div>
		    
		    
		   <div  style="margin-left: 25%;"  >
				<div class="expbutton"><a href="javascript:updatePassportApplication()" ><span>Submit</span></a></div> 
				<div class="expbutton"><a href="javascript:clearPassPortApplication()" ><span>Clear</span></a></div>
		   </div>
		    
		    <div class="line" id="result2">
				<jsp:include page="passportAndAbroadList.jsp" />
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
	<div><jsp:include page="Footer.jsp" /></div>
	</div>
	
	<form:hidden path="param" />
	<form:hidden path="type" />
	<form:hidden path="beanType" />
	<form:hidden path="verificationType" id="verificationType"/>
</form:form>

</body>
</html>
<!-- End:NOCforPassport.jsp -->