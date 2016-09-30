/* 
 * Copyright (c) 2010 CompuCloud Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * Author: Jevon Gill
 */
(function($){

    $.fn.displayTagAjax = function(paramVal) {
        var links = new Array();
        var container = this;
        addItemsToArray(this.find("table .sortable a"),links);
        addItemsToArray(this.find(".pagelinks a"),links);
        hideLoadingMessage();
        $.each(links,function()
            {
	        	var url = $(this).attr("href");
                    addClickEvent(container, this,url,paramVal);
                    $(this).removeAttr("href");
                    //Narayana
                   if(dispUrl !=undefined && dispUrl==""){
                	   $jq('.pagelinks').find('a').eq(0).hide();
                   	   $jq('.pagelinks').find('a').eq(1).hide();
                   }else if(dispUrl !=undefined && dispUrl.split("=")[1]=="1"){
                    	 $jq('.pagelinks').find('a').eq(0).hide();
                     	 $jq('.pagelinks').find('a').eq(1).hide();
                   }else if(dispUrl !=undefined && dispUrl !="" && dispUrl.split("=")[1] !=-1 && (dispUrl.split("=")[1]==(parseInt($jq('.pagelinks').find('a').length)-5)+1)){
                	   $jq('.pagelinks').find('a').eq(0).hide();
                	   $jq('.pagelinks').find('a').eq($jq('.pagelinks').find('a').length-1).hide();
                	   $jq('.pagelinks').find('a').eq($jq('.pagelinks').find('a').length-2).hide();
                   }else if(dispUrl !=undefined && dispUrl !="" && dispUrl.split("=")[1] ==-1){
                	   $jq('.pagelinks').find('a').eq(0).hide();
                   	   $jq('.pagelinks').find('a').eq(1).hide();
	                   
                   	 $jq('.pagelinks').find('a').eq($jq('.pagelinks').find('a').length-1).hide();
              	     $jq('.pagelinks').find('a').eq($jq('.pagelinks').find('a').length-2).hide();
              	     $jq('.pagelinks').find('a').eq($jq('.pagelinks').find('a').length-3).hide();
                   }else{
                	   $jq('.pagelinks').find('a').eq(0).hide();
                	   if(dispUrl !=undefined && dispUrl.split("=")[1]==$jq('.pagelinks').find('a').length){
                		   	 $jq('.pagelinks').find('a').eq($jq('.pagelinks').find('a').length-1).hide();
                    	     $jq('.pagelinks').find('a').eq($jq('.pagelinks').find('a').length-2).hide();
                    	     $jq('.pagelinks').find('a').eq($jq('.pagelinks').find('a').length-3).hide();
    	             	  $jq('.pagelinks').find('a').eq(0).show();
                	   }
                	   if(dispUrl !=undefined){
                    	   $jq('.pagelinks').find('a').eq(0).show();
                       }
                   }
                  
            }        
        );        
        return true;
    };

  function addClickEvent(ctx, element,url,paramVal){	  
        $(element).click(        		
            function(){
            	//showLoadingMessage();
            	if(url!=undefined){
	        		dispUrl = url.split('?')[1];
	        		
	        		if (url.split('?')[1] == undefined) {
						url = url.substring(0,url.length-1) + "?param="+paramVal;
					} else {
						url += "&param="+paramVal;
					}
	            	
	            	jQuery.ajax(
	                {
	                    url: url,
	                    success: function(data){
	                       filteredResponse =  $(data).find(this.selector);
	                       if(filteredResponse.size() == 1){
	                            $(this).html(filteredResponse);
	                       }else{
	                            $(this).html(data);
	                       }                       
	                       $(this).displayTagAjax(paramVal);                       
	                    } ,
	                    data: ({"time":new Date().getTime()}),
	                    context: ctx
	                });	        		
	        	}	        	
            }
        );
    }

   function addItemsToArray(items,array){
        $.each(items,function()
            {
                    array.push(this);
            }
        ); 
        
    }
  showLoadingMessage = function() {

		var div = document.getElementById(this.id + "_loading_div");
		if (div == null) {
			div = document.createElement("DIV");

			document.body.appendChild(div);
			div.id = this.id + "_loading_div";

			div.innerHTML = '<img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">' + "Loading...";
			div.style.position = "absolute";
			div.style.border = "1 solid black";
			div.style.color = "white";
			div.style.backgroundColor = "#004696";
			div.style.padding = "0px 5px";
			div.style.top = 0;
			div.style.right = 0;
			div.style.fontSize = "1.2em";
			div.style.fontWeight = "bold";
			div.style.fontFamily = "Verdana, Arial, Helvetica, sans-serif";

		}

		div.style.display = "";
	}
  hideLoadingMessage = function() {
		var div = document.getElementById(this.id + "_loading_div");
		if (div != null)
			div.style.display = "none";
  }
    
})(jQuery);



