<fieldset>    									
				<div class="line">
						<div class="quarter">Inventory Number</div>
						<div class="quarter">
							<form:select path="inventoryNo" cssStyle="width:145px" id="inventoryNo" onchange="javascript:getInventoryHolderDetails();">
								<form:option value="" disabled="disabled">Select</form:option>
								<c:forEach var="invholder" items="${command.invList}">
		 <form:option value="${invholder.invId}">${invholder.inventoryNo}</form:option>
		</c:forEach>														
	</form:select>
	</div>
				<div class="quarter">Inventory Holder Name</div>
				<div class="quarter" id="holderName"></div>
				
		</div>
		<div class="line">
				<div class="quarter">Directorate</div>
				<div class="quarter" id="directorateName">&nbsp;</div>
				<div class="quarter">Division</div>
				<div class="quarter" id="divisionName">&nbsp;</div>
		</div>
		<div class="line">
				<div class="quarter">Project</div>
				<div class="quarter">
					<form:select path="projectCode" cssStyle="width:145px">
					<form:option value="" disabled="disabled">Select</form:option>
						<c:forEach var="project" items="${command.projectList}">
							 <option value="${project.id}">${project.projectName}</option>
						</c:forEach>
					</form:select>
				</div>
				<div class="quarter">Demand Type</div>
				<div class="quarter">
					<form:select path="demadTypeId" cssStyle="width:145px">
					<form:option value="" disabled="disabled">Select</form:option>
						<c:forEach var="demandType" items="${command.demandTypeList}">
							 <option value="${demandType.id}">${demandType.demandTypeName}</option>
						</c:forEach>
					</form:select>
				</div>												
		</div>
		<div class="line">
				<div class="quarter">Account Head</div>
				<div class="quarter">
					<form:select path="accountHeadId" cssStyle="width:145px" id="accountHeadId">
						<form:option value="" disabled="disabled">Select</form:option>
						<c:forEach var="accountHead" items="${command.accountHeadList}">
							 <option value="${accountHead.id}">${accountHead.description}</option>
						</c:forEach>
					</form:select>
				</div>
				<div class="quarter">Group Demand Number</div>
				<div class="quarter" id="groupDemandNo">&nbsp;</div>
				
		</div>
		<div class="line">
				<div class="quarter">Group Demand Date</div>
				<div class="quarter">${command.demandDate}</div>
		</div>									
		</fieldset>
		<div class="line"><jsp:include page="ItemDetails.jsp" /></div>