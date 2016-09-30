<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:SelectedDivision.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
		<select name="divisionId" class="formSelect" id="divisionId" class="formSelect" disabled="disabled" onmouseover="setSelectWidth('#divisionId')" onchange="javascritp:checkHeadName();">
				<option value="Select">Select</option>
				<c:forEach items="${divisionList}" var="division">
					<option value="${division[0]}">${division[1]}</option>
				</c:forEach>
		</select>
<script type="text/javascript">
	if(document.getElementById('divisionId').options.length==2)
	document.getElementById('divisionId').options[1].selected=true
	ConfirmHeadOrEmp('${employee.headName}');
			</script>
<!-- end:SelectedDivision.jsp -->
