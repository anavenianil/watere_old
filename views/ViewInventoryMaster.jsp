<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:ViewInventoryMaster.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html>
<head>

<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>

<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/jquery.autoSuggest.js"></script>
<script type="text/javascript" src="script/mmgViewScript.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/mmgscript.js"></script>
<script type="text/javascript" src="script/json2_mini.js"></script>
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link href="styles/autoSuggest.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<form method="post" id="viewMaster">
<input type="hidden" name="param"/>
<input type="hidden" name="type"/>
<input type="hidden" name="id" id="id"/>
<input type="hidden" name="changedValues"/>
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
								<div style="padding: 10px 0 0 0;"></div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</div>
								<div id="documentheadtitle" class="headTitle"></div>
								<div id="result"></div>
								<div id="newbutton" class="appbutt1" style="display:none;margin-left:89%" ></div> 
								<div id="viewData" class="line"></div>
								<div id="viewMasterData" class="line"></div>
									<%-- Content Page starts --%>
									
									<script>
									var values=<%= (net.sf.json.JSONObject)session.getAttribute("masterjson") %>;
									PopulateValues(values);</script>
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
			
</form>
</body>
</html>
<!-- end:ViewViewInventoryMaster.jsp -->