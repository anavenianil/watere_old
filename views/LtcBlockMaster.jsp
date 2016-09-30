<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LtcBlockMaster.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>LTC Block Year Master</title>
<link href="./styles/layout.css" rel="stylesheet" type="text/css" />
<link href="./styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/ltc.js"></script>
</head>

<body onload="javascript:loadValidations();">
	<form:form method="post" commandName="ltcMaster" name="ltcMaster" id="ltcMaster" action="" >
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
								<div class="headTitle">Block Year</div>
								<%-- Content Page starts --%>
								<div class="line">
									<div class="line">
										<div class="quarter leftmar">Block Type</div>
										<div class="quarter">
											<form:select path="blockType" id="blockType" cssClass="formSelect" >
												<form:option value="select">Select</form:option>
												<form:options items="${ltcMaster.blockTypeList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Starting Date<span class="failure">&nbsp;*</span></div>
										<div class="quarter">
											<form:input path="startingDate" id="startingDate" cssClass="dateClass" readonly="true"/>
											<img  src="./images/calendar.gif" id="startingDateImg" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"startingDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"startingDateImg",singleClick : true,step : 1});
											</script>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Ending Date<span class="failure">&nbsp;*</span></div>
										<div class="quarter">
											<form:input path="endingDate" id="endingDate" cssClass="dateClass" readonly="true"/>
											<img  src="./images/calendar.gif" id="endingDateImg" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"endingDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"endingDateImg",singleClick : true,step : 1});
											</script>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Extended Date<span class="failure">&nbsp;*</span></div>
										<div class="quarter">
											<form:input path="extendingDate" id="extendingDate" cssClass="dateClass" readonly="true"/>
											<img  src="./images/calendar.gif" id="extendedDateImg" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"extendingDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"extendedDateImg",singleClick : true,step : 1});
											</script>
										</div>
									</div>
									<div class="line">
										<div class="quarter" style="margin-left:25%">
											<div><a href="javascript:resetBlockMaster();"  class="appbutton" style="text-decoration: none;float:right">Reset</a></div>
											<div><a href="javascript:submitBlockMaster();"  class="appbutton" style="text-decoration: none;float:right">Submit</a></div>
											
										</div>
									</div>
									<div class="line" id="result">
										<jsp:include page="LtcBlockMasterList.jsp"></jsp:include>
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
	</body>
</html>
<!-- End:LtcBlockMaster.jsp -->