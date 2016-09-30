<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : EmpExperienceList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<div class="line">
	<c:if test="${message eq 'success'}"><div class="myStyle success"><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'update'}"><div class="myStyle success"><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'delete'}"><div class="myStyle success"><spring:message code="delete"/></div></c:if> 
	<c:if test="${message eq 'failure'}"><div class="myStyle failure"><spring:message code="failure"/></div></c:if>
</div>
<aa:zone name="empExpTable">
	<display:table name="sessionScope.experienceList" excludedParams="*"
		export="false" class="list" requestURI="" id="expList" pagesize="10"
		sort="list">
		<display:column  title='Experience' style="width:10%;" >${expList.experience}</display:column>
		<display:column  title='Description' style="width:40%;" >&nbsp;${expList.description}</display:column>
		<display:column  title='From Date' style="width:17%;">${expList.fromDate}</display:column>
		<display:column  title='To Date' style="width:17%;">${expList.toDate}</display:column>
		<display:column style="width:8%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit"
				onclick="editExperience(${expList.id},'${expList.experience}','${expList.fromDate}','${expList.toDate}','${expList.description}')"/>
		</display:column>
		<display:column style="width:8%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Edit"
				onclick="deleteExperience(${expList.id})"/>
		</display:column>
	</display:table>
</aa:zone>
<script>displayPaging("empExpTable","expList");
clearEmpExperience();
</script>
<!-- End : EmpExperienceList.jsp -->
