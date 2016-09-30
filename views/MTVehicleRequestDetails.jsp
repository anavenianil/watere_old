		<!-- begin:MTVehicleRequestDetails.jsp  -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="net.sf.json.JSONArray"%>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="script/mt.js"></script>
<div class="line">
										<div class="quarter leftmar">MT RequestForm</div>
										<div class="quarter" style="width: 50%;"><a href="javascript:mtRequestFormPrint('${workflowmap.requestId}')">Print</a><font color="red"> (Not required to submit hard copy)</font></div>
					</div>
					
<div class="line">
	<table class="fortable" width="100%">
		<tr>
			<td class="tabletd" align="center">Purpose Of Visit</td>
			<td class="tabletd" align="center">Onward Journey Details</td>
			<c:if test="${workflowmap.mtRequester.vehicleRequiredFlag eq 1}">
				<td class="tabletd" align="center">Return Journey Details</td>
			</c:if>
			
		</tr>
		<tr>
			<td align="center">${workflowmap.mtRequester.purposeOfVisit}</td>
			<td style="text-align:center"><a href="javascript:showOnwardPassEngDetails();"><span id="hideOnwardPassEngDiv" style="display:none">hide</span><span id="showOnwardPassEngDiv">show</span></a></td>
			<c:if test="${workflowmap.mtRequester.vehicleRequiredFlag eq 1}">
				<td style="text-align:center"><a href="javascript:showReturnPassEngDetails();"><span id="hideReturnPassEngDiv" style="display:none">hide</span><span id="showReturnPassEngDiv">show</span></a></td>
			</c:if>
		</tr>
	</table>
</div>
<div  class="line" id="onwardJourneyDetailsID" style="display:none">
									<fieldset><legend><strong><font color="green">Onward Journey Details </font></strong></legend>
										<div class="line">
											<table class="fortable" id="journeyDetailsID" width="100%" border="2px" cellpadding="0px" cellspacing="0px" align="center">
												<tr>
													<td rowspan="3" class="tabletd" align="center"><font size="-3px;">No of People</font></td>
													<td rowspan="3" class="tabletd" align="center">Name With Designation</td>
													<td colspan="4" class="tabletd" align="center" >Departure/Arrival</td>
													<td rowspan="2" colspan="2" class="tabletd" align="center">Pickup Place</td>
													<td rowspan="2" colspan="2" class="tabletd" align="center">Dropping Place</td>
													<td rowspan="3" class="tabletd" align="center">Accom Place</td>
													<td rowspan="3" class="tabletd" align="center">Article Carry</td>
													<td rowspan="3" class="tabletd" align="center">Remarks (Mobile No/ Flight No)</td>
												</tr>
												<tr>
													<td colspan="2" class="tabletd" align="center">Travellig</td>
													<td colspan="2" class="tabletd" align="center">Estimated</td>
												</tr>
												<tr>
													<td class="tabletd" align="center">Date</td>
													<td class="tabletd" align="center">Time</td>
													<td class="tabletd" align="center">Date</td>
													<td class="tabletd" align="center">Time</td>
													<td class="tabletd" align="center">Pickup</td>
													<td class="tabletd" align="center">Other Source</td>
													<td class="tabletd" align="center">Drop</td>
													<td class="tabletd" align="center">Other Destin</td>
												</tr>
												<c:forEach var="journey" items="${workflowmap.mtRequester.mtJourneyDetails}">
													<c:if test="${journey.journeyTypeFlag eq 'ONWARD'}">
													<tr>
														<td align="center">${journey.noOfPeople}</td>
														<td align="center">${journey.nameWithDesignation}</td>
														<td align="center"><fmt:formatDate value="${journey.departureDate}" pattern="dd-MMM-yyyy"/></td>
														<td align="center">${journey.departureTime}</td>
														<td align="center"><fmt:formatDate value="${journey.estimatedDate}" pattern="dd-MMM-yyyy"/>	</td>
														<td align="center">${journey.estimatedTime}</td>
														<td align="center">${journey.mtPickAddressDetails.addressName}</td>
														<td align="center">${journey.pickupPlace}</td>
														<td align="center">${journey.mtDropAddressDetails.addressName}</td>
														<td align="center">${journey.dropPlace}</td>
														<c:if test="${journey.accommReqFlag eq 1}">
														<td align="center">${journey.accommPlace}</td>
														</c:if>
														<c:if test="${journey.accommReqFlag eq 0}">
														<td align="center">**NO**</td>
														</c:if>
														<c:if test="${journey.articleCarryFlag eq 1}">
														<td align="center"><a href="javascript:showOnwardArticleDetails('${journey.id}');"><span id="hideOnwardArticleDiv${journey.id}" style="display:none">hide</span><span id="showOnwardArticleDiv${journey.id}">show</span></a></td>
														</c:if>
														<c:if test="${journey.articleCarryFlag eq 0}">
														<td align="center">**NO**</td>
														</c:if>
														<td align="center">${journey.remarks}</td>
														<c:if test="${journey.status eq 1}">
														<script>complJCount++;</script>
														</c:if>
													</tr>
													
													</c:if>
												</c:forEach>
												
										</table>		
										</div>

