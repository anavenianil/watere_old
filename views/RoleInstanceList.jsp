<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : RoleInstanceList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div>
	<aa:zone name="instanceTable">
		<display:table name="${sessionScope.RoleInstanceList}" excludedParams="*"
			export="false" class="list" requestURI="" id="instanceList" pagesize="10"
			sort="list">
			<display:column title="SFID" style="width:10%">${instanceList.sfid}</display:column>
			<display:column title="Employee Name" style="width:30%">${instanceList.empName}</display:column>
			<display:column title="DIR/PD/AD/TD/PROJ/DIV" style="width:44%">${instanceList.divisionName}</display:column>
			<display:column style="width:8%;text-align:center" title="Edit">
				<img src="./images/edit.gif"
					onclick="editHead(JsonInstanceList,'${instanceList.instanceId}')" />
			</display:column>
			<display:column style="width:8%;text-align:center" title="Delete">
				<img src="./images/delete.gif"
					onclick="deleteHead('${instanceList.sfid}','${instanceList.instanceId}')" />
			</display:column>
		</display:table>
	</aa:zone>
	<script>
		JsonInstanceList= <%= (net.sf.json.JSONArray)session.getAttribute("RoleInstanceList") %>;
		displayPaging("instanceTable","instanceList",'hierarchy');
		divisionList= <%= (net.sf.json.JSONArray)session.getAttribute("divisions") %>;		
	</script>
</div>