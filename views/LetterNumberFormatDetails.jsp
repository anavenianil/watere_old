<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LetterNumberFormatDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
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
<script type="text/javascript" src="script/letternumberformat.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<script language="javascript" src="./script/date.js"></script>
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>

<title>Letter Number Format Details</title>
<script type="text/javascript">
	$jq(document).ready(function(){
	 clearLetterNumberFormatDetails();
		});
</script>
</head>

<body>
	<form:form method="post" commandName="letterNumberFormat" id="letterNumberFormat">
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
								<div class="headTitle">Letter Number Format Details</div>
								<%-- Content Page starts --%>
								<div>
									
									
																	
								
									<div class="line">
										
									</div>
									<div class="line">
										<div  class="quarter bold leftmar">Letter Number<span class="failure">*</span> <br>(Auto Incremented)</div>
										<div ss class="quarter">
											<form:input path="letterNumber" id="letterNumber" maxlength="70" size="45" readonly="true"/>
										</div>
										<div class="quarter bold leftmar" style="text-align: center">Date<span class="mandatory">*</span></div>
										<div ss class="quarter">
											<form:input path="ionDate" id="ionDate" cssClass="dateClass" readonly="true" />
				
			    									 <img  src="./images/calendar.gif"   id="date_start_trigger1" />	
													<script type="text/javascript">
			           																Calendar.setup({inputField :"ionDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
													</script> 
										</div>
										
										
										
									</div>
									<div class="line">
										<div  class="quarter bold leftmar">Delegation Allowed<span class="failure">*</span></div>
										<div ss class="quarter">
											<input type="radio" name="delegationsType" id="delegationy" value="yes" onClick="javascript:changeDelegationStatus();"/>Yes
		                                     <input type="radio" name="delegationsType" id="delegationn"  value="no" onClick="javascript:changeDelegationStatus();"/>No
										
										</div>
									</div>
									<div class="line" id="result1">
										<div  class="quarter bold leftmar">Delegation <span class="failure">*</span></div>
										<div ss class="quarter">
											<form:textarea path="delegation" id="delegation" cols="20" rows="3" onkeypress="textCounter(event,$jq('#delegation'),$jq('#counter'),500);"></form:textarea>
											<input type="text" class="counter" name="counter" value="500" id="counter" disabled=""/>
										
										</div>
									</div>
									
									
									<div class="line" style="margin-left: 15%;">
										<div class="expbutton" style="margin-left: 15%;" id="editIncreButton"><a href="javascript:manageLetterNumberFormatDetails(jsonIonMstList);"><span>Increment</span></a></div>
										<div class="expbutton" style="margin-left: 15%; display: none;" id="editSubmitButton"><a href="javascript:manageLetterNumberFormatDetails(jsonIonMstList);"><span>Submit</span></a></div>
										<a href="javascript:clearLetterNumberFormatDetails();"><div class="appbutton">Clear</div></a>
											<div class="expbutton"><a href="javascript:gotoLetterNumberFormat();"><span>Go To Letter Number Format</span></a></div>
											
									</div>
									
									<div class="line height"><hr/></div>
									<div class="line" id="displayTable">
										<jsp:include page="ionDataList.jsp"></jsp:include>
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
		<form:hidden path="param" id="param"/>
		<form:hidden path="type" id="type" />
		<form:hidden path="id" id="id"/>
		<form:hidden path="yearType" id="yearType"/>
		<form:hidden path="deptSeriesStartNum" id="deptSeriesStartNum"/>
		<form:hidden path="deptSeriesEndNum" id="deptSeriesEndNum"/>
		
		<form:hidden path="back" id="back"/>
		<form:hidden path="deptNum" id="deptNum"/>
		<form:hidden path="delegationType" id="delegationType"/>
		<form:hidden path="letterFormatId" id="letterFormatId"/>
		<form:hidden path="ionScreenType" id="ionScreenType"/>
		<c:if test="${sessionScope.LetterNumberEditFlag =='flag'}">	
		<input type="text" name="backType" id="backType"/>
		</c:if>
		<script>
		
		 jsonIonMstList = <%=(net.sf.json.JSONArray)session.getAttribute("jsonIonMstList")%>
		
		
			
		
		</script>
		
		
		</form:form>
		
	</body>
</html>
<!-- End:LetterNumberFormatDetails.jsp-->