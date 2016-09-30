<!-- Begin : OptionCertificateList1.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<div id="resultvalue">
<jsp:include page="Result.jsp"></jsp:include>
</div>

<div id="Pagination">

	<%int i=0; %>
	<display:table name="${requestScope.OptionalCertificateList}" excludedParams="*"
		export="false" class="list" requestURI="" id="list" pagesize="1000" 
		sort="page" defaultsort="1" defaultorder="descending">
	
		<display:column  style="width:3%;vertical-align:middle"  sortable="true" sortProperty="optionStatus" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxPromotionCheckAll(this.name,\'row\');"/>'>
			
			<c:if test="${list.optionStatus != 1}">
				<input type="checkbox" class="row" name="row" value="${list.optionStatus}" id="check<%=i %>" disabled="disabled" />
			</c:if>
			<c:if test="${list.optionStatus == 1}">
				<input type="checkbox" class="row" name="row" value="${list.optionStatus}" id="check<%=i %>" onclick="javascript:checkAnnual(this,'${list.annualIncrementId}');checkPromotionDate(this);"/>
				<input type="hidden" value="4" id="${list.optionStatus}"/>
			</c:if>			
		</display:column>
		<display:column title="Annual Increment Status" style="width:2%">
		<c:choose>
		<c:when test="${list.annualIncrementId == null}"></c:when>
		<c:otherwise>Updation Pending</c:otherwise>
		</c:choose>
		</display:column>
		<display:column title="SFID" style="width:5%" sortable="true" >${list.sfID}</display:column>
		<display:column title="Name" style="width:10%" sortable="true">${list.empName}</display:column>		
		<display:column title="DesignationFrom-DesignationTo" style="width:23%" sortable="true" ><div id="${list.designationID}">${list.designation} - ${list.designationTo}</div></display:column>				
		<display:column title="Department" style="width:10%" sortable="true" ><div id="${list.departmentID}">${list.department}</div></display:column>	
		<c:choose>
		<c:when test="${list.payStatus==1}"><display:column title="Option Status" style="width:7%" sortable="true">Higher Grade</display:column></c:when>
		<c:when test="${list.payStatus==2}"><display:column title="Option Status" style="width:7%" sortable="true">Lower Grade</display:column></c:when>
		<c:otherwise><display:column title="Option Status" style="width:7%" sortable="true">Not Applied</display:column></c:otherwise>	
		</c:choose>
		<display:column title="Promotion Date" style="width:10%;text-align:right" sortable="true" ><fmt:formatDate pattern="dd-MMM-yyyy" value="${list.promotionDate}"/></display:column>
		<display:column title="Effective Date" style="width:10%;text-align:right" sortable="true"><fmt:formatDate pattern="dd-MMM-yyyy" value="${list.effectiveDate}"/></display:column>				
		<display:column title="Variable Incr" style="width:2%;text-align:right" sortable="true">${list.variableIncr}</display:column>
						
		
		<display:column title="Joining Report" style="width:10%" sortable="true">
		<c:if test="${list.optionStatus != null}">
				<a href="javascript:joiningReport(${list.optionStatus})">Joining Report</a>
			</c:if>
		</display:column>
		<display:column title="Option Certificate" sortable="true" style="width:10%"> 
		<c:choose>
		<c:when test="${list.optionStatus != null}"><a href="javascript:optionalCertificate(${list.optionID})">Get Certificate</a></c:when>
		<c:otherwise><font color="red">Option Not Saved</font></c:otherwise></c:choose>
		</display:column>
		<% i++; %>
	
	</display:table>
	<script>
		$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
	</script>
</div>
<!-- End : OptionCertificateList1.jsp -->
