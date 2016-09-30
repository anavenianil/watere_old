<!-- Begin : LTCMasterDataList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<%@page import="java.text.SimpleDateFormat"%><div>
	<div>
		<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
		<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
		<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
		<c:if test="${message=='delete'}"> <span class="failure"><spring:message code="delete"/></span></c:if>
		<c:if test="${message=='deletefail'}"> <span class="failure"><spring:message code="recordexists"/></span></c:if>
	</div>
	<div>
		<div id="Pagination">
			<display:table name="${sessionScope.jsonMasterDataList}" excludedParams="*"
				export="false" class="list" requestURI="" id="dataList" pagesize="10"
				sort="list">
				<c:choose>
					<c:when test="${sessionScope.masterType eq 'ltcBlockYear'}">
						<display:column title="LTC Block" style="width:20%">${dataList.ltcBlockMaster.name}</display:column>
						<display:column title="Start Date" style="width:20%">${dataList.fromDateOne}</display:column>
						<display:column title="End Date" style="width:20%">${dataList.toDateOne}</display:column>
						<display:column title="Extended Date" style="width:20%">${dataList.extendedDateOne}</display:column>
						<display:column style="width:10%;text-align:center" title="Edit">
							<img src="./images/edit.gif"
								id="masteListEdit" onclick="editLtcMaster(jsonMasterDataList,${dataList.id})"/>
						</display:column>
						<display:column style="width:10%;text-align:center" title="Delete">
							<img src="./images/delete.gif"
								onclick="deleteLtcMaster('${dataList.id}','${sessionScope.masterType}')" />
						</display:column>					
					</c:when>
					
					<c:when test="${sessionScope.masterType eq 'ltcPenalInterestMaster'}">
						<display:column titleKey="${sessionScope.masterType}.name" style="width:30%">${dataList.typeValue}</display:column>
						<display:column title="Description" style="width:20%">${dataList.description}</display:column>
						<display:column title="From Date" style="width:20%">${dataList.fromDateOne}</display:column>
						<display:column title="To Date" style="width:20%">${dataList.toDateOne}</display:column>
						<display:column style="width:10%;text-align:center" title="Edit">
							<img src="./images/edit.gif"
								id="masteListEdit" onclick="editLtcPenalMaster(jsonMasterDataList,${dataList.id})"/>
						</display:column>
						<display:column style="width:10%;text-align:center" title="Delete">
							<img src="./images/delete.gif"
								onclick="deleteLtcMaster('${dataList.id}','${sessionScope.masterType}')" />
						</display:column>	
									
					</c:when>
					
					<c:otherwise>
						<display:column titleKey="${sessionScope.masterType}.name" style="width:30%">${dataList.name}</display:column>
						<display:column title="Description" style="width:30%">${dataList.description}</display:column>
						<display:column style="width:10%;text-align:center" title="Edit">
							<img src="./images/edit.gif"
								id="masteListEdit" onclick="editLtcMaster(jsonMasterDataList,${dataList.id})"/>
						</display:column>
						<display:column style="width:10%;text-align:center" title="Delete">
							<img src="./images/delete.gif"
								onclick="deleteLtcMaster('${dataList.id}','${sessionScope.masterType}')" />
						</display:column>					
					</c:otherwise>
				</c:choose>
			</display:table>
		</div>
		<script>
			$jq( function(){
				   $jq("#Pagination").displayTagAjax('paging');
				})
			jsonMasterDataList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonMasterDataList") %>;
		</script>
	</div>
</div>
<!-- End : LTCMasterDataList.jsp -->