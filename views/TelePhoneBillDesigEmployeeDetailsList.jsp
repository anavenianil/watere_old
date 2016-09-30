<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : TelePhoneBillDesigEmployeeDetailsList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div class="line"> <jsp:include page="Result.jsp"/>
</div>
<div id="dataTable">
	<display:table name="${telephone.telephoneDesigList}" excludedParams="*"
		export="false" class="list" requestURI="" id="teleDesigList" pagesize="10"
		sort="list" cellpadding="2" cellspacing="1">
		
		<display:column title="Designation" style="width:15%;vertical-align:middle">${teleDesigList.designations}</display:column>
		<display:column title="Internet Amount + ServiceTax" style="width:2%;vertical-align:middle">${teleDesigList.amountWithInternet}+${teleDesigList.serviceTaxWithInternet}%</display:column>
		<display:column title="WithOut Internet Amount + ServiceTax" style="width:2%;vertical-align:middle">${teleDesigList.amountWithoutInternet}+${teleDesigList.serviceTaxWithoutInternet}%</display:column>
		<c:choose>
		<c:when test="${teleDesigList.applicableEmployee eq 1}">
		<display:column title="Employee Applicability" style="width:2%;vertical-align:middle;" ><b style=" color:green;  font-size: 13pt ">Yes</b></display:column>
		</c:when>
		<c:otherwise>
		<display:column title="Employee Applicability" style="width:2%;vertical-align:middle"><b style="color: red; font-size: 13pt">No</b></display:column>
		</c:otherwise>
		</c:choose>
		<display:column style="width:5%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="editTeleDesigDetails('${teleDesigList.designationsIds}','${teleDesigList.amountWithInternet}','${teleDesigList.serviceTaxWithInternet}','${teleDesigList.totAmountWithInternet}','${teleDesigList.amountWithoutInternet}','${teleDesigList.serviceTaxWithoutInternet}','${teleDesigList.totAmountWithoutInternet}','${teleDesigList.applicableEmployee}')"/>
		</display:column>
		<display:column style="width:5%;text-align:center" title="Delete">
		<img src="./images/delete.gif" title="Delete" onclick="deleteTeleDesigDetails('${teleDesigList.designationsIds}')" />
		</display:column>
		</display:table>
		
</div>
<!-- End : TelePhoneBillDesigEmployeeDetailsList.jsp -->




