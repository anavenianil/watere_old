<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:HindiIncentiveDetailsMaster.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<%@page import="net.sf.json.JSONArray"%><html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<script language="javascript" src="script/date.js"></script>
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/hindi.js"></script>

<title>Pay Bill</title>
</head>
<body>
   <form:form method="post" commandName="hindi" id="hindiIncentive">
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
								<div class="headTitle">Hindi Incentive Details</div>
								<div>
									<%-- Content Page starts --%>	
								 <div class="line" id="payrunmonth" style="display:none;">
										<div class="half" style="padding-left: 70%;width: 280px;"><strong>Pay Run Month:&nbsp;&nbsp;<font color="green">${hindi.payRunMonth}</font></strong></div>
								 </div>	
								 <div class="line">
										<div class="quarter leftmar">Enter SFID<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:input path="sfidSearch" id="sfidSearch"/>
										</div>
										<div class="expbutton"><a href="javascript:getEmployeeDetails();"><span>Search</span></a></div>
									</div>
									
									<div id="reminingIncentiveDiv" style="display:none;"> </div>
					      <%--<div class="line">
							     	<div class="quarter leftmar" style="margin-left: 8px;">SFID<span class='failure'>*</span></div>
									<div class="quarter"><form:input path="sfid" id="sfid" maxlength="100"></form:input></div>		
	                              </div>								  
	                              
	                              <div class="line">
		                                 	<div class="quarter leftmar" style="width: 24%;">Start Date<span class="mandatory">*</span></div>
	                                  		<div class="quater">
	                                   			<form:input path="fromDate" id="fromDate" cssClass="required" readonly="true" />
				                               	<img  src="./images/calendar.gif"   id="date_start_trigger1" />	
				                                <script type="text/javascript">
					                              	Calendar.setup({inputField :"fromDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
				                                </script>
	                                  		</div>
	                                  		
	                                  		<div class="quarter leftmar" style="width: 24%;">End Date<span class="mandatory">*</span></div>
	                                  		<div class="quater">
	                                   			<form:input path="toDate" id="toDate" cssClass="required" readonly="true" />
				                               	<img  src="./images/calendar.gif"   id="date_start_trigger2" />	
				                                <script type="text/javascript">
					                              	Calendar.setup({inputField :"toDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger2",singleClick : true,step : 1});
				                                </script>
	                                  		</div>
	                                  		
                                   	</div>	
                                   	
                                   <div class="line">
							     	<div class="quarter leftmar" style="margin-left: 8px;">Total Instalments<span class='failure'>*</span></div>
									<div class="quarter"><form:input path="noOfInst" id="noOfInst" maxlength="100"></form:input></div>		
	                              </div>
	                              <div class="line">
							     	<div class="quarter leftmar" style="margin-left: 8px;">Total Amount<span class='failure'>*</span></div>
									<div class="quarter"><form:input path="totalAmount" id="totalAmount" maxlength="100"></form:input></div>		
	                              </div>    		
								<div class="line" style="margin-left: 25%;">
												<div class="expbutton"><a onclick="javascript:saveIncentiveDetails();"> <span>Submit</span></a></div>
												<div class="expbutton"><a onclick="javascript:clearIncentiveDetails();"> <span>Clear</span></a></div>
								</div>	
								<div id="result" class="line"><jsp:include page="HindiIncentiveList.jsp" /></div>
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
		<form:hidden path="param" id="param"/>
		<form:hidden path="pk" id="pk"/> 
		<form:hidden path="creationDate" id="creationDate"/>
		<form:hidden path="createdBy" id="createdBy"/>
		<!--  <input:type="hidden" id="incentiveSfid"></input:type>  -->     
   
   </form:form>
</body>
 <script>
var sfidList = <%= (net.sf.json.JSONArray)session.getAttribute("sfidJsonList") %>

</script>
</html>