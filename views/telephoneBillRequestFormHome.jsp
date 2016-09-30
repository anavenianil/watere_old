<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:telephoneBillRequestFormHome.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/tabs.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.min.js"></script>
<script type="text/javascript" src="script/jquery.ui.custom.min.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/telephone.js"></script>
<script type="text/javascript" src="script/date.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<title>Telephone Bill Request Form</title>
</head>
<body>
	<form:form method="post" commandName="telephone">
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
								<div class="headTitle"><u>Claim For Reimbursement Of Residential Telephone Bills</u></div>
								<div class="line" id="result"></div>
								<div>
									<%-- Content Page starts --%>
									<c:if test="${telephone.message eq 'yes'}">
								    <div class="line">
										<div class="half leftmar">Name</div>
										<div class="half ">${telephone.employeeBean.nameInServiceBook}</div>
									</div>
									  <div class="line">
										<div class="half leftmar">Designation,Sfid</div>
										<div class="half ">${telephone.employeeBean.designationDetails.name}&nbsp;&nbsp;,&nbsp;${telephone.employeeBean.userSfid }</div>
									</div>
									<div class="line">
										<div class="half leftmar">Directorate/Divn</div>
										<div class="half ">${telephone.employeeBean.divisionName}</div>
									</div>
									<div class="line">
										<div class="half leftmar">Internal Telephone No</div>
										<div class="half ">${telephone.employeeBean.internalNo}</div>
									</div>
									<div class="line">
										<div class="half leftmar">Bank Account No</div>
										<div class="half ">${telephone.employeeBean.accountNo}</div>
									</div>
								 	<div class="line">
								    <div class="half leftmar bold">Claim(Includes WithInternet/withOutInternet)</div>
								    	  <input type=radio name="teleApplicableEmp" tabindex="1" value="1" id="teleApplicableEmp1" onclick="javascript:checkTeleEmpDetailsWithInternet('${telephone.employeeBean.designation}');">With Internet</input>
										  <input type=radio name="teleApplicableEmp" tabindex="1" value="2" id="teleApplicableEmp2" onclick="javascript:checkTeleEmpDetailsWithInternet('${telephone.employeeBean.designation}');">WithOut Internet</input>
								    </div>
								    <div class="line" id="internetDetailsDiv" style="display: none">
								    <jsp:include page="telephoneBillRequestFormHomeInternetDetails.jsp" />
								    </div>
								   
								     <div class="line">
								         <div class="half leftmar bold">Period Of Claim<span class="failure">&nbsp;*</span></div>
								     </div>
								    <div class="line">
										<div class="quarter leftmar">From Date<span class="failure">&nbsp;*</span></div>
										<div class="quarter">
											<form:input path="fromDate" id="fromDate" cssClass="dateClass" readonly="true" onchange="javascript:checkForTeleInternet();"/>
											<img  src="./images/calendar.gif" id="fromDateImg" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"fromDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"fromDateImg",singleClick : true,step : 1});
											</script>
									
										</div>				
									 <div class="quarter leftmar">To Date<span class="failure">&nbsp;*</span></div>
										<div class="quarter">
											<form:input path="toDate" id="toDate" cssClass="dateClass" readonly="true" onchange="javascript:checkTelephoneDates();"/>
											<img  src="./images/calendar.gif" id="toDateImg" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"toDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"toDateImg",singleClick : true,step : 1});
											</script>
										</div>
									</div>	
									 <div class="line" id="missingPeriodDiv" style="display: none">
								     <jsp:include page="telephoneBillMissingPeriodDetails.jsp"/>
								     </div>
							<%int i=5;%>
			<div id="telephoneDetails">   
		   <table width="98%" cellpadding="2" cellspacing="0" align="center"
									border="1" class="list" bgcolor="#10519a" id="telephoneDetailsTab">
         			    <tr class=list>
         			             <th width="18%">Details</th>
         			    	     <th width="10%">Telephone No.</th>
	        			   		 <th width="10%">Bill No</th>	
	         			   		 <th width="10%">Bill Dated</th>
	                	   		 <th width="10%">Receipt No</th>
	                	   		 <th width="10%">Receipt Dated</th>
	                       		 <th width="10%">Amount Claimed(<font size="4.5em"><span class="WebRupee" >R</span></font>)</th>
	                       		 <th width="10%">Service Tax(<font size="4.5em"><span class="WebRupee" >R</span></font>)</th>
	                      		 <th width="10%">Total(<font size="4.5em"><span class="WebRupee" >R</span></font>)</th>
	                    </tr>
	                     <c:forEach items="${telephone.telephoneClaimList}" var="sub">
	                         <tr class="even">
	                                         <td>${sub.name}</td>
	                                         <c:choose>
	                                         <c:when test="${sub.claimId!=null && sub.telephoneNo!=null}">
	                                          <td>
	                                          <input  id="telephoneNo"  size="12"  style="color: blue; font-size: 10pt"  onkeypress="return isAlphaNumaricExp(event);" value="${sub.telephoneNo}"/>
	                                          </td>
	                                         </c:when>
	                                         <c:otherwise>
	                                          <td><input id="telephoneNo" onkeypress="return isAlphaNumaricExp(event);" size="12" /></td>
	                                         </c:otherwise>
								             </c:choose>
	                                         <td>
												 <input id="billNo" size="10"/>
								            </td>
											<td > <input type="text" readonly="readonly" id="billDated<%=i %>" style="height:16px;width:82px;font-size:
					                                          11px;font-weight: bold;"  id="toDate" onfocus ="javascript:Calendar.setup({inputField :
					                                          'billDated<%=i %>',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger<%=i %>',step : 1});" onchange="javascript:checkTelephoneBillRecieptDates();"/>				
										    </td>	
										    <td >
												         <input id="receiptNo" size="10"/>
								            </td>
								            <td >
								            <input type="text" readonly="readonly" id="receiptDated<%=i %>" style="height:16px;width:82px;font-size:
					                                          11px;font-weight: bold;"  id="toDate" onfocus ="javascript:Calendar.setup({inputField :
					                                          'receiptDated<%=i %>',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger1<%=i %>',step : 1});" onchange="javascript:checkTelephoneBillRecieptDates();"//>				
								            </td>	
								            <td >
												         <input id="amountClaimed" onkeypress="return checkInt(event);" size="8" maxlength="8"   onkeyup="javascript:sumTelephoneEntries();"/>
								            </td>
								            <td >
												         <input id="serviceTax" onkeypress="return checkFloat(event);" size="8" maxlength="8"   onkeyup="javascript:sumTelephoneEntries();" />
								            </td>
								             <td >
												         <input id="total" onkeypress="return checkInt(event);" size="8"  readonly="readonly"/>
								            </td>
								            <td style="display:none"><input id="teleId" value="${sub.claimId}"/>${sub.claimId}</td>
				            <%i++; %>
							 </tr>
	                       </c:forEach>
	                          </table></div>
	                          <div class="line" id="grandTotalDiv">
								<table class="list">
								<tr class="even">
				                    <td width="70%" ><font color="blue"><b>GRAND TOTAL</b></font></td>
				                    <td width="30%">
					                  <form:input path="grandTotal" readonly="true" id="grandTotal" style="text-align:right"
						                  size="40"/><font size="4.5em"><span class="WebRupee" >R</span></font></td></tr></table>
							  </div>
                  			<div class="line">
										           <div class="quarter leftmar" style="margin-left: 8px;">User Remarks<span class='failure'>*</span></div>
													<div class="quarter"><form:textarea path="userRemarks" id="userRemarks" cols="20" rows="4" onkeypress="textCounter(this,document.forms[0].counter,500);" onkeyup="textCounter(this,document.forms[0].counter,500);"></form:textarea>
													<input type="text" name="counter" id="counter" value="500" class="counter" disabled="disabled"/>
													</div>
							</div>
							
								<div class="line">
								<div class="expbutton" style="margin-left:80%"><a onclick="javascript:submitTelephoneBillRequestDetails();"><span style="align:right">Send Request</span></a></div>
								</div>
								</c:if>
								<c:if test="${telephone.message eq 'no'}">
								<span class="failure"><spring:message code="telephoneEligibilityMessage"/></span>
								</c:if>
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
				<div><jsp:include page="Footer.jsp" /></div>
			</div>
						
		</div>
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		<form:hidden path="requestId"/>
		<form:hidden path="requestID"/>
		</form:form>
	</body>
</html>
<!-- End:telephoneBillRequestFormHome.jsp -->