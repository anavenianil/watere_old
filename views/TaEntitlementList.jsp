<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin :TaEntitlementList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div>
    <div class="line" id="result">
		<jsp:include page="Result.jsp"></jsp:include>								
	</div>
	<div id="Pagination">
			<display:table name="${sessionScope.taEntitleJSON}" excludedParams="*"
				export="false" class="list" requestURI="" id="taEntitleClassList" pagesize="5"
				sort="list">
				<display:column title="Grade Pay" style="width:25%" sortable="true">${taEntitleClassList.gradePay}</display:column>
				<c:forEach var="travelTypeList" items="${sessionScope.travelTypeListJSON}">
				<c:if test="${travelTypeList.id==taEntitleClassList.entitleTypeId}">
				  <display:column title="Travel Type" style="width:25%" sortable="true">${travelTypeList.travelType}</display:column>
				</c:if>
				</c:forEach>
				<display:column style="width:5%;text-align:center" title="Edit">
					<img src="./images/edit.gif"
						onclick="editEntitlement('${taEntitleClassList.gradePay}','${taEntitleClassList.entitleTypeId}');hilightRow(this);" />
				</display:column>
				<display:column style="width:5%;text-align:center" title="Delete">
					<img src="./images/delete.gif"
						onclick="deleteEntitlement('${taEntitleClassList.gradePay}','${taEntitleClassList.entitleTypeId}')" />
				</display:column>
				
				
			</display:table>
		</div>
		<script>
			$jq( function(){
					$jq("#Pagination").displayTagAjax('paging');
				})
		</script>
	<script>
	    travelTypeMapDetailsJSON= <%= (net.sf.json.JSONArray) session.getAttribute("travelTypeMapDetailsJSON") %>;
	    gradePayListMapJSON = <%= (net.sf.json.JSONArray) session.getAttribute("gradePayListJSON") %>;
	    travelTypeListMapJSON = <%= (net.sf.json.JSONArray) session.getAttribute("travelTypeListJSON") %>;
	    entitleClassListJSON = <%= (net.sf.json.JSONArray) session.getAttribute("entitleClassListJSON") %>;
	    taEntitleClassListJSON = <%= (net.sf.json.JSONArray) session.getAttribute("taEntitleClassListJSON") %>;
	    taEntitleJSON = <%= (net.sf.json.JSONArray) session.getAttribute("taEntitleJSON") %>;
	</script>
</div>
<!-- End : TaEntitlementList.jsp -->