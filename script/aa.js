/*
Copyright (c) 2005  Vitaliy Shevchuk (shevit@users.sourceforge.net)

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

 */

var ajaxAnyWhere = null;
AjaxAnywhere.defaultInstanceName = "default";
// constructor;
function AjaxAnywhere() {

	this.id = AjaxAnywhere.defaultInstanceName;
	this.formName = null;

	if (window.XMLHttpRequest) {
		this.req = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		this.req = new ActiveXObject("Microsoft.XMLHTTP");
	}
}

/**
 * Returns a Form object that corresponds to formName property of this
 * AjaxAnywhere class instance.
 */
AjaxAnywhere.prototype.findForm = function() {

	var form;
	if (this.formName != null)
		form = document.forms[this.formName];
	else if (document.forms.length > 0)
		form = document.forms[0];

	if (typeof form != "object")
		alert("AjaxAnywhere error: Form with name [" + this.formName
				+ "] not found");
	return form;
}

/**
 * Binds this instance to window object using "AjaxAnywhere."+this.id as a key.
 */
AjaxAnywhere.prototype.bindById = function() {

	var key = "AjaxAnywhere." + this.id;
	window[key] = this;
}

/**
 * Finds an instance by id.
 */
AjaxAnywhere.findInstance = function(id) {

	var key = "AjaxAnywhere." + id;
	return window[key];
}

/**
 * This function is used to submit all form fields by AJAX resuest to the
 * server.
 */
AjaxAnywhere.prototype.submitAJAX = function(additionalPostData) {

	if (typeof additionalPostData == "undefined")
		additionalPostData = "";

	this.bindById();

	var form = this.findForm();
	var url = form.action;
	if (url == "")
		url = location.href;

	var zones = this.getZonesToReaload(url);

	if (zones == null) {
		if (typeof form.submit_old == "undefined")
			form.submit();
		else
			form.submit_old();
		return;
	}

	this.dropPreviousRequest();

	this.req.open("POST", url, true);
	this.req.setRequestHeader("Content-Type",
			"application/x-www-form-urlencoded");

	var postData = this.preparePostData();

	if (zones != "")
		postData = '&aazones=' + escape(zones) + "&" + postData + "&"
				+ additionalPostData;
	else
		postData += "&" + additionalPostData;

	this.sendPreparedRequest(postData);

}
/**
 * sends a GET request to the server.
 */
AjaxAnywhere.prototype.getAJAX = function(url) {
	this.bindById();

	var zones = this.getZonesToReaload(url);
	this.dropPreviousRequest();

	url += (url.indexOf("?") != -1) ? "&" : "?";

	url += "aa_rand=" + Math.random(); // avoid caching

	if (zones != null && zones != "")
		url += '&aazones=' + escape(zones);
	this.req.open("GET", url, true);

	this.sendPreparedRequest("");
}

/**
 * @private
 */
AjaxAnywhere.prototype.sendPreparedRequest = function(postData) {
	var callbackKey = this.id + "_callbackFunction";
	if (typeof window[callbackKey] == "undefined")
		window[callbackKey] = new Function("AjaxAnywhere.findInstance(\""
				+ this.id + "\").callback(); ");
	this.req.onreadystatechange = window[callbackKey];

	this.showLoadingMessage();
	this.req.setRequestHeader("aaxmlrequest", "true");
	this.req.send(postData);
}
/**
 * Used internally by AjaxAnywhere. Aborts previous request if not completed.
 */
AjaxAnywhere.prototype.dropPreviousRequest = function() {
	if (this.req.readyState != 0 && this.req.readyState != 4) {
		// abort previous request if not completed
		this.req.abort();
		this.handlePrevousRequestAborted();
	}
}

/**
 * Internally used to prepare Post data
 */
AjaxAnywhere.prototype.preparePostData = function() {

	var form = this.findForm();
	var result = "";
	for ( var i = 0; i < form.elements.length; i++) {
		var el = form.elements[i];
		if (el.tagName.toLowerCase() == "select") {
			for ( var j = 0; j < el.options.length; j++) {
				var op = el.options[j];
				if (op.selected)
					result += "&" + el.name + "=" + escape(op.value);
			}
		} else if (el.type.toLowerCase() == "checkbox"
				|| el.type.toLowerCase() == "radio") {
			if (el.checked)
				result += "&" + el.name + "=" + escape(el.value);
		} else if (el.type.toLowerCase() != "submit"
				&& el.type.toLowerCase() != "button") {
			result += "&" + el.name + "=" + escape(el.value);
		}
	}
	return result;
}

