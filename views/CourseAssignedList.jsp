<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--Begin: CourseAssignedList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

	<script>
	
			 type='<c:out value='${sessionScope.trainingMstType}'/>';	
			if(type == 'courseDiscipline')
			{
			jsonDisciplineList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonDisciplineList") %>;
			jsonSelectedDisciplineList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonSelectedDisciplineList") %>;
			
			setMenuLinksforDiscipline(jsonDisciplineList,jsonSelectedDisciplineList);
			}
			else if(type == 'courseQualification')
			{
			jsonQualificationList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonQualificationList") %>;
			jsonSelectedQualificationList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonSelectedQualificationList") %>;
			
			setMenuLinksforQualification(jsonQualificationList,jsonSelectedQualificationList);
			}
			else if(type == 'courseDesignation')
			{
			
			jsonDesignationList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonDesignationList") %>;
			jsonSelectedDesignationList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonSelectedCourseDesignationList") %>;
			
			setDesignationsForCourse(jsonDesignationList,jsonSelectedDesignationList);
			
			}
			
	</script>	

<!--End: CourseAssignedList.jsp -->