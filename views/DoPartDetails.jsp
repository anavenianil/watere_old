<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--  begin:DoPartDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<html>
<head>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<title>Daily Order Part Master</title>
</head>
<body onload="javascript:clearDOPartMasterDetails();">

<form:form method="POST" commandName="DOPart">
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
								<div class="headTitle">Daily Order Part Master</div>
								<div class="line">
									<%-- Content Page starts --%>
									<div class="line">
									<div class="quarter leftmar">Year</div>
									<div class="quarter"><form:select path="year" id="year" cssClass="formSelect"  onchange="javascript:getYearDOPart();">
									<form:option value="${DOPart.year}">Select</form:option>
									<form:options items="${yearList}" itemLabel="name" itemValue="name"/>
									</form:select></div>
									<div class="quarter leftmar">Designation Type<span class="mandatory">*</span></div>
									<div class="quarter">
										<form:select path="gazType" id="gazType" cssClass="formSelect" onchange="javascript:editDopartNo(this);">
											<option value="select">Select</option>
											<c:forEach var="tl" items="${DOPart.typeList}">
												<option value="${tl.description}#${tl.id}">${tl.name}</option>
											</c:forEach>
										</form:select>
									
									</div>
									</div> 
									<div class="line">
									<div class="quarter bold leftmar">Last D0Part No<span class="mandatory">*</span></div>
									<div class="quarter"><form:input path="preDoPartNo" id="preDoPartNo" readonly="readonly"/></div>
									<div class="quarter leftmar">Last DOPart Date<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="preDoPartDate" id="preDoPartDate" cssClass="required" readonly="true"/>
										<img  src="./images/calendar.gif"   id="date_start_trigger2" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"preDoPartDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger2",singleClick : true,step : 1});
											</script>
										</div>
									</div>
									<div class="line">
									<div class="quarter bold leftmar">D0Part No<span class="mandatory">*</span></div>
									<div class="quarter"><form:input path="doPartNo" id="doPartNo"/></div>
										<div class="quarter leftmar">DOPart Date<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="doPartDate" id="doPartDate" cssClass="required" readonly="true"/>
										<img  src="./images/calendar.gif"   id="date_start_trigger1" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"doPartDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
											</script>
										</div>
									</div>
									<div class="line">	
										<div class="quarter leftmar">Description<span class="mandatory">*</span></div>
										<div class="quarter"><form:textarea path="description" id="description" cols="25" rows="4"/></div>
										<div class="quarter leftmar"><form:checkbox path="verifyFlag" id="verifyFlag" value="Y"/><font size="4">Freeze DOPart</font></div>
									</div>	
									<div class="line">
									<div class="quarter leftmar">Distribution List<span class="mandatory">*</span></div>
									<div class="quarter"><form:textarea path="distribution" id="distribution" cols="25" rows="4"/></div>
									</div>
										<div class="line">
										<div style="margin-left:40%">
										<a href="javascript:manageDOPartMasterDetails();" class="appbutton">Submit</a>
										<a href="javascript:clearDOPartMasterDetails();" class="appbutton">Clear</a>
										</div>
									</div>
									<div class="line" id="result">
									<jsp:include page="DoPartList.jsp"/>
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
		<form:hidden path="param"/>	
		<form:hidden path="type"/>	
	</form:form>
</body>
</html>
<!--  End:DoPartDetails.jsp-->