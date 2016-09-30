<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : RequestList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<div class="line">
      <c:if test="${message eq 'success'}">
  <div class="myStyle success"">Successfully Delegated</div>
         </c:if>
         <c:if test="${message eq 'failure'}"> 
             <div class="myStyle failure">Delegation failed</div>
        </c:if>
  </div>

<aa:zone name="requestTable">
	<display:table name="${sessionScope.requestDetails}" excludedParams="*"
		export="false" class="list" requestURI="" id="requestList" pagesize="10"
		sort="list">
		<display:column title="" style="width:5%;"><input type="checkbox"  value="${requestList.requestID}"/></display:column>
		<display:column  title='RequestID'  style="width:5%;"><a href="javascript:getRequestDetails('${requestList.historyID}','${requestList.requestID}','requestDelegation','${requestList.stageID}')" style="text-decoration: none">${requestList.requestID}</a></display:column>
		<display:column  title='Assigned SFID'  style="width:10%;">${requestList.sfID}</display:column>
		<display:column  title='Assigned Employee'  style="width:50%;">${requestList.name}</display:column>
		<display:column  title='Request Type'  style="width:30%;">${requestList.requestType}</display:column>
	</display:table>
</aa:zone>
<script>displayPaging("requestTable","requestList");</script> 
<!-- End : RequestList.jsp -->