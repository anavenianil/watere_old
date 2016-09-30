package com.callippus.web.retriment.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.retriments.beans.RetrimentBean;
import com.callippus.web.retriments.dao.IRetrimentsDAO;
import com.callippus.web.retriments.dto.RetrimentDTO;

@Service
public class RetrimentBusiness {

	@Autowired
	private IRetrimentsDAO iRetrimentsDAO;

	@Autowired
	private IComonObjectDAO commonObjectDAO;

	public RetrimentBean saveDetails(RetrimentBean retrimentBean)
			throws Exception {
		retrimentBean = iRetrimentsDAO.submitRetrimentDetails(retrimentBean);
		return retrimentBean;
	}

	public List<RetrimentDTO> getRetrimentDetails(RetrimentBean retrimentBean)
			throws Exception {
		RetrimentDTO retrimentDTO = new RetrimentDTO();
		List<RetrimentDTO> retrimentBenfitsList = null;
		try {
			retrimentBenfitsList = iRetrimentsDAO
					.getRetrimentBenfitDetails(retrimentBean);
		} catch (Exception e) {
			throw e;
		}
		return retrimentBenfitsList;
	}

	public String deleteRetrimentDetails(RetrimentBean retrimentBean)
			throws Exception {
		try {

			retrimentBean.setResult(iRetrimentsDAO
					.deleteRetrimentDetails(retrimentBean));

		} catch (Exception e) {
			throw e;
		}

		return retrimentBean.getResult();

	}

	public String changeEmpDetails(String changeSfid) throws Exception {
		String name = null;
		try {
			name = iRetrimentsDAO.chageEmployeeDetails(changeSfid);
		} catch (Exception e) {
			throw e;
		}
		return name;
	}

	public RetrimentDTO getretriedEmpDetails(RetrimentBean retrimentBean)
			throws Exception {

		RetrimentDTO retrimentDTO = null;
		try {

			retrimentDTO = iRetrimentsDAO.getEmpDetails(retrimentBean.getId());

		} catch (Exception e) {
			throw e;
		}

		return retrimentDTO;
	}

	@SuppressWarnings("unchecked")
	public RetrimentBean getBankNameDetails(RetrimentBean retrimentBean)
			throws Exception {
		try {
			retrimentBean.setBankNamesList(commonObjectDAO
					.getMasterData(CPSConstants.BANKNAMESDTO));
		} catch (Exception e) {
			throw e;
		}
		return retrimentBean;
	}

	public RetrimentBean savePaymentDetails(RetrimentBean retrimentBean)
			throws Exception {

		retrimentBean = iRetrimentsDAO.submitRetrimentPayDetails(retrimentBean);

		return retrimentBean;
	}

}
