<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : LeaveBalanceVerifyList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa" %>

<div class="line">
	<div class="line">
		<jsp:include page="RequestResult.jsp"></jsp:include>
	</div>
	<div id="Pagination">
		<display:table name="${sessionScope.leaveBalanceList}" excludedParams="*"
			export="false" class="list" requestURI="" id="leaveList" pagesize="10" sort="list">	 		
			<display:column title="SFID" style="width:8%">&nbsp;${leaveList.sfID}</display:column>
			<display:column title="Name" style="width:25%" >${leaveList.empName}</display:column>
			<display:column title="CL" style="width:5%;text-align:right" >${leaveList.cl}</display:column>
			<display:column title="HPL" style="width:5%;text-align:right" >${leaveList.hpl}</display:column>
			<display:column title="EL" style="width:5%;text-align:right" >${leaveList.el}</display:column>
			<display:column title="LND" style="width:5%;text-align:right" >${leaveList.lnd}</display:column>
			<display:column title="CCL" style="width:5%;text-align:right" >${leaveList.ccl}</display:column>
			<display:column title="STL" style="width:5%;text-align:right" >${leaveList.stl}</display:column>
		<%-- 	 <display:column title="EOL with MC" style="width:12%;text-align:right" >${leaveList.eol}</display:column>  --%>
			<display:column title="EOL without MC" style="width:12%;text-align:right" >${leaveList.eolwomc}</display:column>
			<display:column title="Verified By" style="width:8%;">${leaveList.verifiedBy}</display:column>
			<display:column title="Edit" style="width:5%;text-align:center">
				<img src="./images/edit.gif" title="Edit" onclick="javascript:editLeaveBalance('${leaveList.id}','${leaveList.sfID}','${leaveList.cl}','${leaveList.hpl}','${leaveList.el}','${leaveList.lnd}','${leaveList.ccl}','${leaveList.stl}','${leaveList.verifiedBy}','${leaveList.encash}','${leaveList.cclyear}','${leaveList.cclservice}','${leaveList.slyear}','${leaveList.slservice}','${leaveList.eol}','${leaveList.eolwomc}','${leaveList.cmlwomc}','${leaveList.lndwomc}','${leaveList.eolInService}')"/>
			</display:column>
		</display:table>					
	</div>
	<script>
		$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
	</script>
</div>
<!-- End : LeaveBalanceVerifyList.jsp -->