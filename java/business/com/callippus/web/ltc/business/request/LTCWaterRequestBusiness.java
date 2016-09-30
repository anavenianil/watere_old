package com.callippus.web.ltc.business.request;

import org.hibernate.Session;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.ltc.beans.application.LtcApplicationBean;
import com.callippus.web.ltc.beans.request.LTCWaterRequestBean;
import com.callippus.web.ltc.dao.approval.ILtcApprovalRequestDAO;
import com.callippus.web.ltc.dao.request.ILTCWaterRequestDAO;
import com.callippus.web.ltc.dto.LTCWaterRequestProcessBean;
import com.callippus.web.tada.beans.request.TadaWaterRequestBean;

@Service
public class LTCWaterRequestBusiness {
	
	@Autowired
	ILTCWaterRequestDAO ltcWaterRequestDAO;
	@Autowired
	private IComonObjectDAO commonObjectDAO;
	@Autowired
	private ILtcApprovalRequestDAO ltcApprovalRequestDAO;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	
	
	
	public LTCWaterRequestBean getEmpDetails(
			LTCWaterRequestBean ltcWaterRequestBean) throws Exception{
		
		
		try{
			ltcWaterRequestBean=ltcWaterRequestDAO.getEmpDetails(ltcWaterRequestBean);
			
			ltcWaterRequestBean.setHomeTownAddress(ltcApprovalRequestDAO.getHometownAddress(ltcWaterRequestBean.getSfID()));
			
		}catch(Exception e){
			throw e;
		}
		return ltcWaterRequestBean;
	}
	
	
	public LTCWaterRequestBean getEmpDetailsOne(
			LTCWaterRequestBean ltcWaterRequestBean) throws Exception{
		
		
		try{
			ltcWaterRequestBean=ltcWaterRequestDAO.getEmpDetailsOne(ltcWaterRequestBean);
			
			ltcWaterRequestBean.setHomeTownAddress(ltcApprovalRequestDAO.getHometownAddress(ltcWaterRequestBean.getSfID()));
			
		}catch(Exception e){
			throw e;
		}
		return ltcWaterRequestBean;
	}
	
	
	public LTCWaterRequestBean getDeptDetails(LTCWaterRequestBean ltcWaterRequestBean)throws Exception{
		try{
			ltcWaterRequestBean=ltcWaterRequestDAO.getDeptDetails(ltcWaterRequestBean);
			
		}catch(Exception e){
			throw e;
		}
		return ltcWaterRequestBean;
	}
	
	
	
