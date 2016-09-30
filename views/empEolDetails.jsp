<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : empEolDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>

<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />

<div class="line">
    
	<div class="line">
		<div id="Pagination">
			<display:table name="${sessionScope.payeolList}" excludedParams="*"
				export="false" class="list" requestURI="" id="leaveList" pagesize="30"
				sort="list">				 		
				<display:column  style="width:5%;text-align:center" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'row\');"/>'>
					<input type="checkbox" class="row" name="row" id="${leaveList.id}" onclick="checkBoxCheck(this.name);"/>
				</display:column>
				<display:column title="Do Part No" style="width:5%">&nbsp;${leaveList.key}</display:column>
				<display:column title="Do Part Date" style="width:5%">&nbsp;${leaveList.value}</display:column>
				<display:column title="Leave Type" style="width:20%">&nbsp;${leaveList.flag}</display:column>
				<display:column title="No of Days" style="width:15%">&nbsp;${leaveList.name}</display:column>
				</display:table>			
		</div>		
	</div>	
	<div class="line"><div class="quarter"><a href="javascript:acceptEMPEol();" class="appbutton">Save</a></div></div>
	<script>
			$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
	</script>
</div>
<!-- End : empEolDetails.jsp -->