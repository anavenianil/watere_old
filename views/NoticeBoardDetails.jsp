<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:NoticeBoardDetails.jsp -->
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
<script type="text/javascript" src="script/ajaxUpload.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<script language="javascript" src="./script/date.js"></script>
<script type="text/javascript" src="script/letternumberformat.js"></script>
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>


<title>Notice Board</title>
<script type="text/javascript">
	$jq(document).ready(function(){
	//clearIonCirculatedDetails();
	javascript:multiSelectBox();
	
		});
</script>
</head>

<body>
	<form:form method="post" commandName="ion" id="ion">
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
								<div class="headTitle">Received ION Details</div>
								<%-- Content Page starts --%>
								<div>
								    
									<c:if test="${sessionScope.userOrgRoleList != null}">
									<%int i=0; %>
									<c:forEach var="userOrgRolelist" items="${sessionScope.userOrgRoleList}" varStatus="rowCounter">
									<div class="line" id="role">
										<div style="margin-left:25%">
											<div class="expbutton" id="roleButton<%=i %>"> <a href="javascript:setLevel1SfidRole(${userOrgRolelist.id});"><span>${userOrgRolelist.departmentName} View ION </span></a></div>
											<%i++; %>
										</div>
												
									</div>
									</c:forEach>
									
									</c:if>
									<div class="line">
										<div   class="quarter bold leftmar">Remarks<span class="failure">*</span></div>
										<div class="quarter">
											<form:textarea path="remarks" id="forwardedRemarks" cols="20" rows="3" onkeypress="textCounter(event,$jq('#forwardedRemarks'),$jq('#forwardedRemarkscounter'),200);"
															 onkeyup="textCounter(event,$jq('#forwardedRemarks'),$jq('#forwardedRemarkscounter'),200);"></form:textarea>
													<input type="text" class="counter" name="forwardedRemarkscounter" value="200" id="forwardedRemarkscounter" disabled=""/>
																						
										</div>
										
									</div>
									<c:if test="${sessionScope.userOrgRoleList != null}">
									<div id="dispatchList" style="display: none;">
									<div class="line" >
										<div  class="quarter bold leftmar">Sfids to Circulate the I.O.N<span class="failure">*</span></div>
										
									</div>
									
									<div class="line" >
											<div class="leftmar">
												<div style="float: left; width : 35%">
														<form:select path="sfidNames" id="SelectLeft2" size="10" multiple="true" cssStyle="width:300px">
															<form:options items="${sessionScope.level1SfidList}" itemValue="userSfid" itemLabel="nameInServiceBook"/>
														</form:select>
													</div>
												<div style="float: left; width : 10%; margin-top: 60px;">									
													<div style="margin-bottom: 5px;" align="center">
													     <input style="margin-bottom: 5px" id="MoveRight2" type="button" value=" Add " />
	     											     <input id="MoveLeft2" type="button" value=" Remove " />    											        
	     											</div>		      																				
		      									</div>
												<div style="float: left; width : 30%">
														<form:select path="sfidName" id="SelectRight2" size="10" multiple="true" cssStyle="width:300px">
														
														</form:select>
													</div>
											</div>
									</div>
									</div>
									</c:if> 
										<div class="line">
										<div style="margin-left:36%">
                                       
                                         										
											<a href="javascript:ionCirculatedSubmit('forwarded');"><div class="appbutton" id="button" style="display:none">Forward</div></a>
                                       
											<a href="javascript:ionCirculatedSubmit('');"><div class="appbutton" id="button1" style="display:show">Save</div></a>
											
										</div>
										
											
									</div>
									
									<div class="line" id="result1">	
									</div>
									<div class="line height"></div>
									<div class="line" id="displayTable">
										<jsp:include page="NoticeBoardDataList.jsp"/>
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
		
			<script>
			
		</script>
				
			</div>
			<div><jsp:include page="Footer.jsp" /></div>			
		</div>
		<form:hidden path="param" id="param"/>
		<form:hidden path="type" id="type" />
		<form:hidden path="id" id="id"/>
		<form:hidden path="jsonValues1" id="jsonValues1"/>
		<form:hidden path="jsonValues2" id="jsonValues2"/>
		<form:hidden path="sfidNameId" id="sfidNameId"/>
		<form:hidden path="refOrgRoleId" id="refOrgRoleId"/>
		
		
		
		
		</form:form>
		<script>
			
				
		</script>
	</body>
</html>
<!-- End:NoticeBoardDetails.jsp-->

