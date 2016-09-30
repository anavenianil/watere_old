<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:MMGMaterialMaster.jsp -->
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
<script type="text/javascript" src="script/mmgscript.js"></script>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript">
	$jq(document).ready(function(){	    
		clearMMGMaster();
		$jq("#mmgMaster").validate({
    	 rules: { 
	         	categoryCode: "required",
	         subCategoryCode: "required",
	         		itemCode: "required",
	         	materialName: "required",
	        			 uom: "required",
	          consumableFlag: "required"
	        		
	   },
	  messages: {
		categoryCode: "Please select categoryCode.",
	 subCategoryCode: "Please select subCategoryCode.",
			itemCode: "Please select itemCode.",
		materialName: "Please select materialName.",
				 uom: "Please Select UOM.",
	  consumableFlag: "Please Select Consumable Flag."
	  		
		},
    });
   	});
</script>
<title>Material Master</title>
</head>

<body>
	<form:form method="post" commandName="mmgMaster" name="mmgMaster" id="mmgMaster" action="" >
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
								<div class="headTitle" id="headTitle">Create Material</div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</img></div>
								<%-- Content Page starts --%>
								<div>
									
									<div class="line">
									<fieldset><legend><strong><font color='green'>Material Details</font></strong></legend>
									<div class="line">
										<div class="quarter" style="margin-left:8px;">Item Category<span class='failure'>*</span></div>
										<div class="quarter">
											<form:select path="categoryCode" id="categoryCode" cssStyle="width:145px;" onchange="javascript:getMaterialCode(this.id);">
												<form:option value="" disabled="disabled">Select</form:option>
												<c:forEach items="${parentList}" var="pList">
													<form:option value="${pList.id}#${pList.categoryCode}">${pList.name}</form:option>
												</c:forEach>
												
											</form:select>
										</div>
										<div class="quarter" style="margin-left:8px;">Item Sub Category<span class='failure'>*</span></div>
										<div class="quarter">
											<form:select path="subCategoryCode" id="subCategoryCode" cssStyle="width:145px;" onchange="javascript:getMaterialCode(this.id);">
												<form:option value="" >Select</form:option>
												<c:forEach items="${itemParentList}" var="ipList">
													<form:option value="${ipList.id}#${ipList.subCategoryCode}">${ipList.name}</form:option>
												</c:forEach> 
												
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter" style="margin-left:8px;">Item Code<span class='failure'>*</span></div>
										<div class="quarter">
											<form:select path="itemCode" id="itemCode" cssStyle="width:145px;" onchange="javascript:getMaterialCode(this.id);">
												<form:option value="">Select</form:option>
											 	<c:forEach items="${itemCodeList}" var="icList">
													<form:option value="${icList.id}#${icList.itemCode}">${icList.name}</form:option>
												</c:forEach> 
											</form:select>
										</div>
										<div class="quarter" style="margin-left:8px;">Item Sub Code</div>
										<div class="quarter">
											<form:select path="itemSubCode" id="itemSubCode" cssStyle="width:145px;" onchange="javascript:getItemSubCode();">
												<form:option value="Select">Select</form:option>
											<%--   	<c:forEach items="${itemSubCodeList}" var="iscList">
													<form:option value="${iscList.id}#${iscList.itemSubCode}">${iscList.name}</form:option>
												</c:forEach>  --%>
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter" style="margin-left:8px;">Company Name</div>
										<div class="quarter">
											<form:select path="companyCode" id="companyCode" cssStyle="width:145px;" onchange="javascript:getCompanyCode();">
												<form:option value="">Select</form:option>
												<c:forEach items="${itemCompanyList}" var="icomList">
													<form:option value="${icomList.id}#${icomList.companyCode}">${icomList.name}</form:option>
												</c:forEach>
											</form:select>
										</div>
										<div class="quarter" style="margin-left:8px;">Material Code</div>
										<div class="quarter">
											<form:label path="materialCode" id="materialCode"></form:label>
										</div>
									</div>
									<div class="line">
									<div class="quarter" style="margin-left:8px;">Material Name<span class='failure'>*</span></div>
										<div class="quarter">
											<form:input path="materialName" id="materialName" maxlength="100"></form:input>
										</div>
									</div>
									<div class="line">
										<div class="quarter" style="margin-left:8px;">Rate Contract Flag</div>
										<div class="quarter">
											<form:select path="rcFlag" id="rcFlag" cssStyle="width:145px;" onchange="javascript:enableContractRate();">
												<form:option value="0">Select</form:option>
												<form:option value="1">Yes</form:option>
												<form:option value="2">No</form:option>
											</form:select>
										</div>
										<div id="contract" style="display:none">
											<div class="quarter" style="margin-left:8px;">Contract Rate (<b><del>&#2352;</del></b>)</div>
											<div class="quarter">
												<form:input path="unitRate" id="unitRate" maxlength="10" onkeypress="return checkTwoDigitFloat(event,this.id);"></form:input>
											</div>
										</div>
									</div>
									<div class="line">
										<div class="quarter" style="margin-left:8px;">UOM<span class='failure'>*</span></div>
										<div class="quarter">
											<form:select path="uom" id="uom" cssStyle="width:145px;">
												<form:option value="">Select</form:option>
												<form:options items="${uomList}" itemValue="id" itemLabel="name"></form:options>
												
											</form:select>
										</div>
										<div class="quarter" style="margin-left:8px;">Consumable Flag<span class='failure'>*</span></div>
										<div class="quarter">
											<form:select path="consumableFlag" id="consumableFlag" cssStyle="width:145px;">
												<form:option value="">Select</form:option>
												<form:option value="C">Consumable</form:option>
												<form:option value="NC">Non Consumable</form:option>
											</form:select>
										</div>
										
									</div>
									<div class="line">
										<div class="quarter" style="margin-left:8px;">Description</div>
										<div class="quarter"><form:textarea path="description" id="description" cols="20" rows="3" onkeypress="textCounter(event,$jq('#description'),$jq('#counter'),500);"
										 onkeyup="textCounter(event,$jq('#description'),$jq('#counter'),500);"></form:textarea>
											<input type="text" class="counter" name="counter" value="500" id="counter"/>
										</div>
									</div>
									<div class="line">
										<div style="margin-left:25%;"><a id="createFundType" href="javascript:manageMMGMaster('${sessionScope.masterType}');"><div class="appbutton">Submit</div></a></div>
										<div style="margin-left:25%;"><a id="createFundType" href="javascript:clearMMGMaster();"><div class="appbutton">Clear</div></a></div>  
									</div>
									</fieldset>
									</div>
									<div class="line">
										<div id="result" class="line">
											<jsp:include page="MMGMasterDataList.jsp" />
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
		
		</form:form>
		<script>
	JsonParentList = <%= (net.sf.json.JSONArray)session.getAttribute("JsonParentList") %>;
	JsonItemCodeList = <%= (net.sf.json.JSONArray)session.getAttribute("JsonItemCodeList") %>;
    JsonSubCodeList = <%= (net.sf.json.JSONArray)session.getAttribute("JsonSubCodeList") %>;
	
	</script>
	</body>
</html>
<!-- End:MMGMaterialMaster.jsp -->