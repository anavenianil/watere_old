<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin: payBillEmpDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<div class="line">
<jsp:include page="Result.jsp"></jsp:include>
</div>
                     <div class="line">
										<div class="quarter leftmar">SFID<span class="mandatory">*</span></div>
										<div class="quarter"><spring:bind path="payBillMaster">
										<form:select path="payBillMaster.sfidSearchKey"  id="sfidSearchKey"  cssClass="formSelect" onchange="javascript:getEmpCoreDetails();">
										<form:option value="select" label="Select"></form:option>
										<form:options items="${sessionScope.empList}" itemLabel="name" itemValue="name"/>
										</form:select>
												</spring:bind> </div>
												<div class="quarter" >
										</div>
					</div>
								<%-- Content Page end --%>
		<spring:bind path="payBillMaster"><form:hidden path="payBillMaster.param" id="param"/></spring:bind>
		<spring:bind path="payBillMaster"><form:hidden path="payBillMaster.type"/></spring:bind>		

<!-- End: payBillEmpDetails.jsp -->	