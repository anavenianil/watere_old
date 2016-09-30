package com.callippus.web.tada.beans.management;

import java.util.List;

import com.callippus.web.beans.dto.EmpPaymentsDTO;
import com.callippus.web.beans.dto.TadaProjectDirectorsDTO;
import com.callippus.web.tada.dto.CityTypeDTO;
import com.callippus.web.tada.dto.LocalRMADTO;
import com.callippus.web.tada.dto.ProgramDTO;
import com.callippus.web.tada.dto.TaEntitleClassDTO;
import com.callippus.web.tada.dto.TaEntitleExemptionDTO;
import com.callippus.web.tada.dto.TaEntitleTypeDTO;
import com.callippus.web.tada.dto.TadaDaNewDetailsDTO;
import com.callippus.web.tada.dto.TadaDetailsDTO;
import com.callippus.web.tada.dto.TravelTypeDTO;

public class TadaManagementBean {
	private String param;
	private String sfID;
	private String result;
	private int payRangeFrom;
	private int payRangeTo;
	private int ord;
	private int hotel;
	private String type;
	private List<TadaDetailsDTO> daDetailsList;
	private int status;
	private int id;
	private int nodeID;
	private List<EmpPaymentsDTO> gradePayList;
	private String gradePay;
	private String entitleByRail;
	private String entitleByRoad;
	private String entitleType;
	private String entitleClass;
	private String nonEntitleClass;
	private String description;
	private List<TaEntitleTypeDTO> entitleTypeList;
	private String cityClass;
	private String cityName;
	private List<CityTypeDTO> cityTypeList;
	private List<CityTypeDTO> uniqueCityTypeList;
	private String travelType;
	private List<TravelTypeDTO> travelTypeList;
	private String exceptionsJson;
	private int entitleTypeId;
	private int entitleClassId;
	private int nonEntitleClassId;
	private List<TaEntitleClassDTO> entitleClassList;
	private int cityClassId;
	private int travelTypeId;

	private int accommodationBill;
	private int foodBill;
	private String milageAllw;
	private String travelBy;
	private int distance;
	private int amount;
	private List<TadaDaNewDetailsDTO> daNewDetailsList;
	private String fromPlace;
	private String toPlace;
	private String otherFromPlace;
	private String otherToPlace;
	private float localrmadistance;
	private List<LocalRMADTO> localRMAList;
	private List<TadaManagementBean> cityClassList;
	private String projectName;
	private String projectDirector;
	private List<TadaProjectDirectorsDTO> directorList;
	private String message;
	private String pk;
	private List<TadaManagementBean> tadaBeanList;
	private List<TadaManagementBean> projectsList;
	private String exemptionSfID;
	private List<TaEntitleExemptionDTO> entitleExemptionList;
	private String programName;
	private int programCode;
	private List<ProgramDTO> programList;
	
