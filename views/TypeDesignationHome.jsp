<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TypeDesignationHome.jsp -->

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

<title>Do Part Type Designation Master</title>
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
								<div class="headTitle">Do Part Type Designation Master</div>
								<%-- Content Page starts --%>
								<div class="line">	
									<div class="line">
										<div class="quarter leftmar">Type<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="typeId" id="typeId"  cssClass="formSelect" >
												<form:options items="${leaveAdmin.typeList}" itemValue="id"  itemLabel="name"/>
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="leftmar">
											<div style="float: left; width: 45%;">De - Selected Designation</div>
											<div class="half">Selected Designation</div>
										</div>
									</div>
									<div class="line" id="deselectedDiv">
										<div class="leftmar">
											<div style="float: left; width : 32%">
												<form:select path="designation" id="SelectLeft" size="10" multiple="true" cssStyle="width:335px">
													<form:options items="${leaveAdmin.deselectedDesigList}" itemValue="id" itemLabel="name" />
												</form:select>
											</div>
											<div style="float: left; width : 13%; margin-top: 60px;">									
												<div style="margin-bottom: 5px;" align="center">
												     <input style="margin-bottom: 5px" id="MoveRight" type="button" value="     Add     " />
     											     <input id="MoveLeft" type="button" value=" Remove " />    											        
     											</div>		      																				
	      									</div>
											<div style="float: left; width : 30%">
												<form:select path="designation" id="SelectRight" size="10" multiple="true" cssStyle="width:335px"></form:select>
											</div>
										</div>
									</div>
									
									<div class="line">
										<div class="quarter leftmar">Remarks</div>
										<div>										
											<form:textarea path="remarks" cols="32" rows="3" id="remarks" />
										</div>
									</div>
									
									<div class="line" >
										<div class="expbutton" style="margin-left: 30%;"><a onclick="javascript:submitTypeDesigDetails()"> <span>Submit</span></a></div>
										<div class="expbutton"><a onclick="javascript:resetTypeDesigDetails()"> <span>Clear</span></a></div>
									</div>
									<div class="line"><hr /></div>
									<div class="line" id="result">
										<jsp:include page="TypeDesignationList.jsp"></jsp:include>
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
				<script >
		designationListJSON = <%= (net.sf.json.JSONArray)session.getAttribute("designationListJSON") %>;
		deselectedDesig=<%= (net.sf.json.JSONArray)session.getAttribute("deselectedDesig") %>;
		</script>	
				
	</body>
</html>
<!-- End:TypeDesignationHome.jsp -->