<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LtcFamilyDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>LTC Family Details</title>
<link href="./styles/layout.css" rel="stylesheet" type="text/css" />
<link href="./styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/ltc.js"></script>
</head>

<body>
	<form:form method="post" commandName="ltcMaster" name="ltcMaster" id="ltcMaster" action="" >
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
								<div class="headTitle">LTC Family Details</div>
								<%-- Content Page starts --%>
								<div class="line">
									<div class="line" id="result">									
									</div>
									<div class="line">
										<table cellpadding="2" cellspacing="2" border="1" id="familyTable" width="100%">
											<tr style="background-color:#014495;color:#fff">
												<th width="10%"></th>
												<th width="35%">Name</th>
												<th width="15%">Age</th>
												<th width="15%">Sex</th>
												<th width="25%">Relation</th>
											</tr>
											<c:forEach var="dataList" items="${ltcMaster.familyMembersList}">
												<tr>
													<td style="text-align:center">
														<c:if test="${dataList.status=='0'}">
															<input type="checkbox" name="familyID" id="${dataList.id}"/>
														</c:if>
														<c:if test="${dataList.status=='1'}">
															<input type="checkbox" name="familyID" id="${dataList.id}" checked="checked"/>
														</c:if>
													</td>
													<td><c:out value="${dataList.name}"></c:out></td>
													<td style="text-align:right"><c:out value="${dataList.age}"></c:out></td>
													<td><c:out value="${dataList.sex}"></c:out></td>
													<td><c:out value="${dataList.relation}"></c:out></td>
												</tr>
											</c:forEach>
										</table>	
									</div>	
									<div class="line">
										<a href="javascript:submitLtcFamily();"  class="appbutton" style="text-decoration: none;float:right">Submit</a>
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
	</body>
</html>
<!-- End:LtcFamilyDetails.jsp -->