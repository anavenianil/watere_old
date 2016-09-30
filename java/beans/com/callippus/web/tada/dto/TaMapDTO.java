package com.callippus.web.tada.dto;

public class TaMapDTO {
	private int id;
    private String name;
    private String key;
    private String flag;
    private String value;
    private int travelTypeId;
    

    public int getTravelTypeId() {
		return travelTypeId;
	}

	public void setTravelTypeId(int travelTypeId) {
		this.travelTypeId = travelTypeId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
