
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin:ReimbursementDetails.jsp -->
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/ajaxUpload.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/cghs.js"></script>
<script type="text/javascript" src="script/script.js"></script>

<title>CGHS Claims</title>
</head>

<body>
	<form:form method="post" commandName="cghs" id="cghs">
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
								<div class="headTitle">CGHS Claims</div>
								<div>
									<%-- Content Page starts --%>
										<div class="line">
										    <jsp:include page="CghsReimbursementList.jsp" />
										</div>
										<!-- <div class="line" >
											<div class="expbutton"><a href="javascript:reimbursementRequest('','noncghsReimbursement')"> <span>Non-CGHS Hospital Reimbursement</span></a></div>
											<div class="expbutton"><a href="javascript:emergencyRequest()"> <span>Emergency</span></a></div>
										</div> -->
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
		<form:hidden path="requestId"/>
		<form:hidden path="message"/>
		<form:hidden path="historyID"/>
		<form:hidden path="statusMsg"/>
		<form:hidden path="back"/>
		</form:form>
	</body>
</html>

<!-- End:ReimbursementDetails.jsp -->