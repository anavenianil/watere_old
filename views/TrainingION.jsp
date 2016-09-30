<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--Begin: TrainingION.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<script type="text/javascript" src="script/trainingscript.js"></script>



		
											
		<script>
			
			jsonIonMstList = <%=(net.sf.json.JSONArray)session.getAttribute("jsonIonMstList") %>;
			setIONMstList(jsonIonMstList)			
		</script>

<!--End: TrainingION.jsp -->