
function resetReportList(type){
	
	if(document.getElementById(type)!=null){
		document.getElementById(type).value='select';		
	}	
	if(type=='inventoryNo') {
		document.getElementById('inventoryNo').value='select';		
	}
	if(type=='empmapping') {
		document.getElementById('hierarchy').value='select';
		document.getElementById('reportFormaty').checked = false;
		document.getElementById('reportFormatn').checked = false;		
	}
	if(type=='category') {
		document.getElementById('category').value='select';
		document.getElementById('reportFormaty').checked = false;
		document.getElementById('reportFormatn').checked = false;	
	}
	if(type=='designation') {
		document.getElementById('designation').value='select';
		document.getElementById('reportFormaty').checked = false;
		document.getElementById('reportFormatn').checked = false;	
	}
	if(type=='groupName') {
		document.getElementById('groupName').value='select';
		document.getElementById('reportFormaty').checked = false;
		document.getElementById('reportFormatn').checked = false;	
	}
		if(type=='totalVacant' || type=='halfYearEnding' || type=='empDetails' || type=='annualYearEnding'||type=='phWise'||type=='reservationReport' ||type=='areaofDeployment') {
		document.getElementById('reportFormaty').checked = false;
		document.getElementById('reportFormatn').checked = false;			
	}	
	if(type=='religionName') {
		document.getElementById('religionName').value='select';
		document.getElementById('reportFormaty').checked = false;
		document.getElementById('reportFormatn').checked = false;	
	}
	if(type=='community') {
		document.getElementById('community').value='select';
		document.getElementById('reportFormaty').checked = false;
		document.getElementById('reportFormatn').checked = false;	
	}
	if(type=='hierarchy') {
		document.getElementById('hierarchy').value='select';
		document.getElementById('reportFormaty').checked = false;
		document.getElementById('reportFormatn').checked = false;
	}
	if(type=='CashPurchaseVoucher'||type=='CashPurchaseDemand' || type=='HalfYearPhReport' || type=='HalfYearAllPhReport' || type=='dojASL' || type=='scStObc' || type=='lastModified'||type=='DOJDRDO') {
		document.getElementById('fromDate').value="";
		document.getElementById('toDate').value="";
		document.getElementById('reportFormaty').checked = false;
		document.getElementById('reportFormatn').checked = false;		
	}
	if(type=='monthly') {
		document.getElementById('monthlyFlagY').checked = false;
		document.getElementById('monthlyFlagN').checked = false;
		document.getElementById('reportFormaty').checked = false;
		document.getElementById('reportFormatn').checked = false;		
	}
	if(type=='totalVacant') {
		document.getElementById('fromDate').value="";
		document.getElementById('reportFormaty').checked = false;
		document.getElementById('reportFormatn').checked = false;		
	}
	if(type=='hrdgAnnual') {
		document.getElementById('reportFormaty').checked = false;
		document.getElementById('reportFormatn').checked = false;		
	}
}
function resetDisabilities(){
	document.getElementById('reportFormaty').checked = false;
	document.getElementById('reportFormatn').checked = false;
	document.getElementById('pwd1').checked = false;
	document.getElementById('pwd2').checked = false;
}
function getCategoryWiseReport(type) {                   //This function has been modified for pisMiscReport
	var errorMessage = "";
	var status = true;
	
	/*if(document.getElementById('reportFormaty').checked == false &&
			document.getElementById('reportFormatn').checked == false){
		errorMessage = "Please Select Report Type\n";
		document.getElementById('reportFormaty').focus();
		status = false;
	}*/
	if(document.getElementById('category').value=='select'){
		errorMessage += "Please Select Category\n";
		if(status){
			document.getElementById('category').focus();
			status = false;
		}
	}
	if(status){
		/*if(document.getElementById('reportFormaty').checked == true){*/
			if(type == 'pdf'){
			window
			.open(
					"./report.htm?param=ireportEmpCategorywise&returnValue=report&category="
							+ document.forms[0].category.value,
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}/*else if(document.getElementById('reportFormatn').checked == true){*/
			else if(type == 'excel'){
			document.forms[0].param.value = "ireportEmpCategorywiseExcel";// &category="+document.forms[0].category.value;
			document.forms[0].action = "report.htm";
			document.forms[0].submit();
		}
	}else{
		alert(errorMessage);		
	}	
}

function getDesignationwieReport(type) {
	var errorMessage = "";
	var status = true;
	
/*	if(document.getElementById('reportFormaty').checked== false &&
	   document.getElementById('reportFormatn').checked== false	){
		errorMessage = "Please Select Report Type\n";
		document.getElementById('reportFormaty').focus();
		status = false;
	}*/
	if(document.getElementById('designation').value=='select'){
		errorMessage += "Please Select Designation\n";
		if(status){
			document.getElementById('designation').focus();
			status = false;
		}
	}
	if(status){
		if(type == 'pdf'){
			window
			.open(
					"./report.htm?param=ireportEmpDesignationwise&returnValue=report&designation="
							+ document.forms[0].designation.value,
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(type == 'excel'){
			document.forms[0].param.value = "ireportEmpDesignationwiseExcel";
			document.forms[0].action = "report.htm";
			document.forms[0].submit();
		}
	}else{
		alert(errorMessage);		
	}	
}
function getReValueDesig(){
	var errorMessage="";
	var status=true;
	if(status){
		if($jq("#txnDate").val()=='' || $jq("#txnDate").val()== undefined){
			status=false;
			errorMessage+="Please Select the Date";
		}
	}
	if(status){
	var requestDetails={
			"txnDate" : $jq("#txnDate").val(),
			"categoryID" : $jq("#categoryID").val(),
			"param" : "desigReValues"
			};
	$jq.ajax({
		type : "POST",
		url  : "reDetails.htm",
		data : requestDetails,
		cache: false,
		success : function(html){
		$jq("#Designations").html(html);
	}
	});
}else{
	alert(errorMessage);
}
}
function getDesignations(){
	var errorMessage="";
	var status=true;
	if(status){
	if($jq('#categoryID').val()==''){
		message+="Please Select Category \n";
			status=false;
		}
	}
	if(status){
		var requestDetails = {
			"categoryID" : $jq("#categoryID").val(),
			"txnDate" : $jq("#txnDate").val(),
			"param" : "desigReValues"
		};
		$jq.ajax( {
			type : "POST",
			url : "reDetails.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq("#Designations").html(html);
			}
		});
	}else{
		alert(errorMessage);
	}
}
function getDesignationReport(){
	var errorMessage = "";
	var status = true;
	
	if(document.getElementById('reportFormat').value=='select'){
		errorMessage = "Please Select Report Type\n";
		document.getElementById('reportFormaty').focus();
		status = false;
	}
	if(document.getElementById('designation').value=='select'){
		errorMessage += "Please Select Designation\n";
		if(status){
			document.getElementById('designation').focus();
			status = false;
		}
	}
	if(status){
		if(document.getElementById('reportFormat').value=='pdf'){
			window
			.open(
					"./report.htm?param=Designationwise&returnValue=report&designation="
							+ document.forms[0].designation.value,
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(document.getElementById('reportFormat').value=='excel'){
			document.forms[0].param.value = "DesignationExcel";
			document.forms[0].action = "report.htm";
			document.forms[0].submit();
		}
	}else{
		alert(errorMessage);		
	}	
}

function getEmpDOJWiseReport(type) {
	var errorMessage = "";
	var status = true;
	
	if(document.getElementById('fromDate').value==''){
		errorMessage +="Please Select From Date\n";
		if(status==true) {
			document.getElementById('fromDate').focus();
			status = false;
		}
	}
	if(document.getElementById('toDate').value==''){
		errorMessage +="Please Select To Date\n";
		if(status==true) {
			document.getElementById('toDate').focus();
			status = false;
		}
	}
/*	if(document.getElementById('reportFormaty').checked == false &&
			document.getElementById('reportFormatn').checked == false	){
		errorMessage +="Please Select Report Type\n";
		if(status==true) {
			document.getElementById('reportFormaty').focus();
			status = false;
		}
	}*/
	if(status){
		if(type=='pdf'){
			window
			.open(
					"./report.htm?param=ireportEmpDOJwsie&returnValue=report&fromDate="+document.getElementById('fromDate').value+"&toDate="+document.getElementById('toDate').value,
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(type=='excel'){
			document.forms[0].param.value = "ireportEmpDOJwsieExcel";
			document.forms[0].action = "report.htm";
			document.forms[0].submit();
		}
	}else{
		alert(errorMessage);		
	}	
}

function getGroupWiseReport(type) {
	var errorMessage = "";
	var status = true;
	
	/*if(document.getElementById('reportFormaty').checked == false &&
			document.getElementById('reportFormatn').checked == false	){
		errorMessage = "Please Select Report Type\n";
		document.getElementById('reportFormaty').focus();
		status = false;
	}*/
	if(document.getElementById('groupName').value=='select'){
		errorMessage += "Please Select Group Name\n";
		if(status){
			document.getElementById('groupName').focus();
			status = false;
		}
	}
	if(status){
		if(type == 'pdf'){
			window
			.open(
					"./report.htm?param=ireportEmpGroupwise&returnValue=report&groupName="
							+ document.forms[0].groupName.value,
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(type == 'excel' ){
			document.forms[0].param.value = "ireportEmpGroupwiseExcel";
			document.forms[0].action = "report.htm";
			document.forms[0].submit();
		}
	}else{
		alert(errorMessage);		
	}	
}

function getReligionWiseReport(type) {

	var errorMessage = "";
	var status = true;
	
	/*if(document.getElementById('reportFormaty').checked == false &&
		document.getElementById('reportFormatn').checked == false){
		errorMessage = "Please Select Report Type\n";
		document.getElementById('reportFormaty').focus();
		status = false;
	}*/
	if(document.getElementById('religionName').value=='select'){
		errorMessage += "Please Select Religion Name\n";
		if(status){
			document.getElementById('religionName').focus();
			status = false;
		}
	}
	if(status){
		if(type == 'pdf'){
			window
			.open(
					"./report.htm?param=ireportReligionwise&returnValue=report&religion="
							+ document.forms[0].religionName.value,
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(type == 'excel'){
			document.forms[0].param.value = "ireportReligionwiseExcel";
			document.forms[0].action = "report.htm";
			document.forms[0].submit();
		}
	}else{
		alert(errorMessage);		
	}
}

/*function getReservationWiseReport(type) {      //This function will be remove . this has modified with double parameters for PisReports link
	var errorMessage = "";
	var status = true;
	
	if(type=='monthly'){
		if(document.getElementById('monthlyFlagY').checked)
			type=document.getElementById('monthlyFlagY').value;
		else if(document.getElementById('monthlyFlagN').checked)
			type=document.getElementById('monthlyFlagN').value;
		else {
			errorMessage+= "Please Select Monthly Report Type\n";
			status = false;
		}
	}
	if(type=='totalVacant'){
		if(document.getElementById('fromDate').value==''){
			errorMessage +="Please Select  Date\n";
			if(status==true) {
				document.getElementById('fromDate').focus();
				status = false;
			}
		}
	}
	
	if(document.getElementById('reportFormaty').checked == false && document.getElementById('reportFormatn').checked == false ){
		errorMessage+= "Please Select Report Type\n";
		document.getElementById('reportFormaty').focus();
		status = false;
	}
	
	if(status){
		
		if(document.getElementById('reportFormaty').checked == true && type=='totalVacant' ){
			window
			.open(
					"./report.htm?param=ireportReservationwise&type="+type+"&returnValue=report&fromDate="+document.getElementById('fromDate').value+"",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		} 
		if(document.getElementById('reportFormaty').checked == true && (type=='monthly'||type=='catVacant'||type=='desigVacant'||type=='halfYearEnding'||type=='empDetails'||type=='annualYearEnding' ||type=='reservationReport' || type=='hrdgAnnual')){
			window
			.open(
					"./report.htm?param=ireportReservationwise&type="+type+"&returnValue=report",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}
		if(document.getElementById('reportFormatn').checked == true){
			document.forms[0].param.value = "ireportReservationwiseExcel";
			document.forms[0].type.value = type;
			document.forms[0].action = "report.htm";
			document.forms[0].submit();
		} 		
	}else{
		alert(errorMessage);		
	}	
}*/

function getCommunityWiseReport(type) {
	var errorMessage = "";
	var status = true;
	
	/*if(document.getElementById('reportFormaty').checked == false
			&& document.getElementById('reportFormatn').checked == false){
		errorMessage = "Please Select Report Type\n";
		document.getElementById('reportFormaty').focus();
		status = false;
	}*/
	if(document.getElementById('community').value=='select'){
		errorMessage += "Please Select Community Name\n";
		if(status){
			document.getElementById('community').focus();
			status = false;
		}
	}
	if(status){
		if(type == 'pdf'){
			window
			.open(
					"./report.htm?param=ireportCommunitywise&returnValue=report&community="+ document.forms[0].community.value,
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(type == 'excel'){
			document.forms[0].param.value = "ireportCommunitywiseExcel";
			document.forms[0].action = "report.htm";
			document.forms[0].submit();
		}
	}else{
		alert(errorMessage);		
	}	
}
function getPHEmpReport(type) {
	var errorMessage = "";
	var status = true;
	
/*	if(document.getElementById('reportFormaty').checked == false &&
			document.getElementById('reportFormatn').checked == false){
		errorMessage = "Please Select Report Type\n";
		document.getElementById('reportFormaty').focus();
		status = false;
	}*/
	if(status){
		if(type=="pdf"){
			window
			.open(
					"./report.htm?param=ireportPHEmp&returnValue=report",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(type=='excel'){
			document.forms[0].param.value = "ireportPHEmpExcel";
			document.forms[0].action = "report.htm";
			document.forms[0].submit();
		}
	}else{
		alert(errorMessage);		
	}	
}
/*function getDisabilitiesAnnualReport() {//This function will be remove . this has modified with double parameters for PisReports link
	var errorMessage = "";
	var status = true;
	
	if(document.getElementById('pwd1').checked == false && document.getElementById('pwd2').checked == false ){
		errorMessage +="Please Select PWD Report \n";
		if(status==true) {
			document.getElementById('pwd1').focus();
			status = false;
		}
	}
	if(document.getElementById('reportFormaty').checked == false && document.getElementById('reportFormatn').checked == false ){
		errorMessage +="Please Select  Report Type \n";
		if(status==true) {
			document.getElementById('reportFormaty').focus();
			status = false;
		}
	}

	if(status){
		if(document.getElementById('pwd1').checked == true && document.getElementById('reportFormaty').checked == true){
			window
			.open(
					"./report.htm?param=HalfYearAllPhReport",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(document.getElementById('pwd2').checked == true && document.getElementById('reportFormaty').checked == true){

			window
			.open(
					"./report.htm?param=phVacantReport",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		
		}else if(document.getElementById('pwd1').checked == true &&document.getElementById('reportFormatn').checked == true){
			document.forms[0].type.value = type;
			document.forms[0].param.value = "HalfYearAllPhExcelReport";
			document.forms[0].action = "report.htm";
			document.forms[0].submit();
		}else if(document.getElementById('pwd2').checked == true &&document.getElementById('reportFormatn').checked == true){
			document.forms[0].type.value = type;
			document.forms[0].param.value = "phVacantExcelReport";
			document.forms[0].action = "report.htm";
			document.forms[0].submit();
		}
	}else{
		alert(errorMessage);		
	}	
}*/
/*function getEmpLastModifiedReport(type) { //This function will be remove . this has modified with double parameters for PisReports link
	var errorMessage = "";
	var status = true;
	
	if(document.getElementById('fromDate').value==''){
		errorMessage +="Please Select From Date\n";
		if(status==true) {
			document.getElementById('fromDate').focus();
			status = false;
		}
	}
	if(document.getElementById('toDate').value==''){
		errorMessage +="Please Select To Date\n";
		if(status==true) {
			document.getElementById('toDate').focus();
			status = false;
		}
	}
	if(document.getElementById('reportFormaty').checked == false && document.getElementById('reportFormatn').checked == false ){
		errorMessage +="Please Select Report Type\n";
		if(status==true) {
			document.getElementById('reportFormaty').focus();
			status = false;
		}
	}
	if(status){
		if(document.getElementById('reportFormaty').checked == true){
			window
			.open(
					"./report.htm?param=ireportEmpLastModified&type="+type+"&returnValue=report&fromDate="+document.getElementById('fromDate').value+"&toDate="+document.getElementById('toDate').value,
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(document.getElementById('reportFormatn').checked == true){
			document.forms[0].type.value = type;
			document.forms[0].param.value = "ireportEmpLastModifiedExcel";
			document.forms[0].action = "report.htm";
			document.forms[0].submit();
		}
	}else{
		alert(errorMessage);		
	}	
}*/
function DateText(id) {
	document.getElementById(id).value="";

}
function getHierarchyReport(id,type,format) {
	var errorMessage = "";
	var status = true;
	
	if(document.getElementById(id).value=='select'){
		errorMessage += "Please Select Role Instance Name\n";
		if(status){
			document.getElementById('hierarchy').focus();
			status = false;
		}
	}
/*	if(document.getElementById('reportFormaty').checked == false &&
			document.getElementById('reportFormatn').checked == false	){
		errorMessage += "Please Select Report Type";
		document.getElementById('reportFormaty').focus();
		status = false;
	}*/

	if(status){
		document.forms[0].type.value=type;
		if(format== "pdf"){
			window
			.open(
					"./report.htm?param=empHierarchyPdf&returnValue=report&roleInstanceId="+ $jq('#'+id).val()+"&type="+type,
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(format=="excel"){
			document.forms[0].param.value = "empHierarchyExcel";
			document.forms[0].action = "report.htm";
			document.forms[0].submit();
		}
	}else{
		alert(errorMessage);		
	}	

	
}
function lables(type) {
	if (type == 'reservationReport') {
		document.title = "Reservation Wise Report";
		document.getElementById('headTitle').innerHTML = "Reservation Wise Report";
	}else if (type == 'catVacant') {
		document.title = "Category Vacant Report";
		document.getElementById('headTitle').innerHTML = "Category Vacant Report";
	}else if (type == 'desigVacant') {
		document.title = "Designation Vacant Report";
		document.getElementById('headTitle').innerHTML = "Designation Vacant Report";
	}else if (type == 'phVacant') {
		document.title = "Physical Handicaped Vacant Report";
		document.getElementById('headTitle').innerHTML = "Physical Handicaped Vacant Report";
	}else if (type == 'empDetails') {
		document.title = "Annual Nominal Roll Report";
		document.getElementById('headTitle').innerHTML = "Annual Nominal Roll Report";
	}else if (type == 'halfYearEnding') {
		document.title = "Minority Report";
		document.getElementById('headTitle').innerHTML = "Minority Report";
	}else if (type == 'totalVacant') {
		document.title = "Quarterly Strength Return";
		document.getElementById('headTitle').innerHTML = "Quarterly Strength Return";
	}else if (type == 'annualYearEnding') {
		document.title = "Minority welfare Report";
		document.getElementById('headTitle').innerHTML = "Minority welfare Report";
	}else if (type == 'lastModified') {
		document.title = "Last Modified Employee Wise  Report";
		document.getElementById('headTitle').innerHTML = "Last Modified Employee Wise  Report";
	}else if (type == 'scStObc') {
		document.title = "SC/ST/OBC Report";
		document.getElementById('headTitle').innerHTML = "SC/ST/OBC Report";
	}else if (type == 'HalfYearPhReport') {
		document.title = "Half Yearly Report For Physcically Handicapped";
		document.getElementById('headTitle').innerHTML = "Half Yearly Report For Physcically Handicapped";
	}else if (type == 'HalfYearAllPhReport') {
		document.title = "Annual Disabilities Report";
		document.getElementById('headTitle').innerHTML = "Annual Disabilities Report";
	}else if (type == 'CashPurchaseVoucher') {
		document.title = "Cash Purchase Voucher Report";
		document.getElementById('headTitle').innerHTML = "Cash Purchase Voucher Report";
	}else if (type == 'ledgerReport') {
		document.title = "Ledger Report";
		document.getElementById('headTitle').innerHTML = "Ledger Report";
	}else if (type == 'CashPurchaseDemand') {
		document.title = "Cash Purchase Demand Report";
		document.getElementById('headTitle').innerHTML = "Cash Purchase Demand Report";
	}else if (type == 'monthly') {
		document.title = "Manpower vacancy Report";
		document.getElementById('headTitle').innerHTML = "Manpower vacancy Report";
	}else if (type == 'DOJDRDO') {
		document.title = "Date of Joining in DRDO Report";
		document.getElementById('headTitle').innerHTML = "Date of Joining in DRDO Report";
	}else if (type == 'hrdgAnnual') {
		document.title = "Annual HRDG Report";
		document.getElementById('headTitle').innerHTML = "Annual HRDG Report";
	}
}

function getInventoryReport() {
	var errorMessage = "";
	var status = true;
	var inventoryNo='';
	inventoryNo=document.getElementById('inventoryNo').options[document.getElementById('inventoryNo').selectedIndex].text;
	if(document.getElementById('reportFormat').value=='select'){
		errorMessage = "Please Select Report Type\n";
		document.getElementById('reportFormat').focus();
		status = false;
	}
	if(document.getElementById('inventoryNo').value=='select'){
		errorMessage += "Please Select Category\n";
		if(status){
			document.getElementById('inventoryNo').focus();
			status = false;
		}
	}
	if(status){
		if(document.getElementById('reportFormat').value=='pdf'){
			window
			.open(
					"./report.htm?param=ireportInventorywise&returnValue=report&inventoryNo="
							+inventoryNo ,
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(document.getElementById('reportFormat').value=='excel'){
			document.forms[0].param.value = "ireportInventoryExcel";// &category="+document.forms[0].category.value;
			document.forms[0].action = "report.htm";
			document.forms[0].submit();
		}
	}else{
		alert(errorMessage);		
	}	
}
function displayFromToFields(field){
	if(field=='dob'){
		if(document.getElementById("searchWith3").value=="between"){
			document.getElementById("dobdiv").style.display="none";
			document.getElementById("dobfromdiv").style.display="block";
			document.getElementById("dobtodiv").style.display="block";
			document.getElementById("doband").style.display="block";
			document.getElementById("dob").value='';
		}else
		{
			document.getElementById("dobdiv").style.display="block";
			document.getElementById("dobfromdiv").style.display="none";
			document.getElementById("dobtodiv").style.display="none";
			document.getElementById("doband").style.display="none";
			document.getElementById("dobfrom").value='';
			document.getElementById("dobto").value='';
		}
	}else if(field=='dojasl'){
		if(document.getElementById("searchWith4").value=="between"){
			document.getElementById("dojasldiv").style.display="none";
			document.getElementById("dojaslfromdiv").style.display="block";
			document.getElementById("dojasltodiv").style.display="block";
			document.getElementById("dojasland").style.display="block";
			document.getElementById("dojasl").value='';
		}else{
			document.getElementById("dojasldiv").style.display="block";
			document.getElementById("dojaslfromdiv").style.display="none";
			document.getElementById("dojasltodiv").style.display="none";
			document.getElementById("dojasland").style.display="none";
			document.getElementById("dojaslfrom").value='';
			document.getElementById("dojaslto").value='';
		}			
	}
}
function generateCustomReport()
{
	var fields="";
	var queryjson={};
	var n=0;
	$jq('#list_2 li').each(function() {
		if(n==0)
			 fields=$jq(this).text();
		  else
			  fields+=","+$jq(this).text();
		n=n+1;
	  });
	queryjson["fields"]=fields;
	var len=document.forms[0].sfidcheck.length;
	var n=0;
	for(var i=0;i<len;i++){
		var rowjson={};
		if($jq("#sfidcheck"+i).is(":checked")){
			if(i==0){
				rowjson["fieldname"]="sfid";
				rowjson["order"]=$jq('input:radio[name=sfidOrder]:checked').val();
				rowjson["value"]=$jq("#sfid").val();
			}else if(i==1){
				rowjson["fieldname"]="name";
				rowjson["order"]=$jq('input:radio[name=nameOrder]:checked').val();
				rowjson["value"]=$jq("#name").val();
			}else if(i==2){
				rowjson["fieldname"]="desig";
				rowjson["order"]=$jq('input:radio[name=desigOrder]:checked').val();
				rowjson["value"]=$jq("#designation").val();
			}else if(i==3){
				rowjson["fieldname"]="dob";
				rowjson["order"]=$jq('input:radio[name=dobOrder]:checked').val();
				rowjson["from"]=$jq("#dobfrom").val();
				rowjson["to"]=$jq("#dobto").val();
				rowjson["value"]=$jq("#dob").val();
			}else if(i==4){
				rowjson["fieldname"]="dojasl";
				rowjson["order"]=$jq('input:radio[name=dojaslOrder]:checked').val();
				rowjson["from"]=$jq("#dojaslfrom").val();
				rowjson["to"]=$jq("#dojaslto").val();
				rowjson["value"]=$jq("#dojasl").val();
			}else if(i==5){
				rowjson["fieldname"]="groupName";
				rowjson["order"]=$jq('input:radio[name=groupOrder]:checked').val();
				rowjson["value"]=$jq("#groupName").val();
			}else if(i==6){
				rowjson["fieldname"]="category";
				rowjson["order"]=$jq('input:radio[name=categoryOrder]:checked').val();
				rowjson["value"]=$jq("#category").val();
			}else if(i==7){
				rowjson["fieldname"]="ph";
				rowjson["order"]=$jq('input:radio[name=phOrder]:checked').val();
				rowjson["value"]=$jq("#ph").val();
			}else if(i==8){
				rowjson["fieldname"]="religion";
				rowjson["order"]=$jq('input:radio[name=religionOrder]:checked').val();
				rowjson["value"]=$jq("#religion").val();
			}else if(i==9){
				rowjson["fieldname"]="qualification";
				rowjson["order"]=$jq('input:radio[name=qualificationOrder]:checked').val();
				rowjson["value"]=$jq("#qualification").val();
			}else if(i==10){
				rowjson["fieldname"]="cghs";
				rowjson["order"]=$jq('input:radio[name=cghsOrder]:checked').val();
				rowjson["value"]=$jq("#cghs").val();
			}else if(i==11){
				rowjson["fieldname"]="quarter";
				rowjson["order"]=$jq('input:radio[name=quarterOrder]:checked').val();
				rowjson["value"]=$jq("#quarter").val();
			}
			rowjson["search"]=$jq("#searchWith"+i+" option:selected").val();
			queryjson[n]=rowjson;
			n++;
		}	
	}
	document.forms[0].jsonValue.value=JSON.stringify(queryjson);
	document.forms[0].param.value="customreportpdf";
	$jq.post(
			'report.htm',
			$jq('#customReport').serialize(), 
			function(data){ 
					$jq('').html(data);
					window.open('./report.htm?param=customreportpdf&type=showpdf','CustomReport','fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
				} 
		    );	
}
function saveCpoolDetails(){
	var values = "";
	var status=true;
	var message="";
	if($jq('#txnDate').val()==''){
		message+="Select Date \n"
			status=false;
		}
	for (var i=0; i<designationList.length; i++) {
		 var id=designationList[i].id;
		 if($jq("#"+id+"").val()!= "" && $jq("#"+id+"a").val()!= "")
		   {
			values+=id+"-"+$jq("#"+id+"").val()+"-"+$jq("#"+id+"a").val()+"#";
			
		    }
		}
	status=onlyDigits(values);
	if(!status== true){
	message+="Plz enter digits only \n"
	}
	var json={};
	if(values != null && values != ""){
		json["reValues"]=values.split("#");
		
	}else{
		message+="Please Enter CPool Details\n";
		status=false;
	}
	
	if(status){
    $jq('#jsonValue').val(JSON.stringify(json));
	$jq('#param').val('manage');
	// alert($jq('#param').val());
	$jq.post('reportDetails.htm?designationId='+values, $jq('#reportDetails').serialize(),
			function(html) {		      
				$jq('#result').html(html);
				clearCpoolDetails();
						        

			});
	}
	else{
	alert(message);
	clearReDetails();
	}
  	
}

function saveReDetails(){
	var values = "";
	var status=true;
	var message="";
	if(status){
	if($jq('#txnDate').val()==''){
		message+="Please Select Date \n";
			status=false;
		}
	if($jq('#categoryID').val()==''){
		message+="Please Select Category \n";
			status=false;
		}
	for (var i=0; i<designationList.length; i++) {
		 var id=designationList[i].id;
		 if($jq("#"+id).val()!= "" && $jq("#"+id).val()!=undefined)
		   {
			values+=id+"-"+$jq("#"+id).val()+"#";
			
		    }
		}
	if(values=='') {
		message+="Please enter values \n";
		status=false;
	}
	}
	if(status){
		var requestDetails = {
				"categoryID" : $jq("#categoryID").val(),
				"param" : "manage",
				"designationId" : values,
				"txnDate" :$jq('#txnDate').val(),
				"categoryID" : $jq('#categoryID').val()
			};
			$jq.ajax( {
				type : "POST",
				url : "reDetails.htm",
				data : requestDetails,
				cache : false,
				success : function(html) {
				$jq('#result').html(html);
				clearReDetails();
				}
			});
	}else{
		alert(message);
	}
	
 	
}
function clearCpoolDetails(){
	document.getElementById('txnDate').value = "";
	for (var i=0; i<designationList.length; i++) {
		 var id=designationList[i].id;
		 if($jq("#"+id+"").val()!= "" && $jq("#"+id+"a").val()!= "")
		 {
			 $jq("#"+id+"").val('');
			 $jq("#"+id+"a").val('');
		 }
	} 	
}
function clearReDetails(){
	document.getElementById('txnDate').value = "";
	//document.getElementById('categoryID').value = "";
	for (var i=0; i<designationList.length; i++) {
		 var id=designationList[i].id;
		 if($jq("#"+id+"").val()!= "")
		 {
			 $jq("#"+id+"").val('');
		 }
	} 	
}
function onlyDigits(value){
	var text=value;
	var status=true;
	for (var i = 0; i < text.length; i++) {
		var c=text.charAt(i);
		if(c >= 0 && c<= 9 || c == "-" || c == "#"){
			
		}
		else{
		status=false;
		}
	}
	return status;
}
function getAreaofDeploymentReport(){
	var errorMessage = "";
	var status = true;
	
	if(document.getElementById('reportFormaty').checked == false &&
			document.getElementById('reportFormatn').checked == false){
		errorMessage = "Please Select Report Type\n";
		document.getElementById('reportFormaty').focus();
		status = false;
	}
	if(status){
		if(document.getElementById('reportFormaty').checked == true){
			window
			.open(
					"./report.htm?param=ireportareaofDeployment&returnValue=report",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(document.getElementById('reportFormatn').checked == true){
			document.forms[0].param.value = "ireportareaofDeploymentExcel";
			document.forms[0].action = "report.htm";
			document.forms[0].submit();
		}
	}else{
		alert(errorMessage);		
	}	
}
function getNominalRoleReport(){
	var errorMessage = "";
	var status = true;
	
	if(document.getElementById('reportFormaty').checked == false &&
			document.getElementById('reportFormatn').checked == false){
		errorMessage = "Please Select Report Type\n";
		document.getElementById('reportFormaty').focus();
		status = false;
	}
	if(status){
		if(document.getElementById('reportFormaty').checked == true){
			window
			.open(
					"./report.htm?param=ireportnominalRolePdf&returnValue=report",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(document.getElementById('reportFormatn').checked == true){
			document.forms[0].param.value = "ireportnominalRoleExcel";
			document.forms[0].action = "report.htm";
			document.forms[0].submit();
		}
	}else{
		alert(errorMessage);		
	}	
}
	function getOrganizationReport() {
		var errorMessage = "";
		var status = true;
		
		if(document.getElementById('organization').value=='select'){
			errorMessage += "Please Select Report type\n";
			if(status){
				document.getElementById('organization').focus();
				status = false;
			}
		}
	
		if(document.getElementById('organization').value!='select'){
				window
				.open(
						"./report.htm?param=ireportOrganizationwise&returnValue=report&type="
								+ document.forms[0].organization.value,
						'preview',
						'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
			}
		
		else{
			alert(errorMessage);		
		}	
	}

	function clearOrganizationReport() {
		$jq("#organization").val('select');
	}
	function getEmpMapping(format) {
		var errorMessage = "";
		var status = true;
		
		if(document.getElementById('hierarchyMaping').value=='select'){
			errorMessage += "Please Select Role Instance Name\n";
			if(status){
				document.getElementById('hierarchyMaping').focus();
				status = false;
			}
		}
	/*	if(document.getElementById('reportFormaty').checked == false &&
				document.getElementById('reportFormatn').checked == false	){
			errorMessage += "Please Select Report Type\n";
			document.getElementById('reportFormaty').focus();
			status = false;
		}*/
		if(status){
			/*document.forms[0].type.value=type;*/
			if(format == 'pdf'){
				window
				.open(
						"./report.htm?param=empMappingPdf&returnValue=report&roleInstanceId="+ document.forms[0].hierarchyMaping.value+"&type="+type,
						'preview',
						'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
			}else if(format == 'excel'){
				document.forms[0].param.value = "empMappingExcel";
				document.forms[0].action = "report.htm";
				document.forms[0].submit();
			}
		}else{
			alert(errorMessage);		
		}	

		
	}
//start schedule reports
	function paySlip() {
		
		if ($jq('#reportType').val()=="CONVEYANCE") {
			$jq('#subType').show();
	    }else if($jq('#reportType').val()=='paySlip'){
	    	$jq('#subType').hide();
	    	$jq('#month').hide();
	    	$jq('#categoryName').hide();
	    	$jq('#subType2').show();
	    }
		else {
			$jq('#subType').hide();
			$jq('#subType2').hide();
		}
		if ($jq('#reportType').val()=="catList" || $jq('#reportType').val()=='paySlip') {
			if($jq('#reportType').val()=="catList"){
				$jq('#categoryName').show();
			}
			$jq('#month').hide();
	    }else {
			$jq('#month').show();
			$jq('#categoryName').show();
		}
	}
function getScheduleReport()
{
	var errorMessage = "";
	var status = true;
	var type = "";
	if ($jq('#reportType').val() == 'select') {
		errorMessage += "Please Select Report Type\n";
		status = false;
	}
	if ($jq('#schedulemonth').val() == "" && $jq('#reportType').val() != 'catList' && $jq('#reportType').val() != 'paySlip') {
		errorMessage += "Please Select Month and Year\n";
		status = false;
		$jq('#schedulemonth').focus();
	}
	if($jq('#reportType').val() != 'paySlip'){
		if ($jq('#categoryID').val() == "select") {
			errorMessage += "Please Select Category Name\n";
			status = false;
			$jq('#categoryID').focus();
		}
		if ($jq('#reportType').val() == "CONVEYANCE") {
			if($jq('#reportSubType').val() == 'select'){
				errorMessage += "Please select Sub Report Type\n";
				status = false;
			}else{
			type = $jq('#reportSubType').val();
			}
		}else{
			type = $jq('#reportType').val();
		}
	}
	if($jq('#reportType').val()=='paySlip'){
		if(status)
			printMyPaySlipSchRep();
		else
			alert(errorMessage);
		status=false;
	}
	if (status) {
		window
		.open(
				"./scheduleReports.htm?param=ireportShedulereports&returnValue=report&reportType="
						+type+"&categoryID="+document.getElementById('categoryID').value
						+"&schedulemonth="+$jq('#schedulemonth').val(),
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	}
	else {
		if(errorMessage!='')
			alert(errorMessage);
	}
}
	function clearScheduleReport()
	{
		$jq('#categoryID').val('0');
		$jq('#schedulemonth').val('');
		$jq('#categoryName').show();
		$jq('#subType').hide();
		$jq('#reportType').val('select');
		$jq('#reportSubType').val('select');
	}
	function printMyPaySlip() {
		
		window
		.open(
				"./scheduleReports.htm?param=ireportShedulereports&returnValue=report&reportType=PAYSLIP",
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	}
    function printMyPaySlipSchRep() {
		var paySlipYear=$jq('#paySlipMonth').val()+"-"+$jq('#paySlipYear').val();
		window
		.open(
				'./scheduleReports.htm?param=ireportShedulereports&returnValue=report&paySlipYear='+paySlipYear+'&reportType=PAYSLIP',
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	}
	/*function getMyITSlip() {
		var errorMessage = "";
		var status = true;
		
		if(document.getElementById('selectedFYear').value=='select'){
			errorMessage += "Please Select The Fianncial Year\n";
			if(status){
				document.getElementById('selectedFYear').focus();
				status = false;
			}
		}
		if(status){
				window
				.open(
						"./scheduleReports.htm?param=ireportShedulereports&reportType=incomeTaxSlip&returnValue=report&slipType="+$jq('input:radio[name=runTypeFlag]:checked').val()+"&financialYearId="+ $jq('#selectedFYear').val()+"&type="+type,
						'preview',
						'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
			
		}else{
			alert(errorMessage);		
		}	
		clearITPrintDetails();
	}*/
    function getMyITSlip(sfid) {
		var errorMessage = "";
		var status = true;
		var reportTypeValue = "";
		if($jq('#reportSfid').is(':visible')){
			if(document.getElementById('searchSfid').value==''){
				errorMessage += "Please Enter The Sfid \n";
				if(status){
					document.getElementById('searchSfid').focus();
					status=false;
				}
			}
		}
		if(document.getElementById('selectedFYear').value=='select'){
			errorMessage += "Please Select The Fianncial Year\n";
			if(status){
				document.getElementById('selectedFYear').focus();
				status = false;
			}
		}
		reportTypeValue=$jq('input:radio[name=reportTypeFlag]:checked').val();
		if(status){
			if(reportTypeValue==1){
				window
				.open(
						"./scheduleReports.htm?param=ireportShedulereports&reportType=incomeTaxSlip&returnValue=report&slipType="+$jq('input:radio[name=runTypeFlag]:checked').val()+"&financialYearId="+ $jq('#selectedFYear').val()+"&type="+type,
						'preview',
						'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
			}else if(reportTypeValue==2){
				window
				.open(
				"./scheduleReports.htm?param=incomeTaxForm16Report&reportType=incomeTaxForm16&returnValue=report&sfid="+sfid,
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
			}
		}else{
			alert(errorMessage);		
		}	
		//clearITPrintDetails();
	}
    function getMyITSlipFinance() {
		var errorMessage = "";
		var status = true;
		var reportTypeValue = "";
		if($jq('#reportSfid').is(':visible')){
			if(document.getElementById('searchSfid').value==''){
				errorMessage += "Please Enter The Sfid \n";
				if(status){
					document.getElementById('searchSfid').focus();
					status=false;
				}
			}
		}
		if(document.getElementById('selectedFYear').value=='select'){
			errorMessage += "Please Select The Fianncial Year\n";
			if(status){
				document.getElementById('selectedFYear').focus();
				status = false;
			}
		}
		reportTypeValue=$jq('input:radio[name=reportTypeFlag]:checked').val();
		if(status){
			if(reportTypeValue==1){
				window
				.open(
						"./scheduleReports.htm?param=ireportShedulereports&reportType=incomeTaxSlipFinance&returnValue=report&slipType="+$jq('input:radio[name=runTypeFlag]:checked').val()+"&financialYearId="+ $jq('#selectedFYear').val()+"&sfid="+$jq('#searchSfid').val(),
						'preview',
						'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
			}else if(reportTypeValue==2){
				window
				.open(
				"./scheduleReports.htm?param=incomeTaxForm16Report&reportType=incomeTaxForm16Finance&returnValue=report&sfid="+$jq('#searchSfid').val(),
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
			}
		}else{
			alert(errorMessage);		
		}	
		//clearITPrintDetails();
	}
	function printDAArrearsDetails(fromDate,toDate,daTypeFlag,id){
		
		var errorMessage = "";
		var status = true;
		alert($jq('#id').val());
		/*if($jq('#categoryId').val()=='select'){
			errorMessage += "Please Select Category Name\n";
			status=false;
		}*/
		if(status){
			window
			.open(
					"./scheduleReports.htm?param=ireportShedulereports&reportType=daArrearsDetails&returnValue=report&fromDate="+fromDate+"&toDate="+ toDate+"&categoryID="+$jq('#categoryId').val()+"&daTypeFlag="+daTypeFlag+"&type="+type,
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		
				
		}else{
			alert(errorMessage);		
		}	
		$jq('#categoryId').val('select');
	}
	function clearITPrintDetails(){
		$jq('#selectedFYear').val('');
		$jq("input:radio").attr("checked", false); 
	}
//end schedule reports
	/*-----------------pay Master Reports::START----------------------------*/
	
	
	function printCategoryDetails(){
		window
		.open(
				"./scheduleReports.htm?param=ireportShedulereports&reportType=printCategoryDetails&returnValue=report",
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	
	}
	function printPayBillAllwTypeDetails(){
		window
		.open(
				"./scheduleReports.htm?param=ireportShedulereports&reportType=printPayBillAllwTypeDetails&returnValue=report",
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	
		}
	function printPayBillAllwConfDetails(){
		window
		.open(
				"./scheduleReports.htm?param=ireportShedulereports&reportType=printPayBillAllwConfDetails&returnValue=report",
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	
		}
	function printPayBandDetails(){
		window
		.open(
				"./scheduleReports.htm?param=ireportShedulereports&reportType=printPayBandDetails&returnValue=report",
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	
		}
	function printPayscaleDesignation(){
		window
		.open(
				"./scheduleReports.htm?param=ireportShedulereports&reportType=printPayscaleDesignation&returnValue=report",
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	
		}
	function printPaybillCghsMaster(){
		window
		.open(
				"./scheduleReports.htm?param=ireportShedulereports&reportType=printPaybillCghsMaster&returnValue=report",
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	
	}
	function printCgeisMasterDetails(){
		window
		.open(
				"./scheduleReports.htm?param=ireportShedulereports&reportType=printCgeisMasterDetails&returnValue=report",
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	
	}
	function printPayProfTaxDetails(){
		window
		.open(
				"./scheduleReports.htm?param=ireportShedulereports&reportType=printPayProfTaxDetails&returnValue=report",
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	
	}
	function printPayScale(masterType) {
		window
		.open(
				"./scheduleReports.htm?param=ireportShedulereports&reportType="+masterType+"&returnValue=report",
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	
	}
	function printRSMasterDetails() {
		window
		.open(
				"./scheduleReports.htm?param=ireportShedulereports&reportType=printRSMasterDetails&returnValue=report",
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	
	}
	function  printQuarterTypeDetails(){
		window
		.open(
				"./scheduleReports.htm?param=ireportShedulereports&reportType=printQuarterTypeDetails&returnValue=report",
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	
	
	}
	function  printDAMasterDetails(){
		window
		.open(
				"./scheduleReports.htm?param=ireportShedulereports&reportType=printDAMasterDetails&returnValue=report",
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	
	
	}
	
	function printLicenceFeeDetails(){
		window
		.open(
				"./scheduleReports.htm?param=ireportShedulereports&reportType=printLicenceFeeDetails&returnValue=report",
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	
	
	}
/*-----------------pay Master Reports::END----------------------------*/
	function getFinancePaySlip()
	{
		var errorMessage = "";
		var status = true;
		var type = "";
		if ($jq('#sfid').val() == "") 
		{
			errorMessage += "Please Enter SFID\n";
			status = false;
			$jq('#sfid').focus();
		}
		if ($jq('#value2').val() == "") 
		{
			errorMessage += "Please Select Pay Slip Month\n";
			if(status)
			{
				status = false;
				$jq('#value2').focus();
			}
		}
		
		if (status) 
		{
			window.open("./scheduleReports.htm?param=getFinancePaySlip&returnValue=report&reportType=reportType&value2="+$jq('#value2').val()+"&sfid="+$jq('#sfid').val(),
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}
		else 
		{
			alert(errorMessage);
		}
	}
	function clearFinancePaySlipDetails(){
		$jq('#sfid').val('');
		$jq('#value2').val('');
	}
	function printAnnualIncrArrearsDetails(){
		window
		.open(
				"./report.htm?param=printAnnualIncrArrearsDetails&returnValue=report&parameter1="+$jq('#adminAccDate').val()+"&parameter2="+$jq('#financeAccDate').val(),
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	
	}
	function printDAArrearsDetails(){
		window
		.open(
				"./report.htm?param=printDAArrearsDetails&returnValue=report&parameter1="+$jq('#adminAccDate').val()+"&parameter2="+$jq('#financeAccDate').val(),
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	
	}
	function printPromotionArrearsDetails(){
		window
		.open(
				"./report.htm?param=printPromotionArrearsDetails&returnValue=report",
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	
	}
	//data validation reports
	function printDataValidationReport(type,requestType){
		if(type=='address'){
			window
			.open(
					"./validateReport.htm?param=printDataValidationReports&reportType=addressDetails&returnValue=report",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(type=='presentaddress'){
			window
			.open(
					"./validateReport.htm?param=printDataValidationReports&reportType=presentaddress&returnValue=report",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}
		else if(type=='nullMotherTongue'){
			window
			.open(
					"./validateReport.htm?param=printDataValidationReports&reportType=nullMotherTongueDetails&returnValue=report",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(type=='empMotherTongue'){
			window
			.open(
					"./validateReport.htm?param=printDataValidationReports&reportType=empMotherTongueDetails&returnValue=report",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(type=='phyHandicapped'){
			window
			.open(
					"./validateReport.htm?param=printDataValidationReports&reportType=phyHandicappedDetails&returnValue=report",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(type=='exserviceMan'){
			window
			.open(
					"./validateReport.htm?param=printDataValidationReports&reportType=exserviceManDetails&returnValue=report",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(type=='leaveVerification'){                                                                                        //leaveData validation
			window
			.open(
					"./validateReport.htm?param=printDataValidationReports&reportType=leaveVerificationDetails&returnValue=report",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(type=='nominalRole'){
			window
			.open(
					"./validateReport.htm?param=printDataValidationReports&reportType=nominalRoleDetails&returnValue=report",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(type=='leaveManualEntry'){
			window
			.open(
					"./validateReport.htm?param=printDataValidationReports&reportType=leaveManualEntryDetails&returnValue=report",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(type=='hindiIncentive'){
			window
			.open(
					"./validateReport.htm?param=printDataValidationReports&reportType=hindiIncentiveDetails&returnValue=report",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(type=='loanDetails'){
			window
			.open(
					"./validateReport.htm?param=printDataValidationReports&reportType=loanDetails&returnValue=report",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(type=='nearestRailway'){
			window
			.open(
					"./validateReport.htm?param=printDataValidationReports&reportType=nearestRailwayDetails&returnValue=report",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(type=='missingPay'){
			window
			.open(
					"./validateReport.htm?param=printDataValidationReports&reportType=missingPayDetails&returnValue=report",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(type=='missingBasicPay'){
			window
			.open(
					"./validateReport.htm?param=printDataValidationReports&reportType=missingBasicPayDetails&returnValue=report",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(type=='missingGradePay'){
			window
			.open(
					"./validateReport.htm?param=printDataValidationReports&reportType=missingGradePayDetails&returnValue=report",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(type=='payEntry'){
			window
			.open(
					"./validateReport.htm?param=printDataValidationReports&reportType=payEntryDetails&returnValue=report",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(type=='empQuarterDetails'){
			window
			.open(
					"./validateReport.htm?param=printDataValidationReports&reportType=empQuarterDetails&returnValue=report",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(type=='empTaskHolder'){
			window
			.open(
					"./validateReport.htm?param=printDataValidationReports&reportType=empTaskHolderDetails&returnValue=report",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(type=='auditEmpAddress'){
			window
			.open(
					"./validateReport.htm?param=printDataValidationReports&reportType=auditEmpAddressDetails&returnValue=report",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(type=='taskHolderDesig'){
			window
			.open(
					"./validateReport.htm?param=printDataValidationReports&reportType=taskHolderDesigDetails&returnValue=report",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(type=='leaveBalanceValidation'){
			window
			.open(
					"./validateReport.htm?param=printDataValidationReports&reportType=leaveBalanceValidation&returnValue=report",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(type=='empQualification'){
			window
			.open(
					"./validateReport.htm?param=printDataValidationReports&reportType=empQualificationDetails&returnValue=report&requestType="+requestType,
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(type=='empTeleNo'){
			window
			.open(
					"./validateReport.htm?param=printDataValidationReports&reportType=empTeleNoDetails&returnValue=report",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}else if(type =='empPermanentAddr'){
			window
			.open(
					"./validateReport.htm?param=printDataValidationReports&reportType=empPermanentAddrDetails&returnValue=report",
					'preview',
					'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
		}
		
	}
	function viewSubReports(param){                                 //new functionality
	if(param=='monthlyGrid'){
		$jq('#monthlyGrid').toggle();
			$jq('#reportType').toggle();
			if($jq('#treeModel').attr('class')=='collapsed'){
				$jq('#treeModel').attr('class','expanded')
			}
			
			else{
				$jq('#treeModel').attr('class','collapsed')
			}
			 document.getElementById('monthlyFlagY').checked = false;
			 document.getElementById('monthlyFlagN').checked = false; 
			 
			$jq('#quarterlyGrid').css("display","none");
			$jq('#treeModel2').attr('class','collapsed');
			$jq('#halfYearlyGrid').css("display","none");
			$jq('#treeModel3').attr('class','collapsed');
			$jq('#anualGrid').css("display","none");
			$jq('#treeModel4').attr('class','collapsed');
		}else if(param=='quarterlyGrid'){
			$jq('#monthlyGrid').css("display","none");
			$jq('#treeModel').attr('class','collapsed')
			$jq('#quarterlyGrid').toggle();
			if($jq('#treeModel2').attr('class')=='collapsed'){
				$jq('#treeModel2').attr('class','expanded');
			}
			
			else{
				$jq('#treeModel2').attr('class','collapsed');
			}
			 document.getElementById('fromDate').value="";
			 $jq('fromDate').val('');
			$jq('#halfYearlyGrid').css("display","none");
			$jq('#treeModel3').attr('class','collapsed');
			$jq('#anualGrid').css("display","none");
			$jq('#treeModel4').attr('class','collapsed');
		}else if(param=='halfYearlyGrid'){
			$jq('#monthlyGrid').css("display","none");
			$jq('#treeModel').attr('class','collapsed');
			$jq('#quarterlyGrid').css("display","none");
			$jq('#treeModel2').attr('class','collapsed');
			$jq('#halfYearlyGrid').toggle();
			if($jq('#treeModel3').attr('class')=='collapsed'){
				$jq('#treeModel3').attr('class','expanded');
			}
			
			else{
				$jq('#treeModel3').attr('class','collapsed');
			}
			document.getElementById('fromDate2').value="";
			document.getElementById('toDate').value="";
			$jq('#anualGrid').css("display","none");
			$jq('#treeModel4').attr('class','collapsed');
		}else if(param=='anualGrid'){
			$jq('#monthlyGrid').css("display","none");
			$jq('#treeModel').attr('class','collapsed');
			$jq('#quarterlyGrid').css("display","none");
			$jq('#treeModel2').attr('class','collapsed');
			$jq('#halfYearlyGrid').css("display","none");
			$jq('#treeModel3').attr('class','collapsed');
			$jq('#anualGrid').toggle();
			if($jq('#treeModel4').attr('class')=='collapsed'){
				$jq('#treeModel4').attr('class','expanded');
			}
			
			else{
				$jq('#treeModel4').attr('class','collapsed');
			}
			document.getElementById('fromDate5').value="";
			document.getElementById('toDate4').value="";
			document.getElementById('fromDate3').value="";
			document.getElementById('toDate2').value="";
			document.getElementById('pwd1').checked = false;
			document.getElementById('pwd2').checked = false; 
		}
	}
	
	function getReservationWiseReport(type,format,date) {                                         //new modified functions
		var errorMessage = "";
		var status = true;
		
		if(type=='monthly'){
			if(document.getElementById('monthlyFlagY').checked)
				type=document.getElementById('monthlyFlagY').value;
			else if(document.getElementById('monthlyFlagN').checked)
				type=document.getElementById('monthlyFlagN').value;
			else {
				errorMessage+= "Please Select Monthly Report Type\n";
				status = false;
			}
		}
		if(type=='totalVacant'){
			if(document.getElementById('fromDate').value==''){
				errorMessage +="Please Select  Date\n";
				if(status==true) {
					document.getElementById('fromDate').focus();
					status = false;
				}
			}
		}
		
		if(status){
			
			if(format=='pdf' && type=='totalVacant' ){
				window
				.open(
						"./report.htm?param=ireportReservationwise&type="+type+"&returnValue=report&fromDate="+document.getElementById('fromDate').value+"",
						'preview',
						'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
			} 
			if(format=='pdf' && (type=='monthly'||type=='catVacant'||type=='desigVacant'||type=='halfYearEnding'||type=='empDetails'||type=='annualYearEnding' ||type=='reservationReport' || type=='hrdgAnnual')){
				window
				.open(
						"./report.htm?param=ireportReservationwise&type="+type+"&returnValue=report",
						'preview',
						'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
			}
			if(format== 'excel'){
				document.forms[0].param.value = "ireportReservationwiseExcel";
				document.forms[0].type.value = type;
				document.forms[0].action = "report.htm";
				document.forms[0].submit();
			} 		
		}else{
			alert(errorMessage);		
		}	
	} 
	function getEmpLastModifiedReport(type,format,fromid,toid) {                            //new modified functions
		var errorMessage = "";
		var status = true;
		
		if(document.getElementById(fromid).value==''){
			errorMessage +="Please Select From Date\n";
			if(status==true) {
				document.getElementById(fromid).focus();
				status = false;
			}
		}
		if(document.getElementById(toid).value==''){
			errorMessage +="Please Select To Date\n";
			if(status==true) {
				document.getElementById(toid).focus();
				status = false;
			}
		}
		
		if(status){
			if(format == 'pdf'){
				window
				.open(
						"./report.htm?param=ireportEmpLastModified&type="+type+"&returnValue=report&fromDate="+document.getElementById(fromid).value+"&toDate="+document.getElementById(toid).value,
						'preview',
						'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
			}else if(format=='excel'){
				document.forms[0].type.value = type;
				document.forms[0].param.value = "ireportEmpLastModifiedExcel";
				document.forms[0].action = "report.htm";
				document.forms[0].submit();
			}
		}else{
			alert(errorMessage);		
		}	
	}
	function getDisabilitiesAnnualReport(format) {
		var errorMessage = "";
		var status = true;
		
		if(document.getElementById('pwd1').checked == false && document.getElementById('pwd2').checked == false ){
			errorMessage +="Please Select PWD Report \n";
			if(status==true) {
				document.getElementById('pwd1').focus();
				status = false;
			}
		}
		
		if(status){
			if(document.getElementById('pwd1').checked == true && format == 'pdf'){
				window
				.open(
						"./report.htm?param=HalfYearAllPhReport&fromDate="+document.getElementById('fromDate5').value+"&toDate="+document.getElementById('toDate4').value,
						'preview',
						'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
			}else if(document.getElementById('pwd2').checked == true && format == 'pdf'){

				window
				.open(
						"./report.htm?param=phVacantReport&fromDate="+document.getElementById('fromDate5').value+"&toDate="+document.getElementById('toDate4').value,
						'preview',
						'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
			
			}else if(document.getElementById('pwd1').checked == true &&format== 'excel'){
				document.forms[0].type.value = type;
				document.forms[0].param.value = "HalfYearAllPhExcelReport";
				document.forms[0].action = "report.htm";
				document.forms[0].submit();
			}else if(document.getElementById('pwd2').checked == true &&format== 'excel'){
				document.forms[0].type.value = type;
				document.forms[0].param.value = "phVacantExcelReport";
				document.forms[0].action = "report.htm";
				document.forms[0].submit();
			}
		}else{
			alert(errorMessage);		
		}	
	}
 function viewMiscReports(param,id,gridId,type){
	 var treeView=$jq('#'+id).attr('class');
	 if(id!='treeModel0'){
		 $jq('#nominalGrid').css("display","none");
		 $jq('#treeModel0').attr('class','collapsed');
	 }
	 if(id!='treeModel'){
		 $jq('#categoryGrid').css("display","none");
		 $jq('#treeModel').attr('class','collapsed');
	 }
	 if(id!='treeModel2'){
		 $jq('#designationGrid').css("display","none");
		 $jq('#treeModel2').attr('class','collapsed');
	 }
	 if(id!='treeModel3'){
		 $jq('#groupGrid').css("display","none");
		 $jq('#treeModel3').attr('class','collapsed');
	 }
	 if(id!='treeModel4'){
		 $jq('#joiningGrid').css("display","none");
		 $jq('#treeModel4').attr('class','collapsed');
	 }
	 if(id!='treeModel5'){
		 $jq('#phGrid').css("display","none");
		 $jq('#treeModel5').attr('class','collapsed');
	 }
	 if(id!='treeModel6'){
		 $jq('#religionGrid').css("display","none");
		 $jq('#treeModel6').attr('class','collapsed');
	 }
	 if(id!='treeModel7'){
		 $jq('#reservationGrid').css("display","none");
		 $jq('#treeModel7').attr('class','collapsed');
	 }
	 if(id!='treeModel8'){
		 $jq('#communityGrid').css("display","none");
		 $jq('#treeModel8').attr('class','collapsed');
	 }
	 if(id!='treeModel11'){
		 $jq('#joiningDrdoGrid').css("display","none");
		 $jq('#treeModel11').attr('class','collapsed');
	 }
	 if(id!='treeModel12'){
		 $jq('#experienceGrid').css("display","none");
		 $jq('#treeModel12').attr('class','collapsed');
	 }
	 if(id!='treeModel13'){
		 $jq('#retirementGrid').css("display","none");
		 $jq('#treeModel13').attr('class','collapsed');
	 }
	 if(id!='treeModel14'){
		 $jq('#financeGrid').css("display","none");
		 $jq('#treeModel14').attr('class','collapsed');
	 }
	 
	 if(!( param=='categoryreport'||param=='designationwiseReport'||param=='EmpGroupwiseReport'||param=='EmpReligionwiseReport'||param=='EmpCommunityReport')){
	 if($jq('#'+id).attr('class')=='collapsed'){
			$jq('#'+id).attr('class','expanded');	
	 }else{
		 $jq('#'+id).attr('class','collapsed');
		}
	 $jq('#'+gridId).toggle();
	 }
	 if(treeView!='expanded' &&( param=='categoryreport'||param=='designationwiseReport'||param=='EmpGroupwiseReport'||param=='EmpReligionwiseReport'||param=='EmpCommunityReport')){
		 $jq.post('report.htm?param='+param+'&type='+type, function(html){$jq('#body').html(html);
		 $jq('#'+id).attr('class','expanded');
		 $jq('#'+gridId).toggle();});	 
	 }
	 else if(treeView=='expanded' &&( param=='categoryreport'||param=='designationwiseReport'||param=='EmpGroupwiseReport'||param=='EmpReligionwiseReport'||param=='EmpCommunityReport')){
		 $jq('#'+id).attr('class','collapsed');
		 $jq('#'+gridId).toggle();
		 
	 }
	
	}
 function viewStatusReports(id,gridId){
	 var treeView=$jq('#'+id).attr('class');
	 $jq('#allReqGrid').css("display","none");
	 $jq('#treeModel').attr('class','collapsed')
	 $jq('#pendingGrid').css("display","none");
	 $jq('#treeModel2').attr('class','collapsed');
	 $jq('#leaveGridGrid').css("display","none");
	 $jq('#treeModel3').attr('class','collapsed');
	 
	 
	 if($jq('#'+id).attr('class')=='collapsed'){
			$jq('#'+id).attr('class','expanded');	
	 }else{
		 $jq('#'+id).attr('class','collapsed');
		}
	 $jq('#'+gridId).toggle();
	
	
	}
	function viewOrgReports(param,id,gridId,type){
		 var treeView=$jq('#'+id).attr('class');
		 if(id!='treeModel'){
		 $jq('#orgGrid').css("display","none");
		 $jq('#treeModel').attr('class','collapsed')
		 }
		 if(id!='treeModel2'){
		 $jq('#mappingGrid').css("display","none");
		 $jq('#treeModel2').attr('class','collapsed');
		 }
		 if(id!='treeModel9'){
		 $jq('#hierarchyGrid').css("display","none");
		 $jq('#treeModel9').attr('class','collapsed');
		 }
		 if(id!='treeModel10'){
		 $jq('#phyhierarchyGrid').css("display","none");
		 $jq('#treeModel10').attr('class','collapsed');
		 }
		 if(param=='OrganizationReport'){
		 if($jq('#'+id).attr('class')=='collapsed'){
				$jq('#'+id).attr('class','expanded');	
		 }else{
			 $jq('#'+id).attr('class','collapsed');
			}
		 $jq('#'+gridId).toggle();
		 }
		 if(treeView=='collapsed'&&param!='OrganizationReport'){
			 $jq.post('report.htm?param='+param+'&type='+type, function(html){
				 $jq('#body').html(html);
				 $jq('#'+id).attr('class','expanded');
				 $jq('#'+gridId).toggle();
			 });	 
		 }
		 else if(treeView!='collapsed'&&param!='OrganizationReport'){
			 $jq('#'+id).attr('class','collapsed');
			 $jq('#'+gridId).toggle();
		 }
		
	}
	
	
	//naresh
	function viewaddressReports(param,id,gridId,type){
		 var treeView=$jq('#'+id).attr('class');
		 if(id!='treeModel'){
		 $jq('#orgGrid').css("display","none");
		 $jq('#treeModel').attr('class','collapsed')
		 }
		 if(id!='treeModel2'){
		 $jq('#mappingGrid').css("display","none");
		 $jq('#treeModel2').attr('class','collapsed');
		 }
		 if(id!='treeMode3'){
		 $jq('#hierarchyGrid').css("display","none");
		 $jq('#treeMode3').attr('class','collapsed');
		 }
		 if(id!='treeMode4'){
			 $jq('#persinfoGrid').css("display","none");
			 $jq('#treeMode4').attr('class','collapsed');
			 }
		 if(param=='PRESENTADDRESSREPORT'||param=='CGHSREPORT'||param=='addressreport' ||param=='PersonalInfo'){
		 if($jq('#'+id).attr('class')=='collapsed'){
				$jq('#'+id).attr('class','expanded');	
		 }else{
			 $jq('#'+id).attr('class','collapsed');
			}
		 $jq('#'+gridId).toggle();
		 }
		 if(treeView=='collapsed'&&param!='PRESENTADDRESSREPORT'&&param!='CGHSREPORT'&&param!='addressreport'&&param!='PersonalInfo'){
			 $jq.post('report.htm?param='+param+'&type='+type, function(html){
				 $jq('#body').html(html);
				 $jq('#'+id).attr('class','expanded');
				 $jq('#'+gridId).toggle();
			 });	 
		 }
		 else if(treeView!='collapsed'&&param!='addressReports'){
			 $jq('#'+id).attr('class','collapsed');
			 $jq('#'+gridId).toggle();
		 }
		
	}
	
	function nominalReport(){
		window.open('./report.htm?param=nominalReport','Report','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	}
	
	function nominalReportXL(requestType){
						window.open('./report.htm?param=nominalReportXl&type='+requestType+'Report','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
	}

	function PRESENTADDRESSREPORT(){
		window.open('./report.htm?param=PRESENTADDRESSREPORT','Report','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	}
	function CGHSREPORT(){
		window.open('./report.htm?param=CGHSREPORT','Report','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	}
	function addressreport(){
		window.open('./report.htm?param=addressreport','Report','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	}
	
	
	function persInforeport(){
		window.open('./report.htm?param=PersonalInfo','Report','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	}