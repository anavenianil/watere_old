<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--Begin: LetterNumberION.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>




		
		<c:if test="${letterNumberFormat.type =='deptChanged'}">								
		<script>
			
			jsonLetterNumberFormatList = <%=(net.sf.json.JSONArray)session.getAttribute("jsonLetterNumberFormatList") %>;
			setLetterNumberFormatList(jsonLetterNumberFormatList);		
						
		</script>
		</c:if>	
		<c:if test="${letterNumberFormat.type =='serialNumChanged'}">								
		<script>
			jsonLetterNumberFormatList = <%=(net.sf.json.JSONArray)session.getAttribute("jsonLetterNumberFormatList") %>;
			jsonIonMstList = <%=(net.sf.json.JSONArray)session.getAttribute("jsonIonMstList") %>;
			setIonLetterNumberList(jsonIonMstList)			
		</script>
		</c:if>	
		<c:if test="${letterNumberFormat.type =='getRoleDeptList'}">								
		<script>
			jsonLetterNumberFormatList = <%=(net.sf.json.JSONArray)session.getAttribute("jsonLetterNumberFormatList") %>;
			jsonIonMstList = <%=(net.sf.json.JSONArray)session.getAttribute("jsonIonMstList") %>;
			setIonLetterNumberList(jsonIonMstList)			
		</script>
		</c:if>	
		
		<c:if test="${ion.type =='getLevel1SfidList'}">								
		<script>
			
			jsonLevel1SfidList = <%=(net.sf.json.JSONArray)session.getAttribute("jsonLevel1SfidList") %>;
			setLevel1SfidList(jsonLevel1SfidList);		
						
		</script>
		</c:if>	
		
		

<!--End: LetterNumberION.jsp-->