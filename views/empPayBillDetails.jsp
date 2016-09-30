<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:empPayBillDetails.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<script type="text/javascript">
$jq(function() {
	var $tabs = $('#tabs').tabs();
	$jq(".ui-tabs-panel").each(function(i){
		var totalSize = $jq(".ui-tabs-panel").size() - 1;

		if (i != totalSize) {
			next = i + 2;
			$jq(this).append("<a style='margin-left:75%' href='#' class='next-tab mover' rel='" + next + "'>Next Page &#187;</a>");
			
		}
  
		if (i != 0) {
			prev = i;
			$jq(this).append("<a  style='margin-left:0%'href='#' class='prev-tab mover' rel='" + prev + "'>&#171; Prev Page</a>");
			
		}
  		
	});

	$jq('.next-tab, .prev-tab').click(function() { 
		$tabs.tabs('select', $(this).attr("rel"));
		return false;
	});
      
});

</script>
<style>
.line{
	margin-bottom:15px;
}
</style>
<div class="line" id="result1"><font color="#ff0000">${payBill.message}</font></div>
<fieldset>
<div class="line">
					<div class="quarter">SFID: <b>${payBill.searchSfid}</b></div>
										<div class="quarter">
											Name:<b> ${payBill.nameInServiceBook}</b>
										</div>
										<div class="quarter ">Desig:<b> ${payBill.designationName}</b></div>
										<div class="quarter">
											Division: <b>${payBill.departmentName}</b>
										</div>
										<br/><br/>
										<div class="half" style="margin-left: 35%;">
											Run Month:<b><font color="green" size="4">${payBill.runMonth}</font></b>
										</div>
										</div>
										
					
</fieldset>
<div class="line">
					<div class="quarter">Created By: <b>${payBill.createdBy}</b></div>
					<div class="quarter">Creation Date:<b><fmt:formatDate value="${payBill.creationTime}" pattern="dd-MMM-yyyy"/></b></div>
					<div class="quarter ">Last Modified By:<b> ${payBill.lastModifiedBy}</b></div>
					<div class="quarter">Last Modified Date: <b><fmt:formatDate value="${payBill.lastModifiedTime}" pattern="dd-MMM-yyyy"/></b></div>
