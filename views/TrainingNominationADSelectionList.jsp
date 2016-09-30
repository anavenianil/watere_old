<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : TrainingNominationADSelectionList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>

<div>
	<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
	<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
</div>

<div id="dataTable">
<%int i=0; %>
	<display:table name="${sessionScope.TrainingNominationADSelectionList}" excludedParams="*"
		export="false" class="list" requestURI="" id="ADSelections" pagesize="1000"
		sort="list" cellpadding="2" cellspacing="1">
		<display:column  style="width:3%;vertical-align:middle" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAllDisabled(this.name,\'row\',\'ADSelection\');"/>'>
			<input type="checkbox" value="${ADSelections.id}" class="row" name="ADSl" id="ADSl<%=i %>" onclick="checkBoxCheck(this.name);" <c:if test="${ADSelections.requestStatus=='33'}"> checked=true</c:if> <c:if test="${ADSelections.requestStatus=='33'}"> disabled=true</c:if>/>
		</display:column>
		<display:column title="Req ID" style="width:3%;vertical-align:middle">&nbsp;<a href="javascript:getRequestDetails('${ADSelections.historyID}','${ADSelections.requestId}','myRequests','pending','')"><font color="blue">${ADSelections.requestId}</font></a></display:column>
		<display:column title="SFID" style="width:6%;vertical-align:middle">&nbsp;${ADSelections.trainingNominationDto.nomineeSfid}</display:column>
		<display:column title="Employee" style="width:8%;vertical-align:middle">&nbsp;${ADSelections.empBean.nameInServiceBook}</display:column>
		<display:column title="Letter Number" style="width:8%;vertical-align:middle">&nbsp;${ADSelections.letterNumber}</display:column>
		<display:column title="Status" style="width:8%;vertical-align:middle">&nbsp;<c:if test="${ADSelections.requestStatus=='33'}">Approved by AD</c:if><c:if test="${ADSelections.requestStatus=='27'}">Sent for AD approval </c:if></display:column>
		<display:column title="Edit" style="width:8%;vertical-align:middle">&nbsp;<a href="javascript:getEditable('ADSl<%=i %>')"><font color="blue">Edit</font></display:column>
		
		<% i++; %>
	</display:table>
	
</div>
<script>
			$jq( function(){
				   $jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
				   
				})
			
		</script>
		
<!-- End : TrainingNominationADSelectionList.jsp -->





