<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TestPage.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
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
<script type="text/javascript" src="script/report.js"></script>
<title>IT Section Mapping</title>
</head>

<body>
	<form:form method="post" commandName="incomeTaxMaster" id="incomeTaxMasterBean">
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
								<div class="headTitle">Income Tax Section Mapping Master</div>
								<div>
									<%-- Content Page starts --%>
									<div class="line">
									<div class="quarter leftmar">Section Name<span class="mandatory">*</span></div>
									<div class="quarter">
									<form:select path="sectionId"  cssClass="formSelect" style="width:65%">
									<form:option value="select">Select</form:option>
									<form:options items="${sectionList}" itemValue="id" itemLabel="secName"></form:options>
									</form:select></div>
									</div>
									 <div class="line">
									<div class="quarter leftmar">Applicable For<span class="mandatory">*</span></div>
									<div class="half">
											<form:radiobutton path="gender" label="M" value="1" id="gender1"/> 
	         								<form:radiobutton path="gender" label="F" value="0" id="gender2"/>
	         							</div>
	         						</div>
	         						<div class="line">
									<div class="quarter leftmar">Disability<span class="mandatory">*</span></div>
									<div class="half">
											<form:radiobutton path="disability" label="Handicaped" value="1" id="disability1"/> 
	         								<form:radiobutton path="disability" label="Non-Handicaped" value="0" id="disability2"/>
	         							    </div>
	         						</div>
	         						<div class="line">
									<div class="quarter leftmar">Sr.Citizen<span class="mandatory">*</span></div>
									<div class="half">
											<form:radiobutton path="srCitizen" label="Yes" value="1" id="srCitizen1"/> 
	         								<form:radiobutton path="srCitizen" label="No" value="0" id="srCitizen2"/>
	         							    </div>
	         						</div>
	         						<div class="line">
										<div class="quarter leftmar">Limit<span class="mandatory">*</span></div>
										<div class="quarter" ><form:input path="limit" id="limit" onkeypress="return checkInt(event);"/>
												</div>
								    </div>
								    <div class="line">
										<div style="margin-left:50%">
										<a href="javascript:submitITSectionMappingDetails();" class="appbutton">Submit</a>
										<a href="javascript:clearSectionDetails();" class="appbutton">Clear</a>
										</div>
									</div>
								    <div class="line" id="sectionMappingList"><jsp:include page="itSectionMappingList.jsp"></jsp:include> </div>
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
<!-- End:TestPage.jsp -->