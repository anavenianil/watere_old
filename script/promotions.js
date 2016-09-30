function clearResidencyPeriodDetails() {
	nodeID = "";
	$jq('input:text').val("");
	$jq('select').val("0");
	$jq('#designationFrom').find('option').remove().end();
	$jq("#designationFrom").append($jq("<option></option>").attr("value",'0').text('Select'));
	$jq('#designationTo').find('option').remove().end();
	$jq("#designationTo").append($jq("<option></option>").attr("value",'0').text('Select'));
	
	$jq('#Written').attr('checked',false);
	$jq('#trade').attr('checked',false);
	$jq('#interview').attr('checked',false);
	$jq('#board').attr('checked',false);
	$jq('input:radio').attr("checked",false);
	//$jq("#result").val("");
	//$jq('#result').text('');
}
function getDesignations(){
	$jq('#designationFrom').find('option').remove().end();
	$jq('#designationTo').find('option').remove().end();
	$jq("#designationFrom").append($jq("<option></option>").attr("value",'0').text('Select'));
	$jq("#designationTo").append($jq("<option></option>").attr("value",'0').text('Select'));
	$jq('input:radio').attr("checked",false);
	var requestDetails = {
			"assessmentTypeID" :$jq('#assessmentTypeID').val(),
			"assessmentCategoryID":$jq('#assessmentCategoryID').val(),
			"param" : "residencyPeriodList"
		};
		$jq.ajax( {
			type : "POST",
			url : "promotion.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				
				getDesignationList()
			}
		});
		if($jq('#assessmentCategoryID').val()==3){
			ldceDesignationList();
		}

}

function getEmpGradePay(){
	$jq('#newGradePay').val('');
	for(var i=0; i<empGradePayJson.length; i++){
		if(empGradePayJson[i].id == $jq('#designationTo').val()){
		 $jq('#newGradePay').val(empGradePayJson[i].gradePay);
		}
	}
}


function checkSize(e){
	if($jq('#designationTo').val()!=0){
		if($jq('#designationFrom').val() != ''){
			var check = checkInt(e);
			if(check){
				if($jq('#designationFrom').val().length >1){
					alert("Please Enter Single Digit Only");
					$jq('#designationFrom').val('');
				}else{
					getEmpVarVal();
				}	
			}else{
				$jq('#designationFrom').val('');
			}	
		}
	}else{
		alert("Please Select Designation");
		$jq('#designationFrom').val('');
	}
}
function getEmpVarVal(){
	if($jq('#designationTo').val()!=0){
		$jq('#param').val('getVariableIncrementValue');
		$jq.post('promotion.htm',$jq('#PromotionManagementBean').serialize(), function(html) {	
			$jq('#result').html(html);
		});
	}else{
		alert("Please Select Designation");
		$jq('#designationFrom').val('');
	}
	
}

function getDesignationList(){
	$jq('#designationTo').find('option').remove().end();
	$jq("#designationTo").append($jq("<option></option>").attr("value",'0').text('Select'));
	$jq('#designationFrom').find('option').remove().end();
	$jq("#designationFrom").append($jq("<option></option>").attr("value",'0').text('Select'));
	$jq("#newGradePay").append($jq("<option></option>").attr("value",'0').text('Select'));
	var assessmentTypeID=$jq('#assessmentTypeID').val();
	var assessmentCategoryId =$jq('#assessmentCategoryID').val();
	if(residencyListJSON!=null && residencyListJSON !=''){
	for(i=0;i<designationListJSON.length;i++){
		if(designationListJSON[i].id==assessmentTypeID){
			for(j=0;j<residencyListJSON.length;j++){
				
				if(assessmentCategoryId != 3){
				if(designationListJSON[i].desigID!=residencyListJSON[j].designationFrom ){
					
					
					var status=true;
					for(var k=0;k<document.getElementById('designationFrom').options.length;k++) {
						if(residencyListJSON[j].designationFrom==designationListJSON[i].desigID || designationListJSON[i].desigID==document.getElementById('designationFrom').options[k].value){
							status = false;
							break;
						}
					}
					
					for(l=0;l<residencyListJSON.length;l++){
						if(residencyListJSON[l].designationFrom==designationListJSON[i].desigID){
							status = false;
							break;
						}
					}
					if(status){
					$jq('#designationFrom').append($jq("<option></option>").attr("value",designationListJSON[i].desigID).text(designationListJSON[i].name));
					}
				}
			}else{
				var status=true;
				for(var k=0;k<document.getElementById('designationFrom').options.length;k++) {
					if(residencyListJSON[j].designationFrom==designationListJSON[i].desigID || designationListJSON[i].desigID==document.getElementById('designationFrom').options[k].value){
						status = false;
						break;
					}
				}
				if(status){
				  $jq('#designationFrom').append($jq("<option></option>").attr("value",designationListJSON[i].desigID).text(designationListJSON[i].name));
			     }
			}
				if(designationListJSON[i].desigID!=residencyListJSON[j].designationTo ){
					var status=true;
					for(var k=0;k<document.getElementById('designationTo').options.length;k++) {
						if(residencyListJSON[j].designationTo==designationListJSON[i].desigID || designationListJSON[i].desigID==document.getElementById('designationTo').options[k].value){
							status = false;
							break;
						}
					}
					for(l=0;l<residencyListJSON.length;l++){
						if(residencyListJSON[l].designationTo==designationListJSON[i].desigID){
							status = false;
							break;
						}
					}
					if(status){
					$jq('#designationTo').append($jq("<option></option>").attr("value",designationListJSON[i].desigID).text(designationListJSON[i].name));
					}
				}
			}
		}
	}
}
	else{
		for(i=0;i<designationListJSON.length;i++){
			if(designationListJSON[i].id==assessmentTypeID){
				$jq('#designationFrom').append($jq("<option></option>").attr("value",designationListJSON[i].desigID).text(designationListJSON[i].name));
				$jq('#designationTo').append($jq("<option></option>").attr("value",designationListJSON[i].desigID).text(designationListJSON[i].name));
			}
		}

	}
	var assessmentID=$jq('#assessmentCategoryID').val();
	
}
function ldceDesignationList(){
	var assessmentID=$jq('#assessmentCategoryID').val();
	if(assessmentID==3){
	$jq('#designationTo').find('option').remove().end();
	//$jq('#designationFrom').find('option').remove().end();
	$jq("#designationTo").append($jq("<option></option>").attr("value",'0').text('Select'));
	//$jq("#designationFrom").append($jq("<option></option>").attr("value",'0').text('Select'));
	for(i=0;i<designationListJSON.length;i++){
			$jq('#designationTo').append($jq("<option></option>").attr("value",designationListJSON[i].desigID).text(designationListJSON[i].name));
		}
	
//	for(i=0;i<designationListJSON.length;i++){
//		$jq('#designationFrom').append($jq("<option></option>").attr("value",designationListJSON[i].desigID).text(designationListJSON[i].name));
//	}

	}
	else{
		getDesignationList();	
	}
}
function submitResidencyPeriodDetails() {
	var errorMessage = "";
	var status = true;
	if ($jq('#assessmentTypeID').val() == '0') {
		errorMessage += "Please select Category type.\n";
		if (status) {
			$jq('#assessmentTypeID').focus();
			status = false;
		}
	}
	if ($jq('#assessmentCategoryID').val() == '0') {
		errorMessage += "Please select board type.\n";
		if (status) {
			$jq('#assessmentCategoryID').focus();
			status = false;
		}
	}
	if ($jq('#designationFrom').val() == '0') {
		errorMessage += "Please select designation from.\n";
		if (status) {
			$jq('#designationFrom').focus();
			status = false;
		}
	}
	if ($jq('#designationTo').val() == '0') {
		errorMessage += "Please select designation to.\n";
		if (status) {
			
			$jq('#desginationToId').focus();
			status = false;
		}
	}
	if ($jq('#residencyPeriod').val() == '') {
		errorMessage += "Please enter experince.\n";
		if (status) {
			$jq('#residencyPeriod').focus();
			status = false;
		}
	}
	if(!$jq('input:radio[name=Written]').is(':checked')){
		errorMessage += "Please Select Either Written Test Required or Not Required .\n";
		status = false;
		}

	if(!$jq('input:radio[name=trade]').is(':checked')){
		errorMessage += "Please Select Either Trade Test Required or Not Required .\n";
		status = false;
	}
	if(!$jq('input:radio[name=interview]').is(':checked')){
		errorMessage += "Please Select Either Interview  Required or Not Required .\n";
		status = false;
	}
	if(!$jq('input:radio[name=board]').is(':checked')){
		errorMessage += "Please Select Either Public Or Private Board .\n";
		status = false;
	}
	if($jq('#designationFrom').val()==$jq('#designationTo').val()){
		errorMessage += "From Designation and To Designation Cannot be Equal.\n";
		if (status) {
			$jq('#designationTo').focus();
			status = false;
		}
	}
	if (errorMessage == "") {
		$jq('#param').val('manageResidency');
		$jq.post('promotion.htm?nodeID=' + nodeID, $jq(
				'#PromotionManagementBean').serialize(), function(html) {
			$jq("#result").html(html);
			if ($jq('.success').is(':visible')) {
				clearResidencyPeriodDetails();
			}
		});
	} else {
		alert(errorMessage);
	}
}

function deleteResidencyPeriodDetails(id) {
	var requestDetails = {
		"nodeID" : id,
		"param" : "deleteResidency",
		"assessmentTypeID" :$jq('#assessmentTypeID').val()
	};
	var resp=confirm("Do you want to delete this Record?");
	
	if(resp ==  true){
	$jq.ajax( {
		type : "POST",
		url : "promotion.htm",
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq('#result').html(html);
			if ($jq('.success').is(':visible')) {
				//clearResidencyPeriodDetails();
			}
		}
	});
	}
}

function editResidencyPeriodDetails(id, assessmentTypeId, assessmentCategory,
		designFromId, designToId, residencyPeriod, relaxationOfMonths,
		written,trade,interview,board) {
	$jq('#designationFrom').find('option').remove().end();
	$jq("#designationFrom").append($jq("<option></option>").attr("value",'0').text('Select'));
	
	
	if(assessmentCategory != 3){
      $jq('#designationTo').find('option').remove().end();
	$jq("#designationTo").append($jq("<option></option>").attr("value",'0').text('Select'));
}
	$jq('input:radio').attr("checked",false);
	var assessmentTypeID=assessmentTypeId;
	
	
	for(i=0;i<designationListJSON.length;i++){
		if(designationListJSON[i].id==assessmentTypeID){
			$jq('#designationFrom').append($jq("<option></option>").attr("value",designationListJSON[i].desigID).text(designationListJSON[i].name));
			if(assessmentCategory != 3){
		$jq('#designationTo').append($jq("<option></option>").attr("value",designationListJSON[i].desigID).text(designationListJSON[i].name));
			}
		}
	}

	nodeID = id;
	$jq('#assessmentTypeID').val(assessmentTypeId);
	$jq('#assessmentCategoryID').val(assessmentCategory);
	$jq('#designationFrom').val(designFromId);
	$jq('#designationTo').val(designToId);
	$jq('#residencyPeriod').val(residencyPeriod);
	$jq('#relaxationInMonths').val(relaxationOfMonths);
	
	$jq("#Written[value='" +written + "']").attr('checked', true);
	$jq("#trade[value='" +trade + "']").attr('checked', true);
	$jq("#interview[value='" +interview + "']").attr('checked', true);
	$jq("#board[value='" +board + "']").attr('checked', true);
	
}

//Venue Details starts
function getVenueList(){
	var requestDetails = {
			"yearID" :$jq('#yearID').val(),
			"assessmentCategoryID":$jq('#assessmentCategoryID').val(),
			"assessmentTypeID":$jq('#assessmentTypeID').val(),
			"param" : "PromotionVenueDetailsList"
		};
		$jq.ajax( {
			type : "POST",
			url : "promotion.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
			}
		});
}
function resetVenueDetails(){
	nodeID = "";
	$jq('input:text').val("");
	$jq('select').val("0");
	$jq('textarea').val("");
	//$jq('#result').text('');
}

