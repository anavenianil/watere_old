<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:DoPartRoleTaskDetails.jsp -->

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

<title>Assign Designation to TaskHolder</title>
</head>

<body onload="javascript:multiSelectBox()">
	<form:form method="post" commandName="leaveAdmin">
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
								<div class="headTitle">Assign Designation to TaskHolder</div>
								<%-- Content Page starts --%>
								
								
								<div class="line" id="getResponse"></div>
								<div class="line" id="messageDisp"></div>
								
								
								<div class="line">	
									<div class="line"><%--onchange="javascript:TypeselectedDesignation()"--%>
										<div class="quarter leftmar">Task Holder<span class="mandatory">*</span></div>
										<div class="quarter">
										<form:select path="taskHolderMap" id="taskHolderMap"  onmouseover="setSelectWidth('#taskHolderMap')"  cssClass="formSelect" onchange="javascript:getAssignedDesignations()">
												<form:option value="0">Select</form:option>
											    <c:forEach var="thList" items="${sessionScope.TaskHolderDetailsList}">
													 <form:option value="${thList.id}#${thList.typeId}" id="${thList.typeId}">${thList.typeName} - ${thList.roleName}</form:option>
												</c:forEach> 
					                    </form:select>
										</div>
									</div>
									<div class="line">
										<div class="leftmar">
											<div style="float: left; width: 45%;">De Selected Desig</div>
											<div class="half" >Selected Desig</div>
										</div>
									</div>
									<div class="line">
										<div class="leftmar">
											<div style="float: left; width : 35%">
												<form:select path="designation" id="SelectLeft" size="10" multiple="true" cssStyle="width:300px">
													<!--	<form:options items="${leaveAdmin.designationList}" itemValue="id" itemLabel="name" />	-->
												</form:select>
											</div>
											<div style="float: left; width : 10%; margin-top: 60px;">									
												<div style="margin-bottom: 5px;" align="center">
												     <input style="margin-bottom: 5px" id="MoveRight" type="button" value=" Add " />
     											     <input id="MoveLeft" type="button" value=" Remove " />    											        
     											</div>		      																				
	      									</div>
											<div style="float: left; width : 30%">
												<form:select path="designation" id="SelectRight" size="10" multiple="true" cssStyle="width:300px">
												</form:select>
											</div>
										</div>
									</div>
									
									<div class="line" >
										<div class="expbutton" style="margin-left: 25%;"><a onclick="javascript:submitTaskHolderDesig()"> <span>Submit</span></a></div>
										<div class="expbutton"><a onclick="javascript:resetTaskHolderDesig1()"> <span>Clear</span></a></div>
									</div>
									<div class="line"><hr /></div>
									<div class="line" id="result">
										<jsp:include page="DoPartRoleTaskList.jsp"></jsp:include>
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
				<script type="text/javascript">
		getAllDesignations = <%= (net.sf.json.JSONArray)session.getAttribute("getAllDesignations") %>;
		</script>	
			
			
	</body>
</html>
<!-- End:DoPartRoleTaskDetails.jsp -->