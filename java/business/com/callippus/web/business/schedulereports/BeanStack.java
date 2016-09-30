package com.callippus.web.business.schedulereports;

import java.math.*;

public class BeanStack {
	private BigDecimal CGEISTotal, ConPageTotal, SHECTotal, TotalRecTotal, PageTotal, FurTotal, EduTotal, ITAXTotal, HBATotal, ElecTotal, 
						WaterTotal, RentTotal, PITotal, CGHSTotal, GPFTotal, GPFSATotal, GPFRTotal, GPFPageTotal, carinstamt, scooterinstamt, 
						bicycleinstamt,PLITotal,LFTotal,carTotal,scooterTotal,cycleTotal,hbadvTotal,pclnTotal,festadvTot,itaxTotal,educessTotal, 
						shecTotal,ptaxTotal,tadaTotal,ltcTotal,medTotal,eolhplTotal,misc1Total,misc2Total,totalDeduction,totalnetPay,ITAXCESSSHECTotal, 
						PayTotal,MessTotal,RESTotal,CCSTotal,HDFCTotal,CANFINTotal,LICTotal,GICTotal,CAttachTotal,MiscTotal,gpayTotal,daTotal,splpayTotal,fpaTotal, 
						addincrTotal,traTotal,hraTotal,wallowTotal,xallowTotal,vincrTotal,totalpayTotal,TotalDedTotal,NetPayTotal,WsubTotal,WloanTotal, 
						BFTotal,RFTotal, cpfTotal, tptTotal;
	private int totalAmt;
 	private int netPMTTotal;
 	private Integer OTTotal;
 	//for NPS report
 	private BigDecimal Tier1SubTotal,Tier1ArrTotal,ContTotal;
 	
 	public BigDecimal getTier1SubTotal() {
		return Tier1SubTotal;
	}

	public void setTier1SubTotal(BigDecimal tier1SubTotal) {
		Tier1SubTotal = tier1SubTotal;
	}

	public BigDecimal getTier1ArrTotal() {
		return Tier1ArrTotal;
	}

	public void setTier1ArrTotal(BigDecimal tier1ArrTotal) {
		Tier1ArrTotal = tier1ArrTotal;
	}

	public BigDecimal getContTotal() {
		return ContTotal;
	}

	public void setContTotal(BigDecimal contTotal) {
		ContTotal = contTotal;
	}

	//for DAArrearsAllCategoryList Report
	private BigDecimal total, CPF, netPMT, TPT, payable;
	
	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getCPF() {
		return CPF;
	}

	public void setCPF(BigDecimal cPF) {
		CPF = cPF;
	}

	public BigDecimal getNetPMT() {
		return netPMT;
	}

	public void setNetPMT(BigDecimal netPMT) {
		this.netPMT = netPMT;
	}

	public BigDecimal getTPT() {
		return TPT;
	}

	public void setTPT(BigDecimal tPT) {
		TPT = tPT;
	}

	public BigDecimal getPayable() {
		return payable;
	}

	public void setPayable(BigDecimal payable) {
		this.payable = payable;
	}

	public BigDecimal getPayTotal() {
		return PayTotal;
	}

	public void setPayTotal(BigDecimal PayTotal) {
		this.PayTotal = PayTotal;
	}
	public BigDecimal getMessTotal() {
		return MessTotal;
	}

	public void setMessTotal(BigDecimal MessTotal) {
		this.MessTotal = MessTotal;
	}
	public BigDecimal getRESTotal() {
		return RESTotal;
	}

	public void setRESTotal(BigDecimal RESTotal) {
		this.RESTotal = RESTotal;
	}
	public BigDecimal getCCSTotal() {
		return CCSTotal;
	}

	public void setCCSTotal(BigDecimal CCSTotal) {
		this.CCSTotal = CCSTotal;
	}

	public BigDecimal getConPageTotal() {
		return ConPageTotal;
	}

	public void setConPageTotal(BigDecimal ConPageTotal) {
		this.ConPageTotal = ConPageTotal;
	}

	public BigDecimal getCGEISTotal() {
		return CGEISTotal;
	}

	public void setCGEISTotal(BigDecimal CGEISTotal) {
		this.CGEISTotal = CGEISTotal;
	}

	public BigDecimal getCGHSTotal() {
		return CGHSTotal;
	}

