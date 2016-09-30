<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:SelectedCourseList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<script>
	jsonCourseList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonCourseList") %>;
		
</script>
		<select name="courseId" id="courseId"  cssClass="formSelect required"  onmouseover="setSelectWidth('#courseId')" onchange="javascript:getDurationAndVenueList(jsonCourseList);">
			<option value="">Select</option>
				<c:forEach items="${courseList}" var="course">
					<option value="${course.id}">${course.name}</option>
				</c:forEach>
		</select>
		
<!-- end:SelectedCourseList.jsp -->
