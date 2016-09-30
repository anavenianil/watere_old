//----------------Start of ResidentialSecurityMasterDetails--------------------
function manageRSMasterDetails() {
	var errorMessage = "";
	var status = true;
	if ($jq("#quarterTypeId").val() == "") {
		errorMessage += "Select Quarter Type.\n";
		status = false;
		$jq("#quarterTypeId").focus();
	}
	if ($jq("#amount").val() == "") {
		errorMessage += "Enter Monthly Subscription Amount.\n";
		if (status) {
			status = false;
			$jq("#amount").focus();
		}
	}
	if ($jq('#effDate').val() == "") {
		errorMessage += "Select Effective Date.\n";
		if (status) {
			status = false;
			$jq('#effDate').focus();
		}
	}

	if (status) {
		var requestDetails = {
			"quarterTypeId" : $jq("#quarterTypeId").val(),
			"nodeID" : nodeID,
			"amount" : $jq("#amount").val(),
			"param" : "manageRS",
			"effDate" : $jq("#effDate").val(),
			"type" : $jq("#type").val()
		};
		$jq.ajax({
			type : "POST",
			url : "payBill.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#RSMasterList").html(html);
				clearRSMasterDetails();
			}
		});
	} else {
		alert(errorMessage);
	}
}
function clearRSMasterDetails() {
	$jq("#quarterTypeId").val("");
	$jq("#amount").val("");
	$jq("#effDate").val("");
	nodeID = "";
}
function deletePayBillRecord(id, type, replaceID) {
	var check = confirm("Do you want to delete ?");
	if (check == true) {
		var requestDetails = {
			"nodeID" : id,
			"type" : type,
			"param" : "delete"
		};
		$jq.ajax({
			type : "POST",
			url : "payBill.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#" + replaceID).html(html);
				clearRSMasterDetails();
			}
		});
	}
}
function editRSMasterDetails(id, amount, quarterTypeId, effDate) {
	nodeID = id;
	$jq("#quarterTypeId").val(quarterTypeId);
	$jq("#amount").val(amount);
	$jq("#effDate").val(effDate);
	$jq("#type").val('edit');

}

// ----------------End of ResidentialSecurityMasterDetails--------------------

// ----------------Dearness Allowance Master Details--------------------
function clearDAMasterDetails() {
	nodeID = "";
	$jq("#daDate").val("");
	$jq("#daValue").val("");
}
function editDAMasterDetails(id, daDate, daValue) {
	nodeID = id;
	$jq("#daDate").val(daDate);
	$jq("#daValue").val(daValue);
}
function manageDAMasterDetails() {
	var errorMessage = "";
	var status = true;
	if ($jq("#daDate").val() == "") {
		errorMessage += "Please Select Date\n";
		status = false;
		$jq("#daDate").focus();
	}
	if ($jq("#daValue").val() == "") {
		errorMessage += "Please Enter Allowance Amount\n";
		if (status) {
			status = false;
			$jq("#daValue").focus();
		}
	}
	if (status) {
		var requestDetails = {
			"nodeID" : nodeID,
			"daDate" : $jq("#daDate").val(),
			"daValue" : $jq("#daValue").val(),
			"param" : "manageDA"
		};
		$jq.ajax({
			type : "POST",
			url : "payBill.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#DAMasterList").html(html);
				clearDAMasterDetails();
			}
		});
	} else {
		alert(errorMessage);
	}
}
// ------------End of Dearness Allowance Master Details-------------

// ------------Start of CGEIS Master Details-------------

function editCgiesMasterDetails(id, beforeMember, afterMember, groupId,
		groupInsuranceDate) {
	nodeID = id;
	$jq("#groupId").val(groupId);
	$jq("#beforeMember").val(beforeMember);
	$jq("#afterMember").val(afterMember);
	$jq("#groupInsuranceDate").val(groupInsuranceDate);
}
function clearCgeisMasterDetails() {
	nodeID = "";
	$jq("#groupId").val("");
	$jq("#beforeMember").val("");
	$jq("#afterMember").val("");
	$jq("#groupInsuranceDate").val("");
}

function manageCgeisMasterDetails() {
	var errorMessage = "";
	var status = true;

	if ($jq("#groupInsuranceDate").val() == "") {
		errorMessage += "Please Select Year\n";
		status = false;
		$jq("#groupInsuranceDate").focus();
	}
	if ($jq("#groupId").val() == "") {
		errorMessage += "Select Group to which Employee belongs to\n";
		if (status) {
			status = false;
			$jq("#groupId").focus();
		}
	}
	if ($jq("#beforeMember").val() == "") {
		errorMessage += "Enter Before Member\n";
		if (status) {
			status = false;
			$jq("#beforeMember").focus();
		}
	}
	if ($jq("#afterMember").val() == "") {
		errorMessage += "Enter After Memeber\n";
		if (status) {
			status = false;
			$jq("#afterMember").focus();
		}
	}
	if (status) {
		var requestDetails = {
			"groupInsuranceDate" : $jq("#groupInsuranceDate").val(),
			"nodeID" : nodeID,
			"groupId" : $jq("#groupId").val(),
			"beforeMember" : $jq("#beforeMember").val(),
			"afterMember" : $jq("#afterMember").val(),
			"param" : "manageCgeis"
		};
		$jq.ajax({
			type : "POST",
			url : "payBill.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#cgeisMasterList").html(html);
				clearCgeisMasterDetails();
			}
		});
	} else {
		alert(errorMessage);
	}
}

// ------------End of CGEIS Master Details-------------

// ------------Start of CGHS Master Details-------------
function editCghsMasterDetails(id, gradePay, allowanceAmount, effDate) {
	nodeID = id;
	$jq("#gradePay").val(gradePay.split('.')[0]);
	$jq("#allowanceAmount").val(allowanceAmount);
	$jq("#effDate").val(effDate);
}

function clearPaybillCghsMaster() {
	nodeID = "";
	$jq("#gradePay").val("");
	$jq("#allowanceAmount").val("");
	$jq("#effDate").val("");
}

function managePaybillCghsMaster() {
	var errorMessage = "";
	var status = true;

	if ($jq("#gradePay").val() == "select") {
		errorMessage = "Please Select Grade Pay\n";
		status = false;
		$jq("#gradePay").focus();
	}
	if ($jq("#allowanceAmount").val() == "") {
		errorMessage += "Please Enter Monthly Subscription\n";
		if (status) {
			status = false;
			$jq("#allowanceAmount").focus();
		}
	}
	if ($jq("#effDate").val() == "") {
		errorMessage += "Please Select Effective Date\n";
		if (status) {
			status = false;
			$jq("#effDate").focus();
		}

	}
	if (status) {
		var requestDetails = {
			"nodeID" : nodeID,
			"gradePay" : $jq("#gradePay").val(),
			"effDate" : $jq("#effDate").val(),
			"allowanceAmount" : $jq("#allowanceAmount").val(),
			"param" : "manageCghs"
		};
		$jq.ajax({
			type : "POST",
			url : "payBill.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#cghsMasterList").html(html);
				clearPaybillCghsMaster()
			}
		});
	} else {
		alert(errorMessage);
	}
}

// ------------Start of CGHS Master Details-------------

// start of payscale
function payScaleLables(type) {
	document.title = "Pay Bill";
	if (type == 'professionalTax') {
		// document.title = "Professional Tax Master";
		document.getElementById('headTitle').innerHTML = "Professional Tax Master";
		document.getElementById('firstLabelType').innerHTML = "Slab From<span class='mandatory'>*</span>";
		document.getElementById('secondLabelType').innerHTML = "Tax Amount<span class='mandatory'>*</span>";
	} else if (type == 'variableIncrement') {
		// document.title = "Variable Increment Master";
		document.getElementById('headTitle').innerHTML = "Variable Increment Master";
		document.getElementById('firstLabelType').innerHTML = "Grade pay From<span class='mandatory'>*</span>";
		document.getElementById('secondLabelType').innerHTML = "Tax Amount<span class='mandatory'>*</span>";
	} else if (type == 'familyPlanning') {
		// document.title = "Family Planning Master";
		document.getElementById('headTitle').innerHTML = "Family Planning Master";
		document.getElementById('firstLabelType').innerHTML = "Grade pay <span class='mandatory'>*</span>";
		document.getElementById('secondLabelType').innerHTML = "Rate Of Family Planning Allowance<span class='mandatory'>*</span>";
	} else if (type == 'travellingAllowance') {
		// document.title = "Travelling Allowance Master";
		document.getElementById('headTitle').innerHTML = "Travelling Allowance Master";
		document.getElementById('firstLabelType').innerHTML = "Grade pay From<span class='mandatory'>*</span>";
		document.getElementById('secondLabelType').innerHTML = "Transport Allowance Payable<span class='mandatory'>*</span>";
	}
}

function clearPayScale() {
	payID = "";
	if ($jq('#firstTypeValue').length > 0) {
		$jq('#firstTypeValue').val('');
	}
	if ($jq('#secondTypeValue').length > 0) {
		$jq('#secondTypeValue').val('');
	}
	if ($jq('#to').length > 0) {
		$jq('#to').val('');
	}
	$jq('#effDate').val('');
}
function managePayScale(type) {
	var errorMessage = '';
	var flag = true;
	var firstBoxAlert = "";
	var secondBoxAlert = "";
	var thirdBoxAlert = "";

	if (type == 'professionalTax') {
		firstBoxAlert = "Slab From";
		secondBoxAlert = "Slab To";
		thirdBoxAlert = "Tax Amount";
	} else if (type == 'variableIncrement') {
		firstBoxAlert = "Grade Pay From";
		secondBoxAlert = "Grade Pay To";
		thirdBoxAlert = "Increment Amount";
	} else if (type == 'familyPlanning') {
		firstBoxAlert = "Grade Pay ";
		thirdBoxAlert = "Rate Of Family Planning Allowance";
	} else if (type == 'travellingAllowance') {
		firstBoxAlert = "Grade Pay From";
		secondBoxAlert = "Grade Pay To";
		thirdBoxAlert = "Transport Allowance Payable";
	}

	if ($jq('#firstTypeValue').val() == '') {
		errorMessage += "Please Enter " + firstBoxAlert + "\n";
		if (flag) {
			flag = false;
			$jq('#firstTypeValue').focus();
		}
	}
	if ($jq('#to').val() == '') {
		errorMessage += "Please Enter " + secondBoxAlert + "\n";
		if (flag) {
			flag = false;
			$jq('#to').focus();
		}
	}
	if ($jq('#firstTypeValue').val() != ''
			&& $jq('#to').val() != ''
			&& parseInt($jq('#firstTypeValue').val()) > parseInt($jq('#to')
					.val())) {
		errorMessage += "Please Enter Valid Range \n";
		if (flag) {
			$jq('#firstTypeValue').focus();
			flag = false;
		}
	}
	if ($jq('#secondTypeValue').val() == '') {
		errorMessage += "Please Enter " + thirdBoxAlert + "\n";
		if (flag) {
			flag = false;
			$jq('#secondTypeValue').focus();
		}
	}
	if ($jq('#effDate').val() == '') {
		errorMessage += "Please Select Effective Date\n";
		if (flag) {
			flag = false;
			$jq('#effDate').focus();
		}
	}
	if ($jq('#firstTypeValue').val() != "" && $jq('#to').val() != "") {
		var payfrom = parseInt($jq('#firstTypeValue').val());
		var payto = parseInt($jq('#to').val());
		for (var i = 0; i < jsonPayScaleList.length; i++) {
			if ((parseInt(jsonPayScaleList[i].name.split('-')[0]) > payfrom && parseInt(jsonPayScaleList[i].name
					.split('-')[0]) < payto)
					|| (parseInt(jsonPayScaleList[i].name.split('-')[1]) > payfrom && parseInt(jsonPayScaleList[i].name
							.split('-')[1]) < payto)) {
				errorMessage += "Please Enter Valid Range!...";
				flag = false;
				break;
			}
		}
	}
	if (flag) {
		var requestDetails = {
			"firstTypeValue" : $jq('#firstTypeValue').val(),
			"to" : $jq('#to').val(),
			"secondTypeValue" : $jq('#secondTypeValue').val(),
			"effDate" : $jq('#effDate').val(),
			"param" : "manage",
			"status" : "1",
			"type" : type,
			"pk" : payID
		};
		$jq.post('payScale.htm' + '?' + dispUrl, requestDetails,
				function(html) {
					$jq("#payScaleMasterList").html(html);
					clearPayScale();
				});
	} else {
		alert(errorMessage);
	}
}

