package com.callippus.web.retriments.dao;

import java.net.InetAddress;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.retriments.beans.RetrimentBean;
import com.callippus.web.retriments.dto.RetrimentDTO;

@SuppressWarnings("unchecked")
@Service
public class SQLRetrimentsDAO implements IRetrimentsDAO {

	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	@Override
	public RetrimentBean submitRetrimentDetails(RetrimentBean retrimentBean)
			throws Exception {
		System.out.println("welcome");
		Session session = null;
		String message = null;
		
	    InetAddress address = InetAddress.getLocalHost(); 
	    String hostIP = address.getHostAddress() ;
	    String hostName = address.getHostName();
	    Date dd =new Date();
		try {
			RetrimentDTO retrimentDTO = new RetrimentDTO();
			if(retrimentBean.getUpdateID()!=null){
			retrimentDTO.setId(retrimentBean.getUpdateID());
			//retrimentDTO.setSfID(retrimentBean.getSfID());
			retrimentDTO.setSfID(retrimentBean.getSfId());
			retrimentDTO.setEmpName(retrimentBean.getEmpName());
			retrimentDTO.setEmpType(retrimentBean.getEmpType());
			retrimentDTO.setNoOfPerson(retrimentBean.getNoOfPerson());
			retrimentDTO.setRetrimentDate(retrimentBean.getRetrimentDate());
			retrimentDTO.setRetrimentAmt(retrimentBean.getRetrimentAmt());
			retrimentDTO.setTransportAmt(retrimentBean.getTransportAmt());
			retrimentDTO.setNoofTons(retrimentBean.getNoofTons());
			retrimentDTO.setLuggageAmt(retrimentBean.getLuggageAmt());
			retrimentDTO.setTotAmt(retrimentBean.getTotAmt());
			retrimentDTO.setStatus(1);
			
		
		    retrimentDTO.setIpAddr(hostIP);
		    retrimentDTO.setCreatedBy(retrimentBean.getSfID());
		    retrimentDTO.setAppliedDate(dd);
		
			
			}else{
				//retrimentDTO.setSfID(retrimentBean.getSfID());
				retrimentDTO.setSfID(retrimentBean.getSfId());
				retrimentDTO.setEmpName(retrimentBean.getEmpName());
				retrimentDTO.setEmpType(retrimentBean.getEmpType());
				retrimentDTO.setNoOfPerson(retrimentBean.getNoOfPerson());
				retrimentDTO.setRetrimentDate(retrimentBean.getRetrimentDate());
				retrimentDTO.setRetrimentAmt(retrimentBean.getRetrimentAmt());
				retrimentDTO.setTransportAmt(retrimentBean.getTransportAmt());
				retrimentDTO.setNoofTons(retrimentBean.getNoofTons());
				retrimentDTO.setLuggageAmt(retrimentBean.getLuggageAmt());
				retrimentDTO.setTotAmt(retrimentBean.getTotAmt());
				retrimentDTO.setStatus(1);
				
			    retrimentDTO.setIpAddr(hostIP);
			    retrimentDTO.setCreatedBy(retrimentBean.getSfID());
			    retrimentDTO.setAppliedDate(dd);
				
			}
			session = hibernateUtils.getSession();
			session.saveOrUpdate(retrimentDTO);
			session.flush();
			retrimentBean.setMessage(CPSConstants.SUCCESS);
			retrimentBean.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			System.out.println(e);
			throw e;
		}
		return retrimentBean;
	}

	@Override
	public List<RetrimentDTO> getRetrimentBenfitDetails(
			RetrimentBean retrimentBean) throws Exception {
		Session session = null;
		List<RetrimentDTO> retrimentDetailsList = null;
		try {
			session = hibernateUtils.getSession();
			retrimentDetailsList = session.createCriteria(RetrimentDTO.class)
					.add(Expression.eq("status", 1)).list();

		} catch (Exception e) {
			System.out.println(e);
			throw e;
		}

		return retrimentDetailsList;
	}

	@Override
	public String deleteRetrimentDetails(RetrimentBean retrimentBean)
			throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			RetrimentDTO retrimentDTO = (RetrimentDTO) session.get(
					RetrimentDTO.class, Integer.valueOf(retrimentBean.getId()));
			retrimentDTO.setStatus(0);
			session.saveOrUpdate(retrimentDTO);
			retrimentBean.setResult(CPSConstants.DELETE);
		} catch (Exception e) {
			throw e;
		}
		return retrimentBean.getResult();
	}

	@Override
	public String chageEmployeeDetails(String changeSfid) throws Exception {
		
		Session session = null;
		String name = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			name = (String) session.createSQLQuery("SELECT NAME_IN_SERVICE_BOOK FROM emp_master WHERE SFID=? AND STATUS=1 ").setString(0, changeSfid).uniqueResult();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return name;
		
	}

	@Override
	public RetrimentDTO getEmpDetails(Integer id) throws Exception {

		Session session=null;
		RetrimentDTO retrimentDTO =null;
		try {
			session = hibernateUtils.getSession();
		 retrimentDTO = (RetrimentDTO) session.get(
					RetrimentDTO.class, Integer.valueOf(id));
		} catch (Exception e) {
			throw e;
		
		}
		return retrimentDTO;
	}

	@Override
	public RetrimentBean submitRetrimentPayDetails(RetrimentBean retrimentBean)
			throws Exception {
		
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			RetrimentDTO retrimentDTO = (RetrimentDTO) session.get(
					RetrimentDTO.class, Integer.valueOf(retrimentBean.getId()));
			retrimentDTO.setDvno(retrimentBean.getDvno());
			retrimentDTO.setDvDate(retrimentBean.getDvDate());
			retrimentDTO.setCashOrCheqe(retrimentBean.getCashorcheck());
			retrimentDTO.setBankName(retrimentBean.getBankName());
			retrimentDTO.setChequeNo(retrimentBean.getChequeNo());
			retrimentDTO.setBranchName(retrimentBean.getBranchName());
			retrimentDTO.setIssuedBy(retrimentBean.getSfID());
			retrimentDTO.setStatus(2);
			session.saveOrUpdate(retrimentDTO);
			session.flush();
			retrimentBean.setMessage(CPSConstants.SUCCESS);
			retrimentBean.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		}
		return retrimentBean;
	}

}
