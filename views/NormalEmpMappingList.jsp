<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : NormalEmpMappingList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>

<div class="line">
	<div class="line">
		<c:if test="${Result=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
		<c:if test="${Result=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
		<c:if test="${Result=='invalid'}"> <span class="failure"><spring:message code="Invalid"/></span></c:if>
		<c:if test="${remarks ne null}"> <span class="failure"><br/>${remarks}
		<c:if test="${Result eq 'failure' && orgStructure.param eq 'submitNormalEmp' && orgStructure.mapSfID eq ''}">
		<script type="text/javascript">
			if(confirm('${remarks}'+' still do u want to transfer the employee?')) {
				checkValues('${orgStructure.empGroup}');
			}
		</script>
		</c:if>
		</span></c:if>		
	</div>
	<div class="line">
		<div id="Pagination">
			<display:table name="${sessionScope.normalEmployeesList}" excludedParams="*" export="false" class="list" requestURI="" id="empList" pagesize="10" sort="list">
				<display:column  style="width:5%;text-align:center" title='<input type=checkbox class="regular-checkbox" name="selectall" id="selectall" onclick="checkBoxCheckAllDefault(this.name,\'row\');"/><label for="selectall"></label>'>
					<input type="checkbox" class="row regular-checkbox" name="row" id="${empList.empSfid}" onclick="checkBoxCheckDefault(this,'row');"/>
					<label for="${empList.empSfid}"></label>
				</display:column>
				<display:column title="SFID" style="width:7%" sortable="true">${empList.empSfid}</display:column>
				<display:column title="Employee Name" style="width:25%" sortable="true">${empList.empName}</display:column>
				<display:column title="Designation" style="width:20%">${empList.designation}</display:column>
				<display:column title="Department" style="width:20%" sortable="true">${empList.department}</display:column>
				<display:column title="Reporting To" style="width:23%">${empList.parentName}</display:column>
				<display:column title="Default Role" style="width:23%"><c:choose> <c:when test=" ${empList.defaultStatus eq 'NO' && empList.defaultStatus == 'NO'}"><span> <b>${empList.defaultStatus}</b></span></c:when>
				<c:otherwise>
				<font color="green"><b> ${empList.defaultStatus} </b></font>
				</c:otherwise> </c:choose></display:column>
				
			</display:table>
			<script>
				$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
			</script>
		</div>
	</div>
</div>
<!-- End : NormalEmpMappingList.jsp -->