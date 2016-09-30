<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/spring-form" prefix="form"%>	
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/tabs.css" rel="stylesheet" type="text/css" />
<div class="line">
<div class="line">
<fieldset>
<div class="line">
<div class="quarter bold">SFID :</div>
<div class="quarter"><spring:bind path="arrears"><form:input path="arrears.userSfid" size="5" readonly="true"/></spring:bind> </div>
<div class="quarter bold">Name :</div>
<div class="quarter"><spring:bind path="arrears">${arrears.empName}</spring:bind></div>
</div>
<div class="line">
<div class="quarter bold">Designation :</div>
<div class="quarter"><spring:bind path="arrears">${arrears.designation}</spring:bind></div>
<div class="quarter bold">Basic & Grade :</div>
<div class="quarter"><spring:bind path="arrears">${arrears.basicPay} & ${arrears.gradePay}</spring:bind></div>
</div>
<div class="line">
<div class="quarter bold">FPA Flag :</div>
<div class="quarter"><spring:bind path="arrears">${arrears.fpaFlag}</spring:bind></div>
<div class="quarter bold">FPA GradePay :</div>
<div class="quarter"><spring:bind path="arrears">${arrears.fpaGradePay}</spring:bind></div>
</div>
</fieldset>
</div>
<div class="line">
<fieldset>
<div class="line">
<div class="half">
<c:if test='${arrears.message eq "arrearsGiven" ||arrears.message eq "success"}'>
<font  color="green">${arrears.remarks}</font>
</c:if>
<div class="line">
<display:table id="list" list="${arrears.arrearsStatusList}" class="list" style="width:75%">
<display:column title="Month" style="width:0.6%;text-align:center">${list.arrearsMonth}</display:column>
<display:column title="Due" style="width:0.6%;text-align:center">${list.dueFpa}</display:column>
<display:column title="Drawn" style="width:0.3%;text-align:center">${list.drawnFpa}</display:column>
</display:table>
</div>
<div class="line">
<c:if test='${arrears.message eq "success" }'>
<a class="appbutton" style="margin-left:60%" onclick="javascript:submitFPAArrearsDetails();">Submit</a>
</c:if>
<c:if test='${arrears.message eq "notApplied" }'>
<font  color="red">${arrears.remarks}</font>
</c:if>
</div>
</div>
<div class="half">
<div class="line">&nbsp;</div>
<div class="line">
<div class="quarter">&nbsp;</div>
<div class="half bold">Operation Date:</div>
<div class="quarter"><spring:bind path="arrears">${arrears.adminAccDate}</spring:bind></div>
</div>
<div class="line">
<div class="quarter">&nbsp;</div>
<div class="half bold">Operation Location:</div>
<div class="quarter"><spring:bind path="arrears">${arrears.opLocation}</spring:bind></div>
</div>
<div class="line">
<div class="quarter">&nbsp;</div>
<div class="half bold">Effective Month:</div>
<div class="quarter"><spring:bind path="arrears">${arrears.financeAccDate}</spring:bind></div>
</div>
<div class="line">
<div class="quarter">&nbsp;</div>
<div class="half bold">Total Arrears:</div>
<div class="quarter"><spring:bind path="arrears">${arrears.totalArrears}</spring:bind></div>
</div>
</div>
</div>
</fieldset>
</div>
</div>