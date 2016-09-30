function editLevel(id, rowID, parentID, size) {
	levelID = id;
	$jq("#newLevel").val(getRowNum("levelList", rowID, size));
	$jq("#parentLevel").val(parentID);
}
function clearLevel() {
	levelID = "";
	$jq("#newLevel").val("");
	$jq("#parentLevel").val("0");
}
function levelSubmit(type) {
	var status = true;
	var errorMessage = "";
	if ($jq.trim($jq("#newLevel").val()) == "") {
		errorMessage = "Please Enter New Level\n";
		if(status)
			status = false;
	}
	if ($jq("#parentLevel").val() == "0") {
		errorMessage += "Please Select Minimum Level of Reporting\n";
		if (status)
			status = false;
	}
	if (status) {
		var requestDetails = {
			"levelID" : levelID,
			"newLevel" : $jq("#newLevel").val(),
			"parentLevel" : $jq("#parentLevel").val(),
			"type" : type,
			"param" : "submitLevel"
		};
		$jq.ajax( {
			type : "POST",
			url : "orgStructure.htm?" + dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#displayTable").html(html);
				if ($jq('.success').is(':visible')) {
					addNewLevel();
					clearLevel();
				}
			}
		});
	} else {
		alert(errorMessage);
	}
}

function addNewLevel() {
	if (levelID == "") {
		for ( var i = 0; i < jsonArrayObject.length; i++) {
			if (jsonArrayObject[i].name == $jq("#newLevel").val()) {
				$jq("#parentLevel").append(
						'<option value=' + jsonArrayObject[i].id + '>'
								+ jsonArrayObject[i].name + '</option>');
			}
		}
	}
}

function deleteStr(id, type) {
	var check = confirm("Do you want to delete?");
	if(check) {
		var requestDetails = {
			"deleteID" : id,
			"type" : type,
			"param" : "delete"
		};
		$jq.ajax( {
			type : "POST",
			url : "orgStructure.htm?" + dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#displayTable").html(html);
				if (type == 'department')
					clearDepartment();
				else if (type == 'role')
					clearRole();
				else if (type == 'role')
					clearRole();
				else if (type == 'organizations')
					clearOrganization();
				if ($jq('.success').is(':visible'))
					deleteElement(id, type);
			}
		});
	}
}

function getReportsToList(type) {
	var requestDetails = {
		"parentLevel" : $jq("#parentLevel").val(),
		"type" : type,
		"param" : "getList"
	};
	$jq.ajax( {
		type : "POST",
		url : "orgStructure.htm?" + dispUrl,
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq("#result").html(html);
			if (type == 'deptReportsTo') {
				assignReportsToList();
			} else if (type == 'roleReportsTo') {
				assignRoleReportsToList();
			}
		}
	});
}

function assignReportsToList() {
	// delete all options from reports to & add new values
	$jq('#parentDept').find('option').remove().end().append(
			'<option value="0">Select</option>').val('0');
	// Add values from
	if (jsonChildArrayObject != null) {
		for ( var i = 0; i < jsonChildArrayObject.length; i++) {
			$jq("#parentDept").append(
					'<option value=' + jsonChildArrayObject[i].id + '>'
							+ jsonChildArrayObject[i].deptName + '</option>');

		}
	}
}

function assignRoleReportsToList() {
	// delete all options from reports to & add new values
	$jq('#parentRole').find('option').remove().end().append(
			'<option value="0">Select</option>').val('0');
	// Add values from
	if (jsonChildArrayObject != null) {
		for ( var i = 0; i < jsonChildArrayObject.length; i++) {
			$jq("#parentRole").append(
					'<option value=' + jsonChildArrayObject[i].id + '>'
							+ jsonChildArrayObject[i].roleName + '</option>');

		}
	}
}
function editDepartment(id, rowID, hierarchyLevel, reportsTo, deptTypeId, size,description,fax,phoneNumber,email) {
	nodeID = id;
	$jq("#departmentName").val(getRowNum("deptList", rowID, size));
	$jq("#parentLevel").val(hierarchyLevel);
	$jq("#departmentTypeId").val(deptTypeId);
	// delete all options from reports to & add new values
	$jq('#parentDept').find('option').remove().end().append(
			'<option value="0">Select</option>');
	// Add values from
	getReportsToList('deptReportsTo');
	$jq("#parentDept").val(reportsTo);
	$jq('#description').val(description);
	$jq('#fax').val(fax);
	$jq('#phoneNumber').val(phoneNumber);
	$jq('#email').val(email);
	
}

function getRowNum(id, rowID, size) {
	var selectedV = "1";
	if ($jq(".pagelinks").find("strong").html() != null) {
		selectedV = $jq.trim($jq(".pagelinks").find("strong").html());
	}
	return $jq.trim(
			$jq("#" + id).find("tr").eq(rowID - ((selectedV - 1) * size)).find(
					"td").eq(0).html()).replace(/\&amp;/g, '&').replace(
			/\&lt;/g, '<').replace(/</g, '&lt;');
}

