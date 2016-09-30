<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:tiutionFeeClaimMaster.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/report.js"></script>
<script type="text/javascript" src="script/tutionFee.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<title>Claim Master</title>
</head>

<body>
	<form:form method="post" commandName="tutionFee">
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
								<div class="headTitle"> Claim Master</div>
								<div>
									<%-- Content Page starts --%>
								<div class="line">
								<div class="quarter bold">Claim Type<span style="color:red">*</span></div>
								<form:select path="claimType" id="claimType" cssStyle="width:145px;">
						     	<form:option value="select">select</form:option>
						     	<form:options items="${sessionScope.claimTypeMasterList}" itemLabel="claimType" itemValue="id"/>
                                </form:select>
								</div>
								<div class="line">
								<div class="quarter bold">Claim Name<span style="color:red">*</span></div>
								<div class="quarter"><form:input path="claimName" id="claimName"/> </div>
								</div>
								<div class="line">
										<div style="margin-left:50%">
										<a href="javascript:submitTuitionFeeCliamDetails();" class="appbutton">Submit</a>
										<a href="javascript:clearTuitionFeeCliamDetails();" class="appbutton">Clear</a>
										</div>
									</div>
									<div class="line" id="result"><jsp:include page="tutionFeeClaimMasterList.jsp"/>
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
		<form:hidden path="pk"/>
		</form:form>
	</body>
</html>
<!-- End:tiutionFeeClaimMaster.jsp -->
