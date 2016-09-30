<!-- begin:ITaxConfigList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ page import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.incometax.dto.IncomeTaxConfigDTO" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="payStyles/displaytag.css" />
<link href="payStyles/layout.css" rel="stylesheet" type="text/css" />
<link href="payStyles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="payStyles/tabs.css" rel="stylesheet" type="text/css" />
<link href="payStyles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="payScript/calendar.js"></script>
<script type="text/javascript" src="payScript/calendar-en.js"></script>
<script type="text/javascript" src="payScript/calendar-setup_3.js"></script>
<script type="text/javascript" src="payScript/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="payScript/jquery.min.js"></script>
<script type="text/javascript" src="payScript/jquery.ui.custom.min.js"></script>
<script type="text/javascript" src="payScript/RegExpValidate.js"></script>

<link href="./payStyles/calendar-win2k-cold-1.css" type="text/css"
	rel="stylesheet" />
<script type="text/javascript">
$jq(function() {
	var $tabs = $('#tabs').tabs();
	$jq(".ui-tabs-panel").each(function(i){
		var totalSize = $jq(".ui-tabs-panel").size() - 1;

		if (i != totalSize) {
			next = i + 2;
			$jq(this).append("<a href='#' class='next-tab mover' rel='" + next + "'>Next Page &#187;</a>");
			
		}
  
		if (i != 0) {
			prev = i;
			$jq(this).append("<a href='#' class='prev-tab mover' rel='" + prev + "'>&#171; Prev Page</a>");
			
		}
  		
	});

	$jq('.next-tab, .prev-tab').click(function() { 
		$tabs.tabs('select', $(this).attr("rel"));
		return false;
	});
      
});
</script>
<script>
		
		<%if (session.getAttribute("configDetailsList") != null) {%>
			incomeTaxConfigListJSON =<%=(net.sf.json.JSONArray) JSONSerializer.toJSON((List<IncomeTaxConfigDTO>) session.getAttribute("configDetailsList"))%>;
		<%}%>
			
		
	</script>

<div id="result"><jsp:include page="Result.jsp"></jsp:include></div>
<style>
.line {
	margin-bottom: 15px;
}
</style>
<br>
<fieldset>
<div class="line">
<div class="quarter"><b>SFID:</b>${seachsfid}</div>
<div class="quarter"><b>Name:</b>${incomeTaxMaster.nameInServiceBook}</div>
<div class="quarter"><b>Division:</b> ${incomeTaxMaster.division}</div>
<div class="quarter"><b>Desig:</b>
${incomeTaxMaster.designationName}</div>
</div>

<div class="line">
<div class="quarter"><b>Financial Year:</b>
${incomeTaxMaster.financialYear}</div>
</div>
</fieldset>
<fieldset><legend><strong><font
	color='green'>Configuration Details</font></strong></legend>
<div class="line">
<div id="page-wrap">
<div id="tabs">
<ul>
    <li><a href="#fragment-1">PAY DETAILS</a></li>
    <li><a href="#fragment-2">ARREARS</a></li>
	<li><a href="#fragment-3">OTHER INCOME</a></li>
	<li><a href="#fragment-4">SAVINGS</a></li>
	<li><a href="#fragment-5">DEDUCTIONS</a></li>
	<li><a href="#fragment-6">RENT</a></li>
</ul>
<div id="fragment-1" class="ui-tabs-panel">
<div class="line">
	<div class="quarter bold">
		<div style="float:left;margin-left:20px;">Month</div>
			<div style="float:right;margin-right:20px;">
				<spring:bind path="incomeTaxMaster">
						<form:select path="incomeTaxMaster.payMonth" id="payMonth" onchange="javascript:getITPayDetails();">
						<form:option value="select">Select</form:option>
						<form:options items="${sessionScope.payMonthList}" itemLabel="key" itemValue="value"/>
					</form:select>
				</spring:bind>
		   </div>
</div></div>
<div class="line">
<div class="quarter bold">
		<div style="float:left;margin-left:20px;">Basic</div>
			<div style="float:right;margin-right:20px;">
				<spring:bind path="incomeTaxMaster">
						<form:input path="incomeTaxMaster.basic" id="basic"  size="5" />
				</spring:bind>
		   </div>
</div>
<div class="quarter bold">
		<div style="float:left;margin-left:20px;">Grade</div>
			<div style="float:right;margin-right:20px;">
				<spring:bind path="incomeTaxMaster">
						<form:input path="incomeTaxMaster.gradePay" id="gradePay"  size="5" />
						    
				</spring:bind>
		   </div>
