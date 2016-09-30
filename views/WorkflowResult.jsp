<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<div class="line">
	<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
	<c:if test="${message=='success'}"> 
		<span class="success"><spring:message code="success"/></span>
		<c:if test="${type ne null}">
			<script>nodeID = ${type }</script>
		</c:if>
	</c:if>
	<c:if test="${message=='failure'}"> 
		<c:if test="${remarks ne ''}">
			<span class="failure">${remarks }	</span>	
		</c:if>
	</c:if>
</div>