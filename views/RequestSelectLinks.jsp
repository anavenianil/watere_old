<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--Begin: RequestSelectLinks.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	
		                            	<div class="line">
											<div class="leftmar">
												<div style="float: left; width: 38%;">Request Types</div>
												<div class="half" >Selected Request Types</div>
											</div>
									   </div>
									<div class="line">
											<div class="leftmar">
												<div style="float: left; width : 28%">
													<spring:bind path="menu">
													<form:select path="menu.fromID" id="SelectLeft" size="20" multiple="true" cssStyle="width:250px">
													<form:options items="${sessionScope.DeSelectedRequestList}" itemLabel="name" itemValue="id"/>
													</form:select>
													</spring:bind>
												</div>
												<div style="float: left; width : 10%; margin-top: 60px;">									
														<div style="margin-bottom: 5px;" align="center">
													          <input style="margin-bottom: 5px"id="MoveRight" type="button" value=" Add " />
      											              <input id="MoveLeft" type="button" value=" Remove " />    											        
      											        </div>		      																				
		      									</div>
												<div style="float: left; width : 35%">
													<spring:bind path="menu">
													<form:select path="menu.fromID" id="SelectRight" size="20" multiple="true" cssStyle="width:250px">
													<form:options items="${sessionScope.SelectedRolesList}" itemLabel="name" itemValue="id"/>
													</form:select>
													</spring:bind>
												</div>
											</div>
									</div>
									
				
	<script>multiSelectBox();</script>	


<!--End: RequestSelectLinks.jsp -->