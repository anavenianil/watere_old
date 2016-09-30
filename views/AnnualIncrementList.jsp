<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : AnnualIncrementList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>

<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />


<c:if test="${not empty increment.empDetails}">
<c:if test="${increment.param != 'EmployeeIntegratedPayIncrement' && increment.param != 'submitIncrementsTopay'}">
<div class="line">
		<div class="quarter leftmar">Effective Date</div>
		<div class="quarter">
			<input path="adminEffectDate" id="adminEffectDate" readonly="true" />
				<img  src="./images/calendar.gif" id="adminEffectDateImg" />	
					<script type="text/javascript">
						Calendar.setup({inputField :"adminEffectDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"adminEffectDateImg",singleClick : true,step : 1});
					</script>	
		 </div>
</div>
</c:if>
</c:if>
<spring:bind path="increment"><div id="result" class="line" style="color: green;">${increment.result}</div></spring:bind>
<div class="line"><c:if test="${not empty increment.empDetails}">
		<div class="line">
		<%int i=1; %>
		<table width="100%" cellpadding="2" cellspacing="0" border="1"
			id="incrementtable">
			<tr>
				<th style="width: 3%;text-align:center" rowspan="2"><input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll('selectall','row');"/></th>
				<th style="width: 2%" rowspan="2">S.No</th>
				<th style="width: 6%" rowspan="2">SFID</th>
				<th style="width: 20%" rowspan="2">Name</th>
				<th style="width: 5%" rowspan="2">Designation</th>
				<th style="width: 20%" colspan="2">Before Increment</th>
				<th style="width: 7%" rowspan="2">Total</th>
				<th style="width: 10%" rowspan="2">Increment</th>
				<th style="width: 10%" rowspan="2">Increment Round Off</th>
				<th style="width: 20%" colspan="2">After Increment</th>
			</tr>
			<tr>

				<th style="width: 10%">Pay</th>
				<th style="width: 10%">GP</th>
				<th style="width: 10%">Pay</th>
				<th style="width: 10%">GP</th>
			</tr>
			<c:forEach var="annualincrement" items="${increment.empDetails}"
				varStatus="rowCounter">
				<tr>
					<td style="text-align:center"><input type="checkbox" class="row" name="row" id="${annualincrement.userSfid}" onclick="checkBoxCheck('row');"/></td>
					<td style="text-align: right"><%=i%></td>
					<td>${annualincrement.userSfid}</td>
					<td>${annualincrement.name}</td>
					<td>${annualincrement.designationName}</td>
					<td style="text-align: right"><fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="0" value="${annualincrement.basicPay}"/></td>
					<td style="text-align: right"><fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="0" value="${annualincrement.gradePay}"/></td>
					<td style="text-align: right">${annualincrement.basicGradeTotal}</td>
					<td style="text-align: right">${annualincrement.increment3}</td>
					<td style="text-align: right"><fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="0" value="${annualincrement.incRoundOff}"/></td>
					<td style="text-align: right">
					<fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="0" value="${annualincrement.incrementBasicPay}"/>
					<!-- <fmt:formatNumber maxFractionDigits="0" pattern="###.00">
					<c:if test="${(annualincrement.basicPay+annualincrement.incRoundOff)>80000}">80000</c:if>
					<c:if test="${(annualincrement.basicPay+annualincrement.incRoundOff)<80000}">${annualincrement.basicPay+annualincrement.incRoundOff}</c:if> </fmt:formatNumber>-->
					</td>
					<td style="text-align: right"><fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="0" value="${annualincrement.gradePay}"/></td>
				</tr>
				<%i++; %>
			</c:forEach>
		</table>
		</div>
	</c:if></div>
	<c:if test="${not empty increment.empDetails}">
	<c:choose>
	 <c:when test="${increment.param == 'EmployeeIntegratedPayIncrement' || increment.param == 'submitIncrementsTopay'}">
		<div class="line">
			<div class="expbutton"  style="float:right"><a href="javascript:saveAnnualIncrementsToPay();" ><span>Submit</span></a></div>
			</div>
	</c:when>
	<c:otherwise>
	<div class="line" id="publishButtonDiv" style="display:none">
			<div class="expbutton"  style="float:right"><a href="javascript:saveAnnualIncrements();" ><span>Publish in DO Part II</span></a></div>
	</div>
	</c:otherwise>
	</c:choose>
	</c:if>
	<c:if test="${increment.param != 'EmployeeIntegratedPayIncrement' && increment.param != 'submitIncrementsTopay' && increment.param != 'AnnualIncrementFinance'}">
    <c:if test="${empty increment.empDetails && increment.roleId==0}">
	<div class="line"> YOU DONT HAVE PERMISSION TO SEE THIS DO PART 
	</div>
	</c:if>
	 <c:if test="${empty increment.empDetails && increment.roleId!=0}">
	<div class="line" style="color: red;"> NO MORE PENDING LIST IS AVAILABLE WITH THIS TASK HOLDER 
	</div>
	</c:if>
	</c:if>

<!-- End : AnnualIncrementList.jsp -->