/**
 * A callback. internally used
 */
AjaxAnywhere.prototype.callback = function() {

	if (this.req.readyState == 4) {

		this.onBeforeResponseProcessing();
		this.hideLoadingMessage();

		if (this.req.status == 200) {

			var docs = this.req.responseXML.getElementsByTagName("document");
			var redirects = this.req.responseXML
					.getElementsByTagName("redirect");
			var zones = this.req.responseXML.getElementsByTagName("zone");
			var exceptions = this.req.responseXML
					.getElementsByTagName("exception");
			var scripts = this.req.responseXML.getElementsByTagName("script");

			if (redirects.length != 0) {
				var newURL = redirects[0].firstChild.data;
				location.href = newURL;
			}
			if (docs.length != 0) {
				var newContent = docs[0].firstChild.data;

				// cleanup ressources
				delete this.req;

				document.close();
				document.write(newContent);
				document.close();
			}
			if (zones.length != 0) {
				for ( var i = 0; i < zones.length; i++) {
					var zoneNode = zones[i];
					var name = zoneNode.getAttribute("name");
					var fc = zoneNode.firstChild;
					if (fc == null)
						continue;

					var html = fc.data;
					var zoneHolder = document.getElementById("aazone." + name);
					if (typeof (zoneHolder) != "undefined") {
						zoneHolder.innerHTML = html;
					}

				}
			}
			if (exceptions.length != 0) {
				var e = exceptions[0];
				var type = e.getAttribute("type");
				var stackTrace = e.firstChild.data;
				this.handleException(type, stackTrace);
			}

			if (scripts.length != 0) {
				for ( var i = 0; i < scripts.length; i++) {
					var script = scripts[i].firstChild;
					if (script != null) {
						script = script.data;
						if (script.indexOf("document.write") != -1) {
							this
									.handleException(
											"document.write",
											"This script contains document.write(), which is not compatible with AjaxAnywhere : \n\n"
													+ script);
						} else {
							eval(script);
						}
					}
				}

				var globals = this.getGlobalScriptsDeclarationsList(script);
				if (globals != null)
					for ( var i in globals) {
						var objName = globals[i];
						try {
							window[objName] = eval(objName);
						} catch (e) {
						}
					}
			}

		} else {
			if (this.req.status != 0)
				this.handleHttpErrorCode(this.req.status);
		}
		this.onAfterResponseProcessing();

	}

}

/**
 * Default sample loading message shuw function. Overrride it if you like.
 */
