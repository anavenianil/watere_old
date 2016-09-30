<!-- Begin : CghsAdvanceDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<script type="text/javascript" src="script/ajaxUpload.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<div>
	<div class="line">
		<div class="quarter leftmar">Estimation Amount</div>
		<div class="quarter">&nbsp;<font size="4.5em"><span class="WebRupee" >R</span></font>${workflowmap.cghsAdvanceDTO.estimationAmount}/-</div>
		<c:if test="${not empty workflowmap.cghsAdvanceDTO.issuedAmount}">
			<div class="quarter leftmar">Finance Issued Amount</div>
			<div class="quarter"><font size="4.5em"><span class="WebRupee" >R</span></font>${workflowmap.cghsAdvanceDTO.issuedAmount}/-</div>
		</c:if>
		</div>
		<div class="line">
		<c:if test="${not empty workflowmap.cghsAdvanceDTO.cdaAmount}">
			<div class="quarter leftmar">CDA Issued Amount</div>
			<div class="quarter"><font size="4.5em"><span class="WebRupee" >R</span></font>${workflowmap.cghsAdvanceDTO.cdaAmount}/-</div>
		</c:if>
		<c:if test="${empty workflowmap.cghsAdvanceDTO.cdaAmount && not empty workflowmap.cghsAdvanceDTO.issuedAmount}">
			<div class="quarter leftmar">CDA Issued Amount</div>
			<div class="quarter"><font color="red">CDA Amount is in processing</font></div>
		</c:if>
		
		</div>
		<div class="line">
		<fieldset><legend><strong><font color='red'>Note for Individual</font></strong></legend>
			<div class="line">1). Submit the following copies to finance <br>
					<div id="NoFiles" class="line">
					<div style="width: 450px;  margin-left: 20px;">
					<div  class="fvireq">
				<c:if test="${ not empty workflowmap.cghsAdvanceDTO.cghscardref}">
					a) Copy of CGHS card<a href="javascript:showDatabaseFile('${workflowmap.cghsAdvanceDTO.cghscardref}')">View</a>
				</c:if>
					<c:if test="${empty workflowmap.cghsAdvanceDTO.cghscardref}">
				    a) Copy of CGHS card<a href="javascript:showDatabaseFile('${workflowmap.cghsAdvanceDTO.cghsCardFile}')">View</a>
				</c:if>
					</div>
					<div class="svireq">
					<c:if test="${ (workflowmap.statusMsg  !='COMPLETED')&&(workflowmap.statusMsg  !='CANCELLED')&& (workflowmap.tdSettStrStatus !='COMPLETED') &&(workflowmap.cghsAdvanceDTO.cghscardref)}">
					<input type="file" id="files1" name="cghsCardFile" value=""/></c:if></div>
					<div  class="fvireq">
					b) Estimation bill<a href="javascript:showDatabaseFile('${workflowmap.cghsAdvanceDTO.estimationFile}')">View</a>
					</div>
					<div class="svireq">
					<c:if test="${ (workflowmap.statusMsg  !='COMPLETED')&&(workflowmap.statusMsg  !='CANCELLED')&& (workflowmap.tdSettStrStatus !='COMPLETED')}">
					<input type="file" id="files2" name="estimationFile" value=""/></c:if></div>
                    </div>
              </div>
              </div>
			<div class="quarter">
				 <div style="margin-left:45%">
				  <c:if test="${ (workflowmap.statusMsg  !='COMPLETED')&&(workflowmap.statusMsg  !='CANCELLED')&& (workflowmap.tdSettStrStatus !='COMPLETED')}">
					<a href="javascript:manageUploadedFiles('${workflowmap.cghsAdvanceDTO.cghsCardFile}','${workflowmap.cghsAdvanceDTO.estimationFile}','','');"><div class="appbutton" id="button" style="display:show">Upload</div></a>
		     	  </c:if>
		     	</div>
			</div>
		</fieldset>
		
			<div class="line">
		<div class="quarter">
				 <div>
				    
				 <c:if test="${ not empty workflowmap.cghsAdvanceDTO.reimbursementId}">
				 
     <div class="quarter leftmar">settlement RequestId:
       <div class="quarter">  <a href="javascript:getRequestDetails('${workflowmap.cghsAdvanceDTO.historyId}','${workflowmap.cghsAdvanceDTO.reimbursementId}','myRequests','completed','')"> ${workflowmap.cghsAdvanceDTO.reimbursementId}</a></div>
		</div>
	</c:if>
		     	</div>
		     	
		     	
			</div>
			</div>
			
			
		<input type="hidden" name="cghsCardFileName" id="cghsCardFileName"/>
		<input type="hidden" name="estimationFileName" id="estimationFileName"/>
		
	 <input type="hidden"  id="statusMsg"/>
		<input type="hidden"  id="historyID"/> 
		
		
		
	</div>
								 
</div> 
<!-- End : CghsAdvanceDetails.jsp -->