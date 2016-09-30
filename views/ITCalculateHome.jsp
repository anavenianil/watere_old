<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:ITCalculateHome.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
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
<script type="text/javascript" src="script/incomeTax.js"></script>
<title>IT Calculation</title>
</head>

<body onload="javascript:clearITcalcDetails();">
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
								<div class="headTitle">Income Tax Calculation Details</div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle"/>&nbsp;Loading...</div>
								<div>
								<div id="result">${incomeTaxMaster.remarks}</div>
									<%-- Content Page starts --%>
								<div class="line">
										<div class="quarter leftmar">Financial Year<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="selectedFYear" id="selectedFYear"  cssClass="formSelect" >
												<form:option value="select">Select</form:option>
												<form:options items="${sessionScope.FinYearList}" itemValue="id" itemLabel="fyTo" itemLabel="fyToFrom" />
											</form:select>
										</div>
								</div>
								<div class="line">
							  			<div class="quarter leftmar">Type<span class="mandatory">*</span></div>
							  			<div class="half">
							  				<form:radiobutton path="runTypeFlag" label="Planned" value="pl" id="runTypeFlag1" /> 
	         								<form:radiobutton path="runTypeFlag" label="Projected" value="pr" id="runTypeFlag2" />
	         								<form:radiobutton path="runTypeFlag" label="Actual" value="actl" id="runTypeFlag3" /> 
	         								
							  			</div>
							  		</div>
							  		<div class="line" id="runFor">
										<div class="quarter leftmar">Run For<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="runAllOrInd" id="runAllOrInd"  cssClass="formSelect"  onchange="javascript:displaySfid();">
												<form:option value="all" >ALL EMPLOYEES</form:option>
												<form:option value="ind">INDIVIDUAL</form:option>
												</form:select>
										</div>
								  </div>
								  <div class="line" id="dispSfid">
										<div class="quarter leftmar">Enter SFID<span class="mandatory">*</span></div>
										<div class="quarter" ><form:input path="selectSfid" id="selectSfid"/></div>
								  </div>
								  <script>$jq('#dispSfid').hide();
								          $jq('#runFor').hide();
								  </script>
								    <div class="line">
										<div style="margin-left:50%">
										<a href="javascript:runITcalcDetails();" class="appbutton">Run</a>
										</div>
									</div>
									<div class="line" id="keylist">
										<jsp:include page="incomeTaxClaculateList.jsp"></jsp:include>
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
<!-- End:ITCalculateHome.jsp -->