<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:EMUReportHome.jsp -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />


<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/loan.js"></script>
<script type="text/javascript" src="script/workflow.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<title>Demand for conveyance advance Report</title>
</head>

<body>
	<form:form method="post" commandName="quarter" >
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
								<div class="headTitle">EMU Application Report</div>
								<%-- Content Page starts --%>
									<div class="line"><jsp:include page="Result.jsp"></jsp:include></div>
									<div class="line">
										<div id="Pagination">
											<display:table name="${sessionScope.quartersList}" excludedParams="*" export="false"	class="list" requestURI="" id="dataList" pagesize="10" sort="list">
												<display:column title="Sfid" style="width:10%;">${dataList.sfID}</display:column>
												<display:column title="Applied Date" style="width:10%;"><fmt:formatDate value="${dataList.requestedDate}" pattern="dd-MMM-yyyy" /></display:column>
												<display:column title="New Quarter" style="width:25%;"><c:if test="${(dataList.requestTypeID=='33') && (dataList.status=='61')}"><a href="javascript:emuApplication('${dataList.requestID}')">PDF</a></c:if></display:column>
												<display:column title="Change Quarter" style="width:25%;"><c:if test="${(dataList.requestTypeID=='34') && (dataList.status=='61')}"><a href="javascript:emuChangeApplication('${dataList.requestID}')">PDF</a></c:if></display:column>
											</display:table>
										</div>
										<script>
											$jq( function(){$jq("#Pagination").displayTagAjax('paging');});
										</script>
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
	</body>
</html>
<!-- End:EMUReportHome.jsp -->