function financialYearDetails()
{
	var flag = true;
	var errorMessage = "";
	if ($jq('#FYearFrom').val() == '') {
		errorMessage += "Please enter From Year\n";
		if (flag) {
			$jq('#FYearFrom').focus();
			flag = false;
		
		}
	}       
	if ($jq('#FYearTo').val() == '') {
		errorMessage += "Please enter To Year\n";
		if (flag) {
			$jq('#FYearTo').focus();
			flag = false;
		}
	}
	if ($jq('#FYearTo').val()-$jq('#FYearFrom').val()!=1) {
		errorMessage += "Please enter the proper range of years\n";
		if (flag) {
			$jq('#FYearFrom').focus();
			flag = false;
		}
	}
	if(errorMessage==""){
		var requestDetails = {
				"param" : "InsertFYMasterDetails",
				"FYearFrom" : $jq('#FYearFrom').val(),
				"FYearTo" : $jq('#FYearTo').val(),
				"pk" : $jq('#pk').val(),
				"type" : $jq('#type').val()
			};
			$jq.post('incomeTax.htm', requestDetails, function(html) {
				$jq("#result").html(html);
				
			});
		clearFinancialYearDetails();
	}
	else
	{
		alert(errorMessage);
	}
	
}
function clearFinancialYearDetails(){
	$jq('#FYearFrom').val('');	
	$jq('#FYearTo').val('');	
}
function editFinYearDetails(id,from,to)
{
	$jq('#pk').val(id);
	$jq('#type').val("edit");
	$jq('#FYearFrom').val(from);
	$jq('#FYearTo').val(to);
	
}

function deleteFinYearDetails(id,from,to)
{
	var check=confirm("Do you want to delete ?");
	if(check){
		var requestDetails = {
				"param" : "finYearDelete",
				"type"  : '',
				"from"  : from,
				"to"    : to,
				"pk"    : id
			};
			$jq.post('incomeTax.htm', requestDetails, function(html) {
				$jq("#result").html(html);
			});
		clearFinancialYearDetails();
	}
	
}
function loadScript(tableID) {
	$jq("#"+tableID+" tr").live('click', function() {
		levelID = $jq(this)[0].rowIndex;		
	});
}

function getIncomeTaxStages(type)
{
	var flag = true;
	var errorMessage = "";
	if($jq('#selectedFYear').val() == 'select')
	{
    	errorMessage += "Please Select the Financial Year";
 		if(flag)
 		{
 			$jq('#selectedFYear').focus();
 			flag = false;
 		}
	}
	if(($jq('input:radio[name=taxableflag]:checked').val() != 'M') && ($jq('input:radio[name=taxableflag]:checked').val() != 'F') && ($jq('input:radio[name=taxableflag]:checked').val() != 'Sr'))
	{
		$jq('#stages').text('');	
	}
	if(flag == true)
	{
		if(type == 'page' && (($jq('input:radio[name=taxableflag]:checked').val() == 'M') || ($jq('input:radio[name=taxableflag]:checked').val() == 'F') || ($jq('input:radio[name=taxableflag]:checked').val() == 'Sr')))
		{
			$jq('#result').text('');
			var requestDetails = {
					"param" : "stages",
					"taxableflag" : $jq('input:radio[name=taxableflag]:checked').val(),
					"selectedFYear" :  $jq('#selectedFYear').val()
								};
			$jq.post('incomeTax.htm', requestDetails, function(html) {
				$jq("#stages").html(html);
				setIncomeTaxStages();
				});
		}
	}
	else
	{
		alert(errorMessage);
	}
}

function insertNewRow(rowIndex, id)
{
	if(id == 'incomeTaxStages')
		createIncometaxRow(rowIndex);
}

function createIncometaxRow(rowIndex)
{
	count++;	
	var color = "#A29A9A";
	var from = $jq("#FYearTo" + rowIndex).val();
	from++;
	
	var row = "<tr id=row"+count+">";
		row += "<td style=width:10%>";
		row += "<input type=text id=FYearFrom"+count+" name=FYearFrom  value="+from+" onclick=\"javascript:emptyTextBox(this);\"  onkeypress=\"return checkInt(event);\"/>";
		row += "</td>";
		
		row += "<td style=width:20%>";
		row += "<input type=text id=FYearTo"+count+" name=FYearTo  value=To onclick=\"javascript:emptyTextBox(this);\" onkeypress=\"return checkInt(event);\"/>";
	    row += "</td>";

		row += "<td style=width:10%>";
		row += "<input type=text id=amount"+count+" name=amount  value=Tax onclick=\"javascript:emptyTextBox(this);\"  onkeypress=\"return checkInt(event);\"/>";
	    row += "</td>";
	    
	    row += "<td style=width:25%>";
	    row += "%";
	    row += "</td>";
	    
	    row += "<td style=width:5%>";
		row += "<input type=button class=smallbtn value=+ onclick=insertNewRow("+count+",\'incomeTaxStages\'); />";			
	    row += "</td>";
	
	    row += "<td style=width:5%>";
		row += "<input type=button id=delete class=smallbtn value=- onclick=deleteRow(this); />";			
	    row += "</td>";
	    row += "</tr>";
	$jq("#row" + rowIndex).after(row);
}

