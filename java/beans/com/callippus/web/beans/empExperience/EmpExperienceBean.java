package com.callippus.web.beans.empExperience;

public class EmpExperienceBean {
	private String id;
	private String sfid;
	private String experience;
	private String description;
	private String fromDate;
	private String toDate;
	private int status;
	private String creationDate;
	private String lastModifiedDate;
	private String param;
	private String type;
	
	public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getParam() {
        return param;
    }
    public void setParam(String param) {
        this.param = param;
    }
    public String getId()
	{
		return this.id;
	}
	public void setId(String id)
	{
		this.id=id;
	}
	public String getSfid()
	{
		return this.sfid;
	}
	public void setSfid(String sfid)
	{
		this.sfid=sfid;
	}
	public String getExperience()
	{
		return this.experience;
	}
	public void setExperience(String experience)
	{
		this.experience=experience;
	}
	public String getDescription()
	{
		return this.description;
	}
	public void setDescription(String description)
	{
		this.description=description;
	}
	public String getFromDate()
	{
		return this.fromDate;
	}
	public void setFromDate(String fromDate)
	{
		this.fromDate=fromDate;
	}
	public String getToDate()
	{
		return this.toDate;
	}
	public void setToDate(String toDate)
	{
		this.toDate=toDate;
	}
	public int getStatus()
	{
		return this.status;
	}
	public void setStatus(int status)
	{
		this.status=status;
	}
	public String getCreationDate()
	{
		return this.creationDate;
	}
	public void setCreationDate(String creationDate)
	{
		this.creationDate=creationDate;
	}
	public String getLastModifiedDate()
	{
		return this.lastModifiedDate;
	}
	public void setLastModifiedDate(String lastModifiedDate)
	{
		this.lastModifiedDate=lastModifiedDate;
	}


}