<div class=line>
<table class=fortable width=100% border=2px align=center>
  <div class=line align="center" style="color: green;">Vehicle Allocation Details</div>
	<tr>
		<td class=tabletd align=center>Vehicle Allocated For</td>
		<td class=tabletd align=center>Allocated Vehicle No,Model</td>
	    <td class=tabletd align=center>Vehicle Name</td>
	    
		<%-- <td class=tabletd align=center>Allocated Driver Name</td>
		<td class=tabletd align=center>Mobile No</td>--%>
		<c:if test="${fn:length(workflowmap.mtRequester.mtJourneyDetails) gt '1'}">
		<td class=tabletd align=center style="width: 5%;">Cancel</td>
		</c:if>
	</tr>
	<c:forEach var="journey"
		items="${workflowmap.mtRequester.mtJourneyDetails}">
		<c:if test="${journey.journeyTypeFlag eq 'ONWARD'}">
		<tr>
		<td align=center>${journey.nameWithDesignation}</td>
		<td align=center>
		<c:if test="${journey.mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.vehicleDetails.vehicleNo ne null}">
		${journey.mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.vehicleDetails.vehicleNo},
		${journey.mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.vehicleDetails.modelMasterDetails.modelName}
		</c:if>
		</td>
		<c:if test="${journey.mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.vehicleDetails.vehicleType eq 2 }">
		<td align=center>${journey.mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.vehicleDetails.vehicleName},
	   <br/><font color="red">Travel Agency Details:</font>
		${journey.mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.vehicleDetails.vehicleTravelAgencyDetails.travelAgencyName},
		${journey.mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.vehicleDetails.vehicleTravelAgencyDetails.travelMobileNo}
		</td>
		</c:if>
		<c:if test="${journey.mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.vehicleDetails.vehicleType ne 2 }">
		<td align=center>${journey.mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.vehicleDetails.vehicleName}
		</td>
		</c:if>
		<%-- <td align=center>${journey.mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.diverDetails.driverName}</td>
		<td align=center>${journey.mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.diverDetails.driverMobileNo}</td>--%>
		<c:if test="${journey.status ne 9 && workflowmap.mtRequester.status ne 9 && journey.mtVehicleAllocationtDetailsDTO[0].statusFlag ne 8 && fn:length(workflowmap.mtRequester.mtJourneyDetails) gt '1' && workflowmap.workflowStageID ne '4'}">
		<td align=center><div class="expbutton"><a onclick="javascript:userJourneyCancel('${journey.id}',${workflowmap.requestId});"><span>Cancel</a></span></div></td>
		</c:if>
		<c:if test="${journey.status eq 9 || workflowmap.mtRequester.status eq 9}">
		<td align=center>CANCELLED</td>
		</c:if>
</tr>
</c:if>
	</c:forEach>
</table>
</div>
</fieldset>
								
	<div  class="line" id="onwardArticleDetailsID" style="display:none;"></div>
