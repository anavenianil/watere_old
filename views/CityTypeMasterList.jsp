<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin :CityTypeMasterList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div>
	<div>
	<jsp:include page="Result.jsp"></jsp:include>
	</div>
	<div>
	    <%int i=1; %>
		<div id="Pagination">
			<display:table name="${sessionScope.cityTypeList}" excludedParams="*"
				export="false" class="list" requestURI="" id="cityTypeList" pagesize="10"
				sort="list">
				<display:column title="Class Of City" style="width:40%" sortable="true" >&nbsp;${cityTypeList.cityClass}</display:column>
				<display:column title="Name Of City" style="width:25%">&nbsp;${cityTypeList.cityName}</display:column>			
				<display:column style="width:5%;text-align:center" title="Edit">
					<img src="./images/edit.gif"
						onclick="editCityType('${cityTypeList.id1}','${cityTypeList.cityClass}','${cityTypeList.cityName}');hilightRow(this);" />
				</display:column>
				<display:column style="width:5%;text-align:center" title="Delete">
					<img src="./images/delete.gif"
						onclick="deleteCityType('${cityTypeList.id1}')" />
				</display:column>
				<% i++; %>
			</display:table>
		</div>
		<script>
			$jq( function(){
					$jq("#Pagination").displayTagAjax('paging');
				})
		</script>
	</div>
</div>
<!-- End : CityTypeMasterList.jsp -->