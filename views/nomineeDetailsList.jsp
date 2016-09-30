<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
                                 <div class="line">
							               <c:if test="${message eq 'success'}">
										        <div class="myStyle success"><spring:message code="nominee.success"/></div>
						                   </c:if>
						                    <c:if test="${message eq 'update'}">
										        <div class="myStyle success"><spring:message code="nominee.update"/></div>
						                   </c:if>
						                    <c:if test="${message eq 'delete'}">
										        <div class="myStyle success"><spring:message code="nominee.delete"/></div>
						                   </c:if> 
						                   <c:if test="${message eq 'failure'}"> 
						                       <div class="myStyle success"><spring:message code="nominee.failure"/></div>
						                  </c:if>
						            </div>
<aa:zone name="nomineeTable">
	<display:table name="sessionScope.nomineeList" excludedParams="*"
		export="false" class="list" requestURI="" id="nomineesList" pagesize="10"
		sort="list">
		<display:column  title='Name' >${nomineesList.nominee}</display:column>
		<display:column  title='DOB' >${nomineesList.dob}</display:column>
		<display:column  title='percentage' >${nomineesList.percentage}</display:column>
		<display:column>
			<img src="./images/edit.gif" title="Edit" onclick="editNomineeDetails('${nomineesList.id}')"/>
		</display:column>
		<display:column>
			<img src="./images/delete.gif" title="Edit" onclick="deleteNomineeDetails('${nomineesList.id}')"/>
		</display:column>
	</display:table>
</aa:zone>
<script>displayPaging("nomineeTable","nomineesList");</script> 
<%-- End :  nomineeDetailsList.jsp --%>
