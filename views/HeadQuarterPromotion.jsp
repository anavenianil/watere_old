<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:HeadQuarterPromotion.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="script/calendar-hd.js"></script>
<script type="text/javascript" src="script/calendar_us.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/promotions.js"></script>

<title>Eligible Candidates for Assessment</title>
</head>

<body onload="javascript:resetHQPromotion();">
	<form:form method="post" commandName="promotion" id="PromotionManagementBean" name="headQuarter">
		<div>
			<div class="Innermaindiv">
				<div class="header"></div>
				<div><jsp:include page="MenuLinks.jsp" /></div>					
				<div class="mainDisplay">
					<div class="whitebox1">
						<div class="whitebox_t">
							<div class="whitebox_tl">
								<div class="whitebox_tr"></div>
							</div>
						</div>
						<div class="whitebox_m1">
							<div class="lightblueBg1">
								<div class="headTitle">Eligible Candidates List for ASL</div>
								<%-- Content Page starts --%>
								<div class="line">
									<div class="line">
										<div class="quarter leftmar">Category Type<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="assessmentTypeID" id="assessmentTypeID" onchange="getCategoryDesignationList()" cssClass="formSelect" >
												<form:option value="0">Select</form:option>
												<form:options items="${promotion.assessmentTypeList}" itemValue="id"  itemLabel="name"/>
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Board Type<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="assessmentCategoryID" id="assessmentCategoryID"  cssClass="formSelect" >
												<form:option value="0">Select</form:option>
												<form:options items="${promotion.assessmentCategoryList}" itemValue="id" itemLabel="category"/>
											</form:select>
										</div>
									</div>
									    <div class="line">
	                                      <div class="quarter leftmar">As on<span class="mandatory">*</span></div>
	                                     <div class="quarter"><form:input path="assessmentDate" id="assessmentDate" cssClass="dateClass" readonly="true" />&nbsp 
		                               
		                                <script type="text/javascript">
		                                 
											new tcal({'formname':'headQuarter','controlname':'assessmentDate'});
	                                    </script>
	                                    </div>
	                                    </div>
									
									<div class="line">
										<div class="quarter leftmar">Year<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="yearID" id="yearID"  cssClass="formSelect" >
												<form:option value="0">Select</form:option>
												<form:options items="${promotion.yearList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
									</div>	
	                                    
									<div class="line">
										<div class="quarter leftmar">Designation</div>
										<div class="quarter">
											<form:select path="designationFrom" id="designationFrom" onmouseover="setSelectWidth('#designationFrom')" cssClass="formSelect" >
												<form:option value="0">Select</form:option>
												<form:options itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
									</div>
																			
									<div class="line" style="margin-left: 12%;">
										<div class="expbutton"><a onclick="javascript:searchHQCandidates()"> <span>Search</span></a></div>
										<div class="expbutton"><a onclick="javascript:submitHQCandidates()"> <span>Submit</span></a></div>
										<div class="expbutton"><a onclick="javascript:resetQualifiedCandidates()"> <span>Clear</span></a></div>
										<div class="expbutton"><a onclick="javascript:getPromotionReport('aslEligible')"> <span>Eligible List-ASL</span></a></div>
										<div class="expbutton"><a onclick="javascript:getPromotionReport('sendToHQ')"> <span>Eligible List-HQ</span></a></div>
										<div class="expbutton"><a onclick="javascript:getPromotionIONReport('eligibleION')"> <span>Generate ION</span></a></div>
									</div>
									<div class="line"><hr /></div>
									<div class="line" id="result">
										
									</div>
								</div>
								<%-- Content Page end --%>								
								<div>&nbsp;</div>
							</div>
						</div>
						<div class="whitebox_b_non">
							<div class="whitebox_bl">
								<div class="whitebox_br"></div>
							</div>
						</div>
					</div>
				</div>				
			</div>
			<div><jsp:include page="Footer.jsp" /></div>			
		</div>
		<form:hidden path="type"/>
		<form:hidden path="param"/>	
		</form:form>
		<script>
			designationListJSON= <%= (net.sf.json.JSONArray)session.getAttribute("designationListJSON") %>;
			yearID = ${promotion.yearID} 
		</script>		
	</body>
</html>
<!-- End:HeadQuarterPromotion.jsp -->