</div>
<div class="quarter bold">
		<div style="float:left;margin-left:20px;">SPay</div>
			<div style="float:right;margin-right:20px;">
				<spring:bind path="incomeTaxMaster">
						<form:input path="incomeTaxMaster.sPay" id="sPay"  size="5"/>
						    
				</spring:bind>
		   </div>
</div>
<div class="quarter bold">
		<div style="float:left;margin-left:20px;">CGHS</div>
			<div style="float:right;margin-right:20px;">
				<spring:bind path="incomeTaxMaster">
						<form:input path="incomeTaxMaster.cghs" id="cghs"  size="5" />
						    
				</spring:bind>
		   </div>
</div>
</div>
<div class="line">
<div class="quarter bold">
		<div style="float:left;margin-left:20px;">FPay</div>
			<div style="float:right;margin-right:20px;">
				<spring:bind path="incomeTaxMaster">
						<form:input path="incomeTaxMaster.fPay" id="fPay"  size="5"/>
						  
				</spring:bind>
		   </div>
</div>
<div class="quarter bold">
		<div style="float:left;margin-left:20px;">DA</div>
			<div style="float:right;margin-right:20px;">
				<spring:bind path="incomeTaxMaster">
						<form:input path="incomeTaxMaster.da" id="da"  size="5" />
						    
				</spring:bind>
		   </div>
</div>
<div class="quarter bold">
		<div style="float:left;margin-left:20px;">HRA</div>
			<div style="float:right;margin-right:20px;">
				<spring:bind path="incomeTaxMaster">
						<form:input path="incomeTaxMaster.hra" id="hra"  size="5"/>
						    
				</spring:bind>
		   </div>
</div>
<div class="quarter bold">
		<div style="float:left;margin-left:20px;">CCS</div>
			<div style="float:right;margin-right:20px;">
				<spring:bind path="incomeTaxMaster">
						<form:input path="incomeTaxMaster.ccs" id="ccs" size="5" />
						    
				</spring:bind>
		   </div>
</div>
</div>
<div class="line">
<div class="quarter bold">
		<div style="float:left;margin-left:20px;">TRA</div>
			<div style="float:right;margin-right:20px;">
				<spring:bind path="incomeTaxMaster">
						<form:input path="incomeTaxMaster.tpt" id="tpt"  size="5"/>
						    
				</spring:bind>
		   </div>
</div>
<div class="quarter bold">
		<div style="float:left;margin-left:20px;">CMisc</div>
			<div style="float:right;margin-right:20px;">
				<spring:bind path="incomeTaxMaster">
						<form:input path="incomeTaxMaster.cMisc" id="cMisc"  size="5" />
						    
				</spring:bind>
		   </div>
</div>
<div class="quarter bold">
		<div style="float:left;margin-left:20px;">Arrears</div>
			<div style="float:right;margin-right:20px;">
				<spring:bind path="incomeTaxMaster">
						<form:input path="incomeTaxMaster.arrears" id="arrears"  size="5" />
						    
				</spring:bind>
		   </div>
</div>
<div class="quarter bold">
		<div style="float:left;margin-left:20px;">GPF/CPS</div>
			<div style="float:right;margin-right:20px;">
				<spring:bind path="incomeTaxMaster">
						<form:input path="incomeTaxMaster.gpf" id="gpf"  size="5" />
						    
				</spring:bind>
		   </div>
</div>
</div>
<div class="line">
<div class="quarter bold">
		<div style="float:left;margin-left:20px;">CGEGIS</div>
			<div style="float:right;margin-right:20px;">
				<spring:bind path="incomeTaxMaster">
						<form:input path="incomeTaxMaster.cgeis" id="cgeis"  size="5" />
						    
				</spring:bind>
		   </div>
</div>
<div class="quarter bold">
		<div style="float:left;margin-left:20px;">PTax</div>
			<div style="float:right;margin-right:20px;">
				<spring:bind path="incomeTaxMaster">
						<form:input path="incomeTaxMaster.pTax" id="pTax"  size="5"/>
						     
				</spring:bind>
		   </div>
</div>
<div class="quarter bold">
		<div style="float:left;margin-left:20px;">ITax</div>
			<div style="float:right;margin-right:20px;">
				<spring:bind path="incomeTaxMaster">
						<form:input path="incomeTaxMaster.iTax" id="iTax"  size="5" />
						     
				</spring:bind>
		   </div>
