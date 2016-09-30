<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin :GPFSubTypeMasterList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<div class="line"><jsp:include page="Result.jsp"></jsp:include></div>
<div>
	<div id="Pagination">
		<display:table name="${sessionScope.gpfRulesList}" excludedParams="*" export="false" class="list" requestURI="" id="loan" pagesize="10" sort="list">
		    <display:column title="Loan Type" style="width:20%">${loan.loanTypeDetails.loanName}</display:column>
			<display:column title="Purpose" style="width:40%">${loan.purpose}</display:column>
			<display:column title="Rule" style="width:30%"> ${loan.rule}</display:column>
			<display:column style="width:5%;text-align:center" title="<center>Edit</center>">
					<img src="./images/edit.gif" title="Edit"
						onclick="editGPFSubTypeDetails('${loan.id}','${loan.loanTypeDetails.id}','${loan.purpose}','${loan.rule}')" />
			</display:column>
			<display:column style="width:5%;text-align:center" title="<center>Delete</center>">
				<img src="./images/delete.gif" title="Delete"
					onclick="deleteGPFSubTypeDetails('${loan.id}')" />
			</display:column>
		</display:table>
	</div>	
	<script>	
		$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
	</script>
</div>
<!-- End :GPFSubTypeMasterList.jsp-->
