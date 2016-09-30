<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LetterNumberFormatMaster.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
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
<script type="text/javascript" src="script/letternumberformat.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<script language="javascript" src="./script/date.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>

<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>

<title>Create Files</title>
<script type="text/javascript">
	$jq(document).ready(function(){
	if($jq('#back').val()=='letterNumberFormatMaster')
	{
	clearLetterNumberFormats(jsonLetterNumberSerisList);
	}
	else
	{
	clearLetterNumberFormat();
	}
		});
</script>
</head>

<body>
	<form:form method="post" commandName="letterNumberFormat" id="letterNumberFormat">
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
							<c:if test="${sessionScope.LetterNumberEditFlag =='flag'}">	
								<div class="headTitle">Create Files</div>
								</c:if>
								<c:if test="${sessionScope.LetterNumberEditFlag !='flag'}">	
								<div class="headTitle">Create New ION</div>
								</c:if>
								<%-- Content Page starts --%>
								<div>
																		
									<div class="line">
										<div   class="quarter bold leftmar">Department Name<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="deptNum" id="deptNum" cssClass="formSelect" onmouseover="setSelectWidth('#deptNum')" onchange="getDeptLetterNumberFormat();getDeptCodeAndSeries(jsonLetterNumberSerisList);">
												<form:option value="">Select</form:option>
												<form:options items="${sessionScope.deptList}" itemValue="id" itemLabel="deptName"/>
											</form:select>											
										</div>
										
										
									</div>
									
									<fieldset class="line" id="result1">
											<legend ><strong><font color='green'>Department Code & Series Details</font></strong></legend>
									
									<div class="line">
									<div  class="quarter bold leftmar">Department Code</div>
									<div  class="quarter" id="deptCodeVal"></div>
									
									<div  class="quarter bold leftmar">Series</div>
									<div  class="quarter" id="deptSeriesVal"></div>
									</div>	
									</fieldset>
															
									<c:if test="${sessionScope.LetterNumberEditFlag =='flag'}">	
									
									<div class="line">
										<div  class="quarter bold leftmar">Topic<span class="failure">*</span></div>
										<div ss class="quarter">
											<form:input path="topic" id="topic" maxlength="50" />
										</div>
										<div  class="quarter bold leftmar">Short Form<span class="failure">*</span></div>
										<div ss class="quarter">
											<form:input path="shortForm" id="shortForm" maxlength="50" />
										</div>
																
										
									</div>
									<div class="line">
										<div  class="quarter bold leftmar">Serial Number<span class="failure">*</span></div>
										<div ss class="quarter">
											<form:input path="serialNum" id="serialNum" maxlength="50" onkeypress="javascript:return checkInt(event)"/>
										</div>
										<div  class="quarter bold leftmar">Year<span class="failure">*</span></div>
										<div ss class="quarter">
											 <input type="radio" name="deptName" id="yearTypef" value="financial" />Financial Year
		                                     <input type="radio" name="deptName" id="yearTypec"  value="calendar" />Calendar Year	
										</div>
										
																
										
									</div>
									<div class="line">
									<div  class="quarter bold leftmar">Running Number<span class="failure">*</span></div>
										<div ss class="quarter">
											<form:input path="runningNum" id="runningNum" maxlength="50" onkeypress="javascript:return checkInt(event)"/>
										</div>
										<div   class="quarter bold leftmar">File Type<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="fileTypeId" id="fileTypeId" cssClass="formSelect" onmouseover="setSelectWidth('#fileTypeId')" >
												<form:option value="">Select</form:option>
												<form:options items="${sessionScope.fileTypeList}" itemValue="id" itemLabel="name"/>
											</form:select>											
										</div>
									</div>	
									
									
									<div class="line">
										<div style="margin-left:25%">
										
											<a href="javascript:manageLetterNumberFormat(jsonLetterNumberFormatList);"><div class="appbutton">Submit</div></a>
										</div>
										
											<a href="javascript:clearLetterNumberFormat();"><div class="appbutton">Clear</div></a>
											
									</div>
									
									</c:if>	
									<div class="line height"><hr/></div>
									<div class="line" id="displayTable">
										<jsp:include page="LetterNumberFormatMasterDataList.jsp"/>
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
		<form:hidden path="id" id="id"/>
		<form:hidden path="yearType" id="yearType"/>
		<form:hidden path="deptSeriesStartNum" id="deptSeriesStartNum"/>
		<form:hidden path="deptSeriesEndNum" id="deptSeriesEndNum"/>
		<form:hidden path="back" id="back"/>
		<form:hidden path="letterFormatId" id="letterFormatId"/>
		<form:hidden path="editFlag" id="editFlag"/>
	    <form:hidden path="ionScreenType" id="ionScreenType"/>
		
		
		
		</form:form>
		<script>
		 jsonLetterNumberFormatList = <%=(net.sf.json.JSONArray)session.getAttribute("jsonLetterNumberFormatList")%>
		 jsonLetterNumberSerisList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonLetterNumberSerisList") %>;	
		</script>
	</body>
</html>
<!-- End:LetterNumberFormatMaster.jsp -->