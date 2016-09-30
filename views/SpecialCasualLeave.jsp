<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:SpecialCasualLeave.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form" %>
<%@ taglib uri="/tags/spring" prefix="spring"%>

<div class="line"><div id="result1"></div></div>
<div class="line">
	<div class="line">
		<div class="quarter" style="padding-left: 24.3%;"><spring:bind path="leave"><form:radiobutton path="leave.categoryType" id="categoryType1" label="Type 1 (Medical)" value="T1" onclick="javascript:showTypeIdDetails()"/></spring:bind></div>
		<div class="quarter"><spring:bind path="leave"><form:radiobutton path="leave.categoryType" id="categoryType2" label="Type 2 (Sports etc)" value="T2" onclick="javascript:showTypeIdDetails()"/></spring:bind></div>
	</div>
	<div class="line"><hr /></div>
	<div class="line" id="contentpage"></div>
</div>

<!-- End:SpecialCasualLeave.jsp -->