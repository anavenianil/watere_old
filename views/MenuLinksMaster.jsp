<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:MenuLinksMaster.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<link href="styles/menustyles.css" rel="stylesheet" type="text/css"/>
<link href="styles/layout.css" rel="stylesheet" type="text/css"/>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<title>Menu Links Master</title>
</head>

<body>
	<form:form method="post" commandName="menu">
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
								<div class="headTitle">Menu Links Master</div>
								<%-- Content Page starts --%>
								<div>
									<div class="line">
										<div class="quarter bold">Menu Key<span class="mandatory">*</span></div>
										<div class="half">
											<form:input id="keyName" path="keyName" maxlength="25" onkeypress="javascript:increaseTextWidth('keyName');return checkChar(event);" />
										</div>
									</div>
									<div class="line">
										<div class="quarter bold">Link Name<span class="mandatory">*</span></div>
										<div class="half">
											<form:input path="linkName" id="linkName" maxlength="100" onkeypress="javascript:increaseTextWidth('linkName');return checkChar(event);" />
										</div>
									</div>
							
									<div class="line">
										<div class="quarter bold">Class Name<span class="mandatory">*</span></div>
										<div class="half"><form:input path="className" id="className" maxlength="200"
											onkeypress="javascript:increaseTextWidth('className');return checkChar(event);" onmouseover="setSelectWidth('#className');" /></div>
									</div>
							
									<div class="line">
										<div class="quarter bold">Parent</div>
										<div class="quarter bold">
											<form:select path="parentId" id="parentId" cssClass="formSelect" onmouseover="setSelectWidth('#parentId')">
												<form:option value="0">Select</form:option>
												<form:options items="${sessionScope.descriptionList}" itemValue="id" itemLabel="description" />
											</form:select>
										</div>
									</div>
							
									<div class="line">
										<div style="margin-left: 24%;">
											<div class="expbutton"><a href="javascript:saveMenuLink();"><span>Submit</span></a></div>
											<div class="expbutton"><a href="javascript:resetMenuLink();"><span>Clear</span></a></div>
										</div>
									</div>
									<div class="height">
										<hr />
									</div>
									<div class="line" id="menuLinksMasterList"><jsp:include page="MenuLinksMasterList.jsp" /></div>
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
				
			</div></div>
			<div><jsp:include page="Footer.jsp" /></div>			
		</div>
		<form:hidden path="type"/>
		<form:hidden path="param"/>			
		</form:form>		
	</body>
</html>
<!-- End:MenuLinksMaster.jsp -->