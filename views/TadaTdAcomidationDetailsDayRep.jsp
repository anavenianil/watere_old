<!-- Begin : TadaTdSettlementDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<script type="text/javascript" src="script/tada.js"></script>
<link href="styles/jquery-ui-1.8.16.custom.css" rel="stylesheet" type="text/css" />

<div id="result7">
<div id="kdivNew1">
<div id="kdiv1" style="display:none;">

	<c:if test="${workflowmap.tdDaNewAccDetailsList!='[]' && workflowmap.tdDaNewAccDetailsList!=null}">
	<div class="headTitle">Accommodation Details</div>
	
	<div class="line">
		<table BORDER="1" cellpadding="2" cellspacing="0" id="daNewAccDetailsId" style="width:100%" class="sub_2">
			<tr>
				<td rowspan="1" class="tabletd" align="center" >From Date</td>
				<td rowspan="1" class="tabletd" align="center" ></td>
				<td rowspan="1" class="tabletd" align="center" >To Date</td>
				<td rowspan="1" class="tabletd" align="center" ></td>
				<td rowspan="1" class="tabletd" align="center">Amount for Accommodation in <font size="4.5em"><span class="WebRupee" >R</span></font></td>
				<td rowspan="1" class="tabletd" align="center" ><font color="blue">Amount for Acc after Res. in <font size="4.5em"><span class="WebRupee" >R</span></font></font></td>
				<td rowspan="1" class="tabletd" align="center">Claimed Amount in <font size="4.5em"><span class="WebRupee" >R</span></font></td>
				<td rowspan="1" class="tabletd" align="center" ><font color="blue">Claimed Amount after Res. in <font size="4.5em"><span class="WebRupee" >R</span></font>(C)</font></td>
				<c:if test="${(workflowmap.roleInstanceName eq 'TADA TASK HOLDER' || workflowmap.roleInstanceName eq 'TA DA /Medical Section Head') && (workflowmap.checkStage eq 'previous' || workflowmap.checkStage eq 'last') && (workflowmap.message eq 'PENDING' || workflowmap.message eq 'pending')}">
				 <td rowspan="1" class="tabletd" align="center" >Add</td>
				 <td rowspan="1" class="tabletd" align="center" >Del</td>
				</c:if>
			</tr>
			<%int k=0; %>
			<c:forEach var="tdDaNewAccDetailsList" items="${workflowmap.tdDaNewAccDetailsList}">
				<tr id="tdDaNewAccRow<%=k %>">
				    
				       <td><input type="hidden" value="${tdDaNewAccDetailsList.id}" /><input type="text" size="10px" id="accFromDate<%=k %>" value="<fmt:formatDate pattern="dd-MMM-yyyy" value="${tdDaNewAccDetailsList.fromDate}" />" onfocus ="javascript:Calendar.setup({inputField :'accFromDate<%=k %>',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" onchange="javascript:checkNewFoodDate(this);"/></td>
				       <td></td>
				       <td><input type="text" size="10px" id="accToDate<%=k %>" value="<fmt:formatDate pattern="dd-MMM-yyyy" value="${tdDaNewAccDetailsList.toDate}" />" onfocus ="javascript:Calendar.setup({inputField :'accToDate<%=k %>',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" onchange="javascript:checkNewFoodDate(this);" /></td>
				       <td></td>
				       <td><input type="text" size="20px" id="accAmount<%=k %>" style="text-align: right;" value="${tdDaNewAccDetailsList.accAmount}" onkeypress="javascript:return checkFloat(event,'accAmount<%=k %>');" /></td>
				       <td><input type="text" size="20px" id="accAmountAftRes<%=k %>" style="background-color: pink;text-align: right;" value="${tdDaNewAccDetailsList.accAmountAftRes}" onkeypress="javascript:return checkFloat(event,'accAmountAftRes<%=k %>');" onchange="javascript:enableAccAmtAftCal(0);showAccAmtDayRepresentation('${workflowmap.requestId}','${workflowmap.requesterSfid}','${workflowmap.roleInstanceName}');individualTotAmount('all'); " autocomplete="off" /></td> <!-- This line has added for auto calculation  --> 
				       <td><input type="text" size="20px" id="claimedAmount<%=k %>" style="text-align: right;" value="${tdDaNewAccDetailsList.claimedAmount}" onkeypress="javascript:return checkFloat(event,'claimedAmount<%=k %>');" /></td>
				       <td><input type="text" size="25px" id="claimedAmountAftRes<%=k %>" style="background-color: pink;text-align: right;" value="${tdDaNewAccDetailsList.claimedAmountAftRes}" onkeypress="javascript:return checkFloat(event,'claimedAmountAftRes<%=k %>');" onkeyup="javascript:showAccAmtDayRepresentation('${workflowmap.requestId}','${workflowmap.requesterSfid}','${workflowmap.roleInstanceName}');individualTotAmount('all'); " /></td>       <!--function has changed individualTotAmount('daNewAccDetailsId'); as onkeyup function totaly-->
				     
				      <td><input type="hidden" size="25px" id="tadaDaPercentage"  value="${workflowmap.tadaDaPercentage}" /></td>
				       <td><input type="button" id="add0" value="+" onclick="javascript:checkFinDANewAccRow(<%=k %>);"/></td>
					   <td><input type="button" id="del0" value="-" onclick="javascript:deleteFinDANewAccRow(this,'daNewAccDetailsId');" /></td>
				</tr>
			<% k++; %>
			</c:forEach>
		</table> 
		<%-- <table border="1" style="width:100%">
		<tr>
				<td rowspan="1" class="tabletd" align="right" ><b>Accommodation Total(C)</b></td>
				<td rowspan="1" class="tabletd" align="right" >
				<div id="totAccAmount"></div>
				</td>
			</tr>
			</table>--%>
	</div>
	</c:if>
	<a id="accDayRepID" href="javascript:accAmtDayRepresentation('${workflowmap.requestId}','${workflowmap.requesterSfid}','${workflowmap.roleInstanceName}');individualTotAmount('all');">Regenerate Day Representation For Accomodation</a>
	</div>