function deleteRow(e){
	
	$jq(e).parent().closest("tr").remove();
}
function emptyTextBox(div)
{
	funtype = $jq(div).val();
	if(funtype == 'To' || funtype == 'From' || funtype == 'Tax')
	{
		$jq(div).val('');
		$jq(div).attr('style', 'color:#000');
	}
}

function manageIncomeTaxStages()
{
	var errorMessage = "";
	var mainJSON = {};
	var i = 0;
	var stageId = 0;
	$jq("#incomeTaxStages tr").each(function() {		
		var jsonIncomeTaxRow = {};
		stageId++;
		
		jsonIncomeTaxRow['from'] = $jq(this).find("td").eq(0).find('input').val();
		jsonIncomeTaxRow['to'] = $jq(this).find("td").eq(1).find('input').val();
		jsonIncomeTaxRow['tax'] = $jq(this).find("td").eq(2).find('input').val();
		jsonIncomeTaxRow['stageId'] = stageId;
		mainJSON[i] = jsonIncomeTaxRow;
		
		i++;
	});
	
	if (errorMessage == '') 
	{
		$jq.each(mainJSON, function(index, value) {
			var line = parseInt(index) + 1;
			
			if (value.from == '' || value.from == 'From') {
				errorMessage += "Please Enter 'from' at Line : " + line + "\n";
			}
			if (value.to == '' || value.to == 'To') {
				errorMessage += "Please Enter 'To' at Line : " + line + "\n";
			}
			if (value.tax == '' || value.tax == 'Tax') {
				errorMessage += "Please Enter 'tax' at Line : " + line + "\n";
			}
		});
	}
	if (errorMessage == '') 
	{
		var flag = true;
		var totalRows = count + 1;
		for(var i = 0; i < totalRows; i++)
		{
			var nextIndex = i + 1;
			var nextSlabFrom = parseInt($jq('#FYearFrom' + nextIndex).val());
			var nextSlabTo = parseInt($jq('#FYearTo' + nextIndex).val());
			if(!((nextSlabFrom > parseInt($jq('#FYearFrom' + i).val())) && (nextSlabFrom > parseInt($jq('#FYearTo' + i).val())) && (nextSlabFrom < nextSlabTo)
				&& (nextSlabTo > parseInt($jq('#FYearFrom' + i).val())) && (nextSlabTo > parseInt($jq('#FYearTo' + i).val())) && (nextSlabTo > nextSlabFrom)))
			{
				alert("Income Tax slabs are not in series! check slab values");
				flag = false;
				break;
			}
			if(nextIndex == (totalRows - 1)) 
				break;
		}
		if(flag)
		{
			var requestDetails = {
		    "taxableflag": $jq('input:radio[name=taxableflag]:checked').val(),
		    "selectedFYear" :  $jq('#selectedFYear').val(),
			"param" : "taxManage",
			"stages" : JSON.stringify(mainJSON)
								};
			$jq.post('incomeTax.htm', requestDetails, function(html) {
				$jq("#result").html(html);	
			});
			clearIncomeTaxStages();
		}
	}
	else 
	{
		alert(errorMessage);
	}
}

function validateIncomeTaxStages(){
	var errorMessages = "";
	$jq.each(mainJSON, function(index, value) {
		var line = parseInt(index) + 1;
		if (value.from == ''&& value.from == 'From') {
			errorMessages += "Please enter from at Line :" + line + "\n";
		}
		if (value.to == ''&& value.to == 'To') {
			errorMessages += "Please enter to at Line :" + line + "\n";
		}
		if (value.tax == ''&& value.tax == 'Tax') {
			errorMessages += "Please enter tax at Line :" + line + "\n";
		}
	});
	return errorMessages;
}

function clearIncomeTaxStages()
{
	$jq('#selectedFYear').val('select');
	$jq('#stages').text('');
	$jq("input:radio").attr("checked", false);
}

function setIncomeTaxStages() 
{
	count = 0;
	levelID = 1;
	if (incomeTaxStageListJSON != null) 
	{
		for ( var i = 0; i < incomeTaxStageListJSON.length; i++) 
		{
			$jq('#FYearFrom' + i).val(incomeTaxStageListJSON[i].from);
			$jq('#FYearTo' + i).val(incomeTaxStageListJSON[i].to);
			$jq('#amount' + i).val(incomeTaxStageListJSON[i].tax);
			if (i < incomeTaxStageListJSON.length - 1)
			{
				createIncometaxRow(i);
				levelID++;
			}
		}
	}
}

