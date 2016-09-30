<!-- Begin :EmployeePaymentEnrtyList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%-- <script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>--%>



	<c:if test="${message=='failure' || Result=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
	<c:if test="${message=='success' || Result=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
	<c:if test="${message=='empNotExists' || Result=='empNotExists'}"> <span class="failure"><spring:message code="empNotExists"/></span></c:if>
	<c:if test="${message=='duplicate' || Result=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
	<c:if test="${message=='empPayBandFail'}"><span class="failure">Basic Pay value is not in prescribed band</span></c:if>
	<c:if test="${message eq 'update'}"><div class="myStyle success"><spring:message code="update"/></div></c:if>
	<c:if test="${Result eq 'delete'}"><div class="myStyle success"><spring:message code="delete"/></div></c:if> 
	
	<c:choose>
	<c:when test="${sessionScope.jsonRole eq '1' || sessionScope.jsonWorkLocation ne '1' && sessionScope.jsonRole eq '0' || empty sessionScope.empPaymentList}">
		<script>
		 $jq('#empSumbitDiv').css("display","block");
		</script>
	</c:when>
	<c:otherwise>
		<script>
		 $jq('#empSumbitDiv').css("display","none");
		</script>
	</c:otherwise>
	</c:choose> 
	
	<c:if test="${invalidSFID ne null}"><span class="failure">${invalidSFID}</span>
		<script>
			$jq('#empBasicPayGrid').css("display","none");
			$jq('#Pagination').css("display","none");
			$jq('#empDetailsSubGrid').html('');
		</script>
	</c:if>
	<c:if test="${sessionScope.empDetailsList ne null}">
		<div id="empDetailsSubGrid" style="display:none">
			<fieldset style="float:left;width:97.4%;"><legend><strong><font color='green'>Employee Details</font></strong></legend>
				<div class="line">
				<c:forEach var="emp" items="${sessionScope.empDetailsList}">
				 <div class="quarter">SFID:<b>${emp.key}</b></div>
				 <div class="quarter">NAME:<b>${emp.name}</b></div>
				<div class="quarter">DESIGNATION:<b>${emp.flag}</b></div>
				<div class="quarter">DIVISION:<b>${emp.value}</b></div>
				</c:forEach>
				</div>
			</fieldset>																		
		</div>
		<script>
		$jq('#empDetailsMainGrid').html($jq('#empDetailsSubGrid').html());
		$jq('#empDetailsMainGrid').css("display","block");
		</script>
	</c:if>
		
	<c:if test="${message=='catWiseDesig'}"> 
		<script>
		catWiseDesigJson = <%=  (net.sf.json.JSONArray)session.getAttribute("catWiseDesigJson")  %>;
		designationList(catWiseDesigJson);
		var empPayScaleJson = <%= (net.sf.json.JSONArray)session.getAttribute("empPayScaleJson") %>;
		payScaleList(empPayScaleJson);
		$jq('#empBasicPayGrid').css("display","block");
		$jq('#sfid').val($jq('#SearchSfid').val());
		</script>
	 </c:if>
		

<c:if test="${sessionScope.empPaymentList ne null}">
<div>
    <div id="Pagination">
	<display:table name="${sessionScope.empPaymentList}" excludedParams="*"
		export="false" class="list" requestURI="" id="payList" pagesize="10"
		sort="list">
		<display:column title="SFID" style="width:10%">&nbsp;${payList.sfid}</display:column>
		<display:column title="Name" style="width:15%">${payList.name}</display:column>
		<display:column title="Designation" style="width:12%">${payList.desigName}</display:column>
		<display:column title="Basic Pay" style="width:8%">${payList.basicPay}</display:column>
		<display:column title="Inc.Type" style="width:5%">${payList.incrementType}</display:column>
		<display:column title="Inc.Value" style="width:5%">${payList.incrementValue}</display:column>
		<display:column title="Effective Date" style="width:10%">
		
		<fmt:formatDate value="${payList.presentEffectiveDate}" pattern="dd-MMM-yyyy" />
		</display:column>
		
		<display:column style="width:5%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" 
			onclick="editBasicPayHistory('${payList.id}','${payList.sfid}','<fmt:formatDate value="${payList.presentEffectiveDate}" pattern="dd-MMM-yyyy" />','${payList.basicPay}',
			'${payList.designationId}','${payList.incrementType}','${payList.incrementValue}','${payList.status}','${payList.referenceType}')" />
		</display:column>
			
		<display:column style="width:5%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="deleteBasicPayHistory('${payList.id}','${payList.sfid}')" />		
		</display:column>
		
	</display:table>	
	</div>
	<script>
			$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
	</script>
</div>
</c:if>
<!-- End :EmployeePaymentEnrtyList.jsp -->