function checkGradePayFrom(id) {
	var flag = true;
	var payfrom = parseInt($jq('#' + id).val());
	if (jsonPayScaleList.length != 0 && payfrom != "") {
		for (var i = 0; i < jsonPayScaleList.length; i++) {
			if (payfrom >= parseInt(jsonPayScaleList[i].name.split('-')[0])
					&& payfrom <= parseInt(jsonPayScaleList[i].name.split('-')[1])) {
				alert("Please Enter Valid Grade Pay From value!...");
				$jq('#' + id).val('');
				$jq('#' + id).focus();
				flag = false;
				break;
			}
		}
	}
	return flag;
}
function checkGradePayTo(id) {
	var flag = true;
	var payto = parseInt($jq('#' + id).val());
	if (jsonPayScaleList.length != 0 && payto != "") {
		for (var i = 0; i < jsonPayScaleList.length; i++) {
			if (payto >= parseInt(jsonPayScaleList[i].name.split('-')[0])
					&& payto <= parseInt(jsonPayScaleList[i].name.split('-')[1])) {
				alert("Please Enter Valid GradePay To value!...");
				$jq('#' + id).val('');
				$jq('#' + id).focus();
				flag = false;
				break;
			}
		}
	}
	return flag;
}
function editPayScale(id, name, secondTypeValue, effDate) {
	payID = id;
	$jq('#firstTypeValue').val(name.split('-')[0]);
	$jq('#to').val(name.split('-')[1]);
	$jq('#secondTypeValue').val(secondTypeValue);
	$jq('#effDate').val(effDate);
}
function deletePayScale(id, type) {
	var check = confirm("Do you want to delete ?");
	if (check == true) {
		var requestDetails = {
			"status" : "0",
			"param" : "manage",
			"type" : type,
			"pk" : id
		};
		$jq.post('payScale.htm' + '?' + dispUrl, requestDetails,
				function(html) {
					$jq("#payScaleMasterList").html(html);
				});
	}
}

// Pay Scale end

// start of Quarter Type Master
function resetQuarterTypeDetails() {
	$jq('#quarterSubType').val('');
	$jq('#quartersType').val('select');
	quarterTypeID = "";
}
function editQuarterTypeDetails(id, quarterSubType, quartersType) {
	quarterTypeID = id;
	$jq('#quarterSubType').val(quarterSubType);
	$jq('#quartersType').val(quartersType);
}
function manageQuarterTypeDetails() {
	var flag = true;
	var errorMessage = "";
	if ($jq('#quartersType').val() == 'select') {
		errorMessage += "Please Select Quarter Type Category\n";
		if (flag) {
			$jq('#quartersType').focus();
			flag = false;
		}
	}
	if ($jq('#quarterSubType').val().trim() == '') {
		errorMessage += "Please Enter Quarter SubType\n";
		if (flag) {
			$jq('#quarterSubType').focus();
			flag = false;
		}
	}
	if (flag) {
		var requestDetails = {
			"quartersType" : $jq('#quartersType').val(),
			"quarterSubType" : $jq.trim($jq('#quarterSubType').val()),
			"status" : "1",
			"param" : "manage",
			"pk" : quarterTypeID
		};
		$jq.post('quarterType.htm' + '?' + dispUrl, requestDetails, function(
				html) {
			$jq("#result").html(html);

			resetQuarterTypeDetails();
		});
	} else {
		alert(errorMessage);
	}
}
function deleteQuarterTypeDetails(id) {
	var check = confirm("Do you want to delete ?");
	if (check) {
		var requestDetails = {
			"status" : "0",
			"param" : "manage",
			"pk" : id
		};
		$jq.post('quarterType.htm' + '?' + dispUrl, requestDetails, function(
				html) {
			$jq("#result").html(html);
		});
	}
}
// end of Quarter Type Master

// start of Payscale Designation
function resetPayscaleDesignation() {
	$jq('#designation').val('select');
	$jq('#payband').val('select');
	$jq('#gradePay').val('');
	payDesID = "";
}
function editPayscaleDesignation(id, designation, payband, gradePay) {
	payDesID = id;
	$jq('#designation').val(designation);
	$jq('#payband').val(payband);
	$jq('#gradePay').val(gradePay);

}

function managePayscaleDesignation() {
	var flag = true;
	var errorMessage = "";
	if ($jq('#designation').val() == 'select') {
		errorMessage += "Please Select Designation\n";
		if (flag) {
			$jq('#designation').focus();
			flag = false;
		}
	}
	if ($jq('#payband').val() == 'select') {
		errorMessage += "Please Select Payband\n";
		if (flag) {
			$jq('#payband').focus();
			flag = false;
		}
	}

	if ($jq('#gradePay').val() == '') {
		errorMessage += "Please Enter GradePay\n";
		if (flag) {
			$jq('#gradePay').focus();
			flag = false;
		}
	}

	if (flag) {
		var requestDetails = {
			"designation" : $jq('#designation').val(),
			"payband" : $jq('#payband').val(),
			"gradePay" : $jq('#gradePay').val(),
			"status" : "1",
			"param" : "payDesignationManage",
			"pk" : payDesID
		};
		$jq.post('payScale.htm' + '?' + dispUrl, requestDetails,
				function(html) {
					$jq("#result").html(html);

					resetPayscaleDesignation();
				});
	} else {
		alert(errorMessage);
	}
}
function deletePayscaleDesignation(id) {
	var check = confirm("Do you want to delete ?");
	if (check) {
		var requestDetails = {
			"status" : "0",
			"param" : "payDesignationManage",
			"pk" : id
		};
		$jq.post('payScale.htm' + '?' + dispUrl, requestDetails,
				function(html) {
					$jq("#result").html(html);
				});
	}
}
// end of Payscale Designation

// License Fee charges
function resetLicenceFeeDetails() {
	$jq('#quarterSubType').val('select');
	$jq('#quartersType').val('select');
	$jq('#licenceFee').val('');
	licenceFeeID = "";
	$jq('#effDate').val('');
	$jq('#water').val('');
	$jq('#elec').val('');
	$jq('#furn').val('');
}
function editLicenceFeeDetails(id, quarterType, quarterSubType, licenceFee,
		water, elec, furn, effDate) {
	$jq('#quartersType').val(quarterType);
	getQuarterSubTypeList();
	licenceFeeID = id;
	$jq('#quarterSubType').val(quarterSubType);
	$jq('#licenceFee').val(licenceFee);
	$jq('#elec').val(elec);
	$jq('#water').val(water);
	$jq('#furn').val(furn);
	$jq('#effDate').val(effDate);
}
function submitLicenceFeeDetails() {
	var flag = true;
	var errorMessage = "";
	if ($jq('#quartersType').val() == 'select') {
		errorMessage += "Please Select  Type Of quarter\n";
		if (flag) {
			$jq('#quartersType').focus();
			flag = false;
		}
	}
	if ($jq('#quarterSubType').val() == 'select') {
		errorMessage += "Please Select  Sub Type Of quarter\n";
		if (flag) {
			$jq('#quarterSubType').focus();
			flag = false;
		}
	}
	if ($jq('#licenceFee').val() == '') {
		errorMessage += "Please Enter Rent Amount\n";
		if (flag) {
			$jq('#licenceFee').focus();
			flag = false;
		}
	}
	if ($jq('#water').val() == '') {
		errorMessage += "Please Enter Water Bill Amount\n";
		if (flag) {
			$jq('#water').focus();
			flag = false;
		}
	}
	if ($jq('#elec').val() == '') {
		errorMessage += "Please Enter Electricity Bill Amount\n";
		if (flag) {
			$jq('#elec').focus();
			flag = false;
		}
	}
	if ($jq('#furn').val() == '') {
		errorMessage += "Please Enter Furniture Bill Amount\n";
		if (flag) {
			$jq('#furn').focus();
			flag = false;
		}
	}
	if ($jq('#effDate').val() == '') {
		errorMessage += "Please Select Effective Date\n";
		if (flag) {
			$jq('#effDate').focus();
			flag = false;
		}
	}

	if (flag) {

		var requestDetails = {
			"quartersType" : $jq('#quartersType').val(),
			"quarterSubType" : $jq('#quarterSubType').val(),
			"licenceFee" : $jq('#licenceFee').val(),
			"elec" : $jq('#elec').val(),
			"water" : $jq('#water').val(),
			"furn" : $jq('#furn').val(),
			"effDate" : $jq('#effDate').val(),
			"status" : "1",
			"param" : "licenceFeeManage",
			"pk" : licenceFeeID
		};
		$jq.post('quarterType.htm' + '?' + dispUrl, requestDetails, function(
				html) {
			$jq("#result").html(html);

			resetLicenceFeeDetails();
		});
	} else {
		alert(errorMessage);
	}
}

function getQuarterSubTypeList() {
	$jq('#quarterSubType').find('option:not(:first)').remove().end();

	if (quarterSubTypeListJSON != null) {
		for (var i = 0; i < quarterSubTypeListJSON.length; i++) {
			if (quarterSubTypeListJSON[i].quartersType == $jq('#quartersType')
					.val()) {
				$jq("#quarterSubType").append(
						'<option value=' + quarterSubTypeListJSON[i].id + '>'
								+ quarterSubTypeListJSON[i].quarterSubType
								+ '</option>');

			}

		}
	}
}
function deleteLicenceFeeDetails(id) {
	var check = confirm("Do you want to delete ?");
	if (check) {
		var requestDetails = {
			"status" : "0",
			"param" : "licenceFeeManage",
			"pk" : id
		};
		$jq.post('quarterType.htm' + '?' + dispUrl, requestDetails, function(
				html) {
			$jq("#result").html(html);
		});
	}
}

function clearCcsUploadFile() {
	document.forms[0].xslFile.value = "";
}
function payCcsUploadFile() {
	var filename = document.forms[0].xslFile.value;
	var fileType = filename.split(".")[1];

	if (fileType == "xls" || fileType == "xlsx")
		$jq.ajaxFileUpload({
			url : 'payBill.htm?param=newccsupload',
			secureuri : false,
			fileElementId : "xslFile",
			dataType : 'json',
			async : false,
			success : function(data, status) {
				$jq('#result').html(data);
			},
			error : function(data, status, e)

			{
				$jq('#result').html(data);

			}

		});
	else
		alert("Upload xls or xlsx file only");

}

