<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TadaFinanceSettlements.jsp -->
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
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/jquery.min.js"></script>
<script type="text/javascript" src="script/jquery.ui.custom.min.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/tada.js"></script>
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

<title>TADA Finance Settlements</title>
</head>
<body >
	<form:form method="post" commandName="tada">
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
								<div class="headTitle">TADA Finance Settlements</div>
								<%-- Content Page starts --%>
								<div class="line">
									<div id="page-wrap">		
										<div id="tabs">
											<ul>        		
								        		<li><a href="#fragment-1" ">Advance</a></li>
								        	 <li><a href="#fragment-2" onclick="javascript:clearCheckBox();">Settlement</a></li>    
								        		<li><a href="#fragment-3" onclick="javascript:clearCheckBox();">Reimbursement</a></li>
								        	</ul>
									     	<div id="fragment-1" class="ui-tabs-panel">
												<div class="line" id="advanceList">
													<div><jsp:include page="TadaTdAdvList.jsp" /></div>	
												</div>
													<div class="expbutton" style="float:right"><a href="javascript:saveCdaAmont('advance');"><span>Save</span></a></div>
											</div>
								     		 <div id="fragment-2" class="ui-tabs-panel ui-tabs-hide">
												<div class="line" id="settlementList">
													<div><jsp:include page="TadaTdSettlementList.jsp" /></div>	
												</div>
													<div class="expbutton" style="float:right"><a href="javascript:getTdFinSettReport('tadaTdSettlement');"><span>Report</span></a></div> 
													<div class="expbutton" style="float:right"><a href="javascript:saveCdaAmont('settlement');"><span>Save</span></a></div>
								        	</div>   
								         	<div id="fragment-3" class="ui-tabs-panel ui-tabs-hide">
												<div class="line" id="reimbursementList">
													<div><jsp:include page="TadaTdReimbursementList.jsp" /></div>	
												</div>
												    <div class="expbutton" style="float:right"><a href="javascript:saveCdaAmont('reimbursement');"><span>Save</span></a></div>
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
			<form:hidden path="requestId"/>
			<form:hidden path="historyID"/>
			<form:hidden path="back"/>
			<form:hidden path="statusMsg"/>
		    
			
		</form:form>
	</body>
</html>
<!-- End:TadaFinanceSettlements.jsp -->