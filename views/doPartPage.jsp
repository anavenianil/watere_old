<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : doPartPage.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />

<form:form commandName="${sessionScope.type}">
<div class="line">
		<div class="quarter leftmar">Gazetted Type</div>
		<div class="quarter">
			<form:select path="gazettedType" id="gazettedType" onchange="javascript:getDoPartNumbers()">
					<form:option value="0">Select</form:option>
					<c:if test="${sessionScope.type == 'increment'}">
					<form:options items="${increment.typeList}" itemValue="id" itemLabel="name"/>
					</c:if>
					 
					<c:if test="${sessionScope.type == 'promotion'}">
					<form:options items="${promotion.typeList}" itemValue="id" itemLabel="name"/>
					</c:if>
					
			</form:select>
			     
		 </div>
		   
</div>
	<div class="line">
		 <div class="line" id="doPartNumberDiv" style="display:none">
		<div class="quarter leftmar">DoPart Number</div>
		<div class="quarter">
			<form:select path="doPartNo" id="doPartNo" onchange="javascript:getCasualityList();" onmouseover="setSelectWidth('#doPartNo')">
					<form:option value="select">Select</form:option>
					<form:option value="0" >No Action</form:option>
					<c:if test="${sessionScope.type == 'increment'}">
					<c:forEach var="doPartList" items="${increment.doPartList}">
					<option value="${doPartList.id}#<fmt:formatDate pattern="dd-MMM-yyyy" value="${doPartList.doPartDate}"/>">${doPartList.doPartNumber} - Dated <fmt:formatDate pattern="dd-MMM-yyyy" value="${doPartList.doPartDate}"/></option>
					</c:forEach>
					</c:if>
					<c:if test="${sessionScope.type == 'promotion'}">
					<c:forEach var="doPartList" items="${promotion.doPartList}">
					<option value="${doPartList.id}#<fmt:formatDate pattern="dd-MMM-yyyy" value="${doPartList.doPartDate}"/>">${doPartList.doPartNumber} - Dated <fmt:formatDate pattern="dd-MMM-yyyy" value="${doPartList.doPartDate}"/></option>
					</c:forEach>
					</c:if>
					
			</form:select>
		 </div>
		 <div id="serialNumberDiv" style="display:none">
		<div class="quarter bold">Serial Number</div>
		<div class="quarter">
		<input type="text" id="serialNumber"/>
		</div>
		</div>
          
	</div>		
</div>
    <div class="line">
    <div id="casualityDiv" style="display:none"  class="line">
		<div class="quarter leftmar">Casuality</div>
		<div class="quarter">
		<form:select path="casualityId" id="casualityId" onchange="javascript:getEmployeeList();">
					<form:option value="0">Select</form:option>
					<c:if test="${sessionScope.type == 'increment'}">
					<c:forEach var="casualityList" items="${increment.casualitiesList}">
					<form:option value="${casualityList.id}">${casualityList.name}</form:option>
					</c:forEach>
					</c:if>
					<c:if test="${sessionScope.type == 'promotion'}">
					<c:forEach var="casualityList" items="${promotion.casualitiesList}">
					<form:option value="${casualityList.id}#${casualityList.code}">${casualityList.name}</form:option>
					</c:forEach>
					</c:if>
		</form:select>
		</div>
		</div>        
    </div>
</form:form>

<!-- End : doPartPage.jsp -->