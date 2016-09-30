<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--Begin:LeaveExceptionalList.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

	<script>
		leavelistObj = <%= (net.sf.json.JSONArray)session.getAttribute("selectedLeaveExpList") %>
		leavedelistObj = <%= (net.sf.json.JSONArray)session.getAttribute("leaveExpList") %>
		
	</script>	

<!--End:LeaveExceptionalList.jsp  -->