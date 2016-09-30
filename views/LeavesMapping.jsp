<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LeaveMapping.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="./styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/prototype.js"></script>
<script type="text/javascript" src="script/aa.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/leave.js"></script>
<title>Combinations of leave types accepted</title>
</head>

<body>
	<form:form method="post" commandName="LEAVE">
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
								<div class="headTitle">Combinations of leave types accepted</div>
									<%-- Content Page starts --%>
									<div class="line">
										<div class="line">
											<div class="quarter leftmar">Nature of leave<span class="failure">*</span></div>
											<div class="quarter">
												<form:select path="leaveTypeId" id="leaveTypeId" cssClass="formSelect">
													<form:option value="select">Select</form:option>
													<form:options items="${sessionScope.leaveTypeList}" itemValue="id" itemLabel="name"/>
												</form:select>
											</div>
										</div>
										<div class="line">
											<div class="quarter leftmar">Nature of leave<span class="failure">*</span></div>
											<div class="quarter">
												<form:select path="leaveTypeId2" id="leaveTypeId2" cssClass="formSelect">
													<form:option value="select">Select</form:option>
													<form:options items="${sessionScope.leaveTypeList}" itemValue="id" itemLabel="name"/>
												</form:select>
											</div>
										</div>
										<div class="line">
											<div class="quarter leftmar">Description</div>
											<div class="quarter"><form:textarea path="description" id="description" rows="5" cols="35" onkeypress="textCounter(this,document.forms[0].counter,500);" onkeyup="textCounter(this,document.forms[0].counter,500);"/>
											<div class="line"><input type="text" class="counter" name="counter" value="500" id="counter"/></div>
											</div>
										</div>
										<div  class="line">
										    <div><a href="javascript:resetLeaveMapping();" class="appbutton" style="float:right;text-decoration: none">Clear</a></div>
										    <div><a href="javascript:submitLeaveMapping();" class="appbutton" style="float:right;text-decoration: none">Submit</a></div>
										</div>
										<div  class="line" id="leaveMappingList">
											<jsp:include page="LeavesMappingList.jsp"></jsp:include>
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
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		<form:hidden path="pk"/>
		</form:form>
	</body>
</html>
<!-- End:LeaveMapping.jsp -->