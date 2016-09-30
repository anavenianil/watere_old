<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:InventoryHolderMaster.jsp -->
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
<script type="text/javascript" src="script/jquery.autoSuggest.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script language="javascript" src="script/json2_mini.js"></script>
<script type="text/javascript" src="script/mmgscript.js"></script>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/autoSuggest.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript">
	$jq(document).ready(function(){
		clearMMGMaster('${sessionScope.masterType}');
		
   	});
   	
</script>
<title>Inventory Holder Master</title>
</head>

<body>
	<form:form method="post" commandName="mmgMaster" name="mmgMaster" id="mmgMaster" action="">
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
								<div class="headTitle" id="headTitle">Create Inventory Holder</div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</img></div>
								<%-- Content Page starts --%>
								<div>
									
									<div class="line">
									<fieldset><legend><strong><font color='green'>Inventory Holder Details</font></strong></legend>
									<div class="line">
										<div class="quarter" style="margin-left:8px;">Inventory Number</div>
										<div class="quarter">
											<form:label path="inventoryNo" id="inventoryNo" cssStyle="cursor:auto"></form:label>
										</div>
										<div class="quarter" style="margin-left:8px;">SFID<span class='failure'>*</span></div>
										<div class="quarter">
										<!-- <form:select path="sfid" id="sfid" cssStyle="width:145px;" onchange="javascript:getSfidDetails();">
												<form:option value="">Select</form:option>
												<c:forEach items="${parentList}" var="sfidsList">
													<form:option value="${sfidsList.sfid}">${sfidsList.sfid}</form:option>
												</c:forEach>
												
											</form:select> -->
											<div id="sfidCode"><form:input path="sfid" id="sfid"/></div>
										</div>
										
									</div>
								<div id="invHolderDetails">	
									<div class="line">
										<div class="quarter" style="margin-left:8px;">Directorate</div>
										<div class="quarter">
										 	<form:label path="directorateName" id="directorateName" cssStyle="cursor:auto">${mmgMaster.directorateName}</form:label>
										</div>
										<div class="quarter" style="margin-left:8px;">Division</div>
										<div class="quarter">
											<form:label path="divisionName" id="divisionName" cssStyle="cursor:auto">${mmgMaster.divisionName}</form:label>
										</div>
									</div>
									<div class="line">
										<div class="quarter" style="margin-left:8px;">Name</div>
										<div class="quarter">
											<form:label path="holderName" id="holderName" cssStyle="cursor:auto">${mmgMaster.holderName}</form:label>
										</div>
										<div class="quarter" style="margin-left:8px;">Designation</div>
										<div class="quarter">
											<form:label path="designation" id="designation" cssStyle="cursor:auto">${mmgMaster.designation}</form:label>
										</div>
									</div>
									<div class="line">
										<div class="quarter" style="margin-left:8px;">Phone</div>
										<div class="quarter">
											<form:label path="phone" id="phone" cssStyle="cursor:auto">${mmgMaster.phone}</form:label>
										</div>
									</div>
								</div>
									<!--  <div class="line">
										<div class="quarter" style="margin-left:8px;">Demand Purchase Limit (<b><del>&#2352;</del></b>)</div>
										<div class="quarter">
											<form:input path="demandPurchaseLimit" id="demandPurchaseLimit" maxlength="10" />
										</div>
										
									</div> -->
									<div class="line">
										
										<div class="quarter" style="margin-left:8px;">Inventory For<span class='failure'>*</span></div>
										<div class="quarter">
											<form:radiobutton path="inventoryHolderType" id="inventoryHolderType1" onclick="inventoryFor();" value="30"/>Buildup
										</div>
										<div class="quarter">
											<form:radiobutton path="inventoryHolderType" id="inventoryHolderType2" onclick="inventoryFor();"  value="31"/>Project
										</div>
										<div id="project">
										<div class="quarter" style="margin-left:8px;">
											<form:select path="projectName" id="projectName" cssStyle="width:145px;">
												<form:option value="">Select</form:option>
												<form:options items="${sessionScope.projectList}" itemLabel="projectName" itemValue="id"></form:options>												
											</form:select>
										</div>
										</div>
									</div>
									
									<div class="line">
										<div style="margin-left:75%;"><a id="createFundType" href="javascript:manageMMGMaster('${sessionScope.masterType}');" class="quarterbutton appbutton"><div class="appbutton">Submit</div></a></div>
										<div style="margin-left:65%;"><a id="createFundType" href="javascript:clearMMGMaster('${sessionScope.masterType}');" class="quarterbutton appbutton"><div class="appbutton">Clear</div></a></div>  
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
		<form:hidden path="changedValues" id="changedValues"/>
		</form:form>
		<script type="text/javascript">
			autoX='Please Enter SFID';
			var invNum='${sessionScope.inventoryNo}';
			var sfidjson = <%= (net.sf.json.JSONArray)session.getAttribute("sfidjson") %>;
		
			$jq(function(){		
			 autosugg=setSfidValues(sfidjson);
			 $jq("#sfidCode").html('<input type="text" name="sfid" id="sfidone"/>');
			 $jq("#sfidCode input").autoSuggest(autosugg.items, {selectedItemProp: "name", searchObjProps: "name",selectionLimit :1,resultsHighlight:false,selectedValuesProp:"value",resultClick:function(){getSfidDetails();}});
			 
			});	
		</script>
		
	</body>
</html>
<!-- End:InventoryHolderMaster.jsp -->