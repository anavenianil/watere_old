function getRetirementReportDetailsList()
{
	var errorMessage ="";
	var status =true;
	if($jq('#yearId').val()=='Select'){
		errorMessage = "Please Select the Year."
		status=false;
	}
	if(status){
		var requestDetails = {
				"yearId":$jq('#yearId').val(),
				"param":'getRetirementReportDetailsList'
			};
		$jq.ajax( {
			type : "POST",
			url : "retirementDetails.htm?"+dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#displayTable").html(html);
				
			}

		});

	}else{
		alert(errorMessage);
	}	
}
function clearRetirementReportDetails()
{
	$jq('#yearId').val('');
	//getRetirementReportDetailsList();
}
function getRetirementReport(sfid,draftORconfirmed)
{
	if(draftORconfirmed=='draft')
	{
		window
		.open("./report.htm?param=retirementReport&returnValue=report&draftORConfirm=Draft&userSfid="+sfid+"&type=",
						'preview',
						'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	}
	else
	{
		window
		.open(
				"./report.htm?param=retirementReport&draftORConfirm=&returnValue=report&sfid="+sfid+"&type=",
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	}
}

function clearRetirementFinalDetails()
{
	$jq('#startDate').val('');
	$jq('#endDate').val('');
	getRetirementFinalDetailsList();
}

function getRetirementFinalDetails()
{
	var errorMessage = "";
	var status= true;
	
	if($jq('#startDate').val()=='')
	{
		errorMessage ="Enter Start Date\n";
		status=false;
	}
	if($jq('#endDate').val()=='')
	{
		errorMessage="Enter End Date\n";
		status= false;
	}
	if(status)
	{
		getRetirementFinalDetailsList();
		
	}	
		
	
	
}

function getRetirementFinalDetail(sfid)
{
	$jq('#userSfid').val(sfid);
	$jq('#param').val('RetirementFinalDetail');
	$jq('#type').val('RetirementFinalDetail');
	document.forms[0].action="retirementDetails.htm?"+dispUrl;
	document.forms[0].submit();	
	
	
}
function getRetirementFinalDetailsList()
{
	var requestDetails = {
			"startDate":$jq('#startDate').val(),
			"endDate":$jq('#endDate').val(),
			"param":'RetirementFinalDetailsList'
		};
	$jq.ajax( {
		type : "POST",
		url : "retirementDetails.htm?"+dispUrl,
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq("#displayTable").html(html);
			
		}

	});
}








function getCourseSubjCategory(jsonCourseSubjCategoryList,
		jsonCourseSubjSubCategoryList) {
	getCourseSubjCategory1(jsonCourseSubjCategoryList);
	getCourseSubjSubCategory(jsonCourseSubjSubCategoryList);
}
function getCourseSubjCategory1(jsonCourseSubjCategoryList) {
	$jq('#courseSubjCategoryId').find('option').remove().end();
	$jq("#courseSubjCategoryId").append('<option value="">Select</option>');
	var categoryId = $jq("#categoryId").val();

	for ( var i = 0; jsonCourseSubjCategoryList != null
			&& i < jsonCourseSubjCategoryList.length; i++) {
		if (jsonCourseSubjCategoryList[i].categoryId == categoryId) {
			$jq("#courseSubjCategoryId").append(
					'<option value=' + jsonCourseSubjCategoryList[i].id + '>'
							+ jsonCourseSubjCategoryList[i].name + '</option>');
		}
	}
}
function getCourseSubjSubCategory(jsonCourseSubjSubCategoryList) {
	$jq('#courseSubjSubCategoryId').find('option').remove().end();
	$jq("#courseSubjSubCategoryId").append('<option value="">Select</option>');
	var courseSubjCategoryId = $jq("#courseSubjCategoryId").val();

	for ( var i = 0; jsonCourseSubjSubCategoryList != null
			&& i < jsonCourseSubjSubCategoryList.length; i++) {
		if (jsonCourseSubjSubCategoryList[i].courseSubjCategoryId == courseSubjCategoryId) {
			$jq("#courseSubjSubCategoryId").append(
					'<option value=' + jsonCourseSubjSubCategoryList[i].id
							+ '>' + jsonCourseSubjSubCategoryList[i].name
							+ '</option>');
		}
	}
}

function lables(type) {
	var req = "<span class='failure'>*</span>";
	if (type == 'trainingInistitute') {
		document.title = 'Training Institute';
		$jq('#headTitle').html('Training Institute');
		$jq('#labelType').html("Training Institute Name" + req);

	} else if (type == 'courseSubjCategory') {
		document.title = 'Subject Category';
		$jq('#headTitle').html('Subject Category');
		$jq('#labelType').html("Subject Category Name" + req);

	} else if (type == 'courseSubjSubCategory') {
		document.title = 'Subject Sub Category';
		$jq('#headTitle').html('Subject Sub Category');
		$jq('#labelType').html("Subject Sub Category Name" + req);

	} else if (type == 'trainingType') {
		document.title = 'Training Type';
		$jq('#headTitle').html('Training Type');
		$jq('#labelType').html("Training Type" + req);
	} else if (type == 'trainingVenue') {
		document.title = 'Training Venue';
		$jq('#headTitle').html('Venue and Payment details');
		$jq('#labelType').html("Venue and Payment details" + req);
	}

}
function clearTrainingMaster(type) {
	var editMaterial = false;
    
	if ($jq('#id').length > 0)
		$jq('#id').val('');
	if ($jq('#typeValue').length > 0)
		$jq('#typeValue').val('');
	if ($jq('#name').length > 0)
		$jq('#name').val('');
	if ($jq('#description').length > 0)
		$jq('#description').val('');
	if ($jq('#counter').length > 0)
		$jq('#counter').val('500');
	if ($jq('#nameCounter').length > 0)
		$jq('#nameCounter').val('200');
	
	
	if ($jq('#trainingTypeId').length > 0)
		$jq('#trainingTypeId').val('');
	if ($jq('#trainingRegionId').length > 0)
		$jq('#trainingRegionId').val('');

	if ($jq('#categoryId').length > 0)
		$jq('#categoryId').val('Select');

	if ($jq('#courseSubjCategoryId').length > 0)
		$jq('#courseSubjCategoryId').val('');
	if ($jq('#trainingInistituteId').length > 0)
		$jq('#trainingInistituteId').val('');

	if ($jq('#cityId').length > 0)
		$jq('#cityId').val('');
	if ($jq('#address').length > 0)
		$jq('#address').val('');
	if ($jq('#phone').length > 0)
		$jq('#phone').val('');
	if ($jq('#fax').length > 0)
		$jq('#fax').val('');
	if ($jq('#email').length > 0)
		$jq('#email').val('');
	if ($jq('#ddAddress').length > 0)
		$jq('#ddAddress').val('');
	if ($jq('#ddCounter').length > 0)
		$jq('#ddCounter').val('200');
	if ($jq('#shortName').length > 0)
		$jq('#shortName').val('');

	if ($jq('#payableAt').length > 0)
		$jq('#payableAt').val('');

	if (document.forms[0].dd != null)
		$jq('#dd').attr('checked', false);
	if (document.forms[0].check != null)
		$jq('#check').attr('checked', false);
	if (document.forms[0].headOfficeFlag != null)
		$jq('#headOfficeFlag').attr('checked', false);
	if(document.forms[0].back != null)
	     $jq('#back').val('');
	if(document.forms[0].orderNo != null)
	     $jq('#orderNo').val('');
	if(document.forms[0].maxLimit != null)
	     $jq('#maxLimit').val('');
	
	
}

function manageTrainingMaster(type, jsonMasterDataList) {
	document.forms[0].param.value = "manage";
	document.forms[0].type.value = type;
	var errorMessage = "";
	var status = true;
	if (type == 'trainingType') {
		if ($jq("#typeValue").val() == "") {
			errorMessage += "Enter Training Type.\n";
			status = false;
			$jq("#typeValue").focus();
		}
	} else if (type == 'trainingInistitute') {
		if ($jq("#trainingTypeId").val() == "") {
			errorMessage += "Select Training Type.\n";
			status = false;
			$jq("#trainingTypeId").focus();
		}
		if ($jq("#categoryId").val() == "") {
			errorMessage += "Select Category.\n";
			status = false;
			$jq("#categoryId").focus();
		}
		if ($jq("#trainingRegionId").val() == "") {
			errorMessage += "Select Region.\n";
			status = false;
			$jq("#trainingRegionId").focus();
		}
		if ($jq("#typeValue").val() == "") {
			errorMessage += "Enter Institute Name.\n";
			status = false;
			$jq("#typeValue").focus();
		}
	} else if (type == 'trainingVenue') {
		if ($jq('#dd').is(':checked') && $jq('#check').is(':checked'))
			$jq('#ddFlag').val('3');
		else if ($jq('#dd').is(':checked') && !$jq('#check').is(':checked'))
			$jq('#ddFlag').val($jq('#dd').val());
		else if (!$jq('#dd').is(':checked') && $jq('#check').is(':checked'))
			$jq('#ddFlag').val($jq('#check').val());
		else if (!$jq('#dd').is(':checked') && !$jq('#check').is(':checked'))
			$jq('#ddFlag').val('0');

		if ($jq('#headOfficeFlag').is(':checked'))
			$jq('#headOfficeFlag').val('1');
		else
			$jq('#headOfficeFlag').val('0');
		if ($jq("#trainingTypeId").val() == "") {
			errorMessage += "Select Training Type.\n";
			status = false;
			$jq("#trainingTypeId").focus();
		}
		if ($jq("#categoryId").val() == "") {
			errorMessage += "Select Category.\n";
			status = false;
			$jq("#categoryId").focus();
		}
		if ($jq("#trainingInistituteId").val() == "") {
			errorMessage += "Select Training Institute.\n";
			status = false;
			$jq("#trainingInistituteId").focus();
		}
		if ($jq("#typeValue").val() == "") {
			errorMessage += "Enter Institute Name.\n";
			status = false;
			$jq("#typeValue").focus();
		}
		if ($jq("#city").val() == "") {
			errorMessage += "Select City.\n";
			status = false;
			$jq("#city").focus();
		}
		if ($jq("#address").val() == "") {
			errorMessage += "Enter Address\n";
			status = false;
			$jq("#address").focus();
		}
		if ($jq("#phone").val() == "") {
			errorMessage += "Enter Phone\n";
			status = false;
			$jq("#phone").focus();
		}
		if ($jq("#headOfficeFlag").val() == 1 && status) {
			var flag = true;
			for ( var i = 0; jsonMasterDataList != null
					&& jsonMasterDataList.length > 0
					&& i < jsonMasterDataList.length; i++) {
				if(jsonMasterDataList[i].id )
				if (flag
						&& jsonMasterDataList[i].trainingInistituteId == $jq(
								"#trainingInistituteId").val()
						&& jsonMasterDataList[i].headOfficeFlag == 1) {
					flag = false;
				}
			}
			if (!flag) {
				errorMessage += "Already Entered Head Office Details\n";
				status = false;
				$jq("#headOfficeFlag").focus();
			}
		}
	} else if (type == 'courseSubjCategory') {

		if ($jq("#categoryId").val() == "") {
			errorMessage += "Select Category.\n";
			status = false;
			$jq("#categoryId").focus();
		}
		if ($jq("#typeValue").val() == "") {
			errorMessage += "Enter Subject Category.\n";
			status = false;
			$jq("#typeValue").focus();
		}
	} else if (type == 'courseSubjSubCategory') {

		if ($jq("#categoryId").val() == "") {
			errorMessage += "Select Category.\n";
			status = false;
			$jq("#categoryId").focus();
		}
		if ($jq("#courseSubjCategoryId").val() == "") {
			errorMessage += "Select Subject Category.\n";
			status = false;
			$jq("#courseSubjCategoryId").focus();
		}
		if ($jq("#typeValue").val() == "") {
			errorMessage += "Enter Subject Sub Category.\n";
			status = false;
			$jq("#typeValue").focus();
		}
	}
	if (status) {

		if (document.forms[0].typeValue != null)
			$jq('#name').val($jq('#typeValue').val().trim());
		document.forms[0].action = "trainingMaster.htm";
		document.forms[0].submit();
	} else {
		alert(errorMessage);
	}
}
function textCounter(e, des, tbox, maxlimit) {
	var code = (e.keyCode ? e.keyCode : e.which);
	if (code == 8) {
		if ((des.val().length - 1) >= 0)
			tbox.val(maxlimit - (des.val().length - 1));
		return true;
	} else {
		if ((des.val().length) + 1 > maxlimit) {
			des.val(des.val().substring(0, maxlimit));
			// alert( 'Textarea value can only be 10 characters in length.' );
			return false;
		} else {
			tbox.val(maxlimit - ((des.val().length) + 1));
			return true;
		}
	}
}
function textCounter1(e, des, tbox, maxlimit) {
	var code = (e.keyCode ? e.keyCode : e.which);
	if (code == 8) {
		if ((des.val().length - 1) >= 0)
			tbox.val(maxlimit - (des.val().length - 1));
		return true;
	} else {
		if ((des.val().length) + 1 > maxlimit) {
			des.val(des.val().substring(0, maxlimit));
			// alert( 'Textarea value can only be 10 characters in length.' );
			return false;
		} else {
			tbox.val(maxlimit - ((des.val().length) + 1));
			return true;
		}
	}
}

function editTrainingData(type, jsonData, id, jsonTrainingList,
		jsonCourseSubjCategoryList) {
	for ( var i = 0; jsonData != null && i < jsonData.length; i++) {
		if (jsonData[i].id == id) {
			$jq('#id').val(jsonData[i].id);
			$jq('#typeValue').val(jsonData[i].name);
			if (document.forms[0].nameCounter != null) {
				$jq('#nameCounter').val(500);
				$jq('#nameCounter').val(
						$jq('#nameCounter').val() - jsonData[i].name.length);
			}

			if (jsonData[i].description != null
					&& jsonData[i].description != "") {
				$jq('#description').val(jsonData[i].description);
				$jq('#counter').val(500);
				$jq('#counter').val(
						$jq('#counter').val() - jsonData[i].description.length);
			}
			if (jsonData[i].categoryId != null && jsonData[i].categoryId != "") {
				$jq('#categoryId').val(jsonData[i].categoryId);
			}
			if (jsonData[i].trainingTypeId != null
					&& jsonData[i].trainingTypeId != "") {
				$jq('#trainingTypeId').val(jsonData[i].trainingTypeId);
			}
			if (type == 'trainingVenue') {
				getTrainingInistitute(jsonTrainingList)
			} else if (type == 'courseSubjSubCategory') {
				getCourseSubjCategory1(jsonCourseSubjCategoryList);
			}
			if (jsonData[i].courseSubjCategoryId != null
					&& jsonData[i].courseSubjCategoryId != "") {
				$jq('#courseSubjCategoryId').val(
						jsonData[i].courseSubjCategoryId);
			}
			if (jsonData[i].trainingInistituteId != null
					&& jsonData[i].trainingInistituteId != "") {
				$jq('#trainingInistituteId').val(
						jsonData[i].trainingInistituteId);
			}
			if (jsonData[i].trainingRegionId != null
					&& jsonData[i].trainingRegionId != "") {
				$jq('#trainingRegionId').val(
						jsonData[i].trainingRegionId);
			}
			if (jsonData[i].cityId != null && jsonData[i].cityId != "") {
				$jq('#cityId').val(jsonData[i].cityId);
			}
			if (jsonData[i].phone != null && jsonData[i].phone != "") {
				$jq('#phone').val(jsonData[i].phone);
			}
			if (jsonData[i].fax != null && jsonData[i].fax != "") {
				$jq('#fax').val(jsonData[i].fax);
			}
			if (jsonData[i].email != null && jsonData[i].email != "") {
				$jq('#email').val(jsonData[i].email);
			}
			if (jsonData[i].shortName != null && jsonData[i].shortName != "") {
				$jq('#shortName').val(jsonData[i].shortName);
			}
			if (jsonData[i].payableAt != null && jsonData[i].payableAt != "") {
				$jq('#payableAt').val(jsonData[i].payableAt);
			}

			if (jsonData[i].address != null && jsonData[i].address != "") {
				$jq('#address').val(jsonData[i].address);
				$jq('#counter').val(500);
				$jq('#counter').val(
						$jq('#counter').val() - jsonData[i].address.length);
			}
			if (jsonData[i].ddAddress != null && jsonData[i].ddAddress != "") {
				$jq('#ddAddress').val(jsonData[i].ddAddress);
				$jq('#ddcounter').val(200);
				$jq('#ddcounter').val(
						$jq('#ddcounter').val() - jsonData[i].address.length);
			}
			$jq('#dd').attr('checked', false);
			$jq('#check').attr('checked', false);
			if (jsonData[i].ddFlag != null && jsonData[i].ddFlag != "") {
				if (jsonData[i].ddFlag == 1 || jsonData[i].ddFlag == 3)
					$jq('#check').attr('checked', true);
				if (jsonData[i].ddFlag == 2 || jsonData[i].ddFlag == 3)
					$jq('#dd').attr('checked', true);
				if (jsonData[i].ddFlag == 0) {
					$jq('#dd').attr('checked', false);
					$jq('#check').attr('checked', false);
				}
			}
			$jq('#headOfficeFlag').attr('checked', false);
			if (jsonData[i].headOfficeFlag != null
					&& jsonData[i].headOfficeFlag != "") {
				$jq('#headOfficeFlag').val(jsonData[i].headOfficeFlag);
				if (jsonData[i].headOfficeFlag == 1)
					$jq('#headOfficeFlag').attr('checked', true);
				else
					$jq('#headOfficeFlag').attr('checked', false);

			}
			if (jsonData[i].orderNo != null
					&& jsonData[i].orderNo != "") {
				$jq('#orderNo').val(jsonData[i].orderNo);
			}

		}
	}
}
function deleteTrainingMaster(id, type) {
	var del = confirm("Do you want to delete this item?");
	if (del == true) 
	{
	$jq('#id').val(id);
	$jq('#type').val(type);
	if(type == 'trainingType')					$jq('#param').val('deleteTrainingType');
	else if(type == 'TrainingRegionMaster')		$jq('#param').val('deleteTrainingRegion');
	else if(type == 'trainingInistitute')		$jq('#param').val('deleteTrainingInistitute');
	else if(type == 'trainingVenue')			$jq('#param').val('deleteTrainingVenue');
	else if(type == 'courseDuration')			$jq('#param').val('deleteCourseDuration');
	else if(type == 'HRDGBoard')			$jq('#param').val('deleteHRDGBoard');
	else if(type == 'HRDGBoardMember')			$jq('#param').val('deleteHRDGBoardMember');
	else if(type == 'course')			$jq('#param').val('deleteCourse');
	else if(type == 'HRDGBoardType')			$jq('#param').val('deleteHRDGBoardType');
	else if(type == 'HRDGBoardMemberType')			$jq('#param').val('deleteHRDGBoardMemberType');
	if(type != 'course')
	{
	 $jq.post('trainingMaster.htm', $jq('#trainingMaster').serialize(),
			 function(html) {
			 $jq("#result").html(html);
			 $jq("#id").val('');
			 });
	}
	else 
	{
		
		 $jq.post('trainingMaster.htm', $jq('#trainingMaster').serialize(),
				 function(html) {
				 $jq("#displayTable").html(html);
				 $jq("#id").val('');
				 });
	}
	}
	 
	
}
function clearTrainingInistitutesMaster(type,trainingTypeId,trainingRegionId,jsonTrainingRegionList)
{
	$jq('#id').val('');
	$jq('#back').val('');
	$jq('#trainingTypeId').val(trainingTypeId);
	getTrainingRegionList(jsonTrainingRegionList);
	$jq('#trainingRegionId').val(trainingRegionId);
	getTrainingInistituteList();
}
function clearTrainingInistituteMaster(type)
{
	$jq('#id').val('');
	$jq('#back').val('');
	var editMaterial = false;

	if ($jq('#id').length > 0)
		$jq('#id').val('');
	if ($jq('#typeValue').length > 0)
		$jq('#typeValue').val('');
	if ($jq('#description').length > 0)
		$jq('#description').val('');
	if ($jq('#counter').length > 0)
		$jq('#counter').val('500');
	if ($jq('#nameCounter').length > 0)
		$jq('#nameCounter').val('200');

	if ($jq('#trainingTypeId').length > 0)
		$jq('#trainingTypeId').val('');
	if ($jq('#trainingRegionId').length > 0)
		$jq('#trainingRegionId').val('');

	if ($jq('#trainingInistituteId').length > 0)
		$jq('#trainingInistituteId').val('');

	getTrainingInistituteList();
	
}


function getTrainingInistitute(jsonTrainingInistituteList) {
	$jq('#trainingInistituteId').find('option').remove().end();
	$jq("#trainingInistituteId").append('<option value="">Select</option>');
	var trainingRegionId = $jq("#trainingRegionId").val();
	var trainingTypeId = $jq("#trainingTypeId").val();
	for ( var i = 0; jsonTrainingInistituteList != null
			&& i < jsonTrainingInistituteList.length; i++) {
		if (jsonTrainingInistituteList[i].trainingRegionId == trainingRegionId
				&& jsonTrainingInistituteList[i].trainingTypeId == trainingTypeId) {
			$jq("#trainingInistituteId").append(
					'<option value=' + jsonTrainingInistituteList[i].id + '>'
							+ jsonTrainingInistituteList[i].name + '</option>');
		}
	}

}

function clearCourses(type) {
	
	if ($jq('#trainingTypeId').length > 0)
		$jq('#trainingTypeId').val('');
	if ($jq('#trainingInistituteId').length > 0)
		$jq('#trainingInistituteId').val('');
	if ($jq('#trainingRegionId').length > 0)
		$jq('#trainingRegionId').val('');
	if ($jq('#courseSubjCategory').length > 0)
		$jq('#courseSubjCategory').val('');
	if ($jq('#courseSubjSubCategory').length > 0)
		$jq('#courseSubjSubCategory').val('');
	if ($jq('#name').length > 0)
		$jq('#name').val('');
	if ($jq('#fee').length > 0)
		$jq('#fee').val('');
	if ($jq('#serviceTax').length > 0)
		$jq('#serviceTax').val('');
	if ($jq('#nameCounter').length > 0)
		$jq('#nameCounter').val('200');
	if ($jq('#courseId').length > 0)
		$jq('#courseId').val('');
	if ($jq('#startDate').length > 0)
		$jq('#startDate').val('');
	if ($jq('#endDate').length > 0)
		$jq('#endDate').val('');
	if ($jq('#courseYear').length > 0)
		$jq('#courseYear').val('');
	if ($jq('#id').length > 0)
		$jq('#id').val('');
	if ($jq('#back').length > 0)
		$jq('#back').val('');

	if ($jq('#type').length > 0)
		$jq('#type').val(type);
	getCoursesList();
	
}
function clearCourse(type,trainingTypeId,trainingRegionId,trainingInistituteId,courseYear)
{
	
	if ($jq('#trainingTypeId').length > 0)
		$jq('#trainingTypeId').val(trainingTypeId);
	if ($jq('#trainingInistituteId').length > 0)
		$jq('#trainingInistituteId').val(trainingRegionId);
	if ($jq('#trainingInistituteId').length > 0)
		$jq('#trainingInistituteId').val(trainingInistituteId);
	if ($jq('#courseYear').length > 0)
		$jq('#courseYear').val(courseYear);
	
	if ($jq('#courseSubjCategory').length > 0)
		$jq('#courseSubjCategory').val('');
	if ($jq('#courseSubjSubCategory').length > 0)
		$jq('#courseSubjSubCategory').val('');
	if ($jq('#name').length > 0)
		$jq('#name').val('');
	if ($jq('#fee').length > 0)
		$jq('#fee').val('');
	if ($jq('#serviceTax').length > 0)
		$jq('#serviceTax').val('');
	if ($jq('#nameCounter').length > 0)
		$jq('#nameCounter').val('200');
	if ($jq('#courseId').length > 0)
		$jq('#courseId').val('');
	
	if ($jq('#id').length > 0)
		$jq('#id').val('');
	if ($jq('#back').length > 0)
		$jq('#back').val('');
	if ($jq('#type').length > 0)
		$jq('#type').val(type);
}

function courseSubmit(type) {
	var errorMessage = "";
	var status = true;
	if ($jq("#trainingTypeId").val() == "") {
		errorMessage += "Select Training Type.\n";
		status = false;
		$jq("#trainingTypeId").focus();
	}
	if ($jq("#trainingRegionId").val() == "") {
		errorMessage += "Select Region.\n";
		status = false;
		$jq("#trainingRegionId").focus();
	}
	if ($jq("#trainingInistituteId").val() == "") {
		errorMessage += "Select Training Institute.\n";
		status = false;
		$jq("#trainingInistituteId").focus();
	}
	if ($jq("#name").val() == "") {
		errorMessage += "Enter Course Name.\n";
		status = false;
		$jq("#name").focus();
	}
	if ($jq("#courseYear").val() == "") {
		errorMessage += "Enter Year.\n";
		status = false;
		$jq("#courseYear").focus();
	}
	if ($jq("#fee").val()=="0") {
		$jq("#fee").val('');
	}
	if ($jq("#serviceTax").val()=="0") {
		$jq("#serviceTax").val('');
	}
	if (status) {
		$jq('#type').val(type);
		$jq('#param').val('manageCourse');
		var requestDetails = {
				"id" : $jq("#id").val(),
				"name" : $jq("#name").val().trim(),
				"trainingTypeId" : $jq("#trainingTypeId").val(),
				"trainingRegionId" : $jq("#trainingRegionId").val(),
				"trainingInistituteId" : $jq("#trainingInistituteId").val(),
				"courseYear" : $jq("#courseYear").val(),
				"courseSubjCategory" : $jq("#courseSubjCategory").val().trim(),
				"courseSubjSubCategory" : $jq("#courseSubjSubCategory").val().trim(),
				"fee" : $jq("#fee").val(),
				"serviceTax" : $jq("#serviceTax").val().replace('%',''),
				"type" : $jq("#type").val(),
				"param" : 'manageCourse'
			};
			$jq.ajax( {
				type : "POST",
				url : "trainingMaster.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq("#displayTable").html(html);
					if ($jq('.success').is(':visible')) {
					$jq("#id").val('');
					$jq("#name").val('');
					$jq("#fee").val('');
					$jq("#serviceTax").val('');
					$jq("#courseSubjCategory").val('');
					$jq("#courseSubjSubCategory").val('');
					$jq('#nameCounter').val('200');
					$jq('#subjcounter').val('200');
					$jq('#subjsubcounter').val('200');
					}
					
				}

			});
	} else {
		alert(errorMessage);
	}
}
function editCourse(jsonMasterDataList, id) {
	for ( var i = 0; jsonMasterDataList != null
			&& jsonMasterDataList.length > 0 && i < jsonMasterDataList.length; i++) {
		if (jsonMasterDataList[i].id == id) {
			$jq('#id').val(jsonMasterDataList[i].id);
			$jq('#trainingRegionId').val(jsonMasterDataList[i].trainingRegionId);
			$jq('#trainingTypeId').val(jsonMasterDataList[i].trainingTypeId);
			$jq('#trainingInistituteId').val(
					jsonMasterDataList[i].trainingInistituteId);
			if (jsonMasterDataList[i].courseSubjCategory != null)
				$jq('#courseSubjCategory').val(
						jsonMasterDataList[i].courseSubjCategory);
			else
				$jq('#courseSubjCategory').val('');
						
			if (jsonMasterDataList[i].courseSubjSubCategory != null)
				$jq('#courseSubjSubCategory').val(
						jsonMasterDataList[i].courseSubjSubCategory);
			else
				$jq('#courseSubjSubCategory').val('');
			$jq('#name').val(jsonMasterDataList[i].name);
			if (jsonMasterDataList[i].fee != null)
				$jq('#fee').val(jsonMasterDataList[i].fee);
			else
				$jq('#fee').val('');
			if (jsonMasterDataList[i].serviceTax != null)
				$jq('#serviceTax').val(jsonMasterDataList[i].serviceTax);
			else
				$jq('#serviceTax').val('');
			
			

		}

	}

}

function courseDurationSubmit(type, jsonMasterDataList, jsonFinancialYearList) {

	// var j =
	// compareDates($jq("#startDate").val(),'dd-MMM-yyyy',$jq("#endDate").val(),'dd-MMM-yyyy')
	// alert(j);
	var startDate=$jq("#startDate").val();
	var endDate=$jq("#endDate").val();
	var errorMessage = "";
	var status = true;
	if ($jq("#startDate").val() == "") {
		errorMessage += "Select Start Date.\n";
		status = false;
		$jq("#startDate").focus();
	}
	if ($jq("#endDate").val() == "") {
		errorMessage += "Select End Date.\n";
		if (status) {
			status = false;
			$jq("#endDate").focus();
		}
	}
		if (($jq("#startDate").val() != "") && ($jq("#endDate").val() != "") && (compareDates(startDate, 'dd-MMM-yyyy', endDate,'dd-MMM-yyyy') == 1)) {
			errorMessage += "End Date greater than start Date.\n";
			if (status) {
				status = false;
				$jq("#startDate").focus();
			}
		}
		if (status) {
			var startDate = $jq("#startDate").val();
			var endDate = $jq("#endDate").val();
			var sDate1 = '';
			var eDate1 = ''
			for ( var i = 0; jsonFinancialYearList != null
					&& i < jsonFinancialYearList.length; i++) {
				if (jsonFinancialYearList[i].id == $jq("#courseYear").val()) {

					var sDate1 = jsonFinancialYearList[i].fromDate;
					var eDate1 = jsonFinancialYearList[i].toDate;
				}
			}

			for ( var i = 0; jsonMasterDataList != null
					&& i < jsonMasterDataList.length; i++) {
				var sDate = jsonMasterDataList[i].startDate;
				var eDate = jsonMasterDataList[i].endDate;
				var idValue = jsonMasterDataList[i].id;
				var id = $jq("#id").val();
				if(idValue != id)
				{
				if (startDate == sDate
						|| startDate == eDate
						|| endDate == sDate
						|| endDate == eDate
						|| (compareDates(startDate, 'dd-MMM-yyyy', sDate1,
								'dd-MMM-yyyy') == 1)
						|| (compareDates(endDate, 'dd-MMM-yyyy', eDate1,
								'dd-MMM-yyyy') == 1)
						|| (compareDates(sDate, 'dd-MMM-yyyy', startDate,
								'dd-MMM-yyyy') == 1 && compareDates(eDate,
								'dd-MMM-yyyy', endDate, 'dd-MMM-yyyy') == 0)) {
					status = false;
					$jq("#startDate").focus();
					errorMessage += "Course Duration Start Date or Course Duration End Date already assigned.\n";
				}
				}

			}
		}
	

	if (status) {
		var requestDetails = {
			"id" : $jq("#id").val(),
			"courseId" : $jq("#courseId").val(),
			"startDate" : $jq("#startDate").val(),
			"endDate" : $jq("#endDate").val(),
			"type" : $jq("#type").val(),
			"param" : 'manageCourseDuration'
		};
		$jq.ajax( {
			type : "POST",
			url : "trainingMaster.htm?"+dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#result").html(html);
				$jq('#startDate').val('');
				$jq('#endDate').val('');
				$jq("#id").val('');
			}

		});
	} else {
		alert(errorMessage);
	}

}



