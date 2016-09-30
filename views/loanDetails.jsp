<!-- Begin : loanDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>

<div class="line">
	<div class="line" id="resultLoan"></div>
    <div class="line">
     	<div class="quarter bold">Loan Type</div>
     	<div class="quarter">${workflowmap.loanPaymentDetails.loanTypeDetails.loanName}</div>
     	<c:if test="${workflowmap.loanPaymentDetails.loanTypeID == '1'}">
    		<div class="quarter bold">Festival</div>
     		<div class="quarter">${workflowmap.keyValuePair.name}</div>
    	</c:if>
     	<c:if test="${(workflowmap.loanPaymentDetails.loanTypeID == '2') || (workflowmap.loanPaymentDetails.loanTypeID == '3')}">
    		<div class="quarter bold">Purpose</div>
     		<div class="quarter">${workflowmap.keyValuePair.name}  - ${workflowmap.keyValuePair.value}</div>
    	</c:if>
    	<c:if test="${workflowmap.loanHBAPaymentDetails.loanTypeID == '9'}">
    		<div class="quarter">House Building Loan</div>
    	</c:if>
    </div> 
	<div class="line">
     	<div class="quarter bold">Employment Type</div>
     	<div class="quarter">
	     	<c:choose>
	     	  	<c:when test="${workflowmap.loanHBAPaymentDetails.loanTypeID == '9'}">
		    		${workflowmap.loanHBARequestDetails.employmentDetails.name}
		    	</c:when>
		    	<c:otherwise>
		     		${workflowmap.loanRequestDetails.employmentDetails.name}
		     	</c:otherwise>
	     	</c:choose>
     	</div>
     	<div class="quarter bold">Department</div>
     	<div class="quarter">
	     	<c:choose>
		     	<c:when test="${workflowmap.loanHBAPaymentDetails.loanTypeID == '9'}">
		     		${workflowmap.loanHBARequestDetails.departmentDetails.deptName}
		     	</c:when>
		     	<c:otherwise>
		     		${workflowmap.loanRequestDetails.departmentDetails.deptName}
		     	</c:otherwise>
	     	</c:choose>
	     </div>
  	</div>    
    <div class="line">
    	<div class="quarter bold">Date of joining in DRDO</div>
     	<div class="quarter" id="dobDRDO">${workflowmap.dojDrdo}</div>
     	<div class="quarter bold">Date of Retirement or Superannuation</div>
     	<div class="quarter" id="retirement">${workflowmap.retirementDate}</div>
    </div>
    <div class="line">
    	<div class="quarter bold">Basic Pay</div>
    	<div class="quarter">
	    	<c:choose>
		     	<c:when test="${workflowmap.loanHBAPaymentDetails.loanTypeID == '9'}">
		     		<fmt:formatNumber  pattern="###" type="number" maxFractionDigits="0" value="${workflowmap.loanHBARequestDetails.basicPay}"/>
		     	</c:when>
		     	<c:otherwise>
		     		<fmt:formatNumber  pattern="###" type="number" maxFractionDigits="0" value="${workflowmap.loanRequestDetails.basicPay}"/>
		     	</c:otherwise>
		     </c:choose>
		</div>
		<div class="quarter bold">Grade Pay</div>
		<div class="quarter">
			<c:choose>
		     	<c:when test="${workflowmap.loanHBAPaymentDetails.loanTypeID == '9'}">
		     		<fmt:formatNumber  pattern="###" type="number" maxFractionDigits="0" value="${workflowmap.loanHBARequestDetails.gradePay}"/>
		     	</c:when>
		     	<c:otherwise>
		     		<fmt:formatNumber  pattern="###" type="number" maxFractionDigits="0" value="${workflowmap.loanRequestDetails.gradePay}"/>
		     	</c:otherwise>
	     	</c:choose>
	     </div>
    </div>
   <c:if test="${(workflowmap.loanPaymentDetails.loanTypeID == '2') || (workflowmap.loanPaymentDetails.loanTypeID == '3')}">
	    <div class="line">
	    	<div class="quarter bold">GPF A/C Number</div>
	     	<div class="quarter">${workflowmap.gpfNumber}</div>
	     	<div class="quarter bold">Previous Financial Year Closing Balance</div>
	     	<div class="quarter"><fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="2" value="${workflowmap.loanRequestDetails.gpfLoanBalance}"/></div>
	    </div>
    </c:if>
    <div class="line">
    	<div class="quarter bold">Requested Amount</div>
    	<div class="quarter">
	    	<c:choose>
		     	<c:when test="${workflowmap.loanHBAPaymentDetails.loanTypeID == '9'}">
		     		<span><fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="2" value="${workflowmap.loanHBAPaymentDetails.requestedAmount}"/></span>
		     	</c:when>
		     	<c:otherwise>
					<span><fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="2" value="${workflowmap.loanPaymentDetails.requestedAmount}"/></span>
		     	</c:otherwise>
	     	</c:choose>
	     	<c:if test="${workflowmap.lastStagePendingCheck=='lpending'}">
			     <span id="reqDiv" style="display:none">
			     	<form:input path="reqAmount" id="reqAmount" size="10" onkeypress="return isNumberExp(event);" /> 
			     </span>
			</c:if>
     	</div>
     	<c:if test="${(workflowmap.loanPaymentDetails.loanTypeID != '1')}">
	     	<div class="quarter bold">Requested Installments (Principle+Interest)</div>
	     	<div class="quarter">
		     	<c:choose>
			     	<c:when test="${workflowmap.loanHBAPaymentDetails.loanTypeID == '9'}">
			     		<span><fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="0" value="${workflowmap.loanHBAPaymentDetails.requestedInstalments}"/>+
			     		<fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="0" value="${workflowmap.loanHBAPaymentDetails.requestedInterestInstalments}"/></span>
			     	</c:when>
			     	<c:otherwise>
						<span><fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="0" value="${workflowmap.loanPaymentDetails.requestedInstalments}"/>+
			     		<fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="0" value="${workflowmap.loanPaymentDetails.requestedInterestInstalments}"/></span>
			     	</c:otherwise>
		     	</c:choose>
		     	<c:if test="${workflowmap.lastStagePendingCheck=='lpending'}">
		     			<span id="InsDiv" style="display:none">
		     				<form:input path="requestedInstalments" id="requestedInstalments" size="5" onkeypress="return isNumberExp(event);" />+
							<form:input path="requestedInterestInstalments" id="requestedInterestInstalments" size="5" onkeypress="return isNumberExp(event);" />
						</span>
				</c:if>
	     	</div>
	     	
	     	<c:if test="${workflowmap.lastStagePendingCheck=='lpending'}">
	     		<div class="line" id="buttons">
					<div style="margin-left: 80%">
						<a href="javascript:editInstallmentDetails();" class="appbutton" id="editButton">Edit</a> 
						<a style="display:none" id="saveButton" href="javascript:saveInstallmentDetails('${workflowmap.historyID}','${workflowmap.requestId}');" class="appbutton">Save</a>
					</div>
				</div>
			</c:if>
		</c:if>
    </div>
