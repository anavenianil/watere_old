<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:AppliedLeaveList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="script/jquery.js"></script>

<select name="leaveRequestId" id="leaveRequestId" class="formSelect" onchange="checkLeaveStatus();"></select>
<script>
	ltcLeaveTypeList= <%= (net.sf.json.JSONArray)session.getAttribute("ltcLeaveTypeList") %>;
</script>