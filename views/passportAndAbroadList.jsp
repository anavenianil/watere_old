<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : passportAndAbroadList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<div>
    
	<c:if test="${message=='failure' || Result=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
	<c:if test="${message=='success' || Result=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
	<c:if test="${message=='empNotExists' || Result=='empNotExists'}"> <span class="failure"><spring:message code="empNotExists"/></span></c:if>
	<c:if test="${message=='duplicate' || Result=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
	<c:if test="${message=='EXISTS' || Result=='EXISTS'}"> <span class="failure"><spring:message code="detailsExist"/></span></c:if>
	<c:if test="${message=='employeeexists' || Result=='employeeexists'}"> <span class="success"><spring:message code="employeeexists"/></span></c:if>
	<c:if test="${message=='delete' || Result=='delete'}"> <span class="failure"><spring:message code="delete"/></span></c:if>
	<c:if test="${message=='update' || Result=='update'}"> <span class="success"><spring:message code="update"/></span></c:if>

</div>
								
<div id="dataTable">
   	<display:table name="${sessionScope.PassportProceedingDetailsList}" excludedParams="*" export="false" 
   	class="list" requestURI="" id="passProceed" pagesize="100" sort="list">
	
		<display:column title="SFID" style="width:20%;">${passProceed.sfid}</display:column>
		<display:column title="HQRS Letter No" style="width:20%;">${passProceed.hqLetterNo}</display:column>
		<display:column title="HQRS Letter Date" style="width:20%;">${passProceed.hqLetterDt}</display:column>
			<display:column style="width:10%;text-align:center" title="Edit">
				<img src="./images/edit.gif" title="Edit"
					onclick="editPassportAndAbroad('${passProceed.id}','${passProceed.sfid}','${passProceed.hqLetterNo}','${passProceed.hqLetterDt}')" /> 
			</display:column>
				
	</display:table>
</div>




<!-- End : passportAndAbroadList.jsp -->





