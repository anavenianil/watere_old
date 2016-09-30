<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LtcMasterData.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/ltc.js"></script>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/date.js"></script>
<link href="styles/calendar.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="script/calendar-hd.js"></script>
<script type="text/javascript" src="script/calendar_us.js"></script>
<script type="text/javascript">
	$jq(document).ready(function(){
		lables(type);
		clearLtcMaster();
	});
</script>
</head>

<body>
	<form:form method="post" commandName="ltcMaster" name="ltcMaster" id="ltcMaster">
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
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</img></div>
								<%-- Content Page starts --%>
								<div>
									
								<div class="line">
								<c:choose>
								
								<c:when test="${sessionScope.masterType eq 'ltcBlockYear'}">
									<div class="line">
										<div class="quarter bold" style="margin-left:8px;">LTC Block<span class='failure'>*</span></div>
										<div class="quarter">
											<form:select path="ltcBlockId" id="ltcBlockId" cssStyle="width:145px;" onchange="javascript:enableBlockYearDiv();">
												<form:option value="Select" label="Select"></form:option>
												<form:options  items="${sessionScope.ltcBlockList}" itemLabel="name" itemValue="id"></form:options>
											</form:select>
										</div>
									</div>
									<div id="blockYearDiv" style="display:none">
										<div class="line">
											<div class="quarter bold" style="margin-left:8px;">Four Year Block<span class='failure'>*</span></div>
											<div class="quarter">
												<form:select path="fourYearBlockId" id="fourYearBlockId" cssStyle="width:145px;">
													<form:option value="Select" label="Select"></form:option>
													<form:options  items="${sessionScope.ltcBlockYearList}" itemLabel="name" itemValue="key"></form:options>
												</form:select>
											</div>
										</div>
									</div>
									<div class="line">
										<div class="quarter bold" style="margin-left:8px;">From Date<span class='failure'>*</span></div>
										<div class="quarter">
											<form:input path="fromDate" id="fromDate" readonly="true"/> 
												<script type="text/javascript">
											         new tcal({'formname':'ltcMaster','controlname':'fromDate'});
											</script>
										</div>
									</div>
									<div class="line">
										<div class="quarter bold" style="margin-left:8px;">To Date<span class='failure'>*</span></div>
										<div class="quarter">
											<form:input path="toDate" id="toDate" readonly="true"/> 
												<script type="text/javascript">
											         new tcal({'formname':'ltcMaster','controlname':'toDate'});
											</script>
										</div>
									</div>
									<div class="line">
										<div class="quarter bold" style="margin-left:8px;">Extended Date</div>
										<div class="quarter">
											<form:input path="extendedDate" id="extendedDate" readonly="true"/> 
												<script type="text/javascript">
											         new tcal({'formname':'ltcMaster','controlname':'extendedDate'});
											</script>
										</div>
									</div>
								</c:when>
								
								<c:when test="${sessionScope.masterType eq 'ltcPenalInterestMaster'}">
									<div class="line">
										<div class="quarter bold" id="labelType"></div>
										<div class="quarter"><form:input path="typeValue" id="typeValue" maxlength="50" onkeypress="return isNumberExp(event);"/></div>
									</div>
									<div class="line">
										<div class="quarter bold" style="margin-left:8px;">From Date<span class='failure'>*</span></div>
										<div class="quarter">
											<form:input path="fromDate" id="fromDate" readonly="true"/> 
												<script type="text/javascript">
											         new tcal({'formname':'ltcMaster','controlname':'fromDate'});
											</script>
										</div>
									</div>
									<div class="line">
										<div class="quarter bold" style="margin-left:8px;">To Date<span class='failure'>*</span></div>
										<div class="quarter">
											<form:input path="toDate" id="toDate" readonly="true"/> 
												<script type="text/javascript">
											         new tcal({'formname':'ltcMaster','controlname':'toDate'});
											</script>
										</div>
									</div>
									<div class="line">
										<div class="quarter bold" style="margin-left:8px;">Description</div>
										<div class="quarter"><form:textarea path="description" id="description" cols="20" rows="3" onkeypress="textCounter(event,$jq('#description'),$jq('#counter'),250);"
										 onkeyup="textCounter(event,$jq('#description'),$jq('#counter'),250);"></form:textarea>
											<input type="text" class="counter" name="counter" value="250" id="counter" disabled="disabled"></input>
										</div>
									</div>
								</c:when>
								
								
								<c:otherwise>
									<div class="line">
										<div class="quarter bold" id="labelType"></div>
										<div class="quarter"><form:input path="typeValue" id="typeValue" maxlength="50"  onkeypress="return isAlphabetExp(event);" /></div>
									</div>
									<div class="line">
										<div class="quarter bold" style="margin-left:8px;">Description</div>
										<div class="quarter"><form:textarea path="description" id="description" cols="20" rows="3" onkeypress="textCounter(event,$jq('#description'),$jq('#counter'),250);"
										 onkeyup="textCounter(event,$jq('#description'),$jq('#counter'),250);"></form:textarea>
											<input type="text" class="counter" name="counter" value="250" id="counter" disabled="disabled"></input>
										</div>
									</div>
								</c:otherwise>
								
								</c:choose>
									<div class="line">
										<div style="margin-left:25%;"><a href="javascript:manageLtcMaster('${sessionScope.masterType}');"><div class="appbutton">Submit</div></a></div>
										<div><a href="javascript:clearLtcMaster('${sessionScope.masterType}');"><div class="appbutton">Clear</div></a></div>  
									</div>
									<div class="line">
										<div id="result" class="line">
											<jsp:include page="LtcMasterDataList.jsp" />
										</div>
									</div>
								</div>
								<%-- Content Page end --%>
								<div>&nbsp;</div>
							</div>
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
			var type='<c:out value='${sessionScope.masterType}'/>';
			
		</script>
	</body>
</html>
<!-- End:LtcMasterData.jsp -->