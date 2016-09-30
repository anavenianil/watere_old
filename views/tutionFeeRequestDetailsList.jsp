<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:tutionFeeRequestDetailsList.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page
	import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.beans.family.FamilyBean"%>
	<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>



								<%-- Content Page starts --%>
						 <div class="line" id="CQ${tutionFee.familyChildId}" style="display: none;">
						  <div class="quarter bold">Period Of Claim<span style="color:red">*</span></div>
						   <spring:bind path="tutionFee"><form:select path="tutionFee.limitId"  cssClass="fromSelect"  style="width:25%" >
                                  <form:option value="select">Select</form:option>
                                  <c:forEach var="cql" items="${tutionFee.centralQuarterList}" >
                                  <c:if test="${cql.id==tutionFee.familyChildId}">
                                  <form:option value="${cql.key}">${cql.value}</form:option>
                                  </c:if>
                                  </c:forEach>
			                </form:select></spring:bind>
				          </div>
				          <div class="line" id="CH${tutionFee.familyChildId}" style="display: none;">
						      <div class="quarter bold">Period Of Claim<span style="color:red">*</span></div>
						   <spring:bind path="tutionFee"><form:select path="tutionFee.limitId" cssClass="fromSelect" style="width:25%" >
                                  <form:option value="select">Select</form:option>
                                  <c:forEach var="chl" items="${tutionFee.centralHalfList}">
                                  <c:if test="${chl.id==tutionFee.familyChildId}">
                                  <form:option value="${chl.key}">${chl.value}</form:option>
                                  </c:if>
                                  </c:forEach>
			                </form:select></spring:bind>
				          </div>
				          
				          <div class="line" id="SQ${tutionFee.familyChildId}" style="display: none;">
						      <div class="quarter bold">Period Of Claim<span style="color:red">*</span></div>
						   <spring:bind path="tutionFee"><form:select path="tutionFee.limitId" cssClass="fromSelect" style="width:25%" >
                                  <form:option value="select">Select</form:option>
                                 <c:forEach var="sql" items="${tutionFee.stateQuarterList}">
                                  <c:if test="${sql.id==tutionFee.familyChildId}">
                                  <form:option value="${sql.key}">${sql.value}</form:option>
                                  </c:if>
                                  </c:forEach>
			                </form:select></spring:bind>
				          </div>
				          
				          <div class="line" id="SH${tutionFee.familyChildId}" style="display: none;">
						      <div class="quarter bold">Period Of Claim<span style="color:red">*</span></div>
						   <spring:bind path="tutionFee"><form:select path="tutionFee.limitId" cssClass="fromSelect" style="width:25%" >
                                  <form:option value="select">Select</form:option>
                                  <c:forEach var="shl" items="${tutionFee.stateHalfList}">
                                  <c:if test="${shl.id==tutionFee.familyChildId}">
                                  <form:option value="${shl.key}">${shl.value}</form:option>
                                  </c:if>
                                  </c:forEach>
			                </form:select></spring:bind>
				          </div>
                           <div class="line" id="CA${tutionFee.familyChildId}" style="display: none;">
								 <div class="quarter bold">Period Of Claim<span style="color:red">*</span></div>
						     <spring:bind path="tutionFee"><form:select path="tutionFee.limitId" cssClass="fromSelect" style="width:25%" >
                                  <form:option value="select">Select</form:option>
                                 <c:forEach var="cal" items="${tutionFee.centralAnnualList}">
                                  <c:if test="${cal.id==tutionFee.familyChildId}">
                                  <form:option value="${cal.key}">${cal.value}</form:option>
                                  </c:if>
                                  </c:forEach>
                             </form:select></spring:bind>
				          </div>
				          <div class="line" id="SA${tutionFee.familyChildId}" style="display: none;">
								 <div class="quarter bold">Period Of Claim<span style="color:red">*</span></div>
						     <spring:bind path="tutionFee"><form:select path="tutionFee.limitId" cssClass="fromSelect" style="width:25%" >
                                  <form:option value="select">Select</form:option>
                                 <c:forEach var="sal" items="${tutionFee.stateAnnualList}">
                                  <c:if test="${sal.id==tutionFee.familyChildId}">
                                  <form:option value="${sal.key}">${sal.value}</form:option>
                                  </c:if>
                                  </c:forEach>
                             </form:select></spring:bind>
				          </div>
				          
							
								   		
	

<script>
tutionFeeBean= <%= (net.sf.json.JSONObject) session.getAttribute("tutionFeeBean") %>;
</script>							
		
	

<!-- End:tutionFeeRequestDetailsList.jsp -->