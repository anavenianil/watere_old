<!-- Begin : AllowanceEmployeeDetails.jsp -->
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
	<div id="hideEmployeesDetails">
	<div style="float:left;"  ><b>Details of Employees For Allowance</b></div><br>
		<div id="Pagination">
			<display:table name="${allowancesRequest.empList}" excludedParams="*"
				export="false" class="list" requestURI="" id="dataList" pagesize="1000"
				sort="list">
					<display:column  style="width:5%;text-align:center" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'row\');"/>'>
						<input type="checkbox" class="row" name="row" id="${dataList.userSfid}" onclick="checkBoxCheck(this.name);"/>
					</display:column>
					<display:column title="ID" style="width:8%">${dataList.userSfid}</display:column>
					<display:column title="Name" style="width:15%">${dataList.firstName} ${dataList.middleName} ${dataList.lastName}</display:column>
					<%-- <display:column title="DOB" style="width:8%">${dataList.userSfid}</display:column> --%>
					<display:column style="width:18%;text-align:center" title="Designation">${dataList.designationDetails.name}<%-- 		${dataList.designationDetails.id} --%></display:column>
				<%-- 	<display:column title="Directorate" style="width:20%">${dataList.directorate}</display:column>
					<display:column title="Division" style="width:30%">${dataList}</display:column> --%>
			</display:table>
		</div>
	</div>
</div>
<!-- End : AllowanceEmployeeDetails.jsp -->
