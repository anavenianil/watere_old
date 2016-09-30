<!-- Begin : TadaTdSettlementDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<script type="text/javascript" src="script/tada.js"></script>
<link href="styles/jquery-ui-1.8.16.custom.css" rel="stylesheet" type="text/css" />

<div id="result6">

<div id="kdivNew">
<div id="kdiv" style="display:none;">

	<c:if test="${workflowmap.tdDaNewFoodDetailsList!='[]' && workflowmap.tdDaNewFoodDetailsList!=null}">
	<div class="headTitle">Food Details</div>
	 <div class="line">
		<table BORDER="1" cellpadding="2" cellspacing="0" id="daNewFoodDetailsId" style="width:100%" class="sub_2" >
			<tr>
				<td rowspan="1" class="tabletd" align="center" >From Date</td>
				<td rowspan="1" class="tabletd" align="center" >To Date</td>
				<td rowspan="1" class="tabletd" align="center">Amount for Food in <font size="4.5em"><span class="WebRupee" >R</span></font></td>
				<td rowspan="1" class="tabletd" align="center" ><font color="blue">Amount for Food after Res. in <font size="4.5em"><span class="WebRupee" >R</span></font></font></td>
				<td rowspan="1" class="tabletd" align="center">Claimed Amount in <font size="4.5em"><span class="WebRupee" >R</span></font></td>
				 <td rowspan="1" class="tabletd" align="center" >Add</td>
				 <td rowspan="1" class="tabletd" align="center" >Del</td>
			</tr>
			<%int l=0; %>
			<c:forEach var="tdDaNewFoodDetailsList" items="${workflowmap.tdDaNewFoodDetailsList}">
				<tr id="tdDaNewFoodRow<%=l %>">   
				       <td><input type="hidden" value="${tdDaNewFoodDetailsList.id}" /><input type="text" size="10px" id="foodFromDate<%=l %>" value="<fmt:formatDate pattern="dd-MMM-yyyy" value="${tdDaNewFoodDetailsList.fromDate}" />" onfocus ="javascript:Calendar.setup({inputField :'foodFromDate<%=l %>',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});"/></td>
				       <td><input type="text" size="10px" id="foodToDate<%=l %>" value="<fmt:formatDate pattern="dd-MMM-yyyy" value="${tdDaNewFoodDetailsList.toDate}" />" onfocus ="javascript:Calendar.setup({inputField :'foodToDate<%=l %>',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});"/></td>
				       <td><input type="text" size="30px" id="foodAmount<%=l %>" style="text-align: right;" value="${tdDaNewFoodDetailsList.foodAmount}" onkeypress="javascript:return checkFloat(event,'foodAmount<%=l %>');" /></td>
				       <td><input type="text" size="35px" id="foodAmountAftRes<%=l %>" style="background-color: pink;text-align: right;" value="${tdDaNewFoodDetailsList.foodAmountAftRes}" onkeypress="javascript:return checkFloat(event,'foodAmountAftRes<%=l %>');" onchange="showFoodAmtDayRepresentation('${workflowmap.requestId}','${workflowmap.requesterSfid}','${workflowmap.roleInstanceName}');" /></td>
				       <td><input type="text" size="25px" id="claimedFoodAmount<%=l %>" style="text-align: right;" value="${tdDaNewFoodDetailsList.claimedAmount}" onkeypress="javascript:return checkFloat(event,'claimedFoodAmount<%=l %>');" /></td>
				       <td><input type="button" id="add0" value="+" onclick="javascript:checkFinDANewFoodRow(<%=l %>);"/></td>
					   <td><input type="button" id="del0" value="-" onclick="javascript:deleteFinDANewFoodRow(this,'daNewFoodDetailsId');" /></td>
				    
				</tr>
			<% l++; %>
			</c:forEach>
		</table> 
	</div>
	</c:if>
	<a id="foodDayRepID" href="javascript:foodAmtDayRepresentation('${workflowmap.requestId}','${workflowmap.requesterSfid}','${workflowmap.roleInstanceName}');showTotalFoodAmount();individualTotAmount('all');">Regenerate Day Representation For Food</a>
</div>
</div>

<c:if test="${workflowmap.daNewFoodDayRepList!='[]' && workflowmap.daNewFoodDayRepList!=null}">
	<div class="headTitle">Food Details-Day Representation</div>
	<div class="line">
		<table BORDER="1" cellpadding="2" cellspacing="0" id="daNewFoodDayDetailsId" style="width:100%">
			<tr>
				<td rowspan="1" class="tabletd" align="center" >Date</td>
				<td rowspan="1" class="tabletd" align="center" >Amount</td>
			</tr>
			<%int u=0; %>
			<c:forEach var="daNewFoodDayRepList" items="${workflowmap.daNewFoodDayRepList}">
				<tr>
				   
				    <td><input type="hidden" value="${daNewFoodDayRepList.id}" /><input type="text" id="repDate<%=u %>" size="70px" value="${daNewFoodDayRepList.representationDateOne}" onfocus ="javascript:Calendar.setup({inputField :'repDate<%=u %>',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" /></td>
					  <td><input type="text" id="repFoodAmount<%=u %>" size="70px" style="text-align: right;" value="${daNewFoodDayRepList.representationAmount1}" onkeypress="javascript:return checkFloat(event,'repFoodAmount<%=u %>')" onkeyup="javascript:individualTotAmount('all');" /></td>
				</tr>
			<% u++; %>
			</c:forEach>
			<tr>
				<td rowspan="1" class="tabletd" align="center" ><b>Food Total(D)</b></td>
				<td rowspan="1" class="tabletd" align="right" >
				<c:choose>
				    <c:when test="${(workflowmap.roleInstanceName eq 'TADA TASK HOLDER' || workflowmap.roleInstanceName eq 'TA DA /Medical Section Head') && workflowmap.lastStagePendingCheck!='completed'}">
				      <input type="text" id="totalFoodAmount" name="totalFoodAmount" style="text-align: right" readonly="readonly" value="${workflowmap.tdTotalFoodDetails.totalClaimedFoodAmount}"  maxlength="30"/>
				    </c:when>
				    <c:otherwise>
				    <input type="text" id="totalFoodAmount" name="totalFoodAmount" style="text-align: right" readonly="readonly" value="${workflowmap.tdTotalFoodDetails.totalClaimedFoodAmount}"  maxlength="30"/>
				    </c:otherwise>
				</c:choose>
				</td>
			</tr>
			
		</table> 
	</div>
	<!-- <div class="headTitle">Claimed Amount is not Graterthen  TotalFoodAmount</div> -->
</c:if>
</div>
	
