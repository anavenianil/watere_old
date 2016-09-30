<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TestPage.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.autoSuggest.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/script.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="script/mmgscript.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<title>Security Checking</title>
</head>


<body>
	<form:form method="post" id="securityCheck" >
		<form:hidden path="type"/>
		<form:hidden path="param"/>
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
								<div>
									<%-- Content Page starts --%>
									
										<div class="headTitle">Security Checking</div><br></br>										
										<fieldset>
										<div class="line">											
												<div class="quarter">Demand Number</div>
												<div class="quarter">
													<form:select path="demandNo" cssStyle="width:145px" onchange="javascript:getDemandDetails(demandNo);">
														<form:option value="">Select</form:option>
														<c:forEach var="demand" items="${sessionScope.demandNoList}">
															 <option value="${demand.demandNo}">${demand.demandNo}</option>
														</c:forEach>	
													</form:select>
												</div>
											    
												<div class="quarter">Demand Date</div>
												<div class="quarter" id="demandDate"></div>
										</div>
										<div class="line">
												<div class="quarter">Verified By</div>
												<div class="quarter">${sessionScope.sfid}</div>
												<div class="quarter">Remarks</div>
												<div class="quarter"><form:input path="remarks"/></div>
										</div>
									
												<div class="line">
													<div class="quarter">Memo Number</div>
													<div class="quarter"><form:input path="memoNo"/>
													</div>
													<div class="quarter">Memo Date</div>
													<div class="quarter">
													<form:input path="memoDate" id="fromDate" cssClass="dateClass" readonly="true"/>
													<img  src="./images/calendar.gif"   id="date_start_trigger1" />	
														<script type="text/javascript">
											           		Calendar.setup({inputField :"fromDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
														</script>    
													</div>
													
												</div>
												<div class="line">
													<div class="quarter">Supplier Name</div>
													<div class="quarter">
														<form:input path="supplierId"/>
													</div>
													
													
												</div>
												<div class="line">
													<div class="appbutton" style="float:right"><a href="javascript:saveSecurityDetails(demandNo);" class="quarterbutton">Save</a></div>
												</div>
											
											<div class="line">
												<div id="demandList">
													<jsp:include page="SecurityDemandItemsList.jsp" />
												</div>
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
		<form:hidden path="securityCheckJson"/>
		</form:form>
	</body>
	<script>
	
	JsonDemandList= <%= (net.sf.json.JSONArray)session.getAttribute("JsonDemandList") %>;
	</script>
</html>
<!-- End:TestPage.jsp -->		