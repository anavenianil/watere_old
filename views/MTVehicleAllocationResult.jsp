<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : MTVehicleAllocationResult.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

	<c:if test="${message=='allocateSuccess'}"><span class="success"><spring:message code="allocateSuccess"/></span></c:if>
    <c:if test="${message=='allocateFail'}"><span class="success"><spring:message code="allocateFail"/></span></c:if>
	

<script>
		var ApprovedReqJSON = <%=(net.sf.json.JSONArray)session.getAttribute("ApprovedReqJSON")%>;
		
	</script>