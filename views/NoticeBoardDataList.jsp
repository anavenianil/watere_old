<!-- Begin : NoticeBoardDataList.jsp-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div>
	<div>
		<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
		<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
		<c:if test="${message=='update'}"> <span class="success"><spring:message code="update"/></span></c:if>
		<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
		<c:if test="${message=='deletefail'}"> <span class="failure"><spring:message code="recordexists"/>${sessionScope.reason}</span></c:if>
		<c:if test="${message=='delete'}"> <span class="failure"><spring:message code="delete"/></span></c:if>
	</div>
	
	<div>
	<font color="brown">Note:<br/>
	1. Save Action Taken As (Filed Or Replied) To Remove ION's From The List. <br/>
	2. Save Action Taken As 'Forward To Others' To Forward The IONs To Subordinates For Action And Finally Save As Filed/Replied To Remove From List. </font>
		<div id="Pagination">
		<%int i=0; %>
			<display:table name="${sessionScope.jsonNoticeBoardList}" excludedParams="*"
				export="false" class="list" requestURI="" id="dataList" pagesize="10"
				sort="list">
				
					<display:column  style="width:3%;vertical-align:middle" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll1(this.name,\'row\');"/>'>
					<input type="checkbox" value="${dataList.id}" class="row" name="ion" id="ion<%=i %>" <c:if test="${dataList.ionCirculationDetailsList != null && not empty dataList.ionCirculationDetailsList}">disabled="true"</c:if>/>
					</display:column>
				
					<display:column title="Subject" style="width:30%">${dataList.subject}</display:column>
					<%-- new 2 columns  forwaredList and Circulation are added --%>
					<display:column title="Forward List" style="width:20%">
					 <c:if test="${(dataList.createdBy ne null) && (dataList.createdBy eq '5')}">
					 <a style="color: blue">Initiated By :</a> ${dataList.ionInitiatedSfid}<br>
					 <a style="color: blue">Forward By :</a> ${dataList.ionForwardSfid}<br>
					 <a style="color: blue">Approved By :</a> ${dataList.ionApprovedSfid}<br>
					 </c:if>
					</display:column>
					<display:column title="Circulation" style="width:20%">
					 <c:if test="${(dataList.createdBy ne null) && (dataList.createdBy eq '5')}">
					 <a style="color: blue">CirculatedBy:</a>${dataList.lastModifiedBy}
					 </c:if>
					 </display:column>
					<display:column style="width:5%;text-align:center" title="ION">
						<a href="javascript:getPreview('${dataList.id}','pdf');">ION</a>
					</display:column>
					
					<c:if test="${sessionScope.userOrgRoleList != null}">				
					<display:column title="Edit Forward List" style="width:8%;vertical-align:middle">
					
			        <!-- <c:if test="${dataList.ionCirculationDetailsList != null && not empty dataList.ionCirculationDetailsList}"> </c:if> -->
					&nbsp;<a href="javascript:getEditable('ion<%=i %>',jsonNoticeBoardList)"><font color="blue">Edit</font></a>
					
					</display:column>
					</c:if>
					
					<display:column title="Remarks" style="width:15%;vertical-align:middle" >
					<c:forEach items="${dataList.ionCirculationDetailsList}" var="ion">${ion.remarks}
					</c:forEach>
					
					</display:column>
					
					
					
				  <display:column title="Action Taken" style="width:8%;vertical-align:middle" >
				    
			           <select name=savingType id="savingType<%=i%>" style="width:100px" onchange="javascript:enableDispatch(this,'ion<%=i %>',jsonNoticeBoardList)" >
				       <option value="select">Select</option>
				      <c:if test="${sessionScope.userOrgRoleList != null}">
				       <option value="forwarded" >Forward To Others </option>
				       </c:if>
				       <option value="filed">Filed</option>
				       <option value="replied">Replied</option>
		         	</select>
	            </display:column>
				<% i++; %>
				
				
				
				 
				
					
			</display:table>
			</div>
		<script>
			$jq( function(){
				   $jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
				   
				})
		    jsonNoticeBoardList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonNoticeBoardList") %>;	
		</script>
		
	</div>
</div>
<!-- End : NoticeBoardDataList.jsp -->