</div>								

								
<div  class="line" id="returnJourneyDetailsID" style="display:none;">
									<fieldset><legend><strong><font color="green">Return Journey Details </font></strong></legend>
										<div class="line">
											<table class="fortable" id="RjourneyDetailsID" width="100%" border="2px" cellpadding="0px" cellspacing="0px" align="center">
												<tr>
													<td rowspan="3" class="tabletd" align="center"><font size="-3px;">No of People</font></td>
													<td rowspan="3" class="tabletd" align="center">Name With Designation</td>
													<td colspan="4" class="tabletd" align="center" >Departure/Arrival</td>
													<td rowspan="2" colspan="2" class="tabletd" align="center">Pickup Place</td>
													<td rowspan="2" colspan="2" class="tabletd" align="center">Dropping Place</td>
													<td rowspan="3" class="tabletd" align="center">Accom Place</td>
													<td rowspan="3" class="tabletd" align="center">Article Carry</td>
													<td rowspan="3" class="tabletd" align="center">Remarks (Mobile No/ Flight No)</td>
												</tr>
												<tr>
													<td colspan="2" class="tabletd" align="center">Travellig</td>
													<td colspan="2" class="tabletd" align="center">Estimated</td>
												</tr>
												<tr>
													<td class="tabletd" align="center">Date</td>
													<td class="tabletd" align="center">Time</td>
													<td class="tabletd" align="center">Date</td>
													<td class="tabletd" align="center">Time</td>
													<td class="tabletd" align="center">Pickup</td>
													<td class="tabletd" align="center">Other Source</td>
													<td class="tabletd" align="center">Drop</td>
													<td class="tabletd" align="center">Other Destin</td>
												</tr>
												<c:forEach var="Rjourney" items="${workflowmap.mtRequester.mtJourneyDetails}">
													<c:if test="${Rjourney.journeyTypeFlag eq 'RETURN'}">
													<tr>
														<td align="center">${Rjourney.noOfPeople}</td>
														<td align="center">${Rjourney.nameWithDesignation}</td>
														<td align="center"><fmt:formatDate value="${Rjourney.departureDate}" pattern="dd-MMM-yyyy"/></td>
														<td align="center">${Rjourney.departureTime}</td>
														<td align="center"><fmt:formatDate value="${Rjourney.estimatedDate}" pattern="dd-MMM-yyyy"/></td>
														<td align="center">${Rjourney.estimatedTime}</td>
														<td align="center">${Rjourney.mtPickAddressDetails.addressName}</td>
														<td align="center">${Rjourney.pickupPlace}</td>
														<td align="center">${Rjourney.mtDropAddressDetails.addressName}</td>
														<td align="center">${Rjourney.dropPlace}</td>
														<c:if test="${Rjourney.accommReqFlag eq 1}">
														<td align="center">${Rjourney.accommPlace}</td>
														</c:if>
														<c:if test="${Rjourney.accommReqFlag eq 0}">
														<td align="center">**NO**</td>
														</c:if>
														<c:if test="${Rjourney.articleCarryFlag eq 1}">
														<td align="center"><a href="javascript:showReturnArticleDetails('${Rjourney.id}');"><span id="hideReturnArticleDiv${Rjourney.id}" style="display:none">hide</span><span id="showReturnArticleDiv${Rjourney.id}">show</span></a></td>
														</c:if>
														<c:if test="${Rjourney.articleCarryFlag eq 0}">
														<td align="center">**NO**</td>
														</c:if>
														<td align="center">${Rjourney.remarks}</td>
														<c:if test="${journey.status eq 1}">
														<script>complJCount++;</script>
														</c:if>
													</tr>
													
													</c:if>
												</c:forEach>
												
										</table>		
										</div>
