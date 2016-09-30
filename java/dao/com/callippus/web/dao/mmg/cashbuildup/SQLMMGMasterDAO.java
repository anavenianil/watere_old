package com.callippus.web.dao.mmg.cashbuildup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.mmg.cashbuildup.beans.DemandMasterDTO;
import com.callippus.web.mmg.cashbuildup.beans.MMGMasterBean;
import com.callippus.web.mmg.cashbuildup.dto.AccountHeadDTO;
import com.callippus.web.mmg.cashbuildup.dto.DemandItemDetailsDTO;
import com.callippus.web.mmg.cashbuildup.dto.InventoryMasterDTO;
import com.callippus.web.mmg.cashbuildup.dto.ItemCodeDTO;
import com.callippus.web.mmg.cashbuildup.dto.ItemSubCategoryDTO;
import com.callippus.web.mmg.cashbuildup.dto.ItemSubCodeDTO;
import com.callippus.web.mmg.cashbuildup.dto.ProjectCodeDTO;

@Service
public class SQLMMGMasterDAO implements IMMGMasterDAO, Serializable {
	private static Log log = LogFactory.getLog(SQLMMGMasterDAO.class);
	private static final long serialVersionUID = 5611041426238862266L;

	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	@SuppressWarnings("unchecked")
	public String checkMMGName(MMGMasterBean mmgmasterBean) throws Exception {
		log.debug("checkName() --> Start");
		String message = null;
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			StringBuffer sb = new StringBuffer();

			if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.MATERIALMASTERDTO)) {
				if (CPSUtils.isNullOrEmpty(mmgmasterBean.getId()))
					sb.append("select count(*) from " + mmgmasterBean.getBenaName() + " where upper(materialName)=? or materialCode=? and status=1");
				else
					sb.append("select count(*) from " + mmgmasterBean.getBenaName() + " where upper(materialName)=? and materialCode!=? and status=1");

			} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.INVENTORYMASTERDTO))
				sb.append("select count(*) from " + mmgmasterBean.getBenaName() + " where inventoryNo=? and sfid=? and orgRoleId=? and status=1");
			else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ACCOUNTHEADMASTER))
				sb.append("select count(*) from " + mmgmasterBean.getBenaName() + " where accountHeadNumber=? and status=1");
			else
				//sb.append("select count(*) from " + mmgmasterBean.getBenaName() + " where (upper(name)=?");
				sb.append("select count(*) from " + mmgmasterBean.getBenaName() + " where upper(name)=?");

			if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ITEMCATEGORYDTO)) {
				sb.append("or categoryCode=?) and status=1");
			} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ITEMSUBCATEGORYDTO)) {
				sb.append("or subCategoryCode=?) and status=1");
			} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ITEMCODEDTO)) {
				sb.append("or itemCode=?) and status=1");
			} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ITEMSUBCODEDTO)) {
				sb.append("or itemSubCode=?) and status=1");
			} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.COMPANYMASTERDTO)) {
				sb.append("or companyCode=?) and status=1");
			}

			if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.INVENTORYMASTERDTO)) {
				if (!CPSUtils.isNullOrEmpty(mmgmasterBean.getInvId()))
					sb.append(" and invId!=" + mmgmasterBean.getInvId());
			} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ACCOUNTHEADMASTER)) {
				if (!CPSUtils.isNullOrEmpty(mmgmasterBean.getAccId()))
					sb.append(" and accId!=" + mmgmasterBean.getAccId());
			} else if (!CPSUtils.isNullOrEmpty(mmgmasterBean.getId())) {
				if (!CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.MATERIALMASTERDTO))
					sb.append(" and id!=" + mmgmasterBean.getId());
			}

			log.debug("Query --> " + sb.toString());
			Query qry = session.createQuery(sb.toString());
			if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.MATERIALMASTERDTO)) {
				qry.setString(0, mmgmasterBean.getMaterialName().toUpperCase());
				qry.setString(1, mmgmasterBean.getMaterialCode());
			} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.INVENTORYMASTERDTO)) {
				qry.setString(0, mmgmasterBean.getInventoryNo());
				qry.setString(1, mmgmasterBean.getSfid().split(",")[0]);
				qry.setString(2, mmgmasterBean.getOrgRoleId());
			} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ACCOUNTHEADMASTER)) {
				qry.setString(0, mmgmasterBean.getAccountHeadNumber());
			} else
				qry.setString(0, mmgmasterBean.getName().toUpperCase());
			if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ITEMCATEGORYDTO)) {
				qry.setString(1, mmgmasterBean.getCategoryCode());
			} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ITEMSUBCATEGORYDTO)) {
				qry.setString(1, mmgmasterBean.getSubCategoryCode());
			} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ITEMCODEDTO)) {
				qry.setString(1, mmgmasterBean.getItemCode());
			} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ITEMSUBCODEDTO)) {
				qry.setString(1, mmgmasterBean.getItemSubCode());
			} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.COMPANYMASTERDTO)) {
				qry.setString(1, mmgmasterBean.getCompanyCode());
			}
			List<Object> list = qry.list();
			session.flush();//tx.commit() ;
			if (CPSUtils.checkList(list) && Integer.parseInt(list.get(0).toString()) > 0) {
				message = CPSConstants.DUPLICATE;
			} else {
				message = CPSConstants.SUCCESS;
			}

		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();

		}
		log.debug("checkName() --> End");
		return message;
	}

	public String createMMGMasterData(Object obj) throws Exception {
		log.debug("createMasterData() --> Start");
		String message = null;
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			session.save(obj);
			session.flush();//tx.commit() ;

			message = CPSConstants.SUCCESS;
			log.debug("message -->" + message);
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		log.debug("createMasterData() --> End");
		return message;
	}

	public String updateMMGMasterData(MMGMasterBean mmgmasterBean) throws Exception {
		log.debug("updateMasterData() --> Start");
		String message = null;
		Session session = null;
		String sql = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.TAXTYPEDTO))
				sql = "update " + mmgmasterBean.getBenaName() + " set name=?,lastModifiedDate=sysdate,description=?,percentage=? where id=?";
			else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.COMPANYMASTERDTO))
				sql = "update " + mmgmasterBean.getBenaName() + " set name=?,lastModifiedDate=sysdate,description=?,company_code=? where id=?";
			else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ITEMCATEGORYDTO))
				sql = "update " + mmgmasterBean.getBenaName() + " set name=?,lastModifiedDate=sysdate,description=?,categoryCode=? where id=?";
			else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ITEMSUBCATEGORYDTO))
				sql = "update " + mmgmasterBean.getBenaName() + " set name=?,lastModifiedDate=sysdate,description=?,categoryId=?,subCategoryCode=? where id=?";
			else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ITEMCODEDTO))
				sql = "update " + mmgmasterBean.getBenaName() + " set name=?,lastModifiedDate=sysdate,description=?,itemCode=?,categoryId=?,subCategoryId=? where id=?";
			else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ITEMSUBCODEDTO))
				sql = "update " + mmgmasterBean.getBenaName() + " set name=?,lastModifiedDate=sysdate,description=?,itemCodeId=?,itemSubCode=? where id=?";
			else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.MATERIALMASTERDTO))
				sql = "update "
						+ mmgmasterBean.getBenaName()
						+ " set materialName=?,lastModifiedDate=sysdate,description=?,companyCode=?,consumableFlag=?,uom=?,categoryCode=?,subCategoryCode=?,itemCode=?,itemSubCode=?,rcFlag=?,unitRate=? where materialCode=?";
			else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.INVENTORYMASTERDTO))
				sql = "update " + mmgmasterBean.getBenaName() + " set sfid=?,lastModifiedDate=sysdate,inventoryHolderType=? where invId=?";
			else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ACCOUNTHEADMASTER))
				sql = "update "
						+ mmgmasterBean.getBenaName()
						+ " set accountHeadNumber=?,lastModifiedDate=sysdate,qtyHeld=?,qtyRequired=?,allottedFund=?,year=?,fundTypeId=?,departmentId=?,consumedFund=?,commitedFund=?,description=? where accId=?";
			else
				sql = "update " + mmgmasterBean.getBenaName() + " set name=?,last_modified_date=sysdate,description=? where id=?";
			log.debug("query -->" + sql);
			Query qry = session.createQuery(sql);
			if (!CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.MATERIALMASTERDTO) && !CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.INVENTORYMASTERDTO)
					&& !CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ACCOUNTHEADMASTER)) {
				qry.setString(0, mmgmasterBean.getName());
				qry.setString(1, mmgmasterBean.getDescription());
			}
			if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.TAXTYPEDTO)) {
				qry.setString(2, mmgmasterBean.getPercentage());
				qry.setString(3, mmgmasterBean.getId());
			} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.COMPANYMASTERDTO)) {
				qry.setString(2, mmgmasterBean.getCompanyCode());
				qry.setString(3, mmgmasterBean.getId());
			} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ITEMCATEGORYDTO)) {
				qry.setString(2, mmgmasterBean.getCategoryCode());
				qry.setString(3, mmgmasterBean.getId());
			} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ITEMSUBCATEGORYDTO)) {
				qry.setString(2, mmgmasterBean.getCategoryId());
				qry.setString(3, mmgmasterBean.getSubCategoryCode());
				qry.setString(4, mmgmasterBean.getId());
			} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ITEMCODEDTO)) {
				qry.setString(2, mmgmasterBean.getItemCode());
				qry.setString(3, mmgmasterBean.getCategoryId());
				qry.setString(4, mmgmasterBean.getSubCategoryId());
				qry.setString(5, mmgmasterBean.getId());
			} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ITEMSUBCODEDTO)) {
				qry.setString(2, mmgmasterBean.getItemCodeId());
				qry.setString(3, mmgmasterBean.getItemSubCode());
				qry.setString(4, mmgmasterBean.getId());
			} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.MATERIALMASTERDTO)) {
				qry.setString(0, mmgmasterBean.getMaterialName());
				qry.setString(1, mmgmasterBean.getDescription());
				qry.setString(2, mmgmasterBean.getCompanyCode());
				qry.setString(3, mmgmasterBean.getConsumableFlag());
				qry.setString(4, mmgmasterBean.getUom());
				qry.setString(5, mmgmasterBean.getCategoryCode());
				qry.setString(6, mmgmasterBean.getSubCategoryCode());
				qry.setString(7, mmgmasterBean.getItemCode());
				qry.setString(8, mmgmasterBean.getItemSubCode());
				qry.setString(9, mmgmasterBean.getRcFlag());
				qry.setString(10, mmgmasterBean.getUnitRate());
				qry.setString(11, mmgmasterBean.getMaterialCode());

			} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.INVENTORYMASTERDTO)) {
				qry.setString(0, mmgmasterBean.getSfid());
				qry.setString(1, mmgmasterBean.getInventoryHolderType());
				qry.setString(2, mmgmasterBean.getInvId());
			} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ACCOUNTHEADMASTER)) {
				qry.setString(0, mmgmasterBean.getAccountHeadNumber());
				qry.setString(1, mmgmasterBean.getQtyHeld());
				qry.setString(2, mmgmasterBean.getQtyRequired());
				qry.setString(3, mmgmasterBean.getAllottedFund());
				qry.setString(4, mmgmasterBean.getYear());
				qry.setString(5, mmgmasterBean.getFundTypeId());
				qry.setString(6, mmgmasterBean.getDepartmentId());
				qry.setString(7, mmgmasterBean.getConsumedFund());
				qry.setString(8, mmgmasterBean.getCommitedFund());
				qry.setString(9, mmgmasterBean.getDescription());
				qry.setString(10, mmgmasterBean.getAccId());
			} else {
				qry.setString(2, mmgmasterBean.getId());
			}
			qry.executeUpdate();
			session.flush();//tx.commit() ;
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;

		} finally {
			//session.close();
		}
		log.debug("updateMasterData() --> End");
		return message;
	}

	public String deleteMMGMasterData(MMGMasterBean mmgmasterBean) throws Exception {
		log.debug("deleteMasterData() --> Start");
		String message = null;
		Session session = null;
		String sql = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.MATERIALMASTERDTO))
				sql = "update " + mmgmasterBean.getBenaName() + " set status=0,lastModifiedDate=sysdate where materialCode=?";
			else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.INVENTORYMASTERDTO))
				sql = "update " + mmgmasterBean.getBenaName() + " set status=0,lastModifiedDate=sysdate where invId=?";
			else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ACCOUNTHEADMASTER))
				sql = "update " + mmgmasterBean.getBenaName() + " set status=0,lastModifiedDate=sysdate where accId=?";
			else
				sql = "update " + mmgmasterBean.getBenaName() + " set status=0,lastModifiedDate=sysdate where id=?";
			log.debug("Query -->" + sql);
			Query qry = session.createQuery(sql);
			if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.MATERIALMASTERDTO))
				qry.setString(0, mmgmasterBean.getMaterialCode());
			else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.INVENTORYMASTERDTO))
				qry.setString(0, mmgmasterBean.getInvId());
			else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ACCOUNTHEADMASTER))
				qry.setString(0, mmgmasterBean.getAccId());
			else
				qry.setInteger(0, Integer.parseInt(mmgmasterBean.getId()));
			qry.executeUpdate();
			session.flush();//tx.commit() ;
			message = CPSConstants.SUCCESS;

		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		log.debug("deleteMasterData() --> End");
		return message;

	}

	@SuppressWarnings("unchecked")
	public List<ItemSubCategoryDTO> getItemSubCategory() throws Exception {
		log.debug("getItemSubCategory() --> Start");
		Session session = null;
		List itemSubCatList = null;
		Transaction tx = null;
		try {
			itemSubCatList = new ArrayList<ItemSubCategoryDTO>();
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			String sql = "select scm.id as id,scm.name as name,scm.description as description,scm.subCategoryCode as subCategoryCode,scm.categoryId as categoryId,cm.name as categoryName"
					+ " from ItemSubCategoryDTO scm,ItemCategoryDTO cm where scm.categoryId=cm.id and scm.status=1 and cm.status=1 order by scm.name";
			log.debug("query --> " + sql);
			itemSubCatList = session.createQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			session.flush();//tx.commit() ;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		log.debug("getItemSubCategory() --> End");
		return itemSubCatList;
	}

	@SuppressWarnings("unchecked")
	public List<ItemCodeDTO> getItemCodeDetails() throws Exception {
		log.debug("getItemCodeDetails() --> Start");
		Session session = null;
		List itemSubCatList = null;
		Transaction tx = null;
		try {
			itemSubCatList = new ArrayList<ItemSubCategoryDTO>();
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			String sql = "select ic.id as id,ic.name as name,ic.description as description,ic.itemCode as itemCode,ic.categoryId as categoryId,ic.subCategoryId as subCategoryId,"
					+ "icd.name as categoryName,isd.name as subCategoryName from ItemCodeDTO ic,ItemSubCategoryDTO isd,ItemCategoryDTO icd where isd.id=ic.subCategoryId and icd.id=ic.categoryId and isd.status=1 and icd.status=1"
					+ " and ic.status=1 order by ic.name";
			log.debug("query --> " + sql);
			itemSubCatList = session.createQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			session.flush();//tx.commit() ;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		log.debug("getItemCodeDetails() --> End");
		return itemSubCatList;
	}

	@SuppressWarnings("unchecked")
	public List<ItemSubCodeDTO> getItemSubCodeDetails() throws Exception {
		log.debug("getItemSubCodeDetails() --> Start");
		Session session = null;
		List itemSubCatList = null;
		Transaction tx = null;
		try {
			itemSubCatList = new ArrayList<ItemSubCategoryDTO>();
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			String sql = "select isd.id as id,isd.name as name,isd.description as description,isd.itemCodeId as itemCodeId,isd.itemSubCode as itemSubCode,"
					+ "ic.name as itemCodeName from ItemCodeDTO ic,ItemSubCodeDTO isd where ic.id=isd.itemCodeId and isd.status=1 and ic.status=1 order by isd.name";
			log.debug("query --> " + sql);
			itemSubCatList = session.createQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			session.flush();//tx.commit() ;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		log.debug("getItemSubCodeDetails() --> End");
		return itemSubCatList;
	}

	@SuppressWarnings("unchecked")
	public List<InventoryMasterDTO> getInventoryHolderDetails() throws Exception {
		log.debug("getInventoryHolderDetails() --> Start");
		Session session = null;
		List itemSubCatList = null;
		Transaction tx = null;
		try {
			itemSubCatList = new ArrayList<InventoryMasterDTO>();
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			String sql = "Select Ind.Id As invId,Ind.Inventory_no As inventoryNo,Ind.Sfid As sfid,Ind.Inventory_holder_type As Inventoryholdertype,Ind.Org_role_id As Orgroleid,Ind.PROJECT_ID as projectName,"
					+ "(Select Case When Ind.Org_role_id Is Null Then (Select Divdept.Department_name As Divisionname From Emp_master Emp,Designation_master Dm,Org_role_instance Directorate,Org_role_instance Division,"
					+ "Departments_master Dirdept,Departments_master Divdept Where Emp.Sfid=ind.sfid And Emp.Directorate_id=Directorate.Org_role_id And Emp.Designation_id=Dm.Id And"
					+ " directorate.department_id=dirdept.department_id and emp.office_id=division.org_role_id and division.department_id=divdept.department_id) else(select dm.department_name from Departments_master Dm where DM.DEPARTMENT_ID=ind.org_role_id) end from dual) As deptName,St.Status As InventoryHolderTypeName"
					+ " From Mmg_b_inventory_holder Ind,Status_master St Where St.Id=Ind.Inventory_holder_type And Ind.Status=1 order by ind.Inventory_no";
			log.debug("query --> " + sql);
			itemSubCatList = session.createSQLQuery(sql).addScalar("invId").addScalar("inventoryNo").addScalar("sfid").addScalar("deptName").addScalar("projectName", Hibernate.STRING).addScalar(
					"InventoryHolderType").addScalar("InventoryHolderTypeName").addScalar("orgRoleId", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(InventoryMasterDTO.class))
					.list();
			session.flush();//tx.commit() ;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		log.debug("getInventoryHolderDetails() --> End");
		return itemSubCatList;
	}

	@SuppressWarnings("unchecked")
	public List<AccountHeadDTO> getAccountHeadDetails(MMGMasterBean mmgmasterBean) throws Exception {
		log.debug("getInventoryHolderDetails() --> Start");
		Session session = null;
		List itemSubCatList = null;
		Transaction tx = null;
		try {
			itemSubCatList = new ArrayList<AccountHeadDTO>();
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			if (CPSUtils.isNullOrEmpty(mmgmasterBean.getSearchWith()) || CPSUtils.compareStrings(mmgmasterBean.getSearchWith(), CPSConstants.SELECT)) {
				String sql = "select ah.id as accId,ah.ACCOUNT_HEAD_NUMBER as accountHeadNumber,ah.QTY_HELD as qtyHeld,ah.QTY_REQUIRED as qtyRequired,ah.ALLOTTED_FUND as allottedFund,"
						+ "ah.YEAR as year,ah.FUND_TYPE_ID as fundTypeId,ah.DEPARTMENT_ID as departmentId,ah.CONSUMED_FUND as consumedFund,ah.COMMITTED_FUND as commitedFund,ah.ACCOUNT_DESCRIPTION as description,dm.DEPARTMENT_NAME as deptName,sm.NAME as fundTypeName"
						+ " from MMG_B_FUND_TYPE_MASTER sm,DEPARTMENTS_MASTER dm,MMG_B_ACCOUNT_HEAD_MASTER ah where dm.DEPARTMENT_ID=ah.DEPARTMENT_ID and sm.id=ah.FUND_TYPE_ID and ah.status=1 and dm.status=1 and sm.status=1 order by ah.ACCOUNT_HEAD_NUMBER";
				log.debug("query --> " + sql);
				itemSubCatList = session.createSQLQuery(sql).addScalar("accId").addScalar("accountHeadNumber").addScalar("qtyHeld").addScalar("qtyRequired").addScalar("allottedFund")
						.addScalar("year").addScalar("fundTypeId").addScalar("departmentId").addScalar("consumedFund").addScalar("commitedFund").addScalar("deptName").addScalar("fundTypeName")
						.addScalar("description").setResultTransformer(Transformers.aliasToBean(AccountHeadDTO.class)).list();
			} else if (!CPSUtils.isNullOrEmpty(mmgmasterBean.getSearchWithValue())) {
				String sql = "select ah.id as accId,ah.ACCOUNT_HEAD_NUMBER as accountHeadNumber,ah.QTY_HELD as qtyHeld,ah.QTY_REQUIRED as qtyRequired,ah.ALLOTTED_FUND as allottedFund,"
						+ "ah.YEAR as year,ah.FUND_TYPE_ID as fundTypeId,ah.DEPARTMENT_ID as departmentId,ah.CONSUMED_FUND as consumedFund,ah.COMMITTED_FUND as commitedFund,ah.ACCOUNT_DESCRIPTION as description,dm.DEPARTMENT_NAME as deptName,sm.NAME as fundTypeName"
						+ " from MMG_B_FUND_TYPE_MASTER sm,DEPARTMENTS_MASTER dm,MMG_B_ACCOUNT_HEAD_MASTER ah where dm.DEPARTMENT_ID=ah.DEPARTMENT_ID and sm.id=ah.FUND_TYPE_ID and ah.status=1 and dm.status=1 and upper(ah.ACCOUNT_HEAD_NUMBER) like ? and sm.status =1 order by ah.ACCOUNT_HEAD_NUMBER";
				log.debug("query --> " + sql);
				itemSubCatList = session.createSQLQuery(sql).addScalar("accId").addScalar("accountHeadNumber").addScalar("qtyHeld").addScalar("qtyRequired").addScalar("allottedFund")
						.addScalar("year").addScalar("fundTypeId").addScalar("departmentId").addScalar("consumedFund").addScalar("commitedFund").addScalar("deptName").addScalar("fundTypeName")
						.addScalar("description").setResultTransformer(Transformers.aliasToBean(AccountHeadDTO.class)).setString(0, mmgmasterBean.getSearchWithValue().toUpperCase() + "%").list();
			}
			session.flush();//tx.commit() ;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		log.debug("getInventoryHolderDetails() --> End");
		return itemSubCatList;
	}

	public String deleteMMGCheckMaster(String beanName, String propertyName, String status, String propertyValue) throws Exception {
		Session session = null;
		Transaction tx = null;
		String message = null;
		List list = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			StringBuffer sb = new StringBuffer();
			sb.append("select count(*) from " + beanName + " where " + propertyName + "=? ");
			if (CPSUtils.compareStrings(status, CPSConstants.STATUS)) {
				sb.append("and status=1");
				/*
				 * } if(CPSUtils.compareStrings(beanName,CPSConstants.MATERIALMASTERDTO)){
				 */Query qry = session.createQuery(sb.toString());
				qry.setString(0, propertyValue);
				list = qry.list();
				session.flush();//tx.commit() ;
			}

			if (CPSUtils.checkList(list) && Integer.parseInt(list.get(0).toString()) > 0) {
				message = CPSConstants.FAILED;
			}

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public int getInventoryNum() throws Exception {
		log.debug("getInventoryNum() --> Start");
		int inventoryNo = 0;
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			inventoryNo = Integer.parseInt(session.createSQLQuery(
					"select max(case when inventory_no like '%P' then substr(inventory_no,0,length(inventory_no)-1) else inventory_no end) one from mmg_b_inventory_holder").uniqueResult().toString()) + 1;
			session.flush();//tx.commit() ;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		log.debug("getInventoryNum() --> End");
		return inventoryNo;
	}

	@SuppressWarnings("unchecked")
	public List<MMGMasterBean> getMaterialDetails() throws Exception {
		log.debug("getMaterialDetails() --> Start");
		Session session = null;
		List itemSubCatList = null;
		Transaction tx = null;
		try {
			itemSubCatList = new ArrayList<MMGMasterBean>();
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			String sql = "Select Mmd.Id As materialCode,Mmd.Category_id As categoryCode,Mmd.Sub_category_id As subCategoryCode,Mmd.Item_code_id As itemCode,Mmd.Item_sub_code_id As itemSubCode,Mmd.Company_code As companyCode,Mmd.Material_name As materialName,"
					+ "Mmd.Consumable_flag As consumableFlag,Mmd.Uom As uom,Mmd.Unit_rate As unitRate,Mmd.Description As description,Mmd.Rate_contract_flag As rcFlag,Ic.Category_name As categoryName,Ic.Category_code As categoryId,Isc.Sub_category_name As subCategoryName,Isc.Sub_code As subCategoryId,Icd.Item_name As codeName,"
					+ "Icd.Item_code As itemCodeId,Iscd.Sub_code_name As subCodeName,Iscd.Item_sub_code As itemSubCodeId,Cmd.Name As companyName,Cmd.Company_code As companyId,Uom.Name As uomName From Mmg_b_material_master Mmd,Mmg_b_company_master Cmd,Mmg_b_uom_master Uom,Mmg_b_item_category_master Ic,Mmg_b_item_sub_category_master Isc,"
					+ "Mmg_b_item_code_master Icd,Mmg_b_item_sub_code_master Iscd Where Ic.Id=Mmd.Category_id And Isc.Id=Mmd.Sub_category_id And icd.id=Mmd.Item_code_id and iscd.id(+)=Mmd.Item_sub_code_id and cmd.id(+)=Mmd.Company_code and uom.id=Mmd.Uom and mmd.status=1 order by Mmd.Material_name";
			log.debug("query --> " + sql);
			itemSubCatList = session.createSQLQuery(sql).addScalar("materialCode").addScalar("categoryCode").addScalar("subCategoryCode").addScalar("itemCode").addScalar("itemSubCode").addScalar(
					"companyCode").addScalar("materialName").addScalar("consumableFlag").addScalar("uom").addScalar("unitRate").addScalar("description").addScalar("rcFlag", Hibernate.STRING)
					.addScalar("categoryName").addScalar("categoryId").addScalar("subCategoryName").addScalar("subCategoryId").addScalar("codeName").addScalar("itemCodeId").addScalar("subCodeName")
					.addScalar("itemSubCodeId").addScalar("companyName").addScalar("companyId").addScalar("uomName").setResultTransformer(Transformers.aliasToBean(MMGMasterBean.class)).list();
			session.flush();//tx.commit() ;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		log.debug("getMaterialDetails() --> End");
		return itemSubCatList;
	}

	@SuppressWarnings("unchecked")
	public DemandMasterDTO getDemandDetails(String demandNo, String status) throws Exception {
		log.debug("getDemandDetails() --> Start");
		Session session = null;
		DemandMasterDTO demand = null;
		List<Object> itemSubCatList = null;
		List<DemandItemDetailsDTO> demandItems = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			demand = (DemandMasterDTO) session.createQuery("from DemandMasterDTO m where m.status=? and demandNo=?").setString(0, status).setString(1, demandNo).uniqueResult();
			String sql = "select ditem.demand_no as demandNo,mmd.id as materialCode,mmd.MATERIAL_NAME as description,mmd.CONSUMABLE_FLAG as cflag,ditem.UNIT_RATE as unitRate, uom.name as uom,amount.name as amountType,ditem.QTY as qty,(ditem.qty*ditem.UNIT_RATE) as amount "
					+ " from MMG_B_MATERIAL_MASTER mmd,MMG_B_UOM_MASTER uom,MMG_B_AMOUNT_TYPE_MASTER amount,MMG_B_DEMAND_ITEM_DETAILS ditem where ditem.MATERIAL_CODE=mmd.id and ditem.demand_no=? "
					+ " and  stage_id=(select max(stage_id) from MMG_B_DEMAND_ITEM_DETAILS where demand_no=?) and ditem.AMOUNT_TYPE=amount.id and mmd.status=1 and mmd.uom=uom.id and uom.status=1";
			itemSubCatList = session.createSQLQuery(sql).addScalar("materialCode").addScalar("description").addScalar("cflag").addScalar("amount").addScalar("demandNo", Hibernate.STRING).addScalar(
					"unitRate").addScalar("uom").addScalar("amountType").addScalar("qty").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setString(0, demandNo).setString(1, demandNo).list();
			demandItems = new ArrayList();
			Float total = 0.0f;
			for (int i = 0; i < itemSubCatList.size(); i++) {
				HashMap<String, Object> map = (HashMap<String, Object>) itemSubCatList.get(i);
				DemandItemDetailsDTO items = new DemandItemDetailsDTO();
				BeanUtils.copyProperties(items, map);
				total += Float.parseFloat(items.getAmount());
				demandItems.add(items);
			}
			demand.setDemandItems(demandItems);
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return demand;
	}

	@SuppressWarnings("unchecked")
	public List<DemandMasterDTO> getDemandNumbers(String status) throws Exception {
		log.debug("getDemandDetails() --> Start");
		Session session = null;
		List<DemandMasterDTO> demandList = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			demandList = session.createQuery("from DemandMasterDTO m where m.status=?").setString(0, status).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return demandList;
	}

	@SuppressWarnings("unchecked")
	public List<AccountHeadDTO> getAccountHeadNos(String id) throws Exception {
		log.debug("getDemandDetails() --> Start");
		Session session = null;
		List<AccountHeadDTO> accountList = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String str = "select acc.id as accId,account_head_number as accountHeadNumber,consumed_fund as consumedFund,fund.name as fundTypeName from mmg_b_account_head_master acc,mmg_b_fund_type_master fund where department_id=(Select Department_id From Org_role_instance"
					+ " Where Org_role_id=(select office_id from emp_master where sfid=(select sfid from mmg_b_inventory_holder where id=? and status=1) and status=1) and status=1) and acc.status=1 and fund.status=1 and fund.id=acc.fund_type_id";
			accountList = (List<AccountHeadDTO>) session.createSQLQuery(str).addScalar("accId").addScalar("accountHeadNumber").addScalar("consumedFund").addScalar("fundTypeName")
					.setResultTransformer(Transformers.aliasToBean(AccountHeadDTO.class)).setString(0, id).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return accountList;
	}

	public AccountHeadDTO checkingAccountHeadAmt(String inventoryNo) throws Exception {
		AccountHeadDTO accdto = null;
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			String str = "select account_head_number as accountHeadNumber,allotted_fund as allottedFund,committed_fund as commitedFund,consumed_fund as consumedFund from mmg_b_account_head_master where department_id="
					+ "(Select Department_id From Org_role_instance Where Org_role_id="
					+ "(select office_id from emp_master where sfid=(select sfid from mmg_b_inventory_holder where id=? and status=1) and status=1) and status=1) and status=1";
			accdto = (AccountHeadDTO) session.createSQLQuery(str).addScalar("accountHeadNumber").addScalar("allottedFund").addScalar("commitedFund").addScalar("consumedFund").setResultTransformer(
					Transformers.aliasToBean(AccountHeadDTO.class)).setString(0, inventoryNo).uniqueResult();
			session.flush();//tx.commit() ;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return accdto;
	}

	public String UpdateDemandStatus(String demandNo, String status) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			session.createQuery("update DemandMasterDTO set status=? where demandNo=?").setString(0, status).setString(1, demandNo).executeUpdate();
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public List<ProjectCodeDTO> getProjectList() throws Exception {
		Session session = null;
		List<ProjectCodeDTO> list = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			list = session.createCriteria(ProjectCodeDTO.class).add(Expression.eq("status", 1)).addOrder(Order.asc("projectName")).list();
			session.flush();//tx.commit() ;

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}

	public String saveMMGConfigDetails(MMGMasterBean mmgmasterBean) throws Exception {
		String message = null;
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			Object obj = session.createSQLQuery("select count(*) from configuration_details where upper(name)=upper(?)").setString(0, mmgmasterBean.getName()).uniqueResult();
			session.flush();//tx.commit() ;
			if (!CPSUtils.isNullOrEmpty(obj) && Integer.parseInt(obj.toString()) > 0) {
				//tx = session.beginTransaction();
				session.createSQLQuery("update configuration_details set value=?,description=? where upper(name)=upper(?)").setString(0, mmgmasterBean.getTypeValue()).setString(1,
						mmgmasterBean.getDescription()).setString(2, mmgmasterBean.getName()).executeUpdate();
				session.flush();//tx.commit() ;
			} else {
				//tx = session.beginTransaction();
				session.createSQLQuery("insert into configuration_details(name,value,description) values(?,?,?)").setString(0, mmgmasterBean.getName()).setString(1, mmgmasterBean.getTypeValue())
						.setString(2, mmgmasterBean.getDescription()).executeUpdate();
				session.flush();//tx.commit() ;
			}
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> getFinancialYearList() throws Exception {
		List<KeyValueDTO> list = null;
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			list = session.createSQLQuery("select name as name,value as key from configuration_details where name='FINANCIAL_YEAR'").addScalar("name").addScalar("key").setResultTransformer(
					Transformers.aliasToBean(KeyValueDTO.class)).list();
			session.flush();//tx.commit() ;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}
}