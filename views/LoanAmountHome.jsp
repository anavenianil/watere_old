<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LoanAmountHome.jsp -->
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

<title>Loan Amount Details</title>
</head>

<body onload="javascript:multiSelectBox();clearLoanAmountDetails();">
	<form:form method="post" commandName="loan" >
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
								<div class="headTitle">Loan Amount Details</div>
								<%-- Content Page starts --%>
								<div>								
									
									<div class="line">
							  			<div class="quarter leftmar">Loan Type<span class="mandatory">*</span></div>
							  			<div class="quarter">
							  				<form:select path="loanType" id="loanType"  cssClass="formSelect required" onmouseover="setSelectWidth('#loanType')">
                                               <form:option value="0">Select</form:option>
                                               <form:options items="${loan.loanTypeMasterList}" itemValue="id" itemLabel="loanName" />
                                            </form:select>
							  			</div>
							  			</div>
							  		<%--	<div class="line" id="LoanSubTypeDiv" style="display:none">
							  			<div class="quarter leftmar">Loan SubType<span class="mandatory">*</span></div>
							  			<div class="quarter">
							  				<form:select path="loanSubType" id="loanSubType"  cssClass="formSelect required" onmouseover="setSelectWidth('#loanSubType')">
                                            </form:select>
							  			</div>
							  		</div> --%>
									
									<div class="line">
										<div class="quarter leftmar">Gazetted Type<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="gazType" id="gazType" cssClass="formSelect" onchange="javascript:selectedDesignation()">
												<form:option value="0">Select</form:option>
												<form:option value="GAZ">Gazetted</form:option>
												<form:option value="NG">Non-Gazetted</form:option>												
											</form:select>	
										</div>
									</div>
									<div class="line">
										<div class="leftmar">
											<div style="float: left; width: 45%;">De Selected Desig</div>
											<div class="half" >Selected Desig<span class="mandatory">*</span></div>
										</div>
									</div>
									<div class="line">
										<div class="leftmar">
											<div style="float: left; width : 35%">
												<form:select path="designation" id="SelectLeft" size="10" multiple="true" cssStyle="width:300px">
													<form:options items="${loan.designationList}" itemValue="id" itemLabel="name" />
												</form:select>
											</div>
											<div style="float: left; width : 10%; margin-top: 60px;">									
												<div style="margin-bottom: 5px;" align="center">
												     <input style="margin-bottom: 5px" id="MoveRight" type="button" value=" Add " />
     											     <input id="MoveLeft" type="button" value=" Remove " />    											        
     											</div>		      																				
	      									</div>
											<div style="float: left; width : 30%">
												<form:select path="designation" id="SelectRight" size="10" multiple="true" cssStyle="width:300px">
												</form:select>
											</div>
										</div>
									</div>
									<div class="line">
							  			<div class="quarter leftmar">Impact on Pay<span class="mandatory">*</span></div>
							  			<div class="quarter">
							  				<form:radiobutton path="impactOnPayFlag" label="Yes" value="Y" id="impactOnPayFlag" onclick="javascript:impactOnPay();"/> 
	         								<form:radiobutton path="impactOnPayFlag" label="No" value="N" id="impactOnPayFlag" onclick="javascript:impactOnPay();"/>
							  			</div>
							  		</div>
							  		<div class="line">
							  			<div class="quarter leftmar">Impact on No.of Times<span class="mandatory">*</span></div>
							  			<div class="quarter">
							  				<form:radiobutton path="impactOnTimesFlag" label="Yes" value="Y" id="impactOnTimesFlag" onclick="javascript:impactOnTime();"/> 
	         								<form:radiobutton path="impactOnTimesFlag" label="No" value="N" id="impactOnTimesFlag" onclick="javascript:impactOnTime();"/>
							  			</div>
							  		</div>
							  		<div class="line">
							  			<div class="quarter leftmar">Impact on DA<span class="mandatory">*</span></div>
							  			<div class="quarter">
							  				<form:radiobutton path="impactOnDAFlag" label="Yes" value="Y" id="impactOnDAFlag" onclick="javascript:impactOnTimeDA('');"/> 
	         								<form:radiobutton path="impactOnDAFlag" label="No" value="N" id="impactOnDAFlag" onclick="javascript:impactOnTimeDA('');"/>
							  			</div>
							  			<div id="daPercentDiv" style="display:none"><form:input path="daPercentage" id="daPercentage" value="1" />%</div>
							  		</div>
							  		<div class="line">
							  			<div class="quarter leftmar">Impact on Balance<span class="mandatory">*</span></div>
							  			<div class="quarter">
							  				<form:radiobutton path="impactOnBalanceFlag" label="Yes" value="Y" id="impactOnBalanceFlag" onclick="javascript:impactOnBalance();"/> 
	         								<form:radiobutton path="impactOnBalanceFlag" label="No" value="N" id="impactOnBalanceFlag" onclick="javascript:impactOnBalance();"/>
							  			</div>
							  		</div>
							  		<div class="line">
							  			<div class="quarter leftmar">Impact on No.of Months Pay<span class="mandatory">*</span></div>
							  			<div class="quarter">
							  				<form:radiobutton path="impactOnNoOfMonthsPayFlag" label="Yes" value="Y" id="impactOnNoOfMonthsPayFlag" onclick="javascript:impactOnNoOfMonthsPay();"/> 
	         								<form:radiobutton path="impactOnNoOfMonthsPayFlag" label="No" value="N" id="impactOnNoOfMonthsPayFlag" onclick="javascript:impactOnNoOfMonthsPay();"/>
							  			</div>
							  		</div>
									<div class="line">
										<div style="margin-left:30%;">
											<a href="javascript:manageLoanAmountDetails();" class="appbutton">Submit</a>
											<a href="javascript:clearLoanAmountDetails();" class="appbutton">Clear</a>
										</div>										
									</div>
									<div class="line" id="grid">
										
									</div>
									
									<div id="loanAmountDetails" class="line">
										<jsp:include page="LoanAmountDetailsList.jsp" />
									</div>
								</div>
								<script>
							  		$jq('#loanType >option').each(function(){if($jq(this).val()=='9'){$jq(this).remove();}});
							  		
							  									  	</script>
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
<!-- End : LoanAmountHome.jsp -->