function getDesignation(jsonDesignationList) {
	$jq('#SelectLeft2').find('option').remove().end();
	$jq('#SelectRight2').find('option').remove().end();
	for ( var i = 0; jsonDesignationList != null
			&& jsonDesignationList.length > 0 && i < jsonDesignationList.length; i++) {
		if (jsonDesignationList[i].categoryID == document
				.getElementById('categoryId').value) {
			$jq("#SelectLeft2").append(
					'<option value=' + jsonDesignationList[i].id + '>'
							+ jsonDesignationList[i].name + '</option>');
		}
	}
}
function getDesignationList(designationList, id, jsonDesignationList) {

	var jsonDesignationList1 = jsonDesignationList;
	$jq('#SelectRight2').find('option').remove().end();
	for ( var i = 0; designationList != null && designationList.length > 0
			&& i < designationList.length; i++) {
		if (designationList[i].COURSEID == id) {
			$jq("#SelectRight2").append(
					'<option value=' + designationList[i].DESIGNATIONID
							+ ' selected=selected>' + designationList[i].NAME
							+ '</option>');
			for ( var j = 0; jsonDesignationList1 != null
					&& jsonDesignationList1.length > 0
					&& j < jsonDesignationList1.length; j++) {
				if (jsonDesignationList1[j].categoryID == document
						.getElementById('categoryId').value
						&& designationList[i].DESIGNATIONID == jsonDesignationList1[j].id) {
					jsonDesignationList1[j].id = '';
				}
			}
		}

	}
	$jq('#SelectLeft2').find('option').remove().end();

	for ( var i = 0; jsonDesignationList1 != null
			&& jsonDesignationList1.length > 0
			&& i < jsonDesignationList1.length; i++) {
		if (jsonDesignationList1[i].categoryID == document
				.getElementById('categoryId').value
				&& jsonDesignationList1[i].id != '') {
			$jq("#SelectLeft2").append(
					'<option value=' + jsonDesignationList1[i].id + '>'
							+ jsonDesignationList1[i].name + '</option>');
		}
	}
}
function getDesciplineList(desciplineList, id, jsonDesciplineList) {
	var jsonDesciplineList1 = jsonDesciplineList;
	$jq('#SelectRight1').find('option').remove().end();
	for ( var i = 0; desciplineList != null && desciplineList.length > 0
			&& i < desciplineList.length; i++) {
		if (desciplineList[i].COURSEID == id) {
			$jq("#SelectRight1").append(
					'<option value=' + desciplineList[i].DISCIPLINEID
							+ ' selected=selected>' + desciplineList[i].NAME
							+ '</option>');
			for ( var j = 0; jsonDesciplineList1 != null
					&& jsonDesciplineList1.length > 0
					&& j < jsonDesciplineList1.length; j++) {
				if (desciplineList[i].DISCIPLINEID == jsonDesciplineList1[j].id) {
					jsonDesciplineList1[j].id = '';
				}
			}
		}

	}
	$jq('#SelectLeft1').find('option').remove().end();

	for ( var i = 0; jsonDesciplineList1 != null
			&& jsonDesciplineList1.length > 0 && i < jsonDesciplineList1.length; i++) {
		if (jsonDesciplineList1[i].id != '') {
			$jq("#SelectLeft1").append(
					'<option value=' + jsonDesciplineList1[i].id + '>'
							+ jsonDesciplineList1[i].name + '</option>');
		}
	}
}
function multiSelectBox() {
	$jq(function() {

		$jq("#MoveRight1,#MoveLeft1").click(function(event) {
			moveToSelectBox(event, 1)
		});
		$jq("#MoveRight2,#MoveLeft2").click(function(event) {
			moveToSelectBox(event, 2)
		});
	});
}
function moveToSelectBox(event, i) {
	var id = $jq(event.target).attr("id");
	var selectFrom = id == "MoveRight" + i ? "#SelectLeft" + i : "#SelectRight"
			+ i;
	var moveTo = id == "MoveRight" + i ? "#SelectRight" + i : "#SelectLeft" + i;

	var selectedItems = $jq(selectFrom + " :selected").toArray();
	$jq(moveTo).append(selectedItems);
	selectedItems.remove;
}

