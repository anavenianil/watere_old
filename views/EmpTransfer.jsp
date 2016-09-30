<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:EmpTransfer.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/ajaxUpload.js"></script>
<script type="text/javascript" src="script/transfer.js"></script>
<title>Lab Transfer</title>
</head>

<body onload="javascript:resetTransfer('${type}');">
	<form:form method="post" commandName="transfer">
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
								<div class="headTitle" >Lab Transfer</div>
								<%-- Content Page starts --%>
								<div class="line">
									<div id="result"></div>
									<c:if test="${type=='employee'}">
										<!-- Head transfer the employee from one department to another department -->
										<div class="line">
											<div class="quarter leftmar">Working Departments<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:select path="departmentID" id="departmentID" cssClass="formSelect" onchange="javascript:getEmployees();">
													<form:option value="0">Select</form:option>
													<form:options items="${transfer.empWorkingDept}" itemValue="id" itemLabel="deptName"/>
												</form:select>	
											</div>
										</div>
										<div class="line">
											<div class="quarter leftmar">Employee Name<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:select path="transferedSFID" id="transferedSFID" cssClass="formSelect" >
													<form:option value="0">Select</form:option>
												</form:select>	
											</div>
										</div>										
									</c:if>
									<c:if test="${type=='self'}">
										<div class="line">
											<div class="quarter leftmar">Type of Transfer<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:select path="transferType" id="transferType" cssClass="formSelect" onchange="javascript:displayTransferedTo()">
													<form:option value="0">Select</form:option>
													<form:option value="45">Internal</form:option>
													<form:option value="46">External</form:option>
												</form:select>	
											</div>
										</div>
									</c:if>
									<!-- In case of external -->
									<div class="line" id="external" style="display:none">
										<div class="quarter leftmar">Place of Other Lab<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:input path="otherLab" id="otherLab" maxlength="100"/>
										</div>
									</div>
									
									<!-- In case of internal -->
									<div class="line" id="internal" style="display:none">
										<div class="quarter leftmar">Requested Department<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="reqDepartment" id="reqDepartment" cssClass="formSelect" >
												<form:option value="0">Select</form:option>
												<form:options items="${transfer.departmentsList}" itemValue="id" itemLabel="deptName"/>
											</form:select>	
										</div>
									</div>
									<c:if test="${type=='employee'}">
										<script>$jq("#internal").show();</script>
									</c:if>
									<div class="line" id="attachmentDiv" style="display:none">
										<div class="quarter leftmar">Attachment</div>
										<div class="quarter">
											<form:input path="attachmentFile" type="file" id="attachmentFile"/>	
										</div>									
									</div>
									<div class="line">
									 	<div class="quarter leftmar">Reason<span class="mandatory">*</span></div>
										 <div class="quater">
										  	<form:textarea path="reason" id="reason" cols="30" rows="5" ></form:textarea>
										 </div>
									</div>	
									
									<div class="line">
										<div style="margin-left:25%;">
											<div class="expbutton"><a href="javascript:submitTransfer('${type}');"><span>Submit</span></a></div>
											<div class="expbutton"><a href="javascript:resetTransfer('${type}');"><span>Clear</span></a></div>
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
		<form:hidden path="type"/>
		<form:hidden path="param"/>	
		</form:form>		
	</body>
</html>
<!-- End:EmpTransfer.jsp -->