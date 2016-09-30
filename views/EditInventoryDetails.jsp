	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:EditInventoryDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>Inventory Holder Master</title>
</head>
<body>
	<div class="line">
			
			<div class="line">
			<div class="quarter" style="margin-left:8px;">Inventory Number</div>
			<div class="quarter">
				<spring:bind path="mmgMaster">
				<form:label path="mmgMaster.inventoryNo" id="inventoryNo">&nbsp;${mmgMaster.inventoryNo}</form:label>
				</spring:bind>
			</div>
			<div class="quarter" style="margin-left:8px;">SFID<span class='failure'>*</span></div>
			<div class="quarter">
				<spring:bind path="mmgMaster">
				<div id="sfidCode"><form:input path="mmgMaster.sfid" id="sfid"/></div>
				</spring:bind>
			</div>
			</div>
				<div id="invHolderDetails">	
				<div class="line">
				<div class="quarter" style="margin-left:8px;">Directorate</div>
					<div class="quarter">
						<spring:bind path="mmgMaster">
					 	<form:label path="mmgMaster.directorateName" id="directorateName">&nbsp;${mmgMaster.directorateName}</form:label>
						</spring:bind>
					</div>
					<div class="quarter" style="margin-left:8px;">Division</div>
						<div class="quarter">
						<spring:bind path="mmgMaster">
							<form:label path="mmgMaster.divisionName" id="divisionName">&nbsp;${mmgMaster.divisionName}</form:label>
						</spring:bind>
						</div>
					</div>
					<div class="line">
						<div class="quarter" style="margin-left:8px;">Name</div>
						<div class="quarter">
						<spring:bind path="mmgMaster">
							<form:label path="mmgMaster.holderName" id="holderName">&nbsp;${mmgMaster.holderName}</form:label>
						</spring:bind>
						</div>
						<div class="quarter" style="margin-left:8px;">Designation</div>
						<div class="quarter">
						<spring:bind path="mmgMaster">
							<form:label path="mmgMaster.designation" id="designation">&nbsp;${mmgMaster.designation}</form:label>
						</spring:bind>
						</div>
						</div>
						<div class="line">
							<div class="quarter" style="margin-left:8px;">Phone</div>
								<div class="quarter">
								<spring:bind path="mmgMaster">
									<form:label path="mmgMaster.phone" id="phone">&nbsp;${mmgMaster.phone}</form:label>
								</spring:bind>
								</div>
							</div>
				</div>
							<!-- <div class="line">
								<div class="quarter" style="margin-left:8px;">Demand Purchase Limit</div>
								<div class="quarter">
								<spring:bind path="mmgMaster">
									<form:input path="mmgMaster.demandPurchaseLimit" id="demandPurchaseLimit" maxlength="10" />
								</spring:bind>
								</div>
								
							</div>  -->
							<div class="line">
										
								<div class="quarter" style="margin-left:8px;">Inventory For<span class='failure'>*</span></div>
								<div class="quarter">
								<spring:bind path="mmgMaster">
									<form:radiobutton path="mmgMaster.inventoryHolderType" id="inventoryHolderType1" onclick="javascript:inventoryFor();" value="30" />Buildup
								</spring:bind>
								</div>
								<div class="quarter">
								<spring:bind path="mmgMaster">
									<form:radiobutton path="mmgMaster.inventoryHolderType" id="inventoryHolderType2" onclick="javascript:inventoryFor();"  value="31"/>Project
								</spring:bind>
								</div>
								<div id="project">
									<div class="quarter" style="margin-left:8px;">
									<spring:bind path="mmgMaster">
										<form:select path="mmgMaster.projectName" id="projectName" cssStyle="width:145px;">
										<form:option value="">Select</form:option>
											
										</form:select>
									</spring:bind>
									</div>
								</div>
							</div>
									
							<div class="line">
								<div style="margin-left:75%;"><a id="createFundType" href="javascript:inventoryHolderRequest('','');"><div class="appbutton">Submit</div></a></div>
								<div style="margin-left:65%;"><a id="createFundType" href="javascript:clearMMGMaster('${sessionScope.masterType}');"><div class="appbutton">Clear</div></a></div>  
							</div>
					
					</div>
				
		<script>
			inventoryFor();
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
<!-- End:EditInventoryDetails.jsp -->