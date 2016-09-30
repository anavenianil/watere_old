
type="";
var jsonmasterlist="";
var empID="";
var jsonSubCategoryList="";
var pkID=0;





function getltcWaterReimbursementDetails(requestID){
	
	
	document.forms[0].requestId.value = requestID;
	document.forms[0].param.value = "ltcWaterReimbursementDetails";
	document.forms[0].action = "ltcWaterRequest.htm";
	document.forms[0].submit();
	
}


function getltcWaterSettlementDetails(requestID){
	document.forms[0].requestId.value = requestID;
	document.forms[0].param.value = "ltcWaterSettlementDetails";
	document.forms[0].action = "ltcWaterRequest.htm";
	document.forms[0].submit();
}




function getLTCWaterAdvCompDetails(requestID) {
	document.forms[0].requestId.value = requestID;
	document.forms[0].param.value = "ltcWaterAdvCompDetails";
	document.forms[0].action = "ltcWaterRequest.htm";
	document.forms[0].submit();
}

function getLTCWaterAdvCompDetailsForCancel(requestID) {
		document.forms[0].requestId.value = requestID;
		document.forms[0].param.value = "ltcWaterAdvCompDetailsForCancel";
		document.forms[0].action = "ltcWaterRequest.htm";
		document.forms[0].submit();
	}


 //added by 25/04/2016
function getLTCWaterRequestDetails(requestID) {
	document.forms[0].requestId.value = requestID;
	document.forms[0].param.value = "ltcwaterRequestDetails";
	document.forms[0].action = "ltcWaterRequest.htm";
	document.forms[0].submit();
}


//added by bkr 19/05/2016
function fullName(){
	
	var firstName=$jq('#firstName').val();
	var middleName=$jq('#middleName').val();
	var lastName=$jq('#lastName').val();
	
	var fullName=firstName+" "+middleName+" "+lastName;
	
	//alert(firstName+"  "+middleName+"  "+lastName);
	$jq('#nameInServiceBook').val(fullName);
	
	
}

function getYearDOPart(){
	var errorMsg="";
	var status=true;
	if($jq('#year').val()==""){
		errorMsg+="Please Select Year\n";
		status=false;
	}
	if(status){
	var requestDetails={
			"param" : "yearWiseDOPart",
			"year"  : $jq("#year").val(),
			"gazType" :$jq("#gazType").val()
	}
	$jq.ajax({
		data : requestDetails,
		url  : "doPart.htm?"+dispUrl,
		type : "POST", 
		cache: false,
		success: function(html){
		$jq("#result").html(html);
	}
	});
	}else{
		alert(errorMsg);
	}
}

function manageDOPartMasterDetails(){
	var errorMsg="";
	var status=true;
	
	
	
	if($jq('#gazType').val()=="select"){
		errorMsg+="Please Select Designation Type\n";
		status=false;
	}
	if($jq('#preDoPartNo').val()==""){
		errorMsg+="Please Enter Last DOPart Number\n";
		status=false;
	}
	if($jq('#preDoPartDate').val()==""){
		errorMsg+="Please Select Last DOPart Date\n";
		status=false;
	}
	if($jq('#doPartNo').val()==""){
		
		errorMsg+="Please Enter DOPart Number\n";
		status=false;
	}
	if($jq('#doPartDate').val()==""){
		errorMsg+="Please Select DOPart Date\n";
		status=false;
	}
	if($jq('#description').val()==""){
		errorMsg+="Please Enter Description \n";
		status=false;
	}
	if($jq('#distribution').val()==""){
		errorMsg+="Please Enter Distribution \n";
		status=false;
	}
	if($jq('input:checkbox[name=verifyFlag]').is(':checked')) {
		if(status==true)
			status = confirm('Once you Freeze the DOPart you can not modify it. Do you want to continue..?\n Press Ok to Continue');
	}
	if(compareDate($jq('#preDoPartDate').val(),$jq('#doPartDate').val())){
		errorMsg+="Present DOPart Date Is Less Than Pre DOPart Date\n";
		status=false;
	}
	if(status){
		var requestDetails ={
				"param"         : "manageDOPart",
				"id"			: nodeID,
				"gazType"		: $jq('#gazType').val(),
				"doPartNo"      : $jq.trim($jq('#doPartNo').val().toUpperCase()),
				"description"	: $jq('#description').val(),
				"distribution"  : $jq('#distribution').val(),
				"doPartDate"    : $jq('#doPartDate').val(),
				"preDoPartNo"   : $jq.trim($jq('#preDoPartNo').val().toUpperCase()),
				"preDoPartDate" : $jq('#preDoPartDate').val(),
				"year" 			: $jq('#year').val(),
				"verifyFlag"	: $jq('input:checkbox[name=verifyFlag]:checked').val() == "Y"?"Y":"N"
				};
		$jq.ajax({
			type :"POST" ,
			url: "doPart.htm?"+dispUrl,
			data:requestDetails ,
			cache:false,
			success : function(html) {
				$jq("#result").html(html);
				clearDOPartMasterDetails();
			}
			
		});
	}else if(errorMsg!=''){
		alert(errorMsg);
	}
}
function clearDOPartMasterDetails(){
	nodeID="";
	$jq('#doPartNo').val('');
	$jq('#doPartDate').val('');
	$jq('#gazType').val('');
	$jq('#preDoPartNo').val('');
	$jq('#preDoPartDate').val('');
	$jq('#description').val('');
	$jq('#distribution').val('');
	// $jq('#year').val('');
	$jq('#verifyFlag').attr("checked",false);
	// getYearDOPart();
}
// function editDOPartMasterDetails(DOPartId){
function editDOPartMasterDetails(DOPartId){	
	nodeID=DOPartId;
	for( var i = 0;i < jsonDoPartList.length; i++){
		if (jsonDoPartList[i].id == DOPartId) {
			$jq('#year').val(jsonDoPartList[i].year);
			$jq('#doPartNo').val(jsonDoPartList[i].doPartNumber);
			$jq('#doPartDate').val(jsonDoPartList[i].doPartDate.date+'-'+getMonthMON(parseInt(jsonDoPartList[i].doPartDate.month+1))+'-'+parseInt(1900+jsonDoPartList[i].doPartDate.year));
			$jq('#gazType').val(jsonDoPartList[i].gazType+'#'+jsonDoPartList[i].typeId);
			$jq('#preDoPartNo').val(jsonDoPartList[i].preDoPartNo);
			$jq('#preDoPartDate').val(jsonDoPartList[i].preDoPartDate.date+'-'+getMonthMON(parseInt(jsonDoPartList[i].preDoPartDate.month+1))+'-'+parseInt(1900+jsonDoPartList[i].preDoPartDate.year));
		    $jq('#description').val(jsonDoPartList[i].description);   
		    $jq('#distribution').val(jsonDoPartList[i].distribution);
			if(jsonDoPartList[i].status==60)
			$jq('#verifyFlag').attr("checked",true);
		}
	}
	
}
function deleteDOPartMasterDetails(DOPartId){
	var requestDetails ={
			"param"         : "deleteDOPart",
			"id"			: DOPartId,
			"year"			: $jq('#year').val(),
			"gazType"		: $jq('#gazType').val(),
			"doPartNo"      : $jq('#doPartNo').val(),
			"doPartDate"    : $jq('#doPartDate').val(),
			"preDoPartNo"   : $jq('#preDoPartNo').val(),
			"preDoPartDate" : $jq('#preDoPartDate').val()
			};
	$jq.ajax({
		type :"POST" ,
		url: "doPart.htm?"+dispUrl,
		data:requestDetails,
		cache:false,
		success : function(html) {
			$jq("#result").html(html);
			clearDOPartMasterDetails();
		}
	});
}

function manageMaster(type) {
	var errorMessage = "";
	var status = true;
	var typeValue = "";
	if (type == 'category' || type == 'community' || type == 'group'
			|| type == 'approles'||type == 'subcategory') {
		typeValue = (document.getElementById('typeValue').value).toUpperCase();
	
		if (document.getElementById('orderNo') != null && document.getElementById('orderNo').value == '') {
			errorMessage += "Please Enter Order NO\n";
			if (status) {
				document.getElementById('orderNo').focus();
				status = false;
			}
			
	} 
	}else if (type == 'appointment') {
		typeValue = document.getElementById('typeValue').value;
	} else {
		typeValue = document.getElementById('typeValue').value;
	}

	if (type == 'district') {
		if (document.getElementById('parentID').value == 'select') {
			errorMessage += "Please Select State\n";
			document.getElementById('parentID').focus();
			status = false;
		}
	} else if (type == 'subcategory') {
		if (document.getElementById('parentID').value == 'select') {
			errorMessage += "Please Select Category\n";
			document.getElementById('parentID').focus();
			status = false;
		}
	}else if (type == 'subQuarter') {
		if (document.getElementById('parentID').value == 'select') {
			errorMessage += "Please Select Quarter\n";
			document.getElementById('parentID').focus();
			status = false;
		}
	}else if (type == 'awardCategory') {
		if (document.getElementById('parentID').value == 'select') {
			errorMessage += "Please Select Organisation\n";
			document.getElementById('parentID').focus();
			status = false;
		}
	}else if (type == 'disnumber') {
		if (document.getElementById('parentID').value == 'select') {
			errorMessage += "Please Select Dispensary Location\n";
			document.getElementById('parentID').focus();
			status = false;
		}
	}

	if (document.getElementById('typeValue').value == '') {
		
		   if(type =="dislocation"){
			   errorMessage += "Please Enter Dispensary Location Name \n";
		   }else{
			   errorMessage += "Please Enter "+type.toUpperCase()+" Name\n";
		   }
				
		if (status) {
			document.getElementById('typeValue').focus();
			status = false;
		}
	}
	
	if(type == 'year'){
		var temp = document.getElementById('typeValue').value;	
		if(isNaN(temp)){
		errorMessage+="please Enter Numeric Values Only";
		
		if(status){
			document.getElementById('typeValue').focus();
			status = false;
		}
		}
	}
	
	if (status) {

		/*
		 * if (type == 'district' || type == 'subcategory') {
		 * document.forms[0].name.value = typeValue;
		 * document.forms[0].type.value = type; document.forms[0].param.value =
		 * "manage"; new Ajax.Updater('result', 'master.htm?id=' +
		 * masterID+'&'+dispUrl , { onComplete : function() {clearMaster(); },
		 * parameters : Form.serialize(document.forms[0]), asynchronous : false,
		 * evalScripts : true }); } else {
		 */
			document.forms[0].name.value = typeValue;
			document.forms[0].type.value = type;
			document.forms[0].param.value = "manage";
			/*
			 * new Ajax.Updater('result', 'master.htm?id=' +
			 * masterID+'&'+dispUrl, { onComplete : function() {clearMaster(); },
			 * parameters : Form.serialize(document.forms[0]), asynchronous :
			 * false, evalScripts : true });
			 */
			$jq.post( 
					'master.htm?id=' + masterID+'&'+dispUrl,$jq('#master').serialize(), 
			           function(data){ 
			            	 $jq('#result').html(data);
			            	 clearMaster();
			            	 } 
			      );
		// }

	} else {
		
		alert(errorMessage);

	}
}
function changeEmployeeDetails(type) {
	var requestDetails = {
		"changeSfid" : $jq("#changeSfid").val(),
		"param" : "changeEmployee"
	};
	$jq.ajax( {
		type : "POST",
		url : "master.htm",
		data : requestDetails,
		cache : true,
		success : function(html) {
		$jq('#result').html(html);
		if(sfidResult=='employeeexists') {
			if (type == 'address') {
				getAddress();
			} else if (type == 'family') {
				familyDetails();
			} else if (type == 'qualification') {
				viewQualification();
			} else if (type == 'training') {
				getTraining();
			} else if (type == 'passport') {
				getPassport();
			} else if (type == 'experience') {
				getEmpExperienceDetails();
			} else if (type == 'nominee') {
				NomineeDetails();
			}else if (type == 'preOrganisation') {
				PreOrgnDetails();
			} else if (type == 'retirement') {
				getRetirementDetails();
			}else if (type == 'quarter') {
				getQuarterDetails();
			}else if (type == 'award') {
				getAwardDetails();
			}
		}
		}
	});
}
function clearMaster() {
	masterID = "";
	if (document.getElementById('orderNo') != null)
		document.getElementById('orderNo').value = "";
	if (document.getElementById('alias') != null)
		document.getElementById('alias').value = "";
	if (document.getElementById('typeValue') != null)
		document.getElementById('typeValue').value = "";
	if (document.getElementById('description') != null)
		document.getElementById('description').value = "";
	if (document.getElementById('counter') != null)
		document.getElementById('counter').value = "500";
	if (document.getElementById('parentID') != null) {
		document.getElementById('parentID').value = "select";
	}
}

function editMaster(id, value, description, parentID,orderNo,alias) {
	masterID = id;
	document.getElementById('typeValue').value = value;
	document.getElementById('description').value = description;
	if(orderNo !='' || alias !=''){
		if(document.getElementById('orderNo') != null || document.getElementById('alias') != null){
		document.getElementById('orderNo').value = orderNo;
		document.getElementById('alias').value = alias;
		}
	}
	if (parentID != '') {
		if (document.getElementById('parentID') != null)
			document.getElementById('parentID').value = parentID;
	}
	if (description.length > 0) {
		document.getElementById('counter').value = 500;
		document.getElementById('counter').value -= description.length;
	}
}

function deleteMaster(id, type) {
	var del = confirm("Do you want to delete this item?");
	if (del == true) {
		var pageurl=dispUrl.split("=");
		var page=parseInt(pageurl[1]);
		var len=jsonmasterlist.length;
		var disppage=(len-1)/6;
		var dispval=(len-1)%6;
		if(disppage<page && dispval==0)
			dispUrl=pageurl[0]+"="+(page-1);		
		$jq.post( 
				'master.htm?param=delete&type=' + type + '&id=' + id+'&'+dispUrl, 
		           function(data){ 
		            	 $jq('#result').html(data);
		            	 clearMaster();
		            	 } 
		      );
	}
}

function lables(type) {
	if (type == 'category') {
		document.title = "Category Master";
		document.getElementById('headTitle').innerHTML = "Create Category";
		document.getElementById('labelType').innerHTML = "Category Name<span class='mandatory'>*</span>";
	} else if (type == 'appointment') {
		document.title = "Appointment Master";
		document.getElementById('headTitle').innerHTML = "Create Appointment";
		document.getElementById('labelType').innerHTML = "Appointment Name<span class='mandatory'>*</span>";
	} else if (type == 'group') {
		document.title = "Group Master";
		document.getElementById('headTitle').innerHTML = "Create Group";
		document.getElementById('labelType').innerHTML = "Group Name<span class='mandatory'>*</span>";
	} else if (type == 'employment') {
		document.title = "Employment Type Master";
		document.getElementById('headTitle').innerHTML = "Create Employment";
		document.getElementById('labelType').innerHTML = "Employment Type<span class='mandatory'>*</span>";
	}  else if (type == 'relation') {
		document.title = "Relations Master";
		document.getElementById('headTitle').innerHTML = "Create Relation";
		document.getElementById('labelType').innerHTML = "Relation Name<span class='mandatory'>*</span>";
	} else if (type == 'qualification') {
		document.title = "Qualification Master";
		document.getElementById('headTitle').innerHTML = "Create Qualification";
		document.getElementById('Name').innerHTML = "Qualification Name<span class='mandatory'>*</span>";
		document.getElementById('ShortForm').innerHTML = "Qualification Code<span class='mandatory'>*</span>";
		document.getElementById('Flag').innerHTML = "Professional";
	}  else if (type == 'degree') {
		document.title = "Degree Master";
		document.getElementById('headTitle').innerHTML = "Create Degree";
		document.getElementById('labelType').innerHTML = "Degree Name<span class='mandatory'>*</span>";
	} else if (type == 'community') {
		document.title = "Community Master";
		document.getElementById('headTitle').innerHTML = "Create Community";
		document.getElementById('labelType').innerHTML = "Community Name<span class='mandatory'>*</span>";
	} else if (type == 'reservation') {
		document.title = "Reservation Master";
		document.getElementById('headTitle').innerHTML = "Create Reservation";
		document.getElementById('labelType').innerHTML = "Reservation Name<span class='mandatory'>*</span>";
	} else if (type == 'state') {
		document.title = "State Master";
		document.getElementById('headTitle').innerHTML = "Create State";
		document.getElementById('labelType').innerHTML = "State Name<span class='mandatory'>*</span>";
	} else if (type == 'district') {
		document.title = "District Master";
		document.getElementById('headTitle').innerHTML = "Create District";
		document.getElementById('labelType').innerHTML = "District Name<span class='mandatory'>*</span>";
		document.getElementById('parentLable').innerHTML = "State Name<span class='mandatory'>*</span>";
	} else if (type == 'subcategory') {
		document.title = "Sub Category Master";
		document.getElementById('headTitle').innerHTML = "Create Sub Category";
		document.getElementById('labelType').innerHTML = "Sub Category Name<span class='mandatory'>*</span>";
		document.getElementById('parentLable').innerHTML = "Category Name<span class='mandatory'>*</span>";
	}else if (type == 'subQuarter') {
		document.title = "Sub Quarter Type Master";
		document.getElementById('headTitle').innerHTML = "Create Sub Quarter Type";
		document.getElementById('labelType').innerHTML = "Sub Quarter Type<span class='mandatory'>*</span>";
		document.getElementById('parentLable').innerHTML = "Quarter Type<span class='mandatory'>*</span>";
	}
	else if (type == 'dislocation') {
		document.title = "Dispensary Location Master";
		document.getElementById('headTitle').innerHTML = "Create Dispensary Location";
		document.getElementById('labelType').innerHTML = "Dispensary Location Name<span class='mandatory'>*</span>";
	} else if (type == 'disnumber') {
		document.title = "Dispensay Location Mapping";
		document.getElementById('headTitle').innerHTML = "Create Dispensary Number";
		document.getElementById('labelType').innerHTML = "Dispensary Number<span class='mandatory'>*</span>";
		document.getElementById('parentLable').innerHTML = "Dispensary Location<span class='mandatory'>*</span>";
	}
	else if (type == 'request') {
		document.title = "Work Flow Request";
		document.getElementById('headTitle').innerHTML = "Create Request Work Flow";
		document.getElementById('labelType').innerHTML = "Request Type Name<span class='mandatory'>*</span>";
	}
	else if (type == 'workflow') {
		document.title = "Work Flow Master";
		document.getElementById('headTitle').innerHTML = "Create Work Flow";
		document.getElementById('labelType').innerHTML = "Work Flow Name<span class='mandatory'>*</span>";
	} else if (type == 'approles') {
		document.title = "Application Roles Master";
		document.getElementById('headTitle').innerHTML = "Create Application Roles";
		document.getElementById('labelType').innerHTML = "Application Role Name<span class='mandatory'>*</span>";
	} else if (type == 'awardCategory') {
		document.title = "Award Category Master";
		document.getElementById('headTitle').innerHTML = "Create Award Category";
		document.getElementById('labelType').innerHTML = "Award Category Name<span class='mandatory'>*</span>";
		document.getElementById('parentLable').innerHTML = "Organisation<span class='mandatory'>*</span>";
	} else if (type == 'quarterType') {
		document.title = "Quarter Type Master";
		document.getElementById('headTitle').innerHTML = "Create Quarter Type";
		document.getElementById('labelType').innerHTML = "Quarter Type Name<span class='mandatory'>*</span>";
	} else if (type == 'retirementType') {
		document.title = "Retirement Type Master";
		document.getElementById('headTitle').innerHTML = "Create Retirement Type";
		document.getElementById('labelType').innerHTML = "Retirement Type Name<span class='mandatory'>*</span>";
	}else if (type == 'nominee') {
		document.title = "Nominee Type Master";
		document.getElementById('headTitle').innerHTML = "Create Nominee Type";
		document.getElementById('labelType').innerHTML = "Nominee Type <span class='mandatory'>*</span>";
	}else if (type == 'year') {
		document.title = "Year Master";
		document.getElementById('headTitle').innerHTML = "Create Year";
		document.getElementById('labelType').innerHTML = "Create Year <span class='mandatory'>*</span>";
		$jq('#typevalue').removeAttr('onkeypress:');
	}else if (type == 'motherTongue') {
		document.title = "Mother Tongue Master";
		document.getElementById('headTitle').innerHTML = "Create Mother Tongue";
		document.getElementById('labelType').innerHTML = "Mother Tongue <span class='mandatory'>*</span>";
	}else if (type == 'departmentType') {
		document.title = "Department Type Master";
		document.getElementById('headTitle').innerHTML = "Department Type Master";
		document.getElementById('labelType').innerHTML = "Department Type <span class='mandatory'>*</span>";
	}
	else if (type == 'city') {
		document.title = "City Master";
		document.getElementById('headTitle').innerHTML = "City Master";
		document.getElementById('labelType').innerHTML = "City Name <span class='mandatory'>*</span>";
	}
}

function funKeyPress(e) {
	var key;
	if (window.event) {
		key = window.event.keyCode;
	} else {
		key = e.which;
	}
	if (key == 13) {
		login();
	}
}

function login() {
	var flag = true;
	var errorMessage = "";

	if (!validateNotEmpty(document.getElementById('username').value)) {
		errorMessage += "Please Enter User Name \n";
		if (flag) {
			document.forms[0].username.focus();
			flag = false;
		}
	}
	if (!validateNotEmpty(document.getElementById('password').value)) {
		errorMessage += "Please Enter password \n";
		if (flag) {
			document.forms[0].password.focus();
			flag = false;
		}
	}
	if (flag) {
		document.forms[0].action = "index.htm";
		document.forms[0].submit();

	} else {
		alert(errorMessage);
	}
}

function enablefamilyValues(value) {
	if (value != null && value != "") {
		var val = value.split(",");
		if ((val[0] == "view") || (val[0] == "manage") || (val[0] == "delete")
				|| (val[0] == "edit")) {
			document.getElementById("createFamily").style.display = "block";
			document.getElementById("FamilyList").style.display = "block";
		} else {
			document.getElementById("createFamily").style.display = "none";
			document.getElementById("FamilyList").style.display = "none";
		}
	}
}
function manageFamily(type) {
	
	// added by bkr 31/03/2016
var ltcsonordau=$jq('#relation').val();
var ltcage=$jq('#familyage').val();
var ltcdob1=$jq('#familydob').val();
var ltcdob=$jq('#familydob').val();
ltcdob= ltcdob.substring(7);
var d=new Date().getFullYear();
var ltcyesorno=document.getElementById("ltcApplicable").checked;
//added by bkr 31/03/2016
if(ltcsonordau=='5' || ltcsonordau=='6'){
	ltcdob=d-ltcdob;
	if(!ltcage=="" || !ltcage==null){
	if(ltcage>18){
		if(ltcyesorno){
		alert("Your Child is NOt Dependent to you ");
		document.getElementById("ltcNotApplicable").checked="checked";
		}
	}
	}
	if(!ltcdob1=="" || !ltcdob1==null){
	if(ltcdob>18){
		if(ltcyesorno){
		alert("Your Child is NOt Dependent to you");
		document.getElementById("ltcNotApplicable").checked="checked";
		}
	}
	}
}

//alert(ltcage);

//alert(ltcdob);

	var errorMessage = "";
	errorMessage = validateFamily();
	//if($jq('#cghscardfile').val()!='' && !checkFileExtention($jq('#cghscardfile').val().split('.')[$jq('#cghscardfile').val().split('.').length-1])) {
		 //	errorMessage+='Upload only .docx,.doc,.txt,.pdf\n';
		// 	status = false;  
		//}
	
	if (errorMessage == "") {
		if(document.getElementById('city').value=="select")
			document.getElementById('city').value =	"";
		if(document.getElementById('employeedType').value=="select")
			document.forms[0].employeedType.value ="";
// if(document.getElementById('disabilityId').value=="Select")
// document.forms[0].disabilityId.value ="";
		if (type == 'familyRequest') {
			if(familyID==""){
				familyID = 0;
			}if(checkChangedValues(familyID)) {
			document.forms[0].changedValues.value=getFamilyDetails();
			document.forms[0].param.value = "submitRequest";
			new Ajax.Updater('reqResult', 'family.htm?familyID='+familyID, {
				onComplete : function() {clearFamily();
				document.getElementById('button').style.display="none";
				},
				parameters : Form.serialize(document.forms[0]),
				asynchronous : false,
				evalScripts : true
			});}else {
				alert('U have not made any changes');
			}
		}else
		{
			//
			document.forms[0].param.value = "manage";
			document.forms[0].rid.value =$jq('#relation').val();
			
			//$jq('#relation').removeAttr('disabled');
		//	if($jq('#cghscardfile').val()!='') {
				$jq.ajaxFileUpload(
			  		{
				   	url:"family.htm?"+$jq('#family').serialize(),
				   	secureuri:false,
				   	fileElementId :'cghscardfile',
				   	success: function (data, status) {
			  			$jq("#familyList").html($jq(data.body).html());
			  			clearFamily();
			  			// success
			  		}, error: function (data, status, e) {
				    		alert('File Upload failed');
				    	}
			  		
				 });	
			//}
			//
			
		/*document.forms[0].param.value = "manage";
		$jq('#relation').removeAttr('disabled');
		
		new Ajax.Updater('familyList', 'family.htm'+'?', {
		//new Ajax.Updater('familyList', 'family.htm'+'?'+dispUrl, {
			onComplete : function() {clearFamily();
			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : false,
			evalScripts : true
		});*/
			
			
			
	} 
	}else {
		alert(errorMessage);
	}//clearFamily();
}
function onChangeCGHS(val){
	if(val=='Y'){
		// $jq('#notMandatory').css("display","none");
		$jq('#mandatory').css("display","block");
	}else{
		// $jq('#notMandatory').css("display","block");
		$jq('#mandatory').css("display","none");
	}
	
}
function checkChangedValues(id) {
	var status = false;
	
	for(var i=0;i<JsonFamilyList.length;i++) {
		if(JsonFamilyList[i].id==id){
			if(JsonFamilyList[i].name.toUpperCase()!=document.getElementById('name').value.toUpperCase()) {
				status=true;
				break;
			}if(document.getElementById('genderm').checked && JsonFamilyList[i].gender.toUpperCase()!=document.getElementById('genderm').value.toUpperCase()) {
				status=true;
				break;
			}if(document.getElementById('genderf').checked && JsonFamilyList[i].gender.toUpperCase()!=document.getElementById('genderf').value.toUpperCase()) {
				status=true;
				break;
			}if($jq('#familydob').is(':visible') && JsonFamilyList[i].dob.toUpperCase()!=document.getElementById('familydob').value.toUpperCase()) {
				status=true;
				break;
			}if($jq('#familyage').is(':visible') && JsonFamilyList[i].age.toUpperCase()!=document.getElementById('familyage').value.toUpperCase()) {
				status=true;
				break;
			}if(document.getElementById('cghsApplicable').checked && JsonFamilyList[i].cghsFacility.toUpperCase()!=document.getElementById('cghsApplicable').value.toUpperCase()) {
				status=true;
				break;
			}if(document.getElementById('cghsNotApplicable').checked && JsonFamilyList[i].cghsFacility.toUpperCase()!=document.getElementById('cghsNotApplicable').value.toUpperCase()) {
				status=true;
				break;
			}if(document.getElementById('ltcApplicable').checked && JsonFamilyList[i].ltcFacility.toUpperCase()!=document.getElementById('ltcApplicable').value.toUpperCase()) {
				status=true;
				break;
			}if(document.getElementById('ltcNotApplicable').checked && JsonFamilyList[i].ltcFacility.toUpperCase()!=document.getElementById('ltcNotApplicable').value.toUpperCase()) {
				status=true;
				break;
			}if($jq('#dependentFrom').is(':visible') && JsonFamilyList[i].dependentFrom.toUpperCase()!=document.getElementById('dependentFrom').value.toUpperCase()) {
				status=true;
				break;
			}if(JsonFamilyList[i].maritalstatus.toUpperCase()!=document.getElementById('maritalstatus').value.toUpperCase()) {
				status=true;
				break;
			}if($jq('#addressTypeId').val()!='select' && JsonFamilyList[i].addressTypeId.toUpperCase()!=document.getElementById('addressTypeId').value.toUpperCase()) {
				status=true;
				break;
			}if($jq('#address1').is(':visible') && JsonFamilyList[i].address1.toUpperCase()!=document.getElementById('address1').value.toUpperCase()) {
				status=true;
				break;
			}if($jq('#address2').is(':visible') && JsonFamilyList[i].address2.toUpperCase()!=document.getElementById('address2').value.toUpperCase()) {
				status=true;
				break;
			}if($jq('#address3').is(':visible') && JsonFamilyList[i].address3.toUpperCase()!=document.getElementById('address3').value.toUpperCase()) {
				status=true;
				break;
			}if($jq('#city').is(':visible') && JsonFamilyList[i].city.toUpperCase()!=document.getElementById('city').value.toUpperCase()) {
				status=true;
				break;
			}if($jq('#stateId').is(':visible') && JsonFamilyList[i].stateId.toUpperCase()!=document.getElementById('stateId').value.toUpperCase()) {
				status=true;
				break;
			}if($jq('#districtId').is(':visible') && JsonFamilyList[i].districtId.toUpperCase()!=document.getElementById('districtId').value.toUpperCase()) {
				status=true;
				break;
			}if($jq('#pincode').is(':visible') && JsonFamilyList[i].pincode.toUpperCase()!=document.getElementById('pincode').value.toUpperCase()) {
				status=true;
				break;
			}if(JsonFamilyList[i].bloodGroup.toUpperCase()!=document.getElementById('bloodGroup').value.toUpperCase()) {
				status=true;
				break;
			}if(document.getElementById('employeedYes').checked && JsonFamilyList[i].employeed.toUpperCase()!=document.getElementById('employeedYes').value.toUpperCase()) {
				status=true;
				break;
			}if(document.getElementById('employeedNo').checked && JsonFamilyList[i].employeed.toUpperCase()!=document.getElementById('employeedNo').value.toUpperCase()) {
				status=true;
				break;
			}if($jq('#employeedType').is(':visible') && JsonFamilyList[i].employeedType.toUpperCase()!=document.getElementById('employeedType').value.toUpperCase()) {
				status=true;
				break;
			}if($jq('#earningMoney').is(':visible') && JsonFamilyList[i].earningMoney.toUpperCase()!=document.getElementById('earningMoney').value.toUpperCase()) {
				status=true;
				break;
			}if(document.getElementById('yes').checked && JsonFamilyList[i].residingWith.toUpperCase()!=document.getElementById('yes').value.toUpperCase()) {
				status=true;
				break;
			}if(document.getElementById('no').checked && JsonFamilyList[i].residingWith.toUpperCase()!=document.getElementById('no').value.toUpperCase()) {
				status=true;
				break;
			}if(JsonFamilyList[i].contactNumber.toUpperCase()!=document.getElementById('contactNumber').value.toUpperCase()) {
				status=true;
				break;
			}if(JsonFamilyList[i].declareDate.toUpperCase()!=document.getElementById('declareDate').value.toUpperCase()) {
				status=true;
				break;
			}if(document.getElementById('phtypeFlagYes').checked && JsonFamilyList[i].phtypeFlag.toUpperCase()!=document.getElementById('phtypeFlagYes').value.toUpperCase()) {
				status=true;
				break;
			}if(document.getElementById('phtypeFlagNo').checked && JsonFamilyList[i].phtypeFlag.toUpperCase()!=document.getElementById('phtypeFlagNo').value.toUpperCase()) {
				status=true;
				break;
			}if($jq('#disabilityId').is(':visible') && JsonFamilyList[i].disabilityId.toUpperCase()!=document.getElementById('disabilityId').value.toUpperCase()) {
				status=true;
				break;
			}if($jq('#adoptedYes').is(':visible') && $jq('#adoptedYes').is(':checked') && JsonFamilyList[i].adopted.toUpperCase()!=document.getElementById('adoptedYes').value.toUpperCase()) {
				status=true;
				break;
			}if($jq('#adoptedNo').is(':visible') && $jq('#adoptedNo').is(':checked') && JsonFamilyList[i].adopted.toUpperCase()!=document.getElementById('adoptedNo').value.toUpperCase()) {
				status=true;
				break;
			}if($jq('#adoptedDate').is(':visible') && JsonFamilyList[i].adoptedDate.toUpperCase()!=document.getElementById('adoptedDate').value.toUpperCase()) {
				status=true;
				break;
			}if($jq('#placeOfIssue').is(':visible') && JsonFamilyList[i].placeOfIssue.toUpperCase()!=document.getElementById('placeOfIssue').value.toUpperCase()) {
				status=true;
				break;
			}if($jq('#beneficiary').is(':visible') && JsonFamilyList[i].beneficiary.toUpperCase()!=document.getElementById('beneficiary').value.toUpperCase()) {
				status=true;
				break;
			}if($jq('#validFrom').is(':visible') && JsonFamilyList[i].validFrom.toUpperCase()!=document.getElementById('validFrom').value.toUpperCase()) {
				status=true;
				break;
			}if($jq('#validTo').is(':visible') && JsonFamilyList[i].validTo.toUpperCase()!=document.getElementById('validTo').value.toUpperCase()) {
				status=true;
				break;
			}
		}
	}
	return status;
}
function getFamilyDetails(){
	var jsoncell = {};
	jsoncell["PLACE_OF_ISSUE"] = document.getElementById('placeOfIssue').value;
	jsoncell["BENEFICIARY"] = document.getElementById('beneficiary').value;
	jsoncell["VALID_FROM"] = document.getElementById('validFrom').value;
	jsoncell["VALID_TO"] = document.getElementById('validTo').value;	
	jsoncell["NAME"] = document.getElementById('name').value;
	jsoncell["RELATION_ID"] = document.getElementById('relation').value;
	if(document.getElementById("genderm").checked==true){
		jsoncell["SEX"] = 'M';
	}
	if(document.getElementById("genderf").checked==true){
		jsoncell["SEX"] = 'F';
	}

	jsoncell["DOB"] = document.getElementById('familydob').value;
	jsoncell["AGE"] = document.getElementById('familyage').value;

	jsoncell["DEPENDENT_FROM"] = document.getElementById('dependentFrom').value;
	// jsoncell["DEPENDENT_FROM"] =
	// document.getElementById('dependentFrom').value;
	if(document.getElementById('adoptedNo').checked){
		jsoncell["Adopted"] = 'N';
	}else{
		jsoncell["Adopted"] = document.getElementById('adoptedYes').value;
	}	
	
	jsoncell["Adopted Date"] = document.getElementById('adoptedDate').value;

	jsoncell["MARITAL_STATUS_ID"] = document.getElementById('maritalstatus').value;
	jsoncell["ADDRESS_TYPE_ID"] = document.getElementById('addressTypeId').value;
	
	if(document.getElementById('addressTypeId').value==100){
		jsoncell["ADDRESS1"] = document.getElementById('address1').value;
		jsoncell["ADDRESS2"] = document.getElementById('address2').value;
		jsoncell["ADDRESS3"] = document.getElementById('address3').value;
		jsoncell["CITY"] = document.getElementById('city').value;
		if(document.getElementById('state').value=='select'){
			jsoncell["STATE_ID"] = "";
		}else{
			jsoncell["STATE_ID"] = document.getElementById('stateId').value;
		}
		if(document.getElementById('district').value=='select'){
			jsoncell["DISTRICT_ID"] = "";
		}else{
			jsoncell["DISTRICT_ID"] = document.getElementById('districtId').value;
		}
		jsoncell["PIN_CODE"] = document.getElementById('pincode').value;
	}else{
		jsoncell["ADDRESS1"] = '';
		jsoncell["ADDRESS2"] = '';
		jsoncell["ADDRESS3"] ='';
		jsoncell["CITY"] = '';
		jsoncell["STATE_ID"] = '0';
		jsoncell["DISTRICT_ID"] = '0';
		jsoncell["PIN_CODE"] = '';
	}
	jsoncell["BLOOD_GROUP"] = document.getElementById('bloodGroup').value;
	if(document.getElementById('employeedNo').checked){
		jsoncell["EMPLOYED"] = 'N';
	}else{
		jsoncell["EMPLOYED"] = document.getElementById('employeedYes').value;
	}
	if(document.getElementById('employeedType').value=='select'){
		jsoncell["EMPLOYED_TYPE"] = '';
		jsoncell["EARNING_MONEY"] = '';
	}else{
		jsoncell["EMPLOYED_TYPE"] = document.getElementById('employeedType').value;
		jsoncell["EARNING_MONEY"] = document.getElementById('earningMoney').value;
	}	
	
	if(document.getElementById('yes').checked==true){
		jsoncell["RESIDING_WITH"] = "Y";
	}else if(document.getElementById('no').checked==true){
		jsoncell["RESIDING_WITH"] = "N";
	}
	jsoncell["CONTACT_NUMBER"] = document.getElementById('contactNumber').value;
	jsoncell["FAMILY_DECLARE_DATE"] = document.getElementById('declareDate').value;
	if(document.getElementById('phtypeFlagYes').value=='Y' && document.getElementById('phtypeFlagYes').checked) {
		jsoncell["PH_FLAG"] = document.getElementById('phtypeFlagYes').value;
		jsoncell["DISABILITY_ID"] = document.getElementById('disabilityId').value;
	}
	else{
		jsoncell["PH_FLAG"] = document.getElementById('phtypeFlagNo').value;
		jsoncell["DISABILITY_ID"] = "0";
	}
	if(document.getElementById('cghsNotApplicable').checked)
		jsoncell["CGHS_FACILITY"] = "N";
	else
		jsoncell["CGHS_FACILITY"] = "Y";
	if(document.getElementById('ltcNotApplicable').checked)
		jsoncell["LTC_FACILITY"] =  "N";
	else
		jsoncell["LTC_FACILITY"] = "Y";
	
	return JSON.stringify(jsoncell);;
}
function editFamily(id,no,noIds) {
	familyID = id;
	document.forms[0].param.value="getMember";
	if(id==null || id==""){		
		document.forms[0].id.value = id; 
		new Ajax.Updater("viewData", 'family.htm'+'?'+dispUrl, {
			onComplete : function() {
				for(var i=0;i<=noIds;i++){
					if(document.getElementById('el'+i)!=null)
						document.getElementById('el'+i).style.display="none";
				}
			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : false,
			evalScripts : true
		});
			
	}else{
		document.forms[0].id.value = id;		
		var divid="";
		if(no!=null && no!=undefined)
		divid="createFamily"+no;
		else
			divid="createFamily";
		new Ajax.Updater(divid, 'family.htm', {
			onComplete : function() {
				for(var i=0;i<=noIds;i++){
					if(document.getElementById('el'+i)!=null)
						document.getElementById('el'+i).style.display="none";
				}
				document.getElementById('newbutton').style.display="none";
				document.getElementById('relation').disabled = true;
			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : false,
			evalScripts : true
		});
	}
}
function setFamilyDetails(familyjson,id)
{
	if(familyjson!=null && familyjson!="")
	{
	for ( var i = 0; i < familyjson.length; i++) {
		if (familyjson[i].id == id) {
			if(familyjson[i].ltcFacility=="Y") {
				document.getElementById('ltcApplicable').checked=true;
			}else {
				document.getElementById('ltcNotApplicable').checked=true;
			}
			if(familyjson[i].cghsFacility=="Y") {
				document.getElementById('cghsApplicable').checked=true;
			}else {
				document.getElementById('cghsNotApplicable').checked=true;
			}
			document.getElementById('id').value = familyjson[i].id;
			document.getElementById('name').value = familyjson[i].name;
			document.getElementById('relation').value = familyjson[i].relationId;
			if($jq('#relation').val() == '16'){
			$jq('#relation').attr("disabled",true);	
			}
			if(familyjson[i].phtypeFlag=="Y")
			document.getElementById('phtypeFlagYes').checked = true;
			if(familyjson[i].phtypeFlag=="N")
				document.getElementById('phtypeFlagNo').checked = true;
			document.getElementById('disabilityId').value = familyjson[i].disabilityId;
			if(familyjson[i].dob!=null && familyjson[i].dob!="")
				document.getElementById("dobselection").value="dob";
			else
				document.getElementById("dobselection").value="age";
			document.getElementById('familyage').value = familyjson[i].age;
			document.getElementById('familydob').value = familyjson[i].dob;
			if(familyjson[i].maritalstatus=="")
				document.getElementById('maritalstatus').value="0";
			else
			document.getElementById('maritalstatus').value = familyjson[i].maritalstatus;
			if(familyjson[i].addressTypeId=="")
				document.getElementById('addressTypeId').value="select";
			else if(familyjson[i].addressTypeId=="100") {
			document.getElementById('addressTypeId').value = familyjson[i].addressTypeId;
			document.getElementById('address1').value = familyjson[i].address1;
			document.getElementById('address2').value = familyjson[i].address2;
			document.getElementById('address3').value = familyjson[i].address3;
			document.getElementById('district').value = familyjson[i].district;
			document.getElementById('city').value = familyjson[i].city;
			document.getElementById('state').value = familyjson[i].stateId;
			} else {
			document.getElementById('addressTypeId').value = familyjson[i].addressTypeId;
			document.getElementById('address1').value = "";
			document.getElementById('address2').value = "";
			document.getElementById('address3').value = "";
			document.getElementById('district').value = "select";
			document.getElementById('city').value = "";
			document.getElementById('state').value = "select";
			}
		   if(familyjson[i].relation == "SELF"){
			   document.getElementById("earningSalary").style.display = "none";
			   if(familyjson[i].employeed=="Y"){
				   document.getElementById('employeedType').value="21";
			   }
			   if(familyjson[i].gender=="M"){
				   document.getElementById('genderm').checked=true;  
				}else{
					document.getElementById('genderf').checked=true;
				}
		   }
// if(familyjson[i].dependent=="" ||familyjson[i].dependent=="N")
// document.getElementById('dependentNo').checked = true;
// else if(familyjson[i].dependent=="Y")
// document.getElementById('dependentYes').checked = true;
// document.getElementById('dependentFrom').value = familyjson[i].dependentFrom;
		    document.getElementById('placeOfIssue').value = familyjson[i].placeOfIssue;
		    document.getElementById('beneficiary').value = familyjson[i].beneficiary;
		    document.getElementById('cghscardfile').value= familyjson[i].cghscardfile;
		    if(familyjson[i].cghsFacility=="Y") {
		    	onChangeCGHS('Y');	
		    }
		    if(familyjson[i].cghsFacility=="N") {
		    	onChangeCGHS('N');	
		    }
		    document.getElementById('validFrom').value = familyjson[i].validFrom;
		    document.getElementById('validTo').value = familyjson[i].validTo;		   		   
			if(familyjson[i].adopted=="" ||familyjson[i].adopted=="N")
				document.getElementById('adoptedNo').checked = true;
			else if(familyjson[i].adopted=="Y")
				document.getElementById('adoptedYes').checked = true;
			document.getElementById('adoptedDate').value = familyjson[i].adoptedDate;
			if(familyjson[i].employeed=="N"){
				document.getElementById('employeedNo').checked = true;
			}else if(familyjson[i].employeed=="Y"){
				document.getElementById('employeedYes').checked = true;
				document.getElementById('employeedType').value = familyjson[i].employeedType;
			document.getElementById('earningMoney').value = familyjson[i].earningMoney;
			}
			if(familyjson[i].residingWith=="Y")
				document.getElementById('yes').checked=true;
			else if(familyjson[i].residingWith=="N")
				document.getElementById('no').checked=true;
			document.getElementById('contactNumber').value = familyjson[i].contactNumber;
			document.getElementById('bloodGroup').value= familyjson[i].bloodGroup;
			document.getElementById('pincode').value= familyjson[i].pincode;
			document.getElementById('declareDate').value= familyjson[i].declareDate;
			setDistrictList(familyjson[i].district);
		}
	}
	}

	if (document.getElementById("dobselection") != null
			&& document.getElementById("dobselection").value == "dob") {
		document.getElementById("familydobirth").style.display = "block";
		document.getElementById("familyagediv").style.display = "none";
		document.getElementById("familyage").value = "";
		document.getElementById("dobselection").value="dob";
	} else if (document.getElementById("dobselection") != null
			&& document.getElementById("dobselection").value == "age"){
		document.getElementById("familyagediv").style.display = "block";
		document.getElementById("familydob").value = "";
		document.getElementById("familydobirth").style.display = "none";
		document.getElementById("dobselection").value="age";
	}
	else if (document.getElementById("dobselection") != null
			&& document.getElementById("dobselection").value == "select"){
		document.getElementById("familyagediv").style.display = "none";
		document.getElementById("familydob").value = "";
		document.getElementById("familydobirth").style.display = "none";
		document.getElementById("dobselection").value="select";
	}

	if(document.getElementById("relation")!=null)
	{
		var relation=document.getElementById("relation").options[document.getElementById("relation").selectedIndex].text;
		if(relation=="FATHER" || relation=="FATHER-IN-LAW" || relation=="HUSBAND")
		{
			document.getElementById("genderm").checked=true;
		}else if(relation=="MOTHER" || relation=="MOTHER-IN-LAW" || relation=="WIFE")
		{
			document.getElementById("genderf").checked=true;
			document.getElementById("genderm").checked=false;
		}else if(relation=="SON" || relation=="SON-IN-LAW" || relation=="BROTHER-IN-LAW" || relation=="BROTHER")
		{
			document.getElementById("genderm").checked=true;
		}else if(relation=="DAUGHTER" || relation=="DAUGHTER-IN-LAW" || relation=="SISTER-IN-LAW" || relation=="SISTER")
		{
			document.getElementById("genderf").checked=true;
		}else if(relation=="SELF"){
		
		}
		if(relation=="SON" || relation=="DAUGHTER"){
			document.getElementById("adopt").style.display = "block";
		}else {
			document.getElementById("adoptedfrom").style.display = "none";
			if (document.getElementById("adoptedYes").checked==true
					&& document.getElementById("adopted").value == "Y") {
				// document.getElementById("adopted").value="";
				document.getElementById("adoptedDate").value= "";
				document.getElementById("adopt").style.display = "none";
				document.getElementById("adoptedfrom").style.display = "none";
			} else if (document.getElementById("adoptedNo").checked==true ){
				document.getElementById("adoptedDate").value= "";
				document.getElementById("adoptedfrom").style.display = "none";
				document.getElementById("adopt").style.display = "none";
		
			}	
		}
	}
	
	if(document.getElementById("addressTypeId").value!="select")
	{
		if(document.getElementById("addressTypeId").value=="100")
		{
			document.getElementById("addressdiv").style.display="block";
			document.getElementById("familyPin").style.display="block";
		}else
		{
			document.getElementById("addressdiv").style.display="none";
			document.getElementById("familyPin").style.display="none";
		}
	}else
	{
		document.getElementById("addressdiv").style.display="none";
		document.getElementById("familyPin").style.display="none";
	}
/*
 * if (document.getElementById("dependentYes").checked==true &&
 * document.getElementById("dependentYes").value == "Y") {
 * document.getElementById("dependentdate").style.display = "block"; } else if
 * (document.getElementById("dependentNo").checked==true){
 * document.getElementById("dependentdate").style.display = "none";
 * document.getElementById("dependentFrom").value = ""; }
 */
	if (document.getElementById("adoptedYes").checked==true
			&& document.getElementById("adoptedYes").value == "Y") {
	// document.getElementById("adopted").value="";
		document.getElementById("adoptedfrom").style.display = "block";

	} else if (document.getElementById("adoptedNo").checked==true){
		document.getElementById("adoptedDate").value="";
		document.getElementById("adoptedfrom").style.display = "none";
	}
	if (document.getElementById("employeedYes").checked==true
			&& document.getElementById("employeedYes").value == "Y") {
		document.getElementById("emporgtype").style.display = "block";
		document.getElementById("earningSalary").style.display = "block";
	} else if (document.getElementById("employeedNo").checked==true){
		document.getElementById("emporgtype").style.display = "none";
		document.getElementById("employeedType").value = 'select';
		document.getElementById("earningMoney").value = '';
	}if(document.getElementById("phtypeFlagYes").checked==true && document.getElementById("phtypeFlagYes").value=="Y") {
		document.getElementById("disabilityType").style.display = "block";
	}else if(document.getElementById("phtypeFlagNo").checked==true) {
		document.getElementById("disabilityId").value='Select';
		document.getElementById("disabilityType").style.display = "none";
	}
	
}
function validateFamily(){
	var errorMessage="";
	var flag = true;
	if (document.forms[0].name.value == "") {
		errorMessage += "Please Enter Family Member Name\n";
		if (flag) {
			document.getElementById('name').focus();
			flag = false;
		}
	}
	if (document.getElementById('relation').value == "select") {
		errorMessage += "Please Select Relationship\n";
		if (flag) {
		document.getElementById('relation').focus();
		flag = false;
		}
	}else 
	{
		var relation=document.getElementById("relation").options[document.getElementById("relation").selectedIndex].text;
		if(relation=="FATHER" || relation=="FATHER-IN-LAW" || relation=="HUSBAND" || relation=="SON" || relation=="SON-IN-LAW")
		{
			if(!document.getElementById("genderm").checked)
			{

				errorMessage += "Please Select Correct Gender Option\n";

			}
			if(document.forms[0].id.value==""){
			if(document.getElementById('relation').value == "4"){
				 // var fromDate1 =
					// document.getElementById('relation').value;
				 for ( var i = 0; i < JsonFamilyList.length; i++){
					
						 if(JsonFamilyList[i].relation == $jq('#relation').find('option:selected').text()){
					     errorMessage += "Already You have a HUSBAND Realtionship\n";
					     }
				     
			      }
			   }
			if(document.getElementById('relation').value == "1"){
				 // var fromDate1 =
					// document.getElementById('relation').value;
				 for ( var i = 0; i < JsonFamilyList.length; i++){
					
						 if(JsonFamilyList[i].relation == $jq('#relation').find('option:selected').text()){
					     errorMessage += "Already You have a FATHER Realtionship\n";
					     }
				     
			      }
			   }
			if(document.getElementById('relation').value == "9"){
				 // var fromDate1 =
					// document.getElementById('relation').value;
				 for ( var i = 0; i < JsonFamilyList.length; i++){
					
						 if(JsonFamilyList[i].relation == $jq('#relation').find('option:selected').text()){
					     errorMessage += "Already You have a FATHER-IN-LAW Realtionship\n";
					     }
				     
			      }
			   }
			if(document.getElementById('relation').value == "16"){
				 // var fromDate1 =
					// document.getElementById('relation').value;
				 for ( var i = 0; i < JsonFamilyList.length; i++){
					
						 if(JsonFamilyList[i].relation == $jq('#relation').find('option:selected').text()){
					     errorMessage += "Already You have a SELF Realtionship\n";
					     }
				     
			      }
			   }
			}
		}else if(relation=="MOTHER" || relation=="MOTHER-IN-LAW" || relation=="WIFE" || relation=="DAUGHTER" || relation=="DAUGHTER-IN-LAW"){
			if(!document.getElementById("genderf").checked){
                      errorMessage += "Please Select Correct Gender Option\n";
                      }
		}
		if(document.forms[0].id.value==""){
		if(document.getElementById('relation').value == "2"){
			 // var fromDate1 = document.getElementById('relation').value;
			 for ( var i = 0; i < JsonFamilyList.length; i++){
				
					 if(JsonFamilyList[i].relation == $jq('#relation').find('option:selected').text()){
				     errorMessage += "Already You have a MOTHER Realtionship\n";
				     }
			     
		      }
		   }
		if(document.getElementById('relation').value == "10"){
			 // var fromDate1 = document.getElementById('relation').value;
			 for ( var i = 0; i < JsonFamilyList.length; i++){
				
					 if(JsonFamilyList[i].relation == $jq('#relation').find('option:selected').text()){
				     errorMessage += "Already You have a MOTHER-IN-LAW Realtionship\n";
				     }
			     
		      }
		   }
		/*
		 * if(document.getElementById('relation').value == "15"){ //var
		 * fromDate1 = document.getElementById('relation').value; for ( var i =
		 * 0; i < JsonFamilyList.length; i++){
		 * 
		 * if((JsonFamilyList[i].relation ==
		 * $jq('#relation').find('option:selected').text()) &&
		 * ($jq('#maritalstatus').val()==15) &&
		 * ($jq('#ltcApplicable').val()=='Y')){ errorMessage += "You can Not Add
		 * a SISTER Realtionship With Ltc Dependent And martial Status As
		 * Married\n"; } } }
		 */
		if(document.getElementById('relation').value == "12"){
			 // var fromDate1 = document.getElementById('relation').value;
			 for ( var i = 0; i < JsonFamilyList.length; i++){
				
					 if(JsonFamilyList[i].relation == $jq('#relation').find('option:selected').text()){
				     errorMessage += "Already You have a SISTER-IN-LAW Realtionship\n";
				     }
			     
		      }
		   }
		if(document.getElementById('relation').value == "3"){
			 // var fromDate1 = document.getElementById('relation').value;
			 for ( var i = 0; i < JsonFamilyList.length; i++){
				
					 if(JsonFamilyList[i].relation == $jq('#relation').find('option:selected').text()){
				     errorMessage += "Already You have a WIFE Realtionship\n";
				     }
			     
		      }
		   }
		}
	}
	if (document.getElementById('genderf').checked == false
			&& document.getElementById('genderm').checked == false) {
		errorMessage += "Please Select Gender\n";
		if (flag) {
			document.getElementById('genderm').focus();
			flag = false;
		}
	}
	if(document.getElementById('dobselection').options[document.getElementById('dobselection').selectedIndex].text=="Select"){
		errorMessage += "Please Select Date of Birth/Age\n";
		if (flag) {
		document.getElementById('dobselection').focus();
		flag = false;
		}
	}else{
		if (document.getElementById('familydobirth').style.display=="block" && document.getElementById('familydob').value == "") {
			errorMessage += "Please Enter Date of Birth\n";
			if (flag) {
			document.getElementById('familydob').focus();
			flag = false;
			}
		}
		if (document.getElementById('familyagediv').style.display=="block" && document.getElementById('familyage').value == "") {
			errorMessage += "Please Enter Age\n";
			if (flag) {
			document.getElementById('familyage').focus();
			flag = false;
			}
			
		}
	}
	if(document.getElementById('maritalstatus').value=='select') {
		errorMessage += "Please select marital status\n";
		if (flag) {
			document.getElementById('maritalstatus').focus();
			flag = false;
			}
	}
	if(document.getElementById('employeedYes').checked) {
		
		if(document.getElementById('employeedType').value=='select') {
			errorMessage += "Please Select Employment Type\n";
			if (flag) {
			document.getElementById('employeedType').focus();
			flag = false;
			}
		}
		
		if(document.getElementById('earningMoney').value=='' && document.getElementById('relation').value !='16') {
			errorMessage += "Please Enter Earning Salary\n";
			if (flag) {
			document.getElementById('earningMoney').focus();
			flag = false;
			}
		}
		
	}
	if(document.getElementById('phtypeFlagYes').checked) {
		if(document.getElementById('disabilityId').value=='Select') {
			errorMessage += "Please Select Disability Type\n";
			if (flag) {
			document.getElementById('disabilityId').focus();
			flag = false;
			}
		}
	}
	if(compareDate($jq('#validFrom').val(),$jq('#validTo').val())) {
		errorMessage += "Invalid Dates \n";
		if (status) {
			status = false;
			$jq('#validFrom').focus();
		}
	}	
	if($jq('#mandatory').is(':visible')){
	if ($jq('#placeOfIssue').val() == '') {
			errorMessage += "Please Enter CGHS Place Of Issue\n";
			if (flag) {
				status = false;
				$jq('#placeOfIssue').focus();
			}

		}
		if ($jq('#beneficiary').val() == '') {
			errorMessage += "Please Enter CGHS Benificiary Number\n";
			if (status) {
				status = false;
			}
		}
		
		/*
		 * if($jq('#earningMoney').val()=='') { errorMessage += "Please Enter
		 * Earning Salary\n"; if (status) { status = false;
		 * $jq('#earningMoney').focus(); } }
		 */
		if ($jq('#validFrom').val() == '') {
			errorMessage += "Please Select From Date Of Validity Of CGHS\n";
			if (status) {
				status = false;
				$jq('#validFrom').focus();
			}
		}
		if ($jq('#validTo').val() == '') {
			errorMessage += "Please Select To Date Of Validity Of CGHS\n";
			if (status) {
				status = false;
				$jq('#validTo').focus();
			}
		}
	}
	return errorMessage;
}
function setGender()
{
	if(document.getElementById("relation")!=null)
	{
		var relation=document.getElementById("relation").options[document.getElementById("relation").selectedIndex].text;
		if(relation=="FATHER" || relation=="FATHER-IN-LAW" || relation=="HUSBAND")
		{
			document.getElementById("genderm").checked=true;
			document.getElementById("maritalstatus").value="0";
		}else if(relation=="MOTHER" || relation=="MOTHER-IN-LAW" || relation=="WIFE")
		{
			document.getElementById("genderf").checked=true;
			document.getElementById("genderm").checked=false;
			document.getElementById("maritalstatus").value="0";
		}else if(relation=="SON" || relation=="SON-IN-LAW" || relation=="BROTHER-IN-LAW" || relation=="BROTHER")
		{
			document.getElementById("genderm").checked=true;
			document.getElementById("maritalstatus").value="0";
		}else if(relation=="DAUGHTER" || relation=="DAUGHTER-IN-LAW" || relation=="SISTER-IN-LAW" || relation=="SISTER")
		{
			document.getElementById("genderf").checked=true;
			document.getElementById("maritalstatus").value="0";
		}if(relation=="SON" || relation=="DAUGHTER"){
			document.getElementById("adopt").style.display = "block";
		}else {
			if (document.getElementById("adoptedYes").value == "Y") {
				document.getElementById("adopt").style.display = "none";
				document.getElementById("adoptedfrom").style.display = "none";
				document.getElementById("adoptedDate").value= "";
			
			} else if (document.getElementById("adoptedNo") == "N" ){
				document.getElementById("adopt").style.display = "none";			
			}
			/*
			 * document.getElementById("adoptedDate").value= "";
			 * document.getElementById("adopted").value="";
			 * document.getElementById("adopt").style.display = "none";
			 * document.getElementById("adoptedfrom").style.display = "none";
			 */
			
		}		
			
	}
}
function deleteFamily(id) {
	var del = confirm("Do you want to delete this item?");
	if (del == true) {
	
		var remarks=prompt("Deletion Remarks","Please enter remarks");
		if(!remarks.trim()==''){			
			document.forms[0].param.value = "delete";
			document.forms[0].remarks.value=remarks;
			document.forms[0].id.value = id;
			new Ajax.Updater('familyList', 'family.htm'+'?'+dispUrl, {
				onComplete : function() {
			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : false,
			evalScripts : true
		});
	}
		else alert("You should enter remarks.\n And \n Please try again");
	}
}
function resetGeneral() {
	document.getElementById('userSfid').value = "";
	document.getElementById('nameInServiceBook').value = "";
	document.getElementById('firstName').value = "";
	document.getElementById('middleName').value = "";
	document.getElementById('lastName').value = "";
	$jq('#relationId').val('Select');
	$jq('#fatherSpouseTitle').val('Select');
	document.getElementById('relationName').value="";
	document.getElementById('personalNumber').value = "";
	document.getElementById('residenceNo').value = "";
	document.getElementById('internalNo').value = "";
	document.getElementById('dob').value = "";
	document.getElementById('maritalId').value = "Select";
	document.getElementById('title').value = "Select";
	document.getElementById('religion').value = "Select";
	document.getElementById('communityId').value = "Select";
	document.getElementById('nationalityId').value = "Select";
	document.getElementById('blood').value = "Select";
	document.getElementById('motherTongue').value = "";
	document.getElementById('height').value = "";
	document.getElementById('heightType').value = "Select";
	document.getElementById('idMarks').value = "";
	document.getElementById('counter').value = "500";
	document.getElementById('male').checked = false;
	document.getElementById('female').checked = false;
}
function resetProfessional() {

	document.getElementById('designationId').value = "Select";
	document.getElementById('employmentTypeId').value = "Select";
	document.getElementById('appointmentTypeId').value = "Select";
	document.getElementById('directorateId').value = "Select";
	document.getElementById('divisionId').value = "Select";
	document.getElementById('reservationId').value = "Select";
	document.getElementById('handicapId').value = "Select";
	document.getElementById('dojGovt').value = "";
	document.getElementById('dojDrdo').value = "";
	document.getElementById('dojAsl').value = "";
	document.getElementById('seniorityDate').value = "";
	document.getElementById('lastPromotion').value = "";
	document.getElementById('workedYears').value = "";
	document.getElementById('uptoDate').value = "";
}
function resetOther() {

	document.getElementById('dispersaryNumber').value = "Select";
	document.getElementById('cgshNumber').value = "";
	document.getElementById('pranNo').value = "";
	document.getElementById('ppaNo').value = "";
	document.getElementById('gpfAcNo').value = "";

}
function funOnKeyPress(e,type){
	var key;
	if (window.event) {
		key = window.event.keyCode;
	} else {
		key = e.which;
	}
	if (key == 13) {
		if(type=='editEmployee'){
			getEmployeeDetails();
		}else if(type=='changeSfid'){
			setEmpSFID();
		}
	}
}

function nextProfessional() {
	var errorMessage = "";
	var flag = true;
	var dob=document.getElementById('dob').value
	var check;
	var status = true;
   
	
	if (!validateNotEmpty(document.getElementById('nameInServiceBook').value)) {
		errorMessage += "Please Enter Name In Service Book \n";
		if (flag) {
			document.getElementById('nameInServiceBook').focus();
			flag = false;
		}
	}
	if (!document.getElementById('nameInServiceBook').value == '') {
		check=document.getElementById('nameInServiceBook').value;
		if(validateChar(check)) {
			errorMessage += "Name In Service Book Takes Only Character \n";
			document.getElementById('nameInServiceBook').focus();
			flag = false;
		}
	}
	if (document.getElementById('title').value == 'Select') {
		errorMessage += "Please Select Title \n";
		if (flag) {
			document.getElementById('title').focus();
			flag = false;
		}
	}
	if (!validateNotEmpty(document.getElementById('firstName').value)) {
		errorMessage += "Please Enter FirstName \n";
		if (flag) {
			document.getElementById('firstName').focus();
			flag = false;
		}

	}else if (!document.getElementById('firstName').value == '') {
		check=document.getElementById('firstName').value;
		if(validateChar(check)) {
			errorMessage += "First Name Takes Only Character \n";
			if (flag) {
			document.getElementById('firstName').focus();
			flag = false;
			}
		}
	}
	if (!validateNotEmpty(document.getElementById('lastName').value)) {
		errorMessage += "Please Enter LastName \n";
		if (flag) {
			document.getElementById('lastName').focus();
			flag = false;
		}

	}else if (!document.getElementById('lastName').value == '') {
		check=document.getElementById('lastName').value;
		if(validateChar(check)) {
			errorMessage += "Last Name Takes Only Character \n";
			if (flag) {
			document.getElementById('lastName').focus();
			flag = false;
			}
		}
	}
	if (!document.getElementById('middleName').value == '') {
		check=document.getElementById('middleName').value;
		if(validateChar(check)) {
			errorMessage += "Middle Name Takes Only Character \n";
			if (flag) {
			document.getElementById('middleName').focus();
			flag = false;
			}
		}
	}
	
	 if(document.getElementById('relationId').value =="25"){
			if (!validateNotEmpty(document.getElementById('relationName').value)) {
				errorMessage += "Please Enter FatherName \n";

				
				if (flag) {
					document.getElementById('relationName').focus();
					flag = false;
				}
			}
              
		 }
	 if(document.getElementById('relationId').value == "25"){
		 if ($jq('#fatherSpouseTitle').val() == '0') {
				errorMessage += "Please Select Title of the Father Name\n";
				$jq('#fatherSpouseTitle').focus();
				flag = false;
			}
           
		 }
	
	
	 if(document.getElementById('relationId').value =="26"){
			if (!validateNotEmpty(document.getElementById('relationName').value)) {
				errorMessage += "Please Enter SpouseName \n";
				errorMessage +="please select Spouse \n";
				if (flag) {
					document.getElementById('relationName').focus();
					flag = false;
				}
			}
		    }
	 if(document.getElementById('relationId').value == "26"){
		 if ($jq('#fatherSpouseTitle').val() == '0') {
				errorMessage += "Please Select Title of the Spouse Name\n";
				$jq('#fatherSpouseTitle').focus();
				flag = false;
			}
           
		 }
	 if(document.getElementById('height').value == ''){
		 errorMessage += "Please Select Height  \n";
			if (flag) {
			document.getElementById('height').focus();
			flag = false;
			}
	 }
	 if (!(document.getElementById('height').value == '')&& (document.getElementById('heightType').value=='Select')){
			errorMessage += "Please Select Height Type \n";
			if (flag) {
			document.getElementById('heightType').focus();
			flag = false;
			}
			}
			else if(!document.getElementById('height').value == ''){
			check=document.getElementById('height').value;
			if(!validateSignedFloat(check) || !((document.getElementById('height').value).length >= '3')|| !((document.getElementById('height').value).length <'5')) {
				errorMessage += "Height Is Not Valid \n";
				if (flag) {
				document.getElementById('height').focus();
				flag = false;
				}
			} else if(document.getElementById('heightType').value=="2"){ 
				
				var hight=(document.getElementById('height').value).split(".");
				document.getElementById('storeHight').value = parseFloat((hight[0]*12+parseFloat(hight[1]))*2.54).toFixed(2);
				
				
			}
		}
		if(document.getElementById('heightType').value=="2" || document.getElementById('heightType').value=="1"){ 
		if (!validateNotEmpty(document.getElementById('height').value)) {
			errorMessage += "Please Enter height \n";
			if (flag) {
				document.getElementById('height').focus();
				flag = false;
			}
		}
		
	}
	if (!validateNotEmpty(document.getElementById('dob').value)) {
		errorMessage += "Please Enter Data of Birth \n";
		if (flag) {
			document.getElementById('dob').focus();
			flag = false;
		}
	} else {
		if (compareDate(dob, checkDate)) {
			errorMessage += "Date of Birth is not valid\n";
			if (flag) {
				document.getElementById('dob').focus();
				flag = false;
			}
		}
	}

	if (document.getElementById('female').checked == false
			&& document.getElementById('male').checked == false) {
		errorMessage += "Please Select Gender \n";
		if (flag) {
			document.getElementById('male').focus();
			flag = false;
		}
	}
	if (document.getElementById('maritalId').value == 'Select') {
		errorMessage += "Please Select Marital Status \n";
		if (flag) {
			document.getElementById('maritalId').focus();
			flag = false;
		}
	}
	if (document.getElementById('religion').value == 'Select') {
		errorMessage += "Please Select Religion \n";
		if (flag) {
			document.getElementById('religion').focus();
			flag = false;
		}
	}
	if (document.getElementById('communityId').value == 'Select') {
		errorMessage += "Please Select Community \n";
		if (flag) {
			document.getElementById('communityId').focus();
			flag = false;
		}
	}
	if (document.getElementById('nationalityId').value == 'Select') {
		errorMessage += "Please Select Nationality \n";
		if (flag) {
			document.getElementById('nationalityId').focus();
			flag = false;
		}
	}
	if (!document.getElementById('residenceNo').value == '') {
		check=document.getElementById('residenceNo').value;
		if(!validateInteger(check)) {
			errorMessage += "Residency Number Takes Only Number \n";
			document.getElementById('residenceNo').focus();
			flag = false;
		}
	}
	if (!document.getElementById('personalNumber').value == '') {
		check=document.getElementById('personalNumber').value;
		if(!validateInteger(check)) {
			errorMessage += "Mobile Number Takes Only Number \n";
			document.getElementById('personalNumber').focus();
			flag = false;
		}
	}
	if (!validateNotEmpty(document.getElementById('personalNumber').value)) {
		errorMessage += "Please Enter  Mobile number \n";
		if (flag) {
			document.getElementById('personalNumber').focus();
			flag = false;
		}

	}
	if (!validateNotEmpty(document.getElementById('internalNo').value)) {
		errorMessage += "Please Enter  Internal No  \n";
		if (flag) {
			document.getElementById('internalNo').focus();
			flag = false;
		}

	}
	if (!document.getElementById('internalNo').value == '') {
		check=document.getElementById('internalNo').value;
		if(!validateInteger(check)) {
			errorMessage += "Internal Number Takes Only Number\n";
			document.getElementById('internalNo').focus();
			flag = false;
		}
	}
	if (!document.getElementById('motherTongue').value == '') {
		check=document.getElementById('motherTongue').value;
		if(validateChar(check)) {
			errorMessage += "Mother Tongue Takes Only Charector\n";
			document.getElementById('motherTongue').focus();
			flag = false;
		}
	}
	if (!validateNotEmpty(document.getElementById('motherTongue').value)) {
		errorMessage += "Please Enter  MotherTongue \n";
		if (flag) {
			document.getElementById('motherTongue').focus();
			flag = false;
		}

	}
	
	if (flag) {
		
		document.getElementById('titleName').value = document.getElementById('title')[document
				.getElementById('title').selectedIndex].text
		document.forms[0].communityName.value = document.getElementById('communityId')[document
				.getElementById('communityId').selectedIndex].text
		document.getElementById('religionName').value = document.forms[0].religion[document
				.getElementById('religion').selectedIndex].text
		document.getElementById('maritalName').value = document.forms[0].maritalId[document
				.getElementById('maritalId').selectedIndex].text
		document.getElementById('nationalityName').value = document.forms[0].nationalityId[document
				.getElementById('nationalityId').selectedIndex].text
		document.getElementById('bloodName').value = document.forms[0].blood[document
				.getElementById('blood').selectedIndex].text
				
		if(document.forms[0].blood[document.getElementById('blood').selectedIndex].text=='Select') {
			document.getElementById('bloodName').value=""; }
		else {
			document.getElementById('bloodName').value = document.forms[0].blood[document.getElementById('blood').selectedIndex].text
		}

		document.forms[0].param.value = "professional";
		document.forms[0].action = "employee.htm";
		document.forms[0].submit();

	} else {

		alert(errorMessage);
	}
}

function nextOther() {

	var errorMessage = "";
	var flag = true;
	var dateCheck;
	if (document.getElementById('appointmentTypeId').value == 'Select') {
		 errorMessage += "Please Select AppointmentType \n";
		 if (flag) {
			 document.getElementById('appointmentTypeId').focus();
			 flag = false;
		 }
	 }
	if (document.getElementById('employmentTypeId').value == 'Select') {
		 errorMessage += "Please Select EmploymentType \n";
		 if (flag) {
			 document.getElementById('employmentTypeId').focus();
			 flag = false;
		 }
	 }
	 
	if (document.getElementById('designationId').value == 'Select') {
		errorMessage += "Please Select Designation \n";
		if (flag) {
			document.getElementById('designationId').focus();
			flag = false;
		}
	}

	if (document.getElementById('directorateId').value == 'Select') {
		errorMessage += "Please Select Department \n";
		if (flag) {
			document.getElementById('directorateId').focus();
			flag = false;
		}
	}
	 if(document.getElementById('reservationId').value == "1"){
		 if ($jq('#handicapId').val() == 'Select') {
				errorMessage += "Please Select PH /VH /EH Type\n";
				$jq('#handicapId').focus();
				flag = false;
			}
           
		 }
	if (document.getElementById('dojGovt').value == '') {
		 errorMessage += "Please Enter Doj Govt \n";
		 if (flag) {
			 document.getElementById('dojGovt').focus();
			 flag = false;
		 }
	 }
	if (document.getElementById('dojDrdo').value == '') {
		 errorMessage += "Please Enter Doj Drdo \n";
		 if (flag) {
			 document.getElementById('dojDrdo').focus();
			 flag = false;
		 }
	 }
	if (document.getElementById('dojAsl').value == '') {
		 errorMessage += "Please Enter Doj Asl \n";
		 if (flag) {
			 document.getElementById('dojAsl').focus();
			 flag = false;
		 }
	 }
	if (document.getElementById('seniorityDate').value == '') {
		 errorMessage += "Please Enter Seniority Date \n";
		 if (flag) {
			 document.getElementById('seniorityDate').focus();
			 flag = false;
		 }
	 }
	if (!document.getElementById('dojGovt').value == '') {
		
		dateCheck=document.getElementById('dojGovt').value;
		
		if(compareDate(check,dateCheck)) {
			errorMessage += "Date of Join In Govr Is Invalid \n";
			if (flag) {
				document.getElementById('dojGovt').focus();
				flag = false;
			}
		}
		if(!compareDate(document.getElementById('dojDrdo').value,dateCheck)) {
			errorMessage += "Date of Join In Drdo Is Invalid \n";
			if (flag) {
				document.getElementById('dojDrdo').focus();
				flag = false;
			}
		}
		
	}
	if (!document.getElementById('dojAsl').value == '') {
		if(!compareDate(document.getElementById('dojAsl').value,document.getElementById('dojDrdo').value)) {
			errorMessage += "Date of Join In Asl Is Invalid \n";
			if (flag) {
				document.getElementById('dojAsl').focus();
				flag = false;
			}
		}
		
	}
	if (document.getElementById('lastPromotion').value == '') {
		errorMessage += "Please Enter Promotion Date \n";
		if (flag) {
			document.getElementById('lastPromotion').focus();
			flag = false;
		}
	}
	 if (!document.getElementById('lastPromotion').value == '') {
			
		  dateCheck=document.getElementById('lastPromotion').value;
		   var pro = document.getElementById('seniorityDate').value;
	if (!compareDate(dateCheck,pro)) {
		
			errorMessage += "please select Promotion Rank is After Seniority Date\n";
			if (flag) {
				document.getElementById('lastPromotion').focus();
				flag = false;
			}
		}
    }
	/*
	 * if (!document.getElementById('lastPromotion').value == '') {
	 * if(compareDate(document.getElementById('dojAsl').value,document.getElementById('lastPromotion').value)) {
	 * errorMessage += "Date of Last Promotion Is Invalid \n"; if (flag) {
	 * document.getElementById('lastPromotion').focus(); flag = false; } } }
	 */
	
	if (flag) {
		if(document.forms[0].appointmentTypeId[document.getElementById('appointmentTypeId').selectedIndex].text=='Select'){
			document.forms[0].appName.value=""; }
		else{
			document.forms[0].appName.value = document.forms[0].appointmentTypeId[document
			  .getElementById('appointmentTypeId').selectedIndex].text
		}
		if(document.forms[0].employmentTypeId[document.getElementById('employmentTypeId').selectedIndex].text=='Select') {
			document.forms[0].empName.value="";}
			else {
		document.forms[0].empName.value = document.forms[0].employmentTypeId[document
				.getElementById('employmentTypeId').selectedIndex].text
				}
		if(document.forms[0].divisionId[document.getElementById('divisionId').selectedIndex].text=='Select') {
			document.forms[0].divisionName.value="";}
		else {
		
		document.forms[0].divisionName.value = document.forms[0].divisionId[document
				.getElementById('divisionId').selectedIndex].text
		}
		document.forms[0].directorateName.value = document.forms[0].directorateId[document
				.getElementById('directorateId').selectedIndex].text
		document.forms[0].designationName.value = document.forms[0].designationId[document
				.getElementById('designationId').selectedIndex].text
		if(document.forms[0].reservationId[document.getElementById('reservationId').selectedIndex].text=='Select') {
			document.forms[0].reservationName.value=""; }
		else {
		document.forms[0].reservationName.value = document.forms[0].reservationId[document
				.getElementById('reservationId').selectedIndex].text
		}
		if(document.forms[0].handicapId[document.getElementById('handicapId').selectedIndex].text=='Select') {
			document.forms[0].handicapName.value=""; }
		else {
			document.forms[0].handicapName.value = document.forms[0].handicapId[document
				.getElementById('handicapId').selectedIndex].text
		}
		if(document.forms[0].joinType[document.getElementById('joinType').selectedIndex].text=='Select') {
			document.forms[0].joinName.value=""; }
		else {
			document.forms[0].joinName.value = document.forms[0].joinType[document
				.getElementById('joinType').selectedIndex].text
		}
		

		document.forms[0].param.value = "other";
		document.forms[0].action = "employee.htm?divisionId="+document.getElementById('divisionId').value;
		document.forms[0].submit();

	} else {
		alert(errorMessage);
	}
}

function backToProfessional() {
	document.forms[0].param.value = "BackProfessional";
	document.forms[0].action = "employee.htm";
	document.forms[0].submit();

}

function backToGeneral() {

	document.forms[0].param.value = "BackHome";
	document.forms[0].action = "employee.htm";
	document.forms[0].submit();
}
function backToOther() {
	document.forms[0].param.value = "BackOther";
	document.forms[0].action = "employee.htm";
	document.forms[0].submit();

}

function saveEmployee() {
	var saveStatus = confirm("Do you want to submit details ?");
	if (saveStatus) {
		document.forms[0].param.value = "submit";
		document.forms[0].action = "employee.htm";
		document.forms[0].submit();
	}
}

function maleOrFemale() {

	if (document.getElementById('female').checked == true) {
		document.getElementById('husbandName').disabled = false;
		document.getElementById('husbandOccupation').disabled = false;
		document.getElementById('husbandNonOccupation').disabled = false;

	}
	if (document.getElementById('male').checked == true) {
		document.getElementById('husbandName').disabled = true;
		document.getElementById('husbandOccupation').disabled = true;
		document.getElementById('husbandNonOccupation').disabled = true;
	}

}
function employeeOrNot() {
	if (document.getElementById('husbandOccupation').checked == true) {
		document.getElementById('husbandOraganization').disabled = false;
	}
	if (document.getElementById('husbandNonOccupation').checked == true) {
		document.getElementById('husbandOraganization').disabled = true;
	}
}

function fathOccupation() {
	if (document.getElementById('fatherOccupation').checked == true) {
		document.getElementById('fatherOraganization').disabled = false;
	}
	if (document.getElementById('fatherNonOccupation').checked == true) {
		document.getElementById('fatherOraganization').disabled = true;
	}
}
function selectReservation() {
	if (document.getElementById('reservationId').value == '1') {
		document.getElementById('handicapId').disabled = false;
		document.getElementById('joinType').disabled = true;
		document.getElementById('joinType').value = "Select"
		document.getElementById('workedYears').disabled = true;
		document.getElementById('workedYears').value = "";

	} else if (document.getElementById('reservationId').value == '2') {
		document.getElementById('joinType').disabled = false;
		document.getElementById('workedYears').disabled = false;
		document.getElementById('handicapId').value = "Select";
		document.getElementById('handicapId').disabled = true;
	} else {
		document.getElementById('joinType').disabled = true;
		document.getElementById('joinType').value = "Select"
		document.getElementById('workedYears').disabled = true;
		document.getElementById('workedYears').value = "";
		document.getElementById('handicapId').value = "Select";
		document.getElementById('handicapId').disabled = true;

	}

}

function uptoDateAllowence() {
	if (document.getElementById('yesgroupAllowence').checked) {
		document.getElementById('uptoDate').disabled = false;
		document.getElementById('date_start_trigger6').disabled = false;
	} else if (!document.getElementById('yesgroupAllowence').checked) {
		document.getElementById('uptoDate').disabled = true;
		document.getElementById('date_start_trigger6').disabled = true;
		document.getElementById('uptoDate').value = "";
	}
}
function displayInPromotion() {
	document.getElementById('lastPromotion').value = document
			.getElementById('seniorityDate').value
}
function displayInDRDO() {

	document.getElementById('dojDrdo').value = document
			.getElementById('dojGovt').value
}
function viewEmployeeData() {
	
	var errorMessage='';
	var status=true;
	
	if (document.getElementById('dispersaryNumber').value == "Select") {
		errorMessage += "Please Select Dispensary Number\n";
		if (status) {
			document.getElementById('dispersaryNumber').focus();
			status = false;
		}
	}
	if (document.forms[0].dispersaryNumber[document.getElementById('dispersaryNumber').selectedIndex].text =='Select') {
		document.forms[0].dispensaryName.value =""; }
	else {
	document.forms[0].dispensaryName.value = document.forms[0].dispersaryNumber[document
			.getElementById('dispersaryNumber').selectedIndex].text; 
	}
	if (document.getElementById('pranNo').value == "") {
		errorMessage += "Please Select PRA Number\n";
		if (status) {
			document.getElementById('pranNo').focus();
			status = false;
		}
	}
	if (document.getElementById('gpfAcNo').value == "") {
		errorMessage += "Please Select GPF A/C Number\n";
		if (status) {
			document.getElementById('gpfAcNo').focus();
			status = false;
		}
	}
	if(status) {
		//one line add by bkr
		document.getElementById('dispersaryNumber').value='425';
		document.forms[0].param.value = "preview";
		document.forms[0].action = "employee.htm";
		document.forms[0].submit();
	}else {
		alert(errorMessage);
	}
}
// end employee creation

// Address details start
function addressDetails() {
	document.forms[0].param.value = "home";
	document.forms[0].action = "address.htm";
	document.forms[0].submit();
}

function manageAddress(type) {
	//alert("start");
	var errorMessage = "";
	var status = true;
	if (document.getElementById('addressTypeId').value == "Select") {
		errorMessage += "Please Select Address Type\n";
		if (status) {
			document.getElementById('addressTypeId').focus();
			status = false;
		}
	}
	if (document.getElementById('address1').value == "") {
		errorMessage += "Please Enter Address Line1\n";
		if (status) {
			document.getElementById('address1').focus();
			status = false;
		}
	}if (document.getElementById('city').value == "") {
		errorMessage += "Please Enter City\n";
		if (status) {
			document.getElementById('city').focus();
			status = false;
		}
	}
	if (document.getElementById('state').value == "0") {
		errorMessage += "Please select state \n";
		if (status) {
			document.getElementById('state').focus();
			status = false;
		}
	}
	if (document.getElementById('district').value == "0") {
		errorMessage += "Please select district \n";
		if (status) {
			document.getElementById('state').focus();
			status = false;
		}
	}
	if (document.getElementById('nearestRyStation').value == "") {
		errorMessage += "Please Enter Nearest  Railway Station\n";
		if (status) {
			document.getElementById('nearestRyStation').focus();
			status = false;
		}
	}
	if (document.getElementById('email').value != "") {
		if (!validateEmail(document.getElementById('email').value)) {
			errorMessage += "Please Enter valid Email ID\n";
			if (status) {
				document.getElementById('email').focus();
				status = false;
			}
		}
	}
	if(!document.getElementById('dispensaryNumber').checked) {
		document.getElementById('dispensaryNumber').value="N";
	}else{
		document.getElementById('dispensaryNumber').value="Y";
	}
	if (status) {
		if (type == 'addressRequest') {
			status=false;
			if(checkChanges()){
				document.forms[0].changedValues.value=getAddressDetails();
				document.forms[0].param.value = "submitRequest";
				new Ajax.Updater(divid, 'address.htm?addressTypeId='+document.forms[0].addressTypeId.value, {
					onComplete : function() {
					clearAddress();
					},
					parameters : Form.serialize(document.forms[0]),
					asynchronous : false,
					evalScripts : true
				});
			}else {
				alert("U Have Not Made Any Changes");
			}
		} else {
			if(document.getElementById('id').value==""){
				if(document.getElementById('addressTypeId')[document.getElementById('addressTypeId').selectedIndex].text=="HOMETOWN2"){
					if(JsonAddressList!=null && JsonAddressList.length>0){
						
						for ( var i = 0; i < JsonAddressList.length; i++) {
							if (JsonAddressList[i].addressType=="HOMETOWN1") {
								document.getElementById('addFlag').value="false";
							}else if(JsonAddressList[i].addressType!="PERMANENT" && JsonAddressList[i].addressType!="PRESENT" && JsonAddressList[i].addressType!="HOMETOWN2"){
								alert("Already you have  Hometown1 Address Details Not required HOmetown2 address");
								if (status) {
									document.getElementById('addressTypeId').focus();
									status = false;
								}
								
							}
						}
					}else{
						alert("Please Enter Hometown1 Address Details");
						if (status) {
							document.getElementById('addressTypeId').focus();
							status = false;
						}
						
					}
				}/*
					 * else
					 * if(document.getElementById('addressTypeId')[document.getElementById('addressTypeId').selectedIndex].text=="HOMETOWN1"){
					 * if(JsonAddressList!=null && JsonAddressList.length>0){
					 * 
					 * for ( var i = 0; i < JsonAddressList.length; i++) { if
					 * (JsonAddressList[i].addressType=="HOMETOWN2") {
					 * alert("Hometown2 Address Details Existed"); status=false; } } } }
					 */
			}
			
			if(status){
				document.forms[0].param.value = "manage";
				new Ajax.Updater('addressList', 'address.htm?addressTypeId='+document.forms[0].addressTypeId.value+'&'+dispUrl, {
					onComplete : function() {clearAddress();
					},
					parameters : Form.serialize(document.forms[0]),
					asynchronous : false,
					evalScripts : true
				});	
			}
		}
	} else {
		alert(errorMessage);
	}
	
}
function checkChanges() {

	var status = false;
	for(var key in validation){
		if(key=="address1"){
			if(validation[key].toUpperCase()!=document.getElementById('address1').value.toUpperCase()) {
				status=true;
				break;
			}
		}else if(key=="address2"){
			if(validation[key].toUpperCase()!=document.getElementById('address2').value.toUpperCase()) {
				status=true;
				break;
			}
		}else if(key=="address3"){
			if(validation[key].toUpperCase()!=document.getElementById('address3').value.toUpperCase()){
				status=true;
				break;
			}
		}else if(key=="careOfAddress"){
			if(validation[key].toUpperCase()!=document.getElementById('careOfAddress').value.toUpperCase()){
				status=true;
				break;
			}
		}else if(key=="city"){
			if(validation[key].toUpperCase()!=document.getElementById('city').value.toUpperCase()){
				status=true;
				break;
			}
		}else if(key=="district"){
			if(validation[key]!=document.getElementById('district').value.toUpperCase()){
				status=true;
				break;
			}
		}else if(key=="email"){
			if(validation[key].toUpperCase()!=document.getElementById('email').value.toUpperCase()){
				status=true;
				break;
			}
		}else if(key=="mobile"){
			if(validation[key].toUpperCase()!=document.getElementById('mobile').value.toUpperCase()){
				status=true;
				break;
			}
		}else if(key=="phone"){
			if(validation[key].toUpperCase()!=document.getElementById('phone').value.toUpperCase()){
				status=true;
				break;
			}
		}else if(key=="nearestAirport"){
			if(validation[key].toUpperCase()!=document.getElementById('nearestAirport').value.toUpperCase()){
				status=true;
				break;
			}
		}else if(key=="nearestRyStation"){
			if(validation[key].toUpperCase()!=document.getElementById('nearestRyStation').value.toUpperCase()) {
				status=true;
				break;
			}
		}else if(key=="pincode"){
			if(validation[key].toUpperCase()!=document.getElementById('pincode').value.toUpperCase()) {
				status=true;
				break;
			}
		}else if(key=="state"){
			if(validation[key].toUpperCase()!=document.getElementById('state').value.toUpperCase()) {
				status=true;
				break;
			}
		}
		else if(key=="dispensaryNumber"){
			if(validation['addressTypeId']=="2") {
				if(validation[key].toUpperCase()!=document.getElementById('dispensaryNumber').value.toUpperCase()) {
					status=true;
					break;
				}
			}else{
				document.getElementById('dispensaryNumber').value=null;
			}
		}
		
	}
	return status;
}
function getAddressDetails(){
	var jsoncell = {};
	jsoncell["ADDRESS1"] = document.getElementById('address1').value;
	jsoncell["ADDRESS2"] = document.getElementById('address2').value;
	jsoncell["ADDRESS3"] = document.getElementById('address3').value;
	jsoncell["CARE_OF_ADDRESS"] = document.getElementById('careOfAddress').value;
	jsoncell["CITY"] = document.getElementById('city').value;
	jsoncell["STATE"] = document.getElementById('state').value;
	jsoncell["DISTRICT"] = document.getElementById('district').value;
	jsoncell["PINCODE"] = document.getElementById('pincode').value;
	jsoncell["PHONE_NUMBER"] = document.getElementById('phone').value;
	jsoncell["MOBILE_NUMBER"] = document.getElementById('mobile').value;
	jsoncell["EMAIL"] = document.getElementById('email').value;
	jsoncell["NEAREST_RLY_STN"] = document.getElementById('nearestRyStation').value;
	jsoncell["NEAREST_AIRPORT"] = document.getElementById('nearestAirport').value;
	jsoncell["ADDRESS_TYPE_ID"] = document.getElementById('addressTypeId').value;
	jsoncell["DISPENSARY_NUMBER"] = document.getElementById('dispensaryNumber').value;
	return JSON.stringify(jsoncell);	
}
function editAddress(addJson, addid) {

	for ( var i = 0; i < addJson.length; i++) {
		if (addJson[i].id == addid) {
			document.getElementById('address1').value = addJson[i].address1;
			document.getElementById('address2').value = addJson[i].address2;
			document.getElementById('address3').value = addJson[i].address3;
			document.getElementById('careOfAddress').value = addJson[i].careOfAddress;
			document.getElementById('city').value = addJson[i].city;
			document.getElementById('state').value = addJson[i].stateId;
			document.getElementById('pincode').value = addJson[i].pincode;
			document.getElementById('phone').value = addJson[i].phone;
			document.getElementById('mobile').value = addJson[i].mobile;
			document.getElementById('email').value = addJson[i].email;
			document.getElementById('nearestRyStation').value = addJson[i].nearestRyStation;
			document.getElementById('addressTypeId').value = addJson[i].addressTypeId;
			document.getElementById('addressTypeId').disabled = true;
			document.getElementById('id').value = addJson[i].id;
			document.getElementById('nearestAirport').value = addJson[i].nearestAirport;
			setDistrictList(addJson[i].districtId);
			if(addJson[i].addressTypeId=="2"){
				document.getElementById('dispensary').style.display = "block";
				if(addJson[i].dispensaryNumber=="Y"){
					document.getElementById('dispensaryNumber').checked=true;
					document.getElementById('dispensaryNumber').value="Y";}
				else
					document.getElementById('dispensaryNumber').checked=false;
			}else {
				document.getElementById('dispensaryNumber').checked=false;
				document.getElementById('dispensary').style.display = "none";
			}
		}
	}

}
function setDistrictList(district) {
	if (districtJSONList != null) {
		document.getElementById("district").options.length = 1;
		if (document.getElementById("district").value != "select") {
			for ( var i = 0; i < districtJSONList.length; i++) {
				if (districtJSONList[i].stateId == document
						.getElementById("state").value) {
					var len = document.getElementById("district").options.length++;
					document.getElementById("district").options[len].text = districtJSONList[i].name;
					document.getElementById("district").options[len].value = districtJSONList[i].id;
				}
			}
			document.getElementById("district").value = district;
		}
	}
}
function deleteAddress(id) {
	var del = confirm("Do you want to delete this item?");
	if (del == true) {
		document.getElementById('param').value = "delete";
		document.getElementById('id').value = id;
		new Ajax.Updater('addressList', 'address.htm', {
			onComplete : function() {clearAddress();
			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : false,
			evalScripts : true
		});
	}
}
function clearAddress(type) {
	document.forms[0].type.value=type;
	if(type=="edit"){
		document.forms[0].addressTypeId.disabled = true;	
	}else{
		if(document.getElementById('addressTypeId')!=null){
			document.forms[0].addressTypeId.value = "Select";
			document.forms[0].addressTypeId.disabled = false;
		}
	}	
	if(document.getElementById('address1')!=null)
		document.forms[0].address1.value = "";
	if(document.getElementById('address2')!=null)
		document.forms[0].address2.value = "";
	if(document.getElementById('address3')!=null)
		document.forms[0].address3.value = "";
	if(document.getElementById('careOfAddress')!=null)
		document.forms[0].careOfAddress.value = "";
	if(document.getElementById('city')!=null)
		document.forms[0].city.value = "";
	if(document.getElementById('district')!=null)
		document.forms[0].district.value = "0";
	if(document.getElementById('state')!=null)
		document.forms[0].state.value = "0";
	if(document.getElementById('pincode')!=null)
		document.forms[0].pincode.value = "";
	if(document.getElementById('phone')!=null)
		document.forms[0].phone.value = "";
	if(document.getElementById('mobile')!=null)
		document.forms[0].mobile.value = "";
	if(document.getElementById('email')!=null)
		document.forms[0].email.value = "";
	if(document.getElementById('nearestRyStation')!=null)
		document.forms[0].nearestRyStation.value = "";
	if(document.getElementById('checkAdd')!=null)
		document.getElementById('checkAdd').checked=false;
	if(document.getElementById('id')!=null)
		document.forms[0].id.value = "";
	/*
	 * if(document.getElementById('district')!=null)
	 * document.getElementById("district").options.length = 1;
	 */
	if(document.getElementById('stateId')!=null)
		document.getElementById("stateId").value="0"
	if(document.getElementById('districtId')!=null)
		document.getElementById("districtId").options.length = 1;
	if(document.getElementById('nearestAirport')!=null)
		document.getElementById("nearestAirport").value = "";
	// document.getElementById('valueEnable').style.display = "none";
	if(document.getElementById('addFlag')!=null)
		document.getElementById('addFlag').value="";
	if(document.getElementById('selectAdd')!=null)
		document.getElementById("selectAdd").options.length = 1;
	if($jq("#dispensary").is(':visible')){
	if(document.getElementById('dispensaryNumber').checked)
		document.getElementById('dispensaryNumber').checked=false;
	}
	
}
function fillAdd(){
	var name=document.getElementById('selectAdd');
	var temp = document.getElementById('addressTypeId').value;
	/*
	 * if (document.getElementById('selectAdd').value=="Select"){
	 * clearAddress(); document.getElementById('addressTypeId').value =temp; }
	 * else
	 */ if(JsonAddressList!=null && JsonAddressList.length>0){
		for ( var i = 0; i < JsonAddressList.length; i++) {
			if(name[name.selectedIndex].text==JsonAddressList[i].addressType){
				document.getElementById('address1').value = JsonAddressList[i].address1;
				document.getElementById('address2').value = JsonAddressList[i].address2;
				document.getElementById('address3').value = JsonAddressList[i].address3;
				document.getElementById('city').value = JsonAddressList[i].city;
			    document.getElementById('state').value = JsonAddressList[i].stateId;
				document.getElementById('district').value = JsonAddressList[i].districtId;
			    document.getElementById('pincode').value = JsonAddressList[i].pincode;
				document.getElementById('phone').value = JsonAddressList[i].phone;
				document.getElementById('mobile').value = JsonAddressList[i].mobile;
				document.getElementById('email').value = JsonAddressList[i].email;
				document.getElementById('nearestRyStation').value = JsonAddressList[i].nearestRyStation;
				document.getElementById('nearestAirport').value = JsonAddressList[i].nearestAirport;
				setDistrictList(JsonAddressList[i].districtId);
			}
		}
	}
	
}

function sameAsAddress(){
	document.getElementById("selectAdd").options.length = 1;
	if(JsonAddressList!=null && JsonAddressList.length>0){
		var name=document.getElementById('addressTypeId');
		for ( var i = 0; i < JsonAddressList.length; i++) {
			if(name[name.selectedIndex].text!=JsonAddressList[i].addressType){
				if(name[name.selectedIndex].text=="HOMETOWN1" && JsonAddressList[i].addressType=="HOMETOWN2"){
					alert("Hometown2 Address Details Existed");
					name.focus();
					name.value="Select";
				}else{
					var len = document.getElementById("selectAdd").options.length++;
					document.getElementById("selectAdd").options[len].text = JsonAddressList[i].addressType;
					document.getElementById("selectAdd").options[len].value = JsonAddressList[i].addressTypeId;
				}
				
			}
		}
	}
	if(document.getElementById('addressTypeId')=="2" || document.getElementById('addressTypeId')[document.getElementById('addressTypeId').selectedIndex].text=="PRESENT"){
		document.getElementById('dispensary').style.display = "block";
	}else
		document.getElementById('dispensary').style.display = "none";
		
}
// address details end

// Passprot details start
function enableFamilyList() {
	if (document.getElementById('passPortFor').value == "FAMILY") {
		document.getElementById('familyMember').disabled = false;
	} else {
		document.getElementById('familyMember').disabled = true;
	}
	if (document.getElementById('relation').value == "SON" || document.getElementById('relationship').value == "DAUGHTER") {
		document.getElementById('adopted').disabled = false;
	}else {
		document.getElementById('adopted').disabled = true;
	}
	
}
function passportDetails() {
	document.forms[0].param.value = "home";
	document.forms[0].action = "passport.htm";
	document.forms[0].submit();
}

function managePassport() {
	var flag=true;
	var pnumber, iplace;
	var errorMessage = "";
	var validUpTo = "";
	var pasportType = "";
	var passportFor = "";
	var familyMember = "";
	pnumber = document.getElementById('PassportNumber').value;
	iplace = document.getElementById('issuePlace').value;
	validUpTo = document.getElementById('validUpto').value;
	pasportType = document.getElementById('passportType').value;
	passportFor = document.getElementById('passPortFor').value;
	familyMember = document.getElementById('familyMember').value;
	if (pasportType == null || pasportType == "Select") {
		errorMessage += "Please Select Passport Type \n";
		if (flag) {
			document.getElementById('passportType').focus();
			flag = false;
		}
		
	}
	if (passportFor == null || passportFor == "Select") {
		errorMessage += "Please Select Passport For \n";
		if (flag) {
			document.getElementById('passPortFor').focus();
			flag = false;
		}
	}
	if (passportFor == "FAMILY") {
		if (familyMember == null || familyMember == "Select") {
			errorMessage += "Please Select Family Member \n";
			if (flag) {
				document.getElementById('familyMember').focus();
				flag = false;
			}
		}
	}
	if (pnumber == null || pnumber == "") {
		errorMessage += "Please Enter Passport Number\n";
		if (flag) {
			document.getElementById('PassportNumber').focus();
			flag = false;
		}
	}
	if (iplace == null || iplace == "") {
		errorMessage += "Please Enter Issued Place\n";
		if (flag) {
			document.getElementById('issuePlace').focus();
			flag = false;
		}
	}
	if (validUpTo == null || validUpTo == "") {
		errorMessage += "Please Enter ValidUpto Date\n";
		if (flag) {
			document.getElementById('validUpto').focus();
			flag = false;
		}
	}
	if (flag) {
		document.forms[0].param.value='manage';
		new Ajax.Updater('passportList',
				'passport.htm?'+dispUrl, {
					onComplete : function() {
					},
					parameters : Form.serialize(document.forms[0]),
					asynchronous : false,
					evalScripts : true
				});
	} else {
		alert(errorMessage);
	}
}

function editPassport(passportid) {
	for ( var i = 0; i < JsonPassportList.length; i++) {
		if (JsonPassportList[i].id == passportid) {
			document.getElementById('id').value = JsonPassportList[i].id;
			document.getElementById('passportType').disabled = true;
			document.getElementById('passportType').value = JsonPassportList[i].passportType;
			document.getElementById('passPortFor').value = JsonPassportList[i].passPortId;
			if (JsonPassportList[i].familyMember != "") {
				document.getElementById('familyMember').value = JsonPassportList[i].familyMember;
				document.getElementById('familyMember').disabled = false;
			} else {
				document.getElementById('familyMember').value = "Select";
				document.getElementById('familyMember').disabled = true;
			}
			document.getElementById('PassportNumber').value = JsonPassportList[i].passportNumber;
			document.getElementById('issuePlace').value = JsonPassportList[i].issuePlace;
			document.getElementById('validUpto').value = JsonPassportList[i].validUpto;
			document.getElementById('details').value = JsonPassportList[i].details;
			document.getElementById('remarks').value = JsonPassportList[i].remarks;
			if (JsonPassportList[i].details.length > 0) {
				document.getElementById('counter1').value = 500;
				document.getElementById('counter1').value -= JsonPassportList[i].details.length;
			}
			if (JsonPassportList[i].remarks.length > 0) {
				document.getElementById('counter2').value = 500;
				document.getElementById('counter2').value -= JsonPassportList[i].remarks.length;
			}
		}
	}
}

function enablePassportValues(value) {
	if (value != null && value != "") {

		if (value == "view" || value == "manage" || value == "editPassport"
				|| value == "delete" || value == "viewPassport") {
			document.getElementById("createPassport").style.display = "block";
			document.getElementById("passportList").style.display = "block";

		} else {
			document.getElementById("createPassport").style.display = "none";
			document.getElementById("passportList").style.display = "none";
		}
	}
}
function clearPassport() {
	document.getElementById('passportType').value = "Select";
	document.getElementById('passportType').disabled = false;
	document.getElementById('passPortFor').value = "Select";
	document.getElementById('familyMember').value = "Select";
	document.getElementById('familyMember').disabled = true;
	document.getElementById('PassportNumber').value = "";
	document.getElementById('issuePlace').value = "";
	document.getElementById('validUpto').value = "";
	document.getElementById('details').value = "";
	document.getElementById('remarks').value = "";
	document.forms[0].id.value = "";
	document.getElementById('counter1').value = "500";
	document.getElementById('counter2').value = "500";
}
function deletePassport(id) {
	var del = confirm("Do you want to delete this item?");
	if (del == true) {
		document.forms[0].id.value=id;
		document.forms[0].param.value='delete';
		new Ajax.Updater('passportList',
			'passport.htm?beanType=PassportBean', {
				onComplete : function() {
				},
				parameters : Form.serialize(document.forms[0]),
				asynchronous : false,
				evalScripts : true
			});
	}	
}

// passport details end

// Qualification details starts
function addQualification() {
	document.forms[0].param.value = "home";
	document.forms[0].action = "qualification.htm";
	document.forms[0].submit();
}
function clearQualification() {
	if(document.getElementById('degree')!=null)
		document.getElementById('degree').value = "Select";
	if(document.getElementById('qualification')!=null)
		document.forms[0].qualification.value = "Select";
	if(document.getElementById('discipline')!=null)
		document.forms[0].discipline.value = "0";
	if(document.getElementById('specilisation')!=null)
		document.forms[0].specilisation.value = "";
	if(document.getElementById('year')!=null)
		document.forms[0].year.value = "Select";
	if(document.getElementById('university')!=null)
		document.forms[0].university.value = "";
	if(document.getElementById('sponsored1')!=null)
		document.getElementById("sponsored1").checked = true;
	if(document.getElementById('sponsored2')!=null)
		document.getElementById("sponsored2").checked = false;
	if(document.getElementById('sponsored1')!=null){
		if (document.getElementById("sponsored1").checked == true) {
			document.getElementById("incentive").disabled = true;
		}
	}
	if(document.getElementById('divisionId')!=null)
		document.forms[0].divisionId.value = "";
	document.getElementById('qualAttainedYES').checked = false;
    document.getElementById('qualAttainedNO').checked = false; 
    if(document.getElementById('incentive')!=null)
		document.forms[0].incentive.value = "";
	if(document.getElementById('id')!=null)
		document.forms[0].id.value = "";
	if(document.getElementById('marks')!=null)
		document.getElementById('marks').value = "0";
	if(document.getElementById('totalPercentage')!=null)
		document.getElementById('totalPercentage').value = "";
	if(document.getElementById('grade')!=null)
		document.getElementById('grade').value = "";
	enableRTIncentive();
}


function enablequalValues(value) {
	if (value != null && value != "") {
		var val = value.split(",");
		if ((val[0] == "view") || (val[0] == "manage") || (val[0] == "delete")
				|| (val[0] == "edit") || (val[0] == "viewQualificationDetails")) {
			document.getElementById("createQualification").style.display = "block";
			document.getElementById("qualificationList").style.display = "block";
		} else {
			document.getElementById("createQualification").style.display = "none";
			document.getElementById("qualificationList").style.display = "none";
		}
	}
}
function manageQualification() {
	var flag=true;
	var qualification;
	var discipline;
	var passing;
	var degree;
	var university;
	var errorMessage = "";
	qualification = document.forms[0].qualification.value;
	discipline = document.forms[0].discipline.value;
	passing = document.forms[0].year.value;
	degree = document.getElementById('degree').value;
	university = document.getElementById('university').value;
	
	if (degree == "Select") {
		errorMessage += "Please Select Degree \n";
		if(flag){
			document.getElementById('degree').focus();
			flag=false;
		}
	}
	if (qualification == "Select") {
		errorMessage += "Please Select Qualification \n";
		if(flag){
			document.getElementById('qualification').focus();
			flag=false;
		}
	}
	if (discipline == "0") {
		errorMessage += "Please Select Discipline \n";
		if(flag){
			document.getElementById('discipline').focus();
			flag=false;
		}
	}
	if (university == "") {
		errorMessage += "Please Enter University \n";
		if(flag){
			document.getElementById('university').focus();
			flag=false;
		}
	}
	if (passing == "Select") {
		errorMessage += "Please Select Year of Passing \n";
		if(flag){
			document.getElementById('year').focus();
			flag=false;
		}
	}
	if ($jq('#qualAttainedYES').is(':checked')==false && $jq('#qualAttainedNO').is(':checked')==false) {
		errorMessage += "Please Select Qualification Attained While DRDO \n";
		if(flag){
			flag=false;
		}
	}
	if ($jq('#qualAttainedYES').is(':checked')==true) {
		if($jq('#sponsored1').is(':checked')==false && $jq('#sponsored2').is(':checked')==false){
		errorMessage += "Please Select R&T Scheme \n";
		if(flag){
			flag=false;
		  }
	    }
	}
	if(flag){
	var marksType=document.getElementById('marks');
	if (marksType[marksType.selectedIndex].text=="PERCENTAGE" && document.getElementById('totalPercentage').value != "") {
		var str = document.getElementById('totalPercentage').value.split(".");
		if (!(parseInt(str[0]) <= 100)) {
			errorMessage += "Please Enter Total Percentage lessthan 100 \n";
			if(flag){
				document.getElementById('totalPercentage').focus();
				flag=false;
			}
		} else if (!(parseInt(str[0]) >= 0)) {
			errorMessage += "Please Enter Valid Total Percentage \n";
			if(flag){
				document.getElementById('totalPercentage').focus();
				flag=false;
			}
		} else {
			if (str[1] != null) {
				if (str[1].length > 2) {
					str[1] = str[1].substring(0, 2);
					document.getElementById('totalPercentage').value = str[0]
							+ "." + str[1];

				}
				if (str[1].length == 0) {
					document.getElementById('totalPercentage').value = str[0]
							+ ".00";
				}
				if (str[1].length == 1) {
					document.getElementById('totalPercentage').value = str[0]
							+ "." + str[1] + "0";
				}
			} else {
				document.getElementById('totalPercentage').value = str[0]
						+ ".00";
			}
		}
	}
	}
	if (flag) {
		
		document.forms[0].param.value = "manage";
		new Ajax.Updater('qualificationList', 'qualification.htm'+'?'+dispUrl, {
			onComplete : function() {clearQualification();
			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : false,
			evalScripts : true
		});	
	} else {
		alert(errorMessage);
		// document.getElementById('totalPercentage').focus();
	}
}
function deleteQualification(id) {
	var del = confirm("Do you want to delete this item?");
	if (del == true) {
		document.forms[0].param.value = "delete";
		document.getElementById('id').value = id;
		new Ajax.Updater('qualificationList', 'qualification.htm'+'?'+dispUrl, {
			onComplete : function() {clearQualification();
			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : false,
			evalScripts : true
		});
		
	}
}

function editQualification(qualificationJson, qualificationid) {
	for ( var i = 0; i < qualificationJson.length; i++) {
		if (qualificationJson[i].id == qualificationid) {
			document.forms[0].qualification.value = qualificationJson[i].qualification;
			document.forms[0].discipline.value = qualificationJson[i].discipline;
			document.forms[0].specilisation.value = qualificationJson[i].specilisation;
			document.forms[0].university.value = qualificationJson[i].university;
			document.forms[0].year.value = qualificationJson[i].year;
			document.forms[0].divisionId.value = qualificationJson[i].divisionId;
			if (document.getElementById("sponsored1").value == qualificationJson[i].sponsored) {
				document.getElementById("sponsored1").checked = qualificationJson[i].sponsored;
				document.getElementById("incentive").disabled = true;
				document.getElementById("incentive").value = '';
			}
			if (document.getElementById("sponsored2").value == qualificationJson[i].sponsored) {
				document.getElementById("sponsored2").checked = qualificationJson[i].sponsored;
				document.getElementById("incentive").disabled = false;
				document.forms[0].incentive.value = qualificationJson[i].incentive;
			}
			if(qualificationJson[i].qualAttainedDRDO=='Yes'){
				document.getElementById('qualAttainedYES').checked=true;
			}else if(qualificationJson[i].qualAttainedDRDO=='No'){
				document.getElementById('qualAttainedNO').checked=true;
			}else{
				document.getElementById('qualAttainedYES').checked=false;
				document.getElementById('qualAttainedNO').checked=false;
			}
				// document.forms[0].qualAttained.value =
				// qualificationJson[i].qualAttainedDRDO;
			// document.forms[0].incentive.value =
			// qualificationJson[i].incentive;

			document.forms[0].id.value = qualificationJson[i].id;
			document.getElementById('degree').value = qualificationJson[i].degree;
			document.getElementById('marks').value = qualificationJson[i].marks;
			document.getElementById('totalPercentage').value = qualificationJson[i].totalPercentage;
			document.getElementById('grade').value = qualificationJson[i].grade;
			enableRTIncentive();
		}
	}
}
function enableRTIncentive(){
	// var qawdrdo=document.getElementById('qualAttainedYES');
	if(document.getElementById('qualAttainedYES').checked == true){
		document.getElementById('rtscheme').style.display = "block";
		document.getElementById('Incentive').style.display = "block";
	}else if(document.getElementById('qualAttainedNO').checked == true){
		document.getElementById('incentive').value='';
		document.getElementById('sponsored1').value='';
		document.getElementById('sponsored2').value='';
		document.getElementById('rtscheme').style.display = "none";
		document.getElementById('Incentive').style.display = "none";
	}else{
		document.getElementById('rtscheme').style.display = "none";
		document.getElementById('Incentive').style.display = "none";
	}
}

function enableInsentives() {
	if (document.getElementById("sponsored1").checked == true) {
		document.getElementById('incentive').value='';
		document.getElementById("incentive").disabled = true;
	}
	if (document.getElementById("sponsored2").checked == true) {

		document.getElementById("incentive").disabled = false;
	}
}

// Qualification details ends=====

// division/directorate
function directorate() {
	document.forms[0].param.value = "dirhome";
	document.forms[0].action = "master.htm";
	document.forms[0].submit();
}

// Trailing Details Begin

function addTraining() {
	document.forms[0].param.value = "home";
	document.forms[0].action = "training.htm";
	document.forms[0].submit();
}

function manageTraining() {
	var errorMessage = "";
	var status = true;
	if (document.getElementById('course').value == '') {
		errorMessage += "Please Enter Course \n";
		if (status) {
			document.getElementById('course').focus();
			status = false;
		}
	}
	if($jq('#fromDate').val() != '' && $jq('#toDate').val() != '' && !compareDate($jq('#toDate').val(),$jq('#fromDate').val())) {
		errorMessage += "Invalid Dates \n";
		if (status) {
			status = false;
			$jq('#fromDate').focus();
		}
	}
	if (status) {
		document.forms[0].param.value='manage';
		new Ajax.Updater('trainingList',
				'training.htm?'+dispUrl, {
					onComplete : function() {
					},
					parameters : Form.serialize(document.forms[0]),
					asynchronous : false,
					evalScripts : true
				});
	} else {
		alert(errorMessage);
	}
}
function deleteTraining(trainingId) {

	var del = confirm("Do you want to delete this item?");
	if (del == true) {
		document.forms[0].id.value = trainingId;
		document.forms[0].param.value = "delete";
		new Ajax.Updater('trainingList',
				'training.htm?'+dispUrl, {
					onComplete : function() {
					},
					parameters : Form.serialize(document.forms[0]),
					asynchronous : false,
					evalScripts : true
				});
	}
}
function editTraining(id, course, subject, area, org, year, duration, fdate,todate,json) {
	// document.forms[0].id.value = id;
	document.forms[0].id.value = id;
	document.getElementById('course').value = unescape(course);
	document.getElementById('subject').value = unescape(subject);
	document.getElementById('area').value = area;
	document.getElementById('institute').value = org;
	document.getElementById('year').value = year;
	document.getElementById('duration').value = duration;
	document.getElementById('fromDate').value = fdate;
	document.getElementById('toDate').value = todate;
	masterID = "";
	if(json!=null && json!="") {
		var properties=JSON.parse(json);
		document.forms[0].id.value = json.id;
		document.getElementById('course').value = properties.Course;
		document.getElementById('subject').value = properties.Subject;
		document.getElementById('area').value = properties["Training Area"];
		document.getElementById('institute').value = properties.Institute;
		document.getElementById('year').value = properties.Year;
		document.getElementById('duration').value = properties.Duration;
		document.getElementById('fromDate').value = properties["From Date"];
		document.getElementById('toDate').value = properties["To Date"];
	}

}
function resetTraining() {
	document.getElementById('course').value = "";
	document.getElementById('subject').value = "";
	document.getElementById('area').value = "0";
	document.getElementById('institute').value = "";
	document.getElementById('year').value = "0";
	document.getElementById('duration').value = "";
	document.getElementById('fromDate').value = "";
	document.getElementById('toDate').value = "";
	document.forms[0].id.value="" ;
}

function enableValues1(value) {
	if (value != null && value != "") {

		if (value == "home") {
			document.getElementById("createTraining").style.display = "none";
			document.getElementById("trainingList").style.display = "none";

		} else {
			document.getElementById("createTraining").style.display = "block";
			document.getElementById("trainingList").style.display = "block";
		}
	}
}
// Trailing Details End

// designation script
function editDesignation(desigID, categoryID,subCategoryID, groupID, type, designation,
		shortform,serviceType,desig,orderNo,alias) {
	designationID = desigID;
	document.getElementById('category').value = categoryID;
	document.getElementById('group').value = groupID;
	document.getElementById('typeValue').value = trimAll(type);
	document.getElementById('designationId').value = designation;
	document.getElementById('shortForm').value = shortform;
	document.getElementById('description').value = desig;
	document.getElementById('orderNo').value = orderNo;
	document.getElementById('desigAlias').value = alias;
	if(serviceType=="Y"){
		document.getElementById('serviceTypeM').checked = true;
	}else if(serviceType=="N"){
		document.getElementById('serviceTypeN').checked = true;
	}else{
		document.getElementById('serviceTypeN').checked	= false;
		document.getElementById('serviceTypeM').checked = false;
	}
	increaseTextWidth('designationId');
	getSubCategory();
}
function getSubCategory(){
	$jq('#subCategory').find('option').remove().end();
	categoryId=$jq("#category").val();
	for ( var i = 0; i < jsonSubCategoryList.length; i++) {
		if (jsonSubCategoryList[i].categoryID== categoryId) {	
			$jq("#subCategory").append(
					'<option value=' + jsonSubCategoryList[i].id + '>'
							+ jsonSubCategoryList[i].name + '</option>');
		}
	}
}
function editQual(id, name,code ,description,flag) {
	qualificationId = id;
	document.getElementById('name').value = name;
	document.getElementById('shortForm').value = code;
	if(flag=="Y"){
		document.getElementById('flagY').checked = true;
	}else if(flag=="N"){
		document.getElementById('flagN').checked = true;
	}else{
		document.getElementById('flagY').checked	= false;
		document.getElementById('flagN').checked = false;
	}
	document.getElementById('description').value = description;
	if (description.length > 0) {
		document.getElementById('counter').value = 500;
		document.getElementById('counter').value -= description.length;
	}
}
function editDiscipline(id, name,code ,description) {
	disciplineId = id;
	document.getElementById('name').value = name;
	document.getElementById('shortForm').value = code;
	document.getElementById('description').value = description;
	if (description.length > 0) {
		document.getElementById('counter').value = 500;
		document.getElementById('counter').value -= description.length;
	}
}
function editReligion(id, name,religion ,description) {
	religionId = id;
	document.getElementById('name').value = name;
	if(religion=="Y"){
		document.getElementById('religionY').checked = true;
	}else if(religion=="N"){
		document.getElementById('religionN').checked = true;
	}else{
		document.getElementById('religionY').checked	= false;
		document.getElementById('religionY').checked = false;
	}
	document.getElementById('description').value = description;
	if (description.length > 0) {
		document.getElementById('counter').value = 500;
		document.getElementById('counter').value -= description.length;
	}
}
function editPin(id, name, pinNumber) {
	pinId = id;
	$jq('#name').find('option').end();
	$jq("#name").append('<option value=' + name + '>' + name + '</option>');
	document.getElementById('name').value = name;
	document.getElementById('pin').value = pinNumber;
}

function desigSubmit() {
	var errorMessage = "";
	var status = true;
	if (document.getElementById('category').value == "select") {
		errorMessage += "Please Select category\n";
		document.getElementById('category').focus();
		status = false;
	}
	if (document.getElementById('subCategory').value == "select" || document.getElementById('subCategory').value == "") {
		errorMessage += "Please Select Sub Category\n";
		if (status) {
			document.getElementById('subCategory').focus();
			status = false;
		}
	}
	if (document.getElementById('description').value == "select") {
		errorMessage += "Please Select Designation In Short\n";
		if (status) {
			document.getElementById('description').focus();
			status = false;
		}
	}
	if (document.getElementById('group').value == "select") {
		errorMessage += "Please Select group\n";
		if (status) {
			document.getElementById('group').focus();
			status = false;
		}
	}
	if (document.getElementById('typeValue').value == "select") {
		errorMessage += "Please Select type\n";
		if (status) {
			document.getElementById('typeValue').focus();
			status = false;
		}
	}
	if (document.getElementById('designationId').value == "") {
		errorMessage += "Please Enter Designation\n";
		if (status) {
			document.getElementById('designationId').focus();
			status = false;
		}
	}
	if (document.getElementById('shortForm').value == "") {
		errorMessage += "Please Enter Designation Code\n";
		if (status) {
			document.getElementById('shortForm').focus();
			status = false;
		}
	}
	if (document.getElementById('desigAlias').value == "") {
		errorMessage += "Please Select Alias\n";
		if (status) {
			document.getElementById('desigAlias').focus();
			status = false;
		}
	}
	if (document.getElementById('serviceTypeM').checked == false && 
			document.getElementById('serviceTypeN').checked == false) {
		errorMessage += "Please Select Service Type\n";
		if (status) {
			document.getElementById('serviceTypeM').focus();
			status = false;
		}
	}
	if (document.getElementById('orderNo').value == "") {
		errorMessage += "Please Enter Order Number\n";
		if (status) {
			document.getElementById('orderNo').focus();
			status = false;
		}
	}
	if (status) {
 
		document.forms[0].param.value = "manageDesignation";
		$jq.post( 
				'master.htm?id=' + designationID+'&'+dispUrl,$jq('#master').serialize(), 
		           function(data){ 
		            	 $jq('#displayTable').html(data);
		            	 clearDesig();
		            	 } 
		      );
	} else {
		alert(errorMessage);
	}
}

function qualificationSubmit() {
	var errorMessage = "";
	var status = true;
	if (document.getElementById('name').value == "") {
		errorMessage += "Please Enter Qualification Name\n";
		document.getElementById('name').focus();
		status = false;
	}
	if (document.getElementById('shortForm').value == "") {
		errorMessage += "Please Enter Qualification Code\n";
		if (status) {
			document.getElementById('shortForm').focus();
			status = false;
		}
	}

	if (status) {
 
		document.forms[0].param.value = "manageQualification";	
		$jq.post( 
				'master.htm?id=' + qualificationId+'&'+dispUrl,$jq('#master').serialize(), 
		           function(data){ 
		            	 $jq('#displayTable').html(data);
		            	 clearQual();
		            	 } 
		      );
	} else {
		alert(errorMessage);
	}
}
function disciplineSubmit() {
	var errorMessage = "";
	var status = true;
	if (document.getElementById('name').value == "") {
		errorMessage += "Please Enter Discipline Name\n";
		document.getElementById('name').focus();
		status = false;
	}
	if (document.getElementById('shortForm').value == "") {
		errorMessage += "Please Enter Discipline Code\n";
		if (status) {
			document.getElementById('shortForm').focus();
			status = false;
		}
	}

	if (status) {
 
		document.forms[0].param.value = "manageDiscipline";	
		$jq.post( 
				'master.htm?id=' + disciplineId+'&'+dispUrl,$jq('#master').serialize(), 
		           function(data){ 
		            	 $jq('#displayTable').html(data);
		            	 clearDiscipline();
		            	 } 
		      );
	} else {
		alert(errorMessage);
	}
}
function deleteDesignation(id) {
	var del = confirm("Do you want to delete this item?");
	if (del == true) {
		document.forms[0].param.value = "deleteDesignation";
		$jq.post( 
				'master.htm?id=' + id +'&'+dispUrl,$jq('#master').serialize(), 
		           function(data){ 
		            	 $jq('#displayTable').html(data);
		            	 clearDesig();
		            	 } 
		      );
	}
}
function religionSubmit() {
	var errorMessage = "";
	var status = true;
	if (document.getElementById('name').value == "") {
		errorMessage += "Please Enter Religion Name\n";
		document.getElementById('name').focus();
		status = false;
	}
	if (document.getElementById('religionY').checked == false && 
	    document.getElementById('religionN').checked == false) {
        errorMessage += "Please Select Minority Flag\n";
    if (status) {
	document.getElementById('religionY').focus();
	status = false;
    }
}
	if (status) {
 
		document.forms[0].param.value = "manageReligion";	
		$jq.post( 
				'master.htm?id=' + religionId+'&'+dispUrl,$jq('#master').serialize(), 
		           function(data){ 
		            	 $jq('#displayTable').html(data);
		            	 clearReligion();
		            	 } 
		      );
	} else {
		alert(errorMessage);
	}
}
function pinSubmit() {
	var errorMessage = "";
	var status = true;
	if (document.getElementById('name').value == ""
			|| document.getElementById('name').value == "select") {
		errorMessage += "Please Select Sfid\n";
		document.getElementById('name').focus();
		status = false;
	}
	if (document.getElementById('pin').value == "") {
		errorMessage += "Please Enter Pin Number\n";
		document.getElementById('pin').focus();
		status = false;
	}
	if (status) {

		document.forms[0].param.value = "managePin";
		$jq.post('master.htm?id=' + pinId + '&' + dispUrl, $jq('#master')
				.serialize(), function(data) {
			$jq('#displayTable').html(data);
			clearPin();
		});
	} else {
		alert(errorMessage);
	}
}

function deleteQual(id) {
	var del = confirm("Do you want to delete this item?");
	if (del == true) {
		document.forms[0].param.value = "deleteQualification";
		$jq.post( 
				'master.htm?id=' + id +'&'+dispUrl,$jq('#master').serialize(), 
		           function(data){ 
		            	 $jq('#displayTable').html(data);
		            	 clearQual();
		            	 } 
		      );
	}
}
function deleteReligion(id) {
	var del = confirm("Do you want to delete this item?");
	if (del == true) {
		document.forms[0].param.value = "deleteReligion";
		$jq.post( 
				'master.htm?id=' + id +'&'+dispUrl,$jq('#master').serialize(), 
		           function(data){ 
		            	 $jq('#displayTable').html(data);
		            	 clearReligion();
		            	 } 
		      );
	}
}
function deleteDiscipline(id) {
	var del = confirm("Do you want to delete this record ?");
	if (del == true) {
		document.forms[0].param.value = "deleteDiscipline";
		$jq.post( 
				'master.htm?id=' + id +'&'+dispUrl,$jq('#master').serialize(), 
		           function(data){ 
		            	 $jq('#displayTable').html(data);
		            	 clearDiscipline();
		            	 } 
		      );
	}
}

function clearDesig() {
	document.getElementById('category').value = "select";
	document.getElementById('group').value = "select";
	document.getElementById('typeValue').value = "select";
	document.getElementById('designationId').value = "";
	document.getElementById('shortForm').value = "";
	$jq('#subCategory').find('option').remove().end();
	$jq("#subCategory").append('<option value="">Select</option>');
	designationID = "";
	document.getElementById('serviceTypeM').checked=false;
	document.getElementById('serviceTypeN').checked=false;
	document.getElementById('description').value = "";
	document.getElementById('desigAlias').value = "";
	document.getElementById('orderNo').value = "";
}

function clearQual() {
	document.getElementById('name').value = "";
	document.getElementById('shortForm').value = "";
	document.getElementById('description').value = "";
	document.getElementById('counter').value = 500;
	document.getElementById('flagY').checked=false;
	document.getElementById('flagN').checked=false;
}

function clearDiscipline() {
	document.getElementById('name').value = "";
	document.getElementById('shortForm').value = "";
	document.getElementById('description').value = "";
	document.getElementById('counter').value = 500;
}
function clearReligion() {
	document.getElementById('name').value = "";
	document.getElementById('description').value = "";
	document.getElementById('counter').value = 500;	
	document.getElementById('religionY').checked=false;
	document.getElementById('religionN').checked=false;
}
function clearPin() {
	document.getElementById('name').value = "select";
	document.getElementById('pin').value = "";
}
function levelSubmit(type) {
	var errorMessage = "";
	var status = true;
	if (document.getElementById("newLevel").value == "") {
		errorMessage += "Please Enter level name\n";
		if (status) {
			document.getElementById("newLevel").focus();
			status = false;
		}
	}
	if (document.getElementById("parent").value == "select") {
		errorMessage += "Please Select Minimum Level of Reporting\n";
		document.getElementById("parent").focus();
		status = false;
	}
	
	if (status) {
		document.forms[0].param.value = "manageLevel";
		document.forms[0].type.value = type;
		new Ajax.Updater('displayTable', 'hierarchy.htm?id=' + levelID+'&'+dispUrl, {
			onComplete : function() {clearLevel();
			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : false,
			evalScripts : true
		});
	} else {
		alert(errorMessage);
	}
}
function setLevel(newLevelID) {
	if (newLevelID != "" && newLevelID != undefined) {
		var len = document.getElementById('parent').options.length++;
		document.getElementById('parent').options[len].text = document
				.getElementById('newLevel').value;
		document.getElementById('parent').options[len].value = newLevelID;
	}
}

function editLevel(roleID, parentID, jsonNode) {
	for ( var i = 0; i < jsonNode.length; i++) {
		if (jsonNode[i].roleID== roleID) {	
	document.getElementById('newLevel').value =jsonNode[i].roleName ;
		}
	}
	document.getElementById('parent').value = parentID;
	levelID = roleID;
	
	increaseTextWidth('newLevel');
	setWidth('#parent');
}

function deleteLevel(roleID,type) {
	var del = confirm("Do you want to delete this item?");
	if (del == true) {
		new Ajax.Updater('displayTable',
				'hierarchy.htm?param=deleteLevel&id=' + roleID+'&type='+type+'&'+dispUrl, {
				
					onComplete : function() {
					},
					asynchronous : false,
					evalScripts : true
				});
	}
}

function clearLevel() {
	levelID = "";
	document.getElementById('parent').value = "select";
	document.getElementById('newLevel').value = "";
}

function getParentInstance(type) {
	if (document.getElementById('levelName').value != "select") {
		new Ajax.Updater('result',
				'hierarchy.htm?param=getNodeParent&id=' + document
						.getElementById('levelName').value+'&type='+type, {
					onComplete : function() {
						if(type=="Logical")
						getLogicalName();
					},
					asynchronous : false,
					evalScripts : true
				});
	}
	
}

function editNode(nodeid, jsonNode,type) {
	for ( var i = 0; i < jsonNode.length; i++) {
		if (jsonNode[i].nodeID== nodeid) {
			nodeID = nodeid;
			document.getElementById('nodeNames').value = jsonNode[i].nodeName;
			document.getElementById('levelName').value = jsonNode[i].levelID;
			if(type == "Physical")
			  document.getElementById('departmentType').value = jsonNode[i].departmentType;
			nodeParentID = jsonNode[i].nodeParentID;
			if(type=="Logical")
				document.getElementById('departmentId').value=jsonNode[i].departmentId;
			new Ajax.Updater('result',
									'hierarchy.htm?param=getNodeParent&id=' + jsonNode[i].levelID+"&type="+type+'&'+dispUrl, {
						onComplete : function() {

						},
						asynchronous : false,
						evalScripts : true
					});
		}
	}
	increaseTextWidth('nodeNames');
	setWidth('#nodeParentName');
	setWidth('#levelName');
	
}

function setNodeParentList() {
	document.getElementById("nodeParentName").options.length = 1;
	if (document.getElementById("levelName").value != "select") {
		for ( var i = 0; i < roleParentJSONList.length; i++) {
			var len = document.getElementById("nodeParentName").options.length++;
			document.getElementById("nodeParentName").options[len].text = roleParentJSONList[i].roleName;
			document.getElementById("nodeParentName").options[len].value = roleParentJSONList[i].roleID;
		}
	}
	document.getElementById("nodeParentName").value="select";
}

function setNodeSubInstanceList() {
	document.getElementById("roleInstanceSubName").options.length = 1;
	for ( var i = 0; i < subInstanceJSONList.length; i++) {
		var len = document.getElementById("roleInstanceSubName").options.length++;
		document.getElementById("roleInstanceSubName").options[len].text = subInstanceJSONList[i].roleName;
		document.getElementById("roleInstanceSubName").options[len].value = subInstanceJSONList[i].roleID;
	}
}
function nodeSubmit(type) {
	var errorMessage = "";
	var status = true;
	if (document.getElementById('levelName').value == "select") {
		errorMessage += "Please Select Hierarchy Level\n";
		if (status) {
			document.getElementById('levelName').focus();
			status = false;
		}
	}
	if (document.getElementById('nodeParentName').value == "select") {
		errorMessage += "Please Select Reports to\n";
		if (status) {
			document.getElementById('nodeParentName').focus();
			status = false;
		}
	}
	if (document.getElementById('nodeNames').value == "") {
		errorMessage += "Please Enter Organisation role name\n";
		document.getElementById('nodeNames').focus();
		status = false;
	}
	if(type=="Physical"){
	if (document.getElementById('departmentType').value == "select") {
		errorMessage += "Please Select Department Type\n";
		document.getElementById('departmentType').focus();
		status = false;
	}
	}
	if(type=="Logical"){
	if (document.getElementById('departmentId').value == "select") {
		errorMessage += "Please Enter Department name\n";
		document.getElementById('departmentId').focus();
		status = false;
	}
	}
	
	
	if (status) {
		document.forms[0].param.value = "manageNode";
		document.forms[0].type.value=type;
		new Ajax.Updater('displayTable', 'hierarchy.htm?id=' + nodeID+'&'+dispUrl, {
			onComplete : function() {
				clearNode(type);
			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : false,
			evalScripts : true
		});

	} else {
		alert(errorMessage);
	}

}
function deleteNode(nodeID,type) {
	var del = confirm("Do you want to delete this item?");
	if (del == true) {
		new Ajax.Updater('displayTable',
				'hierarchy.htm?param=deleteNode&id=' + nodeID+'&type='+type+'&'+dispUrl, {
				
					onComplete : function() {
					},
					asynchronous : false,
					evalScripts : true
				});
	}
}
function clearNode(type) {
	nodeID = "";
	document.getElementById('nodeNames').value = "";
	document.getElementById('levelName').value = "select";
	document.getElementById('nodeParentName').options.length = 1;
	document.getElementById('nodeParentName').value = "select";
	if(type=="Physical")
	document.getElementById('departmentType').value = "select";
	if(type=="Logical")
		document.getElementById("departmentId").value="select"
}

function menuMapSubmit() {
	var selectedValues = "";
	var errorMessages = "";
	var flag = true;
	var status = false;
	if (document.getElementById('applicationRole').value == "select") {
		errorMessages = "Please Select Application Role Name\n";
		document.getElementById('applicationRole').focus();
		flag = false;
	}
	for ( var i = 0; i < document.getElementById('SelectRight').options.length; i++) {
			selectedValues += document.getElementById('SelectRight').options[i].value+ ",";
			status = true;
	}
	if (!status) {
		errorMessages += "Please Select Menu Links\n";
		flag = false;
	}
	if (flag) {
		document.forms[0].param.value = "mapSubmit";
		document.forms[0].selectedLinks.value = selectedValues;
		$jq.post('menu.htm', $jq('#menulinks').serialize(),function(html) {
			$jq("#result").html(html);
			
		});
	} else {
		alert(errorMessages);
	}
}
function clearMenuMap() {
	document.getElementById('applicationRole').value = "select";
	for ( var i = 0; i < document.getElementById('SelectLeft').options.length; i++) {
		document.getElementById('SelectLeft').options[i].text = "";
		document.getElementById('SelectLeft').options[i].value = "";
	}
	for ( var i = 0; i < document.getElementById('SelectRight').options.length; i++) {
		document.getElementById('SelectRight').options[i].text = "";
		document.getElementById('SelectRight').options[i].value = "";
	}
}
function getSpeMenuLinks() {
	if($jq('#applicationRole').val()!='select'){
		$jq.post('menu.htm?param=getList&id=' + document.getElementById('applicationRole').value, function(html) {
			$jq("#menuLinksList").html(html);
			
		});
	}
}
function setMenuLinks() {
	var create1 = '';
	var create2 = '';
	 jQuery('#SelectRight option').remove();
	 jQuery('#SelectLeft option').remove();
    for(var i = 0; i < delistObj.length;i++)
    {
    	var flag=true;
    	for(var j=0;j<listObj.length;j++) 
    		{
    			if(delistObj[i].id==listObj[j].linkID){
    				create2 += '<option value="'+delistObj[i].id+'">'+delistObj[i].description+'</option>';
    				flag=false;
    				break;
    			}
    		}
    	if(flag){
    		create1 += '<option value="'+delistObj[i].id+'">'+delistObj[i].description+'</option>';
    	}
    }
    jQuery('#SelectRight').append(create2);
    jQuery('#SelectLeft').append(create1);
   }

function getDistrictList(stateID,districtID) {
	if (document.getElementById(stateID).value != "Select") {
		document.getElementById(districtID).options.length = 1;
		for ( var i = 0; i < districtJSONList.length; i++) {
			if (districtJSONList[i].stateId == document.getElementById(stateID).value) {
				var len = document.getElementById(districtID).options.length++;
				document.getElementById(districtID).options[len].text = districtJSONList[i].name;
				document.getElementById(districtID).options[len].value = districtJSONList[i].id;
			}
		}
	}

}

function getSubInstanceLevels() {
	if (document.getElementById('roleInstanceName').value == "select") {
		document.getElementById('internalDivisions').disabled = false;
	} else {
		document.getElementById('internalDivisions').value = "select";
		document.getElementById('internalDivisions').disabled = "true";
		if (document.getElementById('newInternalDiv') != null)
			document.getElementById('newInternalDiv').style.display = "none";

		document.forms[0].param.value = "";
		new Ajax.Updater(
				'result',
				'hierarchy.htm?param=getSubInstance&roleInstanceName=' + document
						.getElementById('roleInstanceName').value, {
					onComplete : function() {
					},
					asynchronous : false,
					evalScripts : true
				});
	}
}

function empRoleMappingSubmit() {
	var errorMessages = "";
	var status = true;
	newRoleName = "";
	newDivision = "";
	if (document.getElementById('mapSFID').value == "") {
		errorMessages += "Please Enter SFID\n";
		document.getElementById('mapSFID').focus();
		status = false;
	}
	if (document.getElementById('roleInstanceName').value == "select") {
		if (document.getElementById('internalDivisions').value == "select") {
			errorMessages += "Please Select internal division\n";
			if (status) {
				document.getElementById('internalDivisions').focus();
				status = false;
			}
		} else if (document.getElementById('internalDivisions').value == "new") {
			if (document.getElementById('newInternalDiv').value == "") {
				errorMessages += "Please Enter New Division\n";
				if (status) {
					document.getElementById('newInternalDiv').focus();
					status = false;
				}
			} else {
				newDivision = document.getElementById('newInternalDiv').value;
			}
		}
	}
	if (document.getElementById('internalRoleName').value == "select") {
		errorMessages += "Please Select internal role\n";
		if (status) {
			document.getElementById('internalRoleName').focus();
			status = false;
		}
	} else if (document.getElementById('internalRoleName').value == "new") {

		if (document.getElementById('newInternalRole').value == "") {
			errorMessages += "Please enter internal role name\n";
			if (status) {
				document.getElementById('newInternalRole').focus();
				status = false;
			}
		} else {
			newRoleName = document.getElementById('newInternalRole').value;
			;
		}
	}

	if (document.getElementById('roleInstanceName').value == "select") {
		if (document.getElementById('parentSFID').value == "select") {
			errorMessages += "Please Select Immediate Superior\n";
			if (status) {
				document.getElementById('parentSFID').focus();
				status = false;
			}
		}
	}

	if (status) {
		document.forms[0].param.value = "submitRole";
		document.forms[0].intDivision.value = document
				.getElementById('internalDivisions').options[document
				.getElementById('internalDivisions').selectedIndex].text;

		document.forms[0].intRole.value = document
				.getElementById('internalRoleName').options[document
				.getElementById('internalRoleName').selectedIndex].text;

		if (document.getElementById('roleInstanceSubName').value != "select") {
			document.forms[0].instanceRoleID.value = document
					.getElementById('roleInstanceSubName').value;
		} else {
			document.forms[0].instanceRoleID.value = document
					.getElementById('roleInstanceName').value;
		}

		new Ajax.Updater('result', 'hierarchy.htm', {
			onComplete : function() {
			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : false,
			evalScripts : true
		});
	} else {
		alert(errorMessages);
	}
}

function setValues() {
	var status = true;
	var status1 = true;
	if (newDivision != "") {
		// created new division
		for ( var i = 0; i < document.getElementById('internalDivisions').options.length; i++) {
			if (document.getElementById('internalDivisions').options[i].text == newDivision) {
				status = false;
			}
		}
		if (status) {
			var len = document.getElementById("internalDivisions").options.length++;
			document.getElementById("internalDivisions").options[len].text = newDivision;
			document.getElementById("internalDivisions").options[len].value = len;
		}
	}
	if (newRoleName != "" && newRoleName != 'Employee' && newRoleName != 'Head') {
		for ( var i = 0; i < document.getElementById('internalRoleName').options.length; i++) {
			if (document.getElementById('internalRoleName').options[i].text == newRoleName) {
				status1 = false;
			}
		}
		if (status1) {
			var len = document.getElementById("internalRoleName").options.length++;
			document.getElementById("internalRoleName").options[len].text = newRoleName;
			document.getElementById("internalRoleName").options[len].value = len;
		}
	}
}
function clearEmpRoleMapping() {
	document.getElementById('mapSFID').value = "select";
	// document.getElementById('roleInstanceName').value = "select";
	document.getElementById('internalDivisions').value = "select";
	if (document.getElementById('newInternalDiv') != null) {
		document.getElementById('newInternalDiv').value = "";
		document.getElementById('newInternalDivTag').style.display = "none";
	}
	document.getElementById('internalRoleName').value = "select";
	if (document.getElementById('newInternalRole') != null) {
		document.getElementById('newInternalRole').value = "";
		document.getElementById('newInternalRoleTag').style.display = "none";
	}
	document.getElementById('department').value = "select";
	internalTreeID = "";
	document.getElementById("mapSFID").options.length = 1;
	document.getElementById("internalDivisions").options.length = 2;
	document.getElementById("internalRoleName").options.length = 4;
}
function checkSelectedDivision() {
	if (document.getElementById('internalDivisions').value == "New")
		document.getElementById('newInternalDivTag').style.display = "block";
	else
		document.getElementById('newInternalDivTag').style.display = "none";
}
function checkSelectedRole() {
	if (document.getElementById('internalRoleName').value == "New")
		document.getElementById('newInternalRoleTag').style.display = "block";
	else
		document.getElementById('newInternalRoleTag').style.display = "none";
}


// Edit Employee Start

function saveEditEmployee() {
	
	var errorMessage = "";
	var flag = true;
	var dob=document.getElementById('dob').value
	var check;
	if (!validateNotEmpty(document.getElementById('nameInServiceBook').value)) {
		errorMessage += "Please Enter Service Book Name \n";
		if (flag) {
			document.getElementById('nameInServiceBook').focus();
			flag = false;
		}

	}
	if (!validateNotEmpty(document.getElementById('userSfid').value)) {
		errorMessage += "Please Enter SFID \n";
		if (flag) {
			document.getElementById('userSfid').focus();
			flag = false;
		}

	}
	if (!document.getElementById('nameInServiceBook').value == '') {
		check=document.getElementById('nameInServiceBook').value;
		if(validateChar(check)) {
			errorMessage += "Name In Service Book Is Not Valid \n";
			document.getElementById('nameInServiceBook').focus();
			flag = false;
		}
	}
	if (document.getElementById('title').value == 'Select') {
		errorMessage += "Please Select Title \n";
		if (flag) {
			document.getElementById('title').focus();
			flag = false;
		}
	}
	if (!validateNotEmpty(document.getElementById('firstName').value)) {
		errorMessage += "Please Enter FirstName \n";
		if (flag) {
			document.getElementById('firstName').focus();
			flag = false;
		}

	}else if (!document.getElementById('firstName').value == '') {
		check=document.getElementById('firstName').value;
		if(validateChar(check)) {
			errorMessage += "First Name Is Not Valid \n";
			document.getElementById('firstName').focus();
			flag = false;
		}
	}
	if (!validateNotEmpty(document.getElementById('lastName').value)) {
		errorMessage += "Please Enter LastName \n";
		if (flag) {
			document.getElementById('lastName').focus();
			flag = false;
		}

	}else if (!document.getElementById('lastName').value == '') {
		check=document.getElementById('lastName').value;
		if(validateChar(check)) {
			errorMessage += "Last Name Is Not Valid \n";
			document.getElementById('lastName').focus();
			flag = false;
		}
	}
	if (!document.getElementById('middleName').value == '') {
		check=document.getElementById('middleName').value;
		if(validateChar(check)) {
			errorMessage += "Middle Name Is Not Valid \n";
			document.getElementById('middleName').focus();
			flag = false;
		}
	}// srinu
	 if(document.getElementById('relationId').value =="25"){
			if (!validateNotEmpty(document.getElementById('relationName').value)) {
				errorMessage += "Please Enter FatherName \n";
				
				if (flag) {
					document.getElementById('relationName').focus();
					flag = false;
				}
			}
		}
	 if(document.getElementById('relationId').value == "25"){
		 if ($jq('#fatherSpouseTitle').val() == '0') {
				errorMessage += "Please Select Title of the Father Name\n";
				$jq('#fatherSpouseTitle').focus();
				flag = false;
			}
           
		 }
	 if(document.getElementById('relationId').value =="26"){
			if (!validateNotEmpty(document.getElementById('relationName').value)) {
				errorMessage += "Please Enter spouseName \n";
				
				if (flag) {
					document.getElementById('relationName').focus();
					flag = false;
				}
			}
		}
	 if(document.getElementById('relationId').value == "26"){
		 if ($jq('#fatherSpouseTitle').val() == '0') {
				errorMessage += "Please Select Title of the Spouse Name\n";
				$jq('#fatherSpouseTitle').focus();
				flag = false;
			}
           
		 }
	// sr
	 if (!(document.getElementById('height').value == '')&& (document.getElementById('heightType').value=='Select')){
			errorMessage += "Please Select Height Type \n";
			if (flag) {
			document.getElementById('heightType').focus();
			flag = false;
			}
			}
			else if(!document.getElementById('height').value == ''){
			check=document.getElementById('height').value;
			if(!validateSignedFloat(check) || !((document.getElementById('height').value).length >= '3')|| !((document.getElementById('height').value).length <'5')) {
				errorMessage += "Height Is Not Valid \n";
				if (flag) {
				document.getElementById('height').focus();
				flag = false;
				}
			} else if(document.getElementById('heightType').value=="2"){ 
				
				var hight=(document.getElementById('height').value).split(".");
				document.getElementById('storeHight').value = parseFloat((hight[0]*12+parseFloat(hight[1]))*2.54).toFixed(2);
				
				
			}
		}
	 
	 //commented by bkr 10/09/2016 
	/*	if(document.getElementById('heightType').value=="2" || document.getElementById('heightType').value=="1"){ 
		if (!validateNotEmpty(document.getElementById('height').value)) {
			errorMessage += "Please Enter height \n";
			if (flag) {
				document.getElementById('height').focus();
				flag = false;
			}
		}
		
	}*/
	// }
	if (!validateNotEmpty(document.getElementById('dob').value)) {
		errorMessage += "Please Enter Data of Birth \n";
		if (flag) {
			document.getElementById('dob').focus();
			flag = false;
		}
	}else {
		if(compareDate(dob,checkDate)) {
		
			errorMessage += "Data of Birth Not Valid \n";
			if (flag) {
				document.getElementById('dob').focus();
				flag = false;
			}
		}
	}
	if (document.getElementById('female').checked == false
			&& document.getElementById('male').checked == false) {
		errorMessage += "Please Select Gender \n";
		if (flag) {
			document.getElementById('male').focus();
			flag = false;
		}
	}
	if (document.getElementById('maritalId').value == 'Select') {
		errorMessage += "Please Select Marital Status \n";
		if (flag) {
			document.getElementById('maritalId').focus();
			flag = false;
		}
	}
	if (document.getElementById('religion').value == 'Select') {
		errorMessage += "Please Select Religion \n";
		if (flag) {
			document.getElementById('religion').focus();
			flag = false;
		}
	}
	if (document.getElementById('communityId').value == 'Select') {
		errorMessage += "Please Select Community \n";
		if (flag) {
			document.getElementById('communityId').focus();
			flag = false;
		}
	}
	if (document.getElementById('nationalityId').value == 'Select') {
		errorMessage += "Please Select Nationality\n";
		if (flag) {
			document.getElementById('nationalityId').focus();
			flag = false;
		}
	}
	if (document.getElementById('blood').value == 'Select') {
		errorMessage += "Please Select Blood Group\n";
		if (flag) {
			document.getElementById('blood').focus();
			flag = false;
		}
	}
	if (document.getElementById('personalNumber').value == '') {
		errorMessage += "Please Enter Mobile Number\n";
		if (flag) {
			document.getElementById('personalNumber').focus();
			flag = false;
		}
	}
	//commented by bkr 10/09/2016
	/*if (document.getElementById('internalNo').value == '') {
		errorMessage += "Please Enter Internal Number\n";
		if (flag) {
			document.getElementById('internalNo').focus();
			flag = false;
		}
	}*/
	if (document.getElementById('motherTongue').value == '') {
		errorMessage += "Please Enter Mother Tongue\n";
		if (flag) {
			document.getElementById('motherTongue').focus();
			flag = false;
		}
	}
	
	//commented by bkr 10/09/2016 
	/*if (document.getElementById('idMarks').value == '') {
		errorMessage += "Please Enter Id Marks\n";
		if (flag) {
			document.getElementById('idMarks').focus();
			flag = false;
		}
	}*/
	if (document.getElementById('gpfAcNo').value == '') {
		errorMessage += "Please Enter GPF A/C No \n";
		if (flag) {
			document.getElementById('gpfAcNo').focus();
			flag = false;
		}
	}
	if (!document.getElementById('residenceNo').value == '') {
		check=document.getElementById('residenceNo').value;
		if(!validateInteger(check)) {
			errorMessage += "Residency Number Is Not Valid \n";
			document.getElementById('residenceNo').focus();
			flag = false;
		}
	}
	if (!document.getElementById('personalNumber').value == '') {
		check=document.getElementById('personalNumber').value;
		if(!validateInteger(check)) {
			errorMessage += "Mobile Number Is Not Valid \n";
			document.getElementById('personalNumber').focus();
			flag = false;
		}
	}
	if (!document.getElementById('internalNo').value == '') {
		check=document.getElementById('internalNo').value;
		if(!validateInteger(check)) {
			errorMessage += "Internal Number Is Not Valid \n";
			document.getElementById('internalNo').focus();
			flag = false;
		}
	}
	if (document.getElementById('appointmentTypeId').value == 'Select') {
		errorMessage += "Please Select Appointment Type \n";
		if (flag) {
			document.getElementById('appointmentTypeId').focus();
			flag = false;
		}
	}
	if (document.getElementById('employmentTypeId').value == 'Select') {
		errorMessage += "Please Select Employment Type \n";
		if (flag) {
			document.getElementById('employmentTypeId').focus();
			flag = false;
		}
	}
	if (document.getElementById('designationId').value == 'Select') {
		errorMessage += "Please Select Designation \n";
		if (flag) {
			document.getElementById('designationId').focus();
			flag = false;
		}
	}

	/*
	 * if (document.getElementById('directorateId').value == 'Select') {
	 * errorMessage += "Please Select Directorate \n"; if (flag) {
	 * document.getElementById('directorateId').focus(); flag = false; } }
	 */
	
	//commented by bkr 10/09/2016
	
	/*if (document.getElementById('dojGovt').value == '') {
		errorMessage += "Please Enter Doj Govt \n";
		if (flag) {
			document.getElementById('dojGovt').focus();
			flag = false;
		}
	}*/
	
	if (document.getElementById('dojDrdo').value == '') {
		errorMessage += "Please Enter Doj Drdo \n";
		if (flag) {
			document.getElementById('dojDrdo').focus();
			flag = false;
		}
	}
	
	if (document.getElementById('dojAsl').value == '') {
		errorMessage += "Please Enter Doj Asl \n";
		if (flag) {
			document.getElementById('dojAsl').focus();
			flag = false;
		}
	}
	
	if (document.getElementById('seniorityDate').value == '') {
		errorMessage += "Please Enter Seniority Date \n";
		if (flag) {
			document.getElementById('seniorityDate').focus();
			flag = false;
		}
	}
	
	//commented by bkr 10/09/2016
	/*if (document.getElementById('lastPromotion').value == '') {
		errorMessage += "Please Enter Promotion Date \n";
		if (flag) {
			document.getElementById('lastPromotion').focus();
			flag = false;
		}
	}*/
	 if (!document.getElementById('lastPromotion').value == '') {
			
		  dateCheck=document.getElementById('lastPromotion').value;
		   var pro = document.getElementById('seniorityDate').value;
	if (!compareDate(dateCheck,pro)) {
		
			errorMessage += "please select Promotion Rank is After Seniority Date\n";
			if (flag) {
				document.getElementById('lastPromotion').focus();
				flag = false;
			}
		}
    }
	if (document.getElementById('dispersaryNumber').value == 'Select') {
		errorMessage += "Please Select Dispersary Number \n";
		if (flag) {
			document.getElementById('dispersaryNumber').focus();
			flag = false;
		}
	}               
	if (!document.getElementById('dojGovt').value == '') {
		
		dateCheck=document.getElementById('dojGovt').value;
		var dob=document.getElementById('dob').value;
		
		if(!compareDate(dateCheck,dob)) {
			errorMessage += "Date of Join In Govt Is After Date of Birth \n";
			if (flag) {
				document.getElementById('dojGovt').focus();
				flag = false;
			}
		}
		if(!compareDate(document.getElementById('dojDrdo').value,dateCheck)) {
			errorMessage += "Date of Join In Drdo Is After Date of Join In Govt \n";
			if (flag) {
				document.getElementById('dojDrdo').focus();
				flag = false;
			}
		}
		
	}
      if (!document.getElementById('dojGovt').value == '') {
		
		   dateCheck=document.getElementById('dojAsl').value;
	if (!compareDate(dateCheck,document.getElementById('dojGovt').value)) {
		
			errorMessage += "Date of Join In ASL Is After Date of Join In Govt \n";
			if (flag) {
				document.getElementById('dojAsl').focus();
				flag = false;
			}
		}
      }
      if (!document.getElementById('dojDrdo').value == '') {
  		
		   dateCheck=document.getElementById('seniorityDate').value;
	if (!compareDate(dateCheck,document.getElementById('dojDrdo').value)) {
		
			errorMessage += "Present Rank Seniority Date Is After Date of Join In DRDO \n";
			if (flag) {
				document.getElementById('seniorityDate').focus();
				flag = false;
			}
		}
     }
		
	
	/*
	 * if (!document.getElementById('lastPromotion').value == '') {
	 * if(!compareDate(document.getElementById('lastPromotion').value,document.getElementById('dojAsl').value)) {
	 * errorMessage += "Date of Last Promotion Is Invalid \n"; if (flag) {
	 * document.getElementById('lastPromotion').focus(); flag = false; } } }
	 */
	if ( document.getElementById('reservationId').value=="1" && document.getElementById('handicapId').value=="Select" ){
		if($jq('#handicapId').is(':visible') && document.getElementById('handicapId').value == "Select" ){
			errorMessage += "Please Enter PH Type \n";
			if (flag) {
				document.getElementById('handicapId').focus();
				flag = false;
			}
		 }
     }
	if (flag) {

	document.forms[0].param.value = "saveEditEmployee";
	document.forms[0].designationId.disabled = false;
	document.forms[0].seniorityDate.disabled = false;
	document.forms[0].lastPromotion.disabled = false;
	document.forms[0].action = "employee.htm?divisionId="+document.getElementById('divisionId').value,"&designationId="+document.getElementById('designationId');
	document.forms[0].submit();
	
	} else { alert(errorMessage); }
	 
}
function enableEditEmployee() {
	document.forms[0].firstName.disabled = false;
	document.forms[0].middleName.disabled = false;
	document.forms[0].lastName.disabled = false;
	document.forms[0].personalNumber.disabled = false;
	document.forms[0].dob.disabled = false;
	document.forms[0].dojGovt.disabled = false;
	document.forms[0].dojDrdo.disabled = false;
	document.forms[0].dojAsl.disabled = false;
// document.forms[0].seniorityDate.disabled = false;
// document.forms[0].lastPromotion.disabled = false;
	document.forms[0].motherTongue.disabled = false;
	document.forms[0].height.disabled = false;
	document.forms[0].idMarks.disabled = false;
	document.forms[0].areaOfInterest.disabled = false;
	document.forms[0].cgshNumber.disabled = false;
	document.forms[0].dispersaryNumber.disabled = false;
	document.forms[0].gpfAcNo.disabled = false;
	document.forms[0].pranNo.disabled = false;

	document.forms[0].male.disabled = false;
	document.forms[0].female.disabled = false;

	document.forms[0].title.disabled = false;
	document.forms[0].religion.disabled = false;
	document.forms[0].nationalityId.disabled = false;
	document.forms[0].maritalId.disabled = false;
	document.forms[0].communityId.disabled = false;
	document.forms[0].appointmentTypeId.disabled = false;
	// document.forms[0].designationId.disabled = false;
	document.forms[0].employmentTypeId.disabled = false;
	document.forms[0].directorateId.disabled = false;
	document.forms[0].divisionId.disabled = false;

	document.forms[0].ppaNo.disabled = false;
	document.forms[0].blood.disabled = false;
	document.forms[0].residenceNo.disabled = false;
	document.forms[0].internalNo.disabled = false;

	document.forms[0].reservationId.disabled = false;
	document.forms[0].handicapId.disabled = true;
	document.forms[0].joinType.disabled = true;
	document.forms[0].workedYears.disabled = true;
	document.forms[0].yeshouseAllowence.disabled = false;
	document.forms[0].nohouseAllowence.disabled = false;

	document.forms[0].yesgroupAllowence.disabled = false;
	document.forms[0].nogroupAllowence.disabled = false;

	document.forms[0].nofamPlanning.disabled = false;
	document.forms[0].yesfamPlanning.disabled = false;
	document.forms[0].uptoDate.disabled = false;
}

function getEmployeeDetails() {

	var errorMessage = "";
	var flag = true;
	var dateCheck;
	if(document.forms[0].userSfid.value=="") {
		errorMessage += "Please Enter SFID \n";
		if (flag) {
			document.forms[0].userSfid.focus();
			flag = false;
		}
	}
	
	if(flag) {
	document.forms[0].param.value = "viewEmployee";
	document.forms[0].action = "employee.htm";
	
	document.forms[0].submit();
	} else {
		
		alert(errorMessage);
		
	}
}

// Edit Employee End


function saveReqWorkFlowMapping() {

	var errorMessage = "";
	var flag = true;
	
	if (document.getElementById('workflowType').value == '0') {
		errorMessage += "Please Select Workflow Type \n";
		if (flag) {
			document.getElementById('workflowType').focus();
			flag = false;
		}
	}
	if(document.getElementById('workflowType').value == '2'){
		if (document.getElementById('roleInstanceID').value == '0') {
			errorMessage += "Please Select Role \n";
			if (flag) {
				document.getElementById('roleInstanceID').focus();
				flag = false;
			}
		}
	}
	if (document.getElementById('workflowId').value == '0') {
		errorMessage += "Please Select  WorkFlow Name \n";
		if (flag) {
			document.getElementById('workflowId').focus();
			flag = false;
		}
	}
	if (document.getElementById('requestId').value == '0') {
		errorMessage += "Please Select Request Type \n";
		if (flag) {
			document.getElementById('requestId').focus();
			flag = false;
		}
	}
	// for designation based workflow
	if($jq('#desigsDiv').is(':visible')){
		if($jq('#designationID').val()=='select'){
			errorMessage += "Please Select Designation \n";
			$jq('#designationID').focus();
			flag = false;
		}
	}
	// for organization specific workflow
	if($jq('#orgDiv').is(':visible')){
		if($jq('#organizationID').val()=='select'){
			errorMessage += "Please Select Organization \n";
			$jq('#organizationID').focus();
			flag = false;
		}
	}

	if (flag) {
		if (document.forms[0].id.value > "0") {
			// for updating the values
			document.forms[0].param.value = "reqmanage";
			new Ajax.Updater('mappingList', 'workflowmap.htm'+'?'+dispUrl, {
				onComplete : function() {
				},
				parameters : Form.serialize(document.forms[0]),
				asynchronous : false,
				evalScripts : true
			});
		} else {
			// for new creation
			document.forms[0].param.value = "reqmanage";
			document.forms[0].id.value = "0";
			new Ajax.Updater('mappingList', 'workflowmap.htm'+'?'+dispUrl, {
				onComplete : function() {
				},
				parameters : Form.serialize(document.forms[0]),
				asynchronous : false,
				evalScripts : true
			});
		}
	} else {

		alert(errorMessage);
	}

}
function editReqWorkFlow(id, requestId, workflowId, roleInstaceID,designationID,organizationID) {
	clearReqWorkFlowMapping();
	document.forms[0].id.value = id;
	document.forms[0].requestId.value = requestId;
	document.forms[0].workflowId.value = workflowId;
	if(designationID!=''){
		document.forms[0].workflowType.value = "3";
		document.getElementById('desigsDiv').style.display="block";
		document.forms[0].designationID.value=designationID;
	}else if(roleInstaceID!=''){
		document.forms[0].workflowType.value = "2";
		document.getElementById('rolesDiv').style.display="block";
		document.forms[0].roleInstanceID.value=roleInstaceID;
	}else if(organizationID!=''){
		document.forms[0].workflowType.value = "4";
		document.getElementById('orgDiv').style.display="block";
		document.forms[0].organizationID.value=organizationID;
	}else{
		document.forms[0].workflowType.value = "1";
	}
	$jq('#workflowType').attr('disabled','disabled');
}
function deleteReqWorkFlow(id,workflowType) {
	document.forms[0].id.value = id;
	document.forms[0].type.value = workflowType;
	document.forms[0].param.value = "delete";
	new Ajax.Updater('mappingList', 'workflowmap.htm'+'?'+dispUrl, {
		onComplete : function() {
		},
		parameters : Form.serialize(document.forms[0]),
		asynchronous : false,
		evalScripts : true
	});
}
function clearReqWorkFlowMapping() {
	document.forms[0].workflowType.value = "0";
	if(document.forms[0].roleInstanceID!=null){
		document.forms[0].roleInstanceID.value="0";
		document.getElementById('rolesDiv').style.display="none";
	}
	if(document.forms[0].designationID!=null){
		document.forms[0].designationID.value="select";
		document.getElementById('desigsDiv').style.display="none";
	}
	if(document.forms[0].organizationID!=null){
		document.forms[0].organizationID.value="select";
		document.getElementById('orgDiv').style.display="none";
	}
	document.forms[0].requestId.value = "0";
	document.forms[0].workflowId.value = "0";
	document.forms[0].id.value = "0";
	$jq('#workflowType').attr('disabled',false);
}
// ---------------WORKFLOW MAPPING END-------------------------

// ---------------REQUEST WORKFLOW MAPPING START-------------------------
function requestWorkflowMapping() {
	document.forms[0].param.value = "mapping";
	document.forms[0].action = "workflowmap.htm";
	document.forms[0].submit();

}
function assignValues() {

	var errorMessage = "";
	var flag = true;
	if (document.getElementById('instanceId').value == '0') {
		errorMessage += "Please Select Role Instance Name \n";
		if (flag) {
			document.getElementById('instanceId').focus();
			flag = false;
		}
	}
	if (document.getElementById('requestId').value == '0') {
		errorMessage += "Please Select Request Type \n";
		if (flag) {
			document.getElementById('requestId').focus();
			flag = false;
		}
	}
	if (document.getElementById('workflowId').value == '0') {
		errorMessage += "Please Select WorkFlow Type \n";
		if (flag) {
			document.getElementById('workflowId').focus();
			flag = false;
		}
	}

	if (flag) {
		if (document.forms[0].id.value > "0") {
			// for updating the values
			document.forms[0].param.value = "manage";
			new Ajax.Updater('displayTable', 'workflowmap.htm', {
				onComplete : function() {
				},
				parameters : Form.serialize(document.forms[0]),
				asynchronous : false,
				evalScripts : true
			});
		} else {
			// for new creation
			document.forms[0].param.value = "manage";
			document.forms[0].id.value = "0";
			new Ajax.Updater('displayTable', 'workflowmap.htm', {
				onComplete : function() {
				},
				parameters : Form.serialize(document.forms[0]),
				asynchronous : false,
				evalScripts : true
			});
		}
	} else {
		alert(errorMessage);

	}

}

function clearworkflow() {

	document.getElementById('instanceId').value = "0"
	document.getElementById('workflowId').value = "0"
	document.getElementById('requestId').value = "0"
	document.forms[0].id.value = "0";
}
function editWorkflowRequest(id, instanceId, requestId, workflowId) {

	document.getElementById('instanceId').value = instanceId;
	document.getElementById('workflowId').value = workflowId;
	document.getElementById('requestId').value = requestId;
	document.forms[0].id.value = id;

}
function deleteWorkflowRequest(id) {

	document.forms[0].id.value = id
	document.forms[0].param.value = "deleteMapping";
	new Ajax.Updater('displayTable', 'workflowmap.htm', {
		onComplete : function() {
		},
		parameters : Form.serialize(document.forms[0]),
		asynchronous : false,
		evalScripts : true
	});
}

// ---------------REQUEST WORKFLOW MAPPING END---------------------------

function enableFamilyList() {
	if (document.getElementById('passPortFor').value == "FAMILY") {
		document.getElementById('familyMember').disabled = false;

	} else {
		document.getElementById('familyMember').disabled = true;

	}
}


function getInstanceSubLevels(roleInstanceId) {
	if(document.getElementById("id")==null || document.getElementById("id").value=="")
	{
		if (document.getElementById('roleInstanceName').value == "select") {
			document.getElementById('roleInstanceSubName').options.length = 1;
		} else {
			new Ajax.Updater(
					'headresult',
					'hierarchy.htm?param=getInstanceSubList&roleInstanceName=' + document
							.getElementById('roleInstanceName').value, {
						onComplete : function() {
						},
						asynchronous : false,
						evalScripts : true
					});
	
		}
	}else
	{
	
		if (document.getElementById("roleInstanceName").value != "Select") {
			document.getElementById("roleInstanceSubName").options.length = 1;
			for ( var i = 0; i < divisionList.length; i++) {
				if (divisionList[i].directorateId == roleInstanceId && divisionList[i].roleId>=9) {
						var parent=divisionList[i].directorateId;
						var len = document.getElementById("roleInstanceSubName").options.length++;
						document.getElementById("roleInstanceSubName").options[len].innerHTML = divisionList[i].divisionName;
						document.getElementById("roleInstanceSubName").options[len].value = divisionList[i].instanceId;
					}
				}
			}
		}
}

function instanceRoleMappingSubmit(type) {
	var errorMessage = "";
	var status = true;
	var selectedRole = document.getElementById('roleInstanceName').value;

	if (document.getElementById('mapSFID').value == "") {
		errorMessage = "Please Enter SFID\n";
		document.getElementById('mapSFID').focus();
		status = false;

	}

	if (document.getElementById('roleInstanceName').value == "select") {
		errorMessage += "Please Select DIR/PD/AD/TD/PROJ/Divisions/Groups\n";
		if (status) {
			document.getElementById('roleInstanceName').focus();
			status = false;
		}
	}
	if (document.getElementById('roleInstanceSubName').value != "select") {
		selectedRole = document.getElementById('roleInstanceSubName').value;
	}
	if(document.getElementById('defaultFlag').checked)
	{
		document.forms[0].defaultFlag.value="yes";
	}else	
	{
		document.forms[0].defaultFlag.value="no";
	}
	if (status) {
		var url="";
		if(type==null || type=="")
		{
			if(document.getElementById("id")!=null && document.getElementById("id").value!="")
			{
				url='hierarchy.htm?param=submitOrgRole&selectedRole='+ selectedRole + '&mapSFID='+ document.getElementById('mapSFID').value	+ '&instanceRoleID='+ document.getElementById('roleInstanceName').value	+ '&roleInstanceSubName='+ document.getElementById('roleInstanceSubName').value
				+'&defaultFlag='+ document.forms[0].defaultFlag.value+'&id='+document.getElementById("id").value+"&type=updatehead"+'&'+dispUrl;
			}else
			{				
				url='hierarchy.htm?param=submitOrgRole&selectedRole='+ selectedRole + '&mapSFID='+ document.getElementById('mapSFID').value	+ '&instanceRoleID='+ document.getElementById('roleInstanceName').value	+ '&roleInstanceSubName='+ document.getElementById('roleInstanceSubName').value
				+'&defaultFlag='+ document.forms[0].defaultFlag.value+'&'+dispUrl;
			}
		}else
		{
			url='hierarchy.htm?param=submitOrgRole&selectedRole='
				+ selectedRole + '&mapSFID='
				+ document.getElementById('mapSFID').value
				+ '&instanceRoleID='
				+ document.getElementById('roleInstanceName').value
				+ '&roleInstanceSubName='
				+ document.getElementById('roleInstanceSubName').value
				+'&defaultFlag='
				+ document.forms[0].defaultFlag.value
				+'&type=employeesame';
		}
		new Ajax.Updater('headresult',url+'?'+dispUrl,{
					onComplete : function() {
			},
					asynchronous : false,
					evalScripts : true
				});
	} else {
		alert(errorMessage);
	}
}


function internalRoleMappingSubmit() {
	var errorMessages = "";
	var status = true;
	newDivision = document.getElementById('internalDivisions').options[document
			.getElementById('internalDivisions').selectedIndex].text
	newRoleName = document.getElementById('internalRoleName').options[document
			.getElementById('internalRoleName').selectedIndex].text;

	if (document.getElementById('mapSFID').value == "select") {
		errorMessages += "Please Select SFID\n";
		document.getElementById('mapSFID').focus();
		status = false;
	}
	if (document.getElementById('internalDivisions').value == "New") {
		if (document.getElementById('newInternalDiv').value == "") {
			errorMessages += "Please Enter New Division\n";
			if (status) {
				document.getElementById('newInternalDiv').focus();
				status = false;
			}
		} else {
			newDivision = document.getElementById('newInternalDiv').value;
		}
	}
	if (document.getElementById('internalRoleName').value == "select") {
		errorMessages += "Please Select internal role\n";
		if (status) {
			document.getElementById('internalRoleName').focus();
			status = false;
		}
	} else if (document.getElementById('internalRoleName').value == "New") {

		if (document.getElementById('newInternalRole').value == "") {
			errorMessages += "Please enter internal role name\n";
			if (status) {
				document.getElementById('newInternalRole').focus();
				status = false;
			}
		} else {
			newRoleName = document.getElementById('newInternalRole').value;
		}
	}

	if (document.getElementById('internalDivisions').value == "New"
			&& document.getElementById('internalRoleName').value != "Head") {
		errorMessages += "First Assign Head to this division\n";
		if (status) {
			document.getElementById('internalDivisions').focus();
			status = false;
		}
	}
	if (document.getElementById('internalDivisions').value != "New"
			&& document.getElementById('internalDivisions').value != "select"
			&& document.getElementById('internalRoleName').value == "Head" && internalTreeID=='') {
		errorMessages += "Head already exists to this division\n";
		if (status) {
			document.getElementById('internalRoleName').focus();
			status = false;
		}
	}
	if((editNewDivision!=document.getElementById('internalDivisions').options[document.getElementById('internalDivisions').selectedIndex].text && internalTreeID!='' && document.getElementById('internalRoleName').value=='Head' && document.getElementById('internalDivisions').value!='New')
			|| (editNewDivision==document.getElementById('internalDivisions').options[document.getElementById('internalDivisions').selectedIndex].text && document.getElementById('internalRoleName').value=='Head')){
		errorMessages += "Head already assigned to this division\n";
		if (status) {
			document.getElementById('internalDivisions').focus();
			status = false;
		}
	}
	
	if (status) {
		document.forms[0].param.value = "submitInternalRole";
		// if(newDivision!='Select'){
			document.forms[0].intDivision.value = newDivision;
		// }
		document.forms[0].intRole.value = newRoleName;
		document.forms[0].manageID.value = internalTreeID;

		new Ajax.Updater('result', 'hierarchy.htm'+'?'+dispUrl, {
			onComplete : function() {
			clearEmpRoleMapping();
			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : false,
			evalScripts : true
		});
	} else {
		alert(errorMessages);

	}
}
// Employee Experience Details
function manageEmpExperience() {
	var errorMessage = "";
	if (document.getElementById('experience').value == "") {
		errorMessage += "Enter Experience\n";
	}
	if (document.getElementById('fromdate').value == "") {
		errorMessage += "Enter From Date\n";
	}
	if (document.getElementById('todate').value == "") {
		errorMessage += "Enter To Date\n";
	}
	if(document.getElementById('fromdate').value != "" && document.getElementById('todate').value != ""){
	if(!compareDate($jq('#todate').val(),$jq('#fromdate').val())){
		errorMessage += "Invalid Dates\n";
	}
	}
	if (errorMessage == "") {
		document.forms[0].param.value = "create";
		new Ajax.Updater('experienceList', 'empExperience.htm'+'?'+dispUrl, {
			onComplete : function() {clearEmpExperience();
			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : false,
			evalScripts : true
		});
	} else {
		alert(errorMessage);
	}
}
function editExperience(id,experience,fromDate,toDate,description) {
	// document.forms[0].param.value = "EditExperience";
	document.forms[0].id.value = id;
	// document.forms[0].action = "empExperience.htm";
	// document.forms[0].submit();
	document.forms[0].experience.value = experience;
	document.forms[0].fromDate.value = fromDate;
	document.forms[0].toDate.value = toDate;
	document.forms[0].description.value = description;
	
	if (document.forms[0].description.textLength > 0) {
		document.getElementById('counter').value -= document.forms[0].description.textLength;
	}
}
function updateExperience() {
	var errorMessage = "";
	if (document.getElementById('experience').value == "") {
		errorMessage += "Enter experience\n";
	}
	if (document.getElementById('fromdate').value == "") {
		errorMessage += "Enter From Date\n";
	}
	if (document.getElementById('todate').value == "") {
		errorMessage += "Enter To Date\n";
	}
	if (errorMessage == "") {
		document.forms[0].param.value = "update";
		new Ajax.Updater('experienceList', 'empExperience.htm'+'?'+dispUrl, {
			onComplete : function() {
			clearEmpExperience();
			
			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : false,
			evalScripts : true
		});
	} else {
		alert(errorMessage);
	}
}
function deleteExperience(id) {
	var del = confirm("Do you want to delete this item?");
	if (del == true) {
		document.forms[0].param.value = "delete";
		document.forms[0].id.value=id;
		new Ajax.Updater('experienceList', 'empExperience.htm'+'?'+dispUrl, {
			onComplete : function() {
			clearEmpExperience();
			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : false,
			evalScripts : true
		});
	}
}

function experienceDetails() {
	document.forms[0].param.value = "home";
	document.forms[0].action = "empExperience.htm";
	document.forms[0].submit();
}
// Pre Organisation Detais
function getPreOrgnDetails() {
	var errorMessage = "";
	var status = true;
	if (document.getElementById('sfid') != null
			&& document.getElementById('sfid').value == "") {
		errorMessage = "Please Enter SFID\n";
		document.getElementById('sfid').focus();
		status = false;
	}
	if (status) {
		document.forms[0].param.value = "getPreOrgn";
		new Ajax.Updater('viewPreOrgnDetails', 'preOrgnDetails.htm'+'?'+dispUrl, {
			onComplete : function() {
			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : false,
			evalScripts : true
		});
	} else {
		alert(errorMessage);
	}
}
function managePreOrgnDetails() {
	var errorMessage = "";
    var flag = true;
	if (document.getElementById('orgtype').value == "select") {
		errorMessage += "Select Organisation Types\n";
	}
	if (document.getElementById('orgname').value == "") {
		errorMessage += "Enter Organisation Name\n";
	}
	if (document.getElementById('fromDate').value == "") {
		errorMessage += "Enter From Date\n";
	}
	if (document.getElementById('toDate').value == "") {
		errorMessage += "Enter To Date\n";
		
		
	}
	//compare from date with todays date
	if(document.getElementById('fromDate').value != ""){
	var currentDate=new Date();
	 currentDate=formatDate(currentDate,'dd-NNN-yyyy');
	 if(!compareDate(currentDate,document.getElementById('fromDate').value) ) {
			errorMessage += "Invalid Date..[From Date should be before of  Todays's Date] \n";
			if (flag) {
				document.getElementById('fromDate').focus();
				flag = false;
			}
	    }
	}
	
	// compare date
	if (!document.getElementById('toDate').value == '') {
		
		   var dateCheck = document.getElementById('fromDate').value;
		   var tod = document.getElementById('toDate').value;
		   if(!compareDate(tod,dateCheck)) {
				errorMessage += "Invalid Date..[To Date Is should be after  Form Date] \n";
				if (flag) {
					document.getElementById('toDate').focus();
					flag = false;
				}
		    }
   }
	if($jq('#scaleOfPay').val()!=""){
	if($jq('#scaleOfPay').val()<10000 || $jq('#scaleOfPay').val()>20000){
		errorMessage += "Enter Valid Range Of Amount\n";
	}
	}
	/*
	 * if(document.getElementById('orgtype').value =="20"){ var fromDate1 =
	 * document.getElementById('fromDate').value; for ( var i = 0; i <
	 * preOrgJSONList.length; i++){
	 * if(compareDates(convertDate(preOrgJSONList[i].fromDate)),convertDate(fromDate1)){
	 * errorMessage += "one----------------"; } } }
	 */
	if(document.getElementById('orgtype').value =="20"){
		 var fromDate1 = document.getElementById('fromDate').value;
		 var toDate1 = document.getElementById('toDate').value;
		 for ( var i = 0; i < preOrgJSONList.length; i++){
			 if(document.forms[0].id.value !=preOrgJSONList[i].id){
			 if(convertDate(preOrgJSONList[i].fromDate) == (convertDate(fromDate1)) || convertDate(preOrgJSONList[i].toDate) == (convertDate(toDate1))){
		     errorMessage += "This Organization you have already these Dates  \n";
		     }
			 }
	      }
	   }
	if(document.getElementById('orgtype').value =="21"){
		 var fromDate1 = document.getElementById('fromDate').value;
		 var toDate1 = document.getElementById('toDate').value;
		 for ( var i = 0; i < preOrgJSONList.length; i++){
			 if(document.forms[0].id.value !=preOrgJSONList[i].id){
			 if(convertDate(preOrgJSONList[i].fromDate) == (convertDate(fromDate1)) || convertDate(preOrgJSONList[i].toDate) == (convertDate(toDate1))){
		     errorMessage += "This Organization you have already these Dates e";
		     }
			 }
	      }
	   }
	
	 if (errorMessage == "") {
			document.forms[0].param.value = "create";
			new Ajax.Updater('preOrgnDetailsList', 'preOrgnDetails.htm'+'?'+dispUrl, {
				onComplete : function() {clearPreOrgnDetails();
				},
				parameters : Form.serialize(document.forms[0]),
				asynchronous : false,
				evalScripts : true
			});
		}
	
	 

	
    // srinu
	
	 else {
			alert(errorMessage);
			
		}  
	 
	
}
function editPreOrgnDetails(id) {
	
	for ( var i = 0; i < preOrgJSONList.length; i++) {
		if (preOrgJSONList[i].id == id) {
			document.getElementById("orgtype").value = preOrgJSONList[i].orgTypeId;
			document.getElementById("orgname").value = preOrgJSONList[i].orgName;
			document.getElementById("fromDate").value = preOrgJSONList[i].fromDate;
			document.getElementById("toDate").value = preOrgJSONList[i].toDate;
			document.getElementById("divisionName").value = preOrgJSONList[i].divisionName;
			document.getElementById("rankHeld").value = preOrgJSONList[i].rankHeld;
			document.getElementById("jobDescription").value = preOrgJSONList[i].jobDescription;
			document.getElementById("skills").value = preOrgJSONList[i].skills;
			document.getElementById("pay").value = preOrgJSONList[i].pay;
			document.getElementById("scaleOfPay").value = preOrgJSONList[i].scaleOfPay;
			document.getElementById("remarks").value = preOrgJSONList[i].remarks;
			document.forms[0].id.value =id;
			if (preOrgJSONList[i].remarks.length > 0) {
				document.getElementById('counter').value = 500;
				document.getElementById('counter').value -= preOrgJSONList[i].remarks.length;
			}
		}
	}
}
function updatePreOrgnDetails() {
	var errorMessage = "";
	if (document.getElementById('orgtype').value == "select") {
		errorMessage += "Select Organisation Types\n";
	}
	if (document.getElementById('orgname').value == "") {
		errorMessage += "Enter Organisation Name\n";
	}
	if (document.getElementById('fromDate').value == "") {
		errorMessage += "Enter From Date\n";
	}
	if (document.getElementById('toDate').value == "") {
		errorMessage += "Enter To Date\n";
	}
	if (errorMessage == "") {
		document.forms[0].param.value = "update";
		new Ajax.Updater('preOrgnDetailsList', 'preOrgnDetails.htm'+'?'+dispUrl, {
			onComplete : function() {clearPreOrgnDetails();

			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : false,
			evalScripts : true
		});
	} else {
		alert(errorMessage);
	}
}
function deletePreOrgnDetails(id) {
	var del = confirm("Do you want to delete this item?");
	if (del == true) {
		document.forms[0].param.value = "delete";
		document.forms[0].id.value = id;
		new Ajax.Updater('preOrgnDetailsList', 'preOrgnDetails.htm'+'?'+dispUrl, {
			onComplete : function() {

			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : false,
			evalScripts : true
		});
	}
}

// Nominee Details

function editNomineeDetails(id,no,count,noIds) {
	if(id==''){
		nomineeID = 0;
	}else{
		nomineeID = id;
	}	
	
	document.forms[0].param.value = "EditNominee";
	if(id==null || id==""){
		document.forms[0].id.value = id;
		new Ajax.Updater("viewData", 'nominee.htm'+'?'+dispUrl, {
			onComplete : function() {
				for(var i=0;i<=noIds;i++){
					if(document.getElementById('el'+i)!=null)
						document.getElementById('el'+i).style.display="none";
				}
			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : false,
			evalScripts : true
		});
			
	}else{
		document.forms[0].id.value = id;
		var divid="";
		if(no!=null && no!=undefined)
			divid="createNomineeDetails"+no;
		else
			divid="createNomineeDetails"
		new Ajax.Updater(divid, 'nominee.htm'+'?'+dispUrl, {
			onComplete : function() {
				var array=count.split(",");
				for(var i=1;i<array.length;i++){
					document.getElementById('createNomineeDetails'+(parseInt(no)+i)).style.display="none";
				}
				for(var i=0;i<=noIds;i++){
					if(document.getElementById('el'+i)!=null)
						document.getElementById('el'+i).style.display="none";
				}
				document.getElementById('newbutton').style.display="none";
				document.getElementById('nomineeTypeId').disabled=true;
			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : false,
			evalScripts : true
		});
	}
}

function deleteInternalStructure(id) {
	new Ajax.Updater('result', 'hierarchy.htm?param=deleteTree&id=' + id, {
		onComplete : function() {
		},
		asynchronous : false,
		evalScripts : true
	});
}
function clearFamily() {
	document.getElementById("id").value="";
	document.forms[0].name.value = "";
	document.getElementById('genderf').checked = false;
	document.getElementById('genderm').checked = false;
	document.forms[0].relationId.value = "select";
	document.forms[0].dob.value = "";
	// document.getElementById('dependentNo').checked = true;
	document.forms[0].dependentFrom.value = "";
	document.forms[0].maritalstatus.value = "select";
	document.forms[0].address1.value = "";
	document.forms[0].address2.value = "";
	document.forms[0].address3.value = "";
	// document.getElementById('stateId').value = "0";
	document.getElementById('familyage').value = "";
	document.forms[0].city.value = "";
	// document.getElementById("districtId").options.length = 1;
	document.forms[0].contactNumber.value = "";
	document.getElementById('employeedNo').checked = true;
	document.forms[0].employeedType.value = "select";
	document.getElementById('no').checked = true;
	document.getElementById('dobselection').value = "select";
	document.getElementById('familydob').value = "";
	document.getElementById('familyage').value = "";
	document.getElementById('addressTypeId').value = "select";
	document.getElementById('bloodGroup').value = "0";
	document.getElementById('pincode').value = "";
	document.forms[0].declareDate.value = "";
	document.getElementById('addressdiv').style.display="none";
	document.getElementById('familyPin').style.display="none";
	document.getElementById('familyagediv').style.display="none";
	document.getElementById('familydobirth').style.display="none";
	document.getElementById('dependentdate').style.display="none";
	document.getElementById('emporgtype').style.display="none";
	document.getElementById('adoptedDate').value="";
	document.getElementById('adoptedNo').checked = true;
	document.getElementById('phtypeFlagNo').checked = true;
	document.getElementById('adopt').style.display="none";
	document.getElementById('adoptedfrom').style.display="none";
	document.getElementById('cghsNotApplicable').checked=true;
	document.getElementById('ltcNotApplicable').checked=true;
	document.getElementById('disabilityId').value= "Select";
	document.getElementById('placeOfIssue').value= "";
	document.getElementById('beneficiary').value= "";
	document.getElementById('validFrom').value= "";
	document.getElementById('validTo').value= "";	
	$jq('#cghscardfile').val('');	
	
	
	
}
function clearEmpExperience() {
	document.forms[0].experience.value = "";
	document.forms[0].description.value = "";
	document.forms[0].fromDate.value = "";
	document.forms[0].toDate.value = "";
	if (document.getElementById("empExpSubmit") != null) {
		document.getElementById("empExpSubmit").style.display = "block";
	}
	if (document.getElementById("empExpUpdate") != null) {
		document.getElementById("empExpUpdate").style.display = "none";
	}
	document.getElementById('counter').value = "500";
	document.forms[0].id.value=""; 
}

function clearPreOrgnDetails() {
	document.forms[0].id.value = "";
	document.forms[0].orgType.value = "select";
	document.forms[0].orgName.value = "";
	document.forms[0].fromDate.value = "";
	document.forms[0].toDate.value = "";
	document.forms[0].divisionName.value = "";
	document.forms[0].rankHeld.value = "";
	document.forms[0].jobDescription.value = "";
	document.forms[0].skills.value = "";
	document.forms[0].remarks.value = "";
	document.forms[0].pay.value = "";
	document.forms[0].scaleOfPay.value = "";
	document.forms[0].counter.value = "500";
	if (document.getElementById("preOrgnSubmit") != null) {
		document.getElementById("preOrgnSubmit").style.display = "block";
	}
	if (document.getElementById("preOrgnUpdate") != null) {
		document.getElementById("preOrgnUpdate").style.display = "none";
	}

}
function displayValues(id, displayid,age) {
	if (document.getElementById(id) != null
			&& document.getElementById(id).value == "yes") {
		document.getElementById(displayid).style.display = "block";
	} else if (document.getElementById(id) != null){
		document.getElementById(displayid).style.display = "none";
	}
	if(age!=null && age!=undefined && age!="")
	{
		document.getElementById("familyagediv").style.display = "block";
		document.getElementById("familydobirth").style.display = "none";
	}else if(age!=null && age!=undefined)
	{
		document.getElementById("familyagediv").style.display = "none";
		document.getElementById("familydobirth").style.display = "block";
	}
}
// Award Details start
function AwardDetails() {
	document.forms[0].param.value = "home";
	document.forms[0].action = "awardDetails.htm";
	document.forms[0].submit();
}

function clearAward() {
	if(document.getElementById('awardCategory')!=null)
		document.getElementById('awardCategory').value = "Select";
	if(document.getElementById('organization')!=null)
		document.getElementById('organization').value = "";
	if(document.getElementById('year')!=null)
		document.getElementById('year').value = "Select";
	if(document.getElementById('cash')!=null)
		document.getElementById('cash').value = "";
	if(document.getElementById('medallion')!=null)
		document.getElementById('medallion').value = "";
	if(document.getElementById('citation')!=null)
		document.getElementById('citation').value = "";
	if(document.getElementById('certificate')!=null)
		document.getElementById('certificate').value = "";
	if(document.getElementById('detailsOfWork')!=null)
		document.getElementById('detailsOfWork').value = "";
	if(document.getElementById('awardDescription')!=null)
		document.getElementById('awardDescription').value = "";
	if(document.getElementById('remarks')!=null)
		document.getElementById('remarks').value = "";
	if(document.getElementById('id')!=null)
		document.getElementById('id').value = "";
	if(document.getElementById('counter1')!=null)
		document.getElementById('counter1').value = "500";
	if(document.getElementById('counter2')!=null)
		document.getElementById('counter2').value = "500";
	if(document.getElementById('counter3')!=null)
		document.getElementById('counter3').value = "500";
}
function editAward(awardJson, awardId) {

	for ( var i = 0; i < awardJson.length; i++) {
		if (awardJson[i].id == awardId) {
			document.getElementById('awardCategory').value = awardJson[i].awardCategory;
			document.getElementById('organization').value = awardJson[i].organization;
			document.getElementById('year').value = awardJson[i].year;
			document.getElementById('cash').value = awardJson[i].cash;
			document.getElementById('medallion').value = awardJson[i].medallion;
			document.getElementById('citation').value = awardJson[i].citation;
			document.getElementById('certificate').value = awardJson[i].certificate;
			document.getElementById('detailsOfWork').value = awardJson[i].detailsOfWork;
			document.getElementById('awardDescription').value = awardJson[i].awardDescription;
			document.getElementById('remarks').value = awardJson[i].remarks;
			document.getElementById('id').value = awardJson[i].id;
			if (awardJson[i].awardDescription.length > 0) {
				document.getElementById('counter1').value = 500;
				document.getElementById('counter1').value -= awardJson[i].awardDescription.length;
			}
			if (awardJson[i].remarks.length > 0) {
				document.getElementById('counter2').value = 500;
				document.getElementById('counter2').value -= awardJson[i].remarks.length;
			}
			if (awardJson[i].detailsOfWork.length > 0) {
				document.getElementById('counter3').value = 500;
				document.getElementById('counter3').value -= awardJson[i].detailsOfWork.length;
			}
		}
	}
}
function manageAward() {
	var errorMessage = "";
	var status = true;
	if (document.getElementById('awardCategory').value == "Select") {
		errorMessage = "Please Select AwardCategory\n";
		status = false;
	}
	if (document.getElementById('year').value == "Select") {
		errorMessage += "Please Select Year\n";
		status = false;
	}
	if (status) {
		document.forms[0].param.value = "manage";
		new Ajax.Updater('awardList','awardDetails.htm'+'?'+dispUrl, {
			onComplete : function() {
				clearAward();
			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : false,
			evalScripts : true
		});
	} else {
		alert(errorMessage);
	}
}
function deleteAward(awardId) {
	var del = confirm("Do you want to delete this item?");
	if (del == true) {
		document.forms[0].param.value = "delete";
		document.getElementById('id').value = awardId;
		new Ajax.Updater('awardList', 'awardDetails.htm'+'?'+dispUrl, {
			onComplete : function() {clearAward();
			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : false,
			evalScripts : true
		});
	}
}

// Award Details end

// Quarter Details start
function clearQuarter() {
	$jq('#quarterNo').val('');
	$jq('#occupiedDate').val('');
	$jq('#quarterSubType').val('0');
	nodeID ="";
}

function editQuarter(id,quarterSubTypeID,quarterNo,occupiedDate){	
	nodeID = id;
	$jq('#quarterSubType').val(quarterSubTypeID);
	$jq('#quarterNo').val(quarterNo);
	$jq('#occupiedDate').val(occupiedDate);
}


function manageQuarter() {
	var errorMessage = "";
	var status = true;

	if ($jq('#quarterSubType').val() == '0') {
		errorMessage += "Please select quarter sub type.\n";
		if (status) {
			status = false;
			$jq('#quarterSubType').focus();
		}
	}
	if ($jq('#quarterNo').val() == '') {
		errorMessage += "Please enter quarter number.\n";
		if (status) {
			status = false;
			$jq('#quarterNo').focus();
		}
	}
	if ($jq('#occupiedDate').val() == '') {
		errorMessage += "Please select occupied date.\n";
		if (status) {
			status = false;
			$jq('#occupiedDate').focus();
		}
	}
	if(nodeID==""){
		// New record check whether already any quarter is assigned or not
		$jq("#quarterData tr:not(:first)").each(function() {	
			if($jq(this).text()!='Nothing found to display.'){
				errorMessage += "Already quarter assigned.\n";
				if (status) {
					status = false;
					$jq('#quarterSubType').focus();
				}
			}
		});		
	}
	
	if (status) {
		var requestDetails = {
			"nodeID" : nodeID,
			"quarterSubType" : $jq('#quarterSubType').val(),
			"quarterNo" : $jq('#quarterNo').val(),
			"occupiedDate" : $jq('#occupiedDate').val(),
			"param" : "manage"
		};
		$jq.ajax( {
			type : "POST",
			url : "quarterDetails.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#quartersList").html(html);
				clearQuarter();
			}
		});
	} else {
		alert(errorMessage);
	}
}
function deleteQuarter(id) {
	var del = confirm("Do you want to delete this item?");
	if (del == true) {
		var requestDetails = {
			"nodeID" : id,
			"param" : "delete"
		};
		$jq.ajax( {
			type : "POST",
			url : "quarterDetails.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#quartersList").html(html);
				clearQuarter();
			}
		});
	}	
}
// Quarter Details end

// Retirement Details start

function RetirementDetails() {
	document.forms[0].param.value = "home";
	document.forms[0].action = "retirementDetails.htm";
	document.forms[0].submit();
}

function clearRetirement() {
	if(document.getElementById('idNo')!=null)
		document.getElementById('idNo').value = "";
	if(document.getElementById('retirementType')!=null)
		document.getElementById('retirementType').value = "Select";
	if(document.getElementById('retirementDate')!=null)
		document.getElementById('retirementDate').value = "";
	if(document.getElementById('referenceNumber')!=null)
		document.getElementById('referenceNumber').value = "";
	if(document.getElementById('id')!=null)
		document.getElementById('id').value = "";
}
function editRetirement(retirementJson, retirementId) {

	for ( var i = 0; i < retirementJson.length; i++) {
		if (retirementJson[i].id == retirementId) {
			document.getElementById('idNo').value = retirementJson[i].idNo;
			document.getElementById('retirementType').value = retirementJson[i].retirementType;
			document.getElementById('retirementDate').value = retirementJson[i].retirementDate;
			document.getElementById('referenceNumber').value = retirementJson[i].referenceNumber;
			document.getElementById('id').value = retirementJson[i].id;
		}
	}
}
function manageRetirement() {
	var errorMessage = "";
	var status = true;
	if (document.getElementById('retirementType').value == "Select") {
		errorMessage = "Please Select Retirement Type\n";
		document.getElementById('retirementType').focus();
		status = false;
	}
	if (status) {
		document.forms[0].param.value = "manage";
		new Ajax.Updater('retirementList', 'retirementDetails.htm', {
			onComplete : function() {clearRetirement();
			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : false,
			evalScripts : true
		});
	} else {
		alert(errorMessage);
	}
}
function deleteRetirement(retirementId) {
	var del = confirm("Do you want to delete this item?");
	if (del == true) {
		document.forms[0].param.value = "delete";
		document.getElementById('id').value = retirementId;
		new Ajax.Updater('retirementList', 'retirementDetails.htm', {
			onComplete : function() {clearRetirement();
			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : false,
			evalScripts : true
		});
	}
}

// Retirement Details end

function editInternalStructure(id, sfid, internalDivision, internalRole, departmentID) {
	clearEmpRoleMapping();
	internalTreeID = id;
	
	document.getElementById('department').value = departmentID;
	
	if(subOrdinatesList!=null){
		document.getElementById("mapSFID").options.length = 1;
		for ( var i = 0; i < subOrdinatesList.length; i++) {
			if (subOrdinatesList[i].id == departmentID) {
				var len = document.getElementById("mapSFID").options.length++;
				document.getElementById("mapSFID").options[len].text = subOrdinatesList[i].name;
				document.getElementById("mapSFID").options[len].value = subOrdinatesList[i].key;					
			}				
		}			
	}
	document.getElementById('mapSFID').value = sfid;
	
	if(internalDivisionsList!=null){
		document.getElementById("internalDivisions").options.length = 2;
		for ( var i = 0; i < internalDivisionsList.length; i++) {
			if (internalDivisionsList[i].id == departmentID) {
				var len = document.getElementById("internalDivisions").options.length++;
				document.getElementById("internalDivisions").options[len].text = internalDivisionsList[i].name;
				document.getElementById("internalDivisions").options[len].value = internalDivisionsList[i].key;	
				if (internalDivisionsList[i].name == internalDivision)
					document.getElementById('internalDivisions').options[len].selected = true;
			}				
		}			
	}
	
	if(internalRolesList!=null){
		document.getElementById("internalRoleName").options.length = 4;
		for ( var i = 0; i < internalRolesList.length; i++) {
			if (internalRolesList[i].id == departmentID) {
				var len = document.getElementById("internalRoleName").options.length++;
				document.getElementById("internalRoleName").options[len].text = internalRolesList[i].name;
				document.getElementById("internalRoleName").options[len].value = internalRolesList[i].key;	
				if (internalRolesList[i].name == internalRole){
					document.getElementById('internalRoleName').options[len].selected = true;
				}
			}				
		}			
	}	
	if(document.getElementById("internalRoleName").value=='select'){
		document.getElementById("internalRoleName").value='Employee';
	}
	document.getElementById("internalRoleName").value=internalRole;
	editNewDivision = internalDivision;
}


//added by bkr 07/04/2016 
function getWaterRequestDetails(requestID) {
	//alert("am in script.js getWaterRequestDetails()");
	document.forms[0].requestId.value = requestID;
	document.forms[0].param.value = "waterRequestDetails";
	document.forms[0].action = "tadaWaterRequest.htm";
	document.forms[0].submit();
}

//added by bkr 10/05/2016

function getWaterAdvCompDetails(requestID) {
	document.forms[0].requestId.value = requestID;
	document.forms[0].param.value = "tadaWaterAdvCompDetails";
	document.forms[0].action = "tadaWaterRequest.htm";
	document.forms[0].submit();
}

function getWaterAdvCompDetailsForCancel(requestID){
	document.forms[0].requestId.value = requestID;
	document.forms[0].param.value = "tadaWaterAdvCompDetailsforCancel";
	document.forms[0].action = "tadaWaterRequest.htm";
	document.forms[0].submit();
	
}

//this function added by bkr 11/05/2016
function gettadaWaterSettlementDetails(requestID){
	document.forms[0].requestId.value = requestID;
	//alert("am in script.js gettadaWaterSettlementDetails()");
	document.forms[0].param.value = "tadaWaterSettlementDetails";
	document.forms[0].action = "tadaWaterRequest.htm";
	document.forms[0].submit();
}


function gettadaWaterReimbursementDetails(requestID){
	//alert("am in script.js gettadaWaterReimbursementDetails()");
	document.forms[0].requestId.value = requestID;
	document.forms[0].param.value = "tadaWaterReimbursementDetails";
	document.forms[0].action = "tadaWaterRequest.htm";
	document.forms[0].submit();
	
}

function getRequestDetails(historyID, requestID, message, status,back) {
	document.forms[0].message.value = message;
	document.forms[0].requestId.value = requestID;
	document.forms[0].historyID.value = historyID;
	document.forms[0].statusMsg.value = status;
	document.forms[0].back.value = back;
	document.forms[0].param.value = "requestDetails";
	document.forms[0].action = "workflowmap.htm";
	document.forms[0].submit();
}
/*
 * function RequestIDGet(requestId, roleId) { var errorMessage = ""; var status =
 * true;
 * 
 * 
 * if(document.getElementById('requestIdBased').value == " "){
 * errorMessage+="Please Select Request Type"; status=false;
 * document.getElementById('requestIdBased').focus(); }
 * 
 * if($jq('#requestIdBased').val()==''){ errorMessage += "Please Select Request
 * Type"; status=false; $jq('#requestIdBased').focus(); } if (status) { var
 * request = document.forms[0].requestIdBased.value;
 * document.forms[0].requestId.value = $jq.trim(request);
 * 
 * document.forms[0].roleId.value = roleId; document.forms[0].param.value =
 * "requestDetails"; document.forms[0].action = "workflowmap.htm";
 * document.forms[0].submit(); } else { alert(errorMessage); } }
 */
function checkKey(e,id,requestId, roleId){
	var key;
	var value = document.getElementById(id).value; 
	
	
	if(window.event){
		key = window.event.keyCode;
	}
	else{
		key = e.which;
	}
	if(value=="" && key==13){
		alert("Please Enter RequestID ");
		return false;
	}
	else if(key!=13 && key!=8 && key!=0 && key!=46 &&  (key<48 || key>57)){
		alert("Please Enter Numbers Only");
		
		return false;
	}
	if(value!="" && key==13){
		var status = true;
	if (status) {
		var request = document.forms[0].requestIdBased.value;
	     document.forms[0].requestId.value = $jq.trim(request);
	 
		document.forms[0].roleId.value = roleId;
	    document.forms[0].param.value = "requestDetails";
		document.forms[0].action = "workflowmap.htm";
		document.forms[0].submit();
		return true;
		}
	}
	
}

function resetDate()
{
	$jq('input:text').val('');
}

function checkReqId(e,id,requestId, roleId){
	var key;
	var value = document.getElementById(id).value; 
	
	
	if(window.event){
		key = window.event.keyCode;
	}
	else{
		key = e.which;
	}
	if(value=="" && key==13){
		alert("Please Enter RequestID ");
		return false;
	}
	else if(key!=13 && key!=8 && key!=0 && key!=46 &&  (key<48 || key>57)){
		alert("Please Enter Numbers Only");
		
		return false;
	}
	if(value!="" && key==13){
		var status = true;
	if (status) {
		getMySearchList();
		
		}
	}
	
}

function getAlertDetails(alertMsgID){
	document.forms[0].historyID.value = alertMsgID;
	document.forms[0].param.value = "alertDetails";
	document.forms[0].action = "workflowmap.htm";
	document.forms[0].submit();
	
}
function textCounter(field, countfield, maxlimit) {
	if (field.value.length > maxlimit) {
		field.value = field.value.substring(0, maxlimit);
		// alert( 'Textarea value can only be 10 characters in length.' );
		return false;
	} else {
		countfield.value = maxlimit - field.value.length;
	}
}


function gridStyle(tableID) {
	if (document.getElementById(tableID) != null) {
		for (i = 2; i < document.getElementById(tableID).rows.length; i++) {
			if (i % 2 == 0)
				document.getElementById(tableID).rows[i].className = "secondRow";
			else
				document.getElementById(tableID).rows[i].className = "firstRow";
		}
	}
}


// Emp search details start

function getSearchDetails() {
	var errormessage = "";
	var status=true;
	if (document.getElementById("sfid").value == ""
			&& document.getElementById("empname").value == ""
			&& document.getElementById("desigId").value == "") {
		errormessage += "Please Enter Employee ID (or) Employee Name (or) Select Designation\n";
		document.getElementById("sfid").focus();
		status=false;
	}
	/*
	 * if (document.getElementById("empname").value != "" &&
	 * document.getElementById("searchwith").value == "") { errormessage +=
	 * "Please select searchWith\n"; if(status){
	 * document.getElementById("empname").focus(); status=false; } }
	 */
	if($jq('#empname').val() == ''   && $jq('#sfid').val()=='' &&  $jq('#desigId').val()==''){
		errormessage += "Please Enter Employee Name \n";
		 $jq('#empname').focus();
		 status = false;
	}
	if (errormessage == "" && status) {
		document.forms[0].param.value = "getDetails";
         $jq('#searchwith').val('contain');
         
         $jq.post('empSearch.htm', $jq('#employee').serialize(),
 				function(html) {
 				$jq("#empSearchList").html(html);
 				});
         
		/*
		 * new Ajax.Updater('empSearchList', 'empSearch.htm', { onComplete :
		 * function() { }, parameters : Form.serialize(document.forms[0]),
		 * asynchronous : false, evalScripts : true });
		 */
	} else {
		alert(errormessage);
	}
}
function selectedDivisionList() {

	if(document.getElementById('directorateId').value!='Select') {
		document.forms[0].param.value = "selectedDivision";
		new Ajax.Updater('selectedDivision', 'employee.htm', {
			onComplete : function() {
			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : false,
			evalScripts : true
		});
	}
}

function getEmployeeSearchDetailsList(id,empTreeIdentity) {
	document.forms[0].id.value = id;
	document.forms[0].param.value = "empSearchDetails";
	document.forms[0].action = "empSearch.htm?empTreeIdentity="+empTreeIdentity;
	document.forms[0].submit();
}
function viewSearchDetails() {
		document.forms[0].param.value = "view";
		document.forms[0].action = "empSearch.htm";
		document.forms[0].submit();
		
}

/*
 * function familyRequest(){ var errorMessage = ""; if
 * (document.forms[0].name.value == "") { errorMessage += "Enter name\n"; } if
 * (document.getElementById('genderf').checked == false &&
 * document.getElementById('genderm').checked == false) { errorMessage +=
 * "Select gender\n"; } if (document.getElementById('maritalstatus').value ==
 * "select") { errorMessage += "Enter Marital Status\n"; } if
 * (document.getElementById('relation').value == "select") { errorMessage +=
 * "Select Relation\n"; } if
 * (document.getElementById('familydobirth').style.display=="block" &&
 * document.getElementById('familydob').value == "") { errorMessage += "Enter
 * date of birth\n"; } if
 * (document.getElementById('familyagediv').style.display=="block" &&
 * document.getElementById('familyage').value == "") { errorMessage += "Enter
 * age\n"; } if (document.getElementById('state').value == "select") {
 * errorMessage += "Please Select state\n"; } if
 * (document.getElementById('district').value == "select") { errorMessage +=
 * "Please Select district\n"; } if (errorMessage == "") { if(familyID==""){
 * familyID = 0; } document.forms[0].param.value = "submitRequest"; new
 * Ajax.Updater('reqResult', 'family.htm?familyID='+familyID, { onComplete :
 * function() { }, parameters : Form.serialize(document.forms[0]), asynchronous :
 * false, evalScripts : true }); } else { alert(errorMessage); } }
 */

// Emp search details end

// reuest master flag value start
function checkingFlagValue(jsonMasterList) {
	if(document.forms[0].flagValue!=null){
		for(var i=0;i<jsonMasterList.length;i++){
			if(jsonMasterList[i].internalFlag=="1"){
				document.getElementById(jsonMasterList[i].name).checked=true;
			}
		}
	}

}
// reuest master flag value end

// Head Mapping Changes
function setHeadValues(valuejson) {
	if(valuejson!="")
	{
		var value=JSON.parse(valuejson);
		document.getElementById("mapSFID").value=value[0];
		document.getElementById("roleInstanceName").value=value[2];
		document.getElementById("roleInstanceSubName").value=value[1];
	}
}
function editHead(instanceJson,id)  {	
	for ( var i = 0; i < instanceJson.length; i++) {
		if (instanceJson[i].instanceId == id) {
			document.getElementById("id").value=instanceJson[i].id;
			document.getElementById("mapSFID").value=instanceJson[i].sfid;				
			if(instanceJson[i].roleId<9)
			{
				getInstanceSubLevels(instanceJson[i].instanceId);
				document.getElementById("roleInstanceName").value=instanceJson[i].instanceId;
			}else
			{
				getInstanceSubLevels(instanceJson[i].directorateId);
				document.getElementById("roleInstanceSubName").value=instanceJson[i].instanceId;
				document.getElementById("roleInstanceName").value=instanceJson[i].directorateId;
			}
			document.getElementById("namediv").style.display="block";
			document.getElementById("name").innerHTML=instanceJson[i].empName;
		}
	}
	document.getElementById("roleInstanceName").disabled=true;
	document.getElementById("roleInstanceSubName").disabled=true;
	setWidth('#roleInstanceName');
	setWidth('#roleInstanceSubName');
	
}
function deleteHead(sfid,instanceId) {
	
	new Ajax.Updater('headresult', 'hierarchy.htm?param=deleteHead&mapSFID='+sfid+'&roleInstanceSubName='+instanceId+'&'+dispUrl, {
			onComplete : function() {
		},
		asynchronous : false,
		evalScripts : true
	});
}

// Head Changes end

// Employee Mapping start
function employeeMappingSubmit(type)
{
	var checkedValues = new Array;
	var errorMessages = "";
	if(document.getElementById('roleInstanceName').value=="select" && type=="map"){
		errorMessages = "Please Select DIR/PD/AD/TD/PROJ/DIV \n";
		document.getElementById('roleInstanceName').focus();
	}
	var cnt=0;
	for(var i=1;i<document.getElementById('employeeList').rows.length;i++){
		if(document.getElementById('employeeList').rows[i].cells[0].childNodes[0].checked==true){
			checkedValues[cnt]=document.getElementById('employeeList').rows[i].cells[0].childNodes[0].value;
			cnt++;
		}			
	}
	if(checkedValues.size()==0){
		errorMessages += "Please Select any row for mapping\n";
	}
	if(errorMessages=="")
	{
		var sfid="";		
		for ( var j = 0; j < checkedValues.size(); j++) {
			var count=0;
			var empid=checkedValues[j].split("#")[1];
			for ( var i = 0; i < divisionList.length; i++) {				
				if(empid==divisionList[i].parent)
				{					
					if(count==0)
					{
						sfid+=empid+",";
						checkedValues[j]+="#"+"yes"+",";
					}						
					count++;
				}
			}
		}
		var flag=false;
		if(sfid!="")
		{
			if(confirm("The sfids"+ sfid + "is/are internal division heads.Still you want to make them as normal employees under selected division"))
				flag=true;
		}else
			flag=true;
		if(flag)
		{
			if(type=="map")
				document.forms[0].param.value = "empmappingsubmit";
			else
				document.forms[0].param.value = "empmappingdelete";
			document.forms[0].checkedValues.value=checkedValues;		
			new Ajax.Updater('empresult', 'hierarchy.htm'+'?'+dispUrl, {
					onComplete : function() {
				
				},
				parameters : Form.serialize(document.forms[0]),
				asynchronous : false,
				evalScripts : true
			});
		}
	}else
	{
		alert(errorMessages);
	}
}
function clearEmpMapping ()
{
	document.getElementById('roleInstanceName').value = "select";
	document.getElementById('id1').value="";
	document.getElementById("searchWithdiv").style.display="none";
	document.getElementById("searchValues").style.display="none";
	document.getElementById("divisions").style.display="none";
	document.getElementById("empname").value="select";
}
function editEmpMapping(id,sfid,instanceId)
{
	document.getElementById('mapSFID').value = sfid;
	document.getElementById('roleInstanceName').value = instanceId;
	document.getElementById('empmapsubmit').style.display="none";
	document.getElementById('empmapupdate').style.display="block";
}
function deleteEmpMapping(id,sfid,instanceId)
{
	document.forms[0].param.value = "empmappingdelete";
	document.forms[0].id.value=id;
	document.getElementById('mapSFID').value = sfid;
	document.forms[0].roleInstanceName.value=instanceId;
	new Ajax.Updater('empresult', 'hierarchy.htm'+'?'+dispUrl, {
			onComplete : function() {
		},
		parameters : Form.serialize(document.forms[0]),
		asynchronous : false,
		evalScripts : true
	});
}

function clearHeadMapping()
{
	document.getElementById('mapSFID').value = "";
	document.getElementById('id').value = "";
	document.getElementById('roleInstanceName').value = "select";
	document.getElementById('roleInstanceSubName').value = "select";
	document.getElementById('defaultFlag').checked=false;
	document.getElementById('namediv').style.display="none";
	document.getElementById("roleInstanceName").disabled=false;
	document.getElementById("roleInstanceSubName").disabled=false;
	document.getElementById("searchWithdiv").style.display="none";
	document.getElementById("searchValues").style.display="none";
	document.getElementById("divisions").style.display="none";
	document.getElementById("empname").value="select";
}

// changeEmployeeSFID start
function setEmpSFID(){
	var errorMessage="";
	var status=true;
	if(document.getElementById('changeSfid').value==""){
		errorMessage+="Please Enter SFID";
		status=false;
		document.getElementById('changeSfid').focus();
	}
	if(status){
		document.forms[0].changeSfid.value = (document.getElementById('changeSfid').value).toUpperCase();
		document.forms[0].param.value = "changedSfid";
		
		new Ajax.Updater('EmpChangeSFID',
				'master.htm', {
					onComplete : function() {
					},
					parameters : Form.serialize(document.forms[0]),
					asynchronous : false,
					evalScripts : true
				});
	}
	else{
		alert(errorMessage);
	}
}
// changeEmployeeSFID end

function requestsDelegate(){
	var checkedRequests = "";
	var errorMessage = "";
	var status=true;
	if(document.getElementById('instanceId').value=='select'){
		errorMessage = "Please Select Delegate To Field\n";
		status=false;
	}
	if(document.getElementById('requestList')==null){
		errorMessage += "Please Select Requests for delegation\n";
		status=false;
	}else{
		for(var i=1;i<document.getElementById('requestList').rows.length;i++){
			if(document.getElementById('requestList').rows[i].cells[0].childNodes[0].checked==true){
				checkedRequests+=document.getElementById('requestList').rows[i].cells[0].childNodes[0].defaultValue+",";
			}			
		}
	}
	if(status && checkedRequests==''){
		errorMessage += "Please Select Requests for delegation\n";
		status=false;
	}
	if(status){
		new Ajax.Updater('reqResult', 'workflow.htm?param=delegateSubmit&instanceId='+document.getElementById('instanceId').value+'&requestIDs='+checkedRequests+'&remarks='+document.getElementById('remarks').value, {
			onComplete : function() {
			clearDelegation();
		},
		asynchronous : false,
		evalScripts : true
	});
		
	}else{
		alert(errorMessage);
	}	
	
}

// changeEmployeeSFID end=======
// changeEmployeeSFID end

function clearSearchDetails(){
	document.getElementById('sfid').value="";
	document.getElementById('searchwith').value="";
	document.getElementById('empname').value="";
	document.getElementById('desigId').value="";
}

function submitNominee(type){
	funtype = "submit";
	var inconsistencyJsonDetails = "";
	var obj= "";
	var incJSON = "";
	var errorMessage = validateNomineeDetails();
	var incPercentage=0;
	var status = false;
	var selectedFamily = "";

	for(var i=0;i<noOfContengensy;i++){
		var nomineeName = "";
		if(document.getElementById('familyID'+i)!=null){
			if(document.getElementById('familyID'+i).value=='select'){
				errorMessage += "Please Select Family Memeber for In contengensy\n";
				document.getElementById('familyID'+i).focus();
				break;
			}else if(document.getElementById('familyID'+i).value!='other'){
				nomineeName=document.getElementById('familyID'+i).options[document.getElementById('familyID'+i).selectedIndex].text;
			}else{
				nomineeName=document.getElementById('nomineeName'+i).value;
			}
			if(document.getElementById('familyID'+i).value=='other'){
				if(document.getElementById('nomineeName'+i).value==''){
					errorMessage += "Please enter Nominee Name In incontengensy\n";
					document.getElementById('nomineeName'+i).focus();
					break;
				}
				obj= {"familyID":document.getElementById('familyID'+i).value,"nomineeName":nomineeName,"percentage":document.getElementById('percentage'+i).value,"dateOfNominate":document.getElementById('dateOfNominate').value,"address1":document.getElementById('address1'+i).value,"address2":document.getElementById('address2'+i).value,"address3":document.getElementById('address3'+i).value,"city":document.getElementById('city'+i).value,"state":document.getElementById('stateId'+i).value,"district":document.getElementById('districtId'+i).value,"pincode":document.getElementById('pincode'+i).value,"nomineeTypeId":document.getElementById('nomineeTypeId').value};
			}else{
				obj= {"familyID":document.getElementById('familyID'+i).value,"nomineeName":nomineeName,"percentage":document.getElementById('percentage'+i).value,"dateOfNominate":document.getElementById('dateOfNominate').value,"nomineeTypeId":document.getElementById('nomineeTypeId').value};
			}
			if(document.getElementById('percentage'+i).value==''){
				errorMessage += "Please enter Percentage of In contengensy\n";
				document.getElementById('percentage'+i).focus();
				break;
			}
			incPercentage += parseFloat(document.getElementById('percentage'+i).value);
			incJSON += Object.toJSON(obj)+"#";
			status = true;
			
			var selFam = selectedFamily.split(',');
			for(var j=0;j<selFam.length;j++){
				if(document.getElementById('familyID'+i).value!='other' && selFam[j]==document.getElementById('familyID'+i).value){
					errorMessage += "Already family memeber exists in In contengensy\n";
					document.getElementById('familyID'+i).focus();
					break;
				}				
			}
			selectedFamily+=document.getElementById('familyID'+i).value+",";
			selFam = selectedFamily.split(',');
			for(var j=0;j<selFam.length;j++){
				if(document.getElementById('familyID'+i).value!='other' && selFam[j]==document.getElementById('familyID').value){
					errorMessage += "In contengensy member should not be the nominee\n";
					document.getElementById('familyID'+i).focus();
					break;
				}
			}
		}
	}
	
	if(parseFloat(incPercentage)!=100 && status){
		errorMessage += "In contengensy Nominee percentage should be 100%\n";
	}
	if(errorMessage==''){
		document.forms[0].nomineeID.value = nomineeID;
		document.forms[0].inconsistencyDetails.value = incJSON;
		
		if(type=='employee'){
			document.forms[0].param.value = "submitRequest";
			var url = "";
			if(nomineeID==0){
				url = 'nominee.htm';
			}else{
				url = 'nominee.htm?nomineeTypeId='+document.getElementById('nomineeTypeId').value;
			}
			nomineeID = 0;
			new Ajax.Updater('nomineeResult', url, {
				onComplete : function() {
				clearNominee();
				},
				parameters : Form.serialize(document.forms[0]),
				asynchronous : false,
				evalScripts : true
			});			
		}else{
			document.forms[0].param.value = "submit";
			new Ajax.Updater('nomineeResult', 'nominee.htm'+'?'+dispUrl, {
				onComplete : function() {
				clearNominee();
				},
				parameters : Form.serialize(document.forms[0]),
				asynchronous : false,
				evalScripts : true
			});
		}	
	}else{
		alert(errorMessage);
	}
}

function validateNomineeDetails(){
	var errorMessage = "";
	var status=true;
	if(document.getElementById('nomineeTypeId').value=='select'){
		errorMessage += "Please Select Nominee type\n";
		if(status){
			status=false;
			document.getElementById('nomineeTypeId').focus();
		}
	}
	if(document.getElementById('familyID').value=='select'){
		errorMessage += "Please Select family member\n";
		if(status){
			status=false;
			document.getElementById('familyID').focus();
		}
	}
	
	if(nomineeID==""){
		if(document.getElementById('familyID').value!='other' && document.getElementById('familyID').value!='select'){
			for(var i=0;i<familyMemberInNominee.length;i++){
				if((familyMemberInNominee[i].key==document.getElementById('nomineeTypeId').value) && (familyMemberInNominee[i].name==document.getElementById('familyID').value) && familyMemberInNominee[i].id==0){
					errorMessage += "Already Member is having some percentage in this nominee type\n";
					if(status){
						status=false;
						document.getElementById('familyID').focus();
					}
				}				
			}			
		}
		if(document.getElementById('nomineeTypeId').value!='select'){
			// check the percentage available
			if(percentageList[document.getElementById('nomineeTypeId').value]!=null){
				if((parseFloat(percentageList[document.getElementById('nomineeTypeId').value])+parseFloat(document.getElementById('percentage').value))>100){
					var rem = 100-parseFloat(percentageList[document.getElementById('nomineeTypeId').value]);
					if(rem==0){
						errorMessage += "No percentage is available in this nominee type\n";
					}else{
					errorMessage += "Only "+rem+" percentage is available in this nominee type\n";
					}
					if(status){
						status=false;
						document.getElementById('nomineeTypeId').focus();
					}
				}			
			}
		}
	}else if(funtype=='submit'){
		// edit
		// search family member name in the list
		// check the percentage
		if(document.getElementById('familyID').value!='other' && document.getElementById('familyID').value!='select'){
			for(var i=0;i<familyMemberInNominee.length;i++){
				if((familyMemberInNominee[i].key==document.getElementById('nomineeTypeId').value) && (familyMemberInNominee[i].name==document.getElementById('familyID').value) && familyMemberInNominee[i].id==0 && (gfamilyID != document.getElementById('familyID').value)){
					errorMessage += "Already Member is having some percentage in this nominee type\n";
					if(status){
						status=false;
						document.getElementById('familyID').focus();
					}
				}				
			}				
		}
		if(document.getElementById('nomineeTypeId').value!='select'){
			if(percentageList[document.getElementById('nomineeTypeId').value]!=null){
				var val = parseFloat(percentageList[document.getElementById('nomineeTypeId').value])-parseFloat(percentage);
				if((val+parseFloat(document.getElementById('percentage').value))>100){
					var rem = 100-val;
					if(rem==0){
						errorMessage += "No percentage is available in this nominee type\n";
					}else{
					errorMessage += "Only "+rem+" percentage is available in this nominee type\n";
					}
					if(status){
						status=false;
						document.getElementById('nomineeTypeId').focus();
					}
				}				
			}			
		}		
	}
	if(status){
		if(document.getElementById('nomineeTypeId').value=='select'){
			errorMessage += "Please Select Nominee Type\n";
			if(status){
				status=false;
				document.getElementById('nomineeTypeId').focus();
			}
			
		}
		if(document.getElementById('familyID').value=='select'){
			errorMessage += "Please Select Family Member\n";
			if(status){
				status=false;
				document.getElementById('familyID').focus();
			}
		}else if(document.getElementById('familyID').value=='other'){
			if(document.getElementById('nomineeName').value==''){
				errorMessage += "Please Enter Nominee\n";
				if(status){
					status=false;
					document.getElementById('nomineeName').focus();
				}
			}				
		}else{
			document.forms[0].nomineeName.value=document.getElementById('familyID').options[document.getElementById('familyID').selectedIndex].text;
		}
		if(document.getElementById("familyID").value=="other"){
			if (document.forms[0].address1.value == "") {
				errorMessage += "Please Enter Address Line1\n";
				if (status) {
					document.getElementById('address1').focus();
					status = false;
				}
			}
				if (document.forms[0].city.value == "") {
					errorMessage += "Please Enter City \n";
					if (status) {
						document.getElementById('city').focus();
						status = false;
					}
				}
				if(document.getElementById('districtId').value=='select'){
					errorMessage += "Please Select District\n";
					if(status){
						status=false;
						document.getElementById('districtId').focus();
					}
				}
				
				
	   }
		if(document.getElementById('percentage').value==''){
			errorMessage += "Please Enter Percentage\n";
			if(status){
				status=false;
				document.getElementById('percentage').focus();
			}
		}else if(parseFloat(document.getElementById('percentage').value)>100){
			errorMessage += "Percentage Should not exceed 100\n";
			if(status){
				status=false;
				document.getElementById('percentage').focus();
			}
		}
		
	}
	return errorMessage;
}

function editNominee(id){
	funtype = "edit";
	var contId="";
	deleteTableRows('incontengensy');
	for ( var i = 0; i < nomineeJSONList.length; i++) {
		if (nomineeJSONList[i].id == id) {
			contId = nomineeJSONList[i].inContengensyParent;
			if(contId==0){
				// parent
				document.getElementById("nomineeTypeId").value = nomineeJSONList[i].nomineeTypeId;
				document.getElementById("familyID").value = nomineeJSONList[i].familyID;
				gfamilyID = nomineeJSONList[i].familyID;
				if(nomineeJSONList[i].familyID==''){
					document.getElementById('other').style.display="block";
					document.getElementById("familyID").value = "other";
					document.getElementById("nomineeName").value = nomineeJSONList[i].nomineeName;
					document.getElementById("address1").value = nomineeJSONList[i].address1;
					document.getElementById("address2").value = nomineeJSONList[i].address2;
					document.getElementById("address3").value = nomineeJSONList[i].address3;
					document.getElementById("city").value = nomineeJSONList[i].city;
					document.getElementById("stateId").value = nomineeJSONList[i].stateId;
					getDistrictList('stateId','districtId');
					document.getElementById("districtId").value = nomineeJSONList[i].districtId;
					document.getElementById("pincode").value = nomineeJSONList[i].pincode;
				}else{
					document.getElementById('other').style.display="none";
				}
				document.getElementById("percentage").value = nomineeJSONList[i].percentage;
				percentage = nomineeJSONList[i].percentage;
				document.getElementById("remarks").value = nomineeJSONList[i].remarks;
				if (nomineeJSONList[i].remarks.length > 0) {
					document.getElementById('counter3').value = 500;
					document.getElementById('counter3').value -= nomineeJSONList[i].remarks.length;
				}
				document.getElementById("dateOfNominate").value = nomineeJSONList[i].dateOfNominate;
				nomineeID = id;
				for ( var j = 0; j < nomineeJSONList.length; j++) {
					// child details
					if (nomineeJSONList[j].inContengensyParent == nomineeID) {
						nomineeInconsistency();						
						assignNomineeDetails(j);
					}
				}
				
			}else{
				// child
				
				for ( var j = 0; j < nomineeJSONList.length; j++) {
					if (nomineeJSONList[j].id == contId) {
						// parent details
						document.getElementById("nomineeTypeId").value = nomineeJSONList[j].nomineeTypeId;
						document.getElementById("familyID").value = nomineeJSONList[j].familyID;
						gfamilyID = nomineeJSONList[j].familyID;
						if(nomineeJSONList[j].familyID==''){
							document.getElementById('other').style.display="block";
							document.getElementById("familyID").value = "other";
							document.getElementById("nomineeName").value = nomineeJSONList[j].nomineeName;
							document.getElementById("address1").value = nomineeJSONList[j].address1;
							document.getElementById("address2").value = nomineeJSONList[j].address2;
							document.getElementById("address3").value = nomineeJSONList[j].address3;
							document.getElementById("city").value = nomineeJSONList[j].city;
							document.getElementById("stateId").value = nomineeJSONList[j].stateId;
							getDistrictList('stateId','districtId');
							document.getElementById("districtId").value = nomineeJSONList[j].districtId;
							document.getElementById("pincode").value = nomineeJSONList[j].pincode;
						}else{
							document.getElementById('other').style.display="none";
						}
						document.getElementById("percentage").value = nomineeJSONList[j].percentage;
						percentage = nomineeJSONList[j].percentage;
						document.getElementById("remarks").value = nomineeJSONList[j].remarks;
						if (nomineeJSONList[i].remarks.length > 0) {
							document.getElementById('counter3').value = 500;
							document.getElementById('counter3').value -= nomineeJSONList[i].remarks.length;
						}
						document.getElementById("dateOfNominate").value = nomineeJSONList[j].dateOfNominate;
						nomineeID = nomineeJSONList[j].id;
						
						for ( var k = 0; k < nomineeJSONList.length; k++) {
							// child details
							if (nomineeJSONList[k].inContengensyParent == nomineeID) {
								nomineeInconsistency();						
								assignNomineeDetails(k);
							}
						}
					}
					
				}
				
			}
			return;
		}
	}	
}

function assignNomineeDetails(j){
	var noOfContengensyID = noOfContengensy-1;
	document.getElementById("familyID"+noOfContengensyID).value = nomineeJSONList[j].familyID;
	document.getElementById("percentage"+noOfContengensyID).value = nomineeJSONList[j].percentage;
	if(nomineeJSONList[j].familyID==''){
		document.getElementById('add'+noOfContengensyID).style.display="block";
		document.getElementById("familyID"+noOfContengensyID).value = "other";
		document.getElementById("nomineeName"+noOfContengensyID).value = nomineeJSONList[j].nomineeName;
		document.getElementById("address1"+noOfContengensyID).value = nomineeJSONList[j].address1;
		document.getElementById("address2"+noOfContengensyID).value = nomineeJSONList[j].address2;
		document.getElementById("address3"+noOfContengensyID).value = nomineeJSONList[j].address3;
		document.getElementById("city"+noOfContengensyID).value = nomineeJSONList[j].city;
		document.getElementById("stateId"+noOfContengensyID).value = nomineeJSONList[j].stateId;
		getDistrictList('stateId'+noOfContengensyID,'districtId'+noOfContengensyID);
		document.getElementById("districtId"+noOfContengensyID).value = nomineeJSONList[j].districtId;
		document.getElementById("pincode"+noOfContengensyID).value = nomineeJSONList[j].pincode;
	}
}
function clearNominee(){
	if(document.getElementById('nomineeTypeId')!=null)
		document.getElementById("nomineeTypeId").value = "select";
	if(document.getElementById("familyID")!=null)
		document.getElementById('familyID').value = "select";
	if(document.getElementById("nomineeName")!=null){
		document.getElementById("nomineeName").value = "";		
		document.getElementById("address1").value = "";
		document.getElementById("address2").value = "";
		document.getElementById("address3").value = "";
		document.getElementById("city").value = "";
		document.getElementById("stateId").value = "select";
		document.getElementById("districtId").value = "select";
		document.getElementById("pincode").value = "";
	}
	if(document.getElementById("percentage")!=null)
		document.getElementById("percentage").value = "";
	if(document.getElementById('remarks')!=null)
		document.getElementById("remarks").value = "";
	if(document.getElementById('dateOfNominate')!=null)
		document.getElementById("dateOfNominate").value = "";
	document.getElementById("counter3").value = "500";
	if(document.getElementById("other")!=null)
		document.getElementById('other').style.display="none";
	nomineeID = "";
	noOfContengensy=0;
	deleteTableRows('incontengensy');
}

function deleteTableRows(id){
	var len = document.getElementById(id).rows.length;
	for(var i=0;i<len;i++){
		document.getElementById(id).deleteRow(0);
	}	
}
function displayAddress(){
	var familyID = document.getElementById('familyID').value;
	var nomineeType = document.getElementById("nomineeTypeId").value;
	var nomID = nomineeID;
	clearNominee();
	nomineeID = nomID;
	document.getElementById('familyID').value = familyID;
	document.getElementById("nomineeTypeId").value = nomineeType;
	if(familyID=='other'){
		document.getElementById('other').style.display="block";
	}else{
		document.getElementById('other').style.display="none";
	}
}

function deleteNominee(id,inconParent){
	if(inconParent=='0'){
		var del = confirm("Do you want to delete this item?");
		if (del == true) {
			document.forms[0].param.value = "delete";
			document.forms[0].nomineeID.value = id;
			new Ajax.Updater('nomineeResult', 'nominee.htm'+'?'+dispUrl, {
				onComplete : function() {

				},
				parameters : Form.serialize(document.forms[0]),
				asynchronous : false,
				evalScripts : true
			});
		}
	}else{
		alert("Incontengensy Percentage should be 100%");
	}		
}

function checkHeadName() {
	if(document.getElementById('divisionId').value!='Select') {
		document.forms[0].param.value = "checkHead";
		new Ajax.Updater('checkHead', 'employee.htm', {
			onComplete : function() {
			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : false,
			evalScripts : true
		});
	}
}

function ConfirmHeadOrEmp(headName) {
	if(headName!=null && headName !="") {
		
		alert("This Person Is Going To Be Employee To: "+headName)
	}
	else {
		
		alert("This department is not having head, please assign head first?");
		document.getElementById('directorateId').value='Select';
	}
}

function nomineeInconsistency(){
	var errorMessages = validateNomineeDetails();
	if(errorMessages==''){
		var dynRow = document.getElementById('incontengensy');
		var noOfRowsInc = dynRow.rows.length;
		var x=dynRow.insertRow(noOfRowsInc);
		for (var j = 0; j < 1; j++) {
			newCell = x.insertCell(j);
		}
		
		var contengensyData = "";
		contengensyData +='<fieldset>';
		contengensyData +='<table align=center width=100%>';
		contengensyData += '<tr>';
		contengensyData += '<td width=25%>Family Memeber*</td>';
		contengensyData += '<td width=25%>';
		contengensyData += '<select style=\'width: 145px;\' id=familyID'+noOfContengensy+' onchange=displayIncAddress(\'familyID'+noOfContengensy+'\',\'add'+noOfContengensy+'\');>';	
		for(var i=0;i<document.getElementById('familyID').options.length;i++)
		{
			contengensyData += '<option value='+document.getElementById('familyID').options[i].value+'>'+document.getElementById('familyID').options[i].text+'</option>';
		}
		contengensyData += '</select>';
		contengensyData += '</td>';
		contengensyData += '<td width=25%>';
		contengensyData += '</td>';
		contengensyData += '<td width=25%>';
		contengensyData += '<input type=button value=\'X\' onclick=\'javascript:deleteIncontengensy('+noOfContengensy+')\' class=deletebtn style=float:right>';
		contengensyData += '</td>';
		
		
		contengensyData += '</tr>';
		
		contengensyData += '<tr style=margin-bottom:8px>';
		contengensyData += '<td width=100% colspan=4>';
		contengensyData += displayAddressDetails('add'+noOfContengensy);
		contengensyData += '</td>';
		contengensyData += '</tr>';	
		
		contengensyData += '<tr>';
		contengensyData += '<td width=25%>Percentage*</td>';
		contengensyData += '<td width=25%>';
		contengensyData += '<input type=text id=percentage'+noOfContengensy+'>';
		contengensyData += '</td>';
		
		
		contengensyData += '<td width=25%>';
		contengensyData += '</td>';
		contengensyData += '<td width=25%>';		
		contengensyData += '</td>';
		/*
		 * contengensyData += '<td width=25%>Date of Nominate</td>';
		 * contengensyData += '<td width=25%>'; contengensyData += '<input
		 * type=text id=dateOfNominate'+noOfContengensy+' readonly=readonly>' ;
		 * var test = 'Calendar.setup({inputField :"dateOfNominate",ifFormat :
		 * "%d-%b-%Y",showsTime : false,button
		 * :"date_start_trigger4",singleClick : true,step : 1});';
		 * contengensyData += '<img src=\'./images/calendar.gif\'
		 * id=img'+noOfContengensy+' onclick='+test+'/>'; contengensyData += '</td>';
		 * 
		 * contengensyData += '<script>'; contengensyData +=
		 * 'Calendar.setup({inputField
		 * :\'dateOfNominate'+noOfContengensy+'\',ifFormat :
		 * \'%d-%b-%Y\',showsTime : false,button
		 * :\'img'+noOfContengensy+'\',singleClick : true,step : 1});';
		 * contengensyData += '</script>';
		 */
		contengensyData += '</tr>';	
		
		contengensyData += '</table>';	
		contengensyData +='</fieldset>';
		x.cells[0].innerHTML=contengensyData;
		noOfContengensy++;
	}else{
		alert(errorMessages);
	}	
}

function deleteIncontengensy(rowID){
	var dynRow = document.getElementById('incontengensy');
	var del=confirm("Do you want to delete this item?");
	if(del==true) 
	{
		dynRow.deleteRow(rowID);
	}
	
}
function displayIncAddress(pid,id){
	if(document.getElementById(pid).value=='other'){
		document.getElementById(id).style.display="block";
	}else{
		document.getElementById(id).style.display="none";
	}
}
function displayAddressDetails(id){
	var addressDetails = '<table width=100% align=center id='+id+' style=display:none>';
	
	addressDetails +='<tr>';
	addressDetails +='<td width=25%>Nominee*</td>';
	addressDetails +='<td width=25%>';
	addressDetails +='<input type=text id=nomineeName'+noOfContengensy+'>';
	addressDetails +='</td>';
	addressDetails +='<td width=25%>Address Line1</td>';
	addressDetails +='<td width=25%>';
	addressDetails +='<input type=text id=address1'+noOfContengensy+'>';
	addressDetails +='</td>';
	addressDetails +='</tr>';
	
	addressDetails +='<tr>';
	addressDetails +='<td width=25%>Address Line2</td>';
	addressDetails +='<td width=25%>';
	addressDetails +='<input type=text id=address2'+noOfContengensy+'>';
	addressDetails +='</td>';
	addressDetails +='<td width=25%>Address Line3</td>';
	addressDetails +='<td width=25%>';
	addressDetails +='<input type=text id=address3'+noOfContengensy+'>';
	addressDetails +='</td>';
	addressDetails +='</tr>';
	
	addressDetails +='<tr>';
	addressDetails +='<td width=25%>City</td>';
	addressDetails +='<td width=25%>';
	addressDetails +='<input type=text id=city'+noOfContengensy+'>';
	addressDetails +='</td>';
	addressDetails +='<td width=25%>State</td>';
	addressDetails +='<td width=25%>';
	addressDetails += '<select id=stateId'+noOfContengensy+' onchange=getDistrictList(\'stateId'+noOfContengensy+'\',\'districtId'+noOfContengensy+'\')>';	
	for(var i=0;i<document.getElementById('stateId').options.length;i++)
	{
		addressDetails += '<option value='+document.getElementById('stateId').options[i].value+'>'+document.getElementById('stateId').options[i].text+'</option>';
	}
	addressDetails += '</select>';
	addressDetails +='</td>';
	addressDetails +='</tr>';
	
	addressDetails +='<tr>';
	addressDetails +='<td width=25%>District</td>';
	addressDetails +='<td width=25%>';
	addressDetails += '<select style=\'width: 145px;\' id=districtId'+noOfContengensy+'>';	
	addressDetails += '<option value=select>Select</value>';
	addressDetails += '</select>';
	addressDetails +='</td>';
	addressDetails +='<td width=25%>Pincode</td>';
	addressDetails +='<td width=25%>';
	addressDetails +='<input type=text id=pincode'+noOfContengensy+'>';
	addressDetails +='</td>';
	addressDetails +='</tr>';	
	
	addressDetails +='</table>';
	return addressDetails;
}
function getSearchList(type)
{
	var errormessage="";
	if(document.getElementById("empname").value!="select")
	{
		if(document.getElementById("empname").value=="SFID" && document.getElementById("searchvalue").value=="")
		{
			errormessage+="Please enter sfid";
			document.getElementById("searchvalue").focus();
		}else if(document.getElementById("empname").value=="NAME")
		{
			var flag=true;
			if(document.getElementById("searchWith").value=="select")
			{
			  errormessage+="Please Select any search option\n";
			  if(flag)
			  {
				  document.getElementById("searchWith").focus();
				  flag=false;
			  }
			}
			if(document.getElementById("searchvalue").value=="")
			{
				errormessage+="Please enter name\n";
				if(flag)
				{
					document.getElementById("searchvalue").focus();
					flag=false;
				}
			}
		}else if(document.getElementById("empname").value=="DIVISION" && document.getElementById("division").value=="select") 
		{
			errormessage+="Please Select any head name\n";
			document.getElementById("division").focus();
		}		
	} else{
		errormessage+="Please Select Search With";
		document.getElementById("empname").focus();
	}
	if(errormessage=="")
	{
		if(type=="head")				
			document.forms[0].param.value = "instanceMap";
		else
			document.forms[0].param.value = "EmpMapping";	
		if(document.getElementById("empname").value!="select" && document.getElementById("empname").value=="DIVISION")
			document.forms[0].searchvalue.value=document.getElementById("division").value;
		var divid="";

		if(type=="head")
			divid="headresult";
		else
			divid="empresult";
		new Ajax.Updater(divid, 'hierarchy.htm?searchtype=search', {
			onComplete : function() {
			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : false,
			evalScripts : true
		});
	}else
	{
		alert(errormessage);
	}
}

function displayInstances(divisionjson)
{
		var ele=document.getElementById("searchWithdiv");
		var ele1=document.getElementById("searchValues");
			
		if(document.getElementById("empname").value=="SFID")
		{					
			ele.style.display="none";
			ele1.style.display="block";	
			document.getElementById("divisions").style.display="none";
			document.getElementById("searchvalue").value="";
		}else if(document.getElementById("empname").value=="NAME")
		{
			ele.style.display="block";
			ele1.style.display="block";
			document.getElementById("divisions").style.display="none";
			document.getElementById("searchvalue").value="";
		}else if(document.getElementById("empname").value=="DIVISION")
		{
			ele.style.display="none";
			ele1.style.display="none";
			document.getElementById("divisions").style.display="block";
			document.getElementById("division").value="select";
		}else
		{
			ele.style.display="none";
			ele1.style.display="none";
			document.getElementById("divisions").style.display="none";
			document.getElementById("searchvalue").value="";
			document.getElementById("division").value="select";
		}	
}
function selectedSearch(){
	var len = 0;
	if(document.getElementById('searchingWith').value=='name'){
		document.getElementById('selectedValueDiv').style.display="block";
		document.getElementById('enteredValueDiv').style.display="block";
		document.getElementById('enteredValue').value="";
		document.getElementById("selectedValue").options.length =1;
		len = document.getElementById("selectedValue").options.length++;
		document.getElementById("selectedValue").options[len].text = 'Starts With';
		document.getElementById("selectedValue").options[len].value = 'startWith';
		len = document.getElementById("selectedValue").options.length++;
		document.getElementById("selectedValue").options[len].text = 'Contains';
		document.getElementById("selectedValue").options[len].value = 'contains';	
		
	}else if(document.getElementById('searchingWith').value=='sfid'){
		document.getElementById('selectedValueDiv').style.display="none";
		document.getElementById('enteredValueDiv').style.display="block";	
		document.getElementById('enteredValue').value="";
	}else if(document.getElementById('searchingWith').value=='designation'){
		document.getElementById('selectedValueDiv').style.display="block";
		document.getElementById('enteredValueDiv').style.display="none";
		document.getElementById("selectedValue").options.length = 1;
		
		if (designationListJSON != null) {
			document.getElementById("selectedValue").options.length = 1;
				for ( var i = 0; i < designationListJSON.length; i++) {
					len = document.getElementById("selectedValue").options.length++;
					document.getElementById("selectedValue").options[len].text = designationListJSON[i].name;
					document.getElementById("selectedValue").options[len].value = designationListJSON[i].id;
				}

		}

	}else if(document.getElementById('searchingWith').value=='instance'){
		document.getElementById('selectedValueDiv').style.display="block";
		document.getElementById('enteredValueDiv').style.display="none";
		
		if (instanceListJSON != null) {
			document.getElementById("selectedValue").options.length = 1;
				for ( var i = 0; i < instanceListJSON.length; i++) {
					len = document.getElementById("selectedValue").options.length++;
					document.getElementById("selectedValue").options[len].text = instanceListJSON[i].name;
					document.getElementById("selectedValue").options[len].value = instanceListJSON[i].id;
				}
		}
	}else if(document.getElementById('searchingWith').value=='approle'){
		document.getElementById('selectedValueDiv').style.display="block";
		document.getElementById('enteredValueDiv').style.display="none";
		if (applicationRolesListJSON != null) {
			document.getElementById("selectedValue").options.length = 1;
				for ( var i = 0; i < applicationRolesListJSON.length; i++) {
					len = document.getElementById("selectedValue").options.length++;
					document.getElementById("selectedValue").options[len].text = applicationRolesListJSON[i].name;
					document.getElementById("selectedValue").options[len].value = applicationRolesListJSON[i].id;
				}
		}
	}	
}

function searchingAppRoles(){
	var errorMessages = "";
	var status=true;
	if(document.getElementById('searchingWith').value=="select"){
		errorMessages = "Please Select search with\n";
		document.getElementById('searchingWith').focus();
		status=false;
	}
	else{
		if(document.getElementById('selectedValue').value=="select" && document.getElementById('searchingWith').value!="sfid"){
			errorMessages = "Please Select searching value\n";
			if(status){
				document.getElementById('selectedValue').focus();
				status=false;
			}
		}
		if(document.getElementById('searchingWith').value=="name" && document.getElementById('enteredValue').value==""){
			errorMessages = "Please Enter name for searching\n";
			if(status){
				document.getElementById('enteredValue').focus();
				status=false;
			}
			
		}
		if(document.getElementById('searchingWith').value=="sfid" && document.getElementById('enteredValue').value==""){
			errorMessages = "Please Enter SFID for searching\n";
			if(status){
				document.getElementById('enteredValue').focus();
				status=false;
			}
			
		}
	}
	if(status){
		document.forms[0].param.value="search";
		new Ajax.Updater('result', 'approles.htm', {
			onComplete : function() {
			resetAppMap();
			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : false,
			evalScripts : true
		});
	}else{
		alert(errorMessages);
	}
}

function assigningRole(){
	var checkedValues = "";
	var errorMessages = "";
	if(document.getElementById('mapSfid').value=="select"){
		errorMessages = "Please Select Employee SFID or Name for changing application role\n";
		document.getElementById('mapSfid').focus();
	}

	for ( var i = 0; i < document.getElementById('assigningRole').options.length; i++) {
		if (document.getElementById('assigningRole').options[i].selected == true) {
			checkedValues += document.getElementById('assigningRole').options[i].value
					+ ",";
			status = true;
		}
	}
	
	
	if(checkedValues==''){
		errorMessages += "Please Select Assign Application roles\n";
	}
	if(errorMessages==''){
		document.forms[0].checkedValues.value=checkedValues;
		document.forms[0].param.value="submit";
		new Ajax.Updater('result', 'approles.htm', {
			onComplete : function() {
			resetAppMap();
			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : false,
			evalScripts : true
		});
	}else{
		alert(errorMessages);
	}	
}
function resetAppMap(){
	if($jq('#sfid').is(':visible')){
		document.getElementById('sfid').value="select";
	}
	document.getElementById('selectedValueDiv').style.display="none";
	document.getElementById('enteredValueDiv').style.display="none";
	document.getElementById('searchingWith').value="select";
	if(document.getElementById('assigningRole')!=null){
		document.getElementById('mapSfid').value='select';
		document.getElementById('assigningRole').options.selectedIndex = -1;		
	}
	applicationRoleID="";
}

function editAppRole(id,appID){
	resetAppMap();
	document.getElementById('assigningRole').value=appID;
	document.getElementById('mapSfid').value=id;
	getApplicationRoles();
// resetAppMap();
// document.getElementById('assigningRole').value=appID;
// applicationRoleID = id;
}
function deleteAppRole(sfid,id){
	var del = confirm("Do you want to delete this item?");
	if (del == true) {
		document.forms[0].param.value = "delete";
		document.forms[0].id.value=id;
		document.getElementById('mapSfid').value=sfid;
		new Ajax.Updater('result', 'approles.htm'+'?'+dispUrl, {
			onComplete : function() {
			},
			parameters : Form.serialize(document.forms[0]),
			asynchronous : false,
			evalScripts : true
		});
	}
}



function saveConfiguration(type) {
	var errorMessage = "";
	var status = true;
	var configurationDetails = "";
	if(type=='pis'){
		if (document.getElementById('escalationtime').value == "") {
			errorMessage += "Please Enter Escalation Time for workflow\n";
			if (status) {
				document.getElementById('escalationtime').focus();
				status = false;
			}
		}else{
			configurationDetails +="ESCALATED_TIME,"+document.getElementById('escalationtime').value+"#";
		}
		if (document.getElementById('admin').value == 'select') {
			errorMessage += "Please Select Admin \n";
			if (status) {
				document.getElementById('admin').focus();
				status = false;
			}
		} else{
			configurationDetails +="ADMIN,"+document.getElementById('admin').value+"#";
		}
		if (document.getElementById('minAgeForJob').value == '') {
			errorMessage += "Please Enter Minimum Age for govt Job\n";
			if (status) {
				document.getElementById('minAgeForJob').focus();
				status = false;
			}
		} else{
			configurationDetails +="DOB,"+document.getElementById('minAgeForJob').value+"#";
		}
		if (document.getElementById('leaveStartDate').value == '') {
			errorMessage += "Please Select Leave Management Starting Date\n";
			if (status) {
				document.getElementById('leaveStartDate').focus();
				status = false;
			}
		} else{
			configurationDetails +="LEAVE_SCRIPT_START_DATE,"+document.getElementById('leaveStartDate').value+"#";
		}if (document.getElementById('myRequestDashboardRecords').value == '') {
			errorMessage += "Please Enter MyRequest DashBoard Record Limit\n";
			if (status) {
				document.getElementById('myRequestDashboardRecords').focus();
				status = false;
			}
		} else{
			configurationDetails +="MY_REQUESTS_DASHBOARD_RECORDS,"+document.getElementById('myRequestDashboardRecords').value+"#";
		}
	}else if(type=='leave'){
		if(document.getElementById('leaveStartDate').value==''){
			errorMessage += "Please Enter Leave Start Date \n";
			if (status) {
				document.getElementById('leaveStartDate').focus();
				status = false;
			}
		}else{
			configurationDetails +="LEAVE_SCRIPT_START_DATE,"+document.getElementById('leaveStartDate').value+"#";			
		}if(document.getElementById('cmlWoMcExceptionId').value==''){
			errorMessage += "Please Enter CML WO MC Exception Type \n";
			if (status) {
				document.getElementById('cmlWoMcExceptionId').focus();
				status = false;
			}
		}else{
			configurationDetails +="CML_WO_MC_EXCEPTIONID,"+document.getElementById('cmlWoMcExceptionId').value+"#";			
		}if(document.getElementById('lndWoMcExceptionId').value==''){
			errorMessage += "Please Enter LND WO MC Exception Type \n";
			if (status) {
				document.getElementById('lndWoMcExceptionId').focus();
				status = false;
			}
		}else{
			configurationDetails +="LND_WO_MC_EXCEPTIONID,"+document.getElementById('lndWoMcExceptionId').value+"#";			
		}
	}else if(type=='cghs'){
		if(document.getElementById('cghsMajorAgeLimit').value==''){
			errorMessage += "Please Enter CGHS Major Age Limit \n";
			if (status) {
				document.getElementById('cghsMajorAgeLimit').focus();
				status = false;
			}
		}else{
			configurationDetails +="CGHS_MAJOR_AGE_LIMIT,"+document.getElementById('cghsMajorAgeLimit').value+"#";			
		}
		if(document.getElementById('cghsSonAgeLimit').value==''){
			errorMessage += "Please Enter CGHS Son Age Limit \n";
			if (status) {
				document.getElementById('cghsSonAgeLimit').focus();
				status = false;
			}
		}else{
			configurationDetails +="CGHS_SON_AGE_LIMIT,"+document.getElementById('cghsSonAgeLimit').value+"#";			
		}
		if(document.getElementById('cghsSalaryLimit').value==''){
			errorMessage += "Please Enter CGHS Salary Limit \n";
			if (status) {
				document.getElementById('cghsSalaryLimit').focus();
				status = false;
			}
		}else{
			configurationDetails +="CGHS_SALARY_LIMIT,"+document.getElementById('cghsSalaryLimit').value+"#";			
		}if(document.getElementById('cghsAdvApprPerc').value==''){
			errorMessage += "Please Enter Advance Appr. Perc. \n";
			if (status) {
				document.getElementById('cghsAdvApprPerc').focus();
				status = false;
			}
		}else{
			configurationDetails +="CGHS_ADVANCE_APPROVED_PERCENTAGE,"+document.getElementById('cghsAdvApprPerc').value+"#";			
		}
	}else if(type=='tada'){     //This condition for tada cfg details
		if(document.getElementById('tadaTimeBoundPast').value=='' || document.getElementById('tadaTimeBoundFuture').value==''){
			errorMessage += "Please Enter TADA Request TimeBound Of Past and Future \n";
			if (status) {
				document.getElementById('tadaTimeBoundPast').focus();
				status = false;
			}
		
	}else{
		configurationDetails +="TADA_REQUEST_TIMEBOUND_PAST,"+document.getElementById('tadaTimeBoundPast').value+"#";
	    configurationDetails +="TADA_REQUEST_TIMEBOUND_FUTURE,"+document.getElementById('tadaTimeBoundFuture').value+"#";		
	}
	}else if(type=='ltc'){
		if(document.getElementById('ltcPriorDob').value==''){
			errorMessage += "Please Enter LTC Prior Date \n";
			if (status) {
				document.getElementById('ltcPriorDob').focus();
				status = false;
			}
		}else{
			configurationDetails +="LTC_PRIOR_DOB,"+document.getElementById('ltcPriorDob').value+"#";			
		}if(document.getElementById('ltcJuniorWorkExp').value==''){
			errorMessage += "Please LTC Junior Work Exp \n";
			if (status) {
				document.getElementById('ltcJuniorWorkExp').focus();
				status = false;
			}
		}else{
			configurationDetails +="LTC_JUNIOR_WORK_EXP,"+document.getElementById('ltcJuniorWorkExp').value+"#";			
		}if(document.getElementById('ltcSeniorrWorkExp').value==''){
			errorMessage += "Please Enter LTC Senior Work Exp \n";
			if (status) {
				document.getElementById('ltcSeniorrWorkExp').focus();
				status = false;
			}
		}else{
			configurationDetails +="LTC_SENIOR_WORK_EXP,"+document.getElementById('ltcSeniorrWorkExp').value+"#";			
		}if(document.getElementById('ltcSonAgeLimit').value==''){
			errorMessage += "Please Enter LTC Son Age Limit \n";
			if (status) {
				document.getElementById('ltcSonAgeLimit').focus();
				status = false;
			}
		}else{
			configurationDetails +="LTC_SON_AGE_LIMIT,"+document.getElementById('ltcSonAgeLimit').value+"#";			
		}
		
		if(document.getElementById('ltcAdvancePercentage').value==''){
			errorMessage += "Please Enter LTC Advance Percentage \n";
			if (status) {
				document.getElementById('ltcAdvancePercentage').focus();
				status = false;
			}
		}else{
			configurationDetails +="LTC_ADVANCE_APPROVED_PERCENTAGE,"+document.getElementById('ltcAdvancePercentage').value+"#";			
		}if(document.getElementById('ltcMajorAgeLimit').value==''){
			errorMessage += "Please Enter LTC Major Age Limit \n";
			if (status) {
				document.getElementById('ltcMajorAgeLimit').focus();
				status = false;
			}
		}else{
			configurationDetails +="LTC_MAJOR_AGE_LIMIT,"+document.getElementById('ltcMajorAgeLimit').value+"#";			
		}if(document.getElementById('ltcAdvancePriorDays').value==''){
			errorMessage += "Please Enter LTC Advance Prior Days \n";
			if (status) {
				document.getElementById('ltcAdvancePriorDays').focus();
				status = false;
			}
		}else{
			configurationDetails +="LTC_PRIOR_ADVANCE_REQUEST_DAYS,"+document.getElementById('ltcAdvancePriorDays').value+"#";			
		}if(document.getElementById('ltcDaPercengate').value==''){
			errorMessage += "Please Enter LTC DA Percentage \n";
			if (status) {
				document.getElementById('ltcDaPercengate').focus();
				status = false;
			}
		}else{
			configurationDetails +="LTC_DA_PERCENTAGE,"+document.getElementById('ltcDaPercengate').value+"#";			
		}
	}else if (type='pay')
	{
		if(document.getElementById('specialAllowance').value==''){
			errorMessage += "Please Enter Special allowance for Scientist G. \n";
			if (status) {
				document.getElementById('specialAllowance').focus();
				status = false;
			}
		}else{
			configurationDetails +="SCG_SP_ALLOWANCE,"+document.getElementById('specialAllowance').value+"#";			
		}
		if(document.getElementById('washAllowance').value==''){
			errorMessage += "Please Enter wash allowance.  \n";
			if (status) {
				document.getElementById('washAllowance').focus();
				status = false;
			}
		}else{
			configurationDetails +="WASH_ALLOWANCE,"+document.getElementById('washAllowance').value+"#";			
		}
		if(document.getElementById('xeroxAllowance').value==''){
			errorMessage += "Please Enter Xerox allowance. \n";
			if (status) {
				document.getElementById('xeroxAllowance').focus();
				status = false;
			}
		}else{
			configurationDetails +="XEROX_ALLOWANCE,"+document.getElementById('xeroxAllowance').value+"#";			
		}
		if(document.getElementById('wfundOfficers').value==''){
			errorMessage += "Please Enter for welfare fund for officer's.\n";
			if (status) {
				document.getElementById('wfundOfficers').focus();
				status = false;
			}
		}else{
			configurationDetails +="WFUND_OFFICERS,"+document.getElementById('wfundOfficers').value+"#";			
		}
		if(document.getElementById('wfundStaff').value==''){
			errorMessage += "Please Enter welfare fund for staff. \n";
			if (status) {
				document.getElementById('wfundStaff').focus();
				status = false;
			}
		}else{
			configurationDetails +="WFUND_STAFF,"+document.getElementById('wfundStaff').value+"#";			
		}
		if(document.getElementById('messOfficers').value==''){
			errorMessage += "Please Enter Officers mess charges for in Orts. \n";
			if (status) {
				document.getElementById('messOfficers').focus();
				status = false;
			}
		}else{
			configurationDetails +="MESS_OFFICERS,"+document.getElementById('messOfficers').value+"#";			
		}
		if(document.getElementById('messOutside').value==''){
			errorMessage += "Please Enter Officers mess charges for outside. \n";
			if (status) {
				document.getElementById('messOutside').focus();
				status = false;
			}
		}else{
			configurationDetails +="MESS_OUTSIDE,"+document.getElementById('messOutside').value+"#";			
		}
		if(document.getElementById('bfundOfficers').value==''){
			errorMessage += "Please Enter benevolent fund for officer's. \n";
			if (status) {
				document.getElementById('bfundOfficers').focus();
				status = false;
			}
		}else{
			configurationDetails +="BFUND_OFFICERS,"+document.getElementById('bfundOfficers').value+"#";			
		}
		if(document.getElementById('bfundStaff').value==''){
			errorMessage += "Please Enter benevolent fund for staff. \n";
			if (status) {
				document.getElementById('bfundStaff').focus();
				status = false;
			}
		}else{
			configurationDetails +="BFUND_STAFF,"+document.getElementById('bfundStaff').value+"#";			
		}
		if(document.getElementById('regFundOfficers').value==''){
			errorMessage += "Please Enter regimental fund for officer's. \n";
			if (status) {
				document.getElementById('regFundOfficers').focus();
				status = false;
			}
		}else{
			configurationDetails +="REGFUND_OFFICERS,"+document.getElementById('regFundOfficers').value+"#";			
		}
		if(document.getElementById('regFundStaff').value==''){
			errorMessage += "Please Enter regimental fund for staff. \n";
			if (status) {
				document.getElementById('regFundStaff').focus();
				status = false;
			}
		}else{
			configurationDetails +="REGFUND_STAFF,"+document.getElementById('regFundStaff').value+"#";			
		}
		if(document.getElementById('water').value==''){
			errorMessage += "Please Enter value for water. \n";
			if (status) {
				document.getElementById('water').focus();
				status = false;
			}
		}else{
			configurationDetails +="WATER,"+document.getElementById('water').value+"#";			
		}
		if(document.getElementById('furniture').value==''){
			errorMessage += "Please Enter value for furniture. \n";
			if (status) {
				document.getElementById('furniture').focus();
				status = false;
			}
		}else{
			configurationDetails +="FURNITURE,"+document.getElementById('furniture').value+"#";			
		}
	}
	if (status) {
		document.forms[0].param.value = "save";
		document.forms[0].configurationDetails.value = configurationDetails;
		
		
		$jq.post('configuration.htm', $jq('#configuration').serialize(),
				function(html) {
				$jq("#result").html(html);
				});

	} else {
		alert(errorMessage);
	}

}
function clearMessage()
{
	$jq('#result').html('');
}

function clearDateText(id) {
	document.getElementById(id).value="";

}
function getSpecificEmpTree() {
	if(document.getElementById('sfid').value!='select') {
		new Ajax.Updater('empSearchList', 'empSearch.htm?param=tree&sfid='+document.getElementById('sfid').value, {
			onComplete : function() {
			},
			asynchronous : false,
			evalScripts : true
		});
	}
}
function searchingTree(type){
	var errorMessages = "";
	var status=true;
	if(document.getElementById('searchingWith').value=="select"){
		errorMessages = "Please Select search with\n";
		document.getElementById('searchingWith').focus();
		status=false;
	}
	else{
		if(document.getElementById('selectedValue').value=="select" && document.getElementById('searchingWith').value!="sfid"){
			errorMessages = "Please Select searching value\n";
			if(status){
				document.getElementById('selectedValue').focus();
				status=false;
			}
		}
		if(document.getElementById('searchingWith').value=="name" && document.getElementById('enteredValue').value==""){
			errorMessages = "Please Enter name for searching\n";
			if(status){
				document.getElementById('enteredValue').focus();
				status=false;
			}
			
		}
		if(document.getElementById('searchingWith').value=="sfid" && document.getElementById('enteredValue').value==""){
			errorMessages = "Please Enter SFID for searching\n";
			if(status){
				document.getElementById('enteredValue').focus();
				status=false;
			}
			
		}
	}
	if(status){
		document.forms[0].param.value = "empTreeSearch";
		var instanceId=document.getElementById("selectedValue").value;
		if(type!=null && type!=undefined) {
			window.open('empSearch.htm?param=empTreeSearch&type='+type+'&instanceId='+instanceId,'preview','fullscreen=yes,toolbar=yes,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else {
			new Ajax.Updater('empSearchList', 'empSearch.htm', {
				onComplete : function() {
				},
				parameters : Form.serialize(document.forms[0]),
				asynchronous : true,
				evalScripts : true
			});
		}
	}else{
		alert(errorMessages);
	}	
}




function getApplicationRoles() {
	if (applicationRolesMapJSON != null) {
		document.getElementById('assigningRole').options.selectedIndex = -1;
		for ( var i = 0; i < applicationRolesMapJSON.length; i++) {
			if(applicationRolesMapJSON[i].sfid == document.getElementById('mapSfid').value){
				for ( var j = 0; j < document.getElementById('assigningRole').options.length; j++) {
					if(applicationRolesMapJSON[i].applicationRoleID==document.getElementById('assigningRole').options[j].value){
						document.getElementById('assigningRole').options[j].selected = true;
					}
				}
			}			
		}		
	}
}

function FatherOrSpouse() {
	
	if(document.getElementById('relationId').value=='25') {
		document.getElementById('name').innerHTML="Father Name";
		document.getElementById('relationName').disabled = false;
	}else if(document.getElementById('relationId').value=='26') {
		document.getElementById('name').innerHTML="Spouse Name";
		document.getElementById('relationName').disabled = false;
	}else if(document.getElementById('relationId').value=='0') {
		document.getElementById('name').innerHTML="Name";
		document.getElementById('relationName').disabled = true;
	}
	
}

function getTreeEmpDetails(sfid,name,empSearchId) {
		document.forms[0].param.value = "treeEmpDetails";
		document.forms[0].id.value=sfid;
		document.forms[0].type.value=name;
		if(empSearchId=='yes')
			document.forms[0].empSearchId.value=empSearchId;
		document.forms[0].action = "empSearch.htm";
		document.forms[0].submit();
}
function isInternetExplorer() {
	var element = null;
	var type="input";
	var name="name";
	var value=true;
	try {
		
		element = document.createElement('<' + type + ' name="' + name + '">');
	} catch (e) {
	}
	if (!element || element.nodeName != type.toUpperCase()) {

		value=false;
	}
	return value;
}

function setOrganizationalDetails(){
	if (document.getElementById("department").value != "select") {
		if(subOrdinatesList!=null){
			document.getElementById("mapSFID").options.length = 1;
			for ( var i = 0; i < subOrdinatesList.length; i++) {
				if (subOrdinatesList[i].id == document.getElementById("department").value) {
					var len = document.getElementById("mapSFID").options.length++;
					document.getElementById("mapSFID").options[len].text = subOrdinatesList[i].name;
					document.getElementById("mapSFID").options[len].value = subOrdinatesList[i].key;					
				}				
			}			
		}
		
		if(internalDivisionsList!=null){
			document.getElementById("internalDivisions").options.length = 2;
			for ( var i = 0; i < internalDivisionsList.length; i++) {
				if (internalDivisionsList[i].id == document.getElementById("department").value) {
					var len = document.getElementById("internalDivisions").options.length++;
					document.getElementById("internalDivisions").options[len].text = internalDivisionsList[i].name;
					document.getElementById("internalDivisions").options[len].value = internalDivisionsList[i].key;					
				}				
			}			
		}
		
		if(internalRolesList!=null){
			document.getElementById("internalRoleName").options.length = 4;
			for ( var i = 0; i < internalRolesList.length; i++) {
				if (internalRolesList[i].id == document.getElementById("department").value) {
					var len = document.getElementById("internalRoleName").options.length++;
					document.getElementById("internalRoleName").options[len].text = internalRolesList[i].name;
					document.getElementById("internalRoleName").options[len].value = internalRolesList[i].key;					
				}				
			}			
		}		
	}	
}

// for hierarchy levels
function getLogicalName()
{
	if(document.getElementById("levelName").value!="select" && document.getElementById("nodeParentName").value!="select")
	{
		document.getElementById("nodeNames").value=document.getElementById("levelName")[document.getElementById("levelName").selectedIndex].text+"-"+document.getElementById("nodeParentName")[document.getElementById("nodeParentName").selectedIndex].text;
	}
}


function printBiodata() {
	testwin=window.open('',	'preview','fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	testwin.document.title = 'Employee Details';
	$jq('#newbutton').css("display","none");
	testwin.document.body.innerHTML=document.getElementById("empbiodataPrintView").innerHTML;
	testwin.print();
	testwin.close();
	$jq('#newbutton').css("display","block");
}
function printTree() {
	$jq.jPrintArea('#empSearchTable');
}

function checkWorkflowType(){
	if($jq('#workflowType').val()=='3'){
		// for designation based workflow
		$jq('#desigsDiv').show();
		$jq('#rolesDiv').hide();
		$jq('#orgDiv').hide();
	}else if(document.getElementById('workflowType').value=='2'){
		// role based workflow
		document.getElementById('rolesDiv').style.display="block";
		$jq('#desigsDiv').hide();
		$jq('#orgDiv').hide();
	}else if($jq('#workflowType').val()=='4'){
		// for organization specific workflow
		$jq('#orgDiv').show();
		$jq('#desigsDiv').hide();
		$jq('#rolesDiv').hide();
	}else{
		document.getElementById('rolesDiv').style.display="none";
		$jq('#desigsDiv').hide();
		$jq('#orgDiv').hide();
	}
}
function setParentList(parentJSONList)
{
	// document.getElementById("parent").options.text = 'Select'
	document.getElementById('parent').options.length = 1;
	var selectobj=document.getElementById('parent');
	for(var i=0;i<parentJSONList.length;i++) {
		var theOption = document.createElement("OPTION");
		if(type=='department'){
			var theText = document.createTextNode(parentJSONList[i].hierarchyName);
			theOption.appendChild(theText);
			theOption.setAttribute("value", parentJSONList[i].id);
		}else{
			var theText = document.createTextNode(parentJSONList[i].roleName);
			theOption.appendChild(theText);
			theOption.setAttribute("value", parentJSONList[i].roleID);
		}			
		selectobj.appendChild(theOption);
	}
}

function otherEmployeeDetails() {
	var errorMessage = "";
	var flag = true;
	var dob=document.getElementById('dob').value
	var check;
	var dateCheck;
	
	/*
	 * if
	 * (!validateNotEmpty(document.getElementById('nameInServiceBook').value)) {
	 * errorMessage += "Please Enter Name In Service Book \n"; if (flag) {
	 * document.getElementById('nameInServiceBook').focus(); flag = false; } }
	 * if (!document.getElementById('nameInServiceBook').value == '') {
	 * check=document.getElementById('nameInServiceBook').value;
	 * if(validateChar(check)) { errorMessage += "Name In Service Book Takes
	 * Only Character \n"; document.getElementById('nameInServiceBook').focus();
	 * flag = false; } }
	 */
	
	
	
	
	
	//alert($jq('#userSfid1').val());
	
	if($jq('#userSfid').val()==''){
		errorMessage += "Please Enter SFID.\n";
		if (flag) {
		$jq('#userSfid').focus();
		flag = false;
		}
	}
	if($jq('#nameInServiceBook').val()==''){
		errorMessage += "Please Enter Full Name.\n";
		if (flag) {
		$jq('#nameInServiceBook').focus();
		flag = false;
		}
	}
	if (document.getElementById('title').value == 'Select') {
		errorMessage += "Please Select Title \n";
		if (flag) {
			document.getElementById('title').focus();
			flag = false;
		}
	}
	if (!validateNotEmpty(document.getElementById('firstName').value)) {
		errorMessage += "Please Enter FirstName \n";
		if (flag) {
			document.getElementById('firstName').focus();
			flag = false;
		}
	}else if (!document.getElementById('firstName').value == '') {
		check=document.getElementById('firstName').value;
		if(validateChar(check)) {
			errorMessage += "First Name Takes Only Character \n";
			if (flag) {
			document.getElementById('firstName').focus();
			flag = false;
			}
		}
	}
	if (!validateNotEmpty(document.getElementById('lastName').value)) {
		errorMessage += "Please Enter LastName \n";
		if (flag) {
			document.getElementById('lastName').focus();
			flag = false;
		}
	}
	//commented by bkr 29/03/2016
/*	if (!validateNotEmpty(document.getElementById('pranNo').value)) {
		errorMessage += "Please Pran No \n";
		if (flag) {
			document.getElementById('pranNo').focus();
			flag = false;
		}
	}*/
	
	
/*
 * else if (!document.getElementById('lastName').value == '') {
 * check=document.getElementById('lastName').value; if(validateChar(check)) {
 * errorMessage += "Last Name Takes Only Character \n"; if (flag) {
 * document.getElementById('lastName').focus(); flag = false; } } }
 */
	if (!document.getElementById('middleName').value == '') {
		check=document.getElementById('middleName').value;
		if(validateChar(check)) {
			errorMessage += "Middle Name Takes Only Character \n";
			if (flag) {
			document.getElementById('middleName').focus();
			flag = false;
			}
		}
	}
	//commented by bkr 29/03/2016
	/*if (!validateNotEmpty(document.getElementById('dojGovt').value)) {
		errorMessage += "Please Enter Date of Join in GOVT \n";
		if (flag) {
			document.getElementById('dojGovt').focus();
			flag = false;
		}
	}*/
	//commented by bkr 29/03/2016
	/*if (!validateNotEmpty(document.getElementById('lastPromotion').value)) {
		errorMessage += "Please Enter Last Promotion Date \n";
		if (flag) {
			document.getElementById('lastPromotion').focus();
			flag = false;
		}
	}*/
	if (!validateNotEmpty(document.getElementById('dojAsl').value)) {
		errorMessage += "Please Enter Date of Join in ASL \n";
		if (flag) {
			document.getElementById('dojAsl').focus();
			flag = false;
		}
	}
	//commented by bkr 29/03/2016
	/*if (!validateNotEmpty(document.getElementById('dojDrdo').value)) {
		errorMessage += "Please Enter Date of Join in DRDO \n";
		if (flag) {
			document.getElementById('dojDrdo').focus();
			flag = false;
		}
	}*/
	if (!validateNotEmpty(document.getElementById('dob').value)) {
		errorMessage += "Please Enter Date of Birth \n";
		if (flag) {
			document.getElementById('dob').focus();
			flag = false;
		}
	}else {
		if(compareDate(dob,checkDate)) {
		
			errorMessage += "Date of Birth Not Valid \n";
			if (flag) {
				document.getElementById('dob').focus();
				flag = false;
			}
		}
	}
	if (document.getElementById('female').checked == false
			&& document.getElementById('male').checked == false) {
		errorMessage += "Please Select Gender \n";
		if (flag) {
			document.getElementById('male').focus();
			flag = false;
		}
	}
	if (document.getElementById('designationId').value == 'Select') {
		errorMessage += "Please Select Designation \n";
		if (flag) {
			document.getElementById('designationId').focus();
			flag = false;
		}
	}
	if (document.getElementById('directorateId').value == 'Select') {
		errorMessage += "Please Select Directorate \n";
		if (flag) {
			document.getElementById('directorateId').focus();
			flag = false;
		}
	}
	//commented by bkr 29/03/2016
/*	if (!document.getElementById('dojDrdo').value == '') {
		if(document.getElementById('dojDrdo').value== '') {
			errorMessage += "Date of Join In DRDO Is Invalid \n";
			if (flag) {
				document.getElementById('dojDrdo').focus();
				flag = false;
			}
		}
	}*/
	if (document.getElementById('dojAsl') != null && !document.getElementById('dojAsl').value == '') {
		if(document.getElementById('dojAsl').value== '') {
			errorMessage += "Date of Join In Asl Is Invalid \n";
			if (flag) {
				document.getElementById('dojAsl').focus();
				flag = false;
			}
		}
	}
	if (document.getElementById('nationalityId').value == 'Select') {
		errorMessage += "Please Select Nationality \n";
		if (flag) {
			document.getElementById('nationalityId').focus();
			flag = false;
		}
	}
	//commented by bkr 29/03/2016
	/*if (document.getElementById('workingLocation').value == 'Select') {
		errorMessage += "Please Select Working Organisation\n";
		if (flag) {
			document.getElementById('workingLocation').focus();
			flag = false;
		}
	}*/
	if (!document.getElementById('residenceNo').value == '') {
		check=document.getElementById('residenceNo').value;
		if(!validateInteger(check)) {
			errorMessage += "Residency Number Takes Only Number \n";
			document.getElementById('residenceNo').focus();
			flag = false;
		}
	}
	if (!document.getElementById('personalNumber').value == '') {
		check=document.getElementById('personalNumber').value;
		if(!validateInteger(check)) {
			errorMessage += "Mobile Number Takes Only Number \n";
			document.getElementById('personalNumber').focus();
			flag = false;
		}
	}
	if (!document.getElementById('internalNo').value == '') {
		check=document.getElementById('internalNo').value;
		if(!validateInteger(check)) {
			errorMessage += "Internal Number Takes Only Number\n";
			document.getElementById('internalNo').focus();
			flag = false;
		}
	}
	if($jq('#personalNumber').val()==''){
		errorMessage += "Please Enter Personal Number.\n";
		if (flag) {
		$jq('#personalNumber').focus();
		flag = false;
		}
	}
	//commented by bkr 29/03/2016
	/*if($jq('#internalNo').val()==''){
		errorMessage += "Please Enter Internal Number.\n";
		$jq('#internalNo').focus();
		flag = false;
	}*/
	//commented by bkr 30/03/2016
	/*if($jq('#gpfAcNo').val()==''){
		errorMessage += "Please Enter GPF Number.\n";
		if (flag) {
		$jq('#gpfAcNo').focus();
		flag = false;
		}
	}*/
	
	
	//commented by bkr 29/03/2016
/*	if(document.getElementById('seniorityDate').value== '') {
		errorMessage += "Please Enter  SeniorityDate.\n";
		if (flag) {
			document.getElementById('seniorityDate').focus();
			flag = false;
		}
	}*/
	if (document.getElementById('employmentId').value == 'Select') {
		errorMessage += "Please Select Employement Type.\n";
		if (flag) {
			document.getElementById('employmentId').focus();
			flag = false;
		}
	}
	//commented by bkr 29/03/2016
	/*if (document.getElementById('motherTongue').value == '') {
		errorMessage += "Please Enter Mother Tongue\n";
		if (flag) {
			document.getElementById('motherTongue').focus();
			flag = false;
		}
	}*/
	if (document.getElementById('marital').value == 'Select') {
		errorMessage += "Please Select Marital Status \n";
		if (flag) {
			document.getElementById('marital').focus();
			flag = false;
		}
	}

	if (document.getElementById('community').value == 'Select') {
		/*errorMessage += "Please Select Community 111\n";
		if (flag) {
			document.getElementById('community').focus();
			flag = false;
		}*/
	}
	if (document.getElementById('religion').value == 'Select') {
		errorMessage += "Please Select religion \n";
		if (flag) {
			document.getElementById('religion').focus();
			flag = false;
		}
	}
	
	//alert(document.getElementById('religion').value);
	//commented by bkr 29/03/2016
	/*if (document.getElementById('appointmentId').value == 'Select') {
		 errorMessage += "Please Select AppointmentType \n";
		 if (flag) {
			 document.getElementById('appointmentId').focus();
			 flag = false;
		 }
	 }*/
	

	
	
	
	if (flag) {
		//added by bkr 30/03/2016
		if($jq('#gpfAcNo').val()==''){
			 var dpfacno = "1364327K";
			    $jq('#gpfAcNo').val(dpfacno);
		}
		if (document.getElementById('appointmentId').value == 'Select') {
			
			document.getElementById('appointmentId').value ='2';
		 }
			if (document.getElementById('community').value == 'Select') {
						
				document.getElementById('community').value ='1';
			 }

		if (document.getElementById('motherTongue').value == '') {
			document.getElementById('motherTongue').value="Swahili";
			//alert(document.getElementById('motherTongue').value);
		}
		
		
		
		var userId = (document.getElementById('userSfid').value);
		document.forms[0].param.value = "submit";
		document.forms[0].userSfid.value = userId;
		$jq.post('outerEmp.htm', $jq('#outside').serialize(), function(data) { 
			resetOtherEmployee();
		     $jq('#result').html(data);
		     /*if($jq('.success').is(':visible')){
			 // $jq('#submit').hide();
		     }	*/
		});
	} else {
		alert(errorMessage);
	}
}
function hideASL()
{
	document.getElementById('aslId').style.display="none";
	if(document.getElementById('workingLocation').value == "1")
	{
		document.getElementById('aslId').style.display="block";
		document.getElementById('dojAsl').value = "";
	}
}
function resetOtherEmployee(){
document.getElementById('userSfid').value = "";
document.getElementById('nameInServiceBook').value = "";
document.getElementById('title').value = "Select";
document.getElementById('firstName').value = "";
document.getElementById('middleName').value = "";
document.getElementById('lastName').value = "";
document.getElementById('bloodGroupId').value = "Select";
document.getElementById('handicap').value = "Select";
//document.getElementById('dispensaryNumberId').value = "Select"; // commented by bkr 13/03/2016
document.getElementById('dispensaryNumberId').value = "429"; // added by bkr 13/03/2016
//document.getElementById('pranNo').value = "";
document.getElementById('pranNo').value = "1234"; // added by bkr 13/03/2016
document.getElementById('gpfAcNo').value = "";
document.getElementById('male').checked = false;
document.getElementById('female').checked = false;
//document.getElementById('workingLocation').value = ""; // commented by bkr 13/03/2016
document.getElementById('workingLocation').value = "1"; // added by bkr 13/03/2016
document.getElementById('designationId').value = "Select";
document.getElementById('directorateId').value = "Select";
document.getElementById('personalNumber').value = "";
document.getElementById('residenceNo').value = "";
document.getElementById('internalNo').value = "";
document.getElementById('nationalityId').value = "Select";
//document.getElementById('dojDrdo').value = "";  // commented by bkr 13/03/2016
document.getElementById('dojDrdo').value = "13-Mar-1997";
document.getElementById('dob').value = "";
//document.getElementById('dojGovt').value = "";  // commented by bkr 13/03/2016
//document.getElementById('lastPromotion').value = ""; // commented by bkr 13/03/2016
document.getElementById('dojGovt').value = "13-Mar-1997";  // added by bkr 13/03/2016
document.getElementById('lastPromotion').value = "01-Mar-2014"; // added by bkr 13/03/2016

document.getElementById('dojAsl').value = "";
//document.getElementById('seniorityDate').value = ""; // commented by bkr 13/03/2016
document.getElementById('seniorityDate').value = "01-Mar-2014"; // added by bkr 13/03/2016
document.getElementById('employmentId').value = "Select";
document.getElementById('community').value = "Select";
document.getElementById('motherTongue').value = "";
document.getElementById('marital').value = "Select";
document.getElementById('appointmentId').value = "Select";

document.getElementById('idMarks').value = "";
	/*
	document.getElementById('nameInServiceBook').value = "";
	document.getElementById('firstName').value = "";
	document.getElementById('middleName').value = "";
	document.getElementById('lastName').value = "";
	document.getElementById('personalNumber').value = "";
	document.getElementById('residenceNo').value = "";
	document.getElementById('internalNo').value = "";
	document.getElementById('dob').value = "";
	document.getElementById('title').value = "Select";
	document.getElementById('nationalityId').value = "Select";
	document.getElementById('male').checked = false;
	document.getElementById('female').checked = false;
	document.getElementById('designationId').value = "Select";
	document.getElementById('directorateId').value = "Select";
	document.getElementById('divisionId').value = "Select";
	document.getElementById('aslId').style.display="none";
	if(document.getElementById('dojAsl') != null)
	document.getElementById('dojAsl').value = "";
	document.getElementById('dojDrdo').value = "";
	
	document.getElementById('workingLocation').value = "Select";
	document.getElementById('userSfid').value = "";
	document.getElementById('gpfAcNo').value = "";
	document.getElementById('seniorityDate').value = "";
	document.getElementById('employmentId').value = "Select";
	document.getElementById('motherTongue').value = "";
	document.getElementById('marital').value = "Select";
	document.getElementById('community').value = "Select";
	document.getElementById('appointmentId').value = "Select";
*/}

function showDiv(){
	$jq('#requestIDValue').val('');
	$jq('#selectedRequestType').val('select');
	if($jq('#reqType').val()=='RequestID'){		
		$jq('#selectedRequestTypeDiv').hide();
		$jq('#requestIDDiv').show();
		$jq('input:text').val('');
	} else if($jq('#reqType').val()=='RequestType'){
		$jq('#requestIDDiv').hide();
		$jq('#selectedRequestTypeDiv').show();			
		$jq('input:text').val('');
	} else {
		$jq('#selectedRequestTypeDiv').hide();
		$jq('#requestIDDiv').hide();
	}
}
function showAlertDiv(){
	
	$jq('#selectedAlertType').val('select');
	
	 if($jq('#alertType').val()=='alertType'){
		$jq('#selectedAlertTypeDiv').show();
	} else {
		$jq('#selectedAlertTypeDiv').hide();
		}
    }

function getMySearchList(){
	var errorMessage="";
	var status=true;
	
	if($jq('#reqType').val()=='RequestID' && $jq('#requestIDValue').val()=='') {
		errorMessage += "Please enter request id\n";
		if(status){
			$jq('#requestIDValue').focus();
			status=false;
		}		
	}
	if($jq('#reqType').val()=='RequestType' && $jq('#selectedRequestType').val()=='select') {
		errorMessage += "Please select request type";
		if(status){
			$jq('#selectedRequestType').focus();
			status=false;
		}		
	}
	
	if($jq('#fromDate').val()!='' && $jq('#toDate').val()==''){
		errorMessage += "Please select to date\n";
		if(status){
			$jq('#toDate').focus();
			status=false;
		}
		
	}
	if($jq('#fromDate').val()=='' && $jq('#toDate').val()!=''){
		errorMessage += "Please select from date\n";
		if(status){
			$jq('#fromDate').focus();
			status=false;
		}
		
	}
	
	if($jq('#fromDate').val()!='' && $jq('#toDate').val()!='' && compareDate($jq('#fromDate').val(),$jq('#toDate').val())){
		errorMessage += "Invalid dates\n";
		if(status){
			$jq('#fromDate').focus();
			status=false;
		}
	}
		if(errorMessage=="") {
		document.forms[0].param.value="search";
		$jq.post('workflowmap.htm',$jq('#dashBoard').serialize(),  
		           function(data){ 
		            	 $jq('#result').html(data);
		           } 
		      );		
	}else{
		alert(errorMessage);
	}
}
function getMyAlertList(){
	var errorMessage="";
	var status=true;
	
	
	if($jq('#alertType').val()=='alertType' && $jq('#selectedAlertType').val()=='select') {
		errorMessage += "Please select alert type";
		if(status){
			$jq('#selectedAlertType').focus();
			status=false;
		}		
	}
	
	if($jq('#fromDate').val()!='' && $jq('#toDate').val()==''){
		errorMessage += "Please select to date\n";
		if(status){
			$jq('#toDate').focus();
			status=false;
		}
		
	}
	if($jq('#fromDate').val()=='' && $jq('#toDate').val()!=''){
		errorMessage += "Please select from date\n";
		if(status){
			$jq('#fromDate').focus();
			status=false;
		}
		
	}
	
	if($jq('#fromDate').val()!='' && $jq('#toDate').val()!='' && compareDate($jq('#fromDate').val(),$jq('#toDate').val())){
		errorMessage += "Invalid dates\n";
		if(status){
			$jq('#fromDate').focus();
			status=false;
		}
	}
		if(errorMessage=="") {
		document.forms[0].param.value="searchalert";
		$jq.post('workflowmap.htm',$jq('#dashBoard').serialize(),  
		           function(data){ 
		            	 $jq('#result').html(data);
		           } 
		      );		
	}else{
		alert(errorMessage);
	}
}

function resetWorkMap(){
	$jq('#reqType').val('select');
	$jq('#requestIDValue').val('');
	$jq('#selectedRequestType').val('select');
	$jq('#selectedRequestTypeDiv').hide();
	$jq('#requestIDDiv').hide();
	$jq('#fromDate').val('');
	$jq('#toDate').val('');	
}
function resetAlertWorkMap(){
	$jq('#alertType').val('select');
	$jq('#selectedAlertType').val('select');
	$jq('#selectedAlertTypeDiv').hide();
	$jq('#fromDate').val('');
	$jq('#toDate').val('');		
}
// menu Links Master Start

function resetMenuLink() {		
	$jq('#keyName').val('');
	$jq('#linkName').val('');
	$jq('#className').val('');
	$jq('#parentId').val('select');
	menuID="";	
}
function saveMenuLink() {
	var flag = true;
	var errorMessage = "";
	var parentID = $jq('#parentId').val();
	if ($jq('#keyName').val() == '') {
		errorMessage += "Please Enter Menu key name \n";
		if (flag) {
			$jq('#keyName').focus();
			flag = false;
		}
	}
	if ($jq('#linkName').val() == '') {
		errorMessage += "Please Enter Link Name \n";
		if (flag) {
			$jq('#linkName').focus();
			flag = false;
		}
	} 
	if ($jq('#className').val() == '') {
		errorMessage += "Please Enter class Name \n";
		if (flag) {
			$jq('#className').focus();
			flag = false;
		}
	} 	
	if ($jq('#parentId').val() == 'select') {
		errorMessage +="Please Enter ParentID  \n";
// parentID = "0";
		if (flag) {
			$jq('#parentId').focus();
			flag = false;
		}
		
	} 	
	if(flag) {
		var requestDetails = {
				"keyName" : $jq.trim($jq('#keyName').val()),
				"linkName" : $jq.trim($jq('#linkName').val()),
				"className" : $jq.trim($jq('#className').val()),				
				"parentId": parentID,
				"param":"manage",
				"status" : "1",
				"pk":menuID
			};
		var url = "menu.htm?"+dispUrl;
		$jq.post(url, requestDetails, function(html) {
				$jq("#menuLinksMasterList").html(html);
				resetMenuLink();
			});
	}else {
		alert(errorMessage);
	}
}
function editMenuLinks(id,linkName,parentId,keyName,className) {
	menuID =id;
	$jq('#parentId').val(parentId);
	$jq('#linkName').val(linkName);
	$jq('#keyName').val(keyName);	
	$jq('#className').val(className);	
}

function deleteMenuLinks(id) {
	var check=confirm("Do u want to delete ?");
	if(check){
		var requestDetails = {
			"status" : "0",
			"param" : "manage",
			"pk" :id			
		};
		$jq.post('menu.htm'+'?'+dispUrl, requestDetails, function(html) {
			$jq("#menuLinksMasterList").html(html);
		});
	}
}
function respondAlert(alertMsgID,referenceID,statusID,alertTypeID){
	if(statusID==54 && $jq('#remarks').val()==''){
		alert("Please enter remarks.\n");
	} else {
		var requestDetails = {
				"param" : "submitAlert",
				"alertMsgID" : alertMsgID,
				"status" : statusID,
				"referenceID" : referenceID,
				"alertTypeID" : alertTypeID,
				"remarks" : $jq('#remarks').val()
			};		
		$jq.post('workflowmap.htm', requestDetails, function(html) {
				$jq("#result").html(html);	
				if ($jq('.success').is(':visible')) {
					$jq('#responseDiv').hide();
					$jq('#buttons1').hide();
				}
				if(statusID==0){
					pisHome();
				}
			});
	}
// end of menu Link Master
}


function getBPSFIDDetails(){
	var flag = false;
	if($jq('#SearchSfid').val() != ""){
		flag = true;
	}else{
		$jq('#SearchSfid').focus();
		flag = false;
		$jq('#empBasicPayGrid').css("display","none");
		// $jq('#result').css("display","none");
		$jq('#empDetailsMainGrid').css("display","none");
		alert("Please Enter SFID ");
	}
	if(flag){
		$jq('#empBasicPayGrid').css("display","none");
		$jq("#designationId").append($jq("<option></option>").attr("value",'').text('Select'));
		$jq('#designationId').find('option').remove().end();
		if($jq('#SearchSfid').val() != ""){
			// $jq('#param').val="basicPaySFIDDetails";
			$jq.post('empPayment.htm?param=basicPaySFIDDetails&sfid='+$jq('#SearchSfid').val().toUpperCase(),
				function(html){
				$jq("#result").html(html);	
			});
		}
	}
}
//script for Basic Pay and Grade Pay

function getBPGPSFIDDetails(){
	var flag = false;
	if($jq('#SearchSfid').val() != ""){
		flag = true;
	}else{
		$jq('#SearchSfid').focus();
		flag = false;
		$jq('#empBasicPayGrid').css("display","none");
		// $jq('#result').css("display","none");
		
		$jq('#empDetailsMainGrid').css("display","none");
		alert("Please Enter SFID ");
	}
	if(flag){
		$jq('#empBasicPayGrid').css("display","none");
		$jq('#empGradePayGrid').css("display","none");
		
		$jq("#designationId").append($jq("<option></option>").attr("value",'').text('Select'));
		$jq('#designationId').find('option').remove().end();
		if($jq('#SearchSfid').val() != ""){
			
			//$jq.post('empPayment.htm?param=basicPaySFIDDetails&sfid='+$jq('#SearchSfid').val().toUpperCase(),
					$jq.post('empPayment.htm?param=basicpaygradepay&sfid='+$jq('#SearchSfid').val().toUpperCase()+'&refSfid='+$jq('#SearchSfid').val().toUpperCase(),
							
				function(html){
				$jq("#result").html(html);	
			});
		}
	}
}




function designationList(catWiseDesigJson){
	$jq("#designationId").append($jq("<option></option>").attr("value",'').text('Select'));
	for(i=0;i<catWiseDesigJson.length;i++){
		$jq('#designationId').append($jq("<option></option>").attr("value",catWiseDesigJson[i].designationID).text(catWiseDesigJson[i].desigName));
	}
}

function payScaleList(empPayScaleJson){
	$jq("#empPayScaleId").append($jq("<option></option>").attr("value",'').text('Select'));
	for(i=0;i<empPayScaleJson.length;i++){
		$jq('#empPayScaleId').append($jq("<option></option>").attr("value",empPayScaleJson[i].id).text(empPayScaleJson[i].name));
	}
}

function manageEmpPayment()
{
	var flag = true;
	var errorMessage = "";
	if ($jq('#sfid').val() == '') {
		errorMessage += "Please Enter SFID \n";
		if (flag) {
			$jq('#sfid').focus();
			flag = false;
		}
	}
	if ($jq('#basic').val() == '') {
		errorMessage += "Please Enter Basic Pay\n";
		if (flag) {
			$jq('#basic').focus();
			flag = false;
		}
	}
	if ($jq('#designationId').val() == '' || $jq('#designationId').val() == 0) {
		errorMessage += "Please Select Designation\n";
		if (flag) {
			$jq('#designationId').focus();
			flag = false;
		}
	}
	if ($jq('#effectiveDate').val() == '') {
		errorMessage += "Please Select Effective Date\n";
		if (flag) {
			$jq('#effectiveDate').focus();
			flag = false;
		}
	}
	if ($jq('#incrementType').val() == '') {
		errorMessage += "Please Select Increment Type\n";
		if (flag) {
			$jq('#incrementType').focus();
			flag = false;
		}
	}
	if ($jq('#incrementValue').val() == '') {
		errorMessage += "Please Select Increment Value\n";
		if (flag) {
			$jq('#incrementValue').focus();
			flag = false;
		}
	}
	
	
	/*
	 * if ($jq('#effectiveDate').val() == '') { errorMessage += "Please Select
	 * Effective Date\n"; if (flag) { $jq('#effectiveDate').focus(); flag =
	 * false; } }
	 */
	if(flag) {

		var requestDetails = {
				"sfid" : $jq('#sfid').val().toUpperCase(),
				"basic" : $jq('#basic').val(),
				"effectiveDate" : $jq('#effectiveDate').val(),
				"designationId" : $jq('#designationId').val(),
				"incrementType" : $jq('#incrementType').val(),
				"incrementValue" : $jq('#incrementValue').val(),
				"status" : $jq('#status').val(),
				"referenceType" : $jq('#referenceType').val(),
				"param":"manage",
				"id":pkID
			};
			$jq.post('empPayment.htm'+'?'+dispUrl, requestDetails, function(html) {
				//$jq('#empGradePayGrid').css("display","none");
				$jq("#result11").html(html);
				clearEmpPayment();
			});
	}else {
		alert(errorMessage);
	}
}

function clearEmpPayment() {		
	//$jq('#sfid').val('');
//	$jq('#basic').val('');
	$jq('#effectiveDate').val('');
	$jq('#designationId').val('');
	$jq('#incrementType').val('');
	$jq('#incrementValue').val('');
	pkID=0;
	
	//$jq('#designationId').find('option').remove().end();
	if($jq('#designationId').find('option:selected').text()!='Select'){
	$jq("#designationId").append($jq("<option></option>").attr("value",'').text('Select'));
	}
	//$jq('#designationId').find('option').remove().end();
	$jq('#empPayScaleId').val('');
	
}

function editBasicPayHistory(id,sfid,presentEffectiveDate,basicPay,designationId,incrementType,incrementValue,status,refType){
	pkID=id;
	$jq('#sfid').val(sfid);
	$jq('#effectiveDate').val(presentEffectiveDate);
	$jq('#basic').val(basicPay);
	$jq('#incrementType').val(incrementType);
	$jq('#incrementValue').val(incrementValue);
	$jq('#status').val(status);
	$jq('#referenceType').val(refType);
	
	$jq('#designationId').find('option').remove().end();
	designationList(catWiseDesigJson);
	$jq('#designationId').val(designationId);
	changeEmpPayscale();
}

function deleteBasicPayHistory(id,sfid){
	if(id !=null || id!=""){
	var resp=confirm("Do you want to delete this Record?");
	if(resp==true){
	$jq.post(
		'empPayment.htm?param=deleteRecord&id='+id,'&sfid='+sfid,
		function(html){
		$jq('#result').html(html);	
		}
	);
	}}
}

function foodAmtDayRepresentation(requestId,sfid,roleInstanceName){
	var status = true;
	var errorMessage='';
	var daNewFoodDetailsJson={};
	var accJson={};
	var mainJson={};
	var flag=false;
	/*
	 * $jq('#daNewFoodDetailsId tr:not(:first)').each(function(){
	 * if($jq(this).find('td').eq(3).find('input').is(':visible')){
	 * if($jq(this).find('td').eq(3).find('input').val()==''){ errorMessage +=
	 * "Please Fill Amount for food aft res.\n"; status=false; } } });
	 */
	$jq('#daNewFoodDetailsId tr:not(:first)').find('td').each(function(){
		if($jq(this).find('input:text').val()=='')
			flag=true;
	});
	
	
	if(flag){
		errorMessage += "Please fill all details in food details.\n";
		status=false;
	}
	if(status){
		var i=0;
		var j =0;
			$jq('#kdiv').html('');
			$jq("#daNewFoodDetailsId").find("tr:not(:first)").each(function() {
				var valuesJson={};
				valuesJson['id'] = $jq(this).find('td').eq(0).find('input:hidden').val();
				valuesJson['fromDate'] = $jq(this).find('td').eq(0).find('input:text').val();
				valuesJson['toDate'] = $jq(this).find('td').eq(1).find('input').val();
				valuesJson['foodAmount'] = $jq(this).find('td').eq(2).find('input').val();
				valuesJson['foodAmountAftRes'] = $jq(this).find('td').eq(3).find('input').val();
				valuesJson['claimedAmount'] = $jq(this).find('td').eq(4).find('input').val();
				if($jq(this).find('td').eq(0).find('input:text').val() != undefined){
				if($jq(this).is(':visible'))
					valuesJson['editType']='add';
				else
					valuesJson['editType']='delete';
				
				daNewFoodDetailsJson[i]=valuesJson;
				i++;
				}
			});
		}
		
	mainJson["daNewFoodDetails"]=daNewFoodDetailsJson;
	mainJson["accDetails"]=accJson;
	if (status) {
		var requestDetails = {
				"requesterSfid" : sfid,
				"requestId" : requestId,
				"jsonValue" : JSON.stringify(mainJson),
				"roleInstanceName" : roleInstanceName,
				"param" : "getFoodDetails"
			};
// $jq.ajax( {
// type : "POST",
// url : "workflowmap.htm?"+dispUrl,
// data : requestDetails,
// cache : false,
// success : function(html) {
// $jq('#result6').html(html);
// if ($jq('.success').is(':visible')) {
// }
// }
// });
			$jq("#kdivold").html('');
			$jq.post('workflowmap.htm',requestDetails,function(html) {
			
				$jq("#foodDayRepID").html('');
				$jq("#result6").html(html);
				$jq("#kdiv").css("display","block");
				if ($jq('#kdivNew').is(':visible')) {
					document.getElementById("kdiv").id="kdivold";
					
				}
				if ($jq('.success').is(':visible')) {
				}
			});

	} else {
		alert(errorMessage);
	}
}
// accomodation
function accAmtDayRepresentation(requestId,sfid,roleInstanceName){
var status = true;
var errorMessage='';
var accJson={};
var mainJson={};
var flag=false;

$jq('#daNewAccDetailsId tr:not(:first)').find('td').each(function(){
	if($jq(this).find('input:text').val()=='')
		flag=true;
});

if($jq('#daNewAccDetailsId').is(':visible')){
	$jq('#daNewAccDetailsId tr:not(:first)').each(function(){
		$jq(this).find('td').each(function(){
			if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()==''){
				errorMessage += "Please fill all details in Accomodation details.\n";
				status=false;
			}
		});
	});
}

if(flag){
	errorMessage += "Please fill all details in Accomodation details.\n";
	status=false;
}
if(status){
	
		if($jq('#daNewAccDetailsId').is(':visible')){
			var i=0;		
			$jq("#daNewAccDetailsId").find("tr:not(:first)").each(function(){
				var valuesJson={};
				valuesJson['id']=$jq(this).find('td').eq(0).find('input:hidden').val();
				valuesJson['accFromDate']=$jq(this).find('td').eq(0).find('input:text').val();
				valuesJson['accToDate']=$jq(this).find('td').eq(2).find('input').val();
				valuesJson['accAmount']=$jq(this).find('td').eq(4).find('input').val();
				valuesJson['accAmountAftRes']=$jq(this).find('td').eq(5).find('input').val();
				valuesJson['claimedAmount']=$jq(this).find('td').eq(6).find('input').val();
				valuesJson['claimedAmountAftRes']=$jq(this).find('td').eq(7).find('input').val();
				if($jq(this).find('td').eq(0).find('input:text').val() != undefined){
				if($jq(this).is(':visible'))
					valuesJson['editType']='add';
				else
					valuesJson['editType']='delete';
				
				accJson[i]=valuesJson;
				i++;
				}
			});
		}
	}
	

mainJson["accDetails"]=accJson;
if (status) {
	var requestDetails = {
			"requesterSfid" : sfid,
			"requestId" : requestId,
			"jsonValue" : JSON.stringify(mainJson),
			"roleInstanceName" : roleInstanceName,
			"param" : "getAccDetails"
		};
// $jq.ajax( {
// type : "POST",
// url : "workflowmap.htm?"+dispUrl,
// data : requestDetails,
// cache : false,
// success : function(html) {
// $jq('#result6').html(html);
// if ($jq('.success').is(':visible')) {
// }
// }
// });
		$jq("#kdivold1").html('');
		$jq.post('workflowmap.htm',requestDetails,function(html) {
		
			$jq("#accDayRepID").html('');
			$jq("#result7").html(html);
			$jq("#kdiv1").css("display","block");
			if ($jq('#kdivNew1').is(':visible')) {
				document.getElementById("kdiv1").id="kdivold1";
				
			}
			if ($jq('.success').is(':visible')) {
			}
		});

} else {
	alert(errorMessage);
}

}

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
//
function getAwardCategory(jsonAwardCategoryList)
{
	$jq('#awardCatId').find('option').remove().end();
	$jq("#awardCatId").append('<option value="">Select</option>');
	var parentID = $jq("#parentID").val();
	for(var i = 0;jsonAwardCategoryList != null && jsonAwardCategoryList.length > 0 && i < jsonAwardCategoryList.length;i++)
	{
		if (jsonAwardCategoryList[i].parentID == parentID) {
			$jq("#awardCatId").append(
					'<option value=' + jsonAwardCategoryList[i].id
							+ '>' + jsonAwardCategoryList[i].name
							+ '</option>');
		}
	}
}

function manageAwardMaster()
{
	var errorMessage = "";
	var status = true;
	if ($jq("#parentID").val() == "") {
		errorMessage += "Select Organisation.\n";
		status = false;
		$jq("#parentID").focus();
	}
	if ($jq("#awardCatId").val() == "") {
		errorMessage += "Select Award Category.\n";
		status = false;
		$jq("#awardCatId").focus();
	}
	if ($jq("#awardTypeId").val() == "") {
		errorMessage += "Select Award Type.\n";
		status = false;
		$jq("#awardTypeId").focus();
	}
	if ($jq("#name").val() == "") {
		errorMessage += "Enter Award Name.\n";
		status = false;
		$jq("#name").focus();
	}
	
	if ($jq("#awardMaxLimit").val()=="0") {
		$jq("#awardMaxLimit").val('');
	}
	if ($jq("#awardMoney").val()=="0") {
		$jq("#awardMoney").val('');
	}
	if (status) {
		
		$jq('#param').val('manageAwardMaster');
		var requestDetails = {
				"id" : $jq("#id").val(),
				"name" : $jq("#name").val(),
				"parentID" : $jq("#parentID").val(),
				"awardTypeId" : $jq("#awardTypeId").val(),
				"awardCatId" : $jq("#awardCatId").val(),
				"awardMaxLimit" : $jq("#awardMaxLimit").val(),
				"awardMoney" : $jq("#awardMoney").val(),
				"remarks" : $jq("#remarks").val(),
				"description" : $jq("#description").val(),
				"type" : $jq("#type").val(),
				"param" : $jq('#param').val()
			};
			$jq.ajax( {
				type : "POST",
				url : "master.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
					$jq("#displayTable").html(html);
					if ($jq('.success').is(':visible')) {
					$jq("#id").val('');
					$jq("#name").val('');
					$jq("#awardTypeId").val('');
					$jq("#awardCatId").val('');
					$jq("#parentID").val('');
					$jq("#remarks").val('');
					$jq("#description").val('');
					$jq("#awardMaxLimit").val('');
					$jq("#awardMoney").val('');
					$jq('#nameCounter').val('200');
					$jq('#remarkscounter').val('200');
					$jq('#descriptioncounter').val('200');
					}
					
				}
			});
	} else {
		alert(errorMessage);
	}
}
//added to clear the fields in createaward screen
function clearManageAwardMaster(){
	$jq('#parentID').val('Select');
	$jq('#awardCatId').val('Select');
	$jq('#awardTypeId').val('Select');
	$jq('#name').val('');
	$jq('#description').val('');
	$jq('#remarks').val('');
	$jq('#awardMaxLimit').val('');
	$jq('#awardMoney').val('');
}
//delete the records in award details screen
function deleteAwardMaster(id){
	var check =confirm("Do u want to delete ?");
	if(check){
		var requestDetails = {
				"param" :"deleteAwardMaster",
				"id"	:id,
				"type"	:"awardMaster"
		};
		$jq.ajax( {
			type : "POST",
			url : "master.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#displayTable').html(html);
				if ($jq('.success').is(':visible')) {
					
				}
			}
		});

	}
}
// for adding scale
function changeEmpPayscale(){
	$jq('#empPayScaleId').val($jq('#designationId option:selected').val());
}

function editAwardMaster(jsonMasterDataList,jsonAwardCategoryList,id)
{
	for(var i = 0;jsonMasterDataList != null && jsonMasterDataList.length > 0 && i < jsonMasterDataList.length;i++)
	{
		if (jsonMasterDataList[i].id == id) {
			$jq("#id").val(jsonMasterDataList[i].id);
			$jq("#name").val(jsonMasterDataList[i].name);
			$jq("#parentID").val(jsonMasterDataList[i].parentID);
			getAwardCategory(jsonAwardCategoryList);
			$jq("#awardTypeId").val(jsonMasterDataList[i].awardTypeId);
			$jq("#awardCatId").val(jsonMasterDataList[i].awardCatId);
			$jq("#remarks").val(jsonMasterDataList[i].remarks);
			$jq("#description").val(jsonMasterDataList[i].description);
			$jq("#awardMaxLimit").val(jsonMasterDataList[i].awardMaxLimit);
			$jq("#awardMoney").val(jsonMasterDataList[i].awardMoney);
			$jq('#nameCounter').val(200-jsonMasterDataList[i].name.length);
			$jq('#remarkscounter').val(200-jsonMasterDataList[i].remarks.length);
			$jq('#descriptioncounter').val(200-jsonMasterDataList[i].description.length);
		}
	}
}
// change password
function changePassword(){
	 document.forms[0].param.value = "changepwd";
	 document.forms[0].type.value = "changepwd"; 
	 document.forms[0].action = "employee.htm";
	 document.forms[0].submit(); 
}
function ChangePwd(){ 
	var errorMessage = "";
	var status=true;
	
	
	if ($jq('#newpassword').val() == '') {
		errorMessage += "Please Enter Newpassword\n";
		if (status) {
			$jq('#newpassword').focus();
			status = false;
		}
	}
	if ($jq('#confirmpassword').val() == '') {
		errorMessage += "Please Enter confirm password\n";
		if (status) {
			$jq('#confirmpassword').focus();
			status = false;
		}
	}
			
	if(document.getElementById('newpassword').value != document.getElementById('confirmpassword').value){  
		errorMessage += "Please Enter new Password and ConformPassword are same\n";
        if(status){
        	status=false;
		document.getElementById('confirmpassword').focus();
			  }
	}
	if(status==false){
		alert(errorMessage);
	}

   // document.forms[0].param.value = "changepwdsubmit";
   // document.forms[0].action = "employee.htm";
    // document.forms[0].submit();
	
	if (status) {
		var requestDetails = {
				"newpassword" : document.getElementById('newpassword').value,
				
				"param" : "changepwdsubmit"
			};
			$jq.ajax( {
				type : "POST",
				url : "employee.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
				$jq('#result').html(html);
				if ($jq('.success').is(':visible')) {
					clearChange();
				    }
				}
			});
	} 
}

function clearChange()
{
	document.getElementById('newpassword').value = '';
	
	document.getElementById('confirmpassword').value = '';
}

// end change password

// emp status
function EmployeeStatus(){
	 document.forms[0].param.value = "empStatus";
	 document.forms[0].type.value = "empStatus"; 
	 document.forms[0].action = "employee.htm";
	 document.forms[0].submit(); 
}


function changeEmpStatus() {
	var selectedValues = "";
	var errorMessages = "";
	var leftValues = "";
	var flag = true;
	var status = false;
	if (document.getElementById('organizationId').value == "select") {
		errorMessages = "Please Select Organisation..\n";
		document.getElementById('organizationId').focus();
		flag = false;
	}
	
	for ( var i = 0; i < document.getElementById('SelectRight').options.length; i++) {
			selectedValues += document.getElementById('SelectRight').options[i].value+ ",";
			status = true;
	}  
	
	if (!status) {
		errorMessages += "Please Select Employees\n";
		flag = false;
	}
	
	  var length = document.getElementById('SelectLeft').options.length;
	  for(var i=0;i<length;i++){ leftValues +=
	  document.getElementById('SelectLeft').options[i].value; }
	  if(leftValues==0){ errorMessages += "Please Select Employees\n"; flag =
	  false; }
	 
	if (flag) {
		document.forms[0].param.value = "empstatusChangeSubmit";
		$jq('#param').val('empstatusChangeSubmit');
		document.forms[0].selectedLinks.value = selectedValues;
		$jq.post('employee.htm', $jq('#employee').serialize(),function(html) {
			$jq("#result1").html(html);
			// $jq("#result").html('');
			//clearEmpStatus();
			
		});
	} else {
		alert(errorMessages);
	}
}
function clearEmpStatus() {
	document.getElementById('organizationId').value = "select";
	for ( var i = 0; i < document.getElementById('SelectLeft').options.length; i++) {
		document.getElementById('SelectLeft').options[i].text = "";
		document.getElementById('SelectLeft').options[i].value = "";
	}
	for ( var i = 0; i < document.getElementById('SelectRight').options.length; i++) {
		document.getElementById('SelectRight').options[i].text = "";
		document.getElementById('SelectRight').options[i].value = "";
	}
	
	$jq('#result').text('');
}

function selectOrganizationWiseEmployees()
{
	var flag=true;
	if (document.getElementById('organizationId').value == "select") {
		errorMessages = "Please Select Organization ?\n";
		document.getElementById('organizationId').focus();
		flag = false;
	}
	if(flag){
	$jq('#param').val('getOrgnizatonData');
	$jq.post('employee.htm', $jq('#employee').serialize(),
			function(html) {
				$jq('#result1').html(html);
				$jq('#result').text('');
				
			});
	}

}
// end empstatus


function getSpeMenuLinks() {
	if($jq('#applicationRole').val()!='select'){
		$jq.post('menu.htm?param=getList&id=' + document.getElementById('applicationRole').value, function(html) {
			$jq("#menuLinksList").html(html);
			
		});
	}
}



function checkPayKey(e){
	   var key;
		if(window.event){
			key = window.event.keyCode;
		}
		else{
			key = e.which;
		}
	  	if(key!=13 && key!=8 && key!=0 && key!=46 && key!=45 && (key<48 || key>57)){
	  		alert("Please enter numbers only");
			return false;
		}
		if(key==0){
			return true;
		}else{
			return true;
		}
}
function checkKeyPay(e,id){
	var key;
	var value = document.getElementById(id).value; 
	
	
	if(window.event){
		key = window.event.keyCode;
	}
	else{
		key = e.which;
	}
	if(value=="" && key==13){
		alert("Please Enter Scale of pay");
		return false;
	}
	if(key!=13 && key!=8 && key!=0 && key!=46 && key!=45 &&  (key<48 || key>57)){
		alert("Please Enter Numbers Only");
		
		return false;
	}
	else{
		return true;
	}
	
}

function getRequestIdDetails(requestId){
	    document.forms[0].requestId.value = $jq.trim(requestId);
		document.forms[0].roleId.value = 'roleId';
	    document.forms[0].param.value = "requestDetails";
		document.forms[0].action = "workflowmap.htm";
		document.forms[0].submit();	
}

function editLetterNumberReference(leterDate,leterno,description,fromPlace,toPlace,remarks,status,id){
	// nodeID = id;
	$jq('#letterDate').val(leterDate); 
	$jq("#status[value='" +status + "']").attr('checked', true);
	$jq('#letterNumber').val(leterno);
	$jq('#description').val(description);
	$jq('#fromPalce').val(fromPlace);
	$jq('#toPalce').val(toPlace);
	$jq('#remarks').val(remarks);
	$jq('#type').val("updateletter");
	$jq('#id').val(id);
	
	$jq("#status[value='" +status + "']").attr('checked', true);
	// if(status == '1'){
	// $jq('#input:radio[name=status]:checked').;
	// }else{
		// $ja('#status').val('2');
	// }
	
}
	
function manageLetterNumber(){
  var flag = true;
  var errorMessage = "";
  var temp = $jq('input:radio[name=status]:checked').val();
  
  if($jq('#letterDate').val() == '' ){
	  errorMessage += "Please Select LetterDate\n";
	  flag=false;
  }
  if($jq('#letterNumber').val() == '' ){
	  errorMessage += "Please Select LetterNumber \n";
	  flag=false;
  }
  if($jq('#description').val() == '' ){
	  errorMessage += "Please Select Description \n";
	  flag=false;
  }if($jq('#fromPalce').val() == '' ){
	  errorMessage += "Please Select FromPlace \n";
	  flag=false;
  }if($jq('#toPalce').val() == '' ){
	  errorMessage += "Please Select ToPlace \n";
	  flag=false;
  }
  if($jq('#remarks').val() == '' ){
	  errorMessage += "Please Select Remarks \n";
	  flag=false;
  }
  if(!$jq('input:radio[name=status]').is(':checked')){
		errorMessage += "Please Select Dipatch1 Or Dispatch2 \n";
		flag = false;
	}
  
 
  
  
	if(flag){
	$jq('#status').val($jq('input:radio[name=status]:checked').val());
	$jq('#param').val('manageletterNumber');
	$jq.post('master.htm',$jq('#master').serialize(),
			function(html) {
				$jq('#result1').html(html);
				
			});
	
	clearLetterNumber()
	}else{
	 alert(errorMessage);	
	}
  
}
function clearLetterNumber(){
   $jq('#letterDate').val('');
   $jq('#letterNumber').val('');
   $jq('#description').val('');
   $jq('#fromPalce').val('');
   $jq('#toPalce').val('');
   $jq('#remarks').val('');
   $jq('#id').val('');
  // $jq('#status').val('')
   $jq('input:radio').attr("checked",false);
}

function getLetterNumberReport(){
	var errorMessage = "";
	var status = true;
	
	if(document.getElementById('status').value==""&&document.getElementById('letterDate').value==""){
	status = true;
	}else{
		errorMessage = "Please Enter LetterDate and Status\n";
		document.getElementById('letterDate').focus();
	}
// if(document.getElementById('category').value=='select'){
// errorMessage += "Please Select Category\n";
// if(status){
// document.getElementById('category').focus();
// status = false;
// }
// }
	if(status){
	window
	.open(
	"./report.htm?param=letterNumberReport&letterDate="+document.forms[0].letterDate.value+"&status="+document.getElementById('status').value,
	'preview',
	'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	// document.forms[0].report();
	}
	else{
	alert(errorMessage);
	} 
}


function deleteLetter(id) {
	var del = confirm("Do you want to delete this item?");
	var date = $jq('#letterDate').val();
	if (del == true) {
		
		var requestDetails = {
				'id' : id,
				'param' : "deleteLetterNumber",
				'type':"letter",
				'letterDate':date
			};
		
		$jq.ajax( {
			type : "POST",
			url : "master.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result1').html(html);
				if ($jq('.success').is(':visible')) {
					
				}
			}
		});

		
		}
	
	}

function getLetterList(date){
	
	status = true;
	
	if(status){
		$jq('#param').val('getLetter');
		$jq.post('master.htm',$jq('#master').serialize(),
				function(html) {
					$jq('#result1').html(html);
					
	  });	
	}
	
}

function editDopartNo(type){
	
	getYearDOPart();
	
	var predopart = $jq.trim($jq('#doPartList').find('tr')[1].children[1].innerHTML);
	var newDopart = parseInt($jq.trim($jq('#doPartList').find('tr')[1].children[1].innerHTML).split('/')[1])+1;
	var newDopartTech = parseInt($jq.trim($jq('#doPartList').find('tr')[1].children[1].innerHTML).split('/')[2])+1;
	
	if(type.value == "G#1"){
		$jq('#doPartNo').val('GAZ/'+newDopart);
		$jq('#preDoPartNo').val(predopart);
		
	}else if(type.value == "G#5"){
		$jq('#doPartNo').val('GAZ/T/'+newDopartTech);
		$jq('#preDoPartNo').val(predopart);
	}else if(type.value == "NG#6"){
		$jq('#doPartNo').val('T/'+newDopart);
		$jq('#preDoPartNo').val(predopart);
	}else if(type.value == "NG#7"){
		$jq('#doPartNo').val('MIN/'+newDopart);
		$jq('#preDoPartNo').val(predopart)
	}else{
		$jq('#doPartNo').val('S/'+newDopart);
		$jq('#preDoPartNo').val(predopart);
	}
	getYearDOPart();
	
	
	
}
/*function test(e){
	var key=e.which;
	if(key==13){
		searchingAppRoles();
	}
}*/
//signing authority
function submitSigningAuthorityDetails(){
	var status = true;
	var errorMessage ="";
	
	if($jq('#authorityType').val()=='select'){
		errorMessage += "Please Select Authority Type \n";
		status=false;
	}
	if($jq('#authorityEmpName').val()==''){
		errorMessage += "Please Select Authority Name \n";
		status=false;
	}
	if(status){
		var requestDetails ={
				"pk" 				: nodeID,
				"param"				:"submitSigningAuthorityDetails",
				"authorityType"		:$jq('#authorityType').val(),
				"authorityEmpName"	:$jq('#authorityEmpName').val(),
		};
		$jq.post('signingAuthority.htm',requestDetails,function(html) {
			$jq("#result").html(html);
			if ($jq('.success').is(':visible')) {
				clearSigningAuthorityDetails();
			}
			});
	}else{
		alert(errorMessage);
	}
}

function submitMailConfiguration(){
	var status = true;
	var errorMessage ="";
	
	if($jq('#sendMail').val()=='select'){
		errorMessage += "Please Select option \n";
		status=false;
	}
	if(status){
		document.forms[0].param.value = "save";
		document.forms[0].action = "mailConfiguration.htm";
		document.forms[0].submit();
	}else{
		alert(errorMessage);
	}
}

function clearMailConfiguration(){
	$jq('#sendMail').val('select');
}

function editSigningAuthorityList(id,authorityType,authorityName,sfid){
	nodeID =id;
	$jq('#authorityType').val(authorityType);
	$jq('#authorityEmpName').val(authorityName+sfid);
}
function clearSigningAuthorityDetails(){
	$jq('#authorityType').val('Select');
	$jq('#authorityEmpName').val('');
}
function deleteSigningAuthorityList(id){
	var check =confirm("Do u want to delete ?");
	if(check){
		var requestDetails={
				"param" : "deleteSigningAuthority",
				"pk" :id
		};
		$jq.post('signingAuthority.htm'+'?'+dispUrl, requestDetails, function(html) {
			$jq("#result").html(html);
		});
	}
}
function ysrcp(divId,trId){
	if(divId=='pendingDiv'){
		$jq('#'+trId).attr('class','dashbpr');
	}
	else if(divId=='myrequestsDiv'){
		$jq('#'+trId).attr('class','dashbpr1');
	}
	else if(divId=='completedDiv'){
		$jq('#'+trId).attr('class','dashbpr2');
	}
	else if(divId=='delegatedDiv'){
		$jq('#'+trId).attr('class','dashbpr3');
	}
	else if(divId=='myAlertsDiv'){
		$jq('#'+trId).attr('class','dashbpr4');
	}
	else if(divId=='escalatedDiv'){
		$jq('#'+trId).attr('class','dashbpr5');
	}
} 
function ysrcp2(divId,trId){
	
	$jq('#'+trId).removeAttr('class');
} 
/* kosalaram */
function menuarrow(liId){
	
		$jq('#'+liId).attr('class','currentact');
	
	/*$jq('#slidemenu').each(function(){
		$jq(this).find('ul').find()
	});*/
	//else if(divId=='myrequestsDiv'){
	//	$jq('#'+trId).attr('class','dashbpr1');
	//}
	//else if(divId=='completedDiv'){
	//	$jq('#'+trId).attr('class','dashbpr2');
	//}
	////else if(divId=='delegatedDiv'){
	//	$jq('#'+trId).attr('class','dashbpr3');
	//}
	//else if(divId=='myAlertsDiv'){
	//	$jq('#'+trId).attr('class','dashbpr4');
//	}
//	else if(divId=='escalatedDiv'){
//		$jq('#'+trId).attr('class','dashbpr5');
	//}
} 
function menuarrow2(divId,liId){
	
	$jq('#'+liId).removeAttr('class');
} 


function setDates(){
	$jq('#fromDate').val('01-Jan-'+formatDate(new Date(),'yyyy'));
	$jq('#toDate').val(formatDate(new Date(),'dd-NNN-yyyy'));
}

// Photo upload
function photoUpload() {
	var status = true;
	var errorMessage = ""; var	smp = null; var pmp = null; var sip=null;
	if($jq('#sfid').val() == '') {
		errorMessage += "Please Enter SFID of employee\n";
		if(status) {
			status = false;
			$jq('#sfid').focus();
		}
	}
	if($jq('#files1').val() == '' && $jq('#files2').val() == '' && $jq('#files3').val() == '') {
		errorMessage += "Please select atleast Passport size photo\n";
		if(status)
			status = false;
	}
	//var n = $jq('input:files').length;
	/*var fileNames = new Array(n);
	for(var i = 0; i < n; i++) {
		if($jq('input:file')[i].value != '') {
			fileNames[i] = $jq('input:file')[i].value;
			$jq('#passportImageFile').val(fileNames[i]);
		} else {
			fileNames[i] = '';
		}
	}*/
	//var files = ['files2', 'files1', 'files3'];
	if($jq('#files1').val() != '') {
	pmp = $jq('#files1').val();
	}
	if($jq('#files2').val() != '') {
	smp =$jq('#files2').val();
	}
	if($jq('#files3').val() != '') {
		sip= $jq('#files3').val();
	}
	if(status) {
		$jq('#param').val('uploadImageFile');
        $jq.ajaxFileUpload({
			url:'image.htm?type=imageType&passportImageFile='+pmp+'&stampImageFile='+smp+'&smallImageFile='+sip+''+$jq('#employee').serialize(),
			secureuri:false,
			fileElementId :'NoFiles',
			async: false,
			success: function (data, status) { // Successful case
			//$jq("#result1").html($jq(data.body).html());
				alert('success');
			},
			error: function (data, status, e) { // Failure case
				alert('File uploading process has failed');
				status = false;
			}
		});
	} else {
		alert(errorMessage);
	}
}

function clearPhotoUpload() {
	$jq('#passportImageFile').val('');
	$jq('#stampImageFile').val();
	$jq('#smallImageFile').val();
}

function editOfflineEntry(id,sfid,presentDesig,promotedDesig,presentDate,promotedDate,basicPay,gradePay,newPay,newGradePay,seniorityDate,varIncEnd){
	
	nodeID=id;
	$jq('#refSfid').val(sfid);
	getCategoryWiseDesignations();
	//$jq('#empGradePayGrid').css("display","none");
	//$jq('#empDetailsMainGrid').css("display","none");
	//$jq('#empGradePayGrid').css("display", "block");
	//$jq('#empBasicPayGrid').css("display","none");
	//$jq('#empDetailsMainGrid').css("display","none");
	//$jq('#empGradePayGrid').show();
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
	//$jq('#VarIncVal').val(varIncVal);
	//getEmpGradePay();
   getEmpVarVal();
   nodeID=id;
}
function getCategoryWiseDesignations(){
	var flag = false;
	$jq('#result2').html('');
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
		//$jq('#Pagination').css("display","none");
		$jq('#empDetailsMainGrid').css("display","none");
		
		$jq("#designationTo").append($jq("<option></option>").attr("value",'0').text('Select'));
		$jq('#designationTo').find('option').remove().end();
			$jq.ajax( {
				type : "GET",
				url : "empPayment.htm",
				data : "param=basicpaygradepay&refSfid="
				//data : 'param=getCategoryDesignation&refSfid='
						+ $jq("#SearchSfid").val().toUpperCase()+'&sfID='+ $jq("#SearchSfid").val().toUpperCase()+'&sfid='+ $jq("#SearchSfid").val().toUpperCase(),
				success : function(html) {
					$jq('#result2').html(html);
				}
			});
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
	$jq('#designationTo').val('');
	//$jq('#newBasicPay').val('');
	$jq('#seniorityDate').val('');
	$jq('#twoAddl').val('');
	$jq('#valriableIncVal').val('');
	$jq('#varIncEnd').val('');
	$jq('textarea').val("");
	$jq('#designationFrom').find('option').remove().end();
	//$jq("#designationFrom").append($jq("<option></option>").attr("value",'0').text('Select'));
	//$jq('#designationTo').find('option').remove().end();
	//if($jq('#designationTo').find('option:selected').text()!='Select'){
	//$jq("#designationTo").append($jq("<option></option>").attr("value",'0').text('Select'));

	//}
	
		//$jq('#result2').text('');
	//$jq('#result2').text('');
	//$jq('#empGradePayGrid').css("display","none");
}

function getEmpGradePay(){
	$jq('#newGradePay').val('');
	for(var i=0; i<empGradePayJson.length; i++){
	if(empGradePayJson[i].id == $jq('#designationTo').val()){
	$jq('#newGradePay').val(empGradePayJson[i].gradePay);
	}
	}
	} 






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
				"sfid": $jq("#refSfid").val(),
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
				url : "empPayment.htm?"+dispUrl,
				data : requestDetails,
				cache : false,
				success : function(html) {
				//$jq('#empBasicPayGrid').css("display","none");
					$jq('#result2').html(html);
					
					
				}
			});
			resetOfflineEntry();	

	} else {
		alert(errorMessage);
	}
	}


function deleteOfflineEntry(id,sfid){
	if(id !="" || id != null){
		var resp=confirm("Do you want to delete this Record?");
		if(resp==true){
		$jq.post(
			'empPayment.htm?param=deleteRecordGradepay&refSfid='+sfid+'&nodeID='+id,
			function(html){
				$jq('#result').html(html);
			}
		)};
		resetOfflineEntry();
	   }
}


/*function getEmpGradePay(){
	$jq('#newGradePay').val('');
	for(var i=0; i<empGradePayJson.length; i++){
		if(empGradePayJson[i].id == $jq('#designationTo').val()){
		 $jq('#newGradePay').val(empGradePayJson[i].gradePay);
		}
	}
}*/





function getEmpVarVal(){
	if($jq('#designationTo').val()!=0){
		document.forms['EmployeePaymentBean'].param.value= "getVariableIncrementValue";
		//$jq('#param').val('getVariableIncrementValue');
	//	$jq.post('promotion.htm',$jq('#PromotionManagementBean').serialize(), function(html) {	
		$jq.post('empPayment.htm',$jq('#EmployeePaymentBean').serialize(), function(html) {	
			
			$jq('#result2').html(html);
		});
	}else{
		alert("Please Select Designation");
		$jq('#designationFrom').val('');
	}
	
}

function getEmpVarVal1(){
	
	if($jq('#designationTo').val()!=0){
		var requestDetails={
				"param" : "getVariableIncrementValue",
				"varIncValue":"",
				
		};
		$jq.post('empPayment.htm'+'?'+dispUrl, requestDetails, function(html) {
			$jq("#result2").html(html);
		});
	}
	else{
		alert("Please Select Designation");
		$jq('#designationFrom').val('');
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