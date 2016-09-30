<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:IONMaster.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fck" prefix="FCK" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/letternumberformat.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<script language="javascript" src="./script/date.js"></script>
<script language="javascript" src="fckeditor/fckeditor.js"></script> 
<script type="text/javascript" src="script/ajaxUpload.js"></script>



<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>

<title>Create ION </title>
<script type="text/javascript">
	$jq(document).ready(function(){
	
		javascript:multiSelectBox();
		 clearIONMaster();
		});
</script>
</head>

<body>
	<form:form method="post" commandName="letterNumberFormat" id="letterNumberFormat">
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
								<div class="headTitle" style=""><h2><u>I.O.N</u></h2></div>
								<%-- Content Page starts --%>
								<div>
								
					<div class="line">
					<table  style="margin-left: 650px; ">
							       <tr>
								   <td style=" text-align:center;  font-size: 15pt; color: purple;">Draft Copy</td> 
								   </tr>
				          <c:forEach items="${letterNumberFormat.displayIONDetails}" var="displayIon">	    
								    <tr>
								    <td><a style="color: blue;">CreatedBy</a></td><td>: ${displayIon.ionCreatedBy}</td>
								    </tr>
								    <tr>
								    <td><a style="color: blue;">CreationDate</a></td><td>: ${displayIon.ionCreationDate}</td>
								    </tr>
								    <tr>
								    <td><a style="color: blue;">LastModifiedBy</a></td><td>: ${displayIon.lastModifiedBy}</td>
								    </tr>
								    <tr>
								    <td><a style="color: blue;">LastModifiedDate</a></td><td>: ${displayIon.lastModifiedDate}</td>
								    </tr>
					   </c:forEach>	
		             </table>
		           </div>
		                           <div class="line" id="result2">
										
									</div>
									<div class="line" >
									<hr style="border-top-style: dotted;border-bottom-style: solid;color: maroon; /">
										<div   class="quarter half leftmar_withoutbold">Letter Number :
										
											<font   size="4">${letterNumberFormat.letterNumber}</font>										
										</div>	
										<div  style="text-align: right;" class="quarter half leftmar_withoutbold">Dated :
										
											<font size="4">${letterNumberFormat.ionDate}</font>										
										</div>					
										
									</div>
									
									<div class="line">
									<hr style="border-top-style: dotted;border-bottom-style: solid;color: maroon; " />	
										<div   class="quarter bold leftmar">Subject<span class="failure">*</span><a id="subject1" href="javascript:hiding('subject');" value="show">(save)</a></div>
										<div class="quarter" id="subject2">
													 <spring:bind path="letterNumberFormat.subject">
													<textarea id="subject" name="subject" cols="30" rows="60"> ${letterNumberFormat.subject} </textarea>
													</spring:bind>
													<input type="text" class="counter" name="subjectcounter" value="1500" id="subjectcounter" disabled=""/>										
										</div>	
										<div class="threefourth" id="subject3">
													
										</div>	
									</div>
									<div class="line">
										<div   class="quarter bold leftmar">Reference <a id="reference1" href="javascript:hiding('reference');" value="show">SAVE</a></div>
										<div class="quarter" id="reference2">
													<spring:bind path="letterNumberFormat.reference">
														<textarea id="reference" name="reference" cols="30" rows="60"> ${letterNumberFormat.reference} </textarea>
													</spring:bind>
													<input type="text" class="counter" name="referencecounter" value="1500" id="referencecounter" disabled=""/>										
										</div>	
										<div class="threefourth" id="reference3">
													
										</div>
									</div>
									<div class="line">
										<div   class="quarter bold leftmar">Content<span class="failure">*</span> <a id="content1" href="javascript:hiding('content');" value="show">SAVE</a></div>
										<div class="quarter" id="content2">
											 		<spring:bind path="letterNumberFormat.content">
													<textarea id="content" name="content" cols="30" rows="60"> ${letterNumberFormat.content}                          </textarea>
													</spring:bind>
													
													<input type="text" class="counter" name="contentcounter" value="1500" id="contentcounter" disabled=""/>										
										</div>	
										<div class="threefourth" id="content3">
													
										</div>				
										
									</div>
									<div class="line">
										<div   class="quarter bold leftmar">Enclosers <a id="enclosers11" href="javascript:hiding('enclosers1');" value="show">SAVE</a></div>
										<div class="quarter" id="enclosers12">
												<spring:bind path="letterNumberFormat.enclosers">
												<textarea id="enclosers" name="enclosers" cols="30" rows="60"> ${letterNumberFormat.enclosers}</textarea>    
												</spring:bind>
													<input type="text" class="counter" name="encloserscounter" value="1500" id="encloserscounter" disabled=""/>										
										</div>	
										<div class="threefourth" id="enclosers13">
													
										</div>	
									</div>
									
									<div class="line">
										<div   class="quarter bold leftmar">Enclosers with Attachment<a id="enclosers1" href="javascript:hidingFile('enclosers');" value="show"></a></div>
										<div class="threefourth" id="enclosers2">
												<div id="enclosers4">
												<c:if test="${sessionScope.fileEncloserList!=null && sessionScope.fileEncloserList!='[]' && sessionScope.fileEncloserList!=''}">
												<table style="width:100%" border="0"  id="filesDetailsId">
												
												<%int i=0; %>
												<c:forEach var="fileEncloserList" items="${sessionScope.fileEncloserList}">	
												<tr id="fileRow<%=i %>">
												<td ><a href="javascript:showFile('${fileEncloserList.id}');">${fileEncloserList.filename}</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:removeFile('${fileEncloserList.id}');">Remove</a></td>
												
												</tr>
												<% i++; %>
												</c:forEach>
												</table>
												</c:if>
												</div>
												<div id="enclosers5">
												
												<table style="width:100%;" border="0" class="list" id="uploadFileTableId">
												
												<tr>
													<td style="width: 40%;"><input type="text" name="enclosureFileNames" id="enclosureFileNames" style="width: 99%;"/></td>
													<td style="width: 30%;"><form:input path="enclosureFile" type="file" id="files" /></td>
													<td style="width: 10%;"><input type="button" value="Upload" onclick="uploadFile(this)" id="uploadFileTDId" /></td>
													
												</tr>
								
												</table>
												
												</div>
										</div>	
										<div class="threefourth" id="enclosers3">
												<c:if test="${sessionScope.fileEncloserList!=null && sessionScope.fileEncloserList!='[]' && sessionScope.fileEncloserList!=''}">
												<table style="width:100%" border="0" id="filesDetailsId">
												
												<%int i=0; %>
												<c:forEach var="fileEncloserList" items="${sessionScope.fileEncloserList}">	
												<tr id="fileRow<%=i %>">
												<td ><a href="javascript:showFile('${fileEncloserList.id}');">${fileEncloserList.filename}</a></td>
												</tr>
												<% i++; %>
												</c:forEach>
												</table>
												</c:if>	
										</div>	
									</div>
									<div class="line">
										<div  class="quarter bold leftmar">Initiated By <span style="color: red">*</span></div>
										<div class="quarter" >
											<form:select path="ionInitiatedSfid" id="ionInitiatedSfid" cssClass="formSelect"  onchange="getInitiatedRoleList(0);">
												<form:option value="">Select</form:option>
												<form:options items="${sessionScope.initiatedSfidList}" itemValue="userSfid" itemLabel="nameInServiceBook"/>
											</form:select>
													
										</div>	
										<div   class="quarter bold leftmar" id="initiatedRoleIdName">Role </div>
										<div class="quarter" id="initiatedRoleIdVal">
														<form:select path="ionInitiatedRoleId" id="ionInitiatedRoleId" cssClass="formSelect"  onchange="">
														<form:option value="">Select</form:option>
														<form:options items="${sessionScope.initiatedRoleList}" itemValue="id" itemLabel="name"/>
														</form:select>
										</div>
										
									</div>
									<div class="line">
										<div   class="quarter bold leftmar">Forwarded By<span style="color: red">*</span></div>
										<div class="quarter" >
											<form:select path="ionForwardSfid" id="ionForwardSfid" cssClass="formSelect"  onchange="getInitiatedRoleList(1);">
												<form:option value="">Select</form:option>
												<form:options items="${sessionScope.forwardSfidList}" itemValue="userSfid" itemLabel="nameInServiceBook"/>
											</form:select>
													
										</div>	
										<div   class="quarter bold leftmar" id="forwardRoleIdName">Role </div>
										<div class="quarter" id="forwardRoleIdVal">
														<form:select path="ionForwardRoleId" id="ionForwardRoleId" cssClass="formSelect"  onchange="">
														<form:option value="">Select</form:option>
														<form:options items="${sessionScope.forwardRoleList}" itemValue="id" itemLabel="name"/>
														</form:select>
										</div>
										
									</div>
									<div class="line">
										<div   class="quarter bold leftmar">Approved By <span style="color: red">*</span></div>
										<div class="quarter" >
											<form:select path="ionApprovedSfid" id="ionApprovedSfid" cssClass="formSelect"  onchange="getInitiatedRoleList(2);">
												<form:option value="">Select</form:option>
												<form:options items="${sessionScope.approvedSfidList}" itemValue="userSfid" itemLabel="nameInServiceBook"/>
											</form:select>
													
										</div>	
										<div   class="quarter bold leftmar" id="approvedRoleIdName">Role </div>
										<div class="quarter" id="approvedRoleIdVal">
														<form:select path="ionApprovedRoleId" id="ionApprovedRoleId" cssClass="formSelect"  onchange="">
														<form:option value="">Select</form:option>
														<form:options items="${sessionScope.approvedRoleList}" itemValue="id" itemLabel="name"/>
														</form:select>
										</div>
										
									</div>
									<div class="line" id="initiatedRoleId">
																				
									</div>
									<div class="line" id="forwardRoleId">
																				
									</div>
									<div class="line" id="approvedRoleId">
																				
									</div>
									<div class="line" id="encloserDiv">
																				
									</div>
									
									<div class="line">
										<div class="quarter">&nbsp;</div>	
										<div class="threefourth">		 
			                                 <input type="checkbox" name="publicChk" id="publicChk" value="Y" onChange="javascript:enableChk();"/>Public
			                                 	 	 	 
		                                </div>
									</div>
									<div class="line">
										<div class="quarter">&nbsp;</div>	
										<div class="threefourth">		 
			                                 <input type="checkbox" name="departmentChk" id="departmentChk" value="Y" onChange="javascript:enableChk();"/>Department 
			                                 <input type="checkbox" name="designationChk" id="designationChk" value="N" onChange="javascript:enableChk();"/>Designation		
			                                 <input type="checkbox" name="roleChk" id="roleChk" value="R" onChange="javascript:enableChk();"/>Role
			                                 <input type="checkbox" name="roleHirarchyChk" id="roleHirarchyChk" value="R" onChange="javascript:enableChk();"/>Role Hirarchy
			                                 <input type="checkbox" name="sfidChk" id="sfidChk" value="S" onChange="javascript:enableChk();"/>SFID	
			                                 <input type="checkbox" name="orgChk" id="orgChk" value="O" onChange="javascript:enableChk();"/>Other Labs 	 	 	 
		                                </div>
									</div>
									
									
									<div class="line" id="departmentSub">
										<div  class="quarter bold leftmar">Departments to Circulate the I.O.N<span class="failure">*</span></div>
										
									</div>
									<div class="line" id="departmentVal">
											<div class="leftmar">
												<div style="float: left; width : 35%">
														<form:select path="departments" id="SelectLeft2" size="10" multiple="true" cssStyle="width:300px">
															<form:options items="${sessionScope.departmentList}" itemValue="id" itemLabel="deptName"/>
														</form:select>
													</div>
												<div style="float: left; width : 12%; margin-top: 60px;">									
													<div style="margin-bottom: 5px;" align="center">
													     <input style="margin-bottom: 5px" id="MoveRight2" type="button" value="Add " />
													     <input style="margin-bottom: 5px" id="MoveRight21" type="button" value=" Add - CC " />
	     											     <input id="MoveLeft2" type="button" value=" Remove " />  
	     											     <input id="MoveLeft21" type="button" value="Remove-CC" />    											        
	     											</div>		      																				
		      									</div>
												<div style="float: left; width : 20%">
														<form:select path="department" id="SelectRight2" size="10" multiple="true" cssStyle="width:225px">
															<form:options items="${sessionScope.ionDeptList}" itemValue="id" itemLabel="deptName"/>
														</form:select>
													</div>
													<div style="float: right; width : 25%">
														<form:select path="departmentCopy" id="SelectRight21" size="10" multiple="true" cssStyle="width:225px">
															<form:options items="${sessionScope.ionDeptListCopy}" itemValue="id" itemLabel="deptName"/>
														</form:select>
													</div>
													
											</div>
									</div>
									<div class="line" id="designationSub">
										<div  class="quarter bold leftmar">Designations to Circulate the I.O.N<span class="failure">*</span></div>
										
									</div>
									<div class="line" id="designationVal">
											<div class="leftmar">
												<div style="float: left; width : 35%">
														<form:select path="designations" id="SelectLeft4" size="10" multiple="true" cssStyle="width:300px">
															<form:options items="${sessionScope.designList}" itemValue="id" itemLabel="name"/>
														</form:select>
													</div>
												<div style="float: left; width : 12%; margin-top: 60px;">									
													<div style="margin-bottom: 5px;" align="center">
													     <input style="margin-bottom: 5px" id="MoveRight4" type="button" value=" Add " />
													     <input style="margin-bottom: 5px" id="MoveRight41" type="button" value=" Add - CC " />
	     											     <input id="MoveLeft4" type="button" value=" Remove " />    
	     											     <input id="MoveLeft41" type="button" value="Remove-CC" />    											        
	     											</div>		      																				
		      									</div>
												<div style="float: left; width : 20%">
														<form:select path="designation" id="SelectRight4" size="10" multiple="true" cssStyle="width:225px">
															<form:options items="${sessionScope.ionDesignList}" itemValue="id" itemLabel="name"/>
														</form:select>
													</div>
													<div style="float: right; width : 25%">
														<form:select path="designationCopy" id="SelectRight41" size="10" multiple="true" cssStyle="width:225px">
															<form:options items="${sessionScope.ionDesignListCopy}" itemValue="id" itemLabel="name"/>
														</form:select>
													</div>
											</div>
									</div>
									<div class="line" id="roleSub">
										<div  class="quarter bold leftmar">Roles to Circulate the I.O.N<span class="failure">*</span></div>
										
									</div>
									<div class="line" id="roleVal">
											<div class="leftmar">
												<div style="float: left; width : 35%">
														<form:select path="roleHirarchys" id="SelectLeft6" size="10" multiple="true" cssStyle="width:300px">
															<form:options items="${sessionScope.roleList}" itemValue="id" itemLabel="name"/>
														</form:select>
													</div>
												<div style="float: left; width : 12%; margin-top: 60px;">									
													<div style="margin-bottom: 5px;" align="center">
													     <input style="margin-bottom: 5px" id="MoveRight6" type="button" value=" Add " />
													     <input style="margin-bottom: 5px" id="MoveRight61" type="button" value=" Add - CC " />
	     											     <input id="MoveLeft6" type="button" value=" Remove " />   
	     											     <input id="MoveLeft61" type="button" value="Remove-CC" />    											        
	     											</div>		      																				
		      									</div>
												<div style="float: left; width : 20%">
														<form:select path="roleHirarchy" id="SelectRight6" size="10" multiple="true" cssStyle="width:225px">
															<form:options items="${sessionScope.ionRoleList}" itemValue="id" itemLabel="name"/>
														</form:select>
													</div>
													<div style="float: right; width : 25%">
														<form:select path="roleHirarchyCopy" id="SelectRight61" size="10" multiple="true" cssStyle="width:225px">
															<form:options items="${sessionScope.ionRoleListCopy}" itemValue="id" itemLabel="name"/>
														</form:select>
													</div>
											</div>
									</div>
									<div class="line" id="roleHirarchySub">
										<div  class="quarter bold leftmar">Role Hirarchy to Circulate the I.O.N<span class="failure">*</span></div>
										
									</div>
									<div class="line" id="roleHirarchyVal">
											<div class="leftmar">
												<div style="float: left; width : 35%">
														<form:select path="roleHirarchys" id="SelectLeft10" size="10" multiple="true" cssStyle="width:300px">
															<form:options items="${sessionScope.roleHirarchyList}" itemValue="roleID" itemLabel="roleName"/>
														</form:select>
													</div>
												<div style="float: left; width : 12%; margin-top: 60px;">									
													<div style="margin-bottom: 5px;" align="center">
													     <input style="margin-bottom: 5px" id="MoveRight10" type="button" value=" Add " />
													      <input style="margin-bottom: 5px" id="MoveRight101" type="button" value=" Add - CC " />
	     											     <input id="MoveLeft10" type="button" value=" Remove " />   
	     											     <input id="MoveLeft101" type="button" value="Remove-CC" />   
	     											      											        
	     											</div>		      																				
		      									</div>
												<div style="float: left; width : 20%">
														<form:select path="roleHirarchy" id="SelectRight10" size="10" multiple="true" cssStyle="width:225px">
															<form:options items="${sessionScope.ionRoleHirarchyList}" itemValue="roleID" itemLabel="roleName"/>
														</form:select>
													</div>
													<div style="float: right; width : 25%">
														<form:select path="roleHirarchyCopy" id="SelectRight101" size="10" multiple="true" cssStyle="width:225px">
															<form:options items="${sessionScope.ionRoleHirarchyListCopy}" itemValue="roleID" itemLabel="roleName"/>
														</form:select>
													</div>
											</div>
									</div>
									<div class="line" id="sfidSub">
										<div  class="quarter bold leftmar">SFIDs to Circulate the I.O.N<span class="failure">*</span></div>
										
									</div>
									<div class="line" id="sfidVal">
											<div class="leftmar">
												<div style="float: left; width : 35%">
														<form:select path="sfidNames" id="SelectLeft8" size="10" multiple="true" cssStyle="width:300px">
															<form:options items="${sessionScope.sfidList}" itemValue="userSfid" itemLabel="nameInServiceBook"/>
														</form:select>
													</div>
												<div style="float: left; width : 12%; margin-top: 60px;">									
													<div style="margin-bottom: 5px;" align="center">
													     <input style="margin-bottom: 5px" id="MoveRight8" type="button" value=" Add " />
													     <input style="margin-bottom: 5px" id="MoveRight81" type="button" value=" Add - CC " />
	     											     <input id="MoveLeft8" type="button" value=" Remove " />   
	     											     <input id="MoveLeft81" type="button" value="Remove-CC" />    											        
	     											</div>		      																				
		      									</div>
												<div style="float: left; width : 20%">
														<form:select path="sfidName" id="SelectRight8" size="10" multiple="true" cssStyle="width:225px">
															<form:options items="${sessionScope.ionSfidList}" itemValue="userSfid" itemLabel="nameInServiceBook"/>
														</form:select>
													</div>
													<div style="float: right; width : 25%">
														<form:select path="sfidNameCopy" id="SelectRight81" size="10" multiple="true" cssStyle="width:225px">
															<form:options items="${sessionScope.ionSfidListCopy}" itemValue="userSfid" itemLabel="nameInServiceBook"/>
														</form:select>
													</div>
											</div>
									</div>
									
									<div class="line" id="orgSub">
										<div  class="quarter bold leftmar">Other Labs to Circulate the I.O.N<span class="failure">*</span></div>
										
									</div>
									<div class="line" id="orgVal">
											<div class="leftmar">
												<div style="float: left; width : 35%">
														<form:select path="organizations" id="SelectLeft12" size="10" multiple="true" cssStyle="width:300px">
															<form:options items="${sessionScope.orgList}" itemValue="id" itemLabel="name"/>
														</form:select>
													</div>
												<div style="float: left; width : 12%; margin-top: 60px;">									
													<div style="margin-bottom: 5px;" align="center">
													     <input style="margin-bottom: 5px" id="MoveRight12" type="button" value=" Add " />
													     <input style="margin-bottom: 5px" id="MoveRight121" type="button" value=" Add - CC " />
	     											     <input id="MoveLeft12" type="button" value=" Remove " />  
	     											     <input id="MoveLeft121" type="button" value="Remove-CC" />    											        
	     											</div>		      																				
		      									</div>
												<div style="float: left; width : 20%">
														<form:select path="organization" id="SelectRight12" size="10" multiple="true" cssStyle="width:225px">
															<form:options items="${sessionScope.ionOrgList}" itemValue="id" itemLabel="name"/>
														</form:select>
													</div>
													<div style="float: right; width : 25%">
														<form:select path="organizationCopy" id="SelectRight121" size="10" multiple="true" cssStyle="width:225px">
															<form:options items="${sessionScope.ionOrgListCopy}" itemValue="id" itemLabel="name"/>
														</form:select>
													</div>
											</div>
									</div>
									
									<div class="line" id="result1">
														
										
									</div>
									
									
									<div class="line">
										<div style="margin-left:25%">
											
											<a href="javascript:manageIONMaster('save');"><div class="appbutton">Save</div></a>
										</div>
										<div style="margin-left:25%" id="circulateVisibleId">
											<a href="javascript:manageIONMaster('circulate');"><div class="appbutton">Circulate</div></a>
										</div>
									
												<%-- <a href="javascript:getIONMaster($jq('#id').val());"><div class="appbutton">Clear</div></a>--%>
											<div class="expbutton"><a href="javascript:goBackToIONDetails();"><span>Go Back To ION Details</span></a></div>  
									</div>
									
									
									<div class="line height"></div>
									<div class="line" id="displayTable">
										
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
		<form:hidden path="id" id="id"/>
		<form:hidden path="yearType" id="yearType"/>
		<form:hidden path="deptSeriesStartNum" id="deptSeriesStartNum"/>
		<form:hidden path="deptSeriesEndNum" id="deptSeriesEndNum"/>
		<form:hidden path="back" id="back"/>
		
		<form:hidden path="departmentId" id="departmentId"/>
		<form:hidden path="designationId" id="designationId"/>
		<form:hidden path="orgRoleId" id="orgRoleId"/>
		<form:hidden path="sfidNameId" id="sfidNameId"/>
		<form:hidden path="roleHirarchyId" id="roleHirarchyId"/>
		<form:hidden path="organizationId" id="organizationId"/>
		
		<form:hidden path="departmentIdCopy" id="departmentIdCopy"/>
		<form:hidden path="designationIdCopy" id="designationIdCopy"/>
		<form:hidden path="orgRoleIdCopy" id="orgRoleIdCopy"/>
		<form:hidden path="sfidNameIdCopy" id="sfidNameIdCopy"/>
		<form:hidden path="roleHirarchyIdCopy" id="roleHirarchyIdCopy"/>
		<form:hidden path="organizationIdCopy" id="organizationIdCopy"/>
		
		<form:hidden path="circulationStatus" id="circulationStatus"/>
		<form:hidden path="deptNum" id="deptNum"/>
		<form:hidden path="letterFormatId" id="letterFormatId"/>
		<form:hidden path="startDate" id="startDate"/>
		<form:hidden path="endDate" id="endDate"/>
		<form:hidden path="selectStatus" id="selectStatus"/>
		<form:hidden path="tempLetterFormatId" id="tempLetterFormatId"/>
		<form:hidden path="enclosureFileName" id="enclosureFileName"/>
		<form:hidden path="fileId" id="fileId"/>
		<form:hidden path="publicStatus" id="publicStatus"/>
		<form:hidden path="circulateVisible" id="circulateVisible"/>
		<form:hidden path="refOrgRoleId" id="refOrgRoleId"/>
		
		
		
		
		
		
		
		
		</form:form>
		<script>
		
		 
		</script>
	</body>
</html>
<!-- End:IONMaster.jsp -->