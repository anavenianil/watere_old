<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LoanAmountHome.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/loan.js"></script>
<script type="text/javascript" src="script/ajaxUpload.js"></script>

<title>Test</title>
</head>

<body>
	<form:form method="post" commandName="test" enctype="multipart/form-data">
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
								<div class="headTitle">test Details</div>
								<%-- Content Page starts --%>
								<%-- For file upload all files should be in one Div with sending ID for ajax file Uploader --%>
								<div id="up">
										<div class="line">
											<div class="quarter leftmar">upload file</div>
											<div class="quarter">
												<form:input path="uploadFile" type="file" id="uploadFile"/>											
											</div>
										</div>																	
									<div class="line">
											<div class="quarter leftmar">upload file</div>
											<div class="quarter">
												<form:input path="UploadFile1" type="file" id="UploadFile1" />
												<form:input path="desc" type="text" id="desc" />											
											</div>
									</div>
								</div>
								<div class="line">
								<div id="result"></div>
							  		<div class="line">
										<div style="margin-left:30%">
											<div class="expbutton"><a href="javascript:testFileUpload('up','error');"><span>Submit</span></a></div>
										</div>
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
		</form:form>								
	</body>
</html>
<!-- End : LoanAmountHome.jsp -->