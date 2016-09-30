<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:HRDGBoardMaster.jsp -->
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

<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/trainingscript.js"></script>


<title>HRDG Board</title>
<script type="text/javascript">
	$jq(document).ready(function(){
		clearHrdgBoard('${trainingMaster.yearId}');
	
		});
</script>
</head>

<body>
	<form:form method="post" commandName="trainingMaster" id="trainingMaster">
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
								<div class="headTitle">HRDG Board</div>
								<%-- Content Page starts --%>
								<div>
									
									<div class="line">
									   
										<div   class="quarter bold leftmar">Board Type<span class="mandatory">*</span></div>
										<div class="quarter">
											
													<form:select path="boardTypeId" id="boardTypeId" cssStyle="width:145px;" onchange="getHRDGBoardDataList();">
														<form:option value="">Select</form:option>
														<form:options items="${HRDGBoardTypeList}" itemValue="id" itemLabel="name"/>
													</form:select>
										</div>
										<div class="quarter leftmar">Year<span class="mandatory">*</span></div>
										<div class="quarter">
											<select id="yearId" name="yearId" class="formSelect" onchange="getHRDGBoardDataList();">
												<option value="" selected="selected">Select</option>
												<option value="65">2013</option><option value="64">2012</option><option value="63">2011</option><option value="61">2010</option><option value="60">2009</option><option value="59">2008</option><option value="58">2007</option><option value="57">2006</option><option value="56">2005</option><option value="55">2004</option><option value="54">2003</option><option value="53">2002</option><option value="52">2001</option><option value="51">2000</option><option value="50">1999</option><option value="49">1998</option><option value="48">1997</option><option value="47">1996</option><option value="46">1995</option><option value="45">1994</option><option value="44">1993</option><option value="43">1992</option><option value="42">1991</option><option value="41">1990</option><option value="40">1989</option><option value="39">1988</option><option value="38">1987</option><option value="37">1986</option><option value="36">1985</option><option value="35">1984</option><option value="34">1983</option><option value="33">1982</option><option value="32">1981</option><option value="31">1980</option><option value="30">1979</option><option value="29">1978</option><option value="28">1977</option><option value="27">1976</option><option value="26">1975</option><option value="25">1974</option><option value="24">1973</option><option value="23">1972</option><option value="22">1971</option><option value="21">1970</option><option value="20">1969</option><option value="19">1968</option><option value="18">1967</option><option value="17">1966</option><option value="16">1965</option><option value="15">1964</option><option value="14">1963</option><option value="13">1962</option><option value="12">1961</option><option value="11">1960</option><option value="10">1959</option><option value="9">1958</option><option value="8">1957</option><option value="7">1956</option><option value="6">1955</option><option value="5">1954</option><option value="4">1953</option><option value="3">1952</option><option value="2">1951</option><option value="1">1950</option>
											</select>
										</div>
										
										
									</div>
									
									<div class="line">
										<div   class="quarter bold leftmar">Board Name<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:input path="name" id="name" maxlength="50" onkeypress="javascript:increaseTextWidth('name')"/>									
										</div>
									</div>
									
									
									<div class="line">
										<div style="margin-left:25%">
											<a href="javascript:hrdgBoardSubmit('${sessionScope.trainingMstType}');"><div class="appbutton">Submit</div></a>
										</div>
											<a href="javascript:clearHrdgBoard('');"><div class="appbutton">Clear</div></a>
											
										
											
									</div>
									
									
									
									
									<div class="line height"><hr/></div>
									<div class="line" id="result">
										<jsp:include page="HRDGBoardDataList.jsp"></jsp:include>
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
		<form:hidden path="type" id="type" />
		<form:hidden path="id" id="id"/>
		<form:hidden path="board" id="board"/>
		<form:hidden path="boardId" id="boardId"/>
		<form:hidden path="year" id="year"/>
		<form:hidden path="back" id="back"/>
		
		
		
		</form:form>
		<script>
			var type='<c:out value='${sessionScope.trainingMstType}'/>';			
			jsonHRDGBoardTypeList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonHRDGBoardTypeList") %>;
			jsonHRDGBoardList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonHRDGBoardList") %>;
			
			
			
		</script>
	</body>
</html>
<!-- End:HRDGBoardMaster.jsp-->