</div>
<div class="quarter bold">
		<div style="float:left;margin-left:20px;">HBA</div>
			<div style="float:right;margin-right:20px;">
				<spring:bind path="incomeTaxMaster">
						<form:input path="incomeTaxMaster.hba" id="hba"  size="5"/>
						    
				</spring:bind>
		   </div>
</div>
</div>
<div class="line">
<div class="quarter bold">
		<div style="float:left;margin-left:20px;">PLI</div>
			<div style="float:right;margin-right:20px;">
				<spring:bind path="incomeTaxMaster">
						<form:input path="incomeTaxMaster.pli" id="pli"  size="5"/>
						    
				</spring:bind>
		   </div>
</div>
<div class="quarter bold">
		<div style="float:left;margin-left:20px;">LIC</div>
			<div style="float:right;margin-right:20px;">
				<spring:bind path="incomeTaxMaster">
						<form:input path="incomeTaxMaster.lic" id="lic"  size="5" />
						    
				</spring:bind>
		   </div>
</div>
<div class="quarter bold">
		<div style="float:left;margin-left:20px;">OTBill</div>
			<div style="float:right;margin-right:20px;">
				<spring:bind path="incomeTaxMaster">
						<form:input path="incomeTaxMaster.otBill" id="otBill"  size="5"/>
						    
				</spring:bind>
		   </div>
</div>
<div class="quarter bold">
		<div style="float:left;margin-left:20px;">EOL/HPL</div>
			<div style="float:right;margin-right:20px;">
				<spring:bind path="incomeTaxMaster">
						<form:input path="incomeTaxMaster.eolHpl" id="eolHpl"  size="5"/>
						    
				</spring:bind>
		   </div>
</div>
</div>
<div class="line">
<div class="quarter bold">
		<div style="float:left;margin-left:20px;">HDFC</div>
			<div style="float:right;margin-right:20px;">
				<spring:bind path="incomeTaxMaster">
						<form:input path="incomeTaxMaster.hdfc" id="hdfc"  size="5" />
						    
				</spring:bind>
		   </div>
</div>

<div class="quarter bold">
		<div style="float:left;margin-left:20px;">GIC</div>
			<div style="float:right;margin-right:20px;">
				<spring:bind path="incomeTaxMaster">
						<form:input path="incomeTaxMaster.gic" id="gic"  size="5" />
						    
				</spring:bind>
		   </div>
</div><div class="quarter bold">
		<div style="float:left;margin-left:20px;">Canfin</div>
			<div style="float:right;margin-right:20px;">
				<spring:bind path="incomeTaxMaster">
						<form:input path="incomeTaxMaster.canfin" id="canfin"  size="5"/>
						    
				</spring:bind>
		   </div>
</div>
<div class="quarter bold">
		<div style="float:left;margin-left:20px;">DMisc</div>
			<div style="float:right;margin-right:20px;">
				<spring:bind path="incomeTaxMaster">
						<form:input path="incomeTaxMaster.dMisc" id="dMisc"  size="5" />
						    
				</spring:bind>
		   </div>
</div>
</div>
</div>
<div id="fragment-2" class="ui-tabs-panel">
<div class="line">
<div class="quarter leftmar">Name</div>
<div class="quarter"><spring:bind path="incomeTaxMaster">
	<form:select path="incomeTaxMaster.name" id="name"
		cssClass="formSelect">
		<form:option value="select">Select</form:option>
		<form:option value="1">DA1</form:option>
		<form:option value="2">DA2</form:option>
	</form:select>
</spring:bind></div>
<div class="quarter leftmar">Amount</div>
<div class="quarter"><spring:bind path="incomeTaxMaster">
	<form:input path="incomeTaxMaster.amount" id="amount" size="20"
		maxlength="6" />
</spring:bind></div>
</div>
<div class="line">
<div class="quarter leftmar">CPS Recovery</div>
<div class="quarter"><spring:bind path="incomeTaxMaster">
	<form:input path="incomeTaxMaster.cpsRec" id="cpsRec" size="20"
		maxlength="6" />
</spring:bind></div>
<div class="quarter leftmar">I.Tax Recovery</div>
<div class="quarter"><spring:bind path="incomeTaxMaster">
	<form:input path="incomeTaxMaster.ITRec" id="ITRec" size="20"
		maxlength="6" />
