<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LoanFundRelief.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" rel="stylesheet"  type="text/css" />
<link href="styles/displaytag.css" rel="stylesheet" type="text/css"  />

<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/loan.js"></script>
<script type="text/javascript" src="./script/script.js"></script>

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>

<script language="javascript" src="./script/RegExpValidate.js"></script>	

<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>

<title>Immediate Fund Relief</title>
</head>

<body >
	<form:form method="post" id="fund" >
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
								<div class="headTitle">Immediate Fund Relief</div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</div>
								
								<%-- Content Page starts --%>
								<div class="line">
								     <div class="line">
										<div class="quarter bold" style="margin-left:8px;"  >SFID<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:input path="sfID" id="sfID" maxlength="10" cssClass="required" onkeyup="javascript:changeToUpper(this);" onchange="javascript:getEmployeeNames(this.value);"/>
											</div>
											<div id="nameDiv"></div>
									</div>
						    	    <div class="line">
										<div class="quarter bold" style="margin-left:8px;" >Date of Death<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="dateofDeath"  id="dateofDeath" readonly="true" cssClass="required" onclick="javascript:clearDateText('dateofDeath');"/>
												<img  src="./images/calendar.gif"   id="date_start_trigger4" />	
												<script type="text/javascript">
												Calendar.setup({inputField :"dateofDeath",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger4",singleClick : true,step : 1});
												</script></div>
								    </div>
									<div class="line">
										<div class="quarter bold" style="margin-left:8px;" >Relief Amount<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="reliefAmount" id="reliefAmount" maxlength="10" cssClass="required number" /></div>
									</div>
									<div class="line">
										<div class="quarter bold" style="margin-left:8px;" >Approved By<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="approvedBy" id="approvedBy" maxlength="10" cssClass="required"/></div>
									</div>
									<div class="line">
										<div style="margin-left:24%;" class="expbutton"><a href="javascript:manageFund();"><span>Submit</span></a></div>
										<div class="expbutton"><a href="javascript:clearFund();"><span>Clear</span></a></div>
									</div>	
									
									<div class="line" id="loanFundList">
									    <div class="line">
									      		 <jsp:include page="LoanFundReliefList.jsp"/>
									    </div>
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
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		</form:form>
	</body>
</html>
<!-- End:LoanFundRelief.jsp -->