<c:if test="${(workflowmap.loanPaymentDetails.loanTypeID == '1') || (workflowmap.loanPaymentDetails.loanTypeID == '2') || (workflowmap.loanPaymentDetails.loanTypeID == '3') || (workflowmap.loanPaymentDetails.loanTypeID == '4') || (workflowmap.loanPaymentDetails.loanTypeID == '5')
    	 	|| (workflowmap.loanPaymentDetails.loanTypeID == '6') || (workflowmap.loanPaymentDetails.loanTypeID == '7') || (workflowmap.loanPaymentDetails.loanTypeID == '8')}">
		<div class="line">
	    	<div class="quarter bold">Loan Request Application Form</div>
	     	<div class="quarter"><a href="javascript:viewLoanForm('${workflowmap.loanPaymentDetails.loanTypeID}','${workflowmap.requestId}','application')">PDF</a></div>
	    </div>
    </c:if>
    <c:if test="${workflowmap.loanHBAPaymentDetails.loanTypeID == '9'}">
		<div class="line">
	    	<div class="quarter bold">HBA Loan Request Application Form</div>
	     	<div class="quarter"><a href="javascript:viewLoanForm('${workflowmap.loanHBAPaymentDetails.loanTypeID}','${workflowmap.requestId}','application')">PDF</a></div>
	     	<c:if test="${workflowmap.loanHBAPaymentDetails.status=='8'}">
	     	<div class="quarter bold">Contingent Bill</div>
		     	<div class="quarter"><a href="javascript:viewLoanForm('${workflowmap.loanHBAPaymentDetails.loanTypeID}','${workflowmap.requestId}','contingent')">PDF</a></div>
		     	<div class="line">
	   				 <div class="quarter bold">Sanction Report(1<sup>st</sup> Installment)</div>
		     			<div class="quarter"><a href="javascript:viewLoanForm('${workflowmap.loanHBAPaymentDetails.loanTypeID}','${workflowmap.requestId}','sanction')">PDF</a></div>
		     			<div class="quarter bold">Sanction Report(2<sup>nd</sup> Installment)</div>
		     			<div class="quarter"><a href="javascript:viewLoanForm('${workflowmap.loanHBAPaymentDetails.loanTypeID}','${workflowmap.requestId}','sanction1')">PDF</a></div>
				</div>
				
	     	</c:if>
	    </div>
    </c:if>
    <!-- Sanction amount details starts -->
      <c:if test="${workflowmap.lastStagePendingCheck=='lpending'}">
      <c:if test="${(workflowmap.loanPaymentDetails.loanTypeID == '1') || (workflowmap.loanPaymentDetails.loanTypeID == '2') || (workflowmap.loanPaymentDetails.loanTypeID == '3')}">
	    <div class="line">
	    	<div class="quarter bold">Sanctioned Amount<span class="mandatory">*</span></div>
	     	<div class="quarter"><form:input path="sanctionedAmount" id="sanctionedAmount" maxlength="50" onkeypress="return isNumberExp(event);"/></div>
	     	<div class="quarter bold">Sanctioned Date<span class="mandatory">*</span></div>
	     	<div class="quarter">
	     		<form:input path="sanctionedDate" id="sanctionedDate" cssClass="dateClass" readonly="true"/>
	     		<img  src="./images/calendar.gif" id="sanctionedDateImg" />	
				<script type="text/javascript">
					Calendar.setup({inputField :"sanctionedDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"sanctionedDateImg",singleClick : true,step : 1});
				</script>
	     	</div>	     	
	   </div>
	   <c:if test="${(workflowmap.loanPaymentDetails.loanTypeDetails.loanType=='46')|| (workflowmap.loanHBAPaymentDetails.loanTypeDetails.loanType=='46')}">
		   <div class="line">
		    	<div class="quarter bold">Sanctioned Installments<span class="mandatory">*</span></div>
		     	<div class="quarter">
		     		<form:input path="sanctionedInstalments" id="sanctionedInstalments" maxlength="50" onkeypress="return isNumberExp(event);"/>
		     	</div>
		   		<div class="quarter bold">Recovery Start Date<span class="mandatory">*</span></div>
		     	<div class="quarter">
		     		<form:input path="recStartDate" id="recStartDate" cssClass="dateClass" readonly="true"/>
		     		<img  src="./images/calendar.gif" id="recStartDateImg" />	
					<script type="text/javascript">
						Calendar.setup({inputField :"recStartDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"recStartDateImg",singleClick : true,step : 1});
					</script>
		     	</div>
		    </div>
	    </c:if>
	    </c:if>
	     <c:if test="${(workflowmap.loanPaymentDetails.loanTypeID == '2') || (workflowmap.loanPaymentDetails.loanTypeID == '3')}" >
	    <div class="line">
	    	<c:if test="${(workflowmap.loanPaymentDetails.loanTypeDetails.loanType=='46')|| (workflowmap.loanHBAPaymentDetails.loanTypeDetails.loanType=='46')}">
		    	<div class="quarter bold">EMI<span class="mandatory">*</span></div>
		     	<div class="quarter"><form:input path="emi" id="emi" maxlength="50" onkeypress="return isNumberExp(event);"/></div>
	     	</c:if>
	   		<div class="quarter bold">Reference Number<span class="mandatory">*</span></div>
	     	<div class="quarter"><form:input path="loanRefNo" id="loanRefNo" maxlength="50" /></div>
	    </div>
         <div class="line">
	     	<div class="quarter bold">Bank Account No<span class="mandatory">*</span></div>
	     	<div class="quarter"><form:input path="bankAccount" id="bankAccount" maxlength="50" /></div>
	     	<div class="quarter bold">Bank Account Branch<span class="mandatory">*</span></div>
	     	<div class="quarter"><form:input path="bankBranch" id="bankBranch" maxlength="50" /></div>
	    </div>
	    </c:if>
	    <script>$jq('input:text').val("");</script>
    
    </c:if>
    <!-- Sanction amount details end -->
    <!-- GPF reports start -->
   <c:if test="${workflowmap.loanPaymentDetails.status=='8'}">     
    	<div class="line">
	    	<div class="quarter bold">Sanctioned Amount<span class="mandatory">*</span></div>
	     	<div class="quarter"><fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="2" value="${workflowmap.loanPaymentDetails.sanctionedAmount}"/></div>
	     	<div class="quarter bold">Sanctioned Date<span class="mandatory">*</span></div>
	     	<div class="quarter"><fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.loanPaymentDetails.sanctionedDate}" /></div>
	   </div>
	   <c:if test="${workflowmap.loanPaymentDetails.loanTypeDetails.loanType=='46'}">
		    <div class="line">
		    	<div class="quarter bold">Sanctioned Installments<span class="mandatory">*</span></div>
		     	<div class="quarter"><fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="0" value="${workflowmap.loanPaymentDetails.sanctionedInstalments}"/></div>
		   		<div class="quarter bold">Recovery Start Date<span class="mandatory">*</span></div>
		     	<div class="quarter"><fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.loanPaymentDetails.recStartDate}" /></div>
		    </div>
	   </c:if>
	    <div class="line">
	    	 <c:if test="${workflowmap.loanPaymentDetails.loanTypeDetails.loanType=='46'}">
		    	<div class="quarter bold">EMI<span class="mandatory">*</span></div>
		     	<div class="quarter"><fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="2" value="${workflowmap.loanPaymentDetails.emi}"/></div>
	   		</c:if>
	   		<div class="quarter bold">Reference Number<span class="mandatory">*</span></div>
	     	<div class="quarter">${workflowmap.loanPaymentDetails.loanRefNo}</div>
	    </div>
	    
	     <div class="line">
	     	<div class="quarter bold">Bank Account No<span class="mandatory">*</span></div>
	     	<div class="quarter">${workflowmap.loanPaymentDetails.bankAccount}</div>
	     	<div class="quarter bold">Bank Account Branch<span class="mandatory">*</span></div>
	     	<div class="quarter">${workflowmap.loanPaymentDetails.bankBranch}</div>
	    </div>
    	
    	<!-- GPF reports start -->
    	<c:if test="${(workflowmap.loanPaymentDetails.loanTypeID == '2') || (workflowmap.loanPaymentDetails.loanTypeID == '3')}">
    		 <div class="line">
		    	<div class="quarter bold">Contingent Bill</div>
		     	<div class="quarter"><a href="javascript:viewLoanForm('${workflowmap.loanPaymentDetails.loanTypeID}','${workflowmap.requestId}','contingent')">PDF</a></div>
		     	<div class="quarter bold">GPF Claim</div>
		     	<div class="quarter"><a href="javascript:viewLoanForm('${workflowmap.loanPaymentDetails.loanTypeID}','${workflowmap.requestId}','claim')">PDF</a></div>
		    </div>
		    <div class="line">
		    	<div class="quarter bold">Sanction Report</div>
		     	<div class="quarter"><a href="javascript:viewLoanForm('${workflowmap.loanPaymentDetails.loanTypeID}','${workflowmap.requestId}','sanction')">PDF</a></div>
		     	<div class="quarter bold">Debit Schedule</div>
		     	<div class="quarter"><a href="javascript:viewLoanForm('${workflowmap.loanPaymentDetails.loanTypeID}','${workflowmap.requestId}','debit')">PDF</a></div>
		   </div>
		   <c:if test="${workflowmap.loanPaymentDetails.loanTypeDetails.loanType=='45'}">	
			   <div class="line">
			    	<div class="quarter bold">Grant of final withdrawal</div>
			     	<div class="quarter"><a href="javascript:viewLoanForm('${workflowmap.loanPaymentDetails.loanTypeID}','${workflowmap.requestId}','certificate')">PDF</a></div>
			   </div>
		   </c:if>	   
    	</c:if>
    	<!-- GPF reports end -->
    	
    	<!-- Motor reports start -->
    	<c:if test="${(workflowmap.loanPaymentDetails.loanTypeID == '4') || (workflowmap.loanPaymentDetails.loanTypeID == '5')
    	 	|| (workflowmap.loanPaymentDetails.loanTypeID == '6') || (workflowmap.loanPaymentDetails.loanTypeID == '7') || (workflowmap.loanPaymentDetails.loanTypeID == '8')}">
    		<div class="line">
		    	<div class="quarter bold">Certificate of sanction</div>
		     	<div class="quarter"><a href="javascript:viewLoanForm('${workflowmap.loanPaymentDetails.loanTypeID}','${workflowmap.requestId}','certificate')">PDF</a></div>
		     	<div class="quarter bold">Contingent Bill</div>
		     	<div class="quarter"><a href="javascript:viewLoanForm('${workflowmap.loanPaymentDetails.loanTypeID}','${workflowmap.requestId}','contingent')">PDF</a></div>
		    </div>
		    <div class="line">
		    <div class="quarter bold">Form of agreement</div>
		     	<div class="quarter"><a href="javascript:viewLoanForm('${workflowmap.loanPaymentDetails.loanTypeID}','${workflowmap.requestId}','agreement')">PDF</a></div>
		    </div>
    	</c:if>
    	<!-- Motor reports End --> 
    </c:if>
     <!-- GPF reports end --> 
</div>
<!-- End : loanDetails.jsp -->