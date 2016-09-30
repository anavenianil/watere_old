<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:CustomReport.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link href="./styles/layout.css" rel="stylesheet" type="text/css" />
<link href="./styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="styles/tabs.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/jquery.drag.drop.js"></script>
<script type="text/javascript" src="script/report.js"></script>
<script type="text/javascript" src="script/jquery-ui.min.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<title>Custom Report</title>
<style>
	
.panel {float:left;width:200px;margin:20px;}

.dds_selected {
    background:#ffc;
}
.dds_ghost {
    opacity:0.5;
}
.dds_move {
    background:#cfc;
}
.dds_hover {
    background:#fc9;
    border:3px dashed #c96;
}

.holder {
    border:3px dashed #333;
    background:#fff;
}

	
</style>
<script>
  $jq(document).ready(function() {
    $jq("#tabs").tabs();  
    $jq(function(){
    mychange = function ( $jqlist ){
        $jq( '#'+$jqlist.attr('id')+'_serialised').html( $jq.dds.serialize( $jqlist.attr('id')) );
    }
    $jq('ul').drag_drop_selectable({
        onListChange:mychange
    });
    $jq( '#list_1_serialised').html( $jq.dds.serialize( 'list_1' ) );
    $jq( '#list_2_serialised').html( $jq.dds.serialize( 'list_2' ) );
});	
 }); 
</script>

</head>

