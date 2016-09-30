<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin:residencyPeriodMaster.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/promotions.js"></script>

<title>Configure Residency Period</title>

</head>
<body onload="javascript:clearResidencyPeriodDetails()">
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
								<div class="headTitle">Residency Period Master</div>
								<%-- Content Page starts --%>
								<div class="line">
									<div class="line">
										<div class="quarter leftmar">Category Type<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="assessmentTypeID" id="assessmentTypeID" onchange="getDesignations()" cssClass="formSelect" >
												<form:option value="0">Select</form:option>
												<form:options items="${promotion.assessmentTypeList}" itemValue="id"  itemLabel="name"/>
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Board Type<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="assessmentCategoryID" id="assessmentCategoryID" onchange="getDesignations();ldceDesignationList();" cssClass="formSelect" >
												<form:option value="0">Select</form:option>
												<form:options items="${promotion.assessmentCategoryList}" itemValue="id" itemLabel="category"/>
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Designation From<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="designationFrom" id="designationFrom" onmouseover="setSelectWidth('#designationFrom')" cssClass="formSelect" >
												<form:option value="0">Select</form:option>
												<form:options items="${promotion.designationList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Designation To<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="designationTo" id="designationTo" onmouseover="setSelectWidth('#designationTo')" cssClass="formSelect" >
												<form:option value="0">Select</form:option>
												<form:options items="${promotion.designationList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Experience(Years)<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:input path="residencyPeriod" id="residencyPeriod" maxlength="2" onkeypress="return checkInt(event);"></form:input>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Relaxation in Experience (Months)</div>
										<div class="quarter">
											<form:input path="relaxationInMonths" id="relaxationInMonths" maxlength="2" onkeypress="return checkInt(event);"></form:input>
										</div>
									</div>			
									<div class="line">
										<div class="quarter leftmar">Written Test:<span class="mandatory">*</span></div>
										<div class="quarter">
										   <form:radiobutton path="Written" id="Written" name="Written" value="1"/><label>Yes</label>
									       &nbsp&nbsp&nbsp
									       <form:radiobutton path="Written" id="Written" name="Written" value="2"/><label>No</label>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Trade Test:<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:radiobutton path="trade" id="trade" name="trade" value="1"/><label>Yes</label>
									       &nbsp&nbsp&nbsp
									       <form:radiobutton path="trade" id="trade" name="trade" value="2"/><label>No</label>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Interview:<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:radiobutton path="interview" id="interview" name="interview" value="1"/><label>Yes</label>
											&nbsp&nbsp&nbsp
									       <form:radiobutton path="interview" id="interview" name="interview" value="2"/><label>No</label>
										</div>
									</div>	
									<div class="line">
										<div class="quarter leftmar">Board Type:<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:radiobutton path="board" id="board" name="board" value="1"/><label>Public</label>
									      
									       <form:radiobutton path="board" id="board" name="board" value="2"/><label>Private</label>
										</div>
									</div>					
								<!-- 	<div class="line">
						   				<div class="quarter leftmar">Date From<span class="mandatory">*</span></div>
						   				<div class="quarter">						   			
						    				<form:input path="dateFrom" id="dateFrom" cssClass="dateClass" readonly="true"/>
								    		<img  src="./images/calendar.gif" id="fromDateImg" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"dateFrom",ifFormat : "%d-%b",showsTime : false,button :"fromDateImg",singleClick : true,step : 1});
											</script>
										</div>
						   			</div>
						   			<div class="line">
						   				<div class="quarter leftmar">Date To<span class="mandatory">*</span></div>
						   				<div class="quarter">						   			
						    				<form:input path="dateTo" id="dateTo" cssClass="dateClass" readonly="true"/>
								    		<img  src="./images/calendar.gif" id="toDateImg" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"dateTo",ifFormat : "%d-%b",showsTime : false,button :"toDateImg",singleClick : true,step : 1});
											</script>
										</div>
						   			</div> -->
						   			<div class="line" style="margin-left: 20%;">
											<div class="expbutton"><a onclick="javascript:submitResidencyPeriodDetails()"> <span>Submit</span></a></div>
											<div class="expbutton"><a onclick="javascript:clearResidencyPeriodDetails()"> <span>Clear</span></a></div>
											<div class="expbutton"><a onclick="javascript:getResidencyPeriodReport()"> <span>Report</span></a></div>
									</div>	
									<div class="line"><hr /></div>
									<div class="line" id="result">
																		
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
		<script type="text/javascript">
		designationListJSON = <%= (net.sf.json.JSONArray)session.getAttribute("designationListJSON") %>;
		totoalDesignationListJSON = <%= (net.sf.json.JSONArray)session.getAttribute("totoalDesignationListJSON") %>;
		</script>	
			
	</body>
</html>
<!-- End:residencyPeriodMaster.jsp -->