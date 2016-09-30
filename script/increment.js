function saveAnnualIncrements(){
	var status=true;
	var checkedSfid="";
	var errorMessage="";
	var i=0;
	if ($jq('#adminEffectDate').val()=='') {
		errorMessage += "Please select Effective Date\n";
		if (status) {
			status = false;
			$jq("#adminEffectDate").focus();
		}
	}
	if ($jq('#gazettedType').val()==0) {
		errorMessage += "Please select Gazetted Type\n";
		if (status) {
			status = false;
			$jq("#gazettedType").focus();
		}
	}
	if ($jq('#doPartNo').val()=='select') {
		errorMessage += "Please select DoPart Number\n";
		if (status) {
			status = false;
			$jq("#doPartNo").focus();
		}
	}
	if ($jq('#casualityId').val()=='0') {
		errorMessage += "Please select Casuality\n";
		if (status) {
			status = false;
			$jq("#casualityId").focus();
		}
	}
   if($jq('#incrementtable tr:not(:first)').each (function(){
		if($jq(this).find('td').eq(0).find('input:checkbox').attr('checked')==true){
			i++;
		}
	}));
	if(i==0){
		errorMessage += "Please select atleast One Request!\n"
		status = false;
	}
	i=0;
	if (status){
	$jq("#incrementtable tr:not(:first)").each(function() {	
		if($jq(this).find("td").eq(0).find("input:checkbox").is(':checked')){
			checkedSfid+="'"+$jq(this).find("td").eq(0).find("input:checkbox").attr('id')+"',"
		}		
	});
	
	requestDetails={
			"param":"submitAnnualIncrements",
			"adminEffectDateString" : $jq('#adminEffectDate').val(),
			"sfIds":checkedSfid,
			"gazettedType":$jq('#gazettedType').val(),
			"doPartNo":$jq('#doPartNo option:selected').val(),
			"casualityId" :$jq('#casualityId option:selected').val()
	}
	$jq.post('annualIncrement.htm?' + dispUrl,requestDetails,function(html){
		$jq('#EmpDetails').html(html);
		getdoPartButton();
		
		});
	}
	else{
		alert(errorMessage);
	}
}

function saveAnnualIncrementsToPay(){
	var checkedSfid="";
	var errorMessage = "";
	var status=true;
	var i=0;
	if($jq('#financeAcceptedDate').val()==''){
		status=false;
		errorMessage +="Please Select the Effective Date \n"
		$jq('#financeAcceptedDate').focus();
	}
	if($jq('#incrementtable tr:not(:first)').each (function(){
		if($jq(this).find('td').eq(0).find('input:checkbox').attr('checked')==true){
			i++
		}
	}));
	if(i==0){
		errorMessage += "Please select atleast One Request!\n"
		status = false;
	}
	i=0;
	if(status){
		$jq("#incrementtable tr:not(:first)").each(function() {	
			if($jq(this).find("td").eq(0).find("input:checkbox").is(':checked')){
				checkedSfid+="'"+$jq(this).find("td").eq(0).find("input:checkbox").attr('id')+"',"
			}		
		});
		requestDetails={
				"param":"submitIncrementsTopay",
				"financeAcceptedDate":$jq('#financeAcceptedDate').val(),
				"doPartNo":$jq('#doPartNumberDetails option:selected').val().split('--')[0].trim().substring(0,3),
				"sfIds":checkedSfid
		}
		$jq.post('annualIncrement.htm?' + dispUrl,requestDetails,function(html){
			$jq('#EmpDetails').html(html);
			$jq('#financeAcceptedDate').val('');
			});

	}else{
		alert(errorMessage);
	}
	}

function getDoPartNumbers(){

		$jq('#param').val("doPartList");
		requestDetails={
				"param" : "doPartList",
				"gazettedType":$jq('#gazettedType').val()
		}
		gazetted=$jq('#gazettedType').val();
		$jq.post('annualIncrement.htm',requestDetails,    
		           function(html){ 
		            	 $jq('#dopart').html(html);
		            	 $jq('#gazettedType').val(gazetted);
		            	 $jq('#doPartNumberDiv').show();
		            	 getdoPartButton();
		            	 $jq('#EmpDetails').html('');
		            	 } 
		      );
		
	
		    
}

function getdoPartButton(){
	if ($jq('#doPartNo').val() == 0) {
		$jq('#saveButtonDiv').show();
		$jq('#publishButtonDiv').hide();
		$jq('#adminEffectDate').val('');
}else if($jq('#doPartNo').val() == 'select'){
	$jq('#publishButtonDiv').hide();
	$jq('#saveButtonDiv').hide();
	$jq('#adminEffectDate').val('');
}else{
	$jq('#publishButtonDiv').show();
	$jq('#saveButtonDiv').hide();
	$jq('#adminEffectDate').val('');
}
}

