<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--Begin: MenuLinksList.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

	<script>
		listObj = <%= (net.sf.json.JSONArray)session.getAttribute("GradePayList") %>
		delistObj = <%= (net.sf.json.JSONArray)session.getAttribute("deQuarterTypeList") %>
		setGradePays();
	</script>	

<!--End: MenuLinksList.jsp -->