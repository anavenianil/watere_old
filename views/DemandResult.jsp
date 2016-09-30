<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--Begin: DemandResult.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ page import="net.sf.json.JSONArray" %>

<c:if test="${sessionScope.resultType=='inventory'}">
	<script type="text/javascript">
		var inventoryHolder = <%= (net.sf.json.JSONObject)session.getAttribute("inventoryHolder") %>
		var accountHead=<%= (net.sf.json.JSONArray)session.getAttribute("accountHeadList") %>
		setInventoryHolderDetails(inventoryHolder);
		setAccountHeadNumbers(accountHead);
		$jq("#accountHeadId").val('${command.accountHeadId}');
	</script>
</c:if>

<c:if test="${sessionScope.resultType eq 'itemName' || sessionScope.resultType eq 'itemCode'}">
	<script type="text/javascript">
		itemsjson = <%= (net.sf.json.JSONArray)session.getAttribute("materialsJson") %>;
		//setMaterials(itemsjson,'${sessionScope.resultType}');
$jq(function(){	
	autosuggjson=setNameValuePairs(itemsjson);	
  if($jq("#search").val()=="itemName"){
	 autoX='Enter Material Name';
	 $jq("#materialCode input").autoSuggest(autosuggjson.items, {selectedItemProp: "name", searchObjProps: "name",selectionLimit :1,resultsHighlight:false,selectedValuesProp:"value",resultClick:function(){ setMaterialValues();}});
 }else{
	 autoX='Enter Material Code';
	 $jq("#materialCode input").autoSuggest(autosuggjson.items, {selectedItemProp: "value", searchObjProps: "value",selectionLimit :1,resultsHighlight:false,selectedValuesProp:"value",resultClick:function(){ setMaterialValues();}});
}
});	
</script>
</c:if>
<c:if test="${sessionScope.resultType=='draftDetails'}">
	<script>
		$jq("#inventoryNo").val('${command.inventoryNo}');
		$jq("#projectCode").val('${command.projectCode}');
		$jq("#demadTypeId").val('${command.demadTypeId}');		
		var inventoryHolder = <%= (net.sf.json.JSONObject)session.getAttribute("inventoryHolder") %>
		var accountHead=<%= (net.sf.json.JSONArray)session.getAttribute("accountHeadList") %>
		setInventoryHolderDetails(inventoryHolder);
		setAccountHeadNumbers(accountHead);
		$jq("#accountHeadId").val('${command.accountHeadId}');
		$jq("#groupDemandNo").html('${command.demandNo}');
		//$jq("#inventoryNo").attr("disabled","disabled");
		//$jq("#projectCode").attr("disabled","disabled");
		//$jq("#demadTypeId").attr("disabled","disabled");
		//$jq('#search').val("itemName");
		//searchValues(voucherType);	
	</script>
	<jsp:include page="DemandItemsList.jsp" />
</c:if>