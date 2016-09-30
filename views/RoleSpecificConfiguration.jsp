<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:RoleSpecificConfiguration.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/orgstructure.js"></script>

<title>User Specific Configuration</title>
<script type="text/javascript">
	$jq(document).ready(function(){
	
		javascript:multiSelectBox2();
		 
		});
</script>
</head>

<body onload="javascript:multiSelectBox()">
	<form:form method="post" commandName="orgStructure">
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
								<div class="headTitle">User Specific Configuration</div>
								<%-- Content Page starts --%>
								
								<div class="line">
									<div class="line" id="result"></div>
									<div class="line">
										<div class="quarter leftmar">Organisation Roles<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="roleName" id="roleName" cssClass="roleName" onmouseover="setSelectWidth('#roleName')" onchange = "javascript:getSubordinateList('displayTable');">
												<form:option value="0">Select</form:option>
												<form:options items="${orgStructure.empRolesList}" itemValue="id" itemLabel="name"/>
              	      						</form:select>
              	   						</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Request Type</div></div>
									
										<div class="line">
											<div class="leftmar" >
											
												<div style="float: left; width: 38%;">De Selected Request Type</div>
												
												<div class="half">Selected Request Type<div><span class="mandatory">*</span></div></div>
												
											</div>
									</div>
									<div class="line">
											<div class="leftmar">
												<div style="float: left; width : 28%">
													<form:select path="requestName" id="SelectLeft2" size="10" multiple="true" cssStyle="width:250px">
														<form:options items="${orgStructure.requestTypeList}" itemValue="id" itemLabel="name"/>
													</form:select>
												</div>
												<div style="float: left; width : 10%; margin-top: 60px;">									
														<div style="margin-bottom: 5px;" align="center">
													          <input style="margin-bottom: 5px" id="MoveRight2" type="button" value=" Add " />
      											              <input id="MoveLeft2" type="button" value=" Remove " />    											        
      											        </div>		      																				
		      									</div>
												<div style="float: left; width : 35%">
													<form:select path="requestName" id="SelectRight2" size="10" multiple="true" cssStyle="width:250px">
													</form:select>
												</div>
											</div>
										
									</div>
									<div class="line">
										<div class="quarter leftmar">Gazetted Type</div>
										<div class="quarter">
											<form:select path="gazType" id="gazType" cssClass="formSelect" onchange="javascript:selectedDesignation()">
												<form:option value="0">All</form:option>
              	      								<form:option value="GAZETTED">Gazetted</form:option>
              	      								<form:option value="NON GAZETTED">Non Gazetted</form:option>
              	   								</form:select></div>
									</div>
									<div class="line">
											<div class="leftmar">
												<div style="float: left; width: 38%;">De Selected Designations</div>
												<div class="half" >Selected Designations</div>
											</div>
									</div>
									<div class="line">
											<div class="leftmar">
												<div style="float: left; width : 28%">
													<form:select path="designation" id="SelectLeft" size="10" multiple="true" cssStyle="width:250px">
														<form:options items="${orgStructure.designationsList}" itemValue="id" itemLabel="name"/>
													</form:select>
												</div>
												<div style="float: left; width : 10%; margin-top: 60px;">									
														<div style="margin-bottom: 5px;" align="center">
													          <input style="margin-bottom: 5px"id="MoveRight" type="button" value=" Add " />
      											              <input id="MoveLeft" type="button" value=" Remove " />    											        
      											        </div>		      																				
		      									</div>
												<div style="float: left; width : 35%">
													<form:select path="designation" id="SelectRight" size="10" multiple="true" cssStyle="width:250px">
													</form:select>
												</div>
											</div>
										
									</div>
									<div class="line">
										<div class="quarter leftmar">Assigned Type<span class="mandatory">*</span></div>
										<div class="quarter"><form:select path="assignType" id="assignType" cssClass="formSelect" >
												<form:option value="0">Select</form:option>
              	      								<form:option value="11">Automatic</form:option>
              	      								<form:option value="10">Manual</form:option>
              	      								<form:option value="32">On Leave</form:option>
              	      							</form:select></div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Delegated Subordinate<span class="mandatory">*</span></div>
										<div class="quarter" >
											<form:select path="subordinateId" id="subordinateId" cssClass="formSelect" onmouseover="setSelectWidth('#subordinateId')">
												<form:option value="0">Select</form:option>
											</form:select>
										</div>
									</div>					
									
									<div class="line">
										<div class="quarter" style="margin-left:16.5%">
											<div><a href="javascript:resetRoleConfiguration('total');" class="appbutton" style="float:right;text-decoration: none">Clear</a></div>
											<div><a href="javascript:saveRoleConfiguration();" class="appbutton" style="float:right;text-decoration: none">Submit</a></div>
										</div>
									</div>
									<div class="line height"><hr/></div>
									<div class="line" id="displayTable">
											<jsp:include page="RoleSpecificConfigurationList.jsp"></jsp:include>								
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
		</form:form>		
	</body>
</html>
<!-- End:RoleSpecificConfiguration.jsp -->