function submitVenueDetails(){
	var errorMessages="";
	var status=true;
	
	if($jq('#assessmentTypeID').val()=='0'){
		errorMessages += "Please select Category type.\n";
		if(status){
			$jq('#assessmentTypeID').focus();
			status = false;
		}
	}

	if($jq('#assessmentCategoryID').val()=='0'){
		errorMessages += "Please select board type.\n";
		if(status){
			$jq('#assessmentCategoryID').focus();
			status = false;
		}
	}
	if($jq('#yearID').val()=='0'){
		errorMessages += "Please select year.\n";
		if(status){
			$jq('#yearID').focus();
			status = false;
		}
	}
	if($jq('#center').val()==''){
		errorMessages += "Please enter center.\n";
		if(status){
			$jq('#center').focus();
			status = false;
		}
	}
	if($jq('#coOrdinator').val()==''){
		errorMessages += "Please enter name of co-ordinator.\n";
		if(status){
			$jq('#coOrdinator').focus();
			status = false;
		}
	}
	if($jq('#coOrdinatorLab').val()==''){
		errorMessages += "Please enter co-ordinator lab.\n";
		if(status){
			$jq('#coOrdinatorLab').focus();
			status = false;
		}
	}
	if($jq('#address').val()==''){
		errorMessages += "Please enter Address.\n";
		if(status){
			$jq('#address').focus();
			status = false;
		}
	}
	if($jq('#contactAddress').val()==''){
		errorMessages += "Please enter contact details.\n";
		if(status){
			$jq('#contactAddress').focus();
			status = false;
		}
	}
	if($jq('#venue').val()==''){
		errorMessages += "Please enter venue.\n";
		if(status){
			$jq('#venue').focus();
			status = false;
		}
	}
	
	if(status){
		$jq('#param').val('manageVenue');
		$jq.post('promotion.htm?nodeID=' + nodeID +'&'+dispUrl, $jq(
				'#PromotionManagementBean').serialize(), function(html) {
			$jq("#result").html(html);
			if ($jq('.success').is(':visible')) {
				resetVenueDetails();
			}
		});
	} else {
		alert(errorMessages);
	}
}

function editVenueDetails(id){
	resetVenueDetails();
	for ( var i = 0; i < jsonArrayObject.length; i++) {
		if (jsonArrayObject[i].id == id) {
			$jq('#assessmentTypeID').val(jsonArrayObject[i].categoryId);
			$jq("#assessmentCategoryID").val(jsonArrayObject[i].assessmentCategoryID);
			$jq("#yearID").val(jsonArrayObject[i].yearID);
			$jq("#center").val(jsonArrayObject[i].center);
			$jq("#coOrdinator").val(jsonArrayObject[i].coOrdinator);
			$jq("#coOrdinatorLab").val(jsonArrayObject[i].coOrdinatorLab);
			$jq("#address").val(jsonArrayObject[i].address);
			$jq("#contactAddress").val(jsonArrayObject[i].contactAddress);
			$jq("#venue").val(jsonArrayObject[i].venue);
			nodeID = id;
		}
	}
}

function deleteVenueDetails(id) {
	var requestDetails = {
		"nodeID" : id,
		"assessmentCategoryID":$jq("#assessmentCategoryID").val(),
		"yearID":$jq("#yearID").val(),
		"param" : "deleteVenue"
	};
var resp=confirm("Do you want to delete this Record?");
	
	if(resp ==  true){
		$jq.ajax( {
		type : "POST",
		url : "promotion.htm",
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq('#result').html(html);
			if ($jq('.success').is(':visible')) {
				//resetVenueDetails();
			}
		}
	});
	}
}
//Venue Details ends

//Add employee for assessment starts
function resetExpEmpDetails(){
	nodeID = "";
	$jq('input:text').val("");
	$jq('textarea').val("");
	$jq('select').val("0");
}
function editExpEmpDetails(id,yearID,sfid,description){
	nodeID = id;
	$jq('#yearID').val(yearID);
	$jq('#expEmp').val(sfid);
	$jq('#description').val(description);
}
function deleteExpEmpDetails(id) {
	var requestDetails = {
		"nodeID" : id,
		"param" : "deleteExpEmp"
	};
var resp=confirm("Do you want to delete this Record?");
	
	if(resp ==  true){
	$jq.ajax( {
		type : "POST",
		url : "promotion.htm",
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq('#result').html(html);
			if ($jq('.success').is(':visible')) {
				resetExpEmpDetails();
			}
		}
	});
	}
}
function submitExpEmpDetails(){
	var errorMessages = "";
	var status = true;
	
	if($jq('#yearID').val()=='0'){
		errorMessages += "Please select year.\n";
		if(status){
			$jq('#yearID').focus();
			status = false;
		}
	}
	if($jq('#expEmp').val()==''){
		errorMessages += "Please enter sfid.\n";
		if(status){
			$jq('#expEmp').focus();
			status = false;
		}
	}
	if($jq('#description').val()==''){
		errorMessages += "Please enter Remarks.\n";
		if(status){
			$jq('#description').focus();
			status = false;
		}
	}
	if(status){
		var requestDetails = {
			"nodeID" : nodeID,
			"yearID" : $jq('#yearID').val(),
			"expEmp" : $jq('#expEmp').val().toUpperCase(),
			"description" : $jq('#description').val(),
			"param" : "manageExpEmp"
		};
		$jq.ajax( {
			type : "POST",
			url : "promotion.htm?"+dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				if ($jq('.success').is(':visible')) {
					resetExpEmpDetails();
				}
			}
		});
	}else{
		alert(errorMessages);
	}
}
//Add employee for assessment ends

//Local Assessment Board Starts
function getCategoryLocalList(){
	var requestDetails = {
			"yearID" :$jq('#yearID').val(),
			"assessmentTypeID":$jq('#assessmentTypeID').val(),
			"param" : "localAssessmentBoardList"
		};
		$jq.ajax( {
			type : "POST",
			url : "promotion.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
			}
		});
}

function getLocalAssessmentList(){
	var requestDetails = {
			"yearID" :$jq('#yearID').val(),
			"param" : "localAssessmentBoardList"
		};
		$jq.ajax( {
			type : "POST",
			url : "promotion.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
			}
		});
}
function submitLocalBoardDetails() {
	var errorMessage = "";
	var status = true;
	var selectedMembers = "";
	
	if ($jq('#assessmentTypeID').val() == "0") {
		errorMessage += "Please select Category.\n";
		if (status) {
			$jq('#assessmentTypeID').focus();
			status = false;
		}
	}
	if ($jq('#yearID').val() == "0") {
		errorMessage += "Please select year.\n";
		if (status) {
			$jq('#yearID').focus();
			status = false;
		}
	}
	if ($jq('#boardName').val() == "") {
		errorMessage += "Please enter board name.\n";
		if (status) {
			$jq('#boardName').focus();
			status = false;
		}
	}
	$jq("#SelectRight option").each(function() {
		selectedMembers += $jq(this).val() + ",";
	});
	if (selectedMembers == "") {
		errorMessage += "Please select members.\n";
		if (status) {
			$jq('#SelectLeft').focus();
			status = false;
		}
	}
	if (status) {
		if (selectedMembers != "") {
			selectedMembers = selectedMembers.substring(0,
					selectedMembers.length - 1);
		}
		
		var requestDetails = {
			"nodeID" : nodeID,
			"editYearID" : yearID,
			"yearID" : $jq('#yearID').val(),
			"assessmentTypeID":$jq('#assessmentTypeID').val(),
			"boardName" : $jq('#boardName').val().toUpperCase(),
			"boardMembers" : selectedMembers,
			"param" : "manageLocalBoard"
		};
		$jq.ajax( {
			type : "POST",
			url : "promotion.htm?"+dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				if ($jq('.success').is(':visible')) {
					clearLocalBoardDetails();
				}
			}
		});
	}else {
		alert(errorMessage);
	}

}
function clearLocalBoardDetails() {
	nodeID = "";
	yearID = "";
	//$jq('#result').text('');
	$jq('#yearID').val('0');
	$jq('#boardName').val('');
	$jq('select').val("0");
	//$jq('#SelectLeft').find('option').remove().end();
	$jq('#desigID').val('0');
	$jq('#SelectLeft').find('option').remove().end();
	var desigID=$jq('#desigID').val()
	for(j=0;j<membersListJSON.length;j++){
			$jq('#SelectLeft').append($jq("<option></option>").attr("value",membersListJSON[j].key).text(membersListJSON[j].value));
	}
	if ($jq('#SelectRight') != null) {
		$jq("#SelectRight > option").each(
				function() {
					/*$jq("#SelectLeft").append(
							'<option value=' + this.value + '>' + this.text
									+ '</option>');*/
					$jq(this).remove();
				});
	}
}
function editLocalBoardDetails(boardID, yID, boardName, members,categoryid) {
	clearLocalBoardDetails();
	nodeID = boardID;
	yearID = yID;
	$jq('#yearID').val(yearID);
	$jq('#boardName').val(boardName);
	$jq('#assessmentTypeID').val(categoryid);
	var membersList = members.split(',');
	for ( var i = 0; i < membersList.length; i++) {
		$jq("#SelectLeft > option").each(
				function() {
					if (membersList[i] == this.value) {
						$jq("#SelectRight").append(
								'<option value=' + this.value + '>' + this.text
										+ '</option>');
						$jq(this).remove();
					}
				});
	}
}
function deleteLocalBoardDetails(boardID, yearID,categoryid) {
	var requestDetails = {
			"nodeID" : boardID,
			"YearID" : yearID,
			"boardID":boardID,
			"assessmentTypeID":categoryid,
			"param" : "deleteLocalBoard"
		};
var resp=confirm("Do you want to delete this Record?");
	
	if(resp ==  true){
		$jq.ajax( {
			type : "POST",
			url : "promotion.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				if ($jq('.success').is(':visible')) {
					clearLocalBoardDetails();
				}
			}
		});
	}
}
function getLocalDesigList() {
	$jq('#SelectLeft').find('option').remove().end();
	var desigID=$jq('#desigID').val()
	for(j=0;j<membersListJSON.length;j++){
		if(membersListJSON[j].id==desigID){
			$jq('#SelectLeft').append($jq("<option></option>").attr("value",membersListJSON[j].key).text(membersListJSON[j].value));
		}
	}
}

//Local Assessment Board ends

//Designation Experience Starts
function submitDesigExperienceDetails() {
	var errorMessage = "";
	var status = true;
	
	if ($jq('#refSfid').val() == '') {
		errorMessage += "Please enter SFID.\n";
		if (status) {
			status = false;
			$jq('#refSfid').focus();
		}
	}
	if ($jq('#desigAttempts').val() == '') {
		errorMessage += "Please enter Next Designation Attempts.\n";
		if (status) {
			$jq('#desigAttempts').focus();
			status = false;
		}
	}
	if(($jq.trim($jq('#result1').text()) == 'Employee not exists')){
		errorMessage += "Invalid SFID";
		if (status) {
			status = false;
			$jq('#refSfid').focus();
			
		}
	}
	if ($jq('.success').is(':visible')) {
		$jq('.success').hide();
	}
	if (status) {
		
		var requestDetails = {
			"nodeID" : nodeID,
			"refSfid" : $jq("#refSfid").val().toUpperCase(),
			"designation" : $jq("#designation").val(),
			"startDate" : $jq("#startDate").val(),
			"desigAttempts" : $jq("#desigAttempts").val(),
			"param" : "managedesigExperience"
		};
		$jq.ajax( {
			type : "POST",
			url : "promotion.htm?"+dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
			$jq('#result').html(html);
			if ($jq('.success').is(':visible')) {
				clearDesignationExperienceDetails()
			}
		}
	});
} else {
		alert(errorMessage);
	}
}
function clearDesignationExperienceDetails() {
	nodeID = "";
	$jq('input:text').val("");
	$jq('select').val("0");
	$jq('#result1').text('');
}
function editDesigExperienceDetails(id, sfid, name, designationID, startDate,
		noOfAttempts) {
	$jq('#result1').text('');
	$jq('.success').hide();
	$jq('.failure').hide();
	nodeID = id;
	$jq('#refSfid').val(sfid);
	$jq('#name').val(name);
	$jq('#designation').val(designationID);
	$jq('#startDate').val(startDate);
	$jq('#desigAttempts').val(noOfAttempts);
}
function deleteDesigExperienceDetails(id) {
	var requestDetails = {
		"nodeID" : id,
		"param" : "deleteDesigExperience"
	};
var resp=confirm("Do you want to delete this Record?");
	
	if(resp ==  true){
	$jq.ajax( {
		type : "POST",
		url : "promotion.htm",
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq('#result').html(html);
			if ($jq('.success').is(':visible')) {
				clearDesignationExperienceDetails();
			}
		}
	});
	}
}
function checkDesignation() {
	$jq('.success').hide();
	$jq('.failure').hide();
	if ($jq("#refSfid").val() == '') {
		alert("Please enter sfid");
	} else {
		$jq.ajax( {
			type : "GET",
			url : "promotion.htm",
			data : "param=checkDesignation&refSfid="
					+ $jq("#refSfid").val().toUpperCase(),
			success : function(html) {
				$jq('#result1').html(html);
			}
		});
	}
}


