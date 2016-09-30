package com.callippus.web.dao.outerEmployee;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SQLOuterEmployeeDAO implements IOuterEmployeeDAO, Serializable {

	private static final long serialVersionUID = 7376543344941542448L;
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

}
