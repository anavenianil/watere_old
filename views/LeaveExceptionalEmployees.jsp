<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin :LeaveExceptionalEmployees.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="./script/RegExpValidate.js"></script>
<script type="text/javascript" src="./script/script.js"></script>
<link href="./styles/layout.css" rel="stylesheet" type="text/css" />
<link href="./styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/aa.js"></script>
<script type="text/javascript" src="script/leave.js"></script>
    <script language="javascript" type="text/javascript">
        $jq(function() {
            $jq("#moveRight,#moveLeft").click(function(event) {
                var id = $jq(event.target).attr("id");
                var selectFrom = (id == "moveRight") ? "#selectLeft" : "#selectRight";
                var moveTo = (id == "moveRight") ? "#selectRight" : "#selectLeft";
 
                var selectedItems = $jq(selectFrom + " :selected").toArray();
                $jq(moveTo).append(selectedItems);
                selectedItems.remove;
            });
        });
    </script>  
<title>Leave Exceptional Employees</title>
</head>

<body>
<form:form method="post" commandName="leaveAdmin" id="leaveExceptionalEmp">
		<div>
			<div class="Innermaindiv">
				<div class="header"></div>
				<div><jsp:include page="MenuLinks.jsp" /></div>	
				<div class="line"><jsp:include page="Result.jsp" /></div>				
				<div class="mainDisplay">
					<div class="whitebox1">
						<div class="whitebox_t">
							<div class="whitebox_tl">
								<div class="whitebox_tr"></div>
							</div>
						</div>
						<div class="whitebox_m1">
							<div class="lightblueBg1">
								<div class="headTitle">Leave Exceptional Employees</div>
								<%-- Content Page starts --%>
								<div>
									<div class="line" id="result"></div>
									<div class="line">
										<table border="0" cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td style="padding-left: 5%;color: red;">Employee List</td>
												<td width="10%" align="center">&nbsp;</td>
												<td style="padding-left: 5%;color: red;">Exceptional Employee List</td>
											</tr>
											<tr>
												<td style="padding-left: 5%;">
													<form:select path="employeeID" id="selectLeft" size="15" multiple="true" cssStyle="width:420px">
														<form:options items="${sessionScope.employeesList}" itemValue="sfid" itemLabel="ename"/>
													</form:select>
												</td>
												<td width="10%" align="center">
													<input id="moveRight" type="button" value="     Add     " style="margin-bottom: 5px"/>
	      											<input id="moveLeft" type="button" value=" Remove " />
												</td>
												<td style="padding-left: 5%">
													<form:select path="selectedID" id="selectRight" size="15" multiple="true" cssStyle="width:420px">
														<form:options items="${sessionScope.leaveExpList}" itemValue="sfid" itemLabel="ename"/>
													</form:select>
												</td>
											</tr>
										</table>
									</div>
									<div class="line">&nbsp;</div>
									<div class="line">
										<div style="float: right;padding-right: 4%">
											<div class="expbutton"><a href="javascript:saveLeaveExceptionEmp();"><span>Submit</span></a></div>
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
		<form:hidden path="param"/>
		<form:hidden path="type"/>
	    <form:hidden path="selectedLinks"/>
		</form:form>
	</body>
</html>
<!-- End :LeaveExceptionalEmployees.jsp  -->