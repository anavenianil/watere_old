package com.callippus.web.ltc.business.master;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.ltc.beans.application.LtcApplicationBean;
import com.callippus.web.ltc.dao.master.ILtcMasterDAO;
import com.callippus.web.ltc.dto.LtcBlockMasterDTO;
import com.callippus.web.ltc.dto.LtcBlockYearsMasterDTO;
import com.callippus.web.ltc.dto.LtcPenalMasterDTO;
import com.callippus.web.ltc.dto.LtcTypeMasterDTO;

@Service
public class LtcMasterBusiness {
	@Autowired
	private ILtcMasterDAO ltcMasterDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;

	@SuppressWarnings("unchecked")
	public LtcApplicationBean getMasterData(LtcApplicationBean ltcBean) throws Exception {
		try {
			if (CPSUtils.compareStrings(ltcBean.getType(), CPSConstants.LTCTYPE)) {
				ltcBean.setMasterDataList(commonDataDAO.getMasterData(CPSConstants.LTCTYPEMASTERDTO));
			} else if (CPSUtils.compareStrings(ltcBean.getType(), CPSConstants.LTCBLOCK)) {
				ltcBean.setMasterDataList(commonDataDAO.getMasterData(CPSConstants.LTCBLOCKMASTERDTO));
			} else if (CPSUtils.compareStrings(ltcBean.getType(), CPSConstants.LTCBLOCKYEAR)) {
				ltcBean.setMasterDataList(commonDataDAO.getMasterData(CPSConstants.LTCBLOCKYEARSMASTERDTO));
				ltcBean.setLtcBlockDetails(commonDataDAO.getMasterData(CPSConstants.LTCBLOCKMASTERDTO));
				ltcBean.setLtcBlockYearList(ltcMasterDAO.getBlockYearsList());
			} else if(CPSUtils.compareStrings(ltcBean.getType(), CPSConstants.LTCPENALINTERESTMASTER)){
				List list=commonDataDAO.getMasterData(CPSConstants.LTCPENALMASTERDTO);
				ltcBean.setMasterDataList(list);
			}
		} catch (Exception e) {
			throw e;
		}
		return ltcBean;
	}

	public String manageLtcMasterData(LtcApplicationBean ltcBean) throws Exception {
		String message = null;
		try {
			if (CPSUtils.isNullOrEmpty(ltcBean.getId())) {
				message = ltcMasterDAO.checkLtcName(ltcBean);
				if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
					ltcBean.setStatus(1);
					ltcBean.setTypeValue(ltcBean.getTypeValue());
					ltcBean.setCreatedBy(ltcBean.getSfID());
					ltcBean.setCreationDate(CPSUtils.getCurrentDate());
					ltcBean.setLastModifiedBy(ltcBean.getSfID());
					ltcBean.setLastModifiedDate(CPSUtils.getCurrentDate());
					if (CPSUtils.compareStrings(ltcBean.getType(), CPSConstants.LTCTYPE)) {
						LtcTypeMasterDTO ltcTypeMaster = new LtcTypeMasterDTO();
						BeanUtils.copyProperties(ltcTypeMaster, ltcBean);
						message = ltcMasterDAO.createLtcMasterData(ltcTypeMaster);
					} else if (CPSUtils.compareStrings(ltcBean.getType(), CPSConstants.LTCBLOCK)) {
						LtcBlockMasterDTO ltcBlockMasterDTO = new LtcBlockMasterDTO();
						BeanUtils.copyProperties(ltcBlockMasterDTO, ltcBean);
						message = ltcMasterDAO.createLtcMasterData(ltcBlockMasterDTO);
					} else if (CPSUtils.compareStrings(ltcBean.getType(), CPSConstants.LTCPENALINTERESTMASTER)) {
						LtcPenalMasterDTO ltcPenalMasterDTO = new LtcPenalMasterDTO();
						BeanUtils.copyProperties(ltcPenalMasterDTO, ltcBean);
						message = ltcMasterDAO.createLtcMasterData(ltcPenalMasterDTO);
					} else if (CPSUtils.compareStrings(ltcBean.getType(), CPSConstants.LTCBLOCKYEAR)) {
						LtcBlockYearsMasterDTO ltcBlockYear = new LtcBlockYearsMasterDTO();
						BeanUtils.copyProperties(ltcBlockYear, ltcBean);
						if (CPSUtils.compareStrings(ltcBlockYear.getFourYearBlockId(), CPSConstants.SELECT)) {
							ltcBlockYear.setFourYearBlockId(null);
						}
						message = ltcMasterDAO.createLtcMasterData(ltcBlockYear);
					}
				}
			} else {
				message = ltcMasterDAO.checkLtcName(ltcBean);
				if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
					message = ltcMasterDAO.updateLtcMasterData(ltcBean);
				}
			}

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String deleteLtcMasterData(LtcApplicationBean ltcBean) throws Exception {
		String message = null;
		boolean status = true;
		try {

			ltcBean.setTableName(getTableName(ltcBean.getType()));

			message = getTableDetails(ltcBean.getType());

			if (!CPSUtils.isNullOrEmpty(message)) {
				String[] record = message.split(",");
				for (int i = 0; i <= record.length - 1; i++) {
					message = commonDataDAO.deleteCheckMaster(record[i].split("#")[0], record[i].split("#")[1], record[i].split("#")[2], ltcBean.getId());
					if (CPSUtils.compareStrings(message, CPSConstants.FAILED)) {
						status = false;
						break;
					}
				}
			}

			if (status) {
				message = ltcMasterDAO.deleteLtcMasterData(ltcBean);
			} else {
				message = CPSConstants.DELETEFAIL;
			}

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String getTableName(String type) throws Exception {
		try {

			if (CPSUtils.compareStrings(type, CPSConstants.LTCTYPE)) {
				return CPSConstants.LTC_TYPE_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.LTCBLOCK)) {
				return CPSConstants.LTC_TYPE_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.LTCBLOCKYEAR)) {
				return CPSConstants.LTC_BLOCK_YEAR_MASTER;
			} else if(CPSUtils.compareStrings(type, CPSConstants.LTCPENALINTERESTMASTER)){
				return CPSConstants.LTC_PENAL_MASTER;
			}
		} catch (Exception e) {
			throw e;
		}

		return null;
	}

