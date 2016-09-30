<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:FinancialYearMaster.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script language="javascript" src="./script/RegExpValidate.js"></script>	

<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/loan.js"></script>

<title>Financial Year Master</title>
</head>

<body >
	<form:form method="post" id="financeMaster" >
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
								<div class="headTitle">Financial Year Master</div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</div>
								<%-- Content Page starts --%>
							  <div class="line">
							  	<div class="line">
									<div class="quarter bold" style="margin-left:8px;"  >From Year <span class="mandatory">*</span></div>
									<div class="quarter">
										<form:select path="fromYear" id="fromYear" cssClass="formSelect required" >
                                               <form:option value="" disabled="yes">Select</form:option>
                                               <form:options items="${sessionScope.allYearList}" itemLabel="name" itemValue="name"/>
                                            </form:select><span class="failure" id="errmsg0"></span>
									</div>
								
									<div class="quarter bold" style="margin-left:8px;"  >To Year <span class="mandatory">*</span></div>
									<div class="quarter">
										<form:select path="toYear" id="toYear" cssClass="formSelect required" >
                                               <form:option value="" disabled="yes">Select</form:option>
                                               <form:options items="${sessionScope.allYearList}" itemLabel="name" itemValue="name" />
                                            </form:select><span class="failure" id="errmsg0"></span>
									</div>
									
							      </div>
						  		<div class="line">
										<div class="quarter bold" style="margin-left:8px;">Description</div>
										<div class="quarter"><form:textarea path="description" id="description" cols="20" rows="3" ></form:textarea>
										</div>
									</div>
							
								<div class="line">
									<div style="margin-left:24%;">
										<div class="expbutton"><a href="javascript:manageFinancialYearMaster();"> <span>Submit</span></a></div>
									</div>
									<div class="expbutton"><a href="javascript:clearFinancialYearMaster();"><span>Clear</span></a></div>
								</div>	
								<div class="height" ><hr/></div>
							  							
								<div class="line" id="FinancialYearList">
								    <jsp:include page="FinancialYearList.jsp"/>		
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















<!-- End:FinancialYearMaster.jsp -->