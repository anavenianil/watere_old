<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TrainingInistituteMaster.jsp -->
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
		clearVenueMaster();
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
								<div class="headTitle" id="headTitle">Venue and Payment details</div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</div>
								<%-- Content Page starts --%>
								<div>
									<div class="line">
											
												<div class="quarter " style="margin-left:8px;">Training Type<span class='failure'>*</span></div>
												<div class="quarter bold">
													${trainingMaster.trainingType}
												</div>
												<div class="quarter " style="margin-left:8px;">Region Name<span class='failure'>*</span></div>
												<div class="quarter bold">
													${trainingMaster.trainingRegion}
												</div>
											
										</div>
										<div class="line">
											
												<div class="quarter " style="margin-left:8px;">Training Institute Name<span class='failure'>*</span></div>
												<div class="quarter bold">
													${trainingMaster.trainingInistitute}
												</div>
										
											
										</div>
										<div class="line">
												<div class="quarter bold" style="margin-left:8px;">City<span class='failure'>*</span></div>
													<div class="quarter">
																<form:select path="cityId" id="cityId" cssStyle="width:145px;">
																	<form:option value="">Select</form:option>
																	<form:options items="${CityTypeMasterList}" itemValue="id" itemLabel="name"/>
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
													<form:input path="phone" id="phone" maxlength="100" onkeypress="javascript:return checkNumSymbolsExp(event);"/>
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
											
										</div>
										<div class="line">
												<div class="quarter bold" style="margin-left:8px;">Payable At</div>
												<div class="quarter">
													<form:select path="payableAt" id="payableAt" cssStyle="width:145px;">
																	<form:option value="">Select</form:option>
																	<form:options items="${CityTypeMasterList}" itemValue="id" itemLabel="name"/>
																</form:select>
												</div>
											
												
												
											
										</div>
									
									
									<div class="line">
											<div style="margin-left:27%;margin-right:5px;float:left;"><a id="createFundType" href="javascript:trainingVenueMasterSubmit('${sessionScope.trainingMstType}',jsonMasterDataList);"><div class="appbutton">Submit</div></a></div>
											<div><a id="createFundType" href="javascript:clearVenueMaster('${sessionScope.trainingMstType}');"><div class="appbutton">Clear</div></a></div>  
											  
											<div class="expbutton"><a href="javascript:goBackToTrainingInistitute('${sessionScope.trainingMstType}');"><span>Go To Institute</span></a></div>					
									</div>
									<div class="line height"><hr/></div>
									<div id="result" class="line">
										<jsp:include page="TrainingMasterDataList.jsp" />
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
		<form:hidden path="type" id="type" />
		<form:hidden path="id" id="id"/>
		<form:hidden path="name" id="name" value=""/>
		<form:hidden path="trainingInistituteId" id="trainingInistituteId"/>
		<form:hidden path="trainingTypeId" id="trainingTypeId"/>
		<form:hidden path="trainingRegionId" id="trainingRegionId"/>
		<form:hidden path="ddFlag" id="ddFlag"/>
		<form:hidden path="headOfficeFlag" id="headOfficeFlag"/>
		<form:hidden path="back" id="back"/>
		 
		
		</form:form>
		<script type="text/javascript">
			var type='<c:out value='${sessionScope.trainingMstType}'/>';
			jsonMasterDataList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonTrainingMstDataList") %>;
			
		</script>
		
		
		
		

	</body>
</html>
<!-- End:TrainingInistituteMaster.jsp -->