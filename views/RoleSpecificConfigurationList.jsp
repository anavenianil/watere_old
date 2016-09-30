<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : RoleSpecificConfigurationList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.beans.dto.DesignationDTO,com.callippus.web.beans.dto.UserSpecificDTO,com.callippus.web.beans.dto.KeyValueDTO,com.callippus.web.beans.dto.RequestsDTO"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>

<div class="line">
	<c:if test="${message!='result'}">
		<div class="line">
			<c:if test="${Result=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
			<c:if test="${Result=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
			<c:if test="${Result=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
			<c:if test="${message=='delete' || Result=='delete'}"> <span class="failure"><spring:message code="delete"/></span></c:if>
		</div>
		<div class="line">
			<div id="Pagination">
				<display:table name="${sessionScope.userSpecificList}" excludedParams="*" export="false" class="list" requestURI="" id="delegateList" pagesize="10" sort="list" >
					<display:column title="Role Name" style="width:15%">${delegateList.roleName}</display:column>
					<display:column title="Request Type" style="width:15%">${delegateList.requestName}</display:column>
					<display:column title="Delegated Employee" style="width:20%">${delegateList.delegate}</display:column>
					<display:column title="Assigned Type" style="width:10%">${delegateList.assigned}</display:column>
					<display:column title="Gazetted Type" style="width:12%">&nbsp;${delegateList.gazettedType}</display:column>
					<display:column title="Designation" style="width:18%">&nbsp;${delegateList.designation}</display:column>
					<display:column title="Edit" style="width:5%;text-align:center">
						<img src="./images/edit.gif" title="Edit"
							onclick="javascript:editRoleSpecific('${delegateList.uniqueID}','${delegateList.roleMapId}','${delegateList.requestTypeIdValue}','${delegateList.delegateUser}','${delegateList.assignedType}','${delegateList.gazettedType}','${delegateList.designationID}')" />
					</display:column>
					<display:column title="Delete" style="width:5%;text-align:center">
						<img src="./images/delete.gif" title="Delete"
							onclick="javascript:deleteRoleSpecific('${delegateList.uniqueID}')" />
					</display:column>
				</display:table>			
									
				<script>
					$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
					<%if(session.getAttribute("designationListJSON")!=null) { %>
					jsonArrayObject =<%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<DesignationDTO>)session.getAttribute("designationListJSON"))%>;
				    <%} %>
				    <%if(session.getAttribute("requestListjson")!=null) { %>
				     jsonArrayObject2=<%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<UserSpecificDTO>)session.getAttribute("requestListjson"))%>;
					<%} %>
				</script>
			</div>
		</div>	
	</c:if>	
	<%if(session.getAttribute("subOrdinatesList")!=null) { %>
		<script>
			subOrdinatesList =<%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<KeyValueDTO>)session.getAttribute("subOrdinatesList"))%>;
			multipleDesigList='${orgStructure.multipleDesignation}'; 
			multipleRequestList='${orgStructure.requestName}'; 
		</script>
	<%} %>
</div>
<!-- End : RoleSpecificConfigurationList.jsp -->