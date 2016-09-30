<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:GetEmployeeDetails.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/leave.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>

<title>Employee Leave Card/Account Search</title>
</head>

<body>
	<form:form method="post" commandName="leaveAdmin">
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
								<div class="headTitle">Employee Leave Card / Account Search</div>
								<%-- Content Page starts --%>
								<div class="line">
									<div class="line">
										<div class="quarter leftmar">SFID<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="searchSfid" id="searchSfid" maxlength="10" onblur="javascript:checkEmployee();" onkeypress="javascript:return isAlphaNumaricExp(event);"/></div>
										<div class="half" id="result"></div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Type<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:radiobutton path="type" id="type1" label="Card" value="1" />&nbsp;
											<form:radiobutton path="type" id="type0" label="Account" value="0" />&nbsp;
										</div>
									</div>	
									<div class="line">									
										<div class="quarter leftmar">Year<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="yearID" id="yearID" cssClass="formSelect">
												<form:option value="0">Select</form:option>
												<%-- <form:options items="${leaveAdmin.yearsList}" itemValue="id" itemLabel="name"/> --%>
											</form:select>
										</div>
									</div>
									<div class="line">
										<div style="float:left;margin-left:25%;"><a href="javascript:getLeaveReport();" class="appbutton" style="text-decoration: none;float:right">Submit</a></div>
										<div style="float:left;"><a href="javascript:resetLeaveAccountSearch();" class="appbutton" style="text-decoration: none;float:right">Clear</a></div>
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
		<form:hidden path="type"/>
		<form:hidden path="param"/>	
		</form:form>		
	</body>
</html>
<!-- End:GetEmployeeDetails.jsp -->