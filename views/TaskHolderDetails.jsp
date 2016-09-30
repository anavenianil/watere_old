<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TaskHolderDetails.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/leave.js"></script>

<title>Task Holder Details</title>
</head>

<body onload="resetTaskHolderDetails();">
	<form:form method="post" commandName="leaveAdmin">
		<div >
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
								<div class="headTitle">Task Holder Details</div>
								<%-- Content Page starts --%>
								<div class="line">
									<div class="line">
										<div class="quarter leftmar">Type<span class="mandatory">*</span></div>
										<div class="quarter">
										<form:select path="typeId" id="typeId"  cssClass="formSelect" >
												<form:option value="0">Select</form:option>
												<form:options items="${leaveAdmin.typeList}" itemValue="id"  itemLabel="name"/>
										</form:select>
										</div>
									</div>
																	
									<div class="line">
										<div class="quarter leftmar">Role<span class="mandatory">*</span></div>
										<div class="quarter">
										<form:select path="roleId" id="roleId"  cssClass="formSelect" onmouseover="setSelectWidth('#roleId');" >
												<form:option value="0">Select</form:option>
												<form:options items="${leaveAdmin.roleList}" itemValue="id"  itemLabel="name"/>
										</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Remarks</div>
										<div>										
											<form:textarea path="remarks" cols="30" rows="4" id="remarks"  onkeypress="textCounter(this,document.forms[0].counter,500);" onkeyup="textCounter(this,document.forms[0].counter,500);"/>
											<input type="text" class="counter" name="counter" value="500" id="counter" disabled="disabled"/>										
										</div>
									</div>
									<div  style="margin-left: 37%;">
										<div class="expbutton"><a onclick="javascript:submitTaskHolderDetails()"> <span>Submit</span></a></div>
										<div class="expbutton"><a onclick="javascript:resetTaskHolderDetails()"> <span>Clear</span></a></div>
									</div>
									<div class="line"><hr /></div>
									<div class="line" id="result">
										<jsp:include page="TaskHolderDetailsList.jsp"></jsp:include>
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
<!-- End:TaskHolderDetails.jsp -->