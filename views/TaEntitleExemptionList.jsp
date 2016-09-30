<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin :TaEntitleExemptionList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div>
	<div>
	<jsp:include page="Result.jsp"></jsp:include>
	</div>
	<div>
		<div id="Pagination">
			<display:table name="${tada.entitleExemptionList}" excludedParams="*"
				export="false" class="list" requestURI="" id="entitleExemptionList" pagesize="10"
				sort="list">
				<display:column title="SFID" style="width:10%" sortable="true" >&nbsp;${entitleExemptionList.sfID}</display:column>
				<display:column title="Program" style="width:20%">&nbsp;${entitleExemptionList.programName}</display:column>
				<display:column title="Project" style="width:20%">&nbsp;${entitleExemptionList.projectName}</display:column>
				<display:column title="Exemption Entitlement" style="width:20%">&nbsp;${entitleExemptionList.entitleTypeDetails.travelType}</display:column>
				<display:column title="Remarks" style="width:40%">&nbsp;${entitleExemptionList.remarks}</display:column>			
				<display:column style="width:5%;text-align:center" title="Edit">
					<img src="./images/edit.gif"
						onclick="editEntitleExemption('${entitleExemptionList.id}','${entitleExemptionList.sfID}','${entitleExemptionList.projectName}','${entitleExemptionList.entitleTypeId}','${entitleExemptionList.remarks}','${entitleExemptionList.programCode}')" />
				</display:column>
				<display:column style="width:5%;text-align:center" title="Delete">
					<img src="./images/delete.gif"
						onclick="deleteEntitleExemption('${entitleExemptionList.id}')" />
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
<!-- End : TaEntitleExemptionList.jsp -->