// Designation Experience Ends

//Qualified Candidates for assessment starts
function searchQualifiedCandidates() {
	var errorMessage = "";
	var status = true;
	if ($jq('#assessmentTypeID').val() == '0') {
		errorMessage += "Please select assessment type.\n";
		if (status) {
			$jq('#assessmentTypeID').focus();
			status = false;
		}
	}
	if ($jq('#assessmentCategoryID').val() == '0') {
		errorMessage += "Please select board type.\n";
		if (status) {
			$jq('#assessmentCategoryID').focus();
			status = false;
		}
	}
	if ($jq('#yearID').val() == '0') {
		errorMessage += "Please select year.\n";
		if (status) {
			$jq('#yearID').focus();
			status = false;
		}
	}
	if (status) {
		var requestDetails = {
		    "assessmentTypeID" :$jq('#assessmentTypeID').val().split('-')[1],
			"designationFrom"  :$jq('#designationFrom').val(), 
			"assessmentCategoryID" : $jq('#assessmentTypeID').val().split('-')[0],
			"yearID" : $jq('#yearID').val(),
			"param" : "getQualifiedCandidates"
		};
		$jq.ajax( {
			type : "POST",
			url : "promotion.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				if ($jq('.success').is(':visible')) {
					resetQualifiedCandidates();
				}
			}
		});
	} else {
		alert(errorMessage);
	}
}
function submitQualifiedCandidates() {
	var errorMessage = "";
	var status = true;
	var rowValues = "";
	
	if ($jq('#assessmentTypeID').val() == '0') {
		errorMessage += "Please select board type.\n";
		if (status) {
			$jq('#assessmentCategoryID').focus();
			status = false;
		}
	}
	if($jq('#interview').is(':visible')){
	if ($jq('#interviewDate').val() == '') {
		errorMessage += "Please select interview date.\n";
		if (status) {
			$jq('#interviewDate').focus();
			status = false;
		}
	}
	if ($jq('#venueID').val() == '0' || $jq('#venueID').val() == null) {
		errorMessage += "Please select venue.\n";
		if (status) {
			$jq('#venueID').focus();
			status = false;
		}
	}
}
	if ($jq('#yearID').val() == '0') {
		errorMessage += "Please select year.\n";
		if (status) {
			$jq('#yearID').focus();
			status = false;
		}
	}
	
	
/*	if ($jq("#refSfid").is(':visible') && $jq('#refSfid').val() == '') {
		errorMessage += "Please enter lab representative.\n";
		if (status) {
			$jq('#refSfid').focus();
			status = false;
		}
	}
*/	if ($jq("#boardID").is(':visible') && $jq('#boardID').val() == '0') {
		errorMessage += "Please select board.\n";
		if (status) {
			$jq('#boardID').focus();
			status = false;
		}
	}
	
	$jq("#list tr:not(:first)").each(function() {	
		if ($jq(this).find("td").eq(0).find(":input:checkbox").is(':checked')) {
					// SFID#intwDate#VenueID#DisciplineID#BoardID(or)labRepresentative#Attempts#designationID#departmentID#effectiveDate#variableIncr#reservationID#status#PromotionDate
					rowValues += $jq.trim($jq(this).find("td").eq(1).html())
							+ '#'
							+ $jq('#interviewDate').val()
							+ '#'
							+ $jq('#venueID').val()
							+ '#'
							+ $jq('#venueID').val()
							+'#'
							;
					if ($jq('#assessmentTypeID').val().split('-')[0] == 1) {
						rowValues += $jq('#refSfid').val().toUpperCase() + '#';
					} else {
						rowValues += $jq('#boardID').val() + '#';
					}
					rowValues += $jq.trim($jq(this).find("td").eq(5).html())
							+ '#'
							+ $jq(this).find("td").eq(3).find('div').attr('id')
							+ '#'
							+ $jq(this).find("td").eq(4).find('div').attr('id')+"####57##1"
							+ '#'
							+ '#'+'########1'
							+ '#'
							+$jq('#WrittenDate').val()
							+ '#'
							+$jq('#tradeDate').val()
							+ ',';
					
		}		
	});
	if(rowValues==''){
		errorMessage += "Please check atleast one member.\n";
		if (status) {
			status = false;
		}
	}
	
	if (status) {
		var requestDetails = {
			"assessmentTypeID" :$jq('#assessmentTypeID').val().split('-')[1],
			"designationFrom"  :$jq('#designationFrom').val(), 
			"assessmentCategoryID" : $jq('#assessmentTypeID').val().split('-')[0],
			"yearID" : $jq('#yearID').val(),
			"nodeID" : nodeID,
			"rowValues" : rowValues,
			"param" : "submitQualifiedCandidates"
		};
		$jq.ajax( {
			type : "POST",
			url : "promotion.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				//resetQual();
			}
		});
	} else {
		alert(errorMessage);
	}
}
function resetQualifiedCandidates() {
	$jq('select').val("0");
	$jq('input').val("");
	$jq('#result').text('');
	$jq('#labRepresentativeDiv').hide();
	$jq('#boardDiv').hide();
	$jq('#venueID').find('option').remove().end();
	$jq("#venueID").append($jq("<option></option>").attr("value",'0').text('Select'));
	$jq('#designationFrom').find('option').remove().end();
	$jq("#designationFrom").append($jq("<option></option>").attr("value",'0').text('Select'));
}

//function getAssessementID(id){
//	nodeID += id +",";
//}

//Qualified Candidates for assessment ends

//Promoted Candidates List starts
function searchPromotedCandidates(){
	var errorMessage = "";
	var status = true;
	if ($jq('#assessmentTypeID').val() == '0') {
		errorMessage += "Please select Category type.\n";
		if (status) {
			$jq('#assessmentTypeID').focus();
			status = false;
		}
	}
	/*if ($jq('#assessmentCategoryID').val() == '0') {
		errorMessage += "Please select board type.\n";
		if (status) {
			$jq('#assessmentCategoryID').focus();
			status = false;
		}
	}*/
	if ($jq('#yearID').val() == '0') {
		errorMessage += "Please select year.\n";
		if (status) {
			$jq('#yearID').focus();
			status = false;
		}
	}
	if (status) {
		var requestDetails = {
			"assessmentTypeID" :$jq('#assessmentTypeID').val().split('-')[1],
			"designationFrom"  :$jq('#designationFrom').val(), 
			"assessmentCategoryID" : $jq('#assessmentTypeID').val().split('-')[0],
			"yearID" : $jq('#yearID').val(),
			"param" : "getPromotedCandidates"
		};
		$jq.ajax( {
			type : "POST",
			url : "promotion.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				if ($jq('.success').is(':visible')) {
					resetQualifiedCandidates();
				}
			}
		});
	} else {
		alert(errorMessage);
	}
}

function submitPromotedCandidates() {
	var errorMessage = "";
	var status = true;
	var rowValues = "";

	if ($jq('#assessmentTypeID').val() == '0') {
		errorMessage += "Please select Category type.\n";
		if (status) {
			$jq('#assessmentCategoryID').focus();
			status = false;
		}
	}
	if ($jq('#yearID').val() == '0') {
		errorMessage += "Please select year.\n";
		if (status) {
			$jq('#yearID').focus();
			status = false;
		}
	}
	if ($jq('#status').val() == '0') {
		errorMessage += "Please select status.\n";
		if (status) {
			$jq('#status').focus();
			status = false;
		}
	}
	else if($jq('#status').val() == '53' || $jq('#status').val() == '55'){
	if ($jq('#promotionDate').val() == '') {
		errorMessage += "Please select promotion date.\n";
		if (status) {
			$jq('#promotionDate').focus();
			status = false;
		}
	}	
	if ($jq('#effectiveDate').val() == '') {
		errorMessage += "Please select Variable Increment Start date.\n";
		if (status) {
			$jq('#effectiveDate').focus();
			status = false;
		}
	}
if ($jq('#variableIncr').val() == '') {
		errorMessage += "Please enter variable increment.\n";
		if (status) {
			$jq('#variableIncr').focus();
			status = false;
		}
	} if ($jq('#endingDate').val() == '') {
		errorMessage += "Please select ending date.\n";
		if (status) {
			$jq('#endingDate').focus();
			status = false;
		}
	}

	}
	if ($jq('#reservationDiv').is(':visible') && $jq('#reservationID').val() == '0') {
		errorMessage += "Please select reservation.\n";
		if (status) {
			$jq('#reservationID').focus();
			status = false;
		}
	}
	
	$jq("#list tr:not(:first)")
			.each(function() {
		if ($jq(this).find("td").eq(0).find(":input:checkbox").is(':checked')) {
			// SFID#intwDate#VenueID#BoardID(or)labRepresentative#Attempts#designationID#departmentID#effectiveDate#variableIncr#reservationID#status#promotionDate
			rowValues += $jq.trim($jq(this).find("td").eq(1).html())
					+ '########' + $jq('#effectiveDate').val() + '#'
					+ $jq('#variableIncr').val() + '#';

			if ($jq('#assessmentTypeID').val().split('-')[0] == 2) {
				rowValues += $jq('#reservationID').val() + '#';
			} else {
				rowValues += '#';
			}
			rowValues += $jq('#status').val() + '#'
					+ $jq('#promotionDate').val() + '#1###' 
					+ $jq('#endingDate').val()
                    +'#####1' +',';
		}
	});
	if (rowValues == '') {
		errorMessage += "Please Check any Row .\n";
		if (status) {
			status = false;
		}
	}

	if (status) {
		var requestDetails = {
			"assessmentTypeID" :$jq('#assessmentTypeID').val().split('-')[1],
			"designationFrom"  :$jq('#designationFrom').val(), 
			"assessmentCategoryID" : $jq('#assessmentTypeID').val().split('-')[0],
			"yearID" : $jq('#yearID').val(),
			"rowValues" : rowValues,
			"param" : "submitPromotedCandidates"
		};
		$jq.ajax( {
			type : "POST",
			url : "promotion.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
			$jq('#result').html(html);
			if ($jq('.success').is(':visible')) {
				//resetQual();
			}

			}
		});
	} else {
		alert(errorMessage);
	}
}
function resetPromotedCandidates() {
	$jq('select').val("0");
	$jq('input').val("");
	$jq('#result').text('');
	$jq('#rejectedID').hide();
	$jq('#reservationDiv').hide();
}
//Promoted Candidates List ends

//Pay Fixation starts
function getCategoryPayList(){
	gazetted=$jq('#gazettedType option:selected').val();
	var requestDetails = {
			"assessmentCategoryID" : $jq('#assessmentCategoryID').val(),
			"yearID" : $jq('#yearID').val(),
			"gazettedType" :$jq('#gazettedType').val(),
			"assessmentTypeID":$jq('#assessmentTypeID').val(),
			"param" : "gazetted"
		};
		$jq.ajax( {
			type : "POST",
			url : "promotion.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
           	    $jq('#gazettedType').val(gazetted);
			}
		});

}
function searchPayFixation(){
	var errorMessage = "";
	var status = true;

	if ($jq('#assessmentCategoryID').val() == '0') {
		errorMessage += "Please select board type.\n";
		if (status) {
			$jq('#assessmentCategoryID').focus();
			status = false;
		}
	}
	if ($jq('#yearID').val() == '0') {
		errorMessage += "Please select year.\n";
		if (status) {
			$jq('#yearID').focus();
			status = false;
		}
	}
	if ($jq('#gazettedType').val() == 'Select') {
		errorMessage += "Please select GazettedType.\n";
		if (status) {
			$jq('#gazettedType').focus();
			status = false;
		}
	}
	if (status) {
		var requestDetails = {
			"assessmentCategoryID" : $jq('#assessmentCategoryID').val(),
			"yearID" : $jq('#yearID').val(),
			"gazettedType" :$jq('#gazettedType').val(),
			"param" : "getPayFixation"
		};
		$jq.ajax( {
			type : "POST",
			url : "promotion.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#list').html(html);
				if ($jq('.success').is(':visible')) {
					resetQualifiedCandidates();
				}
			}
		});
	} else {
		alert(errorMessage);
	}
}

