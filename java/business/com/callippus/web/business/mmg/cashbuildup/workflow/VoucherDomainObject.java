package com.callippus.web.business.mmg.cashbuildup.workflow;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.business.domainobject.DomainObject;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.mmg.cashbuildup.beans.VoucherRequestBean;
import com.callippus.web.mmg.cashbuildup.dto.AccountHeadDTO;
import com.callippus.web.mmg.cashbuildup.dto.LedgerDTO;
import com.callippus.web.mmg.cashbuildup.dto.StockDetailsDTO;
import com.callippus.web.mmg.cashbuildup.dto.VoucherItemDetailsDTO;

@Service
public class VoucherDomainObject extends DomainObject {
	private static Log log = LogFactory.getLog(VoucherDomainObject.class);
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;
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
	public String updateTxnDetails(VoucherRequestBean vMasterBean) throws Exception {
		log.debug(">>>>>>>>>>method>>>>>>>>>>updateTxnDetails(VoucherMasterBean vMasterBean)>>>>>>>>Start");
		Session session = null;
		Transaction tx = null;
		List<StockDetailsDTO> list = null;
		VoucherItemDetailsDTO voucher = null;
		List<VoucherItemDetailsDTO> voucherItems = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			if (CPSUtils.checkList(vMasterBean.getVoucherItems())) {
				voucherItems = vMasterBean.getVoucherItems();
			} else {
				String sql = "from VoucherItemDetailsDTO where voucherId=?";
				voucherItems = (List<VoucherItemDetailsDTO>) session.createQuery(sql).setString(0, vMasterBean.getVoucherId()).list();
			}
			for (int i = 0; i < voucherItems.size(); i++) {
				if (!CPSUtils.isNullOrEmpty(voucherItems.get(i))) {
					voucher = (VoucherItemDetailsDTO) voucherItems.get(i);
					if (CPSUtils.compareStrings(vMasterBean.getVoucherTypeId(), "11")) {
						String uom = "";
						String cnc = "";
						if (!CPSUtils.isNullOrEmpty(voucher.getCncConvert())) {
							cnc = voucher.getCncConvert();
						} else {
							cnc = voucher.getCflag();
						}
						if (!CPSUtils.isNullOrEmpty(voucher.getUomConvert())) {
							uom = voucher.getUomConvert();
						} else {
							uom = voucher.getUom();
						}
						//tx = session.beginTransaction();
						session.createQuery("update StockDetailsDTO set cflag=?,uom=? where materialCode=? and inventoryNo=? and status=1").setString(0, cnc).setString(1, uom).setString(2,
								voucher.getMaterialCode()).setString(3, vMasterBean.getInventoryNo()).executeUpdate();
						session.flush();//tx.commit() ;
					} else {
						int qty = Integer.parseInt(voucher.getQty());
						if (CPSUtils.compareStrings(vMasterBean.getVoucherTypeId(), "4")) {

							int id = commonDAO.getTableID(CPSConstants.MMG_B_STOCK_DETAILS, CPSConstants.UPDATE);
							StockDetailsDTO stock = new StockDetailsDTO();
							BeanUtils.copyProperties(stock, voucher);
							stock.setInventoryNo(vMasterBean.getInventoryNo());
							stock.setId(String.valueOf(id));
							stock.setVoucherNo(vMasterBean.getVoucherId());
							stock.setCreationDate(CPSUtils.getCurrentDate());
							stock.setLastModifiedDate(CPSUtils.getCurrentDate());
							stock.setStatus("1");
							stock.setStockHeld(voucher.getQty());
							//tx = session.beginTransaction();
							session.save(stock);
							session.flush();//tx.commit() ;

						} else if (CPSUtils.compareStrings(vMasterBean.getVoucherTypeId(), "7") || CPSUtils.compareStrings(vMasterBean.getVoucherTypeId(), "9")
								|| CPSUtils.compareStrings(vMasterBean.getVoucherTypeId(), "10")) {
							while (qty > 0) {
								list = new ArrayList<StockDetailsDTO>();
								//tx = session.beginTransaction();
								String sql = "Select S.Id as id,S.Stock_held as stockHeld From Mmg_b_stock_details S Where S.Receipt_date=(Select Min(Receipt_date) From Mmg_b_stock_details Where Stock_held!=0 And Inventory_no=? And Material_code=? And Status=1) And S.Inventory_no=? and S.material_code=? and S.status=1";
								list = session.createSQLQuery(sql).addScalar("id").addScalar("stockHeld", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(StockDetailsDTO.class))
										.setString(0, vMasterBean.getInventoryNo()).setString(1, voucher.getMaterialCode()).setString(2, vMasterBean.getInventoryNo()).setString(3,
												voucher.getMaterialCode()).list();
								session.flush();//tx.commit() ;

								if (CPSUtils.checkList(list)) {

									for (int j = 0; j < list.size(); j++) {
										if (qty > 0) {
											if (qty > Integer.parseInt(list.get(j).getStockHeld())) {
												//tx = session.beginTransaction();
												session.createQuery("update StockDetailsDTO set stockHeld=0 where id=?").setString(0, list.get(j).getId()).executeUpdate();
												session.flush();//tx.commit() ;
												qty = qty - Integer.parseInt(list.get(j).getStockHeld());
											} else {
												//tx = session.beginTransaction();
												session.createQuery("update StockDetailsDTO set stockHeld=? where id=?").setString(0,
														String.valueOf((Integer.parseInt(list.get(j).getStockHeld()) - qty))).setString(1, list.get(j).getId()).executeUpdate();
												session.flush();//tx.commit() ;
												qty = 0;
											}
										}
									}
								}
							}
						} else if (CPSUtils.compareStrings(vMasterBean.getVoucherTypeId(), "5")) {

							if (CPSUtils.compareStrings(vMasterBean.getInventoryNo(), vMasterBean.getToInventory())) {

								int id = commonDAO.getTableID(CPSConstants.MMG_B_STOCK_DETAILS, CPSConstants.UPDATE);
								StockDetailsDTO stock = new StockDetailsDTO();
								BeanUtils.copyProperties(stock, voucher);
								stock.setInventoryNo(vMasterBean.getInventoryNo());
								stock.setId(String.valueOf(id));
								stock.setVoucherNo(vMasterBean.getVoucherId());
								stock.setCreationDate(CPSUtils.getCurrentDate());
								stock.setLastModifiedDate(CPSUtils.getCurrentDate());
								stock.setStatus("1");
								stock.setStockHeld(voucher.getQty());
								stock.setVoucherDate(CPSUtils.getCurrentDateWithTime());
								//tx = session.beginTransaction();
								session.save(stock);
								session.flush();//tx.commit() ;

							} else {
								while (qty > 0) {
									list = new ArrayList<StockDetailsDTO>();
									//tx = session.beginTransaction();
									// stock=(StockDetailsDTO)session.createCriteria(StockDetailsDTO.class).add(Expression.eq("materialCode",voucher.getMaterialCode())).add(Expression.eq("inventoryNo",
									// vMasterBean.getInventoryNo())).uniqueResult();
									String sql = "Select S.Id as id,S.Stock_held as stockHeld From Mmg_b_stock_details S Where S.Receipt_date=(Select Min(Receipt_date) From Mmg_b_stock_details Where Stock_held!=0 And Inventory_no=? And Material_code=? And Status=1) And S.Inventory_no=? and S.material_code=? and S.status=1";
									list = session.createSQLQuery(sql).addScalar("id").addScalar("stockHeld", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(StockDetailsDTO.class))
											.setString(0, vMasterBean.getInventoryNo()).setString(1, voucher.getMaterialCode()).setString(2, vMasterBean.getInventoryNo()).setString(3,
													voucher.getMaterialCode()).list();
									session.flush();//tx.commit() ;

									if (CPSUtils.checkList(list)) {

										for (int j = 0; j < list.size(); j++) {
											if (qty > 0) {
												if (qty > Integer.parseInt(list.get(j).getStockHeld())) {
													//tx = session.beginTransaction();
													session.createQuery("update StockDetailsDTO set stockHeld=0 where id=?").setString(0, list.get(i).getId()).executeUpdate();
													session.flush();//tx.commit() ;
													qty = qty - Integer.parseInt(list.get(j).getStockHeld());
												} else {
													//tx = session.beginTransaction();
													session.createQuery("update StockDetailsDTO set stockHeld=? where id=?").setString(0,
															String.valueOf((Integer.parseInt(list.get(i).getStockHeld()) - qty))).setString(1, list.get(i).getId()).executeUpdate();
													session.flush();//tx.commit() ;
													qty = 0;
												}
											}
										}
									}
								}
							}

						}
					}
					if (!CPSUtils.compareStrings(vMasterBean.getVoucherTypeId(), "11")) {
						LedgerDTO ledgerDto = new LedgerDTO();
						BeanUtils.copyProperties(ledgerDto, voucher);
						ledgerDto.setLedgerCode(String.valueOf(commonDAO.getTableID(CPSConstants.MMG_LEDGER_DETAILS, CPSConstants.UPDATE)));
						ledgerDto.setInventoryNo(vMasterBean.getInventoryNo());
						ledgerDto.setStatus("1");
						if (CPSUtils.compareStrings(vMasterBean.getVoucherTypeId(), "4"))
							ledgerDto.setCreditDebitFlag("1");
						else if (CPSUtils.compareStrings(vMasterBean.getVoucherTypeId(), "7") || CPSUtils.compareStrings(vMasterBean.getVoucherTypeId(), "9")
								|| CPSUtils.compareStrings(vMasterBean.getVoucherTypeId(), "10"))
							ledgerDto.setCreditDebitFlag("2");
						else if (CPSUtils.compareStrings(vMasterBean.getVoucherTypeId(), "5")) {
							if (CPSUtils.compareStrings(vMasterBean.getInventoryNo(), vMasterBean.getToInventory()))
								ledgerDto.setCreditDebitFlag("1");
							else
								ledgerDto.setCreditDebitFlag("2");
						}
						ledgerDto.setPostingDate(CPSUtils.formattedDate(vMasterBean.getPostingDate()));
						ledgerDto.setPurpose(vMasterBean.getPurpose());
						ledgerDto.setPostingDate(CPSUtils.formattedDate(vMasterBean.getPostingDate()));
						ledgerDto.setVoucherNo(vMasterBean.getVoucherId());
						//tx = session.beginTransaction();
						session.save(ledgerDto);
						session.flush();//tx.commit() ;
					}
				}

			}

			vMasterBean.setMessage(CPSConstants.SUCCESS);
		} catch (Exception e) {
			vMasterBean.setMessage(CPSConstants.FAILED);
			throw e;

		} finally {
			//session.close();
		}
		log.debug(">>>>>>>>>>method>>>>>>>>>>updateTxnDetails(VoucherMasterBean vMasterBean)>>>>>>>>End");
		return vMasterBean.getMessage();
	}

	public String certificateSanctionTxnDetails(VoucherRequestBean vMasterBean) throws Exception {
		log.debug(">>>>>>>>>>method>>>>>>>>>>certificateSanctionTxnDetails(VoucherMasterBean vMasterBean)>>>>>>>>Start");
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			String str = "select account_head_number as accountHeadNumber,consumed_fund as consumedFund from mmg_b_account_head_master where department_id="
					+ "(Select Department_id From Org_role_instance Where Org_role_id="
					+ "(select office_id from emp_master where sfid=(select sfid from mmg_b_inventory_holder where id=? and status=1) and status=1) and status=1) and status=1";
			AccountHeadDTO accdto = (AccountHeadDTO) session.createSQLQuery(str).addScalar("accountHeadNumber").addScalar("consumedFund").setResultTransformer(
					Transformers.aliasToBean(AccountHeadDTO.class)).setString(0, vMasterBean.getInventoryNo()).uniqueResult();
			session.flush();//tx.commit() ;
			if (!CPSUtils.isNullOrEmpty(accdto.getAccountHeadNumber())) {
				String amt = String.valueOf(Float.parseFloat(vMasterBean.getWorkAmount()) + Float.parseFloat(accdto.getConsumedFund()));
				//tx = session.beginTransaction();
				session.createQuery("update AccountHeadDTO set consumedFund=?,lastModifiedDate=sysdate where accountHeadNumber=? and status=1").setString(0, amt).setString(1,
						accdto.getAccountHeadNumber()).executeUpdate();
				session.flush();//tx.commit() ;
			}
			vMasterBean.setMessage(CPSConstants.SUCCESS);
		} catch (Exception e) {
			vMasterBean.setMessage(CPSConstants.FAILED);
			throw e;

		} finally {
			//session.close();
		}
		log.debug(">>>>>>>>>>method>>>>>>>>>>certificateSanctionTxnDetails(VoucherMasterBean vMasterBean)>>>>>>>>End");
		return vMasterBean.getMessage();
	}
}