	public void setCGHSTotal(BigDecimal total) {
		CGHSTotal = total;
	}

	public BigDecimal getGPFTotal() {
		return GPFTotal;
	}

	public void setGPFTotal(BigDecimal total) {
		GPFTotal = total;
	}

	public BigDecimal getGPFSATotal() {
		return GPFSATotal;
	}

	public void setGPFSATotal(BigDecimal total) {
		GPFSATotal = total;
	}

	public BigDecimal getGPFRTotal() {
		return GPFRTotal;
	}

	public void setGPFRTotal(BigDecimal total) {
		GPFRTotal = total;
	}

	public BigDecimal getGPFPageTotal() {
		return GPFPageTotal;
	}

	public void setGPFPageTotal(BigDecimal pageTotal) {
		GPFPageTotal = pageTotal;
	}

	public BigDecimal getPITotal() {
		return PITotal;
	}

	public void setPITotal(BigDecimal total) {
		PITotal = total;
	}

	public BigDecimal getRentTotal() {
		return RentTotal;
	}

	public void setRentTotal(BigDecimal rentTotal) {
		RentTotal = rentTotal;
	}

	public BigDecimal getWaterTotal() {
		return WaterTotal;
	}

	public void setWaterTotal(BigDecimal waterTotal) {
		WaterTotal = waterTotal;
	}

	public BigDecimal getElecTotal() {
		return ElecTotal;
	}

	public void setElecTotal(BigDecimal elecTotal) {
		ElecTotal = elecTotal;
	}

	public BigDecimal getHBATotal() {
		return HBATotal;
	}

	public void setHBATotal(BigDecimal total) {
		HBATotal = total;
	}

	public BigDecimal getITAXTotal() {
		return ITAXTotal;
	}

	public void setITAXTotal(BigDecimal total) {
		ITAXTotal = total;
	}

	public BigDecimal getEduTotal() {
		return EduTotal;
	}

	public void setEduTotal(BigDecimal eduTotal) {
		EduTotal = eduTotal;
	}

	public BigDecimal getSHECTotal() {
		return SHECTotal;
	}

	public void setSHECTotal(BigDecimal total) {
		SHECTotal = total;
	}

	public BigDecimal getFurTotal() {
		return FurTotal;
	}

	public void setFurTotal(BigDecimal furTotal) {
		FurTotal = furTotal;
	}

	public BigDecimal getPageTotal() {
		return PageTotal;
	}

	public void setPageTotal(BigDecimal pageTotal) {
		PageTotal = pageTotal;
	}

	public BigDecimal getTotalRecTotal() {
		return TotalRecTotal;
	}

	public void setTotalRecTotal(BigDecimal totalRecTotal) {
		TotalRecTotal = totalRecTotal;
	}

	public BigDecimal getCarinstamt() {
		return carinstamt;
	}

	public void setCarinstamt(BigDecimal carinstamt) {
		this.carinstamt = carinstamt;
	}

	public BigDecimal getScooterinstamt() {
		return scooterinstamt;
	}

	public void setScooterinstamt(BigDecimal scooterinstamt) {
		this.scooterinstamt = scooterinstamt;
	}

	public BigDecimal getBicycleinstamt() {
		return bicycleinstamt;
	}

	public void setBicycleinstamt(BigDecimal bicycleinstamt) {
		this.bicycleinstamt = bicycleinstamt;
	}

	public BigDecimal getPLITotal() {
		return PLITotal;
	}

	public void setPLITotal(BigDecimal pLITotal) {
		PLITotal = pLITotal;
	}

	public BigDecimal getLFTotal() {
		return LFTotal;
	}

	public void setLFTotal(BigDecimal lFTotal) {
		LFTotal = lFTotal;
	}

	public BigDecimal getCarTotal() {
		return carTotal;
	}

	public void setCarTotal(BigDecimal carTotal) {
		this.carTotal = carTotal;
	}

	public BigDecimal getScooterTotal() {
		return scooterTotal;
	}

	public void setScooterTotal(BigDecimal scooterTotal) {
		this.scooterTotal = scooterTotal;
	}

	public BigDecimal getCycleTotal() {
		return cycleTotal;
	}

	public void setCycleTotal(BigDecimal cycleTotal) {
		this.cycleTotal = cycleTotal;
	}

