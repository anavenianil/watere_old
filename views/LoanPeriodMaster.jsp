<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LoanPeriodMaster.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/loan.js"></script>
<title>Loan Period Master</title>
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
								<div class="headTitle" >Loan Period Master</div>
								
								<%-- Content Page starts --%>
								<div class="line">
										<div class="quarter leftmar">Loan Type<span class="mandatory">*</span></div>
										<form:select path="loanTypeID" id="loanTypeID" cssClass="formSelect" onchange="javascript:selectedLoanType(value)">
										<form:option value="0">Select</form:option>
										<form:options items="${sessionScope.LoanTypeMasterList}" itemValue="id" itemLabel="loanName" />
										</form:select>
									</div>
								
								<div class="line">
										<div class="quarter leftmar">Sub Type<span class="mandatory">*</span></div>
										<form:select path="subType" id="subType" cssClass="formSelect">
										<form:option value="">Select</form:option>
										</form:select>
									</div>
									
									<div class="line">
										<div class="quarter leftmar">From Date<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="fromDate" id="fromDate" cssClass="required" readonly="true"/>
										<img  src="./images/calendar.gif"   id="date_start_trigger1" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"fromDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
											</script>
										</div>
									</div>
							
									
										<div class="line">
										<div class="quarter leftmar">To Date<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="toDate" id="toDate" cssClass="required" readonly="true"/>
										<img  src="./images/calendar.gif"   id="date_start_trigger" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"toDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger",singleClick : true,step : 1});
											</script>
										</div>
									</div>
									<div class="line">
										<div style="margin-left:25%;">
										<a href="javascript:manageLoanPeriodMaster();" class="appbutton">SUBMIT</a>
										<a href="javascript:clearLoanPeriodMaster();" class="appbutton">CLEAR</a>
										</div>										
								</div>	
								<div class="line" id="LoanPeriodMasterList">
								<jsp:include page="loanPeriodMasterList.jsp"></jsp:include>
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
<!-- End:LoanPeriodMaster.jsp -->