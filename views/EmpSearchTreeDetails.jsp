<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : EmpSearchTreeDetails.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<script type="text/javascript" src="script/jquery.treeTable.js"></script>

<div class="line">
<table id="empSearchTable" class="treelist" cellpadding="4" cellspacing="0" border="0">
	<thead>
		<tr>
		    <th>Organization Role</th>
			<th>SFID</th>
			<th>Name</th>
			<th>Designation</th>
			<th>Mobile Number</th>
			<th>Internal Number</th>
			<th>DIR/PD/AD/TD/PROJ/DIV</th>
			<th>Role Level</th>
		</tr>
	</thead>
	<tbody>
<c:forEach var="empSearchData" items="${empSearchList}" varStatus="i" begin="0">
<tr>
<c:if test="${empSearchData.level eq '1'}">
	<tr id="empnode-${empSearchData.departmentId}" class="odd">
</c:if>
<c:if test="${empSearchData.level ne '1'}">
	<c:if test="${empSearchData.directorateName ne 'Employee'}">
		<c:if test="${i.count%2 eq 0}">
			<tr id="empnode-${empSearchData.departmentId}" class="child-of-empnode-${empSearchData.parent} even">
		</c:if>
		<c:if test="${i.count%2 ne 0}">
			<tr id="empnode-${empSearchData.departmentId}" class="child-of-empnode-${empSearchData.parent} odd">
		</c:if>
	</c:if>
	<c:if test="${empSearchData.directorateName eq 'Employee'}">
		<c:if test="${i.count%2 eq 0}">
			<tr class="child-of-empnode-${empSearchData.departmentId} even">
		</c:if>
		<c:if test="${i.count%2 ne 0}">
			<tr class="child-of-empnode-${empSearchData.departmentId} odd">
		</c:if>
	</c:if>
</c:if>
   		<c:if test="${empTreeIdentity eq 'yes' || empSearchData.empTreeIdentity eq 'yes'}">
			
			<c:choose><c:when  test = "${not empty empSearchData.divisionName}" >
			<td><b>${empSearchData.divisionName}</b> </td>
			
			</c:when>    <c:otherwise>
			<td></td>
			</c:otherwise>   </c:choose>
			
			<td><a  href="javascript:getTreeEmpDetails('${empSearchData.sfid}','${empSearchData.empName}','${empSearchData.empTreeIdentity}');">${empSearchData.sfid}</a></td>
			
		
		
		
		
		</c:if>
		<c:if test="${empTreeIdentity ne 'yes' && empSearchData.empTreeIdentity ne 'yes'}">
			 <a href="javascript:getEmployeeSearchDetailsList('${empSearchData.sfid}','${empTreeIdentity}')">${empSearchData.sfid}</a>
		</c:if>
		<td>${empSearchData.empName}</td>
		<td>${empSearchData.designationName}</td>
		<td>${empSearchData.personalNumber}</td>
		<td>${empSearchData.internalNo}	</td>
		<td>${empSearchData.officeName}</td>
		<td>${empSearchData.directorateName}</td>	
	</tr>
</c:forEach>
</tbody>
</table>
</div>
<script>
	//displayPaging("empSearchTable","empSearchData");
	$jq(document).ready(function(){
		$jq("#empSearchTable").treeTable({
		});
	});
</script>
<!-- End : EmpSearchList.jsp -->