<div class=line>
<table class=fortable width=100% border=2px align=center>
	<div class=line align="center" style="color: green;">Vehicle
	Allocation Details</div>
	<tr>
		<td class=tabletd align=center>Vehicle Allocated For</td>
		<td class=tabletd align=center>Allocated Vehicle No,Model</td>
		<td class=tabletd align=center>Vehicle Name</td>
		<%-- <td class=tabletd align=center>Allocated Driver Name</td>
		<td class=tabletd align=center>Mobile No</td>--%>
		<c:if test="${fn:length(workflowmap.mtRequester.mtJourneyDetails) gt '1'}">
		<td class=tabletd align=center style="width: 5%;">Cancel</td>
		</c:if>
	</tr>
	<c:forEach var="Rjourney"
		items="${workflowmap.mtRequester.mtJourneyDetails}">
		<c:if test="${Rjourney.journeyTypeFlag eq 'RETURN'}">
		<tr>
			<td align=center>${Rjourney.nameWithDesignation}</td>
			<td align=center>${Rjourney.mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.vehicleDetails.vehicleNo},
			${Rjourney.mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.vehicleDetails.modelMasterDetails.modelName}
			</td>
			<c:if test="${journey.mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.vehicleDetails.vehicleType eq 2 }">
			<td align=center>${Rjourney.mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.vehicleDetails.vehicleName},
			<br/><font color="red;">Travel Agency Details:</font>
			${journey.mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.vehicleDetails.vehicleTravelAgencyDetails.travelAgencyName},
		   ${journey.mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.vehicleDetails.vehicleTravelAgencyDetails.travelMobileNo}
			</td>
			</c:if>
			<c:if test="${journey.mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.vehicleDetails.vehicleType ne 2 }">
			<td align=center>${Rjourney.mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.vehicleDetails.vehicleName}
			</c:if>
			<%-- <td align=center>${Rjourney.mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.diverDetails.driverName}</td>
			<td align=center>${Rjourney.mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.diverDetails.driverMobileNo}</td>--%>
			
			<c:if test="${Rjourney.status ne 9 && workflowmap.mtRequester.status ne 9 && journey.mtVehicleAllocationtDetailsDTO[0].statusFlag ne 8 && fn:length(workflowmap.mtRequester.mtJourneyDetails) gt '1' && workflowmap.workflowStageID ne '4'}">
		<td align=center><div class="expbutton"><a onclick="javascript:userJourneyCancel('${Rjourney.id}');"><span>Cancel</a></span></div></td>
		</c:if>
		<c:if test="${Rjourney.status eq 9 || workflowmap.mtRequester.status eq 9}">
		<td align=center>CANCELLED</td>
		</c:if>
		
			
		</tr>
		</c:if>
	</c:forEach>
</table>
</div>
</fieldset>
	<div  class="line" id="returnArticleDetailsID" style="display:none;"></div>
</div>

<script type="text/javascript">
var requesterJSON = <%=session.getAttribute("MTRequestJSON")%>;
</script>								
								
