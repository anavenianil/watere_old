<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:tutionFeeRequestDetails.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page
	import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.beans.family.FamilyBean"%>
	<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/menu.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<script language="javascript" src="script/tutionFee.js"></script>

<script>
		
		<%if (session.getAttribute("familyDetailsList") != null) {%>
			familyDetailsJSON =<%=(net.sf.json.JSONArray) JSONSerializer.toJSON((List<FamilyBean>) session.getAttribute("familyDetailsList"))%>;
		<%}%>
			
		
	</script>
	<title>TuitionFee Request Form</title>
</head>

<body>
	<form:form method="post" commandName="tutionFee">
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
								<div class="headTitle"><u>Claim For Reimbursement Of Children Education Allowance</u> </div>
								<div>
								<div id="returnrequest">
									<%-- Content Page starts --%>
									<div class="line" id="childInfo">
										<fieldset><legend><strong><font color='green'>Children Info</font></strong></legend>
										<display:table name="${tutionFee.familyList}" excludedParams="*"
												export="false" class="list" requestURI="" id="family" pagesize="5"
												sort="list">
													<display:column title="Name" style="width:20%;text-align:center">${family.name}</display:column>
													<display:column title="Date Of Birth/Age" style="width:20%;text-align:center">${family.dob}</display:column>
													<display:column title="Relation Type" style="width:20%;text-align:center">${family.relation}
                                                    </display:column>
													<display:column title="Class" style="width:20%;text-align:center">
                                                       <form:select path="classId" id="classId${family.id}" cssStyle="width:145px;" onchange="javascript:onChangeBoardType(${family.id});">
												             <form:option value="select">Select</form:option>
												             <c:forEach items="${tutionFee.mapChildList}" var="list">
												             <c:if test="${family.id eq list.key}">
												             <c:forEach items="${list.value}" var="childList">
												             <form:option value="${childList.id}">${childList.className}</form:option>
												             </c:forEach>
												             </c:if>
												             </c:forEach>
												             </form:select>
                                                    </display:column>
                                                    <display:column title="Central/State" style="width:15%;text-align:center">
                                                         <form:select path="boardId" id="boardId${family.id}" cssStyle="width:145px;" onchange="javascript:onChangeBoardType(${family.id});">
                                                         <form:option value="select">Select</form:option>
                                                         <form:option value="C">Central</form:option>
											           <form:option value="S">State</form:option>
							   	                    </form:select>
                                                      </display:column>
                                                      <display:column title="Type" style="width:15%;text-align:center">
                                                         <form:select path="typeId" id="typeId${family.id}" cssStyle="width:145px;" onchange="javascript:stepGrid('${family.id}');">
                                                         <form:option value="select">Select</form:option>
												           <form:option value="Q">Quarterly</form:option>
												           <form:option value="H">Halfyearly</form:option>
												           <form:option value="A">Annually</form:option>
												          </form:select>
                                                      </display:column>
                                                      
                                      
                                    	</display:table>
										</fieldset>
								</div>
									<%int i=0;
								    %>
		
	    
		 <div class="line" id="claimDeatails">
		 <c:forEach items="${tutionFee.familyList}" var="flist">	
		  <div class="line" id="hidingClaimDiv${flist.id}">
		 <fieldset id="field${flist.id}" style="display:none"><legend><strong><font color='green'>In respective of </font><font color="red">${flist.name}</font></strong></legend>
						  <div class="quarter bold">Academic Year<span style="color:red">*</span></div>
								<form:select path="academicYear" id="academicYear${flist.id}" cssStyle="width:145px;" onchange="javascript:checkTuitionFeeAcademicYearDetails('${flist.id}');" >
						     	<form:option value="select">Select</form:option>
						     	<c:forEach items="${tutionFee.mapYearList}" var="year">
								<c:if test="${flist.id eq year.key}">
								<c:forEach items="${year.value}" var="yearList">
								<form:option value="${yearList.fyFrom}">${yearList.fyToFrom}</form:option>
								</c:forEach>
								</c:if>
								</c:forEach>
						       </form:select>
						      <div id="tfID">&nbsp;</div>
					 <div class="line" id="claimDeatailsDiv${flist.id}">	   
						   <div class="line" id="CQ${flist.id}" style="display: none;">
						   <div class="quarter bold">Period Of Claim<span style="color:red">*</span></div>
						   <form:select path="limitId" cssClass="fromSelect" style="width:25%" >
                                  <form:option value="select">Select</form:option>
                                   <c:forEach var="cql" items="${tutionFee.centralQuarterList}">
                                  <c:if test="${cql.id==flist.id}">
                                  <form:option value="${cql.key}">${cql.value}</form:option>
                                  </c:if>
                                  </c:forEach>
			                </form:select>
				          </div>
				          
				          <div class="line" id="CH${flist.id}" style="display: none;">
						      <div class="quarter bold">Period Of Claim<span style="color:red">*</span></div>
						   <form:select path="limitId" cssClass="fromSelect" style="width:25%" >
                                  <form:option value="select">Select</form:option>
                               <c:forEach var="chl" items="${tutionFee.centralHalfList}">
                                  <c:if test="${chl.id==flist.id}">
                                  <form:option value="${chl.key}">${chl.value}</form:option>
                                  </c:if>
                                  </c:forEach> 
			                </form:select>
				          </div>
				          
				          <div class="line" id="SQ${flist.id}" style="display: none;">
						      <div class="quarter bold">Period Of Claim<span style="color:red">*</span></div>
						   <form:select path="limitId"  cssClass="fromSelect" style="width:25%" >
                                 <form:option value="select">Select</form:option>
                                   <c:forEach var="sql" items="${tutionFee.stateQuarterList}">
                                  <c:if test="${sql.id==flist.id}">
                                  <form:option value="${sql.key}">${sql.value}</form:option>
                                  </c:if>
                                  </c:forEach>
                          </form:select>
				          </div>
				          
				          <div class="line" id="SH${flist.id}" style="display: none;">
						     <div class="quarter bold">Period Of Claim<span style="color:red">*</span></div>
						   <form:select path="limitId" cssClass="fromSelect" style="width:25%" >
                                  <form:option value="select">Select</form:option>
                                 <c:forEach var="shl" items="${tutionFee.stateHalfList}">
                                  <c:if test="${shl.id==flist.id}">
                                  <form:option value="${shl.key}">${shl.value}</form:option>
                                  </c:if>
                                  </c:forEach>
                         </form:select>
				          </div>
                          <div class="line" id="CA${flist.id}" style="display: none;">
								 <div class="quarter bold">Period Of Claim<span style="color:red">*</span></div>
						     <form:select path="limitId"  cssClass="fromSelect" style="width:25%" >
                                  <form:option value="select">Select</form:option>
                               <c:forEach var="cal" items="${tutionFee.centralAnnualList}">
                                  <c:if test="${cal.id==flist.id}">
                                  <form:option value="${cal.key}">${cal.value}</form:option>
                                  </c:if>
                                  </c:forEach> 
                             </form:select>
				          </div>
				          <div class="line" id="SA${flist.id}" style="display: none;">
								 <div class="quarter bold">Period Of Claim<span style="color:red">*</span></div>
						         <form:select path="limitId"  cssClass="fromSelect" style="width:25%" >
                                  <form:option value="select">Select</form:option>
                                 <c:forEach var="sal" items="${tutionFee.stateAnnualList}">
                                  <c:if test="${sal.id==flist.id}">
                                  <form:option value="${sal.key}">${sal.value}</form:option>
                                  </c:if>
                                  </c:forEach> 
                                  </form:select>
				          </div>
				 </div>
				         <table width="90%" cellpadding="2" cellspacing="0" align="center"
									border="1" class="list" bgcolor="#10519a" id="itConfigSavings">
								   <tr class=list>
										<th width="25%" style="text-align: center">Detail</th>
										<th width="24%" style="text-align: center">Receipt No</th>
										<th width="17%" style="text-align: center">Dated</th>
										<th width="15%" style="text-align: center">Amount</th>
										<th width="2%" style="width:5%;text-align:center">Add</th>
										<th width="2%" style="width:5%;text-align:center">Del</th>
										<th style="display:none"></th>
									</tr>
									
								   <c:forEach items="${tutionFee.claimList}" var="sub">
								       <tr class="even" id="row<%=i %>${flist.id}">
								           <td size="35"><b>${sub.claimName}</b></td>
								            <td><input  type="text" size="30" maxlength="150" id="receiptNo<%=i %>"  onkeypress="javascript:checkForPeriodOfClaim(<%=i %>,'${flist.id}')" /></td>
											<td>
												<input type="text" readonly="readonly" id="dated<%=i %>" style="height:16px;width:150px;font-size: 12px;font-weight: bold;"  id="dated<%=i %>" onfocus ="javascript:Calendar.setup({inputField :'dated<%=i %>',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" />
											</td>
											<td style="" onkeypress="javascript:checkForPeriodOfClaim(<%=i %>,'${flist.id}')"><input id="amount<%=i %>"
													size="20" maxlength="6" style="float: right;text-align:right" onkeypress="return checkInt(event);"  onkeyup="javascript:sumAllClaimAmounts();"/></td>
											<td><input type="button" id="add<%=i %>" value="+" onclick="javascript:checkCreateNewRowForTuition(<%=i %>,'${flist.id}','${sub.id}','${sub.claimName}');"/></td>
											<td><input type="button" id="del<%=i %>" value="-" onclick="javascript:deleteRowOfTuition(this,'subfield${flist.id}');" style="display: none;"/></td>
											
											<td style="display:none"><input id="amountId<%=i %>" value="${sub.id}"/>${sub.id}</td>
										</tr>
										<%i++; %>
								</c:forEach></table>
							</fieldset>
							</div>
								</c:forEach>
								</div>
								<div class="line" id="grandTotalDiv" style="display:none">
								<table class="list">
								<tr class="even">
				                    <td width="70%" ><font color="blue"><b>GRAND TOTAL</b></font></td>
				                    <td width="30%">
					                  <form:input path="grandTotal" readonly="true" id="grandTotal" style="text-align:right"
						                  size="40"/></td></tr></table>
								</div>
								<div class="line">
								<div class="expbutton" style="margin-left:80%"><a onclick="javascript:manageTutionFeeRequestDetails();"> <span style="align:right">Send Request</span></a></div></div>
									<script>
									$jq('.pagebanner').hide();
									</script>	
									<%-- Content Page end --%>
								</div>
								
								<div>&nbsp;</div>
							</div>
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
<script>
tutionFeeBean= <%= (net.sf.json.JSONObject) session.getAttribute("tutionFeeBean") %>;
</script>		
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		<form:hidden path="pk"/>

	<form:hidden path="requestID"/>
		<form:hidden path="requestId"/>

		</form:form>
	</body>
</html>
<!-- End:tutionFeeRequestDetails.jsp -->