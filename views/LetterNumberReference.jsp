<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LetterNumberReference.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link rel="stylesheet" type="text/css" href="styles/jquery-ui-1.10.3.custom.css" />                          

<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/jquery.js"></script>

<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<script language="javascript" src="./script/date.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script language="javascript" src="./script/script.js"></script>
<script src="script/ui/jquery.ui.autocomplete.js"></script>
<script src="script/jquery-1.9.1.js"></script>
<script src="script/ui/jquery.ui.core.js"></script>
<script src="script/ui/jquery.ui.widget.js"></script>
<script src="script/ui/jquery.ui.position.js"></script>
<script src="script/ui/jquery.ui.menu.js"></script>
<script src="script/ui/jquery.ui.core.js"></script>
<script src="script/ui/minified/jquery.ui.autocomplete.min.js"></script>

   

<title>Letter Number Reference</title>
</head>

<body onload="">
	<form:form method="post" commandName="master" id="master">
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
								<div class="headTitle">Create Letter Number</div>
								<%-- Content Page starts --%>
								<div>
									<div class="line">
										<div   class="quarter bold leftmar">Date:<span class="failure">*</span></div>
											<div class="quarter">
											<form:input path="letterDate" id="letterDate" readonly="true" onchange="javascript:getLetterList();" onclick="javascript:clearDateText(this.id)" /> 
												<img  src="./images/calendar.gif"   id="date_start_trigger4" />	
	                                           <script type="text/javascript">
			                                  Calendar.setup({inputField :"letterDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger4",singleClick : true,step : 1});
	                                           	</script>
										 </div>
										
										<div  class="quarter">
									     <form:radiobutton path="status" id="status" name="status" value="1"/><label>Dispatch1</label>
									     <form:radiobutton path="status" id="status" name="status" value="2"/><label>Dispatch2</label>
											
										</div> 
										 
										</div>
										<br></br>
										<div class="line">
										<div   class="quarter bold leftmar">Year<span class="failure">*</span></div>
										<div class="quarter">
										<form:select path="year" id="year" cssStyle="width:145px;">
									        <form:option value="Select" label="Select"></form:option>
						        			<form:options items="${master.year}" itemLabel="value" itemValue="key"></form:options>
										</form:select>												
										</div>
									    </div>
									    <br/>
										<div class="line">
										<div   class="quarter bold leftmar">LetterNumber:<span class="failure">*</span></div>
										<div class="quarter">
												<form:input path="letterNumber" id="letterNumber" maxlength="70" size="71" />										
										</div>
									    </div>
									<br></br>
									<div class="line">
									<div   class="quarter bold leftmar">Description:<span class="failure">*</span></div>
										<div class="quarter" style="height: 90px;">
											<form:textarea path="description" cssStyle="height:70px;width:448px;" id="description"  onkeypress="javascript:increaseTextWidth('description')"/>
										</div>	
									</div>
																	
									<div class="line" style="height: 35px;">
										<div   class="quarter bold leftmar">From:<span class="failure">*</span></div>
										<div class="quarter">
											<form:input path="fromPalce" id="fromPalce" maxlength="20" size="26"/>
										</div>
										<div  class="quarter bold leftmar" style="width: 4%;">To:<span class="failure">*</span></div>
										<div  class="quarter">
											<form:input path="toPalce" id="toPalce" maxlength="20" size="26"/>
										</div>
									</div>
									
									<div class="line"  style="height: 90px;">
										<div  class="quarter bold leftmar">Remarks:<span class="failure">*</span></div>
										<div class="quarter" style="height:30px;">
											<form:textarea  path="remarks" cssStyle="height:70px;width:448px;" id="remarks"  onkeypress="javascript:increaseTextWidth('remarks')"/>
										</div>
										
									</div>
									
									
									<div class="line" >
										<div style="margin-left:25%">
											<a href="javascript:manageLetterNumber();"><div class="appbutton">Submit</div></a>
										</div>
											<a href="javascript:clearLetterNumber();"><div class="appbutton">Clear</div></a>
											<a href="javascript:getLetterNumberReport()"><div class="appbutton">Report</div></a>
											
									</div>
									
									
									<div class="line" id="displayTable">
									<div id="result1">
										<jsp:include page="LetterNumberList.jsp"/>
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
		<form:hidden path="id" />
		</form:form>s
		<script type="text/javascript">
		
		$(document).ready(function(){
        var url='searchData.htm';
        var type="letter";
       $("#letterNumber").autocomplete({source:url,
         messages: {
        noResults: '',
        results: function() {}
            }
        
       });
     });
		
		</script>
		
	</body>
</html>
<!-- End:LetterNumberReference.jsp -->