<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:EmpSearchDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Employee Search</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/jquery-ui-1.10.3.custom.css" />  
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script language="javascript" src="script/aa.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>

<script src="script/jquery-1.9.1.js"></script>
<script src="script/ui/jquery.ui.core.js"></script>
<script src="script/ui/jquery.ui.widget.js"></script>
<script src="script/ui/jquery.ui.position.js"></script>
<script src="script/ui/jquery.ui.menu.js"></script>
<script src="script/ui/minified/jquery.ui.autocomplete.min.js"></script>
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


</head>

<body>
	<form:form method="post" commandName="employee">
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		<form:hidden path="id" id="id"/>
		<form:hidden path="empSearchId" id="empSearchId"/>
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
								<div class="headTitle">Employee Search</div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</div>
								<%-- Content Page starts --%>
									<div class="line">
										<div class="line">
												<div class="quarter bold leftmar">Employee ID</div>
												<div class="quarter"  style="text-align:left"><form:input path="sfid" id="sfid"/></div>
										</div>
										<div class="line">
												<div class="quarter bold leftmar">Employee Name</div>
												<div class="quarter">
												<div class="quarter"  style="text-align:left"><form:input path="firstName" id="empname" onkeypress="javascript:return isAlphabetExp (event);"/></div>
												</div>
												<form:select path="searchWith" id="searchwith" cssClass="formSelect" >
													<form:option value="">Select</form:option>
													<form:option value="start">Starts with</form:option>
													<form:option value="contain">Contains</form:option>
												</form:select>
												
										</div>
										<div class="line">
												<div class="quarter bold leftmar">Designation</div>
												<div class="quarter"  style="text-align:left">
												<form:select path="designationId" id="desigId" cssClass="formSelect" onmouseover="setSelectWidth('#desigId')">
															<form:option value="">Select</form:option>
		               	      								<form:options items="${designationList}" itemValue="id" itemLabel="name"/>
		               	   						</form:select></div>
												
										</div>
										<div class="line">
											<div style="margin-left:25%">
												<div  class="appbutton"><a class="quarterbutton" href="javascript:getSearchDetails()">Search</a></div>
												<div  class="appbutton"><a class="quarterbutton" href="javascript:clearSearchDetails()">Clear</a></div>
											</div>
										</div>
										
										
										
										<div id="empSearchList">
											<c:if test="${employee.param eq 'view'}">
												<jsp:include page="EmpSearchList.jsp"/>
											</c:if>
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
		
		</form:form>
		<script type="text/javascript">
		
		$(document).ready(function(){
        var url='empName.htm';
        var type="empName";
       $("#empname").autocomplete({source:url,
         messages: {
        noResults: '',
        results: function() {}
            }
        
       });
     });
		
		</script>
		
	</body>
</html>
<%-- End:EmpSearchDetails.jsp --%>
