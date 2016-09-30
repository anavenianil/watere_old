<!-- Start : FestivalAcquittance.jsp -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/calendar-hd.js"></script>


<div class="line" id="festivalListresult"><jsp:include page="Result.jsp"></jsp:include>
<div id="Pagination1">
											<display:table name="${sessionScope.FestivalPayAcquittanceList}" excludedParams="*" export="false"	class="list" requestURI="" id="festivalList" pagesize="20" sort="list">
											<display:column  style="width:5%;text-align:center" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(\'selectall\',\'row\');"/>'>
					<input type="checkbox" class="row" name="row" id="${festivalList.id}" onclick="checkBoxCheck();"/>
					</display:column>
												<display:column title="Request ID" style="width:5%;"><a href="javascript:getRequestDetails('${festivalList.historyID}','${festivalList.requestID}','myRequests','pending')"><font color="blue">${festivalList.requestID}</font></a></display:column>
												<display:column title="SFID" style="width:5%;">${festivalList.sfID}</display:column>
												<display:column title="Emp Name" style="width:10%;">${festivalList.empName}</display:column>
												<display:column title="Sanction Number" style="width:8%"><input type="text" size="10" value="<c:if test="${festivalList.sanctionNo!='0'}">${festivalList.sanctionNo}</c:if>"/></display:column>
												<display:column title="Bill Number" style="width:8%"><input type="text" size="10" value="<c:if test="${festivalList.billNo!='0'}">${festivalList.billNo}</c:if>"/></display:column>
												<display:column title="Account Officer<span class='mandatory'>*</span>" style="width:10%;vertical-align:middle">
														<select name="accOfficer" id="accOfficer${festivalList.requestID}" style="width:90px">
														<option value="select">Select</option>
														<c:forEach var="acc" items="${loan.signingAuthorityList}">
														<c:if test="${acc.type =='ACC'}">
														<option value="${acc.sfid}">${acc.name}</option>
														</c:if>
														</c:forEach>
														</select>
														<script>$jq('#accOfficer'+${festivalList.requestID}).val('${festivalList.accOfficer}');</script>
												</display:column>
												
												<display:column title="CFA Officer<span class='mandatory'>*</span>" style="width:10%;vertical-align:middle">
														<select name="cfaOfficer" id="cfaOfficer${festivalList.requestID}" style="width:90px" >
														<option value="select">Select</option>
														<c:forEach var="cfa" items="${loan.signingAuthorityList}">
														<c:if test="${cfa.type =='CFA'}">
														<option value="${cfa.sfid}">${cfa.name}</option>
														</c:if>
														</c:forEach>
														</select>
														<script>$jq('#cfaOfficer'+${festivalList.requestID}).val('${festivalList.cfaOfficer}');</script>
												</display:column>
												
												<display:column title="DV.No" style="width:8%"><input type="text" size="10" value="<c:if test="${festivalList.dvNo!='0'}">${festivalList.dvNo}</c:if>" name="dvNo" id="dvNoReim"/></display:column>
	<display:column title="DV.Date" style="width:12%">
			<input type="text" size="10" readonly="readonly" name="dvDate" id="dvDate${festivalList.requestID}" value="<fmt:formatDate pattern="dd-MMM-yyyy" value="${festivalList.dvDate}" />"/>
			<img  src="./images/calendar.gif" id="dvDateImg${festivalList.requestID}" />	
				<script type="text/javascript">
					Calendar.setup({inputField :"dvDate"+${festivalList.requestID},ifFormat : "%d-%b-%Y",showsTime : false,button :"dvDateImg"+${festivalList.requestID},singleClick : true,step : 1});
				</script>
	</display:column>
	<display:column title="CDA Amount" style="width:8%"><input type="text" size="10" value="<c:if test="${festivalList.cdaAmount!='0'}">${festivalList.cdaAmount}</c:if>" onkeypress="return isNumberExp(event);"/></display:column>
											</display:table>
										</div>
<script>
	$jq( function(){$jq("#Pagination1").displayTagAjax('paging');})
</script>
</div>
