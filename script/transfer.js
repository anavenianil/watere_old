function getEmployees() {
	$jq('#transferedSFID').empty().append('<option value="0">Select</option>')
			.find('option:first').attr("selected", "selected");

	var requestDetails = {
		"departmentID" : $jq("#departmentID").val(),
		"param" : "getEmployees"
	};
	$jq.ajax( {
		type : "POST",
		url : "transfer.htm",
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq("#result").html(html);

			for ( var k = 0; k < jsonArrayObject.length; k++) {
				$jq('#transferedSFID').append(
						$jq("<option></option>").attr("value",
								jsonArrayObject[k][0]).text(
								jsonArrayObject[k][1]));
			}
		}
	});
}

function displayTransferedTo() {
	$jq("#reqDepartment").val("0");
	$jq("#otherLab").val("");
	if ($jq("#transferType").val() == internal) {
		$jq("#external").hide();
		$jq("#internal").show();
		$jq("#attachmentDiv").hide();
	} else if ($jq("#transferType").val() == external) {
		$jq("#internal").hide();
		$jq("#external").show();
		$jq("#attachmentDiv").show();
	} else {
		$jq("#internal").hide();
		$jq("#external").hide();
		$jq("#attachmentDiv").hide();
	}
}

function resetTransfer(type) {
	$jq("#departmentID").val("0");
	$jq("#transferedSFID").val("0");
	$jq("#transferType").val("0");
	$jq("#reqDepartment").val("0");
	$jq("#otherLab").val("");
	if (type == 'self') {
		$jq("#internal").hide();		
	}
	$jq("#external").hide();
	$jq("#attachmentDiv").hide();
	$jq("#reason").val("");
	$jq("#attachmentFile").val("");
}

function submitTransfer(type) {
	var errorMessage = "";
	var status = true;
	var requestType = "";
	var departmentTo = "";
	if (type == 'employee') {
		if ($jq("#transferedSFID").val() == "0") {
			errorMessage += "Please select Employee\n";
			status = false;
			$jq("#transferedSFID").focus();
		}
		if ($jq("#reqDepartment").val() == "0") {
			errorMessage += "Please select transfer department\n";
			if (status) {
				status = false;
				$jq("#reqDepartment").focus();
			}
		}
	} else {
		if ($jq("#transferType").val() == "0") {
			errorMessage += "Please select transfer type\n";
			if (status) {
				status = false;
				$jq("#transferType").focus();
			}
		} else if ($jq("#transferType").val() == internal) {
			if ($jq("#reqDepartment").val() == "0") {
				errorMessage += "Please select transfer department\n";
				if (status) {
					status = false;
					$jq("#reqDepartment").focus();
				}
			}
		} else if ($jq("#transferType").val() == external) {
			if ($jq("#otherLab").val() == "") {
				errorMessage += "Please enter other lab name\n";
				if (status) {
					status = false;
					$jq("#otherLab").focus();
				}
			}
		}
	}
	if ($jq("#reason").val() == "") {
		errorMessage += "Please enter reason\n";
		if (status) {
			status = false;
			$jq("#reason").focus();
		}
	}

	if (status) {
		if ($jq("#reqDepartment").is(':visible')) {
			departmentTo = $jq("#reqDepartment").val();
		} else {
			departmentTo = $jq("#otherLab").val();
		}

		var requestDetails = {
			"transferedSFID" : $jq("#transferedSFID").val(),
			"transferType" : $jq("#transferType").val(),
			"departmentTo" : departmentTo,
			"reason" : $jq("#reason").val(),
			"type" : type,
			"param" : "submit"
		};

		if ($jq("#attachmentFile").is(':visible')) {
			fileUpload('attachmentFile',requestDetails,type);			
		} else {
			sendTransferRequest(requestDetails,type);
		}
	} else {
		alert(errorMessage);
	}
}


function fileUpload(fileID, requestDetails,type) {
	$jq.ajaxFileUpload( {
		url : "file.htm?param=upload&type=transfer",
		secureuri : false,
		fileElementId : fileID,
		success : function(data, status) {
			sendTransferRequest(requestDetails,type);
		},
		error : function(data, status, e) {
			alert('File Upload failed');
		}
	});
}

function sendTransferRequest(requestDetails,type) {
	$jq.ajax( {
		type : "POST",
		url : "transfer.htm",
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq("#result").html(html);
			
			for ( var k = 0; k < jsonArrayObject.length; k++) {
				$jq('#transferedSFID').append(
						$jq("<option></option>").attr("value",
								jsonArrayObject[k][0]).text(
								jsonArrayObject[k][1]));
			}
			resetTransfer(type);
		}
	});
}