//finance acceptance starts
function getDoPartNumbers(){
	$jq('#param').val("doPartList");
	requestDetails={
			"param" : "doPartList",
			"gazettedType":$jq('#gazettedType').val()
	}
	gazetted=$jq('#gazettedType').val();
	typevalue = $jq('#type').val();
	$jq.post('promotion.htm',requestDetails,    
	           function(html){ 
	            	 $jq('#dopart').html(html);
	            	
	            	 $jq('#doPartNumberDiv').show();
	            	
	            
//	            	 getdoPartButton();
	            	
	            	 } 
	      );	
}
function getCasualityList(){
	requestDetails={
			"param" : "casualityList",
			"gazettedType":$jq('#gazettedType').val(),
			"doPartNo":$jq('#doPartNo option:selected').val()
	}
	gazetted=$jq('#gazettedType option:selected').val();
	doNo=$jq('#doPartNo option:selected').val();
	$jq.post('promotion.htm',requestDetails,    
	           function(html){ 
					$jq('#dopart').html('');
	            	 $jq('#dopart').html(html);
	            	 $jq('#gazettedType').val(gazetted);
	            	 $jq('#doPartNo').val(doNo);
	            	 $jq('#casualityDiv').show();
	            	 $jq('#doPartNumberDiv').show();
//            	 getdoPartButton();
	            	// $jq('#EmpDetails').html('');
	            	 } 
	     );	
}
function getEmployeeList() {
	
	var casuality = null;
	var fixationId = 	$jq('#doPartNo option:selected').val().split('#')[2];
	
	var incrementId = 	$jq('#doPartNo option:selected').val().split('#')[3];
	if(fixationId != 0 && incrementId ==0){
	 casuality = 2;
	}else if(fixationId ==0 && incrementId != 0){
		casuality=3;
	}
	
	requestDetails={
		"param" : "EmployeeList",
		"doPartNo":$jq('#doPartNo option:selected').val().split('--')[0].trim().substring(0,3),
		"gazettedType":$jq('#doPartNo option:selected').val().split('--')[1].trim(),
		"casualityId":casuality
		}
		$jq.post('promotion.htm',requestDetails,function(html){
			
		$jq("#result").html(html);
		}
		);
	 

}

function submitFinanceAcceptance(payType){
	status=true;
	var checkBoxstatus=false;
	var errorMessage = "";
	var rowValues="";
	var list="";
	var casuality = null;
	var fixationId = 	$jq('#doPartNo option:selected').val().split('#')[2];
	
	var incrementId = 	$jq('#doPartNo option:selected').val().split('#')[3];
	if(fixationId != 0 && incrementId ==0){
	 casuality = 2;
	}else if(fixationId ==0 && incrementId != 0){
		casuality=3;
	}
	
	
	if(payType=='promotion'){
		list='fixationList';
	}else if(payType=='varIncrement'){
		list='incrementsList';
	}
	if($jq('#financeAcceptedDate').val()==''){
		errorMessage+="Please enter Pay Effective Date\n";
		if(status){
			$jq('#financeAcceptedDate').focus();
		status=false;
		}
	}
	$jq('#'+list+' tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(0).find(":input:checkbox").is(':checked')){
			checkBoxstatus=true;
		}
	});
	if(checkBoxstatus== false){
		errorMessage+="Please select atleast one record to submit\n";
		if(status){
		status=false;
		}
	}
	
	if(status){
		if(payType=='promotion'){
		$jq('#'+list+' tr:not(:first)').each(function(){
			if($jq(this).find('td').eq(0).find(":input:checkbox").is(':checked')){
				rowValues +=$jq(this).find('td').eq(0).find(":input:checkbox").attr('id')+"###"+
				$jq('#financeAcceptedDate').val()+"##1,";
			}
		});}else if(payType=='varIncrement'){
			$jq('#'+list+' tr:not(:first)').each(function(){
				if($jq(this).find('td').eq(0).find(":input:checkbox").is(':checked')){
					rowValues +=$jq(this).find('td').eq(0).find(":input:checkbox").attr('id')+"####"+
					$jq('#financeAcceptedDate').val()+"#1,";
				}
			});}
		requestDetails={
				"param": "submitFinanceAcceptance",
				"casualityId" : $jq('#casualityId option:selected').val(),
				"doPartNo" : $jq('#doPartNo option:selected').val(),
				"financeAcceptedDate" : $jq('#financeAcceptedDate').val(),
				"casualityId":casuality,
				"rowValues" : rowValues
		}
		$jq.post('promotion.htm',requestDetails,function(html){
			$jq('#result').html(html);
			
			
			$jq('#financeAcceptedDate').val('');
		});
	}else{
		alert(errorMessage);
	}
}


//function getGazettedTypeDoList() {
//	var status=true;
//	var gazID=$jq('#gazettedType').val();
//	if(gazID=="0")
//		status=false;
//	if(status){
//	  $jq.post(
//		'promotion.htm?param=getGazettedTypeDoList&gazettedType='+gazID,
//		 function(data){
//			$jq('#result').html(data);
//			$jq('#casualityDiv').show();
//			$jq('#doPartNumberDiv').show();
//			
//		}
//	  );
//	}
//}

function getDOPartNumList(){
	//$jq('select').val("0");
	$jq("#doPartNumber").val("0");
	$jq('#doPartNumber').find('option').remove().end();
	$jq("#doPartNumber").append($jq("<option></option>").attr("value",'0').text('Select'));
	for(var i=0;i<jsonArrayObject.length;i++){
		$jq('#doPartNumber').append($jq("<option></option>").attr("value",jsonArrayObject[i].id).text(jsonArrayObject[i].doPartNumber +'----'+ jsonArrayObject[i].dOPartDate));
	}
}

function getDOPartNoDetails(){
	var status=true;
	var doPartNo=$jq('#doPartNumber').val();
	if(doPartNo=="0")
		status=false;
	if(status){
	  $jq.post(
		'promotion.htm?param=getPublishedDoList&doPartNumber='+doPartNo,
		 function(data){
			$jq('#result1').html(data);
			
		}
	  );
	}
}


function clearFinanceAccept(){
	$jq('select').val("0");
	$jq('#casualityDiv').hide();
	$jq('#doPartNumberDiv').hide();
//	$jq("input:radio").attr("checked", false);
//	$jq('input').val('');
}

function checkRunmonthDate(runmonth){
	var runOriginalDate=runmonth.split(" ")[0];
	var financeAcceptedDate = $jq("#financeAcceptedDate").val();
	var runmonthDate = new Date(runOriginalDate.split("-")[0],runOriginalDate.split("-")[1]-1, runOriginalDate.split("-")[2], 0, 0, 1, 0);
	var date2 = new Date(financeAcceptedDate.split("-")[2], getMonthID(financeAcceptedDate.split("-")[1]) - 1, financeAcceptedDate.split("-")[0], 0, 0, 1, 0);
	if (runmonthDate >= date2) {
		runString= runmonthDate+'';
		alert("Paybill Of "+runString.split(" ")[1] +" "+runString.split(" ")[3] +" month is already generated please select next month date.\n");
		$jq("#financeAcceptedDate").val('');
	}
}

//finance acceptance ends

function submitPayFixation(){
	var errorMessage = "";
	var status = true;
	var rowValues = "";

	if ($jq('#assessmentCategoryID').val() == '0') {
		errorMessage += "Please select board type.\n";
		if (status) {
			$jq('#assessmentCategoryID').focus();
			status = false;
		}
	}
	if ($jq('#assessmentCategoryID').val().split('-')[0] == '0') {
		errorMessage += "Please select year.\n";
		if (status) {
			$jq('#yearID').focus();
			status = false;
		}
	}
	if ($jq('#casualityId').val() == '0') {
		errorMessage += "Please select Casuality.\n";
		if (status) {
			$jq('#casualityId').focus();
			status = false;
		}
	}
	if ($jq('#doPartNumber').val() == 'select' || $jq('#doPartNumber').val() == '') {
		errorMessage += "Please select DO-Part number.\n";
		if (status) {
			$jq('#doPartNumber').focus();
			status = false;
		}
	}

	if ($jq('#serialNumber').val() == '') {
		errorMessage += "Please enter SerialNumber.\n";
		if (status) {
			$jq('#serialNumber').focus();
			status = false;
		}
	}

/*	if ($jq('#doPartDate').val() == '') {
		errorMessage += "Please select DO-Part date.\n";
		if (status) {
			$jq('#doPartDate').focus();
			status = false;
		}
	}
*/	var i=1;
	$jq("#fixationList tr:not(:first)").each(function() {
		if ($jq(this).find("td").eq(0).find(":input:checkbox").is(':checked')) {
			if($jq(this).find("td").eq(9).find(":input").val() == '' || $jq(this).find("td").eq(10).find(":input").val() == '' || $jq(this).find("td").eq(11).find(":input").val() == ''){
				errorMessage += "Please enter Pay Details in "+ i+" row.\n";
			if (status) {
				$jq('#doPartDate').focus();
				status = false;	
			}
		}
		}
		i++;
	});	
	$jq("#fixationList tr:not(:first)").each(function() {
		if ($jq(this).find("td").eq(0).find(":input:checkbox").is(':checked')) {
			// PayFixationID#AssessmentID#SFID#newBasicPay#newGradePay#twoAddIO
			var id = $jq(this).find("td").eq(0).find(":input:checkbox").attr(
					'id').split('#');
			if (!($jq(this).find("td").eq(8).find(":input").val() == ''
					|| $jq(this).find("td").eq(9).find(":input").val() == ''
					|| $jq(this).find("td").eq(10).find(":input").val() == '')) {
				rowValues += id[0] + '#' + id[1] + '#'
						+ $jq.trim($jq(this).find("td").eq(1).html()) + '#'
						+ $jq(this).find("td").eq(8).find(":input").val() + '#'
						+ $jq(this).find("td").eq(9).find(":input").val() + '#'
						+ $jq(this).find("td").eq(10).find(":input").val()+ '#'
						+ $jq.trim($jq(this).find("td").eq(6).html())+ '#'
						+ $jq(this).find("td").eq(3).find('div').attr('id') +'#'
						+ $jq(this).find("td").eq(11).find(":input").val()+ '#'
						+ $jq(this).find("td").eq(12).find('div').attr('id')+ '#'
						+ $jq(this).find("td").eq(13).find(":input").val()+ '#'                       //This line added for variable increment
						+'######'+$jq('#doPartNumber').val()+'##1'
						+ ',';
			}
		}
	});
	$jq("#incrementsList tr:not(:first)").each(function() {
		if ($jq(this).find("td").eq(0).find(":input:checkbox").is(':checked')) {
			// PayFixationID#AssessmentID#SFID#newBasicPay#newGradePay#twoAddIO
			var id = $jq(this).find("td").eq(0).find(":input:checkbox").attr(
					'id').split('#');
			if (!($jq(this).find("td").eq(7).find(":input").val() == ''))
			{
				rowValues += id[0] + '#' + id[1] + '#'
						+ $jq.trim($jq(this).find("td").eq(1).html()) + '#'
						+ '####' + $jq(this).find("td").eq(3).find('div').attr('id') +'#'
						+'######'+ $jq(this).find("td").eq(6).text()+ '#'
						+ $jq(this).find("td").eq(7).text()+ '##'
						+$jq('#doPartNumber').val()
						+ ',';
			}
		}
	});

	if (rowValues == '') {
		errorMessage += "Please enter pay details.\n";
		if (status) {
			status = false;
		}
	}
	if (status) {
		var requestDetails = {
				"assessmentTypeID" : $jq('#assessmentCategoryID').val().split('-')[1],
				"assessmentCategoryID": $jq('#assessmentCategoryID').val().split('-')[2],
				"yearID" :  $jq('#assessmentCategoryID').val().split('-')[0],
			"serialNumber" : $jq('#serialNumber').val(),
			"casualityId" : $jq('#casualityId').val(),
			"doPartNumber" : $jq('#doPartNumber').val(),
			"doPartDate" : $jq('#doPartDate').val(),
			"gazettedType" :$jq('#gazettedType').val(),
			"rowValues" : rowValues,
			"param" : "submitPayFixation"
		};
		$jq.ajax( {
			type : "POST",
			url : "promotion.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
			$jq('#paylist').html(html);
			$jq('#payFixationDiv').css("display","block");
			if ($jq('.success').is(':visible')) {
				resetQual();
				$jq('#casualityDiv').hide();
				$jq('#doPartNumberDiv').hide();
				
			}

			}
		});
	} else {
		alert(errorMessage);
	}
}
function getDateWiseDoPart(){
	$jq('#param').val('getPayFixation');
	gazetted=$jq('#gazettedType option:selected').val();
	$jq.post('promotion.htm',$jq('#PromotionManagementBean').serialize(),    
	           function(html){ 
	            	 $jq('#list').html(html);
	            	 $jq('#gazettedType').val(gazetted);
	            	// $jq('#casualityDiv').show();
	            	 } 
	      );	
	
}
function resetPayFixation() {
	$jq('select').val("0");
	$jq('input').val("");
	$jq('#result').text('');
	$jq('#doPartNo').find('option').remove().end();
	$jq("#doPartNo").append($jq("<option></option>").attr("value",'').text('Select'));	
}
function payFixationCCS(sfID){
	window.open('./report.htm?param=promotionIONReports&type=payCCS&sfid='+sfID,'pay fixation','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	//window.open('./report.htm?param=promotionReports&type=PayFixation&sfid='+sfID,'pay fixation','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}

function getPromotionSearchList(){
	var casId=$jq('#casualityId').val();
	//getGazettedTypeList();
	$jq('#casualityId').val(casId);
	var code= $jq('#casualityId').val().split('#')[1]
	if (code==3) {
		$jq('#payFixationDiv').hide();
		$jq('#variableIncrementsDiv').show();
	} else if(code==2){
		$jq('#variableIncrementsDiv').hide();
		$jq('#payFixationDiv').show();
	}
	else {
		$jq('#payFixationDiv').hide();
		$jq('#variableIncrementsDiv').show();
	} 
}

function getPromotionSearchList1(){
	var code= $jq('#casualityId').val().split('#')[1]
	if (code==3) {
		$jq('#payFixationDiv').hide();
		$jq('#variableIncrementsDiv').show();
		//$jq('#doPartNumberDiv').show();
		
	} else if(code==2){
		$jq('#variableIncrementsDiv').hide();
		$jq('#payFixationDiv').show();
		//$jq('#doPartNumberDiv').show();
	}
	else {
		$jq('#payFixationDiv').hide();
		$jq('#variableIncrementsDiv').show();
		//$jq('#doPartNumberDiv').show();
	} 
}

//Pay Fixation ends

//Optional Certificate starts
function submitOptionalCert() {
	var errorMessage = "";
	var status = true;
	var sfid ="";

	if($jq('#lowergarde').is(':visible')){
	
	if ($jq('#scaleOfPay').val() == '') {
		errorMessage += "Please enter scale of pay.\n";
		if (status) {
			status = false;
			$jq('#scaleOfPay').focus();
		}
	}
	if ($jq('#incrementDate').val() == '') {
		errorMessage += "Please select next increment date.\n";
		if (status) {
			status = false;
			$jq('#incrementDate').focus();
		}
	}}
	if(!$jq('input:radio[name=payUpdate]').is(':checked')){
		errorMessage += "Please Select Either Lower Grade or Higher Grade.\n";
		status = false;
	}
	if($jq('#savedSfid').val() !=0){
	  sfid = $jq('#savedSfid').val() ;
	}else if($jq('#unsavedSfid').val() != 0){
	  sfid = $jq('#unsavedSfid').val();
	}else{
	   sfid="";
	 }
	if (status) {
		var assessmentCategoryID = $jq('#assessmentCategoryID').val();
		var requestDetails = {
			"nodeID" : nodeID,
			"scaleOfPay" : $jq('#scaleOfPay').val(),
			"incrementDate" : $jq('#incrementDate').val(),
			"nodeID" : nodeID,
			"sfID":sfid,
			"payUpdate":$jq('input:radio[name=payUpdate]:checked').val(),
			"param" : "submitOptCert",
			"yearID":  $jq('#assessmentCategoryID').val().split('-')[0]
		};
		$jq.ajax( {
			type : "POST",
			url : "promotionReq.htm?"+dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				if ($jq('.success').is(':visible')) {
					$jq('#assessmentCategoryID').val(assessmentCategoryID);
					$jq('#unsavedSfid').find('option').each(function(){ 
						if(this.value== sfid) 
							this.remove();
					});
				}

			}
		});
	} else {
		alert(errorMessage);
	}
}

