<!-- Begin : TadaTdAdvanceDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<script type="text/javascript" src="script/tada.js"></script>


<div>
	<div class="line">
		<div style="display: none;"> <!--hiiden by bkr 22/04/2016  -->
			<div class="quarter bold">Basic Pay & Grade Pay</div>
			<div class="quarter">:&nbsp;<font size="4.5em"><span class="WebRupee" >R</span></font>${workflowmap.tadaTdAdvanceRequestDTO.basicPay}/- & <font size="4.5em"><span class="WebRupee" >R</span></font>${workflowmap.tadaTdAdvanceRequestDTO.gradePay}/-</div>
		</div>
		<div class="quarter bold">Address Of TD Work Place</div>
		<div class="quarter" id="tdWorkPlace">:&nbsp;${workflowmap.tadaTdAdvanceRequestDTO.tdWorkPlace}</div>
		
		
		<!--added by bkr 26/04/2016   -->
		<c:if test="${ not empty workflowmap.tadaTdAdvanceRequestDTO.purpose}">
		<div class="quarter bold">Purpose</div>
		<div class="quarter" id="purpose">:&nbsp;${workflowmap.tadaTdAdvanceRequestDTO.purpose}</div>
		</c:if>
		
	</div>
	<div class="line">
		<div class="quarter bold">Stay Duration</div>
		<div class="quarter" id="stayDuration">: ${workflowmap.tadaTdAdvanceRequestDTO.stayDuration} Days</div>
		<div style="display: none">
		<div class="quarter bold">Project For Which Move is authorized</div>
		</div>
		<div>
		<div class="quarter" id="authorizedMove">
		<div style="display: none">
		<c:if test="${workflowmap.tadaTdAdvanceRequestDTO.authorizedMove==1}">: Build-Up</c:if>
		</div>
		<c:if test="${workflowmap.tadaTdAdvanceRequestDTO.authorizedMove==2}">: Project 
		<select name="projectName" id="projectName" style="width:70px" disabled="disabled">
		                                        <c:if test="${workflowmap.tadaApprovalRequestDTO.projectName==null || workflowmap.tadaApprovalRequestDTO.projectName==''}">
											    <option  value="Select">Select</option>
											    </c:if>
											    <c:forEach var="prjlist" items="${workflowmap.prjlist}">
											    <c:choose>
											    <c:when test="${workflowmap.tadaApprovalRequestDTO.projectName==prjlist.projectName}">
											    <option value="${workflowmap.tadaApprovalRequestDTO.projectName}" selected="selected">${workflowmap.tadaApprovalRequestDTO.projectName}</option>
											    </c:when>
											    <c:otherwise>
											    <option  value="${prjlist.projectName}">${prjlist.projectName}</option>
											    </c:otherwise>
											    </c:choose>
											    </c:forEach>
											    </select>
											    
	   		
	    <c:if test="${(workflowmap.requesterSfid ne workflowmap.sfid || workflowmap.roleInstanceName eq 'TADA TASK HOLDER' || workflowmap.roleInstanceName eq 'TA DA /Medical Section Head') && (workflowmap.checkStage eq 'previous' || workflowmap.checkStage eq 'last') && (workflowmap.message eq 'PENDING' || workflowmap.message eq 'pending')}">
			 <a href="javascript:showProjectList()">change</a>
	    </c:if>
	    
		</c:if>
		 </div>
		</div>							
	 </div>
	 <!--this div hidden by bkr 26/04/2016 bcz we are not enter this field befor form  -->
	 <div class="line" style="display: none">
		<div class="quarter bold">DA Requirement</div>
		<div class="quarter">: <c:if test="${workflowmap.tadaApprovalRequestDTO.daType=='hotelrate'}">Hotel Rates</c:if>
		                                   <c:if test="${workflowmap.tadaApprovalRequestDTO.daType=='normalrate'}">Normal Rates</c:if>
		                                   <c:if test="${workflowmap.tadaApprovalRequestDTO.daType=='na'}">Not Required</c:if>
		</div>
	 </div>
	<c:if test="${(workflowmap.requesterSfid ne workflowmap.sfid || workflowmap.roleInstanceName eq 'TADA TASK HOLDER' || workflowmap.roleInstanceName eq 'TA DA /Medical Section Head') && (workflowmap.checkStage eq 'previous' || workflowmap.checkStage eq 'last') && (workflowmap.message eq 'PENDING' || workflowmap.message eq 'pending')}">
	<div class="line">
		<div class="quarter bold">Issuing Authority</div>
		<div class="quarter">: 
		    <form:select path="issueAuthority" id="issueAuthority" cssStyle="width:145px">
				<option value="asl">ASL</option>
				<option value="cda">CDA</option>
			</form:select>
		</div>
	</div>
		</c:if>
	<div class="line" ><fieldset>
	
	<div ><u>For Information: </u><br/>Refer to letter no DBFA/FA/83711/M/01/736/D(R&D) DATED 28TH FEB 2002 POINT B TADA ADVANCE<br/>
	                          ISSUING AUTHORITY :: ASL  <br/>
	                          <div >
	                          <ul>
	                          <li > If applied 5 DAYS BEFORE DEPARTURE & Mode of travel is BY AIR </li>
                              <li > If applied 10 DAYS BEFORE DEPARTURE & Mode of travel is other than By Air</li></ul>
                             ISSUING AUTHORITY :: CDA  <br/>
                             <ul><li > OTHER CASES WILL BE SENT TO CDA FOR PRE AUDIT AND PAYMENT </li></ul>
    </div>
    </div>
	</fieldset>
	</div>

	<div class="line">
		<div class="quarter bold">Accommodation and Food Amount</div>
		<div class="full">: No. Of Days : <input type="hidden" id="noOfDaysAcc1"  value="${workflowmap.tadaTdAdvanceRequestDTO.requestId}" /><input type="text"  size="10px" id="noOfDaysAcc" readonly="readonly" style="text-align: right" value="${workflowmap.tadaTdAdvanceRequestDTO.noOfDaysAcc}" onkeypress="javascript:return checkFloat(event,'noOfDaysAcc');" onkeyup="javascript:setTotalAccAdvAmount();showIssuedTdAdvAmt();" /> 
		X Amount per Day :
		<c:choose> 
		<c:when test="${(workflowmap.requesterSfid eq workflowmap.sfid || workflowmap.roleInstanceName eq 'TADA TASK HOLDER' || workflowmap.roleInstanceName eq 'TA DA /Medical Section Head') && (workflowmap.checkStage eq 'previous' || workflowmap.checkStage eq 'last') && (workflowmap.message eq 'PENDING' || workflowmap.message eq 'pending')}">
		   <input type="text" size="10px"  id="accAmtPerDay" style="text-align: right" value="${workflowmap.tadaTdAdvanceRequestDTO.accAmtPerDay}" onkeypress="javascript:return checkFloat(event,'accAmtPerDay');" onkeyup="javascript:setTotalAccAdvAmount();showIssuedTdAdvAmt();" /> 
		   = <input type="text" size="10px" readonly="readonly" id="totalAccAmt" style="text-align: right" value="${workflowmap.tadaTdAdvanceRequestDTO.noOfDaysAcc*workflowmap.tadaTdAdvanceRequestDTO.accAmtPerDay}" />
		   </br>
		 Taxi : <input type="text" size="10px" readonly="readonly" id="taxi" style="text-align: right" value="${workflowmap.tadaTdAdvanceRequestDTO.taxi}" /> 
		  </br>
		 onTransit : <input type="text" size="10px" readonly="readonly" id="onTransit" style="text-align: right" value="${workflowmap.tadaTdAdvanceRequestDTO.onTransit}" /> 
		</c:when>
		<c:otherwise>
		   <input type="text" size="10px"  id="accAmtPerDay" readonly="readonly" style="text-align: right" value="${workflowmap.tadaTdAdvanceRequestDTO.accAmtPerDay}" onkeypress="javascript:return checkFloat(event,'accAmtPerDay');"  /> 
		   = <input type="text" size="10px" readonly="readonly" id="totalAccAmt" style="text-align: right" value="${workflowmap.tadaTdAdvanceRequestDTO.noOfDaysAcc*workflowmap.tadaTdAdvanceRequestDTO.accAmtPerDay}" />
		</br>
		Taxi : <input type="text" size="10px" readonly="readonly" id="taxi" style="text-align: right" value="${workflowmap.tadaTdAdvanceRequestDTO.taxi}" /> 
		  </br>
		 onTransit : <input type="text" size="10px" readonly="readonly" id="onTransit" style="text-align: right" value="${workflowmap.tadaTdAdvanceRequestDTO.onTransit}" /> 
		
		</c:otherwise>
		
		</c:choose>
		</div>
	</div>
	<!-- added by bkr 22/04/2016 hidden div -->
	<div style="display: none;">
	<div class="line">
		<div class="quarter bold">Food Amount</div>
		<div class="full">: No. Of Days : <input type="text"  size="10px" id="noOfDaysFood" readonly="readonly" style="text-align: right" value="${workflowmap.tadaTdAdvanceRequestDTO.noOfDaysAcc}" onkeypress="javascript:return checkFloat(event,'noOfDaysFood');" onkeyup="javascript:setTotalFoodAdvAmount();showIssuedTdAdvAmt();" /> 
		X Amount per Day : 
		<c:choose>
		<c:when test="${(workflowmap.requesterSfid eq workflowmap.sfid || workflowmap.roleInstanceName eq 'TADA TASK HOLDER' || workflowmap.roleInstanceName eq 'TA DA /Medical Section Head') && (workflowmap.checkStage eq 'previous' || workflowmap.checkStage eq 'last') && (workflowmap.message eq 'PENDING' || workflowmap.message eq 'pending')}">
		  <input type="text" size="10px"  id="foodAmtPerDay" style="text-align: right" value="${workflowmap.tadaTdAdvanceRequestDTO.foodAmtPerDay}" onkeypress="javascript:return checkFloat(event,'foodAmtPerDay');" onkeyup="javascript:setTotalFoodAdvAmount();showIssuedTdAdvAmt();" /> 
		  = <input type="text" size="10px" readonly="readonly" id="totalFoodAmt" style="text-align: right" value="${workflowmap.tadaTdAdvanceRequestDTO.noOfDaysAcc*workflowmap.tadaTdAdvanceRequestDTO.foodAmtPerDay}" />
	    </c:when>
	    <c:otherwise>
	      <input type="text" size="10px"  id="foodAmtPerDay" readonly="readonly" style="text-align: right" value="${workflowmap.tadaTdAdvanceRequestDTO.foodAmtPerDay}" onkeypress="javascript:return checkFloat(event,'foodAmtPerDay');" /> 
		  = <input type="text" size="10px" readonly="readonly" id="totalFoodAmt" style="text-align: right" value="${workflowmap.tadaTdAdvanceRequestDTO.noOfDaysAcc*workflowmap.tadaTdAdvanceRequestDTO.foodAmtPerDay}" />
	    </c:otherwise>
	    </c:choose>
	    </div>
	</div>
	</div>
	<div class="headTitle">Journey Details</div>
	<div class="line">
		<table BORDER="1" cellpadding="2" cellspacing="0" id="reqJourneyDetailsId" style="width:100%" class="sub_2">
			<tr>
				<td  class="tabletd" align="center" >Departure Date</td>
				<td  class="tabletd" align="center" >From Place</td>
				<td  class="tabletd" align="center" >To Place</td>
				<td  class="tabletd" align="center" >No. Of Days</td>
				<td  class="tabletd" align="center" >Mode of Conveyance</td>
				<td  class="tabletd" align="center" >Class Of Entitlement</td>
				<td  class="tabletd" align="center" >Tatkal Quota1</td>
				<td  class="tabletd" align="center" >Ticket Fare in <font size="4.5em"><span class="WebRupee" >R</span></font></td>
				<td  class="tabletd" align="center" ><font color="blue">Ticket Fare Aft Res. in <font size="4.5em"><span class="WebRupee" >R</span></font></font></td>
			</tr>
			
			<c:forEach var="tadaTdReqJourneyList" items="${workflowmap.tadaTdReqJourneyList}">
				<tr>
					<td><fmt:formatDate pattern="dd-MMM-yyyy" value="${tadaTdReqJourneyList.departureDate}"/></td>
					<td>${tadaTdReqJourneyList.fromPlace}</td>
					<td>${tadaTdReqJourneyList.toPlace}</td>
					<td>${tadaTdReqJourneyList.noOfDays}</td>
					<td>${tadaTdReqJourneyList.conveyanceMode}</td>
					<td>${tadaTdReqJourneyList.classOfEntitlement}</td>
					<td><c:if test="${tadaTdReqJourneyList.tatkalQuota=='na'}">N/A</c:if><c:if test="${tadaTdReqJourneyList.tatkalQuota=='yes'}">Required</c:if></td>
					<td>${tadaTdReqJourneyList.ticketFare}</td>
					<td>
					<c:choose>
					<c:when test="${(workflowmap.requesterSfid ne workflowmap.sfid || workflowmap.roleInstanceName eq 'TADA TASK HOLDER' || workflowmap.roleInstanceName eq 'TA DA /Medical Section Head') && (workflowmap.checkStage eq 'previous' || workflowmap.checkStage eq 'last') && (workflowmap.message eq 'PENDING' || workflowmap.message eq 'pending')}">
					<c:choose>
					  <c:when test="${(tadaTdReqJourneyList.ticketFareAftRes!=0 && tadaTdReqJourneyList.ticketFareAftRes!='')}">
					      <input type="text" id="${tadaTdReqJourneyList.id}" value="${tadaTdReqJourneyList.ticketFareAftRes}" style="text-align: right;width: 170px" onkeypress="javascript:return checkFloat(event,'${tadaTdReqJourneyList.id}');" onkeyup="javascript:showIssuedTdAdvAmt();" />
					  </c:when>
					  <c:otherwise>
					      <input type="text" id="${tadaTdReqJourneyList.id}" style="text-align: right;width: 170px" onkeypress="javascript:return checkFloat(event,'${tadaTdReqJourneyList.id}');" onkeyup="javascript:showIssuedTdAdvAmt();" />
					  </c:otherwise>
					</c:choose>  
					</c:when>
					<c:otherwise>
					  ${tadaTdReqJourneyList.ticketFareAftRes}
					</c:otherwise>
					</c:choose>
					</td>
					
					
				</tr>
			</c:forEach>
			
		</table> 
	</div>
	<c:if test="${(workflowmap.requesterSfid ne workflowmap.sfid || workflowmap.roleInstanceName eq 'TADA TASK HOLDER' || workflowmap.roleInstanceName eq 'TA DA /Medical Section Head') && (workflowmap.checkStage eq 'previous' || workflowmap.checkStage eq 'last') && (workflowmap.message eq 'PENDING' || workflowmap.message eq 'pending')}">
	<div class="line">
	    <div class="quarter bold">&nbsp;</div>
		<div class="quarter">&nbsp;</div>
		<div class="quarter bold">Total Amount</div>
		<div class="quarter">: 
		<form:input path="totalAdvAmount" id="totalAdvAmount" readonly="true"  /></div>
	</div>
	</c:if>
	<div class="line">
		<div class="quarter bold">User Desired Advance Amount</div>
		<div class="quarter" id="tadaAdvanceAmount">: <font size="4.5em"><span class="WebRupee" >R</span></font>${workflowmap.tadaTdAdvanceRequestDTO.tadaAdvanceAmount}/-</div>
		<div class="quarter bold">Finance Processed Advance Amount</div>
		<c:choose>
		<c:when test="${(workflowmap.requesterSfid ne workflowmap.sfid || workflowmap.roleInstanceName eq 'TADA TASK HOLDER' || workflowmap.roleInstanceName eq 'TA DA /Medical Section Head') && (workflowmap.checkStage eq 'previous' || workflowmap.checkStage eq 'last') && (workflowmap.message eq 'PENDING' || workflowmap.message eq 'pending')}">
		<div class="quarter">: 
		<form:input path="issuedAdvAmount" id="issuedAdvAmount" readonly="true"  /></div>
		</c:when>
		<c:otherwise>
		: <font size="4.5em"><span class="WebRupee" >R</span></font>${workflowmap.tadaTdAdvanceRequestDTO.advanceAmountAftRes}/-
		</c:otherwise>
		</c:choose>
	</div>
	<c:choose>
	<c:when test="${workflowmap.tadaApprovalRequestDTO.advanceFlag=='C'}">
	<div class="line">
		<div class="quarter bold">Final Issued Advanced Amount</div>
		<div class="quarter" id="tadaAdvanceAmount">: <font size="4.5em"><span class="WebRupee" >R</span></font>${workflowmap.tadaTdAdvanceRequestDTO.cdaAmount}/-</div>
	</div>
	</c:when>
	<c:when test="${workflowmap.tadaApprovalRequestDTO.advanceFlag=='Y'}">
	<div class="line">
		<div class="quarter bold">Final Issued Advanced Amount</div>
		<div class="quarter" id="tadaAdvanceAmount">: <span style="color:red">Not yet issued.</span></div>
	</div>
	</c:when>
	</c:choose>
	<div class="line">
	    <div class="quarter bold">TADA TD Application Form:</div>
		<div class="quarter">:&nbsp;<a href="javascript:getTdInitialReport('${workflowmap.tadaTdAdvanceRequestDTO.referenceRequestID}','tadaTdApproval')">View</a></div>
	    <div class="quarter bold">TADA TD Advance Form:</div>
	    <c:if test="${workflowmap.tadaTdAdvanceRequestDTO.status==8 || workflowmap.tadaTdAdvanceRequestDTO.status==60}">
	    <c:if test="${workflowmap.tadaTdAdvanceRequestDTO.daType=='asl'}">
		  <div class="quarter">:&nbsp;<a href="javascript:getTdInitialReport('${workflowmap.requestId}','tadaTdAdvanceFin')">Print</a></div>
		</c:if>
		<c:if test="${workflowmap.tadaTdAdvanceRequestDTO.daType!='asl'}">
		  <div class="quarter">:&nbsp;<a href="javascript:getTdInitialReport('${workflowmap.requestId}','tadaTdAdvanceCda')">Print</a></div>
		</c:if>
		</c:if>
		<c:if test="${workflowmap.tadaTdAdvanceRequestDTO.status!=8 && workflowmap.tadaTdAdvanceRequestDTO.status!=60}">
		 <div class="quarter">:&nbsp;<b><span style="color:red">Don't forget to print & Sign report after finance approval from here</span></b></div>
		</c:if>
	</div>
	
	
	
<script type="text/javascript">
 showIssuedTdAdvAmt();
</script>
	
</div>

<!-- End : TadaTdAdvanceDetails.jsp -->