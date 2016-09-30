<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : HindiEmpCheckExamsListPage.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	                          
	                                <div class="line">	  
	                                <c:if test="${empCheckExamsList ne null}">                             
							       <div class="quarter leftmar">Eligible Exams</div>
							       <div class="quarter bold" style="width: 70%;" id="notEligibleExams">							          							      
								       <c:forEach items="${empCheckExamsList}" var="exams">								          
								         ${exams.key}
								              <input type="checkbox" name="nonEligibleExamId" id="${exams.value}" value="${exams.value}"/>
								           						         
								        </c:forEach>								    
								    </div> 
								    </c:if>
								 </div>
	
	
<!-- End : HindiEmpCheckExamsListPage.jsp -->