function getCasualityList(){
	requestDetails={
			"param" : "casualityList",
			"gazettedType":$jq('#gazettedType').val(),
			"doPartNo":$jq('#doPartNo option:selected').val()
	}
	gazetted=$jq('#gazettedType option:selected').val();
	doNo=$jq('#doPartNo option:selected').val();
	$jq.post('annualIncrement.htm',requestDetails,    
	           function(html){ 
					$jq('#dopart').html('');
	            	 $jq('#dopart').html(html);
	            	 $jq('#gazettedType').val(gazetted);
	            	 $jq('#doPartNo').val(doNo);
	            	 $jq('#casualityDiv').show();
	            	 $jq('#doPartNumberDiv').show();
	            	 getdoPartButton();
	            	 $jq('#EmpDetails').html('');
	            	 } 
	     );	
}

function getEmployeeList(){
	requestDetails={
			"param" : "EmployeeList",
			"gazettedType":$jq('#gazettedType').val(),
			"doPartNo":$jq('#doPartNo option:selected').val(),
			"casualityId" :$jq('#casualityId option:selected').val()
	}
	$jq.post('annualIncrement.htm',requestDetails,    
	           function(html){
					$jq('#EmpDetails').show();
					$jq('#EmpDetails').html(html);
	            	 getdoPartButton();
	            	 } 
	     );	
	
}

function checkRunmonthDate(runmonth){
	var runOriginalDate=runmonth.split(" ")[0];
	var financeAcceptedDate = $jq("#financeAcceptedDate").val();
	var runmonthDate = new Date(runOriginalDate.split("-")[0],runOriginalDate.split("-")[1]-1, runOriginalDate.split("-")[2], 0, 0, 1, 0);
	var date2 = new Date(financeAcceptedDate.split("-")[2], getMonthID(financeAcceptedDate.split("-")[1])-1, financeAcceptedDate.split("-")[0], 0, 0, 1, 0);
	if (runmonthDate >= date2) {
		runString= runmonthDate+'';
		alert("Paybill Of "+runString.split(" ")[1] +" "+runString.split(" ")[3] +" month is already generated .\n");
		$jq("#financeAcceptedDate").val('');
	}
}
//new script 
function getEmployeeFinalDetailsList(){
	requestDetails={
			"param" : "EmployeeFinalDetailsList",
			"doPartNo":$jq('#doPartNumberDetails option:selected').val().split('#')[0],
			"gazettedType":$jq('#doPartNumberDetails option:selected').val().split('--')[1].trim(),
	}
	$jq.post('annualIncrement.htm',requestDetails,function(html){
		$jq("#result").html(html);
		} 
	     );	
}
function updateAnnualIncrementDetailsTable(){
	var i=0;
	var errorMessage="";
	var status =true;
	var checkedSfid="";
	if($jq('#incrementtable tr:not(:first)').each (function(){
		if($jq(this).find('td').eq(0).find('input:checkbox').attr('checked')==true){
			i++;
		}
	}));
	if(i==0){
		errorMessage += "Please select atleast One Request!\n"
		status = false;
	}
	i=0;
	if (status){
		$jq("#incrementtable tr:not(:first)").each(function() {	
			if($jq(this).find("td").eq(0).find("input:checkbox").is(':checked')){
				checkedSfid+="'"+$jq(this).find("td").eq(0).find("input:checkbox").attr('id')+"',"
			}		
		});
		
		requestDetails={
				"param":"updateAnnualIncrementDetails",
				"doPartNo":$jq('#doPartNumberDetails option:selected').val().split('#')[0],
				"gazettedType":$jq('#doPartNumberDetails option:selected').val().split('--')[1].trim(),
				"sfIds":checkedSfid,		
		}
		$jq.post('annualIncrement.htm?' + dispUrl,requestDetails,function(html){
			$jq("#result").html(html);
			});
		}else{
			alert(errorMessage);
		}
}
function getEmployeeIntegratedPayIncrement(){
	requestDetails={
			"param" : "EmployeeIntegratedPayIncrement",
			"doPartNo":$jq('#doPartNumberDetails option:selected').val().split('--')[0].trim().substring(0,3),
			"gazettedType":$jq('#doPartNumberDetails option:selected').val().split('--')[1].trim(),
	}
	$jq.post('annualIncrement.htm',requestDetails,function(html){
		$jq("#EmpDetails").html(html);
		} 
	     );	
}



