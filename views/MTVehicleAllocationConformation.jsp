<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:MTVehicleAllocationConformation.jsp  -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@page import="net.sf.json.JSONArray"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/tabs.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link href="styles/jquery-ui-1.8.16.custom.css" rel="stylesheet" type="text/css" />


 <%--<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.ui.custom.min.js"></script>
<script type="text/javascript" src="script/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript"  src="script/calendar.js"></script>
<script type="text/javascript"  src="script/calendar-en.js"></script>
<script type="text/javascript"  src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/date.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/mt.js"></script>--%>



 <%--<script type="text/javascript" src="script/jquery.js"></script>
 <script type="text/javascript" src="script/jquery.min.js"></script>
<script type="text/javascript" src="script/jquery.ui.custom.min.js"></script>
<script type="text/javascript" src="script/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript"  src="script/calendar.js"></script>
<script type="text/javascript"  src="script/calendar-en.js"></script>
<script type="text/javascript"  src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/mt.js"></script> --%>

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.min.js"></script>
<script type="text/javascript" src="script/jquery.ui.custom.min.js"></script>


<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/date.js"></script>
<script type="text/javascript"  src="script/calendar.js"></script>
<script type="text/javascript"  src="script/calendar-en.js"></script>
<script type="text/javascript"  src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/jquery-ui-timepicker-addon.js"></script>

<script type="text/javascript" src="script/mt.js"></script>
<script type="text/javascript" src="script/menu.js"></script>


<title> Confirm Vehicle Allocation</title>
<script type="text/javascript">
$jq(function() {
	var $tabs = $('#tabs').tabs();
	$jq(".ui-tabs-panel").each(function(i){
		var totalSize = $jq(".ui-tabs-panel").size() - 1;

		if (i != totalSize) {
			next = i + 2;
			$jq(this).append("<a href='#' class='next-tab mover' rel='" + next + "'>Next Page &#187;</a>");
			
		}
  
		if (i != 0) {
			prev = i;
			$jq(this).append("<a href='#' class='prev-tab mover' rel='" + prev + "'>&#171; Prev Page</a>");
			
		}
  		
	});

	$jq('.next-tab, .prev-tab').click(function() { 
		$tabs.tabs('select', $(this).attr("rel"));
		return false;
	});
      
});
</script>

</head>
<body>
	<form:form method="post" commandName="mtBean" id="mtBean">
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
								<div class="headTitle">Confirm Vehicle Allocation </div>
								<div>
									<%-- Content Page starts --%>
								 
								 <div class="line">
									<div id="page-wrap">		
										<div id="tabs">
											<ul>        		
								        		<li><a href="#fragment-1">Confirm Allocation</a></li>
								        		<li><a href="#fragment-2">DeAllocate</a></li>
								        		
								        	</ul>
									     	<div id="fragment-1" class="ui-tabs-panel">
												<div class="line">
													<div id="result1"><jsp:include page="MTReleaseAllVehicles.jsp" /></div>
												</div>
													
											</div>
								     		<div id="fragment-2" class="ui-tabs-panel ui-tabs-hide">
												<div class="line">
												<div id="result2"><jsp:include page="MTVehicleAllocationCanceledList.jsp" /></div>
														
												</div>
								        	</div>
								         	
								        </div>
									</div>
								</div>					
								 
									<%-- kums <div class="line" id="result">
								  <jsp:include page="MTVehicleAllocationResult.jsp" />
								  </div>
								  
								  <div class="line">
									  <div id="dataTable1">
									  <%int i=1;%>
										   	<display:table name="${sessionScope.ApprovedReqList}" excludedParams="*"
												export="false" class="list" requestURI="" id="req" pagesize="1000" sort="list">
												<display:column  title='SNO' ><%=i%></display:column>
												<display:column  title='REQUEST ID' >${req.requestID}</display:column>
												<display:column  title='SFID' >${req.sfid}</display:column>
												<display:column  title='EMPLOYEE NAME' >${req.requesterName}</display:column>
												<display:column  title='PURPOSE OF VISIT' >${req.purposeOfVisit}</display:column>
												<display:column  title='ONWARD JOURNEY' ><a href="javascript:onwardGrid('${req.requestID}');"><span id="hideOnwardGrid${req.requestID}" style="display:none;">hide</span><span id="showOnwardGrid${req.requestID}" >show</span></a></display:column>
												
												<display:column  title='RETURN JOURNEY' >
												<c:if test="${req.vehicleRequiredFlag eq 1}">
												<a href="javascript:returnGrid('${req.requestID}');"><span id="hideReturnGrid${req.requestID}" style="display:none;">hide</span><span id="showReturnGrid${req.requestID}" >show</span></a>
												</c:if>
												</display:column>
												  <%i++;%>
											</display:table>
										</div>
								  </div>
								<div id="OnwardGrid" style="display:none;">
									<div id="OnwardPassEngVehicleAllotGrid"></div>
									<div id="OnwardPassEngArticleGrid" style="display:none;"></div>
								</div> 
								<div id="OnwardVehicleAllotmentDetailsGrid" style="display:none;"></div>
								<div class="line" id="onwardSubmitDiv" style="display:none;">
									 <div class="expbutton" style="margin-left: 80%;"><a onclick="saveVehicleAllocationDetails('onward')"> <span>Allocate For Onward</span></a></div>
								</div>
								
								<div id="ReturnGrid" style="display:none;">
									<div id="ReturnPassEngVehicleAllotGrid"></div>
									<div id="ReturnPassEngArticleGrid" style="display:none;"></div>
								</div> 
								<div id="ReturnVehicleAllotmentDetailsGrid" style="display:none;"></div>
								<div class="line" id="returnSubmitDiv" style="display:none;">
									 <div class="expbutton" style="margin-left: 80%;"><a onclick="saveVehicleAllocationDetails('return')"> <span>Allocate For Return</span></a></div>
								</div>kums--%>
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
		<form:hidden path="param" id="param"/>
		<form:hidden path="type" id="type"/>
		<form:hidden path="pk" id="pk"/>
		</form:form>
	</body>
	
</html>

<!-- End:MTVehicleAllocationConformation.jsp  -->