<!-- Begin: SPLResult.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>

<div>
	<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
	<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
	<c:if test="${message=='empNotExists'}"> <span class="failure"><spring:message code="empNotExists"/></span></c:if>
	<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
	<c:if test="${message=='EXISTS'}"> <span class="failure"><spring:message code="detailsExist"/></span></c:if>
</div>
<div id="res" style="display:none"><c:out value="${sessionScope.SPCLID}"></c:out></div>

<!-- End: SPLResult.jsp -->