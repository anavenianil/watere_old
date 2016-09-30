<!-- Begin : RetirementReportDetailsList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>

<div>
	
	<div>
		<div id="Pagination">
			<display:table name="${sessionScope.retirementReportDetailsList}" excludedParams="*"
				export="false" class="list" requestURI="" id="dataList" pagesize="10"
				sort="list">
													
					<display:column title="Employee" style="width:30%">${dataList.userSfid}-${dataList.nameInServiceBook}</display:column>
					<display:column style="width:10%;text-align:center" title="Draft"><a href="javascript:getRetirementReport('${dataList.userSfid}','draft')">Reports</a></display:column>
					<display:column style="width:10%;text-align:center" title="Confirmed"><a href="javascript:getRetirementReport('${dataList.userSfid}','confirmed')">Reports</a></display:column>
					
			</display:table>
			</div>
		<script>
			$jq( function(){
				   $jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
				   
				})
			
			
		</script>
		
	</div>
</div>
<!-- End : RetirementReportDetailsList.jsp -->