<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/spring-form" prefix="form"%>	
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/fmt"  prefix="fmt"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/tabs.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/arrears.js"></script>
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
</div>
</fieldset>
</div>
<div class="line">
<fieldset>
<div class="half">
<display:table name="${arrears.arrearsStatusList}" excludedParams="*"
				export="false" class="list" requestURI="" id="arrears" pagesize="1000"
				sort="list" cellpadding="2" cellspacing="1">
				 <display:column title="FROM" style="width:0.7%;vertical-align:middle">${arrears.fromDesignation}</display:column>
				 <display:column title="TO" style="width:0.7%;vertical-align:middle">${arrears.toDesignation}</display:column>
				 <display:column title="ACTUAL" style="width:0.6%;vertical-align:middle">${arrears.adminAccDate}</display:column>
				 <display:column title="CONSIDERED" style="width:0.6%;vertical-align:middle">${arrears.financeAccDate}</display:column>
				 <display:column title="" style="width:0.6%;vertical-align:middle"><div  class="appbutton" onclick="javascript:getPromotionArrearsPreviewList('${arrears.adminAccDate}','${arrears.financeAccDate}','${arrears.assessmentId}');">preview</div></display:column>
	 </display:table>
	 </div>
</fieldset>
</div>
<div class="line" id="previewList"></div>


