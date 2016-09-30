package com.callippus.web.beans.letterNumberFormat;

import java.io.Serializable;

public class IonFileDTO implements Serializable{
	private Integer ionId;
	public Integer getIonId() {
		return ionId;
	}
	public void setIonId(Integer ionId) {
		this.ionId = ionId;
	}
	public Integer getFileId() {
		return fileId;
	}
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	private Integer fileId;
	

}
