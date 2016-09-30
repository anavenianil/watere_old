<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin :TypeDesignationList.jsp -->
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
			<display:table name="${sessionScope.TypeDesignationList}" excludedParams="*"
				export="false" class="list" requestURI="" id="typeDesigList" pagesize="10"
				sort="list">
				<display:column title="Type" style="width:15%;" sortable="true">&nbsp;${typeDesigList.name}</display:column>
				<display:column title="Desigantion" style="width:55%">&nbsp;${typeDesigList.designations}</display:column>
				<display:column title="Remarks" style="width:22%">&nbsp;${typeDesigList.remarks}</display:column>
				<display:column style="width:4%;text-align:center" title="Edit">
					<img src="./images/edit.gif"
						onclick="editTypeDesigDetails('${typeDesigList.id}','${typeDesigList.typeId}','${typeDesigList.remarks}','${typeDesigList.designationIds}','${typeDesigList.nodeIds}',designationListJSON,deselectedDesig)" />
				</display:column>
				<display:column style="width:4%;text-align:center" title="Delete">
					<img src="./images/delete.gif"
						onclick="deleteTypeDesigDetails('${typeDesigList.typeId}')" />
				</display:column>
			</display:table>
		
		</div>
		<script>
			designationListJSON = <%= (net.sf.json.JSONArray)session.getAttribute("designationListJSON") %>;
			deselectedDesig = <%= (net.sf.json.JSONArray)session.getAttribute("deselectedDesig") %>;
			$jq(function() {
					$jq("#Pagination").displayTagAjax('paging');
			});
		</script>
	</div>
</div>
<!-- End : TypeDesignationList.jsp -->