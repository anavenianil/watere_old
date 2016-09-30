function manageLetterNumber(jsonLetterNumberSeriesList) {
	var errorMessage = "";
	var status = true;
	if ($jq("#deptNum").val() == "") {
		errorMessage += "Select Department Name.\n";
		status = false;
		$jq("#deptNum").focus();
	}

	if ($jq("#deptSeriesStartNum").val() == "") {
		errorMessage += "Enter Department series Start Number.\n";
		status = false;
		$jq("#deptSeriesStartNum").focus();
	}
	if ($jq("#deptSeriesEndNum").val() == "") {
		errorMessage += "Enter Department series Last Number.\n";
		status = false;
		$jq("#deptSeriesEndNum").focus();
	}
	if ($jq("#deptCode").val() == "") {
		errorMessage += "Enter Department Code.\n";
		status = false;
		$jq("#deptCode").focus();
	}

	if (status) {
		for ( var i = 0; status && jsonLetterNumberSeriesList != null
				&& jsonLetterNumberSeriesList.length != 0
				&& i < jsonLetterNumberSeriesList.length; i++) {
			var id = parseInt($jq('#id').val());
			var startNum = parseInt($jq('#deptSeriesStartNum').val());
			var endNum = parseInt($jq('#deptSeriesEndNum').val());
			var deptNum = $jq('#deptNum').val();
			var deptCode = $jq('#deptCode').val();
			var deptShortName = $jq('#deptShortName').val();
			var jsonid = jsonLetterNumberSeriesList[i].id;
			var jsonStartNum = jsonLetterNumberSeriesList[i].deptSeriesStartNum;
			var jsonEndNum = jsonLetterNumberSeriesList[i].deptSeriesEndNum;
			var jsondeptNum = jsonLetterNumberSeriesList[i].deptNum;
			var jsondeptCode = jsonLetterNumberSeriesList[i].deptCode;
			var jsondeptShortName = jsonLetterNumberSeriesList[i].deptShortName;

			if (id == '' || id != jsonid) {
				if (deptNum == jsondeptNum) {
					errorMessage += "Department already exists.\n";
					status = false;

				}
				if (deptCode == jsondeptCode) {
					errorMessage += "Department Code already exists.\n";
					status = false;

				}
				if (deptShortName != '' && jsondeptShortName != '') {
					deptShortName = $jq.trim(deptShortName).toUpperCase();
					jsondeptShortName = $jq.trim(jsondeptShortName).toUpperCase();
					if (deptShortName == jsondeptShortName) {
						errorMessage += "Department Short Name already exists.\n";
						status = false;
					}

				}
				if (startNum >= endNum) {
					errorMessage += "Series Start Number should be less than Series Last Number.\n";
					status = false;

				}
				if (startNum >= jsonStartNum && startNum <= jsonEndNum) {
					errorMessage += "Enter valid Series Start Number.(Series Start Number exists between another series)\n";
					status = false;

				}
				if (endNum >= jsonStartNum && endNum <= jsonEndNum) {
					errorMessage += "Enter valid Series Last Number.(Series Last Number exists between another series)\n";
					status = false;

				}
				if (startNum <= jsonStartNum && endNum >= jsonEndNum) {
					errorMessage += "Enter valid Series Start Number & Series Last Number.(Another series exists between Series Start Number & Series Last Number)\n";
					status = false;

				}

			}

		}

	}

	if (status) {

		var requestDetails = {
			"id" : $jq("#id").val(),
			"deptNum" : $jq("#deptNum").val(),
			"deptSeriesStartNum" : $jq("#deptSeriesStartNum").val(),
			"deptSeriesEndNum" : $jq("#deptSeriesEndNum").val(),
			"deptShortName" : $jq("#deptShortName").val(),
			"deptCode" : $jq("#deptCode").val(),
			"param" : 'manageLetterNumber'
		};
		$jq.ajax( {
			type : "POST",
			url : "letterNumberFormat.htm?" + dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#displayTable").html(html);
				if ($jq('.success').is(':visible')) {
					clearLetterNumber();
				}

			}

		});
	} else {
		alert(errorMessage);
	}
}
function clearLetterNumber() {
	$jq("#id").val('');
	$jq("#deptNum").val('');
	$jq("#deptSeriesStartNum").val('');
	$jq("#deptSeriesEndNum").val('');
	$jq("#deptShortName").val('');
	$jq("#deptCode").val('');
}
function editLetterNumber(jsonLetterNumberSeriesList, id) {

	for ( var i = 0; jsonLetterNumberSeriesList != null
			&& jsonLetterNumberSeriesList.length != 0
			&& i < jsonLetterNumberSeriesList.length; i++) {
		if (jsonLetterNumberSeriesList[i].id == id) {
			$jq('#id').val(jsonLetterNumberSeriesList[i].id);
			$jq('#deptNum').val(jsonLetterNumberSeriesList[i].deptNum);
			$jq('#deptShortName').val(
					jsonLetterNumberSeriesList[i].deptShortName);
			$jq('#deptCode').val(jsonLetterNumberSeriesList[i].deptCode);
			$jq('#deptSeriesStartNum').val(
					jsonLetterNumberSeriesList[i].deptSeriesStartNum);
			$jq('#deptSeriesEndNum').val(
					jsonLetterNumberSeriesList[i].deptSeriesEndNum);

		}
	}

}
function deleteLetterNumber(id) {
	$jq("#id").val(id);
	var requestDetails = {
		"id" : $jq("#id").val(),
		"param" : 'deleteLetterNumber'
	};
	$jq.ajax( {
		type : "POST",
		url : "letterNumberFormat.htm?" + dispUrl,
		data : requestDetails,
		cache : false,
		success : function(html) {
      		clearLetterNumber();
			$jq("#displayTable").html(html);
			if ($jq('.success').is(':visible')) {
				
			}

		}
	
	});
}
function clearLetterNumberFormat() {
	$jq("#id").val('');
	$jq("#deptNum").val('');
	$jq("#topic").val('');
	$jq("#shortForm").val('');
	$jq("#yearType").val('');
	$jq("#serialNum").val('');
	$jq("#runningNum").val('');
	$jq('#deptSeriesStartNum').val('');
	$jq('#deptSeriesEndNum').val('');
	$jq("#result1").hide();

	$jq('#yearTypef').attr('checked', true);
	$jq('#back').val('');
	

}
function getDeptCodeAndSeries(jsonLetterNumberSerisList) {
	
	if(document.getElementById('result1')!= null)
	{
		$jq("#result1").hide();
	var deptNum = parseInt($jq('#deptNum').val());
	$jq('#deptSeriesStartNum').val('');
	$jq('#deptSeriesEndNum').val('');
	for ( var i = 0; jsonLetterNumberSerisList != null
			&& jsonLetterNumberSerisList.length != 0
			&& i < jsonLetterNumberSerisList.length; i++) {
		var jsondeptNum = jsonLetterNumberSerisList[i].deptNum;
		if (deptNum == jsondeptNum) {
			document.getElementById('deptCodeVal').innerHTML = jsonLetterNumberSerisList[i].deptCode;
			document.getElementById('deptSeriesVal').innerHTML = jsonLetterNumberSerisList[i].series;
			$jq('#deptSeriesStartNum').val(
					jsonLetterNumberSerisList[i].deptSeriesStartNum);
			$jq('#deptSeriesEndNum').val(
					jsonLetterNumberSerisList[i].deptSeriesEndNum);
			$jq("#result1").show();
		}
	}
	}
}
function getDeptLetterNumberFormat() {
	var requestDetails = {
		"deptNum" : $jq("#deptNum").val(),
		"back" : $jq("#back").val(),
		"param" : 'getDeptLetterNumberFormat'
	};
	$jq.ajax( {
		type : "POST",
		url : "letterNumberFormat.htm?" + dispUrl,
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq("#displayTable").html(html);

		}

	});
}

