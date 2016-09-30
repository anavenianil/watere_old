<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:GPFClosingBalance.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<!-- <link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/> -->
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<!-- <script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script> -->
<script type="text/javascript" src="script/loan.js"></script>
<link href="styles/calendar.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="script/calendar-hd.js"></script>
<script type="text/javascript" src="script/calendar_us.js"></script>
<title>GPF Yearly Closing Balance Master</title>
</head>

<body>
	<form:form method="post" commandName="loan" name="gpfclosing">
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
								<div class="headTitle">GPF Yearly Closing Balance Master</div>
								<%-- Content Page starts --%>
								<div class="line">
									<div class="line">
										<div class="line">
											<div class="quarter leftmar">SFID<span class="mandatory">*</span></div>
											<div class="half">
												<form:input path="changeSfID" id="changeSfID" maxlength="100"/>
											</div>
										</div>
										<div class="line">
											<div class="quarter leftmar">Closing Balance<span class="mandatory">*</span></div>
											<div class="half">
												<form:input path="balance" id="balance" maxlength="10" onkeypress="return checkFloat(event,'balance');"/>
											</div>
										</div>
											
                                       	<div class="line">
		                                 	<div class="quarter leftmar">From Date<span class="mandatory">*</span></div>
	                                  		<div class="quater">
	                                   			<form:input path="fromDate" id="fromDate" cssClass="dateClass" readonly="true" />
				                               	<script type="text/javascript">
											         new tcal({'formname':'gpfclosing','controlname':'fromDate'});
											</script>
	                                  		</div>
                                       	</div>	
                                       	<div class="line">
							             	<div class="quarter leftmar">To Date<span class="failure">&nbsp;*</span></div>
								          	<div class="quarter">
									       		<form:input path="toDate" id="toDate" cssClass="dateClass" readonly="true" />
									        		<script type="text/javascript">
											         new tcal({'formname':'gpfclosing','controlname':'toDate'});
											</script>
								           </div>
							        	</div>	
										
										<div class="line">
											<div style="margin-left:24%;">
												<div class="expbutton"><a href="javascript:saveGpfClosingBalance();"> <span>Submit</span></a></div>
												<div class="expbutton"><a href="javascript:resetGpfClosingBalance();"><span>Clear</span></a></div>
												
											</div>											
										</div>	
									</div>
									<div class="height"><hr/></div>
									<div class="line" id="result">
								    	<jsp:include page="GPFClosingBalanceList.jsp"/>	
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
<!-- End:GPFClosingBalance.jsp -->