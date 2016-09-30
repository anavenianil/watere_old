<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TaEntitleTypeConfig.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/tabs.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.min.js"></script>
<script type="text/javascript" src="script/jquery.ui.custom.min.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/leave.js"></script>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<title>Configure TA Entitlement Type</title>

</head>
<body >
	<form:form method="post" id="TadaManagementBean" commandName="tada">
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
								<div class="headTitle">Configure TA Entitlement Type Mappings</div>
								<%-- Content Page starts --%>
								<div class="line" id="result"></div>
								<div class="line">
									<div>
									<div id="Pagination">
									   <display:table name="${sessionScope.gradePayList}" excludedParams="*" export="false" class="list" requestURI="" id="gradePayList"  sort="list">
				                          <display:column title="Grade Pay" style="width:25%" sortable="true" >${gradePayList.gradePay}</display:column>
				                          <display:column title="Rail" style="width:25%"><input type="checkbox" id="rail" value="1"/></display:column>
				                          <display:column title="Air" style="width:25%"><input type="checkbox" id="air" value="2"/></display:column>
				                          <display:column title="Road" style="width:25%"><input type="checkbox" id="road" value="3"/></display:column>				

					                  </display:table>
					                </div>
					             <script>
					             $jq( function(){
					                $jq("#Pagination").displayTagAjax('paging');
					             })
					             </script>
					          </div>
									
									
																
								</div>		
								<div class="line">
								<div style="margin-left:90%"><div class="expbutton"><a onclick="javascript:submitLeaveMappingDetails()"> <span>Submit</span></a></div></div>
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
<!-- End:TaEntitleTypeConfig.jsp -->