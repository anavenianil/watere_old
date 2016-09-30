<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : LoanFestivalList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>

<div class="line"><jsp:include page="Result.jsp"></jsp:include></div>
<div>
<div id="Pagination">
		<display:table name="${sessionScope.loanFestivalList}" excludedParams="*" export="false" class="list" requestURI="" id="loan" pagesize="10" sort="list">
		    <display:column title="Festival Name" style="width:70%"> ${loan.festivalName}</display:column>
		    <display:column style="width:15%;text-align:center" title="<center>Edit</center>">
					<img src="./images/edit.gif" title="Edit"
						onclick="editLoanFestivalDetails('${loan.id}','${loan.festivalName}')" />
			</display:column>
			<display:column style="width:15%;text-align:center" title="<center>Delete</center>">
				<img src="./images/delete.gif" title="Delete"
					onclick="deleteLoanFestivalDetails('${loan.id}')" />
			</display:column>
		</display:table>
	</div>	
	<script>
		$jq( function(){$jq("#Pagination").displayTagAjax('paging');});
	</script>
</div>
<!-- End :LoanFestivalList.jsp-->