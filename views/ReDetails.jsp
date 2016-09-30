<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:ReDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.js"></script>
<script type="text/javascript" src="script/jquery.autoSuggest.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/menu.js"></script>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<script language="javascript" src="script/report.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<title>Requirement Details</title>
</head>


<body>
	<form:form method="post"  id="reDetails" commandName="reDetailsBean">
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
									
						    <div class="headTitle">Requirement Details</div><br></br>	
						    <div class="line">
						    
						    <div class="quarter bold" style="margin-left:8px;" >Date<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="txnDate"  id="txnDate"  cssClass="dateClass" readonly="true" onclick="javascript:clearDateText('txnDate');" onchange="javascript:getReValueDesig();"/>
												<img  src="./images/calendar.gif"   id="date_start_trigger4" />	
												<script type="text/javascript">
												Calendar.setup({inputField :"txnDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger4",singleClick : true,step : 1});
												</script>
										</div>
						    <div class="quarter bold" style="margin-left:8px;">Category<span class="mandatory">*</span></div>
										<div class="quarter">
										<form:select path="categoryID" id="categoryID" onchange="javascript:getDesignations()">
										<form:option value="">select</form:option>
										<form:options items="${sessionScope.CategoryList}" itemValue="id" itemLabel="name"/>
										</form:select>
							</div>
							
							</div>
							<div id="Designations"><jsp:include page="ReList.jsp"/></div>
							<div class="line">
								<div class="appbutton" style="float: right;"><a	href="javascript:clearReDetails();" class="quarterbutton">Clear</a></div>
								<div class="appbutton" style="float: right;"><a	href="javascript:saveReDetails();" class="quarterbutton">Submit</a></div>
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
		<form:hidden path="jsonValue"/>	
		</form:form>
	</body>
	<script>
	var designationList=<%=(net.sf.json.JSONArray) request.getAttribute("designationList")%>;
	var designation=<%=(net.sf.json.JSONArray) request.getAttribute("DESIGNATION")%>;
	</script>
</html>
<!-- End:ReDetails.jsp -->		
