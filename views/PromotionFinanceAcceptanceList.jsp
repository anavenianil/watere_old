<!-- Begin : PromotionFinanceAcceptanceList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>

<div id="result" class="line"><jsp:include page="Result.jsp"></jsp:include></div>
<form:form commandName="${sessionScope.type}">
	
	 <c:if test="${not empty sessionScope.assessmentDetails}">
	 <div id="Pagination1" class="line">
<%int i=1; %>
	<display:table name="${sessionScope.assessmentDetails}" excludedParams="*"
		export="false" class="list" requestURI="" id="fixationList" pagesize="10"
		sort="list" >
		<display:column  style="width:5%;text-align:center" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'row\');"/>'>
			<c:if test="${empty fixationList.fixationAcceptedDateFinance}"><input type="checkbox" class="row" name="row" id="${fixationList.id}#${fixationList.assessmentID}" onclick="checkBoxCheck('row');"/></c:if>
		</display:column>
		
		<display:column title="SFID" style="width:5%" >
			${fixationList.sfID}			
		</display:column>
		<display:column title="Name" style="width:10%" >
			${fixationList.empName}			
		</display:column>
		<display:column title="Designation" style="width:8%" ><div id="${fixationList.designationID}">${fixationList.designation}</div></display:column>				
		<display:column title="Department" style="width:10%" >${fixationList.department}</display:column>				
		<display:column title="Promotion Date" style="width:5%" ><fmt:formatDate pattern="dd-MMM-yyyy" value="${fixationList.promotionDate}"/></display:column>				
		<display:column title="Effective Date" style="width:5%" ><fmt:formatDate pattern="dd-MMM-yyyy" value="${fixationList.effectiveDate}"/></display:column>				
		<display:column title="Pay" style="width:10%" >${fixationList.basicPay}+${fixationList.gradePay}</display:column>				
		<c:if test="${empty fixationList.fixationAcceptedDate}">
		<display:column title="New Pay" style="width:10%" >
			<input type="text" name="newBasicPay" id="newBasicPay<%=i %>" value="${fixationList.newBasicPay}" size="5" onkeypress="return checkFloat(event,'newBasicPay<%=i %>');" onchange="javascript:clickRadio(this)"/>
		</display:column>
		<display:column title="New Grade Pay" style="width:10%" >
			<input type="text" name="newGradePay" id="newGradePay<%=i %>" value="${fixationList.newGradePay}" size="5" onkeypress="return checkFloat(event,'newGradePay<%=i %>');" onchange="javascript:clickRadio(this)"/>
		</display:column>
		<display:column title="Additional Incr" style="width:8%" >
			<input type="text" name="newTwoAddIncr" id="newTwoAddIncr<%=i %>" value="${fixationList.newTwoAddIncr}" size="5" onkeypress="return checkFloat(event,'newTwoAddIncr<%=i %>');" onchange="javascript:clickRadio(this)"/>
		</display:column>
			<display:column title="Additional Inc Effective Date" style="width:25%;vertical-align:middle">&nbsp;
			<input type="text" size="13" value='${fixationList.fixationAcceptedDate}' readonly="readonly" name="AcceptedDate" id="AcceptedDate<%=i %>"/>
			<img  src="./images/calendar.gif" id="AcceptedDateImg<%=i %>" />
				<script type="text/javascript">
					Calendar.setup({inputField :"AcceptedDate<%=i %>",ifFormat : "%d-%b-%Y",showsTime : false,button :"AcceptedDateImg<%=i %>",singleClick : true,step : 1});
				</script>
		   </display:column>
		</c:if>
		<c:if test="${not empty fixationList.fixationAcceptedDate}">
		<display:column title="New Pay" style="width:10%" >${fixationList.newBasicPay}</display:column>	
		<display:column title="New Grade Pay" style="width:10%" >${fixationList.newGradePay}</display:column>
		<display:column title="Additional Incr" style="width:8%" >${fixationList.newTwoAddIncr}</display:column>
		<display:column title="Emp Reported Date" style="width:8%" ><fmt:formatDate pattern="dd-MMM-yyyy" value="${fixationList.fixationAcceptedDate}"/></display:column>		
		</c:if>
		<display:column title="Report" style="width:5%"><a href="javascript:payFixationCCS('${list.sfID}')"><c:if test="${not empty fixationList.fixationAcceptedDate}">Print</c:if></a></display:column>				
		<%i++; %>	
	</display:table>
</div>
<div class="line" >
			<div class="expbutton"  style="float:right"><a href="javascript:submitFinanceAcceptance('promotion');" ><span>Submit</span></a></div>
	</div>
</c:if>
<c:if test="${not empty sessionScope.varAssessmentDetails}">
<div id="Pagination2" class="line">

<%int j=1; %>
	<display:table name="${sessionScope.varAssessmentDetails}" excludedParams="*"
		export="false" class="list" requestURI="" id="incrementsList" pagesize="10"
		sort="list" >
		<display:column  style="width:5%;text-align:center" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'row\');"/>'>
			<c:if test="${empty incrementsList.incrementsAccepteddate}"><input type="checkbox" class="row" name="row" id="${incrementsList.id}#${incrementsList.assessmentID}" onclick="checkBoxCheck('row');"/></c:if>
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
<div class="line" >
			<div class="expbutton"  style="float:right"><a href="javascript:submitFinanceAcceptance('varIncrement');" ><span>Submit</span></a></div>
</div>
</c:if>

<c:if test="${empty sessionScope.varAssessmentDetails  && empty sessionScope.assessmentDetails}">
	<div class="line" style="color: red"> NOTHING FOUND TO DISPLAY 
	</div>
	</c:if>


	<script>
		$jq( function(){ $jq("#Pagination1").displayTagAjax('paging');})
		$jq( function(){ $jq("#Pagination2").displayTagAjax('paging');})
	</script>
</form:form>
<!-- End : PromotionFinanceAcceptanceList.jsp -->
