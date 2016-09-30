<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin : ViewAddress.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="./styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script language="javascript" src="script/prototype.js"></script>
<script language="javascript" src="script/aa.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<title>Address Details</title>
</head>
<body>
  
	<form:form method="post" commandName="employee">
	<form:hidden path="param"/>
	<form:hidden path="type"/>
	<form:hidden path="id" id="id"/>
	
	<script>document.title="Change password"</script>
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
					<%-- Content Page starts --%>
					<%--
                      <c:if test="${message=='success'}"><span class="success"><spring:message code="success"/></span></c:if>
                       <c:if test="${message=='failure'}"><span class="failure"><spring:message code="failure"/></span></c:if>
                --%>
	       
					<div>
					<div class="headTitle">Change Password</div>
					<div id="result"><jsp:include page="Result.jsp"></jsp:include></div>
					<div>
						<div id="createAddress" class="line">
							<fieldset><legend><strong><font color='green'>Change Password</font></strong></legend>
							
								<div class="line">
									
									<div class="line"></div>
									 <div class="quarter bold leftmar">New Password<span class="mandatory">*</span></div>
								   <div class="quarter">
										<form:input  path="newpassword" type="password" id="newpassword"  maxlength="15"/>
									</div> 
									
								</div>
								<div class="quarter bold leftmar">Confirm Password<span class="mandatory">*</span></div>
								   <div class="quarter">
										<form:input  path="confirmpassword" id="confirmpassword"  type="password" maxlength="15" />
									</div>
										  
										   
						   </fieldset>
						    </div>
						   </div>
							
						            <div class="line">
						             <div style="margin-left: 26%;" class="appbutton"><a class="quarterbutton" href="javascript:ChangePwd();">Submit</a></div>
									 <div style="margin-left:0%;" class="appbutton"><a class="quarterbutton" href="javascript:clearChange();">Clear</a></div>
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
		</form:form>
	
	</body>
</html>	
		<!-- End : ViewAddress.jsp -->