function getCourseWithOutSubSubj(jsonCourseList) {
	$jq('#courseId').find('option').remove().end();
	$jq("#courseId").append('<option value="">Select</option>');
	var courseSubjCategoryId = $jq("#courseSubjCategoryId").val();
	var trainingTypeId = $jq("#trainingTypeId").val();
	var trainingInistituteId = $jq("#trainingInistituteId").val();
	var categoryId = $jq("#categoryId").val();
	var courseYear = $jq("#courseYear").val();

	for ( var i = 0; jsonCourseList != null && i < jsonCourseList.length; i++) {
		if (jsonCourseList[i].categoryId == categoryId
				&& jsonCourseList[i].courseYear == courseYear
				&& jsonCourseList[i].courseSubjCategoryId == courseSubjCategoryId
				&& jsonCourseList[i].trainingTypeId == trainingTypeId
				&& jsonCourseList[i].trainingInistituteId == trainingInistituteId
				&& (jsonCourseList[i].courseSubjSubCategoryId == null || jsonCourseList[i].courseSubjSubCategoryId == 0)) {
			$jq("#courseId").append(
					'<option value=' + jsonCourseList[i].id + '>'
							+ jsonCourseList[i].name + '</option>');
		}
	}
}
function getCourseWithSubSubj(jsonCourseList) {
	$jq('#courseId').find('option').remove().end();
	$jq("#courseId").append('<option value="">Select</option>');
	var courseSubjCategoryId = $jq("#courseSubjCategoryId").val();
	var trainingTypeId = $jq("#trainingTypeId").val();
	var trainingInistituteId = $jq("#trainingInistituteId").val();
	var courseSubjSubCategoryId = $jq("#courseSubjSubCategoryId").val();
	var categoryId = $jq("#categoryId").val();
	var courseYear = $jq("#courseYear").val();

	for ( var i = 0; jsonCourseList != null && i < jsonCourseList.length; i++) {
		if (jsonCourseList[i].categoryId == categoryId
				&& jsonCourseList[i].courseYear == courseYear
				&& jsonCourseList[i].courseSubjCategoryId == courseSubjCategoryId
				&& jsonCourseList[i].trainingTypeId == trainingTypeId
				&& jsonCourseList[i].trainingInistituteId == trainingInistituteId
				&& jsonCourseList[i].courseSubjSubCategoryId == courseSubjSubCategoryId) {// formatDate(parseDate(jsonCourseList[i].courseYear),'dd-MMM-yyyy').split('-')[1]
			// ==
			// formatDate(parseDate(courseYear),'dd-MMM-yyyy').split('-')[1]
			// &&
			// formatDate(parseDate(jsonCourseList[i].courseYear),'dd-MMM-yyyy').split('-')[2]
			// ==
			// formatDate(parseDate(courseYear),'dd-MMM-yyyy').split('-')[2]
			$jq("#courseId").append(
					'<option value=' + jsonCourseList[i].id + '>'
							+ jsonCourseList[i].name + '</option>');
		}
	}
}
function getCourseList(jsonCourseList) {
	if ($jq("#courseSubjSubCategoryId").val() == '')
		getCourseWithOutSubSubj(jsonCourseList);
	else
		getCourseWithSubSubj(jsonCourseList);
}
function emptyList(id) {
	$jq('#' + id).find('option').remove().end();
	$jq("#" + id).append('<option value="">Select</option>');
	selectList(id);

}
function selectList(id) {
	$jq('#' + id).find('option')[0].selected = true;
}
function getCourseDurationList() {
	var requestDetails = {
		"courseId" : $jq("#courseId").val(),
		"type" : $jq("#type").val(),
		"param" : 'CourseDurationDataList'
	};
	$jq.ajax( {
		type : "POST",
		url : "trainingMaster.htm?"+dispUrl,
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq("#result").html(html);
		}
	});
}
function editCourseDuration(jsonMasterDataList, id) {
	for ( var i = 0; jsonMasterDataList != null
			&& jsonMasterDataList.length > 0 && i < jsonMasterDataList.length; i++) {
		if (jsonMasterDataList[i].id == id) {
			$jq('#id').val(jsonMasterDataList[i].id);
			$jq('#startDate').val(jsonMasterDataList[i].startDate);
			$jq('#endDate').val(jsonMasterDataList[i].endDate);
		}

	}
}
function getReport(type) {
	var errorMessage = "";
	var status = true;
	
	if ($jq("#courseYear").val() == "") {
		errorMessage += "Enter Year.\n";
		status = false;
		$jq("#courseYear").focus();
	}
	if ($jq("#trainingInistituteId").val() == "") {
		errorMessage += "Training Institute.\n";
		status = false;
		$jq("#trainingInistituteId").focus();
	}

	if (status) {

		window
				.open(
						"./report.htm?param=courseReport&returnValue=report&courseYear="
								+ $jq("#courseYear").val()
								+ "&type=&trainingInistituteId="
								+ $jq("#trainingInistituteId").val(),
						'preview',
						'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	} else {
		alert(errorMessage);
	}
}
function getCourseQualification(type) {
	if ($jq("#categoryId").val() != "")
		document.forms[0].categoryName.value = document.forms[0].categoryId.options[document.forms[0].categoryId.selectedIndex].text;

	if ($jq("#trainingTypeId").val() != "")
		document.forms[0].trainingType.value = document.forms[0].trainingTypeId.options[document.forms[0].trainingTypeId.selectedIndex].text;

	if ($jq("#trainingInistituteId").val() != "")
		document.forms[0].trainingInistitute.value = document.forms[0].trainingInistituteId.options[document.forms[0].trainingInistituteId.selectedIndex].text;

	if ($jq("#courseSubjCategoryId").val() != "")
		document.forms[0].courseSubjCategory.value = document.forms[0].courseSubjCategoryId.options[document.forms[0].courseSubjCategoryId.selectedIndex].text;

	if ($jq("#courseSubjSubCategoryId").val() != "")
		document.forms[0].courseSubjSubCategory.value = document.forms[0].courseSubjSubCategoryId.options[document.forms[0].courseSubjSubCategoryId.selectedIndex].text;

	$jq('#type').val(type);
	$jq('#param').val('CourseQualification');
	document.forms[0].action = "trainingMaster.htm";
	document.forms[0].submit();
}

function getCourseFee(type) {
	if ($jq("#categoryId").val() != "")
		document.forms[0].categoryName.value = document.forms[0].categoryId.options[document.forms[0].categoryId.selectedIndex].text;

	if ($jq("#trainingTypeId").val() != "")
		document.forms[0].trainingType.value = document.forms[0].trainingTypeId.options[document.forms[0].trainingTypeId.selectedIndex].text;

	if ($jq("#trainingInistituteId").val() != "")
		document.forms[0].trainingInistitute.value = document.forms[0].trainingInistituteId.options[document.forms[0].trainingInistituteId.selectedIndex].text;

	if ($jq("#courseSubjCategoryId").val() != "")
		document.forms[0].courseSubjCategory.value = document.forms[0].courseSubjCategoryId.options[document.forms[0].courseSubjCategoryId.selectedIndex].text;

	if ($jq("#courseSubjSubCategoryId").val() != "")
		document.forms[0].courseSubjSubCategory.value = document.forms[0].courseSubjSubCategoryId.options[document.forms[0].courseSubjSubCategoryId.selectedIndex].text;

	$jq('#type').val(type);
	$jq('#param').val('CourseQualification');
	document.forms[0].action = "trainingMaster.htm";
	document.forms[0].submit();
}
function hrdgBoardSubmit(type) {
	var errorMessage = "";
	var status = true;
	if ($jq("#boardTypeId").val() == "") {
		errorMessage += "Select Board Type.\n";
		status = false;
		$jq("#boardTypeId").focus();
	}
	if ($jq("#yearId").val() == "") {
		errorMessage += "Select Year.\n";
		status = false;
		$jq("#yearId").focus();
	}
	if ($jq("#name").val() == "") {
		errorMessage += "Enter Board Name.\n";
		status = false;
		$jq("#name").focus();
	}

	if (status) {
		$jq('#type').val(type);
		$jq('#param').val('manageHRDGBoard');
		

			var requestDetails = {
				"yearId" : $jq("#yearId").val(),
				"boardTypeId" : $jq("#boardTypeId").val(),
				"type" : $jq("#type").val(),
				"id" : $jq("#id").val(),
				"name" : $jq("#name").val().trim(),
				"param" : 'manageHRDGBoard'
			};
			$jq.ajax({
				type : "POST",
				url : "trainingMaster.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq("#result").html(html);
					$jq('#name').val('');
					$jq("#id").val('');
				}
			});
		
		
	} else {
		alert(errorMessage);
	}
}

function clearHrdgBoard(yearId) {
	if(!($jq('#back').val()=='HRDGBoard'))
	{
	if ($jq('#boardTypeId').length > 0)
		$jq('#boardTypeId').val('');
	if ($jq('#yearId').length > 0)
		$jq('#yearId').val('');
	}
	else
	{
		if ($jq('#yearId').length > 0)
			$jq('#yearId').val(yearId);
	}
	if ($jq('#name').length > 0)
		$jq('#name').val('');
	if ($jq('#id').length > 0)
		$jq('#id').val('');
	if ($jq('#back').length > 0)
		$jq('#back').val('');
	getHRDGBoardDataList();

}
function editHRDGBoard(jsonHRDGBoardList, id) {
	for ( var i = 0; jsonHRDGBoardList != null && jsonHRDGBoardList.length > 0
			&& i < jsonHRDGBoardList.length; i++) {
		if (jsonHRDGBoardList[i].id == id) {
			$jq('#id').val(jsonHRDGBoardList[i].id);
			$jq('#boardTypeId').val(jsonHRDGBoardList[i].boardTypeId);
			$jq('#yearId').val(jsonHRDGBoardList[i].yearId);
			$jq('#name').val(jsonHRDGBoardList[i].name);
		}

	}
}
function deleteHRDGBoard(id,type) {
	
		$jq('#type').val(type);
		$jq('#param').val('deleteHRDGBoard');
		$jq("#id").val(id);

			var requestDetails = {
				"yearId" : $jq("#yearId").val(),
				"boardTypeId" : $jq("#boardTypeId").val(),
				"type" : $jq("#type").val(),
				"id" : $jq("#id").val(),
				"name" : $jq("#name").val(),
				"param" : 'deleteHRDGBoard'
			};
			$jq.ajax( {
				type : "POST",
				url : "trainingMaster.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq("#result").html(html);
					$jq('#name').val('');
					$jq("#id").val('');
				}
			});
		
		
	
}
function hrdgBoardMemberSubmit(type,jsonHRDGBoardMemberTypeList,jsonHRDGBoardMemberList) {
	var errorMessage = "";
	var status = true;
	
	if ($jq("#sfid").val() == "") {
		errorMessage += "Enter SFID.\n";
		status = false;
		$jq("#sfid").focus();
	}
	if ($jq("#memberTypeId").val() == "") {
		errorMessage += "Select Member Type.\n";
		status = false;
		$jq("#memberTypeId").focus();
	}
	var memeberName ="";
	if(status)
	{
		var memberCnt = 0;
		for ( var i = 0; jsonHRDGBoardMemberTypeList != null && jsonHRDGBoardMemberTypeList.length > 0
		&& i < jsonHRDGBoardMemberTypeList.length; i++) {
				if (jsonHRDGBoardMemberTypeList[i].id == $jq("#memberTypeId").val()) {
					memberCnt = jsonHRDGBoardMemberTypeList[i].maxLimit;
					memeberName = jsonHRDGBoardMemberTypeList[i].name;
				}

		}
		var totalmemberCnt = 0;
		for ( var i = 0; jsonHRDGBoardMemberList != null && jsonHRDGBoardMemberList.length > 0
		&& i < jsonHRDGBoardMemberList.length; i++) {
				if (jsonHRDGBoardMemberList[i].memberTypeId == $jq("#memberTypeId").val() && jsonHRDGBoardMemberList[i].id !=$jq("#id").val()) {
					totalmemberCnt++;
				}

		}
		
		
		if(memberCnt < totalmemberCnt+1)
		{
			errorMessage +="You cannot add more than "+memberCnt+" person"+((memberCnt>1)?"s":"")+" as "+memeberName;// "Change Member Type.You can't add More than "+memberCnt+"\n";
			status = false;
			$jq("#memberTypeId").focus();
		}
		
	}

	if (status) {

		var requestDetails = {
			"yearId" : $jq("#yearId").val(),
			"boardId" : $jq("#boardId").val(),
			"type" : $jq("#type").val(),
			"id" : $jq("#id").val(),
			"boardMemberSfid" : $jq("#sfid").val().trim(),
			"memberTypeId" : $jq("#memberTypeId").val(),
			"param" : 'manageHRDGBoardMember'
		};
		$jq.ajax( {
			type : "POST",
			url : "trainingMaster.htm?"+dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#result").html(html);
				if ($jq('.success').is(':visible')) {
				$jq('#memberTypeId').val('');
				$jq('#sfid').val('');
				$jq("#id").val('');
				}
		}
			
		});
	} else {
		alert(errorMessage);
	}
}

