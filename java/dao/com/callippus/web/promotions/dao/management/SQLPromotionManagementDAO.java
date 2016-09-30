package com.callippus.web.promotions.dao.management;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.CasualityDetailsDTO;
import com.callippus.web.beans.dto.CategoryDTO;
import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.DoPartDTO;
import com.callippus.web.beans.dto.DoPartSerialNoDTO;
import com.callippus.web.beans.dto.EmpBasicPayHistoryDTO;
import com.callippus.web.beans.dto.EmpPaymentsDTO;
import com.callippus.web.beans.dto.KeyDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.YearTypeDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.family.FamilyBean;
import com.callippus.web.beans.requests.AlertMessageDTO;
import com.callippus.web.beans.requests.DoPartBean;
import com.callippus.web.business.requestprocess.AlertMessages;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.promotions.beans.management.PromotionManagementBean;
import com.callippus.web.promotions.beans.management.PromotionOfflineEntryBean;
import com.callippus.web.promotions.dto.AssessmentCategoryDTO;
import com.callippus.web.promotions.dto.AssessmentDetailsDTO;
import com.callippus.web.promotions.dto.BoardMappingDTO;
import com.callippus.web.promotions.dto.BoardMasterDTO;
import com.callippus.web.promotions.dto.DesignationExperienceDTO;
import com.callippus.web.promotions.dto.ExceptionalEmpDTO;
import com.callippus.web.promotions.dto.ExternalEmpDTO;
import com.callippus.web.promotions.dto.OptionalCertificateDTO;
import com.callippus.web.promotions.dto.PayFixationDTO;
import com.callippus.web.promotions.dto.PromoteesEntryDTO;
import com.callippus.web.promotions.dto.PromotionsDisciplineDTO;
import com.callippus.web.promotions.dto.PromotionsSubDisciplineDTO;
import com.callippus.web.promotions.dto.ResidencyPeriodDTO;
import com.callippus.web.promotions.dto.VenueDetailsDTO;

@Service
public class SQLPromotionManagementDAO implements IPromotionManagementDAO {
	private static Log log = LogFactory.getLog(SQLPromotionManagementDAO.class);
	@Autowired
	private AlertMessages alertMessages;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	private Session session;

