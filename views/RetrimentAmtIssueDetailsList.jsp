<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin :RetrimentAmtIssueDetailsList.jsp -->
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
			<display:table name="${sessionScope.retrimentBenfitAmtIssueList}" excludedParams="*"
				export="false" class="list" requestURI="" id="retrimentList" pagesize="1000"
				sort="list">
				<display:column title="Employee ID" style="width:20%" sortable="true" >&nbsp;<a href="javascript:retrimentEmpDetails('${retrimentList.id}')"><font color="blue">${retrimentList.id }</font></a></display:column>
				<display:column title="Name" style="width:20%">&nbsp;${retrimentList.empName}</display:column>
				<display:column title="Joining Type" style="width:20%;text-align:right">&nbsp;${retrimentList.empType}</display:column>
				<display:column title="Total Recevied Amt" style="width:10%;text-align:right">&nbsp;${retrimentList.totAmt}</display:column>
				<display:column title="Retriment Date" style="width:10%">
				<fmt:formatDate pattern="dd-MMM-yyyy" value="${retrimentList.retrimentDate}" />
				</display:column>
			</display:table>
		</div>
		<script>
			$jq( function(){
					$jq("#Pagination").displayTagAjax('paging');
				})
		</script>
	</div>
</div>
<!-- End :RetrimentAmtIssueDetailsList.jsp-->