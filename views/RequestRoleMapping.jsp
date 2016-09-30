<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin : RequestRoleMapping.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="./script/RegExpValidate.js"></script>
<script type="text/javascript" src="./script/script.js"></script>
<link href="./styles/layout.css" rel="stylesheet" type="text/css" />
<link href="./styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/aa.js"></script>

    <script language="javascript" type="text/javascript">
        $jq(function() {
            $jq("#MoveRight,#MoveLeft").click(function(event) {
                var id = $jq(event.target).attr("id");
                var selectFrom = id == "MoveRight" ? "#SelectLeft" : "#SelectRight";
                var moveTo = id == "MoveRight" ? "#SelectRight" : "#SelectLeft";
 
                var selectedItems = $jq(selectFrom + " :selected").toArray();
                $jq(moveTo).append(selectedItems);
                selectedItems.remove;
            });
        });
    </script>  
<title>Application Roles Request Type Mapping</title>
</head>

<body>
	<form:form method="post" commandName="menu" id = "requestLinks">
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
								<div class="headTitle">Application Roles Request Type Mapping</div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</div>
								<%-- Content Page starts --%>
								<div>
									<div class="line" id="result">
										
									</div>
									<div class="line">
										<div class="quarter bold leftmar">Application Role Name<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="applicationRole" id="applicationRole" onchange="javascript:getRequesetRoleLinks();">
												<form:option value="select">Select</form:option>
												<form:options items="${sessionScope.applicationRolesList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
									</div>
									<div id="requestTypeList">
									<div class="line">
									<div class="line">
									<div class="leftmar">
												<div style="float: left; width: 38%;">Request Types</div>
												<div class="half" >Selected Request Types</div>
											</div>
									</div>
								   <div class="line">
											<div class="leftmar">
												<div style="float: left; width : 28%">
													<form:select path="fromID" id="SelectLeft" size="20" multiple="true" cssStyle="width:250px">
													    <form:options items="${sessionScope.requestTypeList}" itemValue="id" itemLabel="name"/>
													</form:select>
												</div>
												<div style="float: left; width : 10%; margin-top: 60px;">									
														<div style="margin-bottom: 5px;" align="center">
													          <input style="margin-bottom: 5px"id="MoveRight" type="button" value=" Add " />
      											              <input id="MoveLeft" type="button" value=" Remove " />    											        
      											        </div>		      																				
		      									</div>
												<div style="float: left; width : 35%">
													<form:select path="toID" id="SelectRight" size="20" multiple="true" cssStyle="width:250px">
													 <!-- <form:options items="${sessionScope.SelectedRolesList}" itemValue="id" itemLabel="name"/>  -->
													</form:select>
												</div>
											</div>
										
									</div>
									
									
							   </div>
							   </div>
									<div class="line">
										<div style="margin-left:25%">
										<a href="javascript:RequestSubmit();" class="appbutton">Submit</a>
										<a href="javascript:clearRequest();" class="appbutton">Clear</a>
										
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
		<form:hidden path="param" id="param" />
		<form:hidden path="type" id="type" />
		<form:hidden path="selectedLinks"/>
		</form:form>
	</body>
</html>
<!-- End : RequestRoleMapping.jsp -->