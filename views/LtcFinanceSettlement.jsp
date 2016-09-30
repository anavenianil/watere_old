<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LtcFinanceSettlement.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/tabs.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.min.js"></script>
<script type="text/javascript" src="script/jquery.ui.custom.min.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/date.js"></script>
<script type="text/javascript" src="script/ltc.js"></script>

<script type="text/javascript" src="script/ajaxUpload.js"></script>
<%-- <link href="styles/calendar.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/calendar-hd.js"></script>
<script type="text/javascript" src="script/calendar_us.js"></script>--%>
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

<title>Tour Settlement</title>
</head>
<body >
	<form:form method="post" commandName="ltcApprovalRequest" name="ltctour">
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
								<div class="headTitle">Tour Settlement</div>
								<%-- Content Page starts --%>
								
								<div class="line">
								
									<div id="page-wrap">		
										<div id="tabs">
											<ul>        		

								        		<li><a href="#fragment-1" onclick="javascript:clearCheckBoxa();clearfinance('advance');" >Advance</a></li>
								        		<li><a href="#fragment-2" onclick="javascript:clearCheckBoxs();clearfinance('settement');">Settlement</a></li>
								        		<li><a href="#fragment-3" onclick="javascript:clearCheckBoxr();clearfinance('reimbursement');">Reimbursement</a></li>
								        		<li><a href="#fragment-4" onclick="javascript:clearCheckBoxe();clearfinance('encashment');">Encashment</a></li>

								        		
								        	</ul>
								        	<div id="result"></div>
									     	<div id="fragment-1" class="ui-tabs-panel">
												<div class="line" id="advanceList">
													<div><jsp:include page="LTCAdvanceList.jsp" /></div>	
												</div>
													<div class="expbutton" style="float:right"><a href="javascript:saveCdaAmont('advance');"><span>Save</span></a></div>
													<div class="expbutton" style="float:right"><a href="javascript:getInitialReportCda('ltcCdaAdv');"><span></>Report</span></a></div>
								       
											</div>
								     		<div id="fragment-2" class="ui-tabs-panel ui-tabs-hide">
												<div class="line" id="settlementList">
													<div><jsp:include page="LTCSettlementList.jsp" /></div>	
												</div>
													<div class="expbutton" style="float:right"><a href="javascript:saveCdaAmont('settlement');"><span>Save</span></a></div>
													<div class="expbutton" style="float:right"><a href="javascript:getInitialReportCda('ltcCdaSett');"><span></>Report</span></a></div>
								        	</div>
								         	<div id="fragment-3" class="ui-tabs-panel ui-tabs-hide">
												<div class="line" id="reimbursementList">
													<div><jsp:include page="LTCRimbursementList.jsp" /></div>	
												</div>
													<div class="expbutton" style="float:right"><a href="javascript:saveCdaAmont('reimbursement');"><span>Save</span></a></div>
													<div class="expbutton" style="float:right"><a href="javascript:getInitialReportCda('ltcCdaReimb');"><span></>Report</span></a></div>
								         	</div>
								         	<div id="fragment-4" class="ui-tabs-panel ui-tabs-hide">
												<div class="line" id="encashmentList">
													<div><jsp:include page="LTCEncashmentList.jsp" /></div>	
												</div>
													<div class="expbutton" style="float:right"><a href="javascript:saveCdaAmont('encashment');"><span>Save</span></a></div>
													<div class="expbutton" style="float:right"><a href="javascript:getInitialReportCda('ltcCdaEncash');"><span></>Report</span></a></div>
													
								         	</div>   
								       	</div>
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
			<form:hidden path="message"/>
			<form:hidden path="historyID"/>
			<form:hidden path="requestId"/>
		<form:hidden path="statusMsg"/>
		<form:hidden path="back"/>
			
		</form:form>
	</body>
</html>
<!-- End:LtcFinanceSettlement.jsp -->