<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin: employeephotoupload.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/calendar-hd.js"></script>
<script type="text/javascript" src="script/leave.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script type="text/javascript" src="script/ajaxUpload.js"></script>

<title>Leave Transaction Search</title>
</head>

<body>
	<form:form method="post" commandName="employee" modelAttribute="employee">
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
								<div class="headTitle" >Photo Uploading</div>
								<%-- Content Page starts --%>
								<div class="line">
									<div class="line">
									<div class="line">
											<div class="quarter bold">SFID<span class="mandatory">*</span></div>
											<div class="half">
												<form:input path="sfid" id="sfid" maxlength="10" onkeypress="javascript:return isAlphaNumaricExp(event);" />
											</div>
										</div>
										<div class="line">
											<div class="quarter bold">Passport Size(Photo)<span class="mandatory">*</span></div>
											<div class="half">
												<form:input path="passportImageFile" type="file" name="files1" id="files1" />												
											</div>
										</div>										
										<div class="line">
											<div class="quarter bold">Stamp Size(Photo)</div>
											<div class="half">
												<form:input path="stampImageFile" type="file" name="files2" id="files2" />												
											</div>
										</div>		
										<div class="line">
											<div class="quarter bold">Small Size(Photo)</div>
											<div class="half">
												<form:input path="smallImageFile" type="file" name="files3" id="files3" />												
											</div>
										</div>
                                 		<div class="line">
											<div style="padding-left:30%;">
												<div class="expbutton"><a href="javascript:photoUpload();"><span>Upload</span></a></div>
												<div class="expbutton"><a href="javascript:clearPhotoUpload();"><span>Clear</span></a></div>
											</div>
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
		<form:hidden path="type"/>
		<form:hidden path="param"/>	
		</form:form>		
	</body>
</html>
<!-- End: employeephotoupload.jsp -->