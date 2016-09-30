<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin :TypeMasterList.jsp -->
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
				<display:table name="${sessionScope.TaskHolderDetailsList}" excludedParams="*"
				export="false" class="list" requestURI="" id="taskHolderList" pagesize="10"
				sort="list">
				<display:column title="Type" style="width:30%" sortable="true">&nbsp;${taskHolderList.typeList.name}</display:column>
				<display:column title="Role" style="width:30%" sortable="true">&nbsp;${taskHolderList.roleList.name}</display:column>
				<display:column title="Remarks" style="width:30%">&nbsp;${taskHolderList.remarks}</display:column>
				<display:column style="width:5%;text-align:center" title="Edit">
					<img src="./images/edit.gif"
						onclick="editTaskHolderDetails('${taskHolderList.id}','${taskHolderList.roleList.id}','${taskHolderList.remarks}','${taskHolderList.typeList.id}')" />
				</display:column>
				<display:column style="width:5%;text-align:center" title="Delete">
					<img src="./images/delete.gif"
						onclick="deleteTaskHolderDetails('${taskHolderList.id}')" />
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
<!-- End : TypeMasterList.jsp -->