package com.callippus.web.ltc.dao.master;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.ltc.beans.application.LtcApplicationBean;

@Service
public class SQLLtcMasterDAO implements ILtcMasterDAO, Serializable {
	private static Log log = LogFactory.getLog(SQLLtcMasterDAO.class);
	private static final long serialVersionUID = 5611041426238862266L;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	@SuppressWarnings("unchecked")
	public String checkLtcName(LtcApplicationBean ltcBean) throws Exception {
		log.debug("checkLtcName() --> Start");
		String message = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();;
			StringBuffer sb = new StringBuffer();
			if (CPSUtils.compareStrings(ltcBean.getType(), CPSConstants.LTCTYPE)) {
				sb.append("select count(*) from LtcTypeMasterDTO where trim(upper(name))=trim(upper(?)) and status=1");
			} else if (CPSUtils.compareStrings(ltcBean.getType(), CPSConstants.LTCBLOCK)) {
				sb.append("select count(*) from LtcBlockMasterDTO where upper(name)=? and status=1");
			} else if (CPSUtils.compareStrings(ltcBean.getType(), CPSConstants.LTCBLOCKYEAR)) {
				sb.append("select count(*) from LtcBlockYearsMasterDTO where ? between fromDate and toDate and ltcBlockId=? and status=1");
			}else if(CPSUtils.compareStrings(ltcBean.getType(), CPSConstants.LTCPENALINTERESTMASTER)){
				sb.append("select count(*) from LtcPenalMasterDTO where fromDate=? and toDate=? and status=1");
			}
			if (!CPSUtils.isNullOrEmpty(ltcBean.getId())) {
				sb.append(" and id!=" + ltcBean.getId());
			}
			log.debug("Query --> " + sb.toString());
			Query qry = session.createQuery(sb.toString());
			if (CPSUtils.compareStrings(ltcBean.getType(), CPSConstants.LTCTYPE) || CPSUtils.compareStrings(ltcBean.getType(), CPSConstants.LTCBLOCK)) {
				qry.setString(0, ltcBean.getName().toUpperCase());
			}else if (CPSUtils.compareStrings(ltcBean.getType(), CPSConstants.LTCPENALINTERESTMASTER)) {
				qry.setString(0, ltcBean.getFromDate());
				qry.setString(1, ltcBean.getToDate());
				
			}else if (CPSUtils.compareStrings(ltcBean.getType(), CPSConstants.LTCBLOCKYEAR)) {
				qry.setString(0, ltcBean.getFromDate());
				qry.setString(1, ltcBean.getLtcBlockId());
			}

			List<Object> list = qry.list();
			if (CPSUtils.checkList(list) && Integer.parseInt(list.get(0).toString()) > 0) {
				message = CPSConstants.DUPLICATE;
			} else {
				message = CPSConstants.SUCCESS;
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} 
		log.debug("checkLtcName() --> End");
		return message;
	}

	public String createLtcMasterData(Object obj) throws Exception {
		log.debug("createLtcMasterData() --> Start");
		String message = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();;
			session.save(obj);
			hibernateUtils.commitTransaction();
			message = CPSConstants.SUCCESS;
			log.debug("message -->" + message);
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		log.debug("createLtcMasterData() --> End");
		return message;
	}