function submitDepartment() {
	var status = true;
	var errorMessage = "";
	if ($jq.trim($jq("#departmentName").val()) == "") {
		errorMessage += "Please Enter DIR/PD/AD/TD/PROJ/DIV\n";
		if(status) {
			status = false;
			$jq("#departmentName").focus();
		}
	}
	if ($jq.trim($jq("#description").val()) == "") {
		errorMessage += "Please Enter Full Form\n";
		if(status) {
			status = false;
			$jq("#description").focus();
		}
	}
	if ($jq("#parentLevel").val() == "0") {
		errorMessage += "Please Select Hierarchy Level\n";
		if(status)
			status = false;
	}
	if ($jq("#parentDept").val() == "0") {
		errorMessage += "Please Select Reporting Department\n";
		if(status)
			status = false;
	}
	if ($jq("#departmentTypeId").val() == "select") {
		errorMessage += "Please Select Department Name\n";
		if(status)
			status = false;
	}
	if ($jq.trim($jq("#phoneNumber").val()) == "") {
		errorMessage += "Please Enter Phone Number\n";
		if(status) {
			status = false;
			$jq('#phoneNumber').focus();
		}
	}
	if ($jq.trim($jq("#email").val()) == "") {
		errorMessage += "Please Enter E-Mail\n";
		if(status) {
			status = false;
			$jq('#email').focus();
		}
	}
	if ($jq.trim($jq("#fax").val()) == "") {
		errorMessage += "Please Enter Fax \n";
		if(status) {
			status = false;
			$jq('#fax').focus();
		}
	}
		
	if (status) {
		var requestDetails = {
			"nodeID" : nodeID,
			"departmentName" : $jq("#departmentName").val(),
			"description" : $jq('#description').val(),
			"fax" : $jq('#fax').val(),
			"phoneNumber" : $jq('#phoneNumber').val(),
			"email" : $jq('#email').val(),
			"parentLevel" : $jq("#parentLevel").val(),
			"parentDept" : $jq("#parentDept").val(),
			"departmentTypeId" : $jq("#departmentTypeId").val(),
			"param" : "submitDept"
		};
		$jq.ajax( {
			type : "POST",
			url : "orgStructure.htm?" + dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#displayTable").html(html);
				if ($jq('.success').is(':visible')) {
					addNewDepartment();
					clearDepartment();
				}
			}
		});
	} else {
		alert(errorMessage);
	}
}

function deleteElement(id, type) {
	if (type == 'role') {
		type = "parentRole";
	} else if (type == 'department') {
		type = "parentDept";
	} else {
		type = "parentLevel";
	}
	$jq("#" + type + " option").each(function(i) {
		if ($jq(this).val() == id) {
			$jq(this).remove();
		}
	});
}

function addNewDepartment() {
	if (nodeID == "") {
		for ( var i = 0; i < jsonArrayObject.length; i++) {
			if (jsonArrayObject[i].deptName == $jq("#departmentName").val()) {
				$jq("#parentDept").append(
						'<option value=' + jsonArrayObject[i].id + '>'
								+ jsonArrayObject[i].deptName + '</option>');
			}
		}
	}
}

function clearDepartment() {
	nodeID = "";
	$jq('#parentDept').find('option').remove().end().append('<option value="0">Select</option>').val('0');
	$jq("#departmentName").val("");
	$jq("#parentLevel").val("0");
	$jq("#departmentTypeId").val('select');
	$jq('#description').val('');
	$jq('#phoneNumber').val('');
	$jq('#fax').val('');
	$jq('#email').val('');
}

function clearRole() {
	nodeID = "";
	$jq("#roleName").val("");
	$jq("#parentLevel").val("0");
	$jq("#parentRole").val("0");
	$jq("#departmentId").val("0");
	$jq('input:radio[id=isHead0]').removeAttr('checked');
	$jq('input:radio[id=isHead1]').removeAttr('checked');
}

function editRole(id, rowID, hierarchyLevel, reportsTo, deptId, size, isHead) {
	nodeID = id;
	$jq("#roleName").val(getRowNum("rolesList", rowID, size));
	$jq("#parentLevel").val(hierarchyLevel);
	$jq("#departmentId").val(deptId);
	isDefaultRole = isHead;
	if (isHead == 1) {
		$jq('input:radio[id=isHead1]').attr('checked', 'checked');
		$jq('input:radio[id=isHead0]').removeAttr('checked');
	} else {
		$jq('input:radio[id=isHead0]').attr('checked', 'checked');
		$jq('input:radio[id=isHead1]').removeAttr('checked');
	}

	// delete all options from reports to & add new values
	$jq('#parentRole').find('option').remove().end().append(
			'<option value="0">Select</option>');
	// Add values from
	getReportsToList('roleReportsTo');
	$jq("#parentRole").val(reportsTo);
}