function clearPaybillCCMaster() {
	$jq('#deductionID').val('select');
	$jq('#amount').val('');
	$jq('#presentInst').val('');
	$jq('#inst').val('');
	$jq('#effDate').val('');
	$jq('#type').val('');
	$jq('#pk').val('');

}

function payCCDetails() {
	var flag = true;
	var errorMessage = "";
	if ($jq('#deductionID option:selected').val() == 'select') {
		errorMessage += "Please Select  Deduction type\n";
		if (flag) {
			$jq('#deductionID').focus();
			flag = false;
		}
	}

	if ($jq('#empID').val() == '') {
		errorMessage += "Please enter SFID\n";
		if (flag) {
			$jq('#empID').focus();
			flag = false;
		}
	}

	if ($jq('#amount').val() == '') {
		errorMessage += "Please enter Amount\n";
		if (flag) {
			$jq('#amount').focus();
			flag = false;
		}
	}

	if ($jq('#inst').val() == '') {
		errorMessage += "Please enter no of installments\n";
		if (flag) {
			$jq('#inst').focus();
			flag = false;
		}
	}
	if ($jq('#presentInst').val() == '') {
		errorMessage += "Please enter present installment\n";
		if (flag) {
			$jq('#presentInst').focus();
			flag = false;
		}
	}

	if (parseFloat($jq('#inst').val()) < parseFloat($jq('#presentInst').val())) {
		errorMessage += "Present installment vlaues should not always more than total installment\n";
		if (flag) {
			$jq('#presentInst').focus();
			flag = false;
		}
	}

	if ($jq('#effDate').val() == '') {
		errorMessage += "Please enter effective date\n";
		if (flag) {
			$jq('#effDate').focus();
			flag = false;
		}
	}

	if (errorMessage == "") {
		$jq('#param').val('ManageCCDetails');
		$jq.post('payBill.htm', $jq('#PayBillMasterBean').serialize(),
				function(html) {
					$jq("#result1").html(html);
					clearPaybillCCMaster();
				});
	} else {
		alert(errorMessage);
	}
}

function categoryNameValidation(e) {
	var str = "!`~@#$%^&*()_=+\/*,'|;:[]{}?<>";
	var key;
	var flag = 0;
	if (window.event) {
		key = window.event.keyCode;
	} else {
		key = e.which;
		key = String.fromCharCode(key);
	}
	for (var i = 0; i < str.length; i++) {
		if (str[i] == key) {
			flag = 1;
			break;
		}
	}
	if (flag == 0 && key != '"')
		return true;
	else {
		alert("Allowable special characters are - . and space");
		return false;
	}
}

function clearPayCategoryDetails() {
	$jq('#categoryName').val('');
	$jq('#payOrderNo').val('');
	$jq("input:radio").attr("checked",false);
}

function payCategoryDetails() {
	var flag = true;
	var errorMessage = "";
	if ($jq('#categoryName').val() == '') {
		errorMessage += "Please Enter Category Name\n";
		if (flag) {
			$jq('#categoryName').focus();
			flag = false;
		}
	}
	if ($jq('#payOrderNo').val() == "") {
		errorMessage += "Please Enter Order No\n";
		if (flag) {
			$jq('#payOrderNo').focus();
			flag = false;
		}
	}
	if ($jq('#payOrderNo').val() != "") {
		var orderNo = parseInt($jq('#payOrderNo').val());

		if($jq('#pk').val()==''){
		for (var i = 0; i < jsonPayOrderNo.length; i++) {
				if(orderNo == parseInt(jsonPayOrderNo[i].payOrderNo))
				{
					errorMessage += "Entered Order No is not allowed, Try another one!...";
					$jq('#payOrderNo').focus();
					break;
				}
			}
		}
	}

	if($jq('#payBillType1').is(':checked')==false && $jq('#payBillType2').is(':checked')==false){
		errorMessage+="Please Select The PayBill type \n";
		flag = false;
	}
	if(errorMessage == ""){
        $jq('#categoryName').val($jq.trim($jq('#categoryName').val()));
		$jq('#payOrderNo').val($jq.trim($jq('#payOrderNo').val()));
		$jq('#param').val('empCategoryUpdate');
		$jq('#payBillType').val($jq('input:radio[name=payBillType]:checked').val());
		$jq.post('payBill.htm', $jq('#PayBillMasterBean').serialize(),
				function(html) {
					$jq("#result").html(html);
					$jq('#type').val('');
					$jq('#pk').val('');
				});
		clearPayCategoryDetails();
	} else {
		alert(errorMessage);
	}
}

function payDeleteCategoryDetails(id, name, order) {

	var check = confirm("Do you want to delete it? ");

	if (check) {
		$jq('#param').val('empCategoryDelete');
		$jq('#type').val(name);
		$jq('#payOrderNo').val(order);
		$jq('#pk').val(id);

		$jq.post('payBill.htm', $jq('#PayBillMasterBean').serialize(),
				function(html) {
					$jq("#result").html(html);
					$jq('#type').val('');
					$jq('#payOrderNo').val('');
					$jq('#pk').val('');
				});
		clearPayCategoryDetails();
	}

}
function paySearchSFID() {
	if ($jq('#empID').val() == '') {
		alert("Please Enter SFID");
	} else {
		$jq('#param').val('PaysearchSFID');
		$jq.post('payBill.htm', $jq('#PayBillMasterBean').serialize(),
				function(html) {
					$jq("#result").html(html);

				});
	}
}
function payEditCategoryDetails(id, name, order,payBillType)
{
$jq('#pk').val(id);
	$jq('#type').val("edit");
	$jq('#categoryName').val(name);
	$jq('#payOrderNo').val(order);
	if(payBillType=='Regular'){
		$jq("#payBillType1[value=" + payBillType + "]").attr('checked', true);
	}else
		$jq("#payBillType2[value=" + payBillType + "]").attr('checked', true);
	}
	

function payLoanDetailsSearch() {
	if ($jq('#sfidSearchKey').val() == '') {
		alert("Please Enter SFID");
	} else {
		$jq('#param').val('payLoanSearchSFID');
		$jq.post('payBill.htm', $jq('#PayBillMasterBean').serialize(),
				function(html) {
					$jq("#result").html(html);
				});
		$jq('#payrunmonth').show();
		clearEmpLoanDeatils();
	}
}
function clearEmpLoanDeatils() {
	$jq('#loanType').val('');
	$jq('#emi').val('');
	$jq('#loanDeduType').val('');
	$jq('#presentInst').val('');
	$jq('#totalInst').val('');
	$jq('#totalAmt').val('');
	$jq('#outStandAmt').val('');
	$jq('#recoveryStartDate').val('');
	$jq('#interestRate').val('');
}

function editPayLoanDetails(id, Loanid, totalAmt, emi, prestInst, totalInst,
		deduType, outStandAmt, referenceId, loanRefId, runId, effDate,
		interestRate) {
	$jq('#loanType').val(Loanid);
	$jq('#emi').val(emi);
	$jq('#loanDeduType').val(deduType);
	$jq('#presentInst').val(prestInst);
	$jq('#totalInst').val(totalInst);
	$jq('#outStandAmt').val(outStandAmt);
	$jq('#totalAmt').val(totalAmt);
	$jq('#type').val("edit");
	$jq('#pk').val(id);
	$jq('#referenceId').val(referenceId);
	$jq('#loanRefId').val(loanRefId);
	$jq('#runId').val(runId);
	$jq('#recoveryStartDate').val(effDate);
	$jq('#interestRate').val(interestRate);
}

function saveEmpLoanDeatils() {

	var flag = true;
	var errorMessage = "";
	if ($jq('#loanType option:selected').val() == 'select') {
		errorMessage += "Please Select  Loan Name\n";
		if (flag) {
			$jq('#loanType').focus();
			flag = false;
		}
	}
	if ($jq('#loanDeduType option:selected').val() == 'select') {
		errorMessage += "Please Select  Principal / Interest \n";
		if (flag) {
			$jq('#loanDeduType').focus();
			flag = false;
		}
	}
	if ($jq('#emi').val() == '') {
		errorMessage += "Please Enter Installment Amount\n";
		if (flag) {
			$jq('#emi').focus();
			flag = false;
		}
	}
	if ($jq('#totalAmt').val() == '') {
		errorMessage += "Please Enter Total Amount\n";
		if (flag) {
			$jq('#totalAmt').focus();
			flag = false;
		}
	}
	if ($jq('#presentInst').val() == '') {
		errorMessage += "Please Enter Present Installment No\n";
		if (flag) {
			$jq('#presentInst').focus();
			flag = false;
		}
	}
	if ($jq('#totalInst').val() == '') {
		errorMessage += "Please Enter Total No of Installments\n";
		if (flag) {
			$jq('#totalInst').focus();
			flag = false;
		}
	}
	if (parseFloat($jq('#totalInst').val()) < parseFloat($jq('#presentInst')
			.val())) {
		errorMessage += "Present Installment should be less than Total No of Installments\n";
		if (flag) {
			$jq('#presentInst').focus();
			flag = false;
		}
	}

	if ($jq('#outStandAmt').val() == '') {
		errorMessage += "Please Enter Outstanding amount\n";
		if (flag) {
			$jq('#outStandAmt').focus();
			flag = false;
		}
	}
	if ($jq('#recoveryStartDate').val() == '') {
		errorMessage += "Please Select Recovery Date\n";
		if (flag) {
			$jq('#recoveryStartDate').focus();
			flag = false;
		}
	}
	if ($jq('#interestRate').val() == '' || $jq('#interestRate').val() == '0'
			|| $jq('#interestRate').val() == '0.0') {
		errorMessage += "Please Enter Interest Rate\n";
		if (flag) {
			$jq('#interestRate').focus();
			flag = false;
		}
	}
	if (errorMessage == "") {
		$jq('#param').val('insertPayLoanDetails');
		$jq.post('payBill.htm', $jq('#PayBillMasterBean').serialize(),
				function(html) {
					$jq("#result1").html(html);
					$jq('#type').val('');
					$jq('#pk').val('');
					clearEmpLoanDeatils();
				});
	} else {
		alert(errorMessage);
	}

}

function showCCSDiv() {
	if ($jq('#ccsMemFlag option:selected').val() == 'Yes') {
		$jq("#ccsDiv").show();
	} else {
		$jq('#ccsNo').val('');
		$jq('#ccsSubAmt').val('');
		$jq("#ccsDiv").hide();
	}
}

function showGPFDiv() {
	if ($jq('#gpfFlag option:selected').val() == 'Yes') {
		$jq("#gpfDiv").show();
	} else {
		$jq('#gpfAccountNo').val('');
		$jq('#gpfSubAmt').val('');
		$jq("#gpfDiv").hide();
	}
}

function showFPADiv() {
	if ($jq('#fpaFlag option:selected').val() == 'Yes') {
		$jq("#fpaDiv").show();
	} else {
		$jq('#fpaGradePay').val('');
		$jq("#fpaDiv").hide();
	}
}

function showBankDiv() {
	if ($jq('#bankFlag option:selected').val() == 'Yes') {
		$jq("#bankDiv").show();
	} else {
		$jq('#bankAccNo').val('');
		$jq('#bankBranch').val('');
		$jq("#bankDiv").hide();
	}
}

function showQuarterDiv() {

	if ($jq('#quartersFlag option:selected').val() == 'Yes') {
		$jq("#quarterDiv").show();
	} else {
		$jq('#quarterTypeId').val('select');
		$jq('#quarterNo').val('');
		$jq('#electricityBillAmt').val('');
		$jq("#quarterDiv").hide();
	}
}

