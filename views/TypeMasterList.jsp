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
				<display:table name="${sessionScope.TypeMasterList}" excludedParams="*"
				export="false" class="list" requestURI="" id="typeList" pagesize="10"
				sort="list">
				<display:column title="Name" style="width:25%">&nbsp;${typeList.name}</display:column>
				<display:column title="Prefix" style="width:25%">&nbsp;${typeList.prefixName}</display:column>
				<display:column title="Delimeter" style="width:25%">&nbsp;${typeList.delimeter}</display:column>
				<display:column title="Description " style="width:25%">&nbsp;${typeList.description}</display:column>				
				<display:column style="width:5%;text-align:center" title="Edit">
					<img src="./images/edit.gif"
						onclick="editTypeDetails('${typeList.id}','${typeList.name}','${typeList.prefixName}','${typeList.description}','${typeList.delimeter}')" />
				</display:column>
				<display:column style="width:5%;text-align:center" title="Delete">
					<img src="./images/delete.gif"
						onclick="deleteTypeDetails('${typeList.id}')" />
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