</spring:bind></div>
</div>
<div id="dataTable">
<table class=list>
	<tr class=list>
		<th width="20%" style="text-align: center">Name</th>
		<th width="20%" style="text-align: center">Amount</th>
		<th width="20%" style="text-align: center">CPS Recovery</th>
		<th width="20%" style="text-align: center">I.Tax Recovery</th>
		<th width="20%" style="text-align: center">Edit</th>
	</tr>
	<c:forEach items="${sessionScope.arrearsDetailsList}" var="arr">
		<tr class="even">
			<td width="20%" style="text-align: center">${arr.name}</td>
			<td width="20%">${arr.totAmt}</td>
			<td width="20%">${arr.totCpfRec}</td>
			<td width="20%">${arr.totItRec}</td>
			<td style="text-align: center"><img src="./payImages/edit.gif"
				onclick="editArrearsListDetails('${arr.name}','${arr.totAmt}','${arr.totCpfRec}','${arr.totItRec}')" />
			</td>
		</tr>
	</c:forEach>
</table>
</div>

</div>

<div id="fragment-3" class="ui-tabs-panel">
<div id="dataTable">
<table width="90%" cellpadding="2" cellspacing="0" align="center"
	border="1" class="list" bordercolor="#10519a" id="itConfigOtherSources">
	<tr class=list>
		<th width="50%" style="text-align: center">Configuration</th>
		<th width="15%" style="text-align: center">Actual</th>
		<th width="25%" style="text-align: center">Remarks</th>
		<th width="2%">&nbsp;</th>
	</tr>
	<c:forEach items="${sessionScope.SavingsList}" var="slist">
		<c:if test="${slist.sType=='Other Income Sources'}">
			<tr class="even">
				<td width="50%">${slist.sName}</td>
				<td width="15%"><spring:bind path="incomeTaxMaster">
					<form:input path="incomeTaxMaster.actual" id="actual" size="25"
						maxlength="6" onkeypress="return checkInt(event);"/>
				</spring:bind></td>
				<td width="30%"><spring:bind path="incomeTaxMaster">
					<form:input path="incomeTaxMaster.remarks" id="remarks" size="45"
						maxlength="50" />
				</spring:bind></td>
				<td width="2%"><spring:bind path="incomeTaxMaster">

					<form:checkbox path="incomeTaxMaster.submissionFlag" id="submissionFlag" value="false"  onclick="javascript:checkCheckboxCombination(this);"/> <!-- onclick="javascript:getActualValue();" -->
								</spring:bind></td>
				<td style="display: none">
				<form><input type="text" value=${slist.id } /></form>
				</td>
			</tr>
		</c:if>
	</c:forEach>
</table>
</div>
<div>&nbsp;</div>
<div class="line">
<div style="margin-left: 80%">
<div class="expbutton"><a
	onclick="javascript:manageIncomeTaxConfigDetails()"> <span>Save</span></a></div>
</div>
</div>


<!-- Narayana  Started -->
<fieldset><legend><strong><font
	color='green'>Total Income</font></strong></legend>
				<div class="line">
					<div class="quarter bold"><font size="3">Total Sum</font>
					<spring:bind path="incomeTaxMaster">
							<form:input path="incomeTaxMaster.dMisc" id="totalCredits1"  size="5" readonly="true"/>
						</spring:bind>
					</div>
					
			</div>
			<div class="line">
					<div class="quarter bold"><font size="3">Total Income</font>
					<spring:bind path="incomeTaxMaster">
							<form:input path="incomeTaxMaster.dMisc" id="finalPay1"  size="5" readonly="true"/>
						</spring:bind>
					</div>
					
	        </div>
</fieldset>
<!-- Narayana  ended -->
</div>