function submitRole() {
	var status = true;
	var errorMessage = "";
	if ($jq("#parentLevel").val() == "0") {
		errorMessage += "Please Select Hierarchy level\n";
		if(status)
			status = false;
	}
	if ($jq("#parentRole").val() == "0") {
		errorMessage += "Please Select Reporting Role(Reports To)\n";
		if (status)
			status = false;
	}
	if ($jq.trim($jq("#roleName").val()) == "") {
		errorMessage += "Please Enter Department Role Name\n";
		if (status) {
			status = false;
			$jq("#roleName").focus();
		}
	}
	if ($jq("#departmentId").val() == "0") {
		errorMessage += "Please Select DIR/PD/AD/TD/PROJ/DIV role\n";
		if (status)
			status = false;
	}
	if (!$jq('input:radio[name=isHead]').is(':checked')) {
		errorMessage += "Please choose whether Head of the department is Yes or No\n";
		if (status)
			status = false;
	}
	if (status) {
		var requestDetails = {
			"nodeID" : nodeID,
			"roleName" : $jq("#roleName").val(),
			"parentLevel" : $jq("#parentLevel").val(),
			"parentRole" : $jq("#parentRole").val(),
			"departmentId" : $jq("#departmentId").val(),
			"isHead" : $jq('input:radio[name=isHead]:checked').val(),
			"headID" : headID,
			"param" : "submitRole"
		};
		$jq.ajax( {
			type : "POST",
			url : "orgStructure.htm?" + dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#displayTable").html(html);
				if ($jq('.success').is(':visible')) {
					addNewRole();
					clearRole();
				}
			}
		});
	} else {
		alert(errorMessage);
	}
}

function addNewRole() {
	if (nodeID == "") {
		for ( var i = 0; i < jsonArrayObject.length; i++) {
			if (jsonArrayObject[i].roleName == $jq("#roleName").val()) {
				$jq("#parentRole").append(
						'<option value=' + jsonArrayObject[i].id + '>'
								+ jsonArrayObject[i].roleName + '</option>');
			}
		}
	}
}
function checkHead() {
	var status = false;
	var departmentID = "";
	for ( var i = 0; i < jsonArrayObject.length; i++) {
		if (jsonArrayObject[i].departmentID == $jq("#departmentId").val()
				&& jsonArrayObject[i].isHead == 1) {
			status = true;
			headID = jsonArrayObject[i].id;
			departmentID = jsonArrayObject[i].departmentID;
			break;
		}
	}
	// If nodeID & departmentID is same we should not give an alert
	if (isDefaultRole != 1 && status
			&& $jq('input:radio[name=isHead]:checked').val() == 1) {
		// Head is already assigned to this department, And the user again
		// trying to assign this role as head. So give an alert
		if (!confirm("Already Head of the department is assigned to this department, Do you want to overwrite?")) {
			headID = "";
			$jq('input:radio[id=isHead0]').attr('checked', 'checked');
			$jq('input:radio[id=isHead1]').removeAttr('checked');
			return;
		}
	}
	if (headID == nodeID && $jq('input:radio[name=isHead]:checked').val() == 0) {
		// Head is not assigned to this department, so give an alert
		$jq('input:radio[id=isHead0]').removeAttr('checked');
		$jq('input:radio[id=isHead1]').attr('checked', 'checked');
		alert("Head of the department is not assigned to this department.");
		return;
	}
}

function searchOptions() {
	if ($jq("#searchingWith").val() == "0") {
		$jq("#searchingName").val("0");
		$jq("#selectNameDiv").hide();
		$jq("#searchBox").val("");
		$jq("#textBoxDiv").hide();
		$jq("#searchDept").val("0");
		$jq("#selectDeptDiv").hide();
	} else if ($jq("#searchingWith").val() == "sfid") {
		$jq("#searchingName").val("0");
		$jq("#selectNameDiv").show();
		$jq("#searchBox").val("");
		$jq("#textBoxDiv").hide();
		$jq("#searchDept").val("0");
		$jq("#selectDeptDiv").hide();
	} else if ($jq("#searchingWith").val() == "name") {
		$jq("#searchingName").val("0");
		$jq("#selectNameDiv").show();
		$jq("#searchBox").val("");
		$jq("#textBoxDiv").show();
		$jq("#searchDept").val("0");
		$jq("#selectDeptDiv").hide();
	} else if ($jq("#searchingWith").val() == "dept") {
		$jq("#searchingName").val("0");
		$jq("#selectNameDiv").hide();
		$jq("#searchBox").val("");
		$jq("#textBoxDiv").hide();
		$jq("#searchDept").val("0");
		$jq("#selectDeptDiv").show();
	}
}

function searchName() {
	if ($jq("#searchingName").val() == "0") {
		$jq("#searchBox").val("");
	}
}

function clearNormal() {
	$jq("#searchingWith").val("0");
	searchOptions();
	$jq("#parentRole").val("0");
	$jq("#mapSfID").val("");
	$jq('#isDefault').removeAttr('checked');
}

