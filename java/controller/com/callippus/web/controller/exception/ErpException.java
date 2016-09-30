package com.callippus.web.controller.exception;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ErpException extends Exception implements Serializable {

	private ResultStatus resultStatus;

	public ErpException() {
		super();
	}

	public ErpException(Exception exception) {
		super(exception);
	}

	public ResultStatus getResultStatus() {
		return this.resultStatus;
	}

	public void setResultStatus(ResultStatus resultStatus) {
		this.resultStatus = resultStatus;
	}

	public String getMessage() {
		return super.getMessage();
	}
}