<%--<div>
<div class="line">
	<table width="100%" cellpadding="2" cellspacing="0" border="1" class="sub_2">
		<tr>
			 <th width="">Requested For</th>
			 <th width="">No Of People</th>
			 <th width="">Purpose Of Visit</th>
			 <th width="">Traveling Date & Time </th>
			 <th width="">Estimated Date & Time </th>
			 <th width="">Pickup Point</th>
			 <th width="">Dropping Point</th>
			   <c:if test="${workflowmap.mtRequester.accommodationRequired eq 1}">
				<th width="">Acc Place</th>
			  </c:if>
			  <c:if test="${workflowmap.mtRequester.articleCarry eq 1}">
			 	<th width="">Article Details</th>
			  </c:if>
			   <c:if test="${workflowmap.mtRequester.returnVehicleRequired eq 1}">
			 	<th width="">Return Journey Details</th>
			  </c:if>
			   <c:if test="${workflowmap.mtRequester.returnArticleCarry eq 1}">
				<th width="">Return Article Details</th>
			  </c:if>
			  
			 <c:if test="${workflowmap.checkStage eq 'last' || workflowmap.lastStagePendingCheck eq 'lcheck'}">
			 <th width="">Vehicle No</th>
			 <th width="">Driver Name</th>
			 	 <c:if test="${workflowmap.mtRequester.returnVehicleRequired eq 1}">
			 	 	 <th width="">Return Vehicle No</th>
			 		 <th width="">Return Driver Name</th>
			 	 </c:if>
			 </c:if>
			 <c:if test="${workflowmap.checkStage eq 'last' && workflowmap.message eq 'pending'|| workflowmap.message eq 'PENDING'}">
			
			 <th width="">A</th>
			 </c:if>
		</tr>
		<tr>
			<td style="text-align:center">${workflowmap.mtRequester.requestedFor}</td>
			<td style="text-align:center">${workflowmap.mtRequester.noOfPeople}</td>
			<td style="text-align:center">${workflowmap.mtRequester.purposeOfVisiting}</td>
			<td style="text-align:center"><fmt:formatDate value="${workflowmap.mtRequester.travellingDateTime}" pattern="dd-MMM-yyyy HH-mm"/></td>
			<td style="text-align:center"><fmt:formatDate value="${workflowmap.mtRequester.estimatedDateTime}" pattern="dd-MMM-yyyy HH-mm"/></td>
			<td style="text-align:center">${workflowmap.mtRequester.pickupAddrDetails.addressName}/${workflowmap.mtRequester.otherSourcePlace}</td>
			<td style="text-align:center">${workflowmap.mtRequester.dropAddrDetails.addressName}/${workflowmap.mtRequester.otherDestinationPlace}</td>
				   <c:if test="${workflowmap.mtRequester.accommodationRequired eq 1}">
					<td style="text-align:center">${workflowmap.mtRequester.accommodationPlace}</td>
			 	  </c:if>
				  <c:if test="${workflowmap.mtRequester.articleCarry eq 1}">
				 	<td style="text-align:center"><a href="javascript:showArticleDetails();"><span id="hideArticleDiv" style="display:none">hide</span><span id="showArticleDiv">show</span></a></td>
				  </c:if>
			   <c:if test="${workflowmap.mtRequester.returnVehicleRequired eq 1}">
			 	<td style="text-align:center"><a href="javascript:showReturnJourneyDetails();"><span id="hideReturnJourneyDiv" style="display:none">hide</span><span id="showReturnJourneyDiv">show</span></a></td>
			  </c:if>
			   <c:if test="${workflowmap.mtRequester.returnArticleCarry eq 1}">
				<td style="text-align:center"><a href="javascript:showReturnArticleDetails();"><span id="hideReturnArticleDiv" style="display:none">hide</span><span id="showReturnArticleDiv">show</span></a></td>
			  </c:if>
			 <c:if test="${workflowmap.checkStage eq 'last' || workflowmap.lastStagePendingCheck eq 'lcheck'}">
			<td style="text-align:center"><input id="vehicleNoId" readonly="true" value="${workflowmap.mtRequester.vehicleNo}" size="4%" /></td>
			<td style="text-align:center"><input id="driverNameId" readonly="true" value="${workflowmap.mtRequester.driverName}" size="4%" /></td>
				<c:if test="${workflowmap.mtRequester.returnVehicleRequired eq 1}">
						<c:if test="${workflowmap.mtRequester.rVehicleNo eq null}">
							<td style="text-align:center"><input id="returnVehicleNoId" readonly="true" value="${workflowmap.mtRequester.vehicleNo}" size="4%" /></td>
							<td style="text-align:center"><input id="returnDriverNameId" readonly="true" value="${workflowmap.mtRequester.driverName}" size="4%" /></td>
						</c:if>
						<c:if test="${workflowmap.mtRequester.rVehicleNo ne null}">
							<td style="text-align:center"><input id="returnVehicleNoId" readonly="true" value="${workflowmap.mtRequester.rVehicleNo}" size="4%" /></td>
							<td style="text-align:center"><input id="returnDriverNameId" readonly="true" value="${workflowmap.mtRequester.rDriverName}" size="4%" /></td>
						</c:if>
				</c:if>
			</c:if>
			<c:if test="${workflowmap.checkStage eq 'last' && workflowmap.message eq 'pending' || workflowmap.message eq 'PENDING'}">
			<td style="text-align:center"><a href="javascript:showVehicleAllocationDiv();"><span id="showDiv"><b>A</b></span></a></td>
			</c:if>
		</tr>
	
	</table>
</div>
<div class="line" id="showArticleDetailsDiv" style="display:none;">
 <fieldset><legend><strong><font color='green'>Article Details</font></strong></legend>
 <table width="100%" cellpadding="3" cellspacing="0" border="1" class="sub_2">
		<tr>
			 <th width="">Article Type</th>
			 <th width="">Article Length</th>
			 <th width="">Article Breadth</th>
			 <th width="">Article Height</th>
			 <th width="">Article Weight</th>
			 <th width="">Article Quantity</th>
		</tr>
		<c:forEach items="${workflowmap.articleList}" var="article">
		<tr>
			<td style="text-align:center">${article.articleType}</td>
			<td style="text-align:center">${article.length}</td>
			<td style="text-align:center">${article.breadth}</td>
			<td style="text-align:center">${article.height}</td>
			<td style="text-align:center">${article.weight}</td>
			<td style="text-align:center">${article.quantity}</td>
		</tr>
		</c:forEach>
</table>
 </fieldset>
