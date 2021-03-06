<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:AddressDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="./styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script language="javascript" src="script/prototype.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script language="javascript" src="script/aa.js"></script>
<script type="text/javascript" src="script/script.js"></script>

<title>Address Details</title>
</head>

<body>
	<form:form method="post" commandName="address">
	<form:hidden path="param"/>
	<form:hidden path="type"/>
	<form:hidden path="id" id="id"/>
	<script>document.title="Address Details"</script>
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
								<div style="padding: 10px 0 0 0;"></div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</div>
								<%-- Content Page starts --%>
								<c:if test="${message ne 'ViewAddress'}">
									<div class="headTitle">Manage Address Details</div>
								</c:if>
								<c:if test="${message eq 'ViewAddress'}">
									<div class="headTitle">Address Details</div>
								</c:if>
								<c:if test="${message ne 'ViewAddress'}">
									<c:if test="${sessionScope.addressType eq 'admin'}">
										<div class="line">
												<div class="quarter bold leftmar">SFID</div>
												<div class="quarter"><form:input path="sfid" id="sfid"/></div>
												<div class="quarter"><a href="javascript:getAddress();"><div class="appbutton">Submit</div></a></div>
										</div>
									</c:if>
								</c:if>
								<div class="line">
									<div id="result"></div>
								</div>
								<c:if test="${message eq 'ViewAddress'}">
									<script>
										getAddress();
									</script>
								</c:if>
								
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
		
		</form:form>
	</body>
</html>
<!-- End:AddressDetails.jsp -->