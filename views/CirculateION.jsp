<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:CirculateION.jsp -->
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
<script type="text/javascript" src="script/ajaxUpload.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<script language="javascript" src="./script/date.js"></script>
<script type="text/javascript" src="script/letternumberformat.js"></script>
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>


<title>Circulate ION</title>
<script type="text/javascript">
	$jq(document).ready(function(){
	
	
		});
</script>
</head>

<body>
	<form:form method="post" commandName="letterNumberFormat" id="letterNumberFormat">
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
								<div class="headTitle" style="text-align: center;">Circulate</div>
								<%-- Content Page starts --%>
								<div>
								<div class="line">
									<c:if test="${sessionScope.userOrgRoleList != null}">
									<c:forEach var="userOrgRolelist" items="${sessionScope.userOrgRoleList}" varStatus="rowCounter">
									
										<div style="margin-left:25%">
										
											
											<div class="expbutton"> <a href="javascript:getIONListToCirculate(${userOrgRolelist.id});"><span>${userOrgRolelist.departmentName}-IONS </span></a></div>
										</div>
												
									
									</c:forEach>
									</c:if>	
									</div>
									
									
									
									
									<div class="line" id="result1">
										
										
									</div>
									<div class="line height"></div>
									<div class="line" id="displayTable">
										<jsp:include page="CirculateIONDataList.jsp"/>
									</div>
									<div class="line">
									<span class="failure">Note :</span>
									<c:if test="${sessionScope.userOrgRoleList != null}">
									<span class="failure"> 1.Click on to get list of IONs to be Circulated.<br/></span>
									</c:if>	
									<c:if test="${sessionScope.userOrgRoleList == null}">
									<span class="failure"> 2.IONs Button will appear for Head </span>
									</c:if>	
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
		
			<script>
			
		</script>
				
			</div>
			<div><jsp:include page="Footer.jsp" /></div>			
		</div>
		<form:hidden path="param" id="param"/>
		<form:hidden path="type" id="type" />
		<form:hidden path="id" id="id"/>
		<form:hidden path="circulateVisible" id="circulateVisible"/>
		<form:hidden path="refOrgRoleId" id="refOrgRoleId"/>
		<form:hidden path="ionScreenType" id="ionScreenType"/>
		
		
		
		</form:form>
		<script>
			
				
		</script>
	</body>
</html>
<!-- End:CirculateION.jsp-->

