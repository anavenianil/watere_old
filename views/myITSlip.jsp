<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:myITSlip.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

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
<script type="text/javascript" src="script/report.js"></script>
<title>IT Slip</title>
</head>

<body>
	<form:form method="post" commandName="incomeTaxMaster">
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
								<div class="headTitle">IT Report Details</div>
								<div>
									<%-- Content Page starts --%>
								<c:if test="${incomeTaxMaster.param eq 'myITSlipFinance'}">
								<div class="line" id="reportSfid">
									<div class="quarter leftmar">SFID<span class="failure">*</span></div>
									<div class="quarter"><form:input path="searchSfid" id="searchSfid"></form:input></div>
								</div>
								</c:if>
								<div class="line">
										<div class="quarter leftmar">Financial Year<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="selectedFYear" id="selectedFYear"  cssClass="formSelect" >
												<form:option value="select">Select</form:option>
												<form:options items="${sessionScope.FinYearList}" itemValue="id" itemLabel="fyToFrom" />
											</form:select>
										</div>
								</div>
								<div class="line">
							  			<div class="quarter leftmar">Type<span class="mandatory">*</span></div>
							  			<div class="half">
							  				<form:radiobutton path="runTypeFlag" label="Planned" value="pl" id="runTypeFlag" /> 
	         								<form:radiobutton path="runTypeFlag" label="Projected" value="pr" id="runTypeFlag" />
	         								<form:radiobutton path="runTypeFlag" label="Actual" value="actl" id="runTypeFlag" /> 
	         								
							  			</div>
							  		</div>
							  		<div class="line">
							  		<div class="quarter leftmar">Report Type<span class="mandatory">*</span></div>
							  		<div class="half">
							  		<form:radiobutton path="reportTypeFlag" label="IT Statement" value="1" id="reportTypeFlag"/>
							  		<form:radiobutton path="reportTypeFlag" label="Form 16" value="2" id="reportTypeFlag"/>
							  		</div>
							  		</div>
							  		
							  		<c:if test="${incomeTaxMaster.param eq 'myITSlipHome'}">
							  		<div class="line">
										<div style="margin-left:50%">
										<a href="javascript:getMyITSlip('${incomeTaxMaster.sfID}');" class="appbutton">Print</a>
										</div>
									</div>
							  		</c:if>
							  		<c:if test="${incomeTaxMaster.param eq 'myITSlipFinance'}">
							  		<div class="line">
										<div style="margin-left:50%">
										<a href="javascript:getMyITSlipFinance();" class="appbutton">Print</a>
										</div>
									</div>
							  		</c:if>
							  		
							  	    
								
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
	</body>
</html>
<!-- End:myITSlip.jsp -->