function clearHrdgBoardMember() {
	
	if ($jq('#sfid').length > 0)
		$jq('#sfid').val('');
	if ($jq('#memberTypeId').length > 0)
		$jq('#memberTypeId').val('');
	if ($jq('#id').length > 0)
		$jq('#id').val('');

}
function editHRDGBoardMember(jsonHRDGBoardMemberList, id) {
	for ( var i = 0; jsonHRDGBoardMemberList != null
			&& jsonHRDGBoardMemberList.length > 0
			&& i < jsonHRDGBoardMemberList.length; i++) {
		if (jsonHRDGBoardMemberList[i].id == id) {
			$jq('#id').val(jsonHRDGBoardMemberList[i].id);
			$jq('#boardId').val(jsonHRDGBoardMemberList[i].boardId);
			$jq('#yearId').val(jsonHRDGBoardMemberList[i].yearId);
			$jq('#sfid').val(jsonHRDGBoardMemberList[i].sfid);
			$jq('#memberTypeId').val(jsonHRDGBoardMemberList[i].memberTypeId);

		}

	}
}
function deleteHRDGBoardMember(id,type) {
//	$jq("#id").val(id);
//	$jq("#type").val(type);
//	
//	var requestDetails = {
//			"yearId" : $jq("#yearId").val(),
//			"boardId" : $jq("#boardId").val(),
//			"type" : $jq("#type").val(),
//			"id" : $jq("#id").val(),
//			"param" : 'deleteHRDGBoardMember'
//		};
//		$jq.ajax( {
//			type : "POST",
//			url : "trainingMaster.htm",
//			data : requestDetails,
//			cache : false,
//			success : function(html) {
//				$jq("#result").html(html);
//				$jq('#memberTypeId').val('');
//				$jq('#sfid').val('');
//				$jq("#id").val('');
//			}
//		});

}
function getBoardList(jsonHRDGBoardList) {
	$jq('#boardId').find('option').remove().end();
	$jq("#boardId").append('<option value="">Select</option>');
	var yearId = $jq("#yearId").val();

	for ( var i = 0; jsonHRDGBoardList != null && i < jsonHRDGBoardList.length; i++) {
		if (jsonHRDGBoardList[i].yearId == yearId) {
			$jq("#boardId").append(
					'<option value=' + jsonHRDGBoardList[i].id + '>'
							+ jsonHRDGBoardList[i].name + '</option>');
		}
	}
	getBoardMemberList();
}
function getBoardMemberList() {
	var requestDetails = {
		"yearId" : $jq("#yearId").val(),
		"boardId" : $jq("#boardId").val(),
		"type" : $jq("#type").val(),
		"param" : 'HRDGBoardMemberDataList'
	};
	$jq.ajax( {
		type : "POST",
		url : "trainingMaster.htm?"+dispUrl,
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq("#result").html(html);
			$jq('#memberTypeId').val('');
			$jq('#sfid').val('');
			$jq("#id").val('');
		}
	});

}
function trainingCirculationSubmit() {
	var errorMessage = "";
	var status = true;
	if ($jq("#circulationDate").val() == "") {
		errorMessage += "Select Circulation Date.\n";
		status = false;
		$jq("#circulationDate").focus();
	}
	if ($jq("#ionId").val() == "") {
		errorMessage += "Select Letter Number.\n";
		status = false;
		$jq("#ionId").focus();
	}
	if ($jq("#venueId").val() == "") {
		errorMessage += "Select Venue.\n";
		status = false;
		$jq("#venueId").focus();
	}
	if ($jq("#nominationStartDate").val() == "") {
		errorMessage += "Select Nomination Start Date.\n";
		status = false;
		$jq("#nominationStartDate").focus();
	}
	if ($jq("#nominationEndDate").val() == "") {
		errorMessage += "Select Nomination End Date.\n";
		status = false;
		$jq("#nominationEndDate").focus();
	}
	if ((document.getElementById('files') != null && $jq('#files').val() == '') && $jq('#brochure').val() == '') {
		errorMessage += "Upload Brochure.\n";
		status = false;
		$jq("#files").focus();
	}
	if (document.getElementById('SelectRight2').length==0) {
		errorMessage += "Select Departments to send the Circulation.\n";
		status = false;
		
	}
	if (status) {
		var nomStartDate = $jq("#nominationStartDate").val();
		var nomEndDate = $jq("#nominationEndDate").val();
		var sDate = document.getElementById('durationStartDate').innerHTML.trim();//$jq("#durationStartDate").val().trim();
		var circulationDate = $jq("#circulationDate").val().trim();
		var s = formatDate(new Date(),'dd-MMM-yyyy')
		
		if (compareDates( s, 'dd-MMM-yyyy',sDate, 'dd-MMM-yyyy') == 1)
		 {
			errorMessage += "Course Duration Start Date should be greater than today.(Course is already conducted)\n";
			status = false;
			$jq("#circulationDate").focus();
		}
		if (compareDates(circulationDate, 'dd-MMM-yyyy', sDate, 'dd-MMM-yyyy') == 1
				
				) {
			errorMessage += "Select Valid Circulation Date.(Circulation Date should be less than Course Duration Start Date.)\n";
			status = false;
			$jq("#circulationDate").focus();
		}
		
		if(compareDates(circulationDate, 'dd-MMM-yyyy', nomStartDate, 'dd-MMM-yyyy') == 1)
		{
			errorMessage += "Nomination Start Date should be greater than Circulation Start Date.\n";
			status = false;
			$jq("#nominationStartDate").focus();
		}
		if(compareDates(nomStartDate, 'dd-MMM-yyyy', nomEndDate, 'dd-MMM-yyyy') == 1)
		{
			errorMessage += "Nomination End Date should be greater than Nomination Start Date.\n";
			status = false;
			$jq("#nominationStartDate").focus();
		}
		if(compareDates(nomEndDate, 'dd-MMM-yyyy', sDate, 'dd-MMM-yyyy') == 1)
		{
			errorMessage += "Nomination End Date should be less than Course Duration Start Date.\n";
			status = false;
			$jq("#nominationStartDate").focus();
		}
			
		
//		if (compareDates(nomStartDate, 'dd-MMM-yyyy', sDate, 'dd-MMM-yyyy') == 1
//				|| compareDates(nomEndDate, 'dd-MMM-yyyy', sDate, 'dd-MMM-yyyy') == 1
//				|| compareDates(nomStartDate, 'dd-MMM-yyyy', nomEndDate,
//						'dd-MMM-yyyy') == 1) {
//			errorMessage += "Select Valid Nomination Start Date & End Date.(Nomination Start Date and End Dates should be less than Course Duration Start Date.)\n";
//			status = false;
//			$jq("#nominationStartDate").focus();
//		}

	}

//	if ((document.getElementById('files') != null && $jq('#files').val() != '') && $jq('#brochure').val() == '') {
//		if (!checkFileExtention($jq('#files').val().split('.')[$jq('#files')
//				.val().split('.').length - 1])) {
//			errorMessage += 'Upload only .docx,.doc,.txt,.pdf\n';
//			status = false;
//		}
//	}
	if (status) {
		if(document.getElementById('SelectRight2').length==0){
			
		}
		else
		{
			for ( var i = 0; i < document.getElementById('SelectRight2').options.length; i++) {
				document.getElementById('SelectRight2').options[i].selected=true;
			}
		}

		if ((document.getElementById('files') != null && $jq('#files').val() != '') && $jq('#brochure').val() == '') {
			$jq('#param').val('manageTrainingCirculation');
			$jq('#type').val('TrainingCirculationMaster');
			$jq.ajaxFileUpload( {
				url : "trainingMaster.htm?"
						+ $jq('#trainingMaster').serialize(),
				secureuri : false,
				fileElementId : 'files',
				success : function(data,status) {
				$jq('#param').val('getBrochure');
				$jq('#type').val('TrainingCirculationMaster');
				$jq.ajax( {
					type : "POST",
					url : "trainingMaster.htm?param=viewBrochure&type=TrainingCirculationMaster",
					data : requestDetails,
					cache : false,
					success : function(html) {
					$jq("#brochureDisabled").html(html);
					alert('Record Saved Successfully');
						
					}

				});
				
				},
			error : function(data, status, e) {
				alert('File Upload failed');
			}
			});
			$jq('#brochureName').val($jq('#files').val());
		}
		else
		{
			$jq('#param').val('manageTrainingCirculation');
			$jq('#type').val('TrainingCirculationMaster');
			$jq.post('trainingMaster.htm', $jq('#trainingMaster').serialize(),
					 function(html) {
				 	$jq("#brochureDisabled").html(html);
					alert('Record Saved Successfully');
					 });
			
		}	
		
//		 $jq('#param').val('manageTrainingCirculation');
//		 $jq('#type').val('TrainingCirculationMaster');
//		 $jq.post('trainingMaster.htm', $jq('#trainingMaster').serialize(),
//		 function(html) {
//		 $jq("#result").html(html);
//		 });

		
	} else {
		alert(errorMessage);

	}
}
function cancelBrochure()
{
	$jq('#brochure').val('');
	$jq('#brochureDisabled').hide();
	$jq('#newBrochure').show();
	
	
}
function setBrochureId(idVal)
{
	$jq('#brochure').val(idVal);
	$jq('#brochureDisabled').show();
	$jq('#newBrochure').hide();
	
}
function checkFileExtention(type) {
	var status = false;
	var allowType = "docx,doc,txt,pdf";
	for ( var i = 0; i < allowType.split(',').length; i++) {
		if (allowType.split(',')[i].toUpperCase() == type.toUpperCase()) {
			status = true;
			return status;
		}
	}
	return status;
}
function SelectedCourseList() {
	// $jq.post('trainingMaster.htm?param=SelectedCourseList&categoryId'+$jq('#categoryId').val()+'&trainingTypeId'+$jq('#trainingTypeId').val()+'&startDate'+$jq('#startDate').val()++'&endDate'+$jq('#endDate').val(),
	// function(html) {
	// $jq("#course").html(html);
	// });
	document.forms[0].type.value = "Course";
	document.forms[0].param.value = "SelectedCourseList";
	document.forms[0].action = "trainingMaster.htm";
	document.forms[0].submit();

}
function getCourseDurationAndVenueList(jsonCourseList) {
	$jq('#durationId').find('option').remove().end();
	$jq("#durationId").append('<option value="">Select</option>');
	var courseId = $jq("#courseId").val();

	for ( var i = 0; jsonCourseList != null && i < jsonCourseList.length; i++) {
		if (jsonCourseList[i].id == courseId) {
			var jsonDurationList = jsonCourseList[i].durationList;
			for ( var j = 0; jsonDurationList != null
					&& j < jsonDurationList.length; j++) {

				$jq("#durationId").append(
						'<option value=' + jsonDurationList[j].id + '>'
								+ jsonDurationList[j].startDate + ' To '
								+ jsonDurationList[j].endDate + '</option>');

			}
		}
	}
	$jq('#venueId').find('option').remove().end();
	$jq("#venueId").append('<option value="">Select</option>');
	for ( var i = 0; jsonCourseList != null && i < jsonCourseList.length; i++) {
		if (jsonCourseList[i].id == courseId) {
			var jsonVenueList = jsonCourseList[i].venueList;
			for ( var j = 0; jsonVenueList != null && j < jsonVenueList.length; j++) {

				$jq("#venueId").append(
						'<option value=' + jsonVenueList[j].cityId + '>'
								+ jsonVenueList[j].name + '</option>');

			}
		}
	}
}
function clearTrainingCirculation() {
	
	if($jq("#id").val() == '')
	{
		$jq("#brochureDisabled").hide();
		$jq("#newBrochure").show();
		$jq("#venueId").val('');
	$jq("#brochure").val('');
	$jq("#organizer").val('');
	}
	else
	{
		
		$jq("#newBrochure").hide();
		
	}
	
}
function setDepartmentList(jsonDepartmentList) {
	$jq('#departments').find('option').remove().end();
	$jq('#department').find('option').remove().end();
	$jq("#departments").append('<option value="">Select</option>');
	for ( var i = 0; jsonDepartmentList != null
			&& i < jsonDepartmentList.length; i++) {
		if (jsonDepartmentList[i].id == courseId) {
			var jsonDepartmentList = jsonDepartmentList[i].venueList;
			for ( var j = 0; jsonDepartmentList != null
					&& j < jsonDepartmentList.length; j++) {

				$jq("#department").append(
						'<option value=' + jsonDepartmentList[j].id + '>'
								+ jsonDepartmentList[j].name + '</option>');

			}
		}
	}
}
function getTrainingDetails(alertId) {
	$jq('#param').val('getTrainingCirculationDetails');
	$jq('#type').val('TrainingCirculationMaster');
	window
			.open(
					"./trainingMaster.htm?param=GetBrochure&returnValue=report&type=course&fileId="
							+ alertId,
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');

}
function getBrochure() {
	window
			.open(
					"./trainingMaster.htm?param=GetBrochure&returnValue=report&type=course",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');

}
function getBrochures(alertId) {
	$jq('#param').val('getTrainingCirculationDetails');
	$jq('#type').val('TrainingCirculationMaster');
	window
			.open(
					"./trainingRequest.htm?param=GetBrochure&returnValue=report&type=course&fileId="
							+ alertId,
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');

}
function nominationSubmit() {
	var errorMessage = "";
	var status = true;
	if ($jq("#nomRequestStatus").val() != null && $jq("#nomRequestStatus").val() =='1') {
		errorMessage += "Nomination Applied.\n";
		status = false;
		
	}
	if(status)
	{
	if ($jq("#courseId").val() == "") {
		errorMessage += "Select Course.\n";
		status = false;
		$jq("#courseId").focus();
	}
//	if ($jq("#lastAttendedCourse").val() == "") {
//		errorMessage += "Enter Last Attended Course.\n";
//		status = false;
//		$jq("#lastAttendedCourse").focus();
//	}
	if ($jq("#currentAssignment").val() == "") {
		errorMessage += "Enter Current Assignment.\n";
		status = false;
		$jq("#currentAssignment").focus();
	}
	if ($jq("#relevance").val() == "") {
		errorMessage += "Enter Relevance.\n";
		status = false;
		$jq("#relevance").focus();
	}
	}
	
	if (status) {
		$jq("#currentAssignment").val().trim();
		$jq("#relevance").val().trim();
		document.forms[0].type.value = "manageTrainingNomination";
		document.forms[0].param.value = "manageTrainingNomination";
		document.forms[0].action = "trainingRequest.htm";
		document.forms[0].submit();
	} else {
		alert(errorMessage);
	}
}
function getNominationReport(id,idVal, type) {
	if (type == 'trainingNominationPrint') {
		window
				.open(
						"./report.htm?param=trainingNominationReport&returnValue=report&nominationId="
								+ id,
						'preview',
						'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	}
	if (type == 'trainingCancelNominationPrint') {
		window
				.open(
						"./report.htm?param=trainingCancelNominationReport&returnValue=report&nominationId="
								+ id+
								"&requestId="
									+ idVal,
						'preview',
						'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	}
}
function saveNominations(type) {
	var mainJSON = {};
	var status = true;
	var errorMessage = '';
	var str = '';
	if (type == 'nomination') {
		if ($jq("#courseId1").val() == "") {
			errorMessage += "Select Course.\n";
			status = false;
			$jq("#courseId1").focus();
		}
		if ($jq("#boardId1").val() == "") {
			errorMessage += "Select Board.\n";
			status = false;
			$jq("#boardId1").focus();
		}
		
		str += '&durationId=' + $jq('#courseId1').val();
		str += '&boardId=' + $jq('#boardId1').val();
		str += '&selectStatus=' + $jq('#selectStatus1').val();
		for ( var i = 0; i <= $jq('#nominations').find("tr").length; i++) {

			if ($jq('#nom' + i).is(':checked') && !$jq('#nom' + i).is(':disabled')) {
				// if($jq('#sanctionNoAdv'+i).val()=='') {
				// errorMessage+='enter sanction no\n';
				// $jq('#sanctionNoAdv'+i).focus();
				// status=false;
				// }if($jq('#billNoAdv'+i).val()=='') {
				// errorMessage+='enter bill no\n';
				// $jq('#billNoAdv'+i).focus();
				// status=false;
				// }if($jq('#accOfficerAdv'+i).val()=='select') {
				// errorMessage+='Please select Acc. Officer.\n';
				// $jq('#accOfficerAdv'+i).focus();
				// status=false;
				// }
			}
		}

		for ( var i = 0, j = 0; i <= $jq('#nominations').find("tr").length; i++) {
			if ($jq('#nom' + i).is(':checked') && !$jq('#nom' + i).is(':disabled')) {
				var subJSON = {};
				subJSON['id'] = $jq('#nom' + i).val();
				mainJSON[j] = subJSON;
				j++;
			}
		}
	}
	if (type == 'boardSelection') {
		if ($jq("#courseId2").val() == "") {
			errorMessage += "Select Course.\n";
			status = false;
			$jq("#courseId2").focus();
		}
		if ($jq("#boardId2").val() == "") {
			errorMessage += "Select Board.\n";
			status = false;
			$jq("#boardId2").focus();
		}
		if ($jq("#ionId2").val() == "" && $jq('#selectStatus2').val() =="27") {
			errorMessage += "Select Letter Number.\n";
			status = false;
			$jq("#ionId2").focus();
		}
		else if($jq('#selectStatus2').val() !="27")
		{
			$jq('#ionId2').val('');
		}
		str += '&courseId=' + $jq('#courseId2').val();
		str += '&boardId=' + $jq('#boardId2').val();
		str += '&ionId=' + $jq('#ionId2').val();
		str += '&selectStatus=' + $jq('#selectStatus2').val();
		for ( var i = 0; i <= $jq('#boardSelections').find("tr").length; i++) {
			if ($jq('#boardSl' + i).is(':checked') && !$jq('#boardSl' + i).is(':disabled')) {
				
			}
		}

		for ( var i = 0, j = 0; i <= $jq('#boardSelections').find("tr").length; i++) {
			if ($jq('#boardSl' + i).is(':checked') && !$jq('#boardSl' + i).is(':disabled')) {
				var subJSON = {};
				subJSON['id'] = $jq('#boardSl' + i).val();
				mainJSON[j] = subJSON;
				j++;
			}
		}
	}
	if (type == 'ADSelection') {
		if ($jq("#courseId3").val() == "") {
			errorMessage += "Select Course.\n";
			status = false;
			$jq("#courseId3").focus();
		}
		if (document.getElementById('ionId3') != null && $jq("#ionId3").val() == "" && $jq('#selectStatus2').val() =="33") {
			errorMessage += "Select Letter Number.\n";
			status = false;
			$jq("#ionId3").focus();
		}
		else if($jq('#selectStatus3').val() !="33")
		{
			$jq('#ionId3').val('');
		}
		
		str += '&courseId=' + $jq('#courseId3').val();
		str += '&selectStatus=' + $jq('#selectStatus3').val();
		if(document.getElementById('ionId3') != null)
		str += '&ionId=' + $jq('#ionId3').val();
		else
			str += '&ionId=';
		for ( var i = 0; i <= $jq('#ADSelections').find("tr").length; i++) {
			if ($jq('#ADSl' + i).is(':checked') && !$jq('#ADSl' + i).is(':disabled') ) {
				
			}
		}

		for ( var i = 0, j = 0; i <= $jq('#ADSelections').find("tr").length; i++) {
			if ($jq('#ADSl' + i).is(':checked') && !$jq('#ADSl' + i).is(':disabled')) {
				var subJSON = {};
				subJSON['id'] = $jq('#ADSl' + i).val();
				mainJSON[j] = subJSON;
				j++;
			}
		}
	}
	if (type == 'FinalAlert') {
		if ($jq("#courseId4").val() == "") {
			errorMessage += "Select Course.\n";
			status = false;
			$jq("#courseId4").focus();
		}
		if ($jq("#ionId4").val() == "" && $jq("#selectStatus4").val()=="50") {
			errorMessage += "Select Letter Number.\n";
			status = false;
			$jq("#ionId4").focus();
		}
		else if($jq("#selectStatus4").val()!="50")
		{
			$jq("#ionId4").val('');
		}
		str += '&courseId=' + $jq('#courseId4').val();
		str += '&selectStatus=' + $jq('#selectStatus4').val();
		str += '&ionId=' + $jq('#ionId4').val();

		for ( var i = 0; i <= $jq('#FinalAlerts').find("tr").length; i++) {
			if ($jq('#FnlAlert' + i).is(':checked') && !$jq('#FnlAlert' + i).is(':disabled')) {
				
			}
		}

		for ( var i = 0, j = 0; i <= $jq('#FinalAlerts').find("tr").length; i++) {
			if ($jq('#FnlAlert' + i).is(':checked') && !$jq('#FnlAlert' + i).is(':disabled')) {
				var subJSON = {};
				subJSON['id'] = $jq('#FnlAlert' + i).val();
				subJSON['sfid'] = $jq('#sfid' + i).val();
				mainJSON[j] = subJSON;
				j++;
			}
		}
	}
	if (JSON.stringify(mainJSON).length == 2 && status) {
		errorMessage += 'please check at least one check box';
		status = false;
	}
	if (status && type != 'FinalAlert') {
		$jq.post('trainingRequest.htm?param=manageTrainingNominations&type='
				+ type + '&jsonValues=' + JSON.stringify(mainJSON) + str,
				function(html) {
					if (type == 'nomination') {
						$jq("#nomination").html(html);   
						$jq("#courseId2").val('');    //for updated MDB List 
						$jq("#boardId2").val('');
						getMdbSelectionList();
					}
					if (type == 'boardSelection') {
						$jq("#boardSelection").html(html);
						$jq("#courseId1").val('');  //for updated nomination List 
						$jq("#boardId1").val('');
						gethrdgNominationList1();
						
					}
					if (type == 'ADSelection') {
						$jq("#ADSelection").html(html);  //for updated MDB List 
						$jq("#courseId2").val('');
						$jq("#boardId2").val('');
						getMdbSelectionList1();            //for updated Final List 
						
					}

				});

	} else if (status) {
		$jq.post(
				'trainingRequest.htm?param=manageTrainingFinalNominations&type='
						+ type + '&jsonValues=' + JSON.stringify(mainJSON)
						+ str, function(html) {
					if (type == 'FinalAlert') {
						$jq("#FinalAlert").html(html);
						$jq("#courseId3").val('');   //for updated AD List 
						getADSelectionList()
					}

				});
	} else {
		alert(errorMessage);
	}

}
function gethrdgNominationList() {
	var requestDetails = {
		"durationId" : $jq("#courseId1").val(),
		"param" : 'TrainingNominationRequestList'
	};
	$jq.ajax( {
		type : "POST",
		url : "trainingRequest.htm?"+dispUrl,
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq("#nomination").html(html);

		}

	});
}
function gethrdgNominationList1() {
	var requestDetails = {
		"durationId" : $jq("#courseId1").val(),
		"param" : 'TrainingNominationRequestList'
	};
	$jq.ajax( {
		type : "POST",
		url : "trainingRequest.htm?"+dispUrl,
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq("#nomination").html(html);
			$jq("#courseId3").val('');   //for updated AD List 
			getADSelectionList()
		}

	});
}
function getMdbSelectionList() {
	var requestDetails = {
		"courseId" : $jq("#courseId2").val(),
		"boardId" : $jq("#boardId2").val(),
		"param" : 'TrainingNominationBoardSelectionList'
	};
	$jq.ajax( {
		type : "POST",
		url : "trainingRequest.htm?"+dispUrl,
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq("#boardSelection").html(html);

		}

	});
}
function getMdbSelectionList1() {
	var requestDetails = {
		"courseId" : $jq("#courseId2").val(),
		"boardId" : $jq("#boardId2").val(),
		"param" : 'TrainingNominationBoardSelectionList'
	};
	$jq.ajax( {
		type : "POST",
		url : "trainingRequest.htm?"+dispUrl,
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq("#boardSelection").html(html);
			$jq("#courseId4").val('');          
			getFinalSelectionList();
		}

	});
}
function getADSelectionList() {
	var requestDetails = {
		"courseId" : $jq("#courseId3").val(),
		"param" : 'TrainingNominationADSelectionList'
	};
	$jq.ajax( {
		type : "POST",
		url : "trainingRequest.htm?"+dispUrl,
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq("#ADSelection").html(html);

		}

	});
}
function getFinalSelectionList() {
	var requestDetails = {
		"courseId" : $jq("#courseId4").val(),
		"param" : 'TrainingNominationFinalSelectionList'
	};
	$jq.ajax( {
		type : "POST",
		url : "trainingRequest.htm?"+dispUrl,
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq("#FinalAlert").html(html);

		}

	});
}
function getCFANominationList() {
	var requestDetails = {
		"courseId" : $jq("#courseId").val(),
		"param" : 'TrainingNominationsCFASelectionList'
	};
	$jq.ajax( {
		type : "POST",
		url : "trainingRequest.htm?"+dispUrl,
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq("#CFASelection").html(html);

		}

	});
}
function saveCFANominations(type) {
	var mainJSON = {};
	var status = true;
	var errorMessage = '';
	var str = '';

	if ($jq("#courseId").val() == "") {
		errorMessage += "Select Course.\n";
		status = false;
		$jq("#courseId").focus();
	}
	

	for ( var i = 0; i <= $jq('#CFASelections').find("tr").length; i++) {

		if ($jq('#CFASl' + i).is(':checked') && !$jq('#CFASl' + i).is(':disabled')) {
			// if($jq('#sanctionNoAdv'+i).val()=='') {
			// errorMessage+='enter sanction no\n';
			// $jq('#sanctionNoAdv'+i).focus();
			// status=false;
			// }if($jq('#billNoAdv'+i).val()=='') {
			// errorMessage+='enter bill no\n';
			// $jq('#billNoAdv'+i).focus();
			// status=false;
			// }if($jq('#accOfficerAdv'+i).val()=='select') {
			// errorMessage+='Please select Acc. Officer.\n';
			// $jq('#accOfficerAdv'+i).focus();
			// status=false;
			// }
		}
	}

	for ( var i = 0, j = 0; i <= $jq('#CFASelections').find("tr").length; i++) {
		if ($jq('#CFASl' + i).is(':checked') && !$jq('#CFASl' + i).is(':disabled')) {
			var subJSON = {};
			subJSON['id'] = $jq('#CFASl' + i).val();
			mainJSON[j] = subJSON;
			j++;
		}
	}

	if (JSON.stringify(mainJSON).length == 2 && status) {
		status = false;
		errorMessage += 'please check at least one check box';
	}
	if (status) {

		str += '&courseId=' + $jq('#courseId').val();
		str += '&selectStatus=' + $jq('#selectStatus').val();
		$jq.post(
				'trainingRequest.htm?param=manageTrainingNominationsCFASelectionList&type='
						+ type + '&jsonValues=' + JSON.stringify(mainJSON)
						+ str, function(html) {

					$jq("#CFASelection").html(html);

				});
	} else {
		alert(errorMessage);
	}

}
function getText()
{
$jq('#courseId').val();
}

