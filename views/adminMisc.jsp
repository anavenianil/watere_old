<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:adminMisc.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="./styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="./styles/layout.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/misc.js"></script>

</head>

<body onload="javascript:clearFields();">
	<form:form method="post" commandName="adminMisc">
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
								<div class="headTitle" id="headTitle"></div>
								<div>&nbsp;</div>
								<div>&nbsp;</div>
								<%-- Content Page starts --%>
							<div class="line" id="result">
							 	<jsp:include page="miscResult.jsp"/>
							</div>	
								
								<div class="line">
										<c:if test="${ sessionScope.adminMiscType eq 'residential' or sessionScope.adminMiscType eq 'service'}">	
											<div class="line">
										    	<div class="leftmar quarter"> SFID<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:input path="sfID" id="sfID" value="" onkeypress="javascript:return checkSpecialChar(event);"/> 
												</div>
												<script >
													$jq('#sfID').val("");
												</script>
												
												<div class="leftmar quarter">Gazetted Type<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:select path="adminType" id="adminType" cssClass="formSelect">
														<form:option value="">Select</form:option>
														<form:option value="Gazetted">Gazetted</form:option>
														<form:option value="NonGazetted">Non-Gazetted</form:option>
													</form:select> 
												</div>
												
											</div>	

										    <div class="line">
										    	<div class="leftmar quarter">Letter No<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:input path="letterNo" id="letterNo" onkeypress="javascript:return checkChar(event)" />
												</div>
										    
										    	<div class="leftmar quarter">Letter Date<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:input path="letterDt" id="letterDt" cssClass="dateClass" readonly="true" /> 
													<img src="./images/calendar.gif" id="letterDate1" /> 
													<script type="text/javascript">
												  		Calendar.setup({inputField :"letterDt",ifFormat : "%d-%b-%Y",showsTime : false,button :"letterDate1",singleClick : true,step : 1});
													</script>
												</div>
											</div>	
											
											<div class="line">
												<div class="leftmar quarter">Purpose<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:textarea path="purpose" id="purpose" rows="3" cols="20"  />
												</div>
										    </div>											
										</c:if>	
											<div style="margin-left: 35%;">
												<a href="javascript:getAdminMiscReport('${sessionScope.adminMiscType}')" class="appbutton">Get Report</a> 
												<!-- <a href="" class="appbutton">Clear</a> -->
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
		<form:hidden path="type" id="type"/>
		</form:form>
		
		<script type="text/javascript">
			var misctype='<c:out value='${sessionScope.adminMiscType}'/>';
			miscLablesView(misctype);
		</script>
	</body>
</html>
<!-- End:adminMisc.jsp -->