function resetOptionalCert(){
	nodeID="";
	$jq('input:text').val('');
	
	$jq('input:radio').attr("checked",false);
	$jq('select').val(0)
}

function editOptionalCertificate(id,payStatus,incrDate,scaleOfPay){
	nodeID = id;
	$jq("#payUpdate[value='" +payStatus + "']").attr('checked', true);
	$jq('#scaleOfPay').val(scaleOfPay);
	$jq('#incrementDate').val(incrDate);
	if(payStatus == 2){
	  $jq('#lowergarde').show();
	}
}
function deleteOptionalCertificate(id){
	var requestDetails = {
			"nodeID" : id,
			"param" : "deleteOptCert"
		};
var resp=confirm("Do you want to delete this Record?");
	
	if(resp ==  true){
		$jq.ajax( {
			type : "POST",
			url : "promotionReq.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
			}
		});
	}
}

function optionalCertificate(id){
	window.open('./report.htm?param=payFixationReport&type=optCert&requestID='+id,'Optional Certificate','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}
//Optional Certificate ends

function joiningReport(id){
	window.open('./report.htm?param=payFixationReport&type=joinReport&requestID='+id,'Joining Report','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}

//Pay Fixation DO Part Starts
function searchPayFixationDOPart() {
	var errorMessage = "";
	var status = true;

	if ($jq('#doPartNumber').val() == '' && $jq('#doPartDate').val() == '') {
		errorMessage += "Please enter DO-Part number or DO-Part Date";
		if (status) {
			$jq('#doPartNumber').focus();
			status = false;
		}
	}
	if (status) {
		var requestDetails = {
			"doPartNumber" : $jq('#doPartNumber').val(),	
			"doPartDate" : $jq('#doPartDate').val(),
			"param" : "getPayFixationDOPart"
		};
		$jq.ajax( {
			type : "POST",
			url : "promotion.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				$jq('input').val("");
			}
		});
	} else {
		alert(errorMessage);
	}

}
function resetPayFixationDOPart() {
	$jq('input').val("");
	$jq('#result').text('');
}
function viewPayFixationDOPart(ID){
	window.open('./report.htm?param=payFixationReport&type=DOPart&requestID='+ID,'Pay Fixation DOPart','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}

//Pay Fixation DO Part Ends

//View Optional Certificate starts
function searchViewOptionalCertificate(){
	var errorMessage = "";
	var status = true;

	
	if ($jq('#assessmentTypeID').val() == 0) {
		errorMessage += "Please Select  Category Type.\n";
		if (status) {
			status = false;
			$jq('#assessmentTypeID').focus();
		}
	}
	if ($jq('#assessmentCategoryID').val() == 0) {
		errorMessage += "Please Select  BoardType.\n";
		if (status) {
			status = false;
			$jq('#assessmentCategoryID').focus();
		}
	}
	if ($jq('#yearID').val() == 0) {
		errorMessage += "Please Select Year.\n";
		if (status) {
			status = false;
			$jq('#yearID').focus();
		}
	}
	
	if (status) {
		var requestDetails = {
			"type"  :"viewOtionalCertificates",
			"param" : "getOptionalCertificates",
			"assessmentTypeID" : $jq('#assessmentCategoryID').val().split('-')[1],
			"assessmentCategoryID": $jq('#assessmentCategoryID').val().split('-')[2],
			"yearID" :  $jq('#assessmentCategoryID').val().split('-')[0],
		};
		$jq.ajax( {
			type : "POST",
			url : "promotion.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
			$jq('#result').html(html);		
			
		}
		});
	} else {
		alert(errorMessage);
	}

}
function resetViewOptionalCertificate(){
	
	$jq('select').val("0");
	$jq('#result').text('');
	
}
//View Optional Certificate Ends

function searchHQCandidates() {
	var errorMessage = "";
	var status = true;
	if ($jq('#assessmentTypeID').val() == '0') {
		errorMessage += "Please select Category type.\n";
		if (status) {
			$jq('#assessmentTypeID').focus();
			status = false;
		}
	}

	if ($jq('#assessmentCategoryID').val() == '0') {
		errorMessage += "Please select Board type.\n";
		if (status) {
			$jq('#assessmentCategoryID').focus();
			status = false;
		}
	}
	if ($jq('#assessmentDate').val() == '') {
		errorMessage += "Please select Assessment Date.\n";
		if (status) {
			$jq('#assessmentDate').focus();
			status = false;
		}
	}
	if ($jq('#yearID').val() == '0') {
		errorMessage += "Please select Year .\n";
		if (status) {
			$jq('#assessmentDate').focus();
			status = false;
		}
	}

   if (status) {
		var requestDetails = {
			"assessmentTypeID" :$jq('#assessmentTypeID').val(),
			"designationFrom"  :$jq('#designationFrom').val(), 
			"assessmentCategoryID" : $jq('#assessmentCategoryID').val(),
			"yearID" : $jq('#yearID').val(),
			"assessmentDate" :$jq('#assessmentDate').val(),
			"param" : "getHQCandidates"
		};
		$jq.ajax( {
			type : "POST",
			url : "promotion.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				if ($jq('.success').is(':visible')) {
					resetQualifiedCandidates();
				}
			}
		});
	} else {
		alert(errorMessage);
	}
}

function submitHQCandidates() {
	var errorMessage = "";
	var status = true;
	var rowValues = "";
	
	if ($jq('#assessmentCategoryID').val() == '0') {
		errorMessage += "Please select board type.\n";
		if (status) {
			$jq('#assessmentCategoryID').focus();
			status = false;
		}
	}
	if ($jq('#yearID').val() == '0') {
		errorMessage += "Please select year.\n";
		if (status) {
			$jq('#yearID').focus();
			status = false;
		}
	} 
	var i=1;
	$jq("#list tr:not(:first)").each(function() {
		if ($jq(this).find("td").eq(11).find("select").val() != '0') {
			if ($jq(this).find("td").eq(8).find("select").val() == '0'){
				status = false;
				errorMessage += "Please select Discipline in "+i+"th row.\n";
			}
		}
		i++;
	});

	$jq("#list tr:not(:first)").each(function() {	
		if ($jq(this).find("td").eq(11).find("select").val() != '0') {
			// SFID#intwDate#VenueID#DesciplineID#BoardID(or)labRepresentative#Attempts#designationID#departmentID#effectiveDate#variableIncr#reservationID#status#PromotionDate
			rowValues += $jq.trim($jq(this).find("td").eq(0).html()) + "#####"
					+ $jq(this).find("td").eq(10).find("input:text").val() + '#'
					
					+ $jq(this).find("td").eq(2).find('div').attr('id') + '#'
					+ $jq(this).find("td").eq(3).find('div').attr('id')
					+ "####" + $jq(this).find("td").eq(11).find("select").val()
					+ "##1####1"	
			+ '#'
			+ $jq(this).find("td").eq(8).find("select").val()
			+ '#'
			+ $jq(this).find("td").eq(9).find("select").val()
			+'#1#######'
			+ $jq(this).find("td").eq(10).find("input:hidden").val() + '#' 
			+ ',';

			
		}		
	});
	if(rowValues==''){
		errorMessage += "Please select status.\n";
		if (status) {
			status = false;
		}
	}
	
	if (status) {
		var requestDetails = {
			"assessmentTypeID" :$jq('#assessmentTypeID').val(),
			"designationFrom"  :$jq('#designationFrom').val(), 
			"assessmentCategoryID" : $jq('#assessmentCategoryID').val(),
			"yearID" : $jq('#yearID').val(),
			"assessmentDate" :$jq('#assessmentDate').val(),
			"nodeID" : nodeID,
			"rowValues" : rowValues,
			
			"param" : "submitHQCandidates"
		};
		$jq.ajax( {
			type : "POST",
			url : "promotion.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				if ($jq('.success').is(':visible')) {
					//resetQual();
				}
			}
		});
	} else {
		alert(errorMessage);
	}
}

