<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:HindiEmpResultMaster.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<%@page import="net.sf.json.JSONArray"%><html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<script language="javascript" src="script/date.js"></script>
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/hindi.js"></script>


<title>Hindi Result Master</title>
</head>
<body onload="javascript:clearResultDetails();">
   <form:form method="post" commandName="hindi" id="hindiResult">
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
								<div class="headTitle">Hindi Result Master</div>
								<div>
									<%-- Content Page starts --%>	
									
								
							      <div class="line">
										<div class="quarter leftmar">SFID<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="sfid" id="sfid" cssClass="formSelect" onchange="javascript:onchangeResultSfid();">
												<form:option value="0">Select</form:option>
												<form:options items="${SelectedNominationList}" itemValue="key" itemLabel="value"/>													                            																																		
											</form:select>
										</div>
								    </div>
								    
							 <div id="eligibleExams">  
	                             <div class="line">
										<div class="quarter leftmar">Exam Name<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="examId" id="examId" cssClass="formSelect">
												<form:option value="0">Select</form:option>																								                            																																		
											</form:select>
										</div>
								    </div>
							</div>
								     <div class="line">
							     	<div class="quarter leftmar" style="margin-left: 8px;">Obtained Marks<span class='failure'>*</span></div>
									<div class="quarter"><form:input path="totalMarks" id="totalMarks" maxlength="100" onkeypress="return checkInt(event)" onkeyup="javascript:findMarksPercentage();"></form:input></div>		
	                              </div>
	                              
								   <div class="line">
							     	<div class="quarter leftmar" style="margin-left: 8px;">%Of Marks<span class='failure'>*</span><div id="formula"></div></div>
							     	
									<div class="quarter"><form:input path="marksPercentage" id="marksPercentage" maxlength="100" readonly="true"></form:input></div>		
	                              </div>
	                              
	                              <div class="line">
										<div class="quarter leftmar" style="width: 24%;"><b>Examination Date</b><span class='failure'>*</span> </div>
										<div class="quarter">
											<input name="examDate" id="examDate"  readonly="readonly"  style="height:16px;width:80px;font-size: 11px;font-weight: bold;" />
											<img  src="./images/calendar.gif" id="examDate_trigger1" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"examDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"examDate_trigger1",singleClick : true,step : 1});
											</script>
										</div>
										
								</div>
								
								<div class="line">
										<div class="quarter leftmar" style="width: 24%;"><b>Result Declared Date</b><span class='failure'>*</span> </div>
										<div class="quarter">
											<input name="resultDate" id="resultDate"  readonly="readonly"  style="height:16px;width:80px;font-size: 11px;font-weight: bold;" />
											<img  src="./images/calendar.gif" id="resultDate_trigger1" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"resultDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"resultDate_trigger1",singleClick : true,step : 1});
											</script>
										</div>
										
								</div>
								
								<div class="line">
										<div class="quarter leftmar" style="width: 24%;"><b>Effective Date</b><span class='failure'>*</span> </div>
										<div class="quarter">
											<input name="effectiveDate" id="effectiveDate"  readonly="readonly"  style="height:16px;width:80px;font-size: 11px;font-weight: bold;" />
											<img  src="./images/calendar.gif" id="effectiveDate_trigger1" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"effectiveDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"effectiveDate_trigger1",singleClick : true,step : 1});
											</script>
										</div>
										
								</div>
								  
								<div class="line">
												<div class="expbutton" style="margin-left: 25%;"><a onclick="javascript:saveResultDetails();"> <span>Submit</span></a></div>
												<div class="expbutton"><a onclick="javascript:clearResultDetails();"> <span>Clear</span></a></div>
								</div>	
								<div id="result" class="line"><jsp:include page="HindiEmpResultListPage.jsp" /></div>
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
   
   </form:form>
</body>
 <script>
var hindiExamsJson = <%= (net.sf.json.JSONArray)session.getAttribute("hindiExamsJson") %>
var examConfigDetailsJSON = <%= (net.sf.json.JSONArray)session.getAttribute("examConfigDetailsJSON") %>
</script>
</html>