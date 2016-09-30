<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:NewQuarterRequest.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/pisscript.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/tada.js"></script>

</head>

<body>
	<form:form method="post" commandName="quarterRequest">
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
								<div class="headTitle" id="headTitle"></div>
								<%-- Content Page starts --%>
								<div class="line">
									<div class="line" id="result"></div>
									<div class="line" id="quarterResult"></div>
									
								<c:if test="${ sessionScope.quarterTitleType eq 'quarterOfflineEntry' }">	
									<div class="line">
										<div class="half leftmar">SFID<span class="mandatory">*</span></div>
										<div class="half"><form:input path="sfID" id="sfID" maxlength="10" onchange="javascript:getSFIDDetails();" onkeypress="javascript:return isAlphaNumaricExp(event);"/></div>
									</div>
									<div class="line">
										<div class="half leftmar">Name</div>
										<div class="half" id="sfidName"></div>
										
									</div>
									<div class="line">
										<div class="half leftmar">Designation</div>
										<div class="half" id="sfidDesignation"></div>
									</div>
									<div class="line">
										<div class="half leftmar">Date of Appointment</div>
										<div class="half" id="sfidDOA"></div>
									</div>
								</c:if>		
								<c:if test="${ sessionScope.quarterTitleType ne 'quarterOfflineEntry' }">	
									<div class="line">
										<div class="half leftmar">SFID</div>
										<div class="half">${quarterRequest.sfID}</div>
									</div>
									<div class="line">
										<div class="half leftmar">Name</div>
										<div class="half">${quarterRequest.employeeDetails.nameInServiceBook}</div>
									</div>
									<div class="line">
										<div class="half leftmar">Designation</div>
										<div class="half">${quarterRequest.employeeDetails.designationDetails.name}</div>
									</div>
									<div class="line">
										<div class="half leftmar">Date of Appointment</div>
										<div class="half"><fmt:formatDate pattern="dd-MMM-yyyy" value="${quarterRequest.employeeDetails.dojDrdoInFormat}" /></div>
									</div>
								</c:if>
								<div class="line" id="reqResult" style="display:none">
									<div class="expbutton"><font color="red">Quarter Request has Already Applied</font></div>
								</div>
								<div class="line" id="reqDetailsDiv">
								 <c:if test="${quarterRequest.type == 'change'}">	
									<div class="line">
										<div class="half leftmar">Change Of Quarter <span class="mandatory">*</span></div>
											<div class="half">
											    <form:radiobutton path="appliedQuarter" id="appliedQuarter1" value="1" onchange="javascript:setApplQuarter('${quarterRequest.eligibility}');" /><label>${quarterRequest.eligibility} Type Quarter</label>
											    <form:radiobutton path="appliedQuarter" id="appliedQuarter2" value="2" onchange="javascript:setApplQuarter('${quarterRequest.eligibility}');" /><label>New Quarter</label>
											</div>
									</div>
								 </c:if>

									<div class="line">
										<div class="half leftmar">Type of Quarters Eligibility<span class="mandatory">*</span></div>
										<div class="half">
										<select name="eligibility" id="eligibility" style="width:150px" onchange="javascript:selectedQuarter()">
											<option value="select">Select</option>
											<c:forEach var="qtl" items="${quarterRequest.quarterTypeList}">
												<option value="${qtl.name}">${qtl.name}</option>
											</c:forEach>
										</select>
										</div>
									
									</div>
									<c:if test="${quarterRequest.quarterTypeList eq null}">
										<div class="line"><font size="3%" color="red" style="text-align: left;">
										<br/><br/>IMPORTANT NOTE:<br/> <b>
										<u>Please Contact your Admin Task Holder.</u>
										</b><br/>AS your Basic Pay And Grade Pay Details are not Entered.<br/>
                                         </div>
                                         </font>
									</c:if>
									
									<div class="line">
										<div class="half leftmar">Priority Date<span class="mandatory">*</span></div>
										<div class="half">
											<form:input path="priorityDate" id="priorityDate" cssClass="dateClass" readonly="true" onchange="javascript:checkPriorityDate();"/>
											<img  src="./images/calendar.gif" id="priorityDateImg" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"priorityDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"priorityDateImg",singleClick : true,step : 1});
											</script>
										</div>
									</div>
									<div class="line">
										<div class="half leftmar">Entitled to<span class="mandatory">*</span></div>
										<div class="half">
											<form:radiobutton path="entitled" id="entitledAD" label="Type A to D" value="1"/>
											<form:radiobutton path="entitled" id="entitledE" label="Type E and above" value="2"/>											
										</div>
									</div>
									<div class="line">
										<div class="half leftmar">State particulars of Govt. residence if any allotted<span class="mandatory">*</span></div>
										<div class="half">
											<form:radiobutton path="allotment" id="allotted" label="Yes" value="Y" onclick="javascript:checkAllotment()"/>
											<form:radiobutton path="allotment" id="noallotment" label="No" value="N" onclick="javascript:checkAllotment()"/>											
										</div>
									</div>
									<div class="line" id="allotmentDiv" style="display:none">
										<div class="line">
											<div class="half leftmar">i) By the SA to RM & DG R & D<span class="mandatory">*</span></div>
											<div class="half"><form:input path="saToRm" id="saToRm" /></div>
										</div>
										<div class="line">
											<div class="half leftmar">ii) By any other Govt. Dept. (also state the name of the Dept.)<span class="mandatory">*</span></div>
											<div class="half"><form:input path="otherDept" id="otherDept" /></div>
										</div>
									</div>
									<div class="line">
										<div class="half leftmar">Does the applicant stand debarred for Govt. residence. If so, upto what date & under what authority</div>
										<div class="half">
											<form:textarea cols="30" rows="4" path="debarred" id="debarred"/>											
										</div>
									</div>
									<div class="line">
										<div class="half leftmar">Is the applicant entitled to rent free accommodation<span class="mandatory">*</span></div>
										<div class="half">
											<input type="radio" name="rentFree" id="rentFre" value="Y"/><label>Yes</label>
											<input type="radio" name="rentFree" checked="checked" id="rent" value="N"/><label>No</label>											
										</div>
									</div>
									<div class="line">
										<div class="half leftmar">State whether Pmt/QP/Ty<span class="mandatory">*</span></div>
										<div class="half">
											<input type="radio" name="suretyType" checked="checked" id="pmt" value="P"/><label>Pmt</label>
											<input type="radio" name="suretyType" id="qp" value="Q"/><label>QP</label>	
											<input type="radio" name="suretyType" id="ty" value="T"/><label>Ty</label>										
										</div>
									</div>
									<div class="line">
										<div class="half leftmar">Option Of Quarter Required</div>
										<div class="half">
											<form:textarea cols="30" rows="4" path="quarterOption" id="quarterOption"/>											
										</div>
									</div>
									<div class="line">
										<div class="half leftmar">Remarks</div>
										<div class="half">
											<form:textarea cols="30" rows="4" path="remarks" id="remarks"/>											
										</div>
									</div>
									<div class="line">
										<div class="half leftmar"><font color="blue">Particulars of surety:-</font></div>
										<div class="half">
											    <input type="radio" name="suretyReq" id="suretyReq1" value="1" onchange="javascript:showSuretyDiv();" /><label>Yes</label>
											    <input type="radio" name="suretyReq" checked="checked" id="suretyReq2" value="2" onchange="javascript:showSuretyDiv();"/><label>No</label>
										</div>
									</div>
									<div class="line" id="suretyDiv" style="display: none">
									<div class="line">
										<div class="half leftmar">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Name of Surety<span class="mandatory">*</span></div>
										<div class="half"><form:input path="suretyName" id="suretyName" onkeypress= "return isAlphabetSplCharExp(event,'appname');"/></div>
									</div>
									<div class="line">
										<div class="half leftmar">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Present Desig<span class="mandatory">*</span></div>
										<div class="half"><form:input path="presentDesig" id="presentDesig" onkeypress= "return isAlphabetSplCharExp(event,'appname');"/></div>
									</div>
									<div class="line">
										<div class="half leftmar">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Office to which attached<span class="mandatory">*</span></div>
										<div class="half"><form:input path="officeName" id="officeName" onkeypress= "return isAlphabetExp(event,'appname');"/></div>
									</div>
									<div class="line">
										<div class="half leftmar">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Likely date of retirement<span class="mandatory">*</span></div>
										<div class="half">
											<form:input path="retirement" id="retirement" cssClass="dateClass" readonly="true"/>
											<img  src="./images/calendar.gif" id="retirementImg" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"retirement",ifFormat : "%d-%b-%Y",showsTime : false,button :"retirementImg",singleClick : true,step : 1});
											</script>
										</div>
									</div>
									</div>
									<div class="line">
									  <div style="margin-left:50%">
										<div class="expbutton"><a href="javascript:submitQuarterRequest('${sessionScope.quarterTitleType}')"><span>Submit</span></a></div>
										<div class="expbutton"><a href="javascript:clearQuarterRequest();"><span>Clear</span></a></div>
									  </div>
									</div>
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
		<form:hidden path="type" id="type"/>
		<form:hidden path="param"/>	
		<form:hidden path="requestID" />
		<form:hidden path="requestId"/>	
		</form:form>	
	
		<script >
        var type='<c:out value='${sessionScope.quarterTitleType}'/>';
        qLablesView(type);
        quarterList1 = <%= (net.sf.json.JSONArray) session.getAttribute("quarterList") %>;
		</script>	
	</body>
</html>
<!-- End:NewQuarterRequest.jsp -->