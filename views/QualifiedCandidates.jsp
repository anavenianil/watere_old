<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:QualifiedCandidates.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/calendar-hd.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/promotions.js"></script>
<script type="text/javascript" src="script/date.js"></script>

<title>Eligible Candidates List with Interview Dates</title>
</head>

<body onload="javascript:resetQual()">
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
								<div class="headTitle">Eligible Candidates List with Interview Dates</div>
								<%-- Content Page starts --%>
								<div class="line">
								<div class="line">
										<div class="quarter leftmar">Year<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="yearID" id="yearID"  cssClass="formSelect" onchange="javascript:getYearWiseAssessmentList(this,'qualifiedCandidates');">
												<form:option value="0">Select</form:option>
												<form:options items="${promotion.yearList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
									</div>	
								 <div class="line">
										<div class="quarter leftmar">Assessment Category Type<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="assessmentTypeID" id="assessmentTypeID" onchange="getCategoryDesignationList();enbleDependentQualifiedCan()" cssClass="formSelect" >
												<form:option value="0">Select</form:option>
												<form:options items="${promotion.assessmentTypeList}" itemValue="id"  itemLabel="name"/>
											</form:select>
										</div>
									</div>
	                                    
								<!--   
									<div class="line">
										<div class="quarter leftmar">Board Type<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="assessmentCategoryID" id="assessmentCategoryID"  cssClass="formSelect" onchange="javascript:enbleDependentQualifiedCan()">
												<form:option value="0">Select</form:option>
												<form:options items="${promotion.assessmentCategoryList}" itemValue="id" itemLabel="category"/>
											</form:select>
										</div>
									</div>
								 -->	
									<div class="line">
										<div class="quarter leftmar">Designation</div>
										<div class="quarter">
											<form:select path="designationFrom" id="designationFrom" onmouseover="setSelectWidth('#designationFrom')" cssClass="formSelect" >
												<form:option value="0">Select</form:option>
												<form:options itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
									</div>
																			
									<div class="line" style="margin-left: 15%;">
										<div class="expbutton"><a onclick="javascript:searchQualifiedCandidates()"> <span>Search</span></a></div>
										<div class="expbutton"><a onclick="javascript:resetQualifiedCandidates()"> <span>Clear</span></a></div>
										<div class="expbutton"><a onclick="javascript:getPromotionReport('QualifiedCandidates')"> <span>Qualified list</span></a></div>
										<div class="expbutton"><a onclick="javascript:getPromotionIONReport('QualifiedION')"> <span>Generate ION</span></a></div>
									</div>
									<div class="line"><hr /></div>
								 <div  id="interview" style="display:none">
									<div class="line" >
										<div class="quarter leftmar">Interview Date<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:input path="interviewDate" id="interviewDate" cssClass="dateClass" readonly="true" />
											<img  src="./images/calendar.gif" id="interviewDateImg" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"interviewDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"interviewDateImg",singleClick : true,step : 1});
											</script>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Interview Venue<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="venueID" id="venueID"  cssClass="formSelect" >
												<form:option value="0">Select</form:option>
												
											</form:select>
										</div>
									</div>
									</div>
									<div class="line" id="written" style="display:none">
										<div class="quarter leftmar">Written Test:</div>
										<div class="quarter">
										  <form:input path="WrittenDate" id="WrittenDate" cssClass="dateClass" readonly="true"/>
											<img  src="./images/calendar.gif" id="WrittenDateImg" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"WrittenDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"WrittenDateImg",singleClick : true,step : 1});
											</script>
										</div>
									</div>
									<div class="line" id="trade" style="display:none">
										<div class="quarter leftmar">Trade Test</div>
										<div class="quarter" >
										   <form:input path="tradeDate" id="tradeDate" cssClass="dateClass" readonly="true" />
											<img  src="./images/calendar.gif" id="tradeDateImg" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"tradeDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"tradeDateImg",singleClick : true,step : 1});
											</script>
										</div>
									</div>		
									<div class="line" id="labRepresentativeDiv" style="display:none">
										<div class="quarter leftmar">Lab Representative(SFID)</div>
										<div class="quarter">
											<form:input path="labRepresentative" id="refSfid" onblur="checkDesignation()" />	
										</div>
									<div class="half" id="result1">
		                            <jsp:include page="NameDesignation.jsp"></jsp:include>
	                                 </div>
									</div>											
									<div class="line"  id="boardDiv" style="display:none">
										<div class="quarter leftmar">Local Board Name<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="boardID" id="boardID"  cssClass="formSelect" >
												<form:option value="0">Select</form:option>
												
											</form:select>
										</div>
									</div>	
										<div class="line" style="margin-left: 28%;">
										<div class="expbutton"><a onclick="javascript:submitQualifiedCandidates()"> <span>Submit</span></a></div>
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
		<form:hidden path="type"/>
		<form:hidden path="param"/>	
		</form:form>	
		
		<script type="text/javascript">
		venueMasterJSON = <%=  session.getAttribute("venueMasterJSON") %>;
		designationListJSON= <%= (net.sf.json.JSONArray)session.getAttribute("designationListJSON") %>;
		boardMasterJSON = <%= (net.sf.json.JSONArray)session.getAttribute("boardMasterJSON") %>
		yearID = ${promotion.yearID} 
		
		
		
		</script>	
	</body>
</html>
<!-- End:QualifiedCandidates.jsp -->