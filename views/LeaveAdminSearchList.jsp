<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : Leave12AdminSearchList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<script type="text/javascript" src="script/date.js"></script>

<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />

<div class="line">
   <!-- <div class="line">
		<div class="quarter bold">Gazetted Type</div>
			<div class="quarter">
			    <select path="gazettedType" id="gazettedType" cssClass="formSelect" onchange="javascript:getGazettedType()" >
					<option value="Select">Select</option>
               	    <option value="GAZETTED">Gazetted</option>
               	    <option value="NON GAZETTED">Non Gazetted</option>
               	 </select>
           </div>
		</div> -->
	
	 <div class="line">
		<div class="quarter leftmar">Gazetted Type</div>
		<div class="quarter">
			<select name="gazettedType" id="gazettedType" onchange="javascript:getGazettedType()">
			<option value="0">Select</option>
			<c:forEach var="TypeMasterList" items="${TypeMasterList}">
				<option value="${TypeMasterList.id}">${TypeMasterList.name}</option>
			</c:forEach>
			</select>
		</div>
		<div id="casualityDiv" style="display:none">
			<div class="quarter bold">Casuality</div>
			<div class="quarter">
				<select name="casualityId" id="casualityId" onchange="javascript:getLeaveSearchList()" onmouseover="setSelectWidth('#casualityId')">
					<option value="0">Select</option>
					<c:forEach var="CasualitiesMasterList" items="${CasualitiesMasterList}">
						<option value="${CasualitiesMasterList.id}#${CasualitiesMasterList.code}">${CasualitiesMasterList.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>         
	</div>
	
	<div class="line">		
	<!-- <div class="line">			
			
			<div class="quarter bold">Do Part Date</div>
			<div class="quarter">
				<input type="text" id="doPartDate" class="dateClass" readonly="readonly" onchange="javascript:getDoPartNumber()"/>
				<img  src="./images/calendar.gif" id="doPartDateImg" />	
				<script type="text/javascript">
					Calendar.setup({inputField :"doPartDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"doPartDateImg",singleClick : true,step : 1});
								
				</script>			
			</div>
		</div> -->
		<div class="line" id="doPartNumberDiv" style="display:none">
			<div class="quarter leftmar">DoPart Number</div>
				<div class="quarter">
					<select name="doPartNo" id="doPartNo" onchange="javascript:getdoPartButton();getDateWiseLeaveList();" onmouseover="setSelectWidth('#doPartNo')">
						<option value="select">Select</option>
						<c:forEach var="doPartList" items="${doPartList}">
							<c:choose>
								<c:when test="${doPartList.id != 1}">
									<option value="${doPartList.id}#<fmt:formatDate pattern="dd-MMM-yyyy" value="${doPartList.doPartDate}"/>">${doPartList.doPartNumber} - Dated <fmt:formatDate pattern="dd-MMM-yyyy" value="${doPartList.doPartDate}"/></option>
								</c:when>
								<c:otherwise>
									<option value="${doPartList.id}#<fmt:formatDate pattern="dd-MMM-yyyy" value="${doPartList.doPartDate}"/>">${doPartList.doPartNumber}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
			 	</div>
			<div id="serialNumberDiv" style="display:none">
				<div class="quarter bold">Serial Number</div>
				<div class="quarter">
					<input type="text" id="serialNumber"/>
				</div>
			</div>
        </div>
		<div class="line" id="publishButtonDiv" style="display:none">
			<div class="expbutton" style="float:right"><a href="javascript:completeRequests();" ><span>Publish in DO Part II</span></a></div>
		</div>
		<div class="line" id="saveButtonDiv" style="display:none">
			<div class="expbutton"  style="float:right"><a href="javascript:completeRequests();" ><span>Save No Action</span></a></div>
		</div>
	</div>
	<div class="line"><hr /></div>
	
	<div class="line" id="grantOfLeave" style="display:none">
		<div id="Pagination"><span><b>Grant Of Leave</b></span>
		<div class="line">
			<jsp:include page="RequestResult.jsp" />
		</div>
			<display:table name="${sessionScope.appliedLeavesList}" excludedParams="*" export="false" class="list" requestURI="" id="leaveList" pagesize="10" sort="list">				 		
				<display:column style="width:5%;text-align:center" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'row\');"/>'>
					<input type="checkbox" class="row" name="row" id="${leaveList.requestID}" onclick="checkBoxCheck(this.name);enableDoPartNo('leaveList');"/>
				</display:column>
				<display:column title="SFID" style="width:5%">&nbsp;${leaveList.sfID}</display:column>
				<display:column title="Req ID" style="width:6%">&nbsp;${leaveList.requestID}</display:column>
				<display:column title="Name" style="width:20%">&nbsp;${leaveList.name}</display:column>
				<display:column title="Designation" style="width:20%">&nbsp;${leaveList.designationName}</display:column>
				<display:column title="Nature of Leave" style="width:12%" sortable="true">${leaveList.leaveType}</display:column>
				<display:column title="From Date" style="width:8%">${leaveList.fromDate}</display:column>
				<display:column title="To Date" style="width:8%">&nbsp;${leaveList.toDate}</display:column>
				<display:column title="No of leaves" style="width:8%;text-align:right">&nbsp;<c:out value="${leaveList.noOfDays}"/></display:column>
				<display:column title="Status" style="width:8%;text-align:right">&nbsp;${leaveList.statusMsg}</display:column>
			</display:table>			
		</div>		
	</div>	
	
	<div class="line" id="cancellationOfLeave" style="display:none">
		<div id="Pagination1"><span><b>Cancellation Of Leave</b></span>
		<div class="line">
		<jsp:include page="RequestResult.jsp" />
		</div>
			<display:table name="${sessionScope.cancelledLavesList}" excludedParams="*" export="false" class="list" requestURI="" id="leaveCancelList" pagesize="10" sort="list">
				<display:column  style="width:5%;text-align:center" title='<input type=checkbox  name="cancelall" id="cancelall" onclick="checkBoxCheckAll(this.name,\'row1\');"/>'>
					<input type="checkbox" class="row1" name="row1" id="${leaveCancelList.requestID}" onclick="checkBoxCheck(this.name);enableDoPartNo('leaveCancelList');"/>
				</display:column>
				<display:column title="SFID" style="width:5%">&nbsp;${leaveCancelList.sfID}</display:column>
				<display:column title="Req ID" style="width:6%">&nbsp;${leaveCancelList.requestID}</display:column>
				<display:column title="Name" style="width:20%">&nbsp;${leaveCancelList.name}</display:column>
				<display:column title="Designation" style="width:20%">&nbsp;${leaveCancelList.designationName}</display:column>
				<display:column title="Nature of Leave" style="width:12%" sortable="true">
					${leaveCancelList.leaveType}
				</display:column>
				<display:column title="From Date" style="width:8%">&nbsp;${leaveCancelList.fromDate}</display:column>
				<display:column title="To Date" style="width:8%">&nbsp;${leaveCancelList.toDate}</display:column>
				<display:column title="No of leaves" style="width:8%;text-align: right;">&nbsp;${leaveCancelList.noOfDays}</display:column>
				<display:column title="Status" style="width:8%;text-align:right">&nbsp;${leaveCancelList.statusMsg}</display:column>
			</display:table>			
		</div>		
	</div>
	
	<div class="line" id="conversionOfLeave" style="display:none">
		<div id="Pagination2"><span><b>Conversion Of Leave</b></span>
		<div class="line">
		<jsp:include page="RequestResult.jsp" />
		</div>
			<display:table name="${sessionScope.convertedLavesList}" excludedParams="*"
				export="false" class="list" requestURI="" id="leaveConvertList" pagesize="5"
				sort="list">				 		
				<display:column style="width:5%;text-align:center" title='<input type=checkbox  name="convertall" id="convertall" onclick="checkBoxCheckAll(this.name,\'row2\');"/>'>
					<input type="checkbox" class="row2" name="row2" id="${leaveConvertList.requestID}" onclick="checkBoxCheck(this.name);enableDoPartNo('leaveConvertList');"/>
				</display:column>
				<display:column title="SFID" style="width:5%">&nbsp;${leaveConvertList.sfID}</display:column>
				<display:column title="Req ID" style="width:6%">&nbsp;${leaveConvertList.requestID}</display:column>
				<display:column title="Name" style="width:20%">&nbsp;${leaveConvertList.name}</display:column>
				<display:column title="Designation" style="width:20%">&nbsp;${leaveConvertList.designationName}</display:column>
				<display:column title="Nature of Leave" style="width:12%" sortable="true">
					${leaveConvertList.leaveType}					
				</display:column>
					<display:column title="From Date" style="width:8%">&nbsp;${leaveConvertList.fromDate}</display:column>
				<display:column title="To Date" style="width:8%">&nbsp;${leaveConvertList.toDate}</display:column>
				<display:column title="No of leaves" style="width:8%;text-align: right;">&nbsp;${leaveConvertList.noOfDays}</display:column>
				<display:column title="Status" style="width:8%;text-align:right">&nbsp;${leaveConvertList.statusMsg}</display:column>
			</display:table>			
		</div>		
	</div>
	
	<script>
			$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
			$jq( function(){ $jq("#Pagination1").displayTagAjax('paging');})
			$jq( function(){ $jq("#Pagination2").displayTagAjax('paging');})
	</script>
</div>
<!-- End : LeaveAdminSearchList.jsp -->
