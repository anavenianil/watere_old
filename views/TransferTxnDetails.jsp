<!--begin:TransferTxnDetails -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
    <%@ page import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.beans.dto.DepartmentsDTO"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>

<%@page import="com.callippus.web.beans.dto.DoPartDTO"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/transfer.js"></script>
<script type="text/javascript" src="script/promotions.js"></script>
<title>Transfer Txn Master Details</title>
</head>
<body>
	<form:form method="post" commandName="transfer" id="transfer">
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
								<div class="headTitle">Transfer Transaction Master Details</div>
								<div class="line">
									<%-- Content Page starts --%>
									<div class="line">
										<div class="quarter leftmar">SFID<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="transferedSFID" id="transferedSFID" onblur="checkDepartmetns()"/></div>
									<div class="half" id="result"></div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Department From<span class="mandatory">*</span></div>
										<div class="quarter">
										<form:select path="fromDept" id="fromDept">
										<form:option value="">Select</form:option>
										</form:select>
										</div>	
									</div>
									<div class="line">
										<div class="quarter leftmar">Department To<span class="mandatory">*</span></div>
										<div class="quarter">
										<form:select path="toDept" id="toDept" onchange="javascript:getOrgRole()">
										<form:option value="">Select</form:option>
										<form:options items="${DepartmentsList}" itemLabel="deptName" itemValue="id"/>
										</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Assigned To<span class="mandatory">*</span></div>
										<div class="quarter"><form:select path="assignedTo" id="assignedTo">
										<form:option value="">Select</form:option>
										<form:options items="${orgInstanceList}" itemLabel="name" itemValue="id"/>
										</form:select></div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Gazetted Type<span class="mandatory">*</span></div>
										<div class="quarter">
										<form:select path="gazType" id="gazettedType">
										<form:option value="Select">Select</form:option>
										<form:option value="GAZETTED">Gazetted</form:option>
										<form:option value="NONGAZETTED">Non Gazetted</form:option>
										</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">DO Part Date<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="doPartDate" id="doPartDate" cssClass="required" readonly="true" onchange="javascript:getDoPartNumber()"/>
										<img  src="./images/calendar.gif" id="date_start_trigger1" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"doPartDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
											</script>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Do Part Number<span class="mandatory">*</span></div>
										<div class="quarter" id="doPartNumber"><input type="text" id="doPartNo" class="dateClass" /></div>			
									</div>
									<div class="line">
										<div style="margin-left:30%">
										<a href="javascript:manageTransferTxnDetails();" class="appbutton">SUBMIT</a>
										<a href="javascript:clearTransferTxnDetails();" class="appbutton">CLEAR</a>
										</div>
									</div>
									<div class="line" id="TransferTxnList">
									   <jsp:include page="TransferTxnList.jsp"/>
									</div>
									<%-- Content Page end --%>
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
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		</form:form>
		<script type="text/javascript">
		deptsJSON = <%= (net.sf.json.JSONArray)session.getAttribute("departmentJSON") %>;
		doPartJSON = <%= (net.sf.json.JSONArray)(List<DoPartDTO>)session.getAttribute("doPartJSON") %>;
		orgRoleJSON = <%= (net.sf.json.JSONArray)(List<DoPartDTO>)session.getAttribute("orgRoleJSON") %>;
		</script>
		</body>
</html>
<!--End:TransferTxnDetails -->