function searchDetails(type) {
	var status = true;
	var errorMessage = "";
	nodeID="";
	if ($jq("#searchingWith").val() == "0") {
		errorMessage = "Please select searching option\n";
		status = false;
		$jq("#searchingWith").focus();
	} else if ($jq("#searchingWith").val() == "sfid"
			&& $jq("#searchBox").val() == "") {
		errorMessage += "Please enter sfid for searching\n";
		if (status) {
			status = false;
			$jq("#searchBox").focus();
		}
	} else if ($jq("#searchingWith").val() == "name") {
		/*if ($jq("#searchingName").val() == "0") {
			errorMessage += "Please select searching name type\n";
			if (status) {
				status = false;
				$jq("#searchingName").focus();
			}
		}*/
		if ($jq("#searchBox").val() == "") {
			errorMessage += "Please enter name for search\n";
			if (status) {
				status = false;
				$jq("#searchBox").focus();
			}
		}
		$jq("#searchingName").val('contains');
	} else if ($jq("#searchingWith").val() == "dept"
			&& $jq("#searchDept").val() == "0") {
		errorMessage += "Please select department\n";
		if (status) {
			status = false;
			$jq("#searchDept").focus();
		}
	}
	if (status) {
		var requestDetails = {
			"searchingWith" : $jq("#searchingWith").val(),
			"searchDept" : $jq("#searchDept").val(),
			"searchingName" : $jq("#searchingName").val(),
			"searchBox" : $jq("#searchBox").val(),
			"roleName" : $jq("#roleName").val(),
			"type" : type,
			"param" : "search"
		};
		$jq.ajax( {
			type : "POST",
			url : "orgStructure.htm?" + dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#displayTable").html(html);
				if (type == 'role') {
					clearEmpRole();
				} else {
					clearNormal();
				}
			}
		});
	} else {
		alert(errorMessage);
	}
}

function deleteEmployee() {
	var status = true;
	var errorMessage = "";
	var checkedValues = "";
	$jq("#empList tr:not(:first)").each(
			function() {
				if ($jq(this).find("td").eq(0).find(":input:checkbox").is(
						':checked')) {
					checkedValues += $jq(this).find("td").eq(0).find(
							":input:checkbox").attr('id')
							+ ","
				}
			});
	if (checkedValues == jsonArrayObject) {
		errorMessage += "Please select any employee\n";
		status = false;
	}

	if (status) {
		var requestDetails = {
			"empGroup" : checkedValues.substring(0, checkedValues.length - 1),
			"param" : "deleteEmp"
		};
		$jq.ajax( {
			type : "POST",
			url : "orgStructure.htm?" + dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#displayTable").html(html);
				clearNormal();
			}
		});
	} else {
		alert(errorMessage);
	}
}
function checkValues(empGroup) {
	var checkedValues = new Array();
	checkedValues = empGroup.split(",");
	for(var i=0;i<checkedValues.length;i++) {
		$jq('#'+checkedValues[i]).attr('checked','checked');
	}
	changeParent('proceed');
	clearNormal();
}
function changeParent(proceed) {
	var status = true;
	var errorMessage = "";
	var checkedValues = "";
	/*if ($jq("#mapSfID").val() == "") {
		errorMessage += "Please Enter SFID\n";
		if (status) {
			status = false;
			$jq("#mapSfID").focus();
		}
	}*/

	if ($jq("#parentRole").val() == "0") {
		errorMessage += "Please select reporting Role\n";
		if (status) {
			status = false;
			$jq("#parentRole").focus();
		}
	}
	
	$jq("#empList tr:not(:first)").each(
			function() {
				if ($jq(this).find("td").eq(0).find(":input:checkbox").is(
						':checked')) {
					checkedValues += $jq(this).find("td").eq(0).find(
							":input:checkbox").attr('id')
							+ ","
				}
			});
	if (checkedValues == jsonArrayObject && $jq("#mapSfID").val()==jsonArrayObject) {
		errorMessage += "Please select any employee or enter SFID to map\n";
		status = false;
	}
var  defaultvalue = null;

if($jq("#isDefault").attr('checked')){
	defaultvalue = 1;
}else{
	defaultvalue = 0;
}

	if (status) {
		var requestDetails = {
			"empGroup" : checkedValues.substring(0, checkedValues.length - 1),
			"parentRole" : $jq("#parentRole").val(),
			"searchingWith" : $jq("#searchingWith").val(),
			"searchDept" : $jq("#searchDept").val(),
			"searchingName" : $jq("#searchingName").val(),
			"searchBox" : $jq("#searchBox").val(),
			"mapSfID" : $jq("#mapSfID").val(),
			"param" : "submitNormalEmp" ,
			"type"  : proceed,
			"isDefault":defaultvalue
		};
		$jq.ajax( {
			type : "POST",
			url : "orgStructure.htm?" + dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#displayTable").html(html);
			}
		});
	} else {
		alert(errorMessage);
	}
}

