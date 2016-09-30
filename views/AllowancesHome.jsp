<!-- begin:AllowancesHome.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="script/jquery.1.10.2.min.js"></script>
<script src="script/jquery.autocomplete.min.js"></script>
<link rel="stylesheet" type="text/css" href="styles/main.css">
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/allowances.js"></script>
<link href="styles/calendar.css" type="text/css" rel="stylesheet" />
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />

<!-- <script type="text/javascript" src="script/calendar-hd.js"></script>
<script type="text/javascript" src="script/calendar_us.js"></script> -->

<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>

<title>Allowances Home</title>
</head>

<body onload="">
	<form:form method="post" commandName="allowancesRequest" name="allowancesForm">
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
								<div class="headTitle">Allowances Master</div>
								<div class="line">
									<div class="line">
										<div class="quarter leftmar">Allowance Name</div>
										<div class="quarter">
												<form:select path="deptId" id="deptId" onchange="hideShowRadioButtons()">
													<form:option value="select">-- Select --</form:option>
													<form:options items="${allowancesRequest.allowancesList}" itemValue="id" itemLabel="allowanceType"/>
												</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Allowance For</div>
											<div class="quarter leftmar" id="allowanceForALL1">
											<form:radiobutton path="allowanceFor" id="allowanceForALL" 
												value="ALL"  onclick="checkBoxCheckX();checkBoxCheckAll('selectall',\'row\');"/>For all employees
										</div>
										<div class="quarter leftmar" id="allowanceForExceptMD1">
											<form:radiobutton path="allowanceFor" id="allowanceForExceptMD"
												value="ALLEXCPMD" onclick="checkBoxCheckX();checkBoxCheckAll('selectall',\'row\');checkBoxCheckY();"/> For all employees except MD
										</div>
										<div class="quarter leftmar" id="allowanceForReq1">
											<form:radiobutton path="allowanceFor" id="allowanceForReq"
												value="REQUESTBASED"/> On basis of request
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">
											Approved By<span class="mandatory">*</span>
										</div>
										<div class="quarter" id="deptId">
											<select name="approvedBy" id="approvedBy" style="width:215px">
												<option value="select" >-- Select --</option>
												<c:forEach var="acc" items="${allowancesRequest.empList}"  >
													<option value="${acc.userSfid}">${acc.firstName} ${acc.middleName} ${acc.lastName}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">
											Allowance Amount<span class="mandatory">*</span>
										</div>
										<div class="quarter" id="deptId">
											<form:input path="allowanceAmt" onkeypress="javascript:return checkInt(event);"/>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Date of Approval<span class="mandatory">*</span></div>
										<div class="quarter"><input type="text" name="approvalDate" id="approvalDateAdv" readonly="readonly"/>
										<img  src="./images/calendar.gif" id="approvalDateAdvImg" />	
										<script type="text/javascript">
											Calendar.setup({inputField :"approvalDateAdv",ifFormat : "%d-%b-%Y",showsTime : false,button :"approvalDateAdvImg",singleClick : true,step : 1});
										</script></div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Effect from Date<span class="mandatory">*</span></div>
										<div class="quarter"><input type="text" name="effectiveFromDate" id="effectiveFromDateAdv" readonly="readonly"/>
										<img  src="./images/calendar.gif" id="effectiveFromDateAdvImg" />	
										<script type="text/javascript">
											Calendar.setup({inputField :"effectiveFromDateAdv",ifFormat : "%d-%b-%Y",showsTime : false,button :"effectiveFromDateAdvImg",singleClick : true,step : 1});
										</script></div>
									</div>
									<div class="line" id="effectiveToDateAdv1">
										<div class="quarter leftmar">Effect to Date<span class="mandatory">*</span></div>
										<div class="quarter"><input type="text" name="effectiveToDate" id="effectiveToDateAdv" readonly="readonly"/>
										<img  src="./images/calendar.gif" id="effectiveToDateAdvImg" />	
										<script type="text/javascript">
											Calendar.setup({inputField :"effectiveToDateAdv",ifFormat : "%d-%b-%Y",showsTime : false,button :"effectiveToDateAdvImg",singleClick : true,step : 1});
										</script></div>
									</div>
									<div class="line" id="searchEmp">
										<div class="quarter leftmar">
											Search Employee By Name or ID <span class="mandatory">*</span>
										</div>
										<div class="quarter">
											<form:input path="empDetails" id="w-input-search" size="30" />
										</div>
									</div>
									<div class="expbutton" style="margin-left: 45%">
										<a href="javascript:allowanceSubmit()"><span>Submit</span></a>
									</div>
									<div class="expbutton">
										<a href="javascript:clearfpa();"><span>Clear</span></a>
									</div>
									<div class="line">
										<!-- <div style="float:left;"  ><b>Details of Employees For Allowance</b></div> -->     
										<div id="result" class="line">
											<jsp:include page="AllowanceEmployeeDetails.jsp" />
										</div>
									</div>
								</div>
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
		<form:hidden path="param" />
		<form:hidden path="type" />
		<form:hidden path="requestID" />
		<form:hidden path="requestId" />
	</form:form>
	<script>
	  $(document).ready(function() {
	
		$('#w-input-search').autocomplete({
			serviceUrl: '/essnew/searchEmployee.htm',
			paramName: "searchString",
			delimiter: ",",
		   transformResult: function(response) {
			    	
			return {      	
			  //must convert json to javascript object before process
			  suggestions: $.map($.parseJSON(response), function(item) {
			      return { value: item.nameInServiceBook +" (" + item.userSfid + ")", data: item.userSfid };
			   })
			 };
	        }
		 });
	  });
	  </script>
</body>
</html>
<!-- End:AllowancesHome.jsp -->