function deleteIncometaxStages(){
	var check=confirm("Do you want to delete this item?");
	if(check) {
		var requestDetails = {
		    "taxableflag": $jq('input:radio[name=taxableflag]:checked').val(),
		    "selectedFYear" :  $jq('#selectedFYear').val(),
			"param" : "taxDelete",
			
							};
		$jq.post('incomeTax.htm', requestDetails, function(html) {
			$jq("#result").html(html);
			
		});
		clearIncomeTaxStages();
	} 
}
function submitArrearsDetails(){
	var flag = true;
	var errorMessage = "";
	var fDate = $jq('#fromDate').val();
	var tDate = $jq('#toDate').val();
	      
	if ($jq('#fromDate').val() == '') {
		errorMessage += "Please select From Date\n";
		if (flag) {
			$jq('#fromDate').focus();
			flag = false;
		
		}
		
	}       
	else if ($jq('#toDate').val() == '') {
		errorMessage += "Please select To Date\n";
		if (flag) {
			$jq('#toDate').focus();
			flag = false;
		}
	}
	else if ($jq('#oldDA').val() == '') {
		errorMessage += "Please enter old DA value\n";
		if (flag) {
			$jq('#oldDA').focus();
			flag = false;
		}
	}
	else if ($jq('#newDA').val() == '') {
		errorMessage += "Please enter new DA value\n";
		if (flag) {
			$jq('#newDA').focus();
			flag = false;
		}
	}
	else if ($jq('#daFlag1').is(':checked')==false ) {
		   if($jq('#daFlag2').is(':checked')==false){
		           errorMessage += "Please select DA1 / DA2\n";
		           if (flag) {
			          flag = false;
		          }
		   }
	}
	
	if(errorMessage==""){
		var requestDetails = {
				"param"    : "submitArrears",
				"fromDate" : $jq('#fromDate').val(),
				"toDate"   : $jq('#toDate').val(),
				"oldDA"    : $jq('#oldDA').val(),
				"newDA"    : $jq('#newDA').val(),
				"daFlag" : $jq('input:radio[name=daFlag]:checked').val()
			};
			$jq.post('incomeTax.htm',requestDetails, function(html) {
				$jq("#result").html(html);
				clearArrearsDetails();
			});
		
	}
	else
	{
		alert(errorMessage);
	}
	
}
function clearArrearsDetails()
{
	$jq('#fromDate').val('');
	$jq('#toDate').val('');
	$jq('#oldDA').val('');
	$jq('#newDA').val('');
	$jq("input:radio").attr("checked", false);
	$jq('#categoryId').val('select');
	
}
function submitSavingsDetails(){
	var flag = true;
	var errorMessage = "";
	 if ($jq('#sName').val() == '') {
			errorMessage += "Please enter Savings Name\n";
			if (flag) {
				$jq('#sName').focus();
				flag = false;
			}
		}
	 if ($jq('#sType1').is(':checked')==false && $jq('#sType2').is(':checked')==false && $jq('#sType3').is(':checked')==false) {
			errorMessage += "Please select Savings Type\n";
			if (flag) {
				flag = false;
			}
		}
	if(errorMessage==""){
		var requestDetails = {
				"param"    : "submitSavings",
				"sName" : $jq('#sName').val(),
				"sType" : $jq('input:radio[name=sType]:checked').val(),
				"pk" : $jq('#pk').val(),
				"type" : $jq('#type').val(),
				"sectionId" : $jq('#sectionId').val()
			};
		$jq.post('incomeTax.htm',requestDetails, function(html) {
			$jq("#result").html(html);
			clearSavingsDetails();
		});
	}
	else
	{
		alert(errorMessage);
	}
}
function clearSavingsDetails(){
	$jq('#sName').val('');
	$jq('#sectionId').val('select');
	$jq("input:radio").attr("checked", false);
	$jq('#pk').val('');
	$jq('#type').val('');
}
function editSavingsDetails(id,sName,sType,secId)
{
 
	$jq('#pk').val(id);
	$jq('#type').val("edit");
	$jq('#sName').val(sName);
	$jq('#sectionId').val(secId);

	
	
	if(sType=='Savings'){
		$jq("#sType1[value=" + sType + "]").attr('checked', true);
	}else if(sType=='Exemptions'){
		$jq("#sType2[value=" + sType + "]").attr('checked', true);
	}else if(sType=='Other Income Sources'){
		$jq("#sType3[value=" + sType + "]").attr('checked', true);
	}

	
}
function deleteSavingsDetails(id,sName,sType,secName)
{
	var check=confirm("Do you want to delete ?");
	if(check){
		var requestDetails = {
				"param"  : "savingsDelete",
				"type"   : '',
				"sName"  : sName,
				"sType"  : sType,
				"pk"     : id
			};
			$jq.post('incomeTax.htm', requestDetails, function(html) {
				$jq("#result").html(html);
			});
		clearSavingsDetails();
	}
	
}
function searchConfigDetails(){
	var flag=true;
	var errorMessage = "";
	if ($jq('#searchSfid').val() == '') {
		errorMessage += "Please enter SFID\n";
		if (flag) {
			$jq('#searchSfid').focus();
			flag = false;
		}
	}else if($jq('#selectedFYear').val()=='select'){
		errorMessage += "Please select Financial Year\n";
		if (flag) {
			$jq('#selectedFYear').focus();
			flag = false;
		}
	}
	if(flag){
		  var requestDetails = {
			"param" : "config",
			"searchSfid" : $jq('#searchSfid').val(),
		    "selectedFYear" :  $jq('#selectedFYear').val()
		};
		$jq.post('incomeTax.htm', requestDetails, function(html) {
			$jq("#config").html(html);
			setIncomeTaxConfigDetails();
			clearConfigRowDetails();	
		});
	 }else{
			alert(errorMessage);
		}
}
function clearConfigRowDetails(){
	$jq("#itConfigOtherSources tr:not(:first)").each(function() {		
	
		if($jq(this).find("td").eq(1).find('input').val()=="0"){
			$jq(this).find("td").eq(1).find('input').val('');
			}
	});
$jq("#itConfigSavings tr:not(:first)").each(function() {
		if($jq(this).find("td").eq(1).find('input').val()=="0"){
			$jq(this).find("td").eq(1).find('input').val('');
			}
		if($jq(this).find("td").eq(2).find('input').val()=="0"){
			$jq(this).find("td").eq(2).find('input').val('');
			}
	});
	$jq("#itConfigExemptions tr:not(:first)").each(function() {		
		if($jq(this).find("td").eq(1).find('input').val()=="0"){
			$jq(this).find("td").eq(1).find('input').val('');
			}
	});
	
}

