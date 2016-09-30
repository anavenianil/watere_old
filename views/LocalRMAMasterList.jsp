<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin :LocalRMAMasterList.jsp -->
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
			<display:table name="${sessionScope.localRmaList}" excludedParams="*"
				export="false" class="list" requestURI="" id="localRmaList" pagesize="7"
				sort="list">
				<display:column title="From Place" style="width:40%" sortable="true" >&nbsp;${localRmaList.fromPlace}</display:column>
				<display:column title="To Place" style="width:25%">&nbsp;${localRmaList.toPlace}</display:column>
				<display:column title="Distance" style="width:25%;text-align:right">&nbsp;${localRmaList.localrmadistance}</display:column>			
				<display:column style="width:5%;text-align:center" title="Edit">
					<img src="./images/edit.gif"
						onclick="editLocalRMA('${localRmaList.id}','${localRmaList.fromPlace}','${localRmaList.toPlace}','${localRmaList.localrmadistance}');hilightRow(this);" />
				</display:column>
				<display:column style="width:5%;text-align:center" title="Delete">
					<img src="./images/delete.gif"
						onclick="deleteCLocalRMA('${localRmaList.id}')" />
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
<!-- End : LocalRMAMasterList.jsp -->