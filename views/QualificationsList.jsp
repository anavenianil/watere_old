<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : QualificationsList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div>
	<div>
		<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
		<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
		<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
		<c:if test="${message=='deletefail'}"> <span class="failure"><spring:message code="recordexists"/></span></c:if>
	</div>
	<div>
		<div id="Pagination">
			<display:table name="${sessionScope.QualificationsList}" excludedParams="*"
				export="false" class="list" requestURI="" id="QualificationsList" pagesize="10"
				sort="list">
				<display:column titleKey="${sessionScope.masterType}.name" style="width:15%" sortable="true">&nbsp;${QualificationsList.name}</display:column>
				<display:column titleKey="${sessionScope.masterType}.shortForm" style="width:15%">&nbsp;${QualificationsList.shortForm}</display:column>	
				<display:column title="Professional " style="width:10%"><c:if test="${QualificationsList.flag eq 'Y'}">Yes</c:if>
				<c:if test="${QualificationsList.flag eq 'N'}">No</c:if></display:column>
				<display:column title="Description " style="width:50%">&nbsp;${QualificationsList.description}</display:column>
				<display:column style="width:5%;text-align:center" title="Edit">
					<img src="./images/edit.gif"
						onclick="editQual('${QualificationsList.id}','${QualificationsList.name}','${QualificationsList.shortForm}','${QualificationsList.description}','${QualificationsList.flag}')" />
				</display:column>
				<display:column style="width:5%;text-align:center" title="Delete">
					<img src="./images/delete.gif"
						onclick="deleteQual('${QualificationsList.id}')" />
				</display:column>
			</display:table>
		</div>
		<script>
			//displayPaging("desigTable","desigList");
			$jq( function(){
					$jq("#Pagination").displayTagAjax('paging');
				})
			qualificationId="";
		</script>
	</div>
</div>
<!-- End : QualificationsList.jsp -->