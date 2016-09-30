<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin: emuDetailsHome.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>

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
<script type="text/javascript" src="script/pisscript.js"></script>
</head>

<body>
	<form:form method="post" commandName="quarterRequest">
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
									
										<%-- Content Page starts --%>
								<div>&nbsp;</div>
						<c:if test="${sessionScope.quarterTitleType ne 'vacationCmpl'}">	
							<div class="line">
    							<div class="leftmar quarter">Letter Number<span class="mandatory">*</span></div>
								<div class="quarter">
									<form:select path="letterNo" id="letterNo" cssClass="formSelect" onchange="javascript:setEmuLettDate();" onmouseover="javascript:setSelectWidth(this);">
			  							<form:option value="Select" label="Select"></form:option>
			  							<form:options items="${sessionScope.letterNumberList}" itemValue="id" itemLabel="letterNumber"></form:options>
									</form:select>
								</div>
            					<div class="leftmar quarter">Letter Date<span class="mandatory">*</span></div>
								<div class="quarter">
									<form:input path="letterDt" id="letterDt" cssClass="dateClass" readonly="true" /> 
									<img src="./images/calendar.gif" id="letterDt1" /> 
										<script	type="text/javascript">
		  				Calendar.setup({inputField :"letterDt",ifFormat : "%d-%b-%Y",showsTime : false,button :"letterDt1",singleClick : true,step : 1});
										</script>
								</div>
    						</div>
    						<div>&nbsp;</div>
    						<div>&nbsp;</div>
    					</c:if>
	
						<div class="line" id="result"><jsp:include page="Result.jsp" /></div>
		<div id="dataTable">
			<% int i = 0; %>
			<display:table name="${sessionScope.emuSFID}" excludedParams="*" export="false" class="list" requestURI="" id="emu" pagesize="50" sort="list" cellpadding="2" cellspacing="1">
				<c:if test="${sessionScope.quarterTitleType eq 'occupied' or sessionScope.quarterTitleType eq 'alloted' or sessionScope.quarterTitleType eq 'vacated' or sessionScope.quarterTitleType eq 'vacationCmpl'}">	
					<c:if test="${sessionScope.quarterTitleType ne 'vacationCmpl'}">	
						<display:column style="width:3%;vertical-align:middle" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name, \'row\');"/>'>
							<input type="checkbox" class="row" name="row" id="adv<%= i %>" onclick="checkBoxCheck(this.name);"/>
						</display:column>
					</c:if>
					<display:column title="SFID" style="width:6%;vertical-align:middle">
						<input type="text" class="row" name="sfID" id="sfID<%= i %>" size="7" value="${emu.sfID}" readonly="readonly" />
						<c:if test="${sessionScope.quarterTitleType eq 'alloted'}">
					  		<input type="hidden" class="row" name="letterNo" id="letterNo<%= i %>" size="7" value="${emu.letterNo}" readonly="readonly" /></c:if>
						<c:if test="${sessionScope.quarterTitleType eq 'vacated'}">
					  		<input type="hidden" class="row" name="occupiedId" id="occupiedId<%= i %>" size="7" value="${emu.occupiedId}" readonly="readonly" /></c:if>
					</display:column>
					<display:column title="RequestID" style="width:6%;vertical-align:middle">
						<input type="text" class="row" name="requestID" id="requestID<%=i %>" size="7" value="${emu.requestID}" readonly="readonly" />
					</display:column>
				</c:if>
			
				<c:if test="${ sessionScope.quarterTitleType eq 'alloted' }">	
					<display:column title="Quarter Type <span class='mandatory'>*</span>" style="width:15%;vertical-align:middle"> 
						<select name="quarterType" id="quarterType<%= i %>" style="width:150px">
							<option value="">Select</option>
							<c:forEach var="acc" items="${quarterRequest.quarterSubTypeList}">
								<option value="${acc.id}">${acc.quarterSubType}</option></c:forEach>
						</select>
					</display:column>				
					<display:column title="Quarter No <span class='mandatory'>*</span>" style="width:15%;vertical-align:middle">
						<input type="text" class="row" name="quarterNo" id="quarterNo<%= i %>" size="10"/>
					</display:column>
					<display:column title="Alloted Date <span class='mandatory'>*</span>" style="width:15%;vertical-align:middle">&nbsp; 
						<input type="text" size="13"  readonly="readonly" name="allotedDt" id="allotedDt<%= i %>"/>
						<img src="./images/calendar.gif" id="allDt<%=i %>" /> 
							<script	type="text/javascript">
		Calendar.setup({inputField :"allotedDt<%=i %>",ifFormat : "%d-%b-%Y",showsTime : false,button :"allDt<%= i %>",singleClick : true,step : 1});
							</script>
					</display:column>
					<display:column title="EmuRequest Report" style="width:12%;">&nbsp; 
						<a href="javascript:adminNoteReport('${emu.letterNo}','${emu.requestTypeID}');" ><b>&nbsp;PDF</b></a>
					</display:column>
				</c:if>	
				
				<c:if test="${sessionScope.quarterTitleType eq 'occupied' or sessionScope.quarterTitleType eq 'vacated' or sessionScope.quarterTitleType eq 'vacationCmpl'}">
					<display:column title="Quarter Type" style="width:10%;vertical-align:middle">
					    <input type="text" class="row" name="quarterType" id="quarterType<%= i %>" size="22" value="${emu.quarterType}" readonly="readonly" />
					</display:column>
					<display:column title="Quarter No" style="width:10%;vertical-align:middle">
						<input type="text" class="row" name="quarterNo" id="quarterNo<%= i %>" value="${emu.quarterNo}" readonly="readonly" />
					</display:column>
					<display:column title="Alloted Date" style="width:10%;vertical-align:middle">
						<input type="text" class="row" size="10" name="allotedDt" id="allotedDt<%= i %>" value="${emu.allotedDt}" readonly="readonly" />
					</display:column>
				</c:if>		
									
				<c:if test="${sessionScope.quarterTitleType eq 'occupied'}">
					<display:column title="Occupied Date <span class='mandatory'>*</span>"style="width:15%;vertical-align:middle">
						<input type="text" size="13"  readonly="readonly"  name="occupiedDt" id="occupiedDt<%= i %>" onchange="javascript:checkOccupiedDate(<%=i %>,'occupied');" />
						<img src="./images/calendar.gif" id="occDt<%=i %>" /> 
							<script	type="text/javascript">
		Calendar.setup({inputField :"occupiedDt<%=i %>",ifFormat : "%d-%b-%Y",showsTime : false,button :"occDt<%= i %>",singleClick : true,step : 1});
							</script>
					</display:column>
				</c:if>
				
				<c:if test="${ sessionScope.quarterTitleType eq 'vacated'}">	
			    	<display:column title="Occupied Date" style="width:10%;vertical-align:middle">
						<input type="text" class="row" size="10" name="occupiedDt" id="occupiedDt<%= i %>" value="${emu.occupiedDt}" readonly="readonly" />	
					</display:column>
					<display:column title="Vacation Date <span class='mandatory'>*</span>" style="width:25%;vertical-align:middle">
						<input type="text" size="10"  readonly="readonly" name="vacatedDt" id="vacatedDt<%= i %>" onchange="javascript:checkOccupiedDate(<%=i %>,'vacated');" />
						<img src="./images/calendar.gif" id="vacDt<%= i %>" /> 
							<script	type="text/javascript">
		Calendar.setup({inputField :"vacatedDt<%=i %>",ifFormat : "%d-%b-%Y",showsTime : false,button :"vacDt<%=i %>",singleClick : true,step : 1});
							</script>
					</display:column>
					<display:column title="Quarter Occupied Report" style="width:12%;">&nbsp; 
						<a href="javascript:occupyOrVacationReport('occupied','${emu.sfID}');"><b>&nbsp;PDF</b></a>
					</display:column>
				</c:if>
				
				<c:if test="${ sessionScope.quarterTitleType eq 'vacationCmpl'}">	
			    	<display:column title="Occupied Date" style="width:10%;vertical-align:middle">
						<input type="text" class="row" size="10" name="occupiedDt" id="occupiedDt<%= i %>" value="${emu.occupiedDt}" readonly="readonly" />	
					</display:column>
					<display:column title="Vacation Date" style="width:10%;vertical-align:middle">
					    <input type="text" class="row" size="10" name="vacatedDt" id="vacatedDt<%= i %>" value="${emu.vacatedDt}" readonly="readonly" />	
					</display:column>
					<display:column title="Quarter Vacation Report" style="width:12%;">&nbsp; 
						<a href="javascript:occupyOrVacationReport('vacated','${emu.sfID}');" ><b>&nbsp;PDF</b></a>
					</display:column>
				</c:if>
			<% i++; %>
			</display:table>
		</div>
	
		<div>&nbsp;</div><div>&nbsp;</div>
		<c:if test="${sessionScope.quarterTitleType ne 'vacationCmpl'}">
			<div class="line">
	  			<div style="padding-left: 40%;">
					<div class="expbutton"><a href="javascript:saveEmuDetails1('${sessionScope.quarterTitleType}')"><span>Submit</span></a></div> 
		<%-- <c:if test="${ sessionScope.quarterTitleType eq 'alloted' }"> --%>
					<div class="expbutton"><a href="javascript:clearFields1('${sessionScope.quarterTitleType}');"><span>Clear</span></a></div>
		<%-- </c:if> --%>
	  			</div>
			</div>
    	</c:if>	
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
	
	<form:hidden path="param" />
	<form:hidden path="type" id="type" />
	<form:hidden path="requestID" id="requestID"/>
	<form:hidden path="jsonValues" id="jsonValues"/>
</form:form>
	<script type="text/javascript">
		clearFormFields();
		var type = '<c:out value='${sessionScope.quarterTitleType}'/>';
		lablesView(type);
		letterNoList = <%= (net.sf.json.JSONArray) session.getAttribute("letterNumberListJson") %>;
		emuSFIDList = <%= (net.sf.json.JSONArray) session.getAttribute("emuSFIDJson") %>;
		setPdfReports();
	</script>
</body>
</html>
<!-- End: emuDetailsHome.jsp -->