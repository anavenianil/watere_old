<!-- Start : LoanAmountDeatailsList.jsp -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ page
	import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.beans.dto.DesignationDTO,com.callippus.web.beans.holidays.HolidaysBean,com.callippus.web.loan.beans.dto.LoanAmountDetailsDTO,com.callippus.web.loan.beans.dto.LoanDesigMappingDTO,com.callippus.web.loan.beans.dto.LoanAmountGridDTO"%>
<div class="line"><jsp:include page="Result.jsp"></jsp:include></div>
<div id="Pagination"><display:table
	name="${sessionScope.loanAmountList}" excludedParams="*" export="false"
	class="list" requestURI="" id="dataList" pagesize="10" sort="list">
	<display:column title="Loan Type" style="width:40%;">${dataList.loanTypeDetails.loanName}</display:column>
	<display:column title="Gazetted Type" style="width:40%">
		<c:if test="${dataList.gazType == 'GAZ'}">Gazetted</c:if>
		<c:if test="${dataList.gazType == 'NG'}">Non Gazetted</c:if>
	</display:column>
	<display:column style="width:10%;text-align:center"
		title="<center>Edit</center>">
		<img src="./images/edit.gif" title="Edit"
			onclick="editLoanAmountDetails('${dataList.id}')" />
	</display:column>
	<display:column style="width:10%;text-align:center"
		title="<center>Delete</center>">
		<img src="./images/delete.gif" title="Delete"
			onclick="deleteLoanAmountDetails('${dataList.id}')" />
	</display:column>
</display:table></div>
<script>	
	$jq( function(){$jq("#Pagination").displayTagAjax('paging');});
			jsonArrayObject =<%=(net.sf.json.JSONArray) JSONSerializer.toJSON((List<DesignationDTO>) session.getAttribute("designationListJSON"))%>;
			jsonLoanAmountDetails =<%=(net.sf.json.JSONArray) JSONSerializer.toJSON((List<LoanAmountDetailsDTO>) session.getAttribute("loanAmountList"))%>;
			jsonLoanDesigMappings = <%=(net.sf.json.JSONArray) JSONSerializer.toJSON((List<LoanDesigMappingDTO>) session.getAttribute("loanDesigMappings"))%>;
			jsonLoanAmountGrid =<%=(net.sf.json.JSONArray) JSONSerializer.toJSON((List<LoanAmountGridDTO>) session.getAttribute("loanAmountGrid"))%>;
</script>
<!-- End : LoanAmountDeatailsList.jsp -->