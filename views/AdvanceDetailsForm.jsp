
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin:AdvanceDetailsForm.jsp -->
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
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/ajaxUpload.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/cghs.js"></script>


<title>CGHS Advance Request Details</title>
</head>

<body>
   <script type="text/javascript">
	
	jQuery(window).bind('beforeunload',function(event){
	event.stopPropagation();
	event.returnValue = "Are you Sure You Want to Close Window? You Must Complete Advance page ";
	return event.returnValue;
    });
	</script>
	<form:form method="post" commandName="cghs" id="cghs">
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
								<div class="headTitle">CGHS Advance Request Details</div>
								<div>
									<%-- Content Page starts --%>
									<div id="result"></div>
										<div class="line">
											<div class="quarter leftmar">Date of Admission<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="admissionDate" id="admissionDate" cssClass="dateClass" readonly="true" onchange="javascript:displayFamilyMemberDetails();" />
											<img  src="./images/calendar.gif"   id="date_start_trigger3"  />	
													<script type="text/javascript">
														Calendar.setup({inputField :"admissionDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger3",singleClick : true,step : 1});
													</script>
											</div>
											<div class="quarter leftmar">Estimation Date Of Relieving<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="estimationDate" id="estimationDate" cssClass="dateClass" readonly="true" />
											<img  src="./images/calendar.gif"   id="date_start_trigger4" />	
													<script type="text/javascript">
														Calendar.setup({inputField :"estimationDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger4",singleClick : true,step : 1});
													</script>
											</div>
										</div>
										<div class="line">
											<div class="quarter leftmar">Estimation Amount<span class="mandatory">*</span></div>
											<div class="half"><font size="4.5em"><span class="WebRupee" >R</span></font><form:input path="estimationAmount" id="estimationAmount" onkeypress="return checkInt(event,'sanctionNoAdv');"/> </div>
										</div>
										<div class="line">
											<div class="quarter leftmar">In-patient number</div>
											<div class="half"><form:input path="InPatientNumber" id="InPatientNumber" /> </div>
										</div>
										<div class="line">
											<div class="quarter leftmar">Details of Permission</div>
											<div class="half"><form:textarea path="permissionDetails" id="permissionDetails" /> </div>
										</div>
										<div id="files">
										<div class="line">
											<div class="quarter leftmar">Estimation from hospital authorities (With in-patient number, date of admission and approx expenditure of amount)</div>
											<div class="half"><form:input path="estimationFile" type="file" id="files1"/></div>
										</div>
										<div class="line">
											<div class="quarter leftmar">Copy of CGHS Card</div>
											<div class="half"><form:input path="cghsCardFile" type="file" id="files2"/></div>
										</div>
										</div>
										<div id="submitRequest" class="float">
										   	<div class="expbutton" style="float: right"><a href="javascript:clearAdvanceDetails()"><span>Clear</span></a></div>
											<div class="expbutton" style="float: right"><a href="javascript:manageAdvanceDetails('${cghs.requestId}');"><span>Submit</span></a></div>
										</div>
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
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		<form:hidden path="settlement"/>
		<form:hidden path="requestId"/>
		<form:hidden path="cghscardref"/>
		
		</form:form>
		
	<script>
			familyMember = <%= (net.sf.json.JSONArray)session.getAttribute("jsonfamilyMemberList") %>;
			</script>
	
	</body>
	
</html>
<!-- End:AdvanceDetailsForm.jsp -->
