<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : DaArrearsPreviewDetailsList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<div class="line">
<fieldset>
<div class="headTitle"><u>${remarks}</u></div>
</fieldset>
</div>
<div>
    <jsp:include page="Result.jsp"></jsp:include>	
</div>
<div class="line" id="daArrears">
	<display:table name="${sessionScope.daArrearsPreviewDetails}" excludedParams="*"
				export="false" class="list" requestURI="" id="arrears" pagesize="1000"
				sort="list" cellpadding="2" cellspacing="1">
				<display:column  style="width:0.5%;vertical-align:middle" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'row\');"/>'>
			       <input type="checkbox"  class="row" name="row"  onclick="checkBoxCheck(this.name);" value="${arrears.sfid}"/>
		        </display:column>
		         <display:column title="SFID" style="width:0.6%;vertical-align:middle">${arrears.sfid}</display:column>
		         <display:column title="Name" style="width:0.6%;vertical-align:middle">${arrears.empName}</display:column>
				 <display:column title="Month" style="width:0.6%;vertical-align:middle">${arrears.arrearsMonth}</display:column>
				 <display:column title="Basic" style="width:0.6%;vertical-align:middle">${arrears.basicPay}</display:column>
				 <display:column title="Grade" style="width:0.6%;vertical-align:middle">${arrears.gradePay}</display:column>
				 <display:column title="DUE DA" style="width:0.6%;vertical-align:middle">${arrears.dueDa}</display:column>
				 <display:column title="DRAWN DA" style="width:0.6%;vertical-align:middle">${arrears.drawnDa}</display:column>
				 <display:column title="DA DIFF" style="width:0.6%;vertical-align:middle">${arrears.daDiff}</display:column>
				 <display:column title="TOTAL" style="width:0.6%;vertical-align:middle">${arrears.totalDa}</display:column>
			     <display:column title="CPF" style="width:0.6%;vertical-align:middle">${arrears.cpf}</display:column>
			     <display:column title="NET PMT" style="width:0.6%;vertical-align:middle">${arrears.netPmt}</display:column>
				 <display:column title="TPT DUE" style="width:0.6%;vertical-align:middle">${arrears.dueTra}</display:column>
				 <display:column title="TPT DRAWN" style="width:0.6%;vertical-align:middle">${arrears.drawnTra}</display:column>
				 <display:column title="TPT DIFF" style="width:0.6%;vertical-align:middle">${arrears.tptDiff}</display:column>
				 <display:column title="TOT TPT" style="width:0.6%;vertical-align:middle">${arrears.totTpt}</display:column>
				 <display:column title="TOT AMT" style="width:0.6%;vertical-align:middle">${arrears.totAmt}</display:column>
	 </display:table>
   <script>$jq('.pagebanner').hide();</script>
</div>
 <div class="line">
 <div  style="margin-left:80%"><a href="javascript:submitDAArrearsDetails();" class="appbutton">Submit</a></div>
 <div class="appbutton"><a href="javascript:printDAArrearsDetails();" class="appbutton">Print</a></div>
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
<!-- End : DaArrearsPreviewDetailsList.jsp -->
