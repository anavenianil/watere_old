<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TutionFeeAcademicYearMaster.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/tutionFee.js"></script>
<title>Tuition Fee Standard Master</title>
</head>

<body>
	<form:form method="post" commandName="tutionFee">
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
								<div class="headTitle">Tuition Fee Standard  Master </div>
								<div>
									<%-- Content Page starts --%>
								<div class="line">
									<div class="quarter bold" style="margin-left: 8px;">Standard<span style="color:red">*</span></div>
									<div class="quarter"><form:input path="className" id="className" onkeypress="javascript:return checkChar(event)"/></div>
								</div>
								<div class="line">
										<div class="quarter bold" style="margin-left:8px;" id="Order No">Sequence order<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="claimOrderNo" id="claimOrderNo" maxlength="50" onkeypress="javascript:return checkInt(event)"/></div>
								</div>
							    <div class="line">
							    <div style="margin-left:50%">
							    <a href="javascript:submitTuitionFeeAcademicYearMaster();" class="appbutton">Add</a>
							    <a href="javascript:clearTuitionFeeacademicYearMaster();" class="appbutton">Clear</a>
							    </div>
							    </div>
							    <div class="line" id="result"><jsp:include page="TutionFeeAcademicYearMasterList.jsp"/>
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
		<form:hidden path="pk"/>
		</form:form>
	</body>
</html>
<!-- End:TutionFeeAcademicYearMaster.jsp -->