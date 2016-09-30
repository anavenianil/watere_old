<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:CourseAttendedDetails.jsp -->
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

<title>Letter Number Series</title>
<script type="text/javascript">
	$jq(document).ready(function(){
	clearLetterNumber();
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
								<div class="headTitle">Letter Number Series</div>
								<%-- Content Page starts --%>
								<div>
																		
									<div class="line">
										<div   class="quarter bold leftmar">Department Name<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="deptNum" id="deptNum" cssClass="formSelect" onmouseover="setSelectWidth('#deptNum')">
												<form:option value="">Select</form:option>
												<form:options items="${sessionScope.deptList}" itemValue="id" itemLabel="deptName"/>
											</form:select>											
										</div>
										
										
									</div>
									<div class="line">
										<div  class="quarter bold leftmar">Department Short Name</div>
										<div ss class="quarter">
											<form:input path="deptShortName" id="deptShortName" maxlength="50" />
										</div>
										<div  class="quarter bold leftmar">Department Code<span class="failure">*</span></div>
										<div ss class="quarter">
											<form:input path="deptCode" id="deptCode" maxlength="50" onkeypress="javascript:return checkInt(event)"/>
										</div>
																
										
									</div>
									<div class="line">
										<div  class="quarter bold leftmar">Series Start Number<span class="failure">*</span></div>
										<div ss class="quarter">
											<form:input path="deptSeriesStartNum" id="deptSeriesStartNum" maxlength="50" onkeypress="javascript:return checkInt(event)"/>
										</div>
										<div  class="quarter bold leftmar">Series Last Number<span class="failure">*</span></div>
										<div ss class="quarter">
											<form:input path="deptSeriesEndNum" id="deptSeriesEndNum" maxlength="50" onkeypress="javascript:return checkInt(event)"/>
										</div>
																
										
									</div>
									
									<div class="line">
										<div style="margin-left:25%">
										
											<a href="javascript:manageLetterNumber(jsonLetterNumberSeriesList);"><div class="appbutton">Submit</div></a>
										</div>
										
											<a href="javascript:clearLetterNumber();"><div class="appbutton">Clear</div></a>
											
									</div>
									
									
									<div class="line height"><hr/></div>
									<div class="line" id="displayTable">
										<jsp:include page="LetterNumberMasterDataList.jsp"/>
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
		
		
		
		</form:form>
		<script>
		 jsonLetterNumberSeriesList = <%=(net.sf.json.JSONArray)session.getAttribute("jsonLetterNumberSeriesList")%>
			
		</script>
	</body>
</html>
<!-- End:CourseAttendedDetails.jsp -->