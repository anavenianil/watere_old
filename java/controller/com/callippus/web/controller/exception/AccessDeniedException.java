package com.callippus.web.controller.exception;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AccessDeniedException extends Exception implements Serializable {
	public AccessDeniedException() {
		super();
	}

	public AccessDeniedException(String exception) {
		super(exception);
	}

	public String toString() {
		return "Sorry Access Denied";
	}
}
