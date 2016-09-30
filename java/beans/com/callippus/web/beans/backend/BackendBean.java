package com.callippus.web.beans.backend;

import javax.xml.ws.BindingType;

@BindingType
public class BackendBean {
	private String result;
	private String param;

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
