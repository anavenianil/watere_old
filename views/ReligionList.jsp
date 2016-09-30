<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : ReligionList.jsp -->
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
			<display:table name="${sessionScope.ReligionList}" excludedParams="*"
				export="false" class="list" requestURI="" id="ReligionList" pagesize="10"
				sort="list">
				<display:column title="Religion Name" style="width:30%" sortable="true">&nbsp;${ReligionList.name}</display:column>
				<display:column title="Minority " style="width:15%"><c:if test="${ReligionList.religion eq 'Y'}">Yes</c:if>
				<c:if test="${ReligionList.religion eq 'N'}">No</c:if></display:column>
				<display:column title="Description " style="width:20%">&nbsp;${ReligionList.description}</display:column>
				<display:column style="width:5%;text-align:center" title="Edit">
					<img src="./images/edit.gif"
						onclick="editReligion('${ReligionList.id}','${ReligionList.name}','${ReligionList.religion}','${ReligionList.description}')" />
				</display:column>
				<display:column style="width:5%;text-align:center" title="Delete">
					<img src="./images/delete.gif"
						onclick="deleteReligion('${ReligionList.id}')" />
				</display:column>
			</display:table>
		</div>
		<script>
			//displayPaging("desigTable","desigList");
			$jq( function(){
					$jq("#Pagination").displayTagAjax('paging');
				})
			religionId="";
		</script>
	</div>
</div>
<!-- End : ReligionList.jsp -->