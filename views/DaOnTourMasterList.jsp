<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin :DaOnTourMasterList.jsp -->
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
		<div id="Pagination">
			<display:table name="${sessionScope.daOnTourList}" excludedParams="*"
				export="false" class="list" requestURI="" id="tourDaList" pagesize="7"
				sort="list">
				<display:column title="DA Range From" style="width:40%" sortable="true" >&nbsp;${tourDaList.daRangeFrom}%</display:column>
				<display:column title="DA Ranage To" style="width:25%">&nbsp;${tourDaList.daRangeTo}%</display:column>
				<display:column title="DA On Tour" style="width:25%;text-align:right">&nbsp;${tourDaList.daOnTour}%</display:column>			
				<display:column style="width:5%;text-align:center" title="Edit">
					 <img src="./images/edit.gif"
						onclick="editDaOnTour('${tourDaList.id}','${tourDaList.daRangeFrom}','${tourDaList.daRangeTo}','${tourDaList.daOnTour}');hilightRow(this);" />
				</display:column>
				<display:column style="width:5%;text-align:center" title="Delete">
					<img src="./images/delete.gif"
						onclick="deleteDaOnTour('${tourDaList.id}')" /> 
				</display:column>
				
			</display:table>
		</div>
		<script>
			$jq( function(){
					$jq("#Pagination").displayTagAjax('paging');
				})
		</script>
	</div>
</div>
<!-- End : DaOnTourMasterList.jsp -->