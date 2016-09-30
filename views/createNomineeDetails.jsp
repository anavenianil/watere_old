<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:createNomineeDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/jquery.js"></script>
<div>
	<div id="reqResult"></div>


<fieldset><legend><strong><font color='green'>Nominee Details</font></strong></legend>
<div class="line">
<div class="quarter">SFID</div>
<div class="quarter">${nominee.sfid}</div>
<c:if test="${message ne 'getNomineeDetails'}">
	<div class="quarter">Name</div>
	<div class="quarter">${changeSfidName}</div>
</c:if>
</div>
<div class="line">
		<div class="quarter"><spring:message code="nominee.nominee" /><span class="failure">*</span></div>
		<div class="quarter">
		<spring:bind path="nominee">
			<form:select path="nominee.nominee" id="nomineeId" onchange="javascript:addNominee();"  cssClass="formSelect" onmouseover="setSelectWidth('#nomineeId')">
				<form:option value="select">Select</form:option>
				<c:forEach var="family" items="${familyList}">
					 <form:option value="${family.id}">${family.name}</form:option>
				</c:forEach> 
				<%--<form:option value="new">Add New Member</form:option>--%>
			</form:select>
		</spring:bind>
		</div>
		<%--<div id="nomineedobdiv" >
		<div class="quarter"><spring:message code="nominee.dob" /><span class="failure">*</span></div>
		<div class="quarter"><spring:bind path="nominee"><form:input path="nominee.dob" id="nomineedob" cssClass="dateClass" readonly="true"/></spring:bind>
		<img  src="./images/calendar.gif"   id="date_start_trigger3" />	
						<script type="text/javascript">
						Calendar.setup({inputField :"nomineedob",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger3",singleClick : true,step : 1});
						</script>
		</div>	
		</div>
		<div id="nomineeagediv" class="displayvalues">
		<div class="quarter">Age<span class="failure">*</span></div>
		<div class="quarter"><spring:bind path="nominee"><form:input path="nominee.age" id="nomineeage" readonly="true"/></spring:bind></div>
		</div>	
	</div>
	<div class="line">		
		<div class="quarter"><spring:message code="nominee.relationId" /><span class="failure">*</span></div>
		<div class="quarter"><spring:bind path="nominee">
			<form:select path="nominee.relationId" id="nomineerelation">
				<form:option value="select">Select</form:option>
				<c:forEach var="relation" items="${RelationList}">
					 <form:option value="${relation.id}">${relation.name}</form:option>
				</c:forEach> 
			</form:select>
		</spring:bind></div>--%>
		<div class="quarter"><spring:message code="nominee.percentage" /><span class="failure">*</span></div>
		<div class="quarter"><spring:bind path="nominee"><form:input path="nominee.percentage" maxlength="6" id="percentage" onkeypress="return checkFloat(event,'percentage');" onblur="javascript:checkDecimals('percentage');"/></spring:bind>	</div>
	</div>
	<div class="line">
		<div class="quarter"><spring:message code="nominee.dateofnominate" /></div>
			<div class="quarter"><spring:bind path="nominee"><form:input path="nominee.nominate" id="nominate" cssClass="dateClass" readonly="true"/></spring:bind>
			<img  src="./images/calendar.gif"   id="date_start_trigger4" />	
						<script type="text/javascript">
						Calendar.setup({inputField :"nominate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger4",singleClick : true,step : 1});
						</script>
		
		
		</div>
	    <div class="quarter"><spring:message code="nominee.remarks" /></div>
		<div class="quarter"><spring:bind path="nominee"><form:input path="nominee.remarks" id="remarks"/></spring:bind>	</div>
	</div>
	<div class="line">
		<c:if test="${message ne 'getNomineeDetails'}">
			<spring:bind path="nominee">
				<div class="appbutton"><a class="quarterbutton" href="javascript:clearNomineeDetails()">Clear</a></div>
				<c:if test="${nominee.param eq 'EditNominee'}">
					<div id="nomineeUpdate" class="appbutton submitbutton"><a class="quarterbutton" href="javascript:updateNomineeDetails();">Update</a></div>
				</c:if>
				<c:if test="${nominee.param eq 'EditNominee'}">
					<div id="nomineeSubmit" class="appbutton submitbutton displayvalues"><a class="quarterbutton" href="javascript:manageNomineeDetails();">Submit</a></div>
				</c:if>
				<c:if test="${nominee.param ne 'EditNominee'}">
					<div class="appbutton submitbutton"><a class="quarterbutton" href="javascript:manageNomineeDetails();">Submit</a></div>
				</c:if>
				
			</spring:bind>
		</c:if>
		<c:if test="${message eq 'getNomineeDetails'}">
			<div class="appbutton submitbutton"><a class="quarterbutton" href="javascript:nomineeRequest();">Submit</a></div>		
		</c:if>
	</div>
</fieldset>
</div>
<!-- End:createNomineeDetails.jsp -->