function clearConfigDetails(){
	$jq('#searchSfid').val('');
	$jq('#selectedFYear').val('select');
}
function manageIncomeTaxConfigDetails(){
	
	var errorMessage = "";
	var mainJSON = {};
	var i = 0;
	var otherSourceId = 0;
	var savingsId = 0;
	var exemptionId = 0;
	$jq("#itConfigOtherSources tr:not(:first)").each(function() {		
		var jsonConfigOtherSourcesRow={};
		jsonConfigOtherSourcesRow['actual']=$jq(this).find("td").eq(1).find('input').val();
		jsonConfigOtherSourcesRow['remarks']=$jq(this).find("td").eq(2).find('input').val();
		jsonConfigOtherSourcesRow['submissionFlag']=$jq(this).find("td").eq(3).find(":input:checkbox").is(':checked');
		jsonConfigOtherSourcesRow['savingsTypeId']=$jq(this).find("td").eq(4).find('input').val();
		mainJSON[i] = jsonConfigOtherSourcesRow;	
		i++;
		if(jsonConfigOtherSourcesRow['submissionFlag']==true){
			$jq(this).find("td").eq(3).find(":input:checkbox").attr("checked", "checked").attr('disabled','true');
	}
		
	});
	
	$jq("#itConfigSavings tr:not(:first)").each(function() {
		
		var jsonConfigSavingsRow={};
		jsonConfigSavingsRow['projection']=$jq(this).find("td").eq(1).find('input').val();
		jsonConfigSavingsRow['actual']=$jq(this).find("td").eq(2).find('input').val();
		jsonConfigSavingsRow['remarks']=$jq(this).find("td").eq(3).find('input').val();
		jsonConfigSavingsRow['submissionFlag']=$jq(this).find("td").eq(4).find(":input:radio").is(':checked');
		jsonConfigSavingsRow['savingsTypeId']=$jq(this).find("td").eq(5).find('input').val();
		mainJSON[i] = jsonConfigSavingsRow;	
		i++;
		if(jsonConfigSavingsRow['submissionFlag']==true){
			$jq(this).find("td").eq(4).find(":input:radio").attr("checked", "checked").attr('disabled','true');
		}
	});
	
	$jq("#itConfigExemptions tr:not(:first)").each(function() {		
		var jsonConfigExemptionsRow={};
		jsonConfigExemptionsRow['actual']=$jq(this).find("td").eq(1).find('input').val();
		jsonConfigExemptionsRow['remarks']=$jq(this).find("td").eq(2).find('input').val();
		jsonConfigExemptionsRow['submissionFlag']=$jq(this).find("td").eq(3).find(":input:checkbox").is(':checked');
		jsonConfigExemptionsRow['savingsTypeId']=$jq(this).find("td").eq(4).find('input').val();
		mainJSON[i] = 	jsonConfigExemptionsRow;	
		i++;
		if(jsonConfigExemptionsRow['submissionFlag']==true){
			$jq(this).find("td").eq(3).find(":input:checkbox").attr("checked", "checked").attr('disabled','true');
	}
		
	});
	 if (errorMessage == '') {
		var requestDetails = {
			"name" : $jq('#name').val(),
		    "amount" : $jq('#amount').val(),
		    "cpsRec" : $jq('#cpsRec').val(),
		     "ITRec" : $jq('#ITRec').val(),
			"param" : "configDetails",
			"configList" : JSON.stringify(mainJSON)
		};
		$jq.post('incomeTax.htm',requestDetails,function(html) {
			$jq("#result").html(html);
			});
		
	} else {
		alert(errorMessage);
	}
}