function clearEmpRole() {
	$jq("#searchingWith").val("0");
	searchOptions();
	$jq('#roleName').removeAttr('disabled');
	$jq("#roleName").val("0");
	$jq("#mapSfID").val("");
	$jq("#isDefault").removeAttr("checked");
	$jq("#roleEmpName").text("");
	$jq("#roleEmpNameDiv").hide();
}

function editEmpRole(id,retirementDate,sfid,empName, roleID, defRole) {
	nodeID = id;
	editSfid = sfid;
	isDefaultRole = defRole;
	$jq("#mapSfID").val(sfid);
	$jq("#roleName").val(roleID);
	if (defRole == 1) {
		$jq("#isDefault").attr("checked", "checked");
	} else {
		$jq("#isDefault").removeAttr("checked");
	}
	$jq("#roleEmpNameDiv").show();
	$jq("#roleEmpName").text(empName +"    (Retirement Date:"+retirementDate+")");
	$jq('#roleName').attr('disabled', 'disabled');
}

function deleteEmpRole(id, sfid, roleID) {
	var check = confirm("Do you want to delete ?");
	if(check) {
		var requestDetails = {
			"nodeID" : id,
			"editSfid" : sfid,
			"roleName" : roleID,
			"param" : "deleteEmpRole"
		};
		$jq.ajax( {
			type : "POST",
			url : "orgStructure.htm?" + dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#displayTable").html(html);
					if ($jq('.success').is(':visible')) {
						clearEmpRole();
					}
				}
		});
	}
}
function submitEmpRole() {
	var status = true;
	var errorMessage = "";
	var isDefault = "";
	if ($jq("#mapSfID").val() == "") {
		errorMessage += "Please enter sfid\n";
		status = false;
		$jq("#mapSfID").focus();
	}
	if ($jq("#roleName").val() == "0") {
		errorMessage += "Please select Roles\n";
		if (status) {
			status = false;
			$jq("#roleName").focus();
		}
	}
	if ($jq('input:checkbox[id=isDefault]').is(':checked')) {
		isDefault = "1";
	} else {
		isDefault = "0";
	}
	if (editSfid == $jq("#mapSfID").val() && isDefaultRole == isDefault) {
		isDefault = "";
	}
	if (status) {
		var requestDetails = {
			"nodeID" : nodeID,
			"editSfid" : editSfid,
			"isDefault" : isDefault,
			"roleName" : $jq("#roleName").val(),
			"mapSfID" : $jq("#mapSfID").val(),
			"param" : "submitEmpRole"
		};
		$jq.ajax( {
			type : "POST",
			url : "orgStructure.htm?" + dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#displayTable").html(html);
				if ($jq('.success').is(':visible')) {
					clearEmpRole();
				}
			}
		});
	} else {
		alert(errorMessage);
	}
}



function getSubordinateList(type,requestName,gazType,assignType,subordinateId) {
	nodeID = "";
	
	$jq("#requestName").each(function() {
		$jq("#requestName option").removeAttr("selected");
	});
	$jq("#gazType").val('0');
	selectedDesignation();
	$jq("#assignType").val('0');
	subOrdinatesList = new Object();
	
	var requestDetails = {
		"roleName" : $jq("#roleName").val(),
		"requestName" : requestName,
		"gazType" : gazType,
		"assignType" : assignType,
		"subordinateId" : subordinateId,
		"type" : type,
		"param" : "getSubordinates"
	};
	$jq.ajax( {
		type : "POST",
		url : "orgStructure.htm?" + dispUrl,
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq("#" + type).html(html);
			setSubOrdinatesList();
		}
	});
}

function setSubOrdinatesList() {
	$jq('#subordinateId').find('option').remove().end().append(
		'<option value="0">Select</option>').val('0');
	if (subOrdinatesList != null) {
		for ( var i = 0; i < subOrdinatesList.length; i++) {
			$jq("#subordinateId").append(
					'<option value=' + subOrdinatesList[i].key + '>'
							+ subOrdinatesList[i].name + '</option>');

		}
	}
}

