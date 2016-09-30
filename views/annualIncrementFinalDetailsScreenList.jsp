<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : annualIncrementFinalDetailsScreenList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>

<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />

<spring:bind path="increment"><div id="result1" class="line" style="color: green;">${increment.result}</div></spring:bind>
<div class="line"><c:if test="${not empty increment.empNullValuesDetails}">
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
			<c:forEach var="annualIncrementNullDetails" items="${increment.empNullValuesDetails}"
				varStatus="rowCounter">
				<tr>
					<td style="text-align:center"><input type="checkbox" class="row" name="row" id="${annualIncrementNullDetails.userSfid}" onclick="checkBoxCheck('row');"/></td>
					<td style="text-align: right"><%=i%></td>
					<td>${annualIncrementNullDetails.userSfid}</td>
					<td>${annualIncrementNullDetails.name}</td>
					<td>${annualIncrementNullDetails.designationName}</td>
					<td style="text-align: right"><fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="0" value="${annualIncrementNullDetails.basicPay}"/></td>
					<td style="text-align: right"><fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="0" value="${annualIncrementNullDetails.gradePay}"/></td>
					<td style="text-align: right">${annualIncrementNullDetails.basicGradeTotal}</td>
					<td style="text-align: right">${annualIncrementNullDetails.increment3}</td>
					<td style="text-align: right"><fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="0" value="${annualIncrementNullDetails.incRoundOff}"/></td>
					<td style="text-align: right">
					<fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="0" value="${annualIncrementNullDetails.incrementBasicPay}"/>
					<!--<fmt:formatNumber maxFractionDigits="0" pattern="###.00"><c:if test="${(annualIncrementNullDetails.basicPay+annualIncrementNullDetails.incRoundOff)>80000}">80000</c:if>
					<c:if test="${(annualIncrementNullDetails.basicPay+annualIncrementNullDetails.incRoundOff)<80000}">
					${annualIncrementNullDetails.basicPay+annualIncrementNullDetails.incRoundOff}</c:if></fmt:formatNumber>-->
					</td>
					<td style="text-align: right"><fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="0" value="${annualIncrementNullDetails.gradePay}"/></td>
				</tr>
				<%i++; %>
			</c:forEach>
		</table>
		</div>
	</c:if></div>
	<c:if test="${not empty increment.empNullValuesDetails}">
		<div class="line">
			<div class="expbutton"  style="float:right"><a href="javascript:updateAnnualIncrementDetailsTable();" ><span>Update Incremented Basic Pay</span></a></div>
			</div>
	</c:if>
	<c:choose>
	<c:when test="${not empty increment.empNotNullValuesDetails && empty increment.empNullValuesDetails}">
	<div class="line" style="color: green;"> SOME EMPLOYEE BASIC PAY'S UPDATION IS PENDING WITH ANOTHER TASK HOLDER
	</div>
	</c:when>
	<c:otherwise >
	<c:if test="${empty increment.empNullValuesDetails}">
	<div class="line" style="color: red"> YOU DONT HAVE PERMISSION TO SEE THIS DO PART 
	</div>
	</c:if>
	</c:otherwise>
	</c:choose> 
	
	

<!-- End : annualIncrementFinalDetailsScreenList.jsp -->