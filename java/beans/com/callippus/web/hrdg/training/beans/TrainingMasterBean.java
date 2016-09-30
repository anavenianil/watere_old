package com.callippus.web.hrdg.training.beans;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.web.multipart.MultipartFile;

import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.file.FileUploadBean;
import com.callippus.web.hrdg.file.UploadAndDownloadBean;




	

	public class TrainingMasterBean {
		
		private String typeValue;
		private String description;
		private String param;
		private String type;
		private String name;
		private String trainingTypeId;
		private String courseSubjCategoryId;
		private String courseSubjSubCategoryId;
		private String trainingInistituteId;
		private String trainingType;
		private String courseSubjCategory;
		private String courseSubjSubCategory;
		private String trainingInistitute;
		private String cityId;
		private String fax;
		private String email;
		private String address;
		private String ddFlag;
		private String headOfficeFlag;
		private String ddAddress;
		private String fee;
		private String serviceTax;
		private String designations;
		private String disciplines;
		private String discipline;
		private String shortName;
		private String payableAt;
		private String startDate;
		private String endDate;
		private String courseId;
		private String courseYear;
		private String boardTypeId;
		private String memberTypeId;
		private String yearId;
		private String year;
		private String board;
		private String boardId;
		private String course;
		private String qualification;
		private String qualifications;
		private String courseContent;
		private String maxLimit;
		

		private String circulationDate;
		private String durationId;
		private String venueId;
		private String nominationStartDate;
		private String nominationEndDate;
		private String organizer;
		private String brochure;
		private MultipartFile brochureFile;
		private String department;
		private String departments;
		private String ipAddress;
		private UploadAndDownloadBean uploadBean;
		private String brochureName;
		
		private MultipartFile uploadFile;
		private String desc;
		private int downloadId;
		private FileUploadBean fileUploadBean;
		private String fileId;
		
		private String trainingRegionId;
		private String trainingRegion;
		
		private String back;
		private String durationStartDate;
		

		private String durationEndDate;
		private String count;
		
		private String yearBookType;
		private String boardMemberSfid;
		
		private String orderNo;
		private String dtoName;
		private String nomineeSfid;
		private String nameInServiceBook;
		private EmployeeBean empDetails;
		private String ionId;
		private String letterFormatId;
		private List ionList;
	
		
		

		
		@SuppressWarnings("unchecked")
		private List masterDataList;
		private List fundTypeList;
		private String id;
		private String beanName;
		private String tableName;
		private String percentage;
		private String companyCode;
		private String categoryCode;
		private String categoryId;
		private String subCategoryId;
		private String subCategoryCode;
		private String itemCode;
		private String createdBy;
		private String creationDate;
		private String lastModifiedDate;
		private int status;
		private String itemCodeId;
		private String itemSubCode;
		private String materialCode;
		private String rcFlag;
		private String unitRate;
		private String consumableFlag;
		private String uom;
		private String materialName;
		private String categoryName;
		private String subCategoryName;
		private String codeName;
		private String subCodeName;
		private String companyName;
		private String uomName;
		private String itemSubCodeId;
		private String companyId;
		private String inventoryNo;
		private String sfid;
		private String demandPurchaseLimit;
		private String inventoryHolderType;
		private String holderName;
		private String directorateName;
		private String divisionName;
		private String designation;
		private String phone;
		private String projectName;
		private String invId;
		private String accountHeadNumber;
		private String qtyHeld;
		private String qtyRequired;
		private String allottedFund;
		
		private String fundTypeId;
		private String departmentId;
		private String consumedFund;
		private String commitedFund;
		private String searchWith;
		private String searchWithValue;
		private String accId;
		private JSONObject inventoryDetails;
		private String changedValues;
		private String orgRoleId;
		private String financialYear;
		private String message;
		private String purchaseLimitDesc;
		
		
		public String getPurchaseLimitDesc() {
			return purchaseLimitDesc;
		}

		public void setPurchaseLimitDesc(String purchaseLimitDesc) {
			this.purchaseLimitDesc = purchaseLimitDesc;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getFinancialYear() {
			return financialYear;
		}

		public void setFinancialYear(String financialYear) {
			this.financialYear = financialYear;
		}

		public String getOrgRoleId() {
			return orgRoleId;
		}

		public void setOrgRoleId(String orgRoleId) {
			this.orgRoleId = orgRoleId;
		}

		public String getChangedValues() {
			return changedValues;
		}

		public void setChangedValues(String changedValues) {
			this.changedValues = changedValues;
		}

		public JSONObject getInventoryDetails() {
			return inventoryDetails;
		}

		public void setInventoryDetails(JSONObject inventoryDetails) {
			this.inventoryDetails = inventoryDetails;
		}

		public List getFundTypeList() {
			return fundTypeList;
		}

		public void setFundTypeList(List fundTypeList) {
			this.fundTypeList = fundTypeList;
		}

		public String getAccId() {
			return accId;
		}

		public void setAccId(String accId) {
			this.accId = accId;
		}

		public String getSearchWithValue() {
			return searchWithValue;
		}

		public void setSearchWithValue(String searchWithValue) {
			this.searchWithValue = searchWithValue;
		}

		public String getSearchWith() {
			return searchWith;
		}

		public void setSearchWith(String searchWith) {
			this.searchWith = searchWith;
		}

		public String getAccountHeadNumber() {
			return accountHeadNumber;
		}

		public void setAccountHeadNumber(String accountHeadNumber) {
			this.accountHeadNumber = accountHeadNumber;
		}

		public String getQtyHeld() {
			return qtyHeld;
		}

		public void setQtyHeld(String qtyHeld) {
			this.qtyHeld = qtyHeld;
		}

		public String getQtyRequired() {
			return qtyRequired;
		}

		public void setQtyRequired(String qtyRequired) {
			this.qtyRequired = qtyRequired;
		}

		public String getAllottedFund() {
			return allottedFund;
		}

		public void setAllottedFund(String allottedFund) {
			this.allottedFund = allottedFund;
		}

		public String getYear() {
			return year;
		}

		public void setYear(String year) {
			this.year = year;
		}

		public String getFundTypeId() {
			return fundTypeId;
		}

		public void setFundTypeId(String fundTypeId) {
			this.fundTypeId = fundTypeId;
		}

		public String getDepartmentId() {
			return departmentId;
		}

		public void setDepartmentId(String departmentId) {
			this.departmentId = departmentId;
		}

		public String getConsumedFund() {
			return consumedFund;
		}

		public void setConsumedFund(String consumedFund) {
			this.consumedFund = consumedFund;
		}

		public String getCommitedFund() {
			return commitedFund;
		}

		public void setCommitedFund(String commitedFund) {
			this.commitedFund = commitedFund;
		}

		public String getInvId() {
			return invId;
		}

		public void setInvId(String invId) {
			this.invId = invId;
		}

		public String getProjectName() {
			return projectName;
		}

		public void setProjectName(String projectName) {
			this.projectName = projectName;
		}

		public String getSfid() {
			return sfid;
		}

		public void setSfid(String sfid) {
			this.sfid = sfid;
		}

		public String getDemandPurchaseLimit() {
			return demandPurchaseLimit;
		}

		public void setDemandPurchaseLimit(String demandPurchaseLimit) {
			this.demandPurchaseLimit = demandPurchaseLimit;
		}

		public String getInventoryHolderType() {
			return inventoryHolderType;
		}

		public void setInventoryHolderType(String inventoryHolderType) {
			this.inventoryHolderType = inventoryHolderType;
		}

		public String getHolderName() {
			return holderName;
		}

		public void setHolderName(String holderName) {
			this.holderName = holderName;
		}

		public String getDirectorateName() {
			return directorateName;
		}

		public void setDirectorateName(String directorateName) {
			this.directorateName = directorateName;
		}

		public String getDivisionName() {
			return divisionName;
		}

		public void setDivisionName(String divisionName) {
			this.divisionName = divisionName;
		}

		public String getDesignation() {
			return designation;
		}

		public void setDesignation(String designation) {
			this.designation = designation;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getInventoryNo() {
			return inventoryNo;
		}

		public void setInventoryNo(String inventoryNo) {
			this.inventoryNo = inventoryNo;
		}

		public String getItemSubCodeId() {
			return itemSubCodeId;
		}

		public void setItemSubCodeId(String itemSubCodeId) {
			this.itemSubCodeId = itemSubCodeId;
		}

		public String getCompanyId() {
			return companyId;
		}

		public void setCompanyId(String companyId) {
			this.companyId = companyId;
		}

		public String getMaterialName() {
			return materialName;
		}

		public void setMaterialName(String materialName) {
			this.materialName = materialName;
		}

		public String getCategoryName() {
			return categoryName;
		}

		public void setCategoryName(String categoryName) {
			this.categoryName = categoryName;
		}

		public String getSubCategoryName() {
			return subCategoryName;
		}

		public void setSubCategoryName(String subCategoryName) {
			this.subCategoryName = subCategoryName;
		}

		public String getCodeName() {
			return codeName;
		}

		public void setCodeName(String codeName) {
			this.codeName = codeName;
		}

		public String getSubCodeName() {
			return subCodeName;
		}

		public void setSubCodeName(String subCodeName) {
			this.subCodeName = subCodeName;
		}

		public String getCompanyName() {
			return companyName;
		}

		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}

		public String getUomName() {
			return uomName;
		}

		public void setUomName(String uomName) {
			this.uomName = uomName;
		}

		public String getUom() {
			return uom;
		}

		public void setUom(String uom) {
			this.uom = uom;
		}

		public String getMaterialCode() {
			return materialCode;
		}

		public void setMaterialCode(String materialCode) {
			this.materialCode = materialCode;
		}

		public String getRcFlag() {
			return rcFlag;
		}

		public void setRcFlag(String rcFlag) {
			this.rcFlag = rcFlag;
		}

		public String getUnitRate() {
			return unitRate;
		}

		public void setUnitRate(String unitRate) {
			this.unitRate = unitRate;
		}

		public String getConsumableFlag() {
			return consumableFlag;
		}

		public void setConsumableFlag(String consumableFlag) {
			this.consumableFlag = consumableFlag;
		}

		public String getItemCodeId() {
			return itemCodeId;
		}

		public void setItemCodeId(String itemCodeId) {
			this.itemCodeId = itemCodeId;
		}

		public String getItemSubCode() {
			return itemSubCode;
		}

		public void setItemSubCode(String itemSubCode) {
			this.itemSubCode = itemSubCode;
		}

		public String getItemCode() {
			return itemCode;
		}

		public void setItemCode(String itemCode) {
			this.itemCode = itemCode;
		}

		public String getSubCategoryId() {
			return subCategoryId;
		}

		public void setSubCategoryId(String subCategoryId) {
			this.subCategoryId = subCategoryId;
		}

		public String getSubCategoryCode() {
			return subCategoryCode;
		}

		public void setSubCategoryCode(String subCategoryCode) {
			this.subCategoryCode = subCategoryCode;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public String getCreationDate() {
			return creationDate;
		}

		public void setCreationDate(String creationDate) {
			this.creationDate = creationDate;
		}

		public String getLastModifiedDate() {
			return lastModifiedDate;
		}

		public void setLastModifiedDate(String lastModifiedDate) {
			this.lastModifiedDate = lastModifiedDate;
		}

		public String getCategoryId() {
			return categoryId;
		}

		public void setCategoryId(String categoryId) {
			this.categoryId = categoryId;
		}

		public String getCategoryCode() {
			return categoryCode;
		}

		public void setCategoryCode(String categoryCode) {
			this.categoryCode = categoryCode;
		}

		public String getCompanyCode() {
			return companyCode;
		}

		public void setCompanyCode(String companyCode) {
			this.companyCode = companyCode;
		}
		
		public String getPercentage() {
			return percentage;
		}

		public void setPercentage(String percentage) {
			this.percentage = percentage;
		}

		public String getBeanName() {
			return beanName;
		}

		public void setBeanName(String beanName) {
			this.beanName = beanName;
		}

		public String getTableName() {
			return tableName;
		}

		public void setTableName(String tableName) {
			this.tableName = tableName;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public List getMasterDataList() {
			return masterDataList;
		}

		public void setMasterDataList(List masterDataList) {
			this.masterDataList = masterDataList;
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

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getTypeValue() {
			return typeValue;
		}

		public void setTypeValue(String typeValue) {
			this.typeValue = typeValue;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
		public String getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}
		public String getTrainingTypeId() {
			return trainingTypeId;
		}

		public void setTrainingTypeId(String trainingTypeId) {
			this.trainingTypeId = trainingTypeId;
		}

		public String getCourseSubjCategoryId() {
			return courseSubjCategoryId;
		}

		public void setCourseSubjCategoryId(String courseSubjCategoryId) {
			this.courseSubjCategoryId = courseSubjCategoryId;
		}

		public String getCourseSubjSubCategoryId() {
			return courseSubjSubCategoryId;
		}

		public void setCourseSubjSubCategoryId(String courseSubjSubCategoryId) {
			this.courseSubjSubCategoryId = courseSubjSubCategoryId;
		}
		public String getTrainingInistituteId() {
			return trainingInistituteId;
		}

		public void setTrainingInistituteId(String trainingInistituteId) {
			this.trainingInistituteId = trainingInistituteId;
		}
		public String getCityId() {
			return cityId;
		}

		public void setCityId(String cityId) {
			this.cityId = cityId;
		}
		public String getFax() {
			return fax;
		}

		public void setFax(String fax) {
			this.fax = fax;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getDdFlag() {
			return ddFlag;
		}

		public void setDdFlag(String ddFlag) {
			this.ddFlag = ddFlag;
		}

		public String getHeadOfficeFlag() {
			return headOfficeFlag;
		}

		public void setHeadOfficeFlag(String headOfficeFlag) {
			this.headOfficeFlag = headOfficeFlag;
		}
		public String getDdAddress() {
			return ddAddress;
		}

		public void setDdAddress(String ddAddress) {
			this.ddAddress = ddAddress;
		}
		public String getFee() {
			return fee;
		}

		public void setFee(String fee) {
			this.fee = fee;
		}

		public String getServiceTax() {
			return serviceTax;
		}

		public void setServiceTax(String serviceTax) {
			this.serviceTax = serviceTax;
		}
		public String getDesignations() {
			return designations;
		}

		public void setDesignations(String designations) {
			this.designations = designations;
		}


		public String getDisciplines() {
			return disciplines;
		}

		public void setDisciplines(String disciplines) {
			this.disciplines = disciplines;
		}


		
		public String getDiscipline() {
			return discipline;
		}

		public void setDiscipline(String discipline) {
			this.discipline = discipline;
		}


		public String getShortName() {
			return shortName;
		}

		public void setShortName(String shortName) {
			this.shortName = shortName;
		}


		public String getPayableAt() {
			return payableAt;
		}

		public void setPayableAt(String payableAt) {
			this.payableAt = payableAt;
		}
		public String getStartDate() {
			return startDate;
		}

		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}

		public String getEndDate() {
			return endDate;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}

		public String getCourseId() {
			return courseId;
		}

		public void setCourseId(String courseId) {
			this.courseId = courseId;
		}


		
		public String getCourseYear() {
			return courseYear;
		}

		public void setCourseYear(String courseYear) {
			this.courseYear = courseYear;
		}
		public String getTrainingType() {
			return trainingType;
		}

		public void setTrainingType(String trainingType) {
			this.trainingType = trainingType;
		}

		public String getCourseSubjCategory() {
			return courseSubjCategory;
		}

		public void setCourseSubjCategory(String courseSubjCategory) {
			this.courseSubjCategory = courseSubjCategory;
		}

		public String getCourseSubjSubCategory() {
			return courseSubjSubCategory;
		}

		public void setCourseSubjSubCategory(String courseSubjSubCategory) {
			this.courseSubjSubCategory = courseSubjSubCategory;
		}

		public String getTrainingInistitute() {
			return trainingInistitute;
		}

		public void setTrainingInistitute(String trainingInistitute) {
			this.trainingInistitute = trainingInistitute;
		}
		public String getBoardTypeId() {
			return boardTypeId;
		}

		public void setBoardTypeId(String boardTypeId) {
			this.boardTypeId = boardTypeId;
		}

		public String getMemberTypeId() {
			return memberTypeId;
		}

		public void setMemberTypeId(String memberTypeId) {
			this.memberTypeId = memberTypeId;
		}

		
		public String getYearId() {
			return yearId;
		}

		public void setYearId(String yearId) {
			this.yearId = yearId;
		}

		

		public String getBoardId() {
			return boardId;
		}

		public void setBoardId(String boardId) {
			this.boardId = boardId;
		}
		public String getCirculationDate() {
			return circulationDate;
		}

		public void setCirculationDate(String circulationDate) {
			this.circulationDate = circulationDate;
		}

		public String getDurationId() {
			return durationId;
		}

		public void setDurationId(String durationId) {
			this.durationId = durationId;
		}

		public String getVenueId() {
			return venueId;
		}

		public void setVenueId(String venueId) {
			this.venueId = venueId;
		}

		public String getNominationStartDate() {
			return nominationStartDate;
		}

		public void setNominationStartDate(String nominationStartDate) {
			this.nominationStartDate = nominationStartDate;
		}

		public String getNominationEndDate() {
			return nominationEndDate;
		}

		public void setNominationEndDate(String nominationEndDate) {
			this.nominationEndDate = nominationEndDate;
		}

		public String getOrganizer() {
			return organizer;
		}

		public void setOrganizer(String organizer) {
			this.organizer = organizer;
		}

		public String getBrochure() {
			return brochure;
		}

		public void setBrochure(String brochure) {
			this.brochure = brochure;
		}
		public String getDepartment() {
			return department;
		}

		public void setDepartment(String department) {
			this.department = department;
		}

		public String getDepartments() {
			return departments;
		}

		public void setDepartments(String departments) {
			this.departments = departments;
		}
		public MultipartFile getBrochureFile() {
			return brochureFile;
		}

		public void setBrochureFile(MultipartFile brochureFile) {
			this.brochureFile = brochureFile;
		}


		public String getIpAddress() {
			return ipAddress;
		}

		public void setIpAddress(String ipAddress) {
			this.ipAddress = ipAddress;
		}

		

		public UploadAndDownloadBean getUploadBean() {
			return uploadBean;
		}

		public void setUploadBean(UploadAndDownloadBean uploadBean) {
			this.uploadBean = uploadBean;
		}


		public String getBrochureName() {
			return brochureName;
		}

		public void setBrochureName(String brochureName) {
			this.brochureName = brochureName;
		}
		public MultipartFile getUploadFile() {
			return uploadFile;
		}

		public void setUploadFile(MultipartFile uploadFile) {
			this.uploadFile = uploadFile;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public int getDownloadId() {
			return downloadId;
		}

		public void setDownloadId(int downloadId) {
			this.downloadId = downloadId;
		}

		public FileUploadBean getFileUploadBean() {
			return fileUploadBean;
		}

		public void setFileUploadBean(FileUploadBean fileUploadBean) {
			this.fileUploadBean = fileUploadBean;
		}


		public String getFileId() {
			return fileId;
		}

		public void setFileId(String fileId) {
			this.fileId = fileId;
		}
		public String getTrainingRegionId() {
			return trainingRegionId;
		}

		public void setTrainingRegionId(String trainingRegionId) {
			this.trainingRegionId = trainingRegionId;
		}

		public String getTrainingRegion() {
			return trainingRegion;
		}

		public void setTrainingRegion(String trainingRegion) {
			this.trainingRegion = trainingRegion;
		}


		public String getBack() {
			return back;
		}

		public void setBack(String back) {
			this.back = back;
		}
		public String getBoard() {
			return board;
		}

		public void setBoard(String board) {
			this.board = board;
		}
		public String getCourse() {
			return course;
		}

		public void setCourse(String course) {
			this.course = course;
		}
		public String getQualification() {
			return qualification;
		}

		public void setQualification(String qualification) {
			this.qualification = qualification;
		}

		public String getQualifications() {
			return qualifications;
		}

		public void setQualifications(String qualifications) {
			this.qualifications = qualifications;
		}


		
		public String getCourseContent() {
			return courseContent;
		}

		public void setCourseContent(String courseContent) {
			this.courseContent = courseContent;
		}
		public String getDurationStartDate() {
			return durationStartDate;
		}

		public void setDurationStartDate(String durationStartDate) { 
			this.durationStartDate = durationStartDate;
		}

		public String getDurationEndDate() {
			return durationEndDate;
		}

		public void setDurationEndDate(String durationEndDate) { 
			this.durationEndDate = durationEndDate;
		}



		public String getCount() {
			return count;
		}

		public void setCount(String count) {
			this.count = count;
		}

		public String getYearBookType() {
			return yearBookType;
		}

		public void setYearBookType(String yearBookType) {
			this.yearBookType = yearBookType;
		}



		public String getBoardMemberSfid() {
			return boardMemberSfid;
		}

		public void setBoardMemberSfid(String boardMemberSfid) {
			this.boardMemberSfid = boardMemberSfid;
		}


		public String getOrderNo() {
			return orderNo;
		}

		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}


		public String getDtoName() {
			return dtoName;
		}

		public void setDtoName(String dtoName) {
			this.dtoName = dtoName;
		}

		public String getNomineeSfid() {
			return nomineeSfid;
		}

		public void setNomineeSfid(String nomineeSfid) {
			this.nomineeSfid = nomineeSfid;
		}


		public String getNameInServiceBook() {
			return nameInServiceBook;
		}

		public void setNameInServiceBook(String nameInServiceBook) {
			this.nameInServiceBook = nameInServiceBook;
		}


		public EmployeeBean getEmpDetails() {
			return empDetails;
		}

		public void setEmpDetails(EmployeeBean empDetails) {
			this.empDetails = empDetails;
		}

		public String getIonId() {
			return ionId;
		}

		public void setIonId(String ionId) {
			this.ionId = ionId;
		}


		public String getLetterFormatId() {
			return letterFormatId;
		}

		public void setLetterFormatId(String letterFormatId) {
			this.letterFormatId = letterFormatId;
		}


		public List getIonList() {
			return ionList;
		}

		public void setIonList(List ionList) {
			this.ionList = ionList;
		}

		public String getMaxLimit() {
			return maxLimit;
		}

		public void setMaxLimit(String maxLimit) {
			this.maxLimit = maxLimit;
		}

	}



