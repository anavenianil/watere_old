package com.callippus.web.beans.MT;

import com.callippus.web.beans.dto.CommonDTO;

public class DriverDetailsBean extends CommonDTO{

	private int driverId;
	private int driverType;
	private String driverName;
	private String driverMobileNo;
	private String driverStatus;
	private String driverTravelAgencyNameDetail;
	private String driverTypeName;
	private String driverIdSfid;
	private String driverTravelAgencyName;
	private VehicleTypeDetailsBean driverTypeDetails;
	
	
	public VehicleTypeDetailsBean getDriverTypeDetails() {
		return driverTypeDetails;
	}

	public void setDriverTypeDetails(VehicleTypeDetailsBean driverTypeDetails) {
		this.driverTypeDetails = driverTypeDetails;
	}

	public String getDriverTravelAgencyName() {
		return driverTravelAgencyName;
	}

	public void setDriverTravelAgencyName(String driverTravelAgencyName) {
		this.driverTravelAgencyName = driverTravelAgencyName;
	}


	public String getDriverIdSfid() {
		return driverIdSfid;
	}

	public void setDriverIdSfid(String driverIdSfid) {
		this.driverIdSfid = driverIdSfid;
	}

	public String getDriverTypeName() {
		return driverTypeName;
	}

	public void setDriverTypeName(String driverTypeName) {
		this.driverTypeName = driverTypeName;
	}

	public String getDriverMobileNo() {
		return driverMobileNo;
	}

	public void setDriverMobileNo(String driverMobileNo) {
		this.driverMobileNo = driverMobileNo;
	}

	public int getDriverType() {
		return driverType;
	}

	public void setDriverType(int driverType) {
		this.driverType = driverType;
	}

	public String getDriverTravelAgencyNameDetail() {
		return driverTravelAgencyNameDetail;
	}

	public void setDriverStatus(String driverStatus) {
		this.driverStatus = driverStatus;
	}

	
	public int getDriverId() {
		return driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

	public void setDriverTravelAgencyNameDetail(String driverTravelAgencyNameDetail) {
		this.driverTravelAgencyNameDetail = driverTravelAgencyNameDetail;
	}

	public String getDriverStatus() {
		return driverStatus;
	}

	
	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

}
