package com.example.gestioncocheshibernate.Util;

import com.example.gestioncocheshibernate.Modelo.Coche;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {
    static SessionFactory factory = null;
    static {
        Configuration cfg = new Configuration();
        cfg.configure("configuration/hibernate.cfg.xml");
        cfg.addAnnotatedClass(Coche.class);
        factory = cfg.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }
    public static Session getSession() {
        return factory.getCurrentSession();
    }
}