function getActualValue(){
	
	alert($jq('#projection').val());
	/*$jq("#itConfigSavings tr:not(:first)").each(function() {		
		var jsonConfigSavingsRow={};
		jsonConfigSavingsRow['projection']=$jq(this).find("td").eq(1).find('input').val();
		jsonConfigSavingsRow['actual']=$jq(this).find("td").eq(2).find('input').val();
		jsonConfigSavingsRow['remarks']=$jq(this).find("td").eq(3).find('input').val();
		jsonConfigSavingsRow['submissionFlag']=$jq(this).find("td").eq(4).find(":input:checkbox").is(':checked');
		jsonConfigSavingsRow['savingsTypeId']=$jq(this).find("td").eq(5).find('input').val();
		mainJSON[i] = jsonConfigSavingsRow;	
		i++;
		if(jsonConfigSavingsRow['submissionFlag']==true){
			$jq(this).find("td").eq(4).find(":input:checkbox").attr("checked", "checked").attr('disabled','true');
	}
		
	});*/
}
function setIncomeTaxConfigDetails() {
	if (incomeTaxConfigListJSON != null) {
		$jq("#itConfigOtherSources tr:not(:first)").each(function() {
		for ( var i = 0; i < incomeTaxConfigListJSON.length; i++) {
			if($jq(this).find("td").eq(4).find('input').val()==incomeTaxConfigListJSON[i].savingsTypeId)
			{
			$jq(this).find("td").eq(1).find('input').val(incomeTaxConfigListJSON[i].actual);
			$jq(this).find("td").eq(2).find('input').val(incomeTaxConfigListJSON[i].remarks);
			if(incomeTaxConfigListJSON[i].submissionFlag=="true"){
					$jq(this).find("td").eq(3).find(":input:checkbox").attr("checked", "checked").attr('disabled','true');
					}
			}
		}
		});
		$jq("#itConfigSavings tr:not(:first)").each(function() {
			for ( var i = 0; i < incomeTaxConfigListJSON.length; i++) {
				if($jq(this).find("td").eq(5).find('input').val()==incomeTaxConfigListJSON[i].savingsTypeId){
					$jq(this).find("td").eq(1).find('input').val(incomeTaxConfigListJSON[i].projection);
					$jq(this).find("td").eq(2).find('input').val(incomeTaxConfigListJSON[i].actual);
					$jq(this).find("td").eq(3).find('input').val(incomeTaxConfigListJSON[i].remarks);
					if(incomeTaxConfigListJSON[i].submissionFlag=="true"){
						if(incomeTaxConfigListJSON[i].projection==incomeTaxConfigListJSON[i].actual){
							$jq(this).find('td').eq(4).find('input:radio[id=submissionFlag2]').attr('checked','checked').attr('disabled',true);
						}else{
							$jq(this).find('td').eq(4).find('input:radio[id=submissionFlag1]').attr('checked','checked').attr('disabled',true);
						}
					}
				}
			}
		});
		$jq("#itConfigExemptions tr:not(:first)").each(function() {
			for ( var i = 0; i < incomeTaxConfigListJSON.length; i++) {
				if($jq(this).find("td").eq(4).find('input').val()==incomeTaxConfigListJSON[i].savingsTypeId)
				{
				$jq(this).find("td").eq(1).find('input').val(incomeTaxConfigListJSON[i].actual);
				$jq(this).find("td").eq(2).find('input').val(incomeTaxConfigListJSON[i].remarks);
				if(incomeTaxConfigListJSON[i].submissionFlag=="true"){
					$jq(this).find("td").eq(3).find(":input:checkbox").attr("checked", "checked").attr('disabled','true'); 
					}
				}
			}
			});
		
	}
}
function editArrearsListDetails(name,amt,cpfRec,ItRec){
	$jq('#amount').val(amt);
	$jq('#cpsRec').val(cpfRec);
	$jq('#ITRec').val(ItRec);
	if(name=='DA1')
	$jq('#name').val('1');
	else
		$jq('#name').val('2');
}
function displaySfid(){
	if($jq('#runAllOrInd').val()=="all"){
		$jq('#dispSfid').hide();
	}
	if($jq('#runAllOrInd').val()=="ind"){
		$jq('#dispSfid').show();
	}
}
function runITcalcDetails(){
	var flag=true;
	var errorMessage = "";
	 if($jq('#selectedFYear').val()=='select'){
		errorMessage += "Please select Financial Year\n";
		if (flag) {
			$jq('#selectedFYear').focus();
			flag = false;
		}
	}else if($jq('#runAllOrInd').val()=='ind'){
		if($jq('#selectSfid').val()==''){
		errorMessage += "Please Enter SFID\n";
		if (flag) {
			$jq('#selectSfid').focus();
			flag = false;
		}
	}
	}else if ($jq('#runTypeFlag1').is(':checked')==false ) {
		   if($jq('#runTypeFlag2').is(':checked')==false){
			   if($jq('#runTypeFlag3').is(':checked')==false){
	           errorMessage += "Please select Planned / Projected /actual\n";
	           if (flag) {
		          flag = false;
	          }
	     }
      }
	}
	 
	if(flag){
		  var requestDetails = {
			"param" : "submitITCalc",
			"selectSfid" : $jq('#selectSfid').val(),
		    "selectedFYear" :  $jq('#selectedFYear').val(),
		    "runAllOrInd" :  $jq('#runAllOrInd').val(),
		    "runTypeFlag" : $jq('input:radio[name=runTypeFlag]:checked').val()
		    
		};
		$jq.post('incomeTax.htm', requestDetails, function(html) {
			$jq("#keylist").html(html);
			clearITcalcDetails();
			});
	 }else{
			alert(errorMessage);
		}
	
}
function clearITcalcDetails(){
	
	$jq('#dispSfid').hide();
	$jq('#selectedFYear').val('select');
	$jq('#runAllOrInd').val('all');
	$jq('#selectSfid').val('');
	$jq("input:radio").attr("checked", false);
}
function submitITSectionDetails(){
	var flag = true;
	var errorMessage = "";
	if($jq('#secName').val() == '')
	{
		errorMessage += "Please Enter Section Name\n";
		if (flag) 
		{
			$jq('#secName').focus();
			flag = false;
		}
	}
	else
	{
		for(var i = 0; i < jsonSectionList.length; i++)
		{
			if($jq('#secName').val() == jsonSectionList[i].secName)
			{
				errorMessage += "Entered Section Name is already existed!";
				$jq('#secName').focus();
				flag = false;
				break;
			}
		}
	}
	if(flag){
		  var requestDetails = {
			"param" : "submitSection",
			"secName" : $jq('#secName').val(),
		    "pk" : $jq('#pk').val(),
			"type" : $jq('#type').val()
		       
		};
		$jq.post('incomeTax.htm', requestDetails, function(html) {
			$jq("#result").html(html);
			clearSectionDetails();
			});
	 }else{
			alert(errorMessage);
		}
}
function clearSectionDetails(){
	$jq('#secName').val('');
	$jq('#pk').val('');
	$jq('#type').val('');
	$jq('#param').val('');
}

