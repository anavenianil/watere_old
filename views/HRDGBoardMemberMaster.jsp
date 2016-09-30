<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:HRDGBoardMemberMaster.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
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
<script type="text/javascript" src="script/trainingscript.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<script language="javascript" src="./script/date.js"></script>
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>


<title>Board Members Creation</title>
<script type="text/javascript">
	$jq(document).ready(function(){
		clearHrdgBoardMember();
	
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
								<div class="headTitle">Board Members Creation</div>
								<%-- Content Page starts --%>
								<div>
									
									<div class="line">
									   
										
										<div class="quarter leftmar">Year<span class="mandatory">*</span></div>
										<div class="quarter">
											${trainingMaster.year}
										</div>
										<div   class="quarter bold leftmar">Board<span class="mandatory">*</span></div>
										<div class="quarter">
											${trainingMaster.board}
													
										</div>
										
										
									</div>
									
									<div class="line">
										<div   class="quarter bold leftmar">Member Type<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="memberTypeId" id="memberTypeId" cssStyle="width:145px;">
														<form:option value="">Select</form:option>
														<form:options items="${HRDGBoardMemberTypeList}" itemValue="id" itemLabel="name"/>
													</form:select>									
										</div>
										<div   class="quarter bold leftmar">SFID<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:input path="boardMemberSfid" id="sfid" maxlength="50" onkeypress="javascript:increaseTextWidth('name')"/>									
										</div>
									</div>
									
									
									<div class="line">
										<div style="margin-left:25%">
											<a href="javascript:hrdgBoardMemberSubmit('${sessionScope.trainingMstType}',jsonHRDGBoardMemberTypeList,jsonHRDGBoardMemberList);"><div class="appbutton">Submit</div></a>
										</div>
											<a href="javascript:clearHrdgBoardMember();"><div class="appbutton">Clear</div></a>
											<div class="expbutton"><a href="javascript:goBackToHrdgBoard();"><span>Go To Board</span></a></div>
											
									</div>
									
									
									
									
									<div class="line height"><hr/></div>
									<div class="line" id="result">
										<jsp:include page="HRDGBoardMemberDataList.jsp"></jsp:include>
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
		<form:hidden path="boardTypeId" id="boardTypeId"/>
		<form:hidden path="boardId" id="boardId"/>
		<form:hidden path="yearId" id="yearId"/>
		<form:hidden path="board" id="board"/>
		<form:hidden path="year" id="year"/>
		<form:hidden path="back" id="back"/>
		
		</form:form>
		<script>
						
				jsonHRDGBoardMemberTypeList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonHRDGBoardMemberTypeList") %>;
				jsonHRDGBoardMemberList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonHRDGBoardMemberList") %>;
			
			
			
		</script>
	</body>
</html>
<!-- End:HRDGBoardMemberMaster.jsp-->