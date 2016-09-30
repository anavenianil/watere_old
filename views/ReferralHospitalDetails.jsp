<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:ReferralHospitalDetails.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script type="text/javascript" src="script/cghs.js"></script>
<link href="styles/calendar.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="script/calendar-hd.js"></script>
<script type="text/javascript" src="script/calendar_us.js"></script>
<title>Referral Hospital Details</title>
</head>

<body>
	<form:form method="post" commandName="cghs" id="cghs" name="referralhospital">
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
								<div class="headTitle">Referral Hospital Details</div>
								<div>
									<%-- Content Page starts --%>
									<div class="line">
											<div class="quarter leftmar">Creation Date<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="creationDate" id="creationDate" cssClass="dateClass" readonly="true"/>&nbsp;
											<script type="text/javascript">
											         new tcal({'formname':'referralhospital','controlname':'creationDate'});
											</script>
											</div>
											<div class="quarter leftmar">Treatment</div>
											<div class="quarter leftmar"><form:input path="bankName" id="bankName" cssClass="dateClass"/></div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Referral Hospital Name<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="hospitalName" id="hospitalName" maxlength="100" onkeypress="javascript:increaseTextWidth('hospitalName');return checkChar(event);"/></div>
										<div class="quarter leftmar">Phone No</div>
										<div class="quarter leftmar"><form:input path="branchName" id="branchName" cssClass="dateClass" maxlength="11" onkeypress="return checkInt(event);"/></div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Referral Hospital Location<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="hospitalLocation" id="hospitalLocation" maxlength="100" onkeypress="javascript:increaseTextWidth('hospitalLocation');return checkChar(event);"/></div>
									</div>
									<div class="line">	
										<div class="quarter leftmar">Address<span class="mandatory">*</span></div>
										<div class="quarter"><form:textarea path="address" id="address" cols="30" rows="4" onkeypress="textCounter(this,document.forms[0].counter,500);" onkeyup="textCounter(this,document.forms[0].counter,500);"/><input type="text" class="counter" name="counter" value="500" id="counter" disabled="disabled"/></div>
										
									</div>
									<div class="line" style="display: none" id="deleteDate">
											<div class="quarter leftmar">Deletion Date<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="deletionDate" id="deletionDate"  class="dateClass" readonly="true"/>&nbsp;
											<script type="text/javascript">
											         new tcal({'formname':'referralhospital','controlname':'deletionDate'});
											</script>
											</div>
									</div>
									<div class="line">
										<div style="margin-left:25%">
										<a href="javascript:manageReferralHospital();"><div class="appbutton">Submit</div></a>
											<a href="javascript:selectedDeleteReferralHospital();"><div class="appbutton">Delete</div></a>
											<a href="javascript:clearReferralHospital();"><div class="appbutton">Clear</div></a>
											<a href="javascript:normalReports('hospital');"><div class="appbutton">Report</div></a>
										</div>
									</div>
									<div class="line" id="hospitalList">
									    <jsp:include page="ReferralHospitalList.jsp" />
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
		<form:hidden path="param" id="param"/>
		<form:hidden path="type"/>
		<form:hidden path="pk"/>
		<form:hidden path="selectedDelete"/>
		</form:form>
	</body>
</html>
<!-- End:ReferralHospitalDetails.jsp -->