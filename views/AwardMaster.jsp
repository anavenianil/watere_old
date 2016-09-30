<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:AwardMaster.jsp -->
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
<script type="text/javascript" src="script/trainingscript.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<script language="javascript" src="./script/date.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/script.js"></script>

<title>Create Award</title>
<script type="text/javascript">
	$jq(document).ready(function(){
	
		});
</script>
</head>

<body>
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
							<div class="lightblueBg1" id="AwardM">
								<div class="headTitle">Create Award</div>
								<%-- Content Page starts --%>
								<div>
									<div class="line" id="result">
										
									</div>
									<div class="line">
										<div   class="quarter bold leftmar">Organisation<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="parentID" id="parentID" cssClass="formSelect"  onchange="javascript:getAwardCategory(jsonAwardCategoryList);">
													<form:option value="">Select</form:option>
													<form:options items="${sessionScope.parentList}" itemValue="id" itemLabel="name"/>
											</form:select>											
										</div>
										
									</div>
									<div class="line">
										<div   class="quarter bold leftmar">Award Category<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="awardCatId" id="awardCatId" cssClass="formSelect"  >
													<form:option value="">Select</form:option>
													
											</form:select>											
										</div>
										<div   class="quarter bold leftmar2">Award Type<span class="failure">*</span></div>
										<div class="quarter" >
											<select path="awardTypeId" id="awardTypeId" cssClass="formSelect" >
												<option value="">Select</option>
												<option value="1">INDIVIDUAL AWARD</option>
												<option value="2">LAB TROPHIES</option>
												<option value="3">GROUP/TEAM/INSTITUTIONAL AWARD</option>
											</select>
										</div>
									</div>
									<div class="line">
										<div   class="quarter bold leftmar">Award Name<span class="failure">*</span></div>
										<div class="quarter">
											<div class="quarter"><form:textarea path="name" id="name" cols="20" rows="3" onkeypress="textCounter(event,$jq('#name'),$jq('#namecounter'),200);"
															 onkeyup="textCounter(event,$jq('#name'),$jq('#namecounter'),200);"></form:textarea>
													<input type="text" class="counter" name="namecounter" value="200" id="namecounter" disabled=""/>
											</div>
										</div>
										
									
										
										
									</div>
									
																	
									<div class="line">
										<div class="quarter bold leftmar">Description</div>
										<div class="quarter">
											<div class="quarter"><form:textarea path="description" id="description" cols="20" rows="3" onkeypress="textCounter(event,$jq('#description'),$jq('#descriptioncounter'),200);"
															 onkeyup="textCounter(event,$jq('#description'),$jq('#descriptioncounter'),200);"></form:textarea>
													<input type="text" class="counter" name="descriptioncounter" value="200" id="descriptioncounter" disabled=""/>
											</div>
										</div>
										<div class="quarter bold leftmar2">Remarks</div>
										<div class="quarter">
											<div class="quarter"><form:textarea path="remarks" id="remarks" cols="20" rows="3" onkeypress="textCounter(event,$jq('#remarks'),$jq('#remarkscounter'),200);"
															 onkeyup="textCounter(event,$jq('#remarks'),$jq('#remarkscounter'),200);"></form:textarea>
													<input type="text" class="counter" name="remarkscounter" value="200" id="remarkscounter" disabled=""/>
											</div>
										</div>
								   </div>
								   								
									<div class="line">
										<div  class="quarter bold leftmar">Max Limit</div>
										<div ss class="quarter">
											<form:input path="awardMaxLimit" id="awardMaxLimit" maxlength="50" onkeypress="javascript:increaseTextWidth('awardMaxLimit');return checkInt(event)"/>
										</div>
										<div  class="quarter bold leftmar2">Award Money</div>
										<div ss class="quarter">
											<form:input path="awardMoney" id="awardMoney" maxlength="50" onkeypress="javascript:increaseTextWidth('awardMoney');return isNumberExp(event);"/> %
										</div>
									</div>
									
									
									
									
									<div class="line">
										<div style="margin-left:25%">
										
											<a href="javascript:manageAwardMaster();"><div class="appbutton">Submit</div></a>
										</div>
										
											<a href="javascript:clearManageAwardMaster();"><div class="appbutton">Clear</div></a>
										
									</div>
									
									
									<div class="line height"><hr/></div>
									<div class="line" id="displayTable">
										<jsp:include page="AwardMasterDataList.jsp"></jsp:include>
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
		
		
		
		</form:form>
		<script>
			var type='<c:out value='${sessionScope.type}'/>';
			jsonAwardCategoryList = <%=(net.sf.json.JSONArray)session.getAttribute("jsonAwardCategoryList") %>;
						
		</script>
	</body>
</html>
<!-- End:AwardMaster.jsp -->