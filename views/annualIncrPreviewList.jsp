<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : annualIncrPreviewList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<div>
    <jsp:include page="Result.jsp"></jsp:include>	
</div>
                         <div class="line">
							<div class="half" id="drawn">
							<fieldset><legend><strong><font color='green'>Drawn</font></strong></legend>
	<display:table name="${sessionScope.annIncrArrearsPrevieList}" excludedParams="*"
				export="false" class="list" requestURI="" id="duetable" pagesize="1000"
				sort="list" cellpadding="2" cellspacing="1">
				<display:column  style="width:0.5%;vertical-align:middle" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'row\');"/>'>
			       <input type="checkbox"  class="row" name="row"  onclick="checkBoxCheck(this.name);" value="${duetable.sfid}"/>
		        </display:column>
		        <display:column title="SFID" style="width:0.6%;vertical-align:middle">${duetable.sfid}</display:column>
				<display:column title="Month" style="width:0.6%;vertical-align:middle">${duetable.arrearsMonth}</display:column>
				<display:column title="Basic" style="width:2%;vertical-align:middle">${duetable.drawnBasicPay}</display:column>
				<display:column title="HRA" style="width:0.8%;vertical-align:middle">${duetable.drawnHra}</display:column>
				<display:column title="DA" style="width:0.8%;vertical-align:middle">${duetable.drawnDa}</display:column>
				<display:column title="TRA" style="width:0.8%;vertical-align:middle">${duetable.drawnTra}</display:column>
               	<display:column title="TOTAL" style="width:0.8%;vertical-align:middle">${duetable.totalDrawn}</display:column>
    </display:table>
   <script>$jq('.pagebanner').hide();</script>
 </fieldset>
</div>
<div class="half" id="due">
	<fieldset><legend><strong><font color='red'>Due</font></strong></legend>
		<display:table name="${sessionScope.annIncrArrearsPrevieList}" excludedParams="*"
				export="false" class="list" requestURI="" id="arrearsdrawn" pagesize="1000" 
				sort="list" cellpadding="2" cellspacing="1">
			    <display:column title="Basic" style="width:0.6%;vertical-align:middle">${arrearsdrawn.dueBasicPay}</display:column>
				<display:column title="HRA" style="width:0.6%;vertical-align:middle">${arrearsdrawn.dueHra}</display:column>
				<display:column title="DA" style="width:0.6%;vertical-align:middle">${arrearsdrawn.dueDa}</display:column>
      	        <display:column title="TRA" style="width:0.5%;vertical-align:middle">${arrearsdrawn.dueTra}</display:column>
			    <display:column title="TOTAL" style="width:0.6%;vertical-align:middle">${arrearsdrawn.totalDue}</display:column>
				 <display:column title="DIFF" style="width:2.5%;vertical-align:middle">${arrearsdrawn.total}</display:column>
		</display:table>
        <script>$jq('#pagebanner').hide();</script>
	</fieldset></div>
</div>
 <div class="line">
 <div  style="margin-left:80%"><a href="javascript:submitAnnualIncrArrearsDetails();" class="appbutton">Submit</a></div>
 <div class="appbutton"><a href="javascript:printAnnualIncrArrearsDetails();" class="appbutton">Print</a></div>
 </div>

<script>
	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	
	
</script>
<spring:bind path="arrears">
<form:hidden path="arrears.adminAccDate"/>
</spring:bind>
<spring:bind path="arrears">
<form:hidden path="arrears.financeAccDate"/>
</spring:bind>
<!-- End : annualIncrPreviewList.jsp -->
