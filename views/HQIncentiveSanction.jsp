<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--  Begin:HQIncentiveSanction.jsp -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<html>
<head>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script type="text/javascript" src="script/pisscript.js"></script>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<title>Application For Sanction Of Incentive</title>
</head>
<body>
<form:form method="POST" commandName="HQRequest">
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
								<div class="headTitle">Application For Sanction Of Incentive</div>
								<div class="line">
									<%-- Content Page starts --%>
									<div class="line">
									<div class="line" id="result">
										<jsp:include page="Result.jsp"/>
									</div>
									<div class="line">
										<div class="half leftmar">Whether the higher qualification has been acquired on or after 09-04-1999 and if so the date of qcquiring</div>
										<div class="half">
											<form:radiobutton path="dateOfAcquire" id="dateOfAcquireY" label="Yes" value="Y"/>
											<form:radiobutton path="dateOfAcquire" id="dateOfAcquireN" label="No" value="N"/>											
										</div>
									</div>
									<div class="line">
										<div class="half leftmar">Whether the higher qualification has been acquired after induction into service</div>
										<div class="half">
											<form:radiobutton path="hqAfterInduction" id="hqAfterInductionY" label="Yes" value="Y"/>
											<form:radiobutton path="hqAfterInduction" id="hqAfterInductionN" label="No" value="N"/>											
										</div>
									</div>
								
									<div class="line">
										<div class="half leftmar">(i)Whether the appointment was made in relaxation of the requisite educational qualification</div>
										<div class="half">
											<form:radiobutton path="relaxation" id="relaxationY" label="Yes" value="Y"/>
											<form:radiobutton path="relaxation" id="relaxationN" label="No" value="N"/>											
										</div>
									</div>
									<div class="line">
										<div class="half leftmar">(ii)If yes, Whether the HQ now acquired and for which incentive is being claimed is same as referred to in(i) above</div>
										<div class="half">
											<form:radiobutton path="incentiveClaimed" id="incentiveClaimedY" label="Yes" value="Y"/>
											<form:radiobutton path="incentiveClaimed" id="incentiveClaimedN" label="No" value="N"/>											
										</div>
									</div>
									<div class="line">
										<div class="half leftmar">Whether the Govt servant was sponsored by Govt or he/she availed of study leave</div>
										<div class="half">
											<form:radiobutton path="sponceredByGovt" id="sponceredByGovtY" label="Yes" value="Y"/>
											<form:radiobutton path="sponceredByGovt" id="sponceredByGovtN" label="No" value="N"/>											
										</div>
									</div>
									<div class="line">
										<div class="half leftmar">Whether HQ now acquired essential or desirable</div>
										<div class="half">
											<form:radiobutton path="hqIsEssential" id="hqIsEssentialY" label="Yes" value="Y"/>
											<form:radiobutton path="hqIsEssential" id="hqIsEssentialN" label="No" value="N"/>											
										</div>
									</div>
									<div class="line">
										<div class="half leftmar">Whether the HQ meriting grant of incentive is recognized by AICTE</div>
										<div class="half">
											<form:radiobutton path="hqRecog" id="hqRecogY" label="Yes" value="Y"/>
											<form:radiobutton path="hqRecog" id="hqRecogN" label="No" value="N"/>											
										</div>
									</div>
									<div class="line">
										<div class="half leftmar">Whether there is a direct nexus b/w the functions of the post and the HQ acquired</div>
										<div class="half">
											<form:radiobutton path="nexus" id="nexusY" label="Yes" value="Y"/>
											<form:radiobutton path="nexus" id="nexusN" label="No" value="N"/>											
										</div>
									</div>
									<div class="line">
										<div class="half leftmar">Whether the higher qualification now acquired contributes to the efficiency of the Govt servant</div>
										<div class="half">
											<form:radiobutton path="hqAcquired" id="hqAcquiredY" label="Yes" value="Y"/>
											<form:radiobutton path="hqAcquired" id="hqAcquiredN" label="No" value="N"/>											
										</div>
									</div>
									<div class="line">
										<div class="half leftmar">How the additional qualification now acquired would be helpful?</div>
										<div class="half">
											<form:radiobutton path="addHq" id="addHqY" label="Yes" value="Y"/>
											<form:radiobutton path="addHq" id="addHqN" label="No" value="N"/>											
										</div>
									</div>
									<div class="line">
										<div class="half leftmar">Whether attested copy of Diploma/Degree pertaining to the HQ has been enclosed</div>
										<div class="half">
											<form:radiobutton path="hqEnclosed" id="hqEnclosedY" label="Yes" value="Y"/>
											<form:radiobutton path="hqEnclosed" id="hqEnclosedN" label="No" value="N"/>											
										</div>
									</div>
									<div class="line" style="margin-left:50%">
										<div class="expbutton"><a href="javascript:submitHQSanctionOfIncentive()"> <span>Submit</span></a></div>
										<div class="expbutton"><a href="javascript:clearHQSanctionOfIncentive();"> <span>Clear</span></a></div>
									</div>
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
	</form:form>
	<script>
		clearHQSanctionOfIncentive();
	</script>
</body>
</html>
<!--  End:HQIncentiveSanction.jsp-->