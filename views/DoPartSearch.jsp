<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:DoPartSearch.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/leave.js"></script>
<script type="text/javascript" src="script/date.js"></script>

<title>Do Part Search</title>
</head>

<body>
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
								<div class="headTitle">Do Part Search</div>
								
								<%-- Content Page starts --%>
								<div class="line">
								<div class="line">
										<div class="quarter leftmar">SFID</div>
										<div class="quarter">
											<form:input path="searchSfid" id="searchSfid" maxlength="50"/>
										</div>
									</div>
								<div class="line">
								<div class="quarter leftmar">Gazetted Type</div>
								
								<form:select path="gazettedType" id="gazettedType"  cssClass="formSelect">
									<form:option value="">Select</form:option>
									<form:options items="${sessionScope.DopartType}" itemValue="id"  itemLabel="name"/>
								</form:select>
								
								
								
								</div>
									<div class="line">
										<div class="quarter leftmar">Do Part Number</div>
										<div class="quarter">
											<form:input path="doPartNo" id="doPartNo" maxlength="50"/>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Do Part Date</div>
										<div class="quarter">
											<form:input path="doPartDate" id="doPartDate" cssClass="dateClass" readonly="true" />
											<img  src="./images/calendar.gif" id="doPartDateImg" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"doPartDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"doPartDateImg",singleClick : true,step : 1});
											</script>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Year</div>
										<div class="quarter">
											<form:select path="year" id="year">
												<form:option value="">Select</form:option>
												<form:options items="${YEARS}" itemValue="name" itemLabel="name" />
											</form:select>
										</div>
									</div>
									<div class="line">
										<div style="margin-left: 25%; float: left;">
											<div style="float:left"><a href="javascript:doPartSearch();"  class="appbutton" style="text-decoration: none;float:right">Search</a></div>
											<div style="float:left"><a href="javascript:resetDoPartSearch();" class="appbutton" style="text-decoration: none;float:right">Clear</a></div>
										</div>
									</div>
									<div class="line"><hr /></div>
									<div id="result">
										<jsp:include page="DoPartSearchList.jsp"></jsp:include>										
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
<!-- End:DoPartSearch.jsp -->