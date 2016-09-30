<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:MMGConfigurations.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/mmgscript.js"></script>
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<title>MMG Configurations</title>
</head>

<body>
	<form:form method="post" commandName="mmgMaster" name="mmgMaster" id="mmgMaster" action="">
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
								<div class="headTitle" id="headTitle">MMG Configurations</div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</img></div>
								<%-- Content Page starts --%>
								<div>
									<div id="result"></div>
									<div class="line">
										<fieldset><legend><strong><font color='green'>Financial Year</font></strong></legend>
											<div class="line">
												<div class="quarter">Financial Year&nbsp;(YYYY-YY)<span class="failure">*</span></div>
												<div class="quarter">
													<form:input path="financialYear" id="financialYear" maxlength="7"/>	
												</div>
											</div>
											<div class="line">
												<div class="quarter">Description</div>
												<div class="quarter"><form:textarea path="description" id="description" cols="20" rows="3" onkeypress="textCounter(event,$jq('#description'),$jq('#counter'),100);"
													 onkeyup="textCounter(event,$jq('#description'),$jq('#counter'),100);"></form:textarea>
														<input type="text" class="counter" name="counter" value="100" id="counter"/>
												</div>
											</div>
											<div class="line">
												<div style="margin-left:35%;"><a class="quarterbutton appbutton" href="javascript:manageMMGConfig('financialYear');">Submit</a></div>
												<div class="appbutton"><a class="quarterbutton appbutton" href="javascript:clearMMMGConfig('financialYear');">Clear</a></div>
												
											</div>
										</fieldset>
										<fieldset><legend><strong><font color='green'>Demand Purchase Limit</font></strong></legend>
											<div class="line">
												<div class="quarter">Demand Purchase Limit (<b><del>&#2352;</del></b>)<span class="failure">*</span></div>
												<div class="quarter">
													<form:input path="demandPurchaseLimit" id="demandPurchaseLimit" onkeypress="return checkTwoDigitFloat(event,this.id);" maxlength="5"/>
												</div>
											</div>
											<div class="line">
												<div class="quarter">Description</div>
												<div class="quarter"><form:textarea path="purchaseLimitDesc" id="purchaseLimitDesc" cols="20" rows="3" onkeypress="textCounter(event,$jq('#purchaseLimitDesc'),$jq('#counter1'),100);"
													 onkeyup="textCounter(event,$jq('#purchaseLimitDesc'),$jq('#counter1'),100);"></form:textarea>
														<input type="text" class="counter" name="counter1" value="100" id="counter1"/>
												</div>
											</div>
											<div class="line">
												<div style="margin-left:35%;"><a class="quarterbutton appbutton" href="javascript:manageMMGConfig('demandPurchaseLimit');">Submit</a></div>
												<div class="appbutton"><a class="quarterbutton appbutton" href="javascript:clearMMMGConfig('demandPurchaseLimit');">Clear</a></div>
												
											</div>
										</fieldset>
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
		<form:hidden path="param" id="param"/>
		<form:hidden path="type" id="type"/>
		</form:form>
		
		
	</body>
</html>
<!-- End:MMGConfigurations.jsp -->