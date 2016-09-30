<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:SubordinateList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="quarter">
		<select name="subordinateId" id="subordinateId" style="width:160px">
			<option value="Select">Select</option>
			<c:forEach items="${workflowmap.subordinateList}" var="division">
					<option value="${division.sfid}">${division.name}</option>
				</c:forEach>
			
        </select>
   </div>
		
	</body>
</html>
<!-- End:SubordinateList.jsp -->