<!-- Begin : PayFixationList.jsp -->
<%@page import="net.sf.json.JSONString"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div id="resultMsg">
<jsp:include page="Result.jsp"></jsp:include>
</div>
<div><input id="checkbox-1-1" class="regular-checkbox" disabled="disabled"  type="checkbox"><label for="checkbox-1-1"></label>&nbsp&nbspYou can Check 
&nbsp&nbsp&nbsp<input id="checkbox-1-1" class="regularrkr-checkbox" disabled="disabled"  type="checkbox"><label for="checkbox-1-1"></label>&nbsp&nbspDisabled</div>
<div class="line" id="payFixationDiv" style="display:none">
<div id="Pagination1">
<%int i=1; %>
	<display:table name="${sessionScope.assessmentList}" excludedParams="*"
		export="false" class="list" requestURI="" id="fixationList" pagesize="1000"
		sort="page" >
		
		
		<display:column  sortProperty="optionStatus" style="width:5%;text-align:center" title='<input type=checkbox  name="selectall" id="${fixationList.id}#${fixationList.assessmentID}" onclick="checkBoxPromotionCheckAll(this.name,\'row\');enablePayDoPartNo(\'incrementsList\');"/>'>
			<c:if test="${fixationList.optionStatus == 100 && fixationList.basicpayId == null}">
			 <input type="checkbox" class="row regular-checkbox" name="row" id="${fixationList.id}#${fixationList.assessmentID}" onclick="checkBoxCheck('row');enablePayDoPartNo('fixationList')"/>
			 <label for="${fixationList.id}#${fixationList.assessmentID}"></label></c:if>
		    <c:if test="${fixationList.optionStatus != 100 || fixationList.basicpayId != null || fixationList.payStatus ==2}">
		    <input type="checkbox" class="row regularrkr-checkbox" disabled="disabled" name="row" id="${fixationList.id}#${fixationList.assessmentID}" onclick="checkBoxCheck('row');enablePayDoPartNo('fixationList')"/>
		    <label for="${fixationList.id}#${fixationList.assessmentID}"></label></c:if>
		</display:column>
		
		<display:column title="SFID" style="width:5%" sortable="true" sortProperty="optionStatus">
			${fixationList.sfID}			
		</display:column>
		<display:column title="Name" style="width:10%" sortable="true" sortProperty="optionStatus">
			${fixationList.empName}			
		</display:column>
		<display:column title="DesignationFrom-DesignationTo" style="width:8%" sortable="true"><div id="${fixationList.toDesignation}">${fixationList.designation}</div></display:column>				
		<display:column title="Department" style="width:10%" sortable="true">${fixationList.department}</display:column>
					
		<display:column title="Promotion Date" style="width:5%" sortable="true"><fmt:formatDate pattern="dd-MMM-yyyy" value="${fixationList.promotionDate}"/></display:column>				
		<display:column title="Variable Inc.Start Date" style="width:5%" sortable="true"><fmt:formatDate pattern="dd-MMM-yyyy" value="${fixationList.effectiveDate}"/></display:column>				
		<display:column title="Old Basic Pay+Grade Pay" style="width:10%" sortable="true" >${fixationList.basicPay}+${fixationList.gradePay}</display:column>				
		<c:if test="${empty fixationList.fixationAcceptedDate  || fixationList.basicpayId == null }">
		
		<display:column title="New Basic Pay" style="width:10%" sortable="true" >
			<input type="text" name="newBasicPay" id="newBasicPay<%=i %>" value="${fixationList.newBasicPay}" size="5" onkeypress="return checkFloat(event,'newBasicPay<%=i %>');" onchange="javascript:clickRadio(this)"/>
		</display:column>
		<display:column title="New Grade Pay" style="width:10%" sortable="true" >
			<input type="text" name="newGradePay" id="newGradePay<%=i %>" value="${fixationList.newGradePay}" size="5" onkeypress="return checkFloat(event,'newGradePay<%=i %>');" onchange="javascript:clickRadio(this)"/>
		</display:column>
		<display:column title="Additional Incr" style="width:6%" sortable="true" >
			<input type="text" name="newTwoAddIncr" id="newTwoAddIncr<%=i %>" value="${fixationList.newTwoAddIncr}" size="5" onkeypress="return checkFloat(event,'newTwoAddIncr<%=i %>');" onchange="javascript:clickRadio(this)"/>
		</display:column>
			<display:column title="Emp Reported Date" style="width:25%;vertical-align:middle">&nbsp;
			<input type="text" size="10" value='<fmt:formatDate pattern="dd-MMM-yyyy" value="${fixationList.fixationAcceptedDate}"/>' readonly="readonly" name="AcceptedDate" id="AcceptedDate<%=i %>"/>
			<img  src="./images/calendar.gif" id="AcceptedDateImg<%=i %>" />
				<script type="text/javascript">
					Calendar.setup({inputField :"AcceptedDate<%=i %>",ifFormat : "%d-%b-%Y",showsTime : false,button :"AcceptedDateImg<%=i %>",singleClick : true,step : 1});
				</script>
		   </display:column>
		</c:if>
		<c:if test="${not empty fixationList.fixationAcceptedDate && fixationList.basicpayId != null}">
		<display:column title="New Pay"  style="width:8%;" >${fixationList.newBasicPay}</display:column>	
		<display:column title="New Grade Pay" style="width:8%" >${fixationList.newGradePay}</display:column>
		<display:column title="Additional Incr" style="width:8%" >${fixationList.newTwoAddIncr}</display:column>
		<display:column title="Emp Reported Date" style="width:8%" ><fmt:formatDate pattern="dd-MMM-yyyy" value="${fixationList.fixationAcceptedDate}"/></display:column>		
		</c:if>
		<c:if test="${not empty fixationList.fixationAcceptedDate}">
		
		</c:if>
		<display:column title="Variable Increments" style="width:1%" ><div id="${fixationList.variableIncr}"><fmt:formatNumber value="${fixationList.variableIncr}"></fmt:formatNumber></div></display:column>
