var materialCode='';
var company_Code='00';
var categoryCode='';
var subCategoryCode='';
var itemCode='';
var itemSubCode='';
var companyCode='';
var editMaterial=false;
var orgroleid='';
var invsfid='';
var accHeadNum='';
function editMMGMaster(jsonData,id){
	for ( var i = 0; i < jsonData.length; i++) {
		if (jsonData[i].id == id) {
			masterID=jsonData[i].id;
			$jq('#typeValue').val(jsonData[i].name);
			if($jq('#percentage').length > 0)
				$jq('#percentage').val(jsonData[i].percentage);
			if (jsonData[i].description!="" &&  jsonData[i].description!=null) {
				$jq('#description').val(jsonData[i].description);
				$jq('#counter').val(500);
				$jq('#counter').val($jq('#counter').val() - jsonData[i].description.length);
			}
			if($jq('#companyCode').length > 0)
				$jq('#companyCode').val(jsonData[i].companyCode);
			if($jq('#categoryCode').length > 0)
				$jq('#categoryCode').val(jsonData[i].categoryCode);
			if($jq('#categoryId').length > 0)
				$jq('#categoryId').val(jsonData[i].categoryId);
			if($jq('#subCode').length > 0)
				$jq('#subCode').val(jsonData[i].subCode);
			if($jq('#subCategoryId').length > 0)
				$jq('#subCategoryId').val(jsonData[i].subCategoryId);
			if($jq('#itemCode').length > 0)
				$jq('#itemCode').val(jsonData[i].itemCode);
			if($jq('#subCategoryCode').length > 0)
				$jq('#subCategoryCode').val(jsonData[i].subCategoryCode);
			if($jq('#itemCodeId').length > 0)
				$jq('#itemCodeId').val(jsonData[i].itemCodeId);
			if($jq('#itemSubCode').length > 0)
				$jq('#itemSubCode').val(jsonData[i].itemSubCode);
		}
	}
}
function editMMGMaterial(jsonData,id){
	for ( var i = 0; i < jsonData.length; i++) {
		if (jsonData[i].materialCode == id) {
			masterID=jsonData[i].materialCode;
			if($jq('#categoryCode').length > 0)
				$jq('#categoryCode').val(jsonData[i].categoryCode+'#'+jsonData[i].categoryId);
				getMaterialCode('categoryCode');
				categoryCode=jsonData[i].categoryCode+'#'+jsonData[i].categoryId;
				$jq('#categoryCode').attr("disabled","disabled");
			if($jq('#subCategoryCode').length > 0)
				$jq('#subCategoryCode').val(jsonData[i].subCategoryCode+'#'+jsonData[i].subCategoryId);
				getMaterialCode('subCategoryCode');
				subCategoryCode=jsonData[i].subCategoryCode+'#'+jsonData[i].subCategoryId;
				$jq('#subCategoryCode').attr("disabled","disabled");
			if($jq('#itemCode').length > 0)
				$jq('#itemCode').val(jsonData[i].itemCode+'#'+jsonData[i].itemCodeId);
				getMaterialCode('itemCode');
				itemCode=jsonData[i].itemCode+'#'+jsonData[i].itemCodeId;
				$jq('#itemCode').attr("disabled","disabled");
			if($jq('#itemSubCode').length > 0)
				$jq('#itemSubCode').val(jsonData[i].itemSubCode+'#'+jsonData[i].itemSubCodeId);
				getItemSubCode('itemSubCode');
				itemSubCode=jsonData[i].itemSubCode+'#'+jsonData[i].itemSubCodeId;
				$jq('#itemSubCode').attr("disabled","disabled");
			if($jq('#companyCode').length > 0)
				$jq('#companyCode').val(jsonData[i].companyCode+'#'+jsonData[i].companyId);
				getCompanyCode();
				companyCode=jsonData[i].companyCode+'#'+jsonData[i].companyId;
				$jq('#companyCode').attr("disabled","disabled");
			if($jq('#materialCode').length > 0)
				$jq('#materialCode').html(jsonData[i].materialCode);
			if($jq('#materialName').length > 0)
				$jq('#materialName').val(jsonData[i].materialName);
			if($jq('#rcFlag').length > 0)
				$jq('#rcFlag').val(jsonData[i].rcFlag);
			if($jq('#rcFlag option:selected').text()=="Yes")
				$jq("#contract").css("display","block");
			if($jq('#unitRate').length > 0)
				$jq('#unitRate').val(jsonData[i].unitRate);
			if($jq('#uom').length > 0)
				$jq('#uom').val(jsonData[i].uom);
			if($jq('#consumableFlag').length > 0)
				$jq('#consumableFlag').val(jsonData[i].consumableFlag);
			if (jsonData[i].description!="" &&  jsonData[i].description!=null ) {
				$jq('#description').val(jsonData[i].description);
				$jq('#counter').val(500);
				$jq('#counter').val($jq('#counter').val() - jsonData[i].description.length);
				
			}
			editMaterial=true;				
		}
	}
}
function editMMGInventory(jsonData,id){
	for ( var i = 0; i < jsonData.length; i++) {
		if (jsonData[i].invId == id) {
			masterID=jsonData[i].invId;
			if($jq('#inventoryNo').length > 0){
				$jq('#inventoryNo').html(jsonData[i].inventoryNo);
			}
			//$jq(".as-values").val(jsonData[i].sfid+'#'+jsonData[i].orgRoleId+",");
			$jq('#sfidCode').html(jsonData[i].sfid);
			getSfidDetails(jsonData[i].sfid+'#'+jsonData[i].orgRoleId);
			/*if($jq('#sfid').length > 0){
				$jq('#sfid').val(jsonData[i].sfid);
				getSfidDetails();
			}
			if($jq('#demandPurchaseLimit').length > 0)
				$jq('#demandPurchaseLimit').val(jsonData[i].demandPurchaseLimit);*/
			if($jq('#invHolderDetails').length > 0)
				$jq('#invHolderDetails').css('display','block');
			if($jq('#project').length > 0){
				if(jsonData[i].inventoryHolderType==30){
					$jq('#inventoryHolderType1').attr("checked","checked");
					$jq('#project').css("display","none");
				}else{
					$jq('#inventoryHolderType2').attr("checked","checked");
					$jq('#project').css("display","block");
				}
				$jq('#projectName').val(jsonData[i].projectName);				
			}
			
			orgroleid=jsonData[i].orgRoleId;
			invsfid=jsonData[i].sfid;
		}
	}
}
function editMMGAccHead(jsonData,id){
	for ( var i = 0; i < jsonData.length; i++) {
		if (jsonData[i].accId == id) {
			masterID=jsonData[i].accId;
			accHeadNum=jsonData[i].accountHeadNumber;
			if($jq('#departmentId').length > 0)
				$jq('#departmentId').val(jsonData[i].departmentId);
			if($jq('#accountHeadNumber').length > 0)
				$jq('#accountHeadNumber').val(jsonData[i].accountHeadNumber);
			$jq('#accountHeadNumber').attr("disabled","disabled");
			if($jq('#fundTypeId').length > 0)
				$jq('#fundTypeId').val(jsonData[i].fundTypeId);
			if($jq('#allottedFund').length > 0)
				$jq('#allottedFund').val(jsonData[i].allottedFund);
			if($jq('#qtyHeld').length > 0)
				$jq('#qtyHeld').val(jsonData[i].qtyHeld);
			if($jq('#qtyRequired').length > 0)
				$jq('#qtyRequired').val(jsonData[i].qtyRequired);
			if($jq('#consumedFund').length > 0)
				$jq('#consumedFund').val(jsonData[i].consumedFund);
			if($jq('#commitedFund').length > 0)
				$jq('#commitedFund').val(jsonData[i].commitedFund);
			if($jq('#year').length > 0)
				$jq('#year').val(jsonData[i].year);
			if (jsonData[i].description!="" &&  jsonData[i].description!=null) {
				$jq('#description').val(jsonData[i].description);
				$jq('#counter').val(500);
				$jq('#counter').val($jq('#counter').val() - jsonData[i].description.length);
			}
		
		}
	}
}
function textRequired(id) {
	if ($jq("#typeValue").val() == '') {
		//setTimeout('$jq("#typeValue").focus()', 1);
		alert('Please Enter Name');
		$jq("#typeValue").focus();
		return ;
	}
}
function lables(type) {
	var req="<span class='failure'>*</span>";
	if(type == 'taxType'){
		document.title='Tax Type Master';
		$jq('#headTitle').html('Create Tax Type');
		$jq('#labelType').html("Tax Type Name"+req);
		
	}else if(type == 'uom'){
		document.title='UOM Master';
		$jq('#headTitle').html('Create UOM');
		$jq('#labelType').html('UOM Name'+req);
		
	}else if(type == 'voucherType'){
		document.title='Voucher Type Master';
		$jq('#headTitle').html('Create Voucher Type');
		$jq('#labelType').html('Voucher Type'+req);
		
	}else if(type == 'itemCompany'){
		document.title='Item Company Master';
		$jq('#headTitle').html('Create Item Company');
		$jq('#labelType').html('Company Name'+req);
		
	}else if(type == 'itemCategory'){
		document.title='Item Category Master';
		$jq('#headTitle').html('Create Item Category');
		$jq('#labelType').html('Item Category Name'+req);
		
	}else if(type == 'itemSubCategory'){
		document.title='Item Sub Category Master';
		$jq('#headTitle').html('Create Item Sub Category');
		$jq('#labelType').html('Item Sub Category Name'+req);
		
	}else if(type == 'itemCode'){
		document.title='Item Code Master';
		$jq('#headTitle').html('Create Item Code');
		$jq('#labelType').html('Item Code Name'+req);
		
	}else if(type == 'itemSubCode'){
		document.title='Item Sub Code Master';
		$jq('#headTitle').html('Create Item Sub Code');
		$jq('#labelType').html('Item Sub Code Name'+req);
		
	}else if(type == 'material'){
		document.title='Material Master';
		$jq('#headTitle').html('Create Material');
		$jq('#labelType').html('Material Name'+req);
		
	}
}

