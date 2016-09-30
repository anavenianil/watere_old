<!-- Begin : ConveyanceAcquittance.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.loan.beans.dto.LoanCDADetailsDTO"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>

									<div class="line">
									<div class="quarter leftmar">Sending Report Number<span class="mandatory">*</span></div>
							  			<div class="quarter">
							  				<form:select path="reportNumber" id="reportNumber"  cssClass="formSelect required" value="0" onmouseover="setSelectWidth('#reportNumber')" onchange="javascript:selectedDates()">
                                               <form:option value="0">Select</form:option>
                                               <form:options items="${loan.conveyanceReportList}" itemValue="sendingReportNumber" itemLabel="sendingReportNumber" />
                                            </form:select>
							  			</div>
							  			</div>
							  			<div class="line" id="reportDateDiv" style="display:none">
							  			<div class="quarter leftmar">Report Date<span class="mandatory">*</span></div>
							  			<div class="quarter">
							  				<form:select path="reportDate" id="reportDate"  cssClass="formSelect required" onmouseover="setSelectWidth('#reportDate')">

                                            </form:select>
							  			</div>
							  			</div>
							  			<div class="line">
										<div style="margin-left:30%;">
											<div ><a href="javascript:searchPaybillDetails();" class="appbutton">Search</a></div>
											<div ><a href="javascript:clearPaybillDetails();" class="appbutton"><span>Clear</span></a></div>
										</div>
										</div>
										<div class="line" id="LoanPaybillAcquittanceList">
									     <jsp:include page="LoanPaybillAcquittanceList.jsp"/>	
										</div>
										<div class="expbutton" style="float:right"><a href="javascript:viewAcquittanceReport('acquittance','dataList')"><span>Report</span></a></div>
								    	<div class="expbutton" style="float:right"><a href="javascript:saveCDAdetails('dataList');"><span>Save</span></a></div>
										<script>
											//$jq( function(){$jq("#Pagination").displayTagAjax('paging');});
											jsonReportDate =<%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<LoanCDADetailsDTO>)session.getAttribute("loanCDAListJSON")) %>;
										</script>
<!-- End : ConveyanceAcquittance.jsp -->