<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin : quarterGradePayDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="./script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/pisscript.js"></script>
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
<title>Quarter GradePay Mapping</title>
</head>

<body>
	<form:form method="post" commandName="quarterRequest" id="quarterRequest">
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
								<div class="headTitle">Quarter GradePay Mapping</div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</div>
								<%-- Content Page starts --%>
								<div>
									<div class="line" id="result">
										
									</div>
									<div class="line">
										<div class="quarter bold leftmar">Quarter Type<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="quarterType" id="quarterType" onchange="javascript:getSelQuarterTypes();">
												<form:option value="select">Select</form:option>
												<form:options items="${sessionScope.QuarterTypeList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
									</div>
									<div class="line">
									<table border="0">
										<tr>
											<td style="font-weight: bold;" colspan="2">De Selected GradePays</td>
											<td style="font-weight: bold;">Selected GradePays</td>
										</tr>
										<tr>
											<td>
												<form:select path="gradePayValueId" id="SelectLeft" size="15" multiple="true" cssStyle="width:400px">
													<form:options items="${sessionScope.GradePayList}" itemValue="gradePayID" itemLabel="gradePayID"/>
												</form:select>
											</td>
											<td width="50px" align="center">
												<input style="margin-bottom: 5px" id="MoveRight" type="button" value=" Add " />
      											<input id="MoveLeft" type="button" value=" Remove " />
											</td>
											<td>
												<form:select path="gradePayValueId" id="SelectRight" size="15" multiple="true" cssStyle="width:400px"></form:select>
											</td>
										</tr>
									</table>
								</div>
									<div class="line" id="result1">
		
									</div>
									<div class="line">
										<div style="margin-left:80%">
											<div class="expbutton"><a href="javascript:saveQuarterGradePays();"><span>Submit</span></a></div>
											<div class="expbutton"><a href="javascript:clearMapping();"><span>Clear</span></a></div>
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
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		<form:hidden path="selectedGradePays"/>
		</form:form>
	</body>
</html>
<!-- End : quarterGradePayDetails.jsp -->