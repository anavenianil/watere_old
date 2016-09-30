<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:RequestTypeMaster.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<html>
<head>

<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="script/prototype.js"></script>
<script language="javascript" src="script/aa.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/pisscript.js"></script>

<title>Internal Workflow Mapping</title>
</head>
<body>
<form:form method="post" commandName="master">

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
							<div class="headTitle">Internal Workflow Mapping</div>
								<%-- Content Page starts --%>
								
								<div class="line">
									<div class="line" id="result">
						            </div>
									<div class="line">
										<aa:zone name="requestTypeTable">
											<display:table name="${sessionScope.masterDataList}" excludedParams="*"
												export="false" class="list" requestURI="" id="requestTypeList" pagesize="10"
												sort="list">
												<display:column title="" style="width:5%;text-align:center">
													<c:if test="${requestTypeList.internalFlag=='1'}">
														<input type="checkbox" checked="checked" name="flagValue" id="${requestTypeList.id}"/>
													</c:if>
													<c:if test="${requestTypeList.internalFlag!='1'}">
														<input type="checkbox" name="flagValue" id="${requestTypeList.id}"/>
													</c:if>
												</display:column>
												<display:column title="Name" style="width:35%">${requestTypeList.name}</display:column>
												<display:column title="Description" style="width:60%">&nbsp;${requestTypeList.description}</display:column>
											</display:table>
										</aa:zone>
									</div>
								
									<div class="line">
										<div  class="appbutton submitbutton"><a class="quarterbutton" href="javascript:saveRequestTypes()">Submit</a></div>
									</div>
								</div>
								<script>
									displayPaging("requestTypeTable","requestTypeList");									
								</script> 
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
	<form:hidden path="requestFlags"/>	
	<form:hidden path="param"/>
<form:hidden path="type"/>		
</form:form>
</body>
</html>
<!-- End:ViewMaster.jsp -->