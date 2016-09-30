<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:InventoryHolderDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
		
			<div class="line">
				<div class="quarter" style="margin-left:8px;">Directorate</div>
				<div class="quarter">
				<spring:bind path="mmgMaster">
					<form:label path="mmgMaster.directorateName" id="directorateName" cssStyle="cursor:auto">${mmgMaster.directorateName}</form:label>
				</spring:bind>
				</div>
				
				<div class="quarter" style="margin-left:8px;">Division</div>
				<div class="quarter">
				<spring:bind path="mmgMaster">
					<form:label path="mmgMaster.divisionName" id="divisionName" cssStyle="cursor:auto">${mmgMaster.divisionName}</form:label>
				</spring:bind>
				</div>
								
				</div>
			<div class="line">
				<div class="quarter" style="margin-left:8px;">Name</div>
				<div class="quarter">
				<spring:bind path="mmgMaster">
					<form:label path="mmgMaster.holderName" id="holderName" cssStyle="cursor:auto">${mmgMaster.holderName}</form:label>
				</spring:bind>
				</div>
				<div class="quarter" style="margin-left:8px;">Designation</div>
				<div class="quarter">
				<spring:bind path="mmgMaster">
					<form:label path="mmgMaster.designation" id="designation" cssStyle="cursor:auto">${mmgMaster.designation}</form:label>
				</spring:bind>
				</div>
							
			</div>
			<div class="line">
				<div class="quarter" style="margin-left:8px;">Phone</div>
				<div class="quarter">
				<spring:bind path="mmgMaster">
					<form:label path="mmgMaster.phone" id="phone" cssStyle="cursor:auto">${mmgMaster.phone}</form:label>
				</spring:bind>
				</div>
			</div>


			
<!-- End:InventoryHolderDetails.jsp -->