function manageLetterNumberFormat(jsonLetterNumberFormatList) {
	var errorMessage = "";
	var status = true;
	if ($jq("#deptNum").val() == "") {
		errorMessage += "Select Department Name.\n";
		status = false;
		$jq("#deptNum").focus();
	}

	if ($jq("#topic").val() == "") {
		errorMessage += "Enter Topic.\n";
		status = false;
		$jq("#topic").focus();
	}
	if ($jq("#serialNum").val() == "") {
		errorMessage += "Enter Serial Number.\n";
		status = false;
		$jq("#serialNum").focus();

	}
	if ($jq("#shortForm").val() == "") {
		errorMessage += "Enter Short Form.\n";
		status = false;
		$jq("#shortForm").focus();
	}
	if ($jq("#runningNum").val() == "") {
		errorMessage += "Enter Running Number.\n";
		status = false;
		$jq("#runningNum").focus();
	}
	if ($jq("#fileTypeId").val() == "") {
		errorMessage += "Enter File Type.\n";
		status = false;
		$jq("#fileTypeId").focus();
	}

	if ($jq('#deptSeriesStartNum').val() == ''
			|| $jq('#deptSeriesEndNum').val() == '') {
		errorMessage += "First fill details in Letter Number Series.Then you can create Letter Number Format.\n";
		status = false;

	}

	if (status) {

		var serialNum = parseInt($jq('#serialNum').val());
		var deptSeriesStartNum = parseInt($jq('#deptSeriesStartNum').val());
		var deptSeriesEndNum = parseInt($jq('#deptSeriesEndNum').val());
		if (serialNum < deptSeriesStartNum || deptSeriesEndNum < serialNum) {
			errorMessage += "Serial Number should be in the Series.\n";
			status = false;
			$jq("#runningNum").focus();
		}

		for ( var i = 0; status && jsonLetterNumberFormatList != null
				&& jsonLetterNumberFormatList.length != 0
				&& i < jsonLetterNumberFormatList.length; i++) {
			var id = parseInt($jq('#id').val());
			var serialNum = parseInt($jq('#serialNum').val());
			var shortForm = $jq('#shortForm').val();
			var topic = $jq('#topic').val();

			var jsonid = jsonLetterNumberFormatList[i].id;
			var jsonSerialNum = jsonLetterNumberFormatList[i].serialNum;
			var jsonshortForm = jsonLetterNumberFormatList[i].shortForm;
			var jsontopic = jsonLetterNumberFormatList[i].topic;

			if (id == '' || id != jsonid) {
				if (serialNum == jsonSerialNum) {
					errorMessage += "Serial Number already exists.\n";
					status = false;

				}
				if ($jq.trim(shortForm).toUpperCase() == jsonshortForm.trim()
						.toUpperCase()) {
					errorMessage += "Short Form already exists.\n";
					status = false;

				}
				if ($jq.trim(topic).toUpperCase() == $jq.trim(jsontopic)
						.toUpperCase()) {
					errorMessage += "Topic already exists.\n";
					status = false;

				}

			}

		}

	}

	if (status) {
		if ($jq('#yearTypef').is(':checked'))
			$jq('#yearType').val($jq('#yearTypef').val());
		if ($jq('#yearTypec').is(':checked'))
			$jq('#yearType').val($jq('#yearTypec').val());
		var requestDetails = {
			"id" : $jq("#id").val(),
			"deptNum" : $jq("#deptNum").val(),
			"shortForm" : $jq("#shortForm").val(),
			"serialNum" : $jq("#serialNum").val(),
			"yearType" : $jq("#yearType").val(),
			"topic" : $jq("#topic").val(),
			"runningNum" : $jq("#runningNum").val(),
			"fileTypeId" : $jq("#fileTypeId").val(),
			"param" : 'manageLetterNumberFormat'
		};
		$jq.ajax( {
			type : "POST",
			url : "letterNumberFormat.htm?" + dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#displayTable").html(html);
				if ($jq('.success').is(':visible')) {
					$jq("#id").val('');
					$jq("#topic").val('');
					$jq("#shortForm").val('');
					$jq("#yearType").val('');
					$jq("#serialNum").val('');
					$jq("#runningNum").val('');
				}

			}

		});
	} else {
		alert(errorMessage);
	}
}
function editLetterNumberFormat(jsonLetterNumberFormatList, id) {

	for ( var i = 0; jsonLetterNumberFormatList != null
			&& jsonLetterNumberFormatList.length != 0
			&& i < jsonLetterNumberFormatList.length; i++) {
		var idval = jsonLetterNumberFormatList[i].id;
		if (idval == id) {
			$jq('#id').val(jsonLetterNumberFormatList[i].id);
			$jq('#deptNum').val(jsonLetterNumberFormatList[i].deptNum);
			$jq('#shortForm').val(jsonLetterNumberFormatList[i].shortForm);
			$jq('#topic').val(jsonLetterNumberFormatList[i].topic);
			$jq('#serialNum').val(jsonLetterNumberFormatList[i].serialNum);
			$jq('#yearType').val(jsonLetterNumberFormatList[i].yearType);
			$jq('#runningNum').val(jsonLetterNumberFormatList[i].runningNum);
			$jq('#fileTypeId').val(jsonLetterNumberFormatList[i].fileTypeId);
			if ($jq('#yearType').val() == $jq('#yearTypef').val())
				$jq('#yearTypef').attr('checked', true);
			if ($jq('#yearType').val() == $jq('#yearTypec').val())
				$jq('#yearTypec').attr('checked', true);

		}
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
function deleteLetterNumberFormat(id) {
	$jq("#id").val(id);
	var requestDetails = {
		"id" : $jq("#id").val(),
		"deptNum" : $jq("#deptNum").val(),
		"param" : 'deleteLetterNumberFormat'
	};
	$jq.ajax( {
		type : "POST",
		url : "letterNumberFormat.htm?" + dispUrl,
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq("#displayTable").html(html);
			if ($jq('.success').is(':visible')) {
				$jq("#id").val('');
				$jq("#topic").val('');
				$jq("#shortForm").val('');
				$jq("#yearType").val('');
				$jq("#serialNum").val('');
				$jq("#runningNum").val('');
			}

		}

	});
}
function getIncrementLetterNumberFormat(id, jsonLetterNumberFormatList) {
	$jq('#id').val(id);
	$jq('#param').val('getIncrementLetterNumberFormat');
	$jq('#type').val('getIncrementLetterNumberFormat');
	$jq('#letterFormatId').val(id);
	document.forms[0].action = "letterNumberFormat.htm";
	document.forms[0].submit();

}
function gotoLetterNumberFormat() {
	$jq('#id').val('');
	$jq('#back').val('letterNumberFormatMaster');
	$jq('#param').val('letterNumberFormatMaster');
	if(document.getElementById('backType') == null)
		$jq('#param').val('letterNumberFormatMaster1');
	$jq('#type').val('letterNumberFormatMaster');
	document.forms[0].action = "letterNumberFormat.htm";
	document.forms[0].submit();
}
function clearLetterNumberFormats(jsonLetterNumberSerisList) {
	
	$jq('#back').val('');
	getDeptLetterNumberFormat();
	getDeptCodeAndSeries(jsonLetterNumberSerisList);

}
function clearLetterNumberFormatDetails() {
	$jq('#id').val('');
	$jq('#ionDate').val(formatDate(new Date(),'dd-NNN-yyyy'));
	$jq('#back').val('');
	$jq('#delegationn').attr('checked', true);
	$jq("#result1").hide();

}
function changeDelegationStatus() {
	if ($jq('#delegationn').is(':checked'))
		$jq("#result1").hide();

	if ($jq('#delegationy').is(':checked'))
		$jq("#result1").show();

}
function manageLetterNumberFormatDetails(jsonIonMstList) {
	var errorMessage = "";
	var status = true;
	if ($jq("#ionDate").val() == "") {
		errorMessage += "Select Date.\n";
		status = false;
		$jq("#ionDate").focus();
	}

	if ($jq("#delegationy").is(':checked') && $jq("#delegation").val() == "") {
		errorMessage += "Enter Delegation.\n";
		status = false;
		$jq("#delegation").focus();
	}
	var status1=true;
	
	for(var i =0;jsonIonMstList != null && i< jsonIonMstList.length;i++)
	{
		if(compareDates($jq("#ionDate").val(),'dd-MMM-yyyy',jsonIonMstList[i].ionDate,'dd-MMM-yyyy')==-1 )
		{
			status1=false;
		}
	}
	if(!status1)
	{
		errorMessage += "Enter ION Date not less than previous ION Date.\n";
		status = false;
		$jq("#ionDate").focus();
	}
	status1=true;
	var j = 0;
	for(var i =0;jsonIonMstList != null && i< jsonIonMstList.length;i++)
	{
		if(jsonIonMstList[i].circulationStatus!=5)
		{
			status1=false;
			j++;
		}
	}
	if(j>101)
	{
		errorMessage += "Previous ION not Circulated.\n";
		status = false;
		$jq("#ionDate").focus();
	}
	

	if (status) {
		if ($jq('#delegationn').is(':checked'))
			$jq('#delegation').val('');

		var requestDetails = {
			"id" : $jq("#id").val(),
			"letterNumber" : $jq("#letterNumber").val(),
			"letterFormatId" : $jq("#letterFormatId").val(),
			"ionDate" : $jq("#ionDate").val(),
			"delegation" : $jq("#delegation").val(),
			"param" : 'manageLetterNumberFormatDetails'
		};
		$jq.ajax( {
			type : "POST",
			url : "letterNumberFormat.htm?" + dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#displayTable").html(html);
				if ($jq('.success').is(':visible')) {
					$jq("#id").val('');
					clearManageLetterNumberFormatDetails();
					$jq('#editIncreButton').css("display","block");
					$jq('#editSubmitButton').css("display","none");
				}
	}

		});
	} else {
		alert(errorMessage);
	}
}
//new code added
function clearManageLetterNumberFormatDetails(){
	$jq('#delegation').val('');
	$jq('#ionDate').val('');
	//$jq("input:radio").attr("checked", false);
}
function deleteLetterNumberFormatDetails(jsonIonMstList , id){
	 var letterFormatId = "";
	var check=confirm("Do u want to delete ?");
	for ( var i = 0; jsonIonMstList != null && jsonIonMstList.length != 0
	&& i < jsonIonMstList.length; i++) {
    var idval = jsonIonMstList[i].id;
    if (idval == id) {
    	letterFormatId = jsonIonMstList[i].letterFormatId;
	}
    }
	if(check){
		var requestDetails = {
			"param" : "deleteLetterNumberFormatDetails",
			"pk" :id,
			"letterFormatId" :letterFormatId
		};
		$jq.post('letterNumberFormat.htm'+'?'+dispUrl, requestDetails, function(html) {
			$jq("#displayTable").html(html);
		});
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
function editLetterNumberFormatDetails(jsonIonMstList, id) {

	for ( var i = 0; jsonIonMstList != null && jsonIonMstList.length != 0
			&& i < jsonIonMstList.length; i++) {
		var idval = jsonIonMstList[i].id;
		if (idval == id) {
			$jq('#id').val(jsonIonMstList[i].id);
			$jq('#letterNumber').val(jsonIonMstList[i].letterNumber);
			$jq('#ionDate').val(jsonIonMstList[i].ionDate);
			$jq('#delegation').val(jsonIonMstList[i].delegation);
			if ($jq('#delegation').val() == '') {
				$jq('#delegationn').attr('checked', true);
				$jq("#result1").hide();

			} else {
				$jq('#delegationy').attr('checked', true);
				$jq("#result1").show();

			}

		}
	}
	$jq('#editIncreButton').css("display","none");
	$jq('#editSubmitButton').css("display","block");

}
function setLetterNumber(jsonletter, jsonIonMstList) {
	if (jsonletter != null && jsonletter.length != 0)
		$jq('#letterNumber').val(jsonletter[0]);
	for ( var i = 0; jsonIonMstList != null && jsonIonMstList.length != 0
			&& i < jsonIonMstList.length; i++) {
		if (jsonIonMstList[i].delegation != '')
			$jq('#delegation').val(jsonIonMstList[i].delegation);
	}
}
function getLetterNumberFormatList(type) {
	var requestDetails = {
		"deptNum" : $jq("#deptNum").val(),
		"letterFormatId" : $jq("#letterFormatId").val(),
		"type" : type,
		"param" : 'getLetterNumberFormatList'
	};
	if ($jq('#back').val() == 'IONDetailsDataList') {
		requestDetails = {
			"deptNum" : $jq("#deptNum").val(),
			"letterFormatId" : $jq("#tempLetterFormatId").val(),
			"type" : type,
			"param" : 'getLetterNumberFormatList'
		};

	}

	$jq.ajax( {
		type : "POST",
		url : "letterNumberFormat.htm?" + dispUrl,
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq("#result1").html(html);
			if ($jq('#back').val() == 'IONDetailsDataList') {
				$jq("#letterFormatId").val($jq("#tempLetterFormatId").val());
				$jq('#back').val('');
			}
		}

	});
}
function setLetterNumberFormatList(jsonLetterNumberFormatList) {
	$jq('#letterFormatId').find('option').remove().end();
	$jq("#letterFormatId").append('<option value="">Select</option>');

	for ( var i = 0; jsonLetterNumberFormatList != null
			&& i < jsonLetterNumberFormatList.length; i++) {

		$jq("#letterFormatId")
				.append(
						'<option value=' + jsonLetterNumberFormatList[i].id
								+ '>' + jsonLetterNumberFormatList[i].serialNum
								+ '-' + jsonLetterNumberFormatList[i].shortForm
								+ '</option>');
	}

}
function multiSelectBox() {
	$jq(function() {

		$jq("#MoveRight2,#MoveLeft2").click(function(event) {
			moveToSelectBox(event, 2,0)
		});
		$jq("#MoveRight21,#MoveLeft21").click(function(event) {
			moveToSelectBox(event, 2,1)
		});
		
		$jq("#MoveRight4,#MoveLeft4").click(function(event) {
			moveToSelectBox(event, 4,0)
		});
		$jq("#MoveRight41,#MoveLeft41").click(function(event) {
			moveToSelectBox(event, 4,1)
		});
		$jq("#MoveRight6,#MoveLeft6").click(function(event) {
			moveToSelectBox(event, 6,0)
		});
		$jq("#MoveRight61,#MoveLeft61").click(function(event) {
			moveToSelectBox(event, 6,1)
		});
		$jq("#MoveRight8,#MoveLeft8").click(function(event) {
			moveToSelectBox(event, 8,0)
		});
		$jq("#MoveRight81,#MoveLeft81").click(function(event) {
			moveToSelectBox(event, 8,1)
		});
		$jq("#MoveRight10,#MoveLeft10").click(function(event) {
			moveToSelectBox(event, 10,0)
		});
		$jq("#MoveRight101,#MoveLeft101").click(function(event) {
			moveToSelectBox(event, 10,1)
		});
		$jq("#MoveRight12,#MoveLeft12").click(function(event) {
			moveToSelectBox(event, 12,0)
		});
		$jq("#MoveRight121,#MoveLeft121").click(function(event) {
			moveToSelectBox(event, 12,1)
		});
	});
}
function moveToSelectBox(event, i,j) {
	var id = $jq(event.target).attr("id");
	if(j!=0)
	{
	var selectFrom = id == "MoveRight" + i+j ? "#SelectLeft" + i : "#SelectRight"
			+ i+'1';
	var moveTo = id == "MoveRight" + i+j ? "#SelectRight" + i+'1' : "#SelectLeft" + i;
	var selectedItems = $jq(selectFrom + " :selected").toArray();
	$jq(moveTo).append(selectedItems);
	selectedItems.remove;
	}
	
	
	else
	{
		var selectFrom = id == "MoveRight" + i ? "#SelectLeft" + i : "#SelectRight"
			+ i;
	    var moveTo = id == "MoveRight" + i ? "#SelectRight" + i : "#SelectLeft" + i;
	    var selectedItems = $jq(selectFrom + " :selected").toArray();
		$jq(moveTo).append(selectedItems);
		selectedItems.remove;
	}	
}

function setIonLetterNumberList(jsonIonMstList) {
	$jq('#ionLetterNumberId').find('option').remove().end();
	$jq("#ionLetterNumberId").append('<option value="">Select</option>');

	for ( var i = 0; jsonIonMstList != null && i < jsonIonMstList.length; i++) {

		$jq("#ionLetterNumberId").append(
				'<option value=' + jsonIonMstList[i].id + '>'
						+ jsonIonMstList[i].letterNumber + '</option>');

	}
}
function setDelegation(jsonIonMstList) {
	var content = FCKeditorAPI.GetInstance('content');
	content.SetData('');

	$jq('#contentcounter').val('1500');
	var id = $jq('#ionLetterNumberId').val();
	for ( var i = 0; jsonIonMstList != null && i < jsonIonMstList.length; i++) {
		if (jsonIonMstList[i].delegation != '') {
			content.SetData(jsonIonMstList[i].delegation);
			$jq('#contentcounter').val(1500 - $jq('#content').val().length);
		}

	}
}
function hiding(idVal)
{
	if($jq('#'+idVal+'1').val()=='hide')
	{
	var idv = idVal;
	if(idVal =='enclosers1')
		idv = 'enclosers'
	var subject = FCKeditorAPI.GetInstance(idv);
	var subjectData = subject.GetData(true);
	$jq('#'+idVal+'2').hide();
	$jq('#'+idVal+'3').show();
	var tempVal = document.getElementById(idVal+'3').innerHTML;
	document.getElementById(idVal+'3').innerHTML=subjectData;
	
	document.getElementById(idVal+'1').innerHTML='(edit)';
	$jq('#'+idVal+'1').val('show');
	if($jq.trim(tempVal) == subjectData)
	{
	}
	else
	manageTempIONMaster('save');
	}
	else
	{
		$jq('#'+idVal+'2').show();
		$jq('#'+idVal+'3').hide();
		
		document.getElementById(idVal+'1').innerHTML='(save)';
		$jq('#'+idVal+'1').val('hide');
	}
	
	
	
}
function hidingFile(idVal)
{
	if($jq('#'+idVal+'1').val()=='hide')
	{
//	var subject = FCKeditorAPI.GetInstance(idVal);
//	var subjectData = subject.GetData(true);
	$jq('#'+idVal+'2').hide();
	$jq('#'+idVal+'3').show();
//	var tempVal = document.getElementById(idVal+'3').innerHTML;
//	document.getElementById(idVal+'3').innerHTML=subjectData;
//	
	document.getElementById(idVal+'1').innerHTML='(edit)';
	$jq('#'+idVal+'1').val('show');
	getEnclosersSaveList();
//	if($jq.trim(tempVal) == subjectData)
//	{
//	}
//	else
//	manageTempIONMaster('save');
	}
	else
	{
		$jq('#'+idVal+'2').show();
		$jq('#'+idVal+'3').hide();
		
		document.getElementById(idVal+'1').innerHTML='(save)';
		$jq('#'+idVal+'1').val('hide');
	}
	
	
	
}
function uploadFile(idval)
{
	var status = true;
	var errorMessage = "";
	if ((document.getElementById('enclosureFileNames') != null && $jq('#enclosureFileNames').val() == '')) {
		errorMessage += "Enter File Name.\n";
		status = false;
		$jq("#enclosureFileNames").focus();
	}
	if ((document.getElementById('files') != null && $jq('#files').val() == '')) {
		errorMessage += "Upload File.\n";
		status = false;
		$jq("#files").focus();
	}
	
	if(status)
	{
	$jq('#param').val('uploadEncloserFile');
	$jq('#type').val('uploadEncloserFile');
	$jq('#enclosureFileName').val($jq('#enclosureFileNames').val());
	$jq.ajaxFileUpload( {
		url : "letterNumberFormat.htm?"
				+ $jq('#letterNumberFormat').serialize(),
		secureuri : false,
		fileElementId : 'files',
		success : function(data,status) {
				
				var requestDetails = {
				"id" : $jq('#id').val(),
				"type" : 'getEncloserFileList',
				"param" : 'getEncloserFileList'
									};
					$jq.ajax( {
							type : "POST",
							url : "letterNumberFormat.htm?",
							data : requestDetails,
							cache : false,
							success : function(html) {
									$jq("#encloserDiv").html(html);
									
//									 requestDetails = {
//											"id" : $jq('#id').val(),
//											"type" : 'getEnclosedFileList',
//											"param" : 'getEncloserFileList'
//																};
//												$jq.ajax( {
//														type : "POST",
//														url : "letterNumberFormat.htm?",
//														data : requestDetails,
//														cache : false,
//														success : function(html) {
//																$jq("#enclosers3").html(html);
//																
//																}
//												});
											
				
									}

					});
					alert('File Uploaded Successfully');
					
					
		
		},
	error : function(data, status, e) {
		alert('File Upload failed');
	}
	});
	
	}
	else
	{
	 alert(errorMessage);	
	}
}
function getEnclosersSaveList()
{
//	var requestDetails = {
//			"id" : $jq('#id').val(),
//			"type" : 'getEnclosedFileList',
//			"param" : 'getEncloserFileList'
//								};
//				$jq.ajax( {
//						type : "POST",
//						url : "letterNumberFormat.htm?",
//						data : requestDetails,
//						cache : false,
//						success : function(html) {
//								$jq("#enclosers3").html(html);
//								}
//				});
}
function insertFileRow(idval)
{
	
}
function clearIONMaster(ckeckVal) {

	$jq('#circulateVisibleId').hide();
	if($jq('#circulateVisible').val()==1) $jq('#circulateVisibleId').show();
	$jq('#subjectcounter').val('1500');
	$jq('#referencecounter').val('1500');
	$jq('#contentcounter').val('1500');
	$jq('#encloserscounter').val('1500');
	$jq('#departmentOrRoleVal').val('1');
	$jq('#departmentChk').attr('checked', true);
	$jq('#designationChk').attr('checked', false);
	$jq('#roleChk').attr('checked', false);
	$jq('#sfidNameChk').attr('checked', false);
	$jq('#orgChk').attr('checked', false);
	$jq('#roleHirarchyChk').attr('checked', false);
	$jq('#publicChk').attr('checked', false);
	
	if($jq('#publicStatus').val()=='Y')$jq('#publicChk').attr('checked', true);
	clearIONMasterOnLoad();
	enableChk();
	
	$jq('#subject3').show();
	document.getElementById('subject3').innerHTML=$jq('#subject').val();
	document.getElementById('subject1').innerHTML='(edit)';
	$jq('#subject1').val('show');
	
	$jq('#reference3').show();
	document.getElementById('reference3').innerHTML=$jq('#reference').val();
	document.getElementById('reference1').innerHTML='(edit)';
	$jq('#reference1').val('show');
	
	$jq('#content3').show();
	document.getElementById('content3').innerHTML=$jq('#content').val();
	document.getElementById('content1').innerHTML='(edit)';
	$jq('#content1').val('show');
	
	$jq('#enclosers13').show();
	document.getElementById('enclosers13').innerHTML=$jq('#enclosers').val();
		document.getElementById('enclosers11').innerHTML='(edit)';
		$jq('#enclosers11').val('show');
	
	$jq('#enclosers3').show();
//for copy	document.getElementById('enclosers3').innerHTML=$jq('#enclosers').val();
	document.getElementById('enclosers1').innerHTML='(edit)';
	$jq('#enclosers1').val('show');
	
	var oFCKeditor = new FCKeditor('content');
	oFCKeditor.ReplaceTextarea();
	oFCKeditor = new FCKeditor('subject');
	oFCKeditor.ReplaceTextarea();
	oFCKeditor = new FCKeditor('reference');
	oFCKeditor.ReplaceTextarea();
	oFCKeditor = new FCKeditor('enclosers');
	oFCKeditor.ReplaceTextarea();
	
	$jq('#subject2').hide();
	$jq('#reference2').hide();
	$jq('#content2').hide();
	$jq('#enclosers2').hide();
	$jq('#enclosers12').hide();

//	 var content = FCKeditorAPI.GetInstance('content');
//	 var contentVal = content.GetData(true);
//	 $jq('#subjectLable').hide();
	 
	// var subject = FCKeditorAPI.GetInstance('subject');
	// subject.SetData($jq('#subject').val());
	// var reference = FCKeditorAPI.GetInstance('reference');
	// reference.SetData($jq('#reference').val());
	// var enclosers = FCKeditorAPI.GetInstance('enclosers');
	// enclosers.SetData($jq('#enclosersVal').val());
	//	
	
	if(document.getElementById('ionInitiatedRoleId').options.length<2)
	{
		$jq('#initiatedRoleIdName').hide();
		$jq('#initiatedRoleIdVal').hide();
	}
	if(document.getElementById('ionForwardRoleId').options.length<2)
	{
		$jq('#forwardRoleIdName').hide();
		$jq('#forwardRoleIdVal').hide();
	}
	if(document.getElementById('ionApprovedRoleId').options.length<2)
	{
		$jq('#approvedRoleIdName').hide();
		$jq('#approvedRoleIdVal').hide();
	}
	if($jq('#circulationStatus').val()=='5')
	{
		$jq('#draft').hide();
		$jq('#circulated').show();
	}
	else if($jq('#circulationStatus').val()!='5')
	{
		$jq('#draft').show();
		$jq('#circulated').hide();
	}
		

}
function clearIONMasterOnLoad()
{
	if ((document.getElementById('SelectRight2') != null
			&& document.getElementById('SelectRight2').length != 0) || (document.getElementById('SelectRight21') != null
					&& document.getElementById('SelectRight21').length != 0)) {
		$jq('#departmentChk').attr('checked', true);
		
	}
	else
	{
		$jq('#departmentChk').attr('checked', false);
	}
	if ((document.getElementById('SelectRight4') != null
			&& document.getElementById('SelectRight4').length != 0) || (document.getElementById('SelectRight41') != null
					&& document.getElementById('SelectRight41').length != 0)) {
		$jq('#designationChk').attr('checked', true);
	}
	else
	{
		$jq('#designationChk').attr('checked', false);
	}
	if ((document.getElementById('SelectRight6') != null
			&& document.getElementById('SelectRight6').length != 0) || (document.getElementById('SelectRight61') != null
					&& document.getElementById('SelectRight61').length != 0)) {
		$jq('#roleChk').attr('checked', true);
	}
	else
	{
		$jq('#roleChk').attr('checked', false);
	}
	if ((document.getElementById('SelectRight10') != null
			&& document.getElementById('SelectRight10').length != 0)  || (document.getElementById('SelectRight101') != null
					&& document.getElementById('SelectRight101').length != 0)){
		$jq('#roleHirarchyChk').attr('checked', true);
	}
	else
	{
		$jq('#roleHirarchyChk').attr('checked', false);
	}
	if ((document.getElementById('SelectRight8') != null
			&& document.getElementById('SelectRight8').length != 0) || (document.getElementById('SelectRight81') != null
					&& document.getElementById('SelectRight81').length != 0)){
		$jq('#sfidChk').attr('checked', true);
	}
	else
	{
		$jq('#sfidChk').attr('checked', false);
	}
	if ((document.getElementById('SelectRight12') != null
			&& document.getElementById('SelectRight12').length != 0) || (document.getElementById('SelectRight121') != null
					&& document.getElementById('SelectRight121').length != 0)) {
		$jq('#orgChk').attr('checked', true);
	}
	else
	{
		$jq('#orgChk').attr('checked', false);
	}
}
function manageTempIONMaster(saveOrCirculate) {
	var errorMessage = "";
	var status = true;
	$jq('#departmentId').val('');
	$jq('#designationId').val('');
	$jq('#orgRoleId').val('');
	$jq('#sfidNameId').val('');
	$jq('#roleHirarchyId').val('');

	var subject = FCKeditorAPI.GetInstance('subject');
	var subjectData = subject.GetData(true)
	
	var content = FCKeditorAPI.GetInstance('content');
	var contentData = content.GetData(true)
	
	var reference = FCKeditorAPI.GetInstance('reference');
	var referenceData = reference.GetData(true)

	var enclosers = FCKeditorAPI.GetInstance('enclosers');
	var enclosersData = enclosers.GetData(true)

	

	
		if (document.getElementById('SelectRight2') != null
				&& document.getElementById('SelectRight2').length == 0) {

		} else {
			for ( var i = 0; document.getElementById('SelectRight2') != null
					&& i < document.getElementById('SelectRight2').options.length; i++) {
				document.getElementById('SelectRight2').options[i].selected = true;
				$jq('#departmentId')
						.val(
								$jq('#departmentId').val()
										+ document
												.getElementById('SelectRight2').options[i].value
										+ ",");
			}
			$jq('#departmentId').val().substring(0,
					$jq('#departmentId').val().length - 1);
		}

		if (document.getElementById('SelectRight4') != null
				&& document.getElementById('SelectRight4').length == 0) {

		} else {
			for ( var i = 0; document.getElementById('SelectRight4') != null
					&& i < document.getElementById('SelectRight4').options.length; i++) {
				document.getElementById('SelectRight4').options[i].selected = true;
				$jq('#designationId')
						.val(
								$jq('#designationId').val()
										+ document
												.getElementById('SelectRight4').options[i].value
										+ ",");
			}
			$jq('#designationId').val().substring(0,
					$jq('#designationId').val().length - 1);
		}

		if (document.getElementById('SelectRight6') != null
				&& document.getElementById('SelectRight6').length == 0) {

		} else {
			for ( var i = 0; document.getElementById('SelectRight6') != null
					&& i < document.getElementById('SelectRight6').options.length; i++) {
				document.getElementById('SelectRight6').options[i].selected = true;
				$jq('#orgRoleId')
						.val(
								$jq('#orgRoleId').val()
										+ document
												.getElementById('SelectRight6').options[i].value
										+ ",");
			}
			$jq('#orgRoleId').val().substring(0,
					$jq('#orgRoleId').val().length - 1);
		}

		if (document.getElementById('SelectRight8') != null
				&& document.getElementById('SelectRight8').length == 0) {

		} else {
			for ( var i = 0; document.getElementById('SelectRight8') != null
					&& i < document.getElementById('SelectRight8').options.length; i++) {
				document.getElementById('SelectRight8').options[i].selected = true;
				$jq('#sfidNameId')
						.val(
								$jq('#sfidNameId').val()
										+ document
												.getElementById('SelectRight8').options[i].value
										+ ",");
			}
			$jq('#sfidNameId').val().substring(0,
					$jq('#sfidNameId').val().length - 1);
		}
		if (document.getElementById('SelectRight10') != null
				&& document.getElementById('SelectRight10').length == 0) {

		} else {
			for ( var i = 0; document.getElementById('SelectRight10') != null
					&& i < document.getElementById('SelectRight10').options.length; i++) {
				document.getElementById('SelectRight10').options[i].selected = true;
				$jq('#roleHirarchyId')
						.val(
								$jq('#roleHirarchyId').val()
										+ document
												.getElementById('SelectRight10').options[i].value
										+ ",");
			}
			$jq('#roleHirarchyId').val().substring(0,
					$jq('#roleHirarchyId').val().length - 1);
		}
		
//copy start
		
		if (document.getElementById('SelectRight21') != null
				&& document.getElementById('SelectRight21').length == 0) {

		} else {
			for ( var i = 0; document.getElementById('SelectRight21') != null
					&& i < document.getElementById('SelectRight21').options.length; i++) {
				document.getElementById('SelectRight21').options[i].selected = true;
				$jq('#departmentIdCopy')
						.val(
								$jq('#departmentIdCopy').val()
										+ document
												.getElementById('SelectRight21').options[i].value
										+ ",");
			}
			$jq('#departmentIdCopy').val().substring(0,
					$jq('#departmentIdCopy').val().length - 1);
		}

		if (document.getElementById('SelectRight41') != null
				&& document.getElementById('SelectRight41').length == 0) {

		} else {
			for ( var i = 0; document.getElementById('SelectRight41') != null
					&& i < document.getElementById('SelectRight41').options.length; i++) {
				document.getElementById('SelectRight41').options[i].selected = true;
				$jq('#designationIdCopy')
						.val(
								$jq('#designationIdCopy').val()
										+ document
												.getElementById('SelectRight41').options[i].value
										+ ",");
			}
			$jq('#designationIdCopy').val().substring(0,
					$jq('#designationIdCopy').val().length - 1);
		}

		if (document.getElementById('SelectRight61') != null
				&& document.getElementById('SelectRight61').length == 0) {

		} else {
			for ( var i = 0; document.getElementById('SelectRight61') != null
					&& i < document.getElementById('SelectRight61').options.length; i++) {
				document.getElementById('SelectRight61').options[i].selected = true;
				$jq('#orgRoleIdCopy')
						.val(
								$jq('#orgRoleIdCopy').val()
										+ document
												.getElementById('SelectRight61').options[i].value
										+ ",");
			}
			$jq('#orgRoleIdCopy').val().substring(0,
					$jq('#orgRoleIdCopy').val().length - 1);
		}

		if (document.getElementById('SelectRight81') != null
				&& document.getElementById('SelectRight81').length == 0) {

		} else {
			for ( var i = 0; document.getElementById('SelectRight81') != null
					&& i < document.getElementById('SelectRight81').options.length; i++) {
				document.getElementById('SelectRight81').options[i].selected = true;
				$jq('#sfidNameIdCopy')
						.val(
								$jq('#sfidNameIdCopy').val()
										+ document
												.getElementById('SelectRight81').options[i].value
										+ ",");
			}
			$jq('#sfidNameIdCopy').val().substring(0,
					$jq('#sfidNameIdCopy').val().length - 1);
		}
		if (document.getElementById('SelectRight101') != null
				&& document.getElementById('SelectRight101').length == 0) {

		} else {
			for ( var i = 0; document.getElementById('SelectRight101') != null
					&& i < document.getElementById('SelectRight101').options.length; i++) {
				document.getElementById('SelectRight101').options[i].selected = true;
				$jq('#roleHirarchyIdCopy')
						.val(
								$jq('#roleHirarchyIdCopy').val()
										+ document
												.getElementById('SelectRight101').options[i].value
										+ ",");
			}
			$jq('#roleHirarchyIdCopy').val().substring(0,
					$jq('#roleHirarchyIdCopy').val().length - 1);
		}
//copy end		
		
		
//		if (saveOrCirculate == 'save')
//			$jq('#circulationStatus').val('1');
//		else
//			$jq('#circulationStatus').val('5');
		if($jq('#circulationStatus').val() =='5')
			$jq('#circulationStatus').val('5');
		else
			$jq('#circulationStatus').val('1');
		var ionInitiatedRoleId = '';
		if(document.getElementById('ionInitiatedRoleId') != null)
			ionInitiatedRoleId = $jq("#ionInitiatedRoleId").val();
		
		var requestDetails = {
			"id" : $jq("#id").val(),
			"deptNum" : $jq("#deptNum").val(),
			"letterFormatId" : $jq("#letterFormatId").val(),
			"ionLetterNumberId" : $jq("#ionLetterNumberId").val(),
			"ionInitiatedSfid" : $jq("#ionInitiatedSfid").val(),
			"ionInitiatedRoleId" : ionInitiatedRoleId,
			"subject" : subjectData,
			"reference" : referenceData,
			"content" : contentData,
			"enclosers" : enclosersData,
			"departmentId" : $jq('#departmentId').val(),
			"designationId" : $jq('#designationId').val(),
			"orgRoleId" : $jq('#orgRoleId').val(),
			"roleHirarchyId" : $jq('#roleHirarchyId').val(),
			"sfidNameId" : $jq('#sfidNameId').val(),
			"circulationStatus" : $jq('#circulationStatus').val(),
			"param" : 'manageIONMaster'
		};
		$jq.ajax( {
			type : "POST",
			url : "letterNumberFormat.htm?" + dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#result2").html(html);
				

			}

		});

	
}
function showFile(fileId)
{
	
		window
				.open(
						"./letterNumberFormat.htm?param=showFile&returnValue=report&type=course&fileId="+fileId,
						'preview',
						'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');

	
}
function removeFile(fileId)
{
	var requestDetails = {
			"id" : $jq("#id").val(),
			"fileId" : fileId,
			"param" : 'removeFile'

		};
		$jq.ajax( {
			type : "POST",
			url : "letterNumberFormat.htm?",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#result2").html(html);
				if ($jq('.failure').is(':visible')) {
					$jq("#result2").html('');

					$jq('#param').val('getEncloserFileList');
					$jq('#type').val('getEncloserFileList');
					var requestDetails = {
					"id" : $jq('#id').val(),
					"type" : 'getEncloserFileList',
					"param" : 'getEncloserFileList'
										};
						$jq.ajax( {
								type : "POST",
								url : "letterNumberFormat.htm?",
								data : requestDetails,
								cache : false,
								success : function(html) { 
								$jq("#encloserDiv").html(html);
								
//										$jq("#enclosers4").html(html);
//										alert('File Removed Successfully');
//										var requestDetails = {
//												"id" : $jq('#id').val(),
//												"type" : 'getEnclosedFileList',
//												"param" : 'getEncloserFileList'
//																	};
//													$jq.ajax( {
//															type : "POST",
//															url : "letterNumberFormat.htm?",
//															data : requestDetails,
//															cache : false,
//															success : function(html) {
//																	$jq("#enclosers3").html(html);
//																	}
//													});
				
					
										}

						});
			
			

			}
				else{	alert('File Not Removed');}
		}

		});

}
function manageIONMaster(saveOrCirculate) {
	var errorMessage = "";
	var status = true;
	$jq('#departmentId').val('');
	$jq('#designationId').val('');
	$jq('#orgRoleId').val('');
	$jq('#sfidNameId').val('');
	$jq('#roleHirarchyId').val('');
	$jq('#organizationId').val('');
	
	$jq('#departmentIdCopy').val('');
	$jq('#designationIdCopy').val('');
	$jq('#orgRoleIdCopy').val('');
	$jq('#sfidNameIdCopy').val('');
	$jq('#roleHirarchyIdCopy').val('');
	$jq('#organizationIdCopy').val('');

	var subject = FCKeditorAPI.GetInstance('subject');
	var subjectData = subject.GetData(true)
	if (subject.GetData(true) == "") {
		errorMessage += "Enter Subject.\n";
		status = false;
		$jq("#subject").focus();
	}
	//alerts
	if(($jq('#ionApprovedSfid').val()!='') && $jq('#ionForwardSfid').val()==''){
		errorMessage += " Please Enter Forward By.\n";
		status = false;
		$jq("#ionForwardSfid").focus();
	}
	if($jq('#ionForwardSfid').val()!='' && $jq('#ionInitiatedSfid').val()==''){
		errorMessage += "Please Enter Initiated  By.\n";
		status = false;
		$jq("#ionInitiatedSfid").focus();
	}
	var content = FCKeditorAPI.GetInstance('content');
	var contentData = content.GetData(true)
	if (contentData == "") {
		errorMessage += "Enter Content.\n";
		status = false;
		$jq("#content").focus();
	}
	var reference = FCKeditorAPI.GetInstance('reference');
	var referenceData = reference.GetData(true)

	var enclosers = FCKeditorAPI.GetInstance('enclosers');
	var enclosersData = enclosers.GetData(true)

	if (((document.getElementById('SelectRight2') != null && document
			.getElementById('SelectRight2').length == 0)
			&& (document.getElementById('SelectRight4') != null && document
					.getElementById('SelectRight4').length == 0)
			&& (document.getElementById('SelectRight6') != null && document
					.getElementById('SelectRight6').length == 0)
			&& (document.getElementById('SelectRight8') != null && document
					.getElementById('SelectRight8').length == 0)
			&& (document.getElementById('SelectRight10') != null && document
					.getElementById('SelectRight10').length == 0)
					&& (document.getElementById('SelectRight12') != null && document
							.getElementById('SelectRight12').length == 0)) || (!$jq('#departmentChk').is(':checked') && !$jq('#designationChk').is(':checked') && !$jq('#roleChk').is(':checked') && !$jq('#roleHirarchyChk').is(':checked') && !$jq('#sfidChk').is(':checked') && !$jq('#orgChk').is(':checked'))) {
		errorMessage += "Select Departments or Designations or Role or Sfid or Role Hirarchy or Organizations to send the Circulation.\n";
		status = false;

	}

	if (status) {
		if (document.getElementById('SelectRight2') != null
				&& document.getElementById('SelectRight2').length == 0) {

		} else {
			for ( var i = 0; document.getElementById('SelectRight2') != null
					&& i < document.getElementById('SelectRight2').options.length; i++) {
				document.getElementById('SelectRight2').options[i].selected = true;
				$jq('#departmentId')
						.val(
								$jq('#departmentId').val()
										+ document
												.getElementById('SelectRight2').options[i].value
										+ ",");
			}
			$jq('#departmentId').val().substring(0,
					$jq('#departmentId').val().length - 1);
		}

		if (document.getElementById('SelectRight4') != null
				&& document.getElementById('SelectRight4').length == 0) {

		} else {
			for ( var i = 0; document.getElementById('SelectRight4') != null
					&& i < document.getElementById('SelectRight4').options.length; i++) {
				document.getElementById('SelectRight4').options[i].selected = true;
				$jq('#designationId')
						.val(
								$jq('#designationId').val()
										+ document
												.getElementById('SelectRight4').options[i].value
										+ ",");
			}
			$jq('#designationId').val().substring(0,
					$jq('#designationId').val().length - 1);
		}

		if (document.getElementById('SelectRight6') != null
				&& document.getElementById('SelectRight6').length == 0) {

		} else {
			for ( var i = 0; document.getElementById('SelectRight6') != null
					&& i < document.getElementById('SelectRight6').options.length; i++) {
				document.getElementById('SelectRight6').options[i].selected = true;
				$jq('#orgRoleId')
						.val(
								$jq('#orgRoleId').val()
										+ document
												.getElementById('SelectRight6').options[i].value
										+ ",");
			}
			$jq('#orgRoleId').val().substring(0,
					$jq('#orgRoleId').val().length - 1);
		}

		if (document.getElementById('SelectRight8') != null
				&& document.getElementById('SelectRight8').length == 0) {

		} else {
			for ( var i = 0; document.getElementById('SelectRight8') != null
					&& i < document.getElementById('SelectRight8').options.length; i++) {
				document.getElementById('SelectRight8').options[i].selected = true;
				$jq('#sfidNameId')
						.val(
								$jq('#sfidNameId').val()
										+ document
												.getElementById('SelectRight8').options[i].value
										+ ",");
			}
			$jq('#sfidNameId').val().substring(0,
					$jq('#sfidNameId').val().length - 1);
		}
		if (document.getElementById('SelectRight10') != null
				&& document.getElementById('SelectRight10').length == 0) {

		} else {
			for ( var i = 0; document.getElementById('SelectRight10') != null
					&& i < document.getElementById('SelectRight10').options.length; i++) {
				document.getElementById('SelectRight10').options[i].selected = true;
				$jq('#roleHirarchyId')
						.val(
								$jq('#roleHirarchyId').val()
										+ document
												.getElementById('SelectRight10').options[i].value
										+ ",");
			}
			$jq('#roleHirarchyId').val().substring(0,
					$jq('#roleHirarchyId').val().length - 1);
		}
		if (document.getElementById('SelectRight12') != null
				&& document.getElementById('SelectRight12').length == 0) {

		} else {
			for ( var i = 0; document.getElementById('SelectRight12') != null
					&& i < document.getElementById('SelectRight12').options.length; i++) {
				document.getElementById('SelectRight12').options[i].selected = true;
				$jq('#organizationId')
						.val(
								$jq('#organizationId').val()
										+ document
												.getElementById('SelectRight12').options[i].value
										+ ",");
			}
			$jq('#organizationId').val().substring(0,
					$jq('#organizationId').val().length - 1);
		}
		
//copy start
		
		if (document.getElementById('SelectRight21') != null
				&& document.getElementById('SelectRight21').length == 0) {

		} else {
			for ( var i = 0; document.getElementById('SelectRight21') != null
					&& i < document.getElementById('SelectRight21').options.length; i++) {
				document.getElementById('SelectRight21').options[i].selected = true;
				$jq('#departmentIdCopy')
						.val(
								$jq('#departmentIdCopy').val()
										+ document
												.getElementById('SelectRight21').options[i].value
										+ ",");
			}
			$jq('#departmentIdCopy').val().substring(0,
					$jq('#departmentIdCopy').val().length - 1);
		}

		if (document.getElementById('SelectRight41') != null
				&& document.getElementById('SelectRight41').length == 0) {

		} else {
			for ( var i = 0; document.getElementById('SelectRight41') != null
					&& i < document.getElementById('SelectRight41').options.length; i++) {
				document.getElementById('SelectRight41').options[i].selected = true;
				$jq('#designationIdCopy')
						.val(
								$jq('#designationIdCopy').val()
										+ document
												.getElementById('SelectRight41').options[i].value
										+ ",");
			}
			$jq('#designationIdCopy').val().substring(0,
					$jq('#designationIdCopy').val().length - 1);
		}

		if (document.getElementById('SelectRight61') != null
				&& document.getElementById('SelectRight61').length == 0) {

		} else {
			for ( var i = 0; document.getElementById('SelectRight61') != null
					&& i < document.getElementById('SelectRight61').options.length; i++) {
				document.getElementById('SelectRight61').options[i].selected = true;
				$jq('#orgRoleIdCopy')
						.val(
								$jq('#orgRoleIdCopy').val()
										+ document
												.getElementById('SelectRight61').options[i].value
										+ ",");
			}
			$jq('#orgRoleIdCopy').val().substring(0,
					$jq('#orgRoleIdCopy').val().length - 1);
		}

		if (document.getElementById('SelectRight81') != null
				&& document.getElementById('SelectRight81').length == 0) {

		} else {
			for ( var i = 0; document.getElementById('SelectRight81') != null
					&& i < document.getElementById('SelectRight81').options.length; i++) {
				document.getElementById('SelectRight81').options[i].selected = true;
				$jq('#sfidNameIdCopy')
						.val(
								$jq('#sfidNameIdCopy').val()
										+ document
												.getElementById('SelectRight81').options[i].value
										+ ",");
			}
			$jq('#sfidNameIdCopy').val().substring(0,
					$jq('#sfidNameIdCopy').val().length - 1);
		}
		if (document.getElementById('SelectRight101') != null
				&& document.getElementById('SelectRight101').length == 0) {

		} else {
			for ( var i = 0; document.getElementById('SelectRight101') != null
					&& i < document.getElementById('SelectRight101').options.length; i++) {
				document.getElementById('SelectRight101').options[i].selected = true;
				$jq('#roleHirarchyIdCopy')
						.val(
								$jq('#roleHirarchyIdCopy').val()
										+ document
												.getElementById('SelectRight101').options[i].value
										+ ",");
			}
			$jq('#roleHirarchyIdCopy').val().substring(0,
					$jq('#roleHirarchyIdCopy').val().length - 1);
		}
		if (document.getElementById('SelectRight121') != null
				&& document.getElementById('SelectRight121').length == 0) {

		} else {
			for ( var i = 0; document.getElementById('SelectRight121') != null
					&& i < document.getElementById('SelectRight121').options.length; i++) {
				document.getElementById('SelectRight121').options[i].selected = true;
				$jq('#organizationIdCopy')
						.val(
								$jq('#organizationIdCopy').val()
										+ document
												.getElementById('SelectRight121').options[i].value
										+ ",");
			}
			$jq('#organizationIdCopy').val().substring(0,
					$jq('#organizationIdCopy').val().length - 1);
		}
//copy end		
		
		if (saveOrCirculate == 'save' && $jq('#circulationStatus').val() != '5')
			$jq('#circulationStatus').val('1');
		else
			$jq('#circulationStatus').val('5');
		
		var ionInitiatedRoleId = '';
		if(document.getElementById('ionInitiatedRoleId') != null)
			ionInitiatedRoleId = $jq("#ionInitiatedRoleId").val();
		
		var ionForwardRoleId = '';
		if(document.getElementById('ionForwardRoleId') != null)
			ionForwardRoleId = $jq("#ionForwardRoleId").val();
		
		var ionApprovedRoleId = '';
		if(document.getElementById('ionApprovedRoleId') != null)
			ionApprovedRoleId = $jq("#ionApprovedRoleId").val();
		
		if($jq('#publicChk').is(':checked',true))
		$jq('#publicStatus').val('Y');
		else
			$jq('#publicStatus').val('N');
		
		var requestDetails = {
			"id" : $jq("#id").val(),
			"deptNum" : $jq("#deptNum").val(),
			"letterFormatId" : $jq("#letterFormatId").val(),
			"ionLetterNumberId" : $jq("#ionLetterNumberId").val(),
			"ionInitiatedSfid" : $jq("#ionInitiatedSfid").val(),
			"ionInitiatedRoleId" : ionInitiatedRoleId,
			"ionForwardSfid" : $jq("#ionForwardSfid").val(),
			"ionForwardRoleId" : ionForwardRoleId,
			"ionApprovedSfid" : $jq("#ionApprovedSfid").val(),
			"ionApprovedRoleId" : ionApprovedRoleId,
			"subject" : subjectData,
			"reference" : referenceData,
			"content" : contentData,
			"enclosers" : enclosersData,
			"departmentId" : $jq('#departmentId').val(),
			"designationId" : $jq('#designationId').val(),
			"orgRoleId" : $jq('#orgRoleId').val(),
			"roleHirarchyId" : $jq('#roleHirarchyId').val(),
			"orgId" : $jq('#roleHirarchyId').val(),
			"sfidNameId" : $jq('#sfidNameId').val(),
			"organizationId" : $jq('#organizationId').val(),
			"departmentIdCopy" : $jq('#departmentIdCopy').val(),
			"designationIdCopy" : $jq('#designationIdCopy').val(),
			"orgRoleIdCopy" : $jq('#orgRoleIdCopy').val(),
			"roleHirarchyIdCopy" : $jq('#roleHirarchyIdCopy').val(),
			"sfidNameIdCopy" : $jq('#sfidNameIdCopy').val(),
			"organizationIdCopy" : $jq('#organizationIdCopy').val(),
			"circulationStatus" : $jq('#circulationStatus').val(),
			"publicStatus" : $jq('#publicStatus').val(),
			"param" : 'manageIONMaster'
		};
		$jq.ajax( {
			type : "POST",
			url : "letterNumberFormat.htm?" + dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#result2").html(html);
				if ($jq('.success').is(':visible')) {
					if($jq('#circulationStatus').val()=='5')
					{
						$jq('#draft').hide();
						$jq('#circulated').show();
					}
					else if($jq('#circulationStatus').val()!='5')
					{
						$jq('#draft').show();
						$jq('#circulated').hide();
					}
					if ($jq('.success').is(':visible') && saveOrCirculate != 'save') {
						goBackToIONDetails();
					}
				}

			}

		});

	} else {
		alert(errorMessage);
	}
}
// function getDepartmentListForRole(jsonDeptRoleBasedList)
// {
// $jq('#SelectLeft2').find('option').remove().end();
// $jq('#SelectRight2').find('option').remove().end();
//	
//	
// for ( var i = 0; jsonDeptRoleBasedList != null
// && i < jsonDeptRoleBasedList.length; i++) {
//		
// $jq("#SelectLeft2").append(
// '<option value=' + jsonDeptRoleBasedList[i].id + '>'
// + jsonDeptRoleBasedList[i].name + '</option>');
//		
// }
// }
// function getDepartmentListForDepartment(jsonDeptList)
// {
// $jq('#SelectLeft2').find('option').remove().end();
// $jq('#SelectRight2').find('option').remove().end();
//	
//	
// for ( var i = 0; jsonDeptList != null
// && i < jsonDeptList.length; i++) {
//		
// $jq("#SelectLeft2").append(
// '<option value=' + jsonDeptList[i].id + '>'
// + jsonDeptList[i].deptName + '</option>');
//		
// }
// }

