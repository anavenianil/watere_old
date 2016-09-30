<!-- begin:ReList.jsp -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" isELIgnored="false" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<div class="line">
	<jsp:include page="RequestResult.jsp"></jsp:include>
</div>
<div class="line">	
	<div id="Pagination">
<display:table	name="${sessionScope.designationList}" excludedParams="*" export="false"	class="list" requestURI="" id="dataList" pagesize="10" sort="list">
	<display:column title="Designation" style="width:50%;">${dataList.name}</display:column>
	<display:column title="Required Value" style="width:25%;">&nbsp;<input type="text" class="row" value="${dataList.reValue}" name="reValue" id="${dataList.id}" onkeypress="javascript:return checkInt(event);"/></display:column>
</display:table>
</div>
</div>
<script>
	$jq( function(){ $jq("#Pagination").displayTagAjax('paging');});
	designationList = <%= (net.sf.json.JSONArray)net.sf.json.JSONSerializer.toJSON(session.getAttribute("designationList")) %>;
</script>
<!-- End:ReList.jsp -->