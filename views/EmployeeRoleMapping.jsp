<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:EmployeeRoleMapping.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>

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

<script src="script/jquery-1.9.1.js"></script>
<script src="script/ui/jquery.ui.core.js"></script>
<script src="script/ui/jquery.ui.widget.js"></script>
<script src="script/ui/jquery.ui.position.js"></script>
<script src="script/ui/jquery.ui.menu.js"></script>
<script src="script/ui/minified/jquery.ui.autocomplete.min.js"></script>
<link rel="stylesheet" type="text/css" href="styles/jquery-ui-1.10.3.custom.css" />  
<style>
	.ui-autocomplete {
		max-height: 300px;
		overflow-y: auto;
		/* prevent horizontal scrollbar */
		overflow-x: hidden;
	}
	/* IE 6 doesn't support max-height
	 * we use height instead, but this forces the menu to always be this tall
	 */
	* html .ui-autocomplete {
		height: 300px;
	}
	</style>

<title>Organisation Role</title>
</head>

<body>
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
								<div class="headTitle">Organisation Role</div>
								<!-- Content Starts -->
								<div class="line">								
									<div class="line">
										<div class="quarter leftmar">Search With<span class="mandatory">*</span></div>
										<div class="quarter">				
											<form:select path="searchingWith" id="searchingWith" cssClass="formSelect" onchange="javascript:searchOptions();">
												<form:option value="0">Select</form:option>
												<form:option value="sfid">EmployeeID</form:option>
												<form:option value="name">Name</form:option>
												<form:option value="dept">Department</form:option>
											</form:select>
										</div>
										<div class="quarter" id="selectNameDiv" style="display:none">	
										<form:input path="searchBox" id="searchBox" maxlength="50" />			
											
										</div>
										<div class="quarter" id="textBoxDiv" style="display:none">	
											<form:select path="searchingName" id="searchingName" cssClass="formSelect" onchange="javascript:searchName();">
												<form:option value="0">Select</form:option>
												<form:option value="contains">Contains</form:option>
												<form:option value="startWith">Start With</form:option>
											</form:select>
										</div>
										<div class="quarter" id="selectDeptDiv" style="display:none">	
											<form:select path="searchDept" id="searchDept" cssClass="formSelect" onmouseover="setSelectWidth('#searchDept')">
												<form:option value="0">Select</form:option>
												<form:options items="${orgStructure.departmentsList}" itemValue="id" itemLabel="deptName"/>
											</form:select>
										</div>										
									</div>	
									<div class="line">
										<div class="quarter leftmar">EmployeeID<span class="mandatory">*</span></div>
										<div class="quarter">				
											<form:input path="mapSfID" id="mapSfID" maxlength="50" />											
										</div>
									</div>
									<div class="line" id="roleEmpNameDiv" style="display:none">
										<div class="quarter leftmar">Employee Name<span class="mandatory">*</span></div>
										<div class="quarter" style="width:auto" id="roleEmpName"></div>
					
									</div>
									
									
									
									<div class="line">
										<div class="quarter leftmar">Roles<span class="mandatory">*</span></div>
										<div class="quarter">				
											<form:select path="roleName" id="roleName" cssClass="formSelect" onmouseover="setSelectWidth('#roleName')">
												<form:option value="0">Select</form:option>
												<form:options items="${orgStructure.rolesList}" itemValue="id" itemLabel="roleName"/>
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Is Default Role</div>
										<div class="quarter">				
											<form:checkbox path="isDefault" id="isDefault" value="1"/>											
										</div>
									</div>
									
									<div class="line">
										<div style="margin-left:25%">
											<div class="expbutton"><a href="javascript:searchDetails('role');"> <span>Search</span></a></div>
											<div class="expbutton"><a href="javascript:submitEmpRole();"> <span>Submit</span></a></div>
											<div class="expbutton"><a href="javascript:clearEmpRole();"> <span>Clear</span></a></div>
										</div>										
									</div>	
									<div class="line height"><hr/></div>
									<div class="line" id="displayTable">
										<jsp:include page="EmpRoleMappingList.jsp"></jsp:include>
									</div>		
								</div>
								<!-- Content Ends -->
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
		<script type="text/javascript">
		
		$(document).ready(function(){
        var url='empName.htm';
        var type="empName";
       $("#searchBox").autocomplete({source:url,
         messages: {
        noResults: '',
        results: function() {}
            }
        
       });
     });
		
		</script>
	</body>
</html>
<!-- End:EmployeeRoleMapping.jsp -->