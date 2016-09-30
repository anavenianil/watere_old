package com.callippus.web.dao.master;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.ApplicationRolesDTO;
import com.callippus.web.beans.dto.AppointmentDTO;
import com.callippus.web.beans.dto.AwardCategoryDTO;
import com.callippus.web.beans.dto.AwardDTO;
import com.callippus.web.beans.dto.CategoryDTO;
import com.callippus.web.beans.dto.CghsAdvanceRequestDTO;
import com.callippus.web.beans.dto.CityDTO;
import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.beans.dto.CommunityDTO;
import com.callippus.web.beans.dto.DegreeDTO;
import com.callippus.web.beans.dto.DepartmentTypeDTO;
import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.DirectorateDTO;
import com.callippus.web.beans.dto.DisciplineDTO;
import com.callippus.web.beans.dto.DispensaryLocationDTO;
import com.callippus.web.beans.dto.DispensaryNumberDTO;
import com.callippus.web.beans.dto.DistrictTypeDTO;
import com.callippus.web.beans.dto.DivisionDTO;
import com.callippus.web.beans.dto.EmploymentDTO;
import com.callippus.web.beans.dto.FamilyRelationDTO;
import com.callippus.web.beans.dto.GroupDTO;
import com.callippus.web.beans.dto.LetterNumberReferenceDTO;
import com.callippus.web.beans.dto.MotherTongueDTO;
import com.callippus.web.beans.dto.NomineeDTO;
import com.callippus.web.beans.dto.PinNumberDTO;
import com.callippus.web.beans.dto.QualificationDTO;
import com.callippus.web.beans.dto.QuarterTypeDTO;
import com.callippus.web.beans.dto.ReligionDTO;
import com.callippus.web.beans.dto.RequestWorkDTO;
import com.callippus.web.beans.dto.ReservationDTO;
import com.callippus.web.beans.dto.RetirementTypeDTO;
import com.callippus.web.beans.dto.StateTypeDTO;
import com.callippus.web.beans.dto.WorkflowTypeDTO;
import com.callippus.web.beans.dto.YearTypeDTO;
import com.callippus.web.beans.dto.subCategoryDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.master.MasterDataBean;
import com.callippus.web.beans.quarterType.QuarterTypeBean;
import com.callippus.web.beans.tutionFee.TutionFeeLimitMasterDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.ConnectionUtil;
import com.callippus.web.paybill.dto.PayBillQuartersTypeMasterDTO;

@Service
public class SQLMasterDataDAO implements IMasterDataDAO, Serializable {

	private static Log log = LogFactory.getLog(SQLMasterDataDAO.class);
	private static final long serialVersionUID = 5611041426238862266L;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	public String checkName(MasterDataBean masterBean) throws Exception {
		String message = null;
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		int i = 0;
		try {
			log.debug("::SQLMasterDataDAO>>>>>>>>>>>>checkName ---> method Start>>>>>>>>>>>>>>");
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			StringBuffer sb = new StringBuffer();
			if (CPSUtils.compareStrings(masterBean.getTableName(), CPSConstants.EMP_PIN_NUMBER)) {
				sb.append("select count(*) from " + masterBean.getTableName() + " where status=1 ");
				if (!CPSUtils.isNullOrEmpty(masterBean.getId())) {
					sb.append(" and id!=" + masterBean.getId());
				}
				if (CPSUtils.compareStrings(masterBean.getParam(), CPSConstants.PIN) || CPSUtils.compareStrings(masterBean.getParam(), CPSConstants.MANAGEPIN)) {
					sb.append(" and pin=" + "'" + masterBean.getPin() + "'");
				} else if (CPSUtils.compareStrings(masterBean.getParam(), CPSConstants.SFID) || CPSUtils.compareStrings(masterBean.getParam(), CPSConstants.MANAGEPIN)) {
					sb.append(" and sfid=" + "'" + masterBean.getName() + "'");
				}
				ps = con.prepareStatement(sb.toString());
				rsq = ps.executeQuery();
			} else if (CPSUtils.compareStrings(masterBean.getTableName(), CPSConstants.DISPENSARY_MAPPING)) {

				sb.append("select count(*) from " + masterBean.getTableName() + " where trim(upper(dispensary_number))=trim(upper(?)) and status=1 ");
				sb.append(" and dispensary_location_id=" + masterBean.getParentID());
				if (!CPSUtils.isNullOrEmpty(masterBean.getId())) {
					sb.append(" and id!=" + masterBean.getId());
				}
				ps = con.prepareStatement(sb.toString());
				ps.setString(1, masterBean.getName().toUpperCase());
				rsq = ps.executeQuery();
			} else if (CPSUtils.compareStrings(masterBean.getTableName(), CPSConstants.PAY_QUARTERS_TYPE_MASTER)) {

				sb.append("select count(*) from " + masterBean.getTableName() + " where trim(upper(quarter_type))=trim(upper(?)) and status=1 ");
				if (!CPSUtils.isNullOrEmpty(masterBean.getId())) {
					sb.append(" and id!=" + masterBean.getId());
				}
				ps = con.prepareStatement(sb.toString());
				ps.setString(1, masterBean.getName());
				rsq = ps.executeQuery();
			} else if (CPSUtils.compareStrings(masterBean.getTableName(), CPSConstants.PAY_QUARTERS_SUBTYPE_MASTER)) {

				sb.append("select count(*) from " + masterBean.getTableName() + " where trim(upper(quarters_sub_type))=trim(upper(?)) and status=1 ");
				sb.append(" and quarters_type_id=" + masterBean.getParentID());
				if (!CPSUtils.isNullOrEmpty(masterBean.getId())) {
					sb.append(" and id!=" + masterBean.getId());
				}
				ps = con.prepareStatement(sb.toString());
				ps.setString(1, masterBean.getName());
				rsq = ps.executeQuery();
			} else if(CPSUtils.compareString(masterBean.getTableName(), CPSConstants.DESIGNATION_MASTER)){  // This is for to resove the duplicate records.
				sb.append("select count(*) from " + masterBean.getTableName() + " where trim(upper(name))= ? or trim(upper(code))=?) and status=1 " );
				ps = con.prepareStatement(sb.toString());
				ps.setString(1, masterBean.getDesignationId().toUpperCase());
				ps.setString(2,masterBean.getShortForm().toUpperCase());
				rsq = ps.executeQuery();
			}
			else {
				sb.append("select count(*) from " + masterBean.getTableName() + " where trim(upper(name))=trim(upper(?)) and status=1 ");
				if (!CPSUtils.isNullOrEmpty(masterBean.getId())) {
					sb.append(" and id!=" + masterBean.getId());
				}
				if (CPSUtils.compareStrings(masterBean.getTableName(), CPSConstants.DISTRICT_MASTER)) {
					sb.append(" and state_id=" + masterBean.getParentID());
				}
				if (CPSUtils.compareStrings(masterBean.getTableName(), CPSConstants.CATEGORY_MASTER)) {
					sb.append(" and order_by='" + masterBean.getOrderNo()+"'");
				}
				if (CPSUtils.compareStrings(masterBean.getTableName(), CPSConstants.SUB_CATEGORY_MASTER)) {
					sb.append(" and order_by='" + masterBean.getOrderNo()+"'");
				}
				if (CPSUtils.compareStrings(masterBean.getTableName(), CPSConstants.AWARD_CATEGORY_MASTER) && !CPSUtils.isNull(masterBean.getParentID())) {
					sb.append(" and parent_id=" + masterBean.getParentID());
				}
				ps = con.prepareStatement(sb.toString());
				ps.setString(1, masterBean.getName().toUpperCase());
				
				rsq = ps.executeQuery();
			}
			if (rsq.next()) {
				i = rsq.getInt(1);
			}
			if (i > 0) {
				message = CPSConstants.DUPLICATE;
			} else {
				message = CPSConstants.SUCCESS;
			}
			 
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
			ConnectionUtil.closeConnection(session, ps, rsq);
		}
		//log.debug("::SQLMasterDataDAO>>>>>>>>>>>>checkName ---> method End>>>>>>>>>>>>>>");
		return message;
	}

