package com.callippus.web.beans.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginBean {
	private String id;
    private String username;
    private String password;
    private String newpassword;
    private String confirmpassword;
    private String userId;
    private String sfid;
    private String ipAddress;
    private String status;
    private String name;
    private List loginList;
	private String param;
    private String type;
    private HashMap<String, String> menuLinks;
    private ArrayList roleList;
    private ArrayList<String> roleActions;
    private int teleCheck;
    private String browser;
    
     public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public int getTeleCheck() {
		return teleCheck;
	}

	public void setTeleCheck(int teleCheck) {
		this.teleCheck = teleCheck;
	}

	public ArrayList<String> getRoleActions() {
		return roleActions;
	}

	public void setRoleActions(ArrayList<String> roleActions) {
		this.roleActions = roleActions;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSfid() {
		return sfid;
	}

	public void setSfid(String sfid) {
		this.sfid = sfid;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public ArrayList getRoleList() {
		return roleList;
	}

	public void setRoleList(ArrayList roleList) {
		this.roleList = roleList;
	}

	public HashMap<String, String> getMenuLinks() {
        return menuLinks;
    }

    public void setMenuLinks(HashMap<String, String> menuLinks) {
        this.menuLinks = menuLinks;
    }

    public List getLoginList() {
		return loginList;
	}

	public void setLoginList(List loginList) {
		this.loginList = loginList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}



    public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}
}
