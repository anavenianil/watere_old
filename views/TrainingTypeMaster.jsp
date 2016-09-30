<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TrainingTypeMaster.jsp -->
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
<script type="text/javascript" src="script/trainingscript.js"></script>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript">
	$jq(document).ready(function(){
	
		lables(type);
		clearTrainingMaster(type);
		 
		});
</script>
<style>
.highlight {
font-style: italic;
background-color: #0f0;
}
</style>
</head>

<body>
	<form:form method="post" commandName="trainingMaster" name="trainingMaster" id="trainingMaster">
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
								<div class="headTitle" id="headTitle"></div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</div>
								<%-- Content Page starts --%>
								<div>
									<div class="line">
										
										<div class="quarter bold" id="labelType"></div>
										<div class="quarter"><form:input path="typeValue" id="typeValue" maxlength="50" /></div>
									</div>
									
									
									
									<div class="line">
										<div class="quarter bold" style="margin-left:8px;">Description</div>
										<div class="quarter"><form:textarea path="description" id="description" cols="20" rows="3" onkeypress="textCounter(event,$jq('#description'),$jq('#counter'),500);"
										 ></form:textarea>
											<input type="text" class="counter" name="counter" value="500" id="counter" disabled=""/>
										</div>
									</div>
									<div class="line">
										
										<div class="quarter bold" style="margin-left:8px;" id="Order No">Order No<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="orderNo" id="orderNo" maxlength="50" onkeypress="javascript:return checkInt(event)"/></div>
									</div>
									
									
									<div class="line">
										<div style="margin-left:27%;margin-right:5px;float:left;"><a id="createFundType" href="javascript:manageTrainingType('${sessionScope.trainingMstType}');"><div class="appbutton">Submit</div></a></div>
										<div><a id="createFundType" href="javascript:clearTrainingMaster('${sessionScope.trainingMstType}');"><div class="appbutton">Clear</div></a></div>  
																	
									</div>
									<div class="line height"><hr/></div>
									<div id="result" class="line">
										<jsp:include page="TrainingMasterDataList.jsp" />
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
		<form:hidden path="name" id="name"/>
		<form:hidden path="id" id="id"/>
		<form:hidden path="trainingType" id="trainingType"/>
		<form:hidden path="trainingTypeId" id="trainingTypeId"/>
				
		</form:form>
		<script type="text/javascript">
		
			var type='<c:out value='${sessionScope.trainingMstType}'/>';

		</script>
		



	</body>
</html>
<!-- End:TrainingTypeMaster.jsp -->