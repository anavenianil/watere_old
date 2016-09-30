<!-- Begin : MasterDataList.jsp -->
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
		<c:if test="${message=='deletefail'}"> <span class="failure"><spring:message code="NotDeleted"/></span></c:if>
		<c:if test="${message=='delete'}"> <span class="failure"><spring:message code="delete"/></span></c:if>
		<c:if test="${message=='exist'}"> <span class="failure"><spring:message code="detailsExist"/></span></c:if>
	
	</div>
	<div>
		<div id="Pagination" style="float:right;width:100%;">
		<display:table name="${sessionScope.masterDataList}" excludedParams="*"
				export="false" class="list" requestURI="" id="dataList" pagesize="10"
				sort="list">
				<c:if test="${sessionScope.masterType == 'category' || sessionScope.masterType == 'subcategory'}">
						<display:column title="Order No" style="width:10%" >&nbsp;${dataList.orderNo}</display:column>
				</c:if>
				<display:column titleKey="${sessionScope.masterType}.name" style="width:30%" sortable="true">&nbsp;${dataList.name}</display:column>
				<c:if test="${sessionScope.level eq 'parent'}">
					<c:if test="${sessionScope.masterType == 'district'}">
						<display:column title="State Name" style="width:30%" >&nbsp;${dataList.state}</display:column>
						<display:column title="Description" style="width:20%">&nbsp;${dataList.description}</display:column>
						<display:column style="width:8%;text-align:center" title="Edit">
							<img src="./images/edit.gif"
								onclick="editMaster('${dataList.id}','${dataList.name}','${dataList.description}','${dataList.stateId}','','')" />
						</display:column>
					</c:if>						
					
					<c:if test="${sessionScope.masterType == 'subcategory'}">
						<display:column title="Category Name" style="width:20%" >&nbsp;${dataList.categoryName}</display:column>
						<display:column title="Description" style="width:20%">&nbsp;${dataList.description}</display:column>
						<display:column style="width:8%;text-align:center" title="Edit">
							<img src="./images/edit.gif"
								onclick="editMaster('${dataList.id}','${dataList.name}','${dataList.description}','${dataList.categoryID}','${dataList.orderNo}','${dataList.alias}')" />
						</display:column>
					</c:if>
					<c:if test="${sessionScope.masterType == 'subQuarter'}">
						<display:column title="Quarter Name" style="width:30%" >&nbsp;${dataList.parentName}</display:column>
						<display:column title="Description" style="width:20%">&nbsp;${dataList.description}</display:column>
						<display:column style="width:8%;text-align:center" title="Edit">
							<img src="./images/edit.gif"
								onclick="editMaster('${dataList.id}','${dataList.name}','${dataList.description}','${dataList.parentID}')" />
						</display:column>
					</c:if>
					<c:if test="${sessionScope.masterType == 'awardCategory'}">
						<display:column title="Organisation" style="width:30%" >&nbsp;${dataList.orgName}</display:column>
						<display:column title="Description" style="width:20%">&nbsp;${dataList.description}</display:column>
						<display:column style="width:8%;text-align:center" title="Edit">
							<img src="./images/edit.gif"
								onclick="editMaster('${dataList.id}','${dataList.name}','${dataList.description}','${dataList.parentID}','','')" />
						</display:column>
					</c:if>
					<c:if test="${sessionScope.masterType == 'disnumber'}">
						<display:column title="Dispensary Location" style="width:30%" >&nbsp;${dataList.dispensaryNumber}</display:column>
						<display:column title="Description" style="width:20%">${dataList.description}</display:column>
						<display:column style="width:8%;text-align:center" title="Edit">
							<img src="./images/edit.gif"
								onclick="editMaster('${dataList.id}','${dataList.name}','${dataList.description}','${dataList.parentID}','','')" />
						</display:column>
					</c:if>
				</c:if>
				
				<c:if test="${sessionScope.level eq 'sub'}">
					<display:column title="Description" style="width:50%">&nbsp;${dataList.description}</display:column>
					<display:column style="width:8%;text-align:center" title="Edit">
						<img src="./images/edit.gif"
							onclick="editMaster('${dataList.id}','${dataList.name}','${dataList.description}','','${dataList.orderNo}','${dataList.alias}')" />
					</display:column>
				</c:if>
				<display:column style="width:8%;text-align:center" title="Delete">
					<img src="./images/delete.gif"
						onclick="deleteMaster('${dataList.id}','${sessionScope.masterType}')" />
				</display:column>
			</display:table>
		
	</div>
		<script>
			//displayPaging("dataTable","dataList","paging");
			$jq( function(){
					$jq("#Pagination").displayTagAjax('paging');
				})
			var jsonMasterDataList=<%=(net.sf.json.JSONArray)request.getAttribute("jsonMasterDataList") %>;
			checkingFlagValue(jsonMasterDataList);
		</script>
		
	</div>
</div>
<!-- End : MasterDataList.jsp -->