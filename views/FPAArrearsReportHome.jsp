<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TestPage.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<title>FPA Reports</title>
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
								<div>
									<%-- Content Page starts --%>
              <div  class=line>
					<div id="dataTable" class="line">
					<%int i=1;
					int total=0;
					 %>
				<display:table name="${sessionScope.fpaArrearsList}" excludedParams="*"
					export="false" class="list" requestURI="" id="fpa" pagesize="1000"
					sort="list" cellpadding="2" cellspacing="1" style="width:70%">
					<display:column  style="width:0.5%;vertical-align:middle" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'row\');"/>'>
						<input type="checkbox" value="${fpa.sfid}" class="row" name="row"  onclick="checkBoxCheck(this.name);"/>
					</display:column>
					<display:column title="S.No." style="width:0.1%;vertical-align:middle"><%=i+" )" %></display:column>
					<display:column title="SFID" style="width:0.3%;vertical-align:middle">${fpa.sfid}</display:column>
					<display:column title="Name" style="width:2.5%;vertical-align:middle">${fpa.empName}</display:column>
					<display:column title="Desig" style="width:3.5%;vertical-align:middle">${fpa.designation}</display:column>
					<display:column title="Month" style="width:0.8%;vertical-align:middle">${fpa.arrearsMonth}</display:column>
					<display:column title="Arrears" style="width:0.05%;vertical-align:middle">${fpa.totalDiff}</display:column>
					<display:column title="Total Arrears" style="width:0.5%;vertical-align:middle">${fpa.total}</display:column>
					<%i++;
					%>
					</display:table>
					<script>
					   $jq('.pagebanner').hide();
					</script>
			</div></div>
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