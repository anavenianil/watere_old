<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin: OptionalCertificate.jsp -->
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

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/date.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script language="javascript" src="script/promotions.js"></script>

<title>OptionCertificate</title>
</head>

<body>
	<form:form method="post" commandName="promotion">
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
								<div class="headTitle">Option Certificate</div>
								<%-- Content Page starts --%>
								<div class="line">
								<c:choose>
								<c:when test="${(promotion.appRoleID !=(14) && promotion.appRoleID !=(2))}">
									<div class="line">
										<div class="leftmar quarter">SFID</div>
										<div class="quarter">${promotion.employeeDetails.userSfid}</div>
										<div class="leftmar quarter">${promotion.employeeDetails.nameInServiceBook}</div>
									</div>
								</c:when>
								<c:when test="${(promotion.appRoleID ==(14) || promotion.appRoleID ==(2))}">
								
					             
					             	<div class="line">
				                 <div class="quarter leftmar" style="width: 20%;">pending Category Type<span class="mandatory">*</span></div>
				                 <div class="quarter">
				                 <form:select path="assessmentCategoryID" id="assessmentCategoryID"  cssClass="formSelect" cssStyle="width:90%">
				                   <option value="0">Select</option>
										<c:forEach var="assessmentDetails" items="${promotion.assessmentDetails}" >
									
									<option value="${assessmentDetails.yearID}-${assessmentDetails.boardID}-${assessmentDetails.assessmentCategoryID}">${assessmentDetails.reservation}</option>
									
										
										 </c:forEach>
				 
			                   	</form:select>
				                </div> 
			                   
			                    
				                 <div class="quarter leftmar" style="width: 20%;">Saved Category Type<span class="mandatory">*</span></div>
				                 <div class="quarter">
				                 <form:select path="assessmentTypeID" id="assessmentTypeID"  cssClass="formSelect" cssStyle="width:90%" onchange="javascript:setvalue();" >
				                   <option value="0">Select</option>
										<c:forEach var="assessmentDetails" items="${promotion.assessmentDetails1}" >
									
									<option value="${assessmentDetails.yearID}-${assessmentDetails.boardID}-${assessmentDetails.assessmentCategoryID}">${assessmentDetails.reservation}</option>
									
										
										 </c:forEach>
				 
			                   	</form:select>
				                </div> 
			                     </div>
			                    <div class="line">
										<div class="half"  style="margin-left: 50%;">
			                    <div class="expbutton"><a href="javascript:getSfidList();"><span>Search</span></a></div>
			                    </div></div> 
								<br></br><br></br>
								<div class="line">
								<div class="line">
								<fieldset title="Sfid List "><legend><strong><font color='green'> Sfid List</font></strong></legend>
									<div class="line" id="sfidList">
									<div class="leftmar quarter"  style="width: 20%;">Pending Sfid:</div>
									     <div class="quarter">
										<form:select path="sfID" cssStyle="" id="unsavedSfid">
										<option value="0">Select</option>
										<c:forEach var="assessmentDetails" items="${promotion.assessmentDetails}" >
									<c:if test="${assessmentDetails.optionStatus eq '' || assessmentDetails.optionStatus eq null || assessmentDetails.optionStatus == 0}">
									<option value="${assessmentDetails.sfID}">${assessmentDetails.sfID}-${assessmentDetails.empName}</option>
									</c:if>
										
										 </c:forEach>
										</form:select>
										
										</div>
									
									<div class="leftmar quarter"  style="width: 20%;">Saved Sfid:</div>
									<div class="quarter">
										<form:select path="sfID" id="savedSfid" cssStyle="left-margin:10%" onchange="javascript:getOptioncertificate(this)">
										<option value="0">Select</option>
										<c:forEach var="assessmentDetails" items="${promotion.assessmentDetails}" >
									<c:if test="${assessmentDetails.optionStatus  ne null && assessmentDetails.optionStatus != '' || assessmentDetails.optionStatus != 0}">
									<option value="${assessmentDetails.sfID}">${assessmentDetails.sfID}-${assessmentDetails.empName}</option>
									</c:if>
										
										 </c:forEach>
										</form:select>
										</div>
     							</div>
     							</fieldset>
     							</div></div>
     							</c:when>
     							</c:choose>
     							<div class=line><div class=line>
     							<div class=line>
									<div class=line>
									  <div class="leftmar">
									  <form:radiobutton path="payUpdate" id="payUpdate" value="1" onclick="javascript:enableLower()"/><label><font style="font-weight: normal">  My Pay on Promotion may be fixed in the <b>Higher Grade </b>or post on the basis of FR
									    22(I)(a)(1) straight away without any further review on accrual of increment in the pay scale of the lowrer grade/post.
									   </font>
									  </label>
									  <br></br>
									  <form:radiobutton path="payUpdate" id="payUpdate" value="2" onclick="javascript:enableLower()"/> <label> <font style="font-weight: normal">  My pay on promotion may be fixed initially in the manner as provided under Saving 
									  Clause FR 22 (I)(a)(1) which may be refixed on the basis of the said provision after accural of next increment on -----(date) in the scale of pay of the <b>Lower grade/post </b>.
									   </font>
									   </label>
									  </div>
									</div></div></div></div>
									<br></br> 
									&nbsp;
									
									<div id="lowergarde" style="display:none">
									<div class="line">
										<div class="leftmar quarter">Scale of Pay in the lower grade<span class="mandatory">*</span>
		                                </div>
										<div class="quarter"><form:input path="scaleOfPay" id="scaleOfPay" onkeypress="return checkFloat(event,'scaleOfPay');"/></div>
									</div>
									<div class="line">
										<div class="leftmar quarter">Date of Increment<span  class="mandatory">*</span>
		                                </div>
										<div class="quarter">
											<form:input path="incrementDate" id="incrementDate" cssClass="dateClass" readonly="true" onchange="javascript:checkDate(this)"/>
											<img  src="./images/calendar.gif" id="incrementDateImg" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"incrementDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"incrementDateImg",singleClick : true,step : 1});
											</script>
										</div>
									</div>
									</div>
									 <div class="line">
										<div class="half"  style="margin-left: 25%;">
											<div class="expbutton"><a href="javascript:submitOptionalCert()"><span>Submit</span></a></div>
											<div class="expbutton"><a href="javascript:resetOptionalCert()"><span>Clear</span></a></div>
										</div>
									</div>
									<div class="line"><hr /></div>
									<div>
								 <u style="color: brown">Note</u><label><font style="font-weight:normal:" color="brown">:For the promoted employees whose designation & promotion effective date is updated will be removed from Saved Sfid  list.		
							     </font></label>	
									</div>
									
									<div class="line" id="result">
										<jsp:include page="OptionalCertificateList.jsp"></jsp:include>
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
		</form:form>
	</body>
</html>
<!-- End: OptionalCertificate.jsp -->
