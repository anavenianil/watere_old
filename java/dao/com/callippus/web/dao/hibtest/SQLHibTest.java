package com.callippus.web.dao.hibtest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.callippus.web.beans.dto.Phone;

public class SQLHibTest {
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	
	public void manyToOne() throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = hibernateUtils.getSession(); //session = sessionFactory.openSession();
			//transaction = session.beginTransaction();

			// Many to Many
			/*Address address1 = new Address("OMR Road", "Chennai", "TN", "600097");
			Address address2 = new Address("Ring Road", "Banglore", "Karnataka", "560000");
			Student student1 = new Student("Eswar", address1);
			Student student2 = new Student("Joe", address2);
			session.save(student1);
			session.save(student2);

			Student student3 = (Student) session.get(Student.class, 1);
			LeaveTypeDTO l = (LeaveTypeDTO)session.get(LeaveTypeDTO.class,1);
			
			
			System.out.println("1");
			List list1 = session.createQuery("from Student as ard order by ard.studentName asc").list();
			
			leaveBean = (LeaveRequestBean) session1.createQuery("from LeaveRequestBean lrb where lrb.requestID=:reqID").setParameter("reqID", workflowMap.getRequestId()).uniqueResult();
			leaveBean = (LeaveRequestBean) session1.createCriteria(LeaveRequestBean.class).add(Restrictions.eq("requestID", workflowMap.getRequestId())).uniqueResult();*/
			
			
			/*
1) student.hbm.xml
			 
			 <?xml version="1.0"?>
			<!DOCTYPE hibernate-mapping PUBLIC
			"-//Hibernate/Hibernate Mapping DTD 3.0//EN"
			"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">
			<hibernate-mapping>
			    <class name="com.callippus.web.beans.dto.Student" table="STUDENT1">
			        <meta attribute="class-description">This class contains student details.</meta>
			        <id name="studentId" type="int" column="STUDENT_ID">
			            <generator class="increment" />
			        </id>
			        <property name="studentName" type="string" not-null="true" length="100" column="STUDENT_NAME" />
			        <many-to-one name="studentAddress" class="com.callippus.web.beans.dto.Address" column="STUDENT_ADDRESS" not-null="true" cascade="all" unique="true" />
			    </class>
			</hibernate-mapping>
			
DB:
			CREATE TABLE "CHANGE2"."STUDENT1" 
			   (	"STUDENT_ID" NUMBER NOT NULL ENABLE, 
				"STUDENT_NAME" VARCHAR2(100 BYTE), 
				"STUDENT_ADDRESS" NUMBER, 
				 CONSTRAINT "STUDENT1_PK" PRIMARY KEY ("STUDENT_ID")
			  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
			  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
			  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
			  TABLESPACE "USERS"  ENABLE, 
				 CONSTRAINT "STUDENT1_ADDRESS_FK1" FOREIGN KEY ("STUDENT_ADDRESS")
				  REFERENCES "CHANGE2"."ADDRESS" ("ADDRESS_ID") ON DELETE CASCADE ENABLE
			   ) 
			   
Student.java
			private int studentId;
			private String studentName;
			private Address studentAddress;
	
2) address.hbm.xml
			
			<?xml version="1.0"?>
			<!DOCTYPE hibernate-mapping PUBLIC
			"-//Hibernate/Hibernate Mapping DTD 3.0//EN"
			"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">
			<hibernate-mapping>
				<class name="com.callippus.web.beans.dto.Address" table="ADDRESS">
					<meta attribute="class-description">This class contains the student's address
						details.</meta>
					<id name="addressId" type="int" column="ADDRESS_ID">
						<generator class="increment" />
					</id>
					<property name="street" column="STREET" type="string" length="250" />
					<property name="city" column="CITY" type="string" length="50" />
					<property name="state" column="STATE" type="string" length="50" />
					<property name="zipcode" column="ZIPCODE" type="string" length="10" />
				</class>
			</hibernate-mapping>

Address.java
			private int addressId;
			private String street;
			private String city;
			private String state;
			private String zipcode;

DB:

		CREATE TABLE "CHANGE2"."ADDRESS" 
		   (	"ADDRESS_ID" NUMBER NOT NULL ENABLE, 
			"STREET" VARCHAR2(20 BYTE), 
			"CITY" VARCHAR2(20 BYTE), 
			"STATE" VARCHAR2(20 BYTE), 
			"ZIPCODE" VARCHAR2(20 BYTE), 
			 CONSTRAINT "ADDRESS_PK" PRIMARY KEY ("ADDRESS_ID")
		  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
		  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
		  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
		  TABLESPACE "USERS"  ENABLE
		   )
     
			 */

