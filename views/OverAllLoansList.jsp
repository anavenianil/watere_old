<!-- Start : OverAllLoansList.jsp -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<div class="line"><jsp:include page="Result.jsp"></jsp:include></div>
<div id="Pagination">
	<display:table name="${OverAllLoansList}" excludedParams="*"
		export="false" class="list" requestURI="" id="dataList" pagesize="1"
		sort="list">
		<display:column title="Type of Advance" style="width:30%;">${dataList.name}</display:column>
		<display:column title="No of Applications in the waiting list"  style="width:30%;text-align:right">${dataList.noofRequests}</display:column>
		
		<display:column title="Amount Required" style="width:40%;text-align:right"><fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="2" value="${dataList.total}"/></display:column>
	</display:table>
</div>

<script>
			
				
				$jq( function(){
	 $jq("#Pagination").displayTagAjax('paging');
	})
			
			
</script>





<!-- End : OverAllLoansList.jsp -->