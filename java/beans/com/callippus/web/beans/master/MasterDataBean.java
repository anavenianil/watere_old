package com.callippus.web.beans.master;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSON;

import com.callippus.web.beans.dto.CategoryDTO;
import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.GroupDTO;
import com.callippus.web.beans.dto.LetterNumberReferenceDTO;
import com.callippus.web.beans.dto.PinNumberDTO;
import com.callippus.web.beans.dto.StateTypeDTO;

public class MasterDataBean {
	private String param;
	private String type;
	private String reqParam;
	private String beanName;
	private List masterDataList;
	private String tableName;
	private String typeValue;
	private String id;
	private String name;
	private ArrayList<CategoryDTO> categoryList;
	private JSON categoryListJSON;
	private String category;
	private String subCategory;
	private String subID;
	private String message;
	private String newCategory;
	private String newCategoryID;
	private List<CategoryDTO> subCategoryList;
	private List<GroupDTO> groupList;
	private ArrayList<DesignationDTO> designationDisplayTable;
	private String group;
	private String designationId;
	private String stateId;
	private String description;
	private List<StateTypeDTO> stateList;
	private List<LetterNumberReferenceDTO> letterList;
	

	private String parentID;
	private String shortForm;
	private String changeSfid;
	private String changeSfidName;
	private String requestFlags;
	private String serviceType;
	private String orderNo;
	private String religion;
	private String pin;
	private String flag;
	private String seniority;
	private String alias;
	private String desigAlias;
	private String awardCatId;
	private String awardTypeId;
	private String awardMaxLimit;
	private String awardMoney;
	private String remarks;
	
	
	private String letterDate;
	private String letterNumber;
	private String fromPalce;
	private String toPalce;
	private Integer status;
	
	private String sfid;
	private String year;
    private List<PinNumberDTO> sfidPinNoList;

	public String getSfid() {
		return sfid;
	}

	public void setSfid(String sfid) {
		this.sfid = sfid;
	}

	//
	public List<LetterNumberReferenceDTO> getLetterList() {
		return letterList;
	}

	public void setLetterList(List<LetterNumberReferenceDTO> letterList) {
		this.letterList = letterList;
	}
	public String getLetterDate() {
		return letterDate;
	}

	public void setLetterDate(String letterDate) {
		this.letterDate = letterDate;
	}
	public String getLetterNumber() {
		return letterNumber;
	}

	public void setLetterNumber(String letterNumber) {
		this.letterNumber = letterNumber;
	}

	public String getFromPalce() {
		return fromPalce;
	}

	public void setFromPalce(String fromPalce) {
		this.fromPalce = fromPalce;
	}

	public String getToPalce() {
		return toPalce;
	}

	public void setToPalce(String toPalce) {
		this.toPalce = toPalce;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	//
	public String getDesigAlias() {
		return desigAlias;
	}

	public void setDesigAlias(String desigAlias) {
		this.desigAlias = desigAlias;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	private String sfID;

	public String getSfID() {
		return sfID;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
	}

	public String getSeniority() {
		return seniority;
	}

	public void setSeniority(String seniority) {
		this.seniority = seniority;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getRequestFlags() {
		return requestFlags;
	}

	public void setRequestFlags(String requestFlags) {
		this.requestFlags = requestFlags;
	}

	public String getChangeSfidName() {
		return changeSfidName;
	}

	public void setChangeSfidName(String changeSfidName) {
		this.changeSfidName = changeSfidName;
	}

	public String getChangeSfid() {
		return changeSfid;
	}

	public void setChangeSfid(String changeSfid) {
		this.changeSfid = changeSfid;
	}

	public String getShortForm() {
		return shortForm;
	}

	public void setShortForm(String shortForm) {
		this.shortForm = shortForm;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public List<StateTypeDTO> getStateList() {
		return stateList;
	}

	public void setStateList(List<StateTypeDTO> stateList) {
		this.stateList = stateList;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getDesignationId() {
		return designationId;
	}

	public void setDesignationId(String designationId) {
		this.designationId = designationId;
	}

	public String getNewCategoryID() {
		return newCategoryID;
	}

	public void setNewCategoryID(String newCategoryID) {
		this.newCategoryID = newCategoryID;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public List<CategoryDTO> getSubCategoryList() {
		return subCategoryList;
	}

	public void setSubCategoryList(List<CategoryDTO> subCategoryList) {
		this.subCategoryList = subCategoryList;
	}

	public List<GroupDTO> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<GroupDTO> groupList) {
		this.groupList = groupList;
	}

	public String getNewCategory() {
		return newCategory;
	}

	public ArrayList<DesignationDTO> getDesignationDisplayTable() {
		return designationDisplayTable;
	}

	public void setDesignationDisplayTable(ArrayList<DesignationDTO> designationDisplayTable) {
		this.designationDisplayTable = designationDisplayTable;
	}

	public void setNewCategory(String newCategory) {
		this.newCategory = newCategory;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSubID() {
		return subID;
	}

	public void setSubID(String subID) {
		this.subID = subID;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public JSON getCategoryListJSON() {
		return categoryListJSON;
	}

	public void setCategoryListJSON(JSON categoryListJSON) {
		this.categoryListJSON = categoryListJSON;
	}

	public ArrayList<CategoryDTO> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(ArrayList<CategoryDTO> categoryList) {
		this.categoryList = categoryList;
	}

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public List getMasterDataList() {
		return masterDataList;
	}

	public void setMasterDataList(List masterDataList) {
		this.masterDataList = masterDataList;
	}

	public String getReqParam() {
		return reqParam;
	}

	public void setReqParam(String reqParam) {
		this.reqParam = reqParam;
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

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	
	public String getAwardCatId() {
		return awardCatId;
	}

	public void setAwardCatId(String awardCatId) {
		this.awardCatId = awardCatId;
	}

	public String getAwardTypeId() {
		return awardTypeId;
	}

	public void setAwardTypeId(String awardTypeId) {
		this.awardTypeId = awardTypeId;
	}

	public String getAwardMaxLimit() {
		return awardMaxLimit;
	}

	public void setAwardMaxLimit(String awardMaxLimit) {
		this.awardMaxLimit = awardMaxLimit;
	}

	public String getAwardMoney() {
		return awardMoney;
	}

	public void setAwardMoney(String awardMoney) {
		this.awardMoney = awardMoney;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public List<PinNumberDTO> getSfidPinNoList() {
		return sfidPinNoList;
	}

	public void setSfidPinNoList(List<PinNumberDTO> sfidPinNoList) {
		this.sfidPinNoList = sfidPinNoList;
	}

	
	
}