<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin :PromotionSubDisciplineList.jsp -->
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
			<display:table name="${sessionScope.subDisciplineList}" excludedParams="*"
				export="false" class="list" requestURI="" id="DisciplineList" pagesize="10"
				sort="list">
				<display:column title="Sub Discipline Name" style="width:20%" sortable="true">&nbsp;${DisciplineList.name}</display:column>
				<display:column title="Discipline Name" style="width:15%">&nbsp;${DisciplineList.disciplineDetails.name}</display:column>
				<display:column title="Sub Discipline Code" style="width:15%">&nbsp;${DisciplineList.shortForm}</display:column>
				<display:column title="Description " style="width:55%">&nbsp;${DisciplineList.description}</display:column>				
				<display:column style="width:5%;text-align:center" title="Edit">
					<img src="./images/edit.gif"
						onclick="editPromotionSubDiscipline('${DisciplineList.id}','${DisciplineList.disciplineDetails.id}','${DisciplineList.name}','${DisciplineList.shortForm}','${DisciplineList.description}')" />
				</display:column>
				<display:column style="width:5%;text-align:center" title="Delete">
					<img src="./images/delete.gif"
						onclick="deleteSubDisciplineDetails('${DisciplineList.id}')" />
				</display:column>
				
			</display:table>
		</div>
		<script>
			//displayPaging("desigTable","desigList");
			$jq( function(){
					$jq("#Pagination").displayTagAjax('paging');
				})
			disciplineId="";
		</script>
	</div>
</div>
<!-- End : PromotionSubDisciplineList.jsp -->