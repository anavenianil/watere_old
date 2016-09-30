<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/jstl-fn" prefix="fn" %>
<style type="text/css">
	a:link {
	}
	a:visited {
	}
	a:hover {
		color: red;
	}
	a:active {
	}
</style>
<div id="Pagination">
	<display:table name="${sessionScope.healthInsuranceEmployeesList}" excludedParams="*" export="false" class="list" requestURI="" id="healthEmpList" pagesize="10" sort="list">
		<display:column title="EmployeeID" style="width:10%"><a href="javascript:applyInsuranceAmt('${healthEmpList.sfid}')">${healthEmpList.sfid}</a></display:column>
		<display:column title="Name" style="width:10%">${healthEmpList.fullName}</display:column>
		<display:column title="Designation" style="width:10%">${healthEmpList.designation}</display:column>
		<display:column title="Date Of Join" style="width:10%">
			<fmt:formatDate pattern="dd-MMM-yyyy"
				value="${healthEmpList.joiningDate}" />
		</display:column>
		<display:column title="Enrollment Date" style="width:10%">
			<fmt:formatDate pattern="dd-MMM-yyyy"
				value="${healthEmpList.fromDate}" />
		</display:column>
	</display:table>
</div>