			session.flush() ; //transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
	}

	public void oneToone() throws Exception {
		Session session = null;
		
		try {
			session = hibernateUtils.getSession(); //session = sessionFactory.openSession();

			/*//Transaction tx = session.beginTransaction();
			CourseDTO c = new CourseDTO();
			c.setCourseName("testCourse");

			StudentDTO stu = new StudentDTO();
			stu.setStudentName("test name");
			stu.setCourseDetail(c);
			c.setStudent(stu);

			session.saveOrUpdate(stu);
			// session.saveOrUpdate(c);
			session.flush();//tx.commit() ;

			StudentDTO student3 = (StudentDTO) session.get(StudentDTO.class, 1);
			System.out.println("1");
			List list1 = session.createQuery("from StudentDTO as ard order by ard.studentName asc").list();
			*/
			/*
1) student.hbm.xml
			<?xml version="1.0" encoding="UTF-8"?>
			<!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD//EN"
					"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">
			
			<hibernate-mapping>
				<class name="com.callippus.web.beans.dto.StudentDTO" table="student">
					 <id name="studentID" type="java.lang.Integer" column="student_id">
			            <generator class="increment" />
			        </id>
			
			        <property name="studentName" column="student_name"/>
			        
			        <one-to-one name="courseDetail" class="com.callippus.web.beans.dto.CourseDTO" 
			         cascade="save-update"></one-to-one>    
								
				</class>
			</hibernate-mapping>
			
StudentDTO.java
			private int studentID;
			private String studentName;
			private CourseDTO courseDetail;
			
DB:
		CREATE TABLE "CHANGE2"."STUDENT" 
		   (	"STUDENT_ID" NUMBER NOT NULL ENABLE, 
			"STUDENT_NAME" VARCHAR2(100 BYTE) NOT NULL ENABLE, 
			 CONSTRAINT "STUDENT_PK" PRIMARY KEY ("STUDENT_ID")
		  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
		  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
		  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
		  TABLESPACE "USERS"  ENABLE
		   )
		   
2) course.hbm.xml

		<?xml version="1.0" encoding="UTF-8"?>
		<!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD//EN"
				"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">
		
		<hibernate-mapping>
			<class name="com.callippus.web.beans.dto.CourseDTO" table="course">
				<id name="studentID" type="java.lang.Integer" column="student_id">
		            <generator class="foreign">
		                <param name="property">student</param>
		            </generator>
		        </id>
		        
		        
		        <property name="courseName" column="course_name"/>
		        
		<one-to-one name="student" class="com.callippus.web.beans.dto.StudentDTO" 
		        constrained="true"></one-to-one>
		
		
			</class>
		</hibernate-mapping>
		
CourseDTO.java
		private int studentID;
		private String courseName;
		private StudentDTO student;
	
DB:
		CREATE TABLE "CHANGE2"."COURSE" 
		   (	"STUDENT_ID" NUMBER NOT NULL ENABLE, 
			"COURSE_NAME" VARCHAR2(100 BYTE), 
			 CONSTRAINT "COURSE_PK" PRIMARY KEY ("STUDENT_ID")
		  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
		  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
		  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
		  TABLESPACE "USERS"  ENABLE, 
			 CONSTRAINT "COURSE_STUDENT_FK1" FOREIGN KEY ("STUDENT_ID")
			  REFERENCES "CHANGE2"."STUDENT" ("STUDENT_ID") ON DELETE CASCADE ENABLE
		   ) 
*/

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
	}
	
	public void oneToMany() throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = hibernateUtils.getSession(); //session = sessionFactory.openSession();

			//transaction = session.beginTransaction();

			Set<Phone> phoneNumbers = new HashSet<Phone>();
			phoneNumbers.add(new Phone("house", "32354353"));
			phoneNumbers.add(new Phone("mobile", "9889343423"));

			//Student1 student = new Student1("Eswar", phoneNumbers);
			//session.save(student);
			session.flush() ; //transaction.commit();
			
			/**

		CREATE TABLE "CHANGE2"."STUDENT" 
		   ("STUDENT_ID" NUMBER NOT NULL ENABLE, 
			"STUDENT_NAME" VARCHAR2(100 BYTE) NOT NULL ENABLE, 
			 CONSTRAINT "STUDENT_PK" PRIMARY KEY ("STUDENT_ID")
		  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
		  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
		  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
		  TABLESPACE "USERS"  ENABLE
		   )
		   
	    CREATE TABLE "CHANGE2"."PHONE" 
	   (	"PHONE_ID" NUMBER NOT NULL ENABLE, 
		"PHONE_TYPE" VARCHAR2(20 BYTE), 
		"PHONE_NUMBER" VARCHAR2(20 BYTE), 
		 CONSTRAINT "PHONE_PK" PRIMARY KEY ("PHONE_ID")
	  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
	  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
	  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
	  TABLESPACE "USERS"  ENABLE
	   ) 
	   
	   CREATE TABLE "CHANGE2"."STUDENT_PHONE" 
	   (	"STUDENT_ID" NUMBER NOT NULL ENABLE, 
		"PHONE_ID" NUMBER NOT NULL ENABLE, 
		 CONSTRAINT "STUDENT_PHONE_PHONE_FK1" FOREIGN KEY ("PHONE_ID")
		  REFERENCES "CHANGE2"."PHONE" ("PHONE_ID") ON DELETE CASCADE ENABLE, 
		 CONSTRAINT "STUDENT_PHONE_STUDENT_FK1" FOREIGN KEY ("STUDENT_ID")
		  REFERENCES "CHANGE2"."STUDENT" ("STUDENT_ID") ON DELETE CASCADE ENABLE
	   ) 
	   
	   
		<?xml version="1.0"?>
		<!DOCTYPE hibernate-mapping PUBLIC
		"-//Hibernate/Hibernate Mapping DTD 3.0//EN"
		"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">
		<hibernate-mapping>
		    <class name="com.callippus.web.beans.dto.Student1" table="STUDENT">
		        <id name="studentId" type="int" column="STUDENT_ID">
		            <generator class="increment" />
		        </id>
		        <property name="studentName" type="string" not-null="true" length="100" column="STUDENT_NAME" />
		        <set name="studentPhoneNumbers" table="STUDENT_PHONE" cascade="all">
					<key column="STUDENT_ID" />
					<many-to-many column="PHONE_ID" unique="true" class="com.callippus.web.beans.dto.Phone" />
				</set>
		    </class>
		</hibernate-mapping>


		<?xml version="1.0"?>
		<!DOCTYPE hibernate-mapping PUBLIC
		"-//Hibernate/Hibernate Mapping DTD 3.0//EN"
		"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">
		<hibernate-mapping>
			<class name="com.callippus.web.beans.dto.Phone" table="PHONE">
				<id name="phoneId" type="int" column="PHONE_ID">
					<generator class="increment" />
				</id>
				<property name="phoneType" type="string" length="10" column="PHONE_TYPE" />
				<property name="phoneNumber" type="string" length="15" column="PHONE_NUMBER" />
			</class>
		</hibernate-mapping>

Student.java
		private int studentId;
		private String studentName;
		private Set<Phone> studentPhoneNumbers = new HashSet<Phone>(0);
	
Phone.java
		private int phoneId;
		private String phoneType;
		private String phoneNumber;
	
	
*/
			//Student1 student3 = (Student1) session.get(Student1.class, 3);
			
			List list1 = session.createQuery("from Student1 as ard order by ard.studentName asc").list();
			
			
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
	}
}
