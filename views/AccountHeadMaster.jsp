<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:AccountHeadMaster.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/mmgscript.js"></script>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript">
	$jq(document).ready(function(){
		clearMMGMaster('${sessionScope.masterType}');
		$jq("#mmgMaster").validate({
    	 rules: { 
	         departmentId: "required",
	         accountHeadNumber: "required",
	         fundTypeId: "required",
	         year: "required",
	         allottedFund: "required"
	       
	         
	   },
	  messages: {
		departmentId: "Please select Department.",
		accountHeadNumber: "Please select Account Head No.",
		fundTypeId: "Please select Allotted Fund Type.",
		allottedFund: "Please select Allotted Fund .",
		year: "Please select Year."
		},
    });
   	});
</script>
<title>Account Head Master</title>
</head>
<body>
	<form:form method="post" commandName="mmgMaster" name="mmgMaster" id="mmgMaster" action="">
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
								<div class="headTitle" id="headTitle">Create Account Head</div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</img></div>
								<%-- Content Page starts --%>
								<div>
									<div class="line">
										<div class="quarter bold" style="margin-left:8px;">Search With</div>
										<div class="quarter">
											<form:select path="searchWith" id="searchWith" cssStyle="width:145px;">
												<form:option value="Select">Select</form:option>
												<form:option value="accHeadNumber">Account Head Number</form:option>
											</form:select>
										</div> 
										<div class="quarter">
											<form:input path="searchWithValue" id="searchWithValue" maxlength="10" onkeyup="javascript:accountHeadSearch('${sessionScope.masterType}');"/>
										</div>
									</div>
									<div class="line">
											<%--<div style="margin-left:25%;"><a id="createFundType" href="javascript:accountHeadSearch('${sessionScope.masterType}');"><div class="appbutton">Search</div></a></div> --%>
											<div style="margin-left:52%;"><a id="createFundType" href="javascript:clearSearch();"><div class="appbutton">Clear</div></a></div>  
									</div> 
									<div class="line">
										<fieldset><legend><strong><font color='green'>Account Head Details</font></strong></legend>
										<div class="line">
											<div class="quarter" style="margin-left:8px;">Division<span class='failure'>*</span></div>
											<div class="quarter">
												<form:select path="departmentId" id="departmentId" cssStyle="width:145px;">
													<form:option value="">Select</form:option>
													<form:options items="${parentList}" itemValue="id" itemLabel="deptName"/>
													
												</form:select>
											</div>
											<div class="quarter" style="margin-left:8px;">Account Head Number<span class='failure'>*</span></div>
											<div class="quarter">
												<form:input path="accountHeadNumber" id="accountHeadNumber" maxlength="10"/>
											</div>
										</div>
									
										<div class="line">
											<div class="quarter" style="margin-left:8px;">Alloted Fund Type<span class='failure'>*</span></div>
											<div class="quarter">
												<form:select path="fundTypeId" id="fundTypeId" cssStyle="width:145px;">
													<form:option value="">Select</form:option>
													<form:options items="${fundTypeList}" itemValue="id" itemLabel="name"/>
													
												</form:select>
											</div>
											<div class="quarter" style="margin-left:8px;">Alloted Fund<span class='failure'>*</span></div>
											<div class="quarter">
												<form:input path="allottedFund" id="allottedFund" maxlength="10" onkeypress="return checkTwoDigitFloat(event,this.id);"/>
											</div>
										</div>
										<div class="line">
											<div class="quarter" style="margin-left:8px;">Quantity Held</div>
											<div class="quarter">
												<form:input path="qtyHeld" id="qtyHeld" maxlength="10" onkeypress="return checkTwoDigitFloat(event,this.id);"/>
											</div>
											<div class="quarter" style="margin-left:8px;">Quantity Required</div>
											<div class="quarter">
												<form:input path="qtyRequired" id="qtyRequired" maxlength="10" onkeypress="return checkTwoDigitFloat(event,this.id);"/>
											</div>
										</div>
										<div class="line">
											<div class="quarter" style="margin-left:8px;">Consumed Fund (<b><del>&#2352;</del></b>)</div>
											<div class="quarter">
												<form:input path="consumedFund" id="consumedFund" maxlength="10" onkeypress="return checkTwoDigitFloat(event,this.id);"/>
											</div>
											<div class="quarter" style="margin-left:8px;">Committed Fund (<b><del>&#2352;</del></b>)</div>
											<div class="quarter">
												<form:input path="commitedFund" id="commitedFund" maxlength="10" onkeypress="return checkTwoDigitFloat(event,this.id);"/>
											</div>
										</div>
										<div class="line">
											<div class="quarter" style="margin-left:8px;">Financial Year<span class='failure'>*</span></div>
											<div class="quarter">
												<form:select path="year" id="year" cssStyle="width:145px;">
												<form:option value="">Select</form:option>
												<form:options items="${sessionScope.FinancialYearList}" itemLabel="key" itemValue="key"></form:options>
												</form:select>
											</div>
											<div class="quarter" style="margin-left:8px;">Account Description</div>
											<div class="quarter"><form:textarea path="description" id="description" cols="20" rows="3" onkeypress="textCounter(event,$jq('#description'),$jq('#counter'),500);"
											 onkeyup="textCounter(event,$jq('#description'),$jq('#counter'),500);"></form:textarea>
												<input type="text" class="counter" name="counter" value="500" id="counter"/>
											</div>
										</div>
									
										<div class="line">
											<div style="margin-left:75%;"><a href="javascript:manageMMGMaster('${sessionScope.masterType}');"><div class="appbutton">Submit</div></a></div>
											<div style="margin-left:65%;"><a href="javascript:clearMMGMaster('${sessionScope.masterType}');"><div class="appbutton">Clear</div></a></div>    
										</div>
										</fieldset>
									</div>
									<div class="line">
										<div id="result" class="line">
											<jsp:include page="MMGMasterDataList.jsp" />
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
		<form:hidden path="param" id="param"/>
		<form:hidden path="type" id="type"/>
		
		</form:form>
		
	</body>
</html>
<!-- End:AccountHeadMaster.jsp -->