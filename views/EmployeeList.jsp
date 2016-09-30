<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : RoleInstanceList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div class="line">
         <c:if test="${message eq 'success'}">
    		<div class="myStyle success""><spring:message code="empmap.success"/></div>
        </c:if>
        <c:if test="${message eq 'update'}">
			<div class="myStyle success"><spring:message code="empmap.update"/></div>
        </c:if>
        <c:if test="${message eq 'delete'}">
			<div class="myStyle success"><spring:message code="empmap.delete"/></div>
        </c:if> 
        <c:if test="${message eq 'failure'}"> 
            <div class="myStyle failure"><spring:message code="empmap.failure"/></div>
       </c:if>
</div>
<div>
		<aa:zone name="employeeTable">
			<display:table name="${sessionScope.employeesList}" excludedParams="*"
				export="false" class="list" requestURI="" id="employeeList" pagesize="10"
				sort="list">
				<display:column style="width:5%"><input type="checkbox"  value="${employeeList.id}#${employeeList.sfid}#${employeeList.officeId}"/></display:column>				
				<display:column title="Employee Name" style="width:25%">${employeeList.empName}</display:column>
				<display:column title="Designation" style="width:25%">${employeeList.designationName}</display:column>
				<display:column title="DIR/PD/AD/TD/PROJ/DIV" style="width:30%">${employeeList.divisionName}</display:column>
				<display:column title="Reporting To" style="width:25%">${employeeList.parentName}</display:column>
			</display:table>
		</aa:zone>
		<script>
			JsonInstanceList= <%= (net.sf.json.JSONArray)session.getAttribute("employeesList") %>;
			displayPaging("employeeTable","employeeList",'employee');
			divisionList= <%= (net.sf.json.JSONArray)session.getAttribute("employees") %>;	
		</script>
</div>