<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin:LocalAssessmentBoard.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
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
<script type="text/javascript" src="script/promotions.js"></script>

<title>Local Assessment Board</title>

</head>
<body onload="javascript:multiSelectBox();">
	<form:form method="post" commandName="promotion" id="PromotionManagementBean">
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
								<div class="headTitle">Local Assessment Board</div>
								<%-- Content Page starts --%>
								<div class="line">
								    <div class="line">
										<div class="quarter leftmar">Category Type<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="assessmentTypeID" id="assessmentTypeID"  cssClass="formSelect" onchange="getCategoryLocalList()">
												<form:option value="0">Select</form:option>
												<form:options items="${promotion.assessmentTypeList}" itemValue="id"  itemLabel="name"/>
											</form:select>
										</div>
									</div>
								
									<div class="line">
										<div class="quarter leftmar">Year<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="yearID" id="yearID"  cssClass="formSelect" onchange="getLocalAssessmentList()">
												<form:option value="0">Select</form:option>
												<form:options items="${promotion.yearList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Name of the Board<span class="failure">*</span></div>
										<div class="quarter">
											<form:input path="boardName" id="boardName" onkeypress="return checkSpecialChar(event);"></form:input>
										</div>
									</div>
										<div class="line">
										<div class="quarter leftmar">Designation</div>
										<div class="quarter">
											<form:select path="desigID" id="desigID"  cssClass="formSelect" onchange="getLocalDesigList()">
												<form:option value="0">Select</form:option>
												<form:options items="${promotion.designationList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="leftmar">
											<div style="float: left; width: 45%;">All Members</div>
											<div class="half" >Selected Members</div>
										</div>
									</div>
									
									<div class="line">
										<div class="leftmar">
											<div style="float: left; width : 35%">
												<form:select path="boardMembers" id="SelectLeft" size="10" multiple="true" cssStyle="width:300px">
													<form:options items="${promotion.localBoardMembersList}" itemValue="key" itemLabel="value"/>
												</form:select>
											</div>
											<div style="float: left; width : 10%; margin-top: 60px;">									
												<div style="margin-bottom: 5px;" align="center">
												     <input style="margin-bottom: 5px" id="MoveRight" type="button" value=" Add " />
     											     <input id="MoveLeft" type="button" value=" Remove " />    											        
     											</div>		      																				
	      									</div>
											<div style="float: left; width : 30%">
												<form:select path="boardMembers" id="SelectRight" size="10" multiple="true" cssStyle="width:300px">
												</form:select>
											</div>
										</div>
									</div>
									<div class="line"  style="margin-left: 30%;">
										<div class="half">
											<div class="expbutton"><a onclick="javascript:submitLocalBoardDetails()"> <span>Submit</span></a></div>
											<div class="expbutton"><a onclick="javascript:clearLocalBoardDetails()"> <span>Clear</span></a></div>
										</div>
									</div>	
									<div class="line" id="result">
										<jsp:include page="LocalAssessmentBoardList.jsp" />								
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
		</form:form>
		<script>
			membersListJSON= <%= (net.sf.json.JSONArray)session.getAttribute("membersListJSON") %>;
		</script>		
				
	</body>
</html>
<!-- End:LocalAssessmentBoard.jsp -->