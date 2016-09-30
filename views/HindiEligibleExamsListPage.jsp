<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : HindiEligibleExamsListPage.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	                      <div class="line">
										<div class="quarter leftmar">Exam Name<span class="mandatory">*</span></div>
										<div class="quarter">
										<spring:bind path="hindi">
											<form:select path="hindi.examId" id="examId" cssClass="formSelect" >
												<form:option value="0">Select</form:option>												
												<form:options items="${eligibleExamslist}" itemValue="value" itemLabel="key"/>													                            																																		
											</form:select>
										</spring:bind>
										</div>
								    </div>    
	                                
	
	
<!-- End : HindiEligibleExamsListPage.jsp -->
