package ma.projet.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ma.projet.beans.Mariage;
import ma.projet.util.HibernateUtil;

public class MariageService implements IDao<Mariage> {
    
    @Override
    public void create(Mariage o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    @Override
    public void update(Mariage o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(o);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    @Override
    public void delete(Mariage o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(o);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    @Override
    public Mariage findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Mariage mariage = null;
        try {
            tx = session.beginTransaction();
            mariage = session.get(Mariage.class, id);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return mariage;
    }
    
    @Override
    public List<Mariage> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Mariage> mariages = null;
        try {
            mariages = session.createQuery("FROM Mariage", Mariage.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return mariages;
    }
}

