<!-- Begin : MMGMasterDataList.jsp -->
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
			<display:table name="${jsonMasterDataList}" excludedParams="*"
				export="false" class="list" requestURI="" id="dataList" pagesize="10"
				sort="list">
				<c:if test="${sessionScope.masterType eq 'accHead'}">
						<display:column title="Account Head Number" style="width:25%">${dataList.accountHeadNumber}</display:column>
						<display:column title="Division" style="width:25%">${dataList.deptName}</display:column>
						<display:column title="Alloted Fund Type" style="width:25%">${dataList.fundTypeName}</display:column>
						<display:column title="Alloted Fund" style="width:25%">${dataList.allottedFund}</display:column>
						<display:column title="Quantity Held" style="width:25%">${dataList.qtyHeld}</display:column>
						<display:column title="Quantity Required" style="width:25%">${dataList.qtyRequired}</display:column>
						<display:column title="Consumed Fund" style="width:25%">${dataList.consumedFund}</display:column>
						<display:column title="Committed Fund" style="width:25%">${dataList.commitedFund}</display:column>
						<display:column title="Financial Year" style="width:25%">${dataList.year}</display:column>
						
				</c:if>
				<c:if test="${sessionScope.masterType eq 'itemSubCategory'}">
						<display:column title="Item Category Name" style="width:25%">${dataList.categoryName}</display:column>
				</c:if>
				<c:if test="${sessionScope.masterType eq 'itemCode'}">
						<display:column title="Item Category Name" style="width:20%">${dataList.categoryName}</display:column>
						<display:column title="Item Sub Category Name" style="width:20%">${dataList.subCategoryName}</display:column>
				</c:if>
				<c:if test="${sessionScope.masterType eq 'itemSubCode'}">
						<display:column title="Item Code Name" style="width:20%">${dataList.itemCodeName}</display:column>
				</c:if>
				<c:if test="${sessionScope.masterType ne 'material' and sessionScope.masterType ne 'inventHolder' and sessionScope.masterType ne 'accHead'}">
					<display:column titleKey="${sessionScope.masterType}.name" style="width:30%">${dataList.name}</display:column>
				</c:if>
				<c:if test="${sessionScope.masterType eq 'material'}">
					<display:column titleKey="${sessionScope.masterType}.name" style="width:30%">${dataList.materialName}</display:column>
					<display:column title="Material Code" style="width:30%">${dataList.materialCode}</display:column>
					<display:column title="Item Category" style="width:30%">${dataList.categoryName}</display:column>
					<display:column title="Item Sub Category" style="width:30%">${dataList.subCategoryName}</display:column>
					<display:column title="Item Code" style="width:30%">${dataList.codeName}</display:column>
					<display:column title="Item Sub Code" style="width:30%">${dataList.subCodeName}</display:column>
					<display:column title="Item Company" style="width:30%">${dataList.companyName}</display:column>
					<display:column title="UOM Name" style="width:30%">${dataList.uomName}</display:column>
					
				</c:if>
				<c:if test="${sessionScope.masterType eq 'inventHolder'}">
					<display:column title="Inventory Number" style="width:30%">${dataList.inventoryNo}</display:column>
					<display:column title="SFID" style="width:30%">${dataList.sfid}</display:column>
					<display:column title="Directorate/Division" style="width:30%">${dataList.deptName}</display:column>
					<display:column title="Inventory For" style="width:30%">${dataList.inventoryHolderTypeName}</display:column>
					<display:column style="width:10%;text-align:center" title="Edit">
						<img src="./images/edit.gif"
							id="masteListEdit" onclick="editMMGInventory(jsonMasterDataList,${dataList.invId})"/>
					</display:column>
					<display:column style="width:10%;text-align:center" title="Delete">
						<img src="./images/delete.gif"
							onclick="deleteMMGMaster('${dataList.invId}','${sessionScope.masterType}')" />
					</display:column>					
				</c:if>
					<c:if test="${sessionScope.masterType eq 'taxType'}">
						<display:column title="Percentage (%)" style="width:20%">${dataList.percentage}</display:column>
					</c:if>
					<c:if test="${sessionScope.masterType eq 'itemCompany'}">
						<display:column title="Company Code" style="width:20%">${dataList.companyCode}</display:column>
					</c:if>
					<c:if test="${sessionScope.masterType eq 'itemCategory'}">
						<display:column title="Item Category Code" style="width:25%">${dataList.categoryCode}</display:column>
					</c:if>
					<c:if test="${sessionScope.masterType eq 'itemSubCategory'}">
						<display:column title="Item Sub Category Code" style="width:20%">${dataList.subCategoryCode}</display:column>
					</c:if>
					<c:if test="${sessionScope.masterType eq 'itemCode'}">
						<display:column title="Item Code" style="width:15%">${dataList.itemCode}</display:column>
					</c:if>
					<c:if test="${sessionScope.masterType eq 'itemSubCode'}">
						<display:column title="Item Sub Code" style="width:20%">${dataList.itemSubCode}</display:column>
					</c:if>
					<c:if test="${sessionScope.masterType ne 'inventHolder'}">
						<display:column title="Description" style="width:50%">${dataList.description}</display:column>
					</c:if>
					<c:if test="${sessionScope.masterType ne 'material' and sessionScope.masterType ne 'inventHolder'
					 and sessionScope.masterType ne 'accHead'}">
						<display:column style="width:10%;text-align:center" title="Edit">
							<img src="./images/edit.gif"
								id="masteListEdit" onclick="editMMGMaster(jsonMasterDataList,${dataList.id})"/>
						</display:column>
						<display:column style="width:10%;text-align:center" title="Delete">
							<img src="./images/delete.gif"
								onclick="deleteMMGMaster('${dataList.id}','${sessionScope.masterType}')" />
						</display:column>
					</c:if>
					<c:if test="${sessionScope.masterType eq 'material'}">
						<display:column style="width:10%;text-align:center" title="Edit">
							<img src="./images/edit.gif"
								id="masteListEdit" onclick="editMMGMaterial(jsonMasterDataList,'${dataList.materialCode}')"/>
						</display:column>
						<display:column style="width:10%;text-align:center" title="Delete">
							<img src="./images/delete.gif"
							onclick="deleteMMGMaster('${dataList.materialCode}','${sessionScope.masterType}')" />
						</display:column>
					</c:if>
					<c:if test="${sessionScope.masterType eq 'accHead'}">
						
						<display:column style="width:10%;text-align:center" title="Edit">
						<img src="./images/edit.gif"
							id="masteListEdit" onclick="editMMGAccHead(jsonMasterDataList,${dataList.accId})"/>
					 	</display:column>
						<display:column style="width:10%;text-align:center" title="Delete">
							<img src="./images/delete.gif"
								onclick="deleteMMGMaster('${dataList.accId}','${sessionScope.masterType}')" />
						</display:column>	
					</c:if>
			</display:table>
			</div>
		<script>
			$jq( function(){
				   $jq("#Pagination").displayTagAjax('home');
				})
			jsonMasterDataList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonMasterDataList") %>;
			//checkingFlagValue(jsonMasterDataList);
			invNum='${sessionScope.inventoryNo}';
			
		</script>
		
	</div>
</div>
<!-- End : MMGMasterDataList.jsp -->