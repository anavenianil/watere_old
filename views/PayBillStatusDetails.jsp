
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<div>
<div class="line"><hr></hr></div>
<div>
<jsp:include page="Result.jsp"></jsp:include>
</div>
<div class="line">
        <div class="quarter bold">Month</div>
		<div class="quarter bold">Pay Auto Run</div>
		<div class="quarter bold">Pay Individual Update</div>
		<div class="quarter bold">Show to Employees</div>
</div>
		<div class="line">
		<div class="quarter bold"><font color="green">${payBill.runMonth}</font><br></br><font color="purple">Employees :${payBill.count}<br></br>Generated :${payBill.generatedCount}</font></div>
				<div class="quarter" >
				<c:if test="${payBill.runStatus eq 1}">
				<div ><div class="expbutton" ><a onclick="javascript:startAutoRun()"><span>Start Auto Run</span></a></div></div>
				</c:if>
				<c:if test="${payBill.runStatus eq 0 and payBill.manualStatus eq 1}">
					<div ><div class="expbutton" id="autorun"><a onclick="javascript:startRegenerating()"> <span>Regenerate</span></a></div></div>
					</c:if>
					<c:if test="${payBill.runStatus eq 0 and payBill.manualStatus eq 2}">
					Auto Run Completed
					</c:if>
				</div>
				<div class="quarter">
			    <c:if test="${payBill.manualStatus eq 1}">
				<div ><div class="expbutton" id="manual"><a onclick="javascript:changeStatusToVisible()" > <span>Change Status</span></a></div></div>
				</c:if>
				<c:if test="${payBill.manualStatus eq 2}">
				Manual Updates Completed
				</c:if>
				</div>
				<div class="quarter">
				<c:if test="${payBill.userStatus eq 0}">
				<div ><div class="expbutton"><a onclick="javascript:alertAutoRunManual()"> <span>Show to User</span></a></div></div>
				</c:if>
				<c:if test="${payBill.userStatus eq 1}">
				<div ><div class="expbutton" id="completePay"><a onclick="javascript:completePayBill()"> <span>Show to User</span></a></div></div>
				</c:if>
				<c:if test="${payBill.userStatus eq 2}">
				pay bill visible to user
				</c:if>
				</div>
		</div>
		<div class="line"  id="regenerate">
			<div class="quarter bold">&nbsp;</div>
			<c:if test="${payBill.runStatus eq 0  and payBill.manualStatus eq 1}">Auto Run Completed</c:if>
		</div>
	    <div class="line"><hr></hr></div>
		<c:if test="${payBill.manualStatus eq 1}">
		              <div class="line" id="result1"></div>
					  <div class="line">
					  <div class="quarter bold">Start Remaining Employee payBill</div>
					  <div class="quarter">
					  <div class="expbutton"><a onclick="javascript:runPendingPayBill()"> <span>Run</span></a></div>
					  </div></div>			
       </c:if>							
</div>											
									
									