function deleteSectionDetails(id)
{
	var check=confirm("Do you want to delete ?");
	if(check){
		var requestDetails = {
				"param"  : "sectionDelete",
				"type"   : '',
				"pk"     : id
			};
			$jq.post('incomeTax.htm', requestDetails, function(html) {
				$jq("#result").html(html);
			});
			 clearSectionDetails();
	}
	
}
function editSectionDetails(id,secName)
{
	$jq('#pk').val(id);
	$jq('#type').val("edit");
	$jq('#secName').val(secName);
}

function saveITAllowanceConfiguration(){
	
	var flag=true;
	var errorMessage = "";
	var configurationDetails = "";
	 if($jq('#ittransportAllowance').val()==''){
		errorMessage += "Please Enter Transport Allowance\n";
		if (flag) {
			$jq('#ittransportAllowance').focus();
			flag = false;
		}
		}else{
			configurationDetails +="IT_TRANSPORT_ALLOWANCE,"+$jq('#ittransportAllowance').val()+"#";
			
		}
	  if($jq('#ageLimitForSrCitizen').val()==''){
		errorMessage += "Please Enter the Age Limit\n";
		if (flag) {
			$jq('#ageLimitForSrCitizen').focus();
			flag = false;
		}
	 }else{
			configurationDetails +="AGE_LIMIT_FOR_SRCITIZEN,"+$jq('#ageLimitForSrCitizen').val()+"#";
		}
	
	if (flag) {
		var requestDetails = {
				"param"  : "saveAllConfDetails",
				"configurationDetails"   : configurationDetails
			};
			$jq.post('incomeTax.htm', requestDetails, function(html) {
				$jq("#result").html(html);
			});

	} else {
		alert(errorMessage);
	}
}

