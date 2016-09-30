<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:CashReceiptVoucher.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link href="styles/calander.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.autoSuggest.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<script language="javascript" src="script/mmgscript.js"></script>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<title>Cash Receipt Voucher</title>
</head>

<body>
	<form:form method="post" id="invoice">	
		<form:hidden path="type"/>
		<form:hidden path="param"/>	
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
								
									<%-- Content Page starts --%>
																		
										<div class="headTitle">Cash Receipt Voucher</div>
										<div id="result"></div><br>
										<div class="line">
												<div class="quarter" style="margin-left: 7px;">Voucher Type</div>
												<div class="quarter">
													<form:select path="voucherType" cssStyle="width:145px">
														<form:option value="1">Cash Purchase Receipt Voucher</form:option>
													</form:select>													
												</div>
												
											</div>
										<fieldset><legend><strong>Cash Receipt Voucher</strong></legend>
										<div class="line">
												<div class="quarter">Cp Demand Number</div>
												<div class="quarter">
													<form:select path="demandNo" cssStyle="width:145px" id="IRdemandNo" onchange="javascript:PopulateDemandDetails();">
													 <form:option value="">Select</form:option>
														<c:forEach var="demand" items="${command.demandList}">
															 <form:option value="${demand.demandNo}">${demand.demandNo}</form:option>
														</c:forEach>														
													</form:select>
												</div>
												<div class="quarter">Demand Amount(<b><del>&#2352;</del></b>)</div>
												<div class="quarter" id="demandAmount"><fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="2" value="${command.totalCost}"/></div>
										</div>
										<div class="line">
												<div class="quarter">Voucher Number</div>
												<div class="quarter" id="voucherNo">${command.voucherNo}</div>
												<div class="quarter">Voucher Date</div>
												<div class="quarter" id="voucherDate"><fmt:formatDate pattern="dd-MMM-yyyy" value="${command.voucherDate}"/></div>
										</div>	
										<div class="line">
												<div class="quarter">Tax Amount(<b><del>&#2352;</del></b>)</div>
												<div class="quarter" id="taxAmount">&nbsp;<fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="2" value="${command.taxAmount}"/></div>
												<div class="quarter">Total Amount Paid(<b><del>&#2352;</del></b>)</div>
												<div class="quarter" id="totalAmount">&nbsp;<fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="2" value="${command.totalCost}"/></div>
										</div>
									  	<br>										
										<div class="line" id="IRitems"><jsp:include page="IRItemDetails.jsp"/></div>																					
										<div class="line">
											<div class="appbutton" style="float:right"><a href="javascript:cashReceiptPreview();" class="quarterbutton">Preview</a></div>
											<div class="appbutton" style="float:right"><a href="javascript:generateInvoice();" class="quarterbutton">Generate IR</a></div>												
										</div>
										</fieldset>
										
									<%-- Content Page end --%>
								</div>
								<div>&nbsp;</div>
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
		</form:form>
	</body>
</html>
<!-- End:TestPage.jsp -->