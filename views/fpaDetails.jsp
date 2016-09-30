<!-- Begin : loanDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>

<div class="line">
	<div class="line">
    <div class="quarter bold">Department</div>
    <div class="quarter ">${workflowmap.fpaRequestDetails.departmentDetails.deptName}</div>
    <div class="quarter bold">Operation Location</div>
    <div class="quarter ">${workflowmap.fpaRequestDetails.operationLocation}</div>
    </div>
    <div class="line">
    <div class="quarter bold">Operation Date</div>
    <div class="quarter "><fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.fpaRequestDetails.operationDate}"/></div>
    <div class="quarter bold">Sterilization certificate issued </div>
    <div class="quarter ">${workflowmap.fpaRequestDetails.sterilizationCert}</div>
    </div>
    
    <div class="line">
    	<div class="quarter bold">Basic Pay</div>
     	<div class="quarter"><fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="2" value="${workflowmap.fpaRequestDetails.basicPay}"/></div>
     	<div class="quarter bold">Grade Pay</div>
     	<div class="quarter"><fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="2" value="${workflowmap.fpaRequestDetails.gradePay}"/></div>
    </div>
     <div class="line">
     <div class="quarter bold">FPA application form</div>
    <div class="quarter"><a href="javascript:viewFPAForm('${workflowmap.requestId}','application')">PDF</a></div>
    </div>   
</div>