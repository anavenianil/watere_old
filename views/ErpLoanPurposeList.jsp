<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : ErpLoanPurposeList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.beans.dto.DesignationDTO"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<div class="line">
	<jsp:include page="Result.jsp"></jsp:include>
</div>
<div id="Pagination">
   	<display:table name="${sessionScope.LoanMasterList}" excludedParams="*" export="false" class="list" requestURI="" id="loanList" pagesize="10" sort="list">
		<display:column title="Id" style="width:5%;">&nbsp;${loanList.id}</display:column>
		<display:column title="Loan Type" style="width:30%;">&nbsp;${loanList.loanType}</display:column>
		<display:column title="Created By" style="width:10%;">&nbsp;${loanList.createdBy}</display:column>
		<display:column title="Creation Date" style="width:15%;">&nbsp;<fmt:formatDate pattern="dd-MMM-yyyy" value="${loanList.creationTime}" /></display:column>
		<display:column title="Last Modified By" style="width:10%;">&nbsp;${loanList.lastModifiedBy}</display:column>
		<display:column title="Last Modified Date" style="width:15%;">&nbsp;<fmt:formatDate pattern="dd-MMM-yyyy" value="${loanList.lastModifiedTime}" /></display:column>
			
		<display:column style="width:5%;text-align:center" title="<center>Edit</center>">
			<img src="./images/edit.gif" title="Edit" onclick="editLoanPurpose('${loanList.id}','${loanList.loanType}')"/>
		</display:column>
		<display:column style="width:5%;text-align:center" title="<center>Delete</center>">
			<img src="./images/delete.gif" title="Delete" onclick="deleteLoanPurpose('${loanList.id}')" />
		</display:column>
	</display:table>
</div>
<script>
	$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})		
</script>

<!-- End : ErpLoanPurposeList.jsp -->
