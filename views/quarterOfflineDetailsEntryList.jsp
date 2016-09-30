<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin :LicenceFeeChargesMasterList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ page import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.paybill.dto.PayBillQuartersTypeMasterDTO,"%>
<%@page import="com.callippus.web.paybill.dto.PayBillQuartersTypeMasterDTO"%>
<%@page import="com.callippus.web.paybill.dto.PayQuarterManagementDTO"%>
<%@page import="com.callippus.web.beans.quarterType.QuarterTypeBean"%>
<div class="line"><jsp:include page="Result.jsp"></jsp:include></div>
<div>
<div id="Pagination">
<display:table name="${sessionScope.empQuarterDetails}" excludedParams="*"
		export="false" class="list" requestURI="" id="tf" pagesize="1000"
		sort="list" cellpadding="2" cellspacing="1">
		<display:column title="SFID" style="width:0.6%;vertical-align:middle">${tf.sfid}</display:column>
		<display:column title="Quarter Type" style="width:2%;vertical-align:middle">${tf.quarterTypeDetails.quarterSubType}</display:column>
		<display:column title="Quarter No" style="width:2.5%;vertical-align:middle">${tf.quarterNo}</display:column>
		<display:column title="Occupied Date" style="width:0.8%;vertical-align:middle"><fmt:formatDate pattern="dd-MMM-yyyy" value="${tf.occupiedDate}"/></display:column>
		<display:column title="Vacation Date" style="width:0.8%;vertical-align:middle"><fmt:formatDate pattern="dd-MMM-yyyy" value="${tf.vacationDate}"/></display:column>
		<display:column style="width:0.5%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" 
			onclick="editQuarterOffline('${tf.id}','<fmt:formatDate pattern="dd-MMM-yyyy" value="${tf.occupiedDate}"/>','<fmt:formatDate pattern="dd-MMM-yyyy" value="${tf.vacationDate}"/>')" />
		</display:column>
		<display:column style="width:0.5%;text-align:center" title="Delete">
				<img src="./images/delete.gif" title="Delete"
					onclick="deleteQuarterOfflineDetails('${tf.id}')" />
			</display:column>	
		</display:table>
</div>	
<script>
		$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
</script>
<script>
		<% if (session.getAttribute("quarterTypeList")!=null ) { %>
			quarterTypeListJSON =<%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<PayBillQuartersTypeMasterDTO>)session.getAttribute("quarterTypeList"))%>;
		<%}%>
		<% if (session.getAttribute("quarterSubTypeList")!=null ) { %>
			quarterSubTypeListJSON =<%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<QuarterTypeBean>)session.getAttribute("quarterSubTypeList"))%>;
		<%}%>
		<% if (session.getAttribute("quarterOfflineDetailsEntryList")!=null ) { %>
			quarterOfflineDetailsJson =<%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<PayQuarterManagementDTO>)session.getAttribute("quarterOfflineDetailsEntryList"))%>;
		<%}%>
</script>
</div>
<!-- End :LicenceFeeChargesMasterList.jsp-->