	public BigDecimal getHbadvTotal() {
		return hbadvTotal;
	}

	public void setHbadvTotal(BigDecimal hbadvTotal) {
		this.hbadvTotal = hbadvTotal;
	}

	public BigDecimal getPclnTotal() {
		return pclnTotal;
	}

	public void setPclnTotal(BigDecimal pclnTotal) {
		this.pclnTotal = pclnTotal;
	}

	public BigDecimal getFestadvTot() {
		return festadvTot;
	}

	public void setFestadvTot(BigDecimal festadvTot) {
		this.festadvTot = festadvTot;
	}

	public BigDecimal getItaxTotal() {
		return itaxTotal;
	}

	public void setItaxTotal(BigDecimal itaxTotal) {
		this.itaxTotal = itaxTotal;
	}

	public BigDecimal getEducessTotal() {
		return educessTotal;
	}

	public void setEducessTotal(BigDecimal educessTotal) {
		this.educessTotal = educessTotal;
	}

	public BigDecimal getShecTotal() {
		return shecTotal;
	}

	public void setShecTotal(BigDecimal shecTotal) {
		this.shecTotal = shecTotal;
	}

	public BigDecimal getPtaxTotal() {
		return ptaxTotal;
	}

	public void setPtaxTotal(BigDecimal ptaxTotal) {
		this.ptaxTotal = ptaxTotal;
	}

	public BigDecimal getTadaTotal() {
		return tadaTotal;
	}

	public void setTadaTotal(BigDecimal tadaTotal) {
		this.tadaTotal = tadaTotal;
	}

	public BigDecimal getLtcTotal() {
		return ltcTotal;
	}

	public void setLtcTotal(BigDecimal ltcTotal) {
		this.ltcTotal = ltcTotal;
	}

	public BigDecimal getMedTotal() {
		return medTotal;
	}

	public void setMedTotal(BigDecimal medTotal) {
		this.medTotal = medTotal;
	}

	public BigDecimal getEolhplTotal() {
		return eolhplTotal;
	}

	public void setEolhplTotal(BigDecimal eolhplTotal) {
		this.eolhplTotal = eolhplTotal;
	}

	public BigDecimal getMisc1Total() {
		return misc1Total;
	}

	public void setMisc1Total(BigDecimal misc1Total) {
		this.misc1Total = misc1Total;
	}

	public BigDecimal getMisc2Total() {
		return misc2Total;
	}

	public void setMisc2Total(BigDecimal misc2Total) {
		this.misc2Total = misc2Total;
	}

	public BigDecimal getTotalDeduction() {
		return totalDeduction;
	}

	public void setTotalDeduction(BigDecimal totalDeduction) {
		this.totalDeduction = totalDeduction;
	}

	public BigDecimal getTotalnetPay() {
		return totalnetPay;
	}

	public void setTotalnetPay(BigDecimal totalnetPay) {
		this.totalnetPay = totalnetPay;
	}
		public BigDecimal getITAXCESSSHECTotal() {
		return ITAXCESSSHECTotal;
	}

	public void setITAXCESSSHECTotal(BigDecimal ITAXCESSSHECTotal) {
		this.ITAXCESSSHECTotal = ITAXCESSSHECTotal;
	}

	public BigDecimal getHDFCTotal() {
		return HDFCTotal;
	}

	public void setHDFCTotal(BigDecimal hDFCTotal) {
		HDFCTotal = hDFCTotal;
	}

	public BigDecimal getCANFINTotal() {
		return CANFINTotal;
	}

	public void setCANFINTotal(BigDecimal cANFINTotal) {
		CANFINTotal = cANFINTotal;
	}

	public BigDecimal getLICTotal() {
		return LICTotal;
	}

	public void setLICTotal(BigDecimal lICTotal) {
		LICTotal = lICTotal;
	}

	public BigDecimal getGICTotal() {
		return GICTotal;
	}

	public void setGICTotal(BigDecimal gICTotal) {
		GICTotal = gICTotal;
	}

	public BigDecimal getCAttachTotal() {
		return CAttachTotal;
	}

	public void setCAttachTotal(BigDecimal cAttachTotal) {
		CAttachTotal = cAttachTotal;
	}

