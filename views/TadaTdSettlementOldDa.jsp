<!-- Begin : TadaTdSettlementOldDa.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>



<div>
<div class="line" id="daOldDetailsID">
								<div class="quarter bold">Stay DA And Journey DA</div>
								<table style="width:100%" border="1" class="list" id="daOldDetailsId">
									
									<tr>
										<th style="width:10%;text-align:center">Date</th>
										<th style="width:10%;text-align:center">JDA in days</th>
										<th style="width:15%;text-align:center">Amount Per day</th>
										<th style="width:20%;text-align:center">Claimed JDA Amount</th>
										<th style="width:10%;text-align:center">SDA in days</th>
										<th style="width:15%;text-align:center">Amount Per day</th>
										<th style="width:20%;text-align:center">Claimed SDA Amount</th>
									</tr>
									<%int i=0; %>
									<c:forEach var="jdaListDetails" items="${tada.tadaTdJdaList}">
									<tr id="daOldRow<%=i %>">
											<td >
												<input type="text" readonly="readonly" id="startDate<%=i %>" value="${jdaListDetails.journeyDate}" style="height:16px;width:70px;font-size: 9px;font-weight: bold;"  id="startDate0" onfocus ="javascript:Calendar.setup({inputField :'startDate0',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" onchange="javascript:checkJourneyDate('startDate0');"/>
											</td>
											<td ><input type="text" readonly="readonly" id="jda<%=i %>" value="${jdaListDetails.jda}" style="width:100px"/></td>
											<td ><input type="text" readonly="readonly" id="jdaAmount<%=i %>" value="${jdaListDetails.jdaAmount}" style="width:100px"/></td>
											<td ><input type="text" readonly="readonly" id="totalJdaAmount<%=i %>" value="" style="width:100px"/></td>
											<td ><input type="text" readonly="readonly" id="sda<%=i %>" value="${jdaListDetails.sda}" style="width:100px"/></td>
											<td ><input type="text" readonly="readonly" id="sdaAmount<%=i %>" value="${jdaListDetails.sdaAmount}" style="width:100px"/></td>
											<td ><input type="text" readonly="readonly" id="totalSdaAmount<%=i %>" value="" style="width:100px"/></td>
									</tr>
									<% i++; %>
									</c:forEach>
									
									</table>		
								</div>
<script>
	jdaDetailsList = <%= (net.sf.json.JSONArray)session.getAttribute("jdaDetailsList") %>;
	setJdaValues();
</script>
</div>
<!-- End : TadaTdSettlementOldDa.jsp -->