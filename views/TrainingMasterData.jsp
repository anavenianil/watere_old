<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TrainingMasterData.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/trainingscript.js"></script>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript">
	$jq(document).ready(function(){
	
		lables(type);
		clearTrainingMaster(type);
		});
</script>
<style>
.highlight {
font-style: italic;
background-color: #0f0;
}
</style>
</head>

<body>
	<form:form method="post" commandName="trainingMaster" name="trainingMaster" id="trainingMaster">
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
								<div class="headTitle" id="headTitle"></div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</img></div>
								<%-- Content Page starts --%>
								<div>
									
									<div class="line">
									
									<c:if test="${trainingMstType eq 'trainingInistitute'}">
										<div class="line">
											
												<div class="quarter bold" style="margin-left:8px;">Training Type<span class='failure'>*</span></div>
												<div class="quarter">
													<form:select path="trainingTypeId" id="trainingTypeId" cssStyle="width:145px;" onchange="javascript:getTrainingRegionList(jsonTrainingRegionList);">
														<form:option value="">Select</form:option>
														<form:options items="${trainingTypeList}" itemValue="id" itemLabel="name"/>
													</form:select>
												</div>
												<div class="quarter bold" style="margin-left:8px;">Category<span class='failure'>*</span></div>
												<div class="quarter">
													<form:select path="categoryId" id="categoryId" cssStyle="width:145px;" onchange="javascript:getTrainingInistitute(jsonTrainingInistituteList);">
														<form:option value="">Select</form:option>
														<form:options items="${CategoryList}" itemValue="id" itemLabel="name"/>
													</form:select>
												</div>
											
										</div>
									</c:if>
									<c:if test="${trainingMstType eq 'trainingVenue'}">
									
										<div class="line">
											
												<div class="quarter bold" style="margin-left:8px;">Training Type<span class='failure'>*</span></div>
												<div class="quarter">
													<form:select path="trainingTypeId" id="trainingTypeId" cssStyle="width:145px;" onchange="javascript:getTrainingInistitute(jsonTrainingInistituteList);">
														<form:option value="">Select</form:option>
														<form:options items="${trainingTypeList}" itemValue="id" itemLabel="name"/>
													</form:select>
												</div>
												<div class="quarter bold" style="margin-left:8px;">Category<span class='failure'>*</span></div>
												<div class="quarter">
													<form:select path="categoryId" id="categoryId" cssStyle="width:145px;" onchange="javascript:getTrainingInistitute(jsonTrainingInistituteList);">
														<form:option value="">Select</form:option>
														<form:options items="${CategoryList}" itemValue="id" itemLabel="name"/>
													</form:select>
												</div>
											
										</div>
									</c:if>
									<c:if test="${trainingMstType eq 'trainingInistitute'}">
										<div class="line">
											
												<div class="quarter bold" style="margin-left:8px;">Region Name<span class='failure'>*</span></div>
												<div class="quarter">
													<form:select path="trainingRegionId" id="trainingRegionId" cssStyle="width:145px;" onchange="javascript:getTrainingInistitute(jsonTrainingInistituteList);">
														<form:option value="">Select</form:option>
														<form:options items="${trainingRegionList}" itemValue="id" itemLabel="name"/>
													</form:select>
												</div>
												
											
										</div>
									</c:if>
									<c:if test="${trainingMstType eq 'courseSubjCategory' or trainingMstType eq 'courseSubjSubCategory'}">
										<div class="line">
											
												<div class="quarter bold" style="margin-left:8px;">Category<span class='failure'>*</span></div>
												<div class="quarter">
													<form:select path="categoryId" id="categoryId" cssStyle="width:145px;" onchange="javascript:getCourseSubjCategory(jsonCourseSubjCategoryList);">>
														<form:option value="">Select</form:option>
														<form:options items="${CategoryList}" itemValue="id" itemLabel="name"/>
													</form:select>
												</div>
											
										</div>
									</c:if>
									<c:if test="${trainingMstType eq 'courseSubjSubCategory'}">
										<div class="line">
											
												<div class="quarter bold" style="margin-left:8px;">Course Subject Category<span class='failure'>*</span></div>
												<div class="quarter">
													<form:select path="courseSubjCategoryId" id="courseSubjCategoryId" cssStyle="width:145px;">
														<form:option value="">Select</form:option>
														<form:options items="${courseSubjCategoryList}" itemValue="id" itemLabel="name"/>
													</form:select>
												</div>
											
										</div>
									</c:if>
									<c:if test="${trainingMstType eq 'trainingVenue'}">
										<div class="line">
											
												<div class="quarter bold" style="margin-left:8px;">Training Inistitute Name<span class='failure'>*</span></div>
												<div class="quarter">
													<form:select path="trainingInistituteId" id="trainingInistituteId" cssStyle="width:145px;">
														<form:option value="">Select</form:option>
														<form:options items="${trainingInistituteList}" itemValue="id" itemLabel="name"/>
													</form:select>
												</div>
												
												
											
										</div>
									</c:if>
									<c:if test="${trainingMstType eq 'trainingType'}">
									
									<div class="line">
										
										<div class="quarter bold" id="labelType"></div>
										<div class="quarter"><form:input path="typeValue" id="typeValue" maxlength="50" /></div>
									</div>
									</c:if>
									<c:if test="${trainingMstType eq 'trainingInistitute' or trainingMstType eq 'courseSubjCategory' or trainingMstType eq 'courseSubjSubCategory'}">
									<div class="line">
									<div class="quarter bold" style="margin-left:8px;" id="labelType"></div>
												<div class="quarter">
													
													<div class="quarter"><form:textarea path="typeValue" id="typeValue" cols="20" rows="3" onkeypress="textCounter(event,$jq('#name'),$jq('#nameCounter'),200);"
															 onkeyup="textCounter(event,$jq('#name'),$jq('#nameCounter'),200);"></form:textarea>
													<input type="text" class="counter" name="nameCounter" value="200" id="nameCounter" disabled=""/>
												</div>
								   </div>
								   </c:if>
								   <c:if test="${trainingMstType eq 'trainingInistitute'}">
								   <div class="line">
												<div class="quarter bold" style="margin-left:8px;">Short Name</div>
												<div class="quarter">
													<form:input path="shortName" id="shortName" maxlength="50" />
												</div>
							    	</div>	
							    	</c:if>		
									
									
									<c:if test="${trainingMstType eq 'courseSubjCategory' or trainingMstType eq 'courseSubjSubCategory' or trainingMstType eq 'trainingType'}">
									<div class="line">
										<div class="quarter bold" style="margin-left:8px;">Description</div>
										<div class="quarter"><form:textarea path="description" id="description" cols="20" rows="3" onkeypress="textCounter(event,$jq('#description'),$jq('#counter'),500);"
										 onkeyup="textCounter(event,$jq('#description'),$jq('#counter'),500);"></form:textarea>
											<input type="text" class="counter" name="counter" value="500" id="counter" disabled=""/>
										</div>
									</div>
									</c:if>
									<c:if test="${trainingMstType eq 'trainingVenue'}">
										<div class="line">
												<div class="quarter bold" style="margin-left:8px;">City<span class='failure'>*</span></div>
													<div class="quarter">
																<form:select path="cityId" id="cityId" cssStyle="width:145px;">
																	<form:option value="">Select</form:option>
																	<form:options items="${CityTypeMasterList}" itemValue="id" itemLabel="cityName"/>
																</form:select>
													</div>
												<div class="quarter bold" style="margin-left:8px;">Address<span class='failure'>*</span></div>
												<div class="quarter">
													
													<div class="quarter"><form:textarea path="address" id="address" cols="20" rows="3" onkeypress="textCounter(event,$jq('#address'),$jq('#counter'),500);"
															 onkeyup="textCounter(event,$jq('#address'),$jq('#counter'),500);"></form:textarea>
													<input type="text" class="counter" name="counter" value="500" id="counter" disabled=""/>
												</div>
												</div>
												
											
										</div>
										<div class="line">
												<div class="quarter bold" style="margin-left:8px;">Phone<span class='failure'>*</span></div>
												<div class="quarter">
													<form:input path="phone" id="phone" maxlength="100" />
												</div>
												<div class="quarter bold" style="margin-left:8px;">Fax</div>
												<div class="quarter">
													<form:input path="fax" id="fax" maxlength="100" />
												</div>
												
												
											
										</div>
										<div class="line">
												<div class="quarter bold" style="margin-left:8px;">E-mail</div>
												<div class="quarter">
													<form:input path="email" id="email" maxlength="100" />
												</div>
											
												<div class="quarter bold" style="margin-left:8px;">Head Office</div>
												<div class="quarter">
													<input type="checkbox" value="0" class="row" name="headOfficeFlag" id="headOfficeFlag" />
												</div>
												
											
										</div>
										<div class="line">
												<div class="quarter bold" style="margin-left:8px;">Payment Mode</div>
												<div class="quarter">
													<input type="checkbox" value="1" class="row" name="check" id="check" />Check
													<input type="checkbox" value="2" class="row" name="dd" id="dd" />DD
												</div>
											
												<div class="quarter bold" style="margin-left:8px;">DDAddress</div>
												<div class="quarter">
													<div class="quarter"><form:textarea path="ddAddress" id="ddAddress" cols="20" rows="3" onkeypress="textCounter(event,$jq('#ddAddress'),$jq('#ddcounter'),200);"
															 onkeyup="textCounter(event,$jq('#ddAddress'),$jq('#ddcounter'),200);"></form:textarea>
													<input type="text" class="counter" name="ddcounter" value="500" id="ddcounter" disabled=""/>
												</div>
												
											
										</div>
										<div class="line">
												<div class="quarter bold" style="margin-left:8px;">Payable At</div>
												<div class="quarter">
													<form:select path="payableAt" id="payableAt" cssStyle="width:145px;">
																	<form:option value="">Select</form:option>
																	<form:options items="${CityTypeMasterList}" itemValue="id" itemLabel="cityName"/>
																</form:select>
												</div>
											
												
												
											
										</div>
										
									</c:if>
									
									<div class="line">
										<div style="margin-left:27%;margin-right:5px;float:left;"><a id="createFundType" href="javascript:manageTrainingMaster('${sessionScope.trainingMstType}',jsonMasterDataList);"><div class="appbutton">Submit</div></a></div>
										<div><a id="createFundType" href="javascript:clearTrainingMaster('${sessionScope.trainingMstType}');"><div class="appbutton">Clear</div></a></div>  
																	
									</div>
									
									</div>
									<div class="line">
										<div id="result" class="line">
											<jsp:include page="TrainingMasterDataList.jsp" />
										</div>
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
		<form:hidden path="param" id="param"/>
		<form:hidden path="type" id="type"/>
		<form:hidden path="name" id="name"/>
		<form:hidden path="id" id="id"/>
		<form:hidden path="ddFlag" id="ddFlag"/>
		
		</form:form>
		<script type="text/javascript">
			var type='<c:out value='${sessionScope.trainingMstType}'/>';
			jsonTrainingInistituteList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonTrainingInistituteList") %>;
			jsonCourseSubjCategoryList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonCourseSubjCategoryList") %>;
			jsonMasterDataList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonTrainingMstDataList") %>;
			jsonTrainingRegionList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonTrainingRegionList") %>;
			
		</script>
		
		
		
		

	</body>
</html>
<!-- End:TrainingMasterData.jsp -->