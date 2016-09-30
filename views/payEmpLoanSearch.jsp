<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<!-- begin:payEmpLoanSearch.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link href="./styles/layout.css" rel="stylesheet" type="text/css" />
<link href="./styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/payBill.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>

<script src="script/jquery-ui-1.10.3.custom.min.js"></script>
<script src="script/jquery-1.9.1.js"></script>
<script src="script/ui/jquery.ui.core.js"></script>
<script src="script/ui/jquery.ui.widget.js"></script>
<script src="script/ui/jquery.ui.position.js"></script>
<script src="script/ui/jquery.ui.menu.js"></script>
<script src="script/ui/jquery.ui.autocomplete.js"></script>
<link rel="stylesheet" type="text/css" href="styles/jquery-ui-1.10.3.custom.css" />	
<style>
	.ui-autocomplete {
		max-height: 300px;
		overflow-y: auto;
		/* prevent horizontal scrollbar */
		overflow-x: hidden;
	}
	/* IE 6 doesn't support max-height
	 * we use height instead, but this forces the menu to always be this tall
	 */
	* html .ui-autocomplete {
		height: 350px;
	}
</style>

<title>Loan Details</title>
</head>

<body>
<div><jsp:include page="Result.jsp"></jsp:include></div>
<form:form method="post" commandName="payBillMaster" id="PayBillMasterBean">
		<div>
			<div class="Innermaindiv">
				<div class="header"></div>
				<div><jsp:include page="MenuLinks.jsp"/></div>		
					
				<div class="mainDisplay">
					<div class="whitebox1">
						<div class="whitebox_t">
							<div class="whitebox_tl">
								<div class="whitebox_tr"></div>
							</div>
						</div>
						<div class="whitebox_m1">
							<div class="lightblueBg1">
							<div class="headTitle" id="headTitle">Loan Details</div>
								<%-- Content Page starts --%>
								
								<div class="line" id="payrunmonth" style="display:none;">
									<div class="half" style="padding-left:70%;"><strong>Pay Run Month:&nbsp;&nbsp;<font color="green">${payBillMaster.payRunMonth}</font></strong></div>
								</div>
                                 	<div class="line">
										<div class="quarter leftmar">Enter SFID<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="sfidSearchKey" id="sfidSearchKey"  cssClass="formSelect"  onkeypress="funPayKeyPress(event,'loan');" onblur="javascript:getEmpName(this);"/></div>
										<div class="quarter" >
											<div class="expbutton"><a href="javascript:payLoanDetailsSearch();"><span>Submit</span></a></div>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Name</div>
										<div class="quarter"><form:input path="employeeName" id="empname" /></div>
									</div>
								<div class="line" id="result"></div>
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
		</div>
		<div><jsp:include page="Footer.jsp" /></div>	
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		<form:hidden path="pk"/>
		<form:hidden path="nodeID"/>	
		<form:hidden path="referenceId"/>	
		<form:hidden path="loanRefId"/>	
		<form:hidden path="runId"/>				
		</form:form>	
		
		<script type="text/javascript">
		$(document).ready(function(){
       		var url = "empName.htm";
     		var type = "empName";
     		var name = "";
    		$("#empname").autocomplete({ source: url,
       								 	 messages: {
        									noResults: '',
        									maxItemsToShow: 10,
        									formatResult: function(data, index, max) {
        										return data[1];
         					  				},
            			  			 	 }
       								  });
     	});
     	$("#empname").on('change', function()
     	{
     		$jq('#sfidSearchKey').val($jq('#empname').val().split('-')[1]);
	        $jq('#empname').val($jq('#empname').val().split('-')[0]);
		});
	</script>
	
	</body>
</html>
<!-- End:payEmpLoanSearch.jsp -->	