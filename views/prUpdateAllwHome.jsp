<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:prUpdateAllwHome.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@page import="com.callippus.web.paybill.dto.PayBillQuartersTypeMasterDTO"%>
<%@page import="com.callippus.web.beans.dto.DesignationDTO"%>
<%@page import="com.callippus.web.paybill.dto.PayScaleDesignationDTO"%>
<%@ page import="net.sf.json.JSONSerializer,java.util.List"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/incomeTax.js"></script>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/menu.js"></script>
<script>
		<% if (session.getAttribute("empDesignationList")!=null ) { %>
			empPayDesignationListJSON =<%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<PayScaleDesignationDTO>)session.getAttribute("PayDesignationList"))%>;
		<%}%>
		<% if (session.getAttribute("designationList")!=null ) { %>
			desigListJSON =<%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<DesignationDTO>)session.getAttribute("empDesignationList"))%>;
		<%}%>
	</script>
<title>Professional Update</title>
</head>

<body onload="javascript:clearPUADetails();">

	<form:form method="post" commandName="incomeTaxMaster" id="incomeTaxMaster">
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
								<div class="headTitle">Professional Update Allowance</div>
								<div>
									<%-- Content Page starts --%>
								<div class="line">
                        				<div class="quarter bold">Grade Pay<span class="mandatory">*</span></div>
											  <div class="quarter">:&nbsp;<form:input path="gradePay" id="gradePay" maxlength="10" onkeyup="javascript:getDesignationList()" onkeypress="return checkInt(event);"/>				
										     </div>
								</div>
								<div class="line">
								<div class="quarter bold">Designation Name<span class="failure">*</span></div>
								<div class="quarter">:&nbsp;<form:select path="designationId"
									id="designationId" cssClass="formSelect">
									<form:option value="select">Select</form:option>
								</form:select></div>
								</div>
								<div class="line">
                        				<div class="quarter bold">Amount<span class="mandatory">*</span></div>
											  <div class="quarter">:&nbsp;<form:input path="amount" id="amount" maxlength="10" onkeypress="return checkInt(event);"/>				
										     </div>
								</div>
								<div class="line">
											<div style="margin-left:50%;"><div class="appbutton" onclick="javascript:managePrUpdateAllwMaster();">Submit</div></div>
											<div style="margin-left:50%;"><div class="appbutton" onclick="javascript:clearPUADetails();">Clear</div></div>
								</div>
								<div id="result" class="line"><jsp:include page="PrUpdateAllwList.jsp"/></div>
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
		<form:hidden path="pk"/>
		</form:form>
	</body>
</html>
<!-- End:prUpdateAllwHome.jsp -->
