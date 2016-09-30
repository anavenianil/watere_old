<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:ITConfigHome.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/incomeTax.js"></script>
<title>IT Configuration</title>
</head>

<body onload="javascript:clearConfigDetails();">
	<form:form method="post" commandName="incomeTaxMaster" id="incomeTaxMaster">
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
								<div class="headTitle">Income Tax Configuration Details</div>
								<div>
									<%-- Content Page starts --%>
								<div class="line">
										<div class="quarter leftmar">SFID<span class="mandatory">*</span></div>
										<div class="quarter" ><form:input path="searchSfid" id="searchSfid"/></div>
								</div>
								<div class="line">
										<div class="quarter leftmar">Financial Year<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="selectedFYear" id="selectedFYear"  cssClass="formSelect">
												<form:option value="select">Select</form:option>
												<form:options items="${sessionScope.FinYearList}" itemValue="id"  itemLabel="fyToFrom" />
											</form:select>
										</div>
								</div>
								<div class="line">
										<div style="margin-left:50%">
										<a href="javascript:searchConfigDetails();" class="appbutton">Search</a>
										</div>
									</div>
									<div class="line" id="config"></div>
	
								
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
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		</form:form>
	</body>
</html>
<!-- End:ITConfigHome.jsp -->