<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:ExternalBoardEmployee.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/promotions.js"></script>

<title>Add External Board Member</title>
</head>

<body>
	<form:form method="post" commandName="promotion">
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
								<div class="headTitle">Add External Board Member</div>
								<%-- Content Page starts --%>
								<div class="line">									
									<div class="line">
										<div class="quarter leftmar">Name<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:input path="name" id="name"></form:input>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Designation<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:input path="desigName" id="desigName"></form:input>
										</div>
									</div>
									<div class="line" style="margin-left: 25%;">
										<div class="expbutton"><a onclick="javascript:submitExternalEmp()"> <span>Submit</span></a></div>
										<div class="expbutton"><a onclick="javascript:resetExpEmpDetails()"> <span>Clear</span></a></div>
									</div>
									<div class="line"><hr /></div>
									<div class="line" id="result">
										<jsp:include page="ExternalBoardEmployeeList.jsp"></jsp:include>
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
		<form:hidden path="type"/>
		<form:hidden path="param"/>	
		</form:form>		
	</body>
</html>
<!-- End:ExternalBoardEmployee.jsp -->