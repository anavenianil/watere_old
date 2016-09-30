<!-- Begin : DemandRequestDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<div class="line">
	<div class="line">
		<div class="quarter bold">Inventory Number</div>
		<div class="quarter">${workflowmap.demandRequestDetails.invId}</div>
	</div>
	<div class="line">
		<div class="quarter bold">Purchase Type</div>
		<div class="quarter">Buildup</div>
	</div>
	<div class="line">
		<div class="quarter bold">Demand Type</div>
		<div class="quarter">Cash Purchase</div>
	</div>
	<div class="line">
		<div class="quarter bold">Account Head</div>
		<div class="quarter">${workflowmap.demandRequestDetails.accHeadNo}</div>
	</div>
		<div class="line">
		<div class="quarter bold">Group Demand Number</div>
		<div class="quarter">${workflowmap.demandRequestDetails.demandNo}</div>
	</div>
	<div class="line">
		<div class="quarter bold">Group Demand Date</div>
		<div class="quarter"><fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.demandRequestDetails.demandDate}" /></div>
	</div>
	<div class="line">
		<div class="quarter bold">MMG Control Number</div>
		<div class="quarter">${workflowmap.demandRequestDetails.mmgControlNo}</div>
	</div>
	<div class="line">
		<div class="quarter bold">MMG Control Date</div>
		<div class="quarter"><fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.demandRequestDetails.mmgControlDate}" /></div>
	</div>
	<div class="headTitle">Demand Material Details</div>
	<div class="line" >
		<table width="100%" cellpadding="2" cellspacing="0" border="1" id="demandMaterial" class="sub_2">
			<tr>
				<th width="5%">SNo</th>
				<th width="20%">Nomenclature</th>
				<th width="5%">Qty</th>
				<th width="5%">UOM</th>
				<th width="15%">Consumable/Non-Consumable</th>
				<th width="10%">Unit Rate(<b><del>&#2352;</del></b>)</th>	
				<th width="10%">Estimated Cost(<b><del>&#2352;</del></b>)</th>			
				<th width="15%">Amount Type</th>
				<c:if test="${workflowmap.requestType eq 'DEMAND'}">
					<c:if test="${workflowmap.demandRequestDetails.approvedDept eq 'CFA'}">
						<th width="10%">Delete</th>
					</c:if>
				</c:if>
			</tr>
			<c:forEach items="${sessionScope.JsonDemandList}" var="demand" varStatus="i" begin="0">
				<tr id="demandmaterial${i.count}">
					<td>&nbsp;<c:out value="${i.count}"/></td>
					<td>&nbsp;${demand.description}</td>
					<c:if test="${workflowmap.requestType eq 'DEMAND'}">
						<c:if test="${workflowmap.demandRequestDetails.approvedDept eq 'CFA'}">
							<td><input style="text-align:right" type="text" name="qty" value="${demand.qty}" id="qty${i.count}"/></td>						
						</c:if>
						<c:if test="${workflowmap.demandRequestDetails.approvedDept ne 'CFA'}">
							<td style="text-align:right">&nbsp;${demand.qty}</td>
						</c:if>
					</c:if>
					<c:if test="${workflowmap.requestType eq 'DEMANDCANCEL'}">
						<td style="text-align:right">&nbsp;${demand.qty}</td>
					</c:if>
					<td>&nbsp;${demand.uom}</td>
					<td>&nbsp;<c:if test="${demand.cflag eq 'NC'}">
								Non-Consumable
											 </c:if>
							<c:if test="${demand.cflag eq 'C'}">
								Consumable
							</c:if></td>
					<c:if test="${workflowmap.requestType eq 'DEMAND'}">
						<c:if test="${workflowmap.demandRequestDetails.approvedDept eq 'CFA'}">
							<td style="text-align:right"><input type="text" name="unitRate" style="text-align:right" value="<fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="2" value="${demand.unitRate}"/>" id="unitRate${i.count}"/></td>	
						</c:if>	
						<c:if test="${workflowmap.demandRequestDetails.approvedDept ne 'CFA'}">
							<td style="text-align:right">&nbsp;<fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="2" value="${demand.unitRate}"/></td>	
						</c:if>
					</c:if>
					<c:if test="${workflowmap.requestType eq 'DEMANDCANCEL'}">
						<td style="text-align:right">&nbsp;<fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="2" value="${demand.unitRate}"/></td>	
					</c:if>				
					<td style="text-align:right">&nbsp;<fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="2" value="${demand.amount}"/></td>					
						<c:if test="${workflowmap.requestType eq 'DEMAND'}">
							<c:if test="${workflowmap.demandRequestDetails.approvedDept eq 'BUDGET'}">
								<c:if test="${workflowmap.message ne 'completed'}">	
									<td>&nbsp;
									<select name="amountType" id="amountType${i.count}" class="amountType">
										<option value="select">Select</option>
										<c:forEach var="amounttypes" items="${workflowmap.demandRequestDetails.amountTypeList}">
											 <option value="${amounttypes.id}#${demand.materialCode}">${amounttypes.name}</option>
										</c:forEach> 
									</select> 
									</td>	
								</c:if>
								<c:if test="${workflowmap.message ne 'completed'}">	
									<td>&nbsp;${demand.amountType}</td>	
								</c:if>
							</c:if>
							<c:if test="${workflowmap.demandRequestDetails.approvedDept ne 'BUDGET'}">	
								<td>&nbsp;${demand.amountType}</td>		
							</c:if>	
						</c:if>
					
					<c:if test="${workflowmap.requestType eq 'DEMANDCANCEL'}">
						<td>&nbsp;${demand.amountType}</td>		
					</c:if>
					<c:if test="${workflowmap.demandRequestDetails.approvedDept eq 'CFA'}">	
						<TD align="center" style="width:10%;text-align:center"><a href="javascript:deleteItem('${demand.materialCode}','request','demandmaterial${i.count}')"><img src="./images/delete.gif"/></a></TD>
					</c:if>							
				</tr>
			</c:forEach>
			<tr>
				<td colspan="1">Total :</td>
				<td colspan="6" style="text-align:right"><fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="2" value="${workflowmap.demandRequestDetails.totalCost}"/></td>
				<td>&nbsp;</td>
			</tr>
		</table>							
	</div>
	<c:if test="${workflowmap.message ne 'completed'}">
		<c:if test="${workflowmap.requestType eq 'DEMAND'}">
			<c:if test="${workflowmap.demandRequestDetails.approvedDept eq 'FINANCE'}">	
			<fieldset>
					
				<div class="line">
					<input type="radio" name="paymentTypeId" value="1">Advance Amount
				</div>
				<div class="line">
				<div class="quarter">Amount Date</div>
				<div class="quarter">${workflowmap.paymentDate}</div>
				<div class="quarter">Amount</div>
				<div class="quarter">
					<input type="text" name="fundAmount"></input>
				</div>
				</div>
				<div class="line">
				<div class="quarter">Description</div>
				<div class="quarter"><input type="text" name="description"></input></div>
					
				</div>
			</fieldset>
			</c:if>
		</c:if>
	</c:if>
	<c:if test="${ not empty workflowmap.demandRequestDetails.fundDetails}">
		<div class="headTitle">Demand Payment Details</div>
			<div class="line" >
				<table width="100%" cellpadding="2" cellspacing="0" border="1" id="demandMaterial" class="sub_2">
					<tr>
						<th width="5%">SNo</th>
						<th width="20%">DemandNo</th>
						<th width="5%">Payment Type</th>
						<th width="5%">Fund Amount</th>
						<th width="15%">Payment Date</th>
					</tr>
					<c:forEach items="${workflowmap.demandRequestDetails.fundDetails}" var="payment" varStatus="i" begin="0">
						<tr>
							<td>&nbsp;<c:out value="${i.count}"/></td>
							<td>&nbsp;${workflowmap.demandRequestDetails.demandNo}</td>
							<td>&nbsp;
								<c:if test="${payment.paymentTypeId eq '1'}">
									Advance
								</c:if>
								<c:if test="${payment.paymentTypeId eq '2'}">
									Settlement
								</c:if>
							</td>
							<td>&nbsp;${payment.fundAmount}</td>
							<td>&nbsp;<fmt:formatDate pattern="dd-MMM-yyyy" value="${payment.creationDate}"/></td>
						</tr>
					</c:forEach>
				</table>
			</div>
	</c:if>
	<c:if test="${workflowmap.requestType eq 'DEMANDCANCEL'}">
		<div class="line">
			<div class="quarter">Reason</div>
			<div class="threefourth">:&nbsp;<c:out  value="${workflowmap.demandRequestDetails.reason}"></c:out></div>	
		</div>
	</c:if>
</div>
<script>displayResult('${message}');</script>
<!-- End : RequestHistoryDetails.jsp -->