
//written by Narayana on 03-oct=2012
var complJCount=0;



//added by Narayana
//for category
function saveCategoryDetails(){
	var flag = true;
	var errorMessage = "";
	if ($jq('#categoryName').val() == '') {
		errorMessage += "\n Please Enter Category Name ";
		if (flag) {
			$jq('#categoryName').focus();
			flag = false;
		
		}
	}  
	if ($jq('#carriageType1').is(':checked')==false  && $jq('#carriageType2').is(':checked')==false && $jq('#carriageType3').is(':checked')==false ) {
		errorMessage += "\n Please Select Carriage Type ";
		if (flag) {
			$jq('#carriageType').focus();
			flag = false;
		}
	}
	if ($jq('#catDesc').val() == '') {
		errorMessage += "\n Please Enter Category Description ";
		if (flag) {
			$jq('#catDesc').focus();
			flag = false;
		}
	}
	
	if(errorMessage==""){
		var requestDetails = {
				"param" : "saveCategory",
				"categoryName" : $jq('#categoryName').val(),
				"catDesc" : $jq('#catDesc').val(),
				"carriageType" : $jq('input:radio[name=carriageType]:checked').val(),
				"pk" : $jq('#pk').val()
			};
			$jq.post('transport.htm', requestDetails, function(html) {
				$jq("#result").html(html);
				
			});
			clearCategoryDetails();
	}
	else
	{
		alert(errorMessage);
	}	
}
function editCategoryDetails(catid){
	if(categoryListJson !=null){
		for(var i=0;i<categoryListJson.length;i++){
			if(categoryListJson[i].categoryId==catid){
				$jq('#pk').val(categoryListJson[i].categoryId);
				$jq('#categoryName').val(categoryListJson[i].categoryName);
				$jq('#catDesc').val(categoryListJson[i].catDesc);
				if(categoryListJson[i].carriageType=='1'){
					$jq("#carriageType1[value=" + categoryListJson[i].carriageType + "]").attr('checked', true);
				}else if(categoryListJson[i].carriageType=='2'){
					$jq("#carriageType2[value=" + categoryListJson[i].carriageType + "]").attr('checked', true);
				}else if(categoryListJson[i].carriageType=='3'){
					$jq("#carriageType3[value=" + categoryListJson[i].carriageType + "]").attr('checked', true);
				}
				
				
			}
		}
	}
	
}
function deleteCategoryDetails(catid)
{
	var check=confirm("Do you want to delete ?");
	if(check){
		var requestDetails = {
				"param" : "deleteCategory",
				"pk"    : catid
			};
			$jq.post('transport.htm', requestDetails, function(html) {
				$jq("#result").html(html);
			});
			clearCategoryDetails();
	}
	
}
function clearCategoryDetails(){
	$jq('#categoryName').val('');
	$jq("input:radio").attr("checked", false);
	$jq('#catDesc').val('');
	 $jq('#pk').val('');
	 $jq('#counter').val(500);
}

// for model
function saveModelDetails(){
	var flag = true;
	var errorMessage = "";
	if ($jq('#vehicleCategoryId').val() == '0') {
		errorMessage += "\n Please select Category Name ";
		if (flag) {
			$jq('#vehicleCategoryId').focus();
			flag = false;
		
		}
	}  
	if ($jq('#modelName').val() == '') {
		errorMessage += "\n Please Enter Model Name ";
		if (flag) {
			$jq('#modelName').focus();
			flag = false;
		}
	}
	if ($jq('#modelDesc').val() == '') {
		errorMessage += "\n Please Enter Model Description ";
		if (flag) {
			$jq('#modelDesc').focus();
			flag = false;
		}
	}
	if(errorMessage==""){
		var requestDetails = {
				"param" : "saveModel",
				"vehicleCategoryId" : $jq('#vehicleCategoryId').val(),
				"modelName" : $jq('#modelName').val(),
				"modelDesc" : $jq('#modelDesc').val(),
				"pk" : $jq('#pk').val()
			};
			$jq.post('transport.htm?'+dispUrl, requestDetails, function(html) {
				$jq("#result").html(html);
				
			});
			clearModelDetails();
	}
	else
	{
		alert(errorMessage);
	}	
}
function editModelDetails(modid){
	if(modelListJson !=null){
		for(var i=0;i<modelListJson.length;i++){
			if(modelListJson[i].modelId==modid){
				$jq('#pk').val(modelListJson[i].modelId);
				$jq('#vehicleCategoryId').val(modelListJson[i].vehicleCategoryId);
				$jq('#modelName').val(modelListJson[i].modelName);
				$jq('#modelDesc').val(modelListJson[i].modelDesc);
			}
		}
	}
	/*
	 * $jq('#pk').val(modid); $jq('#vehicleCategoryId').val(catid);
	 * $jq('#modelName').val(modname); $jq('#modelDesc').val(moddesc);
	 */
}
function deleteModelDetails(modid)
{
	var check=confirm("Do you want to delete ?");
	if(check){
		var requestDetails = {
				"param" : "deleteModel",
				"pk"    : modid
			};
			$jq.post('transport.htm', requestDetails, function(html) {
				$jq("#result").html(html);
			});
			clearModelDetails();
	}
	
}
function clearModelDetails(){
	$jq('#vehicleCategoryId').val('0');
	$jq('#modelName').val('');
	$jq('#modelDesc').val('');
	$jq('#pk').val('');
	// $jq('#counter').val('');
}

// for TravelAgency
function saveTravelAgencyDetails(){
	var flag = true;
	var errorMessage = "";
	if ($jq('#travelAgencyName').val() == '') {
		errorMessage += "\n Please Enter Travel Agency Name ";
		if (flag) {
			$jq('#travelAgencyName').focus();
			flag = false;
		
		}
	}  
	if ($jq('#contactPerson').val() == '') {
		errorMessage += "\n Please Enter Contact Person Name ";
		if (flag) {
			$jq('#contactPerson').focus();
			flag = false;
		}
	}
	if ($jq('#travelMobileNo').val() == '') {
		errorMessage += "\n Please Enter Mobile No. ";
		if (flag) {
			$jq('#travelMobileNo').focus();
			flag = false;
		}
	}
	if ($jq('#address').val() == '') {
		errorMessage += "\n Please Enter Address ";
		if (flag) {
			$jq('#address').focus();
			flag = false;
		}
	}
	if(errorMessage==""){
		var requestDetails = {
				"param" : "saveTravelAgency",
				"travelAgencyName" : $jq('#travelAgencyName').val(),
				"contactPerson" : $jq('#contactPerson').val(),
				"travelMobileNo" : $jq('#travelMobileNo').val(),
				"address" : $jq('#address').val(),
				"pk" : $jq('#pk').val()
			};
			$jq.post('transport.htm', requestDetails, function(html) {
				$jq("#result").html(html);
				
			});
			clearTravelAgencyDetails();
	}
	else
	{
		alert(errorMessage);
	}	
}
function editTravelAgencyDetails(travid){
	if(TravelAgencyListJson != null){
		for(var i=0;i<TravelAgencyListJson.length;i++){
			if(TravelAgencyListJson[i].travelId==travid){
				$jq('#pk').val(TravelAgencyListJson[i].travelId);
				$jq('#travelAgencyName').val(TravelAgencyListJson[i].travelAgencyName);
				$jq('#contactPerson').val(TravelAgencyListJson[i].contactPerson);
				$jq('#travelMobileNo').val(TravelAgencyListJson[i].travelMobileNo);
				$jq('#address').val(TravelAgencyListJson[i].address);
			}
		}
	}
	/*
	 * $jq('#pk').val(travid); $jq('#travelAgencyName').val(travname);
	 * $jq('#contactPerson').val(cpname); $jq('#travelMobileNo').val(mno);
	 * $jq('#address').val(add);
	 */
}
function deleteTravelAgencyDetails(travid)
{
	var check=confirm("Do you want to delete ?");
	if(check){
		var requestDetails = {
				"param" : "deleteTravelAgency",
				"pk"    : travid
			};
			$jq.post('transport.htm', requestDetails, function(html) {
				$jq("#result").html(html);
			});
			clearTravelAgencyDetails();
	}
	
}
function clearTravelAgencyDetails(){
	$jq('#travelAgencyName').val('');
	$jq('#contactPerson').val('');
	$jq('#travelMobileNo').val('');
	$jq('#address').val('');
	$jq('#pk').val('');
	// $jq('#counter').val('');
}

// for Driver
function onselectHiredDriver(typeValue) {
	var temp = "";
	var travelList1 = "";
	if (travelList != null) {
		for ( var i = 0; i < travelList.length; i++) {
			travelList1 += '<option value=' + travelList[i].travelId + '>'
					+ travelList[i].travelAgencyName

					+ '</option>';
		}
	}
	if (typeValue == 'YES') {	
		temp = "<div class='line'>";
		temp += "<div class='quarter' style='font-weight:bold';>Travel Agency Name<span class='mandatory'>*</span></div>";
		temp += "<div class='quarter bold'><div class='half'>";
		temp += "<select name='driverTravelAgencyName' tabindex='1' id='driverTravelAgencyName'>";
		temp += "<option value='0'>Select</option>";
		temp += travelList1;
		temp += "</select>";
		temp += "</div></div>";
		temp += "</div>";
		document.getElementById("driverTravelAgency").style.display = "block";
		document.getElementById("driverTravelAgency").innerHTML = temp;

	} else {

		document.getElementById("driverTravelAgency").style.display = "none";
		$jq('#driverTravelAgencyName').val('');
	}
}
function saveDriverDetails(){
	var flag = true;
	var errorMessage = "";
	if ($jq('#driverType1').is(':checked')==false  && $jq('#driverType2').is(':checked')==false && $jq('#driverType3').is(':checked')==false) {
		errorMessage += "\n Please Select Driver Type ";
		if (flag) {
			$jq('#driverType').focus();
			flag = false;
		}
	}
	/*if ($jq('#driverType2').is(':checked')==true) {
		if ($jq('#driverTravelAgencyName').val() == '0') {
			errorMessage += "\n Please Select Travel Agency Name ";
			if (flag) {
				$jq('#driverTravelAgencyName').focus();
				flag = false;
			}
		}
	}*/
	if ($jq('#driverIdSfid').val() == '') {
		errorMessage += "\n Please Enter Driver ID ";
		if (flag) {
			$jq('#driverIdSfid').focus();
			flag = false;
		}
	}
	if ($jq('#driverName').val() == '') {
		errorMessage += "\n Please Enter Driver Name ";
		if (flag) {
			$jq('#driverName').focus();
			flag = false;
		}
	}
	if ($jq('#driverMobileNo').val() == '') {
		errorMessage += "\n Please Enter Mobile No. ";
		if (flag) {
			$jq('#driverMobileNo').focus();
			flag = false;
		}
	}
	if(errorMessage==""){
		var requestDetails = {
				"param" : "saveNewDriver",
				"driverType" : $jq('input:radio[name=driverType]:checked').val(),
				"driverIdSfid" :$jq('#driverIdSfid').val(),
				"driverName" : $jq('#driverName').val(),
				"driverMobileNo" : $jq('#driverMobileNo').val(),
				"driverTravelAgencyName" : $jq('#driverTravelAgencyName').val(),
				"pk" : $jq('#pk').val()
			};
			$jq.post('transport.htm', requestDetails, function(html) {
				$jq("#result").html(html);
				
			});
			clearDriverDetails();
	}
	else
	{
		alert(errorMessage);
	}	
}
function editDriverDetails(did){
	if(driverListJson !=null){
		for(var i=0;i<driverListJson.length;i++){
			if(driverListJson[i].driverId==did){
				$jq('#pk').val(driverListJson[i].driverId);
				$jq('#driverIdSfid').val(driverListJson[i].driverIdSfid);
				$jq('#driverName').val(driverListJson[i].driverName);
				$jq('#driverMobileNo').val(driverListJson[i].driverMobileNo);
				if(driverListJson[i].driverType=='1'){
					onselectHiredDriver('NO');
					$jq("#driverType1[value=" + driverListJson[i].driverType + "]").attr('checked', true);
					// $jq('#driverTravelAgencyName').val(taname);
				}else if(driverListJson[i].driverType=='2'){
					//onselectHiredDriver('YES');
					$jq("#driverType3[value=" + driverListJson[i].driverType + "]").attr('checked', true);
					// $jq('#driverTravelAgencyName').val(taname);
				}
				else if(driverListJson[i].driverType=='3'){
					$jq("#driverType2[value=" + driverListJson[i].driverType + "]").attr('checked', true);
					// onselectHiredDriver('YES');
					// $jq('#driverTravelAgencyName').val(taname);
				}
			}
		}
	}
	
}
function deleteDriverDetails(did)
{
	var check=confirm("Do you want to delete ?");
	if(check){
		var requestDetails = {
				"param" : "deleteDriver",
				"pk"    : did
			};
			$jq.post('transport.htm', requestDetails, function(html) {
				$jq("#result").html(html);
			});
			clearDriverDetails();
	}
	
}
function clearDriverDetails(){
	$jq("input:radio").attr("checked", false);
	$jq('#driverName').val('');
	$jq('#driverIdSfid').val('');
	$jq('#driverMobileNo').val('');
	$jq('#driverTravelAgencyName').val('0');
	$jq('#pk').val('');
	document.getElementById("driverTravelAgency").style.display = "none";
}

// for new Vehicle
function onselectHiredVehicleDropDown(typeValue) {
	var temp = "";
	var travelList1 = "";
	if (travelList != null) {
		for ( var i = 0; i < travelList.length; i++) {
			travelList1 += '<option value=' + travelList[i].travelId + '>'
					+ travelList[i].travelAgencyName

					+ '</option>';
		}
	}
	if (typeValue == 'NO') {
		$jq('#HiredVehicles').css("display","none");
		$jq('#GovtVehicles').css("display","block");
	}else{
		$jq('#GovtVehicles').css("display","none");
	}
	if (typeValue == 'YES') {
		temp = "<div class='line'>";
		temp += "<div class='quarter' style='font-weight:bold;'>Travel Agency Name<span class='mandatory'>*</span></div>";
		temp += "<div class='quarter bold'><div class='half'>";
		temp += "<select name='vehicleTravelAgencyName' tabindex='1' id='vehicleTravelAgencyName'>";
		temp += "<option value='0'>Select</option>";
		temp += travelList1;
		temp += "</select>";
		temp += "</div></div>";
		temp += "</div>";
		temp += "<div class='line'>";
		temp += "<div class='quarter' style='font-weight:bold';>Driver Name<span class='mandatory'>*</span></div>";
		temp += "<div class='quarter bold'><div class='half'>";
		//temp += "<input name='driverName' tabindex='1' id='driverName'>";
        temp +="<select path='driverName' id='driverName' onchange=getDriverMobileNo()><option value='0'>Select</option>";
        	for(var i=0;i<hiredDriversJson.length;i++){
        		temp +="<option value="+hiredDriversJson[i].driverId+">"+hiredDriversJson[i].driverName+"</option>";
        		
        	}
		temp += "</select></div></div>";

		temp += "</div>";
		temp += "</div>";
		temp += "<div class='line'>";
		temp += "<div class='quarter' style='font-weight:bold';>Mobile NO<span class='mandatory'>*</span></div>";
		temp += "<div class='quarter bold'><div class='half'>";
		temp += "<input name='driverMobileNo' tabindex='1' id='driverMobileNo' onkeypress='return checkInt(event)'>";

		temp += "</div></div>";

		temp += "</div>";
		
		document.getElementById("vehicleTravelAgency").style.display = "block";
		document.getElementById("vehicleTravelAgency").innerHTML = temp;
		$jq('#GovtVehicles').css("display","none");
		$jq('#HiredVehicles').css("display","block");
		
	} else {
		$jq('#vehicleTravelAgencyName').val('');
		$jq('#driverName').val('');
		$jq('#driverMobileNo').val('');
		document.getElementById("vehicleTravelAgency").style.display = "none";
		$jq('#HiredVehicles').css("display","none");
	}

}
function getDriverMobileNo(){
	var driverId=$jq('#driverName').val();
	for(var i=0;i<hiredDriversJson.length;i++){
		if(hiredDriversJson[i].driverId==driverId){
			$jq('#driverMobileNo').val(hiredDriversJson[i].driverMobileNo);
			//$jq('#driverMobileNo').attr("readonly",true);
		}
	}
}
function onselectDedicatedVehicle(typeValue){
	$jq('#vehicleDedicatedPerson').html('');
	if(typeValue=='YES'){
	
		
	
  var temp ="<div class='line'><div class='quarter bold'>Vehicle Will Reports To(SFID)<span class='mandatory'>*</span></div>" +
  		"<div class='quarter bold'><select path='dedicatedPerson' id='dedicatedPerson' ><option value='0'>Select</option>";
		for(var i=0;i<AllEmployeeList.length;i++){
			temp += "<option value='"+AllEmployeeList[i].userSfid+"'>"+AllEmployeeList[i].userSfid+"("+AllEmployeeList[i].nameInServiceBook+"--"+AllEmployeeList[i].designationDetails.name+")</option>";
		}
		temp += "</select></div></div><div class='line'><div class='quarter bold'>Purpose To Dedicate</div><div class='half'><input type='text' name='remarks' id='remarks' /></div>"
			$jq('#vehicleDedicatedPerson').append(temp).css("display","block");
	}
	if(typeValue=='NO'){
		$jq('#vehicleDedicatedPerson').html('');
	}
}

function onchangeCategory(){
	var temp = "";
	var modelList1 = "";
	var catId = document.getElementById("vehicleCategoryId").value;
	for ( var i = 0; i < modelList.length; i++) {
		if (modelList[i].vehicleCategoryId == catId) {
					modelList1 += '<option value=' + modelList[i].modelId + '>'
							+ modelList[i].modelName

							+ '</option>'; 
					
					// for load capacity of vehicle
					for(var j=0; j<categoryList1.length; j++){
						if(categoryList1[j].categoryId == catId){
							var temp1= "";
							if(categoryList1[j].carriageType =="1"){
								
								temp1 = "<div class='line'>";
								temp1 += "<div class='quarter bold'>Carriage Type</div>";
								temp1 += "<div class='half'>";
								temp1 += categoryList1[j].carriageDetails.vehicleCarriageType;
								temp1 += "</div></div>";
								document.getElementById("CarriageDetails").style.display = "block";
								document.getElementById("CarriageDetails").innerHTML = temp1;	
							}else if(categoryList1[j].carriageType =="2"){
								
								temp1 = "<div class='line'>";
								temp1 += "<div class='quarter bold'>Carriage Type</div>";
								temp1 += "<div class='half'>";
								temp1 += categoryList1[j].carriageDetails.vehicleCarriageType;
								temp1 += "</div></div>";
								document.getElementById("CarriageDetails").style.display = "block";
								document.getElementById("CarriageDetails").innerHTML = temp1;
							}else if(categoryList1[j].carriageType =="3"){
								
								temp1 = "<div class='line'>";
								temp1 += "<div class='quarter bold'>Carriage Type</div>";
								temp1 += "<div class='half'>";
								temp1 += categoryList1[j].carriageDetails.vehicleCarriageType;
								temp1 += "</div></div>";
								document.getElementById("CarriageDetails").style.display = "block";
								document.getElementById("CarriageDetails").innerHTML = temp1;
							}
						}
					}
		      }
	       }
	
	temp = "<div class='line'>";
	temp += "<div class='quarter' style='font-weight:bold;'>Model<span class='mandatory'>*</span></div>";
	temp += "<div class='quarter bold'>";
	temp += "<select name='vehicleModelId' tabindex='1'  id='vehicleModelId' cssClass='formSelect' >";
	temp += "<option value='0'>Select</option>";
	temp += modelList1;
	temp += "</select>";
	temp += "</div></div>";
	document.getElementById("ModelListDetails").style.display = "block";
	document.getElementById("ModelListDetails").innerHTML = temp;	
	
 }


function saveVehicleDetails(){
	var flag = true;
	var errorMessage = "";
	if ($jq('#vehicleType1').is(':checked')==false  && $jq('#vehicleType2').is(':checked')==false) {
		errorMessage += "\n Please Select Vehicle Type ";
		if (flag) {
			$jq('#vehicleType').focus();
			flag = false;
		}
	}
	if($jq('#vehiclePoolType1').is(':checked')==false  && $jq('#vehiclePoolType2').is(':checked')==false){
		errorMessage += "\n Please Select Purpose Of Vehicle ";
		$jq('#vehiclePoolType').focus();
		flag = false;
		
	}
	if($jq('#vehiclePoolType1').is(':checked')==true  || $jq('#vehiclePoolType2').is(':checked')==true){
		if($jq('#dedicatedPerson').val() == 0){
		errorMessage += "\n Please Enter Dedicated Persion SFID ";
		$jq('#dedicatedPerson').focus();
		flag = false;
		}
	}
	if ($jq('#vehicleType2').is(':checked')==true) {
		if ($jq('#vehicleTravelAgencyName').val() == '0') {
			errorMessage += "\n Please Select Travel Agency Name ";
			if (flag) {
				$jq('#vehicleTravelAgencyName').focus();
				flag = false;
			}
		}
		if ($jq('#driverName').val() == '0') {
			errorMessage += "\n Please Enter Driver Name ";
			if (flag) {
				$jq('#driverName').focus();
				flag = false;
			}
		}
		if ($jq('#driverMobileNo').val() == '') {
			errorMessage += "\n Please Enter Driver Mobile No ";
			if (flag) {
				$jq('#driverMobileNo').focus();
				flag = false;
			}
		}
	}
	if ($jq('#vehicleCategoryId').val() == '0') {
		errorMessage += "\n Please Select Category ";
		if (flag) {
			$jq('#vehicleCategoryId').focus();
			flag = false;
		}
	}
	if ($jq('#vehicleCategoryId').val() != '0') {
		if ($jq('#vehicleModelId').val() == '0') {
			errorMessage += "\n Please Select Model ";
			if (flag) {
				$jq('#vehicleModelId').focus();
				flag = false;
			}
		}
	}
	if ($jq('#vehicleNo').val() == '') {
		errorMessage += "\n Please Enter Vehicle No ";
		if (flag) {
			$jq('#vehicleNo').focus();
			flag = false;
		}
	}
	if ($jq('#vehicleName').val() == '') {
		errorMessage += "\n Please Enter Vehicle Name ";
		if (flag) {
			$jq('#vehicleName').focus();
			flag = false;
		}
	}
	if ($jq('#noOfPeople').val() == '') {
		errorMessage += "\n Please Enter Vehicle Load Capacity ";
		if (flag) {
			$jq('#noOfPeople').focus();
			flag = false;
		}
	}
	if ($jq('#vehicleType1').is(':checked')==true) {
		if ($jq('#fuelTypeId').val() == '0') {
			errorMessage += "\n Please Select Type of Fuel ";
			if (flag) {
				$jq('#fuelTypeId').focus();
				flag = false;
			}
		}
		if ($jq('#fuelCapacity').val() == '') {
			errorMessage += "\n Please Enter Vehicle Fuel Capacity ";
			if (flag) {
				$jq('#fuelCapacity').focus();
				flag = false;
			}
		}
		if ($jq('#mileage').val() == '') {
			errorMessage += "\n Please Enter Vehicle Mileage ";
			if (flag) {
				$jq('#mileage').focus();
				flag = false;
			}
		}
	}
	if ($jq('#initialReading').val() == '') {
		errorMessage += "\n Please Enter Vehicle Initial Reading ";
		if (flag) {
			$jq('#initialReading').focus();
			flag = false;
		}
	}
	if ($jq('#vehicleSensitiveType1').is(':checked')==false  && $jq('#vehicleSensitiveType2').is(':checked')==false) {
		errorMessage += "\n Please Select Vehicle Sesitive Type ";
		if (flag) {
			$jq('#vehicleSensitiveType').focus();
			flag = false;
		}
	}
	
	if ($jq('#vehicleType2').is(':checked')==true) {
		$jq('#fuelCapacity').val($jq('#hiredFuelCapacity').val());
		$jq('#mileage').val($jq('#hiredMileage').val());
	}
	
	/*if ($jq('#vehiclePoolType1').is(':checked')==false  && $jq('#vehiclePoolType2').is(':checked')==false) {
		errorMessage += "\n Please Select Vehicle Pool Type ";
		if (flag) {
			$jq('#vehiclePoolType').focus();
			flag = false;
		}
	}*/
	
	
	if(errorMessage==""){
		var requestDetails = {
				"param" : "saveNewVehicle",
				"vehicleType" : $jq('input:radio[name=vehicleType]:checked').val(),
				"vehicleTravelAgencyName" : $jq('#vehicleTravelAgencyName').val(),
				"driverName" : $jq('#driverName').val(),
				"driverMobileNo" : $jq('#driverMobileNo').val(),
				"vehicleCategoryId" : $jq('#vehicleCategoryId').val(),
				"vehicleModelId" : $jq('#vehicleModelId').val(),
				"vehicleNo" : $jq('#vehicleNo').val(),
				"vehicleName" : $jq('#vehicleName').val(),
				"noOfPeople" : $jq('#noOfPeople').val(),
				"fuelTypeId" : $jq('#fuelTypeId').val(),
				"fuelCapacity" : $jq('#fuelCapacity').val(),
				"mileage" : $jq('#mileage').val(),
				"initialReading" : $jq('#initialReading').val(),
				"vehiclePoolType" :$jq('input:radio[name=vehiclePoolType]:checked').val(),
				"vehicleSensitiveType" :$jq('input:radio[name=vehicleSensitiveType]:checked').val(),
				"dedicatedPerson" :$jq('#dedicatedPerson').val(),
				"remarks" : $jq('#remarks').val(),
				"pk" : $jq('#pk').val()
			};
		var res="";
			$jq.post('transport.htm?'+dispUrl, requestDetails, function(html) {
				$jq("#result").html(html);
				if(html.indexOf('success')>0){
					res="yes";
				}
			});
			if(res=='yes'){
				clearVehicleDetails();
			}
			
	}
	else
	{
		alert(errorMessage);
	}	
}

function editVehicleDetails(vid,vtype,vcid,vmid,vname,vno,vtname,vdname,vdmno,nop,vfc,vm,vir,vpt,vst,ftype){
	$jq('#pk').val(vid);
	$jq('#vehicleDedicatedPerson').html('');
	if(vtype=='1'){
		onselectHiredVehicleDropDown('NO');
		$jq("#vehicleType1[value=" + vtype + "]").attr('checked', true);
		//
		$jq('#fuelTypeId').val(ftype);
		$jq('#hiredFuelCapacity').val(vfc);
		$jq('#hiredMileage').val(vm);
		$jq('#initialReading').val(vir);
		
	}else if(vtype=='2'){
		$jq("#vehicleType2[value=" + vtype + "]").attr('checked', true);
		onselectHiredVehicleDropDown('YES');
		$jq('#vehicleTravelAgencyName').val(vtname);
		$jq('#driverName').val(vdname);
		$jq('#driverMobileNo').val(vdmno);
	}
	if(vcid !='0'){
	$jq('#vehicleCategoryId').val(vcid);
		onchangeCategory();
	$jq('#vehicleModelId').val(vmid);
	}
	$jq('#vehicleNo').val(vno);
	$jq('#vehicleName').val(vname);
	$jq('#noOfPeople').val(nop);
	$jq('#fuelTypeId').val(ftype);
	if (vtype=='2') {
		$jq('#hiredFuelCapacity').val(vfc);
		$jq('#hiredMileage').val(vm);
	}else if(vtype=='1'){
		$jq('#fuelCapacity').val(vfc);
		$jq('#mileage').val(vm);
	}
	$jq('#initialReading').val(vir);
	if(vpt == '1'){
		$jq("#vehiclePoolType1[value=" + vpt + "]").attr('checked', true);
		 onselectDedicatedVehicle('YES');
		 if(vehicleListJson != null){
			 for(var i=0;i<vehicleListJson.length;i++){
				 if(vehicleListJson[i].vehicleId==vid){
					 $jq('#dedicatedPerson').val(vehicleListJson[i].dedicatedPersonSfid); 
					 $jq('#remarks').val(vehicleListJson[i].remarks); 
				 }
			 }
		 }
		// $jq('#dedicatedPerson').val(sfid);
		 
	}else if(vpt == '2'){
		$jq("#vehiclePoolType2[value=" + vpt + "]").attr('checked', true);	
	}
	if(vst == '1'){
		$jq("#vehicleSensitiveType1[value=" + vst + "]").attr('checked', true);	
	}else if(vst == '2'){
		$jq("#vehicleSensitiveType2[value=" + vst + "]").attr('checked', true);	
	}
}
function deleteVehicleDetails(vid)
{
	var check=confirm("Do you want to delete ?");
	if(check){
		var requestDetails = {
				"param" : "deleteVehicle",
				"pk"    : vid
			};
			$jq.post('transport.htm', requestDetails, function(html) {
				$jq("#result").html(html);
			});
			clearVehicleDetails();
	}
	
}
function clearVehicleDetails(){
	$jq("input:radio").attr("checked", false);
	$jq('#vehicleTravelAgencyName').val('0');
	$jq('#driverName').val('');
	$jq('#driverMobileNo').val('');
	$jq('#vehicleCategoryId').val('0');
	$jq('#vehicleModelId').val('0');
	$jq('#vehicleNo').val('');
	$jq('#vehicleName').val('');
	$jq('#noOfPeople').val('');
	$jq('#fuelCapacity').val('');
	$jq('#mileage').val('');
	$jq('#initialReading').val('');
	$jq('#pk').val('');
	$jq('#dedicatedPerson').val('');
	$jq('#hiredFuelCapacity').val('');
	$jq('#hiredMileage').val('');
	$jq('#fuelTypeId').val('0');
	document.getElementById("vehicleTravelAgency").style.display = "none";
	document.getElementById("CarriageDetails").style.display = "none";
	document.getElementById("ModelListDetails").style.display = "none";
}

// for Addresses
function saveAddressDetails(){
	var flag = true;
	var errorMessage = "";
	if ($jq('#addressName').val() == '') {
		errorMessage += "\n Please Enter Address ";
		if (flag) {
			$jq('#addressName').focus();
			flag = false;
		}
	}
	if(errorMessage==""){
		var requestDetails = {
				"param" : "saveAddress",
				"addressName" : $jq('#addressName').val(),
				"pk" : $jq('#pk').val()
			};
			$jq.post('transport.htm', requestDetails, function(html) {
				$jq("#result").html(html);
				
			});
			clearAddressDetails();
	}
	else
	{
		alert(errorMessage);
	}	
}
function editAddressDetails(aid){
	if(addressListJson !=null){
		for(var i=0;i<addressListJson.length;i++){
			if(addressListJson[i].addressId==aid){
				$jq('#pk').val(addressListJson[i].addressId);
				$jq('#addressName').val(addressListJson[i].addressName);
			}
		}
	}
	/*
	 * $jq('#pk').val(aid); $jq('#addressName').val(aname);
	 */
}
function deleteAddressDetails(aid)
{
	var check=confirm("Do you want to delete ?");
	if(check){
		var requestDetails = {
				"param" : "deleteAddress",
				"pk"    : aid
			};
			$jq.post('transport.htm', requestDetails, function(html) {
				$jq("#result").html(html);
			});
			clearAddressDetails();
	}
	
}
function clearAddressDetails(){
	$jq('#addressName').val('');
	$jq('#pk').val('');
}

// for Driver Absent
function saveDriverAbsentDetails(){
var flag = true;
var errorMessage = "";
if($jq('#driverName').val()=='0'){
	errorMessage += "Please Select Driver Name \n"
		if(flag){
			$jq('#driverName').focus();
			flag = false;
		}
 }
if($jq('#fromDate').val()==''){
	errorMessage += "Please Select From Date  \n"
		if(flag){
			$jq('#fromDate').focus();
			flag = false;
		}
 }
if($jq('#fromTime').val()==''){
	errorMessage += "Please Select From Time \n"
		flag = false;
 }
if($jq('#toDate').val()==''){
	errorMessage += "Please Select To Date \n"
		if(flag){
			$jq('#toDate').focus();
			flag = false;
		}
 }
if($jq('#toTime').val()==''){
	errorMessage += "Please Select To Time \n"
		flag = false;
		
 }
if($jq('#fromDate').val()!='' && $jq('#toDate').val()!=''){
	var fdate = convertDate($jq('#fromDate').val());
	var tdate = convertDate($jq('#toDate').val());
	
	if(compareDates(fdate,'dd-MM-yyyy',tdate,'dd-MM-yyyy')==1){
		errorMessage += "To Date Must be Greater Than From Date \n";
			flag = false;
	}else if(compareDates(tdate,'dd-MM-yyyy',fdate,'dd-MM-yyyy')!=1){
		var ftime=$jq('#fromTime').val();
		var ttime=$jq('#toTime').val();
		if(compareHoursTime(ftime.split(":")[0],ttime.split(":")[0])==1){
			errorMessage += "To Time Must be Greater Than From Time \n";
			flag = false;
		}else if(compareHoursTime(ftime.split(":")[0],ttime.split(":")[0])==0){
			if(compareMinutesTime(ftime.split(":")[1],ttime.split(":")[1])==1 || compareMinutesTime(ftime.split(":")[1],ttime.split(":")[1])==0){
				errorMessage += "To Time Must be Greater Than From Time \n";
				flag = false;
			}
		}
	}	
 }
if(errorMessage==""){
	$jq('#param').val("saveDriverAbsent");
		$jq.post('transport.htm', $jq('#mtBean').serialize(), function(html) {
			$jq("#result").html(html);
			
		});
		clearDriverAbsentDetails();
	}else
	{
		alert(errorMessage);
	}	

}

function editDriverAbsentDetails(id,fromDate,toDate){
	if(AbsentDriverListJson !=null){
		for(var i=0;i<AbsentDriverListJson.length;i++){
			if(AbsentDriverListJson[i].id==id){
				$jq('#pk').val(AbsentDriverListJson[i].id);
				$jq('#driverName').val(AbsentDriverListJson[i].diverDetails.driverId);
				$jq('#fromDate').val(fromDate);
				$jq('#fromTime').val(AbsentDriverListJson[i].fromTime);
				$jq('#toDate').val(toDate);	
				$jq('#toTime').val(AbsentDriverListJson[i].toTime);	
				$jq('#remarks').val(AbsentDriverListJson[i].remarks);
			}
		}
	}
	/*
	 * $jq('#pk').val(id); $jq('#driverName').val(did);
	 * $jq('#fromDate').val(fdate); $jq('#fromTime').val(ftime);
	 * $jq('#toDate').val(tdate); $jq('#toTime').val(ttime);
	 * $jq('#remarks').val(remarks);
	 */	
}

function deleteDriverAbsentDetails(id){
	var check=confirm("Do you want to delete ?");
	if(check){
		var requestDetails = {
				"param" : "DeleteDriverAbsent",
				"pk"    : id
			};
			$jq.post('transport.htm', requestDetails, function(html) {
				$jq("#result").html(html);
			});
			clearDriverAbsentDetails();
	}
}

function clearDriverAbsentDetails(){
	$jq('#pk').val('');
	$jq('#driverName').val('0');
	$jq('input').val('');
	$jq('textarea').val('');
}

// for Vehicle Absent
function saveVehicleAbsentDetails(){
var flag = true;
var errorMessage = "";
if($jq('#vehicleNo').val()=='0'){
	errorMessage += "Please Select Vehicle No. \n"
		if(flag){
			$jq('#vehicleNo').focus();
			flag = false;
		}
 }
if($jq('#fromDate').val()==''){
	errorMessage += "Please Select From Date  \n"
		if(flag){
			$jq('#fromDate').focus();
			flag = false;
		}
 }
if($jq('#fromTime').val()==''){
	errorMessage += "Please Select From Time \n"
		flag = false;
		
 }
if($jq('#toDate').val()==''){
	errorMessage += "Please Select To Date \n"
		if(flag){
			$jq('#toDate').focus();
			flag = false;
		}
 }
if($jq('#toTime').val()==''){
	errorMessage += "Please Select To Time \n"
		flag = false;
 }
if($jq('#fromDate').val()!='' && $jq('#toDate').val()!=''){
	var fdate = convertDate($jq('#fromDate').val());
	var tdate = convertDate($jq('#toDate').val());
	
	if(compareDates(fdate,'dd-MM-yyyy',tdate,'dd-MM-yyyy')==1){
		errorMessage += "To Date Must be Greater Than From Date \n";
			flag = false;
	}else if(compareDates(tdate,'dd-MM-yyyy',fdate,'dd-MM-yyyy')!=1){
		var ftime=$jq('#fromTime').val();
		var ttime=$jq('#toTime').val();
		if(compareHoursTime(ftime.split(":")[0],ttime.split(":")[0])==1){
			errorMessage += "To Time Must be Greater Than From Time \n";
			flag = false;
		}else if(compareHoursTime(ftime.split(":")[0],ttime.split(":")[0])==0){
			if(compareMinutesTime(ftime.split(":")[1],ttime.split(":")[1])==1 || compareMinutesTime(ftime.split(":")[1],ttime.split(":")[1])==0){
				errorMessage += "To Time Must be Greater Than From Time \n";
				flag = false;
			}
		}
	}	
 }
if(errorMessage==""){
			$jq('#param').val("saveVehicleAbsent");
			$jq.post('transport.htm', $jq('#mtBean').serialize(), function(html) {
			$jq("#result").html(html);
			});
		clearVehicleAbsentDetails();
	}
	else
	{
		alert(errorMessage);
	}	

}

function editVehicleAbsentDetails(id,fdate,tdate){
	if(AbsentVehicleListJson !=null){
		for(var i=0;i<AbsentVehicleListJson.length;i++){
			if(AbsentVehicleListJson[i].id==id){
				$jq('#pk').val(AbsentVehicleListJson[i].id);
				$jq('#vehicleNo').val(AbsentVehicleListJson[i].vehicleDetails.vehicleId);
				$jq('#fromDate').val(fdate);
				$jq('#fromTime').val(AbsentVehicleListJson[i].fromTime);
				$jq('#toDate').val(tdate);	
				$jq('#toTime').val(AbsentVehicleListJson[i].toTime);	
				$jq('#remarks').val(AbsentVehicleListJson[i].remarks);
			}
		}
	}
	/*
	 * $jq('#pk').val(id); $jq('#vehicleNo').val(vid);
	 * $jq('#fromDate').val(fdate); $jq('#fromTime').val(ftime);
	 * $jq('#toDate').val(tdate); $jq('#toTime').val(ttime);
	 * $jq('#remarks').val(remarks);
	 */	
}
function deleteVehicleAbsentDetails(id){
	var check=confirm("Do you want to delete ?");
	if(check){
		var requestDetails = {
				"param" : "DeleteVehicleAbsent",
				"pk"    : id
			};
			$jq.post('transport.htm', requestDetails, function(html) {
				$jq("#result").html(html);
			});
			clearVehicleAbsentDetails();
	}
}

function clearVehicleAbsentDetails(){
	$jq('#vehicleNo').val('0');
	$jq('#pk').val('');
	$jq('input').val('');
	$jq('textarea').val('');
	
}


// for vehicle-driver map
function saveVehicleDriverDetails(){
	var flag = true;
	var errorMessage = "";
	if($jq('#vehicleId').val()=='0'){
		errorMessage += "Please Select Vehicle No. \n"
			if(flag){
				$jq('#vehicleId').focus();
				flag = false;
			}
	 }
	if($jq('#fromDate').val()==''){
		errorMessage += "Please Select Allotment Date  \n"
			if(flag){
				$jq('#fromDate').focus();
				flag = false;
			}
	 }
	if($jq('#fromTime').val()==''){
		errorMessage += "Please Select Allotment Time \n"
			flag = false;
	 }
	
	if(errorMessage==""){
		/*$jq('#param').val("SaveVehicleVsDriver");
			$jq.post('transport.htm', $jq('#mtBean').serialize(), function(html) {
				
				//$jq('#vehicleId').html('');

				//clearVehicleDriverDetails();
			     //mtVehicleVsDriver();
			     $jq("#messageDiv").val();
			    $jq("#messageDiv").text("sss");
				 	mtVehicleVsDriver();
					$jq("#messageDiv").text("sss");
			});*/
			  
		document.forms[0].param.value = "SaveVehicleVsDriver";
		document.forms[0].vehicleId.value=$jq('#vehicleId').val();
		document.forms[0].driverId.value=$jq('#driverId').val();
		document.forms[0].fromDate.value=$jq('#fromDate').val();
		document.forms[0].fromTime.value=$jq('#fromTime').val();
		document.forms[0].remarks.value=$jq('#remarks').val();
		
		document.forms[0].action = "transport.htm";
		document.forms[0].submit();
			
		}
		else
		{
			alert(errorMessage);
		}	
}

function clearVehicleDriverDetails(){
	$jq('#vehicleId').val('0');
	$jq('#driverId').val('0');
	$jq('#pk').val('');
	
	// $jq('input').val('');
	$jq('textarea').val('');
}

function editVehicleDriverDetails(id,vid,did,fdate,ftime,remarks){
	$jq('#pk').val(id);
	$jq('#vehicleId').val(vid);
	$jq('#driverId').val(did);
	// $jq('#fromDate').val(fdate);
	// $jq('#fromTime').val(ftime);
	$jq('#remarks').val(remarks);
	
}
function deleteVehicleDriverDetails(id){
	var check=confirm("Do you want to delete ?");
	if(check){
		var requestDetails = {
				"param" : "DeleteVehicleVsDriver",
				"pk"    : id
			};
			$jq.post('transport.htm', requestDetails, function(html) {
				$jq("#result").html(html);
				// clearVehicleDriverDetails();
			     // mtVehicleVsDriver();
			});
			clearVehicleDriverDetails();
	}
}
/*
 * function getDriverForVehicle(){ var vid = $jq('#vehicleId').val(); for(var
 * i=0 ; i<vehicleJson.length ; i++){ if(vehicleJson[i].value == vid){
 * if(vehicleJson[i].key == '2'){ alert("Hired vehicle"); }else{ alert("Govt
 * vehicle");} } } }
 */
function changeVehicleDriverMapDetails(mapId,previousVid,PreviousDId,ival,allotmentFromDate){
	var flag = true;
	var errorMessage = "";
	if($jq('#driverId'+ival).val()==''){
		errorMessage +="Please Select Driver Or NoDriver\n";
		$jq('#driverId'+ival).focus();
		flag = false;
	}
	if($jq('#fromDate'+ival).val()!='' && $jq('#fromTime'+ival).val()!=''){
		var fdate = convertDate(allotmentFromDate.split(' ')[0]);
		var tdate = convertDate($jq('#fromDate'+ival).val());
		
		if(compareDates(fdate,'dd-MM-yyyy',tdate,'dd-MM-yyyy')==1){
			errorMessage += "Allotment Date Must be Greater Than "+allotmentFromDate.split(' ')[0];
				flag = false;
		}else if(compareDates(tdate,'dd-MM-yyyy',fdate,'dd-MM-yyyy')!=1){
			var ftime=	allotmentFromDate.split(' ')[1];
			var ttime=$jq('#fromTime'+ival).val();
			
			if(compareHoursTime(ftime.split(":")[0],ttime.split(":")[0])==1){
				errorMessage += "Allotment Time Must be Greater Than "+allotmentFromDate.split(' ')[1];
				flag = false;
			}else if(compareHoursTime(ftime.split(":")[0],ttime.split(":")[0])==0){
				if(compareMinutesTime($jq.trim(ftime.split(":")[1]),$jq.trim(ttime.split(":")[1]))==1 || compareMinutesTime($jq.trim(ftime.split(":")[1]),$jq.trim(ttime.split(":")[1]))==0){
					errorMessage += "Allotment Time Must be Greater Than "+allotmentFromDate.split(' ')[1];
					flag = false;
				}
		}
		}	
	}
	if(flag){
	var requestDetails = {
			"param" : "ChangeDriver",
			"vehicleId" : previousVid,
			"pk" :mapId,
			"driverId" : $jq('#driverId'+ival).val(),
			"fromDate" : $jq('#fromDate'+ival).val(),
			"fromTime" : $jq('#fromTime'+ival).val()
	};
	$jq.post('transport.htm', requestDetails, function(html) {
		$jq("#result").html(html);
	});
	
	// clearVehicleDriverMapDetails(ival);
	}else{
		alert(errorMessage);
	}	
}

function clearVehicleDriverMapDetails(ival){
	$jq('#driverId'+ival).val('0');
	$jq('#pk').val('');
	$jq('#type').val('');
	$jq('input:text').val('');
}


function getVehicleReturn(typeValue){
	if(typeValue=='YES'){
	
	$jq('#vehicleReturndiv').css("display","block");
	}else{
		$jq('#vehicleReturndiv').css("display","none");
		$jq('#vehicleReturndiv').find('input').val('');
		$jq('#vehicleReturndiv').find('input:radio').attr("checked",false);
		$jq('#vehicleReturndiv').find('select').val('0');
		$jq('#returnarticlediv').html('');
		
	}
	
}
function getReturnArticle(typeValue){
	$jq('#returnarticlediv').html('');
	if(typeValue=='YES'){
	$jq('#returnarticlediv').append($jq('<div class="line">'+'<fieldset><legend><strong><font color="#10519A"> Return Article Details</font></strong></legend>'+
			'<table width="100%" border="2px" cellpadding="0px" cellspacing="0px" align="center">'+
				'<tr>'+
					'<th>Article Type</th>'+
					'<th>Article Length</th>'+
					'<th>Article Breadth</th>'+
					'<th>Article Height</th>'+
					'<th>Article Weight</th>'+
					'<th>Article Quantity</th>'+
					'<th>Add</th>'+
					'<th>Del</th>'+
				'</tr>'+
				'<tr id="returnRow0">'+
					'<td><select name="returnArticleType" id="returnArticleType">'+
							'<option value="Non-Explosive">Non-Explosive</option>'+
							'<option value="Explosive">Explosive</option>'+
						'</select>'+
					'</td>'+
					'<td><input type="text" name="returnLength" id="returnLength"  onkeypress="return checkFloat(event,\'returnLength\');" /></td>'+
					'<td><input type="text" name="returnBreadth" id="returnBreadth"  onkeypress="return checkFloat(event,\'returnBreadth\');"/></td>'+
					'<td><input type="text" name="returnHeight" id="returnHeight" onkeypress="return checkFloat(event,\'returnHeight\');" /></td>'+
					'<td><input type="text" name="returnWeight" id="returnWeight"  onkeypress="return checkFloat(event,\'returnWeight\');" /></td>'+
					'<td><input type="text" name="returnQuantity" id="returnQuantity"  onkeypress="return checkInt(event)"/></td>'+
					'<td><input type="button"  id="add" value="+" onclick="javascript:insertReturnNewRow(\'0\',\'returnarticlediv\');"/></td>'+
					'<td><input type="button"  id="delete" value="-" onclick="javascript:deleteReturnRow(\'0\',\'returnarticlediv\');"/></td>'+
				'</tr>'+
			'</table>'+
			'</fieldset></div>'
			)).css("display","block");	
	 }
	if(typeValue=='NO'){
	$jq('#returnarticlediv').html('');	
	}
}
function insertReturnNewRow(rowIndex,divId){
/*
 * if(divId == "returnarticlediv"){ createWorkFlowReturnRow(rowIndex); }
 */
var flag = false;
	
	$jq('#returnarticlediv').find('tr:not(:first)').each(
			function(){
				$jq(this).find('td').each(
						function(){
				// ($jq(this).find('select').is(':visible') &&
				// $jq(this).find('select').val()!='0')
				// && $jq(this).find('input').val()!='+' &&
				// $jq(this).find('input').val()!='-')
					if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()!=''){
					flag = true;
				}
						})
			}
		)
		if(flag == false){
			alert("Please Enter First Row");
		}
	if(flag==true){
	if(divId == "returnarticlediv"){
		createWorkFlowReturnRow(rowIndex);
	}
	}
}
var returnCount=0;
function createWorkFlowReturnRow(rowIndex){
	returnCount++;
	var row = "<tr id=returnRow"+returnCount+">";
	row +=	 "<td><select name=returnArticleType id=returnArticleType"+returnCount+">";
	row +=			'<option value="Non-Explosive">Non-Explosive</option>'+
					'<option value="Explosive">Explosive</option>';
	row +=		"</select>";
	row +=	 "</td>";
	row +=	 "<td><input type=text name=returnLength onkeypress='return checkFloat(event,\'returnLength\');' id=returnLength"+returnCount+"  /></td>";
	row +=	 "<td><input type=text name=returnBreadth onkeypress='return checkFloat(event,\'returnBreadth\');' id=returnBreadth"+returnCount+" /></td>";
	row +=	 "<td><input type=text name=returnHeight onkeypress='return checkFloat(event,\'returnHeight\');' id=returnHeight"+returnCount+" /></td>";
	row +=	 "<td><input type=text name=returnWeight onkeypress='return checkFloat(event,\'returnWeight\');' id=returnWeight"+returnCount+" /></td>";
	row +=	 "<td><input type=text name=returnQuantity onkeypress='return checkInt(event)' id=returnQuantity"+returnCount+"  /></td>";
	row +=	 "<td><input type=button  id=add value=+ onclick=insertReturnNewRow('"+returnCount+"',\'returnarticlediv\'); /></td>";
	row +=	 "<td><input type=button  id=delete value=- onclick=deleteReturnRow('"+returnCount+"',\'returnarticlediv\'); /></td>";
	row +="</tr>";
	$jq("#returnRow" + rowIndex).after(row);
	
		
}

function deleteReturnRow(rowIndex,divId){
	if ($jq('#' + divId + ' tr').length <= 2) {
		alert("Sorry you cannot delete the last row");
	} else {
		var del = confirm("Do you want to delete this item?");
		if (del == true) {
			$jq('#returnRow' + rowIndex).remove();
			returnCount--;
		}
	}
}
function clearRequestFormDetails(){
	
	$jq('input:text').val('');
	$jq("input:radio").attr("checked", false);
	$jq('select').val(0);
}

function saveRequestFormDetails(type){
	var flag = true;
	var errorMessage = "";
	var mainArticleJson ={};
	if($jq('#requestedFor').val()==""){
		errorMessage += "Please Enter Requested For Name \n";
		$jq('#requestedFor').focus();
		flag=false;
	}
	if($jq('#mobileNo').val()==""){
		errorMessage += "Please Enter Mobile No. \n";
		$jq('#mobileNo').focus();
		flag=false;
	}
	if($jq('#noOfPeople').val()==""){
		errorMessage += "Please Enter No. Of People \n";
		$jq('#noOfPeople').focus();
		flag=false;
	}
	if($jq('#purposeOfVisiting').val()==""){
		errorMessage += "Please Enter Purpose Of Visiting \n";
		$jq('#purposeOfVisiting').focus();
		flag=false;
	}
	if($jq('#travellingDate').val()==""){
		errorMessage += "Please Select Travelling Date & Time \n";
		$jq('#travellingDate').focus();
		flag=false;
	}
	if($jq('#estimatedDateTime').val()==""){
		errorMessage += "Please Select Estimated Date & Time  \n";
		$jq('#estimatedDateTime').focus();
		flag=false;
	}
	if($jq('#travellingDate').val()!='' && $jq('#estimatedDateTime').val()!=''){
		var fdate = $jq('#travellingDate').val();
		var tdate = $jq('#estimatedDateTime').val();
		date1 = convertDate(fdate.split(" ")[0]);
		date2 = convertDate(tdate.split(" ")[0]);

		date1 = createDDMMYYY(date1);
		date2 = createDDMMYYY(date2);
		
		
		if(date1>date2){
			errorMessage += "Estimated Date Must be Greater Than Travelling Date \n"
				flag = false;
		}else if(date1.equalsTo(date2)){
			var ftime = fdate.split(" ")[1].split("-");
			var ttime = tdate.split(" ")[1].split("-");
			if(ftime[0]>ttime[0]){
					errorMessage += "Estimated Time Must be Greater Than Travelling Time \n"
					flag = false;
			}else if(ftime[0]==ttime[0]){
				if(ftime[1]>=ttime[1]){
						errorMessage += "Estimated Time Must be Greater Than Travelling Time \n"
						flag = false;
				}
			}
		}
	 }
	if($jq('#pickupPoint').val()=="0"){
		errorMessage += "Please Select Pickup Point \n";
		$jq('#pickupPoint').focus();
		flag=false;
	}
	if($jq('#droppingPoint').val()=="0"){
		errorMessage += "Please Select Dropping Point \n";
		$jq('#droppingPoint').focus();
		flag=false;
	}
	if($jq('#pickupPoint').val() == $jq('#droppingPoint').val()){
		if($jq('#pickupPoint').val() !=0 && $jq('#droppingPoint').val() !=0){
			if($jq('#otherSourceName').val()=='' || $jq('#otherDestiName').val()==''){
				errorMessage += "Pickup Point & Dropping Point Are Same, So Please Enter Source Place & Destination Place \n";
				flag = false;
				$jq('#otherSourceName').focus();
				$jq('#otherDestiName').focus();
			}
		}
	}
	if($jq('#articleCarry1').is(':checked')==false && $jq('#articleCarry2').is(':checked')==false  ){
		errorMessage += "Please Select If Any Article to be Carry or Not \n";
		flag = false;
	}
	var count=0;
	if($jq('#articleCarry1').is(':checked')){
		$jq('#articleTable').find('tr:not(:first)').each(
				function(){
					$jq(this).find('td').each(
							function(){
						if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()==''){
							count++;
						}
						})
						if(count==5){
							errorMessage += "Please Enter Article Dimensions \n";
							flag = false;
						}
				}
				
			)		
	}
	if($jq('#accommodation1').is(':checked')==false && $jq('#accommodation2').is(':checked')==false  ){
		errorMessage += "Please Select If Any Accommodation Required or Not \n";
		flag = false;
	}
	if($jq('#vehicleReturn1').is(':checked')==false && $jq('#vehicleReturn2').is(':checked')==false  ){
		errorMessage += "Please Select Whether Vehicle Required for Return Journey or Not \n";
		flag = false;
	}
	if($jq('#accommodation1').is(':checked')==true){
		if($jq('#accPlace').val()==""){
			errorMessage += "please Enter Accommodation Place Name \n";
			$jq('#accPlace').focus();
			flag = false;
		}
	}
	if($jq('#vehicleReturn1').is(':checked')==true){
		if($jq('#vehicleReturnSame1').is(':checked')==false && $jq('#vehicleReturnSame2').is(':checked')==false){
			errorMessage += "please Select Whether Same Vehicle Required for Return Journey or Not \n";
			flag = false;
		}
		if($jq('#returnPeople').val()==""){
			errorMessage += "Please Enter Return No. Of People \n";
			$jq('#returnPeople').focus();
			flag=false;
		}
		if($jq('#returnDate').val()==""){
			errorMessage += "Please Select Return Travelling Date & Time \n";
			$jq('#returnDate').focus();
			flag=false;
		}
		if($jq('#returnEstimatedDateTime').val()==""){
			errorMessage += "Please Select Return Estimated Date & Time \n";
			$jq('#returnEstimatedDateTime').focus();
			flag=false;
		}
		if($jq('#returnDate').val()!='' && $jq('#returnEstimatedDateTime').val()!=''){
			var fdate = $jq('#returnDate').val();
			var tdate = $jq('#returnEstimatedDateTime').val();
			date1 = convertDate(fdate.split(" ")[0]);
			date2 = convertDate(tdate.split(" ")[0]);

			date1 = createDDMMYYY(date1);
			date2 = createDDMMYYY(date2);
			
			
			if(date1>date2){
				errorMessage += "Return Estimated Date Must be Greater Than Return Travelling Date \n"
					flag = false;
			}else if(date1.equalsTo(date2)){
				var ftime = fdate.split(" ")[1].split("-");
				var ttime = tdate.split(" ")[1].split("-");
				
				if(ftime[0]>ttime[0]){
					errorMessage += "Return Estimated Time Must be Greater Than Return Travelling Time \n"
					flag = false;
			    }else if(ftime[0]==ttime[0]){
				if(ftime[1]>=ttime[1]){
						errorMessage += "Return Estimated Time Must be Greater Than Return Travelling Time \n"
						flag = false;
				}
			}
			}
		 }
		if($jq('#returnDate').val()!='' && $jq('#returnEstimatedDateTime').val()!='' && $jq('#travellingDate').val()!='' && $jq('#estimatedDateTime').val()!=''){
			var tdate = $jq('#travellingDate').val();
			var edate = $jq('#estimatedDateTime').val();
			var rtdate = $jq('#returnDate').val();
			var redate = $jq('#returnEstimatedDateTime').val();
			if(dateComprision(tdate,rtdate) || dateComprision(tdate,redate) || dateComprision(edate,rtdate) || dateComprision(edate,redate)){
				errorMessage += "Return Journey Dates Must Be Greater Than Travelling Dates \n"
				flag = false;
			}
		}
		if($jq('#returnPickUpPoint').val()==""){
			errorMessage += "Please Select Return Pickup Point \n";
			$jq('#returnPickUpPoint').focus();
			flag=false;
		}
		if($jq('#returnDroppingPoint').val()==""){
			errorMessage += "Please Select Return Dropping Point \n";
			$jq('#returnDroppingPoint').focus();
			flag=false;
		}
		if($jq('#returnPickUpPoint').val() == $jq('#returnDroppingPoint').val()){
			if($jq('#returnPickUpPoint').val() !=0 && $jq('#returnDroppingPoint').val() !=0){
				if($jq('#returnOtherSourceName').val()=='' || $jq('#returnOtherDestiName').val()==''){
					errorMessage += "Pickup Point & Dropping Point Are Same, So Please Enter Return Source Place & Return Destination Place \n";
					flag = false;
					$jq('#returnOtherSourceName').focus();
					$jq('#returnOtherDestiName').focus();
				}
			}
		}
		if($jq('#returnArticleCarry1').is(':checked')==false && $jq('#returnArticleCarry2').is(':checked')==false  ){
			errorMessage += "Please Select If Any Article to be Carry or Not \n";
			flag = false;
		}
		var returnCount=0;
		if($jq('#returnArticleCarry1').is(':checked')){
			$jq('#returnarticlediv').find('tr:not(:first)').each(
					function(){
						$jq(this).find('td').each(
								function(){
							if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()==''){
								returnCount++;
							}
							})
							if(returnCount==5){
								errorMessage += "Please Enter Return Article Dimensions \n";
								flag = false;
							}
					}
					
				)		
		}
	}
	if($jq('#articlediv').is(':visible')==true){
		var tableJson={};
		var i=0;
		
		 $jq('#articlediv').find('fieldset').each(function(){
	    				$jq(this).find('table tr:not(:first)').each(function(){
	    					var rowJson = {};
	    					rowJson['articleType']=$jq(this).find('td').eq(0).find('select').val();
	    					rowJson['length']=$jq(this).find('td').eq(1).find('input').val();
	    					rowJson['breadth']=$jq(this).find('td').eq(2).find('input').val();
	    					rowJson['height']=$jq(this).find('td').eq(3).find('input').val();
	    					rowJson['weight']=$jq(this).find('td').eq(4).find('input').val();
	    					rowJson['quantity']=$jq(this).find('td').eq(5).find('input').val();
	    					tableJson[i]=rowJson;
	    					i++;
	    				});
	    			});
		mainArticleJson['takingArticles']=tableJson;	
	}
	if($jq('#returnarticlediv').is(':visible')==true){
		var tableJson={};
		var i=0;
		
		 $jq('#returnarticlediv').find('fieldset').each(function(){
	    				$jq(this).find('table tr:not(:first)').each(function(){
	    					var rowJson = {};
	    					rowJson['articleType']=$jq(this).find('td').eq(0).find('select').val();
	    					rowJson['length']=$jq(this).find('td').eq(1).find('input').val();
	    					rowJson['breadth']=$jq(this).find('td').eq(2).find('input').val();
	    					rowJson['height']=$jq(this).find('td').eq(3).find('input').val();
	    					rowJson['weight']=$jq(this).find('td').eq(4).find('input').val();
	    					rowJson['quantity']=$jq(this).find('td').eq(5).find('input').val();
	    					tableJson[i]=rowJson;
	    					i++;
	    				});
	    			});
		mainArticleJson['returnArticles']=tableJson;	
	}
	if(flag){
	
		$jq('#param').val('SaveVehicleRequestFormDetails');
		$jq('#articleJson').val(JSON.stringify(mainArticleJson));
		if(type=='Offline'){
			$jq('#type').val(type);
		}
		$jq.post('transport.htm', $jq('#mtBean').serialize(), function(html) {
			$jq("#result").html('');
			$jq("#result").html(html);
		});
		
		clearRequestFormDetails();
		
	}else{
		alert(errorMessage);
	}
}
function dateComprision(fdate,tdate){
	var flag = true;
	date1 = convertDate(fdate.split(" ")[0]);
	date2 = convertDate(tdate.split(" ")[0]);

	date1 = createDDMMYYY(date1);
	date2 = createDDMMYYY(date2);
	
	
	if(date1>date2){
		return flag;
	}else if(date1.equalsTo(date2)){
		var ftime = fdate.split(" ")[1].split("-");
		var ttime = tdate.split(" ")[1].split("-");
		if(ftime[0]>=ttime[0]){
			if(ftime[1]>=ttime[1]){
				return flag;
			}
		}
	}
}
// for dashboard
function showVehicleAllocationDiv(){
	$jq('#vehicleAllocationDiv').css('display','block');
}
function onChangeVehicle(map){
	var mapId = $jq('#'+map).val();
	if(mapId !=0){
		for(var i=0; i<vehicleNoJson.length; i++){
			if(vehicleNoJson[i].id == mapId){
				$jq('#vehicleNoId').val('');
				$jq('#driverNameId').val('');
				$jq('#vehicleNoId').val(vehicleNoJson[i].vehicleDetails.vehicleNo);
				$jq('#driverNameId').val(vehicleNoJson[i].diverDetails.driverName);
				if($jq('#returnVehicleType1').is(':checked')){
					$jq('#returnVehicleNoId').val($jq('#vehicleNoId').val());
					$jq('#returnDriverNameId').val($jq('#driverNameId').val());
				}else{
					$jq('#returnVehicleNoId').val('');
					$jq('#returnDriverNameId').val('');
				}
			}
		}	
	}else{
		$jq('#vehicleNoId').val('');
		$jq('#driverNameId').val('');
		$jq('#returnVehicleNoId').val('');
		$jq('#returnDriverNameId').val('');
	}
	
}
function onselectHiredVehicle(){
	var id=$jq('#hiredVehicleDriverMapId').val();
	if(id !=0){
		for(var i=0; i<vehicleHiredJson.length; i++){
			if(vehicleHiredJson[i].vehicleId == id){
				$jq('#vehicleNoId').val('');
				$jq('#driverNameId').val('');
				$jq('#vehicleNoId').val(vehicleHiredJson[i].vehicleNo);
				$jq('#driverNameId').val(vehicleHiredJson[i].driverName);
			}
		}
	}else{
		$jq('#vehicleNoId').val('');
		$jq('#driverNameId').val('');
	}
}
function onselectReturnHiredVehicle(){
	var id=$jq('#returnHiredVehicleDriverMapId').val();
	if(id !=0){
		for(var i=0; i<vehicleHiredJson.length; i++){
			if(vehicleHiredJson[i].vehicleId == id){
				$jq('#returnVehicleNoId').val('');
				$jq('#returnDriverNameId').val('');
				$jq('#returnVehicleNoId').val(vehicleHiredJson[i].vehicleNo);
				$jq('#returnDriverNameId').val(vehicleHiredJson[i].driverName);
			}
		}
	}else{
		$jq('#returnVehicleNoId').val('');
		$jq('#returnDriverNameId').val('');
	}
}
function onChangeReturnVehicle(map){
	var mapId = $jq('#'+map).val();
	if(mapId !=0){
		for(var i=0; i<vehicleNoJson.length; i++){
			if(vehicleNoJson[i].id == mapId){
				$jq('#returnVehicleNoId').val('');
				$jq('#returnDriverNameId').val('');
				$jq('#returnVehicleNoId').val(vehicleNoJson[i].vehicleDetails.vehicleNo);
				$jq('#returnDriverNameId').val(vehicleNoJson[i].diverDetails.driverName);
			}
		}
	}else{
		$jq('#returnVehicleNoId').val('');
		$jq('#returnDriverNameId').val('');
	}
	
}
function onselectBusyVehicle(ddId,divId){
	
	if($jq('#'+ddId).val() !=0){
	var temp ="";
	var mapId = $jq('#'+ddId).val();
	var vehicleNo = "";
	for(var i=0; i<vehicleDriverJson.length; i++){
		if(mapId==vehicleDriverJson[i].vehicleDriverMapId){
			vehicleNo = vehicleDriverJson[i].vehicleDriverMapDetails.vehicleDetails.vehicleNo;
			temp +=	'<tr>';
			temp +='<td width="">'+vehicleDriverJson[i].stringFromDate+'</td>';
			temp +='<td width="">'+vehicleDriverJson[i].stringToDate+'</td>';
			temp +='<td width="">'+vehicleDriverJson[i].vehicleStatus+'</td>';
			temp +='</tr>';
		}
	}
	$jq('#'+divId).html('');
	$jq('#'+divId).append($jq(
												'<table width="100%" cellpadding="3" cellspacing="0" border="1">'
												+'<tr>'
													+'<th  colspan="3">'+vehicleNo+'(Vehicle Timeings)</th>'
												+'</tr>'
											
												+'<tr>'
													+'<th width="">From Date & Time</th>'
													+'<th width="">To Date & Time</th>'
													+'<th width="">Status</th>'
												+'</tr>'
												+temp
												+'</table>'
												+'</fieldset>'
										)).css('display','block');	
	}else{
		$jq('#'+divId).html('');
	}
 
}



function showArticleDetails(){
	$jq('#hideArticleDiv').toggle();
	$jq('#showArticleDiv').toggle();
	$jq('#showArticleDetailsDiv').toggle();
}
function showReturnJourneyDetails(){
	$jq('#hideReturnJourneyDiv').toggle();
	$jq('#showReturnJourneyDiv').toggle();
	$jq('#showReturnJourneyDetailsDiv').toggle();
}
function showReturnArticleDetails(){
	$jq('#hideReturnArticleDiv').toggle();
	$jq('#showReturnArticleDiv').toggle();
	$jq('#showReturnArticleDetailsDiv').toggle();
}

function getReturnVehicleAllocationDiv(type){
	if(type=='YES'){
		if($jq('#freeVehicleDriverMapId').val() !=0){
			var mapId = $jq('#freeVehicleDriverMapId').val();
			var count = 0;
			for(var i=0; i<returnVehicleJson.length; i++){
				if(mapId==returnVehicleJson[i].id){
					$jq('#returnVehicleAllocationDiv').css('display','none');
					document.forms[0].returnVehicleType.value='1'; // used in
																	// requestWorkflowController
					$jq('#returnVehicleNoId').val('');
					$jq('#returnDriverNameId').val('');
					$jq('#returnVehicleNoId').val($jq('#vehicleNoId').val());
					$jq('#returnDriverNameId').val($jq('#driverNameId').val());
					count++;
				}
			}
			if(count==0){
				alert("Sorry, Vehicle Busy In That Time,Choose Other Vehicle");
			}
			
		}else{
			alert("Please Select Travelling Vehicle First");
			$jq('#returnVehicleAllocationDiv').css('display','none');
			$jq("input:radio").attr("checked", false);
		}
		
		
	}else if(type=='NO'){
		document.forms[0].returnVehicleType.value='0'; // used in
														// requestWorkflowController
		$jq('#returnVehicleNoId').val('');
		$jq('#returnDriverNameId').val('');
		$jq('#returnVehicleAllocationDiv').css('display','block');
	}
}
// for Mileage
function getPOLDiv(type){
	if(type=='YES'){
		$jq('#POLDiv').css('display','block');
	}else if(type=='NO'){
		$jq('#fuel').val('');
		$jq('#fuelMRD').val('');
		$jq('#POLDiv').css('display','none');
	}
}
function getEngOilDiv(type){
	if(type=='YES'){
		$jq('#EngOilDiv').css('display','block');
	}else if(type=='NO'){
		$jq('#engineOilMRD').val('');
		$jq('#engineOil').val('');
		$jq('#EngOilDiv').css('display','none');
	}
}
function getCoolentDiv(type){
	if(type=='YES'){
		$jq('#CoolentDiv').css('display','block');
	}else if(type=='NO'){
		$jq('#coolent').val('');
		$jq('#coolentMRD').val('');
		$jq('#CoolentDiv').css('display','none');
	}
}

function saveMileageDetails(){
	var flag = true;
	var errorMessage = "";
	
	if($jq('#todayDate').val()==''){
		errorMessage+="Please Select Date \n";
		$jq('#todayDate').focus();
		flag = false;
	}
	if($jq('#vehicleNo').val()==0){
		errorMessage+="Please Select Vehicle No \n";
		$jq('#vehicleNo').focus();
		flag = false;
	}
	if($jq('#natureOfDuty').val()==''){
		errorMessage+="Please Enter Nature Of Duty \n";
		$jq('#natureOfDuty').focus();
		flag = false;
	}
	
	if($jq('#startingMilometerRde').val()==''){
		errorMessage+="Please Enter Starting Kilometer Reading \n";
		$jq('#startingMilometerRde').focus();
		flag = false;
	}
	if($jq('#totKilometers').val()==''){
		errorMessage+="Please Enter Total Kilometers \n";
		$jq('#totKilometers').focus();
		flag = false;
	}
	
	if($jq('#endingMilometerRde').val()==''){
		errorMessage+="Please Enter Ending Kilometer Reading  \n";
		$jq('#endingMilometerRde').focus();
		flag = false;
	}
	if($jq('#fuel1').is(":checked")==false && $jq('#fuel2').is(":checked")==false){
		errorMessage+="Please Select POL Drawn or Not  \n";
		flag = false;
	}
	if($jq('#engineOil1').is(":checked")==false && $jq('#engineOil2').is(":checked")==false){
		errorMessage+="Please Select Eng. Oil Drawn or Not  \n";
		flag = false;
	}
	if($jq('#coolent1').is(":checked")==false && $jq('#coolent2').is(":checked")==false){
		errorMessage+="Please Select Coolent Drawn or Not  \n";
		flag = false;
	}
	if($jq('#fuel1').is(":checked")==true){
		if($jq('#fuel').val()==''){
			errorMessage+="Please Enter No Of Ltr Of Fuel  \n";
			$jq('#fuel').focus();
			flag = false;
		}
		if($jq('#fuelMRD').val()==''){
			errorMessage+="Please Fuel Enter Meter Reading  \n";
			$jq('#fuelMRD').focus();
			flag = false;
		}
	}
	if($jq('#engineOil1').is(":checked")==true){
		if($jq('#engineOil').val()==''){
			errorMessage+="Please Enter No Of Ltr Of Engine Oil \n";
			$jq('#engineOil').focus();
			flag = false;
		}
		if($jq('#engineOilMRD').val()==''){
			errorMessage+="Please Enter EngineOil Meter Reading  \n";
			$jq('#engineOilMRD').focus();
			flag = false;
		}
	}
	if($jq('#coolent1').is(":checked")==true){
		if($jq('#coolent').val()==''){
			errorMessage+="Please Enter No Of Ltr Of Coolent \n";
			$jq('#coolent').focus();
			flag = false;
		}
		if($jq('#coolentMRD').val()==''){
			errorMessage+="Please Enter Coolent Meter Reading  \n";
			$jq('#coolentMRD').focus();
			flag = false;
		}
	}
	if($jq('#startingMilometerRde').val()!='' && $jq('#endingMilometerRde').val()!=''){
		if(parseInt($jq('#endingMilometerRde').val())<=parseInt($jq('#startingMilometerRde').val())){
			errorMessage+="Ending Milometer Rde Must be Greater Than Starting Milometer Rde\n";
			flag = false;
			
		}
	}
	if(flag){
		$jq('#param').val('SAVEMILEAGE');
		$jq.post('transport.htm',$jq('#mtBean').serialize(),function(html){
			$jq("#result").html(html);
		});	
		clearMileageDetails();
		
	}else{
		alert(errorMessage);
	}
}
function kilometerDiff(){
	var total =0;
	if(parseInt($jq('#endingMilometerRde').val()) > parseInt($jq('#startingMilometerRde').val())){
		total += (parseInt($jq('#endingMilometerRde').val()) - parseInt($jq('#startingMilometerRde').val()));
		$jq('#totKilometers').val(total);
	}else{
		
		$jq('#totKilometers').val('');
	}
	
}
function clearMileageDetails(){
	$jq('#param').val('');
	$jq('#pk').val('');
	$jq('#todayDate').val('');
	$jq('#vehicleNo').val(0);
	$jq('#natureOfDuty').val('');
	$jq('#fromPlace').val('');
	$jq('#totKilometers').val('');
	$jq('#toPlace').val('');
	$jq('#startingMilometerRde').val('');
	$jq('#endingMilometerRde').val('');
	$jq('#fuel').val('');
	$jq('#engineOil').val('');
	$jq('#coolent').val('');
	$jq('#fuelMRD').val('');
	$jq('#engineOilMRD').val('');
	$jq('#coolentMRD').val('');
	$jq('input:radio').attr("checked",false);
	$jq('#FKPL').text('');
	$jq('#EKPL').text('');
	$jq('#CKPL').text('');
}

function editMileageDetails(id,date,vId,nod,fPlace,tPlace,smr,emr,tk,fuel,eOil,cool,fMRD,eMRD,cMRD,fc,ec,cc){
	$jq('#pk').val(id);
	$jq('#todayDate').val(date);
	$jq('#vehicleNo').val(vId);
	$jq('#natureOfDuty').val(nod);
	$jq('#fromPlace').val(fPlace);
	$jq('#toPlace').val(tPlace);
	$jq('#startingMilometerRde').val(smr);
	$jq('#endingMilometerRde').val(emr);
	$jq('#totKilometers').val(tk);
	if(fc !=0){
		getPOLDiv('YES');
		$jq('#fuel1').attr("checked",true);
		$jq('#fuel').val(fuel);
		$jq('#fuelMRD').val(fMRD);
	}else{
		$jq('#fuel2').attr("checked",true);
		getPOLDiv('NO');
	}
	
	$jq('#totKilometers').val(tk);
	if(ec !=0){
		getEngOilDiv('YES');
		$jq('#engineOil1').attr("checked",true);
		$jq('#engineOil').val(eOil);
		$jq('#engineOilMRD').val(eMRD);
	}
	else{
		$jq('#engineOil2').attr("checked",true);
		getEngOilDiv('NO');
	}
	if(cc !=0){
		getCoolentDiv('YES');
		$jq('#coolent1').attr("checked",true);
		$jq('#coolent').val(cool);
		$jq('#coolentMRD').val(cMRD);
	}
	else{
		$jq('#coolent2').attr("checked",true);
		getCoolentDiv('NO');
	}
	
	
}

function deleteMileageDetails(id){
	var check=confirm("Do you want to delete ?");
	$jq('#param').val('DeleteMileage');
	$jq('#pk').val(id);
	if(check){
	$jq.post('transport.htm',$jq('#mtBean').serialize(),function(html){
		$jq("#result").html(html);
	});	
	}
	clearMileageDetails();
}

function releaseSelectedVehicles(){
	var allocVehicleIdList = "";
	var cid ="";
	var flag = true;
	var errMsg ="";
	var vid="";
	$jq('#vehicle').find('tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(5).find('input:checkbox').attr('checked')==true){
			cid = $jq(this).find("td").eq(5).find("input:checkbox").attr("id");
			if(cid.indexOf("@")>0){
				vid = cid.split("@")[0];
				if($jq('#fromDate').val() == ''){
					errMsg += "Please Select From Date & Time \n";
					$jq('#fromDate').focus();
					flag = false;
				}
				if($jq('#toDate').val() == ''){
					errMsg += "Please Select To Date & Time \n";
					$jq('#toDate').focus();
					flag = false;
				}
				if(flag){
					allocVehicleIdList += $jq(this).find("td").eq(5).find("input:checkbox").attr("id")+"@@"+$jq('#fromDate'+vid).val()+"@@"+$jq('#toDate'+vid).val()+",";	
				}
			}if(cid.indexOf("#")>0){
				if($jq('#todayDate'+cid.split("#")[0]).val() == ''){
					errMsg += "Please Select Released Date & Time \n";
					$jq('#todayDate'+cid.split("#")[0]).focus();
					flag = false;
				}
				if(flag){
					allocVehicleIdList += $jq(this).find("td").eq(5).find("input:checkbox").attr("id")+"@" +$jq('#todayDate'+cid.split("#")[0]).val()+",";	
				}
				
			}
			
		}
	});
	if(flag){
		if(allocVehicleIdList !=''){
			$jq('#param').val('ReleaseSelectedVehicle');
			$jq('#pk').val(allocVehicleIdList);
			$jq.post('transport.htm',$jq('#mtBean').serialize(),function(html){
				$jq("#result1").html(html);
			});	
			}else{
				alert("please Select Atleast One Vehicle \n");
			}
	}else{
		alert(errMsg);
	}
	
}
function releaseAllVehicles(){
	var allocVehicleIdList = "";
	var cid ="";
	var flag = true;
	var errMsg ="";
	var vid="";
	var mapId="";
	
	var releasedAllVehiclesJSON={};
	var rowNo=0;
	
	$jq('#vehicle').find('tr:not(:first)').each(function(){
		var subJson={};
		if($jq(this).find('td').eq(7).find('input:checkbox').attr('checked')==true){
			cid = $jq(this).find("td").eq(7).find("input:checkbox").attr("id");
				vid = cid.split("_")[0];
				if($jq('#todayDate'+vid).val() == ''){
					errMsg += "Please Select Released Date \n";
					$jq('#todayDate'+vid).focus();
					flag = false;
				}
				if($jq('#fromTime'+vid).val() == ''){
					errMsg += "Please Select Released Time \n";
					$jq('#fromTime'+vid).focus();
					flag = false;
				}
				
				//for time check(formate and time)
				if($jq('#fromTime'+vid).val()!=''){
				if($jq('#fromTime'+vid).val().length!=5 || $jq('#fromTime'+vid).val().indexOf(':')==-1){
					errMsg += "Please Enter Time In HH:MM formate\n";
					$jq('#fromTime'+vid).focus();
					flag = false;
				}
				if($jq.trim($jq('#fromTime'+vid).val().split(':')[0])>23){
					errMsg += "Invalid Time\n";
					$jq('#fromTime'+vid).focus();
					flag = false;
				}
				if($jq.trim($jq('#fromTime'+vid).val().split(':')[1])>59){
					errMsg += "Invalid Time\n";
					$jq('#fromTime'+vid).focus();
					flag = false;
				}
				}
				//end time check
				
				if(flag){
					subJson['fromDateTime']=$jq(this).find("td").eq(4).text();
					subJson['toDateTime']=$jq(this).find("td").eq(5).text();
					subJson['relesedDateTime']=$jq('#todayDate'+vid).val()+" "+$jq('#fromTime'+vid).val();
					subJson['allocId']=cid.split("_")[0];
					subJson['mapId']=cid.split("_")[1];
					releasedAllVehiclesJSON[rowNo]=subJson;
					rowNo++;
				}
		}
		});
	if(JSON.stringify(releasedAllVehiclesJSON).length == 2 && flag==true){
		errMsg += "Please Select Atleast One Request To Release\n";
		flag = false;
	}
	if(flag){
		
		var requestDetais={
				"param" : "ReleaseAllSelectedVehicles",
				"releasedVehiclesJSON" : JSON.stringify(releasedAllVehiclesJSON)
		};
			$jq.post('transport.htm',requestDetais,function(html){
				$jq("#result1").html(html);
			});
			
	}else{
		alert(errMsg);
	}
		
}

function clearMileageReport(){
	$jq('#vehicleNo').val('');
	$jq('#year').val('');
	$jq('#param').val('');
	$jq('#type').val('');
	$jq('#pk').val('');
}

function getMileageReport(type){
	var flag = true;
	var errMsg ="";
	if($jq('#vehicleNo').val() == '0'){
		flag = false;
		errMsg += "Please Select Vehicle No. \n";
		$jq('#vehicleNo').focus();
	}
	if($jq('#year').val() == '0'){
		flag = false;
		errMsg += "Please Select Year \n";
		$jq('#year').focus();
	}
	if(flag){
		window.open('./report.htm?param=MTReports&type='+type+'&vehicleId='+$jq('#vehicleNo').val()+'&year='+$jq('#year').val(),'fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')	
	}else{
		alert(errMsg);
	}
}


function getDateWiseVDMap(){
	var flag = true;
	var errMsg ="";
	if($jq('#searchFromDate').val() == ''){
		flag = false;
		errMsg += "Please Select From Date \n";
		$jq('#searchFromDate').focus();
	}
	if($jq('#searchFromTime').val() == ''){
		flag = false;
		errMsg += "Please Select From Time \n";
		$jq('#searchFromTime').focus();
	}
	if($jq('#searchToDate').val() == ''){
		flag = false;
		errMsg += "Please Select To Date \n";
		$jq('#searchToDate').focus();
	}
	if($jq('#searchToTime').val() == ''){
		flag = false;
		errMsg += "Please Select To Time s\n";
		$jq('#searchToTime').focus();
	}
	if(flag){
		$jq('#param').val('VehicleVsDriverDetails');
		$jq('#type').val('getDateWiseVDMap');
		$jq.post('transport.htm',$jq('#mtBean').serialize(),function(html){
			$jq('#title').text('Vehicle-Driver Mapping Details');
			$jq("#result").html(html);
		});	
	}else{
		alert(errMsg);
	}
}

function getPreDayClosingRDE(){
	var flag = true;
	var errMsg ="";
	if($jq('#todayDate').val() == ''){
		flag = false;
		errMsg += "Please Select Date  \n";
		$jq('#todayDate').focus();
	}
	if($jq('#vehicleNo').val() == '0'){
		flag = false;
		errMsg += "Please Select Vehicle No. \n";
		$jq('#vehicleNo').focus();
	}
	if(flag){
		$jq('#param').val('getPreDayClosingRDE');
		$jq.post('transport.htm',$jq('#mtBean').serialize(),function(html){
			$jq("#result").html(html);
		});
	}else{
		$jq('#vehicleNo').val('');
		alert(errMsg);
	}
}
var pFuelMRD =0;
var pEngineOilMRD=0;
var pCoolentMRD =0;
var pFuel =0;
var pEngineOil =0;
var pCoolent =0;
function getFuelKPL(){
	if($jq('#fuelMRD').val() !='' && $jq('#fuelMRD').val() > pFuelMRD){
		var avgFKPL = ($jq('#fuelMRD').val() - pFuelMRD)/pFuel;
		$jq('#FKPL').text(avgFKPL);
	}else{
	alert("Please Enter Greater  Fuel Meter Reading Than Previous Meter Reading");
	$jq('#fuelMRD').val('');
	$jq('#FKPL').text('');
	$jq('#fuelMRD').focus();
	}
}
function getEngineOilKPL(){
	if($jq('#engineOilMRD').val() !='' && $jq('#engineOilMRD').val() > pEngineOilMRD){
		var avgEKPL = ($jq('#engineOilMRD').val() - pEngineOilMRD)/pEngineOil;
		$jq('#EKPL').text(avgEKPL);
	}else{
	alert("Please Enter Greater EngineOil Meter Reading Than Previous Meter Reading");
	$jq('#engineOilMRD').val('');
	$jq('#EKPL').text('');
	$jq('#engineOilMRD').focus();
	}
}
function getCoolentKPL(){
	if($jq('#coolentMRD').val() !='' && $jq('#coolentMRD').val() > pCoolentMRD){
		var avgCKPL = ($jq('#coolentMRD').val() - pCoolentMRD)/pCoolent;
		$jq('#CKPL').text(avgCKPL);
	}else{
	alert("Please Enter Greater Coolent Meter Reading Than Previous Meter Reading");
	$jq('#coolentMRD').val('');
	$jq('#CKPL').text('');
	$jq('#coolentMRD').focus();
	}
}
function timePicker(id) {
	$jq(function(){
		$jq('.'+id).each(function(i){
			$jq('.'+id).timepicker({
				// stepMinute: 10
				// hourMin: 5,
				// minuteMin:10
			});
			eval($jq(this).text());
		});
	});
	
}
function timePicker2(id,rowNo) {
	var minHour=0;
	var minMinute=0;
	var temp =1;
		
	     if($jq('#startDate'+rowNo).val()==$jq('#estDate'+rowNo).val()){
	    	 if($jq('#startTime'+rowNo).val()!=''){
			  minHour=parseInt($jq('#startTime'+rowNo).val().split(':')[0].trim());
			  minMinute=parseInt($jq('#startTime'+rowNo).val().split(':')[1].trim());
	    	 }
		}
	
	$jq(function(){
		
		// $jq('#estTime0').val('');
		 temp =2;
		$jq('.'+id).each(function(i){
			temp = temp+1;
			$jq('.'+id).timepicker({
				// stepMinute: '10',
				// stepHour : '1',
				hourMin: temp,
				minuteMin: minMinute
			});
			
			eval($jq(this).text());
		});
	});
	
}
// for Onward Journey
function funcreatenewJourneyRow(idvalue)
{
	//for diasable add button 
	
	
	var errMessage="";
	if($jq('#passengNo'+idvalue).val()=="" || $jq('#passengName'+idvalue).val()=="" || $jq('#startDate'+idvalue).val()=="" || $jq('#startTime'+idvalue).val()=="" || $jq('#estDate'+idvalue).val()=="" || $jq('#estTime'+idvalue).val()=="" || $jq('#pickupPoint'+idvalue).val()=="Select" || $jq('#droppingPoint'+idvalue).val()=="Select"){
		errMessage += "Please Fill All Mandatory Fields Of Row"+idvalue+" in Onward Journey Grid\n";
		alert(errMessage);
	}else{
		if($jq('#accomReq1'+idvalue).is(':checked')==false && $jq('#accomReq2'+idvalue).is(':checked')==false){
			errMessage += "Please Select Accomodation Required Or Not\n";
		}else if($jq('#accomReq1'+idvalue).is(':checked')==true){
			if($jq('#accomPlace'+idvalue).val()=="" ){
				errMessage += "Please Enter Accomodation Place\n";
			}
		}
		if($jq('#articleCarry1'+idvalue).is(':checked')==false && $jq('#articleCarry2'+idvalue).is(':checked')==false){
			errMessage += "Please Select Artical Carry Field\n";
		}
		
		
		
		
		
		if(errMessage!=""){
		alert(errMessage);
		}
	}
	if(errMessage ==""){
		$jq('#add'+idvalue).attr('disabled','disabled');
	var count = 0;
		count = idvalue+1;
var row = "<tr id=row_"+count+">";

		row += "<td>";
			row +="<input type=text  id=passengNo"+count+" style=width:25px; onkeypress='return checkInt(event)' onkeyup='copyToReturnJourney("+count+")'/>";
		row += "</td>";
		
		row += "<td>";
			row +="<input type=text id=passengName"+count+" style=width:auto; onkeyup=copyToReturnJourney("+count+");setArticals('onward',"+count+")></input>";
		row += "</td>";
			
		row += "<td>";
			row += "<input type=text readonly=readonly style=height:16px;width:64px;font-size: 9px;font-weight: bold; id=startDate"+count+" onmouseover=javascript:Calendar.setup({inputField:'startDate"+count+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'startDate"+count+"',step:1}); onchange=checkReqDates(\'onward\');copyToReturnJourney("+count+");mtAddHoursMin("+count+",\'onward\');durationOfDates("+count+",\'onward\');></input>";
		row += "</td>";
		
		row += "<td>";
			row +="<input type=text readonly=readonly  id=startTime"+count+" class =startTime style=width:37px onmouseover=javascript:timePicker('startTime') onchange=copyToReturnJourney("+count+");mtAddHoursMin("+count+",'onward');durationOfDates("+count+",'onward');></input>";
		row += "</td>";
		
		//for duration
		row += "<td>";
		   row +="<input type=text id=durationH"+count+" style=width:18px onkeyup=mtAddHoursMin("+count+",'onward') maxlength=3></input>";
		   row +="<input type=text id=durationM"+count+" style=width:18px onkeyup=mtAddHoursMin("+count+",'onward') maxlength=2></input>";
	    row += "</td>";
		
		row += "<td>";
			row += "<input type=text readonly=readonly style=height:16px;width:64px;font-size: 9px;font-weight: bold; id=estDate"+count+" onmouseover=javascript:Calendar.setup({inputField:'estDate"+count+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'estDate"+count+"',step:1}); onchange=checkReqDates(\'onward\');copyToReturnJourney("+count+");durationOfDates("+count+",\'onward\');></input>";
		row += "</td>";
		
		row += "<td>";
			row +="<input type=text readonly=readonly  id=estTime"+count+" class =estTime style=width:37px onmouseover=javascript:timePicker('estTime') onchange=copyToReturnJourney("+count+");durationOfDates("+count+",'onward');></input>";
		row += "</td>";
		
		row += "<td>";
			row +="<select id=pickupPoint"+count+"  style=width:80px; onchange='copyToReturnJourney("+count+")'>";
				row +="<option value=0>Select</option>";
					for(var i=0; i<addressJSON.length; i++){
				row +="<option value="+addressJSON[i].addressId+">"+addressJSON[i].addressName+"</option>";			
					}
			row +="</select>";
		row += "</td>";
		
		row += "<td>";
			row +="<input type=text  id=otherSourceName"+count+" style=width:50px; onkeyup='copyToReturnJourney("+count+")'/>";
		row += "</td>";
		
		row += "<td>";
			row +="<select id=droppingPoint"+count+"  style=width:80px; onchange='copyToReturnJourney("+count+")'>";
				row +="<option value=0>Select</option>";
					for(var i=0; i<addressJSON.length; i++){
				row +="<option value="+addressJSON[i].addressId+">"+addressJSON[i].addressName+"</option>";		
					}
			row +="</select>";
		row += "</td>";
			
		row += "<td>";
			row +="<input type=text  id=otherDestiName"+count+" style=width:50px; onkeyup='copyToReturnJourney("+count+")'/>";
		row += "</td>";
		
		row += "<td>";
			row += "<input type=checkbox  id=accomReq1"+count+" value=1 onclick=javascript:checkAccomdationRequired('YES','"+count+"');copyToReturnJourney('"+count+"');>Y</input>";
			row += "<input type=checkbox  id=accomReq2"+count+" value=0  onclick=javascript:checkAccomdationRequired('NO','"+count+"');copyToReturnJourney('"+count+"'); checked='checked'>N</input>";
		row += "</td>";
		
		row += "<td>";
			row += "<input type=text  id=accomPlace"+count+" style=width:50px; readonly=readonly/>";
		row += "</td>";
		
		row += "<td>";
			row += "<input type=checkbox  id=articleCarry1"+count+" value=1 onclick=javascript:checkArticleRequired('YES','"+count+"');copyToReturnJourney('"+count+"');>Y</input>";
			row += "<input type=checkbox  id=articleCarry2"+count+" value=0  onclick=javascript:checkArticleRequired('NO','"+count+"');copyToReturnJourney('"+count+"'); checked='checked'>N</input>";
		row += "</td>";		
			
		row += "<td>";
			row +="<input type=text id=remarks"+count+" style=width:70px; onkeyup='copyToReturnJourney("+count+")'/>";
		row += "</td>";	
		
		row += "<td>";
			row +="<input type=button id=add"+count+" class=smallbtn value=+ onclick=javascript:funcreatenewJourneyRow("+count+") />";
		row += "</td>";	
		
		row += "<td>";	
			row +="<input type=button id=del"+count+" class=smallbtn value=- onclick=javascript:deleteJourneyDetailsRow("+count+"); />";
		row += "</td>";	
		
	row += "</tr>";

$jq("#row_"+(count-1)).after(row);

if($jq('input:radio[name=vehicleReturn]:checked').val()==1){
ReturnfuncreatenewJourneyRow(idvalue);
}
	}

}

function deleteJourneyDetailsRow(idvalue)
{	idvalue = parseInt(idvalue);
	var del=confirm("Do you want to delete this row?");
	if(del==true) 
	{
		$jq('#row_'+idvalue).remove();
		$jq('#passeng'+idvalue).remove();// for article remove
		//for add button enable
		$jq('#add'+(idvalue-1)).removeAttr('disabled');
	}
}

function checkAccomdationRequired(type,rowNo){
	if(type=='YES'){
		if($jq('#accomReq1'+rowNo).is(':checked')==true){
			$jq('#accomPlace'+rowNo).val('');
			$jq('#accomReq2'+rowNo).attr("checked",false);
			$jq('#accomPlace'+rowNo).attr("readonly",false);
		}else{
			$jq('#accomPlace'+rowNo).attr("readonly",true);
		}	
	}
	if(type=='NO'){
		if($jq('#accomReq2'+rowNo).is(':checked')==true){
			$jq('#accomReq1'+rowNo).attr("checked",false);
			$jq('#accomPlace'+rowNo).attr("readonly",true);
			$jq('#accomPlace'+rowNo).val('');
		}
	}
}

function checkArticleRequired(type,rowNo){
	if(type=='YES'){
		if($jq('#articleCarry1'+rowNo).is(':checked')==true){
			
			$jq('#articleCarry2'+rowNo).attr("checked",false);
			$jq('#onwardArticleDetailsID').css("display","block");
			addNewArticleDiv(rowNo);
		}else{
			$jq('#passeng'+rowNo).remove();
		}
	}
	if(type=='NO'){
		if($jq('#articleCarry2'+rowNo).is(':checked')==true){
			$jq('#articleCarry1'+rowNo).attr("checked",false);
			$jq('#passeng'+rowNo).remove();
			$jq('#onwardArticleDetailsID').css('display','none');
		}
	}
}
function checkReturnVehicleRequired(type){
	
	if(type=='YES'){
		if($jq('#vehicleReturn2').is(':checked')==true){
			$jq('#returnJourneyDetailsID').css("display","block");
			var i=0;
			$jq('#onwardJourneyDetailsID').find('tr:gt(4)').each(
					function(){
						// ReturnfuncreatenewJourneyRow(i);
						// copyToReturnJourney(i);
						i++;
					}
					);
			for(var j=1;j<i;j++){
				$jq('#Rrow_'+j).remove();
				$jq('#Rpasseng'+j).remove();
			}
			$jq('input:checkbox[id=RaccomReq10]').attr('checked',false);
			$jq('input:checkbox[id=RaccomReq20]').attr('checked',true);
			
			$jq('input:checkbox[id=RarticleCarry10]').attr('checked',false);
			$jq('input:checkbox[id=RarticleCarry20]').attr('checked',true);
			for(var j=0;j<i-1;j++){
				copyToReturnJourney(j);
				ReturnfuncreatenewJourneyRow(j);
				
			}
		}
	}
	if(type=='NO'){
	if($jq('#vehicleReturn1').is(':checked')==true){
			$jq('#returnJourneyDetailsID').css("display","none");	
			$jq('#returnArticleDetailsID').css("display","none");	
		}
	}
}


function insertNewRow(mainRID,tabID,rowDivID,rowIndex){
	
	var flag = false;
	$jq('#'+tabID).find('tr:not(:first)').each(
			function(){
				$jq(this).find('td').each(
						function(){
					if($jq(this).find('input:text').is(':visible') && $jq(this).find('input:text').val()!=''){
					flag = true;
				}})
			})
	if(flag){
		createWorkFlowRow(mainRID,tabID,rowDivID,rowIndex);
	}else{
			alert("Please Enter Row Values \n");
	}
}
function createWorkFlowRow(mainRID,tabID,rowDivID,rowIndex){
	var index =0;
	index = rowIndex+1;
	var row = "<tr id="+rowDivID+index+">";
	row +=	 "<td><select  id="+rowDivID+"ArticleType"+index+" style=width:98%;>";
	row +=			"<option value='Non-Explosive'>Non-Explosive</option>"+
					"<option value='Explosive'>Explosive</option>";
	row +=		"</select>";
	row +=	 "</td>";
	row +=	 "<td><input type=text style=width:98% id="+rowDivID+"Length"+index+" onkeypress='javascript:return checkFloat(event"+",&quot;"+rowDivID+"Length"+index+"&quot;)'  /></td>";
	row +=	 "<td><input type=text style=width:98%; id="+rowDivID+"Breadth"+index+" onkeypress='javascript:return checkFloat(event,&quot;"+rowDivID+"Breadth"+index+"&quot;)';  /></td>";
	row +=	 "<td><input type=text style=width:98%; id="+rowDivID+"Height"+index+" onkeypress='javascript:return checkFloat(event,&quot;"+rowDivID+"Height"+index+"&quot;)';  /></td>";
	row +=	 "<td><input type=text style=width:98%; id="+rowDivID+"Weight"+index+" onkeypress='javascript:return checkFloat(event,&quot;"+rowDivID+"Weight"+index+"&quot;)'; /></td>";
	row +=	 "<td><input type=text style=width:98%;  id="+rowDivID+"Quantity"+index+"  onkeypress='javascript:return checkInt(event)'; /></td>";
	row +=	 "<td><input type=button style=width:98%; id="+rowDivID+"Add"+index+"  value=+ onclick=javascript:insertNewRow('"+mainRID+"','"+tabID+"','"+rowDivID+"',"+index+"); /></td>";
	row +=	 "<td><input type=button style=width:98%; id="+rowDivID+"Del"+index+"  value=- onclick=javascript:deleteRow('"+mainRID+"','"+tabID+"','"+rowDivID+"',"+index+"); /></td>";
	row +="</tr>";
	$jq("#"+rowDivID+rowIndex).after(row);
		
}

function deleteRow(mainRID,tabID,rowDivID,rowIndex){
	var del = confirm("Do you want to delete this item?");
	if (del) {
		$jq('#'+rowDivID+rowIndex).remove();
	}
}

function addNewArticleDiv(rowID){
	$jq('#passeng'+rowID).remove();
	var row = "<tr id=passeng"+rowID+">";
	var onwardPassengerName=$jq('#passengName'+rowID).val();
			row +="<td>";
			
				// row +="<div class=line id=passeng"+rowID+"Name
				// style=color:red;>Passenger Row"+rowID+"</div>";
			row +="<div class=line id=passeng"+rowID+"Name style=color:red;>"+onwardPassengerName+"</div>";
				
				row +="<table id=passeng"+rowID+"Tab width=100% border=2px cellpadding=0px cellspacing=0px align=center>";
				
					row +="<tr>";
						row +="<td class=tabletd align=center style=width:15%;>Type</td>";
						row +="<td class=tabletd align=center style=width:15%;>Length (CM)</td>";
						row +="<td class=tabletd align=center style=width:15%;>Breadth (CM)</td>";
						row +="<td class=tabletd align=center style=width:15%;>Height (CM)</td>";
						row +="<td class=tabletd align=center style=width:15%;>Weight (KG)</td>";
						row +="<td class=tabletd align=center style=width:15%;>Quantity</td>";
						row +="<td class=tabletd align=center style=width:5%;>Add</td>";
						row +="<td class=tabletd align=center style=width:5%;>Del</td>";
					row +="</tr>";
					row +="<tr id=passeng"+rowID+"Row"+rowID+">";
						row +="<td><select  id=passeng"+rowID+"RowArticleType"+rowID+" style=width:98%;>";
						row +="<option value=Non-Explosive>Non-Explosive</option>";
						row +="<option value=Explosive>Explosive</option>";
						row +="</select>";
						row +="</td>";
						row +="<td><input type=text  style=width:98%; id=passeng"+rowID+"RowLength"+rowID+" onkeypress='javascript:return checkFloat(event,&quot;passeng"+rowID+"RowLength"+rowID+"&quot;)'; /></td>";
						row +="<td><input type=text  style=width:98%; id=passeng"+rowID+"RowBreadth"+rowID+" onkeypress='javascript:return checkFloat(event,&quot;passeng"+rowID+"RowBreadth"+rowID+"&quot;)'; /></td>";
						row +="<td><input type=text  style=width:98%; id=passeng"+rowID+"RowHeight"+rowID+" onkeypress='javascript:return checkFloat(event,&quot;passeng"+rowID+"RowHeight"+rowID+"&quot;)'; /></td>";
						row +="<td><input type=text  style=width:98%; id=passeng"+rowID+"RowWeight"+rowID+" onkeypress='javascript:return checkFloat(event,&quot;passeng"+rowID+"RowWeight"+rowID+"&quot;)'; /></td>";
						row +="<td><input type=text  style=width:98%; id=passeng"+rowID+"RowQuantity"+rowID+" onkeypress='javascript:return checkInt(event)'; /></td>";
						row +="<td><input type=button style=width:98%; id=passeng"+rowID+"RowAdd"+rowID+" value=+ onclick=javascript:insertNewRow(&quot;passeng"+rowID+"&quot;,&quot;passeng"+rowID+"Tab&quot;,&quot;passeng"+rowID+"Row&quot;,"+rowID+"); /></td>";
						row +="<td><input type=button style=width:98%;display:none; id=passeng"+rowID+"RowDel"+rowID+" value=- onclick=javascript:deleteRow(&quot;passeng"+rowID+"&quot;,&quot;passeng"+rowID+"Tab&quot;,&quot;passeng"+rowID+"Row&quot;,"+rowID+"); /></td>";
					row +="</tr>";
				row +="</table>";				
			row +="</td>";
		row +="</tr>";
$jq("#passeng").after(row);	
}

// for Return Journey
function ReturnfuncreatenewJourneyRow(idvalue)
{
	//for diasable add button 
	$jq('#Radd'+idvalue).attr('disabled','disabled');
	
	var count = 0;
		count = idvalue+1;
var row = "<tr id=Rrow_"+count+">";

		row += "<td>";
			row +="<input type=text  id=RpassengNo"+count+" style=width:25px; onkeypress='return checkInt(event)' />";
		row += "</td>";
		
		row += "<td>";
			row +="<input type=text id=RpassengName"+count+" style=width:auto; onkeyup=setArticals('return',"+count+")></input>";
		row += "</td>";
			
		row += "<td>";
			row += "<input type=text readonly=readonly style=height:16px;width:64px;font-size: 9px;font-weight: bold; id=RstartDate"+count+" onmouseover=javascript:Calendar.setup({inputField:'RstartDate"+count+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'RstartDate"+count+"',step:1}); onchange=checkReqDates(\'return\');onkeyup=mtAddHoursMin("+count+",\'return\');durationOfDates("+count+",\'return\');></input>";
		row += "</td>";
		
		row += "<td>";
			row +="<input type=text readonly=readonly  id=RstartTime"+count+" class =RstartTime style=width:37px onmouseover=javascript:timePicker('RstartTime') onchange=onkeyup=mtAddHoursMin("+count+",'return');durationOfDates("+count+",'return');></input>";
		row += "</td>";
		
		//for duration
		row += "<td>";
		   row +="<input type=text id=RdurationH"+count+" style=width:18px onkeyup=mtAddHoursMin("+count+",'return') maxlength=3></input>";
		   row +="<input type=text id=RdurationM"+count+" style=width:18px onkeyup=mtAddHoursMin("+count+",'return') maxlength=2></input>";
	    row += "</td>";
		
		row += "<td>";
			row += "<input type=text readonly=readonly style=height:16px;width:64px;font-size: 9px;font-weight: bold; id=RestDate"+count+" onmouseover=javascript:Calendar.setup({inputField:'RestDate"+count+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'RestDate"+count+"',step:1}); onchange=checkReqDates(\'return\');durationOfDates("+count+",'return');></input>";
		row += "</td>";
		
		row += "<td>";
			row +="<input type=text readonly=readonly  id=RestTime"+count+" class =RestTime style=width:37px onmouseover=javascript:timePicker('RestTime');durationOfDates("+count+",'return');></input>";
		row += "</td>";
		
		row += "<td>";
			row +="<select id=RpickupPoint"+count+"  style=width:80px;>";
				row +="<option value=0>Select</option>";
					for(var i=0; i<addressJSON.length; i++){
				row +="<option value="+addressJSON[i].addressId+">"+addressJSON[i].addressName+"</option>";			
					}
			row +="</select>";
		row += "</td>";
		
		row += "<td>";
			row +="<input type=text  id=RotherSourceName"+count+" style=width:50px;/>";
		row += "</td>";
		
		row += "<td>";
			row +="<select id=RdroppingPoint"+count+"  style=width:80px;>";
				row +="<option value=0>Select</option>";
					for(var i=0; i<addressJSON.length; i++){
				row +="<option value="+addressJSON[i].addressId+">"+addressJSON[i].addressName+"</option>";		
					}
			row +="</select>";
		row += "</td>";
			
		row += "<td>";
			row +="<input type=text  id=RotherDestiName"+count+" style=width:50px;/>";
		row += "</td>";
		
		row += "<td>";
			row += "<input type=checkbox  id=RaccomReq1"+count+" value=1 onclick=javascript:ReturncheckAccomdationRequired('YES','"+count+"');>Y</input>";
			row += "<input type=checkbox  id=RaccomReq2"+count+" value=0  onclick=javascript:ReturncheckAccomdationRequired('NO','"+count+"'); checked='checked'>N</input>";
		row += "</td>";
		
		row += "<td>";
			row += "<input type=text  id=RaccomPlace"+count+" style=width:50px; readonly=readonly/>";
		row += "</td>";
		
		row += "<td>";
			row += "<input type=checkbox  id=RarticleCarry1"+count+" value=1 onclick=javascript:ReturncheckArticleRequired('YES','"+count+"');>Y</input>";
			row += "<input type=checkbox  id=RarticleCarry2"+count+" value=0  onclick=javascript:ReturncheckArticleRequired('NO','"+count+"'); checked='checked'>N</input>";
		row += "</td>";		
			
		row += "<td>";
			row +="<input type=text id=Rremarks"+count+" style=width:70px;/>";
		row += "</td>";	
		
		row += "<td>";
			row +="<input type=button id=Radd"+count+" class=smallbtn value=+ onclick=javascript:ReturnfuncreatenewJourneyRow("+count+") />";
		row += "</td>";	
		
		row += "<td>";	
			row +="<input type=button id=Rdel"+count+" class=smallbtn value=- onclick=javascript:ReturndeleteJourneyDetailsRow("+count+"); />";
		row += "</td>";	
		
	row += "</tr>";

$jq("#Rrow_"+(count-1)).after(row);
copyToReturnJourney(count);

	// }
}

function ReturndeleteJourneyDetailsRow(idvalue)
{
	idvalue = parseInt(idvalue);
	var del=confirm("Do you want to delete this row?");
	if(del==true) 
	{
		$jq('#Rrow_'+idvalue).remove();
		$jq('#Rpasseng'+idvalue).remove();// for article remove
		$jq('#Radd'+(idvalue-1)).removeAttr('disabled');		
	}
}

function ReturncheckAccomdationRequired(type,rowNo){
	if(type=='YES'){
		if($jq('#RaccomReq1'+rowNo).is(':checked')==true){
			$jq('#RaccomPlace'+rowNo).val('');
			$jq('#RaccomReq2'+rowNo).attr("checked",false);
			$jq('#RaccomPlace'+rowNo).attr("readonly",false);
		}else{
			$jq('#RaccomPlace'+rowNo).attr("readonly",true);
		}	
	}
	if(type=='NO'){
		if($jq('#RaccomReq2'+rowNo).is(':checked')==true){
			$jq('#RaccomReq1'+rowNo).attr("checked",false);
			$jq('#RaccomPlace'+rowNo).attr("readonly",true);
			$jq('#RaccomPlace'+rowNo).val('');
		}
	}
}

function ReturncheckArticleRequired(type,rowNo){
	if(type=='YES'){
		if($jq('#RarticleCarry1'+rowNo).is(':checked')==true){
			
			$jq('#RarticleCarry2'+rowNo).attr("checked",false);
			$jq('#returnArticleDetailsID').css("display","block");
			ReturnaddNewArticleDiv(rowNo);
		}else{
			$jq('#Rpasseng'+rowNo).remove();
		}
	}
	if(type=='NO'){
		if($jq('#RarticleCarry2'+rowNo).is(':checked')==true){
			$jq('#RarticleCarry1'+rowNo).attr("checked",false);
			$jq('#Rpasseng'+rowNo).remove();
			$jq('#returnArticleDetailsID').css('display','none');
		}
	}
}
function ReturninsertNewRow(mainRID,tabID,rowDivID,rowIndex){
	ReturncreateWorkFlowRow(mainRID,tabID,rowDivID,rowIndex);
	
	
}
function ReturncreateWorkFlowRow(mainRID,tabID,rowDivID,rowIndex){
	var index =0;
	index = rowIndex+1;
	var row = "<tr id="+rowDivID+index+">";
	row +=	 "<td><select  id="+rowDivID+"ArticleType"+index+" style=width:98%;>";
	row +=			"<option value='Non-Explosive'>Non-Explosive</option>"+
					"<option value='Explosive'>Explosive</option>";
	row +=		"</select>";
	row +=	 "</td>";
	row +=	 "<td><input type=text style=width:98% id="+rowDivID+"Length"+index+" onkeypress='javascript:return checkFloat(event"+",&quot;"+rowDivID+"Length"+index+"&quot;)'  /></td>";
	row +=	 "<td><input type=text style=width:98%; id="+rowDivID+"Breadth"+index+" onkeypress='javascript:return checkFloat(event,&quot;"+rowDivID+"Breadth"+index+"&quot;)';  /></td>";
	row +=	 "<td><input type=text style=width:98%; id="+rowDivID+"Height"+index+" onkeypress='javascript:return checkFloat(event,&quot;"+rowDivID+"Height"+index+"&quot;)';  /></td>";
	row +=	 "<td><input type=text style=width:98%; id="+rowDivID+"Weight"+index+" onkeypress='javascript:return checkFloat(event,&quot;"+rowDivID+"Weight"+index+"&quot;)'; /></td>";
	row +=	 "<td><input type=text style=width:98%;  id="+rowDivID+"Quantity"+index+"  onkeypress='javascript:return checkInt(event)'; /></td>";
	row +=	 "<td><input type=button style=width:98%; id="+rowDivID+"Add"+index+"  value=+ onclick=javascript:ReturninsertNewRow('"+mainRID+"','"+tabID+"','"+rowDivID+"',"+index+"); /></td>";
	row +=	 "<td><input type=button style=width:98%; id="+rowDivID+"Del"+index+"  value=- onclick=javascript:ReturndeleteRow('"+mainRID+"','"+tabID+"','"+rowDivID+"',"+index+"); /></td>";
	row +="</tr>";
	$jq("#"+rowDivID+rowIndex).after(row);
		
}

function ReturndeleteRow(mainRID,tabID,rowDivID,rowIndex){
	var del = confirm("Do you want to delete this item?");
	if (del) {
		$jq('#'+rowDivID+rowIndex).remove();
	}
}

function ReturnaddNewArticleDiv(rowID){
	$jq('#Rpasseng'+rowID).remove();
	var row = "<tr id=Rpasseng"+rowID+">";
	var returnPassengerName=$jq('#RpassengName'+rowID).val();
			row +="<td>";
			
				// row +="<div class=line id=Rpasseng"+rowID+"Name
				// style=color:red;> Return Passenger Row"+rowID+"</div>";
			row +="<div class=line id=Rpasseng"+rowID+"Name style=color:red;>"+returnPassengerName+"</div>";	
				row +="<table id=Rpasseng"+rowID+"Tab width=100% border=2px cellpadding=0px cellspacing=0px align=center>";
				
					row +="<tr>";
						row +="<td class=tabletd align=center style=width:15%;>Type</td>";
						row +="<td class=tabletd align=center style=width:15%;>Length (CM)</td>";
						row +="<td class=tabletd align=center style=width:15%;>Breadth (CM)</td>";
						row +="<td class=tabletd align=center style=width:15%;>Height (CM)</td>";
						row +="<td class=tabletd align=center style=width:15%;>Weight (KG)</td>";
						row +="<td class=tabletd align=center style=width:15%;>Quantity</td>";
						row +="<td class=tabletd align=center style=width:5%;>Add</td>";
						row +="<td class=tabletd align=center style=width:5%;>Del</td>";
					row +="</tr>";
					row +="<tr id=Rpasseng"+rowID+"Row"+rowID+">";
						row +="<td><select  id=Rpasseng"+rowID+"RowArticleType"+rowID+" style=width:98%;>";
						row +="<option value=Non-Explosive>Non-Explosive</option>";
						row +="<option value=Explosive>Explosive</option>";
						row +="</select>";
						row +="</td>";
						row +="<td><input type=text  style=width:98%; id=Rpasseng"+rowID+"RowLength"+rowID+" onkeypress='javascript:return checkFloat(event,&quot;Rpasseng"+rowID+"RowLength"+rowID+"&quot;)'; /></td>";
						row +="<td><input type=text  style=width:98%; id=Rpasseng"+rowID+"RowBreadth"+rowID+" onkeypress='javascript:return checkFloat(event,&quot;Rpasseng"+rowID+"RowBreadth"+rowID+"&quot;)'; /></td>";
						row +="<td><input type=text  style=width:98%; id=Rpasseng"+rowID+"RowHeight"+rowID+" onkeypress='javascript:return checkFloat(event,&quot;Rpasseng"+rowID+"RowHeight"+rowID+"&quot;)'; /></td>";
						row +="<td><input type=text  style=width:98%; id=Rpasseng"+rowID+"RowWeight"+rowID+" onkeypress='javascript:return checkFloat(event,&quot;Rpasseng"+rowID+"RowWeight"+rowID+"&quot;)'; /></td>";
						row +="<td><input type=text  style=width:98%; id=Rpasseng"+rowID+"RowQuantity"+rowID+" onkeypress='javascript:return checkInt(event)'; /></td>";
						row +="<td><input type=button style=width:98%; id=Rpasseng"+rowID+"RowAdd"+rowID+" value=+ onclick=javascript:ReturninsertNewRow(&quot;Rpasseng"+rowID+"&quot;,&quot;Rpasseng"+rowID+"Tab&quot;,&quot;Rpasseng"+rowID+"Row&quot;,"+rowID+"); /></td>";
						row +="<td><input type=button style=width:98%;display:none; id=Rpasseng"+rowID+"RowDel"+rowID+" value=- onclick=javascript:ReturndeleteRow(&quot;Rpasseng"+rowID+"&quot;,&quot;Rpasseng"+rowID+"Tab&quot;,&quot;Rpasseng"+rowID+"Row&quot;,"+rowID+"); /></td>";
					row +="</tr>";
				row +="</table>";				
			row +="</td>";
		row +="</tr>";
$jq("#Rpasseng").after(row);	
}

// RequestForm
/*function saveVehicleRequestDetails(){
	var flag = true;
	var errorMessage = "";
if($jq('#requestedFor').is(':visible')){
	if($jq('#requestedFor').val()==0){
		errorMessage += "Please Select Initiating Officer \n";
		flag=false;
	}
}
	if($jq('#vehicleReturn1').is(':checked')==false && $jq('#vehicleReturn2').is(':checked')==false){
		errorMessage += "Please Select Vehicle Required For \n";
		flag=false;
	}
	if($jq('#purposeOfVisiting').val()==""){
		errorMessage += "Please Enter Purpose Of Visit \n";
		$jq('#purposeOfVisiting').focus();
		flag=false;
	}
	if($jq('#requestedFor').val()==""){
		errorMessage += "Please Enter Requested SFID \n";
		$jq('#requestedFor').focus();
		flag=false;
	}
	var onwardPassEngRowNo="";
	var returnPassEngRowNo="";
	$jq('#onwardJourneyDetailsID').find('table').find('tr').each(function(){
			if($jq(this).attr("id") !=''){
				onwardPassEngRowNo = $jq(this).attr("id");
			}
			if(onwardPassEngRowNo !=""){
				
				if($jq(this).find('td').eq(0).find('input:text').is(':visible') && $jq(this).find('td').eq(0).find('input:text').val()==''){
					errorMessage += "Please Enter No Of People in Onward Journey Details-"+onwardPassEngRowNo+"\n";
					flag=false;
				}
				if($jq(this).find('td').eq(1).find('input:text').is(':visible') && $jq(this).find('td').eq(1).find('input:text').val()==''){
					errorMessage += "Please Enter Name With Designation in Onward Journey Details-"+onwardPassEngRowNo+"\n";
					flag=false;
				}
				if($jq(this).find('td').eq(2).find('input:text').is(':visible') && $jq(this).find('td').eq(2).find('input:text').val()==''){
					errorMessage += "Please Select Travelling Date in Onward Journey Details-"+onwardPassEngRowNo+"\n";
					flag=false;
				}
				if($jq(this).find('td').eq(3).find('input:text').is(':visible') && $jq(this).find('td').eq(3).find('input:text').val()==''){
					errorMessage += "Please Select Travelling Time in Onward Journey Details-"+onwardPassEngRowNo+"\n";
					flag=false;
				}
				if($jq(this).find('td').eq(4).find('input:text').is(':visible') && $jq(this).find('td').eq(4).find('input:text').val()==''){
					errorMessage += "Please Select Estimated Date in Onward Journey Details-"+onwardPassEngRowNo+"\n";
					flag=false;
				}
				if($jq(this).find('td').eq(5).find('input:text').is(':visible') && $jq(this).find('td').eq(5).find('input:text').val()==''){
					errorMessage += "Please Select Estimated Time in Onward Journey Details-"+onwardPassEngRowNo+"\n";
					flag=false;
				}
				if($jq(this).find('td').eq(6).find('select').is(':visible') && $jq(this).find('td').eq(6).find('select').val()=='0'){
					errorMessage += "Please Select Pickup Point in Onward Journey Details-"+onwardPassEngRowNo+"\n";
					flag=false;
				}
				if($jq(this).find('td').eq(8).find('select').is(':visible') && $jq(this).find('td').eq(8).find('select').val()=='0'){
					errorMessage += "Please Select Dropping Point in Onward Journey Details-"+onwardPassEngRowNo+"\n";
					flag=false;
				}
				if($jq(this).find('td').eq(10).find('input:checkbox').is(':visible') && $jq(this).find('td').eq(10).find('input:checkbox').is(':checked')==false){
					errorMessage += "Please Select Accommodation Required or Not in Onward Journey Details-"+onwardPassEngRowNo+"\n";
					flag=false;
				}else{
					if($jq(this).find('td').eq(10).find('input:checkbox:checked').val()==1){
						if($jq(this).find('td').eq(11).find('input:text').is(':visible') &&  $jq(this).find('td').eq(11).find('input:text').val() ==''){
							errorMessage += "Please Enter Accommodation Place in Onward Journey Details-"+onwardPassEngRowNo+"\n";	
							flag=false;
						}
					}
				}

				if($jq(this).find('td').eq(12).find('input:checkbox').is(':visible') && $jq(this).find('td').eq(12).find('input:checkbox').is(':checked')==false){
					errorMessage += "Please Select Article Carry or Not in Onward Journey Details-"+onwardPassEngRowNo+"\n";
					flag=false;
				}
			}
	});
	
	$jq('#returnJourneyDetailsID').find('table').find('tr').each(function(){
		if($jq(this).attr("id") !=''){
			returnPassEngRowNo = $jq(this).attr("id");
		}
		if(returnPassEngRowNo !=""){
			if($jq(this).find('td').eq(0).find('input:text').is(':visible') && $jq(this).find('td').eq(0).find('input:text').val()==''){
				errorMessage += "Please Enter No Of People in Return Journey Details-"+returnPassEngRowNo+"\n";
				flag=false;
			}
			if($jq(this).find('td').eq(1).find('input:text').is(':visible') && $jq(this).find('td').eq(1).find('input:text').val()==''){
				errorMessage += "Please Enter Name With Designation in Return Journey Details-"+returnPassEngRowNo+"\n";
				flag=false;
			}
			if($jq(this).find('td').eq(2).find('input:text').is(':visible') && $jq(this).find('td').eq(2).find('input:text').val()==''){
				errorMessage += "Please Select Travelling Date in Return Journey Details-"+returnPassEngRowNo+"\n";
				flag=false;
			}
			if($jq(this).find('td').eq(3).find('input:text').is(':visible') && $jq(this).find('td').eq(3).find('input:text').val()==''){
				errorMessage += "Please Select Travelling Time in Return Journey Details-"+returnPassEngRowNo+"\n";
				flag=false;
			}
			if($jq(this).find('td').eq(4).find('input:text').is(':visible') && $jq(this).find('td').eq(4).find('input:text').val()==''){
				errorMessage += "Please Select Estimated Date in Return Journey Details-"+returnPassEngRowNo+"\n";
				flag=false;
			}
			if($jq(this).find('td').eq(5).find('input:text').is(':visible') && $jq(this).find('td').eq(5).find('input:text').val()==''){
				errorMessage += "Please Select Estimated Time in Return Journey Details-"+returnPassEngRowNo+"\n";
				flag=false;
			}
			if($jq(this).find('td').eq(6).find('select').is(':visible') && $jq(this).find('td').eq(6).find('select').val()=='0'){
				errorMessage += "Please Select Pickup Point in Return Journey Details-"+returnPassEngRowNo+"\n";
				flag=false;
			}
			if($jq(this).find('td').eq(8).find('select').is(':visible') && $jq(this).find('td').eq(8).find('select').val()=='0'){
				errorMessage += "Please Select Dropping Point in Return Journey Details-"+returnPassEngRowNo+"\n";
				flag=false;
			}
			if($jq(this).find('td').eq(10).find('input:checkbox').is(':visible') && $jq(this).find('td').eq(10).find('input:checkbox').is(':checked')==false){
				errorMessage += "Please Select Accommodation Required or Not in Return Journey Details-"+returnPassEngRowNo+"\n";
				flag=false;
			}else{
				if($jq(this).find('td').eq(10).find('input:checkbox:checked').val()==1){
					if($jq(this).find('td').eq(11).find('input:text').is(':visible') &&  $jq(this).find('td').eq(11).find('input:text').val() ==''){
						errorMessage += "Please Enter Accommodation Place in Return Journey Details-"+returnPassEngRowNo+"\n";	
						flag=false;
					}
				}
			}
		
			if($jq(this).find('td').eq(12).find('input:checkbox').is(':visible') && $jq(this).find('td').eq(12).find('input:checkbox').is(':checked')==false){
				errorMessage += "Please Select Article Carry or Not in Return Journey Details-"+returnPassEngRowNo+"\n";
				flag=false;
			}
			
		}
});
	//time check for Onward Details drid
	$jq('#onwardJourneyDetailsID').find('tr:gt(4)').each(
			function(){	
				onwardPassEngRowNo = $jq(this).attr("id");
				if(parseFloat(compareDates(convertDate($jq(this).find('td').eq(2).find('input').val()),convertDate($jq(this).find('td').eq(4).find('input').val())))==0){
					if($jq.trim($jq(this).find('td').eq(3).find('input').val().split(":")[0])==$jq.trim($jq(this).find('td').eq(5).find('input').val().split(":")[0])){
						if(($jq.trim($jq(this).find('td').eq(3).find('input').val().split(":")[1])==$jq.trim($jq(this).find('td').eq(5).find('input').val().split(":")[1])) || ($jq.trim($jq(this).find('td').eq(3).find('input').val().split(":")[1]) > $jq.trim($jq(this).find('td').eq(5).find('input').val().split(":")[1]))){
							errorMessage += "Estimated Time Must Be Greater Than Travelling Time In Onward Journey Details-"+onwardPassEngRowNo+"\n";
						}
					}
					if($jq.trim($jq(this).find('td').eq(3).find('input').val().split(":")[0]) > $jq.trim($jq(this).find('td').eq(5).find('input').val().split(":")[0])){
						errorMessage += "Estimated Time Must Be Greater Than Travelling Time In Onward Journey Details-"+onwardPassEngRowNo+"\n";
					}
				}
			});
	//time check for return Details drid
	$jq('#returnJourneyDetailsID').find('tr:gt(4)').each(
			function(){	
				returnPassEngRowNo = $jq(this).attr("id");
				if(parseFloat(compareDates(convertDate($jq(this).find('td').eq(2).find('input').val()),convertDate($jq(this).find('td').eq(4).find('input').val())))==0){
					if($jq.trim($jq(this).find('td').eq(3).find('input').val().split(":")[0])==$jq.trim($jq(this).find('td').eq(5).find('input').val().split(":")[0])){
						if(($jq.trim($jq(this).find('td').eq(3).find('input').val().split(":")[1])==$jq.trim($jq(this).find('td').eq(5).find('input').val().split(":")[1])) || ($jq.trim($jq(this).find('td').eq(3).find('input').val().split(":")[1]) > $jq.trim($jq(this).find('td').eq(5).find('input').val().split(":")[1]))){
							errorMessage += "Estimated Time Must Be Greater Than Travelling Time In Return Journey Details-"+returnPassEngRowNo+"\n";
						}
					}
					if($jq.trim($jq(this).find('td').eq(3).find('input').val().split(":")[0]) > $jq.trim($jq(this).find('td').eq(5).find('input').val().split(":")[0])){
						errorMessage += "Estimated Time Must Be Greater Than Travelling Time In Return Journey Details-"+returnPassEngRowNo+"\n";
					}
				}
			});
	
	if(flag){
		var onwardMainJSON = {};
		var returnMainJSON = {};
		var onwardPassEngCheck=0;
		var returnPassEngCheck=0;
		
		var onwardPassEngRowNo;
		var returnPassEngRowNo;
		
		$jq('#onwardJourneyDetailsID').find('table').find('tr').each(function(){
			var onwardPassEngRowJSON = {};
			var onwardArticleTabJSON = {};
			var onwardArtCheck=0;
			var subJSON ={};
			
				if($jq(this).attr("id") !=''){
					onwardPassEngRowNo = $jq(this).attr("id").split("_")[1];
					onwardPassEngCheck++
				}
				if(onwardPassEngCheck>0){
					
					if($jq(this).find('td').eq(0).find('input:text').is(':visible')){
						onwardPassEngRowJSON["onwardNOP"] = $jq(this).find('td').eq(0).find('input:text').val();
					}
					if($jq(this).find('td').eq(1).find('input:text').is(':visible')){
						onwardPassEngRowJSON["onwardNameWithDesig"] = $jq(this).find('td').eq(1).find('input:text').val();
					}
					if($jq(this).find('td').eq(2).find('input:text').is(':visible')){
						onwardPassEngRowJSON["onwardDepartureDate"] = $jq(this).find('td').eq(2).find('input:text').val();
					}
					if($jq(this).find('td').eq(3).find('input:text').is(':visible')){
						onwardPassEngRowJSON["onwardDepartureTime"] = $jq(this).find('td').eq(3).find('input:text').val();
					}
					if($jq(this).find('td').eq(4).find('input:text').is(':visible')){
						onwardPassEngRowJSON["onwardEstiDate"] = $jq(this).find('td').eq(4).find('input:text').val();
					}
					if($jq(this).find('td').eq(5).find('input:text').is(':visible')){
						onwardPassEngRowJSON["onwardEstiTime"] = $jq(this).find('td').eq(5).find('input:text').val();
					}
					if($jq(this).find('td').eq(6).find('select').is(':visible')){
						onwardPassEngRowJSON["onwardPickup"] = $jq(this).find('td').eq(6).find('select').val();
					}
					if($jq(this).find('td').eq(7).find('input:text').is(':visible')){
						onwardPassEngRowJSON["onwardOtherSource"] = $jq(this).find('td').eq(7).find('input:text').val();
					}
					if($jq(this).find('td').eq(8).find('select').is(':visible')){
						onwardPassEngRowJSON["onwardDrop"] = $jq(this).find('td').eq(8).find('select').val();
					}
					if($jq(this).find('td').eq(9).find('input:text').is(':visible')){
						onwardPassEngRowJSON["onwardOtherDesti"] = $jq(this).find('td').eq(9).find('input:text').val();
					}
					if($jq(this).find('td').eq(10).find('input:checkbox').is(':visible')){
						onwardPassEngRowJSON["onwardAccomReq"] = $jq(this).find('td').eq(10).find('input:checkbox:checked').val();
						if($jq(this).find('td').eq(10).find('input:checkbox:checked').val()==1){
							if($jq(this).find('td').eq(11).find('input:text').is(':visible') &&  $jq(this).find('td').eq(11).find('input:text').val() !=''){
								onwardPassEngRowJSON["onwardAccomPlace"] = $jq(this).find('td').eq(11).find('input:text').val();
							}
						}
					}
					
					if($jq(this).find('td').eq(12).find('input:checkbox').is(':visible')){
						onwardPassEngRowJSON["onwardArtiReq"] = $jq(this).find('td').eq(12).find('input:checkbox:checked').val();
					}
					if($jq(this).find('td').eq(13).find('input:text').is(':visible')){
						onwardPassEngRowJSON["onwardRemarks"] = $jq(this).find('td').eq(13).find('input:text').val();
					}
					
					
					if(onwardPassEngRowJSON.onwardArtiReq == '1'){
						$jq('#passeng'+onwardPassEngRowNo).find('table').find('tr').each(function(){
							var onwardArticleRowJSON = {};
							if($jq(this).attr("id") !=''){
								onwardArtCheck++
							}
							if(onwardArtCheck>0){
								if($jq(this).find('td').eq(0).find('select').is(':visible')){
									onwardArticleRowJSON["onwardArtType"] = $jq(this).find('td').eq(0).find('select').val();
								}
								if($jq(this).find('td').eq(1).find('input:text').is(':visible')){
									onwardArticleRowJSON["onwardArtLength"] = $jq(this).find('td').eq(1).find('input:text').val();
								}
								if($jq(this).find('td').eq(2).find('input:text').is(':visible')){
									onwardArticleRowJSON["onwardArtBreadth"] = $jq(this).find('td').eq(2).find('input:text').val();
								}
								if($jq(this).find('td').eq(3).find('input:text').is(':visible')){
									onwardArticleRowJSON["onwardArtHeight"] = $jq(this).find('td').eq(3).find('input:text').val();
								}
								if($jq(this).find('td').eq(4).find('input:text').is(':visible')){
									onwardArticleRowJSON["onwardArtWeight"] = $jq(this).find('td').eq(4).find('input:text').val();
								}
								if($jq(this).find('td').eq(5).find('input:text').is(':visible')){
									onwardArticleRowJSON["onwardArtQuantity"] = $jq(this).find('td').eq(5).find('input:text').val();
								}
								
								onwardArticleTabJSON[onwardArtCheck]=onwardArticleRowJSON;
							}
						});
					}
					
					subJSON["onwardPassEngRowJSON"]=onwardPassEngRowJSON;
					subJSON["onwardArticleTabJSON"]=onwardArticleTabJSON;
					
					onwardMainJSON[onwardPassEngCheck]=subJSON;
				}
			
		});
		if($jq('#returnJourneyDetailsID').is(':visible')){
			$jq('#returnJourneyDetailsID').find('table').find('tr').each(function(){
				var returnPassEngRowJSON = {};
				var returnArticleTabJSON = {};
				var returnArtCheck=0;
				var subJSON ={};
				
				
					if($jq(this).attr("id") !=''){
						returnPassEngRowNo = $jq(this).attr("id").split("_")[1];
						returnPassEngCheck++
					}
					if(returnPassEngCheck>0){
						
						if($jq(this).find('td').eq(0).find('input:text').is(':visible')){
							returnPassEngRowJSON["returnNOP"] = $jq(this).find('td').eq(0).find('input:text').val();
						}
						if($jq(this).find('td').eq(1).find('input:text').is(':visible')){
							returnPassEngRowJSON["returnNameWithDesig"] = $jq(this).find('td').eq(1).find('input:text').val();
						}
						if($jq(this).find('td').eq(2).find('input:text').is(':visible')){
							returnPassEngRowJSON["returnDepartureDate"] = $jq(this).find('td').eq(2).find('input:text').val();
						}
						if($jq(this).find('td').eq(3).find('input:text').is(':visible')){
							returnPassEngRowJSON["returnDepartureTime"] = $jq(this).find('td').eq(3).find('input:text').val();
						}
						if($jq(this).find('td').eq(4).find('input:text').is(':visible')){
							returnPassEngRowJSON["returnEstiDate"] = $jq(this).find('td').eq(4).find('input:text').val();
						}
						if($jq(this).find('td').eq(5).find('input:text').is(':visible')){
							returnPassEngRowJSON["returnEstiTime"] = $jq(this).find('td').eq(5).find('input:text').val();
						}
						if($jq(this).find('td').eq(6).find('select').is(':visible')){
							returnPassEngRowJSON["returnPickup"] = $jq(this).find('td').eq(6).find('select').val();
						}
						if($jq(this).find('td').eq(7).find('input:text').is(':visible')){
							returnPassEngRowJSON["returnOtherSource"] = $jq(this).find('td').eq(7).find('input:text').val();
						}
						if($jq(this).find('td').eq(8).find('select').is(':visible')){
							returnPassEngRowJSON["returnDrop"] = $jq(this).find('td').eq(8).find('select').val();
						}
						if($jq(this).find('td').eq(9).find('input:text').is(':visible')){
							returnPassEngRowJSON["returnOtherDesti"] = $jq(this).find('td').eq(9).find('input:text').val();
						}
						if($jq(this).find('td').eq(10).find('input:checkbox').is(':visible')){
							returnPassEngRowJSON["returnAccomReq"] = $jq(this).find('td').eq(10).find('input:checkbox:checked').val();
						}
						if($jq(this).find('td').eq(11).find('input:text').is(':visible') &&  $jq(this).find('td').eq(11).find('input:text').val() !=''){
							returnPassEngRowJSON["returnAccomPlace"] = $jq(this).find('td').eq(11).find('input:text').val();
						}
						if($jq(this).find('td').eq(12).find('input:checkbox').is(':visible')){
							returnPassEngRowJSON["returnArtiReq"] = $jq(this).find('td').eq(12).find('input:checkbox:checked').val();
						}
						if($jq(this).find('td').eq(13).find('input:text').is(':visible')){
							returnPassEngRowJSON["returnRemarks"] = $jq(this).find('td').eq(13).find('input:text').val();
						}
						
						
						if(returnPassEngRowJSON.returnArtiReq == '1'){
							$jq('#Rpasseng'+returnPassEngRowNo).find('table').find('tr').each(function(){
								var returnArticleRowJSON = {};
								if($jq(this).attr("id") !=''){
									returnArtCheck++
								}
								if(returnArtCheck>0){
									if($jq(this).find('td').eq(0).find('select').is(':visible')){
										returnArticleRowJSON["returnArtType"] = $jq(this).find('td').eq(0).find('select').val();
									}
									if($jq(this).find('td').eq(1).find('input:text').is(':visible')){
										returnArticleRowJSON["returnArtLength"] = $jq(this).find('td').eq(1).find('input:text').val();
									}
									if($jq(this).find('td').eq(2).find('input:text').is(':visible')){
										returnArticleRowJSON["returnArtBreadth"] = $jq(this).find('td').eq(2).find('input:text').val();
									}
									if($jq(this).find('td').eq(3).find('input:text').is(':visible')){
										returnArticleRowJSON["returnArtHeight"] = $jq(this).find('td').eq(3).find('input:text').val();
									}
									if($jq(this).find('td').eq(4).find('input:text').is(':visible')){
										returnArticleRowJSON["returnArtWeight"] = $jq(this).find('td').eq(4).find('input:text').val();
									}
									if($jq(this).find('td').eq(5).find('input:text').is(':visible')){
										returnArticleRowJSON["returnArtQuantity"] = $jq(this).find('td').eq(5).find('input:text').val();
									}
									
									returnArticleTabJSON[returnArtCheck]=returnArticleRowJSON;
								}
							});
						}
						
						subJSON["returnPassEngRowJSON"]=returnPassEngRowJSON;
						subJSON["returnArticleTabJSON"]=returnArticleTabJSON;
						
						returnMainJSON[returnPassEngCheck]=subJSON;
					}
				
			});
			
		}
		
		
		$jq('#param').val('SaveVehicleRequestDetails');
		$jq('#onwardMainJSON').val(JSON.stringify(onwardMainJSON));
		$jq('#returnMainJSON').val(JSON.stringify(returnMainJSON));
		$jq.post('transport.htm', $jq('#mtBean').serialize(), function(html) {
			clearVehicleRequestDetails();
			$jq("#result").html(html);
			
		});
		
		
		
	}else{
		alert(errorMessage);
	}
	
}*/
function saveVehicleRequestDetails(){
	var flag = true;
	var errorMessage = "";
if($jq('#requestedFor').is(':visible')){
	if($jq('#requestedFor').val()==0){
		errorMessage += "Please Select Initiating Officer \n";
		flag=false;
	}
}
	if($jq('#vehicleReturn1').is(':checked')==false && $jq('#vehicleReturn2').is(':checked')==false){
		errorMessage += "Please Select Vehicle Required For \n";
		flag=false;
	}
	if($jq('#purposeOfVisiting').val()==""){
		errorMessage += "Please Enter Purpose Of Visit \n";
		$jq('#purposeOfVisiting').focus();
		flag=false;
	}
	if($jq('#requestedFor').val()==""){
		errorMessage += "Please Enter Requested SFID \n";
		$jq('#requestedFor').focus();
		flag=false;
	}
	var onwardPassEngRowNo="";
	var returnPassEngRowNo="";
	$jq('#onwardJourneyDetailsID').find('table').find('tr').each(function(){
			if($jq(this).attr("id") !=''){
				onwardPassEngRowNo = $jq(this).attr("id");
			}
			if(onwardPassEngRowNo !=""){
				
				if($jq(this).find('td').eq(0).find('input:text').is(':visible') && $jq(this).find('td').eq(0).find('input:text').val()==''){
					errorMessage += "Please Enter No Of People in Onward Journey Details-"+onwardPassEngRowNo+"\n";
					flag=false;
				}
				if($jq(this).find('td').eq(1).find('input:text').is(':visible') && $jq(this).find('td').eq(1).find('input:text').val()==''){
					errorMessage += "Please Enter Name With Designation in Onward Journey Details-"+onwardPassEngRowNo+"\n";
					flag=false;
				}
				if($jq(this).find('td').eq(2).find('input:text').is(':visible') && $jq(this).find('td').eq(2).find('input:text').val()==''){
					errorMessage += "Please Select Travelling Date in Onward Journey Details-"+onwardPassEngRowNo+"\n";
					flag=false;
				}
				if($jq(this).find('td').eq(3).find('input:text').is(':visible') && $jq(this).find('td').eq(3).find('input:text').val()==''){
					errorMessage += "Please Select Travelling Time in Onward Journey Details-"+onwardPassEngRowNo+"\n";
					flag=false;
				}
				//changed bcz of duration
				if($jq(this).find('td').eq(5).find('input:text').is(':visible') && $jq(this).find('td').eq(5).find('input:text').val()==''){
					errorMessage += "Please Select Estimated Date in Onward Journey Details-"+onwardPassEngRowNo+"\n";
					flag=false;
				}
				if($jq(this).find('td').eq(6).find('input:text').is(':visible') && $jq(this).find('td').eq(6).find('input:text').val()==''){
					errorMessage += "Please Select Estimated Time in Onward Journey Details-"+onwardPassEngRowNo+"\n";
					flag=false;
				}
				if($jq(this).find('td').eq(7).find('select').is(':visible') && $jq(this).find('td').eq(7).find('select').val()=='0'){
					errorMessage += "Please Select Pickup Point in Onward Journey Details-"+onwardPassEngRowNo+"\n";
					flag=false;
				}
				if($jq(this).find('td').eq(9).find('select').is(':visible') && $jq(this).find('td').eq(9).find('select').val()=='0'){
					errorMessage += "Please Select Dropping Point in Onward Journey Details-"+onwardPassEngRowNo+"\n";
					flag=false;
				}
				if($jq(this).find('td').eq(11).find('input:checkbox').is(':visible') && $jq(this).find('td').eq(11).find('input:checkbox').is(':checked')==false){
					errorMessage += "Please Select Accommodation Required or Not in Onward Journey Details-"+onwardPassEngRowNo+"\n";
					flag=false;
				}else{
					if($jq(this).find('td').eq(11).find('input:checkbox:checked').val()==1){
						if($jq(this).find('td').eq(12).find('input:text').is(':visible') &&  $jq(this).find('td').eq(12).find('input:text').val() ==''){
							errorMessage += "Please Enter Accommodation Place in Onward Journey Details-"+onwardPassEngRowNo+"\n";	
							flag=false;
						}
					}
				}

				if($jq(this).find('td').eq(13).find('input:checkbox').is(':visible') && $jq(this).find('td').eq(13).find('input:checkbox').is(':checked')==false){
					errorMessage += "Please Select Article Carry or Not in Onward Journey Details-"+onwardPassEngRowNo+"\n";
					flag=false;
				}
			}
	});
	
	$jq('#returnJourneyDetailsID').find('table').find('tr').each(function(){
		if($jq(this).attr("id") !=''){
			returnPassEngRowNo = $jq(this).attr("id");
		}
		if(returnPassEngRowNo !=""){
			if($jq(this).find('td').eq(0).find('input:text').is(':visible') && $jq(this).find('td').eq(0).find('input:text').val()==''){
				errorMessage += "Please Enter No Of People in Return Journey Details-"+returnPassEngRowNo+"\n";
				flag=false;
			}
			if($jq(this).find('td').eq(1).find('input:text').is(':visible') && $jq(this).find('td').eq(1).find('input:text').val()==''){
				errorMessage += "Please Enter Name With Designation in Return Journey Details-"+returnPassEngRowNo+"\n";
				flag=false;
			}
			if($jq(this).find('td').eq(2).find('input:text').is(':visible') && $jq(this).find('td').eq(2).find('input:text').val()==''){
				errorMessage += "Please Select Travelling Date in Return Journey Details-"+returnPassEngRowNo+"\n";
				flag=false;
			}
			if($jq(this).find('td').eq(3).find('input:text').is(':visible') && $jq(this).find('td').eq(3).find('input:text').val()==''){
				errorMessage += "Please Select Travelling Time in Return Journey Details-"+returnPassEngRowNo+"\n";
				flag=false;
			}
			if($jq(this).find('td').eq(5).find('input:text').is(':visible') && $jq(this).find('td').eq(5).find('input:text').val()==''){
				errorMessage += "Please Select Estimated Date in Return Journey Details-"+returnPassEngRowNo+"\n";
				flag=false;
			}
			if($jq(this).find('td').eq(6).find('input:text').is(':visible') && $jq(this).find('td').eq(6).find('input:text').val()==''){
				errorMessage += "Please Select Estimated Time in Return Journey Details-"+returnPassEngRowNo+"\n";
				flag=false;
			}
			if($jq(this).find('td').eq(7).find('select').is(':visible') && $jq(this).find('td').eq(7).find('select').val()=='0'){
				errorMessage += "Please Select Pickup Point in Return Journey Details-"+returnPassEngRowNo+"\n";
				flag=false;
			}
			if($jq(this).find('td').eq(9).find('select').is(':visible') && $jq(this).find('td').eq(9).find('select').val()=='0'){
				errorMessage += "Please Select Dropping Point in Return Journey Details-"+returnPassEngRowNo+"\n";
				flag=false;
			}
			if($jq(this).find('td').eq(11).find('input:checkbox').is(':visible') && $jq(this).find('td').eq(11).find('input:checkbox').is(':checked')==false){
				errorMessage += "Please Select Accommodation Required or Not in Return Journey Details-"+returnPassEngRowNo+"\n";
				flag=false;
			}else{
				if($jq(this).find('td').eq(11).find('input:checkbox:checked').val()==1){
					if($jq(this).find('td').eq(12).find('input:text').is(':visible') &&  $jq(this).find('td').eq(12).find('input:text').val() ==''){
						errorMessage += "Please Enter Accommodation Place in Return Journey Details-"+returnPassEngRowNo+"\n";	
						flag=false;
					}
				}
			}
		
			if($jq(this).find('td').eq(13).find('input:checkbox').is(':visible') && $jq(this).find('td').eq(13).find('input:checkbox').is(':checked')==false){
				errorMessage += "Please Select Article Carry or Not in Return Journey Details-"+returnPassEngRowNo+"\n";
				flag=false;
			}
			
		}
});
	//time check for Onward Details drid
	$jq('#onwardJourneyDetailsID').find('tr:gt(4)').each(
			function(){	
				onwardPassEngRowNo = $jq(this).attr("id");
				if(parseFloat(compareDates(convertDate($jq(this).find('td').eq(2).find('input').val()),convertDate($jq(this).find('td').eq(5).find('input').val())))==0){
					if($jq.trim($jq(this).find('td').eq(3).find('input').val().split(":")[0])==$jq.trim($jq(this).find('td').eq(6).find('input').val().split(":")[0])){
						if(($jq.trim($jq(this).find('td').eq(3).find('input').val().split(":")[1])==$jq.trim($jq(this).find('td').eq(6).find('input').val().split(":")[1])) || ($jq.trim($jq(this).find('td').eq(3).find('input').val().split(":")[1]) > $jq.trim($jq(this).find('td').eq(6).find('input').val().split(":")[1]))){
							errorMessage += "Estimated Time Must Be Greater Than Travelling Time In Onward Journey Details-"+onwardPassEngRowNo+"\n";
							flag =false;
						}
					}
					if($jq.trim($jq(this).find('td').eq(3).find('input').val().split(":")[0]) > $jq.trim($jq(this).find('td').eq(6).find('input').val().split(":")[0])){
						errorMessage += "Estimated Time Must Be Greater Than Travelling Time In Onward Journey Details-"+onwardPassEngRowNo+"\n";
						   flag =false;
					}
				}
			});
	//time check for return Details drid
	if($jq('#returnJourneyDetailsID').is(':visible')){
	$jq('#returnJourneyDetailsID').find('tr:gt(4)').each(
			function(){	
				returnPassEngRowNo = $jq(this).attr("id");
				if(parseFloat(compareDates(convertDate($jq(this).find('td').eq(2).find('input').val()),convertDate($jq(this).find('td').eq(5).find('input').val())))==0){
					if($jq.trim($jq(this).find('td').eq(3).find('input').val().split(":")[0])==$jq.trim($jq(this).find('td').eq(6).find('input').val().split(":")[0])){
						if(($jq.trim($jq(this).find('td').eq(3).find('input').val().split(":")[1])==$jq.trim($jq(this).find('td').eq(6).find('input').val().split(":")[1])) || ($jq.trim($jq(this).find('td').eq(3).find('input').val().split(":")[1]) > $jq.trim($jq(this).find('td').eq(6).find('input').val().split(":")[1]))){
							errorMessage += "Estimated Time Must Be Greater Than Travelling Time In Return Journey Details-"+returnPassEngRowNo+"\n";
							flag =false;
						}
					}
					if($jq.trim($jq(this).find('td').eq(3).find('input').val().split(":")[0]) > $jq.trim($jq(this).find('td').eq(6).find('input').val().split(":")[0])){
						errorMessage += "Estimated Time Must Be Greater Than Travelling Time In Return Journey Details-"+returnPassEngRowNo+"\n";
						flag =false;
					}
				}
			});
	}
	
	if(flag){
		var onwardMainJSON = {};
		var returnMainJSON = {};
		var onwardPassEngCheck=0;
		var returnPassEngCheck=0;
		
		var onwardPassEngRowNo;
		var returnPassEngRowNo;
		
		$jq('#onwardJourneyDetailsID').find('table').find('tr').each(function(){
			var onwardPassEngRowJSON = {};
			var onwardArticleTabJSON = {};
			var onwardArtCheck=0;
			var subJSON ={};
			
				if($jq(this).attr("id") !=''){
					onwardPassEngRowNo = $jq(this).attr("id").split("_")[1];
					onwardPassEngCheck++
				}
				if(onwardPassEngCheck>0){
					
					if($jq(this).find('td').eq(0).find('input:text').is(':visible')){
						onwardPassEngRowJSON["onwardNOP"] = $jq(this).find('td').eq(0).find('input:text').val();
					}
					if($jq(this).find('td').eq(1).find('input:text').is(':visible')){
						onwardPassEngRowJSON["onwardNameWithDesig"] = $jq(this).find('td').eq(1).find('input:text').val();
					}
					if($jq(this).find('td').eq(2).find('input:text').is(':visible')){
						onwardPassEngRowJSON["onwardDepartureDate"] = $jq(this).find('td').eq(2).find('input:text').val();
					}
					if($jq(this).find('td').eq(3).find('input:text').is(':visible')){
						onwardPassEngRowJSON["onwardDepartureTime"] = $jq(this).find('td').eq(3).find('input:text').val();
					}
					if($jq(this).find('td').eq(5).find('input:text').is(':visible')){
						onwardPassEngRowJSON["onwardEstiDate"] = $jq(this).find('td').eq(5).find('input:text').val();
					}
					if($jq(this).find('td').eq(6).find('input:text').is(':visible')){
						onwardPassEngRowJSON["onwardEstiTime"] = $jq(this).find('td').eq(6).find('input:text').val();
					}
					if($jq(this).find('td').eq(7).find('select').is(':visible')){
						onwardPassEngRowJSON["onwardPickup"] = $jq(this).find('td').eq(7).find('select').val();
					}
					if($jq(this).find('td').eq(8).find('input:text').is(':visible')){
						onwardPassEngRowJSON["onwardOtherSource"] = $jq(this).find('td').eq(8).find('input:text').val();
					}
					if($jq(this).find('td').eq(9).find('select').is(':visible')){
						onwardPassEngRowJSON["onwardDrop"] = $jq(this).find('td').eq(9).find('select').val();
					}
					if($jq(this).find('td').eq(10).find('input:text').is(':visible')){
						onwardPassEngRowJSON["onwardOtherDesti"] = $jq(this).find('td').eq(10).find('input:text').val();
					}
					if($jq(this).find('td').eq(11).find('input:checkbox').is(':visible')){
						onwardPassEngRowJSON["onwardAccomReq"] = $jq(this).find('td').eq(11).find('input:checkbox:checked').val();
						if($jq(this).find('td').eq(11).find('input:checkbox:checked').val()==1){
							if($jq(this).find('td').eq(12).find('input:text').is(':visible') &&  $jq(this).find('td').eq(12).find('input:text').val() !=''){
								onwardPassEngRowJSON["onwardAccomPlace"] = $jq(this).find('td').eq(12).find('input:text').val();
							}
						}
					}
					
					if($jq(this).find('td').eq(13).find('input:checkbox').is(':visible')){
						onwardPassEngRowJSON["onwardArtiReq"] = $jq(this).find('td').eq(13).find('input:checkbox:checked').val();
					}
					if($jq(this).find('td').eq(14).find('input:text').is(':visible')){
						onwardPassEngRowJSON["onwardRemarks"] = $jq(this).find('td').eq(14).find('input:text').val();
					}
					
					
					if(onwardPassEngRowJSON.onwardArtiReq == '1'){
						$jq('#passeng'+onwardPassEngRowNo).find('table').find('tr').each(function(){
							var onwardArticleRowJSON = {};
							if($jq(this).attr("id") !=''){
								onwardArtCheck++
							}
							if(onwardArtCheck>0){
								if($jq(this).find('td').eq(0).find('select').is(':visible')){
									onwardArticleRowJSON["onwardArtType"] = $jq(this).find('td').eq(0).find('select').val();
								}
								if($jq(this).find('td').eq(1).find('input:text').is(':visible')){
									onwardArticleRowJSON["onwardArtLength"] = $jq(this).find('td').eq(1).find('input:text').val();
								}
								if($jq(this).find('td').eq(2).find('input:text').is(':visible')){
									onwardArticleRowJSON["onwardArtBreadth"] = $jq(this).find('td').eq(2).find('input:text').val();
								}
								if($jq(this).find('td').eq(3).find('input:text').is(':visible')){
									onwardArticleRowJSON["onwardArtHeight"] = $jq(this).find('td').eq(3).find('input:text').val();
								}
								if($jq(this).find('td').eq(4).find('input:text').is(':visible')){
									onwardArticleRowJSON["onwardArtWeight"] = $jq(this).find('td').eq(4).find('input:text').val();
								}
								if($jq(this).find('td').eq(5).find('input:text').is(':visible')){
									onwardArticleRowJSON["onwardArtQuantity"] = $jq(this).find('td').eq(5).find('input:text').val();
								}
								
								onwardArticleTabJSON[onwardArtCheck]=onwardArticleRowJSON;
							}
						});
					}
					
					subJSON["onwardPassEngRowJSON"]=onwardPassEngRowJSON;
					subJSON["onwardArticleTabJSON"]=onwardArticleTabJSON;
					
					onwardMainJSON[onwardPassEngCheck]=subJSON;
				}
			
		});
		if($jq('#returnJourneyDetailsID').is(':visible')){
			$jq('#returnJourneyDetailsID').find('table').find('tr').each(function(){
				var returnPassEngRowJSON = {};
				var returnArticleTabJSON = {};
				var returnArtCheck=0;
				var subJSON ={};
				
				
					if($jq(this).attr("id") !=''){
						returnPassEngRowNo = $jq(this).attr("id").split("_")[1];
						returnPassEngCheck++
					}
					if(returnPassEngCheck>0){
						
						if($jq(this).find('td').eq(0).find('input:text').is(':visible')){
							returnPassEngRowJSON["returnNOP"] = $jq(this).find('td').eq(0).find('input:text').val();
						}
						if($jq(this).find('td').eq(1).find('input:text').is(':visible')){
							returnPassEngRowJSON["returnNameWithDesig"] = $jq(this).find('td').eq(1).find('input:text').val();
						}
						if($jq(this).find('td').eq(2).find('input:text').is(':visible')){
							returnPassEngRowJSON["returnDepartureDate"] = $jq(this).find('td').eq(2).find('input:text').val();
						}
						if($jq(this).find('td').eq(3).find('input:text').is(':visible')){
							returnPassEngRowJSON["returnDepartureTime"] = $jq(this).find('td').eq(3).find('input:text').val();
						}
						if($jq(this).find('td').eq(5).find('input:text').is(':visible')){
							returnPassEngRowJSON["returnEstiDate"] = $jq(this).find('td').eq(5).find('input:text').val();
						}
						if($jq(this).find('td').eq(6).find('input:text').is(':visible')){
							returnPassEngRowJSON["returnEstiTime"] = $jq(this).find('td').eq(6).find('input:text').val();
						}
						if($jq(this).find('td').eq(7).find('select').is(':visible')){
							returnPassEngRowJSON["returnPickup"] = $jq(this).find('td').eq(7).find('select').val();
						}
						if($jq(this).find('td').eq(8).find('input:text').is(':visible')){
							returnPassEngRowJSON["returnOtherSource"] = $jq(this).find('td').eq(8).find('input:text').val();
						}
						if($jq(this).find('td').eq(9).find('select').is(':visible')){
							returnPassEngRowJSON["returnDrop"] = $jq(this).find('td').eq(9).find('select').val();
						}
						if($jq(this).find('td').eq(10).find('input:text').is(':visible')){
							returnPassEngRowJSON["returnOtherDesti"] = $jq(this).find('td').eq(10).find('input:text').val();
						}
						if($jq(this).find('td').eq(11).find('input:checkbox').is(':visible')){
							returnPassEngRowJSON["returnAccomReq"] = $jq(this).find('td').eq(11).find('input:checkbox:checked').val();
						}
						if($jq(this).find('td').eq(12).find('input:text').is(':visible') &&  $jq(this).find('td').eq(12).find('input:text').val() !=''){
							returnPassEngRowJSON["returnAccomPlace"] = $jq(this).find('td').eq(12).find('input:text').val();
						}
						if($jq(this).find('td').eq(13).find('input:checkbox').is(':visible')){
							returnPassEngRowJSON["returnArtiReq"] = $jq(this).find('td').eq(13).find('input:checkbox:checked').val();
						}
						if($jq(this).find('td').eq(14).find('input:text').is(':visible')){
							returnPassEngRowJSON["returnRemarks"] = $jq(this).find('td').eq(14).find('input:text').val();
						}
						
						
						if(returnPassEngRowJSON.returnArtiReq == '1'){
							$jq('#Rpasseng'+returnPassEngRowNo).find('table').find('tr').each(function(){
								var returnArticleRowJSON = {};
								if($jq(this).attr("id") !=''){
									returnArtCheck++
								}
								if(returnArtCheck>0){
									if($jq(this).find('td').eq(0).find('select').is(':visible')){
										returnArticleRowJSON["returnArtType"] = $jq(this).find('td').eq(0).find('select').val();
									}
									if($jq(this).find('td').eq(1).find('input:text').is(':visible')){
										returnArticleRowJSON["returnArtLength"] = $jq(this).find('td').eq(1).find('input:text').val();
									}
									if($jq(this).find('td').eq(2).find('input:text').is(':visible')){
										returnArticleRowJSON["returnArtBreadth"] = $jq(this).find('td').eq(2).find('input:text').val();
									}
									if($jq(this).find('td').eq(3).find('input:text').is(':visible')){
										returnArticleRowJSON["returnArtHeight"] = $jq(this).find('td').eq(3).find('input:text').val();
									}
									if($jq(this).find('td').eq(4).find('input:text').is(':visible')){
										returnArticleRowJSON["returnArtWeight"] = $jq(this).find('td').eq(4).find('input:text').val();
									}
									if($jq(this).find('td').eq(5).find('input:text').is(':visible')){
										returnArticleRowJSON["returnArtQuantity"] = $jq(this).find('td').eq(5).find('input:text').val();
									}
									
									returnArticleTabJSON[returnArtCheck]=returnArticleRowJSON;
								}
							});
						}
						
						subJSON["returnPassEngRowJSON"]=returnPassEngRowJSON;
						subJSON["returnArticleTabJSON"]=returnArticleTabJSON;
						
						returnMainJSON[returnPassEngCheck]=subJSON;
					}
				
			});
			
		}
		
		
		$jq('#param').val('SaveVehicleRequestDetails');
		$jq('#onwardMainJSON').val(JSON.stringify(onwardMainJSON));
		$jq('#returnMainJSON').val(JSON.stringify(returnMainJSON));
		$jq.post('transport.htm', $jq('#mtBean').serialize(), function(html) {
			if(html.indexOf('success')!=-1){
				clearVehicleRequestDetails();
			}
			
			$jq("#result").html(html);
			var check=confirm(" Vehicle Requisition has been Successfully Sent...\n\nPlease click ok to 'Preview Vehicle Requisition Form 'Take print' from here\n\n");
			if(check){
			document.forms[0].requestId.value = $jq.trim(requestIDMT);
			//document.forms[0].roleId.value = 'roleId';
		   document.forms[0].param.value = "requestDetails";
			document.forms[0].action = "workflowmap.htm";
			document.forms[0].submit();	
			}
			
		});
		
		
		
	}else{
		alert(errorMessage);
	}
	
}

function clearVehicleRequestDetails(){
	$jq("input:hidden").val('');
	$jq("input:text").val('');
	$jq('#purposeOfVisiting').val('');
	$jq('#counter').val(100);
	$jq("select").val('Select');
	$jq("input:radio").attr("checked", false);
	$jq("input:checkbox").attr("checked", false);
	$jq('input:radio[id=vehicleReturn1]').attr('checked',true);
	checkReturnVehicleRequired('NO');
	
}

function showOnwardPassEngDetails(){
	$jq('#hideOnwardPassEngDiv').toggle();
	$jq('#showOnwardPassEngDiv').toggle();
	$jq('#onwardJourneyDetailsID').toggle();
}
function showReturnPassEngDetails(){
	$jq('#hideReturnPassEngDiv').toggle();
	$jq('#showReturnPassEngDiv').toggle();
	$jq('#returnJourneyDetailsID').toggle();
}

function showOnwardArticleDetails(jID){
	$jq('#onwardArticleDetailsID').html('');
	createOnwardArticleDiv(jID);
	$jq('#hideOnwardArticleDiv'+jID).toggle();
	$jq('#showOnwardArticleDiv'+jID).toggle();
	$jq('#onwardArticleDetailsID').toggle();
}
function showReturnArticleDetails(jID){
	createReturnArticleDiv(jID);
	$jq('#hideReturnArticleDiv'+jID).toggle();
	$jq('#showReturnArticleDiv'+jID).toggle();
	$jq('#returnArticleDetailsID').toggle();
}

function createOnwardArticleDiv(jID){
	$jq('#onwardArticleDetailsID').html('');
	var onwardPassEngName = "";
	var row = "";
	var temp = "";
	for(var i=0; i<requesterJSON.mtJourneyDetails.length ; i++){
		if(requesterJSON.mtJourneyDetails[i].id==jID){
			onwardPassEngName = requesterJSON.mtJourneyDetails[i].nameWithDesignation;
			for(var j=0; j<requesterJSON.mtJourneyDetails[i].mtArticleDetails.length ; j++){
				if(requesterJSON.mtJourneyDetails[i].mtArticleDetails[j].journeyTypeFlag == "ONWARD"){
					temp +="<tr>";
					temp +="<td>"+requesterJSON.mtJourneyDetails[i].mtArticleDetails[j].type+"</td>";
					temp +="<td>"+requesterJSON.mtJourneyDetails[i].mtArticleDetails[j].length+"</td>";
					temp +="<td>"+requesterJSON.mtJourneyDetails[i].mtArticleDetails[j].breadth+"</td>";
					temp +="<td>"+requesterJSON.mtJourneyDetails[i].mtArticleDetails[j].height+"</td>";
					temp +="<td>"+requesterJSON.mtJourneyDetails[i].mtArticleDetails[j].weight+"</td>";
					temp +="<td>"+requesterJSON.mtJourneyDetails[i].mtArticleDetails[j].quantity+"</td>";
					temp +="</tr>";
				}
			}
		}
	}
	row += "<fieldset><legend><strong><font color=green>"+onwardPassEngName+" Article Details</font></strong></legend>";
	row +="<table  width=100% border=2px cellpadding=0px cellspacing=0px align=center>";
	
	row +="<tr>";
		row +="<td class=tabletd align=center style=width:15%;>Type</td>";
		row +="<td class=tabletd align=center style=width:15%;>Length (CM)</td>";
		row +="<td class=tabletd align=center style=width:15%;>Breadth (CM)</td>";
		row +="<td class=tabletd align=center style=width:15%;>Height (CM)</td>";
		row +="<td class=tabletd align=center style=width:15%;>Weight (KG)</td>";
		row +="<td class=tabletd align=center style=width:15%;>Quantity</td>";
	row +="</tr>";
	/*
	 * row +="<tr>"; row +="<td></td>"; row +="<td></td>"; row +="<td></td>";
	 * row +="<td></td>"; row +="<td></td>"; row +="<td></td>"; row +="</tr>";
	 */
	row +=temp;
row +="</table>";
row += "</fieldset>";
$jq('#onwardArticleDetailsID').html(row);
}

function createReturnArticleDiv(jID){
	$jq('#returnArticleDetailsID').html('');
	var returnPassEngName = "";
	var row = "";
	var temp = "";
	for(var i=0; i<requesterJSON.mtJourneyDetails.length ; i++){
		if(requesterJSON.mtJourneyDetails[i].id==jID){
			returnPassEngName = requesterJSON.mtJourneyDetails[i].nameWithDesignation;
			for(var j=0; j<requesterJSON.mtJourneyDetails[i].mtArticleDetails.length ; j++){
				if(requesterJSON.mtJourneyDetails[i].mtArticleDetails[j].journeyTypeFlag == "RETURN"){
					temp +="<tr>";
					temp +="<td>"+requesterJSON.mtJourneyDetails[i].mtArticleDetails[j].type+"</td>";
					temp +="<td>"+requesterJSON.mtJourneyDetails[i].mtArticleDetails[j].length+"</td>";
					temp +="<td>"+requesterJSON.mtJourneyDetails[i].mtArticleDetails[j].breadth+"</td>";
					temp +="<td>"+requesterJSON.mtJourneyDetails[i].mtArticleDetails[j].height+"</td>";
					temp +="<td>"+requesterJSON.mtJourneyDetails[i].mtArticleDetails[j].weight+"</td>";
					temp +="<td>"+requesterJSON.mtJourneyDetails[i].mtArticleDetails[j].quantity+"</td>";
					temp +="</tr>";
				}
			}
		}
	}
	row += "<fieldset><legend><strong><font color=green>"+returnPassEngName+" Article Details</font></strong></legend>";
	row +="<table  width=100% border=2px cellpadding=0px cellspacing=0px align=center>";
	
	row +="<tr>";
		row +="<td class=tabletd align=center style=width:15%;>Type</td>";
		row +="<td class=tabletd align=center style=width:15%;>Length (CM)</td>";
		row +="<td class=tabletd align=center style=width:15%;>Breadth (CM)</td>";
		row +="<td class=tabletd align=center style=width:15%;>Height (CM)</td>";
		row +="<td class=tabletd align=center style=width:15%;>Weight (KG)</td>";
		row +="<td class=tabletd align=center style=width:15%;>Quantity</td>";
	row +="</tr>";
	/*
	 * row +="<tr>"; row +="<td></td>"; row +="<td></td>"; row +="<td></td>";
	 * row +="<td></td>"; row +="<td></td>"; row +="<td></td>"; row +="</tr>";
	 */
	row +=temp;
row +="</table>";
row += "</fieldset>";
$jq('#returnArticleDetailsID').html(row);
}

function showOnwardPassEngVehicleAllotGrid(RID){
	$jq('#OnwardPassEngVehicleAllotGrid').html('');
	$jq('#OnwardVehicleAllotmentDetailsGrid').html('');
	$jq('#OnwardCombineVehicleDetailsGrid').html('');
	$jq('#onwardSubmitDiv').css("display","none");
	$jq('#onwardSearchDivId').html('');
	$jq('#onwardSearch').css("display","none");
	
var temp = "";
var temp2="";
//var count=0;
var allocationFlag=false;
	temp += "<div class=line>";
	temp += "<fieldset><legend><strong><font color=green>Onward Journey Details </font></strong></legend>";
	temp += "<div class=line>";
	temp += "<table class=fortable id=journeyDetailsID width=100% border=2px cellpadding=0px cellspacing=0px align=center>";
		temp += "<tr>";
			// temp += "<td rowspan=3 class=tabletd align=center><input
			// type=checkbox id="+RID+"_ONWARD
			// onchange=javascript:checkAllCheckboxes('OnwardPassEngVehicleAllotGrid','"+RID+"_ONWARD');
			// /></td>";
		    temp += "<td rowspan=3 class=tabletd align=center></td>";
		    temp += "<td rowspan=3 class=tabletd align=center><font size=-3px;>No of People</font></td>";
			temp += "<td rowspan=3 class=tabletd align=center>Name With Designation</td>";
			temp += "<td colspan=4 class=tabletd align=center >Departure/Arrival</td>";
			temp += "<td rowspan=2 colspan=2 class=tabletd align=center>Pickup Place</td>";
			temp += "<td rowspan=2 colspan=2 class=tabletd align=center>Dropping Place</td>";
			temp += "<td rowspan=3 class=tabletd align=center>Accom Place</td>";
			temp += "<td rowspan=3 class=tabletd align=center>Article Carry</td>";
			temp += "<td rowspan=3 class=tabletd align=center>Remarks (Mobile No/ Flight No)</td>";
			
		temp += "</tr>";
			temp += "<tr>";
			temp += "<td colspan=2 class=tabletd align=center>Travelling</td>";
			temp += "<td colspan=2 class=tabletd align=center>Estimated</td>";
		temp += "</tr>";
		temp += "<tr>";
			temp += "<td class=tabletd align=center>Date</td>";
			temp += "<td class=tabletd align=center>Time</td>";
			temp += "<td class=tabletd align=center>Date</td>";
			temp += "<td class=tabletd align=center>Time</td>";
			temp += "<td class=tabletd align=center>Pickup</td>";
			temp += "<td class=tabletd align=center>Other Source</td>";
			temp += "<td class=tabletd align=center>Drop</td>";
			temp += "<td class=tabletd align=center>Other Destin</td>";
		temp += "</tr>";
		
		temp2+= "<div class=line>";
		temp2 += "<table id=onwardAlloc class=fortable width=100% border=2px align=center>";
		temp2 += "<tr>";
		temp2 += "<td class=tabletd align=center>Vehicle Allocated For</td>";
		temp2 += "<td class=tabletd align=center>Allocated Vehicle No</td>";
		temp2 += "<td class=tabletd align=center>Allocated Driver Name</td>";
		temp2 += "<td class=tabletd align=center>Mobile No</td>";
		temp2 += "</tr>";
		
		
		for (var i=0; i<ApprovedReqJSON.length; i++){
			if(ApprovedReqJSON[i].requestID == RID){
				count = 1;
				for(var j=0; j<ApprovedReqJSON[i].mtJourneyDetails.length; j++){
					if(ApprovedReqJSON[i].mtJourneyDetails[j].journeyTypeFlag == "ONWARD"){
						temp += "<tr>";
						if(ApprovedReqJSON[i].mtJourneyDetails[j].status==8){
							allocationFlag=true;
							if(temp2==''){
								temp2 += "<div class=line>";
								temp2 += "<table class=fortable width=100% border=2px align=center>";
								temp2 += "<tr>";
								temp2 += "<td class=tabletd align=center>Vehicle Allocated For</td>";
								temp2 += "<td class=tabletd align=center>Allocated Vehicle No</td>";
								temp2 += "<td class=tabletd align=center>Allocated Driver Name</td>";
								temp2 += "<td class=tabletd align=center>Mobile No</td>";
								temp2 += "</tr>";
							}
						temp += "<td align=center><input type=checkbox checked='checked' disabled='disabled' id="+RID+"_"+ApprovedReqJSON[i].mtJourneyDetails[j].id+"  onchange=javascript:checkCheckbox('"+RID+"_"+ApprovedReqJSON[i].mtJourneyDetails[j].id+"','"+RID+"_ONWARD'); /></td>";
						temp2 += "<tr>";
						temp2+= "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].nameWithDesignation+"</td>";
						temp2+= "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.vehicleDetails.vehicleNo+"</td>";
						temp2+= "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.diverDetails.driverName+"</td>";
						temp2+= "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.diverDetails.driverMobileNo+"</td>";
						temp2 += "</tr>";
						}else{
							if(allocationFlag==false){
							temp2="";
						    }
						// temp2="";
							temp += "<td align=center><input type=checkbox id="+RID+"_"+ApprovedReqJSON[i].mtJourneyDetails[j].id+"  onchange=javascript:checkCheckbox('"+RID+"_"+ApprovedReqJSON[i].mtJourneyDetails[j].id+"','"+RID+"_ONWARD'); /></td>";
						}
						temp += "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].noOfPeople+"</td>";
						temp += "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].nameWithDesignation+"</td>";
						temp += "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].departureDateString+"</td>";
						temp += "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].departureTime+"</td>";
						temp += "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].estimatedDateString+"</td>";
						temp += "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].estimatedTime+"</td>";
						temp += "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].mtPickAddressDetails.addressName+"</td>";
						temp += "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].pickupPlace+"</td>";
						temp += "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].mtDropAddressDetails.addressName+"</td>";
						temp += "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].dropPlace+"</td>";
						if(ApprovedReqJSON[i].mtJourneyDetails[j].accommReqFlag == '1'){
						 temp += "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].accommPlace+"</td>";	
						}else {
						 temp += "<td align=center>**NO**</td>";
						}
						if(ApprovedReqJSON[i].mtJourneyDetails[j].articleCarryFlag == '1'){
							temp += "<td align=center><a href=javascript:showOnwardPassEngArticleGrid('"+RID+"','"+ApprovedReqJSON[i].mtJourneyDetails[j].id+"');><span id=hideOnwardArticleDiv"+ApprovedReqJSON[i].mtJourneyDetails[j].id+" style=display:none>hide</span><span id=showOnwardArticleDiv"+ApprovedReqJSON[i].mtJourneyDetails[j].id+">show</span></a></td>";	
						}else {
							temp += "<td align=center>**NO**</td>";
						}
						
						temp += "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].remarks+"</td>";
					temp += "</tr>";
					
					
					}
				}
				
			}
		}
		
		temp2 += "</table>";
		temp2 += "</div>";
		
		temp += "</table>";		
		temp += "</div>";
		temp += "</fieldset>";
		temp += temp2;
		temp += "</div>";
		
		//if(count==1){
	$jq('#OnwardPassEngVehicleAllotGrid').html(temp);
		//}
	
}

function showOnwardPassEngArticleGrid(RID,jid){
	$jq('#hideOnwardArticleDiv'+jid).toggle();
	$jq('#showOnwardArticleDiv'+jid).toggle();
	$jq('#OnwardPassEngArticleGrid').toggle();
	$jq('#OnwardPassEngArticleGrid').html('');
	var temp ="";
	var temp1 ="";
	var onwardPassEngName = "";
	
	for (var i=0; i<ApprovedReqJSON.length; i++){
		if(ApprovedReqJSON[i].requestID == RID){
			for(var j=0; j<ApprovedReqJSON[i].mtJourneyDetails.length; j++){
				if(ApprovedReqJSON[i].mtJourneyDetails[j].id == jid){
					onwardPassEngName = ApprovedReqJSON[i].mtJourneyDetails[j].nameWithDesignation;
					if(ApprovedReqJSON[i].mtJourneyDetails[j].articleCarryFlag == "1"){
						for(var k=0; k<ApprovedReqJSON[i].mtJourneyDetails[j].mtArticleDetails.length ; k++){
							if(ApprovedReqJSON[i].mtJourneyDetails[j].mtArticleDetails[k].journeyTypeFlag == "ONWARD"){
								temp +="<tr>";
								temp +="<td>"+ApprovedReqJSON[i].mtJourneyDetails[j].mtArticleDetails[k].type+"</td>";
								temp +="<td>"+ApprovedReqJSON[i].mtJourneyDetails[j].mtArticleDetails[k].length+"</td>";
								temp +="<td>"+ApprovedReqJSON[i].mtJourneyDetails[j].mtArticleDetails[k].breadth+"</td>";
								temp +="<td>"+ApprovedReqJSON[i].mtJourneyDetails[j].mtArticleDetails[k].height+"</td>";
								temp +="<td>"+ApprovedReqJSON[i].mtJourneyDetails[j].mtArticleDetails[k].weight+"</td>";
								temp +="<td>"+ApprovedReqJSON[i].mtJourneyDetails[j].mtArticleDetails[k].quantity+"</td>";
								temp +="</tr>";
							}
						}
					}
				}
			}
		}
	}
	temp1 += "<div class=line>";
	temp1 += "<fieldset><legend><strong><font color=green>"+onwardPassEngName+" Article Details</font></strong></legend>";
	temp1 +="<table  width=100% border=2px cellpadding=0px cellspacing=0px align=center>";
			
	temp1 +="<tr>";
	temp1 +="<td class=tabletd align=center style=width:15%;>Type</td>";
	temp1 +="<td class=tabletd align=center style=width:15%;>Length (CM)</td>";
	temp1 +="<td class=tabletd align=center style=width:15%;>Breadth (CM)</td>";
	temp1 +="<td class=tabletd align=center style=width:15%;>Height (CM)</td>";
	temp1 +="<td class=tabletd align=center style=width:15%;>Weight (KG)</td>";
	temp1 +="<td class=tabletd align=center style=width:15%;>Quantity</td>";
	temp1 +="</tr>";
		
	temp1 +=temp;
	temp1 +="</table>";
	temp1 += "</fieldset>";
	temp1 += "</div>";
	
	$jq('#OnwardPassEngArticleGrid').html(temp1);
}

function showReturnPassEngVehicleAllotGrid(RID){
	$jq('#ReturnPassEngVehicleAllotGrid').html('');
	$jq('#ReturnVehicleAllotmentDetailsGrid').html('');
	$jq('#ReturnCombineVehicleDetailsGrid').html('');
	$jq('#returnSubmitDiv').css("display","none");
	$jq('#returnSearchDivId').html('');
	$jq('#returnSearch').css("display","none");
	
var temp = "";
var temp2 = "";
var allocationFlag=false;
	temp += "<div class=line>";
	temp += "<fieldset><legend><strong><font color=green>Return Journey Details </font></strong></legend>";
	temp += "<div class=line>";
	temp += "<table class=fortable id=journeyDetailsID width=100% border=2px cellpadding=0px cellspacing=0px align=center>";
		temp += "<tr>";
			// temp += "<td rowspan=3 class=tabletd align=center><input
			// type=checkbox id="+RID+"_RETURN
			// onchange=javascript:checkAllCheckboxes('ReturnPassEngVehicleAllotGrid','"+RID+"_RETURN');
			// /></td>";
		 temp += "<td rowspan=3 class=tabletd align=center></td>";	
		temp += "<td rowspan=3 class=tabletd align=center><font size=-3px;>No of People</font></td>";
			temp += "<td rowspan=3 class=tabletd align=center>Name With Designation</td>";
			temp += "<td colspan=4 class=tabletd align=center >Departure/Arrival</td>";
			temp += "<td rowspan=2 colspan=2 class=tabletd align=center>Pickup Place</td>";
			temp += "<td rowspan=2 colspan=2 class=tabletd align=center>Dropping Place</td>";
			temp += "<td rowspan=3 class=tabletd align=center>Accom Place</td>";
			temp += "<td rowspan=3 class=tabletd align=center>Article Carry</td>";
			temp += "<td rowspan=3 class=tabletd align=center>Remarks (Mobile No/ Flight No)</td>";
		temp += "</tr>";
			temp += "<tr>";
			temp += "<td colspan=2 class=tabletd align=center>Travelling</td>";
			temp += "<td colspan=2 class=tabletd align=center>Estimated</td>";
		temp += "</tr>";
		temp += "<tr>";
			temp += "<td class=tabletd align=center>Date</td>";
			temp += "<td class=tabletd align=center>Time</td>";
			temp += "<td class=tabletd align=center>Date</td>";
			temp += "<td class=tabletd align=center>Time</td>";
			temp += "<td class=tabletd align=center>Pickup</td>";
			temp += "<td class=tabletd align=center>Other Source</td>";
			temp += "<td class=tabletd align=center>Drop</td>";
			temp += "<td class=tabletd align=center>Other Destin</td>";
		temp += "</tr>";
		
		temp2+= "<div class=line>";
		temp2 += "<table class=fortable width=100% border=2px align=center>";
		temp2 += "<tr>";
		temp2 += "<td class=tabletd align=center>Vehicle Allocated For</td>";
		temp2 += "<td class=tabletd align=center>Allocated Vehicle No</td>";
		temp2 += "<td class=tabletd align=center>Allocated Driver Name</td>";
		temp2 += "<td class=tabletd align=center>Mobile No</td>";
		temp2 += "</tr>";
		for (var i=0; i<ApprovedReqJSON.length; i++){
			if(ApprovedReqJSON[i].requestID == RID){
				for(var j=0; j<ApprovedReqJSON[i].mtJourneyDetails.length; j++){
					if(ApprovedReqJSON[i].mtJourneyDetails[j].journeyTypeFlag == "RETURN"){
						temp += "<tr>";
						if(ApprovedReqJSON[i].mtJourneyDetails[j].status==8){
							allocationFlag=true;
							if(temp2==''){
								temp2 += "<div class=line>";
								temp2 += "<table class=fortable width=100% border=2px align=center>";
								temp2 += "<tr>";
								temp2 += "<td class=tabletd align=center>Vehicle Allocated For</td>";
								temp2 += "<td class=tabletd align=center>Allocated Vehicle No</td>";
								temp2 += "<td class=tabletd align=center>Allocated Driver Name</td>";
								temp2 += "<td class=tabletd align=center>Mobile No</td>";
								temp2 += "</tr>";
							}
							temp += "<td align=center><input type=checkbox checked='checked' disabled='disabled' id="+RID+"_"+ApprovedReqJSON[i].mtJourneyDetails[j].id+" onchange=javascript:checkCheckbox('"+RID+"_"+ApprovedReqJSON[i].mtJourneyDetails[j].id+"','"+RID+"_RETURN'); /></td>";
							temp2 += "<tr>";
							temp2+= "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].nameWithDesignation+"</td>";
							temp2+= "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.vehicleDetails.vehicleNo+"</td>";
							temp2+= "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.diverDetails.driverName+"</td>";
							temp2+= "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.diverDetails.driverMobileNo+"</td>";
							temp2 += "</tr>";
							}else{
								if(allocationFlag==false){
									temp2="";
								    }
								temp += "<td align=center><input type=checkbox id="+RID+"_"+ApprovedReqJSON[i].mtJourneyDetails[j].id+" onchange=javascript:checkCheckbox('"+RID+"_"+ApprovedReqJSON[i].mtJourneyDetails[j].id+"','"+RID+"_RETURN'); /></td>";
							}
						// temp += "<td align=center><input type=checkbox
						// id="+RID+"_"+ApprovedReqJSON[i].mtJourneyDetails[j].id+"
						// onchange=javascript:checkCheckbox('"+RID+"_"+ApprovedReqJSON[i].mtJourneyDetails[j].id+"','"+RID+"_RETURN');
						// /></td>";
						temp += "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].noOfPeople+"</td>";
						temp += "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].nameWithDesignation+"</td>";
						temp += "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].departureDateString+"</td>";
						temp += "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].departureTime+"</td>";
						temp += "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].estimatedDateString+"</td>";
						temp += "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].estimatedTime+"</td>";
						temp += "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].mtPickAddressDetails.addressName+"</td>";
						temp += "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].pickupPlace+"</td>";
						temp += "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].mtDropAddressDetails.addressName+"</td>";
						temp += "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].dropPlace+"</td>";
						if(ApprovedReqJSON[i].mtJourneyDetails[j].accommReqFlag == '1'){
						 temp += "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].accommPlace+"</td>";	
						}else if(ApprovedReqJSON[i].mtJourneyDetails[j].accommReqFlag == '0'){
						 temp += "<td align=center>**NO**</td>";
						}
						if(ApprovedReqJSON[i].mtJourneyDetails[j].articleCarryFlag == '1'){
							temp += "<td align=center><a href=javascript:showReturnPassEngArticleGrid('"+RID+"','"+ApprovedReqJSON[i].mtJourneyDetails[j].id+"');><span id=hideReturnArticleDiv"+ApprovedReqJSON[i].mtJourneyDetails[j].id+" style=display:none>hide</span><span id=showReturnArticleDiv"+ApprovedReqJSON[i].mtJourneyDetails[j].id+">show</span></a></td>";	
						}else if(ApprovedReqJSON[i].mtJourneyDetails[j].accommReqFlag == '0'){
							temp += "<td align=center>**NO**</td>";
						}
						
						temp += "<td align=center>"+ApprovedReqJSON[i].mtJourneyDetails[j].remarks+"</td>";
					temp += "</tr>";
					
					}
				}
			}
		}
		
		
		temp += "</table>";		
		temp += "</div>";
		temp += "</fieldset>";
		temp += temp2;
		temp += "</div>";
	
	$jq('#ReturnPassEngVehicleAllotGrid').html(temp);
	
}

function showReturnPassEngArticleGrid(RID,jid){
	
	$jq('#hideReturnArticleDiv'+jid).toggle();
	$jq('#showReturnArticleDiv'+jid).toggle();
	$jq('#ReturnPassEngArticleGrid').toggle();
	$jq('#ReturnPassEngArticleGrid').html('');
	var temp ="";
	var temp1 ="";
	var returnPassEngName = "";
	
	for (var i=0; i<ApprovedReqJSON.length; i++){
		if(ApprovedReqJSON[i].requestID == RID){
			for(var j=0; j<ApprovedReqJSON[i].mtJourneyDetails.length; j++){
				if(ApprovedReqJSON[i].mtJourneyDetails[j].id == jid){
					returnPassEngName = ApprovedReqJSON[i].mtJourneyDetails[j].nameWithDesignation;
					if(ApprovedReqJSON[i].mtJourneyDetails[j].articleCarryFlag == "1"){
						for(var k=0; k<ApprovedReqJSON[i].mtJourneyDetails[j].mtArticleDetails.length ; k++){
							if(ApprovedReqJSON[i].mtJourneyDetails[j].mtArticleDetails[k].journeyTypeFlag == "RETURN"){
								temp +="<tr>";
								temp +="<td>"+ApprovedReqJSON[i].mtJourneyDetails[j].mtArticleDetails[k].type+"</td>";
								temp +="<td>"+ApprovedReqJSON[i].mtJourneyDetails[j].mtArticleDetails[k].length+"</td>";
								temp +="<td>"+ApprovedReqJSON[i].mtJourneyDetails[j].mtArticleDetails[k].breadth+"</td>";
								temp +="<td>"+ApprovedReqJSON[i].mtJourneyDetails[j].mtArticleDetails[k].height+"</td>";
								temp +="<td>"+ApprovedReqJSON[i].mtJourneyDetails[j].mtArticleDetails[k].weight+"</td>";
								temp +="<td>"+ApprovedReqJSON[i].mtJourneyDetails[j].mtArticleDetails[k].quantity+"</td>";
								temp +="</tr>";
							}
						}
					}
				}
			}
		}
	}
	temp1 += "<div class=line>";
	temp1 += "<fieldset><legend><strong><font color=green>"+returnPassEngName+" Article Details</font></strong></legend>";
	temp1 +="<table  width=100% border=2px cellpadding=0px cellspacing=0px align=center>";
			
	temp1 +="<tr>";
	temp1 +="<td class=tabletd align=center style=width:15%;>Type</td>";
	temp1 +="<td class=tabletd align=center style=width:15%;>Length (CM)</td>";
	temp1 +="<td class=tabletd align=center style=width:15%;>Breadth (CM)</td>";
	temp1 +="<td class=tabletd align=center style=width:15%;>Height (CM)</td>";
	temp1 +="<td class=tabletd align=center style=width:15%;>Weight (KG)</td>";
	temp1 +="<td class=tabletd align=center style=width:15%;>Quantity</td>";
	temp1 +="</tr>";
		
	temp1 +=temp;
	temp1 +="</table>";
	temp1 += "</fieldset>";
	temp1 += "</div>";
	
	$jq('#ReturnPassEngArticleGrid').html(temp1);
}
function onwardGrid(RID){
	showOnwardPassEngVehicleAllotGrid(RID);
	$jq('#hideOnwardGrid'+RID).toggle();
	$jq('#showOnwardGrid'+RID).toggle();
	$jq('#OnwardGrid').toggle();
	$jq('#OnwardVehicleAllotmentDetailsGrid').toggle();
	/*
	 * if($jq('#OnwardVehicleAllotmentDetailsGrid').is(':visible')){
	 * $jq('#onwardSubmitDiv').css("display","block"); }else{
	 * $jq('#onwardSubmitDiv').css("display","none"); }
	 */
	
}
function returnGrid(RID){
	showReturnPassEngVehicleAllotGrid(RID);
	$jq('#hideReturnGrid'+RID).toggle();
	$jq('#showReturnGrid'+RID).toggle();
	$jq('#ReturnGrid').toggle();
	$jq('#ReturnVehicleAllotmentDetailsGrid').toggle();
}
function checkAllCheckboxes(jTypeId,RID){
	
	if($jq('#'+RID).is(':checked')){
		$jq('#'+jTypeId).find('table').find('tr').each(function(){
			$jq(this).find('input:checkbox').attr("checked",true);
		});
	}else{
		$jq('#'+jTypeId).find('table').find('tr').each(function(){
			$jq(this).find('input:checkbox').attr("checked",false);
		});
	}

}
function searchAvailableVehicles(jTypeId,RID){
var type = RID.split("_")[1];
	
	
var searchFromDate="";
var searchFromTime="";
var searchToDate="";
var searchToTime="";

var searchFromDateId="";
var searchFromTimeId="";
var searchToDateId="";
var searchToTimeId="";
var errMsg="";
var flag=true;

    if(type == "ONWARD"){
    	$jq("#OnwardCombineVehicleDetailsGrid").html('');
            searchFromDateId="searchFromDate";
    		searchFromTimeId="searchFromTime";
    		searchToDateId="searchToDate";
    		searchToTimeId="searchToTime";	
    }
    if(type == "RETURN"){
    	 $jq("#ReturnCombineVehicleDetailsGrid").html('');
    	 searchFromDateId="RsearchFromDate";
 		searchFromTimeId="RsearchFromTime";
 		searchToDateId="RsearchToDate";
 		searchToTimeId="RsearchToTime";	
    }
    
    //for dates check
    var fdate=convertDate($jq('#'+searchFromDateId).val());
    var tdate=convertDate($jq('#'+searchToDateId).val());
    if(compareDates(fdate,'dd-MM-yyyy',tdate,'dd-MM-yyyy')==1){
    	errMsg += "To Date Must be Greater Than From Date \n";
			flag = false;
	}else if(compareDates(tdate,'dd-MM-yyyy',fdate,'dd-MM-yyyy')!=1){
		var ftime=$jq('#'+searchFromTimeId).val();
		var ttime=$jq('#'+searchToTimeId).val();
		if(compareHoursTime(ftime.split(":")[0],ttime.split(":")[0])==1){
			errMsg += "To Time Must be Greater Than From Time \n";
			flag = false;
		}else if(compareHoursTime(ftime.split(":")[0],ttime.split(":")[0])==0){
			if(compareMinutesTime(ftime.split(":")[1],ttime.split(":")[1])==1 || compareMinutesTime(ftime.split(":")[1],ttime.split(":")[1])==0){
				errMsg += "To Time Must be Greater Than From Time \n";
				flag = false;
			}
		}
	}	
    
    //for from time check
    if($jq('#'+searchFromTimeId).val()==''){
		errMsg += "Enter Search From Time To Get Available Vehicles\n";
		flag=false;
	}else {
		if( $jq('#'+searchFromTimeId).val().indexOf(':')==-1){
			errMsg += "Enter From Time In HH:MM formate\n";
			$jq('#'+searchFromTimeId).focus();
			flag = false;
		}else{
			if($jq('#'+searchFromTimeId).val().split(':')[0].trim().length!=2 || $jq('#'+searchFromTimeId).val().split(':')[1].trim().length!=2){
				errMsg += "Enter From Time In HH:MM formate\n";
				$jq('#'+searchFromTimeId).focus();
				flag = false;
			}
		if($jq('#'+searchFromTimeId).val().split(':')[0]<0 || $jq('#'+searchFromTimeId).val().split(':')[0]>23){
			errMsg += "Enter Valid Hours For Search From Time\n";
			$jq('#'+searchFromTimeId).focus();
    		flag=false;
		}
		if(($jq('#'+searchFromTimeId).val().split(':')[0]==0 && $jq('#'+searchFromTimeId).val().split(':')[1]<=0) || $jq('#'+searchFromTimeId).val().split(':')[1]>59){
			errMsg += "Enter Valid Minutes For Search From Time\n";
			$jq('#'+searchFromTimeId).focus();
    		flag=false;
		}
		}
	}
    
    //for to time check
    if($jq('#'+searchToTimeId).val()==''){
		errMsg += "Enter Search To Time To Get Available Vehicles\n";
		flag=false;
	}else {
		if( $jq('#'+searchToTimeId).val().indexOf(':')==-1){
			errMsg += "Enter ToTime In HH:MM formate\n";
			$jq('#'+searchToTimeId).focus();
			flag = false;
		}else{
			if($jq('#'+searchToTimeId).val().split(':')[0].trim().length!=2 || $jq('#'+searchToTimeId).val().split(':')[1].trim().length!=2){
				errMsg += "Enter To Time In HH:MM formate\n";
				$jq('#'+searchToTimeId).focus();
				flag = false;
			}
		if($jq('#'+searchToTimeId).val().split(':')[0]<0 || $jq('#'+searchToTimeId).val().split(':')[0]>23){
			errMsg += "Enter Valid Hours For Search To Time\n";
			$jq('#'+searchToTimeId).focus();
    		flag=false;
		}
		if(($jq('#'+searchToTimeId).val().split(':')[0]==0 && $jq('#'+searchToTimeId).val().split(':')[1]<=0) || $jq('#'+searchToTimeId).val().split(':')[1]>59){
			errMsg += "Enter Valid Minutes For Search To Time\n";
			$jq('#'+searchToTimeId).focus();
    		flag=false;
		}
		}
	}
    
	if(flag){
	 searchFromDate=$jq('#'+searchFromDateId).val();
	 searchFromTime=$jq('#'+searchFromTimeId).val();
	 searchToDate=$jq('#'+searchToDateId).val();
	 searchToTime=$jq('#'+searchToTimeId).val();
	}
    
    
    if(flag){
		var requestDetails = {
				"param" : "getVDList",
				"pk" :jTypeId,
				"type" : type,
				"searchFromDate":searchFromDate,
				"searchFromTime":searchFromTime,
			    "searchToDate":searchToDate,
			    "searchToTime":searchToTime
		};
		$jq.post('transport.htm', requestDetails, function(html) {
			//for display busy vehicles
			var temp = "<div class=line><fieldset style=float:left;width:100%><legend><strong><font color=green>Busy Vehicle-Driver Details </font></strong></legend>";
			temp += "<div class=line><table id=busyVehicles class=fortable width=100% border=2px align=center>";
			temp += "<tr>";
			temp += "<td class=tabletd align=center>Select</td>";
			temp += "<td class=tabletd align=center>Vehicle No</td>";
			temp += "<td class=tabletd align=center>Driver Name</td>";
			temp += "<td class=tabletd align=center>From Date</td>";
			temp += "<td class=tabletd align=center>To Date</td>";
			temp += "<td class=tabletd align=center>Vehicle Capacity</td>";
			temp += "<td class=tabletd align=center>Allocated No.Of Persons</td>";
			temp += "</tr>";
			
			
			
				
				
			if(type == "ONWARD"){
				$jq("#OnwardVehicleAllotmentDetailsGrid").html(html);
				$jq("#OnwardVehicleAllotmentDetailsGrid").css("display","block");
				if(BusyVDListJSON.length>0){
					//for onward busy details table
					for(var k=0;k<BusyVDListJSON.length;k++){
						temp += "<tr>";
					temp += "<td align=center><input type=checkbox id="+BusyVDListJSON[k].vehicleDriverMapId+" onchange=allotOrCombineButtons('"+type+"','combineAlloc')></input></td>";
						//temp += "<td align=center><input type=checkbox id="+BusyVDListJSON[k].id+" onchange=allotOrCombineButtons('"+type+"','combineAlloc')></input></td>";
						temp += "<td align=center>"+BusyVDListJSON[k].vehicleDriverMapDetails.vehicleDetails.vehicleNo+"("+BusyVDListJSON[k].vehicleDriverMapDetails.vehicleDetails.vehiclePoolTypeDetails.vehiclePoolType+")</td>";
						temp += "<td align=center>"+BusyVDListJSON[k].vehicleDriverMapDetails.diverDetails.driverName+"</td>";
						temp += "<td align=center>"+BusyVDListJSON[k].stringFromDate+"</td>";
						temp += "<td align=center>"+BusyVDListJSON[k].stringToDate+"</td>";
						temp += "<td align=center>"+BusyVDListJSON[k].vehicleDriverMapDetails.vehicleDetails.noOfPeople+"</td>";
						temp += "<td align=center>"+BusyVDListJSON[k].allotedNoOfPersons+"</td>";
						temp += "</tr>";
					}
				}else{
					temp += "<td colspan=7>Nothing found to display</td>";
				}
				
				temp += "</table></div></fieldset></div>";
				 $jq("#OnwardCombineVehicleDetailsGrid").append(temp);
				$jq("#OnwardCombineVehicleDetailsGrid").css("display","block");
				//$jq('#onwardSubmitDiv').css("display","block");
			}
			if(type == "RETURN"){
				$jq("#ReturnVehicleAllotmentDetailsGrid").html(html);
				$jq("#ReturnVehicleAllotmentDetailsGrid").css("display","block");
				if(BusyVDListJSON.length>0){
					//for return busy details table
					for(var k=0;k<BusyVDListJSON.length;k++){
						temp += "<tr>";
					   // temp += "<td align=center><input type=checkbox id="+BusyVDListJSON[k].id+" onchange=allotOrCombineButtons('"+type+"','combineAlloc')></input></td>";
						temp += "<td align=center><input type=checkbox id="+BusyVDListJSON[k].vehicleDriverMapId+" onchange=allotOrCombineButtons('"+type+"','combineAlloc')></input></td>";
						
						temp += "<td align=center>"+BusyVDListJSON[k].vehicleDriverMapDetails.vehicleDetails.vehicleNo+"("+BusyVDListJSON[k].vehicleDriverMapDetails.vehicleDetails.vehiclePoolTypeDetails.vehiclePoolType+")</td>";
						temp += "<td align=center>"+BusyVDListJSON[k].vehicleDriverMapDetails.diverDetails.driverName+"</td>";
						temp += "<td align=center>"+BusyVDListJSON[k].stringFromDate+"</td>";
						temp += "<td align=center>"+BusyVDListJSON[k].stringToDate+"</td>";
						temp += "<td align=center>"+BusyVDListJSON[k].vehicleDriverMapDetails.vehicleDetails.noOfPeople+"</td>";
						temp += "<td align=center>"+BusyVDListJSON[k].allotedNoOfPersons+"</td>";
						temp += "</tr>";
					}
				}else{
					temp += "<td colspan=7>Nothing found to display</td>";
				}
				
				temp += "</table></div></fieldset></div>";
				 $jq("#ReturnCombineVehicleDetailsGrid").append(temp);
				 $jq("#ReturnCombineVehicleDetailsGrid").css("display","block");
				//$jq('#returnSubmitDiv').css("display","block");
			}
			
		});
    }else{
    	alert(errMsg);
    }
	}
    
	
/*function checkCheckbox(jTypeId,RID){
	
	var type = RID.split("_")[1];
	
	//uncheck journey grid
	if($jq('#'+jTypeId).is(':checked')==false){
		$jq('#'+RID).attr("checked",false);
     if(type == "ONWARD"){
			$jq("#OnwardVehicleAllotmentDetailsGrid").html('');
			$jq("#OnwardCombineVehicleDetailsGrid").html('');
			$jq('#onwardSubmitDiv').css("display","none");
		}
		if(type == "RETURN"){
			$jq("#ReturnVehicleAllotmentDetailsGrid").html('');
			$jq("#ReturnCombineVehicleDetailsGrid").html('');
			$jq('#returnSubmitDiv').css("display","none");
		}
	}
	
	//check journey grid
	if($jq('#'+jTypeId).is(':checked')){
		if(type == "ONWARD"){
			$jq("#OnwardVehicleAllotmentDetailsGrid").html('');
		}
		if(type == "RETURN"){
			$jq("#ReturnVehicleAllotmentDetailsGrid").html('');
		}
		
		var requestDetails = {
				"param" : "getVDList",
				"pk" :jTypeId,
				"type" : type,
				"searchFromDate":$jq('#searchFromDate').val(),
				"searchFromTime":$jq('#searchFromTime').val(),
			    "searchToDate":$jq('#searchToDate').val(),
			    "searchToTime":$jq('#searchToTime').val()
		};
		$jq.post('transport.htm', requestDetails, function(html) {
			//for display busy vehicles
			var temp = "<div class=line><fieldset style=float:left;width:100%><legend><strong><font color=green>Busy Vehicle-Driver Details </font></strong></legend>";
			temp += "<div class=line><table id=busyVehicles class=fortable width=100% border=2px align=center>";
			temp += "<tr>";
			temp += "<td class=tabletd align=center>Select</td>";
			temp += "<td class=tabletd align=center>Vehicle No</td>";
			temp += "<td class=tabletd align=center>Driver Name</td>";
			temp += "<td class=tabletd align=center>From Date</td>";
			temp += "<td class=tabletd align=center>To Date</td>";
			temp += "<td class=tabletd align=center>Vehicle Capacity</td>";
			temp += "<td class=tabletd align=center>Allocated No.Of Persons</td>";
			temp += "</tr>";
			
			
			
				
				
			if(type == "ONWARD"){
				$jq("#OnwardVehicleAllotmentDetailsGrid").html(html);
				$jq("#OnwardVehicleAllotmentDetailsGrid").css("display","block");
				if(BusyVDListJSON.length>0){
					//for onward busy details table
					for(var k=0;k<BusyVDListJSON.length;k++){
						temp += "<tr>";
						temp += "<td align=center><input type=checkbox id="+BusyVDListJSON[k].id+" onchange=allotOrCombineButtons('"+type+"','combineAlloc')></input></td>";
						temp += "<td align=center>"+BusyVDListJSON[k].vehicleDriverMapDetails.vehicleDetails.vehicleNo+"("+BusyVDListJSON[k].vehicleDriverMapDetails.vehicleDetails.vehiclePoolTypeDetails.vehiclePoolType+")</td>";
						temp += "<td align=center>"+BusyVDListJSON[k].vehicleDriverMapDetails.diverDetails.driverName+"</td>";
						temp += "<td align=center>"+BusyVDListJSON[k].stringFromDate+"</td>";
						temp += "<td align=center>"+BusyVDListJSON[k].stringToDate+"</td>";
						temp += "<td align=center>"+BusyVDListJSON[k].vehicleDriverMapDetails.vehicleDetails.noOfPeople+"</td>";
						temp += "<td align=center>"+BusyVDListJSON[k].allotedNoOfPersons+"</td>";
						temp += "</tr>";
					}
				}else{
					temp += "<td colspan=7>Nothing found to display</td>";
				}
				
				temp += "</table></div></fieldset></div>";
				 $jq("#OnwardCombineVehicleDetailsGrid").append(temp);
				$jq("#OnwardCombineVehicleDetailsGrid").css("display","block");
				//$jq('#onwardSubmitDiv').css("display","block");
			}
			if(type == "RETURN"){
				$jq("#ReturnVehicleAllotmentDetailsGrid").html(html);
				$jq("#ReturnVehicleAllotmentDetailsGrid").css("display","block");
				if(BusyVDListJSON.length>0){
					//for return busy details table
					for(var k=0;k<BusyVDListJSON.length;k++){
						temp += "<tr>";
						temp += "<td align=center><input type=checkbox id="+BusyVDListJSON[k].id+" onchange=allotOrCombineButtons('"+type+"','combineAlloc')></input></td>";
						temp += "<td align=center>"+BusyVDListJSON[k].vehicleDriverMapDetails.vehicleDetails.vehicleNo+"("+BusyVDListJSON[k].vehicleDriverMapDetails.vehicleDetails.vehiclePoolTypeDetails.vehiclePoolType+")</td>";
						temp += "<td align=center>"+BusyVDListJSON[k].vehicleDriverMapDetails.diverDetails.driverName+"</td>";
						temp += "<td align=center>"+BusyVDListJSON[k].stringFromDate+"</td>";
						temp += "<td align=center>"+BusyVDListJSON[k].stringToDate+"</td>";
						temp += "<td align=center>"+BusyVDListJSON[k].vehicleDriverMapDetails.vehicleDetails.noOfPeople+"</td>";
						temp += "<td align=center>"+BusyVDListJSON[k].allotedNoOfPersons+"</td>";
						temp += "</tr>";
					}
				}else{
					temp += "<td colspan=7>Nothing found to display</td>";
				}
				
				temp += "</table></div></fieldset></div>";
				 $jq("#ReturnCombineVehicleDetailsGrid").append(temp);
				 $jq("#ReturnCombineVehicleDetailsGrid").css("display","block");
				//$jq('#returnSubmitDiv').css("display","block");
			}
			
		});
	}
}*/
//give search option
function checkCheckbox(jTypeId,RID){
	var journeyGridId="";
	var temp="";
	var fromDate="";
	var fromTime="";
	var toDate="";
	var toTime="";
	
	/*$jq('#onwardSearchDivId').html('');
	$jq('#returnSearchDivId').html('');*/
	var type = RID.split("_")[1];
	//unchecked
	if($jq('#'+jTypeId).is(':checked')==false){
		$jq('#'+RID).attr("checked",false);
     if(type == "ONWARD"){
			$jq("#OnwardVehicleAllotmentDetailsGrid").html('');
			$jq("#OnwardCombineVehicleDetailsGrid").html('');
			$jq('#onwardSubmitDiv').css("display","none");
			$jq('#onwardSearchDivId').html('');
			$jq('#onwardSearch').css("display","none");
			
		}
		if(type == "RETURN"){
			$jq("#ReturnVehicleAllotmentDetailsGrid").html('');
			$jq("#ReturnCombineVehicleDetailsGrid").html('');
			$jq('#returnSubmitDiv').css("display","none");
			$jq('#returnSearchDivId').html('');
			$jq('#returnSearch').css("display","none");
		}
	}
	//checked
	if($jq('#'+jTypeId).is(':checked')){
		if(type == "ONWARD"){
			journeyGridId="OnwardPassEngVehicleAllotGrid";
			$jq('#onwardSearchDivId').html('');
			$jq('#onwardSearch').css("display","block");
			//for hide available,busy list when check another checkbox
			$jq('#OnwardVehicleAllotmentDetailsGrid').html('');
			$jq('#OnwardCombineVehicleDetailsGrid').html('');
			
		}
		if(type == "RETURN"){
			journeyGridId="ReturnPassEngVehicleAllotGrid";
			$jq('#returnSearchDivId').html('');
			$jq('#returnSearch').css("display","block");
			//for hide available,busy list when check another checkbox
			$jq('#ReturnVehicleAllotmentDetailsGrid').html('');
			$jq('#ReturnCombineVehicleDetailsGrid').html('');
		}
		$jq('#'+journeyGridId).find('tr:gt(2)').each(function(){
			if($jq(this).find('td').eq(0).find('input').attr('id')!=jTypeId){
			$jq(this).find('td').eq(0).find('input:checkbox').attr('checked',false);
			}
		});
		
		$jq('#'+journeyGridId).find('tr:gt(2)').each(function(){
			if($jq(this).find('td').eq(0).find('input').attr('id')==jTypeId){
				fromDate=$jq(this).find('td').eq(3).text();
				fromTime=$jq(this).find('td').eq(4).text();
				toDate=	$jq(this).find('td').eq(5).text();
				toTime=$jq(this).find('td').eq(6).text();
					
				temp += "<a onclick=searchAvailableVehicles('"+jTypeId+"','"+RID+"')><span>Get Available Vehicles</span></a>";
			}
		});
		if(type == "ONWARD"){
			$jq('#searchFromDate').val(fromDate);
			$jq('#searchFromTime').val(fromTime);
			$jq('#searchToDate').val(toDate);
			$jq('#searchToTime').val(toTime);
			
			$jq('#onwardSearchDivId').append(temp);
		}
		if(type == "RETURN"){
			$jq('#RsearchFromDate').val(fromDate);
			$jq('#RsearchFromTime').val(fromTime);
			$jq('#RsearchToDate').val(toDate);
			$jq('#RsearchToTime').val(toTime);
			
			$jq('#returnSearchDivId').append(temp);
		}
		
	}
	
	/*$jq('#'+journeyGridId).find('tr:gt(2)').each(function(){
		if($jq(this).find('td').eq(0).attr('checked',true)){
			
			temp += "<a onclick='searchAvailableVehicles('onward')'><span>Search</span></a>";
		}
	});*/
	
	//<a onclick="saveCombineAlloc('onward')"><span>Search</span></a>
}
function getAvailableDrivers(id){
	var errorMessage="";
	var flag=true;
	var temp="";
	// $jq('#driverId'+id).html('');
	if($jq('#fromDate'+id).val()==''){
		errorMessage += "Please Select Date \n"
			flag=false;
	}
	if($jq('#fromTime'+id).val()==''){
		errorMessage += "Please Select Time \n"
			flag=false;
	}
	if(flag){
		var fromDate= $jq('#fromDate'+id).val();
		var fromTime= $jq('#fromTime'+id).val();
		var requestDetails = {
				"param" : "getAvailableDrivers",
				"fromDate" : $jq('#fromDate'+id).val(),
				"fromTime" : $jq('#fromTime'+id).val()
		};
		$jq.post('transport.htm', requestDetails, function(html) {
			$jq('#result').html(html);
			$jq('#fromDate'+id).val(fromDate);
			$jq('#fromTime'+id).val(fromTime);
			for(var i=0;i<AvailableDriversJson.length;i++){
				var checkFlag = true;
				
				for(var j=0;j<DriverAbsentJson.length;j++){
					if(AvailableDriversJson[i].value==DriverAbsentJson[j].key){
						//temp += "<option value='0' style='color: red;'>Remove Driver</option>";
						temp += "<option value='"+AvailableDriversJson[i].value+"' style='color: red;'>"+AvailableDriversJson[i].name+"("+DriverAbsentJson[j].name+" To"+DriverAbsentJson[j].value+")</option>";
						checkFlag = false;
					}
				}
				if(checkFlag){
					temp += "<option value='"+AvailableDriversJson[i].value+"'>"+AvailableDriversJson[i].name+"</option>";
				}
				
			}
			$jq('#driverId'+id).append(temp);
		if(AvailableDriversJson.length>0){
			$jq('#getDriversDiv'+id).css('display','none');
			$jq('#changeDriverDiv'+id).css('display','block');
		}
			
		});
	}else{
		alert(errorMessage);
	}
	
}
// kumari
function saveVehicleAllocationDetails(type){
	
	var journeyId;
	var mapId;
	var fromDate;
	var fromTime;
	var toDate;
	var toTime;
	var journeyDetailsGridId;
	var vehicleDetailsGridId;
	var requestId;
	var journeyFlag=false;
	var vehicleFlag=false;
	var errMessage="";
	var errMessage1="";
	
	
	
	if(type=='onward'){
		journeyDetailsGridId="OnwardPassEngVehicleAllotGrid";
		vehicleDetailsGridId="OnwardVehicleAllotmentDetailsGrid";
	}else if(type=='return'){
		journeyDetailsGridId="ReturnPassEngVehicleAllotGrid";
		vehicleDetailsGridId="ReturnVehicleAllotmentDetailsGrid";
	}
	// for alerts
	$jq('#'+journeyDetailsGridId).find('tr:gt(2)').each(function(){
		
			if($jq(this).find('td').eq(0).find('input:checkbox').is(':checked') && $jq(this).find('td').eq(0).find('input:checkbox').attr('disabled')==false){
				if(journeyFlag==true && errMessage==''){
					errMessage += "Please select only one row on journy details grid\n";
				}else{
					journeyFlag=true;
				}
				
			}
		
	});
	$jq('#'+vehicleDetailsGridId).find('tr:gt(0)').each(function(){
		
		if($jq(this).find('td').eq(0).find('input:checkbox').is(':checked')){
			if(vehicleFlag==true && errMessage1==''){
				errMessage1 += "Please select only one vehicle from available vehicle-driver details grid\n";
			}else{
				vehicleFlag=true;
			}
			
		}
	
});
	if(journeyFlag==false){
		errMessage += "Please select atleast one row on journy details grid\n";
	}
	else if(vehicleFlag==false){
		errMessage += "Please select atleast one vehicle from available vehicle-driver details grid\n";
	}
	errMessage += errMessage1;
	
	if(errMessage==""){
	$jq('#'+journeyDetailsGridId).find('tr:gt(2)').each(function(){
		if($jq(this).find('td').eq(0).find('input:checkbox').is(':checked') && $jq(this).find('td').eq(0).find('input:checkbox').attr('disabled')==false){
			journeyId=$jq(this).find('td').eq(0).find('input:checkbox').attr('id');
			/*fromDate=$jq(this).find('td').eq(3).text();
			fromTime=$jq(this).find('td').eq(4).text();
			toDate=$jq(this).find('td').eq(5).text();
			toTime=$jq(this).find('td').eq(6).text();*/
			
			//provide search functionality
			if(type=='onward'){
			fromDate=$jq("#searchFromDate").val();
			fromTime=$jq("#searchFromTime").val();
			toDate=$jq("#searchToDate").val();
			toTime=$jq("#searchToTime").val();
			}
			if(type=='return'){
				fromDate=$jq("#RsearchFromDate").val();
				fromTime=$jq("#RsearchFromTime").val();
				toDate=$jq("#RsearchToDate").val();
				toTime=$jq("#RsearchToTime").val();
				}
			requestId=journeyId.split("_")[0];
		}
	});
	$jq('#'+vehicleDetailsGridId).find('tr:gt(0)').each(function(){
		if($jq(this).find('td').eq(0).find('input:checked').is(':checked')){
			mapId=$jq(this).find('td').eq(0).find('input:checked').attr('id');
		}
	});	
	
	var requestDetails = {
			"param" : "saveVehicleAllocationDetails",
			"journeyId" :journeyId.split("_")[1],
			"mapId" : mapId,
			"fromDate" : fromDate,
			"fromTime" : fromTime,
			"toDate" : toDate,
			"toTime" : toTime
	};
	$jq.post('transport.htm', requestDetails, function(html) {
		//$jq('#result').html(html);
		//var temp2 = "<div class='line'><div>"+html+"</div></div>";
		$jq('#fragment-1').html(html);
		
		showOnwardPassEngVehicleAllotGrid(requestId);
		showReturnPassEngVehicleAllotGrid(requestId);
	});
}else{
	alert(errMessage);
}
}
var onwardDisplayId="";
var returnDisplayId="";
/*function onwardAllocation(requestId,type){
	var temp="";
	
	
	$jq('#OnwardAllocation').html('');
	$jq('#OnwardAllocation').css('display','none');
	temp+= "<div class=line>";
	temp += "<table id=onwardAlloc class=fortable width=100% border=2px align=center>";
	temp += "<tr>";
	temp += "<td class=tabletd align=center>Vehicle Allocated For</td>";
	temp += "<td class=tabletd align=center>Allocated Vehicle No</td>";
	temp += "<td class=tabletd align=center>Allocated Driver Name</td>";
	temp += "<td class=tabletd align=center>Mobile No</td>";
	temp += "</tr>";
	for (var i=0; i<AllocatedReqJSON.length; i++){
		if(AllocatedReqJSON[i].requestID == requestId){
			for(var j=0; j<AllocatedReqJSON[i].mtJourneyDetails.length; j++){
				if(AllocatedReqJSON[i].mtJourneyDetails[j].journeyTypeFlag == "ONWARD"){
					temp += "<tr>";
					temp+= "<td align=center>"+AllocatedReqJSON[i].mtJourneyDetails[j].nameWithDesignation+"</td>";
					temp+= "<td align=center>"+AllocatedReqJSON[i].mtJourneyDetails[j].mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.vehicleDetails.vehicleNo+"</td>";
					temp+= "<td align=center>"+AllocatedReqJSON[i].mtJourneyDetails[j].mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.diverDetails.driverName+"</td>";
					temp+= "<td align=center>"+AllocatedReqJSON[i].mtJourneyDetails[j].mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.diverDetails.driverMobileNo+"</td>";
					temp += "</tr>";
					temp+= "</div";
					temp += "</table>";
					
				}
			}
		}
	}
	$jq('#OnwardAllocation').append(temp);
	if(type=='show'){
		if(onwardDisplayId!=''){
			$jq('#onwardShow'+onwardDisplayId).css('display','block');
			$jq('#onwardHide'+onwardDisplayId).css('display','none');
		}
		onwardDisplayId=requestId;
		$jq('#onwardShow'+requestId).css('display','none');
		$jq('#onwardHide'+requestId).css('display','block');
		$jq('#OnwardAllocation').css('display','block');
	}
	if(type=='hide'){
		$jq('#onwardHide'+requestId).css('display','none');
		$jq('#onwardShow'+requestId).css('display','block');
		$jq('#OnwardAllocation').css('display','none');
	}
}*/
function onwardAllocation(requestId,type){
	var temp = "";
	var temp2="";
	var temp3="";
	if(type=='show'){
		$jq('#onwardShow'+requestId).css('display','none');
		$jq('#onwardHide'+requestId).css('display','block');
	}
	if(type=='hide'){
		$jq('#onwardShow'+requestId).css('display','block');
		$jq('#onwardHide'+requestId).css('display','none');
		$jq('#OnwardAllocation').html('');
		$jq('#OnwardAllocation').css('display','none');
	} 
	
	if(type=='show'){
	$jq('#OnwardAllocation').html('');
	$jq('#OnwardAllocation').css('display','none');
	
	temp += "<div class=line>";
	temp += "<fieldset><legend><strong><font color=green>Onward Journey Details </font></strong></legend>";
	temp += "<div class=line>";
	temp += "<table class=fortable id=onwardJourneyDetailsID width=100% border=2px cellpadding=0px cellspacing=0px align=center>";
	temp += "<tr>";
	temp +="<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>";
	temp +="</tr>";
	
		temp += "<tr>";
			
		   
		    temp += "<td rowspan=3 class=tabletd align=center><font size=-3px;>No of People</font></td>";
			temp += "<td rowspan=3 class=tabletd align=center>Name With Designation</td>";
			temp += "<td colspan=5 class=tabletd align=center >Departure/Arrival</td>";
			temp += "<td rowspan=2 colspan=2 class=tabletd align=center>Pickup Place</td>";
			temp += "<td rowspan=2 colspan=2 class=tabletd align=center>Dropping Place</td>";
			temp += "<td rowspan=3 class=tabletd align=center>Accom Required</td>";
			temp += "<td rowspan=3 class=tabletd align=center>Accom Place</td>";
			temp += "<td rowspan=3 class=tabletd align=center>Article Carry</td>";
			temp += "<td rowspan=3 class=tabletd align=center>Remarks (Mobile No/ Flight No)</td>";
			temp += "<td rowspan=3 class=tabletd align=center></td>";
			
		temp += "</tr>";
			temp += "<tr>";
			temp += "<td colspan=2 class=tabletd align=center>Travelling</td>";
			temp += "<td colspan=1 class=tabletd align=center>Duration</td>";
			temp += "<td colspan=2 class=tabletd align=center>Estimated</td>";
		temp += "</tr>";
		temp += "<tr>";
			temp += "<td class=tabletd align=center>Date</td>";
			temp += "<td class=tabletd align=center>Time</td>";
			temp +="<td class=tabletd align=center>HH:MM</td>";
			temp += "<td class=tabletd align=center>Date</td>";
			temp += "<td class=tabletd align=center>Time</td>";
			temp += "<td class=tabletd align=center>Pickup</td>";
			temp += "<td class=tabletd align=center>Other Source</td>";
			temp += "<td class=tabletd align=center>Drop</td>";
			temp += "<td class=tabletd align=center>Other Destin</td>";
		temp += "</tr>";
		temp += "<tr>";
		temp +="<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>";
		temp +="</tr>";	
		
		temp2+= "<div class=line>";
		temp2 += "<table id=onwardAlloc class=fortable width=100% border=2px align=center>";
		temp2 += "<tr>";
		temp2 += "<td class=tabletd align=center>Vehicle Allocated For</td>";
		temp2 += "<td class=tabletd align=center>Allocated Vehicle No</td>";
		temp2 += "</tr>";
		
		 temp3 +="<div  id=onwardArticleDetailsID class=line>";
		  temp3 +="<fieldset><legend><strong><font color=green>Onward Article Details </font></strong></legend>";
		  temp3 +="<div class=line>";
		  temp3 +="<table  class=fortable width=100%>";
		
		for (var i=0; i<AllocatedReqJSON.length; i++){
			if(AllocatedReqJSON[i].requestID == requestId){
				//count = 1;
				for(var j=0; j<AllocatedReqJSON[i].mtJourneyDetails.length; j++){
					if(AllocatedReqJSON[i].mtJourneyDetails[j].journeyTypeFlag == "ONWARD"){
						temp += "<tr id=row_"+j+">";
					
						temp += "<td>";
						temp +="<input type=text id=passengNo"+j+" style=width:25px; onkeypress='return checkInt(event);' value="+AllocatedReqJSON[i].mtJourneyDetails[j].noOfPeople+"></input>";
						temp +="</td>";
						
						temp += "<td>";
						temp +="<input type=text id=passengName"+j+" style=width:auto; value="+AllocatedReqJSON[i].mtJourneyDetails[j].nameWithDesignation+"></input>";
						temp += "</td>";
						
						temp += "<td>";
						temp += "<input type=text readonly=readonly style=height:16px;width:64px;font-size: 9px;font-weight: bold; id=startDate"+j+" onmouseover=Calendar.setup({inputField:'startDate"+j+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'startDate"+j+"',step:1}); onchange=checkReqDates(\'onward\');mtAddHoursMin("+j+",'onward');durationOfDates("+j+",'onward'); value="+AllocatedReqJSON[i].mtJourneyDetails[j].departureDateString+"></input>";
						temp += "</td>";
					
						temp += "<td>";
						temp +="<input type=text id=startTime"+j+" class =startTime style=width:37px onkeyup=checkReqDates(\'onward\');mtAddHoursMin("+j+",'onward');durationOfDates("+j+",'onward'); value="+AllocatedReqJSON[i].mtJourneyDetails[j].departureTime+"></input>";
						temp += "</td>";
						
						temp += "<td>";
						temp +="<input type=text id=durationH"+j+" style=width:18px onkeyup=mtAddHoursMin("+j+",'onward') maxlength=3></input>";
						temp +="<input type=text id=durationM"+j+" style=width:18px onkeyup=mtAddHoursMin("+j+",'onward') maxlength=2></input>";
						temp += "</td>";
						
						temp += "<td>";
						temp += "<input type=text readonly=readonly style=height:16px;width:64px;font-size: 9px;font-weight: bold; id=estDate"+j+" onmouseover=Calendar.setup({inputField:'estDate"+j+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'estDate"+j+"',step:1}); onchange=checkReqDates(\'onward\');durationOfDates("+j+",'onward'); value="+AllocatedReqJSON[i].mtJourneyDetails[j].estimatedDateString+"></input>";
						temp += "</td>";
					
						temp += "<td>";
						temp +="<input type=text id=estTime"+j+" class =estTime style=width:37px  onkeyup=checkReqDates(\'onward\');durationOfDates("+j+",'onward'); value="+AllocatedReqJSON[i].mtJourneyDetails[j].estimatedTime+"></input>";
						temp += "</td>";
						
						temp += "<td>";
						temp +="<select id=pickupPoint"+j+"  style=width:80px;>";
						temp +="<option value="+AllocatedReqJSON[i].mtJourneyDetails[j].mtPickAddressDetails.addressId+">"+AllocatedReqJSON[i].mtJourneyDetails[j].mtPickAddressDetails.addressName+"</option>";
								for(var m=0; m<addressJSON.length; m++){
									temp +="<option value="+addressJSON[m].addressId+">"+addressJSON[m].addressName+"</option>";			
								}
								temp +="</select>";
								temp += "</td>";
					
						temp += "<td>";
						temp +="<input type=text  id=otherSourceName"+j+" style=width:50px; value="+AllocatedReqJSON[i].mtJourneyDetails[j].pickupPlace+"></input>";
						temp += "</td>";
						
						temp += "<td>";
						temp +="<select id=droppingPoint"+j+"  style=width:80px;>";
						temp +="<option value="+AllocatedReqJSON[i].mtJourneyDetails[j].mtDropAddressDetails.addressId+">"+AllocatedReqJSON[i].mtJourneyDetails[j].mtDropAddressDetails.addressName+"</option>";
								for(var m=0; m<addressJSON.length; m++){
									temp +="<option value="+addressJSON[m].addressId+">"+addressJSON[m].addressName+"</option>";	
								}
								temp +="</select>";
								temp += "</td>";
						
						temp += "<td>";
						temp +="<input type=text  id=otherDestiName"+j+" style=width:50px; value="+AllocatedReqJSON[i].mtJourneyDetails[j].dropPlace+"></input>";
						temp += "</td>";
						
						temp += "<td>";
						if(AllocatedReqJSON[i].mtJourneyDetails[j].accommReqFlag == '1'){
						temp += "<input type=checkbox  id=accomReq1"+j+" value=1 onclick=javascript:checkAccomdationRequired('YES','"+j+"'); checked='checked'>Y</input>";
						temp += "<input type=checkbox  id=accomReq2"+j+" value=0  onclick=javascript:checkAccomdationRequired('NO','"+j+"');>N</input>";
						}else {
							temp += "<input type=checkbox  id=accomReq1"+j+" value=1 onclick=javascript:checkAccomdationRequired('YES','"+j+"');>Y</input>";
						temp += "<input type=checkbox  id=accomReq2"+j+" value=0  onclick=javascript:checkAccomdationRequired('NO','"+j+"'); checked='checked'>N</input>";
						}
						temp += "</td>";
					
						temp += "<td>";
						temp += "<input type=text  id=accomPlace"+j+" style=width:50px; value="+AllocatedReqJSON[i].mtJourneyDetails[j].accommPlace+"></input>";
						temp += "</td>";
						
						temp += "<td>";
						if(AllocatedReqJSON[i].mtJourneyDetails[j].articleCarryFlag == '1'){
						temp += "<input type=checkbox  id=articleCarry1"+j+" value=1 onclick=javascript:checkArticleRequired('YES','"+j+"');editCheckArticle('onward','YES','"+j+"','"+requestId+"_"+AllocatedReqJSON[i].mtJourneyDetails[j].id+"'); checked='checked'>Y</input>";
						temp += "<input type=checkbox  id=articleCarry2"+j+" value=0  onclick=javascript:checkArticleRequired('NO','"+j+"');editCheckArticle('onward','NO','"+j+"','"+requestId+"_"+AllocatedReqJSON[i].mtJourneyDetails[j].id+"');>N</input>";
						}else {
						temp += "<input type=checkbox  id=articleCarry1"+j+" value=1 onclick=javascript:checkArticleRequired('YES','"+j+"');editCheckArticle('onward','YES','"+j+"','"+requestId+"_"+AllocatedReqJSON[i].mtJourneyDetails[j].id+"');>Y</input>";
						temp += "<input type=checkbox  id=articleCarry2"+j+" value=0  onclick=javascript:checkArticleRequired('NO','"+j+"');editCheckArticle('onward','NO','"+j+"','"+requestId+"_"+AllocatedReqJSON[i].mtJourneyDetails[j].id+"'); checked='checked'>N</input>";
						}
						temp += "</td>";		
						
						temp += "<td>";
						temp +="<input type=text id=remarks"+j+" style=width:70px; value="+AllocatedReqJSON[i].mtJourneyDetails[j].remarks+"></input>";
						temp += "</td>";	
						temp += "<td align=center><input type=checkbox  id="+requestId+"_"+AllocatedReqJSON[i].mtJourneyDetails[j].id+"  onchange=javascript:checkEditCheckbox('"+requestId+"_"+AllocatedReqJSON[i].mtJourneyDetails[j].id+"','"+requestId+"_ONWARD'); /></td>";
						
						//for articals
						 temp3 +=" <tr id=passeng  style=display:none;></tr>";
						 if(AllocatedReqJSON[i].mtJourneyDetails[j].articleCarryFlag == '1'){
						
							
							 temp3 +=" <tr id=passeng"+j+">";
							  temp3 +="<td><div class=line id=passeng"+j+"Name style=color:red;>"+AllocatedReqJSON[i].mtJourneyDetails[j].nameWithDesignation+"</div>";
							
							  temp3 +="<table id=passeng"+j+"Tab width=100% border=2px cellpadding=0px cellspacing=0px align=center>";
							  temp3 +="<tr>";
							  temp3 +="<td class=tabletd align=center style=width:15%;>Type</td>";
							  temp3 +="<td class=tabletd align=center style=width:15%;>Length (CM)</td>";
							  temp3 +="<td class=tabletd align=center style=width:15%;>Breadth (CM)</td>";
							  temp3 +="<td class=tabletd align=center style=width:15%;>Height (CM)</td>";
							  temp3 +="<td class=tabletd align=center style=width:15%;>Weight (KG)</td>";
							  temp3 +="<td class=tabletd align=center style=width:15%;>Quantity</td>";
							  temp3 +="<td class=tabletd align=center style=width:5%;>Add</td>";
							  temp3 +="<td class=tabletd align=center style=width:5%;>Del</td>";
							  temp3 +="</tr>";
						for(var k=0;k<AllocatedReqJSON[i].mtJourneyDetails[j].mtArticleDetails.length;k++){
						
							  temp3 +="<tr id=passeng"+j+"Row"+k+">";
							  temp3 +="<td><select  id=passeng"+j+"RowArticleType"+k+" style=width:98%;>";
							  temp3 +="<option value=Non-Explosive>Non-Explosive</option>";
							  temp3 +="<option value=Explosive>Explosive</option>";
							  temp3 +="</select>";
							  temp3 +="</td>";
							  temp3 +="<td><input type=text  style=width:98%; id=passeng"+j+"RowLength"+k+" onkeypress='javascript:return checkFloat(event,&quot;passeng"+k+"RowLength"+k+"&quot;)'; value="+AllocatedReqJSON[i].mtJourneyDetails[j].mtArticleDetails[k].length+"></input></td>";
								 temp3 +="<td><input type=text  style=width:98%; id=passeng"+j+"RowBreadth"+k+" onkeypress='javascript:return checkFloat(event,&quot;passeng"+k+"RowBreadth"+k+"&quot;)'; value="+AllocatedReqJSON[i].mtJourneyDetails[j].mtArticleDetails[k].breadth+"></input></td>";
								 temp3 +="<td><input type=text  style=width:98%; id=passeng"+j+"RowHeight"+k+" onkeypress='javascript:return checkFloat(event,&quot;passeng"+k+"RowHeight"+k+"&quot;)'; value="+AllocatedReqJSON[i].mtJourneyDetails[j].mtArticleDetails[k].height+"></input></td>";
								 temp3 +="<td><input type=text  style=width:98%; id=passeng"+j+"RowWeight"+k+" onkeypress='javascript:return checkFloat(event,&quot;passeng"+k+"RowWeight"+k+"&quot;)'; value="+AllocatedReqJSON[i].mtJourneyDetails[j].mtArticleDetails[k].weight+"></input></td>";
								 temp3 +="<td><input type=text  style=width:98%; id=passeng"+j+"RowQuantity"+k+" onkeypress='javascript:return checkInt(event)'; value="+AllocatedReqJSON[i].mtJourneyDetails[j].mtArticleDetails[k].quantity+"></input></td>";
								 temp3 +="<td><input type=button style=width:98%; id=passeng"+j+"RowAdd"+k+" value=+ onclick=javascript:insertNewRow(&quot;passeng"+j+"&quot;,&quot;passeng"+j+"Tab&quot;,&quot;passeng"+j+"Row&quot;,"+k+"); /></td>";
								 if(k!=0){
								 temp3 +="<td><input type=button style=width:98%; id=passeng"+j+"RowDel"+k+" value=- onclick=javascript:deleteRow(&quot;passeng"+j+"&quot;,&quot;passeng"+j+"Tab&quot;,&quot;passeng"+j+"Row&quot;,"+k+"); /></td>";
								 }
								 temp3 +="</tr>";
							
					}
						  temp3 +="</table></td></tr>";		
						}	
					
						
						//for allocation details
						temp2 += "<tr>";
						temp2+= "<td align=center>"+AllocatedReqJSON[i].mtJourneyDetails[j].nameWithDesignation+"</td>";
						temp2+= "<td align=center>"+AllocatedReqJSON[i].mtJourneyDetails[j].mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.vehicleDetails.vehicleNo+"</td>";
						temp2 += "</tr>";
						
						
					temp += "</tr>";
					
					
					}
				}
				temp3 +="</table></fieldset></div>";
			}
		}
		
		temp2 += "</table>";
		temp2 += "</div>";
		
		temp += "</table>";		
		temp += "</div>";
		//for artical details
		temp += temp3;
		
		temp +="<div class=line><div class=expbutton style=margin-left:75%;><a onclick=javascript:updateOnwardRequestDetails();> <span>Change Request Details</span></a></div></div>	";
		temp += "</fieldset>";
		
		
		//for allocation details
		temp += temp2;
		temp += "</div>";
		$jq('#OnwardAllocation').append(temp);
		$jq('#OnwardAllocation').css('display','block');
	}
	
}
function returnAllocation(requestId,type){
	var temp = "";
	var temp2="";
	var temp3="";
	if(type=='show'){
		$jq('#returnShow'+requestId).css('display','none');
		$jq('#returnHide'+requestId).css('display','block');
	}
	if(type=='hide'){
		$jq('#returnShow'+requestId).css('display','block');
		$jq('#returnHide'+requestId).css('display','none');
		$jq('#ReturnAllocation').html('');
		$jq('#ReturnAllocation').css('display','none');
	} 

if(type=='show'){
	$jq('#ReturnAllocation').html('');
	$jq('#ReturnAllocation').css('display','none');
	
	temp += "<div class=line>";
	temp += "<fieldset><legend><strong><font color=green>Return Journey Details </font></strong></legend>";
	temp += "<div class=line>";
	temp += "<table class=fortable id=returnJourneyDetailsID width=100% border=2px cellpadding=0px cellspacing=0px align=center>";
	temp += "<tr>";
	temp +="<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>";
	temp +="</tr>";
	
		temp += "<tr>";
			
		   
		    temp += "<td rowspan=3 class=tabletd align=center><font size=-3px;>No of People</font></td>";
			temp += "<td rowspan=3 class=tabletd align=center>Name With Designation</td>";
			temp += "<td colspan=5 class=tabletd align=center >Departure/Arrival</td>";
			temp += "<td rowspan=2 colspan=2 class=tabletd align=center>Pickup Place</td>";
			temp += "<td rowspan=2 colspan=2 class=tabletd align=center>Dropping Place</td>";
			temp += "<td rowspan=3 class=tabletd align=center>Accom Required</td>";
			temp += "<td rowspan=3 class=tabletd align=center>Accom Place</td>";
			temp += "<td rowspan=3 class=tabletd align=center>Article Carry</td>";
			temp += "<td rowspan=3 class=tabletd align=center>Remarks (Mobile No/ Flight No)</td>";
			temp += "<td rowspan=3 class=tabletd align=center></td>";
			
		temp += "</tr>";
			temp += "<tr>";
			temp += "<td colspan=2 class=tabletd align=center>Travelling</td>";
			temp += "<td colspan=1 class=tabletd align=center>Duration</td>";
			temp += "<td colspan=2 class=tabletd align=center>Estimated</td>";
		temp += "</tr>";
		temp += "<tr>";
			temp += "<td class=tabletd align=center>Date</td>";
			temp += "<td class=tabletd align=center>Time</td>";
			temp +="<td class=tabletd align=center>HH:MM</td>";
			temp += "<td class=tabletd align=center>Date</td>";
			temp += "<td class=tabletd align=center>Time</td>";
			temp += "<td class=tabletd align=center>Pickup</td>";
			temp += "<td class=tabletd align=center>Other Source</td>";
			temp += "<td class=tabletd align=center>Drop</td>";
			temp += "<td class=tabletd align=center>Other Destin</td>";
		temp += "</tr>";
		temp += "<tr>";
		temp +="<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>";
		temp +="</tr>";	
		
		temp2+= "<div class=line>";
		temp2 += "<table id=returnAlloc class=fortable width=100% border=2px align=center>";
		temp2 += "<tr>";
		temp2 += "<td class=tabletd align=center>Vehicle Allocated For</td>";
		temp2 += "<td class=tabletd align=center>Allocated Vehicle No</td>";
		temp2 += "</tr>";
		
		 temp3 +="<div  id=returnArticleDetailsID class=line>";
		  temp3 +="<fieldset><legend><strong><font color=green>Return Article Details </font></strong></legend>";
		  temp3 +="<div class=line>";
		  temp3 +="<table  class=fortable width=100%>";
		
		for (var i=0; i<AllocatedReqJSON.length; i++){
			if(AllocatedReqJSON[i].requestID == requestId){
				//count = 1;
				for(var j=0; j<AllocatedReqJSON[i].mtJourneyDetails.length; j++){
					if(AllocatedReqJSON[i].mtJourneyDetails[j].journeyTypeFlag == "RETURN"){
						temp += "<tr id=row_"+j+">";
					
						temp += "<td>";
						temp +="<input type=text id=RpassengNo"+j+" style=width:25px; onkeypress='return checkInt(event);' value="+AllocatedReqJSON[i].mtJourneyDetails[j].noOfPeople+"></input>";
						temp +="</td>";
						
						temp += "<td>";
						temp +="<input type=text id=RpassengName"+j+" style=width:auto; value="+AllocatedReqJSON[i].mtJourneyDetails[j].nameWithDesignation+"></input>";
						temp += "</td>";
						
						temp += "<td>";
						temp += "<input type=text readonly=readonly style=height:16px;width:64px;font-size: 9px;font-weight: bold; id=RstartDate"+j+" onmouseover=Calendar.setup({inputField:'RstartDate"+j+"',ifFormat:'%d-%b-%Y',showsTime:false,button:R'startDate"+j+"',step:1}); onchange=checkReqDates(\'return\');mtAddHoursMin("+j+",'return');durationOfDates("+j+",'return'); value="+AllocatedReqJSON[i].mtJourneyDetails[j].departureDateString+"></input>";
						temp += "</td>";
					
						temp += "<td>";
						temp +="<input type=text id=RstartTime"+j+" class =RstartTime style=width:37px onkeyup=checkReqDates(\'return\');mtAddHoursMin("+j+",'return');durationOfDates("+j+",'return'); value="+AllocatedReqJSON[i].mtJourneyDetails[j].departureTime+"></input>";
						temp += "</td>";
						
						temp += "<td>";
						temp +="<input type=text id=RdurationH"+j+" style=width:18px onkeyup=mtAddHoursMin("+j+",'return') maxlength=3></input>";
						temp +="<input type=text id=RdurationM"+j+" style=width:18px onkeyup=mtAddHoursMin("+j+",'return') maxlength=2></input>";
						temp += "</td>";
						
						temp += "<td>";
						temp += "<input type=text readonly=readonly style=height:16px;width:64px;font-size: 9px;font-weight: bold; id=RestDate"+j+" onmouseover=Calendar.setup({inputField:R'estDate"+j+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'RestDate"+j+"',step:1}); onchange=checkReqDates(\'onward\');durationOfDates("+j+",'return'); value="+AllocatedReqJSON[i].mtJourneyDetails[j].estimatedDateString+"></input>";
						temp += "</td>";
					
						temp += "<td>";
						temp +="<input type=text id=RestTime"+j+" class =RestTime style=width:37px  onkeyup=checkReqDates(\'return\');durationOfDates("+j+",'return'); value="+AllocatedReqJSON[i].mtJourneyDetails[j].estimatedTime+"></input>";
						temp += "</td>";
						
						temp += "<td>";
						temp +="<select id=RpickupPoint"+j+"  style=width:80px;>";
						temp +="<option value="+AllocatedReqJSON[i].mtJourneyDetails[j].mtPickAddressDetails.addressId+">"+AllocatedReqJSON[i].mtJourneyDetails[j].mtPickAddressDetails.addressName+"</option>";
								for(var m=0; m<addressJSON.length; m++){
									temp +="<option value="+addressJSON[m].addressId+">"+addressJSON[m].addressName+"</option>";			
								}
								temp +="</select>";
								temp += "</td>";
					
						temp += "<td>";
						temp +="<input type=text  id=RotherSourceName"+j+" style=width:50px; value="+AllocatedReqJSON[i].mtJourneyDetails[j].pickupPlace+"></input>";
						temp += "</td>";
						
						temp += "<td>";
						temp +="<select id=RdroppingPoint"+j+"  style=width:80px;>";
						temp +="<option value="+AllocatedReqJSON[i].mtJourneyDetails[j].mtDropAddressDetails.addressId+">"+AllocatedReqJSON[i].mtJourneyDetails[j].mtDropAddressDetails.addressName+"</option>";
								for(var m=0; m<addressJSON.length; m++){
									temp +="<option value="+addressJSON[m].addressId+">"+addressJSON[m].addressName+"</option>";	
								}
								temp +="</select>";
								temp += "</td>";
						
						temp += "<td>";
						temp +="<input type=text  id=RotherDestiName"+j+" style=width:50px; value="+AllocatedReqJSON[i].mtJourneyDetails[j].dropPlace+"></input>";
						temp += "</td>";
						
						temp += "<td>";
						if(AllocatedReqJSON[i].mtJourneyDetails[j].accommReqFlag == '1'){
						temp += "<input type=checkbox  id=RaccomReq1"+j+" value=1 onclick=javascript:ReturncheckAccomdationRequired('YES','"+j+"'); checked='checked'>Y</input>";
						temp += "<input type=checkbox  id=RaccomReq2"+j+" value=0  onclick=javascript:ReturncheckAccomdationRequired('NO','"+j+"');>N</input>";
						}else {
							temp += "<input type=checkbox  id=RaccomReq1"+j+" value=1 onclick=javascript:ReturncheckAccomdationRequired('YES','"+j+"');>Y</input>";
						temp += "<input type=checkbox  id=RaccomReq2"+j+" value=0  onclick=javascript:ReturncheckAccomdationRequired('NO','"+j+"'); checked='checked'>N</input>";
						}
						temp += "</td>";
					
						temp += "<td>";
						temp += "<input type=text  id=RaccomPlace"+j+" style=width:50px; value="+AllocatedReqJSON[i].mtJourneyDetails[j].accommPlace+"></input>";
						temp += "</td>";
						
						temp += "<td>";
						if(AllocatedReqJSON[i].mtJourneyDetails[j].articleCarryFlag == '1'){
						temp += "<input type=checkbox  id=RarticleCarry1"+j+" value=1 onclick=javascript:ReturncheckArticleRequired('YES','"+j+"');editCheckArticle('return','YES','"+j+"','"+requestId+"_"+AllocatedReqJSON[i].mtJourneyDetails[j].id+"'); checked='checked'>Y</input>";
						temp += "<input type=checkbox  id=RarticleCarry2"+j+" value=0  onclick=javascript:ReturncheckArticleRequired('NO','"+j+"');editCheckArticle('return','NO','"+j+"','"+requestId+"_"+AllocatedReqJSON[i].mtJourneyDetails[j].id+"');>N</input>";
						}else {
						temp += "<input type=checkbox  id=RarticleCarry1"+j+" value=1 onclick=javascript:ReturncheckArticleRequired('YES','"+j+"');editCheckArticle('return','YES','"+j+"','"+requestId+"_"+AllocatedReqJSON[i].mtJourneyDetails[j].id+"');>Y</input>";
						temp += "<input type=checkbox  id=RarticleCarry2"+j+" value=0  onclick=javascript:ReturncheckArticleRequired('NO','"+j+"');editCheckArticle('return','NO','"+j+"','"+requestId+"_"+AllocatedReqJSON[i].mtJourneyDetails[j].id+"'); checked='checked'>N</input>";
						}
						temp += "</td>";		
						
						temp += "<td>";
						temp +="<input type=text id=Rremarks"+j+" style=width:70px; value="+AllocatedReqJSON[i].mtJourneyDetails[j].remarks+"></input>";
						temp += "</td>";	
						temp += "<td align=center><input type=checkbox  id="+requestId+"_"+AllocatedReqJSON[i].mtJourneyDetails[j].id+"  onchange=javascript:checkEditCheckbox('"+requestId+"_"+AllocatedReqJSON[i].mtJourneyDetails[j].id+"','"+requestId+"_RETURN'); /></td>";
						
						//for articals
						
						 if(AllocatedReqJSON[i].mtJourneyDetails[j].articleCarryFlag == '1'){
							 temp3 +=" <tr id=Rpasseng  style=display:none;></tr>";
							
							 temp3 +=" <tr id=Rpasseng"+j+">";
							  temp3 +="<td><div class=line id=Rpasseng"+j+"Name style=color:red;>"+AllocatedReqJSON[i].mtJourneyDetails[j].nameWithDesignation+"</div>";
							
							  temp3 +="<table id=Rpasseng"+j+"Tab width=100% border=2px cellpadding=0px cellspacing=0px align=center>";
							  temp3 +="<tr>";
							  temp3 +="<td class=tabletd align=center style=width:15%;>Type</td>";
							  temp3 +="<td class=tabletd align=center style=width:15%;>Length (CM)</td>";
							  temp3 +="<td class=tabletd align=center style=width:15%;>Breadth (CM)</td>";
							  temp3 +="<td class=tabletd align=center style=width:15%;>Height (CM)</td>";
							  temp3 +="<td class=tabletd align=center style=width:15%;>Weight (KG)</td>";
							  temp3 +="<td class=tabletd align=center style=width:15%;>Quantity</td>";
							  temp3 +="<td class=tabletd align=center style=width:5%;>Add</td>";
							  temp3 +="<td class=tabletd align=center style=width:5%;>Del</td>";
							  temp3 +="</tr>";
						for(var k=0;k<AllocatedReqJSON[i].mtJourneyDetails[j].mtArticleDetails.length;k++){
						
							  temp3 +="<tr id=Rpasseng"+j+"Row"+k+">";
							  temp3 +="<td><select  id=Rpasseng"+j+"RowArticleType"+k+" style=width:98%;>";
							  temp3 +="<option value=Non-Explosive>Non-Explosive</option>";
							  temp3 +="<option value=Explosive>Explosive</option>";
							  temp3 +="</select>";
							  temp3 +="</td>";
							  temp3 +="<td><input type=text  style=width:98%; id=Rpasseng"+j+"RowLength"+k+" onkeypress='javascript:return checkFloat(event,&quot;Rpasseng"+k+"RowLength"+k+"&quot;)'; value="+AllocatedReqJSON[i].mtJourneyDetails[j].mtArticleDetails[k].length+"></input></td>";
								 temp3 +="<td><input type=text  style=width:98%; id=Rpasseng"+j+"RowBreadth"+k+" onkeypress='javascript:return checkFloat(event,&quot;Rpasseng"+k+"RowBreadth"+k+"&quot;)'; value="+AllocatedReqJSON[i].mtJourneyDetails[j].mtArticleDetails[k].breadth+"></input></td>";
								 temp3 +="<td><input type=text  style=width:98%; id=Rpasseng"+j+"RowHeight"+k+" onkeypress='javascript:return checkFloat(event,&quot;Rpasseng"+k+"RowHeight"+k+"&quot;)'; value="+AllocatedReqJSON[i].mtJourneyDetails[j].mtArticleDetails[k].height+"></input></td>";
								 temp3 +="<td><input type=text  style=width:98%; id=Rpasseng"+j+"RowWeight"+k+" onkeypress='javascript:return checkFloat(event,&quot;Rpasseng"+k+"RowWeight"+k+"&quot;)'; value="+AllocatedReqJSON[i].mtJourneyDetails[j].mtArticleDetails[k].weight+"></input></td>";
								 temp3 +="<td><input type=text  style=width:98%; id=Rpasseng"+j+"RowQuantity"+k+" onkeypress='javascript:return checkInt(event)'; value="+AllocatedReqJSON[i].mtJourneyDetails[j].mtArticleDetails[k].quantity+"></input></td>";
								 temp3 +="<td><input type=button style=width:98%; id=Rpasseng"+j+"RowAdd"+k+" value=+ onclick=javascript:insertNewRow(&quot;Rpasseng"+j+"&quot;,&quot;Rpasseng"+j+"Tab&quot;,&quot;Rpasseng"+j+"Row&quot;,"+k+"); /></td>";
								 if(k!=0){
								 temp3 +="<td><input type=button style=width:98%; id=Rpasseng"+j+"RowDel"+k+" value=- onclick=javascript:deleteRow(&quot;Rpasseng"+j+"&quot;,&quot;Rpasseng"+j+"Tab&quot;,&quot;Rpasseng"+j+"Row&quot;,"+k+"); /></td>";
								 }
								 temp3 +="</tr>";
							
					}
						  temp3 +="</table></td></tr>";		
						}	
					
						
						//for allocation details
						temp2 += "<tr>";
						temp2+= "<td align=center>"+AllocatedReqJSON[i].mtJourneyDetails[j].nameWithDesignation+"</td>";
						temp2+= "<td align=center>"+AllocatedReqJSON[i].mtJourneyDetails[j].mtVehicleAllocationtDetailsDTO[0].vehicleDriverMapDetails.vehicleDetails.vehicleNo+"</td>";
						temp2 += "</tr>";
						
						
					temp += "</tr>";
					
					
					}
				}
				temp3 +="</table></fieldset></div>";
			}
		}
		
		temp2 += "</table>";
		temp2 += "</div>";
		
		temp += "</table>";		
		temp += "</div>";
		//for artical details
		temp += temp3;
		
		temp +="<div class=line><div class=expbutton style=margin-left:75%;><a onclick=javascript:updateReturnRequestDetails();> <span>Change Request Details</span></a></div></div>	";
		temp += "</fieldset>";
		
		
		//for allocation details
		temp += temp2;
		temp += "</div>";
		$jq('#ReturnAllocation').append(temp);
		$jq('#ReturnAllocation').css('display','block');
}
	
}
function userJourneyCancel(journeyId,requestId){
	var requestDetails={
			"param" : "userCancelJourney",
			"journeyId" : journeyId
	};
	$jq.post('transport.htm', requestDetails, function(html) {
		$jq('#result').html(html);
		// $jq('#vehicleRequestDetails').html(html);
		
	});
	getRequestIdDetails(requestId);
}
function deallocateVehicleForJourney(){
	var journeyIdJSON={};
	var i=0;
	$jq('#cancelReq').find('tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(5).find('input:checkbox').attr('checked')==true){
			journeyId = $jq(this).find("td").eq(5).find("input:checkbox").attr("id");
			journeyIdJSON[i]=journeyId;
			i++;
		}
	});
	var requestDetais={
			"param" : "deallocateVehicleForJourney",
			"deallocateJourneyJSON" : JSON.stringify(journeyIdJSON)
	};
	if(JSON.stringify(journeyIdJSON).length>2){
		$jq.post('transport.htm',requestDetais,function(html){
			$jq("#result2").html(html);
		});	
	}else{
		alert("Please Select Atleast One Request");
	}
}
function copyToReturnJourney(rowNo){
	if($jq('input:radio[name=vehicleReturn]:checked').val()==1){
		$jq('#RpassengNo'+rowNo).val($jq('#passengNo'+rowNo).val());
		$jq('#RpassengName'+rowNo).val($jq('#passengName'+rowNo).val());
		
		$jq('#RpickupPoint'+rowNo).val($jq('#droppingPoint'+rowNo).val());		
		$jq('#RdroppingPoint'+rowNo).val($jq('#pickupPoint'+rowNo).val());
		$jq('#RotherSourceName'+rowNo).val($jq('#otherDestiName'+rowNo).val());
		$jq('#RotherDestiName'+rowNo).val($jq('#otherSourceName'+rowNo).val());
		$jq('#Rremarks'+rowNo).val($jq('#remarks'+rowNo).val());
		
		if($jq('#accomReq1'+rowNo).is(':checked')){
			$jq('#RaccomReq1'+rowNo).attr('checked',true);
			$jq('#RaccomReq2'+rowNo).attr('checked',false);
		}
		if($jq('#accomReq2'+rowNo).is(':checked')){
			$jq('#RaccomReq2'+rowNo).attr('checked',true);
			$jq('#RaccomReq1'+rowNo).attr('checked',false);
		}
		if($jq('#articleCarry1'+rowNo).is(':checked')){
			$jq('#RarticleCarry1'+rowNo).attr('checked',true);
			$jq('#RarticleCarry2'+rowNo).attr('checked',false);
			ReturncheckArticleRequired('YES',rowNo)
		}
		if($jq('#articleCarry2'+rowNo).is(':checked')){
			$jq('#RarticleCarry2'+rowNo).attr('checked',true);
			$jq('#RarticleCarry1'+rowNo).attr('checked',false);
			ReturncheckArticleRequired('NO',rowNo)
		}
		
	}
}
	function getDayWiseAllocationReport()
	{
		var errMsg="";
        var status=true;
		if($jq('#searchFromDate').val()==''){
			errMsg += "Please Select FromDate \n";
			status=false;
		}
		if($jq('#searchToDate').val()==''){
			errMsg += "Please Select ToDate\n";
			status=false;
		}
		if($jq('#vehicleTypeid').val()=='select'){
			errMsg += "Please Select Vehicle Type\n";
			status=false;
		}
		
		if(status==true){
		//var searchDate=$jq('#searchFromDate').val()+" "+$jq('#searchFromTime').val();
		var fromDate=$jq('#searchFromDate').val();
		var toDate=$jq('#searchToDate').val();
		var vehicleTypeId=$jq('#vehicleTypeid').val();
		window.open('./report.htm?param=MTReports&fromDate='+fromDate+'&toDate='+toDate+'&vehicleTypeId='+vehicleTypeId+'&type=dayWiseAllocationReport','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		
		}else{
			alert(errMsg);
		}
	}
	function getCompletionReport(){
		var errMsg="";
        var status=true;
		if($jq('#searchFromDate').val()=='' || $jq('#searchFromTime').val()==''){
			errMsg += "Please Select FromDate & Time \n";
			status=false;
		}
		if($jq('#searchToDate').val()=='' || $jq('#searchToTime').val()==''){
			errMsg += "Please Select ToDate & Time \n";
			status=false;
		}
		if($jq('#vehicleTypeid').val()=='select'){
			errMsg += "Please Select Vehicle Type\n";
			status=false;
		}
		if(status==true){
		var fromDate=$jq('#searchFromDate').val()+" "+$jq('#searchFromTime').val();
		var toDate=$jq('#searchToDate').val()+" "+$jq('#searchToTime').val();
		var vehicleTypeId=$jq('#vehicleTypeid').val();
		window.open('./report.htm?param=MTReports&fromDate='+fromDate+'&toDate='+toDate+'&vehicleTypeId='+vehicleTypeId+'&type=completionReport','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else{
			alert(errMsg);
		}
		}
	function getDailyMileageReport(){
		var errMsg="";
        var status=true;
        if($jq('#vehicleNo').val()=='0'){
			errMsg += "Please Select VehicleNo \n";
			status=false;
		}
		if($jq('#searchFromDate').val()==''){
			errMsg += "Please Select FromDate & Time \n";
			status=false;
		}
		if($jq('#searchToDate').val()==''){
			errMsg += "Please Select ToDate & Time \n";
			status=false;
		}
		
		if(status==true){
		var vehicleId=	$jq('#vehicleNo').val();
		var fromDate=$jq('#searchFromDate').val();
		var toDate=$jq('#searchToDate').val();
		window.open('./report.htm?param=MTReports&fromDate='+fromDate+'&toDate='+toDate+'&vehicleId='+vehicleId+'&type=dailyMileageEntryPdf','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else{
			alert(errMsg);
		}
	}
	function getEmpDetails(e){
		/*var key;
		var value = $jq('#requestedFor').val();
		
		
		if(window.event){
			key = window.event.keyCode;
		}
		else{
			key = e.which;
		}
		if(value=="" && key==13){
			alert("Please Enter SFID ");
			// return false;
		}
		
		if(value!="" && key==13){
			var status = true;*/
		var status = true;
		if($jq('#requestedFor').val()==0){
			$jq('#empDetails').html('');
			status = false;
		}
		if (status) {
			/*
			 * var requestDetails={ "param" : "getEmpDetails", "requestedFor" :
			 * value };
			 */
			// $jq('input:text').val();
			 document.forms[0].param.value = "getEmpDetails";
			// document.forms[0].requestedFor.value = value;
			 document.forms[0].action = "transport.htm";
			 
				document.forms[0].submit();
			
		}
		
	}
	function freeSelectedDedicatedVehicle(){
		var id="";
		var allocId="";
		var mapId="";
		var flag=true;
		var rowNo=0;
		var errMsg="";
		var freeDedicatedVehicleJSON={};
		$jq('#dedicatedVehicle').find('tr:not(:first)').each(
				function(){
					if($jq(this).find('td').eq(5).find('input:checkbox').attr('checked')==true){ 
			         id=$jq(this).find('td').eq(5).find('input:checkbox').attr('id');
			         allocId = id.split('_')[0];
			         mapId = id.split('_')[1];
			         if($jq('#fromDate'+allocId).val() == ''){
							errMsg += "Please Select From Date\n";
							$jq('#fromDate'+allocId).focus();
							flag = false;
						}
						if($jq('#fromTime'+allocId).val() == ''){
							errMsg += "Please Select From Time\n";
							$jq('#fromTime'+allocId).focus();
							flag = false;
						}  
						if($jq('#toDate'+allocId).val() == ''){
							errMsg += "Please Select To Date\n";
							$jq('#toDate'+allocId).focus();
							flag = false;
						}
						if($jq('#toTime'+allocId).val() == ''){
							errMsg += "Please Select To Time\n";
							$jq('#toTime'+allocId).focus();
							flag = false;
						}  
						if(flag){
							var subJson={};
							subJson['fromDateTime']=$jq('#fromDate'+allocId).val()+" "+$jq('#fromTime'+allocId).val();
							subJson['toDateTime']=$jq('#toDate'+allocId).val()+" "+$jq('#toTime'+allocId).val();
							subJson['mapId']=mapId;
							subJson['allocId']=allocId;
							freeDedicatedVehicleJSON[rowNo]=subJson;
							rowNo++;
						}
						
			
		         }
					
				});
		if(JSON.stringify(freeDedicatedVehicleJSON).length==2 && flag == true){
			errMsg += "Please Select Atleast One Vehicle To Free \n";
			flag = false;
		}
		  var requestDetails={
				"param" : "freeDedicatedVehicles",
				"freeDedicatedVehicleJSON" : JSON.stringify(freeDedicatedVehicleJSON)
		};
		  if(flag){
		  $jq.post('transport.htm',requestDetails,function(html){
				$jq("#result1").html(html);
			});	
		  }else{
			  alert(errMsg);
		  }
	}
	/*function checkReqDates(id){
		var errMsg="";
		var flag=true;
		
		
			if($jq('#startDate'+id).val()==''){
				errMsg += "Please Select Travelling Date Before Selecting Estimated Date";
				$jq('#estDate'+id).val('');
			}
			else{
				var fdate = convertDate($jq('#startDate'+id).val());
				var tdate = convertDate($jq('#estDate'+id).val());
				if(compareDates(fdate,'dd-MM-yyyy',tdate,'dd-MM-yyyy')==1){
					errMsg += "To Date Must be Greater Than From Date";
					$jq('#estDate'+id).val('');
				}
			}
			
	
		
		if(errMsg !=""){
			alert(errMsg);
		}
		
	}*/
	function getSpecificVehicleList(){
		var requestDetails = {
		"param" : "newVehicle",
		"vehicleType" : $jq('input:radio[name=vehicleType]:checked').val(),
		"vehiclePoolType" : $jq('input:radio[name=vehiclePoolType]:checked').val()
		};
		$jq.post('transport.htm', requestDetails, function(html) {
			$jq("#result").html(html);
		});
	}
	/*function getVDListForCombineAlloc(type){
		var requestDetails = {
				"param" : "getVDListForCombineAlloc"
				
		};
		$jq.post('transport.htm', requestDetails, function(html) {
			$jq("#OnwardCombineVehicleDetailsGrid").html(html);
			$jq("#OnwardCombineVehicleDetailsGrid").css("display","block");
		});
	}*/
	function saveCombineAlloc(type){
		var journeyDetailsGridId;
		var busyVehicleGridId;
		var journeyId;
		//var allocId;
		var mapId;
		var requestId;
		var journeyFlag=false;
		var vehicleFlag=false;
		var errMessage="";
		var errMessage1="";
		if(type=='onward'){
			journeyDetailsGridId="OnwardPassEngVehicleAllotGrid";
			busyVehicleGridId="OnwardCombineVehicleDetailsGrid";
		}
		if(type=='return'){
			journeyDetailsGridId="ReturnPassEngVehicleAllotGrid";
			busyVehicleGridId="ReturnCombineVehicleDetailsGrid";
		}
		// for alerts-----
		$jq('#'+journeyDetailsGridId).find('table tr:gt(2)').each(function(){
			
				if($jq(this).find('td').eq(0).find('input:checkbox').is(':checked') && $jq(this).find('td').eq(0).find('input:checkbox').attr('disabled')==false){
					if(journeyFlag==true && errMessage==''){
						errMessage += "Please select only one row on journy details grid\n";
					}else{
						journeyFlag=true;
					}
					
				}
			
		});
		$jq('#'+busyVehicleGridId).find('table tr:gt(0)').each(function(){
			
			if($jq(this).find('td').eq(0).find('input:checkbox').is(':checked')){
				if(vehicleFlag==true && errMessage1==''){
					errMessage1 += "Please select only one vehicle from busy vehicle-driver details grid\n";
				}else{
					vehicleFlag=true;
				}
				
			}
		
	});
		if(journeyFlag==false){
			errMessage += "Please select atleast one row on journy details grid\n";
		}
		else if(vehicleFlag==false){
			errMessage += "Please select atleast one vehicle from busy vehicle-driver details grid\n";
		}
		errMessage += errMessage1;
		//-----
		if(errMessage==""){
		//for journeyid
		$jq('#'+journeyDetailsGridId).find('table tr:gt(2)').each(function(){
		if($jq(this).find('td').eq(0).find('input:checkbox').is(':checked') && $jq(this).find('td').eq(0).find('input:checkbox').attr('disabled')==false){
			journeyId=$jq(this).find('td').eq(0).find('input:checkbox').attr('id').split('_')[1];
			requestId=$jq(this).find('td').eq(0).find('input:checkbox').attr('id').split('_')[0];
		}
		});
		//for map id  
		$jq('#'+busyVehicleGridId).find('table tr:gt(0)').each(function(){
			if($jq(this).find('td').eq(0).find('input:checkbox').is(':checked')){
				mapId=$jq(this).find('td').eq(0).find('input:checkbox').attr('id');
			}
			});
		if(type=='onward'){
			fromDate=$jq("#searchFromDate").val();
			fromTime=$jq("#searchFromTime").val();
			toDate=$jq("#searchToDate").val();
			toTime=$jq("#searchToTime").val();
			}
			if(type=='return'){
				fromDate=$jq("#RsearchFromDate").val();
				fromTime=$jq("#RsearchFromTime").val();
				toDate=$jq("#RsearchToDate").val();
				toTime=$jq("#RsearchToTime").val();
				}
		var requestDetails = {
				/*
				"param" : "saveCombineAlloc",
				"journeyId" : journeyId,
				"mapId" : mapId,
				"searchFromDate":fromDate,
				"searchFromTime":fromTime,
			    "searchToDate":toDate,
			    "searchToTime":toTime*/
				"param" : "saveCombineAlloc",
				"journeyId" : journeyId,
				"mapId" : mapId,
				"fromDate":fromDate,
				"fromTime":fromTime,
			    "toDate":toDate,
			    "toTime":toTime
		};
		$jq.post('transport.htm', requestDetails, function(html) {
			//$jq('#result').html(html);
			
			$jq('#fragment-1').html(html);
			showOnwardPassEngVehicleAllotGrid(requestId);
		    showReturnPassEngVehicleAllotGrid(requestId);
		});
	}else{
		alert(errMessage);
	}
		
	}
	function validateChangeDriver(row){
		var sysdate= new Date();
		var sys=formatDate(sysdate,'dd-NNN-yyyy');
		var fromDate=$jq('#fromDate'+row).val();
		var errMsg="";
		var flag=true;
		
		if(compareDates(convertDate(sys),'dd-MM-yyyy',convertDate(fromDate),'dd-MM-yyyy')==1){
			errMsg += "From Date Must be Greater or Equal to Today Date \n";
			flag=false;
			$jq('#fromDate'+row).val(sys);
			
		}
		if(errMsg !=''){
			alert(errMsg);
		}
		if(flag){
		$jq('#changeDriverDiv'+row).css("display","none");
		$jq('#getDriversDiv'+row).css("display","block");
		}
	}
	function changeButtons(id){
		if($jq('#driverId'+id).val()==''){
			$jq('#changeDriverDiv'+id).css('display','none');
			$jq('#getDriversDiv'+id).css('display','block');
			$jq('#driverId'+id).html('<option value="">Select</option><option value="0" style="color: red;">Remove Driver</option>');
		}
		if($jq('#driverId'+id).val()==0 && $jq('#driverId'+id).val()!=''){
			//$jq('#changeDriverDiv'+id).html('');
			if($jq('#getDriversDiv'+id).is(':visible')){
				$jq('#getDriversDiv'+id).css('display','none');
				$jq('#changeDriverDiv'+id).find('a').find('span').text('RemoveDriver');
				$jq('#changeDriverDiv'+id).css('display','block');
			}
		$jq('#changeDriverDiv'+id).find('a').find('span').text('RemoveDriver');
		}else{
			$jq('#changeDriverDiv'+id).find('a').find('span').text('ChangeDriver');
		}
	}
	function allotOrCombineButtons(journeyType,allocType){
	
			 $jq('#onwardSubmitDiv').html('');
				$jq('#returnSubmitDiv').html('');
				//for onward
				if(journeyType=='ONWARD'){
					if(allocType=='allocate'){
						if($jq('#OnwardVehicleAllotmentDetailsGrid').find('input:checkbox').is(':checked')){
							//if we check free vehicles,then deselect the busy vehicles checkboxes
							$jq('#OnwardCombineVehicleDetailsGrid').find('tr').each(
									function(){
										$jq(this).find('input:checkbox').attr('checked',false)
									});
							 $jq('#onwardSubmitDiv').append('<div class="expbutton"><a onclick="saveVehicleAllocationDetails(\'onward\')"> <span>Allocate For Onward</span></a></div>');
						}else{
							$jq('#onwardSubmitDiv').html('');
						}
						
					}else if(allocType=='combineAlloc'){
						if($jq('#OnwardCombineVehicleDetailsGrid').find('input:checkbox').is(':checked')){
							//if we check busy vehicles,then deselect the free vehicles checkboxes
							$jq('#OnwardVehicleAllotmentDetailsGrid').find('tr').each(
									function(){
										$jq(this).find('input:checkbox').attr('checked',false)
									});
							
						      $jq('#onwardSubmitDiv').append('<div class="expbutton" ><a onclick="saveCombineAlloc(\'onward\')"><span>Combine Allocation</span></a></div>');
						}else{
							 $jq('#onwardSubmitDiv').html('');
						 }
						}
					 $jq('#onwardSubmitDiv').css("display","block");
				}
				//for return
				else if(journeyType=='RETURN'){
					if(allocType=='allocate'){
						if($jq('#ReturnVehicleAllotmentDetailsGrid').find('input:checkbox').is(':checked')){
							//if we check free vehicles,then deselect the busy vehicles checkboxes
							$jq('#ReturnCombineVehicleDetailsGrid').find('tr').each(
									function(){
										$jq(this).find('input:checkbox').attr('checked',false)
									});
						  $jq('#returnSubmitDiv').append('<div class="expbutton"><a onclick="saveVehicleAllocationDetails(\'return\')"> <span>Allocate For Return</span></a></div> ');
						}else{
							$jq('#returnSubmitDiv').html('');
						}
					}else if(allocType=='combineAlloc'){
						if($jq('#ReturnCombineVehicleDetailsGrid').find('input:checkbox').is(':checked')){
							//if we check busy vehicles,then deselect the free vehicles checkboxes
							$jq('#ReturnVehicleAllotmentDetailsGrid').find('tr').each(
									function(){
										$jq(this).find('input:checkbox').attr('checked',false)
									});
						  $jq('#returnSubmitDiv').append('<div class="expbutton" ><a onclick="saveCombineAlloc(\'return\')"> <span>Combine Allocation</span></a></div>');
						}else{
							$jq('#returnSubmitDiv').html('');
						}
						}
					$jq('#returnSubmitDiv').css("display","block");
				}
				  
			
		
		
	}
	function checkReqDates(journeyType){
		var toDate="";
		var errorMessage="";
		var journeyGridId="";
		if(journeyType=='onward'){
			journeyGridId="onwardJourneyDetailsID";
		}
		if(journeyType=='return'){
			journeyGridId="returnJourneyDetailsID";
		}
		$jq('#'+journeyGridId).find('tr:gt(4)').each(
		function(){		
			if(parseFloat(compareDates(convertDate($jq(this).find('td').eq(2).find('input').val()),convertDate($jq(this).find('td').eq(5).find('input').val())))<0){
				if($jq(this).find('td').eq(5).find('input').val() !=""){
					errorMessage += "Estimated Date should be equal or after Travelling Date \n";
					$jq(this).find('td').eq(4).find('input').val('');
					$jq(this).find('td').eq(5).find('input').val('');
					$jq(this).find('td').eq(6).find('input').val('');
					//status=false;
				}
			
			}
		});
		if(errorMessage!=""){
		alert(errorMessage);
		}
	}
	function setArticals(type,rowNo){
		if(type=='onward'){
			$jq('#passeng'+rowNo+'Name').text($jq('#passengName'+rowNo).val());
			$jq('#Rpasseng'+rowNo+'Name').text($jq('#RpassengName'+rowNo).val());
		}
		if(type=='return'){
			$jq('#Rpasseng'+rowNo+'Name').text($jq('#RpassengName'+rowNo).val());
		}
	}
	function mtRequestFormPrint(requestId){
		window.open('./report.htm?param=MTReports&type=mtRequestForm&requestID='+requestId,'fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
	//window.open('./report.htm?param=leaveReport&type='+type+'&requestID='+id+'&requestType='+requestType,'Leave','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
	}
	function mtYearlyMileageReport(){
		var flag=true;
		var errorMessage="";
		if($jq('#vehicleNo').val()=='select'){
			flag=false;
			errorMessage += "Select VehicleNo\n";
		}
		if($jq('#year').val()=='select'){
			flag=false;
			errorMessage += "Select Year\n";
		}
		if(flag){
			var vehicleId=$jq('#vehicleNo').val();
			var year=$jq('#year').val();
		window.open('./report.htm?param=MTReports&type=yearlyMielageCard&vehicleId='+vehicleId+'&year='+year,'fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
	   }else{
		   alert(errorMessage);
	   }
	}
	function freeDedicatedDateCheck(rid){
		if($jq('#'+rid).is(':checked')){
	
		var id =rid.split("_")[0]
		var errMsg="";
		var sysdate=new Date();
		var fromDate = $jq('#fromDate'+id).val();
		var toDate =$jq('#toDate'+id).val();
		var fromTime = $jq('#fromTime'+id).val();
		var toTime =$jq('#toTime'+id).val();
		var flag = true;
		
		if(fromDate !="" && toDate !="" && fromTime!="" && toTime!=""){
			for(var i=0;i<DedicatedVehiclesListJson.length;i++){
				if(DedicatedVehiclesListJson[i].id==id){
					//current date is greater
					if(DedicatedVehiclesListJson[i].flag=="yes"){	
						var sys=formatDate(sysdate,'dd-NNN-yyyy');
						if(compareDates(convertDate(sys),'dd-MM-yyyy',convertDate(fromDate),'dd-MM-yyyy')==1){
							$jq('#'+rid).attr("checked",false);
							errMsg += "From Date Must be Greater or Equal to Today Date \n";
							flag = false;
						}
						if(compareDates(convertDate(sys),'dd-MM-yyyy',convertDate(toDate),'dd-MM-yyyy')==1){
							$jq('#'+rid).attr("checked",false);
							errMsg += "To Date Must be Greater or Equal to Today Date \n";
							flag = false;
						}
					}
					if(DedicatedVehiclesListJson[i].flag!="yes"){
						var date1=DedicatedVehiclesListJson[i].stringFromDate.split(" ")[0];
						if(compareDates(convertDate(date1),'dd-MM-yyyy',convertDate(fromDate),'dd-MM-yyyy')==1){
							$jq('#'+rid).attr("checked",false);
							errMsg += " From Date Must Be Greater Than Or Equal To "+DedicatedVehiclesListJson[i].stringFromDate;
							flag = false;
						}
						if(compareDates(convertDate(date1),'dd-MM-yyyy',convertDate(toDate),'dd-MM-yyyy')==1){
							$jq('#'+rid).attr("checked",false);
							errMsg += " To Date Must Be Greater Than Or Equal To "+DedicatedVehiclesListJson[i].stringFromDate;
							flag = false;
						}
					}
				}
			}
			if(flag){
				 //for dates check  
			    var fdate=convertDate(fromDate);
			    var tdate=convertDate(toDate);
			    if(compareDates(fdate,'dd-MM-yyyy',tdate,'dd-MM-yyyy')==1){
			    	$jq('#'+rid).attr("checked",false);
			    	errMsg += "To Date Must be Greater Or Equal To From Date \n";
						flag = false;
				}else if(compareDates(tdate,'dd-MM-yyyy',fdate,'dd-MM-yyyy')!=1){
				
					var ftime=fromTime;
					var ttime=toTime;
					if(compareHoursTime(ftime.split(":")[0],ttime.split(":")[0])==1){
						$jq('#'+rid).attr("checked",false);
						errMsg += "To Time Must be Greater Than From Time \n";
						flag = false;
					}else if(compareHoursTime(ftime.split(":")[0],ttime.split(":")[0])==0){
						if(compareMinutesTime(ftime.split(":")[1].trim(),ttime.split(":")[1].trim())==1 || compareMinutesTime(ftime.split(":")[1].trim(),ttime.split(":")[1].trim())==0){
							$jq('#'+rid).attr("checked",false);
							errMsg += "To Time Must be Greater Than From Time \n";
							flag = false;
						}
					}
				}	
			}
			
		}else{
			$jq('#'+rid).attr("checked",false);
			errMsg += "Please Select From Date,Time and To Date,Time \n";
		}
		
		if(errMsg!=""){
			
			alert(errMsg);
		}
		
		}
	}
	function onchangeFreeDedicatedDate(id){
		$jq('#'+id).attr("checked",false);
		//$jq('input:checkbox[id='+id+']').attr('checked',false);
	}
	function releaseDatesCheck(rid){
		var fdate="";
		 var tdate="";
		 var ftime="";
			var ttime="";
			var flag=true;
			var errMsg="";
		var id= rid.split('_')[0];
		 //for dates check  
		if($jq('#'+rid).is(':checked')){
			if($jq('#todayDate'+id).val()!="" && $jq('#fromTime'+id).val()!=""){
			$jq('#vehicle').find('tr:not(:first)').each(function(){
				if($jq(this).find('td').eq('7').find('input').attr('id')==rid){
					fdate=convertDate($jq(this).find('td').eq('4').text().split(' ')[0]);
					ftime=$jq(this).find('td').eq('4').text().split(' ')[1];
				}
			});
			
		
		tdate=convertDate($jq('#todayDate'+id).val());
		ttime=$jq('#fromTime'+id).val();
	   
	    if(compareDates(fdate,'dd-MM-yyyy',tdate,'dd-MM-yyyy')==1){
	    	$jq('#'+rid).attr("checked",false);
	    	errMsg += "Released Date Must be Greater Or Equal To From Date \n";
				flag = false;
		}else if(compareDates(tdate,'dd-MM-yyyy',fdate,'dd-MM-yyyy')!=1){
			if(compareHoursTime(ftime.split(":")[0],ttime.split(":")[0])==1){
				$jq('#'+rid).attr("checked",false);
				errMsg += "Released Time Must be Greater Than From Time \n";
				flag = false;
			}else if(compareHoursTime(ftime.split(":")[0],ttime.split(":")[0])==0){
				if(compareMinutesTime(ftime.split(":")[1],ttime.split(":")[1])==1 || compareMinutesTime(ftime.split(":")[1],ttime.split(":")[1])==0){
					$jq('#'+rid).attr("checked",false);
					errMsg += "Released Time Must be Greater Than From Time \n";
					flag = false;
				}
			}
		}
		}else{
			$jq('#'+rid).attr("checked",false);
	    	errMsg += "Select Released Date And Time \n";
		}
			if(errMsg !=""){
		    	alert(errMsg);
		    }
		}
	}
	function onchangeReleaseDate(id){
		$jq('#'+id).attr("checked",false);
		//$jq('input:checkbox[id='+id+']').attr('checked',false);
	}
	//for repeated dates req
	function checkRepeation(id){
		/*if(confirm("Do You Need to Repeat The Vehilce With Same Dates For More Than One Day?")){
			var noDays= window.prompt("How Many Days Do You Need ?")
			if(parseInt(noDays)>1){
				if($jq('#startDate'+id).val()==$jq('#estDate'+id).val()){
					for(var i=0; i<noDays-1;i++){
						funcreatenewJourneyRow(id);
						id=id+1;
						$jq('#passengNo'+id).val($jq('#passengNo0').val());
						$jq('#passengName'+id).val($jq('#passengName0').val());
						$jq('#startDate'+id).val($jq('#startDate0').val());
						$jq('#startTime'+id).val($jq('#startTime0').val());
						$jq('#estDate'+id).val($jq('#estDate0').val());
						$jq('#estTime'+id).val($jq('#estTime0').val());
					}
				}else{
					alert("you are Choosed Different dates for Travelling and Estimated..Please Select Same Date For Both \n")
				}
				
			}
			
		}else{
			funcreatenewJourneyRow(id);
		}*/
		var errMsg="";
		if($jq('#repeateNoOfDays').val()!=""){
		
		if(parseInt($jq('#repeateNoOfDays').val())<=9){
		var rowNo=1;
		$jq('#onwardJourneyDetailsID').find('tr:gt(4)').each(function(){
			$jq('#row_'+rowNo).remove();
			rowNo++;
		});
		var rRowNo=1;
		$jq('#RjourneyDetailsID').find('tr:gt(4)').each(function(){
			$jq('#Rrow_'+rRowNo).remove();
			rRowNo++;
		});
		
		$jq('#onwardArticleDetailsID').html('');
		$jq('#onwardArticleDetailsID').append("<tr id='passeng'></tr>");
		checkArticleRequired('YES','0');
		
		
			var noDays=$jq('#repeateNoOfDays').val();
			if(parseInt(noDays)>=1){
				if($jq('#startDate'+id).val()==$jq('#estDate'+id).val()){
					for(var i=0; i<=noDays-1;i++){
						funcreatenewJourneyRow(id);
						var startDate=convertDate($jq('#startDate'+id).val());
						var estimatedDate=convertDate($jq('#estDate'+id).val());
						id=id+1;
						$jq('#passengNo'+id).val($jq('#passengNo0').val());
						$jq('#passengName'+id).val($jq('#passengName0').val());
						
						//get Previous date and increment one day(for departure date)
						var sDate=new Date(startDate.split("-")[2],startDate.split("-")[1]-1,startDate.split("-")[0]);
						var sNextDay=sDate.getDate()+1;
						var d1=new Date(startDate.split("-")[2],startDate.split("-")[1]-1,sNextDay);
						$jq('#startDate'+id).val(tadaFormatDate(d1,'dd-NNN-yyyy'));
						
						//get Previous date and increment one day(for estimated date)
						var eDate=new Date(estimatedDate.split("-")[2],estimatedDate.split("-")[1]-1,estimatedDate.split("-")[0]);
						var eNextDay=eDate.getDate()+1;
						var d2=new Date(estimatedDate.split("-")[2],estimatedDate.split("-")[1]-1,eNextDay);
						$jq('#estDate'+id).val(tadaFormatDate(d2,'dd-NNN-yyyy'));
						
						$jq('#startTime'+id).val($jq('#startTime0').val());
						$jq('#estTime'+id).val($jq('#estTime0').val());
						$jq('#pickupPoint'+id).val($jq('#pickupPoint0').val());
						$jq('#otherSourceName'+id).val($jq('#otherSourceName0').val());
						$jq('#droppingPoint'+id).val($jq('#droppingPoint0').val());
						$jq('#otherDestiName'+id).val($jq('#otherDestiName0').val());
						$jq('#accomPlace'+id).val($jq('#accomPlace0').val());
						$jq('#remarks'+id).val($jq('#remarks0').val());
						$jq('#durationH'+id).val($jq('#durationH0').val());
						$jq('#durationM'+id).val($jq('#durationM0').val());
						copyToReturnJourney(id);
						
						if($jq('#accomReq10').is(':checked')){
							$jq('#accomReq1'+id).attr("checked",true);
							$jq('#accomReq2'+id).attr("checked",false);
						}
						if($jq('#accomReq20').is(':checked')){
							$jq('#accomReq2'+id).attr("checked",true);
							$jq('#accomReq1'+id).attr("checked",false);
						}
						if($jq('#articleCarry10').is(':checked')){
							$jq('#articleCarry1'+id).attr("checked",true);
							$jq('#articleCarry2'+id).attr("checked",false);
							
							checkArticleRequired('YES',id);
						}
						if($jq('#articleCarry20').is(':checked')){
							$jq('#articleCarry2'+id).attr("checked",true);
							$jq('#articleCarry1'+id).attr("checked",false);
							checkArticleRequired('NO',id);
						}
						
					}
				}else{
					errMsg += "Choosed Different dates for Travelling and Estimated..Please Select Same Date For Both \n";
					//alert("you are Choosed Different dates for Travelling and Estimated..Please Select Same Date For Both \n")
				}
				
			
		}
	}else{
		$jq('#repeateNoOfDays').val('');
		errMsg += "Max Repeat No Of Days Are 9 Only \n";
		//alert("Max Repeat No Of Days Are 9 Only");
	}
		}else{
			errMsg += "Enter No Of Days To Repeat \n";
		}
		if(errMsg !=""){
			alert(errMsg);
		}
	}
	function durationOfDates(id,type){
		var fromDateId="";
		var fromTimeId="";
		var toDateId="";
		var toTimeId="";
		var durationHoursId="";
		var duratiomMinId="";
		
		if(type=='onward'){
			 fromDateId="startDate";
			 fromTimeId="startTime";
			 toDateId="estDate";
			 toTimeId="estTime";
			 durationHoursId="durationH";
			 duratiomMinId="durationM";	 
		}
        if(type=='return'){
        	 fromDateId="RstartDate";
			 fromTimeId="RstartTime";
			 toDateId="RestDate";
			 toTimeId="RestTime";
			 durationHoursId="RdurationH";
			 duratiomMinId="RdurationM";
		}
		
        if($jq('#'+fromDateId+""+id).val()!='' && $jq('#'+toDateId+""+id).val()!=''){
		var fromDate=convertDate($jq('#'+fromDateId+""+id).val());
		var fromTime=$jq('#'+fromTimeId+""+id).val();
		var toDate=convertDate($jq('#'+toDateId+""+id).val());
		var toTime=$jq('#'+toTimeId+""+id).val();
		if(fromTime==""){
			fromTime="00:00";
		}
		
		var minDiff=0;
		var date1 = new Date(fromDate.split("-")[2],fromDate.split("-")[1]-1,fromDate.split("-")[0],fromTime.split(":")[0],fromTime.split(":")[1]);
		var date2 = new Date(toDate.split("-")[2],toDate.split("-")[1]-1,toDate.split("-")[0],toTime.split(":")[0],toTime.split(":")[1]);
		var minDiff=(date2.getTime()-date1.getTime())/(1000*60);
		if(minDiff<60){
			$jq('#'+durationHoursId+""+id).val(00);
			$jq('#'+duratiomMinId+""+id).val(minDiff);
		}
		else if(minDiff==60){
			$jq('#'+durationHoursId+""+id).val(01);
			$jq('#'+duratiomMinId+""+id).val(00);
		}
		else {
			 hoursDiff=minDiff/60;
			 hoursDiff = ""+hoursDiff;
			 if(hoursDiff.indexOf('.')==-1){
			    $jq('#'+durationHoursId+""+id).val(hoursDiff);
			    $jq('#'+duratiomMinId+""+id).val(00);
			 }else{
			 $jq('#'+durationHoursId+""+id).val(hoursDiff.split('.')[0]);
			 $jq('#'+duratiomMinId+""+id).val(hoursDiff.split('.')[1]);
			 }
			 /*$jq('#'+durationHoursId+""+id).val(hoursDiff.split('.')[0]);
			 $jq('#'+duratiomMinId+""+id).val(hoursDiff.split('.')[1]);*/
		}
		
        }
	}
	function mtAddHoursMin(id,type){
		var errMsg="";
		
		var fromDate="";
		var fromTime="";	
		var fromHours="";
		var fromMinutes="";
		var noOfHours="";
		var noOfMinutes=0;
		var dateId="";
		
		if(type=='onward'){
			dateId="startDate";
		}
		if(type=='return'){
			dateId="RstartDate";
		}
		
		if($jq('#'+dateId+""+id).val()!=''){
		if(type=='onward'){
			 fromDate=convertDate($jq('#startDate'+id).val());
			 fromTime=$jq('#startTime'+id).val();
			 fromHours=$jq.trim($jq('#startTime'+id).val().split(":")[0]);
			 fromMinutes=$jq.trim($jq('#startTime'+id).val().split(":")[1]);
			 
			if($jq('#durationH'+id).val()==''){
				noOfHours=Math.round(fromHours)+0
			}else{
				noOfHours=Math.round(fromHours)+Math.round($jq('#durationH'+id).val());
			}
			 noOfMinutes=Math.round(fromMinutes)+Math.round($jq('#durationM'+id).val());	
			
		}
		else if(type=='return'){
			fromDate=convertDate($jq('#RstartDate'+id).val());
			 fromTime=$jq('#RstartTime'+id).val();
			 fromHours=$jq.trim($jq('#RstartTime'+id).val().split(":")[0]);
			 fromMinutes=$jq.trim($jq('#RstartTime'+id).val().split(":")[1]);
			 
			   if($jq('#RdurationH'+id).val()==''){
				 noOfHours=Math.round(fromHours)+0;
				}else{
					 noOfHours=Math.round(fromHours)+Math.round($jq('#RdurationH'+id).val());
				}
			
			 noOfMinutes=Math.round(fromMinutes)+Math.round($jq('#RdurationM'+id).val());	
		}
		
		if(!isNaN(noOfHours)){
			if(isNaN(noOfMinutes)){
				noOfMinutes=0;
			}
		 var toDate=new Date(fromDate.split("-")[2],fromDate.split("-")[1]-1,fromDate.split("-")[0],noOfHours,noOfMinutes);
		 var formatedToDate=tadaFormatDate(toDate,'dd-NNN-yyyy HH:mm');
		 
		 if(type=='onward'){
			 $jq('#estDate'+id).val(formatedToDate.split(" ")[0]);
	         $jq('#estTime'+id).val(formatedToDate.split(" ")[1]);
		}else if(type=='return'){
			$jq('#RestDate'+id).val(formatedToDate.split(" ")[0]);
	         $jq('#RestTime'+id).val(formatedToDate.split(" ")[1]);
		}
		
		}
	}
		if(errMsg !=''){
			alert(errMsg);
		}
	}
	function textCounterMT(e, des, tbox, maxlimit) {
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
	function checkNextAllocationForRelease(rid){
		if($jq('#'+rid).is(':checked')){
		var allocId=rid.split('_')[0];
		var mapId=rid.split('_')[1];
		/*var fromDate=$jq('#'+rid).parent().parent().find('td').eq(4).text().split(' ')[0];
		var fromTime=$jq('#'+rid).parent().parent().find('td').eq(4).text().split(' ')[1];*/
		var requestDetails = {
				"param" : "checkNextAllocationForRelease",
				"allocId" : allocId,
				"mapId" : mapId,
				"fromDate": $jq('#'+rid).parent().parent().find('td').eq(4).text(),
			    "toDate": $jq('#todayDate'+allocId).val()+" "+$jq('#fromTime'+allocId).val()
		};
		$jq.post('transport.htm', requestDetails, function(html) {
			//html +="<font color=red>${message}</font>";
			$jq('#result1').html(html);
			
		});
		}
	}
	function updateOnwardRequestDetails(){
		var flag=true;
		var errorMessage="";
		$jq('#onwardJourneyDetailsID').find('tr:gt(4)').each(function(){
			/*if($jq(this).attr("id") !=''){
				onwardPassEngRowNo = $jq(this).attr("id");
			}*/
			if($jq(this).find('td').eq(15).find('input:checkbox').is(':checked')){
			if(onwardPassEngRowNo !=""){
				
				if($jq(this).find('td').eq(0).find('input:text').is(':visible') && $jq(this).find('td').eq(0).find('input:text').val()==''){
					errorMessage += "Please Enter No Of People in Onward Journey Details\n";
					flag=false;
				}
				if($jq(this).find('td').eq(1).find('input:text').is(':visible') && $jq(this).find('td').eq(1).find('input:text').val()==''){
					errorMessage += "Please Enter Name With Designation in Onward Journey Details\n";
					flag=false;
				}
				if($jq(this).find('td').eq(2).find('input:text').is(':visible') && $jq(this).find('td').eq(2).find('input:text').val()==''){
					errorMessage += "Please Select Travelling Date in Onward Journey Details\n";
					flag=false;
				}
				if($jq(this).find('td').eq(3).find('input:text').is(':visible') && $jq(this).find('td').eq(3).find('input:text').val()==''){
					errorMessage += "Please Select Travelling Time in Onward Journey Details\n";
					flag=false;
				}
				//changed bcz of duration
				if($jq(this).find('td').eq(5).find('input:text').is(':visible') && $jq(this).find('td').eq(5).find('input:text').val()==''){
					errorMessage += "Please Select Estimated Date in Onward Journey Details\n";
					flag=false;
				}
				if($jq(this).find('td').eq(6).find('input:text').is(':visible') && $jq(this).find('td').eq(6).find('input:text').val()==''){
					errorMessage += "Please Select Estimated Time in Onward Journey Details\n";
					flag=false;
				}
				if($jq(this).find('td').eq(7).find('select').is(':visible') && $jq(this).find('td').eq(7).find('select').val()=='0'){
					errorMessage += "Please Select Pickup Point in Onward Journey Details\n";
					flag=false;
				}
				if($jq(this).find('td').eq(9).find('select').is(':visible') && $jq(this).find('td').eq(9).find('select').val()=='0'){
					errorMessage += "Please Select Dropping Point in Onward Journey Details\n";
					flag=false;
				}
				if($jq(this).find('td').eq(11).find('input:checkbox').is(':visible') && $jq(this).find('td').eq(11).find('input:checkbox').is(':checked')==false){
					errorMessage += "Please Select Accommodation Required or Not in Onward Journey Details\n";
					flag=false;
				}else{
					if($jq(this).find('td').eq(11).find('input:checkbox:checked').val()==1){
						if($jq(this).find('td').eq(12).find('input:text').is(':visible') &&  $jq(this).find('td').eq(12).find('input:text').val() ==''){
							errorMessage += "Please Enter Accommodation Place in Onward Journey Details\n";	
							flag=false;
						}
					}
				}

				if($jq(this).find('td').eq(13).find('input:checkbox').is(':visible') && $jq(this).find('td').eq(13).find('input:checkbox').is(':checked')==false){
					errorMessage += "Please Select Article Carry or Not in Onward Journey Details\n";
					flag=false;
				}
			}
		}
	});
	
		$jq('#onwardJourneyDetailsID').find('tr:gt(4)').each(
				function(){	
					onwardPassEngRowNo = $jq(this).attr("id");
					if(parseFloat(compareDates(convertDate($jq(this).find('td').eq(2).find('input').val()),convertDate($jq(this).find('td').eq(5).find('input').val())))==0){
						if($jq.trim($jq(this).find('td').eq(3).find('input').val().split(":")[0])==$jq.trim($jq(this).find('td').eq(6).find('input').val().split(":")[0])){
							if(($jq.trim($jq(this).find('td').eq(3).find('input').val().split(":")[1])==$jq.trim($jq(this).find('td').eq(6).find('input').val().split(":")[1])) || ($jq.trim($jq(this).find('td').eq(3).find('input').val().split(":")[1]) > $jq.trim($jq(this).find('td').eq(6).find('input').val().split(":")[1]))){
								errorMessage += "Estimated Time Must Be Greater Than Travelling Time In Onward Journey Details\n";
								flag =false;
							}
						}
						if($jq.trim($jq(this).find('td').eq(3).find('input').val().split(":")[0]) > $jq.trim($jq(this).find('td').eq(6).find('input').val().split(":")[0])){
							errorMessage += "Estimated Time Must Be Greater Than Travelling Time In Onward Journey Details\n";
							   flag =false;
						}
					}
				});
		
		
		if(flag){
		var onwardMainJSON = {};
		var onwardPassEngCheck=0;
		
		var onwardPassEngRowNo;
		var errorMsg="";
		
		$jq('#onwardJourneyDetailsID').find('tr').each(function(){
			var onwardPassEngRowJSON = {};
			var onwardArticleTabJSON = {};
			var onwardArtCheck=0;
			var subJSON ={};
			
			if($jq(this).find('td').eq(15).find('input:checkbox').is(':checked')){
				if($jq(this).attr("id") !=''){
					onwardPassEngRowNo = $jq(this).attr("id").split("_")[1];
					//check check box is checked or not
					
					onwardPassEngCheck++
					
				}
				if(onwardPassEngCheck>0){
					
					
					
					
					if($jq(this).find('td').eq(0).find('input:text').is(':visible')){
						onwardPassEngRowJSON["onwardNOP"] = $jq(this).find('td').eq(0).find('input:text').val();
					}
					if($jq(this).find('td').eq(1).find('input:text').is(':visible')){
						onwardPassEngRowJSON["onwardNameWithDesig"] = $jq(this).find('td').eq(1).find('input:text').val();
					}
					if($jq(this).find('td').eq(2).find('input:text').is(':visible')){
						onwardPassEngRowJSON["onwardDepartureDate"] = $jq(this).find('td').eq(2).find('input:text').val();
					}
					if($jq(this).find('td').eq(3).find('input:text').is(':visible')){
						onwardPassEngRowJSON["onwardDepartureTime"] = $jq(this).find('td').eq(3).find('input:text').val();
					}
					if($jq(this).find('td').eq(5).find('input:text').is(':visible')){
						onwardPassEngRowJSON["onwardEstiDate"] = $jq(this).find('td').eq(5).find('input:text').val();
					}
					if($jq(this).find('td').eq(6).find('input:text').is(':visible')){
						onwardPassEngRowJSON["onwardEstiTime"] = $jq(this).find('td').eq(6).find('input:text').val();
					}
					if($jq(this).find('td').eq(7).find('select').is(':visible')){
						onwardPassEngRowJSON["onwardPickup"] = $jq(this).find('td').eq(7).find('select').val();
					}
					if($jq(this).find('td').eq(8).find('input:text').is(':visible')){
						onwardPassEngRowJSON["onwardOtherSource"] = $jq(this).find('td').eq(8).find('input:text').val();
					}
					if($jq(this).find('td').eq(9).find('select').is(':visible')){
						onwardPassEngRowJSON["onwardDrop"] = $jq(this).find('td').eq(9).find('select').val();
					}
					if($jq(this).find('td').eq(10).find('input:text').is(':visible')){
						onwardPassEngRowJSON["onwardOtherDesti"] = $jq(this).find('td').eq(10).find('input:text').val();
					}
					if($jq(this).find('td').eq(11).find('input:checkbox').is(':visible')){
						onwardPassEngRowJSON["onwardAccomReq"] = $jq(this).find('td').eq(11).find('input:checkbox:checked').val();
						if($jq(this).find('td').eq(11).find('input:checkbox:checked').val()==1){
							if($jq(this).find('td').eq(12).find('input:text').is(':visible') &&  $jq(this).find('td').eq(12).find('input:text').val() !=''){
								onwardPassEngRowJSON["onwardAccomPlace"] = $jq(this).find('td').eq(12).find('input:text').val();
							}
						}
					}
					
					if($jq(this).find('td').eq(13).find('input:checkbox').is(':visible')){
						onwardPassEngRowJSON["onwardArtiReq"] = $jq(this).find('td').eq(13).find('input:checkbox:checked').val();
					}
					if($jq(this).find('td').eq(14).find('input:text').is(':visible')){
						onwardPassEngRowJSON["onwardRemarks"] = $jq(this).find('td').eq(14).find('input:text').val();
					}
					if($jq(this).find('td').eq(15).find('input:checkbox').is(':visible')){
						onwardPassEngRowJSON["journeyId"] = $jq(this).find('td').eq(15).find('input:checkbox').attr('id').split('_')[1];
					}
					
					if(onwardPassEngRowJSON.onwardArtiReq == '1'){
						$jq('#passeng'+onwardPassEngRowNo).find('table').find('tr').each(function(){
							var onwardArticleRowJSON = {};
							if($jq(this).attr("id") !=''){
								onwardArtCheck++
							}
							if(onwardArtCheck>0){
								if($jq(this).find('td').eq(0).find('select').is(':visible')){
									onwardArticleRowJSON["onwardArtType"] = $jq(this).find('td').eq(0).find('select').val();
								}
								if($jq(this).find('td').eq(1).find('input:text').is(':visible')){
									onwardArticleRowJSON["onwardArtLength"] = $jq(this).find('td').eq(1).find('input:text').val();
								}
								if($jq(this).find('td').eq(2).find('input:text').is(':visible')){
									onwardArticleRowJSON["onwardArtBreadth"] = $jq(this).find('td').eq(2).find('input:text').val();
								}
								if($jq(this).find('td').eq(3).find('input:text').is(':visible')){
									onwardArticleRowJSON["onwardArtHeight"] = $jq(this).find('td').eq(3).find('input:text').val();
								}
								if($jq(this).find('td').eq(4).find('input:text').is(':visible')){
									onwardArticleRowJSON["onwardArtWeight"] = $jq(this).find('td').eq(4).find('input:text').val();
								}
								if($jq(this).find('td').eq(5).find('input:text').is(':visible')){
									onwardArticleRowJSON["onwardArtQuantity"] = $jq(this).find('td').eq(5).find('input:text').val();
								}
								
								onwardArticleTabJSON[onwardArtCheck]=onwardArticleRowJSON;
							}
						});
					}
					
					subJSON["onwardPassEngRowJSON"]=onwardPassEngRowJSON;
					subJSON["onwardArticleTabJSON"]=onwardArticleTabJSON;
					
					onwardMainJSON[onwardPassEngCheck]=subJSON;
				}
			}
		});
	
		if(JSON.stringify(onwardMainJSON).length==2){
			errorMsg += "Select Atleast One Onward Journey To Update";
		}
		if(errorMsg == ""){
			var requestDetails = {
					"param" : "updateRequestDetails",
					"onwardMainJSON" : JSON.stringify(onwardMainJSON),
			};
			$jq.post('transport.htm', requestDetails, function(html) {
				$jq('#fragment-2').html(html);
				
			});
		}else{
			alert(errorMsg);
		}
		}else{
			alert(errorMessage);
		}
	}
	
	
	function updateReturnRequestDetails(){
		var flag=true;
		var errorMessage="";
		$jq('#returnJourneyDetailsID').find('tr:gt(4)').each(function(){
			/*if($jq(this).attr("id") !=''){
				returnPassEngRowNo = $jq(this).attr("id");
			}*/
			if($jq(this).find('td').eq(15).find('input:checkbox').is(':checked')){
			if(returnPassEngRowNo !=""){
				if($jq(this).find('td').eq(0).find('input:text').is(':visible') && $jq(this).find('td').eq(0).find('input:text').val()==''){
					errorMessage += "Please Enter No Of People in Return Journey Details\n";
					flag=false;
				}
				if($jq(this).find('td').eq(1).find('input:text').is(':visible') && $jq(this).find('td').eq(1).find('input:text').val()==''){
					errorMessage += "Please Enter Name With Designation in Return Journey Details\n";
					flag=false;
				}
				if($jq(this).find('td').eq(2).find('input:text').is(':visible') && $jq(this).find('td').eq(2).find('input:text').val()==''){
					errorMessage += "Please Select Travelling Date in Return Journey Details\n";
					flag=false;
				}
				if($jq(this).find('td').eq(3).find('input:text').is(':visible') && $jq(this).find('td').eq(3).find('input:text').val()==''){
					errorMessage += "Please Select Travelling Time in Return Journey Details\n";
					flag=false;
				}
				if($jq(this).find('td').eq(5).find('input:text').is(':visible') && $jq(this).find('td').eq(5).find('input:text').val()==''){
					errorMessage += "Please Select Estimated Date in Return Journey Details\n";
					flag=false;
				}
				if($jq(this).find('td').eq(6).find('input:text').is(':visible') && $jq(this).find('td').eq(6).find('input:text').val()==''){
					errorMessage += "Please Select Estimated Time in Return Journey Details\n";
					flag=false;
				}
				if($jq(this).find('td').eq(7).find('select').is(':visible') && $jq(this).find('td').eq(7).find('select').val()=='0'){
					errorMessage += "Please Select Pickup Point in Return Journey Details\n";
					flag=false;
				}
				if($jq(this).find('td').eq(9).find('select').is(':visible') && $jq(this).find('td').eq(9).find('select').val()=='0'){
					errorMessage += "Please Select Dropping Point in Return Journey Details\n";
					flag=false;
				}
				if($jq(this).find('td').eq(11).find('input:checkbox').is(':visible') && $jq(this).find('td').eq(11).find('input:checkbox').is(':checked')==false){
					errorMessage += "Please Select Accommodation Required or Not in Return Journey Details\n";
					flag=false;
				}else{
					if($jq(this).find('td').eq(11).find('input:checkbox:checked').val()==1){
						if($jq(this).find('td').eq(12).find('input:text').is(':visible') &&  $jq(this).find('td').eq(12).find('input:text').val() ==''){
							errorMessage += "Please Enter Accommodation Place in Return Journey Details\n";	
							flag=false;
						}
					}
				}
			
				if($jq(this).find('td').eq(13).find('input:checkbox').is(':visible') && $jq(this).find('td').eq(13).find('input:checkbox').is(':checked')==false){
					errorMessage += "Please Select Article Carry or Not in Return Journey Details\n";
					flag=false;
				}
				
			}
		}
	});
		
		
	if(flag){	
	var returnMainJSON = {};
	var returnPassEngCheck=0;
	var returnPassEngRowNo;
	var errorMsg="";
	
	$jq('#returnJourneyDetailsID').find('tr').each(function(){
		var  returnPassEngRowJSON = {};
		var  returnArticleTabJSON = {};
		var  returnArtCheck=0;
		var subJSON ={};
		
		if($jq(this).find('td').eq(15).find('input:checkbox').is(':checked')){
			if($jq(this).attr("id") !=''){
				 returnPassEngRowNo = $jq(this).attr("id").split("_")[1];
				//check check box is checked or not
				
				 returnPassEngCheck++
				
			}
			if(returnPassEngCheck>0){
				
				if($jq(this).find('td').eq(0).find('input:text').is(':visible')){
					 returnPassEngRowJSON["returnNOP"] = $jq(this).find('td').eq(0).find('input:text').val();
				}
				if($jq(this).find('td').eq(1).find('input:text').is(':visible')){
					returnPassEngRowJSON["returnNameWithDesig"] = $jq(this).find('td').eq(1).find('input:text').val();
				}
				if($jq(this).find('td').eq(2).find('input:text').is(':visible')){
					returnPassEngRowJSON["returnDepartureDate"] = $jq(this).find('td').eq(2).find('input:text').val();
				}
				if($jq(this).find('td').eq(3).find('input:text').is(':visible')){
					returnPassEngRowJSON["returnDepartureTime"] = $jq(this).find('td').eq(3).find('input:text').val();
				}
				if($jq(this).find('td').eq(5).find('input:text').is(':visible')){
					returnPassEngRowJSON["returnEstiDate"] = $jq(this).find('td').eq(5).find('input:text').val();
				}
				if($jq(this).find('td').eq(6).find('input:text').is(':visible')){
					returnPassEngRowJSON["returnEstiTime"] = $jq(this).find('td').eq(6).find('input:text').val();
				}
				if($jq(this).find('td').eq(7).find('select').is(':visible')){
					returnPassEngRowJSON["returnPickup"] = $jq(this).find('td').eq(7).find('select').val();
				}
				if($jq(this).find('td').eq(8).find('input:text').is(':visible')){
					returnPassEngRowJSON["returnOtherSource"] = $jq(this).find('td').eq(8).find('input:text').val();
				}
				if($jq(this).find('td').eq(9).find('select').is(':visible')){
					returnPassEngRowJSON["returnDrop"] = $jq(this).find('td').eq(9).find('select').val();
				}
				if($jq(this).find('td').eq(10).find('input:text').is(':visible')){
					returnPassEngRowJSON["returnOtherDesti"] = $jq(this).find('td').eq(10).find('input:text').val();
				}
				if($jq(this).find('td').eq(11).find('input:checkbox').is(':visible')){
					returnPassEngRowJSON["returnAccomReq"] = $jq(this).find('td').eq(11).find('input:checkbox:checked').val();
					if($jq(this).find('td').eq(11).find('input:checkbox:checked').val()==1){
						if($jq(this).find('td').eq(12).find('input:text').is(':visible') &&  $jq(this).find('td').eq(12).find('input:text').val() !=''){
							returnPassEngRowJSON["returnAccomPlace"] = $jq(this).find('td').eq(12).find('input:text').val();
						}
					}
				}
				
				if($jq(this).find('td').eq(13).find('input:checkbox').is(':visible')){
					returnPassEngRowJSON["returnArtiReq"] = $jq(this).find('td').eq(13).find('input:checkbox:checked').val();
				}
				if($jq(this).find('td').eq(14).find('input:text').is(':visible')){
					returnPassEngRowJSON["returnRemarks"] = $jq(this).find('td').eq(14).find('input:text').val();
				}
				if($jq(this).find('td').eq(15).find('input:checkbox').is(':visible')){
					returnPassEngRowJSON["journeyId"] = $jq(this).find('td').eq(15).find('input:checkbox').attr('id').split('_')[1];
				}
				
				if(returnPassEngRowJSON.returnArtiReq == '1'){
					$jq('#Rpasseng'+returnPassEngRowNo).find('table').find('tr').each(function(){
						var returnArticleRowJSON = {};
						if($jq(this).attr("id") !=''){
							returnArtCheck++
						}
						if(returnArtCheck>0){
							if($jq(this).find('td').eq(0).find('select').is(':visible')){
								returnArticleRowJSON["returnArtType"] = $jq(this).find('td').eq(0).find('select').val();
							}
							if($jq(this).find('td').eq(1).find('input:text').is(':visible')){
								returnArticleRowJSON["returnArtLength"] = $jq(this).find('td').eq(1).find('input:text').val();
							}
							if($jq(this).find('td').eq(2).find('input:text').is(':visible')){
								returnArticleRowJSON["returnArtBreadth"] = $jq(this).find('td').eq(2).find('input:text').val();
							}
							if($jq(this).find('td').eq(3).find('input:text').is(':visible')){
								returnArticleRowJSON["returnArtHeight"] = $jq(this).find('td').eq(3).find('input:text').val();
							}
							if($jq(this).find('td').eq(4).find('input:text').is(':visible')){
								returnArticleRowJSON["returnArtWeight"] = $jq(this).find('td').eq(4).find('input:text').val();
							}
							if($jq(this).find('td').eq(5).find('input:text').is(':visible')){
								returnArticleRowJSON["returnArtQuantity"] = $jq(this).find('td').eq(5).find('input:text').val();
							}
							
							returnArticleTabJSON[returnArtCheck]=returnArticleRowJSON;
						}
					});
				}
				
				subJSON["returnPassEngRowJSON"]=returnPassEngRowJSON;
				subJSON["returnArticleTabJSON"]=returnArticleTabJSON;
				
				returnMainJSON[returnPassEngCheck]=subJSON;
			}
		}
	});

	if(JSON.stringify(returnMainJSON).length==2){
		errorMsg += "Select Atleast One Return Journey To Update";
	}
	if(errorMsg == ""){
		var requestDetails = {
				"param" : "updateRequestDetails",
				"returnMainJSON" : JSON.stringify(returnMainJSON),
		};
		$jq.post('transport.htm', requestDetails, function(html) {
			$jq('#fragment-2').html(html);
		});
	}else{
		alert(errorMsg);
	}
	}else{
		alert(errorMessage);
	}
}
	
	function tadaFormatDate(date,format) {
		format=format+"";
		var result="";
		var i_format=0;
		var c="";
		var token="";
		var y=date.getYear()+"";
		var M=date.getMonth()+1;
		var d=date.getDate();
		var E=date.getDay();
		var H=date.getHours();
		var m=date.getMinutes();
		var s=date.getSeconds();
		var yyyy,yy,MMM,MM,dd,hh,h,mm,ss,ampm,HH,H,KK,K,kk,k;
		// Convert real date parts into formatted versions
		var value=new Object();
		if (y.length < 4) {y=""+(y-0+1900);}
		value["y"]=""+y;
		value["yyyy"]=y;
		value["yy"]=y.substring(2,4);
		value["M"]=M;
		value["MM"]=LZ(M);
		value["MMM"]=MONTH_NAMES[M-1];
		value["NNN"]=MONTH_NAMES[M+11];
		value["d"]=d;
		value["dd"]=LZ(d);
		value["E"]=DAY_NAMES[E+7];
		value["EE"]=DAY_NAMES[E];
		value["H"]=H;
		value["HH"]=LZ(H);
		if (H==0){value["h"]=12;}
		else if (H>12){value["h"]=H-12;}
		else {value["h"]=H;}
		value["hh"]=LZ(value["h"]);
		if (H>11){value["K"]=H-12;} else {value["K"]=H;}
		value["k"]=H+1;
		value["KK"]=LZ(value["K"]);
		value["kk"]=LZ(value["k"]);
		if (H > 11) { value["a"]="PM"; }
		else { value["a"]="AM"; }
		value["m"]=m;
		value["mm"]=LZ(m);
		value["s"]=s;
		value["ss"]=LZ(s);
		while (i_format < format.length) {
			c=format.charAt(i_format);
			token="";
			while ((format.charAt(i_format)==c) && (i_format < format.length)) {
				token += format.charAt(i_format++);
				}
			if (value[token] != null) { result=result + value[token]; }
			else { result=result + token; }
			}
		return result;
	}
	function compareDates(dt1, dt2){

		var dateRegEx = /^([0123]?\d)[\.\-\/\s]?([0123]?\d)[\.\-\/\s]?(\d{4})$/;
		var result1 = dt1.match(dateRegEx);
		var result2 = dt2.match(dateRegEx);
		if(result1 != null){
		     var month1 = result1[2];
		     var day1 = result1[1];
		     var year1 = result1[3];
		}
		if(result2 != null){
		     var month2 = result2[2];
		     var day2 = result2[1];
		     var year2 = result2[3];
		}
		if(result1 && result2){
			var d2 = new Date(year1, month1-1, day1);
			var d1 = new Date(year2, month2-1, day2);
			
			var diff = d1.getTime() - d2.getTime();
			return diff = (((diff / 1000) / 60) / 60) / 24;
		}
		return null;
	}
	//give search option for edit tab
	function checkEditCheckbox(jTypeId,RID){
		var journeyGridId="";
		var temp="";
		var fromDate="";
		var fromTime="";
		var toDate="";
		var toTime="";
		
		
		var type = RID.split("_")[1];
		//unchecked
		if($jq('#'+jTypeId).is(':checked')==false){
			//$jq('#'+RID).attr("checked",false);
	     if(type == "ONWARD"){
				/*$jq("#OnwardVehicleAllotmentDetailsGrid").html('');
				$jq("#OnwardCombineVehicleDetailsGrid").html('');
				$jq('#onwardSubmitDiv').css("display","none");
				$jq('#onwardSearchDivId').html('');
				$jq('#onwardSearch').css("display","none");*/
	    	    $jq('#editOnwardSearchDivId').html('');
				$jq('#editOnwardSearch').css("display","none");
				$jq('#editOnwardAvailableVehiclesGrid').html('');
				$jq('#editOnwardCombineVehiclesGrid').html('');
				
			}
			if(type == "RETURN"){
				 $jq('#editReturnSearchDivId').html('');
					$jq('#editReturnSearch').css("display","none");
					$jq('#editReturnAvailableVehiclesGrid').html('');
					$jq('#editReturnCombineVehiclesGrid').html('');
			}
		}
		//checked
		if($jq('#'+jTypeId).is(':checked')){
			if(type == "ONWARD"){
				/*journeyGridId="OnwardPassEngVehicleAllotGrid";
				$jq('#onwardSearchDivId').html('');
				$jq('#onwardSearch').css("display","block");
				//for hide available,busy list when check another checkbox
				$jq('#OnwardVehicleAllotmentDetailsGrid').html('');
				$jq('#OnwardCombineVehicleDetailsGrid').html('');*/
				journeyGridId="onwardJourneyDetailsID";
				
				$jq('#editOnwardSearchDivId').html('');
				$jq('#editOnwardSearch').css("display","block");
				$jq('#editOnwardAvailableVehiclesGrid').html('');
				$jq('#editOnwardCombineVehiclesGrid').html('');
			}
			if(type == "RETURN"){
	journeyGridId="returnJourneyDetailsID";
				
				$jq('#editReturnSearchDivId').html('');
				$jq('#editReturnSearch').css("display","block");
				$jq('#editReturnAvailableVehiclesGrid').html('');
				$jq('#editReturnCombineVehiclesGrid').html('');
			}
			$jq('#'+journeyGridId).find('tr:gt(2)').each(function(){
				if($jq(this).find('td').eq(15).find('input').attr('id')!=jTypeId){
				$jq(this).find('td').eq(15).find('input:checkbox').attr('checked',false);
				}
			});
			
			$jq('#'+journeyGridId).find('tr:gt(2)').each(function(){
				if($jq(this).find('td').eq(15).find('input').attr('id')==jTypeId){
					fromDate=$jq(this).find('td').eq(2).find('input').val();
					fromTime=$jq(this).find('td').eq(3).find('input').val();
					toDate=	$jq(this).find('td').eq(5).find('input').val();
					toTime=$jq(this).find('td').eq(6).find('input').val();
						
					temp += "<a onclick=editSearchAvailableVehicles('"+jTypeId+"','"+RID+"')><span>Get Available Vehicles</span></a>";
				}
			});
			if(type == "ONWARD"){
				$jq('#editSearchFromDate').val(fromDate);
				$jq('#editSearchFromTime').val(fromTime);
				$jq('#editSearchToDate').val(toDate);
				$jq('#editSearchToTime').val(toTime);
				
				$jq('#editOnwardSearchDivId').append(temp);
			}
			if(type == "RETURN"){
				$jq('#editRSearchFromDate').val(fromDate);
				$jq('#editRSearchFromTime').val(fromTime);
				$jq('#editRSearchToDate').val(toDate);
				$jq('#editRSearchToTime').val(toTime);
				
				$jq('#editReturnSearchDivId').append(temp);
			}
			
		}
		
		
	}
	function editSearchAvailableVehicles(jTypeId,RID){
		var type = RID.split("_")[1];
			
			
		var searchFromDate="";
		var searchFromTime="";
		var searchToDate="";
		var searchToTime="";

		var searchFromDateId="";
		var searchFromTimeId="";
		var searchToDateId="";
		var searchToTimeId="";
		var errMsg="";
		var flag=true;

		    if(type == "ONWARD"){
		    	$jq("#editOnwardCombineVehiclesGrid").html('');
		            searchFromDateId="editSearchFromDate";
		    		searchFromTimeId="editSearchFromTime";
		    		searchToDateId="editSearchToDate";
		    		searchToTimeId="editSearchToTime";	
		    }
		    if(type == "RETURN"){
		    	$jq("#editReturnCombineVehiclesGrid").html('');
	            searchFromDateId="editRSearchFromDate";
	    		searchFromTimeId="editRSearchFromTime";
	    		searchToDateId="editRSearchToDate";
	    		searchToTimeId="editRSearchToTime";	
		    }
		    
		    //for dates check
		    var fdate=convertDate($jq('#'+searchFromDateId).val());
		    var tdate=convertDate($jq('#'+searchToDateId).val());
		    if(compareDates(fdate,'dd-MM-yyyy',tdate,'dd-MM-yyyy')==1){
		    	errMsg += "To Date Must be Greater Than From Date \n";
					flag = false;
			}else if(compareDates(tdate,'dd-MM-yyyy',fdate,'dd-MM-yyyy')!=1){
				var ftime=$jq('#'+searchFromTimeId).val();
				var ttime=$jq('#'+searchToTimeId).val();
				if(compareHoursTime(ftime.split(":")[0],ttime.split(":")[0])==1){
					errMsg += "To Time Must be Greater Than From Time \n";
					flag = false;
				}else if(compareHoursTime(ftime.split(":")[0],ttime.split(":")[0])==0){
					if(compareMinutesTime(ftime.split(":")[1],ttime.split(":")[1])==1 || compareMinutesTime(ftime.split(":")[1],ttime.split(":")[1])==0){
						errMsg += "To Time Must be Greater Than From Time \n";
						flag = false;
					}
				}
			}	
		    
		    //for from time check
		    if($jq('#'+searchFromTimeId).val()==''){
				errMsg += "Enter Search From Time To Get Available Vehicles\n";
				flag=false;
			}else {
				if( $jq('#'+searchFromTimeId).val().indexOf(':')==-1){
					errMsg += "Enter From Time In HH:MM formate\n";
					$jq('#'+searchFromTimeId).focus();
					flag = false;
				}else{
					if($jq('#'+searchFromTimeId).val().split(':')[0].trim().length!=2 || $jq('#'+searchFromTimeId).val().split(':')[1].trim().length!=2){
						errMsg += "Enter From Time In HH:MM formate\n";
						$jq('#'+searchFromTimeId).focus();
						flag = false;
					}
				if($jq('#'+searchFromTimeId).val().split(':')[0]<0 || $jq('#'+searchFromTimeId).val().split(':')[0]>23){
					errMsg += "Enter Valid Hours For Search From Time\n";
					$jq('#'+searchFromTimeId).focus();
		    		flag=false;
				}
				if(($jq('#'+searchFromTimeId).val().split(':')[0]==0 && $jq('#'+searchFromTimeId).val().split(':')[1]<=0) || $jq('#'+searchFromTimeId).val().split(':')[1]>59){
					errMsg += "Enter Valid Minutes For Search From Time\n";
					$jq('#'+searchFromTimeId).focus();
		    		flag=false;
				}
				}
			}
		    
		    //for to time check
		    if($jq('#'+searchToTimeId).val()==''){
				errMsg += "Enter Search To Time To Get Available Vehicles\n";
				flag=false;
			}else {
				if( $jq('#'+searchToTimeId).val().indexOf(':')==-1){
					errMsg += "Enter ToTime In HH:MM formate\n";
					$jq('#'+searchToTimeId).focus();
					flag = false;
				}else{
					if($jq('#'+searchToTimeId).val().split(':')[0].trim().length!=2 || $jq('#'+searchToTimeId).val().split(':')[1].trim().length!=2){
						errMsg += "Enter To Time In HH:MM formate\n";
						$jq('#'+searchToTimeId).focus();
						flag = false;
					}
				if($jq('#'+searchToTimeId).val().split(':')[0]<0 || $jq('#'+searchToTimeId).val().split(':')[0]>23){
					errMsg += "Enter Valid Hours For Search To Time\n";
					$jq('#'+searchToTimeId).focus();
		    		flag=false;
				}
				if(($jq('#'+searchToTimeId).val().split(':')[0]==0 && $jq('#'+searchToTimeId).val().split(':')[1]<=0) || $jq('#'+searchToTimeId).val().split(':')[1]>59){
					errMsg += "Enter Valid Minutes For Search To Time\n";
					$jq('#'+searchToTimeId).focus();
		    		flag=false;
				}
				}
			}
		    
			if(flag){
			 searchFromDate=$jq('#'+searchFromDateId).val();
			 searchFromTime=$jq('#'+searchFromTimeId).val();
			 searchToDate=$jq('#'+searchToDateId).val();
			 searchToTime=$jq('#'+searchToTimeId).val();
			}
		    
		    
		    if(flag){
				var requestDetails = {
						"param" : "getVDList",
						"pk" :jTypeId,
						"type" : type,
						"searchFromDate":searchFromDate,
						"searchFromTime":searchFromTime,
					    "searchToDate":searchToDate,
					    "searchToTime":searchToTime,
					    "remarks":"editAlloc"
				};
				$jq.post('transport.htm', requestDetails, function(html) {
					//for display busy vehicles
					var temp = "<div class=line><fieldset style=float:left;width:100%><legend><strong><font color=green>Busy Vehicle-Driver Details </font></strong></legend>";
					temp += "<div class=line><table id=busyVehicles class=fortable width=100% border=2px align=center>";
					temp += "<tr>";
					temp += "<td class=tabletd align=center>Select</td>";
					temp += "<td class=tabletd align=center>Vehicle No</td>";
					temp += "<td class=tabletd align=center>Driver Name</td>";
					temp += "<td class=tabletd align=center>From Date</td>";
					temp += "<td class=tabletd align=center>To Date</td>";
					temp += "<td class=tabletd align=center>Vehicle Capacity</td>";
					temp += "<td class=tabletd align=center>Allocated No.Of Persons</td>";
					temp += "</tr>";
					
					
					
						
						
					if(type == "ONWARD"){
						$jq("#editOnwardAvailableVehiclesGrid").html(html);
						$jq("#editOnwardAvailableVehiclesGrid").css("display","block");
						
						if(BusyVDListJSON.length>0){
							//for onward busy details table
							for(var k=0;k<BusyVDListJSON.length;k++){
								temp += "<tr>";
							temp += "<td align=center><input type=checkbox id="+BusyVDListJSON[k].vehicleDriverMapId+" onchange=editAllotOrCombineButtons('"+type+"','combineAlloc')></input></td>";
								//temp += "<td align=center><input type=checkbox id="+BusyVDListJSON[k].id+" onchange=allotOrCombineButtons('"+type+"','combineAlloc')></input></td>";
								temp += "<td align=center>"+BusyVDListJSON[k].vehicleDriverMapDetails.vehicleDetails.vehicleNo+"("+BusyVDListJSON[k].vehicleDriverMapDetails.vehicleDetails.vehiclePoolTypeDetails.vehiclePoolType+")</td>";
								temp += "<td align=center>"+BusyVDListJSON[k].vehicleDriverMapDetails.diverDetails.driverName+"</td>";
								temp += "<td align=center>"+BusyVDListJSON[k].stringFromDate+"</td>";
								temp += "<td align=center>"+BusyVDListJSON[k].stringToDate+"</td>";
								temp += "<td align=center>"+BusyVDListJSON[k].vehicleDriverMapDetails.vehicleDetails.noOfPeople+"</td>";
								temp += "<td align=center>"+BusyVDListJSON[k].allotedNoOfPersons+"</td>";
								temp += "</tr>";
							}
						}else{
							temp += "<td colspan=7>Nothing found to display</td>";
						}
						
						temp += "</table></div></fieldset></div>";
						 $jq("#editOnwardCombineVehiclesGrid").append(temp);
						$jq("#editOnwardCombineVehiclesGrid").css("display","block");
						//$jq('#onwardSubmitDiv').css("display","block");
					}
					if(type == "RETURN"){
						$jq("#editReturnAvailableVehiclesGrid").html(html);
						$jq("#editReturnAvailableVehiclesGrid").css("display","block");
						
						if(BusyVDListJSON.length>0){
							//for onward busy details table
							for(var k=0;k<BusyVDListJSON.length;k++){
								temp += "<tr>";
							temp += "<td align=center><input type=checkbox id="+BusyVDListJSON[k].vehicleDriverMapId+" onchange=editAllotOrCombineButtons('"+type+"','combineAlloc')></input></td>";
								//temp += "<td align=center><input type=checkbox id="+BusyVDListJSON[k].id+" onchange=allotOrCombineButtons('"+type+"','combineAlloc')></input></td>";
								temp += "<td align=center>"+BusyVDListJSON[k].vehicleDriverMapDetails.vehicleDetails.vehicleNo+"("+BusyVDListJSON[k].vehicleDriverMapDetails.vehicleDetails.vehiclePoolTypeDetails.vehiclePoolType+")</td>";
								temp += "<td align=center>"+BusyVDListJSON[k].vehicleDriverMapDetails.diverDetails.driverName+"</td>";
								temp += "<td align=center>"+BusyVDListJSON[k].stringFromDate+"</td>";
								temp += "<td align=center>"+BusyVDListJSON[k].stringToDate+"</td>";
								temp += "<td align=center>"+BusyVDListJSON[k].vehicleDriverMapDetails.vehicleDetails.noOfPeople+"</td>";
								temp += "<td align=center>"+BusyVDListJSON[k].allotedNoOfPersons+"</td>";
								temp += "</tr>";
							}
						}else{
							temp += "<td colspan=7>Nothing found to display</td>";
						}
						
						temp += "</table></div></fieldset></div>";
						 $jq("#editReturnCombineVehiclesGrid").append(temp);
						$jq("#editReturnCombineVehiclesGrid").css("display","block");
			}
					
				});
		    }else{
		    	alert(errMsg);
		    }
			}
	function editAllotOrCombineButtons(journeyType,allocType){
		
		 $jq('#editOnwardSubmitDiv').html('');
		$jq('#editReturnSubmitDiv').html('');
		//for onward
		if(journeyType=='ONWARD'){
			if(allocType=='allocate'){
				if($jq('#editOnwardAvailableVehiclesGrid').find('input:checkbox').is(':checked')){
					//if we check free vehicles,then deselect the busy vehicles checkboxes
					$jq('#editOnwardCombineVehiclesGrid').find('tr').each(
							function(){
								$jq(this).find('input:checkbox').attr('checked',false)
							});
					 $jq('#editOnwardSubmitDiv').append('<div class="expbutton"><a onclick="updateVehicleAllocationDetails(\'onward\')"> <span>Allocate For Onward</span></a></div>');
				}else{
					$jq('#editOnwardSubmitDiv').html('');
				}
				
			}else if(allocType=='combineAlloc'){
				if($jq('#editOnwardCombineVehiclesGrid').find('input:checkbox').is(':checked')){
					//if we check busy vehicles,then deselect the free vehicles checkboxes
					$jq('#editOnwardAvailableVehiclesGrid').find('tr').each(
							function(){
								$jq(this).find('input:checkbox').attr('checked',false)
							});
					
				      $jq('#editOnwardSubmitDiv').append('<div class="expbutton" ><a onclick="updateCombineAlloc(\'onward\')"><span>Combine Allocation</span></a></div>');
				}else{
					 $jq('#editOnwardSubmitDiv').html('');
				 }
				}
			 $jq('#editOnwardSubmitDiv').css("display","block");
		}
		//for return
		else if(journeyType=='RETURN'){
			if(allocType=='allocate'){
				if($jq('#editReturnAvailableVehiclesGrid').find('input:checkbox').is(':checked')){
					//if we check free vehicles,then deselect the busy vehicles checkboxes
					$jq('#editReturnCombineVehiclesGrid').find('tr').each(
							function(){
								$jq(this).find('input:checkbox').attr('checked',false)
							});
					 $jq('#editReturnSubmitDiv').append('<div class="expbutton"><a onclick="updateVehicleAllocationDetails(\'return\')"> <span>Allocate For Return</span></a></div>');
				}else{
					$jq('#editReturnSubmitDiv').html('');
				}
				
			}else if(allocType=='combineAlloc'){
				if($jq('#editReturnCombineVehiclesGrid').find('input:checkbox').is(':checked')){
					//if we check busy vehicles,then deselect the free vehicles checkboxes
					$jq('#editReturnAvailableVehiclesGrid').find('tr').each(
							function(){
								$jq(this).find('input:checkbox').attr('checked',false)
							});
					
				      $jq('#editReturnSubmitDiv').append('<div class="expbutton" ><a onclick="updateCombineAlloc(\'return\')"><span>Combine Allocation</span></a></div>');
				}else{
					 $jq('#editReturnSubmitDiv').html('');
				 }
				}
			 $jq('#editReturnSubmitDiv').css("display","block");
		}
		  
	}
function editCheckArticle(type,flag,row,rid){
	var requestId=rid.split('_')[0];
	var journeyId=rid.split('_')[1];
	if(type=='return'){
		if(flag=='NO'){
			$jq('#returnArticleDetailsID').css('display','block');
		}
	}
	if(type=='onward'){
		if(flag=='NO'){
			$jq('#onwardArticleDetailsID').css('display','block');
		}
	}
}
//for edit allocation for vehicle
function updateVehicleAllocationDetails(type){
	
	var journeyId;
	var mapId;
	var fromDate;
	var fromTime;
	var toDate;
	var toTime;
	var journeyDetailsGridId;
	var vehicleDetailsGridId;
	var requestId;
	var journeyFlag=false;
	var vehicleFlag=false;
	var errMessage="";
	var errMessage1="";
	
	
	
	if(type=='onward'){
		journeyDetailsGridId="onwardJourneyDetailsID";
		vehicleDetailsGridId="editOnwardAvailableVehiclesGrid";
	}else if(type=='return'){
		journeyDetailsGridId="returnJourneyDetailsID";
		vehicleDetailsGridId="editReturnAvailableVehiclesGrid";
	}
	// for alerts
	/*$jq('#'+journeyDetailsGridId).find('tr:gt(2)').each(function(){
		
			if($jq(this).find('td').eq(15).find('input:checkbox').is(':checked') && $jq(this).find('td').eq(15).find('input:checkbox').attr('disabled')==false){
				if(journeyFlag==true && errMessage==''){
					errMessage += "Please select only one row on journy details grid\n";
				}else{
					journeyFlag=true;
				}
				
			}
		
	});*/
	$jq('#'+vehicleDetailsGridId).find('tr:gt(0)').each(function(){
		
		if($jq(this).find('td').eq(0).find('input:checkbox').is(':checked')){
			if(vehicleFlag==true && errMessage1==''){
				errMessage1 += "Please select only one vehicle from available vehicle-driver details grid\n";
			}else{
				vehicleFlag=true;
			}
			
		}
	
});
	/*if(journeyFlag==false){
		errMessage += "Please select atleast one row on journy details grid\n";
	}*/
	 if(vehicleFlag==false){
		errMessage += "Please select atleast one vehicle from available vehicle-driver details grid\n";
	}
	errMessage += errMessage1;
	
	if(errMessage==""){
	$jq('#'+journeyDetailsGridId).find('tr:gt(2)').each(function(){
		if($jq(this).find('td').eq(15).find('input:checkbox').is(':checked') && $jq(this).find('td').eq(15).find('input:checkbox').attr('disabled')==false){
			journeyId=$jq(this).find('td').eq(15).find('input:checkbox').attr('id');
			/*fromDate=$jq(this).find('td').eq(3).text();
			fromTime=$jq(this).find('td').eq(4).text();
			toDate=$jq(this).find('td').eq(5).text();
			toTime=$jq(this).find('td').eq(6).text();*/
			
			//provide search functionality
			if(type=='onward'){
			fromDate=$jq("#editSearchFromDate").val();
			fromTime=$jq("#editSearchFromTime").val();
			toDate=$jq("#editSearchToDate").val();
			toTime=$jq("#editSearchToTime").val();
			}
			if(type=='return'){
				fromDate=$jq("#editRSearchFromDate").val();
				fromTime=$jq("#editRSearchFromTime").val();
				toDate=$jq("#editRSearchToDate").val();
				toTime=$jq("#editRSearchToTime").val();
				}
			requestId=journeyId.split("_")[0];
		}
	});
	$jq('#'+vehicleDetailsGridId).find('tr:gt(0)').each(function(){
		if($jq(this).find('td').eq(0).find('input:checked').is(':checked')){
			mapId=$jq(this).find('td').eq(0).find('input:checked').attr('id');
		}
	});	
	
	var requestDetails = {
			"param" : "updateVehicleAllocationDetails",
			"journeyId" :journeyId.split("_")[1],
			"mapId" : mapId,
			"fromDate" : fromDate,
			"fromTime" : fromTime,
			"toDate" : toDate,
			"toTime" : toTime
	};
	$jq.post('transport.htm', requestDetails, function(html) {
		//$jq('#result').html(html);
		//var temp2 = "<div class='line'><div>"+html+"</div></div>";
		$jq('#fragment-2').html(html);
		
		/*showOnwardPassEngVehicleAllotGrid(requestId);
		showReturnPassEngVehicleAllotGrid(requestId);*/
	});
}else{
	alert(errMessage);
}
}
function updateCombineAlloc(type){
	var journeyDetailsGridId;
	var busyVehicleGridId;
	var journeyId;
	//var allocId;
	var mapId;
	var requestId;
	var journeyFlag=false;
	var vehicleFlag=false;
	var errMessage="";
	var errMessage1="";
	if(type=='onward'){
		journeyDetailsGridId="onwardJourneyDetailsID";
		busyVehicleGridId="editOnwardCombineVehiclesGrid";
	}
	if(type=='return'){
		journeyDetailsGridId="returnJourneyDetailsID";
		busyVehicleGridId="editReturnCombineVehiclesGrid";
	}
	// for alerts-----
	/*$jq('#'+journeyDetailsGridId).find('table tr:gt(2)').each(function(){
		
			if($jq(this).find('td').eq(15).find('input:checkbox').is(':checked') && $jq(this).find('td').eq(15).find('input:checkbox').attr('disabled')==false){
				if(journeyFlag==true && errMessage==''){
					errMessage += "Please select only one row on journy details grid\n";
				}else{
					journeyFlag=true;
				}
				
			}
		
	});*/
	$jq('#'+busyVehicleGridId).find('table tr:gt(0)').each(function(){
		
		if($jq(this).find('td').eq(0).find('input:checkbox').is(':checked')){
			if(vehicleFlag==true && errMessage1==''){
				errMessage1 += "Please select only one vehicle from busy vehicle-driver details grid\n";
			}else{
				vehicleFlag=true;
			}
			
		}
	
});
	/*if(journeyFlag==false){
		errMessage += "Please select atleast one row on journy details grid\n";
	}*/
	if(vehicleFlag==false){
		errMessage += "Please select atleast one vehicle from busy vehicle-driver details grid\n";
	}
	errMessage += errMessage1;
	//-----
	if(errMessage==""){
	//for journeyid
	$jq('#'+journeyDetailsGridId).find('tr:gt(2)').each(function(){
	if($jq(this).find('td').eq(15).find('input:checkbox').is(':checked') && $jq(this).find('td').eq(15).find('input:checkbox').attr('disabled')==false){
		journeyId=$jq(this).find('td').eq(15).find('input:checkbox').attr('id').split('_')[1];
		requestId=$jq(this).find('td').eq(15).find('input:checkbox').attr('id').split('_')[0];
	}
	});
	//for map id  
	$jq('#'+busyVehicleGridId).find('table tr:gt(0)').each(function(){
		if($jq(this).find('td').eq(0).find('input:checkbox').is(':checked')){
			mapId=$jq(this).find('td').eq(0).find('input:checkbox').attr('id');
		}
		});
	if(type=='onward'){
		fromDate=$jq("#editSearchFromDate").val();
		fromTime=$jq("#editSearchFromTime").val();
		toDate=$jq("#editSearchToDate").val();
		toTime=$jq("#editSearchToTime").val();
		}
		if(type=='return'){
			fromDate=$jq("#editRSearchFromDate").val();
			fromTime=$jq("#editRSearchFromTime").val();
			toDate=$jq("#editRSearchToDate").val();
			toTime=$jq("#editRSearchToTime").val();
			}
	var requestDetails = {
			/*
			"param" : "saveCombineAlloc",
			"journeyId" : journeyId,
			"mapId" : mapId,
			"searchFromDate":fromDate,
			"searchFromTime":fromTime,
		    "searchToDate":toDate,
		    "searchToTime":toTime*/
			"param" : "updateCombineAlloc",
			"journeyId" : journeyId,
			"mapId" : mapId,
			"fromDate":fromDate,
			"fromTime":fromTime,
		    "toDate":toDate,
		    "toTime":toTime
	};
	$jq.post('transport.htm', requestDetails, function(html) {
		$jq('#fragment-2').html(html);
		/*showOnwardPassEngVehicleAllotGrid(requestId);
	    showReturnPassEngVehicleAllotGrid(requestId);*/
	});
}else{
	alert(errMessage);
}
	
}
function clearReports(){
	$jq("select").val('select');
}