<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:ConveyanceAdvanceDemand.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

<title>Demand for conveyance advance Report</title>
</head>

<body>
	<form:form method="post" commandName="loan" >
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
								<div class="headTitle">Demand for conveyance advance Report</div>
								<%-- Content Page starts --%>
									<div id="result" class="line"></div>
									<div class="line">
										<div id="Pagination">
											<display:table name="${sessionScope.conveyanceReportList}" excludedParams="*" export="false"	class="list" requestURI="" id="dataList" pagesize="20" sort="list">
												<display:column title="Report Number" style="width:40%;">${dataList.sendingReportNumber}</display:column>
												<display:column title="Report Date" style="width:20%;"><fmt:formatDate pattern="dd-MMM-yyyy" value="${dataList.sendingReportDate}"/></display:column>
												<display:column title="Admin Officer<span class='mandatory'>*</span>" style="width:20%;vertical-align:middle">&nbsp;
														<select name="adminOfficer" id="adminOfficer" style="width:150px">
														<option value="select">Select</option>
														<c:forEach var="acc" items="${loan.signingAuthorityList}">
														<c:if test="${acc.type =='SAO'}">
														<option value="${acc.sfid}" <c:if test="${acc.sfid==dataList.srAccOfficer}">selected=selected</c:if>>${acc.name}</option>
														</c:if>
														</c:forEach>
														</select>
												</display:column>
												<display:column title="Save" style="width:10%;">
												<input type="button" value="Save" onclick="saveSrAdminOfficer('${dataList.sendingReportNumber}','<fmt:formatDate pattern="dd-MMM-yyyy" value="${dataList.sendingReportDate}"/>',this)"/>
												</display:column>
												<display:column title="Conveyance Report" style="width:10%"><a href="javascript:viewConveyanceReport('${dataList.sendingReportNumber}','<fmt:formatDate pattern="dd-MMM-yy" value="${dataList.sendingReportDate}"/>','loanConveyenceReport','application')">PDF</a></display:column>
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
<!-- End:ConveyanceAdvanceDemand.jsp -->