<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:PinNumber.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<title>Pin Number Master</title>
</head>

<body id="displayTable" >
	<form:form method="post" commandName="master" id="master">
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
							    <div class="headTitle">Create Pin Number </div>
								<%-- Content Page starts --%>
								<div>				
								<div class="line">
								<div class="quarter bold leftmar">SFID<span style="color:red">*</span></div>
								<form:select path="name" id="name" cssStyle="width:145px;">
						     	<form:option value="select">Select</form:option>
						     	<%-- <form:options items="${master.sfidPinNoList}" /> --%>
						     	<c:forEach var="sfidPinNoList" items="${master.sfidPinNoList}">
					 <form:option value="${sfidPinNoList.name}">${sfidPinNoList.name}-${sfidPinNoList.description}</form:option>
				</c:forEach> 
                                </form:select>
								</div>
									<div class="line">
										<div   class="quarter bold leftmar" >Pin Number<span class="failure">*</span></div>
										<div class="quarter">		 
			                                <form:input path="pin" id="pin" maxlength="15" onkeypress="javascript:return checkSpecialChar(event);"/> 
		                                </div>
									</div>
									<div class="line">
										<div style="margin-left:25%">
											<a href="javascript:pinSubmit();"><div class="appbutton">Submit</div></a>
										</div>
											<a href="javascript:clearPin();"><div class="appbutton">Clear</div></a>										
									</div>
									<div class="line height"><hr/></div>
									<div class="line" id="displayTable">
										<jsp:include page="PinList.jsp"></jsp:include>
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
		<form:hidden path="type"/>
		
		</form:form>
		<script>
			clearPin();
		</script>
	</body>
</html>
<!-- End:PinNumber.jsp  -->