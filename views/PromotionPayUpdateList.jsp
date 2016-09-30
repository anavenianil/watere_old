<!-- Begin : PromotionPayUpdateList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<div id="resultMsg">
<jsp:include page="Result.jsp"></jsp:include>
</div>

<div>
<font color="brown">Note:1)New Pay is calculated form Minimum Value of Calculated Basic Pay,Basic Pay Form Master,80000<br></br>
                         2)Calculated Basic Pay is calculated from (Basic Pay +(no of increments * slab)) 


</font>
</div>
</br>
<div><input id="checkbox-1-1" class="regular-checkbox" disabled="disabled"  type="checkbox"><label for="checkbox-1-1"></label>&nbsp&nbspYou can Check 
&nbsp&nbsp&nbsp<input id="checkbox-1-1" class="regularrkr-checkbox" disabled="disabled"  type="checkbox"><label for="checkbox-1-1"></label>&nbsp&nbspDisabled</div>
<div class="line" id="payFixationDiv" >
<div id="Pagination1">
<%int i=1; %>
	<display:table name="${sessionScope.assessmentList}" excludedParams="*"
		export="false" class="list" requestURI="" id="fixationList" pagesize="1000"
		sort="page" >
		<display:column  sortProperty="optionStatus" style="width:5%;text-align:center" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxPromotionCheckAll(this.name,\'row\');"/>'>
			<c:if test="${fixationList.optionStatus == 100 || empty fixationList.fixationAcceptedDate}">
			 <input type="checkbox" class="row regular-checkbox" name="row" id="${fixationList.id}#${fixationList.assessmentID}" onclick="checkBoxCheck('row');"/>
			 <label for="${fixationList.id}#${fixationList.assessmentID}"></label></c:if>
		   <c:if test="${fixationList.optionStatus == 100 &&  fixationList.payStatus ==2}">
			 <input type="checkbox" class="row regularrkr-checkbox" name="row" disabled="disabled" id="${fixationList.id}#${fixationList.assessmentID}" onclick="checkBoxCheck('row');"/>
			 <label for="${fixationList.id}#${fixationList.assessmentID}"></label>
			</c:if>	 
		    
		   
		</display:column>
		
		<display:column title="SFID" style="width:5%" sortable="true" sortProperty="optionStatus">
			${fixationList.sfID}			
		</display:column>
		<display:column title="Name" style="width:7%" sortable="true" sortProperty="optionStatus">
			${fixationList.empName}			
		</display:column>
		<c:choose>
		<c:when test="${fixationList.payStatus ==2}">
		<display:column title="Pay Status" style="width:7%" sortable="true">Lower Grade</display:column>
		</c:when>
		<c:when test="${fixationList.payStatus ==1}">
		<display:column title="Pay Status" style="width:7%" sortable="true">Higher Grade</display:column>
		</c:when>
		</c:choose>	
		<display:column title="DesignationFrom-DesignationTo" style="width:8%" sortable="true"><div id="${fixationList.toDesignation}">${fixationList.designation}-${fixationList.designationTo}</div></display:column>								
		<display:column title="Promotion Date" style="width:5%" sortable="true"><fmt:formatDate pattern="dd-MMM-yyyy" value="${fixationList.promotionDate}"/></display:column>				
		<display:column title="Variable Inc.Start Date" style="width:5%" sortable="true"><fmt:formatDate pattern="dd-MMM-yyyy" value="${fixationList.effectiveDate}"/></display:column>				
		<display:column title=" Old Basic Pay+Grade Pay" style="width:10%" sortable="true" >${fixationList.basicPay}+${fixationList.gradePay}</display:column>				
		<display:column title="Pay Band" style="width:10%">${fixationList.masterBasicPay}</display:column>
		<display:column title="Calculated Basic Pay" style="width:10%">${fixationList.calculatedBasicPay}</display:column>
		
		
		<display:column title="New Basic Pay" style="width:10%" sortable="true" >
			<input type="text" name="newBasicPay" id="newBasicPay<%=i %>" value="${fixationList.newBasicPay}" size="5" onkeypress="return checkFloat(event,'newBasicPay<%=i %>');" onchange="javascript:clickRadio(this)"/>
		</display:column>
		<display:column title="New Grade Pay" style="width:10%" sortable="true" >
			<input type="text" name="newGradePay" id="newGradePay<%=i %>" value="${fixationList.newGradePay}" size="5" onkeypress="return checkFloat(event,'newGradePay<%=i %>');" onchange="javascript:clickRadio(this)"/>
		</display:column>
		<display:column title="Two Additional Incr" style="width:8%" sortable="true" >
			<input type="text" name="newTwoAddIncr" id="newTwoAddIncr<%=i %>" value="${fixationList.newTwoAddIncr}" size="5" onkeypress="return checkFloat(event,'newTwoAddIncr<%=i %>');" onchange="javascript:clickRadio(this)"/>
		</display:column>
			<display:column title="Emp Reported Date" style="width:25%;vertical-align:middle">&nbsp;
			
			<input type="text" size="13" value='<fmt:formatDate pattern="dd-MMM-yyyy" value="${fixationList.fixationAcceptedDate}"/>'  readonly="readonly" name="AcceptedDate" id="AcceptedDate<%=i %>"/>
			
			<img  src="./images/calendar.gif" id="AcceptedDateImg<%=i %>" />
				<script type="text/javascript">
					Calendar.setup({inputField :"AcceptedDate<%=i %>",ifFormat : "%d-%b-%Y",showsTime : false,button :"AcceptedDateImg<%=i %>",singleClick : true,step : 1});
				</script>
		   </display:column>
		
		
		
		<display:column title="Variable Increments" style="width:5%" ><div id="${fixationList.variableIncr}"><fmt:formatNumber value="${fixationList.variableIncr}"></fmt:formatNumber></div></display:column>
		<display:column title="Report" style="width:5%"><a href="javascript:payFixationCCS('${fixationList.sfID}')"><c:if test="${not empty fixationList.fixationAcceptedDate}">Print</c:if></a></display:column>				
		
		<%i++; %>	
	</display:table>
</div>
</div>

	<script>
		$jq( function(){ $jq("#Pagination1").displayTagAjax('paging');})
		
	</script>

<!-- End : PromotionPayUpdateList.jsp -->
