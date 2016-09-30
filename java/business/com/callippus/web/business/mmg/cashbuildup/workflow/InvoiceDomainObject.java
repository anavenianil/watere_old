package com.callippus.web.business.mmg.cashbuildup.workflow;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.business.domainobject.DomainObject;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.mmg.cashbuildup.beans.InvoiceRequestBean;
import com.callippus.web.mmg.cashbuildup.dto.IRItemDetailsDTO;
import com.callippus.web.mmg.cashbuildup.dto.LedgerDTO;
import com.callippus.web.mmg.cashbuildup.dto.StockDetailsDTO;

@Service
public class InvoiceDomainObject extends DomainObject {
	private static Log log = LogFactory.getLog(InvoiceDomainObject.class);
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private DomainObject domainObject;
	@Autowired
	private IComonObjectDAO commonDAO;

	/**
	 * This method will update the stock details
	 * 
	 * @param prb
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String updateTxnDetails(InvoiceRequestBean invoiceMasterBean) throws Exception {
		log.debug(">>>>>>>>>>method>>>>>>>>>>updateTxnDetails(InvoiceMasterBean vMasterBean)>>>>>>>>Start");
		Session session = null;
		Transaction tx = null;
		List<IRItemDetailsDTO> voucherItems = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			if (!CPSUtils.isNull(invoiceMasterBean.getIrItems()))
				voucherItems = invoiceMasterBean.getIrItems();
			else {
				String sql = "from InvoiceItemDetailsDTO where voucherId=?";
				voucherItems = (List<IRItemDetailsDTO>) session.createQuery(sql).setString(0, invoiceMasterBean.getVoucherNo()).list();
			}

			for (int i = 0; i < voucherItems.size(); i++) {
				if (!CPSUtils.isNullOrEmpty(voucherItems.get(i))) {
					/**
					 * For saving the materials into the stock details table
					 */
					StockDetailsDTO stock = null;
					IRItemDetailsDTO voucher = (IRItemDetailsDTO) voucherItems.get(i);
					//tx = session.beginTransaction();
					stock = (StockDetailsDTO) session.createCriteria(StockDetailsDTO.class).add(Expression.eq("materialCode", voucher.getMaterialCode())).add(
							Expression.eq("voucherNo", voucher.getVoucherNo())).add(Expression.eq("inventoryNo", invoiceMasterBean.getInventoryNo())).uniqueResult();
					if (!CPSUtils.isNull(stock)) {
						String sql = "update StockDetailsDTO set stockHeld=? where materialCode=? and inventoryNo=?";
						int qty = Integer.parseInt(stock.getStockHeld());
						qty = qty + Integer.parseInt(voucher.getQty());
						//tx = session.beginTransaction();
						session.createQuery(sql).setString(0, String.valueOf(qty)).setString(1, voucher.getMaterialCode()).setString(2, invoiceMasterBean.getInventoryNo()).executeUpdate();
						session.flush();//tx.commit() ;
					} else {
						int id = commonDAO.getTableID(CPSConstants.MMG_B_STOCK_DETAILS, CPSConstants.UPDATE);
						stock = new StockDetailsDTO();
						BeanUtils.copyProperties(stock, voucher);
						stock.setStockHeld(voucher.getQty());
						stock.setInventoryNo(invoiceMasterBean.getInventoryNo());
						stock.setVoucherNo(invoiceMasterBean.getVoucherNo());
						stock.setVoucherDate(invoiceMasterBean.getVoucherDate());
						stock.setUom(voucher.getUomConvert());
						stock.setMinStock("0");
						stock.setMaxStock("0");
						stock.setStatus("1");
						stock.setId(String.valueOf(id));
						stock.setCreationDate(CPSUtils.getCurrentDate());
						stock.setLastModifiedDate(CPSUtils.getCurrentDate());
						//tx = session.beginTransaction();
						session.save(stock);
						session.flush();//tx.commit() ;
					}
					/**
					 * for updating in the ledger table
					 */
					LedgerDTO ledgerDto = new LedgerDTO();
					BeanUtils.copyProperties(ledgerDto, voucher);
					ledgerDto.setLedgerCode(String.valueOf(commonDAO.getTableID(CPSConstants.MMG_LEDGER_DETAILS, CPSConstants.UPDATE)));
					ledgerDto.setInventoryNo(invoiceMasterBean.getInventoryNo());
					ledgerDto.setStatus("1");
					ledgerDto.setCreditDebitFlag("1");
					ledgerDto.setPostingDate(invoiceMasterBean.getPostingDate());
					ledgerDto.setVoucherNo(invoiceMasterBean.getVoucherNo());
					//tx = session.beginTransaction();
					session.save(ledgerDto);
					session.flush();//tx.commit() ;

				}
			}

			invoiceMasterBean.setMessage(CPSConstants.SUCCESS);
		} catch (Exception e) {
			invoiceMasterBean.setMessage(CPSConstants.FAILED);
			throw e;

		} finally {
			//session.close();
		}
		log.debug(">>>>>>>>>>method>>>>>>>>>>updateTxnDetails(InvoiceMasterBean invoiceMasterBean)>>>>>>>>Start");
		return invoiceMasterBean.getMessage();
	}

	public String UpdateInvoiceStatus(String voucherNo, String status) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			session.createQuery("update IRMasterBean set status=? where demandNo=?").setString(0, status).setString(1, voucherNo).executeUpdate();
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

}