function clearTransferTxnDetails(){
	$jq("#transferedSFID").val('');
	$jq('#fromDept').find('option').remove().end();
	$jq("#fromDept").append($jq("<option></option>").attr("value",'').text('Select'));
	$jq("#toDept").val('');
	$jq('#assignedTo').find('option').remove().end();
	$jq("#assignedTo").append($jq("<option></option>").attr("value",'').text('Select'));
	$jq("#doPartDate").val('');
	$jq("#doPartNo").val('');
	$jq('#doPartNo').find('option').remove().end();
	$jq("#doPartNo").append($jq("<option></option>").attr("value",'').text('Select'));
	$jq("#gazettedType").val('Select');
	$jq('#result').text('');
}
function editTransferTxnDetails(sfid,fromDept,toDept,assignedTo,deptName,doPartId){
	$jq("#transferedSFID").val(sfid);
	$jq('#fromDept').find('option').remove().end();
	$jq("#fromDept").append($jq("<option></option>").attr("value",fromDept).text(deptName));
	$jq("#toDept").val(toDept);
	editDoPart(doPartId);
	getOrgRoleName(assignedTo);
}
function editDoPart(doPartId){
	if(doPartJSON!=null){
		for ( var k = 0; k < doPartJSON.length; k++) {
			if(doPartJSON[k].id==doPartId){
			$jq('#doPartNo').val(doPartJSON[k].doPartNumber);
			$jq('#doPartNo').find('option').val(doPartJSON[k].id).text(doPartJSON[k].doPartNumber);
			$jq('#doPartDate').val(doPartJSON[k].dOPartDate);
			if(doPartJSON[k].gazType=='G')
			$jq("#gazettedType").val('GAZETTED');
			else if(doPartJSON[k].gazType=='NG')
				$jq("#gazettedType").val('NONGAZETTED');

			}
		}
	}
}
function manageTransferTxnDetails(){
	var errorMsg="";
	var status=true;
	if($jq("#transferedSFID").val()== ""){
		errorMsg+="Please Enter SFID";
		status=false;
	}else if($jq("#fromDept").val()== ""){
		errorMsg+="Please Select Departement From";
		status=false;
	}else if($jq("#toDept").val()== ""){
		errorMsg+="Please Select Departement To";
		status=false;
	}else if($jq("#assignedTo").val()== ""){
		errorMsg+="Please Select Assigned To";
		status=false;
	}else if($jq("#doPartDate").val()== ""){
		errorMsg+="Please DO Part Date";
		status=false;
	}else if($jq("#doPartNo").val()==""){
		errorMsg+="Please DO Part Number";
		status=false;
	}
	if(status){
	var requestDetails={
		param			:	"manageTransTxn",
		transferedSFID 	:	$jq("#transferedSFID").val(),
		fromDept		:	$jq("#fromDept").val(),
		toDept			:	$jq("#toDept").val(),
		assignedTo		:	$jq("#assignedTo").val(),
		doPartDate		:	$jq("#doPartDate").val(),
		doPartNo		:	$jq("#doPartNo").val()
	}
	$jq.ajax({
		type : "POST",
		url : "transfer.htm",
		data : requestDetails,
		cache : false,
		success : function(html) {
		$jq("#TransferTxnList").html(html);
		clearTransferTxnDetails();
	}
	});
	}else{
		alert(errorMsg);
	}
}
function deleteTransferTxnDetails(doPartId){
	var requestDetails={
			param 	:	"deleteTransTxn",
			doPartID:	doPartId
		}
		$jq.ajax({
			type : "POST",
			url : "transfer.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
			$jq("#TransferTxnList").html(html);
			clearTransferTxnDetails();
		}
		});
}
function checkDepartmetns() {
	$jq('#fromDept').find('option').remove().end();
	var dept = '';
	var status = true;
	var requestDetails = {
		param : "getDepartments",
		transferedSFID : $jq("#transferedSFID").val().toUpperCase()
	}
	$jq.ajax( {
		type : "POST",
		url : "transfer.htm",
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq('#result').html(html);
		}
	});
}
function getDepartments(msg) {
	$jq('#fromDept').find('option').remove().end();
	if (msg == 'employeeexists') {
		for ( var k = 0; k < deptJSON.length; k++) {
			$jq('#fromDept').append(
					$jq("<option></option>").attr("value", deptJSON[k].id)
							.text(deptJSON[k].deptName));
		}
	}
}

function getOrgRoleName(roleID){
	$jq('#assignedTo').find('option').remove().end();
	if(orgRoleJSON!=null){
		for ( var k = 0; k < orgRoleJSON.length; k++) {
			if(orgRoleJSON[k].id==roleID){
			$jq('#assignedTo').append($jq("<option></option>").attr("value",orgRoleJSON[k].id).text(orgRoleJSON[k].name));
			}
		}
	}
}
function getOrgRole(){
	$jq('#assignedTo').find('option').remove().end();
	deptId=$jq("#toDept").val();
	if(orgRoleJSON!=null){
		for ( var k = 0; k < orgRoleJSON.length; k++) {
			if(orgRoleJSON[k].departmentID==deptId){
			$jq('#assignedTo').append($jq("<option></option>").attr("value",orgRoleJSON[k].id).text(orgRoleJSON[k].name));
			}
		}
	}
}