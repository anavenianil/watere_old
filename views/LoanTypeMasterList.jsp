<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : LoanTypeMasterList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.beans.dto.DesignationDTO"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<div class="line">
	<jsp:include page="Result.jsp"></jsp:include>
</div>
<div id="Pagination">
   	<display:table name="${sessionScope.LoanMasterList}" excludedParams="*" export="false" class="list" requestURI="" id="loanList" pagesize="10" sort="list">
		<display:column title="Loan/Advance Name" style="width:30%;">&nbsp;${loanList.loanName}</display:column>
		<display:column title="Type" style="width:20%;">&nbsp;
			<c:if test="${loanList.loanType=='46'}">Advance</c:if>
			<c:if test="${loanList.loanType=='45'}">Withdrawal</c:if>
			<c:if test="${loanList.loanType=='47'}">Both</c:if>
		</display:column>	
		<display:column style="width:5%;text-align:center" title="<center>Edit</center>">
			<img src="./images/edit.gif" title="Edit" onclick="editLoanTypeMaster('${loanList.id}','${loanList.loanName}','${loanList.loanType}')"/>
		</display:column>
		<display:column style="width:5%;text-align:center" title="<center>Delete</center>">
			<img src="./images/delete.gif" title="Delete" onclick="deleteLoanTypeMaster('${loanList.id}')" />
		</display:column>
	</display:table>
</div>
<script>
	$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})		
</script>

<!-- End : LoanTypeMasterList.jsp -->