</div>
<div class="line">
	<div id="page-wrap">		
		<div id="tabs">
			<ul>        		
        		<li><a href="#fragment-1" onclick="javascript:clearPayMessage()">Credits</a></li>
        		<li><a href="#fragment-2" onclick="javascript:clearPayMessage()">Debits</a></li>
        		<li><a href="#fragment-3" onclick="javascript:clearPayMessage()">Recoveries</a></li>
        	</ul>
	     	<div id="fragment-1" class="ui-tabs-panel">
	     	<div class="line">
					<div class="quarter bold">
					<div style="float:left;margin-left:15px;">Basic</div>
					<div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.basicPay" id="basicPay" onkeypress="return checkTwoDigitFloat(event,'basicPay');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind>
						</div>
					</div>
					<div class="quarter bold">
					<div style="float:left;margin-left:15px;">2 Addl Incr</div>
					<div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.twoAddlIncr" id="twoAddlIncr" onkeypress="return checkTwoDigitFloat(event,'twoAddlIncr');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Dep Allow</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.deputAllowance" id="deputAllowance" onkeypress="return checkTwoDigitFloat(event,'deputAllowance');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Hindi Inc</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.hindiIncentive" id="hindiIncentive" onkeypress="return checkTwoDigitFloat(event,'hindiIncentive');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
				</div>
				
				<div class="line">
					<div class="quarter bold"><div style="float:left;margin-left:15px;">GPay</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.gradePay" id="gradePay" onkeypress="return checkTwoDigitFloat(event,'gradePay');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">S.pay</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.specialPay" id="specialPay" onkeypress="return checkTwoDigitFloat(event,'specialPay');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">FPA</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.fpa" id="fpa" onkeypress="return checkTwoDigitFloat(event,'fpa');" size="5" onblur="javascript:sumCredits()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">V.Incr</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.variableIncr" id="variableIncr" onkeypress="return checkTwoDigitFloat(event,'variableIncr');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
				</div>
				<div class="line">
					<div class="quarter bold"><div style="float:left;margin-left:15px;">DA</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.da" id="da" onkeypress="return checkTwoDigitFloat(event,'da');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">TPT</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill"> 
							<form:input path="payBill.tpt" id="tpt" onkeypress="return checkTwoDigitFloat(event,'tpt');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Xerox Allow</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.xeroxAllowance" id="xeroxAllowance" onkeypress="return checkTwoDigitFloat(event,'xeroxAllowance');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Misc</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.crMisc" id="crMisc" onkeypress="return checkTwoDigitFloat(event,'crMisc');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
				</div>
				<div class="line">
					<div class="quarter bold"><div style="float:left;margin-left:15px;">HRA</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.hra" id="hra" onkeypress="return checkTwoDigitFloat(event,'hra');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Wash Allow</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.washAllowance" id="washAllowance" onkeypress="return checkTwoDigitFloat(event,'washAllowance');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Risk Allow</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.riskAllowance" id="riskAllowance" onkeypress="return checkTwoDigitFloat(event,'riskAllowance');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Total Credits</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.totalCredits" id="totalCredits" onkeypress="return checkTwoDigitFloat(event,'totalCredits');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
				</div>
				
				<div class="line">
					<div class="line bold">Remarks
					<spring:bind path="payBill">
							<form:input path="payBill.crRemarks" id="crRemarks" size="80" />
						</spring:bind>
					</div>
				</div>
			
			</div>
     	
     		 <div id="fragment-2" class="ui-tabs-panel ui-tabs-hide">
                
				<div class="line">
					<div class="quarter bold"><div style="float:left;margin-left:15px;">CGHS</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.cghs" id="cghs" onkeypress="return checkTwoDigitFloat(event,'cghs');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">I.Tax</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.incomeTax" id="incomeTax" onkeypress="return checkTwoDigitFloat(event,'incomeTax');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Cycle (${payBill.cycleInstNumb}/${payBill.cycleTotInst})</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.cycleLoan" id="cycleLoan" onkeypress="return checkTwoDigitFloat(event,'cycleLoan');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Rent</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.rent" id="rent" onkeypress="return checkTwoDigitFloat(event,'rent');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
				</div>
				
				<div class="line">
					<div class="quarter bold"><div style="float:left;margin-left:15px;">CEGIS</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.cegis" id="cegis" onkeypress="return checkTwoDigitFloat(event,'cegis');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Cess</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.cess" id="cess" onkeypress="return checkTwoDigitFloat(event,'cess');" size="5"  onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Scooter (${payBill.scooterInstNumb}/${payBill.scooterTotInst})</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.scooterLoan" id="scooterLoan" onkeypress="return checkTwoDigitFloat(event,'scooterLoan');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Water</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.water" id="water" onkeypress="return checkTwoDigitFloat(event,'water');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
				</div>
				<div class="line">
					<div class="quarter bold"><div style="float:left;margin-left:15px;">
					<c:if test="${payBill.member eq 'No'}">CPS</c:if>
					<c:if test="${payBill.member eq 'Yes'}">GPF</c:if>
					</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.gpf" id="gpf" onkeypress="return checkTwoDigitFloat(event,'gpf');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">SHEC</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.secondaryCess" id="secondaryCess" onkeypress="return checkTwoDigitFloat(event,'secondaryCess');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Car (${payBill.carInstNumb}/${payBill.carTotInst})</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.carLoan" id="carLoan" onkeypress="return checkTwoDigitFloat(event,'carLoan');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Elec</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.elec" id="elec" onkeypress="return checkTwoDigitFloat(event,'elec');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
				</div>
				<div class="line">
					<div class="quarter bold"><div style="float:left;margin-left:15px;">
                    <c:if test="${payBill.member eq 'No'}">CPS SA</c:if>
					<c:if test="${payBill.member eq 'Yes'}">GPF SA</c:if></div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.gpfSa" id="gpfSa" onkeypress="return checkTwoDigitFloat(event,'gpfSa');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Prof Tax</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.profTax" id="profTax" onkeypress="return checkTwoDigitFloat(event,'profTax');" size="5"  onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">PC (${payBill.pcInstNumb}/${payBill.pcTotInst})</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.pcLoan" id="pcLoan" onkeypress="return checkTwoDigitFloat(event,'pcLoan');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Furn</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.furn" id="furn" onkeypress="return checkTwoDigitFloat(event,'furn');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
				</div>
				<div class="line">
					<div class="quarter bold"><div style="float:left;margin-left:15px;">
                    <c:if test="${payBill.member eq 'No'}">CPS REC</c:if>
					<c:if test="${payBill.member eq 'Yes'}">GPF REC</c:if></div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.gpfRecovery" id="gpfRecovery" onkeypress="return checkTwoDigitFloat(event,'gpfRecovery');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">LTC</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.ltc" id="ltc" onkeypress="return checkTwoDigitFloat(event,'ltc');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">HBA (${payBill.hbaInstNumb}/${payBill.hbaTotInst})</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.hbaLoan" id="hbaLoan" onkeypress="return checkTwoDigitFloat(event,'hbaLoan');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">EOL/HPL</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.eol" id="eol" onkeypress="return checkTwoDigitFloat(event,'eol');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
				</div>
				
				
				<div class="line">
					<div class="quarter bold"><div style="float:left;margin-left:15px;">PLI</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.pli" id="pli" onkeypress="return checkTwoDigitFloat(event,'pli');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Medi</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.medical" id="medical" onkeypress="return checkTwoDigitFloat(event,'medical');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Fest ADV (${payBill.festivInstNumb}/${payBill.festivTotInst})</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.festivalAdv" id="festivalAdv" onkeypress="return checkTwoDigitFloat(event,'festivalAdv');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">TADA</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.tada" id="tada" onkeypress="return checkTwoDigitFloat(event,'tada');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
				</div>
				<div class="line">
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Misc1</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.drMisc1" id="drMisc1" onkeypress="return checkTwoDigitFloat(event,'drMisc1');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Misc2</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.drMisc2" id="drMisc2" onkeypress="return checkTwoDigitFloat(event,'drMisc2');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Total Debits</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.totalDebits" id="totalDebits" onkeypress="return checkTwoDigitFloat(event,'totalDebits');" size="5" readonly="true"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Net Pay</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.netPay" id="netPay" onkeypress="return checkTwoDigitFloat(event,'netPay');" size="5" readonly="true"  maxlength="6"/>
						</spring:bind></div>
					</div>
				</div>
				
				<div class="line">
					<div class="line bold">Remarks
					<spring:bind path="payBill">
							<form:input path="payBill.drRemarks" id="drRemarks"  size="50"/>
						</spring:bind>
					</div>
				</div>
			</div>
    	         	
         	<div id="fragment-3" class="ui-tabs-panel ui-tabs-hide">
    			
        				
				<div class="line">
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Ccsc</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.ccs" id="ccs" onkeypress="return checkTwoDigitFloat(event,'ccs');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Mess</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.mess" id="mess" onkeypress="return checkTwoDigitFloat(event,'mess');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Lic (${payBill.licInstNumb}/${payBill.licTotInst})</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.lic" id="lic" onkeypress="return checkTwoDigitFloat(event,'lic');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Secu</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.resSecu" id="resSecu" onkeypress="return checkTwoDigitFloat(event,'resSecu');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
				</div>
				
				<div class="line">
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Ccsr</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.ccsrecovery" id="ccsrecovery" onkeypress="return checkTwoDigitFloat(event,'ccsrecovery');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Reg</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.regimentalFund" id="regimentalFund" onkeypress="return checkTwoDigitFloat(event,'regimentalFund');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Canfin (${payBill.canfinInstNumb}/${payBill.canfinTotInst})</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.canfin" id="canfin" onkeypress="return checkTwoDigitFloat(event,'canfin');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Misc1</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.recMisc1" id="recMisc1" onkeypress="return checkTwoDigitFloat(event,'recMisc1');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
				</div>
				<div class="line">
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Welc</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.welC" id="welC" onkeypress="return checkTwoDigitFloat(event,'welC');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Ben</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.benvolentFund" id="benvolentFund" onkeypress="return checkTwoDigitFloat(event,'benvolentFund');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Gic (${payBill.gicInstNumb}/${payBill.gicTotInst})</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.gic" id="gic" onkeypress="return checkTwoDigitFloat(event,'gic');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Misc2</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.recMisc2" id="recMisc2" onkeypress="return checkTwoDigitFloat(event,'recMisc2');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
				</div>
				<div class="line">
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Welcr (${payBill.welInst}/${payBill.welTotinst})</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.welRefund" id="welRefund" onkeypress="return checkTwoDigitFloat(event,'welRefund');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">HDFC (${payBill.hdfcInstNumb}/${payBill.hdfcTotInst})</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.hdfc" id="hdfc" onkeypress="return checkTwoDigitFloat(event,'hdfc');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">CortAtch (${payBill.courtInstNumb}/${payBill.courtTotInst})</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.courtAttachment" id="courtAttachment" onkeypress="return checkTwoDigitFloat(event,'courtAttachment');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Misc3</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.recMisc3" id="recMisc3" onkeypress="return checkTwoDigitFloat(event,'recMisc2');" size="5" onblur="javascript:sumAll()"  maxlength="6"/>
						</spring:bind></div>
					</div>
				</div>
				<div class="line">
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Tot Rec</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.totalRecovery" id="totalRecovery" onkeypress="return checkTwoDigitFloat(event,'totalRecovery');" size="5"  maxlength="6"/>
						</spring:bind></div>
					</div>
					<div class="quarter bold"><div style="float:left;margin-left:15px;">Final Pay</div><div style="float:right;margin-right:15px;">
					<spring:bind path="payBill">
							<form:input path="payBill.finalPay" id="finalPay" onkeypress="return checkTwoDigitFloat(event,'finalPay');" size="5"  maxlength="6"/>
						</spring:bind></div>
					</div>
					
				</div>
				<div class="line">
					<div class="line bold">Remarks
					<spring:bind path="payBill">
							<form:input path="payBill.recRemarks" id="recRemarks"  size="50"/>
						</spring:bind>
					</div>
				</div>
					
         	</div>  
        	</div>
 	</div>
 	       	
