<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : TrainingNominationsCFASelectionList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>

<div>
	<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
	<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
</div>
<div>
<div id="dataTable">
<%int i=0; %>
	<display:table name="${sessionScope.TrainingNominationsCFASelectionList}" excludedParams="*"
		export="false" class="list" requestURI="" id="CFASelections" pagesize="1000"
		sort="list" cellpadding="2" cellspacing="1">
		<display:column  style="width:3%;vertical-align:middle" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'row\');"/>'>
			<input type="checkbox" value="${CFASelections.id}" class="row" name="CFASl" id="CFASl<%=i %>" onclick="checkBoxCheck(this.name);" <c:if test="${CFASelections.requestStatus=='39'}"> checked=true</c:if> <c:if test="${CFASelections.requestStatus=='39'}"> disabled=true</c:if>/>
		</display:column>
		<display:column title="Req ID" style="width:3%;vertical-align:middle">&nbsp;<a href="javascript:getRequestDetails('${CFASelections.historyID}','${CFASelections.requestId}','myRequests','pending','')"><font color="blue">${CFASelections.requestId}</font></a></display:column>
		<display:column title="SFID" style="width:6%;vertical-align:middle">&nbsp;${CFASelections.trainingNominationDto.nomineeSfid}</display:column>
		<display:column title="Employee" style="width:8%;vertical-align:middle">&nbsp;${CFASelections.empBean.nameInServiceBook}</display:column>
		<display:column title="Rs. Fee & ServiceTax " style="width:8%;vertical-align:middle">Rs.&nbsp;${CFASelections.courseDto.fee}&nbsp;<c:if test="${CFASelections.courseDto.serviceTax != null}">+Service Tax ${CFASelections.courseDto.serviceTax}%</c:if></display:column>
		<display:column title="Edit" style="width:8%;vertical-align:middle">&nbsp;<a href="javascript:getEditable('CFASl<%=i %>')"><font color="blue">Edit</font></display:column>
		
		<% i++; %>
	</display:table>
	
	
</div>
<script>
			$jq( function(){
				   $jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
				   
				})
				
		</script>
		
<!-- End : TrainingNominationsCFASelectionList.jsp -->