function getTrainingRegions()
{
	var requestDetails = {
			"trainingTypeId" : $jq("#trainingTypeId").val(),
			"type" : $jq("#type").val(),
			"param" : 'TrainingRegionDataList'
		};
		$jq.ajax( {
			type : "POST",
			url : "trainingMaster.htm?"+dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#result").html(html);
			}
		});
}
function trainingRegionSubmit(type)
{
	var errorMessage = "";
	var status = true;
	
	if ($jq("#name").val() == "") {
		errorMessage += "Enter Region Name.\n";
		status = false;
		$jq("#name").focus();
	}
	
	if (status) {
		var requestDetails = {
				"trainingTypeId" : $jq("#trainingTypeId").val(),
				"type" : $jq("#type").val(),
				"param" : 'manageTrainingRegion',
				"id" : $jq("#id").val(),
				"name" : $jq("#name").val().trim(),
				"description" : $jq("#description").val(),
				
				
			};
			$jq.ajax( {
				type : "POST",
				url : "trainingMaster.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq("#result").html(html);
					$jq('#name').val('');
					$jq('#description').val('');
					$jq('#counter').val(500);
					$jq('#id').val('');
				}
			
			
			});
		
	} else {
		alert(errorMessage);
	}
	
}
function clearTrainingRegion()
{
	
	$jq('#name').val('');
	$jq('#description').val('');
	$jq('#counter').val(500);
	$jq('#id').val('');
	
	
}
function editTrainingRegion(jsonTrainingRegionList,id)
{
	
	for ( var i = 0; jsonTrainingRegionList != null && i < jsonTrainingRegionList.length; i++) {
		if (jsonTrainingRegionList[i].id == id) {
			$jq('#id').val(jsonTrainingRegionList[i].id);
			$jq('#name').val(jsonTrainingRegionList[i].name);
			$jq('#trainingTypeId').val(jsonTrainingRegionList[i].trainingTypeId);

			if (jsonTrainingRegionList[i].description != null
					&& jsonTrainingRegionList[i].description != "") {
				$jq('#description').val(jsonTrainingRegionList[i].description);
				$jq('#counter').val(500);
				$jq('#counter').val(
						$jq('#counter').val() - jsonTrainingRegionList[i].description.length);
			}
			
		}
	}
	
}
function deleteTrainingRegion(id,jsonTrainingRegionList)
{
	
}

function getTrainingRegionList(jsonTrainingRegionList)
{
	$jq('#trainingRegionId').find('option').remove().end();
	
	$jq("#trainingRegionId").append('<option value="">Select</option>');
	var trainingTypeId = $jq('#trainingTypeId').val();
	for ( var i = 0; jsonTrainingRegionList != null
			&& i < jsonTrainingRegionList.length; i++) {
		if (jsonTrainingRegionList[i].trainingTypeId == trainingTypeId) {
			
				$jq("#trainingRegionId").append(
						'<option value=' + jsonTrainingRegionList[i].id + '>'
								+ jsonTrainingRegionList[i].name + '</option>');

			}
		}
	
	
	
}
function trainingRegionReport()
{
	window
	.open(
			"./report.htm?param=trainingRegionReport&returnValue=report",
			'preview',
			'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	
}
function getTrainingRegionBasedOnTrainingType(id,jsonMasterDataList)
{
	for ( var i = 0; jsonMasterDataList != null && i < jsonMasterDataList.length; i++) {
		if (jsonMasterDataList[i].id == id)
			{
	$jq('#id').val('');
	$jq('#trainingTypeId').val(id);
	$jq('#trainingType').val(jsonMasterDataList[i].name);
	$jq('#param').val('TrainingRegionMaster');
	$jq('#type').val('TrainingRegionMaster');
	document.forms[0].action = "trainingMaster.htm";
	document.forms[0].submit();
		}
		}
		
}
function goBack()
{
	var type=$jq('#type').val();
	if(type == 'TrainingRegionMaster')
	{
		$jq('#type').val('trainingType');
		$jq('#param').val('trainingType');
		$jq('#id').val('');
		
	}
	document.forms[0].action = "trainingMaster.htm";
	document.forms[0].submit();
}
function getTrainingInistituteList()
{
	var requestDetails = {
			"trainingTypeId" : $jq("#trainingTypeId").val(),
			"trainingRegionId" : $jq("#trainingRegionId").val(),
			"type" : $jq("#type").val(),
			"param" : 'TrainingInistituteDataList',
			"name" : $jq("#name").val(),
			"id" : $jq("#id").val(),
			
			
		};
		$jq.ajax( {
			type : "POST",
			url : "trainingMaster.htm?"+dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#result").html(html);
				$jq('#id').val('');
				$jq('#typeValue').val('');
				$jq('#shortName').val('');
			}
		
		
		});
}
function trainingMasterSubmit(type)
{
	document.forms[0].param.value = "manage";
	document.forms[0].type.value = type;
	var errorMessage = "";
	var status = true;
	
		if ($jq("#trainingTypeId").val() == "") {
			errorMessage += "Select Training Type.\n";
			status = false;
			$jq("#trainingTypeId").focus();
		}
		
		if ($jq("#trainingRegionId").val() == "") {
			errorMessage += "Select Region.\n";
			status = false;
			$jq("#trainingRegionId").focus();
		}
		if ($jq("#typeValue").val() == "") {
			errorMessage += "Enter Institute Name.\n";
			status = false;
			$jq("#typeValue").focus();
		}
	
	if (status) {

		if (document.forms[0].typeValue != null)
			$jq('#name').val($jq('#typeValue').val().trim());
		
		var requestDetails = {
				"trainingTypeId" : $jq("#trainingTypeId").val(),
				"trainingRegionId" : $jq("#trainingRegionId").val(),
				"type" : $jq("#type").val(),
				"param" : 'manageTrainingInistitute',
				"name" : $jq("#name").val().trim(),
				"id" : $jq("#id").val(),
				"shortName" : $jq("#shortName").val().trim(),
				
				
			};
			$jq.ajax( {
				type : "POST",
				url : "trainingMaster.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq("#result").html(html);
					$jq('#id').val('');
					$jq('#typeValue').val('');
					$jq('#shortName').val('');
				}
			
			
			});
		
	} else {
		alert(errorMessage);
	}
}


function goBackToTrainingInistitute(type)
{
	
		$jq('#type').val('trainingInistitute');
		$jq('#param').val('trainingInistitute');
		$jq('#back').val('trainingInistitute');
		$jq('#id').val('');
		
	document.forms[0].action = "trainingMaster.htm";
	document.forms[0].submit();
}
function getTrainingVenueBasedOnTrainingInistitute(id,jsonMasterDataList)
{
	for ( var i = 0; jsonMasterDataList != null && i < jsonMasterDataList.length; i++) {
		if (jsonMasterDataList[i].id == id) {
	$jq('#type').val('trainingVenue');
	$jq('#param').val('trainingVenue');
	$jq('#id').val('');
	$jq('#trainingInistituteId').val(id);
	$jq('#trainingTypeId').val(jsonMasterDataList[i].trainingTypeId);
	$jq('#trainingRegionId').val(jsonMasterDataList[i].trainingRegionId);
	$jq('#trainingInistitute').val(jsonMasterDataList[i].name);
	$jq('#trainingType').val(jsonMasterDataList[i].trainingType);
	$jq('#trainingRegion').val(jsonMasterDataList[i].trainingRegion);
	document.forms[0].action = "trainingMaster.htm";
	document.forms[0].submit();
		}
	}
}
function trainingVenueMasterSubmit(type,jsonMasterDataList)
{
	var errorMessage = "";
	var status = true;
	if ($jq('#dd').is(':checked') && $jq('#check').is(':checked'))
		$jq('#ddFlag').val('3');
	else if ($jq('#dd').is(':checked') && !$jq('#check').is(':checked'))
		$jq('#ddFlag').val($jq('#dd').val());
	else if (!$jq('#dd').is(':checked') && $jq('#check').is(':checked'))
		$jq('#ddFlag').val($jq('#check').val());
	else if (!$jq('#dd').is(':checked') && !$jq('#check').is(':checked'))
		$jq('#ddFlag').val('0');

	if ($jq('#headOfficeFlag').is(':checked'))
		$jq('#headOfficeFlag').val('1');
	else
		$jq('#headOfficeFlag').val('0');
	
	
	
	if ($jq("#cityId").val() == "") {
		errorMessage += "Select City.\n";
		status = false;
		$jq("#cityId").focus();
	}
	if ($jq("#address").val() == "") {
		errorMessage += "Enter Address\n";
		status = false;
		$jq("#address").focus();
	}
	if ($jq("#phone").val() == "") {
		errorMessage += "Enter Phone\n";
		status = false;
		$jq("#phone").focus();
	}
	if ($jq("#headOfficeFlag").val() == '1' && status) {
		var flag = true;
		for ( var i = 0; jsonMasterDataList != null
				&& jsonMasterDataList.length > 0
				&& i < jsonMasterDataList.length; i++) {
			if (flag
					&& jsonMasterDataList[i].trainingInistituteId == $jq(
							"#trainingInistituteId").val()
					&& jsonMasterDataList[i].headOfficeFlag == 1) {
				if($jq("#id").val()!='' && $jq("#id").val()==jsonMasterDataList[i].id)
				{
					
				}
				else
				{
				flag = false;
				}
			}
		}
		if (!flag) {
			errorMessage += "Already Entered Head Office Details\n";
			status = false;
			$jq("#headOfficeFlag").focus();
		}
	}
	if(status)
	{
		var requestDetails = {
				"trainingTypeId" : $jq("#trainingTypeId").val(),
				"trainingInistituteId" : $jq("#trainingInistituteId").val(),
				"trainingRegionId" : $jq("#trainingRegionId").val(),
				"type" : $jq("#type").val(),
				"name" : $jq("#name").val().trim(),
				"param" : 'manageTrainingVenue',
				"headOfficeFlag" : $jq("#headOfficeFlag").val(),
				"ddFlag" : $jq("#ddFlag").val(),
				"cityId" : $jq("#cityId").val(),
				"address" : $jq("#address").val().trim(),
				"phone" : $jq("#phone").val().trim(),
				"fax" : $jq("#fax").val().trim(),
				"email" : $jq("#email").val().trim(),
				"ddAddress" : $jq("#ddAddress").val().trim(),
				"payableAt" : $jq("#payableAt").val(),
				"id" : $jq("#id").val(),
				
			};
			$jq.ajax( {
				type : "POST",
				url : "trainingMaster.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq("#result").html(html);
					$jq('#id').val('');
					clearVenueMaster();
				}
			
			
			});
		
	}
	else {
		alert(errorMessage);
	}
}
function clearVenueMaster()
{
	var editMaterial = false;

	if ($jq('#id').length > 0)
		$jq('#id').val('');
	

	if ($jq('#cityId').length > 0)
		$jq('#cityId').val('');
	if ($jq('#address').length > 0)
		$jq('#address').val('');
	if ($jq('#phone').length > 0)
		$jq('#phone').val('');
	if ($jq('#fax').length > 0)
		$jq('#fax').val('');
	if ($jq('#email').length > 0)
		$jq('#email').val('');
	if ($jq('#ddAddress').length > 0)
		$jq('#ddAddress').val('');
	if ($jq('#ddCounter').length > 0)
		$jq('#ddCounter').val('200');
	if ($jq('#shortName').length > 0)
		$jq('#shortName').val('');

	if ($jq('#payableAt').length > 0)
		$jq('#payableAt').val('');

	if (document.forms[0].dd != null)
		$jq('#dd').attr('checked', false);
	if (document.forms[0].check != null)
		$jq('#check').attr('checked', false);
	if (document.forms[0].headOfficeFlag != null)
		$jq('#headOfficeFlag').attr('checked', false);
}

