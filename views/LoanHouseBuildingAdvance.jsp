<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LoanHouseBuildingAdvance.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.loan.beans.dto.GPFRulesDTO,com.callippus.web.loan.beans.dto.LoanHBATypeMasterDTO"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/loan.js"></script>
<link href="styles/calendar.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="script/calendar-hd.js"></script>
<script type="text/javascript" src="script/calendar_us.js"></script>

<title>Application for House Building advance</title>
</head>

<body onload="javascript:clearHBADetails(),getEmpService('${LoanHbaRequest.employeeDetails.dojDrdo}');" >
<form:form method="post" commandName="LoanHbaRequest"  name="HbaLoanRequest">
		<div>
			<div class="Innermaindiv">
				<div class="header"></div>
				<div><jsp:include page="MenuLinks.jsp" /></div>					
				<div class="mainDisplay">
					<div class="whitebox1">
						<div class="whitebox_t">
							<div class="whitebox_tl">
								<div class="whitebox_tr"></div>
							</div>
						</div>
						<div class="whitebox_m1">
							<div class="lightblueBg1">
								<div class="headTitle">Application for House Building advance</div>
								<div class="line">
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="middle">&nbsp;Loading...</img></div>
									<%-- Content Page starts --%>
									<div id="result"></div>
									<c:if test="${message=='offline'}">
										<div class="line">
											<div class="half leftmar">SFID<span class="failure">&nbsp;*</span></div>
											<div class="half">
												<form:input path="offlineSFID" id="offlineSFID" onblur="javascript:offlineLoanHBA()"/>
											</div>
										</div>									
									</c:if>
									<c:if test="${message!='offline'}">
									<div class="line">
										<div class="half leftmar">SFID</div>
										<div class="half">${LoanHbaRequest.sfID}</div>
									</div>
									</c:if>
									<div class="line">
										<div class="half leftmar">Name</div>
										<div class="half">${LoanHbaRequest.employeeDetails.nameInServiceBook}</div>
									</div>
									<div class="line">
										<div class="half leftmar">Employment Type</div>
										<div class="half">${LoanHbaRequest.employeeDetails.employmentDetails.name}</div>
									</div>
									<div class="line">
										<div class="half leftmar">Length of service on the date of application</div>
										<div class="half" id="empService"></div>
									</div>
									<div class="line">
										<div class="half leftmar">Present pay as defined in Rule4(b) and scale of pay</div>
										<div class="half">${LoanHbaRequest.paymentDetails.basicPay} + ${LoanHbaRequest.paymentDetails.gradePay}</div>
									</div>
									<div class="line">
										<div class="half leftmar">Whether governed by pension rules</div>
										<div class="half">Yes</div>
									</div>
									<div class="line">
										<div class="half leftmar">Date of superannuation or retirement</div>
										<div class="half">${LoanHbaRequest.employeeDetails.retiredDate}</div>
									</div>
									<div class="line">
										<div class="half leftmar">Amount of Provident Fund/any other advance/final withdrawal taken for purchase of  Land/construction(an attested copy of the sanction to be enclosed)  </div>
										<div class="half"><form:radiobutton path="pfFlag" id="Yes" value="Y" label="Yes" />
										<form:radiobutton path="pfFlag" id="No" value="N" label="No" /></div>
									</div>
									<div class="line">
										<div class="half leftmar">Advance is needed for the purchase of </div>
										<div class="half">
							  				<form:select path="houseAdvanceType" id="houseAdvanceType"  cssClass="formSelect required" onmouseover="setSelectWidth('#houseAdvanceType')" onchange="javascript:selectedHouseType()">
                                               <form:option value="0">Select</form:option>
                                               <form:options items="${LoanHbaRequest.hbaLoanTypesList}" itemValue="id" itemLabel="hbaAdvanceType" />
                                            </form:select>
	         								</div>
									</div>
								<!-- Start new plot and constructions -->
									<div id="plotAndConstructionsDiv" class="line" style="display:none">
									<table style="width:100%;" border="1" class="list">
									<tr>
										<th style="width: 20%;">Location with address</th>
										<th style="width: 10%;">Rural/Urban</th>
										<th style="width: 10%;">Is it clearly demarketed and developed</th>
										<th style="width: 10%;">Approximate Area(in sq.mtrs.)</th>
										<th style="width: 20%;">(a)Cost (b)Amount actually paid</th>
										<th style="width: 15%;">if not purchased when proposed to be acquired</th>
										<th style="width: 15%;">Un expired portion of lease if not free hold</th>
									</tr>
									<tr class="odd" >
										<td><center>(1)</center></td>
										<td><center>(2)</center></td>
										<td><center>(3)</center></td>
										<td><center>(4)</center></td>
										<td><center>(5)</center></td>
										<td><center>(6)</center></td>
										<td><center>(7)</center></td>
									</tr>
									<tr class="even" >
										<td><form:textarea path="locationWithAddress" id="locationWithAddress1" cols="20" rows="4" onkeypress="return isAddressExp(event);" /></td>
										<td><form:radiobutton path="ruralOrUrban" id="Rural" value="Rural" label="Rural" /><br/>
										<form:radiobutton path="ruralOrUrban" id="Urban" value="Urban" label="Urban" /></td>
										
										<td><form:radiobutton path="demarAndDev" id="Yes" value="Y" label="Yes" /><br/>
										<form:radiobutton path="demarAndDev" id="No" value="N" label="No" /></td>
										<td><form:input path="approximateArea" id="approximateArea" size="10" onkeypress="return isNumberExp(event);"/></td>
										<td><center>a.<form:input path="cost" id="cost" size="10" onkeypress="return isNumberExp(event);" /></center><center>b.<form:input path="amountPaid" id="amountPaid" size="10" onkeypress="return isNumberExp(event);" /></center></td>
										<td><form:input path="proposedAcquire" id="proposedAcquire" size="10" readonly="true" />
										<script type="text/javascript">
											         new tcal({'formname':'HbaLoanRequest','controlname':'proposedAcquire'});
											</script>
												</td>
										<td><form:input path="unExPortionLease" id="unExPortionLease" size="10" /></td>
									</tr>
									</table>
									<table style="width:100%;" border="1" class="list" id="floorTable">
									<tr>
										<th style="width: 20%;">Floor-wise area to be constructed in (sq.mtrs.)</th>
										<th style="width: 20%;">Estimated cost</th>
										<th style="width: 20%;">Amount of advance required(for land/ Construction/ both)</th>
										<th style="width: 20%;">No.of installments for repayment</th>
										<th style="width: 10%;">Add</th>
										<th style="width: 10%;">Remove</th>
									</tr>
									<tr class="odd" >
										<td><center>(8)</center></td>
										<td><center>(9)</center></td>
										<td><center>(10)</center></td>
										<td><center>(11)</center></td>
										<td></td>
										<td></td>
									</tr>
									<tr class="even" >
										<td id='0'>Ground Floor</td>
										<td><form:input path="estimatedCost" id="estimatedCost" onkeypress="return isNumberExp(event);" /></td>
										<td><form:input path="advanceReq" id="advanceReq1" onkeypress="return isNumberExp(event);" /></td>
										<td><center>Principle<form:input path="noOfInstalPrinciple" id="noOfInstalPrinciple1" size="5" onkeypress="return isNumberExp(event);" /></center>
										<center>Interest<form:input path="noOfInstalInterest" id="noOfInstalInterest1" size="5" onkeypress="return isNumberExp(event);" /></center></td>
										<td><center><input type="button" value="+" onclick="insertRow()" /></center></td>
										<td></td>
									</tr>
									</table>								  		
							  		</div>
							  		<!-- End new plot and constructions -->
							  		
								<!--  Start: Enlarging the exixting house-->
								<div id="enlargingHouseDiv" class="line" style="display:none">
								<table style="width:100%;" border="1" class="list" >
									<tr>
										<th style="width: 20%;">Location with address</th>
										<th style="width: 10%;">Plinth area( in sq.mtrs.)</th>
										<th style="width: 10%;">Plinth area proposed for enlargement (in sq.mtrs.)</th>
										<th style="width: 10%;">Cost of construction /Acquisition of
										existing house</th>
										<th style="width: 10%;">cost of proposed enlargement</th>
										<th style="width: 10%;">Total plinth area(2+3)</th>
										<th style="width: 10%;">Total Cost (4+5)</th>
										<th style="width: 10%;">Amount of advance required</th>
										<th style="width: 10%;">No.of installments for repayment</th>
									</tr>
									<tr class="odd" >
										<td><center>(1)</center></td>
										<td><center>(2)</center></td>
										<td><center>(3)</center></td>
										<td><center>(4)</center></td>
										<td><center>(5)</center></td>
										<td><center>(6)</center></td>
										<td><center>(7)</center></td>
										<td><center>(8)</center></td>
										<td><center>(9)</center></td>
									</tr>
									<tr class="even" >
										<td><form:textarea path="locationWithAddress" id="locationWithAddress2" cols="20" rows="4" onkeypress="return isAddressExp(event);" /></td>
										<td><form:input path="plinthArea" id="plinthArea2" size="10" onkeypress="return isNumberExp(event);" onblur="javascript:calculateTotalPlinth()"/></td>
										<td><form:input path="pliProposedEnlarge" id="pliProposedEnlarge" size="10" onkeypress="return isNumberExp(event);" onblur="javascript:calculateTotalPlinth()" /></td>
										<td><form:input path="costAcquisition" id="costAcquisition" size="10" onkeypress="return isNumberExp(event);" onblur="javascript:calculateTotalCost()" /></td>
										<td><form:input path="costProposed" id="costProposed" size="10" onkeypress="return isNumberExp(event);" onblur="javascript:calculateTotalCost()"/></td>
										<td><form:input path="totalPlinth" id="totalPlinth" size="10" onkeypress="return isNumberExp(event);" readonly="true" /></td>
										<td><form:input path="totalCost" id="totalCost" size="10" onkeypress="return isNumberExp(event);" readonly="true" /></td>
										<td><form:input path="advanceReq" id="advanceReq2" size="10" onkeypress="return isNumberExp(event);" /></td>
										<td><center>Principle<form:input path="noOfInstalPrinciple" id="noOfInstalPrinciple2" size="5" onkeypress="return isNumberExp(event);" /></center>
										<center>Interest<form:input path="noOfInstalInterest" id="noOfInstalInterest2" size="5" onkeypress="return isNumberExp(event);" /></center></td>
									</tr>
								</table>
								</div>
								<div id="readyBuiltHouseDiv" class="line" style="display:none">
								<table style="width: 100%;"  border="1" class="list" >
									<tr>
										<th style="width: 15%;">Location with address</th>
										<th style="width: 10%;">Plinth area( in sq.mtrs.)</th>
										<th style="width: 25%;">When constructed </th>
										<th style="width: 5%">price settled</th>
										<th style="width: 10%;">The agency from whom to be purchased</th>
										<th style="width: 15%;">Amount (a)already paid (b)to be paid</th>
										<th style="width: 10%;">Amount of advance required</th>
										<th style="width: 10%;">No.of installments for repayment</th>
									</tr>
									<tr class="odd" >
										<td><center>(1)</center></td>
										<td><center>(2)</center></td>
										<td><center>(3)</center></td>
										<td><center>(4)</center></td>
										<td><center>(5)</center></td>
										<td><center>(6)</center></td>
										<td><center>(7)</center></td>
										<td><center>(8)</center></td>
									</tr>
									<tr class="even" >
										<td><form:textarea path="locationWithAddress" id="locationWithAddress3" cols="20" rows="4" onkeypress="return isAddressExp(event);" /></td>
										<td><form:input path="plinthArea" id="plinthArea3" size="10" onkeypress="return isNumberExp(event);" /></td>
										<td><form:input path="whenConstructed" id="whenConstructed" size="8" readonly="true" />
												<script type="text/javascript">
											         new tcal({'formname':'HbaLoanRequest','controlname':'whenConstructed'});
											</script></td>
										<td><form:input path="priceSettled" id="priceSettled" size="10" onkeypress="return isNumberExp(event);" /></td>
										<td><form:input path="agencyFrmPurchased" id="agencyFrmPurchased" size="10" onkeypress="return isAlphabetExp(event);"/></td>
										<td><center>a.<form:input path="amtAlreadyPaid" id="amtAlreadyPaid" size="9" onkeypress="return isNumberExp(event);" /></center><center>b.<form:input path="amtToPaid" id="amtToPaid" size="9" onkeypress="return isNumberExp(event);" /></center></td>
										<td><form:input path="advanceReq" id="advanceReq3" size="10" onkeypress="return isNumberExp(event);" /></td>
										<td><center>Principle<form:input path="noOfInstalPrinciple" id="noOfInstalPrinciple3" size="5" onkeypress="return isNumberExp(event);" /></center>
										<center>Interest<form:input path="noOfInstalInterest" id="noOfInstalInterest3" size="5" onkeypress="return isNumberExp(event);" /></center></td>
									</tr>
								</table>
								</div>
								<div class="line" id="miscellaneous" style="display:none">
										<div class="half leftmar">if you or any dependent member of your family already own(s) a house,please state</div>
										<div class="half"><form:radiobutton path="miscFlag" id="miscFlag" value="Y" label="Yes" onclick="javascript:selectedMisc()" />
										<form:radiobutton path="miscFlag" id="miscFlag" value="N" label="No" onclick="javascript:selectedMisc()" />
										</div>
								</div>
								<div id="miscellaneousDiv" class="line" style="display:none">
								<table style="width: 100%;" border="1" class="list" >
									<tr>
										<th style="width: 25%;">Location with address</th>
										<th style="width: 25%;">Plinth area(Floor wise)</th>
										<th style="width: 25%;">Present fair market value</th>
										<th style="width: 25%;">Reasons acquiring another house or enlarging the existing house</th>
									</tr>
									<tr class="odd" >
										<td><center>(1)</center></td>
										<td><center>(2)</center></td>
										<td><center>(3)</center></td>
										<td><center>(4)</center></td>
									</tr>
									<tr class="even" >
										<td><form:textarea path="miscLocationAddress" id="locationWithAddress4" cols="20" rows="4" onkeypress="return isAddressExp(event);" /></td>
										<td><form:input path="plinthAreaFW" id="plinthAreaFW" size="20" onkeypress="return isNumberExp(event);" /></td>
										<td><form:input path="presentMarketValue" id="presentMarketValue" size="20" onkeypress="return isNumberExp(event);" /></td>
										<td><form:textarea path="reasons" id="reasons" cols="20" rows="4" onkeypress="return isAlphabetExp(event);"/></td>
									</tr>
								</table>
								</div>
								<div class="line" id="buttons" style="display:none">
										<div style="margin-left:30%;">
											<a href="javascript:manageHBADetails();" class="appbutton">Submit</a>
											<a href="javascript:clearHBADetails();" class="appbutton">Clear</a>
										</div>										
									</div>
									
								<fieldset><legend><strong><font color=green>HBA CHECK-LIST</font></strong></legend>
								
								<div class="line">1.HBA application form(Form S-252)</div>
								<div class="line">2.Permission for purchase of plot(from Head of the establishment for Non-Gazetted employees and from R&D HQrs., for Group 'A' Gazetted officers</div>
								<div class="line">3.Permission for construction of house/Purchase of Flat (from Head of the Establishment in respect of Non-Gazetted employees and from R&D HQrs., in respect of Group 'A' Gazetted officers</div>
								<div class="line">4.Original sale Deed in the name of the Govt. servent/applicant or jointly in the name of the applicant and his/her spouse.</div>
								<div class="line">5.Approved layout copy of the plot.</div>
								<div class="line">6.Original approved house construction plan by the concerned Municipal Corporation.Panchayat, alon with covering letter issued by Municipal Corporation/Panchayat.</div>
								<div class="line">7.Detailed and abstract estimates and soid Certificate from licensed surveyor.</div>
								<div class="line">8.Certificate of Non-encumbrances for the last 16 years, from the concerned Registrar/Sub-registrar's office.</div>
								<div class="line">9.Before release of Ist installment of HBA for construction of house, the plot should be mortgaged in favour of President of India</div>
								<div class="line">10.Govt. Pleader Certificate.</div>
								<div class="line">11.Sworn statements from two co-employees stating that the site belongs to the officer/individual.</div>
								<div class="line">12.Latest Pay Slip.</div>
								<div class="line">13.Sterilisation Certificate copy, ifapplicable.</div>
								<div class="line">14.Flat allotment letter in case of Ready Built Flat(The Builder should be a registered Builder).</div>
								<div class="line">15.Original Agreement letter in case of Ready Built Flat between the Govt. servent and the Builder.</div>
								<div class="line">16.Surity from a permanent Govt. servent of equal/higher rank to be obtained from:</div>
								<div class="line">
								<pre >   <font face="Trebuchet MS"> a) all the applicants who are not permanent Central Govt. Servents<br/>       b) all the applicants who are due to retire with in a period of 18 months from the date of application<br/>       c) all the applicants who are permanent Govt. Servents who require the advance for the purchase of ready-built flat/house.</font></pre></div>								
								<div class="line">17.Agreement between the Govt.servent and the Govt. ie., the CFA</div>
								<div class="line">18.Two House plan copies on cloth paper(A4 size) for Mortgage.</div>
								<div class="line"><div class="mandatory"><u>NOTE</u>:-</div></div>
								<div class="line"><div class="mandatory">Employees (either Gazetted or Non-Gazetted) who have not permanent in Govt. Service or who have not completed 10 years qualifying service are not entitled for grant of HBA.</div></div>
								<div class="line"><div class="mandatory">The quantum of HBA to be granted is 34 times of basic pay only on the date of application, subject to a maximum of Rs.7. lakhs or cost of the house/flat or the repaying capacity whichever is the least, for construction of house/purchase of ready built flat.</div></div>
								</fieldset>
								<%-- Content Page end --%>
								</div>
								<div>&nbsp;</div>
							</div>
						</div>
						<div class="whitebox_b_non">
							<div class="whitebox_bl">
								<div class="whitebox_br"></div>
							</div>
						</div>
					</div>
				</div>
				
			</div>
			<div><jsp:include page="Footer.jsp" /></div>			
		</div>
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		<form:hidden path="requestID"/>
		<form:hidden path="requestId"/>
		</form:form>
		<script>
		</script>
	</body>
</html>
<!-- End:LoanHouseBuildingAdvance.jsp -->