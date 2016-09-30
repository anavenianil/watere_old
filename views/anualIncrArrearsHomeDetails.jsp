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
<script type="text/javascript" src="script/arrears.js"></script>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/report.js"></script>
<title>Annual Incr Arrears</title>
</head>

<body>
	<form:form method="post" commandName="arrears">
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
								<div class="headTitle">Annual Increment Arrears Details</div>
								<div>
									<%-- Content Page starts --%>
							<div class="line">
							<div id="table1">
							  <div class="dataTable" style="width:50%">
								<display:table name="${arrears.arrearsStatusList}" excludedParams="*"
									export="false" class="list" requestURI="" id="arrears" pagesize="1000"
									sort="list" cellpadding="2" cellspacing="1">
									<display:column title="Effective Date" style="vertical-align:middle">${arrears.adminAccDate}</display:column>
									<display:column title="Pay Effective Date" style="vertical-align:middle">${arrears.financeAccDate}</display:column>
									<display:column title="" style="width:25%;vertical-align:middle">
                                    <a href="javascript:previewAnnualIncrArrears('${arrears.adminAccDate}','${arrears.financeAccDate}');" class="appbutton">Preview</a>
                                    </display:column>
								</display:table>
							  </div>
							 </div>
							 <div class="details"></div>
							</div>
							<div id="preview" class="line"></div>
							
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
<!-- End:TestPage.jsp -->