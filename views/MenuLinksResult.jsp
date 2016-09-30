<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--Begin: MenuLinksResult.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
	<div>
		<c:if test="${message=='failure'}"> <span class="failure">Error Saving the Record</span></c:if>
		<c:if test="${message=='success'}"> <span class="success">Saved Record Successfully</span>
			<script>
				alert('Changes Will be effected After Re-Login');
			</script>
		</c:if>
	</div>	
<!--End: MenuLinksResult.jsp -->