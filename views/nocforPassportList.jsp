<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : nocforPassportList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
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
   	<display:table name="${sessionScope.nocforPassportList}" excludedParams="*"
		export="false" class="list" requestURI="" id="passProceed" pagesize="10"
		sort="list">
		<display:column title="SFID" style="width:5%;">${passProceed.sfID}</display:column>
		<display:column title="Admin Note" style="width:20%;">${passProceed.adminNoteNo}</display:column>
		<display:column title="Admin Date" style="width:15%;">${passProceed.adminNoteDt}</display:column>
		
		<display:column title="Report" style="width:15%"><a href="javascript:adminNoteReport('${passProceed.sfID}','${passProceed.adminNoteNo}','${passProceed.adminNoteDt}')">Admin Note</a></display:column>
		
		<display:column title="Passport Type" style="width:20%;">${passProceed.passportAppType}</display:column>
		<display:column title="Received Date" style="width:15%;right;">${passProceed.receivedDt}</display:column>
		
			<display:column style="width:5%;text-align:center" title="Edit">
				<img src="./images/edit.gif" title="Edit"
					onclick="editNOCforPPDetails('${passProceed.id}','${passProceed.sfID}','${passProceed.passportAppType}','${passProceed.letterDesc}','${passProceed.adminNoteNo}','${passProceed.adminNoteDt}','${passProceed.letterDate}','${passProceed.receivedDt}','${passProceed.idCertificateNo}','${passProceed.idCertificateDt}')"  />
			</display:column>
			<display:column style="width:10%;text-align:center" title="Delete">
			<c:if test="${passProceed.passportAppType eq 'passport'}" >
				<img src="./images/delete.gif" title="Delete"
					onclick="deletePassportDetailsforNOC('${passProceed.id}')" />
			</c:if>
			</display:column>
		
	</display:table>
</div>





<!-- End : nocforPassportList.jsp -->





