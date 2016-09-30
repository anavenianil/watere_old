<!-- Start : LoanFundReliefList.jsp -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<div class="line"><jsp:include page="Result.jsp"></jsp:include></div>
<div id="Pagination">
	<display:table name="${LoanFundReliefList}" excludedParams="*"
		export="false" class="list" requestURI="" id="dataList" pagesize="1"
		sort="list">
		<display:column title="SFID" style="width:15%;">${dataList.sfID}</display:column>
		<display:column title="Date of Death" style="width:25%;">${dataList.dateOfDeath}</display:column>
		<display:column title="Relief Amount" style="width:25%;text-align:right">&nbsp;${dataList.reliefAmount}</display:column>
		<display:column title="Approved By" style="width:15%;">${dataList.approvedBy}</display:column>

		<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit"
				onclick="javascript:editFundDetails('${dataList.id}')" />
		</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete"
				onclick="javascript:deleteFundDetails('${dataList.id}')" />
		</display:column>
	</display:table>
</div>

<script>
		       $jq( function(){
				   $jq("#Pagination").displayTagAjax('paging');
				})
					
	var JSONLoanFundList=<%=(net.sf.json.JSONArray) request.getAttribute("JSONLoanFundList")%>;
</script>




<!-- End : LoanFundReliefList.jsp -->