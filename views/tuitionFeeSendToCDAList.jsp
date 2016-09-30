<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : tuitionFeeSendToCDAList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<div class="line"><jsp:include page="Result.jsp"/>
</div>
<div class="line">
<b>TOTAL : ${sessionScope.cdaTotal}</b>
</div>
<div id="dataTable">
<%int i=1;
int total=0;
 %>
 <c:if test="${tutionFee.claimType=='1'}">
	<display:table name="${sessionScope.tfCDAList}" excludedParams="*"
		export="false" class="list" requestURI="" id="tf" pagesize="1000"
		sort="list" cellpadding="2" cellspacing="1">
		<display:column  style="width:3%;vertical-align:middle" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'row\');"/>'>
			<input type="checkbox" value="${tf.pk}" class="row" name="row" id="encash<%=i %>" onclick="checkBoxCheck(this.name);"/>
		</display:column>
		<display:column title="S.No." style="width:0.3%;vertical-align:middle"><%=i+" )" %></display:column>
		<display:column title="REQUESTID" style="width:2%;vertical-align:middle">${tf.requestID}</display:column>
		<display:column title="SFID" style="width:0.6%;vertical-align:middle">${tf.sfid}</display:column>
		<display:column title="Name" style="width:7%;vertical-align:middle">${tf.empName}</display:column>
		<display:column title="Desig" style="width:7%;vertical-align:middle">${tf.desig}</display:column>
		<%-- <display:column title="Bill Period" style="width:2.5%;vertical-align:middle">${tf.type}</display:column>--%>
		<display:column title="Bill Amount" style="width:0.8%;vertical-align:middle">${tf.claimedAmount}</display:column>
		<display:column title="Amt Admissible" style="width:0.8%;vertical-align:middle">${tf.sanctionedAmount}</display:column>
		<display:column title="G.Total" style="width:0.8%;vertical-align:middle">${tf.sanctionedAmount}</display:column>
		<%--<display:column title="DV.No" style="width:7%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${tf.dvNo}" name="dvNo" id="dvNo<%=i %>" onkeypress="javascript:return checkInt(event);" /></display:column>
		<display:column title="DV.Date" style="width:14%;vertical-align:middle">&nbsp;
			<input type="text" size="13" value='<fmt:formatDate value="${tf.dvDate}" pattern="dd-MMM-yyyy"/>' readonly="readonly" name="dvDate" id="dvDateEncash<%=i %>"/>
			<img  src="./images/calendar.gif" id="dvDateEncashImg<%=i %>" />	
				<script type="text/javascript">
					Calendar.setup({inputField :"dvDateEncash<%=i %>",ifFormat : "%d-%b-%Y",showsTime : false,button :"dvDateEncashImg<%=i %>",singleClick : true,step : 1});
				</script>
		</display:column>--%>
		<display:column title="CDA Amount" style="width:2%;vertical-align:middle;">&nbsp;<input type="text" style="background-color: pink;" size="15" value="${tf.cdaAmount}" name="cdaAmount" id="cdaAmount<%=i %>" onkeypress="javascript:return checkInt(event);"/></display:column>
		<%i++;
		%>
		
	</display:table>
	</c:if>
	<c:if test="${tutionFee.claimType=='2'}">
	<display:table name="${sessionScope.tfCDAList}" excludedParams="*"
		export="false" class="list" requestURI="" id="tf" pagesize="1000"
		sort="list" cellpadding="2" cellspacing="1">
		<display:column  style="width:3%;vertical-align:middle" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'row\');"/>'>
			<input type="checkbox" value="${tf.pk}" class="row" name="row" id="encash<%=i %>" onclick="checkBoxCheck(this.name);"/>
		</display:column>
		<display:column title="S.No." style="width:0.3%;vertical-align:middle"><%=i+" )" %></display:column>
		<display:column title="REQUESTID" style="width:2%;vertical-align:middle">${tf.requestID}</display:column>
		<display:column title="SFID" style="width:0.6%;vertical-align:middle">${tf.sfid}</display:column>
		<display:column title="Name" style="width:7%;vertical-align:middle">${tf.empName}</display:column>
		<display:column title="Desig" style="width:7%;vertical-align:middle">${tf.desig}</display:column>
		<display:column title="Bill Amount" style="width:0.8%;text-align:right">${tf.sanctionedAmount}</display:column>
		<display:column title="Eligibility" style="width:0.8%;text-align:right">${tf.eligiblity}</display:column>
		<display:column title="Amt Admissible" style="width:0.8%;text-align:right">${tf.amtAdmissible}</display:column>
		<display:column title="G.Total" style="width:0.8%;text-align:right">${tf.amtAdmissible}</display:column>
		<display:column title="CDA Amount" style="width:2%;text-align:right">&nbsp;<input type="text" style="background-color: pink;" size="8" value="${tf.amtAdmissible}" name="cdaAmount" id="cdaAmount<%=i %>" onkeypress="javascript:return checkInt(event);" style="text-align: right;"/></display:column>
		<%i++;
		%>
		
	</display:table>
	</c:if>
		<script>
		   $jq('.pagebanner').hide();
		</script>
</div>
<div class="line">&nbsp;</div>
<div class="quarter leftmar">DV.No<span style="color:red">*</span></div>
<input path="cdaDvNo" id="cdaDvNo" onkeypress="javascript:return checkInt(event);"/>
<div class="line">
			<div class="quarter leftmar" style="width: 24%;"><b>DV Date</b><span class='failure'>*</span> </div>
			<div class="quarter">
				<input name="fromDate" id="fromDate"  readonly="readonly"  style="height:16px;width:90;font-size: 11px;font-weight: bold;" value="${mtBean.currentDate}"/>
					<img  src="./images/calendar.gif" id="date_start_trigger1" />	
						<script type="text/javascript">
							Calendar.setup({inputField :"fromDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
						</script>
			</div>
</div>
<div class="line">
        <div class="expbutton" style="float:right"><a href="javascript:saveTutionFeeCdaAmount();"><span>Save</span></a>
           </div> 
</div>

<div class="line">
<c:if test="${tutionFee.claimType=='1'}">
           <div class="expbutton" style="float:right"><a href="javascript:printAllTuitionFeeRelatedDocuments();"><span>SANCTION AND BILL REPORT</span></a>
           </div>
           
           <div class="expbutton" style="float:right"><a href="javascript:printTuitionFeeSanctionedIndividualReport();"><span>SY.BILL REPORT</span></a>
           </div>
</c:if>
<c:if test="${tutionFee.claimType=='2'}">
 		 <div class="expbutton" style="float:right"><a href="javascript:printTelephoneBillSanctionedIndividualReport();"><span>CDA-Print2</span></a>
         </div>
         <div class="expbutton" style="float:right"><a href="javascript:printAllTelephoneBillRelatedDocuments();"><span>CDA-Print1</span></a>
         </div>
</c:if>
<script>
requestIDTF = '<%= session.getAttribute("tutionFeeRequestID") != null ? session.getAttribute("tutionFeeRequestID") : "" %>';
</script>
</div>

<!-- End : tuitionFeeSendToCDAList.jsp -->





