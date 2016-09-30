package com.callippus.web.business.domainobject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.address.AddressBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.ConnectionUtil;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.mmg.cashbuildup.dto.InventoryHolderRequestBean;
import com.callippus.web.mmg.cashbuildup.dto.InventoryMasterDTO;

@Service
public class InventoryDomainObject extends DomainObject {
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private DomainObject domainObject;
	
	
	/**
	 * This method will update the txn inventory details
	 * 
	 * @param prb
	 * @return
	 * @throws Exception
	 */
	public String updateTxnDetails(InventoryHolderRequestBean invRequestBean) throws Exception {

		Session session = null;
		Connection con = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps1 = null;
		ResultSet rsq2 = null;
		Transaction tx = null;
		try {
			//VN         session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			session = hibernateUtils.getSession();

			con = session.connection();

			////tx = session.beginTransaction();
			String getTxnDetails = "SELECT row_number,reference_number,MAX(CASE WHEN column_name = 'INVENTORY_NO' THEN new_value END) AS INVENTORY_NO,MAX(CASE WHEN column_name = 'SFID' "
					+ "THEN new_value END) AS SFID,MAX(CASE WHEN column_name = 'DELETE' THEN new_value END) AS COLUMN_DELETE,MAX(CASE WHEN column_name = 'ORG_ROLE_ID' THEN new_value END) AS ORG_ROLE_ID,MAX(CASE WHEN column_name = 'INVENTORY_HOLDER_TYPE' "
					+ "THEN new_value END) AS INVENTORY_HOLDER_TYPE FROM workflow_txn_details where request_id=? GROUP BY row_number, reference_number";

			ps2 = con.prepareStatement(getTxnDetails);
			ps2.setString(1, invRequestBean.getRequestID());
			rsq2 = ps2.executeQuery();
			session.flush();
			//session.flush();//tx.commit() ;
			if (rsq2.next()) {

				// String rowNumber = rsq2.getString("row_number");
				String delete = rsq2.getString("COLUMN_DELETE");
				String referenceNumber = rsq2.getString("reference_number");
				String inventoryNo = rsq2.getString("INVENTORY_NO");
				String sfid = rsq2.getString("SFID");
				// String demandPurchaseLimit = rsq2.getString("DEMAND_PURCHASE_LIMIT");
				String inventoryHolderType = rsq2.getString("INVENTORY_HOLDER_TYPE");
				String orgRoleId = rsq2.getString("ORG_ROLE_ID");
				/*
				 * // Home town address changed
				 * 
				 * String updateStatus = "update MMG_B_INVENTORY_HOLDER set status=0,last_modified_date=sysdate where INVENTORY_NO=?"; ps1 = con.prepareStatement(updateStatus); ps1.setString(1,
				 * referenceNumber); ps1.executeUpdate();
				 */

				Criteria crit = session.createCriteria(InventoryMasterDTO.class);
				crit.add(Expression.eq("inventoryNo", referenceNumber));
				List<AddressBean> list = crit.list();
				Iterator it = list.iterator();
				if (it.hasNext()) {
					InventoryMasterDTO invMasterDTO = (InventoryMasterDTO) it.next();

					if (!CPSUtils.isNullOrEmpty(inventoryNo)) {
						invMasterDTO.setInventoryNo(CPSUtils.setNullIfEmpty(inventoryNo));
					}
					if (!CPSUtils.isNullOrEmpty(sfid)) {
						invMasterDTO.setSfid(CPSUtils.setNullIfEmpty(sfid));
					}
					if (!CPSUtils.isNullOrEmpty(orgRoleId)) {
						invMasterDTO.setOrgRoleId(CPSUtils.setNullIfEmpty(orgRoleId));
					}
					if (!CPSUtils.isNullOrEmpty(inventoryHolderType)) {
						if (CPSUtils.compareStrings(inventoryHolderType, "Buildup"))
							inventoryHolderType = "30";
						else if (CPSUtils.compareStrings(inventoryHolderType, "Project"))
							inventoryHolderType = "31";
						invMasterDTO.setInventoryHolderType(CPSUtils.setNullIfEmpty(inventoryHolderType));
					}
					invMasterDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					invMasterDTO.setCreationDate(CPSUtils.formattedDate(invMasterDTO.getCreationDate()));
					if (CPSUtils.compareStrings(delete, CPSConstants.DELETE)) {
						invMasterDTO.setStatus(0);
					} else
						invMasterDTO.setStatus(1);
					////tx = session.beginTransaction();
					session.saveOrUpdate(invMasterDTO);
					session.flush();
					//session.flush();//tx.commit() ;
				}
			}
			invRequestBean.setMessage(CPSConstants.SUCCESS);

		} catch (Exception e) {
			invRequestBean.setMessage(CPSConstants.FAILED);
			throw e;

		} finally {
			ConnectionUtil.closeConnection(null, ps1, rsq2);
			ConnectionUtil.closeConnection(null, ps2, null);
			ConnectionUtil.closeConnection(null, ps3, null);
		}
		return invRequestBean.getMessage();
	}
}