function submitRentDetails(){
	var flag=true;
	var errorMessage = "";
	
	if($jq('#rentFromMonth').val()==''){
		errorMessage+="Please Enter From Date\n";
		flag=false;
		$jq('#rentFromMonth').focus();
	}else if($jq('#rentToMonth').val()==''){
		errorMessage+="Please Enter To Date\n";
		flag=false;
		$jq('#rentToMonth').focus();
		
	}else if($jq('#rent').val()==''){
		errorMessage+="Please Enter the Rent\n";
		flag=false;
		$jq('#rent').focus();
	}
	if (flag) {
		var requestDetails = {
				"param"  : "submitRent",
				"rentFromMonth" : $jq('#rentFromMonth').val(),
				"rentToMonth" : $jq('#rentToMonth').val(),
				"rent" : $jq('#rent').val(),
				"rentRemarks" : $jq('#rentRemarks').val(),
				"pk" : $jq('#pk').val(),
				"type" : $jq('#type').val()
			};
			$jq.post('incomeTax.htm', requestDetails, function(html) {
				$jq("#RentResult").html(html);
				clearITRentDetails();
			});

	} else {
		alert(errorMessage);
	}
}

function deleteITRentDetails(id){
	var check=confirm("Do you want to delete ?");
	if(check){
		var requestDetails = {
				"param" : "ITRentDelete",
				"type"  : '',
				"pk"    : id
			};
			$jq.post('incomeTax.htm', requestDetails, function(html) {
				$jq("#RentResult").html(html);
			});
	}
}
function editITRentDetails(id,month,rent,remarks){
	$jq('#rentFromMonth').val('01-'+month);
	$jq('#rentToMonth').val('01-'+month);
	$jq('#pk').val(id);
	$jq('#rent').val(rent);
	$jq('#rentRemarks').val(remarks);
	$jq('#type').val('edit');
	
}
function clearITRentDetails(){
	$jq('#rentFromMonth').val('');
	$jq('#rentToMonth').val('');
	$jq('#pk').val('');
	$jq('#rent').val('');
	$jq('#rentRemarks').val('');
	$jq('#type').val('');
}
function getDesignationList(){
	$jq('#designationId').find('option:not(:first)').remove().end();
  if(empPayDesignationListJSON!=null){
	 
	 	for(var i=0;i<empPayDesignationListJSON.length;i++){
	 		if(empPayDesignationListJSON[i].gradePay==$jq('#gradePay').val()){
	 		  	$jq("#designationId").append(
				'<option value=' + empPayDesignationListJSON[i].id + '>'
				+ empPayDesignationListJSON[i].designationDetails.name
				+ '</option>');
			}	
		}
	}
  }
function managePrUpdateAllwMaster(){
	var flag=true;
	var errorMessage = "";
	if($jq('#gradePay').val()==''){
		errorMessage+="Please Enter Grade Pay\n";
		flag=false;
		$jq('#gradePay').focus();
	}else if($jq('#designationId').val()=='select'){
		errorMessage+="Please Enter Designation Name\n";
		flag=false;
		$jq('#designationId').focus();
	}else if($jq('#amount').val()==''){
		errorMessage+="Please Enter The Amount\n";
		flag=false;
		$jq('#amount').focus();
	}
	if (flag) {
		var requestDetails = {
				"param"  : "managePUA",
				"gradePay" : $jq('#gradePay').val(),
				"designationId" : $jq('#designationId').val(),
				"amount" : $jq('#amount').val(),
				"type"   : $jq('#type').val(),
				"pk"   : $jq('#pk').val()
			};
			$jq.post('incomeTax.htm', requestDetails, function(html) {
				$jq("#result").html(html);
				clearPUADetails();
			});

	} else {
		alert(errorMessage);
	}
	
}function deletePUADetails(id){
	var check=confirm("Do you want to delete ?");
	if(check){
		var requestDetails = {
				"param" : "deletePUA",
				"type"  : '',
				"pk"    : id
			};
			$jq.post('incomeTax.htm', requestDetails, function(html) {
				$jq("#result").html(html);
			});
	}
}
function editPUADetails(id,gradePay,desId,amount){
	    $jq('#gradePay').val(gradePay);
	    getDesignationList();
	    $jq('#designationId').val(desId);
		$jq('#pk').val(id);
		$jq('#amount').val(amount);
		$jq('#type').val('edit');
}
function clearPUADetails(){
	$jq('#gradePay').val('');
	$jq('#amount').val('');
	$jq('#pk').val('');
	$jq('#designationId').val('select');
	$jq('#type').val('');
}
function manageTestDetails1(){
	var flag=true;
	var errorMessage = "";
	
	if (flag) {
		var requestDetails = {
				"param"  : "testSubmit",
				"name" : $jq('#name').val(),
				"value" : $jq('#value').val()
			};
			$jq.post('incomeTax.htm', requestDetails, function(html) {
				$jq("#result").html(html);
				
			});

	} else {
		alert(errorMessage);
	}
}
function manageDBMigration(){
	var flag=true;
	var errorMessage = "";
	 if ($jq('#sName').val() == 'select') {
			errorMessage += "Please Select Month\n";
			if (flag) {
				$jq('#sName').focus();
				flag = false;
			}
		}
	if (flag) {
		var requestDetails = {
				"param"  : "dbMigrationSubmit",
				"sName" : $jq('#sName').val()
			};
			$jq.post('incomeTax.htm', requestDetails, function(html) {
				$jq("#comp").html(html);
				
			});

	} else {
		alert(errorMessage);
	}
}

