<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin : HierarchyLevel.jsp -->
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
<script type="text/javascript" src="script/prototype.js"></script>
<script type="text/javascript" src="script/aa.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>

</head><body onload="javascript:clearLevel()">
	<form:form method="post" commandName="hierarchy">
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
								<c:if test="${hierarchy.type eq 'role'}">
									<div class="headTitle">Create Organisation Role Levels</div>
									<title>Create Organisation Role Levels</title>
								</c:if>
								<c:if test="${hierarchy.type eq 'department'}">
									<div class="headTitle">Create Organisation Levels</div>
									<title>Create Organisation Levels</title>
								</c:if>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</div>
								<%-- Content Page starts --%>
								<div class="line">
									<div class="line">
										<div class="quarter bold leftmar">New Level<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:input path="newLevel" id="newLevel" cssClass="" maxlength="50" onkeypress="javascript:increaseTextWidth('newLevel')"/>
										</div>
									</div>
									<div class="line">
										<div class="quarter bold leftmar">Minimum Level of Reporting<span class="mandatory">*</span></div>
										<div class="quarter">
											<div class="formSelect">
												<form:select path="parent" id="parent" cssClass="formSelect" onmouseover="setSelectWidth('#parent')">
													<form:option value="select">Select</form:option>
													<c:if test="${hierarchy.type eq 'role'}">
														<form:options items="${parentList}" itemValue="roleID" itemLabel="roleName"/>
													</c:if>
													<c:if test="${hierarchy.type eq 'department'}">
														<form:options items="${parentList}" itemValue="id" itemLabel="hierarchyName"/>
													</c:if>
												</form:select>	
											</div>										
										</div>
									</div>
									<div class="line">
										<div style="margin-left:25%">
											<a href="javascript:levelSubmit('${hierarchy.type}');"><div class="appbutton">Submit</div></a>
											<a href="javascript:clearLevel();"><div class="appbutton">Clear</div></a>
										</div>									
									</div>
									 <div class="line height"><hr/></div>
									<div class="line" id="displayTable">
										<jsp:include page="HierarchyLevelList.jsp"></jsp:include>
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
<!-- End : HierarchyLevel.jsp -->