function enableCheckBox1(id,residencyId){
	var temp2 = $jq('#list').find('input:checked').parent().parent().find('td').eq(9).find('input:hidden').val();
	var temp = $jq('#list').find('input:checked').length;
	
	if(temp2 == null || temp2==''){
		temp2 = $jq('#list').find('input:checked').val();
	}
	

	
		if(temp == 0){
			$jq('#check'+id).removeAttr('disabled');
			//$jq('#check'+id).attr('checked', true);	
			
			
			for(i=0;i<residencyIdJSON.length;i++){
				  
				if(residencyIdJSON[i].id==residencyId){
					if(residencyIdJSON[i].written == 1){
					  $jq('#written').show()
					}
					if(residencyIdJSON[i].trade == 1){
						  $jq('#trade').show();
					}
					if(residencyIdJSON[i].interview == 1){
						  $jq('#interview').show();
					}
			    	}
			    }
		}else if (temp2 == residencyId) {
     	$jq('#check'+id).removeAttr('disabled');
     	//$jq('#check'+id).attr('checked', true);
     	for(i=0;i<residencyIdJSON.length;i++){
     		  
    		if(residencyIdJSON[i].id==residencyId){
    			if(residencyIdJSON[i].written == 1){
    			  $jq('#written').show()
    			}
    			if(residencyIdJSON[i].trade == 1){
    				  $jq('#trade').show();
    			}
    			if(residencyIdJSON[i].interview == 1){
					  $jq('#interview').show();
				}
    	    	}
    	    }
         }else{
        	 $jq('#check'+id).attr('checked', false);	
          alert("You Can't Edit Differnt Designation At A Time")
         }
	
}

function enableCheckBox(id){
	$jq('#check'+id).removeAttr('disabled');
}

function enbleDependentQualifiedCan() {
	 var assessmentCategoryId = $jq('#assessmentTypeID').val().split('-')[1];
	
	if (assessmentCategoryId == 1) {
		$jq('#labRepresentativeDiv').show();
		$jq('#boardDiv').hide();
	} else if (assessmentCategoryId == 2 || assessmentCategoryId == 3|| assessmentCategoryId == 4) {
		$jq('#labRepresentativeDiv').hide();
		$jq('#boardDiv').show();
	} else {
		$jq('#labRepresentativeDiv').hide();
		$jq('#boardDiv').hide();
	}
	getBoardDetails();
}
function enbleReservation() {
	if ($jq('#assessmentCategoryID').val() == 2) {
		$jq('#reservationDiv').show();
	} else {
		$jq('#reservationDiv').hide();
	}
}
function disableDates(){
	if($jq('#assessmentCategoryID').val() != 2){
	if ($jq('#status').val() == 54 || $jq('#status').val() == 0 || $jq('#status').val() == 62) {
		$jq('#promotionDate').val("");
		$jq('#effectiveDate').val("");
		$jq('#variableIncr').val("");
		$jq('#rejectedID').hide();
	} else {
		$jq('#rejectedID').show();
	}
	}
	else if($jq('#assessmentCategoryID').val() == 2){
		if ($jq('#status').val() == 54 || $jq('#status').val() == 0) {
			$jq('#promotionDate').val("");
			$jq('#effectiveDate').val("");
			$jq('#variableIncr').val("");
			$jq('#reservationID').val("0");
			$jq('#rejectedID').hide();
			$jq('#reservationDiv').hide();
		} else {
			$jq('#reservationDiv').show();
			$jq('#rejectedID').show();
		}
	}

}
function getCategoryDesignationList(){
	if($jq('#assessmentTypeID').is(':visible')){
	if($jq('#assessmentTypeID').val().contains('-')==true){
		var assessmentTypeID=$jq('#assessmentTypeID').val().split('-')[1];
	}else{
		var assessmentTypeID=$jq('#assessmentTypeID').val();
	}
	}
	
	$jq('#designationFrom').find('option').remove().end();
	$jq("#designationFrom").append($jq("<option></option>").attr("value",'0').text('Select'));
	for(i=0;i<designationListJSON.length;i++){
		if(designationListJSON[i].id==assessmentTypeID){
			$jq('#designationFrom').append($jq("<option></option>").attr("value",designationListJSON[i].desigID).text(designationListJSON[i].name));
	}
	}
}

//Promotion Report starts
function resetPromotionReoprt(){
	$jq('select').val("0");
	$jq("input:radio").attr("checked", false);
	$jq('input').val('');
}
function submitPromotionReport(){
	var errorMessage = "";
	var status = true;
	var type="";
	
	if (!$jq('input:radio[name=reportType]').is(':checked')) {
		errorMessage += "Please select Report Type\n";
		status=false;
	}
	else
	{
		type=$jq('input:radio[name=reportType]:checked').val();
	}
	if ($jq('#assessmentCategoryID').val() == "0") {
		errorMessage += "Please select assessmentType\n";
		status = false;
		$jq('#assessmentCategoryID').focus();
	}
	if ($jq('#yearID').val() =="0") {
		errorMessage += "Please select Year\n";
		status = false;
		$jq('#yearID').focus();
	}
	if (status) {
		window
		.open(
				"./report.htm?param=promotionReports&returnValue=report&type="
						+type+"&assessmentCategoryID="+document.getElementById('assessmentCategoryID').value
						+"&yearID="+$jq('#yearID').val(),
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}
	else {
		alert(errorMessage);
	}

}


function resetHQPromotion(){
	$jq('#assessmentTypeID').val("0");
	$jq('#assessmentCategoryID').val("0");
}

function resetQual(){
	$jq('#boardID').find('option').remove().end();
	$jq('#venueID').find('option').remove().end();
	
	//if(document.forms[0].param.value != "qualifiedCandidatesHome"){
	//$jq('#assessmentCategoryID').val("1");
	///else{
		$jq('#assessmentCategoryID').val("0");
	$jq('#yearID').val(yearID),
	$jq('#assessmentTypeID').val("0"),
	$jq('#designationFrom').val("0"), 
	$jq('#assessmentDate').val(""),
	$jq('#interviewDate').val("");
	$jq('#labRepresentative').val("");
	$jq('#status').val("0");
	$jq('#promotionDate').val("");
	$jq('#effectiveDate').val("");
	$jq('#variableIncr').val("");
	$jq("#boardID").append($jq("<option></option>").attr("value",'0').text('Select'));
	$jq("#venueID").append($jq("<option></option>").attr("value",'0').text('Select'));
	$jq("#gazettedType").val('Select');
	$jq("#doPartDate").val('');
	$jq("#doPartNo").val('');
	$jq("#doPartNumber").val('');
	$jq('#reservationID').val("0");
	$jq('#yearID').val("0");
	$jq('#doPartNo').find('option').remove().end();
	$jq("#doPartNo").append($jq("<option></option>").attr("value",'').text('Select'));
	getCategoryDesignationList();
}
function getBoardDetails(){
	$jq('#boardID').find('option').remove().end();
	$jq('#venueID').find('option').remove().end();
	var yearId=$jq("#yearID").val();
//	var assessmentID=$jq("#assessmentCategoryID").val();
	if($jq("#assessmentTypeID").val().contains('-')==true){
		var assessmentID=$jq("#assessmentTypeID").val().split('-')[1];
	}else{
		var assessmentID=$jq("#assessmentTypeID").val();
	}
	if(boardMasterJSON!=null){
		for(i=0;i<boardMasterJSON.length;i++){
			if(boardMasterJSON[i].yearID==yearId){
				$jq('#boardID').append($jq("<option></option>").attr("value",boardMasterJSON[i].id).text(boardMasterJSON[i].name));
			}
		}
	}
	if(venueMasterJSON!=null){
		for(j=0;j<venueMasterJSON.length;j++){
			if(venueMasterJSON[j].yearID==yearId && venueMasterJSON[j].assessmentCategoryID==assessmentID){
				$jq('#venueID').append($jq("<option></option>").attr("value",venueMasterJSON[j].id).text(venueMasterJSON[j].center));
			}
		}
	}
}
//option Certificate report

function getCertificate(){
		window.open("./report.htm?param=optionCertificateReport",'preview',	
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
}

function checkBoxPromotionCheckAll(type,rowType){
	if($jq('input:checkbox[name='+type+']').is(':checked')) {
		
		if(!$jq('.'+rowType)[0].disabled){
		$jq('.'+rowType).attr('checked', 'checked');
	}
	}else {
		 $jq("."+rowType).removeAttr("checked");
	}
	
	$jq('#list >tbody >tr').each(function(){
		if($jq(this).find('td').eq(0).find('input').is(":disabled"))
			$jq(this).find('td').eq(0).find('input').removeAttr("checked");
	});
	
}
function getPromotionReport(type){
	var errorMessages="";
	var status=true;
	var type=type;
	if($jq('#assessmentTypeID').val()=='0'){
		errorMessages += "Please select Category Type.\n";
		if(status){
			$jq('#assessmentTypeID').focus();
			status = false;
		}
	}
	
	if($jq('#assessmentCategoryID').val()=='0'){
		errorMessages += "Please select board type.\n";
		if(status){
			$jq('#assessmentCategoryID').focus();
			status = false;
		}
	}
	if($jq('#yearID').val()=='0'){
		errorMessages += "Please select year.\n";
		if(status){
			$jq('#yearID').focus();
			status = false;
		}
	}
	
       var mainJson={};
       
	if($jq('#designationFrom').val()=='0'){
		$jq('#designationFrom').find('option').each(function(){
			var tempJson={};
		 tempJson['designation'] = $jq(this).val();
		
		});   
		 mainJson["tempJson"]=tempJson;
	}else{
		var tempJson={};
		tempJson['designation'] = $jq('#designationFrom').val();
		mainJson["tempJson"]=tempJson;
	}
	
	
	
	if(type=='aslEligible'){
		if($jq('#assessmentDate').val()==''){
		errorMessages += "Please select assessment Date.\n";
		if(status){
			$jq('#assessmentDate').focus();
			status = false;
		}
		}
	}
	if (status) {
		
		window
		.open(
				"./report.htm?param=promotionReports&returnValue=report&type="
	+type+"&assessmentCategoryID="+document.getElementById('assessmentCategoryID').value
	+"&yearID="+$jq('#yearID').val()+"&assessmentDate="+$jq('#assessmentDate').val()+"&assessmentTypeID="+$jq('#assessmentTypeID').val()+"&jsonValue="+JSON.stringify(mainJson),
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}
	else{
		alert(errorMessages);
	}
}
function getResidencyPeriodReport(){
	window.open('./report.htm?param=promotionReports&type=residencyPeriodMaster&sfid=,residency period,fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
}
function getPromotionIONReport(type){
	var errorMessages="";
	var status=true;
	var type=type;
	if($jq('#assessmentCategoryID').val()=='0'){
		errorMessages += "Please select board type.\n";
		if(status){
			$jq('#assessmentCategoryID').focus();
			status = false;
		}
	}
	if($jq('#yearID').val()=='0'){
		errorMessages += "Please select year.\n";
		if(status){
			$jq('#yearID').focus();
			status = false;
		}
	}
	if(type=='sendToHQ' && $jq('#assessmentDate').val()==''){
		errorMessages += "Please select assessment Date.\n";
		if(status){
			$jq('#assessmentDate').focus();
			status = false;
		}
	}
	if (status) {
		
		window
		.open(
				"./report.htm?param=promotionIONReports&returnValue=report&type="
						+type+"&assessmentCategoryID="+document.getElementById('assessmentCategoryID').value
						+"&yearID="+$jq('#yearID').val()+"&assessmentDate="+$jq('#assessmentDate').val(),
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}
	else{
		alert(errorMessages);
	}
}

//Promotion Report Ends

//Promotion Discipline Starts
function promotionDisciplineSubmit(){
	var errorMessage = "";
	var status = true;
	if ($jq('#name').val()=='') {
		errorMessage += "Please Enter Discipline Name\n";
		$jq('#name').focus();
		status = false;
	}
	if ($jq('#shortForm').val()=='') {
		errorMessage += "Please Enter Discipline Code\n";
		if (status) {
			document.getElementById('shortForm').focus();
			status = false;
		}
	}
	if ($jq('#assessmentTypeID').val() == '0') {
		errorMessage += "Please select board type.\n";
		if (status) {
			$jq('#assessmentTypeID').focus();
			status = false;
		}
	}
	if (status) {
		var requestDetails = {
				"nodeID" : nodeID,
				"name" : $jq('#name').val(),
				"shortForm" : $jq('#shortForm').val(),
				"description" : $jq('#description').val(),
				"assessmentTypeID" : $jq('#assessmentTypeID').val(),
				"param" : "managePromotionDiscipline"
			};
			$jq.ajax( {
				type : "POST",
				url : "promotion.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#result').html(html);
					if ($jq('.success').is(':visible')) {
						clearPromotionDiscipline();
					}
				}
			});

	} else {
		alert(errorMessage);
	}

}
function clearPromotionDiscipline(){
	nodeID="";
	$jq('#name').val('');
	$jq('#shortForm').val('');
	$jq('#description').val('');
	$jq('#counter').val('');
	$jq('#assessmentTypeID').val('0');
}
function editPromotionDiscipline(id,name,shortForm,Description,assessmentTypeID){
	nodeID=id;
	$jq('#name').val(name);
	$jq('#shortForm').val(shortForm);
	$jq('#description').val(Description);
	$jq('#assessmentTypeID').val(assessmentTypeID);
}
function deleteDisciplineDetails(id) {
	var requestDetails = {
		"nodeID" : id,
		"param" : "deleteDisciplineMaster"
	};
var resp=confirm("Do you want to delete this Record?");
	
	if(resp ==  true){
	$jq.ajax( {
		type : "POST",
		url : "promotion.htm",
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq('#result').html(html);
			if ($jq('.success').is(':visible')) {
				//clearPromotionDiscipline();
			}
		}
	});
	}
}

//Promotion Discipline Ends

//Promotion Sub Discipline Starts
function promotionSubDisciplineSubmit(){
	var errorMessage = "";
	var status = true;
	
		errorMessage += "Please select Discipline\n";
		$jq('#name').focus();
		status = false;
	
	if ($jq('#subName').val()=='') {
		errorMessage += "Please Enter Sub Discipline Name\n";
		if (status) {
			document.getElementById('subName').focus();
			status = false;
		}
	}
	if ($jq('#shortForm').val()=='') {
		errorMessage += "Please Enter Sub Discipline Code\n";
		if (status) {
			document.getElementById('shortForm').focus();
			status = false;
		}
	}

	if (status) {
		var requestDetails = {
				"nodeID" : nodeID,
				"name" : $jq('#name').val(),
				"subName" :$jq('#subName').val(),
				"shortForm" : $jq('#shortForm').val(),
				"description" : $jq('#description').val(),
				"param" : "managePromotionSubDiscipline"
			};
			$jq.ajax( {
				type : "POST",
				url : "promotion.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#result').html(html);
					if ($jq('.success').is(':visible')) {
						clearPromotionSubDiscipline();
					}
				}
			});

	} else {
		alert(errorMessage);
	}
}

function clearPromotionSubDiscipline(){
	nodeID="";
	$jq('#name').val(0);
	$jq('#subName').val('');
	$jq('#shortForm').val('');
	$jq('#description').val('');
	$jq('#counter').val('');
}
function editPromotionSubDiscipline(id,name,subName,shortForm,Description){
	nodeID=id;
	$jq('#name').val(name);
	$jq('#subName').val(subName);
	$jq('#shortForm').val(shortForm);
	$jq('#description').val(Description);
}
function deleteSubDisciplineDetails(id) {
	var requestDetails = {
		"nodeID" : id,
		"param" : "deleteSubDisciplineMaster"
	};
var resp=confirm("Do you want to delete this Record?");
	
	if(resp ==  true){
	$jq.ajax( {
		type : "POST",
		url : "promotion.htm",
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq('#result').html(html);
			if ($jq('.success').is(':visible')) {
				//clearPromotionSubDiscipline();
			}
		}
	});
	}
}

//Promotion Sub Discipline Ends

//Promotion Add external employee
function submitExternalEmp(){
var errorMessage = "";
var status = true;
if ($jq('#name').val()=='0') {
	errorMessage += "Please enter Name\n";
	$jq('#name').focus();
	status = false;
}
if ($jq('#designation').val()=='') {
	errorMessage += "Please Enter Designation\n";
	if (status) {
		document.getElementById('designation').focus();
		status = false;
	}
}

if (status) {
	var requestDetails = {
			"nodeID" : nodeID,
			"name" : $jq('#name').val(),
			"desigName" :$jq('#desigName').val(),
			"param" : "manageExternalBoardMember"
		};
		$jq.ajax( {
			type : "POST",
			url : "promotion.htm?"+dispUrl,
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				if ($jq('.success').is(':visible')) {
					
				}
			}
		});

} else {
	alert(errorMessage);
}
}
function deleteExternalBoardMember(id) {
	var requestDetails = {
		"nodeID" : id,
		"param" : "deleteExternalBoardMember"
	};
var resp=confirm("Do you want to delete this Record?");
	
	if(resp ==  true){
	$jq.ajax( {
		type : "POST",
		url : "promotion.htm",
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq('#result').html(html);
			if ($jq('.success').is(':visible')) {
				clearPromotionSubDiscipline();
			}
		}
	});
	}
}
function editExternalBoardMember(id,name,desigName){
	nodeID=id;
	$jq('#name').val(name);
	$jq('#desigName').val(desigName);
}
//Promotion Add external employee