function getHRDGBoardDataList()
{
	var requestDetails = {
			"boardTypeId" : $jq("#boardTypeId").val(),
			"yearId" : $jq("#yearId").val(),
			"type" : $jq("#type").val(),
			"param" : 'HRDGBoardDataList',
			
			
		};
		$jq.ajax( {
			type : "POST",
			url : "trainingMaster.htm?"+dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#result").html(html);
				$jq('#id').val('');
				
			}
		
		
		});
}
function getHrdgBoardMemberList(id,boardTypeId,yearId,boardType,year,board)
{

	$jq('#type').val('HRDGBoardMember');
	$jq('#param').val('HRDGBoardMember');
	$jq('#back').val('');
	$jq('#id').val('');
	$jq('#boardId').val(id);
	$jq('#boardTypeId').val(boardTypeId);
	$jq('#yearId').val(yearId);
	$jq('#boardType').val(boardType);
	$jq('#year').val(year);
	$jq('#board').val(board);
	
	
document.forms[0].action = "trainingMaster.htm";
document.forms[0].submit();
	

}
function goBackToHrdgBoard()
{

	$jq('#type').val('HRDGBoard');
	$jq('#param').val('HRDGBoard');
	$jq('#back').val('HRDGBoard');
	$jq('#id').val('');
	
document.forms[0].action = "trainingMaster.htm";
document.forms[0].submit();
}
function manageTrainingType(type) {
	document.forms[0].param.value = "manageTrainingType";
	document.forms[0].type.value = type;
	var errorMessage = "";
	var status = true;
	
		if ($jq("#typeValue").val() == "") {
			errorMessage += "Enter Training Type.\n";
			status = false;
			$jq("#typeValue").focus();
		}
		if ($jq("#orderNo").val() == "") {
			errorMessage += "Enter Order Number.\n";
			status = false;
			$jq("#orderNo").focus();
		}
	
	if (status) {

		if (document.forms[0].typeValue != null)
			$jq('#name').val($jq('#typeValue').val());
		var requestDetails = {
				"name" : $jq("#name").val().trim(),
				"id" : $jq("#id").val(),
				"description" : $jq("#description").val().trim(),
				"type" : $jq("#type").val(),
				"orderNo" : $jq("#orderNo").val().trim(),
				"param" : 'manageTrainingType',
				
				
			};
			$jq.ajax( {
				type : "POST",
				url : "trainingMaster.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq("#result").html(html);
					$jq('#id').val('');
					$jq('#typeValue').val('');
					$jq('#description').val('');
					$jq('#counter').val('500');
					$jq('#orderNo').val('');
					
				}
			
			
			});
	} else {
		alert(errorMessage);
	}
}
function getCoursesList() {
	var requestDetails = {
			"trainingTypeId" : $jq("#trainingTypeId").val(),
			"trainingRegionId" : $jq("#trainingRegionId").val(),
			"trainingInistituteId" : $jq("#trainingInistituteId").val(),
		"courseYear" : $jq("#courseYear").val(),
		"type" : $jq("#type").val(),
		"param" : 'CourseMasterDataList'
	};
	$jq.ajax( {
		type : "POST",
		url : "trainingMaster.htm?"+dispUrl,
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq("#displayTable").html(html);
		}
	});
}
function getCourseDurationBasedOnCourse(id,jsonTrainingMstDataList)
{
	for ( var i = 0; jsonTrainingMstDataList != null && i < jsonTrainingMstDataList.length; i++) {
		if (jsonTrainingMstDataList[i].id == id) {
			$jq('#id').val('');
			$jq('#name').val(jsonTrainingMstDataList[i].name);
			$jq('#trainingTypeId').val(jsonTrainingMstDataList[i].trainingTypeId);
			$jq('#trainingInistituteId').val(
					jsonTrainingMstDataList[i].trainingInistituteId);
			$jq('#trainingRegionId').val(
					jsonTrainingMstDataList[i].trainingRegionId);
			$jq('#courseYear').val(
					jsonTrainingMstDataList[i].courseYear);
			$jq('#courseId').val(
					jsonTrainingMstDataList[i].id);
			$jq('#course').val(
					jsonTrainingMstDataList[i].name);
			$jq('#trainingType').val(jsonTrainingMstDataList[i].trainingType);
			$jq('#trainingInistitute').val(
					jsonTrainingMstDataList[i].trainingInistitute);
			$jq('#trainingRegion').val(
					jsonTrainingMstDataList[i].trainingRegion);
			$jq('#year').val(
					jsonTrainingMstDataList[i].year);
			$jq('#type').val('courseDuration');
			$jq('#param').val('courseDuration');
			document.forms[0].action = "trainingMaster.htm";
			document.forms[0].submit();

		}
	}
}
function getQualificationsBasedOnCourse(id,jsonTrainingMstDataList)
{

	for ( var i = 0; jsonTrainingMstDataList != null && i < jsonTrainingMstDataList.length; i++) {
		if (jsonTrainingMstDataList[i].id == id) {
			$jq('#id').val('');
			$jq('#name').val(jsonTrainingMstDataList[i].name);
			$jq('#trainingTypeId').val(jsonTrainingMstDataList[i].trainingTypeId);
			$jq('#trainingInistituteId').val(
					jsonTrainingMstDataList[i].trainingInistituteId);
			$jq('#trainingRegionId').val(
					jsonTrainingMstDataList[i].trainingRegionId);
			$jq('#courseYear').val(
					jsonTrainingMstDataList[i].courseYear);
			$jq('#courseId').val(
					jsonTrainingMstDataList[i].id);
			$jq('#course').val(
					jsonTrainingMstDataList[i].name);
			$jq('#trainingType').val(jsonTrainingMstDataList[i].trainingType);
			$jq('#trainingInistitute').val(
					jsonTrainingMstDataList[i].trainingInistitute);
			$jq('#trainingRegion').val(
					jsonTrainingMstDataList[i].trainingRegion);
			$jq('#year').val(
					jsonTrainingMstDataList[i].year);
			$jq('#type').val('courseQualification');
			$jq('#param').val('courseQualification');
			document.forms[0].action = "trainingMaster.htm";
			document.forms[0].submit();

		}
	}
}

function getDisciplinesBasedOnCourse(id,jsonTrainingMstDataList)
{

	for ( var i = 0; jsonTrainingMstDataList != null && i < jsonTrainingMstDataList.length; i++) {
		if (jsonTrainingMstDataList[i].id == id) {
			$jq('#id').val('');
			$jq('#name').val(jsonTrainingMstDataList[i].name);
			$jq('#trainingTypeId').val(jsonTrainingMstDataList[i].trainingTypeId);
			$jq('#trainingInistituteId').val(
					jsonTrainingMstDataList[i].trainingInistituteId);
			$jq('#trainingRegionId').val(
					jsonTrainingMstDataList[i].trainingRegionId);
			$jq('#courseYear').val(
					jsonTrainingMstDataList[i].courseYear);
			$jq('#courseId').val(
					jsonTrainingMstDataList[i].id);
			$jq('#course').val(
					jsonTrainingMstDataList[i].name);
			$jq('#trainingType').val(jsonTrainingMstDataList[i].trainingType);
			$jq('#trainingInistitute').val(
					jsonTrainingMstDataList[i].trainingInistitute);
			$jq('#trainingRegion').val(
					jsonTrainingMstDataList[i].trainingRegion);
			$jq('#year').val(
					jsonTrainingMstDataList[i].year);
			$jq('#type').val('courseDiscipline');
			$jq('#param').val('courseDiscipline');
			document.forms[0].action = "trainingMaster.htm";
			document.forms[0].submit();

		}
	}
}
function goBackToCourse()
{
	$jq('#id').val('');
	$jq('#type').val('course');
	$jq('#param').val('course');
	$jq('#back').val('course');
	document.forms[0].action = "trainingMaster.htm";
	document.forms[0].submit();
}
function clearCourseDuration()
{
	$jq('#id').val('');
	$jq('#startDate').val('');
	$jq('#endDate').val('');
}
function clearDiscipline()
{
	$jq('#id').val('');
	for(var j=0;j<$jq('#SelectRight2')[0].length;j++) {
		var subJSON = {};
		var a = $jq('#SelectRight2')[0][j].value;
		
	}
}
function courseDisciplineSubmit(type)
{
	var errorMessage = "";
	var status = true;
	var del = true;
	var mainJSON = {};
		if(document.getElementById('SelectRight2').length==0){
			if($jq("#count").val()==0)
			{
			
				errorMessage += "Enter Disciplines\n";
				status = false;
			}
			else
			{
				
				 del = confirm("Do you want to delete this item?");
			}
			
		}
		else 
		{
			for ( var i = 0; i < document.getElementById('SelectRight2').options.length; i++) {
				document.getElementById('SelectRight2').options[i].selected=true;
			}
		}
	
	
	
	if (status && del) {
		$jq('#type').val(type);
		$jq('#param').val('manageCourseDiscipline');
		for(var j=0;j<$jq('#SelectRight2')[0].length;j++) {
			var subJSON = {};
			subJSON['id'] = $jq('#SelectRight2')[0][j].value;
			mainJSON[j] = subJSON;
			
		}
		var requestDetails = {
				"id" : $jq("#id").val(),
				"courseId" : $jq("#courseId").val(),
				"trainingTypeId" : $jq("#trainingTypeId").val(),
				"trainingRegionId" : $jq("#trainingRegionId").val(),
				"trainingInistituteId" : $jq("#trainingInistituteId").val(),
				"courseYear" : $jq("#courseYear").val(),
				"type" : $jq("#type").val(),
				"discipline" : ""+JSON.stringify(mainJSON),
				"param" : 'manageCourseDiscipline'
			};
			$jq.ajax( {
				type : "POST",
				url : "trainingMaster.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq("#result").html(html);
					$jq("#id").val('');
					countSelectedList();
		
				}

			});
	} else {
		if(errorMessage.length > 5)alert(errorMessage);
	}
}
function courseQualificationSubmit(type)
{
	var errorMessage = "";
	var status = true;
	var mainJSON = {};
	var del = true;
	if(document.getElementById('SelectRight2').length==0){
		if($jq("#count").val()==0)
		{
		
			errorMessage += "Enter Qualification\n";
			status = false;
		}
		else
		{
			
			del = confirm("Do you want to delete this item?");
		}
	}
	else
	{
		for ( var i = 0; i < document.getElementById('SelectRight2').options.length; i++) {
			document.getElementById('SelectRight2').options[i].selected=true;
		}
	}
	if ($jq("#SelectRight2").val() == "") {
		errorMessage += "Select Disciplines.\n";
		status = false;
		
	}
	
	if (status && del) {
		
		for(var j=0;j<$jq('#SelectRight2')[0].length;j++) {
			var subJSON = {};
			subJSON['id'] = $jq('#SelectRight2')[0][j].value;
			mainJSON[j] = subJSON;
			
		}
		var requestDetails = {
				"id" : $jq("#id").val(),
				"courseId" : $jq("#courseId").val(),
				"trainingTypeId" : $jq("#trainingTypeId").val(),
				"trainingRegionId" : $jq("#trainingRegionId").val(),
				"trainingInistituteId" : $jq("#trainingInistituteId").val(),
				"courseYear" : $jq("#courseYear").val(),
				"type" : $jq("#type").val(),
				"qualification" : ""+JSON.stringify(mainJSON),
				"param" : 'manageCourseQualification'
			};
			$jq.ajax( {
				type : "POST",
				url : "trainingMaster.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq("#result").html(html);
					$jq("#id").val('');
					countSelectedList();
				}

			});
	} else {
		if(errorMessage.length > 5)alert(errorMessage);
	}
}
function clearQualification()
{
	
}
function getDesignationsBasedOnCourse(id,jsonTrainingMstDataList)
{

	for ( var i = 0; jsonTrainingMstDataList != null && i < jsonTrainingMstDataList.length; i++) {
		if (jsonTrainingMstDataList[i].id == id) {
			$jq('#id').val('');
			
			$jq('#trainingTypeId').val(jsonTrainingMstDataList[i].trainingTypeId);
			$jq('#trainingInistituteId').val(
					jsonTrainingMstDataList[i].trainingInistituteId);
			$jq('#trainingRegionId').val(
					jsonTrainingMstDataList[i].trainingRegionId);
			$jq('#courseYear').val(
					jsonTrainingMstDataList[i].courseYear);
			$jq('#courseId').val(
					jsonTrainingMstDataList[i].id);
			$jq('#course').val(
					jsonTrainingMstDataList[i].name);
			$jq('#trainingType').val(jsonTrainingMstDataList[i].trainingType);
			$jq('#trainingInistitute').val(
					jsonTrainingMstDataList[i].trainingInistitute);
			$jq('#trainingRegion').val(
					jsonTrainingMstDataList[i].trainingRegion);
			$jq('#year').val(
					jsonTrainingMstDataList[i].year);
			$jq('#type').val('courseDesignation');
			$jq('#param').val('courseDesignation');
			document.forms[0].action = "trainingMaster.htm";
			document.forms[0].submit();

		}
	}
}
function getDesignationsBasedOnCategory()
{
	document.forms[0].param.value = "getDesignationList";
	document.forms[0].type.value = type;
	var errorMessage = "";
	var status = true;
	
		if ($jq("#categoryId").val() == "") {
			$jq('#SelectRight2 option').remove();
			 $jq('#SelectLeft2 option').remove();
			status = false;
			$jq("#categoryId").focus();
		}
	
	if (status) {
		$jq.post('trainingMaster.htm', $jq('#trainingMaster').serialize(),function(html) {
			$jq("#result").html(html);
			$jq('#id').val('');
			
		});
		
	} else {
		
	}
}
function setDesignationsForCourse(jsonDesignationList,jsonSelectedDesignationList)
{
	var create1 = '';
	var create2 = '';
	 $jq('#SelectRight2 option').remove();
	 $jq('#SelectLeft2 option').remove();
	 if($jq('#categoryId').val() !='')
	 {
    for(var i = 0; i < jsonDesignationList.length;i++)
    {
    	var flag=true;
    	for(var j=0;jsonSelectedDesignationList != null && j<jsonSelectedDesignationList.length;j++) 
    		{
    			if(jsonDesignationList[i].id==jsonSelectedDesignationList[j].id){
    				create2 += '<option value="'+jsonDesignationList[i].id+'">'+jsonDesignationList[i].name+'</option>';
    				flag=false;
    				break;
    			}
    		}
    	if(flag){
    		create1 += '<option value="'+jsonDesignationList[i].id+'">'+jsonDesignationList[i].name+'</option>';
    	}
    }
    $jq('#SelectRight2').append(create2);
    $jq('#SelectLeft2').append(create1);
	 }
    countSelectedList();
}
function courseDesignationSubmit(type)
{
	var errorMessage = "";
	var status = true;
	var mainJSON = {};
	var del = true;
	if ($jq("#categoryId").val() == "") {
		errorMessage += "Select Category.\n";
		status = false;
		$jq("#categoryId").focus();
	}
	if(document.getElementById('SelectRight2').length==0){
		if($jq("#count").val()==0)
		{
		
			errorMessage += "Enter Designations\n";
			status = false;
		}
		else
		{
			
			del = confirm("Do you want to delete this item?");
		}
	}
	else
	{
		for ( var i = 0; i < document.getElementById('SelectRight2').options.length; i++) {
			document.getElementById('SelectRight2').options[i].selected=true;
		}
	}
	
	
	
	if (status && del) {
		$jq('#type').val(type);
		$jq('#param').val('manageCourseDesignation');
		for(var j=0;j<$jq('#SelectRight2')[0].length;j++) {
			var subJSON = {};
			subJSON['id'] = $jq('#SelectRight2')[0][j].value;
			mainJSON[j] = subJSON;
			
		}
		
		var requestDetails = {
				"id" : $jq("#id").val(),
				"courseId" : $jq("#courseId").val(),
				"trainingTypeId" : $jq("#trainingTypeId").val(),
				"trainingRegionId" : $jq("#trainingRegionId").val(),
				"trainingInistituteId" : $jq("#trainingInistituteId").val(),
				"courseYear" : $jq("#courseYear").val(),
				"categoryId" : $jq("#categoryId").val(),
				"type" : $jq("#type").val(),
				"designation" : ""+JSON.stringify(mainJSON),
				"param" : 'manageCourseDesignation'
			};
			$jq.ajax( {
				type : "POST",
				url : "trainingMaster.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq("#result").html(html);
					$jq("#id").val('');
					var resultVal = document.getElementById("resultVal").innerHTML;
					
					document.forms[0].param.value = "getDesignationList";
					document.forms[0].type.value = type;
					$jq.post('trainingMaster.htm', $jq('#trainingMaster').serialize(),function(html) {
						$jq("#result").html(html);
						$jq('#id').val('');
						document.getElementById("resultVal").innerHTML=resultVal;
						clearDesignation();
						countSelectedList();
					});
					
					
				}

			});
	} else {
		if(errorMessage.length > 5)alert(errorMessage);
	}
}
function clearDesignation()
{
	$jq('#SelectRight2 option').remove();
	 $jq('#SelectLeft2 option').remove();
	$jq('#categoryId').val('');
}
function getCourseContentBasedOnCourse(id,jsonTrainingMstDataList)
{
	for ( var i = 0; jsonTrainingMstDataList != null && i < jsonTrainingMstDataList.length; i++) {
		if (jsonTrainingMstDataList[i].id == id) {
			$jq('#id').val('');
			
			$jq('#trainingTypeId').val(jsonTrainingMstDataList[i].trainingTypeId);
			$jq('#trainingInistituteId').val(
					jsonTrainingMstDataList[i].trainingInistituteId);
			$jq('#trainingRegionId').val(
					jsonTrainingMstDataList[i].trainingRegionId);
			$jq('#courseYear').val(
					jsonTrainingMstDataList[i].courseYear);
			$jq('#courseId').val(
					jsonTrainingMstDataList[i].id);
			$jq('#course').val(
					jsonTrainingMstDataList[i].name);
			$jq('#trainingType').val(jsonTrainingMstDataList[i].trainingType);
			$jq('#trainingInistitute').val(
					jsonTrainingMstDataList[i].trainingInistitute);
			$jq('#trainingRegion').val(
					jsonTrainingMstDataList[i].trainingRegion);
			$jq('#year').val(
					jsonTrainingMstDataList[i].year);
			$jq('#type').val('CourseContent');
			$jq('#param').val('CourseContent');
			document.forms[0].action = "trainingMaster.htm";
			document.forms[0].submit();

		}
	}
}
function courseContentSubmit(type)
{
	var errorMessage = "";
	var status = true;
	var del = true;
	//$jq('#courseContent').val(FCKeditorAPI.GetInstance('content').GetData());
	var content = $jq('#courseContent').val();
	content.replace("\n","").replace("\r","").replace(" ","");
	if(content.length==0){
		if($jq("#count").val()==0)
		{
		
			errorMessage += "Enter Course Content\n";
			status = false;
		}
		else
		{
			
			del = confirm("Do you want to delete this item?");
		}
	}
	
		
	if (status && del) {
		$jq('#type').val(type);
		$jq('#param').val('manageCourseContent');
		
		var requestDetails = {
				"id" : $jq("#id").val(),
				"courseId" : $jq("#courseId").val(),
				"trainingTypeId" : $jq("#trainingTypeId").val(),
				"trainingRegionId" : $jq("#trainingRegionId").val(),
				"trainingInistituteId" : $jq("#trainingInistituteId").val(),
				"courseYear" : $jq("#courseYear").val(),
				"categoryId" : $jq("#categoryId").val(),
				"type" : $jq("#type").val(),
				"courseContent" : $jq("#courseContent").val().trim(),
				"param" : 'manageCourseContent'
			};
			$jq.ajax( {
				type : "POST",
				url : "trainingMaster.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq("#result").html(html);
					$jq("#id").val('');
					countSelectedList();
				}

			});
	} else {
		if(errorMessage.length > 5)alert(errorMessage);
	}
}
function clearContent()
{
	if ($jq('#courseContent') != null)
		{
		$jq('#contentcounter').val(500);
		$jq('#contentcounter').val(
				$jq('#contentcounter').val() - $jq('#courseContent').val().length);
	}
//	var oFCKeditor = new FCKeditor('content') ;
//    oFCKeditor.ReplaceTextarea() ;
}
function clearTrainingCirculationDetails()
{
  $jq('#id').val('');
  if($jq('#back').val() !='TrainingCirculationDetails')
  {
  $jq('#startDate').val('');
  $jq('#endDate').val('');
  $jq('#back').val('');  
 
	var requestDetails = {
		"id" : $jq("#id").val(),
		"startDate" : $jq("#startDate").val(),
		"endDate" : $jq("#endDate").val(),
		"type" : $jq("#type").val(),
		"param" : 'TrainingCirculationDetailsDataList'
	};
	$jq.ajax( {
		type : "POST",
		url : "trainingMaster.htm?"+dispUrl,
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq("#displayTable").html(html);
			
		}});
  }
  else
  {
	  $jq('#back').val('');  
	  getTrainingCirculationDetails();
  }
 
  
}
function goBackToTrainingCirculationDetails()
{
	$jq('#back').val('TrainingCirculationDetails');
	$jq('#id').val('');
	$jq('#durationId').val('');
	$jq('#type').val('TrainingCirculationMaster');
	$jq('#param').val('TrainingCirculationDetails');
	document.forms[0].action = "trainingMaster.htm";
	document.forms[0].submit();
}
function getTrainingCirculationDetails()
{
	// var j =
	// compareDates($jq("#startDate").val(),'dd-MMM-yyyy',$jq("#endDate").val(),'dd-MMM-yyyy')
	// alert(j);
	var startDate=$jq("#startDate").val();
	var endDate=$jq("#endDate").val();
	var errorMessage = "";
	var status = true;
	if ($jq("#startDate").val() == "") {
		errorMessage += "Select Start Date.\n";
		status = false;
		$jq("#startDate").focus();
	}
	if ($jq("#endDate").val() == "") {
		errorMessage += "Select End Date.\n";
		if (status) {
			status = false;
			$jq("#endDate").focus();
		}
	}
		if (($jq("#startDate").val() != "") && ($jq("#endDate").val() != "") && (compareDates(startDate, 'dd-MMM-yyyy', endDate,'dd-MMM-yyyy') == 1)) {
			errorMessage += "End Date greater than start Date.\n";
			if (status) {
				status = false;
				$jq("#startDate").focus();
			}
		}
		

	if (status) {
		$jq("#id").val('');
		var requestDetails = {
			"id" : $jq("#id").val(),
			"startDate" : $jq("#startDate").val(),
			"endDate" : $jq("#endDate").val(),
			"type" : $jq("#type").val(),
			"param" : 'TrainingCirculationDetailsDataList'
		};
		$jq.ajax( {
			type : "POST",
			url : "trainingMaster.htm?"+dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#displayTable").html(html);
				
			}});
	
		
	} else {
		alert(errorMessage);
	}

}