	public String createMasterData(MasterDataBean masterBean) throws Exception {
		String message = null;
		Session session = null;
		
		Transaction tx = null;
		String sql = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			log.debug("::SQLMasterDataDAO>>>>>>>>>>>>createMasterData ---> method Start>>>>>>>>>>>>>>");
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			con = session.connection();
			if (masterBean.getType().equals(CPSConstants.DISNUMBER)) {
				DispensaryNumberDTO dispensaryDTO=new DispensaryNumberDTO();
				
				dispensaryDTO.setDispensaryNumber(masterBean.getTypeValue());
				dispensaryDTO.setDispensaryLocationId(masterBean.getParentID());
				dispensaryDTO.setDescription(masterBean.getDescription());
				dispensaryDTO.setStatus(1);
				dispensaryDTO.setCreationDate(CPSUtils.getCurrentDate());
				dispensaryDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				session.saveOrUpdate(dispensaryDTO);
		
//				sql = "insert into " + masterBean.getTableName()
//						+ "(id,dispensary_number,dispensary_location_id,status,creation_date,last_modified_date,description) values(?,?,?,1,sysdate,sysdate,?)";
//				pstmt = con.prepareStatement(sql);
//				pstmt.setInt(1, Integer.parseInt(masterBean.getId()));
//				pstmt.setString(2, masterBean.getTypeValue());
//				pstmt.setString(3, masterBean.getParentID());
//				pstmt.setString(4, masterBean.getDescription());
//				pstmt.executeUpdate();
                
				session.flush();
			} else if (masterBean.getType().equals(CPSConstants.DISTRICT)) {
				
				DistrictTypeDTO districtDto = new DistrictTypeDTO();
				
				districtDto.setName( masterBean.getName());
				districtDto.setStatus(1);
				districtDto.setCreationDate(CPSUtils.getCurrentDate());
				districtDto.setLastModifiedDate(CPSUtils.getCurrentDate());
				districtDto.setDescription(masterBean.getDescription());
				districtDto.setStateId( masterBean.getParentID());
				session.saveOrUpdate(districtDto);
			
				session.flush();
				
			} 
			else if (masterBean.getType().equals(CPSConstants.SUBCATEGORY)) {
				
				 subCategoryDTO subcategoryDto = new subCategoryDTO();
			
				
				 subcategoryDto.setSubCategoryName(masterBean.getName());
				 subcategoryDto.setOrderNo(masterBean.getOrderNo());
				 subcategoryDto.setStatus(1);
				 subcategoryDto.setCreationDate(CPSUtils.getCurrentDate());
				 subcategoryDto.setLastModifiedDate(CPSUtils.getCurrentDate());
				 subcategoryDto.setAlias(masterBean.getAlias());
				 subcategoryDto.setDescription(masterBean.getDescription());
				 subcategoryDto.setCategoryId(masterBean.getParentID());
			
				 session.saveOrUpdate(subcategoryDto);
			     session.flush();
				 session.flush();//tx.commit() ;
				
			} else if (masterBean.getType().equals(CPSConstants.CATEGORY)) {
				
				
				CategoryDTO cDto = new CategoryDTO();
				
				cDto.setName(masterBean.getName());
				cDto.setDescription(masterBean.getDescription());
				cDto.setAlias(masterBean.getAlias());
				cDto.setOrderNo(masterBean.getOrderNo());
				cDto.setCreationDate(CPSUtils.getCurrentDate());
				cDto.setLastModifiedDate(CPSUtils.getCurrentDate());
				cDto.setCreatedBy("");
				cDto.setStatus(1);
				session.saveOrUpdate(cDto);
				session.flush();
				session.flush();//tx.commit() ;
				
			
				
			} else if (masterBean.getType().equals(CPSConstants.AWARDCATEGORY)) {
				/*sql = "insert into " + masterBean.getTableName() + "(id,name,parent_id,status,creation_date,last_modified_date,description) values(?,?,?,1,sysdate,sysdate,?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, Integer.parseInt(masterBean.getId()));
				pstmt.setString(2, masterBean.getName());
				pstmt.setString(3, masterBean.getParentID());
				pstmt.setString(4, masterBean.getDescription());
				pstmt.executeUpdate();
				session.flush();*/
				AwardCategoryDTO awardCategoryDTO =new AwardCategoryDTO();
				
				awardCategoryDTO.setName(masterBean.getName());
				awardCategoryDTO.setParentID(masterBean.getParentID());
				awardCategoryDTO.setStatus(1);
				awardCategoryDTO.setDescription(masterBean.getDescription());
				awardCategoryDTO.setCreationDate(CPSUtils.getCurrentDate());
				awardCategoryDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				session.saveOrUpdate(awardCategoryDTO);
				session.flush();
				
			} else if (masterBean.getType().equals(CPSConstants.QUALIFICATION)) {
				
				QualificationDTO qualificationDto = new QualificationDTO();
				
				qualificationDto.setName(masterBean.getName().trim());
				qualificationDto.setStatus(1);
				qualificationDto.setCreationDate(CPSUtils.getCurrentDate());
				qualificationDto.setLastModifiedDate(CPSUtils.getCurrentDate());
				qualificationDto.setShortForm(masterBean.getShortForm());
				qualificationDto.setDescription(masterBean.getDescription());
				qualificationDto.setFlag(masterBean.getFlag());
				session.saveOrUpdate(qualificationDto);
				session.flush();
				session.flush();//tx.commit() ;
			
			
			} else if (masterBean.getType().equals(CPSConstants.DISCIPLINE)) {
				
				DisciplineDTO disciplineDto = new DisciplineDTO();
				
				disciplineDto.setName(masterBean.getName());
				disciplineDto.setStatus(1);
				disciplineDto.setCreationDate(CPSUtils.getCurrentDate());
				disciplineDto.setLastModifiedDate(CPSUtils.getCurrentDate());
				disciplineDto.setShortForm(masterBean.getShortForm());
				disciplineDto.setDescription(masterBean.getDescription());
				session.saveOrUpdate(disciplineDto);
				session.flush();
				session.flush();//tx.commit() ;
			
				
			} else if (masterBean.getType().equals(CPSConstants.RELIGION)) {
				ReligionDTO religionDto = new ReligionDTO();
				
				religionDto.setName(masterBean.getName());
				religionDto.setStatus(1);
				religionDto.setCreationDate(CPSUtils.getCurrentDate());
				religionDto.setLastModifiedDate(CPSUtils.getCurrentDate());
				religionDto.setDescription(masterBean.getDescription());
				religionDto.setReligion(masterBean.getReligion());
				session.saveOrUpdate(religionDto);
				session.flush();
				session.flush();//tx.commit() ;
			
			} else if (masterBean.getType().equals(CPSConstants.PIN)) {
				PinNumberDTO pinDto = new PinNumberDTO();
				pinDto.setPinNumber(masterBean.getPin());
				pinDto.setStatus(1);
				pinDto.setName(masterBean.getName().toUpperCase());
				session.save(pinDto);
				session.flush();
				/*sql = "insert into " + masterBean.getTableName() + "(id,sfid,pin,status) values(?,?,?,1)";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, Integer.parseInt(masterBean.getId()));
				pstmt.setString(2, masterBean.getName().toUpperCase());
				pstmt.setString(3, masterBean.getPin());
				pstmt.executeUpdate();*/
				
			} else if (masterBean.getType().equals(CPSConstants.SUBQUARTER)) {
				sql = "insert into " + masterBean.getTableName()
						+ "(id,quarters_sub_type,quarters_type_id,status,creation_date,modified_date,description,created_by,modified_by) values(?,?,?,1,sysdate,sysdate,?,?,?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, Integer.parseInt(masterBean.getId()));
				pstmt.setString(2, masterBean.getName());
				pstmt.setString(3, masterBean.getParentID());
				pstmt.setString(4, masterBean.getDescription());
				pstmt.setString(5, masterBean.getSfID());
				pstmt.setString(6, masterBean.getSfID());
				pstmt.executeUpdate();
				session.flush();
			} else if (masterBean.getType().equals(CPSConstants.QUARTERTYPE)) {
				QuarterTypeDTO quarterDto = new QuarterTypeDTO();
				
				quarterDto.setName(masterBean.getName());
				quarterDto.setStatus(1);
				quarterDto.setCreationDate(CPSUtils.getCurrentDate());
				quarterDto.setLastModifiedDate(CPSUtils.getCurrentDate());
				quarterDto.setDescription(masterBean.getDescription());
				quarterDto.setCreatedBy("");
				quarterDto.setLastModifiedBy("");
				session.saveOrUpdate(quarterDto);
				session.flush();
				session.flush();//tx.commit() ;
				
			} 
			else{
				CommonDTO commonDto = null;
				
			    if(masterBean.getType().equals(CPSConstants.RELATION)){
			    commonDto = new FamilyRelationDTO();
			    }
			    else if(masterBean.getType().equals(CPSConstants.APPOINTMENT)){
			     commonDto = new AppointmentDTO();
			    }
			    else if(masterBean.getType().equals(CPSConstants.COMMUNITY)){
				     commonDto = new CommunityDTO();
				    }
			    else if(masterBean.getType().equals(CPSConstants.DEPARTMENTTYPE)){
				     commonDto = new DepartmentTypeDTO();
				    }
			    else if(masterBean.getType().equals(CPSConstants.DEGREE)){
				     commonDto = new DegreeDTO();
				    }
			    else if(masterBean.getType().equals(CPSConstants.GROUP)){
				     commonDto = new GroupDTO();
				    }
			    else if(masterBean.getType().equals(CPSConstants.NOMINEE)){
				     commonDto = new NomineeDTO();
				    }
			    else if(masterBean.getType().equals(CPSConstants.RESERVATION)){
				     commonDto = new ReservationDTO();
				    }
			    else if(masterBean.getType().equals(CPSConstants.RETIREMENTTYPE)){
				     commonDto = new RetirementTypeDTO();
				    }
			    else if(masterBean.getType().equals(CPSConstants.RESERVATION)){
				     commonDto = new ReservationDTO();
				    }
			    else if(masterBean.getType().equals(CPSConstants.STATE)){
				     commonDto = new StateTypeDTO();
				    }
			    else if(masterBean.getType().equals(CPSConstants.YEAR)){
				     commonDto = new YearTypeDTO();
				    }
			    else if(masterBean.getType().equals(CPSConstants.CITY)){
				     commonDto = new CityDTO();
				     commonDto.setCreatedBy("");
				     commonDto.setLastModifiedBy("");
				    }
			    else if(masterBean.getType().equals(CPSConstants.EMPLOYMENT)){
				     commonDto = new EmploymentDTO();
				    }
			    else if(masterBean.getType().equals(CPSConstants.APPROLES)){
				    	commonDto = new ApplicationRolesDTO();
				    }

			 
			   /* else if(masterBean.getType().equals(CPSConstants.APPROLES)){
			    	commonDto = new ApplicationRolesDTO();
			    }*/


			    else if(masterBean.getType().equals(CPSConstants.DIRECTORATE)){
			    	commonDto = new DirectorateDTO();
			        }
			    else if(masterBean.getType().equals(CPSConstants.DIVISION)){
			    	commonDto = new DivisionDTO();
			        }

			    else if(masterBean.getType().equals(CPSConstants.REQUEST)){
			    	commonDto = new RequestWorkDTO();
			        }
			    else if(masterBean.getType().equals(CPSConstants.WORKFLOW)){
			    	commonDto = new WorkflowTypeDTO();
			        }
			    else if(masterBean.getType().equals(CPSConstants.DISLOCATION)){
			    	commonDto = new DispensaryLocationDTO();
			        }
//			    else if(masterBean.getType().equals(CPSConstants.DISNUMBER)){
//			    	commonDto = new DispensaryNumberDTO();
//			        }
//			    else if(masterBean.getType().equals(CPSConstants.AWARDCATEGORY)){
//			    	commonDto = new AwardCategoryDTO();
//			        }
			    else if(masterBean.getType().equals(CPSConstants.MOTHERTONGUE)){
			    	commonDto = new MotherTongueDTO();
			        }
//			    else if(masterBean.getType().equals(CPSConstants.PIN)){
//			    	commonDto = new PinNumberDTO();
//			        }
			   
               

			    if(commonDto != null){

				commonDto.setName(masterBean.getName());
				commonDto.setStatus(1);
				commonDto.setCreationDate(CPSUtils.getCurrentDate());
				commonDto.setLastModifiedDate(CPSUtils.getCurrentDate());
				commonDto.setDescription(masterBean.getDescription());
				
			    }
			    session.saveOrUpdate(commonDto);
				session.flush();
				session.flush();//tx.commit() ;
				 
			}
		 message = CPSConstants.SUCCESS;
		    session.flush() ; //con.commit();
			session.flush();
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		log.debug("::SQLMasterDataDAO>>>>>>>>>>>>createMasterData ---> method End>>>>>>>>>>>>>>");
		return message;
	}
	