function getITPayDetails(){
	if ($jq('#payMonth').val()!='select') {
		var requestDetails = {
				"param"  : "payMonthDetails",
				"payMonth" : $jq('#payMonth').val()
			};
			$jq.post('incomeTax.htm', requestDetails, function(html) {
				$jq("#config").html(html);
				
			});

	} else {
		alert(errorMessage);
	}
}
function compareDBMigration(){
	var errorMessage = "";
	var flag = true;
	if ($jq('#sName').val() == 'select') {
		errorMessage += "Please Select Month\n";
		if (flag) {
			$jq('#sName').focus();
			flag = false;
		}
	}
	if ($jq('#matchedY').is(':checked') == false && $jq('#matchedN').is(':checked') == false) {
		errorMessage += "Please Select Matched / Not-matched case\n";
		if (flag) {
			flag = false;
		}
	}
	if (flag) {
		window
		.open(
				"./incomeTax.htm?param=dbMigrationCompare&sName="
						+$jq('#sName').val()+"&matched="+$jq('input:radio[name=matched]:checked').val(),
				'preview',
				'fullscreen=yes,toolbar=yes,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
      clearDBMigration();
	} 
	else {
		alert(errorMessage);
	}
}
function clearDBMigration(){
	$jq('#sName').val('select');
	$jq("input:radio").attr("checked", false);
}
function submitITSectionMappingDetails(){
		$jq('#param').val('submitSectionMappingDetails');
		$jq.post('incomeTax.htm', $jq('#incomeTaxMasterBean').serialize(), function(html) {
			$jq("#sectionMappingList").html(html);
		});
		clearSectionMappingDetails();
}
function editSectionMappingDetails(id,sectionId,gender,disability,srCitizen,limit){
	 $jq('#limit').val(limit);
	 $jq('#pk').val(id);
	 $jq('#type').val('edit');
	 $jq('#sectionId').val(sectionId);
	 if(gender=='1'){
			$jq("#gender1[value=" + gender + "]").attr('checked', true);
		}else if(gender=='0'){
			$jq("#gender2[value=" + gender + "]").attr('checked', true);
		}
		if(disability=='1'){
			$jq("#disability1[value=" + disability + "]").attr('checked', true);
		}else if(disability=='0'){
			$jq("#disability2[value=" + disability + "]").attr('checked', true);
		}
		if(srCitizen=='1'){
			$jq("#srCitizen1[value=" + srCitizen + "]").attr('checked', true);
		}else if(srCitizen=='0'){
			$jq("#srCitizen2[value=" + srCitizen + "]").attr('checked', true);
		}
		
}
function deleteSectionMappingDetails(id){
	 $jq('#pk').val(id);
	 $jq('#param').val('deleteSectionMappingDetails');
	 $jq.post('incomeTax.htm', $jq('#incomeTaxMasterBean').serialize(), function(html) {
			$jq("#sectionMappingList").html(html);
		});
}
function clearSectionMappingDetails(){
	 $jq('#limit').val('');
	 $jq('#pk').val('');
	 $jq('#type').val('');
	 $jq('#sectionId').val('select');
	 $jq("input:radio").attr("checked", false);
}
function setSavingsVal(idVal){
	if($jq('input:radio[id=submissionFlag2]').is(':checked')){
		$jq('#itConfigSavings').find('tr').eq(parseInt(idVal)+1).find('td').eq(2).find('input').val($jq('#itConfigSavings').find('tr').eq(parseInt(idVal)+1).find('td').eq(1).find('input').val());
		$jq('#itConfigSavings').find('tr').eq(parseInt(idVal)+1).find('td').eq(2).find('input').attr('readonly',true);
	}else{
		$jq('#itConfigSavings').find('tr').eq(parseInt(idVal)+1).find('td').eq(2).find('input').val('');
		$jq('#itConfigSavings').find('tr').eq(parseInt(idVal)+1).find('td').eq(2).find('input').attr('readonly',false);
	}
}