<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--Begin: RoleParentList.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<script type="text/javascript" src="script/jquery.js"></script>
<form:form method="post" commandName="hierarchy">
<div>
<div class="quarter">Reports To</div>
<div class="quarter">
	<form:select path="nodeParentName" id="nodeParentName" cssClass="formSelect" onmouseover="setSelectWidth('#nodeParentName')">
		<form:option value="select">Select</form:option>
		<form:options items="${roleParentList}" itemValue="roleID" itemLabel="roleName"/>
	</form:select>
</div>
<script>document.getElementById('nodeParentName').value = nodeParentID;</script>
</div>
</form:form>
<!-- End: RoleParentList.jsp -->