AjaxAnywhere.prototype.showLoadingMessage = function() {

	var div = document.getElementById("AA_" + this.id + "_loading_div");
	if (div == null) {
		div = document.createElement("DIV");

		document.body.appendChild(div);
		div.id = "AA_" + this.id + "_loading_div";

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

/**
 * Default sample loading message hide function. Overrride it if you like.
 */
AjaxAnywhere.prototype.hideLoadingMessage = function() {
	var div = document.getElementById("AA_" + this.id + "_loading_div");
	if (div != null)
		div.style.display = "none";

}

/**
 * This function is used to facilitatte AjaxAnywhere integration with existing
 * projects/frameworks. It substitutes default Form.sumbit(). The new
 * implementation calls AjaxAnywhere.isFormSubmitByAjax() function to find out
 * if the form should be submitted in traditional way or by AjaxAnywhere.
 */
AjaxAnywhere.prototype.substituteFormSubmitFunction = function() {

	this.bindById();

	var form = this.findForm();

	form.submit_old = form.submit;
	var code = "var ajax = AjaxAnywhere.findInstance(\"" + this.id + "\"); "
			+ "if (typeof ajax !='object' || ! ajax.isFormSubmitByAjax() ) "
			+ "ajax.findForm().submit_old();" + " else " + "ajax.submitAJAX();"
	form.submit = new Function(code);

}
/**
 * Override this function if you use AjaxAnywhere.substituteFormSubmitFunction()
 * to dynamically inform AjaxAnywhere of the method you want to use for the form
 * submission.
 */
AjaxAnywhere.prototype.isFormSubmitByAjax = function() {
	return true;
}

/**
 * If an exception is throws on the server-side during AJAX request, it will be
 * processed by this function. The default implementation is alert(stackTrace);
 * Override it if you need.
 */
AjaxAnywhere.prototype.handleException = function(type, details) {
	alert(details);
}
/**
 * If an HTTP Error code returned during AJAX request, it will be processed by
 * this function. The default implementation is alert(code); Override it if you
 * need.
 */
AjaxAnywhere.prototype.handleHttpErrorCode = function(code) {
	alert("AjaxAnywhere default error handler. XMLHttpRequest HTTP Error code:"
			+ code);
}

/**
 * Override it if you need.
 */
AjaxAnywhere.prototype.handlePrevousRequestAborted = function() {
	alert("AjaxAnywhere default error handler. INFO: previous AJAX request dropped")
}

/**
 * If the HTML received in responce to AJAX request contains JavaScript that
 * defines new functions/variables, they must be propagated to the proper
 * context. Override this method to return the Array of function/variable names.
 */
AjaxAnywhere.prototype.getGlobalScriptsDeclarationsList = function(script) {
	return null;
}

/**
 * This function should be overridden by AjaxAnywhere user to implement
 * client-side determination of zones to reload.
 * 
 * @Returns a comma separated list of zones to reload, or "document.all" to
 *          reload the whole page. Return null if the form must be submitted in
 *          traditional way
 * 
 * 
 */
AjaxAnywhere.prototype.getZonesToReaload = function(url) {
	return "";
}

/**
 * Override this method to implement a custom action
 */
AjaxAnywhere.prototype.onRequestSent = function() {
};
/**
 * Override this method to implement a custom action
 */
AjaxAnywhere.prototype.onBeforeResponseProcessing = function() {
};
/**
 * Override this method to implement a custom action
 */
AjaxAnywhere.prototype.onAfterResponseProcessing = function() {
};

// All customized methods for Pagination using AjaxAnywhere framework By Ramesh
// 18/12

// default instance.

function displayPaging(aaID, tableID, param) {
	ajaxAnywhere = new AjaxAnywhere();
	ajaxAnywhere.bindById();
	ajaxAnywhere.getZonesToReaload = function() {
		return aaID
	}
	ajaxAnywhere.onAfterResponseProcessing = function() {
		if ($(tableID) != null) {
			replaceLinks(tableID, param)
		}
	}
	if ($(tableID) != null) {
		replaceLinks(tableID, param);
	}

}
function replaceLinks(tableID, param) {
	if ($(tableID).getElementsByTagName('thead')[0] != null
			&& $(tableID).getElementsByTagName('thead')[0]
					.getElementsByTagName('a') > 0) {
		var sortLinks = $(tableID).getElementsByTagName('thead')[0]
				.getElementsByTagName('a');
		ajaxifyLinks(sortLinks, param);
	}
	if (document.getElementsByClassName('pagelinks').length > 0) {
		var pagelinks = document.getElementsByClassName('pagelinks')[0]
				.getElementsByTagName('a');
		ajaxifyLinks(pagelinks, param);
	}
	if (document.getElementsByClassName('exportlinks').length > 0) {
		var exportlinks = document.getElementsByClassName('exportlinks')[0]
				.getElementsByTagName('a');
		ajaxifyLinks(exportlinks, param);
	}
	if (document.getElementsByClassName('sortable').length > 0) {
		var sortlinks = document.getElementsByClassName('sortable')[0]
				.getElementsByTagName('a');
		ajaxifyLinks(sortlinks, param);
	}
}
function ajaxifyLinks(links, param) {
	for (i = 0; i < links.length; i++) {
		links[i].onclick = function() {
			var str = this.href;
			dispUrl = (this.href).split('?')[1];
			if (this.href.split('?')[1] == undefined) {
				str = str.substring(0,str.length-1) + "?param=paging";
			} else {
				str += "&param=paging";
			}
			if (param != null && param != undefined) {
				str += "&name=" + param;
			}
			if (type != null && type != undefined) {
				str += "&type=" + type;
			}
			ajaxAnywhere.getAJAX(str);
			return false;
		}
	}
}