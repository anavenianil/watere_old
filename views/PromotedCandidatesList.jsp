<!-- Begin : PromotedCandidatesList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<div id="resultMsg">
	<jsp:include page="Result.jsp"></jsp:include>
</div>
<div id="Pagination">
	<%int i=0; %>
	<display:table name="${sessionScope.assessmentList}" excludedParams="*"
		export="false" class="list" requestURI="" id="list" pagesize="1000"
		sort="list" >
		<display:column  style="width:3%;vertical-align:middle" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxPromotionCheckAll(this.name,\'row\');"/>'>
			<c:if test="${list.promotionStatus != 'Accepted'}">
				<input type="checkbox" class="row" name="row" id="check<%=i %>" disabled="disabled"/>
			</c:if>
			<c:if test="${list.promotionStatus == 'Accepted'}">
				<input type="checkbox" class="row" name="row" id="check<%=i %>"/>
			</c:if>			
		</display:column>
		<display:column title="SFID" style="width:5%" >
			${list.sfID}			
		</display:column>
		<display:column title="Name" style="width:15%" >
			${list.empName}			
		</display:column>		
		<display:column title="From Designation-To Designation" style="width:10%" ><div id="${list.designationID}">${list.designation}-${list.designationTo}</div></display:column>
		<display:column title="Current Designation" style="width:10%" ><div>${list.currentDesignation}</div></display:column>				
		<display:column title="Department" style="width:15%" ><div id="${list.departmentID}">${list.department}</div></display:column>				
		<display:column title="Status" style="width:10%" >${list.promotionStatus}</display:column>	
		
		<display:column title="Promotion Date" style="width:10%;text-align:right" ><fmt:formatDate pattern="dd-MMM-yyyy" value="${list.promotionDate}"/></display:column>
		<display:column title="Variable Incr St Date" style="width:10%;text-align:right" ><fmt:formatDate pattern="dd-MMM-yyyy" value="${list.effectiveDate}"/></display:column>				
		<display:column title="Variable Incr" style="width:10%;text-align:right" >${list.variableIncr}</display:column>
		<display:column title="Ending Date" style="width:10%;text-align:right" ><fmt:formatDate pattern="dd-MMM-yyyy" value="${list.endingDate}"/></display:column>				
		<c:if test="${type == 2}">			
			<display:column title="Reservation" style="width:15%" >${list.reservation}</display:column>					
		</c:if>
		<display:column title="Edit" style="width:15%">
			<c:if test="${list.promotionStatus == 'Accepted'}">
				<input type="button" value="Edit" disabled="disabled"/>
			</c:if>
			<c:if test="${list.promotionStatus !='Accepted'}">
				<input type="button" value="Edit" onclick="javascript:enableCheckBox('<%=i %>')"/>
			</c:if>
		</display:column>
		<% i++; %>
	</display:table>
	<script>
		$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
	</script>
</div>
<!-- End : PromotedCandidatesList.jsp -->