<!-- Begin : QuarterRequestDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>

<div class="line">
    <div class="line">
     	
     	<div class="quarter bold">Quarter Application Form</div>
     	<div class="quarter bold"><a href="javascript:quarterApplication('${workflowmap.requestId}')">PDF</a></div> 
    </div>
    <div class="line">
     	<div class="quarter bold">Preffered Quarter</div>
     	<div class="quarter">${workflowmap.quarterDetails.eligibility}</div>
     	<div class="quarter bold">Priority Date</div>
     	<div class="quarter"><fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.quarterDetails.priorityDate}" /></div>
    </div> 
    <div class="line">
     	<div class="quarter bold">Allotment</div>
     	<div class="quarter">${workflowmap.quarterDetails.allotment}</div>
    </div> 
    
    <c:if test="${workflowmap.quarterDetails.allotment=='Y'}">
    <div class="line">
     		<div class="quarter bold">Alloted Date</div>
     		<div class="quarter"><fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.quarterDetails.allotedDate}"/></div>
     		
     		<div class="quarter bold">Quarter No</div>
     		<div class="quarter">${workflowmap.quarterNo}</div>
   </div> 
    </c:if>
    <c:if test="${workflowmap.lastStagePendingCheck=='lpending'}">
	    <div class="line">
	    	<!--<div class="quarter bold">Quarter Type</div>
	     	<div class="quarter" id="quarterTypeDiv">
	     		<form:input path="quarterType" id="quarterType"  readonly="true"/>
	     	
	     		<form:select path="quarterType" id="quarterType" cssClass="formSelect">
					<form:option value="0">Select</form:option>
					<form:options items="${workflowmap.quarterSubTypeList}" itemValue="id" itemLabel="quarterSubType"/>
				</form:select>
	     	</div>
	     	<div class="quarter bold">Alloted Date</div>
	     	<div class="quarter" id="allotedDateDiv">
	     		<form:input path="allotedDate" id="allotedDate" cssClass="dateClass" readonly="true"/>
				<img  src="./images/calendar.gif" id="allotedDateImg" />	
				  <script type="text/javascript">
					Calendar.setup({inputField :"allotedDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"allotedDateImg",singleClick : true,step : 1});
				
				</script>
	     	</div>  -->
	     </div> 
	     <div class="line">
	    	<!--<div class="quarter bold">Quarter Number</div>
	     	<div class="quarter" id="quarterNoDiv">
	     		 <form:input path="quarterNo" id="quarterNo" readonly="true"/> 
	     	</div>
	     	<div class="quarter bold">Occupied Date</div>
	     	<div class="quarter" id="occupiedDateDiv">
	     		<form:input path="occupiedDate" id="occupiedDate" cssClass="dateClass" readonly="true"/>
				<img  src="./images/calendar.gif" id="occupiedDateImg" />	
				 <script type="text/javascript">
					Calendar.setup({inputField :"occupiedDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"occupiedDateImg",singleClick : true,step : 1});
				</script>  
	     	</div>  -->
	     </div> 
    </c:if>
    <c:if test="${workflowmap.lastStagePendingCheck!='lpending' && workflowmap.quarterDetails.allotedQuarterID!=0 }">
	    <div class="line">
	    	<div class="quarter bold">Alloted Quarter</div>
	     	<div class="quarter">${workflowmap.quarterDetails.quarterDetails.quarterSubType}</div>
	     	<div class="quarter bold">Alloted Date</div>
	     	<div class="quarter"><fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.quarterDetails.allotedDate}" /></div>  
	     </div>  
	     <div class="line">
	     <div class="bold quarter">ION Form</div>
	     <div class="bold quarter"><a href="javascript:quarterION('${workflowmap.requestId}')">PDF</a></div>
	     </div>
    </c:if>	
</div>
<!-- End : QuarterRequestDetails.jsp -->