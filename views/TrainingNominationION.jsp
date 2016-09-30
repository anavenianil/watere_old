<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--Begin: TrainingNominationION.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>




		
		<c:if test="${trainingRequest.ionFlag =='2'}">								
		<script>
			
			jsonIonMstList2 = <%=(net.sf.json.JSONArray)session.getAttribute("jsonIonMstList2") %>;
			setIONMstLists(jsonIonMstList2,2)			
		</script>
		</c:if>	
		<c:if test="${trainingRequest.ionFlag =='3'}">								
		<script>
			
			jsonIonMstList3 = <%=(net.sf.json.JSONArray)session.getAttribute("jsonIonMstList3") %>;
			setIONMstLists(jsonIonMstList3,3)			
		</script>
		</c:if>	
		<c:if test="${trainingRequest.ionFlag =='4'}">								
		<script>
			
			jsonIonMstList4 = <%=(net.sf.json.JSONArray)session.getAttribute("jsonIonMstList4") %>;
			setIONMstLists(jsonIonMstList4,4)			
		</script>
		</c:if>	
		

<!--End: TrainingNominationION.jsp -->