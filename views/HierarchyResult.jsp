<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--Begin: HierarchyResult.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>

<c:if test="${sessionScope.resultType=='node'}">
	<script type="text/javascript">
		roleParentJSONList = <%= (net.sf.json.JSONArray)session.getAttribute("roleParentList") %>
		setNodeParentList();
		if(nodeParentID!=null && nodeParentID!="")
		document.getElementById('nodeParentName').value=nodeParentID;
	</script>
</c:if>
<c:if test="${sessionScope.resultType=='subInstance'}">
	<script type="text/javascript">
		subInstanceJSONList = <%= (net.sf.json.JSONArray)session.getAttribute("subInstanceJSONList") %>
		setNodeSubInstanceList();
	</script>
</c:if>
<c:if test="${hierarchy.internalRoleName ne ''}">
	<spring:bind path="hierarchy">
		<script type="text/javascript">
			setHeadValues('${hierarchy.internalRoleName}');
		</script>
	</spring:bind>
</c:if>
<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
<c:if test="${message=='success'}"> 
	<span class="success"><spring:message code="success"/></span>
	<script>setValues();</script>
</c:if>
<c:if test="${message=='invalid'}"> <span class="failure"><spring:message code="invalidsfid"/></span></c:if>
<c:if test="${message=='duplicate'}"> <span class="failure">Already Role is assigned</span></c:if>
<c:if test="${message=='empduplicate'}"> <span class="failure">Employee Already Mapped</span></c:if>
<c:if test="${message=='notexists'}"> <span class="failure">Head is not Available</span></c:if>
<c:if test="${message=='headexists'}"> <span class="failure">Head is already mapped.</span></c:if>
<c:if test="${message=='employee'}"> <span class="failure">Sorry you cannot map this employee to the role because <br/>he/she is an employee of other division.</span></c:if>
<c:if test="${message=='deleted'}"><span class="success">Head mapping deleted successfully.</span></c:if>
<c:if test="${message=='employeeexists'}"> <span class="failure">First map the employees under the Head to other head or User Specific Configuration is there for workflow<br/> then only you can delete this Head Mapping.</span></c:if>
<c:if test="${message=='UserSpecificConfiguration'}"> <span class="failure">Please remove User Specific Configuration for this head <br/> then only you can delete this Head Mapping.</span></c:if>

<c:if test="${sessionScope.resultType=='submit' or sessionScope.resultType=='subInstance' or sessionScope.resultType=='deletehead'}">
<div id="instanceList">
		<jsp:include page="RoleInstanceList.jsp"/>
</div>
</c:if>

<c:if test="${message eq 'employeesame'}">
	<script>
		if(confirm("Employee is working in other/same division.Do you want to delete as an employee and add as a head"))
		{
			instanceRoleMappingSubmit('${message}');
		}
	</script>
</c:if>
<!--End: HierarchyResult.jsp -->