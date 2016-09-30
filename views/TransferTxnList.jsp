<!--begin:TransfetTxnList.jsp -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<div>
<jsp:include page="Result.jsp"></jsp:include>
</div>
<div id="dataTable">
   	<display:table name="${sessionScope.TransferTxnList}" excludedParams="*"
		export="false" class="list" requestURI="" id="transfer" pagesize="10" sort="list">
		<display:column title="SFID" style="width:10%;text-align:left">&nbsp;${transfer.sfID}</display:column>
		<display:column title="FROM DEPT" style="width:20%;text-align:left">&nbsp;
		<c:forEach items="${DepartmentsList}" var="deptList">
		<c:if test="${deptList.id==transfer.fromDept}">${deptList.deptName}</c:if>
		</c:forEach>
		</display:column>
		<display:column title="TO DEPT" style="width:20%;text-align:left">&nbsp;
		<c:forEach items="${DepartmentsList}" var="deptList" >
		<c:if test="${deptList.id==transfer.toDept}">${deptList.deptName}</c:if>
		</c:forEach>
		</display:column>
		<display:column title="Assigned To" style="width:30%;text-align:left">&nbsp;
		<c:forEach items="${orgInstanceList}" var="orgInsList" >
		<c:if test="${orgInsList.id==transfer.assignedTo}">${orgInsList.name}</c:if>
		</c:forEach>
		</display:column>
		<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="editTransferTxnDetails('${transfer.sfID}','${transfer.fromDept}','${transfer.toDept}','${transfer.assignedTo}','<c:forEach items="${DepartmentsList}" var="deptList"><c:if test="${deptList.id==transfer.fromDept}">${deptList.deptName}</c:if></c:forEach>','${transfer.doPartId}')"/>
		</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="deleteTransferTxnDetails('${transfer.id}')" />
		</display:column>
	</display:table>
</div>

<script>
	clearTransferTxnDetails();
</script>
<!--End:TransfetTxnList.jsp -->