<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : PassportProceedingDetailsList.jsp -->

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
   	<display:table name="${sessionScope.PassportProceedingDetailsList}" excludedParams="*"
		export="false" class="list" requestURI="" id="passProceed" pagesize="10"
		sort="list">
		<display:column title="SFID" style="width:20%;">${passProceed.sfid}</display:column>
		<display:column title="Issue Date" style="width:20%;">${passProceed.issueDate}</display:column>
		<display:column title="Return Date" style="width:20%;">${passProceed.returnDate}</display:column>
		<display:column title="Stay Duration" style="width:20%;">${passProceed.duration}</display:column>
		<display:column title="Amount" style="width:20%;right;">${passProceed.spendingAmount}</display:column>
		
			<display:column style="width:10%;text-align:center" title="Edit">
				<img src="./images/edit.gif" title="Edit"
					onclick="editPassProceed('${passProceed.requestID}','${passProceed.sfid}','${passProceed.passportType}','${passProceed.relations}','${passProceed.vigilanceFlag}','${passProceed.passportNumber}','${passProceed.issueDate}','${passProceed.returnDate}','${passProceed.validityDate}','${passProceed.duration}','${passProceed.purpose}','${passProceed.familyMemberId}','${passProceed.remarks}','${passProceed.spendingAmount}','${passProceed.sourceOfAmount}','${passProceed.lenderName}','${passProceed.nationality}','${passProceed.lenderRelationship}','${passProceed.departureDate}','${passProceed.countriesToVisit}','${passProceed.employmentDetails}','${passProceed.passportPossessFlag}','${passProceed.familyMembersFlag}','${passProceed.selfFinanceFlag}','PassportProceedingDetailsList','${passProceed.verificationType}',
					                          '${passProceed.detailsFlag}','${passProceed.supportName}','${passProceed.supportOccupation}','${passProceed.supportAddress}','${passProceed.passportLostFlag}','${passProceed.lostPassportType}','${passProceed.lostPassportNumber}','${passProceed.ppissueDate}','${passProceed.ppvalidityDate}','${passProceed.hospitalFlag}','${passProceed.prevCountryDetails}','${passProceed.idCardFlag}','${passProceed.settleFlag}','${passProceed.contractualFlag}','${passProceed.coPeriodFrom}','${passProceed.coPeriodTo}','${passProceed.certifyStatus}')" />
			
			
																																																																																		
																				
			
			</display:column>
			<display:column style="width:10%;text-align:center" title="Delete">
		    <c:if test="${passProceed.verificationType ne 'Y'}" >
				<img src="./images/delete.gif" title="Delete"
					onclick="deletePassProceedDetails('${passProceed.requestID}')" />
			</c:if>		
			</display:column>
			
		<display:column title="Report" style="width:10%"><a href="javascript:passportReport('${passProceed.sfid}')">pdf</a></display:column>
		
	</display:table>
</div>



	<script>
		
		$jq( function(){
	 $jq("#dataTable").displayTagAjax('paging');
	})
	</script>


<!-- End : PassportProceedingDetailsList.jsp -->





