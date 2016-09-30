<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin :PinList.jsp -->
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
		<c:if test="${message=='notexists'}"> <span class="failure"><spring:message code="notexists"/></span></c:if>
	</div>
	<div>
		<div id="Pagination">
			<display:table name="${sessionScope.PinList}" excludedParams="*"
				export="false" class="list" requestURI="" id="PinList" pagesize="10"
				sort="list">
				<display:column title="SFID" style="width:10%" sortable="true">&nbsp;${PinList.name}</display:column>
				<display:column title="NAME" style="width:20%" sortable="true">&nbsp;${PinList.description}</display:column>
				<display:column title="Pin Number" style="width:15%">&nbsp;${PinList.pinNumber}</display:column>				
				<display:column style="width:5%;text-align:center" title="Edit">
					<img src="./images/edit.gif"
						onclick="editPin('${PinList.id}','${PinList.name}','${PinList.pinNumber}')" />
				</display:column>
				
			</display:table>
		</div>
		<script>
			//displayPaging("desigTable","desigList");
			$jq( function(){
					$jq("#Pagination").displayTagAjax('paging');
				})
			pinId="";
		</script>
	</div>
</div>
<!-- End : PinList.jsp -->