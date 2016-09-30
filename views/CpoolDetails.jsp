<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:ReportDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.js"></script>
<script type="text/javascript" src="script/jquery.autoSuggest.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/menu.js"></script>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<script language="javascript" src="script/report.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<title>Cpool Details</title>
</head>


<body>
	<form:form method="post"  id="reportDetails" commandName="reportDetailsBean">
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
								<div>
									<%-- Content Page starts --%>
									
						    <div class="headTitle">Cpool Details</div><br></br>	
						    <div class="line">
										<div class="quarter bold" style="margin-left:8px;" >Date<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="txnDate"  id="txnDate"  cssClass="dateClass" readonly="true" onclick="javascript:clearDateText('txnDate');"/>
												<img  src="./images/calendar.gif"   id="date_start_trigger4" />	
												<script type="text/javascript">
												Calendar.setup({inputField :"txnDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger4",singleClick : true,step : 1});
												</script></div>
							</div>
							<div class="line" id="result"></div>	
							<div class="line">								
							   <display:table name="${designationList}" excludedParams="*"
		                           export="false" class="list" requestURI="" id="dataList" pagesize="100"	sort="list">
		                       <display:column title="DESIGNATION" style="width:50%;">${dataList.name}</display:column>
		                       <display:column title="CPOOL_IN"  style="width:25%;">&nbsp;<input type="text" class="row" name="cpool_in"  id="${dataList.id}" /></display:column>
		                       <display:column title="CPOOL_OUT" style="width:25%;">&nbsp;<input type="text" class="row" name="cpool_out"  id="${dataList.id}a" /></display:column>
		                       </display:table>
							</div>							
					       <div class="line">						       
						        <div class="appbutton" style="float:right;" ><a href="javascript:clearCpoolDetails();" class="quarterbutton" >Clear</a></div>
						         <div class="appbutton" style="float:right;" ><a href="javascript:saveCpoolDetails();" class="quarterbutton" >Submit</a></div>
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
		<form:hidden path="jsonValue"/>	
		</form:form>
	</body>
	<script>
	var designationList=<%=(net.sf.json.JSONArray) request.getAttribute("designationList")%>;
	
	</script>
</html>
<!-- End:ReportDetails.jsp -->		