//Promotion Offline Entry Screen Starts
function submitOfflineEntryDetails(){
	var errorMessage = "";
	var status = true;
	if ($jq('#refSfid').val()=='') {
		errorMessage += "Please Enter SFID\n";
		if (status) {
			document.getElementById('refSfid').focus();
			status = false;
		}
	}
	if ($jq('#designationTo').val()=='0') {
		errorMessage += "Please Select Designation \n";
		if (status) {
			document.getElementById('designationTo').focus();
			status = false;
		}
	}
	if ($jq('#newGradePay').val()=='') {
		errorMessage += "Please Enter new Grade Pay\n";
		if (status) {
			document.getElementById('newGradePay').focus();
			status = false;
		}
	}else{
		for(var i=0; i<empGradePayJson.length; i++){
			if(empGradePayJson[i].id == $jq('#designationTo').val()){
			 if($jq('#newGradePay').val() <empGradePayJson[i].gradePay){
				 errorMessage += "Please Enter Grade Pay Equal or Above Value Of Respected Designation\n"; 
				  document.getElementById('newGradePay').focus();
					status = false;
			 }
			}
		}
	}
	if ($jq('#seniorityDate').val()=='') {
		errorMessage += "Please Select Seniority Date\n";
		if (status) {
			document.getElementById('seniorityDate').focus();
			status = false;
		}
	}
	if ($jq('#promotionDate').val()=='') {
		errorMessage += "Please Select Effective From \n";
		if (status) {
			document.getElementById('promotionDate').focus();
			status = false;
		}
	}
	if ($jq('#twoAddl').val()=='') {
		errorMessage += "Please Enter Two Additional Increment value\n";
		if (status) {
			document.getElementById('twoAddl').focus();
			status = false;
		}
	}
	if ($jq('#designationFrom').val()=='') {
		errorMessage += "Please Enter Variable Increments Points\n";
		if (status) {
			document.getElementById('designationFrom').focus();
			status = false;
		}
	}
	if ($jq('#valriableIncVal').val()=='') {
		errorMessage += "Please Enter Variable Increments Value\n";
		if (status) {
			document.getElementById('valriableIncVal').focus();
			status = false;
		}
	}
	if ($jq('#presentEffectivedate').val()=='') {
		errorMessage += "Please Select VarInc Start Date\n";
		if (status) {
			document.getElementById('presentEffectivedate').focus();
			status = false;
		}
	}
	if ($jq('#varIncEnd').val()=='') {
		errorMessage += "Please Enter varInc End Date \n";
		if (status) {
			document.getElementById('varIncEnd').focus();
			status = false;
		}
	}
	if(!$jq('#varIncEnd').val()=='' && !$jq('#presentEffectivedate').val()=='') {
		if(!compareDate($jq('#varIncEnd').val(),$jq('#presentEffectivedate').val())) {
			errorMessage += "Var Inc Start Date Should Not Greater Than Var Inc End Date \n Please Enter Right Dates";
			status=false;
		}
	}

	if (status) {
		var requestDetails = {
				"nodeID" : nodeID,
				"refSfid" : $jq('#refSfid').val(),
				"designationTo" :$jq('#designationTo').val(),
				"promotionDate" :$jq('#promotionDate').val(),
				"varIncEnd" :$jq('#varIncEnd').val(),
				"twoAddl" :$jq('#twoAddl').val(),
				"newGradePay" :	$jq('#newGradePay').val(),
				"presentEffectivedate" :$jq('#presentEffectivedate').val(),
				"designationFrom" :$jq('#designationFrom').val(),
				"seniorityDate" :$jq('#seniorityDate').val(),
				"param" : "managePromotionOfflineEntry"
			};
			$jq.ajax( {
				type : "POST",
				url : "promotion.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq('#result').html(html);
					
				}
			});
			resetOfflineEntry();	

	} else {
		alert(errorMessage);
	}
	}
function editOfflineEntry(id,sfid,presentDesig,promotedDesig,presentDate,promotedDate,basicPay,gradePay,newPay,newGradePay,seniorityDate,varIncEnd){
	
	nodeID=id;
	$jq('#refSfid').val(sfid);
	getCategoryWiseDesignations();
	$jq('#designationFrom').val(presentDesig);
	$jq('#designationTo').val(promotedDesig);
	$jq('#presentEffectivedate').val(presentDate);
	$jq('#promotionDate').val(promotedDate);
	$jq('#twoAddl').val(basicPay);
	$jq('#gradePay').val(gradePay);
	$jq('#newGradePay').val(newGradePay);
	$jq('#newBasicPay').val(newPay);
	$jq('#seniorityDate').val(seniorityDate);
	$jq('#varIncEnd').val(varIncEnd);
	getEmpGradePay();
	getEmpVarVal();
	
}

function deleteOfflineEntry(id,sfid){
	if(id !="" || id != null){
		var resp=confirm("Do you want to delete this Record?");
		if(resp==true){
		$jq.post(
			'promotion.htm?param=deleteRecord&refSfid='+sfid+'&nodeID='+id,
			function(html){
				$jq('#result').html(html);
			}
		)};
		resetOfflineEntry();
	   }
}

function resetOfflineEntry(){
	nodeID = "";

	//$jq('input:text').val("");
	//$jq('#refSfid').val('');
	$jq('#newGradePay').val('');
	$jq('#promotionDate').val('');
	$jq('#designationFrom').val('');
	$jq('#presentEffectivedate').val('');
	//$jq('#newBasicPay').val('');
	$jq('#seniorityDate').val('');
	$jq('#twoAddl').val('');
	$jq('#valriableIncVal').val('');
	$jq('#varIncEnd').val('');
	$jq('textarea').val("");
	$jq('#designationFrom').find('option').remove().end();
	$jq("#designationFrom").append($jq("<option></option>").attr("value",'0').text('Select'));
	//$jq('#designationTo').find('option').remove().end();
	$jq("#designationTo").append($jq("<option></option>").attr("value",'0').text('Select'));
	$jq('#result1').text('');
	//$jq('#empGradePayGrid').css("display","none");
}
function getCategoryWiseDesignations(){
	var flag = false;
	$jq('#result').html('');
	 //resetOfflineEntry();
	if ($jq("#SearchSfid").val() != '') {
		flag = true;
	}else{
		$jq("#SearchSfid").focus();
		flag = false;
		alert("Please Enter SFID ");
	}
	if(flag){
		$jq('#empGradePayGrid').css("display","none");
		$jq('#Pagination').css("display","none");
		$jq('#empDetailsMainGrid').css("display","none");
		
		$jq("#designationTo").append($jq("<option></option>").attr("value",'0').text('Select'));
		$jq('#designationTo').find('option').remove().end();
			$jq.ajax( {
				type : "GET",
				url : "promotion.htm",
				data : "param=getCategoryDesignation&refSfid="
						+ $jq("#SearchSfid").val().toUpperCase(),
				success : function(html) {
					$jq('#result').html(html);
				}
			});
	}
	
}
//Do Part Code Starts
function getGazettedTypeList(){
	gazetted = $jq('#gazettedType').val();
	 $jq('#casualityDiv').show();
	var requestDetails = {
			"type":"updateDesig",
		
		"assessmentTypeID" : $jq('#assessmentCategoryID').val().split('-')[1],
		"assessmentCategoryID": $jq('#assessmentCategoryID').val().split('-')[2],
		"yearID" :  $jq('#assessmentCategoryID').val().split('-')[0],
		"gazettedType":$jq('#assessmentCategoryID').val().split('-')[3],
		"param" : "gazetted"
	    /*"gazettedType":$jq('#gazettedType').val()*/
	};
	$jq.ajax( {
		type : "POST",
		url : "promotion.htm",
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq('#result').html(html);
		/*	$jq('#gazettedType').val($jq('test').val());*/
       	 $jq('#casualityDiv').show();
		}
	});
}
function enablePayDoPartNo(listType){
	var flag=false;
	$jq("#"+listType+" tr").each(function() {	
		if($jq(this).find("td").eq(0).find(":input:checkbox").is(':checked')){
			flag=true;
		}
	});
	if(flag){
		$jq('#doPartNumberDiv').show();
	}else{
		$jq('#doPartNumberDiv').hide();
	}
}
//Do Part Code Ends

