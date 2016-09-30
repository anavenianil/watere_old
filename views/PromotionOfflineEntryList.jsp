<!-- Begin :PromotionOfflineEntryList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>

	<%--<c:if test="${message=='employeeexists' || Result=='employeeexists'}"> 
 	 <div class="leftmar">Name : ${sessionScope.employeeDetails.nameInServiceBook} 
 	&nbsp; &nbsp; &nbsp; &nbsp; Designation : ${sessionScope.employeeDetails.designationDetails.name}</div>
	</c:if>--%>
	<c:choose>
	<c:when test="${sessionScope.jsonRole eq '1' || sessionScope.jsonWorkLocation ne '1' && sessionScope.jsonRole eq '0' || empty sessionScope.PromotionOfflineList}">
		<script>
		 $jq('#promoSumbitDiv').css("display","block");
		</script>
	</c:when>
	<c:otherwise>
		<script>
		 $jq('#promoSumbitDiv').css("display","none");
		</script>
	</c:otherwise>
	</c:choose> 
	
	<c:if test="${invalidSFID =='invalidSFID'}"><span class="failure">Invalid SFID</span>
		<script>
			$jq('#empGradePayGrid').css("display","none");
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
 <c:if test="${empPayment.varIncVal ne null}">
	<script>
	$jq('#valriableIncVal').val(${empPayment.varIncVal});
	</script>
</c:if>

	<c:if test="${sessionScope.catWiseDesigJson ne null && sessionScope.empGradePayJson ne null}">
	<script>
		   catWiseDesigJson= <%=session.getAttribute("catWiseDesigJson")%>;
		   if (catWiseDesigJson!=null) {
						//$jq('#designationFrom').append($jq('<option></option>').attr("value",'0').text('Select'));
						$jq('#designationTo').append($jq('<option></option>').attr("value",'0').text('Select'));
						for(i=0;i<catWiseDesigJson.length;i++){
						//$jq('#designationFrom').append($jq("<option></option>").attr("value",catWiseDesigJson[i].designationID).text(catWiseDesigJson[i].desigName));
						$jq('#designationTo').append($jq("<option></option>").attr("value",catWiseDesigJson[i].designationID).text(catWiseDesigJson[i].desigName));
						}
					}
			var empGradePayJson = <%=session.getAttribute("empGradePayJson")%>;
			$jq('#newBasicPay').val(${basicPay});
				$jq('#empGradePayGrid').css("display","block");
				$jq('#refSfid').val($jq('#SearchSfid').val());
		</script>
	</c:if>
	
<c:if test="${sessionScope.PromotionOfflineList ne null}">
	<c:if test="${message=='empNotExists' || Result=='empNotExists'}"> <span class="failure"><spring:message code="empNotExists"/></span></c:if>
	<c:if test="${message=='failure' || Result=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
	<c:if test="${message=='success' || Result=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
	<c:if test="${message=='delete' || Result=='delete'}"> <span class="failure"><spring:message code="delete"/></span></c:if>
	<c:if test="${message=='update' || Result=='update'}"> <span class="success"><spring:message code="update"/></span></c:if>
	<c:if test="${message=='duplicate' || Result=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
	<div>
    <div id="Pagination">
			<display:table name="${sessionScope.PromotionOfflineList}" excludedParams="*"
				export="false" class="list" requestURI="" id="offlineList" pagesize="10"
				sort="list">
				<display:column title="Type" style="width:10%">&nbsp;${offlineList.referenceType}</display:column>
				<display:column title="SFID" style="width:10%">&nbsp;${offlineList.sfID}</display:column>
				<display:column title="Name" style="width:20%">${offlineList.empName}</display:column>
				
				<display:column title="Designation" style="width:15%">${offlineList.name}</display:column>
				<%--<display:column title="Basic Pay" style="width:15%">${offlineList.pay}</display:column>--%>
				<display:column title="Grade Pay" style="width:10%">${offlineList.empNewGradePay}</display:column>
				<display:column title="Variable Increments Points" style="width:10%">${offlineList.varIncPt}</display:column>
				<display:column title="two addl inc values" style="width:10%">${offlineList.twoAddl}</display:column>
				
					<display:column style="width:5%;text-align:center" title="Edit">
						<c:if test="${offlineList.referenceType ne 'ONLINE'}" >
							<img src="./images/edit.gif" title="Edit"
								onclick="editOfflineEntry('${offlineList.id}','${offlineList.sfID}','${offlineList.varIncPt}','${offlineList.promotedDesignation}','<fmt:formatDate pattern="dd-MMM-yyyy" value="${offlineList.presentEffectiveDate}"/>','<fmt:formatDate pattern="dd-MMM-yyyy" value="${offlineList.promotedEffectiveDate}"/>','${offlineList.twoAddl}','${offlineList.gradePay}','${offlineList.pay}','${offlineList.empNewGradePay}','${offlineList.seniorityDate}','<fmt:formatDate pattern="dd-MMM-yyyy" value="${offlineList.varIncEndDate}"/>');" />
						</c:if>
					</display:column>
					<display:column style="width:5%;text-align:center" title="Delete">
						<c:if test="${offlineList.referenceType ne 'ONLINE'}" >
							<img src="./images/delete.gif" title="Delete"
								onclick="deleteOfflineEntry('${offlineList.id}','${offlineList.sfID}');" />	
						</c:if>
					</display:column>
			 </display:table>	
		 </div>
		 <script>
		
		
		 </script>
		
	</div>
</c:if>
<!-- End :PromotionOfflineEntryList.jsp -->