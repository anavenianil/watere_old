<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : DoPartIIPortal.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
	
	
	<div id="Pagination">
	<%int i=0; %>
		<display:table name="${sessionScope.doPartList}" excludedParams="*"
			export="false" class="list" requestURI="" id="doPartList" pagesize="10"
			sort="list">
			<c:if test="${sessionScope.masterType eq 'all'}">
			<display:column title="DOPart No" style="width:25%">&nbsp;<a href="javascript:leaveApplication('${doPartList.id}','doPartReport','');">${doPartList.doPartNumber}</a></display:column>
			</c:if>		
			<c:if test="${sessionScope.masterType ne 'all'}"><display:column title="DOPart No" style="width:25%">&nbsp;<a href="javascript:leaveApplication('${doPartList.id}','doPartReport','');">${doPartList.doPartNumber}</display:column></c:if>
			<display:column title="DOPart Date" style="width:25%">&nbsp;<fmt:formatDate value="${doPartList.doPartDate}" pattern="dd-MMM-yyyy" /></display:column>
			 <c:if test="${sessionScope.masterType eq 'r'}">		                    <!-- This column has been added -->	 
			<display:column title="Freeze Out" style="width:10%;vertical-align:middle">&nbsp;
			
			<div style="float:inherit;"><a href="javascript:doFreezeOut('${doPartList.id}','<%=i %>');"  class="appbutton" style="text-decoration: none;float:left; font-size: 13px; background-color: #FF0000;">UnFreeze</a></div>
		 
			</display:column>
			</c:if>
			 
			 
			 <c:if test="${sessionScope.masterType eq 'all'}">
			<display:column title="Released Date" style="width:25%">&nbsp;<fmt:formatDate value="${doPartList.releasedDate}" pattern="dd-MMM-yyyy" /></display:column>
			<display:column title="Accepted Date" style="width:25%">&nbsp;<fmt:formatDate value="${doPartList.acceptedDate}" pattern="dd-MMM-yyyy" /></display:column>
			 </c:if>
			 <c:if test="${sessionScope.masterType eq 'r'}">			 
			<display:column title="Released Date" style="width:25%;vertical-align:middle">&nbsp;
			<input type="text" size="13"  readonly="readonly" name="ReleasedDate" id="ReleasedDate<%=i %>" onchange="javascript:checkDopartDate(this);" value="<fmt:formatDate value="${doPartList.releasedDate}" pattern="dd-MMM-yyyy" />"   />
			<img  src="./images/calendar.gif" id="ReleasedDateImg<%=i %>" />
			<script>
					Calendar.setup({inputField :"ReleasedDate<%=i %>",ifFormat : "%d-%b-%Y",showsTime : false,button :"ReleasedDateImg<%=i %>",singleClick : true,step : 1});
				</script>
			<div style="float:right"><a href="javascript:saveDOPartPortal('${doPartList.id}','<%=i %>');"  class="appbutton" style="text-decoration: none;float:right">Release</a></div>
		   </display:column>
		   </c:if>
		   <c:if test="${sessionScope.masterType eq 'a'}">
		   	<display:column title="Accepted Date" style="width:25%;vertical-align:middle">&nbsp;
			<input type="text" size="13" value='${doPartList.doPartType}' readonly="readonly" name="AcceptedDate" id="AcceptedDate<%=i %>" onchange="javascript:checkDopartDate(this);"/>
			<img  src="./images/calendar.gif" id="AcceptedDateImg<%=i %>" />
			<div style="float:right"><a href="javascript:saveDOPartPortal('${doPartList.id}','<%=i %>');"  class="appbutton" style="text-decoration: none;float:right">Accept</a></div>	
				<script type="text/javascript">
					Calendar.setup({inputField :"AcceptedDate<%=i %>",ifFormat : "%d-%b-%Y",showsTime : false,button :"AcceptedDateImg<%=i %>",singleClick : true,step : 1});
				</script>
		   </display:column>
		   </c:if>
		  
		   
		<% i++; %>
		</display:table>
	</div>	
	<script>
		$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
		var type='<c:out value='${sessionScope.masterType}'/>';
		
	</script>
	

<!-- End : DoPartIIPortal.jsp-->