function enableChk() {
	if ($jq('#departmentChk').is(':checked')) {
		$jq('#departmentSub').show();
		$jq('#departmentVal').show();

	} else {
		$jq('#SelectRight2').find('option').remove().end();
		$jq('#SelectRight21').find('option').remove().end();
		$jq('#departmentSub').hide();
		$jq('#departmentVal').hide();

	}

	if ($jq('#designationChk').is(':checked')) {
		$jq('#designationSub').show();
		$jq('#designationVal').show();

	} else {
		$jq('#SelectRight4').find('option').remove().end();
		$jq('#SelectRight41').find('option').remove().end();
		$jq('#designationSub').hide();
		$jq('#designationVal').hide();

	}
	if ($jq('#roleChk').is(':checked')) {
		$jq('#roleSub').show();
		$jq('#roleVal').show();

	} else {
		$jq('#SelectRight6').find('option').remove().end();
		$jq('#SelectRight61').find('option').remove().end();
		$jq('#roleSub').hide();
		$jq('#roleVal').hide();

	}
	if ($jq('#sfidChk').is(':checked')) {
		$jq('#sfidSub').show();
		$jq('#sfidVal').show();

	} else {
		$jq('#SelectRight8').find('option').remove().end();
		$jq('#SelectRight81').find('option').remove().end();
		$jq('#sfidSub').hide();
		$jq('#sfidVal').hide();

	}
	if ($jq('#roleHirarchyChk').is(':checked')) {
		$jq('#roleHirarchySub').show();
		$jq('#roleHirarchyVal').show();

	} else {
		$jq('#SelectRight10').find('option').remove().end();
		$jq('#SelectRight101').find('option').remove().end();
		$jq('#roleHirarchySub').hide();
		$jq('#roleHirarchyVal').hide();

	}
	if ($jq('#orgChk').is(':checked')) {
		$jq('#orgSub').show();
		$jq('#orgVal').show();

	} else {
		$jq('#SelectRight12').find('option').remove().end();
		$jq('#SelectRight121').find('option').remove().end();
		$jq('#orgSub').hide();
		$jq('#orgVal').hide();

	}
	if ($jq('#publicChk').is(':checked')) {
		setSfidAsPublic();

	} else {
		
	}

}
function setSfidAsPublic()
{
		$jq('#sfidChk').attr('checked',true);
		$jq('#sfidSub').show();
		$jq('#sfidVal').show();
		for ( var i = 0; document.getElementById('SelectLeft8') != null	&& i < document.getElementById('SelectLeft8').options.length; i++) {
			if( document.getElementById('SelectLeft8').options[i].value.substring(0,2)=='SF')
			document.getElementById('SelectLeft8').options[i].selected = true;
		}
		$jq('#MoveRight8').click();
}
function getIONDispatchList(id,reportType)
{
	if(reportType =='doc')
	{
		window
		.open(
				"./report.htm?param=ionDispatchListinDOC&returnValue=report&ionId="
						+ id,
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	}
	if(reportType =='pdf')
	{
		window
		.open(
				"./report.htm?param=ionDispatchListinPDF&returnValue=report&ionId="
						+ id,
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	}
	
}
function getPreview(id,reportType) {

	if(reportType =='doc')
	{
	window
			.open(
					"./report.htm?param=previewIONinDOC&returnValue=report&ionId="
							+ id,
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	}
	if(reportType =='pdf')
	{
	window
			.open(
					"./report.htm?param=previewIONinPDF&returnValue=report&ionId="
							+ id,
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	}
	if(reportType =='preview')
	{
		var subject = FCKeditorAPI.GetInstance('subject');
		var subjectData = subject.GetData(true);
		
		var content = FCKeditorAPI.GetInstance('content');
		var contentData = content.GetData(true);
		
		var reference = FCKeditorAPI.GetInstance('reference');
		var referenceData = reference.GetData(true);

		var enclosers = FCKeditorAPI.GetInstance('enclosers');
		var enclosersData = enclosers.GetData(true);
		
		var department = '';
		var designation = '';
		var role = '';
		var sfid = '';
		var roleHirarchy = '';
		id=$jq('#id').val();
		
		if (document.getElementById('SelectRight2') != null
				&& document.getElementById('SelectRight2').length == 0) {

		} else {
			for ( var i = 0; document.getElementById('SelectRight2') != null
					&& i < document.getElementById('SelectRight2').options.length; i++) {
				document.getElementById('SelectRight2').options[i].selected = true;
				department = department + document.getElementById('SelectRight2').options[i].text
										+ ",";
			}
			department.substring(0,
					department.length - 1);
		}

		if (document.getElementById('SelectRight4') != null
				&& document.getElementById('SelectRight4').length == 0) {

		} else {
			for ( var i = 0; document.getElementById('SelectRight4') != null
					&& i < document.getElementById('SelectRight4').options.length; i++) {
				document.getElementById('SelectRight4').options[i].selected = true;
				designation = designation
										+ document
												.getElementById('SelectRight4').options[i].text
										+ ",";
			}
			designation.substring(0,
					designation.length - 1);
		}

		if (document.getElementById('SelectRight6') != null
				&& document.getElementById('SelectRight6').length == 0) {

		} else {
			for ( var i = 0; document.getElementById('SelectRight6') != null
					&& i < document.getElementById('SelectRight6').options.length; i++) {
				document.getElementById('SelectRight6').options[i].selected = true;
				role = role +document
												.getElementById('SelectRight6').options[i].text+",";
			}
			role.substring(0,
					role.length - 1);
		}

		if (document.getElementById('SelectRight8') != null
				&& document.getElementById('SelectRight8').length == 0) {

		} else {
			for ( var i = 0; document.getElementById('SelectRight8') != null
					&& i < document.getElementById('SelectRight8').options.length; i++) {
				document.getElementById('SelectRight8').options[i].selected = true;
				sfid = sfid + document
												.getElementById('SelectRight8').options[i].text
										+ ",";
			}
			sfid.substring(0,
					sfid.length - 1);
		}
		if (document.getElementById('SelectRight10') != null
				&& document.getElementById('SelectRight10').length == 0) {

		} else {
			for ( var i = 0; document.getElementById('SelectRight10') != null
					&& i < document.getElementById('SelectRight10').options.length; i++) {
				document.getElementById('SelectRight10').options[i].selected = true;
				roleHirarchy = roleHirarchy
										+ document
												.getElementById('SelectRight10').options[i].value
										+ ",";
			}
			roleHirarchy.substring(0,
					roleHirarchy.length - 1);
		}
		
		var mainJSON = {};	
		var subJSON = {};
			subJSON['subjectData'] = subjectData;
			subJSON['contentData'] = contentData;
			subJSON['referenceData'] = referenceData;
			subJSON['enclosersData'] = enclosersData;
			subJSON['department'] = department;
			subJSON['designation'] = designation;
			subJSON['role'] = role;
			subJSON['sfid'] = sfid;
			subJSON['roleHirarchy'] = roleHirarchy;
			mainJSON[0] = subJSON;
		

	window
			.open(
					"./report.htm?param=previewIONinPreview&returnValue=report&ionId="
							+ id+'&jsonValue='+ JSON.stringify(mainJSON),
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	}

}

function clearIONDetails() {
	$jq('#id').val('');
	$jq('#ionSubjectSearch').val('');
	if ($jq('#back').val() != 'IONDetailsDataList') {
		$jq('#startDate').val('');
		$jq('#endDate').val('');
		$jq('#back').val('');
		$jq('#deptNum').val('');
		$jq('#letterFormatId').val('');
		$jq('#selectStatus').val('0');

		var requestDetails = {
			"id" : $jq("#id").val(),
			"startDate" : $jq("#startDate").val(),
			"ionSubjectSearch" : $jq('#ionSubjectSearch').val(),
			"endDate" : $jq("#endDate").val(),
			"type" : $jq("#type").val(),
			"param" : 'IONDetailsDataList'
		};
//		$jq.ajax( {
//			type : "POST",
//			url : "letterNumberFormat.htm?" + dispUrl,
//			data : requestDetails,
//			cache : false,
//			success : function(html) {
//				$jq("#displayTable").html(html);
//
//			}
//		});
	} else {

		getLetterNumberFormatList('deptChanged');

	}
}
function clearIONDetails1() {
	$jq('#id').val('');
	$jq('#ionSubjectSearch').val('');
	if ($jq('#back').val() != 'IONDetailsDataList') {
		$jq('#startDate').val('');
		$jq('#endDate').val('');
		$jq('#back').val('');
		$jq('#deptNum').val('');
		$jq('#letterFormatId').val('');
		$jq('#selectStatus').val('0');

		var requestDetails = {
			"id" : $jq("#id").val(),
			"startDate" : $jq("#startDate").val(),
			"ionSubjectSearch" : $jq('#ionSubjectSearch').val(),
			"endDate" : $jq("#endDate").val(),
			"type" : $jq("#type").val(),
			"param" : 'IONDetailsDataList'
		};
		$jq.ajax( {
			type : "POST",
			url : "letterNumberFormat.htm?" + dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#displayTable").html(html);

			}
		});
	} 
}
function getIONCirculationDetails() {

	var startDate = $jq("#startDate").val();
	var endDate = $jq("#endDate").val();
	var errorMessage = "";
	var status = true;
	if ($jq("#deptNum").val() == "") {
		errorMessage += "Select Department.\n";
		status = false;
		$jq("#deptNum").focus();
	}
	if ($jq("#letterFormatId").val() == "") {
		errorMessage += "Select Serial Number.\n";
		status = false;
		$jq("#letterFormatId").focus();
	}
//	if ($jq("#startDate").val() == "") {
//		errorMessage += "Select Start Date.\n";
//		status = false;
//		$jq("#startDate").focus();
//	}
//	if ($jq("#endDate").val() == "") {
//		errorMessage += "Select End Date.\n";
//		if (status) {
//			status = false;
//			$jq("#endDate").focus();
//		}
//	}
	if (($jq("#startDate").val() != "")
			&& ($jq("#endDate").val() != "")
			&& (compareDates(startDate, 'dd-MMM-yyyy', endDate, 'dd-MMM-yyyy') == 1)) {
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
			"deptNum" : $jq("#deptNum").val(),
			"ionSubjectSearch" : $jq("#ionSubjectSearch").val(),
			"letterFormatId" : $jq("#letterFormatId").val(),
			"selectStatus" : $jq("#selectStatus").val(),
			"type" : $jq("#type").val(),
			"param" : 'IONDetailsDataList'
		};
		$jq.ajax( {
			type : "POST",
			url : "letterNumberFormat.htm?" + dispUrl,
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
function getIONMaster(id) {
	var errorMessage = "";
	var status = true;
	if ($jq("#deptNum").val() == "") {
		errorMessage += "Select Department.\n";
		status = false;
		$jq("#deptNum").focus();
	}
	if ($jq("#letterFormatId").val() == "") {
		errorMessage += "Select Serial Number.\n";
		status = false;
		$jq("#letterFormatId").focus();
	}
	if(status)
	{
	$jq('#id').val(id);
	$jq('#param').val('ionMaster');
	$jq('#type').val('ionMaster');
	$jq('#circulateVisible').val('');
	$jq('#ionScreenType').val('searchAndEdit');
	
	document.forms[0].action = "letterNumberFormat.htm";
	document.forms[0].submit();
	}
	else
	{
		alert(errorMessage);
	}
}

function getIONMasterToCirculate(id) {
	$jq('#id').val(id);
	$jq('#param').val('ionMaster');
	$jq('#type').val('ionMaster');
	$jq('#circulateVisible').val('1');
	$jq('#ionScreenType').val('circulateIon');
	
	document.forms[0].action = "letterNumberFormat.htm";
	document.forms[0].submit();
}
function goBackToIONDetails() {
	$jq('#id').val('');
	$jq('#param').val('IONDetails');
	$jq('#type').val('IONDetails');
	$jq('#back').val('IONDetailsDataList');
	$jq('#tempLetterFormatId').val($jq('#letterFormatId').val());
	if($jq('#circulateVisible').val()=='1')
	{
		$jq('#param').val('CirculateION');
	}

	document.forms[0].action = "letterNumberFormat.htm";
	document.forms[0].submit();
}
function clearIonCirculatedDetails() {
	if (document.getElementById('roleButton0') != null) {
		$jq("#id").val(''), $jq("#refOrgRoleId").val(''), $jq(
				"#forwardedRemarks").val(''), $jq("#savingType").val(''), $jq(
				"#jsonValues1").val(''), $jq("#jsonValues2").val(''),

		getLevel1SfidList();
	}
}
function ionCirculatedSubmit(type) {
	var errorMessage = "";
	var del = null;
	var status = true;
	$jq('#sfidNameId').val('');
	var mainJSON1 = {};
	var mainJSON2 = {};
	if (type == 'forwarded') {
		if ((document.getElementById('SelectRight2') != null && document
				.getElementById('SelectRight2').length == 0)) {
			errorMessage += "Select Sfid to send the Circulation.\n";
			status = false;

		}
	}
	
	
	/*if(type != 'forwarded' && !$jq('#role').is(':visible')){
		
		var del=confirm("Please Note Your Remarks Will Be Visible To Your Boss /n Do You Want To Continue");
		if(!del){
	    status = false;
		}
	}*/
	if ($jq("#forwardedRemarks").val() == "") {
		errorMessage += "Enter Remarks.\n";
		status = false;
		$jq("#forwardedRemarks").focus();
	}
	
	if (type == 'forwarded' && document.getElementById('SelectRight2') != null
			&& document.getElementById('SelectRight2').length == 0) {

	} else {
		for ( var i = 0, j = 0; i <= $jq('#dataList').find("tr").length; i++) {
			if ($jq('#ion' + i).is(':checked')
					&& !$jq('#ion' + i).is(':disabled')) {
				var subJSON = {};
				subJSON['ionId'] = $jq('#ion' + i).val();
				subJSON['savingType'] = $jq("#savingType"+i).val();
				mainJSON1[j] = subJSON;
				j++;
				//var savetype = $jq("#savingType"+i).val();
			}
		}
		if(type == 'forwarded'){
		for ( var i = 0, j = 0; document.getElementById('SelectRight2') != null
				&& i < document.getElementById('SelectRight2').options.length; i++) {
			document.getElementById('SelectRight2').options[i].selected = true;

			var subJSON = {};

			subJSON['sfid'] = document.getElementById('SelectRight2').options[i].value;
			mainJSON2[j] = subJSON;
			j++;
		}
		}
	}
	if (JSON.stringify(mainJSON1).length == 2 && status) {
		errorMessage += 'please check at least one check box';
		status = false;
	}
       if(type != 'forwarded' && !$jq('#role').is(':visible')){
    	   if(status){
		
		var del=confirm("Please Note Your Remarks Will Be Visible To Your Boss /n Do You Want To Continue");
		if(del){
	
	
		var requestDetails = {
			"id" : $jq("#id").val(),
			"refOrgRoleId" : $jq("#refOrgRoleId").val(),
			"remarks" : $jq("#forwardedRemarks").val(),
			"sfidNameId" : $jq("#sfidNameId").val(),
			"savingType" : type,
			"jsonValues1" : JSON.stringify(mainJSON1),
			"jsonValues2" : JSON.stringify(mainJSON2),
			"param" : 'manageIONCirculationDetails'

		};
		$jq.ajax( {
			type : "POST",
			url : "ion.htm?" + dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#displayTable").html(html);
				if ($jq('.success').is(':visible')) {
					
				}

			}

		});
		}
	}else {
		alert(errorMessage);
	}
}else{
	
if(status){
		var requestDetails = {
			"id" : $jq("#id").val(),
			"refOrgRoleId" : $jq("#refOrgRoleId").val(),
			"remarks" : $jq("#forwardedRemarks").val(),
			"sfidNameId" : $jq("#sfidNameId").val(),
			"savingType" : type,
			"jsonValues1" : JSON.stringify(mainJSON1),
			"jsonValues2" : JSON.stringify(mainJSON2),
			"param" : 'manageIONCirculationDetails'

		};
		$jq.ajax( {
			type : "POST",
			url : "ion.htm?" + dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#displayTable").html(html);
				if ($jq('.success').is(':visible')) {
					
				}

			}

		});
		} 
   else{
   alert(errorMessage); 
   }
	
	
}
}
function setLevel1SfidRole(roleId)
{
	$jq("#refOrgRoleId").val(roleId);
	getLevel1SfidList();
}
function getLevel1SfidList() {

	$jq('#id').val('');
	var requestDetails = {
		"refOrgRoleId" : $jq("#refOrgRoleId").val(),
		"type" : 'getLevel1SfidList',
		"param" : 'getLevel1SfidList'
	};
	$jq.ajax( {
		type : "POST",
		url : "ion.htm?" + dispUrl,
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq("#result1").html(html);

			getNoticeList();

		}

	});
}

function setLevel1SfidList(jsonLevel1SfidList) {
	$jq('#SelectLeft2').find('option').remove().end();
	$jq('#SelectRight2').find('option').remove().end();

	for ( var i = 0; jsonLevel1SfidList != null
			&& i < jsonLevel1SfidList.length; i++) {

		$jq("#SelectLeft2")
				.append(
						'<option value=' + jsonLevel1SfidList[i].userSfid + '>'
								+ jsonLevel1SfidList[i].nameInServiceBook
								+ '</option>');

	}

}
function getEditable(id, jsonNoticeBoardList) {
	$jq('#EmpList').show();
	$jq('#' + id).attr("disabled", false);

	$jq('#id').val($jq('#' + id).val());
	$jq('#forwardedRemarks').val('');

	$jq("input:checkbox").attr("checked", false);

	$jq('#' + id).attr("checked", true);
	for ( var i = 0; i < jsonNoticeBoardList.length; i++) {
		if (jsonNoticeBoardList[i].id == $jq('#id').val()) {
			var jsonSelectedList = jsonNoticeBoardList[i].ionCirculationDetailsList;
			jsonMainList = jsonNoticeBoardList[i].sfidList;
			setSelecledList(jsonMainList, jsonSelectedList);
		}
	}
}
function checkBoxCheckAll1(type,rowType){
	if($jq('input:checkbox[name='+type+']').is(':checked')) {
		$jq('.'+rowType).attr('checked', 'checked');	
	}else {
		 $jq("."+rowType).removeAttr("checked");
	}	
}
function setSelecledList(jsonMainList, jsonSelectedList) {
	var create1 = '';
	var create2 = '';
	$jq('#SelectRight2 option').remove();
	$jq('#SelectLeft2 option').remove();

	for ( var i = 0; i < jsonMainList.length; i++) {
		var flag = true;
		for ( var j = 0; jsonSelectedList != null
				&& j < jsonSelectedList.length; j++) {
			if (jsonMainList[i].userSfid == jsonSelectedList[j].sfid) {
				create2 += '<option value="' + jsonMainList[i].userSfid + '">'
						+ jsonMainList[i].nameInServiceBook + '</option>';
				$jq('#forwardedRemarks').val(jsonSelectedList[j].remarks);
				flag = false;
				break;
			}
		}
		if (flag) {
			create1 += '<option value="' + jsonMainList[i].userSfid + '">'
					+ jsonMainList[i].nameInServiceBook + '</option>';
		}
	}
	$jq('#SelectRight2').append(create2);
	$jq('#SelectLeft2').append(create1);

}
function getNoticeList() {
	var requestDetails = {
		"refOrgRoleId" : $jq("#refOrgRoleId").val(),
		"type" : 'getNoticeBoardList',
		"param" : 'getNoticeBoardList'
	};
	$jq.ajax( {
		type : "POST",
		url : "ion.htm?" + dispUrl,
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq("#displayTable").html(html);
			if ($jq('.success').is(':visible')) {

			}

		}

	});
}

function getInitiatedRoleList(val)
{
	var val1='';
	var val2='';
	var val3='';
	if(val==0)
	{
		val1 = 'ionInitiatedSfid';
		val2 = 'getIONInitiatedRoleList';
		val3 = 'initiatedRoleId';
	}
	else if(val ==1)
	{
		val1 = 'ionForwardSfid';
		val2 = 'getIONForwardRoleList';
		val3 = 'forwardRoleId';
	
	}
	else if(val ==2)
	{
		val1 = 'ionApprovedSfid';
		val2 = 'getIONApprovedRoleList';
		val3 = 'approvedRoleId';
	}
	var requestDetails = {
			"ionInitiatedSfid" : $jq("#"+val1).val(),
			"type" : 'getIONInitiatedRoleList',
			"param" : val2
		};
		$jq.ajax( {
			type : "POST",
			url : "letterNumberFormat.htm?" + dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#"+val3).html(html);
				
			}

		});
}

function setInitiatedRoleList(jsonIONInitiatedRoleList)
{
	$jq('#initiatedRoleIdName').hide();
	$jq('#initiatedRoleIdVal').hide();
	if(jsonIONInitiatedRoleList != null && jsonIONInitiatedRoleList.length >0)
	{
		$jq('#initiatedRoleIdName').show();
		$jq('#initiatedRoleIdVal').show();
		$jq('#ionInitiatedRoleId').find('option').remove().end();
		$jq("#ionInitiatedRoleId").append('<option value="">Select</option>');
		

		for ( var i = 0; jsonIONInitiatedRoleList != null
				&& i < jsonIONInitiatedRoleList.length; i++) {
			
				$jq("#ionInitiatedRoleId").append(
						'<option value=' + jsonIONInitiatedRoleList[i].id + '>'
								+ jsonIONInitiatedRoleList[i].name + '</option>');
			
		}
	}
	
}

function setForwardRoleList(jsonIONForwardRoleList)
{
	$jq('#forwardRoleIdName').hide();
	$jq('#forwardRoleIdVal').hide();
	if(jsonIONForwardRoleList != null && jsonIONForwardRoleList.length >0)
	{
		$jq('#forwardRoleIdName').show();
		$jq('#forwardRoleIdVal').show();
		$jq('#ionForwardRoleId').find('option').remove().end();
		$jq("#ionForwardRoleId").append('<option value="">Select</option>');
		

		for ( var i = 0; jsonIONForwardRoleList != null
				&& i < jsonIONForwardRoleList.length; i++) {
			
				$jq("#ionForwardRoleId").append(
						'<option value=' + jsonIONForwardRoleList[i].id + '>'
								+ jsonIONForwardRoleList[i].name + '</option>');
			
		}
	}
	
}

function setApprovedRoleList(jsonIONApprovedRoleList)
{
	$jq('#approvedRoleIdName').hide();
	$jq('#approvedRoleIdVal').hide();
	if(jsonIONApprovedRoleList != null && jsonIONApprovedRoleList.length >0)
	{
		$jq('#approvedRoleIdName').show();
		$jq('#approvedRoleIdVal').show();
		$jq('#ionApprovedRoleId').find('option').remove().end();
		$jq("#ionApprovedRoleId").append('<option value="">Select</option>');
		

		for ( var i = 0; jsonIONApprovedRoleList != null
				&& i < jsonIONApprovedRoleList.length; i++) {
			
				$jq("#ionApprovedRoleId").append(
						'<option value=' + jsonIONApprovedRoleList[i].id + '>'
								+ jsonIONApprovedRoleList[i].name + '</option>');
			
		}
	}
	
}
function getIONListToCirculate(roleId)
{
	$jq("#refOrgRoleId").val(roleId)
	var requestDetails = {
			"refOrgRoleId" : $jq("#refOrgRoleId").val(),
			"type" : 'CirculateIONDataList',
			"param" : 'CirculateION'
		};
		$jq.ajax( {
			type : "POST",
			url : "letterNumberFormat.htm?" + dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#displayTable").html(html);
				
			}

		});
}
function createEncloserList(jsonfileEncloserList)
{
	var row1 = '';
	$jq('#enclosers4').html(row1);
	
	if(jsonfileEncloserList != null)
	{
	row1 +="<table style=width:100% border=0>";
	for(var i = 0;jsonfileEncloserList != null && i<jsonfileEncloserList.length;i++)
	{
		row1 +="<tr>";
	row1 += "<td ><a href=javascript:showFile('"+jsonfileEncloserList[i].id+"');>"+jsonfileEncloserList[i].filename+"</a></td>";
	row1 += "<td ><a href=javascript:removeFile('"+jsonfileEncloserList[i].id+"');>Remove</a></td>";
	row1 +="</tr>";
	}
	row1 +="</table>";
	$jq('#enclosers4').html(row1);
	}
	
	 row1 = '';
	$jq('#enclosers3').html(row1);
	if(jsonfileEncloserList != null)
	{
	row1 +="<table style=width:100% border=0>"
	for(var i = 0;jsonfileEncloserList != null && i<jsonfileEncloserList.length;i++)
	{
		row1 +="<tr>";
	row1 += "<td ><a href=javascript:showFile('"+jsonfileEncloserList[i].id+"');>"+jsonfileEncloserList[i].filename+"</a></td>";
		row1 +="</tr>";
	}
	row1 +="</table>";
	$jq('#enclosers3').html(row1);
	}
	
}
function manageCirculation(ionId)
{
	$jq("#id").val(ionId);
	var requestDetails = {
			"id" : $jq("#id").val(),
			"refOrgRoleId" : $jq("#refOrgRoleId").val(),
			"type" : 'manageCirculation',
			"param" : 'manageCirculation'
		};
		$jq.ajax( {
			type : "POST",
			url : "letterNumberFormat.htm?" + dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#displayTable").html(html);
				
			}

		});
}
function enableDispatch(type,id, jsonNoticeBoardList){	
	
	
	//if(JSON.stringify(mainJSON1).length == 2 && status){
	if(type.value == 'forwarded'){
		
		$jq('#' + id).attr("disabled", false);
		$jq('#id').val($jq('#' + id).val());
		$jq('#forwardedRemarks').val('');
		$jq("input:checkbox").attr("checked", false);
		$jq('#' + id).attr("checked", true);
		for ( var i = 0; i < jsonNoticeBoardList.length; i++) {
		if (jsonNoticeBoardList[i].id == $jq('#id').val()) {
		var jsonSelectedList = jsonNoticeBoardList[i].ionCirculationDetailsList;
		jsonMainList = jsonNoticeBoardList[i].sfidList;
		setSelecledList(jsonMainList, jsonSelectedList);
		}
		} 	
	$jq('#dispatchList').show();
	$jq('#button1').hide();
	$jq('#button').show();
	}else{
		$jq('#' + id).attr("disabled", false);
		$jq('#forwardedRemarks').val('');
		$jq('#id').val($jq('#' + id).val());
		$jq("input:checkbox").attr("checked", false);
		$jq('#' + id).attr("checked", true);
		$jq('#dispatchList').hide();
		$jq('#button1').show();
		$jq('#button').hide();
	}
	//}
}