</div>
	
	<fieldset><div class="line">
					<div class="quarter bold"><font size="3">Tot Credits</font>
					<spring:bind path="payBill">
							<form:input path="payBill.totalCredits1" id="totalCredits1"  size="5" readonly="true"/>
						</spring:bind>
					</div>
					<div class="quarter bold"><font size="3">Tot Debits</font>
					<spring:bind path="payBill">
							<form:input path="payBill.totalDebits1" id="totalDebits1"  size="5" readonly="true"/>
						</spring:bind>
					</div>
					<div class="quarter bold"><font size="3">NetPay</font>
					<spring:bind path="payBill">
							<form:input path="payBill.netPay1" id="netPay1"  size="5" readonly="true"/>
						</spring:bind>
					</div>
					<div class="quarter bold"><font size="3">Recoveries</font>
					<spring:bind path="payBill">
							<form:input path="payBill.totalRecovery1" id="totalRecovery1"  size="5" readonly="true"/>
						</spring:bind>
					</div>
				</div>
					<div class="line">
					<div class="quarter bold"><font size="3">Take Home</font>
					<spring:bind path="payBill">
							<form:input path="payBill.finalPay1" id="finalPay1"  size="5" readonly="true"/>
						</spring:bind>
					</div>
					<div style="margin-left:80%"><div class="expbutton"><a onclick="javascript:updateEmpPayDetails()"> <span>Update</span></a></div></div>
	                </div>
					</fieldset>
				
<!-- End:empPayBillDetails.jsp -->