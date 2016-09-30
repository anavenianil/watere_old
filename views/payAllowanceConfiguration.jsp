<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:payAllowanceConfiguration.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/payBill.js"></script>

<title>Pay Bill </title>
</head>
<body onload="javascript:clearAllowanceCgfDetails(); multiSelectBox();">
	<form:form method="post" commandName="payBillMaster" id="PayBillMasterBean">
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
								<div class="headTitle">Pay Allowance Configuration Master</div>
								<div class="line">
									<%-- Content Page starts --%>
									<div class="line">
										<div class="half" style="padding-left: 70%;width: 280px;"><strong>Pay Run Month:&nbsp;&nbsp;<font color="green">${payBillMaster.payRunMonth}</font></strong></div>
									</div>
									<div id="result" class="line"></div>
									<div class="line">
										<div class="quarter leftmar">Allowance Name<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="allowanceID" id="allowanceID"  cssClass="formSelect" onchange="javascript:selectAllowanceType()" >
												<form:option value="select">Select</form:option>
												<form:option value="WASHALLOWANCE">Washing Allowance</form:option>
												<form:option value="SPLALLOWANCE">Special Pay</form:option>
											</form:select>
										</div>
									</div>
									<div  class="line" id="result1">
									<div class="line">
											<div class="leftmar">
												<div style="float: left; width: 38%;">De Selected </div>
												<div class="half" >Selected</div>
											</div>
									</div>
									<div class="line">
											<div class="leftmar">
												<div style="float: left; width : 28%">
													<form:select path="fromID" id="SelectLeft" size="10" multiple="true" cssStyle="width:250px"></form:select>
												</div>
												<div style="float: left; width : 10%; margin-top: 60px;">									
														<div style="margin-bottom: 5px;" align="center">
													          <input style="margin-bottom: 5px"id="MoveRight" type="button" value=" Add " />
      											              <input id="MoveLeft" type="button" value=" Remove " />    											        
      											        </div>		      																				
		      									</div>
												<div style="float: left; width : 35%">
													<form:select path="toID" id="SelectRight" size="10" multiple="true" cssStyle="width:250px"></form:select>
												</div>
											</div>
									</div>
									</div>
									<div class="line">
										<div style="margin-left:45%">
											<div class="expbutton"><a href="javascript:submitAllowanceCgfDetails();"><span>Submit</span></a></div>
											<div class="expbutton"><a href="javascript:clearAllowanceCgfDetails();"><span>Clear</span></a></div>
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
		<form:hidden path="pk"/>
		</form:form>
	</body>
</html>
<!-- End: payAllowanceConfiguration.jsp -->