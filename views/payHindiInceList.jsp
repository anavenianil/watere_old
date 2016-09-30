<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin :TypeMasterList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div>
	<div>
	<jsp:include page="Result.jsp"/>
	</div>
	<div>
		<div id="Pagination">
				<display:table name="${sessionScope.hindiInceList}" excludedParams="*" export="false" class="list" requestURI="" id="hindiList" pagesize="10">
				<display:column title="SFID" style="width:20%">${hindiList.sfid}</display:column>
				<display:column title="START MONTH" style="width:20%"><fmt:formatDate pattern="MMM-yyyy" value="${hindiList.startDate}"/></display:column>
				<display:column title="END MONTH" style="width:20%"><fmt:formatDate pattern="MMM-yyyy" value="${hindiList.endDate}"/></display:column>
				<display:column title="TOTAL INST" style="width:20%">${hindiList.totInst}</display:column>				
				<display:column title="PRESENT INST" style="width:20%">${hindiList.presentInst}</display:column>				
				<display:column style="width:5%;text-align:center" title="Edit">
					<img src="./images/edit.gif" onclick="editHindiDetails('${hindiList.id}','${hindiList.sfid}','<fmt:formatDate pattern="dd-MMM-yyyy" value="${hindiList.startDate}"/>','<fmt:formatDate pattern="dd-MMM-yyyy" value="${hindiList.endDate}"/>','${hindiList.presentInst}')"/>
				</display:column>
				<display:column style="width:5%;text-align:center" title="Delete">
					<img src="./images/delete.gif" onclick="deleteHindiDetails('${hindiList.id}')"/>
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