	public BigDecimal getMiscTotal() {
		return MiscTotal;
	}

	public void setMiscTotal(BigDecimal miscTotal) {
		MiscTotal = miscTotal;
	}

	public BigDecimal getGpayTotal() {
		return gpayTotal;
	}

	public void setGpayTotal(BigDecimal gpayTotal) {
		this.gpayTotal = gpayTotal;
	}

	public BigDecimal getDaTotal() {
		return daTotal;
	}

	public void setDaTotal(BigDecimal daTotal) {
		this.daTotal = daTotal;
	}

	public BigDecimal getSplpayTotal() {
		return splpayTotal;
	}

	public void setSplpayTotal(BigDecimal splpayTotal) {
		this.splpayTotal = splpayTotal;
	}

	public BigDecimal getFpaTotal() {
		return fpaTotal;
	}

	public void setFpaTotal(BigDecimal fpaTotal) {
		this.fpaTotal = fpaTotal;
	}

	public BigDecimal getAddincrTotal() {
		return addincrTotal;
	}

	public void setAddincrTotal(BigDecimal addincrTotal) {
		this.addincrTotal = addincrTotal;
	}

	public BigDecimal getTraTotal() {
		return traTotal;
	}

	public void setTraTotal(BigDecimal traTotal) {
		this.traTotal = traTotal;
	}

	public BigDecimal getHraTotal() {
		return hraTotal;
	}

	public void setHraTotal(BigDecimal hraTotal) {
		this.hraTotal = hraTotal;
	}

	public BigDecimal getWallowTotal() {
		return wallowTotal;
	}

	public void setWallowTotal(BigDecimal wallowTotal) {
		this.wallowTotal = wallowTotal;
	}

	public BigDecimal getXallowTotal() {
		return xallowTotal;
	}

	public void setXallowTotal(BigDecimal xallowTotal) {
		this.xallowTotal = xallowTotal;
	}

	public BigDecimal getVincrTotal() {
		return vincrTotal;
	}

	public void setVincrTotal(BigDecimal vincrTotal) {
		this.vincrTotal = vincrTotal;
	}

	public BigDecimal getTotalpayTotal() {
		return totalpayTotal;
	}

	public void setTotalpayTotal(BigDecimal totalpayTotal) {
		this.totalpayTotal = totalpayTotal;
	}

	public BigDecimal getTotalDedTotal() {
		return TotalDedTotal;
	}

	public void setTotalDedTotal(BigDecimal totalDedTotal) {
		TotalDedTotal = totalDedTotal;
	}

	public BigDecimal getNetPayTotal() {
		return NetPayTotal;
	}

	public void setNetPayTotal(BigDecimal netPayTotal) {
		NetPayTotal = netPayTotal;
	}

	public BigDecimal getWsubTotal() {
		return WsubTotal;
	}

	public void setWsubTotal(BigDecimal wsubTotal) {
		WsubTotal = wsubTotal;
	}

	public BigDecimal getWloanTotal() {
		return WloanTotal;
	}

	public void setWloanTotal(BigDecimal wloanTotal) {
		WloanTotal = wloanTotal;
	}

	public BigDecimal getBFTotal() {
		return BFTotal;
	}

	public void setBFTotal(BigDecimal bFTotal) {
		BFTotal = bFTotal;
	}

	public BigDecimal getRFTotal() {
		return RFTotal;
	}

	public void setRFTotal(BigDecimal rFTotal) {
		RFTotal = rFTotal;
	}
	public BigDecimal getCpfTotal() {
		return cpfTotal;
	}
	public void setCpfTotal(BigDecimal cpfTotal) {
		this.cpfTotal = cpfTotal;
	}
	

	public int getNetPMTTotal() {
		return netPMTTotal;
	}

	public BigDecimal getTptTotal() {
		return tptTotal;
	}
	public void setTptTotal(BigDecimal tptTotal) {
		this.tptTotal = tptTotal;
	}

	public int getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(int totalAmt) {
		this.totalAmt = totalAmt;
	}

	public void setNetPMTTotal(int netPMTTotal) {
		this.netPMTTotal = netPMTTotal;
	}

	public Integer getOTTotal() {
		return OTTotal;
	}

	public void setOTTotal(Integer oTTotal) {
		OTTotal = oTTotal;
	}


}