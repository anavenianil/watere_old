<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin:<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin:boardMaster.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />

<link href="./styles/layout.css" rel="stylesheet" type="text/css" />
<link href="./styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/promotions.js"></script>

<title>Board Master</title>

</head>
<body>
	<form:form method="post" commandName="promotion" id="PromotionManagementBean">
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
								<div class="headTitle">Board Master </div>
								<%-- Content Page starts --%>
								<div class="line">
									
									<div class="line">
										<div class="quarter leftmar">Board Name<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:input path="boardType" id="boardType" placeholder="Enter The Board Name"></form:input>
										</div>
									</div>									
								<!-- 	<div class="line">*
						   				<div class="quarter leftmar">Date From<span class="mandatory">*</span></div>
						   				<div class="quarter">						   			
						    				<form:input path="dateFrom" id="dateFrom" cssClass="dateClass" readonly="true"/>
								    		<img  src="./images/calendar.gif" id="fromDateImg" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"dateFrom",ifFormat : "%d-%b",showsTime : false,button :"fromDateImg",singleClick : true,step : 1});
											</script>
										</div>
						   			</div>
						   			<div class="line">
						   				<div class="quarter leftmar">Date To<span class="mandatory">*</span></div>
						   				<div class="quarter">						   			
						    				<form:input path="dateTo" id="dateTo" cssClass="dateClass" readonly="true"/>
								    		<img  src="./images/calendar.gif" id="toDateImg" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"dateTo",ifFormat : "%d-%b",showsTime : false,button :"toDateImg",singleClick : true,step : 1});
											</script>
										</div>
						   			</div> -->
						   			<div class="line" style="margin-left: 20%;">
											<div class="expbutton"><a onclick="javascript:submitBoardMasterDetails();"> <span>Submit</span></a></div>
											<div class="expbutton"><a onclick="javascript:clearBoardMasterDetails();"> <span>Clear</span></a></div>
									</div>	
									<div class="line"><hr /></div>
									<div class="line" id="result"><jsp:include page="boardMasterList.jsp" />
																	
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
		</form:form>	
		
	</body>
</html>
<!-- End:boardMaster.jsp -->