<!-- Begin : LtcFamilyDataList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>

<div>
	<div>
		<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
		<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
		<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
		<c:if test="${message=='deletefail'}"> <span class="failure"><spring:message code="recordexists"/></span></c:if>
	</div>
	<div>
	
	
		<div id="Pagination">
			<display:table name="${sessionScope.LtcFamilyMemberList}" excludedParams="*"
				export="false" class="list" requestURI="" id="dataList" pagesize="10"
				sort="list">
					<display:column  style="width:5%;text-align:center" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'row\'); checkApplying();"/>'>
						<input type="checkbox" class="row" name="row" id="${dataList.id}" onclick="checkApplying()"/>
					</display:column>
					<display:column title="Name" style="width:15%">${dataList.name}  </display:column>
					<display:column title="DOB" style="width:8%">${dataList.dob}</display:column>
					<display:column style="width:18%;text-align:center" title="Age"> <span><font color='red'>${dataList.age} </font></span> as on ${dataList.toDay} </display:column>
					<display:column title="Relationship With Govt.servant" style="width:20%">${dataList.relation}</display:column>
				<%-- 	<display:column title="Applied LTCs" style="width:30%">${dataList.ltcAvail}</display:column> --%>
			</display:table>
		</div>

	    <script>
			LtcFamilyMemberList = <%= (net.sf.json.JSONArray)session.getAttribute("LtcFamilyMemberList") %> 
		</script>
	</div>
</div>
<!-- End : LtcFamilyDataList.jsp -->