function clearMMGMaster(type){
		editMaterial=false;
		//inventory holder
		orgroleid='';
		invsfid='';
		accHeadNum='';
		if(type=='inventHolder'){
			if($jq('#inventoryNo').length > 0)
				$jq('#inventoryNo').html(invNum);
			if($jq('#sfid').length > 0)
				$jq('#sfid').val('');	
			if($jq('#directorateName').length > 0)
				$jq('#directorateName').html('&nbsp;');
			if($jq('#divisionName').length > 0)
				$jq('#divisionName').html('&nbsp;');
			if($jq('#holderName').length > 0)
				$jq('#holderName').html('&nbsp;');
			if($jq('#designation').length > 0)
				$jq('#designation').html('&nbsp;');
			if($jq('#phone').length > 0)
				$jq('#phone').html('&nbsp;');
			if($jq('#demandPurchaseLimit').length > 0)
				$jq('#demandPurchaseLimit').val('');
			if($jq('#project').length > 0){
				$jq('#inventoryHolderType1').attr("checked","checked");
				$jq('#project').css("display","none");
				 autosugg=setSfidValues(sfidjson);
				 $jq("#sfidCode").html('<input type="text" name="sfid" id="sfidone"/>');
				 $jq("#sfidCode input").autoSuggest(autosugg.items, {selectedItemProp: "name", searchObjProps: "name",selectionLimit :1,resultsHighlight:false,selectedValuesProp:"value",resultClick:function(){getSfidDetails();}});
			}
		}else if(type=='accHead'){
			if($jq('#departmentId').length > 0)
				$jq('#departmentId').val('Select');
			if($jq('#accountHeadNumber').length > 0){
				$jq('#accountHeadNumber').val('');
				$jq('#accountHeadNumber').removeAttr('disabled');
			}
			if($jq('#fundTypeId').length > 0)
				$jq('#fundTypeId').val('Select');
			if($jq('#allottedFund').length > 0)
				$jq('#allottedFund').val('');
			if($jq('#qtyHeld').length > 0)
				$jq('#qtyHeld').val('');
			if($jq('#qtyRequired').length > 0)
				$jq('#qtyRequired').val('');
			if($jq('#consumedFund').length > 0)
				$jq('#consumedFund').val('');
			if($jq('#commitedFund').length > 0)
				$jq('#commitedFund').val('');
			if($jq('#year').length > 0)
				$jq('#year').val('');
			if($jq('#description').length > 0)
				$jq('#description').val('');
			if($jq('#searchWith').length > 0)
				$jq('#searchWith').val('Select');
			if($jq('#searchWithValue').length > 0)
				$jq('#searchWithValue').val('');
		}else{
			if($jq('#typeValue').length > 0)
				$jq('#typeValue').val('');
			if($jq('#description').length > 0)
				$jq('#description').val('');
			if($jq('#percentage').length > 0)
				$jq('#percentage').val('');
			if($jq('#counter').length > 0)
				$jq('#counter').val('500');
			//company
			if($jq('#companyCode').length > 0){
				$jq('#companyCode').val('');
				$jq('#companyCode').removeAttr('disabled');
			}
				//category
			if($jq('#categoryCode').length > 0){
			   $jq('#categoryCode').val('');
			   $jq('#categoryCode').removeAttr('disabled');
			}
			if($jq('#categoryId').length > 0)
				$jq('#categoryId').val('Select');
			//sub code
			if($jq('#subCode').length > 0)
				$jq('#subCode').val('');
			//sub category
			if($jq('#subCategoryId').length > 0)
				$jq('#subCategoryId').val('');
			if($jq('#subCategoryCode').length > 0){
				$jq('#subCategoryCode').empty().append('<option selected="selected" value="">Select</option>');
				$jq('#subCategoryCode').removeAttr('disabled');
			}
			//itemcode
			if($jq('#itemCode').length > 0){
				$jq('#itemCode').empty().append('<option selected="selected" value="">Select</option>');
				$jq('#itemCode').removeAttr('disabled');
			}
				//itemSubCode
			if($jq('#itemSubCode').length > 0){
				$jq('#itemSubCode').empty().append('<option selected="selected" value="">Select</option>');
				$jq('#itemSubCode').removeAttr('disabled');
			}
			//material
			if($jq('#itemCode').length > 0)
				$jq('#itemCode').val('');
			if($jq('#subCategoryCode').length > 0)
				$jq('#subCategoryCode').val('');
			if($jq('#itemCodeId').length > 0)
				$jq('#itemCodeId').val('');
			if($jq('#itemSubCode').length > 0)
				$jq('#itemSubCode').val('');
			if($jq('#contract').length > 0){
				if($jq('#unitRate').length > 0)
					$jq('#unitRate').val('');
				$jq("#contract").removeAttr('disabled');
			}
			if($jq('#rcFlag').length > 0){
				$jq('#rcFlag').val('Select');
				$jq("#contract").css("display","none");
			}
			if($jq('#consumableFlag').length > 0)
				$jq('#consumableFlag').val('Select');
			if($jq('#materialCode').length > 0)
				$jq('#materialCode').html('');
			if($jq('#uom').length > 0)
				$jq('#uom').val('Select');
			if($jq('#materialName').length > 0)
				$jq('#materialName').val('');
		}
		masterID="";
		materialCode='';
}
function textCounter(e,des,tbox,maxlimit){
	   var code = (e.keyCode ? e.keyCode : e.which);
	   if (code == 8){
		   if((des.val().length-1)>=0)
			   tbox.val(maxlimit-(des.val().length-1));
		   return true;
	   }else{
		   if ((des.val().length)+1 > maxlimit) {
			   des.val(des.val().substring(0, maxlimit));
				// alert( 'Textarea value can only be 10 characters in length.' );
				return false;
			} else {
				tbox.val(maxlimit - ((des.val().length)+1));
				return true;
			}
	   }
}
function manageMMGMaster(type) {
	var errorMessage = "";
	var status = true;
	
	if( $jq('#mmgMaster').valid() == true) {
		status = true;
		}else{
			status = false;
		}
	
	
	if (status) {
		if(type=='material'){
			$jq('#type').val(type);
			if(editMaterial){
				$jq.post( 
					    'mmgMaster.htm?param=manage&id='+masterID+'&materialCode='+$jq('#materialCode').html()+'&categoryCode='+escape(categoryCode)+'&subCategoryCode='+escape(subCategoryCode)+'&itemCode='+escape(itemCode)+'&itemSubCode='+escape(subCategoryCode)+'&companyCode='+escape(companyCode)+'&'+dispUrl,   
						$jq('#mmgMaster').serialize(), 
						function(data){ 
							$jq('#result').html(data);
							clearMMGMaster(type);
						} 
					); 
			}else{
			$jq.post( 
				    'mmgMaster.htm?param=manage&id='+masterID+'&materialCode='+$jq('#materialCode').html()+'&categoryCode='+escape(categoryCode)+'&subCategoryCode='+escape(subCategoryCode)+'&'+dispUrl,   
				$jq('#mmgMaster').serialize(), 
				function(data){ 
					$jq('#result').html(data);
					clearMMGMaster(type);
				} 
			); 
		 }
		}else if(type=='inventHolder'){
			$jq('#type').val(type);
			var value='';
			if($jq(".as-values").val()!=undefined){
				value=$jq(".as-values").val().split(",")[$jq(".as-values").val().split(",").length-2];
			}
			if(value.split("#")[1]!=undefined){
				orgroleid=value.split("#")[1];
			}
			if(value.split("#")[0]!=undefined && value.split("#")[0]!=""){
				invsfid=value.split("#")[0];
			}
			$jq.post( 
				    'mmgMaster.htm?param=manage&id='+masterID+'&inventoryNo='+$jq('#inventoryNo').html()+'&sfid='+invsfid+'&orgRoleId='+orgroleid+'&'+dispUrl,   
				$jq('#mmgMaster').serialize(), 
				function(data){ 
					$jq('#result').html(data);
					clearMMGMaster(type);
				} 
			); 
		}else if(type=='accHead'){
			$jq('#name').val($jq('#typeValue').val());
			$jq('#type').val(type);
			if(accHeadNum!=''){
				$jq.post( 
					    'mmgMaster.htm?param=manage&id='+masterID+'&accountHeadNumber='+accHeadNum+'&'+dispUrl, 
						$jq('#mmgMaster').serialize(), 
						function(data){ 
							$jq('#result').html(data);
							clearMMGMaster(type);
						} 
					); 
			}else{
				$jq.post( 
					    'mmgMaster.htm?param=manage&id='+masterID+'&'+dispUrl, 
						$jq('#mmgMaster').serialize(), 
						function(data){ 
							$jq('#result').html(data);
							clearMMGMaster(type);
						} 
					); 
			}
		}else{
			$jq('#name').val($jq('#typeValue').val());
			$jq('#type').val(type);
			$jq.post( 
				    'mmgMaster.htm?param=manage&id='+masterID+'&'+dispUrl, 
				$jq('#mmgMaster').serialize(), 
				function(data){ 
					$jq('#result').html(data);
					clearMMGMaster(type);
				} 
			);
		}
	} 
	

}
function deleteMMGMaster(id, type) {
	var del = confirm("Do you want to delete this item?");
	if (del == true) {
		$jq.post( 
			    'mmgMaster.htm?param=manage&id='+masterID+'&'+dispUrl,  
                 function(data){ 
                	 $jq('#result').html(data);
                	 clearMMGMaster();
               } 
          ); 
	}
}
function setInventoryHolderDetails(inventoryDetails) {
		$jq("#holderName").text(inventoryDetails.holderName);
		$jq("#directorateName").text(inventoryDetails.directorateName);
		$jq("#divisionName").text(inventoryDetails.divisionName);
		$jq("#groupDemandNo").html(inventoryDetails.demandNo);
}
function setAccountHeadNumbers(json) {
	var selectobj=$jq('#accountHeadId');
	var length = selectobj[0].options.length;
	for ( var opt=0;opt<length;opt++) {
		var theOption = document.createElement("OPTION");
		var theText = document.createTextNode(json[opt].accountHeadNumber+" ("+json[opt].fundTypeName+")");
		theOption.appendChild(theText);
		theOption.setAttribute("value", json[opt].accId);
		selectobj.append(theOption);
	}
}
function getInventoryHolderDetails(inventoryno){
	var invno=$jq('#inventoryNo')[0].options[$jq('#inventoryNo')[0].selectedIndex].value;
	if(inventoryno!=null && inventoryno!=undefined)
		invno=inventoryno;
	if(invno!="")
	{
		$jq.post( 
	            'raiseDemand.htm?param=GetInventoryHolder&inventoryNo='+invno, 
	           function(data){ 
	            	 $jq('#result').html(data);
	           } 
	      );
	}
}
function addItem(type) {
	var status=true;
	var errorMessage="";
	var json={};
	var id="";
	if(type!=null && type=="request"){
		json["materialCode"]=$jq("#amountType").val().split("#")[1];
		json["amountType"]=$jq("#amountType").val().split("#")[0];
		json["unitRate"]="";
		json["qty"]="";
		json["uom"]="";
		json["uomConvert"]="";
		json["cncConvert"]="";
		id="workflowmap";
	}else{
		if($jq('#search').length > 0 && $jq('#search').val()=='Select'){
			status=false;
			errorMessage+="Please Select Search Type\n";
			$jq('#search').focus();
		}else{
			var materialCode="";
			if($jq("#demamdItemCode")!=null && $jq("#demamdItemCode").val()!=undefined)
				materialCode=$jq("#demamdItemCode").val();
			else
			materialCode=$jq(".as-values").val().split(",")[$jq(".as-values").val().split(",").length-2];
			if(materialCode==undefined) {
				status=false;
				if($jq('#search').val()=="itemName")
					errorMessage+="Please Enter materialName\n";
				else if($jq('#search').val()=="itemCode")
					errorMessage+="Please Enter materialCode\n";
			}
			json["materialCode"]=materialCode;
			json["unitRate"]=$jq("#unitRate").val();
			json["qty"]=$jq("#qty").val();
			json["uomConvert"]="";
			json["cncConvert"]="";
			if($jq('#uomConvert').length > 0 && $jq('#voucherTypeId option:selected').val()=='11'){

				if($jq("#uomConvert option:selected").text()!='Select'){
					json["uomConvert"]=$jq("#uomConvert option:selected").text();
				}else if($jq("#cncConvert").val()=='Select'){
					status=false;
					errorMessage+="Please Select UOM(To be Convert)\n";
				}else{
					json["uomConvert"]="";
				}
			}
			if($jq('#cncConvert').length > 0 && $jq('#voucherTypeId option:selected').val()=='11'){
				if($jq("#cncConvert").val()!='Select'){
					json["cncConvert"]=$jq("#cncConvert").val();
				}else if($jq("#uomConvert option:selected").text()=='Select'){
					status=false;
					errorMessage+="Please Select C/NC(To be Convert)\n";
				}else{
					json["cncConvert"]="";
				}
			}
			//json["cncConvert"]=$jq("#cncConvert").val();
			for ( var i = 0; i < itemsjson.length; i++) {
				if (itemsjson[i].materialCode == json.materialCode) {
					json["uom"]=itemsjson[i].uomName;
					if(type!="voucher")
						json["uomConvert"]=itemsjson[i].uom;
				}
			}
			
			json["amountType"]="";
			json["demandNo"]=$jq("#groupDemandNo").text();
			if(type!=null && type=="voucher")
				id="mmgVoucher";
			else
				id="demandMaster";
		}
		if($jq('#voucherTypeId').length > 0){
			if($jq('#voucherTypeId option:selected').val()!='11'){
				if($jq('#qty').val()=="") {
					status=false;
					errorMessage+="Please Enter qty\n";
				}
				if($jq('#unitRate').val()=="") {
					status=false;
					errorMessage+="Please Enter unitRate\n";
				}
			}
		}else{
			if($jq('#qty').val()=="") {
				status=false;
				errorMessage+="Please Enter qty\n";
			}
			if($jq('#unitRate').val()=="") {
				status=false;
				errorMessage+="Please Enter unitRate\n";
			}
		}
	}
	if(status){
		document.forms[0].jsonValue.value=JSON.stringify(json);
		document.forms[0].param.value='addItem';
		$jq.post( 
		    'raiseDemand.htm',$jq('#'+id).serialize(),  
		    function(data){ 
		     $jq('#demandList').html(data);
		   } 
		);
	}else
		alert(errorMessage);
	
}
function searchValues(type) {
	var status=true;
	var errorMessage="";
	if($jq('#search').val()!="select"){
		if(type!=null && type=="voucher"){
			if($jq('#voucherTypeId').length > 0 && $jq('#voucherTypeId').val()=='Select'){
				status=false;
				errorMessage+="Please Select Voucher Type\n";
				$jq('#search').val('Select');
				$jq('#voucherTypeId').focus();
			}
			if($jq('#inventoryNo').length > 0 && $jq('#inventoryNo').val()=='Select'){
				status=false;
				errorMessage+="Please Select Inventory Number\n";
				$jq('#search').val('Select');
				$jq('#inventoryNo').focus();
			}
			
		}
		if(status){
			if(type!=null && type=="voucher"){
				document.forms[0].param.value='searchValues';
				$jq.post( 
				           'raiseDemand.htm',$jq('#mmgVoucher').serialize(),  
				           function(data){ 
				            	 $jq('#result').html(data);
				            	 } 
				      );
			}else{
				$jq.post( 
			           'raiseDemand.htm?param=searchValues&search='+$jq('#search').val(),  
			           function(data){ 
			            	 $jq('#result').html(data);
			            	 } 
			      );
			}
			
		}else
			alert(errorMessage);
	}
	
}
function setMaterials(materialJson,searchType) {
	$jq('#materialCode').children().remove();
	$jq('#materialCode').append($jq("<option></option>").attr("value","select").text("Select"));
	if ($jq("#materialCode option:selected")!= "select") {
		for ( var i = 0; i < materialJson.length; i++) {
			if(searchType=="itemName")
			{
				$jq('#materialCode').append($jq("<option></option>").attr("value",materialJson[i].materialCode).text(materialJson[i].materialName)); 
			}else
			{
				$jq('#materialCode').append($jq("<option></option>").attr("value",materialJson[i].materialCode).text(materialJson[i].materialCode));
			}			
		}
		$jq("#materialCode").val("select");
	}
}
function setMaterialValues(materialCode,data) {
	for ( var i = 0; i < itemsjson.length; i++) {
		var value="";
		if(materialCode!=null && materialCode!="undefined")
			value=materialCode;
		else
			value=$jq(".as-values").val().split(",")[$jq(".as-values").val().split(",").length-2];
		
			if(data!=null && data!=undefined){
					for(var j=0;j<data.length;j++){
						if(materialCode==data[j].materialCode){
							$jq("#search option:selected").val("itemName");
							$jq("#materialCode").html('<input type="text" name="materialCode" id="demamdItemCode" readonly/>');
							$jq("#demamdItemCode").val(data[j].materialCode);
							if(itemsjson[i].rcFlag=="1")
							{
								$jq("#unitRate").attr("readonly","true");
								$jq("#unitRate").val(itemsjson[i].unitRate);
							}else{
								$jq("#unitRate").attr("readonly","");
								$jq("#unitRate").val(data[j].unitRate);
							}
							$jq("#qty").val(data[j].qty);
						}
					}
					
				}else{
				
					if(itemsjson[i].rcFlag=="1")
					{
						$jq("#unitRate").attr("readonly","true");
						$jq("#unitRate").val(itemsjson[i].unitRate);
					}else
					{
						$jq("#unitRate").attr("readonly","");
						$jq("#unitRate").val("");
					}
					if($jq('#uomCnc').is(':visible')==true){
						$jq('#uomLable').html(itemsjson[i].uomName);
						$jq('#cncLable').html(itemsjson[i].consumableFlag);
					}
				}
			if(itemsjson[i].materialCode==value) {
				if($jq("#search option:selected").val()=="itemName")
				{
					$jq("#materialLabel").html("Material Code");
					$jq("#materialdesc").html(itemsjson[i].materialCode);
				}
				else
				{
					$jq("#materialLabel").html("Material Name");
					$jq("#materialdesc").html(itemsjson[i].materialName);
				}
			}		
		}
}
function deleteItem(materialCode,type,divid) {
	var json={};
	json["materialCode"]=materialCode;
	json["unitRate"]="";
	json["qty"]="";
	json["uom"]="";
	document.forms[0].jsonValue.value=JSON.stringify(json);
	var id="";
	if(type!=null && type=="request"){
		id="workflowmap";
		divid=divid;
		var len=$jq("#demandMaterial")[0].rows.length;
		if(len==2){
			alert("sorry you cannot delete this item");
			return;			
		}
	}
	else
	{
		id="demandMaster";
		divid="demandList";
	}
	$jq.post( 
	           'raiseDemand.htm?param=deleteItem',$jq('#'+id).serialize(),  
	           function(data){ 
	            	 $jq('#'+divid).html(data);
	           } 
	      );
}
function enableContractRate(){
	if($jq("#rcFlag option:selected").text()=='Yes'){
		$jq("#contract").css("display","block");
	}else{
		$jq("#contract").css("display","none");
	}
}
function getMaterialCode(id){
	
	var errMessage='';
	var strFlag=true;
	categoryCode=$jq('#categoryCode option:selected').val();
	subCategoryCode=$jq('#subCategoryCode option:selected').val();
	if(id=='categoryCode'){
		$jq('#subCategoryCode').empty().append('<option selected="selected" value="">Select</option>');
		$jq('#itemCode').empty().append('<option selected="selected" value="">Select</option>');
		$jq('#itemSubCode').empty().append('<option selected="selected" value="">Select</option>');
		
	if ($jq("#categoryCode option:selected").text()!='Select'){
		$jq('#subCategoryCode').empty().append('<option selected="selected" value="">Select</option>');
		for ( var i = 0; i < JsonParentList.length; i++){
			if (JsonParentList[i].categoryId== $jq("#categoryCode").val().split('#')[0]){
				$jq('#subCategoryCode').append($jq('<option></option>').val(JsonParentList[i].id+"#"+JsonParentList[i].subCategoryCode).html(JsonParentList[i].name));
			 }
	     }
	 }
	}
	if(id=='subCategoryCode'){
		if ($jq("#subCategoryCode option:selected").text()!='Select'){
			$jq('#itemCode').empty().append('<option selected="selected" value="">Select</option>');
		for ( var i = 0; i < JsonItemCodeList.length; i++){
			if (JsonItemCodeList[i].subCategoryId== $jq("#subCategoryCode").val().split('#')[0]){
				$jq('#itemCode').append($jq('<option></option>').val(JsonItemCodeList[i].id+"#"+JsonItemCodeList[i].itemCode).html(JsonItemCodeList[i].name));
			 }
		}	
	 }
	}
	if(id=='itemCode'){
		if ($jq("#itemCode option:selected").text()!='Select'){
			$jq('#itemSubCode').empty().append('<option selected="selected" value="">Select</option>');
		for ( var i = 0; i < JsonSubCodeList.length; i++){
			if (JsonSubCodeList[i].itemCodeId== $jq("#itemCode").val().split('#')[0]){
				$jq('#itemSubCode').append( $jq('<option></option>').val(JsonSubCodeList[i].id+"#"+JsonSubCodeList[i].itemSubCode).html(JsonSubCodeList[i].name));
			 }
		}
		$jq('#categoryCode').attr("disabled","disabled");
		$jq('#subCategoryCode').attr("disabled","disabled");
		//$jq('#itemCode').attr("disabled","disabled");
	}else{
		$jq('#itemSubCode').val("");
		$jq('#companyCode').val("");
		/*materialCode=$jq.trim($jq('#categoryCode').val().split('#')[1])+$jq.trim($jq('#subCategoryCode').val().split('#')[1])+'000000000';
		$jq('#materialCode').html(materialCode);*/
	}
	}
	if(materialCode==''){
		
		if($jq("#itemCode option:selected").text()!='Select'){
			if($jq("#categoryCode option:selected").text()=='Select'){
				errMessage="Please Select Item Category\n";
				strFlag=false;
				$jq('#categoryCode').focus();
				$jq('#categoryCode').val('Select');
				$jq('#itemCode').val('Select');
			}
			if($jq("#subCategoryCode option:selected").text()=='Select'){
				errMessage+="Please Select Item Sub Category\n";
				if(strFlag){
					$jq('#subCategoryCode').focus();
					$jq('#itemCode').val('Select');
				}
				strFlag=false;
			}
			if(strFlag){
				
				materialCode=$jq.trim($jq('#categoryCode').val().split('#')[1])+$jq.trim($jq('#subCategoryCode').val().split('#')[1])+$jq.trim($jq('#itemCode').val().split('#')[1])+'00000';
				$jq('#materialCode').html(materialCode);
				
			}else{
				alert(errMessage);
			}
		}else{
			if($jq("#subCategoryCode option:selected").text()!='Select'){
				if($jq("#categoryCode option:selected").text()=='Select'){
					alert("Please Select Item Category\n");
					$jq('#categoryCode').focus();
					$jq('#categoryCode').val('Select');
					$jq('#itemCode').val('Select');
				}
			}
		}
	}else{

			//var subStr=materialCode.substr(10,15);
			if($jq("#itemCode option:selected").text()!='Select'){
				materialCode=$jq.trim($jq('#categoryCode').val().split('#')[1])+$jq.trim($jq('#subCategoryCode').val().split('#')[1])+$jq.trim($jq('#itemCode').val().split('#')[1]);
			}else{
				materialCode=$jq.trim($jq('#categoryCode').val().split('#')[1])+$jq.trim($jq('#subCategoryCode').val().split('#')[1])+'0000';
			}
			if($jq("#itemSubCode option:selected").text()!='Select'){
				materialCode=materialCode.substr(0,10)+$jq.trim($jq('#itemSubCode').val().split('#')[1])+materialCode.substr(13,15);
			}else{
				materialCode=materialCode.substr(0,10)+"000"+materialCode.substr(13,15);
			}
			if($jq("#companyCode option:selected").text()!='Select'){
				materialCode=materialCode.substr(0,13)+$jq.trim($jq('#companyCode').val().split('#')[1]);
			}else{
				materialCode=materialCode.substr(0,13)+"00";
			}
			//materialCode=materialCode+subStr;
			$jq('#materialCode').html(materialCode);
//		}
	}
	
		
}
function getItemSubCode(){
	
	if($jq("#itemCode option:selected").text()!='Select'){
		if($jq("#itemSubCode option:selected").text()!='Select'){
			materialCode=materialCode.substr(0,10)+$jq.trim($jq('#itemSubCode').val().split('#')[1])+materialCode.substr(13,15);
			$jq('#materialCode').html(materialCode);
		}
		else
		{   
			if($jq('#companyCode').val()==''){
			company_Code='00';
			}
			materialCode=$jq.trim($jq('#categoryCode').val().split('#')[1])+$jq.trim($jq('#subCategoryCode').val().split('#')[1])+$jq.trim($jq('#itemCode').val().split('#')[1])+'000'+company_Code;
			$jq('#materialCode').html(materialCode);
		}
	}else{
		$jq('#itemSubCode').val("");
		alert("Please Select Item Code");
		$jq('#itemCode').focus();
	}
	
}
function getCompanyCode(){
	if($jq("#itemCode option:selected").text()!='Select'){
		if($jq("#companyCode option:selected").text()!='Select'){
			company_Code=$jq.trim($jq('#companyCode').val().split('#')[1])
			materialCode=materialCode.substr(0,13)+$jq.trim($jq('#companyCode').val().split('#')[1]);
			$jq('#materialCode').html(materialCode);
		}else{
			materialCode=materialCode.substr(0,13)+"00";
			$jq('#materialCode').html(materialCode);
		}
	}else{
		$jq('#companyCode').val("");
		alert("Please Select Item Code");
		$jq('#itemCode').focus();
	}
}
function getSfidDetails(type){
	var value='';
	if(type!=undefined){
		value=type;
	}else{
		value=$jq(".as-values").val().split(",")[$jq(".as-values").val().split(",").length-2];
	}
	var orgRoleId='';
	if(value.split('#')[1]!=undefined)
		orgRoleId=value.split('#')[1];
	if(value!=''){
		
		$jq.post( 
			'mmgMaster.htm?param=getSfidDetails&mmgsfid='+value.split('#')[0]+'&orgRoleId='+orgRoleId,  
			function(data){ 
			  
			  $jq('#invHolderDetails').html(data);
			  if($jq('#invHolderDetails').length > 0)
					$jq('#invHolderDetails').css('display','block');
			 } 
		);
	}else{
		if($jq('#invHolderDetails').length > 0)
			$jq('#invHolderDetails').css('display','none');
	}
}
function demandRaise() {
	if( $jq('#demandMaster').valid() == true) {
		if($jq('#demandList').length >0) {	
		document.forms[0].param.value="submitRequest";
		document.forms[0].type.value="Demand";
		$jq.post( 
		           'raiseDemand.htm?search='+$jq('#search').val()+'&demandNo='+escape($jq("#groupDemandNo").html()),
		           $jq('#demandMaster').serialize(), 
		           function(data){ 
		            	 $jq('#result').html(data);
		            	 } 
		      );
		}else
		{
			alert("Please add one material for demand raise");
			return;
		}
	}
}
function setNameValuePairs(materialjson) {
	var autojson={};
	var data=new Array();
	var len=0;
	for(var key in materialjson)
	{
		len++;
	}
	for(var i=0;i<len;i++)
	{
		var innerjson={};
		innerjson['value'] = materialjson[i].materialCode;
		innerjson['name'] = materialjson[i].materialName;
		data[i] = innerjson;
	}
	autojson["items"]=data;
	return autojson;
}
function setSfidValues(sfidjson) {
	var autojson={};
	var data=new Array();
	var len=0;
	for(var key in sfidjson)
	{
		len++;
	}
	for(var i=0;i<len;i++)
	{
		var innerjson={};
		innerjson['value'] = sfidjson[i].key;
		innerjson['name'] = sfidjson[i].name;
		data[i] = innerjson;
	}
	autojson["items"]=data;
	return autojson;
}
function inventoryFor(){
	if($jq('#inventoryHolderType1').is(':checked')==true){
		$jq('#project').css("display","none");
	}
	if($jq('#inventoryHolderType2').is(':checked')==true){
		$jq('#project').css("display","block");
	}
}

