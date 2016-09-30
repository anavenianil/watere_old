<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:MMGCPVoucher.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.autoSuggest.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/mmgscript.js"></script>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/autoSuggest.css" type="text/css" rel="stylesheet"/>
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/menu.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript">
	$jq(document).ready(function(){
		document.title='Voucher';
		$jq('#headTitle').html('Voucher');
		$jq("#mmgVoucher").validate({
    	 rules: { 
	         inventoryNo: "required"
	   },
	  messages: {
		inventoryNo: "Please Select Inventory Number."
		},
    });
   	});
   	
</script>
</head>

<body>
	<form:form method="post" commandName="mmgVoucher" id="mmgVoucher">
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
								<div class="headTitle" id="headTitle"></div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</img></div>
								<%-- Content Page starts --%>
								<br/>
								<div id="result"></div>
								<div>
									
									<div class="line">
										<div class="line">
											<div class="quarter" style="margin-left:7px;">Voucher Type</div>
											<div class="quarter">
												<form:select path="voucherTypeId" id="voucherTypeId" onchange="javascript:getVoucherNames();">
													<form:option value="Select">Select</form:option>
													<c:forEach var="listvar" items="${voucherTypeList}">
													<c:if test="${listvar.id ne '3'}">
														<form:option value="${listvar.id}">${listvar.name}</form:option>
													</c:if>
													</c:forEach>
													
												</form:select>
											</div>
										</div>
										
										<div class="line">
											<div id="voucherNames"></div>
											<div id="itemDetails">
												<jsp:include page="ItemDetails.jsp" />
											</div>
										</div>
										<div class="line">
											<div id="demandList" class="line"><jsp:include page="DemandItemsList.jsp" /></div>
										</div>
																	
											<div class="line">
												<div class="quarter">Purpose</div>
												<div class="quarter">
													<form:input path="purpose" id="purpose"/>
												</div>
												<div class="quarter">Posting Date</div>
												<div class="quarter">
													<form:input path="postingDate" id="postingDate" readonly="true"></form:input>
													<img  src="./images/calendar.gif"   id="date_start_trigger3" />	
														<script type="text/javascript">
															Calendar.setup({inputField :"postingDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger3",singleClick : true,step : 1});
														</script>
												</div>
											</div>
											<div id="expVoucherField" style="display:none">
											<div class="line">
												
												<div class="quarter">Period From</div>
												<div class="quarter">
													<form:input path="periodFrom" id="periodFrom" readonly="true"></form:input>
													<img  src="./images/calendar.gif"   id="date_start_trigger1" />	
														<script type="text/javascript">
															Calendar.setup({inputField :"periodFrom",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
														</script>
												</div>
												<div class="quarter">Period To</div>
												<div class="quarter">
													<form:input path="periodTo" id="periodTo" readonly="true"></form:input>
													<img  src="./images/calendar.gif"   id="date_start_trigger2" />	
														<script type="text/javascript">
															Calendar.setup({inputField :"periodTo",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger2",singleClick : true,step : 1});
														</script>
												</div>
												
											</div>
											</div>
											<div class="line">
												<div class="appbutton" style="float:right"><a href="javascript:previewVoucher();" class="quarterbutton">Preview</a></div>
												<div class="appbutton" style="float:right"><a href="javascript:clearMMGVoucher();" class="quarterbutton">Clear</a></div>
												<div class="appbutton" style="float:right"><a href="javascript:genarateVoucher();" class="quarterbutton">Submit</a></div>
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
	<form:hidden path="param" id="param"/>
	<form:hidden path="type" id="type"/>
	
	</form:form>
</body>
</html>
<!-- End:MMGCPVoucher-.jsp -->