//Promotion Offline Entry Screen Ends
//
function submitOptionValue(){
	var errorMessage = "";
	var status = true;
	var rowValues = "";
	
	$jq("#list tr:not(:first)").each(function() {	
		if ($jq(this).find("td").eq(0).find(":input:checkbox").is(':checked')) {
			rowValues += $jq.trim($jq(this).find("td").eq(0).find(":input:hidden").attr('id'))
			+ '#'
			+$jq.trim($jq(this).find("td").eq(2).html())
			+ '#';
	if ($jq('#assessmentCategoryID').val() == 1) {
		//rowValues += $jq('#refSfid').val().toUpperCase() + '#';
	} else {
		
	}
	rowValues += $jq.trim($jq(this).find("td").eq(6).html())
	+'#'
	+',';
	
}		
});
	
	if(rowValues==''){
		errorMessage += "Please check atleast one member.\n";
		if (status) {
			status = false;
		}
	}
	if (status) {
		var requestDetails = {
				"type":"updateDesig",
			"rowValues" : rowValues,
			"assessmentTypeID" : $jq('#assessmentCategoryID').val().split('-')[1],
			"assessmentCategoryID": $jq('#assessmentCategoryID').val().split('-')[2],
			"yearID" :  $jq('#assessmentCategoryID').val().split('-')[0],
			"param" : "submitCandidatesOption"
		};
		$jq.ajax( {
			type : "POST",
			url : "promotion.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
			}
		});
	} else {
		alert(errorMessage);
	}	
}

function getOptioncertificate(sfid){
	$jq('#unsavedSfid').attr("disabled",true);	
		var requestDetails = {
			"sfID":sfid.value,
			"param" : "getOptCert"
		};
	$jq.ajax( {
		type : "POST",
		url : 'promotionReq.htm',
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq('#result').html(html);
		}
	});
	
}
function checkAnnual(e,e1){
	   
		if(!(e1 == null || e1 =="")){
			
			$jq('#'+(e.attributes[2].value)).attr('checked',false);
			
		alert('Please update Annual Increment -Basic Pay /n Before Updating Option & Designation');
		
		}
}

function enableLower(){
	
	if($jq('input:radio[name=payUpdate]:checked').val() == 2){
		
	$jq('#lowergarde').show();
	}else{
		$jq('#lowergarde').hide();
	
	}
}





function getSfidList(){
	var errorMessage = "";
	var status = true;

	
	
	if ($jq('#assessmentCategoryID').val() == 0 && $jq('#assessmentTypeID').val() == 0) {
		errorMessage += "Please Select  BoardType.\n";
		if (status) {
			status = false;
			$jq('#assessmentCategoryID').focus();
		}
	}
	if ($jq('#yearID').val() == 0) {
		errorMessage += "Please Select Year.\n";
		if (status) {
			status = false;
			$jq('#yearID').focus();
		}
	}
	
	if (status) {
		
		if($jq('#assessmentCategoryID').val() != "0"){
		var requestDetails = {
			"type":"sfidList",
			"param" : "getsfidList",
			
			"assessmentTypeID" : $jq('#assessmentCategoryID').val().split('-')[1],
			"assessmentCategoryID": $jq('#assessmentCategoryID').val().split('-')[2],
			"yearID" :  $jq('#assessmentCategoryID').val().split('-')[0],
		};
	}else{
		var requestDetails = {
				"type":"sfidList",
				"param" : "getsfidList",
				
				"assessmentTypeID" : $jq('#assessmentTypeID').val().split('-')[1],
				"assessmentCategoryID": $jq('#assessmentTypeID').val().split('-')[2],
				"yearID" :  $jq('#assessmentTypeID').val().split('-')[0],
		
	};
	}
		$jq.ajax( {
			type : "POST",
			url : "promotionReq.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
			$jq('#sfidList').html(html);		
			
		}
		});
	} else {
		alert(errorMessage);
	}
	
	
}

//
/*********
 * promotion pay update list search
 * 
*/
 
 function searchPromotionPayUpdate(){
		var errorMessage = "";
		var status = true;

		
		if ($jq('#assessmentTypeID').val() == 0) {
			errorMessage += "Please Select  Category Type.\n";
			if (status) {
				status = false;
				$jq('#assessmentTypeID').focus();
			}
		}
		if ($jq('#assessmentCategoryID').val() == 0) {
			errorMessage += "Please Select  BoardType.\n";
			if (status) {
				status = false;
				$jq('#assessmentCategoryID').focus();
			}
		}
		if ($jq('#yearID').val() == 0) {
			errorMessage += "Please Select Year.\n";
			if (status) {
				status = false;
				$jq('#yearID').focus();
			}
		}
		
		if (status) {
			var requestDetails = {
				
				"param" : "getPromotionPayUpdateList",
				"assessmentTypeID" : $jq('#assessmentCategoryID').val().split('-')[1],
				"assessmentCategoryID": $jq('#assessmentCategoryID').val().split('-')[2],
				"yearID" :  $jq('#assessmentCategoryID').val().split('-')[0],
				"type":"PromotionPayUpdate"
			};
			$jq.ajax( {
				type : "POST",
				url : "promotion.htm",
				data : requestDetails,
				cache : false,
				success : function(html) {
				$jq('#paylist').html(html);		
				
			}
			});
		} else {
			alert(errorMessage);
		}

	}
 
 function checkDate(date){
	 
	 var id = date.id;
	 var sysdate=new Date();
	 var sys=formatDate(sysdate,'dd-NNN-yyyy');
	 if(date.value < sys){
	 alert("Selected Date  should Greater  than to Today");
      
	 $jq('#'+id).val('');
	 }
 }
 
function submitPromotionPayUpdate(){
	var errorMessage = "";
	var status = true;
	var rowValues = "";
	
	
	if ($jq('#assessmentCategoryID').val() == '0') {
		errorMessage += "Please select board type.\n";
		if (status) {
			$jq('#assessmentCategoryID').focus();
			status = false;
		}
	}
	if ($jq('#yearID').val() == '0') {
		errorMessage += "Please select year.\n";
		if (status) {
			$jq('#yearID').focus();
			status = false;
		}
	}
	
	var i=1;
	$jq("#fixationList tr:not(:first)").each(function() {
		if ($jq(this).find("td").eq(0).find(":input:checkbox").is(':checked')) {
			if($jq(this).find("td").eq(9).find(":input").val() == '' || $jq(this).find("td").eq(10).find(":input").val() == '' || $jq(this).find("td").eq(11).find(":input").val() == ''){
				errorMessage += "Please enter Pay Details in "+ i+" row.\n";
			if (status) {
				status = false;	
			}
		}
		}
		i++;
	});
	
	$jq("#fixationList tr:not(:first)").each(function() {
		if ($jq(this).find("td").eq(0).find(":input:checkbox").is(':checked')) {
			// PayFixationID#AssessmentID#SFID#newBasicPay#newGradePay#twoAddIO
			var id = $jq(this).find("td").eq(0).find(":input:checkbox").attr(
					'id').split('#');
			if (!($jq(this).find("td").eq(10).find(":input").val() == ''
					|| $jq(this).find("td").eq(11).find(":input").val() == ''
					|| $jq(this).find("td").eq(12).find(":input").val() == '')) {
				rowValues += id[0] + '#' + id[1] + '#'
						+ $jq.trim($jq(this).find("td").eq(1).html()) + '#'
					
						+ $jq(this).find("td").eq(10).find(":input").val() + '#'
						+ $jq(this).find("td").eq(11).find(":input").val()+ '#'
						
						+  $jq(this).find("td").eq(12).find(':input').val()+ '#'
						
						+ $jq(this).find("td").eq(13).find(":input").val()+ '#'
						+
						+ $jq(this).find("td").eq(14).find('div').attr('id')+'#'
						+ $jq(this).find("td").eq(15).find(':input').val()+'#'       //This is for save variableIncrementValue
						+'#######'+'##1'
						+ ',';
			}
		}
	});
	if (rowValues == '') {
		errorMessage += "Please enter pay details.\n";
		if (status) {
			status = false;
		}
	}
	if (status) {
		var requestDetails = {
				"assessmentTypeID" : $jq('#assessmentCategoryID').val().split('-')[1],
				"assessmentCategoryID": $jq('#assessmentCategoryID').val().split('-')[2],
				"yearID" :  $jq('#assessmentCategoryID').val().split('-')[0],
			"rowValues" : rowValues,
			"param" : "submitPromotionPayUpdate"
		};
	$jq.ajax( {
			type : "POST",
			url : "promotion.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
			$jq('#paylist').html(html);
			}
		});
	} else {
		alert(errorMessage);
	}
}
 
 /*********/
 function setvalue(){
	 $jq('#assessmentCategoryID').val(0);
 }
 
//new submitBoardMasterDetails
 function submitBoardMasterDetails(){
 	var errorMessage = "";
 	var status = true;
 	if ($jq('#boardType').val() == '') {
 		errorMessage += "Please Enter BoardType.\n";	
 		if (status) {
 			$jq('#boardType').focus();
 			status = false;
 		}
 	}
 	if(($jq('#boardType').val()).length >=20){
 		errorMessage+="Board name should not exceds 20 characters\n";
 	}
 	if (errorMessage == "") {
 		$jq('#param').val('boardInformation');
 		$jq.post('promotion.htm?nodeID=' + nodeID, $jq(
 				'#PromotionManagementBean').serialize(), function(html) {
 			$jq("#result").html(html);
 			if ($jq('.success').is(':visible')) {
 				clearBoardMasterDetails();
 			}
 		});
 		
 	} else {
 		alert(errorMessage);
 	}
 }
 
 function clearBoardMasterDetails(){
		nodeID = "";
		$jq('#boardType').val("");
	}
 
 function editBoardMasterDetails(id,category){
 	nodeID = id;
 	$jq('#boardType').val(category);
 	}

 function deleteBoardMasterDetails(id) {
 	var check=confirm("Do you want to delete ?");
 	if(check){
 	var requestDetails = {
 		"nodeID" : id,
 		"param" : "deleteBoardType"
 	};
 	$jq.ajax( {
 		type : "POST",
 		url : "promotion.htm",
 		data : requestDetails,
 		cache : false,
 		success : function(html) {
 			$jq('#result').html(html);
 			if ($jq('.success').is(':visible')) {
 			}
 		}
 	});
 	clearBoardMasterDetails();
 	}
 }

 
 //end deleteBoardMasterDetails

 ///check promotionDate (Author Rajendra)
 function checkPromotionDate(e){
	 
	var promotedDate =  e.parentNode.parentNode.cells[7].textContent;
	 var date = new Date();
	 var sys=formatDate(date,'dd-NNN-yyyy');
	 
		 if((compareDate(promotedDate,sys)) && $jq('#'+(e.attributes[2].value)).attr('checked')   && !($jq.trim(sys.toUpperCase().toString()) == $jq.trim(promotedDate.toUpperCase().toString()))){	 
		 alert("You Can't Update  Designation Until Promotion Date");
		 $jq('#'+(e.attributes[2].value)).attr('checked',false);
	 }
 }
 
 function getYearWiseAssessmentList(year,type){
	 var requestDetails = {
	 "yearID" : $jq('#yearID').val(),
	 "param" : "yearWiseAssesmentList",
	 "type"	: type 
	 };
	 $jq.ajax( {
	 type : "POST",
	 url : "promotionYearWise.htm",
	 data : requestDetails,
	 cache : false,
	 success : function(html) {
		 $jq('#assessmentTypeID').find('option').remove().end();
		 $jq('#assessmentTypeID').append($jq("<option></option>").attr("value",0).text('Select'));
		 for(var i=0;i <= (html.length)-1;i++)
			 $jq('#assessmentTypeID').append($jq("<option></option>").attr("value",html[i].value).text(html[i].name));		 
	 }
	 }); 
}
 
 
 