<div id="fragment-4" class="ui-tabs-panel ui-tabs-hide">
<div id="dataTable">
<table width="90%" cellpadding="2" cellspacing="0" align="center"
	border="1" class="list" bordercolor="#10519a" id="itConfigSavings">
	<tr class=list>
		<th width="30%" style="text-align: center">Configuration</th>
		<th width="13%" style="text-align: center">Projection</th>
		<th width="13%" style="text-align: center">Actual</th>
		<th width="20%" style="text-align: center">Remarks</th>
		<th width="24%" style="text-align: center">Proof Verification</th>
	</tr>

	<% int i = 0; %>
	<c:forEach items="${sessionScope.SavingsList}" var="slist">
		<c:if test="${slist.sType=='Savings'}">
			<tr class="even">
				<td width="30%">${slist.sName}</td>
				<td width="10%"><spring:bind path="incomeTaxMaster">
					<form:input path="incomeTaxMaster.projection" id="projection"
						size="20" maxlength="6" onkeypress="return checkInt(event);"/>
				</spring:bind></td>
				<td width="10%"><spring:bind path="incomeTaxMaster">
					<form:input path="incomeTaxMaster.actual" id="actual" size="20"
						maxlength="6" onkeypress="return checkInt(event);"/>
				</spring:bind></td>
				<td width="10%"><spring:bind path="incomeTaxMaster">
					<form:input path="incomeTaxMaster.remarks" id="remarks" size="30"
						maxlength="50" />
				</spring:bind></td>
				<td width="2%"><spring:bind path="incomeTaxMaster">
					<input type="radio" name="incomeTaxMaster.submissionFlag" id="submissionFlag1" onchange="javascript:setSavingsVal(<%=i %>);" /><label>Partial Proof</label>
					&nbsp;&nbsp;&nbsp;<input type="radio" name="incomeTaxMaster.submissionFlag" id="submissionFlag2" onchange="javascript:setSavingsVal(<%=i %>);" /><label>Full Proof</label>
				</spring:bind></td>
				<td style="display: none">
				<form><input type="text" value=${slist.id } /></form>
				</td>
			</tr>
			<% i++; %>
		</c:if>
	</c:forEach>
</table>
</div>
<div>&nbsp;</div>
<div class="line">
<div style="margin-left: 80%">
<div class="expbutton"><a
	onclick="javascript:manageIncomeTaxConfigDetails()"> <span>Save</span></a></div>
</div>
</div>
<!-- Narayana  Started -->
<fieldset><legend><strong><font
	color='green'>Savings</font></strong></legend>
				<div class="line">
					<div class="quarter bold"><font size="3">Total Sum(8a)</font>
					<spring:bind path="incomeTaxMaster">
							<form:input path="incomeTaxMaster.dMisc" id="totalCredits1"  size="5" readonly="true"/>
						</spring:bind>
					</div>
					<div class="quarter bold"><font size="3">Long Term Bonds(8b)</font>
					<spring:bind path="incomeTaxMaster">
							<form:input path="incomeTaxMaster.dMisc" id="totalCredits1"  size="5" readonly="true"/>
						</spring:bind>
					</div>
					<div class="quarter bold"><font size="3">Govt. Subs to CPS(8c)</font>
					<spring:bind path="incomeTaxMaster">
							<form:input path="incomeTaxMaster.dMisc" id="totalCredits1"  size="5" readonly="true"/>
						</spring:bind>
					</div>
					
			</div>
			<div class="line">
					<div class="quarter bold"><font size="3">Total Savings(8a+8b+8c)</font>
					<spring:bind path="incomeTaxMaster">
							<form:input path="incomeTaxMaster.dMisc" id="finalPay1"  size="5" readonly="true"/>
						</spring:bind>
					</div>
					
	        </div>
	        <div class="line">
					<div class="quarter bold"><font size="3">Taxable Income</font>
					<spring:bind path="incomeTaxMaster">
							<form:input path="incomeTaxMaster.dMisc" id="finalPay1"  size="5" readonly="true"/>
						</spring:bind>
					</div>
					<div class="quarter bold"><font size="3">Taxable Income round off</font>
					<spring:bind path="incomeTaxMaster">
							<form:input path="incomeTaxMaster.dMisc" id="finalPay1"  size="5" readonly="true"/>
						</spring:bind>
					</div>
					
	        </div>
	         <div class="line">
					<div class="quarter bold"><font size="3">Total Income Tax</font>
					<spring:bind path="incomeTaxMaster">
							<form:input path="incomeTaxMaster.dMisc" id="finalPay1"  size="5" readonly="true"/>
						</spring:bind>
					</div>
					<div class="quarter bold"><font size="3">Education Cess(3%)</font>
					<spring:bind path="incomeTaxMaster">
							<form:input path="incomeTaxMaster.dMisc" id="finalPay1"  size="5" readonly="true"/>
						</spring:bind>
					</div>
					<div class="quarter bold"><font size="3">Amt of IT rec from Pay & OT Bill</font>
					<spring:bind path="incomeTaxMaster">
							<form:input path="incomeTaxMaster.dMisc" id="finalPay1"  size="5" readonly="true"/>
						</spring:bind>
					</div>
					
	        </div>
	        <div class="line">
					<div class="quarter bold"><font size="3">Balance to be recovered in the remaining months</font>
					<spring:bind path="incomeTaxMaster">
							<form:input path="incomeTaxMaster.dMisc" id="finalPay1"  size="5" readonly="true"/>
						</spring:bind>
					</div>
					
	        </div>
