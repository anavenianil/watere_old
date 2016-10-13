<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:MasterData.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link href="./styles/layout.css" rel="stylesheet" type="text/css" />
<link href="./styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>

</head>

<body onload="javascript:lables('${sessionScope.masterType}');">
	<form:form method="post" commandName="master" name="master" id="master">
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
									
									<c:if test="${sessionScope.level eq 'parent'}">
										<div class="line">
											<div class="quarter bold" style="margin-left:8px;" id="parentLable" ><!-- State -->Region Name</div>
											<div class="quarter">
												<form:select path="parentID" id="parentID" cssClass="formSelect" onmouseover="setSelectWidth('#parentID')" >
													<form:option value="select" >Select</form:option>
													<form:options items="${sessionScope.parentList}" itemValue="id" itemLabel="name"/>
												</form:select>
											</div>
										</div>
									</c:if>
									<c:if  test="${sessionScope.masterType == 'category' || sessionScope.masterType == 'subcategory'}">
									<div class="line">
										<div class="quarter bold" style="margin-left:8px;">Order No<span class="mandatory">*</span></div>
										<div><form:input path="orderNo" id="orderNo" onkeypress="javascript:return checkInt(event)"/></div>
									</div>
									</c:if>
									<div class="line">
										<div class="quarter bold" id="labelType"></div>
										<c:choose>
										<c:when test="${sessionScope.masterType != 'year'}">
										<div class="quarter"><form:input path="typeValue" id="typeValue" maxlength="50" onkeypress="javascript:increaseTextWidth('typeValue'); return isAlphabetExp(event)" /></div>
										</c:when>
										<c:otherwise>
										<div class="quarter"><form:input path="typeValue" id="typeValue" maxlength="4" onkeypress="javascript:return checkInt(event)" /></div>
										</c:otherwise>
										</c:choose>
									</div>
									<c:if test="${sessionScope.masterType == 'category' || sessionScope.masterType == 'subcategory'}">
									<div class="line">
										<div class="quarter bold" style="margin-left:8px;">Alias</div>
										<div><form:input path="alias" id="alias" /></div>
									</div>
									</c:if>
									<div class="line">
										<div class="quarter bold" style="margin-left:8px;">Description</div>
										<div class="quarter"><form:textarea path="description" id="description" cols="20" rows="3" onkeypress="textCounter(this,document.forms[0].counter,500);" onkeyup="textCounter(this,document.forms[0].counter,500);"></form:textarea>
											<input type="text" class="counter" name="counter" value="500" id="counter" disabled=""/>
										</div>
									</div>
									<div class="line">
										<div style="margin-left:29%;"><a href="javascript:manageMaster('${sessionScope.masterType}');"><div class="appbutton">Submit</div></a></div>
										<div><a href="javascript:clearMaster();"><div class="appbutton">Clear</div></a></div>
									</div>
									<div class="line height"><hr/></div>
									<div id="result" class="line">
										<jsp:include page="MasterDataList.jsp" />
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
		<form:hidden path="name"/>
		</form:form>
		<script>
			clearMaster();
		</script>
	</body>
</html>
<!-- End:MasterData.jsp -->