function clearPayEmpOneTimeDeatils() {
	$jq('#sfidSearchKey').val('select');
	$jq('#ccsMemFlag').val('No');
	$jq('#bankFlag').val('No');
	$jq('#fpaFlag').val('No');
	$jq('#gpfFlag').val('No');
	$jq('#basicPay').val('');
	$jq('#gPay').val('');
	$jq('#incomeTaxAmt').val('');
	$jq('#panCardNo').val('');
	$jq('#varIncrPts').val('');
	$jq('#twoAddIncr').val('');
	$jq('#categoryID').val('select');
	$jq('#tptFlag').val('No');
	$jq('#ccsNo').val('');
	$jq('#ccsSubAmt').val('');
	$jq('#gpfAccountNo').val('');
	$jq('#gpfSubAmt').val('');
	$jq('#cgeisGroupID').val('select');
	$jq('#fpaGradePay').val('');
	$jq('#bankAccNo').val('');
	$jq('#bankBranch').val('');
	$jq("#bankDiv").hide();
	$jq("#fpaDiv").hide();
	$jq("#gpfDiv").hide();
	$jq("#ccsDiv").hide();
	$jq('#param').val('');
	$jq('#washAllwFlag').val('No');
	$jq('#payResult').html('');

}

function submitPayEmpOneTimeDeatils() {
	var flag = true;
	var errorMessage = "";
	if ($jq('#sfidSearchKey').val() == 'select') {
		errorMessage += "Please Select SFID\n";
		if (flag) {
			$jq('#sfidSearchKey').focus();
			flag = false;
		}
	}

	if ($jq('#basicPay').val() == '') {
		errorMessage += "Please Enter Basic pay amount\n";
		if (flag) {
			$jq('#basicPay').focus();
			flag = false;
		}
	}

	if ($jq('#gPay').val() == '') {
		errorMessage += "Please Enter Grade pay amount\n";
		if (flag) {
			$jq('#gPay').focus();
			flag = false;
		}
	}

	if ($jq('#incomeTaxAmt').val() == '') {
		errorMessage += "Please Enter Income tax amount\n";
		if (flag) {
			$jq('#incomeTaxAmt').focus();
			flag = false;
		}
	}
	if ($jq('#panCardNo').val() == '') {
		errorMessage += "Please Enter Pan card number\n";
		if (flag) {
			$jq('#panCardNo').focus();
			flag = false;
		}
	}
	if ($jq('#varIncrPts').val() == '') {
		errorMessage += "Please Enter Variable increment\n";
		if (flag) {
			$jq('#varIncrPts').focus();
			flag = false;
		}
	}

	if ($jq('#twoAddIncr').val() == '') {
		errorMessage += "Please Enter Two additional increment\n";
		if (flag) {
			$jq('#twoAddIncr').focus();
			flag = false;
		}
	}

	if ($jq('#categoryID option:selected').val() == 'select') {
		errorMessage += "Please Select Category\n";
		if (flag) {
			$jq('#categoryID').focus();
			flag = false;
		}
	}

	if ($jq('#tptFlag option:selected').val() == 'select') {
		errorMessage += "Please Select TPT \n";
		if (flag) {
			$jq('#tptFlag').focus();
			flag = false;
		}
	}

	if ($jq('#ccsMemFlag option:selected').val() == 'Yes') {

		if ($jq('#ccsNo').val() == '') {
			errorMessage += "Please Enter CCS Member\n";
			if (flag) {
				$jq('#ccsNo').focus();
				flag = false;
			}
		}

		if ($jq('#ccsSubAmt').val() == '') {
			errorMessage += "Please Enter CCS Subscription amount\n";
			if (flag) {
				$jq('#ccsSubAmt').focus();
				flag = false;
			}
		}
	}

	if ($jq('#gpfFlag option:selected').val() == 'Yes') {

		if ($jq('#gpfAccountNo').val() == '') {
			errorMessage += "Please Enter GPF/CPS Number\n";
			if (flag) {
				$jq('#gpfAccountNo').focus();
				flag = false;
			}
		}

		if ($jq('#gpfSubAmt').val() == '') {
			errorMessage += "Please Enter GPF/CPS Subscription amount\n";
			if (flag) {
				$jq('#gpfSubAmt').focus();
				flag = false;
			}
		}
	}

	if ($jq('#cgeisGroupID option:selected').val() == 'select') {
		errorMessage += "Please Select CEGIS Group \n";
		if (flag) {
			$jq('#cgeisGroupID').focus();
			flag = false;
		}
	}

	if ($jq('#cegis1').is(':checked') == false
			&& $jq('#cegis2').is(':checked') == false) {
		errorMessage += "Please Select Full or Half subscription \n";
		if (flag) {
			$jq('#cegis1').focus();
			flag = false;
		}
	}

	if ($jq('#fpaFlag option:selected').val() == 'Yes') {

		if ($jq('#fpaGradePay').val() == '') {
			errorMessage += "Please Enter FPA Grade pay\n";
			if (flag) {
				$jq('#fpaGradePay').focus();
				flag = false;
			}
		}

	}
	if ($jq('#bankFlag option:selected').val() == 'Yes') {
		if ($jq('#bankAccNo').val() == '') {
			errorMessage += "Please Enter Bank account number\n";
			if (flag) {
				$jq('#bankAccNo').focus();
				flag = false;
			}
		}

		if ($jq('#bankBranch').val() == '') {
			errorMessage += "Please Enter Bank branch name\n";
			if (flag) {
				$jq('#bankBranch').focus();
				flag = false;
			}
		}
	}

	if (errorMessage == "") {
		$jq('#param').val('insertPayOneTimeDetails');
		$jq.post('payBill.htm', $jq('#PayBillMasterBean').serialize(),
				function(html) {
					$jq("#payresult1").html(html);
					clearPayEmpOneTimeDeatils();
					$jq('#sfidSearchKey').val("select");
				});
		$jq('body #ajaxBusy:visible').each(function() {
			if ($jq(this).is(':visible')) {
				$jq(this).remove();
			}
		});
	} else {
		alert(errorMessage);
	}

}

function clearPayBandDetails() {
	$jq('#name').val('');
	$jq('#rangeFrom').val('');
	$jq('#rangeTo').val('');
	$jq('#type').val('');
	$jq('#pk').val('');
	$jq('#effDate').val('');
}

function createPayBand() {
	var flag = true;
	var errorMessage = "";
	if ($jq('#name').val() == '') {
		errorMessage += "Please Enter Pay Band Name\n";
		if (flag) {
			$jq('#name').focus();
			flag = false;
		}
	}
	if ($jq('#effDate').val() == '') {
		errorMessage += "Please Select Effective Date\n";
		if (flag) {
			$jq('#effDate').focus();
			flag = false;
		}
	}
	if ($jq('#rangeFrom').val() == '') {
		errorMessage += "Please Enter Range From\n";
		if (flag) {
			$jq('#basicPay').focus();
			flag = false;
		}
	}

	if ($jq('#rangeTo').val() == '') {
		errorMessage += "Please Enter Range To\n";
		if (flag) {
			$jq('#rangeTo').focus();
			flag = false;
		}
	}

	if ($jq('#rangeFrom').val() != ''
			&& $jq('#rangeTo').val() != ''
			&& parseInt($jq('#rangeFrom').val()) > parseInt($jq('#rangeTo')
					.val())) {
		errorMessage += "Please Enter Valid Range \n";
		if (flag) {
			$jq('#rangeFrom').focus();
			flag = false;
		}
	}

	if (errorMessage == "") {
		$jq('#param').val('insertPayBand');
		$jq.post('payScale.htm', $jq('#PayScaleBean').serialize(), function(
				html) {
			$jq("#result").html(html);
			clearPayBandDetails();
		});
	} else {
		alert(errorMessage);
	}
}

function setReadOnly(e, id) {
	$jq('#' + id).attr("readonly", "true");
}

function payBandDeleteDetails(id) {
	var check = confirm("Do you want to delete ?");
	if (check == true) {
		$jq('#param').val('deletePayBand');
		$jq('#pk').val(id);

		$jq.post('payScale.htm', $jq('#PayScaleBean').serialize(), function(
				html) {
			$jq("#result").html(html);
			clearPayBandDetails();
		});
	}
}

function payBandEditDetails(id, name, rangeFrom, rangeTo, effDate) {
	$jq('#type').val('edit');
	$jq('#pk').val(id);
	$jq('#name').val(name);
	$jq('#rangeFrom').val(rangeFrom);
	$jq('#rangeTo').val(rangeTo);
	$jq('#effDate').val(effDate);
}

function payCCsearch() {
	if ($jq('#sfidSearchKey').val() == '') {
		alert("Please Enter SFID");
	} else {
		$jq('#param').val('coinCutHome');
		$jq.post('payBill.htm', $jq('#PayBillMasterBean').serialize(),
				function(html) {
					$jq("#result").html(html);
				});
		$jq('#payrunmonth').show();
	}
}

function payEditCcDetails(id, amount, deductionType, presentInst, totalInst, effDate) {
	$jq('#type').val('edit');
	$jq('#pk').val(id);
	$jq('#deductionID').val(deductionType);
	$jq('#amount').val(amount);
	$jq('#presentInst').val(presentInst);
	$jq('#inst').val(totalInst);
	$jq('#effDate').val(effDate);

}

function payDeleteCcDetails(id) {
	var check = confirm("Do you want to delete this item?");
	if (check) {
		$jq('#param').val('deleteCCDeatils');
		$jq('#pk').val(id);
		$jq.post('payBill.htm', $jq('#PayBillMasterBean').serialize(),
				function(html) {
					$jq("#result1").html(html);
				});
	}
}

function selectAllowanceType() {
	if (document.getElementById('allowanceID').value != "select") {
		$jq('#SelectLeft').val('');
		$jq('#param').val('showAllowanceGroup');
		$jq.post('payBill.htm', $jq('#PayBillMasterBean').serialize(),
				function(html) {
					$jq("#result1").html(html);
					$jq("#result").html('');
				});
	} else {
		$jq('#SelectLeft').find('option').remove().end();
	}
}

function submitAllowanceCgfDetails() {
	var selectedValues = "";
	var errorMessages = "";
	var flag = true;
	var status = false;
	if (document.getElementById('allowanceID').value == "select") {
		errorMessages += "Please Select Allowance Type\n";
		document.getElementById('allowanceID').focus();
		flag = false;
	}
	if ($jq('#allowanceID').val() != "select") {
		if ($jq('#SelectLeft').find('option').length == "0"
				&& $jq('#SelectRight').find('option').length == "0") {
			errorMessages += "Please Select Designation \n";
			flag = false;
		} else {
			var left = 0;
			var right = 0;
			if ($jq('#SelectLeft').find('option').length != "0") {
				$jq('#SelectLeft').find('option').each(function() {
					if ($jq(this).attr("selected") == false) {
						left++;
					}
				});
			}
			if ($jq('#SelectRight').find('option').length != "0") {
				$jq('#SelectRight').find('option').each(function() {
					if ($jq(this).attr("selected") == false) {
						right++;
					}
				});
			}

			if (left == $jq('#SelectLeft').find('option').length
					&& $jq('#SelectRight').find('option').length == "0") {
				errorMessages += "Please Select Designation \n";
				flag = false;
			} else if ($jq('#SelectLeft').find('option').length == "0"
					&& right == $jq('#SelectRight').find('option').length) {
				errorMessages += "Please Select Designation \n";
				flag = false;
			} else if (left == $jq('#SelectLeft').find('option').length
					&& right == $jq('#SelectRight').find('option').length) {
				errorMessages += "Please Select Designation \n";
				flag = false;
			}
		}

	}
	for (var i = 0; i < document.getElementById('SelectRight').options.length; i++) {
		selectedValues += document.getElementById('SelectRight').options[i].value
				+ ",";
		status = true;
	}
	if (flag) {
		$jq('#pk').val(selectedValues);
		$jq('#param').val('manageAllowanceDetails');
		$jq.post('payBill.htm', $jq('#PayBillMasterBean').serialize(),
				function(html) {
					$jq("#result").html(html);
					$jq('#pk').val('');
				});
	} else {
		alert(errorMessages);
	}
}

