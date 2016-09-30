var divid;

function PopulateValues(detailsjson){
	var flag=false;
	var status=true;
	var documentTitle="";
	var documentHeadTitle="";
	var inventoryNo="";
	var newb = document.createElement('a');
	newb.innerHTML = "NEW";
	newb.setAttribute("class", "quarterbutton");
	
	if(detailsjson!="")	{
		var length=0;
		var nomineeFlag=true;
		var count="";
		for(var key in detailsjson)
		{
			length=length+1;		
		}
		
		for(var k=0;k<length-1;k++)
		{
			status=false;
			var id="";
			var fieldsetelement=document.createElement("fieldset");
			var innertable=document.createElement("table");
			innertable.style.width="100%";
			var i=0;
			
			var tbody=document.createElement("tbody");
			var  linediv=document.createElement("tr");
			for(var keys in detailsjson[k])
			{
				if(keys!="id")
				{				
				var  firstdiv=document.createElement("td");
				var  seconddiv=document.createElement("td");
				if(i%2==0)
				{
					linediv=document.createElement("tr");
				}	
				if(detailsjson[k][keys]!=null && detailsjson[k][keys]!='Select' && detailsjson[k][keys]!='select'){
					var bold=document.createElement("b");
					bold.innerHTML=keys;
					firstdiv.appendChild(bold);
					
					if(detailsjson[k][keys]!=null){
						if(keys=="Inventory Number")
							inventoryNo=detailsjson[k][keys];
						seconddiv.innerHTML="&nbsp;<b >:</b>"+detailsjson[k][keys];
					}else
						seconddiv.innerHTML="&nbsp;<b >:</b>";	
					
					
					firstdiv.style.width="25%";
					//firstdiv.style.fontFamily="Times New Roman";
					firstdiv.style.lineHeight="24px";
					firstdiv.style.verticalAlign="top";
					seconddiv.style.width="25%";
					//seconddiv.style.fontFamily="Times New Roman";
					seconddiv.style.lineHeight="24px";
					seconddiv.style.verticalAlign="top";
					linediv.appendChild(firstdiv);
					linediv.appendChild(seconddiv);
					//valuediv.innerHTML="<b>"+keys+"</b> : "+detailsjson[k][keys];
				}else{
					//valuediv.innerHTML="<b>"+keys+"</b> : "+"";
					var bold=document.createElement("b");
					bold.innerHTML=keys;
					firstdiv.appendChild(bold);
					seconddiv.innerHTML="&nbsp;<b >:</b>";
					if(detailsjson[k][keys]!=null)
					seconddiv.innerHTML="&nbsp;<b >:</b>"+detailsjson[k][keys];
					else
					seconddiv.innerHTML="&nbsp;<b >:</b>";
					firstdiv.style.width="25%";
					//firstdiv.style.fontFamily="Times New Roman";
					firstdiv.style.lineHeight="24px";
					firstdiv.style.verticalAlign="top";
					seconddiv.style.width="25%";
					//seconddiv.style.fontFamily="Times New Roman";
					seconddiv.style.lineHeight="24px";
					seconddiv.style.verticalAlign="top";
					linediv.appendChild(firstdiv);
					linediv.appendChild(seconddiv);
					linediv.appendChild(firstdiv);
					linediv.appendChild(seconddiv);
				}						 
				i++;
				tbody.appendChild(linediv);
				innertable.appendChild(tbody);
				if(detailsjson[length-1]=="address")
				{
					if(i==2){
						var font=document.createElement("font");
						font.innerHTML=detailsjson[k][keys];
						font.setAttribute("color","green");
						var legend=document.createElement("legend");
						legend.appendChild(font);					
						fieldsetelement.appendChild(legend);
					}
				}
				fieldsetelement.appendChild(innertable);
				
				}else
				{
					id=detailsjson[k].id;
				}
			}
			
				if(nomineeFlag)
					var buttondiv=document.createElement("div");
				var elb = document.createElement('a');
				elb.innerHTML = "Edit";
			if(detailsjson[length-1]=="inventHolder"){
				elb.setAttribute("href", "javascript:editInventHolderDetails('" +id+ "','"+k+"',"+(length-1)+")");	
				var elb1 = document.createElement('a');
				elb1.innerHTML = "Delete";
				elb1.setAttribute("href", "javascript:inventoryHolderRequest('Delete',"+inventoryNo+","+k+");");
				documentHeadTitle="View Inventory Holder Details";
				documentTitle="View Inventory Holder Details";
				elb1.setAttribute("maxlength", "45");
				elb1.className="quarterbutton";		
			}
			
			elb.setAttribute("maxlength", "45");
			elb.className="quarterbutton";		
			buttondiv.setAttribute("id","el"+k);
			
			if(nomineeFlag){
				if(detailsjson[length-1]=="inventHolder"){
					var editdiv=document.createElement("div");
					editdiv.style.marginLeft="70%";
					editdiv.style.float="left";
					editdiv.appendChild(elb);
					editdiv.className="apprightbutton";
					editdiv.setAttribute("id","edit"+k)
					var deletediv=document.createElement("div");
					deletediv.appendChild(elb1);
					deletediv.className="apprightbutton";
					buttondiv.appendChild(deletediv);
					buttondiv.appendChild(editdiv);
				}else{
					buttondiv.className="apprightbutton";
					buttondiv.appendChild(elb);
				}
			}
			

			if(detailsjson[length-1]=="inventHolder"){
				fieldsetelement.appendChild(buttondiv);
			}
			
				if(detailsjson[length-1]=="inventHolder")
					innertable.setAttribute("id", "createInventHolderDetails"+k);
			var maindiv=document.getElementById("viewMasterData");
			maindiv.appendChild(fieldsetelement);

		}
	}
	if(status){
			var maindiv=document.getElementById("viewMasterData");
			maindiv.innerHTML="Nothing found to display.";
	}
	if(detailsjson!=null){
		if(detailsjson[0]=="inventHolder"){
			documentHeadTitle="View Inventory Holder Details";
			documentTitle="View Inventory Holder Details";
		}
	}
	document.getElementById('documentheadtitle').innerHTML=documentHeadTitle;
	document.title=documentTitle;
}

function editInventHolderDetails(id,no,noIds) {
	divid="createInventHolderDetails"+no;	
		document.forms[0].param.value="editInventHolderDetails";
		document.forms[0].type.value="inventHolder";
		$jq.post(
				'mmgMaster.htm?orgRoleId='+id,$jq('#viewMaster').serialize(), 
		function(data){
			$jq('#'+divid).html(data);
			for(var i=0;i<=noIds;i++){
				if(document.getElementById('el'+i)!=null)
					document.getElementById('el'+i).style.display="none";
			}
		});
}
