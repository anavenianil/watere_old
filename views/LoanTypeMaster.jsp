<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LoanTypeMaster.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
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
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/loan.js"></script>


<title>Loan-Advance Type Master</title>
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
								<div class="headTitle" >Loan-Advance Type Master</div>
								
								<%-- Content Page starts --%>
								<div class="line">
									<div class="line">
										<div class="quarter leftmar">Loan/Advance Name<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:input path="loanName" id="loanName" maxlength="30" onkeypress="return checkSpace(event);"/>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Loan/Advance Type<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="loanType" id="loanType" cssClass="formSelect">
												<form:option value="0">Select</form:option>
												<form:option value="46">Advance</form:option>
												<form:option value="45">Withdrawal</form:option>
												<form:option value="47">Both</form:option>
											</form:select>
										</div>
									</div>									
									<div class="line">
										<div style="margin-left:25%;">
											<a href="javascript:manageLoanTypeMaster();" class="appbutton">Submit</a>
											<a href="javascript:clearLoanTypeMaster();" class="appbutton">Clear</a>
										</div>										
									</div>	
									<div class="line" id="result">
										<jsp:include page="LoanTypeMasterList.jsp"></jsp:include>
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
<!-- End:LoanTypeMaster.jsp -->