function resetRoleConfiguration() {
	nodeID = "";
	$jq('#MoveLeft').click();
	$jq("#roleName").val('0');

	$jq("#requestName").each(function() {
		$jq("#requestName option").removeAttr("selected");
	});
	$jq("#gazType").val('0');
	selectedDesignation();
	$jq('#SelectRight2').find('option').remove().end();
	$jq("#assignType").val('0');
	$jq('#subordinateId').find('option').remove().end().append(
			'<option value="0">Select</option>').val('0');
	subOrdinatesList = new Object();
}
//new code
function editRoleSpecific(id, roleID, requestID, delegateType, assignedType,
		gazType, designationID) {
	$jq('#SelectRight').find('option').remove().end();
	$jq("#roleName").val(roleID);
	getSubordinateList('result',requestID,gazType,assignedType,delegateType);
	$jq("#assignType").val(assignedType);

	$jq("#gazType").val(gazType);
	selectedDesignation();
	$jq("#SelectLeft").val(designationID);
	
	$jq("#subordinateId").val(delegateType);
	selectedRequest();
	nodeID = id;
	multipleDesigSelet();
	multipleRequestSelect(requestID);
}
function multipleDesigSelet() {
	var multipleDesig=multipleDesigList.split(",");
	for ( var i = 0; i < multipleDesig.length; i++) {
		for ( var j = 0; j < document.getElementById('SelectLeft').options.length; j++) {
			if(multipleDesig[i]==document.getElementById('SelectLeft').options[j].value){
				document.getElementById('SelectLeft').options[j].selected = true;
			}
		}
	}
	$jq('#MoveRight').click();
}
function deleteRoleSpecific(id) {
	var del = confirm("Do you want to delete?");
	$jq("#requestName").each(function() {
		$jq("#requestName option").removeAttr("selected");
	});
	$jq("#gazType").val('0');
	selectedDesignation();
	$jq("#assignType").val('0');
	$jq("#subordinateId").val('0');
	if(del == true){
		var requestDetails = {
				"nodeID" : id,
				"param" : "deleteRoleConf"
			};
	}
	$jq.ajax( {
		type : "POST",
		url : "orgStructure.htm?" + dispUrl,
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq("#displayTable").html(html);
		}
	});
}
//back up of old code
/*function saveRoleConfiguration() {
	var errorMessage = "";
	var status = true;
	var selectedRequests = "";
	var selectedDesignations = "";
	var gazType = $jq("#gazType").val();
	
	if ($jq("#roleName").val() == "0") {
		errorMessage = "Please select role\n";
		status = false;
		$jq("#roleName").focus();
	}
	$jq('#requestName :selected').each(function(i, selected) {
		selectedRequests += $jq(selected).val() + ",";
	});
	if ($jq("#requestName").val() == "0") {
		errorMessage += "Please select request\n";
		if (status) {
			status = false;
			$jq("#requestName").focus();
		}
	} else {
		selectedRequests = selectedRequests.substring(0,
				selectedRequests.length - 1);
	}

	if ($jq("#assignType").val() == "0") {
		errorMessage += "Please select assigned type\n";
		if (status) {
			status = false;
			$jq("#assignType").focus();
		}
	}
	if ($jq("#subordinateId").val() == "0") {
		errorMessage += "Please select delegated subordinate\n";
		if (status) {
			status = false;
			$jq("#subordinateId").focus();
		}
	}
	if (status) {
		$jq("#SelectRight option").each(function() {
			selectedDesignations += $jq(this).val() + ",";					
		});	
		
		if (selectedDesignations != "") {
			selectedDesignations = selectedDesignations.substring(0,
					selectedDesignations.length - 1);
		}
		if(gazType=="0"){
			gazType="";
		}
		var requestDetails = {
			"nodeID" : nodeID,
			"roleName" : $jq("#roleName").val(),
			"requestName" : $jq("#requestName").val(),
			"gazType" : gazType,
			"designation" : selectedDesignations,
			"assignType" : $jq("#assignType").val(),
			"subordinateId" : $jq("#subordinateId").val(),
			"param" : "submitRoleConf"
		};
		$jq.ajax( {
			type : "POST",
			url : "orgStructure.htm?" + dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#displayTable").html(html);
				resetRoleConfiguration();
			}
		});
	} else {
		alert(errorMessage);
	}
}*/
function saveRoleConfiguration() {
	var errorMessage = "";
	var status = true;
	var selectedRequests = "";
	var selectedDesignations = "";
	var gazType = $jq("#gazType").val();
	
	if ($jq("#roleName").val() == "0") {
		errorMessage = "Please select role\n";
		status = false;
		$jq("#roleName").focus();
	}
$jq('#requestName :selected').each(function(i, selected) {
		selectedRequests += $jq(selected).val() + ",";
	});
	if ($jq("#requestName").val() == "0") {
		errorMessage += "Please select request\n";
		if (status) {
			status = false;
			$jq("#requestName").focus();
		}
	} else {
		selectedRequests = selectedRequests.substring(0,
				selectedRequests.length - 1);
	}

	if ($jq("#assignType").val() == "0") {
		errorMessage += "Please select assigned type\n";
		if (status) {
			status = false;
			$jq("#assignType").focus();
		}
	}
	if ($jq("#subordinateId").val() == "0") {
		errorMessage += "Please select delegated subordinate\n";
		if (status) {
			status = false;
			$jq("#subordinateId").focus();
		}
	}
	if (status) {
		$jq("#SelectRight option").each(function() {
			selectedDesignations += $jq(this).val() + ",";					
		});	
			
		if (selectedDesignations != "") {
			selectedDesignations = selectedDesignations.substring(0,
					selectedDesignations.length - 1);
		}
		$jq("#SelectRight2 option").each(function() {
			selectedRequests += $jq(this).val() + ",";					
		});	
			
		if (selectedRequests != "") {
			selectedRequests = selectedRequests.substring(0,
					selectedRequests.length - 1);
		}
		if(gazType=="0"){
			gazType="";
		}
		var requestDetails = {
			"nodeID" : nodeID,
			"roleName" : $jq("#roleName").val(),
			//"requestName" : $jq("#requestName").val(),
			"requestName" :selectedRequests,
			"gazType" : gazType,
			"designation" : selectedDesignations,
			"assignType" : $jq("#assignType").val(),
			"subordinateId" : $jq("#subordinateId").val(),
			"param" : "submitRoleConf"
		};
		$jq.ajax( {
			type : "POST",
			url : "orgStructure.htm?" + dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#displayTable").html(html);
				resetRoleConfiguration();
			}
		});
	} else {
		alert(errorMessage);
	}
}
function clearOrganization()
{
	$jq("#nodeID").val('');
	$jq("#name").val('');
	$jq('#description').val('');
	$jq('#fax').val('');
	$jq('#phoneNumber').val('');
	$jq('#email').val('');
	$jq("#address1").val('');
	$jq("#address2").val('');
	$jq("#address3").val('');
	$jq("#pincode").val('');
}

