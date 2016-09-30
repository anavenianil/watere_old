<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:ErpLoanTypeMaster.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript"
	src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/erpLoan.js"></script>

<title>Loan Type Master</title>
</head>
<body>
	<form:form method="post" commandName="erpLoan">
		<div></div>
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
							<div class="headTitle">Loan Type Master</div>
							<!-- Content Page starts -->
							<div class="line">
								<div class="line">
									<div class="quarter leftmar">
										Loan Type<span class="mandatory">*</span>
									</div>
									<div class="quarter">
										<form:input path="loanType" id="loanType" maxlength="30"
											onkeypress="return checkSpace(event);" />
									</div>
								</div>
								<div class="line">
									<div style="margin-left: 25%;">
										<a href="javascript:manageLoanPurpose();" class="appbutton">Submit</a>
										<a href="javascript:clearLoanPurpose();" class="appbutton">Clear</a>
									</div>
								</div>
								<div class="line" id="result">
									<jsp:include page="ErpLoanPurposeList.jsp"></jsp:include>
								</div>
							</div>
							<!-- Content Page end -->
							<div>&nbsp;</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<form:hidden path="param" />
		<%-- <form:hidden path="type" /> --%>
		<%-- <form:hidden path="requestID" />
		<form:hidden path="requestId" /> --%>
	</form:form>
</body>


</html>

