<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin:nomineeDetailsList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<div class="line">
    <c:if test="${message eq 'success'}">
		<div class="myStyle success"><spring:message code="success"/></div>
     </c:if>
    <c:if test="${message eq 'failure'}"> 
          <div class="myStyle failure"><spring:message code="failure"/></div>
    </c:if>
</div>
<aa:zone name="nomineeTable">
	<display:table name="sessionScope.displayNomineeList" excludedParams="*"
		export="false" class="list" requestURI="" id="nomineeListID" pagesize="10"
		sort="list">
		<display:column  title='Nominee' style="width:30%">${nomineeListID.nomineeName}</display:column>
		<display:column  title='Nominee Type'  style="width:20%">${nomineeListID.nomineeTypeName}</display:column>
		<display:column  title='Percentage'  style="width:10%" ><div align="right">${nomineeListID.percentage}</div></display:column>
		<display:column  title='In contingency of'  style="width:30%">&nbsp;${nomineeListID.inContengensyParentName}</display:column>
		<display:column style="width:5%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit"
				onclick="editNominee('${nomineeListID.id}')"/>
		</display:column>
		<display:column style="width:5%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Edit"
				onclick="deleteNominee('${nomineeListID.id}','${nomineeListID.inContengensyParent}')"/>
		</display:column>
	</display:table>
</aa:zone>
<script>
	displayPaging("nomineeTable","nomineeListID");
	nomineeJSONList = <%= (net.sf.json.JSONArray)session.getAttribute("nomineeListJSON") %>;
	percentageList = <%= (net.sf.json.JSON)session.getAttribute("percentageList") %>;
	familyMemberInNominee = <%= (net.sf.json.JSON)session.getAttribute("familyMemberInNominee") %>;	
</script> 
<!-- End :  nomineeDetailsList.jsp -->