<%-- 		<display:column title="Variable IncrValue" style="width:5%" ><div id="${fixationList.varIncrValue}"><fmt:formatNumber value="${fixationList.varIncrValue}"></fmt:formatNumber></div>${fixationList.varIncrValue}</display:column>  <!--  This column added for variable Increment value-->
 --%>	
 <display:column title="Variable IncrValue" style="width:5%" >			
 	<input type="text" name="variableIncrValue" id="variableIncrValue<%=i %>" value="${fixationList.varIncrValue}" size="5" onkeypress="return checkFloat(event,'variableIncrValue<%=i %>');" />
		</display:column>
		<display:column title="Report" style="width:5%"><a href="javascript:payFixationCCS('${fixationList.sfID}')"><c:if test="${not empty fixationList.fixationAcceptedDate}">Print</c:if></a></display:column>				
		
		<%i++; %>	
		
	</display:table>
</div>
</div>
<div class="line" id="variableIncrementsDiv" style="display:none">
<div id="Pagination2">
<%int j=1; %>
 <c:if test="${not empty sessionScope.incrementsList}">
	<display:table name="${sessionScope.incrementsList}" excludedParams="*"
		export="false" class="list" requestURI="" id="incrementsList" pagesize="1000"
		sort="list" >
		<display:column  style="width:5%;text-align:center" title='<input type=checkbox  name="selectall" id="${incrementsList.id}#${incrementsList.assessmentID}" onclick="checkBoxCheckAll(this.name,\'row\');enablePayDoPartNo(\'incrementsList\');"/>'>
			
			<input type="checkbox" class="row regular-checkbox" name="row" id="${incrementsList.id}#${incrementsList.assessmentID}" onclick="checkBoxCheck('row');enablePayDoPartNo('incrementsList');"/>
		      <label for="${incrementsList.id}#${incrementsList.assessmentID}"></label>
		      
		</display:column>
		
		<display:column title="SFID" style="width:5%" >
			${incrementsList.sfID}			
		</display:column>
		<display:column title="Name" style="width:10%" >
			${incrementsList.empName}			
		</display:column>
		<display:column title="DesignationFrom-DesignationTo" style="width:8%" ><div id="${incrementsList.designationID}">${incrementsList.designation}</div></display:column>				
		<display:column title="Promotion Effective Date" style="width:5%" ><fmt:formatDate pattern="dd-MMM-yyyy" value="${incrementsList.promotionDate}"/></display:column>				
		<display:column title="Variable Inc.Start Date" style="width:5%" ><fmt:formatDate pattern="dd-MMM-yyyy" value="${incrementsList.effectiveDate}"/></display:column>				
		
		
	    
	    <c:if test="${not empty incrementsList.variableIncr}">
	   		<display:column title="Variable Increments" style="width:10%" ><div id="${incrementsList.variableIncr}">${incrementsList.variableIncr}</div></display:column>				
	   		<display:column title="Variable Inc.End Date" style="width:15%" ><fmt:formatDate pattern="dd-MMM-yyyy" value="${incrementsList.endingDate}"/></display:column>				
	    </c:if>
			<%j++; %>	
			</display:table>	
			</c:if>		
		</div>
		</div>		
	<script>
		$jq( function(){ $jq("#Pagination1").displayTagAjax('paging');})
		$jq( function(){ $jq("#Pagination2").displayTagAjax('paging');})
	</script>
	<c:if test="${not empty requestScope.TadaRequestBean}">
		 <script>
			GazettedType = <%= (JSONString)request.getAttribute("GazettedType") %>;
		</script>
	   </c:if>	

<!-- End : PayFixationList.jsp -->
