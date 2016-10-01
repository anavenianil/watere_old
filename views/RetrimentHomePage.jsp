<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin:RetrimentHomePage.jsp-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/tada.js"></script>
<script type="text/javascript" src="script/retriment.js"></script>

<title>Retirement Benefits</title>

</head>
<body>
	<form:form method="post" commandName="retriment" id="retriment">
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
								<div class="headTitle">Retirement Benefits</div>
								<%-- Content Page starts --%>
								<div class="line">
									<div class="quarter leftmar">
										Employee ID<span class="mandatory">*</span>
									</div>
									<div class="quarter">
										<form:input path="changeSfid" id="changeSfid" placeholder="PleseEnter EmployeeID"/>

									</div>
									<div class="appbutton">
										<a href="javascript:changeRetrimentDetails();"
											style="text-decoration: none">Search</a>
									</div>
								</div>
								<hr></hr>
								<div class="line">
									<div class="quarter leftmar">
										Employee ID<span class="mandatory">*</span>
									</div>
									<div class="quarter">
										<form:input path="sfID" id="sfID" readonly="true"/>
										&nbsp;
									</div>
								</div>
								<div class="line">
									<div class="quarter leftmar">
										Employee Name<span class="mandatory">*</span>
									</div>
									<div class="quarter">
										<form:input path="empName" id="empName"  readonly="true"/>
									</div>
								</div>
								<div class="line">
									<div class="quarter leftmar">
										Employee Type<span class="mandatory">*</span>
									</div>
										<div class="quarter">
											<form:select path="empType" id="empType" cssClass="formSelect" onchange="javascript:clearTons()">
												<form:option value="Select">Select</form:option>
												<form:option value="Permenent">Permenent</form:option>
												<form:option value="Contract">Contract</form:option>
											</form:select>
										</div>
								</div>
								
								
										<div class="line">
									<div class="quarter leftmar">
										Retirement Date<span class="mandatory">*</span>
									</div>
									<div class="quarter">
											<form:input path="retrimentDate" id="retrimentDate"
													cssClass="dateClass" readonly="true" 
													onclick="javascript:clearDateText('retrimentDate');" /> &nbsp; <img
												src="./images/calendar.gif" id="date_start_trigger4" /> <script
													type="text/javascript">
													Calendar
															.setup({
																inputField : "retrimentDate",
																ifFormat : "%d-%b-%Y",
																showsTime : false,
																button : "date_start_trigger4",
																singleClick : true,
																step : 1
															});
												</script>
									
										</div>
								</div>
								
								<div class="line">
									<div class="quarter leftmar">
										Fair Money<span class="mandatory">*</span>
									</div>
									<div class="quarter">
										<form:input path="retrimentAmt" id="retrimentAmt" onkeypress="javascript:return checkFloat(event,'retrimentAmt');" onkeyup="javascript:totBenfitAmt()"/>
										&nbsp;
									</div>
								</div>
								
								
									<div class="line">
									<div class="quarter leftmar">
										No Of Persons<span class="mandatory">*</span>
									</div>
									<div class="quarter">
										<form:input path="noOfPerson" id="noOfPerson" onkeyup="noOFPeople()" onkeypress="javascript:return checkFloat(event,'noOfPerson');" />
									&nbsp;
									</div>
								</div>
								
								<div class="line">
									<div class="quarter leftmar">
										Transport Amount<span class="mandatory">*</span>
									</div>
									<div class="quarter">
										<form:input path="transportAmt" id="transportAmt" onkeyup="javascript:totBenfitAmt()"  onkeypress="javascript:return checkFloat(event,'transportAmt');"/>
									&nbsp;
									</div>
								</div>
								
							
								
								
							
								
								
							
								
								<div class="line">
									<div class="quarter leftmar">
										Luggage Wait (in TONS)<span class="mandatory">*</span>
									</div>
									<div class="quarter">
										<form:input path="noofTons" id="noofTons" onkeyup="javascript:noOfTons()" onkeypress="javascript:return checkFloat(event,'noofTons');" />
									&nbsp;
									</div>
								</div>
								
								<div class="line">
									<div class="quarter leftmar">
										Lugguage Fair Amount<span class="mandatory">*</span>
									</div>
									<div class="quarter">
										<form:input path="luggageAmt" id="luggageAmt" onkeyup="javascript:totBenfitAmt()"  onkeypress="javascript:return checkFloat(event,'luggageAmt');"/>
									&nbsp;
									</div>
								</div>
								<div class="line">
									<div class="quarter leftmar">
										Total Amount <span class="mandatory">*</span>
									</div>
									<div class="quarter">
										<form:input path="totAmt" id="totAmt" readonly="true" onkeypress="javascript:return checkFloat(event,'totAmt');" />
									&nbsp;
									</div>
								</div>
								<div class="line">
									Note :
			
								</div>	
								<hr></hr>
								
								
									<div class="line" style="margin-left: 35%;">
											<div class="expbutton"><a onclick="javascript:submitRetrimentDetails();"> <span>Submit</span></a></div>
											<div class="expbutton"><a onclick="javascript:clearRetrimentDetails();"> <span>Clear</span></a></div>
									</div>		
									
									<div>
									<div class="line" id="result" >
										<jsp:include page="RetrimentDetailsList.jsp"></jsp:include>
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
			
			<input type="hidden" name="ename" id="ename" value="${employeeName}"/>
			<input type="hidden" name="eid" id="eid" value="${employeeID}"/>
			<div><jsp:include page="Footer.jsp" /></div>			
		</div>
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		<form:hidden path="updateID"/>
		</form:form>	
			
	</body>
	<script>
	clearRetrimentDetails();
	var eid= $jq('#eid').val();
	var ename= $jq('#ename').val();
//	$jq('#ename').val();
	 $jq('#empName').val(ename);
	 $jq('#sfID').val(eid);
	</script>
</html>
<!-- End:RetrimentHomePage.jsp -->