	public String getTableDetails(String type) throws Exception {
		String message = "";
		try {

			if (CPSUtils.compareStrings(type, CPSConstants.LTCTYPE)) {
				return CPSConstants.LTC_REQUEST_DETAILS + "#" + "LTC_TYPE_ID" + "#" + "NOSTATUS" + "," + CPSConstants.LTC_ADVANCE_REQUEST_DETAILS + "#" + "LTC_TYPE_ID" + "#" + "NOSTATUS";
			} else if (CPSUtils.compareStrings(type, CPSConstants.LTCBLOCK)) {
				return CPSConstants.LTC_BLOCK_YEAR_MASTER + "#" + "LTC_BLOCK_ID" + "#" + "STATUS";
			} else if (CPSUtils.compareStrings(type, CPSConstants.LTCBLOCKYEAR)) {
				return CPSConstants.LTC_REQUEST_DETAILS + "#" + "LTC_BLOCK_YEAR_ID" + "#" + "NOSTATUS" + "," + CPSConstants.LTC_ADVANCE_REQUEST_DETAILS + "#" + "LTC_BLOCK_YEAR_ID" + "#" + "NOSTATUS";
			} else if(CPSUtils.compareStrings(type, CPSConstants.LTCPENALINTERESTMASTER)){
				return CPSConstants.LTC_PENAL_MASTER + "#" + "PENAL_INTEREST_PERCENTAGE" + "#" + "STATUS";
			}

		} catch (Exception e) {
			throw e;
		}

		return message;
	}

	public String insertLtcYearBlock(String type) throws Exception {
		String message = null;
		LtcBlockYearsMasterDTO ltcBlockYear = null;
		try {
			Calendar today_plus_year = Calendar.getInstance();
			Format format = new SimpleDateFormat("dd-MMM-yyyy");
			ltcBlockYear = new LtcBlockYearsMasterDTO();
			ltcBlockYear.setCreatedBy("BackEnd");
			ltcBlockYear.setCreationDate(CPSUtils.getCurrentDate());
			ltcBlockYear.setLastModifiedBy("BackEnd");
			ltcBlockYear.setLastModifiedDate(CPSUtils.getCurrentDate());
			ltcBlockYear.setStatus(1);
			if (CPSUtils.compareStrings(type, CPSConstants.ONE)) {
				ltcBlockYear.setFromDate(CPSUtils.getCurrentDate());
				ltcBlockYear.setLtcBlockId(ltcMasterDAO.getLtcBlockType(CPSConstants.ONE));
			} else if (CPSUtils.compareStrings(type, CPSConstants.TWO)) {
				ltcBlockYear.setLtcBlockId(ltcMasterDAO.getLtcBlockType(CPSConstants.TWO));
				today_plus_year.add(Calendar.YEAR, 1);
				today_plus_year.add(Calendar.MONTH, 12);
				ltcBlockYear.setFromDate(format.format(today_plus_year.getTime()));
				today_plus_year.add(Calendar.YEAR, 1);
				today_plus_year.add(Calendar.MONTH, 11);
				today_plus_year.add(Calendar.DAY_OF_MONTH, 30);
				ltcBlockYear.setToDate(format.format(today_plus_year.getTime()));
				today_plus_year.add(Calendar.YEAR, 1);
				ltcBlockYear.setExtendedDate(format.format(today_plus_year.getTime()));
			} else if (CPSUtils.compareStrings(type, CPSConstants.FOUR)) {
				ltcBlockYear.setLtcBlockId(ltcMasterDAO.getLtcBlockType(CPSConstants.FOUR));
				ltcBlockYear.setFromDate(CPSUtils.getCurrentDate());
				today_plus_year.add(Calendar.YEAR, 3);
				today_plus_year.add(Calendar.MONTH, 11);
				today_plus_year.add(Calendar.DAY_OF_MONTH, 30);
				ltcBlockYear.setToDate(format.format(today_plus_year.getTime()));
			}
			message = ltcMasterDAO.createLtcMasterData(ltcBlockYear);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}
}
