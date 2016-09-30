<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : LetterNumberList.jsp -->
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
	
			<display:table name="${sessionScope.letterNumberList}" excludedParams="*"
				export="false" class="list" requestURI="" id="desigList" pagesize="10"
				sort="list">
				<display:column title="LetterDate" style="width:5%">&nbsp;${desigList.letterDate}</display:column>
				<display:column title="LetterNumber" style="width:20%" sortable="true">&nbsp;${desigList.letterNumber}</display:column>
				
				<display:column title="Description" style="width:5%">&nbsp;${desigList.description}</display:column>
				
				<display:column title="From" style="width:5%">&nbsp;${desigList.fromPalce}</display:column>
				<display:column title="To" style="width:15%">&nbsp;${desigList.toPalce}</display:column>
				<display:column title="Remarks" style="width:5%">&nbsp;${desigList.remarks}
				</display:column>
                <display:column style="width:5%;text-align:center" title="Edit">
			  <img src="./images/edit.gif" title="Edit"
				onclick="editLetterNumberReference('${desigList.letterDate}','${desigList.letterNumber}','${desigList.description}','${desigList.fromPalce}','${desigList.toPalce}','${desigList.remarks}','${desigList.status}','${desigList.id}')" />
		     </display:column>
		       <display:column style="width:5%;text-align:center" title="Delete">
			     <img src="./images/delete.gif" title="Delete"
				onclick="deleteLetter(${desigList.id})" />
		        </display:column>			
		 	</display:table>
			</div>
		<script>
			$jq( function(){
				   $jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
				   
				})
		
		
			
			
			
		</script>
		
	</div>
</div><!-- End : LetterNumberList.jsp -->