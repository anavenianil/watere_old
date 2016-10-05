<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin:RetrimentHomePage.jsp-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
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
										<form:input path="sfID" id="sfID" readonly="true" value='${getretriedEmpDetails.sfID }'/>
										&nbsp;
									</div>
									
									<div class="quarter leftmar">
										Employee Name<span class="mandatory">*</span>
									</div>
									<div class="quarter">
										<form:input path="empName" id="empName"  readonly="true" value='${getretriedEmpDetails.empName }'/>
									</div>
									
								</div>
								
								<div class="line">
									<div class="quarter leftmar">
										Employee Type<span class="mandatory">*</span>
									</div>
										<div class="quarter">
										<form:input path="empType" id="empType"  readonly="true" value='${getretriedEmpDetails.empType }'/>
										</div>
										
										<div class="quarter leftmar">
										Retriment Date<span class="mandatory">*</span>
									</div>
									<div class="quarter">
											
								<%-- 	<form:input path="retrimentDate" id="retrimentDate"  readonly="true" value='${getretriedEmpDetails.retrimentDate }' /> --%>
									
								<input type="text" name="redate" id="redate"  readonly="readonly" value='<fmt:formatDate pattern="dd-MMM-yyyy" value="${getretriedEmpDetails.retrimentDate}" />'/>
										
										</div>
										
									
										
								</div>
								<div class="line">
								
								
								<div class="quarter leftmar">
										Fair Money<span class="mandatory">*</span>
									</div>
									<div class="quarter">
										<form:input path="retrimentAmt" id="retrimentAmt" readonly="true" value='${getretriedEmpDetails.retrimentAmt }'/>
										&nbsp;
									</div>
									
									
									<div class="quarter leftmar">
										No Of Persons<span class="mandatory">*</span>
									</div>
									<div class="quarter">
										<form:input path="noOfPerson" id="noOfPerson" readonly="true" value='${getretriedEmpDetails.noOfPerson }'/>
									&nbsp;
									</div>
									
										
									
									
									
								</div>
									
								<div class="line">
										
										<div class="quarter leftmar">
										Transport Amount<span class="mandatory">*</span>
									</div>
									<div class="quarter">
										<form:input path="transportAmt" id="transportAmt" readonly="true" value='${getretriedEmpDetails.transportAmt }'/>
									&nbsp;
									</div>
									
									<div class="quarter leftmar">
										Luggage wait (in Tons)<span class="mandatory">*</span>
									</div>
									<div class="quarter">
										<form:input path="noofTons" id="noofTons" readonly="true" value='${getretriedEmpDetails.noofTons }'/>
									&nbsp;
									</div>
								</div>
								<div class="line">
									<div class="quarter leftmar">
										Lugguage Fair Amount<span class="mandatory">*</span>
									</div>
									<div class="quarter">
										<form:input path="luggageAmt" id="luggageAmt" readonly="true" value='${getretriedEmpDetails.luggageAmt }'/>
									&nbsp;
									</div>
									
									<div class="quarter leftmar">
										Total Amount <span class="mandatory">*</span>
									</div>
									<div class="quarter">
										<form:input path="totAmt" id="totAmt" readonly="true" value='${getretriedEmpDetails.totAmt }'/>
									&nbsp;
									</div>
								</div>

								<div class="line">
									<div class="quarter leftmar">
										DV NO<span class="mandatory">*</span>
									</div>
									<div class="quarter">
										<form:input path="dvno" id="dvno" />
									&nbsp;
									</div>
									
									
									<div class="quarter leftmar">
										DV DATE <span class="mandatory">*</span>
									</div>
									<div class="quarter">
										<form:input path="dvDate" id="dvDate"
															cssClass="dateClass" readonly="true"
															onclick="javascript:clearDateText('dvDate');" /> &nbsp;
														<img src="./images/calendar.gif" id="date_start_trigger5" />
														<script type="text/javascript">
															Calendar
																	.setup({
																		inputField : "dvDate",
																		ifFormat : "%d-%b-%Y",
																		showsTime : false,
																		button : "date_start_trigger5",
																		singleClick : true,
																		step : 1
																	});
														</script>
									</div>
								</div>


								<div class="line">
									<div class="quarter leftmar">
										Mode Of Payment <span class="mandatory">*</span>
									</div>
									<div class="quarter">
										<form:radiobutton path="cashorcheck" id="cashorcheck1"
											value="CHEQUE"  onclick="javascript:showBankDetails('show')"/>
										<label>Cheque</label>
										<form:radiobutton path="cashorcheck" id="cashorcheck2"
											value="CASH" onclick="javascript:showBankDetails('hide')" />
										<label>Cash</label>
									</div>
								</div>

						<div id="bnkdetails">
								<div class="line">
									<div class="quarter leftmar">
										Bank Name<span class="mandatory">*</span>
									</div>
										<div class="quarter">
											<select name="bankName" path="bankName" id="bankName">
												<c:forEach var="item" items="${retrimentBean.bankNamesList}">
													<option value="${item.bankName}">${item.bankName}</option>
												</c:forEach>
											</select>
										</div>
										<div class="quarter leftmar">
										Branch Name <span class="mandatory">*</span>
									</div>
									<div class="quarter">
										<form:input path="branchName" id="branchName" />
									</div>
								</div>

								<div class="line">
									<div class="quarter leftmar">
										Cheque No. :<span class="mandatory">*</span>
									</div>
									<div class="quarter">
									<form:input path="chequeNo" id="chequeNo" />
									</div>
								</div>
							</div>
							

								<div class="line" style="margin-left: 35%;">
											<div class="expbutton"><a onclick="javascript:submitRetrimentAmt();"> <span>Submit</span></a></div>
											<div class="expbutton"><a onclick="javascript:clearsubmitRetrimentAmt();"> <span>Clear</span></a></div>
									</div>		
									
									<div>
									

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
		<form:hidden path="updateID" value='${getretriedEmpDetails.id }'/>
		</form:form>	
			
	</body>
	<script>
	</script>
</html>
<!-- End:RetrimentHomePage.jsp -->