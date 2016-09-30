<!-- Begin : FestivalAcquittance.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.loan.beans.dto.LoanCDADetailsDTO"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>

									<div class="line">
								<div class="line">
							  			<div class="quarter leftmar">Financial Year<span class="mandatory">*</span></div>
							  			<div class="quarter">
							  				<form:select path="financialYear" id="financialYear"  cssClass="formSelect required" onmouseover="setSelectWidth('#financialYear')">
                                               <form:option value="0">Select</form:option>
                                               <form:options items="${loan.financialYearList}" itemValue="id" itemLabel="financialYear" />
                                            </form:select>
							  			</div>
							  		</div>
							  		<div class="line">
							  			<div class="quarter leftmar">Festival Name<span class="mandatory">*</span></div>
							  			<div class="quarter">
							  				<form:select path="loanSubType" id="loanSubType"  cssClass="formSelect required" onmouseover="setSelectWidth('#loanSubType')" >
                                               <form:option value="">Select</form:option>
                                               <form:options items="${loan.loanFestivalList}" itemValue="id" itemLabel="festivalName" />
                                            </form:select>
							  			</div>
							  		</div>
							  		<div class="line">
										<div style="margin-left:30%;">
											<a href="javascript:searchPayAcReport();" class="appbutton">Search</a>
											<a href="javascript:clearPayAcReport();" class="appbutton" id="clearBut" >Clear</a>
										</div>										
									</div>
							  		<div id="FestivalPayAcquittanceList" class="line" >
										<jsp:include page="FestivalPayAcquittanceList.jsp" />
									</div>
							  			<div class="expbutton" style="float:right"><a href="javascript:viewAcquittanceReport('acquittance','festivalList')"><span>Report</span></a></div>
								    	<div class="expbutton" style="float:right"><a href="javascript:saveCDAdetails('festivalList');"><span>Save</span></a></div>
							  	</div>
							  			<script>
											$jq( function(){$jq("#Pagination").displayTagAjax('paging');});
										</script>
<!-- End : ConveyanceAcquittance.jsp -->