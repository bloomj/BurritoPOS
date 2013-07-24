package com.burritopos.service.dao.hibernate;

import org.hibernate.*;
import org.hibernate.cfg.*;
import org.hibernate.service.*;
import org.apache.log4j.*;

/**
 *
 * @author james.bloom
 */
public class BaseSvcImpl {
	private static Logger dLog = Logger.getLogger(BaseSvcImpl.class);
	private static final Configuration config = new Configuration();
	private static SessionFactory sessionFactory = null;
	private static ServiceRegistry serviceRegistry = null;

	//Hibernate
	private static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			dLog.trace("Configuring Hibernate");
			
			config.configure("hibernate\\hibernate.cfg.xml");
			serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
			sessionFactory = config.buildSessionFactory(serviceRegistry);
		}

		return sessionFactory;
	}

	protected static Session getSession() {
		SessionFactory factory = getSessionFactory();
		return (factory != null) ? factory.openSession() : null;
	}
}