function getCourseCirculationBasedOnCourseDuration(id)
{
		$jq("#durationId").val(id);
	
		$jq('#type').val('TrainingCirculationMaster');
		$jq('#param').val('TrainingCirculationMaster');
		document.forms[0].action = "trainingMaster.htm";
		document.forms[0].submit();
}
function reportCirculationDetails()
{
	var startDate=$jq("#startDate").val();
	var endDate=$jq("#endDate").val();
	var errorMessage = "";
	var status = true;
	if ($jq("#startDate").val() == "") {
		errorMessage += "Select Start Date.\n";
		status = false;
		$jq("#startDate").focus();
	}
	if ($jq("#endDate").val() == "") {
		errorMessage += "Select End Date.\n";
		if (status) {
			status = false;
			$jq("#endDate").focus();
		}
	}
		if (($jq("#startDate").val() != "") && ($jq("#endDate").val() != "") && (compareDates(startDate, 'dd-MMM-yyyy', endDate,'dd-MMM-yyyy') == 1)) {
			errorMessage += "End Date greater than start Date.\n";
			if (status) {
				status = false;
				$jq("#startDate").focus();
			}
		}
		if(status)
		{
	window
	.open(
			"./report.htm?param=circulationReport&returnValue=report&trainingTypeId="
					+ $jq("#trainingTypeId").val() + "&categoryId="
					+ $jq("#categoryId").val() + "&courseYear="
					+ $jq("#courseYear").val()
					+ "&startDate="+$jq("#startDate").val()+"&endDate="+$jq("#endDate").val()
					+ "&type=&trainingInistituteId="
					+ $jq("#trainingInistituteId").val(),
			'preview',
			'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}
 else {
	alert(errorMessage);
}
}
function reportION(id)
{
	window
	.open(
			"./report.htm?param=ionReport&returnValue=report&DurationId="+id,
			'preview',
			'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
}
function getEditable(id)
{
	$jq('#'+id).attr("disabled","false");
	document.getElementById(id).disabled=false;
}
function reportNominations(type)
{
	var status = true;
	var errorMessage = "";
	
	if(type =='nomination')
	{
		if ($jq("#courseId1").val() == "") {
			errorMessage += "Select Course.\n";
			status = false;
			$jq("#courseId1").focus();
		}
		if(status)	
		{
		
		window
		.open(
				"./report.htm?param=trainingNominationToMDBReport&returnValue=report&durationId="
						+ $jq("#courseId1").val() + "&boardId="
						+ $jq("#boardId1").val(),
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}
	}
	else if(type == 'boardSelection')
	{
		if ($jq("#courseId2").val() == "") {
			errorMessage += "Select Course.\n";
			status = false;
			$jq("#courseId2").focus();
		}
		
		if(status)	
		{
		window
		.open(
				"./report.htm?param=trainingNominationsApprovedByMDBReport&returnValue=report&durationId="
						+ $jq("#courseId2").val() + "&boardId="
						+ $jq("#boardId2").val(),
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}
	}
	
	else if(type == 'ADSelection')
	{
		
		if ($jq("#courseId3").val() == "") {
			errorMessage += "Select Course.\n";
			status = false;
			$jq("#courseId3").focus();
		}
		if(status)	
		{
		window
		.open(
				"./report.htm?param=trainingNominationsToCDAReport&returnValue=report&durationId="
						+ $jq("#courseId3").val() + "&boardId=",
						
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}
	}
	else if(type == 'FinalAlert')
	{
		if ($jq("#courseId4").val() == "") {
			errorMessage += "Select Course.\n";
			status = false;
			$jq("#courseId4").focus();
		}
		if(status)	
		{
		window
		.open(
				"./report.htm?param=trainingNominationsToInistituteReport&returnValue=report&durationId="
						+ $jq("#courseId4").val() + "&boardId=",
						
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}
	}
	if(!status){
		alert(errorMessage);
	}

		
}
function getNominationFormReport()
{
	window
	.open(
			"./report.htm?param=trainingNominationFormReport&returnValue=report&cirId="
					+ $jq("#courseId").val()+"&nomineeSfid="+$jq("#nomineeSfid").val(),
					
			'preview',
			'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
}
function countSelectedList()
{
	var count = 0;
	if(document.getElementById('courseContent') != null)
	{
		var content = document.getElementById('courseContent').value;
		content.replace('\n','').replace('\r','').replace(' ','');
		count = content.length;
	}
	else
	{
		count = document.getElementById('SelectRight2').length;
	
	}
	$jq("#count").val(count);
}
function editCourseDesignation(id)
{
	$jq('#categoryId').val(id);
	getDesignationsBasedOnCategory();
}
function deleteCourseDesignation(id)
{
	
		var errorMessage = "";
		var status = true;
		var mainJSON = {};
		var del = true;
		
		del = confirm("Do you want to delete this item?");
		
		if (status && del) {
			$jq('#type').val(type);
			$jq('#param').val('manageCourseDesignation');
			
			
			var requestDetails = {
					"id" : $jq("#id").val(),
					"courseId" : $jq("#courseId").val(),
					"trainingTypeId" : $jq("#trainingTypeId").val(),
					"trainingRegionId" : $jq("#trainingRegionId").val(),
					"trainingInistituteId" : $jq("#trainingInistituteId").val(),
					"courseYear" : $jq("#courseYear").val(),
					"categoryId" : id,
					"type" : $jq("#type").val(),
					"designation" : ""+JSON.stringify(mainJSON),
					"param" : $jq('#param').val()
				};
				$jq.ajax( {
					type : "POST",
					url : "trainingMaster.htm?"+dispUrl,
					data : requestDetails,
					cache : false,
					success : function(html) {
						$jq("#result").html(html);
						$jq("#id").val('');
						var resultVal = document.getElementById("resultVal").innerHTML;
						
						document.forms[0].param.value = "getDesignationList";
						document.forms[0].type.value = type;
						$jq('#categoryId').val(id);
						$jq.post('trainingMaster.htm', $jq('#trainingMaster').serialize(),function(html) {
							$jq("#result").html(html);
							$jq('#id').val('');
							document.getElementById("resultVal").innerHTML=resultVal;
							clearDesignation();
							countSelectedList();
						});
						
						
					}

				});
		} else {
			if(errorMessage.length > 5)alert(errorMessage);
		}
	
}
function getHRDGReport()
{
	var errorMessage = "";
	var status = true;
	if ($jq("#trainingTypeId").val() == "") {
		errorMessage += "Select Training Type.\n";
		status = false;
		$jq("#trainingTypeId").focus();
	}
	if ($jq("#courseYear").val() == "") {
		errorMessage += "Select Year.\n";
		status = false;
		$jq("#courseYear").focus();
	}
	if ($jq("#yearBookType").val() == "") {
		errorMessage += "Select Report Type.\n";
		status = false;
		$jq("#yearBookType").focus();
	}
	

	if (status) {

		window
				.open(
						"./report.htm?param=yearBookReport&returnValue=report&courseYear="
								+ $jq("#courseYear").val()
								+ "&type=&trainingTypeId="
								+ $jq("#trainingTypeId").val()+"&yearBookType="+$jq("#yearBookType").val(),
						'preview',
						'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	} else {
		alert(errorMessage);
	}
}
function getHRDGReport()
{
	var errorMessage = "";
	var status = true;
	if ($jq("#trainingTypeId").val() == "") {
		errorMessage += "Select Training Type.\n";
		status = false;
		$jq("#trainingTypeId").focus();
	}
	if ($jq("#courseYear").val() == "") {
		errorMessage += "Select Year.\n";
		status = false;
		$jq("#courseYear").focus();
	}
	if ($jq("#yearBookType").val() == "") {
		errorMessage += "Select Report Type.\n";
		status = false;
		$jq("#yearBookType").focus();
	}
	

	if (status) {

		window
				.open(
						"./report.htm?param=yearBookReport&returnValue=report&courseYear="
								+ $jq("#courseYear").val()
								+ "&type=&trainingTypeId="
								+ $jq("#trainingTypeId").val()+"&yearBookType="+$jq("#yearBookType").val(),
						'preview',
						'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	} else {
		alert(errorMessage);
	}
}
function clearReportPage()
{
	
	$jq("#courseYear").val('');
	$jq("#yearBookType").val('');
	$jq("#trainingTypeId").val('');
	}
function clearViewTrainingCirculationDetails()
{
  $jq('#id').val('');
  
  $jq('#startDate').val('');
  $jq('#endDate').val('');
  $jq('#back').val('');  
 
	var requestDetails = {
		"id" : $jq("#id").val(),
		"startDate" : $jq("#startDate").val(),
		"endDate" : $jq("#endDate").val(),
		"type" : $jq("#type").val(),
		"param" : 'ViewTrainingNominationDetailsDataList'
	};
	$jq.ajax( {
		type : "POST",
		url : "trainingRequest.htm?",
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq("#displayTable").html(html);
			
		}});
 
}
function viewTrainingNominationDetails()
{
	var status= true;
	var errorMessage = "";
	
	if ($jq("#startDate").val() == "") {
		errorMessage += "Select Start Date.\n";
		status = false;
		$jq("#startDate").focus();
	}
	if ($jq("#endDate").val() == "") {
		errorMessage += "Select End Date.\n";
		status = false;
		$jq("#endDate").focus();
	}
	if(status)
	{
		var requestDetails = {
				"id" : $jq("#id").val(),
				"startDate" : $jq("#startDate").val(),
				"endDate" : $jq("#endDate").val(),
				"type" : $jq("#type").val(),
				"param" : 'ViewTrainingNominationDetailsDataList'
			};
			$jq.ajax( {
				type : "POST",
				url : "trainingRequest.htm?",
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq("#displayTable").html(html);
					
				}});
	}
}

function getTrainingCourseDetailsForNomination()
{
	if($jq("#courseId").val() != "")
	{
	var requestDetails = {
			"cirId" : $jq("#courseId").val(),
			"type" : $jq("#type").val(),
			"nomineeSfid" : $jq("#nomineeSfid").val(),
			"param" : 'getBrochureAndION'
		};
		$jq.ajax( {
			type : "POST",
			url : "trainingRequest.htm?",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#ion").html(html);
				$jq("#ion").show();
				
				
			}});
	}
	else
	{
		$jq("#ion").hide();
		
	}



}

function clearNomination()
{
	$jq("#courseId").val('');
	$jq("#currentAssignment").val('');
	$jq('#currentAssignmentCounter').val('200');
	$jq('#lastAttendedCourseCounter').val('1500');
	$jq('#relevanceCounter').val('200');
	$jq("#lastAttendedCourse").val('');
	$jq("#relevance").val('');
	$jq("#ion").hide();
}
function cancelNominationRequest(historyID,requestId,myRequests,status,historyStage)
{
	$jq('#requestId').val(requestId);
	$jq('#param').val('ViewTrainingNomination');
	$jq('#type').val('ViewTrainingNomination');
	document.forms[0].action = "trainingRequest.htm";
	document.forms[0].submit();
	
}
function cancelNominationSubmit()
{
	var status= true;
	var errorMessage = "";
	
	if ($jq("#cancelReason").val() == "") {
		errorMessage += "Enter Cancel Reason.\n";
		status = false;
		$jq("#cancelReason").focus();
	}
	if(status)
	{
	if($jq('#stageID').val()=='1')
	{
		$jq('#requestType').val('TRAINING NOMINATION');
		$jq('#param').val('CancelTrainingNomination');
		$jq('#type').val('CancelTrainingNomination');
		document.forms[0].action = "trainingRequest.htm";
		document.forms[0].submit();
	}
	else
	{
		
		$jq('#requestType').val('TRAINING NOMINATION');
		$jq('#param').val('CancelTrainingNominationAndInitiateReq');
		$jq('#type').val('CancelTrainingNominationAndInitiateReq');
		document.forms[0].action = "trainingRequest.htm";
		document.forms[0].submit();
	}
	}
	else
	{
		alert(errorMessage);
	}
		
}
function backToNominationDetails()
{
	$jq('#param').val('ViewTrainingNominationDetails');
	$jq('#type').val('ViewTrainingNominationDetails');
	document.forms[0].action = "trainingRequest.htm";
	document.forms[0].submit();
}
function setLastAttendedCourse(jsonLastAttendedCourse)
{
	var str = "";
	for ( var i = 0; jsonLastAttendedCourse != null
	&& i < jsonLastAttendedCourse.length; i++) {
		str += jsonLastAttendedCourse[i];
		if(i!=jsonLastAttendedCourse.length-1)
			str +=",";
	}
	$jq('#lastAttendedCourse').val(str);
	$jq('#lastAttendedCourseCounter').val(1500);
					$jq('#lastAttendedCourseCounter').val(
							$jq('#lastAttendedCourseCounter').val() - $jq('#lastAttendedCourse').val().trim().length);
}
function clearAttendedCourses(type)
{
	if ($jq('#trainingTypeId').length > 0)
		$jq('#trainingTypeId').val('');
	
	if ($jq('#course').length > 0)
		$jq('#course').val('');
	if ($jq('#nomineeSfid').length > 0)
		$jq('#nomineeSfid').val('');
	
	if ($jq('#nameCounter').length > 0)
		$jq('#nameCounter').val('200');
	document.getElementById('result1').innerHTML='';
	
	
	if ($jq('#courseYear').length > 0)
		$jq('#courseYear').val('');
	if ($jq('#id').length > 0)
		$jq('#id').val('');
	if ($jq('#back').length > 0)
		$jq('#back').val('');

	if ($jq('#type').length > 0)
		$jq('#type').val(type);
	if ($jq('#durationStartDate').length > 0)
		$jq('#durationStartDate').val('');
	if ($jq('#durationEndDate').length > 0)
		$jq('#durationEndDate').val('');
	if ($jq('#fee').length > 0)
		$jq('#fee').val('');
	if ($jq('#serviceTax').length > 0)
		$jq('#serviceTax').val('');
	
	getCoursesAttendedList();
}
function getCoursesAttendedList()
{
	var errorMessage = "";
	var status = true;
		
	if (status) {
		$jq('#type').val(type);
		$jq('#param').val('CourseAttendedDataList');
		var requestDetails = {
				"trainingTypeId" : $jq("#trainingTypeId").val(),
				"courseYear" : $jq("#courseYear").val(),
				"type" : $jq("#type").val(),
				"param" : 'CourseAttendedDataList'
			};
			$jq.ajax( {
				type : "POST",
				url : "trainingMaster.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq("#displayTable").html(html);
					$jq("#id").val('');
					$jq("#course").val('');
					
				}

			});
	} else {
		alert(errorMessage);
	}
}
function manageCourseAttended(type,jsonFinancialList,jsonMasterDataList)
{
	var errorMessage = "";
	var status = true;
	
	if ($jq("#trainingTypeId").val() == "") {
		errorMessage += "Select Training Type.\n";
		status = false;
		$jq("#trainingTypeId").focus();
	}
	
	if ($jq("#course").val() == "") {
		errorMessage += "Enter Course Name.\n";
		status = false;
		$jq("#course").focus();
	}
	if ($jq("#courseYear").val() == "") {
		errorMessage += "Enter Year.\n";
		status = false;
		$jq("#courseYear").focus();
	}
	if ($jq("#nomineeSfid").val() == "") {
		errorMessage += "Enter SFID.\n";
		status = false;
		$jq("#nomineeSfid").focus();
	}
	if ($jq("#durationStartDate").val() == "") {
		errorMessage += "Enter Start Date\n";
		status = false;
		$jq("#durationStartDate").focus();
	}
	if ($jq("#durationEndDate").val() == "") {
		errorMessage += "Enter End Date\n";
		status = false;
		$jq("#durationEndDate").focus();
	}
	if (($jq("#durationStartDate").val() != "") && ($jq("#durationEndDate").val() != "") && (compareDates($jq("#durationStartDate").val(), 'dd-MMM-yyyy', $jq("#durationEndDate").val(),'dd-MMM-yyyy') == 1)) {
		errorMessage += "End Date greater than start Date.\n";
		if (status) {
			status = false;
			$jq("#durationStartDate").focus();
		}
	}
	if(document.getElementById('employeeName')!= null)
	{
		errorMessage += "Enter valid SFID.\n";
		status = false;
		$jq("#nomineeSfid").focus();
	}
	if(status)
	{
		for(var i = 0;jsonFinancialList != null && jsonFinancialList.length != 0 && i < jsonFinancialList.length;i++)
		{
			if(jsonFinancialList[i].id==$jq("#courseYear").val())
			{
			  finStartDate = jsonFinancialList[i].lastModifiedBy;
			  finEndDate = jsonFinancialList[i].lastModifiedDate;
			  var startdate = $jq("#durationStartDate").val();
			  var enddate = $jq("#durationEndDate").val();
			  
			  if((compareDates(finStartDate, 'dd-MMM-yyyy',startdate ,'dd-MMM-yyyy') == 1) || (compareDates(startdate,'dd-MMM-yyyy',finEndDate,'dd-MMM-yyyy') == 1) )
			  {
				  	errorMessage += "Start Date should be in financial Year\n";
					status = false;
					$jq("#durationStartDate").focus();
			  }
			  if((compareDates(finStartDate, 'dd-MMM-yyyy',enddate,'dd-MMM-yyyy') == 1) || (compareDates(enddate, 'dd-MMM-yyyy', finEndDate,'dd-MMM-yyyy') == 1) )
			  {
				  	errorMessage += "End Date should be in financial Year\n";
					status = false;
					$jq("#durationEndDate").focus();
			  }
	
			}
		}
	}
	if (status) {
			var startDate = $jq("#durationStartDate").val();
			var endDate = $jq("#durationEndDate").val();
			var sDate1 = '';
			var eDate1 = '';
			

			for ( var i = 0; jsonMasterDataList != null
					&& i < jsonMasterDataList.length; i++) {
				var sDate = jsonMasterDataList[i].durationStartDate;
				var eDate = jsonMasterDataList[i].durationEndDate;
				var idValue = jsonMasterDataList[i].id;
				var id = $jq("#id").val();
				var sfidval = $jq("#nomineeSfid").val();
				var jsonsfidval = jsonMasterDataList[i].nomineeSfid;
				
				if(idValue != id && jsonsfidval.toUpperCase() == sfidval.toUpperCase())
				{
				if (startDate == sDate
						|| startDate == eDate
						|| endDate == sDate
						|| endDate == eDate
						|| ((compareDates(startDate, 'dd-MMM-yyyy', sDate,
								'dd-MMM-yyyy') == 1) && (compareDates(eDate, 'dd-MMM-yyyy', startDate,
								'dd-MMM-yyyy') == 1))
						|| ((compareDates(endDate, 'dd-MMM-yyyy', sDate,
							'dd-MMM-yyyy') == 1) && (compareDates(eDate, 'dd-MMM-yyyy', endDate,
							'dd-MMM-yyyy') == 1))
						|| ((compareDates(sDate, 'dd-MMM-yyyy', startDate,
								'dd-MMM-yyyy') == 1) && (compareDates(eDate,
								'dd-MMM-yyyy', endDate, 'dd-MMM-yyyy') == 0))) {
					status = false;
					$jq("#durationStartDate").focus();
					errorMessage += "Course Start Date or Course End Date already assigned.\n";
				}
				}

			}
		}
	
	
	
	
	if ($jq("#fee").val()=="0") {
		$jq("#fee").val('');
	}
	if ($jq("#serviceTax").val()=="0") {
		$jq("#serviceTax").val('');
	}
	
	
	if (status) {
		$jq('#type').val(type);
		$jq('#param').val('manageCourseAttended');
		var requestDetails = {
				"id" : $jq("#id").val(),
				"course" : $jq("#course").val(),
				"trainingTypeId" : $jq("#trainingTypeId").val(),
				"nomineeSfid" : $jq("#nomineeSfid").val(),
				"courseYear" : $jq("#courseYear").val(),
				"durationStartDate" : $jq("#durationStartDate").val(),
				"durationEndDate" : $jq("#durationEndDate").val(),
				"fee" : $jq("#fee").val().trim(),
				"serviceTax" : $jq("#serviceTax").val().trim().replace('%',''),
				"type" : $jq("#type").val(),
				"param" : 'manageCourseAttended'
			};
			$jq.ajax( {
				type : "POST",
				url : "trainingMaster.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq("#displayTable").html(html);
					if ($jq('.success').is(':visible')) {
					$jq("#id").val('');
					
					}
					
				}

			});
	} else {
		alert(errorMessage);
	}
}
function editCourseAttended(jsonMasterDataList,id)
{
	for ( var i = 0; jsonMasterDataList != null
	&& jsonMasterDataList.length > 0 && i < jsonMasterDataList.length; i++) {
if (jsonMasterDataList[i].id == id) {
	$jq('#id').val(jsonMasterDataList[i].id);
	
	$jq('#trainingTypeId').val(jsonMasterDataList[i].trainingTypeId);
	$jq('#courseYear').val(jsonMasterDataList[i].courseYear);	
	$jq('#course').val(jsonMasterDataList[i].course);
	$jq('#nomineeSfid').val(jsonMasterDataList[i].nomineeSfid);
	$jq('#durationStartDate').val(jsonMasterDataList[i].durationStartDate);
	$jq('#durationEndDate').val(jsonMasterDataList[i].durationEndDate);
	$jq('#fee').val(jsonMasterDataList[i].fee);
	$jq('#serviceTax').val(jsonMasterDataList[i].serviceTax);
	$jq('#nameCounter').val(200-$jq('#course').val().length);
	getEmployeeName();

}

}
}
function deleteCourseAttended(id,type)
{
	$jq('#type').val(type);
	$jq('#param').val('deleteCourseAttended');
	var requestDetails = {
			"id" : id,
			"trainingTypeId" : $jq("#trainingTypeId").val(),
			"courseYear" : $jq("#courseYear").val(),
			"type" : $jq("#type").val(),
			"param" : 'deleteCourseAttended'
		};
		$jq.ajax( {
			type : "POST",
			url : "trainingMaster.htm?"+dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#displayTable").html(html);
				if ($jq('.success').is(':visible')) {
				$jq("#id").val('');
				$jq("#course").val('');
				$jq("#nomineeSfid").val('');
				}
				
			}

		});
} 
function getEmployeeName()
{
	$jq("#nomineeSfid").val($jq("#nomineeSfid").val().toUpperCase());
	$jq('#type').val(type);
	$jq('#param').val('getEmployeeName');
	var requestDetails = {
			"nomineeSfid" : $jq("#nomineeSfid").val(),
			"type" : $jq("#type").val(),
			"param" : 'getEmployeeName'
		};
		$jq.ajax( {
			type : "POST",
			url : "trainingMaster.htm?"+dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#result1").html(html);
				
				
			}

		});
}
function getCirculationStartDate(jsonIonMstList)
{
	var id = $jq('#ionId').val();
	for(var i = 0;jsonIonMstList != null && jsonIonMstList.length != 0 && i< jsonIonMstList.length;i++)
	{
		if(id != '' && id ==jsonIonMstList[i].id)
		{
			$jq('#circulationDate').val(jsonIonMstList[i].ionDate);
		}	
	}
	
}

function getCirculationIonList()
{
	$jq('#type').val(type);
	$jq('#param').val('getIonMstList');
	var requestDetails = {
			"letterFormatId" : $jq("#letterFormatId").val(),
			"id" : $jq("#id").val(),
			"type" : $jq("#type").val(),
			"param" : 'getIonMstList'
		};
		$jq.ajax( {
			type : "POST",
			url : "trainingMaster.htm?"+dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#result2").html(html);
				
				
			}

		});
	
}
function setIONMstList(jsonIonMstList)
{
	$jq('#ionId').find('option').remove().end();
	$jq("#ionId").append('<option value="">Select</option>');

	for ( var i = 0; jsonIonMstList != null
			&& i < jsonIonMstList.length; i++) {
	
			$jq("#ionId").append(
					'<option value=' + jsonIonMstList[i].id + '>'
							+ jsonIonMstList[i].letterNumber + '</option>');
		
	}
}
function getCirculationIonLists(val)
{
	$jq('#type').val(type);
	$jq('#param').val('getIonMstList');
	if(val =='2')$jq("#letterFormatId").val($jq("#letterFormatId2").val())
	if(val =='3')$jq("#letterFormatId").val($jq("#letterFormatId3").val())
	if(val =='4')$jq("#letterFormatId").val($jq("#letterFormatId4").val())
	var requestDetails = {
			
			"letterFormatId" : $jq("#letterFormatId").val(),
			"type" : $jq("#type").val(),
			"param" : 'getIonMstList',
			"ionFlag" : val
		};
		$jq.ajax( {
			type : "POST",
			url : "trainingRequest.htm?"+dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
			    if(val =='2')
			    {
				$jq("#result2").html(html);
				
			    }
			    if(val =='3')
			    {
				$jq("#result31").html(html);
				
			    }
			    if(val =='4')
			    {
				$jq("#result4").html(html);
				
			    }
				
			$jq('#ionFlag').val('');
			$jq('#letterFormatId').val('');	
			}

		});
}

function setIONMstLists(jsonIonMstList,type)
{
	if(type =='2')
	{
		$jq('#ionId2').find('option').remove().end();
		$jq("#ionId2").append('<option value="">Select</option>');
		

		for ( var i = 0; jsonIonMstList != null
				&& i < jsonIonMstList.length; i++) {
		
				$jq("#ionId2").append(
						'<option value=' + jsonIonMstList[i].id + '>'
								+ jsonIonMstList[i].letterNumber + '</option>');
			
		}
	}
	if(type =='3')
	{
		$jq('#ionId3').find('option').remove().end();
		$jq("#ionId3").append('<option value="">Select</option>');
		

		for ( var i = 0; jsonIonMstList != null
				&& i < jsonIonMstList.length; i++) {
		
				$jq("#ionId3").append(
						'<option value=' + jsonIonMstList[i].id + '>'
								+ jsonIonMstList[i].letterNumber + '</option>');
			
		}
	}
	if(type =='4')
	{
		$jq('#ionId4').find('option').remove().end();
		$jq("#ionId4").append('<option value="">Select</option>');
		

		for ( var i = 0; jsonIonMstList != null
				&& i < jsonIonMstList.length; i++) {
		
				$jq("#ionId4").append(
						'<option value=' + jsonIonMstList[i].id + '>'
								+ jsonIonMstList[i].letterNumber + '</option>');
			
		}
	}
}
function getLetterFormatListForCourse()
{
var requestDetails = {
			
			"courseId" : $jq("#courseId3").val(),
			"type" : $jq("#type").val(),
			"param" : 'getLetterFormatMstList'
			
		};
		$jq.ajax( {
			type : "POST",
			url : "trainingRequest.htm?"+dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
			   
				$jq("#result3").html(html);
		
			}

		});
}
function checkFloat1(e,id){
	var key;
	var value = document.getElementById(id).value; 
	var str1 = value.split(".");
	if(window.event){
		key = window.event.keyCode;
	}
	else{
		key = e.which;
	}
	if(key==46){	
	  var check = true;
		for(j=0;j<str1.length;j++){
			if(check){
				check = false;
			}else{
				alert("Invalid character");
				document.getElementById(id).focus();
				return false;
			}
		}
	}	
	if( key!=8 && key!=0 && key!=46 && (key<48 || key>57)){
		alert("Please enter numbers only");
		document.getElementById(id).focus();
		return false;
	}
	if(key==8 || key==0){
		document.getElementById(id).focus();
		return true;
	}else{
		document.getElementById(id).focus();
	    return true;
	}
}

