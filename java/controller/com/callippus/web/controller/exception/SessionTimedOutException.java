package com.callippus.web.controller.exception;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SessionTimedOutException extends Exception implements Serializable {
	public SessionTimedOutException() {
		super();
	}

	public SessionTimedOutException(String ex) {
		super(ex);
	}
}
