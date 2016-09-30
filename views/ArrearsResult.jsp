<!-- Begin : ArrearsResult.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
		
<div>
<c:if test="${message=='success'}"> <span class="success">${remarks}</span></c:if>
<c:if test="${message!='success'&& message!='notexists' && message!='lessDate' 
&& message!='notStarted' && message!='duplicate'&& message!='notBwMonth'&& message!='delete'&& message!='alreadyDone'}">
<div class="myStyle failure">${remarks}</div></c:if>
<c:if test="${message=='notexists'}"><div class="myStyle failure">${remarks}</div></c:if>
<c:if test="${message=='alreadyDone'}"><div class="myStyle failure">${remarks}</div></c:if>
<c:if test="${message=='lessDate'}"><div class="myStyle failure">${remarks}</div></c:if>
<c:if test="${message=='notStarted'}"><div class="failure">${remarks}</div></c:if>
<c:if test="${message=='duplicate'}"><div class="failure">${remarks}</div></c:if>
<c:if test="${message=='notBwMonth'}"><div class="failure">${remarks}</div></c:if>
<c:if test="${message=='delete' || Result=='delete'}"> <span class="failure"><spring:message code="delete"/></span></c:if>
<c:if test="${message=='update' || Result=='update'}"> <span class="success"><spring:message code="update"/></span></c:if>
<c:if test="${message=='empNotExists' || Result=='empNotExists'}"> <span class="failure"><spring:message code="empNotExists"/></span></c:if>
				
</div>
<!-- End : ArrearsResult.jsp -->