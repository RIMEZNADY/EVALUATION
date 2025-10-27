package ma.projet.service;

import ma.projet.classes.EmployeTache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeTacheService implements IDao<EmployeTache> {

    @Override
    public boolean create(EmployeTache o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(o);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(EmployeTache o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(o);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(EmployeTache o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(o);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public EmployeTache findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        EmployeTache employeTache = session.get(EmployeTache.class, id);
        session.close();
        return employeTache;
    }

    @Override
    public List<EmployeTache> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<EmployeTache> employeTaches = session.createQuery("FROM EmployeTache", EmployeTache.class).getResultList();
        session.close();
        return employeTaches;
    }
}
