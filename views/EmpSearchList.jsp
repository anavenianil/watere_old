<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : EmpSearchList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div>
<div id="Pagination">
   	<display:table name="${sessionScope.empSearchList}" excludedParams="*"
		export="false" class="list" requestURI="" id="empSearchData" pagesize="10"
		sort="list">
		<c:if test="${empTreeIdentity eq 'yes' || empSearchData.empTreeIdentity eq 'yes'}">
			<display:column  title='SFID' style="width:5%"><a href="javascript:getTreeEmpDetails('${empSearchData.sfid}','${empSearchData.empName}','${empSearchData.empTreeIdentity}');">${empSearchData.sfid}</a></display:column>		
		</c:if>
		<c:if test="${empTreeIdentity ne 'yes' && empSearchData.empTreeIdentity ne 'yes'}">
			<display:column  title='SFID' style="width:5%"><%-- <a href="javascript:getEmployeeSearchDetailsList('${empSearchData.sfid}','${empTreeIdentity}')"> --%>${empSearchData.sfid}<!-- </a> --></display:column>
		</c:if>
		<display:column  title='Name'  style="width:25%">${empSearchData.empName}</display:column>
		<display:column  title='Designation'   style="width:20%">${empSearchData.designationName}</display:column>
		<display:column  title='Mobile Number'  style="width:12%">&nbsp;${empSearchData.personalNumber}</display:column>
		<display:column  title='Internal Number'  style="width:8%">&nbsp;${empSearchData.internalNo}</display:column>	
		<display:column  title='DIR/PD/AD/TD/PROJ/DIV' style="width:20%">${empSearchData.officeName}</display:column>
		<display:column  title='Role'  style="width:10%">${empSearchData.directorateName}</display:column>		
	</display:table>
</div>
  <script>
		$jq( function(){
					$jq("#Pagination").displayTagAjax('paging');
				})
	</script>
</div>
<!-- End : EmpSearchList.jsp -->