function submitOrganization()
{
	var status = true;
	var errorMessage = "";
	if ($jq("#name").val() == "") {
		errorMessage = "Enter Name\n";
		status = false;
		$jq("#departmentName").focus();
	}
	
	if ($jq("#description").val() == "") {
		errorMessage += "Please enter Full Form\n";
		status = false;
		$jq("#description").focus();
	}
	if ($jq.trim($jq("#phoneNumber").val()) == "") {
		errorMessage += "Please enter Phone Number\n";
		status = false;
		$jq("#phoneNumber").focus();
	}
	if ($jq.trim($jq("#fax").val()) == "") {
		errorMessage += "Please enter Fax \n";
		status = false;
		$jq("#fax").focus();
	}
	
	if ($jq.trim($jq("#email").val()) == "" || ($jq.trim($jq("#email").val()) != "" && !validateEmail($jq("#email").val()))) {
		errorMessage += "Please enter E-Mail\n";
		status = false;
		$jq("#email").focus();
	}
	if ($jq.trim($jq("#address1").val()) == "") {
		errorMessage += "Please enter Address1\n";
		status = false;
		$jq("#address1").focus();
	}
	if ($jq.trim($jq("#address2").val()) == "") {
		errorMessage += "Please enter Address2\n";
		status = false;
		$jq("#address2").focus();
	}
	if ($jq.trim($jq("#address3").val()) == "") {
		errorMessage += "Please enter Address3\n";
		status = false;
		$jq("#address3").focus();
	}
	if ($jq.trim($jq("#pincode").val()) == "") {
		errorMessage += "Please enter Pincode\n";
		status = false;
		$jq("#pincode").focus();
	}
	
	
	if (status) {
		var requestDetails = {
			"nodeID" : $jq('#nodeID').val(),
			"name" : $jq("#name").val(),
			"description" : $jq('#description').val(),
			"fax" : $jq('#fax').val(),
			"phoneNumber" : $jq('#phoneNumber').val(),
			"email" : $jq('#email').val(),
			"address1" : $jq("#address1").val(),
			"address2" : $jq("#address2").val(),
			"address3" : $jq("#address3").val(),
			"pincode" : $jq("#pincode").val(),
			"param" : "submitOrg"
		};
		$jq.ajax( {
			type : "POST",
			url : "orgStructure.htm?" + dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#displayTable").html(html);
				if ($jq('.success').is(':visible')) {
					clearOrganization();
				}
			}
		});
	} else {
		alert(errorMessage);
	}
}
function editOrganization(nodeID,jsonArrayObject)
{
	$jq('#nodeID').val(nodeID);
	for(var i=0;jsonArrayObject != null && i<jsonArrayObject.length;i++)
	{
		if(nodeID==jsonArrayObject[i].id)
		{
			
			$jq("#name").val(jsonArrayObject[i].name);
			$jq('#description').val(jsonArrayObject[i].description);
			$jq('#fax').val(jsonArrayObject[i].fax);
			$jq('#phoneNumber').val(jsonArrayObject[i].phoneNumber);
			$jq('#email').val(jsonArrayObject[i].email);
			$jq("#address1").val(jsonArrayObject[i].address1);
			$jq("#address2").val(jsonArrayObject[i].address2);
			$jq("#address3").val(jsonArrayObject[i].address3);
			$jq("#pincode").val(jsonArrayObject[i].pincode);
		}
	}
}
//script code for orgstructure
function multiSelectBox2() {
	$jq(function() {

		$jq("#MoveRight2,#MoveLeft2").click(function(event) {
			moveToSelectBox2(event, 2,0)
		});
		
	});
}
function moveToSelectBox2(event, i,j) {
	var id = $jq(event.target).attr("id");
	if(j!=0)
	{
	var selectFrom = id == "MoveRight2" + i+j ? "#SelectLeft2" + i : "#SelectRight2"
			+ i+'1';
	var moveTo = id == "MoveRight2" + i+j ? "#SelectRight2" + i+'1' : "#SelectLeft2" + i;
	var selectedItems = $jq(selectFrom + " :selected").toArray();
	$jq(moveTo).append(selectedItems);
	selectedItems.remove;
	}
	
	
	else
	{
		var selectFrom = id == "MoveRight2" + i ? "#SelectLeft2" + i : "#SelectRight2"
			+ i;
	    var moveTo = id == "MoveRight2" + i ? "#SelectRight2" + i : "#SelectLeft2" + i;
	    var selectedItems = $jq(selectFrom + " :selected").toArray();
		$jq(moveTo).append(selectedItems);
		selectedItems.remove;
	}	
}
function multiSelectBox2(){
	 $jq(function() {
       $jq("#MoveRight2,#MoveLeft2").click(function(event) {
           var id = $jq(event.target).attr("id");
           var selectFrom = id == "MoveRight2" ? "#SelectLeft2" : "#SelectRight2";
           var moveTo = id == "MoveRight2" ? "#SelectRight2" : "#SelectLeft2";

           var selectedItems = $jq(selectFrom + " :selected").toArray();
           $jq(moveTo).append(selectedItems);
           selectedItems.remove;
       });
   });
}
function selectedRequest() {
	$jq('#SelectRight2').find('option').remove().end();
	$jq('#SelectLeft2').find('option').remove().end();
	if (jsonArrayObject2 != null) {
		for ( var i = 0; i < jsonArrayObject2.length; i++) {
			if ($jq("#requestName").val() == 0 || $jq("#requestName").val() == undefined
					|| jsonArrayObject2[i].id == $jq("#requestName").val()) {
				$jq("#SelectLeft2").append(
						'<option value=' + jsonArrayObject2[i].id + '>'
								+ jsonArrayObject2[i].name + '</option>');
			}
		}
	}
}

