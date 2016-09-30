<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:ccsUpload.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/payBill.js"></script>
<script type="text/javascript" src="script/ajaxUpload.js"></script>
<title>Pay Bill</title>
</head>
<body>
	<form:form method="post" commandName="payBillMaster" id="PayBillMasterBean">
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
								<div class="headTitle">CCS</div>
								<div class="line">
									<%-- Content Page starts --%>
									<div class="line" id="result"></div>
									<div class="line">
										<div class="quarter leftmar">CCS Upload<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="xslFile" type="file" id="xslFile"/></div>
									</div>
									
									<div class="line">
										<div style="margin-left:50%">
											<div class="expbutton"><a href="javascript:payCcsUploadFile();"><span>Submit</span></a></div>
											<div class="expbutton"><a href="javascript:clearCcsUploadFile();"><span>Clear</span></a></div>
										</div>
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
<!-- End:ccsUpload.jsp -->