	public String updateMasterData(MasterDataBean masterBean) throws Exception {
		String message = null;
		Session session = null;
		String sql = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			log.debug("::SQLMasterDataDAO>>>>>>>>>>>>updateMasterData ---> method Start>>>>>>>>>>>>>>");
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			if (masterBean.getType().equals(CPSConstants.DISNUMBER)) {
				sql = "update " + masterBean.getTableName() + " set dispensary_number=?,dispensary_location_id=?,last_modified_date=sysdate,description=? where id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, masterBean.getTypeValue());
				pstmt.setString(2, masterBean.getParentID());
				pstmt.setString(3, masterBean.getDescription());
				pstmt.setString(4, masterBean.getId());
				pstmt.executeUpdate();
				session.flush();
			} else if (masterBean.getType().equals(CPSConstants.DISTRICT)) {
				sql = "update " + masterBean.getTableName() + " set name=?,last_modified_date=sysdate,state_id=?,description=? where id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, masterBean.getName());
				pstmt.setString(2, masterBean.getParentID());
				pstmt.setString(3, masterBean.getDescription());
				pstmt.setString(4, masterBean.getId());
				pstmt.executeUpdate();
				session.flush();
			} else if (masterBean.getType().equals(CPSConstants.CATEGORY)) {
				sql = "update " + masterBean.getTableName() + " set name=?,last_modified_date=sysdate,id=?,description=?,order_by=?,alias=? where id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, masterBean.getName());
				pstmt.setString(2, masterBean.getId());
				pstmt.setString(3, masterBean.getDescription());
				pstmt.setString(4, masterBean.getOrderNo());
				pstmt.setString(5, masterBean.getAlias());
				pstmt.setString(6, masterBean.getId());

				pstmt.executeUpdate();
				session.flush();
			} else if (masterBean.getType().equals(CPSConstants.REQUEST)) {
				sql = "update " + masterBean.getTableName() + " set REQUEST_TYPE=?,last_modified_date=sysdate,description=? where REQUEST_TYPE_ID=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, masterBean.getName());
				pstmt.setString(2, masterBean.getDescription());
				pstmt.setString(3, masterBean.getId());
				pstmt.executeUpdate();
				session.flush();
			} else if (masterBean.getType().equals(CPSConstants.AWARDCATEGORY)) {
				sql = "update " + masterBean.getTableName() + " set name=?,last_modified_date=sysdate,parent_id=?,description=? where id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, masterBean.getName());
				pstmt.setString(2, masterBean.getParentID());
				pstmt.setString(3, masterBean.getDescription());
				pstmt.setString(4, masterBean.getId());
				pstmt.executeUpdate();
				session.flush();
			} else if (masterBean.getType().equals(CPSConstants.DISCIPLINE)) {
				sql = "update " + masterBean.getTableName() + " set name=?,last_modified_date=sysdate,description=?,code=? where id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, masterBean.getName());
				pstmt.setString(2, masterBean.getDescription());
				pstmt.setString(3, masterBean.getShortForm());
				pstmt.setString(4, masterBean.getId());
				pstmt.executeUpdate();
				session.flush();
			} else if (masterBean.getType().equals(CPSConstants.QUALIFICATION)) {
				sql = "update " + masterBean.getTableName() + " set name=?,last_modified_date=sysdate,description=?,code=?,prof_flag=? where id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, masterBean.getName());
				pstmt.setString(2, masterBean.getDescription());
				pstmt.setString(3, masterBean.getShortForm());
				pstmt.setString(4, masterBean.getFlag());
				pstmt.setString(5, masterBean.getId());
				pstmt.executeUpdate();
				session.flush();
			} else if (masterBean.getType().equals(CPSConstants.RELIGION)) {
				sql = "update " + masterBean.getTableName() + " set name=?,last_modified_date=sysdate,description=?,minority_flag=? where id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, masterBean.getName());
				pstmt.setString(2, masterBean.getDescription());
				pstmt.setString(3, masterBean.getReligion());
				pstmt.setString(4, masterBean.getId());
				pstmt.executeUpdate();
				session.flush();
			} else if (masterBean.getType().equals(CPSConstants.PIN)) {
				sql = "update " + masterBean.getTableName() + " set sfid=?, pin=?,status=1 where  id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, masterBean.getName().toUpperCase());
				pstmt.setString(2, masterBean.getPin());
				pstmt.setString(3, masterBean.getId());
				pstmt.executeUpdate();
				session.flush();
			} else if (masterBean.getType().equals(CPSConstants.SUBCATEGORY)) {
				sql = "update " + masterBean.getTableName() + " set name=?,last_modified_date=sysdate,description=?,order_by=?,alias=?,category_id=? where id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, masterBean.getName());
				pstmt.setString(2, masterBean.getDescription());
				pstmt.setString(3, masterBean.getOrderNo());
				pstmt.setString(4, masterBean.getAlias());
				pstmt.setString(5, masterBean.getParentID());
				pstmt.setString(6, masterBean.getId());
				pstmt.executeUpdate();
				session.flush();
			} else if (masterBean.getType().equals(CPSConstants.QUARTERTYPE)) {
				sql = "update " + masterBean.getTableName() + " set quarter_type=?,modified_date=sysdate,description=?,modified_by=? where id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, masterBean.getName());
				pstmt.setString(2, masterBean.getDescription());
				pstmt.setString(3, masterBean.getSfID());
				pstmt.setString(4, masterBean.getId());
				pstmt.executeUpdate();
				session.flush();
			} else if (masterBean.getType().equals(CPSConstants.SUBQUARTER)) {
				sql = "update " + masterBean.getTableName() + " set quarters_sub_type=?,modified_date=sysdate,description=?,quarters_type_id=?,modified_by=? where id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, masterBean.getName());
				pstmt.setString(2, masterBean.getDescription());
				pstmt.setString(3, masterBean.getParentID());
				pstmt.setString(4, masterBean.getSfID());
				pstmt.setString(5, masterBean.getId());
				pstmt.executeUpdate();
				session.flush();
			} else {
				sql = "update " + masterBean.getTableName() + " set name=?,last_modified_date=sysdate,description=? where id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, masterBean.getName());
				pstmt.setString(2, masterBean.getDescription());
				pstmt.setString(3, masterBean.getId());
				pstmt.executeUpdate();
				session.flush();
			}
			message = CPSConstants.SUCCESS;
		
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, pstmt, null);
		}
		log.debug("message ---> " + message);
		log.debug("::SQLMasterDataDAO>>>>>>>>>>>>updateMasterData ---> method End>>>>>>>>>>>>>>");
		return message;
	}

	public String deleteMasterData(MasterDataBean masterBean) throws Exception {
		String message = null;
		Session session = null;
		String sql = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		boolean status = true;
		try {
			log.debug("::SQLMasterDataDAO>>>>>>>>>>>>deleteMasterData ---> method Start>>>>>>>>>>>>>>");
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			if (masterBean.getType().equals(CPSConstants.REQUEST)) {
				sql = "update " + masterBean.getTableName() + " set status=0,last_modified_date=sysdate where REQUEST_TYPE_ID=?";

			} else if (masterBean.getType().equals(CPSConstants.LOAN)) {

				sql = "select count(*) from loan_type_details where loan_type_id=" + masterBean.getId() + " and status=1";

				pstmt = con.prepareStatement(sql);
				rst = pstmt.executeQuery();
				if (rst.next()) {
					if (rst.getInt(1) > 0) {
						message = CPSConstants.EXIST;
						status = false;
					} else {
						sql = "update " + masterBean.getTableName() + " set status=0,last_modified_date=sysdate where id=?";

					}
				}
			} else if (masterBean.getType().equals(CPSConstants.QUARTERTYPE) || masterBean.getType().equals(CPSConstants.SUBQUARTER)) {
				sql = "update " + masterBean.getTableName() + " set status=0, modified_date=sysdate where id=?";
			} else {

				sql = "update " + masterBean.getTableName() + " set status=0,last_modified_date=sysdate where id=?";
			}
			if (status) {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, Integer.parseInt(masterBean.getId()));
				pstmt.executeUpdate();
				session.flush();
				message = CPSConstants.DELETE;
			}
			
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, pstmt, null);
		}
		log.debug("::SQLMasterDataDAO>>>>>>>>>>>>deleteMasterData ---> method End>>>>>>>>>>>>>>");
		return message;
	}

	public MasterDataBean getDesignationTableList(MasterDataBean masterBean) throws Exception {
		Session session = null;
		Statement st = null;
		ResultSet rsq = null;
		Connection con = null;
		try {
			log.debug("::SQLMasterDataDAO>>>>>>>>>>>>getDesignationTableList ---> method Start>>>>>>>>>>>>>>");
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String sql = "select dmap.desig_id,dm.name designation,dm.desigalias,dmap.category_id,cat.name category,scm.id subCategoryID,scm.name subcategory,dmap.group_id,"
					+ "gr.name groupName,dmap.type,dm.code,dm.service_type,dm.order_no,dm.description from designation_mappings dmap,designation_master dm,"
					+ "category_master cat,group_master gr,sub_category_master scm where dmap.desig_id=dm.id and cat.id=dmap.category_id "
					+ "and scm.id=dmap.sub_category_id and scm.category_id=cat.id and dm.status=1 and cat.status=1 and gr.id=dmap.group_id "
					+ "and gr.status=1 and scm.status=1 order by cat.order_by,scm.order_by,dm.order_no";
			st = con.createStatement();
			rsq = st.executeQuery(sql);
			ArrayList<DesignationDTO> designationArr = new ArrayList<DesignationDTO>();
			while (rsq.next()) {
				DesignationDTO desigDTO = new DesignationDTO();
				desigDTO.setDesigID(rsq.getString("desig_id"));
				desigDTO.setDesignationId(rsq.getString("designation"));
				desigDTO.setDesigAlias(rsq.getString("desigalias"));
				desigDTO.setCategoryID(rsq.getString("category_id"));
				desigDTO.setCategoryName(rsq.getString("category"));
				desigDTO.setSubCategoryID(rsq.getString("subCategoryID"));
				desigDTO.setSubCategoryName(rsq.getString("subcategory"));
				desigDTO.setGroupID(rsq.getString("group_id"));
				desigDTO.setGroupName(rsq.getString("groupName"));
				desigDTO.setType(rsq.getString("type"));
				desigDTO.setCode(rsq.getString("code"));
				desigDTO.setServiceType(rsq.getString("service_type"));
				desigDTO.setOrderNo(rsq.getString("order_no"));
				desigDTO.setDescription(rsq.getString("description"));
				designationArr.add(desigDTO);
			}
			masterBean.setDesignationDisplayTable(designationArr);
			
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, st, rsq);
		}
		log.debug("::SQLMasterDataDAO>>>>>>>>>>>>getDesignationTableList ---> method End>>>>>>>>>>>>>>");
		return masterBean;
	}

	public String createDesignation(MasterDataBean masterBean) throws Exception {
		PreparedStatement ps = null;
		Session session = null;
		Connection con = null;
		String message = "";
		try {
			log.debug("::SQLMasterDataDAO>>>>>>>>>>>>createDesignation ---> method Start>>>>>>>>>>>>>>");
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String sql = "insert into designation_master(id,name,status,creation_date,last_modified_date,code,service_type,order_no,description,desigalias) values(?,?,1,sysdate,sysdate,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, masterBean.getId());
			ps.setString(2, masterBean.getDesignationId());
			ps.setString(3, masterBean.getShortForm());
			ps.setString(4, masterBean.getServiceType());
			ps.setString(5, masterBean.getOrderNo());
			ps.setString(6, masterBean.getDescription());
			ps.setString(7, masterBean.getDesigAlias());
			ps.executeUpdate();
			session.flush();
			session.flush() ; //con.commit();
			con.close();
			message = CPSConstants.SUCCESS;
			
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			//con.rollback();
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, null);
		}
		log.debug("::SQLMasterDataDAO>>>>>>>>>>>>createDesignation ---> method End>>>>>>>>>>>>>>");
		return message;
	}

	public String createDesigMapping(MasterDataBean masterBean) throws Exception {
		PreparedStatement ps = null;
		Session session = null;
		Connection con = null;
		String message = "";
		try {
			log.debug("::SQLMasterDataDAO>>>>>>>>>>>>createDesignation ---> method Start>>>>>>>>>>>>>>");
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String sql = "insert into designation_mappings(desig_id,category_id,group_id,type,sub_category_id) values(?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, masterBean.getId());
			ps.setString(2, masterBean.getCategory());
			ps.setString(3, masterBean.getGroup());
			ps.setString(4, masterBean.getTypeValue());
			ps.setString(5, masterBean.getSubCategory());
			ps.executeUpdate();
			session.flush();
			message = CPSConstants.SUCCESS;
			
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, null);
		}
		log.debug("::SQLMasterDataDAO>>>>>>>>>>>>createDesignation ---> method End>>>>>>>>>>>>>>");
		return message;

	}

	public String updateDesignation(MasterDataBean masterBean) throws Exception {
		PreparedStatement ps = null;
		Session session = null;
		Connection con = null;
		String message = "";
		try {
			log.debug("::SQLMasterDataDAO>>>>>>>>>>>>updateDesignation ---> method Start>>>>>>>>>>>>>>");
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String sql = "update designation_master set name=?,last_modified_date=sysdate,code=?,service_type=?,order_no=?,description=?,desigalias=? where id=?";

			ps = con.prepareStatement(sql);
			ps.setString(1, masterBean.getDesignationId());
			ps.setString(2, masterBean.getShortForm());
			ps.setString(3, masterBean.getServiceType());
			ps.setString(4, masterBean.getOrderNo());
			ps.setString(5, masterBean.getDescription());
			ps.setString(6, masterBean.getDesigAlias());
			ps.setString(7, masterBean.getId());

			ps.executeUpdate();
			session.flush();
			message = CPSConstants.SUCCESS;
			
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, null);
		}
		log.debug("::SQLMasterDataDAO>>>>>>>>>>>>updateDesignation ---> method End>>>>>>>>>>>>>>");
		return message;
	}

	public String updateDesignationMapping(MasterDataBean masterBean) throws Exception {
		PreparedStatement ps = null;
		Session session = null;
		Connection con = null;
		String message = "";
		try {
			log.debug("::SQLMasterDataDAO>>>>>>>>>>>>updateDesignationMapping ---> method Start>>>>>>>>>>>>>>");
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String sql = "update designation_mappings set category_id=?,group_id=?,type=?,sub_category_id=? where desig_id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, masterBean.getCategory());
			ps.setString(2, masterBean.getGroup());
			ps.setString(3, masterBean.getTypeValue());
			ps.setString(4, masterBean.getSubCategory());
			ps.setString(5, masterBean.getId());
			ps.executeUpdate();
			session.flush();
			message = CPSConstants.SUCCESS;
		
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, null);
		}
		log.debug("::SQLMasterDataDAO>>>>>>>>>>>>updateDesignationMapping ---> method End>>>>>>>>>>>>>>");
		return message;
	}

	public String deleteDesignation(String desigID) throws Exception {
		PreparedStatement ps = null;
		Session session = null;
		Connection con = null;
		String message = "";
		try {
			log.debug("::SQLMasterDataDAO>>>>>>>>>>>>deleteDesignation ---> method Start>>>>>>>>>>>>>>");
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String sql = "update designation_master set status=0,last_modified_date=sysdate where id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, desigID);
			ps.executeUpdate();
			session.flush();
			message = CPSConstants.DELETE;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, null);
		}
		log.debug("::SQLMasterDataDAO>>>>>>>>>>>>deleteDesignation ---> method End>>>>>>>>>>>>>>");
		return message;
	}

	public List<DistrictTypeDTO> getDistrictStateList() throws Exception {
		Session session = null;
		ArrayList<DistrictTypeDTO> districtList = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rsq = null;
		try {
			log.debug("::SQLMasterDataDAO>>>>>>>>>>>>getDistrictStateList() ---> method Start>>>>>>>>>>>>>>");
			districtList = new ArrayList<DistrictTypeDTO>();
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String sql = "select d.id,d.name,d.description,s.name state,s.id stateId from district_master d,state_master s where d.state_Id=s.id and d.status=1";
			stmt = con.createStatement();
			rsq = stmt.executeQuery(sql);
			while (rsq.next()) {
				DistrictTypeDTO distDTO = new DistrictTypeDTO();
				distDTO.setId(rsq.getInt("id"));
				distDTO.setName(rsq.getString("name"));
				distDTO.setDescription(rsq.getString("description"));
				distDTO.setState(rsq.getString("state"));
				distDTO.setStateId(rsq.getString("stateId"));
				districtList.add(distDTO);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, stmt, rsq);
		}
		log.debug("::SQLMasterDataDAO>>>>>>>>>>>>getDistrictStateList() ---> method End>>>>>>>>>>>>>>");
		return districtList;
	}

	public List<PinNumberDTO> getPinNumberList() throws Exception {
		Session session = null;
		ArrayList<PinNumberDTO> pinList = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rsq = null;
		try {
			log.debug("::SQLMasterDataDAO>>>>>>>>>>>>getPinNumberList() ---> method Start>>>>>>>>>>>>>>");
			pinList = new ArrayList<PinNumberDTO>();
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			/*String sql = "select * from emp_pin_number where status=1 order by sfid";*/
			String sql = "select pin.sfid,em.name_in_service_book,pin.id,pin.pin,pin.status  from emp_pin_number PIN,emp_master EM where pin.status=1  and em.sfid=pin.sfid order by pin.sfid";
			stmt = con.createStatement();
			rsq = stmt.executeQuery(sql);
			while (rsq.next()) {
				PinNumberDTO pinDTO = new PinNumberDTO();
				pinDTO.setName(rsq.getString("sfid"));
				pinDTO.setDescription(rsq.getString("name_in_service_book"));
				pinDTO.setPinNumber(rsq.getString("pin"));
				pinDTO.setStatus(rsq.getInt("status"));
				pinDTO.setId(rsq.getInt("id"));
				pinList.add(pinDTO);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, stmt, rsq);
		}
		log.debug("::SQLMasterDataDAO>>>>>>>>>>>>getPinNumberList() ---> method End>>>>>>>>>>>>>>");
		return pinList;
	}

	public List<DispensaryLocationDTO> getDisLocationList() throws Exception {
		Session session = null;
		ArrayList<DispensaryLocationDTO> locationList = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rsq = null;
		try {
			log.debug("::SQLMasterDataDAO>>>>>>>>>>>>getDisLocationList() ---> method Start>>>>>>>>>>>>>>");
			locationList = new ArrayList<DispensaryLocationDTO>();
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String sql = "select dm.id,dm.dispensary_number,dm.description,(select name from dispensary_location where id=dm.dispensary_location_id) name,(select id from dispensary_location where id=dm.dispensary_location_id) parentid from dispensary_mapping dm where dm.status=1";
			stmt = con.createStatement();
			rsq = stmt.executeQuery(sql);
			while (rsq.next()) {
				DispensaryLocationDTO distDTO = new DispensaryLocationDTO();
				distDTO.setId(rsq.getInt("id"));
				distDTO.setName(rsq.getString("dispensary_number"));
				distDTO.setDescription(rsq.getString("description"));
				distDTO.setDispensaryNumber(rsq.getString("name"));// Dispensary
				// location
				distDTO.setParentID(rsq.getString("parentid"));
				locationList.add(distDTO);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, stmt, rsq);
		}
		log.debug("::SQLMasterDataDAO>>>>>>>>>>>>getDisLocationList() ---> method End>>>>>>>>>>>>>>");
		return locationList;
	}

	public List<AwardCategoryDTO> getAwardOrganizationList() throws Exception {
		Session session = null;
		ArrayList<AwardCategoryDTO> orgList = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rsq = null;
		try {
			log.debug("::SQLMasterDataDAO>>>>>>>>>>>>getAwardOrganizationList() ---> method Start>>>>>>>>>>>>>>");
			orgList = new ArrayList<AwardCategoryDTO>();
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String sql = "select a.id,a.name,p.id parentid,p.name parentname,a.description from award_category_master a,award_category_master p where a.parent_id=p.id and a.parent_id!='0' and a.status='1' order by name";
			stmt = con.createStatement();
			rsq = stmt.executeQuery(sql);
			while (rsq.next()) {
				AwardCategoryDTO orgDTO = new AwardCategoryDTO();
				orgDTO.setId(rsq.getInt("id"));
				orgDTO.setName(rsq.getString("name"));
				orgDTO.setDescription(rsq.getString("description"));
				orgDTO.setOrgName(rsq.getString("parentname"));
				orgDTO.setParentID(rsq.getString("parentid"));
				orgList.add(orgDTO);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, stmt, rsq);
		}
		log.debug("::SQLMasterDataDAO>>>>>>>>>>>>getAwardOrganizationList() ---> method End>>>>>>>>>>>>>>");
		return orgList;
	}

	@SuppressWarnings("unchecked")
	public List<RequestWorkDTO> getRequestDetails() throws Exception {
		Session session = null;
		List<RequestWorkDTO> list = null;
		try {
			log.debug("::SQLMasterDataDAO>>>>>>>>>>>>getRequestDetails() ---> method Start>>>>>>>>>>>>>>");
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			list = session.createCriteria(RequestWorkDTO.class).add(Restrictions.ne("status", 0)).addOrder(Order.asc("name")).list();

		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, null, null);
		}
		log.debug("::SQLMasterDataDAO>>>>>>>>>>>>getRequestDetails() ---> method End>>>>>>>>>>>>>>");
		return list;
	}

	public String updateRequestTypes(String requestFlags) throws Exception {
		String message = "";
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			log.debug("::SQLMasterDataDAO>>>>>>>>>>>>updateRequestTypes ---> method Start>>>>>>>>>>>>>>");
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String sql = "update request_master set internal_flag=? where request_type_id=?";
			ps = con.prepareStatement(sql);

			String[] requestFlagArr = requestFlags.split(",");
			for (String request : requestFlagArr) {
				ps.setString(1, request.split("_")[1]);
				ps.setString(2, request.split("_")[0]);
				rs = ps.executeQuery();
			}
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rs);
		}
		log.debug("::SQLMasterDataDAO>>>>>>>>>>>>updateRequestTypes ---> method End>>>>>>>>>>>>>>");
		return message;
	}

	public String validSfid(MasterDataBean masterBean) throws Exception {
		String message = null;
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		int i = 0;
		try {
			log.debug("::SQLMasterDataDAO>>>>>>>>>>>>validSfid ---> method Start>>>>>>>>>>>>>>");
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			StringBuffer sb = new StringBuffer();

			sb.append("select count(*) from emp_master where sfid=? and status=1");
			ps = con.prepareStatement(sb.toString());
			ps.setString(1, masterBean.getName().toUpperCase());
			rsq = ps.executeQuery();

			if (rsq.next()) {
				i = rsq.getInt(1);
			}
			if (i > 0) {
				message = CPSConstants.SUCCESS;
			} else {
				message = CPSConstants.NOTEXISTS;
			}

		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rsq);
		}
		log.debug("::SQLMasterDataDAO>>>>>>>>>>>>validSfid ---> method End>>>>>>>>>>>>>>");
		return message;

	}

	public String chageEmployeeDetails(String changeSfid) throws Exception {
		Session session = null;
		String empName = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			empName = (String) session.createSQLQuery("SELECT NAME_IN_SERVICE_BOOK FROM emp_master WHERE SFID=? AND STATUS=1 ").setString(0, changeSfid).uniqueResult();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return empName;
	}

	@SuppressWarnings("unchecked")
	public List<QuarterTypeBean> getSubQuarterList() throws Exception {
		Session session = null;
		List<QuarterTypeBean> list = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			list = session
					.createSQLQuery(
							"select qsm.id id,qsm.quarters_sub_type name,qsm.quarters_type_id parentID,qtm.quarter_type parentName,qsm.description from pay_quarters_subtype_master qsm,pay_quarters_type_master qtm where qsm.status=1 and qtm.status=1 and qsm.quarters_type_id=qtm.id order by qsm.quarters_sub_type")
					.addScalar("id", Hibernate.INTEGER).addScalar("name", Hibernate.STRING).addScalar("parentID", Hibernate.INTEGER).addScalar("parentName", Hibernate.STRING).addScalar("description",
							Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(QuarterTypeBean.class)).list();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}
	public List getAwardMasterList(MasterDataBean masterDataBean)throws Exception
	{
		Session session = null;
		List list = null;
		String sb = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			sb = "select a.id as id,a.name as name,a.awardCatId as awardCatId,a.awardTypeId as awardTypeId,a.awardMaxLimit as awardMaxLimit,a.awardMoney as awardMoney,a.remarks as remarks,c.parentID as parentID,a.description as description from AwardDTO a,AwardCategoryDTO c where c.status=1 and a.awardCatId=c.id and a.status=1";
			list = session.createQuery(sb).setResultTransformer(Transformers.aliasToBean(AwardDTO.class)).list();
			
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}
	
	public List getAwardCategoryList()throws Exception
	{
		Session session = null;
		List list = null;
		String sb = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			sb = "from AwardCategoryDTO where status=1";
			list = session.createQuery(sb).list();
			
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;
		
	}
	public String updateAwardMasterData(MasterDataBean masterBean)throws Exception
	{
		log.debug("cancelNewRequest() --> Start");
		
		Session session = null;
		String message = null;
		
		String sb = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			
			sb = "update AwardDTO set name=?,awardTypeId=?,awardCatId=?,remarks=?,description=?,awardMoney=?,awardMaxLimit=?,lastModifiedDate=sysdate";
			
				
			sb += " where status =1 and id="+masterBean.getId();
		
		
			Query qry = session.createQuery(sb);
		
			
			qry.setString(0, masterBean.getName());
			qry.setString(1, masterBean.getAwardTypeId());
			qry.setString(2, masterBean.getAwardCatId());
			qry.setString(3, masterBean.getRemarks());
			qry.setString(4, masterBean.getDescription());
			qry.setString(5, masterBean.getAwardMoney());
			qry.setString(6, masterBean.getAwardMaxLimit());
			
		
		
		qry.executeUpdate();
		session.flush();
		message = CPSConstants.UPDATE;
		session.flush();//tx.commit() ;
			
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			//tx.rollback();
			throw e;
		} finally {
			
			//session.close();
		}
		log.debug("cancelNewRequest() --> End");
		return message;
	}

	public List checkAwardMasterData(MasterDataBean masterBean)throws Exception
	{
		Session session = null;
		List list = null;
		String sb = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			sb = "select id as id from AwardDTO where status=1 and upper(name) like '"+masterBean.getName().toUpperCase()+"'";
			if(!CPSUtils.isNullOrEmpty(masterBean.getId()))
			   sb += " and id !="+masterBean.getId();
			list = session.createQuery(sb).list();
			
			
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}

	public List getCityList()throws Exception
	{
		List list = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			Query qry = session.createQuery("select id as id,name as name,description as description from CityDTO where status=1 order by name");
			
			list = qry.setResultTransformer(Transformers.aliasToBean(CityDTO.class)).list();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<LetterNumberReferenceDTO> getLetterNoRefList(MasterDataBean masterbean)throws Exception
	{
		BigDecimal der = null;
		
		List<LetterNumberReferenceDTO> list = null;
		
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			der = (BigDecimal)session.createSQLQuery("select OFFICE_ID from Emp_Master where sfid =?").setString(0,masterbean.getSfID()).uniqueResult();
		    if(masterbean.getLetterDate() == null || masterbean.getLetterDate().equalsIgnoreCase("") ){
			list = session.createQuery("from LetterNumberReferenceDTO where status IN(1,2) and deptId="+der.intValue()+"").list();
		    }else{
		    	list = session.createQuery("from LetterNumberReferenceDTO where status IN(1,2) and letterDate='"+masterbean.getLetterDate()+"' and deptId="+der.intValue()+" ").list();	
		    }
             session.flush();
             
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}


	public String manageLetterData(MasterDataBean masterBean) throws Exception{
		Session session = null;
		String message = null;
		BigDecimal der = null;
		LetterNumberReferenceDTO lnrDTO = new LetterNumberReferenceDTO();
		try{
			session = hibernateUtils.getSession();
			if((!(masterBean.getId() == null)) && !(masterBean.getId().equals(""))){
			lnrDTO=(LetterNumberReferenceDTO)session.createCriteria(LetterNumberReferenceDTO.class).add(Expression.eq("id",Integer.parseInt(masterBean.getId()))).uniqueResult();
			der = (BigDecimal)session.createSQLQuery("select OFFICE_ID from Emp_Master where sfid =?").setString(0,masterBean.getSfID()).uniqueResult();
			lnrDTO.setDeptId(der.intValue());
			lnrDTO.setLetterDate(masterBean.getLetterDate());
			lnrDTO.setLetterNumber(masterBean.getLetterNumber());
			lnrDTO.setDescription(masterBean.getDescription());
			lnrDTO.setFromPalce(masterBean.getFromPalce());
			lnrDTO.setToPalce(masterBean.getToPalce());
			lnrDTO.setRemarks(masterBean.getRemarks());
			lnrDTO.setCreationDate(CPSUtils.getCurrentDate());
			lnrDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			lnrDTO.setStatus(masterBean.getStatus().intValue());
			lnrDTO.setCreatedBy(masterBean.getSfID());
			session.update(lnrDTO);
			session.flush();
			message = CPSConstants.UPDATE;
			}else{
			der = (BigDecimal)session.createSQLQuery("select OFFICE_ID from Emp_Master where sfid =?").setString(0,masterBean.getSfID()).uniqueResult();
			
			lnrDTO.setDeptId(der.intValue());
			lnrDTO.setLetterDate(masterBean.getLetterDate());
			lnrDTO.setLetterNumber(masterBean.getLetterNumber());
			lnrDTO.setDescription(masterBean.getDescription());
			lnrDTO.setFromPalce(masterBean.getFromPalce());
			lnrDTO.setToPalce(masterBean.getToPalce());
			lnrDTO.setRemarks(masterBean.getRemarks());
			lnrDTO.setCreationDate(CPSUtils.getCurrentDate());
			lnrDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			lnrDTO.setStatus((masterBean.getStatus().intValue()));
			lnrDTO.setCreatedBy(masterBean.getSfID());
			session.saveOrUpdate(lnrDTO);
			message = CPSConstants.SUCCESS;
			}
		}catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		
		
		
		return message;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String deleteLetterData(MasterDataBean masterBean) throws Exception {
		String message = null;
		List<LetterNumberReferenceDTO> list = null;
		Session session = null;
		try{
			session = hibernateUtils.getSession();
		   LetterNumberReferenceDTO lnrd = null;
			//cghsAdvance = (CghsAdvanceRequestDTO)session.createCriteria(CghsAdvanceRequestDTO.class).add(Expression.eq("requestID", cghsBean.getRequestId())).uniqueResult();
			
			String qry = "update   LetterNumberReferenceDTO set status=0 where id="+masterBean.getId()+"";
			int i  = session.createQuery(qry).executeUpdate();
			
			if(i>0){
			message = CPSConstants.DELETE;
		}}catch (Exception e) {
			
			throw e;	
		}
		return message;
	}
	//delete functionality in create award screen 
    public String deleteAwardMasterDetails(int id) throws Exception{
		Session session = null;
		String message="";
		try{
			session=hibernateUtils.getSession();
			AwardDTO awardDTO =(AwardDTO)session.get(AwardDTO.class, id);
			awardDTO.setStatus(0);
			message=CPSConstants.DELETE;
			session.flush();
		}catch (Exception e) {
			throw e;
		}finally{
			
		}
		return message;
	}
    //displaying list of emp in emp pin master screen
    @SuppressWarnings("unchecked")
	public List<PinNumberDTO> getPinNoSfidList(String sfid)throws Exception{
    	Session session= null;
    	List<PinNumberDTO> sfidPinNoList=new ArrayList<PinNumberDTO>();
    	Connection con = null;
		Statement stmt = null;
		ResultSet rsq = null;
    	
    	try{
    		session=hibernateUtils.getSession();
    		con = session.connection();
    		String sql="select distinct  sfid,name_in_service_book from emp_master  where sfid  not in(select sfid from emp_pin_number where status=1) and status=1 and working_location in(select working_location from emp_master where sfid='"+sfid+"' and status=1) order by sfid";
    		//sfidPinNoList=session.createSQLQuery("select distinct  sfid,name_in_service_book from emp_master  where sfid  not in(select sfid from emp_pin_number where status=1) and status=1 and working_location in(select working_location from emp_master where sfid='"+sfid+"' and status=1) order by sfid").list();
    		stmt = con.createStatement();
			rsq = stmt.executeQuery(sql);
			while (rsq.next()) {
				PinNumberDTO pinDTO = new PinNumberDTO();
				pinDTO.setName(rsq.getString("sfid"));
				pinDTO.setDescription(rsq.getString("name_in_service_book"));
				sfidPinNoList.add(pinDTO);
			}
    	}catch(Exception e){
    		throw e;
    	}

    	return sfidPinNoList;
    }
}
