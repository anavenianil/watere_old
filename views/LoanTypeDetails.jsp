<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LoanTypeDetails.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ page import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.loan.beans.dto.LoanFestivalMasterDTO"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>


<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/calendar-hd.js"></script>
<script language="javascript" src="./script/RegExpValidate.js"></script>	
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/loan.js"></script>

<title>Loan Type Details</title>
</head>

<body onload="javascript:multiSelectBox(),clearLoanDetails()">
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
								<div class="headTitle">Loan Type Details</div>
								<%-- Content Page starts --%>
							  	<div class="line">
							  		<div class="line">
							  			<div class="quarter leftmar">Loan Type<span class="mandatory">*</span></div>
							  			<div class="quarter">
							  				<form:select path="loanType" id="loanType"  cssClass="formSelect required" onmouseover="setSelectWidth('#loanType')" onchange="javascript:selectedSubLoan()">
                                               <form:option value="0">Select</form:option>
                                               <form:options items="${loan.loanTypeMasterList}" itemValue="id" itemLabel="loanName" />
                                            </form:select>
							  			</div>
							  			</div>
							  			<div class="line" id="LoanSubTypeDiv" style="display:none">
							  			<div class="quarter leftmar">Loan SubType<span class="mandatory">*</span></div>
							  			<div class="quarter">
							  				<form:select path="loanSubType" id="loanSubType"  cssClass="formSelect required" onmouseover="setSelectWidth('#loanSubType')">
                                            </form:select>
							  			</div>
							  		</div>
							  		
							  		<div class="line">
							  			<div class="quarter leftmar">Minimum Service<span class="mandatory">*</span></div>
							  			<div class="quarter">
							  				<form:input path="experience" id="experience" onkeypress="return checkFloat(event,'experience');"/> Years
							  			</div> 
							  		
							  		</div>		
							  		<div class="line">
							  			<div class="quarter leftmar">Impact on time period<span class="mandatory">*</span></div>
							  			<div class="quarter">
							  				<form:radiobutton path="periodTypeFlag" label="Yes" value="Y" id="periodTypeFlag" onclick="javascript:impactOnTimePeriod();"/> 
	         								<form:radiobutton path="periodTypeFlag" label="No" value="N" id="periodTypeFlag" onclick="javascript:impactOnTimePeriod();"/>
							  			</div>
							  		</div>
							  		<div class="line" id="periodDiv" style="display:none">
							  			<div class="line">
								  			<div class="quarter leftmar">From Date<span class="mandatory">*</span></div>
								  			<div class="quarter">
									  			<form:input path="fromDate" id="fromDate" readonly="true"/>
									  			<img  src="./images/calendar.gif" id="fromDateImg" />	
												<script type="text/javascript">
													Calendar.setup({inputField :"fromDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"fromDateImg",singleClick : true,step : 1});
												</script>
									  		</div>
									  	</div>
								  		<div class="line">
								  			<div class="quarter leftmar">To date<span class="mandatory">*</span></div>
								  			<div class="quarter">
								  				<form:input path="toDate" id="toDate" readonly="true"/>
								  				<img  src="./images/calendar.gif" id="toDateImg" />	
												<script type="text/javascript">
													Calendar.setup({inputField :"toDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"toDateImg",singleClick : true,step : 1});
												</script>
								  			</div>
								  		</div>
								  	</div>
								  	<div class="line">
							  			<div class="quarter leftmar">Recovery<span class="mandatory">*</span></div>
							  			<div class="quarter">
							  				<form:radiobutton path="recoveryFlag" label="Yes" value="Y" id="recoveryFlag" onclick="javascript:recovery();"/> 
	         								<form:radiobutton path="recoveryFlag" label="No" value="N" id="recoveryFlag" onclick="javascript:recovery();"/>
						  				</div>
						  			</div>
						  			<div class="line" id="RecoveryDiv" style="display:none">
									  	<div class="line">
								  			<div class="quarter leftmar">Minimum Principle Installments<span class="mandatory">*</span></div>
								  			<div class="quarter">
								  				<form:input path="minInstallments" id="minInstallments"/>
								  			</div>
								  		</div>
								  		<div class="line">
								  			<div class="quarter leftmar">Maximum Principle Installments<span class="mandatory">*</span></div>
								  			<div class="quarter">
								  				<form:input path="maxInstallments" id="maxInstallments"/>
								  			</div>
								  		</div>
								  		<div class="line">
								  			<div class="quarter leftmar">Minimum Interest Installments<span class="mandatory">*</span></div>
								  			<div class="quarter">
								  				<form:input path="minInterestInstallments" id="minInterestInstallments"/>
								  			</div>
								  		</div>
								  		<div class="line">
								  			<div class="quarter leftmar">Maximum Interest Installments<span class="mandatory">*</span></div>
								  			<div class="quarter">
								  				<form:input path="maxInterestInstallments" id="maxInterestInstallments"/>
								  			</div>
								  		</div>
									  	<div class="line">
								  			<div class="quarter leftmar">Recovery Starts From<span class="mandatory">*</span></div>
								  			<div class="quarter">
								  				<form:input path="noOfMonths" id="noOfMonths"/> Months
								  			</div>
									  	</div>
								  		<div class="line">
								  			<div class="quarter leftmar">Maximum recovery period<span class="mandatory">*</span></div>
								  			<div class="quarter">
								  				<form:input path="maxRecoveryPeriod" id="maxRecoveryPeriod"/> Months
								  			</div>
								  		</div>
								  		<div class="line">
								  			<div class="quarter leftmar">Interest Rate<span class="mandatory">*</span></div>
								  			<div class="quarter">
								  				<form:input path="interestRate" id="interestRate" onkeypress="return checkFloat(event,'interestRate');"/> %
								  			</div>
								  		</div>
							  		</div>
							  		<div class="line">
							  			<div class="quarter leftmar">Impact on leave<span class="mandatory">*</span></div>
							  			<div class="quarter">
							  				<form:radiobutton path="impactOnLeaveFlag" label="Yes" value="Y" id="impactOnLeaveFlag" onclick="javascript:impactOnLeave();"/> 
	         								<form:radiobutton path="impactOnLeaveFlag" label="No" value="N" id="impactOnLeaveFlag" onclick="javascript:impactOnLeave();"/>
							  			</div>
							  		</div>
							  		<div class="line" id="LeaveDiv" style="display:none">
								  		<div class="line">
											<div class="leftmar">
												<div style="float: left; width: 45%;">De Selected Leaves</div>
												<div class="half" >Selected Leaves</div>
											</div>
										</div>
										
										<div class="line">
											<div class="leftmar">
												<div style="float: left; width : 35%">
														<form:select path="leave" id="SelectLeft" size="10" multiple="true" cssStyle="width:300px">
															<form:options items="${loan.leaveTypesList}" itemValue="id" itemLabel="leaveType"/>
														</form:select>
													</div>
												<div style="float: left; width : 10%; margin-top: 60px;">									
													<div style="margin-bottom: 5px;" align="center">
													     <input style="margin-bottom: 5px" id="MoveRight" type="button" value=" Add " />
	     											     <input id="MoveLeft" type="button" value=" Remove " />    											        
	     											</div>		      																				
		      									</div>
												<div style="float: left; width : 30%">
														<form:select path="leave" id="SelectRight" size="10" multiple="true" cssStyle="width:300px">
														</form:select>
													</div>
											</div>
										</div>
							  		</div>
							  						
									<div class="line">
										<div style="margin-left:24%;">
											<div class="expbutton"><a href="javascript:manageLoanDetails();"> <span>Submit</span></a></div>
										</div>
										<div class="expbutton"><a href="javascript:clearLoanDetails();"><span>Clear</span></a></div>
									</div>	
									<div class="height"><hr/></div>
							  								
									<div class="line" id="LoanTypeList">
									     <jsp:include page="LoanTypeDetailsList.jsp"/>	
									</div>
								
							  </div>
							  <script>
							  		$jq('#loanType >option').each(function(){if($jq(this).val()== HBAID ){$jq(this).remove();}});
									jsonLoanSubType =<%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<LoanFestivalMasterDTO>)session.getAttribute("loanSubTypeListJSON")) %>;
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
<!-- End:LoanTypeDetails.jsp -->