<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : HindiIncentiveEmpListPage.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	                          <div id="empDetailsDiv">
	                                <div class="line">
								        <fieldset>	
								      		<c:forEach items="${sessionScope.hindiIncentiveEmpList}" var="empList" varStatus="true">			        
											       <div class="quarter">SFID: <strong><span id="empDivSfid">${empList.id}</span></strong></div>
											       <div class="quarter">NAME: <strong><span id="empDivName">${empList.name}</span></strong></div>
											       <div class="quarter">DIVISION: <strong><span id="empDivDivision">${empList.department}</span></strong></div> 										       
											       <div class="quarter">DESIGNATION: <strong><span id="empDivDesignation">${empList.designation}</span></strong></div>
									      </c:forEach>
									     </fieldset> 
									 </div>
								</div>
									 
								<div id="empFinanceDetailsDiv">
								<spring:bind path="hindi"> 
									 <div class="line">
											<div class="quarter leftmar">SFID<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:input path="hindi.sfid" id="sfid" readonly="true"/>
											</div>
										</div>
										<div class="line">
											<div class="quarter leftmar">Total Incentive Amount<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:input path="hindi.totalAmount" id="totalAmount" value="${sessionScope.hindiIncentiveAmount}" readonly="true"/>
											</div>
											
											<%--<div class="quarter leftmar">CashAward Amount</div>
											<div class="quarter">
												<form:input path="hindi.cashAwardAmount" id="cashAwardAmount" onkeypress="return isNumberExp(event)"/>
											</div> --%>
										</div>
										<div class="line">
											<div class="quarter leftmar">No Of Installments<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:input path="hindi.noOfInst" id="noOfInst" onkeypress="return checkInt(event)"  />
											</div>
										</div>
										<div class="line">
											<div class="quarter leftmar">Present Installment<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:input path="hindi.presentInst" id="presentInst" onkeypress="return checkInt(event)"/>
											</div>
										</div>
										<div class="line">
											<div class="quarter leftmar">Effective Date<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:input path="hindi.fromDate" id="fromDate" cssClass="required" onkeypress="javascript:setReadOnly(event, 'fromDate')"/>
				                               	<img  src="./images/calendar.gif" id="date_start_trigger1" />	
				                                <script type="text/javascript">
					                              	Calendar.setup({inputField :"fromDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
				                                </script>
											</div>
										</div>
									
										<div class="line">
											<div class="half" style="padding-left: 35%;width: 280px;">
												<div class="expbutton"><a href="javascript:saveIncentiveDetails();"><span>Submit</span></a></div>
												<div class="expbutton"><a href="javascript:clearIncentiveDetails();"><span>Clear</span></a></div>
											</div>
										</div>	
										<div id="result" class="line"><jsp:include page="HindiIncentiveList.jsp" /></div>
										</spring:bind>
	                                </div>
	                                <script>
	                               // $jq('#sfid').val($jq('#incentiveSfid').val());
	                               $jq('#sfid').val($jq('#sfidSearch').val().toUpperCase());
	                                </script>
	
<!-- End : HindiIncentiveEmpListPage.jsp -->
