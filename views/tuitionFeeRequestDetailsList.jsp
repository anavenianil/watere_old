<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page
	import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.beans.family.FamilyBean"%>
	<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<div id="returnrequest">
									<%-- Content Page starts --%>
									 <c:if test="${message=='tutionFeeRequest'}"><span class="success">Successfully Applied For Tuition Fee</span></c:if>
								     <div class="line" id="childInfo">
										<fieldset><legend><strong><font color='green'>Children Info</font></strong></legend>
										<display:table name="${tutionFee.familyList}" excludedParams="*"
												export="false" class="list" requestURI="" id="family" pagesize="5"
												sort="list">
													<display:column title="Name" style="width:20%;text-align:center">${family.name}</display:column>
													<display:column title="Date Of Birth" style="width:20%;text-align:center">${family.dob}</display:column>
													<display:column title="Relation Type" style="width:20%;text-align:center">${family.relation}
                                                    </display:column>
													<display:column title="Class" style="width:20%;text-align:center">
                                                       <spring:bind path="tutionFee"><form:select path="tutionFee.classId" id="classId${family.id}" cssStyle="width:145px;">
												             <form:option value="select">Select</form:option>
												             <form:options items="${sessionScope.academicYearList}" itemLabel="className" itemValue="id"/>
											            </form:select></spring:bind>
                                                    </display:column>
                                                    <display:column title="central/state" style="width:15%;text-align:center">
                                                         <spring:bind path="tutionFee"><form:select path="tutionFee.boardId" id="boardId${family.id}" cssStyle="width:145px;">
                                                         <form:option value="select">Select</form:option>
                                                         <form:option value="C">Central</form:option>
											           <form:option value="S">State</form:option>
							   	                    </form:select></spring:bind>
                                                      </display:column>
                                                      <display:column title="Type" style="width:15%;text-align:center">
                                                         <spring:bind path="tutionFee"><form:select path="tutionFee.typeId" id="typeId${family.id}" cssStyle="width:145px;">
                                                         <form:option value="select">Select</form:option>
												           <form:option value="Q">Quarterly</form:option>
												           <form:option value="H">HalfYearly</form:option>
												           <form:option value="A">Annually</form:option>
												          </form:select></spring:bind>
                                                      </display:column>
                                     	</display:table>
										</fieldset>
								</div>
									<%int i=5;
								    %>
								   
			  <div class="line" id="claimDeatails">
					<c:forEach items="${tutionFee.familyList}" var="flist">
						<fieldset id="field${flist.id}" style="display:none"><legend><strong><font color='green'>In respective of </font><font color="red">${flist.name}</font></strong></legend>
						  <div class="line" id="CQ${flist.id}" style="display:none">
						   <spring:bind path="tutionFee"><form:select path="tutionFee.limitId"  cssClass="fromSelect" style="width:25%">
                                  <form:option value="select">Select</form:option>
                                  <c:forEach var="cql" items="${tutionFee.centralQuarterList}">
                                  <c:if test="${cql.id==flist.id}">
                                  <form:option value="${cql.key}">${cql.value}</form:option>
                                  </c:if>
                                  </c:forEach>
			                </form:select></spring:bind>
				          </div>
				          
				          <div class="line" id="CH${flist.id}" style="display:none">
						      <div class="quarter bold">Period Of Claim<span style="color:red">*</span></div>
						   <spring:bind path="tutionFee"><form:select path="tutionFee.limitId" cssClass="fromSelect" style="width:25%">
                                  <form:option value="select">Select</form:option>
                                  <c:forEach var="chl" items="${tutionFee.centralHalfList}">
                                  <c:if test="${chl.id==flist.id}">
                                  <form:option value="${chl.key}">${chl.value}</form:option>
                                  </c:if>
                                  </c:forEach>
			                </form:select></spring:bind>
				          </div>
				          
				          <div class="line" id="SQ${flist.id}" style="display:none">
						      <div class="quarter bold">Period Of Claim<span style="color:red">*</span></div>
						   <spring:bind path="tutionFee"><form:select path="tutionFee.limitId" cssClass="fromSelect" style="width:25%">
                                  <form:option value="select">Select</form:option>
                                 <c:forEach var="sql" items="${tutionFee.stateQuarterList}">
                                  <c:if test="${sql.id==flist.id}">
                                  <form:option value="${sql.key}">${sql.value}</form:option>
                                  </c:if>
                                  </c:forEach>
			                </form:select></spring:bind>
				          </div>
				          
				          <div class="line" id="SH${flist.id}" style="display:none">
						      <div class="quarter bold">Period Of Claim<span style="color:red">*</span></div>
						   <spring:bind path="tutionFee"><form:select path="tutionFee.limitId" cssClass="fromSelect" style="width:25%">
                                  <form:option value="select">Select</form:option>
                                  <c:forEach var="shl" items="${tutionFee.stateHalfList}">
                                  <c:if test="${shl.id==flist.id}">
                                  <form:option value="${shl.key}">${shl.value}</form:option>
                                  </c:if>
                                  </c:forEach>
			                </form:select></spring:bind>
				          </div>
                           <div class="line" id="CA${flist.id}" style="display:none">
								 <div class="quarter bold">Period Of Claim<span style="color:red">*</span></div>
						     <spring:bind path="tutionFee"><form:select path="tutionFee.limitId" cssClass="fromSelect" style="width:25%">
                                  <form:option value="select">Select</form:option>
                                 <c:forEach var="cal" items="${tutionFee.centralAnnualList}">
                                  <c:if test="${cal.id==flist.id}">
                                  <form:option value="${cal.key}">${cal.value}</form:option>
                                  </c:if>
                                  </c:forEach>
                             </form:select></spring:bind>
				          </div>
				          <div class="line" id="SA${flist.id}" style="display:none">
								 <div class="quarter bold">Period Of Claim<span style="color:red">*</span></div>
						     <spring:bind path="tutionFee"><form:select path="tutionFee.limitId" cssClass="fromSelect" style="width:25%">
                                  <form:option value="select">Select</form:option>
                                 <c:forEach var="sal" items="${tutionFee.stateAnnualList}">
                                  <c:if test="${sal.id==flist.id}">
                                  <form:option value="${sal.key}">${sal.value}</form:option>
                                  </c:if>
                                  </c:forEach>
                             </form:select></spring:bind>
				          </div>
				          <table width="90%" cellpadding="2" cellspacing="0" align="center"
									border="1" class="list" bgcolor="#10519a" id="itConfigSavings">
								   <tr class=list>
										<th width="40%" style="text-align: center">Detail</th>
										<th width="15%" style="text-align: center">Receipt No.</th>
										<th width="15%" style="text-align: center">Dated</th>
										<th width="20%" style="text-align: center">Amount</th>
										<th style="display:none"></th>
									</tr>
									
								   <c:forEach items="${tutionFee.claimList}" var="sub">
								       <tr class="even">
											<td width="30%">${sub.claimName}</td>
											 <td><input  type="text" size="20px" id="receiptNo<%=i %>"  onkeypress="javascript:checkForPeriodOfClaim(${flist.id})" /></td>
											<td >
												<input type="text" readonly="readonly" id="dated<%=i %>"  style="height:16px;width:65px;font-size: 9px;font-weight: bold;"  id="dated<%=i %>" onfocus ="javascript:Calendar.setup({inputField :'dated<%=i %>',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});"  />
											</td>
											<td width="10%" onkeypress="javascript:checkForPeriodOfClaim(${flist.id})"><input id="amount<%=i %>"
													size="20" maxlength="6" style="text-align:right" onkeypress="return checkInt(event);"  onkeyup="javascript:sumAllClaimAmounts();"/></td>
											<td><input type="button" id="add0" value="+" onclick="javascript:checkCreateNewRowForTuition(<%=i %>,${flist.id});"/></td>
											<td><input type="button" id="del0" value="-" onclick="javascript:deleteRowOfTuition(this,'itConfigSavings');" /></td>
											
											<td style="display:none">
											<form><input type="text" value="${sub.id}" /></form>
											</td>
										</tr>
										<%i++; %>
								</c:forEach></table>
							</fieldset>
								</c:forEach> 
								</div>
								<div class="line" id="grandTotalDiv" style="display:none">
								<table class="list">
								<tr class="even">
				                    <td width="70%" ><font color="blue"><b>GRAND TOTAL</b></font></td>
				                    <td width="30%">
					                  <spring:bind path="tutionFee"><form:input path="tutionFee.grandTotal" readonly="true" id="grandTotal" style="text-align:right"
						                  size="40"/></spring:bind></td></tr></table>
								</div>
								<div class="line">
								<div class="expbutton" style="margin-left:80%"><a onclick="javascript:manageTutionFeeRequestDetails();"> <span style="align:right">Send Request</span></a></div></div>
									
									<script>
									$jq('.pagebanner').hide();
									</script>
<script>
tutionFeeBean= <%= (net.sf.json.JSONObject) session.getAttribute("tutionFeeBean") %>;
</script>		
<script>
requestIDt = '<%= session.getAttribute("tutionFeeReID") != null ? session.getAttribute("tutionFeeReID") : "" %>';


</script>								
									<%-- Content Page end --%>
								</div>