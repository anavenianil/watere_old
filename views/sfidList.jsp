<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:sfidList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
		                  <div class="line">
								<div class="leftmar quarter" style="width: 20%;">Pending Sfid:</div>
					  <div class="quarter">
						<select  name="sfID" id="unsavedSfid" style="width: 90%">
						<option value="0">Select</option>
						<c:forEach var="assessmentList" items="${sessionScope.assessmentList}" >				
						<c:if test="${assessmentList.optionStatus eq '' || assessmentList.optionStatus eq null || assessmentList.optionStatus==0}">
							
							<c:choose>
				    <c:when test="${assessmentList.venueID == '53'}">
				   <option value="${assessmentList.sfID}" >${assessmentList.sfID}-${assessmentList.empName}---${assessmentList.designationTo}---Promoted</option> 
				    </c:when>
				   <c:otherwise>
				   <option value="${assessmentList.sfID}">${assessmentList.sfID}-${assessmentList.empName}---${assessmentList.designationTo}---Deferred</option>
				   </c:otherwise>
				   </c:choose>
						</c:if>
						</c:forEach>
						</select>
						</div>
						  <div class="leftmar quarter" style="width: 20%;">Saved Sfid:</div>
							  <div class="quarter">
							<select id="savedSfid" name="sfID" onchange="javascript:getOptioncertificate(this)" style="width: 90%">
								<option value="0">Select</option>
								<c:forEach var="assessmentList" items="${sessionScope.assessmentList}" >					
					<c:if test="${assessmentList.optionStatus  ne null && assessmentList.optionStatus != '' && assessmentList.optionStatus !=100 && assessmentList.optionStatus !=0}">
				   
				   <c:choose>
				    <c:when test="${assessmentList.venueID == '53'}">
				   <option value="${assessmentList.sfID}">${assessmentList.sfID}-${assessmentList.empName}---${assessmentList.designationTo}---Promoted</option> 
				    </c:when>
				   <c:otherwise>
				   <option value="${assessmentList.sfID}">${assessmentList.sfID}-${assessmentList.empName}---${assessmentList.designationTo}---Deferred</option>
				   </c:otherwise>
				   </c:choose>
				   								
					</c:if>
					</c:forEach>
					</select>
					</div>
     			</div>
<!-- end:sfidList.jsp -->
