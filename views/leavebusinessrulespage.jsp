<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : leavebusinessrulespage.jsp -->

<%@page import="net.sf.json.JSONArray"%>
<%@page import="com.callippus.web.leave.dto.LeaveTypeDTO"%>
<%@page import="java.util.List"%>
<%@page import="com.callippus.web.beans.dto.KeyValueDTO"%>
<%@page import="net.sf.json.JSONSerializer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/leave.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<title>Leave Business Rules</title>

</head>

<body>
	<form:form method="post" commandName="leaveAdmin">
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
							<div class="headTitle">Leave Business Rules</div>
							
							<%-- Content Page starts --%>
							<div class="line">
								<div class="line"> <!-- id="Pagination" -->
								<div class="line" id="result"><jsp:include page="Result.jsp"/></div>
									<% int i = 0; %>
									<display:table name="${requestScope.leaveBusinessRules}" id="businessRules" excludedParams="*" export="false" class="list" requestURI="" sort="list"> <!-- pagesize="10" -->
										<display:column style="width:3%;" title=''>
   											<input type="checkbox" value="" class="row" name="row" id="encashed<%= i %>" disabled="true" onchange="javascript: defaultBusinessRules('<%= i %>');"/>
   											<input type="hidden" id="pk<%= i %>" value="${businessRules.id}"/>
   										</display:column>

										<display:column title="Request Type<span class='mandatory'>*</span>" style="width:5%;">
											<div class="lbredit<%= i %>">${businessRules.requestType}</div>
											<div class="lbreditview<%= i %>" style="display: none;">
												<select id="requesttype" style="width: 100%;">
													<option value="select">Select</option>
												</select>
											</div>
										</display:column>
										
										<display:column title="One<span class='mandatory'>*</span>" style="width:7%;">
											<div class="lbredit<%= i %>" title="${businessRules.leaveType1}">${businessRules.leaveCode1}</div>
											<div class="lbreditview<%= i %>" style="display: none;">
												<select id="one" style="width: 100%;">
													<option value="select">Select</option>
												</select>
											</div>
										</display:column>
										
										<display:column title="Two<span class='mandatory'>*</span>" style="width:7%;">
											<div class="lbredit<%= i %>" title="${businessRules.leaveType2}">${businessRules.leaveCode2}</div>
											<div class="lbreditview<%= i %>" style="display: none;">
												<select id="two" style="width: 100%;">
													<option value="select">Select</option>
												</select>
											</div>
										</display:column>
										
										<display:column title="Three" style="width:7%;">
											<div class="lbredit<%= i %>" title="${businessRules.leaveType3}">${businessRules.leaveCode3}</div>
											<div class="lbreditview<%= i %>" style="display: none;">
												<select id="three" style="width: 100%;">
													<option value="select">Select</option>
												</select>
											</div>
										</display:column>
										
										<display:column title="Four" style="width:7%;">
											<div class="lbredit<%= i %>" title="${businessRules.leaveType4}">${businessRules.leaveCode4}</div>
											<div class="lbreditview<%= i %>" style="display: none;">
												<select id="four" style="width: 100%;">
													<option value="select">Select</option>
												</select>
											</div>
										</display:column>
										
										<display:column title="Five" style="width:7%;">
											<div class="lbredit<%= i %>" title="${businessRules.leaveType5}">${businessRules.leaveCode5}</div>
											<div class="lbreditview<%= i %>" style="display: none;">
												<select id="five" style="width: 100%;">
													<option value="select">Select</option>
												</select>
											</div>
										</display:column>
										
										<display:column title="Condition<span class='mandatory'>*</span>" style="width:5%;">
											<div class="lbredit<%= i %>">${businessRules.condition}</div>
											<div class="lbreditview<%= i %>" style="display: none;">
												<select id="condition" style="width: 100%;">
													<option value="select">Select</option>
												</select>
											</div>
										</display:column>
										
										<display:column title="Result Value<span class='mandatory'>*</span>" style="width:5%;text-align:right;">
											<div class="lbredit<%= i %>">${businessRules.resultValue}</div>
											<div class="lbreditview<%= i %>" style="display: none;">
												<input type="text" class="lbreditview<%= i %>" style="display: none;text-align: right;" size="4" maxlength="4" value="${businessRules.resultValue}" onkeypress="javascript: return checkInt(event);" />
											</div>
										</display:column>
										
										<display:column title="Duration<span class='mandatory'>*</span>" style="width:5%;">
											<div class="lbredit<%= i %>">${businessRules.duration}</div>
											<div class="lbreditview<%= i %>" style="display: none;">
												<select id="durationtype" style="width: 100%;">
													<option value="select">Select</option>
												</select>
											</div>
										</display:column>
										
										<display:column title="Remarks<span class='mandatory'>*</span>" style="width:38%"><div class="lbredit<%= i %>">${businessRules.remarks}</div>
											<textarea cols="50" rows="1" id="remarks"  name ="remarks" class="lbreditview<%= i %>" style="display: none;">${businessRules.remarks}</textarea>
										</display:column>
										
										<display:column title="Edit" style="width:5%;text-align:center" id="edit<%= i %>">
											<img src="./images/edit.gif" title="Edit" onclick="editBusinessRules('<%= i %>')" />
										</display:column>
										
										<display:column style="width:5%;text-align:center" title="Delete">
											<img src="./images/delete.gif" title="Delete" onclick="deleteBusinessRule('${businessRules.id}')" />
										</display:column>
										<% i++; %>
									</display:table>

									<div class="line">
										<div class="half" style="padding-left: 8%;padding-top: 5px;"><input type="button" value="New Rule" onclick="javascript: createRecord()" /></div>
										<div style="display: none;float: right;padding-right: 8%;padding-top: 5px;" id="cancelRule"><input type="button" value="Cancel Rule" onclick="javascript: deleteRecord();" /></div>
									</div>
									<div class="line">
										<div style="padding-left: 45%;padding-top: 10px;">
											<div><a href="javascript: submitBusinessRules();" class="appbutton" style="text-decoration: none;">Submit</a></div>
											<!-- <div><a href="javascript: clearBusinessRules();" class="appbutton" style="text-decoration: none;">Clear</a></div> -->
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
		<form:hidden path="param" value=""/>
		<form:hidden path="type" value=""/>
		<form:hidden path="pk" value=""/>
		<form:hidden path="businessRulesList"/>
		</form:form>
		
		<script>
			//jsonRequestTypes = '<%= request.getAttribute("leaveRequestTypes") != null ? (List<KeyValueDTO>) JSONSerializer.toJSON(request.getAttribute("leaveRequestTypes")) : "" %>';
			jsonLeaveTypes = <%= session.getAttribute("leaveTypes") != null ? (JSONArray) JSONSerializer.toJSON(session.getAttribute("leaveTypes")) : "" %>;
		</script>
</body>
</html>

<!-- End : leavebusinessrulespage.jsp -->