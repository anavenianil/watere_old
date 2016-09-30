<!-- Begin : LocalAssessmentBoardList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<div>
	<jsp:include page="Result.jsp"></jsp:include>
</div>
<div id="Pagination">
	<display:table name="${sessionScope.localAssessmentBoardList}" excludedParams="*"
		export="false" class="list" requestURI="" id="boardList" pagesize="10"
		sort="list">
		<display:column title="Year" style="width:10%" sortable="true">${boardList.year}</display:column>
		<display:column title="Board Name" style="width:25%">&nbsp;	${boardList.boardName}		</display:column>
		<display:column title="Board Members" style="width:45%;">&nbsp;	${boardList.employees}		</display:column>
		<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit"
				onclick="editLocalBoardDetails('${boardList.boardID}','${boardList.yearID}','${boardList.boardName}','${boardList.members}','${boardList.categoryId}')" />
		</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete"
				onclick="deleteLocalBoardDetails('${boardList.boardID}','${boardList.yearID}','${boardList.categoryId}')" />
		</display:column>				
	</display:table>
	<script>
		$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
	</script>
</div>
<!-- End : LocalAssessmentBoardList.jsp -->