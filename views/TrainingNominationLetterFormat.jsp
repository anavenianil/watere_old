<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--Begin: TrainingNominationLetterFormat.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>





		
		
												    <c:if test="${trainingRequest.feeFlag != null && trainingRequest.feeFlag == 1}">
													<div class="quarter bold leftmar">Serial Number<span class="failure">*</span></div>
													<div class="quarter">
															<form:select path="trainingRequest.letterFormatId3" id="letterFormatId3" cssClass="formSelect" onchange="javascript:getCirculationIonLists('3');" onmouseover="setSelectWidth('#letterFormatId3');">
															<form:option value="">Select</form:option>
															<form:options items="${seriesMstList3}" itemValue="id" itemLabel="serialNum"/>
												
													</form:select>
													</div>
													<div class="quarter bold leftmar">Letter Number<span class="failure">*</span></div>
													<div class="quarter" >
														<form:select path="trainingRequest.ionId3" id="ionId3" cssClass="formSelect" onmouseover="setSelectWidth('#ionId3');">
														<form:option value="">Select</form:option>
														
												
														</form:select>
													</div>
													</c:if>
									
												
		

<!--End: TrainingNominationION.jsp -->