	@Override
	@SuppressWarnings("unchecked")
	public List<AssessmentCategoryDTO> getAssessmentCategoryList()
			throws Exception {
		List<AssessmentCategoryDTO> list = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			list = session.createCriteria(AssessmentCategoryDTO.class)
					.add(Expression.eq(CPSConstants.STATUS, 1))
					.addOrder(Order.asc("id")).list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CategoryDTO> getAssessmentTypeList() throws Exception {
		List<CategoryDTO> list = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			// list =
			// session.createCriteria(CategoryDTO.class).add(Expression.eq(CPSConstants.STATUS,
			// 1)).list();
			list = session
					.createSQLQuery(
							"Select Distinct Cm.Id Id,Cm.Name Name From Category_master Cm,Designation_mappings Dmap where dmap.category_id=cm.id order by cm.id")
							.addScalar("id", Hibernate.INTEGER)
							.addScalar("name", Hibernate.STRING)
							.setResultTransformer(
									Transformers.aliasToBean(CategoryDTO.class)).list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DesignationDTO> getDesignationList() throws Exception {
		List<DesignationDTO> list = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			list = session.createCriteria(DesignationDTO.class)
					.add(Expression.eq(CPSConstants.STATUS, 1))
					.addOrder(Order.asc("orderNo")).list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DesignationDTO> getLocalMembersDesignationList()
			throws Exception {
		List<DesignationDTO> list = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			list = session
					.createCriteria(DesignationDTO.class)
					.add(Expression.eq(CPSConstants.STATUS, 1))
					.add(Expression.in(CPSConstants.ID, new Integer[] { 52, 53,
							54, 55, 56, 57, 58 })).addOrder(Order.asc("id"))
							.list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ResidencyPeriodDTO> getResidencyPeriodList(
			PromotionManagementBean promotionBean) throws Exception {
		List<ResidencyPeriodDTO> list = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			session.clear();
			if (!(promotionBean.getAssessmentCategoryID() == 0)) {
				list = session
						.createCriteria(ResidencyPeriodDTO.class)
						.add(Expression.eq(CPSConstants.STATUS, 1))
						.add(Expression.eq(CPSConstants.ASSESSMENTTYPEID,
								promotionBean.getAssessmentTypeID()))
								.add(Expression.eq("assessmentCategoryID",
										promotionBean.getAssessmentCategoryID()))
										.addOrder(Order.desc("designationFrom")).list();
			} else {
				list = session
						.createCriteria(ResidencyPeriodDTO.class)
						.add(Expression.eq(CPSConstants.STATUS, 1))
						.add(Expression.eq(CPSConstants.ASSESSMENTTYPEID,
								promotionBean.getAssessmentTypeID()))
								.addOrder(Order.desc("designationFrom")).list();
			}
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public String checkDuplicate(PromotionManagementBean promotionBean)
			throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			Criteria crit = session
					.createCriteria(ResidencyPeriodDTO.class)
					.add(Expression.eq(CPSConstants.STATUS, 1))
					.add(Expression.eq(CPSConstants.ASSESSMENTTYPEID,promotionBean.getAssessmentTypeID()))
					.add(Expression.eq("assessmentCategoryID",promotionBean.getAssessmentCategoryID()))
					.add(Expression.eq(CPSConstants.DESIGNATIONFROM,
							promotionBean.getDesignationFrom()))
							.add(Expression.eq(CPSConstants.DESIGNATIONTO,
									promotionBean.getDesignationTo())).add(Expression.ne(CPSConstants.ASSESSMENTCATEGORYID,3));
			if (!CPSUtils.isNullOrEmpty(promotionBean.getNodeID())) {
				crit = crit.add(Expression.ne(CPSConstants.ID,
						Integer.parseInt(promotionBean.getNodeID())));
			}

			List<ResidencyPeriodDTO> list = crit.list();

			if (CPSUtils.checkList(list)) {
				promotionBean.setResult(CPSConstants.DUPLICATE);
			}
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}

	@Override
	public String submitResidencyPeriodDetails(
			ResidencyPeriodDTO residencyPeriodDTO) throws Exception {
		Session session = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();


			residencyPeriodDTO.setAssessmentTypeDetails((CategoryDTO) session
					.createCriteria(CategoryDTO.class)
					.add(Expression.eq("id",
							residencyPeriodDTO.getAssessmentTypeID()))
							.add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult());
			residencyPeriodDTO
			.setDesignationFromDetails((DesignationDTO) session
					.createCriteria(DesignationDTO.class)
					.add(Expression.eq("id",
							residencyPeriodDTO.getDesignationFrom()))
							.add(Expression.eq(CPSConstants.STATUS, 1))
							.uniqueResult());
			residencyPeriodDTO.setDesignationToDetails((DesignationDTO) session
					.createCriteria(DesignationDTO.class)
					.add(Expression.eq("id",
							residencyPeriodDTO.getDesignationTo()))
							.add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult());
			session.saveOrUpdate(residencyPeriodDTO);
			session.flush();

			message = CPSConstants.SUCCESS;

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	@Override
	public String deleteResidencyPeriodDetails(
			PromotionManagementBean promotionBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			ResidencyPeriodDTO residencyPeriodDTO = (ResidencyPeriodDTO) session
					.get(ResidencyPeriodDTO.class,
							Integer.valueOf(promotionBean.getNodeID()));
			residencyPeriodDTO.setStatus(0);
			residencyPeriodDTO.setLastModifiedBy(promotionBean.getSfID());
			residencyPeriodDTO.setLastModifiedTime(CPSUtils
					.getCurrentDateWithTime());
			if (CPSUtils.isNullOrEmpty(residencyPeriodDTO.getInterview())) {
				residencyPeriodDTO.setInterview(0);
			}
			if (CPSUtils.isNullOrEmpty(residencyPeriodDTO.getWritten())) {
				residencyPeriodDTO.setWritten(0);
			}
			if (CPSUtils.isNullOrEmpty(residencyPeriodDTO.getTrade())) {
				residencyPeriodDTO.setTrade(0);
			}
			if (CPSUtils.isNullOrEmpty(residencyPeriodDTO.getBoard())) {
				residencyPeriodDTO.setBoard(0);
			}
			session.saveOrUpdate(residencyPeriodDTO);
			session.flush();
			promotionBean.setResult(CPSConstants.DELETE);
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<VenueDetailsDTO> getVenueList(
			PromotionManagementBean promotionBean) throws Exception {
		List<VenueDetailsDTO> list = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			Criteria ctr = session.createCriteria(VenueDetailsDTO.class).add(
					Expression.eq(CPSConstants.STATUS, 1));
			if (!CPSUtils.compareStrings(CPSConstants.QUALIFIEDCANDIDATES,
					promotionBean.getType())) {
				if (promotionBean.getAssessmentCategoryID() != 0) {
					ctr.add(Expression.eq("assessmentCategoryID",
							promotionBean.getAssessmentCategoryID()));
				}
				if (promotionBean.getYearID() != 0) {
					ctr.add(Expression.eq("yearID", promotionBean.getYearID()));
				}
				if (promotionBean.getAssessmentTypeID() != 0) {
					ctr.add(Expression.eq("categoryId",
							promotionBean.getAssessmentTypeID()));
				}
			}
			list = ctr.list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public String checkDuplicateVenue(PromotionManagementBean promotionBean)
			throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			Criteria crit = session
					.createCriteria(VenueDetailsDTO.class)
					.add(Expression.eq(CPSConstants.STATUS, 1))
					.add(Expression.eq(CPSConstants.ASSESSMENTCATEGORYID,
							promotionBean.getAssessmentCategoryID()))
							.add(Expression.eq("categoryId",
									promotionBean.getAssessmentTypeID()))
									.add(Expression.eq(CPSConstants.YEARIDSTR,
											promotionBean.getYearID()))
											.add(Expression.eq(CPSConstants.CENTER,
													promotionBean.getCenter()));

			if (!CPSUtils.isNullOrEmpty(promotionBean.getNodeID())) {
				crit = crit.add(Expression.ne(CPSConstants.ID,
						Integer.parseInt(promotionBean.getNodeID())));
			}
			if (CPSUtils.checkList(crit.list())) {
				promotionBean.setResult(CPSConstants.DUPLICATE);
			}
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}

	@Override
	public String deleteVenueDetails(PromotionManagementBean promotionBean)
			throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			VenueDetailsDTO venueDetailsDTO = (VenueDetailsDTO) session.get(
					VenueDetailsDTO.class,
					Integer.valueOf(promotionBean.getNodeID()));
			venueDetailsDTO.setStatus(0);
			venueDetailsDTO.setLastModifiedBy(promotionBean.getSfID());
			venueDetailsDTO.setLastModifiedTime(CPSUtils
					.getCurrentDateWithTime());
			session.saveOrUpdate(venueDetailsDTO);
			session.flush();
			promotionBean.setResult(CPSConstants.DELETE);
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}

	@Override
	public String submitVenueDetails(VenueDetailsDTO venueDetails)
			throws Exception {
		Session session = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();

			// satya.
			// hibernateUtils.closeSession();
			// hibernateUtils.getSession();
			venueDetails.setYearDetails((YearTypeDTO) session
					.createCriteria(YearTypeDTO.class)
					.add(Expression.eq("id", venueDetails.getYearID()))
					.add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult());
			venueDetails
			.setAssessmentCategoryDetails((AssessmentCategoryDTO) session
					.createCriteria(AssessmentCategoryDTO.class)
					.add(Expression.eq("id",
							venueDetails.getAssessmentCategoryID()))
							.add(Expression.eq(CPSConstants.STATUS, 1))
							.uniqueResult());
			venueDetails.setCategoryName(((CategoryDTO) session
					.createCriteria(CategoryDTO.class)
					.add(Expression.eq("id", venueDetails.getCategoryId()))
					.add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult())
					.getName());
			session.saveOrUpdate(venueDetails);
			session.flush();

			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ExceptionalEmpDTO> getAddAssessmentEmpList(
			PromotionManagementBean promotionBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			promotionBean.setExceptionalEmp(session
					.createCriteria(ExceptionalEmpDTO.class)
					.add(Expression.eq(CPSConstants.STATUS, 1)).list());
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getExceptionalEmp();
	}

	@Override
	public String deleteExceptionEmpDetails(
			PromotionManagementBean promotionBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			ExceptionalEmpDTO expEmpDTO = (ExceptionalEmpDTO) session.get(
					ExceptionalEmpDTO.class,
					Integer.valueOf(promotionBean.getNodeID()));
			expEmpDTO.setStatus(0);
			expEmpDTO.setLastModifiedBy(promotionBean.getSfID());
			expEmpDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
			session.saveOrUpdate(expEmpDTO);
			session.flush();
			promotionBean.setResult(CPSConstants.DELETE);
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}

	@Override
	public String checkDuplicateExp(PromotionManagementBean promotionBean)
			throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			Criteria crit = session
					.createCriteria(ExceptionalEmpDTO.class)
					.add(Expression.eq(CPSConstants.STATUS, 1))
					.add(Expression.eq(CPSConstants.YEARIDSTR,
							promotionBean.getYearID()))
							.add(Expression.eq("sfID", promotionBean.getExpEmp()));

			if (!CPSUtils.isNullOrEmpty(promotionBean.getNodeID())) {
				crit = crit.add(Expression.ne(CPSConstants.ID,
						Integer.parseInt(promotionBean.getNodeID())));
			}
			if (CPSUtils.checkList(crit.list())) {
				promotionBean.setResult(CPSConstants.DUPLICATE);
			}
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}

	@Override
	public String submitExpEmpDetails(ExceptionalEmpDTO exceptionalEmpDTO)
			throws Exception {
		Session session = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();
			session.saveOrUpdate(exceptionalEmpDTO);

			session.flush();
			exceptionalEmpDTO.setYearDetails((YearTypeDTO) session
					.createCriteria(YearTypeDTO.class)
					.add(Expression.eq("id", exceptionalEmpDTO.getYearID()))
					.add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult());
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> getLocalBoardMembers(
			PromotionManagementBean promotionBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			promotionBean
			.setLocalBoardMembersList(session
					.createSQLQuery(
							"select designation_id id,sfid key,name_in_service_book value from emp_master where status=1 and designation_id in ("
									+ CPSConstants.LOCALBOARDMEMBERSDESIG
									+ ") order by name_in_service_book")
									.addScalar("id", Hibernate.INTEGER)
									.addScalar("key", Hibernate.STRING)
									.addScalar("value", Hibernate.STRING)
									.setResultTransformer(
											Transformers.aliasToBean(KeyValueDTO.class))
											.list());
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getLocalBoardMembersList();
	}

	@Override
	public String deleteLocalBoardDetails(PromotionManagementBean promotionBean)
			throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			session.createSQLQuery(
					"update pro_assessment_board_mapping set status=0,last_modified_by=?,last_modified_date=? where board_id=? and year_id=? and category_id=?")
					.setString(0, promotionBean.getSfID())
					.setDate(1, CPSUtils.getCurrentDateWithTime())
					.setInteger(2, promotionBean.getBoardID())
					.setInteger(3, promotionBean.getYearID())
					.setInteger(4, promotionBean.getAssessmentTypeID())
					.executeUpdate();

			promotionBean.setResult(CPSConstants.DELETE);
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}

	@Override
	public String checkDuplicateLocalBoard(PromotionManagementBean promotionBean)
			throws Exception {
		Session session = null;
		StringBuilder sb = new StringBuilder();
		try {
			session = hibernateUtils.getSession();
			sb.append("select count(*) from pro_assessment_board_mapping pabm,pro_board_name_master pbn where pbn.status=1 and pabm.status=1 and pabm.board_id=pbn.id and pabm.year_id=? and pbn.name=? ");
			if (!CPSUtils.isNullOrEmpty(promotionBean.getNodeID())) {
				sb.append(" and pabm.board_id!=" + promotionBean.getNodeID()
						+ " and pabm.year_id!=" + promotionBean.getEditYearID());
			}

			String count = session.createSQLQuery(sb.toString())
					.setInteger(0, promotionBean.getYearID())
					.setString(1, promotionBean.getBoardName()).uniqueResult()
					.toString();
			if (!CPSUtils.compareStrings(count, "0")) {
				promotionBean.setResult(CPSConstants.DUPLICATE);
			}
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}

	@Override
	public String submitLocalBoardDetails(PromotionManagementBean promotionBean)
			throws Exception {
		Session session = null;
		String message = null;
		BoardMappingDTO boardMappingDTO = null;
		Date currentDate = CPSUtils.getCurrentDateWithTime();
		try {
			session = hibernateUtils.getSession();

			session.createSQLQuery(
					"update pro_assessment_board_mapping set status=0,last_modified_by=?,last_modified_date=? where board_id=? and year_id=?")
					.setString(0, promotionBean.getSfID())
					.setDate(1, currentDate)
					.setInteger(2, promotionBean.getBoardID())
					.setInteger(3, promotionBean.getEditYearID())
					.executeUpdate();
			session.flush();
			// GATTU hibernateUtils.commitTransaction();
			// GATTU hibernateUtils.beginTransaction(session);
			session.flush();

			String[] members = promotionBean.getBoardMembers().split(",");
			for (String member : members) {

				boardMappingDTO = (BoardMappingDTO) session
						.createCriteria(BoardMappingDTO.class)
						.add(Expression.eq("boardID",
								promotionBean.getBoardID()))
								.add(Expression.eq("yearID", promotionBean.getYearID()))
								.add(Expression.eq("boardMember", member))
								.uniqueResult();
				if (CPSUtils.isNull(boardMappingDTO)) {
					boardMappingDTO = new BoardMappingDTO();
				}

				boardMappingDTO.setYearID(promotionBean.getYearID());
				boardMappingDTO.setBoardID(promotionBean.getBoardID());
				boardMappingDTO.setCategoryId(promotionBean
						.getAssessmentTypeID());
				boardMappingDTO.setBoardMember(member);
				boardMappingDTO.setStatus(1);
				boardMappingDTO.setCreatedBy(promotionBean.getSfID());
				boardMappingDTO.setCreationTime(currentDate);
				boardMappingDTO.setLastModifiedBy(promotionBean.getSfID());
				boardMappingDTO.setLastModifiedTime(currentDate);
				session.saveOrUpdate(boardMappingDTO);
			}
			session.flush();
			// session.flush();//tx.commit() ;
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			// //tx.rollback();
			throw e;
		}
		return message;
	}

	@Override
	public int submitBoardMasterDetails(BoardMasterDTO boardMasterDTO)
			throws Exception {
		Session session = null;
		int boardID = boardMasterDTO.getId();
		try {
			session = hibernateUtils.getSession();
			session.saveOrUpdate(boardMasterDTO);

			if (boardID == 0) {
				// New Record
				boardID = Integer.valueOf(session
						.createCriteria(BoardMasterDTO.class)
						.setProjection(
								Projections.projectionList().add(
										Projections.max("id"))).uniqueResult()
										.toString());
			}
		} catch (Exception e) {

			throw e;
		}
		return boardID;

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BoardMappingDTO> getLocalAssessmentBoardList(
			PromotionManagementBean promotionBean) throws Exception {
		Session session = null;
		StringBuilder sb = new StringBuilder();
		try {

			session = hibernateUtils.getSession();
			sb.append("select board_id boardID,year_id yearID,year,boardName,categoryId,(select rtrim (xmlagg (xmlelement (e, emp.name_in_service_book || ',')).extract ('//text()'), ',') from emp_master emp where emp.sfid in(select "
					+ "board_member from pro_assessment_board_mapping in1 where in1.board_id=tab.board_id and in1.year_id=tab.year_id and in1.status=1)) employees,(select rtrim (xmlagg (xmlelement (e, emp.sfid || ',')).extract ('//text()'), ',') from "
					+ "emp_master emp where emp.sfid in (select board_member from pro_assessment_board_mapping in1 where in1.board_id=tab.board_id and in1.year_id=tab.year_id and in1.status=1)) members from (select distinct abm.board_id,abm.year_id,y.name year,"
					+ "bnm.name boardName,abm.category_id categoryId from pro_assessment_board_mapping abm,pro_board_name_master bnm,year_master y where abm.status=1 and bnm.status=1 and bnm.id=abm.board_id and y.status=1 and y.id=abm.year_id ");
			if (promotionBean.getAssessmentTypeID() != 0) {
				sb.append("and category_id="
						+ promotionBean.getAssessmentTypeID() + "");
			}
			if (promotionBean.getYearID() != 0) {
				sb.append(" and abm.year_id=" + promotionBean.getYearID() + "");
			}

			sb.append(") tab");
			promotionBean.setLocalBoardMappingList(session
					.createSQLQuery(sb.toString())
					.addScalar("boardID", Hibernate.INTEGER)
					.addScalar("yearID", Hibernate.INTEGER)
					.addScalar("year", Hibernate.STRING)
					.addScalar("boardName", Hibernate.STRING)
					.addScalar("categoryId", Hibernate.INTEGER)
					.addScalar("employees", Hibernate.STRING)
					.addScalar("members", Hibernate.STRING)
					.setResultTransformer(
							Transformers.aliasToBean(BoardMappingDTO.class))
							.list());
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getLocalBoardMappingList();
	}

	// Desig Experience Details start
	@Override
	public String submitDesigExperienceDetails(
			DesignationExperienceDTO experienceDTO) throws Exception {
		Session session = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();
			session.saveOrUpdate(experienceDTO);
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DesignationExperienceDTO> getDesigExperienceList()
			throws Exception {
		Session session = null;
		List<DesignationExperienceDTO> list = null;
		try {
			session = hibernateUtils.getSession();
			/*
			 * list =
			 * session.createCriteria(DesignationExperienceDTO.class).add(
			 * Expression.eq(CPSConstants.STATUS, 1)).list();
			 */list = session
					 .createSQLQuery(
							 "Select  pde.id id,desig.id designationID,emp.sfid sfid,emp.name_in_service_book empName,desig.name name,emp.seniority_date startDate,pde.no_of_attempts noOfAttempts From Designation_master Desig,Emp_master Emp,Pro_designation_experience Pde where pde.sfid=emp.sfid and emp.designation_id=desig.id and  emp.status=1 and pde.status=1")
							 .addScalar("id", Hibernate.INTEGER)
							 .addScalar("designationID", Hibernate.INTEGER)
							 .addScalar("sfid", Hibernate.STRING)
							 .addScalar("empName", Hibernate.STRING)
							 .addScalar("name", Hibernate.STRING)
							 .addScalar("startDate", Hibernate.DATE)
							 .addScalar("noOfAttempts", Hibernate.INTEGER)
							 .setResultTransformer(
									 Transformers
									 .aliasToBean(DesignationExperienceDTO.class))
									 .list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public String checkDuplicateDesigExp(PromotionManagementBean promotionBean)
			throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			Criteria crit = session
					.createCriteria(DesignationExperienceDTO.class)
					.add(Expression.eq(CPSConstants.STATUS, 1))
					.add(Expression.eq(CPSConstants.SFID, promotionBean
							.getRefSfid().toUpperCase()));
			if (!CPSUtils.isNullOrEmpty(promotionBean.getNodeID())) {
				crit = crit.add(Expression.ne(CPSConstants.ID,
						Integer.parseInt(promotionBean.getNodeID())));
			}
			if (CPSUtils.checkList(crit.list())) {
				promotionBean.setResult(CPSConstants.DUPLICATE);
			}
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}

	@Override
	public String deleteDesigExperienceDetails(
			PromotionManagementBean promotionBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			DesignationExperienceDTO experienceDTO = (DesignationExperienceDTO) session
					.get(DesignationExperienceDTO.class,
							Integer.valueOf(promotionBean.getNodeID()));
			experienceDTO.setStatus(0);
			session.saveOrUpdate(experienceDTO);
			session.flush();
			promotionBean.setResult(CPSConstants.DELETE);

		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}

	@Override
	public PromotionManagementBean getDesigName(
			PromotionManagementBean promotionBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			promotionBean.setEmpDetails((EmployeeBean) session
					.createCriteria(EmployeeBean.class)
					.add(Expression.eq(CPSConstants.STATUS, 1))
					.add(Expression.eq("userSfid", promotionBean.getRefSfid()))
					.uniqueResult());
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	// Desig Experience Details end

	@Override
	@SuppressWarnings("unchecked")
	public PromotionManagementBean getQualifiedCandidates(
			PromotionManagementBean promotionBean) throws Exception {
		Session session = null;
		StringBuilder sb = new StringBuilder();
		try {
			session = hibernateUtils.getSession();
			sb.append("Select pad.id,pad.residency_Id residencyPeriodId,dv.department_id departmentID,desig.id designationID, desig2.name designationTo,to_char(emp.designation_id)  currentDesignation,PDM.NAME subDisciplineName,case when pdm.id is null then 0 else pdm.id end SubDisciplineID,PSDM.NAME disciplineName,case when PSDM.ID is null then 0 else PSDM.ID end disciplineID,Emp.Sfid sfID,Emp.Name_in_service_book empName,case when dv.tech_dir is null then dv.department_name else dv.tech_dir end department,Desig.Name designation,Pad.No_of_attempts attempts,Pad.Interview_date interviewDate,"
					+ "pad.lab_representative labRepresentative,Case When Pad.Venue_id Is Null Then '' Else (Select Center From Pro_venue_details Where Id=Pad.Venue_id) End Venue,"
					+ "case when pad.board_id is null then '' else (select name from pro_board_name_master where id=pad.board_id) end board From Emp_master Emp,directorate_view dv,Pro_assessment_details Pad,pro_residency_period_master prpm,designation_master desig2,"
					+ "Org_role_instance Ori,Departments_master Dept,designation_master desig,DESIGNATION_MAPPINGS DMAP,CATEGORY_MASTER CM,PRO_DISCIPLINE_MASTER PDM,PRO_SUB_DISCIPLINE_MASTER psdm Where Emp.Status=1 And Emp.Sfid=Pad.Sfid and emp.sfid=dv.sfid And Emp.Office_id=Ori.Org_role_id  And  pad.residency_id = prpm.id And prpm.designation_to = desig2.id  And "
					+ "Ori.Department_id=Dept.Department_id and pad.designation_id=desig.id AND DMAP.DESIG_ID=DESIG.ID AND DMAP.CATEGORY_ID=CM.ID and pdm.id(+)=psdm.discipline_id AND PSDM.ID(+)=PAD.DISCIPLINE_ID and pad.year_id=? and pad.assessment_category_id=? and cm.id=? ");
			if (!(promotionBean.getDesignationFrom() == 0)) {
				sb.append("and desig.id=" + promotionBean.getDesignationFrom());
			}
			sb.append("union Select case when pad.id is null then 0 else pad.id end id,residency_Id residencyPeriodId,dv.department_id departmentID,dm.id designationID,to_char(emp.designation_id)  currentDesignation,PDM.NAME subDisciplineName,desig2.name designationTo,case when pdm.id is null then 0 else pdm.id end SubDisciplineID,PSDM.NAME disciplineName,case when PSDM.ID is null then 0 else PSDM.ID end disciplineID,Emp.Sfid Sfid,Emp.Name_in_service_book Empname,case when dv.tech_dir is null then dv.department_name else dv.tech_dir end Department,Dm.Name Designation,case when pad.no_of_attempts is null then '' else pad.No_of_attempts end Attempts,Pad.Interview_date Interviewdate,Pad.Lab_representative Labrepresentative,Case When Pad.Venue_id Is Null Then '' Else (Select Center From Pro_venue_details Where Id=Pad.Venue_id) End Venue,case when pad.board_id is null then '' else (select name from pro_board_name_master where id=pad.board_id) end board From Pro_assessment_details Pad,Pro_exceptional_assessment_emp Peae,Emp_master Emp,Org_role_instance Ori,Departments_master Dept,Designation_master Dm,pro_residency_period_master prpm,DESIGNATION_MAPPINGS DMAP, designation_master desig2,CATEGORY_MASTER CM,PRO_DISCIPLINE_MASTER PDM,PRO_SUB_DISCIPLINE_MASTER psdm,directorate_view dv Where Pad.Sfid(+)=Peae.Sfid And Emp.Sfid=Peae.Sfid and emp.sfid=dv.sfid And Ori.Org_role_id=Emp.Office_id and ori.department_id=dept.department_id and dm.id=pad.designation_id and prpm.designation_from=dm.id and peae.status=1  And prpm.designation_to = desig2.id  AND DMAP.DESIG_ID=DM.ID AND DMAP.CATEGORY_ID=CM.ID and pdm.id(+)=psdm.discipline_id AND PSDM.ID(+)=PAD.DISCIPLINE_ID and peae.year_id=? and emp.sfid=peae.sfid and prpm.assessment_category_id=? and cm.id=? ");
			if (!(promotionBean.getDesignationFrom() == 0)) {
				sb.append("and dm.id=" + promotionBean.getDesignationFrom());
			}
			promotionBean.setAssessmentDetails(session
					.createSQLQuery(sb.toString())
					.addScalar("id", Hibernate.INTEGER)
					.addScalar("sfID", Hibernate.STRING)
					.addScalar("empName", Hibernate.STRING)
					.addScalar("subDisciplineName", Hibernate.STRING)
					.addScalar("subDisciplineID", Hibernate.INTEGER)
					.addScalar("disciplineName", Hibernate.STRING)
					.addScalar("disciplineID", Hibernate.INTEGER)
					.addScalar("residencyPeriodId", Hibernate.INTEGER)
					.addScalar("designation", Hibernate.STRING)
					.addScalar("department", Hibernate.STRING)
					.addScalar("interviewDate", Hibernate.DATE)
					.addScalar("venue", Hibernate.STRING)
					.addScalar("board", Hibernate.STRING)
					.addScalar("labRepresentative", Hibernate.STRING)
					.addScalar("attempts", Hibernate.STRING)
					.addScalar("departmentID", Hibernate.INTEGER)
					.addScalar("designationTo", Hibernate.STRING)
					.addScalar("currentDesignation", Hibernate.STRING)
					.addScalar("designationID", Hibernate.INTEGER)
					.setResultTransformer(
							Transformers
							.aliasToBean(AssessmentDetailsDTO.class))
							.setInteger(0, promotionBean.getYearID())
							.setInteger(1, promotionBean.getAssessmentCategoryID())
							.setInteger(2, promotionBean.getAssessmentTypeID())
							.setInteger(3, promotionBean.getYearID())
							.setInteger(4, promotionBean.getAssessmentCategoryID())
							.setInteger(5, promotionBean.getAssessmentTypeID()).list());

			List<Integer> residencyIdSet = new ArrayList<Integer>();
			for (int i = 0; i <= promotionBean.getAssessmentDetails().size() - 1; i++) {
				residencyIdSet.add((promotionBean.getAssessmentDetails().get(i)
						.getResidencyPeriodId()));
			}
			if (!residencyIdSet.isEmpty()) {
				promotionBean.setResidencyPeriodList((session.createCriteria(
						ResidencyPeriodDTO.class).add(
								Expression.in("id", residencyIdSet)).list()));
			}
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	@Override
	public PromotionManagementBean submitQualifiedCandidates(
			PromotionManagementBean promotionBean) throws Exception {

		Session session = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		// AssessmentDetailsDTO assessmentDetailsDTO = null;
		int assessmentID = 0;
		try {
			session = hibernateUtils.getSession();
			Date currentDate = CPSUtils.getCurrentDateWithTime();
			String[] rows = promotionBean.getRowValues().split(",");

			// Get assessment details max id
			assessmentID = Integer
					.valueOf(session
							.createSQLQuery(
									"select case when max(id) is null then 1 else max(id)+1 end from pro_assessment_details")
									.uniqueResult().toString());

			for (String row : rows) {
				// SFID#intwDate#VenueID#BoardID(or)labRepresentative#Attempts#designationID#departmentID#effectiveDate#variableIncr#reservationID#status
				AlertMessageDTO alertMessageDTO = new AlertMessageDTO();
				String[] rvalues = row.split("#");
				AssessmentDetailsDTO assessmentDetailsDTO = (AssessmentDetailsDTO) session
						.createCriteria(AssessmentDetailsDTO.class)
						.add(Expression.eq(CPSConstants.YEARIDSTR,
								promotionBean.getYearID()))
								.add(Expression.eq(CPSConstants.ASSESSMENTCATEGORYID,
										promotionBean.getAssessmentCategoryID()))
										.add(Expression.eq("sfID", rvalues[0])).uniqueResult();

				if (CPSUtils.compareStrings(promotionBean.getType(),
						CPSConstants.QUALIFIEDCANDIDATES)) {
					if (!CPSUtils.isNull(assessmentDetailsDTO)) {
						assessmentDetailsDTO.setStatus(Integer
								.valueOf(rvalues[11]));
						if (!CPSUtils.isNull(assessmentDetailsDTO
								.getInterviewDate())) {
							// already exists compare dates
							if (!CPSUtils.compareStrings(sdf
									.format(assessmentDetailsDTO
											.getInterviewDate()), rvalues[1])
											|| !CPSUtils.compareStrings(String
													.valueOf(assessmentDetailsDTO
															.getVenueID()), rvalues[2])) {
								alertMessageDTO
								.setAlertMessage(CPSConstants.PROMOTIONALERT
										.replace("%2%", rvalues[1]));
								alertMessageDTO.setAssignedFrom(promotionBean
										.getSfID());
								alertMessageDTO.setAssignedDate(currentDate);
								alertMessageDTO
								.setAssignedIpAddress(promotionBean
										.getIpAddress());
								alertMessageDTO.setAssignedTo(rvalues[0]);
								alertMessageDTO
								.setAlertID(Integer
										.valueOf(CPSConstants.PROMOTIONALERTID));
								alertMessageDTO.setStatus(Integer
										.valueOf(CPSConstants.STATUSPENDING));
								alertMessageDTO
								.setReferenceID(assessmentDetailsDTO
										.getId());
								alertMessageDTO.setVenueID(Integer
										.valueOf(rvalues[2]));
								alertMessageDTO
								.setLabRepresentative(rvalues[4]);
								assessmentDetailsDTO.setStatus(Integer
										.valueOf(rvalues[11]));

								alertMessages
								.checkAndUpdateAlert(alertMessageDTO);
							}
						} else {
							// already exists but date null, new alert
							alertMessageDTO
							.setAlertMessage(CPSConstants.PROMOTIONALERT
									.replace("%2%", rvalues[1]));
							alertMessageDTO.setAssignedFrom(promotionBean
									.getSfID());
							alertMessageDTO.setAssignedDate(currentDate);
							alertMessageDTO.setAssignedIpAddress(promotionBean
									.getIpAddress());
							alertMessageDTO.setAssignedTo(rvalues[0]);
							alertMessageDTO.setAlertID(Integer
									.valueOf(CPSConstants.PROMOTIONALERTID));
							alertMessageDTO.setStatus(Integer
									.valueOf(CPSConstants.STATUSPENDING));
							alertMessageDTO.setReferenceID(assessmentDetailsDTO
									.getId());
							if(!CPSUtils.isNullOrEmpty(rvalues[2]) && !(rvalues[2].toString().equals("null"))){
								alertMessageDTO.setVenueID(Integer
										.valueOf(rvalues[2]));
							}else{
								alertMessageDTO.setVenueID(0);
							}
							alertMessageDTO.setLabRepresentative(rvalues[4]);
							alertMessages.checkAndUpdateAlert(alertMessageDTO);
						}
					} else {
						// alert for exceptional employees
						alertMessageDTO
						.setAlertMessage(CPSConstants.PROMOTIONALERT
								.replace("%2%", rvalues[1]));
						alertMessageDTO
						.setAssignedFrom(promotionBean.getSfID());
						alertMessageDTO.setAssignedDate(currentDate);
						alertMessageDTO.setAssignedIpAddress(promotionBean
								.getIpAddress());
						alertMessageDTO.setAssignedTo(rvalues[0]);
						alertMessageDTO.setAlertID(Integer
								.valueOf(CPSConstants.PROMOTIONALERTID));
						alertMessageDTO.setStatus(Integer
								.valueOf(CPSConstants.STATUSPENDING));
						alertMessageDTO.setVenueID(Integer.valueOf(rvalues[2]));
						alertMessageDTO.setReferenceID(assessmentID);
						alertMessageDTO.setLabRepresentative(rvalues[4]);
						alertMessages.checkAndUpdateAlert(alertMessageDTO);
					}
				}
				if (CPSUtils.isNull(assessmentDetailsDTO)) {
					// New Record
					assessmentDetailsDTO = new AssessmentDetailsDTO();
					assessmentDetailsDTO.setSfID(rvalues[0]);
					assessmentDetailsDTO.setAssessmentCategoryID(promotionBean
							.getAssessmentCategoryID());
					assessmentDetailsDTO.setYearID(promotionBean.getYearID());
					assessmentDetailsDTO.setDesignationID(Integer
							.valueOf(rvalues[6]));
					assessmentDetailsDTO.setDepartmentID(Integer
							.valueOf(rvalues[7]));
					assessmentDetailsDTO
					.setAttempts(String.valueOf(rvalues[5]));
					assessmentDetailsDTO.setCreatedBy(promotionBean.getSfID());
					assessmentDetailsDTO.setCreationTime(currentDate);
					assessmentDetailsDTO.setStatus(Integer.valueOf(rvalues[11]));
					assessmentDetailsDTO.setVenueID(0);
					if (!CPSUtils.isNullOrEmpty(rvalues[18])) {
						assessmentDetailsDTO.setDisciplineID(Integer
								.parseInt(rvalues[18]));
					}
					if (!CPSUtils.isNullOrEmpty(rvalues[19])) {
						assessmentDetailsDTO.setSubDisciplineID(Integer
								.parseInt(rvalues[19]));
					}
					alertMessageDTO.setReferenceID(assessmentID);
					assessmentID++;
				}
				if (!CPSUtils.isNullOrEmpty(rvalues[1])) {
					assessmentDetailsDTO.setInterviewDate(CPSUtils
							.convertStringToDate(rvalues[1]));
				}
				if(!CPSUtils.isNullOrEmpty(rvalues[2])) {
					if ((!rvalues[2].equals("null"))){	
						assessmentDetailsDTO
						.setVenueID(Integer.valueOf(rvalues[2]));
					}}
				if (!CPSUtils.isNullOrEmpty(rvalues[5])) {
					assessmentDetailsDTO
					.setAttempts(String.valueOf(rvalues[5]));
				}
				if (!CPSUtils.isNullOrEmpty(rvalues[4])) {
					if (CPSUtils.isInteger(rvalues[4])) {
						assessmentDetailsDTO.setBoardID(Integer
								.valueOf(rvalues[4]));
					} else {
						assessmentDetailsDTO.setLabRepresentative(rvalues[4]);
					}
				}
				if (!CPSUtils.isNullOrEmpty(rvalues[8])) {
					assessmentDetailsDTO.setEffectiveDate(CPSUtils
							.convertStringToDate(rvalues[8]));
				}
				if (!CPSUtils.isNullOrEmpty(rvalues[16])) {
					assessmentDetailsDTO.setEndingDate(CPSUtils
							.convertStringToDate(rvalues[16]));
				}
				if (!CPSUtils.isNullOrEmpty(rvalues[9])) {
					assessmentDetailsDTO.setVariableIncr(Integer
							.parseInt(rvalues[9]));
				}
				if (!CPSUtils.isNullOrEmpty(rvalues[10])) {
					assessmentDetailsDTO.setReservationID(Integer
							.valueOf(rvalues[10]));
				}
				if (CPSUtils.compareStrings(promotionBean.getType(),
						CPSConstants.PROMOTEDCANDIDATES)) {
					assessmentDetailsDTO
					.setStatus(Integer.valueOf(rvalues[11]));
				}
				if (!CPSUtils.isNullOrEmpty(rvalues[12])) {
					assessmentDetailsDTO.setPromotionDate(CPSUtils
							.convertStringToDate(rvalues[12]));
				}
				if (!CPSUtils.isNullOrEmpty(rvalues[18])) {
					assessmentDetailsDTO.setDisciplineID(Integer
							.parseInt(rvalues[18]));
				}
				if (!CPSUtils.isNullOrEmpty(rvalues[19])) {
					assessmentDetailsDTO.setSubDisciplineID(Integer
							.parseInt(rvalues[19]));
				}
				if ((rvalues.length - 1) >= 24) {
					if (!CPSUtils.isNullOrEmpty(rvalues[24])) {
						assessmentDetailsDTO.setWrittenDate((rvalues[24]
								.toString()));
					}
				}
				if ((rvalues.length - 1) >= 25) {
					if (!CPSUtils.isNullOrEmpty(rvalues[25])) {
						assessmentDetailsDTO.setTradeDate((rvalues[25]
								.toString()));
					}
				}
				if ((rvalues.length - 1) >= 27) {
					if (!CPSUtils.isNullOrEmpty(rvalues[27])) {
						assessmentDetailsDTO.setResidencyPeriodId(Integer
								.parseInt(rvalues[27]));
						System.out.println("residency period is --"
								+ Integer.parseInt(rvalues[27]));
					}
				}

				// assessmentDetailsDTO.setStatus(Integer.valueOf(rvalues[11]));
				// assessmentDetailsDTO.setAttempts(String.valueOf(rvalues[5]));
				assessmentDetailsDTO.setLastModifiedBy(promotionBean.getSfID());
				assessmentDetailsDTO.setLastModifiedTime(currentDate);
				// if (CPSUtils.compareStrings(promotionBean.getType(),
				// CPSConstants.QUALIFIEDCANDIDATES)) {
				// assessmentDetailsDTO.setSubDisciplineID(Integer.valueOf(rvalues[15]));
				// }
				session.saveOrUpdate(assessmentDetailsDTO);
				session.flush();
			}
			// hibernateUtils.commitTransaction();
			session.flush();
			promotionBean.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BoardMasterDTO> getBoardMasterList(
			PromotionManagementBean promotionBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			promotionBean
			.setBoardMasterList(session
					.createSQLQuery(
							"select distinct abm.year_id yearID,bnm.id,bnm.name from pro_board_name_master bnm,pro_assessment_board_mapping abm where bnm.status=1 and abm.status=1 and bnm.id=abm.board_id")
							.addScalar("id", Hibernate.INTEGER)
							.addScalar("yearID", Hibernate.INTEGER)
							.addScalar("name", Hibernate.STRING)
							.setResultTransformer(
									Transformers
									.aliasToBean(BoardMappingDTO.class))
									.list());
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getBoardMasterList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public PromotionManagementBean getPromotedCandidates(
			PromotionManagementBean promotionBean) throws Exception {
		Session session = null;
		StringBuilder sb = new StringBuilder();
		try {
			session = hibernateUtils.getSession();
			sb.append("select pad.id,emp.sfid sfID,emp.name_in_service_book empName,case when pad.status=?  then 'Promoted' when pad.status=?  then 'Deferred' when pad.status=?  then 'Not Attended' when pad.status=?  then 'Qualified' else 'Accepted' end promotionStatus,pad.effective_date effectiveDate,pad.promotion_date promotionDate,(SELECT dm.name FROM pro_residency_period_master prpd,"
					+"designation_master dm  WHERE pad.residency_id  = prpd.id AND prpd.designation_to = dm.id) as designationTo,pad.variable_incr variableIncr,pad.ending_date endingDate,(select name from reservation_master where id=pad.reservation_id) reservation,"
					+ "desig.name designation,desig2.name currentDesignation,dept.department_name department from pro_assessment_details pad,emp_master emp,designation_master desig, designation_master desig2,org_role_instance ori,departments_master dept,DESIGNATION_MAPPINGS DMAP,CATEGORY_MASTER CM "
					+ "where pad.sfid=emp.sfid and pad.assessment_category_id=? and pad.year_id=? and pad.status in(?,?,?) and desig.status=1 and pad.designation_id=desig.id and ori.status=1 And emp.designation_id  = desig2.id and dept.status=1 and ori.org_role_id=emp.office_id and ori.department_id=dept.department_id and desig.id=DMAP.DESIG_ID and DMAP.CATEGORY_ID=cm.id and cm.id=? ");
			if (!(promotionBean.getDesignationFrom() == 0)) {
				sb.append("and desig.id=" + promotionBean.getDesignationFrom());
			}
			sb.append("order by pad.status DESC,pad.promotion_date desc,desig.order_no,emp.sfid");
			promotionBean.setAssessmentDetails(session
					.createSQLQuery(sb.toString())
					.addScalar("id", Hibernate.INTEGER)
					.addScalar("sfID", Hibernate.STRING)
					.addScalar("empName", Hibernate.STRING)
					.addScalar("promotionStatus", Hibernate.STRING)
					.addScalar("effectiveDate", Hibernate.DATE)
					.addScalar("designationTo", Hibernate.STRING)
					.addScalar("variableIncr", Hibernate.INTEGER)
					.addScalar("currentDesignation", Hibernate.STRING)
					.addScalar("endingDate", Hibernate.DATE)
					.addScalar("reservation", Hibernate.STRING)
					.addScalar("designation", Hibernate.STRING)
					.addScalar("department", Hibernate.STRING)
					.addScalar("promotionDate", Hibernate.DATE)
					.setResultTransformer(
							Transformers
							.aliasToBean(AssessmentDetailsDTO.class))
							.setString(0, CPSConstants.STATUSPROMOTED)
							.setString(1, CPSConstants.STATUSDEFERRED)
							.setString(2, CPSConstants.STATUSREJECTED)
							.setString(3, CPSConstants.STATUSQUALIFIED)
							.setInteger(4, promotionBean.getAssessmentCategoryID())
							.setInteger(5, promotionBean.getYearID())
							.setString(6, CPSConstants.STATUSQUALIFIED)
							.setString(7, CPSConstants.STATUSPROMOTED)
							.setString(8, CPSConstants.STATUSDEFERRED)
							.setInteger(9, promotionBean.getAssessmentTypeID()).list());
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public PromotionManagementBean getPayFixationDetails(
			PromotionManagementBean promotionBean) throws Exception {
		Session session = null;
		StringBuilder sb = new StringBuilder();
		// String[] rvalues = promotionBean.getDoPartNumber().split("#");
		// String id=rvalues[0];
		// String doDate=rvalues[1];
		try {
			session = hibernateUtils.getSession();
			session.clear();                                                                                                      // Variable increment value added 
			sb.append("Select ppf.INCREMENTS_ACCEPTED_DATE incrementsAccepteddate, Nvl(ppf.fixation_dopart_id,0) fixationDoPartId, poc.status  optionStatus,poc.increment_date incrementDate,pad.ending_date endingDate,NVL(ppf.increments_dopart_id,0) incrementsDoPartId,ppf.VAR_INC_END_DATE varIncEndDate,ppf.FIXATION_ACCEPTED_DATE fixationAcceptedDate,to_number(pad.variable_incr)  variableIncr,ppf.variable_increments payFixVariableIncr,Pad.Status,Pad.Id Assessmentid,emp.designation_id designationID,Emp.Sfid Sfid,Emp.Name_in_service_book Empname,EMP.SENIORITY_DATE seniorityDate,(SELECT ID FROM DESIGNATION_MASTER WHERE ID=PRPM.DESIGNATION_TO) toDesignation,(Desig.Name||'-'||(SELECT Name FROM DESIGNATION_MASTER WHERE ID=PRPM.DESIGNATION_TO)) Designation,Dept.Department_name Department,case when ppf.id is null then 0 else ppf.id end id,pad.promotion_date Promotiondate,Pad.Effective_date Effectivedate,(SELECT nvl(Basic_pay,0) FROM emp_payment_details WHERE sfid=emp.sfid) basicpay "
					+ ",Ppf.New_pay Newbasicpay,(SELECT nvl(Grade_pay,0) FROM emp_payment_details  WHERE sfid=emp.sfid)gradePay, ppf.variable_incr_value varIncrValue,ppf.new_grade_pay newGradePay,Case When Ppf.Two_add_incr Is Null Then "
					+ "(Select Two_addl_incr From Emp_payment_details Where Sfid=Emp.Sfid)||'' Else Ppf.Two_add_incr End  Twoaddincr,Ppf.New_two_add_incr Newtwoaddincr,dtdm.type_id gazettedId From Pro_assessment_details Pad, pro_optional_certificate poc,Emp_master Emp,Designation_master Desig,designation_mappings dmap,category_master cm,Org_role_instance Ori,Departments_master Dept,Pro_pay_fixation Ppf,PRO_RESIDENCY_PERIOD_MASTER PRPM ,dopart_ii_type_master dtm,dopart_ii_type_desig_master dtdm Where  Pad.Sfid=Emp.Sfid And Pad.Assessment_category_id=? ");
			/*if (promotionBean.getYearID() != 0) {                                                                                                                       //gazettedType variable added
					+ "(Select Two_addl_incr From Emp_payment_details Where Sfid=Emp.Sfid)||'' Else Ppf.Two_add_incr End  Twoaddincr,Ppf.New_two_add_incr Newtwoaddincr From Pro_assessment_details Pad, pro_optional_certificate poc,Emp_master Emp,Designation_master Desig,designation_mappings dmap,category_master cm,Org_role_instance Ori,Departments_master Dept,Pro_pay_fixation Ppf,PRO_RESIDENCY_PERIOD_MASTER PRPM ,dopart_ii_type_master dtm,dopart_ii_type_desig_master dtdm Where  Pad.Sfid=Emp.Sfid And Pad.Assessment_category_id=? AND prpm.assessment_category_id =? ");
			 */		
			if (promotionBean.getYearID() != 0) {
				sb.append("And ((Pad.Year_id="
						+ promotionBean.getYearID()
						+ " And  Pad.Status in (53,55)) Or (To_char(Pad.Promotion_date,'yyyy')=(select name from year_master where id="
						+ promotionBean.getYearID() + ") and Pad.Status=55)) ");
			}

			if (!CPSUtils.isNullOrEmpty(promotionBean.getGazettedType())) {
				sb.append(" and dtdm.type_id="+ promotionBean.getGazettedType() + "");
			}
			if (promotionBean.getAssessmentTypeID() != 0) {
				sb.append(" and cm.id=" + promotionBean.getAssessmentTypeID()
						+ "");
				if (!CPSUtils.isNullOrEmpty(promotionBean.getCasualityId())) {
					if (promotionBean.getCasualityId().contains("#")) {
						if (promotionBean.getCasualityId().split("#")[1]
								.equals("2")) {
							sb.append(" and ppf.fixation_accepted_date_finance is null ");
						} else if (promotionBean.getCasualityId().split("#")[1]
								.equals("3")) {
							sb.append(" and ppf.increments_accepted_date is null ");
						}
					}
				}
			}
			sb.append(" and Desig.Status=1 And pad.Designation_id=Desig.Id And Ori.Status=1 AND pad.residency_id = prpm.id And  Dept.Status=1 And Ori.Org_role_id=Emp.Office_id And Ori.Department_id=Dept.Department_id And Pad.Id=Ppf.Assessment_id(+) and Pad.Promotion_date Is Not Null and PRPM.DESIGNATION_FROM=DESIG.ID   And prpm.status=1  AND prpm.designation_to   = emp.designation_id and dtm.id=dtdm.type_id and emp.designation_id=dtdm.designation_id and dtdm.status=1 and dtm.status=1 and poc.assessment_id=pad.id and desig.id=dmap.desig_id and cm.id=dmap.category_id order by ppf.INCREMENTS_ACCEPTED_DATE desc,optionStatus desc,ppf.FIXATION_ACCEPTED_DATE desc,ppf.grade_pay desc");
			promotionBean.setAssessmentDetails(session
					.createSQLQuery(sb.toString())
					.addScalar("incrementsAccepteddate", Hibernate.DATE)
					.addScalar("incrementsDoPartId", Hibernate.INTEGER)
					.addScalar("fixationDoPartId", Hibernate.INTEGER)
					.addScalar("endingDate", Hibernate.DATE)
					.addScalar("varIncEndDate", Hibernate.DATE)
					.addScalar("optionStatus", Hibernate.INTEGER)
					.addScalar("incrementDate", Hibernate.DATE)
					.addScalar("variableIncr", Hibernate.INTEGER)
					.addScalar("payFixVariableIncr")
					.addScalar("fixationAcceptedDate", Hibernate.DATE)
					.addScalar("assessmentID", Hibernate.INTEGER)
					.addScalar("id", Hibernate.INTEGER)
					.addScalar("sfID", Hibernate.STRING)
					.addScalar("designationID", Hibernate.INTEGER)
					.addScalar("empName", Hibernate.STRING)
					.addScalar("effectiveDate", Hibernate.DATE)
					.addScalar("promotionDate", Hibernate.DATE)
					.addScalar("designation", Hibernate.STRING)
					.addScalar("department", Hibernate.STRING)
					.addScalar("toDesignation", Hibernate.INTEGER)
					.addScalar("basicPay", Hibernate.STRING)
					.addScalar("gradePay", Hibernate.STRING)
					.addScalar("varIncrValue", Hibernate.INTEGER)                               //Here i added Variable Increment value
					.addScalar("newBasicPay", Hibernate.STRING)
					.addScalar("newGradePay", Hibernate.STRING)
					.addScalar("twoAddIncr", Hibernate.STRING)
					.addScalar("newTwoAddIncr", Hibernate.STRING)
					.addScalar("gazettedId", Hibernate.INTEGER)                                //here added variable gazettedType
					.setResultTransformer(
							Transformers
							.aliasToBean(AssessmentDetailsDTO.class))
							.setInteger(0, promotionBean.getAssessmentCategoryID())                         //.setInteger(1, promotionBean.getAssessmentCategoryID()
							.list());

			promotionBean.setAssessmentDetails1(promotionBean
					.getAssessmentDetails());

			List<AssessmentDetailsDTO> payFixationList = promotionBean
					.getAssessmentDetails();
			List<AssessmentDetailsDTO> payFixationFinalList = new ArrayList<AssessmentDetailsDTO>();
			List<AssessmentDetailsDTO> VariableIncrementList = new ArrayList<AssessmentDetailsDTO>();
			List<Integer> gazettedList = new ArrayList<Integer>();

			if (!payFixationList.isEmpty()) {
				for (int i = 0; i <= payFixationList.size() - 1; i++) {
					AssessmentDetailsDTO assessmentDto = payFixationList.get(i);
					if (assessmentDto.getFixationDoPartId() == 0) {
						payFixationFinalList.add(assessmentDto);
					}
				}
				promotionBean.setAssessmentDetails(payFixationFinalList);

				for (int j = 0; j <= payFixationList.size() - 1; j++) {
					AssessmentDetailsDTO assessDto1 = payFixationList.get(j);
					if (assessDto1.getIncrementsDoPartId() == 0  )   {
						VariableIncrementList.add(assessDto1);
					}
				}
				promotionBean.setAssessmentDetails1(VariableIncrementList);
				promotionBean.setGazttedTypeList(gazettedList);

			}
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public PromotionManagementBean submitPayFixationDetails(
			PromotionManagementBean promotionBean) throws Exception {
		Session session = null;
		Date currentDate = CPSUtils.getCurrentDateWithTime();
		int offlineReferenceId = 0;
		Integer basicPayId = 0;
		Integer gradePayid = 0;
		AssessmentDetailsDTO Assementdto  = null;
		PromotionOfflineEntryBean entryBean = null;
		try {
			String[] casualityId = promotionBean.getCasualityId().split("#");
			promotionBean.setCasualityId(casualityId[0]);
			session = hibernateUtils.getSession();
			String[] rows = promotionBean.getRowValues().split(",");
			if (casualityId[1].equals("2")) {
				for (String row : rows) {
					String[] rvalues = row.split("#");

					// promotionBean.setOfflineReferenceId(offlineReferenceId);

					// submitPayFixationOfflineDetails(promotionBean);
					PromoteesEntryDTO entryDTO = (PromoteesEntryDTO) session
							.createCriteria(PromoteesEntryDTO.class)
							.add(Expression.eq("sfID", rvalues[2]))
							.add(Expression.eq(CPSConstants.STATUS, 1))
							.add(Expression.eq("promotedDesignation",
									Integer.parseInt(rvalues[7])))
									.uniqueResult();

					// for(int i=0;i<=)

					if (CPSUtils.isNull(entryDTO)) {
						entryDTO = new PromoteesEntryDTO();
						entryDTO.setSfID(rvalues[2].toUpperCase());

						/*
						 * entryBean=(PromotionOfflineEntryBean)session.
						 * createSQLQuery(
						 * "SELECT pad.variable_incr varIncPt,pad.effective_date varIncStartDate,pad.ending_date varIncEndDate,EMP.DESIGNATION_ID designationID,EMP.SENIORITY_DATE seniorityDate,RPM.DESIGNATION_TO designationTo,PAY.BASIC_PAY basicPay,pay.GRADE_PAY gradePay "
						 * +
						 * "FROM PRO_RESIDENCY_PERIOD_MASTER RPM,EMP_MASTER EMP,EMP_PAYMENT_DETAILS PAY,PRO_ASSESSMENT_DETAILS PAD where EMP.DESIGNATION_ID=RPM.DESIGNATION_FROM AND EMP.SFID=PAY.SFID AND EMP.SFID=PAD.SFID and EMP.STATUS=1 and rpm.status=1 and EMP.sfid=?"
						 * ) .addScalar("varIncPt",
						 * Hibernate.INTEGER).addScalar("varIncStartDate",
						 * Hibernate.DATE).addScalar("varIncEndDate",
						 * Hibernate.DATE).addScalar("designationID",
						 * Hibernate.INTEGER).addScalar("seniorityDate",
						 * Hibernate.DATE).addScalar("designationTo",
						 * Hibernate.INTEGER).addScalar("basicPay",
						 * Hibernate.FLOAT).addScalar("gradePay",
						 * Hibernate.FLOAT
						 * ).setString(0,rvalues[2]).setResultTransformer
						 * (Transformers
						 * .aliasToBean(PromotionOfflineEntryBean.class
						 * )).uniqueResult();
						 */
						entryBean = (PromotionOfflineEntryBean) session
								.createSQLQuery(
										"SELECT EMP.DESIGNATION_ID designationID,EMP.SENIORITY_DATE seniorityDate,RPM.DESIGNATION_TO designationTo,PAY.BASIC_PAY basicPay,PAY.GRADE_PAY gradePay,"
												+ " PAD.EFFECTIVE_DATE varIncStartDate,pad.promotion_date effectiveDate, pad.variable_incr varIncPt,pad.ending_date varIncEndDate"
												+ " FROM PRO_RESIDENCY_PERIOD_MASTER RPM,EMP_MASTER EMP,EMP_PAYMENT_DETAILS PAY,"
												+ " PRO_ASSESSMENT_DETAILS PAD where EMP.DESIGNATION_ID=RPM.DESIGNATION_TO AND EMP.SFID=PAY.SFID"
												+ " AND EMP.SFID=PAD.SFID and EMP.STATUS=1 and rpm.status=1 and  EMP.sfid=? AND PAD.STATUS IN(53,55) AND pad.residency_id = rpm.id")
												.addScalar("designationID", Hibernate.INTEGER)
												.addScalar("seniorityDate", Hibernate.DATE)
												.addScalar("designationTo", Hibernate.INTEGER)
												.addScalar("basicPay", Hibernate.FLOAT)
												.addScalar("gradePay", Hibernate.FLOAT)
												.addScalar("varIncStartDate", Hibernate.DATE)
												.addScalar("effectiveDate", Hibernate.DATE)
												.addScalar("varIncPt", Hibernate.INTEGER)
												.addScalar("varIncEndDate", Hibernate.DATE)
												.setString(0, rvalues[2])
												.setResultTransformer(
														Transformers
														.aliasToBean(PromotionOfflineEntryBean.class))
														.uniqueResult();

						if (!CPSUtils.isNull(entryBean)) {
							if (!CPSUtils.isNullOrEmpty(rvalues[5]))
								entryDTO.setTwoAddl(rvalues[5]);
							entryDTO.setPay(entryBean.getBasicPay());
							// entryDTO.setPresentDesignation(entryBean.getDesignationID());
							if (!CPSUtils.isNullOrEmpty(rvalues[8]))
								entryDTO.setPresentEffectiveDate(CPSUtils
										.critFormattedDate(rvalues[8]));
							// entryDTO.setPresentEffectiveDate(entryBean.getSeniorityDate());
							entryDTO.setGradePay(entryBean.getGradePay());
							entryDTO.setPromotedEffectiveDate(new SimpleDateFormat(
									"dd-MMM-yyyy").parse(new SimpleDateFormat(
											"dd-MMM-yyyy").format(entryBean
													.getEffectiveDate())));
							entryDTO.setVarIncEndDate(entryBean
									.getVarIncEndDate());
							entryDTO.setPromotedDesignation(entryBean
									.getDesignationTo());
							entryDTO.setCreationTime(CPSUtils
									.getCurrentDateWithTime());
							entryDTO.setCreatedBy(promotionBean.getSfID());
							entryDTO.setLastModifiedTime(CPSUtils
									.getCurrentDateWithTime());
							entryDTO.setLastModifiedBy(promotionBean.getSfID());
							entryDTO.setSeniorityDate(CPSUtils
									.formatDate(entryBean.getSeniorityDate()));
												
							entryDTO.setReferenceType("Online");

							entryDTO.setStatus(1);
							if (!CPSUtils.isNullOrEmpty(rvalues[3])) {
								entryDTO.setNewPay(Float.valueOf(rvalues[3]));
							}
							if (!CPSUtils.isNullOrEmpty(rvalues[4])) {
								entryDTO.setNewGradePay(Float.valueOf(rvalues[4]));
							}
							if (!CPSUtils.isNullOrEmpty(rvalues[5])) {
								entryDTO.setTwoAddl(rvalues[5]);
							}
							if (!CPSUtils.isNullOrEmpty(rvalues[9])) {
								entryDTO.setVarIncPt(CPSUtils
										.convertToInteger(rvalues[9]));
							}



							List<PromoteesEntryDTO> list = session
									.createCriteria(PromoteesEntryDTO.class)
									.add(Expression.eq("status", 1))
									.add(Expression.eq("sfID", rvalues[2]))
									.list();
							for (int i = 0; i <= list.size() - 1; i++) {
								PromoteesEntryDTO entryDTO1 = list.get(i);
								entryDTO1.setStatus(100);
								entryDTO1
								.setPromotedEffectiveDate(CPSUtils.convertStringToDate(CPSUtils.formatDate(entryDTO1
										.getPromotedEffectiveDate())));
								entryDTO1.setCreationDate(CPSUtils
										.formattedDate(entryDTO1
												.getCreationDate()));
								entryDTO1.setLastModifiedDate(CPSUtils
										.getCurrentDate());
								entryDTO1.setSeniorityDate(CPSUtils
										.formattedDate(entryDTO1
												.getSeniorityDate()));
								entryDTO1.setVarIncEndDate(CPSUtils
										.convertStringToDate(CPSUtils
												.formatDate(entryDTO1
														.getVarIncEndDate())));
								entryDTO1
								.setPresentEffectiveDate(CPSUtils.convertStringToDate(CPSUtils.formatDate(entryDTO1
										.getPresentEffectiveDate())));
								session.flush();


							}


							session.clear();
							session.saveOrUpdate(entryDTO);
							session.flush();
							gradePayid = entryDTO.getId();

							// code for updating Grade pay status to 100 fro
							// prevoius records


							//
							// code for updating basic pay status to 100 fro
							// prevoius records
							List<EmpBasicPayHistoryDTO> list1 = session
									.createCriteria(EmpBasicPayHistoryDTO.class)
									.add(Expression.eq("status", 1))
									.add(Expression.eq("sfid", rvalues[2]))
									.list();
							for (int i = 0; i <= list1.size() - 1; i++) {
								EmpBasicPayHistoryDTO basicDTO1 = list1.get(i);
								basicDTO1.setStatus(100);
								basicDTO1
								.setPresentEffectiveDate(CPSUtils.convertStringToDate(CPSUtils.formatDate(basicDTO1
										.getPresentEffectiveDate())));
								basicDTO1.setLastModifiedDate(CPSUtils
										.getCurrentDate());
								basicDTO1.setCreationDate(CPSUtils
										.formattedDate(basicDTO1
												.getCreationDate()));
								session.flush();
							}

							//
							// code for saving in emp_basic_pay_history
							EmpBasicPayHistoryDTO basicPayHistoryDTO = new EmpBasicPayHistoryDTO();
							basicPayHistoryDTO
							.setSfid(rvalues[2].toUpperCase());
							basicPayHistoryDTO.setBasicPay(Float
									.valueOf(rvalues[3]));
							basicPayHistoryDTO.setStatus(1);
							if (!CPSUtils.isNullOrEmpty(rvalues[8]))
								basicPayHistoryDTO
								.setPresentEffectiveDate(CPSUtils
										.convertStringToDate(rvalues[8]));
							basicPayHistoryDTO.setDesignationId(entryBean
									.getDesignationTo());

							basicPayHistoryDTO.setReferenceType("online");
							basicPayHistoryDTO.setIncrementType("P");
							basicPayHistoryDTO.setIncrementValue(0);// change 0
							// to the
							// value of
							// 3%*(B.P+G.P)
							basicPayHistoryDTO.setCreatedBy(promotionBean
									.getSfID());
							basicPayHistoryDTO.setCreationDate(CPSUtils
									.getCurrentDate());
							basicPayHistoryDTO.setLastModifiedBy(promotionBean
									.getSfID());
							basicPayHistoryDTO.setLastModifiedDate(CPSUtils
									.getCurrentDate());
							session.save(basicPayHistoryDTO);
							session.flush();
							basicPayId = basicPayHistoryDTO.getId();


							Assementdto = (AssessmentDetailsDTO) session
									.createCriteria(AssessmentDetailsDTO.class)
									.add(Expression.eq("id",
											Integer.parseInt(rvalues[1])))
											.uniqueResult();
							Assementdto.setBasicpayId(basicPayId);
							Assementdto.setGradepayId(gradePayid);
							session.flush();

						}
					} else {
						entryBean = (PromotionOfflineEntryBean) session
								.createSQLQuery(
										"SELECT EMP.DESIGNATION_ID designationID,EMP.SENIORITY_DATE seniorityDate,PAY.BASIC_PAY basicPay,pay.GRADE_PAY gradePay FROM EMP_MASTER EMP,EMP_PAYMENT_DETAILS PAY where EMP.SFID=PAY.SFID AND EMP.STATUS=1 and EMP.sfid=?")
										.addScalar("designationID", Hibernate.INTEGER)
										.addScalar("seniorityDate", Hibernate.DATE)
										.addScalar("basicPay", Hibernate.FLOAT)
										.addScalar("gradePay", Hibernate.FLOAT)
										.setString(0, rvalues[2])
										.setResultTransformer(
												Transformers
												.aliasToBean(PromotionOfflineEntryBean.class))
												.uniqueResult();

						// code for updating basic pay status to 100 fro
						// prevoius records
						List<EmpBasicPayHistoryDTO> list1 = session
								.createCriteria(EmpBasicPayHistoryDTO.class)
								.add(Expression.eq("status", 1))
								.add(Expression.eq("sfid", rvalues[2])).list();
						for (int i = 0; i <= list1.size() - 1; i++) {
							EmpBasicPayHistoryDTO basicDTO1 = list1.get(i);
							basicDTO1.setStatus(100);
							basicDTO1
							.setPresentEffectiveDate(CPSUtils.convertStringToDate(CPSUtils
									.formatDate(basicDTO1
											.getPresentEffectiveDate())));
							basicDTO1.setLastModifiedDate(CPSUtils
									.getCurrentDate());
							basicDTO1
							.setCreationDate(CPSUtils
									.formattedDate(basicDTO1
											.getCreationDate()));
							session.flush();
						}

						//

						// code for saving in emp_basic_pay_history
						EmpBasicPayHistoryDTO basicPayHistoryDTO = new EmpBasicPayHistoryDTO();
						basicPayHistoryDTO.setSfid(rvalues[2].toUpperCase());
						basicPayHistoryDTO.setBasicPay(Float
								.valueOf(rvalues[3]));
						basicPayHistoryDTO.setStatus(1);
						if (!CPSUtils.isNullOrEmpty(rvalues[8]))
							basicPayHistoryDTO.setPresentEffectiveDate(CPSUtils
									.convertStringToDate(rvalues[8]));

						basicPayHistoryDTO.setDesignationId(entryBean
								.getDesignationID());

						basicPayHistoryDTO.setReferenceType("online");
						basicPayHistoryDTO.setIncrementType("P");
						basicPayHistoryDTO.setIncrementValue(0);// change 0 to
						// the value of
						// 3%*(B.P+G.P)
						basicPayHistoryDTO
						.setCreatedBy(promotionBean.getSfID());
						basicPayHistoryDTO.setDesignationId(entryBean
								.getDesignationTo());
						basicPayHistoryDTO.setCreationDate(CPSUtils
								.getCurrentDate());
						basicPayHistoryDTO.setLastModifiedBy(promotionBean
								.getSfID());
						basicPayHistoryDTO.setLastModifiedDate(CPSUtils
								.getCurrentDate());

						session.save(basicPayHistoryDTO);
						session.flush();

						basicPayId = basicPayHistoryDTO.getId();
						offlineReferenceId = entryDTO.getId();
						entryDTO.setSeniorityDate(CPSUtils
								.formattedDate(entryDTO.getSeniorityDate()));


						entryDTO.setCreatedBy(promotionBean.getSfID());
						entryDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
						entryDTO.setLastModifiedTime(CPSUtils
								.getCurrentDateWithTime());
						entryDTO.setLastModifiedBy(promotionBean.getSfID());


						if (!CPSUtils.isNullOrEmpty(rvalues[3])) {
							entryDTO.setNewPay(Float.valueOf(rvalues[3]));
						}
						if (!CPSUtils.isNullOrEmpty(rvalues[4])) {
							entryDTO.setNewGradePay(Float.valueOf(rvalues[4]));
						}
						if (!CPSUtils.isNullOrEmpty(rvalues[5])) {
							entryDTO.setTwoAddl(rvalues[5]);
						}
						if (!CPSUtils.isNullOrEmpty(rvalues[9])) {
							entryDTO.setVarIncPt(CPSUtils
									.convertToInteger(rvalues[9]));
						}
						entryDTO.setReferenceType("Online");
						entryDTO.setCreatedBy(promotionBean.getSfID());
						entryDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
						entryDTO.setLastModifiedTime(CPSUtils
								.getCurrentDateWithTime());
						entryDTO.setLastModifiedBy(promotionBean.getSfID());
						session.clear();
						session.saveOrUpdate(entryDTO);
						session.flush();
						gradePayid = entryDTO.getId();

						Assementdto = (AssessmentDetailsDTO) session
								.createCriteria(AssessmentDetailsDTO.class)
								.add(Expression.eq("id",
										Integer.parseInt(rvalues[1])))
										.uniqueResult();
						Assementdto.setBasicpayId(basicPayId);
						Assementdto.setGradepayId(gradePayid);
						session.flush();

						offlineReferenceId = Integer
								.valueOf(session
										.createSQLQuery(
												"select case when max(id) is null then 1 else max(id) end from EMP_GRADE_PAY_HISTORY")
												.uniqueResult().toString());
						// PayFixationID#AssessmentID#SFID#newBasicPay#newGradePay#twoAddIO
					}


					PayFixationDTO payFixationDTO = (PayFixationDTO) session
							.createCriteria(PayFixationDTO.class)
							.add(Expression.eq("id",
									Integer.valueOf(rvalues[0])))
									.add(Expression.eq(CPSConstants.STATUS, 1))
									.uniqueResult();
					if (CPSUtils.isNull(payFixationDTO)) {
						payFixationDTO = new PayFixationDTO();
						payFixationDTO.setAssessmentID(Integer
								.valueOf(rvalues[1]));
						payFixationDTO.setCategoryId(promotionBean
								.getAssessmentTypeID());
						payFixationDTO.setStatus(1);
						payFixationDTO.setCreatedBy(promotionBean.getSfID());
						payFixationDTO.setCreationTime(currentDate);
						payFixationDTO.setReferenceId(offlineReferenceId);

						EmpPaymentsDTO empPaymentsDTO = (EmpPaymentsDTO) session
								.createCriteria(EmpPaymentsDTO.class)
								.add(Expression.eq(CPSConstants.SFID,
										rvalues[2])).uniqueResult();
						if (!CPSUtils.isNull(empPaymentsDTO)) {
							if (!CPSUtils.isNull(empPaymentsDTO.getBasicPay())) {
								payFixationDTO.setPay(Float
										.valueOf(empPaymentsDTO.getBasicPay()));
							}
							if (!CPSUtils.isNull(empPaymentsDTO.getGradePay())) {
								payFixationDTO.setGradePay(Float
										.valueOf(empPaymentsDTO.getGradePay()));
							}
							if (!CPSUtils
									.isNull(empPaymentsDTO.getTwoAddIncr())) {
								payFixationDTO
								.setTwoAddPay(Float
										.valueOf(empPaymentsDTO
												.getTwoAddIncr()));
							}
						}
					}
					if (!CPSUtils.isNullOrEmpty(rvalues[3])) {
						payFixationDTO.setNewPay(Float.valueOf(rvalues[3]));
					}
					if (!CPSUtils.isNullOrEmpty(rvalues[4])) {
						payFixationDTO
						.setNewGradePay(Float.valueOf(rvalues[4]));
					}
					if (!CPSUtils.isNullOrEmpty(rvalues[5])) {
						payFixationDTO.setNewTwoAddPay(Float
								.valueOf(rvalues[5]));
					}
					if (!CPSUtils.isNullOrEmpty(rvalues[15])) {
						payFixationDTO.setVarIncEndDate(CPSUtils
								.convertStringToDate(rvalues[15]));
					}
					if (!CPSUtils.isNullOrEmpty(rvalues[9])) {

						payFixationDTO.setVariableIncrements(rvalues[9]);
					}
					if (!CPSUtils.isNullOrEmpty(rvalues[10])) {

						payFixationDTO.setVariableIncrValue(Integer.parseInt(rvalues[10]));
					}
					if (!CPSUtils.isNullOrEmpty(rvalues[17])) {
						// payFixationDTO.setIncrementsDoPartId(Integer.parseInt(rvalues[17]));
					}
					payFixationDTO.setLastModifiedBy(promotionBean.getSfID());
					payFixationDTO.setLastModifiedTime(currentDate);
					if (!CPSUtils.isNullOrEmpty(rvalues[8])) {
						payFixationDTO.setFixationAcceptedDate(CPSUtils
								.convertStringToDate(rvalues[8]));
						if (!CPSUtils.isNullOrEmpty(rvalues[17])) {
							payFixationDTO.setFixationDoPartId(Integer
									.parseInt(rvalues[17]));
						}
					}
					// payFixationDTO.setVariableIncrements(Integer.parseInt(rvalues[9]));
					// payFixationDTO.setDoPartDetails(doPartBean);
					payFixationDTO.setDoPartID(Integer.valueOf(promotionBean
							.getDoPartNumber()));
					session.saveOrUpdate(payFixationDTO);
					session.flush();
					// insert into DOPART_II_SLNO
					DoPartSerialNoDTO serialNoDTO = new DoPartSerialNoDTO();
					serialNoDTO.setRefDoPartId(Integer.parseInt(promotionBean
							.getDoPartNumber()));
					serialNoDTO.setModuleId(Integer
							.parseInt(CPSConstants.PROMOTIONMODULEID));
					serialNoDTO.setCasualityId(Integer.parseInt(promotionBean
							.getCasualityId()));
					serialNoDTO.setSlNo(promotionBean.getSerialNumber());
					serialNoDTO.setRequestId(rvalues[1]);
					serialNoDTO.setStatus(1);
					serialNoDTO.setCreationDate(CPSUtils.getCurrentDate());
					serialNoDTO.setCreatedBy(promotionBean.getSfID());
					serialNoDTO.setLastModifiedBy(promotionBean.getSfID());
					serialNoDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					session.saveOrUpdate(serialNoDTO);
					session.flush();


				}
			} else {
				for (String row : rows) {
					String[] rvalues = row.split("#");
					PayFixationDTO payFixationDTO = (PayFixationDTO) session
							.createCriteria(PayFixationDTO.class)
							.add(Expression.eq("id",
									Integer.valueOf(rvalues[0])))
									.add(Expression.eq(CPSConstants.STATUS, 1))
									.uniqueResult();
					if (CPSUtils.isNull(payFixationDTO)) {
						payFixationDTO = new PayFixationDTO();
						payFixationDTO.setAssessmentID(Integer
								.valueOf(rvalues[1]));
						payFixationDTO.setCategoryId(promotionBean
								.getAssessmentTypeID());
						payFixationDTO.setStatus(1);
						payFixationDTO.setCreatedBy(promotionBean.getSfID());
						payFixationDTO.setCreationTime(currentDate);
						payFixationDTO.setReferenceId(offlineReferenceId);

						EmpPaymentsDTO empPaymentsDTO = (EmpPaymentsDTO) session
								.createCriteria(EmpPaymentsDTO.class)
								.add(Expression.eq(CPSConstants.SFID,
										rvalues[2])).uniqueResult();
						if (!CPSUtils.isNull(empPaymentsDTO)) {
							if (!CPSUtils.isNull(empPaymentsDTO.getBasicPay())) {
								payFixationDTO.setPay(Float
										.valueOf(empPaymentsDTO.getBasicPay()));
							}
							if (!CPSUtils.isNull(empPaymentsDTO.getGradePay())) {
								payFixationDTO.setGradePay(Float
										.valueOf(empPaymentsDTO.getGradePay()));
							}
							if (!CPSUtils
									.isNull(empPaymentsDTO.getTwoAddIncr())) {
								payFixationDTO
								.setTwoAddPay(Float
										.valueOf(empPaymentsDTO
												.getTwoAddIncr()));
							}
						}
					}

					if (!CPSUtils.isNullOrEmpty(rvalues[15])) {
						payFixationDTO.setVarIncEndDate(CPSUtils
								.convertStringToDate(rvalues[15]));
					}
					if (!CPSUtils.isNullOrEmpty(rvalues[14])) {
						payFixationDTO.setVariableIncrements(rvalues[14]);
					}
					if (!CPSUtils.isNullOrEmpty(rvalues[17])) {
						payFixationDTO.setIncrementsDoPartId(Integer
								.parseInt(rvalues[17]));
					}
					payFixationDTO.setLastModifiedBy(promotionBean.getSfID());
					payFixationDTO.setLastModifiedTime(currentDate);
					payFixationDTO.setDoPartID(Integer.valueOf(promotionBean
							.getDoPartNumber()));
					session.saveOrUpdate(payFixationDTO);
					session.flush();
					// insert into DOPART_II_SLNO
					DoPartSerialNoDTO serialNoDTO = new DoPartSerialNoDTO();
					serialNoDTO.setRefDoPartId(Integer.parseInt(promotionBean
							.getDoPartNumber()));
					serialNoDTO.setModuleId(Integer
							.parseInt(CPSConstants.PROMOTIONMODULEID));
					serialNoDTO.setCasualityId(Integer.parseInt(promotionBean
							.getCasualityId()));
					serialNoDTO.setSlNo(promotionBean.getSerialNumber());
					serialNoDTO.setRequestId(rvalues[1]);
					serialNoDTO.setStatus(1);
					serialNoDTO.setCreationDate(CPSUtils.getCurrentDate());
					serialNoDTO.setCreatedBy(promotionBean.getSfID());
					serialNoDTO.setLastModifiedBy(promotionBean.getSfID());
					serialNoDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					session.saveOrUpdate(serialNoDTO);
					session.flush();

				}
			}
			// hibernateUtils.closeSession();
			// hibernateUtils.getSession();
			promotionBean.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	public PromotionManagementBean submitPayFixationOfflineDetails(
			PromotionManagementBean promotionBean) throws Exception {
		Session session = null;
		try {
			PromotionOfflineEntryBean entryBean = null;
			session = hibernateUtils.getSession();
			String[] rows = promotionBean.getRowValues().split(",");
			for (String row : rows) {
				String[] rvalues = row.split("#");
				PromoteesEntryDTO entryDTO = (PromoteesEntryDTO) session
						.createCriteria(PromoteesEntryDTO.class)
						.add(Expression.eq("sfID", rvalues[2]))
						.add(Expression.eq(CPSConstants.STATUS, 1))
						.uniqueResult();
				if (CPSUtils.isNull(entryDTO)) {
					entryDTO = new PromoteesEntryDTO();
					entryDTO.setSfID(rvalues[2].toUpperCase());
					/*
					 * entryBean=(PromotionOfflineEntryBean)session.createSQLQuery
					 * (
					 * "SELECT EMP.DESIGNATION_ID designationID,EMP.SENIORITY_DATE seniorityDate,RPM.DESIGNATION_TO designationTo,PAY.BASIC_PAY basicPay,PAY.GRADE_PAY gradePay,PAD.EFFECTIVE_DATE effectiveDate "
					 * +
					 * "FROM PRO_RESIDENCY_PERIOD_MASTER RPM,EMP_MASTER EMP,EMP_PAYMENT_DETAILS PAY,PRO_ASSESSMENT_DETAILS PAD where EMP.DESIGNATION_ID=RPM.DESIGNATION_FROM AND EMP.SFID=PAY.SFID AND EMP.SFID=PAD.SFID and EMP.STATUS=1 and rpm.status=1 and EMP.sfid=?"
					 * ) .addScalar("designationID",
					 * Hibernate.INTEGER).addScalar("seniorityDate",
					 * Hibernate.DATE).addScalar("designationTo",
					 * Hibernate.INTEGER).addScalar("basicPay",
					 * Hibernate.FLOAT).addScalar("gradePay",
					 * Hibernate.FLOAT).addScalar("effectiveDate",
					 * Hibernate.DATE
					 * ).setString(0,rvalues[2]).setResultTransformer
					 * (Transformers
					 * .aliasToBean(PromotionOfflineEntryBean.class)
					 * ).uniqueResult();
					 */
					entryBean = (PromotionOfflineEntryBean) session
							.createSQLQuery(
									"SELECT EMP.DESIGNATION_ID designationID,EMP.SENIORITY_DATE seniorityDate,RPM.DESIGNATION_TO designationTo,PAY.BASIC_PAY basicPay,PAY.GRADE_PAY gradePay,"
											+ " PAD.EFFECTIVE_DATE varIncStartDate,pad.promotion_date effectiveDate, pad.variable_incr varIncPt,pad.ending_date varIncEndDate"
											+ " FROM PRO_RESIDENCY_PERIOD_MASTER RPM,EMP_MASTER EMP,EMP_PAYMENT_DETAILS PAY,"
											+ " PRO_ASSESSMENT_DETAILS PAD where EMP.DESIGNATION_ID=RPM.DESIGNATION_FROM AND EMP.SFID=PAY.SFID"
											+ " AND EMP.SFID=PAD.SFID and EMP.STATUS=1 and rpm.status=1 and  EMP.sfid=?")
											.addScalar("designationID", Hibernate.INTEGER)
											.addScalar("seniorityDate", Hibernate.DATE)
											.addScalar("designationTo", Hibernate.INTEGER)
											.addScalar("basicPay", Hibernate.FLOAT)
											.addScalar("gradePay", Hibernate.FLOAT)
											.addScalar("varIncStartDate", Hibernate.DATE)
											.addScalar("effectiveDate", Hibernate.DATE)
											.addScalar("varIncPt", Hibernate.INTEGER)
											.addScalar("varIncEndDate", Hibernate.DATE)
											.setString(0, rvalues[2])
											.setResultTransformer(
													Transformers
													.aliasToBean(PromotionOfflineEntryBean.class))
													.uniqueResult();
					if (!CPSUtils.isNull(entryBean)) {
						entryDTO.setPay(entryBean.getBasicPay());
						// entryDTO.setPresentDesignation(entryBean.getDesignationID());
						entryDTO.setPresentEffectiveDate(entryBean
								.getVarIncStartDate());
						entryDTO.setVarIncPt(entryBean.getVarIncPt());
						entryDTO.setVarIncEndDate(entryBean.getVarIncEndDate());
						entryDTO.setGradePay(entryBean.getGradePay());
						entryDTO.setPromotedEffectiveDate(entryBean
								.getEffectiveDate());
						entryDTO.setPromotedDesignation(entryBean
								.getDesignationTo());
						entryDTO.setCreationTime(CPSUtils
								.getCurrentDateWithTime());
						entryDTO.setCreatedBy(promotionBean.getSfID());
						entryDTO.setLastModifiedTime(CPSUtils
								.getCurrentDateWithTime());
						entryDTO.setLastModifiedBy(promotionBean.getSfID());
						entryDTO.setReferenceType("Online");
						entryDTO.setStatus(1);
					}
				}
				entryDTO.setNewPay(Float.valueOf(rvalues[3]));
				entryDTO.setNewGradePay(Float.valueOf(rvalues[4]));

				session.saveOrUpdate(entryDTO);
				promotionBean.setResult(CPSConstants.SUCCESS);
			}
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	@Override
	public String changeStatus(String referenceID, int status) throws Exception {
		String result = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			AssessmentDetailsDTO assessmentDetailsDTO = (AssessmentDetailsDTO) session
					.get(AssessmentDetailsDTO.class,
							Integer.valueOf(referenceID));
			assessmentDetailsDTO.setStatus(status);
			session.saveOrUpdate(assessmentDetailsDTO);
			result = CPSConstants.SUCCESS;
		} catch (Exception e) {

			throw e;
		}
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public PromotionManagementBean getPayFixationDOPartDetails(
			PromotionManagementBean promotionBean) throws Exception {
		Session session = null;
		StringBuilder sb = new StringBuilder();
		try {
			session = hibernateUtils.getSession();
			session.clear();
			sb.append("select distinct rnd.id id,rnd.ref_number doPartNo,rnd.ref_date formatDoPartDate from pro_pay_fixation ppf,reference_number_details rnd where ppf.do_part_id=rnd.id and ppf.status=1");
			if (!CPSUtils.isNullOrEmpty(promotionBean.getDoPartNumber())) {
				sb.append(" and upper(rnd.ref_number)=upper('"
						+ promotionBean.getDoPartNumber() + "')");
			}
			if (!CPSUtils.isNullOrEmpty(promotionBean.getDoPartDate())) {
				sb.append(" and upper(to_char(rnd.ref_date,'DD-MON-YYYY'))=upper('"
						+ promotionBean.getDoPartDate() + "')");
			}
			promotionBean.setPayFixationDOPartDetails(session
					.createSQLQuery(sb.toString())
					.addScalar("doPartNo", Hibernate.STRING)
					.addScalar("formatDoPartDate", Hibernate.DATE)
					.addScalar("id", Hibernate.INTEGER)
					.setResultTransformer(
							Transformers.aliasToBean(DoPartBean.class)).list());
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public PromotionManagementBean getHQCandidates(
			PromotionManagementBean promotionBean) throws Exception {
		Session session = null;
		StringBuilder sb = new StringBuilder();
		try {
			session = hibernateUtils.getSession();
			if(promotionBean.getAssessmentCategoryID()!=3)
			{
				sb.append("SELECT *FROM(Select  (SELECT SUM(LRD.NO_OF_DAYS) FROM LEAVE_REQUEST_DETAILS LRD WHERE lrd.SFID=tab2.sfid AND lrd.STATUS not in(?) and lrd.leave_type_id=? and lrd.from_date between tab2.seniority_date and ?) eolWomc,case when  tab2.disciplineID is null then 0 else tab2.disciplineID end disciplineID,case when tab2.subDisciplineID is null then 0 else tab2.subDisciplineID end subDisciplineID,tab2.status sta,tab2.dob,TAB2.ORDER_NO,tab2.doj_drdo,NVL(TAB2.ATTEMPTS,(ROUND((?-TAB2.SENIORITY_DATE)/365)-TAB2.RESIDENCY_PERIOD)+1) attempts,round((?-tab2.seniority_date)/365) desigExperince,Tab2.seniority_date seniorityDate,Tab2.Sfid Sfid,Empname,Designationid,Designation,Departmentid,Department,Grade,Assessmentid,"
					+ "case when tab2.status is null then 0 when tab2.status in (?,?,?,?,?,?) then 58 else tab2.status end status,relaxationInMonths,residencyPeriodId,residencyPeriod,Written,designationTo,trade,interview,boardFlag from (select   pad.discipline_id disciplineID,pad.sub_discipline_id subDisciplineID,tab1.residency_period,residencyPeriodId,Written,trade,designationTo,interview,boardFlag,tab1.dob,TAB1.ORDER_NO,tab1.seniority_date,tab1.sfid,empName,doj_drdo,designationID,designation,departmentID,department,grade,"
					+ "pad.id assessmentID,pad.interview_date interviewDate,case when pad.no_of_attempts is null then '' else pad.no_of_attempts end attempts,pad.venue_id venueID,pad.board_id boardID,pad.lab_representative labRepresentative,relaxationInMonths,residencyPeriod,pad.status,pad.effective_date effectiveDate,"
					+ "pad.variable_incr variableIncr,pad.reservation_id reservationID from (select   RPM.RESIDENCY_PERIOD,RPM.ID residencyPeriodId, Rpm.WRITTEN_TEST_FLAG Written,desig1.name designationTo, Rpm.TRADE_TEST_FLAG trade, Rpm.INTERVIEW_FLAG interview, Rpm.BOARD_TYPE_FLAG boardFlag,emp.dob,DESIG.ORDER_NO,emp.seniority_date,emp.sfid,emp.name_in_service_book empName,emp.doj_drdo,desig.id designationID,desig.name designation,dept.department_id departmentID,dept.department_name department,gm.name grade,"
					+ "Rpm.Residency_period Residencyperiod,Rpm.Relaxation_in_months Relaxationinmonths from emp_master emp,designation_master desig,org_role_instance ori,departments_master dept,"
					+ "designation_mappings dmap, designation_master desig1,group_master gm,pro_residency_period_master rpm,CATEGORY_MASTER CM Where rpm.status=1 and Emp.Status=1 And Emp.Designation_id=Desig.Id  And Desig.Status=1 And Ori.Status=1 And Ori.Org_role_id=Emp.Office_id And Dept.Status=1 And  "
					+ "Dept.Department_id=Ori.Department_id  And Rpm.DESIGNATION_TO = desig1.id  And Dmap.Desig_id=Desig.Id And Gm.Status=1 And Gm.Id=Dmap.Group_id and DMAP.CATEGORY_ID=cm.id And (to_char(?,'dd-Mon-yyyy')>=Add_months(emp.seniority_date,(12*Rpm.Residency_period)-(Rpm.Relaxation_in_months)) ) And Rpm.Designation_from=Emp.Designation_id   And Rpm.Assessment_category_id=? and DMAP.CATEGORY_ID=? ");
				if (!(promotionBean.getDesignationFrom() == 0)) {
					sb.append(" AND desig.id=" + promotionBean.getDesignationFrom());
				}
				sb.append(") Tab1,Pro_assessment_details Pad Where Pad.Sfid(+)=Tab1.Sfid  And pad.residency_id(+)=tab1.residencyPeriodId   and Pad.Year_id(+)=?) Tab2) ORDER BY sta desc,seniorityDate,dob,ORDER_NO,EMPNAME,SFID");
				promotionBean.setAssessmentDetails(session
						.createSQLQuery(sb.toString())
						.addScalar("eolWomc", Hibernate.STRING)
						.addScalar("disciplineID", Hibernate.INTEGER)
						.addScalar("subDisciplineID", Hibernate.INTEGER)
						.addScalar("desigExperince", Hibernate.STRING)
						.addScalar("seniorityDate", Hibernate.DATE)
						.addScalar("doj_drdo", Hibernate.DATE)
						.addScalar("sfID", Hibernate.STRING)
						.addScalar("designationTo", Hibernate.STRING)
						.addScalar("empName", Hibernate.STRING)
						.addScalar("designation", Hibernate.STRING)
						.addScalar("department", Hibernate.STRING)
						.addScalar("grade", Hibernate.STRING)
						.addScalar("status", Hibernate.INTEGER)
						.addScalar("relaxationInMonths", Hibernate.INTEGER)
						.addScalar("residencyPeriod", Hibernate.INTEGER)
						.addScalar("residencyPeriodId", Hibernate.INTEGER)
						.addScalar("Written", Hibernate.INTEGER)
						.addScalar("trade", Hibernate.INTEGER)
						.addScalar("interview", Hibernate.INTEGER)
						.addScalar("boardFlag", Hibernate.INTEGER)
						.addScalar("attempts", Hibernate.STRING)
						.addScalar("designationID", Hibernate.INTEGER)
						.addScalar("departmentID", Hibernate.INTEGER)
						.setResultTransformer(
								Transformers
								.aliasToBean(AssessmentDetailsDTO.class))
								.setString(0, CPSConstants.STATUSCANCELLED)
								.setString(1, CPSConstants.EOLWOMC)
								.setDate(2, promotionBean.getAssessmentDate())
								.setDate(3, promotionBean.getAssessmentDate())
								.setDate(4, promotionBean.getAssessmentDate())
								.setString(5, CPSConstants.STATUSHEADQUARTER)
								.setString(6, CPSConstants.STATUSQUALIFIED)
								.setString(7, CPSConstants.STATUSAACCEPTED)
								.setString(8, CPSConstants.STATUSDEFERRED)
								.setString(9, CPSConstants.STATUSREJECTED)
								.setString(10, CPSConstants.STATUSPROMOTED)
								.setDate(11, promotionBean.getAssessmentDate())
								.setInteger(12, promotionBean.getAssessmentCategoryID())
								.setInteger(13, promotionBean.getAssessmentTypeID())
								.setInteger(14, promotionBean.getYearID()).list());
			}
			else
			{
				sb.append("SELECT *FROM(Select  (SELECT SUM(LRD.NO_OF_DAYS) FROM LEAVE_REQUEST_DETAILS LRD WHERE lrd.SFID=tab2.sfid AND lrd.STATUS not in(?) and lrd.leave_type_id=? and lrd.from_date between tab2.seniority_date and ?) eolWomc,case when  tab2.disciplineID is null then 0 else tab2.disciplineID end disciplineID,case when tab2.subDisciplineID is null then 0 else tab2.subDisciplineID end subDisciplineID,tab2.status sta,tab2.dob,TAB2.ORDER_NO,tab2.doj_drdo,NVL(TAB2.ATTEMPTS,(ROUND((?-TAB2.SENIORITY_DATE)/365)-TAB2.RESIDENCY_PERIOD)+1) attempts,round((?-tab2.doj_drdo)/365) desigExperince,Tab2.seniority_date seniorityDate,Tab2.Sfid Sfid,Empname,Designationid,Designation,Departmentid,Department,Grade,Assessmentid,"
						+ "case when tab2.status is null then 0 when tab2.status in (?,?,?,?,?,?) then 58 else tab2.status end status,relaxationInMonths,residencyPeriodId,residencyPeriod,Written,designationTo,trade,interview,boardFlag from (select   pad.discipline_id disciplineID,pad.sub_discipline_id subDisciplineID,tab1.residency_period,residencyPeriodId,Written,trade,designationTo,interview,boardFlag,tab1.dob,TAB1.ORDER_NO,tab1.seniority_date,tab1.sfid,empName,doj_drdo,designationID,designation,departmentID,department,grade,"
						+ "pad.id assessmentID,pad.interview_date interviewDate,case when pad.no_of_attempts is null then '' else pad.no_of_attempts end attempts,pad.venue_id venueID,pad.board_id boardID,pad.lab_representative labRepresentative,relaxationInMonths,residencyPeriod,pad.status,pad.effective_date effectiveDate,"
						+ "pad.variable_incr variableIncr,pad.reservation_id reservationID from (select   RPM.RESIDENCY_PERIOD,RPM.ID residencyPeriodId, Rpm.WRITTEN_TEST_FLAG Written,desig1.name designationTo, Rpm.TRADE_TEST_FLAG trade, Rpm.INTERVIEW_FLAG interview, Rpm.BOARD_TYPE_FLAG boardFlag,emp.dob,DESIG.ORDER_NO,emp.seniority_date,emp.sfid,emp.name_in_service_book empName,emp.doj_drdo,desig.id designationID,desig.name designation,dept.department_id departmentID,dept.department_name department,gm.name grade,"
						+ "Rpm.Residency_period Residencyperiod,Rpm.Relaxation_in_months Relaxationinmonths from emp_master emp,designation_master desig,org_role_instance ori,departments_master dept,"
						+ "designation_mappings dmap, designation_master desig1,group_master gm,pro_residency_period_master rpm,CATEGORY_MASTER CM Where rpm.status=1 and Emp.Status=1 And Emp.Designation_id=Desig.Id  And Desig.Status=1 And Ori.Status=1 And Ori.Org_role_id=Emp.Office_id And Dept.Status=1 And  "
						+ "Dept.Department_id=Ori.Department_id  And Rpm.DESIGNATION_TO = desig1.id  And Dmap.Desig_id=Desig.Id And Gm.Status=1 And Gm.Id=Dmap.Group_id and DMAP.CATEGORY_ID=cm.id And (to_char(?,'dd-Mon-yyyy')>=Add_months(emp.doj_drdo,(12*Rpm.Residency_period)-(Rpm.Relaxation_in_months)) ) And Rpm.Designation_from=Emp.Designation_id   And Rpm.Assessment_category_id=? and DMAP.CATEGORY_ID=? ");
				
			if (!(promotionBean.getDesignationFrom() == 0)) {
				sb.append(" AND desig.id=" + promotionBean.getDesignationFrom());
			}
			sb.append(") Tab1,Pro_assessment_details Pad Where Pad.Sfid(+)=Tab1.Sfid  And pad.residency_id(+)=tab1.residencyPeriodId   and Pad.Year_id(+)=?) Tab2) ORDER BY sta desc,seniorityDate,dob,ORDER_NO,EMPNAME,SFID");
			promotionBean.setAssessmentDetails(session
					.createSQLQuery(sb.toString())
					.addScalar("eolWomc", Hibernate.STRING)
					.addScalar("disciplineID", Hibernate.INTEGER)
					.addScalar("subDisciplineID", Hibernate.INTEGER)
					.addScalar("desigExperince", Hibernate.STRING)
					.addScalar("seniorityDate", Hibernate.DATE)
					.addScalar("doj_drdo", Hibernate.DATE)
					.addScalar("sfID", Hibernate.STRING)
					.addScalar("designationTo", Hibernate.STRING)
					.addScalar("empName", Hibernate.STRING)
					.addScalar("designation", Hibernate.STRING)
					.addScalar("department", Hibernate.STRING)
					.addScalar("grade", Hibernate.STRING)
					.addScalar("status", Hibernate.INTEGER)
					.addScalar("relaxationInMonths", Hibernate.INTEGER)
					.addScalar("residencyPeriod", Hibernate.INTEGER)
					.addScalar("residencyPeriodId", Hibernate.INTEGER)
					.addScalar("Written", Hibernate.INTEGER)
					.addScalar("trade", Hibernate.INTEGER)
					.addScalar("interview", Hibernate.INTEGER)
					.addScalar("boardFlag", Hibernate.INTEGER)
					.addScalar("attempts", Hibernate.STRING)
					.addScalar("designationID", Hibernate.INTEGER)
					.addScalar("departmentID", Hibernate.INTEGER)
					.setResultTransformer(
							Transformers
							.aliasToBean(AssessmentDetailsDTO.class))
							.setString(0, CPSConstants.STATUSCANCELLED)
							.setString(1, CPSConstants.EOLWOMC)
							.setDate(2, promotionBean.getAssessmentDate())
							.setDate(3, promotionBean.getAssessmentDate())
							.setDate(4, promotionBean.getAssessmentDate())
							.setString(5, CPSConstants.STATUSHEADQUARTER)
							.setString(6, CPSConstants.STATUSQUALIFIED)
							.setString(7, CPSConstants.STATUSAACCEPTED)
							.setString(8, CPSConstants.STATUSDEFERRED)
							.setString(9, CPSConstants.STATUSREJECTED)
							.setString(10, CPSConstants.STATUSPROMOTED)
							.setDate(11, promotionBean.getAssessmentDate())
							.setInteger(12, promotionBean.getAssessmentCategoryID())
							.setInteger(13, promotionBean.getAssessmentTypeID())
							.setInteger(14, promotionBean.getYearID()).list());
			}

		} catch (Exception e) {
			throw e;
		}
		return promotionBean;

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PromotionsSubDisciplineDTO> getDesciplineDetails(
			PromotionManagementBean promotionBean) throws Exception {
		Session session = null;
		List list = null;
		try {
			session = hibernateUtils.getSession();
			list = session.createCriteria(PromotionsSubDisciplineDTO.class)
					.add(Expression.eq(CPSConstants.STATUS, 1))
					.addOrder(Order.asc("name")).list();

		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public PromotionManagementBean getTypeDesignationList(
			PromotionManagementBean promotionBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			promotionBean
			.setDesignationList(session
					.createSQLQuery(
							"Select Cm.Id Id,Dms.Id Desigid,Dms.Name Name From Category_master Cm,Designation_mappings Dm,Designation_master Dms Where Dm.Category_id=Cm.Id And Cm.Status=1 And Dms.Id=Dm.Desig_id AND DMS.STATUS=1 ORDER BY Dms.order_no")
							.addScalar("id", Hibernate.INTEGER)
							.addScalar("desigID", Hibernate.STRING)
							.addScalar("name", Hibernate.STRING)
							.setResultTransformer(
									Transformers
									.aliasToBean(DesignationDTO.class))
									.list());
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	@Override
	public int getCurrentYearID() throws Exception {
		int yearID = 0;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			yearID = (Integer) session
					.createSQLQuery(
							"select id from year_master where name=? and status=1")
							.addScalar("id", Hibernate.INTEGER)
							.setString(0, CPSUtils.getCurrentYear()).uniqueResult();
		} catch (Exception e) {
			throw e;
		}
		return yearID;
	}

	@Override
	public String submitDiscipline(PromotionsDisciplineDTO disciplineDTO)
			throws Exception {
		Session session = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();
			session.saveOrUpdate(disciplineDTO);
			// hibernateUtils.commitTransaction();
			message = CPSConstants.SUCCESS;
			// satya
			disciplineDTO.setCategoryName(((CategoryDTO) session
					.createCriteria(CategoryDTO.class)
					.add(Expression.eq("id", disciplineDTO.getCategoryID()))
					.add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult())
					.getName());
			session.flush();

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	@Override
	public String checkDiscipline(PromotionManagementBean promotionBean)
			throws Exception {
		Session session = null;
		try {
			Criteria crit = null;
			session = hibernateUtils.getSession();
			if (CPSUtils.compareStrings(promotionBean.getType(),
					"manageSubDiscipline")) {
				crit = session
						.createCriteria(PromotionsSubDisciplineDTO.class)
						.add(Expression.eq(CPSConstants.STATUS, 1))
						.add(Expression.eq("disciplineID",
								Integer.valueOf(promotionBean.getName())))
								.add(Expression.like(CPSConstants.NAME, promotionBean
										.getSubName().trim(), MatchMode.ANYWHERE))
										.add(Expression.eq("shortForm",
												promotionBean.getShortForm()));
			} else {
				crit = session
						.createCriteria(PromotionsDisciplineDTO.class)
						.add(Expression.eq(CPSConstants.STATUS, 1))
						.add(Expression.like(CPSConstants.NAME, promotionBean
								.getName().trim(), MatchMode.ANYWHERE))
								.add(Expression.eq("shortForm",
										promotionBean.getShortForm()))
										.add(Expression.eq("categoryID",
												promotionBean.getAssessmentTypeID()));
			}
			if (!CPSUtils.isNullOrEmpty(promotionBean.getNodeID())) {
				crit = crit.add(Expression.ne(CPSConstants.ID,
						Integer.parseInt(promotionBean.getNodeID())));
			}
			if (CPSUtils.checkList(crit.list())) {
				promotionBean.setResult(CPSConstants.DUPLICATE);
			}
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}

	@Override
	public String submitSubDiscipline(
			PromotionsSubDisciplineDTO subDisciplineDTO) throws Exception {
		Session session = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();
			session.saveOrUpdate(subDisciplineDTO);
			session.flush();
			// satya.
			// hibernateUtils.commitTransaction();
			subDisciplineDTO
			.setDisciplineDetails((PromotionsDisciplineDTO) session
					.createCriteria(PromotionsDisciplineDTO.class)
					.add(Expression.eq("id",
							subDisciplineDTO.getDisciplineID()))
							.add(Expression.eq(CPSConstants.STATUS, 1))
							.uniqueResult());
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	@Override
	public String submitExternalMember(ExternalEmpDTO externalEmpDTO)
			throws Exception {
		Session session = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();
			session.saveOrUpdate(externalEmpDTO);
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	@Override
	public String checkDuplicateExtMember(PromotionManagementBean promotionBean)
			throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			Criteria crit = session.createCriteria(ExternalEmpDTO.class).add(
					Expression.eq(CPSConstants.STATUS, 1));
			if (!CPSUtils.isNullOrEmpty(promotionBean.getNodeID())) {
				crit = crit.add(Expression.ne(CPSConstants.ID,
						Integer.parseInt(promotionBean.getNodeID())));
			}
			if (CPSUtils.checkList(crit.list())) {
				promotionBean.setResult(CPSConstants.DUPLICATE);
			}
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public String deleteDiscipline(PromotionManagementBean promotionBean)
			throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			List<AssessmentDetailsDTO> list = session
					.createCriteria(AssessmentDetailsDTO.class)
					.add(Expression.eq("disciplineID",
							Integer.valueOf(promotionBean.getNodeID()))).list();

			if (list.isEmpty()) {
				PromotionsDisciplineDTO disciplineDTO = (PromotionsDisciplineDTO) session
						.get(PromotionsDisciplineDTO.class,
								Integer.valueOf(promotionBean.getNodeID()));
				disciplineDTO.setStatus(0);
				disciplineDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				session.saveOrUpdate(disciplineDTO);
				session.flush();
				promotionBean.setResult(CPSConstants.DELETE);
				session.clear();
			} else {
				promotionBean.setResult(CPSConstants.DETAILSEXIST);
			}
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();

	}

	@Override
	public String deleteSubDiscipline(PromotionManagementBean promotionBean)
			throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			PromotionsSubDisciplineDTO subDisciplineDTO = (PromotionsSubDisciplineDTO) session
					.get(PromotionsSubDisciplineDTO.class,
							Integer.valueOf(promotionBean.getNodeID()));
			subDisciplineDTO.setStatus(0);
			subDisciplineDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			session.saveOrUpdate(subDisciplineDTO);
			session.flush();
			promotionBean.setResult(CPSConstants.DELETE);
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}

	@Override
	public String deleteExtBoardMember(PromotionManagementBean promotionBean)
			throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			ExternalEmpDTO externalEmpDTO = (ExternalEmpDTO) session.get(
					ExternalEmpDTO.class,
					Integer.valueOf(promotionBean.getNodeID()));
			externalEmpDTO.setStatus(0);
			externalEmpDTO.setLastModifiedBy(promotionBean.getSfID());
			externalEmpDTO.setLastModifiedTime(CPSUtils
					.getCurrentDateWithTime());
			session.saveOrUpdate(externalEmpDTO);
			session.flush();
			promotionBean.setResult(CPSConstants.DELETE);
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ExternalEmpDTO> getExternalBoardList() throws Exception {
		List<ExternalEmpDTO> list = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			list = session.createCriteria(ExternalEmpDTO.class)
					.add(Expression.eq(CPSConstants.STATUS, 1)).list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	@SuppressWarnings("static-access")
	public String saveOfflineEntry(PromotionManagementBean promotionBean)
			throws Exception {
		Session session = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();
			if(CPSUtils.isNullOrEmpty(promotionBean.getNodeID())){  //new code added to update old record to 100 and new record to status 1
				PromoteesEntryDTO pDto=(PromoteesEntryDTO)session.createCriteria(PromoteesEntryDTO.class).add(Expression.eq("sfID", promotionBean.getRefSfid())).add(Expression.eq("status", 1)).uniqueResult();
				if(pDto!=null){
					pDto.setPresentEffectiveDate(CPSUtils.convertStringToDate(CPSUtils.formattedDate(pDto.getPresentEffectiveDate().toString())));
					pDto.setPromotedEffectiveDate(CPSUtils.convertStringToDate(CPSUtils.formattedDate(pDto.getPromotedEffectiveDate().toString())));
					pDto.setLastModifiedDate(CPSUtils.getCurrentDate());
					pDto.setVarIncEndDate(CPSUtils.convertStringToDate(CPSUtils.formattedDate(pDto.getVarIncEndDate().toString())));
					pDto.setSeniorityDate(CPSUtils.formattedDate(pDto.getSeniorityDate()));
					pDto.setReferenceType(pDto.getReferenceType());
					pDto.setStatus(100);
					session.saveOrUpdate(pDto);
					session.flush();	
				}

			}

			PromoteesEntryDTO entryDTO = new PromoteesEntryDTO();
			if (!CPSUtils.isNullOrEmpty(promotionBean.getNodeID())) {
				entryDTO.setId(Integer.valueOf(promotionBean.getNodeID()));
			}
			entryDTO.setSfID(promotionBean.getRefSfid().toUpperCase());
			entryDTO.setPromotedDesignation(promotionBean.getDesignationTo());
			entryDTO.setPromotedEffectiveDate(promotionBean.getPromotionDate());
			// entryDTO.setNewPay(Float.valueOf(promotionBean.getNewBasicPay()));
			entryDTO.setNewGradePay(Float.valueOf(promotionBean
					.getNewGradePay()));
			entryDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
			entryDTO.setCreatedBy(promotionBean.getSfID());
			entryDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
			entryDTO.setLastModifiedBy(promotionBean.getSfID());
			entryDTO.setReferenceType("Offline");
			entryDTO.setTwoAddl(promotionBean.getTwoAddl());
			entryDTO.setVarIncEndDate(CPSUtils.critFormattedDate(promotionBean
					.getVarIncEnd()));
			entryDTO.setStatus(1);
			entryDTO.setVarIncPt(promotionBean.getDesignationFrom());
			entryDTO.setPresentEffectiveDate(promotionBean
					.getPresentEffectivedate());
			entryDTO.setSeniorityDate(promotionBean.getSeniorityDate());
			session.saveOrUpdate(entryDTO);
			message = CPSConstants.SUCCESS;
			// hibernateUtils.closeSession();
			/*
			 * //Newly Added session = hibernateUtils.getSession();
			 * EmpGradePayHistoryDTO gpDTO = new EmpGradePayHistoryDTO();
			 * EmpVarIncHistoryDTO viDTO = new EmpVarIncHistoryDTO();
			 * 
			 * gpDTO.setSfID(promotionBean.getRefSfid().toUpperCase());
			 * gpDTO.setPromotedDesignation(promotionBean.getDesignationTo());
			 * gpDTO.setPromotedEffectiveDate(promotionBean.getPromotionDate());
			 * gpDTO
			 * .setNewGradePay(Float.valueOf(promotionBean.getNewGradePay()));
			 * gpDTO.setTwoAddl(promotionBean.getTwoAddl());
			 * gpDTO.setReferenceType("Offline");
			 * gpDTO.setSeniorityDate(promotionBean.getSeniorityDate());
			 * gpDTO.setStatus(1);
			 * gpDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
			 * gpDTO.setCreatedBy(promotionBean.getSfID());
			 * gpDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
			 * gpDTO.setLastModifiedBy(promotionBean.getSfID());
			 * 
			 * viDTO.setVarIncPt(promotionBean.getDesignationFrom());
			 * viDTO.setVatIncStartDate
			 * (promotionBean.getPresentEffectivedate());
			 * viDTO.setVarIncEndDate(
			 * CPSUtils.critFormattedDate(promotionBean.getVarIncEnd()));
			 * viDTO.setStatus(1);
			 * viDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
			 * viDTO.setCreatedBy(promotionBean.getSfID());
			 * viDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
			 * viDTO.setLastModifiedBy(promotionBean.getSfID());
			 * 
			 * if(!CPSUtils.isNullOrEmpty(promotionBean.getNodeID())){
			 * gpDTO.setId(Integer.valueOf(promotionBean.getNodeID()));
			 * session.saveOrUpdate(gpDTO); viDTO.setGradePayId(gpDTO.getId());
			 * BigDecimal varId = (BigDecimal)session.createSQLQuery(
			 * "select max(id) from EMP_VAR_INC_HISTORY where GRADE_PAY_ID=? and status=1"
			 * ).setInteger(0, viDTO.getGradePayId()).uniqueResult();
			 * if(!CPSUtils.isNullOrEmpty(varId)){
			 * viDTO.setId(varId.intValue()); session.saveOrUpdate(viDTO); }
			 * 
			 * 
			 * }else{ int gpId = (Integer)session.save(gpDTO);
			 * viDTO.setGradePayId(gpId); session.save(viDTO); }
			 * 
			 * message = CPSConstants.SUCCESS;
			 * hibernateUtils.commitTransaction();
			 */

		} catch (Exception e) {
			e.printStackTrace();
			hibernateUtils.rollbackTransaction();
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			hibernateUtils.closeSession();
		}
		return message;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PromoteesEntryDTO> getOfflineEntryList(
			PromotionManagementBean promotionBean) throws Exception {
		Session session = null;
		String sql = "";
		try {
			session = hibernateUtils.getSession();
			/*
			 * promotionBean.setOfflineEntryList(session.createSQLQuery(
			 * "SELECT ppe.id id,EMP.SFID sfID,EMP.NAME_IN_SERVICE_BOOK empName,(select name from DESIGNATION_MASTER where id=PPE.VARIABLE_INCREMENTS) presentDesigName ,ppe.VARIABLE_INCREMENTS presentDesignation,ppe.promoted_designation promotedDesignation,ppe.VAR_INC_EFFECTIVE_DATE presentEffectiveDate,(SELECT NAME FROM DESIGNATION_MASTER WHERE ID=PPE.PROMOTED_DESIGNATION) promotedDesigName,PPE.PROMOTED_EFFECTIVE_DATE promotedEffectiveDate,PPE.BASIC_PAY_ID newPay,PPE.GRADE_PAY newGradePay ,ppe.reference_type referenceType,ppe.TWO_ADDL_INC pay FROM EMP_MASTER EMP,EMP_PAYMENT_DETAILS PAY,DESIGNATION_MASTER DESIG,PRO_RESIDENCY_PERIOD_MASTER PRPM,EMP_GRADE_PAY_HISTORY PPE WHERE EMP.DESIGNATION_ID=DESIG.ID AND EMP.SFID=PAY.SFID AND EMP.STATUS=1 AND PRPM.DESIGNATION_FROM=EMP.DESIGNATION_ID AND PPE.SFID=EMP.SFID order by desig.order_no,ppe.reference_type,EMP.SFID,EMP.NAME_IN_SERVICE_BOOK"
			 * ) .addScalar("id", Hibernate.INTEGER).addScalar("sfID",
			 * Hibernate.STRING).addScalar("empName",
			 * Hibernate.STRING).addScalar("presentDesigName",
			 * Hibernate.STRING).addScalar("promotedDesigName",
			 * Hibernate.STRING).addScalar("presentDesignation",
			 * Hibernate.INTEGER).addScalar("promotedDesignation",
			 * Hibernate.INTEGER).addScalar("presentEffectiveDate",
			 * Hibernate.DATE).addScalar("promotedEffectiveDate",
			 * Hibernate.DATE).addScalar("newPay",
			 * Hibernate.FLOAT).addScalar("newGradePay",
			 * Hibernate.FLOAT).addScalar("referenceType",
			 * Hibernate.STRING).addScalar("pay",
			 * Hibernate.FLOAT).setResultTransformer
			 * (Transformers.aliasToBean(PromoteesEntryDTO.class)).list());
			 */

			// sql="SELECT ppe.id id,EMP.SFID sfID,EMP.NAME_IN_SERVICE_BOOK empName,(select name from DESIGNATION_MASTER where id=PPE.VARIABLE_INCREMENTS) presentDesigName ,ppe.VARIABLE_INCREMENTS presentDesignation,pay.basic_pay basicPay,ppe.promoted_designation promotedDesignation,ppe.VAR_INC_EFFECTIVE_DATE presentEffectiveDate,(SELECT NAME FROM DESIGNATION_MASTER WHERE ID=PPE.PROMOTED_DESIGNATION) promotedDesigName,PPE.PROMOTED_EFFECTIVE_DATE promotedEffectiveDate,PPE.GRADE_PAY newGradePay ,ppe.reference_type referenceType,ppe.TWO_ADDL_INC twoAddl,to_char(ppe.SENIORITY_DATE, 'dd-Mon-yyyy') as seniorityDate,to_char(ppe.VAR_INC_END_DATE, 'dd-Mon-yyyy') as varIncEnd  FROM EMP_MASTER EMP,EMP_BASIC_PAY_HISTORY PAY,DESIGNATION_MASTER DESIG,PRO_RESIDENCY_PERIOD_MASTER PRPM,EMP_GRADE_PAY_HISTORY PPE WHERE EMP.DESIGNATION_ID=DESIG.ID AND EMP.SFID=PAY.SFID AND EMP.STATUS=1 AND PRPM.DESIGNATION_FROM=EMP.DESIGNATION_ID AND PPE.SFID=EMP.SFID and emp.sfid='"+promotionBean.getRefSfid()+"' order by desig.order_no,ppe.reference_type,EMP.SFID,EMP.NAME_IN_SERVICE_BOOK";

			/*
			 * max(id) taken here instead of max(effective_date),which was
			 * corrected in running query
			 * 
			 * select egph.id id,egph.sfid sfID,egph.variable_increments
			 * presentDesignation,egph.var_inc_effective_date
			 * presentEffectiveDate,egph.two_addl_inc
			 * twoAddl,to_char(egph.var_inc_end_date,'dd-Mon-YYYY') varIncEnd,
			 * egph.promoted_designation
			 * promotedDesignation,egph.promoted_effective_date
			 * promotedEffectiveDate,egph.grade_pay
			 * newGradePay,egph.reference_type
			 * referenceType,to_char(egph.seniority_date,'dd-Mon-YYYY')
			 * seniorityDate, nvl((select ebph.basic_pay from
			 * emp_basic_pay_history ebph where ebph.sfid='SF0043' and
			 * ebph.id=(select max(id) from emp_basic_pay_history ebp where
			 * ebp.designation_id=egph.promoted_designation and
			 * ebp.sfid='SF0043')),'0') pay, dm.name
			 * name,emp.NAME_IN_SERVICE_BOOK empName from emp_grade_pay_history
			 * egph,designation_master dm,emp_master emp where
			 * egph.promoted_designation=dm.id and egph.sfid='SF0043' and
			 * egph.sfid=emp.sfid and emp.status=1 and egph.status=1;
			 */
			sql = "select egph.id id,egph.sfid sfID,egph.variable_increments varIncPt,egph.var_inc_effective_date presentEffectiveDate,egph.two_addl_inc twoAddl,egph.var_inc_end_date varIncEndDate,"
					+ "egph.promoted_designation promotedDesignation,egph.promoted_effective_date promotedEffectiveDate,egph.grade_pay newGradePay,egph.reference_type referenceType,to_char(egph.seniority_date,'dd-Mon-YYYY') seniorityDate,"
					+ "nvl((select ebph.basic_pay from emp_basic_pay_history ebph where ebph.sfid=? and ebph.effective_date=(select max(effective_date) from emp_basic_pay_history ebp where ebp.designation_id=egph.promoted_designation and ebp.sfid=? and ebp.status=1)),'0') pay,"
					+ "dm.name name,emp.NAME_IN_SERVICE_BOOK empName from emp_grade_pay_history egph,designation_master dm,emp_master emp where egph.promoted_designation=dm.id and egph.sfid=? and egph.sfid=emp.sfid and emp.status=1 and egph.status=1";

			promotionBean.setOfflineEntryList(session
					.createSQLQuery(sql)
					.addScalar("id", Hibernate.INTEGER)
					.addScalar("sfID", Hibernate.STRING)
					.addScalar("empName", Hibernate.STRING)
					.addScalar("name")
					.addScalar("pay", Hibernate.FLOAT)
					.addScalar("varIncPt", Hibernate.INTEGER)
					.addScalar("promotedDesignation", Hibernate.INTEGER)
					.addScalar("presentEffectiveDate", Hibernate.DATE)
					.addScalar("promotedEffectiveDate", Hibernate.DATE)
					.addScalar("newGradePay", Hibernate.FLOAT)
					.addScalar("referenceType", Hibernate.STRING)
					.addScalar("twoAddl", Hibernate.STRING)
					.addScalar("seniorityDate", Hibernate.STRING)
					.addScalar("varIncEndDate", Hibernate.DATE)
					.setString(0, promotionBean.getRefSfid().toUpperCase())
					.setString(1, promotionBean.getRefSfid().toUpperCase())
					.setString(2, promotionBean.getRefSfid().toUpperCase())
					.setResultTransformer(
							Transformers.aliasToBean(PromoteesEntryDTO.class))
							.list());

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return promotionBean.getOfflineEntryList();
	}

	@Override
	public String checkDuplicateOfflineEntry(
			PromotionManagementBean promotionBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			Criteria crit = session
					.createCriteria(PromoteesEntryDTO.class)
					.add(Expression.eq(CPSConstants.STATUS, 1))
					.add(Expression.eq("sfID", promotionBean.getRefSfid()
							.toUpperCase()))
							.add(Expression.eq("promotedDesignation",
									promotionBean.getDesignationTo()));
			if (!CPSUtils.isNullOrEmpty(promotionBean.getNodeID())) {
				crit = crit.add(Expression.ne(CPSConstants.ID,
						Integer.parseInt(promotionBean.getNodeID())));
			}
			if (CPSUtils.checkList(crit.list())) {
				promotionBean.setResult(CPSConstants.DUPLICATE);
			} else {
				promotionBean.setResult(CPSConstants.SUCCESS);
			}
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public String designationUpdation() throws Exception {
		Session session = null;
		String message = null;
		List<PromotionOfflineEntryBean> offlineList = null;
		try {
			session = hibernateUtils.getSession();
			offlineList = session
					.createSQLQuery(
							"Select Emp.Sfid ,DESIG.NAME designationFrom,Prpm.Designation_to designationTo,Pad.Effective_date effectiveDate From Pro_assessment_details Pad,Emp_master Emp,Pro_residency_period_master Prpm,Designation_master Desig WHERE EMP.SFID=PAD.SFID AND TO_CHAR(PAD.EFFECTIVE_DATE)=TO_DATE(SYSDATE) AND EMP.DESIGNATION_ID=DESIG.ID AND DESIG.ID=PRPM.DESIGNATION_FROM and emp.status=1 and prpm.status=1")
							.addScalar("id", Hibernate.STRING)
							.addScalar("name", Hibernate.STRING)
							.addScalar("sfid", Hibernate.STRING)
							.addScalar("age", Hibernate.STRING)
							.setResultTransformer(
									Transformers.aliasToBean(FamilyBean.class)).list();
			log.debug("Updation list length :::::::::::::" + offlineList.size());
			for (PromotionOfflineEntryBean eachRow : offlineList) {
				log.debug("Updating designation and seniority date field values in emp_master where sfid= "
						+ eachRow.getSfid()
						+ ",designationFrom= "
						+ eachRow.getDesignationFrom()
						+ ",designationTo= "
						+ eachRow.getDesignationTo());
				int i = session
						.createQuery(
								"update EmployeeBean set designation=?,seniorityDate=?,lastModifiedDate=sysdate where userSfid=?")
								.setInteger(0, eachRow.getDesignationTo())
								.setDate(1, eachRow.getEffectiveDate())
								.setString(2, eachRow.getSfid()).executeUpdate();
				log.debug("no fo rows updated :::" + i);
			}
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return message;

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PromotionOfflineEntryBean> getCatwiseDesig(String refSfid)
			throws Exception {
		List<PromotionOfflineEntryBean> list = null;
		Session session = null;
		try {
			String query = "SELECT CM1.ID id,DESIG.ID designationID,DESIG.NAME desigName FROM CATEGORY_MASTER CM1,DESIGNATION_MASTER DESIG,DESIGNATION_MAPPINGS DMAP1 WHERE CM1.ID=(SELECT CM.ID FROM CATEGORY_MASTER CM,DESIGNATION_MASTER DM,DESIGNATION_MAPPINGS DMAP,EMP_MASTER EMP WHERE DMAP.DESIG_ID=DM.ID AND CM.ID=DMAP.CATEGORY_ID AND CM.STATUS=1 and DM.STATUS=1 AND EMP.DESIGNATION_ID=dm.id and emp.sfid=?) AND DMAP1.CATEGORY_ID=CM1.ID and DMAP1.DESIG_ID=desig.id and DESIG.STATUS=1 and CM1.STATUS=1";
			session = hibernateUtils.getSession();
			list = session
					.createSQLQuery(query)
					.addScalar("id", Hibernate.INTEGER)
					.addScalar("designationID", Hibernate.INTEGER)
					.addScalar("desigName", Hibernate.STRING)
					.setResultTransformer(
							Transformers
							.aliasToBean(PromotionOfflineEntryBean.class))
							.setString(0, refSfid.toUpperCase()).list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public String getBasicPay(PromotionManagementBean promotionBean)
			throws Exception {
		Session session = null;
		KeyDTO kDTO = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();

			String qry = "select ebph.basic_pay as key from emp_basic_pay_history ebph "
					+ " where ebph.sfid='"
					+ promotionBean.getRefSfid().toUpperCase()
					+ "' and ebph.effective_date=(select max(effective_date) from emp_basic_pay_history ebp "
					+ " where  ebp.sfid='"
					+ promotionBean.getRefSfid().toUpperCase()
					+ "' and ebp.status=1)" + " and ebph.status=1";

			kDTO = (KeyDTO) session
					.createSQLQuery(qry)
					.addScalar("key", Hibernate.STRING)
					.setResultTransformer(
							Transformers.aliasToBean(KeyDTO.class))
							.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		if(kDTO!=null){
			return kDTO.getKey();
		}else{
			return message;
			//promotionBean.setBasicPay(kDTO.get)
		}
		//return kDTO.getKey();
	}

	@Override
	public String getVarIncr(int gradepay, int varIncrPts) throws Exception {
		Session session = null;
		String varIncr = "";
		try {
			session = hibernateUtils.getSession();

			String query = "select calculate_var_incr(?,?) from dual";

			varIncr = session.createSQLQuery(query).setFloat(0, gradepay)
					.setFloat(1, varIncrPts).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		}
		return varIncr;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PromotionsDisciplineDTO> getCategoryDisciplineList(
			PromotionManagementBean promotionBean) throws Exception {
		List<PromotionsDisciplineDTO> list = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			list = session
					.createCriteria(PromotionsDisciplineDTO.class)
					.add(Expression.eq(CPSConstants.STATUS, 1))
					.add(Expression.eq("categoryID",
							promotionBean.getAssessmentTypeID()))
							.addOrder(Order.asc("shortForm")).list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PromotionsSubDisciplineDTO> getCategorySubDisciplineList(
			PromotionManagementBean promotionBean) throws Exception {
		List<PromotionsSubDisciplineDTO> list = null;
		Session session = null;
		try {
			String query = "select psdm.id,psdm.name from pro_discipline_master pdm,pro_sub_discipline_master psdm where psdm.discipline_id=pdm.id and pdm.category_id=? and pdm.status=1 and psdm.status=1 order by psdm.code";
			session = hibernateUtils.getSession();
			list = session
					.createSQLQuery(query)
					.addScalar("id", Hibernate.INTEGER)
					.addScalar("name", Hibernate.STRING)
					.setResultTransformer(
							Transformers
							.aliasToBean(PromotionsSubDisciplineDTO.class))
							.setInteger(0, promotionBean.getAssessmentTypeID()).list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DoPartDTO> getTypeDoPartList(String gazettedType)
			throws Exception {
		Session session = null;
		List<DoPartDTO> list = null;
		try {
			session = hibernateUtils.getSession();
			list = session
					.createCriteria(DoPartDTO.class)
					.add(Expression.eq(CPSConstants.STATUS, 1))
					.add(Expression.eq("typeId", Integer.parseInt(gazettedType)))
					.addOrder(Order.desc("doPartDate")).list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CasualityDetailsDTO> getModuleSpecificCasuality(
			PromotionManagementBean promotionBean) throws Exception {
		Session session = null;
		List<CasualityDetailsDTO> list = null;
		try {
			session = hibernateUtils.getSession();
			list = session
					.createCriteria(CasualityDetailsDTO.class)
					.add(Expression.eq(CPSConstants.STATUS, 1))
					.add(Expression.eq("moduleId", promotionBean.getModuleId()))
					.add(Expression.eq("typeId", CPSUtils
							.convertToInteger(promotionBean.getGazettedType())))
							.addOrder(Order.asc("orderBy")).list();
		} catch (Exception e) {
			throw e;
		}
		// System.out.println("id is:" + list.get(0).getCode());
		return list;

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DoPartDTO> getGazTypeDoPartList(
			PromotionManagementBean promotionBean) throws Exception {
		List<DoPartDTO> list = null;
		Session session = null;
		try {
			String query = "SELECT rnd.REF_NUMBER as doPartNo,rnd.REF_DATE as doPartDate1 ,rnd.ID as id ,rnd.type_id as type,nvl((select distinct ppf.increments_dopart_id from pro_pay_fixation ppf  where ppf.increments_dopart_id in(rnd.id)),0) incrementsDoPartId,nvl((select distinct ppf.fixation_dopart_id from pro_pay_fixation ppf  where ppf.fixation_dopart_id in(rnd.id)),0) fixationDoPartId,dptm.name as gazettedType FROM REFERENCE_NUMBER_DETAILS rnd,DOPART_II_TYPE_MASTER dptm WHERE rnd.ID IN ((SELECT fixation_dopart_id dopart_id FROM pro_pay_fixation) UNION (SELECT increments_dopart_id FROM pro_pay_fixation)) and RND.TYPE_ID =DPTM.ID order by rnd.TYPE_ID";                                      //it get all records irrespective release date

			//			String query = "SELECT rnd.REF_NUMBER as doPartNo,rnd.REF_DATE as doPartDate1 ,rnd.ID as id ,rnd.type_id as type,nvl((select distinct ppf.increments_dopart_id from pro_pay_fixation ppf  where ppf.increments_dopart_id in(rnd.id)),0) incrementsDoPartId,nvl((select distinct ppf.fixation_dopart_id from pro_pay_fixation ppf  where ppf.fixation_dopart_id in(rnd.id)),0) fixationDoPartId,dptm.name as gazettedType FROM REFERENCE_NUMBER_DETAILS rnd,DOPART_II_TYPE_MASTER dptm WHERE rnd.ID IN ((SELECT fixation_dopart_id dopart_id FROM pro_pay_fixation) UNION (SELECT increments_dopart_id FROM pro_pay_fixation)) and RND.TYPE_ID =DPTM.ID AND rnd.released_date is not null order by rnd.TYPE_ID";      //it get all records which have release date 


			session = hibernateUtils.getSession();
			list = session
					.createSQLQuery(query)
					.addScalar("doPartNo", Hibernate.STRING)
					.addScalar("doPartDate1", Hibernate.DATE)
					.addScalar("id", Hibernate.INTEGER)
					.addScalar("gazettedType", Hibernate.STRING)
					.addScalar("type", Hibernate.STRING)
					.addScalar("fixationDoPartId", Hibernate.INTEGER)
					.addScalar("incrementsDoPartId", Hibernate.INTEGER)
					.setResultTransformer(
							Transformers.aliasToBean(DoPartBean.class)).list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<AssessmentDetailsDTO> getPublishedDoList(
			PromotionManagementBean promotionBean) throws Exception {
		List<AssessmentDetailsDTO> list = null;
		Session session = null;
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("select PPF.FIXATION_ACCEPTED_DATE fixationAcceptedDate,PAD.id assessmentID,EMP.DESIGNATION_ID designationID,EMP.SFID sfID,EMP.NAME_IN_SERVICE_BOOK empName,EMP.SENIORITY_DATE seniorityDate,case when PPF.id is null then 0 else PPF.id end id,PAD.PROMOTION_DATE promotionDate,PAD.EFFECTIVE_DATE effectiveDate,case when PPF.PAY is null then (select case when BASIC_PAY is null then 0 else BASIC_PAY end from EMP_PAYMENT_DETAILS where SFID=EMP.SFID)||'' else PPF.PAY end basicPay,PPF.NEW_PAY newBasicPay,case when PPF.GRADE_PAY is null then (select case when GRADE_PAY is null then 0 else GRADE_PAY end from EMP_PAYMENT_DETAILS where SFID=EMP.SFID)||'' else PPF.GRADE_PAY end gradePay,PPF.NEW_GRADE_PAY newGradePay,case when PPF.TWO_ADD_INCR is null then (select TWO_ADDL_INCR from EMP_PAYMENT_DETAILS where SFID=EMP.SFID)||'' else PPF.TWO_ADD_INCR end  twoAddIncr,PPF.NEW_TWO_ADD_INCR NEWTWOADDINCR from PRO_ASSESSMENT_DETAILS PAD,EMP_MASTER EMP,DESIGNATION_MASTER DESIG,PRO_PAY_FIXATION PPF,PRO_RESIDENCY_PERIOD_MASTER PRPM  where  PAD.SFID=EMP.SFID   and DESIG.STATUS=1 and PAD.DESIGNATION_ID=DESIG.id and PAD.id=PPF.ASSESSMENT_ID(+) and PAD.PROMOTION_DATE is not null and PRPM.DESIGNATION_FROM=DESIG.id and PRPM.STATUS=1 and PPF.FIXATION_DOPART_ID=?");
			session = hibernateUtils.getSession();
			list = session
					.createSQLQuery(sb.toString())
					.addScalar("fixationAcceptedDate", Hibernate.DATE)
					.addScalar("assessmentID", Hibernate.INTEGER)
					.addScalar("designationID", Hibernate.INTEGER)
					.addScalar("sfID", Hibernate.STRING)
					.addScalar("empName", Hibernate.STRING)
					.addScalar("seniorityDate", Hibernate.DATE)
					.addScalar("id", Hibernate.INTEGER)
					.addScalar("promotionDate", Hibernate.DATE)
					.addScalar("effectiveDate", Hibernate.DATE)
					.addScalar("basicPay", Hibernate.STRING)
					.addScalar("newBasicPay", Hibernate.STRING)
					.addScalar("gradePay", Hibernate.STRING)
					.addScalar("newGradePay", Hibernate.STRING)
					.addScalar("twoAddIncr", Hibernate.STRING)
					.addScalar("newTwoAddIncr", Hibernate.STRING)
					.setResultTransformer(
							Transformers
							.aliasToBean(AssessmentDetailsDTO.class))
							.setInteger(0,
									Integer.parseInt(promotionBean.getDoPartNumber()))
									.list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<AssessmentDetailsDTO> getPublishedVarDoList(
			PromotionManagementBean promotionBean) throws Exception {
		List<AssessmentDetailsDTO> list = null;
		Session session = null;
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("select PPF.INCREMENTS_ACCEPTED_DATE incrementsAccepteddate,PPF.VARIABLE_INCREMENTS variableIncr,PAD.id ASSESSMENTID,EMP.DESIGNATION_ID DESIGNATIONID,EMP.SFID SFID,EMP.NAME_IN_SERVICE_BOOK EMPNAME,EMP.SENIORITY_DATE SENIORITYDATE,case when PPF.id is null then 0 else PPF.id end id,PAD.PROMOTION_DATE PROMOTIONDATE,PAD.EFFECTIVE_DATE EFFECTIVEDATE,case when PPF.PAY is null then (select case when BASIC_PAY is null then 0 else BASIC_PAY end from EMP_PAYMENT_DETAILS where SFID=EMP.SFID)||'' else PPF.PAY end BASICPAY,PPF.NEW_PAY NEWBASICPAY,case when PPF.GRADE_PAY is null then (select case when GRADE_PAY is null then 0 else GRADE_PAY end from EMP_PAYMENT_DETAILS where SFID=EMP.SFID)||'' else PPF.GRADE_PAY end GRADEPAY,PPF.NEW_GRADE_PAY NEWGRADEPAY,case when PPF.TWO_ADD_INCR is null then (select TWO_ADDL_INCR from EMP_PAYMENT_DETAILS where SFID=EMP.SFID)||'' else PPF.TWO_ADD_INCR end  TWOADDINCR,PPF.NEW_TWO_ADD_INCR NEWTWOADDINCR from PRO_ASSESSMENT_DETAILS PAD,EMP_MASTER EMP,DESIGNATION_MASTER DESIG,PRO_PAY_FIXATION PPF,PRO_RESIDENCY_PERIOD_MASTER PRPM  where  PAD.SFID=EMP.SFID   and DESIG.STATUS=1 and PAD.DESIGNATION_ID=DESIG.id and PAD.id=PPF.ASSESSMENT_ID(+) and PAD.PROMOTION_DATE is not null and PRPM.DESIGNATION_FROM=DESIG.id and PRPM.STATUS=1 and PPF.INCREMENTS_DOPART_ID=?");
			session = hibernateUtils.getSession();
			list = session
					.createSQLQuery(sb.toString())
					.addScalar("incrementsAccepteddate", Hibernate.DATE)
					.addScalar("variableIncr", Hibernate.STRING)
					.addScalar("assessmentID", Hibernate.INTEGER)
					.addScalar("designationID", Hibernate.INTEGER)
					.addScalar("sfID", Hibernate.STRING)
					.addScalar("empName", Hibernate.STRING)
					.addScalar("seniorityDate", Hibernate.DATE)
					.addScalar("id", Hibernate.INTEGER)
					.addScalar("promotionDate", Hibernate.DATE)
					.addScalar("effectiveDate", Hibernate.DATE)
					.addScalar("basicPay", Hibernate.STRING)
					.addScalar("newBasicPay", Hibernate.STRING)
					.addScalar("gradePay", Hibernate.STRING)
					.addScalar("newGradePay", Hibernate.STRING)
					.addScalar("twoAddIncr", Hibernate.STRING)
					.addScalar("newTwoAddIncr", Hibernate.STRING)
					.setResultTransformer(
							Transformers
							.aliasToBean(AssessmentDetailsDTO.class))
							.setInteger(0,
									Integer.parseInt(promotionBean.getDoPartNumber()))
									.list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public String submitFinanceAcceptanceDetails(
			PromotionManagementBean promotionBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			String[] rows = promotionBean.getRowValues().split(",");
			for (String row : rows) {
				String[] rvalues = row.split("#");

				PayFixationDTO payFixationDTO = (PayFixationDTO) session
						.createCriteria(PayFixationDTO.class)
						.add(Expression.eq("id", Integer.valueOf(rvalues[0])))
						.add(Expression.eq(CPSConstants.STATUS, 1))
						.uniqueResult();
				if (!CPSUtils.isNullOrEmpty(rvalues[4])) {
					payFixationDTO.setFixationAcceptedDateFinance(CPSUtils
							.convertStringToDate(rvalues[4]));
					payFixationDTO.setIncrementsAccepteddate(CPSUtils.convertStringToDate(rvalues[4]));      //Here accepted date same
				}
				if (!CPSUtils.isNullOrEmpty(rvalues[5])) {
					payFixationDTO.setIncrementsAccepteddate(CPSUtils

							.convertStringToDate(rvalues[5]));
				}
				session.saveOrUpdate(payFixationDTO);
				session.flush();
			}
			promotionBean.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public String deleteRecord(PromotionManagementBean promotionBean)
			throws Exception {
		/*
		 * Session session = null; String message="";
		 * 
		 * try { session = hibernateUtils.getSession(); Query
		 * qry=session.createQuery
		 * ("update EmpGradePayHistoryDTO set status=? where id=?");
		 * qry.setInteger(0,0);
		 * qry.setInteger(1,Integer.parseInt(promotionBean.getNodeID()));
		 * qry.executeUpdate(); session.createSQLQuery(
		 * "update EMP_VAR_INC_HISTORY set status=0 where GRADE_PAY_ID=?"
		 * ).setInteger
		 * (0,Integer.parseInt(promotionBean.getNodeID())).executeUpdate();
		 * promotionBean.setResult(CPSConstants.DELETE);
		 * hibernateUtils.closeSession(); hibernateUtils.getSession(); } catch
		 * (Exception e) { e.printStackTrace(); throw e; } return
		 * promotionBean.getResult();
		 */
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();
			Query qry = session
					.createQuery("update PromoteesEntryDTO set status=? where id=?");
			qry.setInteger(0, 0);
			qry.setInteger(1, Integer.parseInt(promotionBean.getNodeID()));
			qry.executeUpdate();
			promotionBean.setResult(CPSConstants.DELETE);

		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();

	}

	@Override
	@SuppressWarnings("unchecked")
	public PromotionManagementBean getpromotionCasualityList(
			PromotionManagementBean promotionBean) throws Exception {

		Session session = null;
		try {
			session = hibernateUtils.getSession();
			promotionBean.setCasualitiesList(session
					.createCriteria(CasualityDetailsDTO.class)
					.add(Expression.eq(CPSConstants.STATUS, 1))
					.add(Expression.eq("moduleId",
							Integer.valueOf(CPSConstants.PROMOTIONMODULEID)))
							.add(Expression.eq("typeId",
									Integer.parseInt(promotionBean.getGazettedType())))
									.addOrder(Order.asc("orderBy")).list());
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;

	}

	@Override
	@SuppressWarnings("unchecked")
	public PromotionManagementBean getEmpDetails(
			PromotionManagementBean promotionBean) throws Exception {
		//
		Session session = null;
		StringBuilder sb = new StringBuilder();
		try {
			// commit transaction & close the session

			session = hibernateUtils.getSession();
			/*promotionBean.setCasualityId("2");                  //This line added for testing it will remove after some time
			if (promotionBean.getCasualityId().equals("2")) {  */       // It could not required the screen has convert into single screen as variableIncrment and pay fixation                    
			//				sb.append("SELECT case when PPF.id is null then 0 else PPF.id end id,ppf.fixation_accepted_date_finance fixationAcceptedDateFinance,ppf.fixation_dopart_id fixationDoPartId,PAD.id assessmentID,EM.SFID sfID,EM.NAME_IN_SERVICE_BOOK empName,DM.NAME designation,dm.id designationID,DEPT.DEPARTMENT_NAME department,PAD.PROMOTION_DATE promotionDate,PAD.EFFECTIVE_DATE effectiveDate,PPF.PAY basicPay,PPF.NEW_PAY newBasicPay,PPF.GRADE_PAY gradePay,PPF.NEW_GRADE_PAY newGradePay,PPF.NEW_TWO_ADD_INCR newTwoAddIncr,PPF.FIXATION_ACCEPTED_DATE fixationAcceptedDate FROM PRO_ASSESSMENT_DETAILS PAD,EMP_MASTER EM,DESIGNATION_MASTER DM,DESIGNATION_MAPPINGS DMAP,CATEGORY_MASTER CM,ORG_ROLE_INSTANCE ORI,DEPARTMENTS_MASTER DEPT,PRO_PAY_FIXATION PPF,PRO_RESIDENCY_PERIOD_MASTER PRPM ,DOPART_II_TYPE_MASTER DTM,DOPART_II_TYPE_DESIG_MASTER DTDM WHERE PAD.SFID=EM.SFID AND EM.STATUS=1 AND PAD.STATUS=53 AND EM.DESIGNATION_ID=DM.ID AND DM.STATUS=1 AND ORI.STATUS=1 AND  DEPT.STATUS=1 AND ORI.ORG_ROLE_ID=EM.OFFICE_ID AND ORI.DEPARTMENT_ID=DEPT.DEPARTMENT_ID AND PAD.ID=PPF.ASSESSMENT_ID(+) AND PAD.PROMOTION_DATE IS NOT NULL AND PRPM.DESIGNATION_TO=DM.ID AND PRPM.STATUS=1 AND DTM.ID=DTDM.TYPE_ID AND PAD.DESIGNATION_ID=DTDM.DESIGNATION_ID AND DTDM.STATUS=1 AND DTM.STATUS=1  AND DM.ID=DMAP.DESIG_ID AND CM.ID=DMAP.CATEGORY_ID and ppf.fixation_dopart_id=? and ppf.fixation_dopart_id in (select id from REFERENCE_NUMBER_DETAILS ) and ppf.fixation_accepted_date_finance is null ORDER BY PPF.INCREMENTS_ACCEPTED_DATE DESC,PPF.FIXATION_ACCEPTED_DATE DESC,PPF.GRADE_PAY DESC");    //1@query

			/*sb.append("SELECT case when PPF.id is null then 0 else PPF.id end id, ppf.variable_increments variableIncr, ppf.VARIABLE_INCR_VALUE varIncrValue,PPF.INCREMENTS_ACCEPTED_DATE incrementsAccepteddate, ppf.var_inc_end_date varIncEndDate,ppf.fixation_accepted_date_finance fixationAcceptedDateFinance,ppf.fixation_dopart_id fixationDoPartId,PAD.id assessmentID,EM.SFID sfID,EM.NAME_IN_SERVICE_BOOK empName,DM.NAME designation,dm.id designationID,DEPT.DEPARTMENT_NAME department,PAD.PROMOTION_DATE promotionDate,PAD.EFFECTIVE_DATE effectiveDate,PPF.PAY basicPay,PPF.NEW_PAY newBasicPay,PPF.GRADE_PAY gradePay,PPF.NEW_GRADE_PAY newGradePay,PPF.NEW_TWO_ADD_INCR newTwoAddIncr,PPF.FIXATION_ACCEPTED_DATE fixationAcceptedDate FROM PRO_ASSESSMENT_DETAILS PAD,EMP_MASTER EM,DESIGNATION_MASTER DM,DESIGNATION_MAPPINGS DMAP,CATEGORY_MASTER CM,ORG_ROLE_INSTANCE ORI,DEPARTMENTS_MASTER DEPT,PRO_PAY_FIXATION PPF,PRO_RESIDENCY_PERIOD_MASTER PRPM ,DOPART_II_TYPE_MASTER DTM,DOPART_II_TYPE_DESIG_MASTER DTDM WHERE PAD.SFID=EM.SFID AND EM.STATUS=1 AND PAD.STATUS=53 AND EM.DESIGNATION_ID=DM.ID AND DM.STATUS=1 AND ORI.STATUS=1 AND  DEPT.STATUS=1 AND ORI.ORG_ROLE_ID=EM.OFFICE_ID AND ORI.DEPARTMENT_ID=DEPT.DEPARTMENT_ID AND PAD.ID=PPF.ASSESSMENT_ID(+) AND PAD.PROMOTION_DATE IS NOT NULL AND PRPM.DESIGNATION_TO=DM.ID AND PRPM.STATUS=1 AND DTM.ID=DTDM.TYPE_ID AND PAD.DESIGNATION_ID=DTDM.DESIGNATION_ID AND DTDM.STATUS=1 AND DTM.STATUS=1  AND DM.ID=DMAP.DESIG_ID AND CM.ID=DMAP.CATEGORY_ID and (ppf.fixation_dopart_id=? OR ppf.increments_dopart_id  =? )and ppf.fixation_dopart_id in (select id from REFERENCE_NUMBER_DETAILS )  "
						+ "and  ppf.increments_dopart_id IN (SELECT id FROM REFERENCE_NUMBER_DETAILS)and (ppf.fixation_accepted_date_finance is null OR ppf.increments_accepted_date IS NULL) ORDER BY PPF.INCREMENTS_ACCEPTED_DATE DESC,PPF.FIXATION_ACCEPTED_DATE DESC,PPF.GRADE_PAY DESC");
			 */   //(2)This old query it couldn't give all the dopart results . it has modified
			sb.append("SELECT CASE WHEN PPF.id IS NULL THEN 0 ELSE PPF.id END id,ppf.variable_increments variableIncr,ppf.VARIABLE_INCR_VALUE varIncrValue,PPF.INCREMENTS_ACCEPTED_DATE incrementsAccepteddate,   ppf.var_inc_end_date varIncEndDate,   ppf.fixation_accepted_date_finance fixationAcceptedDateFinance,   ppf.fixation_dopart_id fixationDoPartId,   PAD.id assessmentID,   EM.SFID sfID,   EM.NAME_IN_SERVICE_BOOK empName,   DM.NAME designation,   dm.id designationID,   DEPT.DEPARTMENT_NAME department,   PAD.PROMOTION_DATE promotionDate,   PAD.EFFECTIVE_DATE effectiveDate,   PPF.PAY basicPay,   PPF.NEW_PAY newBasicPay,   PPF.GRADE_PAY gradePay,   PPF.NEW_GRADE_PAY newGradePay,   PPF.NEW_TWO_ADD_INCR newTwoAddIncr,   PPF.FIXATION_ACCEPTED_DATE fixationAcceptedDate FROM PRO_ASSESSMENT_DETAILS PAD,   EMP_MASTER EM,   DESIGNATION_MASTER DM,   DESIGNATION_MAPPINGS DMAP,   CATEGORY_MASTER CM,   ORG_ROLE_INSTANCE ORI,   DEPARTMENTS_MASTER DEPT,   PRO_PAY_FIXATION PPF,   PRO_RESIDENCY_PERIOD_MASTER PRPM   WHERE PAD.SFID              =EM.SFID AND EM.STATUS               =1 AND PAD.STATUS              in (53,55) AND EM.DESIGNATION_ID       =DM.ID AND DM.STATUS               =1 AND ORI.STATUS              =1 AND DEPT.STATUS             =1 AND ORI.ORG_ROLE_ID         =EM.OFFICE_ID AND ORI.DEPARTMENT_ID       =DEPT.DEPARTMENT_ID AND PAD.ID          =PPF.ASSESSMENT_ID(+) AND PAD.PROMOTION_DATE     IS NOT NULL AND PRPM.DESIGNATION_TO     =DM.ID AND PRPM.STATUS             =1 AND DM.ID                   =DMAP.DESIG_ID AND CM.ID                   =DMAP.CATEGORY_ID AND (ppf.fixation_dopart_id =? OR ppf.increments_dopart_id =?) AND ( ppf.fixation_dopart_id IN   (SELECT id FROM REFERENCE_NUMBER_DETAILS   ) OR ppf.increments_dopart_id IN   (SELECT id FROM REFERENCE_NUMBER_DETAILS   )) AND (ppf.fixation_accepted_date_finance IS NULL OR ppf.increments_accepted_date         IS NULL) ORDER BY PPF.INCREMENTS_ACCEPTED_DATE DESC,   PPF.FIXATION_ACCEPTED_DATE DESC,   PPF.GRADE_PAY DESC");

			promotionBean
			.setAssessmentDetails(session
					.createSQLQuery(sb.toString())
					.addScalar("id", Hibernate.INTEGER)
					.addScalar("variableIncr", Hibernate.INTEGER)     //here i added four scalar
					.addScalar("varIncrValue", Hibernate.INTEGER)
					.addScalar("incrementsAccepteddate",Hibernate.DATE)
					.addScalar("varIncEndDate", Hibernate.DATE)
					.addScalar("fixationDoPartId",
							Hibernate.INTEGER)
							.addScalar("fixationAcceptedDateFinance",
									Hibernate.DATE)
									.addScalar("assessmentID", Hibernate.INTEGER)
									.addScalar("sfID")
									.addScalar("empName")
									.addScalar("designationID", Hibernate.INTEGER)
									.addScalar("designation")
									.addScalar("department")
									.addScalar("promotionDate", Hibernate.DATE)
									.addScalar("effectiveDate", Hibernate.DATE)
									.addScalar("basicPay")
									.addScalar("gradePay")
									.addScalar("newBasicPay")
									.addScalar("newGradePay")
									.addScalar("newTwoAddIncr")
									.addScalar("fixationAcceptedDate",
											Hibernate.DATE)
											.setInteger(
													0,
													Integer.valueOf(promotionBean
															.getDoPartNo().split("#")[0]))
															.setInteger(
																	1,
																	Integer.valueOf(promotionBean
																			.getDoPartNo().split("#")[0]))
																			.setResultTransformer(
																					Transformers
																					.aliasToBean(AssessmentDetailsDTO.class))
																					.list());
			/*	}*/
			/*if (promotionBean.getCasualityId().equals("3")) {
				sb.append("SELECT case when PPF.id is null then 0 else PPF.id end id,ppf.increments_accepted_date incrementsAccepteddate,ppf.increments_dopart_id incrementsDoPartId,PAD.id assessmentID,EM.SFID sfID,EM.NAME_IN_SERVICE_BOOK empName,DM.NAME designation,dm.id designationID,DEPT.DEPARTMENT_NAME department,PAD.PROMOTION_DATE promotionDate,PAD.EFFECTIVE_DATE effectiveDate,PPF.PAY basicPay,PPF.NEW_PAY newBasicPay,PPF.GRADE_PAY gradePay,PPF.NEW_GRADE_PAY newGradePay,PPF.NEW_TWO_ADD_INCR newTwoAddIncr,PPF.INCREMENTS_ACCEPTED_DATE incrementsAccepteddate,PPF.VARIABLE_INCREMENTS variableIncr,ppf.var_inc_end_date varIncEndDate FROM PRO_ASSESSMENT_DETAILS PAD,EMP_MASTER EM,DESIGNATION_MASTER DM,DESIGNATION_MAPPINGS DMAP,CATEGORY_MASTER CM,ORG_ROLE_INSTANCE ORI,DEPARTMENTS_MASTER DEPT,PRO_PAY_FIXATION PPF,PRO_RESIDENCY_PERIOD_MASTER PRPM ,DOPART_II_TYPE_MASTER DTM,DOPART_II_TYPE_DESIG_MASTER DTDM WHERE PAD.SFID=EM.SFID AND EM.STATUS=1 AND PAD.STATUS=53 AND EM.DESIGNATION_ID=DM.ID AND DM.STATUS=1 AND ORI.STATUS=1 AND  DEPT.STATUS=1 AND ORI.ORG_ROLE_ID=EM.OFFICE_ID AND ORI.DEPARTMENT_ID=DEPT.DEPARTMENT_ID AND PAD.ID=PPF.ASSESSMENT_ID(+) AND PAD.PROMOTION_DATE IS NOT NULL AND PRPM.DESIGNATION_TO=DM.ID AND PRPM.STATUS=1 AND DTM.ID=DTDM.TYPE_ID AND PAD.DESIGNATION_ID=DTDM.DESIGNATION_ID AND DTDM.STATUS=1 AND DTM.STATUS=1  AND DM.ID=DMAP.DESIG_ID AND CM.ID=DMAP.CATEGORY_ID and ppf.increments_dopart_id=? and ppf.increments_dopart_id in (select id from REFERENCE_NUMBER_DETAILS) and ppf.increments_accepted_date is null ORDER BY PPF.INCREMENTS_ACCEPTED_DATE DESC,PPF.FIXATION_ACCEPTED_DATE DESC,PPF.GRADE_PAY DESC");
				promotionBean
						.setVarAssessmentDetails(session
								.createSQLQuery(sb.toString())
								.addScalar("id", Hibernate.INTEGER)
								.addScalar("incrementsDoPartId",
										Hibernate.INTEGER)
								.addScalar("incrementsAccepteddate",
										Hibernate.DATE)
								.addScalar("assessmentID", Hibernate.INTEGER)
								.addScalar("sfID")
								.addScalar("empName")
								.addScalar("designationID", Hibernate.INTEGER)
								.addScalar("designation")
								.addScalar("department")
								.addScalar("promotionDate", Hibernate.DATE)
								.addScalar("effectiveDate", Hibernate.DATE)
								.addScalar("basicPay")
								.addScalar("gradePay")
								.addScalar("newBasicPay")
								.addScalar("newGradePay")
								.addScalar("newTwoAddIncr")
								.addScalar("incrementsAccepteddate",
										Hibernate.DATE)
								.addScalar("variableIncr", Hibernate.INTEGER)
								.addScalar("varIncEndDate", Hibernate.DATE)
								.setInteger(
										0,
										Integer.valueOf(promotionBean
												.getDoPartNo().split("#")[0]))
								.setResultTransformer(
										Transformers
												.aliasToBean(AssessmentDetailsDTO.class))
								.list());
			}*/ // It could not required the screen has convert into single screen as variableIncrment and pay fixation
			// sb.append(
			// "Select ppf.INCREMENTS_ACCEPTED_DATE incrementsAccepteddate,ppf.VAR_INC_END_DATE varIncEndDate,ppf.variable_increments variableIncr,ppf.FIXATION_ACCEPTED_DATE fixationAcceptedDate,pad.variable_incr variableIncr,Pad.Status,Pad.Id Assessmentid,emp.designation_id designationID,Emp.Sfid Sfid,Emp.Name_in_service_book Empname,EMP.SENIORITY_DATE seniorityDate,(SELECT NAME FROM DESIGNATION_MASTER WHERE ID=PRPM.DESIGNATION_TO) designationTo,Desig.Name Designation,Dept.Department_name Department,case when ppf.id is null then 0 else ppf.id end id,pad.promotion_date Promotiondate,Pad.Effective_date Effectivedate,Case When Ppf.Pay Is Null Then (Select Case When Basic_pay Is Null Then 0 Else Basic_pay End "
			// +
			// "from emp_payment_details where sfid=emp.sfid)||'' else ppf.pay end basicPay,Ppf.New_pay Newbasicpay,Case When Ppf.Grade_pay Is Null Then (Select Case When Grade_pay Is Null Then 0 Else Grade_pay End from emp_payment_details where sfid=emp.sfid)||'' else ppf.grade_pay end gradePay,ppf.new_grade_pay newGradePay,Case When Ppf.Two_add_incr Is Null Then "
			// +
			// "(Select Two_addl_incr From Emp_payment_details Where Sfid=Emp.Sfid)||'' Else Ppf.Two_add_incr End  Twoaddincr,Ppf.New_two_add_incr Newtwoaddincr From Pro_assessment_details Pad,Emp_master Emp,Designation_master Desig,designation_mappings dmap,category_master cm,Org_role_instance Ori,Departments_master Dept,Pro_pay_fixation Ppf,PRO_RESIDENCY_PERIOD_MASTER PRPM ,dopart_ii_type_master dtm,dopart_ii_type_desig_master dtdm Where  Pad.Sfid=Emp.Sfid And Pad.Assessment_category_id=? ");
			// if(promotionBean.getYearID()!=0){
			// sb.append("And ((Pad.Year_id="+promotionBean.getYearID()+" And  Pad.Status =53) Or (To_char(Pad.Promotion_date,'yyyy')=(select name from year_master where id="+promotionBean.getYearID()+") and Pad.Status=55)) ");
			// }
			//
			// if(!CPSUtils.isNull(promotionBean.getGazettedType())){
			// sb.append(" and dtdm.type_id="+promotionBean.getGazettedType()+"");
			// }
			// if(promotionBean.getAssessmentTypeID()!=0){
			// sb.append(" and cm.id="+promotionBean.getAssessmentTypeID()+"");
			// }
			// sb.append(" and Desig.Status=1 And pad.Designation_id=Desig.Id And Ori.Status=1 And  Dept.Status=1 And Ori.Org_role_id=Emp.Office_id And Ori.Department_id=Dept.Department_id And Pad.Id=Ppf.Assessment_id(+) and Pad.Promotion_date Is Not Null and PRPM.DESIGNATION_FROM=DESIG.ID and prpm.status=1 and dtm.id=dtdm.type_id and pad.designation_id=dtdm.designation_id and dtdm.status=1 and dtm.status=1  and desig.id=dmap.desig_id and cm.id=dmap.category_id order by ppf.INCREMENTS_ACCEPTED_DATE desc,ppf.FIXATION_ACCEPTED_DATE desc,ppf.grade_pay desc");
			// promotionBean.setAssessmentDetails(session.createSQLQuery(sb.toString())
			// .addScalar("incrementsAccepteddate",Hibernate.DATE).addScalar("varIncEndDate",Hibernate.DATE).addScalar("variableIncr",Hibernate.STRING).addScalar("fixationAcceptedDate",Hibernate.DATE).addScalar("assessmentID",
			// Hibernate.INTEGER).addScalar("id",
			// Hibernate.INTEGER).addScalar("sfID",
			// Hibernate.STRING).addScalar("designationID",
			// Hibernate.INTEGER).addScalar("empName",
			// Hibernate.STRING).addScalar(
			// "effectiveDate", Hibernate.DATE).addScalar("promotionDate",
			// Hibernate.DATE).addScalar("designation",
			// Hibernate.STRING).addScalar("department", Hibernate.STRING)
			// .addScalar("basicPay", Hibernate.STRING).addScalar("gradePay",
			// Hibernate.STRING).addScalar("newBasicPay",
			// Hibernate.STRING).addScalar("newGradePay", Hibernate.STRING)
			// .addScalar("twoAddIncr",
			// Hibernate.STRING).addScalar("newTwoAddIncr",
			// Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(AssessmentDetailsDTO.class))
			// .setInteger(0, promotionBean.getAssessmentCategoryID()).list());
		} catch (Exception e) {
			throw e;
		}
		return promotionBean;

	}

	// NEW CODE RAJENDRA

	@Override
	@SuppressWarnings("unchecked")
	public PromotionManagementBean getPromotedCandidatesWithoption(
			PromotionManagementBean promotionBean) throws Exception {
		Session session = null;
		StringBuilder sb = new StringBuilder();
		try {
			session = hibernateUtils.getSession();
			sb.append("select tab.id,tab.sfID,tab.payStatus,tab.venueID,tab.optionID,tab.optionStatus,tab.annualIncrementId,tab.empName,tab.effectiveDate,tab.promotionDate,tab.variableIncr,tab.endingDate,tab.reservation,tab.designation,tab.department,tab.designationTo   from "
					+ "(select pad.id,emp.sfid sfID,POC.pay_update  payStatus,POC.status  optionStatus,poc.id  optionID,pad.status venueID,"
					+ " aid.id  annualIncrementId,emp.name_in_service_book empName,pad.effective_date effectiveDate,pad.promotion_date promotionDate,"
					+ "pad.variable_incr variableIncr,pad.ending_date endingDate,(select name from reservation_master where id=pad.reservation_id) reservation,"
					+ "desig.name designation,desig2.name designationTo,dept.department_name department FROM "
					+ "pro_assessment_details pad left outer join pro_optional_certificate poc on (poc.requested_by=pad.sfid  and poc.status != 0) left outer join annual_increment_details aid on(aid.sfid=pad.sfid and aid.increment_year_id="+promotionBean.getYearID()+" and aid.basic_pay_id    IS NULL),"
					+ "emp_master emp,pro_residency_period_master prpd,designation_master desig2,designation_master desig,org_role_instance ori,departments_master dept,DESIGNATION_MAPPINGS DMAP,CATEGORY_MASTER CM  ");

			sb.append("  where pad.sfid=emp.sfid  and (pad.year_id =?"
					+ " or pad.year_id = ?) and pad.status in(53,55) and desig.status=1 and pad.designation_id=desig.id   And prpd.id = pad.residency_id  And prpd.designation_to = desig2.id "
					+ "and ori.status=1 and dept.status=1 and ori.org_role_id=emp.office_id and ori.department_id=dept.department_id and desig.id=DMAP.DESIG_ID and DMAP.CATEGORY_ID=cm.id");

			if ((promotionBean.getDesignationFrom() != 0)) {
				sb.append("  and desig.id="
						+ promotionBean.getDesignationFrom());
			}
			if ((promotionBean.getAssessmentCategoryID() != 0)) {
				sb.append(" and pad.assessment_category_id="
						+ promotionBean.getAssessmentCategoryID());
			}
			if ((promotionBean.getAssessmentTypeID() != 0)) {
				sb.append(" and cm.id=" + promotionBean.getAssessmentTypeID()
						+ " ");
			}
			sb.append("   order by payStatus asc,optionStatus)tab  where tab.optionstatus!=100 or  tab.optionstatus is null  order by tab.sfid");

			promotionBean.setAssessmentDetails1(session
					.createSQLQuery(sb.toString())
					.addScalar("id", Hibernate.INTEGER)
					.addScalar("optionID",Hibernate.STRING)
					.addScalar("sfID", Hibernate.STRING)
					.addScalar("payStatus", Hibernate.INTEGER)
					.addScalar("optionStatus", Hibernate.INTEGER)
					.addScalar("annualIncrementId", Hibernate.INTEGER)
					.addScalar("empName", Hibernate.STRING)
					.addScalar("effectiveDate", Hibernate.DATE)
					.addScalar("designationTo", Hibernate.STRING)
					.addScalar("variableIncr", Hibernate.INTEGER)
					.addScalar("endingDate", Hibernate.DATE)
					.addScalar("reservation", Hibernate.STRING)
					.addScalar("designation", Hibernate.STRING)
					.addScalar("department", Hibernate.STRING)
					.addScalar("promotionDate", Hibernate.DATE)
					.addScalar("venueID",Hibernate.INTEGER)
					.setResultTransformer(
							Transformers
							.aliasToBean(AssessmentDetailsDTO.class))
							.setInteger(0, promotionBean.getYearID()).setInteger(1, promotionBean.getYearID()-1).list());

		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	@Override
	public PromotionManagementBean submitCandidatesOption(
			PromotionManagementBean promotionBean) throws Exception {

		Session session = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		// AssessmentDetailsDTO assessmentDetailsDTO = null;
		int assessmentID = 0;
		try {
			session = hibernateUtils.getSession();
			Date currentDate = CPSUtils.getCurrentDateWithTime();
			String[] rows = promotionBean.getRowValues().split(",");

			// Get assessment details max id
			// assessmentID =
			// Integer.valueOf(session.createSQLQuery("select case when max(id) is null then 1 else max(id)+1 end from pro_assessment_details").uniqueResult().toString());

			for (String row : rows) {
				// SFID#intwDate#VenueID#BoardID(or)labRepresentative#Attempts#designationID#departmentID#effectiveDate#variableIncr#reservationID#status
				AlertMessageDTO alertMessageDTO = new AlertMessageDTO();
				String[] rvalues = row.split("#");
				AssessmentDetailsDTO assessmentDetailsDTO = (AssessmentDetailsDTO) session
						.createCriteria(AssessmentDetailsDTO.class)
						.add(Expression.eq(CPSConstants.YEARIDSTR,
								promotionBean.getYearID()))
								.add(Expression.eq(CPSConstants.ASSESSMENTCATEGORYID,
										promotionBean.getAssessmentCategoryID()))
										.add(Expression.eq("sfID", rvalues[1])).uniqueResult();

				ResidencyPeriodDTO residencyDTO = (ResidencyPeriodDTO) session
						.get(ResidencyPeriodDTO.class,
								assessmentDetailsDTO.getResidencyPeriodId());

				OptionalCertificateDTO opdto = (OptionalCertificateDTO) session
						.createCriteria(OptionalCertificateDTO.class)
						.add(Expression.eq("requestedBy", rvalues[1]))
						.uniqueResult();

				opdto.setStatus(100);
				session.flush();

				// EmployeeBean empBean =
				// (EmployeeBean)session.createCriteria(EmployeeBean.class).add(Expression.eq("userSfid",
				// assessmentDetailsDTO.getSfID().toUpperCase())).uniqueResult();

				Date date = new Date();
				SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

				Date date1 = assessmentDetailsDTO.getPromotionDate();

				String temp = df.format(date1);

				session.createSQLQuery(
						"update emp_master set designation_id=?,LAST_PROMOTION=?,SENIORITY_DATE=?  where sfid=?")
						.setInteger(0, (residencyDTO.getDesignationTo()))
						.setString(1, temp)
						.setString(3, assessmentDetailsDTO.getSfID())
						.setString(2, temp).executeUpdate();

				session.flush();

				// date = df.parse(date1.toString());

				// String temp = CPSUtils.formattedDate(date1.toString());

				// empBean.setDesignation(residencyDTO.getDesignationTo());
				// empBean.setLastPromotion(CPSUtils.formatDate(assessmentDetailsDTO.getPromotionDate()));
				/*
				 * empBean.setDob(CPSUtils.formattedDate(empBean.getDob()));
				 * empBean
				 * .setDojAsl(CPSUtils.formattedDate(empBean.getDojAsl()));
				 * empBean
				 * .setDojDrdo(CPSUtils.formattedDate(empBean.getDojDrdo()));
				 * empBean
				 * .setDojGovt(CPSUtils.formattedDate(empBean.getDojGovt()));
				 * empBean
				 * .setSeniorityDate(CPSUtils.formattedDate(empBean.getSeniorityDate
				 * ()));
				 * 
				 * empBean.setCreationDate(CPSUtils.formattedDate(empBean.
				 * getCreationDate()));
				 * empBean.setUptoDate(CPSUtils.formattedDate
				 * (empBean.getUptoDate()));
				 */

				/*
				 * empBean.setDob(CPSUtils.formattedDate(empBean.getDob()));
				 * empBean
				 * .setDojAsl(CPSUtils.formattedDate(empBean.getDojAsl()));
				 * empBean
				 * .setDojDrdo(CPSUtils.formattedDate(empBean.getDojDrdo()));
				 * empBean
				 * .setDojGovt(CPSUtils.formattedDate(empBean.getDojGovt()));
				 * empBean
				 * .setSeniorityDate(CPSUtils.formattedDate(empBean.getSeniorityDate
				 * ())); //
				 * empBean.setLastPromotion(CPSUtils.formattedDate(empBean
				 * .getLastPromotion()));
				 * empBean.setCreationDate(CPSUtils.formattedDate
				 * (empBean.getCreationDate()));
				 */

				session.flush();
				promotionBean.setResult(CPSConstants.SUCCESS);

			}

			return promotionBean;
		} catch (Exception e) {

			throw e;
		}

	}

	// ???
	@Override
	@SuppressWarnings("unchecked")
	public PromotionManagementBean getPromotedCandidatesPayData(
			PromotionManagementBean promotionBean) throws Exception {
		Session session = null;
		StringBuilder sb = new StringBuilder();
		try {                                                                                                                                                                                                                                             //VarIncrValue
			session = hibernateUtils.getSession();
			sb.append("select tab.Assessmentid,case when (tab.id is null) then 0 else tab.id  end id,tab.sfID,tab.payStatus,tab.fixationAcceptedDate,tab.newTwoAddIncr,tab.optionStatus,tab.empName,tab.effectiveDate,tab.promotionDate,tab.variableIncr, tab.varIncrValue,tab.designation,tab.designationTo,tab.department,tab.gradePay,tab.basicPay,tab.calculatedBasicPay,tab.masterBasicPay, "
					+ "case when (tab.newpay = 0 or tab.newpay is null)  Then(select distinct case when (tab.calculatedBasicPay  > tab.Masterbasicpay and tab.calculatedBasicPay<80000)   then tab.calculatedBasicPay when (tab.Masterbasicpay > tab.calculatedBasicPay and tab.Masterbasicpay<80000) then tab.Masterbasicpay else 80000 END  ProBasicPay from tab)"
					+ "ELSE tab.newpay  end newBasicPay,case when (tab.newppfGradepay = 0 or tab.newppfGradepay is null)  then tab.Newgradepay else tab.newppfGradepay end newGradepay  from (SELECT pad.id Assessmentid,(SELECT POC.pay_update  FROM pro_optional_certificate POC WHERE poc.requested_by=pad.sfid) AS payStatus, emp.sfid sfID,(SELECT trim(POC.status)FROM pro_optional_certificate POC"
					+ " WHERE poc.requested_by=pad.sfid) AS optionStatus,(SELECT NEW_TWO_ADD_INCR  FROM pro_pay_fixation ppf WHERE ppf.ASSESSMENT_ID=pad.id) newTwoAddIncr,"
					+ " (SELECT to_number(NEW_PAY) FROM pro_pay_fixation ppf  WHERE ppf.ASSESSMENT_ID=pad.id) newpay,(SELECT ppf.increments_dopart_id FROM pro_pay_fixation ppf WHERE ppf.ASSESSMENT_ID=pad.id) incrementDopartId,(SELECT to_number(NEW_GRADE_PAY) FROM pro_pay_fixation ppf  WHERE ppf.ASSESSMENT_ID=pad.id) newppfGradepay, (SELECT FIXATION_ACCEPTED_DATE  FROM pro_pay_fixation ppf"
					+ " WHERE ppf.ASSESSMENT_ID=pad.id)   fixationAcceptedDate,(SELECT id  FROM pro_pay_fixation ppf  WHERE ppf.ASSESSMENT_ID=pad.id) id,"

					+ " emp.name_in_service_book empName,pad.effective_date effectiveDate,pad.promotion_date promotionDate,pad.variable_incr variableIncr,(select CALCULATE_VAR_INCR_VAL_SYN(tab1.Newgradepay,pad.variable_incr) from dual) varIncrValue,"           //Here i added varIncrValue
					+ "desig.name designation,desig2.name  designationTo,dept.department_name department, pay.GradePay,pay.basicPay,tab1.Newgradepay,tab1.Masterbasicpay,(((floor(floor((pay.Basicpay+pay.Gradepay)*0.03)/10)*10)+(ceil(mod(floor((pay.Basicpay+pay.Gradepay)*0.03),10)/10)*10))+pay.Basicpay) calculatedBasicPay  "
					+ "   FROM pro_assessment_details pad,emp_master emp,designation_master desig2,designation_master desig,org_role_instance ori,departments_master dept,DESIGNATION_MAPPINGS DMAP,(SELECT (ppm.range_from) Masterbasicpay,"
					+ "pdm.grade_pay Newgradepay,pad1.id proid,prpd.designation_to desigto  FROM payband_designation_mapping pdm,pay_payband_master ppm,pro_assessment_details pad1,pro_residency_period_master prpd ");

			sb.append("   WHERE pdm.designation_id = prpd.designation_to  And pad1.residency_id = prpd.id  AND pdm.payband_type_id =ppm.id)tab1,CATEGORY_MASTER CM ,(SELECT CASE WHEN epd.Basic_pay IS NULL THEN 0 ELSE epd.Basic_pay END Basicpay,"
					+ "CASE WHEN epd.Grade_pay IS NULL THEN 0 ELSE epd.Grade_pay END GradePay,pad2.id proid FROM emp_payment_details epd,pro_assessment_details pad2 WHERE epd.sfid=pad2.sfid) pay "
					+ "  WHERE pad.sfid   =emp.sfid AND (pad.year_id = ? "
//					+ " or (pad.year_id= (SELECT id FROM year_master WHERE name=(TO_CHAR(sysdate,'yyyy'))))"
					+ ") AND pad.status IN(53,55) AND desig.status =1"
					+ " And emp.sfid = pad.sfid And tab1.proid = pad.id And pay.proid = pad.id  AND pad.designation_id =desig.id  AND ori.status =1  AND dept.status =1 AND ori.org_role_id = emp.office_id"
					+ " AND ori.department_id  = dept.department_id  And tab1.desigto = desig2.id  AND desig.id =DMAP.DESIG_ID  AND DMAP.CATEGORY_ID =cm.id AND  PAD.BASIC_PAY_ID  IS NULL");

			if ((promotionBean.getDesignationFrom() != 0)) {
				sb.append("  and desig.id="
						+ promotionBean.getDesignationFrom());
			}
			if ((promotionBean.getAssessmentCategoryID() != 0)) {
				sb.append(" and pad.assessment_category_id="
						+ promotionBean.getAssessmentCategoryID());
			}
			if ((promotionBean.getAssessmentTypeID() != 0)) {
				sb.append(" and cm.id=" + promotionBean.getAssessmentTypeID()
						+ " ");
			}
			sb.append("   order by fixationAcceptedDate asc)tab  where (tab.incrementDopartId  is null or tab.incrementDopartId =0)  and   (tab.optionstatus =100 or  tab.optionstatus is not null)  order by tab.id");

			promotionBean.setAssessmentDetails1(session
					.createSQLQuery(sb.toString())
					.addScalar("id", Hibernate.INTEGER)
					.addScalar("sfID", Hibernate.STRING)
					.addScalar("optionStatus", Hibernate.INTEGER)
					.addScalar("empName", Hibernate.STRING)
					.addScalar("effectiveDate", Hibernate.DATE)
					.addScalar("variableIncr", Hibernate.INTEGER)
					.addScalar("varIncrValue",Hibernate.INTEGER)                         //This property has been added.
					.addScalar("designationTo", Hibernate.STRING)
					.addScalar("designation", Hibernate.STRING)
					.addScalar("payStatus", Hibernate.INTEGER)
					.addScalar("department", Hibernate.STRING)
					.addScalar("fixationAcceptedDate", Hibernate.DATE)
					.addScalar("newTwoAddIncr", Hibernate.STRING)
					.addScalar("assessmentID", Hibernate.INTEGER)
					.addScalar("newGradePay", Hibernate.STRING)
					.addScalar("newBasicPay", Hibernate.STRING)
					.addScalar("calculatedBasicPay", Hibernate.STRING)
					.addScalar("basicPay", Hibernate.STRING)
					.addScalar("gradePay", Hibernate.STRING)
					.addScalar("masterBasicPay", Hibernate.STRING)
					.addScalar("promotionDate", Hibernate.DATE)
					.setResultTransformer(
							Transformers
							.aliasToBean(AssessmentDetailsDTO.class))
							.setInteger(0, promotionBean.getYearID()).list());

		} catch (Exception e) {
			throw e;
		}
		return promotionBean;
	}

	@Override
	public PromotionManagementBean submitPromotionPayUpdate(
			PromotionManagementBean promotionBean) throws Exception {
		Session session = null;
		Date currentDate = CPSUtils.getCurrentDateWithTime();
		int offlineReferenceId = 0;
		Integer basicPayId = 0;
		PromotionOfflineEntryBean entryBean = null;
		try {
			// String [] casualityId=promotionBean.getCasualityId().split("#");
			promotionBean.setCasualityId("2");
			session = hibernateUtils.getSession();
			String[] rows = promotionBean.getRowValues().split(",");
			if (promotionBean.getCasualityId().equals("2")) {
				for (String row : rows) {
					String[] rvalues = row.split("#");

					PayFixationDTO payFixationDTO = (PayFixationDTO) session
							.createCriteria(PayFixationDTO.class)
							.add(Expression.eq("id",
									Integer.valueOf(rvalues[0])))
									.add(Expression.eq(CPSConstants.STATUS, 1))
									.uniqueResult();
					if (CPSUtils.isNull(payFixationDTO)) {
						payFixationDTO = new PayFixationDTO();
						payFixationDTO.setAssessmentID(Integer
								.valueOf(rvalues[1]));
						payFixationDTO.setCategoryId(promotionBean
								.getAssessmentTypeID());
						payFixationDTO.setStatus(1);
						payFixationDTO.setCreatedBy(promotionBean.getSfID());
						payFixationDTO.setCreationTime(currentDate);
						payFixationDTO.setReferenceId(offlineReferenceId);

						EmpPaymentsDTO empPaymentsDTO = (EmpPaymentsDTO) session
								.createCriteria(EmpPaymentsDTO.class)
								.add(Expression.eq(CPSConstants.SFID,
										rvalues[2])).uniqueResult();
						if (!CPSUtils.isNull(empPaymentsDTO)) {
							if (!CPSUtils.isNull(empPaymentsDTO.getBasicPay())) {
								payFixationDTO.setPay(Float
										.valueOf(empPaymentsDTO.getBasicPay()));
							}
							if (!CPSUtils.isNull(empPaymentsDTO.getGradePay())) {
								payFixationDTO.setGradePay(Float
										.valueOf(empPaymentsDTO.getGradePay()));
							}
							if (!CPSUtils
									.isNull(empPaymentsDTO.getTwoAddIncr())) {
								payFixationDTO
								.setTwoAddPay(Float
										.valueOf(empPaymentsDTO
												.getTwoAddIncr()));
							}
						}

						if (!CPSUtils.isNullOrEmpty(rvalues[3])) {

							payFixationDTO.setNewPay(Float.valueOf(rvalues[3]));
						}
						if (!CPSUtils.isNullOrEmpty(rvalues[4])) {
							payFixationDTO
							.setNewGradePay(Float.valueOf(rvalues[4]));
						}
						if (!CPSUtils.isNullOrEmpty(rvalues[5])) {
							payFixationDTO.setNewTwoAddPay(Float
									.valueOf(rvalues[5]));
						}
						if (!CPSUtils.isNullOrEmpty(rvalues[6])) {

							payFixationDTO.setFixationAcceptedDate(CPSUtils
									.convertStringToDate(rvalues[6]));
						}
						if (!CPSUtils.isNullOrEmpty(rvalues[7])) {
							payFixationDTO.setVariableIncrements(rvalues[7]);
						}

						payFixationDTO.setLastModifiedBy(promotionBean.getSfID());
						payFixationDTO.setLastModifiedTime(currentDate);
						if (!CPSUtils.isNullOrEmpty(rvalues[8])) {
							payFixationDTO.setVariableIncrValue(Integer.parseInt(rvalues[8]));                   //To save the variable increement value
						}//To save the variable increement value
						// payFixationDTO.setFixationAcceptedDate(CPSUtils.convertStringToDate(rvalues[8]));
					}/*
					 * if(!CPSUtils.isNullOrEmpty(rvalues[16])){
					 * //payFixationDTO
					 * .setFixationDoPartId(Integer.parseInt(rvalues[16])); }
					 */

					// payFixationDTO.setVariableIncrements(Integer.parseInt(rvalues[9]));
					// payFixationDTO.setDoPartDetails(doPartBean);
					// payFixationDTO.setDoPartID(Integer.valueOf(promotionBean.getDoPartNumber()));

					session.saveOrUpdate(payFixationDTO);
					session.flush();
					promotionBean.setResult(CPSConstants.SUCCESS);
				}

			} else {                                                           //check this else part(not working)
				for (String row : rows) {
					String[] rvalues = row.split("#");
					PayFixationDTO payFixationDTO = (PayFixationDTO) session
							.createCriteria(PayFixationDTO.class)
							.add(Expression.eq("id",
									Integer.valueOf(rvalues[0])))
									.add(Expression.eq(CPSConstants.STATUS, 1))
									.uniqueResult();
					if (CPSUtils.isNull(payFixationDTO)) {
						payFixationDTO = new PayFixationDTO();
						payFixationDTO.setAssessmentID(Integer
								.valueOf(rvalues[1]));
						payFixationDTO.setCategoryId(promotionBean
								.getAssessmentTypeID());
						payFixationDTO.setStatus(1);
						payFixationDTO.setCreatedBy(promotionBean.getSfID());
						payFixationDTO.setCreationTime(currentDate);
						payFixationDTO.setReferenceId(offlineReferenceId);

						EmpPaymentsDTO empPaymentsDTO = (EmpPaymentsDTO) session
								.createCriteria(EmpPaymentsDTO.class)
								.add(Expression.eq(CPSConstants.SFID,
										rvalues[2])).uniqueResult();
						if (!CPSUtils.isNull(empPaymentsDTO)) {
							if (!CPSUtils.isNull(empPaymentsDTO.getBasicPay())) {
								payFixationDTO.setPay(Float
										.valueOf(empPaymentsDTO.getBasicPay()));
							}
							if (!CPSUtils.isNull(empPaymentsDTO.getGradePay())) {
								payFixationDTO.setGradePay(Float
										.valueOf(empPaymentsDTO.getGradePay()));
							}
							if (!CPSUtils
									.isNull(empPaymentsDTO.getTwoAddIncr())) {
								payFixationDTO
								.setTwoAddPay(Float
										.valueOf(empPaymentsDTO
												.getTwoAddIncr()));
							}
						}
					}

					if (!CPSUtils.isNullOrEmpty(rvalues[15])) {
						payFixationDTO.setVarIncEndDate(CPSUtils
								.convertStringToDate(rvalues[15]));
					}
					if (!CPSUtils.isNullOrEmpty(rvalues[14])) {
						payFixationDTO.setVariableIncrements(rvalues[14]);
					}
					if (!CPSUtils.isNullOrEmpty(rvalues[17])) {
						payFixationDTO.setIncrementsDoPartId(Integer
								.parseInt(rvalues[17]));
					}
					payFixationDTO.setLastModifiedBy(promotionBean.getSfID());
					payFixationDTO.setLastModifiedTime(currentDate);
					payFixationDTO.setDoPartID(Integer.valueOf(promotionBean
							.getDoPartNumber()));
					session.saveOrUpdate(payFixationDTO);
					session.flush();

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		}
		return promotionBean;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PromotionManagementBean getOptionCertificateHome(PromotionManagementBean promotionBean) throws Exception {
		Session session = null;
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		List<AssessmentDetailsDTO> assessmentCategoryList = null;
		List<KeyValueDTO> yearwiseProList = null;
		try {
			session = hibernateUtils.getSession();
			sb.append("SELECT DISTINCT(TO_CHAR(");
		 if(promotionBean.getType().equals("payFixation") || promotionBean.getType() == "payFixation") {              //For pay fixation screen only display board type
			 sb.append("rpad(dtm.name,(select length(max(name)) from dopart_ii_type_master))||'--'||");
			}
			
		 sb.append( "rpad(cm.name,(select length(max(name)) from category_master)) ||'--'||rpad(pacm.assessment_category_type,(select length(max(assessment_category_type))+1 from pro_assessment_category_master))||'--'||ym.name)) reservation,pad.year_id yearID,prpd.assessment_type_id boardID,prpd.assessment_category_id assessmentCategoryID, dtm.id gazettedId FROM pro_assessment_category_master pacm,category_master cm,year_master ym,pro_assessment_details PAD, dopart_ii_type_master dtm,"       //newly two table joined (dtm,dtdm) ,condtions ,columns(dtm.id,dtm.name)
					+ " dopart_ii_type_desig_master dtdm,pro_residency_period_master prpd WHERE pad.residency_id=prpd.id AND prpd.assessment_type_id=cm.id AND prpd.assessment_category_id=pacm.id AND pad.status in(53,55) AND ym.id IN(pad.year_id) and   dtdm.type_id=dtm.id "
					+ "and dtdm.designation_id =prpd.designation_to and prpd.status=1 and dtdm.status=1 and dtm.status=1");

			if(promotionBean.getType() == "option"  || promotionBean.getType().equals("option")) {
				sb.append("And pad.id not in(select poc.assessment_id from pro_optional_certificate poc)");
			}else if(promotionBean.getType() == "option1"  || promotionBean.getType().equals("option1")) {
				sb.append("And pad.id  in(select poc.assessment_id from pro_optional_certificate poc)");
			}else if(promotionBean.getType() == "viewOtionalCertificates"  || promotionBean.getType().equals("viewOtionalCertificates")) {
				sb.append("And pad.id  in(select poc.assessment_id from pro_optional_certificate poc where poc.status !=100)");
			}else if(promotionBean.getType() == "promotionPayUpdate"  || promotionBean.getType().equals("promotionPayUpdate")) {
				sb.append( "AND pad.id  IN(SELECT poc.assessment_id FROM pro_optional_certificate poc where poc.status = 100)  AND PAD.ID NOT IN(SELECT ppf.assessment_id FROM pro_pay_fixation ppf)");
			}else if(promotionBean.getType().equals("payFixation") || promotionBean.getType() == "payFixation") {
				sb.append( "AND pad.id  IN(SELECT poc.assessment_id FROM pro_optional_certificate poc where poc.status = 100)  AND PAD.ID  IN(SELECT ppf.assessment_id FROM pro_pay_fixation ppf where "
						+ "(ppf.FIXATION_DOPART_ID =0 or ppf.increments_dopart_id=0)"            //This is new condition instead of below condtion to get who don't variableincrdo

						+ ")");
			}
			sb.append(" order by reservation");

			if(promotionBean.getType().equals(CPSConstants.PROMOTEDCANDIDATEASSESSMENT) || promotionBean.getType().equals(CPSConstants.QUALIFIEDCANDIDATEASSESSMENT)){
				sb2.append("SELECT distinct cm.name||'-'||pacm.assessment_category_type||'-'|| ym.name as name,pad.assessment_category_id||'-'||cm.id as value FROM pro_assessment_details pad,pro_assessment_category_master pacm,"
						+ "year_master ym,pro_residency_period_master prpd,DESIGNATION_MAPPINGS DMAP,CATEGORY_MASTER CM  WHERE pacm.id=pad."
						+ "assessment_category_id And pad.residency_id=prpd.id And prpd.assessment_category_id = pacm.id  And prpd.assessment_type_id=cm.id "
						+ "AND pad.year_id="+promotionBean.getYearID()+" And pad.year_id=ym.id(+) ");
						if(promotionBean.getType().equals(CPSConstants.PROMOTEDCANDIDATEASSESSMENT)){
								sb2.append(" AND pad.status IN(53,55,57)");
						}		
						sb2.append(" AND pad.designation_id=DMAP.DESIG_ID AND DMAP.CATEGORY_ID=cm.id");
				yearwiseProList = session.createSQLQuery(sb2.toString()).addScalar("name",Hibernate.STRING).addScalar("value",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
				promotionBean.setLocalBoardMembersList(yearwiseProList);
			}else{

				assessmentCategoryList = session.createSQLQuery(sb.toString()).addScalar("reservation", Hibernate.STRING).addScalar("assessmentCategoryID", Hibernate.INTEGER).addScalar("yearID",Hibernate.INTEGER).addScalar("boardID", Hibernate.INTEGER).addScalar("gazettedId",Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(AssessmentDetailsDTO.class)).list();
			}
			if(promotionBean.getType()  !=  "option1") {
				promotionBean.setAssessmentDetails(assessmentCategoryList);
			}else if(promotionBean.getType() ==  "option1") {
				promotionBean.setAssessmentDetails1(assessmentCategoryList);
			}
		} 
		catch(Exception e){
			throw e;
		}
		return promotionBean;
	}
	@Override
	//start storeBoardTypeInfo
	public String storeBoardTypeInfo(AssessmentCategoryDTO assessmentCategoryDTO)throws Exception{
		Session session = null;
		String message=null;
		try{
			session=hibernateUtils.getSession();
			if(assessmentCategoryDTO.getId() != 0){
				session.saveOrUpdate(assessmentCategoryDTO);	
				message = CPSConstants.UPDATE;
			}else{
				session.saveOrUpdate(assessmentCategoryDTO);
				session.flush();
				message = CPSConstants.SUCCESS;
			}
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	//end  storeBoardTypeInfo


	// start getBoardTypeInfoList(PromotionManagementBean promotionBean)
	@Override
	@SuppressWarnings("unchecked")
	public List<AssessmentCategoryDTO>getBoardTypeInfoList(PromotionManagementBean promotionBean) throws Exception {
		List<AssessmentCategoryDTO> list = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			list = session.createCriteria(AssessmentCategoryDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	//end getBoardTypeInfoList(PromotionManagementBean promotionBean)

	//start deleteBoardTypeInfo
	@Override
	@SuppressWarnings("unchecked")
	public String deleteBoardTypeInfo(PromotionManagementBean promotionBean) throws Exception {

		//String message = "";
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			session = hibernateUtils.getSession();
			//List<ResidencyPeriodDTO> residencyList = session.createCriteria(ResidencyPeriodDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("assessmentCategoryID",Integer.parseInt(promotionBean.getNodeID()))).list();
			//List<VenueDetailsDTO> venueList = session.createCriteria(VenueDetailsDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("assessmentCategoryID",Integer.parseInt(promotionBean.getNodeID()))).list();
			int nodeId=Integer.parseInt(promotionBean.getNodeID());
			List list=session.createSQLQuery("select assessment_category_id from pro_residency_period_master where status=1 and assessment_category_id="+nodeId+" union all((select assessment_category_id from pro_venue_details where status=1 and assessment_category_id="+nodeId+")union all(select assessment_category_id from pro_assessment_details where assessment_category_id="+nodeId+"))").list();

			if(list.size()==0){
				AssessmentCategoryDTO assessmentCategoryDTO = (AssessmentCategoryDTO) session.get(AssessmentCategoryDTO.class, Integer.valueOf(promotionBean.getNodeID()));
				assessmentCategoryDTO.setStatus(0);
				//				assessmentCategoryDTO.setLastModifiedBy(promotionBean.getSfID());
				assessmentCategoryDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				session.saveOrUpdate(assessmentCategoryDTO);
				hibernateUtils.closeSession();
				hibernateUtils.getSession();
				promotionBean.setResult(CPSConstants.DELETE);
				//message=promotionBean.getResult();
			}else{
				promotionBean.setResult(CPSConstants.FAILED);	
				//message=promotionBean.getResult();
			}
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}
	//end deleteBoardTypeInfo

	@Override
	//start checkDuplicateBoard
	public String checkDuplicateBoard(PromotionManagementBean promotionBean) throws Exception {
		Session session = null;
		ArrayList list=null;
		try {
			session = hibernateUtils.getSession();
			Criteria crit = session.createCriteria(AssessmentCategoryDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(
					Expression.eq(CPSConstants.CATEGORY, (promotionBean.getBoardType().toUpperCase())));
			//				if (!CPSUtils.isNullOrEmpty(promotionBean.getNodeID())) {
			//					crit = crit.add(Expression.ne(CPSConstants.ID, Integer.parseInt(promotionBean.getNodeID())));
			//				}
			if (CPSUtils.checkList(crit.list())) {
				promotionBean.setResult(CPSConstants.DUPLICATE);
			}
		} catch (Exception e) {
			throw e;
		}
		return promotionBean.getResult();
	}
	//end checkDu

}