function clearAllowanceCgfDetails() {
	$jq('#allowanceID').val('Select')
	$jq('#SelectLeft').find('option').remove().end();
	$jq('#SelectRight').find('option').remove().end();
}

function payEmpDetailsSearch() {
	if ($jq('#sfidSearchKey').val() == '') {
		alert("Please Enter SFID\n");
	} else {
		$jq('#param').val('PaysearchSFID');
		$jq.post('payBill.htm', $jq('#PayBillMasterBean').serialize(),
				function(html) {
					$jq("#result").html(html);
					displayProperties();
				});
		$jq('#payrunmonth').show();
	}
}

function displayProperties() {
	if ($jq('#ccsMemFlag option:selected').val() == 'Yes') {
		$jq("#ccsDiv").show();
	}

	if ($jq('#gpfFlag option:selected').val() == 'Yes') {
		$jq("#gpfDiv").show();
	}

	if ($jq('#fpaFlag option:selected').val() == 'Yes') {
		$jq("#fpaDiv").show();
	}

	if ($jq('#bankFlag option:selected').val() == 'Yes') {
		$jq("#bankDiv").show();
	}

	if ($jq('#quartersFlag option:selected').val() == 'Yes') {
		$jq("#quarterDiv").show();
	}

}

function changePayscale() {
	$jq('#payscaleId').val($jq('#payDesignationId option:selected').val());
	$jq('#gradePay').val($jq('#payDesignationId option:selected').val());
}

function updatePayEmpDeatils() {
	var flag = true;
	var errorMessage = "";
	if ($jq('#categoryID option:selected').val() == 'select') {
		errorMessage += "Please select Category \n";
		if (flag) {
			$jq('#categoryID').focus();
			flag = false;
		}
	}

	if ($jq('#basicPay').val() == '') {
		errorMessage += "Please enter basic pay.\n";
		if (flag) {
			$jq('#basicPay').focus();
			flag = false;
		}
	}

	if ($jq('#designationId option:selected').val() == 'select') {
		errorMessage += "Please select desgination.\n";
		if (flag) {
			$jq('#designationId').focus();
			flag = false;
		}
	}

	if ($jq('#incomeTaxAmt').val() == '') {
		errorMessage += "Please enter income tax amount\n";
		if (flag) {
			$jq('#incomeTaxAmt').focus();
			flag = false;
		}
	}
	if ($jq('#panCardNo').val() == '') {
		errorMessage += "Please enter pan card number\n";
		if (flag) {
			$jq('#panCardNo').focus();
			flag = false;
		}
	}
	if ($jq('#varIncrPts').val() == '') {
		errorMessage += "Please enter variable increment\n";
		if (flag) {
			$jq('#varIncrPts').focus();
			flag = false;
		}
	}

	if ($jq('#twoAddIncr').val() == '') {
		errorMessage += "Please enter two additional increment\n";
		if (flag) {
			$jq('#twoAddIncr').focus();
			flag = false;
		}
	}

	if ($jq('#ccsMemFlag option:selected').val() == 'Yes') {

		if ($jq('#ccsNo').val() == '') {
			errorMessage += "Please enter CCS Number\n";
			if (flag) {
				$jq('#ccsNo').focus();
				flag = false;
			}
		}

		if ($jq('#ccsSubAmt').val() == '') {
			errorMessage += "Please enter CCS Subscription amount\n";
			if (flag) {
				$jq('#ccsSubAmt').focus();
				flag = false;
			}
		}
	}

	if ($jq('#gpfFlag option:selected').val() == 'Yes') {

		if ($jq('#gpfAccountNo').val() == '') {
			errorMessage += "Please enter GPF/CPS Number\n";
			if (flag) {
				$jq('#gpfAccountNo').focus();
				flag = false;
			}
		}

		if ($jq('#gpfSubAmt').val() == '') {
			errorMessage += "Please enter GPF/CPS Subscription amount\n";
			if (flag) {
				$jq('#gpfSubAmt').focus();
				flag = false;
			}
		}
	}

	if ($jq('#cgeisGroupID option:selected').val() == 'select') {
		errorMessage += "Please select CEGIS Group \n";
		if (flag) {
			$jq('#cgeisGroupID').focus();
			flag = false;
		}
	}

	if ($jq('#cegis1').is(':checked') == false
			&& $jq('#cegis2').is(':checked') == false) {
		errorMessage += "Please select radio button \n";
		if (flag) {
			$jq('#cegis1').focus();
			flag = false;
		}
	}

	if ($jq('#fpaFlag option:selected').val() == 'Yes') {

		if ($jq('#fpaGradePay').val() == '') {
			errorMessage += "Please enter FPA Grade pay\n";
			if (flag) {
				$jq('#fpaGradePay').focus();
				flag = false;
			}
		}

	}
	if ($jq('#bankFlag option:selected').val() == 'Yes') {

		if ($jq('#bankAccNo').val() == '') {
			errorMessage += "Please enter bank account number\n";
			if (flag) {
				$jq('#bankAccNo').focus();
				flag = false;
			}
		}

		if ($jq('#bankBranch').val() == '') {
			errorMessage += "Please enter bank branch name\n";
			if (flag) {
				$jq('#bankBranch').focus();
				flag = false;
			}
		}
	}

	if ($jq('#quartersFlag option:selected').val() == 'Yes') {

		if ($jq('#quarterTypeId').val() == '') {
			errorMessage += "Please selet quarter type\n";
			if (flag) {
				$jq('#quarterTypeId').focus();
				flag = false;
			}
		}

		if ($jq('#quarterNo').val() == '') {
			errorMessage += "Please enter quarter no\n";
			if (flag) {
				$jq('#quarterNo').focus();
				flag = false;
			}
		}
		if ($jq('#electricityBillAmt').val() == '') {
			errorMessage += "Please enter electricity bill amount\n";
			if (flag) {
				$jq('#electricityBillAmt').focus();
				flag = false;
			}
		}
	}

	if (errorMessage == "") {
		$jq('#param').val('updatePayOneTimeDetails');
		$jq('#type').val($jq('#gradePay option:selected').text());
		$jq.post('payBill.htm', $jq('#PayBillMasterBean').serialize(),
				function(html) {
					$jq("#result1").html(html);
					$jq('#type').val('');
				});
	} else {
		alert(errorMessage);
	}
}

function payBillEmpsearch() {
	if ($jq('#searchSfid').val() == '') {
		alert("Please Enter SFID\n");
	} else {
		$jq('#param').val('PaysearchSFID');
		$jq.post('pay.htm', $jq('#PayBillBean').serialize(), function(html) {
			$jq("#result").html(html);

		});
	}
}