	public String updateLtcMasterData(LtcApplicationBean ltcBean) throws Exception {
		log.debug("updateLtcMasterData() --> Start");
		String message = null;
		Session session = null;
		String sql = null;
		try {
			session = hibernateUtils.getSession();;
			if (CPSUtils.compareStrings(ltcBean.getType(), CPSConstants.LTCTYPE)) {
				sql = "update LtcTypeMasterDTO set name=?,lastModifiedDate=sysdate,description=? where id=?";
			} else if (CPSUtils.compareStrings(ltcBean.getType(), CPSConstants.LTCBLOCK)) {
				sql = "update LtcBlockMasterDTO set name=?,lastModifiedDate=sysdate,description=? where id=?";
			} else if (CPSUtils.compareStrings(ltcBean.getType(), CPSConstants.LTCBLOCKYEAR)) {
				sql = "update LtcBlockYearsMasterDTO set fromDate=?,toDate=?,extendedDate=?,ltcBlockId=?,lastModifiedDate=sysdate where id=?";
			} else if(CPSUtils.compareStrings(ltcBean.getType(), CPSConstants.LTCPENALINTERESTMASTER)){
				sql = "update LtcPenalMasterDTO set fromDate=?,toDate=?,typeValue=?,lastModifiedDate=sysdate,description=? where id=?";
			}
			log.debug("query -->" + sql);
			Query qry = session.createQuery(sql);
			if (CPSUtils.compareStrings(ltcBean.getType(), CPSConstants.LTCTYPE) || CPSUtils.compareStrings(ltcBean.getType(), CPSConstants.LTCBLOCK)) {
				qry.setString(0, ltcBean.getName());
				qry.setString(1, ltcBean.getDescription());
				qry.setString(2, ltcBean.getId());
			} else if (CPSUtils.compareStrings(ltcBean.getType(), CPSConstants.LTCBLOCKYEAR)) {
				qry.setString(0, ltcBean.getFromDate());
				qry.setString(1, ltcBean.getToDate());
				qry.setString(2, ltcBean.getExtendedDate());
				qry.setString(3, ltcBean.getLtcBlockId());
				qry.setString(4, ltcBean.getId());
			} else if(CPSUtils.compareStrings(ltcBean.getType(), CPSConstants.LTCPENALINTERESTMASTER)){
				qry.setString(0, ltcBean.getFromDate());
				qry.setString(1, ltcBean.getToDate());
				qry.setString(2, ltcBean.getTypeValue());
				qry.setString(3, ltcBean.getDescription());
				qry.setString(4,ltcBean.getId());
			}
			qry.executeUpdate();
			hibernateUtils.commitTransaction();
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		log.debug("updateLtcMasterData() --> End");
		return message;
	}

	public String deleteLtcMasterData(LtcApplicationBean ltcBean) throws Exception {
		log.debug("deleteLtcMasterData() --> Start");
		String message = null;
		Session session = null;
		String sql = null;
		try {
			session = hibernateUtils.getSession();;
			if (CPSUtils.compareStrings(ltcBean.getType(), CPSConstants.LTCTYPE)) {
				sql = "update LtcTypeMasterDTO set status=0,lastModifiedDate=sysdate where id=?";
			} else if (CPSUtils.compareStrings(ltcBean.getType(), CPSConstants.LTCBLOCK)) {
				sql = "update LtcBlockMasterDTO set status=0,lastModifiedDate=sysdate where id=?";
			} else if (CPSUtils.compareStrings(ltcBean.getType(), CPSConstants.LTCBLOCKYEAR)) {
				sql = "update LtcBlockYearsMasterDTO set status=0,lastModifiedDate=sysdate where id=?";
			} else if(CPSUtils.compareStrings(ltcBean.getType(), CPSConstants.LTCPENALINTERESTMASTER)){
				sql = "update LtcPenalMasterDTO set status=0,lastModifiedDate=sysdate where id=?";
			}
			log.debug("Query -->" + sql);
			Query qry = session.createQuery(sql);
			qry.setString(0, ltcBean.getId());
			qry.executeUpdate();
			message = CPSConstants.DELETE;

		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		hibernateUtils.commitTransaction();
		log.debug("deleteLtcMasterData() --> End");
		return message;

	}

	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> getBlockYearsList() throws Exception {
		List<KeyValueDTO> list = null;
		Session session = null;
		String query = null;
		try {
			query = "select id key,to_char(from_date,'yyyy')||'-'||to_char(to_date,'yy') as name from ltc_block_year_master where status=1 and ltc_block_id=(select id from ltc_block_master where ltc_block='Four Year') order by name";
			session = hibernateUtils.getSession();;
			list = session.createSQLQuery(query).addScalar("key", Hibernate.STRING).addScalar("name").setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
		} catch (Exception e) {
			throw e;
		} 
		return list;
	}

	public String getLtcBlockType(String type) throws Exception {
		Session session = null;
		String ltcBlockId = null;
		try {
			session = hibernateUtils.getSession();;
			if (CPSUtils.compareStrings(type, CPSConstants.ONE)) {
				ltcBlockId = session.createSQLQuery("select id from ltc_block_master where ltc_block='One Year' and status=1").uniqueResult().toString();
			} else if (CPSUtils.compareStrings(type, CPSConstants.TWO)) {
				ltcBlockId = session.createSQLQuery("select id from ltc_block_master where ltc_block='Two Year' and status=1").uniqueResult().toString();
			} else if (CPSUtils.compareStrings(type, CPSConstants.FOUR)) {
				ltcBlockId = session.createSQLQuery("select id from ltc_block_master where ltc_block='Four Year' and status=1").uniqueResult().toString();
			}
		} catch (Exception e) {
			throw e;
		} 
		return ltcBlockId;
	}

}
