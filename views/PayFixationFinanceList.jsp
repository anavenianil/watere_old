<!-- Begin : PayFixationFinanceList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<div id="resultMsg">
<jsp:include page="Result.jsp"></jsp:include>
</div>
<div class="line" id="payFixationDiv" style="display:none">
<div id="Pagination1">
<%int i=1; %>
	<display:table name="${sessionScope.assessmentList}" excludedParams="*"
		export="false" class="list" requestURI="" id="fixationList" pagesize="1000"
		sort="list" >
		<display:column  style="width:5%;text-align:center" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'row\');"/>'>
			<c:if test="${empty fixationList.fixationAcceptedDate}"><input type="checkbox" class="row" name="row" id="${fixationList.id}#${fixationList.assessmentID}" onclick="checkBoxCheck('row');enablePayDoPartNo('fixationList')"/></c:if>
		</display:column>
		
		<display:column title="SFID" style="width:5%" >${fixationList.sfID}</display:column>
		<display:column title="Name" style="width:10%" >${fixationList.empName}</display:column>
		<display:column title="Designation" style="width:8%" ><div id="${fixationList.designationID}">${fixationList.designation}</div></display:column>				
		<display:column title="Department" style="width:10%" >${list.department}</display:column>				
		<display:column title="Promotion Date" style="width:5%" ><fmt:formatDate pattern="dd-MMM-yyyy" value="${fixationList.promotionDate}"/></display:column>				
		<display:column title="Effective Date" style="width:5%" ><fmt:formatDate pattern="dd-MMM-yyyy" value="${fixationList.effectiveDate}"/></display:column>				
		<display:column title="Pay" style="width:10%" >${fixationList.basicPay}+${fixationList.gradePay}</display:column>				
		<c:if test="${empty fixationList.fixationAcceptedDate}">
		<display:column title="New Pay" style="width:10%" >${fixationList.newBasicPay}</display:column>
		<display:column title="New Grade Pay" style="width:10%" >
			${fixationList.newGradePay}
		</display:column>
		<display:column title="Additional Incr" style="width:8%" >
			${fixationList.newTwoAddIncr}
		</display:column>
			<display:column title="Additional Inc Effective Date" style="width:25%;vertical-align:middle">&nbsp;
			${fixationList.fixationAcceptedDate}
			</display:column>
		</c:if>
		<c:if test="${not empty fixationList.fixationAcceptedDate}">
		<display:column title="New Pay" style="width:10%" >${fixationList.newBasicPay}</display:column>	
		<display:column title="New Grade Pay" style="width:10%" >${fixationList.newGradePay}</display:column>
		<display:column title="Additional Incr" style="width:8%" >${fixationList.newTwoAddIncr}</display:column>
		<display:column title="Acceptance Date" style="width:8%" ><fmt:formatDate pattern="dd-MMM-yyyy" value="${fixationList.fixationAcceptedDate}"/></display:column>		
		</c:if>
		<display:column title="Report" style="width:5%"><a href="javascript:payFixationCCS('${list.sfID}')"><c:if test="${not empty fixationList.fixationAcceptedDate}">Print</c:if></a></display:column>				
		<%i++; %>	
	</display:table>
</div>
</div>
<div class="line" id="variableIncrementsDiv" style="display:none">
<div id="Pagination2">
<%int j=1; %>
	<display:table name="${sessionScope.incrementsList}" excludedParams="*"
		export="false" class="list" requestURI="" id="incrementsList" pagesize="1000"
		sort="list" >
		<display:column  style="width:5%;text-align:center" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'row\');"/>'>
			<c:if test="${empty incrementsList.variableIncr}"><input type="checkbox" class="row" name="row" id="${incrementsList.id}#${incrementsList.assessmentID}" onclick="checkBoxCheck('row');enablePayDoPartNo('incrementsList')"/></c:if>
		</display:column>
		
		<display:column title="SFID" style="width:5%" >
			${incrementsList.sfID}			
		</display:column>
		<display:column title="Name" style="width:10%" >
			${incrementsList.empName}			
		</display:column>
		<display:column title="Designation" style="width:8%" ><div id="${incrementsList.designationID}">${incrementsList.designation}</div></display:column>				
		<display:column title="Promotion Effective Date" style="width:5%" ><fmt:formatDate pattern="dd-MMM-yyyy" value="${incrementsList.promotionDate}"/></display:column>				
		<display:column title="Variable Inc.Start Date" style="width:5%" ><fmt:formatDate pattern="dd-MMM-yyyy" value="${incrementsList.effectiveDate}"/></display:column>				
		<c:if test="${empty incrementsList.variableIncr}">
	    <display:column title="Variable Increments" style="width:10%" >
			<input type="text" name="variableIncr" id="variableIncr<%=j %>" value="${incrementsList.variableIncr}" size="5" onkeypress="return checkFloat(event,'variableIncr<%=j %>');" onchange="javascript:clickRadio(this)"/>
		</display:column>
	    <display:column title="Variable Inc.End Date" style="width:25%;vertical-align:middle">&nbsp;
			<input type="text" size="13" value='${incrementsList.incrementsAccepteddate}' readonly="readonly" name="incrAcceptedDate" id="incrAcceptedDate<%=j %>"/>
			<img  src="./images/calendar.gif" id="incrAcceptedDateImg<%=j %>" />
				<script type="text/javascript">
					Calendar.setup({inputField :"incrAcceptedDate<%=j %>",ifFormat : "%d-%b-%Y",showsTime : false,button :"incrAcceptedDateImg<%=j %>",singleClick : true,step : 1});
				</script>
		 </display:column>
	    </c:if>
	    <c:if test="${not empty incrementsList.variableIncr}">
	   		<display:column title="Variable Increments" style="width:10%" ><div id="${incrementsList.variableIncr}">${incrementsList.variableIncr}</div></display:column>				
	   		<display:column title="Variable Inc.End Date" style="width:25%" ><fmt:formatDate pattern="dd-MMM-yyyy" value="${incrementsList.varIncEndDate}"/></display:column>				
	    </c:if>
			<%j++; %>	
			</display:table>			
		</div>
		</div>		
	<script>
		$jq( function(){ $jq("#Pagination1").displayTagAjax('paging');})
		$jq( function(){ $jq("#Pagination2").displayTagAjax('paging');})
	</script>

<!-- End : PayFixationFinanceList.jsp -->