function multipleRequestSelect() {
	var multipleRequest=multipleRequestList.split(",");
	for ( var i = 0; i < multipleRequest.length; i++) {
		for ( var j = 0; j < document.getElementById('SelectLeft2').options.length; j++) {
			if(multipleRequest[i]==document.getElementById('SelectLeft2').options[j].value){
				document.getElementById('SelectLeft2').options[j].selected = true;
			}
		}
	}
	$jq('#MoveRight2').click();
}


function checkBoxCheckDefault(type,row){
	
	errorMessage = "";
	var status = false;
	
	if($jq.trim(type.parentElement.parentNode.lastChild.textContent) == 'NO' ) {
		
		if(type.checked == true){
		$jq('#Pagination').find('input:checked').each(function(){ 
			
			if($jq.trim($jq(this).parent().parent().find('td').eq(6).text()) == 'YES' && !status){
				$jq('#'+type.id).removeAttr('checked');
				errorMessage += "You can't select both yes and no at a time";
				status = true;
				}
		});
		     if(!status){
		    	 $jq('#isDefault').removeAttr('disabled');
		 		$jq('#isDefault').removeAttr('checked');
		     }
		
		}
	}else {
			if(type.checked == true){
				$jq('#Pagination').find('input:checked').each(function(){ 
					if($jq.trim($jq(this).parent().parent().find('td').eq(6).text()) == 'NO' && !status){
						$jq('#'+type.id).removeAttr('checked');
						errorMessage += "You can't select both yes and no at a time";
						status = true;	
						}
				});
				if(!status){
					$jq('#isDefault').attr('checked','checked');
		        	$jq('#isDefault').attr('disabled','disabled');
		        	$jq('#isDefault').attr('class','regularrkr-checkbox')
				}
				
				
		}else{
			$jq('#isDefault').removeAttr('checked');
			$jq('#isDefault').removeAttr('disabled');
		} 
		}
	if(status){
		alert(errorMessage);
	}
	
	
}


function checkBoxCheckAllDefault(type, rowType) {
	
	var status = false;
	
if($jq('input:checkbox[name='+type+']').is(':checked')) {
		
		if(!$jq('.'+rowType)[0].disabled){
		$jq('.'+rowType).attr('checked', 'checked');
	}
	}else {
		 $jq("."+rowType).removeAttr("checked");
	}
	
	$jq('#Pagination tbody  tr').each(function(){
		if(($jq.trim($jq(this).find('td').eq(6).text()) == 'NO')  && ($jq(this).find('td').eq(0).find('input').attr('checked') == true )){
			$jq(this).find('td').eq(0).find('input').removeAttr("checked");
		   status = true;
		}
	});
	
	if(status){
		alert("You Can't Select Both Yes and No at same time");
	}
	
}



