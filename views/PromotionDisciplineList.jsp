<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin :PromotionDisciplineList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div>
	<div>
	
	<c:if test="${message=='success' || Result=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
	
	<c:if test="${Result=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
	
	<c:if test="${Result=='detailsExist'}"> <span class="failure"><spring:message code="detailsExist"/></span></c:if>
	<c:if test="${Result=='delete'}"> <span class="failure"><spring:message code="delete"/></span></c:if>
	</div>
	<div>
		<div id="Pagination">
			<display:table name="${sessionScope.DisciplineList}" excludedParams="*"
				export="false" class="list" requestURI="" id="DisciplineList" pagesize="10"
				sort="list">
				<display:column title="Category Name" style="width:15%" sortable="true">&nbsp;${DisciplineList.categoryName}</display:column>
				<display:column title="Discipline Name" style="width:15%" sortable="true">&nbsp;${DisciplineList.name}</display:column>
				<display:column title="Discipline Code" style="width:15%">&nbsp;${DisciplineList.shortForm}</display:column>
				<display:column title="Description " style="width:40%">&nbsp;${DisciplineList.description}</display:column>				
				<display:column style="width:5%;text-align:center" title="Edit">
					<img src="./images/edit.gif"
						onclick="editPromotionDiscipline('${DisciplineList.id}','${DisciplineList.name}','${DisciplineList.shortForm}','${DisciplineList.description}','${DisciplineList.categoryID}')" />
				</display:column>
				<display:column style="width:5%;text-align:center" title="Delete">
					<img src="./images/delete.gif"
						onclick="deleteDisciplineDetails('${DisciplineList.id}')" />
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
<!-- End : PromotionDisciplineList.jsp -->