</fieldset>
<!-- Narayana  ended -->
</div>

<div id="fragment-5" class="ui-tabs-panel ui-tabs-hide">&nbsp;
<div id="dataTable">
<table width="100%" cellpadding="2" cellspacing="0" align="center"
	border="1" class="list" bordercolor="#10519a" id="itConfigExemptions">
	<tr class=list>
		<th style="display: none"></th>
		<th width="80%" style="text-align: center">Configuration</th>
		<th width="15%" style="text-align: center">Actual</th>
		<th width="25%" style="text-align: center">Remarks</th>
		<th width="5%">&nbsp;</th>
	</tr>
	<c:forEach items="${sessionScope.SavingsList}" var="slist">
		<c:if test="${slist.sType=='Exemptions'}">
			<tr class="even">
				<td width="70%">${slist.sName}</td>
				<td width="15%"><spring:bind path="incomeTaxMaster">
					<form:input path="incomeTaxMaster.actual" id="actual" size="20"
						maxlength="6" onkeypress="return checkInt(event);"/>
				</spring:bind></td>
				<td width="25%"><spring:bind path="incomeTaxMaster">
					<form:input path="incomeTaxMaster.remarks" id="remarks" size="30"
						maxlength="100" />
				</spring:bind></td>
				<td width="5%"><spring:bind path="incomeTaxMaster">
					<form:checkbox path="incomeTaxMaster.submissionFlag"
						id="submissionFlag" />
				</spring:bind></td>
				<td style="display: none">
				<form><input type="text" value=${slist.id} /></form>
				</td>
			</tr>
		</c:if>
	</c:forEach>
</table>
</div>
<div>&nbsp;</div>
<div class="line">
<div style="margin-left: 80%">
<div class="expbutton"><a
	onclick="javascript:manageIncomeTaxConfigDetails()"> <span>Save</span></a></div>

</div>
</div>
</div>
<div id="fragment-6" class="ui-tabs-panel ui-tabs-hide"><form:form
	method="post" commandName="incomeTaxMaster">
	<div class="line">
	<div class="quarter leftmar">From Month<span class="mandatory">*</span></div>
	<div class="quarter"><form:input path="rentFromMonth"
		id="rentFromMonth" cssClass="dateClass" readonly="true" /> <img
		src="./payImages/calendar.gif" id="rentFromMonthImg" /> <script
		type="text/javascript">
												Calendar.setup({inputField :"rentFromMonth", ifFormat : "%d-%b-%Y",showsTime : false,button :"rentFromMonthImg",singleClick : true,step : 1});
											</script></div>

	<div class="quarter leftmar">To Month<span class="mandatory">*</span></div>
	<div class="quarter"><form:input path="rentToMonth"
		id="rentToMonth" cssClass="dateClass" readonly="true" /> <img
		src="./payImages/calendar.gif" id="rentToMonthImg" /> <script
		type="text/javascript">
												Calendar.setup({inputField :"rentToMonth", ifFormat : "%d-%b-%Y",showsTime : false,button :"rentToMonthImg",singleClick : true,step : 1});
											</script></div>
	</div>

	<div class="line">
	<div class="quarter leftmar">Rent<span class="mandatory">*</span></div>
	<div class="quarter"><form:input path="rent" id="rent" onkeypress="return checkInt(event);"/></div>
	<div class="quarter leftmar">Remarks</div>
	<div class="quarter"><form:input path="rentRemarks"
		id="rentRemarks" /></div>
	</div>

	<div>&nbsp;</div>
	<div class="line">
	<div style="margin-left: 80%">
	<div class="expbutton"><a
		onclick="javascript:submitRentDetails();"> <span>Save</span></a></div>
	<div class="expbutton"><a
		onclick="javascript:clearITRentDetails();"> <span>Clear</span></a></div>
	</div>
	</div>
	<div class="line" id="RentResult"><jsp:include
		page="ITRentList.jsp" /></div>
	<form:hidden path="param"/>
	<form:hidden path="type"/>
	<form:hidden path="pk"/>
</form:form></div>
</div>
</div>
</div>


</fieldset>
<!-- End:ITaxConfigList.jsp -->