<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:empPaySlipDetails.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
.line{
	margin-bottom:15px;
}
</style>

		<fieldset>
					<div class="line">
							<div class="quarter">SFID: <strong>${payBill.sfId}</strong></div>
							<div class="quarter">Name: <strong>${payBill.nameInServiceBook}</strong></div>
							<div class="quarter">Designation: <strong>${payBill.designationName}</strong></div>
							<div class="quarter">Division: <strong>${payBill.departmentName}</strong></div>
							<div class="quarter">Month: <b>${payBill.paySlipMonth}-${payBill.paySlipYear}</b></div>
					</div>
		</fieldset>
		
		<div class="line">
			<table id="dataList" class="list" width="100%">
<thead>
<tr>
<th colspan="2" >Earnings</th>
<th colspan="4" >Deductions</th>
</tr>
</thead>
<tbody>
	<tr class="odd" >
		<td style="width:15%;text-align:left">Basic Pay</td>
		<td style="width:15%;text-align:right">${payBill.basicPay}</td>
		<td style="width:10%;text-align:left">GPF/CPS SUB</td>
		<td style="width:5%;text-align:right">${payBill.gpf}&nbsp;&nbsp;${payBill.gpfSa}</td>
		<td style="width:10%;text-align:left">ELEC</td>
		<td style="width:5%;text-align:right">${payBill.elec}</td>
	</tr>
	<tr class="even">
		<td style="width:15%;text-align:left">Grade Pay</td>
		<td style="width:15%;text-align:right">${payBill.gradePay}</td>
		<td style="width:10%;text-align:left">GPF/CPS R</td>
		<td style="width:5%;text-align:right">${payBill.gpfRecovery}</td>
		<td style="width:10%;text-align:left">Water</td>
		<td style="width:5%;text-align:right">${payBill.water}</td>
	</tr>	
	<tr class="odd" >
		<td style="width:15%;text-align:left">SPL Pay</td>
		<td style="width:15%;text-align:right">${payBill.specialPay}</td>
		<td style="width:10%;text-align:left">PLI</td>
		<td style="width:5%;text-align:right">${payBill.pli}</td>
		<td style="width:10%;text-align:left">FURN</td>
		<td style="width:5%;text-align:right">${payBill.furn}</td>
	</tr>
	<tr class="even">
		<td style="width:15%;text-align:left">DA</td>
		<td style="width:15%;text-align:right">${payBill.da}</td>
		<td style="width:10%;text-align:left">CGEIS</td>
		<td style="width:5%;text-align:right">${payBill.cegis}</td>
		<td style="width:10%;text-align:left">DR MISC</td>
		<td style="width:5%;text-align:right">${payBill.drMisc1+payBill.drMisc2}</td>
	</tr>	
	<tr class="odd" >
		<td style="width:15%;text-align:left">HRA</td>
		<td style="width:15%;text-align:right">${payBill.hra}</td>
		<td style="width:10%;text-align:left">CGHS</td>
		<td style="width:5%;text-align:right">${payBill.cghs}</td>
		<td style="width:10%;text-align:left">WFUND</td>
		<td style="width:5%;text-align:right">${payBill.welC}</td>
	</tr>
	<tr class="even">
		<td style="width:15%;text-align:left">TPT</td>
		<td style="width:15%;text-align:right">${payBill.tpt}</td>
		<td style="width:10%;text-align:left">CAR</td>
		<td style="width:5%;text-align:right">${payBill.carLoan}</td>
		<td style="width:10%;text-align:left">WLOAN</td>
		<td style="width:5%;text-align:right">${payBill.welRefund}</td>
	</tr>	
	<tr class="odd" >
		<td style="width:15%;text-align:left">Incentive</td>
		<td style="width:15%;text-align:right">${payBill.hindiIncentive+payBill.deputAllowance}</td>
		<td style="width:10%;text-align:left">Scooter</td>
		<td style="width:5%;text-align:right">${payBill.scooterLoan}</td>
		<td style="width:10%;text-align:left">MESS</td>
		<td style="width:5%;text-align:right">${payBill.mess}</td>
	</tr>
	<tr class="even">
		<td style="width:15%;text-align:left">Wash A/w</td>
		<td style="width:15%;text-align:right">${payBill.washAllowance}</td>
		<td style="width:10%;text-align:left">Cycle</td>
		<td style="width:5%;text-align:right">${payBill.cycleLoan}</td>
		<td style="width:10%;text-align:left">R.SEC</td>
		<td style="width:5%;text-align:right">${payBill.resSecu }</td>
	</tr>	
	<tr class="odd" >
		<td style="width:15%;text-align:left">Xerox A/w</td>
		<td style="width:15%;text-align:right">${payBill.xeroxAllowance}</td>
		<td style="width:10%;text-align:left">HBA</td>
		<td style="width:5%;text-align:right">${payBill.hbaLoan}</td>
		<td style="width:10%;text-align:left">B.FUND</td>
		<td style="width:5%;text-align:right">${payBill.benvolentFund}</td>
	</tr>
	<tr class="even">
		<td style="width:15%;text-align:left">Risk A/w</td>
		<td style="width:15%;text-align:right">${payBill.riskAllowance}</td>
		<td style="width:10%;text-align:left">PC LOAN</td>
		<td style="width:5%;text-align:right">${payBill.pcLoan}</td>
		<td style="width:10%;text-align:left">R.FUND</td>
		<td style="width:5%;text-align:right">${payBill.regimentalFund}</td>
	</tr>	
	<tr class="odd" >
		<td style="width:15%;text-align:left">Var.Inc</td>
		<td style="width:15%;text-align:right">${payBill.variableIncr}</td>
		<td style="width:10%;text-align:left">F.ADV</td>
		<td style="width:5%;text-align:right">${payBill.festivalAdv }</td>
		<td style="width:10%;text-align:left">CCS</td>
		<td style="width:5%;text-align:right">${payBill.ccs}</td>
	</tr>
	<tr class="even">
		<td style="width:15%;text-align:left">FPA</td>
		<td style="width:15%;text-align:right">${payBill.fpa}</td>
		<td style="width:10%;text-align:left">ITAX</td>
		<td style="width:5%;text-align:right">${payBill.incomeTax+payBill.cess+payBill.secondaryCess}</td>
		<td style="width:10%;text-align:left">CCs Rec</td>
		<td style="width:5%;text-align:right">${payBill.ccsrecovery}</td>
	</tr>
	<tr class="odd" >
		<td style="width:15%;text-align:left">Two ADD INCR</td>
		<td style="width:15%;text-align:right">${payBill.twoAddlIncr}</td>
		<td style="width:10%;text-align:left">P.TAX</td>
		<td style="width:5%;text-align:right">${payBill.profTax}</td>
		<td style="width:10%;text-align:left">HDFC</td>
		<td style="width:5%;text-align:right">${payBill.hdfc}</td>
	</tr>	
	<tr class="even">
		<td style="width:15%;text-align:left">Misc</td>
		<td style="width:15%;text-align:right">${payBill.crMisc}</td>
		<td style="width:10%;text-align:left">TADA</td>
		<td style="width:5%;text-align:right">${payBill.tada }</td>
		<td style="width:10%;text-align:left">CANFIN</td>
		<td style="width:5%;text-align:right">${payBill.canfin}</td>
	</tr>
	<tr class="odd" >
		<td style="width:15%;text-align:left"></td>
		<td style="width:15%;text-align:right"></td>
		<td style="width:10%;text-align:left">LTC</td>
		<td style="width:5%;text-align:right">${payBill.ltc}</td>
		<td style="width:10%;text-align:left">LIC</td>
		<td style="width:5%;text-align:right">${payBill.lic}</td>
	</tr>	
	<tr class="even">
		<td style="width:15%;text-align:left"></td>
		<td style="width:15%;text-align:right"></td>
		<td style="width:10%;text-align:left">MED</td>
		<td style="width:5%;text-align:right">${payBill.medical }</td>
		<td style="width:10%;text-align:left">GIC</td>
		<td style="width:5%;text-align:right">${payBill.gic}</td>
	</tr>
	<tr class="odd" >
		<td style="width:15%;text-align:left"></td>
		<td style="width:15%;text-align:right"></td>
		<td style="width:10%;text-align:left">EOL/HPL</td>
		<td style="width:5%;text-align:right">${payBill.eol}</td>
		<td style="width:10%;text-align:left">Court</td>
		<td style="width:5%;text-align:right">${payBill.courtAttachment}</td>
	</tr>	
	<tr class="even">
		<td style="width:15%;text-align:left"></td>
		<td style="width:15%;text-align:right"></td>
		<td style="width:10%;text-align:left">L/F</td>
		<td style="width:5%;text-align:right">${payBill.rent}</td>
		<td style="width:10%;text-align:left">RMisc</td>
		<td style="width:5%;text-align:right">${payBill.recMisc1+payBill.recMisc2+payBill.recMisc3}</td>
	</tr>
	<tr class="even">
		<td style="width:15%;text-align:right">Gross Pay:</td>
		<td style="width:10%;text-align:left">${payBill.totalCredits}</td>
		<td style="width:15%;text-align:right" colspan="3">Total Deductions:</td>
		<td style="width:10%;text-align:left">${payBill.totalDebits+payBill.totalRecovery}</td>
	</tr>
	<tr>
	<td style="width:15%;text-align:right" colspan="6">Take Home:${payBill.finalPay }</td>
		</tr>
		</tbody></table>
		</div>
	<div class="line">
		<div style="margin-left:90%">
		<a href="javascript:printMyPaySlip();" class="appbutton">Print</a>
		</div>
	</div>					
<!-- End:empPaySlipDetails.jsp -->