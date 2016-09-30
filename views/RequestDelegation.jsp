<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:RequestDelegation.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html>
<head>
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script language="javascript" src="script/prototype.js"></script>
<script type="text/javascript" src="script/aa.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/pisscript.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script type="text/javascript" src="script/json2_mini.js"></script>
<script language="javascript" src="script/calendar.js"></script>


<title>Request Delegation</title>
</head>
<body>
<form:form method="post" commandName="workflow">
<form:hidden path="param"/>
<form:hidden path="type"/>
<form:hidden path="message"/>
<form:hidden path="requestId"/>
<form:hidden path="historyID"/>
<form:hidden path="statusMsg"/>
<form:hidden path="back"/>

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
								<div class="headTitle">Request Delegation</div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</div>
									
									<%-- Content Page starts --%>
										<div class="line">
											<div class="quarter bold leftmar" >Assigned Employees<span class="mandatory">*</span></div>
											<div class="quarter" >
												<form:select path="sfID" id="assignedEmp" cssClass="formSelect" onmouseover="setSelectWidth('#assignedEmp')">
													<form:option value="select">Select</form:option>
													<c:forEach var="employee" items="${workflow.empList}" >
												 		<form:option value="${employee.id}">${employee.id}-${employee.name}</form:option>
													</c:forEach> 
												</form:select>
											</div>
										</div>
										<div class="line">
											<div class="quarter bold leftmar" >Request Type Id<span class="mandatory">*</span></div>
											<div class="quarter" >
												<form:select path="requestTypeID" id="requestTypeID" cssClass="formSelect" onmouseover="setSelectWidth('#requestTypeID')">
													<form:option value="select">Select</form:option>
													<c:forEach var="reqtype" items="${workflow.requestTypeList}">
												 		<form:option value="${reqtype.requestType}">${reqtype.requestTypeName}</form:option>
													</c:forEach> 
												</form:select>
											</div>
										</div>
										<div class="line">
											<div class="quarter bold leftmar" >Request Id<span class="mandatory">*</span></div>
											<div class="quarter" >
												<form:input path="requestID" id="requestID" maxlength="15" onkeypress="javascript:return checkInt(event)"/>
											</div>
										</div>
										<div class="line">
											<div class="quarter bold leftmar" >Delegate To<span class="mandatory">*</span></div>
											<div class="quarter" >
												<form:select path="instanceId" id="instanceId" cssClass="formSelect" onmouseover="setSelectWidth('#instanceId')">
													<form:option value="select">Select</form:option>
													<c:forEach var="instance" items="${workflow.instanceList}">
												 		<form:option value="${instance.id}">${instance.name}</form:option>
													</c:forEach> 
												</form:select>
											</div>
										</div>
										<div class="line">
											<div class="quarter bold leftmar" >Remarks</div>
											<div class="quarter" >
												<form:textarea path="remarks" id="remarks"/>
											</div>
										</div>
										<div class="line">
											<div class="appbutton" style="margin-left: 25%;"><a class="quarterbutton" href="javascript:searchRequests()">Search</a></div>
											<div class="appbutton" ><a class="quarterbutton" href="javascript:requestsDelegate();">Delegate</a></div>
											<div class="appbutton" ><a class="quarterbutton" href="javascript:clearDelegation()">Clear</a></div>
										</div>
										<div id="reqResult"></div>
										<div class="line" id="searchList">
										<jsp:include page="RequestList.jsp"></jsp:include>
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
			
</form:form>
</body>
</html>
<!-- end:RequestDelegation.jsp -->