function updateEmpPayDetails() {
	var errorMessage = "";
	var flag = true;
	if ($jq('#basicPay').val() == "") {
		errorMessage += "Enter  value in Basic pay field.\n";
		if (flag) {
			flag = false;
			$jq('#basicPay').focus();
		}
	}
	if ($jq('#gradePay').val() == "") {
		errorMessage += "Enter  value in grade pay field.\n";
		if (flag) {
			flag = false;
			$jq('#gradePay').focus();
		}
	}
	if ($jq('#da').val() == "") {
		errorMessage += "Enter  value in Dearness Allowance field.\n";
		if (flag) {
			flag = false;
			$jq('#da').focus();
		}
	}
	if ($jq('#hra').val() == "") {
		errorMessage += "Enter  value in hra\n";
		if (flag) {
			flag = false;
			$jq('#hra').focus();
		}
	}
	if ($jq('#twoAddlIncr').val() == "") {
		errorMessage += "Enter  value in two additional increment field.\n";
		if (flag) {
			flag = false;
			$jq('#twoAddlIncr').focus();
		}
	}
	if ($jq('#specialPay').val() == "") {
		errorMessage += "Enter value in  Special pay field.\n";
		if (flag) {
			flag = false;
			$jq('#specialPay').focus();
		}
	}
	if ($jq('#tpt').val() == "") {
		errorMessage += "Enter value in  TPT field.\n";
		if (flag) {
			flag = false;
			$jq('#tpt').focus();
		}
	}
	if ($jq('#washAllowance').val() == "") {
		errorMessage += "Enter  value in wash allowance field.\n";
		if (flag) {
			flag = false;
			$jq('#washAllowance').focus();
		}
	}
	if ($jq('#deputAllowance').val() == "") {
		errorMessage += "Enter  value in Deputation allowance field.\n";
		if (flag) {
			flag = false;
			$jq('#deputAllowance').focus();
		}
	}
	if ($jq('#fpa').val() == "") {
		errorMessage += "Enter value in  FPA field.\n";
		if (flag) {
			flag = false;
			$jq('#fpa').focus();
		}
	}
	if ($jq('#xeroxAllowance').val() == "") {
		errorMessage += "Enter  value in xerox Allowance field.\n";
		if (flag) {
			flag = false;
			$jq('#xeroxAllowance').focus();
		}
	}
	if ($jq('#riskAllowance').val() == "") {
		errorMessage += "Enter value in  risk Allowance field.\n";
		if (flag) {
			flag = false;
			$jq('#riskAllowance').focus();
		}
	}
	if ($jq('#hindiIncentive').val() == "") {
		errorMessage += "Enter  value in hindi Incentive field.\n";
		if (flag) {
			flag = false;
			$jq('#hindiIncentive').focus();
		}
	}
	if ($jq('#variableIncr').val() == "") {
		errorMessage += "Enter value in  variable Incr field.\n";
		if (flag) {
			flag = false;
			$jq('#variableIncr').focus();
		}
	}
	if ($jq('#crMisc').val() == "") {
		errorMessage += "Enter  value in Misc feild.\n";
		if (flag) {
			flag = false;
			$jq('#crMisc').focus();
		}
	}
	if ($jq('#drMisc1').val() == "") {
		errorMessage += "Enter value in  Misc1 feild.\n";
		if (flag) {
			flag = false;
			$jq('#drMisc1').focus();
		}
	}
	if ($jq('#drMisc2').val() == "") {
		errorMessage += "Enter value in  Misc2 feild.\n";
		if (flag) {
			flag = false;
			$jq('#drMisc2').focus();
		}
	}
	if ($jq('#tada').val() == "") {
		errorMessage += "Enter value in  TADA feild.\n";
		if (flag) {
			flag = false;
			$jq('#tada').focus();
		}
	}
	if ($jq('#festivalAdv').val() == "") {
		errorMessage += "Enter value in  festival advance feild.\n";
		if (flag) {
			flag = false;
			$jq('#festivalAdv').focus();
		}
	}
	if ($jq('#medical').val() == "") {
		errorMessage += "Enter value in  medical feild.\n";
		if (flag) {
			flag = false;
			$jq('#medical').focus();
		}
	}
	if ($jq('#pli').val() == "") {
		errorMessage += "Enter value in  PLI feild.\n";
		if (flag) {
			flag = false;
			$jq('#pli').focus();
		}
	}
	if ($jq('#eol').val() == "") {
		errorMessage += "Enter value in  EOL/HPL feild.\n";
		if (flag) {
			flag = false;
			$jq('#eol').focus();
		}
	}
	if ($jq('#hbaLoan').val() == "") {
		errorMessage += "Enter value in  HBA loan feild.\n";
		if (flag) {
			flag = false;
			$jq('#hbaLoan').focus();
		}
	}
	if ($jq('#ltc').val() == "") {
		errorMessage += "Enter value in  LTC feild.\n";
		if (flag) {
			flag = false;
			$jq('#ltc').focus();
		}
	}
	if ($jq('#gpfRecovery').val() == "") {
		errorMessage += "Enter  value in GPF Recovery feild.\n";
		if (flag) {
			flag = false;
			$jq('#gpfRecovery').focus();
		}
	}
	if ($jq('#furn').val() == "") {
		errorMessage += "Enter  value in furn feild.\n";
		if (flag) {
			flag = false;
			$jq('#furn').focus();
		}
	}
	if ($jq('#pcLoan').val() == "") {
		errorMessage += "Enter value in  pc Loan feild.\n";
		if (flag) {
			flag = false;
			$jq('#pcLoan').focus();
		}
	}
	if ($jq('#profTax').val() == "") {
		errorMessage += "Enter  value in prof Tax feild.\n";
		if (flag) {
			flag = false;
			$jq('#profTax').focus();
		}
	}
	if ($jq('#gpfSa').val() == "") {
		errorMessage += "Enter  value in gpfSa feild.\n";
		if (flag) {
			flag = false;
			$jq('#gpfSa').focus();
		}
	}
	if ($jq('#elec').val() == "") {
		errorMessage += "Enter value in  electricity feild.\n";
		if (flag) {
			flag = false;
			$jq('#elec').focus();
		}
	}
	if ($jq('#carLoan').val() == "") {
		errorMessage += "Enter  value in car Loan feild.\n";
		if (flag) {
			flag = false;
			$jq('#carLoan').focus();
		}
	}
	if ($jq('#secondaryCess').val() == "") {
		errorMessage += "Enter  value in secondary Cess feild.\n";
		if (flag) {
			flag = false;
			$jq('#secondaryCess').focus();
		}
	}
	if ($jq('#gpf').val() == "") {
		errorMessage += "Enter  value in gpf feild.\n";
		if (flag) {
			flag = false;
			$jq('#gpf').focus();
		}
	}
	if ($jq('#water').val() == "") {
		errorMessage += "Enter value in  water feild.\n";
		if (flag) {
			flag = false;
			$jq('#water').focus();
		}
	}
	if ($jq('#scooterLoan').val() == "") {
		errorMessage += "Enter value in  scooter Loan feild.\n";
		if (flag) {
			flag = false;
			$jq('#scooterLoan').focus();
		}
	}
	if ($jq('#cess').val() == "") {
		errorMessage += "Enter  value in cess feild.\n";
		if (flag) {
			flag = false;
			$jq('#cess').focus();
		}
	}
	if ($jq('#cegis').val() == "") {
		errorMessage += "Enter  value in cegis feild.\n";
		if (flag) {
			flag = false;
			$jq('#cegis').focus();
		}
	}
	if ($jq('#rent').val() == "") {
		errorMessage += "Enter value in  rent feild.\n";
		if (flag) {
			flag = false;
			$jq('#rent').focus();
		}
	}
	if ($jq('#cycleLoan').val() == "") {
		errorMessage += "Enter  value in cycleLoan feild.\n";
		if (flag) {
			flag = false;
			$jq('#cycleLoan').focus();
		}
	}
	if ($jq('#incomeTax').val() == "") {
		errorMessage += "Enter  value in incomeTax feild.\n";
		if (flag) {
			flag = false;
			$jq('#incomeTax').focus();
		}
	}
	if ($jq('#cghs').val() == "") {
		errorMessage += "Enter value in  cghs feild.\n";
		if (flag) {
			flag = false;
			$jq('#cghs').focus();
		}
	}
	if ($jq('#ccs').val() == "") {
		errorMessage += "Enter value in ccs feild.\n";
		if (flag) {
			flag = false;
			$jq('#ccs').focus();
		}
	}
	if ($jq('#mess').val() == "") {
		errorMessage += "Enter  value in mess feild.\n";
		if (flag) {
			flag = false;
			$jq('#mess').focus();
		}
	}
	if ($jq('#lic').val() == "") {
		errorMessage += "Enter  value in lic feild.\n";
		if (flag) {
			flag = false;
			$jq('#lic').focus();
		}
	}
	if ($jq('#resSecu').val() == "") {
		errorMessage += "Enter  value in resSecu feild.\n";
		if (flag) {
			flag = false;
			$jq('#resSecu').focus();
		}
	}
	if ($jq('#ccsrecovery').val() == "") {
		errorMessage += "Enter  value in ccsrecovery feild.\n";
		if (flag) {
			flag = false;
			$jq('#ccsrecovery').focus();
		}
	}
	if ($jq('#regimentalFund').val() == "") {
		errorMessage += "Enter value in  regimentalFund feild.\n";
		if (flag) {
			flag = false;
			$jq('#regimentalFund').focus();
		}
	}
	if ($jq('#canfin').val() == "") {
		errorMessage += "Enter value in  canfin feild.\n";
		if (flag) {
			flag = false;
			$jq('#canfin').focus();
		}
	}
	if ($jq('#welC').val() == "") {
		errorMessage += "Enter value in  welC feild.\n";
		if (flag) {
			flag = false;
			$jq('#welC').focus();
		}
	}
	if ($jq('#benvolentFund').val() == "") {
		errorMessage += "Enter  value in benvolentFund feild.\n";
		if (flag) {
			flag = false;
			$jq('#benvolentFund').focus();
		}
	}
	if ($jq('#gic').val() == "") {
		errorMessage += "Enter value in  gic feild.\n";
		if (flag) {
			flag = false;
			$jq('#gic').focus();
		}
	}
	if ($jq('#welRefund').val() == "") {
		errorMessage += "Enter value in  welRefund feild.\n";
		if (flag) {
			flag = false;
			$jq('#welRefund').focus();
		}
	}
	if ($jq('#hdfc').val() == "") {
		errorMessage += "Enter value in  hdfc feild.\n";
		if (flag) {
			flag = false;
			$jq('#hdfc').focus();
		}
	}
	if ($jq('#courtAttachment').val() == "") {
		errorMessage += "Enter value in  courtAttachment feild.\n";
		if (flag) {
			flag = false;
			$jq('#courtAttachment').focus();
		}
	}
	if ($jq('#recMisc1').val() == "") {
		errorMessage += "Enter value in  Misc1 feild.\n";
		if (flag) {
			flag = false;
			$jq('#recMisc1').focus();
		}
	}
	if ($jq('#recMisc2').val() == "") {
		errorMessage += "Enter  value in Misc2 feild.\n";
		if (flag) {
			flag = false;
			$jq('#recMisc2').focus();
		}
	}
	if ($jq('#recMisc3').val() == "") {
		errorMessage += "Enter value in  Misc3 feild.\n";
		if (flag) {
			flag = false;
			$jq('#recMisc3').focus();
		}
	}
	if (errorMessage == "") {
		sumAll();
		$jq('#param').val('payEmpUpdate');
		$jq.post('pay.htm', $jq('#PayBillBean').serialize(), function(html) {
			$jq("#result1").html(html);
		});
	} else {
		alert(errorMessage);
	}
}
function sumAll() {
	var basicpay = parseFloat($jq('#basicPay').val());
	var gradepay = parseFloat($jq('#gradePay').val());
	var da = parseFloat($jq('#da').val());
	var hra = parseFloat($jq('#hra').val());
	var twoaddIncr = parseFloat($jq('#twoAddlIncr').val());
	var spay = parseFloat($jq('#specialPay').val());
	var tpt = parseFloat($jq('#tpt').val());
	var washallow = parseFloat($jq('#washAllowance').val());
	var depAllow = parseFloat($jq('#deputAllowance').val());
	var fpa = parseFloat($jq('#fpa').val());
	var xeroxAllow = parseFloat($jq('#xeroxAllowance').val());
	var riskAllow = parseFloat($jq('#riskAllowance').val());
	var hindiIncv = parseFloat($jq('#hindiIncentive').val());
	var varIncr = parseFloat($jq('#variableIncr').val());
	var misc = parseFloat($jq('#crMisc').val());
	var totalCreditsValue = basicpay + gradepay + da + hra + twoaddIncr + spay
			+ tpt + washallow + depAllow + fpa + xeroxAllow + riskAllow
			+ hindiIncv + varIncr + misc;
	var drMisc1 = parseFloat($jq('#drMisc1').val());
	var drMisc2 = parseFloat($jq('#drMisc2').val());
	var tada = parseFloat($jq('#tada').val());
	var festivalAdv = parseFloat($jq('#festivalAdv').val());
	var medical = parseFloat($jq('#medical').val());
	var pli = parseFloat($jq('#pli').val());
	var eol = parseFloat($jq('#eol').val());
	var hbaLoan = parseFloat($jq('#hbaLoan').val());
	var ltc = parseFloat($jq('#ltc').val());
	var gpfRecovery = parseFloat($jq('#gpfRecovery').val());
	var furn = parseFloat($jq('#furn').val());
	var pcLoan = parseFloat($jq('#pcLoan').val());
	var profTax = parseFloat($jq('#profTax').val());
	var gpfSa = parseFloat($jq('#gpfSa').val());
	var elec = parseFloat($jq('#elec').val());
	var carLoan = parseFloat($jq('#carLoan').val());
	var secondaryCess = parseFloat($jq('#secondaryCess').val());
	var gpf = parseFloat($jq('#gpf').val());
	var water = parseFloat($jq('#water').val());
	var scooterLoan = parseFloat($jq('#scooterLoan').val());
	var cess = parseFloat($jq('#cess').val());
	var cegis = parseFloat($jq('#cegis').val());
	var rent = parseFloat($jq('#rent').val());
	var cycleLoan = parseFloat($jq('#cycleLoan').val());
	var incomeTax = parseFloat($jq('#incomeTax').val());
	var cghs = parseFloat($jq('#cghs').val());
	var ccs = parseFloat($jq('#ccs').val());
	var mess = parseFloat($jq('#mess').val());
	var lic = parseFloat($jq('#lic').val());
	var resSecu = parseFloat($jq('#resSecu').val());
	var ccsrecovery = parseFloat($jq('#ccsrecovery').val());
	var regimentalFund = parseFloat($jq('#regimentalFund').val());
	var canfin = parseFloat($jq('#canfin').val());
	var welC = parseFloat($jq('#welC').val());
	var benvolentFund = parseFloat($jq('#benvolentFund').val());
	var gic = parseFloat($jq('#gic').val());
	var welRefund = parseFloat($jq('#welRefund').val());
	var hdfc = parseFloat($jq('#hdfc').val());
	var courtAttachment = parseFloat($jq('#courtAttachment').val());
	var misc1 = parseFloat($jq('#recMisc1').val());
	var misc2 = parseFloat($jq('#recMisc2').val());
	var misc3 = parseFloat($jq('#recMisc3').val());
	var totalCreditsValue = basicpay + gradepay + da + hra + twoaddIncr + spay
			+ tpt + washallow + depAllow + fpa + xeroxAllow + riskAllow
			+ hindiIncv + varIncr + misc;
	var totDebit = drMisc1 + drMisc2 + tada + festivalAdv + medical + pli + eol
			+ hbaLoan + ltc + gpfRecovery + furn + pcLoan + profTax + gpfSa
			+ elec + carLoan + secondaryCess + gpf + water + scooterLoan + cess
			+ cegis + rent + cycleLoan + incomeTax + cghs;
	var totRec = ccs + mess + lic + resSecu + ccsrecovery + regimentalFund
			+ canfin + welC + benvolentFund + gic + welRefund + hdfc
			+ courtAttachment + misc1 + misc2 + misc3;
	$jq('#totalCredits').val(totalCreditsValue);
	$jq('#totalCredits1').val(totalCreditsValue);
	$jq('#totalDebits').val(totDebit);
	$jq('#totalDebits1').val(totDebit);
	$jq('#totalRecovery').val(totRec);
	$jq('#totalRecovery1').val(totRec);
	var netpay = totalCreditsValue - totDebit;
	$jq('#netPay').val(netpay);
	$jq('#netPay1').val(netpay);
	var finalpay = netpay - totRec;
	$jq('#finalPay').val(finalpay);
	$jq('#finalPay1').val(finalpay);
}

