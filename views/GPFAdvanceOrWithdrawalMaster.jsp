<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:GPFAdvanceOrWithdrawalMaster.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />

<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/loan.js"></script>
<title>GPF Advance/Withdrawal Purpose Master</title>
</head>

<body>
	<form:form method="post" commandName="loan">
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
								<div class="headTitle" id="title">GPF Advance/Withdrawal Purpose Master</div>
								<%-- Content Page starts --%>
								<div class="line">
									<div class="line">
										<div class="quarter bold">Type of GPF<span class="mandatory">*</span></div>
					     					<div class="quarter bold">
												<form:select path="gpfType" id="gpfType" cssClass="formSelect" >
													<form:option value="select">Select</form:option>
													<form:options items="${sessionScope.gpfTypeList}" itemValue="id" itemLabel="name"/>
												</form:select>
											</div>
									</div>
									<div class="line">
										<div class="quarter bold">Name of Purpose<span class="mandatory">*</span></div>
										<div class="half">
											<form:input path="purposeName" id="purposeName" maxlength="100" onkeypress="javascript:increaseTextWidth('purposeName');return checkChar(event,'purposeName');"/>
										</div>
									</div>
									<div class="line">
										<div class="quarter bold">Rule under which the request is covered <span class="mandatory">*</span></div>
										<div class="half">
											<form:input path="ruleNo" id="ruleNo" maxlength="100"/>
										</div>
									</div>
									<div class="line">
										<div style="margin-left:24%;">
											<div class="expbutton"><a href="javascript:submitGPFAdvanceOrWithdrawlDetails();"> <span>Submit</span></a></div>
										</div>
										<div class="expbutton"><a href="javascript:resetGPFAdvanceOrWithdrawlDetails();"><span>Clear</span></a></div>
									</div>	
								</div>
								<div class="height"><hr/></div>
								<div class="line" id="result">
								    	<jsp:include page="GPFAdvanceOrWithdrawalMasterList.jsp"/>	
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
		<form:hidden path="param"/>	
		<form:hidden path="type"/>	
		</form:form>		
	</body>
</html>
<!-- End:GPFAdvanceOrWithdrawalMaster.jsp -->