</div>
<c:if test="${workflowmap.daNewAccDayRepList!='[]' && workflowmap.daNewAccDayRepList!=null}">
	<div class="headTitle">Accomodation Details-Day Representation</div>
	<div class="line">
		<table BORDER="1" cellpadding="2" cellspacing="0" id="daNewAccDayDetailsId" style="width:100%">
			<tr>
				<td rowspan="1" class="tabletd" align="center" >Date</td>
				<td rowspan="1" class="tabletd" align="center" >Amount</td>
			</tr>
			<%int u=0; %>
			<c:forEach var="daNewAccDayRepList" items="${workflowmap.daNewAccDayRepList}">
				<tr>
				   <%--  <c:choose>
				    <c:when test="${(workflowmap.roleInstanceName eq 'TADA TASK HOLDER' || workflowmap.roleInstanceName eq 'TA DA /Medical Section Head') && workflowmap.lastStagePendingCheck!='completed'}">
				      <td><input type="hidden" value="${daNewFoodDayRepList.id}" /><input type="text" id="repDate<%=u %>" size="70px" value="${daNewFoodDayRepList.representationDateOne}" onfocus ="javascript:Calendar.setup({inputField :'repDate<%=u %>',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" /></td>
					  <td><input type="text" id="repAmount<%=u %>" size="70px" style="text-align: right;" value="${daNewFoodDayRepList.representationAmount1}" onkeypress="javascript:return checkFloat(event,'repAmount<%=u %>')" onkeyup="javascript:getFoodTotal();getTadaTdTotalAmount();" /></td>
				    </c:when>
				    <c:otherwise>
				      <td>${daNewFoodDayRepList.representationDateOne}</td>
					  <td style="text-align: right;">${daNewFoodDayRepList.representationAmount1}</td>
				    </c:otherwise>
				    </c:choose>--%>
				    <td><input type="hidden" value="${daNewAccDayRepList.id}" /><input type="text" id="repDate<%=u %>" size="70px" value="${daNewAccDayRepList.representationDate}" onfocus ="javascript:Calendar.setup({inputField :'repDate<%=u %>',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" /></td>
					  <td><input type="text" id="repAccAmount<%=u %>" size="70px" style="text-align: right;" value="${daNewAccDayRepList.admisAmount}" onkeypress="javascript:return checkFloat(event,'repAccAmount<%=u %>')" onkeyup="javascript:individualTotAmount('all');" /></td>
				</tr>
			<% u++; %>
			</c:forEach>
			<tr>
				<td rowspan="1" class="tabletd" align="center" ><b>Accomodation Total(C)</b></td>
				<td rowspan="1" class="tabletd" align="right" >
				<c:choose>
				    <c:when test="${(workflowmap.roleInstanceName eq 'TADA TASK HOLDER' || workflowmap.roleInstanceName eq 'TA DA /Medical Section Head') && workflowmap.lastStagePendingCheck!='completed'}">
				      <input type="text" id="totalAccAmount" name="totalAccAmount" style="text-align: right" readonly="readonly" value="" maxlength="30"/>
				    </c:when>
				    <c:otherwise>
				     <input type="text" id="totalAccAmount" name="totalAccAmount" style="text-align: right" readonly="readonly" value="" maxlength="30"/>
				    </c:otherwise>
				</c:choose>
				</td>
			</tr>
			
		</table> 
	</div>
	<!-- <div class="headTitle">Claimed Amount is not Graterthen Accomodation Total Amount</div> -->
</c:if>
</div>