	private Float daRangeFrom;             //These Three properties has been definde for Da on tour
	private Float daRangeTo;
	private Float daOnTour;
	
	
	
	
	
	
	public Float getDaOnTour() {
		return daOnTour;
	}
	public void setDaOnTour(Float daOnTour) {
		this.daOnTour = daOnTour;
	}
	public Float getDaRangeFrom() {
		return daRangeFrom;
	}
	public void setDaRangeFrom(Float daRangeFrom) {
		this.daRangeFrom = daRangeFrom;
	}
	public Float getDaRangeTo() {
		return daRangeTo;
	}
	public void setDaRangeTo(Float daRangeTo) {
		this.daRangeTo = daRangeTo;
	}
	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
	}
	public String getProjectDirector() {
		return projectDirector;
	}
	public void setProjectDirector(String projectDirector) {
		this.projectDirector = projectDirector;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<TadaProjectDirectorsDTO> getDirectorList() {
		return directorList;
	}
	public void setDirectorList(List<TadaProjectDirectorsDTO> directorList) {
		this.directorList = directorList;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public List<TadaManagementBean> getCityClassList() {
		return cityClassList;
	}
	public void setCityClassList(List<TadaManagementBean> cityClassList) {
		this.cityClassList = cityClassList;
	}
	public List<LocalRMADTO> getLocalRMAList() {
		return localRMAList;
	}
	public void setLocalRMAList(List<LocalRMADTO> localRMAList) {
		this.localRMAList = localRMAList;
	}
	public float getLocalrmadistance() {
		return localrmadistance;
	}
	public void setLocalrmadistance(float localrmadistance) {
		this.localrmadistance = localrmadistance;
	}
	public String getOtherFromPlace() {
		return otherFromPlace;
	}
	public void setOtherFromPlace(String otherFromPlace) {
		this.otherFromPlace = otherFromPlace;
	}
	public String getOtherToPlace() {
		return otherToPlace;
	}
	public void setOtherToPlace(String otherToPlace) {
		this.otherToPlace = otherToPlace;
	}
	public String getFromPlace() {
		return fromPlace;
	}
	public void setFromPlace(String fromPlace) {
		this.fromPlace = fromPlace;
	}
	public String getToPlace() {
		return toPlace;
	}
	public void setToPlace(String toPlace) {
		this.toPlace = toPlace;
	}
	public String getNonEntitleClass() {
		return nonEntitleClass;
	}
	public void setNonEntitleClass(String nonEntitleClass) {
		this.nonEntitleClass = nonEntitleClass;
	}
	public int getNonEntitleClassId() {
		return nonEntitleClassId;
	}
	public void setNonEntitleClassId(int nonEntitleClassId) {
		this.nonEntitleClassId = nonEntitleClassId;
	}
	public List<TadaDaNewDetailsDTO> getDaNewDetailsList() {
		return daNewDetailsList;
	}
	public void setDaNewDetailsList(List<TadaDaNewDetailsDTO> daNewDetailsList) {
		this.daNewDetailsList = daNewDetailsList;
	}
	public int getAccommodationBill() {
		return accommodationBill;
	}
	public void setAccommodationBill(int accommodationBill) {
		this.accommodationBill = accommodationBill;
	}
	public int getFoodBill() {
		return foodBill;
	}
	public void setFoodBill(int foodBill) {
		this.foodBill = foodBill;
	}
	public String getMilageAllw() {
		return milageAllw;
	}
	public void setMilageAllw(String milageAllw) {
		this.milageAllw = milageAllw;
	}
	public String getTravelBy() {
		return travelBy;
	}
	public void setTravelBy(String travelBy) {
		this.travelBy = travelBy;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getTravelTypeId() {
		return travelTypeId;
	}
	public void setTravelTypeId(int travelTypeId) {
		this.travelTypeId = travelTypeId;
	}
	public int getCityClassId() {
		return cityClassId;
	}
	public void setCityClassId(int cityClassId) {
		this.cityClassId = cityClassId;
	}
	public List<TaEntitleClassDTO> getEntitleClassList() {
		return entitleClassList;
	}
	public void setEntitleClassList(List<TaEntitleClassDTO> entitleClassList) {
		this.entitleClassList = entitleClassList;
	}
	public int getEntitleClassId() {
		return entitleClassId;
	}
	public void setEntitleClassId(int entitleClassId) {
		this.entitleClassId = entitleClassId;
	}
	public int getEntitleTypeId() {
		return entitleTypeId;
	}
	public void setEntitleTypeId(int entitleTypeId) {
		this.entitleTypeId = entitleTypeId;
	}
	public String getExceptionsJson() {
		return exceptionsJson;
	}
	public void setExceptionsJson(String exceptionsJson) {
		this.exceptionsJson = exceptionsJson;
	}
	public List<TravelTypeDTO> getTravelTypeList() {
		return travelTypeList;
	}
	public void setTravelTypeList(List<TravelTypeDTO> travelTypeList) {
		this.travelTypeList = travelTypeList;
	}
	public String getTravelType() {
		return travelType;
	}
	public void setTravelType(String travelType) {
		this.travelType = travelType;
	}
	public List<CityTypeDTO> getUniqueCityTypeList() {
		return uniqueCityTypeList;
	}
	public void setUniqueCityTypeList(List<CityTypeDTO> uniqueCityTypeList) {
		this.uniqueCityTypeList = uniqueCityTypeList;
	}
	public List<CityTypeDTO> getCityTypeList() {
		return cityTypeList;
	}
	public void setCityTypeList(List<CityTypeDTO> cityTypeList) {
		this.cityTypeList = cityTypeList;
	}
	public String getCityClass() {
		return cityClass;
	}
	public void setCityClass(String cityClass) {
		this.cityClass = cityClass;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public List<EmpPaymentsDTO> getGradePayList() {
		return gradePayList;
	}
	public void setGradePayList(List<EmpPaymentsDTO> gradePayList) {
		this.gradePayList = gradePayList;
	}
	public String getGradePay() {
		return gradePay;
	}
	public void setGradePay(String gradePay) {
		this.gradePay = gradePay;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNodeID() {
		return nodeID;
	}
	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}
	public List<TaEntitleTypeDTO> getEntitleTypeList() {
		return entitleTypeList;
	}
	public void setEntitleTypeList(List<TaEntitleTypeDTO> entitleTypeList) {
		this.entitleTypeList = entitleTypeList;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getSfID() {
		return sfID;
	}
	public void setSfID(String sfID) {
		this.sfID = sfID;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public int getPayRangeFrom() {
		return payRangeFrom;
	}
	public void setPayRangeFrom(int payRangeFrom) {
		this.payRangeFrom = payRangeFrom;
	}
	public int getPayRangeTo() {
		return payRangeTo;
	}
	public void setPayRangeTo(int payRangeTo) {
		this.payRangeTo = payRangeTo;
	}
	public int getOrd() {
		return ord;
	}
	public void setOrd(int ord) {
		this.ord = ord;
	}
	public int getHotel() {
		return hotel;
	}
	public void setHotel(int hotel) {
		this.hotel = hotel;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<TadaDetailsDTO> getDaDetailsList() {
		return daDetailsList;
	}
	public void setDaDetailsList(List<TadaDetailsDTO> daDetailsList) {
		this.daDetailsList = daDetailsList;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	public String getEntitleByRail() {
		return entitleByRail;
	}
	public void setEntitleByRail(String entitleByRail) {
		this.entitleByRail = entitleByRail;
	}
	public String getEntitleByRoad() {
		return entitleByRoad;
	}
	public void setEntitleByRoad(String entitleByRoad) {
		this.entitleByRoad = entitleByRoad;
	}
	public String getEntitleType() {
		return entitleType;
	}
	public void setEntitleType(String entitleType) {
		this.entitleType = entitleType;
	}
	public String getEntitleClass() {
		return entitleClass;
	}
	public void setEntitleClass(String entitleClass) {
		this.entitleClass = entitleClass;
	}
	public List<TadaManagementBean> getTadaBeanList() {
		return tadaBeanList;
	}
	public void setTadaBeanList(List<TadaManagementBean> tadaBeanList) {
		this.tadaBeanList = tadaBeanList;
	}
	public List<TadaManagementBean> getProjectsList() {
		return projectsList;
	}
	public void setProjectsList(List<TadaManagementBean> projectsList) {
		this.projectsList = projectsList;
	}
	public String getExemptionSfID() {
		return exemptionSfID;
	}
	public void setExemptionSfID(String exemptionSfID) {
		this.exemptionSfID = exemptionSfID;
	}
	public List<TaEntitleExemptionDTO> getEntitleExemptionList() {
		return entitleExemptionList;
	}
	public void setEntitleExemptionList(
			List<TaEntitleExemptionDTO> entitleExemptionList) {
		this.entitleExemptionList = entitleExemptionList;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public int getProgramCode() {
		return programCode;
	}
	public void setProgramCode(int programCode) {
		this.programCode = programCode;
	}
	public List<ProgramDTO> getProgramList() {
		return programList;
	}
	public void setProgramList(List<ProgramDTO> programList) {
		this.programList = programList;
	}
	
	

	
	
	

}
