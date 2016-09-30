<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin :RetrimentDetailsList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<div>
	<div>
	<jsp:include page="Result.jsp"></jsp:include>
	</div>
	<div>
		<div id="Pagination">
			<display:table name="${sessionScope.retrimentBenfitList}" excludedParams="*"
				export="false" class="list" requestURI="" id="retrimentList" pagesize="1000"
				sort="list">
				<display:column title="Employee ID" style="width:20%" sortable="true" >&nbsp;${retrimentList.sfID}</display:column>
				<display:column title="Name" style="width:20%">&nbsp;${retrimentList.empName}</display:column>
				<display:column title="Joining Type" style="width:20%;text-align:right">&nbsp;${retrimentList.empType}</display:column>
				<display:column title="Total Recevied Amt" style="width:10%;text-align:right">&nbsp;${retrimentList.totAmt}</display:column>
				<display:column title="Retriment Date" style="width:10%">
				<fmt:formatDate pattern="dd-MMM-yyyy" value="${retrimentList.retrimentDate}" />
			</display:column>
				<display:column style="width:10%;text-align:center" title="Edit">
					<img src="./images/edit.gif"
						onclick="editRetrimentDetails('${retrimentList.id}','${retrimentList.sfID }','${retrimentList.empName }','${retrimentList.empType }','${retrimentList.noOfPerson }','<fmt:formatDate pattern="dd-MMM-yyyy" value="${retrimentList.retrimentDate}" />','${retrimentList.retrimentAmt }','${retrimentList.transportAmt }','${retrimentList.luggageAmt }','${retrimentList.noofTons }','${retrimentList.totAmt }')" /> 
				</display:column>
				<display:column style="width:10%;text-align:center" title="Delete">
					<img src="./images/delete.gif"
						onclick="deleteRetrimentDetails('${retrimentList.id}')" /> 
				</display:column>
				
			${retrimentList.retrimentDate}
			</display:table>
		</div>
		<script>
			$jq( function(){
					$jq("#Pagination").displayTagAjax('paging');
				})
		</script>
	</div>
</div>
<!-- End : RetrimentDetailsList.jsp -->