</div>
<div class="line" id="showReturnJourneyDetailsDiv" style="display:none;">
 <fieldset><legend><strong><font color='green'>Return Journey Details</font></strong></legend>
 <table width="100%" cellpadding="3" cellspacing="0" border="1" class="sub_2">
		<tr>
			 <th width="">No Of People</th>
			 <th width="">Traveling Date & Time</th>
			 <th width="">Estimated Date & Time</th>
			 <th width="">Pickup point</th>
			 <th width="">Dropping point</th>
			 <c:if test="${workflowmap.mtRequester.returnVehicleSame eq 1}">
			 <th width="">Same Vehicle Required</th>
			 </c:if>
		</tr>
		<c:forEach items="1" var="1">
		<tr>
			<td style="text-align:center">${workflowmap.mtRequester.returnNoOfPeople}</td>
			<td style="text-align:center"><fmt:formatDate value="${workflowmap.mtRequester.returnTravellingDateTime}" pattern="dd-MMM-yyyy HH-mm"/></td>
			<td style="text-align:center"><fmt:formatDate value="${workflowmap.mtRequester.returnEstimatedDateTime}" pattern="dd-MMM-yyyy HH-mm"/></td>
			<td style="text-align:center">${workflowmap.mtRequester.returnPickupPoint}/${workflowmap.mtRequester.returnOtherSourcePlace}</td>
			<td style="text-align:center">${workflowmap.mtRequester.returnDroppingPoint}/${workflowmap.mtRequester.returnOtherDestinationPlace}</td>
			<c:if test="${workflowmap.mtRequester.returnVehicleSame eq 1}">
			<td style="text-align:center">Yes</td>
			</c:if>
		</tr>
		</c:forEach>
</table>
 </fieldset>
</div>
<div class="line" id="showReturnArticleDetailsDiv" style="display:none;">
 <fieldset><legend><strong><font color='green'>Return Article Details</font></strong></legend>
 <table width="100%" cellpadding="3" cellspacing="0" border="1" class="sub_2">
		<tr>
			 <th width="">Article Type</th>
			 <th width="">Article Length</th>
			 <th width="">Article Breadth</th>
			 <th width="">Article Height</th>
			 <th width="">Article Weight</th>
			 <th width="">Article Quantity</th>
		</tr>
		<c:forEach items="${workflowmap.returnArticleList}" var="article">
		<tr>
			<td style="text-align:center">${article.articleType}</td>
			<td style="text-align:center">${article.length}</td>
			<td style="text-align:center">${article.breadth}</td>
			<td style="text-align:center">${article.height}</td>
			<td style="text-align:center">${article.weight}</td>
			<td style="text-align:center">${article.quantity}</td>
		</tr>
		</c:forEach>
</table>

 </fieldset>