	public LTCWaterRequestBean getDeptDetailsOne(LTCWaterRequestBean ltcWaterRequestBean)throws Exception{
		try{
			ltcWaterRequestBean=ltcWaterRequestDAO.getDeptDetailsOne(ltcWaterRequestBean);
			
		}catch(Exception e){
			throw e;
		}
		return ltcWaterRequestBean;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public LTCWaterRequestBean getltcYearsList(LTCWaterRequestBean ltcWaterRequestBean) throws Exception {
		try {
			ltcWaterRequestBean.setLtcYearList(commonObjectDAO.getMasterData(CPSConstants.LTCYEARSDTO));
			ltcWaterRequestBean.setLtcYearsList(commonObjectDAO.getMasterData(CPSConstants.LTCYEARSDTO));
		} catch (Exception e) {
			throw e;
		}
		return ltcWaterRequestBean;
	}
	
	public LTCWaterRequestBean getCurrentWaterReqIdDetails(LTCWaterRequestBean ltcWaterRequestBean)throws Exception{
		try{
			ltcWaterRequestBean=ltcWaterRequestDAO.getCurrentWaterReqIdDetails(ltcWaterRequestBean);
			
		}catch(Exception e){
			throw e;
		}
		return ltcWaterRequestBean;
	}
	
	public String updateLTCAdvance(
			LTCWaterRequestProcessBean ltcWaterRequestProcessBean) throws Exception{
		Session session=null;
		try {
			
			session=hibernateUtils.getSession();
			 session.createQuery("update LTCWaterRequestDTO set amountAdults=?,amountChildren=?,adultsTotAmt=?,childrenTotAmt=?,refLetterDate=?,refLetterNo=?,otherAmt=?,totalTicketsAmt=?,ltcadvBankName=?,ltcadvBranchName=?,ltcadvChequeNo=?,ltcadvAdminDvno=?,ltcadvAdminDvDate=?,ltcadvcashorcheck=?,advance='COMP',stageStatus=3  where requestId=?").setInteger(0, ltcWaterRequestProcessBean.getAmountAdults()).setInteger(1,  ltcWaterRequestProcessBean.getAmountChildren()).setInteger(2, ltcWaterRequestProcessBean.getAdultsTotAmt()).setInteger(3, ltcWaterRequestProcessBean.getChildrenTotAmt()).setDate(4,  ltcWaterRequestProcessBean.getRefLetterDate()).setString(5, ltcWaterRequestProcessBean.getRefLetterNo()).setInteger(6, ltcWaterRequestProcessBean.getOtherAmt()).setInteger(7,  ltcWaterRequestProcessBean.getTotalTicketsAmt()).setString(8, ltcWaterRequestProcessBean.getLtcadvBankName()).setString(9, ltcWaterRequestProcessBean.getLtcadvBranchName()).setString(10, ltcWaterRequestProcessBean.getLtcadvChequeNo()).setString(11, ltcWaterRequestProcessBean.getLtcadvAdminDvno()).setDate(12, ltcWaterRequestProcessBean.getLtcadvAdminDvDate()).setString(13, ltcWaterRequestProcessBean.getLtcadvcashorcheck()).setString(14, ltcWaterRequestProcessBean.getRequestId()).executeUpdate();
				
		} catch (Exception e) {
				throw e;
		}
		
		return "success";
	}
	
	
	@SuppressWarnings("unchecked")
	public LTCWaterRequestBean getBankNameDetails(LTCWaterRequestBean ltcWaterRequestBean) throws Exception {
		try {
			ltcWaterRequestBean.setBankNamesList(commonObjectDAO.getMasterData(CPSConstants.BANKNAMESDTO));
		} catch (Exception e) {
			throw e;
		}
		return ltcWaterRequestBean;
	}
	
	public LTCWaterRequestBean getCurrentWaterReqIdDetail(LTCWaterRequestBean ltcWaterRequestBean)throws Exception{
		try{
			ltcWaterRequestBean=ltcWaterRequestDAO.getCurrentWaterReqIdDetail(ltcWaterRequestBean);
			
		}catch(Exception e){
			throw e;
		}
		return ltcWaterRequestBean;
	}

	@SuppressWarnings("unchecked")
	public LTCWaterRequestBean getLTCDetails(LTCWaterRequestBean ltcWaterRequestBean) throws Exception {
		try {
			ltcWaterRequestBean.setFamilyMemberDetails(ltcApprovalRequestDAO.getFamilyMemberDetails(ltcWaterRequestBean.getSfid()));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		return ltcWaterRequestBean;
	}
	
	public LTCWaterRequestBean getCurrentWaterSettlementDetail(LTCWaterRequestBean ltcWaterRequestBean)throws Exception{
		try{
			ltcWaterRequestBean=ltcWaterRequestDAO.getCurrentWaterSettlementDetail(ltcWaterRequestBean);
			
		}catch(Exception e){
			throw e;
		}
		return ltcWaterRequestBean;
	}
	
	public LTCWaterRequestBean getCurrentWaterReimbursementDetail(LTCWaterRequestBean ltcWaterRequestBean)throws Exception{
		try{
			ltcWaterRequestBean=ltcWaterRequestDAO.getCurrentWaterReimbursementDetail(ltcWaterRequestBean);
			
		}catch(Exception e){
			throw e;
		}
		return ltcWaterRequestBean;
	}
	
	public String getLtcYear(String ltcYear,String sfid) throws Exception{
		String ltcYearID=null;
		try{
			ltcYearID=ltcWaterRequestDAO.getLtcYear(ltcYear,sfid);
		}catch (Exception e) {
			throw e;
		}
		return ltcYearID;
	}
	
	
	
	
}
