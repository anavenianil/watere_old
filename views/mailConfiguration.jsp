<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:mailConfiguration.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link rel="stylesheet" type="text/css" href="styles/jquery-ui-1.10.3.custom.css" /> 
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script language="javascript" src="script/aa.js"></script>

<script src="script/jquery-1.9.1.js"></script>
<script src="script/ui/jquery.ui.core.js"></script>
<script src="script/ui/jquery.ui.widget.js"></script>
<script src="script/ui/jquery.ui.position.js"></script>
<script src="script/ui/jquery.ui.menu.js"></script>
<script src="script/ui/minified/jquery.ui.autocomplete.min.js"></script>
<title>Mail Configuration</title>

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
		height: 300px;
	}
	</style>
</head>
<body onload="javascript:clearMailConfiguration();">
	<form:form method="post" commandName="configuration">
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
								<div class="headTitle"> Mail Configuration</div>
								<div class="line"><jsp:include page="Result.jsp"></jsp:include></div>
								<div>
									<%-- Content Page starts --%>
								<div class="line">
								<div class="quarter bold" style="margin-left: 35%">Mail For Workflow Requests<span style="color:red">*</span></div>
								<form:select path="sendMail" id="sendMail" cssStyle="width:145px;">
						     	<form:option value="select">Select</form:option>
						     	 	<form:option value="TRUE">Enable</form:option>
						     	  	<form:option value="FALSE">Disable</form:option>
                                </form:select>
                                </div>
								<%-- <div class="line">
								<div class="quarter bold">Authority Name<span style="color:red">*</span></div>
								<div class="quarter"><form:input path="authorityEmpName" id="authorityEmpName" onkeypress="javascript:return isAlphabetExp(event);"/> </div>
								</div> --%>
								<div class="line"></div><div class="line"></div><div class="line"></div>
								<div class="line">
										<div style="margin-left:45%">
										<a href="javascript:submitMailConfiguration();" class="appbutton">Submit</a>
										<a href="javascript:clearMailConfiguration();" class="appbutton">Clear</a>
										</div>
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
		<form:hidden path="type" id="type"/>
		<form:hidden path="pk" id="pk"/>
		</form:form>
		<script type="text/javascript">
		
		$(document).ready(function(){
        var url='employeeName.htm';
        var type="empName";
       $("#authorityEmpName").autocomplete({source:url,
         messages: {
        noResults: '',
        results: function() {}
            }
        
       });
     });
		
		</script>
	</body>
</html>
<!-- End:mailConfiguration.jsp -->