</div>
<div class="line" id="vehicleAllocationDiv" style="display:none;">
		<div class="headTitle"><font color="green">Vehicle Allocation</font></div>
		<div class="line">
			<div class="quarter bold">Govt. Vehicles<span class="mandatory">*</span>&nbsp;&nbsp;:</div>
			<div class="quarter">
				 <form:select path="freeVehicleDriverMapId" id="freeVehicleDriverMapId" onchange="javascript:onselectBusyVehicle('freeVehicleDriverMapId','VehicleScheduleDetailsDiv'),onChangeVehicle(this.id);">
				 	<form:option value="0">Select</form:option>
					<form:options items="${workflowmap.vehicleDriverFreeList}" itemValue="id" itemLabel="vehicleNo"/>
				 </form:select>	
				 <div  id="VehicleScheduleDetailsDiv" style="float:right;display:none;" ></div>
			</div>
		</div>
		<!-- div class="line">
			<div class="quarter bold">Govt. Busy Vehicles<span class="mandatory">*</span>&nbsp;&nbsp;:</div>
			<div class="quarter">
				 <form:select path="busyVehicleDriverMapId" id="busyVehicleDriverMapId" onchange="javascript:onselectBusyVehicle('busyVehicleDriverMapId','VehicleScheduleDetailsDiv'),onChangeVehicle(this.id);">
				 	<form:option value="0">Select</form:option>
					<form:options items="${workflowmap.vehicleDriverBusyList}" itemValue="id" itemLabel="vehicleNo"/>
				 </form:select>
				 <div  id="VehicleScheduleDetailsDiv" style="float:right;display:none;" ></div>		
			</div>
			
		</div> -->
		<div class="line">
			<div class="quarter bold">Hired Vehicles<span class="mandatory">*</span>&nbsp;&nbsp;:</div>
			<div class="quarter">
				 <form:select path="hiredVehicleDriverMapId" id="hiredVehicleDriverMapId" onchange="javascript:onselectHiredVehicle();">
				 	<form:option value="0">Select</form:option>
					<form:options items="${workflowmap.hiredVehicleList}" itemValue="vehicleId" itemLabel="vehicleNoString"/>
				 </form:select>	
			</div>			
		</div>
		<c:if test="${workflowmap.mtRequester.returnVehicleRequired eq 1}">
			<div id="returnVehicleRequiredDiv">
				<div class="headTitle"><font color="green">Return Vehicle Allocation</font></div>
				<div class="line">
					<div class="quarter bold">Allocate same Vehicle<span class="mandatory">*</span>&nbsp;&nbsp;:</div>
					<div class="quarter bold">
						<input type="radio" name="returnVehicleType" id="returnVehicleType1" value="1" onclick="javascript:getReturnVehicleAllocationDiv('YES');" />Yes
						<input type="radio" name="returnVehicleType" id="returnVehicleType2" value="0" onclick="javascript:getReturnVehicleAllocationDiv('NO');"/>No
					</div>
				</div>
			</div>
			<div id="returnVehicleAllocationDiv" style="display:none;">
						<div class="line">
						<div class="quarter bold">Govt. Vehicles<span class="mandatory">*</span>&nbsp;&nbsp;:</div>
						<div class="quarter">
							 <form:select path="returnFreeVehicleDriverMapId" id="returnFreeVehicleDriverMapId" onchange="javascript:onselectBusyVehicle('returnFreeVehicleDriverMapId','returnVehicleScheduleDetailsDiv'),onChangeReturnVehicle(this.id);">
							 	<form:option value="0">Select</form:option>
								<form:options items="${workflowmap.vehicleDriverBusyList}" itemValue="id" itemLabel="vehicleNo"/>
							 </form:select>	
							  <div  id="returnVehicleScheduleDetailsDiv" style="float:right;display:none;" ></div>
						</div>
					</div>
					<!-- div class="line">
						<div class="quarter bold">Govt. Busy Vehicles<span class="mandatory">*</span>&nbsp;&nbsp;:</div>
						<div class="quarter">
							 <form:select path="returnBusyVehicleDriverMapId" id="returnBusyVehicleDriverMapId" onchange="javascript:onselectBusyVehicle('returnBusyVehicleDriverMapId','returnVehicleScheduleDetailsDiv'),onChangeReturnVehicle(this.id);">
							 	<form:option value="0">Select</form:option>
								<form:options items="${workflowmap.vehicleDriverBusyList}" itemValue="id" itemLabel="vehicleNo"/>
							 </form:select>
							 <div  id="returnVehicleScheduleDetailsDiv" style="float:right;display:none;" ></div>		
						</div>
						
					</div> -->
					<div class="line">
						<div class="quarter bold">Hired Vehicles<span class="mandatory">*</span>&nbsp;&nbsp;:</div>
						<div class="quarter">
							 <form:select path="returnHiredVehicleDriverMapId" id="returnHiredVehicleDriverMapId" onchange="javascript:onselectReturnHiredVehicle();">
							 	<form:option value="0">Select</form:option>
								<form:options items="${workflowmap.hiredVehicleList}" itemValue="vehicleId" itemLabel="vehicleNoString"/>
							 </form:select>	
						</div>			
					</div>
			</div>
		</c:if>	
</div>
</div>
<form:hidden path="vehicleDriverMapId" id="vehicleDriverMapId" />
<form:hidden path="returnVehicleDriverMapId" id="returnVehicleDriverMapId" />	

<script type="text/javascript">
var vehicleDriverJson = <%=(net.sf.json.JSONArray)session.getAttribute("MTVehicleDriverJson")%>;
var vehicleNoJson = <%=(net.sf.json.JSONArray)session.getAttribute("MTVehicleNoJson")%>;
var vehicleHiredJson = <%=(net.sf.json.JSONArray)session.getAttribute("MTVehicleHiredJson")%>;
var returnVehicleJson = <%=(net.sf.json.JSONArray)session.getAttribute("ReturnVehicleJSON")%>;
</script> --%>
	<!-- End:MTVehicleRequestDetails.jsp  -->