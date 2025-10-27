package ma.projet.util;

import ma.projet.classes.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            
            // Add entity classes
            configuration.addAnnotatedClass(Projet.class);
            configuration.addAnnotatedClass(Tache.class);
            configuration.addAnnotatedClass(Employe.class);
            configuration.addAnnotatedClass(EmployeTache.class);
            
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Initial SessionFactory creation failed: " + e.getMessage());
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
