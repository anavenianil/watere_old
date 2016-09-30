<!-- Start : FinancialYearList.jsp -->
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
	<display:table name="${FinancialYearList}" excludedParams="*"
		export="false" class="list" requestURI="" id="dataList" pagesize="1"
		sort="list">
		<display:column title="Financial Year" style="width:15%;">${dataList.name}</display:column>
		<display:column title="Description" style="width:15%;">${dataList.description}</display:column>
		<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit"
				onclick="EditFinancialYearDetails('${dataList.id}','${dataList.name}','${dataList.description}')" />
		</display:column>
				
		<display:column style="width:5%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete"
				onclick="deleteFinancialYearDetails('${dataList.id}')" />
		</display:column>
	</display:table>
</div>

<script>
		$jq( function(){
	 $jq("#Pagination").displayTagAjax('paging');
	})		
	var yearList=<%=(net.sf.json.JSONArray) request.getAttribute("allYearList")%>;			
</script>




<!-- End : FinancialYearList.jsp -->