<body>
	<form:form method="post" commandName="reports" id="customReport">
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
								<div class="headTitle" id="headTitle">Custom Report</div>
								<div id="result"></div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle"/>&nbsp;Loading...</div>
								<%-- Content Page starts --%>	
									<div id="tabs">
										    <ul>
										        <li><a href="#fragment-1"><span>Query1</span></a></li>
										        <li><a href="#fragment-2"><span>Query2</span></a></li>
										        <li><a href="#fragment-3"><span>Fields</span></a></li>
										    </ul>
										    <div id="fragment-1">
										    <table width="100%">
										    	<tr>
										    		<td width="5%">
										    			<div class="quarter"  style="text-align:left">
										    				<input type="checkbox" name="sfidcheck" id="sfidcheck0" value="sfidcheck"/>
										    			</div>
										    		</td>
										       		<td width="10%">
										       			<div class="quarter" id="field0">SFID</div>
										       		</td>												
													<td width="20%">
														<div class="quarter" id="searchWithdiv">
														    <form:select path="searchWith"  id="searchWith0" cssClass="formSelect">	
																<form:option value="contains">Contains</form:option>
																<form:option value="starts">Starts With</form:option>
																<form:option value="equal">Equal to</form:option>
															</form:select>
														</div>
													</td>
													<td width="30%">
														<div class="quarter" id="searchWithdiv">
															<form:input path="sfid" id="sfid" maxlength="50" cssClass="formSelect"/>
														</div>
													</td>
													<td width="35%">
														<div class="quarter"><input type="radio" name="sfidOrder" value="asc"/>Asc</div>
														<div class="quarter"><input type="radio" name="sfidOrder" value="desc"/>Dsc</div>
														<div class="quarter"><input type="radio" name="sfidOrder" value="none" checked="checked"/>None</div>
													</td>
												</tr>
												<tr>
										    		<td width="5%">
										    			<div class="quarter" style="text-align:left">
										    				<input type="checkbox" name="sfidcheck" id="sfidcheck1" value="namecheck"/>
										    			</div>
										    		</td>
										       		<td width="10%" nowrap>
										       			<div class="quarter" id="field1">Name</div>
										       		</td>												
													<td width="20%">
														<div class="quarter" id="searchWithdiv">
														    <form:select path="searchWith"  id="searchWith1" cssClass="formSelect">	
																<form:option value="contains">Contains</form:option>
																<form:option value="starts">Starts With</form:option>
																<form:option value="equal">Equal to</form:option>
															</form:select>
														</div>
													</td>
													<td width="30%">
														<div class="quarter" id="searchWithdiv">
														<form:input path="name" id="name" maxlength="50" cssClass="formSelect"/>
													</div></td>
													<td width="35%">
														<div class="quarter"><input type="radio" name="nameOrder" value="asc"/>Asc</div>
														<div class="quarter"><input type="radio" name="nameOrder" value="desc"/>Dsc</div>
														<div class="quarter"><input type="radio" name="nameOrder" value="none" checked="checked"/>None</div>
													</td>
										    	</tr>
										    	<tr>
										    		<td width="5%">
										    			<div class="quarter"  style="text-align:left">
										    				<input type="checkbox" name="sfidcheck" id="sfidcheck2" value="desigcheck"/>
										    			</div>
										    		</td>
										       		<td width="10%" nowrap>
										       			<div class="quarter" id="field2">Designation</div>
										       		</td>												
													<td width="20%">
														<div class="quarter" id="searchWithdiv">
														    <form:select path="searchWith"  id="searchWith2" cssClass="formSelect">	
																<form:option value="equal">Equal to</form:option>
															</form:select>
														</div>
													</td>
													<td width="30%">
														<div class="quarter" id="searchWithdiv">
															<form:select path="designation" id="designation" cssClass="formSelect" >
																<form:option value="Select">Select</form:option>
               	      											<form:options items="${reports.designationList}" itemValue="id" itemLabel="name"/>
               	   											</form:select>
														</div>
													</td>
													<td width="35%">
														<div class="quarter"><input type="radio" name="desigOrder" value="asc"/>Asc</div>
														<div class="quarter"><input type="radio" name="desigOrder" value="desc"/>Dsc</div>
														<div class="quarter"><input type="radio" name="desigOrder" value="none" checked="checked"/>None</div>
													</td>
										    	</tr>
										    	<tr>
										    		<td width="5%">
										    			<div class="quarter"  style="text-align:left">
										    				<input type="checkbox" name="sfidcheck" id="sfidcheck3" value="dobcheck"/>
										    			</div>
										    		</td>
										       		<td width="10%" nowrap>
										       			<div class="quarter" id="field3">DOB</div>
										       		</td>												
													<td width="20%">
														<div class="quarter" id="searchWithdiv">
													    	<form:select path="searchWith"  id="searchWith3" cssClass="formSelect" onchange="javascript:displayFromToFields('dob');">	
																<form:option value="equal">Equal</form:option>
																<form:option value="between">Between</form:option>
															</form:select>
														</div>
													</td>
													<td height="50%">
														<div class="quarter" id="dobdiv" style="width:65%;">
															<form:input path="dob" id="dob" cssClass="dateClass" readonly="true" onclick="javascript:clearDateText('dob');"/>
																<img  src="./images/calendar.gif"   id="date_start_trigger1" />	
																<script type="text/javascript">
																	Calendar.setup({inputField :"dob",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
																</script>
														</div>
														<div class="displayvalues" id="dobfromdiv" style="width:65%;">
															<form:input path="dob" id="dobfrom" cssClass="dateClass" readonly="true" onclick="javascript:clearDateText('dobfrom');"/>
															<img  src="./images/calendar.gif"   id="date_start_trigger2" />	
																<script type="text/javascript">
																	Calendar.setup({inputField :"dobfrom",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger2",singleClick : true,step : 1});
																</script>
														</div>
														<div  class="displayvalues" id="doband"><span>And</span></div>  
														<div class="displayvalues" id="dobtodiv" style="width:65%;">
															<form:input path="dob" id="dobto" cssClass="dateClass" readonly="true" onclick="javascript:clearDateText('dobto');"/>
															<img  src="./images/calendar.gif"   id="date_start_trigger3" />	
																<script type="text/javascript">
																	Calendar.setup({inputField :"dobto",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger3",singleClick : true,step : 1});
																</script>
														</div>
													</td>
													<td width="35%">
														<div class="quarter"><input type="radio" name="dobOrder" value="asc"/>Asc</div>
														<div class="quarter"><input type="radio" name="dobOrder" value="desc"/>Dsc</div>
														<div class="quarter"><input type="radio" name="dobOrder" value="none" checked="checked"/>None</div>
													</td>
										    	</tr>
										    	<tr>
										    		<td width="5%">
										    			<div class="quarter"  style="text-align:left">
										    				<input type="checkbox" name="sfidcheck" id="sfidcheck4" value="dojaslcheck"/>
										    			</div>
										    		</td>
										       		<td width="10%" nowrap>
										       			<div class="quarter" id="field4">DOJ in ASL</div>
										       		</td>												
													<td width="20%">
														<div class="quarter" id="searchWithdiv">
														    <form:select path="searchWith"  id="searchWith4" cssClass="formSelect" onchange="javascript:displayFromToFields('dojasl');">	
																<form:option value="equal">Equal</form:option>
																<form:option value="between">Between</form:option>
															</form:select>
														</div>
													</td>
													<td width="30%">
														<div class="quarter" id="dojasldiv" style="width:65%;">
															<form:input path="dojasl" id="dojasl" cssClass="dateClass" readonly="true" onclick="javascript:clearDateText('dojasl');"/>
																<img  src="./images/calendar.gif"   id="date_start_trigger4" />	
																<script type="text/javascript">
																	Calendar.setup({inputField :"dojasl",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger4",singleClick : true,step : 1});
																</script>
														</div>
														<div class="displayvalues" id="dojaslfromdiv" style="width:65%;">
															<form:input path="dojasl" id="dojaslfrom" cssClass="dateClass" readonly="true" onclick="javascript:clearDateText('dojaslfrom');"/>
																<img  src="./images/calendar.gif"   id="date_start_trigger5" />	
																<script type="text/javascript">
																	Calendar.setup({inputField :"dojaslfrom",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger5",singleClick : true,step : 1});
																</script>
														</div> <span class="displayvalues" id="dojasland">AND</span> 
														<div class="displayvalues" id="dojasltodiv" style="width:65%;">
															<form:input path="dojasl" id="dojaslto" cssClass="dateClass" readonly="true" onclick="javascript:clearDateText('dojaslto');"/>
																<img  src="./images/calendar.gif"   id="date_start_trigger6" />	
																<script type="text/javascript">
																	Calendar.setup({inputField :"dojaslto",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger6",singleClick : true,step : 1});
																</script>
														</div>
													</td>
													<td width="35%"><div class="quarter">
														<input type="radio" name="dojaslOrder" value="asc"/>Asc</div>
														<div class="quarter"><input type="radio" name="dojaslOrder" value="desc"/>Dsc</div>
														<div class="quarter"><input type="radio" name="dojaslOrder" value="none" checked="checked"/>None</div>
													</td>
										    	</tr>
										    	<tr>
										    		<td width="5%">
										    			<div class="quarter"  style="text-align:left">
										    				<input type="checkbox" name="sfidcheck" id="sfidcheck5" value="groupcheck"/>
										    			</div>
										    		</td>
										       		<td width="10%" nowrap>
										       			<div class="quarter" id="field5">Group</div>
										       		</td>												
													<td width="20%">
														<div class="quarter" id="searchWithdiv">
														    <form:select path="searchWith"  id="searchWith5" cssClass="formSelect">	
																<form:option value="equal">Equal to</form:option>
															</form:select>
														</div>
													</td>
													<td width="30%">
														<div class="quarter" id="searchWithdiv">
															<form:select path="groupName" id="groupName" cssClass="formSelect" >
																<form:option value="Select">Select</form:option>
               	      											<form:options items="${reports.groupList}" itemValue="id" itemLabel="name"/>
               	   											</form:select>
														</div>
													</td>
													<td width="35%">
														<div class="quarter"><input type="radio" name="groupOrder" value="asc"/>Asc</div>
														<div class="quarter"><input type="radio" name="groupOrder" value="desc"/>Dsc</div>
														<div class="quarter"><input type="radio" name="groupOrder" value="none" checked="checked"/>None</div>
													</td>
										    	</tr>
										    	<!-- <tr>
										    		<td width="5%">
										    			<div class="quarter"  style="text-align:left">
										    				<input type="checkbox" name="sfidcheck" id="sfidcheck7" value="divisioncheck"/>
										    			</div>
										    		</td>
										       		<td width="10%" nowrap>
										       			<div class="quarter" id="field7">Division</div>
										       		</td>												
													<td width="20%">
														<div class="quarter" id="searchWithdiv">
														    <form:select path="searchWith"  id="searchWith7" cssClass="formSelect">	
																<form:option value="contains">Contains</form:option>
																<form:option value="starts">Starts With</form:option>
																<form:option value="equal">Equal to</form:option>
															</form:select>
														</div>
													</td>
													<td width="30%">
														<div class="quarter" id="searchWithdiv">
															<form:input path="division" id="division" maxlength="50" cssClass="formSelect"/>
														</div>
													</td>
													<td width="35%">
														<div class="quarter"><input type="radio" name="divisionOrder" value="asc"/>Asc</div>
														<div class="quarter"><input type="radio" name="divisionOrder" value="desc"/>Dsc</div>
														<div class="quarter"><input type="radio" name="divisionOrder" value="none" checked="checked"/>None</div>
													</td>
										    	</tr> -->
										    	<tr>
										    		<td width="5%">
										    			<div class="quarter"  style="text-align:left">
										    				<input type="checkbox" name="sfidcheck" id="sfidcheck6" value="categorycheck"/>
										    			</div>
										    		</td>
										       		<td width="10%" nowrap>
										       			<div class="quarter" id="field6">Category</div>
										       		</td>												
													<td width="20%">
														<div class="quarter" id="searchWithdiv">
														    <form:select path="searchWith"  id="searchWith6" cssClass="formSelect">	
																<form:option value="equal">Equal to</form:option>
															</form:select>
														</div>
													</td>
													<td width="30%">
														<div class="quarter" id="searchWithdiv">
															<form:select path="category" id="category" cssClass="formSelect" >
																<form:option value="Select">Select</form:option>
               	      											<form:options items="${reports.categoryList}" itemValue="id" itemLabel="name"/>
               	   											</form:select>
														</div>
													</td>
													<td width="35%">
														<div class="quarter"><input type="radio" name="categoryOrder" value="asc"/>Asc</div>
														<div class="quarter"><input type="radio" name="categoryOrder" value="desc"/>Dsc</div>
														<div class="quarter"><input type="radio" name="categoryOrder" value="none" checked="checked"/>None</div>
													</td>
										    	</tr>
										    	<tr>
										    		<td width="5%">
										    			<div class="quarter"  style="text-align:left">
										    				<input type="checkbox" name="sfidcheck" id="sfidcheck7" value="phcheck"/>
										    			</div>
										    		</td>
										       		<td width="10%" nowrap>
										       			<div class="quarter" id="field7">PH</div>
										       		</td>												
													<td width="20%">
														<div class="quarter" id="searchWithdiv">
														    <form:select path="searchWith"  id="searchWith7" cssClass="formSelect">	
																<form:option value="equal">Equal to</form:option>
															</form:select>
														</div>
													</td>
													<td width="30%">
														<div class="quarter" id="searchWithdiv">
															<form:select path="ph" id="ph" cssClass="formSelect" >
																<form:option value="Select">Select</form:option>
               	      											<form:options items="${reports.handicapList}" itemValue="id" itemLabel="name"/>
               	   											</form:select>
														</div>
													</td>
													<td width="35%">
														<div class="quarter"><input type="radio" name="phOrder" value="asc"/>Asc</div>
														<div class="quarter"><input type="radio" name="phOrder" value="desc"/>Dsc</div>
														<div class="quarter"><input type="radio" name="phOrder" value="none" checked="checked"/>None</div>
													</td>
										    	</tr>
											</table>
										    </div>
										    <div id="fragment-2">
										    <table width="100%">
										    	<tr>
										    		<td width="5%">
										    			<div class="quarter"  style="text-align:left">
										    				<input type="checkbox" name="sfidcheck" id="sfidcheck8" value="religioncheck"/>
										    			</div>
										    		</td>
										       		<td width="10%" nowrap>
										       			<div class="quarter" id="field8">Religion</div>
										       		</td>												
													<td width="20%">
														<div class="quarter" id="searchWithdiv">
														    <form:select path="searchWith"  id="searchWith8" cssClass="formSelect">	
																<form:option value="equal">Equal to</form:option>
															</form:select>
														</div>
													</td>
													<td width="30%">
														<div class="quarter" id="searchWithdiv">
															<form:select path="religion" id="religion" cssClass="formSelect" >
																<form:option value="Select">Select</form:option>
               	      											<form:options items="${reports.religionList}" itemValue="id" itemLabel="name"/>
               	   											</form:select>
														</div>
													</td>
													<td width="35%">
														<div class="quarter"><input type="radio" name="religionOrder" value="asc"/>Asc</div>
														<div class="quarter"><input type="radio" name="religionOrder" value="desc"/>Dsc</div>
														<div class="quarter"><input type="radio" name="religionOrder" value="none" checked="checked"/>None</div>
													</td>
										    	</tr>
										    	<tr>
										    		<td width="5%">
										    			<div class="quarter"  style="text-align:left">
										    				<input type="checkbox" name="sfidcheck" id="sfidcheck9" value="qualcheck"/>
										    			</div>
										    		</td>
										       		<td width="10%" nowrap>
										       			<div class="quarter" id="field9">Qualification</div>
										       		</td>												
													<td width="20%">
														<div class="quarter" id="searchWithdiv">
														    <form:select path="searchWith"  id="searchWith9" cssClass="formSelect">	
																<form:option value="equal">Equal to</form:option>
															</form:select>
														</div>
													</td>
													<td width="30%">
														<div class="quarter" id="searchWithdiv">
															<form:select path="qualification" id="qualification" cssClass="formSelect" >
																<form:option value="Select">Select</form:option>
               	      											<form:options items="${reports.qualificationList}" itemValue="id" itemLabel="name"/>
               	   											</form:select>
               	   										</div>
													</td>
													<td width="35%">
														<div class="quarter"><input type="radio" name="qualificationOrder" value="asc"/>Asc</div>
														<div class="quarter"><input type="radio" name="qualificationOrder" value="desc"/>Dsc</div>
														<div class="quarter"><input type="radio" name="qualificationOrder" value="none" checked="checked"/>None</div>
													</td>
										    	</tr>
										    	<tr>
										    		<td width="5%">
										    			<div class="quarter"  style="text-align:left">
										    				<input type="checkbox" name="sfidcheck" id="sfidcheck10" value="cghscheck"/>
										    			</div>
										    		</td>
										       		<td width="10%" nowrap>
										       			<div class="quarter" id="field10">CGHS Disp No</div>
										       		</td>												
													<td width="20%">
														<div class="quarter" id="searchWithdiv">
														    <form:select path="searchWith"  id="searchWith10" cssClass="formSelect">	
																<form:option value="contains">Contains</form:option>
																<form:option value="starts">Starts With</form:option>
																<form:option value="equal">Equal to</form:option>
															</form:select>
														</div>
													</td>
													<td width="30%">
														<div class="quarter" id="searchWithdiv">
															<form:input path="cghs" id="cghs" maxlength="50" cssClass="formSelect"/>
														</div>
													</td>
													<td width="35%">
														<div class="quarter"><input type="radio" name="cghsOrder" value="asc"/>Asc</div>
														<div class="quarter"><input type="radio" name="cghsOrder" value="desc"/>Dsc</div>
														<div class="quarter"><input type="radio" name="cghsOrder" value="none" checked="checked"/>None</div>
													</td>
										    	</tr>
										    	<tr>
										    		<td width="5%">
										    			<div class="quarter"  style="text-align:left">
										    				<input type="checkbox" name="sfidcheck" id="sfidcheck11" value="quartercheck"/>
										    			</div>
										    		</td>
										       		<td width="10%" nowrap>
										       			<div class="quarter" id="field11">Quarter</div>
										       		</td>												
													<td width="20%">
														<div class="quarter">
														    <form:select path="searchWith"  id="searchWith11" cssClass="formSelect">	
																<form:option value="contains">Equal</form:option>
																<form:option value="equal">Equal to</form:option>
															</form:select>
														</div>
													</td>
													<td width="30%">
														<div class="quarter" id="searchWithdiv">
															<form:input path="quarter" id="quarter" maxlength="50" cssClass="formSelect"/>
														</div>
													</td>
													<td width="35%">
														<div class="quarter"><input type="radio" name="quarterOrder" value="asc"/>Asc</div>
														<div class="quarter"><input type="radio" name="quarterOrder" value="desc"/>Dsc</div>
														<div class="quarter"><input type="radio" name="quarterOrder" value="none" checked="checked"/>None</div>
													</td>
										    	</tr>
											</table>
										    </div>
										   <div id="fragment-3">
										     	<table width="100%">
										    		<tr><td>
												    	<div class="panel">
															<ul id="list_1" >																
																<li id="list_1_item_1">CGHS Disp No</li>
																<li id="list_1_item_2">Quarter No</li>
																<li id="list_1_item_3">Doj in ASL</li>																
																<li id="list_1_item_4">DOB</li>																
																<li id="list_1_item_5">CL</li>
																<li id="list_1_item_6">EL</li>
																<li id="list_1_item_7">Phone No</li>																
																<li id="list_1_item_8">Date Of Present Rank</li>
																<li id="list_1_item_9">PH</li>
																<li id="list_1_item_10">HPL</li>
																<li id="list_1_item_11">Religion</li>
															</ul>
														</div>
														<div class="panel">
															<ul id="list_2" >
																<li id="list_2_item_12">SFID</li>
																<li id="list_2_item_13">Name</li>
																<li id="list_2_item_14">Division</li>
																<li id="list_2_item_15">Category</li>
																<li id="list_2_item_16">Group</li>
																<li id="list_2_item_17">Qualification</li>
																<li id="list_2_item_18">Reporting Officer</li>
																<li id="list_2_item_19">Designation</li>
																
															</ul>
														</div>
													</td></tr>
													<tr><td>
														<div class="line">
															<div class="quarter">Report Name</div>
															<div class="quarter"><form:input path="reportName" id="reportName" maxlength="50" cssClass="formSelect"/></div>
															<div class="quarter"></div>
															<div class="quarter expbutton"><a href="javascript:generateCustomReport()"><span>Generate</span></a></div>
														</div>
													</td></tr>
												</table>
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
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		<form:hidden path="jsonValue"/>
		</form:form>
	</body>
</html>
<!-- End:CustomReport.jsp -->