function manageHRDGBoardType(type)
{
	var errorMessage = "";
	var status = true;
	if ($jq("#name").val() == "") {
		errorMessage += "Select Board Type.\n";
		status = false;
		$jq("#name").focus();
	}
	
	
	if (status) {
		$jq('#type').val(type);
		$jq('#param').val('manageHRDGBoardType');
		var requestDetails = {
				"id" : $jq("#id").val(),
				"name" : $jq("#name").val(),
				"description" : $jq("#description").val(),
				"type" : $jq("#type").val(),
				"param" : $jq("#param").val()
			};
			$jq.ajax( {
				type : "POST",
				url : "trainingMaster.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq("#result").html(html);
					if ($jq('.success').is(':visible')) {
					$jq("#id").val('');
					$jq("#name").val('');
					$jq("#description").val('');
					$jq('#counter').val('500');
					
					}
					
				}

			});
	} else {
		alert(errorMessage);
	}
}
function editHRDGBoardType(jsonMasterDataList,id)
{
	for ( var i = 0; jsonMasterDataList != null
	&& jsonMasterDataList.length > 0 && i < jsonMasterDataList.length; i++) {
		if (jsonMasterDataList[i].id == id) {
		$jq('#id').val(jsonMasterDataList[i].id);
		$jq('#name').val(jsonMasterDataList[i].name);
		$jq('#description').val(jsonMasterDataList[i].description);
		$jq('#counter').val(500-jsonMasterDataList[i].description.length);
	   
		}
	
	

	}
}
function getHrdgBoardTypeList(id)
{

	$jq('#type').val('HRDGBoardType');
	$jq('#param').val('HRDGBoardType');
	$jq('#back').val('');
	$jq('#id').val('');
	
	
document.forms[0].action = "trainingMaster.htm";
document.forms[0].submit();
	

}
function manageHRDGBoardMemberType(type)
{
	var errorMessage = "";
	var status = true;
	if ($jq("#name").val() == "") {
		errorMessage += "Select Board Member Type.\n";
		status = false;
		$jq("#name").focus();
	}
	
	if ($jq("#maxLimit").val() == "") {
		errorMessage += "Enter Members Limit.\n";
		status = false;
		$jq("#maxLimit").focus();
	}

	
	
	if (status) {
		$jq('#type').val(type);
		$jq('#param').val('manageHRDGBoardMemberType');
		var requestDetails = {
				"id" : $jq("#id").val(),
				"name" : $jq("#name").val().trim(),
				"description" : $jq("#description").val(),
				"maxLimit" : $jq("#maxLimit").val(),
				"type" : $jq("#type").val(),
				"param" : $jq("#param").val()
			};
			$jq.ajax( {
				type : "POST",
				url : "trainingMaster.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq("#result").html(html);
					if ($jq('.success').is(':visible')) {
					$jq("#id").val('');
					$jq("#name").val('');
					$jq("#description").val('');
					$jq('#maxLimit').val('');
					$jq('#counter').val('500');
					
					}
					
				}

			});
	} else {
		alert(errorMessage);
	}
}
function editHRDGBoardMemberType(jsonMasterDataList,id)
{
	for ( var i = 0; jsonMasterDataList != null
	&& jsonMasterDataList.length > 0 && i < jsonMasterDataList.length; i++) {
		if (jsonMasterDataList[i].id == id) {
		$jq('#id').val(jsonMasterDataList[i].id);
		$jq('#name').val(jsonMasterDataList[i].name);
		$jq('#description').val(jsonMasterDataList[i].description);
		$jq('#maxLimit').val(jsonMasterDataList[i].maxLimit);
		$jq('#counter').val(500-jsonMasterDataList[i].description.length);
	
		}
	
	

	}
}

function getHrdgBoardMemberTypeList(id)
{

	$jq('#type').val('HRDGBoardMemberType');
	$jq('#param').val('HRDGBoardMemberType');
	$jq('#back').val('');
	$jq('#id').val('');
	
	
document.forms[0].action = "trainingMaster.htm";
document.forms[0].submit();
	

}

function goBackToHrdgBoardMember()
{

	$jq('#type').val('HRDGBoardMember');
	$jq('#param').val('HRDGBoardMember');
	
	$jq('#id').val('');
	
document.forms[0].action = "trainingMaster.htm";
document.forms[0].submit();
}
function getHRDGReports()
{
	var errorMessage = "";
	var status = true;
	
	if ($jq("#courseYear").val() == "") {
		errorMessage += "Select Year.\n";
		status = false;
		$jq("#courseYear").focus();
	}
	if ($jq("#yearBookType").val() == "") {
		errorMessage += "Select Report Type.\n";
		status = false;
		$jq("#yearBookType").focus();
	}
	

	if (status) {

		window
				.open(
						"./report.htm?param=HRDGReport&returnValue=report&courseYear="
								+ $jq("#courseYear").val()
								+ "&type="
								+"&yearVal="+$jq('#courseYear option:selected').text()
								+"&yearBookType="+$jq("#yearBookType").val(),
						'preview',
						'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	} else {
		alert(errorMessage);
	}
	
}
function example1()
{
	alert($jq('tr#onetr').val());
}
















