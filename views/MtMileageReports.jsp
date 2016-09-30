<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:MtMileageReports.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/menu.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/mt.js"></script>
<title>MT Reports</title>
</head>
<body onload="javascript:clearMileageReport();">
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
								<div class="headTitle"></div>
								<div>
									<%-- Content Page starts --%>
								  <div class="line">
							     		<div class="quarter leftmar" >Vehicle No.<span class='failure'>*</span></div>
										<div class="quarter" >	
		                              					<form:select path="vehicleNo" id="vehicleNo">
															<form:option value="0">Select</form:option>
															<form:options items="${VehicleList}" itemValue="value" itemLabel="name"/>
														</form:select>								
		                                </div>
	                                	<div class="quarter leftmar" >Year<span class='failure'>*</span></div>
										<div class="quarter" >	
		                              					<form:select path="year" id="year">
															<form:option value="0">Select</form:option>
															<form:options items="${MtYearList}" itemValue="name" itemLabel="name"/>
														</form:select>	
																			
		                                </div>
	                              </div>
	                              <div class="line" style="margin-left: 25%;">
	                              		<c:if test="${mtBean.type=='yearlyMileageCard'}">
	                              			<div class="expbutton"><a onclick="javascript:getMileageReport('yearlyMileageCardPdf');"> <span>Get Pdf Report</span></a></div>
	                              			<div class="expbutton"><a onclick="javascript:getMileageReport('yearlyMileageCardExcel');"> <span>Get Excel Report</span></a></div>
	                              		</c:if>
	                              		<c:if test="${mtBean.type=='dailyMileageEntry'}">
	                              			<div class="expbutton"><a onclick="javascript:getMileageReport('dailyMileageEntryPdf');"> <span>Get Pdf Report</span></a></div>
	                              			<div class="expbutton"><a onclick="javascript:getMileageReport('dailyMileageEntryExcel');"> <span>Get Excel Report</span></a></div>
	                              		</c:if>
	                              		<c:if test="${mtBean.type=='distanceKPLRecord'}">
	                              			<div class="expbutton"><a onclick="javascript:getMileageReport('distanceKPLRecordPdf');"> <span>Get Pdf Report</span></a></div>
	                              			<div class="expbutton"><a onclick="javascript:getMileageReport('distanceKPLRecordExcel');"> <span>Get Excel Report</span></a></div>
	                              		</c:if>
												
											<div class="expbutton"><a onclick="javascript:clearMileageReport();"> <span>Clear</span></a></div>
								 </div>	
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
		<form:hidden path="pk" id="pk"/>
		<form:hidden path="type" id="type"/>
		</form:form>
	</body>
</html>
<!-- End:MtMileageReports.jsp -->