function startAutoRun() {
	$jq("#autorun").replaceWith('AutoRun has been started please wait!...');
	$jq('#param').val('paystartautorun');
	$jq.post('pay.htm', $jq('#PayBillBean').serialize(), function(html) {
		$jq("#result").html(html);
	});
}
function startRegenerating() {
	$jq("#autorun").replaceWith(
			'Regenerating PayBill has been started please wait....');
	$jq('#param').val('regeneration');
	$jq.post('pay.htm', $jq('#PayBillBean').serialize(), function(html) {
		$jq("#result").html(html);
	});
}
function changeStatusToVisible() {
	var check = confirm("Once you run manual updates ,you can not regenerate again..\n Would you like to continue ?");
	if (check) {
		$jq("#manual").replaceWith(
				'Manual Updates have been started please wait....');
		$jq("#autorun").replaceWith('AutoRun completed');
		$jq("#regenerate").replaceWith('');
		$jq('#param').val('payupdatestatus');
		$jq.post('pay.htm', $jq('#PayBillBean').serialize(), function(html) {
			$jq("#result").html(html);
		});
	}
}
function completePayBill() {
	$jq("#completePay").replaceWith('processing....');
	$jq('#param').val('paycompletestatus');
	$jq.post('pay.htm', $jq('#PayBillBean').serialize(), function(html) {
		$jq("#result").html(html);
	});
}

function getMyPaySlip() {
	var errorMessage = "";
	var status = true;
	if ($jq('#paySlipYear').val() == "select") {
		status = false;
		errorMessage += "Please Select Year\n";
		$jq('#paySlipYear').focus();
	} else if ($jq('#paySlipMonth').val() == "select") {
		status = false;
		errorMessage += "Please Select Month\n"
		$jq('#paySlipMonth').focus();
	}
	if (status) {
		var requestDetails = {
			"param" : "showPaySlip",
			"paySlipMonth" : $jq('#paySlipMonth').val(),
			"paySlipYear" : $jq('#paySlipYear').val()
		};
		$jq.post('pay.htm', requestDetails, function(html) {
			$jq("#result").html(html);
		});
		clearPaySlip();
	} else {
		alert(errorMessage);
	}
}
function clearPaySlip() {
	$jq('#paySlipYear').val('select');
	$jq('#monthList').hide();
}
function submitPayDues() {
	var errorMessage = "";
	var flag = true;
	if ($jq('#amount').val() == "") {
		errorMessage += "Enter Pending amount\n";
		if (flag) {
			$jq('#amount').focus();
			flag = false;
		}
	}
	if ($jq('#deductionID option:selected').val() == "select") {
		errorMessage += "Select Pending type\n";
		if (flag) {
			$jq('#deductionID').focus();
			flag = false;
		}
	}

	if ($jq('#loanDeduType1').is(':checked') == false
			&& $jq('#loanDeduType2').is(':checked') == false) {
		errorMessage += "Please Select Credit / Debit\n";
		if (flag) {
			$jq('#loanDeduType1').focus();
			flag = false;
		}
	}

	if (errorMessage == "") {

		$jq('#param').val('updateEmpDues');
		$jq.post('payBill.htm', $jq('#PayBillMasterBean').serialize(),
				function(html) {
					$jq("#result1").html(html);
				});
	} else {
		alert(errorMessage);
	}
}

function runPendingPayBill() {
	$jq('#param').val('dryRun');
	$jq.post('pay.htm', $jq('#PayBillBean').serialize(), function(html) {
		$jq("#result").html(html);
	});
}
//new script code
function runPendingPayBill1(){
	var status=true
	if(status){
		 var requestDetails = {
				 "param"    : "dryRun",
				 "runMonth" : '01'+"-"+$jq('#paySlipMonth').val()+"-"+$jq('#paySlipYear').val(),
		 };
		 $jq.post('pay.htm', requestDetails, function(html) {
				$jq("#result").html(html);
			});
	}	
}
function searchPayDues() {
	if ($jq('#sfidSearchKey').val() == "") {
		alert("Please Enter SFID\n");
	} else {
		$jq('#param').val('getDueDetails');
		$jq.post('payBill.htm', $jq('#PayBillMasterBean').serialize(),
				function(html) {
					$jq("#result").html(html);
					$jq('#payrunmonth').show();
				});
	}
}

function searchEMPEol() {
	var errorMessage = "";
	var flag = true;

	if ($jq('#sfidSearchKey').val() == "") {
		errorMessage += "Please Enter SFID\n";
		if (flag) {
			$jq('#sfidSearchKey').focus();
			flag = false;
		}
	}
	if ($jq('#startDate').val() == "") {
		errorMessage += "Please Select Start Date\n";
		if (flag) {
			$jq('#startDate').focus();
			flag = false;
		}
	}
	if ($jq('#endDate').val() == "") {
		errorMessage += "Please Select End Date\n";
		if (flag) {
			$jq('#endDate').focus();
			flag = false;
		}
	}

	if (errorMessage == "") {
		$jq('#param').val('getpayeol');
		$jq.post('payBill.htm', $jq('#PayBillMasterBean').serialize(),
				function(html) {
					$jq("#result").html(html);
				});
	} else {
		alert(errorMessage);
	}
}

function acceptEMPEol() {

	var checkedValues = "";

	$jq("#leaveList tr:not(:first)").each(
			function() {
				if ($jq(this).find("td").eq(0).find(":input:checkbox").is(
						':checked')) {
					checkedValues += $jq(this).find("td").eq(0).find(
							":input:checkbox").attr('id')
							+ ","
				}
			});

	if (checkedValues == '') {
		alert("Please select any request");

	} else {
		$jq('#param').val('payEolAccept');
		$jq('#type').val(checkedValues);
		$jq.post('payBill.htm', $jq('#PayBillMasterBean').serialize(),
				function(html) {
					$jq("#result").html(html);
				});

	}
}
function alertAutoRun() {

	alert("AutoRun has not yet been started for this month");

}
function alertAutoRunManual() {

	alert("Manual updates have not yet been started for this month");

}
function portPayMastersDetails() {
	var flag = true;
	if ($jq('#portFromDate').val() == "") {
		alert("Please Select From Date");
		flag = false;
	} else if ($jq('#portToDate').val() == "") {
		alert("Please Select To Date");
		flag = false;
	}

	if (flag) {
		var requestDetails = {
			"param" : "managePortMasters",
			"portFromDate" : $jq('#portFromDate').val(),
			"portToDate" : $jq('#portToDate').val()
		};
		$jq.post('payBill.htm', requestDetails, function(html) {
			$jq("#result").html(html);
		});
		clearPayBillPortingMasters();
	}
}
function clearPayBillPortingMasters() {
	$jq('#portFromDate').val('');
	$jq('#portToDate').val('');
}

function checkDesignations() // Developed by Naresh
{
	$jq('#designationId').find('option').remove().end();
	$jq('#designationId').append(
			$jq("<option></option>").attr("value", '0').text('Select'));
	for (var i = 0; i < jsonPayDesigList.length; i++) {
		var flag = true;
		for (var j = 0; j < jsonPayDesigIds.length; j++) {
			if (parseInt(jsonPayDesigList[i].id) == parseInt(jsonPayDesigIds[j].designationId)) {
				flag = false;
				break;
			}
		}
		if (flag)
			$jq('#designationId').append(
					$jq("<option></option>").attr("value",
							jsonPayDesigList[i].id).text(
							jsonPayDesigList[i].name));
	}
}

function submitTAIDetails() {
	var flag = true;
	var errorMessage = "";
	if ($jq('#designationId').val() == 0) {
		errorMessage += "Please Select Designation Name\n";
		$jq('#designationId').focus();
		flag = false;
	}
	if ($jq('#gradePay').val() == "") {
		errorMessage += "Please Enter Grade Pay Amount\n";
		$jq('#gradePay').focus();
		flag = false;
	}
	if ($jq('#effDate').val() == "") {
		errorMessage += "Please Select Effective From value\n";
		$jq('#effDate').focus();
		flag = false;
	}
	if (errorMessage == "") {
		var requestDetails = {
			"param" : "TAIMasterManage",
			"designationId" : $jq('#designationId').val(),
			"gradePay" : $jq('#gradePay').val(),
			"effDate" : $jq('#effDate').val(),
			"pk" : $jq('#pk').val()
		};
		$jq.post('payScale.htm', requestDetails, function(html) {
			$jq("#result").html(html);
		});
		checkDesignations();
		clearTAIDetails();
	} else
		alert(errorMessage);
}
function editTAIDetails(id, desId, desName, amount, effDate) {
	var temp = "<option value='" + desId + "'>" + desName + "</option>";
	$jq('#designationId').append(temp);
	$jq('#designationId').val(desId);
	$jq('#designationId').attr("disabled", "disabled");
	$jq('#gradePay').val(amount);
	$jq('#effDate').val(effDate);
	$jq('#pk').val(id);
}
function clearTAIDetails() {
	$jq('#designationId').val('Select');
	if ($jq('#designationId').is(':disabled')) {
		$jq('#designationId').removeAttr('disabled');
	}
	$jq('#gradePay').val('');
	$jq('#effDate').val('');
	$jq('#pk').val('');
}
function deleteTAIDetails(id) {
	var check = confirm("Do you want to Delete it?");
	if (check) {
		var requestDetails = {
			"param" : "TAIMasterDelete",
			"pk" : id

		};
		$jq.post('payScale.htm', requestDetails, function(html) {
			$jq("#result").html(html);
		});
		checkDesignations();
		clearTAIDetails();
	}
}
function managePayBillAllwConfDetails() {
	var flag = true;
	if ($jq('#cofigurationId').val() == "select") {
		alert("Please Select Configuration Name");
		flag = false;
	} else if ($jq('#amount').val() == "") {
		alert("Please Enter Amount");
		flag = false;
	} else if ($jq('#effDate').val() == "") {
		alert("Please Enter Eff Date");
		flag = false;
	}
	if (flag) {
		var requestDetails = {
			"param" : "submitPayAllwDetailsHome",
			"cofigurationId" : $jq('#cofigurationId').val(),
			"amount" : $jq('#amount').val(),
			"effDate" : $jq('#effDate').val(),
			"pk" : $jq('#pk').val()
		};
		$jq.post('payScale.htm', requestDetails, function(html) {
			$jq("#result").html(html);
		});
		clearpayBillAllwConfDetails();
	}

}
function clearpayBillAllwConfDetails() {
	$jq('#cofigurationId').val('select');
	$jq('#amount').val('');
	$jq('#effDate').val('');

}
function editPayAllwDetails(id, alleTypeId, amount, effDate) {
	$jq('#cofigurationId').val(alleTypeId);
	$jq('#amount').val(amount);
	$jq('#effDate').val(effDate);
	$jq('#pk').val(id);

}
function deletePayAllwDetails(id) {
	var check = confirm("Do you want to Delete it?");
	if (check) {
		var requestDetails = {
			"param" : "deletePayAllwDetailsHome",
			"pk" : id
		};
		$jq.post('payScale.htm', requestDetails, function(html) {
			$jq("#result").html(html);
		});
		clearpayBillAllwConfDetails();
	}
}
function managePayBillAllwTypeDetails() {
	var flag = true;
	if ($jq('#name').val() == "") {
		alert("Please Enter Configuration Name");
		flag = false;
	}
	if (flag) {
		var requestDetails = {
			"param" : "submitPayAllwTypeHome",
			"name" : $jq('#name').val(),
			"pk" : $jq('#pk').val()
		};
		$jq.post('payScale.htm', requestDetails, function(html) {
			$jq("#result").html(html);
		});
		clearpayBillAllwTypeDetails();
	}
}
function editPayAllwType(id, allwType) {
	$jq('#name').val(allwType);
	$jq('#pk').val(id);
}
function deletePayAllwType(id) {
	var check = confirm("Do you want to Delete it?");
	if (check) {
		var requestDetails = {
			"param" : "deletePayAllwTypeHome",
			"pk" : id

		};
		$jq.post('payScale.htm', requestDetails, function(html) {
			$jq("#result").html(html);
		});
		clearpayBillAllwTypeDetails();
	}
}
function clearpayBillAllwTypeDetails() {
	$jq('#name').val('');
	$jq('#pk').val('');
}

