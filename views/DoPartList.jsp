<!-- Beging: DoPartList.jsp -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ page import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.beans.dto.DoPartDTO"%>
<div class="line">
	<jsp:include page="RequestResult.jsp"></jsp:include>
</div>
<div class="line">	
	<div id="Pagination">
		<display:table name="${sessionScope.doPartList}" excludedParams="*"
			export="false" class="list" requestURI="" id="doPartList" pagesize="10"
			sort="list">		
			<display:column title="Designation Type" style="width:12%">&nbsp;<c:choose >
			<c:when test="${doPartList.typeId==1}">Gazetted</c:when>
			<c:when test="${doPartList.typeId==6}">Non Gazetted</c:when>
			<c:when test="${doPartList.typeId==7}">Ministerial</c:when>
			<c:when test="${doPartList.typeId==9}">Non Gazetted-S</c:when>
			<c:otherwise>Gazetted Technical</c:otherwise>
			</c:choose>
			</display:column>
			<display:column title="DOPart No" style="width:5%"> ${doPartList.doPartNumber}</display:column>
			<display:column title="DOPart Date" style="width:7%">&nbsp;<fmt:formatDate value="${doPartList.doPartDate}" pattern="dd-MMM-yyyy" /></display:column>
			<display:column title="Last DOPart No" style="width:7%">&nbsp;${doPartList.preDoPartNo}</display:column>
			<display:column title="Last DOPart Date" style="width:7%">&nbsp;<fmt:formatDate value="${doPartList.preDoPartDate}" pattern="dd-MMM-yyyy" /></display:column>
			<display:column title="Description" style="width:30%">${doPartList.description}</display:column>
			<display:column title="Distribution" style="width:30%">${doPartList.distribution}</display:column>
			
			<display:column title="Edit" style="width:3%;text-align:center">
			<c:if test="${doPartList.status == '1'}" >
			<img src="./images/edit.gif" title="Edit" 
			 onclick="editDOPartMasterDetails('${doPartList.id}')" /> 
			  
			</c:if>	
			</display:column>	
			<display:column style="width:3%;text-align:center" title="Delete">
			<c:if test="${doPartList.status == '1'}" >
			<img src="./images/delete.gif" title="Delete"
				onclick="deleteDOPartMasterDetails('${doPartList.id}')" />
				</c:if>	
			</display:column>		
			</display:table>
	</div>	
</div>
<script>
	$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
	jsonDoPartList=<%=(net.sf.json.JSONArray)JSONSerializer.toJSON((List<DoPartDTO>)session.getAttribute("doPartList")) %>;
</script>
<!-- End: DoPartList.jsp -->