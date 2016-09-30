<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:HBAInterestRates.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<!-- <link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/> -->

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<!-- <script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/calendar-hd.js"></script> -->
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/loan.js"></script>
<script type="text/javascript" src="script/date.js"></script>
<link href="styles/calendar.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="script/calendar-hd.js"></script>
<script type="text/javascript" src="script/calendar_us.js"></script>

<title>HBAInterestRates</title>
</head>

<body>
	<form:form method="post" commandName="loan" name="HbaInterest">
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
								<div class="headTitle">HBA Interest Rates</div>
								<%-- Content Page starts --%>
								<div>
								<div class="line">
								<div class="quarter leftmar">Applicable Year<span class="mandatory">*</span></div>
							  			<div class="quarter">
							  			<form:input path="hbaInterestYear" id="hbaInterestYear" readonly="true" />
									  			<script type="text/javascript">
											         new tcal({'formname':'HbaInterest','controlname':'hbaInterestYear'});
											</script>
							  			</div>
								</div>
								<div class="line">
								<table style="width:100%;" border="1" class="list" id="HBAInterestGrid">
								<tr>
										<th style="width: 30%;">Lower Amount Limit</th>
										<th style="width: 30%;">Upper Amount Limit</th>
										<th style="width: 30%;">Interest Rate</th>
										<th style="width: 5%;">Add</th>
										<th style="width: 5%;">Remove</th>
								</tr>
								<tr>
										<td style="width: 30%;"><input type="text" onkeypress="return isNumberExp(event);"/></td>
										<td style="width: 30%;"><input type="text" onkeypress="return isNumberExp(event);"/></td>
										<td style="width: 30%;"><input type="text" onkeypress="return isNumberExp(event);"/></td>
										<td style="width: 5%;"><center><input type="button" value="+" onclick="insertHBAInterestRow() " id="add" /></center></td>
										<td style="width: 5%;"></td>
								</tr>
								
								</table>
								</div>
								<div class="line">
										<div style="margin-left:24%;">
											<div class="expbutton"><a href="javascript:manageHBAInterestRates(jsonLoanHBAInterestGridList);"> <span>Submit</span></a></div>
										</div>
										<div class="expbutton"><a href="javascript:clearHBAInterestRates();"><span>Clear</span></a></div>
									</div>	
									<div class="line" id="result">
								    	<jsp:include page="loanHBAInterestGridList.jsp"/>	
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
		<script>
		
			jsonLoanHBAInterestGridList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonLoanHBAInterestGridList") %>;
			
		</script>
		</form:form>								
	</body>
</html>
<!-- End : HBAInterestRates.jsp -->