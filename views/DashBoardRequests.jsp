<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin:DashBoardRequests.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />

<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script type="text/javascript" src="script/date.js"></script>

<!-- <script src="script/grid.locale-en.js" type="text/javascript"></script>
<script src="script/jquery.jqGrid.min.js" type="text/javascript"></script>
<script src="script/grid.common.js" type="text/javascript"></script>
<script src="script/grid.filter.js" type="text/javascript"></script>
<script src="script/grid.custom.js" type="text/javascript"></script>
<script src="script/grid.loader.js" type="text/javascript"></script> -->

<title>All 
		<c:if test="${workflowmap.type=='myrequest'}">My </c:if>
		<c:if test="${workflowmap.type=='pending'}">Pending </c:if>
		<c:if test="${workflowmap.type=='completed'}">Completed </c:if>
		<c:if test="${workflowmap.type=='escalated'}">Escalated </c:if>
		<c:if test="${workflowmap.type=='delegated'}">Delegated </c:if>
		<c:if test="${workflowmap.type=='hold'}">Holding </c:if>										 
	Requests</title>
</head>

<body onload="javaScript:setDates();">
	<form:form method="post" commandName="workflowmap" id="dashBoard" >
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
								<div id="headTitle" class="headTitle"></div>
								<script>$jq('#headTitle').text(document.title);</script>
								<%-- Content Page starts --%>
								<div class="line">
									<div class="line">
										<div class="line">
											<div class="expbutton" style="float:right"><a href="javascript:pisHome()"><span>Back</span></a></div>
										</div>
																									
										<div class="line">
											<div class="quarter leftmar">Request ID</div>
											<div class="quarter">
												<form:input path="requestIDValue" id="requestIDValue" cssStyle="width:145px" onkeypress="javascript:checkReqId(event,this.id,'requestId','roleId');" onclick="javascript:resetDate();"/>
											</div>
									
										</div>	
										<div class="line">	
											<div class="quarter leftmar">Request Type</div>
											<div class="quarter"  >
												<form:select path="selectedRequestType" id="selectedRequestType" cssClass="formSelect" onmouseover="setSelectWidth('#selectedValue')" onchange="javascript:resetDate();">
													<form:option value="select">Select</form:option>
													<form:options items="${sessionScope.requestTypeList}" itemValue="id" itemLabel="name"/> 
												</form:select>
											</div>
										</div>							
									</div>									
									<div class="line">
										<div class="quarter leftmar">From Date<span class="failure">&nbsp;*</span></div>
										<div class="quarter">
											<form:input path="fromDate" id="fromDate" cssClass="dateClass" readonly="true" />
											<img  src="./images/calendar.gif" id="date_start_trigger" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"fromDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger",singleClick : true,step : 1});
											</script>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">To Date<span class="failure">&nbsp;*</span></div>
										<div class="quarter">
											<form:input path="toDate" id="toDate" cssClass="dateClass" readonly="true" />
											<img  src="./images/calendar.gif" id="date_start_trigger1"  />	
											<script type="text/javascript">
												Calendar.setup({inputField :"toDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
											</script>
										</div>
									</div>
									<div class="line">
										<div class="expbutton" style="padding-left:45%"><a href="javascript:getMySearchList();"><span>Search</span></a></div>
										<div class="expbutton"><a href="javascript:resetWorkMap();"><span>Reset</span></a></div>
									</div>

									<div  id="result" class="line">
										<jsp:include page="DashBoardRequestList.jsp"></jsp:include>
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
		<form:hidden path="message"/>
		<form:hidden path="historyID"/>
		<form:hidden path="requestId"/>
		<form:hidden path="statusMsg"/>
		<form:hidden path="back"/>				
		</form:form>		
	</body>
</html>
<!-- End:DashBoardRequests.jsp -->