function accountHeadSearch(type){
	if($jq('#searchWith option:selected').text()!='Select'){
		if($jq('#searchWithValue').val()!=''){
			$jq('#param').val('accHeadSearch');
			$jq('#type').val(type);
			$jq.post( 
			    'mmgMaster.htm', 
				$jq('#mmgMaster').serialize(), 
				function(data){ 
					$jq('#result').html(data);
					
				} 
			); 
		}else{
			//alert("Please Enter Search Value");
			$jq('#searchWithValue').focus();
		}
	}else{
		alert("Please select Search Type");
		$jq('#searchWith').focus();
	}
}
function getVoucherNames(){
	if($jq('#voucherTypeId option:selected').text()!='Select' && $jq('#voucherTypeId option:selected').text()!='Cash Purchase Receipt Voucher'){
		
			$jq('#param').val('getVouchers');
			$jq('#type').val($jq('#voucherTypeId option:selected').text().replace(/ /g,''));
			$jq.post( 
			    'mmgVoucher.htm', 
			    $jq('#mmgVoucher').serialize(), 
				function(data){ 
					$jq('#voucherNames').html(data);
					
				} 
			); 
		}	
}
function genarateVoucher(){
	
var status = true;
	
	if( $jq('#mmgVoucher').valid() == true) {
		status = true;
		}else{
			status = false;
		}
	
	
	if (status) {
	document.forms[0].param.value="submitRequest";
	$jq.post( 
	           'mmgVoucher.htm?voucherId='+ $jq('#voucherId').html() +"&voucherDate="+$jq('#voucherDate').html(),
	           $jq('#mmgVoucher').serialize(), 
	           function(data){ 
	            	 $jq('#result').html(data);
	           } 
	      );
	}
}
function clearSearch(){
	$jq('#searchWith').val('Select');
	$jq('#searchWithValue').val('');

}
function saveDraft() {
	document.forms[0].param.value="saveDraft";
	 $jq.post( 
	           'raiseDemand.htm?demandNo='+escape($jq("#groupDemandNo").html()),$jq('#demandMaster').serialize(), 
	           function(data){ 
	            	 		$jq('#drafts').html(data);
	            	      } 
	         );
}
function getDemandDetails() {
	var demandNo=$jq('#demandNo option:selected').html();
	if(demandNo!="") {
		$jq.post( 
		           'securityDetails.htm?param=getDemandDetails&demandNo='+escape(demandNo), 
		           function(data){
		        	   $jq('#demandList').html(data);
		        	   
		        	  } 
		         );
	}
}
function getDraftDetails(id) {
		$jq.post( 
				'raiseDemand.htm?param=draftDetails&demandNo='+escape($jq("#"+id).html()), 
	           function(data){
	        	   $jq('#demandList').html(data);
	        	 } 
	         );
}
function previewVoucher(){
	var previewJson={
			"voucherTypeId" : $jq('#voucherTypeId').val(),
            "voucherTypeName"  : $jq('#voucherTypeId option:selected').text(),
            "voucherId"       : $jq('#voucherId').html(), 
            "voucherDate" : $jq('#voucherDate').html(),
            "inventoryNo"  : $jq('#inventoryNo option:selected').text(),
            "labName"       : $jq('#labName').val(),
            "location"       : $jq('#location').val(), 
            "referenceNo" : $jq('#referenceNo').val(),
            "purpose"  : $jq('#purpose').val(),
            "postingDate"   : $jq('#postingDate').html(),
            "periodFrom" : $jq('#periodFrom').val(),
            "periodTo" : $jq('#periodTo').val(),
            "toInventory" : $jq('#toInventory option:selected').text(),
            "workDetails" : $jq('#workDetails').val(),
            "workAmount" : $jq('#workAmount').val(),
            "divControlNum" : $jq('#divControlNum').val()
            };
	
	window.open('mmgVoucher.htm?param=voucherPreview&previewJson='+JSON.stringify(previewJson),'preview','fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	
}
function createVoucherPreview(){ 
	var totalQty=0;
	if(JsonDemandList!=null)
	for(var i=0;i<JsonDemandList.length;i++){
		//totalQty=parseInt(totalQty);
		totalQty+=parseInt(JsonDemandList[i].qty);
	}
	var myTable = '' ;
	myTable += '<br/><br/><table id="myTable" cellspacing=0 cellpadding=0 border=0 width=100%>' ;
	myTable += '<tr align=center><th><font size=5>ADVANCED SYSTEMS LABORATORY, HYDERABAD 500 058</font></th></tr>';
	myTable += '<tr align=center><th><font size=5>'+previewJson.voucherTypeName+'</font></th></tr>';
	
	myTable += '<br/><br/><tr><table cellspacing=0 cellpadding=1 border=0 width=100%>';
	if(previewJson.voucherTypeId=='11' || previewJson.voucherTypeId=='8'){
		myTable += '<tr><td>VOUCHER ID</td><td>:&nbsp;<input type="text" readonly="true" value="'+previewJson.voucherTypeId+'"></input></td></tr>';
	}else
		myTable += '<tr><td>VOUCHER ID</td><td>:&nbsp;<input type="text" readonly="true" value="'+previewJson.voucherTypeId+'"></input></td>&nbsp;&nbsp;<td>NO OF ITEMS IN A VOUCHER</td><td>:&nbsp;<input type="text" readonly="true" value="'+totalQty+'"></input></td></tr>';
	if(previewJson.voucherTypeId=='5'){
		myTable += '<tr><td>VOUCHER NO</td><td>:&nbsp;<input type="text" readonly="true" value="'+previewJson.voucherId+'"></input></td>&nbsp;&nbsp;<td>VOUCHER DATE</td><td>:&nbsp;<input type="text" readonly="true" value="'+previewJson.voucherDate+'"></input></td></tr><tr><td>FROM INVENTORY NUMBER</td><td>:&nbsp;<input type="text" readonly="true" value="'+previewJson.inventoryNo+'"></input></td>';
		myTable += '&nbsp;&nbsp;<td>TO INVENTORY NUMBER</td><td>:&nbsp;<input type="text" readonly="true" value="'+previewJson.toInventory+'"></input></td></tr>';
		myTable += '</table></tr>';
	}else{
		myTable += '<tr><td>VOUCHER NO</td><td>:&nbsp;<input type="text" readonly="true" value="'+previewJson.voucherId+'"></input></td>&nbsp;&nbsp;<td>VOUCHER DATE</td><td>:&nbsp;<input type="text" readonly="true" value="'+previewJson.voucherDate+'"></input></td></tr><tr><td>INVENTORY NUMBER</td><td>:&nbsp;<input type="text" readonly="true" value="'+previewJson.inventoryNo+'"></input></td>';
		myTable += '&nbsp;&nbsp;<td>COST CENTER</td><td>:&nbsp;<input type="text" readonly="true" value=""></input></td></tr><tr><td>JOB NO</td><td>:&nbsp;<input type="text" readonly="true" value=""></input></td></tr>';
		myTable += '</table></tr>';
	}
	
	myTable += '<br/><br/><tr><table cellspacing=0 cellpadding=0 border=1 width=100%>';
	if(previewJson.voucherTypeId=='11'){
		myTable += '<tr><th>SL.NO</th><th>ITEM CODE</th><th>DESCRIPTION</th><th>UOM(Present)</th><th>UOM(To be convert)</th><th>C/NC(Present)</th><th>C/NC(To be convert)</th><th>REMARKS</th></tr>';
	}else if(previewJson.voucherTypeId=='8'){
		myTable += '<tr><th width="50%">Work Details</th><th width="20%">Work Amount</th><th width="30%">Division Control Number</th></tr>';
		myTable +='<tr><td>'+previewJson.workDetails+'</td><td>'+previewJson.workAmount+'</td><td>'+previewJson.divControlNum+'</td></tr>';
	}else
		myTable += '<tr><th>SL.NO</th><th>ITEM CODE</th><th>CHDG</th><th>DESCRIPTION</th><th>RATE</th><th>UOM</th><th>QUANTITY</th><th>VALUE</th><th>REMARKS</th></tr>';
	if(JsonDemandList!=null)
	for(var i=0;i<JsonDemandList.length;i++){
		if(previewJson.voucherTypeId=='11'){
			myTable +='<tr><td>'+(i+1)+'</td><td>'+JsonDemandList[i].materialCode+'</td><td>'+JsonDemandList[i].description+'</td><td>'+JsonDemandList[i].uom+'</td><td>'+JsonDemandList[i].uomConvert+'</td><td>'+JsonDemandList[i].cflag+'</td><td>'+JsonDemandList[i].cncConvert+'</td><td></td></tr>';
		}else
			myTable +='<tr><td>'+(i+1)+'</td><td>'+JsonDemandList[i].materialCode+'</td><td></td><td>'+JsonDemandList[i].description+'</td><td>'+JsonDemandList[i].unitRate+'</td><td>'+JsonDemandList[i].uom+'</td><td>'+JsonDemandList[i].qty+'</td><td>'+(JsonDemandList[i].unitRate*JsonDemandList[i].qty)+'</td><td></td></tr>';
	}
	
	myTable += '</table></tr>';
	if(previewJson.voucherTypeId=='7'){
		myTable += '</table><br/><table><tr><td><table><tr><td width="75%">Articles listed above have been issued to</td><td></td></tr><br/><tr><td colspan="2" style="border-bottom: 2px dotted #000000;"></td></tr><br/><tr><td colspan="2" align="left">in complience with</td></tr><br/><tr><td colspan="2" style="border-bottom: 2px dotted #000000;"></td></tr><br/><tr><td></td><td align="center">Issueing Officer</td></tr></table></td>';
		myTable += '<td><table><tr><td width="75%">Articles listed above have been received from</td><td></td></tr><br/><tr><td colspan="2" style="border-bottom: 2px dotted #000000;"></td></tr><br/><tr><td colspan="2" align="left">in complience with</td></tr><br/><tr><td colspan="2" style="border-bottom: 2px dotted #000000;"></td></tr><br/><tr><td></td><td align="center">Receiving Officer</td></tr></table></td></tr></table>';
		myTable += '<br/><div style="border-bottom:1px solid gray;"></div>';
		myTable += '<table width="100%"><tr><td width="40%" valign="top"><table><tr><td>Store:Posted in Ledger by</td><td width="80%" style="border-bottom: 2px dotted #000000;"></td></tr><br/><tr><td>Posted in Stores Inventory By</td><td style="border-bottom: 2px dotted #000000;"></td></tr></table></td><td width="1" bgcolor="#000000"><BR></td><td width="30%" valign="top"><table><tr><td>EDP Batch No</td></tr></table></td><td width="1" bgcolor="#000000"><BR></td><td width="30%"><table><tr><td>Audit</td></tr></table></td></tr></table>';
	}else if(previewJson.voucherTypeId=='9' || previewJson.voucherTypeId=='10'){
		myTable += '<br/><table><tr><td colspan="4">Certified that the above items have used / expended during</td></tr><tr><td>the period from </td><td style="border-bottom: 2px dotted #000000;width:100px">'+previewJson.periodFrom+'</td><td> to </td><td style="border-bottom: 2px dotted #000000;width:100px">'+previewJson.periodTo+'</td></tr></table>';
		myTable += '<br/><div style="margin-left:40%;"><b>Inventory Holer</b></div>';
		myTable += '<br/><div style="border-bottom:1px solid gray;"></div>';
		myTable += '<br/><div style="margin-left:36%;">Entered in Budget Register</div>';
		myTable += '<br/><table width="100%"><tr><td width="25%">Posted in Ledger</td><td width="25%">Ledger Keeper</td><td width="25%">I / C Budget</td><td width="25%">EDP Batch No</td></tr></table>';
	}else if(previewJson.voucherTypeId=='5'){
		myTable += '<br/><table><tr><td colspan="2">Certified that the above items have been trnsferred </td><td width="40%" align="right">Posted in Ledger</td><td style="border-bottom: 2px dotted #000000;width:139px"></tr><tr><td>To </td><td style="border-bottom: 2px dotted #000000;width:300px"></td><td></td><td></td></tr></table>';
		myTable += '<br/><div style="margin-left:21%;">Signature&nbsp;:</div>';
		myTable += '<div style="margin-left:17%;">Transferred by&nbsp;:</div>';
		myTable += '<br/><div style="border-bottom:1px solid gray;"></div>';
		myTable += '<br/><table><tr><td colspan="2">Certified that the above items have been received by me, </td><td width="48%" align="right">Entered in Budget Register</td></tr><br/><tr><td></td><td align="center">Signature&nbsp;:</td><td width="20%" align="right">Signature</td></tr><tr><td colspan="2" align="center">Received by&nbsp;:</td></tr></table>';
		
	}else if(previewJson.voucherTypeId=='11'){
		myTable += '<br/><div>Certified that the above mentioned items have changed UOM/CNC</div><br/>';
		myTable += '<br/><div style="margin-left:40%;"><b>Inventory Holer</b></div>';
		myTable += '<br/><div style="border-bottom:1px solid gray;"></div>';
		myTable += '<br/><div style="margin-left:36%;">Entered in Budget Register</div>';
		myTable += '<br/><table width="100%"><tr><td width="25%">Posted in Ledger</td><td width="25%">Ledger Keeper</td><td width="25%">I / C Budget</td><td width="25%">EDP Batch No</td></tr></table>';
	}if(previewJson.voucherTypeId=='4'){
		myTable += '</table><br/><table><tr><td><table><tr><td width="75%">Articles listed above have been Received by</td><td></td></tr><br/><tr><td colspan="2" style="border-bottom: 2px dotted #000000;"></td></tr><br/><tr><td colspan="2" align="left">in complience with</td></tr><br/><tr><td colspan="2" style="border-bottom: 2px dotted #000000;"></td></tr><br/><tr><td></td><td align="center">Receiving Officer</td></tr></table></td>';
		myTable += '<td><table><tr><td width="75%">Articles listed above have been issued from</td><td></td></tr><br/><tr><td colspan="2" style="border-bottom: 2px dotted #000000;"></td></tr><br/><tr><td colspan="2" align="left">in complience with</td></tr><br/><tr><td colspan="2" style="border-bottom: 2px dotted #000000;"></td></tr><br/><tr><td></td><td align="center">Issueing Officer</td></tr></table></td></tr></table>';
		myTable += '<br/><div style="border-bottom:1px solid gray;"></div>';
		myTable += '<table width="100%"><tr><td width="40%" valign="top"><table><tr><td>Store:Posted in Ledger by</td><td width="80%" style="border-bottom: 2px dotted #000000;"></td></tr><br/><tr><td>Posted in Stores Inventory By</td><td style="border-bottom: 2px dotted #000000;"></td></tr></table></td><td width="1" bgcolor="#000000"><BR></td><td width="30%" valign="top"><table><tr><td>EDP Batch No</td></tr></table></td><td width="1" bgcolor="#000000"><BR></td><td width="30%"><table><tr><td>Audit</td></tr></table></td></tr></table>';
	}if(previewJson.voucherTypeId=='8'){
		myTable += '<br/><br/><table><tr><td width="28%">Signature</td><td width="19%">STORES</td><td width="17%">Signature</td><td width="18%">Holder1</td><td width="19%">Signature</td><td width="20%">Holder2</td></tr></table>';
		myTable += '<br/><div style="border-bottom:1px solid gray;"></div>';
		myTable += '<table width="100%"><tr><td width="40%" valign="top"><table><tr><td>Store:Posted in Ledger by</td><td width="60%" style="border-bottom: 2px dotted #000000;"></td></tr><br/><tr><td>Posted in Stores Inventory By</td><td width="60%" style="border-bottom: 2px dotted #000000;"></td></tr></table></td></tr></table>';
	}
	$jq('#mainDiv').append(myTable);
}
function demandPreview(){
	var demandPreviewJson={
			"inventoryNo" : $jq('#inventoryNo option:selected').text(),
            "holderName"  : $jq('#holderName').html(),
            "directorateName"  : escape($jq('#directorateName').html()),
            "divisionName"  : $jq('#divisionName').html(),
            "projectCode"  : $jq('#projectCode option:selected').text(),
            "demadTypeId"  : $jq('#demadTypeId option:selected').text(),
            "accountHeadId" : $jq('#accountHeadId option:selected').text(),
            "groupDemandNo" : escape($jq('#groupDemandNo').html()),
            "demandDate" : $jq('#demandDate').html()
	};
	window.open('raiseDemand.htm?param=preview&demandPreviewJson='+JSON.stringify(demandPreviewJson),'preview','fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
}
function createDemandPreview(){
	var totalCost=0.0;
	var projectCode='';
	var demadTypeId='';
	if(demandPreviewJson.projectCode!='Select')
		projectCode=demandPreviewJson.projectCode;
	if(demandPreviewJson.demadTypeId!='Select')
		demadTypeId=demandPreviewJson.demadTypeId;
	if(JsonDemandList!=null)
		for(var i=0;i<JsonDemandList.length;i++){
			totalCost=parseFloat(totalCost)+(parseFloat(JsonDemandList[i].qty)*parseFloat(JsonDemandList[i].unitRate));
		}
	var myTable = '' ;
	myTable += '<br/><br/><table id="myTable" cellspacing=0 cellpadding=0 border=0 width=100%>' ;
	myTable += '<tr align=center><th><font size=5>ADVANCED SYSTEMS LABORATORY, HYDERABAD 500 058</font></th></tr>';
	myTable += '<tr align=center><th><font size=5>Cash Purchase Demand</font></th></tr>';
	
	myTable += '<br/><br/><tr><table cellspacing=0 cellpadding=1 border=0 width=100%>';
	myTable += '<tr><td>Inventory Number</td><td>:&nbsp;<input type="text" readonly="true" value="'+demandPreviewJson.inventoryNo+'"></input></td>&nbsp;&nbsp;<td>Inventory Holder Name</td><td>:&nbsp;<input type="text" readonly="true" value="'+demandPreviewJson.holderName+'"></input></td></tr>';
	myTable += '<tr><td>Directorate</td><td>:&nbsp;<input type="text" readonly="true" value="'+demandPreviewJson.directorateName+'"></input></td>&nbsp;&nbsp;<td>Division</td><td>:&nbsp;<input type="text" readonly="true" value="'+demandPreviewJson.divisionName+'"></input></td></tr><tr><td>Project</td><td>:&nbsp;<input type="text" readonly="true" value="'+projectCode+'"></input></td>';
	myTable += '&nbsp;&nbsp;<td>Demand Type</td><td>:&nbsp;<input type="text" readonly="true" value="'+demadTypeId+'"></input></td></tr><tr><td>Account Head</td><td>:&nbsp;<input type="text" readonly="true" value="'+demandPreviewJson.accountHeadId+'"></input></td>&nbsp;&nbsp;<td>Group Demand Number</td><td>:&nbsp;<input type="text" readonly="true" value="'+demandPreviewJson.groupDemandNo+'"></input></td></tr>';
	myTable += '<tr><td>Group Demand Date</td><td>:&nbsp;<input type="text" readonly="true" value="'+demandPreviewJson.demandDate+'"></input></td></tr></table></tr>';
		
	myTable += '<br/><br/><div>To following items are to be produced through cash purchase to meet urgent requirements as supply'+
	' of these items through normal purchase process cannot be awaited. Approval of the CFA may kindly be accorded.</div>';
	myTable += '<br/><tr><table cellspacing=0 cellpadding=0 border=1 width=100%>';
	myTable += '<tr><th>SL.NO</th><th>ITEM CODE</th><th>DESCRIPTION</th><th>UOM</th><th>C/NC</th><th>RATE</th><th>QUANTITY</th><th>Estimated Cost</th></tr>';
	if(JsonDemandList!=null)
	for(var i=0;i<JsonDemandList.length;i++){
		myTable +='<tr><td>'+(i+1)+'</td><td>'+JsonDemandList[i].materialCode+'</td><td>'+JsonDemandList[i].description+'</td><td>'+JsonDemandList[i].uom+'</td><td>'+JsonDemandList[i].cflag+'</td><td>'+JsonDemandList[i].unitRate+'</td><td>'+JsonDemandList[i].qty+'</td><td>'+(JsonDemandList[i].unitRate*JsonDemandList[i].qty)+'</td></tr>';
	}
	myTable += '<tr><td colspan="7">Total&nbsp;</td><td>'+totalCost+'</td></tr>';
	myTable += '</table></tr>';
	myTable += '<br/><table><tr><td>Total amount for which sanction is required : Rs.</td><td style="border-bottom: 2px dotted #000000;width:400px"></td></tr></table>';
	myTable += '<br/><div style="border-bottom:1px solid gray;"></div>';
	
	myTable += '<div><pre>Justification : </pre></div><br/><br/>';
	myTable += '<table><tr><td colspan="4">Initiating Officer</td><td colspan="4">Division Head</td><td colspan="2">Dir of Concerned Dte.</td></tr>';
	myTable += '<tr><td width="7%">Phone :</td><td width="7%"></td><td width="7%">Date :</td><td width="10%"></td><td width="7%">Phone :</td><td width="7%"></td><td width="7%">Date :</td><td width="10%"></td><td width="7%">Phone :</td><td width="7%"></td><td width="7%">Date :</td><td width="10%"></td></tr></table>';
	myTable += '<br/><div style="border-bottom:1px solid gray;"></div>';
	myTable += '<br/><div>Certified that unit measurement and C/NC category is correct as per ledger. Items <b>Available/Not Available</b> in central stock</div><br/><br/>';
	myTable += '<table><tr><td width="30%">Phone :</td><td width="40%"></td><td width="30%"></td></tr><tr><td width="30%">Date :</td><td width="40%"></td><td width="30%">Stores Officer in the Directorate</td></tr></table>';
	
	myTable += '<br/><div style="border-bottom:1px solid gray;"></div>';
	myTable += '<br/><table><tr><td>Certified that funds are/will be available under Budget head </td><td style="border-bottom: 2px dotted #000000;width:400px"></td></tr></table><br/><br/>';
	myTable += '<table><tr><td width="30%">Phone :</td><td width="58%"></td><td width="30%"></td></tr><tr><td width="30%">Date :</td><td width="30%"></td><td width="58%">I/C Budget</td></tr></table>';
	//myTable += '<br/><br/><br/><br/><br/><br/><br/><br/>';
	myTable += '<br/><div style="border-bottom:1px solid gray;"></div><br/>';
	myTable += '<table><tr><td colspan="2">Local Purchases/Outstation purchase approved and cash officer is authorized to pay sanctioned amount of Rs.</td></tr>';
	myTable += '<br/><tr><td style="border-bottom: 2px dotted #000000;width:500px"></td><td>in cash to Cash Purchase Officer</td></tr></table>';
	myTable += '<br/><table><tr><td>Shri</td><td style="border-bottom: 2px dotted #000000;width:100px"></td><td>Designation</td><td style="border-bottom: 2px dotted #000000;width:100px"></td><td>Whose specimen signatures are attested below.</td></tr></table><br/>';
	
	myTable += '<table><tr><td width="5%">1.</td><td style="border-bottom: 2px dotted #000000;width:200px"></td><td></td></tr><tr><td width="5%">2.</td><td style="border-bottom:2px dotted #000000;width:200px"></td><td width="65%" align="right">CFA Authorised Signatory</td></tr></table>';
	
	myTable += '<br/><div style="border-bottom:1px solid gray;"></div><br/>';
	myTable += '<div align="center"><b>RECEIPT</b></div>';
	myTable += '<table><tr><td width="11%">Received Rs.</td><td style="border-bottom: 2px dotted #000000;width:700px"></td></tr></table>';
	myTable += '<br/><table><tr><td width="59%">from the Cash Officer for the purchase mentioned above on (Date)</td><td style="border-bottom: 2px dotted #000000;width:300px"></td></tr></table>';
	myTable += '<br/><br/><div align="right">Cash purchase Officer</div>';
	
	myTable += '<br/><div style="border-bottom:1px solid gray;"></div><br/>';
	myTable += '<div align="center"><b>CERTIFICATE</b></div>';
	myTable += '<div width="20%">It is Certified that :</div>';
	myTable += '<table><tr><td width="74%">1. Verbal enquiped were made and the prices paid are the cheapest / reasonable due to</td><td style="border-bottom: 2px dotted #000000;width:200px"></td></tr></table>';
	
	myTable += '<br/><div style="border-bottom:1px solid gray;"></div><br/>';
	myTable += '<div>Purchase are from authorised dealers, stockist or reputed vendors.</div>';
	myTable += '<br/><br/><table><tr><td width="45%">Member</td><td width="30%">Member</td><td width="20%">Cash Purchase Officer</td><tr></table>';
	
	myTable += '<br/><div style="border-bottom:1px solid gray;"></div><br/>';
	myTable += '<div align="center"><b>SETTLEMENT</b></div><br/>';
	myTable += '<table><tr><td width="26%">Received balance amount of Rs</td><td style="border-bottom: 2px dotted #000000;width:600px"></td></tr></table>';
	myTable += '<table><tr><td>from Shri</td><td style="border-bottom: 2px dotted #000000;width:100px"></td><td>Desig</td><td style="border-bottom: 2px dotted #000000;width:100px"></td><td>on</td><td style="border-bottom: 2px dotted #000000;width:100px"></td></tr></table>';
	myTable += '<div>(date) with two copies of cash memo and receipt voucher. The advance drawn by the Officer has been settled without ant</div>';
	myTable += '<table><tr><td width="37%">discrepancy.Actual expenditure incurred is Rs</td><td style="border-bottom: 2px dotted #000000;width:600px"></td></tr></table>';
	myTable += '<table><tr><td width="36%">and the DV No. is</td><td style="border-bottom: 2px dotted #000000;width:200px"></td></tr></table>';
	
	myTable += '<br/><br/><table><tr><td width="85%">Date</td><td width="11%" align="right">Cash Officer</td></tr></table>';
	myTable += '</table>';
	$jq('#mainDiv').append(myTable);
}
function PopulateDemandDetails() {
	document.forms[0].param.value="getDemandDetails";
	document.forms[0].action='invoiceReceipt.htm?param=getDemandDetails';
	document.forms[0].submit();
//	$jq.post( 
//	           'invoiceReceipt.htm?param=getDemandDetails&demandNo='+$jq('#IRdemandNo option:selected').text(), 
//	           function(data){
//	        	   $jq('#IRitems').html(data);
//	        	  } 
//	         );
}
function generateInvoice() {
	document.forms[0].param.value="submitRequest";
	document.forms[0].type.value="invoice";
			$jq.post( 
	           'invoiceReceipt.htm?&voucherNo='+$jq('#voucherNo').html(),$jq('#invoice').serialize(), 
	           function(data){ 
	            	 		$jq('#result').html(data);
	            	      } 
	         );
}

function saveSecurityDetails(demandNo){
	var status=true;
	var status1=true;
	var errorMessage="";
	var len=$jq('#dil')[0].rows.length;
	if(status && $jq('#memoNo').val()==''){
		errorMessage="plz Enter Memo Number \n";
		status=false;
	}
	if(status && $jq('#fromDate').val()==''){
		errorMessage+="plz Enter Memo Date \n";
		  status=false;
	}
	if(status && $jq('#qty').val()==''){
		errorMessage+="plz Enter Quatity  \n";
		  status=false;
	}
	if(status && $jq('#qty').val()<=0){
		errorMessage+="Plz Enter Quatity \n";
		  status=false;
	}	
	for(var i=1;i<len;i++){
		if($jq('#selectDemand'+i)[0]!=null && $jq('#selectDemand'+i)[0].checked){
			status1=true;
		}
	}
	if(!status1)
	{
		errorMessage+="Plz Select atleast one material \n";
		status1=false;
	}
	if(status && status1){
	var demandNo=$jq('#demandNo option:selected').html();
	var securityCheckjson={};
	var count=0;
	for(var i=1;i<len;i++){
		var json={};
		if($jq('#selectDemand'+i)[0]!=null && $jq('#selectDemand'+i)[0].checked){
			count=count+1;
			json["materialCode"]=$jq('#selectDemand'+i)[0].value;
			json["qty"]=$jq('#qty'+i)[0].value;
			json["uom"]=$jq('#uom'+i)[0].innerHTML;
			json["demandNo"]=demandNo;
			securityCheckjson[count]=json;
		}
	}		
	document.forms[0].securityCheckJson.value=JSON.stringify(securityCheckjson);
	$jq.post(
			'securityDetails.htm?param=savesSecuritycheck',$jq('#securityCheck').serialize(),
			function(data){
				$jq('#demandList').html(data);
				}
			);
	}
	else{
	   alert(errorMessage);
	}

}
function clearMaterial() {
	$jq('#search').val("Select");
	$jq('#materialCode').html('<input type="text" name="materialCode"/>');
	$jq('#materialdesc').html('');
	if($jq('#qty').length > 0){
		$jq('#qty').val("");
	}
	if($jq('#unitRate').length > 0){
		$jq('#unitRate').val("");
	}
	if($jq('#uomLable').length > 0){
		$jq('#uomLable').html("&nbsp;");
	}
	if($jq('#cncLable').length > 0){
		$jq('#cncLable').html("&nbsp;");
	}
	if($jq('#uomConvert').length > 0){
		$jq('#uomConvert').val("Select");
	}
	if($jq('#cncConvert').length > 0){
		$jq('#cncConvert').val("Select");
	}
	
	//$jq(".as-values").val("");
}

function cashReceiptPreview(){
	var cashReceiptPreviewJson={
			"IRdemandNo" : escape($jq('#IRdemandNo option:selected').text()),
			"voucherNo" : $jq('#voucherNo').html(),
			"voucherDate" : $jq('#voucherDate').html()
	};
	window.open('invoiceReceipt.htm?param=preview&cashReceiptPreviewJson='+JSON.stringify(cashReceiptPreviewJson),'preview','fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
}

function createCashReceiptPreview(){
	var totalQty=0;
	if(IRItemsJson!=null)
	for(var i=0;i<IRItemsJson.length;i++){
		//totalQty=parseInt(totalQty);
		//totalQty+=parseInt(JsonDemandList[i].qty);
	}
	var myTable = '' ;
	myTable += '<br/><br/><table id="myTable" cellspacing=0 cellpadding=0 border=0 width=100%>' ;
	myTable += '<tr align=center><th><font size=5>ADVANCED SYSTEMS LABORATORY, HYDERABAD 500 058</font></th></tr>';
	myTable += '<tr align=center><th><font size=5>CASH PURCHASE RECEIPT VOUCHER</font></th></tr>';
	
	myTable += '<br/><br/><tr><td><table cellspacing=0 cellpadding=1 border=0 width=100%><tr><td>VOUCHER NO</td><td>:&nbsp;<input type="text" readonly="true" value="'+cashReceiptPreviewJson.voucherNo+'"></input></td><td>GROUP DEMAND NO</td><td>:&nbsp;<input type="text" readonly="true" value="'+cashReceiptPreviewJson.IRdemandNo+'"></input></td></tr>';
	myTable += '<tr><td>INVENTORY NO</td><td>:&nbsp;<input type="text" readonly="true" value=""></input></td><td>DIRECTORATE</td><td>:&nbsp;<input type="text" readonly="true" value=""></input></td></tr>';
	myTable += '<tr><td>VOUCHER DATE</td><td>:&nbsp;<input type="text" readonly="true" value="'+cashReceiptPreviewJson.voucherDate+'"></input></td><td>GROUP DEMAND DATE</td><td>:&nbsp;<input type="text" readonly="true" value=""></input></td></tr>';
	myTable += '<tr><td>INVENTORY HOLDER</td><td>:&nbsp;<input type="text" readonly="true" value=""></input></td><td>DIV/GP</td><td>:&nbsp;<input type="text" readonly="true" value=""></input></td></tr></table>';
	
	myTable += '<br/></td></tr><tr><td><table cellspacing=0 cellpadding=0 border=1 width=100%>';
	myTable += '<tr><th>SL.NO</th><th>ITEM CODE</th><th>DESCRIPTION</th><th>UOM</th><th>C/NC</th><th>RATE</th><th>QTY</th><th>TAX</th><th>VALUE</th><th>MEMO NO</th><th>MEMO DATE</th><th>SOURCE</th></tr>';
	if(IRItemsJson!=null)
	for(var i=0;i<IRItemsJson.length;i++){
		myTable +='<tr><td>'+(i+1)+'</td><td>'+IRItemsJson[i].materialCode+'</td><td>'+IRItemsJson[i].description+'</td><td>'+IRItemsJson[i].uom+'</td><td>'+IRItemsJson[i].cflag+'</td><td>'+IRItemsJson[i].unitRate+'</td><td>'+IRItemsJson[i].qty+'</td><td></td><td>'+(IRItemsJson[i].unitRate*IRItemsJson[i].qty)+'</td><td></td><td></td><td></td></tr>';
	}
	myTable += '</table></td></tr><br/><br/><br/>';
	myTable += '<tr><td><div style="border-bottom:1px solid gray;"></div></tr></td><br/>';
	myTable += '<tr><td><table width="100%"><tr><td width="29%">THE ITEMS ARE SENT HEREWITH</td><td width="55%" align="center">ACCEPTANCE REPORT</td><td>COST :</td></tr>';
	myTable += '<tr><td>FOR ACCEPTANCE AND RETENTION</td><td><table><tr><td>1. THE STORES MENTIONED ARE OF THE REQUIRED SPECIFICATIONS AND ARE ACCEPTED</td></tr><tr><td>2. THE DESCRIPTION OF STORES IS CORRECT AND COMPLETE</td></tr><tr><td>3. THE STORES ARE CONSUMBLE / NON CONSUMABLE </td></tr><tr><td>4. THE STORES MAY BE ISSUED TO / RETAINED IN INVENTORY NO </td></tr></table></td><td></td></tr></table></td></tr><br/><br/>';
	myTable += '<tr><td><table width="100%"><tr><td width="23%">DATE :</td><td width="27%">STORES OFFICER</td><td width="17%">DATE :</td><td width="28%">SIGNATURE OF INSPECTING OFFICER</td></tr></table></td></tr>';
	myTable += '<tr><td><div style="border-bottom:1px solid gray;"></div></tr></td><br/>';
	myTable += '<tr><td><table width="100%"><tr><td>STORES LISTED ABOVE HAVEN BEEN TAKEN ON LEDGER CHARGE</td><td>THE STORES RECEIVED  AS PER THIS IR DULY INSPECTED AND HELD ON INVENTEROY NO: </td></tr><br/><br/>';
	myTable += '<tr><td></td><td><table><tr><td width="35%">DATE :</td><td>SIGNATURE OF INV.HOLDER NAME :&nbsp;</td></tr></table></td></tr></table></td></tr>';
	myTable += '</td></tr></table>';
	$jq('#mainDiv').append(myTable);
}
function inventoryHolderRequest(type,value,k){
	if(type=='Delete'){
		document.forms[0].param.value="invHolderRequest";
		document.forms[0].type.value="delete";
		$jq.post(
				'mmgMaster.htm?inventoryNo='+value,$jq('#viewMaster').serialize(),
				function(data){
					for(var i=0;i<=k;i++){
						if($jq('#edit'+i).length > 0)
							$jq('#edit'+i).css("display","none");
					}
						
					$jq('#result').html(data);
					}
				);
	}else{
		document.forms[0].param.value="invHolderRequest";
		document.forms[0].changedValues.value=getInvDetails();
		$jq.post(
				'mmgMaster.htm?inventoryNo='+$jq.trim($jq('#inventoryNo').text()),$jq('#viewMaster').serialize(),
				function(data){
					$jq('#result').html(data);
					}
				);
	}
	
}
function getInvDetails(){
	var value='';
	var jsoncell = {};
	var invFor='';
	if($jq('#inventoryHolderType1').is(':checked')==true){
		invFor='30';
	}else{
		invFor='31';
	}
	value=$jq(".as-values").val().split(",")[$jq(".as-values").val().split(",").length-2];
	var orgroleid='';
	if(value.split('#')[1]!=undefined){
		orgroleid=value.split('#')[1];
	}
	jsoncell["inventoryNo"] = $jq.trim($jq('#inventoryNo').text());
	jsoncell["sfid"] = value.split('#')[0];
	jsoncell["orgRoleId"] =orgroleid;
	jsoncell["inventoryHolderType"] = invFor;
	
	return JSON.stringify(jsoncell);
}
function demandCancelRaise() {
	if( $jq('#cancelDemand').valid() == true) {
		$jq.post( 
		           'raiseDemand.htm?&demandNo='+escape($jq("#demandNo option:selected").html())+'&type=DEMANDCANCEL&param=submitRequest',
		            function(data){ 
		            	 $jq('#result').html(data);
		            	 } 
		      );
		
	}
}
function invoiceCancelRaise() {
	$jq.post( 
	           'invoiceReceipt.htm?&voucherNo='+$jq('#voucherNo option:selected').html()+'&type=INVOICECANCEL&param=submitRequest', 
	           function(data){ 
	            	 		$jq('#result').html(data);
	            	      } 
	         );
}

function clearMMGVoucher(){
	if($jq('#inventoryNo').length > 0){
		$jq('#inventoryNo').val('');
	}
	if($jq('#divControlNum').length > 0){
		$jq('#divControlNum').val('');
	}
	if($jq('#workDetails').length > 0){
		$jq('#workDetails').val('');
		$jq('#counter').val('200');
		
	}
	if($jq('#workAmount').length > 0){
		$jq('#workAmount').val('');
	}
	if($jq('#purpose').length > 0){
		$jq('#purpose').val('');
	}
	if($jq('#rfcondemnation').length > 0){
		$jq('#rfcondemnation').val('');
	}
	if($jq('#ineNum').length > 0){
		$jq('#ineNum').val('');
	}
	if($jq('#periodFrom').length > 0){
		$jq('#periodFrom').val('');
	}
	if($jq('#periodTo').length > 0){
		$jq('#periodTo').val('');
	}
	if($jq('#labName').length > 0){
		$jq('#labName').val('');
	}
	if($jq('#location').length > 0){
		$jq('#location').val('');
	}
	if($jq('#referenceNo').length > 0){
		$jq('#referenceNo').val('');
	}
	if($jq('#toInventory').length > 0){
		$jq('#toInventory').val('Select');
	}
}
function getProjects() {
	$jq.post( 
	           'raiseDemand.htm?demadTypeId='+escape($jq("#demadTypeId option:selected").html())+'&param=getProjects',
	            function(data){ 
	            	 	$jq('#result').html(data);
	            	 } 
	      );
}
function clearCancelDemand()
{
	$jq("#cancelDemand")[0].demandNo.value="";
	$jq("#cancelDemand")[0].demandCancelDate.value="";
	$jq("#cancelDemand")[0].remarks.value="";
}
function clearCancelInvoice()
{
	$jq("#cancelInvoice")[0].voucherNo.value="";
	$jq("#cancelInvoice")[0].invoiceCancelDate.value="";
	$jq("#cancelInvoice")[0].reason.value="";
}
function deleteDraft(id){
	$jq.post( 
	           'raiseDemand.htm?param=deleteDraft&demandNo='+escape($jq("#"+id).html()),  
	           function(data){ 
	            	 $jq('#drafts').html(data);
	           } 
	      );
}
function checkPercentage(id,peramVal){
	if(parseInt(peramVal)>100){
		alert("Please Enter Valid Percentage Value");
		$jq('#'+id).focus();
	}
}
function manageMMGConfig(type){
	var status=true;
	if(type=='financialYear'){
		document.forms[0].type.value="financialYear";
	}else if(type=='demandPurchaseLimit'){
		document.forms[0].type.value="demandPurchaseLimit";
	}
	if(status){
		document.forms[0].param.value="mmgConfigSubmit";
		$jq.post( 
		           'mmgMaster.htm',$jq('#mmgMaster').serialize(),  
		           function(data){ 
		            	 $jq('#result').html(data);
		            	 clearMMMGConfig(type);
		           } 
		      );
	}
}
function clearMMMGConfig(type){
	if(type=='financialYear'){
		if($jq('#financialYear').length > 0){
			$jq('#financialYear').val('');
		}
		if($jq('#description').length > 0){
			$jq('#description').val('');
		}
		if($jq('#counter').length > 0){
			$jq('#counter').val('100');
		}
	}
	if(type=='demandPurchaseLimit'){
		if($jq('#demandPurchaseLimit').length > 0){
			$jq('#demandPurchaseLimit').val('');
		}
		if($jq('#purchaseLimitDesc').length > 0){
			$jq('#purchaseLimitDesc').val('');
		}
		if($jq('#counter1').length > 0){
			$jq('#counter1').val('100');
		}
	}
}