<!-- Begin : QualifiedCandidatesList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<div id="resultMsg">
	<jsp:include page="Result.jsp"></jsp:include>
</div>

<div id="Pagination">
	<%int i=1; %>
	<display:table name="${sessionScope.assessmentList}" excludedParams="*"
		export="false" class="list" requestURI="" id="list" pagesize="1000"
		sort="list" >
		<display:column  style="width:3%;vertical-align:middle" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxPromotionCheckAll(this.name,\'row\');"/>'>
			<c:if test="${list.interviewDate == null}">
				
				<input type="checkbox" class="row" name="row" value="${list.residencyPeriodId}" id="check<%=i %>" onclick="javascript:enableCheckBox1('<%=i %>','${list.residencyPeriodId}')"/>
			</c:if>
			<c:if test="${list.interviewDate != null}">
				<input type="checkbox" class="row" name="row" id="check<%=i %>"  disabled="disabled"/>
			</c:if>	
					
		</display:column>
		
		<display:column title="SFID" style="width:5%;" sortable="true">
			${list.sfID}			
		</display:column>
		<display:column title="Name" style="width:10%;" sortable="true">
			${list.empName}			
		</display:column>
		<display:column title="DesignationFrom -DesignationTo" style="width:20%;" sortable="true"><div id="${list.designationID}">${list.designation} - ${list.designationTo}</div></display:column>				
		<display:column title="Department" style="width:7%;" sortable="true"><div id="${list.departmentID}">${list.department}</div></display:column>				
		<display:column title="No of Attempts" style="width:2%;" sortable="true">
			${list.attempts}			
		</display:column>
		<display:column title="Interview Date" style="width:5%;" sortable="true"><fmt:formatDate pattern="dd-MMM-yyyy" value="${list.interviewDate}"/></display:column>				
		<display:column title="Venue" style="width:10%" sortable="true">${list.venue}</display:column>	
		<c:if test="${type == 1}">			
			<display:column title="Lab Representative" style="width:5%;" sortable="true">${list.labRepresentative}</display:column>	
		</c:if>	
		<c:if test="${type == 2}">			
			<display:column title="Board" style="width:5%;" sortable="true">${list.board}</display:column>					
		</c:if>
		<display:column title="Edit" style="width:5%">
			<c:if test="${list.interviewDate == null}">
				<input type="button" value="Edit" disabled="disabled"/>
			</c:if>
			<c:if test="${list.interviewDate != null  && list.designationID == list.currentDesignation }">
			     <input type="hidden" name="residencyPeriodId"  id="residencyPeriodId<%=i%>"/ value="${list.residencyPeriodId}">
				<input type="button" value="Edit" onclick="javascript:enableCheckBox1('<%=i %>','${list.residencyPeriodId}')"/>
			</c:if>
		</display:column>
		<%i++; %>
	</display:table>
	<c:if test="${not empty sessionScope.residencyPeriodList}">
	<script>
		residencyIdJSON = <%= (net.sf.json.JSONArray)session.getAttribute("residencyPeriodList") %>;
	 	$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
	</script>
	</c:if>
</div>
<!-- End : QualifiedCandidatesList.jsp -->