/*------------start:Hindi Incentive Details---------------*/

function submitPayHindiDetails() {
	var flag = true;
	if (flag) {
		var requestDetails = {
			"param" : "submitPayHindiDetails",
			"sfidSearchKey" : $jq('#sfidSearchKey').val(),
			"startDate" : $jq('#startDate').val(),
			"endDate" : $jq('#endDate').val(),
			"inst" : $jq('#inst').val(),
			"type" : $jq('#type').val(),
			"pk" : $jq('#pk').val()
		};
		$jq.post('payBill.htm', requestDetails, function(html) {
			$jq("#result").html(html);
		});
	}
	clearPayHindiDetails();
}
function editHindiDetails(id, sfid, fromDate, toDate, presentInst) {
	$jq('#sfidSearchKey').val(sfid);
	$jq('#pk').val(id);
	$jq('#startDate').val(fromDate);
	$jq('#endDate').val(toDate);
	$jq('#inst').val(presentInst);
	$jq('#type').val('edit');
}
function clearPayHindiDetails() {
	$jq('#sfidSearchKey').val('');
	$jq('#pk').val('');
	$jq('#startDate').val('');
	$jq('#endDate').val('');
	$jq('#inst').val('');
	$jq('#type').val('');
}
function deleteHindiDetails(id) {
	var flag = confirm("Do you want to delete it?");
	if (flag) {
		var requestDetails = {
			"param" : "deletePayHindiDetails",
			"pk" : id
		};
		$jq.post('payBill.htm', requestDetails, function(html) {
			$jq("#result").html(html);
		});
	}
	clearPayHindiDetails();
}
function getEmpCoreDetails() {
	/*
	 * document.forms[0].param.value = "getEmpCoreDetails";
	 * document.forms[0].sfidSearchKey.value = $jq('#sfidSearchKey').val();
	 * document.forms[0].action = "payBill.htm"; document.forms[0].submit();
	 * clearEmpCoreDetails();
	 */
	var requestDetails = {
		"param" : "getEmpCoreDetails",
		"sfidSearchKey" : $jq('#sfidSearchKey').val()
	};
	$jq.post('payBill.htm', requestDetails, function(html) {
		$jq("#result").html(html);
	});
	$jq('body #ajaxBusy:visible').each(function() {
		if ($jq(this).is(':visible')) {
			$jq(this).remove();
		}
	});
	clearPayEmpOneTimeCoreDeatils();
}

function clearPayEmpOneTimeCoreDeatils() {
	$jq('#ccsMemFlag').val('No');
	$jq('#bankFlag').val('No');
	$jq('#fpaFlag').val('No');
	$jq('#gpfFlag').val('No');
	$jq('#incomeTaxAmt').val('0');
	$jq('#panCardNo').val('');
	$jq('#categoryID').val('select');
	$jq('#tptFlag').val('Yes');
	$jq('#ccsNo').val('');
	$jq('#ccsSubAmt').val('');
	$jq('#gpfAccountNo').val('');
	$jq('#gpfSubAmt').val('');
	$jq('#cgeisGroupID').val('select');
	$jq('#fpaGradePay').val('');
	$jq('#bankAccNo').val('');
	$jq('#bankBranch').val('');
	$jq("#bankDiv").hide();
	$jq("#fpaDiv").hide();
	$jq("#gpfDiv").hide();
	$jq("#ccsDiv").hide();
	$jq('#param').val('');
	$jq('#washAllwFlag').val('No');
}
function clearEmpCoreDetails() {
	$jq('#param').val('');
}
function getPaySlipMonthList() {
	var status = true;
	if ($jq('#paySlipYear').val() == 'select') {
		$jq("#monthList").html('');
		status = false;
	}
	if (status) {
		var requestDetails = {
			"param" : "getPaySlipMonthList",
			"paySlipYear" : $jq('#paySlipYear').val()
		};
		$jq.post('pay.htm', requestDetails, function(html) {
			$jq("#monthList").html(html);
		});
		$jq("#monthList").show();
	}
}
function funPayKeyPress(e, type) {
	var key;
	if (window.event) {
		key = window.event.keyCode;
	} else {
		key = e.which;
	}
	if (key == 13) {
		if (type == 'loan') {
			payLoanDetailsSearch();
		} else if (type == 'payDetails') {
			payBillEmpsearch();
		} else if (type == 'deduction') {
			payCCsearch();
		} else if (type == 'payDetails') {
			payBillEmpsearch();
		} else if (type == 'payDetails') {
			payBillEmpsearch();
		}

	}
}
function clearPayMessage() {

}
function funPayKeyPress(e, type) {
	var key;
	if (window.event) {
		key = window.event.keyCode;
	} else {
		key = e.which;
	}
	if (key == 13) {
		if (type == 'payEmpDetailsSearch')
			payEmpDetailsSearch();
		else if (type == 'payDetails') {
			payBillEmpsearch();
		}
	}
}

/*------------end:Hindi Incentive Details---------------*/

// Developed By Naresh
function getEmpName(component) 
{
	var value = $jq('#' + component.id).val().toUpperCase().trim();
	if(value != '')
	{
		var requestDetails = { 'sfidSearchKey' : value}
		$jq.post("empSFID.htm", requestDetails, function(html) {
			$jq('#result').html(html);
			$jq('#empname').val(ename);
		});
	}
	else
	{
		alert("Please Enter SFID\n");
	}
}
// Employee Bank Account
/*
function searchKey()
{
	if ($jq('#sfid').val().toUpperCase().trim() == '') 
	{
		alert("Plese Enter SFID\n");
		$jq('#sfid').focus();
	}
	else
	{
		var requestDetails = {  'param' : 'EmpSearchSFID',
								'sfidSearchKey' : $jq('#sfid').val().toUpperCase().trim()};
		$jq('#param').val('EmpSearchSFID');
		$jq.post('payBill.htm', requestDetails, function(html) {
			$jq('#semiblock').show();
		}); 
	}
}
*/
// Employee Bank Account Master
function clearField()
{
	$jq('#sfidSearchKey').val('');
}
function getBankAccountDetails(component)
{
	var value = $jq('#' + component.id).val().toUpperCase().trim();
	if(value != '')
	{
		var requestDetails = { 	'param' : 'bankRecord', 
								'sfidSearchKey' : value };
		$jq.post('payBill.htm', requestDetails, function(html) {
			$jq('#result').html(html);
			$jq('#result').show();
		});
		
	}
	else
	{
		alert("Please Enter SFID\n");
		return false;
	}
}

function submitDetails() 
{
	var errorMessage = '';
	var status = true;
	if ($jq('#sfidSearchKey').val().toUpperCase().trim() == '') 
	{
		errorMessage += "Plese Enter SFID\n";
		if (status) 
		{
			status = false;
			$jq('#sfidSearchKey').focus();
		}
	}
	if ($jq('#accNo').val() == '') 
	{
		errorMessage += "Please Enter Bank Account No\n";
		if (status) 
		{
			status = false;
			$jq('#accNo').focus();
		}
	}
	if ($jq('#branchName').val().trim() == '') 
	{
		errorMessage += "Please Enter Branch Name\n";
		if (status) 
		{
			status = false;
			$jq('#branchName').focus();
		}
	}
	if ($jq('#branchCode').val().trim() == '') 
	{
		errorMessage += "Please Enter Branch Code\n";
		if (status) 
		{
			status = false;
			$jq('#branchCode').focus();
		}
	}
	if (status) 
	{
		var requestDetails = {
			'param' : "submitDetails",
			'sfidSearchKey' : $jq('#sfidSearchKey').val().toUpperCase().trim(),
			'bankAccNo' : $jq('#accNo').val(),
			'bankBranch' : $jq('#branchName').val().trim(),
			'branchCode' : $jq('#branchCode').val().trim()
		};
		$jq.post('payBill.htm', requestDetails, function(html) {
			$jq('#result').html(html);
		});
		clearDetails();
	} 
	else 
	{
		alert(errorMessage);
	}
}
/*------------end:Hindi Incentive Details---------------*/
function getEmpSupplimentaryBillDetails(){
	var errorMessage = "";
	var flag=true;
	if($jq('#paySlipMonth').val()=='select'){
		errorMessage="Please Select the Month";
		flag =false;
	}
	if(flag){
		var requestDetails = {
				"param"          : "paySupplimentaryEmpList",
				"fromDate"  :'01'+"-"+$jq('#paySlipMonth').val()+"-"+$jq('#paySlipYear').val(),
			};
		$jq.post('pay.htm',requestDetails,function(html) {
			$jq("#empSuppList").html(html);
			});
			}else{
				alert(errorMessage);
			}
}
function clearDetails() 
{
	$jq('#sfidSearchKey').val('');
	$jq('#accNo').val('');
	$jq('#branchName').val('');
	$jq('#branchCode').val('');
}

function editDetails(sfid, baccno, bname, bcode)
{
	$jq('#sfidSearchKey').val(sfid);
	$jq('#accNo').val(baccno);
	$jq('#branchName').val(bname);
	$jq('#branchCode').val(bcode);
}
