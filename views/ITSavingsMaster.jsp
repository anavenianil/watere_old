<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:ITSavingsMaster.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script type="text/javascript" src="script/incomeTax.js"></script>

<title>IT Savings</title>
</head>

<body onload="javascript:clearSavingsDetails();">
	<form:form method="post" commandName="incomeTaxMaster" id="incomeTaxMaster">
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
								<div class="headTitle">Income Tax Savings/ Exemptions /Other Income Master</div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</img></div>
								<div>
									<%-- Content Page starts --%>
								<div class="line">
										<div class="quarter leftmar">Name<span class="mandatory">*</span></div>
										<div class="quarter" ><form:input path="sName" id="sName" maxlength="200" onkeypress="javascript:increaseTextWidth('sName');" onmouseover="setSelectWidth('#sName');" /></div>
									 
								</div>
									<div class="line">
									<div class="quarter leftmar">Type<span class="mandatory">*</span></div>
									<div class="half">
											<form:radiobutton path="sType" label="Savings" value="Savings" id="sType1"/> 
	         								<form:radiobutton path="sType" label="Exemptions" value="Exemptions" id="sType2"/>
	         							    <form:radiobutton path="sType" label="Other Income Sources" value="Other Income Sources" id="sType3"/>
	         							</div>
	         						</div>
	         						<div class="line">
										<div class="quarter leftmar">Section Name<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="sectionId" id="sectionId"  cssClass="formSelect">
												<form:option value="select">Select</form:option>
												<form:options items="${sessionScope.SectionList}" itemValue="id" itemLabel="secName" />
											</form:select>
											
										</div>
								</div>
									<div class="line">
										<div style="margin-left:50%">
										<a href="javascript:submitSavingsDetails();" class="appbutton">Submit</a>
										<a href="javascript:clearSavingsDetails();" class="appbutton">Clear</a>
										</div>
									</div>
									<div class="line" id="result"><jsp:include page="ITSavingsMasterList.jsp"></jsp:include></div>
									
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
<!-- End:ITSavingsMaster.jsp -->