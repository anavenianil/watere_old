<!-- Begin : HeadQuarterPromotionList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<div id="resultMsg">
	<jsp:include page="Result.jsp"></jsp:include>
</div>
<div style="float:right;padding:0 10px; width:450px;  "><span style="font-weight: bold">Note: </span> Residency period is calculated w.r.t Date of Join DRDO for LDCE board Type in other cases Date of Present Grade</div>
<div id="Pagination">
	<%int i=0; %>
	<display:table name="${sessionScope.assessmentList}" excludedParams="*"
		export="false" class="list" requestURI="" id="list" pagesize="1000"
		sort="list" >
		<display:column title="SFID" style="width:7%" sortable="true">
			${list.sfID}			
		</display:column>
		<display:column title="Name" style="width:10%" sortable="true">
			${list.empName}			
		</display:column>
		<display:column title="From Designation-To Designation" style="width:300%" sortable="true"><div id="${list.designationID}">${list.designation}-${list.designationTo}</div></display:column>				
		
		<display:column title="Department" style="width:15%" sortable="true"><div id="${list.departmentID}">${list.department}</div></display:column>				
		<display:column title="Date of Present Grade" style="width:10%" sortable="true">&nbsp;	<fmt:formatDate pattern="dd-MMM-yyyy" value="${list.seniorityDate}"/></display:column>
		<display:column title="Date of Join DRDO" style="width:10%" sortable="true">&nbsp;	<fmt:formatDate pattern="dd-MMM-yyyy" value="${list.doj_drdo}"/></display:column>				
		<display:column title="Residency Period" style="width:5%" sortable="true">${list.desigExperince}</display:column>				
		<display:column title="EOL Without MC" style="width:5%" sortable="true"><div >${list.eolWomc}</div></display:column>				
		<display:column title="Discipline" style="width:16%" sortable="true">
			<select name="disciplineID" id="disciplineID<%=i %>" style="width:90%">
				<option value="0">Select</option>
				<c:forEach var="subDisciplineList" items="${subDisciplineList}">
					 <option value="${subDisciplineList.id}">${subDisciplineList.name}</option>
				</c:forEach>
			</select>
			<script>
				$jq('#disciplineID'+<%=i %>).val(${list.disciplineID});			
			</script>				
		</display:column>		
		<display:column title="Sub Discipline" style="width:7%" sortable="true">
			<select name="subDisciplineID" id="subDisciplineID<%=i %>" >
				<option value="0">Select</option>
				<c:forEach var="DisciplineList" items="${DisciplineList}">
					 <option value="${DisciplineList.id}">${DisciplineList.name}</option>
				</c:forEach>
			</select>
			<script>
				$jq('#subDisciplineID'+<%=i %>).val(${list.subDisciplineID});			
			</script>				
		</display:column>		
		
		<display:column title="No of Attempts" style="width:5%;text-align:left" sortable="true">
		   <input type="hidden" name="residencyPeriodId"  id="residencyPeriodId<%=i%>"/ value="${list.residencyPeriodId}">
			<input type="text" name="desigAttempts" id="desigAttempts<%=i %>" value="${list.attempts}" size="3" onkeypress="return checkFloat(event,'desigAttempts<%=i %>');"/>
		</display:column>
		
		<display:column title="Status" style="width:5%" sortable="true">	
			<select name="statusID" id="statusID<%=i %>" >
				<option value="0">Select</option>
				<option value="58">Eligible</option>
			</select>
			<script>
				$jq('#statusID'+<%=i %>).val(${list.status});			
			</script>
		</display:column>
		<% i++; %>
	</display:table>
	
	<script>
		$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
	</script>
	        
</div>
<!-- End : HeadQuarterPromotionList.jsp -->