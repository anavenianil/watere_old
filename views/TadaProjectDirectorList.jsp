<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin :TadaProjectDirectorList.jsp -->
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
			<display:table name="${sessionScope.directorList}" excludedParams="*"
				export="false" class="list" requestURI="" id="project" pagesize="10"
				sort="list">
				<display:column title="Program Code" style="width:30%" sortable="true" >&nbsp;${project.programName}</display:column>
				<display:column title="Project Name" style="width:30%" sortable="true" >&nbsp;${project.projectName}</display:column>
				<display:column title="Project Director SFID" style="width:30%">&nbsp;${project.sfID}</display:column>			
				<display:column style="width:5%;text-align:center" title="Edit">
					<img src="./images/edit.gif"
						onclick="editProjectDirector('${project.id}','${project.projectName}','${project.sfID}','${project.programCode}')" />
				</display:column>
				<display:column style="width:5%;text-align:center" title="Delete">
					<img src="./images/delete.gif"
						onclick="deleteProjectDirector('${project.id}')" />
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
<!-- End : TadaProjectDirectorList.jsp -->